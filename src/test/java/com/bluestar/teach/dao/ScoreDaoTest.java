package com.bluestar.teach.dao;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bluestar.teach.entity.Score;
import com.bluestar.teach.entity.ScoreData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class ScoreDaoTest {
	
	@Autowired
	private ScoreDao scoreDao;
	
	@Test
	public void testInsertScore() {
		Score score = new Score();
		score.setClassId(1);
		
		score.setDate(new Date());
		score.setScore(50);
		score.setUserId(1);
		int num = scoreDao.insertScore(score);
		System.out.println(num);
	}

	@Test
	public void testGetScoresByClassId() {
		List<ScoreData> list = scoreDao.getScoresByClassId(1);
		System.out.println(list);
	}

	@Test
	public void testGetScoreByUserId() {
		List<Score> list  = scoreDao.getScoreByUserId(1);
		System.out.println(list);
	}

	@Test
	public void testGetAllScores() {
		List<Score> list = scoreDao.getAllScores();
		System.out.println(list);
	}
	
	@Test
	public void testUpDateScoreByUserIdAndCourseId() {
		Score score = new Score();
		
		score.setUserId(1);
		score.setScore(62);
		score.setStatus(1);
		score.setClassId(1);
		int num = scoreDao.updateScoreByUserIdAndClassIdAndStatus(score);
		System.out.println(num);
		System.out.println(score.getScoreId());
	}
	
	@Test
	public void testA() {
		double double1 = scoreDao.getPassNumberForTeacher(2, 1, 1);
		System.out.println(double1);
	}
	
	
	
	
	
}
