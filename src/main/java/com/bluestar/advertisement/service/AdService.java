package com.bluestar.advertisement.service;

import com.bluestar.advertisement.dto.ServerResponse;
import com.bluestar.advertisement.entity.Advertise;
import com.bluestar.advertisement.enums.response.AdResponse;
import com.bluestar.advertisement.utils.AdUtils;
import com.bluestar.advertisement.vo.AdVo;
import com.github.pagehelper.PageInfo;
import javafx.beans.binding.ObjectExpression;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author Imp
 * email: 1318944013@qq.com
 * date: 2018/7/18 11:07
 */
public interface AdService {

    /**
     * 新增广告
     * @param file 图片
     * @param dirPath 路径
     * @param advertise 广告类
     * @return 上传结果
     */
    public ServerResponse saveAd(CommonsMultipartFile file, String dirPath, Advertise advertise);

    /**
     * 删除广告（实际上是把广告置为无效状态）
     * @param adId 广告id
     * @return 删除结果
     */
    public ServerResponse deleteAdById(String adId);


    /**
     * 更新广告
     * @param file 图片
     * @param dirPath 路径
     * @param advertise 广告
     * @param pictureId 图片id
     * @return
     */
    public ServerResponse updateAd(CommonsMultipartFile file, String dirPath, Advertise advertise,
                                   String pictureId, String dir);




    public PageInfo queryAds(String adTitle, String adStatus, Integer pageNum) ;


    public ServerResponse getUpdateData(String adId);

}