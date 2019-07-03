package com.monolithic.service.sms;

import com.monolithic.config.ApplicationProperties;
import com.monolithic.config.Constants;
import com.monolithic.service.util.StringConvertUtil;
import com.monolithic.web.rest.errors.BadRequestAlertException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TwilioSms extends MultiSms {

    private final Logger logger = LoggerFactory.getLogger(TwilioSms.class);

    /**
     * Twillo Config
     */
    private String accountId;
    private String authToken;
    private String fromNumber;

    // CN
    private String registryCN;
    private String welcomeCN;
    private String loginVerifyCN;
    private String loginNoticeCN;
    private String bindPhoneCN;
    private String changeOldPhoneCN;
    private String changeNewPhoneCN;
    // EN
    private String registryEN;
    private String welcomeEN;
    private String loginVerifyEN;
    private String loginNoticeEN;
    private String bindPhoneEN;
    private String changeOldPhoneEN;
    private String changeNewPhoneEN;
    // KR
    private String registryKR;
    private String welcomeKR;
    private String loginVerifyKR;
    private String loginNoticeKR;
    private String bindPhoneKR;
    private String changeOldPhoneKR;
    private String changeNewPhoneKR;
    // JP
    private String registryJP;
    private String welcomeJP;
    private String loginVerifyJP;
    private String loginNoticeJP;
    private String bindPhoneJP;
    private String changeOldPhoneJP;
    private String changeNewPhoneJP;

    private final RedisTemplate redisTemplate;

    private final ApplicationProperties properties;

    protected TwilioSms(RedisTemplate redisTemplate, ApplicationProperties properties) {
        super(redisTemplate);
        this.redisTemplate = redisTemplate;
        this.properties = properties;
        accountId = properties.getSmsConfig().getTwilio().getAccountId();
        authToken = properties.getSmsConfig().getTwilio().getAuthToken();
        fromNumber = properties.getSmsConfig().getTwilio().getFromNumber();
        registryCN = properties.getSmsConfig().getCommonTemplates().getRegistryCN();
        welcomeCN = properties.getSmsConfig().getCommonTemplates().getWelcomeCN();
        loginVerifyCN = properties.getSmsConfig().getCommonTemplates().getLoginVerifyCN();
        loginNoticeCN = properties.getSmsConfig().getCommonTemplates().getLoginNoticeCN();
        bindPhoneCN = properties.getSmsConfig().getCommonTemplates().getBindPhoneCN();
        changeOldPhoneCN = properties.getSmsConfig().getCommonTemplates().getChangeOldPhoneCN();
        changeNewPhoneCN = properties.getSmsConfig().getCommonTemplates().getChangeNewPhoneCN();
        registryEN = properties.getSmsConfig().getCommonTemplates().getRegistryEN();
        welcomeEN = properties.getSmsConfig().getCommonTemplates().getWelcomeEN();
        loginVerifyEN = properties.getSmsConfig().getCommonTemplates().getLoginVerifyEN();
        loginNoticeEN = properties.getSmsConfig().getCommonTemplates().getLoginNoticeEN();
        bindPhoneEN = properties.getSmsConfig().getCommonTemplates().getBindPhoneEN();
        changeOldPhoneEN = properties.getSmsConfig().getCommonTemplates().getChangeOldPhoneEN();
        changeNewPhoneEN = properties.getSmsConfig().getCommonTemplates().getChangeNewPhoneEN();
        registryKR = properties.getSmsConfig().getCommonTemplates().getRegistryKR();
        welcomeKR = properties.getSmsConfig().getCommonTemplates().getWelcomeKR();
        loginVerifyKR = properties.getSmsConfig().getCommonTemplates().getLoginVerifyKR();
        loginNoticeKR = properties.getSmsConfig().getCommonTemplates().getLoginNoticeKR();
        bindPhoneKR = properties.getSmsConfig().getCommonTemplates().getBindPhoneKR();
        changeOldPhoneKR = properties.getSmsConfig().getCommonTemplates().getChangeOldPhoneKR();
        changeNewPhoneKR = properties.getSmsConfig().getCommonTemplates().getChangeNewPhoneKR();
        registryJP = properties.getSmsConfig().getCommonTemplates().getRegistryJP();
        welcomeJP = properties.getSmsConfig().getCommonTemplates().getWelcomeJP();
        loginVerifyJP = properties.getSmsConfig().getCommonTemplates().getLoginVerifyJP();
        loginNoticeJP = properties.getSmsConfig().getCommonTemplates().getLoginNoticeJP();
        bindPhoneJP = properties.getSmsConfig().getCommonTemplates().getBindPhoneJP();
        changeOldPhoneJP = properties.getSmsConfig().getCommonTemplates().getChangeOldPhoneJP();
        changeNewPhoneJP = properties.getSmsConfig().getCommonTemplates().getChangeNewPhoneJP();
    }

    /**
     * Send SMS based on different message template
     *
     * @param type  sms type
     * @param phone phone +8613811119999
     * @param text  text
     * @return send_sms_success / send_sms_try / send_sms_failed / send_sms_limit_minute ...
     */
    @Async
    @Override
    public Future<String> sendMessage(String type, String phone, String text) {

        logger.debug("Twilio send sms to type : {} ,phone : {} ,text : {}", type, phone, text);
        if (phone.contains("-")) {
            phone = phone.replace("-", "");
        }
        phone = phone.trim();
        accountId = accountId.trim();
        authToken = authToken.trim();
        String fromNum = fromNumber;
        if (!fromNumber.startsWith("+")) {
            fromNum = "+".concat(fromNumber).trim();
        }

        Twilio.init(accountId, authToken);
        // to +8618883387006  // from +12176002317
        text = replaceText(type, phone, text);
        Message message = null;
        try {
            message = Message.creator(
                new PhoneNumber(phone),
                new PhoneNumber(fromNum),
                text)
                .create();
        } catch (Exception e) {
            logger.error("Twilio Send Sms Failed : {}", e.toString());
            return AsyncResult.forValue(Constants.SEND_SMS_TRY);
        }

        logger.debug("Twilio send sms status : {}", message.getStatus());
        String status = message.getStatus().name();
        if ("accepted".equalsIgnoreCase(status) || "queued".equalsIgnoreCase(status) || "sending".equalsIgnoreCase(status)
            || "sent".equalsIgnoreCase(status) || "receiving".equalsIgnoreCase(status) || "received".equalsIgnoreCase(status)
            || "delivered".equalsIgnoreCase(status)
        ) {
            return AsyncResult.forValue(Constants.SEND_SMS_SUCCESS);
        } else if ("undelivered".equalsIgnoreCase(status) || "failed".equalsIgnoreCase(status)) {
            return AsyncResult.forValue(Constants.SEND_SMS_TRY);
        }
        return AsyncResult.forValue(Constants.SEND_SMS_FAILED);
    }

    @Override
    public Future<String> verifyCode(String type, String phone, String code) {
        return super.verifyCode(type, phone, code);
    }

    private String replaceText(String type, String phone, String text) {
        // +86-13812345678 -> 13812345678
        if (phone.matches(Constants.LOGIN_REGEX_PHONE)) {
            phone = phone.replaceAll("-", "");
        }
        String result = "";
        switch (type) {
            case Constants.SMS_LOGIN_VERIFY_CN:
                result = String.format(loginVerifyCN, setAuthCodeByRedis(type, phone));
                break;
            case Constants.SMS_LOGIN_VERIFY_EN:
                result = String.format(loginVerifyEN, setAuthCodeByRedis(type, phone));
                break;
            case Constants.SMS_LOGIN_VERIFY_KR:
                result = String.format(loginVerifyKR, setAuthCodeByRedis(type, phone));
                break;
            case Constants.SMS_LOGIN_VERIFY_JP:
                result = String.format(loginVerifyJP, setAuthCodeByRedis(type, phone));
                break;
            case Constants.SMS_WELCOME_CN:
                result = String.format(welcomeCN, phone);
                break;
            case Constants.SMS_WELCOME_EN:
                result = String.format(welcomeEN, phone);
                break;
            case Constants.SMS_WELCOME_KR:
                result = String.format(welcomeKR, phone);
                break;
            case Constants.SMS_WELCOME_JP:
                result = String.format(welcomeJP, phone);
                break;
            case Constants.SMS_LOGIN_NOTICE_CN:
                result = String.format(loginNoticeCN, text, currentTimeFormat());
                break;
            case Constants.SMS_LOGIN_NOTICE_EN:
                result = String.format(loginNoticeEN, text, currentTimeFormat());
                break;
            case Constants.SMS_LOGIN_NOTICE_KR:
                result = String.format(loginNoticeKR, text, currentTimeFormat());
                break;
            case Constants.SMS_LOGIN_NOTICE_JP:
                result = String.format(loginNoticeJP, text, currentTimeFormat());
                break;
            case Constants.SMS_REGISTRY_CN:
                result = String.format(registryCN, setAuthCodeByRedis(type, phone));
                break;
            case Constants.SMS_REGISTRY_EN:
                result = String.format(registryEN, setAuthCodeByRedis(type, phone));
                break;
            case Constants.SMS_REGISTRY_KR:
                result = String.format(registryKR, setAuthCodeByRedis(type, phone));
                break;
            case Constants.SMS_REGISTRY_JP:
                result = String.format(registryJP, setAuthCodeByRedis(type, phone));
                break;
            case Constants.SMS_BIND_PHONE_CN:
                result = String.format(bindPhoneCN, setAuthCodeByRedis(type, phone));
                break;
            case Constants.SMS_BIND_PHONE_EN:
                result = String.format(bindPhoneEN, setAuthCodeByRedis(type, phone));
                break;
            case Constants.SMS_BIND_PHONE_KR:
                result = String.format(bindPhoneKR, setAuthCodeByRedis(type, phone));
                break;
            case Constants.SMS_BIND_PHONE_JP:
                result = String.format(bindPhoneJP, setAuthCodeByRedis(type, phone));
                break;
            case Constants.SMS_CHANGE_OLD_PHONE_CN:
                result = String.format(changeOldPhoneCN, setAuthCodeByRedis(type, phone));
                break;
            case Constants.SMS_CHANGE_OLD_PHONE_EN:
                result = String.format(changeOldPhoneEN, setAuthCodeByRedis(type, phone));
                break;
            case Constants.SMS_CHANGE_OLD_PHONE_KR:
                result = String.format(changeOldPhoneKR, setAuthCodeByRedis(type, phone));
                break;
            case Constants.SMS_CHANGE_OLD_PHONE_JP:
                result = String.format(changeOldPhoneJP, setAuthCodeByRedis(type, phone));
                break;
            case Constants.SMS_CHANGE_NEW_PHONE_CN:
                result = String.format(changeNewPhoneCN, setAuthCodeByRedis(type, phone));
                break;
            case Constants.SMS_CHANGE_NEW_PHONE_EN:
                result = String.format(changeNewPhoneEN, setAuthCodeByRedis(type, phone));
                break;
            case Constants.SMS_CHANGE_NEW_PHONE_KR:
                result = String.format(changeNewPhoneKR, setAuthCodeByRedis(type, phone));
                break;
            case Constants.SMS_CHANGE_NEW_PHONE_JP:
                result = String.format(changeNewPhoneJP, setAuthCodeByRedis(type, phone));
                break;
            default:
                throw new BadRequestAlertException("SMS Template does not exists", null, null);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private String setAuthCodeByRedis(String type, String phone) {
        // registryCN -> registry
        type = StringConvertUtil.trimAtEndIndex(type, 2);
        String authCode = getAuthCode();
        String authKey = Constants.SMS_CODE.concat(type).concat(phone);
        redisTemplate.opsForValue().set(authKey, authCode, 300, TimeUnit.SECONDS);
        return authCode;
    }
}
