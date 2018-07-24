package com.bluestar.organization.common.status.enums;

import com.bluestar.organization.common.status.Statusable;

/**
 * 部门业务状态类
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2018/07/18 15:15:13
 */
public enum DepartmentEnum implements Statusable {

    SUCCESS(Statusable.SUCCESS_CODE, "操作成功"),
    PARAMETER_UNCOMPLETED(-1, "参数不完整"),
    SAVE_FAILED(-2, "保存失败"),
    CODE_EXISTED(-3, "部门编号重复"),
    REMOVE_FAILED(-4, "删除失败"),
    UPDATE_FAILED(-5, "修改失败"),
    PARAMETER_TOO_LONG(-6, "参数太长"),
    CODE_DOSE_NOT_EXIST(-7, "部门编号不存在"),
    ERROR(-8, "内部错误"),
    USER_IS_IN_THIS_DEPARTMENT(-9, "这个用户已经存在于这个部门了");

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
