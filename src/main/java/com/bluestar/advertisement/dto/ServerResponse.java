package com.bluestar.advertisement.dto;

import com.bluestar.advertisement.enums.ResponseStatus;
import com.bluestar.advertisement.vo.AdVo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;

/**
 * Created by rzh on 2018/06/03
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
// 保证序列化json对象的时候，如果是null的对象，key也会消失
public class ServerResponse<T> extends AdVo implements Serializable {

    // 状态
    private int statusCode;

    // 信息
    private String msg;

    // 数据
    private T data;

    private ServerResponse(int statusCode, String msg, T data) {
        this.statusCode = statusCode;
        this.msg = msg;
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    /**
     * 判断是否成功，默认状态码都是 ResponseCode 中的 SUCCESS
     * @return true 成功，false 失败
     */
    @JsonIgnore // 使之不在json序列化当中
    public boolean isSuccess() {
        return this.statusCode == ResponseStatus.SUCCESS_CODE;
    }

    public static <T> ServerResponse<T> getServerResponse(ResponseStatus responseStatus) {
        return getServerResponse(responseStatus, null);
    }

    public static <T> ServerResponse<T> getServerResponse(ResponseStatus responseStatus, T data) {
        return new ServerResponse<T>(responseStatus.getCode(), responseStatus.getDesc(), data);
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "statusCode=" + statusCode +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
