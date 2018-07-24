package com.bluestar.information.dao;

import com.bluestar.information.entity.Information;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * @author: MackyHuang
 * @eamil: 973151766@qq.com
 * @createTime: 2018/7/16 15:40
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class InformationDaoTest {

    @Resource
    private InformationDao informationDao;

    @Test
    public void getInformationById() {
        Information information = informationDao.getInformationById("1001");
        System.out.println(information);
    }

    @Test
    public void listInformationByStatu() {
        String statu = "1";
        List<Information> informations = informationDao.listInformationByStatu(null, "i");
        for (Information information: informations) {
            System.out.println(information);
        }
    }

    @Test
    public void saveInformation() {
        Date nowTime = new Date();
        Information information = new Information("1003",
                "JavaSpring", "This is a good frameworks", "fu1001",
                "mackyhuang", nowTime, nowTime, "mackyhuang",
                "1", 888);
        int result = informationDao.saveInformation(information);
        System.out.println(result);
    }

    @Test
    public void updateInformation() {
        Date nowTime = new Date();
        Information information = new Information("1002",
                "IOS", "I want to learn it very well", "fu1001",
                "mackyhuang", nowTime, nowTime, "mackyhuang",
                "1", 888);
        int result = informationDao.updateInformation(information);
        System.out.println(result);
    }

    @Test
    public void removeInformatin() {
        int result = informationDao.removeInformatin("1003");
        System.out.println(result);
    }

    @Test
    public void listInformationByTitle() {
        String informationTitle = "I";
        List<Information> informations = informationDao.listInformationByTitle(informationTitle);
        for (Information information: informations) {
            System.out.println(information);
        }
    }

    @Test
    public void updateStatuByid() {
        int result = informationDao.updateStatuByid("1001", "2");
        System.out.println(result); 
    }
}