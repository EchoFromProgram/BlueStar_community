package com.bluestar.teach.service;

import com.bluestar.teach.dto.AccountDto;
import com.bluestar.teach.entity.Clazz;
import com.bluestar.teach.entity.SignData;
import com.bluestar.teach.entity.User;
import com.bluestar.teach.entity.UserClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import aj.org.objectweb.asm.Type;

/**
 * 测试账号业务方法
 *
 * @author Fish
 * */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class AccountServiceTest
{
    @Autowired
    private AccountService accountService = null;

    @Autowired
    private SignService signService = null;

    @Test
    public void testLogin()
    {
        User user = new User();
        user.setUserName("macky");
        user.setPassword("123");

        AccountDto accountDto = accountService.login(user);

        System.out.println(accountDto);
    }

    @Test
    public void testCreateAccount()
    {
//        User user = new User();
//        user.setUserName("西欧I嘻嘻系");
//        user.setPassword("696969");
//        user.setName("dd");
//        user.setRoleId(1);
//        user.setTypeId(0);
//        UserClass userClass = new UserClass();
//        List<Integer> classIds = new ArrayList<>();
//        classIds.add(2);
//        
//        userClass.setClassIds(classIds);
//        AccountDto accountDto = accountService.createAccount(user,userClass);
//
//        System.out.println(accountDto);
    		
      User user = new User();
      user.setPassword("696969");
      user.setName("dd");
      user.setUserId(22);
      user.setRoleId(3);
      user.setTypeId(0);
      
      UserClass userClass = new UserClass();
      List<Integer> classIds = new ArrayList<>();
      userClass.setClassIds(classIds);
      AccountDto accountDto = accountService.updateUser(user, userClass);
      System.out.println(accountDto);
    }
    
    @Test
    public void testPageInfo() {
    	AccountDto<PageInfo<User>> accountDto = accountService.getAllAccounts(88);
    	System.out.println((accountDto.getData()));
    }

    @Test
    public void testGetSignsByClassId()
    {
        AccountDto<PageInfo<SignData>> signs = signService.getSignsByUserId(1, 2);
        System.out.println(signs);
    }
    
    @Test
    public void testInsert() {
    	Clazz clazz = new Clazz();
		clazz.setClassName("3.班");
		accountService.saveClass(clazz);
    }

    @Test
    public void testGetUser(){
        AccountDto accountDto = accountService.getAccounts(1,null,"m");
        System.out.println(accountDto);
    }
}
