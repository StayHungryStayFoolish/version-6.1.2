package com.monolithic.service.sms;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.monolithic.config.ApplicationProperties;
import com.monolithic.config.Constants;
import com.monolithic.service.util.StringConvertUtil;
import com.monolithic.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ALiSms extends MultiSms {

    private final Logger logger = LoggerFactory.getLogger(ALiSms.class);

    /**
     * ALi Sms Config
     */
    private String product;
    private String domain;
    private String accessKeyId;
    private String accessKeySecret;
    private String signName;

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

    private final RedisTemplate redisplate;
    private final ApplicationProperties properties;

    protected ALiSms(RedisTemplate redisTemplate, ApplicationProperties properties) {
        super(redisTemplate);
        this.redisplate = redisTemplate;
        this.properties = properties;
        product = properties.getSmsConfig().getaLi().getProduct();
        domain = properties.getSmsConfig().getaLi().getDomain();
        accessKeyId = properties.getSmsConfig().getaLi().getAccessKeyId();
        accessKeySecret = properties.getSmsConfig().getaLi().getAccessKeySecret();
        signName = properties.getSmsConfig().getaLi().getSignName();
        registryCN = properties.getSmsConfig().getaLi().getTemplates().getRegistryCN();
        welcomeCN = properties.getSmsConfig().getaLi().getTemplates().getWelcomeCN();
        loginVerifyCN = properties.getSmsConfig().getaLi().getTemplates().getLoginVerifyCN();
        loginNoticeCN = properties.getSmsConfig().getaLi().getTemplates().getLoginNoticeCN();
        bindPhoneCN = properties.getSmsConfig().getaLi().getTemplates().getBindPhoneCN();
        changeOldPhoneCN = properties.getSmsConfig().getaLi().getTemplates().getChangeOldPhoneCN();
        changeNewPhoneCN = properties.getSmsConfig().getaLi().getTemplates().getChangeNewPhoneCN();
        registryEN = properties.getSmsConfig().getaLi().getTemplates().getRegistryEN();
        welcomeEN = properties.getSmsConfig().getaLi().getTemplates().getWelcomeEN();
        loginVerifyEN = properties.getSmsConfig().getaLi().getTemplates().getLoginVerifyEN();
        loginNoticeEN = properties.getSmsConfig().getaLi().getTemplates().getLoginNoticeEN();
        bindPhoneEN = properties.getSmsConfig().getaLi().getTemplates().getBindPhoneEN();
        changeOldPhoneEN = properties.getSmsConfig().getaLi().getTemplates().getChangeOldPhoneEN();
        changeNewPhoneEN = properties.getSmsConfig().getaLi().getTemplates().getChangeNewPhoneEN();
        registryKR = properties.getSmsConfig().getaLi().getTemplates().getRegistryKR();
        welcomeKR = properties.getSmsConfig().getaLi().getTemplates().getWelcomeKR();
        loginVerifyKR = properties.getSmsConfig().getaLi().getTemplates().getLoginVerifyKR();
        loginNoticeKR = properties.getSmsConfig().getaLi().getTemplates().getLoginNoticeKR();
        bindPhoneKR = properties.getSmsConfig().getaLi().getTemplates().getBindPhoneKR();
        changeOldPhoneKR = properties.getSmsConfig().getaLi().getTemplates().getChangeOldPhoneKR();
        changeNewPhoneKR = properties.getSmsConfig().getaLi().getTemplates().getChangeNewPhoneKR();
        registryJP = properties.getSmsConfig().getaLi().getTemplates().getRegistryJP();
        welcomeJP = properties.getSmsConfig().getaLi().getTemplates().getWelcomeJP();
        loginVerifyJP = properties.getSmsConfig().getaLi().getTemplates().getLoginVerifyJP();
        loginNoticeJP = properties.getSmsConfig().getaLi().getTemplates().getLoginNoticeJP();
        bindPhoneJP = properties.getSmsConfig().getaLi().getTemplates().getBindPhoneJP();
        changeOldPhoneJP = properties.getSmsConfig().getaLi().getTemplates().getChangeOldPhoneJP();
        changeNewPhoneJP = properties.getSmsConfig().getaLi().getTemplates().getChangeNewPhoneJP();
    }

    /**
     * Init ALi Sms Client
     * @return IAcsClient
     */
    private IAcsClient initializationClient() {
        logger.debug("Initialization IAcsClient");
        try {
            //可自助调整超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");

            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            return new DefaultAcsClient(profile);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Init SendSmsRequest
     * @param templateCode SMS_1000000
     * @param phone 13812345678
     * @return SendSmsRequest
     */
    private SendSmsRequest initializationRequest(String templateCode, String phone) {
        logger.debug("Initialization SendSmsRequest");
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        return request;
    }

    /**
     * Send SMS based on different message template
     *
     * @param type    sms type
     * @param phone  +86-13812345678
     * @param text text
     * @return send_sms_success / send_sms_try / send_sms_failed / send_sms_limit_minute ...
     */
    @Async
    @Override
    public Future<String> sendMessage(String type, String phone, String text) {
        // +86-13812345678 -> 13812345678
        phone = stripAreaCode(phone);
        String result = sendSmsResult(type, phone, text);
        return AsyncResult.forValue(result);
    }

    @Override
    public Future<String> verifyCode(String type, String phone, String code) {
        return super.verifyCode(type, phone, code);
    }

    /**
     * Send sms by different type
     */
    private String sendSmsResult(String type, String phone, String content) {
        String result = "";
        switch (type) {
            case Constants.SMS_REGISTRY_CN:
                result = sendAuthMsg(Constants.SMS_REGISTRY_CN, registryCN, phone);
                break;
            case Constants.SMS_REGISTRY_EN:
                result = sendAuthMsg(Constants.SMS_REGISTRY_EN, registryEN, phone);
                break;
            case Constants.SMS_REGISTRY_KR:
                result = sendAuthMsg(Constants.SMS_REGISTRY_KR, registryKR, phone);
                break;
            case Constants.SMS_REGISTRY_JP:
                result = sendAuthMsg(Constants.SMS_REGISTRY_JP, registryJP, phone);
                break;
            case Constants.SMS_WELCOME_CN:
                result = sendWelcomeMsg(welcomeCN, phone);
                break;
            case Constants.SMS_WELCOME_EN:
                result = sendWelcomeMsg(welcomeEN, phone);
                break;
            case Constants.SMS_WELCOME_KR:
                result = sendWelcomeMsg(welcomeKR, phone);
                break;
            case Constants.SMS_WELCOME_JP:
                result = sendWelcomeMsg(welcomeJP, phone);
                break;
            case Constants.SMS_LOGIN_VERIFY_CN:
                result = sendAuthMsg(Constants.SMS_LOGIN_VERIFY_CN, loginVerifyCN, phone);
                break;
            case Constants.SMS_LOGIN_VERIFY_EN:
                result = sendAuthMsg(Constants.SMS_LOGIN_VERIFY_EN, loginVerifyEN, phone);
                break;
            case Constants.SMS_LOGIN_VERIFY_KR:
                result = sendAuthMsg(Constants.SMS_LOGIN_VERIFY_KR, loginVerifyKR, phone);
                break;
            case Constants.SMS_LOGIN_VERIFY_JP:
                result = sendAuthMsg(Constants.SMS_LOGIN_VERIFY_JP, loginVerifyJP, phone);
                break;
            case Constants.SMS_LOGIN_NOTICE_CN:
                result = sendLoginNoticeMsg(loginNoticeCN, content, phone);
                break;
            case Constants.SMS_LOGIN_NOTICE_EN:
                result = sendLoginNoticeMsg(loginNoticeEN, content, phone);
                break;
            case Constants.SMS_LOGIN_NOTICE_KR:
                result = sendLoginNoticeMsg(loginNoticeKR, content, phone);
                break;
            case Constants.SMS_LOGIN_NOTICE_JP:
                result = sendLoginNoticeMsg(loginNoticeJP, content, phone);
                break;
            case Constants.SMS_BIND_PHONE_CN:
                result = sendAuthMsg(Constants.SMS_BIND_PHONE_CN, bindPhoneCN, phone);
                break;
            case Constants.SMS_BIND_PHONE_EN:
                result = sendAuthMsg(Constants.SMS_BIND_PHONE_EN, bindPhoneEN, phone);
                break;
            case Constants.SMS_BIND_PHONE_KR:
                result = sendAuthMsg(Constants.SMS_BIND_PHONE_KR, bindPhoneKR, phone);
                break;
            case Constants.SMS_BIND_PHONE_JP:
                result = sendAuthMsg(Constants.SMS_BIND_PHONE_JP, bindPhoneJP, phone);
                break;
            case Constants.SMS_CHANGE_OLD_PHONE_CN:
                result = sendAuthMsg(Constants.SMS_CHANGE_OLD_PHONE_CN, changeOldPhoneCN, phone);
                break;
            case Constants.SMS_CHANGE_OLD_PHONE_EN:
                result = sendAuthMsg(Constants.SMS_CHANGE_OLD_PHONE_EN, changeOldPhoneEN, phone);
                break;
            case Constants.SMS_CHANGE_OLD_PHONE_KR:
                result = sendAuthMsg(Constants.SMS_CHANGE_OLD_PHONE_KR, changeOldPhoneKR, phone);
                break;
            case Constants.SMS_CHANGE_OLD_PHONE_JP:
                result = sendAuthMsg(Constants.SMS_CHANGE_OLD_PHONE_JP, changeOldPhoneJP, phone);
                break;
            case Constants.SMS_CHANGE_NEW_PHONE_CN:
                result = sendAuthMsg(Constants.SMS_CHANGE_NEW_PHONE_CN, changeNewPhoneCN, phone);
                break;
            case Constants.SMS_CHANGE_NEW_PHONE_EN:
                result = sendAuthMsg(Constants.SMS_CHANGE_NEW_PHONE_EN, changeNewPhoneEN, phone);
                break;
            case Constants.SMS_CHANGE_NEW_PHONE_KR:
                result = sendAuthMsg(Constants.SMS_CHANGE_NEW_PHONE_KR, changeNewPhoneKR, phone);
                break;
            case Constants.SMS_CHANGE_NEW_PHONE_JP:
                result = sendAuthMsg(Constants.SMS_CHANGE_NEW_PHONE_JP, changeNewPhoneJP, phone);
                break;
            default:
                throw new BadRequestAlertException("SMS plate does not exists", null, null);
        }
        return result;
    }

    /**
     * Send Welcome Msg
     */
    private String sendWelcomeMsg(String templateCode, String phone) {
        logger.debug("Sending welcome sms templateCode {} ,login: {} ,phone : {}", templateCode, phone);
        String result = sendWelcome(templateCode, phone, phone);
        return getSendSmsRequestStatus(result);
    }

    /**
     * Send Login Notice Msg
     */
    private String sendLoginNoticeMsg(String templateCode, String login, String phone) {
        logger.debug("Sending sms templateCode {} ,login: {} ,phone : {}", templateCode, login, phone);
        String result = sendLoginNotice(templateCode, login, phone);
        return getSendSmsRequestStatus(result);
    }

    /**
     * Send Auth Code Msg
     */
    @SuppressWarnings("unchecked")
    private String sendAuthMsg(String type, String templateCode, String phone) {
        logger.debug("Send sms auth type : {} ,templateCode : {} ,phone : {}", type, templateCode, phone);
        String authCode = getAuthCode();
        String result = sendAuth(type, authCode, phone);
        return getSendSmsRequestStatusByRedis(type, result, phone, authCode);
    }

    private String getSendSmsRequestStatus(String result) {
        if (Constants.SEND_SMS_FAILED.equalsIgnoreCase(result)) {
            result = Constants.SEND_SMS_FAILED;
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private String getSendSmsRequestStatusByRedis(String type, String smsResult, String phone, String authCode) {
        String result = "";
        switch (smsResult){
            case Constants.SEND_SMS_SUCCESS:
                // regCN -> reg
                type = StringConvertUtil.trimAtEndIndex(type, 2);
                // concat "+86" is same with other services (twilio , nexmo )
                redisTemplate.opsForValue().set(Constants.SMS_CODE.concat(type).concat("+86").concat(phone), authCode, 300, TimeUnit.SECONDS);
                logger.debug("ALiYun sms auth code : {}", authCode);
                result = Constants.SEND_SMS_SUCCESS;
                break;
            case Constants.SEND_SMS_MINUTE_LIMIT:
                result = Constants.SEND_SMS_MINUTE_LIMIT;
                break;
            case Constants.SEND_SMS_HOUR_LIMIT:
                result = Constants.SEND_SMS_HOUR_LIMIT;
                break;
            case Constants.SEND_SMS_DAY_LIMIT:
                result = Constants.SEND_SMS_DAY_LIMIT;
                break;
            default:
                result = Constants.SEND_SMS_FAILED;
        }
        return result;
    }

    private String sendWelcome(String templateCode, String login, String mobile) {
        SendSmsRequest request = welcomeRequest(templateCode, login, mobile);
        return sendMessageRequest(request);
    }

    private String sendLoginNotice(String templateCode, String login, String mobile) {
        SendSmsRequest request = loginNoticeRequest(templateCode, login, mobile);
        return sendMessageRequest(request);
    }

    private String sendAuth(String templateCode, String content, String mobile) {
        SendSmsRequest request = authRequest(templateCode, content, mobile);
        return sendMessageRequest(request);
    }


    private SendSmsRequest welcomeRequest(String templateCode, String authCode, String mobile) {
        SendSmsRequest request = initializationRequest(templateCode, mobile);
        // 尊敬的 ${name} 用户，您的账户已创建，如有疑问请随时和我们联系。祝您使用愉快。
        request.setTemplateParam("{name:" + authCode + "}");
        return request;
    }

    private SendSmsRequest loginNoticeRequest(String templateCode, String login, String mobile) {
        SendSmsRequest request = initializationRequest(templateCode, mobile);
        // 尊敬的 ${name} 用户：您于 ${time} 已经成功登陆，如若不是本人，请您尽快修改密码，保证账户安全。
        JSONObject temParam = new JSONObject();
        temParam.put("name", login);
        temParam.put("time", currentTimeFormat());
        request.setTemplateParam(temParam.toJSONString());
        return request;
    }

    private SendSmsRequest authRequest(String templateCode, String authCode, String mobile) {
        SendSmsRequest request = initializationRequest(templateCode, mobile);
        // 您当前正在重置登陆密码，验证码：${code}。5分钟内有效，请勿告诉他人。
        request.setTemplateParam("{code:" + authCode + "}");
        return request;
    }

    private String sendMessageRequest(SendSmsRequest request) {
        logger.info("ALiYun send message");
        try {
            IAcsClient acsClient = initializationClient();
            if (null != acsClient && null != request) {
                SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
                if (null != sendSmsResponse.getBizId() &&
                    null != sendSmsResponse.getCode() &&
                    "OK".equals(sendSmsResponse.getCode())) {
                    logger.info("ALiYun send message success ,the requestId is : {}", sendSmsResponse.getRequestId());
                    return Constants.SEND_SMS_SUCCESS;
                } else if (Constants.SEND_SMS_ALIYUN_LIMIT_CODE.equalsIgnoreCase(sendSmsResponse.getCode())
                    &&
                    Constants.SEND_SMS_ALIYUN_MINUTE_LIMIT_MESSAGE.equalsIgnoreCase(sendSmsResponse.getMessage())) {
                    // 触发分钟级流控Permits:1
                    logger.warn("ALiYun send message limit ,minute limit message : {}", sendSmsResponse.getMessage());
                    return Constants.SEND_SMS_MINUTE_LIMIT;
                } else if (Constants.SEND_SMS_ALIYUN_LIMIT_CODE.equalsIgnoreCase(sendSmsResponse.getCode())
                    &&
                    Constants.SEND_SMS_ALIYUN_HOUR_LIMIT_MESSAGE.equalsIgnoreCase(sendSmsResponse.getMessage())) {
                    // 触发小时级流控Permits:5
                    logger.warn("ALiYun send message limit ,hour limit message : {}", sendSmsResponse.getMessage());
                    return Constants.SEND_SMS_HOUR_LIMIT;
                } else if (Constants.SEND_SMS_ALIYUN_LIMIT_CODE.equalsIgnoreCase(sendSmsResponse.getCode())
                    &&
                    Constants.SEND_SMS_ALIYUN_DAY_LIMIT_MESSAGE.equalsIgnoreCase(sendSmsResponse.getMessage())) {
                    // 触发天级流控Permits:10
                    logger.warn("ALiYun send message limit ,day limit message : {}", sendSmsResponse.getMessage());
                    return Constants.SEND_SMS_DAY_LIMIT;
                } else {
                    logger.warn("ALiYun send message failed ,failed code : {}", sendSmsResponse.getCode());
                    return Constants.SEND_SMS_FAILED;
                }
            }
        } catch (ClientException e) {
            if (logger.isDebugEnabled()) {
                logger.warn("Message could not be sent to user '{}'", e);
            } else {
                logger.warn("Message could not be sent to user '{}' ", e.getMessage());
            }
        }
        return null;
    }



    public static String stripAreaCode(String phone) {
        phone = phone.trim();
        if (phone.matches(Constants.LOGIN_REGEX_PHONE)) {
            phone = phone.replaceAll("-", "");
            phone = StringConvertUtil.getExtension(phone, 3);
            return phone;
        }
        return phone;
    }
}
