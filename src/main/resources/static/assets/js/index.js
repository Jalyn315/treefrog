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