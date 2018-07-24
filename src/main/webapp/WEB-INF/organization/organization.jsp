<%@ page import="com.bluestar.advertisement.vo.AdVo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
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
    <link rel="stylesheet" href="./static/organization/css/fishtree.css">
    <style>
        .list_tr td {
            height: 50px;
            line-height: 50px !important;
        }
    </style>
</head>

<body>
<!-- 保存部门模态框 -->
<div class="modal fade" id="saveModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">新增部门</h4>
            </div>
            <div class="modal-body">
                <form method="POST">
                    <div class="form-group">
                        <label for="saveDeptCode">部门编号</label>
                        <input type="text" class="form-control" id="saveDeptCode" placeholder="部门编号" name="deptCode"
                               required>
                    </div>
                    <div class="form-group">
                        <label for="saveDeptName">部门名称</label>
                        <input type="text" class="form-control" id="saveDeptName" placeholder="部门名称" name="deptName"
                               required>
                    </div>
                    <div class="form-group">
                        <label for="saveDeptRemark">部门说明</label>
                        <textarea class="form-control" rows="3" id="saveDeptRemark" name="deptRemark"></textarea>
                    </div>
                    <input type="hidden" id="saveDeptPCode" name="deptPCode">
                    <input type="hidden" id="saveDeptLevel" name="deptLevel">
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="doSaveDept">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 修改部门模态框 -->
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">修改部门</h4>
            </div>
            <div class="modal-body">
                <form method="POST">
                    <div class="form-group">
                        <label for="saveDeptCode">部门编号</label>
                        <input type="text" class="form-control" id="updateDeptCode" placeholder="部门编号" name="deptCode">
                    </div>
                    <div class="form-group">
                        <label for="saveDeptName">部门名称</label>
                        <input type="text" class="form-control" id="updateDeptName" placeholder="部门名称" name="deptName">
                    </div>
                    <div class="form-group">
                        <label for="saveDeptRemark">部门说明</label>
                        <textarea class="form-control" rows="3" id="updateDeptRemark" name="deptRemark"></textarea>
                    </div>
                    <input type="hidden" id="updateDeptId" name="deptId">
                    <input type="hidden" id="updateDeptPCode" name="deptPCode">
                    <input type="hidden" id="updateDeptLevel" name="deptLevel">
                    <input type="hidden" id="updateDeptOrder" name="deptOrder">
                    <input type="hidden" id="updateOldDeptCode" name="oldDeptCode">
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="doUpdateDept">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 列出用户模态框 -->
<div class="modal fade" id="listUserModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">修改部门</h4>
            </div>
            <div class="modal-body">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>用户名</th>
                        <th>名字</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody id="listUsersTable" data-current-page="1">
                    <%--<tr>
                        <td>stu</td>
                        <td>凤佳</td>
                        <td>
                            <button class="btn btn-primary pull-right">添加</button>
                        </td>
                    </tr>--%>
                    </tbody>
                </table>
                <p class="text-center">
                    <a href="javascript:;" id="listMoreUsers">显示更多用户...</a>
                </p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭此窗口</button>
            </div>
        </div>
    </div>
</div>

<!-- 顶部导航栏 -->
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
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
                <li class="text-center bord"><a href="#"><strong>功能一览</strong> <span
                        class="sr-only">(current)</span></a></li>
            </div>
            <ul class="nav nav-sidebar" id="nav-list">

            </ul>
        </div>

        <!-- 右侧模块 -->
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1 class="page-header">组织管理</h1>
            <div class="row">
                <!-- 左侧树状图 -->
                <div class="col-md-6">
                    <h3 class="page-header">
                        组织概览
                        <button class="btn btn-danger btn-sm navbar-right" id="deleteDept"
                                style="margin-right: 2px"><span
                                class="glyphicon glyphicon-trash"></span> 删除
                        </button>
                        <button class="btn btn-primary btn-sm navbar-right" id="updateDept"
                                style="margin-right: 2px" data-toggle="modal" data-target="#updateModal"><span
                                class="glyphicon glyphicon-pencil"></span> 修改
                        </button>
                        <button class="btn btn-primary btn-sm navbar-right" id="saveDept"
                                style="margin-right: 2px" data-toggle="modal" data-target="#saveModal"><span
                                class="glyphicon glyphicon-plus"></span> 新增
                        </button>
                    </h3>
                    <div class="container-fluid">
                        <div id="tree">
                            <%--<p data-deptLevel="1" class="fish-open" data-deptId="001">一级</p>
                            <p data-deptLevel="2" class="001 fish-close" data-deptId="001-001">二级</p>
                            <p data-deptLevel="2" class="001 fish-open" data-deptId="001-002">二级</p>
                            <p data-deptLevel="3" class="001-002 fish-close" data-deptId="001-002-001">三级</p>
                            <p data-deptLevel="3" class="001-002 fish-close" data-deptId="001-002-002">三级</p>
                            <p data-deptLevel="3" class="001-002 fish-close" data-deptId="001-002-003">三级</p>
                            <p data-deptLevel="2" class="001 fish-close" data-deptId="001-003">二级</p>
                            <p data-deptLevel="1" class="fish-close" data-deptId="002">一级</p>--%>
                        </div>
                    </div>
                </div>
                <!-- 右侧详情 -->
                <div class="col-md-6">
                    <h3 class="page-header" id="deptDetail">（未选中部门）</h3>
                    <div class="container-fluid">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>用户名</th>
                                <th>名字</th>
                                <th>
                                    <button class="btn btn-primary btn-sm pull-right" data-toggle="modal"
                                            data-target="#listUserModal" id="saveUserDept"><span
                                            class="glyphicon glyphicon-plus"></span> 添加用户
                                    </button>
                                </th>
                            </tr>
                            </thead>
                            <tbody id="userDeptTable">
                            <%--<tr>
                                <td>fish</td>
                                <td>叶子</td>
                                <td>
                                    <button class="btn btn-primary btn-xs navbar-right" style="margin-right: 2px"><span
                                            class="glyphicon glyphicon-pencil"></span> 更换部门
                                    </button>
                                    <button class="btn btn-danger btn-xs navbar-right" style="margin-right: 2px"><span
                                            class="glyphicon glyphicon-trash"></span> 移出部门
                                    </button>
                                </td>
                            </tr>--%>
                            </tbody>
                        </table>
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
<%--<script src="./static/organization/js/fishtree.js"></script>--%>
<script src="./static/teach/js/general.js"></script>
<script src=./static/organization/js/organization.js></script>
</body>
</html>
