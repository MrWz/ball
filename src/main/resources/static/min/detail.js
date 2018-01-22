var bookPrice;
$(function () {
    setStatus();
    (function (a) {
        $.ajax({
            type: "GET", url: "/book/v1/detail/" + a, data: null, error: function (a) {
                alert("Connection error")
            }, success: function (a) {
                a = a.data.book;
                $("#book_uid").val(a.uid);
                $("#book_name").text(a.name);
                $("#book_author").text(a.author);
                $("#book_price").text(a.price);
                $("#book_desc").text(a.description);
                $("#bookuid_buy").val(a.uid);
                $("#bookuid_add").val(a.uid);
                bookPrice = a.price
            }
        })
    })(function (a) {
        a = new RegExp("(^|&)" + a + "=([^&]*)(&|$)", "i");
        a = window.location.search.substr(1).match(a);
        return null !=
        a ? unescape(a[2]) : null
    }("bookid"));
    $("#book_buy_num").change(function () {
        0 >= $("#book_buy_num").val() ? alert("\u8bf7\u8f93\u5165\u6b63\u786e\u7684\u6570\u91cf") : 5 < $(this).val().length ? alert("\u6570\u91cf\u8fc7\u591a") : ($("#bookPrice_buy").text("\u60a8\u9700\u652f\u4ed8\uffe5" + $(this).val() * bookPrice), $("#buyBtn").removeAttr("disabled"))
    });
    $("#buyBtn").click(function () {
        if (0 >= $("#book_buy_num").val()) alert("\u8bf7\u8f93\u5165\u6b63\u786e\u7684\u6570\u91cf"); else if (5 < $(this).val().length) alert("\u6570\u91cf\u8fc7\u591a");
        else return $.ajax({
                type: "POST",
                headers: {AUTH: sessionStorage.getItem("xrf_")},
                url: "/book/v1/buy",
                data: $("#buyModal form").serialize(),
                error: function (a) {
                    alert("\u8bf7\u60a8\u5148\u53bb\u767b\u5f55")
                },
                success: function (a) {
                    200 == a.code ? (alert("\u8d2d\u4e70\u6210\u529f"), $("#buyModal").modal("hide")) : alert(a.message)
                }
            }), !1
    });
    $(function () {
        $("#addBtn").click(function () {
            if (0 >= $("#book_buy_num").val()) alert("\u8bf7\u8f93\u5165\u6b63\u786e\u7684\u6570\u91cf"); else if (5 < $(this).val().length) alert("\u6570\u91cf\u8fc7\u591a");
            else return $.ajax({
                    type: "POST",
                    headers: {AUTH: sessionStorage.getItem("xrf_")},
                    url: "/book/v1/shopcar",
                    data: $("#shopCarModal form").serialize(),
                    error: function (a) {
                        alert("\u8bf7\u60a8\u5148\u53bb\u767b\u5f55")
                    },
                    success: function (a) {
                        200 == a.code ? ($("#shopCarModal").modal("hide"), location.reload(!0)) : alert(a.message)
                    }
                }), !1
        })
    })
});