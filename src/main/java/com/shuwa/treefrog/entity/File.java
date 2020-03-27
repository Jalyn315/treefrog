package com.shuwa.treefrog.entity;

import java.util.Date;
import java.sql.Timestamp;

public class File {
    private long id;

    private String name;

    private String suffix;

    private String localUrl;

    private String visitUrl;

    private long size;

    private Date createTime;

    private String description;

    private int checkTimes;

    private int downloadCount;

    private String tag;

    private Integer userId;

    private int categoryId;

    private int isUploadable;

    private int isDeletable;

    private int isUpdatable;

    private int isDownloadable;

    private int isVisible;

    private Timestamp lastModifyTime;

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
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

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCheckTimes() {
        return checkTimes;
    }

    public void setCheckTimes(int checkTimes) {
        this.checkTimes = checkTimes;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getIsUploadable() {
        return isUploadable;
    }

    public void setIsUploadable(int isUploadable) {
        this.isUploadable = isUploadable;
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

    public Timestamp getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Timestamp lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    /**
     * 文件上传构造器
     * @param id
     * @param name
     * @param suffix
     * @param localUrl
     * @param visitUrl
     * @param size
     * @param createTime
     * @param description
     * @param checkTimes
     * @param downloadTimes
     * @param tag
     * @param userId
     * @param categoryId
     * @param isUploadable
     * @param isDeletable
     * @param isUpdatable
     * @param isDownloadable
     * @param isVisible
     * @param lastModifyTime
     */
    /**
     *
     * @param id    文件id
     * @param name   文件名
     * @param suffix  文件后缀
     * @param localUrl  上传本地路径
     * @param visitUrl   访问路径
     * @param size         文件大小
     * @param createTime   上传时间
     * @param description   文件描述
     * @param checkTimes   文件浏览次数
     * @param downloadCount 文件下载次数
     * @param tag              文件标签
     * @param userId            上传用户id
     * @param categoryId        文件类别
     */
    public File(long id, String name, String suffix, String localUrl, String visitUrl, long size, Date createTime, String description, int checkTimes, int downloadCount, String tag, int userId, int categoryId) {
        this.id = id;
        this.name = name;
        this.suffix = suffix;
        this.localUrl = localUrl;
        this.visitUrl = visitUrl;
        this.size = size;
        this.createTime = createTime;
        this.description = description;
        this.checkTimes = checkTimes;
        this.downloadCount = downloadCount;
        this.tag = tag;
        this.userId = userId;
        this.categoryId = categoryId;
}

    /**
     * 文件作者设置文件权限
     * @param isDeletable
     * @param isUpdatable
     * @param isDownloadable
     * @param isVisible
     */
    public void setAuth( int isDeletable, int isUpdatable, int isDownloadable, int isVisible) {
        this.isDeletable = isDeletable;
        this.isUpdatable = isUpdatable;
        this.isDownloadable = isDownloadable;
        this.isVisible = isVisible;
    }
}
