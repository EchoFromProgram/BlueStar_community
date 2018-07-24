package com.bluestar.organization.controller;

import com.bluestar.organization.dto.ServerResponse;
import com.bluestar.organization.entity.Department;
import com.bluestar.organization.entity.UserDepartment;
import com.bluestar.organization.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 部门类控制器
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2018/07/18 15:23:27
 */
@Controller
public class DepartmentController {

    private DepartmentService departmentService = null;

    @Autowired
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * 跳转到组织页面
     *
     * @return 没有意义的返回值，这个返回值只是跳转界面的
     */
    @RequestMapping(path = "organization.do")
    public String toOrganizationPage() {
        return "organization/organization.jsp";
    }

    /**
     * 验证用户名是否可用
     *
     * @param deptCode 要被验证的部门编号
     * @return 返回验证情况
     */
    @RequestMapping(path = "checkDeptCode.do")
    @ResponseBody
    public ServerResponse checkDeptCode(String deptCode) {
        return departmentService.checkDepartmentCode(deptCode);
    }

    /**
     * 新增一个部门
     *
     * @param department 要新增的部门
     * @return 返回新增的情况
     */
    @RequestMapping(path = "addDepartment.do")
    @ResponseBody
    public ServerResponse addANewDepartment(Department department) {
        return departmentService.addDepartment(department);
    }

    /**
     * 删除指定的部门
     *
     * @param deptId 指定部门的 id
     * @return 返回删除的情况
     */
    @RequestMapping(path = "deleteDepartment.do")
    @ResponseBody
    public ServerResponse deleteDepartment(String deptId) {
        return departmentService.deleteDepartment(deptId);
    }

    /**
     * 更新指定的部门信息
     *
     * @param department  指定的部门信息，这里的 id 必须得有
     * @param oldDeptCode 旧的 id 部门编号
     * @return 返回更新情况
     */
    @RequestMapping(path = "updateDepartment.do")
    @ResponseBody
    public ServerResponse updateDepartment(Department department, String oldDeptCode) {
        return departmentService.updateDepartment(department, oldDeptCode);
    }

    /**
     * 通过部门编号得到这个部门旗下的所有子部门
     *
     * @param deptCode            指定的部门编号
     * @param isGetAllDepartments 是否要显示所有部门信息，包括无效，这个参数不是必须的
     * @return 返回查询的结果
     */
    @RequestMapping(path = "getChildrenDepartments.do")
    @ResponseBody
    public ServerResponse getChildrenDepartments(String deptCode, @RequestParam(required = false) Boolean isGetAllDepartments) {
        System.out.println(isGetAllDepartments);
        return departmentService.getChildrenDepartments(deptCode, isGetAllDepartments);
    }

    /**
     * 通过部门级别和父级别得到部门信息
     *
     * @param deptLevel           指定的部门级别
     * @param deptPCode           父级部门编号
     * @param isGetAllDepartments 是否要显示所有部门信息，包括无效，这个参数不是必须的
     * @return 返回查询的结果
     */
    @RequestMapping(path = "getDepartmentsByLevelAndDeptPCode.do")
    @ResponseBody
    public ServerResponse getDepartmentsByLevelAndDeptPCode(String deptLevel,
                                                            @RequestParam(required = false) String deptPCode,
                                                            @RequestParam(required = false) Boolean isGetAllDepartments) {
        return departmentService.getDepartmentsByLevelAndDeptPCode(deptLevel, deptPCode, isGetAllDepartments);
    }

    /**
     * 将用户保存进部门
     * deptCode 和 userId 是必须的
     *
     * @param userDepartment 保存上面所需参数的对象
     * @return 返回保存情况
     */
    @RequestMapping(path = "putUserInDepartment.do")
    @ResponseBody
    public ServerResponse putUserInDepartment(UserDepartment userDepartment) {
        return departmentService.putUserInDepartment(userDepartment);
    }

    /**
     * 获得部门中的用户
     *
     * @param deptCode 指定的部门编号
     * @return 返回得到的用户
     */
    @RequestMapping(path = "getUsersInDepartment.do")
    @ResponseBody
    public ServerResponse getUsersInDepartment(String deptCode) {
        return departmentService.getUsersInDepartment(deptCode);
    }

    /**
     * 更新用户部门关系，比如把一个用户转移到另外一个部门
     * 根据 userDepartment 中的 id 来找到具体的信息
     *
     * @param userDepartment 用户部门关系对象
     * @return 返回修改情况
     */
    @RequestMapping(path = "updateUserInDepartment.do")
    @ResponseBody
    public ServerResponse updateUserInDepartment(UserDepartment userDepartment) {
        return departmentService.updateUserInDepartment(userDepartment);
    }

    /**
     * 根据 userDeptId 来删除一条关系，就是把一个对象从部门中移除
     *
     * @param userDeptId 指定的 id
     * @return 返回删除情况
     */
    @RequestMapping(path = "deleteUserInDepartment.do")
    @ResponseBody
    public ServerResponse deleteUserInDepartment(String userDeptId) {
        return departmentService.deleteUserInDepartment(userDeptId);
    }

    /**
     * 列出不属于这个部门的用户
     *
     * @param page     页码数
     * @param deptCode 部门编号
     * @return 返回用户列表
     */
    @RequestMapping(path = "listUsers.do")
    @ResponseBody
    public ServerResponse listUsers(Integer page, String deptCode) {
        return departmentService.listUsers(page, deptCode);
    }
}
