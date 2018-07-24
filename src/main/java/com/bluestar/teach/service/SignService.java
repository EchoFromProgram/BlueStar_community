package com.bluestar.teach.service;

import com.bluestar.teach.dto.AccountDto;
import com.bluestar.teach.entity.Clazz;
import com.bluestar.teach.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 签到相关业务
 *
 * @author Fish
 * */
public interface SignService
{
    /**
     * 获取到一个随机的签到码
     *
     * @return 返回生成的签到码
     */
    public int getSignCode();

    /**
     * 获取用户所属班级，签到时需要老师指定现在在哪个班级上课
     *
     * @param user 指定的用户
     * @return 返回这个用户所属的班级
     */
    public AccountDto getClasses(User user);

    /**
     * 签到方法，传入一个对象和验证码
     *
     * @param user   要签到的用户
     * @param classId  用户所属班级
     *               如果用户没有班级，那就传入一个 null 即可
     * @param reason * 签到原因，（选填）
     *               如果是迟到或者旷课就必须填写
     * @return 返回签到情况
     */
    public AccountDto sign(User user, Integer inputCode, Integer realCode, Integer classId, String reason);

    /**
     * 老师签到
     *
     * @param user    要签到的用户
     * @param classId 用户所属班级
     *                如果用户没有班级，那就传入一个 null 即可
     * @param reason  * 签到原因，（选填）
     *                如果是迟到或者旷课就必须填写
     * @return
     */
    public AccountDto sign(User user, Integer classId, String reason);

    /**
     * 根据班级信息获取签到信息
     *
     * @param pageNumber 要显示的页数
     * @param clazz 指定的班级
     * @return 返回指定的签到信息
     * */
    public AccountDto getClassSigns(Integer pageNumber, Clazz clazz);

    /**
     * 获取指定班级的老师的签到信息
     *
     * @param pageNumber 要显示的页数
     * @param clazz 指定班级
     * @return 返回指定班级的老师签到信息
     * */
    public AccountDto getTeacherSignsByClass(Integer pageNumber, Clazz clazz);

    /**
     * 获取指定班级的学生的签到信息
     *
     * @param pageNumber 要显示的页数
     * @param clazz 指定班级
     * @return 返回指定班级的学生的签到信息
     * */
    public AccountDto getStudentSignsByClass(Integer pageNumber, Clazz clazz);

    /**
     * 获取整个签到表的信息
     *
     * @param pageNumber 要显示的页数
     * @return 返回整个签到表
     * */
    public AccountDto getSigns(Integer pageNumber);

    /**
     * 获取这个用户的所有签到信息
     *
     * @param pageNumber 要显示的页数
     * @param user 要被查询的用户
     * @return 返回这个用户的签到信息
     * */
    public AccountDto getSignsByUser(Integer pageNumber, User user);
    
    /**
     * 根据课程获取学生签到信息
     * @param pageNumber 显示的页数
     * @param courseId 课程id
     * @return 返回这个用户的签到信息
     */
    public AccountDto getStudentSignsByCourseId(Integer pageNumber,Integer courseId);
    
    /**
     * 根据课程获取老师签到信息
     * @param pageNumber 显示的页数
     * @param courseId 课程id
     * @return 返回这个用户的签到信息
     */
    public AccountDto getTeacherSignsByCourseId(Integer pageNumber,Integer courseId);
    
    /**
     * 根据班级和课程获得签到信息
     * @param pageNumber 显示的页数
     * @param courseId 课程id
     * @param classId 班级id
     * @return 返回签到信息
     */
    public AccountDto getSignsByCouseIdAndClassId(Integer pageNumber, Integer courseId, Integer classId);

    /**
     * 通过 userId 获取签到信息
     *
     * @param pageNumber 页数
     * @param userId     用户 id
     * @return 返回签到信息
     */
    public AccountDto getSignsByUserId(Integer pageNumber, Integer userId);

    /**
     * 通过用户 id 和课程 id 查询签到信息
     *
     * @param pageNumber 页数
     * @param userId     用户 id
     * @param courseId   课程 id
     * @return 返回签到信息
     */
    public AccountDto getSignsByCourseIdAndHisClassId(Integer pageNumber, Integer userId, Integer courseId);

    /**
     * 通过课程 id 获取签到信息
     *
     * @param pageNumber 页数
     * @param courseId   课程 id
     * @return 返回课程信息
     */
    public AccountDto getSignsByCourseId(Integer pageNumber, Integer courseId);

    /**
     * 通过学生 id 得到老师 id
     *
     * @param studentId 学生 id
     * @return 返回老师 id
     */
    public Integer getTeacherId(Integer studentId);

    /**
     * 得到签到统计信息，比率，0 - 1 之间
     *
     * @param classId  班级 id
     * @param courseId 课程 id
     * @return 返回签到情况
     */
    public AccountDto getSignRate(Integer classId, Integer courseId);
}
