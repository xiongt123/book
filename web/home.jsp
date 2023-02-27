<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>书城首页</title>
    <link type="text/css" rel="stylesheet" href="static/css/style.css">
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <script src="static/script/jquery-1.7.2.js"></script>
    <style>
        ::-webkit-scrollbar {
            display: none;
        }
    </style>
</head>
<body>
<c:choose>
    <%-- session里面的user对象为null 并且 cookie中的用户与密码不会null, 就自动登录 --%>
    <c:when test="${not empty cookie.username && not empty cookie.password && empty sessionScope.user}">
        <jsp:forward page="/UserServlet">
            <jsp:param name="action" value="login"/>
            <jsp:param name="username" value="${cookie.username.value}"/>
            <jsp:param name="password" value="${cookie.password.value}"/>
            <jsp:param name="url" value="index.jsp"/>
        </jsp:forward>
    </c:when>
</c:choose>


<div id="header">
    <img class="logo_img" alt="" src="static/img/logo.gif" width="230px" height="80px">
    <span class="wel_word">网上书城</span>
    <div>
        <%@ include file="/pages/common/menu.jsp" %>
        <a href="pages/cart/cart.jsp">购物车</a>
        <a href="pages/user/admin_login.jsp">后台管理</a>
    </div>
</div>


<div id="main" style="height: 500px;">
    <div id="book">
        <div class="book_cond" style="margin-left: 350px;">
            <form action="BookServlet" method="get">
                <input type="hidden" name="action" value="searchPage">
                书名：<input id="name" type="text" name="name" value="${name}">
                作者：<input id="author" type="text" name="author" value="${author}">
                价格：<input id="min" type="text" name="min" value="${min eq 0?"":min}"> 元 -
                <input id="max" type="text" name="max" value="${max eq 0?"":max}"> 元
                <input type="submit" value="查询"/>
            </form>
        </div>


        <%-- 购物车数据回显 --%>
        <%--<div style="text-align: center">
            <span>您的购物车中有3件商品</span>
            <div>
                您刚刚将<span style="color: red">时间简史</span>加入到了购物车中
            </div>
        </div>--%>
        <div style="text-align: center">
            <c:if test="${empty sessionScope.cart.items}">
                <%--购物车为空的输出--%>
                <span> </span>
                <div>
                    <span style="color: red">当前购物车为空</span>
                </div>
            </c:if>
            <c:if test="${not empty sessionScope.cart.items}">
                <%--购物车非空的输出--%>
                <span>您的购物车中有 ${sessionScope.cart.totalCount} 件商品</span>
                <div>
                    您刚刚将<span style="color: red">${sessionScope.lastName}</span>加入到了购物车中
                </div>
            </c:if>
        </div>


        <c:choose>
            <%-- 如果没有查询的结果 就显示没有找到数据 ； 如果有 循环显示当页书 --%>
            <c:when test="${empty page.items}">
                <h1 style="text-align:center; color:red;">没有找到数据</h1>
            </c:when>
            <c:otherwise>
                <c:forEach items="${page.items}" var="book">
                    <div class="b_list" style="margin-left: 25px">
                        <div class="img_div">
                            <img class="book_img" alt="" src="${book.imgPath}"/>
                        </div>
                        <div class="book_info" style="padding-left: 50px;">
                            <div class="book_name" style="padding-left: 0px; !important;">
                                <span class="sp1">书名:</span>
                                <span class="sp2">${book.name}</span>
                            </div>
                            <div class="book_author">
                                <span class="sp1">作者:</span>
                                <span class="sp2">${book.author}</span>
                            </div>
                            <div class="book_price">
                                <span class="sp1">价格:</span>
                                <span class="sp2">${book.price}</span>
                            </div>
                            <div class="book_sales">
                                <span class="sp1">销量:</span>
                                <span class="sp2">${book.sales}</span>
                            </div>
                            <div class="book_amount">
                                <span class="sp1">库存:</span>
                                <span class="sp2">${book.stock}</span>
                            </div>
                            <div class="book_add">
                                <button bookId="${book.id}" bookName="${book.name}" userId="${user.id}" class="addToCart">加入购物车</button>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
    <div style="margin-top: -50px;">
        <%-- 动态包含 分页跳转 --%>
        <jsp:include page="/pages/common/page.jsp"></jsp:include>
    </div>
</div>
<div style="margin-top: 20px;">
    <%@include file="/pages/common/bottom.jsp" %>
</div>

<%--<form action="aaa.jsp?action=peijie&name=xiaoming" method="post">
    <input type="text" name="pass">
    <input type="text" name="email">
    <input type="submit" value="提交">
</form>--%>

</body>
</html>
<Script type="text/javascript">
    $(function () {
        // 给加入购物车按钮绑定单击事件
        $("button.addToCart").click(function () {
            /**
             *    在事件响应的function 函数中，有一个this 对象，这个this 对象，是当前正在响应事件的dom 对象
             *    @type {jQuery}
             */
            var userId = $(this).attr("userId");
            if(userId !=""){
                var bookId = $(this).attr("bookId");
                var bookName = $(this).attr("bookName");
                alert("<<"+bookName+">>添加到购物车");
                window.location.href = "CartServlet?action=addItem&id="+bookId;
            }else{
                alert("添加到购物车 , 请登录账号 !");
                window.location.href = "pages/user/login.jsp";
            }

        });
    });
</Script>
