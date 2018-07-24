package com.bluestar.teach.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bluestar.teach.entity.Sign;
import com.bluestar.teach.entity.SignData;

/**
 * 签到方法
 * @author happyChicken
 *
 */
public interface SignDao {
	
	/**
	 * 插入一个签到->签到表
	 * @param sign 签到状态
	 * @return	影响的行数，如果是1则签到成功
	 */
	public Integer insertIntoSign(Sign sign);
	
	/**
	 * 根据用户id查询签到情况
	 * @param userId 用户id
	 * @return 签到情况集合
	 */
	public List<Sign> getSignsByUserId(Integer userId);
	
	/**
	 * 根据班级id和角色id得到老师或学生的签到情况
	 * @param classId 班级id
	 * @param roleId  角色id
	 * @return 班级学生签到集合
	 */
	public List<Sign> getSignsByClassIdAndRoleId(@Param("classId")Integer classId, 
												 @Param("roleId")Integer  roleId);
	
	/**
	 * 得到所有的签到情况
	 * @return 所有签到情况集合
	 */
	public List<Sign> getAllSigns();
	
	/**
	 * 根据班级id得到老师和学生的签到情况
	 * @param classId 班级id
	 * @return 签到集合
	 */
	public List<SignData> getSignsByClassId(Integer classId);
	
	/**
     * 得到某个班级某种角色的签到数据
     * @param classId 班级id
     * @return 返回班级签到数据
     */
    public List<SignData> getSignDatasByClassIdAndRoleId(@Param("classId")Integer classId, 
			 											 @Param("roleId")Integer  roleId);
    
    /**
     * 得到某个用户的签到数据
     * @param userId 用户id
     * @return 返回用户签到数据
     */
    public List<SignData> getSignDatasByUserId(Integer userId);
    
    /**
     * 得到某个班级的签到数据
     * @param classId 班级id
     * @return 签到数据
     */
    public List<SignData> getSignDatasByClassId(Integer classId);
    
    /**
     * 得到全部的签到数据
     * @return 签到数据集合
     */
    public List<SignData> getAllSignDatas();
    
    /**
     * 根据课程id得到签到数据
     * @param courseId 课程id
     * @return 签到数据集合
     */ 
    public List<SignData> getSignDatasByCourseIdAndRoleId(@Param("courseId")Integer courseId,
    													  @Param("roleId")Integer roleId);
    
    /**
     * 根据课程id和班级id得到签到数据
     * @param classId 班级id
     * @param courseId 课程id
     * @return 签到数据集合
     */
    public List<SignData> getSignDatasByClassIdAndCourseId(@Param("classId")Integer classId,
    													   @Param("courseId")Integer courseId);
    
    /**
     * 根据课程查询签到情况
     * @param courseId 课程id
     * @return 签到数据集合
     */
    public List<SignData> getSignDatasByCourseId(Integer courseId);
    
    /**
     * 根据老师所属班级得到签到数据
     * @param userId 用户id
     * @return 签到数据
     */
    public List<SignData> getSignDatasByHisClassId(Integer userId);
    
    /**
     * 根据老师所属班级和课程得到签到数据
     * @param userId 用户id 
     * @param courseId 课程id
     * @return 签到数据集合
     */
    public List<SignData> getSignDatasByCourseIdAndHisClassId(@Param("userId")Integer userId,
			   												  @Param("courseId")Integer courseId);

    public int signIsExisted(@Param("userId") Integer userId, @Param("courseId") Integer courseId);
    
    /**
	 * 得到学生签到成功百分比
	 * @param classId 班级id
	 * @param courseId 课程id
	 * @return 百分比
	 */
	public Double getStudentSignSuccessNumber(@Param("classId")Integer classId,@Param("courseId")Integer courseId);
	
	
	
	/**
	 * 得到学生迟到百分比
	 * @param classId 班级id
	 * @param courseId 课程id
	 * @return 百分比
	 */
	public Double getStudentSignLateNumber(@Param("classId")Integer classId, @Param("courseId")Integer courseId);
	
	/**
	 * 得到老师签到成功百分比
	 * @param classId 班级id
	 * @param courseId 课程id
	 * @return 百分比
	 */
	public Double getTeacherSignSuccessNumber(@Param("classId")Integer classId,@Param("courseId")Integer courseId);
   
	/**
	 * 得到老师迟到百分比
	 * @param classId 班级id
	 * @param courseId 课程id
	 * @return 百分比
	 */
	public Double getTeacherSignLateNumber(@Param("classId")Integer classId,@Param("courseId")Integer courseId);
}
