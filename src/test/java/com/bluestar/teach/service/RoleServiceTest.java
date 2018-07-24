package com.bluestar.teach.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import com.bluestar.teach.dto.AccountDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class RoleServiceTest {
	@Autowired
	private RoleService roleService;
	
	@Test
	public void test() {
//		Role role = new Role();
//		role.setRole("3333");
//		List<Integer> list = new ArrayList<>();
//		list.add(4);
//		list.add(8);
//		RolePower rolePower = new RolePower();
//		rolePower.setPowerIds(list);
//		roleService.insertRole(role, rolePower);
	
		AccountDto accountDto = roleService.deleteRole(12);
	}

}
