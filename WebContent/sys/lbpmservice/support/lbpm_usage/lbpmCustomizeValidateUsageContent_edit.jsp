<%@page import="java.util.Locale"%>
<%@page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmUsageContent"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextareaGroupTag"%>
<%
	pageContext.setAttribute("isLangSuportEnabled", MultiLangTextareaGroupTag.isLangSuportEnabled());
	request.setAttribute("vEnter", "\r\n\t");
%>

<style type="text/css">
.calcBtn {
	display: block;
	margin: 0;
	padding: 5px 0;
	width: 100%;
	color: #333;
	font-size: 18px;
	font-weight: 400;
	line-height: 1.42857143;
	text-align: center;
	white-space: nowrap;
	vertical-align: middle;
	text-transform: capitalize;
	-ms-touch-action: manipulation;
			touch-action: manipulation;
	cursor: pointer;
	-webkit-user-select: none;
		 -moz-user-select: none;
			-ms-user-select: none;
					user-select: none;
  background-image: none;
  background-color: #fff;
	border: 1px solid transparent;
	border-radius: 0;
	outline: 0;
	transition-duration: .3s;
	-wekbit-box-sizing: content-box;
	        box-sizing: content-box;
}
.calcBtn:hover,
.calcBtn:focus{
  text-decoration: none;
	color: #fff;
  background-color: #4285f4;
  border-color: #4285f4;
}
.calcBtn:active {
  background-image: none;
  outline: 0;
  -webkit-box-shadow: inset 0 3px 5px rgba(0, 0, 0, .15);
          box-shadow: inset 0 3px 5px rgba(0, 0, 0, .15);
}

.resultBtn{
	font-size: 14px;
	padding: 2px 10px;
	border-radius: 4px;
	border-color: #d2d2d2;
	margin: 0 5px;
	width: auto;
	display: inline-block;
}

</style>


<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title"><bean:message bundle="sys-lbpmservice-support" key="table.lbpmUsageContent"/></span>
		</h2>
		<html:form action="/sys/lbpmservice/support/lbpm_usage/lbpmCommunicateUsageAction.do">
		<center>
		<div style="margin:auto auto 60px;">
		<table class="tb_normal" width=95%>
			<!-- 默认审批通过意见 -->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.handler.pass"/>
					 
				</td>
				<td width=85%>
					<table class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" align="center" width=80%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.content"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isValidated"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isExcludeValidated"/>
							</td>
							
						</tr>
						<tr>
							<td width=80%>
									<c:if test="${!isLangSuportEnabled }">
											<xform:textarea property="customizeUsageContentValidated" style="width:100%;height:80px" validators="maxLength(4000)" showStatus="edit"/>
									</c:if>
									
									<c:if test="${isLangSuportEnabled }">
										<xlang:lbpmlangAreaNew validators="maxLength(4000)" property="customizeUsageContentValidated" alias="customizeUsageContentValidated" style="width:100%;height:80px" />
										
									</c:if>
									<html:hidden property="customizeUsageContentValidated_lang" />
							</td>
							
							<td align="center">
								<ui:switch property="isPassContentValidated" id="isPassContentValidated" onValueChange="switchValidate('isPassContentValidated','isPassContentExcludeValidated','isPassContentRequired')"></ui:switch>
							</td>
							<td align="center">
								<ui:switch property="isPassContentExcludeValidated" id="isPassContentExcludeValidated" onValueChange="switchExcludeValidate('isPassContentValidated','isPassContentExcludeValidated','isPassContentRequired')"></ui:switch>
							</td>
						</tr>
					</table>
				
				</td>
			</tr>
			
			
			
			
			
			
			<!-- 默认审批驳回意见 -->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.handler.refuse"/>
				</td>
				<td width=85%>
					<table class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" align="center" width=80%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.content"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isValidated"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isExcludeValidated"/>
							</td>
							
						</tr>
						<tr>
							<td width=80%>
							
							<c:if test="${!isLangSuportEnabled }">
								<xform:textarea property="customizeUsageContentRefuseValidated" style="width:100%;height:80px" validators="maxLength(4000)"  showStatus="edit"/>
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentRefuseValidated" alias="customizeUsageContentRefuseValidated" validators="maxLength(4000)" style="width:100%;height:80px"/>
							</c:if>
							<html:hidden property="customizeUsageContentRefuseValidated_lang" />
							</td>
							
							<td align="center">
								<ui:switch property="isRefuseContentValidated" id="isRefuseContentValidated" onValueChange="switchValidate('isRefuseContentValidated','isRefuseContentExcludeValidated','isRefuseContentRequired')"></ui:switch>
							</td>
							<td align="center">
								<ui:switch property="isRefuseContentExcludeValidated" id="isRefuseContentExcludeValidated" onValueChange="switchExcludeValidate('isRefuseContentValidated','isRefuseContentExcludeValidated')"></ui:switch>
							</td>
						</tr>
					</table>
				
				</td>
			</tr>
			
			
			<!-- 默认审批转办意见 -->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.handler.commission"/>
				</td>
				<td width=85%>
					<table class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" align="center" width=80%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.content"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isValidated"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isExcludeValidated"/>
							</td>
							
						</tr>
						<tr>
							<td width=80%>
							
							<c:if test="${!isLangSuportEnabled }">
								
										<xform:textarea property="customizeUsageContentCommissionValidated" style="width:100%;height:80px" validators="maxLength(4000)"  showStatus="edit" />
									
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentCommissionValidated" alias="customizeUsageContentCommissionValidated" validators="maxLength(4000)" style="width:100%;height:80px" />
							</c:if>
							<html:hidden property="customizeUsageContentCommissionValidated_lang" />

							</td>
							
							<td align="center">
								<ui:switch property="isCommissionContentValidated" id="isCommissionContentValidated" onValueChange="switchValidate('isCommissionContentValidated','isCommissionContentExcludeValidated','isCommissionContentRequired')"></ui:switch>
							</td>
							<td align="center">
								<ui:switch property="isCommissionContentExcludeValidated" id="isCommissionContentExcludeValidated" onValueChange="switchExcludeValidate('isCommissionContentValidated','isCommissionContentExcludeValidated','isCommissionContentRequired')"></ui:switch>
							</td>
						</tr>
					</table>
				
				</td>
			</tr>
			
			
			<!-- 默认审批沟通意见 -->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.handler.communicate"/>
				</td>
				<td width=85%>
					<table class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" align="center" width=80%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.content"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isValidated"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isExcludeValidated"/>
							</td>
							
						</tr>
						<tr>
							<td width=80%>
							
							<c:if test="${!isLangSuportEnabled }">
								
										<xform:textarea property="customizeUsageContentCommunicateValidated" style="width:100%;height:80px" validators="maxLength(4000)" showStatus="edit"/>
									
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentCommunicateValidated" alias="customizeUsageContentCommunicateValidated" validators="maxLength(4000)" style="width:100%;height:80px" />
							</c:if>
							<html:hidden property="customizeUsageContentCommunicateValidated_lang" />
							</td>
							
							<td align="center">
								<ui:switch property="isCommunicateContentValidated" id="isCommunicateContentValidated" onValueChange="switchValidate('isCommunicateContentValidated','isCommunicateContentExcludeValidated','isCommunicateContentRequired')"></ui:switch>
							</td>
							<td align="center">
								<ui:switch property="isCommunicateContentExcludeValidated" id="isCommunicateContentExcludeValidated" onValueChange="switchExcludeValidate('isCommunicateContentValidated','isCommunicateContentExcludeValidated','isCommunicateContentRequired')"></ui:switch>
							</td>
						</tr>
					</table>
				
				</td>
			</tr>
			
			
			
			
			<!-- 默认审批废弃意见 -->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.handler.abandon"/>
				</td>
				<td width=85%>
					<table class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" align="center" width=80%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.content"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isValidated"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isExcludeValidated"/>
							</td>
							
						</tr>
						<tr>
							<td width=80%>
							
							<c:if test="${!isLangSuportEnabled }">
								 
										<xform:textarea property="customizeUsageContentAandonValidated" style="width:100%;height:80px" validators="maxLength(4000)" showStatus="edit"/>
									
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentAandonValidated" alias="customizeUsageContentAandonValidated" validators="maxLength(4000)" style="width:100%;height:80px" />
							</c:if>
							<html:hidden property="customizeUsageContentAandonValidated_lang" />
							</td>
							
							<td align="center">
								<ui:switch property="isAbandonContentValidated" id="isAbandonContentValidated" onValueChange="switchValidate('isAbandonContentValidated','isAbandonContentExcludeValidated','isAbandonContentRequired')"></ui:switch>
							</td>
							<td align="center">
								<ui:switch property="isAbandonContentExcludeValidated" id="isAbandonContentExcludeValidated" onValueChange="switchExcludeValidate('isAbandonContentValidated','isAbandonContentExcludeValidated','isAbandonContentRequired')"></ui:switch>
							</td>
						</tr>
					</table>
				
				</td>
			</tr>
			
			
			
			<!-- 默认审批超级驳回意见 -->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.handler.superRefuse"/>
				</td>
				<td width=85%>
					<table class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" align="center" width=80%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.content"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isValidated"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isExcludeValidated"/>
							</td>
							
						</tr>
						<tr>
							<td width=80%>
							
							<c:if test="${!isLangSuportEnabled }">
								 
										<xform:textarea property="customizeUsageContentSuperRefuseValidated" style="width:100%;height:80px" validators="maxLength(4000)" showStatus="edit"/>
									
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentSuperRefuseValidated" alias="customizeUsageContentSuperRefuseValidated" validators="maxLength(4000)" style="width:100%;height:80px"/>
							</c:if>
							<html:hidden property="customizeUsageContentSuperRefuseValidated_lang" />
							</td>
							
							<td align="center">
								<ui:switch property="isSuperRefuseContentValidated" id="isSuperRefuseContentValidated" onValueChange="switchValidate('isSuperRefuseContentValidated','isSuperRefuseContentExcludeValidated','isSuperRefuseContentRequired')"></ui:switch>
							</td>
							<td align="center">
								<ui:switch property="isSuperRefuseContentExcludeValidated" id="isSuperRefuseContentExcludeValidated" onValueChange="switchExcludeValidate('isSuperRefuseContentValidated','isSuperRefuseContentExcludeValidated','isSuperRefuseContentRequired')"></ui:switch>								
							</td>
						</tr>
					</table>
				
				</td>
			</tr>
			
			
			
			<!-- 默认审批补签意见 -->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.handler.additionSign"/>
				</td>
				<td width=85%>
					<table class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" align="center" width=80%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.content"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isValidated"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isExcludeValidated"/>
							</td>
							
						</tr>
						<tr>
							<td width=80%>
							
							<c:if test="${!isLangSuportEnabled }">
								
										<xform:textarea property="customizeUsageContentAdditionSignValidated" style="width:100%;height:80px" validators="maxLength(4000)" showStatus="edit" />
									
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentAdditionSignValidated" alias="customizeUsageContentAdditionSignValidated" validators="maxLength(4000)" style="width:100%;height:80px" />
							</c:if>
							<html:hidden property="customizeUsageContentAdditionSignValidated_lang" />
							</td>
							
							<td align="center">
								<ui:switch property="isAdditionSignContentValidated" id="isAdditionSignContentValidated" onValueChange="switchValidate('isAdditionSignContentValidated','isAdditionSignContentExcludeValidated','isAdditionSignContentRequired')"></ui:switch>
							</td>
							<td align="center">
								<ui:switch property="isAdditionSignContentExcludeValidated" id="isAdditionSignContentExcludeValidated" onValueChange="switchExcludeValidate('isAdditionSignContentValidated','isAdditionSignContentExcludeValidated','isAdditionSignContentRequired')"></ui:switch>
							</td>
						</tr>
					</table>
				
				</td>
			</tr>
			
			
			
			<!-- 默认审批加签意见 -->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.handler.assign"/>
				</td>
				<td width=85%>
					<table class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" align="center" width=80%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.content"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isValidated"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isExcludeValidated"/>
							</td>
							
						</tr>
						<tr>
							<td width=80%>
							
							<c:if test="${!isLangSuportEnabled }">
										<xform:textarea property="customizeUsageContentAssignValidated" style="width:100%;height:80px" validators="maxLength(4000)" showStatus="edit" />
								
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentAssignValidated" alias="customizeUsageContentAssignValidated" validators="maxLength(4000)" style="width:100%;height:80px" />
							</c:if>
							<html:hidden property="customizeUsageContentAssignValidated_lang" />
							</td>
							
							<td align="center">
								<ui:switch property="isAssignContentValidated" id="isAssignContentValidated" onValueChange="switchValidate('isAssignContentValidated','isAssignContentExcludeValidated','isAssignContentRequired')"></ui:switch>
							</td>
							<td align="center">
								<ui:switch property="isAssignContentExcludeValidated" id="isAssignContentExcludeValidated" onValueChange="switchExcludeValidate('isAssignContentValidated','isAssignContentExcludeValidated','isAssignContentRequired')"></ui:switch>
							</td>
						</tr>
					</table>
				
				</td>
			</tr>
			
			
			<!-- 默认审批通过加签意见 -->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.handler.assignPass"/>
				</td>
				<td width=85%>
					<table class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" align="center" width=80%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.content"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isValidated"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isExcludeValidated"/>
							</td>
							
						</tr>
						<tr>
							<td width=80%>
							
							<c:if test="${!isLangSuportEnabled }">
										<xform:textarea property="customizeUsageContentAssignPassValidated" style="width:100%;height:80px" validators="maxLength(4000)" showStatus="edit" />
								
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentAssignPassValidated" alias="customizeUsageContentAssignPassValidated" validators="maxLength(4000)" style="width:100%;height:80px" />
							</c:if>
							<html:hidden property="customizeUsageContentAssignPassValidated_lang" />
							</td>
							
							<td align="center">
								<ui:switch property="isAssignPassContentValidated" id="isAssignPassContentValidated" onValueChange="switchValidate('isAssignPassContentValidated','isAssignPassContentExcludeValidated','isAssignPassContentRequired')"></ui:switch>
							</td>
							<td align="center">
								<ui:switch property="isAssignPassContentExcludeValidated" id="isAssignPassContentExcludeValidated" onValueChange="switchExcludeValidate('isAssignPassContentValidated','isAssignPassContentExcludeValidated','isAssignPassContentRequired')"></ui:switch>
							</td>
						</tr>
					</table>
				
				</td>
			</tr>
			
			
			
			
			<!-- 默认审批退回加签意见 -->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.handler.assignRefuse"/>
				</td>
				<td width=85%>
					<table class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" align="center" width=80%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.content"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isValidated"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isExcludeValidated"/>
							</td>
							
						</tr>
						<tr>
							<td width=80%>
							
							<c:if test="${!isLangSuportEnabled }">
										<xform:textarea property="customizeUsageContentAssignRefuseValidated" style="width:100%;height:80px" validators="maxLength(4000)" showStatus="edit"/>
								
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentAssignRefuseValidated" alias="customizeUsageContentAssignRefuseValidated" validators="maxLength(4000)" style="width:100%;height:80px" />
							</c:if>
							<html:hidden property="customizeUsageContentAssignRefuseValidated_lang" />
							</td>
							
							<td align="center">
								<ui:switch property="isAssignRefuseContentValidated" id="isAssignRefuseContentValidated" onValueChange="switchValidate('isAssignRefuseContentValidated','isAssignRefuseContentExcludeValidated','isAssignRefuseContentRequired')"></ui:switch>
							</td>
							<td align="center">
								<ui:switch property="isAssignRefuseContentExcludeValidated" id="isAssignRefuseContentExcludeValidated" onValueChange="switchExcludeValidate('isAssignRefuseContentValidated','isAssignRefuseContentExcludeValidated','isAssignRefuseContentRequired')"></ui:switch>
							</td>
						</tr>
					</table>
				
				</td>
			</tr>
			
			<!-- 默认审批跳转意见 -->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.handler.jump"/>
				</td>
				<td width=85%>
					<table class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" align="center" width=80%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.content"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isValidated"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isExcludeValidated"/>
							</td>
							
						</tr>
						<tr>
							<td width=80%>
							
							<c:if test="${!isLangSuportEnabled }">
										<xform:textarea property="customizeUsageContentJumpValidated" style="width:100%;height:80px" validators="maxLength(4000)" showStatus="edit"/>
								
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentJumpValidated" alias="customizeUsageContentJumpValidated" validators="maxLength(4000)" style="width:100%;height:80px" />
							</c:if>
							<html:hidden property="customizeUsageContentJumpValidated_lang" />
							</td>
							
							<td align="center">
								<ui:switch property="isJumpContentValidated" id="isJumpContentValidated" onValueChange="switchValidate('isJumpContentValidated','isJumpContentExcludeValidated','isJumpContentRequired')"></ui:switch>
							</td>
							<td align="center">
								<ui:switch property="isJumpContentExcludeValidated" id="isJumpContentExcludeValidated" onValueChange="switchExcludeValidate('isJumpContentValidated','isJumpContentExcludeValidated','isJumpContentRequired')"></ui:switch>
							</td>
						</tr>
					</table>
				
				</td>
			</tr>
			
			<!-- 默认审批节点暂停意见 -->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.handler.nodeSuspend"/>
				</td>
				<td width=85%>
					<table class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" align="center" width=80%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.content"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isValidated"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isExcludeValidated"/>
							</td>
							
						</tr>
						<tr>
							<td width=80%>
							
							<c:if test="${!isLangSuportEnabled }">
										<xform:textarea property="customizeUsageContentNodeSuspendValidated" style="width:100%;height:80px" validators="maxLength(4000)" showStatus="edit" />
							
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentNodeSuspendValidated" alias="customizeUsageContentNodeSuspendValidated" validators="maxLength(4000)" style="width:100%;height:80px"/>
							</c:if>
							<html:hidden property="customizeUsageContentNodeSuspendValidated_lang" />
							</td>
							
							<td align="center">
								<ui:switch property="isNodeSuspendContentValidated" id="isNodeSuspendContentValidated"  onValueChange="switchValidate('isNodeSuspendContentValidated','isNodeSuspendContentExcludeValidated','isNodeSuspendContentRequired')"></ui:switch>
							</td>
							<td align="center">
								<ui:switch property="isNodeSuspendContentExcludeValidated"  id="isNodeSuspendContentExcludeValidated" onValueChange="switchExcludeValidate('isNodeSuspendContentValidated','isNodeSuspendContentExcludeValidated','isNodeSuspendContentRequired')"></ui:switch>
							</td>
						</tr>
					</table>
				
				</td>
			</tr>
			
			
			
			<!-- 默认审批节点唤醒意见 -->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.handler.nodeResume"/>
				</td>
				<td width=85%>
					<table class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" align="center" width=80%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.content"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isValidated"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isExcludeValidated"/>
							</td>
							
						</tr>
						<tr>
							<td width=80%>
							
							<c:if test="${!isLangSuportEnabled }">
										<xform:textarea property="customizeUsageContentNodeResumeValidated" style="width:100%;height:80px" validators="maxLength(4000)" showStatus="edit"/>
								
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentNodeResumeValidated" alias="customizeUsageContentNodeResumeValidated" validators="maxLength(4000)" style="width:100%;height:80px" />
							</c:if>
							<html:hidden property="customizeUsageContentNodeResumeValidated_lang" />
							</td>
							
							<td align="center">
								<ui:switch property="isNodeResumeContentValidated" id="isNodeResumeContentValidated" onValueChange="switchValidate('isNodeResumeContentValidated','isNodeResumeContentExcludeValidated','isNodeResumeContentRequired')"></ui:switch>
							</td>
							<td align="center">
								<ui:switch property="isNodeResumeContentExcludeValidated" id="isNodeResumeContentExcludeValidated" onValueChange="switchExcludeValidate('isNodeResumeContentValidated','isNodeResumeContentExcludeValidated','isNodeResumeContentRequired')"></ui:switch>
							</td>
						</tr>
					</table>
				
				</td>
			</tr>
			
			
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="lbpmUsage.fdDescription"/>
				</td><td width="85%">
					<bean:message  bundle="sys-lbpmservice-support" key="lbpmUsageContent.fdDescription.details.3"/><br>
					<bean:message  bundle="sys-lbpmservice-support" key="lbpmUsageContent.fdDescription.details.4"/>
				</td>
			</tr>
			<tr>
				<td  colspan="2" align="center" >
					<!-- 确定 -->
					<input class="calcBtn resultBtn" type=button value="<bean:message key="button.ok"/>" onclick="setCustomizeUsageContentValidate();">
					<input class="calcBtn resultBtn" type="button" value="<bean:message key="button.cancel"/>" onClick="cancel();">
				</td>
			</tr>
		</table>
		</div>
		</center>
		<center >
				
				
			</center>
			
		</html:form>
		
		<%
			//LbpmUsageContent lbpmUsageContent = new LbpmUsageContent();
			//pageContext.setAttribute("lbpmUsageContent", lbpmUsageContent);
		%>
		<script type="text/javascript">
		
		<%
		Locale locale =Locale.getDefault();
		%>
		var currentLang ='<%=MultiLangTextareaGroupTag.getUserLangKey()%>';
		
		var langJson = <%=MultiLangTextareaGroupTag.getLangsJsonStr()%>;
		var isLangSuportEnabled = <%=MultiLangTextareaGroupTag.isLangSuportEnabled()%>;

		// 开启校验开关时必须保证必填开关已经开启
		function switchValidate(op1,op2,op3){
			
			if(LUI(op1).checkbox.prop('checked')){
				if((LUI(op2).checkbox.prop('checked'))){
					  seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
						  dialog.alert("${lfn:message('sys-lbpmservice-support:lbpmUsageContent.fdDescription.details.6')}");
			          }); 
						LUI(op1).checkbox.prop('checked',false);
						$("input[name='"+op1+"']").val("false");
				}else{
					context.LUI(op3).checkbox.prop('checked',true);
					context.document.getElementsByName(op3)[0].value = "true";
				}
			}
			
		}
		// 开启校验开关时必须保证必填开关已经开启
		function switchExcludeValidate(op1,op2,op3){
			
			if(LUI(op2).checkbox.prop('checked')){
				if(LUI(op1).checkbox.prop('checked')){
					seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
		                dialog.alert("${lfn:message('sys-lbpmservice-support:lbpmUsageContent.fdDescription.details.5')}");
		          });
					LUI(op2).checkbox.prop('checked',false);
					$("input[name='"+op2+"']").val("false");
				}else{
					context.LUI(op3).checkbox.prop('checked',true);
					/* $("input[name='"+op3+"']",context).val("true"); */
					context.document.getElementsByName(op3)[0].value = "true";
				}
		}
			
		}
		
		LUI.ready(function(){
			setTimeout(function(){
				dialogUi = window.$dialog;
				params = dialogUi.___params;
				context = dialogUi.___params.win;
				parentContext = dialogUi.___params.val;
				//支持多语言
				if(isLangSuportEnabled){
					//扩展属性值
					//var lbpmCustomizeContentJsonStr=params.lbpmCustomizeParameterValidate;
					var lbpmCustomizeContentJsonStr=$("input[name='ext_lbpmCustomizeValidateContentJson']",context.document).val();
					if(lbpmCustomizeContentJsonStr){
						var lbpmCustomizeContentJson=JSON.parse(lbpmCustomizeContentJsonStr);
						//有扩展属性值
						if(lbpmCustomizeContentJson){
							initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentValidated','isPassContentExcludeValidated');
							initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentRefuseValidated','isRefuseContentExcludeValidated');
							initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentCommissionValidated','isCommissionContentExcludeValidated');
							initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentCommunicateValidated','isCommunicateContentExcludeValidated');
							initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentAandonValidated','isAbandonContentExcludeValidated');
							initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentSuperRefuseValidated','isSuperRefuseContentExcludeValidated');
							initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentAdditionSignValidated','isAdditionSignContentExcludeValidated');
							initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentAssignValidated','isAssignContentExcludeValidated');
							initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentAssignPassValidated','isAssignPassContentExcludeValidated');
							initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentAssignRefuseValidated','isAssignRefuseContentExcludeValidated');
							initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentJumpValidated','isJumpContentExcludeValidated');
							initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentNodeSuspendValidated','isNodeSuspendContentExcludeValidated');
							initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentNodeResumeValidated','isNodeResumeContentExcludeValidated');
							
						 	initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentValidated','isPassContentValidated');
							initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentRefuseValidated','isRefuseContentValidated');
							initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentCommissionValidated','isCommissionContentValidated');
							initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentCommunicateValidated','isCommunicateContentValidated');
							initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentAandonValidated','isAbandonContentValidated');
							initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentSuperRefuseValidated','isSuperRefuseContentValidated');
							initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentAdditionSignValidated','isAdditionSignContentValidated');
							initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentAssignValidated','isAssignContentValidated');
							initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentAssignPassValidated','isAssignPassContentValidated');
							initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentAssignRefuseValidated','isAssignRefuseContentValidated');
							initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentJumpValidated','isJumpContentValidated');
							initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentNodeSuspendValidated','isNodeSuspendContentValidated');
							initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentNodeResumeValidated','isNodeResumeContentValidated');
							 
						}
					}else{
						initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent4Lang)}","customizeUsageContentValidated","isPassContentExcludeValidated","${lbpmUsageContent.isPassContentExcludeValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent)}");
						initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_refuse4Lang)}","customizeUsageContentRefuseValidated","isRefuseContentExcludeValidated","${lbpmUsageContent.isRefuseContentExcludeValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_refuse4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_refuse)}");
						initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_commission4Lang)}","customizeUsageContentCommissionValidated","isCommissionContentExcludeValidated","${lbpmUsageContent.isCommissionContentExcludeValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_commission4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_commission)}");
						initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_communicate4Lang)}","customizeUsageContentCommunicateValidated","isCommunicateContentExcludeValidated","${lbpmUsageContent.isCommunicateContentExcludeValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_communicate4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_communicate)}");
						initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_abandon4Lang)}","customizeUsageContentAandonValidated","isAbandonContentExcludeValidated","${lbpmUsageContent.isAbandonContentExcludeValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_abandon4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_abandon)}");
						initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_superRefuse4Lang)}","customizeUsageContentSuperRefuseValidated","isSuperRefuseContentExcludeValidated","${lbpmUsageContent.isSuperRefuseContentExcludeValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_superRefuse4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_superRefuse)}");
						initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_additionSign4Lang)}","customizeUsageContentAdditionSignValidated","isAdditionSignContentExcludeValidated","${lbpmUsageContent.isAdditionSignContentExcludeValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_additionSign4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_additionSign)}");
						initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assign4Lang)}","customizeUsageContentAssignValidated","isAssignContentExcludeValidated","${lbpmUsageContent.isAssignContentExcludeValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assign4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assign)}");
						initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignPass4Lang)}","customizeUsageContentAssignPassValidated","isAssignPassContentExcludeValidated","${lbpmUsageContent.isAssignPassContentExcludeValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignPass4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignPass)}");
						initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignRefuse4Lang)}","customizeUsageContentAssignRefuseValidated","isAssignRefuseContentExcludeValidated","${lbpmUsageContent.isAssignRefuseContentExcludeValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignRefuse4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignRefuse)}");
						initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_jump4Lang)}","customizeUsageContentJumpValidated","isJumpContentExcludeValidated","${lbpmUsageContent.isJumpContentExcludeValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_jump4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_jump)}");
						initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeSuspend4Lang)}","customizeUsageContentNodeSuspendValidated","isNodeSuspendContentExcludeValidated","${lbpmUsageContent.isNodeSuspendContentExcludeValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeSuspend4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeSuspend)}");
						initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeResume4Lang)}","customizeUsageContentNodeResumeValidated","isNodeResumeContentExcludeValidated","${lbpmUsageContent.isNodeResumeContentExcludeValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeResume4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeResume)}");
						
						initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent4Lang)}","customizeUsageContentValidated","isPassContentValidated","${lbpmUsageContent.isPassContentValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent)}");
						initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_refuse4Lang)}","customizeUsageContentRefuseValidated","isRefuseContentValidated","${lbpmUsageContent.isRefuseContentValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_refuse4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_refuse)}");
						initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_commission4Lang)}","customizeUsageContentCommissionValidated","isCommissionContentValidated","${lbpmUsageContent.isCommissionContentValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_commission4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_commission)}");
						initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_communicate4Lang)}","customizeUsageContentCommunicateValidated","isCommunicateContentValidated","${lbpmUsageContent.isCommunicateContentValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_communicate4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_communicate)}");
						initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_abandon4Lang)}","customizeUsageContentAandonValidated","isAbandonContentValidated","${lbpmUsageContent.isAbandonContentValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_abandon4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_abandon)}");
						initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_superRefuse4Lang)}","customizeUsageContentSuperRefuseValidated","isSuperRefuseContentValidated","${lbpmUsageContent.isSuperRefuseContentValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_superRefuse4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_superRefuse)}");
						initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_additionSign4Lang)}","customizeUsageContentAdditionSignValidated","isAdditionSignContentValidated","${lbpmUsageContent.isAdditionSignContentValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_additionSign4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_additionSign)}");
						initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assign4Lang)}","customizeUsageContentAssignValidated","isAssignContentValidated","${lbpmUsageContent.isAssignContentValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assign4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assign)}");
						initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignPass4Lang)}","customizeUsageContentAssignPassValidated","isAssignPassContentValidated","${lbpmUsageContent.isAssignPassContentValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignPass4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignPass)}");
						initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignRefuse4Lang)}","customizeUsageContentAssignRefuseValidated","isAssignRefuseContentValidated","${lbpmUsageContent.isAssignRefuseContentValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignRefuse4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignRefuse)}");
						initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_jump4Lang)}","customizeUsageContentJumpValidated","isAssignRefuseContentValidated","${lbpmUsageContent.isJumpContentValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_jump4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_jump)}");
						initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeSuspend4Lang)}","customizeUsageContentNodeSuspendValidated","isNodeSuspendContentValidated","${lbpmUsageContent.isNodeSuspendContentValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeSuspend4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeSuspend)}");
						initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeResume4Lang)}","customizeUsageContentNodeResumeValidated","isNodeResumeContentValidated","${lbpmUsageContent.isNodeResumeContentValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeResume4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeResume)}");
						
					}
				}else{
					var lbpmCustomizeContentJsonStr=$("input[id='lbpmCustomizeValidateJson']",context.document).val();
					if(lbpmCustomizeContentJsonStr){
						var lbpmCustomizeContentJson=JSON.parse(lbpmCustomizeContentJsonStr);
						//有扩展属性值
						if(lbpmCustomizeContentJson){
							initHasValuenoSuportLang(lbpmCustomizeContentJson,'isPassContentExcludeValidated','customizeUsageContentValidated');
							initHasValuenoSuportLang(lbpmCustomizeContentJson,'isRefuseContentExcludeValidated','customizeUsageContentRefuseValidated');
							initHasValuenoSuportLang(lbpmCustomizeContentJson,'isCommissionContentExcludeValidated','customizeUsageContentCommissionValidated');
							initHasValuenoSuportLang(lbpmCustomizeContentJson,'isCommunicateContentExcludeValidated','customizeUsageContentCommunicateValidated');
							initHasValuenoSuportLang(lbpmCustomizeContentJson,'isAbandonContentExcludeValidated','customizeUsageContentAandonValidated');
							initHasValuenoSuportLang(lbpmCustomizeContentJson,'isSuperRefuseContentExcludeValidated','customizeUsageContentSuperRefuseValidated');
							initHasValuenoSuportLang(lbpmCustomizeContentJson,'isAdditionSignContentExcludeValidated','customizeUsageContentAdditionSignValidated');
							initHasValuenoSuportLang(lbpmCustomizeContentJson,'isAssignContentExcludeValidated','customizeUsageContentAssignValidated');
							initHasValuenoSuportLang(lbpmCustomizeContentJson,'isAssignPassContentExcludeValidated','customizeUsageContentAssignPassValidated');
							initHasValuenoSuportLang(lbpmCustomizeContentJson,'isAssignRefuseContentExcludeValidated','customizeUsageContentAssignRefuseValidated');
							initHasValuenoSuportLang(lbpmCustomizeContentJson,'isJumpContentExcludeValidated','customizeUsageContentJumpValidated');
							initHasValuenoSuportLang(lbpmCustomizeContentJson,'isNodeSuspendContentExcludeValidated','customizeUsageContentNodeSuspendValidated');
							initHasValuenoSuportLang(lbpmCustomizeContentJson,'isNodeResumeContentExcludeValidated','customizeUsageContentNodeResumeValidated');
							
						    initHasValuenoSuportLang(lbpmCustomizeContentJson,'isPassContentValidated','customizeUsageContentValidated');
							initHasValuenoSuportLang(lbpmCustomizeContentJson,'isRefuseContentValidated','customizeUsageContentRefuseValidated');
							initHasValuenoSuportLang(lbpmCustomizeContentJson,'isCommissionContentValidated','customizeUsageContentCommissionValidated');
							initHasValuenoSuportLang(lbpmCustomizeContentJson,'isCommunicateContentValidated','customizeUsageContentCommunicateValidated');
							initHasValuenoSuportLang(lbpmCustomizeContentJson,'isAbandonContentValidated','customizeUsageContentAandonValidated');
							initHasValuenoSuportLang(lbpmCustomizeContentJson,'isSuperRefuseContentValidated','customizeUsageContentSuperRefuseValidated');
							initHasValuenoSuportLang(lbpmCustomizeContentJson,'isAdditionSignContentValidated','customizeUsageContentAdditionSignValidated');
							initHasValuenoSuportLang(lbpmCustomizeContentJson,'isAssignContentValidated','customizeUsageContentAssignValidated');
							initHasValuenoSuportLang(lbpmCustomizeContentJson,'isAssignPassContentValidated','customizeUsageContentAssignPassValidated');
							initHasValuenoSuportLang(lbpmCustomizeContentJson,'isAssignRefuseContentValidated','customizeUsageContentAssignRefuseValidated');
							initHasValuenoSuportLang(lbpmCustomizeContentJson,'isJumpContentValidated','customizeUsageContentJumpValidated');
							initHasValuenoSuportLang(lbpmCustomizeContentJson,'isNodeSuspendContentValidated','customizeUsageContentNodeSuspendValidated');
							initHasValuenoSuportLang(lbpmCustomizeContentJson,'isNodeResumeContentValidated','customizeUsageContentNodeResumeValidated');
							
						}
					}else{
						initNoValuenoSuportLang('isPassContentExcludeValidated','${lbpmUsageContent.isPassContentExcludeValidated}','customizeUsageContentValidated',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent)}");
						initNoValuenoSuportLang('isRefuseContentExcludeValidated','${lbpmUsageContent.isRefuseContentExcludeValidated}','customizeUsageContentRefuseValidated',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_refuse)}");
						initNoValuenoSuportLang('isCommissionContentExcludeValidated','${lbpmUsageContent.isCommissionContentExcludeValidated}','customizeUsageContentCommissionValidated',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_commission)}");
						initNoValuenoSuportLang('isCommunicateContentExcludeValidated','${lbpmUsageContent.isCommunicateContentExcludeValidated}','customizeUsageContentCommunicateValidated',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_communicate)}");
						initNoValuenoSuportLang('isAbandonContentExcludeValidated','${lbpmUsageContent.isAbandonContentExcludeValidated}','customizeUsageContentAandonValidated',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_abandon)}");
						initNoValuenoSuportLang('isSuperRefuseContentExcludeValidated','${lbpmUsageContent.isSuperRefuseContentExcludeValidated}','customizeUsageContentSuperRefuseValidated',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_superRefuse)}");
						initNoValuenoSuportLang('isAdditionSignContentExcludeValidated','${lbpmUsageContent.isAdditionSignContentExcludeValidated}','customizeUsageContentAdditionSignValidated',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_additionSign)}");
						initNoValuenoSuportLang('isAssignContentExcludeValidated','${lbpmUsageContent.isAssignContentExcludeValidated}','customizeUsageContentAssignValidated',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assign)}");
						initNoValuenoSuportLang('isAssignPassContentExcludeValidated','${lbpmUsageContent.isAssignPassContentExcludeValidated}','customizeUsageContentAssignPassValidated',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignPass)}");
						initNoValuenoSuportLang('isAssignRefuseContentExcludeValidated','${lbpmUsageContent.isAssignRefuseContentExcludeValidated}','customizeUsageContentAssignRefuseValidated',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignRefuse)}");
						initNoValuenoSuportLang('isJumpContentExcludeValidated','${lbpmUsageContent.isJumpContentExcludeValidated}','customizeUsageContentJumpValidated',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_jump)}");
						initNoValuenoSuportLang('isNodeSuspendContentExcludeValidated','${lbpmUsageContent.isNodeSuspendContentExcludeValidated}','customizeUsageContentNodeSuspendValidated',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeSuspend)}");
						initNoValuenoSuportLang('isNodeResumeContentExcludeValidated','${lbpmUsageContent.isNodeResumeContentExcludeValidated}','customizeUsageContentNodeResumeValidated',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeResume)}");
						
						initNoValuenoSuportLang('isPassContentValidated','${lbpmUsageContent.isPassContentValidated}','customizeUsageContentValidated',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent)}");
						initNoValuenoSuportLang('isRefuseContentValidated','${lbpmUsageContent.isRefuseContentValidated}','customizeUsageContentRefuseValidated',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_refuse)}");
						initNoValuenoSuportLang('isCommissionContentValidated','${lbpmUsageContent.isCommissionContentValidated}','customizeUsageContentCommissionValidated',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_commission)}");
						initNoValuenoSuportLang('isCommunicateContentValidated','${lbpmUsageContent.isCommunicateContentValidated}','customizeUsageContentCommunicateValidated',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_communicate)}");
						initNoValuenoSuportLang('isAbandonContentValidated','${lbpmUsageContent.isAbandonContentValidated}','customizeUsageContentAandonValidated',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_abandon)}");
						initNoValuenoSuportLang('isSuperRefuseContentValidated','${lbpmUsageContent.isSuperRefuseContentValidated}','customizeUsageContentSuperRefuseValidated',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_superRefuse)}");
						initNoValuenoSuportLang('isAdditionSignContentValidated','${lbpmUsageContent.isAdditionSignContentValidated}','customizeUsageContentAdditionSignValidated',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_additionSign)}");
						initNoValuenoSuportLang('isAssignContentValidated','${lbpmUsageContent.isAssignContentValidated}','customizeUsageContentAssignValidated',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assign)}");
						initNoValuenoSuportLang('isAssignPassContentValidated','${lbpmUsageContent.isAssignPassContentValidated}','customizeUsageContentAssignPassValidated',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignPass)}");
						initNoValuenoSuportLang('isAssignRefuseContentValidated','${lbpmUsageContent.isAssignRefuseContentValidated}','customizeUsageContentAssignRefuseValidated',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignRefuse)}");
						initNoValuenoSuportLang('isJumpContentValidated','${lbpmUsageContent.isJumpContentValidated}','customizeUsageContentJumpValidated',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_jump)}");
						initNoValuenoSuportLang('isNodeSuspendContentValidated','${lbpmUsageContent.isNodeSuspendContentValidated}','customizeUsageContentNodeSuspendValidated',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeSuspend)}");
						initNoValuenoSuportLang('isNodeResumeContentValidated','${lbpmUsageContent.isNodeResumeContentValidated}','customizeUsageContentNodeResumeValidated',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeResume)}");
						
						
						
					}
				}
				
			},100);
			 
			 function getCustomizeJsonValue(pJson,langType){
				if(!pJson){
					return "";
				}
				var customizeJson=JSON.parse(pJson);
				for(var z=0;z<customizeJson.length;z++){
					if(customizeJson[z]["lang"]==langType){
						if(customizeJson[z]["value"]=="&nbsp;"){
							return "";
						}else{
							return customizeJson[z]["value"];	
						}
						
					}
				}
				return "";
				
			} 
			
			function initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,fileName,isContentRequired){
				
				var usageContentJson=lbpmCustomizeContentJson[fileName+"_lang"];
				for(var i=0;i<langJson["support"].length;i++){
					var supportValue =langJson["support"][i]["value"];
					if(getCustomizeJsonValue(usageContentJson,supportValue)&&document.getElementsByName(fileName+"_"+supportValue)[0]){
						document.getElementsByName(fileName+"_"+supportValue)[0].value=getCustomizeJsonValue(usageContentJson,supportValue);	
					}else{
						document.getElementsByName(fileName+"_"+supportValue)[0].value="";
					}
				}	
				document.getElementsByName(fileName+"_lang")[0].value=lbpmCustomizeContentJson[fileName+"_lang"];
				var isPassContentRequired=lbpmCustomizeContentJson[isContentRequired];
				$("input[name='"+isContentRequired+"']",context).val(isPassContentRequired);
				LUI(isContentRequired).checkbox.prop('checked',isPassContentRequired=="true");
				if(document.getElementsByName(fileName)[0]){
					document.getElementsByName(fileName)[0].value=lbpmCustomizeContentJson[fileName];
				}
			}
			
			function initNoValueCustomizeLangEnabled(lbpmCustomizeContentJson,filedName,isContentRequiredFiled,isContentRequired,defaultUsageContent4Lang,defaultUsageContent){
				var officialLang=currentLang;
				var usageContentJson=lbpmCustomizeContentJson;
				var defaultUsageContentOfficialLang='';
				for(var i=0;i<langJson["support"].length;i++){
					var supportValue =langJson["support"][i]["value"];
					if(officialLang==supportValue){
						defaultUsageContentOfficialLang=getCustomizeJsonValue(usageContentJson,supportValue);
					}
					if(document.getElementsByName(filedName+"_"+supportValue)[0]){
						document.getElementsByName(filedName+"_"+supportValue)[0].value=getCustomizeJsonValue(usageContentJson,supportValue);
					}
				}	
				var isPassContentRequired=isContentRequired;
				$("input[name='"+isContentRequiredFiled+"']",context).val(isContentRequired);
				LUI(isContentRequiredFiled).checkbox.prop('checked',isPassContentRequired=="true");
				if(document.getElementsByName(filedName+"_lang")[0]){
					document.getElementsByName(filedName+"_lang")[0].value=defaultUsageContent4Lang;
				}
			
				if(document.getElementsByName(filedName)[0]){
					document.getElementsByName(filedName)[0].value=defaultUsageContentOfficialLang;
				}
			}
			
			
			
			
		});
		
		
		function initHasValuenoSuportLang(lbpmCustomizeContentJson,requiredFiled,customizeUsageContentFiled){
			var isPassContentRequired=lbpmCustomizeContentJson[requiredFiled];
			$("input[name='"+requiredFiled+"']",context).val(isPassContentRequired);
			LUI(requiredFiled).checkbox.prop('checked',isPassContentRequired=="true");
			if(document.getElementsByName(customizeUsageContentFiled)[0]){
				document.getElementsByName(customizeUsageContentFiled)[0].value=lbpmCustomizeContentJson[customizeUsageContentFiled];
			}
		}
		
		function initNoValuenoSuportLang(requiredFiledName,requiredValue,customizeUsageContentFiled,customizeUsageContentValue){
			
			var isPassContentRequired=requiredValue;
			$("input[name='"+requiredFiledName+"']",context).val(isPassContentRequired);
			LUI(requiredFiledName).checkbox.prop('checked',isPassContentRequired=="true");
			if(document.getElementsByName(customizeUsageContentFiled)[0]){
				document.getElementsByName(customizeUsageContentFiled)[0].value=customizeUsageContentValue;
			}
		}
		
		//如果是单语言环境下去同步多语言环境下官方语言的值
		function handlerOfficialLangValue(eleName){
			var fdValue = document.getElementsByName(eleName)[0].value;
			
			var eleNameLangJsonStr=$(eleName+"_lang").val();
			if(eleNameLangJsonStr){
				var eleNameLangJson=JSON.parse(eleNameLangJsonStr);
				for(var i=0;i<eleNameLangJson.length;i++){
					if(eleNameLangJson[i]["lang"]==currentLang){
						eleNameLangJson[i]["value"]=fdValue;
					}
				}
				
				$("input[name="+eleName+"_lang]",context).val(JSON.stringify(eleNameLangJsonStr));
			}else{//即使没有多语言，也需要更新多语言环境下的官方语言值 [{"lang":"zh-CN","value":"&nbsp;"},{"lang":"en-US","value":"&nbsp;"}]
				$("input[name="+eleName+"_lang]",context).val('[{"lang":"'+currentLang+'","value":'+'"'+fdValue+'"}]');
			}
		}
		
		
		
		function handleLangByElName(eleName,elJsonName){
			var elLang=[];
			if(!isLangSuportEnabled){
				return elLang;
			}
			var fdValue = document.getElementsByName(eleName)[0].value;
			
			var officialElName=eleName+"_"+langJson["official"]["value"];
			if(document.getElementsByName(officialElName)[0]){
				document.getElementsByName(officialElName)[0].value=fdValue;
			}
			var lang={};
			lang["lang"]=langJson["official"]["value"];
			lang["value"]=fdValue;
			elLang.push(lang);
			for(var i=0;i<langJson["support"].length;i++){
				var elName = eleName+"_"+langJson["support"][i]["value"];
				if(elName==officialElName){
					continue;
				}
				lang={};
				lang["lang"]=langJson["support"][i]["value"];
				lang["value"]=document.getElementsByName(elName)[0].value;
				elLang.push(lang);
			}
			if(document.getElementsByName(elJsonName)[0]){
				document.getElementsByName(elJsonName)[0].value=JSON.stringify(elLang);
			}
		}
		
		
		function setCustomizeUsageContentValidate(){
			handleLangByElName("customizeUsageContentValidated","customizeUsageContentValidated_lang");
			handleLangByElName("customizeUsageContentRefuseValidated","customizeUsageContentRefuseValidated_lang");
			handleLangByElName("customizeUsageContentCommissionValidated","customizeUsageContentCommissionValidated_lang");
			handleLangByElName("customizeUsageContentCommunicateValidated","customizeUsageContentCommunicateValidated_lang");
			handleLangByElName("customizeUsageContentAandonValidated","customizeUsageContentAandonValidated_lang");
			handleLangByElName("customizeUsageContentSuperRefuseValidated","customizeUsageContentSuperRefuseValidated_lang");
			handleLangByElName("customizeUsageContentAdditionSignValidated","customizeUsageContentAdditionSignValidated_lang");
			handleLangByElName("customizeUsageContentAssignValidated","customizeUsageContentAssignValidated_lang");
			handleLangByElName("customizeUsageContentAssignPassValidated","customizeUsageContentAssignPassValidated_lang");
			handleLangByElName("customizeUsageContentAssignRefuseValidated","customizeUsageContentAssignRefuseValidated_lang");
			handleLangByElName("customizeUsageContentJumpValidated","customizeUsageContentJumpValidated_lang");
			handleLangByElName("customizeUsageContentNodeSuspendValidated","customizeUsageContentNodeSuspendValidated_lang");
			handleLangByElName("customizeUsageContentNodeResumeValidated","customizeUsageContentNodeResumeValidated_lang");
			
		
			if(!isLangSuportEnabled){
				handlerOfficialLangValue("customizeUsageContentValidated");
				handlerOfficialLangValue("customizeUsageContentRefuseValidated");
				handlerOfficialLangValue("customizeUsageContentCommissionValidated");
				handlerOfficialLangValue("customizeUsageContentCommunicateValidated");
				handlerOfficialLangValue("customizeUsageContentAandonValidated");
				handlerOfficialLangValue("customizeUsageContentSuperRefuseValidated");
				handlerOfficialLangValue("customizeUsageContentAdditionSignValidated");
				handlerOfficialLangValue("customizeUsageContentAssignValidated");
				handlerOfficialLangValue("customizeUsageContentAssignPassValidated");
				handlerOfficialLangValue("customizeUsageContentAssignRefuseValidated");
				handlerOfficialLangValue("customizeUsageContentJumpValidated");
				handlerOfficialLangValue("customizeUsageContentNodeSuspendValidated");
				
			}
			
			
			var jsonCustomizeValidate={};
			jsonCustomizeValidate.customizeUsageContentValidated=$("textarea[name='customizeUsageContentValidated']").val();
			jsonCustomizeValidate.customizeUsageContentValidated_lang=$("input[name='customizeUsageContentValidated_lang']").val();
			jsonCustomizeValidate.isPassContentExcludeValidated=$("input[name='isPassContentExcludeValidated']").val();
			jsonCustomizeValidate.isPassContentValidated=$("input[name='isPassContentValidated']").val();

			
			jsonCustomizeValidate.customizeUsageContentRefuseValidated=$("textarea[name='customizeUsageContentRefuseValidated']").val();
			jsonCustomizeValidate.customizeUsageContentRefuseValidated_lang=$("input[name='customizeUsageContentRefuseValidated_lang']").val();
			jsonCustomizeValidate.isRefuseContentExcludeValidated=$("input[name='isRefuseContentExcludeValidated']").val();
			jsonCustomizeValidate.isRefuseContentValidated=$("input[name='isRefuseContentValidated']").val();

			
			jsonCustomizeValidate.customizeUsageContentCommissionValidated=$("textarea[name='customizeUsageContentCommissionValidated']").val();
			jsonCustomizeValidate.customizeUsageContentCommissionValidated_lang=$("input[name='customizeUsageContentCommissionValidated_lang']").val();
			jsonCustomizeValidate.isCommissionContentExcludeValidated=$("input[name='isCommissionContentExcludeValidated']").val();
			jsonCustomizeValidate.isCommissionContentValidated=$("input[name='isCommissionContentValidated']").val();

			
			jsonCustomizeValidate.customizeUsageContentValidated=$("textarea[name='customizeUsageContentValidated']").val();
			jsonCustomizeValidate.customizeUsageContentValidated_lang=$("input[name='customizeUsageContentValidated_lang']").val();
			jsonCustomizeValidate.isPassContentExcludeValidated=$("input[name='isPassContentExcludeValidated']").val();
			jsonCustomizeValidate.isPassContentValidated=$("input[name='isPassContentValidated']").val();

			
			jsonCustomizeValidate.customizeUsageContentCommunicateValidated=$("textarea[name='customizeUsageContentCommunicateValidated']").val();
			jsonCustomizeValidate.customizeUsageContentCommunicateValidated_lang=$("input[name='customizeUsageContentCommunicateValidated_lang']").val();
			jsonCustomizeValidate.isCommunicateContentExcludeValidated=$("input[name='isCommunicateContentExcludeValidated']").val();
			jsonCustomizeValidate.isCommunicateContentValidated=$("input[name='isCommunicateContentValidated']").val();

			
			jsonCustomizeValidate.customizeUsageContentAandonValidated=$("textarea[name='customizeUsageContentAandonValidated']").val();
			jsonCustomizeValidate.customizeUsageContentAandonValidated_lang=$("input[name='customizeUsageContentAandonValidated_lang']").val();
			jsonCustomizeValidate.isAbandonContentExcludeValidated=$("input[name='isAbandonContentExcludeValidated']").val();
			jsonCustomizeValidate.isAbandonContentValidated=$("input[name='isAbandonContentValidated']").val();

			
			jsonCustomizeValidate.customizeUsageContentValidated=$("textarea[name='customizeUsageContentValidated']").val();
			jsonCustomizeValidate.customizeUsageContentValidated_lang=$("input[name='customizeUsageContentValidated_lang']").val();
			jsonCustomizeValidate.isPassContentExcludeValidated=$("input[name='isPassContentExcludeValidated']").val();
			jsonCustomizeValidate.isPassContentValidated=$("input[name='isPassContentValidated']").val();

			
			jsonCustomizeValidate.customizeUsageContentSuperRefuseValidated=$("textarea[name='customizeUsageContentSuperRefuseValidated']").val();
			jsonCustomizeValidate.customizeUsageContentSuperRefuseValidated_lang=$("input[name='customizeUsageContentSuperRefuseValidated_lang']").val();
			jsonCustomizeValidate.isSuperRefuseContentExcludeValidated=$("input[name='isSuperRefuseContentExcludeValidated']").val();
			jsonCustomizeValidate.isSuperRefuseContentValidated=$("input[name='isSuperRefuseContentValidated']").val();

			
			jsonCustomizeValidate.customizeUsageContentAdditionSignValidated=$("textarea[name='customizeUsageContentAdditionSignValidated']").val();
			jsonCustomizeValidate.customizeUsageContentAdditionSignValidated_lang=$("input[name='customizeUsageContentAdditionSignValidated_lang']").val();
			jsonCustomizeValidate.isAdditionSignContentExcludeValidated=$("input[name='isAdditionSignContentExcludeValidated']").val();
			jsonCustomizeValidate.isAdditionSignContentValidated=$("input[name='isAdditionSignContentValidated']").val();

			
			jsonCustomizeValidate.customizeUsageContentValidated=$("textarea[name='customizeUsageContentValidated']").val();
			jsonCustomizeValidate.customizeUsageContentValidated_lang=$("input[name='customizeUsageContentValidated_lang']").val();
			jsonCustomizeValidate.isPassContentExcludeValidated=$("input[name='isPassContentExcludeValidated']").val();
			jsonCustomizeValidate.isPassContentValidated=$("input[name='isPassContentValidated']").val();

			
			jsonCustomizeValidate.customizeUsageContentAssignValidated=$("textarea[name='customizeUsageContentAssignValidated']").val();
			jsonCustomizeValidate.customizeUsageContentAssignValidated_lang=$("input[name='customizeUsageContentAssignValidated_lang']").val();
			jsonCustomizeValidate.isAssignContentExcludeValidated=$("input[name='isAssignContentExcludeValidated']").val();
			jsonCustomizeValidate.isAssignContentValidated=$("input[name='isAssignContentValidated']").val();

			
			jsonCustomizeValidate.customizeUsageContentAssignPassValidated=$("textarea[name='customizeUsageContentAssignPassValidated']").val();
			jsonCustomizeValidate.customizeUsageContentAssignPassValidated_lang=$("input[name='customizeUsageContentAssignPassValidated_lang']").val();
			jsonCustomizeValidate.isAssignPassContentExcludeValidated=$("input[name='isAssignPassContentExcludeValidated']").val();
			jsonCustomizeValidate.isAssignPassContentValidated=$("input[name='isAssignPassContentValidated']").val();

			
			jsonCustomizeValidate.customizeUsageContentAssignRefuseValidated=$("textarea[name='customizeUsageContentAssignRefuseValidated']").val();
			jsonCustomizeValidate.customizeUsageContentAssignRefuseValidated_lang=$("input[name='customizeUsageContentAssignRefuseValidated_lang']").val();
			jsonCustomizeValidate.isAssignRefuseContentExcludeValidated=$("input[name='isAssignRefuseContentExcludeValidated']").val();
			jsonCustomizeValidate.isAssignRefuseContentValidated=$("input[name='isAssignRefuseContentValidated']").val();

			
			jsonCustomizeValidate.customizeUsageContentJumpValidated=$("textarea[name='customizeUsageContentJumpValidated']").val();
			jsonCustomizeValidate.customizeUsageContentJumpValidated_lang=$("input[name='customizeUsageContentJumpValidated_lang']").val();
			jsonCustomizeValidate.isJumpContentExcludeValidated=$("input[name='isJumpContentExcludeValidated']").val();
			jsonCustomizeValidate.isJumpContentValidated=$("input[name='isJumpContentValidated']").val();
			
			jsonCustomizeValidate.customizeUsageContentNodeSuspendValidated=$("textarea[name='customizeUsageContentNodeSuspendValidated']").val();
			jsonCustomizeValidate.customizeUsageContentNodeSuspendValidated_lang=$("input[name='customizeUsageContentNodeSuspendValidated_lang']").val();
			jsonCustomizeValidate.isNodeSuspendContentExcludeValidated=$("input[name='isNodeSuspendContentExcludeValidated']").val();
			jsonCustomizeValidate.isNodeSuspendContentValidated=$("input[name='isNodeSuspendContentValidated']").val();

			
			jsonCustomizeValidate.customizeUsageContentNodeResumeValidated=$("textarea[name='customizeUsageContentNodeResumeValidated']").val();
			jsonCustomizeValidate.customizeUsageContentNodeResumeValidated_lang=$("input[name='customizeUsageContentNodeResumeValidated_lang']").val();
			jsonCustomizeValidate.isNodeResumeContentExcludeValidated=$("input[name='isNodeResumeContentExcludeValidated']").val();
			jsonCustomizeValidate.isNodeResumeContentValidated=$("input[name='isNodeResumeContentValidated']").val();
			
			var jsonCustomizeValidateText=JSON.stringify(jsonCustomizeValidate);
			
			rtnData={'jsonCustomizeValidateText':jsonCustomizeValidateText};
			
			dialogUi.hide(rtnData);
}
		function cancel(){
			var jsonCustomizeValidateText = $("input[id='lbpmCustomizeValidateJson']",context.document).val();
			rtnData={'jsonCustomizeValidateText':jsonCustomizeValidateText};
			dialogUi.hide(rtnData);
		}

	 	</script>
	</template:replace>
</template:include>
