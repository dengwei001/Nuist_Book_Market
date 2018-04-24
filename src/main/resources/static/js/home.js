$(function () {
    $('#mainPage').css("width",$(window).width());
    $('#mainPage').css("height",$(window).height()-46);

    $.ajax({
        type:'get',
        url:'/book_market/userManager/getUserInfo',
        success:function (data) {
            $('#username').text(data.name);
            console.log(data.userRole);
            console.log(data.name);
            if (data.userRole=='commonUser'){
                $('#adminModel').hide();
            }
        }
    });
    var underline = document.querySelector(".underline");
    var links = document.querySelectorAll(".mynav a");
    var colors = ["deepskyblue", "orange", "firebrick", "gold", "magenta", "black", "darkblue"];

    function mouseenterFunc() {
        if (!this.parentNode.classList.contains("active")) {
            for (var i = 0; i < links.length; i++) {
                if (links[i].parentNode.classList.contains("active")) {
                    links[i].parentNode.classList.remove("active");
                }
                links[i].style.opacity = "0.25";
            }

            this.parentNode.classList.add("active");
            this.style.opacity = "1";

            var width = this.getBoundingClientRect().width;
            var height = this.getBoundingClientRect().height;
            var left = this.getBoundingClientRect().left + window.pageXOffset;
            var top = this.getBoundingClientRect().top + window.pageYOffset;
            var color = colors[Math.floor(Math.random() * colors.length)];

            underline.style.width = width + "px";
            underline.style.height = height + "px";
            underline.style.left = left + "px";
            underline.style.top = top + "px";
            underline.style.borderColor = color;
            underline.style.transform = "none";
        }
    }
    for (var i = 0; i < links.length; i++) {
        links[i].addEventListener("click", function (e) {
            // return e.preventDefault();
        });
        links[i].addEventListener("mouseenter", mouseenterFunc);
    }

    function resizeFunc() {
        var active = document.querySelector(".mynav li.active");

        if (active) {
            var left = active.getBoundingClientRect().left + window.pageXOffset;
            var top = active.getBoundingClientRect().top + window.pageYOffset;

            underline.style.left = left + "px";
            underline.style.top = top + "px";
        }
    }

    window.addEventListener("resize", resizeFunc);

})

