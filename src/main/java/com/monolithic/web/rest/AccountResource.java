package com.monolithic.web.rest;


import com.monolithic.config.ApplicationProperties;
import com.monolithic.domain.User;
import com.monolithic.repository.UserRepository;
import com.monolithic.security.SecurityUtils;
import com.monolithic.service.AsyncIpService;
import com.monolithic.service.MailService;
import com.monolithic.service.UserService;
import com.monolithic.service.dto.PasswordChangeDTO;
import com.monolithic.service.dto.UserDTO;
import com.monolithic.web.rest.errors.*;
import com.monolithic.web.rest.utils.EmailUtil;
import com.monolithic.web.rest.utils.SunBase64Util;
import com.monolithic.web.rest.vm.KeyAndPasswordVM;
import com.monolithic.web.rest.vm.ManagedUserVM;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final MailService mailService;

    private final RedisTemplate redisTemplate;

    private final AsyncIpService asyncIpService;

    private final ApplicationProperties properties;

    public AccountResource(UserRepository userRepository, UserService userService, MailService mailService, RedisTemplate redisTemplate, AsyncIpService asyncIpService, ApplicationProperties properties) {

        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
        this.redisTemplate = redisTemplate;
        this.asyncIpService = asyncIpService;
        this.properties = properties;
    }

    /**
     * {@code POST  /register} : register the user.
     *
     * @param managedUserVM the managed user View Model.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is already used.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
        String password = SunBase64Util.decode(managedUserVM.getPassword());
        if (!checkPasswordLength(password)) {
            throw new InvalidPasswordException();
        }
        User user = userService.registerUser(managedUserVM, password);
        if (StringUtils.isNoneBlank(user.getEmail())) {
            mailService.sendActivationEmail(EmailUtil.transferEmailUser(user));
        }
    }

    /**
     * {@code GET  /activate} : activate the registered user.
     *
     * @param key the activation key.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be activated.
     */
    @GetMapping("/activate")
    public void activateAccount(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new AccountResourceException("No user was found for this activation key");
        } else {
            mailService.sendWelcomeMail(EmailUtil.transferEmailUser(user.get()));
        }
    }

    /**
     * {@code GET  /authenticate} : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request.
     * @return the login if the user is authenticated.
     */
    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * {@code GET  /account} : get the current user.
     *
     * @return the current user.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be returned.
     */
    @GetMapping("/account")
    public UserDTO getAccount() {
        return userService.getUserWithAuthorities()
            .map(UserDTO::new)
            .orElseThrow(() -> new AccountResourceException("User could not be found"));
    }

    /**
     * {@code GET  /account} : get the current user.
     *
     * @return the current user.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be returned.
     */
    @GetMapping("/account-callback")
    public UserDTO callback(HttpServletRequest request) {
        UserDTO userDTO = userService.getUserWithAuthorities()
            .map(UserDTO::new)
            .orElseThrow(() -> new AccountResourceException("User could not be found"));
        if (StringUtils.isNotBlank(userDTO.getEmail())) {
            mailService.sendLoginNotificationMail(EmailUtil.transferEmailUserDTO(userDTO));
        }
        asyncIpService.ip(request, userDTO);
        return userDTO;
    }

    /**
     * {@code POST  /account} : update the current user information.
     *
     * @param userDTO the current user information.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws PhoneAlreadyUsedException {@code 400 (Bad Request)} if the phone is already used.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user login wasn't found.
     */
    @PostMapping("/account")
    public void saveAccount(@Valid @RequestBody UserDTO userDTO) {
        String appId = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));

        Optional<User> existingUser = Optional.empty();

        if (StringUtils.isNoneBlank(userDTO.getEmail())) {
            existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
            if (existingUser.isPresent() && (existingUser.get().getEmail().equalsIgnoreCase(userDTO.getEmail()))) {
                throw new EmailAlreadyUsedException();
            }
        }
        if (!existingUser.isPresent() && StringUtils.isNoneBlank(userDTO.getPhone())) {
            existingUser = userRepository.findOneByPhone(userDTO.getPhone());
            if (existingUser.isPresent() && (existingUser.get().getPhone().equalsIgnoreCase(userDTO.getPhone()))) {
                throw new PhoneAlreadyUsedException();
            }
        }
        Optional<User> user = userRepository.findOneByAppId(appId);
        if (!user.isPresent()) {
            throw new AccountResourceException("User could not be found");
        }
        userService.updateUser(userDTO.getEmail(), userDTO.getPhone(),
            userDTO.getLangKey(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getImageUrl());
    }

    /**
     * {@code POST  /account/change-password} : changes the current user's password.
     *
     * @param passwordChangeDto current and new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the new password is incorrect.
     */
    @PostMapping(path = "/account/change-password")
    public void changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
        String currentPass = SunBase64Util.decode(passwordChangeDto.getCurrentPassword());
        String newPass = SunBase64Util.decode(passwordChangeDto.getNewPassword());
        if (!checkPasswordLength(newPass)) {
            throw new InvalidPasswordException();
        }
        userService.changePassword(currentPass, newPass);
    }

    /**
     * {@code POST   /account/reset-password/init} : Send an email to reset the password of the user.
     *
     * @param mail the mail of the user.
     * @throws EmailNotFoundException {@code 400 (Bad Request)} if the email address is not registered.
     */
    @PostMapping(path = "/account/reset-password/init")
    public void requestPasswordReset(@RequestBody String mail) {
        User user = userService.requestPasswordReset(mail).orElseThrow(EmailNotFoundException::new);
        mailService.sendPasswordResetMail(EmailUtil.transferEmailUser(user));
    }

    /**
     * {@code POST   /account/reset-password/finish} : Finish to reset the password of the user.
     *
     * @param keyAndPassword the generated key and the new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the password could not be reset.
     */
    @PostMapping(path = "/account/reset-password/finish")
    public void finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        String newPass = SunBase64Util.decode(keyAndPassword.getNewPassword());
        if (!checkPasswordLength(newPass)) {
            throw new InvalidPasswordException();
        }
        Optional<User> user =
            userService.completePasswordReset(newPass, keyAndPassword.getKey());

        if (!user.isPresent()) {
            throw new AccountResourceException("No user was found for this reset key");
        }
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    }
}
