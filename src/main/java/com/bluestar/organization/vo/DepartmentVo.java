package com.bluestar.organization.vo;

import com.bluestar.teach.entity.User;

import java.util.List;

/**
 * 部门视图实体类
 * 主要是一个部门下有很多个用户
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2018/07/18 12:09:17
 */
public class DepartmentVo {

    private String deptCode = null;
    private List<User> users = null;

    public DepartmentVo() {}

    public DepartmentVo(String deptCode, List<User> users) {
        this.deptCode = deptCode;
        this.users = users;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "DepartmentVo{" +
                "deptCode='" + deptCode + '\'' +
                ", users=" + users +
                '}';
    }
}
