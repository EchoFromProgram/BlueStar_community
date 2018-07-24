//获取签到密码
$("#modal-pub").click(function(){
		$.ajax({
			url:"get_sign_code.do",
			type:"POST",
			success: function(data){
				console.log(data);
				$("#sign-code-display").html(data);
			},
			error:function () {
	            alert("签到码获取失败，请重试");
	        }
		});
});

function to_page(page, classId, courseId){
    $.ajax({
        type: "POST",
        url: "teacher_get_signs.do",
        dataType: "json",
        data:{"page":page, "classId":classId, "courseId":courseId},
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
    
    //获取统计情况
    $.ajax({
        type: "POST",
        url: "get_static_sign.do",
        dataType: "json",
        data:{"classId":classId, "courseId":courseId},
        success: function(data){
        	if(data.code == 0){
	            $("#teacher-ssign-good").text(data.data.studentSuccessRate + '%');
	            $("#teacher-ssign-good").css("width", data.data.studentSuccessRate + '%');
	            $("#teacher-ssign-bad").text(data.data.studentLateRate + '%');
	            $("#teacher-ssign-bad").css("width", data.data.studentLateRate + '%');
	            if(data.data.studentLateRate == 0 && data.data.studentSuccessRate == 0){
		            $("#teacher-ssign-sobad").text('0%');
		            $("#teacher-ssign-sobad").css("width", '0%');
	            }else{
	            	$("#teacher-ssign-sobad").text((100 - Number(data.data.studentSuccessRate) - Number(data.data.studentLateRate)) + '%');
		            $("#teacher-ssign-sobad").css("width", (100 - Number(data.data.studentSuccessRate) - Number(data.data.studentLateRate)) + '%');
	            }
            }else{
            	alert(data.info);
            }
        },
        error:function () {
            alert("签到统计情况拉取失败");
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
            to_page(1, $("#which-class").val(), $("#which-stage").val());
        })
        //上一页
        prePageLi.click(function () {
            to_page(data.pageNum - 1, $("#which-class").val(), $("#which-stage").val());
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
            to_page(data.pages, $("#which-class").val(), $("#which-stage").val());
        })
        //下一页
        nextPageLi.click(function () {
            to_page(data.pageNum + 1, $("#which-class").val(), $("#which-stage").val());
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
            to_page(item, $("#which-class").val(), $("#which-stage").val());
        });
        ul.append(numLi);
    })
    //添加下一页和末页按钮
    ul.append(nextPageLi).append(lastPageLi);

    var navEle = $("<nav></nav>").append(ul).appendTo("#page_nav_area");
}

var Courses;
//获取课程列表
$(function(){
		$.ajax({
			url:"get_courses.do",
			type:"POST",
			dataType:"json",
			success: function(data){
				Courses = data;
				
				$("#which-stage").append('<option value="0">全部课程</option>');
				$.each(Courses,function(index, item){
				    var option = $("<option></option>").append(item.course);
				    option.attr("value", item.courseId);
				    option.appendTo("#which-stage");
				});
			},
			error:function () {
	            alert("课程信息拉取失败");
	        }
		});
});
	
//获取班级列表
$(function(){
	$.ajax({
		url:"getSessionHisClasses.do",
		type:"POST",
		success: function(data){
			$("#which-class").empty();
			$("#which-class").append('<option value="0">全部班级</option>');
		    $.each(data,function(index, item){
		        var option = $("<option></option>").append(item.className);
		        option.attr("value", item.classId);
		        option.appendTo("#which-class");
		    });
		    $("#which-class").change(function(){
	        	getCourseByClass(this.value)
	        });
		},
		error:function () {
          alert("班级信息拉取失败");
      }
	});
});

//根据班级获取课程
function getCourseByClass(classId){
	$.ajax({
		url:"get_course_by_class.do",
		type:"POST",
		data:{"classId":classId},
		success: function(data){
			if(classId == 0){
				$("#which-stage").val(0);
			}else{
				$("#which-stage").val(data.data.courseId);	
			}
		},
		error:function () {
            alert("请重新选择班级获取相应课程");
        }
	});
}

//显示签到信息的总函数
function teacherGetSign(page, classId, courseId){
	to_page(page, classId, courseId);
};

//点击查询按钮获取相应的签到信息
$("#submit-which-need").click(function(){
	teacherGetSign(1, $("#which-class").val(), $("#which-stage").val());
	if($("#which-class").val() != 0 && $("#which-stage").val() != 0){
		$("#teacher-static-box").show();
	}else{
		$("#teacher-static-box").hide();
	}
});

//页面载入的时候获取全部签到信息
$(function(){
	teacherGetSign(1, 0, 0);
})

//教师签到

function teacherSign(){
	$.ajax({
	    url:"teacher_sign.do",
	    type:"POST",
	    dataType:"json",
	    data:{"reason":$("#teacher-sign-reason").val()},
	    success: function(data){
	    	$('#teacher-sign-reason-help').text("");
            $('#teacher-sign-reason-div').removeClass("has-error");
            
	    	if(0 == data.code){
	    		alert(data.info);
	    	}else if(-2 == data.code){
	    		$("#teacher-sign-reason-help").text(data.info);
	    		$('#teacher-sign-reason-div').addClass("has-error");
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

	