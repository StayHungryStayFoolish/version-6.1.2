package com.monolithic.service.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPUtil {

    public static String getDevice(HttpServletRequest request){
        String userAgent = request.getHeader("user-agent");
        if(userAgent.contains("Android")){
            return "Android";
        }else if(userAgent.contains("iPhone") || userAgent.contains("iPad")){
            return "iPhone";
        }else{
            return "PC";
        }
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = null;
        ipAddress = request.getHeader("x-forwarded-for");
        if (null == ipAddress || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (null == ipAddress || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (null == ipAddress || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1")) {
                // get location ip address
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet != null ? inet.getHostAddress() : null;
            }

        }
        // when have multi ip address, use first ip ,because it`s real ip
        //"***.***.***.***".length() = 15
        if (null != ipAddress && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }
}
