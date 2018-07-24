package com.bluestar.advertisement.entity;

import java.util.Date;

/**
 * 广告类
 *
 * @author Imp
 * email: 1318944013@qq.com
 * date: 2018/7/14 15:10
 */
public class Advertise {

    private String adId;

    private String adTitle;

    private String adPicture;

    private Date adCreateTime;

    private String adCreateUser;

    private String adStatus;

    private Integer adOrder;

    private String adLinkUrl;

    public Advertise() { }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
    }

    public String getAdPicture() {
        return adPicture;
    }

    public void setAdPicture(String adPicture) {
        this.adPicture = adPicture;
    }

    public Date getAdCreateTime() {
        return adCreateTime;
    }

    public void setAdCreateTime(Date adCreateTime) {
        this.adCreateTime = adCreateTime;
    }

    public String getAdCreateUser() {
        return adCreateUser;
    }

    public void setAdCreateUser(String adCreateUser) {
        this.adCreateUser = adCreateUser;
    }

    public String getAdStatus() {
        return adStatus;
    }

    public void setAdStatus(String adStatus) {
        this.adStatus = adStatus;
    }

    public Integer getAdOrder() {
        return adOrder;
    }

    public void setAdOrder(Integer adOrder) {
        this.adOrder = adOrder;
    }

    public String getAdLinkUrl() {
        return adLinkUrl;
    }

    public void setAdLinkUrl(String adLinkUrl) {
        this.adLinkUrl = adLinkUrl;
    }

    @Override
    public String toString() {
        return "Advertise{" +
                "adId='" + adId + '\'' +
                ", adTitle='" + adTitle + '\'' +
                ", adPicture='" + adPicture + '\'' +
                ", adCreateTime=" + adCreateTime +
                ", adCreateUser='" + adCreateUser + '\'' +
                ", adStatus='" + adStatus + '\'' +
                ", adOrder=" + adOrder +
                ", adLinkUrl='" + adLinkUrl + '\'' +
                '}';
    }
}
