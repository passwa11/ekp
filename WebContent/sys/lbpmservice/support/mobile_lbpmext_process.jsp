<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<script>
</script>
<html>
<title>这是移动端</title>
<body>
<table class="muiSimple" cellpadding="0" cellspacing="0">
	<tr>
		<%-- 允许传阅 --%>
		<td class="muiTitle">允许传阅</td>
		<td>
			<xform:radio property="fdCanCircularize" mobile="true" alignment="H" showStatus="edit" value="1">
				<xform:simpleDataSource value="1">允许传阅是</xform:simpleDataSource>
				<xform:simpleDataSource value="0">允许传阅否</xform:simpleDataSource>
			</xform:radio>
		</td>
	</tr>
</table>
</body>
</html>
