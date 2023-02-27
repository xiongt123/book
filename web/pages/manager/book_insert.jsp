<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>编辑图书</title>
	<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
<link type="text/css" rel="stylesheet" href="static/css/style.css" >
<style type="text/css">
	h1 {
		text-align: center;
		margin-top: 200px;
	}
	
	h1 a {
		color:red;
	}
	
	input {
		text-align: center;
	}
</style>
</head>
<body>
		<% request.setCharacterEncoding("utf-8"); %>
		<jsp:include page="/pages/common/header.jsp">
			<jsp:param name="msg" value="添加图书"/>
		</jsp:include>

		
		<div id="main">
			<form action="BookServlet?action=insert" enctype="multipart/form-data" method="post" onsubmit="return mysub();">
				<table>
<%--					<input type="hidden" value="insert" name="action"/>--%>
					<tr>
						<td>名称</td>
						<td><input name="name" type="text" value="${book.name}"/></td>
					</tr>
					<tr>
						<td>价格</td>
						<td><input name="price" type="text" id="price"/></td>
					</tr>
					<tr>
						<td>作者</td>
						<td><input name="author" type="text"/></td>
					</tr>
					<tr>
						<td>销量</td>
						<td><input name="sales" type="text" id="sales"/></td>
					</tr>
					<tr>
						<td>库存</td>
						<td><input name="stock" type="text" id="stock"/></td>
					</tr>
					<tr>
						<td>封面</td>
						<td>
							<input name="imgPath" type="file" id="file_input" onchange="show_image()"/>
							<img src="${book.imgPath}" id="show_img" width="50px" height="50px">
						</td>
					</tr>
					<tr>
						<td colspan="2"><input type="submit" value="提交"></td>
					</tr>
				</table>
			</form>

		</div>
		
		<div id="bottom">
			<span>
				网上书城.Copyright &copy;2020
			</span>
		</div>
</body>
</html>
<script>
	function show_image() {
		//抓取到上传图片的input标签的信息
		file_input = document.getElementById("file_input");
		//抓取到需要展示预览图的img标签信息
		show_img = document.getElementById("show_img");
		//回去预览图的src属性信息
		show_img.src = window.URL.createObjectURL(file_input.files[0]);
		//改变style属性中block的值
		//show_img.style.display = 'block';
	}

	function mysub(){
		var priceobj= document.getElementById("price").value;
		var salesobj= document.getElementById("sales").value;
		var stockobj= document.getElementById("stock").value;
		var rule1 = /^[1-9]\d*(\.\d{1,2})?$|(^0(\.\d{1,2})?$)/;
		var  rule2 = /^[+]{0,1}(\d+)$/;
		if (priceobj&& salesobj && stockobj) {
			if (!rule1.test(priceobj)) {
				alert("价格格式错误,应为正数");
			} else if (!rule2.test(salesobj)) {
				alert("销量格式错误,应为正整数");
			} else if (!rule2.test(stockobj)) {
				alert("库存格式错误,应为正整数");
			} else {
				return true;
			}
		}
		return false;
	}

</script>