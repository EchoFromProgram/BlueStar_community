package com.bluestar.information.common.status;


public interface Statusable {

    // 操作成功的状态码
    int SUCCESS_CODE = 0;

    // 得到状态码
    int getCode();

    // 得到状态信息
    String getMsg();
}
