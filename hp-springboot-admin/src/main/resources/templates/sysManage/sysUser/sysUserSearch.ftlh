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
		<button type="button" class="layui-btn layui-btn-normal" id="searchUser">
			<i class="layui-icon layui-icon-search"></i>搜索
		</button>
		<button type="button" class="layui-btn" id="addUser">
			<i class="layui-icon layui-icon-add-circle"></i>添加
		</button>
	</div>
</div>
<script>
	$(function() {
		//搜索
		$("#sysUserSearchDiv #searchUser").click(function() {
			layui.table.reload("sysUserListTable", {
				where : {
					id : $("#sysUserSearchDiv #id").val(),
					loginName : $("#sysUserSearchDiv #loginName").val(),
					nickName : $("#sysUserSearchDiv #nickName").val()
				}
			});
		});
	});
</script>