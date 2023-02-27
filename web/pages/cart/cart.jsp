<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>购物车</title>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <link type="text/css" rel="stylesheet" href="static/css/style.css">
    <script src="static/script/jquery-1.7.2.js"></script>
</head>
<body>

<div id="header">
    <img class="logo_img" alt="" src="static/img/logo.gif" width="230px" height="80px">
    <span class="wel_word">购物车</span>
    <div>
        <%@ include file="/pages/common/menu.jsp" %>
        <a href="index.jsp">返回</a>
    </div>
</div>


<div id="main">
    <table>
        <tr>
            <td>商品名称</td>
            <td>数量</td>
            <td>单价</td>
            <td>金额</td>
            <td>总金额</td>
            <td>操作</td>
        </tr>
        <c:if test="${empty sessionScope.cart.items}">
            <%-- 如果购物车空的情况 --%>
            <tr>
                <td colspan="5">
                    <a href="index.jsp">亲,当前购物车为空!快跟小伙伴们去浏览商品去吧!!!</a>
                </td>
            </tr>
        </c:if>
        <c:if test="${not empty sessionScope.cart.items}">
            <%-- 如果购物车非空的情况 --%>
            <c:forEach items="${sessionScope.cart.items}" var="entry">
                <tr>
                    <td>${entry.value.name}</td>
                        <td>
                            <input class="updateCount" style="width:80px;"
                                bookId="${entry.value.id}" type="number" min="1" max="10" value="${entry.value.count}">
                        </td>
                    <td>${entry.value.count}</td>
                    <td>${entry.value.price}</td>
                    <td>${entry.value.totalPrice}</td>
                    <td><a href="CartServlet?action=deleteItem&id=${entry.value.id}" calass="deleteItem">删除</a></td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
    <c:if test="${not empty sessionScope.cart.items}">
        <div class="cart_info">
            <span class="cart_span">购物车中共有<span class="b_count">${sessionScope.cart.totalCount}</span>件商品</span>
            <span class="cart_span">总金额<span class="b_price">${sessionScope.cart.totalPrice}</span>元</span>
            <span class="cart_span"><a href="CartServlet?action=clear" id="clearCart">清空购物车</a></span>
            <span class="cart_span"><a href="OrderServlet?action=createOrder">去结账</a></span>
        </div>
    </c:if>
</div>

<%-- 使用静态包含 效率略快 ,动态包含当然也是可以的 ;--%>
<%-- 这里静态包含找不到路径--%>
<%@include file="/pages/common/bottom.jsp" %>
<%--<jsp:forward page="/pages/common/bottom.jsp"></jsp:forward>--%>

</body>
</html>
<script type="text/javascript">
    $(function () {
        // 给【删除】绑定单击事件
        $("a.deleteItem").click(function () {
            return confirm("你确定要删除【" + $(this).parent().parent().find("td:first").text() + "】吗?")
        });
    });

    // 给清空购物车绑定单击事件
    $("#clearCart").click(function () {
        return confirm("你确定要清空购物车吗?");
    })


    // 给输入框绑定 onchange 内容发生改变事件
    $(".updateCount").change(function () {
        // 获取商品名称
        var name = $(this).parent().parent().find("td:first").text();
        var id = $(this).attr('bookId');
        // 获取商品数量
        var count = this.value;
        if ( confirm("你确定要将【" + name + "】商品修改数量为：" + count + " 吗?") ) {
            //发起请求。给服务器保存修改
            location.href = "CartServlet?action=updateCount&count="+count+"&id="+id;
        } else {
            // defaultValue 属性是表单项Dom 对象的属性。它表示默认的value 属性值。
            this.value = this.defaultValue;
        }
    });
</script>
