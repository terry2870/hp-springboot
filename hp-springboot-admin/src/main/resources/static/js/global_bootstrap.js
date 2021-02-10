$.ajaxSetup({
	cache : false,
	dataType : "json",
	type : "POST",
	complete : function(XMLHttpRequest, textStatus) {
		window.top.$.messager.progress("close");
		let status = XMLHttpRequest.status;
		if (status == 200) {
			return;
		}
		if (status == 900) {
			//数据库超时
			$.messager.alert({
				title : "失败",
				content : "查询超时了",
				icon : "error"
			});
		} else if (status == 901) {
			//session超时
			$.messager.alert({
				title : "失败",
				content : "登录超时了，请重新登录",
				icon : "error",
				success : function() {
					window.top.location.href = "/logout";
				}
			});
		} else {
			//其他异常
			let text = XMLHttpRequest.responseText;
			text = JSON.parse(text);
			$.messager.alert({
				title : "失败",
				content : text.message,
				icon : "error"
			});
		}
	}
});

//通用的LoadFilter
function defaultLoadFilter(data) {
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
