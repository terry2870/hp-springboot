<div id="sysRoleSearchDiv" class="search-div">
	<div class="layui-input-inline">
		<input id="id" placeholder="角色id" autocomplete="off" class="layui-input">
	</div>
	<div class="layui-input-inline">
		<input id="roleName" placeholder="角色名称" autocomplete="off" class="layui-input">
	</div>
	<div class="layui-input-inline">
		<button type="button" class="layui-btn layui-btn-normal" id="searchBtn">
			<i class="layui-icon layui-icon-search"></i>搜索
		</button>
		<#if SecuritySessionUtil.checkButtonEnabled('addSysRoleBtn') == true>
			<button type="button" class="layui-btn layui-btn-success" id="addBtn">
				<i class="layui-icon layui-icon-add-circle"></i>新增
			</button>
		</#if>
	</div>
</div>
<script>
	$(function() {
		//搜索
		$("#sysRoleSearchDiv #searchBtn").click(function() {
			layui.table.reload("sysRoleListTable", {
				where : {
					id : $("#sysRoleSearchDiv #id").val(),
					roleName : $("#sysRoleSearchDiv #roleName").val()
				},
				page : {
					curr : 1
				}
			});
		});
		
		//新增
		$("#sysRoleSearchDiv #addBtn").click(function() {
			editSysRole(0);
		});
	});
</script>