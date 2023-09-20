<%--<%@ page import="com.landray.kmss.sys.xform.util.LangUtil" %>--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="config.profile.list">
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/listview.css"/>
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/override.css"/>
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/process.css"/>
	</template:replace>
	<template:replace name="content">
		<ui:fixed elem=".lui_list_operation" />
		<!-- 列表 -->
		<list:listview id="listview">
			<!-- 网格列表 -->

			<list:gridTable name="gridtable"
							layout="modeling.ui.listview.layout.version">--%>
				<ui:source type="AjaxJson">
					{url:'/sys/modeling/base/modelingAppVersion.do?method=data&fdAppId=${param.fdAppId}&isDialog=true&rowsize=100&orderby=fdOrder&ordertype=down'}
				</ui:source>
				<c:choose>
					<c:when test="${param.currentLang eq 'zh-HK'}">
						<list:row-template ref="modeling.ui.listview.template.zh-HK.version">
						</list:row-template>
					</c:when>
					<c:when test="${param.currentLang eq 'en-US'}">
						<list:row-template ref="modeling.ui.listview.template.en-US.version">
						</list:row-template>
					</c:when>
					<c:otherwise>
						<list:row-template ref="modeling.ui.listview.template.zh-CN.version">
						</list:row-template>
					</c:otherwise>
				</c:choose>
				<ui:event topic="list.loaded">
					bindOnGridClick();
				</ui:event>

			</list:gridTable>

		</list:listview>

		<%--底部--%>
		<div class="toolbar-bottom">
			<%--当前选择的--%>
			<div id="selectedVersion" class="lui_left_text"></div>
			<%--操作--%>
			<div class="lui_widget_btns"><%--复制为新版本--%>
				<%--切换版本--%>
				<kmss:auth requestURL="/sys/modeling/base/modelingAppVersion.do?method=switchVersion">
					<ui:button id="switchVersionBtn" text="${lfn:message('sys-modeling-base:page.modelingAppVersion.switchVersion')}" onclick="switchVersion()" order="2" />
				</kmss:auth>
				<kmss:auth requestURL="/sys/modeling/base/modelingAppVersion.do?method=addNewVersion">
					<ui:button text="${lfn:message('sys-modeling-base:page.modelingAppVersion.addNewVersion')}" onclick="addNewVersion()" order="2" />
				</kmss:auth>
				<ui:button text="${lfn:message('button.cancel')}" styleClass="lui_toolbar_btn_gray" onclick="closeDialog()" order="2" />
			</div>
		</div>

		<script>
			var isSwitchVersion = false;
			var listOption = {
				contextPath: '${LUI_ContextPath}',
				modelName: 'com.landray.kmss.sys.modeling.base.model.ModelingAppVersion',
				basePath: '/sys/modeling/base/modelingAppVersion.do',
				lang: {
					noSelect: '${lfn:message("page.noSelect")}',
					comfirmAdd: '${lfn:message("sys-modeling-base:page.modelingAppVersion.comfirmAdd")}',
					comfirmSwitch: '${lfn:message("sys-modeling-base:page.modelingAppVersion.comfirmSwitch")}',
					AddDialogTitle : '${lfn:message("sys-modeling-base:page.modelingAppListview.relatedDialogTitle")}',
					copyNewAppTip1 : '${lfn:message("sys-modeling-base:modeling.page.copy.newApp.tip1")}',
					copyNewAppTip2 : '${lfn:message("sys-modeling-base:modeling.page.copy.newApp.tip2")}',
					copyNewAppTip3 : '${lfn:message("sys-modeling-base:modeling.page.copy.newApp.tip3")}',
					tips : '${lfn:message("sys-modeling-base:modeling.page.tips")}',
					failed : '${lfn:message("sys-modeling-base:modeling.page.operation.failed")}',
					selected : '${lfn:message("sys-modeling-base:modeling.common.selected")}',
					licenseFormal : '${lfn:message("sys-modeling-base:modelingLicense.license.formal")}',
					licenseShort : '${lfn:message("sys-modeling-base:modelingLicense.license.short")}'
				}
			};
			function closeDialog(){
				console.log("ss",isSwitchVersion);
				$dialog.hide(isSwitchVersion);
			}
			Com_IncludeFile("list.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/appVersion/", 'js', true);
		</script>

	</template:replace>
</template:include>