<%--
  Created by IntelliJ IDEA.
  User: 王鸿175
  Date: 2018/7/18
  Time: 10:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试新增广告</title>
    <style>
        input{
            margin-top:10px;
            width: 300px;
            height: 30px;
        }
    </style>
</head>
<body>

    <form action="saveAd.do" method="POST" enctype="multipart/form-data">
        <input type="text" placeholder="标题" name="adTitle" required><br>
        <input type="text" placeholder="创建人" name="adCreateUser" required><br>
        <input type="text" placeholder="排序" name="adOrder" required><br>
        <input type="text" placeholder="url" name="adLinkUrl" required><br>
        <input type="file"   accept="image/*" name="file" required><br>
        <input type="submit" value="上传"/>
    </form>

</body>
</html>
