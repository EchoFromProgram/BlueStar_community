package com.bluestar.advertisement.vo;

import java.util.Date;

/**
 * 展示广告的视图类
 *
 * @author Imp
 * email: 1318944013@qq.com
 * date: 2018/7/18 16:44
 */
public class AdVo {

    private String adId;

    private String adTitle;

    private String adPicture; // 图片id

    private Date adCreateTime;

    private String adCreateUser;

    private String adStatus;

    private Integer adOrder;

    private String adLinkUrl;

    private String enclosureName; // 附件中文名

    private String enclosurePath; // 附件文件路径

    private long fileSize; // 附件大小

    public AdVo() {
    }

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

    public String getEnclosureName() {
        return enclosureName;
    }

    public void setEnclosureName(String enclosureName) {
        this.enclosureName = enclosureName;
    }

    public String getEnclosurePath() {
        return enclosurePath;
    }

    public void setEnclosurePath(String enclosurePath) {
        this.enclosurePath = enclosurePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "AdVo{" +
                "adId='" + adId + '\'' +
                ", adTitle='" + adTitle + '\'' +
                ", adPicture='" + adPicture + '\'' +
                ", adCreateTime=" + adCreateTime +
                ", adCreateUser='" + adCreateUser + '\'' +
                ", adStatus='" + adStatus + '\'' +
                ", adOrder=" + adOrder +
                ", adLinkUrl='" + adLinkUrl + '\'' +
                ", enclosureName='" + enclosureName + '\'' +
                ", enclosurePath='" + enclosurePath + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }
}
