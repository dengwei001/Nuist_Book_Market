$(function () {
    $('#pagination').pagination({
        total:2000,
        pageSize:10
    });
})
function creat(){
    var schoolBookDiv = $('#schoolBookDiv');
    var bookDiv=$('<div></div>');        //创建一个父div
    bookDiv.addClass('bookDiv');    //添加css样式
    //创建图片DIV
    var imgDiv=$('<div></div>');
    imgDiv.addClass('imgDiv');
    var img=$('<img/>');
    img.attr('src','/book_market/bookImages/15708488_1469252087464.jpg');
    img.appendTo(imgDiv);
    img.addClass('bookImg');
    imgDiv.appendTo(bookDiv);
    //创建名称DIV
    var bookNameDiv=$('<Div></Div>');
    var p=$('<p></p>');
    p.text('单片机');
    p.addClass('bookName');
    p.appendTo(bookNameDiv);
    bookNameDiv.appendTo(bookDiv);
    //创建链接DIV
    var linkDiv=$('<Div></Div>');
    var detailLink=$('<a></a>');
    detailLink.attr('href','javascript:void(0)');
    detailLink.text('详情');
    detailLink.appendTo(linkDiv);
    var carLink=$('<a></a>');
    carLink.attr('href','javascript:void(0)');
    carLink.text('加入购物车');
    carLink.appendTo(linkDiv);
    linkDiv.addClass('linkDiv');
    linkDiv.appendTo(bookDiv);
    //添加到主DIV
    bookDiv.appendTo(schoolBookDiv);
}