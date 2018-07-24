package com.bluestar.teach.entity;

import java.util.List;

/**
 * 问卷回答类
 *
 * @author Fish
 * created by 2018-05-16 17:27
 */
public class QuizAnswer
{
    // 对应谁回答的
    private Integer quizId = null;

    // 答案
    private List<String> answers = null;

    public Integer getQuizId()
    {
        return quizId;
    }

    public void setQuizId(Integer quizId)
    {
        this.quizId = quizId;
    }

    public List<String> getAnswers()
    {
        return answers;
    }

    public void setAnswers(List<String> answers)
    {
        this.answers = answers;
    }

    @Override
    public String toString()
    {
        return "QuizAnswer{" +
                "quizId=" + quizId +
                ", answers=" + answers +
                '}';
    }
}
