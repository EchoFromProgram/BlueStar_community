//个人资料转跳url设置
$(function(){
	$.ajax({
	    url:"getSessionUser.do",
	    type:"POST",
	    dataType:"json",
	    success: function(data){
	    	$('#username').html(data.name);
	    	if(data.type_id == 0){
	    		$('#index_info').attr('href', "staff_info.do");
	    	}
	    	else{
	    		$('#index_info').attr('href', "customer_info.do");
	    	}
	    },
	    error:function () {
	        //alert("信息标签url设置失败");
	    }
	});
});

//搜索框样式
$(".select").click(function () {
    $('#current_select').html($(this).children().html());
})

//响应式菜单隐藏
var jump_index = 0;
$('#huge').click(
    function () {
        if(jump_index == 0) {
            $('.sidebar').css("display", "block");

            jump_index = 1;
        }
        else{
            $('.sidebar').css("display", "none");
            jump_index = 0;
        }
    }
);

//获取权限表以及设置导航
var powerTable;
$(function(){
	   $.ajax({
	       url:"getPowerTable.do",
	       type:"POST",
	       dataType:"json",
	       success:function (data) {
	            powerTable = data;
	          //获取个人权限

	     	   $.ajax({
	     	       url:"getSessionHisPowers.do",
	     	       type:"POST",
	     	       dataType:"json",
	     	       success:function (data) {
	     	    	   var path = window.location.pathname;
	     	    	   var suffix = path.substr(path.lastIndexOf('/') + 1);
	     	    	 	$.each(data,function(i, item){
	     	    	 		if(suffix == powerTable[item].power){
	     	    	 			$("#nav-list").append(
	     		    	 				'<li class="active"><a href="#">' +
	     		    	 				powerTable[item].powerName
	     		    	 				+ "</a></li>"
	     		    	 				);
	     	    	 		}else{
	     	    	 			$("#nav-list").append(
	     		    	 				"<li><a href=" + 
	     		    	 				powerTable[item].power + 
	     		    	 				">" +
	     		    	 				powerTable[item].powerName
	     		    	 				+ "</a></li>"
	     		    	 				);
	     	    	 		}
	     	    	 	});
	     	       },
	     	       error:function () {
	     	           alert("您还未登陆或是Session失效，请重新登陆");
	     	       }
	     	   });
	       },
	       error:function () {
	           alert("您还未登陆或是Session失效，请重新登陆");
	       }
	   });
	});


//时间格式转换
function dateFormat(date) {  
    var y = date.getFullYear();  
    var m = date.getMonth() + 1;  
    m = m < 10 ? '0' + m : m;  
    var d = date.getDate();  
    d = d < 10 ? ('0' + d) : d;  
    return y + '-' + m + '-' + d;  
};

//注销功能
$("#logout").click(function(){
	/*$.cookie('userData', null);
	$.cookie('infoId', null);
	$.cookie('typeId', null);
	$.cookie('name', null);*/
	$.ajax({
        url: "logout.do",
        type: "GET",
        success: function (resp) {
            console.log(resp);
            if (resp.code === 0) {
                window.location.href = "login.do";
                return;
            }

            alert("对不起，退出失败！");
        },
        error: function () {
            alert("对不起，退出失败！");
        }
    });
});