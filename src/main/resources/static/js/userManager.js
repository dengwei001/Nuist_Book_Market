var user_role="";
var user_id="";
$(function () {
    $("#changePassword").dialog({
        onClose: function () {
            $('#newPassword').textbox('setValue','');
            $('#rePassword').textbox('setValue','');
        }
    });
    $('#userRoleCombobox').combobox({
        editable : false,
        width : "150px",
        height:"30px",
        panelHeight:140,
    })
    $('#changePassword').dialog('close');
    $('#userManager').datagrid({
        fitColumns:true,
        pagination:true,
        height:500,
        pageSize:20,
        pageList:[20,30,50,100],
        url:'/book_market/userManager/getAllUser',
        loadFilter:function (data) {
            var arr = data.rows;
            for (var i = 0; i < arr.length; i++) {
                switch (arr[i].userRole) {
                    case 'adminFugled':
                        arr[i].userRole = '超级管理员';
                        break;
                    case 'administrator':
                        arr[i].userRole = '管理员';
                        break;
                    case 'commonUser':
                        arr[i].userRole = '普通用户';
                        break;
                }
            }
            return data;
        }
    })
    $.ajax({
        type:'get',
        url:'/book_market/userManager/getUserInfo',
        success:function (data) {
            user_role=data.userRole;
            user_id=data.userId;
        }
    })
})

function operate(val,row) {
    var operate="";
    console.log(user_role);
    if (user_id!=row.userId){
        if (user_role=='adminFugled'){
            if (row.userRole=='管理员'){
                operate+= "<button onclick='cancelAdmin("+row.userId+")'>取消管理员</button>";
            }else if(row.userRole=='普通用户') {
                operate+= "<button onclick='setAdmin("+row.userId+")'>设置管理员</button>";
            }
            operate += "<button onclick='openChangePassword("+row.userId+")'>修改密码</button>";
            operate += "<button onclick='deleteUser("+row.userId+")'>删除</button>";
        }
        if(user_role=='administrator') {
            if (row.userRole!='管理员'&&row.userRole!='超级管理员'){
                operate += "<button onclick='openChangePassword("+row.userId+")'>修改密码</button>";
                operate += "<button onclick='deleteUser("+row.userId+")'>删除</button>";
            }
        }
    }
    else {
        // operate += "<button onclick='("+row.userId+")'>修改密码</button>";
    }

    return operate;
}

function deleteUser(userId) {
    $.messager.confirm('删除用户','确定删除该用户吗？',function (r) {
        if (r){
            $.ajax({
                type:'post',
                cache:false,
                data:{
                    userId:userId
                },
                url:'/book_market/userManager/deleteUser',
                success:function (data) {
                    $.messager.show({
                        title:'删除成功',
                        msg:'删除成功',
                        showType:'solid',
                        timeout:1000,
                        style:{
                            right:'',
                            bottom:''
                        }
                    });
                    $('#userManager').datagrid('reload');
                }
            })
        }
    })
}

function setAdmin(userId) {
    $.messager.confirm('设置','确定设置该用户为管理员吗？',function (r) {
        if (r){
            $.ajax({
                type:'post',
                cache:false,
                data:{
                    userId:userId
                },
                url:'/book_market/userManager/setAdmin',
                success:function (data) {
                    $('#userManager').datagrid('reload');
                }
            })
        }
    })
}
function cancelAdmin(userId) {
    $.messager.confirm('设置','确定取消该用户的管理员身份吗？',function (r) {
        if (r){
            $.ajax({
                type:'post',
                cache:false,
                data:{
                    userId:userId
                },
                url:'/book_market/userManager/cancelAdmin',
                success:function (data) {
                    $('#userManager').datagrid('reload');
                }
            })
        }
    })
}
function queryUserList(){
    var userRole = $('#userRole').combobox('getValue');
    var name = $('#name').textbox('getText');
    $('#userManager').datagrid({
        fitColumns:true,
        pagination:true,
        height:500,
        pageSize:20,
        pageList:[20,30,50,100],
        queryParams:{
            USER_ROLE:userRole,
            NAME:name
        },
        url:'/book_market/userManager/getUserListByParam',
        loadFilter:function (data) {
            var arr = data.rows;
            for (var i = 0; i < arr.length; i++) {
                switch (arr[i].userRole) {
                    case 'adminFugled':
                        arr[i].userRole = '超级管理员';
                        break;
                    case 'administrator':
                        arr[i].userRole = '管理员';
                        break;
                    case 'commonUser':
                        arr[i].userRole = '普通用户';
                        break;
                }
            }
            return data;
        }
    })
}

function openChangePassword(userId) {
    $('#userIdText').textbox('setValue',userId);
    $('#changePassword').dialog('open')
}
function changePsd() {
        var userId=$('#userIdText').textbox('getValue');
        var newPassword=$('#newPassword').textbox('getValue');
        var rePassword=$('#rePassword').textbox('getValue');
        if (newPassword==rePassword&&newPassword.length>=6){
            $.ajax({
                type:'post',
                data:{
                    userId:userId,
                    newPassword:newPassword
                },
                url:'/book_market/userManager/changePassword',
                success:function (data) {
                    if (data==1){
                        $.messager.show({
                            title:'成功',
                            msg:'修改成功',
                            timeout:1000,
                            showType:'slide',
                            style:{
                                right:'',
                                bottom:''
                            }
                        });
                        $('#newPassword').textbox('setValue','');
                        $('#rePassword').textbox('setValue','');
                        $('#changePassword').dialog('close');
                    }else {
                        $.messager.show({
                            title:'失败',
                            msg:'修改失败，请稍后再试',
                            timeout:1000,
                            showType:'slide',
                            style:{
                                right:'',
                                bottom:''
                            }
                        });
                    }
                }
            })
        }else if (newPassword!=rePassword){
            $.messager.show({
                title:'警告',
                msg:'输入的两次密码不相同',
                timeout:1000,
                showType:'slide',
                style:{
                    right:'',
                    bottom:''
                }
            })
        }else if (newPassword.length<6){
            $.messager.show({
                title:'警告',
                msg:'密码不能小于6位',
                timeout:1000,
                showType:'slide',
                style:{
                    right:'',
                    bottom:''
                }
            })
        }
}

