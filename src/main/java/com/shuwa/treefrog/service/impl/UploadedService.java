package com.shuwa.treefrog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shuwa.treefrog.dao.UserDao;
import com.shuwa.treefrog.entity.File;
import com.shuwa.treefrog.model.UploadedRecord;
import com.shuwa.treefrog.service.IFileService;
import com.shuwa.treefrog.service.IUploadedService;
import com.shuwa.treefrog.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class UploadedService implements IUploadedService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private IFileService fileService;
    @Override
    public boolean uploadRecord(MultipartFile file, UploadedRecord uploadedRecord)
    {
        //获取文件名
        String fileName = FileUtils.getFileName(file.getOriginalFilename());
        //获取上传路径
        String uploadPath = FileUtils.getUploadPath();
        /**
         * 将所有文件上信息存入上传记录类对象中
         */
        uploadedRecord.setSize(file.getSize());
        uploadedRecord.setFileName(fileName);
        uploadedRecord.setLocalUrl(uploadPath);
        uploadedRecord.setUsername( userDao.getByUserName(uploadedRecord.getUserId()));
        uploadedRecord.setEmail( userDao.getByEmail(uploadedRecord.getUserId()));
        uploadedRecord.setDate(new Date());
        System.out.println(uploadedRecord);
        if(fileService.uploaded(file,uploadedRecord)){
            //上传成功
            return true;
        }else {
            //上传失败
            return false;
        }
    }

    @Override
    public PageInfo<File> fileUploadInfo(Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        List<File> uploadInfo = fileService.getFileList();
        for(File file : uploadInfo){
            file.setName(FileUtils.getFileRealName(file.getName()));
            file.setUserName(userDao.getByUserName(file.getUserId()));
        }
        return new PageInfo<>(uploadInfo);
    }
}
