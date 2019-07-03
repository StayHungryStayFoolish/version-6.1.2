package com.monolithic.service.util;

import com.monolithic.config.Constants;
import com.monolithic.domain.User;
import com.monolithic.repository.UserRepository;
import com.monolithic.service.UserService;
import com.monolithic.web.rest.errors.BadRequestAlertException;
import com.monolithic.web.rest.errors.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class LoginUtil {

    private final static Logger logger = LoggerFactory.getLogger(LoginUtil.class);

    /**
     * Get User
     * @param login email or phone
     * @return User
     */
    public static Optional<User> returnUserByLogin(String login, UserService userService) {
        if (login.matches(Constants.LOGIN_REGEX_EMAIL)) {
            return userService.getUserWithAuthoritiesByEmail(login);
        } else if (login.matches(Constants.LOGIN_REGEX_PHONE)) {
            return userService.getUserWithAuthoritiesByPhone(login);
        } else {
            logger.warn("Invalid parameter login : {}", login);
            return Optional.empty();
        }
    }

    /**
     * UserService use this method
     * @param login email or phone
     * @return User
     */
    public static Optional<User> findUserByLogin(String login, UserRepository userRepository) {
        if (login.matches(Constants.LOGIN_REGEX_EMAIL)) {
            return userRepository.findOneWithAuthoritiesByEmail(login);
        } else if (login.matches(Constants.LOGIN_REGEX_PHONE)) {
            return userRepository.findOneWithAuthoritiesByPhone(login);
        } else {
            logger.warn("Invalid parameter login : {}", login);
            return Optional.empty();
        }
    }


    /**
     * Get appId
     * @param login email or phone
     * @return appId
     */
    public static String returnAppIdByLogin(String login, UserService userService) {
        if (login.matches(Constants.LOGIN_REGEX_EMAIL)) {
            return userService.getUserWithAuthoritiesByEmail(login).orElseThrow(UserNotFoundException::new).getAppId();
        } else if (login.matches(Constants.LOGIN_REGEX_PHONE)) {
            return userService.getUserWithAuthoritiesByPhone(login).orElseThrow(UserNotFoundException::new).getAppId();
        } else {
            throw new BadRequestAlertException("Invalid parameter", null, null);
        }
    }

}
