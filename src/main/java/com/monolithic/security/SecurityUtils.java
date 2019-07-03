package com.monolithic.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.monolithic.config.Constants;
import com.monolithic.service.UserService;
import com.monolithic.service.dto.UserDTO;
import com.monolithic.web.rest.errors.UserFreezeException;
import com.monolithic.web.rest.vm.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.Set;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

    private static UserService userService;

    private static RedisTemplate redisTemplate;

    @Autowired
    public void setUserService(UserService userService) {
        SecurityUtils.userService = userService;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        SecurityUtils.redisTemplate = redisTemplate;
    }

    private SecurityUtils() {
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user.
     */
    public static Optional<String> getCurrentUserLogin() {
        /*
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .map(authentication -> {
                if (authentication.getPrincipal() instanceof UserDetails) {
                    UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                    return springSecurityUser.getUsername();
                } else if (authentication.getPrincipal() instanceof String) {
                    return (String) authentication.getPrincipal();
                }
                return null;
            });
        */
        SecurityContext securityContext = SecurityContextHolder.getContext();

        // judge the current user is freeze, throw 410
        Authentication contextAuthentication = securityContext.getAuthentication();
        if (null != contextAuthentication && contextAuthentication.isAuthenticated()) {
            if (contextAuthentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) contextAuthentication.getPrincipal();
                if (null == springSecurityUser) {
                    return Optional.empty();
                }
            } else if (contextAuthentication.getPrincipal() instanceof String) {
                String principal = (String) contextAuthentication.getPrincipal();
                UserVM userCache = (UserVM) redisTemplate.opsForValue().get(Constants.USER_SECURE_STATUS.concat(principal));
                Optional<UserDTO> user;
                if (null == userCache) {
                    user = userService.getUserWithAuthoritiesByAppId(principal).map(UserDTO::new);
                    if (user.isPresent()) {
                        UserDTO dto = user.get();
                        String userJson = JSON.toJSONString(dto);
                        JSONObject jsonObject = JSONObject.parseObject(userJson);
                        UserVM userVM = JSONObject.toJavaObject(jsonObject, UserVM.class);
                        redisTemplate.opsForValue().set(Constants.USER_SECURE_STATUS.concat(principal), userVM);
                    }
                }
                if (null != userCache) {
                    Set<String> authorities = userCache.getAuthorities();
                    if (0 != authorities.size()) {
                        for (String authority : authorities) {
                            if (authority.equalsIgnoreCase(Constants.ROLE_FREEZE)) {
                                securityContext.setAuthentication(null);
                                SecurityContextHolder.clearContext();
                                throw new UserFreezeException();
                            }
                        }
                    }
                }
            }
        }

        return Optional.ofNullable(securityContext.getAuthentication())
            .map(authentication -> {
                if (authentication.getPrincipal() instanceof UserDetails) {
                    UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                    return springSecurityUser.getUsername();
                } else if (authentication.getPrincipal() instanceof String) {
                    return (String) authentication.getPrincipal();
                }
                return null;
            });
    }

    /**
     * Get the JWT of the current user.
     *
     * @return the JWT of the current user.
     */
    public static Optional<String> getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .filter(authentication -> authentication.getCredentials() instanceof String)
            .map(authentication -> (String) authentication.getCredentials());
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise.
     */
    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .map(authentication -> authentication.getAuthorities().stream()
                .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(AuthoritiesConstants.ANONYMOUS)))
            .orElse(false);
    }

    /**
     * If the current user has a specific authority (security role).
     * <p>
     * The name of this method comes from the {@code isUserInRole()} method in the Servlet API.
     *
     * @param authority the authority to check.
     * @return true if the current user has the authority, false otherwise.
     */
    public static boolean isCurrentUserInRole(String authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .map(authentication -> authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority)))
            .orElse(false);
    }
}
