<div class="layui-container">
	<div class="layui-row">
		<!-- 左侧树 -->
		<div class="layui-col-md3" id="leftTree"></div>
		
		<!-- 右侧表单 -->
		<div class="layui-col-md9" id="rightCard">
			<div class="layui-card layui-card-style-blue" style="width:700px;margin:auto;">
				<div class="layui-card-header">菜单详情</div>
				<div class="layui-card-body">
					<form name="menuEditForm" id="menuEditForm" class="layui-form" lay-filter="menuEditFormFilter" action="" method="post"></form>
				</div>
			</div>
		</div>
	</div>
</div> 

<!-- 菜单修改页面模板 -->
<script id="menuEditTemplate" type="text/html">
	<input type="hidden" name="id" value="{{d.id}}">
	<input type="hidden" name="parentMenuId" value="{{d.parentMenuId}}">
	<div class="layui-form-item">
		<label for="menuName" class="layui-form-label">菜单名称：</label>
		<div class="layui-input-block">
			<input name="menuName" id="menuName" lay-verify="required" placeholder="请输入菜单名称" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label for="menuUrl" class="layui-form-label">菜单地址：</label>
		<div class="layui-input-block">
			<input name="menuUrl" id="menuUrl" lay-verify="required" placeholder="请输入菜单地址" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label for="extraUrl" class="layui-form-label">额外地址：</label>
		<div class="layui-input-block">
			<input name="extraUrl" id="extraUrl" lay-verify="required" placeholder="请输入额外菜单地址" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label for="buttonId" class="layui-form-label">按钮ID：</label>
		<div class="layui-input-block">
			<input name="buttonId" id="buttonId" lay-verify="required" placeholder="请输入按钮ID" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label for="status" class="layui-form-label">状态：</label>
		<div class="layui-input-block">
			<input type="checkbox" name="status" id="status" lay-verify="required" lay-skin="switch" lay-text="有效|无效">
		</div>
	</div>
	<div class="layui-form-item">
		<label for="sortNumber" class="layui-form-label">排序：</label>
		<div class="layui-input-block">
			<input type="number" name="sortNumber" id="sortNumber" lay-verify="required" placeholder="请输入排序" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<div class="layui-input-block">
			<button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
			<button type="reset" class="layui-btn layui-btn-primary">重置</button>
		</div>
	</div>
</script>


<script>
	$(function() {
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
			showCheckbox : true,
			accordion : true,
			onclick : function(obj) {
				//生成模板
				layui.laytpl(menuEditTemplate.innerHTML).render(obj.data.attribute, function(html) {
					$("#menuEditForm").html(html);
					
					//赋值
					layui.form.val("menuEditFormFilter", obj.data.attribute);
				});
			}
		});
		
		$("#btn").click(function() {
			$("#leftTree").tree("setChecked", [2]);
		});
	});
</script>