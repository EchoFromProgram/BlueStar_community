$(function () {
    $("select option[value='"+ $('#which-stage').attr("data-sele") +"']").attr("selected", "selected");

    // 点击删除按钮删除指定的广告
    $(".delete-btn").on('click', function () {
        $("#delete-button").attr('data-adId',$(this).attr('data-adId'));
    })

    $("#delete-button").on("click",function() {
        $.ajax({
            url: "deleteAd.do",
            data: {"adId": $(this).attr("data-adId")},
            type: "post",
            success: function(data) {
                if(data.statusCode == 0) {
                    window.location.reload();
                } else {
                    alert(data.msg);
                }
            },
            error: function () {
                alert("未知错误！");
            }
        })

    })


    // 得到更新数据
    $(".update-btn").on("click",function() {
        var $this = this;
        $.ajax({
            url: "getUpdateData.do",
            data: {"adId": $(this).attr("data-adId")},
            type: "post",
            success: function(obj) {
                if(obj.statusCode == 0) {
                    $("#update_adPic").val(obj.data.adPicture);
                    $("#update_adId").val($($this).attr("data-adId"));
                    $("#update_title").val(obj.data.adTitle);
                    $("#update_createUser").val(obj.data.adCreateUser);
                    $("#update_order").val(obj.data.adOrder);
                    $("#update_url").val(  obj.data.adLinkUrl);
                    $("#update_pic").prop("src",obj.data.enclosurePath);
                    $("#update-which-stage option[value='"+ obj.data.adStatus +"']").attr("selected", "selected");
                } else {
                    alert(obj.msg);
                }
            },
            error: function () {
                alert("未知错误！");
            }
        })
    })

    // 更新操作
    $("#update-button").on("click",function() {
        let formData = new FormData($("#updateForm" )[0]);
        $.ajax({
            url: "updateAd.do",
            type: 'POST',
            data: formData,
            async: false,
            contentType: false,
            processData: false,
            success: function(data) {
                if(data.statusCode == 0) {
                    window.location.reload();
                } else {
                    alert(data.msg);
                }
            },
            error: function () {
                alert("未知错误！");
            }
        })

    })



    $("#create-btn").on("click", function() {

        if(isBlank($("#addTitle").val())){
            alert("请填写标题");
            return ;
        }
        else if(isBlank($("#addCreateUser").val())){
            alert("请填写采编人");
            return ;
        }
        let formData = new FormData($("#uploadForm" )[0]);
        $.ajax({
            url: 'saveAd.do' ,
            type: 'POST',
            data: formData,
            async: false,
            contentType: false,
            processData: false,
            success: function (data) {
                if(data.statusCode != 0) {
                    alert(data.msg);
                } else {
                    window.location.reload();
                }
            },
            error: function () {
                alert("未知错误！");
            }
        });
        return false;
    })

    // 判断字符串是否为空
    function isBlank(str) {
        if(str === '' || str === null || str === undefined)
            return true;
        return false;
    }





})

// 为jsp页面加分页

function to_page(data) {
    buile_page_info(data);
    buile_page_nav(data)
}

//解析显示分页文字
function buile_page_info(data) {
    $("#page_info_area").empty();
    $("#page_info_area").append("当前第"+ data.pageNum +"页,总"+data.pages+
        "页,共"+ data.total +"条记录");
    totalRecord = data.total ;
    currentPage = data.pageNum;
}

// 解析显示分页条
function buile_page_nav(data) {
    $("#page_nav_area").empty();
    var ul = $("<ul></ul>").addClass("pagination");
    var firstPageLi = $("<li></li>").append($("<a></a>").append("首页"));
    var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));

    // 如果没有前一页，首页和前一页无法点击
    if(data.hasPreviousPage == false){
        firstPageLi.addClass("disabled");
        prePageLi.addClass("disabled");
    }else{
        // 跳转到首页
        firstPageLi.click(function () {
            window.location.href =
                "listAd.do?pageNum=" + 1 + "&adTitle="+$("#which-input").val()
                +"&adStatus=" + $("#which-stage").val();
        })
        // 上一页
        prePageLi.click(function () {

            // 注意：得到的data.pageNum不是int类型！
            window.location.href =
                "listAd.do?pageNum=" + (Number(data.pageNum) - 1) + "&adTitle="+$("#which-input").val()
                +"&adStatus=" + $("#which-stage").val();
        })
    }


    var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
    var lastPageLi = $("<li></li>").append($("<a></a>").append("末页"));
    // 如果没有下一页，末页和下一页无法点击
    if(data.hasNextPage== false){
        nextPageLi.addClass("disabled");
        lastPageLi.addClass("disabled");
    }else{
        // 跳转到末页
        lastPageLi.click(function () {
            window.location.href =
                "listAd.do?pageNum=" + data.pages  + "&adTitle="+$("#which-input").val()
                +"&adStatus=" + $("#which-stage").val();
        })

        // 下一页
        nextPageLi.click(function () {
            window.location.href =
                "listAd.do?pageNum=" + (Number(data.pageNum ) + 1)  + "&adTitle="+$("#which-input").val()
                +"&adStatus=" + $("#which-stage").val();
        })
    }

    // 添加首页和上一页按钮
    ul.append(firstPageLi).append(prePageLi);
    // 遍历分页条
    var navPageNums = data.navigatepageNums;//里面为1,2,3,4,5 ..
    $.each(navPageNums,function(index,item){
        var numLi =$("<li></li>").append($("<a></a>").append(item));
        if(data.pageNum == item){
            numLi.addClass("active")
        }
        numLi.click(function() {
            window.location.href =
                "listAd.do?pageNum=" + item + "&adTitle="+$("#which-input").val()
                +"&adStatus=" + $("#which-stage").val();
        });
        ul.append(numLi);
    })
    //添加下一页和末页按钮
    ul.append(nextPageLi).append(lastPageLi);

    var navEle = $("<nav></nav>").append(ul).appendTo("#page_nav_area");
}
