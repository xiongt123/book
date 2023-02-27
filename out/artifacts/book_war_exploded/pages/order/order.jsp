<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的订单</title>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <link type="text/css" rel="stylesheet" href="static/css/style.css">
    <style type="text/css">
        h1 {
            text-align: center;
            margin-top: 200px;
        }
    </style>
</head>
<body>

<div id="header">

    <a href="index.jsp">
        <img class="logo_img" alt="" src="static/img/logo.gif" width="230px" height="80px">
    </a>
    <span class="wel_word">我的订单</span>
    <div>
        <%@ include file="/pages/common/menu.jsp" %>
        <a href="index.jsp">返回</a>
    </div>
</div>

<div id="main">

    <table>
        <tr>
            <td>订单编号</td>
            <td>日期</td>
            <td>金额</td>
            <td>状态</td>
            <td>详情</td>
        </tr>

        <c:if test="${empty requestScope.page}">
            <%-- 如果商品订单空的情况 --%>
            <tr>
                <td colspan="5">
                    <a href="index.jsp">亲,当前商品订单为空!快跟小伙伴们去浏览商品去吧!!!</a>
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
                    <c:choose>
                        <c:when test="${order.status==0}">
                            <td>未发货</td>
                        </c:when>
                        <c:when test="${order.status==1}">
                            <td>已发货</td>
                        </c:when>
                        <c:when test="${order.status==2}">
                            <td>已签收</td>
                        </c:when>
                    </c:choose>
                    <td><a href="OrderItemServlet?action=findAllOrderByOrderid&orderId=${order.orderId}">查看详情</a></td>
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