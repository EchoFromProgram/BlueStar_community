package com.bluestar.advertisement.enums.response;

import com.bluestar.advertisement.enums.ResponseStatus;

/**
 * @author Imp
 * email: 1318944013@qq.com
 * date: 2018/7/18 11:23
 */
public enum  AdResponse  implements ResponseStatus {

    SUCCESS(ResponseStatus.SUCCESS_CODE, "操作成功！"),
    GET_SUCCESS(ResponseStatus.SUCCESS_CODE, "获取数据成功！"),
    PICTURE_IS_NULL(-1, "未上传图片！"),
    PICTURE_IS_EXITS(-1, "该图片名已经存在！"),
    UP_PICTURE_FAILURE(-1, "上传图片失败"),
    IS_NOT_PICTURE(-1,"请上传图片"),
    DELETE_AD_FAILURE(-1, "删除广告失败"),
    UPDATE_AD_FAILURE(-1, "更新广告失败"),
    GET_DATA_FAILURE(-1, "获取数据失败"),
    UP_AD_FAILURE(-1, "新增广告失败");



    private final int code;

    private final String desc;

    AdResponse(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
