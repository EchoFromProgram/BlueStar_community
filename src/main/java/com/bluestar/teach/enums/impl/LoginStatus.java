package com.bluestar.teach.enums.impl;

import com.bluestar.teach.enums.Statusable;

/**
 * 登陆业务要用到的传输信息状态枚举类
 *
 * @author Fish
 * */
public enum LoginStatus implements Statusable
{
    SUCCESS(0, "登陆成功！"),
    WRONG_USERNAME(-1, "用户名或密码错误！"),
    WRONG_PASSWORD(-2, "用户名或密码错误！"),
    NEED_LOGIN(-3, "您还没有登陆！"),
    LOGOUT_SUCCESS(0, "成功退出登录！");

    // 登陆状态码
    private int code;

    // 登陆状态信息
    private String info = null;

    LoginStatus()
    {}

    LoginStatus(int code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getInfo()
    {
        return info;
    }

    public void setInfo(String info)
    {
        this.info = info;
    }
}
