<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>页面首页</title>
<link href="${request.contextPath}/css/bootstrap.min.css" rel="stylesheet" >
<link href="${request.contextPath}/css/login.css" rel="stylesheet" >
<link href="${request.contextPath}/css/jquery.messager.css" rel="stylesheet" >
<link rel="stylesheet" type="text/css" href="${request.contextPath}/css/bootstrap-plugins/bootstrap.textbox.css">
<script type="text/javascript" src="${request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.default.js"></script>
<script type="text/javascript" src="${request.contextPath}/js/jquery.loadSuccess.js"></script>
<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.card.js"></script>
<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.validatebox.js"></script>
<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.textbox.js"></script>
<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.dialog.js"></script>
<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.messager.js"></script>
</head>
<body>
	<div class="login_container">
		<form class="login-form">
			<div class="form-group row">
				<label for="staticEmail" class="col-sm-3 col-form-label">用户名：</label>
				<div class="col-sm-9">
					<input class="form-control" id="username" name="username" />
				</div>
			</div>
			<div class="form-group row">
				<label for="inputPassword" class="col-sm-3 col-form-label">密码：</label>
				<div class="col-sm-9">
					<input class="form-control" id="password" name="password" />
				</div>
			</div>
			<div class="form-group row">
				<div class="col-sm-12 text-center">
					<input type="button" class="btn btn-success w-75" id="submitBtn" value="登录" />
				</div>
			</div>
		</form>
	</div>
	<script>
		$(function() {
			$(".login-form").card({
				width : "100%",
				title : "用户登录",
				cardStyle : $.bootstrapClass.INFO,
				body : {
					html : "asdasd"
				},
				closeable : false
			});
			
			$("#username").textbox({
				required : true
			});
			$("#password").textbox({
				required : true,
				type : "password"
			});
			$("#submitBtn").click(function() {
				$.post("${request.contextPath}${loginProcessingUrl}", {
					username : $("#username").textbox("value"),
					password : $("#password").textbox("value")
				}, function(data) {
					if (data.code != 200) {
						$.messager.alert({
							title : "错误",
							content : data.message,
							icon : "error"
						});
					} else {
						location.href = "${request.contextPath}${indexUrl}";
					}
				});
			});
		});
	</script>
</body>
</html>
