<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script type="text/javascript">Com_IncludeFile("doclist.js");</script>
<input type="hidden" name="fdRewardsPunishments_Flag" value="1">
<table class="muiAgendaNormal" width="100%" border="0" cellspacing="0" cellpadding="0" id="TABLE_DocList_fdRewardsPunishments_Form" >
	<script type="text/javascript">DocList_Info.push("TABLE_DocList_fdRewardsPunishments_Form");</script>
	<%--基准行--%>
	<tr data-dojo-type="mui/form/Template" class="muiAgendaNormalTr" data-celltr="true" KMSS_IsReferRow="1" style="display:none;" border='0'>
		<td>
			<table class="muiSimple">
				<tr>
					<td class="muiDetailTableNo" colspan="2" style="padding-left: 1rem;">
						<span class="muiDetailTableNoTxt">!{index}</span>
						<div class="muiDetailTableDel" onclick="del(this,'TABLE_DocList_fdRewardsPunishments_Form');"><bean:message key="button.delete"/></div>
					</td>
				</tr>
				<tr>
					<td class="muiTitle">
						<bean:message bundle="hr-ratify" key="hrRatifyRewPuni.fdName"/>
					</td>
					<td>
						<xform:text property="fdRewardsPunishments_Form[!{index}].fdName" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyRewPuni.fdName')}" validators="maxLength(200)" required="true" style="width:95%;" mobile="true" />
					</td>
				</tr>
				<tr>
					<td class="muiTitle">
						<bean:message bundle="hr-ratify" key="hrRatifyRewPuni.fdDate"/>
					</td>
					<td>
						<xform:datetime property="fdRewardsPunishments_Form[!{index}].fdDate" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyRewPuni.fdDate')}" dateTimeType="date" style="width:95%;" mobile="true" />
					</td>
				</tr>
				<tr>
					<td class="muiTitle">
						<bean:message bundle="hr-ratify" key="hrRatifyRewPuni.fdRemark"/>
					</td>
					<td>
						<xform:text property="fdRewardsPunishments_Form[!{index}].fdRemark" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyRewPuni.fdRemark')}" style="width:95%;" mobile="true" />
					</td>
				</tr>
			</table>
		</td>	
	</tr>
	<c:forEach items="${hrRatifyEntryForm.fdRewardsPunishments_Form}" var="fdRewardsPunishments_FormItem" varStatus="vstatus">
		<tr KMSS_IsContentRow="1" data-celltr="true" class="muiAgendaNormalTr">
			<td>
				<input type="hidden" name="fdRewardsPunishments_Form[${vstatus.index}].fdId" value="${fdRewardsPunishments_FormItem.fdId}" />
				<input type="hidden" name="fdRewardsPunishments_Form[${vstatus.index}].docMainId" value="${fdRewardsPunishments_FormItem.docMainId}" />
				<table class="muiSimple">
					<tr>
						<td class="muiDetailTableNo" colspan="2" style="padding-left: 1rem;">
							<span class="muiDetailTableNoTxt">${vstatus.index+1}</span>
							<div class="muiDetailTableDel" onclick="del(this,'TABLE_DocList_fdRewardsPunishments_Form');"><bean:message key="button.delete"/></div>
						</td>
					</tr>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="hr-ratify" key="hrRatifyRewPuni.fdName"/>
						</td>
						<td>
							<xform:text property="fdRewardsPunishments_Form[${vstatus.index}].fdName" value="${fdRewardsPunishments_FormItem.fdName}" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyRewPuni.fdName')}" validators="maxLength(200)" required="true" style="width:95%;" mobile="true" />
						</td>
					</tr>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="hr-ratify" key="hrRatifyRewPuni.fdDate"/>
						</td>
						<td>
							<xform:datetime property="fdRewardsPunishments_Form[${vstatus.index}].fdDate" value="${fdRewardsPunishments_FormItem.fdDate}" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyRewPuni.fdDate')}" dateTimeType="date" style="width:95%;" mobile="true" />
						</td>
					</tr>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="hr-ratify" key="hrRatifyRewPuni.fdRemark"/>
						</td>
						<td>
							<xform:text property="fdRewardsPunishments_Form[${vstatus.index}].fdRemark" value="${fdRewardsPunishments_FormItem.fdRemark}" showStatus="edit" subject="${lfn:message('hr-ratify:hrRatifyRewPuni.fdRemark')}" style="width:95%;" mobile="true" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</c:forEach>
</table>
<div class="muiDetailTableAdd"  onclick="add('TABLE_DocList_fdRewardsPunishments_Form');">
	<div class="mblTabBarButtonIconArea">
		<div class="mblTabBarButtonIconParent">
			<i class="mui mui-plus"></i>
		</div>
	</div>
	<div class="mblTabBarButtonLabel" ><bean:message bundle="hr-ratify" key="hrRatifyRewPuni.addRatifyRewPuni" /></div>
</div>	