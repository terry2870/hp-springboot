<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
 	<link rel="icon" href="data:image/ico;base64,aWNv">
	<title>layout 后台大布局 - Layui</title>
	<link rel="stylesheet" href="${request.contextPath}/layui/css/layui.css">
	<link rel="stylesheet" href="${request.contextPath}/css/layui-skin.css">
	<style>
		body {
			background-color: silver;
		}
		.login-card {
			width: 400px;
			margin: auto;
			margin-top: 150px;
		}
	</style>
</head>
<#assign verifyCode = javaClass['com.hp.springboot.admin.constant.AdminConstants'].VALIDATE_CODE_KEY >
<body>
	<div class="layui-card-style-default login-card layui-card">
		<div class="layui-card-header">用户登录</div>
		<div class="layui-card-body">
			<form class="layui-form" method="post">
				<div class="layui-form-item">
					<label for="username" class="layui-form-label">用户名：</label>
					<div class="layui-input-block">
						<input type="text" name="username" id="username" lay-verify="required" lay-verType="tips" placeholder="请输入用户名" autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					<label for="password" class="layui-form-label">密码：</label>
					<div class="layui-input-block">
						<input type="password" name="password" id="password" lay-verify="required" lay-verType="tips" placeholder="请输入密码" autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					<label for="password" class="layui-form-label">验证码：</label>
					<div class="layui-input-block">
						<input id="${verifyCode}" maxlength="4" lay-verify="required" lay-verType="tips" autocomplete="off" class="layui-input" style="width:60px;display:inline">
						<img id="verifyCodeImg" src="${request.contextPath}/verifyCode" style="height:38px;">
						<a id="refreshVerifyCode" style="cursor:pointer">看不清，换一个</a>
					</div>
				</div>
				<div class="layui-form-item">
					<button class="layui-btn layui-btn-default layui-btn-fluid layui-btn-radius" lay-submit lay-filter="go">登录</button>
				</div>
			</form>
		</div>
	</div>
<script src="${request.contextPath}/js/jquery.min.js"></script>
<script src="${request.contextPath}/js/global.js"></script>
<script src="${request.contextPath}/layui/layui.all.js"></script>
<script>
$(function() {
	$("#refreshVerifyCode").click(function() {
		$("#verifyCodeImg").attr("src", "${request.contextPath}/verifyCode?" + new Date().getTime());
	});

	layui.form.on('submit(go)', function(data) {
		let sumbmitData = data.field;
		sumbmitData.${verifyCode} = $("#${verifyCode}").val();
		$.post("${request.contextPath}${loginProcessingUrl}", sumbmitData, function(d) {
			if (d.code != 200) {
				layer.alert(d.message, {icon: 2});
				//刷新验证码
				$("#refreshVerifyCode").click();
				$("#${verifyCode}").val("");
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
