package com.shuwa.treefrog.util;

import java.io.*;

public class ReptileUtil {
    public static StringBuffer stater(String url, String name) throws IOException {
        StringBuffer startLog = new StringBuffer();

        String executePath = url + name;

        //执行命令Arr,
        // cmd /c dir 是执行完dir命令后关闭命令窗口。
        // cmd /c start dir 会打开一个新窗口后执行dir指令，原窗口会关闭。
        String[] cmdArr = new String[]{"python", executePath};
//        String[] cmdArr = new String[]{"cmd", "/c", "python","F:\\Pycharm\\Treefrog\\mySpider\\startSpiders.py"};
//        String[] cmdArr = new String[]{"cmd", "/c","start", "python","F:\\Pycharm\\Treefrog\\mySpider\\startSpiders.py"};

        //参数分别为： 执行命令；执行此脚本的路径
        Process process = Runtime.getRuntime().exec(cmdArr, null, new File(url));
//        Process process = Runtime.getRuntime().exec(cmdArr, envp, new File(path));
        try {
            //BufferedReader这段代码中的GBK是为了防止Python输出中文时乱码
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();

            //接收scrapy中的print打印：
            InputStream inputStream = process.getInputStream();
            byte[] b = new byte[1024];
            while (inputStream.read(b) != -1) {
                String writeFilePath = new String(b);
                System.out.println(writeFilePath);
            }

            //返回linux执行状态码，0为执行正常
            int statusNum = process.waitFor();
            System.out.println(statusNum);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startLog.append("启动" + url + name +"成功");
        return startLog;
    }
}
