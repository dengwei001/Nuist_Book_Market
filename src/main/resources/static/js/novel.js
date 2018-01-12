
$(function () {

    $('#detail').dialog('close');

    $("#style").combobox({
        url:'/book_market/paramAdmin/getNovelStyle',// 查询的controller
        editable : false,
        width : "150px",
        panelHeight:140,
        valueField : "STYLE_CODE",   //value值
        textField : "STYLE",   //text值
        loadFilter:function (data) {
            var obj = {};
            obj.STYLE_CODE = 'all';
            obj.STYLE = '全部';
            data.splice(0,0,obj);
            var obj2 = {};
            obj2.STYLE_CODE = 'other';
            obj2.STYLE = '其他';
            data.splice(data.length,0,obj2);
            return data;
        },
        onLoadSuccess : function() {    // 数据加载完毕事件
            var data = $('#style').combobox('getData'); // 赋默认值
            var typevalue=$('#style').val();
            $("#style").combobox('select', data[0].STYLE_CODE);
            if(typevalue!=null&&typevalue!=""){
                for(var i= 0;i<data.length;i++){
                    if(typevalue == data[i].VALUE){
                        $("#style").combobox('select', data[i].STYLE_CODE);
                    }
                }
            }
        }
    })

    $("#press").combobox({
        url:'/book_market/paramAdmin/getPress',// 查询的controller
        editable : false,
        width : "150px",
        panelHeight:140,
        valueField : "PRESS_CODE",   //value值
        textField : "PRESS",   //text值
        loadFilter:function (data) {
            var obj = {};
            obj.PRESS_CODE = 'all';
            obj.PRESS = '全部';
            data.splice(0,0,obj);
            var obj2 = {};
            obj2.PRESS_CODE = 'other';
            obj2.PRESS = '其他';
            data.splice(data.length,0,obj2);
            return data;
        },
        onLoadSuccess : function() {    // 数据加载完毕事件
            var data = $('#press').combobox('getData'); // 赋默认值
            var typevalue=$('#press').val();
            $("#press").combobox('select', data[0].PRESS_CODE);
            if(typevalue!=null&&typevalue!=""){
                for(var i= 0;i<data.length;i++){
                    if(typevalue == data[i].VALUE){
                        $("#press").combobox('select', data[i].PRESS_CODE);
                    }
                }
            }
        }
    })


    $('#bookGrid').datagrid('resize',{
        height:$(window).height()-62
    });

    $('#bookGrid').datagrid({
        // showHeader:false,
        fit:true,
        pagination:true,
        fitColumns:true,
        url:'/book_market/novel/queryNovel'

    });
    $('#bookGrid').datagrid('getPager').pagination({
        pageSize: 10,//每页显示的记录条数，默认为10
        pageList: [10,20,30,50],//可以设置每页记录条数的列表
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
    });
    $('#bookGrid').datagrid('hideColumn','BOOK_ID');

})

function formatImg(val,row){
    var img = " <img  src="+row.IMAGE+" "+"style="+"width:100px;"+"/>";
    return img;
}

var novel = "novel";
function formatOpera(val,row){
    var operate = "<a href='javascript:void(0)' onclick='openDetail("+row.BOOK_ID+")' style='width: 100%;text-align: center'>"+"详情"+"</a>"+
        "<br/><br/>"+
        "<a href='javascript:void(0)' onclick='addToShopping("+row.BOOK_ID+","+'novel'+")' style='width: 100%;text-align: center'>"+"加入购物车"+"</a>";
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
                $('#old').text(detail.OLD);
                $('#damage').text(detail.DAMAGE);
                $('#abstract').text(detail.ABSTRACT);
            }
        }
    })
    $('#detail').dialog('open');
}

function addToShopping(bookId,bookType) {
    $.ajax({
        method:'post',
        cache:false,
        data:{
            BOOK_ID: bookId,
            bookType: bookType
        },
        url:'/book_market/shoppingCar/addToCar',
        success:function (data) {
            if (data == true){
                $.messager.alert({
                    title:'成功',
                    msg:'加入成功，可在个人中心购物车中查看',
                    icon:'info'
                })
            }else {
                $.messager.alert({
                    title:'成功',
                    msg:data,
                    icon:'info'
                })
            }
        }
    })
}

function queryNovel(){

    var style = $('#style').combobox('getText');
    var press = $('#press').combobox('getText');
    var bookName = $('#bookName').textbox('getText');
    $('#bookGrid').datagrid({
        // showHeader:false,
        queryParams:{
            STYLE:style,
            PRESS:press,
            BOOK_NAME:bookName
        },
        fit:true,
        pagination:true,
        fitColumns:true,
        pageSize:10,
        pageList:[10,20,30,50],
        url:'/book_market/novel/queryNovel'
    });
}

