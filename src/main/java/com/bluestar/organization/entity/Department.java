package com.bluestar.organization.entity;

import com.bluestar.organization.common.DepartmentConst;

/**
 * 组织实体类
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2018/07/15 14:13:26
 */
public class Department {

    /*
    create table t_dept
    (
        dept_id              varchar(64) not null comment 'ID',
        dept_code            varchar(64) comment '组织编号',
        dept_name            varchar(200) comment '组织名称',
        dept_p_code          varchar(64) comment '父级组织编号',
        dept_level           varchar(2) comment '级别',
        dept_order           int comment '在同一级的的排序',
        dept_status          varchar(2) comment '状态:1正常，2无效',
        dept_remark          varchar(400) comment '说明',
        primary key (dept_id)
    );
    */

    private String deptId = null;
    private String deptCode = null;
    private String deptName = null;
    private String deptPCode = null;
    private String deptLevel = null; // 级别，从 1 开始，且 1 是最高层级
    private Integer deptOrder = null;
    private String deptStatus = null; // 状态:1正常，2无效
    private String deptRemark = null;

    public Department() {
        // 默认是正常的状态
        this.setDeptStatus(DepartmentConst.DEPT_STATUS_NORMAL);
        this.setDeptOrder(DepartmentConst.DEFAULT_ORDER);
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptPCode() {
        return deptPCode;
    }

    public void setDeptPCode(String deptPCode) {
        this.deptPCode = deptPCode;
    }

    public String getDeptLevel() {
        return deptLevel;
    }

    public void setDeptLevel(String deptLevel) {
        this.deptLevel = deptLevel;
    }

    public Integer getDeptOrder() {
        return deptOrder;
    }

    public void setDeptOrder(Integer deptOrder) {
        this.deptOrder = deptOrder;
    }

    public String getDeptStatus() {
        return deptStatus;
    }

    public void setDeptStatus(String deptStatus) {
        this.deptStatus = deptStatus;
    }

    public String getDeptRemark() {
        return deptRemark;
    }

    public void setDeptRemark(String deptRemark) {
        this.deptRemark = deptRemark;
    }

    @Override
    public String toString() {
        return "Department{" +
                "deptId='" + deptId + '\'' +
                ", deptCode='" + deptCode + '\'' +
                ", deptName='" + deptName + '\'' +
                ", deptPCode='" + deptPCode + '\'' +
                ", deptLevel='" + deptLevel + '\'' +
                ", deptOrder=" + deptOrder +
                ", deptStatus='" + deptStatus + '\'' +
                ", deptRemark='" + deptRemark + '\'' +
                '}';
    }
}
