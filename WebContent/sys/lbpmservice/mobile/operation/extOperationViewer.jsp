<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.util.SysFormDingUtil"%>
<%@ include file="/resource/jsp/common.jsp"%>
<jsp:include page="/sys/lbpmservice/mobile/lbpm_audit_note/import/view_include.jsp">
	<jsp:param name="processId" value="${param.processId}" />
</jsp:include>
<div data-dojo-type="sys/lbpmservice/mobile/operation/ExtOperationView" 
	data-dojo-props='processId:"${param.processId}"'
	class="lbpmView" id="OperationView" style="display: none">
	<div id='lbpmExtOperContent'>
	<div class="actionView panel">
	<table class="muiSimple" cellpadding="0" cellspacing="0">
		<tr id="updateInfo" style="display:none" colspan="2">
			<input type="hidden" name="ext_modelName" value="${param.modelName}"/>
			<input type="hidden" name="ext_modelId" value="${param.modelId}"/>
		</tr>
		<!-- 移动端弹窗优化去除 -->
		<%-- <tr class="actionArea">
			<td class="muiTitle">
				<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.handMethods" />
			</td>
			<td>
				<div id="OperationMethodTable" class="operationMethodArea">
					<div class="detailNode">
						
					</div>
				</div>
			</td>
		</tr> --%>
	
		<tr id="keepAuditNoteArea" style="display:none">
			<td class="keepAuditNoteRow" colspan="2">
				<div class="titleNode operation_title" >
					<bean:message bundle="sys-lbpmservice-operation-historyhandler" key="lbpmOperations.fdOperType.historyhandler.back.keepAuditNote" />
				</div>
				<div class="detailNode">
					<div>
						<xform:radio value="false" mobile="true" showStatus="edit" property="keepAuditNote">
							<xform:enumsDataSource enumsType="common_yesno" />
						</xform:radio>
					</div>
				</div>
			</td>
		</tr>
		<tr id="operationsRow" style="display:none;">
			<td class="muiTitle" id="operationsTDTitle">
				&nbsp;
			</td>
			<td id="operationsTDContent" >
				&nbsp;
			</td>
		</tr>
		<tr id="commonUsagesArea" class="actionArea">
			<td class="lbpmAuditNoteTable" colspan="2">
				<div>
					<div class="titleNode operation_title"  width="15%">
						<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdUsageContent" />
					</div>
					<div>
						<div class="extOperView_usageContent" data-dojo-type='mui/form/Textarea' 
							data-dojo-props="'placeholder':'<bean:message bundle="sys-lbpmservice" key="lbpmNode.mustSignYourSuggestion"/>',
								'name':'ext_usageContent', opt:false" alertText="">
						</div>
					</div>
					<div class="commonUsagesDiv">
						<div class="handingWay" id="commonExtUsages"><div class="iconArea"><i class="mui mui-create"></i></div>
							<span class="iconTitle"><bean:message bundle="sys-lbpmservice" key="mui.lbpmNode.usage"/></span>
						</div>
					</div>
				</div>
			</td>
		</tr>
	</table>
	</div>
	<div class="actionView panel" id="moreActionViewExt" style="display: none">
		<table class="muiSimple" cellpadding="0" cellspacing="0">
			<tr id="notifyLevelArea" class="actionArea">
				<td class="notifyLevelRow" colspan="2">
					<div class="titleNode operation_title" >
						<bean:message bundle="sys-notify" key="sysNotifyTodo.level.title" />
					</div>
					<div class="detailNode">
						<div>
							<kmss:editNotifyLevel property='ext_notifyLevel' mobile='true' value=""/>
						</div>
					</div>
				</td>
			</tr>
			<tr id="curNodeInfoArea" class="actionArea">
				<td class='muiTitle'>
					<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.currentProcessor" />
				</td>
				<td>
					<div class="lbpmInfoTable" colspan="2">
						<div class="detailNode">
							<div>
								<kmss:showWfPropertyValues idValue="${param.processId}" propertyName="handerNameDetail" mobile="true" />
							</div>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<div data-dojo-type="sys/lbpmservice/mobile/common/LbpmFolder"
		 data-dojo-props="expandDomId:'moreActionViewExt',showType:'dialog'">
	</div> 
	<div style="height: 20px"></div>
	<div data-dojo-type="mui/tabbar/TabBar" id="lbpmExtOperationTabBar" fixed="bottom">
		<c:choose>
			<c:when test='<%="true".equals(SysFormDingUtil.getEnableDing())%>'>
				<li data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props='id:"process_cancel_button_ext",totalCopies:6,proportion:3' class="muiSplitterButton lbpmCancel"
					onclick="closeExtDialog()">
				<bean:message  key="button.cancel" /> 
				</li>
				<li data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props='totalCopies:6,proportion:3' id="OperationSubmit" class="mainTabBarButton">
					<bean:message  key="button.submit" />
				</li>
			</c:when>
			<c:otherwise>
				<li data-dojo-type="mui/tabbar/TabBarButton" class="lbpmSwitchButton muiSplitterButton" 
					data-dojo-props='icon1:"mui mui-flowchart",onClick:function(){showFlowChart();}'>
				<bean:message bundle="sys-lbpmservice" key="lbpm.tab.graphic" />
				</li>
				<li data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props='id:"process_cancel_button_ext",totalCopies:6,proportion:2' class="muiSplitterButton lbpmCancel"
					onclick="closeExtDialog()">
				<bean:message  key="button.cancel" /> 
				</li>
				<li data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props='totalCopies:6,proportion:4' id="OperationSubmit" class="mainTabBarButton">
					<bean:message  key="button.submit" />
				</li>
			</c:otherwise>
		</c:choose>
	</div>
	</div>
</div>