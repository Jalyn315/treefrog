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
function search() {
    $('.List1 input').on('keyup', function () {
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
function editPermission() {
    $('.up').click(function () {
        var fileName = [];
        var index = 0;
        var aId = [];
        var file_index = 0;  //username数组的下标
        var item = [];
        $('tbody input').each(function () {
            if($(this).prop('checked')){
                fileName[index]= $(this).parent().siblings().eq(1).text();
                item [index] =  $(this).parent().parent();
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
        //默认从选择的第一个开始
        $('#dispose-box .filen  input').val(fileName[file_index]);

        $('#last1').click(function () {
            if(file_index != 0){
                $('#dispose-box .filen  input').val(fileName[--file_index]);
            }
        });
        $('#next1').click(function () {
            if(file_index < (index-1)){
                $('#dispose-box .filen  input').val(fileName[++file_index]);
            }
        });

        $('#update').click(function () {

            var upload = $('#up option:selected').val();
            var del = $('#de option:selected').val();
            var edit = $('#ed option:selected').val();
            var visit = $('#vi option:selected').val();
            $.post({
                url:"/admin/editPermission",
                data:{"id": aId[file_index],"upload":upload,"delete":del,"edit":edit,"visit":visit},
                success:function (data) {
                    $('.msgbox #info').html(data);
                    $('.msgbox').show();
                    if ("权限已更新!" == data.toString()){
                        //更新列表项中的内容。
                        $(item[file_index]).children().eq(3).html(upload);
                        $(item[file_index]).children().eq(4).html(del);
                        $(item[file_index]).children().eq(5).html(edit);
                        $(item[file_index]).children().eq(6).html(visit);
                    }
                }
            });
        });






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