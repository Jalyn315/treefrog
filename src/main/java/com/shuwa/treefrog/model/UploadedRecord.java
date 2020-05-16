package com.shuwa.treefrog.model;


import java.util.Date;

/**
 * 上传记录
 */
public class UploadedRecord {
    private long fileId; // 文件ID

    private Integer userId; //上传用户ID

    private String username; //上传的用户名

    private String email;  //上传用户的邮箱

    private String fileName; //文件名称

    private String categoryName;  //标签名称

    private String localUrl; //本地路径

    private String visitUrl; //访问路径

    private String description;//文件描述

    private String tag;//文件标签

    private Date date; //文件上传时间

    private long size;//文件大小

    private int categoryId; //标签ID。

    private int isDeletable;

    private int isUpdatable;

    private int isDownloadable;

    private int isVisible;

    @Override
    public String toString() {
        return "UploadedRecord{" +
                "fileId=" + fileId +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", fileName='" + fileName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", localUrl='" + localUrl + '\'' +
                ", visitUrl='" + visitUrl + '\'' +
                ", description='" + description + '\'' +
                ", tag='" + tag + '\'' +
                ", date=" + date +
                ", size=" + size +
                ", categoryId=" + categoryId +
                ", isDeletable=" + isDeletable +
                ", isUpdatable=" + isUpdatable +
                ", isDownloadable=" + isDownloadable +
                ", isVisible=" + isVisible +
                '}';
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }


    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public String getVisitUrl() {
        return visitUrl;
    }

    public void setVisitUrl(String visitUrl) {
        this.visitUrl = visitUrl;
    }

    public int getIsDeletable() {
        return isDeletable;
    }

    public void setIsDeletable(int isDeletable) {
        this.isDeletable = isDeletable;
    }

    public int getIsUpdatable() {
        return isUpdatable;
    }

    public void setIsUpdatable(int isUpdatable) {
        this.isUpdatable = isUpdatable;
    }

    public int getIsDownloadable() {
        return isDownloadable;
    }

    public void setIsDownloadable(int isDownloadable) {
        this.isDownloadable = isDownloadable;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }
}
