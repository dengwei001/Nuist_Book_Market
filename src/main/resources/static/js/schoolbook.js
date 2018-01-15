
$(function () {
    $('#detail').dialog('close');

    $("#college").combobox({
        url:'/book_market/paramAdmin/getCollege',// 查询的controller
        editable : false,
        width : "150px",
        panelHeight:140,
        valueField : "COLLEGE_CODE",   //value值
        textField : "COLLEGE",   //text值
        loadFilter:function (data) {
            var obj = {};
            obj.COLLEGE_CODE = 'all';
            obj.COLLEGE = '全部';
            data.splice(0,0,obj);
            return data;
        },
        onLoadSuccess : function() {    // 数据加载完毕事件
            var data = $('#college').combobox('getData'); // 赋默认值
            var typevalue=$('#college').val();
            $("#college").combobox('select', data[0].COLLEGE_CODE);
            if(typevalue!=null&&typevalue!=""){
                for(var i= 0;i<data.length;i++){
                    if(typevalue == data[i].VALUE){
                        $("#college").combobox('select', data[i].COLLEGE_CODE);
                    }
                }
            }
            var collegeCode=$("#college").combobox("getValue");
            getSpecialty(collegeCode);
        },
        onChange:function(){  //onchange事件 触发 选择下拉框值进行操作
            var collegeCode=$("#college").combobox("getValue");
            getSpecialty(collegeCode);
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
           querySchoolBook();
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
        url:'/book_market/schoolBook/getBook',
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
    var schoolBookDiv = $('#schoolBookDiv');
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
    carLink.attr('onclick','addToShopping('+row.BOOK_ID+','+'"schoolBook"'+')');
    var carLogo=$('<img/>')
    carLogo.attr('src','/book_market/images/shoppingCar.jpg')
    carLogo.addClass('carLogo');
    carLogo.appendTo(carLink);
    carLink.appendTo(linkDiv);
    linkDiv.addClass('linkDiv');
    linkDiv.appendTo(bookDiv);
    //添加到主DIV
    bookDiv.appendTo(schoolBookDiv);
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
                    title:'失败',
                    msg:data,
                    icon:'info'
                })
            }
        }
    })
}

function getSpecialty(collegeCode) {
    $("#specialty").combobox({
        queryParams:{
            COLLEGE_CODE:collegeCode
        },
        url:'/book_market/paramAdmin/getSpecialty',// 查询的controller
        editable : false,
        width : "150px",
        panelHeight:140,
        valueField : "SPECIALTY_CODE",   //value值
        textField : "SPECIALTY",   //text值
        loadFilter:function (data) {
          var obj = {};
          obj.SPECIALTY_CODE = 'all';
          obj.SPECIALTY = '全部';
          data.splice(0,0,obj);
          return data;
        },
        onLoadSuccess : function() {    // 数据加载完毕事件
            var data = $('#specialty').combobox('getData'); // 赋默认值
            var typevalue=$('#specialty').val();
            $("#specialty").combobox('select', data[0].SPECIALTY_CODE);
            if(typevalue!=null&&typevalue!=""){
                for(var i= 0;i<data.length;i++){
                    if(typevalue == data[i].VALUE){
                        $("#specialty").combobox('select', data[i].SPECIALTY_CODE);
                    }
                }
            }
        }
    })
}
function querySchoolBook(page,rows){
    var pager = $('#pagination').pagination('options');
    var college = $('#college').combobox('getText');
    var specialty = $('#specialty').combobox('getText');
    var press = $('#press').combobox('getText');
    var bookName = $('#bookName').textbox('getText');
    $.ajax({
        method:'get',
        data:{
            COLLEGE:college,
            SPECIALTY:specialty,
            PRESS:press,
            BOOK_NAME:bookName,
            page:pager.pageNumber,
            rows:pager.pageSize
        },
        url:'/book_market/schoolBook/querySchoolBook',
        success:function (data) {
            $('#schoolBookDiv').empty();
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

