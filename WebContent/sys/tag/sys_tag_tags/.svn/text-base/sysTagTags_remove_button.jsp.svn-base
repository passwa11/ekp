<%@ include file="/resource/jsp/common.jsp"%>
<script type="text/javascript">
Com_IncludeFile("dialog.js|optbar.js");
function removeTagCheckSelect() {
	var fdRemoveMainId = "${JsParam.fdId}";
	var url = "/sys/tag/sys_tag_tags/sysTagTags_remove.jsp?fdRemoveMainId="+fdRemoveMainId;
	seajs.use(['lui/dialog', 'lui/util/env','lang!sys-tag','lang!sys-ui'],function(dialog, env,lang,ui_lang) {
		dialog.iframe(url, lang["sysTagTags.removeTag.title"], null, {
			width : 500,
			height : 250
		});
	});
}
</script>
<kmss:auth
	requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=editRemoveAliasTag&fdCategoryId=${param.fdCategoryId}"
	requestMethod="GET">
	<div
		id="removeTag"
		style="display:none;">
		<input type="button"
			   value="<bean:message key="sysTagTags.button.removeTag" bundle="sys-tag"/>"
			   onclick="removeTagCheckSelect();">
	</div>
	<script>OptBar_AddOptBar("removeTag");</script>
</kmss:auth>

