<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/mobile/jsp/ajax-accept.jsp" %>
<template:include ref="mobile.edit" compatibleMode="true">
    <template:replace name="head">
    <script>
    Com_IncludeFile("data.css","${LUI_ContextPath}/sys/lbpmservice/mobile/lbpm_summary_approval/css/","css",true);
    Com_IncludeFile("dialog.css","${LUI_ContextPath}/sys/lbpmservice/mobile/lbpm_summary_approval/css/","css",true);
    </script>
    <script>
    require(["dijit/registry","dojo/topic","dojo/query"], function(registry,topic,query) {
	    window.submit = function(evt){
	    	var processId = query("[name='processId']")[0].value;
	    	var opType = query("[name='opType']")[0].value;
			var usageWdt = registry.byId("fdUsageContent");
			var result = usageWdt._validation.validateElement(usageWdt);
			if(result){
				topic.publish("/mui/lbpmSummary/approve",{"processId":processId,"opType":opType});
			}
		}
		
		window.closeDialog = function(){
			topic.publish("/mui/lbpmSummary/destoryDialog");
		}
    });
    </script>
    </template:replace>
    <template:replace name="title">
    	<c:out value="${requestScope.templateName}"></c:out>
    </template:replace>
    <template:replace name="content">
		<%-- 此处为内容 --%>
		<div data-dojo-block="content" style="overflow: hidden;">
			<div class="lbpmSummaryNodeName">${requestScope.fdNodeFactName }</div>
			<div data-dojo-type="sys/lbpmservice/mobile/lbpm_summary_approval/LbpmSummaryView"  class="scrollView">
				<ul id="lbpmSummaryList" data-dojo-type='sys/lbpmservice/mobile/lbpm_summary_approval/LbpmSummaryList' data-dojo-props='isNotHandler:"${requestScope.isNotHandler }",templateName:"${requestScope.templateName}"' data-dojo-mixins="sys/lbpmservice/mobile/lbpm_summary_approval/LbpmSummaryItemListMixin"></ul>
			</div>
			<div class='muiLbpmSummarySelection' data-dojo-props='nodeType:"${requestScope.fdNodeType }",isBatchApprove:"${requestScope.isBatchApprove }",isBatchReject:"${requestScope.isBatchReject }"' id='lbpmSummarySelection' data-dojo-type='sys/lbpmservice/mobile/lbpm_summary_approval/LbpmSummarySelection'>
				
			</div>
		</div>
    </template:replace>
</template:include>
