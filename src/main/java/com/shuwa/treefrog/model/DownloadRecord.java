package com.shuwa.treefrog.model;

import java.util.Date;

public class DownloadRecord {

    private long id;
    private String userName;
    private String fileName;
    private String fileUrl;
    private long fileSize;
    private String type;
    private Date time;

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public Date getDownloadTime() {
        return time;
    }

    public void setDownloadTime(Date downloadTime) {
        this.time = downloadTime;
    }
}
