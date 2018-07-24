package com.bluestar.teach.service.impl;

import com.bluestar.teach.dao.AccountDao;
import com.bluestar.teach.dto.AccountDto;
import com.bluestar.teach.entity.Clazz;
import com.bluestar.teach.entity.Course;
import com.bluestar.teach.enums.impl.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bluestar.teach.service.ClassService;

/**
 * 班级相关业务实现类
 *
 * @author Fish
 * created by 2018-05-09
 */
@Service
public class ClassServiceImpl implements ClassService {
    private AccountDao accountDao = null;

    @Autowired
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * 通过班级 id 获取班级上到哪一节课
     *
     * @param classId 班级 id
     * @return 返回课程
     */
    @Override
    public AccountDto getCourseByClassId(Integer classId) {
        // 检测参数有没有问题
        if (classId == null || classId < 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        Clazz clazz = accountDao.getClassByClassId(classId);
        // 没有查到数据，提示错误
        if (clazz == null) {
            return new AccountDto(Common.GET_IS_NULL);
        }

        // 通过班级查课程
        Course course = accountDao.getCoursesByCourseId(clazz.getCourseId());
        // 没有查到数据，报错
        if (course == null) {
            return new AccountDto(Common.GET_IS_NULL);
        }

        return new AccountDto<>(course, Common.SUCCESS);
    }
}
