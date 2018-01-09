
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


    $('#bookGrid').datagrid('resize',{
        height:$(window).height()-62
    });

    $('#bookGrid').datagrid({
        // showHeader:false,
        fit:true,
        pagination:true,
        fitColumns:true,
        url:'/book_market/schoolBook/getBook',
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


function formatOpera(val,row){
    var operate = "<a href='javascript:void(0)' onclick='openDetail("+row.BOOK_ID+")' style='width: 100%;text-align: center'>"+"详情"+"</a>"+
        "<br/><br/>"+
        "<a href='javascript:void(0)' onclick='addToShopping("+row.BOOK_ID+")' style='width: 100%;text-align: center'>"+"加入购物车"+"</a>";
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

function addToShopping(bookId) {
    alert("加入购物车"+bookId);
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
function querySchoolBook(){

    var college = $('#college').combobox('getText');
    var specialty = $('#specialty').combobox('getText');
    var press = $('#press').combobox('getText');
    var bookName = $('#bookName').textbox('getText');
    $('#bookGrid').datagrid({
        // showHeader:false,
        queryParams:{
            COLLEGE:college,
            SPECIALTY:specialty,
            PRESS:press,
            BOOK_NAME:bookName
        },
        fit:true,
        pagination:true,
        fitColumns:true,
        url:'/book_market/schoolBook/querySchoolBook'
    });
}

