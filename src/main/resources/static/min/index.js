var currentPage;
$(function () {
    function d(a) {
        $.ajax({
            type: "POST",
            url: "/game/v1/list",
            data: "pn=" + a + "&pageSize=9",
            error: function (a, b, c) {
                333 == a.responseJSON.code ? alert("\u64cd\u4f5c\u8fc7\u5feb") : alert("Connection error")
            },
            success: function (a) {
                200 == a.statusCode ? (k(a), l(a)) : 333 == a.code ? alert("\u64cd\u4f5c\u8fc7\u5feb") : alert(a.message)
            }
        })
    }

    function k(a) {
        $("#gameList").empty();
        $.each(a.data.page.list, function (a, b) {
            a = $("<h3></h3>").append(b.type);
            var e = $("<p></p>").append(b.place), f = $("<p></p>").append($("<span></span>").append(b.peopleNum)),
                g =
                    $("<a></a>").attr("href", "/book/detail?bookid=" + b.uid).append("\u67e5\u770b\u8be6\u60c5");
            b = $("<span></span>").addClass("pull-right").css({
                "margin-right": "10px",
                color: "darkred"
            }).append("\uffe5" + b.price);
            var m = $("<div></div>").addClass("col-md-4"), h = $("<div></div>").addClass("caption"),
                d = $("<div></div>").addClass("thumbnail").append();
            d.append(a).append(a).append(e).append(f).append($("<p></p>").append(g).append(b));
            h.append(d);
            m.append(h).appendTo("#gameList")
        })
    }

    function l(a) {
        $("#page_nav_area").empty();
        var e = $("<ul></ul>").addClass("pagination"),
            b = $("<li></li>").append($("<a></a>").append("\u9996\u9875").attr("href", "#")),
            c = $("<li></li>").append($("<a></a>").append("&laquo;"));
        0 == a.data.page.hasPreviousPage ? (b.addClass("disabled"), c.addClass("disabled")) : (b.click(function () {
            d(1)
        }), c.click(function () {
            d(a.data.page.pageNum - 1)
        }));
        var f = $("<li></li>").append($("<a></a>").append("&raquo;")),
            g = $("<li></li>").append($("<a></a>").append("\u672b\u9875").attr("href", "#"));
        0 == a.data.page.hasNextPage ? (f.addClass("disabled"),
            g.addClass("disabled")) : (f.click(function () {
            d(a.data.page.pageNum + 1)
        }), g.click(function () {
            d(a.data.page.pages)
        }));
        e.append(b).append(c);
        $.each(a.data.page.navigatepageNums, function (b, c) {
            b = $("<li></li>").append($("<a></a>").append(c));
            a.data.page.pageNum == c && (currentPage = c, b.addClass("active"));
            b.click(function () {
                d(c)
            });
            e.append(b)
        });
        e.append(f).append(g).appendTo($("#page_nav_area"))
    }

    function n(a) {
        $.each(a.data.page.list, function (a, b) {
            var c = $("<div></div>").addClass("list-group-item"), e = $("<h4></h4>").append($("<a></a>").attr("id",
                    "panic_" + a).attr("href", "/book/panic?bookid=" + b.uid)),
                d = $("<p></p>").text((new Date(b.startTime)).toLocaleString() + " \u51c6\u65f6\u5f00\u59cb");
            c.append(e).append(d);
            $("#panic_list").append(c);
            $.ajax({
                type: "GET", url: "/book/v1/detail/" + b.uid, data: null, error: function (a) {
                    alert("Connection error")
                }, success: function (b) {
                    b = b.data.book;
                    $("#panic_" + a).text(b.name)
                }
            })
        })
    }

    function p(a) {
        $.each(a.data.TOP_TEN, function (a, b) {
            var c = $("<div></div>").addClass("list-group-item");
            a = $("<span></span>").addClass("badge").text(a +
                1);
            b = $("<a></a>").attr("href", "/book/detail?bookid=" + b.uid).text(b.name);
            c.append(a).append(b);
            $("#hotBooks").append(c)
        })
    }

    setStatus();
    $(function () {
        d(1)
    });
    (function () {
        $.ajax({
            type: "POST", url: "/book/v1/panic/list", data: null, error: function (a, d, b) {
                333 == a.responseJSON.code ? alert("\u64cd\u4f5c\u8fc7\u5feb") : alert("Connection error")
            }, success: function (a) {
                200 == a.code ? n(a) : alert(a.message)
            }
        })
    })();
    (function () {
        $.ajax({
            type: "GET", url: "/book/v1/hotBook", data: null, error: function (a, d, b) {
                333 == a.responseJSON.code ?
                    alert("\u64cd\u4f5c\u8fc7\u5feb") : alert("Connection error")
            }, success: function (a) {
                200 == a.code ? p(a) : alert(a.message)
            }
        })
    })()
});