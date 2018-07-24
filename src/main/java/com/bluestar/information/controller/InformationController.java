package com.bluestar.information.controller;

import com.bluestar.information.dto.ServerResponse;
import com.bluestar.information.entity.Information;
import com.bluestar.information.service.InformationService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * @author: MackyHuang
 * @eamil: 973151766@qq.com
 * @createTime: 2018/7/18 12:06
 */
@Controller
public class InformationController {

    @Resource
    private InformationService informationService;

    /**
     * 根据statu查询列表  null，代表查询所有
     * @param model
     * @param
     * @return
     * 1正常，2上架，3无效
     */
    @RequestMapping(value = "information_list.do", method = RequestMethod.GET)
    public String InformationList(Model model, String statu, String title, Integer page){
        if(page == null){
            page = 1;
        }
        if(title == ""){
            title = null;
        }
        if(statu == ""){
            title = null;
        }
        PageInfo<Information> pageInfo = null;
        List<Information> informationList = null;
        if("-1".equals(statu)){
            pageInfo = informationService.listInformationByStatu(page, null, title);
        }else {
            pageInfo = informationService.listInformationByStatu(page, (String) statu, title);
        }
        if(pageInfo == null){
            model.addAttribute("statu", statu);
            model.addAttribute("title", title);
            model.addAttribute("informationList", informationList);
            return "information/information_list.jsp";
        }
        informationList = pageInfo.getList();
        ObjectMapper objectMapper = new ObjectMapper();
        String pageList = null;
        try {
            pageList = objectMapper.writeValueAsString(pageInfo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        model.addAttribute("statu", statu);
        model.addAttribute("title", title);
        model.addAttribute("informationList", informationList);
        model.addAttribute("pageInfo", pageList);
        return "information/information_list.jsp";
    }

//    @RequestMapping(value = "information_add.do", method = RequestMethod.POST)
//    @ResponseBody
//    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request, String content, String title, String createUser) {
//        String contentType = file.getContentType();
//        String fileName = file.getOriginalFilename();
//        /*
//         * System.out.println("fileName-->" + fileName);
//         * System.out.println("getContentType-->" + contentType);
//         */
//        String filePath = request.getSession().getServletContext().getRealPath("upload/");
//        System.out.println("______________________________");
//        System.out.println(filePath);
//        System.out.println(content);
//        System.out.println(title);
//        System.out.println(createUser);
//        try {
//            uploadFile(file.getBytes(), filePath, fileName);
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//        // 返回json
//        System.out.println("上传成功!");
//        return "true";
//    }
//
//    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
//        File targetFile = new File(filePath);
//        if (!targetFile.exists()) {
//            targetFile.mkdirs();
//        }
//        FileOutputStream out = new FileOutputStream(filePath + fileName);
//        out.write(file);
//        out.flush();
//        out.close();
//    }

    /**
     * 资讯上传功能  文件在另外的部分
     * @param content
     * @param title
     * @param createUser
     * @param statu
     * @return
     */
    @RequestMapping(value = "information_addtext.do", produces = {"application/json;charset=UTF8"})
    @ResponseBody
    public ServerResponse contentSubmit(String content, String title, String createUser, String statu) {
        Information information = new Information();
        information.setInformationContent(content);
        information.setInformationAuthor(createUser);
        information.setInformationCreateUser(createUser);
        information.setInformationTitle(title);
        information.setInformationStatu(statu);
        ServerResponse serverResponse = informationService.saveInformation(information);
        return serverResponse;
    }

    /**
     * 更新前获取信息
     * @param informationId
     * @return
     */
    @RequestMapping(value = "get_information.do", produces = {"application/json;charset=UTF8"})
    @ResponseBody
    public ServerResponse getInformationById(String informationId){
        ServerResponse serverResponse = informationService.getInformationById(informationId);
        return serverResponse;
    }

    /**
     * 更新资讯
     * @param informationId
     * @param content
     * @param title
     * @param createUser
     * @param statu
     * @return
     */
    @RequestMapping(value = "update_information.do", produces = {"application/json;charset=UTF8"})
    @ResponseBody
    public ServerResponse updateInformation(String informationId, String content, String title, String createUser, String statu) {
        Information information = new Information();
        information.setInformationId(informationId);
        information.setInformationContent(content);
        information.setInformationAuthor(createUser);
        information.setInformationCreateUser(createUser);
        information.setInformationTitle(title);
        information.setInformationStatu(statu);
        ServerResponse serverResponse = informationService.updateInformation(information);
        return serverResponse;
    }

    /**
     * 删除资讯
     * @param informationId
     * @return
     */
    @RequestMapping(value = "remove_information.do", produces = {"application/json;charset=UTF8"})
    @ResponseBody
    public ServerResponse removeInformation(String informationId) {
        ServerResponse serverResponse = informationService.removeInformatin(informationId);
        return serverResponse;
    }
}
