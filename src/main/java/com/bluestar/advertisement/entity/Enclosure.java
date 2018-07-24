package com.bluestar.advertisement.entity;

/**
 * 附件类
 *
 * @author Imp
 * email: 1318944013@qq.com
 * date: 2018/7/16 15:02
 */
public class Enclosure {

    private String enclosureId;

    private String businessId; // 业务id

    private String busiConId; // 业务二级id

    private String enclosureName; // 附件中文名

    private String enclosurePath; // 附件文件路径

    private String enclosureOrder; // 排序

    private long fileSize; // 附件大小

    private String extName; // 扩展名

    private String enclosureUrl; // url

    public String getEnclosureId() {
        return enclosureId;
    }

    public void setEnclosureId(String enclosureId) {
        this.enclosureId = enclosureId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusiConId() {
        return busiConId;
    }

    public void setBusiConId(String busiConId) {
        this.busiConId = busiConId;
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

    public String getEnclosureOrder() {
        return enclosureOrder;
    }

    public void setEnclosureOrder(String enclosureOrder) {
        this.enclosureOrder = enclosureOrder;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getExtName() {
        return extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    public String getEnclosureUrl() {
        return enclosureUrl;
    }

    public void setEnclosureUrl(String enclosureUrl) {
        this.enclosureUrl = enclosureUrl;
    }

    public Enclosure(String enclosureId, String businessId, String busiConId, String enclosureName, String enclosurePath, String enclosureOrder, long fileSize, String extName, String enclosureUrl) {
        this.enclosureId = enclosureId;
        this.businessId = businessId;
        this.busiConId = busiConId;
        this.enclosureName = enclosureName;
        this.enclosurePath = enclosurePath;
        this.enclosureOrder = enclosureOrder;
        this.fileSize = fileSize;
        this.extName = extName;
        this.enclosureUrl = enclosureUrl;
    }

    public Enclosure() {
    }

    @Override
    public String toString() {
        return "Enclosure{" +
                "enclosureId='" + enclosureId + '\'' +
                ", businessId='" + businessId + '\'' +
                ", busiConId='" + busiConId + '\'' +
                ", enclosureName='" + enclosureName + '\'' +
                ", enclosurePath='" + enclosurePath + '\'' +
                ", enclosureOrder='" + enclosureOrder + '\'' +
                ", fileSize=" + fileSize +
                ", extName='" + extName + '\'' +
                ", enclosureUrl='" + enclosureUrl + '\'' +
                '}';
    }
}
