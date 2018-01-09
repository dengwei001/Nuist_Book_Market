$(function () {
    $('#paramTab').tabs({
        tabPosition:'left',
        tabHeight:50,
    });
})
window.onload=function () {
    //刷新tabs的大小
    $('#paramTab').tabs('select',1);
    $('#paramTab').tabs('select',0);
}