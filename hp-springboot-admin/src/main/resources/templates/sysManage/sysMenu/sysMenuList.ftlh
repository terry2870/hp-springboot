<div class="layui-container">
	<div class="layui-row">
		<!-- 左侧树 -->
		<div class="layui-col-md3" id="leftTree"></div>
		
		<!-- 右侧表单 -->
		<div class="layui-col-md9" id="rightCard">
			<div class="layui-card layui-card-style-default" style="width:700px;margin:auto;">
				<div class="layui-card-header">菜单详情</div>
				<div class="layui-card-body">
					<form name="menuEditForm" id="menuEditForm" class="layui-form" lay-filter="menuEditFormFilter" method="post"></form>
				</div>
			</div>
		</div>
	</div>
</div>
<#assign menu_type_none = 0 >
<#assign menu_type_root = '${javaClass["com.hp.springboot.admin.enums.MenuTypeEnum"].ROOT.getValue()}' >
<#assign menu_type_child = '${javaClass["com.hp.springboot.admin.enums.MenuTypeEnum"].CHILD.getValue()}' >
<#assign menu_type_button = '${javaClass["com.hp.springboot.admin.enums.MenuTypeEnum"].BUTTON.getValue()}' >
<#assign status_valid = '${javaClass["com.hp.springboot.common.enums.StatusEnum"].VALID.getValue()}' >
<#assign status_invalid = '${javaClass["com.hp.springboot.common.enums.StatusEnum"].INVALID.getValue()}' >
<!-- 菜单修改页面模板 -->
<script id="menuEditTemplate" type="text/html">
	<input type="hidden" name="id" id="id">
	<input type="hidden" name="parentMenuId" id="parentMenuId">
	<input type="hidden" name="menuType" id="menuType">
	<div class="layui-form-item">
		<label for="menuName" class="layui-form-label">菜单名称：</label>
		<div class="layui-input-block">
			<input name="menuName" id="menuName" lay-verify="required" placeholder="请输入菜单名称" autocomplete="off" class="layui-input">
		</div>
	</div>
	{{# if (d.menuType == ${menu_type_child}) { }}
	<div class="layui-form-item">
		<label for="menuUrl" class="layui-form-label">菜单地址：</label>
		<div class="layui-input-block">
			<input name="menuUrl" id="menuUrl" lay-verify="required" placeholder="请输入菜单地址" autocomplete="off" class="layui-input">
		</div>
	</div>
	{{# } }}
	{{# if (d.menuType == ${menu_type_child} || d.menuType == ${menu_type_button}) { }}
	<div class="layui-form-item">
		<label for="extraUrl" class="layui-form-label">额外地址：</label>
		<div class="layui-input-block">
			<textarea name="extraUrl" id="extraUrl" placeholder="请输入额外菜单地址" class="layui-textarea"></textarea>
		</div>
	</div>
	{{# } }}
	{{# if (d.menuType == ${menu_type_button}) { }}
	<div class="layui-form-item">
		<label for="buttonId" class="layui-form-label">按钮ID：</label>
		<div class="layui-input-block">
			<input name="buttonId" id="buttonId" lay-verify="required" placeholder="请输入按钮ID" autocomplete="off" class="layui-input">
		</div>
	</div>
	{{# } }}
	<div class="layui-form-item">
		<label for="status" class="layui-form-label">状态：</label>
		<div class="layui-input-block">
			<input type="checkbox" name="status" id="status" value="1" checked lay-verify="required" lay-skin="switch" lay-text="有效|无效">
		</div>
	</div>
	<div class="layui-form-item">
		<label for="sortNumber" class="layui-form-label">排序：</label>
		<div class="layui-input-block">
			<input type="number" name="sortNumber" id="sortNumber" lay-verify="required" placeholder="请输入排序" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<div class="layui-input-block layui-btn-container">
			{{# if (d.id == -1) { }}
			<input type="button" class="layui-btn layui-btn-warm" id="addRootBtn" value="添加根节点">
			{{# } }}
			{{# if (d.menuType == ${menu_type_root} && d.id > 0) { }}
			<input type="button" class="layui-btn layui-btn-success" id="addChildBtn" value="添加子节点">
			{{# } }}
			{{# if (d.menuType == ${menu_type_child} && d.id > 0) { }}
			<input type="button" class="layui-btn" id="addButtonBtn" value="添加按钮">
			{{# } }}
			{{# if (d.id > 0 && d.status == ${status_valid}) { }}
			<input type="button" class="layui-btn layui-btn-danger" id="deleteBtn" value="删除节点" />
			{{# } }}
			{{# if (d.id > 0 && d.status == ${status_invalid}) { }}
			<input type="button" class="layui-btn layui-btn-danger" id="forceDeleteBtn" value="强制删除">
			{{# } }}
			{{# if (d.id >= 0) { }}
			<input class="layui-btn" lay-submit lay-filter="submitBtn" value="立即提交">
			{{# } }}
		</div>
	</div>
</script>


<script>
	$(function() {
		let form = layui.form;
		$("#leftTree").tree({
			ajaxParam : {
				cache : false,
				type : "POST",
				url : "${request.contextPath}/SysMenuController/queryAllSysMenu",
				dataType : "json"
			},
			loadFilter : defaultLoadFilter,
			pidField : "parentMenuId",
			textField : "menuName",
			accordion : true,
			onlyIconControl : true,
			onclick : function(obj) {
				//生成模板
				layui.laytpl(menuEditTemplate.innerHTML).render(obj.data.attribute, function(html) {
					$("#menuEditForm").html(html);
					
					//赋值
					form.val("menuEditFormFilter", obj.data.attribute);
				});
			}
		});
		
		layui.laytpl(menuEditTemplate.innerHTML).render({
			menuType : ${menu_type_none},
			id : -1
		}, function(html) {
			$("#menuEditForm").html(html);
			form.render(null, "menuEditFormFilter");
		});
		
		//添加根节点
		$("body").on("click", "#addRootBtn", function() {
			let data = {
				menuType : ${menu_type_root},
				parentMenuId : 0,
				id : 0
			};
			layui.laytpl(menuEditTemplate.innerHTML).render(data, function(html) {
				$("#menuEditForm").html(html);
				form.val("menuEditFormFilter", data);
			});
		});
		
		//添加子节点
		$("body").on("click", "#addChildBtn", function() {
			let data = {
				menuType : ${menu_type_child},
				parentMenuId : $("#menuEditForm #id").val(),
				id : 0
			};
			layui.laytpl(menuEditTemplate.innerHTML).render(data, function(html) {
				$("#menuEditForm").html(html);
				form.val("menuEditFormFilter", data);
			});
		});
		
		//添加按钮
		$("body").on("click", "#addButtonBtn", function() {
			let data = {
				menuType : ${menu_type_button},
				parentMenuId : $("#menuEditForm #id").val(),
				id : 0
			};
			layui.laytpl(menuEditTemplate.innerHTML).render(data, function(html) {
				$("#menuEditForm").html(html);
				form.val("menuEditFormFilter", data);
			});
		});
		
		//删除节点
		$("body").on("click", "#deleteBtn", function() {
			layer.confirm("您确定要删除此菜单吗？", {icon: 3, title:'提示'}, function(index) {
				$.post("${request.contextPath}/SysMenuController/deleteSysMenu", {
					id : $("#menuEditForm #id").val(),
					force : false
				}, function(dd) {
					layer.close(index);
					window.location.reload();
				});
			});
		});
		
		//强制删除
		$("body").on("click", "#forceDeleteBtn", function() {
			layer.confirm("强制删除后不能恢复，您确定要强制删除此菜单吗？", {icon: 3, title:'提示'}, function(index) {
				$.post("${request.contextPath}/SysMenuController/deleteSysMenu", {
					id : $("#menuEditForm #id").val(),
					force : true
				}, function(dd) {
					layer.close(index);
					window.location.reload();
				});
			});
		});
		
		//提交
		form.on('submit(submitBtn)', function(data) {
			let url = "${request.contextPath}/SysMenuController/saveSysMenu";
			if (data.field.status === undefined) {
				data.field.status = 0;
			}
			
			//ajax 提交
			$.post(url, data.field, function(d) {
				//成功，刷新页面
				window.location.reload();
			});
			return false;
		});
	});
</script>