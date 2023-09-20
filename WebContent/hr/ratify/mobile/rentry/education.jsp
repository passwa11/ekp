<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script type="text/javascript">Com_IncludeFile("doclist.js");</script>
<input type="hidden" name="fdEducations_Flag" value="1">
<table class="muiAgendaNormal" width="100%" border="0" cellspacing="0" cellpadding="0" id="TABLE_DocList_fdEducations_Form" >
	<script type="text/javascript">DocList_Info.push("TABLE_DocList_fdEducations_Form");</script>
	<%--基准行--%>
	<tr data-dojo-type="mui/form/Template" class="muiAgendaNormalTr" data-celltr="true" KMSS_IsReferRow="1" style="display:none;" border='0'>
		<td>
			<table class="muiSimple">
				<tr>
					<td class="muiDetailTableNo" colspan="2" style="padding-left: 1rem;">
						<span class="muiDetailTableNoTxt">!{index}</span>
						<div class="muiDetailTableDel" onclick="del(this,'TABLE_DocList_fdEducations_Form');"><bean:message key="button.delete"/></div>
					</td>
				</tr>
				<tr>
					<td class="muiTitle">
						<bean:message bundle="hr-ratify" key="hrRatifyEduExp.fdName"/>
					</td>
					<td>
						<xform:text property="fdEducations_Form[!{index}].fdName" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyEduExp.fdName')}" validators="maxLength(200)" required="true" style="width:95%;" mobile="true" />
					</td>
				</tr>
				<tr>
					<td class="muiTitle">
						<bean:message bundle="hr-ratify" key="hrRatifyEduExp.fdMajor"/>
					</td>
					<td>
						<xform:text property="fdEducations_Form[!{index}].fdMajor" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyEduExp.fdMajor')}" validators="maxLength(200)" required="true" style="width:95%;" mobile="true" />
					</td>
				</tr>
				<tr>
					<td class="muiTitle">
						<bean:message bundle="hr-ratify" key="hrRatifyEduExp.fdAcademic"/>
					</td>
					<td>
						<xform:select property="fdEducations_Form[!{index}].fdAcadeRecordId" showStatus="edit" mobile="true" required="true">
                            <xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" 
                            whereBlock="hrStaffPersonInfoSettingNew.fdType = 'fdHighestDegree'"/>
                        </xform:select>
					</td>
				</tr>
				<tr>
					<td class="muiTitle">
						<bean:message bundle="hr-ratify" key="hrRatifyEduExp.fdEntranceDate"/>
					</td>
					<td>
						<xform:datetime property="fdEducations_Form[!{index}].fdEntranceDate" validators="before compareFdEntranceDate" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyEduExp.fdEntranceDate')}" required="true" dateTimeType="date" style="width:95%;" mobile="true" />
					</td>
				</tr>
				<tr>
					<td class="muiTitle">
						<bean:message bundle="hr-ratify" key="hrRatifyEduExp.fdGraduationDate"/>
					</td>
					<td>
						<xform:datetime property="fdEducations_Form[!{index}].fdGraduationDate" validators="before compareFdEntranceDate" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyEduExp.fdGraduationDate')}" dateTimeType="date" style="width:95%;" mobile="true" />
					</td>
				</tr>
				<tr>
					<td class="muiTitle">
						<bean:message bundle="hr-ratify" key="hrRatifyEduExp.fdRemark"/>
					</td>
					<td>
						<xform:text property="fdEducations_Form[!{index}].fdRemark" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyEduExp.fdRemark')}" style="width:95%;" mobile="true" />
					</td>
				</tr>
			</table>
		</td>	
	</tr>
	<c:forEach items="${hrRatifyEntryForm.fdEducations_Form}" var="fdEducations_FormItem" varStatus="vstatus">
		<tr KMSS_IsContentRow="1" data-celltr="true" class="muiAgendaNormalTr">
			<td>
				<input type="hidden" name="fdEducations_Form[${vstatus.index}].fdId" value="${fdEducations_FormItem.fdId}" />
				<input type="hidden" name="fdEducations_Form[${vstatus.index}].docMainId" value="${fdEducations_FormItem.docMainId}" />
				<table class="muiSimple">
					<tr>
						<td class="muiDetailTableNo" colspan="2" style="padding-left: 1rem;">
							<span class="muiDetailTableNoTxt">${vstatus.index+1}</span>
							<div class="muiDetailTableDel" onclick="del(this,'TABLE_DocList_fdEducations_Form');"><bean:message key="button.delete"/></div>
						</td>
					</tr>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="hr-ratify" key="hrRatifyEduExp.fdName"/>
						</td>
						<td>
							<xform:text property="fdEducations_Form[${vstatus.index}].fdName" value="${fdEducations_FormItem.fdName}" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyEduExp.fdName')}" validators="maxLength(200)" required="true" style="width:95%;" mobile="true" />
						</td>
					</tr>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="hr-ratify" key="hrRatifyEduExp.fdMajor"/>
						</td>
						<td>
							<xform:text property="fdEducations_Form[${vstatus.index}].fdMajor" value="${fdEducations_FormItem.fdMajor}" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyEduExp.fdMajor')}" validators="maxLength(200)" required="true" style="width:95%;" mobile="true" />
						</td>
					</tr>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="hr-ratify" key="hrRatifyEduExp.fdAcademic"/>
						</td>
						<td>
							<xform:select property="fdEducations_Form[${vstatus.index}].fdAcadeRecordId" value="${fdEducations_FormItem.fdAcadeRecordId}" showStatus="edit" mobile="true" required="true">
	                            <xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" 
	                            whereBlock="hrStaffPersonInfoSettingNew.fdType = 'fdHighestDegree'"/>
	                        </xform:select>
						</td>
					</tr>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="hr-ratify" key="hrRatifyEduExp.fdEntranceDate"/>
						</td>
						<td>
							<xform:datetime property="fdEducations_Form[${vstatus.index}].fdEntranceDate" value="${fdEducations_FormItem.fdEntranceDate}" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyEduExp.fdEntranceDate')}" required="true" dateTimeType="date" style="width:95%;" mobile="true" />
						</td>
					</tr>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="hr-ratify" key="hrRatifyEduExp.fdGraduationDate"/>
						</td>
						<td>
							<xform:datetime property="fdEducations_Form[${vstatus.index}].fdGraduationDate" value="${fdEducations_FormItem.fdGraduationDate}" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyEduExp.fdGraduationDate')}" dateTimeType="date" style="width:95%;" mobile="true" />
						</td>
					</tr>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="hr-ratify" key="hrRatifyEduExp.fdRemark"/>
						</td>
						<td>
							<xform:text property="fdEducations_Form[${vstatus.index}].fdRemark" value="${fdEducations_FormItem.fdRemark}" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyEduExp.fdRemark')}" style="width:95%;" mobile="true" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</c:forEach>
</table>
<div class="muiDetailTableAdd"  onclick="add('TABLE_DocList_fdEducations_Form');">
	<div class="mblTabBarButtonIconArea">
		<div class="mblTabBarButtonIconParent">
			<i class="mui mui-plus"></i>
		</div>
	</div>
	<div class="mblTabBarButtonLabel" ><bean:message bundle="hr-ratify" key="hrRatifyEduExp.addRatifyEduExp" /></div>
</div>