<!-- 导航条 -->
<span class="layui-breadcrumb">
	<a>系统管理</a>
	<a href="">修改密码</a>
</span>

<div class="layui-card-style-default layui-card" style="width:600px;margin:auto;margin-top:20px;">
	<div class="layui-card-header">修改密码</div>
	<div class="layui-card-body">
		<form class="layui-form" method="post" id="modifyPwdForm">
			<div class="layui-form-item">
				<label for="loginName" class="layui-form-label">登录名称：</label>
				<div class="layui-input-block">
					<input value='${Session[javaClass["com.hp.springboot.admin.constant.AdminConstants"].SESSION_USER_KEY].loginName}' readonly class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label for="oldPwd" class="layui-form-label">原密码：</label>
				<div class="layui-input-block">
					<input type="password" name="oldPwd" id="oldPwd" lay-verify="required" placeholder="请输入原密码" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label for="newPwd" class="layui-form-label">新密码：</label>
				<div class="layui-input-block">
					<input type="password" name="newPwd" id="newPwd" lay-verify="required" placeholder="请输入新密码" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label for="newPwd2" class="layui-form-label">重复密码：</label>
				<div class="layui-input-block">
					<input type="password" name="newPwd2" id="newPwd2" lay-verify="required" placeholder="请输入重复新密码" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<button class="layui-btn layui-btn-fluid layui-btn-radius" lay-submit lay-filter="go">确定</button>
			</div>
		</form>
	</div>
</div>
<script>

	$(function() {
		layui.form.on('submit(go)', function(data) {
			let newPwd = $("#modifyPwdForm #newPwd").val();
			let newPwd2 = $("#modifyPwdForm #newPwd2").val();
			if (newPwd != newPwd2) {
				layer.alert("两次输入的新密码不一致。", {icon: 2});
				return false;
			}
			$.post("${request.contextPath}/SysUserController/modifyPwd", data.field, function(d) {
				window.location.reload();
			});
			return false;
		});
	});
</script>