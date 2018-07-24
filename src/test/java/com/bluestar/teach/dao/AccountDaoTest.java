package com.bluestar.teach.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.aspectj.bridge.MessageWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysql.jdbc.jdbc2.optional.SuspendableXAConnection;

import com.bluestar.teach.entity.City;
import com.bluestar.teach.entity.Clazz;
import com.bluestar.teach.entity.Course;
import com.bluestar.teach.entity.Customer;
import com.bluestar.teach.entity.Power;
import com.bluestar.teach.entity.Province;
import com.bluestar.teach.entity.School;
import com.bluestar.teach.entity.SignData;
import com.bluestar.teach.entity.Staff;
import com.bluestar.teach.entity.User;
import com.bluestar.teach.entity.UserClass;
import com.bluestar.teach.entity.UserData;
import com.bluestar.teach.service.AccountService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class AccountDaoTest {

	@Autowired
	public AccountDao accountDao;
	
	@Test
	public void testUserNameIsExit() {
		int num = accountDao.userNameIsExit("2222");
		System.out.println(num);
	}

	@Test
	public void testGetUserByUserName() {
		Map<String, Object> user = accountDao.getUserByUserName("Fish");
		System.out.println(user);
	}

	@Test
	public void testCreateAccount() {
		
		Customer customer = new Customer();
		accountDao.inserIntoCustomer(customer);
		System.out.println(customer.getInfoId());
	}

	@Test
	public void testSettingStaffInfo() {
		Staff staff = new Staff();
		staff.setQq("1112");
		staff.settId(1);
		staff.setEmail("131");
		accountDao.settingStaffInfo(staff);
	}

	@Test
	public void testSettingCustomerInfo() {
		Customer customer = new Customer();
		customer.setEmail("1133");
		customer.setInfoId(1);
		customer.setCity("福州");
		accountDao.settingCustomerInfo(customer);
	}
	
	@Test
	public void testGetPowerIdByRoleId() {
		int roleId = 2;
		List<Integer> list = accountDao.getPowerIdByRoleId(roleId);
		System.out.println(list);
	}
	
	@Test
	public void testGetPowers() {
		List<Power> list = accountDao.getPowers();
		System.out.println(list);
	}
	
	@Test
	public void testGetStaffDetailByTid() {
		Staff staff = accountDao.getStaffDetailByTid(1);
		System.out.println(staff);
	}
	
	@Test
	public void testGetCustomerDetailByInfoId() {
		Customer customer = accountDao.getCustomerDetailByInfoId(1);
		System.out.println(customer);
	}
	

	
	@Test
	public void testGetCoursesByCourseId() {
		Course course = accountDao.getCoursesByCourseId(2);
		System.out.println(course);
	}
	
	@Test 
	public void testGetClassesIdsByClassId() {
		Clazz class1 = accountDao.getClassByClassId(2);
		System.out.println(class1);
		Clazz class2 = accountDao.getClassByClassId(2);
		
	}
	
	@Test
	public void testGetClassIdsByStaffId() {
		List<Integer> list = accountDao.getClassIdsByUserId(1);
		System.out.println(list);
	}
	
	@Test
	public void testGetAllUsers() {
		List<User> list = accountDao.getAllUsers();
		System.out.println(list);
	}
	
	
	@Test
	public void test1() {

		List<Map<String,Object>> list = accountDao.getUsersByTypeIdAndName(null,null);
		System.out.println(list);
	}
	
	

}
