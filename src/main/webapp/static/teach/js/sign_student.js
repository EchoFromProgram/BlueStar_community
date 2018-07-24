$(function(){
	to_page(1);
});

function to_page(page){
    $.ajax({
        type: "POST",
        url: "init_sign_student.do",
        dataType: "json",
        data:{"page":page},
        success: function(data){
        	if(data.code == 0){
                //显示table
                build_table(data.data);

                //显示分页文字
                buile_page_info(data.data);

                //显示分页条
                buile_page_nav(data.data);
            }else{
            	alert(data.info);
            }
        },
        error:function () {
            alert("签到列表拉取失败");
        }
    });
};

//解析显示table
function build_table(data) {
    //清空
    $("#sign-student-table tbody").empty();
    var dataList = data.list;
    //jquery遍历,emps为遍历对象，function(index索引,item得到的每一个对象)为回调函数
    $.each(dataList,function(index, item){
        //创建td并朝里面追加内容
        var classId = $("<td></td>").append(item.className);
        var userId = $("<td></td>").append(item.name);
        var courseId = $("<td></td>").append(item.course);
        var date = $("<td></td>").append(dateFormat(new Date(item.date)));
        var status = $("<td></td>");
        if(item.status == 0) {
            status.append("正常上课")
        }else if(item.status == 1){
            status.append("迟到")
        }else if(item.status == 2){
            status.append("旷课")
        }
        var reason = $("<td></td>").append(item.reason==null?"":item.reason);
        //向一个tr中添加所有的td
        $("<tr></tr>").append(classId).append(userId).append(courseId)
            .append(date).append(status).append(reason).appendTo("#sign-student-table tbody");
    })
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
            to_page(1);
        })
        //上一页
        prePageLi.click(function () {
            to_page(data.pageNum - 1);
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
            to_page(data.pages);
        })
        //下一页
        nextPageLi.click(function () {
            to_page(data.pageNum + 1);
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
            to_page(item);
        });
        ul.append(numLi);
    })
    //添加下一页和末页按钮
    ul.append(nextPageLi).append(lastPageLi);

    var navEle = $("<nav></nav>").append(ul).appendTo("#page_nav_area");
}

//学生签到
function studentSign(){
	$.ajax({
	    url:"student_sign.do",
	    type:"POST",
	    dataType:"json",
	    data:{"inputCode":$("#signword").val(), "reason":$("#sign-reason").val()},
	    success: function(data){
	    	$('#sign-help').text("");
            $('#sign-div').removeClass("has-error");
	    	$('#sign-reason-help').text("");
            $('#sign-reason-div').removeClass("has-error");
            
	    	if(0 == data.code){
	    		alert(data.info);
	    	}else if(-1 == data.code){
	    		$("#sign-help").text(data.info);
	    		$('#sign-div').addClass("has-error");
	    	}else if(-2 == data.code){
	    		$("#sign-reason-help").text(data.info);
	    		$('#sign-reason-div').addClass("has-error");
	    	}else{
	    		alert(data.info);
	    	}
	    },
	    error:function () {
	        alert("签到时出现异常，请重试");
	    }
	});
	return false;
};
