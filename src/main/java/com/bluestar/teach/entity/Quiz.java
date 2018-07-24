package com.bluestar.teach.entity;

import java.util.Date;
import java.util.List;

/**
 * 问卷类，对应quiz表
 *
 * @author happyChicken
 */
public class Quiz
{
    // 问卷id
    private Integer quizId;

    // 班级id
    private Integer classId;

    // 用户id
    private Integer userId;

    // 课程id
    private Integer courseId;

    // 日期
    private Date date;

    // 问卷内容
    private List<String> questions = null;

    private List<String> answers = null;

    //问卷内容
    private Integer quizDetailId;

    public Integer getQuizId()
    {
        return quizId;
    }

    public void setQuizId(Integer quizId)
    {
        this.quizId = quizId;
    }

    public Integer getClassId()
    {
        return classId;
    }

    public void setClassId(Integer classId)
    {
        this.classId = classId;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public Integer getCourseId()
    {
        return courseId;
    }

    public void setCourseId(Integer courseId)
    {
        this.courseId = courseId;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public List<String> getQuestions()
    {
        return questions;
    }

    public void setQuestions(List<String> questions)
    {
        this.questions = questions;
    }

    public List<String> getAnswers()
    {
        return answers;
    }

    public void setAnswers(List<String> answers)
    {
        this.answers = answers;
    }

    public Integer getQuizDetailId()
    {
        return quizDetailId;
    }

    public void setQuizDetailId(Integer quizDetailId)
    {
        this.quizDetailId = quizDetailId;
    }

    @Override
    public String toString()
    {
        return "Quiz{" +
                "quizId=" + quizId +
                ", classId=" + classId +
                ", userId=" + userId +
                ", courseId=" + courseId +
                ", date=" + date +
                ", questions=" + questions +
                ", answers=" + answers +
                ", quizDetailId=" + quizDetailId +
                '}';
    }
}
