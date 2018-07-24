package com.bluestar.teach.dao;

import com.bluestar.teach.entity.QuizAnswer;
import com.bluestar.teach.entity.Quiz;
import com.bluestar.teach.entity.QuizDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 问卷 com.bluestar.teach.dao 方法
 *
 * @author Fish
 * created by 2018-05-15 15:05
 */
public interface NewQuizDao
{
    public List<Map<String, Object>> getQuizByUserIdOrCourseId(@Param("userId") Integer userId, @Param("courseId") Integer courseId);

    public Quiz getQuizById(Integer id);

    public List<String> getQuestionsByQuizDetailId(@Param("quizDetailId") Integer quizDetailId);

    public List<String> getAnswersByQuizId(@Param("quizId") Integer quizId);

    public List<Map<String, Object>> getQuizByClassIdOrCourseId(@Param("classId") Integer classId, @Param("courseId") Integer courseId);

    public QuizDetail getQuiz();

    public List<QuizDetail> getQuizes();

    public List<Map<String, Object>> getQuizByHisClassIdOrCourseId(@Param("userId") Integer userId, @Param("courseId") Integer courseId);

    public int checkIfWritten(@Param("userId") Integer userId, @Param("courseId") Integer courseId, @Param("quizDetailId") Integer quizDetailId);

    public int insertQuiz(Quiz quiz);

    public int insertQuizAnswer(QuizAnswer quizAnswer);

    public int insertQuizDetail(QuizDetail quizDetail);

    public int insertQuizQuestion(QuizDetail quizDetail);

    public int updateUsedQuiz(@Param("id") Integer quizDetailId, @Param("isUsed") boolean isUsed);
}
