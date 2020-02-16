package com.shuwa.treefrog.util;

import com.shuwa.treefrog.constant.ConfigConstant;

import java.io.File;
import java.util.UUID;

public class FileUtils {
    /**
     * 获取文件存储数据库的文件名
     * @param fileName
     * @return
     */
    public static String getFileName(String fileName){

        if(fileName.isEmpty()) {
            return null;
        }
        fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
        return fileName;
    }

    /**
     * 返回上传路径
     * @return
     */
    public static String getUploadPath(){
        return ConfigConstant.UPLOAD_PATH;
    }

    /**
     * 获取文件后缀名
     * @param fileName
     * @return
     */
    public static String getFileSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 文件名唯一
     * @param name
     * @return
     */
    public static String getFileNameUUID(String name){

        return UUID.randomUUID().toString()+ "_" + name;
    }

    /**
     * 根据文件名的创建存储列表
     * @param filename
     * @param savaPath
     * @return
     */
    public static String makePath(String filename, String savaPath) {
        int hashcode = filename.hashCode();
        int dir1 = hashcode&0xf;
        int dir2 = (hashcode&0xf) >> 4;
        //构造新的保存目录
        String dir = savaPath + "\\" + dir1 + "\\" + dir2 + "\\";
        File file = new File(dir);
        //如果目录不存在
        if(!file.exists()) {
            file.mkdirs();//按照该路径创建目录
        }
        return dir;
    }

    /**
     * 去除文件名的的UUID唯一标识符
     * @param fileName
     * @return
     */
    public static String  getFileRealName(String fileName){
        return fileName.substring(fileName.lastIndexOf("_") + 1);
    }

}
