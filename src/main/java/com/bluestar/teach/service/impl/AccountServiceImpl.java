package com.bluestar.teach.service.impl;

import com.bluestar.common.utils.CodeUtil;
import com.bluestar.teach.constant.Role;
import com.bluestar.teach.constant.Type;
import com.bluestar.teach.dao.AccountDao;
import com.bluestar.teach.dto.AccountDto;
import com.bluestar.teach.entity.*;
import com.bluestar.teach.enums.Statusable;
import com.bluestar.teach.enums.impl.Common;
import com.bluestar.teach.enums.impl.CreateAccountStatus;
import com.bluestar.teach.enums.impl.LoginStatus;
import com.bluestar.teach.enums.impl.UpdateAccountStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluestar.teach.service.AccountService;
import com.bluestar.common.utils.PageUtil;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 账号业务实现类
 *
 * @author Fish
 */
@Service
public class AccountServiceImpl implements AccountService {
    // 账号持久层对象
    private AccountDao accountDao = null;
    
    private static final String Message = "创建班级成功";

    @Autowired
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * 登陆业务，传入一个用户对象进行登陆
     *
     * @param user 前台传过来的要登陆的用户
     * @return true 登陆验证通过，false 用户名或密码不正确
     */
    public AccountDto login(User user) {
        // 如果前台给过来的用户名是空的，返回错误提示
        if (user == null || user.getUserName() == null || "".equals(user.getUserName()) ) {
            return new AccountDto(LoginStatus.WRONG_USERNAME);
        }

        // 从数据库中查出这个账号的密码
        Map u = accountDao.getUserByUserName(user.getUserName());

        // 用户名不存在！
        if (u == null) {
            return new AccountDto(LoginStatus.WRONG_USERNAME);
        }

        // 判断前台登陆用户输入的密码和后台数据的密码是否一致
        if (!u.get("password").equals(CodeUtil.getMD5(user.getPassword()))) {
            return new AccountDto(LoginStatus.WRONG_PASSWORD);
        }

        // 登陆成功，将要携带的信息带给前台
        Map<String, Object> infos = new HashMap<String, Object>();
        infos.put("user", u);
        infos.put("hisPowers", accountDao.getPowerIdByRoleId((Integer) u.get("role_id")));

        if ((Integer)u.get("role_id") == Role.ADMIN) {
            infos.put("hisClasses", accountDao.getAllClasses());
            return new AccountDto<>(infos, LoginStatus.SUCCESS);
        }

        Integer[] classIds = accountDao.getClassIdsByUserId((Integer) u.get("user_id")).toArray(new Integer[0]);
        Arrays.sort(classIds); // 给这个列表排序
        List<Clazz> clazzes = new ArrayList<>();
        for (Integer i : classIds) // 通过班级 id 获取班级信息
        {
            clazzes.add(accountDao.getClassByClassId(i));
        }
        infos.put("hisClasses", clazzes);

        return new AccountDto<>(infos, LoginStatus.SUCCESS);
    }

    /**
     * 验证用户名是否可用
     * 这里的可用有两种含义：
     * 1）登陆的时候：这个方法用于检测账号是否正确，在数据库中是否存在
     * 2）创建用户的时候：这个方法用于检测账号是否已经存在，存在时不允许创建这个账号
     *
     * @param username 用于验证的账号
     * @return true 账号存在，false 账号不存在
     */
    public boolean userNameExisted(String username) {
        return accountDao.userNameIsExit(username) > 0;
    }

    /**
     * 创建一个用户，由前台传过来一个新用户
     *
     * @param user 前台传过来的用户
     * @return 返回创建的信息状态
     */
    @Transactional
    public AccountDto createAccount(User user, UserClass userClass) {
        int affect;
        boolean flag = true;//默认是管理员
        
        // 如果前台传了一个空对象过来，创建失败
        if (user == null ||  user.getRoleId() < 0 || user.getTypeId() < 0 ) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }
        
        //如果不是管理员，判断传入的班级是否为空
        if(user.getRoleId() != Role.ADMIN )
        {	
        	flag = false;
        	if (userClass.getClassIds() == null || userClass.getClassIds().size() < 0) {
            return new AccountDto(CreateAccountStatus.CLASS_IS_NULL);
        	}
        }
        
        // 如果这个用户的账号或密码为空，返回提示
        if (user.getUserName() == null || "".equals(user.getUserName())
                || user.getPassword() == null || "".equals(user.getPassword())) {
            return new AccountDto(CreateAccountStatus.CORE_INFO_IS_NULL);
        }

        
        // 如果这个用户的其他信息为空，返回提示
        if (user.getName() == null || "".equals(user.getName())
                || user.getRoleId() == null || user.getTypeId() == null) {
            return new AccountDto(CreateAccountStatus.INFO_IS_NOT_COMPLETED);
        }

        // 当所有信息都填完整了，就进行数据库查询，看看这个用户是否存在
        if (userNameExisted(user.getUserName())) {
            return new AccountDto(CreateAccountStatus.USERNAME_EXISTED);
        }

        // 将密码进行 MD5 加密
        user.setPassword(CodeUtil.getMD5(user.getPassword()));

        // 判断是什么员工
        switch (user.getTypeId()) {
            case Type.INNER_STAFF:
                // 内部员工
                Staff staff = new Staff();
                accountDao.insertIntoStaff(staff);
                user.setInfoId(staff.gettId()); // 填充详细信息 id
                break;
            case Type.OUTTER_CLIENT:
                // 外部客户
                Customer customer = new Customer();
                accountDao.inserIntoCustomer(customer);
                System.out.println(customer);
                user.setInfoId(customer.getInfoId()); // 填充详细信息 id
                break;
        }
        
        //判断学生还是老师，对应的班级数不同
        switch (user.getTypeId()) {
            case Type.OUTTER_CLIENT:
            	//如果是客户，判断班级是否过多
                if (userClass.getClassIds().size() > 1) {
                    return new AccountDto(CreateAccountStatus.CLASS_TOO_MANY);
                }
                affect = accountDao.insertIntoUser(user);
                if (affect <= 0) {
                    return new AccountDto(CreateAccountStatus.UNKNOWN_ERROR);
                }
                userClass.setUserId(user.getUserId());
                affect = accountDao.insertUserClass(userClass);
                if (affect < 0) {
                    return new AccountDto(CreateAccountStatus.UNKNOWN_ERROR);
                }
                break;
                
            case Type.INNER_STAFF:
            	//如果不是管理员
            	if(flag == false){
	                affect = accountDao.insertIntoUser(user);
	                if (affect <= 0) {
	                    return new AccountDto(CreateAccountStatus.UNKNOWN_ERROR);
	                }
	                userClass.setUserId(user.getUserId());
	                affect = accountDao.insertUserClass(userClass);
	                if (affect < 0) {
	                    return new AccountDto(CreateAccountStatus.UNKNOWN_ERROR);
	                }
	                break;
            	}
            	else {
            	 affect = accountDao.insertIntoUser(user);
                 if (affect <= 0) {
                     return new AccountDto(CreateAccountStatus.UNKNOWN_ERROR);
                 }
                 break;
            	}
            	
        }	
        //成功
        return new AccountDto(CreateAccountStatus.SUCCESS);
    }


    /**
     * 得到所有的账户信息，主要供管理员使用
     *
     * @param pageNumber 获取第几页的数据
     * @return 返回所有账户的信息
     */
    @Override
    public AccountDto getAllAccounts(Integer pageNumber) {
        if (pageNumber == null) // 如果参数为空，则返回参数错误
        {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        // pageHelper 中每进行一次分页就要执行一次这个方法
        PageUtil.toPage(pageNumber);

        List<Map<String, Object>> users = accountDao.getUsersNotByTypeId();
        if (users == null || users.size() == 0) // 如果为空，说明没有获取到数据，有可能是系统错误
        {
            return new AccountDto(Common.GET_IS_NULL);
        }

        // 这里如果 users 的元素个数为 0 也算成功，只能说没有成员
        return new AccountDto<>(PageUtil.pageInfo(users), Common.SUCCESS);
    }

    /**
     * 通过 roleId 获取用户信息，目前主要是内部和外部
     *
     * @param pageNumber 页数
     * @param typeId     用户类型
     * @return 返回用户信息
     */
    public AccountDto getAccounts(Integer pageNumber, Integer typeId, String name) {
        if (pageNumber == null ) // 如果参数为空，则返回参数错误
        {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }
        // pageHelper 中每进行一次分页就要执行一次这个方法
        PageUtil.toPage(pageNumber);

        List<Map<String, Object>> users = accountDao.getUsersByTypeIdAndName(typeId,name);

        if (users == null || users.size() == 0) // 如果为空，说明没有获取到数据，有可能是系统错误
        {
            return new AccountDto(Common.GET_IS_NULL);
        }

        // 这里如果 users 的元素个数为 0 也算成功，只能说没有成员
        return new AccountDto<>(PageUtil.pageInfo(users), Common.SUCCESS);
    }

    /**
     * 根据信息id得到信息工具类
     *
     * @param infoId 信息id
     * @param typeId 类型id
     * @return 返回处理结果
     */
    private AccountDto getCustomerOrStaffByInfoId(Integer infoId, Integer typeId) {
        //参数为空
        if (infoId == null) {
            return new AccountDto<>(Common.WRONG_ARGEMENT);
        }
        if (Type.INNER_STAFF == typeId) {
            //得到员工信息
            Staff staff = accountDao.getStaffDetailByTid(infoId);
            if (staff == null) {
                return new AccountDto<>(Common.GET_IS_NULL);
            }
            return new AccountDto<>(staff, Common.SUCCESS);
        } else {
            if (Type.OUTTER_CLIENT == typeId) {
                //得到客户信息
                Customer customer = accountDao.getCustomerDetailByInfoId(infoId);
                if (customer == null) {
                    return new AccountDto<>(Common.GET_IS_NULL);
                }
                return new AccountDto<>(customer, Common.SUCCESS);
            } else {
                return new AccountDto<>(Common.ERROR);
            }
        }
    }

    /**
     * 根据信息id更新员工或客户
     *
     * @param object 信息id
     * @param typeId 类型id
     * @return 处理结果
     */
    private AccountDto updateCustomerOrStaffInfoByInfoId(Object object, Integer typeId) {
        //参数为空
        if (object == null) {
            return new AccountDto<>(Common.WRONG_ARGEMENT);
        }
        if (Type.INNER_STAFF == typeId) {
            //更新员工信息
            int num = accountDao.settingStaffInfo((Staff) object);
            if (num == 0) {
                return new AccountDto<>(Common.ERROR);
            }
            return new AccountDto<>(UpdateAccountStatus.SUCCESS);
        } else {
            if (Type.OUTTER_CLIENT == typeId) {
                //得到客户信息
                int num = accountDao.settingCustomerInfo((Customer) object);
                if (num == 0) {
                    return new AccountDto<>(Common.ERROR);
                }
                return new AccountDto<>(UpdateAccountStatus.SUCCESS);
            } else {
                return new AccountDto<>(UpdateAccountStatus.UPDATE_ERROR);
            }
        }
    }

    /**
     * 得到员工信息
     *
     * @param infoId 信息id
     * @return 处理结果状态
     */
    public AccountDto getStaffInfoByInfoId(Integer infoId) {
        return getCustomerOrStaffByInfoId(infoId, Type.INNER_STAFF);
    }

    /**
     * 得到客户信息
     *
     * @param infoId 信息id
     * @return 处理结果状态
     */
    public AccountDto getCustomerInfoByInfoId(Integer infoId) {
        return getCustomerOrStaffByInfoId(infoId, Type.OUTTER_CLIENT);
    }

    /**
     * 根据信息id更新员工信息
     *
     * @param staff 员工类
     * @return 处理结果
     */
    public AccountDto updateStaffInfoByInfoId(Staff staff) {
        return updateCustomerOrStaffInfoByInfoId(staff, Type.INNER_STAFF);
    }

    /**
     * 根据信息id更新客户信息
     *
     * @param customer 客户类
     * @return 处理结果
     */
    public AccountDto updateCustomerInfoByInfoId(Customer customer) {
        return updateCustomerOrStaffInfoByInfoId(customer, Type.OUTTER_CLIENT);
    }

    /**
     * 得到所有的省份
     *
     * @return 返回省份集合
     */
    @Override
    public AccountDto getAllProvinces() {
        //得到所有省份
        List<Province> provinces = accountDao.getProvinces();
        //获得为空异常
        if (provinces == null) {
            return new AccountDto<>(Common.GET_IS_NULL);
        }
        return new AccountDto<>(provinces,Common.SUCCESS);
    }

    /**
     * 根据省份id得到对应的所有城市
     *
     * @param provinceId 省份id
     * @return 返回城市集合
     */
    @Override
    public AccountDto getCitysByProvinceId(Integer provinceId) {
        //参数为空异常
        if (provinceId == null) {
            return new AccountDto<>(Common.WRONG_ARGEMENT);
        }
        //获得城市
        List<City> citys = accountDao.getCitysByProvinceId(provinceId);
        //得到结果为空异常
        if (citys == null) {
            return new AccountDto<>(Common.GET_IS_NULL);
        }
        return new AccountDto<>(citys, Common.SUCCESS);
    }

    /**
     * 根据城市id得到所有学校
     *
     * @param city 城市id
     * @return 返回城市集合
     */
    @Override
    public AccountDto getSchoolsByCity(String city) {
        //获得参数为空异常
        if (city == null) {
            return new AccountDto<>(Common.WRONG_ARGEMENT);
        }
        List<School> schools = accountDao.getSchoolsByCity(city);
        //得到结果为空异常
        if (schools == null) {
            return new AccountDto<>(Common.GET_IS_NULL);
        }
        return new AccountDto<>(schools, Common.SUCCESS);
    }

    /**
     * 新增班级
     *
     * @param clazz 班级类
     * @return 插入结果
     */
    @Override
    public AccountDto saveClass(Clazz clazz) {
        try {
            if (accountDao.getClassByClassName(clazz.getClassName()) != null) {
                return new AccountDto(CreateAccountStatus.CLASS_EXISTED);
            }
            if (clazz == null || clazz.getClassId() < 0) {
                return new AccountDto(Common.WRONG_ARGEMENT);
            }
            int affect = accountDao.insertClass(clazz);
            if (affect <= 0) {
                return new AccountDto(Common.ERROR);
            }
        } catch (Exception e) {
            return new AccountDto(Common.ERROR);
        }
        return new AccountDto(new Statusable() {
			
			@Override
			public String getInfo() {
				return Message;
			}
			
			@Override
			public int getCode() {
				return 0;
			}
		});
    }

    /**
     * 更新用户
     *
     * @param user      用户类
     * @param userClass 用户班级类
     * @return 更新结果
     */
    @Override
    public AccountDto updateUser(User user, UserClass userClass) {

        int affect;
        boolean flag = true;//默认是管理员
        // 如果前台传了一个空对象过来，创建失败
        if (user == null || user.getUserId() == null || user.getRoleId() < 0 || user.getTypeId() < 0 || user.getUserId() < 0) {
            return new AccountDto(CreateAccountStatus.USER_IS_NULL);
        }

        //班级为空
        if(user.getRoleId() != Role.ADMIN )
        {	
        	flag = false;
        	if (userClass.getClassIds() == null || userClass.getClassIds().size() < 0) {
            return new AccountDto(CreateAccountStatus.CLASS_IS_NULL);
        	}
        }


        // 当所有信息都填完整了，就进行数据库查询，看看这个用户是否存在
        if (userNameExisted(user.getUserName())) {
            return new AccountDto(CreateAccountStatus.USERNAME_EXISTED);
        }
        //判断是内部员工还是外部员工
        switch (user.getTypeId()) {
            case Type.OUTTER_CLIENT:
                if (userClass.getClassIds().size() > 1) {
                    return new AccountDto(CreateAccountStatus.CLASS_TOO_MANY);
                }
                //删除用户所属班级
                affect = accountDao.deleteUserClass(user.getUserId());
                if (affect <= 0) {
                    return new AccountDto(UpdateAccountStatus.UNKNOWN_ERROR);
                }
                affect = accountDao.updateUser(user);
                if (affect <= 0) // 更新失败
                {
                    return new AccountDto(UpdateAccountStatus.UNKNOWN_ERROR);
                }
                userClass.setUserId(user.getUserId());
                affect = accountDao.insertUserClass(userClass);
                if (affect <= 0) // 插入失败
                {
                    return new AccountDto(UpdateAccountStatus.UNKNOWN_ERROR);
                }
            case Type.INNER_STAFF:
                //删除用户所属班级
            	if(flag == false)
            	{	
            		
	                affect = accountDao.deleteUserClass(user.getUserId());
	                if (affect <= 0) {
	                    return new AccountDto(UpdateAccountStatus.UNKNOWN_ERROR);
	                }
	                affect = accountDao.updateUser(user);
	                if (affect <= 0) // 更新失败
	                {
	                    return new AccountDto(UpdateAccountStatus.UNKNOWN_ERROR);
	                }
	                userClass.setUserId(user.getUserId());
	                affect = accountDao.insertUserClass(userClass);
	                if (affect <= 0) // 插入失败
	                {
	                    return new AccountDto(UpdateAccountStatus.UNKNOWN_ERROR);
	                }
            	}
            	else {
            		accountDao.deleteUserClass(user.getUserId());
	            	affect = accountDao.updateUser(user);
	                if (affect <= 0) // 更新失败
	                {
	                    return new AccountDto(UpdateAccountStatus.UNKNOWN_ERROR);
	                }
            	}
            	
        }
        return new AccountDto(UpdateAccountStatus.SUCCESS);
    }

    /**
     * 得到用户信息用于更新
     * @param username 用户名
     * @return 用户数据
     */
	@Override
	public AccountDto getUserByUserNameForUpdate(String username) {
		UserData u = null;
		if(username == null) {
			return new AccountDto(Common.WRONG_ARGEMENT);
		}
		 // 用户名不存在！
		User user = accountDao.getTypeIdAndRoleId(username);
		if (user == null) {
            return new AccountDto(CreateAccountStatus.USERNAME_NOEXISTED);
        }
		//判断是管理员还是其他
		switch (user.getRoleId()) {
			case Role.ADMIN:
				u = accountDao.getAdminByUserNameForUpdate(username);
				break;
			default:
				u = accountDao.getUserByUserNameForUpdate(username);
				break;
		}
		
        return new AccountDto<UserData>(u,Common.SUCCESS);
      
	}


}
