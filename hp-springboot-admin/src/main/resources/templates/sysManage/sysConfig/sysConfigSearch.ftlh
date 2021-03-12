<div id="sysConfigSearchDiv" class="search-div layui-form">
	<div class="layui-input-inline">
		<input id="configName" placeholder="配置名称" autocomplete="off" class="layui-input">
	</div>
	<div class="layui-input-inline">
		<input id="configKey" placeholder="配置key" autocomplete="off" class="layui-input">
	</div>
	<div class="layui-input-inline">
		<select name="status" id="status">
			<#list javaClass["com.hp.springboot.common.enums.StatusEnum"].toJson() as item>
				<#if item_index == 0>
				<option value=""></option>
				</#if>
				<option value="${item.value}">${item.text}</option>
			</#list>
		</select>
	</div>
	<div class="layui-input-inline">
		<button type="button" class="layui-btn layui-btn-normal" id="searchBtn">
			<i class="layui-icon layui-icon-search"></i>搜索
		</button>
		<#if SecuritySessionUtil.checkButtonEnabled('addSysConfigBtn') == true>
		<button type="button" class="layui-btn layui-btn-success" id="addBtn">
			<i class="layui-icon layui-icon-add-circle"></i>新增
		</button>
		</#if>
	</div>
</div>
<script>
	$(function() {
		//搜索
		$("#sysConfigSearchDiv #searchBtn").click(function() {
			layui.table.reload("sysConfigListTable", {
				where : {
					configName : $("#sysConfigSearchDiv #configName").val(),
					configKey : $("#sysConfigSearchDiv #configKey").val(),
					status : $("#sysConfigSearchDiv #status").val()
				},
				page : {
					curr : 1
				}
			});
		});
		
		//新增
		$("#sysConfigSearchDiv #addBtn").click(function() {
			editSysConfig(0);
		});
	});
</script>