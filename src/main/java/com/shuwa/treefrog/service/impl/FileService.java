package com.shuwa.treefrog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shuwa.treefrog.constant.ConfigConstant;
import com.shuwa.treefrog.dao.FileDao;
import com.shuwa.treefrog.dao.UserDao;
import com.shuwa.treefrog.entity.File;
import com.shuwa.treefrog.model.DownloadRecord;
import com.shuwa.treefrog.model.UploadedRecord;
import com.shuwa.treefrog.service.IDownloadRecordService;
import com.shuwa.treefrog.service.IFileService;
import com.shuwa.treefrog.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class FileService implements IFileService {
    /**
     * 通过上传信息进行上传
     * 建立文件实体对象 将文件信息存入数据库
     *
     * @param uploadedRecord
     */

    @Autowired
    FileDao fileDao;
    @Autowired
    UserDao userDao;
    @Autowired
    IDownloadRecordService downloadRecordService;

    /**
     * 文件上传
     *
     * @param file
     * @param uploadedRecord
     * @return
     */
    @Override
    public boolean uploaded(MultipartFile file, UploadedRecord uploadedRecord) {

        if (uploadedRecord != null) {

            //文件后缀
            String suffix = FileUtils.getFileSuffix(uploadedRecord.getFileName());
            //存储路径
            String storagePath = uploadedRecord.getLocalUrl();
            //文件名唯一
            String UUID_FileName = FileUtils.getFileNameUUID(uploadedRecord.getFileName());
            //得到文件保存路径
            String fileSaveUrl = FileUtils.makePath(UUID_FileName, uploadedRecord.getLocalUrl());
            System.out.println("路径=" + fileSaveUrl);
            //判断文件是否可以上传  大小不超过限定大小      上传文件不为空
            boolean isUploaded = uploadedRecord.getSize() < ConfigConstant.MAX_SIZE && !file.isEmpty();
            if (isUploaded) {
                try {
                    file.transferTo(new java.io.File(fileSaveUrl + UUID_FileName));  //文件上传
                    //创建文件实体类 从上传记录中获取相应的文件信息
                    File file1 = new File(0, UUID_FileName, suffix, fileSaveUrl, "", uploadedRecord.getSize(),
                            uploadedRecord.getDate(), uploadedRecord.getDescription(), 0, 0,
                            uploadedRecord.getTag(), uploadedRecord.getUserId(), uploadedRecord.getCategoryId());
                    file1.setAuth(uploadedRecord.getIsDeletable(), uploadedRecord.getIsUpdatable(), uploadedRecord.getIsDownloadable(), uploadedRecord.getIsVisible());
                    //调用持久层接口 存入文件信息
                    fileDao.insertFile(file1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取数据库中所有文件信息
     *
     * @return
     */
    @Override
    public List<File> getFileList() {
        List<File> fileList = fileDao.findAll();
        //将文件列表中的所有文件名去掉UUID唯一标识符
        for (File file : fileList) {
            file.setName(FileUtils.getFileRealName(file.getName()));
            file.setUserName(userDao.getByUserName(file.getUserId()));
        }
        return fileList;
    }

    /**
     * 文件下载
     *
     * @param response 相应对象
     * @param id       文件id
     * @return
     */

    @Override
    public boolean downloadFile(HttpServletResponse response, long id, String username) {
        File file = fileDao.getFile(id);
        //获取文件名称
        String fileName = file.getName();
        System.out.println(fileName);
        //获取文件路径
        String filePath = file.getLocalUrl();
        //获取要上传的文件实体
        java.io.File file1 = new java.io.File(filePath + fileName);
        if (!file1.exists()) {
            System.out.println("文件已经删除");
            return false;
        }
        fileDao.updateDownloadTimesById(file.getId());
        DownloadRecord downloadRecord = new DownloadRecord();
        downloadRecord.setFileName(FileUtils.getFileRealName(file.getName()));
        downloadRecord.setUserName(username);
        downloadRecord.setDownloadTime(new Date());
        downloadRecord.setFileSize(file.getSize());
        downloadRecord.setType(file.getTag());
        downloadRecord.setFileUrl(file.getLocalUrl());
        downloadRecordService.addDownloadRecord(downloadRecord);
        response.setContentType("application/gorce-download");
        response.addHeader("Content-disposition", "attachment;fileName=" + FileUtils.getFileRealName(fileName));
        try {
            InputStream in = new FileInputStream(file1);
            OutputStream out = response.getOutputStream();
            byte buffer[] = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取我的文件
     *
     * @param id 用户id
     * @return
     */
    @Override
    public List<File> getMyFileList(Integer id) {

        List<File> fileList = fileDao.getMyFile(id);
        //将文件列表中的所有文件名去掉UUID唯一标识符
        for (File file : fileList) {
            file.setName(FileUtils.getFileRealName(file.getName()));
            file.setUserName(userDao.getByUserName(file.getUserId()));
        }
        return fileList;
    }

    /**
     * 分页查询
     *
     * @param page  当前页数
     * @param limit 每页显示多少条数据
     * @return
     */
    @Override
    public PageInfo<File> listFiles(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);  //这条必须在条用持久层接口之前执行，它会帮我们自动拼接sql语句
        List<File> fileList = fileDao.findAll();
        //将文件列表中的所有文件名去掉UUID唯一标识符
        for (File file : fileList) {
            file.setName(FileUtils.getFileRealName(file.getName()));
            file.setUserName(userDao.getByUserName(file.getUserId()));
        }
        return new PageInfo<>(fileList);
    }


    @Override
    public File findOne(Integer id) {
        return null;
    }

    @Override
    public File findByName(String name) {
        return null;
    }

    @Override
    public File findByUserName(String userName) {
        return null;
    }

    /**
     * 分页查询文件管理
     *
     * @param currentPage
     * @param limit
     * @return
     */
    @Override
    public PageInfo<File> filePageQuery(Integer currentPage, Integer limit) {
        PageHelper.startPage(currentPage, limit);
        List<File> fileInfo = fileDao.filePageQuery();
        for (File file : fileInfo) {
            file.setName(FileUtils.getFileRealName(file.getName()));
            file.setUserName(userDao.getByUserName(file.getUserId()));
        }
        return new PageInfo<>(fileInfo);
    }

    /**
     * 根据id删除一个或者多个文件
     *
     * @param id
     * @return
     */
    @Override
    public boolean removeFile(Long[] id) {

        for (int i = 0; i < id.length; i++) {
            //获取存储数据库中的文件信息
            File fileInfo = fileDao.getFile(id[i]);
            //获取文件名
            String fileName = fileInfo.getName();
            //获取文件路径
            String fileUrl = fileInfo.getLocalUrl();
            //获取存储目录下的文件
            java.io.File file = new java.io.File(fileUrl + fileName);
            //判断文件是否存在
            if (!file.exists()) {
                return true;
            }
            //删除数据库中的文件信息
            fileDao.removeById(id[i]);
            //删除存储目录下的文件
            file.delete();
        }
        return true;
    }

    /**
     * 更新文件权限
     *
     * @param id
     * @param upload
     * @param delete
     * @param edit
     * @param visit
     * @return
     */
    public boolean updatePermission(Long id, Integer upload, Integer delete, Integer edit, Integer visit) {
        return fileDao.updatePermission(id, upload, delete, edit, visit);
    }

    /**
     * 模糊查询
     *
     * @param keyword
     * @return
     */
    @Override
    public List<File> fuzzyQuery(String keyword) {
        List<File> fileList = fileDao.fuzzyQuery(keyword);
        for (File file : fileList) {
            file.setName(FileUtils.getFileRealName(file.getName()));
            file.setUserName(userDao.getByUserName(file.getUserId()));
        }
        return fileList;
    }

    /**
     * 查询全部文件
     *
     * @return
     */
    @Override
    public List<File> findAll() {
        List<File> fileList = fileDao.findAll();
        for (File file : fileList) {
            file.setName(FileUtils.getFileRealName(file.getName()));
            file.setUserName(userDao.getByUserName(file.getUserId()));
        }
        return fileList;
    }

    /**
     * 添加收藏
     *
     * @param fileId
     * @param userId
     * @return
     */
    @Override
    public boolean addCollect(Integer fileId, Integer userId) {
        return fileDao.collectFile(fileId, userId);
    }

    /**
     * 查询个人收藏
     *
     * @param userId
     * @return
     */
    @Override
    public List<File> findAllToCollect(Integer userId) {
        List<File> fileList = new ArrayList<>();
        for (Integer i : fileDao.findAllToCollect(userId)) {
            fileList.add(fileDao.getFile(i));
        }
        for (File file : fileList) {
            file.setName(FileUtils.getFileRealName(file.getName()));
            file.setUserName(userDao.getByUserName(file.getUserId()));
        }
        return fileList;
    }

    @Override
    public boolean removeCollect(Integer fileId, Integer userId) {
        return fileDao.deleteToCollect(fileId, userId);
    }

    /**
     * 检查是否已经被收藏
     *
     * @param fileId
     * @param userId
     * @return
     */
    @Override
    public boolean isCollected(Integer fileId, Integer userId) {
        if (fileDao.findCollectById(fileId, userId) == null) {
            return false;
        } else {
            return true;

        }
    }
}
