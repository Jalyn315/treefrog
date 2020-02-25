# 增加此文件的目的是为了将每次所做的来不及提交的小操作进行记录，便于后序查验
## 2020年2月21日20点00分
## by summer
1. 添加 templates/commons/bar.html
2. 添加 templates/error/404.html
3. 重命名 templates/index.html 文件为 templates/index2.html 并移动到 templates/temp/ 目录下 -》12. 恢复 templates/temp/index2.html 到 templates/ 目录下，并改名为 templates/index.html
4. 添加 templates/signup.html 用户注册页面
5. 添加 templates/index.html 首页
6. 添加 resources/static/css 下所有样式文件
7. 添加 resources/static/img 下所有图片文件
8. 添加 resources/static/js 下所有 javascript 文件
9. 给 IUserService 接口添加相关注册功能
10. 实现 IUserService 接口中新添加的注册功能
11. 添加 UserServiceTest 类用于测试 UserService
13. 在 UserController 类里面添加一些注册功能相关的方法
14. 增加 ViewController 类，主要是对一些不带数据的请求进行视图的控制转发
15. 新增发送短信服务的包 com.shuwa.treefrog.util.sendSMS
16. UserController 类增加注册功能
17. 添加 maven 依赖
		<dependency>
            <groupId>com.vaadin.external.google</groupId>
            <artifactId>android-json</artifactId>
            <version>0.0.20131108.vaadin1</version>
            <scope>compile</scope>
        </dependency>
用于解析 json 数据		

18. 增加用户注册时所需的拦截器 RegisterInterceptor 用于检验用户信息填写是否合法 
19. 发现的问题：使用的 webjars 的版本不同，我使用的 3.4.1, 而你使用的是 3.3.1
20. 添加 modificationRecord.md 文件，用于将每次所做的小的操作记录下来

## 2020年2月22日16点12分
## by summer
1. 给 UserController 和 ViewController 中的每个处理请求的方法加了打印日志信息：
形式：logger.info("类名->方法名");

## 2020年2月25日15点11分
## by summer
1. 修改 LoginController.java 里面
return "redirect:/login.html"; //返回主页 -> return "redirect:login"; //返回主页
2. UserController
增加：
userUpdate() 用户更新方法 <br>
userDelete() 用户删除方法 <br>
users() 返回用户列表 <br>
ViewController 增加： <br>
user() 返回用户主页面 <br>
userList() 返回用户列表 <br>
3. 新增 list.html 列出所有用户，userUpdate.html 用户更新信息页面




