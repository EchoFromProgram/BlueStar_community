package com.bluestar.organization.common.status;

/**
 * 状态类接口
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2018/07/18 15:12:33
 */
public interface Statusable {

    // 操作成功的状态码
    int SUCCESS_CODE = 0;

    // 得到状态码
    int getCode();

    // 得到状态信息
    String getMsg();
}
