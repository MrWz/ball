var totalRecord, currentPage;
$(function () {
    null == sessionStorage.getItem("admin") ? location.href = "/admin/login" : (to_page(1), $("#log_off_btn").click(function () {
        $.ajax({
            type: "DELETE",
            headers: {AUTH: sessionStorage.getItem("axrf_")},
            url: "/admin/v1/login",
            data: null,
            error: function (a) {
                alert("Connection error")
            },
            success: function (a) {
                200 == a.code ? (alert(a.message), sessionStorage.removeItem("admin"), sessionStorage.removeItem("axrf_"), location.href = "/admin/login") : alert(a.message)
            }
        })
    }))
});
function to_page(a) {
    $.ajax({
        type: "POST", url: "/book/v1/list", data: "pn=" + a, error: function (a) {
            alert("Connection error")
        }, success: function (a) {
            build_book_table(a);
            build_page_info(a);
            build_page_nav(a)
        }
    })
}
function build_book_table(a) {
    $("tbody").empty();
    $.each(a.data.page.list, function (a, b) {
        a = $("<td></td>").append(b.id);
        var d = $("<td></td>").append(b.name), e = $("<td></td>").append(b.author),
            f = $("<td></td>").append("\uffe5" + b.price), l = $("<td></td>").append(b.stock),
            c = $("<button>\u7f16\u8f91</button>").addClass("btn btn-info btn-sm edit_btn");
        c.attr("edit-id", b.uid);
        var h = $("<button>\u53d1\u5e03\u62a2\u8d2d</button>").addClass("btn btn-primary btn-sm panic_btn");
        h.attr("panic-id", b.uid);
        h.attr("panic-price",
            b.price);
        var k = $("<button>\u4e0b\u67b6</button>").addClass("btn btn-danger btn-sm delete_btn");
        k.attr("delete-id", b.uid).attr("bookname", b.name);
        b = $("<td></td>").append(h).append(" ").append(c).append(" ").append(k);
        $("<tr></tr>").append(a).append(d).append(e).append(f).append(l).append(b).appendTo("tbody")
    })
}
function build_page_info(a) {
    $("#page_info_area").empty();
    $("#page_info_area").append("\u5f53\u524d<kbd>" + a.data.page.pageNum + "</kbd>\u9875\uff0c\u5171<kbd>" + a.data.page.pages + "</kbd>\u9875\uff0c\u603b\u5171<kbd>" + a.data.page.total + "</kbd>\u6761\u6570\u636e");
    totalRecord = a.data.page.total
}
function build_page_nav(a) {
    $("#page_nav_area").empty();
    var d = $("<ul></ul>").addClass("pagination"),
        b = $("<li></li>").append($("<a></a>").append("\u9996\u9875").attr("href", "#")),
        g = $("<li></li>").append($("<a></a>").append("&laquo;"));
    0 == a.data.page.hasPreviousPage ? (b.addClass("disabled"), g.addClass("disabled")) : (b.click(function () {
        to_page(1)
    }), g.click(function () {
        to_page(a.data.page.pageNum - 1)
    }));
    var e = $("<li></li>").append($("<a></a>").append("&raquo;")),
        f = $("<li></li>").append($("<a></a>").append("\u672b\u9875").attr("href",
            "#"));
    0 == a.data.page.hasNextPage ? (e.addClass("disabled"), f.addClass("disabled")) : (e.click(function () {
        to_page(a.data.page.pageNum + 1)
    }), f.click(function () {
        to_page(a.data.page.pages)
    }));
    d.append(b).append(g);
    $.each(a.data.page.navigatepageNums, function (b, c) {
        b = $("<li></li>").append($("<a></a>").append(c));
        a.data.page.pageNum == c && (currentPage = c, b.addClass("active"));
        b.click(function () {
            to_page(c)
        });
        d.append(b)
    });
    d.append(e).append(f).appendTo($("#page_nav_area"))
}
$("#book_add_btn").click(function () {
    $("#bookModal form")[0].reset();
    getBookTags();
    $("#bookModal").modal("show")
});
function getBookTags() {
    $.ajax({
        type: "GET",
        headers: {AUTH: sessionStorage.getItem("axrf_")},
        url: "/book/v1/booktag",
        data: null,
        error: function (a) {
        },
        success: function (a) {
            200 == a.code ? (a = a.data.booktags, $("#bookType").empty(), $.each(a, function (a, b) {
                $("#bookType").append("<option value='" + b.description + "'>" + b.description + "</option>")
            })) : alert("\u83b7\u53d6\u56fe\u4e66\u5206\u7c7b\u4fe1\u606f\u5931\u8d25")
        }
    })
}
$("#book_save_btn").click(function () {
    $.ajax({
        type: "POST",
        headers: {AUTH: sessionStorage.getItem("axrf_")},
        url: "/admin/v1/book/add",
        data: $("#bookModal form").serialize(),
        error: function (a) {
            alert("Connection error")
        },
        success: function (a) {
            alert(a.message);
            200 == a.code && ($("#bookModal").modal("hide"), to_page(totalRecord))
        }
    });
    return !1
});
$(document).on("click", ".edit_btn", function () {
    getBook($(this).attr("edit-id"));
    $("#bookUpdateModal").modal("show")
});
function getBook(a) {
    $.ajax({
        type: "GET",
        headers: {AUTH: sessionStorage.getItem("axrf_")},
        url: "/book/v1/detail/" + a,
        data: null,
        error: function (a) {
            alert("Connection error")
        },
        success: function (a) {
            a = a.data.book;
            $("#bookuid_input").val(a.uid);
            $("#bookname_input").val(a.name);
            $("#author_input").val(a.author);
            $("#price_input").val(a.price);
            $("#stock_input").val(a.stock);
            $("#description_input").val(a.description)
        }
    })
}
$("#book_update_btn").click(function () {
    $.ajax({
        type: "PUT",
        headers: {AUTH: sessionStorage.getItem("axrf_")},
        url: "/admin/v1/book/adjust",
        data: $("#bookUpdateModal form").serialize(),
        error: function (a) {
            alert("Connection error")
        },
        success: function (a) {
            alert(a.message);
            $("#bookUpdateModal").modal("hide");
            to_page(currentPage)
        }
    });
    return !1
});
$(document).on("click", ".delete_btn", function () {
    confirm("\u786e\u5b9a\u5220\u9664 " + $(this).attr("bookname") + " \u5417\uff1f") && del_book($(this).attr("delete-id"));
    return !1
});
function del_book(a) {
    $.ajax({
        type: "DELETE",
        headers: {AUTH: sessionStorage.getItem("axrf_")},
        url: "/admin/v1/book/del/" + a,
        data: null,
        error: function (a) {
            alert("Connection error")
        },
        success: function (a) {
            alert(a.message);
            to_page(currentPage)
        }
    })
}
$(document).on("click", ".panic_btn", function () {
    $("#panicModal").modal("show");
    $("#panicuid_input").val($(this).attr("panic-id"));
    $("#panicPrice_input").val($(this).attr("panic-price"));
    $("#panicNums_input").val(1)
});
$("#book_panic_btn").click(function () {
    $.ajax({
        type: "POST",
        headers: {AUTH: sessionStorage.getItem("axrf_")},
        url: "/admin/v1/book/panic",
        data: $("#panicModal form").serialize(),
        error: function (a) {
            alert("Connection error")
        },
        success: function (a) {
            200 == a.code && $("#panicModal").modal("hide");
            alert(a.message)
        }
    });
    return !1
});