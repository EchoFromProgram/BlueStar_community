package com.bluestar.teach.service;

import com.bluestar.teach.entity.Clazz;
import com.bluestar.teach.entity.Customer;
import com.bluestar.teach.entity.Staff;

import org.springframework.stereotype.Service;

import com.bluestar.teach.dto.AccountDto;
import com.bluestar.teach.entity.User;
import com.bluestar.teach.entity.UserClass;

import java.util.List;

/**
 * 账号相关业务类
 *
 * @author Fish
 */
public interface AccountService
{
    /**
     * 登陆业务，传入一个用户对象进行登陆
     *
     * @param user 要登陆的用户
     * @return 返回传输包装类，包含所有需要的信息
     */
    public AccountDto login(User user);

    /**
     * 验证用户名是否可用
     * 这里的可用有两种含义：
     * 1）登陆的时候：这个方法用于检测账号是否正确，在数据库中是否存在
     * 2）创建用户的时候：这个方法用于检测账号是否已经存在，存在时不允许创建这个账号
     *
     * @param username 用于验证的账号
     * @return true 账号存在，false 账号不存在
     */
    public boolean userNameExisted(String username);

    /**
     * 创建一个用户
     * @param user 用户类
     * @param userClass 用户班级类
     * @return 创建结果
     */
    public AccountDto createAccount(User user, UserClass userClass);

    /**
     * 得到所有的账户信息，主要供管理员使用
     *
     * @param pageNumber 获取第几页的数据
     * @return 返回所有账户的信息
     */
    public AccountDto getAllAccounts(Integer pageNumber);

    /**
     * 通过 roleId 获取用户信息，目前主要是内部和外部
     *
     * @param pageNumber 页数
     * @param typeId     用户类型
     * @return 返回用户信息 
     */
    public AccountDto getAccounts(Integer pageNumber, Integer typeId, String name);
    
    /**
     * 根据信息id查询客户的信息
     * @param infoId 信息id
     * @return 返回客户的信息
     */
    public AccountDto getCustomerInfoByInfoId(Integer infoId);
    
    /**
     * 根据信息id得到员工的信息
     * @param infoId
     * @return 返回员工信息
     */
    public AccountDto getStaffInfoByInfoId(Integer infoId);
    
    /**
     * 根据信息id更新客户信息
     * @param customer 客户信息
     * @return 处理信息状态
     */
    public AccountDto updateCustomerInfoByInfoId(Customer customer);
    
    /**
     * 根据信息id更新员工信息
     * @param staff 员工信息
     * @return 处理信息状态
     */
    public AccountDto updateStaffInfoByInfoId(Staff staff);
    
    /**
     * 得到所有的省份
     * @return 返回省份集合
     */
    public AccountDto getAllProvinces();
    
    /**
     * 根据省份id得到所有的城市
     * @param provinceId 省份id
     * @return 返回城市集合
     */
    public AccountDto getCitysByProvinceId(Integer provinceId);
    
    /**
     * 根据城市id得到所有的学校
     * @param cityId 城市id
     * @return 学校集合
     */
    public AccountDto getSchoolsByCity(String city);
    
    /**
     * 新增一个班级
     * @param clazz 班级类
     * @return 插入结果
     */
    public AccountDto saveClass(Clazz clazz);
    
    /**
     * 修改用户信息
     * @param user 用户类
     * @param userClass 用户班级类
     * @return 修改结果
     */
    public AccountDto updateUser(User user, UserClass userClass);
    
    /**
     * 根据用户名查询用户
     * @param userName
     * @return
     */
    public AccountDto getUserByUserNameForUpdate(String userName);
}
