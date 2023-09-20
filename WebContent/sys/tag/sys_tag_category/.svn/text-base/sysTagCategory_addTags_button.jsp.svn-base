<%@ include file="/resource/jsp/common.jsp"%>
<script type="text/javascript">
Com_IncludeFile("dialog.js|optbar.js");
function addTagCheckSelect() {
	var fdAddTargetId = "${JsParam.fdId}";
	var url = "/sys/tag/sys_tag_category/sysTagCategory_addTags.jsp?fdAddTargetId="+fdAddTargetId;
	seajs.use(['lui/dialog', 'lui/util/env','lang!sys-tag','lang!sys-ui'],function(dialog, env,lang,ui_lang) {
		dialog.iframe(url, lang["sysTagCategory.addTags.title"], null, {
			width : 500,
			height : 250
		});
	});
}
</script>
<kmss:auth
	requestURL="/sys/tag/sys_tag_category/sysTagCategory.do?method=editAddTags"
	requestMethod="GET">
	<div
		id="addTag"
		style="display:none;">
		<input type="button"
			   value="<bean:message key="sysTagCategory.button.addTags" bundle="sys-tag"/>"
			   onclick="addTagCheckSelect();">
	</div>
	<script>OptBar_AddOptBar("addTag");</script>
</kmss:auth>

