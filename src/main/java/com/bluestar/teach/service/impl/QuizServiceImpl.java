package com.bluestar.teach.service.impl;

import com.bluestar.teach.dao.AccountDao;
import com.bluestar.teach.dao.NewQuizDao;
import com.bluestar.teach.dto.AccountDto;
import com.bluestar.teach.entity.Clazz;
import com.bluestar.teach.entity.QuizAnswer;
import com.bluestar.teach.entity.Quiz;
import com.bluestar.teach.entity.QuizDetail;
import com.bluestar.teach.enums.Statusable;
import com.bluestar.teach.enums.impl.Common;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.bluestar.teach.service.QuizService;
import com.bluestar.common.utils.PageUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 问卷业务实现类
 *
 * @author Fish
 * created by 2018-05-15 15:10
 */
@Service
/*
 * @Transactional中的的属性 propagation :事务的传播行为 isolation :事务的隔离级别 readOnly :只读
 *                     rollbackFor :发生哪些异常回滚 noRollbackFor :发生哪些异常不回滚
 *                     rollbackForClassName 根据异常类名回滚
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
public class QuizServiceImpl implements QuizService {

    // 记录日志
    private Logger logger = Logger.getLogger(this.getClass());

    private NewQuizDao quizDao = null;

    private AccountDao accountDao = null;

    @Autowired
    public void setQuizDao(NewQuizDao quizDao) {
        this.quizDao = quizDao;
    }

    @Autowired
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * 主要是学生查看他填写的问卷，通过 userId 获得
     *
     * @param pageNumber 页数
     * @param userId     用户 id
     * @param courseId   课程 id，如果不用可以传入 null
     * @return 返回这个用户填写过的问卷
     */
    public AccountDto getQuizsByUser(Integer pageNumber, Integer userId, Integer courseId) {
        // 参数为空错误
        if (pageNumber == null || pageNumber <= 0 || userId == null || userId < 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        PageUtil.toPage(pageNumber);
        List<Map<String, Object>> quizzes = quizDao.getQuizByUserIdOrCourseId(userId, courseId);
        // 没有得到数据
        if (quizzes == null || quizzes.size() == 0) {
            return new AccountDto(Common.GET_IS_NULL);
        }

        return new AccountDto<>(PageUtil.pageInfo(quizzes), Common.SUCCESS);
    }

    /**
     * 通过 quiz_id 获取具体的问卷和回答
     *
     * @param quizId 问卷 id
     * @return 返回这条数据的详细信息
     */
    public AccountDto getQuizById(Integer quizId) {
        // id 必须填
        if (quizId == null || quizId < 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        Quiz quiz = quizDao.getQuizById(quizId);
        if (quiz == null) {
            return new AccountDto(Common.GET_IS_NULL);
        }

        quiz.setQuestions(quizDao.getQuestionsByQuizDetailId(quiz.getQuizDetailId()));
        quiz.setAnswers(quizDao.getAnswersByQuizId(quiz.getQuizId()));

        return new AccountDto<>(quiz, Common.SUCCESS);
    }

    /**
     * 通过 classId 来获取班级问卷
     * 传入 classId == null 就获取所有班级的，也就是管理员的权限
     * 传入 courseId == null 就获取所有课程的
     *
     * @param pageNumber 页数
     * @param classId    班级 id
     * @param courseId   课程 id
     * @return 返回这个班级的所有问卷
     */
    public AccountDto getQuizsByClassAndCourse(Integer pageNumber, Integer classId, Integer courseId) {
        // 参数错误
        if (pageNumber == null || pageNumber <= 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        PageUtil.toPage(pageNumber);
        List<Map<String, Object>> quizzes = quizDao.getQuizByClassIdOrCourseId(classId, courseId);
        // 没有得到数据
        if (quizzes == null || quizzes.size() == 0) {
            return new AccountDto(Common.GET_IS_NULL);
        }

        return new AccountDto<>(PageUtil.pageInfo(quizzes), Common.SUCCESS);
    }

    /**
     * 学生得到问卷问题
     *
     * @return 返回问卷问题
     */
    public AccountDto getQuiz() {
        // 得到问卷
        QuizDetail quizDetail = quizDao.getQuiz();
        if (quizDetail == null) {
            return new AccountDto(Common.GET_IS_NULL);
        }

        return new AccountDto<>(quizDetail, Common.SUCCESS);
    }

    /**
     * 管理员获得所有问卷
     *
     * @param pageNumber 页数
     * @return 返回所有问卷, QuizDetail
     */
    public AccountDto getQuizes(Integer pageNumber) {
        // 必须分页
        if (pageNumber == null || pageNumber <= 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        PageUtil.toPage(pageNumber); // 开始分页
        List<QuizDetail> quizes = quizDao.getQuizes();
        if (quizes == null || quizes.size() == 0) {
            return new AccountDto(Common.GET_IS_NULL);
        }

        return new AccountDto<>(PageUtil.pageInfo(quizes), Common.SUCCESS);
    }

    /**
     * 老师通过 userId 可以查到他的班级的学生的问卷
     * userId 必须传
     * 如果 courseId == null，就是课程
     *
     * @param pageNumber 页数
     * @param userId     用户 id
     * @param courseId   课程 id
     * @return 返回问卷信息
     */
    public AccountDto getQuizByClassIdAndCourseId(Integer pageNumber, Integer userId, Integer courseId) {
        // userId 必须有
        if (pageNumber == null || pageNumber <= 0 || userId == null || userId < 0) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        PageUtil.toPage(pageNumber);
        List<Map<String, Object>> quizzes = quizDao.getQuizByHisClassIdOrCourseId(userId, courseId);
        // 没有得到数据
        if (quizzes == null || quizzes.size() == 0) {
            return new AccountDto(Common.GET_IS_NULL);
        }

        return new AccountDto<>(PageUtil.pageInfo(quizzes), Common.SUCCESS);
    }

    /**
     * 填写问卷
     * 需要给 quiz 传入 userId, classId, quizDetailId 和 answers
     *
     * @param quiz 填写的问卷
     * @return 返回是否填写成功
     */
    public AccountDto writeQuiz(Quiz quiz) {
        if (quiz == null || quiz.getUserId() == null || quiz.getUserId() < 0 ||
                quiz.getClassId() == null || quiz.getClassId() < 0) {
            // 这些参数必须传入，并且有意义
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        quiz.setDate(new Date()); // 填写时间
        Clazz clazz = accountDao.getClassByClassId(quiz.getClassId());
        // 班级信息必须要有，主要是课程
        if (clazz == null) {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        quiz.setCourseId(clazz.getCourseId());
        int affect = quizDao.checkIfWritten(quiz.getUserId(), quiz.getCourseId(), quiz.getQuizDetailId());
        // 重复填写问卷错误
        if (affect > 0) {
            return new AccountDto(new Statusable() {
                @Override
                public int getCode() {
                    return -1;
                }

                @Override
                public String getInfo() {
                    return "这节课，您已经填写过这个问卷了！";
                }
            });
        }

        affect = quizDao.insertQuiz(quiz); // 可以获得 quiz_id
        // 由于未知错误，插入失败
        if (affect <= 0) {
            logger.warn("insertQuiz...err...");
            return new AccountDto(Common.ERROR);
        }

        QuizAnswer quizAnswer = new QuizAnswer();
        quizAnswer.setQuizId(quiz.getQuizId());
        quizAnswer.setAnswers(quiz.getAnswers());
        affect = quizDao.insertQuizAnswer(quizAnswer);
        if (affect <= 0) {
            logger.warn("insertQuizAnswer...err...");
            return new AccountDto(Common.ERROR);
        }

        return new AccountDto(Common.SUCCESS);
    }

    /**
     * 发布问卷调查
     * 必须传入 questions
     *
     * @param quizDetail 要发布的问卷
     * @return 返回是否发布成功
     */
    public AccountDto publishQuiz(QuizDetail quizDetail) {
        if (quizDetail == null || quizDetail.getQuestions() == null || quizDetail.getQuestions().size() == 0) {
            // questions 必须传入
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        quizDetail.setUsed(true); // 刚发布的默认就是使用的
        quizDao.updateUsedQuiz(null, false); // 先把所有的 is_used 置 0，然后再插入使用的

        // 先在 quiz_detail 表中插入一条数据，得到 quiz_detail_id
        int affect = quizDao.insertQuizDetail(quizDetail);
        // 内部错误
        if (affect <= 0) {
            logger.warn("insertQuizDetail...err...");
            return new AccountDto(Common.ERROR);
        }

        // 然后再将 quiz_detail_id 插入到 quiz_question 中
        affect = quizDao.insertQuizQuestion(quizDetail);
        // 内部错误
        if (affect <= 0) {
            return new AccountDto(Common.ERROR);
        }

        return new AccountDto(Common.SUCCESS);
    }
}
