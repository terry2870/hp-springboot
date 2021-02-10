<div class="row top-banner">
	<div class="col title">后台管理系统</div>
	<div class="col menu"><ul class="nav justify-content-end">
		<!--
		<li>
			<a class="dropdown">
				<a class="btn dropdown-toggle" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					${Session[javaClass["com.hp.springboot.admin.constant.AdminConstants"].SESSION_USER_KEY].nickName}
				</a>
				<div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
					<a class="dropdown-item" href="${request.contextPath}${logoutUrl}">退出</a>
				</div>
			</a>
		</li>
		-->
		<li>
			<a>
				${Session[javaClass["com.hp.springboot.admin.constant.AdminConstants"].SESSION_USER_KEY].nickName}
			</a>
		</li>
		<li>
			<a id="logout">
				退出
			</a>
		</li>
	</ul></div>
</div>
<script>
	$(function() {
		$("#logout").click(function() {
			$.messager.confirm({
				title : "确认",
				content : "您确定要退出吗？",
				callback : function() {
					window.location.href = "${request.contextPath}${logoutUrl}";
				}
			});
		});
	});
</script>