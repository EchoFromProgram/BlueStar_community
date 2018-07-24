$(function(){
	to_page(1);
});

function to_page(page){
    $.ajax({
        type: "POST",
        url: "admin_get_all_notice.do",
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
            alert("拉取通知列表失败");
        }
    });
};

//解析显示table
function build_table(data) {
    //清空
    $("#accordion-notice").empty();
    var dataList = data.list;
    //jquery遍历,dataList为遍历对象，function(index索引,item得到的每一个对象)为回调函数
    $.each(dataList,function(index, item){
    	$("#accordion-notice").append('<div class="accordion-group">' + 
                '<h3 class="accordion-heading">' + 
                '<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion-notice" href="#accordion-element-notice-'+ item.noticeDetailId +'">'+
					new Date(item.date).toLocaleString() + ": &nbsp;&nbsp;&nbsp;" + item.title +
                '</a>'+
                '<div id="accordion-element-notice-'+ item.noticeDetailId +'" class="accordion-body collapse">'+
                    '<h4 class="accordion-inner">'+
                        item.content +
                    '</h4>'+
                '</div>'+
                '<!-- 通知删除模块 -->'+
                '<button href="#modal-notice-delete-'+ item.noticeDetailId +'" role="button" class="btn btn-danger pull-right" data-toggle="modal">'+
                    '删除'+
                '</button>'+
                '<div class="modal fade" id="modal-notice-delete-'+ item.noticeDetailId +'" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'+
                    '<div class="modal-dialog">'+
                        '<div class="modal-content">'+
                            '<div class="modal-header">'+
                                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>'+
                                '<h4 class="modal-title">'+
                                    '确定要删除这个通知吗？'+
                                '</h4>'+
                            '</div>'+
                            '<div class="modal-footer">'+
                                '<button class="btn btn-danger delete-table-button" delete-prop="'+ item.noticeDetailId +'">删除</button>'+
                            '</div>'+
                        '</div>'+
                    '</div>'+
                '</div>'+
                '<!-- 通知修改模块 -->'+
                '<button href="#modal-container-pub-'+ item.noticeDetailId +'" pre-prop="'+ item.noticeDetailId +'" data-toggle="modal" class="btn btn-info pull-right pre-update-button" >'+
                    '修改'+
                '</button>'+
                '<div class="modal fade" id="modal-container-pub-'+ item.noticeDetailId +'" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'+
                    '<div class="modal-dialog">'+
                        '<div class="modal-content">'+
                            '<div class="modal-header">'+
                                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>'+
                                '<h4 class="modal-title">'+
                                    '请在下面修改通知内容'+
                                '</h4>'+
                            '</div>'+
                            '<div class="modal-body">'+
                            	'<div class="form-group">'+
                            		'<label class="sr-only">标题</label>'+
                            		'<textarea id="notcie-detail-update-title-'+ item.noticeDetailId +'" class="form-control" placeholder="请输入通知标题" required=""></textarea>'+
                            		'<span class="help-block" id="update-notice-help-title-1"></span>'+
                            	'</div>'+
                                '<div class="form-group">'+
                                    '<label class="sr-only">内容</label>'+
                                    '<textarea id="notcie-detail-update-'+ item.noticeDetailId +'" class="form-control" placeholder="请输入通知具体内容" required=""></textarea>'+
                                    '<span class="help-block" id="update-notice-help-1"></span>'+
                                '</div>'+
                            '</div>'+
                            '<div class="modal-footer">'+
                                '<button class="btn btn-danger update-table-button" update-prop="'+ item.noticeDetailId +'">修改</button>'+
                            '</div>'+
                        '</div>'+
                    '</div>'+
                '</div>'+
            '</h3>'+
        '</div>'+'<br>'
    	);
    })
    $(".delete-table-button").click(delete_notice);
    $(".pre-update-button").click(get_notice_by_detailId);
    $(".update-table-button").click(update_notice);
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
    var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
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
    var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href","#"));
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

//删除通知
function delete_notice(){
	$('.delete-table-button').attr("disabled", true);
	$.ajax({
	    url:"delete_notice.do",
	    type:"POST",
	    dataType:"json",
	    data:{"noticeId":$(this).attr("delete-prop")},
	    success: function(data){
            $('.delete-table-button').removeAttr("disabled");
	        alert(data.info);
	        window.location.reload();
	    },
	    error:function () {
            $('.delete-table-button').removeAttr("disabled");
	        alert("删除通知出现异常");
	    }
	});
}

//点击修改后，将后台数据展示在前台，以便后面的修改
function get_notice_by_detailId(){
	var titleId = 'notcie-detail-update-title-'+ $(this).attr("pre-prop") +'';
	var contentId = 'notcie-detail-update-'+ $(this).attr("pre-prop") +'';
	$.ajax({
	    url:"get_one_notice.do",
	    type:"POST",
	    dataType:"json",
	    data:{"noticeId":$(this).attr("pre-prop")},
	    success: function(data){
	        $("#" + titleId).val(data.data.title);
	        $("#" + contentId).val(data.data.content);
	    },
	    error:function () {
	        alert("拉取通知信息失败");
	    }
	});
}

//更新通知
function update_notice(){
	var titleId = 'notcie-detail-update-title-'+ $(this).attr("update-prop") +'';
	var contentId = 'notcie-detail-update-'+ $(this).attr("update-prop") +'';
	$.ajax({
	    url:"update_notice.do",
	    type:"POST",
	    dataType:"json",
	    data:{"noticeDetailId":$(this).attr("update-prop"), "content": $("#" + contentId).val(), "title":$("#" + titleId).val()},
	    success: function(data){
	        alert(data.info);
	        window.location.reload();
	    },
	    error:function () {
	        alert("更新通知出现异常");
	    }
	});
}

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
		},
		error:function () {
          alert("班级信息拉取失败");
      }
	});
});

//添加一条通知
function addNotice(){
    $('#submit-change').attr("disabled", true);
    console.log("ing");
    $('#submit-change').text("正在发布...");
	$.ajax({
	    url:"add_notice.do",
	    type:"POST",
	    dataType:"json",
	    async: false,
	    data:{
	    	"classId":$("#which-class").val(),
	    	"title":$("#notcie-title-input").val(),
	    	"content":$("#notcie-detail-input").val()
	    	},
	    success: function(data){
            alert(data.info);
            $('#submit-change').removeAttr("disabled");
            $('#submit-change').text("发布");
            console.log("already");
	        window.location.reload();
	    },
	    error:function () {
            $('#submit-change').removeAttr("disabled");
            $('#submit-change').text("发布");
	        alert("添加通知出现异常");
	    }
	});
}

