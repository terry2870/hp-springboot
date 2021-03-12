<#assign ConfigValueTypeEnum = javaClass["com.hp.springboot.admin.enums.ConfigValueTypeEnum"] >
<#assign DateTimeConfigTypeEnum = javaClass["com.hp.springboot.admin.enums.DateTimeConfigTypeEnum"] >
<form name="sysConfigEditForm" id="sysConfigEditForm" class="layui-form" lay-filter="sysConfigEditFormFilter" method="post">
	<input type="hidden" name="id" id="id">
	<div class="layui-form-item">
		<label for="configName" class="layui-form-label">配置名称：</label>
		<div class="layui-input-block">
			<input name="configName" id="configName" lay-verify="required" lay-verType="tips" placeholder="请输入配置名称" autocomplete="off" class="layui-input" />
		</div>
	</div>
	<div class="layui-form-item">
		<label for="configKey" class="layui-form-label">配置key：</label>
		<div class="layui-input-block" >
			<input name="configKey" id="configKey" lay-verify="required" lay-verType="tips" placeholder="请输入配置key" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label for="configValueType" class="layui-form-label">配置类型：</label>
		<div class="layui-input-block">
			<select name="configValueType" id="configValueType" lay-verify="required" lay-verType="tips" lay-filter="configValueTypeFilter">
				<#list ConfigValueTypeEnum.toJson() as item>
					<option value="${item.value}">${item.text}</option>
				</#list>
			</select>
		</div>
	</div>
	<div class="layui-form-item" id="configValueTypeDescDIV" style="display:none">
		<label for="configValueTypeDesc" class="layui-form-label">时间格式：</label>
		<div class="layui-input-block">
			<select name="configValueTypeDesc" id="configValueTypeDesc" lay-filter="configValueTypeDescFilter">
				<#list DateTimeConfigTypeEnum.toJson() as item>
					<#if item_index == 0>
					<option value="">请选择</option>
					</#if>
					<option value="${item.value}">${item.text}</option>
				</#list>
			</select>
		</div>
	</div>
	<div class="layui-form-item">
		<label for="configValue" class="layui-form-label">配置值：</label>
		<div class="layui-input-block" id="configValueDIV">
			<input name="configValue" id="configValue" lay-verify="required" lay-verType="tips" placeholder="请输入配置值" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label for="configDesc" class="layui-form-label">配置描述：</label>
		<div class="layui-input-block">
			<textarea id="configDesc" name="configDesc" style="height:70px;" placeholder="请输入配置描述" class="layui-textarea"></textarea>
		</div>
	</div>
	<div class="layui-form-item">
		<label for="status" class="layui-form-label">配置状态：</label>
		<div class="layui-input-block">
			<input type="checkbox" name="status" lay-skin="switch" lay-text="开启|关闭" value="1" checked>
		</div>
	</div>
</form>
<script>
	$(function() {
		let id = ${id};
		
		//选择配置类型
		function selectConfigValueType(value, data) {
			let configValueDiv = $("#sysConfigEditForm #configValueDIV");
			configValueDiv.empty();
			$("#sysConfigEditForm #configValueTypeDesc").val("");
			let configValue = data.configValue || "";
			if (value == ${ConfigValueTypeEnum.TEXT.getValue()} || value == ${ConfigValueTypeEnum.NUMBER.getValue()}) {
				//文本，数字
				$("#sysConfigEditForm #configValueTypeDescDIV").hide();
				configValueDiv.append($("<input type='"+ (value == ${ConfigValueTypeEnum.TEXT.getValue()} ? "text" : "number") +"'>").addClass("layui-input").val(configValue).attr({
					name : "configValue",
					id : "configValue",
					"lay-verify" : "required",
					"lay-verType" : "tips",
					placeholder : "请输入配置值",
					autocomplete : "off"
				}));
			} else if (value == ${ConfigValueTypeEnum.DATE_TIME.getValue()}) {
				// 日期时间
				$("#sysConfigEditForm #configValueTypeDescDIV").show();
				selectConfigValueTypeDesc(data.configValueTypeDesc, data);
			}
		}
		
		//选择时间格式
		function selectConfigValueTypeDesc(value, data) {
			let configValueDiv = $("#sysConfigEditForm #configValueDIV");
			configValueDiv.empty();
			let configValue = data.configValue || "";
			if (!value) {
				return;
			}
			
			let configValueInput = $("<input>").addClass("layui-input").val(configValue).attr({
				name : "configValue",
				id : "configValue",
				"lay-verify" : "required",
				"lay-verType" : "tips",
				placeholder : "请输入配置值",
				autocomplete : "off"
			});
			
			let type = null, format = "";
			if (value == ${DateTimeConfigTypeEnum.YEAR.getValue()}) {
				type = "year";
				format = "yyyy";
			} else if (value == ${DateTimeConfigTypeEnum.MONTH.getValue()}) {
				type = "month";
				format = "yyyy-MM";
			} else if (value == ${DateTimeConfigTypeEnum.DATE.getValue()}) {
				type = "date";
				format = "yyyy-MM-dd";
			} else if (value == ${DateTimeConfigTypeEnum.TIME.getValue()}) {
				type = "time";
				format = "HH:mm:ss";
			} else if (value == ${DateTimeConfigTypeEnum.DATETIME.getValue()}) {
				type = "datetime";
				format = "yyyy-MM-dd HH:mm:ss";
			}
			configValueDiv.append(configValueInput);
			layui.laydate.render({
				elem : "#configValue",
				type : type,
				format : format
			});
		}
		
		//监听类型选择
		layui.form.on("select(configValueTypeFilter)", function(data) {
			selectConfigValueType(data.value, {});
		});
		
		//监听日期格式选择
		layui.form.on("select(configValueTypeDescFilter)", function(data) {
			selectConfigValueTypeDesc(data.value, {});
		});
		
		if (id > 0) {
			$.getJSON("${request.contextPath}/SysConfigController/querySysConfigById", {
				id : id
			}, function(data) {
				data = data.data;
				layui.form.val("sysConfigEditFormFilter", data);
				selectConfigValueType(data.configValueType, data);
				
				//$.triggerSelect("#sysConfigEditForm #configValueType");
			});
		} else {
		}
	});
</script>