package com.bluestar.organization.dao;

import com.bluestar.organization.entity.Department;
import com.bluestar.organization.entity.UserDepartment;
import com.bluestar.teach.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 组织部门 dao 接口
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2018/07/16 14:17:44
 */
public interface DepartmentDao {

    /**
     * 通过 deptCode 获得这个组织的下一级组织部门信息
     *
     * @param deptCode 组织部门编号
     * @param all 是否要查询出无效的
     * @return 返回这个部门下一级的信息
     */
    List<Department> listDepartmentByDeptCode(@Param("deptCode") String deptCode, @Param("all") boolean all);

    /**
     * 通过 deptLevel 获得相同等级的组织信息
     *
     * @param deptLevel 组织级别
     * @param all 是否要查询出无效
     * @return 返回和 deptLevel 相同级别的组织信息
     */
    List<Department> listDepartmentByDeptLevel(@Param("deptLevel") String deptLevel, @Param("deptPCode") String deptPCode,
                                               @Param("all") boolean all);

    /**
     * 保存一个新的组织部门信息
     *
     * @param department 要保存的部门信息
     * @return 0 表示失败，大于 0 表示成功，通常这个值就是 1
     */
    int saveDepartment(Department department);

    /**
     * 通过 deptId 找到并删除一个部门信息，这里的“删除”仅仅只是把状态改为“无效”
     *
     * @param DeptId 部门信息主键
     * @return 返回 0 表示操作失败，大于 0 表示成功
     */
    int removeDepartment(String DeptId);

    /**
     * 修改指定的部门信息
     * 这里主要是通过这个对象的 deptId 来找到旧的对象
     * 部门编号可以改，但是编号一旦改了，所有相同子类的父级编号都得改
     * 这要体现在 Service 层
     *
     * @param department 包含修改信息的部门对象
     * @return 返回 0 表示失败，大于 0 表示成功
     */
    int updateDepartment(Department department);

    /**
     * 根据旧的部门编号修改成新的部门编号
     * 主要是配合上面的一个修改操作
     * 如果这两个编号相同，也就是没有改动编号，这个操作应该要避免
     *
     * @param oldDeptPCode 旧的部门编号
     * @param newDeptPCode 新的部门编号
     * @return 返回 0 表示失败，大于 0 表示成功
     */
    int updateDepartmentPCode(@Param("oldDeptPCode") String oldDeptPCode, @Param("newDeptPCode") String newDeptPCode);

    /**
     * 统计这个部门编号的个数，正常应该是 0 或 1 个
     *
     * @param deptCode 部门编号
     * @return 返回这个编号出现的次数，正常应该是 0 或 1 个
     */
    int countDepartmentCode(String deptCode);

    /**
     * 将一个用户归类到某一个部门中
     * userId 用户 id
     * deptCode 部门编号
     * userDeptOrder 在部门的排序
     *
     * @param userDepartment 要被保存的用户部门关系
     * @return 返回 0 表示失败，大于 0 表示成功
     */
    int saveUserInDepartment(UserDepartment userDepartment);

    /**
     * 获得部门编号为 deptCode 下的所有用户
     *
     * @param deptCode 指定的部门编号
     * @return 返回这个部门下的所有用户
     */
    List<UserDepartment> listUsersInDepartment(String deptCode);

    /**
     * 当 t_dept 表中的部门编号改变之后，所有相关联的编号都要跟着改
     *
     * @param oldDeptCode 原来的部门编号，主要是用来找到要改动的信息
     * @param newDeptCode 新的部门编号
     * @return 返回 0 表示失败，大于 0 表示成功
     */
    int updateDepartmentCode(@Param("oldDeptCode") String oldDeptCode, @Param("newDeptCode") String newDeptCode);

    /**
     * 更新 t_user_department 表中的信息
     * 根据 userDepartment 中的 id 来找到具体的信息
     *
     * @param userDepartment 包含更新的信息
     * @return 返回 0 表示失败，大于 0 表示成功
     */
    int updateUserInUserDepartment(UserDepartment userDepartment);

    /**
     * 通过具体信息的 id 值找到具体信息，然后移除
     *
     * @param userDeptId 具体信息主键
     * @return 返回 0 表示失败，大于 0 表示成功
     */
    int removeUserInUserDepartment(String userDeptId);

    /**
     * 列出不属于这个部门的用户，方便添加业务
     *
     * @param deptCode 部门编号
     * @return 返回用户列表
     */
    List<User> listUsers(String deptCode);

    /**
     * 查看这个关系是否存在了
     *
     * @param userDepartment 这个关系
     * @return 返回 0 说明不存在，这是可用状态
     */
    int countUserDept(UserDepartment userDepartment);
}
