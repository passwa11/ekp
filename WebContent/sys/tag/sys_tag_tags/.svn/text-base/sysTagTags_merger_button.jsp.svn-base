<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script type="text/javascript">
Com_IncludeFile("dialog.js|optbar.js");
function mergerTagCheckSelect() {
	//list页面选择标签合并到主标签
	if(${param.type == 'main'}){
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
			var url="/sys/tag/sys_tag_tags/sysTagTags_merger.jsp";
			url += "?type=${JsParam.type}&fdMergerTagIds="+values+"&fdCategoryId=${JsParam.fdCategoryId}";
			seajs.use(['lui/dialog', 'lui/util/env','lang!sys-tag','lang!sys-ui'],function(dialog, env,lang,ui_lang) {
				dialog.iframe(url, lang["sysTagTags.mergerTag.title"], null, {
					width : 500,
					height : 250
				});
			});
			return;
		}
		 seajs.use(['lui/dialog'],
					function(dialog) {
		dialog.alert("<bean:message key="page.noSelect"/>");
		return ;
		})
		
	}
	//view页面选择别名标签合并到当前标签
	if(${param.type == 'alias'}){
		var fdMergerTargetId = "${JsParam.fdId}";
		if(fdMergerTargetId!=""){
			var url="/sys/tag/sys_tag_tags/sysTagTags_merger.jsp";
			url+="?fdCategoryId=${JsParam.fdCategoryId}&type=${JsParam.type}&fdMergerTargetId="+fdMergerTargetId;
			seajs.use(['lui/dialog', 'lui/util/env','lang!sys-tag','lang!sys-ui'],function(dialog, env,lang,ui_lang) {
				dialog.iframe(url, lang["sysTagTags.mergerTag.title"], null, {
					width : 500,
					height : 250
				});
			});
			return;
		}
	}	
}
</script>
<c:set var="isOptBar" value="true"></c:set>

<c:if test="${param.isOptBar!=null && param.isOptBar==false}">
	<c:set var="isOptBar" value="false"></c:set>
</c:if>
<c:if test="${isOptBar==true }">
<kmss:auth
	requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=editMergerTag&fdCategoryId=${param.fdCategoryId}"
	requestMethod="GET">
		<div
			id="mergerTag"
			style="display:none;">
			<!-- list页面合并标签 -->
			<c:if test="${param.type == 'main' }">
				<input type="button"
					   value="<bean:message key="sysTagTags.button.mergerTag" bundle="sys-tag"/>"
					   onclick="mergerTagCheckSelect();">
			</c:if>
			<c:if test="${param.type == 'alias' }">
			<input type="button"
				   value="<bean:message key="sysTagTags.button.mergerTags" bundle="sys-tag"/>"
				   onclick="mergerTagCheckSelect();">
			</c:if>
		</div>
	<script>OptBar_AddOptBar("mergerTag");</script>
</kmss:auth>
</c:if>
