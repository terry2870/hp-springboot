<!-- 导航条 -->
<span class="layui-breadcrumb">
	<a>一级菜单</a>
	<a href="">二级菜单</a>
</span>

<!-- 查询条件 -->
${'<#include "/other/${modelNameFirstLow}Search.ftlh">'}
<!-- 列表 -->
<table id="${modelNameFirstLow}ListTable" lay-filter="${modelNameFirstLow}ListTableFilter"></table>
<script>
	function edit${modelName}(id, view) {
		$.myplugin.saveDialog({
			skin : "layui-layer-success",
			title : id == 0 ? "新增${tableComment}" : "修改${tableComment}",
			area : "600px",
			formSelector : "#${modelNameFirstLow}EditForm",
			pageUrl : "${r"${request.contextPath}"}/NoFilterController",
			pageData : {
				method : "forward",
				redirectUrl : "other/${modelNameFirstLow}Edit",
				id : id
			},
			submitUrl : "${r"${request.contextPath}"}/${modelName}Controller/save${modelName}",
			btn : view ? ["关闭"] : null,
			onBeforeSubmit : function(formData) {
				if (formData.status === undefined) {
					formData.status = 0;
				}
			},
			yes : view ? function(index) {
				layer.close(index);
			} : null
		});
	}

	$(function() {
		layui.table.render({
			elem : "#${modelNameFirstLow}ListTable",
			defaultToolbar : false,
			url : "${r"${request.contextPath}"}/${modelName}Controller/query${modelName}PageList",
			method : "post",
			autoSort : false,
			parseData : defaultParseData,
			page : true,
			limit : 10,
			cols : [[
				{field:"id",title:"id",width:100,align:"center"},
				{field:"name",title:"名称",width:100,align:"center"},
				{field:"statusStr",title:"状态",width:100,align:"center", templet:function(d) {
					return $.myplugin.getSwitchHtml({
						id : d.id,
						status : d.status,
						disabled : !${r"${SecuritySessionUtil.checkButtonEnabled('changeSysConfigStatusBtn')}"},
						filter : "${modelNameFirstLow}StatusFilter"
					});
				}},
				{field:"id",title:"操作",align:"center",toolbar:"#toolbar"}
			]]
		});
		
		//监听状态开关按钮点击
		$.myplugin.statusSwitchEvent({
			filter : "${modelNameFirstLow}StatusFilter",
			name : "${tableComment}",
			postUrl : "${r"${request.contextPath}"}/${modelName}Controller/change${modelName}Status"
		});
		
		//监听事件
		layui.table.on('tool(${modelNameFirstLow}ListTableFilter)', function(obj) {
			let rowData = obj.data; //获得当前行数据
			let layEvent = obj.event;
			if (layEvent === "view") {
				//查看
				edit${modelName}(rowData.id, 1);
			} else if (layEvent === "edit") {
				//编辑
				edit${modelName}(rowData.id);
			} else if (layEvent === "del") {
				//删除
				layer.confirm("您确定要删除此数据吗？", {icon: 3, title:'提示'}, function(index) {
					$.post("${r"${request.contextPath}"}/${modelName}Controller/delete${modelName}", {
						id : rowData.id
					}, function(dd) {
						layer.close(index);
						window.location.reload();
					});
				});
			}
		});
	});
</script>

<script type="text/html" id="toolbar">
	${'<#if SecuritySessionUtil.checkButtonEnabled("view${modelName}Btn") == true>'}
		<button type="button" class="layui-btn layui-btn-xs layui-btn-default" lay-event="view">
			<i class="layui-icon layui-icon-chat"></i>查看
		</button>
	${'</#if>'}
	${'<#if SecuritySessionUtil.checkButtonEnabled("edit${modelName}Btn") == true>'}
		<button type="button" class="layui-btn layui-btn-xs layui-btn-success" lay-event="edit">
			<i class="layui-icon layui-icon-edit"></i>修改
		</button>
	${'</#if>'}
	${'<#if SecuritySessionUtil.checkButtonEnabled("del${modelName}Btn") == true>'}
		<button type="button" class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">
			<i class="layui-icon layui-icon-delete"></i>删除
		</button>
	${'</#if>'}
</script>