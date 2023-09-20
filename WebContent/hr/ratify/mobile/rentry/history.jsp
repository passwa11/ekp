<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script type="text/javascript">Com_IncludeFile("doclist.js");</script>
<input type="hidden" name="fdHistory_Flag" value="1">
<table class="muiAgendaNormal" width="100%" border="0" cellspacing="0" cellpadding="0" id="TABLE_DocList_fdHistory_Form" >
	<script type="text/javascript">DocList_Info.push("TABLE_DocList_fdHistory_Form");</script>
	<%--基准行--%>
	<tr data-dojo-type="mui/form/Template" class="muiAgendaNormalTr" data-celltr="true" KMSS_IsReferRow="1" style="display:none;" border='0'>
		<td>
			<table class="muiSimple">
				<tr>
					<td class="muiDetailTableNo" colspan="2" style="padding-left: 1rem;">
						<span class="muiDetailTableNoTxt">!{index}</span>
						<div class="muiDetailTableDel" onclick="del(this,'TABLE_DocList_fdHistory_Form');"><bean:message key="button.delete"/></div>
					</td>
				</tr>
				<tr>
					<td class="muiTitle">
						<bean:message bundle="hr-ratify" key="hrRatifyHistory.fdName"/>
					</td>
					<td>
						<xform:text property="fdHistory_Form[!{index}].fdName" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyHistory.fdName')}" validators="maxLength(200)" required="true" style="width:95%;" mobile="true" />
					</td>
				</tr>
				<tr>
					<td class="muiTitle">
						<bean:message bundle="hr-ratify" key="hrRatifyHistory.fdPost"/>
					</td>
					<td>
						<xform:text property="fdHistory_Form[!{index}].fdPost" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyHistory.fdPost')}" validators="maxLength(200)" style="width:95%;" mobile="true" />
					</td>
				</tr>
				<tr>
					<td class="muiTitle">
						<bean:message bundle="hr-ratify" key="hrRatifyHistory.fdStartDate"/>
					</td>
					<td>
						<xform:datetime property="fdHistory_Form[!{index}].fdStartDate" validators="before compareFdStartDateHistory" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyHistory.fdStartDate')}" required="true" dateTimeType="date" style="width:95%;" mobile="true" />
					</td>
				</tr>
				<tr>
					<td class="muiTitle">
						<bean:message bundle="hr-ratify" key="hrRatifyHistory.fdEndDate"/>
					</td>
					<td>
						<xform:datetime property="fdHistory_Form[!{index}].fdEndDate" validators="compareFdStartDateHistory" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyHistory.fdEndDate')}" dateTimeType="date" style="width:95%;" mobile="true" />
					</td>
				</tr>
				<tr>
					<td class="muiTitle">
						<bean:message bundle="hr-ratify" key="hrRatifyHistory.fdDesc"/>
					</td>
					<td>
						<xform:text property="fdHistory_Form[!{index}].fdDesc" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyHistory.fdDesc')}" style="width:95%;" mobile="true" />
					</td>
				</tr>
				<tr>
					<td class="muiTitle">
						<bean:message bundle="hr-ratify" key="hrRatifyHistory.fdLeaveReason"/>
					</td>
					<td>
						<xform:text property="fdHistory_Form[!{index}].fdLeaveReason" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyHistory.fdLeaveReason')}" style="width:95%;" mobile="true" />
					</td>
				</tr>
			</table>
		</td>	
	</tr>
	<c:forEach items="${hrRatifyEntryForm.fdHistory_Form}" var="fdHistory_FormItem" varStatus="vstatus">
		<tr KMSS_IsContentRow="1" data-celltr="true" class="muiAgendaNormalTr">
			<td>
				<input type="hidden" name="fdHistory_Form[${vstatus.index}].fdId" value="${fdHistory_FormItem.fdId}" />
				<input type="hidden" name="fdHistory_Form[${vstatus.index}].docMainId" value="${fdHistory_FormItem.docMainId}" />
				<table class="muiSimple">
					<tr>
						<td class="muiDetailTableNo" colspan="2" style="padding-left: 1rem;">
							<span class="muiDetailTableNoTxt">${vstatus.index+1}</span>
							<div class="muiDetailTableDel" onclick="del(this,'TABLE_DocList_fdHistory_Form');"><bean:message key="button.delete"/></div>
						</td>
					</tr>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="hr-ratify" key="hrRatifyHistory.fdName"/>
						</td>
						<td>
							<xform:text property="fdHistory_Form[${vstatus.index}].fdName" value="${fdHistory_FormItem.fdName}" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyHistory.fdName')}" validators="maxLength(200)" required="true" style="width:95%;" mobile="true" />
						</td>
					</tr>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="hr-ratify" key="hrRatifyHistory.fdPost"/>
						</td>
						<td>
							<xform:text property="fdHistory_Form[${vstatus.index}].fdPost" value="${fdHistory_FormItem.fdPost}" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyHistory.fdPost')}" validators="maxLength(200)" style="width:95%;" mobile="true" />
						</td>
					</tr>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="hr-ratify" key="hrRatifyHistory.fdStartDate"/>
						</td>
						<td>
							<xform:datetime property="fdHistory_Form[${vstatus.index}].fdStartDate" value="${fdHistory_FormItem.fdStartDate}" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyHistory.fdStartDate')}" required="true" dateTimeType="date" style="width:95%;" mobile="true" />
						</td>
					</tr>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="hr-ratify" key="hrRatifyHistory.fdEndDate"/>
						</td>
						<td>
							<xform:datetime property="fdHistory_Form[${vstatus.index}].fdEndDate" value="${fdHistory_FormItem.fdEndDate}" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyHistory.fdEndDate')}" dateTimeType="date" style="width:95%;" mobile="true" />
						</td>
					</tr>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="hr-ratify" key="hrRatifyHistory.fdDesc"/>
						</td>
						<td>
							<xform:text property="fdHistory_Form[${vstatus.index}].fdDesc" value="${fdHistory_FormItem.fdDesc}" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyHistory.fdDesc')}" style="width:95%;" mobile="true" />
						</td>
					</tr>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="hr-ratify" key="hrRatifyHistory.fdLeaveReason"/>
						</td>
						<td>
							<xform:text property="fdHistory_Form[${vstatus.index}].fdLeaveReason" value="${fdHistory_FormItem.fdLeaveReason}" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyHistory.fdLeaveReason')}" style="width:95%;" mobile="true" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</c:forEach>
</table>
<div class="muiDetailTableAdd"  onclick="add('TABLE_DocList_fdHistory_Form');">
	<div class="mblTabBarButtonIconArea">
		<div class="mblTabBarButtonIconParent">
			<i class="mui mui-plus"></i>
		</div>
	</div>
	<div class="mblTabBarButtonLabel" ><bean:message bundle="hr-ratify" key="hrRatifyHistory.addRatifyHistory" /></div>
</div>