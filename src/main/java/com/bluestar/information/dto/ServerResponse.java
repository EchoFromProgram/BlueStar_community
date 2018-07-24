package com.bluestar.information.dto;

import com.bluestar.information.common.status.Statusable;

/**
 * @author: MackyHuang
 * @eamil: 973151766@qq.com
 * @createTime: 2018/7/20 11:41
 */
public class ServerResponse<T> {
    private int code;
    private String msg = null;
    private T data = null;

    private ServerResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    /**
     * 判断是否操作成功
     *
     * @return true 成功，false 失败
     */
    public boolean isSuccess() {
        return this.code == Statusable.SUCCESS_CODE;
    }

    public static <T> ServerResponse<T> response(Statusable status, T data) {
        return new ServerResponse<T>(status.getCode(), status.getMsg(), data);
    }

    public static ServerResponse response(Statusable status) {
        return new ServerResponse(status.getCode(), status.getMsg(), null);
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
