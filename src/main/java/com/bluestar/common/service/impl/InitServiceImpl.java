package com.bluestar.common.service.impl;

import com.bluestar.common.service.InitService;
import com.bluestar.teach.dao.AccountDao;
import com.bluestar.teach.dao.NewQuizDao;
import com.bluestar.teach.dto.AccountDto;
import com.bluestar.teach.entity.Course;
import com.bluestar.teach.entity.Power;
import com.bluestar.teach.enums.impl.Common;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提供服务器初始化的业务实现类
 *
 * @author Fish
 */
@Service
public class InitServiceImpl implements InitService {

    // 记录日志
    private Log log = LogFactory.getLog(InitService.class);

    private AccountDao accountDao = null;

    @Autowired
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    private NewQuizDao quizDao = null;

    @Autowired
    public void setQuizDao(NewQuizDao quizDao) {
        this.quizDao = quizDao;
    }

    /**
     * 得到整张权限表
     *
     * @return 整张权限表，以 Map 封装，key 值为权限 id，value 值为权限（这里主要是 URL 和功能名称）
     */
    public AccountDto getAllPowers() {
        Map<Integer, Power> powersMap = new HashMap<>();
        List<Power> powers = accountDao.getPowers();

        if (powers != null) {
            // 如果成功得到权限表，就将它转成 Map
            for (Power power : powers) {
                powersMap.put(power.getPowerId(), power);
            }
        }

        if (powersMap.size() == 0) {
            log.warn("获取的权限表为空！");
        }

        return new AccountDto<>(powersMap, Common.SUCCESS);
    }

    /**
     * 得到整张课程表
     *
     * @return 整张课程表
     */
    public AccountDto getAllCourses() {
        List<Course> courses = accountDao.getAllcourses();
        // 没有获取数据
        if (courses == null) {
            log.error("课程表获取失败！");
            return new AccountDto(Common.GET_IS_NULL);
        }

        // 课程排序器
        courses.sort((o1, o2) ->
        {
            if (o1.getCourseId() > o2.getCourseId()) {
                return 1;
            } else {
                if (o1.getCourseId() < o2.getCourseId()) {
                    return -1;
                }
            }

            return 0;
        });

        return new AccountDto<>(courses, Common.SUCCESS);
    }

    /**
     * 得到现在活跃的问卷
     *
     * @return 返回问卷
     */
    public AccountDto getQuiz() {
        return new AccountDto<>(quizDao.getQuiz(), Common.SUCCESS);
    }
}
