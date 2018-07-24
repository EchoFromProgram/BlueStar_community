package com.bluestar.teach.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 成绩业务测试类
 *
 * @author Fish
 * created by 2018-06-02 12:33
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class ScoreServiceTest {

    @Autowired
    private ScoreService scoreService = null;

    @Test
    public void testGetScoreRate() {
        System.out.println(scoreService.getScoreRate(null, null, null));
    }
}
