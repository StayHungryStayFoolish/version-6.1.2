package com.monolithic.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable login (must use non-capturing).

    public static final String LOGIN_REGEX_EMAIL = "(?:\\w+[\\w-_.\\+]*)@(?:\\w+[\\w_-]*)(?:\\.\\w+[\\w_-]*)+";
    public static final String LOGIN_REGEX_PHONE = "(?:^\\+\\d{2,5}-\\d+)";
    
    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "zh-CN";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String ROLE_FREEZE = "ROLE_FREEZE";

    public static final String SMS_PROVIDER_ALI = "aLi";
    public static final String SMS_PROVIDER_TWILIO = "twilio";
    // Verify Code From Redis
    public static final String CHECK_AUTH_SUCCESS = "checkSuccess";
    public static final String CHECK_AUTH_FAILED = "checkFailed";
    public static final String CHECK_NOT_EXISTS = "checkNotExists";

    // Redis key
    public static final String USER_CACHE = "USER:";
    public static final String SMS_CODE = "AUTH_SMS_CODE:";
    public static final String USER_SECURE_STATUS = "USER_SECURE_STATUS:";
    // Login Limit Level Redis Key
    public static final String LOGIN_KEY = "LOGIN_";
    public static final String SMS_KEY = "SMS_";
    public static final String EMAIL_KEY = "EMAIL_";
    public static final String MINUTE_LIMIT = "MINUTE_LIMIT:";
    public static final String HOUR_LIMIT = "HOUR_LIMIT:";
    public static final String DAY_LIMIT = "DAY_LIMIT:";
    // Email Limit Level
    public static final String SEND_EMAIL_SUCCESS = "emailSuccess";
    public static final String SEND_EMAIL_MINUTE_LIMIT = "emailMinuteLimit";
    public static final String SEND_EMAIL_HOUR_LIMIT = "emailHourLimit";
    public static final String SEND_EMAIL_DAY_LIMIT = "emailDayLimit";

    // SMS Limit Level
    public static final String SEND_SMS_SUCCESS = "smsSuccess";
    public static final String SEND_SMS_TRY = "smsTry";
    public static final String SEND_SMS_FAILED = "smsFailed";
    public static final String SEND_SMS_ALIYUN_LIMIT_CODE = "isv.BUSINESS_LIMIT_CONTROL";
    public static final String SEND_SMS_ALIYUN_MINUTE_LIMIT_MESSAGE = "触发小时级流控Permits:1";
    public static final String SEND_SMS_ALIYUN_HOUR_LIMIT_MESSAGE = "触发小时级流控Permits:5";
    public static final String SEND_SMS_ALIYUN_DAY_LIMIT_MESSAGE = "触发天级流控Permits:10";
    public static final String SEND_SMS_MINUTE_LIMIT = "smsMinuteLimit";
    public static final String SEND_SMS_HOUR_LIMIT = "smsHourLimit";
    public static final String SEND_SMS_DAY_LIMIT = "smsDayLimit";

    /**
     * Sms Template Type
     */
    public static final String SMS_REGISTRY_CN = "registryCN";
    public static final String SMS_REGISTRY_EN = "registryEN";
    public static final String SMS_REGISTRY_KR = "registryKR";
    public static final String SMS_REGISTRY_JP = "registryJP";
    public static final String SMS_WELCOME_CN = "welcomeCN";
    public static final String SMS_WELCOME_EN = "welcomeEN";
    public static final String SMS_WELCOME_KR = "welcomeKR";
    public static final String SMS_WELCOME_JP = "welcomeJP";
    public static final String SMS_LOGIN_VERIFY_CN = "loginVerifyCN";
    public static final String SMS_LOGIN_VERIFY_EN = "loginVerifyEN";
    public static final String SMS_LOGIN_VERIFY_KR = "loginVerifyKR";
    public static final String SMS_LOGIN_VERIFY_JP = "loginVerifyJP";
    public static final String SMS_LOGIN_NOTICE_CN = "loginNoticeCN";
    public static final String SMS_LOGIN_NOTICE_EN = "loginNoticeEN";
    public static final String SMS_LOGIN_NOTICE_KR = "loginNoticeKR";
    public static final String SMS_LOGIN_NOTICE_JP = "loginNoticeJP";
    public static final String SMS_BIND_PHONE_CN = "bindPhoneCN";
    public static final String SMS_BIND_PHONE_EN = "bindPhoneEN";
    public static final String SMS_BIND_PHONE_KR = "bindPhoneKR";
    public static final String SMS_BIND_PHONE_JP = "bindPhoneJP";
    public static final String SMS_CHANGE_OLD_PHONE_CN = "changeOldPhoneCN";
    public static final String SMS_CHANGE_OLD_PHONE_EN = "changeOldPhoneEN";
    public static final String SMS_CHANGE_OLD_PHONE_KR = "changeOldPhoneKR";
    public static final String SMS_CHANGE_OLD_PHONE_JP = "changeOldPhoneJP";
    public static final String SMS_CHANGE_NEW_PHONE_CN = "changeNewPhoneCN";
    public static final String SMS_CHANGE_NEW_PHONE_EN = "changeNewPhoneEN";
    public static final String SMS_CHANGE_NEW_PHONE_KR = "changeNewPhoneKR";
    public static final String SMS_CHANGE_NEW_PHONE_JP = "changeNewPhoneJP";

    private Constants() {
    }
}
