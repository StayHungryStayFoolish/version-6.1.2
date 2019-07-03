package com.monolithic.web.rest.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SunBase64Util {

    /**
     * Encode plain string
     *
     * @param plain plain string
     * @return decode string
     */
    public static String encode(String plain) {
        return encodeByte(plain.getBytes());
    }

    /**
     * Decode String
     *
     * @param encoder encode string
     * @return plain string
     */
    public static String decode(String encoder) {
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(encoder);
            return new String(bytes);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Encode bytes
     *
     * @param bytes string converted bytes
     * @return encode bytes
     */
    private static String encodeByte(byte[] bytes) {
        return new BASE64Encoder().encode(bytes);
    }
}
