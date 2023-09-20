<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>


<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextareaGroupTag"%>
<%
	pageContext.setAttribute("isLangSuportEnabled", MultiLangTextareaGroupTag.isLangSuportEnabled());
%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title"><bean:message bundle="sys-lbpmservice-support" key="table.lbpmUsageContent"/></span>
		</h2>
		
		<html:form action="/sys/lbpmservice/support/lbpmConfigAction.do">
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
							
						</tr>
						<tr>
							<td width=80%>
							<c:choose>
								<c:when test="${empty sysAppConfigForm.map.defaultUsageContent}">
									<%-- <xform:textarea property="value(defaultUsageContent)" value="${lfn:message('sys-lbpmservice:lbpmProcess.handler.usageContent.default')}" style="width:100%;height:80px" validators="maxLength(4000)"/> --%>
									<c:if test="${!isLangSuportEnabled }">
										<xform:textarea property="value(defaultUsageContent)" value="${lfn:message('sys-lbpmservice:lbpmProcess.handler.usageContent.default')}" style="width:100%;height:80px" validators="maxLength(4000)"/>
									</c:if>
									<c:if test="${isLangSuportEnabled }">
										<xlang:lbpmlangAreaNew validators="maxLength(4000)" property="defaultUsageContent" alias="value(defaultUsageContent)" value="${lfn:message('sys-lbpmservice:lbpmProcess.handler.usageContent.default')}" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_lang}"/>
									</c:if>
								</c:when>
								<c:otherwise>
									<%-- <c:if test="${sysAppConfigForm.map.defaultUsageContent == '&nbsp;'}">
										<textarea name="value(defaultUsageContent)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
									</c:if>
									<c:if test="${sysAppConfigForm.map.defaultUsageContent != '&nbsp;'}">
										<xform:textarea property="value(defaultUsageContent)" style="width:100%;height:80px" validators="maxLength(4000)"/>
									</c:if> --%>
									<c:if test="${!isLangSuportEnabled }">
										<c:if test="${sysAppConfigForm.map.defaultUsageContent == '&nbsp;'}">
											<textarea name="value(defaultUsageContent)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
										</c:if>
										<c:if test="${sysAppConfigForm.map.defaultUsageContent != '&nbsp;'}">
											<xform:textarea property="value(defaultUsageContent)" style="width:100%;height:80px" validators="maxLength(4000)"/>
										</c:if>
									</c:if>
									<c:if test="${isLangSuportEnabled }">
										<xlang:lbpmlangAreaNew validators="maxLength(4000)" property="defaultUsageContent" alias="value(defaultUsageContent)" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_lang}"/>
									</c:if>
								</c:otherwise>
							</c:choose>

							<%-- <xlang:lbpmlangArea property="defaultUsageContent" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_lang}"/> --%>
							<html:hidden property="value(defaultUsageContent_lang)" />
							</td>
							<td align="center">
								<ui:switch property="value(isPassContentRequired)" id="isPassContentRequired" onValueChange="switchValidate('isPassContentValidated','isPassContentRequired')"></ui:switch>
							</td>
							
						</tr>
					</table>
				
				</td>
			</tr>
			
			<!-- 默认签字意见 -->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.handler.sign"/>
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
							
						</tr>
						<tr>
							<td width=80%>
							<%-- <c:choose>
								<c:when test="${sysAppConfigForm.map.defaultUsageContent_sign == '&nbsp;'}">
									<textarea name="value(defaultUsageContent_sign)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
								</c:when>
								<c:otherwise>
									<xform:textarea property="value(defaultUsageContent_sign)" style="width:100%;height:80px" validators="maxLength(4000)"/>
								</c:otherwise>
							</c:choose>
							<xlang:lbpmlangArea property="defaultUsageContent_sign" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_sign_lang}"/> --%>
							<c:if test="${!isLangSuportEnabled }">
								 <c:choose>
									<c:when test="${sysAppConfigForm.map.defaultUsageContent_sign == '&nbsp;'}">
										<textarea name="value(defaultUsageContent_sign)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
									</c:when>
									<c:otherwise>
										<xform:textarea property="value(defaultUsageContent_sign)" style="width:100%;height:80px" validators="maxLength(4000)"/>
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="defaultUsageContent_sign" alias="value(defaultUsageContent_sign)" validators="maxLength(4000)" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_sign_lang}"/>
							</c:if>
							<html:hidden property="value(defaultUsageContent_sign_lang)" />

							</td>
							<td align="center">
								<c:if test="${sysAppConfigForm.map.isSignContentRequired != null}">
								<ui:switch property="value(isSignContentRequired)" id="isSignContentRequired" onValueChange="switchValidate('isSignContentValidated','isSignContentRequired')"></ui:switch>
								</c:if>
								<c:if test="${sysAppConfigForm.map.isSignContentRequired == null}">
								<ui:switch checked="true" property="value(isSignContentRequired)" id="isSignContentRequired" onValueChange="switchValidate('isSignContentValidated','isSignContentRequired')"></ui:switch>
								</c:if>
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
							
						</tr>
						<tr>
							<td width=80%>
							<%-- <c:choose>
								<c:when test="${sysAppConfigForm.map.defaultUsageContent_refuse == '&nbsp;'}">
									<textarea name="value(defaultUsageContent_refuse)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
								</c:when>
								<c:otherwise>
									<xform:textarea property="value(defaultUsageContent_refuse)" style="width:100%;height:80px" validators="maxLength(4000)"/>
								</c:otherwise>
							</c:choose>
							<xlang:lbpmlangArea property="defaultUsageContent_refuse" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_refuse_lang}"/> --%>
							<c:if test="${!isLangSuportEnabled }">
								<c:choose>
									<c:when test="${sysAppConfigForm.map.defaultUsageContent_refuse == '&nbsp;'}">
										<textarea name="value(defaultUsageContent_refuse)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
									</c:when>
									<c:otherwise>
										<xform:textarea property="value(defaultUsageContent_refuse)" style="width:100%;height:80px" validators="maxLength(4000)"/>
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="defaultUsageContent_refuse" alias="value(defaultUsageContent_refuse)" validators="maxLength(4000)" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_refuse_lang}"/>
							</c:if>
							<html:hidden property="value(defaultUsageContent_refuse_lang)" />
							</td>
							<td align="center">
								<c:if test="${sysAppConfigForm.map.isRefuseContentRequired != null}">
								<ui:switch property="value(isRefuseContentRequired)" id="isRefuseContentRequired" onValueChange="switchValidate('isRefuseContentValidated','isRefuseContentRequired')"></ui:switch>
								</c:if>
								<c:if test="${sysAppConfigForm.map.isRefuseContentRequired == null}">	
								<ui:switch checked="true" property="value(isRefuseContentRequired)" id="isRefuseContentRequired" onValueChange="switchValidate('isRefuseContentValidated','isRefuseContentRequired')"></ui:switch>
								</c:if>
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
							
						</tr>
						<tr>
							<td width=80%>
							<%-- <c:choose>
								<c:when test="${sysAppConfigForm.map.defaultUsageContent_commission == '&nbsp;'}">
									<textarea name="value(defaultUsageContent_commission)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
								</c:when>
								<c:otherwise>
									<xform:textarea property="value(defaultUsageContent_commission)" style="width:100%;height:80px" validators="maxLength(4000)"/>
								</c:otherwise>
							</c:choose>
							<xlang:lbpmlangArea property="defaultUsageContent_commission" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_commission_lang}"/> --%>
							<c:if test="${!isLangSuportEnabled }">
								<c:choose>
									<c:when test="${sysAppConfigForm.map.defaultUsageContent_commission == '&nbsp;'}">
										<textarea name="value(defaultUsageContent_commission)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
									</c:when>
									<c:otherwise>
										<xform:textarea property="value(defaultUsageContent_commission)" style="width:100%;height:80px" validators="maxLength(4000)"/>
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="defaultUsageContent_commission" alias="value(defaultUsageContent_commission)" validators="maxLength(4000)" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_commission_lang}"/>
							</c:if>
							<html:hidden property="value(defaultUsageContent_commission_lang)" />

							</td>
							<td align="center">
								<ui:switch property="value(isCommissionContentRequired)" id="isCommissionContentRequired" onValueChange="switchValidate('isCommissionContentValidated','isCommissionContentRequired')"></ui:switch>
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
							
						</tr>
						<tr>
							<td width=80%>
							<%-- <c:choose>
								<c:when test="${sysAppConfigForm.map.defaultUsageContent_communicate == '&nbsp;'}">
									<textarea name="value(defaultUsageContent_communicate)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
								</c:when>
								<c:otherwise>
									<xform:textarea property="value(defaultUsageContent_communicate)" style="width:100%;height:80px" validators="maxLength(4000)"/>
								</c:otherwise>
							</c:choose>
							<xlang:lbpmlangArea property="defaultUsageContent_communicate" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_communicate_lang}"/> --%>
							<c:if test="${!isLangSuportEnabled }">
								<c:choose>
									<c:when test="${sysAppConfigForm.map.defaultUsageContent_communicate == '&nbsp;'}">
										<textarea name="value(defaultUsageContent_communicate)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
									</c:when>
									<c:otherwise>
										<xform:textarea property="value(defaultUsageContent_communicate)" style="width:100%;height:80px" validators="maxLength(4000)"/>
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="defaultUsageContent_communicate" alias="value(defaultUsageContent_communicate)" validators="maxLength(4000)" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_communicate_lang}"/>
							</c:if>
							<html:hidden property="value(defaultUsageContent_communicate_lang)" />
							</td>
							<td align="center">
								<c:if test="${sysAppConfigForm.map.isCommunicateContentRequired != null}">
								<ui:switch property="value(isCommunicateContentRequired)" id="isCommunicateContentRequired" onValueChange="switchValidate('isCommunicateContentValidated','isCommunicateContentRequired')"></ui:switch>
								</c:if>
								<c:if test="${sysAppConfigForm.map.isCommunicateContentRequired == null}">
								<ui:switch checked="true" property="value(isCommunicateContentRequired)" id="isCommunicateContentRequired" onValueChange="switchValidate('isCommunicateContentValidated','isCommunicateContentRequired')"></ui:switch>
								</c:if>
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
						
						</tr>
						<tr>
							<td width=80%>
							<%-- <c:choose>
								<c:when test="${sysAppConfigForm.map.defaultUsageContent_abandon == '&nbsp;'}">
									<textarea name="value(defaultUsageContent_abandon)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
								</c:when>
								<c:otherwise>
									<xform:textarea property="value(defaultUsageContent_abandon)" style="width:100%;height:80px" validators="maxLength(4000)"/>
								</c:otherwise>
							</c:choose>
							<xlang:lbpmlangArea property="defaultUsageContent_abandon" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_abandon_lang}"/> --%>
							<c:if test="${!isLangSuportEnabled }">
								 <c:choose>
									<c:when test="${sysAppConfigForm.map.defaultUsageContent_abandon == '&nbsp;'}">
										<textarea name="value(defaultUsageContent_abandon)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
									</c:when>
									<c:otherwise>
										<xform:textarea property="value(defaultUsageContent_abandon)" style="width:100%;height:80px" validators="maxLength(4000)"/>
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="defaultUsageContent_abandon" alias="value(defaultUsageContent_abandon)" validators="maxLength(4000)" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_abandon_lang}"/>
							</c:if>
							<html:hidden property="value(defaultUsageContent_abandon_lang)" />
							</td>
							<td align="center">
								<c:if test="${sysAppConfigForm.map.isAbandonContentRequired != null}">
								<ui:switch property="value(isAbandonContentRequired)" id="isAbandonContentRequired" onValueChange="switchValidate('isAbandonContentValidated','isAbandonContentRequired')"></ui:switch>
								</c:if>
								<c:if test="${sysAppConfigForm.map.isAbandonContentRequired == null}">
								<ui:switch checked="true" property="value(isAbandonContentRequired)" id="isAbandonContentRequired" onValueChange="switchValidate('isAbandonContentValidated','isAbandonContentRequired')"></ui:switch>
								</c:if>
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
							
						</tr>
						<tr>
							<td width=80%>
							<%-- <c:choose>
								<c:when test="${sysAppConfigForm.map.defaultUsageContent_superRefuse == '&nbsp;'}">
									<textarea name="value(defaultUsageContent_superRefuse)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
								</c:when>
								<c:otherwise>
									<xform:textarea property="value(defaultUsageContent_superRefuse)" style="width:100%;height:80px" validators="maxLength(4000)"/>
								</c:otherwise>
							</c:choose>
							<xlang:lbpmlangArea property="defaultUsageContent_superRefuse" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_superRefuse_lang}"/> --%>
							<c:if test="${!isLangSuportEnabled }">
								 <c:choose>
									<c:when test="${sysAppConfigForm.map.defaultUsageContent_superRefuse == '&nbsp;'}">
										<textarea name="value(defaultUsageContent_superRefuse)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
									</c:when>
									<c:otherwise>
										<xform:textarea property="value(defaultUsageContent_superRefuse)" style="width:100%;height:80px" validators="maxLength(4000)"/>
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="defaultUsageContent_superRefuse" alias="value(defaultUsageContent_superRefuse)" validators="maxLength(4000)" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_superRefuse_lang}"/>
							</c:if>
							<html:hidden property="value(defaultUsageContent_superRefuse_lang)" />
							</td>
							<td align="center">
								<c:if test="${sysAppConfigForm.map.isSuperRefuseContentRequired != null}">
								<ui:switch property="value(isSuperRefuseContentRequired)" id="isSuperRefuseContentRequired" onValueChange="switchValidate('isSuperRefuseContentValidated','isSuperRefuseContentRequired')"></ui:switch>								
								</c:if>
								<c:if test="${sysAppConfigForm.map.isSuperRefuseContentRequired == null}">
								<ui:switch checked="true" property="value(isSuperRefuseContentRequired)" id="isSuperRefuseContentRequired" onValueChange="switchValidate('isSuperRefuseContentValidated','isSuperRefuseContentRequired')"></ui:switch>								
								</c:if>
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
						</tr>
						<tr>
							<td width=80%>
							<%-- <c:choose>
								<c:when test="${sysAppConfigForm.map.defaultUsageContent_additionSign == '&nbsp;'}">
									<textarea name="value(defaultUsageContent_additionSign)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
								</c:when>
								<c:otherwise>
									<xform:textarea property="value(defaultUsageContent_additionSign)" style="width:100%;height:80px" validators="maxLength(4000)"/>
								</c:otherwise>
							</c:choose>
							<xlang:lbpmlangArea property="defaultUsageContent_additionSign" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_additionSign_lang}"/> --%>
							<c:if test="${!isLangSuportEnabled }">
								 <c:choose>
									<c:when test="${sysAppConfigForm.map.defaultUsageContent_additionSign == '&nbsp;'}">
										<textarea name="value(defaultUsageContent_additionSign)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
									</c:when>
									<c:otherwise>
										<xform:textarea property="value(defaultUsageContent_additionSign)" style="width:100%;height:80px" validators="maxLength(4000)"/>
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="defaultUsageContent_additionSign" alias="value(defaultUsageContent_additionSign)" validators="maxLength(4000)" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_additionSign_lang}"/>
							</c:if>
							<html:hidden property="value(defaultUsageContent_additionSign_lang)" />
							</td>
							<td align="center">
								<ui:switch property="value(isAdditionSignContentRequired)" id="isAdditionSignContentRequired" onValueChange="switchValidate('isAdditionSignContentValidated','isAdditionSignContentRequired')"></ui:switch>
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
							
						</tr>
						<tr>
							<td width=80%>
							<%-- <c:choose>
								<c:when test="${sysAppConfigForm.map.defaultUsageContent_assign == '&nbsp;'}">
									<textarea name="value(defaultUsageContent_assign)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
								</c:when>
								<c:otherwise>
									<xform:textarea property="value(defaultUsageContent_assign)" style="width:100%;height:80px" validators="maxLength(4000)"/>
								</c:otherwise>
							</c:choose>
							<xlang:lbpmlangArea property="defaultUsageContent_assign" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_assign_lang}"/> --%>
							<c:if test="${!isLangSuportEnabled }">
								 <c:choose>
									<c:when test="${sysAppConfigForm.map.defaultUsageContent_assign == '&nbsp;'}">
										<textarea name="value(defaultUsageContent_assign)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
									</c:when>
									<c:otherwise>
										<xform:textarea property="value(defaultUsageContent_assign)" style="width:100%;height:80px" validators="maxLength(4000)"/>
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="defaultUsageContent_assign" alias="value(defaultUsageContent_assign)" validators="maxLength(4000)" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_assign_lang}"/>
							</c:if>
							<html:hidden property="value(defaultUsageContent_assign_lang)" />
							</td>
							<td align="center">
								<ui:switch property="value(isAssignContentRequired)" id="isAssignContentRequired" onValueChange="switchValidate('isAssignContentValidated','isAssignContentRequired')"></ui:switch>
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
							
						</tr>
						<tr>
							<td width=80%>
							<%-- <c:choose>
								<c:when test="${sysAppConfigForm.map.defaultUsageContent_assignPass == '&nbsp;'}">
									<textarea name="value(defaultUsageContent_assignPass)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
								</c:when>
								<c:otherwise>
									<xform:textarea property="value(defaultUsageContent_assignPass)" style="width:100%;height:80px" validators="maxLength(4000)"/>
								</c:otherwise>
							</c:choose>
							<xlang:lbpmlangArea property="defaultUsageContent_assignPass" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_assignPass_lang}"/> --%>
							<c:if test="${!isLangSuportEnabled }">
								 <c:choose>
									<c:when test="${sysAppConfigForm.map.defaultUsageContent_assignPass == '&nbsp;'}">
										<textarea name="value(defaultUsageContent_assignPass)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
									</c:when>
									<c:otherwise>
										<xform:textarea property="value(defaultUsageContent_assignPass)" style="width:100%;height:80px" validators="maxLength(4000)"/>
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="defaultUsageContent_assignPass" alias="value(defaultUsageContent_assignPass)" validators="maxLength(4000)" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_assignPass_lang}"/>
							</c:if>
							<html:hidden property="value(defaultUsageContent_assignPass_lang)" />
							</td>
							<td align="center">
								<ui:switch property="value(isAssignPassContentRequired)" id="isAssignPassContentRequired" onValueChange="switchValidate('isAssignPassContentValidated','isAssignPassContentRequired')"></ui:switch>
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
							
						</tr>
						<tr>
							<td width=80%>
							<%-- <c:choose>
								<c:when test="${sysAppConfigForm.map.defaultUsageContent_assignRefuse == '&nbsp;'}">
									<textarea name="value(defaultUsageContent_assignRefuse)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
								</c:when>
								<c:otherwise>
									<xform:textarea property="value(defaultUsageContent_assignRefuse)" style="width:100%;height:80px" validators="maxLength(4000)"/>
								</c:otherwise>
							</c:choose>
							<xlang:lbpmlangArea property="defaultUsageContent_assignRefuse" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_assignRefuse_lang}"/> --%>
							<c:if test="${!isLangSuportEnabled }">
								 <c:choose>
									<c:when test="${sysAppConfigForm.map.defaultUsageContent_assignRefuse == '&nbsp;'}">
										<textarea name="value(defaultUsageContent_assignRefuse)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
									</c:when>
									<c:otherwise>
										<xform:textarea property="value(defaultUsageContent_assignRefuse)" style="width:100%;height:80px" validators="maxLength(4000)"/>
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="defaultUsageContent_assignRefuse" alias="value(defaultUsageContent_assignRefuse)" validators="maxLength(4000)" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_assignRefuse_lang}"/>
							</c:if>
							<html:hidden property="value(defaultUsageContent_assignRefuse_lang)" />
							</td>
							<td align="center">
								<ui:switch property="value(isAssignRefuseContentRequired)" id="isAssignRefuseContentRequired" onValueChange="switchValidate('isAssignRefuseContentValidated','isAssignRefuseContentRequired')"></ui:switch>
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
							
						</tr>
						<tr>
							<td width=80%>
							<%-- <c:choose>
								<c:when test="${sysAppConfigForm.map.defaultUsageContent_jump == '&nbsp;'}">
									<textarea name="value(defaultUsageContent_jump)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
								</c:when>
								<c:otherwise>
									<xform:textarea property="value(defaultUsageContent_jump)" style="width:100%;height:80px" validators="maxLength(4000)"/>
								</c:otherwise>
							</c:choose>
							<xlang:lbpmlangArea property="defaultUsageContent_jump" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_jump_lang}"/> --%>
							<c:if test="${!isLangSuportEnabled }">
								 <c:choose>
									<c:when test="${sysAppConfigForm.map.defaultUsageContent_jump == '&nbsp;'}">
										<textarea name="value(defaultUsageContent_jump)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
									</c:when>
									<c:otherwise>
										<xform:textarea property="value(defaultUsageContent_jump)" style="width:100%;height:80px" validators="maxLength(4000)"/>
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="defaultUsageContent_jump" alias="value(defaultUsageContent_jump)" validators="maxLength(4000)" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_jump_lang}"/>
							</c:if>
							<html:hidden property="value(defaultUsageContent_jump_lang)" />
							</td>
							<td align="center">
								<ui:switch property="value(isJumpContentRequired)" id="isjumpContentRequired" onValueChange="switchValidate('isJumpContentValidated','isJumpContentRequired')"></ui:switch>
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
							
						</tr>
						<tr>
							<td width=80%>
							<%-- <c:choose>
								<c:when test="${sysAppConfigForm.map.defaultUsageContent_nodeSuspend == '&nbsp;'}">
									<textarea name="value(defaultUsageContent_nodeSuspend)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
								</c:when>
								<c:otherwise>
									<xform:textarea property="value(defaultUsageContent_nodeSuspend)" style="width:100%;height:80px" validators="maxLength(4000)"/>
								</c:otherwise>
							</c:choose>
							<xlang:lbpmlangArea property="defaultUsageContent_nodeSuspend" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_nodeSuspend_lang}"/> --%>
							<c:if test="${!isLangSuportEnabled }">
								 <c:choose>
									<c:when test="${sysAppConfigForm.map.defaultUsageContent_nodeSuspend == '&nbsp;'}">
										<textarea name="value(defaultUsageContent_nodeSuspend)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
									</c:when>
									<c:otherwise>
										<xform:textarea property="value(defaultUsageContent_nodeSuspend)" style="width:100%;height:80px" validators="maxLength(4000)"/>
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="defaultUsageContent_nodeSuspend" alias="value(defaultUsageContent_nodeSuspend)" validators="maxLength(4000)" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_nodeSuspend_lang}"/>
							</c:if>
							<html:hidden property="value(defaultUsageContent_nodeSuspend_lang)" />
							</td>
							<td align="center">
								<ui:switch property="value(isNodeSuspendContentRequired)"  id="isNodeSuspendContentRequired" onValueChange="switchValidate('isNodeSuspendContentValidated','isNodeSuspendContentRequired')"></ui:switch>
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
							
						</tr>
						<tr>
							<td width=80%>
							<%-- <c:choose>
								<c:when test="${sysAppConfigForm.map.defaultUsageContent_nodeResume == '&nbsp;'}">
									<textarea name="value(defaultUsageContent_nodeResume)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
								</c:when>
								<c:otherwise>
									<xform:textarea property="value(defaultUsageContent_nodeResume)" style="width:100%;height:80px" validators="maxLength(4000)"/>
								</c:otherwise>
							</c:choose>
							<xlang:lbpmlangArea property="defaultUsageContent_nodeResume" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_nodeResume_lang}"/> --%>
							<c:if test="${!isLangSuportEnabled }">
								 <c:choose>
									<c:when test="${sysAppConfigForm.map.defaultUsageContent_nodeResume == '&nbsp;'}">
										<textarea name="value(defaultUsageContent_nodeResume)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
									</c:when>
									<c:otherwise>
										<xform:textarea property="value(defaultUsageContent_nodeResume)" style="width:100%;height:80px" validators="maxLength(4000)"/>
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="defaultUsageContent_nodeResume" alias="value(defaultUsageContent_nodeResume)" validators="maxLength(4000)" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_nodeResume_lang}"/>
							</c:if>
							<html:hidden property="value(defaultUsageContent_nodeResume_lang)" />
							</td>
							<td align="center">
								<ui:switch property="value(isNodeResumeContentRequired)" id="isNodeResumeContentRequired" onValueChange="switchValidate('isNodeResumeContentValidated','isNodeResumeContentRequired')"></ui:switch>
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
							<td width=10%><ui:switch property="value(defaultRepeatJumpValue)" id="defaultRepeatJumpValue" onValueChange="switchDefaultRepeatJump('defaultRepeatJumpValue','beforeRepeatJumpValue','systemRepeatJumpValue')"></ui:switch></td>
							<td width=30% class="td_normal_title">使用该处理人在之前节点的审核意见</td>
							<td width=10%><ui:switch property="value(beforeRepeatJumpValue)" id="beforeRepeatJumpValue" onValueChange="switchDefaultRepeatJump('beforeRepeatJumpValue','defaultRepeatJumpValue','systemRepeatJumpValue')"></ui:switch></td>
							<td width=20% class="td_normal_title">使用系统自动生成的意见</td>
							<td width=10%><ui:switch property="value(systemRepeatJumpValue)" id="systemRepeatJumpValue" onValueChange="switchDefaultRepeatJump('systemRepeatJumpValue','beforeRepeatJumpValue','defaultRepeatJumpValue')"></ui:switch></td>
						</tr>
						
						<tr>
							<td width=100% colspan="6">
								<c:if test="${!isLangSuportEnabled }">
									 <c:choose>
										<c:when test="${sysAppConfigForm.map.defaultUsageContent_repeatJump == '&nbsp;'}">
											<textarea name="value(defaultUsageContent_repeatJump)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
										</c:when>
										<c:otherwise>
											<xform:textarea property="value(defaultUsageContent_repeatJump)" style="width:100%;height:80px" validators="maxLength(4000)"/>
										</c:otherwise>
									</c:choose>
								</c:if>
								<c:if test="${isLangSuportEnabled }">
									<xlang:lbpmlangAreaNew property="defaultUsageContent_repeatJump" alias="value(defaultUsageContent_repeatJump)" validators="maxLength(4000)" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_repeatJump_lang}"/>
								</c:if>
								<html:hidden property="value(defaultUsageContent_repeatJump_lang)" />
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
							<td width=20%><ui:switch property="value(defaultTimeOutValue)" id="defaultTimeOutValue" onValueChange="switchTimeOut('defaultTimeOutValue','systemTimeOutValue')"></ui:switch></td>
							<td width=30% class="td_normal_title">使用系统自动生成的意见</td>
							<td width=20%><ui:switch property="value(systemTimeOutValue)" id="systemTimeOutValue" onValueChange="switchTimeOut('systemTimeOutValue','defaultTimeOutValue')"></ui:switch></td>
						</tr>
						
						<tr>
							<td width=100% colspan="4">
								<c:if test="${!isLangSuportEnabled }">
									 <c:choose>
										<c:when test="${sysAppConfigForm.map.defaultUsageContent_timeOut == '&nbsp;'}">
											<textarea name="value(defaultUsageContent_timeOut)" validate="maxLength(4000)" style="width:100%;height:80px" ></textarea>
										</c:when>
										<c:otherwise>
											<xform:textarea property="value(defaultUsageContent_timeOut)" style="width:100%;height:80px" validators="maxLength(4000)"/>
										</c:otherwise>
									</c:choose>
								</c:if>
								<c:if test="${isLangSuportEnabled }">
									<xlang:lbpmlangAreaNew property="defaultUsageContent_timeOut" alias="value(defaultUsageContent_timeOut)" validators="maxLength(4000)" style="width:100%;height:80px" langs="${sysAppConfigForm.map.defaultUsageContent_timeOut_lang}"/>
								</c:if>
								<html:hidden property="value(defaultUsageContent_timeOut_lang)" />
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
		</table>
		</div>
		</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.sys.lbpmservice.support.model.LbpmUsageContent" />
			
			<center style="margin-top: 10px;">
			<!-- 保存 -->
			<ui:button text="${lfn:message('button.save')}" style="position: fixed;bottom:0px;left: 15px;width:95%;background: #fff;" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');" order="1" ></ui:button>
			</center>
		</html:form>
		<script type="text/javascript" src="${LUI_ContextPath}/resource/js/data.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript">
	 		$KMSSValidation();
	 		function writeFormField(){
	 			var form = document.sysAppConfigForm;
	 			for(var i=0;i<form.length;i++){
	 				if(form[i].value == ""){
	 					form[i].value = "&nbsp;";
	 				}
	 			}
	 			return true;
	 		}
	 		Com_Parameter.event["submit"].push(writeFormField);

//==============多语言增加==================//
Com_IncludeFile("json2.js");
var langJson = <%=MultiLangTextareaGroupTag.getLangsJsonStr()%>;
var isLangSuportEnabled = <%=MultiLangTextareaGroupTag.isLangSuportEnabled()%>;

Com_Parameter.event["submit"].push(function(){
	 handleLangByElName("defaultUsageContent","value(defaultUsageContent_lang)");
	 handleLangByElName("defaultUsageContent_refuse","value(defaultUsageContent_refuse_lang)");
	 handleLangByElName("defaultUsageContent_commission","value(defaultUsageContent_commission_lang)");
	 handleLangByElName("defaultUsageContent_communicate","value(defaultUsageContent_communicate_lang)");
	 handleLangByElName("defaultUsageContent_abandon","value(defaultUsageContent_abandon_lang)");
	 handleLangByElName("defaultUsageContent_superRefuse","value(defaultUsageContent_superRefuse_lang)");
	 handleLangByElName("defaultUsageContent_sign","value(defaultUsageContent_sign_lang)");
	 handleLangByElName("defaultUsageContent_additionSign","value(defaultUsageContent_additionSign_lang)");
	 handleLangByElName("defaultUsageContent_assign","value(defaultUsageContent_assign_lang)");
	 handleLangByElName("defaultUsageContent_assignPass","value(defaultUsageContent_assignPass_lang)");
	 handleLangByElName("defaultUsageContent_assignRefuse","value(defaultUsageContent_assignRefuse_lang)");
	 handleLangByElName("defaultUsageContent_jump","value(defaultUsageContent_jump_lang)");
	 handleLangByElName("defaultUsageContent_nodeSuspend","value(defaultUsageContent_nodeSuspend_lang)");
	 handleLangByElName("defaultUsageContent_nodeResume","value(defaultUsageContent_nodeResume_lang)");
	 handleLangByElName("defaultUsageContent_timeOut","value(defaultUsageContent_timeOut_lang)");
	 handleLangByElName("defaultUsageContent_repeatJump","value(defaultUsageContent_repeatJump_lang)");
	 return true;
});

function handleLangByElName(eleName,elJsonName){
	//[{lang:"zh-CN","value":""},{lang:"en-US","value":""}]
	var elLang=[];
	if(!isLangSuportEnabled){
		return elLang;
	}
	var fdValue = document.getElementsByName("value("+eleName+")")[0].value;
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
// 开启校验开关时必须保证必填开关已经开启(全局的校验暂时关闭)
function switchValidate(op1,op2){
	/* if(!(LUI(op2).checkbox.prop('checked'))){
		if(LUI(op1).checkbox.prop('checked')){
			LUI(op1).checkbox.prop('checked',false);
			$("input[name='value("+op1+")']").val("false");
		}
	} */
		
}

function closeValidate(op1,op2){
	if(LUI(op1).checkbox.prop('checked')){
		if(!(LUI(op2).checkbox.prop('checked'))){
			seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
                dialog.alert("${lfn:message('sys-lbpmservice-support:lbpmUsageContent.fdDescription.details.4')}");
          });
			LUI(op1).checkbox.prop('checked',false);
			$("input[name='value("+op1+")']").val("false");
		}
	}
}

function switchDefaultRepeatJump(op,op1,op2){
	if(LUI(op).checkbox.prop('checked')){
		LUI(op1).checkbox.prop('checked',false);
		$("input[name='value("+op1+")']").val("false");
		LUI(op2).checkbox.prop('checked',false);
		$("input[name='value("+op2+")']").val("false");
	}
}

function switchTimeOut(op,op1){
	if(LUI(op).checkbox.prop('checked')){
		LUI(op1).checkbox.prop('checked',false);
		$("input[name='value("+op1+")']").val("false");
	}
}

$(document).ready(function(){ 
	
	 
});

	
	
	 	</script>
	</template:replace>
</template:include>
