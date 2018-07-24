package com.bluestar.teach.service;

import com.bluestar.common.service.InitService;
import com.bluestar.teach.dto.AccountDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 初始化业务测试类
 *
 * @author Fish
 * created by 2018-05-10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-service.xml",
        "classpath:spring/spring-dao.xml"
})
public class InitServiceTest
{
    @Autowired
    private InitService initService = null;

    @Test
    public void testGetAllCourses()
    {
        AccountDto accountDto = initService.getAllCourses();
        System.out.println(accountDto.getData());
    }

    @Test
    public void testGetQuiz()
    {
        System.out.println(initService.getQuiz());
    }
}
