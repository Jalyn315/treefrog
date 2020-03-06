/*实现模糊查询*/
function search() {
    $('#textSearch').on('keyup', function () {
        var strValue = $(this).val();
        searchList(strValue);
    });
}
function searchList(strValue) {
    var count = 0;
    if (strValue != '') {
        $('tbody tr').each(function (i) {
            var contentValue = $(this).children().eq(2).text();
            if (contentValue.toLowerCase().indexOf(strValue.toLowerCase()) < 0) {
                $(this).hide();
                count++;
            } else {
                $(this).show();
            }
            if (count == (i + 1)) {
                $('tbody').hide();
            } else {
                $('tbody').show();
            }
        });
    } else {
        $('tbody tr').each(function (i) {
            $(this).show();
            $('tbody').show();
        });
    }
}
function  checkbox() {
    //change表示当当状态改变时，可用来判断是否被选中
    $('tbody input').change(function () {
        if($(this).prop('checked')){
            $(this).parent().parent().addClass('checked');
        }else {
            $(this).parent().parent().removeClass('checked');
        }
    });
}
function buddle(){
    $('.usern').mouseover(function(){
        $('.userBuddle').show();
    });

    $('.usern').mouseout(function(){
        $('.userBuddle').hide();
    });
    $('.selectbox').mouseover(function(){
        $('.permissionBuddle').show();
    });

    $('.selectbox').mouseout(function(){
        $('.permissionBuddle').hide();
    });

    $('.userp').mouseover(function(){
        $('.permissionBuddle').show();
    });
    $('.userp').mouseout(function(){
        $('.permissionBuddle').hide();
    });
}
function btnClose() {
    $('.titlebox button').click(function () {
        $('#dispose-box').hide();
    });
    $('#close').click(function () {
        $('#dispose-box').hide();
    });
}
function resetPassword() {

    $('.delete').click(function () {
        var aUserName = [];
        var index = 0;
        var aId = [];
        var user_index = 0;  //username数组的下标
        $('#dispose-box .selectbox').hide();
        $('#dispose-box .userp').show();
        $('.titlebox h4').html('重置用户密码');
        $('.permissionBuddle .message p').html('新密码')
        $('tbody input').each(function () {
            if($(this).prop('checked')){
             aUserName[index]= $(this).parent().siblings().eq(1).text();
             aId[index] = $(this).parent().siblings().eq(0).text(); //获取id
                index++;
            }
        });
        if (index == 0){
            $('.msgbox #info').html('至少选择一个!');
            $('.msgbox').show();
        }else {
            $('#dispose-box').show();  //如果一个都不选择则无法打开
        }
        $('#dispose-box .usern  input').val(aUserName[user_index]); //姓名框显示的第一个姓名
        $('#last1').click(function () {
                if(user_index != 0){
                    $('#dispose-box .usern  input').val(aUserName[--user_index]);
                }
        });
        $('#next1').click(function () {
            if(user_index < (index-1)){
                $('#dispose-box .usern  input').val(aUserName[++user_index]);
            }
        });
        $('#update').click(function () {
            $.post({
                url:"/resetPassword",
                data:{"password":$('#newPassword').val(),"id":aId[user_index]},
                success:function (data) {
                    $('.msgbox #info').html(data);
                    $('.msgbox').show();
                }
            });
        });
    });
}
function msg() {
    var html = "<div class=\"msgbox\">\n" +
        "            <div class=\"msgtitle\"><h4>提示</h4></div>\n" +
        "            <p id=\"info\">修改成功</p>\n" +
        "            <button id=\"sure\">确定</button>\n" +
        "        </div>";
    $('body').append(html);
    $('#sure').click(function () {
        $('.msgbox').hide();
    });
}