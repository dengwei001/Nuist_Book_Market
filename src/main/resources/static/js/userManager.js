$(function () {
    $('#userManager').datagrid({
        fitColumns:true,
        pagination:true,
        pageSize:10,
        pageList:[10,20,30,50,100],
        url:'/book_market/userManager/getAllUser',

    })
})