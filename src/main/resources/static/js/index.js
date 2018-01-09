$(function () {
    $('#indexTab').tabs({
        tabPosition:'left',
        tabHeight:50,
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
})
window.onload=function () {
    //刷新iframe大小
    $('#indexTab').tabs('select',1);
    $('#indexTab').tabs('select',0);
}