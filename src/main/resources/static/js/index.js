$(function () {
    $('#schbook').attr('src','/book_market/schoolbook');
    $('#indexTab').tabs({
        tabPosition:'left',
        tabHeight:50,
        tabWidth:146,
        fit:true,
        onSelect:function () {
            var tab = $('#indexTab').tabs('getSelected');
            var index = $('#indexTab').tabs('getTabIndex',tab);
            if (index==0){
                window.frames['schbook'].document.location.reload()
            }
            if (index==1){
                window.frames['reference'].document.location.reload()
            }
            if (index==2){
                window.frames['novel'].document.location.reload()
            }
        }
    });
    //刷新tabs的大小
    $('#indexTab').tabs('resize',{
        width:$(window).width()-150
    })
})
window.onload=function () {
    //当页面加载完成后再加载
    $('#reference').attr('src','/book_market/reference');
    $('#novel').attr('src','/book_market/novel');
}


