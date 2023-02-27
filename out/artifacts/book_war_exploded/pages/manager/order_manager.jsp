<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>订单管理</title>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <link type="text/css" rel="stylesheet" href="static/css/style.css">
</head>
<body>

<% request.setCharacterEncoding("utf-8"); %>
<jsp:include page="/pages/common/header.jsp">
    <jsp:param name="msg" value="订单管理系统"/>
</jsp:include>


<div id="main">
    <table>
        <tr>
            <td>订单编号</td>
            <td>日期</td>
            <td>金额</td>
            <td>详情</td>
            <td>发货</td>
        </tr>
        <c:if test="${empty requestScope.page}">
            <%-- 如果商品订单空的情况 --%>
            <tr>
                <td colspan="5">
                    <a href="index.jsp">亲,当前商品订单为空!快跟小伙伴们去宣传商品去吧!!!</a>
                </td>
            </tr>
        </c:if>
        <c:if test="${not empty requestScope.page}">
            <%-- 如果商品订单非空的情况 --%>
            <c:forEach items="${page.items}" var="order">
                <tr>
                    <td>${order.orderId}</td>
                    <td>${order.createTime}</td>
                    <td>${order.price}</td>
                    <td><a href="OrderItemServlet?action=findAllOrderByOrderid&orderId=${order.orderId}">查看详情</a></td>
                    <c:choose>
                        <c:when test="${order.status==0}">
                            <td><a href="OrderManagerServlet?action=deliver&orderId=${order.orderId}&pageNo=${pageNo}">点击发货</a>
                        </c:when>
                        <c:when test="${order.status==1}">
                            <td>已发货</td>
                        </c:when>
                        <c:when test="${order.status==2}">
                            <td>等待收货</td>
                        </c:when>
                    </c:choose>
                </tr>
            </c:forEach>
        </c:if>
    </table>
</div>
<div style="margin-top: -80px;">
    <%-- 动态包含 分页跳转 --%>
    <jsp:include page="/pages/common/page.jsp"></jsp:include>
</div>
<%@include file="/pages/common/bottom.jsp" %>
</body>
</html>