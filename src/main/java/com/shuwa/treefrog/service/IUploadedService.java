package com.shuwa.treefrog.service;

import com.shuwa.treefrog.model.UploadedRecord;
import org.springframework.web.multipart.MultipartFile;


public interface IUploadedService {

    /**
     * 获取上传的所有信息
     * @param file
     * @param uploadedRecord
     * @return
     */
    boolean uploadRecord (MultipartFile file, UploadedRecord uploadedRecord );

}
