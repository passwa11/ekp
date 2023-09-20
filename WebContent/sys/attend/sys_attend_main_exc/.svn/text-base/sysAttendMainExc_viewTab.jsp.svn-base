<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.attend.forms.SysAttendMainExcForm"%>
<template:replace name="content">
		<div class='lui_form_title_frame'>
			<div class='lui_form_subject'>
				${ lfn:message('sys-attend:table.sysAttendMainExc') }
			</div>
			<div class='lui_form_baseinfo'>
			</div>
		</div>
		<div class="lui_form_content_frame">
			<table class="tb_normal" width=100%>
					<tr>
						<td class="td_normal_title" width=15%>
							${ lfn:message('sys-attend:sysAttendCategory.attend.fdName') }
						</td>
						<td width="85%" colspan="3">
							<c:out value="${sysAttendMainExcForm.fdAttendMainCateName }" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendMain.docCreator"/>
						</td>
						<td width="35%">
							<c:out value="${sysAttendMainExcForm.fdAttendMainDocCreatorName}" />
						</td>
						<td class="td_normal_title" width=15%>
							${ lfn:message('sys-attend:sysAttendMain.export.dept') }
						</td>
						<td width="35%">
							<c:out value="${sysAttendMainExcForm.fdAttendMainDocCreatorDeptName}" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendMain.docCreateTime"/>
						</td>
						<td width="35%">
							<c:if test="${empty sysAttendMainExcForm.fdAttendTime} ">
								<%-- 兼容 --%>
								<c:out value="${sysAttendMainExcForm.fdAttendMainCreateTime }" />
							</c:if>
							${sysAttendMainExcForm.fdAttendTime}
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendMain.fdLocation1"/>
						</td>
						<td width="35%" colspan="3">
							<c:if test="${not empty sysAttendMainExcForm.fdAttendMainLocation }">
								<%@ taglib uri="/WEB-INF/KmssConfig/sys/attend/map.tld" prefix="map"%>
								<c:set var="fdLocationCoordinate" value="${sysAttendMainExcForm.fdAttendMainLat}${','}${sysAttendMainExcForm.fdAttendMainLng}"/>
								<map:location propertyName="fdLocation" nameValue="${sysAttendMainExcForm.fdAttendMainLocation }"
									propertyCoordinate="fdLocationCoordinate" coordinateValue="${fdLocationCoordinate }" 
									showStatus="view"></map:location>
							</c:if>
							
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendMain.fdStatus"/>
						</td>
						<td width="85%" colspan="3">
							<c:choose>
								<c:when test="${sysAttendMainExcForm.fdAttendMainStatus=='1' && sysAttendMainExcForm.fdAttendOutside=='true'}">
									${ lfn:message('sys-attend:sysAttendMain.fdOutside') }
								</c:when>
								<c:otherwise>
									<sunbor:enumsShow value="${sysAttendMainExcForm.fdAttendMainStatus}" enumsType="sysAttendMain_fdStatus" />
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>${ lfn:message('sys-attend:sysAttendMainExc.reason') }</td>
						<td colspan="3">
							<xform:textarea showStatus="view" property="fdDesc"></xform:textarea>
							
							<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
								<c:param name="formBeanName" value="sysAttendMainExcForm" />
								<c:param name="fdKey" value="attachment"/>
							</c:import>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>${ lfn:message('sys-attend:sysAttendMainExc.result') }</td>
						<td>
							【<sunbor:enumsShow value="${sysAttendMainExcForm.docStatus}" enumsType="sysAttendMainExc_docStatus" />】
						</td>
						<td class="td_normal_title" width=15%><bean:message bundle="sys-attend" key="sysAttendMainExc.handler"/></td>
						<td>
							<kmss:showWfPropertyValues idValue="${sysAttendMainExcForm.fdId}" propertyName="handlerName" />
						</td>
					</tr>
					<c:if test="${sysAttendMainExcForm.fdStatus==1  }">
					<tr>
						<td class="td_normal_title" width=15%>${ lfn:message('sys-attend:sysAttendMainExc.docCreateTime1') }</td>
						<td colspan="3">
							<c:out value="${sysAttendMainExcForm.docCreateTime}" />
						</td>
					</tr>
					</c:if>
					<c:if test="${sysAttendMainExcForm.fdStatus!=1  }">
					<tr>
						<td class="td_normal_title" width=15%>${ lfn:message('sys-attend:sysAttendMainExc.docCreateTime1') }</td>
						<td>
							<c:out value="${sysAttendMainExcForm.docCreateTime}" />
						</td>
						<td class="td_normal_title" width=15%>${ lfn:message('sys-attend:sysAttendMainExc.docHandleTime') }</td>
						<td>
							<c:out value="${sysAttendMainExcForm.docHandleTime}" />
						</td>
					</tr>
					</c:if>
				</table>
			
		</div>
		
		<c:choose> 
			<c:when test="${param.approveModel eq 'right'}">
				<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-supportExpand="true" var-expand="true"  var-count="5" var-average='false' var-useMaxWidth='true'>
					<c:choose>
						<c:when test="${sysAttendMainExcForm.docStatus>='30' || sysAttendMainExcForm.docStatus=='00'}">
							<!-- 流程 -->
							<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="sysAttendMainExcForm" />
								<c:param name="fdKey" value="attendMainExc" />
								<c:param name="showHistoryOpers" value="true" />
								<c:param name="isExpand" value="true" />
								<c:param name="approveType" value="right"></c:param>
								<c:param name="needInitLbpm" value="true" />
							</c:import>
						</c:when>
						<c:otherwise>
							<!-- 流程 -->
							<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="sysAttendMainExcForm" />
								<c:param name="fdKey" value="attendMainExc" />
								<c:param name="showHistoryOpers" value="true" />
								<c:param name="isExpand" value="true" />
								<c:param name="approveType" value="right"></c:param>
								
							</c:import>
						</c:otherwise>
					</c:choose>
					<c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="sysAttendMainExcForm" />
						<c:param name="moduleModelName" value="com.landray.kmss.sys.attend.model.SysAttendMainExc" />
					</c:import>
				</ui:tabpanel>
			</c:when>
			<c:otherwise>
				<ui:tabpage expand="false">
					<c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="sysAttendMainExcForm" />
						<c:param name="fdKey" value="attendMainExc" />
					</c:import>
					<c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="sysAttendMainExcForm" />
						<c:param name="moduleModelName" value="com.landray.kmss.sys.attend.model.SysAttendMainExc" />
					</c:import>
				</ui:tabpage>
			</c:otherwise>
		</c:choose>	
		
	</template:replace>
	<c:choose>
	<c:when test="${param.approveModel eq 'right'}">
		<template:replace name="barRight">
			<c:choose>
				 <c:when test="${sysAttendMainExcForm.docStatus>='30' || sysAttendMainExcForm.docStatus=='00'}">
					<ui:accordionpanel>
						<!-- 基本信息-->
						<c:import url="/sys/attend/sys_attend_main_exc/sysAttendMainExc_viewBaseInfoContent.jsp" charEncoding="UTF-8">
						</c:import>
					</ui:accordionpanel>
				</c:when> 
				<c:otherwise>
					<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel" >
						<%-- 流程 --%>
								<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="sysAttendMainExcForm" />
									<c:param name="fdKey" value="attendMainExcDoc" />
									<c:param name="showHistoryOpers" value="true" />
									<c:param name="isExpand" value="true" />
									<c:param name="approveType" value="right" />
									<c:param name="approvePosition" value="right" />
								</c:import>
						<!-- 审批记录 -->
						<c:import url="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote_list_content.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="sysAttendMainExcForm" />
							<c:param name="fdModelId" value="${sysAttendMainExcForm.fdId}" />
							<c:param name="fdModelName" value="com.landray.kmss.sys.attend.model.SysAttendMainExc" />
						</c:import>
						<!-- 基本信息-->
						<c:import url="/sys/attend/sys_attend_main_exc/sysAttendMainExc_viewBaseInfoContent.jsp" charEncoding="UTF-8">
						</c:import>
					</ui:tabpanel>
				</c:otherwise>
			</c:choose>
		</template:replace>
	</c:when>
</c:choose>