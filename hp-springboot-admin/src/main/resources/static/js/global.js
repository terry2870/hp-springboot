$.ajaxSetup({
	cache : false,
	dataType : "json",
	type : "POST",
	complete : function(XMLHttpRequest, textStatus) {
		let status = XMLHttpRequest.status;
		if (status == 200) {
			return;
		}
		if (status == 900) {
			//数据库超时
			layer.alert("查询超时了", {icon: 2});
		} else if (status == 901) {
			//session超时
			layer.alert("登录超时了，请重新登录", {icon: 2}, function() {
				window.top.location.href = "/logout";
			});
		} else {
			//其他异常
			let text = XMLHttpRequest.responseText;
			text = JSON.parse(text);
			layer.alert(text.message, {icon: 2});
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

//通用的parseData
function defaultParseData(data) {
	if ($.type(data) == "array") {
		return data;
	}
	if (!data) {
		return emptyLayuiTableData();
	}
	let rowsData = data.data;
	if (!rowsData) {
		return emptyLayuiTableData();
	}
	return {
		code : data.code == 200 ? 0 : data.code,
		msg : data.message,
		count : rowsData.total,
		data : rowsData.rows
	};
}

function emptyLayuiTableData() {
	return {
		code : 0,
		msg : "",
		count : 0,
		data : []
	};
}
