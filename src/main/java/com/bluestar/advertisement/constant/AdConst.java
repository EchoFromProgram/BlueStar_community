package com.bluestar.advertisement.constant;

import java.io.File;

/**
 * @author Imp
 * email: 1318944013@qq.com
 * date: 2018/7/18 10:17
 */
public final class  AdConst {


    public static final String SAVE_AD_RESULT = "saveResult";

    public static final String  DIR_PATH = File.separator + "File";

    // 正常状态
    public static final String STATUS_NROMAL = "1";

    // 上架状态
    public static final String STATUS_USE = "2";

    // 无效状态
    public static final String STATUS_USELESS = "3";

    // 模块id
    public static final String MODULE = "1";

    public static Integer size = 5;

    public static final String LIST_FAILURE = "获取广告列表错误";
    // 图片类型后缀
    public static final String IMG[] =
            {"bmp", "jpg", "jpeg", "png", "tiff", "gif", "pcx", "tga", "exif", "fpx", "svg", "psd",
                    "cdr", "pcd", "dxf", "ufo", "eps", "ai", "raw", "wmf"};


}
