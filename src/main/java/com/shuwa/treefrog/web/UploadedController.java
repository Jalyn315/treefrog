package com.shuwa.treefrog.web;


import com.github.pagehelper.PageInfo;
import com.shuwa.treefrog.entity.File;
import com.shuwa.treefrog.model.PageParam;
import com.shuwa.treefrog.model.UploadedRecord;
import com.shuwa.treefrog.service.IUploadedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
public class UploadedController {
    //定制日志 打印类的日志
    private static final Logger logger = LoggerFactory.getLogger(UploadedController.class);
    @Autowired
    IUploadedService uploadService;

    /**
     * 来到请求页面
     * @return
     */
    @GetMapping("/upload")
    public String  toUploadPage(HttpSession session ){
        return "upload";
    }


    public UploadedController() {
    }

    /**
     * 文件上传处理
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public String  upload(@RequestParam("file") MultipartFile file, UploadedRecord uploadedRecord, Model model){


        if(uploadService.uploadRecord(file, uploadedRecord)){
            logger.info("文件上传成功");
            model.addAttribute("msg","上传成功");
        }else{
            logger.info("文件上传失败");
            model.addAttribute("msg","文件上传失败");
        }
        return "success";
    }
    @GetMapping("admin/uploads/{page}")
    public String uploadInfo(@PathVariable("page") Integer page, Model model){
        int limit = 4;
        PageInfo<File> uploadInfo = uploadService.fileUploadInfo(page, limit);
        PageParam pageParam = new PageParam();
        pageParam.setPageNum(uploadInfo.getPageNum());
        pageParam.setPageTotal(uploadInfo.getPages());
        pageParam.setLastPage(limit);
        pageParam.setIsFirstPage(uploadInfo.isIsFirstPage());
        pageParam.setIsLastPage(uploadInfo.isIsLastPage());
        model.addAttribute("uploads",uploadInfo.getList());
        model.addAttribute("uploadPage",pageParam);
        return "admin/uploadlist";
    }


}
