package com.bluestar.teach.service;

import com.bluestar.teach.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 签到业务测试类
 *
 * @author Fish
 * created by 2018-05-17 20:38
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class SignServiceTest {
    @Autowired
    private SignService signService = null;

    @Test
    public void testSign() {
        User user = new User();
        user.setUserId(4);
        int code = signService.getSignCode();
        System.out.println(signService.sign(user, code, code, 2, "哈哈哈，日常迟到！"));
    }

    @Test
    public void testGetSignRate() {
        System.out.println(signService.getSignRate(2, null));
    }
}
