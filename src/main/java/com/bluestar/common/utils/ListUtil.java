package com.bluestar.common.utils;

import com.bluestar.teach.entity.QuizQuestion;
import com.bluestar.teach.entity.Score;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 列表工具类
 *
 * @author Fish
 * created by 2018-05-16 20:15
 */
public final class ListUtil {

    // 记录日志
    private static Log log = LogFactory.getLog(ListUtil.class);

    /**
     * 将一堆的 String 转成一堆的 QuizQuestion
     *
     * @param list 一堆的 String
     * @return 返回一堆的 QuizQuestion
     */
    public static List<QuizQuestion> strings2QuizQuestions(List<String> list) {
        if (list == null || list.size() == 0) {
            return null;
        }

        List<QuizQuestion> quizQuestions = new ArrayList<>(list.size());
        for (String s : list) {
            quizQuestions.add(new QuizQuestion(s));
        }

        return quizQuestions;
    }

    /**
     * 将前台的数据封装成成绩对象集合
     *
     * @param classId 班级id
     * @param status  阶段
     * @param date    日期
     * @param map     用户id:成绩
     * @return 成绩对象集合
     */
    public static List<Score> toScores(Integer classId, Integer status, Date date, Map<Integer, Integer> map) {
        List<Score> scores = new ArrayList<>();
        for (Integer u : map.keySet()) {
            scores.add(new Score(classId, u, status, date, map.get(u)));
        }

        return scores;
    }

    /**
     * 将 String[] 转换为 List&lt;Integer&gt;
     * 如果传入参数 strings 为空或者是 0 元素的，就返回 null
     * 如果抛出异常，被捕获了，也会返回 null
     *
     * @param strings 要被转换为 List 的元素数组
     * @return 返回 List&lt;Integer&gt;，里面装着转换之后的 number
     */
    public static List<Integer> strings2integers(String[] strings) {

        // 如果参数为空，或者元素个数为 0，就返回 null
        if (strings == null || strings.length == 0) {
            return null;
        }

        // 装数字的集合
        List<Integer> numbers = new ArrayList<>(strings.length);
        String errString = null;
        try {
            // 遍历参数，一个个拿出来转成 Integer
            for (String string : strings) {
                errString = string;
                numbers.add(Integer.valueOf(string.trim()));
            }
        } catch (NumberFormatException nfe) {
            log.error("数字格式错误：不能把 '" + errString + "' 转为数字！", nfe);
            numbers = null;
        } catch (Exception e) {
            log.error("未知错误！错误位置：'" + errString + "'", e);
            numbers = null;
        }

        return numbers;
    }
}
