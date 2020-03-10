package com.shuwa.treefrog.web;

import com.github.pagehelper.PageInfo;
import com.shuwa.treefrog.entity.File;
import com.shuwa.treefrog.entity.Type;
import com.shuwa.treefrog.entity.User;
import com.shuwa.treefrog.exception.LoginException;
import com.shuwa.treefrog.exception.RegisterException;
import com.shuwa.treefrog.exception.TypeNameException;
import com.shuwa.treefrog.model.DownloadRecord;
import com.shuwa.treefrog.model.PageParam;
import com.shuwa.treefrog.model.UploadedRecord;
import com.shuwa.treefrog.service.IDownloadRecordService;
import com.shuwa.treefrog.service.IUploadedService;
import com.shuwa.treefrog.service.impl.AdminService;
import com.shuwa.treefrog.service.impl.FileService;
import com.shuwa.treefrog.service.impl.TypeService;
import com.shuwa.treefrog.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.Multipart;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于转发 管理员 相关页面
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService; // userervice -> userService
    @Autowired
    private AdminService adminService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private FileService fileService;
    @Autowired
    IDownloadRecordService downloadRecordService;
    @Autowired
    IUploadedService uploadService;

    /**
     * 进入登录界面
     *
     * @return
     */
    @GetMapping
    public String toLoginPage() {
        return "admin/login";
    }

    /**
     * 传递到主页面
     * 主页面
     * @return
     */
    @RequestMapping(value = "/user_list")
    public String userList(Model model) {
        logger.info("AdminController->userList");
        model.addAttribute("page",new PageParam());
        return "admin/userlist";
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @DeleteMapping("/userDelete/{id}")
    public String userDelete(@PathVariable("id") int id) {
        logger.info("AdminController->userDelete");
        //判断用户权限，如果不是管理员，则显示不能删除
        userService.deleteUser(id);
        return "admin/userlist";
    }

    /**
     * 处理 管理员登陆
     * @return
     */
    @PostMapping(value = "/adminLoginForm")
    public String adminLoginForm(
            @RequestParam("username") String userName
            ,@RequestParam("password") String password
            ,HttpServletRequest request
            ,Model model) {
        logger.info("AdminController->adminLoginForm");
        Map<String,String> loginErrorMap = new HashMap<>();
        try {
            adminService.login(userName,password);
        } catch (LoginException e) {
            loginErrorMap.put("loginError",e.getMessage());
            request.setAttribute("loginErrorMap", loginErrorMap);
            return "admin/login";
        }
        model.addAttribute("firstLoad",'1');//判断是否是第一次加载
        model.addAttribute("page",new PageParam());//判断是否是第一次加载
        return "admin/userlist"; //登录成功，到用户管理页面
    }

    @PostMapping(value = "/adminRegisterForm")
    public String adminRegisterForm(
            @RequestParam("username") String userName
            , @RequestParam("password") String password
            , @RequestParam("rePassword") String rePassword
            ,Model model
            ) {
        logger.info("AdminController->adminRegisterForm");
        Map<String,String> registerErrorMap = new HashMap<>();
        //进行简单的密码重复验证
        if (password != null && !password.equals(rePassword)) {
           model.addAttribute("passwordmsg","两次密码不匹配！");
            return "admin/login";
        }
        try {
            adminService.register(userName,password);
        } catch (RegisterException e) {
            logger.info(e.toString());
            model.addAttribute("usernamemsg","用户名重复！");
            return "admin/login";
        }
//        注册成功后重定向的登录页面，否则再次注册转调路径会出错
        return "redirect:/admin";
    }

    /**
     * 管理员进行用户分页查询功能
     * @param currentPage
     * @param model
     * @return
     */
    @GetMapping(value = "/users/{id}")
    public String userList(@PathVariable("id") Integer currentPage, Model model) {
        int limit =2; //页面数据个数
        PageInfo<User> pageInfo = adminService.getAllUserByPageingQuery(currentPage,limit);
        PageParam pageParam = new PageParam();
        pageParam.setPageNum(pageInfo.getPageNum());
        pageParam.setPageTotal(pageInfo.getPages());
        pageParam.setLastPage(limit);
        pageParam.setIsFirstPage(pageInfo.isIsFirstPage());
        pageParam.setIsLastPage(pageInfo.isIsLastPage());
        //传递到 admin/userlist.html 的参数
        model.addAttribute("users",pageInfo.getList());
        model.addAttribute("page",pageParam);
        return "admin/userlist";
    }

    /**
     * typelist 的分页查询
     * @param currentPage
     * @param model
     * @return
     */
    @GetMapping(value = "/types/{id}")
    public String types(@PathVariable("id") Integer currentPage, Model model){
        int limit = 2;//页面显示数据个数
        PageInfo<Type> pageInfo = typeService.typePageQuery(currentPage,limit);
        PageParam pageParam = new PageParam();
        pageParam.setPageNum(pageInfo.getPageNum());
        pageParam.setPageTotal(pageInfo.getPages());
        pageParam.setLastPage(limit);
        pageParam.setIsFirstPage(pageInfo.isIsFirstPage());
        pageParam.setIsLastPage(pageInfo.isIsLastPage());
        //传递到 admin/userlist.html 的参数
        model.addAttribute("types",pageInfo.getList());
        model.addAttribute("page",pageParam);
        return "admin/typelist";
    }

    /**
     * 保存一个 Type
     * @param typeName
     * @param model
     * @return
     */
    @PostMapping(value = "/saveType")
    public String saveType(@RequestParam("typeName") String typeName, RedirectAttributes model) {
        logger.info("AdminController->saveType");
        try {
            typeService.addType(typeName, new Date());
            model.addFlashAttribute("msg","创建成功!");
        } catch (TypeNameException e) {
            model.addFlashAttribute("addTypeError", e.getMessage());
        }
        model.addFlashAttribute("page", new PageParam());
        return "redirect:/admin/types/1";
    }

    /**
     * 删除一个type
     * @param id
     * @return
     */
    @GetMapping("/typeDelete")
    @ResponseBody
    public String typeDel(Integer id){
        String msg = "";
        if(typeService.deleteType(id)){
            msg = "删除成功!";
        }else {
            msg = "删除失败!";
        }
        return msg;
    }
    /**
     * 分页查询 file
     *
     * @param currentPage
     * @param model
     * @return
     */
    @GetMapping(value = "/files/{id}")
    public String files(@PathVariable("id") Integer currentPage, Model model) {
        int limit = 5;//页面显示数据个数
        PageInfo<File> pageInfo = fileService.filePageQuery(currentPage, limit);
        PageParam pageParam = new PageParam();
        pageParam.setPageNum(pageInfo.getPageNum());
        pageParam.setPageTotal(pageInfo.getPages());
        pageParam.setLastPage(limit);
        pageParam.setIsFirstPage(pageInfo.isIsFirstPage());
        //传递到 admin/userlist.html 的参数
        model.addAttribute("files", pageInfo.getList());
        model.addAttribute("filePage", pageParam);

        return "admin/filelist";
    }

    /**
     * 分页查询下载记录
     * @param page
     * @param model
     * @return
     */
    @GetMapping("downloads/{page}")
    public String uploadInfo(@PathVariable("page") Integer page, Model model){
        int limit = 4;
        PageInfo<DownloadRecord> downloadInfo = downloadRecordService.getDownloadRecordS(page, limit);
        PageParam pageParam = new PageParam();
        pageParam.setPageNum(downloadInfo.getPageNum());
        pageParam.setPageTotal(downloadInfo.getPages());
        pageParam.setLastPage(limit);
        pageParam.setIsFirstPage(downloadInfo.isIsFirstPage());
        pageParam.setIsLastPage(downloadInfo.isIsLastPage());
        model.addAttribute("downloads",downloadInfo.getList());
        model.addAttribute("downloadPage",pageParam);
        return "admin/downloadlist";
    }

    /**
     * 删除文件
     * @param id
     * @return
     */
    @PostMapping("/filesDelete")
    @ResponseBody
    public String deleteFiles(Long id[]){
        String msg = "";
        if(fileService.removeFile(id)){
            msg = "删除成功!";
        }else {
            msg = "删除失败!";
        }
        return msg;
    }

    /**
     * 管理员上传文件
     * @param file
     * @param tag
     * @param description
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public String adminUploadFiles(@RequestParam("file") MultipartFile file, String tag, String description){
        String msg = "上传成功";
        UploadedRecord uploadedRecord = new UploadedRecord();
        uploadedRecord.setUserId(0); //0表示管理员上传
        uploadedRecord.setTag(tag);
        uploadedRecord.setDescription(description);
        if(uploadService.uploadRecord(file, uploadedRecord)){
            logger.info("文件上传成功");
           msg = "文件上传成功!";
        }else{
            logger.info("文件上传失败");
            msg = "文件上传失败";
        }
        return msg;
    }

    /**
     * 权限管理
     * @param currentPage
     * @param model
     * @return
     */
    @GetMapping(value = "/permissions/{id}")
    public String permission(@PathVariable("id") Integer currentPage, Model model) {
        int limit = 5;//页面显示数据个数
        PageInfo<File> pageInfo = fileService.filePageQuery(currentPage, limit);
        PageParam pageParam = new PageParam();
        pageParam.setPageNum(pageInfo.getPageNum());
        pageParam.setPageTotal(pageInfo.getPages());
        pageParam.setLastPage(limit);
        pageParam.setIsFirstPage(pageInfo.isIsFirstPage());
        //传递到 admin/userlist.html 的参数
        model.addAttribute("files", pageInfo.getList());
        model.addAttribute("filePage", pageParam);
        return "admin/permissionlist";
    }

    /**
     * 更改权限
     * @param id
     * @param upload
     * @param delete
     * @param edit
     * @param visit
     * @return
     */
    @PostMapping("/editPermission")
    @ResponseBody
    public String editPermisson(Long id, Integer upload, Integer delete, Integer edit, Integer visit){
        String msg = "";
        if (fileService.updatePermission(id,upload,delete,edit,visit)){
            msg = "权限已更新!";
        }else {
            msg =  "权限更新失败!";
        }
        return msg;
    }


    @GetMapping(value = "/uploads")
    public String uploads() {
        return "admin/uploadlist";
    }
    @GetMapping(value = "/downloads")
    public String downloads() {
        return "admin/downloadlist";
    }
    @GetMapping(value = "/permissions")
    public String permissions(){
        return "admin/permissionlist";
    }




    @GetMapping(value = "/systemset")
    public String systemSet(){
        return "admin/system";
    }
}
