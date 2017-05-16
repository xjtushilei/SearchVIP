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

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return unescape(r[2]);
    return null;
}


// --------------------------------------------下面的程序才是逻辑，上面的是自己写的函数-------------------------------------

info = JSON.parse(getCookie('info'))
console.log(info)
$('#vipID').val(info.vipID)
$('#cardType').val(info.cardType)
$('#carNumber').val(info.carNumber)
$('#remainingPoints').val(info.remainingPoints)
$('#balance').val(info.balance + '元')

$.ajax({
    type: 'GET',
    url: ip + '/adminAPI/get',
    data: {temp: new Date()},
    dataType: 'json',
    timeout: 10000,
    success: function (data) {
        console.log(data)
        $('#href1').attr('href', data.lianjie1)
        $('#href2').attr('href', data.lianjie2)
    }
})
