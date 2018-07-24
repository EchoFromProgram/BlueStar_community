package com.bluestar.organization.dto;

import com.bluestar.organization.common.status.Statusable;

/**
 * 部门业务传输类
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2018/07/18 15:07:53
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
