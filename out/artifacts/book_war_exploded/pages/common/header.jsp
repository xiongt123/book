<%--
  Created by IntelliJ IDEA.
  User: Xiongtkong
  Date: 2022/7/28
  Time: 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
</head>
<body>

    <div id="header">
      <a href="index.jsp">
        <img class="logo_img" alt="" src="static/img/logo.gif" width="230px" height="80px">
</a>      <span class="wel_word">${param.msg}</span>
      <div>
        <a href="BookServlet?action=page">图书管理</a>
        <a href="OrderManagerServlet?action=findAllOrder">订单管理</a><%--pages/manager/order_manager.jsp--%>
        <a href="index.jsp">返回商城</a>
      </div>
    </div>
</body>
</html>
