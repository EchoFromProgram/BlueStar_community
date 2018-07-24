package com.bluestar.advertisement.controller;

import com.bluestar.advertisement.constant.AdConst;
import com.bluestar.advertisement.dto.ServerResponse;
import com.bluestar.advertisement.entity.Advertise;
import com.bluestar.advertisement.enums.response.AdResponse;
import com.bluestar.advertisement.service.AdService;
import com.bluestar.advertisement.vo.AdVo;
import com.bluestar.common.utils.CodeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.mchange.v2.codegen.CodegenUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Imp
 * email: 1318944013@qq.com
 * date: 2018/7/18 10:13
 */
@Controller
public class AdController {

    private final AdService adService;

    @Autowired
    public AdController(AdService adService) {
        this.adService = adService;
    }


    @RequestMapping(value = "saveAd.do")
    @ResponseBody
    public ServerResponse upFile(@RequestParam("file")CommonsMultipartFile file, Advertise advertise,
                         HttpSession session) {

        // 判断是否上传图片
        if( file == null || file.getSize() == 0 ){
            return ServerResponse.getServerResponse(AdResponse.PICTURE_IS_NULL);
        }


        // 得到文件上传路径 /File/advertise/作者
        String dirPath =
                session.getServletContext().getRealPath(AdConst.DIR_PATH) + File.separator + "advertise"
                        + File.separator + advertise.getAdCreateUser() ;

        // 得到上传广告结果
        ServerResponse serverResponse = adService.saveAd(file, dirPath ,advertise);

        return  serverResponse;
    }



    @RequestMapping(value = "deleteAd.do")
    @ResponseBody
    public ServerResponse deleteAd(String adId) {
        return adService.deleteAdById(adId);
    }



    @RequestMapping(value = "updateAd.do")
    @ResponseBody
    public ServerResponse updateAd(@RequestParam(value = "file",required = false)CommonsMultipartFile file, Advertise advertise,
                                   String pictureId, HttpSession session) {


        String dirPath =
                session.getServletContext().getRealPath(AdConst.DIR_PATH) + File.separator + "advertise"
                        + File.separator + advertise.getAdCreateUser() ;



        String dir = session.getServletContext().getRealPath("/");


        // 得到更新结果
        ServerResponse serverResponse = adService.updateAd(file, dirPath ,advertise, pictureId, dir);

        return  serverResponse;
    }


    @RequestMapping(value = "listAd.do")
    public String listAd(Model model, @RequestParam(value = "adTitle", defaultValue = "-1")String adTitle,
                         @RequestParam(value = "adStatus",defaultValue = "-1") String adStatus,
                         @RequestParam(value = "pageNum", required = false)Integer pageNum ) {

        // 第一次访问
        if(pageNum == null){
            pageNum = 1;
        }

        // 保存标题
        if(!adTitle.equals("-1")) {
            model.addAttribute("title",adTitle);
        } else {
            model.addAttribute("title",null);
        }

        // 保存状态
        model.addAttribute("status",adStatus);

        if(CodeUtil.isBlank(adTitle) || CodeUtil.isBlank(adStatus)) {
            model.addAttribute(AdConst.LIST_FAILURE);
        }

        List<AdVo> ads = new ArrayList<>();

        // 得到分页信息
        PageInfo pageInfo = adService.queryAds(adTitle ,adStatus, pageNum );

        // 单独保存数据
        ads = pageInfo.getList();
        model.addAttribute("ads", ads);

        ObjectMapper objectMapper = new ObjectMapper();
        String pageList = null;
        try {
            // 把对象转换成json格式
            pageList = objectMapper.writeValueAsString(pageInfo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        model.addAttribute("pageInfo", pageList);

        return "advertisement/advertise_list.jsp";
    }


    @RequestMapping(value = "getUpdateData.do")
    @ResponseBody
    public ServerResponse getUpdata(String adId) {

        if(adId == null ) {
            return ServerResponse.getServerResponse(AdResponse.GET_DATA_FAILURE);
        }
        return adService.getUpdateData(adId);
    }
}
