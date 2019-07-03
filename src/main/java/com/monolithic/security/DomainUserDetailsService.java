package com.monolithic.security;

import com.monolithic.domain.User;
import com.monolithic.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;

    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);

        Optional<User> userByEmailFromDatabase = userRepository.findOneWithAuthoritiesByEmail(login.toLowerCase(Locale.ENGLISH));
        return userByEmailFromDatabase.map(user -> createSpringSecurityUser(login, user)).orElseGet(() -> {
            Optional<User> userByLoginFromDatabase = userRepository.findOneWithAuthoritiesByPhone(login);
            return userByLoginFromDatabase.map(user -> createSpringSecurityUser(login, user)).orElseGet(() -> {
                Optional<User> userByInkIdFromDatabase = Optional.empty();
                if (StringUtils.isNoneBlank(login)) {
                    userByInkIdFromDatabase = userRepository.findOneWithAuthoritiesByAppId(login);
                }
                return userByInkIdFromDatabase.map(user -> createSpringSecurityUser(login, user)).orElseThrow(() ->
                    new UsernameNotFoundException("User " + login + " was not found in the " +
                        "database"));
            });
        });
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, User user) {
        if (!user.getActivated()) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getName()))
            .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getAppId(),
            user.getPassword(),
            grantedAuthorities);
    }
}
