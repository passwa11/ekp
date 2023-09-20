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
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isRequired"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isSetValidated"/>
							</td>
						</tr>
						<tr>
							<td width=80%>
									<c:if test="${!isLangSuportEnabled }">
											<xform:textarea property="customizeUsageContent" style="width:100%;height:80px" validators="maxLength(4000)" showStatus="edit"/>
									</c:if>
									
									<c:if test="${isLangSuportEnabled }">
										<xlang:lbpmlangAreaNew validators="maxLength(4000)" property="customizeUsageContent" alias="customizeUsageContent" style="width:100%;height:80px" />
										
									</c:if>
									<html:hidden property="customizeUsageContent_lang" />
							</td>
							<td align="center">
								<ui:switch property="isPassContentRequired" id="isPassContentRequired" onValueChange="closeValidate('isPassContentValidated','isPassContentExcludeValidated','isPassContentRequired')"></ui:switch>
							</td>
							<td align="center">
								<a style="cursor:pointer;" href="#" id="update_set" class="btn_b" title="修改设置" onclick="lbpmCustomizeValidateFunc(this.window);"><span><em><bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isSetting"/></em></span></a>
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
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isRequired"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isSetValidated"/>
							</td>
						</tr>
						<tr>
							<td width=80%>
							
							<c:if test="${!isLangSuportEnabled }">
								<xform:textarea property="customizeUsageContentRefuse" style="width:100%;height:80px" validators="maxLength(4000)"  showStatus="edit"/>
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentRefuse" alias="customizeUsageContentRefuse" validators="maxLength(4000)" style="width:100%;height:80px"/>
							</c:if>
							<html:hidden property="customizeUsageContentRefuse_lang" />
							</td>
							<td align="center">
								
								<ui:switch property="isRefuseContentRequired" id="isRefuseContentRequired" onValueChange="closeValidate('isRefuseContentValidated','isRefuseContentExcludeValidated','isRefuseContentRequired')"></ui:switch>
								
							</td>
							<td align="center">
								<a style="cursor:pointer;" href="#" id="update_set" class="btn_b" title="修改设置" onclick="lbpmCustomizeValidateFunc(this.window);"><span><em><bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isSetting"/></em></span></a>
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
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isRequired"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isSetValidated"/>
							</td>
						</tr>
						<tr>
							<td width=80%>
							
							<c:if test="${!isLangSuportEnabled }">
								
										<xform:textarea property="customizeUsageContentCommission" style="width:100%;height:80px" validators="maxLength(4000)"  showStatus="edit" />
									
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentCommission" alias="customizeUsageContentCommission" validators="maxLength(4000)" style="width:100%;height:80px" />
							</c:if>
							<html:hidden property="customizeUsageContentCommission_lang" />

							</td>
							<td align="center">
								<ui:switch property="isCommissionContentRequired" id="isCommissionContentRequired" onValueChange="closeValidate('isCommissionContentValidated','isCommissionContentExcludeValidated',,'isCommissionContentRequired')"></ui:switch>
							</td>
							<td align="center">
								<a style="cursor:pointer;" href="#" id="update_set" class="btn_b" title="修改设置" onclick="lbpmCustomizeValidateFunc(this.window);"><span><em><bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isSetting"/></em></span></a>
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
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isRequired"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isSetValidated"/>
							</td>
						</tr>
						<tr>
							<td width=80%>
							
							<c:if test="${!isLangSuportEnabled }">
								
										<xform:textarea property="customizeUsageContentCommunicate" style="width:100%;height:80px" validators="maxLength(4000)" showStatus="edit"/>
									
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentCommunicate" alias="customizeUsageContentCommunicate" validators="maxLength(4000)" style="width:100%;height:80px" />
							</c:if>
							<html:hidden property="customizeUsageContentCommunicate_lang" />
							</td>
							<td align="center">
								
								<ui:switch property="isCommunicateContentRequired" id="isCommunicateContentRequired" onValueChange="closeValidate('isCommunicateContentValidated','isCommunicateContentExcludeValidated','isCommunicateContentRequired')"></ui:switch>
								
							</td>
							<td align="center">
								<a style="cursor:pointer;" href="#" id="update_set" class="btn_b" title="修改设置" onclick="lbpmCustomizeValidateFunc(this.window);"><span><em><bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isSetting"/></em></span></a>
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
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isRequired"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isSetValidated"/>
							</td>
						</tr>
						<tr>
							<td width=80%>
							
							<c:if test="${!isLangSuportEnabled }">
								 
										<xform:textarea property="customizeUsageContentAandon" style="width:100%;height:80px" validators="maxLength(4000)" showStatus="edit"/>
									
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentAandon" alias="customizeUsageContentAandon" validators="maxLength(4000)" style="width:100%;height:80px" />
							</c:if>
							<html:hidden property="customizeUsageContentAandon_lang" />
							</td>
							<td align="center">
								<ui:switch property="isAbandonContentRequired" id="isAbandonContentRequired" onValueChange="closeValidate('isAbandonContentValidated','isAbandonContentExcludeValidated','isAbandonContentRequired')"></ui:switch>
							</td>
							<td align="center">
								<a style="cursor:pointer;" href="#" id="update_set" class="btn_b" title="修改设置" onclick="lbpmCustomizeValidateFunc(this.window);"><span><em><bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isSetting"/></em></span></a>
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
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isRequired"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isSetValidated"/>
							</td>
						</tr>
						<tr>
							<td width=80%>
							
							<c:if test="${!isLangSuportEnabled }">
								 
										<xform:textarea property="customizeUsageContentSuperRefuse" style="width:100%;height:80px" validators="maxLength(4000)" showStatus="edit"/>
									
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentSuperRefuse" alias="customizeUsageContentSuperRefuse" validators="maxLength(4000)" style="width:100%;height:80px"/>
							</c:if>
							<html:hidden property="customizeUsageContentSuperRefuse_lang" />
							</td>
							<td align="center">
								
								<ui:switch property="isSuperRefuseContentRequired" id="isSuperRefuseContentRequired" onValueChange="closeValidate('isSuperRefuseContentValidated','isSuperRefuseContentExcludeValidated','isSuperRefuseContentRequired')"></ui:switch>								
								
							</td>
							<td align="center">
								<a style="cursor:pointer;" href="#" id="update_set" class="btn_b" title="修改设置" onclick="lbpmCustomizeValidateFunc(this.window);"><span><em><bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isSetting"/></em></span></a>
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
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isRequired"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isSetValidated"/>
							</td>
						</tr>
						<tr>
							<td width=80%>
							
							<c:if test="${!isLangSuportEnabled }">
								
										<xform:textarea property="customizeUsageContentAdditionSign" style="width:100%;height:80px" validators="maxLength(4000)" showStatus="edit" />
									
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentAdditionSign" alias="customizeUsageContentAdditionSign" validators="maxLength(4000)" style="width:100%;height:80px" />
							</c:if>
							<html:hidden property="customizeUsageContentAdditionSign_lang" />
							</td>
							<td align="center">
								<ui:switch property="isAdditionSignContentRequired" id="isAdditionSignContentRequired" onValueChange="closeValidate('isAdditionSignContentValidated','isAdditionSignContentExcludeValidated','isAdditionSignContentRequired')"></ui:switch>
							</td>
							<td align="center">
								<a style="cursor:pointer;" href="#" id="update_set" class="btn_b" title="修改设置" onclick="lbpmCustomizeValidateFunc(this.window);"><span><em><bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isSetting"/></em></span></a>
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
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isRequired"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isSetValidated"/>
							</td>
						</tr>
						<tr>
							<td width=80%>
							
							<c:if test="${!isLangSuportEnabled }">
										<xform:textarea property="customizeUsageContentAssign" style="width:100%;height:80px" validators="maxLength(4000)" showStatus="edit" />
								
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentAssign" alias="customizeUsageContentAssign" validators="maxLength(4000)" style="width:100%;height:80px" />
							</c:if>
							<html:hidden property="customizeUsageContentAssign_lang" />
							</td>
							<td align="center">
								<ui:switch property="isAssignContentRequired" id="isAssignContentRequired" onValueChange="closeValidate('isAssignContentValidated','isAssignContentExcludeValidated','isAssignContentRequired')"></ui:switch>
							</td>
							<td align="center">
								<a style="cursor:pointer;" href="#" id="update_set" class="btn_b" title="修改设置" onclick="lbpmCustomizeValidateFunc(this.window);"><span><em><bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isSetting"/></em></span></a>
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
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isRequired"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isSetValidated"/>
							</td>
						</tr>
						<tr>
							<td width=80%>
							
							<c:if test="${!isLangSuportEnabled }">
										<xform:textarea property="customizeUsageContentAssignPass" style="width:100%;height:80px" validators="maxLength(4000)" showStatus="edit" />
								
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentAssignPass" alias="customizeUsageContentAssignPass" validators="maxLength(4000)" style="width:100%;height:80px" />
							</c:if>
							<html:hidden property="customizeUsageContentAssignPass_lang" />
							</td>
							<td align="center">
								<ui:switch property="isAssignPassContentRequired" id="isAssignPassContentRequired" onValueChange="closeValidate('isAssignPassContentValidated','isAssignPassContentExcludeValidated','isAssignPassContentRequired')"></ui:switch>
							</td>
							<td align="center">
								<a style="cursor:pointer;" href="#" id="update_set" class="btn_b" title="修改设置" onclick="lbpmCustomizeValidateFunc(this.window);"><span><em><bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isSetting"/></em></span></a>
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
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isRequired"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isSetValidated"/>
							</td>
						</tr>
						<tr>
							<td width=80%>
							
							<c:if test="${!isLangSuportEnabled }">
										<xform:textarea property="customizeUsageContentAssignRefuse" style="width:100%;height:80px" validators="maxLength(4000)" showStatus="edit"/>
								
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentAssignRefuse" alias="customizeUsageContentAssignRefuse" validators="maxLength(4000)" style="width:100%;height:80px" />
							</c:if>
							<html:hidden property="customizeUsageContentAssignRefuse_lang" />
							</td>
							<td align="center">
								<ui:switch property="isAssignRefuseContentRequired" id="isAssignRefuseContentRequired" onValueChange="closeValidate('isAssignRefuseContentValidated','isAssignRefuseContentExcludeValidated','isAssignRefuseContentRequired')"></ui:switch>
							</td>
							<td align="center">
								<a style="cursor:pointer;" href="#" id="update_set" class="btn_b" title="修改设置" onclick="lbpmCustomizeValidateFunc(this.window);"><span><em><bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isSetting"/></em></span></a>
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
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isRequired"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isSetValidated"/>
							</td>
						</tr>
						<tr>
							<td width=80%>
							
							<c:if test="${!isLangSuportEnabled }">
										<xform:textarea property="customizeUsageContentJump" style="width:100%;height:80px" validators="maxLength(4000)" showStatus="edit"/>
								
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentJump" alias="customizeUsageContentJump" validators="maxLength(4000)" style="width:100%;height:80px" />
							</c:if>
							<html:hidden property="customizeUsageContentJump_lang" />
							</td>
							<td align="center">
								<ui:switch property="isJumpContentRequired" id="isJumpContentRequired" onValueChange="closeValidate('isJumpContentValidated','isJumpContentExcludeValidated','isJumpContentRequired')"></ui:switch>
							</td>
							<td align="center">
								<a style="cursor:pointer;" href="#" id="update_set" class="btn_b" title="修改设置" onclick="lbpmCustomizeValidateFunc(this.window);"><span><em><bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isSetting"/></em></span></a>
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
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isRequired"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isSetValidated"/>
							</td>
						</tr>
						<tr>
							<td width=80%>
							
							<c:if test="${!isLangSuportEnabled }">
										<xform:textarea property="customizeUsageContentNodeSuspend" style="width:100%;height:80px" validators="maxLength(4000)" showStatus="edit" />
							
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentNodeSuspend" alias="customizeUsageContentNodeSuspend" validators="maxLength(4000)" style="width:100%;height:80px"/>
							</c:if>
							<html:hidden property="customizeUsageContentNodeSuspend_lang" />
							</td>
							<td align="center">
								<ui:switch property="isNodeSuspendContentRequired"  id="isNodeSuspendContentRequired" onValueChange="closeValidate('isNodeSuspendContentValidated','isNodeSuspendContentExcludeValidated','isNodeSuspendContentRequired')"></ui:switch>
							</td>
							<td align="center">
								<a style="cursor:pointer;" href="#" id="update_set" class="btn_b" title="修改设置" onclick="lbpmCustomizeValidateFunc(this.window);"><span><em><bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isSetting"/></em></span></a>
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
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isRequired"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isSetValidated"/>
							</td>
						</tr>
						<tr>
							<td width=80%>
							
							<c:if test="${!isLangSuportEnabled }">
										<xform:textarea property="customizeUsageContentNodeResume" style="width:100%;height:80px" validators="maxLength(4000)" showStatus="edit"/>
								
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentNodeResume" alias="customizeUsageContentNodeResume" validators="maxLength(4000)" style="width:100%;height:80px" />
							</c:if>
							<html:hidden property="customizeUsageContentNodeResume_lang" />
							</td>
							<td align="center">
								<ui:switch property="isNodeResumeContentRequired" id="isNodeResumeContentRequired" onValueChange="closeValidate('isNodeResumeContentValidated','isNodeResumeContentExcludeValidated','isNodeResumeContentRequired')"></ui:switch>
							</td>
							<td align="center">
								<a style="cursor:pointer;" href="#" id="update_set" class="btn_b" title="修改设置" onclick="lbpmCustomizeValidateFunc(this.window);"><span><em><bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isSetting"/></em></span></a>
							</td>
						</tr>
					</table>
				
				</td>
			</tr>
			
			
			<!-- 默认节点处理人身份重复跳过意见 -->
			<tr>
				<td class="td_normal_title" width=15%>
					默认节点处理人身份重复跳过意见
				</td>
				<td width=85%>
					<table class="tb_normal" width=100%>
						<tr>
							<td width=20% class="td_normal_title">使用下方设置的意见</td>
							<td width=10%><ui:switch property="customizeRepeatJumpValue" id="customizeRepeatJumpValue" onValueChange="switchDefaultRepeatJump('customizeRepeatJumpValue','customizeBeforeRepeatJumpValue','customizeSystemRepeatJumpValue')"></ui:switch></td>
							<td width=30% class="td_normal_title">使用该处理人在之前节点的审核意见</td>
							<td width=10%><ui:switch property="customizeBeforeRepeatJumpValue" id="customizeBeforeRepeatJumpValue" onValueChange="switchDefaultRepeatJump('customizeBeforeRepeatJumpValue','customizeRepeatJumpValue','customizeSystemRepeatJumpValue')"></ui:switch></td>
							<td width=20% class="td_normal_title">使用系统自动生成的意见</td>
							<td width=10%><ui:switch property="customizeSystemRepeatJumpValue" id="customizeSystemRepeatJumpValue" onValueChange="switchDefaultRepeatJump('customizeSystemRepeatJumpValue','customizeBeforeRepeatJumpValue','customizeRepeatJumpValue')"></ui:switch></td>
						</tr>
						
						<tr>
							<td width=100% colspan="6">
								<c:if test="${!isLangSuportEnabled }">
									 <textarea name="customizeUsageContent_repeatJump" validate="maxLength(4000)" style="width:100%;height:80px" showStatus="edit"></textarea>
								</c:if>
								<c:if test="${isLangSuportEnabled }">
									<xlang:lbpmlangAreaNew property="customizeUsageContent_repeatJump" alias="customizeUsageContent_repeatJump" validators="maxLength(4000)" style="width:100%;height:80px" />
								</c:if>
								<html:hidden property="customizeUsageContent_repeatJump_lang" />
							</td>
						</tr>
						
					</table>
				</td>
			</tr>
			
			<!-- 默认节点超时跳过意见 -->
			<tr>
				<td class="td_normal_title" width=15%>
					默认节点超时跳过意见
				</td>
				<td width=85%>
					<table class="tb_normal" width=100%>
					     <tr>
							<td width=30% class="td_normal_title">使用下方设置的意见</td>
							<td width=20%><ui:switch property="customizeTimeOutValue" id="customizeTimeOutValue" onValueChange="switchTimeOut('customizeTimeOutValue','customizeSystemTimeOutValue')"></ui:switch></td>
							<td width=30% class="td_normal_title">使用系统自动生成的意见</td>
							<td width=20%><ui:switch property="customizeSystemTimeOutValue" id="customizeSystemTimeOutValue" onValueChange="switchTimeOut('customizeSystemTimeOutValue','customizeTimeOutValue')"></ui:switch></td>
						</tr>
						
						<tr>
							<td width=100% colspan="4">
								<c:if test="${!isLangSuportEnabled }">
									 <textarea name="customizeUsageContent_timeOut" validate="maxLength(4000)" style="width:100%;height:80px" showStatus="edit"></textarea>
								</c:if>
								<c:if test="${isLangSuportEnabled }">
									<xlang:lbpmlangAreaNew property="customizeUsageContent_timeOut" alias="customizeUsageContent_timeOut" validators="maxLength(4000)" style="width:100%;height:80px" />
								</c:if>
								<html:hidden property="customizeUsageContent_timeOut_lang" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
			
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="lbpmUsage.fdDescription"/>
				</td><td width="85%">
					<bean:message  bundle="sys-lbpmservice-support" key="lbpmUsageContent.fdDescription.details.1"/><br>
					<bean:message  bundle="sys-lbpmservice-support" key="lbpmUsageContent.fdDescription.details.2"/><br>
				</td>
			</tr>
			<tr>
				<td  colspan="2" align="center" >
					<input name="ext_lbpmCustomizeValidateContentJson" id="lbpmCustomizeValidateJson" type="hidden">
					<!-- 确定 -->
					<input class="calcBtn resultBtn" type=button value="<bean:message key="button.ok"/>" onclick="setCustomizeUsageContent();">
					<input class="calcBtn resultBtn" type="button" value="<bean:message key="button.cancel"/>" onClick="window.close();">
				</td>
			</tr>
		</table>
		</div>
		</center>
			<center >
				
				
			</center>
		</html:form>
		
		<%
			LbpmUsageContent lbpmUsageContent = new LbpmUsageContent();
			pageContext.setAttribute("lbpmUsageContent", lbpmUsageContent);
		%>
		<script type="text/javascript">
		
		if(window.showModalDialog){
			dialogObject = window.dialogArguments;
		}else{
			dialogObject = opener.Com_Parameter.Dialog;
		}
		//dialog = new KMSSDialog();
		<%
		Locale locale =Locale.getDefault();
		%>
		var currentLang ='<%=MultiLangTextareaGroupTag.getUserLangKey()%>';
		
		var langJson = <%=MultiLangTextareaGroupTag.getLangsJsonStr()%>;
		var isLangSuportEnabled = <%=MultiLangTextareaGroupTag.isLangSuportEnabled()%>;
		
		// 开启校验开关时必须保证必填开关已经开启
			function closeValidate(op1,op2,op3){
			if(!(LUI(op3).checkbox.prop('checked'))){
					var dataInfoValidate = $("input[id='lbpmCustomizeValidateJson']").val(); 
					 
				 	if(dataInfoValidate){
						var dataInfoValidateJson= JSON.parse(dataInfoValidate);
						dataInfoValidateJson[op1] = "false";
						dataInfoValidateJson[op2] = "false";
						var lbpmCustomizeValidateStr = JSON.stringify(dataInfoValidateJson);
						console.log("001"+lbpmCustomizeValidateStr);
						$("#lbpmCustomizeValidateJson").val(lbpmCustomizeValidateStr);
				}
			}
			
		}
		
		function switchDefaultRepeatJump(op,op1,op2){
			if(LUI(op).checkbox.prop('checked')){
				LUI(op1).checkbox.prop('checked',false);
				$("input[name='"+op1+"']").val("false");
				LUI(op2).checkbox.prop('checked',false);
				$("input[name='"+op2+"']").val("false");
			}
		}

		function switchTimeOut(op,op1){
			if(LUI(op).checkbox.prop('checked')){
				LUI(op1).checkbox.prop('checked',false);
				$("input[name='"+op1+"']").val("false");
			}
		}
		
		
		
		LUI.ready(function(){
			var dataInfoValidate = dialogObject.lbpmCustomizeParameter.dataInfoValidate;
			$("#lbpmCustomizeValidateJson").val(dataInfoValidate);
			function getCustomizeJsonValue(pJson,langType){
				if(!pJson){
					return "";
				}
				var customizeJson=JSON.parse(pJson);
				for(var z=0;z<customizeJson.length;z++){
					console.log("hah2:"+customizeJson[z]["lang"]);
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
					if(getCustomizeJsonValue(usageContentJson,supportValue)){
						document.getElementsByName(fileName+"_"+supportValue)[0].value=getCustomizeJsonValue(usageContentJson,supportValue);	
					}else{
						document.getElementsByName(fileName+"_"+supportValue)[0].value="";
					}
				}	
				document.getElementsByName(fileName+"_lang")[0].value=lbpmCustomizeContentJson[fileName+"_lang"];
				var isPassContentRequired=lbpmCustomizeContentJson[isContentRequired];
				$("input[name='"+isContentRequired+"']").val(isPassContentRequired);
				LUI(isContentRequired).checkbox.prop('checked',isPassContentRequired=="true");
				document.getElementsByName(fileName)[0].value=lbpmCustomizeContentJson[fileName];
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
					document.getElementsByName(filedName+"_"+supportValue)[0].value=getCustomizeJsonValue(usageContentJson,supportValue);
				}	
				var isPassContentRequired=isContentRequired;
				$("input[name='"+isContentRequiredFiled+"']").val(isContentRequired);
				LUI(isContentRequiredFiled).checkbox.prop('checked',isPassContentRequired=="true");
				document.getElementsByName(filedName+"_lang")[0].value=defaultUsageContent4Lang;
				document.getElementsByName(filedName)[0].value=defaultUsageContentOfficialLang;
			}
			
			
			//支持多语言
			if(isLangSuportEnabled){
				//扩展属性值
				var lbpmCustomizeContentJsonStr=dialogObject.lbpmCustomizeParameter.dataInfo;
				if(lbpmCustomizeContentJsonStr){
					var lbpmCustomizeContentJson=JSON.parse(lbpmCustomizeContentJsonStr);
					//有扩展属性值
					if(lbpmCustomizeContentJson){
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContent','isPassContentRequired');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentRefuse','isRefuseContentRequired');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentCommission','isCommissionContentRequired');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentCommunicate','isCommunicateContentRequired');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentAandon','isAbandonContentRequired');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentSuperRefuse','isSuperRefuseContentRequired');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentAdditionSign','isAdditionSignContentRequired');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentAssign','isAssignContentRequired');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentAssignPass','isAssignPassContentRequired');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentAssignRefuse','isAssignRefuseContentRequired');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentJump','isJumpContentRequired');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentNodeSuspend','isNodeSuspendContentRequired');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentNodeResume','isNodeResumeContentRequired');
						
					 	/* initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContent','isPassContentValidated');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentRefuse','isRefuseContentValidated');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentCommission','isCommissionContentValidated');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentCommunicate','isCommunicateContentValidated');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentAandon','isAbandonContentValidated');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentSuperRefuse','isSuperRefuseContentValidated');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentAdditionSign','isAdditionSignContentValidated');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentAssign','isAssignContentValidated');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentAssignPass','isAssignPassContentValidated');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentAssignRefuse','isAssignRefuseContentValidated');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentJump','isJumpContentValidated');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentNodeSuspend','isNodeSuspendContentValidated');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentNodeResume','isNodeResumeContentValidated');
						  */
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContent_repeatJump','customizeRepeatJumpValue');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContent_repeatJump','customizeBeforeRepeatJumpValue');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContent_repeatJump','customizeSystemRepeatJumpValue');
						
						
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContent_timeOut','customizeTimeOutValue');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContent_timeOut','customizeSystemTimeOutValue');
						
					}
				}else{
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent4Lang)}","customizeUsageContent","isPassContentRequired","${lbpmUsageContent.isPassContentRequired}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_refuse4Lang)}","customizeUsageContentRefuse","isRefuseContentRequired","${lbpmUsageContent.isRefuseContentRequired}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_refuse4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_refuse)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_commission4Lang)}","customizeUsageContentCommission","isCommissionContentRequired","${lbpmUsageContent.isCommissionContentRequired}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_commission4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_commission)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_communicate4Lang)}","customizeUsageContentCommunicate","isCommunicateContentRequired","${lbpmUsageContent.isCommunicateContentRequired}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_communicate4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_communicate)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_abandon4Lang)}","customizeUsageContentAandon","isAbandonContentRequired","${lbpmUsageContent.isAbandonContentRequired}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_abandon4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_abandon)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_superRefuse4Lang)}","customizeUsageContentSuperRefuse","isSuperRefuseContentRequired","${lbpmUsageContent.isSuperRefuseContentRequired}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_superRefuse4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_superRefuse)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_additionSign4Lang)}","customizeUsageContentAdditionSign","isAdditionSignContentRequired","${lbpmUsageContent.isAdditionSignContentRequired}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_additionSign4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_additionSign)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assign4Lang)}","customizeUsageContentAssign","isAssignContentRequired","${lbpmUsageContent.isAssignContentRequired}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assign4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assign)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignPass4Lang)}","customizeUsageContentAssignPass","isAssignPassContentRequired","${lbpmUsageContent.isAssignPassContentRequired}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignPass4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignPass)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignRefuse4Lang)}","customizeUsageContentAssignRefuse","isAssignRefuseContentRequired","${lbpmUsageContent.isAssignRefuseContentRequired}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignRefuse4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignRefuse)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_jump4Lang)}","customizeUsageContentJump","isJumpContentRequired","${lbpmUsageContent.isJumpContentRequired}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_jump4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_jump)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeSuspend4Lang)}","customizeUsageContentNodeSuspend","isNodeSuspendContentRequired","${lbpmUsageContent.isNodeSuspendContentRequired}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeSuspend4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeSuspend)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeResume4Lang)}","customizeUsageContentNodeResume","isNodeResumeContentRequired","${lbpmUsageContent.isNodeResumeContentRequired}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeResume4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeResume)}");
					
					/* initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent4Lang)}","customizeUsageContent","isPassContentValidated","${lbpmUsageContent.isPassContentValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_refuse4Lang)}","customizeUsageContentRefuse","isRefuseContentValidated","${lbpmUsageContent.isRefuseContentValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_refuse4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_refuse)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_commission4Lang)}","customizeUsageContentCommission","isCommissionContentValidated","${lbpmUsageContent.isCommissionContentValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_commission4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_commission)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_communicate4Lang)}","customizeUsageContentCommunicate","isCommunicateContentValidated","${lbpmUsageContent.isCommunicateContentValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_communicate4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_communicate)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_abandon4Lang)}","customizeUsageContentAandon","isAbandonContentValidated","${lbpmUsageContent.isAbandonContentValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_abandon4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_abandon)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_superRefuse4Lang)}","customizeUsageContentSuperRefuse","isSuperRefuseContentValidated","${lbpmUsageContent.isSuperRefuseContentValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_superRefuse4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_superRefuse)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_additionSign4Lang)}","customizeUsageContentAdditionSign","isAdditionSignContentValidated","${lbpmUsageContent.isAdditionSignContentValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_additionSign4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_additionSign)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assign4Lang)}","customizeUsageContentAssign","isAssignContentValidated","${lbpmUsageContent.isAssignContentValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assign4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assign)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignPass4Lang)}","customizeUsageContentAssignPass","isAssignPassContentValidated","${lbpmUsageContent.isAssignPassContentValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignPass4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignPass)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignRefuse4Lang)}","customizeUsageContentAssignRefuse","isAssignRefuseContentValidated","${lbpmUsageContent.isAssignRefuseContentValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignRefuse4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignRefuse)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_jump4Lang)}","customizeUsageContentJump","isAssignRefuseContentValidated","${lbpmUsageContent.isJumpContentValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_jump4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_jump)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeSuspend4Lang)}","customizeUsageContentNodeSuspend","isNodeSuspendContentValidated","${lbpmUsageContent.isNodeSuspendContentValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeSuspend4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeSuspend)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeResume4Lang)}","customizeUsageContentNodeResume","isNodeResumeContentValidated","${lbpmUsageContent.isNodeResumeContentValidated}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeResume4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeResume)}");
					 */
					
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_repeatJump4Lang)}","customizeUsageContent_repeatJump","customizeRepeatJumpValue","${lbpmUsageContent.defaultRepeatJumpValue}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_repeatJump4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_repeatJump)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_repeatJump4Lang)}","customizeUsageContent_repeatJump","customizeBeforeRepeatJumpValue","${lbpmUsageContent.beforeRepeatJumpValue}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_repeatJump4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_repeatJump)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_repeatJump4Lang)}","customizeUsageContent_repeatJump","customizeSystemRepeatJumpValue","${lbpmUsageContent.systemRepeatJumpValue}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_repeatJump4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_repeatJump)}");
					
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_timeOut4Lang)}","customizeUsageContent_timeOut","customizeTimeOutValue","${lbpmUsageContent.defaultTimeOutValue}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_timeOut4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_timeOut)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_timeOut4Lang)}","customizeUsageContent_timeOut","customizeSystemTimeOutValue","${lbpmUsageContent.systemTimeOutValue}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_timeOut4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_timeOut)}");
					
					
				}
			}else{
				var lbpmCustomizeContentJsonStr=dialogObject.lbpmCustomizeParameter.dataInfo;
				if(lbpmCustomizeContentJsonStr){
					var lbpmCustomizeContentJson=JSON.parse(lbpmCustomizeContentJsonStr);
					//有扩展属性值
					if(lbpmCustomizeContentJson){
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'isPassContentRequired','customizeUsageContent');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'isRefuseContentRequired','customizeUsageContentRefuse');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'isCommissionContentRequired','customizeUsageContentCommission');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'isCommunicateContentRequired','customizeUsageContentCommunicate');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'isAbandonContentRequired','customizeUsageContentAandon');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'isSuperRefuseContentRequired','customizeUsageContentSuperRefuse');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'isAdditionSignContentRequired','customizeUsageContentAdditionSign');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'isAssignContentRequired','customizeUsageContentAssign');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'isAssignPassContentRequired','customizeUsageContentAssignPass');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'isAssignRefuseContentRequired','customizeUsageContentAssignRefuse');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'isJumpContentRequired','customizeUsageContentJump');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'isNodeSuspendContentRequired','customizeUsageContentNodeSuspend');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'isNodeResumeContentRequired','customizeUsageContentNodeResume');
						
					   /*  initHasValuenoSuportLang(lbpmCustomizeContentJson,'isPassContentValidated','customizeUsageContent');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'isRefuseContentValidated','customizeUsageContentRefuse');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'isCommissionContentValidated','customizeUsageContentCommission');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'isCommunicateContentValidated','customizeUsageContentCommunicate');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'isAbandonContentValidated','customizeUsageContentAandon');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'isSuperRefuseContentValidated','customizeUsageContentSuperRefuse');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'isAdditionSignContentValidated','customizeUsageContentAdditionSign');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'isAssignContentValidated','customizeUsageContentAssign');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'isAssignPassContentValidated','customizeUsageContentAssignPass');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'isAssignRefuseContentValidated','customizeUsageContentAssignRefuse');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'isJumpContentValidated','customizeUsageContentJump');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'isNodeSuspendContentValidated','customizeUsageContentNodeSuspend');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'isNodeResumeContentValidated','customizeUsageContentNodeResume');
						
						 */
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'customizeRepeatJumpValue','customizeUsageContent_repeatJump');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'customizeBeforeRepeatJumpValue','customizeUsageContent_repeatJump');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'customizeSystemRepeatJumpValue','customizeUsageContent_repeatJump');
						
						
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'customizeTimeOutValue','customizeUsageContent_timeOut');
						initHasValuenoSuportLang(lbpmCustomizeContentJson,'customizeSystemTimeOutValue','customizeUsageContent_timeOut');
						
						
					}
				}else{
					initNoValuenoSuportLang('isPassContentRequired','${lbpmUsageContent.isPassContentRequired}','customizeUsageContent',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent)}");
					initNoValuenoSuportLang('isRefuseContentRequired','${lbpmUsageContent.isRefuseContentRequired}','customizeUsageContentRefuse',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_refuse)}");
					initNoValuenoSuportLang('isCommissionContentRequired','${lbpmUsageContent.isCommissionContentRequired}','customizeUsageContentCommission',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_commission)}");
					initNoValuenoSuportLang('isCommunicateContentRequired','${lbpmUsageContent.isCommunicateContentRequired}','customizeUsageContentCommunicate',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_communicate)}");
					initNoValuenoSuportLang('isAbandonContentRequired','${lbpmUsageContent.isAbandonContentRequired}','customizeUsageContentAandon',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_abandon)}");
					initNoValuenoSuportLang('isSuperRefuseContentRequired','${lbpmUsageContent.isSuperRefuseContentRequired}','customizeUsageContentSuperRefuse',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_superRefuse)}");
					initNoValuenoSuportLang('isAdditionSignContentRequired','${lbpmUsageContent.isAdditionSignContentRequired}','customizeUsageContentAdditionSign',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_additionSign)}");
					initNoValuenoSuportLang('isAssignContentRequired','${lbpmUsageContent.isAssignContentRequired}','customizeUsageContentAssign',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assign)}");
					initNoValuenoSuportLang('isAssignPassContentRequired','${lbpmUsageContent.isAssignPassContentRequired}','customizeUsageContentAssignPass',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignPass)}");
					initNoValuenoSuportLang('isAssignRefuseContentRequired','${lbpmUsageContent.isAssignRefuseContentRequired}','customizeUsageContentAssignRefuse',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignRefuse)}");
					initNoValuenoSuportLang('isJumpContentRequired','${lbpmUsageContent.isJumpContentRequired}','customizeUsageContentJump',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_jump)}");
					initNoValuenoSuportLang('isNodeSuspendContentRequired','${lbpmUsageContent.isNodeSuspendContentRequired}','customizeUsageContentNodeSuspend',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeSuspend)}");
					initNoValuenoSuportLang('isNodeResumeContentRequired','${lbpmUsageContent.isNodeResumeContentRequired}','customizeUsageContentNodeResume',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeResume)}");
					
					/* initNoValuenoSuportLang('isPassContentValidated','${lbpmUsageContent.isPassContentValidated}','customizeUsageContent',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent)}");
					initNoValuenoSuportLang('isRefuseContentValidated','${lbpmUsageContent.isRefuseContentValidated}','customizeUsageContentRefuse',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_refuse)}");
					initNoValuenoSuportLang('isCommissionContentValidated','${lbpmUsageContent.isCommissionContentValidated}','customizeUsageContentCommission',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_commission)}");
					initNoValuenoSuportLang('isCommunicateContentValidated','${lbpmUsageContent.isCommunicateContentValidated}','customizeUsageContentCommunicate',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_communicate)}");
					initNoValuenoSuportLang('isAbandonContentValidated','${lbpmUsageContent.isAbandonContentValidated}','customizeUsageContentAandon',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_abandon)}");
					initNoValuenoSuportLang('isSuperRefuseContentValidated','${lbpmUsageContent.isSuperRefuseContentValidated}','customizeUsageContentSuperRefuse',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_superRefuse)}");
					initNoValuenoSuportLang('isAdditionSignContentValidated','${lbpmUsageContent.isAdditionSignContentValidated}','customizeUsageContentAdditionSign',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_additionSign)}");
					initNoValuenoSuportLang('isAssignContentValidated','${lbpmUsageContent.isAssignContentValidated}','customizeUsageContentAssign',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assign)}");
					initNoValuenoSuportLang('isAssignPassContentValidated','${lbpmUsageContent.isAssignPassContentValidated}','customizeUsageContentAssignPass',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignPass)}");
					initNoValuenoSuportLang('isAssignRefuseContentValidated','${lbpmUsageContent.isAssignRefuseContentValidated}','customizeUsageContentAssignRefuse',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_assignRefuse)}");
					initNoValuenoSuportLang('isJumpContentValidated','${lbpmUsageContent.isJumpContentValidated}','customizeUsageContentJump',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_jump)}");
					initNoValuenoSuportLang('isNodeSuspendContentValidated','${lbpmUsageContent.isNodeSuspendContentValidated}','customizeUsageContentNodeSuspend',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeSuspend)}");
					initNoValuenoSuportLang('isNodeResumeContentValidated','${lbpmUsageContent.isNodeResumeContentValidated}','customizeUsageContentNodeResume',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeResume)}");
					 */
					
					initNoValuenoSuportLang('customizeRepeatJumpValue','${lbpmUsageContent.defaultRepeatJumpValue}','customizeUsageContent_repeatJump',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_repeatJump)}");
					initNoValuenoSuportLang('customizeBeforeRepeatJumpValue','${lbpmUsageContent.beforeRepeatJumpValue}','customizeUsageContent_repeatJump',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_repeatJump)}");
					initNoValuenoSuportLang('customizeSystemRepeatJumpValue','${lbpmUsageContent.systemRepeatJumpValue}','customizeUsageContent_repeatJump',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_repeatJump)}");
					
					initNoValuenoSuportLang('customizeTimeOutValue','${lbpmUsageContent.defaultTimeOutValue}','customizeUsageContent_timeOut',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_timeOut)}");
					initNoValuenoSuportLang('customizeSystemTimeOutValue','${lbpmUsageContent.systemTimeOutValue}','customizeUsageContent_timeOut',"${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_timeOut)}");
					
					
				}
			}
		});
		
		function initHasValuenoSuportLang(lbpmCustomizeContentJson,requiredFiled,customizeUsageContentFiled){
			var isPassContentRequired=lbpmCustomizeContentJson[requiredFiled];
			$("input[name='"+requiredFiled+"']").val(isPassContentRequired);
			LUI(requiredFiled).checkbox.prop('checked',isPassContentRequired=="true");
			document.getElementsByName(customizeUsageContentFiled)[0].value=lbpmCustomizeContentJson[customizeUsageContentFiled];
		}
		
		function initNoValuenoSuportLang(requiredFiledName,requiredValue,customizeUsageContentFiled,customizeUsageContentValue){
			
			var isPassContentRequired=requiredValue;
			$("input[name='"+requiredFiledName+"']").val(isPassContentRequired);
			LUI(requiredFiledName).checkbox.prop('checked',isPassContentRequired=="true");
			document.getElementsByName(customizeUsageContentFiled)[0].value=customizeUsageContentValue;
			
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
				
				$("input[name="+eleName+"_lang]").val(JSON.stringify(eleNameLangJsonStr));
			}else{//即使没有多语言，也需要更新多语言环境下的官方语言值 [{"lang":"zh-CN","value":"&nbsp;"},{"lang":"en-US","value":"&nbsp;"}]
				$("input[name="+eleName+"_lang]").val('[{"lang":"'+currentLang+'","value":'+'"'+fdValue+'"}]');
			}
		}
		
		
		
		function handleLangByElName(eleName,elJsonName){
			var elLang=[];
			if(!isLangSuportEnabled){
				return elLang;
			}
			var fdValue = document.getElementsByName(eleName)[0].value;
			
			var officialElName=eleName+"_"+langJson["official"]["value"];
			document.getElementsByName(officialElName)[0].value=fdValue;
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
			document.getElementsByName(elJsonName)[0].value=JSON.stringify(elLang);
		}
		
		// 设置审批意见校验
		function lbpmCustomizeValidateFunc(val){
			var lbpmCustomizeParameterValidate = {
					dataInfo : $("#lbpmCustomizeValidateJson").val(),
					returnType: "String"
			};
			var setAfterShow = function (rtn){
				if(rtn){
					$("#lbpmCustomizeValidateJson").val(rtn.jsonCustomizeValidateText);
					console.log("par"+$("#lbpmCustomizeValidateJson").val());
					}
			}
			
			// 弹出框自适应
			var width = screen.width * 0.55;
			if(width < 800)
				width = 800;
			var height = screen.height * 0.63;
			if(height < 560)
				height = 560;
			var	params = {val:val,win:window,lbpmCustomizeParameterValidate:lbpmCustomizeParameterValidate};//
			top['seajs'].use('lui/dialog', function(dialog) {
				//var fdValidateTitle = Data_GetResourceString("sys-lbpmservice:lbpmUsageContent.fdValidateTitle");
				var url="/sys/lbpmservice/support/lbpm_usage/lbpmCustomizeValidateUsageContent_edit.jsp";
				dialogUi = dialog.iframe(url,"审批意见校验",setAfterShow,{width : width,height : height,params:params});
				
			});
		}
		
		
		function setCustomizeUsageContent(){
			handleLangByElName("customizeUsageContent","customizeUsageContent_lang");
			handleLangByElName("customizeUsageContentRefuse","customizeUsageContentRefuse_lang");
			handleLangByElName("customizeUsageContentCommission","customizeUsageContentCommission_lang");
			handleLangByElName("customizeUsageContentCommunicate","customizeUsageContentCommunicate_lang");
			handleLangByElName("customizeUsageContentAandon","customizeUsageContentAandon_lang");
			handleLangByElName("customizeUsageContentSuperRefuse","customizeUsageContentSuperRefuse_lang");
			handleLangByElName("customizeUsageContentAdditionSign","customizeUsageContentAdditionSign_lang");
			handleLangByElName("customizeUsageContentAssign","customizeUsageContentAssign_lang");
			handleLangByElName("customizeUsageContentAssignPass","customizeUsageContentAssignPass_lang");
			handleLangByElName("customizeUsageContentAssignRefuse","customizeUsageContentAssignRefuse_lang");
			handleLangByElName("customizeUsageContentJump","customizeUsageContentJump_lang");
			handleLangByElName("customizeUsageContentNodeSuspend","customizeUsageContentNodeSuspend_lang");
			handleLangByElName("customizeUsageContentNodeResume","customizeUsageContentNodeResume_lang");
			
			handleLangByElName("customizeUsageContent_repeatJump","customizeUsageContent_repeatJump_lang");
			handleLangByElName("customizeUsageContent_timeOut","customizeUsageContent_timeOut_lang");
		
			if(!isLangSuportEnabled){
				handlerOfficialLangValue("customizeUsageContent");
				handlerOfficialLangValue("customizeUsageContentRefuse");
				handlerOfficialLangValue("customizeUsageContentCommission");
				handlerOfficialLangValue("customizeUsageContentCommunicate");
				handlerOfficialLangValue("customizeUsageContentAandon");
				handlerOfficialLangValue("customizeUsageContentSuperRefuse");
				handlerOfficialLangValue("customizeUsageContentAdditionSign");
				handlerOfficialLangValue("customizeUsageContentAssign");
				handlerOfficialLangValue("customizeUsageContentAssignPass");
				handlerOfficialLangValue("customizeUsageContentAssignRefuse");
				handlerOfficialLangValue("customizeUsageContentJump");
				handlerOfficialLangValue("customizeUsageContentNodeSuspend");
				
				handlerOfficialLangValue("customizeUsageContent_repeatJump");
				handlerOfficialLangValue("customizeUsageContent_timeOut");
			}
			
			
			var jsonCustomize={};
			jsonCustomize.customizeUsageContent=$("textarea[name='customizeUsageContent']").val();
			jsonCustomize.customizeUsageContent_lang=$("input[name='customizeUsageContent_lang']").val();
			jsonCustomize.isPassContentRequired=$("input[name='isPassContentRequired']").val();
			//jsonCustomize.isPassContentValidated=$("input[name='isPassContentValidated']").val();

			
			jsonCustomize.customizeUsageContentRefuse=$("textarea[name='customizeUsageContentRefuse']").val();
			jsonCustomize.customizeUsageContentRefuse_lang=$("input[name='customizeUsageContentRefuse_lang']").val();
			jsonCustomize.isRefuseContentRequired=$("input[name='isRefuseContentRequired']").val();
			//jsonCustomize.isRefuseContentValidated=$("input[name='isRefuseContentValidated']").val();

			
			jsonCustomize.customizeUsageContentCommission=$("textarea[name='customizeUsageContentCommission']").val();
			jsonCustomize.customizeUsageContentCommission_lang=$("input[name='customizeUsageContentCommission_lang']").val();
			jsonCustomize.isCommissionContentRequired=$("input[name='isCommissionContentRequired']").val();
			//jsonCustomize.isCommissionContentValidated=$("input[name='isCommissionContentValidated']").val();

			
			jsonCustomize.customizeUsageContent=$("textarea[name='customizeUsageContent']").val();
			jsonCustomize.customizeUsageContent_lang=$("input[name='customizeUsageContent_lang']").val();
			jsonCustomize.isPassContentRequired=$("input[name='isPassContentRequired']").val();
			//jsonCustomize.isPassContentValidated=$("input[name='isPassContentValidated']").val();

			
			jsonCustomize.customizeUsageContentCommunicate=$("textarea[name='customizeUsageContentCommunicate']").val();
			jsonCustomize.customizeUsageContentCommunicate_lang=$("input[name='customizeUsageContentCommunicate_lang']").val();
			jsonCustomize.isCommunicateContentRequired=$("input[name='isCommunicateContentRequired']").val();
			//jsonCustomize.isCommunicateContentValidated=$("input[name='isCommunicateContentValidated']").val();

			
			jsonCustomize.customizeUsageContentAandon=$("textarea[name='customizeUsageContentAandon']").val();
			jsonCustomize.customizeUsageContentAandon_lang=$("input[name='customizeUsageContentAandon_lang']").val();
			jsonCustomize.isAbandonContentRequired=$("input[name='isAbandonContentRequired']").val();
			//jsonCustomize.isAbandonContentValidated=$("input[name='isAbandonContentValidated']").val();

			
			jsonCustomize.customizeUsageContent=$("textarea[name='customizeUsageContent']").val();
			jsonCustomize.customizeUsageContent_lang=$("input[name='customizeUsageContent_lang']").val();
			jsonCustomize.isPassContentRequired=$("input[name='isPassContentRequired']").val();
			//jsonCustomize.isPassContentValidated=$("input[name='isPassContentValidated']").val();

			
			jsonCustomize.customizeUsageContentSuperRefuse=$("textarea[name='customizeUsageContentSuperRefuse']").val();
			jsonCustomize.customizeUsageContentSuperRefuse_lang=$("input[name='customizeUsageContentSuperRefuse_lang']").val();
			jsonCustomize.isSuperRefuseContentRequired=$("input[name='isSuperRefuseContentRequired']").val();
			//jsonCustomize.isSuperRefuseContentValidated=$("input[name='isSuperRefuseContentValidated']").val();

			
			jsonCustomize.customizeUsageContentAdditionSign=$("textarea[name='customizeUsageContentAdditionSign']").val();
			jsonCustomize.customizeUsageContentAdditionSign_lang=$("input[name='customizeUsageContentAdditionSign_lang']").val();
			jsonCustomize.isAdditionSignContentRequired=$("input[name='isAdditionSignContentRequired']").val();
			//jsonCustomize.isAdditionSignContentValidated=$("input[name='isAdditionSignContentValidated']").val();

			
			jsonCustomize.customizeUsageContent=$("textarea[name='customizeUsageContent']").val();
			jsonCustomize.customizeUsageContent_lang=$("input[name='customizeUsageContent_lang']").val();
			jsonCustomize.isPassContentRequired=$("input[name='isPassContentRequired']").val();
			//jsonCustomize.isPassContentValidated=$("input[name='isPassContentValidated']").val();

			
			jsonCustomize.customizeUsageContentAssign=$("textarea[name='customizeUsageContentAssign']").val();
			jsonCustomize.customizeUsageContentAssign_lang=$("input[name='customizeUsageContentAssign_lang']").val();
			jsonCustomize.isAssignContentRequired=$("input[name='isAssignContentRequired']").val();
			//jsonCustomize.isAssignContentValidated=$("input[name='isAssignContentValidated']").val();

			
			jsonCustomize.customizeUsageContentAssignPass=$("textarea[name='customizeUsageContentAssignPass']").val();
			jsonCustomize.customizeUsageContentAssignPass_lang=$("input[name='customizeUsageContentAssignPass_lang']").val();
			jsonCustomize.isAssignPassContentRequired=$("input[name='isAssignPassContentRequired']").val();
			//jsonCustomize.isAssignPassContentValidated=$("input[name='isAssignPassContentValidated']").val();

			
			jsonCustomize.customizeUsageContentAssignRefuse=$("textarea[name='customizeUsageContentAssignRefuse']").val();
			jsonCustomize.customizeUsageContentAssignRefuse_lang=$("input[name='customizeUsageContentAssignRefuse_lang']").val();
			jsonCustomize.isAssignRefuseContentRequired=$("input[name='isAssignRefuseContentRequired']").val();
			//jsonCustomize.isAssignRefuseContentValidated=$("input[name='isAssignRefuseContentValidated']").val();

			
			jsonCustomize.customizeUsageContentJump=$("textarea[name='customizeUsageContentJump']").val();
			jsonCustomize.customizeUsageContentJump_lang=$("input[name='customizeUsageContentJump_lang']").val();
			jsonCustomize.isJumpContentRequired=$("input[name='isJumpContentRequired']").val();
			//jsonCustomize.isJumpContentValidated=$("input[name='isJumpContentValidated']").val();
			
			jsonCustomize.customizeUsageContentNodeSuspend=$("textarea[name='customizeUsageContentNodeSuspend']").val();
			jsonCustomize.customizeUsageContentNodeSuspend_lang=$("input[name='customizeUsageContentNodeSuspend_lang']").val();
			jsonCustomize.isNodeSuspendContentRequired=$("input[name='isNodeSuspendContentRequired']").val();
			//jsonCustomize.isNodeSuspendContentValidated=$("input[name='isNodeSuspendContentValidated']").val();

			
			jsonCustomize.customizeUsageContentNodeResume=$("textarea[name='customizeUsageContentNodeResume']").val();
			jsonCustomize.customizeUsageContentNodeResume_lang=$("input[name='customizeUsageContentNodeResume_lang']").val();
			jsonCustomize.isNodeResumeContentRequired=$("input[name='isNodeResumeContentRequired']").val();
			//jsonCustomize.isNodeResumeContentValidated=$("input[name='isNodeResumeContentValidated']").val();

			
			jsonCustomize.customizeUsageContent_repeatJump=$("textarea[name='customizeUsageContent_repeatJump']").val();
			jsonCustomize.customizeUsageContent_repeatJump_lang=$("input[name='customizeUsageContent_repeatJump_lang']").val();
			jsonCustomize.customizeRepeatJumpValue=$("input[name='customizeRepeatJumpValue']").val();
			jsonCustomize.customizeBeforeRepeatJumpValue=$("input[name='customizeBeforeRepeatJumpValue']").val();
			jsonCustomize.customizeSystemRepeatJumpValue=$("input[name='customizeSystemRepeatJumpValue']").val();
			
			
			jsonCustomize.customizeUsageContent_timeOut=$("textarea[name='customizeUsageContent_timeOut']").val();
			jsonCustomize.customizeUsageContent_timeOut_lang=$("input[name='customizeUsageContent_timeOut_lang']").val();
			jsonCustomize.customizeTimeOutValue=$("input[name='customizeTimeOutValue']").val();
			jsonCustomize.customizeSystemTimeOutValue=$("input[name='customizeSystemTimeOutValue']").val();
			
			
			var jsonCustomizeText=JSON.stringify(jsonCustomize);
			
			var jsonCustomizeValidate = $("#lbpmCustomizeValidateJson").val();
			
			dialogObject.rtnData=[{'jsonCustomize':jsonCustomizeText},{'jsonCustomizeValidate':jsonCustomizeValidate}];
			
			close();
		}

		//添加关闭事件
		Com_AddEventListener(window, "beforeunload", function(){dialogObject.AfterShow();});
		

	 	</script>
	</template:replace>
</template:include>
