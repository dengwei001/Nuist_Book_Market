$(function () {
    $('#indexTab').tabs({
        tabPosition:'left',
        tabHeight:50,
        tabWidth:146,
        fit:true,
        onLoadSuccess:function () {
            
        },
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



