<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessOperationService"%>
<%@page import="com.landray.kmss.sys.lbpmservice.support.service.spring.InternalLbpmProcessForm"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/template.tld" prefix="template"%>

<c:set var="sysWfBusinessForm" value="${requestScope[param.formName]}" scope="page" />
<c:set var="lbpmProcessForm" value="${sysWfBusinessForm.sysWfBusinessForm.internalForm}" scope="page" />
<c:set var="docStatus" value="${(empty param.docStatus) ? (sysWfBusinessForm.docStatus) : param.docStatus}" scope="page" />
<c:set var="lbpmViewName" value="${(empty param.viewName) ? 'lbpmView' : (param.viewName)}"/>
<c:set var="lbpmBtnId" value="${(empty param.tabbaId) ? '' : (param.tabbaId)}"/>

<!-- 群协作 -->
<c:if test="${docStatus > '10' }">
	<kmss:ifModuleExist path="/km/cogroup/">
		<c:import url="/km/cogroup/mobile/include/view.jsp" charEncoding="UTF-8">
			<c:param name="modelName" value="${sysWfBusinessForm.modelClass.name}"></c:param>
		</c:import>
	</kmss:ifModuleExist>
</c:if>

<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"' id="${lbpmBtnId}">
  <li data-dojo-type="mui/back/BackButton" data-dojo-props="icon1:''"><bean:message  bundle="sys-mobile"  key="mui.button.back" /></li>
  <c:choose>
	  <c:when test="${(sysWfBusinessForm.sysWfBusinessForm.fdProcessStatus==null || sysWfBusinessForm.sysWfBusinessForm.fdProcessStatus=='') 
	  			&& lbpmProcessForm.fdIsError != 'true'}">
			<%
				ILbpmProcessOperationService lbpmProcessOperationService = 
					(ILbpmProcessOperationService)SpringBeanUtil.getBean("lbpmProcessService");
				InternalLbpmProcessForm form=(InternalLbpmProcessForm)pageContext.getAttribute("lbpmProcessForm");
				JSONArray jsonArr = JSONArray.fromObject(form.getMobileDraferReviewJs());
				request.setAttribute("_drafterJs", jsonArr.toString());
				jsonArr = JSONArray.fromObject(form.getMobileHistoryReviewJs());
				request.setAttribute("_historyJs", jsonArr.toString());
				jsonArr = JSONArray.fromObject(form.getMobileBranchAdminReviewJs());
				request.setAttribute("_branchAdminJs", jsonArr.toString());
				String processId = request.getParameter("fdId");
				request.setAttribute("_operationCfg", lbpmProcessOperationService.getOperationList(processId).toString());
				request.setAttribute("_nodeCfg", lbpmProcessOperationService.getCurrentNodesInfo(processId).toString());
			%>
			<script type="text/javascript">
				var LBPM_CurOperationCfg = ${_operationCfg};
				var LBPM_CurNodeCfg =  ${_nodeCfg};
				var LBPM_DrafterJs = ${_drafterJs};
				var LBPM_HistoryJs =  ${_historyJs};
				var LBPM_BranchAdminJs = ${_branchAdminJs};
			</script>
			<c:choose>
	  		<c:when test="${docStatus >= '20' && docStatus < '30' }">
				<li data-dojo-type="sys/lbpmservice/mobile/operation/ExtOperationButton"
		  			data-dojo-props='colSize:2,processId:"${sysWfBusinessForm.sysWfBusinessForm.fdProcessId}",
		  				modelName:"${sysWfBusinessForm.sysWfBusinessForm.fdModelName}",
		  				modelId:"${sysWfBusinessForm.sysWfBusinessForm.fdModelId}",
		  				handleView:"${lbpmViewName}"'>
		  		</li>
			</c:when>
			<c:when test="${docStatus < '20' && docStatus >= '10' && not empty param.editUrl }">
		  		<c:if test="${ sysWfBusinessForm.sysWfBusinessForm.fdIsHander == 'true'}">
					<kmss:auth requestURL="${param.editUrl }">
						<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit" 
							data-dojo-props='colSize:2,href:"${param.editUrl }",icon1:"mui mui-create"'><bean:message key="button.edit"/></li>
					</kmss:auth>
		  		</c:if>
			</c:when>
			<c:otherwise>
				 <li data-dojo-type="mui/tabbar/TabBarButton" 
			  		data-dojo-props='colSize:2'></li>
			</c:otherwise>
			</c:choose>
	  </c:when>
	  <c:otherwise>
		  <li data-dojo-type="mui/tabbar/TabBarButton" 
		  		data-dojo-props='colSize:2'></li>
	  </c:otherwise>
   </c:choose>
   <li data-dojo-type="mui/tabbar/TabBarButtonGroup" data-dojo-props="icon1:'',label:'<bean:message  bundle="sys-mobile"  key="mui.button.more" />'">
   		<template:block name="group">
   			<div data-dojo-type="mui/back/HomeButton"></div>
    	</template:block>
    </li>
</ul>
<!-- 用于记录当前的view，用于返回使用 -->
<div data-dojo-type="sys/lbpmservice/mobile/common/_RecordBackToMixin" style="display: none"></div>