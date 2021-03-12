<div id="${modelNameFirstLow}SearchDiv" class="search-div layui-form">
	<div class="layui-input-inline">
		<input id="id" placeholder="id" autocomplete="off" class="layui-input">
	</div>
	<div class="layui-input-inline">
		<button type="button" class="layui-btn layui-btn-normal" id="searchBtn">
			<i class="layui-icon layui-icon-search"></i>搜索
		</button>
		${"<#if SecuritySessionUtil.checkButtonEnabled('add${modelName}Btn') == true>"}
		<button type="button" class="layui-btn layui-btn-success" id="addBtn">
			<i class="layui-icon layui-icon-add-circle"></i>新增
		</button>
		${"</#if>"}
	</div>
</div>
<script>
	$(function() {
		//搜索
		$("#${modelNameFirstLow}SearchDiv #searchBtn").click(function() {
			layui.table.reload("${modelNameFirstLow}ListTable", {
				where : {
					id : $("#${modelNameFirstLow}SearchDiv #id").val()
				},
				page : {
					curr : 1
				}
			});
		});
		
		//新增
		$("#${modelNameFirstLow}SearchDiv #addBtn").click(function() {
			edit${modelName}(0);
		});
	});
</script>