$(function () {
    $('#detail').dialog('close');
    $('#shoppingCar').datagrid({
        fit:false,
        view:cardview,
        fitColumns:true,
        pagination:false,
        url:'/book_market/shoppingCar/getCar',
        loadFilter:function (data) {
            for (var i=0;i<data.length;i++){
                // 两种添加写法
                if (!data[i].hasOwnProperty('COLLEGE')){
                    data[i].COLLEGE='——'
                }
                if (!data[i].hasOwnProperty('SPECIALTY')){
                    data[i]['SPECIALTY']='——'                    }
                if (!data[i].hasOwnProperty('TYPE')){
                    data[i]['TYPE']='——'                    }
                if (!data[i].hasOwnProperty('STYLE')){
                    data[i]['STYLE']='——'                    }
            }
            return data;
        },
        onLoadSuccess:function () {
            balance()
        },
        onClickRow:function () {
            balance()
        },
        onCheckAll:function () {
            balance()
        }
    })
    $('#shoppingCar').datagrid('getPager').pagination({
        pageSize: 10,//每页显示的记录条数，默认为10
        pageList: [10,20,30,50],//可以设置每页记录条数的列表
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
    });
    $('#shoppingCar').datagrid('resize',{
        height:$(window).height()
    })
})

var cardview = $.extend({}, $.fn.datagrid.defaults.view, {
    renderRow: function(target, fields, frozen, rowIndex, rowData) {
        if (typeof (rowData.BOOK_ID) == "undefined") {
            var cc = [];
            cc.push('<td colspan=' + fields.length + ' style="padding:10px 5px;border-bottom:1px dashed;">');
            cc.push('<div style="float:left;width: 100%;text-align: center">');
            cc.push('<p>您的购物车空空如也，快去书市看看吧！</p>');
            cc.push('</div>');
            cc.push('</td>');
            return cc.join('');
        } else {
            var cc = [];
            cc.push('<td colspan=' + fields.length + ' style="padding:10px 5px;border-bottom:1px dashed;">');
            if (!frozen) {
                cc.push('<div style="float:left;width: 100%;height: 100%">');
                cc.push('<div style="float:left;width: 10%;text-align: center">');
                cc.push('<img src="/book_market/' + rowData.IMAGE + '" style="height:100px;float:left">');
                cc.push('</div>');
                cc.push('<div style="float:left;width: 15%;padding: 0px 20px 0px 20px">');
                for (var i = 2; i < 9; i++) {
                    var copts = $(target).datagrid('getColumnOption', fields[i]);
                    cc.push('<p style="margin-top: 3px;margin-bottom: 3px"><span class="c-label">' + copts.title + ':</span> ' + rowData[fields[i]] + '</p>');
                }
                cc.push('</div>');
                cc.push('<div style="float:left;width: 15%;padding: 0px 20px 0px 20px">');
                for (var i = 9; i < 15; i++) {
                    var copts = $(target).datagrid('getColumnOption', fields[i]);
                    cc.push('<p style="margin-top: 3px;margin-bottom: 3px"><span class="c-label">' + copts.title + ':</span> ' + rowData[fields[i]] + '</p>');
                }
                cc.push('</div>');
                cc.push('<div style="float:left;width: 25%;padding: 0px 20px 0px 20px">');
                for (var i = 15; i < 16; i++) {
                    var copts = $(target).datagrid('getColumnOption', fields[i]);
                    cc.push('<p style="margin-top: 3px;margin-bottom: 3px"><span class="c-label">' + copts.title + ':</span> ' + rowData[fields[i]] + '</p>');
                }
                cc.push('</div>');
                cc.push('<div style="float:left;width: 10%;padding: 0px 20px 0px 20px">');
                for (var i = 16; i < 17; i++) {
                    var copts = $(target).datagrid('getColumnOption', fields[i]);
                    cc.push('<p style="margin-top: 3px;margin-bottom: 3px"><span class="c-label">' + copts.title + ':</span> ' + rowData[fields[i]] + '</p>');
                }
                for (var i = 17; i < 18; i++) {
                    var copts = $(target).datagrid('getColumnOption', fields[i]);
                    cc.push('<p style="color: red;margin-top: 3px;margin-bottom: 3px"><span class="c-label">' + copts.title + ':</span> '+'￥'+ Number(rowData.PRICE)*Number(rowData.NUM) + '</p>');
                }
                cc.push('</div>');
                cc.push('<div style="float:right;width: *%;padding: 0px 20px 0px 20px">');
                for (var i = 18; i < fields.length; i++) {
                    var copts = $(target).datagrid('getColumnOption', fields[i]);
                    cc.push('<p style="margin-top: 3px;margin-bottom: 3px">'
                        +'<button id="button" onclick="updataNum('+rowData.BOOK_ID+')">修改数量</button>'
                        + '</p>');
                }
                cc.push('</div>');
            }
            cc.push('</td>');
            return cc.join('');
        }
    }
});


function delFromCar() {
    var arr = $('#shoppingCar').datagrid('getChecked');
    var list = [] ;
    for (var i=0;i<arr.length;i++){
        var json = {
            BOOK_ID:arr[i].BOOK_ID
        };
        list.push(json);
    };
    $.ajax({
        type:'post',
        cache:false,
        dataType:'json',
        contentType:"application/json",
        data:JSON.stringify(list),
        url:'/book_market/shoppingCar/delFromCar',
        success:function (data) {
            if (data.res=="OK"){
                $('#shoppingCar').datagrid('reload');
                balance()
            }else {
                $.messager.alert({
                    title:'失败',
                    msg:data
                })
            }
        },
        error:function (data) {
            $.messager.alert({
                title:'系统异常，请稍候再试！',
                msg:data.res
            })
        }
    });
}

function buyBookNow() {
    var arr = $('#shoppingCar').datagrid('getChecked');
    var list = [] ;
    for (var i=0;i<arr.length;i++){
        var json = {
            BOOK_ID:arr[i].BOOK_ID,
            BOOK_NAME:arr[i].BOOK_NAME,
            SELLER_ID:arr[i].SELLER_ID,
            NUM:arr[i].NUM,
        };
        list.push(json);
    };
    $.ajax({
        type: 'post',
        cache: false,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(list),
        url: '/book_market/shoppingCar/buyBook',
        success:function (data) {
            console.log(data);
            console.log(data.webResult);
            if (data.webResult==true){
                var msg = '';
                var unPass=data.data;
                if (unPass.length<1){
                    $.messager.show({
                        title:'下单成功',
                        msg:'在我的信息中查看订单信息',
                        timeout:1000,
                        showType:'slide',
                        style:{
                            right:'',
                            bottom:''
                        }
                    });
                    $('#shoppingCar').datagrid('reload');
                }else {
                    for (var i=0;i<unPass.length;i++){
                        msg = msg+'书号为'+unPass[i].BOOK_ID+'的'+unPass[i].BOOK_NAME+'库存已不足！'+'<br/>';
                    };
                    $.messager.show({
                        title:'下单成功',
                        msg:msg,
                        timeout:1000,
                        showType:'slide',
                        style:{
                            right:'',
                            bottom:''
                        }
                    });
                    $('#shoppingCar').datagrid('reload');
                }
            }else{
                $.messager.show({
                    title:'下单失败',
                    msg:'请选择商品',
                    timeout:1000,
                    showType:'slide',
                    style:{
                        right:'',
                        bottom:''
                    }
                });
            }
        },
        error:function (data) {
            $.messager.alert({
                title:'系统异常，请稍候再试！',
                msg:data[0].res
            })
        }
    })
}

function updataNum(bookId) {
    $.messager.prompt('修改数量','购买数量',function (r) {
        if (r){
            $.ajax({
                type:'post',
                cache:false,
                data:{
                    BOOK_ID:bookId,
                    NUM:r
                },
                url:'/book_market/shoppingCar/updateNum',
                success:function (data) {
                    if (data==true){
                        $.messager.show({
                            title:'修改成功',
                            msg:'修改成功',
                            showType:'slide',
                            timeout:1000,
                            style:{
                                right:'',
                                bottom:''
                            }
                        });
                        $('#shoppingCar').datagrid('reload');
                    }else {
                        $.messager.show({
                            title:'异常',
                            msg:'系统异常，请稍后再试',
                            showType:'slide',
                            timeout:1000,
                            style:{
                                right:'',
                                bottom:''
                            }
                        })
                    }
                }

            })
        }
    })
}

function balance() {
    var rows = $('#shoppingCar').datagrid('getChecked');
    var totalBookNum = 0;
    var totalPrice = 0;
    for (var i=0;i<rows.length;i++){
        totalBookNum = totalBookNum+Number(rows[i].NUM);
        totalPrice = totalPrice+Number(rows[i].NUM)*Number(rows[i].PRICE);
    }
    $('#totalBookNum').text('共 '+totalBookNum+' 本书');
    $('#totalPrice').text(totalPrice+'元');
}