<%@ include file="/resource/jsp/common.jsp"%>
<script type="text/javascript">
<!-- 
function changeRightCheckSelect(formObj, method) {
	var values="";
	var selected;
	var select = document.getElementsByName("List_Selected");
	for(var i=0;i<select.length;i++) {
		if(select[i].checked){
			values+=select[i].value;
			values+=",";
			selected=true;
		}
	}
	if(selected) {
		values = values.substring(0,values.length-1);
		if(selected) {
			document.getElementById('fdIds').value=values;
			window.open('<c:url value="/sys/right/cchange_tmp_right/cchange_tmp_right.jsp?tmpModelName=${JsParam.tmpModelName}&mainModelName=${JsParam.mainModelName}&authReaderNoteFlag=${JsParam.authReaderNoteFlag}&templateName=${JsParam.templateName}"/>');
			return;
		}
	}
	alert("<bean:message bundle="sys-right" key="right.change.batch.selecttmpfirst" />");
	return false;
}
// -->
</script>
<div id="changeRightBatch" style="display:none;">
<kmss:auth requestURL="/sys/right/cchange_tmp_right/cchange_tmp_right.jsp?tmpModelName=${param.modelName}&mainModelName=${param.mainModelName}" requestMethod="GET">
	<c:if test="${not empty param.type }">
		<input type="button" value="<bean:message key="right.button.changeRight" bundle="sys-right"/>"
			onclick="changeRightCheckSelect(document.tmpBatchChangeRightForm, 'tmpChangeRightBatch');">
	</c:if>
	<c:if test="${empty param.type }">
		<input type="button" value="<bean:message key="right.button.changeRightBatch" bundle="sys-right"/>"
			onclick="changeRightCheckSelect(document.tmpBatchChangeRightForm, 'tmpChangeRightBatch');">
	</c:if>
</kmss:auth>
</div>
<input type='hidden' id='fdIds'>
<script>OptBar_AddOptBar("changeRightBatch");</script>
