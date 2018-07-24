$(function(){
	$.ajax({
	    url:"student_get_score.do",
	    type:"POST",
	    dataType:"json",
	    data:{"page":1},
	    success: function(data){
	       $("#student-score-table ")
			//清空
		    $("#student-score-table tbody").empty();
		    var dataList = data.data.list;
		    //jquery遍历,emps为遍历对象，function(index索引,item得到的每一个对象)为回调函数
		    $.each(dataList,function(index, item){
		        //创建td并朝里面追加内容
		        var classId = $("<td></td>").append(item.className);
		        var name = $("<td></td>").append(item.name);
		        var date = $("<td></td>").append(dateFormat(new Date(item.date)));
		        var status = $("<td></td>");
		        if(item.status == 1){
		            status.append("第一阶段测试")
		        }else if(item.status == 2){
		            status.append("第二阶段测试")
		        }else if(item.status == 3){
		            status.append("第三阶段测试")
		        }
		        var score = $("<td></td>").append(item.score);
		        //向一个tr中添加所有的td
		        $("<tr></tr>").append(classId).append(name).append(date)
		            .append(status).append(score).appendTo("#student-score-table tbody");
		    })
	    },
	    error:function () {
	        alert("成绩列表拉取失败");
	    }
	});
});