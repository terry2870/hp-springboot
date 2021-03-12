<form name="sysRoleEditForm" id="sysRoleEditForm" class="layui-form" lay-filter="sysRoleEditFormFilter" method="post">
	<input type="hidden" name="id" id="id">
	<div class="layui-form-item">
		<label for="roleName" class="layui-form-label">角色名称：</label>
		<div class="layui-input-block">
			<input name="roleName" id="roleName" lay-verify="required" lay-verType="tips" placeholder="请输入角色名称" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label for="roleInfo" class="layui-form-label">角色描述：</label>
		<div class="layui-input-block">
			<textarea name="roleInfo" id="roleInfo" placeholder="请输入角色描述" class="layui-textarea"></textarea>
		</div>
	</div>
	<div class="layui-form-item">
		<label for="status" class="layui-form-label">角色状态：</label>
		<div class="layui-input-block">
			<input type="checkbox" name="status" lay-skin="switch" lay-text="开启|关闭" value="1" checked>
		</div>
	</div>
</form>
<script>
	$(function() {
		layui.form.render(null, "sysRoleEditFormFilter");
		let id = "${id}";
		if (id > 0) {
			$.get("${request.contextPath}/SysRoleController/querySysRoleById", {
				id : id
			}, function(data) {
				layui.form.val("sysRoleEditFormFilter", data.data);
			});
		}
	});
</script>