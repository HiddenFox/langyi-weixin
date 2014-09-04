<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>绑定成功</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet" media="all">
</head>
<body>
 	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<h3>绑定成功</h3>
				<p class="lead">恭喜您！已成功将此微信号绑定到逸卡：${account.cardNumber }</p>
				<p>当前积分值：${account.credit }</p>
			</div>
		</div>
	</div>
	<script src="<c:url value="/js/jquery-1.8.3.min.js" />"></script>
	<script src="<c:url value="/js/bootstrap.min.js" />"></script>
</body>
</html>