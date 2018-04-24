
$(function () {
    $('#iframeDiv').height($(window).height());
    $('#iframeDiv').width($(window).width()-205);
    console.log($(window).height());
    $('#baseInfoA').on('click',function () {
        $('#baseInfoA').addClass('active');
        $('#myStoreA').removeClass('active');
        $('#shoppingCarA').removeClass('active');
        $('#orderCenterA').removeClass('active');
    })

    $('#myStoreA').on('click',function () {
        $('#baseInfoA').removeClass('active');
        $('#myStoreA').addClass('active');
        $('#shoppingCarA').removeClass('active');
        $('#orderCenterA').removeClass('active');
    })

    $('#shoppingCarA').on('click',function () {
        $('#baseInfoA').removeClass('active');
        $('#myStoreA').removeClass('active');
        $('#shoppingCarA').addClass('active');
        $('#orderCenterA').removeClass('active');
    })

    $('#orderCenterA').on('click',function () {
        $('#baseInfoA').removeClass('active');
        $('#myStoreA').removeClass('active');
        $('#shoppingCarA').removeClass('active');
        $('#orderCenterA').addClass('active');
    })
})

