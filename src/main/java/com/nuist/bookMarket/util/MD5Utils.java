package com.nuist.bookMarket.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密算法工具类
 *@author dengwei
 */

public class MD5Utils {

    public static String md5(String input) {
        if (null == input) {
            input = "";
        }
        String result = "";
        try {
            // MessageDigest类用于为应用程序提供信息摘要算法的功能，如MD5或SHA算法
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 获取输入
            md.update(input.getBytes());
            // 获得产出（有符号的哈希值字节数组，包含16个元素）
            byte output[] = md.digest();

            // 32位的加密字符串
            StringBuilder builder = new StringBuilder(32);
            // 下面进行十六进制的转换
            for (int offset = 0; offset < output.length; offset++) {
                // 转变成对应的ASSIC值
                int value = output[offset];
                // 将负数转为正数（最终返回结果是无符号的）
                if (value < 0) {
                    value += 256;
                }
                // 小于16，转为十六进制后只有一个字节，左边追加0来补足2个字节
                if (value < 16) {
                    builder.append("0");
                }
                // 将16位byte[]转换为32位无符号String
                builder.append(Integer.toHexString(value));
            }
            result = builder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }
}
