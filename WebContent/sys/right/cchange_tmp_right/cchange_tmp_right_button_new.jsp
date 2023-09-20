<%@ include file="/resource/jsp/common.jsp"%>
<script type="text/javascript">
seajs.use(['sys/ui/js/dialog'], function(dialog) {
	window.dialog = dialog;
});
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
			if(Com_Parameter.dingXForm=='true'){
				//
				var url = '/sys/right/dingSuit/cchange_tmp_right.jsp?tmpModelName=${param.tmpModelName}&mainModelName=${param.mainModelName}&categoryId=${param.categoryId}&authReaderNoteFlag=${param.authReaderNoteFlag}&templateName=${param.templateName}';
				seajs.use( [ 'lui/dialog','lui/topic' ], function(dialog,topic) {
					window.parent.$dialogParameter={fdIds:values};
					dialog.iframe(url,"${lfn:message('sys-right:right.button.changeRightBatch')}", function(value) {
					}, {
						"width" : 680,
						"height" : 600
					});
				});
				return;
			}
			window.open('<c:url value="/sys/right/cchange_tmp_right/cchange_tmp_right.jsp?tmpModelName=${param.tmpModelName}&mainModelName=${param.mainModelName}&categoryId=${param.categoryId}&authReaderNoteFlag=${param.authReaderNoteFlag}&templateName=${param.templateName}"/>');
			return;
		}
	}
	dialog.alert('<bean:message key="page.noSelect"/>');
	return false;
}
</script>
<div id="changeRightBatch" style="display:none;">
<kmss:auth requestURL="/sys/right/cchange_tmp_right/cchange_tmp_right.jsp?tmpModelName=${param.tmpModelName}&mainModelName=${param.mainModelName}&categoryId=${param.categoryId}" requestMethod="GET">
	<c:if test="${not empty param.type }">
	    <ui:button text="${lfn:message('sys-right:right.button.changeRight')}" order="4" onclick="changeRightCheckSelect(document.tmpBatchChangeRightForm, 'tmpChangeRightBatch');" >
	    </ui:button>
		<input type="button" value="<bean:message key="right.button.changeRight" bundle="sys-right"/>"
			onclick="changeRightCheckSelect(document.tmpBatchChangeRightForm, 'tmpChangeRightBatch');">
	</c:if>
	<c:if test="${empty param.type }">
	    <ui:button text="${lfn:message('sys-right:right.button.changeRightBatch')}" order="4"  onclick="changeRightCheckSelect(document.tmpBatchChangeRightForm, 'tmpChangeRightBatch');" >
	    </ui:button>
	</c:if>
</kmss:auth>
</div>
<input type='hidden' id='fdIds'>
