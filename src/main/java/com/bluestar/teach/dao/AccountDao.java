package com.bluestar.teach.dao;

import java.util.List;
import java.util.Map;

import javax.jws.soap.SOAPBinding.Use;

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
import org.apache.ibatis.annotations.Param;

/**
 * 该方法用于管理用户
 *
 * @author happyChicken
 */
public interface AccountDao
{
	
	/**
	 * 根据记录数判断用户名是否存在
	 * @param username 传入一个用户名，用来判断用户名是否存在
	 * @return 返回记录数，1是用户名已存在，0是不存在
	 */
	public Integer userNameIsExit(String username);
	
    /**
     * 登录的方法，通过传入的用户名得到一个用户(user)对象
     * @param username 登录时传入的用户名
     * @return 返回一个用户（user）对象
     */
    public Map<String, Object> getUserByUserName(String username);

    /**
     * 创建一个新用户
     * @param user 传入一个用户类，用来创建一个新用户
     * @return 返回影响的行数，如果是1则创建成功
     */
    public Integer insertIntoUser(User user);
    
    /**
     * 插入用户所属班级
     * @param userClass 用户班级类
     * @return 影响的行数，如果是1则创建成功
     */
    public Integer insertUserClass(UserClass userClass);
    
    /**
     * 向客户信息表插入一条客户信息
     * @param customer 客户
     * @return 返回影响的行数
     */
    public Integer inserIntoCustomer(Customer customer);
    
    /**
     * 向员工信息表插入一条员工信息
     * @param staff 员工
     * @return 返回影响的行数
     */
    public Integer insertIntoStaff(Staff staff);
   /**
    * 设置员工信息
    * @param staff 传入一个员工类，用来设置员工信息
    */
    public Integer settingStaffInfo(Staff staff);

    /**
     * 设置客户信息
     * @param customer 传入一个客户类，用来设置客户信息
     */
    public Integer settingCustomerInfo(Customer customer);
    
    /**
     * 查询一个员工的具体信息
     * @param tId 对应的信息id
     * @return 返回一个查询到的员工信息
     */
    public Staff getStaffDetailByTid(Integer tId);
    
    /**
     * 查询一个客户的具体信息
     * @param infoId 对应的信息id
     * @return 返回一个查询到的客户信息
     */
    public Customer getCustomerDetailByInfoId(Integer infoId);
    
    /**
     * 根据角色id查询对应的权限集合
     * @param roleId
     * @return 返回一个权限集合
     */
    public List<Integer> getPowerIdByRoleId(Integer roleId);
    
    /**
     * 得到整张权限表
     * @return 包含多个权限对象的集合
     */
    public List<Power> getPowers();
    
    
    /**
     * 通过用户id得到班级id
     * @param staffId 员工id，即信息id
     * @return 班级id集合
     */
    public List<Integer> getClassIdsByUserId(Integer userId);
    
    
    /**
     * 通过班级id得到对应的班级
     * @param classId 班级id
     * @return 班级对象
     */
    public Clazz getClassByClassId(Integer classId);
    
    /**
     * 通过课程id得到对应的课程
     * @param courseId 课程id
     * @return 课程集合
     */
    public Course getCoursesByCourseId(Integer courseId);
    
    /**
     * 得到所有用户
     * @return 用户集合
     */
    public List<User> getAllUsers();

 /**
  * 根据类型、名字查询用户
  *
  * @param typeId 类型id
  * @param name 姓名
  * @return 用户列表
  */
 public List<Map<String, Object>> getUsersByTypeIdAndName(@Param("typeId") Integer typeId,
                                                             @Param("name") String name);
    
    /**
     * 得到所有的省份
     * @return 省份集合
     */
    public List<Province> getProvinces();
    
    /**
     * 根据省份id得到对应的城市
     * @param provinceId 省份id
     * @return 城市集合
     */
    public List<City> getCitysByProvinceId(Integer provinceId);
    
    /**
     * 根据城市id得到对应的学校
     * @param cityId
     * @return 学校集合
     */
    public List<School> getSchoolsByCity(String city);
    
    
    /**
     * 得到整张课程表
     * @return 课表集合
     */
    public List<Course> getAllcourses();
    
    /**
     * 根据学生的userId得到他老师的userId
     * @param userId 学生的userId
     * @return 
     */
    public int getHisTeacherUserId(Integer userId);
    
    /**
     * 新增一个班级 
     * @param clazz 班级类
     * @return 影响的行数，如果是1则插入成功
     */
    public Integer insertClass(Clazz clazz);
    
    /**
     * 根据用户id删除用户所在班级，用户更新
     * @param userId 用户id
     * @return 删除的行数
     */
    public Integer deleteUserClass(Integer userId);
    
    /**
     * 更新用户
     * @param user
     * @return
     */
    public Integer updateUser(User user);
    
    /**
     * 根据班级名得到该班级
     * @param className 班级名
     * @return 班级
     */
    public Clazz getClassByClassName(String className);

    /**
     * 得到全部班级，主要是给管理员用
     *
     * @return 返回班级列表
     */
    public List<Clazz> getAllClasses();
    
    /**
     * 根据用户名得到用户
     * @param userName 用户名
     * @return 用户
     */
    public UserData getUserByUserNameForUpdate(String username);
   
    /**
     * 根据用户名得到管理员
     * @param username 用户名
     * @return 管理员
     */
    public UserData getAdminByUserNameForUpdate(String username);
    
    /**
     * 得到类型和角色id
     * @param username 用户名
     * @return 用户类
     */
    public User getTypeIdAndRoleId(String username);
    
    /***
     * 获得所有用户列表
     * @return 用户列表
     */
    public List<Map<String, Object>> getUsersNotByTypeId();
}
