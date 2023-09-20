<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% response.addHeader("X-UA-Compatible", "IE=edge"); %>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<%@ include file="/sys/formula/dialog_edit_head.jsp" %>
<body>
<script type="text/javascript">
//某些浏览器，高度设为100%,不继承父类高度？导致样式错乱，这里手动设置一下
Com_AddEventListener(window, "load", function(){
	if(window.innerHeight){
		document.getElementById("treeiframe").setAttribute("height",window.innerHeight-3);
	}else{
		var winHeight = Math.max(document.documentElement.clientHeight, document.body.clientHeight);
		document.getElementById("treeiframe").setAttribute("height",Math.max(winHeight, document.body.scrollHeight)-3);
	}
});
</script>
<table cellpadding=0 cellspacing=0 width="100%" style="height:99%; border-collapse:collapse;border: 0px #303030 solid;">
	<tr>
		<td valign="top" style="width:220px; border:#303030 solid; border-width:0px 1px 0px 0px; border-collapse:collapse;">
			<iframe id="treeiframe" height=100% style="width:220px;" frameborder=0 scrolling="auto" src='dialog_tree.jsp'></iframe>
		</td>
		<td width="5px">&nbsp;</td>
		<td valign="top">
			<c:import url="/sys/formula/dialog_edit_right.jsp" charEncoding="utf-8"></c:import>	
		</td>
	</tr>
</table>
</body>
</html>