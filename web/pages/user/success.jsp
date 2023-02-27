<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>会员注册页面</title>
    <%--<base href="http://localhost:8080/book/">--%>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <link type="text/css" rel="stylesheet" href="static/css/style.css">
    <style type="text/css">
        h1 {
            text-align: center;
            margin-top: 200px;
        }

        h1 a {
            color: red;
        }
    </style>
</head>
<body>
<div id="header">
    <a href="index.jsp">
        <img class="logo_img" alt="" src="static/img/logo.gif" width="230px" height="80px">
    </a>
    <div>
        <%@ include file="/pages/common/menu.jsp" %>
        <a href="index.jsp">返回</a>
    </div>
</div>

<div id="main">
    <h1>${msg}<a href="index.jsp">转到主页</a></h1>
</div>

<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>