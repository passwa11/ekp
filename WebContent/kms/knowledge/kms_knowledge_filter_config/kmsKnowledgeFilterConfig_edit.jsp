<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit">
	<template:replace name="content">
		
		<h2 align="center" style="margin: 10px 0">
			<span  class="profile_config_title">${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.filter.settings')}</span>
		</h2>

		<html:form action="/kms/knowledge/kms_knowledge_filter_config/kmsKnowledgeFilterConfig.do">
			<div style="margin:0 auto">
				<table class="tb_normal" width=95%>
					<tr>
						<td width=15% class="td_normal_title">
							<bean:message key="kmsKnowledgeBaseDoc.docDeptId" bundle="kms-knowledge" />
						</td>
						<td width="35%">
							<label>
								<ui:switch 
									property="value(department)" 
									checked="false" 
									checkVal="1" 
									unCheckVal="0"
									onValueChange="config_verifytimes_chgEnabled();"
									enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
									disabledText="${lfn:message('sys-ui:ui.switch.disabled')}">
								</ui:switch>
							</label>
						</td>
						<td width=15% class="td_normal_title">
							<bean:message key="kmsKnowledgeBaseDoc.docDeptId.disable" bundle="kms-knowledge" />
						</td>
						<td width="35%">
							<label>
								<ui:switch 
									property="value(disableDepartment)" 
									checked="false" 
									checkVal="1" 
									unCheckVal="0"
									onValueChange="config_verifytimes_chgEnabled();"
									enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
									disabledText="${lfn:message('sys-ui:ui.switch.disabled')}">
								</ui:switch>
							</label>
						</td>
					</tr>
					
					
					<tr>
						<td width=15% class="td_normal_title">
							<bean:message key="kmsKnowledgeBaseDoc.list.docAuthor" bundle="kms-knowledge" />
						</td>
						<td width="35%">
							<label>
								<label>
									<ui:switch 
										property="value(author)" 
										checked="false" 
										checkVal="1" 
										unCheckVal="0"
										onValueChange="config_verifytimes_chgEnabled();"
										enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
										disabledText="${lfn:message('sys-ui:ui.switch.disabled')}">
									</ui:switch>
								</label>
							</label>
						</td>
						<td width=15% class="td_normal_title">
							<bean:message key="kmsKnowledgeCategory.docCreator" bundle="kms-knowledge" />
						</td>
						<td width="35%">
							<label>
								<label>
									<ui:switch 
										property="value(docCreator)" 
										checked="false" 
										checkVal="1" 
										unCheckVal="0"
										onValueChange="config_verifytimes_chgEnabled();"
										enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
										disabledText="${lfn:message('sys-ui:ui.switch.disabled')}">
									</ui:switch>
								</label>
							</label>
						</td>
					</tr>
					
					<tr>
						<td width=15% class="td_normal_title">
							<bean:message key="kmsKnowledgeBaseDoc.docPublishTime" bundle="kms-knowledge" />
						</td>
						<td width="35%">
							<label>
								<ui:switch 
										property="value(docPublishTime)" 
										checked="false" 
										checkVal="1" 
										unCheckVal="0"
										onValueChange="config_verifytimes_chgEnabled();"
										enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
										disabledText="${lfn:message('sys-ui:ui.switch.disabled')}">
								</ui:switch>
							</label>
						</td>
						<td width=15% class="td_normal_title">
							<bean:message key="kmsKnowledgeCategory.docCreateTime" bundle="kms-knowledge" />
						</td>
						<td width="35%">
							<label>
								<ui:switch 
										property="value(docCreateTime)" 
										checked="false" 
										checkVal="1" 
										unCheckVal="0"
										onValueChange="config_verifytimes_chgEnabled();"
										enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
										disabledText="${lfn:message('sys-ui:ui.switch.disabled')}">
								</ui:switch>
							</label>
						</td>
					</tr>
				</table>
				<center style="margin:10px 0;">
					<!-- 保存 -->
					<ui:button text="${lfn:message('button.save')}" height="35" width="120" 
								onclick="Com_Submit(document.sysAppConfigForm,'update');"></ui:button>
				</center>
			</div>
			<input type="hidden" name="modelName" value="com.landray.kmss.kms.knowledge.model.KmsKnowledgeFilterConfig" />
			<html:hidden property="method_GET"/>
		</html:form>
		
	</template:replace>
</template:include>



