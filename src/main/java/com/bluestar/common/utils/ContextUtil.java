package com.bluestar.common.utils;

import org.apache.log4j.Logger;

import javax.servlet.ServletContext;

/**
 * 上下文的工具类，目前只是 ServletContext
 *
 * @author Fish
 * created by 2018-05-17 15:03
 */
public final class ContextUtil {
    // 上下文对象
    private static ServletContext servletContext = null;

    // 记录日志
    private static Logger logger = Logger.getLogger(ContextUtil.class);

    // 签到码前缀，防止和其他 key 重复
    private static final String SIGN_CODE_PREFIX = "signCode_";

    // 权限表 key
    public static final String POWER_MAP = "powerMap";

    // 课程表 key
    public static final String COURSES = "Courses";

    // 问卷 key
    public static final String CURRENT_QUIZ = "currentQuiz";

    /**
     * 初始化工具类
     *
     * @param context 上下文对象
     */
    public static void init(ServletContext context) {
        if (context == null) {
            logger.error("ServletContext is null!");
            return;
        }

        servletContext = context;
        logger.info("ContextUtil is ready!!!");
    }

    /**
     * 销毁工具类，释放资源
     */
    public static void destroy() {
        servletContext = null;
        logger.info("ContextUtil is destroyed");
        logger = null;
    }

    /**
     * 加载数据
     *
     * @param key      数据 key 值
     * @param data     数据来源
     * @param infoName 数据提示信息名字
     */
    public static void load(String key, Object data, String infoName) {
        if (data == null) {
            logger.error(infoName + " 数据为空，无法加载！");
            return;
        }

        // 放进 Context
        set(key, data);
        logger.info(infoName + " 数据准备完毕！！！！！！！！！");
    }

    /**
     * 添加数据到 Context
     *
     * @param key  数据 key 值
     * @param data 数据
     */
    public static void set(String key, Object data) {
        servletContext.setAttribute(key, data);
    }

    /**
     * 从 Context 中得到数据
     *
     * @param key 数据 key 值
     * @return 返回数据
     */
    public static Object get(String key) {
        return servletContext.getAttribute(key);
    }

    /**
     * 将老师发布的签到码加载到 ServletContext 中
     *
     * @param userId 老师 userId
     * @param code   签到码
     */
    public synchronized static void putSignCode(Integer userId, Integer code) {
        set(SIGN_CODE_PREFIX + userId, code);
    }

    /**
     * 得到老师的签到码
     *
     * @param userId 老师 userId
     * @return 返回签到码
     */
    public static Integer getSignCode(Integer userId) {
        return (Integer) get(SIGN_CODE_PREFIX + userId);
    }
}
