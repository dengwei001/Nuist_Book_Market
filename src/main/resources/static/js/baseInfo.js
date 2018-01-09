$(function () {
    $.ajax({
        method:'get',
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