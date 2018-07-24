package com.bluestar.teach.service;

import com.bluestar.teach.dto.AccountDto;
import org.springframework.stereotype.Service;

/**
 * 班级相关业务类
 *
 * @author Fish
 * created by 2018-05-09
 */
public interface ClassService
{
    /**
     * 通过班级 id 获取班级上到哪一节课
     *
     * @param classId 班级 id
     * @return 返回课程
     */
    public AccountDto getCourseByClassId(Integer classId);
}
