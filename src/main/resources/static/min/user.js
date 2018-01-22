$("#userLoginBtn").click(function () {
    $.ajax({
        type: "POST",
        url: "/user/v1/login",
        data: $("#loginModal form").serialize(),
        error: function (a) {
            alert("Connection error")
        },
        success: function (a, c, b) {
            switch (a.code) {
                case 200:
                    sessionStorage.setItem("xrf_", b.getResponseHeader("AUTH"));
                    sessionStorage.setItem("username", b.getResponseHeader("username"));
                    $("#loginModal").modal("hide");
                    setStatus();
                    break;
                default:
                    alert(a.message)
            }
        }
    });
    return !1
});
$("#userRegisterBtn").click(function () {
    $.ajax({
        type: "PUT", url: "/user/v1/login", data: $("#registerModal form").serialize(), error: function (a) {
            alert("Connection error")
        }, success: function (a, c, b) {
            switch (a.code) {
                case 200:
                    alert("\u6ce8\u518c\u6210\u529f");
                    sessionStorage.setItem("xrf_", b.getResponseHeader("AUTH"));
                    sessionStorage.setItem("username", b.getResponseHeader("username"));
                    $("#registerModal").modal("hide");
                    setStatus();
                    break;
                default:
                    alert(a.message)
            }
        }
    });
    return !1
});
$(".Logout").click(function () {
    $.ajax({
        type: "DELETE",
        headers: {AUTH: sessionStorage.getItem("xrf_")},
        url: "/user/v1/login",
        data: null,
        error: function (a) {
            alert("\u8bf7\u5148\u767b\u5f55")
        },
        success: function (a) {
            switch (a.code) {
                case 200:
                    sessionStorage.removeItem("username");
                    sessionStorage.removeItem("AUTH");
                    setStatus();
                    break;
                default:
                    alert(a.message)
            }
        }
    });
    return !1
});
function setStatus() {
    null == sessionStorage.getItem("username") ? ($(".unLogin").show(), $(".Logout").hide(), $("#username").hide(), $("#shopcarSize").text(0)) : ($(".unLogin").hide(), $(".Logout").show(), $("#username").show(), $("#username a").text("\u6b22\u8fce\u60a8\uff0c" + sessionStorage.getItem("username")), getShopcarSize())
}
$("#bookCarbtn").click(function () {
    if (null == sessionStorage.getItem("username"))return alert("\u8bf7\u5148\u767b\u5f55"), !1;
    location.href = "/book/shopcar"
});
function getShopcarSize() {
    $.ajax({
        type: "GET",
        headers: {AUTH: sessionStorage.getItem("xrf_")},
        url: "/book/v1/shopcar/size",
        data: null,
        error: function (a) {
            alert("\u8bf7\u5148\u767b\u5f55")
        },
        success: function (a) {
            switch (a.code) {
                case 200:
                    $("#shopcarSize").text(a.data.size);
                    break;
                default:
                    console.log(a.message)
            }
        }
    })
};