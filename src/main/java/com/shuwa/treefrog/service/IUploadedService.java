package com.shuwa.treefrog.service;

import com.github.pagehelper.PageInfo;
import com.shuwa.treefrog.entity.File;
import com.shuwa.treefrog.model.UploadedRecord;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface IUploadedService {

    /**
     * 获取上传的所有信息
     * @param file
     * @param uploadedRecord
     * @return
     */
    boolean uploadRecord (MultipartFile file, UploadedRecord uploadedRecord );

    /**
     * 获取文件上传记录
     * @return
     */
    PageInfo<File> fileUploadInfo(Integer page, Integer limit);

}
