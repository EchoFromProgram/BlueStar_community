package com.bluestar.information.dao;

import com.bluestar.information.entity.Information;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: MackyHuang
 * @eamil: 973151766@qq.com
 * @createTime: 2018/7/16 15:02
 */
public interface InformationDao {

    /**
     * 根据infomationId查找一个咨询
     * @param informationId
     * @return
     */
    Information getInformationById(String informationId);

    /**
     * 列举所有资讯 按照标题状态模糊查询
     * TODO 分页
     * @return
     */
    List<Information> listInformationByStatu(@Param("informationStatu") String informationStatu, @Param("informationTitle") String informationTitle);

    /**
     * 按照标题模糊查询
     * @param informationTitle
     * @return
     */
    List<Information> listInformationByTitle(@Param("informationTitle") String informationTitle);

    /**
     * 添加一条咨询
     *
     * @param information
     * @return
     */
    Integer saveInformation(Information information);

    /**
     * 更新一条资讯
     * @param information
     * @return
     */
    Integer updateInformation(Information information);

    /**
     * 删除资讯
     * @param informationId
     * @return
     */
    Integer removeInformatin(String informationId);

    /**
     * 根据id修改状态
     * @param informationId
     * @param statu
     * @return
     */
    Integer updateStatuByid(@Param("informationId") String informationId, @Param("statu")String statu);

}
