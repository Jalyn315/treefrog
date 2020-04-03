/*****************************重置密码*************************/
function  resetPassword(){
    var isupdatePsw = false;
    //判断原密码是否已经输入
    $('#passwordAgo').keyup(function () {
        if($(this).val().length != 0){
            $(this).removeClass('is-invalid');
        }
    });
    //判断确认密码是否达到要求
    $('#password2').keyup(function () {
        var newPassword = $('#password').val();
        if ($(this).val().length == 0){
            isupdatePsw = false;
            $(this).removeClass('is-invalid');
            $(this).removeClass('is-valid');
            return;
        }
        if($(this).val() != newPassword){
            isupdatePsw = false;
            $(this).addClass('is-invalid');
            $('#confirmation').html('两次密码不一致!');
        }
        else{
            isupdatePsw = true;
            $(this).addClass('is-valid');
            $(this).removeClass('is-invalid');
        }
    });
    //已经输入确认密码又重新更改新密码的情况
    $('#password').keyup(function () {
        //当有输入时，不在提示密码为空
        if($(this).val().length != 0){
            $(this).removeClass('is-invalid');
        }
        //如果确认密码不为空，并且两次密码不想等，则在确认密码下方提示两次密码不相等
        if ( $('#password2').val().length != 0 && $('#password2').val() != $(this).val()){
            $('#password2').addClass('is-invalid');
            isupdatePsw = false;
        }
        else{
            isupdatePsw = true;
            $('#password2').addClass('is-valid');
            $('#password2').removeClass('is-invalid');
        }
        //如果确认密码为空，取消校验信息提示
        if ($('#password2').val().length == 0){
            isupdatePsw = false;
            $('#password2').removeClass('is-invalid');
            $('#password2').removeClass('is-valid');
            return;
        }
    });

    //模态框关闭后清空内容
    $('#updatePassword').on('hidden.bs.modal', function (){
        document.getElementById("form-update").reset();
        $('#password').removeClass('is-invalid');
        $('#password2').removeClass('is-invalid');
        $('#password2').removeClass('is-valid');
        //表单不可提交
        isupdatePsw = false;
    });

    //提交
    $('#sure').click(function () {
        //新密码不嫩为空
        if($('#password').val().length == 0){
            $('#password').addClass('is-invalid');
            isupdatePsw = false;
        }
        //确认密码不能为空
        if($('#password2').val().length == 0){
            $('#confirmation').html('请输入确认密码!');
            $('#password2').addClass('is-invalid');
            isupdatePsw = false;
        }
        //原密码不能为空
        if($('#passwordAgo').val().length == 0){
            $('#passwordAgo').addClass('is-invalid');
            $('#pswAgo').html('请输入原先的密码!');
            isupdatePsw = false;
        }
        //发送ajax校验原密码是否正确
        if($('#passwordAgo').val().length != 0) {
            $.post({
                url: '/verify_passwordAgo',
                data: {"passwordAgo": $('#passwordAgo').val(), "id": $('#userId').val()},
                success: function (data) {
                    if (data != "true") {
                        $('#passwordAgo').addClass('is-invalid');
                        $('#pswAgo').html(data);
                        isupdatePsw = false;
                    }
                }
            });
        }
        //发送更改密码请求
        if(isupdatePsw){
            $.post({
                  url:'/resetPassword',
                  data:{"password":$('#password').val(),"id":$('#userId').val()},
                  success:function(data) {
                      $('.toast-body').html(data);
                      $('#updatePassword').modal('hide');
                      $('#mytoast').toast('show');
                  }
                });
        }
    });
}
/****************************end******************************/

/***************************更改用户信息***********************/
function resetUserInfo() {
    //显示用户人人信息
    $('#personal_center').click(function () {
        $.get({
            url:"/getUserInfo",
            data:{"id":$('#userId').val()},
            success:function (data) {
                $('#username').html(data.username);
                $('#realName').val(data.realName);
                if(data.sex == "男"){
                    $('#man').attr('checked', 'checked');
                }else{
                    $('#woman').attr('checked', 'checked');
                }
                $('#birth').val(data.birth);
                $('#email').val(data.email);
                $('#phone').val(data.phone);
                $('#description').val(data.description);
            }
        })
    });
    //保存修改信息
    $('#saveInfo').click(function () {
        var userid = $('#userId').val();
        var realName = $('#realName').val();
        var sex = $('input:radio[name="sex"]:checked').val();
        var birth = $('#birth').val();
        var email = $('#email').val();
        var phone = $('#phone').val();
        var description = $('#description').val();
        var form = new FormData();
        form.append('id',userid);
        form.append('realName',realName);
        form.append('sex',sex);
        form.append('birth',birth);
        form.append('email',email);
        form.append('phone',phone);
        form.append('description',description);
        $.post({
            url:"/updateUserInfo",
            processData: false,  //告诉jquery要传输data对象
            contentType: false,   //告诉jquery不需要增加请求头对于contentType的设置
            data:form,
            success:function (data) {
                $('.toast-body').html(data);
                $('#userInfo').modal('hide');
                $('#mytoast').toast('show');
            }
        });




    });

}
/***************************end******************************/

/****************************文件上传*************************/
function uploadFile() {
    //判断是否已经上传，
    var isuploaded = false;

    //隐藏表单后初始化已输入内容
    $('#uploadModal').on('hidden.bs.modal', function () {
        document.getElementById("file-form").reset();
        $('#show-fileName').html('选择文件.....');
        $('#fileType').removeClass('is-invalid');
        $('#fileDescription').removeClass('is-invalid');
        $('#file').removeClass('is-invalid');
        $('#progressBar').css("width",0+"%"); //进度条清零
        $('#progressBar').html(0+"%"); //进度条清零
        isuploaded = false;//未上传
    });
/*****************************************对信息输入进行监听*****************************/
    $('#fileDescription').bind('input propertychange', function(){
        $('#fileDescription').removeClass('is-invalid');
    });

    $('#fileType').bind('input propertychange', function(){
        $('#fileType').removeClass('is-invalid');
    });

    $('#file').bind('input propertychange', function(){
        $('#file').removeClass('is-invalid');
    });
    //提交按钮处理
    $('#upload-btn').click(function () {
        if (!isuploaded) {
            //判断是否可以上传
            var isupload = true;
            //获取上传信息
            var fileobj = $('#file')[0].files[0];
            var fileDescription = $('#fileDescription').val();
            var fileType = $('#fileType option:selected').text();
            var isVisit = $('#isVisit').prop('checked') ? 1 : 0;
            var isupdate = $('#isUpdate').prop('checked') ? 1 : 0;
            var isDelete = $('#isDelete').prop('checked') ? 1 : 0;
            var isDownload = $('#isDownload').prop('checked') ? 1 : 0;

            //下列情况不能上传文件
            if (fileobj == null || fileDescription.length == 0 || $('#fileType option:selected').val() == 0) {
                isupload = false;
            }
            /******************************************判断信息是否输入*********************************/
            if ($('#fileType option:selected').val() == 0) {
                $('#fileType').addClass('is-invalid');
            }

            if (fileDescription.length == 0) {
                $('#fileDescription').addClass('is-invalid');
            }

            if (fileobj == null) {
                $('#file').addClass('is-invalid');
            }
            //将信息存放到form对象中
            var form = new FormData();
            form.append('file', fileobj);
            form.append('tag', fileType);
            form.append('description', fileDescription);
            form.append('isDeletable', isDelete);
            form.append('isDownloadable', isDownload);
            form.append('isVisible', isVisit);
            form.append('isUpdatable', isupdate);
            form.append('userId', $('#userId').val());


            /*****文件上传*****/
            if (isupdate) {
                isuploaded = true; //已经进行上传
                //获取下载进度
                var progressBar = setInterval(function () {
                    $.ajax({
                        type: "get",
                        dataType: 'json',
                        url: "/uploadStatus",
                        success: function (result) {
                            $('#progressBar').css("width", result + "%");
                            $('#progressBar').html(result + "%");
                            if (result == 100) {
                                clearInterval(progressBar);
                            }
                        }
                    });
                }, 300);
                $.post({
                    url: "/upload",
                    data: form,
                    processData: false,  //告诉jquery要传输data对象
                    contentType: false,   //告诉jquery不需要增加请求头对于contentType的设置
                    success: function (data) {
                        $('.toast-body').html(data);
                        $('#mytoast').toast('show');
                    }
                });
            }
        }else{
            $('.toast-body').html("不能重复上传!");
            $('#mytoast').toast('show');
        }
    });
}
/*****************************end****************************/

/****************************文件查询*************************/
function fileSearch() {
    $('#search-btn').click(function () {
        var searchInfo = $('#search-text').val();
        if(searchInfo.length == 0){
            $('.toast-body').html('请输入查询条件!');
            $('#mytoast').toast('show');
        }else{
            console.log('关键字:' + searchInfo);
            $.get({
                url:"/fileQuery",
                data:{"keyWord":searchInfo},
                success:function (data) {
                    var pageNum = Math.ceil(data.length / 4);  //计算总页数
                    var presentPage = 0;  //从0开始
                    if(pageNum <= 1){
                        $('#jqueryPageList').hide();
                    }else {
                        //添加上一页图标
                        $('#jqueryPageList').append("<li class=\"page-item\" >\n" +
                            "                        <a class=\"page-link\" href=\"#\" id=\"lastPage\" aria-label=\"Previous\">\n" +
                            "                            <span aria-hidden=\"true\">&laquo;</span>\n" +
                            "                        </a>\n" +
                            "                    </li>");
                        //遍历下标
                        for (var j = 0; j < pageNum; j++){
                            var indexItem = "<li class=\"page-item\"><a class=\"page-link\" id = \"index"+j+"\" href=\"#\">"+(j+1)+"</a></li>"
                            $('#jqueryPageList').append(indexItem);
                            var pageindex = 'index'+ j;
                            //给每一个下标添加点击事件
                            $('#'+pageindex).click(function () {
                                presentPage = $(this).text() - 1;
                                addFileItem(presentPage,data);
                            })
                        }
                        //添加下一页图标
                        $('#jqueryPageList').append("<li class=\"page-item\" >\n" +
                            "                        <a class=\"page-link\" href=\"#\" id=\"nextPage\" aria-label=\"Next\">\n" +
                            "                            <span aria-hidden=\"true\">&raquo;</span>\n" +
                            "                        </a>\n" +
                            "                    </li>");
                        //上一页点击
                        $('#nextPage').click(function () {
                            if(presentPage < (pageNum - 1)){
                                presentPage++;
                                addFileItem(presentPage,data);
                            }
                        });
                        //下一页点击
                        $('#lastPage').click(function () {
                            if(presentPage > 0){
                                presentPage--;
                                addFileItem(presentPage,data);
                            }
                        });
                        //显示列表
                        $('#jqueryPageListi').show();
                    }

                    $('#jqueryResult').modal('show');
                    addFileItem(presentPage,data);
                }
            });
        }
    });
    //遍历文件项目
    function addFileItem(presentPage,data){
        //清空所有内容
        $('#resultSet').html('');
        if (data.length == 0){
            $('#resultSet').html('没有找到目标文件!');
        }
        for(var i = presentPage*4; i < presentPage*4+4 && i < data.length; i++){
            var item = " <tr>\n" +
                "                            <th>"+ (i+1) +"</th>\n" +
                "                            <td>"+data[i].name+"</td>\n" +
                "                            <td>"+data[i].userName+"</td>\n" +
                "                            <td><a href=\"#\">查看详细</a></td>\n" +
                "                            <td><a href=\"#\">立即下载</a></td>\n" +
                "                        </tr>"
            $('#resultSet').append(item);
        }
    }
    //窗口关闭后
    $('#jqueryResult').on('hidden.bs.modal', function () {
        $('#resultSet').html('');
        $('#jqueryPageList').html('');
    });
}
/****************************end*******************************/

/*****************************获取我的文件*********************/
function getMyFile() {
    var isClicked = false;

        $('#myfiles').click(function () {

            var mutationObserver = new MutationObserver(function(mutations) {
                mutations.forEach(function(mutation) {
                    /************************当DOM元素发送改变时执行的函数体***********************/
                    if(!$('#myfiles').hasClass('active')){
                        $('#myFileList').empty();
                        $('#myfilePageList').empty();
                        isClicked = false;
                    }
                    /*********************函数体结束*****************************/
                });
            });
            mutationObserver.observe($('#myfiles')[0], {
                attributes: true,
                characterData: true,
                childList: true,
                subtree: true,
                attributeOldValue: true,
                characterDataOldValue: true
            });

            if(!isClicked) {
                isClicked = true;
                $.get({
                    url: "/personalFile",
                    success: function (data) {
                        var pageNum = Math.ceil(data.length / 6);  //计算总页数
                        var presentPage = 0;  //从0开始
                        if (pageNum <= 1) {
                            $('#myfilePageList').hide();
                        } else {
                            $('#myfilePageList').empty();
                            //添加上一页图标
                            $('#myfilePageList').append("<li class=\"page-item\" >\n" +
                                "                        <a class=\"page-link\" href=\"#\" id=\"myFileLastPage\" aria-label=\"Previous\">\n" +
                                "                            <span aria-hidden=\"true\">&laquo;</span>\n" +
                                "                        </a>\n" +
                                "                    </li>");
                            //遍历下标
                            for (var j = 0; j < pageNum; j++) {
                                var indexItem = "<li class=\"page-item\"><a class=\"page-link\" id = \"index" + j + "\" href=\"#\">" + (j + 1) + "</a></li>"
                                $('#myfilePageList').append(indexItem);
                                var pageindex = 'index' + j;
                                //给每一个下标添加点击事件
                                $('#' + pageindex).click(function () {
                                    presentPage = $(this).text() - 1;
                                    addMyFileItem(presentPage, data);
                                })
                            }
                            //添加下一页图标
                            $('#myfilePageList').append("<li class=\"page-item\" >\n" +
                                "                        <a class=\"page-link\" href=\"#\" id=\"myFileNextPage\" aria-label=\"Next\">\n" +
                                "                            <span aria-hidden=\"true\">&raquo;</span>\n" +
                                "                        </a>\n" +
                                "                    </li>");
                            //下一页点击事件
                            $('#myFileNextPage').click(function () {
                                if (presentPage < (pageNum - 1)) {
                                    presentPage++;
                                    addMyFileItem(presentPage, data);
                                }
                            });
                            //上一页点击事件
                            $('#myFileLastPage').click(function () {
                                if (presentPage > 0) {
                                    presentPage--;
                                    addMyFileItem(presentPage, data);
                                }
                            });
                            //显示列表
                            $('#myfilePageList').show();
                        }
                        addMyFileItem(presentPage, data);

                        //分页展示文件项目
                        function addMyFileItem(presentPage, data) {

                            $('#myFileList').html('');//清空所有内容，重新遍历新的一页

                            if (data.length == 0) {//判断是否上传过文件
                                $('#myFileList').html('您还没有上传过文件');
                            }
                            //突出显示当前分页的下标
                            $('#myfilePageList li').each(function () {
                                if ($(this).children().html() == presentPage+1){
                                    $(this).addClass('active');
                                }else {
                                    $(this).removeClass('active');
                                }
                            });
                            //遍历当前分页da
                            for (var i = presentPage * 6; i < presentPage * 6 + 6 && i < data.length; i++) {
                                //文件项
                                {
                                    var html = "<div class=\"card mt-2 mb-2 col-lg-3 col-sm-6 ml-5 border-success\" >\n" +
                                        "                                    <div class=\"card-body text-center\">\n" +
                                        "                                        <h5 class=\"card-title\">" + data[i].name + "</h5>\n" +
                                        "                                        <a href=\"/downloadFile/"+data[i].id+"\" class=\"card-link\">下载到本地</a>\n" +
                                        "                                        <a href=\"#\" class=\"card-link\"data-toggle=\"modal\" name=\"check\" index = \""+i+"\" data-target=\"#fileInfo\">查看详细</a>\n" +
                                        "                                        <a href=\"#\" class=\"card-link\" name=\"deleteFile\" fileId = \""+data[i].id+"\">删除</a>\n" +
                                        "                                        <a href=\"#\" class=\"card-link\" data-toggle=\"modal\" fileId = \""+data[i].id+"\" name=\"update\"  data-target=\"#updateFile\">更改权限</a>\n" +
                                        "                                    </div>\n" +
                                        "                                    <div class=\"card-footer text-muted\" style=\"padding: 5px 10px\">\n" +
                                        "                                        <small class=\"card-text \"><i class=\"fa fa-eye\" aria-hidden=\"true\"></i>浏览：<span>" + data[i].checkTimes + "</span></small>\n" +
                                        "                                        &nbsp;&nbsp;&nbsp;\n" +
                                        "                                        <small class=\"card-text \"><i class=\"fa fa-download\" aria-hidden=\"true\"></i>下载：<span>" + data[i].downloadCount + "</span></small>\n" +
                                        "                                    </div>\n" +
                                        "                                </div>"
                                }
                                $('#myFileList').append(html);
                                //给查看详细按钮添加时间
                                $('#myFileList a[name="check"]').each(function () {
                                    $(this).click(function () {
                                        var temp = $(this).attr('index');
                                        $('#fileInfo h5[name="fileName"]').html(data[temp].name);
                                        $('#fileInfo strong[name="username"]').html(data[temp].userName);
                                        $('#fileInfo strong[name="uploadTime"]').html(getMyDate(data[temp].createTime));
                                        $('#fileInfo strong[name="fileSize"]').html(getFileSize(data[temp].size));
                                        $('#fileInfo strong[name="fileType"]').html(data[temp].tag);
                                        $('#fileInfo strong[name="description"]').html(data[temp].description);
                                        $('#fileInfo strong[name="pageView"]').html(data[temp].checkTimes);
                                        $('#fileInfo strong[name="downloadCount"]').html(data[temp].downloadCount);
                                        $('#fileInfo #fileDownload').attr('href',"/downloadFile/"+data[i].id);
                                    });
                                });
                                //给删除按钮添加事件
                                $('#myFileList a[name="deleteFile"]').each(function () {
                                    $(this).click(function () {
                                        $.post({
                                            url: "/admin/filesDelete",
                                            data: {"id":$(this).attr('fileId')},
                                            traditional:true,
                                            success: function (data) {
                                                $('.toast-body').html(data); //显示删除完成提示信息
                                                $('#mytoast').toast('show');
                                                isClicked = false; //标记为未点击
                                                $("#myfiles").trigger("click"); //删除完成后触发我的文件列表
                                            }
                                        });
                                   });
                                });
                                $('#myFileList a[name="update"]').each(function () {
                                    $(this).click(function () {
                                        var fileid = $(this).attr('fileId');
                                        $('#updatePermission').click(function () {
                                            var update = $('#downloadFile option:selected').val();
                                            var del = $('#fileDelete option:selected').val();
                                            var edit = $('#fileUpdate option:selected').val();
                                            var visit = $('#fileVisit option:selected').val();
                                            $.post({
                                                url:"/admin/editPermission",
                                                data:{"id": fileid, "upload":update, "delete":del, "edit":edit, "visit":visit},
                                                success:function (data) {
                                                    $('.toast-body').html(data); //显示更新完成提示信息
                                                    $('#mytoast').toast('show');
                                                }
                                            });
                                        });
                                    });
                                });

                            }
                        }
                    }
                });
        }
    });
        //判断


}
/****************************end*******************************/

/*********************************遍历类型*********************/
function getTypeList() {
    $.get({
        url:"/typeList",
        success:function (data) {
            for (var i = 0; i < data.length; i++){
                var typeItem = "<li class=\"nav-item\">\n" +
                    "              <a href=\"#fileTabPanel\" class=\"nav-link \" data-toggle=\"tab\">"+data[i].typeName+"</a>\n" +
                    "           </li>"
                $('#types').append(typeItem);
            }
            var typeItem = "<li class=\"nav-item\">\n" +
                "              <a href=\"#fileTabPanel\" class=\"nav-link \" data-toggle=\"tab\">其他</a>\n" +
                "           </li>";
            $('#types').append(typeItem);
        }
    });
}
/****************************end*******************************/

/***************************获取用户共享的文件（核心）************/
function getUserShareFile() {
    var isTypesExist = false;   //类型列表是否已经存在
    $('#fileList').click(function () {
        if(!isTypesExist){ //判读
            getTypeList(); //遍历类型列表
            isTypesExist = true;
        }
        //对导航进行监听
        var mutationObserver = new MutationObserver(function(mutations) {
            mutations.forEach(function(mutation) {
                /************************当DOM元素发送改变时执行的函数体***********************/
                if(!$('#systemFile').hasClass('active')){
                    //隐藏所有的面板

                    $('#fileTabPanel').removeClass('active show');
                    //清除type列表，方便下一次重新查询
                    $('#types li').each(function () {
                        this.remove();
                    })
                    isTypesExist = false;
                }
                /*********************函数体结束*****************************/
            });
        });
        mutationObserver.observe($('#systemFile')[0], {
            attributes: true   //只捕获属性值的改变
            // characterData: true,
            // childList: true,
            // subtree: true,
            // attributeOldValue: true,
            // characterDataOldValue: true
        });//启动监听
        $.get({     //发送请求获取数据库中的所有文件
            url:"/fileList",
            success:function (data) {
                $('#types').on("click","li",function(){   //获取type的选项列表，对其进行遍历。
                        var fileNum = [];  //保存当前类型总个数
                        var fileNumIndex = 0; //记录下标
                        $('#systemFileList').empty(); //清空显示文件的面板
                        $('#systemFilePageList').empty();//清空分页列表
                        for (var i = 0; i < data.length; i++) {   //求出与当前分类标签名称相同的文件的总个数
                            if (data[i].tag == $(this).children().text()) {
                                fileNum[fileNumIndex++] = data[i];
                            }
                        }
                         var pageNum = Math.ceil(fileNum.length / 6); //获取总页数
                         var presentPage = 0; //当前页

                         if (pageNum <= 1){  //如果总页数只有一页
                             $('#systemFilePageList').empty();
                         }else //处理分页列表问题
                             {
                             //上一页
                             $('#systemFilePageList').append("<li class=\"page-item\" >\n" +
                                 "                        <a class=\"page-link\" href=\"#\" id=\"systemFileLastPage\" aria-label=\"Previous\">\n" +
                                 "                            <span aria-hidden=\"true\">&laquo;</span>\n" +
                                 "                        </a>\n" +
                                 "                    </li>");
                             //遍历下标
                             for (var j = 0; j < pageNum; j++) {
                                 var indexItem = "<li class=\"page-item\"><a class=\"page-link\" id = \"index" + j + "\" href=\"#\">" + (j + 1) + "</a></li>"
                                 $('#systemFilePageList').append(indexItem);
                                 var pageindex = 'index' + j;
                                 //给每一个下标添加点击事件
                                 $('#' + pageindex).click(function () {
                                     $('#systemFileList').empty();
                                     presentPage = $(this).text() - 1;
                                     addSystemFileItem(presentPage,fileNum);
                                 })
                             }
                             //添加下一页图标
                             $('#systemFilePageList').append("<li class=\"page-item\" >\n" +
                                 "                        <a class=\"page-link\" href=\"#\" id=\"systemFileNextPage\" aria-label=\"Next\">\n" +
                                 "                            <span aria-hidden=\"true\">&raquo;</span>\n" +
                                 "                        </a>\n" +
                                 "                    </li>");
                             //下一页点击
                             $('#systemFileNextPage').click(function () {
                                 if (presentPage < (pageNum - 1)) {
                                     $('#systemFileList').empty();
                                     presentPage++;
                                     addSystemFileItem(presentPage,fileNum);
                                 }
                             });
                             //上一页点击
                             $('#systemFileLastPage').click(function () {
                                 if (presentPage > 0) {
                                     $('#systemFileList').empty();
                                     presentPage--;
                                     addSystemFileItem(presentPage,fileNum);
                                 }
                             });
                         }
                    //遍历文件 必须放在遍历pageList的后面
                    addSystemFileItem(presentPage,fileNum);
                         /*用于遍历文件项**/
                    function addSystemFileItem(presentPage,fileList) {
                        $('#systemFilePageList li').each(function () {
                            if ($(this).children().html() == presentPage+1){
                                $(this).addClass('active');
                            }else {
                                $(this).removeClass('active');
                            }
                        });
                        for (var i = presentPage * 6; i < presentPage * 6 + 6 && i < fileNum.length; i++){
                            {
                                var fileItem = "<div class=\"card mt-2 mb-2 col-lg-3 col-sm-6 ml-5 border-success\" >\n" +
                                    "                                    <div class=\"card-body \">\n" +
                                    "                                        <input type='hidden' value=\""+i+"\">                                                       "+
                                    "                                        <h5 class=\"card-title\">" + fileList[i].name + "</h5>\n" +
                                    "                                        <p class=\"card-title\">作者:<strong>" + fileList[i].userName + "</strong></p>\n" +
                                    "                                        <a href=\"/downloadFile/"+fileList[i].id+"\" class=\"btn btn-danger btn-sm\">立即下载</a>\n" +
                                    "                                        <a href=\"#\" class=\"btn btn-primary btn-sm \" data-toggle=\"modal\" name=\"check\" index = \""+i+"\" data-target=\"#fileInfo\">查看详细</a>\n" +
                                    "                                    </div>\n" +
                                    "                                    <div class=\"card-footer text-muted\" style=\"padding: 5px 10px\">\n" +
                                    "                                        <small class=\"card-text \"><i class=\"fa fa-eye\" aria-hidden=\"true\"></i>浏览：<span>" + fileList[i].checkTimes + "</span></small>\n" +
                                    "                                        &nbsp;&nbsp;&nbsp;\n" +
                                    "                                        <small class=\"card-text \"><i class=\"fa fa-download\" aria-hidden=\"true\"></i>下载：<span>" + fileList[i].downloadCount + "</span></small>\n" +
                                    "                                    </div>\n" +
                                    "                                </div>"
                            }
                            $('#systemFileList').append(fileItem);

                        }
                        $('#systemFileList a[name="check"]').each(function (i) {
                            $(this).click(function () {
                                var temp = $(this).attr('index');
                                $('#fileInfo h5[name="fileName"]').html(fileList[temp].name);
                                $('#fileInfo strong[name="username"]').html(fileList[temp].userName);
                                $('#fileInfo strong[name="uploadTime"]').html(getMyDate(fileList[temp].createTime));
                                $('#fileInfo strong[name="fileSize"]').html(getFileSize(fileList[temp].size));
                                $('#fileInfo strong[name="fileType"]').html(fileList[temp].tag);
                                $('#fileInfo strong[name="description"]').html(fileList[temp].description);
                                $('#fileInfo strong[name="pageView"]').html(fileList[temp].checkTimes);
                                $('#fileInfo strong[name="downloadCount"]').html(fileList[temp].downloadCount);
                            });
                        });
                    }

                });
            }
        });
    });
}
/****************************end*******************************/

/*****************************个人收藏**************************/
function getMyCollect() {
    var isClicked = false;

    $('#collectFile').click(function () {

        var mutationObserver = new MutationObserver(function(mutations) {
            mutations.forEach(function(mutation) {
                /************************当DOM元素发送改变时执行的函数体***********************/
                if(!$('#collectFile').hasClass('active')){
                    $('#collectFileList').empty();
                    $('#collectPageList').empty();
                    isClicked = false;
                }
                /*********************函数体结束*****************************/
            });
        });
        mutationObserver.observe($('#collectFile')[0], {
            attributes: true,
            // characterData: true,
            // childList: true,
            // subtree: true,
            // attributeOldValue: true,
            // characterDataOldValue: true
        });

        if(!isClicked) {
            isClicked = true;
            $.get({
                url: "/collectList",
                success: function (data) {
                    var pageNum = Math.ceil(data.length / 6);  //计算总页数
                    var presentPage = 0;  //从0开始
                    if (pageNum <= 1) {
                        $('#collectPageList').hide();
                    } else {
                        $('#collectPageList').empty();
                        //添加上一页图标
                        $('#collectPageList').append("<li class=\"page-item\" >\n" +
                            "                        <a class=\"page-link\" href=\"#\" id=\"collectLastPage\" aria-label=\"Previous\">\n" +
                            "                            <span aria-hidden=\"true\">&laquo;</span>\n" +
                            "                        </a>\n" +
                            "                    </li>");
                        //遍历下标
                        for (var j = 0; j < pageNum; j++) {
                            var indexItem = "<li class=\"page-item\"><a class=\"page-link\" id = \"index" + j + "\" href=\"#\">" + (j + 1) + "</a></li>"
                            $('#collectPageList').append(indexItem);
                            var pageindex = 'index' + j;
                            //给每一个下标添加点击事件
                            $('#' + pageindex).click(function () {
                                presentPage = $(this).text() - 1;
                                addCollectFileItem(presentPage, data);
                            })
                        }
                        //添加下一页图标
                        $('#collectPageList').append("<li class=\"page-item\" >\n" +
                            "                        <a class=\"page-link\" href=\"#\" id=\"collectNextPage\" aria-label=\"Next\">\n" +
                            "                            <span aria-hidden=\"true\">&raquo;</span>\n" +
                            "                        </a>\n" +
                            "                    </li>");
                        //下一页点击事件
                        $('#collectNextPage').click(function () {
                            if (presentPage < (pageNum - 1)) {
                                presentPage++;
                                addCollectFileItem(presentPage, data);
                            }
                        });
                        //上一页点击事件
                        $('#collectLastPage').click(function () {
                            if (presentPage > 0) {
                                presentPage--;
                                addCollectFileItem(presentPage, data);
                            }
                        });
                        //显示列表
                        $('#collectPageList').show();
                    }
                    addCollectFileItem(presentPage, data);

                    //分页展示文件项目
                    function addCollectFileItem(presentPage, data) {

                        $('#collectFileList').html('');//清空所有内容，重新遍历新的一页

                        if (data.length == 0) {//判断是否上传过文件
                            $('#collectFileList').html('您的收藏空空如也!');
                        }
                        //突出显示当前分页的下标
                        $('#collectPageList li').each(function () {
                            if ($(this).children().html() == presentPage+1){
                                $(this).addClass('active');
                            }else {
                                $(this).removeClass('active');
                            }
                        });
                        //遍历当前分页da
                        for (var i = presentPage * 6; i < presentPage * 6 + 6 && i < data.length; i++){
                            {
                                var fileItem = "<div class=\"card mt-2 mb-2 col-lg-3 col-sm-6 ml-5 border-success\" >\n" +
                                    "                                    <div class=\"card-body \">\n" +
                                    "                                        <input type='hidden' value=\""+i+"\">                                                       "+
                                    "                                        <h5 class=\"card-title\">" + data[i].name + "</h5>\n" +
                                    "                                        <p class=\"card-title\">作者:<strong>" + data[i].userName + "</strong></p>\n" +
                                    "                                        <a href=\"/downloadFile/"+data[i].id+"\" class=\"btn btn-danger btn-sm\">立即下载</a>\n" +
                                    "                                        <a href=\"#\" class=\"btn btn-primary btn-sm \" data-toggle=\"modal\" name=\"check\" index = \""+i+"\" data-target=\"#fileInfo\">查看详细</a>\n" +
                                    "                                    </div>\n" +
                                    "                                    <div class=\"card-footer text-muted\" style=\"padding: 5px 10px\">\n" +
                                    "                                        <small class=\"card-text \"><i class=\"fa fa-eye\" aria-hidden=\"true\"></i>浏览：<span>" + data[i].checkTimes + "</span></small>\n" +
                                    "                                        &nbsp;&nbsp;&nbsp;\n" +
                                    "                                        <small class=\"card-text \"><i class=\"fa fa-download\" aria-hidden=\"true\"></i>下载：<span>" + data[i].downloadCount + "</span></small>\n" +
                                    "                                    </div>\n" +
                                    "                                </div>"
                            }
                            $('#collectFileList').append(fileItem);
                            $('#collectFileList a[name="check"]').each(function () {
                                $(this).click(function () {
                                    var temp = $(this).attr('index');
                                    $('#fileInfo h5[name="fileName"]').html(data[temp].name);
                                    $('#fileInfo strong[name="username"]').html(data[temp].userName);
                                    $('#fileInfo strong[name="uploadTime"]').html(getMyDate(data[temp].createTime));
                                    $('#fileInfo strong[name="fileSize"]').html(getFileSize(data[temp].size));
                                    $('#fileInfo strong[name="fileType"]').html(data[temp].tag);
                                    $('#fileInfo strong[name="description"]').html(data[temp].description);
                                    $('#fileInfo strong[name="pageView"]').html(data[temp].checkTimes);
                                    $('#fileInfo strong[name="downloadCount"]').html(data[temp].downloadCount);
                                });
                            });
                        }
                    }
                }
            });
        }
    });
    //判断


}

/*******************************end****************************/

/************************对日期格式进行转换***********************/
function getMyDate(str){
    var oDate = new Date(str),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth()+1,
        oDay = oDate.getDate(),
        oHour = oDate.getHours(),
        oMin = oDate.getMinutes(),
        oSen = oDate.getSeconds(),
        oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay) +' '+ getzf(oHour) +':'+ getzf(oMin) +':'+getzf(oSen);//最后拼接时间
    return oTime;
};
function getzf(num){
    if(parseInt(num) < 10){
        num = '0'+num;
    }
    return num;
}
/************************end***********************/

/**************************获取文件大小*****************/
function getFileSize(size) {
    var fileSize = 0;
    if (size < 1024*1024){
        fileSize = new Number (size / 1024).toFixed(2) + "KB";
    }

    if (size > 1024*1024 && size < 1024*1024*1024){
        fileSize = new Number (size / (1024*1024)).toFixed(2) + "MB";
    }

    if (size >1024*1024*1024){
        fileSize = new Number (size / (1024*1024*1024)).toFixed(2) + "GB";
    }
    return fileSize;
}
/************************end***********************/

