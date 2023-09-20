<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessOperationService"%>
<%@page import="com.landray.kmss.sys.lbpmservice.support.service.spring.InternalLbpmProcessForm"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/template.tld" prefix="template"%>

<c:set var="sysWfBusinessForm" value="${requestScope[param.formName]}" scope="page" />
<c:set var="lbpmProcessForm" value="${sysWfBusinessForm.sysWfBusinessForm.internalForm}" scope="page" />
<c:set var="docStatus" value="${(empty param.docStatus) ? (sysWfBusinessForm.docStatus) : param.docStatus}" scope="page" />
<c:set var="lbpmViewName" value="${(empty param.viewName) ? 'lbpmView' : (param.viewName)}"/>
<c:set var="isAllowReview" value="${param.allowReview}"/>
<c:set var="lbpmBtnId" value="${(empty param.tabbaId) ? '' : (param.tabbaId)}"/>

<!-- 群协作 -->
<c:if test="${docStatus > '10' }">
	<kmss:ifModuleExist path="/km/cogroup/">
		<c:import url="/km/cogroup/mobile/include/view.jsp" charEncoding="UTF-8">
			<c:param name="modelName" value="${sysWfBusinessForm.modelClass.name}"></c:param>
		</c:import>
	</kmss:ifModuleExist>
</c:if>

<c:if test="${docStatus < '30' }">
	<%
		ILbpmProcessOperationService lbpmProcessOperationService = (ILbpmProcessOperationService)SpringBeanUtil.getBean("lbpmProcessService");
		String processId = request.getParameter("fdId");
		request.setAttribute("_operationCfg", lbpmProcessOperationService.getOperationList(processId).toString());
		request.setAttribute("_nodeCfg", lbpmProcessOperationService.getCurrentNodesInfo(processId).toString());
	%>
	<script type="text/javascript">
		var LBPM_CurOperationCfg = ${_operationCfg};
		var LBPM_CurNodeCfg =  ${_nodeCfg};
	</script>
	<%-- 按钮区域分组 --%>
	<div data-dojo-type="mui/tabbar/TabBarGroup" data-dojo-props='isFilterEmptyChild:true' fixed="bottom" id="${lbpmBtnId}">
		<c:if test="${isAllowReview}">
			<c:if test="${(sysWfBusinessForm.sysWfBusinessForm.fdProcessStatus==null 
				|| sysWfBusinessForm.sysWfBusinessForm.fdProcessStatus=='' || fn:contains(sysWfBusinessForm.sysWfBusinessForm.fdProcessStatus,'N')) 
	  			&& lbpmProcessForm.fdIsError != 'true'}">
	  			<c:choose>
		  		<c:when test="${docStatus >= '20' && docStatus < '30' }">
		  			<c:if test="${(!lbpmProcessForm.subFormMode && sysWfBusinessForm.sysWfBusinessForm.fdIsHander == 'true')||(lbpmProcessForm.subFormMode && lbpmProcessForm.haveTask)}">
						<ul data-dojo-type="sys/lbpmservice/mobile/workitem/LbpmTabBar"
							data-dojo-props='viewName:"${lbpmViewName}"'>
							<c:if test="${lbpmProcessForm.showSubBut}">
								<li data-dojo-type="sys/lbpmservice/mobile/workitem/SubFormSwitchButton">
								</li>
							</c:if>
						</ul>
					</c:if>
				</c:when>
				<c:when test="${docStatus < '20' && docStatus >= '10' && not empty param.editUrl }">
			  		<c:if test="${ sysWfBusinessForm.sysWfBusinessForm.fdIsHander == 'true'}">
			  			<ul data-dojo-type="mui/tabbar/TabBar" data-dojo-props='fill:"grid"'>
			  			<%--起草人草稿编辑操作 --%>
						<kmss:auth requestURL="${param.editUrl}">
							<li data-dojo-type="mui/tabbar/TabBarButton" 
								data-dojo-props="colSize:4,href:'${param.editUrl}'">
								<bean:message key="button.edit"/>
							</li>
						</kmss:auth>
						</ul>
			  		</c:if>
				</c:when>
				</c:choose>
			</c:if>
		</c:if>
        <ul data-dojo-type="mui/tabbar/TabBar" data-dojo-props='fill:"grid"' id="businessTabBar">
            <c:if test="${isAllowReview && docStatus > '10' && docStatus < '30'}">
                <c:if test="${(sysWfBusinessForm.sysWfBusinessForm.fdProcessStatus==null
                    || sysWfBusinessForm.sysWfBusinessForm.fdProcessStatus=='' || fn:contains(sysWfBusinessForm.sysWfBusinessForm.fdProcessStatus,'N'))
                    && lbpmProcessForm.fdIsError != 'true'}">
                        <c:if test="${sysWfBusinessForm.sysWfBusinessForm.fdIsDrafter == 'true'
                            || sysWfBusinessForm.sysWfBusinessForm.fdIsHistoryHandler == 'true'
                            || sysWfBusinessForm.sysWfBusinessForm.fdIsBranchAdmin == 'true'}">
                            <%
                                InternalLbpmProcessForm form=(InternalLbpmProcessForm)pageContext.getAttribute("lbpmProcessForm");
                                boolean hasOtherReviewJs = false;
                                JSONArray jsonArr = JSONArray.fromObject(form.getMobileDraferReviewJs());
                                request.setAttribute("_drafterJs", jsonArr.toString());
                                if (jsonArr.size()!=0) {
                                    hasOtherReviewJs = true;
                                }
                                jsonArr = JSONArray.fromObject(form.getMobileHistoryReviewJs());
                                request.setAttribute("_historyJs", jsonArr.toString());
                                if (jsonArr.size()!=0) {
                                    hasOtherReviewJs = true;
                                }
                                jsonArr = JSONArray.fromObject(form.getMobileBranchAdminReviewJs());
                                request.setAttribute("_branchAdminJs", jsonArr.toString());
                                if (jsonArr.size()!=0) {
                                    hasOtherReviewJs = true;
                                }
                                request.setAttribute("hasOtherReviewJs", hasOtherReviewJs);
                            %>
                                <script type="text/javascript">
                                    var LBPM_DrafterJs = ${_drafterJs};
                                    var LBPM_HistoryJs =  ${_historyJs};
                                    var LBPM_BranchAdminJs = ${_branchAdminJs};
                                    console.log(LBPM_BranchAdminJs)
                                </script>
                                <c:if test="${hasOtherReviewJs}">
                                    <li data-dojo-type="sys/lbpmservice/mobile/operation/OperationButton"
                                        data-dojo-props='processId:"${sysWfBusinessForm.sysWfBusinessForm.fdProcessId}",
                                            modelName:"${sysWfBusinessForm.sysWfBusinessForm.fdModelName}",
                                            modelId:"${sysWfBusinessForm.sysWfBusinessForm.fdModelId}"'>
                                    </li>
                                </c:if>
                        </c:if>
                </c:if>
            </c:if>
            <%-- 流程分发按钮区 --%>
            <c:if test="${sysWfBusinessForm.sysWfBusinessForm.fdIsHander != 'true'}">
                <%
                com.landray.kmss.sys.lbpmservice.support.service.spring.LbpmAssignServiceImp assignService =
                    (com.landray.kmss.sys.lbpmservice.support.service.spring.LbpmAssignServiceImp) SpringBeanUtil.getBean("lbpmAssignService");
                Object assignData = assignService.getAssignJsonData((String) request.getParameter("fdId"));
                if (assignData != null) {
                    JSONArray assignArray = (JSONArray) assignData;
                    if (!assignArray.isEmpty()) {
                        request.setAttribute("isAssignee", true);
                        request.setAttribute("canAssign", false);
                        JSONObject assignItem = assignArray.getJSONObject(0);
                        if (assignItem != null && assignItem.has("isCanAssign")) {
                            String isCanAssign = assignItem.getString("isCanAssign");
                            if ("true".equals(isCanAssign)) {
                                request.setAttribute("canAssign", true);
                            }
                        }
                    }
                }
                %>
                <c:if test="${isAssignee}">
                    <script type="text/javascript">
                    var LBPM_CanAssign = ${canAssign};
                    require(["dojo/ready","dijit/registry","dojo/query","dojo/_base/array"], function(ready, registry, query, array) {
                        ready(function() {
                            var isAssignee = false; // 是否为被分发人
                            var assignData = <%=assignData%>;
                            if (assignData != null && assignData.length > 0) {
                                isAssignee = true;

                                //因为加载顺序原因，导致lbpm未声明就去使用。
                                setTimeout(function(){
                                    lbpm.nowAssignItem = assignData[0];
                                }, 450);
                            }
                        });
                    });
                    </script>
                    <li data-dojo-type="sys/lbpmservice/mobile/operation/other/AssignOprButton"
                        data-dojo-props='processId:"${sysWfBusinessForm.sysWfBusinessForm.fdProcessId}",
                            modelName:"${sysWfBusinessForm.sysWfBusinessForm.fdModelName}",
                            modelId:"${sysWfBusinessForm.sysWfBusinessForm.fdModelId}",
                            operationType:0'>
                    </li>
                    <c:if test="${canAssign}">
                    <li data-dojo-type="sys/lbpmservice/mobile/operation/other/AssignOprButton"
                        data-dojo-props='processId:"${sysWfBusinessForm.sysWfBusinessForm.fdProcessId}",
                            modelName:"${sysWfBusinessForm.sysWfBusinessForm.fdModelName}",
                            modelId:"${sysWfBusinessForm.sysWfBusinessForm.fdModelId}",
                            operationType:1'>
                    </li>
                    </c:if>
                </c:if>
            </c:if>
            <%--流程过程中的业务操作按钮区域 --%>
            <template:block name="flowArea">
            </template:block>
            <c:if test="${sysWfBusinessForm.docStatus!='10' && sysWfBusinessForm.docStatus!='11' && lbpmProcessForm.showSubBut && !lbpmProcessForm.haveTask}">
                <li data-dojo-type="sys/lbpmservice/mobile/workitem/SubFormSwitchButton">
                </li>
            </c:if>
        </ul>
        <template:block name="modelingOperationsArea">
        </template:block>
	</div>
</c:if>
<c:if test="${docStatus>='30' }">
	<%-- 流程发布后的业务按钮区域--%>
	<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>
		<template:block name="publishArea">
		</template:block>
		<c:if test="${lbpmProcessForm.showSubBut}">
			<mui:cache-file name="sys-lbpm.css" cacheType="md5"/>
			<input type="hidden" id="sysWfBusinessForm.fdSubFormXML" name="sysWfBusinessForm.fdSubFormXML" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdSubFormXML}" />'>
			<li data-dojo-type="sys/lbpmservice/mobile/workitem/SubFormSwitchButton"
				data-dojo-props='nowSubFormId:"${sysWfBusinessForm.sysWfBusinessForm.fdSubFormId}"'>
			</li>
		</c:if>
	</ul>
    <template:block name="modelingOperationsPublishArea">
    </template:block>
</c:if>
<!-- 用于记录当前的view，用于返回使用 -->
<div data-dojo-type="sys/lbpmservice/mobile/common/_RecordBackToMixin" style="display: none"></div>

