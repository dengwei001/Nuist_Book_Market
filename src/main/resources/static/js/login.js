$(function(){
    $('#registerDialog').dialog('close');
    $.idcode.setCode();//设置验证码
    $('.easyui-textbox').textbox({
        iconAlign:'left'
    })
    $('.easyui-textbox').textbox('textbox').css('background','none')

})

function submitLogin() {
    var IsBy = $.idcode.validateCode();
    if (IsBy){
        $("#loginForm").submit();
    }else {
        $('#message').html("验证码错误");
    }
}

function openRegister() {
    $('#registerDialog').dialog('open');
}

function registerUser() {
    var resUsername = $('#resUsername').textbox('getValue');
    var resPassword = $('#resPassword').textbox('getValue');
    var resName = $('#resName').textbox('getValue');
    var resMobile = $('#resMobile').textbox('getValue');
    if (resPassword.length>=6&&resUsername!=null&&resName!=null&&resMobile.length==11){
        $.ajax({
            method:'post',
            data:{
                username:resUsername,
                password:resPassword,
                name:resName,
                mobile:resMobile
            },
            url:'/book_market/register/registerUser',
            success:function (data) {
                if (data==1){
                    $.messager.alert({
                        title:'注册成功',
                        msg:'注册成功啦！欢迎你的加入！',
                        icon:'msg',
                    });
                    $('#registerDialog').dialog('close');
                }else if (data==false) {
                    $.messager.alert({
                        title:'失败',
                        msg:"注册失败,用户名已存在！",
                        icon:'msg'
                    });
                }else {
                    $.messager.alert({
                        title: '失败',
                        msg: "系统繁忙，请稍候再试！",
                        icon: 'msg',
                    })
                }
            }
        })
    }else (
        $.messager.alert({
            title: '失败',
            msg: "请检查你的输入",
            icon: 'msg',
        })
    )


}