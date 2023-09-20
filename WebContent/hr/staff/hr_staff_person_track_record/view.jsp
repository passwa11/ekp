<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view">
    <template:replace name="head">
        <style type="text/css">
   			.lui_paragraph_title{
   				font-size: 15px;
   				color: #15a4fa;
   		    	padding: 15px 0px 5px 0px;
   			}
   			.lui_paragraph_title span{
   				display: inline-block;
   				margin: -2px 5px 0px 0px;
   			}
   			.barCodeImg {
   				position:absolute;
   				right:20px;
   			}
        </style>
    </template:replace>
    <template:replace name="title">
        <c:out value="${hrStaffPersonExperienceContractForm.fdName} - "/>
        <c:out value="${lfn:message('hr-staff:hrStaffPersonExperience.type.contract')}" />
    </template:replace>
    <template:replace name="toolbar">
    	<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3" var-navwidth="90%">
    		<ui:button text="${lfn:message('button.close')}" onclick="Com_CloseWindow();" order="1" />
    	</ui:toolbar>
    </template:replace>
    <template:replace name="content">
    	<ui:tabpage expand="false" var-navwidth="90%">
            <ui:content title="${ lfn:message('hr-staff:hrStaffPersonExperience.type.contract') }" expand="true">
                <table class="tb_normal" width="100%">
                    <tr>
						<!-- 合同名称-->
						<td width="15%" class="td_normal_title">
							${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdName') }
						</td>
						<td colspan="3">
							<xform:text property="fdName" style="width:98%;" />
						</td>
					</tr>
					<tr>
						<!-- 合同类型 -->
						<td class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdContType" />
						</td>
						<td>
							<c:out value="${hrStaffPersonExperienceContractForm.fdContType }"></c:out>
						</td>
						<!-- 签订标识 -->
						<td class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdSignType" />
						</td>
						<td>
							<c:out value="${signType }"></c:out>
						</td>
					</tr>
					<tr>
						<!-- 开始时间-->
						<td width="15%" class="td_normal_title">
							${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdBeginDate') }
						</td>
						<td width="35%">
							<xform:datetime property="fdBeginDate" dateTimeType="date"></xform:datetime>
						</td>
						<!-- 结束时间-->
						<td width="15%" class="td_normal_title">
							${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdEndDate') }
						</td>
						<td width="35%">
							<xform:datetime property="fdEndDate" dateTimeType="date"></xform:datetime>
						</td>
					</tr>
					<tr>
						<!-- 合同办理时间 -->
						<td class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdHandleDate" />
						</td>
						<td>
							<xform:datetime property="fdHandleDate" dateTimeType="date"/>
						</td>
						<!-- 合同状态 -->
						<td class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdContStatus" />
						</td>
						<td>
							<c:choose>
								<c:when test="${empty hrStaffPersonExperienceContractForm.fdContStatus }">
									<bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdContStatus.1" />
								</c:when>
								<c:otherwise>
									<xform:select property="fdContStatus">
										<xform:enumsDataSource enumsType="hrStaffPersonExperienceContract_fdContStatus" />
									</xform:select>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<!-- 备注-->
						<td width="15%" class="td_normal_title">
							${ lfn:message('hr-staff:hrStaffPersonExperience.fdMemo') }
						</td>
						<td colspan="3">
							<xform:textarea property="fdMemo" style="width:98%;height:50px;"/>
						</td>
					</tr>
					<tr>
						<!-- 合同附件 -->
						<td class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.autoHashMap" />
						</td>
						<td colspan="3">
							<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
								<c:param name="fdKey" value="attHrExpCont"/>
								<c:param name="fdModelId" value="${param.fdId}" />
								<c:param name="formBeanName" value="hrStaffPersonExperienceContractForm"/>
							</c:import>
						</td>
					</tr>
					<c:if test="${not empty hrStaffPersonExperienceContractForm.fdRelatedProcess }">
						<tr>
							<!-- 签订流程 -->
							<td class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdCreateProcess" />
							</td>
							<td colspan="3">
								<a href="${LUI_ContextPath}${hrStaffPersonExperienceContractForm.fdRelatedProcess}">
									<bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdCreateProcess.tip" />
								</a>
							</td>
						</tr>
					</c:if>
					<c:if test="${hrStaffPersonExperienceContractForm.fdContStatus eq '3'}">
						<tr>
							<!-- 合同解除时间 -->
							<td class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdCancelDate" />
							</td>
							<td colspan="3">
								<xform:datetime property="fdCancelDate" dateTimeType="date" />
							</td>
						</tr>
						<tr>
							<!-- 合同解除原因 -->
							<td class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdCancelReason" />
							</td>
							<td colspan="3">
								<xform:textarea property="fdCancelReason" style="width:98%;height:50px;" />
							</td>
						</tr>
					</c:if>
					<c:if test="${not empty hrStaffPersonExperienceContractForm.fdCancelProcess }">
						<tr>
							<!-- 解除流程 -->
							<td class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdCancelProcess" />
							</td>
							<td colspan="3">
								<a href="${LUI_ContextPath}${hrStaffPersonExperienceContractForm.fdCancelProcess}">
									<bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdCancelProcess.tip" />
								</a>
							</td>
						</tr>
					</c:if>
                </table>
            </ui:content>
        </ui:tabpage>
    </template:replace>
</template:include>