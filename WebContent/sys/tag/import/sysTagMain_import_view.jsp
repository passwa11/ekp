<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.tag.forms.SysTagMainForm"  %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="com.landray.kmss.sys.authentication.user.KMSSUser"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<c:set var="mainForm" value="${requestScope[param.formName]}" />
<c:set var="sysTagMainForm"
	value="${requestScope[param.formName].sysTagMainForm}" />
<c:set var="modelName" value="${mainForm.modelClass.name}" />

<c:set var="toolbarOrder" value="2"></c:set>
<c:if test="${ not empty param && not empty param.toolbarOrder }">
	<c:set var="toolbarOrder" value="${param.toolbarOrder}"></c:set>
</c:if>

<c:set var="useTab" value="true"></c:set>

<c:if test="${param.useTab!=null && param.useTab==false}">
	<c:set var="useTab" value="false"></c:set>
</c:if>
<c:if test="${param.isInContent == true}" >
	<c:if test="${not empty sysTagMainForm.fdTagNames }">
		<ui:content title="${lfn:message('sys-tag:sysTagTags.title') }" titleicon="${JsParam.titleicon }">
			<%@ include file="/sys/tag/import/sysTagMain_import_view_include.jsp"%>
		</ui:content>
	</c:if>
</c:if>

<c:if test="${param.isInContent ne true }">
	<%@ include file="/sys/tag/import/sysTagMain_import_view_include.jsp"%>
</c:if>
<kmss:auth
	requestURL="/sys/tag/sys_tag_main/sysTagMain.do?method=updateTag&fdModelName=${modelName}&fdModelId=${mainForm.fdId }"
	requestMethod="GET">

	<c:if test="${not empty param.showEditButton && param.showEditButton == 'true' }">
		<c:if test="${param.onlyShowAddButton !='true' }">
			<ui:button text="${lfn:message('sys-tag:sysTagMain.edit') }"
				parentId="toolbar" onclick="___modifyTag___()" order="${toolbarOrder}" />
		</c:if>
	</c:if>
	<script>
		// 调整标签，空的时候也只走这条路径
		
		window.___modifyTag___ = function() {
			seajs.use([ 'lui/dialog' ],
				function(dialog) {
					dialog.iframe("/sys/tag/sys_tag_main/sysTagMain.do?method=editTag&fdModelId=${mainForm.fdId}&fdModelName=${modelName}&fdQueryCondition=${HtmlParam.fdQueryCondition}",
					"${lfn:message('sys-tag:sysTagMain.edit')}",null, {
					width : 500,
					height : 250
					});
				});
		}
	</script>
</kmss:auth>

<kmss:auth
	requestURL="/sys/tag/sys_tag_main/sysTagMain.do?method=addTag&fdModelName=${modelName}&fdModelId=${mainForm.fdId }"
	requestMethod="GET">
	<c:if
		test="${not empty param.showEditButton && param.showEditButton == 'true' }">
		<ui:button
			text="${lfn:message('sys-tag:sysTagCategory.button.addTags') }"
			parentId="toolbar" onclick="___addTag___()" order="${toolbarOrder}" />
	</c:if>
	<script>
		// 添加标签
		window.___addTag___ = function() {
			seajs.use([ 'lui/dialog' ],
				function(dialog) {
					dialog.iframe("/sys/tag/sys_tag_main/sysTagMain.do?method=addTag&fdModelId=${mainForm.fdId}&fdModelName=${modelName}&fdQueryCondition=${HtmlParam.fdQueryCondition}",
					"${lfn:message('sys-tag:sysTagCategory.addTags.title')}",null, {
					width : 500,
					height : 250
					});
				});
		}
	</script>
</kmss:auth>
