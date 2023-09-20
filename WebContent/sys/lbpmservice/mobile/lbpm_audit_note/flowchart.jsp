<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="title">
		<c:out value="${kmReviewMainForm.docSubject}"></c:out>
	</template:replace>
	<template:replace name="head">
		<mui:cache-file name="sys-lbpm.css" cacheType="md5"/>
	</template:replace>
	<template:replace name="content">
<%@ page
	import="org.springframework.context.ApplicationContext,
		org.springframework.web.context.support.WebApplicationContextUtils,
		com.landray.kmss.util.StringUtil,
		com.landray.kmss.common.service.IBaseService,
		com.landray.kmss.common.model.IBaseModel,
		com.landray.kmss.sys.lbpm.engine.service.ProcessExecuteService,
		com.landray.kmss.sys.lbpm.engine.service.ProcessInstanceInfo,
		com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess,
		com.landray.kmss.sys.lbpmservice.support.service.spring.InternalLbpmProcessForm,
		com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm" 
%>
<%
	try{
		String processId = org.apache.commons.lang.StringEscapeUtils.escapeHtml(request.getParameter("processId"));
		if(StringUtil.isNull(processId)){
			processId = (String)request.getAttribute("processId");
		}
		if(StringUtil.isNotNull(processId)){
			ApplicationContext ctx = WebApplicationContextUtils
					.getRequiredWebApplicationContext(request.getSession()
					.getServletContext());
			ProcessExecuteService processExecuteService = (ProcessExecuteService)ctx.getBean("lbpmProcessExecuteService");
			ProcessInstanceInfo processInstanceInfo = processExecuteService.load(processId);
			if(processInstanceInfo==null){
				out.println("没有找到对应的流程实例："+processId);
				return;
			}
			LbpmProcess lbpmProcess=(LbpmProcess)processInstanceInfo.getProcessInstance();
			InternalLbpmProcessForm internalForm = new InternalLbpmProcessForm(
					processInstanceInfo);
			request.setAttribute("sysWfBusinessForm", internalForm);
			request.setAttribute("lbpmProcess",lbpmProcess);
			request.setAttribute("processId",processId);
%>
	<input type="hidden" id="sysWfBusinessForm.fdFlowContent" 
		name="sysWfBusinessForm.fdFlowContent" value='<c:out value="${sysWfBusinessForm.fdFlowContent}" />' >
	<input type="hidden" id="sysWfBusinessForm.fdTranProcessXML" 
		name="sysWfBusinessForm.fdTranProcessXML" value='<c:out value="${sysWfBusinessForm.fdTranProcessXML}" />' >
	<input type="hidden" id="sysWfBusinessForm.fdProcessId"
		name="sysWfBusinessForm.fdProcessId" value='<c:out value="${processId}" />'>
	<div data-dojo-type="dojox/mobile/View" id="flowchart_view">
		<table class="tb_noborder" width="100%">
			<tr>
				<td id="workflowInfoTD" valign="top">
					<div id="workflowInfoDiv" style="width:100%; -webkit-overflow-scrolling:touch; overflow:scroll">
						<iframe scrolling="no" width="100%" id="WF_IFrame"></iframe>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<script type="text/javascript">
		$(document).ready(function(){
			if (parent.lbpm) {
				if (!parent.lbpm.globals.getProcessXmlString) {
					getProcessXmlString = function(){
						// 到服务器加载详细信息
						var processXml = document.getElementsByName("sysWfBusinessForm.fdFlowContent")[0].value;
						var processId = document.getElementsByName("sysWfBusinessForm.fdProcessId")[0].value;
						var data = new KMSSData();
						data.SendToUrl(Com_Parameter.ContextPath + "sys/lbpm/flowchart/page/detail.jsp?processId=" + processId, function(req) {
							processXml = req.responseText;
						}, false);
						var xmlObj = XML_CreateByContent(processXml);	
						return (xmlObj.xml || new XMLSerializer().serializeToString(xmlObj));
					};
				} else {
					getProcessXmlString = parent.lbpm.globals.getProcessXmlString;
				}
			}
			
			$("meta[name='viewport']").attr('content',"width=device-width,initial-scale=1,maximum-scale=2,minimum-scale=1,user-scalable=yes");
			$('#flowchart_view #workflowInfoTD iframe')[0].src ='<c:url value="/sys/lbpm/flowchart/page/panel.html">'
					+'<c:param name="edit" value="false" />'
					+'<c:param name="extend" value="oa" />'
					+'<c:param name="template" value="false" />'
					+'<c:param name="contentField" value="sysWfBusinessForm.fdFlowContent" />'
					+'<c:param name="statusField" value="sysWfBusinessForm.fdTranProcessXML" />'
					+'<c:param name="modelName" value="${lbpmProcess.fdModelName}" />'
					+'<c:param name="modelId" value="${lbpmProcess.fdModelId}" />'
					+'<c:param name="hasParentProcess" value="${sysWfBusinessForm.hasParentProcess}" />'
					+'<c:param name="hasSubProcesses" value="${sysWfBusinessForm.hasSubProcesses}" />'
					+'<c:param name="mobile" value="true" />'
				+'</c:url>';
				
				var arguObj = document.getElementById("workflowInfoDiv");	
			if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
				arguObj.style.height = window.frameElement.height + "px";
			}
		});
	</script>
<%
		}else{
			out.println("没有找到对应的文档：" + processId);
			return;
		}
	}catch(Exception e){
		e.printStackTrace();
	}
%>
	</template:replace>
</template:include>
