package com.bluestar.common.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 签到工具类，包括时间对比
 *
 * @author Fish
 * created by 2018-05-17 16:24
 */
public final class SignUtil {
    /**
     * 得到时间，只有小时和分钟
     *
     * @param hour   小时
     * @param minute 分钟
     * @return 返回时间
     */
    private static Calendar getCalendar(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, 0, 0, hour, minute, 0);
        return calendar;
    }

    /**
     * 得到时间，只有小时和分钟
     *
     * @param date 时间
     * @return 返回时间
     */
    private static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return getCalendar(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }

    // 早上签到开始时间
    private static final Calendar MORNING_SIGN_TIME = getCalendar(9, 0);

    // 早上开课时间
    private static final Calendar MORNING_BEGIN_TIME = getCalendar(9, 30);

    // 早上结课时间
    private static final Calendar MORNING_END_TIME = getCalendar(12, 0);

    // 下午签到开始时间
    private static final Calendar AFTERNOON_SIGN_TIME = getCalendar(13, 0);

    // 下午开课时间
    private static final Calendar AFTERNOON_BEGIN_TIME = getCalendar(13, 30);

    // 下午结课时间
    private static final Calendar AFTERNOON_END_TIME = getCalendar(16, 0);

    // 晚上签到时间
    private static final Calendar EVENING_SIGN_TIME = getCalendar(18, 30);

    // 晚上开课时间
    private static final Calendar EVENING_BEGIN_TIME = getCalendar(19, 0);

    // 晚上结课时间
    private static final Calendar EVENING_END_TIME = getCalendar(22, 0);

    /**
     * 判断是否是签到时间
     *
     * @param date 要被判断的时间
     * @return true 在签到时间内 false 不在签到时间内
     */
    public static boolean isSignTime(Date date) {
        // 重新得到只有小时和分钟的时间
        Calendar calendar = getCalendar(date);

        // 判断是否在签到时间开始和上课时间开始之间
        if ((calendar.after(MORNING_SIGN_TIME) && calendar.before(MORNING_BEGIN_TIME))
                || (calendar.after(AFTERNOON_SIGN_TIME) && calendar.before(AFTERNOON_BEGIN_TIME))
                || (calendar.after(EVENING_SIGN_TIME) && calendar.before(EVENING_BEGIN_TIME))) {
            return true;
        }

        return false;
    }

    /**
     * 判断是否是上课时间
     *
     * @param date 要被判断的时间
     * @return true 在上课时间内 false 不在上课时间内
     */
    public static boolean isClassTime(Date date) {
        // 重新得到只有小时和分钟的时间
        Calendar calendar = getCalendar(date);

        // 判断是否在签到时间开始和上课时间开始之间
        if ((calendar.after(MORNING_BEGIN_TIME) && calendar.before(MORNING_END_TIME))
                || (calendar.after(AFTERNOON_BEGIN_TIME) && calendar.before(AFTERNOON_END_TIME))
                || (calendar.after(EVENING_BEGIN_TIME) && calendar.before(EVENING_END_TIME))) {
            return true;
        }

        return false;
    }

    /**
     * 如果 rate 为 null，就返回 0
     *
     * @param rate 比率
     * @return 返回合理的比率
     */
    public static Double checkRate(Double rate) {
        return (rate == null) ? 0 : rate * 100;
    }
}
