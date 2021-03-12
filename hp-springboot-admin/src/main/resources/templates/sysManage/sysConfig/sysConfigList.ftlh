<!-- 导航条 -->
<span class="layui-breadcrumb">
	<a>系统管理</a>
	<a href="">系统配置管理</a>
</span>

<!-- 查询条件 -->
<#include "sysManage/sysConfig/sysConfigSearch.ftlh">
<!-- 列表 -->
<table id="sysConfigListTable" lay-filter="sysConfigListTableFilter"></table>
<script>
	function editSysConfig(id, view) {
		$.myplugin.saveDialog({
			skin : "layui-layer-success",
			title : id == 0 ? "新增系统配置" : "修改系统配置",
			area : "600px",
			formSelector : "#sysConfigEditForm",
			pageUrl : "${request.contextPath}/NoFilterController",
			pageData : {
				method : "forward",
				redirectUrl : "sysManage/sysConfig/sysConfigEdit",
				id : id
			},
			submitUrl : "${request.contextPath}/SysConfigController/saveSysConfig",
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
			elem : "#sysConfigListTable",
			defaultToolbar : false,
			url : "${request.contextPath}/SysConfigController/querySysConfigPageList",
			method : "post",
			autoSort : false,
			parseData : defaultParseData,
			page : true,
			limit : 10,
			cols : [[
				{field:"configName",title:"配置名称",width:150,align:"center"},
				{field:"configValueTypeStr",title:"配置类型",width:100,align:"center"},
				{field:"configKey",title:"配置key",width:100,align:"center"},
				{field:"configValue",title:"配置值",width:100,align:"center"},
				{field:"statusStr",title:"用户状态",width:100,align:"center", templet:function(d) {
					return $.myplugin.getSwitchHtml({
						id : d.id,
						status : d.status,
						disabled : !${SecuritySessionUtil.checkButtonEnabled('changeSysConfigStatusBtn')},
						filter : "configStatusFilter"
					});
				}},
				{field:"createTimeStr",title:"创建时间",width:200,align:"center"},
				{field:"id",title:"操作",align:"center",toolbar:"#toolbar"}
			]]
		});
		
		//监听状态开关按钮点击
		$.myplugin.statusSwitchEvent({
			filter : "configStatusFilter",
			name : "配置",
			postUrl : "${request.contextPath}/SysConfigController/changeSysConfigStatus"
		});
		
		//监听事件
		layui.table.on('tool(sysConfigListTableFilter)', function(obj) {
			let rowData = obj.data; //获得当前行数据
			let layEvent = obj.event;
			if (layEvent === "view") {
				//查看
				editSysConfig(rowData.id, 1);
			} else if (layEvent === "edit") {
				//编辑
				editSysConfig(rowData.id);
			} else if (layEvent === "del") {
				//删除
				layer.confirm("您确定要删除此数据吗？", {icon: 3, title:'提示'}, function(index) {
					$.post("${request.contextPath}/SysConfigController/deleteSysConfig", {
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
	<#if SecuritySessionUtil.checkButtonEnabled('viewSysConfigBtn') == true>
		<button type="button" class="layui-btn layui-btn-xs layui-btn-default" lay-event="view">
			<i class="layui-icon layui-icon-chat"></i>查看
		</button>
	</#if>
	<#if SecuritySessionUtil.checkButtonEnabled('editSysConfigBtn') == true>
		<button type="button" class="layui-btn layui-btn-xs layui-btn-success" lay-event="edit">
			<i class="layui-icon layui-icon-edit"></i>修改
		</button>
	</#if>
	<#if SecuritySessionUtil.checkButtonEnabled('delSysConfigBtn') == true>
		<button type="button" class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">
			<i class="layui-icon layui-icon-delete"></i>删除
		</button>
	</#if>
</script>