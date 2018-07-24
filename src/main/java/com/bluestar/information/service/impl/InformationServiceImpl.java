package com.bluestar.information.service.impl;

import com.bluestar.common.utils.CodeUtil;
import com.bluestar.common.utils.PageUtil;
import com.bluestar.information.common.status.enums.DepartmentEnum;
import com.bluestar.information.dao.InformationDao;
import com.bluestar.information.dto.ServerResponse;
import com.bluestar.information.entity.Information;
import com.bluestar.information.service.InformationService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author: MackyHuang
 * @eamil: 973151766@qq.com
 * @createTime: 2018/7/18 11:37
 */
@Service
public class InformationServiceImpl implements InformationService {

    @Resource
    private InformationDao informationDao;

    /**
     * 根据咨询状态返回一个列表
     * 1正常，2上架，3无效
     * @param informationStatu
     * @param informationTitle
     * @return
     */
    @Override
    public PageInfo<Information> listInformationByStatu(Integer page, String informationStatu, String informationTitle) {
        List<Information> informationList = null;
        PageUtil.toPage(page);
        informationList = informationDao.listInformationByStatu(informationStatu, informationTitle);
        if(informationList != null && informationList.size() > 0){
            return PageUtil.pageInfo(informationList);
        }
        return null;
    }

    @Override
    public List<Information> listInformationByTitle(String informationTitle) {
        return null;
    }

    @Override
    public ServerResponse<Information> getInformationById(String informationId) {
        if(informationId == null){
            return ServerResponse.response(DepartmentEnum.PARAMETER_UNCOMPLETED);
        }
        Information information = informationDao.getInformationById(informationId);
        if(information != null){
            return ServerResponse.response(DepartmentEnum.SUCCESS, information);
        }else {
            return ServerResponse.response(DepartmentEnum.GET_FAILED);
        }
    }

    /**
     * 插入新资讯
     * @param information
     * @return
     */
    @Override
    public ServerResponse saveInformation(Information information) {
        if (information == null || information.getInformationCreateUser() == null ||
                information.getInformationContent() == null || information.getInformationTitle() == null ||
                information.getInformationAuthor() == null || information.getInformationStatu() == null){
            return ServerResponse.response(DepartmentEnum.PARAMETER_UNCOMPLETED);
        }
        String informationId = CodeUtil.getId();
        information.setInformationId(informationId);
        information.setInformationPublishTime(new Date());
        information.setInformationCreateTime(new Date());
        information.setInformationOrder(1);
        int result = informationDao.saveInformation(information);
        if(result > 0){
            return ServerResponse.response(DepartmentEnum.SUCCESS);
        }else {
            return ServerResponse.response(DepartmentEnum.SAVE_FAILED);
        }
    }

    @Override
    public ServerResponse updateInformation(Information information) {
        if (information == null || information.getInformationCreateUser() == null ||
                information.getInformationContent() == null || information.getInformationTitle() == null ||
                information.getInformationAuthor() == null || information.getInformationStatu() == null ||
                information.getInformationId() == null){
            return ServerResponse.response(DepartmentEnum.PARAMETER_UNCOMPLETED);
        }
        information.setInformationPublishTime(new Date());
        information.setInformationOrder(1);
        int result = informationDao.updateInformation(information);
        if(result > 0){
            return ServerResponse.response(DepartmentEnum.SUCCESS);
        }else {
            return ServerResponse.response(DepartmentEnum.UPDATE_FAILED);
        }

    }

    @Override
    public ServerResponse removeInformatin(String informationId) {
        if(informationId == null){
            return ServerResponse.response(DepartmentEnum.PARAMETER_UNCOMPLETED);
        }
        int result = informationDao.removeInformatin(informationId);
        if(result > 0){
            return ServerResponse.response(DepartmentEnum.SUCCESS);
        }else {
            return ServerResponse.response(DepartmentEnum.REMOVE_FAILED);
        }
    }

    @Override
    public Integer updateStatuByid(String informationId, String statu) {
        return null;
    }
}
