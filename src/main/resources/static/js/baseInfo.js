$(function () {
    $('#changePassword').dialog('close');
    $('#changeMobile').dialog('close');
    $.ajax({
        type:'get',
        url:'/book_market/myInfo/getUser',
        cache:false,
        success:function (data) {
            $('#userId').text(data.userId);
            $('#username').text(data.username);
            switch (data.userRole){
                case 'superAdmin':
                    $('#userRole').text('超级管理员');
                    break;
                case 'admin':
                    $('#userRole').text('管理员');
                    break;
                case 'commonUser':
                    $('#userRole').text('普通用户');
                    break;
                default:
                    break;
            }
            $('#name').text(data.name);
            $('#mobile').text(data.mobile);
        }
    })
})

function openChangePassword() {
    $('#changePassword').dialog('open')
}
function openChangeMobile() {
    $('#changeMobile').dialog('open')
}
function changePasswprd() {
    var oldPassword=$('#oldPassword').textbox('getValue');
    var newPassword=$('#newPassword').textbox('getValue');
    var rePassword=$('#rePassword').textbox('getValue');
    if (newPassword==rePassword&&newPassword.length>=6){
        $.ajax({
            type:'post',
            data:{
                oldPassword:oldPassword,
                newPassword:newPassword
            },
            url:'/book_market/myInfo/changePassword',
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
                    $('#changePassword').dialog('close');
                }else {
                    $.messager.show({
                        title:'失败',
                        msg:'原始密码不正确',
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

function changeMobile() {
    var newMobile=$('#newMobile').textbox('getValue');
    if (newMobile.length==11){
        $.ajax({
            type:'post',
            data:{
                newMobile:newMobile
            },
            url:'/book_market/myInfo/changeMobile',
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
                    })
                    $('#changeMobile').dialog('close');
                }else {
                    $.messager.show({
                        title:'失败',
                        msg:'请检查输入信息',
                        timeout:1000,
                        showType:'slide',
                        style:{
                            right:'',
                            bottom:''
                        }
                    })
                }
            }
        })
    }else {
        $.messager.show({
            title:'失败',
            msg:'请输入正确的手机号',
            timeout:1000,
            showType:'slide',
            style:{
                right:'',
                bottom:''
            }
        })
    }


}