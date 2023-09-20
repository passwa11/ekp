<%@page import="com.landray.kmss.sys.lbpmext.businessauth.service.ILbpmExtBusinessSettingInfoService"%>
<%@page import="com.alibaba.fastjson.JSONArray"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.lbpmservice.support.service.ILbpmAuditNoteTypeService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%
	ILbpmExtBusinessSettingInfoService lbpmExtBusinessSettingInfoService = (ILbpmExtBusinessSettingInfoService) SpringBeanUtil
			.getBean("lbpmExtBusinessSettingInfoService");
	String isOpinionTypeEnabled = lbpmExtBusinessSettingInfoService.getIsOpinionTypeEnabled("imissiveLbpmSwitch");
	request.setAttribute("isOpinionTypeEnabled", isOpinionTypeEnabled);
	// 获取意见类型
	if ("true".equals(isOpinionTypeEnabled)) {
		ILbpmAuditNoteTypeService lbpmAuditNoteTypeService = (ILbpmAuditNoteTypeService) SpringBeanUtil
				.getBean("lbpmAuditNoteTypeService");
		JSONArray allAuditNodeTypes = lbpmAuditNoteTypeService.queryAllAuditNodeType();
		if (allAuditNodeTypes == null) {
			allAuditNodeTypes = new JSONArray();
		}
		request.setAttribute("allAuditNodeTypes", allAuditNodeTypes);
	}
%>
<script>
	<c:if test="${isOpinionTypeEnabled eq 'true'}">
		window.allAuditNodeTypes = JSON.parse('${allAuditNodeTypes}');
	</c:if>
</script>
<jsp:include page="/sys/lbpmservice/mobile/lbpm_audit_note/import/view_include.jsp">
	<jsp:param name="processId" value="${param.processId}" />
</jsp:include>
<div data-dojo-type="sys/lbpmservice/mobile/operation/other/AssignOprView" 
	data-dojo-props='processId:"${param.processId}"'
	class="lbpmView" id="AssignOprView">
	<div id="updateInfo" style="display:none">
		<input type="hidden" name="ext_modelName" value="${param.modelName}"/>
		<input type="hidden" name="ext_modelId" value="${param.modelId}"/>
	</div>
	<div class="actionArea">
		<div class="actionView">
			<div id="OperationMethodTable" class="operationMethodArea">
				<div class="titleNode" >
					<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.handMethods" />
				</div>
				<div class="detailNode">
					
				</div>
			</div>
		</div>
	</div>
	
	<div id="assignOprArea" style="display:none">
		<div class="optionsSplitLine"></div>
		<div class="actionView">
			<div class="assignOprRow">
				<div class="titleNode" >
					<bean:message key="lbpmAssign.assignOption" bundle="sys-lbpmservice-support" />
				</div>
				<div class="detailNode" style="border-bottom:0">
					<input type='hidden' alertText='' value='' id='assigneeIds' name='assigneeIds' key='assigneeIds'>
					<input type='hidden' id='assigneeNames' name='assigneeNames' key='assigneeNames' alertText='' value=''>
					<div data-dojo-type="mui/form/Address" data-dojo-props="type: 12,idField:'assigneeIds',nameField:'assigneeNames',curIds:'',curNames:'',isMul:true,exceptValue:'${KMSS_Parameter_CurrentUserId}'"></div>
					<div id="canMultiAssign" alertText="" key="canMultiAssign" data-dojo-type="mui/form/CheckBox" 
						data-dojo-props="name:'canMultiAssign', value:'true', mul:false, text:'<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle_Scope.allow.muti" /><bean:message key="lbpmAssign.fdOperType.assign" bundle="sys-lbpmservice-support" />'"></div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="commonUsagesArea" class="actionArea">
		<div class="optionsSplitLine"></div>
		<div class="actionView">
			<div class="lbpmAuditNoteTable">
				<div>
					<div class="titleNode" width="15%">
						<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdUsageContent" />
					</div>
					<div>
						<div data-dojo-type='mui/form/Textarea' 
							data-dojo-props="'placeholder':'<bean:message bundle="sys-lbpmservice" key="lbpmNode.mustSignYourSuggestion"/>',
								'name':'ext_usageContent', opt:false" alertText="">
						</div>
					</div>
					<div class="commonUsagesDiv">
						<div class="handingWay" id="commonExtUsages">
							<div class="iconArea"><i class="mui mui-create"></i></div>
							<span class="iconTitle"><bean:message bundle="sys-lbpmservice" key="mui.lbpmNode.usage"/></span>
						</div>
					</div>
					<c:if test="${isOpinionTypeEnabled eq 'true'}">
						<div id="opinionConfig">
							<input type="checkbox" data-dojo-type="mui/form/CheckBox" data-dojo-props="id:'notifyIsScript',text:'是否显示在稿纸',checked:true,value:true">
							<div  data-dojo-type="mui/form/Select"
								data-dojo-props="subject:'下拉框',mul:false,id:'approveOpinionType',name:'approveOpinionType',value:'1',store:allAuditNodeTypes">
							</div>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	
	<div id="curNodeInfoArea" class="actionArea">
		<div class="optionsSplitLine"></div>
		<div class="actionView">
			<div class="lbpmInfoTable">
				<div class="titleNode" >
					<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.currentProcessor" />
				</div>
				<div class="detailNode">
					<div>
						<kmss:showWfPropertyValues idValue="${param.processId}" propertyName="handerNameDetail" mobile="true" />
					</div>
				</div>
			</div>
		</div>
	</div>
	<div data-dojo-type="mui/tabbar/TabBar" fixed="bottom">
		<li data-dojo-type="mui/tabbar/TabBarButton" id="OperationSubmit" class="mainTabBarButton">
			<bean:message  key="button.submit" />
		</li>
		<li data-dojo-type="mui/tabbar/TabBarButton" class="lbpmSwitchButton muiSplitterButton" 
		data-dojo-props='icon1:"mui mui-flowchart",onClick:function(){showFlowChart();}'>
			<bean:message bundle="sys-lbpmservice" key="lbpm.tab.graphic" />
		</li>
	</div>
</div>