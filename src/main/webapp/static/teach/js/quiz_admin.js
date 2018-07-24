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


function to_page(page, classId, courseId){
    $.ajax({
        type: "POST",
        url: "admin_get_quiz.do",
        dataType: "json",
        data:{"page":page, "userId":$.cookie('userData'), "classId":classId, "courseId":courseId},
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
            alert("问卷列表拉取失败");
        }
    });
};

//解析显示table
function build_table(data) {
    //清空
    $("#quiz-table").empty();
    var dataList = data.list;
    //jquery遍历,emps为遍历对象，function(index索引,item得到的每一个对象)为回调函数
    $.each(dataList,function(index, item){
        //创建div并朝里面追加内容
    	$("#quiz-table").append('<div class="quiz-list">'+
    		'<a href="#modal-quiz-check" quiz-prop="'+ item.quizId +'" class="btn quiz-detail-check" data-toggle="modal">'+
    			item.class_name + " | " + item.name + " | " + item.course + " | " + dateFormat(new Date(item.date)) +
    		'</a>'
    	);
    })
    $(".quiz-detail-check").click(getQuizDetail);
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

//显示问卷信息的总函数
function adminGetSign(page, classId, courseId){
	to_page(page, classId, courseId);
};

//点击查询按钮获取相应的问卷信息
$("#submit-which-need").click(function(){
	adminGetSign(1, $("#which-class").val(), $("#which-stage").val());
});

//页面载入的时候获取全部问卷信息
$(function(){
	adminGetSign(1, 0, 0);
})

//获取详细的问卷内容
function getQuizDetail(){
	$.ajax({
	    url:"get_quiz_by_id.do",
	    type:"POST",
	    dataType:"json",
	    data:{"quizId":$(this).attr("quiz-prop")},
	    success: function(data){
	    	$("#question-modal").empty();
	    	$.each(data.questions,function(index, item){
		        $("#question-modal").append('<div class="form-group">'+
					'<div class="form-control question-title">问题：'+
					item +
					'</div>'+
					'<div placeholder="请填写问卷内容" class="form-control question-detail">答案：'+
					data.answers[index] +
					'</div>'+
					'</div>'
					);
	    	})

	    },
	    error:function () {
	        alert("详细问卷内容拉取失败");
	    }
	});
}

//点击增加问题数量
$("#add-question-num").click(function () {
    $("#question-submit-list").append('<div class="row-fluid">'+
    '<label>问题</label>'+
    '<input class="form-control question-title question-submit" placeholder="请输入问卷问题，如需添加问题，请点击右侧＋号" required="">'+
    '</div>');
});
//点击减少问题数量
$("#delete-question-num").click(function () {
	$("#question-submit-list").children("div:last-child").remove();
})

//发布问卷
//填写问卷的提交
function publictQuiz(){
    $('.btn-primary').attr("disabled", true);
	var questions = new Array();
	$.each($(".question-submit"), function(index, item){
		questions.push($(item).val());
	});
	console.log(questions);
	$.ajax({
	    url:"public_quiz.do",
	    type:"POST",
	    dataType:"json",
	    data:{
	    	"questions":questions,
	    },
	    traditional: true,
	    success: function(data){
	        alert(data.info);
            $('.btn-primary').removeAttr("disabled");
	    },
	    error:function () {
	        alert("问卷问题提交出现异常，请重试");
            $('.btn-primary').removeAttr("disabled");
	    }
	});
	return false;
}