<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:choose>
	<c:when test="${param.contentType eq 'xform'}">
		<ui:content title="${lfn:message('hr-ratify:py.BiaoDanNeiRong')}"
			expand="true" toggle="false">
			<table class="tb_normal" width=100%>
				<%-- 标题--%>
				<tr>
					<td align="right" style="border-right: 0px;" width=15%>
						${lfn:message('hr-ratify:hrRatifyMain.docSubject')}</td>
					<td style="border-left: 0px !important;">
						<div id="_xform_docSubject" _xform_type="text">
							<c:if
								test="${hrRatifySignForm.titleRegulation==null || hrRatifySignForm.titleRegulation=='' }">
								<xform:text property="docSubject" style="width:98%;"
									className="inputsgl" />
							</c:if>
							<c:if
								test="${hrRatifySignForm.titleRegulation!=null && hrRatifySignForm.titleRegulation!='' }">
								<xform:text property="docSubject"
									style="width:98%;height:auto;color:#333;" className="inputsgl"
									showStatus="readOnly"
									value="${lfn:message('hr-ratify:hrRatifyMain.docSubject.info') }" />
							</c:if>
							
						</div>
					</td>
				</tr>
			</table>
			<br>
			<c:if test="${hrRatifySignForm.docUseXform == 'false'}">
				<table class="tb_normal" width=100%>
					<tr>
						<td colspan="2"><kmss:editor property="docXform" width="95%" />
						</td>
					</tr>
				</table>
			</c:if>
			<c:if
				test="${hrRatifySignForm.docUseXform == 'true' || empty hrRatifySignForm.docUseXform}">
				<c:import url="/sys/xform/include/sysForm_edit.jsp"
					charEncoding="UTF-8">
					<c:param name="formName" value="hrRatifySignForm" />
					<c:param name="fdKey" value="${fdTempKey }" />
					<c:param name="useTab" value="false" />
				</c:import>
			</c:if>
			 <br>
                            <table class="tb_normal" width=100%>
                             <tr>
                             <td align="left" class="td_normal_title"width=15.5%>合同附件</td>
                             <td >
	                             <c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
											<c:param name="fdKey" value="attHrExpCont"/>
								</c:import>
                             </td>
                             </tr>
                               <!-- 盖章文件 -->
						 	<c:if test="${hrRatifySignForm.fdSignEnable}">
						 	<tr>
						 		<td class="td_normal_title" width=15%>
								 	<bean:message bundle="hr-ratify" key="hrRatifyMain.fdSignFile"/>
								 </td>
							 	<c:if test="${hrRatifySignForm.method_GET=='add'}">
								 		<td width="85%" colspan="3" >
								 			<div style="padding:10px 0"><font color="red"><bean:message bundle="hr-ratify" key="hrRatifyMain.file.big"/></font></div>
											<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
												<c:param name="fdKey" value="fdSignFile" />
												<c:param name="fdModelId" value="${param.fdId }" />
												<c:param name="enabledFileType" value=".pdf;.doc;.xls;.ppt;.docx;.xlsx;.pptx;.jpg;.jpeg;.png;" />
												<c:param name="uploadAfterSelect" value="true" />
												<c:param name="fdModelName" value="com.landray.kmss.hr.ratify.model.HrRatifySign" />
												<c:param name="fdRequired" value="true" />
											</c:import>
										</td>
								 	
							 	</c:if>
							 	<c:if test="${hrRatifySignForm.method_GET=='edit'}">
							 		<td width="85%" colspan="3" >
								 			<div style="padding:10px 0"><font color="red"><bean:message bundle="hr-ratify" key="hrRatifyMain.file.big"/></font></div>
											<c:import url="/sys/attachment/sys_att_main/sysAttMain_viewe.jsp" charEncoding="UTF-8">
												<c:param name="fdKey" value="fdSignFile" />
												<c:param name="fdModelId" value="${param.fdId }" />
												<c:param name="uploadAfterSelect" value="true" />
												<c:param name="fdModelName" value="com.landray.kmss.hr.ratify.model.HrRatifySign" />
												<c:param name="fdRequired" value="true" />
											</c:import>
										</td>
							 	</c:if>
							 </tr>	
						 	</c:if>
                            </table>
		</ui:content>
	</c:when>
	<c:when test="${param.contentType eq 'baseInfo'}">

		<ui:content title="${ lfn:message('hr-ratify:py.JiBenXinXi') }"
			expand="true">
			<table class="tb_normal" width="100%">
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
							<html:hidden property="docTemplateId" />
							<html:hidden property="fdSignEnable" />
							<c:out value="${ hrRatifySignForm.docTemplateName}" />
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">
						${lfn:message('hr-ratify:hrRatifyMain.docCreator')}</td>
					<td width="35%">
						<%-- 创建人--%>
						<div id="_xform_docCreatorId" _xform_type="address">
							<ui:person personId="${hrRatifySignForm.docCreatorId}"
								personName="${hrRatifySignForm.docCreatorName}" />
						</div>
					</td>
					<td class="td_normal_title" width="15%">
						${lfn:message('hr-ratify:hrRatifyMain.docNumber')}</td>
					<td width="35%">
						<%-- 编号--%>
						<div id="_xform_docNumber" _xform_type="text">
							<c:if test="${not empty hrRatifySignForm.docNumber}">
								<xform:text property="docNumber" showStatus="readOnly"
									style="width:95%;" />
							</c:if>
							<c:if test="${empty hrRatifySignForm.docNumber}">
								<span style="color: #868686;">${lfn:message("hr-ratify:hrRatifyMain.docNumber.title")}</span>
							</c:if>
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
						${lfn:message('hr-ratify:hrRatifyMain.docStatus')}</td>
					<td width="35%">
						<%-- 文档状态--%>
						<div id="_xform_docStatus" _xform_type="select">
							<xform:select property="docStatus"
								htmlElementProperties="id='docStatus'" showStatus="view">
								<xform:enumsDataSource enumsType="hr_ratify_doc_status" />
							</xform:select>
						</div>
					</td>
					<td class="td_normal_title" width="15%">
						${lfn:message('hr-ratify:hrRatifyMain.docPublishTime')}</td>
					<td width="35%">
						<%-- 结束时间--%>
						<div id="_xform_docPublishTime" _xform_type="datetime">
							<xform:datetime property="docPublishTime" showStatus="view"
								dateTimeType="datetime" style="width:95%;" />
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">
						${lfn:message('hr-ratify:hrRatifyMain.fdFeedback')}</td>
					<td colspan="3">
						<%-- 实施反馈人--%>
						<div id="_xform_fdFeedbackIds" _xform_type="address">
							<xform:address propertyId="fdFeedbackIds"
								propertyName="fdFeedbackNames" mulSelect="true"
								orgType="ORG_TYPE_ALL" showStatus="edit" style="width:95%;" />
						</div>
					</td>
				</tr>
			</table>
		</ui:content>

	</c:when>
	<c:when test="${param.contentType eq 'other'}">
		<c:if test="${param.approveModel ne 'right'}">
			<c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp"
				charEncoding="UTF-8">
				<c:param name="formName" value="hrRatifySignForm" />
				<c:param name="fdKey" value="${fdTempKey }" />
				<c:param name="isExpand" value="false" />
			</c:import>

		</c:if>
		<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="hrRatifySignForm" />
			<c:param name="moduleModelName"
				value="com.landray.kmss.hr.ratify.model.HrRatifySign" />
		</c:import>
	</c:when>
</c:choose>