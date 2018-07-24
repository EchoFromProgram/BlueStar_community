package com.bluestar.common.utils;

import java.util.UUID;

/**
 * 服务器令牌工具类
 *
 * @author Imp
 * email: 1318944013@qq.com
 * date: 2018/7/14 14:55
 */
public class TokenUtil {

    /**
     * 随机生成一个token
     * @return string
     */
    public static String getToken() {
        return UUID.randomUUID().toString();
    }

    public static String checkToken(String token) {


        return null;
    }

    public static void main(String[] args) {
        System.out.println(getToken());
    }
}
