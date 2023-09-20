<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<style>
.tbHeader{
	text-align:center;
}
.tbRow{
	text-align:center;
}
</style>
<table class="tb_normal" width="100%" id="lbpmSimulationNodeTestLogTable" >
		<tr class="tr_normal">
			<td width="40px" class="td_normal_title tbHeader" >
				<bean:message bundle="sys-lbpmservice-support" key="lbpmSimulationNodeTestLog.fdNumber" />
			</td>
			<td width="60px" class="td_normal_title tbHeader" >
				<bean:message bundle="sys-lbpmservice-support" key="lbpmSimulationNodeTestLog.fdApprovalStatus" />
			</td>
			<td width="15%" class="td_normal_title tbHeader" >
				<bean:message bundle="sys-lbpmservice-support" key="lbpmSimulationNodeTestLog.fdNodeName" />
			</td>
			<td width="15%" class="td_normal_title tbHeader" >
				<bean:message bundle="sys-lbpmservice-support" key="lbpmSimulationNodeTestLog.fdHandlerName" />
			</td>
			<td  class="td_normal_title tbHeader" >
				<bean:message bundle="sys-lbpmservice-support" key="lbpmSimulationNodeTestLog.fdLogMessage" />
			</td>
			<td width="150px" class="td_normal_title tbHeader">
				<bean:message bundle="sys-lbpmservice-support" key="lbpmSimulationNodeTestLog.fdCreateTime" />
			</td>
		</tr>
		
		<c:forEach items="${lbpmSimulationNodeTestLog}" var="lbpmSimulationNodeTestLog" varStatus="vStatus">
		  	<c:if test="${lbpmSimulationNodeTestLog.fdApprovalStatus == false}">
				<tr style="background-color:#FED6D6;">
			</c:if>
			<c:if test="${lbpmSimulationNodeTestLog.fdApprovalStatus == true}">
				<c:if test="${lbpmSimulationNodeTestLog.fdIsWarn != 1}">
					<tr>
				</c:if>
				<c:if test="${lbpmSimulationNodeTestLog.fdIsWarn == 1}">
					<tr style="background-color:#FFF68F;">
				</c:if>	
			</c:if>
					<td style="white-space: nowrap;word-break: keep-all;" class='tbRow'>
						<c:out value="${vStatus.index + 1}" />
					</td>
					<td style="white-space: nowrap;word-break: keep-all;" class='tbRow'>
							<c:if test="${lbpmSimulationNodeTestLog.fdApprovalStatus == true}">
								<c:if test="${lbpmSimulationNodeTestLog.fdIsWarn != 1}">
									<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationNodeTestLog.fdApprovalStatus_pass"/>
								</c:if>
								<c:if test="${lbpmSimulationNodeTestLog.fdIsWarn == 1}">
									<bean:message  bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.log.warn"/>
								</c:if>	        		     	
				            </c:if>
							<c:if test="${lbpmSimulationNodeTestLog.fdApprovalStatus == false}">
					        	<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationNodeTestLog.fdApprovalStatus_fail"/>
							</c:if>
					</td>
					<td style="padding-left:14px;">
						<c:out value="${lbpmSimulationNodeTestLog.fdNodeId}" />
						<c:out value="." />
						<c:out value="${lbpmSimulationNodeTestLog.fdNodeName}" />
					</td>
					<td>
						<c:if test="${empty lbpmSimulationNodeTestLog.fdHandlerName}">
							<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationNodeTestLog.noThing"/>
						</c:if>
						<c:if test="${not empty lbpmSimulationNodeTestLog.fdHandlerName}">
							<c:out value="${lbpmSimulationNodeTestLog.fdHandlerName}" />
						</c:if>
					</td>
					<td style="word-wrap: break-word;word-break: break-all;">
						<c:if test="${empty lbpmSimulationNodeTestLog.fdLogMessage}">
							<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationNodeTestLog.noThing"/>
						</c:if>
						<c:if test="${not empty lbpmSimulationNodeTestLog.fdLogMessage}">
							<c:out value="${lbpmSimulationNodeTestLog.fdLogMessage}" />
						</c:if>
					</td>
					<td style="word-wrap: break-word;word-break: break-all;" class='tbRow'>
						<kmss:showDate type="datetime" value="${lbpmSimulationNodeTestLog.fdCreateTime}"/>
					</td>
			 </tr>
		</c:forEach>
</table>
