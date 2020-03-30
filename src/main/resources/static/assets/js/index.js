function  resetPasssword(){
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
function uploadFile() {
    //隐藏表单后初始化已输入内容
    $('#uploadModal').on('hidden.bs.modal', function () {
        document.getElementById("file-form").reset();
        $('#show-fileName').html('选择文件.....');
        $('#fileType').removeClass('is-invalid');
        $('#fileDescription').removeClass('is-invalid');
        $('#file').removeClass('is-invalid');
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
            $.post({
                url: "/upload",
                data: form,
                processData: false,  //告诉jquery要传输data对象
                contentType: false,   //告诉jquery不需要增加请求头对于contentType的设置
                success: function (data) {
                    $('.toast-body').html(data);
                    $('#uploadModal').modal('hide');
                    $('#mytoast').toast('show');
                }
            });
        }
    });
}
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
                console.log('点击了一次');
                $.get({
                    url: "/personalFile",
                    success: function (data) {
                        var pageNum = Math.ceil(data.length / 6);  //计算总页数
                        var presentPage = 0;  //从0开始
                        if (pageNum <= 1) {
                            $('#myfilePageList').hide();
                        } else {
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
                            //上一页点击
                            $('#myFileNextPage').click(function () {
                                if (presentPage < (pageNum - 1)) {
                                    presentPage++;
                                    addMyFileItem(presentPage, data);
                                }
                            });
                            //下一页点击
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
                            //清空所有内容
                            $('#myFileList').html('');
                            if (data.length == 0) {
                                $('#myFileList').html('您还没有上传过文件');
                            }
                            for (var i = presentPage * 6; i < presentPage * 6 + 6 && i < data.length; i++) {
                                var html = "<div class=\"card mt-2 mb-2 col-lg-3 col-sm-6 ml-5 border-success\" >\n" +
                                    "                                    <div class=\"card-body text-center\">\n" +
                                    "                                        <h5 class=\"card-title\">" + data[i].name + "</h5>\n" +
                                    "                                        <a href=\"#\" class=\"card-link\">下载到本地</a>\n" +
                                    "                                        <a href=\"#\" class=\"card-link\">查看详细</a>\n" +
                                    "                                        <a href=\"#\" class=\"card-link\">删除</a>\n" +
                                    "                                        <a href=\"#\" class=\"card-link\">更改权限</a>\n" +
                                    "                                    </div>\n" +
                                    "                                    <div class=\"card-footer text-muted\" style=\"padding: 5px 10px\">\n" +
                                    "                                        <small class=\"card-text \"><i class=\"fa fa-eye\" aria-hidden=\"true\"></i>浏览：<span>" + data[i].checkTimes + "</span></small>\n" +
                                    "                                        &nbsp;&nbsp;&nbsp;\n" +
                                    "                                        <small class=\"card-text \"><i class=\"fa fa-download\" aria-hidden=\"true\"></i>下载：<span>" + data[i].downloadCount + "</span></small>\n" +
                                    "                                    </div>\n" +
                                    "                                </div>"
                                $('#myFileList').append(html);
                            }
                        }
                    }
                });
        }
    });
        //判断


}