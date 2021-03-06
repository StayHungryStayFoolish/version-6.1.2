package com.monolithic.web.rest.utils;

import com.monolithic.domain.User;
import com.monolithic.service.dto.UserDTO;
import com.monolithic.web.rest.vm.EmailUserVM;
import org.apache.commons.lang3.StringUtils;

public class EmailUtil {

    public static EmailUserVM transferEmailUser(User user) {
        EmailUserVM emailUser = new EmailUserVM();
        if (StringUtils.isNoneBlank(user.getEmail())) {
            emailUser.setEmail(user.getEmail());
        }
        if (StringUtils.isNoneBlank(user.getPhone())) {
            emailUser.setPhone(user.getPhone());
        }
        if (StringUtils.isNotBlank(user.getActivationKey())) {
            emailUser.setActivationKey(user.getActivationKey());
        }
        if (StringUtils.isNoneBlank(user.getResetKey())) {
            emailUser.setResetKey(user.getResetKey());
        }
        emailUser.setLangKey(user.getLangKey());
        return emailUser;
    }

    public static EmailUserVM transferEmailUserDTO(UserDTO user) {
        EmailUserVM emailUser = new EmailUserVM();
        if (StringUtils.isNoneBlank(user.getEmail())) {
            emailUser.setEmail(user.getEmail());
        }
        if (StringUtils.isNoneBlank(user.getPhone())) {
            emailUser.setPhone(user.getPhone());
        }
        emailUser.setLangKey(user.getLangKey());
        return emailUser;
    }
}
