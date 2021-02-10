<script>
	$(function() {
		$(".nav-main").leftMenu({
			idField : "id",
			pidField : "parentMenuId",
			textField : "menuName",
			iconField : "iconName",
			ajaxParam : {
				cache : false,
				type : "POST",
				url : "${request.contextPath}/NoFilterController?method=queryUserMenuFromSession",
				dataType : "json"
			},
			loadFilter : defaultLoadFilter,
			onClickMenu : function(item, index) {
				if (!item.menuUrl) {
					return;
				}
				location.href = "${request.contextPath}" + item.menuUrl;
			},
			contextPath : "${request.contextPath}",
			onLoadSuccess : function(data) {
				//处理展开的菜单和选中的菜单
				expandMenu(data);
			}
		});
	});
	
	//展开当前菜单
	function expandMenu(data) {
		let pageUrl = window.location.pathname;
		let allMenus = data;
		if (!allMenus || allMenus.length == 0) {
			return;
		}
		
		let menuId = 0, parentMenuId = 0;
		for (let i = 0; i < allMenus.length; i++) {
			if ("${request.contextPath}" + allMenus[i].menuUrl == pageUrl) {
				menuId = allMenus[i].id;
				parentMenuId = allMenus[i].parentMenuId;
				break;
			}
		}
		if (menuId == 0) {
			return;
		}
		
		$(".nav-main").leftMenu("select", menuId);
	}
</script>
