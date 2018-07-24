<%--
  Created by IntelliJ IDEA.
  User: MackyHuang
  Date: 2018/7/18
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />
    <meta name="description" content="BULUSTAR-index">
    <meta name="author" content="Mackyhuang">
    <link rel="icon" href="../../favicon.ico">

    <title>用户管理</title>

    <link href="./static/information/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="./static/information/third-party/jquery.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="./static/information/umeditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="./static/information/umeditor.min.js"></script>
    <script type="text/javascript" src="./static/information/lang/zh-cn/zh-cn.js"></script>
    <link rel="stylesheet" type="text/css" href="./static/information/editor.css">
    <link href="./static/teach/css/bootstrap.min.css" rel="stylesheet">
    <link href="./static/teach/css/ie10-viewport-bug-workaround.css" rel="stylesheet">
    <link href="./static/teach/css/dashboard.css" rel="stylesheet">
    <link href="./static/teach/css/mycss.css" rel="stylesheet">
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
            <h1 class="page-header">资讯管理</h1>

            <div class="span12" id="jump-menu">
                <div class="tabbable" id="tabs-user">
                    <div class="nav nav-tabs">
                        <!-- 标签1 -->
                        <div class="active">
                            <a href="#panel-1" data-toggle="tab" class="col-xs-4 col-sm-4 placeholders" id="show-role" style='text-decoration:none;'>
                                <h4>查看资讯列表</h4>
                                <span class="text-muted">点击查看当前资讯详细</span>
                            </a>
                        </div>
                    </div>
                    <!-- 标签1对应的模块 列表 -->
                    <div class="tab-content">
                        <div class="tab-pane active" id="panel-1">
                            <h2 class="sub-header">资讯列表</h2>
                            <form action="information_list.do", method="get">
                            <div class="row">
                                <div class="col-sm-4">
                                    <label for="which-input" class="">标题</label>
                                    <input name="title" type="text" class="form-control" placeholder="请输入资讯标题" id="which-input" value="${title}">
                                </div>
                                <div class="col-sm-4">
                                    <label for="which-stage" class="">类型</label>
                                    <select name="statu" class="form-control" id="which-stage" style="width:100%" selec="${statu}">
                                        <option value ="-1">全部</option>
                                        <option value ="1">正常</option>
                                        <option value ="2">上架</option>
                                        <option value ="3">无效</option>
                                    </select>
                                </div>
                                <div class="col-sm-1">
                                    <br/>
                                    <input type="submit" class="btn btn-primary text-center center-block " id="submit-which-stage" value="查询">
                                </div>
                            </div>
                            </form>
                            <div class="row">
                                <br>
                                <div class="col-sm-3">
                                    <button href="#panel-3" data-toggle="tab" class="btn btn-warning " id="submit-add">添加</button>
                                </div>
                                <br>
                            </div>
                            <div class="table-responsive">
                                <table class="table table-striped table-hover" id="role-table">
                                    <thead>
                                    <tr>
                                        <th>标题</th>
                                        <th>作者</th>
                                        <th>采编人</th>
                                        <th>发布时间</th>
                                        <th>采编时间</th>
                                        <th>状态</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${informationList }" var="information">
                                    <tr>
                                        <td>${information.informationTitle}</td>
                                        <td>${information.informationAuthor}</td>
                                        <td>${information.informationCreateUser}</td>
                                        <td>
                                            <fmt:formatDate value="${information.informationPublishTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${information.informationCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                                        </td>
                                        <td>${information.informationStatu}</td>
                                        <td>
                                            <button class="delete-information-btn btn btn-danger pull-right" href="#modal-delete-block" data-toggle="modal" informationId="${information.informationId}" role="button">
                                                删除
                                            </button>
                                            <button class="update-information-btn btn btn-primary pull-right pre-update-button" href="#panel-3" data-toggle="tab" informationId="${information.informationId}" role="button">
                                                修改
                                            </button>
                                        </td>
                                    </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页信息 -->
                            <div class="row">
                                <!-- 分页文字 -->
                                <div class="col-sm-5" id="page_info_area"></div>
                                <!-- 分页条 -->
                                <div class="col-sm-5" id="page_nav_area"></div>
                            </div>
                            <!-- 被隐藏的模块 -->
                            <div class="modal fade" id="modal-delete-block" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                            <h4 class="modal-title " id="myModalLabel">
                                                确定要删除这条资讯吗
                                            </h4>
                                        </div>
                                        <div class="modal-footer">
                                            <button class="btn btn-danger" id="delete-information-button">删除</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- 标签2对应的模块  修改-->
                        <div class="tab-pane col-sm-12" id="panel-2">

                            <h2 class="sub-header">修改标题</h2>
                            <form class="form" onsubmit="return ">
                                <div class="form-group" id="rolename-change">
                                    <label for="insert" class="sr-only ">用户名</label>
                                    <input id="insert" type="text" maxlength="63" id="update-rolename" style="width:200px;" class="form-control center-block" placeholder="请输入需要修改的角色名" required="" autofocus=""  readonly="readonly">
                                    <span class="help-block center-block" style="text-align: center;" id="update-rolename-help"></span>
                                </div>
                                <div id="select-box-update">

                                </div>
                                <button class="btn btn-lg btn-primary btn-block center-block" id="submit-update-role" style="width:200px;">修改</button>
                            </form>


                        </div>
                        <!-- 标签3对应的模块  新增-->
                        <div class="tab-pane col-sm-12" id="panel-3">
                            <form id="text-form" class="form"  onsubmit="return submitEditor()" enctype="multipart/form-data">
                                <div class="row">
                                    <div class="col-sm-10 col-sm-offset-1">
                                        <h2 id="change-title" class="sub-header">新增资讯</h2>
                                        <div class="row">
                                            <div class="col-sm-12">
                                                <input id="editor-title" class="form-control" type="text" placeholder="请输入资讯标题" required="required">
                                                <br>
                                            </div>
                                        </div>
                                        <!--style给定宽度可以影响编辑器的最终宽度-->
                                        <div type="text/plain" id="myEditor" style="width:1100px;height:410px;">
                                            <p>这里我可以写一些输入提示</p>
                                        </div>
                                    </div>
                                </div>
                                <%--<div>--%>
                                    <%--<h3 id="focush2"></h3>--%>
                                <%--</div>--%>
                                <div class="row">
                                    <br>
                                    <div class="col-sm-3 col-sm-offset-1">
                                        <label for="createUser">采编人：</label>
                                        <input id="createUser" class="form-control" type="text" placeholder="请输入采编人姓名"  required="required">
                                    </div>
                                    <div class="col-sm-4">
                                        <label for="file">文件：</label>
                                        <input type="file" class="form-control" id="file">
                                    </div>
                                    <div class="col-sm-3">
                                        <label for="which-stage" class="">类型</label>
                                        <select class="form-control" id="statu-select" style="width:100%">
                                            <option value ="1">正常</option>
                                            <option value ="2">上架</option>
                                            <option value ="3">无效</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-11">
                                        <br>
                                        <input type="submit" class="btn btn-info pull-right" id="submit-editor">
                                    </div>
                                </div>
                            </form>
                            <%--<div class="clear"></div>--%>
                                <%--<div id="btns">--%>
                                <%--<table>--%>
                                <%--<tr>--%>
                                <%--<td>--%>
                                    <%--<button class="btn" unselected="on" onclick="getAllHtml()">获得整个html的内容</button>&nbsp;--%>
                            <%--<button class="btn" onclick="getContent()">获得内容</button>&nbsp;--%>
                                    <%--<button class="btn" onclick="setContent()">写入内容</button>&nbsp;--%>
                                    <%--<button class="btn" onclick="setContent(true)">追加内容</button>&nbsp;--%>
                                    <%--<button class="btn" onclick="getContentTxt()">获得纯文本</button>&nbsp;--%>
                                    <%--<button class="btn" onclick="getPlainTxt()">获得带格式的纯文本</button>&nbsp;--%>
                                    <%--<button class="btn" onclick="hasContent()">判断是否有内容</button>--%>
                                <%--</td>--%>
                                <%--</tr>--%>
                                <%--<tr>--%>
                                <%--<td>--%>
                                    <%--<button class="btn" onclick="setFocus()">编辑器获得焦点</button>&nbsp;--%>
                                    <%--<button class="btn" onmousedown="isFocus();return false;">编辑器是否获得焦点</button>&nbsp;--%>
                                    <%--<button class="btn" onclick="doBlur()">编辑器取消焦点</button>&nbsp;--%>
                                    <%--<button class="btn" onclick="insertHtml()">插入给定的内容</button>&nbsp;--%>
                                    <%--<button class="btn" onclick="getContentTxt()">获得纯文本</button>&nbsp;--%>
                                    <%--<button class="btn" id="enable" onclick="setEnabled()">可以编辑</button>&nbsp;--%>
                                    <%--<button class="btn" onclick="setDisabled()">不可编辑</button>--%>
                                <%--</td>--%>
                                <%--</tr>--%>
                                <%--<tr>--%>
                                <%--<td>--%>
                                    <%--<button class="btn" onclick="UM.getEditor('myEditor').setHide()">隐藏编辑器</button>&nbsp;--%>
                                    <%--<button class="btn" onclick="UM.getEditor('myEditor').setShow()">显示编辑器</button>&nbsp;--%>
                                    <%--<button class="btn" onclick="UM.getEditor('myEditor').setHeight(300)">设置编辑器的高度为300</button>&nbsp;--%>
                                    <%--<button class="btn" onclick="UM.getEditor('myEditor').setWidth(1200)">设置编辑器的宽度为1200</button>--%>
                                <%--</td>--%>
                                <%--</tr>--%>

                                <%--</table>--%>
                                <%--</div>--%>
                                <%--<table>--%>
                                <%--<tr>--%>
                                <%--<td>--%>
                                    <%--<button class="btn" onclick="createEditor()"/>创建编辑器</button>--%>
                                    <%--<button class="btn" onclick="deleteEditor()"/>删除编辑器</button>--%>
                                <%--</td>--%>
                                <%--</tr>--%>
                                <%--</table>--%>
                        </div>
                        <!-- 标签4对应的模块 -->
                        <div class="tab-pane col-sm-5 col-sm-offset-3" id="panel-4">
                            <h2 class="sub-header">删除标题</h2>
                            <form class="form" onsubmit="return false">
                                <div class="form-group" id="role-delete" style="margin-top:50px">
                                    <label for="delete-information-input" class="sr-only">角色名</label>
                                    <input type="text" maxlength="63" id="delete-information-input" class="form-control" placeholder="输入需要删除的角色名" required=""  readonly="readonly">
                                    <span class="help-block" id="delete-user-help"></span>
                                </div>
                                <!-- 遮罩窗体确定是否操作 -->
                                <!-- 触发按钮 -->
                                <button id="modal-information-delete-a" href="#modal-delete-block" role="button" class="btn btn-lg btn-primary btn-block" data-toggle="modal" style="margin-top:50px">
                                    删除
                                </button>
                                <%--<!-- 被隐藏的模块 -->--%>
                                <%--<div class="modal fade" id="modal-delete-block" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">--%>
                                    <%--<div class="modal-dialog">--%>
                                        <%--<div class="modal-content">--%>
                                            <%--<div class="modal-header">--%>
                                                <%--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>--%>
                                                <%--<h4 class="modal-title " id="myModalLabel">--%>
                                                    <%--确定要删除这条资讯吗--%>
                                                <%--</h4>--%>
                                            <%--</div>--%>
                                            <%--<div class="modal-footer">--%>
                                                <%--<button class="btn btn-danger" id="delete-role-button">删除</button>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            </form>
                        </div>

                    </div>
                </div>
            </div>

        </div>
    </div>
</div>


<script>window.jQuery || document.write('<script src="./static/teach/js/jquery.min.js"><\/script>')</script>
<script src="./static/teach/js/bootstrap.min.js"></script>
<script src="./static/teach/js/holder.min.js"></script>
<script src="./static/teach/js/ie10-viewport-bug-workaround.js"></script>
<script src="./static/teach/js/general.js"></script>
<script src="./static/information/js/information.js"></script>
<script>
    var pageData = <%=request.getAttribute("pageInfo")%>
        pageHelper(pageData);
        console.log(pageData)
</script>
<script type="text/javascript">
    //实例化编辑器
    var um = UM.getEditor('myEditor');
    um.addListener('blur',function(){
        $('#focush2').html('编辑器失去焦点了')
    });
    um.addListener('focus',function(){
        $('#focush2').html('')
    });
    //按钮的操作
    function insertHtml() {
        var value = prompt('插入html代码', '');
        um.execCommand('insertHtml', value)
    }
    function isFocus(){
        alert(um.isFocus())
    }
    function doBlur(){
        um.blur()
    }
    function createEditor() {
        enableBtn();
        um = UM.getEditor('myEditor');
    }
    function getAllHtml() {
        alert(UM.getEditor('myEditor').getAllHtml())
    }
    function getContent() {
        var arr = [];
        arr.push("使用editor.getContent()方法可以获得编辑器的内容");
        arr.push("内容为：");
        arr.push(UM.getEditor('myEditor').getContent());
        alert(arr.join("\n"));
    }
    function getPlainTxt() {
        var arr = [];
        arr.push("使用editor.getPlainTxt()方法可以获得编辑器的带格式的纯文本内容");
        arr.push("内容为：");
        arr.push(UM.getEditor('myEditor').getPlainTxt());
        alert(arr.join('\n'))
    }
    function setContent(isAppendTo) {
        var arr = [];
        arr.push("使用editor.setContent('欢迎使用umeditor')方法可以设置编辑器的内容");
        UM.getEditor('myEditor').setContent('欢迎使用umeditor', isAppendTo);
        alert(arr.join("\n"));
    }
    function setDisabled() {
        UM.getEditor('myEditor').setDisabled('fullscreen');
        disableBtn("enable");
    }

    function setEnabled() {
        UM.getEditor('myEditor').setEnabled();
        enableBtn();
    }

    function getText() {
        //当你点击按钮时编辑区域已经失去了焦点，如果直接用getText将不会得到内容，所以要在选回来，然后取得内容
        var range = UM.getEditor('myEditor').selection.getRange();
        range.select();
        var txt = UM.getEditor('myEditor').selection.getText();
        alert(txt)
    }

    function getContentTxt() {
        var arr = [];
        arr.push("使用editor.getContentTxt()方法可以获得编辑器的纯文本内容");
        arr.push("编辑器的纯文本内容为：");
        arr.push(UM.getEditor('myEditor').getContentTxt());
        alert(arr.join("\n"));
    }
    function hasContent() {
        var arr = [];
        arr.push("使用editor.hasContents()方法判断编辑器里是否有内容");
        arr.push("判断结果为：");
        arr.push(UM.getEditor('myEditor').hasContents());
        alert(arr.join("\n"));
    }
    function setFocus() {
        UM.getEditor('myEditor').focus();
    }
    function deleteEditor() {
        disableBtn();
        UM.getEditor('myEditor').destroy();
    }
    function disableBtn(str) {
        var div = document.getElementById('btns');
        var btns = domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            if (btn.id == str) {
                domUtils.removeAttributes(btn, ["disabled"]);
            } else {
                btn.setAttribute("disabled", "true");
            }
        }
    }
    function enableBtn() {
        var div = document.getElementById('btns');
        var btns = domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            domUtils.removeAttributes(btn, ["disabled"]);
        }
    }
</script>
</body>
</html>
