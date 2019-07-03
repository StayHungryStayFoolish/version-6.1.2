package com.monolithic.service.util;

import org.apache.commons.lang3.StringUtils;

public class StringConvertUtil {

    /**
     * Get file extension name
     *
     * @param origin file name
     * @return extension name   .jpg .text
     */
    public static String getExtensionName(String origin) {
        if ((origin != null) && (origin.length() > 0)) {
            int dot = origin.lastIndexOf('.');
            origin = getExtension(origin, dot);
            if (StringUtils.isNoneBlank(origin)) {
                return origin;
            }
        }
        return null;
    }

    /**
     * Get file file type
     *
     * @param origin origin
     * @return type     jpg   text
     */
    public static String getTypeByLastDot(String origin) {
        if ((origin != null) && (origin.length() > 0)) {
            int dot = origin.lastIndexOf('.') + 1;
            origin = getExtension(origin, dot);
            if (origin.contains("?")) {
                origin = getFrontAtSymbol(origin, "?");
            }
            if (StringUtils.isNoneBlank(origin)) {
                return origin;
            }
        }
        return null;
    }

    /**
     * Get the filename before the symbol
     * https://s3.ap-southeast-1.amazonaws.com/chinaexchange  TO  https
     *
     * @param origin string
     * @param symbol symbol
     * @return string
     */
    public static String getFrontAtSymbol(String origin,String symbol) {
        if ((origin != null) && (origin.length() > 0)) {
            int dot = origin.lastIndexOf(symbol) + 1;
            origin = getFront(origin, dot);
            if (StringUtils.isNoneBlank(origin)) {
                return origin;
            }
        }
        return null;
    }

    /**
     * Get the filename after the symbol
     * https://s3.ap-southeast-1.amazonaws.com/chinaexchange  TO  //s3.ap-southeast-1.amazonaws.com/chinaexchange
     *
     * @param origin string
     * @param symbol symbol
     * @return string
     */
    public static String getBackAtSymbol(String origin,String symbol) {
        if ((origin != null) && (origin.length() > 0)) {
            int dot = origin.lastIndexOf(symbol) + 1;
            origin = getExtension(origin, dot);
            if (StringUtils.isNoneBlank(origin)) {
                return origin;
            }
        }
        return null;
    }

    /**
     * Get the trim string after dot
     *
     * @param origin origin
     * @param dot dot
     * @return trim string
     */
    public static String getExtension(String origin ,int dot) {
        if ((dot > -1) && (dot < (origin.length() - 1))) {
            return origin.substring(dot);
        }
        return null;
    }

    /**
     * Get the trim string before dot
     *
     * @param origin origin
     * @param dot dot
     * @return trim string
     */
    public static String getFront(String origin ,int dot) {
        if ((dot > -1) && (dot < (origin.length() - 1))) {
            return origin.substring(0, dot - 1);
        }
        return null;
    }

    public static String trimAtEndIndex(String origin,int endIndex) {
        return origin.substring(0, origin.length() - endIndex);
    }
}


