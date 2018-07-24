// 点击加一个
$("#tree").delegate("p", "click", (e) => {
    $("#tree p.active").removeClass("active");
    $(e.target).addClass("active");

    //const active = $("#tree .active");
    //active.after("<p data-deptLevel='"+ (active.data("deptlevel") + 1) +"'>哈哈哈</p>");
    //reflash();

    if ($(e.target).hasClass("fish-open")) {
        closeList($(e.target));
    } else {
        openList($(e.target));
    }
    $(e.target).toggleClass("fish-open fish-close");
});

// 刷新画面
function reflash() {
    $("#tree p").each((index, item) => {
        $(item).css({
            "text-indent": ($(item).data("deptlevel") - 1) * 2 + "em"
        });
    });
}

// 展开列表
function openList(ele) {
    $("#tree p").each((index, item) => {
        if ($(item).hasClass(ele.data("deptid"))) {
            $(item).show("slow");
        }
    });
}

// 关闭列表
function closeList(ele) {
    const minLevel = ele.data("deptlevel");
    let current = ele.next();
    while (true) {
        if (current.length === 0 || current.data("deptlevel") <= minLevel) {
            break;
        }
        current.hide("slow");
        current.removeClass("fish-open");
        current.addClass("fish-close");
        current = current.next();
    }
}

// 得到选中的卡片
function getActive() {
    return $("#tree p.active");
}

// 判断是否有选中
function hasActive() {
    return $("#tree p.active").length > 0;
}

reflash();