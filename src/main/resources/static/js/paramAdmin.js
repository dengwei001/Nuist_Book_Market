//全局变量
var college = 'college';
var specialty = 'specialty';
var press = 'press';
var reftype = 'type'
var novelstyle = 'style'

$(function () {


    $('#collegeParam').datagrid({
        // showHeader:false,
        fit:true,
        pagination:true,
        fitColumns:true,
        striped:true,
        url:'/book_market/paramAdmin/getCollege',
        onLoadSuccess:function (data) {
            $('#collegeParam').datagrid('selectRow',0);
            var collegeCode = $('#collegeParam').datagrid('getSelected').COLLEGE_CODE;
            specialtyAdmin(collegeCode);
        },
        onSelect:function () {
            var collegeCode = $('#collegeParam').datagrid('getSelected').COLLEGE_CODE;
            specialtyAdmin(collegeCode);
        }
    });
    $('#collegeParam').datagrid('getPager').pagination({
        pageSize: 5,//每页显示的记录条数，默认为10
        pageList: [5, 10, 15],//可以设置每页记录条数的列表
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
    });

    $('#collegeParam').datagrid('hideColumn','COLLEGE_CODE');


    $('#pressParam').datagrid({
        // showHeader:false,
        fit:true,
        pagination:true,
        fitColumns:true,
        striped:true,
        url:'/book_market/paramAdmin/getPress'
    });
    $('#pressParam').datagrid('hideColumn','PRESS_CODE');
    $('#pressParam').datagrid('getPager').pagination({
        pageSize: 5,//每页显示的记录条数，默认为10
        pageList: [5, 10, 15],//可以设置每页记录条数的列表
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
    });

    $('#typeParam').datagrid({
        // showHeader:false,
        fit:true,
        pagination:true,
        fitColumns:true,
        striped:true,
        url:'/book_market/paramAdmin/getRefType'
    });
    $('#typeParam').datagrid('hideColumn','TYPE_CODE');
    $('#typeParam').datagrid('getPager').pagination({
        pageSize: 5,//每页显示的记录条数，默认为10
        pageList: [5, 10, 15],//可以设置每页记录条数的列表
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
    });

    $('#styleParam').datagrid({
        // showHeader:false,
        fit:true,
        pagination:true,
        fitColumns:true,
        striped:true,
        url:'/book_market/paramAdmin/getNovelStyle'
    });
    $('#styleParam').datagrid('hideColumn','STYLE_CODE');
    $('#styleParam').datagrid('getPager').pagination({
        pageSize: 5,//每页显示的记录条数，默认为10
        pageList: [5, 10, 15],//可以设置每页记录条数的列表
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
    });


})

function colFormatOpera(val,row){
    var operate = "<a href='javascript:void(0)' onclick='deleteParam("+row.COLLEGE_CODE+",college)'>删除</a>";
    return operate;
}

function speFormatOpera(val,row){
    var operate = "<a href='javascript:void(0)' onclick='deleteParam("+row.SPECIALTY_CODE+",specialty)'>删除</a>";
    return operate;
}

function typeFormatOpera(val,row){
    var operate = "<a href='javascript:void(0)' onclick='deleteParam("+row.TYPE_CODE+",reftype)'>删除</a>";
    return operate;
}

function pressFormatOpera(val,row){
    var operate = "<a href='javascript:void(0)' onclick='deleteParam("+row.PRESS_CODE+",press)'>删除</a>";
    return operate;
}

function styleFormatOpera(val,row){
    var operate = "<a href='javascript:void(0)' onclick='deleteParam("+row.STYLE_CODE+",novelstyle)'>删除</a>";
    return operate;
}

function specialtyAdmin(collegeCode) {
    $('#specialtyParam').datagrid({
        // showHeader:false,
        queryParams:{
            COLLEGE_CODE:collegeCode
        },
        fit:true,
        pagination:true,
        fitColumns:true,
        striped:true,
        url:'/book_market/paramAdmin/getSpecialty'
    });
    $('#specialtyParam').datagrid('hideColumn','SPECIALTY_CODE');
    $('#specialtyParam').datagrid('getPager').pagination({
        pageSize: 5,//每页显示的记录条数，默认为10
        pageList: [5, 10, 15],//可以设置每页记录条数的列表
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
    });
}

function insertParam(type) {
    var url;
    var paramDataGrid;
    if (type=='college'){
        var paramName = $("#collegeText").textbox('getValue');
        paramDataGrid = $("#collegeParam");
        url = '/book_market/paramAdmin/insertCollege'
    }
    if (type=='specialty'){
        var paramName = $("#specialtyText").textbox('getValue');
        paramDataGrid = $("#specialtyParam");
        var extra = $('#collegeParam').datagrid('getSelected').COLLEGE_CODE;
        url = '/book_market/paramAdmin/insertSpecialty'
    }
    if (type=='press'){
        var paramName = $("#pressText").textbox('getValue');
        paramDataGrid = $("#pressParam");
        url = '/book_market/paramAdmin/insertPress'
    }
    if (type=='type'){
        var paramName = $("#typeText").textbox('getValue');
        paramDataGrid = $("#typeParam");
        url = '/book_market/paramAdmin/insertType'
    }
    if (type=='style'){
        var paramName = $("#styleText").textbox('getValue');
        paramDataGrid = $("#styleParam");
        url = '/book_market/paramAdmin/insertStyle'
    }
    if (paramName!=null&&paramName!=''){
        $.ajax({
            method:'post',
            data:{
                paramName : paramName,
                extra: extra
            },
            url:url,
            success:function (data) {
                if (data == 1){
                    $.messager.show({
                        title:'新增',
                        msg:'新增成功！',
                        icon:'info',
                        timeout:5000,
                        showType:'slide'
                    });
                    paramDataGrid.datagrid('reload')
                }else {
                    $.messager.alert({
                        title:'失败',
                        timeout:5000,
                        msg:data[0].answer,
                        icon:'info'
                    })
                }
            }
        })
    }else {
        $.messager.alert({
            title:'警告',
            msg:'输入信息不能为空！',
            icon:'warning'
        })
    }
}

function deleteParam(code,paramType) {
    var url;
    var paramDataGrid;
    if (paramType=='college'){
        paramDataGrid = $("#collegeParam");
        url = '/book_market/paramAdmin/delCollege'
    }
    if (paramType=='specialty'){
        paramDataGrid = $("#specialtyParam");
        url = '/book_market/paramAdmin/delSpecialty'
    }
    if (paramType=='press'){
        paramDataGrid = $("#pressParam");
        url = '/book_market/paramAdmin/delPress'
    }
    if (paramType=='type'){
        paramDataGrid = $("#typeParam");
        url = '/book_market/paramAdmin/delType'
    }
    if (paramType=='style'){
        paramDataGrid = $("#styleParam");
        url = '/book_market/paramAdmin/delStyle'
    }
    $.messager.confirm('删除','确定要删除这条数据吗？',function(r){
        if (r){
            $.ajax({
                method:'post',
                data:{
                    paramCode: code,
                },
                url: url,
                success:function (data) {
                    $.messager.show({
                        title:'删除',
                        msg:'删除成功！',
                        icon:'info',
                        timeout:5000,
                        showType:'slide'
                    });
                    paramDataGrid.datagrid('reload');
                }
            })
        }
    })

}