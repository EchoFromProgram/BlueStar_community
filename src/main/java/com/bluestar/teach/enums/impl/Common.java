package com.bluestar.teach.enums.impl;

import com.bluestar.teach.enums.Statusable;

/**
 * 常用状态的枚举类
 *
 * @author Fish
 * */
public enum Common implements Statusable
{
    SUCCESS(0, "成功！"),
    SAVE_CLASS_SUCCESS(0,"新增班级成功！"),
    DELTE_NOTICE_SUCCESS(0,"删除通知成功！"),
    UPDATE_NOTICE_SUCCESS(0,"修改通知成功！"),
    INSERT_NOTICE_SUCCESS(0,"发布通知成功！"),
    DELETE_ROLE_SUCCESS(0,"删除角色成功！"),
    UPDATE_ROLE_SUCCESS(0,"修改角色成功！"),
    INSERT_ROLE_SUCCESS(0,"新增角色成功！"),
    UPDATE_SCORE_SUCCESS(0,"修改成绩成功！"),
    INSERT_SCORE_SUCCESS(0,"发布成绩成功！"),
    DELETE_SCORE_SUCCESS(0,"删除成绩成功！"),
    ERROR(-1, "系统内部错误！请重试！"),

    WRONG_ARGEMENT(-2, "参数错误！"),
    GET_IS_NULL(-3 , "得到的数据为空！");

    // 创建状态码
    private int code;

    // 创建信息
    private String info = null;

    Common()
    {}

    Common(int code, String info)
    {
        this.code = code;
        this.info = info;
    }

    @Override
    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    @Override
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
        return "Common{" +
                "code=" + code +
                ", info='" + info + '\'' +
                '}';
    }
}
