/**
 * 读取配置文件
 */
// setCookie("name","hayden");
// alert(getCookie("name"));
//如果需要设定自定义过期时间
//那么把上面的setCookie　函数换成下面两个函数就ok;
//程序代码
function setCookie(name, value, time) {
    var strsec = getsec(time);
    var exp = new Date();
    exp.setTime(exp.getTime() + strsec * 1);
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString() + ";path=/";
}
function getsec(str) {
    // alert(str);
    var str1 = str.substring(1, str.length) * 1;
    var str2 = str.substring(0, 1);
    if (str2 == "s") {
        return str1 * 1000;
    }
    else if (str2 == "h") {
        return str1 * 60 * 60 * 1000;
    }
    else if (str2 == "d") {
        return str1 * 24 * 60 * 60 * 1000;
    }
}
//这是有设定过期时间的使用示例：
//s20是代表20秒
//h是指小时，如12小时则是：h12
//d是天数，30天则：d30
// setCookie("name","hayden","s20");
function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return "";
}
$(document).ready(function () {
// 点击查询的动画
    $('#search').tap(function () {
        // console.log($('#check').attr('src'))
        if ($('#check').css('color').indexOf('reen' > 0)) {
            setCookie("vipIDCookie", $('#vipID').val(), "d666")
        } else {
            setCookie("vipIDCookie", "", "d666")
        }
        setTimeout('search()', 400)

    });
});


// --------------------------------------------下面的程序才是逻辑，上面的是自己写的函数---------------------------------------

if (getCookie("vipIDCookie") != "") {
    $('#vipID').val(getCookie("vipIDCookie"))
    $('#check').attr('src', 'img/lvse.png')
}

function qiehuan() {
    console.log($('#check').css('color'))
    if ($('#check').css('color').indexOf('lack') > 0) {
        $('#check').css('color', 'green')
    } else {
        $('#check').css('color', 'black')
    }
}


function search() {

    $("#ing").css('display', '-webkit-box');
    $.ajax({
        type: 'GET',
        // url: 'a.txt',
        url: ip + '/searchAPI/get',
        data: {vipID: $('#vipID').val(), temp: new Date()},
        // type of data we are expecting in return:
        dataType: 'json',
        timeout: 10000,
        success: function (data) {
            console.log(JSON.stringify(data))
            setCookie("info", JSON.stringify(data), 'd200')
            $("#ing").css('display', 'none');
            window.location = 'info.html'
        },
        error: function (data) {
            setTimeout("$('#ing').css('display', 'none');$('#noid').css('display', '-webkit-box');", 400);
        }
    })
}