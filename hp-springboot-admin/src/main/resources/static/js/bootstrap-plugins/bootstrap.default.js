let NAME_SPACE = "http://www.w3.org/2000/svg";
let XLINKNS = "http://www.w3.org/1999/xlink";
let PLUGIN_NAME = "plugin-name";

/**
 * bootstrap的一些扩展工具
*/
/**
 * bootstrap中的样式
 */
jQuery.bootstrapClass = {
	DEFAULT : "default",
	PRIMARY : "primary",
	SUCCESS : "success",
	INFO : "info",
	WARNING : "warning",
	DANGER : "danger",
	LINK : "link"
};

jQuery.tools = {
	/**
	 * 标记成功
	 * @param {*} jq 
	 * @param {*} pluginName 
	 */
	markSuccess : function(jq, pluginName) {
		jq.loadSuccess();
		jq.attr(PLUGIN_NAME, pluginName);
	},
	/**
	 * 通用的LoadFilter
	 * @param {*} data 
	 */
	defaultLoadFilter : function(data) {
		if ($.type(data) == "array") {
			return data;
		}
		if (!data) {
			return [];
		}
		if (data.code) {
			return data.data || [];
		} else {
			return data || [];
		}
	}
};

/**
 * 创建svg标签
 * @param {*} tagName 
 */
function createSVG(tagName) {
	return document.createElementNS(NAME_SPACE, tagName);
}

/**
 * 创建svg的图标
 * @param {*} icon 
 * @param {*} contextPath 
 * @param {*} property 
 */
function createSVGIcon(svgIcon, contextPath, property) {
	if (!svgIcon) {
		svgIcon = "gear";
	}

	let icon = createSVG("svg");
	if (property) {
		for (key in property) {
			icon.setAttribute(key, property[key]);
		}
	}

	let iconUse = createSVG("use");
	iconUse.setAttributeNS(XLINKNS, "href", contextPath +"/css/bootstrap-icons.svg#"+ svgIcon);
	icon.appendChild(iconUse);
	return icon;
}

jQuery.message = {
	/**
	 * 显示一个对话框
	 */
	alert : function(title, content, callback) {
		var dialog = $("<div>").appendTo($("body"));
		dialog.dialog({
			backdrop : "static",
			title : title,
			content : content,
			buttons : [{
				text : "关闭",
				className : "btn-danger",
				onclick : function() {
					$(dialog).dialog("hide");
				}
			}],
			onAfterHide : callback || $.noop
		});
	},
	/**
	 * 显示一个可以选择是或否的对话框
	 */
	confirm : function(title, content, callback) {
		var dialog = $("<div>").appendTo($("body"));
		dialog.dialog({
			backdrop : "static",
			title : title,
			content : content,
			buttons : [{
				text : "否",
				className : "btn-danger",
				onclick : function() {
					$(dialog).dialog("hide");
				}
			}, {
				text : "是",
				className : "btn-success",
				onclick : function() {
					callback();
					$(dialog).dialog("hide");
				}
			}]
		});
	},
	/**
	 * 显示一个可以输入框的对话框
	 * title, msg, callback, multiline, value, placeholder
	 */
	prompt : function(options) {
		var dialog = $("<div>").appendTo($("body"));
		var content = $("<div>").addClass("form-group");
		content.append($("<label>").addClass("col-sm-3 control-label").html(options.msg));
		var innerDiv = $("<div>").addClass("col-sm-9").appendTo(content);
		var txt = null;
		if (options.multiline === true) {
			txt = $("<textarea>").addClass("form-control");
		} else {
			txt = $("<input>").addClass("form-control");
		}
		txt.attr("placeholder", options.placeholder || options.msg);
		txt.val(options.value || "");
		innerDiv.append(txt);
		
		dialog.dialog({
			backdrop : "static",
			title : options.title,
			content : content,
			buttons : [{
				text : "否",
				className : "btn-danger",
				onclick : function() {
					$(dialog).dialog("hide");
				}
			}, {
				text : "是",
				className : "btn-success",
				onclick : function() {
					if (options.callback) {
						options.callback(txt.val());
					}
					$(dialog).dialog("hide");
				}
			}]
		});
	}
};