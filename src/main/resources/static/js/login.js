$(function(){
    $('#registerDialog').dialog('close');
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
        }
    }, 1000);
}