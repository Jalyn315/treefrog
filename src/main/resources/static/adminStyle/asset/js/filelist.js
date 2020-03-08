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