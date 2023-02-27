<%--
  Created by IntelliJ IDEA.
  User: Xiongtkong
  Date: 2022/8/2
  Time: 18:57
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<meta charset="UTF-8">
	<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
	<title>书城首页</title>
</head>
<body>
<jsp:forward page="BookServlet?action=searchPage"></jsp:forward>
</body>
</html>