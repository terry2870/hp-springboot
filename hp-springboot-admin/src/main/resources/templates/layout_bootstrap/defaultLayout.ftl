<#macro layout>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>页面首页</title>
<link rel="icon" href="data:image/ico;base64,aWNv">
<link href="${request.contextPath}/css/bootstrap.min.css" rel="stylesheet" >
<link href="${request.contextPath}/css/jquery.leftMenu.css" rel="stylesheet" >
<link href="${request.contextPath}/css/main-body.css" rel="stylesheet" >
<link rel="stylesheet" type="text/css" href="${request.contextPath}/css/bootstrap-plugins/bootstrap.textbox.css">
<link rel="stylesheet" type="text/css" href="${request.contextPath}/js/ztree/zTreeStyle.css">
<script type="text/javascript" src="${request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/js/global.js"></script>
<script type="text/javascript" src="${request.contextPath}/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<div class="container-fluid">
		<#include 'layout_bootstrap/header.ftlh' />
		<div class="row main">
			<div class="col nav-main">
				<#include 'layout_bootstrap/nav.ftlh' />
			</div>
			<div class="col main-body">
				<#-- 在这里嵌入main content -->
				<#nested>
			</div>
		</div>
	</div>
	<#include "layout_bootstrap/footer.ftlh" />
	<script type="text/javascript" src="${request.contextPath}/js/jquery.loadSuccess.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.default.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.breadcrumb.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.pagination.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.table.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.card.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.validatebox.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.textbox.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.combo.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.combobox.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.form.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.dialog.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.messager.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/bootstrap-plugins/bootstrap.progress.js"></script>
	
	<script type="text/javascript" src="${request.contextPath}/js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="${request.contextPath}/js/jquery.leftMenu.js"></script>
	
	<script type="text/javascript" src="${request.contextPath}/js/ztree/jquery.ztree.all.min.js"></script>
</body>
</html>
</#macro>