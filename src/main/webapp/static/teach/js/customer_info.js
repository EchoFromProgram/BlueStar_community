//预先获取页面中的资料信息
$(function(){
	//获取省
	getProvince();
	$.ajax({
		url:"get_customer_info.do",
		dataTpye:"json",
		type:"POST",
		success: function(data){
			if(data.code == 0){
				$("#name-display").html($("#username").text());
				$("#id-display").html(data.data.identityNum);
				$("#school-display").html(data.data.school);
				$("#grade-display").html(data.data.gradeMajor);
				$("#qq-display").html(data.data.qq);
				$("#tel-display").html(data.data.telephone);
				$("#mail-display").html(data.data.email);
				$("#info-update-ID-input").val(data.data.identityNum);
				$("#info-update-grade-input").val(data.data.gradeMajor);
				$("#info-update-qq-input").val(data.data.qq);
				$("#info-update-tel-input").val(data.data.telephone);
				$("#info-update-mail-input").val(data.data.email);
				//给已经选择的省selected
				$('#province-select option').filter(function(){return $(this).text()==data.data.province}).attr("selected",true);
				getCity();
				//给已经选择的城市selected
				$('#city-select option').filter(function(){return $(this).text()==data.data.city}).attr("selected",true);
				getSchool();
				//给已经选择的学校selected
				$('#school-select option').filter(function(){return $(this).text()==data.data.school}).attr("selected",true);
			}else{
				alert(data.info);
			}
		},
		error:function () {
            alert("预先拉取个人信息出现异常");
        }
	});
});


function setCustomerInfo(){
	$.ajax({
		url:"update_customer_info.do",
		type:"POST",
		async : false,
		data:{
			"identityNum":$("#info-update-ID-input").val(),
			"province":$("#province-select").find("option:selected").text(),
			"city":$("#city-select").find("option:selected").text(),
			"school":$("#school-select").find("option:selected").text(),
			"gradeMajor":$("#info-update-grade-input").val(),
			"qq":$("#info-update-qq-input").val(),
			"telephone":$("#info-update-tel-input").val(),
			"email":$("#info-update-mail-input").val()
			},
		success: function(data){
			if(data.code == 0){
				window.location.reload();
			}
			alert(data.info);
		},
		error:function () {
            alert("个人资料设置错误");
        }
	});
	return false;
};

//获取省份
function getProvince(){
    $.ajax({
        url:"get_provinces.do",
        type:"POST",
        dataType:"json",
        async : false,
        success: function(data){
            $("#province-select").empty();
            $("#province-select").append("<option value='' disabled selected style='display:none;'>请选择省份</option>  ");
            $.each(data,function(index, item){
                var option = $("<option></option>").append(item.name);
                option.attr("value", item.id);
                option.appendTo("#province-select");
            });
        },
        error:function () {
            alert("获取省份错误");
        }
    });
}


//获取市
function getCity(){
    $.ajax({
        url:"get_citys.do",
        type:"POST",
        dataType:"json",
        async : false,
        data:{"provinceId":$("#province-select").val()},
        success: function(data){
            $("#city-select").empty();
            $("#city-select").append("<option value='' disabled selected style='display:none;'>请选择城市</option>");
            $.each(data,function(index, item){
                var option = $("<option></option>").append(item);
                option.appendTo("#city-select");
            });
        },
        error:function () {
            alert("获取城市错误");
        }
    });
}
$("#province-select").change(
    getCity
);


//获取学校
function getSchool(){
  $.ajax({
      url:"get_schools.do",
      type:"POST",
      dataType:"json",
      async : false,
      data:{"city":$("#city-select").val()},
      success: function(data){
          $("#school-select").empty();
          $("#school-select").append("<option value='' disabled selected style='display:none;'>请选择学校</option>");
          $.each(data,function(index, item){
              var option = $("<option></option>").append(item.name);
              option.attr("value", item.id);
              option.appendTo("#school-select");
          });
      },
      error:function () {
          alert("获取学校错误");
      }
  });
}
$("#city-select").change(getSchool);





