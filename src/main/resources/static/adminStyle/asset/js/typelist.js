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

function del() {
    $('.del').click(function () {
        var typeItem = $(this).parent().parent();

        $.get({
            url: "/admin/typeDelete",
            data: {"id": $(this).parent().siblings().eq(0).text()},
            success: function (data) {
                $('.msgbox #info').html(data);
                $('.msgbox').show();
                typeItem.remove();
            }
        });
    });
}