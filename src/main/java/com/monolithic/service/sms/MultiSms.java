package com.monolithic.service.sms;

import com.monolithic.config.Constants;
import com.monolithic.service.util.StringConvertUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.AsyncResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Future;

public abstract class MultiSms {

    private final Logger logger = LoggerFactory.getLogger(MultiSms.class);

    public final RedisTemplate redisTemplate;

    protected MultiSms(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Send SMS based on different message template
     *
     * @param type    sms type
     * @param phone  phone
     * @param text text
     * @return send_sms_success / send_sms_try / send_sms_failed / send_sms_limit_minute ...
     */
    public abstract Future<String> sendMessage(String type, String phone, String text);

    public Future<String> verifyCode(String type, String phone, String code) {
        logger.debug("VerifyCode type : {} ,phone : {} ,code : {}", type, phone, code);
        type = StringConvertUtil.trimAtEndIndex(type, 2);
        if (phone.matches(Constants.LOGIN_REGEX_PHONE)) {
            phone = phone.replace("-", "");
        }
        String smsRedisKey = Constants.SMS_CODE.concat(type).concat(phone);
        String smsRedisValue = (String) redisTemplate.opsForValue().get(smsRedisKey);
        if (StringUtils.isNoneBlank(smsRedisValue)) {
            if (code.equals(smsRedisValue)) {
                // Open one-time verification
                //redisTemplate.delete(smsAuthKey);
                return AsyncResult.forValue(Constants.CHECK_AUTH_SUCCESS);
            } else {
                return AsyncResult.forValue(Constants.CHECK_AUTH_FAILED);
            }
        } else {
            return AsyncResult.forValue(Constants.CHECK_NOT_EXISTS);
        }
    }

    public static String getAuthCode() {
        return String.valueOf((new Random().nextInt(899999) + 100000));
    }

    /**
     * @return 2019-07-03 11:33:22
     */
    public static String currentTimeFormat() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
