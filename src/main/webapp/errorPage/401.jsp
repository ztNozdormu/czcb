<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--下面是常量定义 --%>
<c:set var="contextPath" value="${pageContext.servletContext.contextPath}"/><%-- 写静态资源时使用 --%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>401</title>
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="iE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/error.css">
</head>
<body>
<div class="info">
	<div class="info_middle">
		<div class="info_left">
			<img src="${contextPath}/images/warning.png">
		</div>
		<div class="info_right">
			<p><b>401,对不起，您的请求被拒绝了！</b></p>
			<span>原因可能是：</span>
			<ul>
				<li>请检查您输入的地址是否正确</li>
				<li>请更换一个有权限的账户访问</li>
				<li>如果您确定有权限访问该资源，请与管理员联系</li>
			</ul>
		</div>
		<p style="clear:both"></p>
	</div>
	<!-- btn -->
	<div class="info_btn">
		<a href="javascript:;" onclick="history.go(-1)">&lt; 上一页</a>
	</div>
</div>
<script type="text/javascript" src="${contextPath}/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript">
$(function(){
	$(".info").css({
		position:"absolute",
		top:($(document).height() - $(".info").height()) / 3,
		left:($(document).width() - $(".info").width()) / 2
	});
})
</script>
</body>
</html>