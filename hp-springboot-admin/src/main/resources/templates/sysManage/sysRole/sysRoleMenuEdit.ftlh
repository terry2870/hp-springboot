<div id="roleMenuTree"></div>
<script>
	$(function() {
		$("#roleMenuTree").tree({
			ajaxParam : {
				cache : false,
				type : "POST",
				url : "${request.contextPath}/SysMenuController/queryAllSysMenu?status=${javaClass['com.hp.springboot.common.enums.StatusEnum'].VALID.getValue()}",
				dataType : "json"
			},
			loadFilter : defaultLoadFilter,
			pidField : "parentMenuId",
			textField : "menuName",
			showCheckbox : true,
			onLoadSuccess : function() {
				let self = this;
				$(self).tree("expandAll");
				//查找已有的权限
				$.getJSON("${request.contextPath}/SysRoleController/queryMenuByRoleId", {
					roleId : ${id}
				}, function(data) {
					$(self).tree("setChecked", data.data);
				});
			}
		});
	});
</script>