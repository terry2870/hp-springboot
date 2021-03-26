<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
 	<link rel="icon" href="data:image/ico;base64,aWNv">
	<meta http-equiv="refresh" content="10"><!-- 每隔10秒刷新一次页面 -->
	<style>
		table {
			width: 60%;
			border-top: 1px solid #999;
			border-left: 1px solid #999;
			border-spacing: 0;/*去掉单元格间隙*/
		}
		table td,table th {
			padding: 10px 30px;
			border-bottom: 1px solid #999;
			border-right: 1px solid #999;
		}
	</style>
	<title>队列监控</title>
</head>
<body>
<table align="center">
	<thead>
		<tr>
			<th>队列名称</th>
			<th>消费者数量</th>
			<th>队列容量(当前数量/最大容量)</th>
		</tr>
	</thead>
	<tbody>
		<#list queueList as queue>
		<tr align="center">
			<td>${queue.queueName}</td>
			<td>${queue.consumerSize}</td>
			<td>${queue.currentSize}/${queue.maxSize}</td>
		</tr>
		</#list>
	</tbody>
</table>
<script>
</script>
</body>
</html>