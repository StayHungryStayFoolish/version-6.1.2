package com.monolithic.web.rest;

import com.monolithic.config.ApplicationProperties;
import com.monolithic.config.Constants;
import com.monolithic.service.sms.MultiSms;
import com.monolithic.service.sms.SmsService;
import com.monolithic.service.util.StringConvertUtil;
import com.monolithic.web.rest.errors.BadRequestAlertException;
import com.monolithic.web.rest.utils.RedisUtil;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/security")
public class PublicInfoResource {

    private final Logger log = LoggerFactory.getLogger(PublicInfoResource.class);

    private Long smsDayExpire;
    private Long smsDayLimit;
    private Long smsHourExpire;
    private Long smsHourLimit;
    private Long smsMinuteExpire;
    private Long smsMinuteLimit;

    private final RedisTemplate redisTemplate;
    private final SmsService smsService;
    private final ApplicationProperties properties;

    public PublicInfoResource(RedisTemplate redisTemplate, SmsService smsService, ApplicationProperties properties) {
        this.redisTemplate = redisTemplate;
        this.smsService = smsService;
        this.properties = properties;
        smsDayExpire = properties.getRateLimit().getSmsLimit().getDayExpire();
        smsDayLimit = properties.getRateLimit().getSmsLimit().getDayLimit();
        smsHourExpire = properties.getRateLimit().getSmsLimit().getHourExpire();
        smsHourLimit = properties.getRateLimit().getSmsLimit().getHourLimit();
        smsMinuteExpire = properties.getRateLimit().getSmsLimit().getMinuteExpire();
        smsMinuteLimit = properties.getRateLimit().getSmsLimit().getMinuteLimit();
    }

    /**
     * GET / send sms auth code to user`s phone
     * Open Security
     *
     * @param type  template type
     * @param phone phone
     * @param text  text
     * @return send_sms_success is success, send_sms_failed is failed
     * @throws BadRequestAlertException 400 (Invalid phone number) if phone incorrect will be returned
     * @throws BadRequestAlertException 400 (SMS Template does not exists) if sms template does not exists will be returned
     */
    @GetMapping(value = {"/sms/send/{type}/{phone}", "/sms/send/{type}/{phone}/{text}"})
    @Timed
    public Map<String, Object> sendSms(@PathVariable String type, @PathVariable String phone, @PathVariable(required = false) String text) {
        log.debug("REST request send sms type : {} ,phone : {} ,text : {}", type, phone, text);
        String country = StringConvertUtil.getFrontAtSymbol(phone, "-").replace("+", "");
        // Redis limit
        String dayRedisKey = Constants.SMS_KEY.concat(Constants.DAY_LIMIT).concat(type).concat(phone);
        String hourRedisKey = Constants.SMS_KEY.concat(Constants.HOUR_LIMIT).concat(type).concat(phone);
        String minuteRedisKey = Constants.SMS_KEY.concat(Constants.MINUTE_LIMIT).concat(type).concat(phone);
        Map<String, Object> result = new HashMap<>();

        result.put("dayLimit", smsDayLimit);
        result.put("hourLimit", smsHourLimit);
        result.put("minuteLimit", smsMinuteLimit);

        // Get Redis limit
        Integer dayLimitRedis = (Integer) redisTemplate.opsForValue().get(dayRedisKey);
        if (null != dayLimitRedis && null != smsDayLimit && dayLimitRedis >= smsDayLimit) {
            result.put("expire", redisTemplate.getExpire(dayRedisKey));
            result.put("smsResult", Constants.SEND_SMS_DAY_LIMIT);
            return result;
        }

        Integer hourLimitRedis = (Integer) redisTemplate.opsForValue().get(hourRedisKey);
        if (null != hourLimitRedis && null != smsHourLimit && hourLimitRedis >= smsHourLimit) {
            result.put("expire", redisTemplate.getExpire(hourRedisKey));
            result.put("smsResult", Constants.SEND_SMS_HOUR_LIMIT);
            return result;
        }

        Integer minuteLimitRedis = (Integer) redisTemplate.opsForValue().get(minuteRedisKey);
        if (null != minuteLimitRedis && null != smsMinuteLimit && minuteLimitRedis >= smsMinuteLimit) {
            result.put("expire", redisTemplate.getExpire(minuteRedisKey));
            result.put("smsResult", Constants.SEND_SMS_MINUTE_LIMIT);
            return result;
        }
        MultiSms sms = smsService.getInstance(country);
        String smsResult = null;
        try {
            smsResult = sms.sendMessage(type, phone, text).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        result.put("smsResult", smsResult);
        // Set Redis Increment
        RedisUtil.increment(dayRedisKey, smsDayExpire, redisTemplate);
        RedisUtil.increment(hourRedisKey, smsHourExpire, redisTemplate);
        RedisUtil.increment(minuteRedisKey, smsMinuteExpire, redisTemplate);
        return result;
    }

    /**
     * GET /check sms auth code
     * Open Security
     *
     * @param phone    phone (+86-138********)
     * @param authCode auth code (987654)
     * @return check_success is right ,check_failed is wrong , check_notExists is not exists
     * @throws BadRequestAlertException 400 (Invalid phone number) if phone number invalid will be returned
     */
    @GetMapping(value = "/sms/verify/{type}/{phone}/{authCode}")
    @Timed
    public String verifyAuthCode(@PathVariable String type, @PathVariable String phone, @PathVariable String authCode) {
        log.debug("REST request verify sms code type : {} ,phone : {} ,authCode : {}", type, phone, authCode);
        String country = StringConvertUtil.getFrontAtSymbol(phone, "-").replace("+", "");
        MultiSms sms = smsService.getInstance(country);
        try {
            return sms.verifyCode(type, phone, authCode).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return Constants.CHECK_AUTH_FAILED;
    }
}




