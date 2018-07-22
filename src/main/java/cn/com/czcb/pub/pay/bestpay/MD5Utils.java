/**
 * keydom.com.cn Inc.
 * Copyright (c) 2005-2017 All Rights Reserved.
 */
package cn.com.czcb.pub.pay.bestpay;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * MD5工具类
 * @author liufei
 * @version $Id: MD5Utils.java, v 0.1 2017年4月17日 下午4:39:15 liufei Exp $
 */
public class MD5Utils {

    /**
     * 生成有效签名
     * @param orgin 
     * @return String
     */
    public static String signature(String orgin) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byte2hex(md.digest(orgin.toString().getBytes("utf-8")));
        } catch (Exception e) {
            throw new RuntimeException("sign error !");
        }
        return result;
    }

    /**
     * 生成有效签名
     * @param args
     * @return String
     */
    public static String signature(String... args) {
        String arr = "";
        for (String arg : args) {
            if (arg != null) {
                arr += arg;
            }
        }
        return signature(arr);
    }

    /**
     * 二行制转字符串
     *
     * @param b
     * @return String
     */
    private static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString().toLowerCase();
    }

    /**
     * 获取单个文件的MD5值！ 
     * @param filePath
     * @return String
     */
    public static String getFileMD5(String filePath) {
        return getFileMD5(new File(filePath));
    }

    /**
     * 获取单个文件的MD5值！
     * @param file
     * @return String
     */
    public static String getFileMD5(File file) {

        if (!file.isFile()) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            FileInputStream in = new FileInputStream(file);
            byte buffer[] = new byte[1024];
            int len;
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
            BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(16).toUpperCase();
        } catch (Exception e) {
            throw new RuntimeException("Sign file error!");
        }
    }

    /**
     * 获取单个文件的MD5值！
     * @param fileBytes
     * @return String
     */
    public static String getFileMD5(byte[] fileBytes) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(fileBytes, 0, fileBytes.length);
            BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(16).toUpperCase();
        } catch (Exception e) {
            throw new RuntimeException("Sign file error!");
        }
    }
}
