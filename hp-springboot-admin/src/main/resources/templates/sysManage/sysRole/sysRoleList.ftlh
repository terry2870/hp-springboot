<!-- 导航条 -->
<span class="layui-breadcrumb">
	<a>系统管理</a>
	<a href="">角色管理</a>
</span>

<!-- 查询条件 -->
<#include "sysManage/sysRole/sysRoleSearch.ftlh">
<!-- 列表 -->
<table id="sysRoleListTable" lay-filter="sysRoleListTableFilter"></table>
<script>
	function editSysRole(id) {
		$.myplugin.saveDialog({
			skin : "layui-layer-success",
			title : id == 0 ? "新增角色" : "修改角色",
			area : "500px",
			formSelector : "#sysRoleEditForm",
			pageUrl : "${request.contextPath}/NoFilterController",
			pageData : {
				method : "forward",
				redirectUrl : "sysManage/sysRole/sysRoleEdit",
				id : id
			},
			submitUrl : "${request.contextPath}/SysRoleController/saveSysRole",
			onBeforeSubmit : function(formData) {
				if (formData.status === undefined) {
					formData.status = 0;
				}
			}
		});
	}

	$(function() {
		layui.table.render({
			elem : "#sysRoleListTable",
			defaultToolbar : false,
			url : "${request.contextPath}/SysRoleController/querySysRolePageList",
			method : "post",
			autoSort : false,
			parseData : defaultParseData,
			page : true,
			limit : 10,
			cols : [[
				{field:"id",title:"角色id",width:100,align:"center"},
				{field:"roleName",title:"角色名称",align:"center"},
				{field:"statusStr",title:"用户状态",width:100,align:"center", templet:function(d) {
					return "<input type='checkbox' userid='"+ d.id +"' lay-skin='switch' lay-text='开启|关闭' "+ (d.status == 1 ? "checked" : "") +" disabled lay-filter='roleStatusFilter'>";
				}},
				{field:"createTimeStr",title:"创建时间",align:"center"},
				{field:"id",title:"操作",align:"center",toolbar:"#toolbar"}
			]]
		});
		
		//监听事件
		layui.table.on('tool(sysRoleListTableFilter)', function(obj) {
			let rowData = obj.data; //获得当前行数据
			let layEvent = obj.event;
			if(layEvent === "edit") {
				//编辑
				editSysRole(rowData.id);
			} else if (layEvent === "del") {
				//删除
				layer.confirm("您确定要删除此角色吗？", {icon: 3, title:'提示'}, function(index) {
					$.post("${request.contextPath}/SysRoleController/deleteSysRole", {
						id : rowData.id
					}, function(dd) {
						layer.close(index);
						window.location.reload();
					});
				});
			} else if (layEvent === "roleMenu") {
				//分配菜单
				$.myplugin.saveDialog({
					overflow : false,
					area : ["350px", "90%"],
					pageUrl : "${request.contextPath}/NoFilterController",
					pageData : {
						method : "forward",
						redirectUrl : "sysManage/sysRole/sysRoleMenuEdit",
						id : rowData.id
					},
					yes : function(index) {
						//只获取选中的叶子节点
						let checkedData = $("#roleMenuTree").tree("getCheckedId", true);
						$.post("${request.contextPath}/SysRoleController/saveSysRoleMenu", {
							roleId : rowData.id,
							menuIds : checkedData.join(",")
						}, function() {
							layer.close(index);
							window.location.reload();
						});
					}
				});
			}
		});
		
	});
</script>
<script type="text/html" id="toolbar">
	<#if SecuritySessionUtil.checkButtonEnabled('editSysRoleBtn') == true>
		<button type="button" class="layui-btn layui-btn-xs layui-btn-success" lay-event="edit">
			<i class="layui-icon layui-icon-edit"></i>修改
		</button>
	</#if>
	<#if SecuritySessionUtil.checkButtonEnabled('delSysRoleBtn') == true>
		<button type="button" class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">
			<i class="layui-icon layui-icon-delete"></i>删除
		</button>
	</#if>
	<#if SecuritySessionUtil.checkButtonEnabled('viewSysRoleMenuBtn') == true>
		<button type="button" class="layui-btn layui-btn-xs layui-btn-info" lay-event="roleMenu">
			<i class="layui-icon layui-icon-align-left"></i>分配菜单
		</button>
	</#if>
</script>