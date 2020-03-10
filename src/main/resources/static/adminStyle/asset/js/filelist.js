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
function removeFiles() {
    $('.delete').click(function () {
        var index = 0;
        var id = [];
        var fileItem = [];
        $('tbody input').each(function () {
            if($(this).prop('checked')){
                id[index] = $(this).parent().siblings().eq(0).text(); //获取id
                fileItem[index] = $(this).parent().parent(); //获取祖先节点:文件信息项
                index++;
            }
        });
        if (index == 0){
            $('.msgbox #info').html('至少选择一个!');
            $('.msgbox').show();
        }else {
            $.post({
                url: "/admin/filesDelete",
                data: {"id":id},
                traditional:true,
                success: function (data) {
                    for (var i = 0; i < fileItem.length; i ++){
                        fileItem[i].remove();
                    }
                    $('.msgbox #info').html(data);
                    $('.msgbox').show();
                }
            });
        }
    });
}