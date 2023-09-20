<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<ui:content title="基本信息" titleicon="lui-fm-icon-2">
	<table class="tb_normal lui-fm-noneBorderTable" width=100%>
		<!--主题-->
		<tr>
			<td width=30% class="tr_normal_title">
				<bean:message bundle="km-imeeting" key="kmImeetingTopic.docSubject"/>
			</td>
			<td>
				<c:out value="${kmImeetingTopicForm.docSubject}"></c:out>
			</td>
		</tr>
		<tr>
			<td width=30% class="tr_normal_title">
				<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdNo"/>
			</td>
			<td>
				<c:out value="${kmImeetingTopicForm.fdNo}"></c:out>
			</td>
		</tr>
		<tr>
			<td width=30% class="tr_normal_title">
				<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdTopicCategory"/>
			</td>
			<td>
				<c:out value="${kmImeetingTopicForm.fdTopicCategoryName}"></c:out>	
			</td>
		</tr>
		<tr>
			<td width=30% class="tr_normal_title">
				<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdChargeUnit"/>
			</td>
			<td>
				<c:out value="${kmImeetingTopicForm.fdChargeUnitName}"></c:out>	
			</td>
		</tr>
		<tr>
			<td width=30% class="tr_normal_title">
				<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdReporter"/>
			</td>
			<td>
				<c:out value="${kmImeetingTopicForm.fdReporterName}"></c:out>
			</td>
		</tr>
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdMaterialStaff"/>
			</td>			
			<td>
				<c:out value="${kmImeetingTopicForm.fdMaterialStaffName}"></c:out>
			</td>
		</tr>
		
		<tr>
			<td class="tr_normal_title" width=30%>
	 			<bean:message bundle="km-imeeting" key="kmImeetingTopic.docCreator"/>
	 		</td>
	 		<td>
	 			<c:out value="${kmImeetingTopicForm.docCreatorName }"></c:out>
			</td>
		</tr>
		<tr>
			<%--创建时间--%>
	 		<td class="tr_normal_title" width=30%>
	 			<bean:message bundle="km-imeeting" key="kmImeetingTopic.docCreateTime"/>
	 		</td>
	 		<td>
	 			<c:out value="${kmImeetingTopicForm.docCreateTime }"></c:out>
			</td>
		</tr>
		<%-- 所属场所 --%>
		<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp" charEncoding="UTF-8">
               <c:param name="id" value="${kmImeetingTopicForm.authAreaId}"/>
               <c:param name="rightModel" value="true"/>
         </c:import> 
	</table>
</ui:content>