
$(function () {
    // $('#home_tabs').tabs('resize',{
    //     plain : false,
    //     boder : false,
    //     width:$(window).width(),
    //     height:$(window).height()
    // });
    // $("#home_tabs").tabs({
    //     tools:'#tab-tools'
    // });
    $('#mainPage').css("width",$(window).width());
    $('#mainPage').css("height",$(window).height()-46);

    $.ajax({
        type:'get',
        url:'/book_market/getUserName',
        success:function (data) {
            $('#username').text(data)
        }
    });

})


