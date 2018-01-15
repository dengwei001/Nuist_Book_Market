
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

    $('#pagination').pagination({
        pageSize: 15,//每页显示的记录条数，默认为10
        pageNumber:1,
        pageList: [15,30,40,60],//可以设置每页记录条数的列表
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        onSelectPage: function(pageNumber, pageSize){
            queryNovel();
        }
    });
    $('#bookPanel').panel('resize',{
        height:$(window).height()-94
    })
    $.ajax({
        method:'get',
        data:{
            page:1,
            rows:15
        },
        url:'/book_market/novel/queryNovel',
        success:function (data) {
            for (var i=0;i<data.rows.length;i++){
                creatBook(data.rows[i]);
            };
            $('#pagination').pagination({
                total:data.total,
            });
        }
    })

})
function creatBook(row){
    var novelDiv = $('#novelDiv');
    var bookDiv=$('<div></div>');        //创建一个父div
    bookDiv.addClass('bookDiv');    //添加css样式
    var detailLink=$('<a></a>');
    detailLink.attr('href','javascript:void(0)');
    detailLink.attr('onclick','openDetail('+row.BOOK_ID +')')
    detailLink.appendTo(bookDiv);
    //创建图片DIV
    var imgDiv=$('<div></div>');
    imgDiv.addClass('imgDiv');
    var img=$('<img/>');
    img.attr('src',row.IMAGE);
    img.appendTo(imgDiv);
    img.addClass('bookImg');
    imgDiv.appendTo(detailLink);
    //价格Div
    var priceDiv=$('<Div></Div>');
    var price=$('<b></b>');
    price.addClass('priceClass');
    price.text('￥'+row.PRICE);
    price.appendTo(priceDiv);
    priceDiv.addClass('priceDiv');
    priceDiv.appendTo(detailLink);
    //创建名称DIV
    var bookNameDiv=$('<Div></Div>');
    var p0=$('<p></p>');
    var p3=$('<p></p>');
    p0.text('《'+row.BOOK_NAME+'》 '+row.AUTHOR+'著 '+row.PRESS);
    p3.text(' 卖家：'+row.SELLER);
    p0.addClass('bookName');
    p0.appendTo(bookNameDiv);
    p3.addClass('bookName');
    p3.appendTo(bookNameDiv);
    bookNameDiv.appendTo(detailLink);

    //创建链接DIV
    var linkDiv=$('<Div></Div>');
    var carLink=$('<a></a>');
    carLink.attr('href','javascript:void(0)');
    carLink.addClass('carA');
    carLink.attr('onclick','addToShopping('+row.BOOK_ID+','+'"novel"'+')');
    var carLogo=$('<img/>')
    carLogo.attr('src','/book_market/images/shoppingCar.jpg')
    carLogo.addClass('carLogo');
    carLogo.appendTo(carLink);
    carLink.appendTo(linkDiv);
    linkDiv.addClass('linkDiv');
    linkDiv.appendTo(bookDiv);
    //添加到主DIV
    bookDiv.appendTo(novelDiv);
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
    var pager = $('#pagination').pagination('options');
    var style = $('#style').combobox('getText');
    var press = $('#press').combobox('getText');
    var bookName = $('#bookName').textbox('getText');
    $.ajax({
        method:'get',
        data:{
            STYLE:style,
            PRESS:press,
            BOOK_NAME:bookName,
            page:pager.pageNumber,
            rows:pager.pageSize
        },
        url:'/book_market/novel/queryNovel',
        success:function (data) {
            $('#novelDiv').empty();
            for (var i=0;i<data.rows.length;i++){
                creatBook(data.rows[i]);
            };
            $('#pagination').pagination({
                total:data.total,
                pageSize:pager.pageSize
            });
            $('#bookPanel').panel('refresh');
        }
    })
}

