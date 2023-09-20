<%@ include file="/resource/jsp/common.jsp"%>
<script type="text/javascript">
function changeRightCheckSelect() {
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
			document.getElementById('fdIds').value = values;
			var url="<c:url value="/sys/right/cchange_doc_right/cchange_doc_right.jsp"/>";
			url+="?modelName=${JsParam.modelName}";
			url+="&authReaderNoteFlag=${JsParam.authReaderNoteFlag}";
			url+="&categoryId=${JsParam.categoryId}";
			url+="&nodeType=${JsParam.nodeType}"; 
			window.open(url);
			return true;
		}
	}
	alert("<bean:message bundle="sys-right" key="right.change.batch.selectdocfirst" />");
	return true;
}

</script>
<div id="changeRightBatch" style="display:none;">
  <kmss:auth requestURL="/sys/right/cchange_doc_right/cchange_doc_right.jsp?modelName=${param.modelName}&categoryId=${param.categoryId}&nodeType=${param.nodeType}" requestMethod="GET">
  	<c:choose>
  		<c:when test="${param.newUi == 'true'}">
  			<ui:button text="${lfn:message('sys-right:right.button.changeRightBatch') }" onclick="changeRightCheckSelect();" order="-99"></ui:button>
  		</c:when>
  		<c:otherwise>
			<input type="button" value="<bean:message key="right.button.changeRightBatch" bundle="sys-right"/>" onclick="changeRightCheckSelect();">
  		</c:otherwise>
  	</c:choose>
  </kmss:auth>
</div>
<c:if test="${param.newUi != 'true'}">
<script>OptBar_AddOptBar("changeRightBatch");</script>
</c:if>
<input type='hidden' id='fdIds'>
