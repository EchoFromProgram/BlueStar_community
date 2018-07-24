//登陆时用户名提示
$('#username').blur(
    function () {
        if($('#username').val() == "")
        {
            $('#username-div').addClass("has-error");
            $('#username-help').text("用户名不能为空");
        }
        else
        {
            $('#username-div').removeClass("has-error")
            $('#username-help').text("");
        }
    }
);

//登陆时密码提示
$('#password').blur(
    function () {
        if($('#password').val() == "")
        {
            $('#password-div').addClass("has-error");
            $('#password-help').text("密码不能为空");
        }
        else
        {
            $('#password-div').removeClass("has-error")
            $('#password-help').text("");
        }
    }
);

//登陆验证 以及 必要数据的cookie
function submitData() {
	$("#submit").attr("disabled", true);
	$("#submit").text("正在登录...");
    $.ajax({
        type: "POST",
        url: "loginCheck.do",
        data: {"userName":$("#username").val(), "password":$("#password").val()},
        dataType: "json",
        success: function(data){
            if(0 == data.code)
            {
//            	$.cookie('userData', data.data.user.user_id);
//            	$.cookie('infoId', data.data.user.info_id);
//            	$.cookie('typeId', data.data.user.type_id);
//            	$.cookie('name', data.data.user.name);
            	$("#submit").removeAttr("disabled");
	        	$("#submit").text("登录");
                window.location.href="index.do";
            }
            else
            {
            	$('#password-help').text(data.info);
	            $('#username-div').addClass("has-error");
	            $('#password-div').addClass("has-error");
	            $("#submit").removeAttr("disabled");
	        	$("#submit").text("登录");
            }
        },
        error:function () {
        	$("#submit").removeAttr("disabled");
        	$("#submit").text("登录");
            alert("登陆时发生异常");
        }
    });
    return false;
};