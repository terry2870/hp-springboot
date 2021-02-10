<form id="sysUserEditForm" name="sysUserEditForm">
	<div class="form-group row">
		<label for="loginName" class="col-sm-3 col-form-label">登录名：</label>
		<div class="col-sm-9">
			<input type="text" class="form-control" id="loginName" name="loginName" value="">
		</div>
	</div>
	<div class="form-group row">
		<label for="nickName" class="col-sm-3">用户昵称：</label>
		<div class="col-sm-9">
			<input type="text" class="form-control" id="nickName" name="nickName" value="">
		</div>
	</div>
	<div class="form-group row">
		<label for="mobile" class="col-sm-3">手机号：</label>
		<div class="col-sm-9">
			<input type="text" class="form-control" id="mobile" name="mobile" value="">
		</div>
	</div>
	<div class="form-group row">
		<label for="address" class="col-sm-3">联系地址：</label>
		<div class="col-sm-9">
			<input type="text" class="form-control" id="address" name="address" value="">
		</div>
	</div>
</form>

<script>
	$(function() {
		$("#sysUserEditForm #loginName").textbox({
			placeholder : "请输入登录名称",
			required : true
		});
		
		$("#sysUserEditForm #nickName").textbox({
			placeholder : "请输入用户昵称",
			required : true
		});
	});
</script>