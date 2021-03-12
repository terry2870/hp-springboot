<div id="sysUserSearchDiv" class="search-div">
	<div class="layui-input-inline">
		<input id="id" placeholder="用户id" autocomplete="off" class="layui-input">
	</div>
	<div class="layui-input-inline">
		<input id="loginName" placeholder="登录名" autocomplete="off" class="layui-input">
	</div>
	<div class="layui-input-inline">
		<input id="nickName" placeholder="昵称" autocomplete="off" class="layui-input">
	</div>
	<div class="layui-input-inline">
		<button type="button" class="layui-btn layui-btn-normal" id="searchBtn">
			<i class="layui-icon layui-icon-search"></i>搜索
		</button>
		<#if SecuritySessionUtil.checkButtonEnabled('addSysUserBtn') == true>
		<button type="button" class="layui-btn layui-btn-success" id="addBtn">
			<i class="layui-icon layui-icon-add-circle"></i>新增
		</button>
		</#if>
	</div>
</div>
<script>
	$(function() {
		//搜索
		$("#sysUserSearchDiv #searchBtn").click(function() {
			layui.table.reload("sysUserListTable", {
				where : {
					id : $("#sysUserSearchDiv #id").val(),
					loginName : $("#sysUserSearchDiv #loginName").val(),
					nickName : $("#sysUserSearchDiv #nickName").val()
				},
				page : {
					curr : 1
				}
			});
		});
		
		//新增
		$("#sysUserSearchDiv #addBtn").click(function() {
			editSysUser(0);
		});
	});
</script>