package com.bluestar.teach.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bluestar.teach.entity.Role;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RoleDaoTest {
	@Autowired
	private RoleDao roleDao;
	@Test
	public void test() {
//		List<Integer> list = new ArrayList<>();
//		list.add(1);
//		list.add(2);
//		List<PowerId> powerIds = ListUtil.integers2Powers(list);
//		RolePower rolePower = new RolePower();
//		rolePower.setPowerIds(powerIds);
//		rolePower.setRoleId(4);
//		roleDao.insertRolePower(rolePower);
//		Role role = new Role();
//		role.setRole("老师");
//		roleDao.insertRole(role);
//		System.out.println(role.getRoleId());
		
		
		List<Role> list = roleDao.getRolesPowerName();
		System.out.println(list);
	}

}
