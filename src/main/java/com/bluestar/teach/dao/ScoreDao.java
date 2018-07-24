package com.bluestar.teach.dao;
/**
 * 成绩方法
 * @author happyChicken
 *
 */

import java.util.List;

import javax.net.ssl.SSLEngineResult.Status;
import javax.swing.ListModel;

import org.apache.ibatis.annotations.Param;

import com.bluestar.teach.entity.Score;
import com.bluestar.teach.entity.ScoreData;
import com.bluestar.teach.entity.User;

public interface ScoreDao {
	
	/**
	 * 插入一条成绩        
	 * @param score 成绩类
	 * @return 影响的行数，如果是1则插入成功 
	 */ 
	public Integer insertScore(Score score);    
	
	/**
	 * 根据班级id得到该班级的成绩
	 * @return 成绩集合
	 */
	public List<ScoreData> getScoresByClassId(Integer classId);
	
	/**
	 * 根据userId得到一个学生的成绩 
	 * @return 一个学生的成绩    
	 */
	public List<Score> getScoreByUserId(Integer userId);  
	
	/**
	 * 得到所有成绩，用于管理员查看所有学员成绩
	 * @return 所有成绩的集合
	 */
	public List<Score> getAllScores(); 
	
	/**
	 * 更新成绩
	 * @param score 成绩对象
	 * @return 影响的行数，如果是1则更新成功
	 */
	public Integer updateScoreByUserIdAndClassIdAndStatus(Score score); 
	

	
	/**
	 * 根据用户id得到成绩数据
	 * @param userId
	 * @return 成绩数据集合
	 */
	public List<ScoreData> getScoreDatasByUserId(Integer userId);
	
	/**
	 * 根据班级和阶段得到成绩数据
	 * @param status
	 * @return 成绩数据集合
	 */
	public List<ScoreData> getScoreDatasByClassIdAndStatus(@Param("status")Integer status,
														   @Param("classId")Integer classId);
	
	/**
	 * 得到所有的成绩数据
	 * @return 成绩数据集合
	 */
	public List<ScoreData> getAllScoreDatas();
	
	/**
	 * 根据阶段查询成绩
	 * @param status 阶段
	 * @return 成绩数据集合
	 */
	public List<ScoreData> getScoreDatasByStatus(Integer status);
	
	/**
	 * 根据老师所在班级查询班级的成绩
	 * @param useId 用户id  用户得到所属班级
	 * @return 成绩数据集合
	 */
	public List<ScoreData> getScoreDatasByHisClassId(Integer useId);
	
	/**
	 * 根据老师所在班级和课程id查询班级成绩
	 * @param userId 用户id
	 * @param status 阶段id
	 * @return 成绩数据集合
	 */
	public List<ScoreData> getScoreDatasByStatusAndHisClassId(@Param("userId")Integer userId,
														 	  @Param("status")Integer status);
	
	/**
	 * 根据成绩id更新成绩
	 * @param score 成绩
	 * @return
	 */
	public Integer updateScoreByScoreId(Score score);
	
	/**
	 * 根据成绩id删除成绩
	 * @param scoreId 成绩id
	 * @return
	 */
	public Integer deleteScoreByScoreId(Integer scoreId);
	
	/**
	 * 根据班级id得到用户
	 * @param classId 班级id
	 * @return 用户集合
	 */
	public List<User> getUsersByClassId(Integer classId);
	
	/**
	 * 批量插入成绩
	 * @param scores 成绩集合
	 * @return 
	 */
	public Integer insertScores(@Param("scores")List<Score> scores);
	
	/**
	 * 得到考试合格百分比
	 * @param classId 班级id
	 * @param status 阶段
	 * @return 百分比
	 */
	public Double getPassNumberForAdmin(@Param("classId")Integer classId, @Param("status")Integer status);
	
	public Double getPassNumberForTeacher(@Param("classId")Integer classId, @Param("status")Integer status,@Param("userId")Integer userId);
	
}
