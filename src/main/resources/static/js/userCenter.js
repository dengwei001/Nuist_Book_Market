$(function () {
    $('#userCenterTabs').tabs({
        tabPosition:'left',
        tabHeight:50,
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
                window.frames['myStore'].document.location.reload()
            }

        }
    });
})
window.onload=function () {
    //刷新iframe的大小
    $('#userCenterTabs').tabs('select',1);
    $('#userCenterTabs').tabs('select',0);
}