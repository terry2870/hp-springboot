<!-- 导航条 -->
<span class="layui-breadcrumb">
	<a>系统管理</a>
	<a href="">用户管理</a>
</span>

<!-- 查询条件 -->
<#include "sysManage/sysUser/sysUserSearch.ftlh">
<!-- 列表 -->
<table id="sysUserListTable" lay-filter="sysUserListTableFilter"></table>
<script>
	function editSysUser(id, view) {
		$.myplugin.saveDialog({
			skin : "layui-layer-success",
			title : id == 0 ? "新增用户" : "修改用户",
			area : "600px",
			formSelector : "#sysUserEditForm",
			pageUrl : "${request.contextPath}/SysUserController/sysUserEdit",
			pageData : {
				id : id
			},
			submitUrl : "${request.contextPath}/SysUserController/saveSysUser",
			btn : view ? ["关闭"] : null,
			onBeforeSubmit : function(formData) {
				if (formData.status === undefined) {
					formData.status = 0;
				}
				let arr = [];
				$("#sysUserEditForm input[name='roleIdChk']:checked").each(function() {
					arr.push($(this).val());
				});
				if (arr.length == 0) {
					layer.alert("请选择角色", {icon: 2});
					return false;
				}
				formData.roleIdList = arr;
			},
			yes : view ? function(index) {
				layer.close(index);
			} : null
		});
	}

	$(function() {
		layui.table.render({
			elem : "#sysUserListTable",
			defaultToolbar : false,
			url : "${request.contextPath}/SysUserController/querySysUserPageList",
			method : "post",
			autoSort : false,
			parseData : defaultParseData,
			page : true,
			limit : 10,
			cols : [[
				{field:"id",title:"用户id",width:100,align:"center",sort:true},
				{field:"loginName",title:"登录名",width:200,align:"center"},
				{field:"nickName",title:"用户昵称",width:200,align:"center"},
				{field:"statusStr",title:"用户状态",width:100,align:"center", templet:function(d) {
					return $.myplugin.getSwitchHtml({
						id : d.id,
						status : d.status,
						disabled : !${SecuritySessionUtil.checkButtonEnabled('changeSysUserStatusBtn')},
						filter : "userStatusFilter"
					});
				}},
				{field:"createTimeStr",title:"创建时间",width:200,align:"center"},
				{field:"id",title:"操作",align:"center",toolbar:"#toolbar"}
			]]
		});
		
		//监听状态开关按钮点击
		$.myplugin.statusSwitchEvent({
			filter : "userStatusFilter",
			name : "用户",
			postUrl : "${request.contextPath}/SysUserController/changeUserStatus"
		});
				
		//监听事件
		layui.table.on('tool(sysUserListTableFilter)', function(obj) {
			let rowData = obj.data; //获得当前行数据
			let layEvent = obj.event;
			if (layEvent === "view") {
				//查看
				editSysUser(rowData.id, 1);
			} else if (layEvent === "edit") {
				//编辑
				editSysUser(rowData.id);
			} else if (layEvent === "del") {
				//删除
				layer.confirm("您确定要删除此用户吗？", {icon: 3, title:'提示'}, function(index) {
					$.post("${request.contextPath}/SysUserController/deleteSysUser", {
						id : rowData.id
					}, function(dd) {
						layer.close(index);
						window.location.reload();
					});
				});
			}
		});
		
		//排序
		layui.table.on("sort(sysUserListTableFilter)", function(obj) {
			let field = obj.field;
			let order = obj.type;
			if (order == null) {
				field = "";
			}
			
			layui.table.reload("sysUserListTable", {
				initSort : obj,
				where : {
					sort : field,
					order : order
				}
			});
		});
	});
</script>

<script type="text/html" id="toolbar">
	<#if SecuritySessionUtil.checkButtonEnabled('viewSysUserBtn') == true>
		<button type="button" class="layui-btn layui-btn-xs layui-btn-default" lay-event="view">
			<i class="layui-icon layui-icon-chat"></i>查看
		</button>
	</#if>
	<#if SecuritySessionUtil.checkButtonEnabled('editSysUserBtn') == true>
		<button type="button" class="layui-btn layui-btn-xs layui-btn-success" lay-event="edit">
			<i class="layui-icon layui-icon-edit"></i>修改
		</button>
	</#if>
	<#if SecuritySessionUtil.checkButtonEnabled('delSysUserBtn') == true>
		<button type="button" class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">
			<i class="layui-icon layui-icon-delete"></i>删除
		</button>
	</#if>
</script>