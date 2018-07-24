//获取问卷的问题
var quizDetailId;
$(function(){
	$.ajax({
	    url:"get_quiz_question.do",
	    type:"POST",
	    dataType:"json",
	    success: function(data){
	    	if(data.code == 0){
	    	quizDetailId = data.data.quizDetailId;
	    	$("#quiz-answer").empty();
	    	$.each(data.data.questions,function(index, item){
	    		$("#quiz-answer").append('<div class="form-group">'+
	    				'<div class="form-control question-title">'+
	    				item.question +
	    				'</div>'+
	    				'<textarea maxlength="255" placeholder="请填写问卷内容" class="form-control question-detail answer-box" required=""></textarea>'+
	    				'</div>'
	    				);
	    	});
	    	}else{
	    		alert(data.info)
	    	}
	    },
	    error:function () {
	        alert("问卷的问题部分拉取失败");
	    }
	});
});

//填写问卷的提交
function submitQuiz(){
	var answers = new Array();
	$.each($(".answer-box"), function(index, item){
		answers.push($(item).val());
	});
	$.ajax({
	    url:"write_quiz.do",
	    type:"POST",
	    dataType:"json",
	    data:{
	    	"answers":answers,
	    	"quizDetailId":quizDetailId
	    },
	    traditional: true,
	    success: function(data){
	        alert(data.info);
	    },
	    error:function () {
	        alert("问卷提交出现异常，请重试");
	    }
	});

	return false;
}

function to_page(page){
    $.ajax({
        type: "POST",
        url: "student_get_quiz.do",
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
            alert("网络错误");
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

//显示问卷信息的总函数
function adminGetSign(page){
	to_page(page);
};

//页面载入的时候获取全部问卷信息
$(function(){
	adminGetSign(1);
})

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
	        alert("网络错误");
	    }
	});
}