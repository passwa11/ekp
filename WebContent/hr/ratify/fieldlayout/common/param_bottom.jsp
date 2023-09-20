<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<tr>
	<td align="center" colspan="2">
		<%@ include file="/sys/xform/designer/fieldlayout/default_layout/common_param.jsp" %>
    </td>
</tr>
<script>
	function checkOK(){
		var control_width=$("#control_width").val();
		if(control_width&&!/^\d+%$|^\d+px$/g.test(control_width)){
			alert('<bean:message bundle="hr-ratify" key="hrRatify.fieldLayout.validate_width" />');
			return false;
		}
		var control_height=$("#control_height").val();
		if(control_height&&!/^\d+%$|^\d+px$/g.test(control_height)){
			alert('<bean:message bundle="hr-ratify" key="hrRatify.fieldLayout.validate_height" />');
			return false;
		}
		var control_content=$("#control_content").val();
		if(control_content&&!/^\d+$/g.test(control_content)){
			alert('<bean:message bundle="hr-ratify" key="hrRatify.fieldLayout.validate_content" />');
			return false;
		}
		return true;
	}
</script>