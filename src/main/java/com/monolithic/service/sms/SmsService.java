package com.monolithic.service.sms;

import com.monolithic.config.ApplicationProperties;
import com.monolithic.config.Constants;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private final ApplicationProperties properties;

    private String defaultProvider;

    private String[] aLiYunCountryCode;
    private String[] twilioCountryCode;

    private ALiSms aLiYunSMS = null;

    private TwilioSms twilioSMS = null;

    private final RedisTemplate redisTemplate;

    public SmsService(ApplicationProperties properties, RedisTemplate redisTemplate) {
        this.properties = properties;
        defaultProvider = properties.getSmsConfig().getDefaultProvider();
        aLiYunCountryCode = properties.getSmsConfig().getaLi().getCountryCode();
        twilioCountryCode = properties.getSmsConfig().getTwilio().getCountryCode();
        this.redisTemplate = redisTemplate;
    }

    //
    public MultiSms getInstance(String country) {
        return getMultiSMS(country);
    }

    private MultiSms getMultiSMS(String country) {
        if (0 != aLiYunCountryCode.length && ArrayUtils.contains(aLiYunCountryCode, country)) {
            if (null == this.aLiYunSMS) {
                aLiYunSMS = new ALiSms(redisTemplate, properties);
            }
            return aLiYunSMS;
        } else if (0 != twilioCountryCode.length && ArrayUtils.contains(twilioCountryCode, country)) {
            if (null == this.twilioSMS) {
                twilioSMS = new TwilioSms(redisTemplate, properties);
            }
            return twilioSMS;
        }  else {
            if (StringUtils.isNoneBlank(defaultProvider) && defaultProvider.equalsIgnoreCase(Constants.SMS_PROVIDER_ALI)) {
                if (null == this.aLiYunSMS) {
                    aLiYunSMS = new ALiSms(redisTemplate, properties);
                }
                return aLiYunSMS;
            } else if (StringUtils.isNoneBlank(defaultProvider) && defaultProvider.equalsIgnoreCase(Constants.SMS_PROVIDER_TWILIO)) {
                if (null == this.twilioSMS) {
                    twilioSMS = new TwilioSms(redisTemplate, properties);
                }
                return twilioSMS;
            }
        }
        return null;
    }
}
