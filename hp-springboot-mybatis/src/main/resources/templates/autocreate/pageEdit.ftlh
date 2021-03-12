${"<#assign ConfigValueTypeEnum = javaClass['com.hp.springboot.admin.enums.ConfigValueTypeEnum'] >"}
${"<#assign DateTimeConfigTypeEnum = javaClass['com.hp.springboot.admin.enums.DateTimeConfigTypeEnum'] >"}
<form name="${modelNameFirstLow}EditForm" id="${modelNameFirstLow}EditForm" class="layui-form" lay-filter="${modelNameFirstLow}EditFormFilter" method="post">
	<input type="hidden" name="id" id="id" vlaue="${r'${id}'}">
	<#list columnList as column>
	<#if column.fieldName == 'id' || commonColumnName?seq_contains(column.fieldName)>
	<#else>
	<div class="layui-form-item">
		<label for="${column.fieldName}" class="layui-form-label">${column.columnComment}：</label>
		<div class="layui-input-block">
			<input name="${column.fieldName}" id="${column.fieldName}" lay-verify="required" lay-verType="tips" placeholder="请输入${column.columnComment}" autocomplete="off" class="layui-input" />
		</div>
	</div>
	</#if>
	</#list>
	<div class="layui-form-item">
		<label for="status" class="layui-form-label">状态：</label>
		<div class="layui-input-block">
			<input type="checkbox" name="status" lay-skin="switch" lay-text="开启|关闭" value="1" checked>
		</div>
	</div>
</form>
<script>
	$(function() {
		let id = ${r'${id}'};
		
		if (id > 0) {
			$.getJSON("${r"${request.contextPath}"}/${modelName}Controller/query${modelName}ById", {
				id : id
			}, function(data) {
				data = data.data;
				layui.form.val("${modelNameFirstLow}EditFormFilter", data);
			});
		} else {
		}
	});
</script>