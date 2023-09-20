<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/km/archives/km_archives_main/kmArchivesMain_view_include.jsp"%>

<template:replace name="head">
	<style type="text/css">
		.lui_paragraph_title {
			font-size: 15px;
			color: #15a4fa;
			padding: 15px 0px 5px 0px;
		}
		.lui_paragraph_title span {
			display: inline-block;
			margin: -2px 5px 0px 0px;
		}
		.barCodeImg {
			position: absolute;
			right: 20px;
			max-width:450px;
			height: 80px;
		}
	</style>
</template:replace>
<template:replace name="title">
	<c:out value="${kmArchivesMainForm.docSubject} - " />
	<c:out value="${ lfn:message('km-archives:table.kmArchivesMain') }" />
</template:replace>
<template:replace name="toolbar">
	<script>
		seajs.use([ 'lui/dialog' ],function(dialog) {
			window.deleteDoc = function(delUrl) {
			var delUrl = '<c:url value="/km/archives/km_archives_main/kmArchivesMain.do"/>?method=delete&fdId=${param.fdId}';
				dialog.iframe('/sys/edition/import/doc_delete_iframe.jsp?fdModelName=com.landray.kmss.km.archives.model.KmArchivesMain&fdModelId=${param.fdId}',
					"<bean:message key='ui.dialog.operation.title' bundle='sys-ui'/>",
					function(url) {
						if (url) {
							Com_OpenWindow(url,'_self');
							}
						 }, {
							width : 400,
							height : 170,
							params : {
								url : delUrl,
								type : 'GET'
							}
					});
				};
		});
		var docNumber = '${kmArchivesMainForm.docNumber}';
		if (docNumber != null && docNumber != '') {
			seajs.use([ 'lui/jquery', 'lui/barcode' ], function($, barcode) {
				$(document).ready(function() {
					try {
						barcode.Barcode("#imgcode", docNumber, {
							height : 50
						});
					} catch (e) {
						console.error("档案编号含有中文或者特殊字符，具体信息：", e);
						console.warn("档案编号含有中文或者特殊字符，导致无法展示条形码！");
						$("#barCodeImgBox").hide();
					}
				});
				
			});
		}
		window.addBorrow = function() {
			Com_OpenWindow('${LUI_ContextPath}/km/archives/km_archives_borrow/kmArchivesBorrow.do?method=add&fdMainId=${JsParam.fdId}');
		};
		window.returnBack = function() {
			seajs.use([ 'lui/jquery', 'lui/dialog' ],function($, dialog) {
						var _url = Com_Parameter.ContextPath + 'km/archives/km_archives_details/kmArchivesDetails.do?method=returnBack';
						$.ajax({
							type : 'post',
							url : _url,
							data : {
								fdArchId : '${kmArchivesMainForm.fdId}'
							},
							aysnc : true,
							success : function(data) {
								var _data = eval(data);
								if (_data.length == 0) {
									dialog
											.alert("${ lfn:message('km-archives:kmArchivesMain.no.borrow') }");
								} else {
									dialog.confirm("${ lfn:message('km-archives:kmArchivesMain.return.confirm') }",
										function(isOk) {
											if (isOk) {
												var delUrl = Com_Parameter.ContextPath + 'km/archives/km_archives_details/kmArchivesDetails.do?method=comfirmReturnBack&fdId=' + _data[0].fdId;
												Com_OpenWindow(delUrl,'_self');
											}
										});
								}
						}
				});
			});
		}
	</script>
	<!-- 软删除部署 -->
	<c:if test="${kmArchivesMainForm.docDeleteFlag ==1}">
		<ui:toolbar id="toolbar" style="display:none;"></ui:toolbar>
	</c:if>
	<c:if test="${kmArchivesMainForm.docDeleteFlag !=1}">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5"
			var-navwidth="90%" style="display:none;">
			<!-- 借阅  -->
			<c:if test="${isValidity and canBorrow}">
				<ui:button text="${ lfn:message('km-archives:button.borrow') }" order="2" onclick="addBorrow();">
				</ui:button>
			</c:if>
			<!-- 归还 -->
			<c:if test="${isBorrowed }">
				<ui:button text="${ lfn:message('km-archives:button.return') }"
					order="1" onclick="returnBack();" />
			</c:if>
			<c:if test="${ kmArchivesMainForm.docStatus=='10' || kmArchivesMainForm.docStatus=='11' || kmArchivesMainForm.docStatus=='20' }">
				<!--edit-->
				<kmss:auth requestURL="/km/archives/km_archives_main/kmArchivesMain.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
					<ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('kmArchivesMain.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
				</kmss:auth>
			</c:if>
			<!--delete-->
			<kmss:auth requestURL="/km/archives/km_archives_main/kmArchivesMain.do?method=delete&fdId=${param.fdId}">
				<ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('kmArchivesMain.do?method=delete&fdId=${param.fdId}');" order="4" />
			</kmss:auth>
			<ui:button text="${lfn:message('button.close')}" order="7" onclick="Com_CloseWindow();" />
		</ui:toolbar>
	</c:if>
</template:replace>
<template:replace name="path">
	<ui:menu layout="sys.ui.menu.nav">
		<ui:menu-item text="${ lfn:message('home.home') }"
			icon="lui_icon_s_home" href="/" target="_self" />
		<ui:menu-item
			text="${ lfn:message('km-archives:table.kmArchivesMain') }"
			href="/km/archives/" target="_self" />
		<ui:menu-source autoFetch="false" target="_self"
			href="/km/archives/index.jsp#j_path=%2FallArchives&mydoc=all&cri.q=docTemplate%3A${kmArchivesMainForm.docTemplateId}">
			<ui:source type="AjaxJson">
				{"url":"/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=path&modelName=com.landray.kmss.km.archives.model.KmArchivesCategory&categoryId=${kmArchivesMainForm.docTemplateId}&currId=!{value}&authType=2&pAdmin=!{pAdmin}"}
			</ui:source>
		</ui:menu-source>
	</ui:menu>
</template:replace>
