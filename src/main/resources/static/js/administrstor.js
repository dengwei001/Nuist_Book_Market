$(function () {
    $('#paramTab').tabs({
        tabPosition:'left',
        tabHeight:50,
        tabWidth:146,
        fit:true,
    });
    //刷新tabs的大小
    $('#paramTab').tabs('resize',{
        width:$(window).width()-150
    })
})
