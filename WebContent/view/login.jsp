<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>绑定逸卡</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet" media="all">
</head>
<body>
 	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<form:form modelAttribute="account" action="login" method="post">
					<fieldset>
    				<legend>绑定逸卡</legend>
    				<p class="text-error">${error }</p>
    				<div class="control-group">
						<form:label	for="cardNumber" path="cardNumber" cssClass="control-label">逸卡卡号</form:label>
						<div class="controls">
							<form:input path="cardNumber" cssClass="input-block-level" /> 
							<form:errors path="cardNumber" />	
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<form:hidden path="weixinAccount" />
							<button class="btn btn-large btn-block btn-primary" type="submit">绑定</button>
						</div>
					</div>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
	<script src="<c:url value="/js/jquery-1.8.3.min.js" />"></script>
	<script src="<c:url value="/js/bootstrap.min.js" />"></script>
</body>
</html>