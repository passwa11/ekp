<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<script type="text/javascript">
	Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
	Com_IncludeFile("jquery.js");
	Com_IncludeFile('json2.js');
</script>
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">
</head>
<body>
	<form>
	<table class="tb_normal"  width=95%>
		<tr>
			<td align="center" colspan="2">
				<b><bean:message bundle="sys-xform-base" key="Designer_Lang.fieldLayout_setParameters" /></b>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=40%><bean:message bundle="sys-xform-base" key="Designer_Lang.fieldLayout_required" /></td>
			<td>
				<input type="checkbox" name="control_required" value="true" storage='true'/>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=40%><bean:message bundle="sys-xform-base" key="Designer_Lang.fieldLayout_defauleValue" /></td>
			<td><input type='text' id='defaultValue' class='inputsgl' style="width:80%" storage="true"/></td>
		</tr>
		<tr>
			<td class="td_normal_title" width=40%><bean:message bundle="sys-xform-base" key="Designer_Lang.fieldLayout_width" /></td>
			<td><input type='text' id='control_width' class='inputsgl' style="width:80%" storage="true"/></td>
		</tr>
		<tr>
				<td colspan="2">
					<c:import url="/sys/xform/designer/fieldlayout/default_layout/param_style.jsp" charEncoding="UTF-8">
					</c:import>
				</td>
			</tr>
		<tr>
			<td align="center" colspan="2">
			   <%@ include file="/sys/xform/designer/fieldlayout/default_layout/common_param.jsp"%>
			</td>
		</tr>
	</table>
	</form>
</body>
</html>
<script>
function checkOK(){
	var control_width=$("#control_width").val();
	if(control_width&&!/^\d+%$|^\d+px$/g.test(control_width)){
		alert('<bean:message bundle="sys-xform-base" key="Designer_Lang.fieldLayout_widthValidate" />');
		return false;
	}
	return true;
}
</script>
