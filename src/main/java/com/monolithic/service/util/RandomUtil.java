package com.monolithic.service.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

/**
 * Utility class for generating random Strings.
 */
public final class RandomUtil {

    private static final int DEF_COUNT = 20;

    private RandomUtil() {
    }

    /**
     * Generate a password.
     *
     * @return the generated password.
     */
    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(DEF_COUNT);
    }

    /**
     * Generate an activation key.
     *
     * @return the generated activation key.
     */
    public static String generateActivationKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }

    /**
     * Generate a reset key.
     *
     * @return the generated reset key.
     */
    public static String generateResetKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }

    public static String generateAppId() {
        String appId = "appId_";
        String uuid = UUID.randomUUID().toString().replace("-","").toLowerCase();
        return appId.concat(uuid);
    }
}
