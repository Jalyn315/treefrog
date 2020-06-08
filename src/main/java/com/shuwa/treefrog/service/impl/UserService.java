package com.shuwa.treefrog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shuwa.treefrog.dao.FileDao;
import com.shuwa.treefrog.dao.TypeDao;
import com.shuwa.treefrog.dao.UserDao;
import com.shuwa.treefrog.entity.File;
import com.shuwa.treefrog.entity.User;
import com.shuwa.treefrog.service.IUserService;
import com.shuwa.treefrog.util.FileUtils;
import io.netty.util.internal.ResourcesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private TypeDao typeDao;

    @Autowired
    private FileDao fileDao;

    /**
     * redis 存储数据的 api
     */
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public User login(String username, String password) {
        User user = userDao.login(username, password);
        if (user == null) {
            return null;
        }
        if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

    /**
     * 修改个人信息
     *
     * @param user
     * @return
     */
    @Override
    public boolean update(User user) {
        return userDao.update(user);
    }

    /**
     * 根据Id获取用户对像
     *
     * @param id
     * @return
     */
    @Override
    public User get(Integer id) {
        return userDao.get(id);
    }

    @Override
    public boolean register(User user) {
        return userDao.addUser(user);
    }

    @Override
    public boolean isAllowRegister(String username, String phone) {
        if (username.equals(userDao.isUserNameExist(username)) && phone.equals(userDao.isPhoneExist(phone))) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isPhoneExist(String phone) {
        return phone.equals(userDao.isPhoneExist(phone));
    }

    @Override
    public boolean isUserNameExist(String username) {
        return username.equals(userDao.isUserNameExist(username));
    }

    @Override
    public boolean deleteUser(int id) {
        return userDao.deleteUser(id);
    }

    @Override
    public List<User> getAllUser() {
        return userDao.getAllUser();
    }

    /**
     * 重置用户密码
     *
     * @param password 新密码
     * @param id       用户id
     * @return
     */
    @Override
    public boolean resetassword(String password, Integer id) {
        return userDao.updataPasswordById(password, id);
    }


    @Override
    public boolean verifyPassword(String password, Integer id) {
        if (password.equals(userDao.getpasswordById(id))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getEmail(String email) {
        return userDao.getEmail(email);
    }

    @Override
    public boolean resetPasswordByEmail(String password, String email) {
        return userDao.resetPasswordByEmail(password, email);
    }

    @Override
    public String getUserNameByEmail(String email) {
        return userDao.getUserNameByEmail(email);
    }


    @Override
    public PageInfo<File> getFileByTypeNamePageQuery(String typeName, Integer currentPage, Integer limit) {
        List<File> fileList = null;
        if (typeName != null) {
            Integer id = typeDao.returnIdByName(typeName);
            PageHelper.startPage(currentPage, limit);
            fileList = fileDao.getFileByCategoryId(id);
        }
        return new PageInfo<>(fileList);
    }

    /**
     * 上传头像
     *
     * @param file
     * @returnid
     */
    @Override
    public boolean uploadUserVia(MultipartFile file, Integer id) throws Exception {
        //获取文件名
        String fileName = FileUtils.getFileName(file.getOriginalFilename());
        //获取上传路径
        java.io.File userVia = new java.io.File(ResourceUtils.getURL("classpath:static").getPath()+"/userVia");
        if (!userVia.exists()){
            userVia.mkdirs();
            System.out.println("我创建了");
        }
        String path = ResourceUtils.getURL("classpath:static/userVia").getPath() + "/";
        file.transferTo(new java.io.File(path + fileName));
        userDao.updateVia("/userVia/" + fileName, id);
        System.out.println("上传成功");
        return true;
    }

    @Override
    public String getVia(Integer id) {
        return userDao.getVia(id);
    }

//    @Override
//    public PageInfo<File> getFileByTypeName(String typeName) {
//        //先去查询 redis 缓存中是否有以 typeName 为 key 的值
//        //如果没有，则去数据库中第一次查询出以 typeName 的全部文件，放入 redis 缓存中
//
//        //然后在 redis 缓存中传入参数进行分页查询
//
//
//        return null;
//    }
//
//    @Override
//    public PageInfo<File> getFileByTypeNamePageQuery(String typeName, Integer currentPage, Integer limit) {
//        //先去查询 redis 缓存中是否有以 typeName 为 key 的值
//        //如果没有，则去数据库中第一次查询出以 typeName 的全部文件，放入 redis 缓存中
//
//        //然后在 redis 缓存中传入参数进行分页查询
//
//
//        return null;
//    }
//
//    /**
//     * 查询集合中指定顺序的值， 0 -1 表示获取全部的集合内容  zrange
//     *
//     * 返回有序的集合，score小的在前面
//     *
//     * @param key
//     * @param start
//     * @param end
//     * @return
//     */
//    public Set<String> range(String key, int start, int end) {
//        return redisTemplate.opsForZSet().range(key, start, end);
//    }

}
