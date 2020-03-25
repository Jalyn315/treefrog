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

function toast_treefrog() {
    var html = "<div class=\"toast ml-5 mt-5\" id=\"mytoast\" data-autohide=\"false\" style=\"position: fixed; top: 20%; left: 40%; width: 250px; height: 150px; z-index: 999\">\n" +
        "        <div class=\"toast-header bg-success\">\n" +
        "            <img  th:src=\"@{/assets/img/logo.png}\" alt=\"title\" width=\"30\" class=\"rounded mr-2\">\n" +
        "            <!-- mr-auto自动占完右侧空余部分 -->\n" +
        "            <strong class=\"mr-auto text-white\">提示</strong>\n" +
        // "            <small class=\"text-white\">一分钟前</small>\n" +
        "            <!-- data-dismiss=\"toast\" 可以触发关闭事件 -->\n" +
        "            <button class=\"close ml-2 mb-1\" data-dismiss=\"toast\">&times;</button>\n" +
        "        </div>\n" +
        "        <div class=\"toast-body\">\n" +
        "            你输入的账户不正确.\n" +
        "        </div>\n" +
        "    </div>";
    $('body').append(html);
}