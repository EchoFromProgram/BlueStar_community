package com.bluestar.organization.entity;

import com.bluestar.organization.common.DepartmentConst;

/**
 * 对应 t_user_dept 的实体类
 * 这仅仅是一个关系实体类
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2018/07/18 12:29:15
 */
public class UserDepartment {

    private String userDeptId = null;
    private String deptCode = null;
    private Integer userId = null;
    private Integer userDeptOrder = null;
    private String username = null;
    private String name = null;

    public UserDepartment() {
        this.setUserDeptOrder(DepartmentConst.DEFAULT_ORDER);
    }

    public String getUserDeptId() {
        return userDeptId;
    }

    public void setUserDeptId(String userDeptId) {
        this.userDeptId = userDeptId;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserDeptOrder() {
        return userDeptOrder;
    }

    public void setUserDeptOrder(Integer userDeptOrder) {
        this.userDeptOrder = userDeptOrder;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserDepartment{" +
                "userDeptId='" + userDeptId + '\'' +
                ", deptCode='" + deptCode + '\'' +
                ", userId=" + userId +
                ", userDeptOrder=" + userDeptOrder +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
