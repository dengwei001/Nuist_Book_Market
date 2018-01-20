$(function () {
    $('#baseInfo').attr('src','/book_market/baseInfo');
    $('#userCenterTabs').tabs({
        tabPosition:'left',
        tabHeight:50,
        tabWidth:146,
        fit:true,
        onSelect:function () {
            var tab = $('#userCenterTabs').tabs('getSelected');
            var index = $('#userCenterTabs').tabs('getTabIndex',tab);
            if (index==0){
                window.frames['baseInfo'].document.location.reload()
            }
            if (index==1){
                window.frames['myStore'].document.location.reload()
            }
            if (index==2){
                window.frames['shoppingCar'].document.location.reload()
            }
            if (index==3){
                window.frames['orderCenter'].document.location.reload()
            }
        }
    });
    //刷新tabs的大小
    $('#userCenterTabs').tabs('resize',{
        width:$(window).width()-150
    })
})
window.onload=function () {
    //当页面加载完成后再加载
    $('#myStore').attr('src','/book_market/myStore');
    $('#shoppingCar').attr('src','/book_market/shoppingCar');
    $('#orderCenter').attr('src','/book_market/orderCenter');
}
