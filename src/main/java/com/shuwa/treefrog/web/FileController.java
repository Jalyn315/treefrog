package com.shuwa.treefrog.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shuwa.treefrog.entity.File;
import com.shuwa.treefrog.model.PageParam;
import com.shuwa.treefrog.service.IFileService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class FileController {

    @Autowired
    IFileService fileService;

    /**
     *
     * 分页查询文件
     *
     * @param model
     * @return
     */
    @GetMapping("/file_List")
    public String fileList(Model model){
        model.addAttribute("fileList", fileService.getFileList());
        return "index";
    }

    /**
     * 分页查询Controller接口
     * @param page
     * @param model
     * @return
     */

    @GetMapping("/listfile/{id}")
    public String fils(@PathVariable("id") Integer page, Model model){
        int limit = 4; //页面数据个数
        PageInfo<File> pageInfo = fileService.listFiles(page,limit);
        PageParam pageParam = new PageParam();
        pageParam.setPageNum(pageInfo.getPageNum());
        pageParam.setPageTotal(pageInfo.getTotal());
        pageParam.setLastPage(limit);
        pageParam.setIsFirstPage(pageInfo.isIsFirstPage());
        pageParam.setIsLastPage(pageInfo.isIsLastPage());
        model.addAttribute("files",pageInfo.getList());
        model.addAttribute("page",pageParam);

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
