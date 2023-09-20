<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<template:include ref="config.profile.list">
	<template:replace name="content">
		<div style="margin:5px 10px;">
			<!-- 操作 -->
			<div class="lui_list_operation">
				<div style="float:right">
					<div style="display: inline-block;vertical-align: middle;">
						<ui:toolbar count="3">
							<%--复制为新版本--%>
							<kmss:auth requestURL="/sys/modeling/base/modelingAppVersion.do?method=addNewVersion">
								<ui:button text="${lfn:message('sys-modeling-base:page.modelingAppVersion.addNewVersion')}" onclick="addNewVersion()" order="2" />
							</kmss:auth>
							<%--切换版本--%>
							<kmss:auth requestURL="/sys/modeling/base/modelingAppVersion.do?method=switchVersion">
								<ui:button text="${lfn:message('sys-modeling-base:page.modelingAppVersion.switchVersion')}" onclick="switchVersion()" order="2" />
							</kmss:auth>
						</ui:toolbar>
					</div>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation" />
		<!-- 列表 -->
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/sys/modeling/base/modelingAppVersion.do?method=data&fdAppId=${param.fdAppId}&orderby=fdOrder&ordertype=down'}
			</ui:source>
			<!-- 列表视图 -->
			<list:colTable isDefault="false" name="columntable" onRowClick="selectRow(this,'!{fdId}')">
				<list:col-radio />
				<list:col-auto props="fdVersion;fdStatusText;fdCreator;fdCreateTime;fdModifier;fdUpdateTime;fdPublisher;fdPublishTime;operation" /></list:colTable>
		</list:listview>
		<!-- 翻页 -->
		<list:paging />

		<script>
			var listOption = {
				fdId: '${param.fdAppId}',
				contextPath: '${LUI_ContextPath}',
				modelName: 'com.landray.kmss.sys.modeling.base.model.ModelingAppVersion',
				basePath: '/sys/modeling/base/modelingAppVersion.do',
				lang: {
					noSelect: '${lfn:message("page.noSelect")}',
					comfirmAdd: '${lfn:message("sys-modeling-base:page.modelingAppVersion.comfirmAdd")}',
					comfirmSwitch: '${lfn:message("sys-modeling-base:page.modelingAppVersion.comfirmSwitch")}',
					AddDialogTitle : '${lfn:message("sys-modeling-base:page.modelingAppListview.relatedDialogTitle")}'
				}
			};
			Com_IncludeFile("list.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/appVersion/", 'js', true);
			//刷新iframe高度
			$(function () {
				seajs.use(["lui/jquery", "sys/ui/js/dialog", "lui/topic"], function ($, dialog, topic) {
					topic.subscribe("list.loaded", setIframeHeight);
				});

				let interval = setInterval(function () {
					let btnName = "${lfn:message('sys-modeling-base:table.modelingAppVersion')}";
					let btn = $("body", parent.document).find('input[value="' + btnName + '"]');
					btn.on("click", setIframeHeight);
					if (btn.length > 0) {
						clearInterval(interval);
					}
				}, 200);
			})

			function setIframeHeight() {
				var bodyHeight = $(document.body).outerHeight(true) + 70;
				$("body", parent.document).find('#versionIframe').animate({
					height: bodyHeight
				}, "fast");
			}

		</script>

	</template:replace>
</template:include>