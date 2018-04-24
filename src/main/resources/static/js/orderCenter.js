$(function () {
    $('#buyerOrder').datagrid({
        fit:false,
        view:buyerardview,
        fitColumns:true,
        pagination:true,
    })
    $('#buyerOrder').datagrid('getPager').pagination({
        pageSize: 10,//每页显示的记录条数，默认为10
        pageList: [10,20,30,50],//可以设置每页记录条数的列表
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
    });
    $('#buyerOrder').datagrid('resize',{
        height:$(window).height()
    })
    getBuyerOrderByState('all');

    $('#sellerOrder').datagrid({
        fit:false,
        view:sellerCardView,
        fitColumns:true,
        pagination:true,
    })
    $('#sellerOrder').datagrid('getPager').pagination({
        pageSize: 10,//每页显示的记录条数，默认为10
        pageList: [10,20,30,50],//可以设置每页记录条数的列表
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
    });
    getSellerOrderByState('all');
    $('#sellerOrder').datagrid('resize',{
        height:$(window).height()
    })
})
window.onload=function () {
    $('#sellerDiv').css('display','none');
}

var buyer = "buyer"
var buyerardview = $.extend({}, $.fn.datagrid.defaults.view, {
    renderRow: function(target, fields, frozen, rowIndex, rowData) {
        if (typeof (rowData.ORDER_ID) == "undefined") {
            var cc = [];
            cc.push('<td colspan=' + fields.length + ' style="padding:10px 5px;border-bottom:1px dashed;">');
            cc.push('<div style="float:left;width: 100%;text-align: center">');
            cc.push('<p>暂无订单！</p>');
            cc.push('</div>');
            cc.push('</td>');
            return cc.join('');
        } else {
            var cc = [];
            cc.push('<td colspan=' + fields.length + ' style="padding:10px 5px;border-bottom:1px dashed;">');
            if (!frozen) {
                cc.push('<div style="float:left;width: 100%;height: 100%">');
                cc.push('<div style="float:left;width: 20%;padding: 0px 20px 0px 20px">');
                for (var i = 0; i < 3; i++) {
                    var copts = $(target).datagrid('getColumnOption', fields[i]);
                    cc.push('<p style="margin-top: 3px;margin-bottom: 3px"><span class="c-label">' + copts.title + ':</span> ' + rowData[fields[i]] + '</p>');
                }
                cc.push('</div>');
                cc.push('<div style="float:left;width: 15%;padding: 0px 20px 0px 20px">');
                for (var i = 3; i < 6; i++) {
                    var copts = $(target).datagrid('getColumnOption', fields[i]);
                    cc.push('<p style="margin-top: 3px;margin-bottom: 3px"><span class="c-label">' + copts.title + ':</span> ' + rowData[fields[i]] + '</p>');
                }
                cc.push('</div>');
                cc.push('<div style="float:left;width: 20%;padding: 0px 20px 0px 20px">');
                for (var i = 6; i < 9; i++) {
                    var copts = $(target).datagrid('getColumnOption', fields[i]);
                    cc.push('<p style="margin-top: 3px;margin-bottom: 3px"><span class="c-label">' + copts.title + ':</span> ' + rowData[fields[i]] + '</p>');
                }
                cc.push('</div>');
                cc.push('<div style="float:left;width: 20%;padding: 0px 20px 0px 20px">');
                for (var i = 9; i < 11; i++) {
                    var copts = $(target).datagrid('getColumnOption', fields[i]);
                    cc.push('<p style="margin-top: 3px;margin-bottom: 3px"><span class="c-label">' + copts.title + ':</span> ' + rowData[fields[i]] + '</p>');
                };
                if (i==11){
                    var copts = $(target).datagrid('getColumnOption', fields[i]);
                    var state;
                    switch (rowData[fields[i]]){
                        case '0':
                            state='未处理';
                            break;
                        case '1':
                            state='已处理';
                            break;
                        case '2':
                            state='已完成';
                            break;
                        case '3':
                            state='已取消';
                            break;
                    }
                    cc.push('<p style="margin-top: 3px;margin-bottom: 3px"><span class="c-label">' + copts.title + ':</span> ' + state + '</p>');
                }
                cc.push('</div>');
                cc.push('<div style="float:right;width: *%;padding: 0px 20px 0px 20px">');
                for (var i = 12; i < fields.length; i++) {
                    switch (rowData[fields[11]]){
                        case '0':
                            cc.push('<p style="margin-top: 3px;margin-bottom: 3px">'
                                +'<button id="button" onclick="cancelOrder('+rowData.ORDER_ID+','+buyer+','+rowData.NUM+ ')">取消订单</button>'
                                + '</p>');
                            break;
                        case '1':
                            cc.push('<p style="margin-top: 3px;margin-bottom: 3px">'
                                +'<button id="button" onclick="confirmReceiving('+rowData.ORDER_ID+')">确认收货</button>'
                                + '</p>');
                            break;
                        case '2':
                            break;
                        case '3':
                            break;
                    }

                }
                cc.push('</div>');
            }
            cc.push('</td>');
            return cc.join('');
        }
    }
});

var seller = "seller"
var sellerCardView = $.extend({}, $.fn.datagrid.defaults.view, {
    renderRow: function(target, fields, frozen, rowIndex, rowData) {
        if (typeof (rowData.ORDER_ID) == "undefined") {
            var cc = [];
            cc.push('<td colspan=' + fields.length + ' style="padding:10px 5px;border-bottom:1px dashed;">');
            cc.push('<div style="float:left;width: 100%;text-align: center">');
            cc.push('<p>暂无订单！</p>');
            cc.push('</div>');
            cc.push('</td>');
            return cc.join('');
        } else {
            var cc = [];
            cc.push('<td colspan=' + fields.length + ' style="padding:10px 5px;border-bottom:1px dashed;">');
            if (!frozen) {
                cc.push('<div style="float:left;width: 100%;height: 100%">');
                cc.push('<div style="float:left;width: 20%;padding: 0px 20px 0px 20px">');
                for (var i = 0; i < 3; i++) {
                    var copts = $(target).datagrid('getColumnOption', fields[i]);
                    cc.push('<p style="margin-top: 3px;margin-bottom: 3px"><span class="c-label">' + copts.title + ':</span> ' + rowData[fields[i]] + '</p>');
                }
                cc.push('</div>');
                cc.push('<div style="float:left;width: 15%;padding: 0px 20px 0px 20px">');
                for (var i = 3; i < 6; i++) {
                    var copts = $(target).datagrid('getColumnOption', fields[i]);
                    cc.push('<p style="margin-top: 3px;margin-bottom: 3px"><span class="c-label">' + copts.title + ':</span> ' + rowData[fields[i]] + '</p>');
                }
                cc.push('</div>');
                cc.push('<div style="float:left;width: 20%;padding: 0px 20px 0px 20px">');
                for (var i = 6; i < 9; i++) {
                    var copts = $(target).datagrid('getColumnOption', fields[i]);
                    cc.push('<p style="margin-top: 3px;margin-bottom: 3px"><span class="c-label">' + copts.title + ':</span> ' + rowData[fields[i]] + '</p>');
                }
                cc.push('</div>');
                cc.push('<div style="float:left;width: 20%;padding: 0px 20px 0px 20px">');
                for (var i = 9; i < 11; i++) {
                    var copts = $(target).datagrid('getColumnOption', fields[i]);
                    cc.push('<p style="margin-top: 3px;margin-bottom: 3px"><span class="c-label">' + copts.title + ':</span> ' + rowData[fields[i]] + '</p>');
                }
                ;
                if (i == 11) {
                    var copts = $(target).datagrid('getColumnOption', fields[i]);
                    var state;
                    switch (rowData[fields[i]]) {
                        case '0':
                            state = '未处理';
                            break;
                        case '1':
                            state = '已处理';
                            break;
                        case '2':
                            state = '已完成';
                            break;
                        case '3':
                            state = '已取消';
                            break;
                    }
                    cc.push('<p style="margin-top: 3px;margin-bottom: 3px"><span class="c-label">' + copts.title + ':</span> ' + state + '</p>');
                }
                cc.push('</div>');
                cc.push('<div style="float:right;width: *%;padding: 0px 20px 0px 20px">');
                for (var i = 12; i < fields.length; i++) {

                    switch (rowData[fields[11]]) {
                        case '0':
                            cc.push('<p style="margin-top: 3px;margin-bottom: 3px">'
                                + '<button id="button" onclick="confirmTransaction(' + rowData.ORDER_ID + ')">确认交易</button>'
                                + '</p>');
                            cc.push('<p style="margin-top: 3px;margin-bottom: 3px">'
                                + '<button id="button" onclick="cancelOrder(' + rowData.ORDER_ID +','+seller+','+rowData.NUM+  ')">取消订单</button>'
                                + '</p>');
                            break;
                        case '1':
                            cc.push('<p style="margin-top: 3px;margin-bottom: 3px">'
                                + '<button id="button" onclick="cancelOrder(' + rowData.ORDER_ID +','+seller+','+rowData.NUM+ ')">取消订单</button>'
                                + '</p>');
                            break;
                        case '2':
                            break;
                        case '3':
                            break;
                    }
                }
                cc.push('</div>');
                cc.push('</td>');
                return cc.join('');
            }
        }
    }
})

function changeDiv(id) {
    switch (id){
        case 'buyerDiv':
            $('#buyerDiv').css('display','none');
            $('#sellerDiv').css('display','block');
            $('#sellerOrder').datagrid('reload')
            break;
        case 'sellerDiv':
            $('#buyerDiv').css('display','block');
            $('#sellerDiv').css('display','none');
            $('#buyerOrder').datagrid('reload')
            break;
    }
}

function confirmTransaction(orderId) {
    $.messager.confirm('确认','确认要进行此交易吗？',  function(r) {
        if (r){
            $.ajax({
                type:'post',
                cache:false,
                data:{
                    ORDER_ID:orderId
                },
                url:'/book_market/orderCenter/confirmTransaction',
                success:function (data) {
                    $('#buyerOrder').datagrid('reload');
                    $('#sellerOrder').datagrid('reload');
                }
            })
        }
    })
}

function cancelOrder(orderId,role,num) {
    $.messager.confirm('确认','确认要取消此交易吗？',function (r) {
        if (r){
            $.ajax({
                type:'post',
                cache:false,
                data:{
                    ORDER_ID:orderId,
                    ROLE:role,
                    NUM:num
                },
                url:'/book_market/orderCenter/cancelOrder',
                success:function (data) {
                    $('#buyerOrder').datagrid('reload');
                    $('#sellerOrder').datagrid('reload');
                },
                error:function (data) {
                    $.messager.alert({
                        title:'失败',
                        msg:'系统异常请稍候再试',
                        icon:'info'
                    })
                }
            })
        }
    })
}

function confirmReceiving(orderId) {
    $.messager.confirm('确认','确认收货？',function (r) {
        if (r){
            $.ajax({
                type:'post',
                cache:false,
                data:{
                    ORDER_ID:orderId,
                },
                url:'/book_market/orderCenter/confirmReceiving',
                success:function (data) {
                    $('#buyerOrder').datagrid('reload');
                    $('#sellerOrder').datagrid('reload');
                },
                error:function (data) {
                    $.messager.alert({
                        title:'失败',
                        msg:'系统异常请稍候再试',
                        icon:'info'
                    })
                }
            })
        }
    })
}

function getBuyerOrderByState(state) {
    var params={};
    params.order='buyerOrder';
    if (state!='all'){
        params.ORDER_STATE=state;
    }
    $('#buyerOrder').datagrid({
        queryParams:params,
        url:'/book_market/orderCenter/getOrderByState'
    })
}

function getSellerOrderByState(state) {
    var params={};
    params.order='sellerOrder';
    if (state!='all'){
        params.ORDER_STATE=state;
    }
    $('#sellerOrder').datagrid({
        queryParams:params,
        url:'/book_market/orderCenter/getOrderByState'
    })
}