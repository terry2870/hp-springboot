<div class="layui-logo">${Session["projectName"]}</div>
<ul class="layui-nav layui-layout-right">
	<li class="layui-nav-item">
		<a href="javascript:;">
			<#if Session[javaClass["com.hp.springboot.admin.constant.AdminConstants"].SESSION_USER_KEY].headIcon != ''>
				<img src="${Session[javaClass["com.hp.springboot.admin.constant.AdminConstants"].SESSION_USER_KEY].headIcon}" class="layui-nav-img">
			</#if>
			${Session[javaClass["com.hp.springboot.admin.constant.AdminConstants"].SESSION_USER_KEY].nickName}
		</a>
		<dl class="layui-nav-child">
			<dd><a href="#">基本资料</a></dd>
			<dd><a id="logout" href="#">退出</a></dd>
		</dl>
	</li>
</ul>
<script>
	$(function() {
		$("#logout").click(function() {
			layer.confirm("您确定要退出吗？", {
				icon : 5
			}, function(index) {
				layer.close(index);
				window.location.href = "${request.contextPath}${Session["logoutUrl"]}";
			});
		});
	});
</script>