package com.bluestar.teach.service.impl;

import com.bluestar.teach.dao.ScoreDao;
import com.bluestar.teach.dto.AccountDto;
import com.bluestar.teach.entity.Score;
import com.bluestar.teach.entity.ScoreData;
import com.bluestar.teach.entity.User;
import com.bluestar.teach.enums.impl.Common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bluestar.teach.service.ScoreService;
import com.bluestar.common.utils.PageUtil;
import com.bluestar.common.utils.SignUtil;

import java.util.List;

/**
 * 成绩业务实现类
 *
 * @author Fish
 * created by 2018-05-10
 */
@Service
public class ScoreServiceImpl implements ScoreService {
    private ScoreDao scoreDao = null;

    @Autowired
    public void setScoreDao(ScoreDao scoreDao) {
        this.scoreDao = scoreDao;
    }

    /**
     * 得到所有成绩
     *
     * @param pageNumber 页数
     * @return 成绩集合
     */
    @Override
    public AccountDto getAllScores(Integer pageNumber) {
        // 页数必须传
        if (pageNumber == null || pageNumber < 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        PageUtil.toPage(pageNumber);
        List<ScoreData> scores = scoreDao.getAllScoreDatas();
        // 数据为空
        if (scores == null) {
            return new AccountDto(Common.GET_IS_NULL);
        }

        return new AccountDto<>(PageUtil.pageInfo(scores), Common.SUCCESS);
    }

    /**
     * 通过用户查询他的成绩
     *
     * @param pageNumber 页数
     * @param userId     用户 id
     * @return 返回成绩信息
     */
    public AccountDto getScoresByUserId(Integer pageNumber, Integer userId) {
        // 参数错误
        if (pageNumber == null || userId == null || pageNumber < 0 || userId < 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        PageUtil.toPage(pageNumber);
        List<ScoreData> scores = scoreDao.getScoreDatasByUserId(userId);
        // 未知错误
        if (scores == null) {
            return new AccountDto(Common.GET_IS_NULL);
        }

        return new AccountDto<>(PageUtil.pageInfo(scores), Common.SUCCESS);
    }

    /**
     * 通过班级 id 查询班级学生成绩
     *
     * @param pageNumber 页数
     * @param classId    班级 id
     * @return 返回班级成绩信息
     */
    public AccountDto getScoresByClassId(Integer pageNumber, Integer classId) {
        // 参数不能为空
        if (pageNumber == null || classId == null || pageNumber < 0 || classId < 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        PageUtil.toPage(pageNumber);
        List<ScoreData> scores = scoreDao.getScoresByClassId(classId);
        // 未知错误
        if (scores == null) {
            return new AccountDto(Common.GET_IS_NULL);
        }

        return new AccountDto<>(PageUtil.pageInfo(scores), Common.SUCCESS);
    }

    /**
     * 通过阶段查成绩
     *
     * @param pageNumber 页数
     * @param status     阶段
     * @return 返回成绩信息
     */
    public AccountDto getScoreByStatus(Integer pageNumber, Integer status) {
        if (pageNumber == null || status == null || pageNumber < 0 || status < 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        PageUtil.toPage(pageNumber);
        List<ScoreData> scores = scoreDao.getScoreDatasByStatus(status);
        if (scores == null) {
            return new AccountDto(Common.GET_IS_NULL);
        }

        return new AccountDto<>(PageUtil.pageInfo(scores), Common.SUCCESS);
    }

    /**
     * 通过班级和阶段查询成绩
     * 这里的阶段要引用 Level 里面的常量
     *
     * @param pageNumber 页数
     * @param status     阶段，目前只有三个
     * @return 返回成绩信息
     */
    public AccountDto getScoresByClassIdAndStatus(Integer pageNumber, Integer status, Integer classId) {
        // 参数不能为空
        if (pageNumber == null || status == null || classId == null || pageNumber < 0 || status < 0 || classId < 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        PageUtil.toPage(pageNumber);
        List<ScoreData> scores = scoreDao.getScoreDatasByClassIdAndStatus(status, classId);
        // 数据没有获取到
        if (scores == null) {
            return new AccountDto(Common.GET_IS_NULL);
        }

        return new AccountDto<>(PageUtil.pageInfo(scores), Common.SUCCESS);
    }

    /**
     * 通过 userId 获取成绩
     *
     * @param pageNumber 页数
     * @param userId     用户 id
     * @return 返回成绩信息
     */
    public AccountDto getScoresByHisClassId(Integer pageNumber, Integer userId) {
        // 参数错误
        if (pageNumber == null || userId == null || pageNumber < 0 || userId < 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        PageUtil.toPage(pageNumber); // 开始分页
        List<ScoreData> scores = scoreDao.getScoreDatasByHisClassId(userId);
        // 没能得到数据
        if (scores == null) {
            return new AccountDto(Common.GET_IS_NULL);
        }

        return new AccountDto<>(PageUtil.pageInfo(scores), Common.SUCCESS);
    }

    /**
     * 通过 userId 和阶段获取成绩
     *
     * @param pageNumber 页数
     * @param userId     用户 id
     * @param status     阶段
     * @return 返回成绩信息
     */
    public AccountDto getScoresByStatusAndHisClassId(Integer pageNumber, Integer userId, Integer status) {
        // 参数错误
        if (pageNumber == null || userId == null || status == null || pageNumber < 0 || userId < 0 || status < 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        PageUtil.toPage(pageNumber); // 开始分页
        List<ScoreData> scores = scoreDao.getScoreDatasByStatusAndHisClassId(userId, status);
        // 没能得到数据
        if (scores == null) {
            return new AccountDto(Common.GET_IS_NULL);
        }

        return new AccountDto<>(PageUtil.pageInfo(scores), Common.SUCCESS);
    }


    /**
     * 通过 scoreId 更新数据
     *
     * @param score 要被更新的数据
     * @return 返回更新数据
     */
    public AccountDto updateScore(Score score) {
        if (score == null || score.getScoreId() < 0) // 参数错误
        {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }
        int num = scoreDao.updateScoreByScoreId(score);
        if (num <= 0) {
            return new AccountDto(Common.ERROR);
        }
        return new AccountDto(Common.UPDATE_SCORE_SUCCESS);
    }

    /**
     * 批量插入成绩
     *
     * @param scores 成绩对象集合
     * @return 插入结果
     */
    @Override
    public AccountDto insertScores(List<Score> scores) {
        if (scores == null || scores.size() == 0 ) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }
        int num = scoreDao.insertScores(scores);
        if (num <= 0) {
            return new AccountDto(Common.ERROR);
        }
        return new AccountDto(Common.INSERT_SCORE_SUCCESS);
    }

    /**
     * 删除成绩
     *
     * @param scoreId 成绩id
     * @return 删除结果
     */
    @Override
    public AccountDto deleteScore(Integer scoreId) {
        if (scoreId == null || scoreId < 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }
        int num = scoreDao.deleteScoreByScoreId(scoreId);
        if (num <= 0) {
            return new AccountDto(Common.ERROR);
        }
        return new AccountDto(Common.DELETE_SCORE_SUCCESS);
    }

    /**
     * 根据班级id得到该班级的用户
     *
     * @param classId 班级id
     * @return 用户集合
     */
    @Override
    public AccountDto getUsersByClassId(Integer classId) {
        if (classId == null || classId < 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        List<User> users = scoreDao.getUsersByClassId(classId);
        if (users == null || users.size() == 0) {
            return new AccountDto(Common.GET_IS_NULL);
        }

        return new AccountDto<List<User>>(users, Common.SUCCESS);
    }

    /**
     * 得到成绩统计情况比率，0 - 1 之间
     * 可以得到多种组合，比如传入 null
     *
     * @param classId 班级 id
     * @param status 阶段
     * @return 返回成绩统计情况比率
     */
    public AccountDto getScoreRate(Integer classId, Integer status, Integer userId) {
        if (userId == null) {
            return new AccountDto<>(SignUtil.checkRate(scoreDao.getPassNumberForAdmin(classId, status)), Common.SUCCESS);
        }

        return new AccountDto<>(SignUtil.checkRate(scoreDao.getPassNumberForTeacher(classId, status, userId)), Common.SUCCESS);
    }
}
