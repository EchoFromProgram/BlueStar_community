package com.bluestar.teach.service;


import org.apache.ibatis.annotations.Param;

import com.bluestar.teach.dto.AccountDto;
import com.bluestar.teach.entity.Role;
import com.bluestar.teach.entity.RolePower;

/**
 * 角色管理业务接口
 * @author 王鸿175
 *
 */
public interface RoleService {
	
	/**
	 * 得到当前所有的角色已经他们的权限
	 * 
	 * @return 用户集合
	 */
	public AccountDto getRoles();
	
	/**
	 * 根据角色id联表删除角色
	 * @param roleId 角色id
	 * @return 删除结果
	 */
	public AccountDto deleteRole(Integer roleId);
	
	/**
	 * 新建角色，先插入role，再插入role-power
	 * @param role 角色
	 * @param rolePower 角色权限
	 * @return 插入结果
	 */
	public AccountDto insertRole(Role role, RolePower rolePower);
	
	/**
	 * 修改角色权限，先根据角色id删除原有权限，再插入新的权限
	 * @param roleId 角色id
	 * @param rolePower 角色权限
	 * @return 修改结果
	 */
	public AccountDto updateRole(RolePower rolePower);
}
