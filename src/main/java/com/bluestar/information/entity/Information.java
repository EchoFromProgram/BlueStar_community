package com.bluestar.information.entity;

import java.util.Date;

/**
 * @author: MackyHuang
 * @eamil: 973151766@qq.com
 * @createTime: 2018/7/16 14:48
 */
public class Information {

    private String informationId;

    private String informationTitle;

    private String informationContent;

    private String informationEnclosure;

    //作者  默认采编人
    private String informationAuthor;

    //发布时间 默认是当前时间
    private Date informationPublishTime;

    //实际采编时间
    private Date informationCreateTime;

    //采编人
    private String informationCreateUser;

    //状态， 1正常，2上架，3无效
    private String informationStatu;

    private Integer informationOrder;

    public Information() {
    }

    public Information(String informationId, String informationTitle, String informationContent, String informationEnclosure, String informationAuthor, Date informationPublishTime, Date informationCreateTime, String informationCreateUser, String informationStatu, Integer informationOrder) {
        this.informationId = informationId;
        this.informationTitle = informationTitle;
        this.informationContent = informationContent;
        this.informationEnclosure = informationEnclosure;
        this.informationAuthor = informationAuthor;
        this.informationPublishTime = informationPublishTime;
        this.informationCreateTime = informationCreateTime;
        this.informationCreateUser = informationCreateUser;
        this.informationStatu = informationStatu;
        this.informationOrder = informationOrder;
    }

    public String getInformationId() {
        return informationId;
    }

    public void setInformationId(String informationId) {
        this.informationId = informationId;
    }

    public String getInformationTitle() {
        return informationTitle;
    }

    public void setInformationTitle(String informationTitle) {
        this.informationTitle = informationTitle;
    }

    public String getInformationContent() {
        return informationContent;
    }

    public void setInformationContent(String informationContent) {
        this.informationContent = informationContent;
    }

    public String getInformationEnclosure() {
        return informationEnclosure;
    }

    public void setInformationEnclosure(String informationEnclosure) {
        this.informationEnclosure = informationEnclosure;
    }

    public String getInformationAuthor() {
        return informationAuthor;
    }

    public void setInformationAuthor(String informationAuthor) {
        this.informationAuthor = informationAuthor;
    }

    public Date getInformationPublishTime() {
        return informationPublishTime;
    }

    public void setInformationPublishTime(Date informationPublishTime) {
        this.informationPublishTime = informationPublishTime;
    }

    public Date getInformationCreateTime() {
        return informationCreateTime;
    }

    public void setInformationCreateTime(Date informationCreateTime) {
        this.informationCreateTime = informationCreateTime;
    }

    public String getInformationCreateUser() {
        return informationCreateUser;
    }

    public void setInformationCreateUser(String informationCreateUser) {
        this.informationCreateUser = informationCreateUser;
    }

    public String getInformationStatu() {
        return informationStatu;
    }

    public void setInformationStatu(String informationStatu) {
        this.informationStatu = informationStatu;
    }

    public Integer getInformationOrder() {
        return informationOrder;
    }

    public void setInformationOrder(Integer informationOrder) {
        this.informationOrder = informationOrder;
    }

    @Override
    public String toString() {
        return "Information{" +
                "informationId='" + informationId + '\'' +
                ", informationTitle='" + informationTitle + '\'' +
                ", informationContent='" + informationContent + '\'' +
                ", informationEnclosure='" + informationEnclosure + '\'' +
                ", informationAuthor='" + informationAuthor + '\'' +
                ", informationPublishTime=" + informationPublishTime +
                ", informationCreateTime=" + informationCreateTime +
                ", informationCreateUser='" + informationCreateUser + '\'' +
                ", informationStatu='" + informationStatu + '\'' +
                ", informationOrder=" + informationOrder +
                '}';
    }
}
