<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService,com.landray.kmss.sys.organization.model.SysOrgElement" %>
<%@ page import="com.landray.kmss.sys.attend.forms.SysAttendSynDingBakForm,com.landray.kmss.sys.attend.model.SysAttendCategory" %>
<%@ page import="com.landray.kmss.sys.attend.service.ISysAttendCategoryService" %>
<%@ page import="com.landray.kmss.sys.attend.util.CategoryUtil" %>

<template:include ref="default.view">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-attend:module.sys.attend') }"></c:out>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="1">
			<ui:button text="${lfn:message('button.close')}" order="1" onclick="Com_CloseWindow();"></ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav">
			<ui:menu-item text="${lfn:message('home.home') }" icon="lui_icon_s_home"></ui:menu-item>
			<ui:menu-item text="${lfn:message('sys-attend:module.sys.attend') }"></ui:menu-item>
			<ui:menu-item text="${lfn:message('sys-attend:table.sysAttendSynDing') }"></ui:menu-item>
		</ui:menu>
	</template:replace>
	<template:replace name="content">
		<%
			ISysOrgCoreService sysOrgCoreService=(ISysOrgCoreService) SpringBeanUtil.getBean("sysOrgCoreService");
			ISysAttendCategoryService sysAttendCategoryService=(ISysAttendCategoryService) SpringBeanUtil.getBean("sysAttendCategoryService");
		    SysAttendSynDingBakForm main = (SysAttendSynDingBakForm) request.getAttribute("sysAttendSynDingBakForm");
		    if(main!=null){
		    	SysOrgElement fdPerson = sysOrgCoreService.findByPrimaryKey(main.getFdPersonId());
		    	pageContext.setAttribute("fdPerson", fdPerson);
		    	String cateId=sysAttendCategoryService.getAttendCategory(fdPerson);

				SysAttendCategory category = CategoryUtil.getCategoryById(cateId);
				if(category==null) {
					category =(SysAttendCategory) sysAttendCategoryService
							.findByPrimaryKey(cateId);
				}
		    	pageContext.setAttribute("categoryName", category.getFdName());
		    }
		%>
		<div class="lui_form_title_frame">
			<div class="lui_form_subject">
				${ lfn:message('sys-attend:table.sysAttendSynDing') }
			</div>
			<div class="lui_form_baseinfo"></div>
		</div>
		<div class="lui_form_content_frame">
			<table class="tb_normal" width="100%">
				<tr>
					<td class="td_normal_title" width="15%">
						${lfn:message('sys-attend:sysAttendCategory.attend.fdName') }
					</td>
					<td width="85%" colspan="3">
						<c:out value="${categoryName }" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">
						${lfn:message('sys-attend:sysAttendMain.docCreator') }
					</td>
					<td width="35%">
						<c:out value="${fdPerson.getFdName() }"></c:out>
					</td>
					<td class="td_normal_title" width="15%">
						${lfn:message('sys-attend:sysAttendMain.export.dept') }
					</td>
					<td width="35%">
						<c:out value="${fdPerson.getFdParent().getFdName() }"></c:out>
					</td>
				</tr>
				<tr>
					<c:set var="hasLocation" value="${not empty sysAttendSynDingBakForm.fdSourceType}"></c:set>
					<td class="td_normal_title" width="15%">
						${lfn:message('sys-attend:sysAttendMain.docCreateTime') }
					</td>
					<td <c:if test="${!hasLocation }">colspan="3"</c:if>>
						<kmss:showDate value="${sysAttendSynDingBakForm.fdUserCheckTime }" type="datetime"></kmss:showDate>
					</td>
					<c:if test="${hasLocation }">
						<td class="td_normal_title" width="15%">
							${lfn:message('sys-attend:sysAttendCategoryRule.fdMode') }
						</td>
						<td width="35%">
							<sunbor:enumsShow value="${sysAttendSynDingBakForm.fdSourceType}" enumsType="sysAttendMain_fdSourceType" />
						</td>
					</c:if>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">
						${lfn:message('sys-attend:sysAttendMain.fdLocation') }
					</td>
					<td width="85%" colspan="3">
						<c:choose>
					        <c:when test="${sysAttendSynDingBakForm.fdLocationMethod=='MAP'}">
					            <%@ taglib uri="/WEB-INF/KmssConfig/sys/attend/map.tld" prefix="map"%>
								<c:set var="fdLocationCoordinate" value="${sysAttendSynDingBakForm.fdUserLatitude}${','}${sysAttendSynDingBakForm.fdUserLongitude}"/>
								<map:location propertyName="fdLocation" nameValue="${sysAttendSynDingBakForm.fdUserAddress }"
									propertyCoordinate="fdLocationCoordinate" coordinateValue="${fdLocationCoordinate }" 
									showStatus="view"></map:location>
					        </c:when>
					        <c:otherwise>
								${sysAttendSynDingBakForm.fdUserAddress}
					        </c:otherwise>
					    </c:choose>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">
						${lfn:message('sys-attend:sysAttendMain.fdStatus') }
					</td>
					<td width="85%" colspan="3">
						<c:choose>
							<c:when test="${not empty sysAttendSynDingBakForm.fdInvalidRecordType}">${lfn:message('sys-attend:sysAttendMain.fdStatus.invalid') }</c:when>
							<c:when test="${sysAttendSynDingBakForm.fdTimeResult =='Normal' && sysAttendSynDingBakForm.fdLocationResult=='Outside'}">${lfn:message('sys-attend:sysAttendMain.outside') }</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${sysAttendSynDingBakForm.fdTimeResult=='Late' || sysAttendSynDingBakForm.fdTimeResult=='SeriousLate'  || sysAttendSynDingBakForm.fdTimeResult=='Absenteeism' }">${lfn:message('sys-attend:sysAttendMain.fdStatus.late') }</c:when>
									<c:when test="${sysAttendSynDingBakForm.fdTimeResult =='Early' }">${lfn:message('sys-attend:sysAttendMain.fdStatus.left') }</c:when>
									<c:when test="${sysAttendSynDingBakForm.fdTimeResult =='Normal' }">${lfn:message('sys-attend:sysAttendMain.fdStatus.ok') }</c:when>
									<c:when test="${sysAttendSynDingBakForm.fdTimeResult =='NotSigned' }">${lfn:message('sys-attend:sysAttendMain.fdStatus.unSign') }</c:when>
									<c:when test="${sysAttendSynDingBakForm.fdTimeResult =='Trip' }">${lfn:message('sys-attend:sysAttendMain.fdStatus.business') }</c:when>
									<c:when test="${sysAttendSynDingBakForm.fdTimeResult =='Leave' }">${lfn:message('sys-attend:sysAttendMain.fdStatus.askforleave') }</c:when>
									<c:when test="${sysAttendSynDingBakForm.fdTimeResult =='Outgoing' }">${lfn:message('sys-attend:sysAttendMain.fdStatus.outgoing') }</c:when>
									<c:otherwise></c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">
						${lfn:message('sys-attend:sysAttendMain.fdDesc') }
					</td>
					<td width="85%" colspan="3">
						<c:choose>
							<c:when test="${not empty sysAttendSynDingBakForm.fdInvalidRecordType}">${lfn:message('sys-attend:sysAttendSynDing.remarkDesc') }</c:when>
							<c:otherwise>
								${sysAttendSynDingBakForm.fdOutsideRemark}
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">
						${lfn:message('sys-attend:sysAttendMain.export.fdDeviceInfo') }
					</td>
					<td width="85%" colspan="3">
						<sunbor:enumsShow value="${sysAttendSynDingBakForm.fdSourceType}" enumsType="sysAttendMain_fdSourceType" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">
						${lfn:message('sys-attend:sysAttendMain.fdAppName') }
					</td>
					<td width="85%" colspan="3">
						<c:choose>
							<c:when test="${sysAttendSynDingBakForm.fdAppName=='dingding' }">
								${ lfn:message('sys-attend:sysAttendMain.fdAppName.dingDing') }
							</c:when>
							<c:when test="${sysAttendSynDingBakForm.fdAppName=='qywx' }">
								${ lfn:message('sys-attend:sysAttendMain.fdAppName.qywx') }
							</c:when>
							<c:when test="${empty sysAttendSynDingBakForm.fdAppName }">
								${ lfn:message('sys-attend:sysAttendMain.fdAppName.ekp') }
							</c:when>
							<c:otherwise>
								${sysAttendSynDingBakForm.fdAppName}
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</table>
		</div>
	</template:replace>
</template:include>