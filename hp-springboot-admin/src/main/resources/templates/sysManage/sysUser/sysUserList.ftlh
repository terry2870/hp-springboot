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
				{field:"loginName",title:"登录名",align:"center"},
				{field:"nickName",title:"用户昵称",align:"center"},
				{field:"statusStr",title:"用户状态",align:"center"},
				{field:"createTimeStr",title:"创建时间",align:"center"},
				{field:"id",title:"操作",align:"center",toolbar:"#toolbar"}
			]]
		});
		
		//监听事件
		layui.table.on('tool(sysUserListTableFilter)', function(obj) {
			let rowData = obj.data; //获得当前行数据
			let layEvent = obj.event;
			if(layEvent === "edit") {
				//编辑
				alert("edit" + JSON.stringify(rowData));
			} else if (layEvent === "del") {
				//删除
				alert("del" + JSON.stringify(rowData));
			}
		});
		
	});
</script>

<script type="text/html" id="toolbar">
	<button type="button" class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit">
		<i class="layui-icon layui-icon-edit"></i>修改
	</button>
	<button type="button" class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">
		<i class="layui-icon layui-icon-delete"></i>删除
	</button>
</script>