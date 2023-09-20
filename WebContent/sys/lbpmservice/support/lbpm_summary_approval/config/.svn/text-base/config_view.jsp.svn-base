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
<template:include ref="default.view" showQrcode='false' sidebar="no">
	<template:replace name="title">
		<bean:message bundle="sys-lbpmservice-support" key="module.sys.lbpmservice.summaryApproval"/>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
	    	<ui:button text="${lfn:message('button.edit')}" order="1" onclick="Com_OpenWindow('lbpmSummaryApprovalConfig.do?method=edit&fdId=${lbpmSummaryApprovalConfigForm.fdId}','_self');" />
			<ui:button text="${lfn:message('button.delete') }" order="2" onclick="Com_OpenWindow('lbpmSummaryApprovalConfig.do?method=updateOrDel&fdId=${lbpmSummaryApprovalConfigForm.fdId}','_self');"></ui:button>
			<ui:button text="${lfn:message('button.close') }" order="3" onclick="Com_CloseWindow();"/>
		</ui:toolbar>
	</template:replace>
	<template:replace name="head">
	</template:replace>
	<template:replace name="content">
		<div style="width: 100%; padding: 10px 0 20px 0">
			<p class="txttitle">
				汇总审批设置
			</p>
			<center>
				<table class="tb_normal" width=95%>
					<tr>
						<td width=15% class="td_normal_title">
							汇总审批流程
						</td>
						<td width=85% colspan="3">
							<c:if test="${not empty requestScope['templateViewUrl']}">
								<a href="${ LUI_ContextPath}${requestScope['templateViewUrl']}"><bean:write name="lbpmSummaryApprovalConfigForm" property="fdTemplateHierarchy"/></a>
							</c:if>
							<c:if test="${empty requestScope['templateViewUrl']}">
								<bean:write name="lbpmSummaryApprovalConfigForm" property="fdTemplateHierarchy"/>
							</c:if>
						</td>
					</tr>
					<tr>
						<td width=15% class="td_normal_title">
							汇总审批节点
						</td>
						<td width=85% colspan="3">
							<bean:write name="lbpmSummaryApprovalConfigForm" property="fdNodeFactNames"/>
						</td>
					</tr>
					<tr>
						<td width=15% class="td_normal_title">
							汇总审批发送时间
						</td>
						<td width=85% colspan="3">
						    <span>每天</span>
						 	<span><kmss:showText value="${fdNoticeTimeHour}"/>时</span>
						 	<span><kmss:showText value="${fdNoticeTimeMinute}"/>分</span>
						 	<span><kmss:showText value="${fdNoticeTimeSecond}"/>秒</span>
						</td>
					</tr>
					<tr>
						<td width=15% class="td_normal_title">
							创建者
						</td>
						<td width=35%>
						    ${lbpmSummaryApprovalConfigForm.docCreatorName}
						</td>
						<td width=15% class="td_normal_title">
							创建时间
						</td>
						<td width=35%>
						    <bean:write name="lbpmSummaryApprovalConfigForm" format="yyyy-MM-dd HH:mm" property="docCreateTime" />
						</td>
					</tr>
				</table>
			</center>
		</div>
	</template:replace>
</template:include>