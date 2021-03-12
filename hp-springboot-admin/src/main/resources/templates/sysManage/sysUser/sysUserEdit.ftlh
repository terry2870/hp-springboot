<form name="sysUserEditForm" id="sysUserEditForm" class="layui-form" lay-filter="sysUserEditFormFilter" method="post">
	<input type="hidden" name="id" id="id">
	<div class="layui-form-item">
		<label for="loginName" class="layui-form-label">登录名称：</label>
		<div class="layui-input-block">
			<input name="loginName" id="loginName" lay-verify="required" lay-verType="tips" placeholder="请输入登录名称" autocomplete="off" class="layui-input" <#if (id > 0) >readonly</#if> >
		</div>
	</div>
	<div class="layui-form-item">
		<label for="nickName" class="layui-form-label">用户昵称：</label>
		<div class="layui-input-block">
			<input name="nickName" id="nickName" lay-verify="required" lay-verType="tips" placeholder="请输入用户昵称" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label for="mobile" class="layui-form-label">手机号码：</label>
		<div class="layui-input-block">
			<input type="number" name="mobile" id="mobile" placeholder="请输入手机号码" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label for="email" class="layui-form-label">email：</label>
		<div class="layui-input-block">
			<input name="email" id="email" placeholder="请输入email" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label for="address" class="layui-form-label">联系地址：</label>
		<div class="layui-input-block">
			<input name="address" id="address" placeholder="请输入联系地址" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label for="identity" class="layui-form-label">用户角色：</label>
		<div class="layui-input-block">
			<#list roleList as role>
				<input type="checkbox" name="roleIdChk" title="${role.text}" value="${role.value}" lay-skin="primary" ${role.extraParams} >
			</#list>
		</div>
	</div>
	<div class="layui-form-item">
		<label for="identity" class="layui-form-label">用户身份：</label>
		<div class="layui-input-block">
			<select name="identity" id="identity">
				<#list javaClass["com.hp.springboot.admin.enums.IdentityEnum"].toJson() as item>
					<option value="${item.value}">${item.text}</option>
				</#list>
			</select>
		</div>
	</div>
	<div class="layui-form-item">
		<label for="status" class="layui-form-label">用户状态：</label>
		<div class="layui-input-block">
			<input type="checkbox" name="status" lay-skin="switch" lay-text="开启|关闭" value="1" checked>
		</div>
	</div>
</form>
<script>
	$(function() {
		let id = ${id};
		if (id > 0) {
			$.getJSON("${request.contextPath}/SysUserController/querySysUserById", {
				id : id
			}, function(data) {
				layui.form.val("sysUserEditFormFilter", data.data);
			});
		} else {
			//默认选中普通用户
			layui.form.val("sysUserEditFormFilter", {
				identity : ${javaClass["com.hp.springboot.admin.enums.IdentityEnum"].NORMAL.getValue()}
			});
		}
	});
</script>