<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="sysEditionForm" value="${requestScope[param.formName].editionForm}" />
<c:set var="order" value="${empty param.order ? '10' : param.order}"/>
<c:set var="disable" value="${empty param.disable ? 'false' : param.disable}"/>
<ui:content title="${lfn:message('sys-edition:sysEditionMain.tab.label') }" cfg-order="${order}" cfg-disable="${disable}">
		<c:set var="editionQuery"
				value="/sys/edition/sys_edition_main/sysEditionMain.do?method=newVersion&mainVersion=${sysEditionForm.mainVersion}&auxiVersion=${sysEditionForm.auxiVersion}&fdModelName=${sysEditionForm.fdModelName}&fdModelId=${sysEditionForm.fdModelId}" />
		<c:if test="${sysEditionForm.enabledNewVersion=='true'}">
			<kmss:auth requestURL="${editionQuery}" requestMethod="GET">				
				<script language="JavaScript">
				function edition_SelectVersion(){
					seajs.use(
								[ 'lui/dialog' ],
								function(dialog) {
								
									dialog
										.iframe(
											'/sys/edition/sys_edition_main/sysEditionMain.do?method=newVersion&mainVersion=${sysEditionForm.mainVersion}&auxiVersion=${sysEditionForm.auxiVersion}&fdModelName=${sysEditionForm.fdModelName}&fdModelId=${sysEditionForm.fdModelId}',
											"${lfn:message('sys-edition:sysEditionMain.button.newedition')}", function() {
												var version = top.returnValue;
												if (version != null) {
													var href = assemblyHref();
													href = href + "&version=" + version;
													window.location.href = href;
												}
											}, {
												width : '477',
												height : '250'
											});
						});
				}

				function assemblyHref() {
					var href = window.location.href;
					var reg = /method=\w*/;
					href = href.replace(reg, "method=newEdition");
					var reg1 = /fdId/;
					href = href.replace(reg1, "originId");
					return href;
				}
			</script>
				<c:set var="toolbarOrder" value="2"></c:set>
				<c:if test="${not empty param && not empty param.toolbarOrder}">
					<c:set var="toolbarOrder" value="${param.toolbarOrder}"></c:set>
				</c:if>
				<ui:button parentId="toolbar" text="${lfn:message('sys-edition:sysEditionMain.button.newedition') }" 
					onclick="edition_SelectVersion();" order="${toolbarOrder}">
				</ui:button>
			</kmss:auth>
		</c:if>
		<ui:event event="show"> 
			var url = '<%= request.getContextPath() %>/sys/edition/sys_edition_main/sysEditionMain_list.jsp?fdModelName=${sysEditionForm.fdModelName}&fdModelId=${sysEditionForm.fdModelId}&currentVer=${sysEditionForm.currentVersion}';
			document.getElementById("editionContent").setAttribute("src",url);
		</ui:event>
		<iframe id="editionContent" width=100% height="1000" frameborder=0 scrolling=no>
		</iframe>
</ui:content>
