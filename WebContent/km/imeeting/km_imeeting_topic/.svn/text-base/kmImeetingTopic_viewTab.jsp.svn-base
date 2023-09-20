<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:replace name="content"> 
<c:if test="${param.approveModel ne 'right'}">
	<form name="kmImeetingTopicForm" method="post" action ="${KMSS_Parameter_ContextPath}km/imeeting/km_imeeting_topic/kmImeetingTopic.do">
</c:if>
	<div class="lui_form_content_frame" style="padding-top:20px">
		<table class="tb_normal" width=100%>
			<html:hidden property="fdId" />
			<html:hidden property="fdModelId" />
			<html:hidden property="fdModelName" />
			<html:hidden property="docStatus" />
			<html:hidden property="docCreateTime"/>
			<html:hidden property="method_GET" />
			<tr>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="km-imeeting" key="kmImeetingTopic.docSubject"/>
				</td>
				<td colspan="3">
					<c:out value="${kmImeetingTopicForm.docSubject}"></c:out>
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdTopicCategory"/>
				</td>
				<td  width="35%">	
					<bean:write	name="kmImeetingTopicForm" property="fdTopicCategoryName" />
				</td>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdNo"/>
				</td>
				<td  width="35%">
					<c:if test="${kmImeetingTopicForm.docStatus==10 || kmImeetingTopicForm.docStatus==null || kmImeetingTopicForm.docStatus=='' }">
					   提交后自动生成
					</c:if>
					<c:if test="${kmImeetingTopicForm.fdNo!='' && kmImeetingTopicForm.fdNo!=null && kmImeetingTopicForm.docStatus!=10 }">
                   	 	${ kmImeetingTopicForm.fdNo}
                	</c:if>
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdReporter"/>
				</td>
				<td  width="35%">
					<c:out value="${kmImeetingTopicForm.fdReporterName}"></c:out>
				</td>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdChargeUnit"/>
				</td>
				<td  width="35%">
					<c:out value="${kmImeetingTopicForm.fdChargeUnitName}"></c:out>
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdMaterialStaff"/>
				</td>
				<td  width="35%">
					<c:out value="${kmImeetingTopicForm.fdMaterialStaffName}"></c:out>
				</td>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdSourceSubject"/>
				</td>
				<td  width="35%">
					<c:out value="${kmImeetingTopicForm.fdSourceSubject}"></c:out>
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdAttendUnit"/>
				</td>
				<td  width="85%" colspan="3">
					 <c:out value="${kmImeetingTopicForm.fdAttendUnitNames}"></c:out>
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdListenUnit"/>
				</td>
				<td  width="85%" colspan="3">
					 <c:out value="${kmImeetingTopicForm.fdListenUnitNames}"></c:out>
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="km-imeeting" key="kmImeetingTopic.issue.text"/>
				</td>
				<td colspan="3">
					<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
						<c:param name="fdKey" value="mainonline"/>
						<c:param  name="fdMulti" value="false" />
						<c:param name="uploadAfterSelect" value="true" />  
						<c:param name="fdModelId" value="${kmImeetingTopicForm.fdId }" />
						<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingTopic" />
					</c:import>
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="km-imeeting" key="kmImeetingTopic.issue.material"/>
				</td>
				<td colspan="3">
					<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
						<c:param name="fdKey" value="attachment" />
						<c:param name="uploadAfterSelect" value="true" />  
						<c:param name="fdModelId" value="${kmImeetingTopicForm.fdId }" />
						<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingTopic" />
					</c:import>
				</td>
			</tr>	
			<tr>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdContent"/>
				</td>
				<td  width="85%" colspan="3">
					<xform:textarea property="fdContent" style="width:97.5%;height:80px" validators="senWordsValidator(kmImeetingTopicForm)"/>
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="km-imeeting" key="kmImeetingTopic.docCreator"/>
				</td>
				<td  width="35%">
					 <c:out value="${kmImeetingTopicForm.docCreatorName}"></c:out>
				</td>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="km-imeeting" key="kmImeetingTopic.docCreateTime"/>
				</td>
				<td  width="35%">
					 <c:out value="${kmImeetingTopicForm.docCreateTime}"></c:out>
				</td>
			</tr>
		</table>
		<c:if test="${param.approveModel ne 'right'}">
			</form>
		</c:if>
	</div>
	<c:choose>
		<c:when test="${param.approveModel eq 'right'}">
			<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="5" var-average='false' var-useMaxWidth='true'
				var-supportExpand="true" var-expand="true">
				<c:choose>
					<c:when test="${kmImeetingTopicForm.docStatus>='30' || kmImeetingTopicForm.docStatus=='00'}">
						<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmImeetingTopicForm" />
							<c:param name="fdKey" value="mainTopic" />
							<c:param name="showHistoryOpers" value="true" />
							<c:param name="isExpand" value="true" />
							<c:param name="approveType" value="right" />
							<c:param name="needInitLbpm" value="true" />
						</c:import>
					</c:when>
					<c:otherwise>
						<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmImeetingTopicForm" />
							<c:param name="fdKey" value="mainTopic" />
							<c:param name="showHistoryOpers" value="true" />
							<c:param name="isExpand" value="true" />
							<c:param name="approveType" value="right" />
						</c:import>
					</c:otherwise>
				</c:choose>
				
				<c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmImeetingTopicForm" />
					<c:param name="moduleModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingTopic" />
				</c:import>
			</ui:tabpanel>
		</c:when>
		<c:otherwise>
			<ui:tabpage expand="false">
				<%--流程机制 --%>
				<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmImeetingTopicForm" />
					<c:param name="fdKey" value="mainTopic" />
				</c:import>
				<%--权限机制 --%>
				<c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmImeetingTopicForm" />
					<c:param name="moduleModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingTopic" />
				</c:import>
			</ui:tabpage>
		</c:otherwise>
	</c:choose>
<script language="JavaScript">
	var validation = $KMSSValidation(document.forms['kmImeetingTopicForm']);
</script>
</template:replace>
<c:if test="${param.approveModel eq 'right'}">
	<template:replace name="barRight">
		<c:choose>
			<c:when test="${kmImeetingTopicForm.docStatus>='30' || kmImeetingTopicForm.docStatus=='00'}">
				<ui:accordionpanel>
					<!-- 基本信息-->
					<c:import url="/km/imeeting/km_imeeting_topic/kmImeetingTopic_viewBaseInfo.jsp" charEncoding="UTF-8">
					</c:import>
				</ui:accordionpanel>
			</c:when>
			<c:otherwise>
				<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel" >
					<c:if test="${kmImeetingTopicForm.docStatus != '10'}">
					<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="kmImeetingTopicForm" />
						<c:param name="fdKey" value="mainTopic" />
						<c:param name="showHistoryOpers" value="true" />
						<c:param name="isExpand" value="true" />
						<c:param name="approveType" value="right" />
						<c:param name="approvePosition" value="right" />
					</c:import>
					</c:if>
					<!-- 审批记录 -->
					<c:import url="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote_list_content.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="kmImeetingTopicForm" />
						<c:param name="fdModelId" value="${kmImeetingTopicForm.fdId}" />
						<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingTopic" />
					</c:import>
				</ui:tabpanel>
			</c:otherwise>
		</c:choose>
	</template:replace>
</c:if>	
