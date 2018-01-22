$("#searchBtn").click(function () {
    location.href = "/book/search?" + encodeURI($("#searchForm").serialize());
    return !1
});