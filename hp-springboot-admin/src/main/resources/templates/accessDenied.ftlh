<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
 	<link rel="icon" href="data:image/ico;base64,aWNv">
	<title>您的访问被拒绝</title>
	<link rel="stylesheet" href="${request.contextPath}/layui/css/layui.css">
	<link rel="stylesheet" href="${request.contextPath}/css/login.css">
</head>
<body>
	<div class="login-card layui-card">
		<div class="layui-card-header">您的访问被拒绝</div>
		<div class="layui-card-body">
			您没有权限访问地址【${redirectUrl}】，请联系管理员
		</div>
	</div>
<script src="${request.contextPath}/js/jquery.min.js"></script>
<script src="${request.contextPath}/layui/layui.all.js"></script>
</body>
</html>
