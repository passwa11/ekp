<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgPerson"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!-- 内容区 -->
<div class='lbpmSummaryContent'>
	<div class='processInfo {categroy.processInfoClassName}'>
		<div class='title'>审批信息</div>
		<div id='processName' class='content'>{categroy.processName}</div>
	</div>
	<div class='usageContent'>
		<div class='title'><bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdUsageContent" /></div>
		<div class='content' id="fdUsageContent" data-dojo-type='mui/form/Textarea'
			data-dojo-props="value:'{categroy.fdUsageContentValue}',validate:'fdUsageContentNoLbpm usageContentMaxLenNoLbpm','subject':'<bean:message bundle="sys-lbpmservice" key="mui.lbpmNode.usage" />','placeholder':'<bean:message bundle="sys-lbpmservice" key="lbpmNode.mustSignYourSuggestion"/>','name':'fdUsageContent',opt:false" alertText="">
		</div>
	</div>
	<input name='processId' value='{categroy.processId}' type="hidden"/>
	<input name='opType' value='{categroy.opType}' type="hidden"/>
</div>
<!-- 按钮区 -->
<ul id='lbpmSummaryTabBar' data-dojo-type="mui/tabbar/TabBar" fixed="bottom" class="lbpmSummaryTabBar muiViewBottom">
	<li data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props='id:"process_cancel_button",totalCopies:6,proportion:2' class="muiSplitterButton lbpmCancel"
	onclick="closeDialog()">
		<bean:message  key="button.cancel" /> 
	</li>
	<li data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props='id:"process_review_button",totalCopies:6,proportion:4' class="mainTabBarButton lbpmSubmit"
		onclick="submit()">
		<bean:message  key="button.submit" /> 
	</li>
</ul>