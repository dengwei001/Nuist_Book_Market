$(function () {
    $('#iframeDiv').height($(window).height());
    $('#iframeDiv').width($(window).width()-205);
    console.log($(window).height());
    $('#paramAdminA').on('click',function () {
        $('#paramAdminA').addClass('active');
        $('#userManagerA').removeClass('active');
    })

    $('#userManagerA').on('click',function () {
        $('#paramAdminA').removeClass('active');
        $('#userManagerA').addClass('active');
    })
})

