package com.bluestar.common.service;


import java.util.Map;

import com.bluestar.teach.dto.AccountDto;
import org.springframework.stereotype.Service;

/**
 * 提供服务器初始化的业务类
 *
 * @author Fish
 * */
public interface InitService
{
    /**
     * 得到整张权限表
     *
     * @return 整张权限表，以 Map 封装，key 值为权限 id，value 值为权限（这里主要是 URL）
     */
    public AccountDto getAllPowers();

    /**
     * 得到整张课程表
     *
     * @return 整张课程表
     */
    public AccountDto getAllCourses();

    /**
     * 得到现在活跃的问卷
     *
     * @return 返回问卷
     */
    public AccountDto getQuiz();
}
