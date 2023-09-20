<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.km.archives.service.IKmArchivesMainService"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDictModel"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDataDict"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<template:include ref="default.simple">
	<template:replace name="body">
		<script type="text/javascript">
			seajs.use(['theme!list']);	
		</script>
		<div>
			<ui:tabpanel layout="sys.ui.tabpanel.list">
			<c:choose>
				<c:when test="${'myApproval' eq param.type}">
					<%-- 待我审的 --%>
					<ui:content title="${lfn:message('list.approval')}${lfn:message('km-archives:py.DangAnJiLu')}">
						<ui:iframe cfg-takeHash="false" src="${LUI_ContextPath }/km/archives/km_archives_main/myExamine.jsp?mydoc=approval"></ui:iframe>
					</ui:content>
					<ui:content title="${lfn:message('list.approval')}${lfn:message('km-archives:py.BorrowRecord')}">
						<ui:iframe cfg-takeHash="false" src="${LUI_ContextPath }/km/archives/km_archives_borrow/index.jsp?mydoc=approval"></ui:iframe>
					</ui:content>
					<ui:content title="${lfn:message('list.approval')}${lfn:message('km-archives:py.JianDingJiLu')}">
						<ui:iframe cfg-takeHash="false" src="${LUI_ContextPath }/km/archives/km_archives_appraise/index.jsp?mydoc=approval"></ui:iframe>
					</ui:content>
					<ui:content title="${lfn:message('list.approval')}${lfn:message('km-archives:py.XiaoHuiJiLu')}">
						<ui:iframe cfg-takeHash="false" src="${LUI_ContextPath }/km/archives/km_archives_destroy/index.jsp?mydoc=approval"></ui:iframe>
					</ui:content>
				</c:when>
				<c:when test="${'myApproved' eq param.type}">
					<%-- 我已审的 --%>
					<ui:content title="${lfn:message('list.approved')}${lfn:message('km-archives:py.DangAnJiLu')}">
						<ui:iframe cfg-takeHash="false" src="${LUI_ContextPath }/km/archives/km_archives_main/myExamine.jsp?mydoc=approved"></ui:iframe>
					</ui:content>
					<ui:content title="${lfn:message('list.approved')}${lfn:message('km-archives:py.BorrowRecord')}">
						<ui:iframe cfg-takeHash="false" src="${LUI_ContextPath }/km/archives/km_archives_borrow/index.jsp?mydoc=approved"></ui:iframe>
					</ui:content>
					<ui:content title="${lfn:message('list.approved')}${lfn:message('km-archives:py.JianDingJiLu')}">
						<ui:iframe cfg-takeHash="false" src="${LUI_ContextPath }/km/archives/km_archives_appraise/index.jsp?mydoc=approved"></ui:iframe>
					</ui:content>
					<ui:content title="${lfn:message('list.approved')}${lfn:message('km-archives:py.XiaoHuiJiLu')}">
						<ui:iframe cfg-takeHash="false" src="${LUI_ContextPath }/km/archives/km_archives_destroy/index.jsp?mydoc=approved"></ui:iframe>
					</ui:content>
				</c:when>
				<c:otherwise>
					<%-- 档案审核 --%>
					<ui:content title="${lfn:message('list.approval') }">
						<ui:iframe cfg-takeHash="false" src="${LUI_ContextPath }/km/archives/km_archives_main/myExamine.jsp?mydoc=approval"></ui:iframe>
					</ui:content>
					<ui:content title="${lfn:message('list.approved') }">
						<ui:iframe cfg-takeHash="false" src="${LUI_ContextPath }/km/archives/km_archives_main/myExamine.jsp?mydoc=approved"></ui:iframe>
					</ui:content>
				</c:otherwise>
			</c:choose>
		    </ui:tabpanel>
		    <c:set var="frameShowTop" scope="page" value="${(empty param.showTop) ? 'yes' : (param.showTop)}"/>
      		<c:if test="${frameShowTop=='yes' }">
		        <ui:top id="top"></ui:top>
		        <kmss:ifModuleExist path="/sys/help">
		          <c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
		        </kmss:ifModuleExist>
		      </c:if>
	  </div>
	  
	</template:replace> 
</template:include>