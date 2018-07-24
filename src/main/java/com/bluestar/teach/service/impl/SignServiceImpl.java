package com.bluestar.teach.service.impl;

import com.bluestar.teach.constant.Role;
import com.bluestar.teach.constant.SignCode;
import com.bluestar.teach.dao.AccountDao;
import com.bluestar.teach.dao.SignDao;
import com.bluestar.teach.dto.AccountDto;
import com.bluestar.teach.entity.Clazz;
import com.bluestar.teach.entity.Sign;
import com.bluestar.teach.entity.SignData;
import com.bluestar.teach.entity.User;
import com.bluestar.teach.enums.impl.Common;
import com.bluestar.teach.enums.impl.CreateAccountStatus;
import com.bluestar.teach.enums.impl.SignStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.bluestar.teach.service.SignService;
import com.bluestar.common.utils.PageUtil;
import com.bluestar.common.utils.SignUtil;

import java.util.*;

/**
 * 签到业务实现类
 *
 * @author Fish
 */
@Service
public class SignServiceImpl implements SignService {
    // 签到持久层对象
    private SignDao signDao = null;

    @Autowired
    public void setSignDao(SignDao signDao) {
        this.signDao = signDao;
    }

    // 账号持久层对象
    private AccountDao accountDao = null;

    @Autowired
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * 获取到一个随机的签到码
     * 范围是 1000 ~ 9999
     *
     * @return 返回生成的签到码
     */
    @Override
    public int getSignCode() {
        return 1000 + (int) (9000 * Math.random());
    }

    /**
     * 获取用户所属班级，签到时需要老师指定现在在哪个班级上课
     *
     * @param user 指定的用户
     * @return 返回这个用户所属的班级
     */
    @Override
    public AccountDto getClasses(User user) {
        // 避免空指针
        if (user == null || user.getUserId() == null || user.getUserId() < 0) {
            return new AccountDto(Common.ERROR);
        }

        // 如果是管理员，获得的班级是全部的
        if (user.getRoleId() == Role.ADMIN) {
            List<Clazz> clazzes = accountDao.getAllClasses();
            if (clazzes == null || clazzes.size() == 0) {
                return new AccountDto(Common.ERROR);
            }

            return new AccountDto<>(clazzes, Common.SUCCESS);
        }

        List<Integer> classIds = accountDao.getClassIdsByUserId(user.getUserId());
        if (classIds == null || classIds.size() == 0) {
            // 这个用户没有所属班级
            return new AccountDto(Common.ERROR);
        }

        // 遍历去获取所有班级信息
        List<Clazz> clazzes = new ArrayList<>(classIds.size());
        for (Integer classId : classIds) {
            clazzes.add(accountDao.getClassByClassId(classId));
        }

        return new AccountDto<>(clazzes, Common.SUCCESS);
    }

    /**
     * 学生签到方法，传入一个对象和验证码
     *
     * @param user    要签到的用户
     * @param classId 用户所属班级
     *                如果用户没有班级，那就传入一个 null 即可
     * @param reason  * 签到原因，（选填）
     *                如果是迟到或者旷课就必须填写
     * @return 返回签到情况
     */
    @Override
    public AccountDto sign(User user, Integer inputCode, Integer realCode, Integer classId, String reason) {
        // 必须存在这个用户才能进行签到
        if (user == null || user.getUserId() == null || user.getUserId() < 0 || inputCode == null) {
            return new AccountDto(SignStatus.ILLEGAL_SIGN);
        }

        Date now = new Date();
        // 签到还没开放
        if (!(SignUtil.isSignTime(now) || SignUtil.isClassTime(now))) {
            return new AccountDto(SignStatus.TOO_EARLY);
        }

        // 可以正常签到了
        if (realCode == null || !realCode.equals(inputCode)) // 判断签到码是否正确
        {
            return new AccountDto(SignStatus.WRONG_CODE);
        }

        // 上课时间签到，迟到了不写原因，还想签到？不存在的！
        if (SignUtil.isClassTime(now) && StringUtils.isEmpty(reason)) {
            return new AccountDto(SignStatus.TOO_LATE);
        }

        // 判断这个班级是否属于这个用户
        List<Clazz> classes = (List<Clazz>) this.getClasses(user).getData();
        if (classes == null) {
            return new AccountDto(SignStatus.WRONG_CLASS);
        }

        Clazz clazz = accountDao.getClassByClassId(classId); // 得到这个班级信息
        if (!classes.contains(clazz)) {
            return new AccountDto(SignStatus.WRONG_CLASS);
        }

        // 重复签到！
        if (signDao.signIsExisted(user.getUserId(), clazz.getCourseId()) > 0) {
            return new AccountDto(SignStatus.HAS_SIGNED);
        }

        Sign sign = new Sign();
        sign.setUserId(user.getUserId());
        sign.setClassId(clazz.getClassId());
        sign.setCourseId(clazz.getCourseId());
        sign.setReason(reason);
        sign.setDate(now);

        if (SignUtil.isClassTime(now)) // 迟到了
        {
            sign.setStatus(SignCode.LATE);
        } else {
            sign.setReason(SignStatus.SUCCESS.getInfo());
            sign.setStatus(SignCode.SUCCESS);
        }

        // 插入失败，可能是系统错误或者网络错误
        int affect = signDao.insertIntoSign(sign);
        if (affect <= 0) {
            return new AccountDto(Common.ERROR);
        }

        return new AccountDto(SignStatus.SUCCESS);
    }

    /**
     * 老师签到
     *
     * @param user    要签到的用户
     * @param classId 用户所属班级
     *                如果用户没有班级，那就传入一个 null 即可
     * @param reason  * 签到原因，（选填）
     *                如果是迟到或者旷课就必须填写
     */
    public AccountDto sign(User user, Integer classId, String reason) {
        return sign(user, 0, 0, classId, reason);
    }

    /**
     * 根据班级信息获取签到信息
     *
     * @param pageNumber 要显示的页数
     * @param clazz      指定的班级
     * @return 返回指定的签到信息
     */
    @Override
    public AccountDto getClassSigns(Integer pageNumber, Clazz clazz) {
        // 判断页数是否为空
        if (pageNumber == null || pageNumber <= 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        if (clazz == null || clazz.getClassId() == null || clazz.getClassId() < 0) {
            return new AccountDto(SignStatus.WRONG_CLASS);
        }

        PageUtil.toPage(pageNumber);

        return new AccountDto<>(PageUtil.pageInfo(signDao.getSignDatasByClassId(clazz.getClassId())),
                SignStatus.SUCCESS);
    }

    /**
     * 获取指定班级的老师的签到信息
     *
     * @param pageNumber 指定的页数
     * @param clazz      指定班级
     * @return 返回指定班级的老师签到信息
     */
    @Override
    public AccountDto getTeacherSignsByClass(Integer pageNumber, Clazz clazz) {
        // 判断参数是否正确
        if (pageNumber == null || pageNumber <= 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        if (clazz == null || clazz.getClassId() == null || clazz.getClassId() < 0) {
            return new AccountDto(SignStatus.WRONG_CLASS);
        }

        PageUtil.toPage(pageNumber);
        return new AccountDto<>(PageUtil.pageInfo(signDao.getSignDatasByClassIdAndRoleId(clazz.getClassId(), Role.TEACHER)),
                SignStatus.SUCCESS);
    }

    /**
     * 获取指定班级的学生的签到信息
     *
     * @param pageNumber 指定的页数
     * @param clazz      指定班级
     * @return 返回指定班级的学生的签到信息
     */
    @Override
    public AccountDto getStudentSignsByClass(Integer pageNumber, Clazz clazz) {
        // 判断参数是否正确
        if (pageNumber == null || pageNumber <= 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        if (clazz == null || clazz.getClassId() == null || clazz.getClassId() < 0) {
            return new AccountDto(SignStatus.WRONG_CLASS);
        }

        PageUtil.toPage(pageNumber);
        return new AccountDto<>(PageUtil.pageInfo(signDao.getSignDatasByClassIdAndRoleId(clazz.getClassId(), Role.STUDENT)),
                SignStatus.SUCCESS);
    }

    /**
     * 获取整个签到表的信息
     *
     * @param pageNumber 指定的页数
     * @return 返回整个签到表
     */
    @Override
    public AccountDto getSigns(Integer pageNumber) {
        // 判断参数是否正确
        if (pageNumber == null || pageNumber <= 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        PageUtil.toPage(pageNumber);
        return new AccountDto<>(PageUtil.pageInfo(signDao.getAllSignDatas()), SignStatus.SUCCESS);
    }

    /**
     * 获取这个用户的所有签到信息
     *
     * @param pageNumber 指定的页数
     * @param user       要被查询的用户
     * @return 返回这个用户的签到信息
     */
    @Override
    public AccountDto getSignsByUser(Integer pageNumber, User user) {
        // 判断参数是否正确
        if (pageNumber == null || pageNumber <= 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        // 先判断用户是否可用
        if (user == null || user.getUserId() == null || user.getUserId() < 0) {
            return new AccountDto(CreateAccountStatus.USER_IS_NULL);
        }

        PageUtil.toPage(pageNumber);
        return new AccountDto<>(PageUtil.pageInfo(signDao.getSignDatasByUserId(user.getUserId())),
                SignStatus.SUCCESS);
    }

    /**
     * 根据课程获得学生的签到情况
     *
     * @param pageNumber 指定的页数
     * @param courseId   课程id
     * @return 返回这个用户的签到信息
     */
    @Override
    public AccountDto getStudentSignsByCourseId(Integer pageNumber, Integer courseId) {
        // 判断参数是否正确
        if (pageNumber == null || pageNumber <= 0 || courseId == null || courseId < 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        PageUtil.toPage(pageNumber);
        return new AccountDto<>(PageUtil.pageInfo(signDao.getSignDatasByCourseIdAndRoleId(courseId, Role.STUDENT)),
                SignStatus.SUCCESS);
    }

    /**
     * 根据课程获得老师的签到情况
     *
     * @param pageNumber 指定的页数
     * @param courseId   课程id
     * @return 返回这个用户的签到信息
     */
    @Override
    public AccountDto getTeacherSignsByCourseId(Integer pageNumber, Integer courseId) {
        // 判断参数是否正确
        if (pageNumber == null || pageNumber <= 0 || courseId == null || courseId < 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }


        PageUtil.toPage(pageNumber);
        return new AccountDto<>(PageUtil.pageInfo(signDao.getSignDatasByCourseIdAndRoleId(courseId, Role.TEACHER)),
                SignStatus.SUCCESS);
    }

    /**
     * 根据课程和班级获得签到情况
     *
     * @param pageNumber 指定的页数
     * @param courseId   课程id
     * @param classId    班级id
     * @return 返回这个用户的签到信息
     */
    @Override
    public AccountDto getSignsByCouseIdAndClassId(Integer pageNumber, Integer courseId, Integer classId) {
        // 判断参数是否正确
        if (pageNumber == null || pageNumber <= 0 ||
                courseId == null || courseId < 0 ||
                classId == null || classId < 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        PageUtil.toPage(pageNumber);
        return new AccountDto<>(PageUtil.pageInfo(signDao.getSignDatasByClassIdAndCourseId(classId, courseId)),
                SignStatus.SUCCESS);
    }

    /**
     * 老师通过 userId 获取他的班级的签到信息
     *
     * @param pageNumber 页数
     * @param userId     用户 id
     * @return 返回签到信息
     */
    public AccountDto getSignsByUserId(Integer pageNumber, Integer userId) {
        // 参数错误
        if (userId == null || userId < 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        PageUtil.toPage(pageNumber);
        List<SignData> signs = signDao.getSignDatasByHisClassId(userId);
        // 没能得到数据
        if (signs == null || signs.size() == 0) {
            return new AccountDto(Common.GET_IS_NULL);
        }

        return new AccountDto<>(PageUtil.pageInfo(signs), Common.SUCCESS);
    }

    /**
     * 通过用户 id 和课程 id 查询签到信息
     *
     * @param pageNumber 页数
     * @param userId     用户 id
     * @param courseId   课程 id
     * @return 返回签到信息
     */
    public AccountDto getSignsByCourseIdAndHisClassId(Integer pageNumber, Integer userId, Integer courseId) {
        // 参数错误
        if (pageNumber == null || pageNumber <= 0 ||
                userId == null || userId < 0 ||
                courseId == null || courseId < 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        PageUtil.toPage(pageNumber); // 开始分页
        List<SignData> signs = signDao.getSignDatasByCourseIdAndHisClassId(userId, courseId);
        // 没能得到数据
        if (signs == null || signs.size() == 0) {
            return new AccountDto(Common.GET_IS_NULL);
        }

        return new AccountDto<>(PageUtil.pageInfo(signs), Common.SUCCESS);
    }

    /**
     * 通过课程 id 获取签到信息
     *
     * @param pageNumber 页数
     * @param courseId   课程 id
     * @return 返回课程信息
     */
    public AccountDto getSignsByCourseId(Integer pageNumber, Integer courseId) {
        // 参数错误
        if (pageNumber == null || pageNumber <= 0 ||
                courseId == null || courseId < 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        PageUtil.toPage(pageNumber); // 开始分页
        List<SignData> signs = signDao.getSignDatasByCourseId(courseId);
        // 没有返回数据
        if (signs == null || signs.size() == 0) {
            return new AccountDto(Common.GET_IS_NULL);
        }

        return new AccountDto<>(PageUtil.pageInfo(signs), Common.SUCCESS);
    }

    /**
     * 通过学生 id 得到老师 id
     *
     * @param studentId 学生 id
     * @return 返回老师 id
     */
    public Integer getTeacherId(Integer studentId) {
        return accountDao.getHisTeacherUserId(studentId);
    }

    /**
     * 得到签到统计信息，比率，0 - 1 之间
     *
     * @param classId  班级 id
     * @param courseId 课程 id
     * @return 返回签到情况
     */
    public AccountDto getSignRate(Integer classId, Integer courseId) {
        // 参数一个都不能为 null
        if (classId == null || classId < 0 ||
                courseId == null || courseId < 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        Map<String, Double> rates = new HashMap<>();
        rates.put("teacherSuccessRate", SignUtil.checkRate(signDao.getTeacherSignSuccessNumber(classId, courseId)));
        rates.put("teacherLateRate", SignUtil.checkRate(signDao.getTeacherSignLateNumber(classId, courseId)));
        rates.put("studentSuccessRate", SignUtil.checkRate(signDao.getStudentSignSuccessNumber(classId, courseId)));
        rates.put("studentLateRate", SignUtil.checkRate(signDao.getStudentSignLateNumber(classId, courseId)));

        return new AccountDto<>(rates, Common.SUCCESS);
    }
}
