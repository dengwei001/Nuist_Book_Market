$(function () {
    $('#iframeDiv').height($(window).height());
    $('#iframeDiv').width($(window).width()-205);
    console.log($(window).height());
    $('#schoolBookA').on('click',function () {
        $('#schoolBookA').addClass('active');
        $('#referenceA').removeClass('active');
        $('#novelA').removeClass('active');
    })

    $('#referenceA').on('click',function () {
        $('#schoolBookA').removeClass('active');
        $('#referenceA').addClass('active');
        $('#novelA').removeClass('active');
    })

    $('#novelA').on('click',function () {
        $('#schoolBookA').removeClass('active');
        $('#referenceA').removeClass('active');
        $('#novelA').addClass('active');
    })
})



