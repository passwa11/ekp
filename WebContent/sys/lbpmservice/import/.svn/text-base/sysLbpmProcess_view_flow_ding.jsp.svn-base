<%@page import="com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainFormAdapter"%>
<%@page import="com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm"%>
<%@page import="java.util.Map"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessLimitTimeOperationLogService"%>
<%@page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmSetting"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmSettingDefault"%>
<%@page import="com.landray.kmss.util.ResourceUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="sysWfBusinessForm" value="${requestScope[param.formName]}" />
<c:set var="lbpmProcessForm" value="${sysWfBusinessForm.sysWfBusinessForm.internalForm}" />
<c:set var="resize_prefix" value="_" scope="request" />
<c:if test="${param.needInitLbpm eq 'true'}">
	<%@ include file="/sys/lbpmservice/include/sysLbpmProcess_script.jsp"%>
	<!--引入暂存按钮-->
	<c:import url="/sys/lbpmservice/import/sysLbpmProcess_saveDraft.jsp" charEncoding="UTF-8"> 
		<c:param name="formName" value="${param.formName}">
		</c:param>
	</c:import>
	<!--引入切换表单按钮-->
	<%@ include file="/sys/lbpmservice/import/sysLbpmProcess_subform.jsp"%>
	<input type="hidden" id="sysWfBusinessForm.fdParameterJson" name="sysWfBusinessForm.fdParameterJson" value='' >
	<input type="hidden" id="sysWfBusinessForm.fdAdditionsParameterJson" name="sysWfBusinessForm.fdAdditionsParameterJson" value='' >
	<input type="hidden" id="sysWfBusinessForm.fdIsModify" name="sysWfBusinessForm.fdIsModify" value='' >
	<input type="hidden" id="sysWfBusinessForm.fdCurHanderId" name="sysWfBusinessForm.fdCurHanderId" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdCurHanderId}" />' >
	<input type="hidden" id="sysWfBusinessForm.fdCurNodeSavedXML" name="sysWfBusinessForm.fdCurNodeSavedXML" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdCurNodeSavedXML}" />' >
	<input type="hidden" id="sysWfBusinessForm.fdFlowContent" name="sysWfBusinessForm.fdFlowContent" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdFlowContent}" />' >
	<input type="hidden" id="sysWfBusinessForm.fdProcessId" name="sysWfBusinessForm.fdProcessId" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdProcessId}" />'>
	<input type="hidden" id="sysWfBusinessForm.fdKey" name="sysWfBusinessForm.fdKey" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdKey}" />' >
	<input type="hidden" id="sysWfBusinessForm.fdModelId" name="sysWfBusinessForm.fdModelId" value='<c:out value="${sysWfBusinessForm.fdId}" />' >
	<input type="hidden" id="sysWfBusinessForm.fdModelName" name="sysWfBusinessForm.fdModelName" value='<c:out value="${sysWfBusinessForm.modelClass.name}" />' >
	<input type="hidden" id="sysWfBusinessForm.fdCurNodeXML" name="sysWfBusinessForm.fdCurNodeXML" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdCurNodeXML}" />' >
	<input type="hidden" id="sysWfBusinessForm.fdTemplateModelName" name="sysWfBusinessForm.fdTemplateModelName" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdTemplateModelName}" />' >
	<input type="hidden" id="sysWfBusinessForm.fdTemplateKey" name="sysWfBusinessForm.fdTemplateKey" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdTemplateKey}" />' >
	<input type="hidden" id="sysWfBusinessForm.canStartProcess" name="sysWfBusinessForm.canStartProcess" value='' >
	<input type="hidden" id="sysWfBusinessForm.fdTranProcessXML" name="sysWfBusinessForm.fdTranProcessXML" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdTranProcessXML}" />' >
	<input type="hidden" id="sysWfBusinessForm.fdDraftorId" name="sysWfBusinessForm.fdDraftorId" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdDraftorId}" />' >
	<input type="hidden" id="sysWfBusinessForm.fdDraftorName" name="sysWfBusinessForm.fdDraftorName" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdDraftorName}" />' >
	<input type="hidden" id="sysWfBusinessForm.fdHandlerRoleInfoIds" name="sysWfBusinessForm.fdHandlerRoleInfoIds" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdHandlerRoleInfoIds}" />' >
	<input type="hidden" id="sysWfBusinessForm.fdHandlerRoleInfoNames" name="sysWfBusinessForm.fdHandlerRoleInfoNames" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdHandlerRoleInfoNames}" />' >
	<input type="hidden" id="sysWfBusinessForm.fdAuditNoteFdId" name="sysWfBusinessForm.fdAuditNoteFdId" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdAuditNoteFdId}" />' >
	<input type="hidden" id="docStatus" name="docStatus" value='<c:out value="${sysWfBusinessForm.docStatus}" />' >
	<input type="hidden" id="sysWfBusinessForm.fdIdentityId" name="sysWfBusinessForm.fdIdentityId" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdIdentityId}" />' >
	<input type="hidden" id="sysWfBusinessForm.fdProcessStatus" name="sysWfBusinessForm.fdProcessStatus" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdProcessStatus}" />' >
	<input type="hidden" id="sysWfBusinessForm.fdSubFormXML" name="sysWfBusinessForm.fdSubFormXML" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdSubFormXML}" />'>
	<!-- 留下其他信息处理的域，为了兼容特权人修改流程图信息时保存其他设计的信息 -->
	<input type="hidden" id="sysWfBusinessForm.fdOtherContentInfo" name="sysWfBusinessForm.fdOtherContentInfo" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdOtherContentInfo}" />'>
</c:if>
<c:if test="${sysWfBusinessForm.sysWfBusinessForm!=null && param.needInitLbpm ne 'true'}">
	<%
		LbpmSettingDefault settingDefault = new LbpmSettingDefault();
		pageContext.setAttribute("approveModel", settingDefault.getApproveModel());
		String title = ResourceUtil.getString("sys-lbpmservice:lbpm.tab.label");
		if("right".equals(request.getParameter("approvePosition"))){
			title = ResourceUtil.getString("sys-lbpmservice:lbpmview.approve.model.dialog.view");
			pageContext.setAttribute("titleicon", "lui-fm-icon-3");
			pageContext.setAttribute("tabcontentId", "_right");
		}else if(StringUtil.isNotNull(request.getParameter("titleicon"))){
			pageContext.setAttribute("titleicon", request.getParameter("titleicon"));
		}
		pageContext.setAttribute("title", title);
		
		//限时办理，获取开关
		LbpmSetting lbpmSettingInfo = new LbpmSetting();
		String isShowLimitTimeOperation = lbpmSettingInfo.getIsShowLimitTimeOperation();
		request.setAttribute("showLimitTimeOperation", isShowLimitTimeOperation);
		
		if("true".equals(isShowLimitTimeOperation)){
			//限时办理，获取剩余时间
			ILbpmProcessLimitTimeOperationLogService operationLogService = 
			(ILbpmProcessLimitTimeOperationLogService)SpringBeanUtil.getBean("lbpmProcessLimitTimeOperationLogService");
			String fdCurNodeXML = null;
			String fdProcessId = null;
			if(pageContext.getAttribute("sysWfBusinessForm") instanceof ISysLbpmMainFormAdapter){
				ISysLbpmMainFormAdapter adapter = (ISysLbpmMainFormAdapter)pageContext.getAttribute("sysWfBusinessForm");
				fdCurNodeXML = adapter.getSysWfBusinessForm().getFdCurNodeXML();
				fdProcessId = adapter.getSysWfBusinessForm().getFdProcessId();
			}
			if(StringUtil.isNotNull(fdCurNodeXML) && StringUtil.isNotNull(fdProcessId)){
				Map<String, Object> result = operationLogService.getRemainingTime(fdProcessId, fdCurNodeXML);
				Integer status = (Integer)result.get("status");
				if(status == 0){
					//表示已经超时
					request.setAttribute("timeoutDay", result.get("day"));
					request.setAttribute("timeoutHour",result.get("hour"));
					request.setAttribute("timeoutMinute",result.get("minute"));
					request.setAttribute("timeoutSecond", result.get("second"));
					request.setAttribute("limitTotalTime", result.get("total"));
					request.setAttribute("isTimeout", "true");
					request.setAttribute("showTimeType","timeout");//表示超时时间
				}else if(status == 1){
					//存在限时并未超过
					request.setAttribute("limitDay", result.get("day"));
					request.setAttribute("limitHour",result.get("hour"));
					request.setAttribute("limitMinute",result.get("minute"));
					request.setAttribute("limitSecond", result.get("second"));
					request.setAttribute("limitTotalTime", result.get("total"));
					request.setAttribute("isTimeout", "false");
					request.setAttribute("showTimeType", "limit");//表示限时时间
				}else if(status == -1){
					//不存在限时，不显示
					request.setAttribute("showLimitTimeOperation", "false");
				}
			}else{
				request.setAttribute("showLimitTimeOperation", "false");
			}
		}
	%>
	<script>
		var limitTotalTime = '${requestScope.limitTotalTime}';//提供在流程初始化时使用
		var isTimeoutTotal = '${requestScope.isTimeout}';
	</script>
	<c:choose>
		<c:when test="${empty param.isExpand and (sysWfBusinessForm.docStatus == '20' or sysWfBusinessForm.docStatus == '11')}">
		    <c:set var="expand" value="true"></c:set>
		</c:when>
		<c:otherwise>
		  <c:set var="expand" value="${param.isExpand}"></c:set>
		</c:otherwise>
	</c:choose>
	<c:set var="order" value="${empty param.order ? '10' : param.order}"/>
	<c:set var="disable" value="${empty param.disable ? 'false' : param.disable}"/>
	
	<ui:content id="process_review_tabcontent${tabcontentId}" 
		title="${ lfn:message('sys-lbpmservice-support:lbpm.process.status.processStatus') }" 
		expand="${expand}" titleicon="${titleicon}" cfg-order="${order}" cfg-disable="${disable}">
	<ui:event event="show">
		if (this.isBindOnResize) {
			return;
		}
		var element = this.element;
		function onResize() {
			element.find("*[_onresize]").each(function(){
				var elem = $(this);
				var funStr = elem.attr("_onresize");
				var show = elem.closest('tr').is(":visible");
				var init = elem.attr("data-init-resize");
				if(funStr!=null && funStr!="" && show && init == null){
					elem.attr("data-init-resize", 'true');
					var tmpFunc = new Function(funStr);
					tmpFunc.call();
				}
			});
		}
		this.isBindOnResize = true;
		onResize();
		element.click(onResize);
	</ui:event>
		<%-- <iframe id="process_view_flow_ding" src="${LUI_ContextPath}/sys/lbpmservice/common/process_view_flow_ding.jsp"></iframe> --%>

		<c:import url="/sys/lbpmservice/common/process_view_flow_ding.jsp" charEncoding="UTF-8"></c:import> 
	</ui:content>
	
	<ui:content id="process_review_tabcontent${tabcontentId}_${order}"
		title="${ lfn:message('sys-lbpmservice-support:lbpm.process.status.processChart') }" 
		expand="${expand}" titleicon="${titleicon}" cfg-order="${order}" cfg-disable="${disable}">
	<ui:event event="show">
		if (this.isBindOnResize) {
			return;
		}
		var element = this.element;
		function onResize() {
			element.find("*[_onresize]").each(function(){
				var elem = $(this);
				var funStr = elem.attr("_onresize");
				var show = elem.closest('tr').is(":visible");
				var init = elem.attr("data-init-resize");
				if(funStr!=null && funStr!="" && show && init == null){
					elem.attr("data-init-resize", 'true');
					var tmpFunc = new Function(funStr);
					tmpFunc.call();
				}
			});
		}
		this.isBindOnResize = true;
		onResize();
		element.click(onResize);
	</ui:event>
	<%--  <iframe id="process_view_flow_ding" src="${LUI_ContextPath}/sys/lbpmservice/common/process_view_flow_ding_img.jsp"></iframe> --%>
		<c:import url="/sys/lbpmservice/common/process_view_flow_ding_img.jsp" charEncoding="UTF-8"></c:import>
	</ui:content>
	<ui:content id="process_review_tabcontent${tabcontentId}" 
		title="${ lfn:message('sys-lbpmservice-support:lbpm.process.status.processTable') }" 
		expand="${expand}" titleicon="${titleicon}" cfg-order="${order}" cfg-disable="${disable}">
	<ui:event event="show">
		if (this.isBindOnResize) {
			return;
		}
		var element = this.element;
		function onResize() {
			element.find("*[_onresize]").each(function(){
				var elem = $(this);
				var funStr = elem.attr("_onresize");
				var show = elem.closest('tr').is(":visible");
				var init = elem.attr("data-init-resize");
				if(funStr!=null && funStr!="" && show && init == null){
					elem.attr("data-init-resize", 'true');
					var tmpFunc = new Function(funStr);
					tmpFunc.call();
				}
			});
		}
		this.isBindOnResize = true;
		onResize();
		element.click(onResize);
	</ui:event>
		<%-- <iframe id="process_view_flow_ding" src="${LUI_ContextPath}/sys/lbpmservice/common/process_view_flow_ding.jsp"></iframe> --%>

		<c:import url="/sys/lbpmservice/common/process_view_flow_ding_table.jsp" charEncoding="UTF-8"></c:import> 
	</ui:content>
	<ui:content id="process_review_tabcontent${tabcontentId}" 
		title="${ lfn:message('sys-lbpmservice-support:lbpm.process.status.processLog') }" 
		expand="${expand}" titleicon="${titleicon}" cfg-order="${order}" cfg-disable="${disable}">
	<ui:event event="show">
		if (this.isBindOnResize) {
			return;
		}
		var element = this.element;
		function onResize() {
			element.find("*[_onresize]").each(function(){
				var elem = $(this);
				var funStr = elem.attr("_onresize");
				var show = elem.closest('tr').is(":visible");
				var init = elem.attr("data-init-resize");
				if(funStr!=null && funStr!="" && show && init == null){
					elem.attr("data-init-resize", 'true');
					var tmpFunc = new Function(funStr);
					tmpFunc.call();
				}
			});
		}
		this.isBindOnResize = true;
		onResize();
		element.click(onResize);
	</ui:event>
		<%-- <iframe id="process_view_flow_ding" src="${LUI_ContextPath}/sys/lbpmservice/common/process_view_flow_ding.jsp"></iframe> --%>

		<c:import url="/sys/lbpmservice/common/process_view_flow_ding_log.jsp" charEncoding="UTF-8"></c:import> 
	</ui:content>
</c:if>

