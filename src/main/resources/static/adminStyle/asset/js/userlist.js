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
            var contentValue = $(this).children().eq(1).text();
            console.log(this);
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
        var user_index = 0;  //username数组的下标
        $('#dispose-box').show();
        $('#dispose-box .selectbox').hide();
        $('#dispose-box .userp').show();
        $('.titlebox h4').html('重置用户密码');
        $('.permissionBuddle .message p').html('新密码')
        $('tbody input').each(function () {
            if($(this).prop('checked')){
             aUserName[index++]= $(this).parent().siblings().eq(1).text();
            }
        });
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

    });
}
