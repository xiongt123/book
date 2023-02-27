<%--
  Created by IntelliJ IDEA.
  User: Xiongtkong
  Date: 2022/8/2
  Time: 17:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script src="static/script/jquery-1.7.2.js"></script>
</head>
<body>

    <%--页码输出的开始--%>
    <c:choose>
        <%--情况1：如果总页码小于等于5 的情况，页码的范围是：1-总页码--%>
        <c:when test="${ requestScope.page.pageTotal <= 5 }">
            <c:set var="begin" value="1" scope="page"/><%--scope可以省略--%>
            <c:set var="end" value="${requestScope.page.pageTotal}"/>
        </c:when>
        <%--情况2：总页码大于5 的情况--%>
        <c:when test="${requestScope.page.pageTotal > 5}">
            <c:choose>
                <%--小情况1：当前页码为前面3 个：1，2，3 的情况，页码范围是：1-5.--%>
                <c:when test="${requestScope.page.pageNo <= 3}">
                    <c:set var="begin" value="1"/>
                    <c:set var="end" value="5"/>
                </c:when>
                <%--小情况2：当前页码为最后3 个，8，9，10，页码范围是：总页码减4 - 总页码--%>
                <c:when test="${requestScope.page.pageNo > requestScope.page.pageTotal-3}">
                    <c:set var="begin" value="${requestScope.page.pageTotal-4}"/>
                    <c:set var="end" value="${requestScope.page.pageTotal}"/>
                </c:when>
                <%--小情况3：4，5，6，7，页码范围是：当前页码减2 - 当前页码加2--%>
                <c:otherwise>
                    <c:set var="begin" value="${requestScope.page.pageNo-2}"/>
                    <c:set var="end" value="${requestScope.page.pageNo+2}"/>
                </c:otherwise>
            </c:choose>
        </c:when>
    </c:choose>
    <%--页码输出的结束--%>
    <div id="page_nav" style="margin-top:20px;">
        <%--大于首页，才显示--%>
        <c:if test="${requestScope.page.pageNo > 1}">
            <a href="${page.url}pageNo=1">首页</a>
            <a href="${page.url}pageNo=${requestScope.page.pageNo-1}">上一页</a>
        </c:if>


        <%--  【${ requestScope.page.pageNo }】--%>
        <c:forEach begin="${begin}" end="${end}" var="i">
            <c:if test="${i == requestScope.page.pageNo}">
                【${i}】
            </c:if>
            <c:if test="${i != requestScope.page.pageNo}">
                <a href="${page.url}pageNo=${i}">${i}</a>
            </c:if>
        </c:forEach>


        <%-- 如果已经 是最后一页，则不显示下一页，末页 --%>
        <c:if test="${requestScope.page.pageNo < requestScope.page.pageTotal}">
            <a href="${page.url}pageNo=${requestScope.page.pageNo+1}">下一页</a>
            <a href="${page.url}pageNo=${requestScope.page.pageTotal}">末页</a>
        </c:if>

        共${ requestScope.page.pageTotal }页，
        ${ requestScope.page.pageTotalCount }条记录到第
        <input value="${page.pageNo}" name="pn" id="pn_input"/>页
        <input id="searchPageBtn" type="button" value="确定"/>
    </div>

</body>
</html>
<script>
    $(function () {
        // 跳到指定的页码
        $("#searchPageBtn").click(function () {
            var pageNo = $("#pn_input").val();
            <%--var pageTotal = ${requestScope.page.pageTotal};--%>
            <%--alert(pageTotal);--%>
            // javaScript 语言中提供了一个location 地址栏对象
            // 它有一个属性叫href.它可以获取浏览器地址栏中的地址
            // href 属性可读，可写
            location.href = "${page.url}pageNo=" + pageNo;

        });
    });
</script>