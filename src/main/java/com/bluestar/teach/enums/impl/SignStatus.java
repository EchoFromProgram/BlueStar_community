package com.bluestar.teach.enums.impl;

import com.bluestar.teach.enums.Statusable;

/**
 * 签到情况枚举类
 *
 * @author Fish
 * */
public enum SignStatus implements Statusable
{
    SUCCESS(0, "签到成功！"),
    WRONG_CODE(-1, "验证码错误！"),
    TOO_LATE(-2, "你迟到了！请填写原因！"),
    TOO_EARLY(-3, "签到还未开放！"),
    SIGN_IS_OVER(-4, "签到已经结束！请填写原因！"),
    ILLEGAL_SIGN(-5, "非法签到！可能是用户问题！"),
    WRONG_CLASS(-6, "班级错误！"),
    HAS_SIGNED(-7, "这节课，你已经签到过了！");

    // 签到情况状态码
    private int code;

    // 签到情况信息
    private String info = null;

    SignStatus()
    {}

    SignStatus(int code, String info)
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

    @Override
    public String toString()
    {
        return "SignStatus{" +
                "code=" + code +
                ", info='" + info + '\'' +
                '}';
    }
}
