jQuery.myplugin = {
	/**
	 * 生成一个列表中的状态开关的html
	 * @param {*} obj 
	 * 	id			行数据的id
	 * 	status		状态
	 * 	disabled	是否禁用点击事件
	 * 	filter		lay-filter
	 */
	getSwitchHtml(obj) {
		let idKey = "layui-status-switch-id";
		return "<input type='checkbox' "+ idKey +"='"+ obj.id +"' lay-skin='switch' lay-text='开启|关闭' "+ (obj.status == 1 ? "checked" : "") +" "+ (obj.disabled ? "disabled" : "") +" lay-filter='"+ obj.filter +"'>";
	},
	/**
	 * 状态开关的事件监听
	 * @param {*} obj 
	 * 	filter		lay-filter
	 * 	skin		对话框的skin（默认 layui-layer-info）
	 * 	name		提示确认语句中的名称
	 * 	postUrl		提交的url
	 * 	idKey		提交时传递的id的key（默认id）
	 * 	statusKey	提交时传递的status的key（默认status）
	 */
	statusSwitchEvent : function(obj) {
		layui.form.on("switch("+ obj.filter +")", function(data) {
			let newStatus = data.elem.checked;
			let idKey = "layui-status-switch-id";
			let rowId = $(data.elem).attr(idKey);
			layer.open({
				skin : obj.skin || "layui-layer-info",
				content : "您确定要 " + (newStatus ? "【开启】" : "【关闭】") + " 该"+ obj.name +"吗！",
				btn: ['确定', '取消'],
				yes: function(index) {
					$.post(obj.postUrl, {
						[obj.idKey || "id"] : rowId,
						[obj.statusKey || "status"] : newStatus ? 1 : 0
					}, function() {
						layer.close(index);
						window.location.reload();
					});
				},
				btn2 : function(index) {
					data.elem.checked = !newStatus;
					layui.form.render();
					layer.close(index);
				},
				cancel : function(index) {
					data.elem.checked = !newStatus;
					layui.form.render();
					layer.close(index);
				}
			});
		});
	},
	/**
	 * 触发select的监听事件
	 * @param {select对象} obj 
	 */
	triggerSelect: function(obj) {
		obj = $(obj);
		let selectValue = obj.val();
		let ddObject = 'dd[lay-value=' + selectValue + ']';
		obj.siblings("div.layui-form-select").find('dl').find(ddObject).click();//触发
	},
	/**
	 * 生成一个可以保存的页面
	 * @param {页面的参数} dialogParams 
	 * 包含：
	 * 		formSelector	form 的对象选择器
	 * 		pageUrl			打开的页面地址
	 * 		pageData		打开页面传递参数
	 * 		submitUrl		提交请求地址
	 * 		onBeforeSubmit	提交之前检查（带有一个参数formData，该参数是form提交时，里面的所有参数。返回false，可以阻止提交动作）
	 * 		onLoadSuccess	弹出层打开成功后执行
	 * 		onSubmitSuccess	提交成功后执行
	 * 		title			标题
	 * 		area			宽高
	 * 		offset			坐标
	 * 		anim			动画
	 * 		btn				按钮
	 *		overflow		不填或等于true，则添加layui-layer-visible 这个样式
	 */
	saveDialog : function(dialogParams) {
		$.get(dialogParams.pageUrl, dialogParams.pageData, function(html) {
			let skin = (dialogParams.skin || "") + " layui-layer-editform";
			if (dialogParams.overflow === undefined || dialogParams.overflow === true) {
				skin += " layui-layer-visible";
			}
			layer.open($.extend({}, {
				type : 1,
				title : dialogParams.title,
				area : dialogParams.area,
				offset : dialogParams.offset,
				anim : dialogParams.anim ,
				content : html,
				resize : false,
				skin : skin,
				btn : dialogParams.btn || ["确定", "关闭"],
				success : function(layero, index) {
					if (dialogParams.onLoadSuccess) {
						dialogParams.onLoadSuccess.call(this, layero, index);
					}

					if (!dialogParams.formSelector) {
						return;
					}

					let formObject = $(dialogParams.formSelector);
					//给form里面隐藏一个提交按钮
					let hideSubmit = $("<input type='button'>").attr({
						"lay-submit" : "",
						"lay-filter" : "submitBtn"
					}).hide();
					formObject.append(hideSubmit);

					layui.form.render(null, formObject.attr("lay-filter"));
				},
				yes : function(index) {
					if (dialogParams.yes) {
						// 如果传入yes，则就执行该方法
						dialogParams.yes.call(this, index);
						return;
					}

					//没有传入yes，则默认为提交表单
					//提交表单
					layui.form.on('submit(submitBtn)', function(data) {
						let formData = data.field;
						if (dialogParams.onBeforeSubmit) {
							let onBeforeSubmitResult = dialogParams.onBeforeSubmit.call(this, formData);
							if (onBeforeSubmitResult === false) {
								return false;
							}
						}

						//ajax 提交
						$.post(dialogParams.submitUrl, formData, function(d) {
							//成功
							if (dialogParams.onSubmitSuccess) {
								dialogParams.onSubmitSuccess.call(this, d);
							}

							//刷新页面
							layer.close(index);
							window.location.reload();
						});
						return false;
					});
					$(dialogParams.formSelector + " [lay-submit]").click();
				}
			}));
		}, "html");
	}
};