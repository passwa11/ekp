<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.sys.modeling.base.util.SysModelingUtil" %>
<%@ page import="com.landray.kmss.sys.modeling.base.constant.ModelingAppVersionConstant" %>
<template:include ref="default.simple">
	<template:replace name="head">
		<link type="text/css" rel="stylesheet"
			  href="${ LUI_ContextPath }/sys/modeling/base/resources/css/monitor.css?s_cache=${LUI_Cache}"/>
		<style>
			.lui_list_body_frame br {
				display: block !important;
			}
		</style>
		<script type="text/javascript">
			seajs.use(['theme!form']);
		</script>
	</template:replace>
	<template:replace name="body">
		<%--spa初始化，影响日期的默认值设置--%>
		<div data-lui-type="lui/spa!Spa" style="display: none;">
			<script type="text/config">
				{"groups": "[ ['fdCreateTime'] ]" }
			</script>
		</div>
		<div style="margin:5px 10px;">
			<!-- 筛选 -->
			<list:criteria id="operationCri" channel="operation">
				<list:cri-ref title="${lfn:message('sys-modeling-base:modelingOperLog.fdOperItemName') }" key="fdOperItemName" ref="criterion.sys.docSubject"/>
				<list:cri-criterion title="${ lfn:message('sys-modeling-base:modelingOperLog.fdAppName') }" key="fdAppId" channel="operation">
					<list:box-select>
						<list:item-select cfg-defaultValue="">
							<ui:source type="Static">
								<%=SysModelingUtil.buildCriteria("modelingApplicationService",
										"modelingApplication.fdId,modelingApplication.fdAppName",
										"left join modelingApplication.fdVersion fdVersion",
										"(fdVersion is null or fdVersion.fdStatus='" + ModelingAppVersionConstant.STATUS_DRAFT + "' " +
												"or fdVersion.fdStatus='" + ModelingAppVersionConstant.STATUS_CURRENT + "')", null)%>
							</ui:source>
						</list:item-select>
					</list:box-select>
				</list:cri-criterion>
				<list:cri-auto modelName="com.landray.kmss.sys.modeling.base.model.ModelingOperLog"
							   property="fdCreateTime" expand="true" cfg-defaultValue="1"/>
			</list:criteria>
			<!-- 操作 -->
			<div class="lui_list_operation">
				<!-- 排序 -->
				<div class="lui_list_operation_sort_btn">
					<div class="lui_list_operation_order_text">
						${ lfn:message('list.orderType') }：
					</div>
					<div class="lui_list_operation_sort_toolbar">
						<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" channel="operation">
							<list:sort property="modelingOperLog.fdCreateTime"
									   text="${lfn:message('sys-modeling-base:modelingOperLog.fdCreateTime')}"
									   group="sort.list" value="down" channel="operation"/>
						</ui:toolbar>
					</div>
				</div>
				<div class="lui_list_operation_page_top">
					<list:paging layout="sys.ui.paging.top" channel="operation"/>
				</div>
				<div style="float:right">
					<div style="display: inline-block;vertical-align: middle;">
						<ui:toolbar count="3" channel="operation">
							<kmss:auth requestURL="/sys/modeling/base/monitor.do?method=exportOperLogData">
								<!--导出数据-->
								<ui:button text="${lfn:message('sys-modeling-base:btn.exportData')}"
										   onclick="exportOperLogData()" order="5" channel="operation"/>
							</kmss:auth>
						</ui:toolbar>
					</div>
				</div>
			</div>
			<ui:fixed elem=".lui_list_operation" />
			<!-- 列表 -->
			<list:listview id="operationLv" channel="operation" cfg-needMinHeight="false">
				<ui:source type="AjaxJson">
					{url:appendQueryParameter('/sys/modeling/base/monitor.do?method=operLogData')}
				</ui:source>
				<list:colTable isDefault="false" name="columntable" channel="operation">
					<list:col-serial></list:col-serial>
					<list:col-auto props="fdCreator.name;fdIp;fdAppName;fdOperItemName;subject;fdCreateTime;oper"/>
				</list:colTable>
			</list:listview>
			<!-- 翻页 -->
			<list:paging channel="operation"/>
		</div>
		<script>
			var listOption = {
				contextPath: '${LUI_ContextPath}',
				// modelName: 'com.landray.kmss.sys.modeling.base.model.ModelingOperLog',
				// templateName: '',
				// basePath: '/sys/modeling/base/modelingOperLog.do',
				canDelete: '${canDelete}',
				mode: '',
				templateService: '',
				templateAlert: '${lfn:message("sys-modeling:treeModel.alert.templateAlert")}',
				customOpts: {

					____fork__: 0
				},
				lang: {
					noSelect: '${lfn:message("page.noSelect")}',
					comfirmDelete: '${lfn:message("page.comfirmDelete")}'
				}

			};
			Com_IncludeFile("list.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/", 'js', true);

			function exportOperLogData() {
				seajs.use('lui/dialog', function (dialog) {
					if(LUI('operationLv').table._data.page.totalSize <=0){
						let title = '<bean:message key="sys-modeling-base:page.comfirmExport.fail.nodata"/>';
						dialog.alert(title);
					}else{
						let title = '<bean:message key="sys-modeling-base:page.comfirmExport.part1"/>';
						if (LUI('operationLv').table._data.page.totalSize > 5000) {
							title += '<bean:message key="sys-modeling-base:page.comfirmExport.part2"/>';
						}
						title += '<bean:message key="sys-modeling-base:page.comfirmExport.part3"/>';
						dialog.confirm(title, function (value) {
							if (value == true) {
								window.__exportLoading = dialog.loading();
								let listview = LUI('operationLv');
								let url = "${LUI_ContextPath}" + listview.table._resolveUrls(listview.cacheEvt);
								url = url.replace("method=operLogData", "method=exportOperLogData");
								if ($('#exportDownloadIframe').length > 0) {
									$('#exportDownloadIframe')[0].src = url;
								} else {
									var elemIF = document.createElement("iframe");
									elemIF.id = "exportDownloadIframe";
									elemIF.src = url;
									elemIF.style.display = "none";
									document.body.appendChild(elemIF);
								}
								setDownloadTimer();
							}
						});
					}
				});
			}

			var downloadTimer;

			function setDownloadTimer() {
				downloadTimer = setInterval(function () {
					var iframe = document.getElementById('exportDownloadIframe');
					var iframeDoc = iframe.contentDocument || iframe.contentWindow.document;
					if (iframeDoc.readyState == 'complete' || iframeDoc.readyState == 'interactive') {
						//隐藏loading
						setTimeout("downloadComplete()", 500);
					}
				}, 1000);
			}

			function downloadComplete() {
				var iframe = document.getElementById('exportDownloadIframe');
				var iframeDoc = iframe.contentDocument || iframe.contentWindow.document;
				window.__exportLoading.hide();
				if (!iframeDoc.body.innerHTML) {
					//开始下载则显示成功弹框
					seajs.use('lui/dialog', function (dialog) {
						dialog.success({
							status: true,
							title: '<bean:message key="sys-modeling-base:page.exportFinished"/>'
						});
					});
				}
				clearInterval(downloadTimer);
			}

			//刷新iframe高度
			$(function () {
				seajs.use(["lui/jquery", "sys/ui/js/dialog", "lui/topic"], function ($, dialog, topic) {
					topic.channel("operation").subscribe("list.loaded", setIframeHeight);
				});
			})

			function setIframeHeight() {
				var bodyHeight = $(document.body).outerHeight(true);
				$("body", parent.document).find('#operlogIframe').animate({
					height: bodyHeight
				}, "fast");
			}

		</script>
	</template:replace>
</template:include>