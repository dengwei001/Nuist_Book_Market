$(function () {
    $('#upLoad').dialog('close');
    //初始化div的显示与隐藏
    $('#schoolBookUpLoad').show();
    $('#referenceUpLoad').hide();
    $('#novelUpLoad').hide();

    $('#MYBook').datagrid({
        fit:false,
        showHeader:false,
        fitColumns:true,
        pagination:true,
        view:cardview,
        url:'/book_market/myStore/getMyBook',
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
    $('#MYBook').datagrid('getPager').pagination({
        pageSize: 10,//每页显示的记录条数，默认为10
        pageList: [10,20,30,50],//可以设置每页记录条数的列表
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
    });
})

var cardview = $.extend({}, $.fn.datagrid.defaults.view, {
    renderRow: function(target, fields, frozen, rowIndex, rowData){
        if (typeof (rowData.BOOK_ID)=="undefined"){
            var cc = [];
            cc.push('<td colspan=' + fields.length + ' style="padding:10px 5px;border-bottom:1px dashed;">');
            cc.push('<div style="float:left;width: 100%;text-align: center">');
            cc.push('<p>没有相关记录</p>');
            cc.push('</div>');
            cc.push('</td>') ;
            return cc.join('');
        }else {
            var cc = [];
            if (rowData.STOCK==0){
                cc.push('<td colspan=' + fields.length + ' style="background:red;padding:10px 5px;border-bottom:1px dashed;">');
            }else {
                cc.push('<td colspan=' + fields.length + ' style="padding:10px 5px;border-bottom:1px dashed;">');
            }
            if (!frozen){
                cc.push('<div style="float:left;width: 100%">');
                cc.push('<div style="float:left;width: 15%;text-align: center">');
                cc.push('<img src="/book_market/' + rowData.IMAGE + '" style="height:100px;float:left">');
                cc.push('</div>');
                cc.push('<div style="float:left;width: 15%;padding: 0px 20px 0px 20px">');
                for(var i=1; i<7; i++){
                    var copts = $(target).datagrid('getColumnOption', fields[i]);
                    cc.push('<p style="margin-top: 3px;margin-bottom: 3px"><span class="c-label">' + copts.title + ':</span> ' + rowData[fields[i]] + '</p>');
                }
                cc.push('</div>');
                cc.push('<div style="float:left;width: 15%;padding: 0px 20px 0px 20px">');
                for(var i=7; i<13; i++){
                    var copts = $(target).datagrid('getColumnOption', fields[i]);
                    cc.push('<p style="margin-top: 3px;margin-bottom: 3px"><span class="c-label">' + copts.title + ':</span> ' + rowData[fields[i]] + '</p>');
                }
                cc.push('</div>');
                cc.push('<div style="float:left;width: 30%;padding: 0px 20px 0px 20px">');
                for(var i=13; i<14; i++){
                    var copts = $(target).datagrid('getColumnOption', fields[i]);
                    cc.push('<p style="margin-top: 3px;margin-bottom: 3px"><span class="c-label">' + copts.title + ':</span> ' + rowData[fields[i]] + '</p>');
                }
                cc.push('</div>');
                cc.push('<div style="float:right;width: *%;padding: 0px 60px 0px 20px">');
                for(var i=14; i<fields.length; i++){
                    var copts = $(target).datagrid('getColumnOption', fields[i]);
                    cc.push('<p style="margin-top: 3px;margin-bottom: 3px">'
                        +'<a href="javascript:void(0) ">修改</a>'
                        + '</p>');
                    cc.push('<p style="margin-top: 3px;margin-bottom: 3px">'
                        +'<a href="javascript:void(0) ">下架</a>'
                        + '</p>');
                    cc.push('<p style="margin-top: 3px;margin-bottom: 3px">'
                        +'<a href="javascript:void(0) ">确认</a>'
                        + '</p>');
                }
                cc.push('</div>');
                cc.push('</div>');
            }
            cc.push('</td>') ;
            return cc.join('');
        }
    }
});

//用jquery-form.js提交表单
function submitForm(id) {
    var right = validateUpLoadForm(id);
    if (right){
        var form;
        switch (id){
            case 'schoolBook':
                var form = $('#schoolForm');
                break;
            case 'reference':
                var form = $('#referenceForm');
                break;
            case 'novel':
                var form = $('#novelForm');
        }
        form.ajaxSubmit({
            url:'/book_market/myStore/upLoadBook',
            type:'post',
            dataType:'json',
            success:function (data) {
                if (data){
                    $('#MYBook').datagrid('reload');
                    $('#upLoad').dialog('close');
                    $.messager.alert({
                        title:'上传成功',
                        msg:'上传成功，可以在我的小店中查看',
                        icon:'info',
                    });
                }else {
                    $.messager.alert({
                        title:'上传失败',
                        msg:'请检查您的输入信息是否正确',
                        icon:'info',
                    });
                }
            }
        })
    }
}

function sellBook() {
    $("#college").combobox({
        url:'/book_market/paramAdmin/getCollege',// 查询的controller
        editable : false,
        valueField : "COLLEGE_CODE",   //value值
        textField : "COLLEGE",   //text值
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
    });

    $("#schoolPress").combobox({
        url:'/book_market/paramAdmin/getPress',// 查询的controller
        editable : false,
        width : "150px",
        panelHeight:140,
        valueField : "PRESS_CODE",   //value值
        textField : "PRESS",   //text值
        loadFilter:function (data) {
            var obj2 = {};
            obj2.PRESS_CODE = 'other';
            obj2.PRESS = '其他';
            data.splice(data.length,0,obj2);
            return data;
        },
        onLoadSuccess : function() {    // 数据加载完毕事件
            var data = $('#schoolPress').combobox('getData'); // 赋默认值
            var typevalue=$('#schoolPress').val();
            $("#schoolPress").combobox('select', data[0].PRESS_CODE);
            if(typevalue!=null&&typevalue!=""){
                for(var i= 0;i<data.length;i++){
                    if(typevalue == data[i].VALUE){
                        $("#schoolPress").combobox('select', data[i].PRESS_CODE);
                    }
                }
            }
        }
    })
    $("#referencePress").combobox({
        url:'/book_market/paramAdmin/getPress',// 查询的controller
        editable : false,
        width : "150px",
        panelHeight:140,
        valueField : "PRESS_CODE",   //value值
        textField : "PRESS",   //text值
        loadFilter:function (data) {
            var obj2 = {};
            obj2.PRESS_CODE = 'other';
            obj2.PRESS = '其他';
            data.splice(data.length,0,obj2);
            return data;
        },
        onLoadSuccess : function() {    // 数据加载完毕事件
            var data = $('#referencePress').combobox('getData'); // 赋默认值
            var typevalue=$('#referencePress').val();
            $("#referencePress").combobox('select', data[0].PRESS_CODE);
            if(typevalue!=null&&typevalue!=""){
                for(var i= 0;i<data.length;i++){
                    if(typevalue == data[i].VALUE){
                        $("#referencePress").combobox('select', data[i].PRESS_CODE);
                    }
                }
            }
        }
    })
    $("#novelPress").combobox({
        url:'/book_market/paramAdmin/getPress',// 查询的controller
        editable : false,
        width : "150px",
        panelHeight:140,
        valueField : "PRESS_CODE",   //value值
        textField : "PRESS",   //text值
        loadFilter:function (data) {
            var obj2 = {};
            obj2.PRESS_CODE = 'other';
            obj2.PRESS = '其他';
            data.splice(data.length,0,obj2);
            return data;
        },
        onLoadSuccess : function() {    // 数据加载完毕事件
            var data = $('#novelPress').combobox('getData'); // 赋默认值
            var typevalue=$('#novelPress').val();
            $("#novelPress").combobox('select', data[0].PRESS_CODE);
            if(typevalue!=null&&typevalue!=""){
                for(var i= 0;i<data.length;i++){
                    if(typevalue == data[i].VALUE){
                        $("#novelPress").combobox('select', data[i].PRESS_CODE);
                    }
                }
            }
        }
    })

    $("#type").combobox({
        url:'/book_market/paramAdmin/getRefType',// 查询的controller
        editable : false,
        width : "150px",
        panelHeight:140,
        valueField : "TYPE_CODE",   //value值
        textField : "TYPE",   //text值
        loadFilter:function (data) {
            var obj2 = {};
            obj2.TYPE_CODE = 'other';
            obj2.TYPE = '其他';
            data.splice(data.length,0,obj2);
            return data;
        },
        onLoadSuccess : function() {    // 数据加载完毕事件
            var data = $('#type').combobox('getData'); // 赋默认值
            var typevalue=$('#type').val();
            $("#type").combobox('select', data[0].TYPE_CODE);
            if(typevalue!=null&&typevalue!=""){
                for(var i= 0;i<data.length;i++){
                    if(typevalue == data[i].VALUE){
                        $("#type").combobox('select', data[i].TYPE_CODE);
                    }
                }
            }
        }
    })
    $("#style").combobox({
        url:'/book_market/paramAdmin/getNovelStyle',// 查询的controller
        editable : false,
        width : "150px",
        panelHeight:140,
        valueField : "STYLE_CODE",   //value值
        textField : "STYLE",   //text值
        loadFilter:function (data) {
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
    $('#upLoad').dialog('open');
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

function validateUpLoadForm(id) {
    switch (id){
        case 'schoolBook':
            var author = $('#schAuthor').textbox('getValue');
            var bookName = $('#schBookName').textbox('getValue');
            var price = $('#schPrice').textbox('getValue');
            var abstract = $('#schAbstract').textbox('getValue');
            var stock = parseInt($('#schStock').textbox('getValue'));
            var image = $('#schoolBookImage').filebox('getValue');
            console.log(image);
            break;
        case 'reference':
            var author = $('#refAuthor').textbox('getValue');
            var bookName = $('#refBookName').textbox('getValue');
            var price = $('#refPrice').textbox('getValue');
            var abstract = $('#refAbstract').textbox('getValue');
            var stock = parseInt($('#refStock').textbox('getValue'));
            var image = $('#referenceImage').filebox('getValue');
            break;
        case 'novel':
            var author = $('#novelAuthor').textbox('getValue');
            var bookName = $('#novelBookName').textbox('getValue');
            var price = $('#novelPrice').textbox('getValue');
            var abstract = $('#novelAbstract').textbox('getValue');
            var stock = parseInt($('#novelStock').textbox('getValue'));
            var image = $('#novelImage').filebox('getValue');
            break;
        default:
    }
    if (author.length<1){
        $.messager.alert({
            title:'上传失败',
            msg:'作者不能为空！',
            icon:'warning',
        });
        return false;
    }else if (bookName.length<1){
        $.messager.alert({
            title:'上传失败',
            msg:'书名不能为空！',
            icon:'warning',
        });
        return false;
    }else if (price.length<1){
        $.messager.alert({
            title:'上传失败',
            msg:'价格不能为空！',
            icon:'warning',
        });
        return false;
    }else if (abstract.length<15){
        $.messager.alert({
            title:'上传失败',
            msg:'简介不得少于15字！',
            icon:'warning',
        });
        return false;
    }else if (isNaN(stock)){
        $.messager.alert({
            title:'上传失败',
            msg:'请输入正确数量',
            icon:'warning',
        });
    }else if (stock<1){
        $.messager.alert({
            title:'上传失败',
            msg:'数量不能小于1本',
            icon:'warning',
        });
    } else if (image.length<=1){
        $.messager.alert({
            title:'上传失败',
            msg:'请选择图片',
            icon:'warning',
        });
        return false;
    }else if (abstract.length>150){
        $.messager.alert({
            title:'上传失败',
            msg:'简介不得大于180字！',
            icon:'warning',
        });
     }else{
        return true;
    }
}

function chooseDiv(id) {
    switch (id){
        case 'schoolBook':
            $('#schoolBookUpLoad').show();
            $('#referenceUpLoad').hide();
            $('#novelUpLoad').hide();
            break;
        case 'reference':
            $('#schoolBookUpLoad').hide();
            $('#referenceUpLoad').show();
            $('#novelUpLoad').hide();
            break;
        case 'novel':
            $('#schoolBookUpLoad').hide();
            $('#referenceUpLoad').hide();
            $('#novelUpLoad').show();
            break;
        default:
    }
}

function formatImg(val,row){
    var img = " <img  src="+row.IMAGE+" "+"style="+"width:100px;"+"/>";
    return img;
}

function formatOpera(val,row){
    var operate = "<a href='javascript:void(0)' onclick='openDetail("+row.BOOK_ID+")' style='width: 100%;text-align: center'>"+"详情"+"</a>"+
        "<br/><br/>"+
        "<a href='javascript:void(0)' onclick='addToShopping("+row.BOOK_ID+")' style='width: 100%;text-align: center'>"+"修改"+"</a>";
    return operate;
}
