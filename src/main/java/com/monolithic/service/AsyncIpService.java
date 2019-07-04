package com.monolithic.service;

import com.monolithic.config.ApplicationProperties;
import com.monolithic.service.dto.IPRecordsDTO;
import com.monolithic.service.dto.UserDTO;
import com.monolithic.service.util.IPUtil;
import com.monolithic.web.rest.vm.IPApiResultVM;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
public class AsyncIpService {

    private final Logger logger = LoggerFactory.getLogger(AsyncIpService.class);

    private String ipApi;

    private final IPRecordsService ipRecordsService;

    private final RedisTemplate redisTemplate;

    private final RestTemplate restTemplate;

    private final ApplicationProperties properties;

    public AsyncIpService(IPRecordsService ipRecordsService, RedisTemplate redisTemplate, RestTemplate restTemplate, ApplicationProperties properties) {
        this.ipRecordsService = ipRecordsService;
        this.redisTemplate = redisTemplate;
        this.restTemplate = restTemplate;
        this.properties = properties;
        ipApi = properties.getIpApi();
    }

    @Async
    public void ip(HttpServletRequest request, UserDTO user) {
        String ip = IPUtil.getIpAddress(request);
        if (StringUtils.isNoneBlank(ip)) {
            String device = IPUtil.getDevice(request);
            IPApiResultVM ipAPI = queryIpByIpAPI(ip);
            if (null != ipAPI && "success".equals(ipAPI.getStatus())) {
                IPRecordsDTO ipRecordsDTO = new IPRecordsDTO();
                ipRecordsDTO.setUserId(user.getId());
                ipRecordsDTO.setDevice(device);
                ipRecordsDTO.setCountryName(ipAPI.getCountry());
                ipRecordsDTO.setCityName(ipAPI.getCity());
                ipRecordsDTO.setIpAddress(ipAPI.getQuery());
                ipRecordsDTO.setStatus(ipAPI.getStatus());
                ipRecordsDTO = ipRecordsService.save(ipRecordsDTO);
                logger.debug("User ip info : {}", ipRecordsDTO);
            }
        }
    }

    /**
     * http://ip-api.com/docs/api:json
     */
    private IPApiResultVM queryIpByIpAPI(String ip) {
        logger.debug("Query IP from ip-api.com : {}", ip);
        IPApiResultVM ipResult = (IPApiResultVM) redisTemplate.opsForValue().get(ip);
        if (null != ipResult && "success".equalsIgnoreCase(ipResult.getStatus())) {
            return ipResult;
        }
        String url = ipApi + ip;
        ResponseEntity<IPApiResultVM> entity = null;
        try {
            entity = restTemplate.getForEntity(url, IPApiResultVM.class);
        } catch (Exception e) {
            logger.error("Query IP Error ! " + e.toString());
        }
        if (null != entity && 200 == entity.getStatusCodeValue() && "success".equalsIgnoreCase(Objects.requireNonNull(entity.getBody()).getStatus())) {
            redisTemplate.opsForValue().set(ip, entity.getBody());
            return entity.getBody();
        }
        return null;
    }
}
