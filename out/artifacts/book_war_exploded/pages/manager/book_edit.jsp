<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>编辑图书</title>
	<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
	<link type="text/css" rel="stylesheet" href="static/css/style.css" >
	<script src="static/script/jquery-1.7.2.js"></script>
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

		label{
			position: relative;
		}
		#file_input{
			position: absolute;
			left: 0;
			top: 0;
			opacity: 0;
		}
		#btn{
			margin-right: 5px;
		}
		#text {
			color: red;
		}


	</style>
</head>
<body>
		<% request.setCharacterEncoding("utf-8"); %>
		<jsp:include page="/pages/common/header.jsp">
			<jsp:param name="msg" value="修改图书"/>
		</jsp:include>

		
		<div id="main">
			<%--enctype : 当上传文件的时候需要修改特殊的值multipart/form-data,否则默认--%>
			<form action="BookServlet?action=update&pageNo=${pageNo}" enctype="multipart/form-data" method="post" onsubmit="return mysub();">
				<table>
<%--					<input type="hidden" value="update" name="action"/>--%>
					<%--主键id 只是数据库中的一个标识 , 对给用户展示 没有任何的好处 --%>
					<input name="id" type="text" value="${book.id}" hidden="hidden"/>
					<input name="pageNo" type="text" value="${pageNo}" hidden="hidden"/>
					<%--
					disabled="disabled"
						placeholder 提示字
						disabled 禁用 不能修改,不能提交
						readonly 只读 不能修改,可以提交
					--%>
					<tr>
						<td>名称</td>
						<td><input name="name" type="text" value="${book.name}"/></td>
					</tr>
					<tr>
						<td>价格</td>
						<td><input name="price" id="price" type="text" value="${book.price}"/></td>
					</tr>
					<tr>
						<td>作者</td>
						<td><input name="author" id="author" type="text" value="${book.author}"/></td>
					</tr>
					<tr>
						<td>销量</td>
						<td><input name="sales" id="sales" type="text" value="${book.sales}"/></td>
					</tr>
					<tr>
						<td>库存</td>
						<td><input name="stock" id="stock" type="text" value="${book.stock}"/></td>
					</tr>
					<tr>
						<td>封面</td>
						<td>
							<label for="file_input">
								<input type="button" id="btn" value="选择文件"><span id="text">${book.imgPath}</span>
								<input type="file" id="file_input" name="imgPath" value="${book.imgPath}" onchange="show_image()">
							</label>
							<img src="${book.imgPath}" id="show_img" width="50px" height="50px">
						</td>
					</tr>
					<tr>
						<td colspan="2"><input type="submit" value="提交" ></td>
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

	$("#file_input").change(function() {
		$("#text").html($("#file_input").val());
	})

	function show_image() {
		//抓取到上传图片的input标签的信息
		file_input = document.getElementById("file_input");
		//抓取到需要展示预览图的img标签信息
		show_img = document.getElementById("show_img");
		//回去预览图的src属性信息
		show_img.src = window.URL.createObjectURL(file_input.files[0]);
		//改变style属性中block的值
		//show_img.style.display = 'block';1

		//并且修改图片路径
		text=document.getElementById("text");
		text.innerHTML= show_img.src;
	}

	function mysub(){
		var priceobj= document.getElementById("price").value;
		console.log(priceobj);
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