$(function () {
    $('#detail').dialog('close');
    $('#shoppingCar').datagrid({
        fit:false,
        fitColumns:true,
        pagination:true,
        url:'/book_market/shoppingCar/getCar',
        loadFilter:function (data) {
            for (var i=0;i<data.rows.length;i++){
                // 两种添加写法
                if (!data.rows[i].hasOwnProperty('COLLEGE')){
                    data.rows[i].COLLEGE='——'
                }
                if (!data.rows[i].hasOwnProperty('SPECIALTY')){
                    data.rows[i]['SPECIALTY']='——'                    }
                if (!data.rows[i].hasOwnProperty('TYPE')){
                    data.rows[i]['TYPE']='——'                    }
                if (!data.rows[i].hasOwnProperty('STYLE')){
                    data.rows[i]['STYLE']='——'                    }
            }
            return data;
        }
    })
    $('#shoppingCar').datagrid('getPager').pagination({
        pageSize: 10,//每页显示的记录条数，默认为10
        pageList: [10,20,30,50],//可以设置每页记录条数的列表
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
    });
})
function formatImg(val,row){
    var img = " <img  src="+row.IMAGE+" "+"style="+"width:100px;"+"/>";
    return img;
}

function formatOpera(val,row){
    var operate = "<a href='javascript:void(0)' onclick='openDetail("+row.BOOK_ID+")' style='width: 100%;text-align: center'>"+"详情"+"</a>"+
        "<br/><br/>"+
        "<a href='javascript:void(0)' onclick='addToShopping("+row.BOOK_ID+")' style='width: 100%;text-align: center'>"+"移出购物车"+"</a>";
    return operate;
}
function openDetail(bookId) {
    $.ajax({
        method:'get',
        data:{
            BOOK_ID :bookId
        },
        url:'/book_market/combine/getDetail',
        success:function (data) {
            if (data!=null){
                var detail = data[0];
                $('#bigImg').attr('src',detail.BIGIMAGE);
                $('#oldDetail').text(detail.OLD);
                $('#damageDetail').text(detail.DAMAGE);
                $('#abstract').text(detail.ABSTRACT);
            }
        }
    })
    $('#detail').dialog('open');
}