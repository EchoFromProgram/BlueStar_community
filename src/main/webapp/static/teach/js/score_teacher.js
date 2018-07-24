//获取班级列表
$(function(){
	$.ajax({
		url:"getSessionHisClasses.do",
		type:"POST",
		success: function(data){
			//便签1班级获取
			$("#which-class").empty();
			$("#which-class").append('<option value="0">全部班级</option>');
		    $.each(data,function(index, item){
		        var option = $("<option></option>").append(item.className);
		        option.attr("value", item.classId);
		        option.appendTo("#which-class");
		    });
		    //便签2班级获取
		    $("#which-class-need").empty();
		    $.each(data,function(index, item){
		        var option = $("<option></option>").append(item.className);
		        option.attr("value", item.classId);
		        option.appendTo("#which-class-need");
		    });
		    getUsers($("#which-class-need").val());
		},
		error:function () {
          alert("班级信息拉取失败");
      }
	});
});

function to_page(page, classId, stage){
    $.ajax({
        type: "POST",
        url: "teacher_get_score.do",
        dataType: "json",
        data:{"page":page, "classId":classId, "stage":stage},
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
            alert("成绩列表拉取失败");
        }
    });
    
    //获取统计情况
    $.ajax({
        type: "POST",
        url: "teacher_get_static_score.do",
        dataType: "json",
        data:{"classId":classId, "stage":stage},
        success: function(data){
        	if(data.code == 0){
        		if(data.data != 0){
	                $("#teacher-score-good-static").text(data.data + '%');
	                $("#teacher-score-good-static").css("width", data.data + '%');
	                $("#teacher-score-bad-static").text((100 - Number(data.data)) + '%');
	                $("#teacher-score-bad-static").css("width", (100 - Number(data.data)) + '%');
        		}else{
        			$("#teacher-score-good-static").text('0%');
	                $("#teacher-score-good-static").css("width", '0%');
	                $("#teacher-score-bad-static").text('0%');
	                $("#teacher-score-bad-static").css("width", '0%');
        		}
            }else{
            	alert(data.info);
            }
        },
        error:function () {
            alert("成绩统计情况拉取失败");
        }
    });
};

//解析显示table
function build_table(data) {
    //清空
    $("#teacher-get-score-table tbody").empty();
    var dataList = data.list;
    //jquery遍历,emps为遍历对象，function(index索引,item得到的每一个对象)为回调函数
    $.each(dataList,function(index, item){
        //创建td并朝里面追加内容
    	var stage = null;
    	 if(item.status == 1){
	            stage = "第一阶段测试";
	        }else if(item.status == 2){
	        	stage = "第二阶段测试";
	        }else if(item.status == 3){
	        	stage = "第三阶段测试";
	        }
    	$("#teacher-get-score-table tbody").append('<tr> <td>'+
    		    item.className +
    		    '</td>'+
    		    '<td>'+
    		    item.name +
    		    '</td>'+
    		    '<td>'+
    		    dateFormat(new Date(item.date)) +
    		    '</td>'+
    		    '<td>'+
    		    stage +
    		    '</td>'+
    		    '<td>'+
    		    item.score+
    		    '</td>'+
    		    '<td>'+
    		    '<button id="modal-score-delete-sta-1" href="#modal-score-delete-s-' + item.scoreId + '" role="button" class="btn btn-danger pull-right" data-toggle="modal">'+
    		    '删除'+
    		    '</button>'+
    		    '<div class="modal fade" id="modal-score-delete-s-' + item.scoreId + '" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'+
    		    '<div class="modal-dialog">'+
    		    '<div class="modal-content">'+
    		    '<div class="modal-header">'+
    		    '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>'+
    		    '<h4 class="modal-title " id="myModalLabel-2">'+
    		    '确定要删除这条成绩吗？'+
    		    '</h4>'+
    		    '</div>'+
    		    '<div class="modal-footer">'+
    		    '<button class="btn btn-danger delete-score-button" delete-prop="' + item.scoreId + '">删除</button>'+
    		    '</div>'+
    		    '</div>'+
    		    '</div>'+
    		    '</div>'+
    		    '<button id="modal-score-update-sta-1" href="#modal-score-update-s-' + item.scoreId + '" pre-prop="'+ item.scoreId +'" role="button" class="btn btn-primary pull-right pre-update-button" data-toggle="modal">'+
    		    '修改'+
    		    '</button>'+
    		    '<div class="modal fade" id="modal-score-update-s-' + item.scoreId + '" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'+
    		    '<div class="modal-dialog">'+
    		    '<form class="form" onsubmit="return ">'+
    		    '<div class="modal-content">'+
    		    '<div class="modal-header">'+
    		    '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>'+
    		    '<h4 class="modal-title ">'+
    		    '修改这位学生的成绩信息'+
    		    '</h4>'+
    		    '</div>'+
    		    '<div class="modal-body">'+
    		    '<div class="form-group">'+
    		    '<label class="sr-only">姓名</label>'+
    		    '<div id="score-update-name-i-s-' + item.scoreId + '" class="placeholders">' + item.name + '</div>'+
    		    '<span class="help-block" id="update-score-help-name-s-1"></span>'+
    		    '</div>'+
    		    '<div class="form-group" id="score-update-num-s-' + item.scoreId + '">'+
    		    '<label class="sr-only">分数</label>'+
    		    '<input min="0" type="number" id="score-update-num-i-s-' + item.scoreId + '" class="form-control" required="" value="' + item.score + '"></input>'+
    		    '<span class="help-block" id="update-score-help-num-s-1"></span>'+
    		    '</div>'+
    		    '</div>'+
    		    '<div class="modal-footer">'+
    		    '<button class="btn btn-danger update-score-button" update-prop="'+ item.scoreId +'">修改</button>'+
    		    '</div>'+
    		    '</div>'+
    		    '</form>'+
    		    '</div>'+
    		    '</div>'+
    		    '</td>'+
    		    '</tr>'
    		);
    })
    $(".delete-score-button").click(deleteScore);
    $(".update-score-button").click(updateScore);
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


//显示分数信息的总函数
function teacherGetScore(page, classId, stage){
	to_page(page, classId, stage);
};

//点击查询按钮获取相应的分数信息
$("#submit-which-score").click(function(){
	teacherGetScore(1, $("#which-class").val(), $("#which-stage").val());
});

//页面载入的时候获取全部分数信息
$(function(){
	teacherGetScore(1, 0, 0);
})

//更新学生成绩
function updateScore(){
	$.ajax({
		url:"update_score.do",
		data:{
			"scoreId":$(this).attr("update-prop"),
			"score":$("#score-update-num-i-s-" + $(this).attr("update-prop")).val()
			},
		dataType:"json",
		type:"POST",
		async:false,
		traditional: true,
		success: function(data){
			window.location.reload();
	        alert(data.info);
	    },
	    error:function () {
	        alert("成绩更新出现异常");
	    }
	});
	return false;
}

//删除学生成绩
function deleteScore(){
	$.ajax({
	    url:"delete_score.do",
	    type:"POST",
	    dataType:"json",
	    data:{"scoreId":$(this).attr("delete-prop")},
	    success: function(data){
	    	window.location.reload();
	        alert(data.info);
	    },
	    error:function () {
	        alert("成绩删除出现异常");
	    }
	});
}


//发布成绩
function publicScore(){
	var names = new Array();
	$.each($(".td-name"), function(index, item){
		names.push($(item).attr("user-id-prop"));
	});

	var scores = new Array();
	$.each($(".td-score"), function(index, item){
		scores.push($(item).val());
	});

	$.ajax({
	    url:"public_score.do",
	    type:"POST",
	    dataType:"json",
	    traditional: true,
	    data:{
	    	"userIds":names,
	    	"scoreArr":scores,
	    	"classId":$("#which-class-need").val(),
	    	"stage":$("#which-stage-need").val()
	    	},
	    success: function(data){
	    	if(data.code == 0){
	    		window.location.reload();
	    	}
	        alert(data.info);
	    },
	    error:function () {
	        alert("成绩发布出现异常");
	    }
	});
	return false;
}

//通过classId获取这个班级的所有学生
function getUsers(classId){
		$.ajax({
	    url:"get_users.do",
	    type:"POST",
	    dataType:"json",
	    data:{"classId":classId},
	    success: function(data){
	    	$("#public-score-box").empty();
	        $.each(data.data,function(index, item){
	        	$("#public-score-box").append(
	        	'<tr>'+
	        	'<td class="td-name" user-id-prop="' + item.userId + '">'+
	        		item.name +
                '</td>'+
                '<td>'+
                	'<input min="0" max="100" type="number" class="form-control td-score" placeholder="学生成绩分数" required="">'+
                '</td>'+
                '</tr>'
	        	)
	        	
	        });
	    },
	    error:function () {
	        alert("班级的学生信息获取失败，请重试");
	    }
	});
}

$("#which-class-need").change(function(){
	getUsers(this.value);
});
