package com.bluestar.teach.dto;

import com.bluestar.teach.enums.Statusable;

import java.io.Serializable;

/**
 * 用于账号登陆的传输信息类
 * T 是被携带数据的类型
 *
 * @author Fish
 * */
public class AccountDto<T> implements Serializable
{
    /**
     * 传输的主要数据
     * 如果是成功就返回正常的数据 Object
     * 如果错误，就返回错误信息 String
     * */
    private T data = null;

    /**
     * 登陆状态
     * 这是一个枚举类，包含有登陆的状态码和状态信息
     * */
    //private Statusable status = null; /* TODO jackson 返回枚举类型会返回字符串，导致无法获取里面的值，目前我想到的就是把枚举转成 map */

    // 代表枚举类里面的属性
    private int code = 0;
    private String info = null;

    public AccountDto()
    {}

    public AccountDto(Statusable status)
    {
        this(null, status);
    }

    public AccountDto(T data, Statusable status)
    {
        this.data = data;
        this.code = status.getCode();
        this.info = status.getInfo();
    }

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    public int getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }

    /**
     * 将枚举信息封装到字段中
     *
     * @param status 要被封装的枚举
     * */
    public void setStatus(Statusable status)
    {
        this.code = status.getCode();
        this.info = status.getInfo();
    }

    @Override
    public String toString()
    {
        return "AccountDto{" +
                "data=" + data +
                ", code=" + code +
                ", info='" + info + '\'' +
                '}';
    }
}
