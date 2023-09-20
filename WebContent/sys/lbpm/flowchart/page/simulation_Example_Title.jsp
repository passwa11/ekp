<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
<script type="text/javascript">
Com_IncludeFile('jquery.js');
</script>
<script type="text/javascript">
function save(){
	var vTitle=$("#txtTitle").val();
	if(vTitle!=""){
		$dialog.callback(vTitle);
		$dialog.hide(null);
	}
	else{
		alert("实例标题不能为空！");
	}
	
}
</script>
</head>
<body>
	<table style="width: 100%" class="tb_normal">
		<tbody>
			<tr>
				<td style="text-align:right;">
					<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.flowExampleTitle" />
				</td>
				<td>
					<input style="width:100%" id="txtTitle" type="text" value="">
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align:center;">
					<input type="button" value="<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.save" />" onclick="save();" class="btnopt">
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>