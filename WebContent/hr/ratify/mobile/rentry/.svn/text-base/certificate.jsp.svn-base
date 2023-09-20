<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script type="text/javascript">Com_IncludeFile("doclist.js");</script>
<input type="hidden" name="fdCertificate_Flag" value="1">
<table class="muiAgendaNormal" width="100%" border="0" cellspacing="0" cellpadding="0" id="TABLE_DocList_fdCertificate_Form" >
	<script type="text/javascript">DocList_Info.push("TABLE_DocList_fdCertificate_Form");</script>
	<%--基准行--%>
	<tr data-dojo-type="mui/form/Template" class="muiAgendaNormalTr" data-celltr="true" KMSS_IsReferRow="1" style="display:none;" border='0'>
		<td>
			<table class="muiSimple">
				<tr>
					<td class="muiDetailTableNo" colspan="2" style="padding-left: 1rem;">
						<span class="muiDetailTableNoTxt">!{index}</span>
						<div class="muiDetailTableDel" onclick="del(this,'TABLE_DocList_fdCertificate_Form');"><bean:message key="button.delete"/></div>
					</td>
				</tr>
				<tr>
					<td class="muiTitle">
						<bean:message bundle="hr-ratify" key="hrRatifyCertifi.fdName"/>
					</td>
					<td>
						<xform:text property="fdCertificate_Form[!{index}].fdName" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyCertifi.fdName')}" validators="maxLength(200)" required="true" style="width:95%;" mobile="true" />
					</td>
				</tr>
				<tr>
					<td class="muiTitle">
						<bean:message bundle="hr-ratify" key="hrRatifyCertifi.fdIssuingUnit"/>
					</td>
					<td>
						<xform:text property="fdCertificate_Form[!{index}].fdIssuingUnit" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyCertifi.fdIssuingUnit')}" validators="maxLength(200)" required="true" style="width:95%;" mobile="true" />
					</td>
				</tr>
				<tr>
					<td class="muiTitle">
						<bean:message bundle="hr-ratify" key="hrRatifyCertifi.fdIssueDate"/>
					</td>
					<td>
						<xform:datetime property="fdCertificate_Form[!{index}].fdIssueDate" validators="before compareFdIssueDate" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyCertifi.fdIssueDate')}" required="true" dateTimeType="date" style="width:95%;" mobile="true" />
					</td>
				</tr>
				<tr>
					<td class="muiTitle">
						<bean:message bundle="hr-ratify" key="hrRatifyCertifi.fdInvalidDate"/>
					</td>
					<td>
						<xform:datetime property="fdCertificate_Form[!{index}].fdInvalidDate" validators="compareFdIssueDate" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyCertifi.fdInvalidDate')}" dateTimeType="date" style="width:95%;" mobile="true" />
					</td>
				</tr>
				<tr>
					<td class="muiTitle">
						<bean:message bundle="hr-ratify" key="hrRatifyCertifi.fdRemark"/>
					</td>
					<td>
						<xform:text property="fdCertificate_Form[!{index}].fdRemark" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyCertifi.fdRemark')}" style="width:95%;" mobile="true" />
					</td>
				</tr>
			</table>
		</td>	
	</tr>
	<c:forEach items="${hrRatifyEntryForm.fdCertificate_Form}" var="fdCertificate_FormItem" varStatus="vstatus">
		<tr KMSS_IsContentRow="1" data-celltr="true" class="muiAgendaNormalTr">
			<td>
				<input type="hidden" name="fdCertificate_Form[${vstatus.index}].fdId" value="${fdCertificate_FormItem.fdId}" />
				<input type="hidden" name="fdCertificate_Form[${vstatus.index}].docMainId" value="${fdCertificate_FormItem.docMainId}" />
				<table class="muiSimple">
					<tr>
						<td class="muiDetailTableNo" colspan="2" style="padding-left: 1rem;">
							<span class="muiDetailTableNoTxt">${vstatus.index+1}</span>
							<div class="muiDetailTableDel" onclick="del(this,'TABLE_DocList_fdCertificate_Form');"><bean:message key="button.delete"/></div>
						</td>
					</tr>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="hr-ratify" key="hrRatifyCertifi.fdName"/>
						</td>
						<td>
							<xform:text property="fdCertificate_Form[${vstatus.index}].fdName" value="${fdCertificate_FormItem.fdName}" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyCertifi.fdName')}" validators="maxLength(200)" required="true" style="width:95%;" mobile="true" />
						</td>
					</tr>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="hr-ratify" key="hrRatifyCertifi.fdIssuingUnit"/>
						</td>
						<td>
							<xform:text property="fdCertificate_Form[${vstatus.index}].fdIssuingUnit" value="${fdCertificate_FormItem.fdIssuingUnit}" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyCertifi.fdIssuingUnit')}" validators="maxLength(200)" required="true" style="width:95%;" mobile="true" />
						</td>
					</tr>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="hr-ratify" key="hrRatifyCertifi.fdIssueDate"/>
						</td>
						<td>
							<xform:datetime property="fdCertificate_Form[${vstatus.index}].fdIssueDate" value="${fdCertificate_FormItem.fdIssueDate}" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyCertifi.fdIssueDate')}" required="true" dateTimeType="date" style="width:95%;" mobile="true" />
						</td>
					</tr>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="hr-ratify" key="hrRatifyCertifi.fdInvalidDate"/>
						</td>
						<td>
							<xform:datetime property="fdCertificate_Form[${vstatus.index}].fdInvalidDate" value="${fdCertificate_FormItem.fdInvalidDate}" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyCertifi.fdInvalidDate')}" dateTimeType="date" style="width:95%;" mobile="true" />
						</td>
					</tr>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="hr-ratify" key="hrRatifyCertifi.fdRemark"/>
						</td>
						<td>
							<xform:text property="fdCertificate_Form[${vstatus.index}].fdRemark" value="${fdCertificate_FormItem.fdRemark}" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyCertifi.fdRemark')}" style="width:95%;" mobile="true" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</c:forEach>
</table>
<div class="muiDetailTableAdd"  onclick="add('TABLE_DocList_fdCertificate_Form');">
	<div class="mblTabBarButtonIconArea">
		<div class="mblTabBarButtonIconParent">
			<i class="mui mui-plus"></i>
		</div>
	</div>
	<div class="mblTabBarButtonLabel" ><bean:message bundle="hr-ratify" key="hrRatifyCertifi.addRatifyCertifi" /></div>
</div>	