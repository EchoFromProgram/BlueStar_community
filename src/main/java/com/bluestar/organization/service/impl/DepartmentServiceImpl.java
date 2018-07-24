package com.bluestar.organization.service.impl;

import com.bluestar.common.utils.CodeUtil;
import com.bluestar.common.utils.PageUtil;
import com.bluestar.organization.common.DepartmentConst;
import com.bluestar.organization.common.status.enums.DepartmentEnum;
import com.bluestar.organization.dao.DepartmentDao;
import com.bluestar.organization.dto.ServerResponse;
import com.bluestar.organization.entity.Department;
import com.bluestar.organization.entity.UserDepartment;
import com.bluestar.organization.service.DepartmentService;
import com.bluestar.teach.entity.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门业务具体实现类
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2018/07/18 14:31:43
 */
@Service
/*
 * @Transactional中的的属性 propagation :事务的传播行为 isolation :事务的隔离级别 readOnly :只读
 *                     rollbackFor :发生哪些异常回滚 noRollbackFor :发生哪些异常不回滚
 *                     rollbackForClassName 根据异常类名回滚
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
public class DepartmentServiceImpl implements DepartmentService {

    // log4j 记录日志
    private static final Log log = LogFactory.getLog(DepartmentServiceImpl.class);

    DepartmentDao departmentDao = null;

    @Autowired
    public void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    /**
     * 新增一个部门
     *
     * @param department 要被添加的部门
     * @return 返回添加状态
     */
    public ServerResponse addDepartment(Department department) {
        // 首先进行合法性检验
        if (department == null) {
            return ServerResponse.response(DepartmentEnum.PARAMETER_UNCOMPLETED);
        }

        boolean isParameterUncompleted = (CodeUtil.isBlank(department.getDeptCode()) ||
                CodeUtil.isBlank(department.getDeptName()) || CodeUtil.isBlank(department.getDeptLevel()));
        if (isParameterUncompleted) {
            return ServerResponse.response(DepartmentEnum.PARAMETER_UNCOMPLETED);
        }

        boolean isParameterTooLong = (department.getDeptCode().length() >= DepartmentConst.MAX_LENGTH_OF_DEPT_CODE ||
                department.getDeptName().length() >= DepartmentConst.MAX_LENGTH_OF_DEPT_NAME ||
                department.getDeptLevel().length() >= DepartmentConst.MAX_LENGTH_OF_DEPT_STATUS);
        if (isParameterTooLong) {
            return ServerResponse.response(DepartmentEnum.PARAMETER_TOO_LONG);
        }

        // 检查部门编号是否可用
        ServerResponse resp = checkDepartmentCode(department.getDeptCode());
        if (!resp.isSuccess()) {
            return resp;
        }

        // 参数合法
        department.setDeptId(CodeUtil.getId());

        int affect = departmentDao.saveDepartment(department);
        if (affect <= 0) {
            log.error("保存部门：" + department);
            return ServerResponse.response(DepartmentEnum.SAVE_FAILED);
        }

        // 保存成功
        return ServerResponse.response(DepartmentEnum.SUCCESS);
    }

    /**
     * 检查部门编号是否可用
     *
     * @param deptCode 部门编号
     * @return 返回状态信息
     */
    public ServerResponse checkDepartmentCode(String deptCode) {

        if (CodeUtil.isBlank(deptCode)) {
            return ServerResponse.response(DepartmentEnum.PARAMETER_UNCOMPLETED);
        }

        int count = departmentDao.countDepartmentCode(deptCode);
        if (count > 0) {
            return ServerResponse.response(DepartmentEnum.CODE_EXISTED);
        }

        return ServerResponse.response(DepartmentEnum.SUCCESS);
    }

    /**
     * 删除一个部门
     *
     * @param deptId 要被删除的部门 id
     * @return 返回删除情况
     */
    @Override
    public ServerResponse deleteDepartment(String deptId) {

        // 判断参数合法性
        if (CodeUtil.isBlank(deptId)) {
            return ServerResponse.response(DepartmentEnum.PARAMETER_UNCOMPLETED);
        }

        // TODO 删除目前是置于无效状态，但是部门编号肯定是唯一的，那如果一个部门被删除了（但这时数据库还在），
        // 那这个编号还是用不了。。。会一直提示编号存在
        int affect = departmentDao.removeDepartment(deptId);
        if (affect <= 0) {
            log.error("删除失败，部门 id：" + deptId);
            return ServerResponse.response(DepartmentEnum.REMOVE_FAILED);
        }

        // 删除成功
        return ServerResponse.response(DepartmentEnum.SUCCESS);
    }

    /**
     * 更新部门信息
     * 这里的业务较为复杂，因为要涉及 3 个数据库操作
     * 1. 首先是部门表更新，具体一条信息的更新
     * 2. 如果修改涉及编号的修改，还要把这张表其他父节点是这个编号的都改了
     * 3. 如果修改涉及编号的修改，还要把用户部门表中的关系约一起改了
     *
     * @param department        要被更新的部门信息
     * @param oldDepartmentCode 原来的部门编号
     * @return 返回部门信息
     */
    @Override
    public ServerResponse updateDepartment(Department department, String oldDepartmentCode) {
        // 检验参数合法性
        if (department == null || CodeUtil.isBlank(department.getDeptId()) || CodeUtil.isBlank(oldDepartmentCode)) {
            return ServerResponse.response(DepartmentEnum.PARAMETER_UNCOMPLETED);
        }

        boolean isParameterUncompleted = (CodeUtil.isBlank(department.getDeptCode()) ||
                CodeUtil.isBlank(department.getDeptName()) || CodeUtil.isBlank(department.getDeptLevel()));
        if (isParameterUncompleted) {
            return ServerResponse.response(DepartmentEnum.PARAMETER_UNCOMPLETED);
        }

        boolean isParameterTooLong = (department.getDeptCode().length() >= DepartmentConst.MAX_LENGTH_OF_DEPT_CODE ||
                department.getDeptName().length() >= DepartmentConst.MAX_LENGTH_OF_DEPT_NAME ||
                department.getDeptLevel().length() >= DepartmentConst.MAX_LENGTH_OF_DEPT_STATUS);
        if (isParameterTooLong) {
            return ServerResponse.response(DepartmentEnum.PARAMETER_TOO_LONG);
        }

        // 参数合法
        if (!department.getDeptCode().equals(oldDepartmentCode)) {
            // 验证新的部门编号合法性
            ServerResponse resp = checkDepartmentCode(department.getDeptCode());
            if (!resp.isSuccess()) {
                return resp;
            }
        }

        // 首先更新具体这条数据的信息
        int affect = departmentDao.updateDepartment(department);
        if (affect <= 0) {
            log.error("更新失败：" + department);
            return ServerResponse.response(DepartmentEnum.UPDATE_FAILED);
        }

        // 到这一步就说明，上一步更新成功，这时候要去更新表中其他数据
        // 当然，先看看部门编号有没有改动，没有的话，这一步就可以省略了
        if (!department.getDeptCode().equals(oldDepartmentCode)) {
            // 部门编号被修改了，要同时改动另外两处
            affect = departmentDao.updateDepartmentPCode(oldDepartmentCode, department.getDeptCode());
            if (affect <= 0) {
                log.error("旧的部门编号：" + oldDepartmentCode + "，想改为部门编号：" + department.getDeptCode());
                return ServerResponse.response(DepartmentEnum.UPDATE_FAILED);
            }

            // 修改另外一张表
            affect = departmentDao.updateDepartmentCode(oldDepartmentCode, department.getDeptCode());
            if (affect <= 0) {
                log.error("旧的部门编号：" + oldDepartmentCode + "，想改为部门编号：" + department.getDeptCode());
                return ServerResponse.response(DepartmentEnum.UPDATE_FAILED);
            }
        }

        // 修改成功
        return ServerResponse.response(DepartmentEnum.SUCCESS);
    }

    /**
     * 通过部门编号得到这个部门旗下的所有子部门
     *
     * @param deptCode            指定的部门编号
     * @param isGetAllDepartments 是否查询出所有部门信息
     * @return 返回得到的所有子部门
     */
    @Override
    public ServerResponse getChildrenDepartments(String deptCode, boolean isGetAllDepartments) {

        // 这两个参数都是必须的
        if (CodeUtil.isBlank(deptCode)) {
            return ServerResponse.response(DepartmentEnum.PARAMETER_UNCOMPLETED);
        }

        // 查询数据库
        List<Department> departments = departmentDao.listDepartmentByDeptCode(deptCode, isGetAllDepartments);
        if (departments == null) {
            // 查询是空的，意味着查询出错了，因为即使数据为空，返回值也应该是空的 list，而不是 null
            log.warn("listDepartmentByDeptCode: 返回 null " + "(参数: " + deptCode + ", " + isGetAllDepartments + ")");
            return ServerResponse.response(DepartmentEnum.SUCCESS, new ArrayList<Department>(0));
        }

        // 成功查询，返回前台
        return ServerResponse.response(DepartmentEnum.SUCCESS, departments);
    }

    /**
     * 通过父级编号找到指定级别的部门，其实只能找到子级
     * 所以这个方法的参数设定其实是有问题的，但以后说不定会有新功能用到
     *
     * @param deptLevel           级别
     * @param deptPCode           父级部门
     * @param isGetAllDepartments 是否查询出所有部门信息
     * @return 返回得到的部门
     */
    @Override
    public ServerResponse getDepartmentsByLevelAndDeptPCode(String deptLevel, String deptPCode,
                                                            boolean isGetAllDepartments) {

        // 级别不能省略，这个方法的精髓就在级别。。。
        if (CodeUtil.isBlank(deptLevel)) {
            return ServerResponse.response(DepartmentEnum.PARAMETER_UNCOMPLETED);
        }

        // 查询数据库
        List<Department> departments = departmentDao.listDepartmentByDeptLevel(deptLevel, deptPCode,
                isGetAllDepartments);
        if (departments == null) {
            // 查询是空的，意味着查询出错了，因为即使数据为空，返回值也应该是空的 list，而不是 null
            log.warn("listDepartmentByDeptLevel: 返回 null " + "(参数: " + deptLevel + ", "
                    + deptPCode + ", " + isGetAllDepartments + ")");
            return ServerResponse.response(DepartmentEnum.SUCCESS, new ArrayList<Department>(0));
        }

        return ServerResponse.response(DepartmentEnum.SUCCESS, departments);
    }

    /**
     * 将一个用户放进部门中
     *
     * @param userDepartment 保存着用户 id 和部门编号
     * @return 返回保存结果
     */
    @Override
    public ServerResponse putUserInDepartment(UserDepartment userDepartment) {

        // 检查参数合法性
        if (userDepartment == null) {
            return ServerResponse.response(DepartmentEnum.PARAMETER_UNCOMPLETED);
        }

        boolean isParameterUncompleted = CodeUtil.isBlank(userDepartment.getDeptCode()) ||
                userDepartment.getUserId() == null;
        if (isParameterUncompleted) {
            return ServerResponse.response(DepartmentEnum.PARAMETER_UNCOMPLETED);
        }

        // 检查部门编号的合法性，存不存在这个部门
        ServerResponse resp = checkDepartmentCode(userDepartment.getDeptCode());
        if (resp.getCode() != DepartmentEnum.CODE_EXISTED.getCode()) {
            // 如果这个部门编号是可用的，也就说现在不存在这个部门编号！！
            return ServerResponse.response(DepartmentEnum.CODE_DOSE_NOT_EXIST);
        }

        // 判断这个关系是否已经存在
        resp = checkUserDept(userDepartment);
        if (!resp.isSuccess()) {
            return resp;
        }

        // 设置主键，不允许别的地方设置主键
        userDepartment.setUserDeptId(CodeUtil.getId());

        // 参数合法
        int affect = departmentDao.saveUserInDepartment(userDepartment);
        if (affect <= 0) {
            log.error("将用户放进部门中失败！" + userDepartment);
            return ServerResponse.response(DepartmentEnum.SAVE_FAILED);
        }

        // 操作成功
        return ServerResponse.response(DepartmentEnum.SUCCESS);
    }

    /**
     * 通过部门编号获取这个部门所拥有的用户
     *
     * @param deptCode 部门编号
     * @return 返回这个部门的所有用户
     */
    @Override
    public ServerResponse getUsersInDepartment(String deptCode) {

        if (CodeUtil.isBlank(deptCode)) {
            return ServerResponse.response(DepartmentEnum.PARAMETER_UNCOMPLETED);
        }

        // 查询数据库
        List<UserDepartment> users = departmentDao.listUsersInDepartment(deptCode);
        if (users == null) {
            // 查询是空的，意味着查询出错了，因为即使数据为空，返回值也应该是空的 list，而不是 null
            log.warn("listDepartmentByDeptLevel: 返回 null " + "(参数: " + deptCode + ")");
            return ServerResponse.response(DepartmentEnum.SUCCESS, new ArrayList<UserDepartment>(0));
        }

        return ServerResponse.response(DepartmentEnum.SUCCESS, users);
    }

    /**
     * 更新用户部门关系，比如把一个用户转移到另外一个部门
     * 根据 userDepartment 中的 id 来找到具体的信息
     *
     * @param userDepartment 指定的关系对象
     * @return 返回修改情况
     */
    @Override
    public ServerResponse updateUserInDepartment(UserDepartment userDepartment) {

        // 检查参数合法性
        if (userDepartment == null || CodeUtil.isBlank(userDepartment.getUserDeptId())) {
            return ServerResponse.response(DepartmentEnum.PARAMETER_UNCOMPLETED);
        }

        // 用户 id 必须要
        if (userDepartment.getUserId() == null) {
            return ServerResponse.response(DepartmentEnum.PARAMETER_UNCOMPLETED);
        }

        // 检查部门编号的合法性，存不存在这个部门
        ServerResponse resp = checkDepartmentCode(userDepartment.getDeptCode());
        if (resp.getCode() != DepartmentEnum.CODE_EXISTED.getCode()) {
            // 如果这个部门编号是可用的，也就说现在不存在这个部门编号！！
            return ServerResponse.response(DepartmentEnum.CODE_DOSE_NOT_EXIST);
        }

        // 参数合法
        int affect = departmentDao.updateUserInUserDepartment(userDepartment);
        if (affect <= 0) {
            log.error("修改用户部门关系失败！" + userDepartment);
            return ServerResponse.response(DepartmentEnum.UPDATE_FAILED);
        }

        // 操作成功
        return ServerResponse.response(DepartmentEnum.SUCCESS);
    }

    /**
     * 删除用户部门关系
     *
     * @param userDeptId 用户部门编号，这是主键
     * @return 返回删除情况
     */
    @Override
    public ServerResponse deleteUserInDepartment(String userDeptId) {

        if (CodeUtil.isBlank(userDeptId)) {
            return ServerResponse.response(DepartmentEnum.PARAMETER_UNCOMPLETED);
        }

        // 开始删除
        int affect = departmentDao.removeUserInUserDepartment(userDeptId);
        if (affect <= 0) {
            log.error("用户关系删除失败！id = " + userDeptId);
            return ServerResponse.response(DepartmentEnum.REMOVE_FAILED);
        }

        // 删除成功
        return ServerResponse.response(DepartmentEnum.SUCCESS);
    }

    /**
     * 列出不属于这个部门的用户
     *
     * @param deptCode 部门编号
     * @param page 页码数
     * @return 返回用户列表
     */
    @Override
    public ServerResponse listUsers(Integer page, String deptCode) {
        if (CodeUtil.isBlank(deptCode)) {
            return ServerResponse.response(DepartmentEnum.PARAMETER_UNCOMPLETED);
        }

        // 检查部门编号的合法性，存不存在这个部门
        ServerResponse resp = checkDepartmentCode(deptCode);
        if (resp.getCode() != DepartmentEnum.CODE_EXISTED.getCode()) {
            // 如果这个部门编号是可用的，也就说现在不存在这个部门编号！！
            return ServerResponse.response(DepartmentEnum.CODE_DOSE_NOT_EXIST);
        }

        // 没有传页码，就默认为第一页
        if (page == null || page <= 0) {
            page = 1;
        }

        // 一次查询 10 条记录
        PageUtil.toPage(page, DepartmentConst.PAGE_SIZE);
        List<User> users = departmentDao.listUsers(deptCode);
        if (users == null) {
            log.error("listUsers: " + page + ", " + deptCode);
            return ServerResponse.response(DepartmentEnum.ERROR);
        }

        // 查询成功，返回数据
        return ServerResponse.response(DepartmentEnum.SUCCESS, PageUtil.pageInfo(users));
    }

    /**
     * 判断这个关系是否存在
     *
     * @param userDepartment 这个关系
     * @return 返回 success 表示可用
     */
    @Override
    public ServerResponse checkUserDept(UserDepartment userDepartment) {
        // 检验参数
        if (userDepartment == null) {
            return ServerResponse.response(DepartmentEnum.PARAMETER_UNCOMPLETED);
        }

        boolean isParametersUncompleted = userDepartment.getUserId() == null ||
                CodeUtil.isBlank(userDepartment.getDeptCode());
        if (isParametersUncompleted) {
            return ServerResponse.response(DepartmentEnum.PARAMETER_UNCOMPLETED);
        }

        int affect = departmentDao.countUserDept(userDepartment);
        if (affect > 0) {
            return ServerResponse.response(DepartmentEnum.USER_IS_IN_THIS_DEPARTMENT);
        }

        // 关系可用
        return ServerResponse.response(DepartmentEnum.SUCCESS);
    }
}
