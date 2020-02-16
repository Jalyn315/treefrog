package com.shuwa.treefrog.web;

import com.shuwa.treefrog.entity.File;
import com.shuwa.treefrog.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class FileController {

    @Autowired
    IFileService fileService;

    /**
     * 获取文件列表 在主页显示
     * @param model
     * @return
     */
    @GetMapping("/file_List")
    public String fileList(Model model){

        model.addAttribute("fileList", fileService.getFileList());
        return "index";
    }

    /**
     * 下载指定id文件
     * @param response
     * @param id
     * @return
     */
    @GetMapping("/downloadFile/{id}")
    public String downloadFile(HttpServletResponse response,@PathVariable("id") long id){
        fileService.downloadFile(response,id);
        return null;
    }

    /**
     * 展示我的文件
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/personalFile")
    public String personalFile(HttpSession session, Model model){
        List<File> fileList = fileService.getMyFileList(Integer.parseInt(session.getAttribute("userId").toString()));
        if(!fileList.isEmpty()){
            model.addAttribute("fileList", fileList);
        }
        return "myFile";
    }



}
