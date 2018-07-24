package com.bluestar.advertisement.service.impl;

import com.bluestar.advertisement.constant.AdConst;
import com.bluestar.advertisement.dao.AdDao;
import com.bluestar.advertisement.dto.ServerResponse;
import com.bluestar.advertisement.entity.Advertise;
import com.bluestar.advertisement.entity.Enclosure;
import com.bluestar.advertisement.enums.response.AdResponse;
import com.bluestar.advertisement.service.AdService;
import com.bluestar.advertisement.utils.AdUtils;
import com.bluestar.advertisement.vo.AdVo;
import com.bluestar.common.utils.CodeUtil;
import com.bluestar.common.utils.PageUtil;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Imp
 * email: 1318944013@qq.com
 * date: 2018/7/18 11:08
 */
@Service
public class AdServiceImpl implements AdService {

    public static final Logger log = Logger.getLogger(AdServiceImpl.class);

    @Autowired
    private AdDao adDao;



    /**
     * 新增一个广告
     * @param file 图片
     * @param dirPath 路径
     * @param advertise 广告类
     * @return 上传结果
     */
    @Transactional
    public ServerResponse saveAd(CommonsMultipartFile file, String dirPath, Advertise advertise) {

        int affect = 0;

        String path = null;

        // 得到上传的图片名
        String pictureName = file.getOriginalFilename();

        // 得到图片上传完整路径
        path = dirPath + File.separator + pictureName;

        String uri = AdUtils.getUri(path);

        // 设置广告id和图片id
        advertise.setAdId(CodeUtil.getId());
        advertise.setAdPicture(CodeUtil.getId());

        // 上传图片
        if(upFile(file, path , pictureName) != null) {
            return upFile(file, path, pictureName);
        }

        // 图片上传成功，将广告和图片信息插入数据库
        try {

            //  把广告插入数据库
            affect = adDao.insertAd(advertise);
            if(affect <= 0){
                File f = new File(path);
                f.delete();
                return ServerResponse.getServerResponse(AdResponse.UP_AD_FAILURE);
            }

            Enclosure enclosure =
                    new Enclosure(CodeUtil.getId(), AdConst.MODULE, advertise.getAdPicture(), pictureName,
                            uri, "0", file.getSize(), StringUtils.getFilenameExtension(pictureName), advertise.getAdLinkUrl());

            // 把图片插入数据库
            affect = adDao.insertEn(enclosure);
            if(affect <=0){
                File f = new File(path);
                f.delete();
                return ServerResponse.getServerResponse(AdResponse.UP_AD_FAILURE);
            }
        }catch (Exception e){
            File f = new File(path);
            f.delete();
            log.error("未知错误发生",e);
            return ServerResponse.getServerResponse(AdResponse.UP_AD_FAILURE);
        }

        // 默认返回成功
        return ServerResponse.getServerResponse(AdResponse.SUCCESS);
    }



    /**
     * 删除一个广告
     * @param adId 广告id
     * @return 结果
     */
    @Override
    public ServerResponse deleteAdById(String adId) {

        // 如果参数为空，删除失败
        if(CodeUtil.isBlank(adId)) {
            return ServerResponse.getServerResponse(AdResponse.DELETE_AD_FAILURE);
        }

        // 删除广告（把状态置为无效）
        int affect = adDao.deleteAdById(adId);

        // 删除失败
        if(affect <= 0) {
            return ServerResponse.getServerResponse(AdResponse.DELETE_AD_FAILURE);
        }

        return ServerResponse.getServerResponse(AdResponse.SUCCESS);
    }



    /**
     * 修改广告，如果重新上传图片，则修改图片
     * @param file 图片
     * @param dirPath 路径
     * @param advertise 广告
     * @param pictureId 被修改的图片id
     * @return
     */
    @Override
    public ServerResponse updateAd(CommonsMultipartFile file, String dirPath, Advertise advertise,
                                   String pictureId, String dir) {

        if(advertise == null) {
            return ServerResponse.getServerResponse(AdResponse.UPDATE_AD_FAILURE);
        }

        //默认不更新图片
        advertise.setAdPicture(null);
        int affect ;
        String path = null;
        String oldPath = dir + adDao.getUpdateData(advertise.getAdId()).getEnclosurePath();


        // 重新上传了文件
        if(file != null && file.getSize() > 0) {

            // 得到上传的图片名
            String pictureName = file.getOriginalFilename();

            // 得到图片上传完整路径
            path = dirPath + File.separator + pictureName;

            // 得到 /File/xx/xx
            String uri = AdUtils.getUri(path); // TODO

            // 新的图片有新的id
            advertise.setAdPicture(CodeUtil.getId());

            // 上传图片
            if(upFile(file, path , pictureName) != null) {
                return upFile(file, path, pictureName);
            }

            // 删除旧照片
            File oldfile = new File(oldPath);
            oldfile.delete();

            // 图片上传成功，将图片信息插入数据库
            try {

                Enclosure enclosure =
                        new Enclosure(null, null, advertise.getAdPicture(), pictureName,
                                uri, "0", file.getSize(), StringUtils.getFilenameExtension(pictureName), advertise.getAdLinkUrl());
                // 把图片插入数据库
                affect = adDao.updateEnById(pictureId, enclosure);
                if(affect <=0){
                    File f = new File(path);
                    f.delete();
                    return ServerResponse.getServerResponse(AdResponse.UPDATE_AD_FAILURE);
                }
            }catch (Exception e){
                File f = new File(path);
                f.delete();
                log.error("未知错误发生",e);
                return ServerResponse.getServerResponse(AdResponse.UPDATE_AD_FAILURE);
            }
        }

        // 至少要更新广告的信息
        affect = adDao.updateAdById(advertise);
        if(affect <= 0){
            return ServerResponse.getServerResponse(AdResponse.UPDATE_AD_FAILURE);
        }
        return  ServerResponse.getServerResponse(AdResponse.SUCCESS);
    }



    @Override
    public PageInfo queryAds(String adTitle, String adStatus, Integer pageNum) {

        List<AdVo> ads = new ArrayList<>();
        PageInfo pageInfo = null;
        PageUtil.toPage(pageNum, AdConst.size);

        // -1代表全部
        if(adTitle.equals("-1") && adStatus.equals("-1")) {
            ads = adDao.queryAdsByStatusAndTitle(null, null);
        } else if(adTitle.equals("-1") && !adStatus.equals("-1")) {

            ads = adDao.queryAdsByStatusAndTitle(null, adStatus);
        } else if(!adTitle.equals("-1") && adStatus.equals("-1")) {

            ads = adDao.queryAdsByStatusAndTitle(adTitle, null);
        } else if(!adTitle.equals("-1") && !adStatus.equals("-1")) {

            ads = adDao.queryAdsByStatusAndTitle(adTitle, adStatus);
        }

        return PageUtil.pageInfo(ads);
    }


    @Override
    public ServerResponse getUpdateData(String adId) {
        return ServerResponse.getServerResponse( AdResponse.GET_SUCCESS, adDao.getUpdateData(adId));
    }


    /**
     * 封装上传图片方法
     * @param file 图片
     * @param path 路径
     * @param pictureName 图片名
     * @return 上传结果 如果是null表示上传成功
     */
    private ServerResponse upFile
            (CommonsMultipartFile file, String path, String pictureName) {

        // 判断上传的是否是图片
        if(!AdUtils.isPicture(pictureName)){
            return ServerResponse.getServerResponse(AdResponse.IS_NOT_PICTURE);
        }

        //如果该文件存在，提示重复
        if(AdUtils.fileIsExits(path)){
            return ServerResponse.getServerResponse(AdResponse.PICTURE_IS_EXITS);
        }

        // 如果图片上传失败，返回失败结果
        if(!AdUtils.upFileUtils(file, new File(path))){
            return ServerResponse.getServerResponse(AdResponse.UP_PICTURE_FAILURE);
        }

        return null;
    }
}
