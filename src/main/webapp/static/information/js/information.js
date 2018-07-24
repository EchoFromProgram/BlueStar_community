$(function () {
    $("select option[value='"+ $('#which-stage').attr("selec") +"']").attr("selected", "selected");
})

//新增资讯
function submitEditor() {
    var content = UM.getEditor('myEditor').getContent();
    var title = $('#editor-title').val();
    var createUser = $('#createUser').val();
    var statu = $('#statu-select').val();
    $.ajax({
        url : "information_addtext.do",
        data : {
            "content" : content,
            "title" : title,
            "createUser" : createUser,
            "statu" : statu,
        },
        type : "POST",
        success : function (data) {
            alert(data.msg);
            window.location.reload();
        },
        error : function () {
            alert("提交资讯失败");
        }
    });
    return false;
}

//修改资讯
function updateEditor(){
    var content = UM.getEditor('myEditor').getContent();
    var title = $('#editor-title').val();
    var createUser = $('#createUser').val();
    var statu = $('#statu-select').val();
    var informationId = $('#editor-title').attr("informationId");
    $.ajax({
        url : "update_information.do",
        data : {
            "content" : content,
            "title" : title,
            "createUser" : createUser,
            "statu" : statu,
            "informationId" : informationId
        },
        async : false,
        type : "POST",
        success : function (data) {
            alert(data.msg);
            window.location.reload();
        },
        error : function () {
            alert("提交资讯失败");
        }
    });
}


//点击修改以后  因为到一个页面，所以这里的要是需要改变
//并且这里需要把数据库的数据拿下了去做修改
$(".update-information-btn").bind("click", function () {
    $('#change-title').text("修改资讯");
    $('#submit-editor').val("修改");
    $('#submit-editor').attr('id','update-editor');
    $('#text-form').attr('onsubmit', 'return updateEditor()');
    $.ajax({
        url : "get_information.do",
        data : {
            "informationId" : $(this).attr("informationid")
        },
        type : "POST",
        success : function (data) {
            $('#editor-title').val(data.data.informationTitle);
            $('#editor-title').attr("informationId", data.data.informationId);
            $('#createUser').val(data.data.informationCreateUser);
            $('#statu-select').val(data.data.informationStatu);
            UM.getEditor('myEditor').setContent("" + data.data.informationContent, false);
        },
        error : function () {
            alert("更新资讯失败");
        }
    });
})

//样式改到增加的样式
$("#submit-add").bind("click", function () {
    $('#change-title').text("新增资讯");
    $('#update-editor').val("提交");
    $('#update-editor').attr('id','submit-editor');
    $('#text-form').attr('onsubmit', 'return submitEditor()');
})

$(".delete-information-btn").bind("click", function () {
    $("#delete-information-button").attr("informationid", $(this).attr("informationid"));
})

$("#delete-information-button").click(function () {
    $.ajax({
        url : "remove_information.do",
        data : {
            "informationId" : $(this).attr("informationid")
        },
        type : "POST",
        async : false,
        success : function (data) {
            alert(data.msg);
            window.location.reload();
        },
        error : function () {
            alert("资讯删除失败");
        }
    })
})


//分页
function pageHelper(data) {
    buile_page_info(data);
    buile_page_nav(data);
}
//解析显示分页文字
function buile_page_info(data) {
    $("#page_info_area").empty();
    $("#page_info_area").append("当前第"+ data.pageNum +"页,总"+data.pages+
        "页,共"+data.total +"条记录");
    totalRecord = data.total ;
    currentPage = data.pageNum;
}

//解析显示分页条
function buile_page_nav(data) {
    $("#page_nav_area").empty();
    var ul = $("<ul></ul>").addClass("pagination");
    var firstPageLi = $("<li></li>").append($("<a></a>").append("首页"));
    var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));
    //如果没有前一页，首页和前一页无法点击
    if(data.hasPreviousPage == false){
        firstPageLi.addClass("disabled");
        prePageLi.addClass("disabled");
    }else{
        //跳转到首页
        firstPageLi.click(function () {
            window.location.href = "information_list.do?page=1&statu=" + $("#which-stage").val() + "&title=" + $("#which-input").val() +"";
        })
        //上一页
        prePageLi.click(function () {
            window.location.href = "information_list.do?page="+ data.pageNum - 1 +"&statu=" + $("#which-stage").val() + "&title=" + $("#which-input").val() +"";
        })
    }


    var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
    var lastPageLi = $("<li></li>").append($("<a></a>").append("末页"));
    //如果没有下一页，末页和下一页无法点击
    if(data.hasNextPage== false){
        nextPageLi.addClass("disabled");
        lastPageLi.addClass("disabled");
    }else{
        //跳转到末页
        lastPageLi.click(function () {
            window.location.href = "information_list.do?page="+ data.pages +"&statu=" + $("#which-stage").val() + "&title=" + $("#which-input").val() +"";
        })
        //下一页
        nextPageLi.click(function () {
            window.location.href = "information_list.do?page="+ data.pageNum + 1 +"&statu=" + $("#which-stage").val() + "&title=" + $("#which-input").val() +"";
        })
    }

    //添加首页和下一页按钮
    ul.append(firstPageLi).append(prePageLi);
    //遍历分页条
    var navPageNums = data.navigatepageNums;//里面为1,2,3,4,5 ..
    $.each(navPageNums,function(index,item){
        var numLi =$("<li></li>").append($("<a></a>").append(item));
        if(data.pageNum == item){
            numLi.addClass("active")
        }
        numLi.click(function() {
            window.location.href = "information_list.do?page="+ item +"&statu=" + $("#which-stage").val() + "&title=" + $("#which-input").val() +"";
        });
        ul.append(numLi);
    })
    //添加下一页和末页按钮
    ul.append(nextPageLi).append(lastPageLi);

    var navEle = $("<nav></nav>").append(ul).appendTo("#page_nav_area");
}



