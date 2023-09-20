<%@ include file="/resource/jsp/common.jsp"%>
<script type="text/javascript">
Com_IncludeFile("dialog.js|optbar.js");
function moveTagCheckSelect() {
	var values="";
	var fdRemoveMainId = "${JsParam.fdId}";
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
			var url="<c:url value="sys/tag/sys_tag_tags/sysTagTags.do"/>";
			url+="?method=editMoveTag&fdCategoryId=${JsParam.fdCategoryId}&fdMoveTagIds="+values;
			var left = document.body.clientWidth/2;
			var top = document.body.clientHeight/2;
			Dialog_PopupWindow(Com_Parameter.ContextPath+'resource/jsp/frame.jsp?url='+encodeURIComponent(Com_Parameter.ContextPath+url),'500','250');
			return;
		}
	}else if(fdRemoveMainId!=""){
			var url="<c:url value="sys/tag/sys_tag_tags/sysTagTags.do"/>";
			url+="?method=editMoveTag&fdCategoryId=${JsParam.fdCategoryId}&fdMoveTagIds="+fdRemoveMainId;
			var left = document.body.clientWidth/2;
			var top = document.body.clientHeight/2;
			Dialog_PopupWindow(Com_Parameter.ContextPath+'resource/jsp/frame.jsp?url='+encodeURIComponent(Com_Parameter.ContextPath+url),'500','250');
			return;
	}
	 seajs.use(['lui/dialog'],
				function(dialog) {
	dialog.alert("<bean:message key="page.noSelect"/>");
	return ;
	})
}
</script>
<c:set var="isOptBar" value="true"></c:set>

<c:if test="${param.isOptBar!=null && param.isOptBar==false}">
	<c:set var="isOptBar" value="false"></c:set>
</c:if>
<c:if test="${isOptBar==true }">
<kmss:auth
	requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=editMoveTag&fdCategoryId=${param.fdCategoryId}&fdId=${param.fdId}"
	requestMethod="GET">
	<div
		id="moveTag"
		style="display:none;">
		<input type="button"
			   value="<bean:message key="sysTagTags.button.updateCategory" bundle="sys-tag"/>"
			   onclick="moveTagCheckSelect();">
	</div>
	<script>OptBar_AddOptBar("moveTag");</script>
</kmss:auth>
</c:if>
