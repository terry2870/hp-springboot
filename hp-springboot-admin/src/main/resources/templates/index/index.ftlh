<input type="button" id="expand" value="展开" />
<input type="button" id="collapse" value="收起" />
<script>
	$(function() {
		$("#expand").click(function() {
			$(".nav-main").leftMenu("expand", 1);
		});
		$("#collapse").click(function() {
			$(".nav-main").leftMenu("collapse", 2);
		});
		
	});
</script>