<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.km.smissive.util.KmSmissiveConfigUtil"%>
<%@page import="com.landray.kmss.km.smissive.forms.KmSmissiveMainForm"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@page import="com.landray.kmss.sys.number.util.NumberResourceUtil"%>
<template:replace name="head">
	<link rel="Stylesheet" href="${LUI_ContextPath}/km/smissive/resource/css/att.css" />
</template:replace>
<template:replace name="title">
	<c:out value="${ kmSmissiveMainForm.docSubject } - ${ lfn:message('km-smissive:module.km.smissive') }"></c:out>
</template:replace>
<template:replace name="toolbar">
	<!--软删除配置  -->
	<c:if test="${kmSmissiveMainForm.docDeleteFlag ==1}">
		<ui:toolbar id="toolbar" style="display:none;" count="6"></ui:toolbar>
	</c:if>
	<c:if test="${kmSmissiveMainForm.docDeleteFlag !=1}">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="6"
			var-navwidth="90%" style="display:none;">
			<%-- 文件编号附加选项 --%>
			<%
				if (NumberResourceUtil
										.isModuleNumberEnable("com.landray.kmss.km.smissive.model.KmSmissiveMain")) {
			%>
			<c:if
				test="${kmSmissiveMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.modifyDocNum =='true' and  not empty fdNoId}">
				<ui:button
					text="${lfn:message('km-smissive:kmSmissive.modifyDocNum') }"
					order="2" onclick="generateFileNum();">
				</ui:button>
			</c:if>
			<%
				}
			%>
			<!-- 切换阅读 -->
			<%
				String bigVersion = JgWebOffice.getJGBigVersion();
				if (null != bigVersion && bigVersion.equals(JgWebOffice.JG_OCX_BIG_VERSION_2015)) {
					//2015版本控件 
					request.setAttribute("isIE", true);
				} else {
					if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > -1) {
						request.setAttribute("isIE", true);
					} else if (request.getHeader("User-Agent").toUpperCase().indexOf("TRIDENT") > -1) {
						request.setAttribute("isIE", true);
					} else {
						request.setAttribute("isIE", false);
					}
				}

				request.setAttribute("showAllPage",
						"allpage".equals(KmSmissiveConfigUtil.getLoadType()) ? true : false);
			%>
			<c:if test="${kmSmissiveMainForm.docStatus!='10'}">
				<kmss:auth
					requestURL="/km/smissive/km_smissive_main/kmSmissiveMain.do?method=print&fdId=${param.fdId}"
					requestMethod="GET">
					<ui:button
						text="${lfn:message('km-smissive:kmSmissive.button.print') }"
						order="4"
						onclick="Com_OpenWindow('kmSmissiveMain.do?method=print&fdId=${param.fdId}','_blank');">
					</ui:button>
				</kmss:auth>
			</c:if>
			<c:if test="${kmSmissiveMainForm.docStatusFirstDigit > '0' }">
				<kmss:auth
					requestURL="/km/smissive/km_smissive_main/kmSmissiveMain.do?method=edit&fdId=${param.fdId}"
					requestMethod="GET">
					<ui:button text="${lfn:message('button.edit') }" order="4"
						onclick="Com_OpenWindow('kmSmissiveMain.do?method=edit&fdId=${param.fdId}','_self');">
					</ui:button>
				</kmss:auth>
			</c:if>
			<kmss:auth
				requestURL="/km/smissive/km_smissive_main/kmSmissiveMain.do?method=edit&forward=modifyIssuer&fdId=${param.fdId}"
				requestMethod="GET">
				<ui:button
					text="${lfn:message('km-smissive:smissive.button.changeissuer') }"
					order="4"
					onclick="fn_dialog('${LUI_ContextPath }/km/smissive/km_smissive_main/kmSmissiveMain.do?method=edit&forward=modifyIssuer&fdId=${param.fdId}&aotuWidth=no');">
				</ui:button>
			</kmss:auth>
			<c:if test="${kmSmissiveMainForm.docStatus =='20' }">
				<kmss:auth
					requestURL="/km/smissive/km_smissive_main/kmSmissiveMain.do?method=edit&fdId=${param.fdId}"
					requestMethod="GET">
					<!--用于标识显示附件编辑还是查看页面 -->
					<c:set var="editStatus" value="true" scope="request"/>
				</kmss:auth>
				<c:if
					test="${kmSmissiveMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.editDocContent =='true'}">
					<c:set var="editStatus" value="true" scope="request"/>
				</c:if>
				<%--editStatus不为true，说明当前正文不处于编辑状态，如果当前审批人具有编号附件选项，此时需要将editStatus设置为true，且为只读状态，当用户点击文件编号按钮时再解除保护，编号完再设为保护状态--%>
				<c:if
					test="${!editStatus eq true and kmSmissiveMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.modifyDocNum eq true}">
					<c:set var="editStatus" value="true" scope="request"/>
					<c:set var="isReadOnly" value="true" scope="request"/>
				</c:if>
			</c:if>
			<kmss:auth
				requestURL="/km/smissive/km_smissive_main/kmSmissiveMain.do?method=delete&fdId=${param.fdId}"
				requestMethod="GET">
				<ui:button text="${lfn:message('button.delete') }" order="4"
					onclick="Delete('kmSmissiveMain.do?method=delete&fdId=${param.fdId}');">
				</ui:button>
			</kmss:auth>
			<ui:button text="${ lfn:message('button.close') }" order="5"
				onclick="Com_CloseWindow()">
			</ui:button>
		</ui:toolbar>
	</c:if>
</template:replace>
<template:replace name="path">
	<ui:combin ref="menu.path.simplecategory">
		<ui:varParams
			moduleTitle="${ lfn:message('km-smissive:table.kmSmissiveMain') }"
			modulePath="/km/smissive/"
			modelName="com.landray.kmss.km.smissive.model.KmSmissiveTemplate"
			autoFetch="false" href="/km/smissive/"
			categoryId="${kmSmissiveMainForm.fdTemplateId}" />
	</ui:combin>
</template:replace>

