package com.bluestar.teach.dao;

import java.util.List;
import java.util.Map;

import com.bluestar.teach.entity.Role;
import com.bluestar.teach.entity.RolePower;
import com.bluestar.teach.entity.RolePowerName;

/**
 * 角色管理方法
 * 
 * @author 王鸿175
 *
 */
public interface RoleDao {
	
	/**
	 * 得到角色名以及所拥有的权限名
	 * @return
	 */
	public List<Role> getRolesPowerName();
	
	/**
	 * 删除对应角色的所有权限，更新第一步
	 * @param roleId 角色id
	 * @return 删除行数
	 */
	public Integer deleteRolePowerByRoleId(Integer roleId);
	
	/**
	 * 插入角色对应的权限,新增角色第二步，更新第二步
	 * @param rolePower 角色-权限类
	 * @return 影响行数
	 */
	public Integer insertRolePower(RolePower rolePower);
	
	/**
	 * 插入一个角色，新增一个角色第一步，需要返回roleId
	 * @param role 角色类
	 * @return 影响行数
	 */
	public Integer insertRole(Role role);
	
	/**
	 * 联表删除一个角色以及对应的权限
	 * @param roleId 角色id
	 * @return 影响行数
	 */
	public Integer deleteRole(Integer roleId);
}
