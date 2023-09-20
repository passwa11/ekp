<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.lbpmservice.support.forms.LbpmSummaryApprovalConfigForm"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	LbpmSummaryApprovalConfigForm lbpmSummaryApprovalConfigForm = (LbpmSummaryApprovalConfigForm)request.getAttribute("lbpmSummaryApprovalConfigForm");
	if(lbpmSummaryApprovalConfigForm != null){
		//解析时间
		String fdNoticeTimeJson = lbpmSummaryApprovalConfigForm.getFdNoticeTimeJson();
		if(StringUtil.isNotNull(fdNoticeTimeJson)){
			JSONObject fdNoticeTime = JSONObject.fromObject(fdNoticeTimeJson);
			pageContext.setAttribute("fdNoticeTimeHour", fdNoticeTime.get("hour"));
			pageContext.setAttribute("fdNoticeTimeMinute", fdNoticeTime.get("minute"));
			pageContext.setAttribute("fdNoticeTimeSecond", fdNoticeTime.get("second"));
		}else{
			pageContext.setAttribute("fdNoticeTimeHour", "0");
			pageContext.setAttribute("fdNoticeTimeMinute", "0");
			pageContext.setAttribute("fdNoticeTimeSecond", "0");
		}
	}
%>
<template:include ref="default.edit" showQrcode='false' sidebar="no">
	<template:replace name="title">
		<bean:message bundle="sys-lbpmservice-support" key="module.sys.lbpmservice.summaryApproval"/>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<c:choose>
				<c:when test="${ lbpmSummaryApprovalConfigForm.method_GET == 'add' || lbpmSummaryApprovalConfigForm.method == 'add' || lbpmSummaryApprovalConfigForm.method_GET == 'saveadd' || lbpmSummaryApprovalConfigForm.method == 'saveadd'}">
					<ui:button text="${lfn:message('button.save')}" order="1" onclick="Com_Submit(document.lbpmSummaryApprovalConfigForm, 'save');" />
					<ui:button text="${lfn:message('button.saveadd')}" order="2" onclick="Com_Submit(document.lbpmSummaryApprovalConfigForm, 'saveadd');" />
				</c:when>
				<c:when test="${ lbpmSummaryApprovalConfigForm.method_GET == 'edit' || lbpmSummaryApprovalConfigForm.method == 'edit'}">
					<ui:button text="${lfn:message('button.submit')}" order="1" onclick="Com_Submit(document.lbpmSummaryApprovalConfigForm, 'update');" />
				</c:when>
			</c:choose>
	    	<ui:button text="${lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();"/>
		</ui:toolbar>
	</template:replace>
	<template:replace name="head">
		<script>Com_IncludeFile('plugin.js');</script>
		<script type="text/javascript" src="<c:url value="/sys/lbpmservice/support/lbpm_summary_approval/config/js/config_edit.js"/>"></script>
	</template:replace>
	<template:replace name="content">
		<div style="width: 100%; padding: 10px 0 20px 0">
			<p class="txttitle">
				汇总审批设置
			</p>
			<html:form action="/sys/lbpmservice/support/lbpm_summary_approval/lbpmSummaryApprovalConfig.do" onsubmit="return $lbpmSummaryConfig.beforeSubmit();">
				<center>
					<table class="tb_normal" width=95%>
						<tr>
							<td width=15% class="td_normal_title">
								汇总审批流程
							</td>
							<td width=85% colspan="3">
								<input type="hidden" name='fdTemplateIdTemp' value='${lbpmSummaryApprovalConfigForm.fdTemplateId }'>
								<html:hidden property="fdTemplateId"/>
							    <html:hidden property="fdTemplateName"/>
							    <html:hidden property="fdTemplateKey"/>
							    <xform:text property="fdTemplateHierarchy" style="width:90%" showStatus="readOnly" htmlElementProperties="validate='required' subject='汇总审批流程'"></xform:text>
							    <a href="#" onclick="Dialog_Tree(false, 'fdTemplateId', 'fdTemplateName', ';','lbpmSummaryProcessTreeService&top=true', '选中模板',null, $lbpmSummaryConfig.afterSelectProcessActon,null, null, null,'选中模板');">
									<bean:message key="dialog.selectOrg"/>
								</a>
								<span class="txtstrong">*</span>
							</td>
						</tr>
						<tr>
							<td width=15% class="td_normal_title">
								汇总审批节点
							</td>
							<td width=85% colspan="3">
								<html:hidden property="fdNodeFactIds"/>
							    <textarea name="fdNodeFactNames" value='${ lbpmSummaryApprovalConfigForm.fdNodeFactNames}' readonly="readonly" style="width:90%" validate='required' subject='汇总审批节点'>${ lbpmSummaryApprovalConfigForm.fdNodeFactNames}</textarea>
								<a href="#" onclick="$lbpmSummaryConfig.selectNodes('fdNodeFactIds','fdNodeFactNames',';',false)"><bean:message key="dialog.selectOther" /></a>
								<span class="txtstrong">*</span>
							</td>
						</tr>
						<tr>
							<td width=15% class="td_normal_title">
								汇总审批发送时间
							</td>
							<td width=85% colspan="3">
							    <span>每天</span>
							 	<span><input name="fdNoticeTimeHour" size="2" class="inputsgl" style="text-align: center" value="${fdNoticeTimeHour}">时</span>
							 	<span><input name="fdNoticeTimeMinute" size="2" class="inputsgl" style="text-align: center" value="${fdNoticeTimeMinute }">分</span>
							 	<span><input name="fdNoticeTimeSecond" size="2" class="inputsgl" style="text-align: center" value="${fdNoticeTimeSecond }">秒</span>
							 	<html:hidden property="fdNoticeTimeJson"/>
							</td>
						</tr>
					</table>
				</center>
			</html:form>
			<script type="text/javascript">
				var _validation = $KMSSValidation(document.forms['lbpmSummaryApprovalConfigForm']);
				var errorInteger = "<kmss:message key="errors.integer" />";
				var errorRange = "<kmss:message key="errors.range" />";
			</script>
		</div>
	</template:replace>
</template:include>