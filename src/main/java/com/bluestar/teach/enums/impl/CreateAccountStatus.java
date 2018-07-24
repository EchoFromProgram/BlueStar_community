package com.bluestar.teach.enums.impl;

import com.bluestar.teach.enums.Statusable;

/**
 * 创建用户的状态枚举类
 *
 * @author Fish
 * */
public enum CreateAccountStatus implements Statusable
{
    SUCCESS(0, "创建用户成功！"),
    CORE_INFO_IS_NULL(-1, "用户名或密码为空！"),
    INFO_IS_NOT_COMPLETED(-2, "用户信息不完整！"),
    USER_IS_NULL(-3, "用户对象为空！"),
    USERNAME_EXISTED(-4, "用户名已经存在！"),
    UNKNOWN_ERROR(-5, "未知错误！"),
	CLASS_IS_NULL(-6,"班级为空!"),
	CLASS_TOO_MANY(-7,"一个学生只能对应一个班级！"),
	CLASS_EXISTED(-8,"班级已存在！"),
	USERNAME_NOEXISTED(-9, "用户不存在！");
	
    // 创建状态码
    private int code;

    // 创建信息
    private String info = null;

    CreateAccountStatus()
    {}

    CreateAccountStatus(int code, String info)
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
