<#macro layout>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
 	<link rel="icon" href="data:image/ico;base64,aWNv">
	<title>${Session["projectName"]}</title>
	<link rel="stylesheet" href="${request.contextPath}/layui/css/layui.css">
	<link rel="stylesheet" href="${request.contextPath}/css/main-body.css">
	<link rel="stylesheet" href="${request.contextPath}/css/layui-card.css">
	<script src="${request.contextPath}/js/jquery.min.js"></script>
	<script src="${request.contextPath}/js/global.js"></script>
	<script src="${request.contextPath}/js/layui-plugins/layui.nav.js"></script>
	<script src="${request.contextPath}/js/layui-plugins/layui.tree.js"></script>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
	<div class="layui-header">
		<#include "layout/header.ftlh">
	</div>
	<div class="layui-side layui-bg-black">
		<#include "layout/nav.ftlh">
	</div>
	<div class="layui-body" style="bottom:0px;">
		<#-- 在这里嵌入main content -->
		<div style="padding: 15px;">
			<#nested>
		</div>
	</div>
</div>
<script src="${request.contextPath}/layui/layui.all.js"></script>
<script>
//JavaScript代码区域
layui.use('element', function() {
	var element = layui.element;
	
});
</script>
</body>
</html>
</#macro>