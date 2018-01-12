$(function () {
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

        }
    });
    //刷新tabs的大小
    $('#userCenterTabs').tabs('resize',{
        width:$(window).width()-150
    })
})
