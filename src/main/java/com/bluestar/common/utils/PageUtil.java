package com.bluestar.common.utils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.bluestar.teach.constant.Page;

import java.util.List;

/**
 * 分页常用工具
 *
 * @author Fish
 */
public final class PageUtil {
    /**
     * pageHelper 每进行一次分页就得调用一次这个方法
     *
     * @param pn 到第几页
     */
    public static void toPage(Integer pn) {
        PageHelper.startPage(pn, Page.ONE_PAGE_SIZE);
    }

    /**
     * 返回 pageInfo 对象，包含分页的信息
     *
     * @param objs 要被分页的集合
     */
    public static <T> PageInfo<T> pageInfo(List<T> objs) {
        //第一个参数为分页对象，第二个为每次显示的页数
        return new PageInfo<T>(objs, Page.SHOW_PAGES);
    }

    public static void toPage(Integer pn, Integer size) {
        PageHelper.startPage(pn, size);
    }
}
