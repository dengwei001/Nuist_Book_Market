$(function () {
    $('#paramMain').attr('src','/book_market/paramAdmin');
    $('#paramTab').tabs({
        tabPosition:'left',
        tabHeight:50,
        tabWidth:146,
        fit:true,
        onSelect:function () {
            var tab = $('#paramTab').tabs('getSelected');
            var index = $('#paramTab').tabs('getTabIndex',tab);
            if (index==0){
                window.frames['paramMain'].document.location.reload()
            }
            if (index==1){
                window.frames['userManager'].document.location.reload()
            }
            // if (index==2){
            //     window.frames['novel'].document.location.reload()
            // }
        }
    });
    //刷新tabs的大小
    $('#paramTab').tabs('resize',{
        width:$(window).width()-150
    })
})
window.onload=function () {
    //当页面加载完成后再加载
    $('#userManager').attr('src','/book_market/userManager');
}
