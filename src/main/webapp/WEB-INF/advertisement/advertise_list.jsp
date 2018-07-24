<%@ page import="com.bluestar.advertisement.vo.AdVo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.github.pagehelper.PageInfo" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <meta name="description" content="BULUSTAR-index">
    <meta name="author" content="Mackyhuang">
    <link rel="icon" href="../../favicon.ico">

    <title>用户管理</title>

    <link href="./static/teach/css/bootstrap.min.css" rel="stylesheet">
    <link href="./static/teach/css/ie10-viewport-bug-workaround.css" rel="stylesheet">
    <link href="./static/teach/css/dashboard.css" rel="stylesheet">
    <link href="./static/teach/css/mycss.css" rel="stylesheet">
    <style>
        .list_tr td {
            height: 50px;
            line-height: 50px !important;
        }

        .btn-mine {
            margin-top: 7px;
        }
    </style>
</head>

<body>
<!-- 顶部导航栏 -->
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#" id="huge">蓝星苑</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">
                    <span class="glyphicon glyphicon-user"></span>
                    &nbsp;
                    <span class="" id="username">Mackyhuang</span>
                    <span class="">，欢迎您</span></a></li>
                <li><a href="staff_info.do">个人资料</a></li>
                <li><a href="#" id="logout">注销</a></li>
                <li><a href="#">帮助</a></li>
            </ul>
        </div>
    </div>
</nav>
<!-- 舞台 -->
<div class="container-fluid">
    <div class="row">
        <!-- 左侧导航栏 -->
        <div class="col-sm-3 col-md-2 sidebar">
            <div class="nav nav-sidebar">
                <li class="text-center bord"><a href="#"><strong>功能一览</strong> <span class="sr-only">(current)</span></a></li>
            </div>
            <ul class="nav nav-sidebar" id="nav-list">

            </ul>
        </div>

        <!-- 右侧模块 -->
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1 class="page-header">广告管理</h1>

            <div class="span12" id="jump-menu">
                <div class="tabbable" id="tabs-user">
                    <div class="nav nav-tabs">
                        <!-- 标签1 -->
                        <div class="active">
                            <a href="#panel-1" data-toggle="tab" class="col-xs-4 col-sm-4 placeholders" id="show-role" style='text-decoration:none;'>
                                <h4>查看广告列表</h4>
                                <span class="text-muted">点击查看当前广告详细</span>
                            </a>
                        </div>
                    </div>
                    <!-- 标签1对应的模块 列表 -->
                    <div class="tab-content">
                        <div class="tab-pane active" id="panel-1">
                            <h2 class="sub-header">广告列表</h2>
                            <form action="listAd.do">
                                <div class="row">
                                    <div class="col-sm-4">
                                        <label for="which-input" class="">广告标题</label>
                                        <input type="text" name="adTitle" class="form-control" placeholder="请输入广告标题进行查询" id="which-input" value=${title}>
                                    </div>
                                    <div class="col-sm-4">
                                        <label for="which-stage" class="">状态</label>
                                        <select class="form-control" id="which-stage" style="width:100%" name="adStatus" data-sele="${status}">
                                            <option value ="-1">全部</option>
                                            <option value ="1">正常</option>
                                            <option value ="2">上架</option>
                                            <option value ="3">无效</option>
                                        </select>
                                    </div>
                                    <div class="col-sm-1">
                                        <br/>
                                        <button class="btn btn-primary text-center center-block " id="submit-which-stage">查询</button>
                                    </div>
                                </div>
                            </form>
                            <div class="row">
                                <br>
                                <div class="col-sm-3">
                                    <button href="#panel-3" data-toggle="tab" class="btn btn-warning " id="submit-add">添加广告</button>
                                </div>
                                <br>
                            </div>
                            <div class="table-responsive">
                                <table class="table table-striped table-hover" id="role-table">
                                    <thead>
                                    <tr>
                                        <th>广告标题</th>
                                        <th>采编人</th>
                                        <th>采编时间</th>
                                        <th>状态</th>
                                        <th>url</th>
                                        <th>图片</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <%
                                        String status = null;
                                        List<AdVo> ads = new ArrayList<>();
                                        ads = (List<AdVo>)request.getAttribute("ads");
                                        if(ads != null && ads.size() > 0)
                                        for(int i = 0; i < ads.size(); i++) {
                                            AdVo advo = ads.get(i);
                                            if(advo.getAdStatus().equals("1")){
                                                status = "正常";
                                            } else if(advo.getAdStatus().equals("2")) {
                                                status = "上架";
                                            } else if(advo.getAdStatus().equals("3")) {
                                                status = "无效";
                                            }

                                    %>
                                    <tr class="list_tr">
                                        <td><%= advo.getAdTitle() %></td>
                                        <td><%= advo.getAdCreateUser() %></td>
                                        <td> <fmt:formatDate value="<%= advo.getAdCreateTime() %>" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                                        <td><%= status %></td>
                                        <td><%= advo.getAdLinkUrl() %></td>
                                        <td><img style="height: 60px;width: 70px" src="<%= advo.getEnclosurePath() %>" alt=""></td>
                                        <td>
                                            <button   href="#modal-delete-block" data-toggle="modal" data-adId="<%= advo.getAdId() %>" class="delete-role-btn btn btn-danger pull-right btn-mine delete-btn"   role="button">
                                                删除
                                            </button>
                                            <button data-adId="<%= advo.getAdId() %>" data-picId="<%= advo.getAdPicture()%>" class="update-role-btn btn btn-primary pull-right pre-update-button btn-mine update-btn" href="#modal-update-block" data-toggle="modal" role="button">
                                                修改
                                            </button>
                                        </td>
                                    </tr>

                                    <%
                                        }
                                    %>
                                    </tbody>
                                </table>

                            </div>
                            <!-- 分页信息 -->
                            <div class="row">
                                <!-- 分页文字 -->
                                <div class="col-md-5" id="page_info_area"></div>
                                <!-- 分页条 -->
                                <div class="col-md-5" id="page_nav_area"></div>
                            </div>
                            <!--/ 分页结束-->
                                <!-- 被隐藏的删除框 -->
                                <div class="modal fade" id="modal-delete-block" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                                <h4 class="modal-title " id="myModalLabel">
                                                    确定要删除这个广告吗?
                                                </h4>
                                            </div>
                                            <div class="modal-footer">
                                                <button class="btn btn-danger" id="delete-button">删除</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                        </div>


                            <!-- 被隐藏的修改模块 -->
                            <div class="modal fade" id="modal-update-block" role="dialog" aria-labelledby="myModalLabel2" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                            <form id="updateForm" method="POST" >
                                                <div class="form-group" >
                                                    标题<input id="update_title" type="text" name="adTitle" maxlength="200"  style="width:400px;margin-bottom: 20px" class="form-control center-block" placeholder="标题" required="" autofocus="">
                                                    采编人<input id="update_createUser" type="text" name="adCreateUser" maxlength="63" style="width:400px;margin-bottom: 20px" class="form-control center-block" placeholder="创建人" required="" autofocus="">
                                                    排序<input id="update_order" type="number" name="adOrder"  min=0 style="width:400px;margin-bottom: 20px" class="form-control center-block" placeholder="排序" required="" autofocus="">
                                                    url<input id="update_url" type="text" name="adLinkUrl" maxlength="200" style="width:400px;margin-bottom: 20px" class="form-control center-block" placeholder="url" required="" autofocus="">
                                                    状态<select class="form-control center-block" id="update-which-stage" style="width:400px;margin-bottom: 20px;" name="adStatus" data-sele="">
                                                            <option value ="1">正常</option>
                                                            <option value ="2">上架</option>
                                                            <option value ="3">无效</option>
                                                        </select>
                                                    <input id="update_adId"  name="adId" type="hidden"  style="width:400px;margin-bottom: 20px" class="form-control center-block" placeholder="url" required="" autofocus="" >
                                                    <input id="update_adPic" name="pictureId"  type="hidden"  style="width:400px;margin-bottom: 20px" class="form-control center-block" placeholder="url" required="" autofocus="">
                                                        <img  style="margin: 0 10px;width:96%" id="update_pic" src="" alt="">
                                                    <input  type="file" name="file" style="width:400px;margin-bottom: 20px" class=" center-block"  autofocus="" accept="image/*">
                                                </div>
                                            </form>
                                        </div>
                                        <div class="modal-footer">
                                            <button class="btn btn-danger" id="update-button">修改</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        <!-- //被隐藏的修改模块 -->


                        <!-- 标签3对应的模块  新增模块-->
                        <div class="tab-pane col-sm-12" id="panel-3">
                            <h2 class="sub-header">新增广告</h2>
                            <form id="uploadForm" method="POST" >
                                <div class="form-group" id="rolename-create">
                                    <input id="addTitle" type="text" name="adTitle" maxlength="200"  style="width:400px;margin-bottom: 20px" class="form-control center-block" placeholder="标题(必填)" required="required" autofocus="">
                                    <input id="addCreateUser" type="text" name="adCreateUser" maxlength="63" style="width:400px;margin-bottom: 20px" class="form-control center-block" placeholder="创建人(必填)" required="required" autofocus="">
                                    <input type="text" name="adOrder" min=0 style="width:400px;margin-bottom: 20px" class="form-control center-block" placeholder="排序" required="required" autofocus="">
                                    <input type="text" name="adLinkUrl" maxlength="200" style="width:400px;margin-bottom: 20px" class="form-control center-block" placeholder="url"  autofocus="">
                                    <input type="file" name="file" style="width:400px;margin-bottom: 20px" class=" center-block"  autofocus="" accept="image/*">
                                </div>
                                <button  class="btn btn-lg btn-primary btn-block center-block" id="create-btn" style="width:200px;">创建</button>
                            </form>

                        </div>
                        <!-- //标签3对应的模块  新增模块-->


                        <!-- 标签4对应的模块 -->
                        <%--<div class="tab-pane col-sm-5 col-sm-offset-3" id="panel-4">--%>
                            <%--<h2 class="sub-header">删除标题</h2>--%>
                            <%--<form class="form" onsubmit="return false">--%>
                                <%--<div class="form-group" id="role-delete" style="margin-top:50px">--%>
                                    <%--<label for="delete-role-input" class="sr-only">角色名</label>--%>
                                    <%--<input type="text" maxlength="63" id="delete-role-input" class="form-control" placeholder="输入需要删除的角色名" required=""  readonly="readonly">--%>
                                    <%--<span class="help-block" id="delete-user-help"></span>--%>
                                <%--</div>--%>
                                <%--<!-- 遮罩窗体确定是否操作 -->--%>
                                <%--<!-- 触发按钮 -->--%>
                                <%--<button  id="modal-role-delete-a" href="#modal-delete-block" role="button" class="btn btn-lg btn-primary btn-block" data-toggle="modal" style="margin-top:50px">--%>
                                    <%--删除--%>
                                <%--</button>--%>

                            <%--</form>--%>
                        <%--</div>--%>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="./static/teach/js/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="./static/teach/js/jquery.min.js"><\/script>')</script>
<script src="./static/teach/js/bootstrap.min.js"></script>
<script src="./static/teach/js/holder.min.js"></script>
<script src="./static/teach/js/ie10-viewport-bug-workaround.js"></script>
<script src="./static/teach/js/general.js"></script>
<script src=./static/advertisement/js/advertise.js></script>

</body>
<script>
    var pageData = <%= request.getAttribute("pageInfo") %>;
    to_page(pageData);
</script>
</html>
