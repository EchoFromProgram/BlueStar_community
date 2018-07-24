package com.bluestar.information.common.status.enums;

import com.bluestar.information.common.status.Statusable;


public enum DepartmentEnum implements Statusable {

    SUCCESS(Statusable.SUCCESS_CODE, "操作成功"),
    PARAMETER_UNCOMPLETED(-1, "参数不完整"),
    SAVE_FAILED(-2, "保存失败"),
    CODE_EXISTED(-3, "编号重复"),
    REMOVE_FAILED(-4, "删除失败"),
    UPDATE_FAILED(-5, "修改失败"),
    PARAMETER_TOO_LONG(-6, "参数太长"),
    GET_FAILED(-7, "获取资讯失败");

    private int code;
    private String msg;

    DepartmentEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
