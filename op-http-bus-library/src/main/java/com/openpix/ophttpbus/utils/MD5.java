package com.openpix.ophttpbus.utils;

import android.util.Log;

import java.security.MessageDigest;

/**
 * Copyright (C), 2020-2020, openpix
 * Author: pix
 * Date: 2020/4/14 16:49
 * Version: 1.0.0
 * History:
 * <author> <time> <version> <desc>
 */
public class MD5 {
    public static String getMD5(String str) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            Log.d("md5", "", e);
            return "";
        }

        byte[] md5Bytes = md5.digest(str.getBytes());

        StringBuilder hexValue = new StringBuilder();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
