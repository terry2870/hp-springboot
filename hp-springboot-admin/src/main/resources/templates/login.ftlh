<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
 	<link rel="icon" href="data:image/ico;base64,aWNv">
	<title>layout 后台大布局 - Layui</title>
	<link rel="stylesheet" href="${request.contextPath}/layui/css/layui.css">
	<link rel="stylesheet" href="${request.contextPath}/css/login.css">
</head>
<body>
	<div class="login-card layui-card">
		<div class="layui-card-header">用户登录</div>
		<div class="layui-card-body">
			<form class="layui-form" method="post">
				<div class="layui-form-item">
					<label for="username" class="layui-form-label">用户名：</label>
					<div class="layui-input-block">
						<input type="text" name="username" id="username" lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					<label for="password" class="layui-form-label">密码：</label>
					<div class="layui-input-block">
						<input type="password" name="password" id="password" lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					<button class="layui-btn layui-btn-fluid layui-btn-radius" lay-submit lay-filter="go">登录</button>
				</div>
			</form>
		</div>
	</div>
<script src="${request.contextPath}/js/jquery.min.js"></script>
<script src="${request.contextPath}/js/global.js"></script>
<script src="${request.contextPath}/layui/layui.all.js"></script>
<script>
$(function() {
	layui.form.on('submit(go)', function(data) {
		$.post("${request.contextPath}${loginProcessingUrl}", data.field, function(d) {
			if (d.code != 200) {
				layer.alert(d.message, {icon: 2});
			} else {
				location.href = "${request.contextPath}${indexUrl}";
			}
		});
		return false;
	});
});
</script>
</body>
</html>
