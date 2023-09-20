<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<c:choose>
	<c:when test="${param.contentType eq 'xform'}">
		<div>
			<c:if test="${hrRatifySalaryForm.docUseXform == 'false'}">
				<table class="tb_normal" width=100%>
					<tr>
						<td colspan="4">${hrRatifySalaryForm.docXform}</td>
					</tr>
				</table>
			</c:if>
			<c:if
				test="${hrRatifySalaryForm.docUseXform == 'true' || empty hrRatifySalaryForm.docUseXform}">
				<c:import url="/sys/xform/include/sysForm_view.jsp"
					charEncoding="UTF-8">
					<c:param name="formName" value="hrRatifySalaryForm" />
					<c:param name="fdKey" value="hrRatifyMain" />
					<c:param name="useTab" value="false" />
				</c:import>
			</c:if>
		</div>

		<c:if test="${param.approveModel ne 'right'}">
			<ui:content title="${ lfn:message('hr-ratify:py.JiBenXinXi') }"
				expand="false">
				<table class="tb_normal" width="100%">
					<tr>
						<td class="td_normal_title" width="15%">
							${lfn:message('hr-ratify:hrRatifyMain.docSubject')}</td>
						<td colspan="3">
							<%-- 标题--%>
							<div id="_xform_docSubject" _xform_type="text">
								<xform:text property="docSubject" showStatus="view"
									style="width:95%;" />
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%">
							${lfn:message('hr-ratify:hrRatifyMKeyword.docKeyword')}</td>
						<td colspan="3"><xform:text property="fdKeywordNames"
								style="width:97%" /></td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%">
							${lfn:message('hr-ratify:hrRatifyMain.docTemplate')}</td>
						<td colspan="3">
							<%-- 分类模板--%>
							<div id="_xform_docTemplateId" _xform_type="dialog">
								<c:out value="${ hrRatifySalaryForm.docTemplateName}" />
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%">
							${lfn:message('hr-ratify:hrRatifyMain.docCreator')}</td>
						<td width="35%">
							<%-- 创建人--%>
							<div id="_xform_docCreatorId" _xform_type="address">
								<ui:person personId="${hrRatifySalaryForm.docCreatorId}"
									personName="${hrRatifySalaryForm.docCreatorName}" />
							</div>
						</td>
						<td class="td_normal_title" width="15%">
							${lfn:message('hr-ratify:hrRatifyMain.docNumber')}</td>
						<td width="35%">
							<%-- 编号--%>
							<div id="_xform_docNumber" _xform_type="text">
								<xform:text property="docNumber" showStatus="view"
									style="width:95%;" />
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%">
							${lfn:message('hr-ratify:hrRatifyMain.fdDepartment')}</td>
						<td width="35%">
							<%-- 部门--%>
							<div id="_xform_fdDepartmentId" _xform_type="address">
								<xform:address propertyId="fdDepartmentId"
									propertyName="fdDepartmentName" orgType="ORG_TYPE_ALL"
									showStatus="view" style="width:95%;" />
							</div>
						</td>
						<td class="td_normal_title" width="15%">
							${lfn:message('hr-ratify:hrRatifyMain.docCreateTime')}</td>
						<td width="35%">
							<%-- 创建时间--%>
							<div id="_xform_docCreateTime" _xform_type="datetime">
								<xform:datetime property="docCreateTime" showStatus="view"
									dateTimeType="datetime" style="width:95%;" />
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%">
							${lfn:message('hr-ratify:hrRatifyMain.docPublishTime')}</td>
						<td width="35%">
							<%-- 结束时间--%>
							<div id="_xform_docPublishTime" _xform_type="datetime">
								<xform:datetime property="docPublishTime" showStatus="view"
									dateTimeType="datetime" style="width:95%;" />
							</div>
						</td>
						<td class="td_normal_title" width="15%">
							${lfn:message('hr-ratify:hrRatifyMain.fdFeedback')}</td>
						<td width="35%">
							<%-- 实施反馈人--%>
							<div id="_xform_fdFeedbackIds" _xform_type="address">
								<xform:address propertyId="fdFeedbackIds"
									propertyName="fdFeedbackNames" mulSelect="true"
									orgType="ORG_TYPE_ALL" showStatus="view" style="width:95%;" />
							</div>
						</td>
					</tr>
				</table>
			</ui:content>
		</c:if>
	</c:when>
	<c:when test="${param.contentType eq 'other'}">
		<c:if test="${param.approveModel ne 'right'}">
			<c:choose>
				<c:when
					test="${hrRatifySalaryForm.docUseXform == 'true' || empty hrRatifySalaryForm.docUseXform}">
					<c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="hrRatifySalaryForm" />
						<c:param name="fdKey" value="${fdTempKey }" />
						<c:param name="isExpand" value="true" />
						<c:param name="onClickSubmitButton"
							value="Com_Submit(document.hrRatifySalaryForm, 'update');" />
					</c:import>
				</c:when>
				<c:otherwise>
					<c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="hrRatifySalaryForm" />
						<c:param name="fdKey" value="${fdTempKey }" />
						<c:param name="isExpand" value="true" />
						<c:param name="onClickSubmitButton"
							value="Com_Submit(document.hrRatifySalaryForm, 'update');" />
					</c:import>
				</c:otherwise>
			</c:choose>
		</c:if>
		<c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="hrRatifySalaryForm" />
			<c:param name="moduleModelName"
				value="com.landray.kmss.hr.ratify.model.HrRatifySalary" />
		</c:import>

		<c:import url="/sys/readlog/import/sysReadLog_view.jsp"
			charEncoding="UTF-8">
			<c:param name="formName" value="hrRatifySalaryForm" />
		</c:import>

		<c:import url="/sys/circulation/import/sysCirculationMain_view.jsp"
			charEncoding="UTF-8">
			<c:param name="formName" value="hrRatifySalaryForm" />
		</c:import>

		<%-- 反馈记录 --%>
		<c:import url="/hr/ratify/hr_ratify_feedback/hrRatifyFeedback_tab.jsp"
			charEncoding="UTF-8">
			<c:param name="formName" value="hrRatifySalaryForm" />
		</c:import>
		<%-- 发起沟通 --%>
		<c:if test="${hrRatifySalaryForm.docStatus!='10'}">
			<kmss:ifModuleExist path = "/km/collaborate/">
				<%request.setAttribute("communicateTitle",ResourceUtil.getString("hrRatifyMain.communicateTitle","hr-ratify"));%>
					<c:import url="/km/collaborate/import/kmCollaborateMain_view.jsp" charEncoding="UTF-8">
						<c:param name="commuTitle" value="${communicateTitle}" />
						<c:param name="formName" value="hrRatifySalaryForm" />
					</c:import>
			</kmss:ifModuleExist>   
		</c:if>
		<c:import url="/sys/bookmark/import/bookmark_bar.jsp" charEncoding="UTF-8">
		    <c:param name="fdSubject" value="${hrRatifySalaryForm.docSubject}" />
		    <c:param name="fdModelId" value="${hrRatifySalaryForm.fdId}" />
		    <c:param name="fdModelName" value="com.landray.kmss.hr.ratify.model.HrRatifySalary" />
		</c:import>
	</c:when>
</c:choose>