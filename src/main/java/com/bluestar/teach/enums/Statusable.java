package com.bluestar.teach.enums;

/**
 * 返回状态的接口类
 *
 * @author Fish
 * */
public interface Statusable
{
    // 获取状态码
    public int getCode();

    // 获取状态信息
    public String getInfo();
}
