<%@ include file="/resource/jsp/common.jsp"%>
<script type="text/javascript">
Com_IncludeFile("dialog.js|optbar.js");
function removeTagCheckSelect() {
	var fdCategoryId = "${JsParam.fdId}";
	var url = "/sys/tag/sys_tag_category/sysTagCategory_removeTags.jsp?fdCategoryId="+fdCategoryId;
	seajs.use(['lui/dialog', 'lui/util/env','lang!sys-tag','lang!sys-ui'],function(dialog, env,lang,ui_lang) {
		dialog.iframe(url, lang["sysTagCategory.button.removeTags"], null, {
			width : 500,
			height : 250
		});
	});
}
</script>
<kmss:auth
	requestURL="/sys/tag/sys_tag_category/sysTagCategory.do?method=editRemoveTags"
	requestMethod="GET">
	<div
		id="removeTag"
		style="display:none;">
		<input type="button"
			   value="<bean:message key="sysTagCategory.button.removeTags" bundle="sys-tag"/>"
			   onclick="removeTagCheckSelect();">
	</div>
	<script>OptBar_AddOptBar("removeTag");</script>
</kmss:auth>

