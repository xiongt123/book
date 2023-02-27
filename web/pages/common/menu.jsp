<%--
  Created by IntelliJ IDEA.
  User: Xiongtkong
  Date: 2022/8/6
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <c:if test="${empty user}">
    <a href="pages/user/login.jsp">登录</a> |
    <a href="pages/user/regist.jsp">注册</a> &nbsp;&nbsp;
  </c:if>
  <c:if test="${not empty user}">
    <span>欢迎<span class="um_span">${user.username},ip号:${user.id}</span>光临网上书城</span>
    <a href="OrderServlet?action=findAllOrderByUser">我的订单</a>
    <a href="UserServlet?action=logout">注销</a>&nbsp;&nbsp;
  </c:if>
</body>
</html>
