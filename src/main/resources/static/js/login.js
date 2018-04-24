$(function(){
    $('#changePassword').dialog('close');
    $('#registerDialog').dialog('close');
    $('#forgetPassword').dialog('close');
    $.idcode.setCode();//设置验证码
    $('.easyui-textbox').textbox({
        iconAlign:'left'
    })
    $('.easyui-textbox').textbox('textbox').css('background','none')
    //绑定回车时间，回车提交登录表单
    document.onkeydown = function(e){
        var ev = document.all ? window.event : e;
        if(ev.keyCode==13) {
            submitLogin()
        }
    }
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
function openFgDialog() {
    var username = $('#username').textbox('getValue');
    $('#fgUsername').textbox('setValue',username);
    $('#forgetPassword').dialog('open');
}

function registerUser() {
    var resUsername = $('#resUsername').textbox('getValue');
    var resPassword = $('#resPassword').textbox('getValue');
    var resName = $('#resName').textbox('getValue');
    var resMobile = $('#resMobile').textbox('getValue');
    if (resPassword.length>=6&&resUsername!=null&&resName!=null&&resMobile.length==11){
        $.ajax({
            type:'post',
            data:{
                code:$('#messageCode').textbox('getValue')
            },
            url:'/book_market/register/validateMessageCode',
            success:function (data) {
               if (data==true){
                   $.ajax({
                       type:'post',
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
                                   icon:'info',
                               });
                               $('#registerDialog').dialog('close');
                           }else if (data==false) {
                               $.messager.alert({
                                   title:'失败',
                                   msg:"注册失败,用户名已存在！",
                                   icon:'warning'
                               });
                           }else {
                               $.messager.alert({
                                   title: '失败',
                                   msg: "系统繁忙，请稍候再试！",
                                   icon: 'warning',
                               })
                           }
                       }
                   })
               }else {
                   $.messager.alert({
                       title: '失败',
                       msg: "验证码不正确",
                       icon: 'warning',
                   })
               }
           }
        });
    }else (
        $.messager.alert({
            title: '失败',
            msg: "请检查你的输入",
            icon: 'warning',
        })
    )
}

// 定义发送时间间隔(s)
var SEND_INTERVAL = 60;
var timeLeft = SEND_INTERVAL;

function sendMessageCode() {
    var resMobile = $('#resMobile').textbox('getValue');
    if (resMobile.length > 0) {
        // 需要先禁用按钮（为防止用户重复点击）
        $("#sendMessage").attr('disabled', 'disabled');
        $('#resMobile').attr('disabled','disabled')
        $.ajax({
            type: 'post',
            data: {
                mobile: $('#resMobile').textbox('getValue')
            },
            url: '/book_market/register/getMessageCode',
            success: function (data) {
                timeLeft = SEND_INTERVAL;
                timeCount();
            }
        });
    }
}
function sendFgMessageCode() {
    var username = $('#fgUsername').textbox('getValue');
    if (username.length > 0) {
        // 需要先禁用按钮（为防止用户重复点击）
        $("#sendFgCode").attr('disabled', 'disabled');
        $('#fgUsername').attr('disabled','disabled')
        $.ajax({
            type: 'post',
            data: {
                username: username
            },
            url: '/book_market/register/getFgMessageCode',
            success: function (data) {
                timeLeft = SEND_INTERVAL;
                timeCountFg();
            }
        });
    }
}
/**
 * 重新发送计时
 **/
function timeCount() {
    window.setTimeout(function() {
        if(timeLeft > 0) {
            timeLeft -= 1;
            $("#sendMessage").attr('value',timeLeft + "后重新发送");
            timeCount();
        } else {
            $("#sendMessage").attr('value',"重新发送");
            $("#sendMessage").attr('disabled',false)
            $("#resMobile").attr('disabled',false)
        }
    }, 1000);
}
function timeCountFg() {
    window.setTimeout(function() {
        if(timeLeft > 0) {
            timeLeft -= 1;
            $("#sendFgCode").attr('value',timeLeft + "后重新发送");
            timeCount();
        } else {
            $("#sendFgCode").attr('value',"重新发送");
            $("#sendFgCode").attr('disabled',false)
            $("#fgUsername").attr('disabled',false)
        }
    }, 1000);
}

function forgetNextStep() {
    $.ajax({
        type: 'post',
        data: {
            code: $('#fgMessageCode').textbox('getValue')
        },
        url: '/book_market/register/validateMessageCode',
        success:function (data) {
            if (data==true){
                $('#forgetPassword').dialog('close');
                $('#changePassword').dialog('open');
            }

        }
    })
}

function changePasswprd() {
    var username = $('#fgUsername').textbox('getValue');
    var newPassword=$('#newPassword').textbox('getValue');
    var rePassword=$('#rePassword').textbox('getValue');
    if (newPassword==rePassword&&newPassword.length>=6){
        $.ajax({
            type:'post',
            data:{
                username:username,
                newPassword:newPassword
            },
            url:'/book_market/register/changePassword',
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
                        msg:'用户不存在',
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