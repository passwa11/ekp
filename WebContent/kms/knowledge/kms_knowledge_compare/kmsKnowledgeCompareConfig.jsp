<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title">
				<bean:message bundle="kms-knowledge" key="kmsKnowledgeCompare.tree.menu" />
			</span>
		</h2>
		
		<html:form action="/kms/knowledge/kms_knowledge_compare/kmsKnowledgeCompareConfig.do">
			<center>
				<table class="tb_normal" width=95%>
					
					<tr>
					
					  <td class="td_normal_title" width=15%>
						  <bean:message bundle="kms-knowledge" key="kmsKnowledgeCompare.isuse" />
					  </td>
					  <td colspan="3">
					  		<ui:switch property="value(kms.knowledge.compare.enabled)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
					  </td>
					</tr>					
					<!-- 文本比对设置 -->
					<tr>
					  <td class="td_normal_title" width="15%">
						  <bean:message bundle="kms-knowledge" key="kmsKnowledgeCompare.provider" />
					  </td>
					  <td colspan="3">		
						<xform:radio property="value(kms.knowledge.compare.provider)"
										htmlElementProperties="id='_fdCompareProviders'"
										showStatus="edit">
							<xform:customizeDataSource
								className="com.landray.kmss.kms.knowledge.service.spring.KmsKnowledgeCompareProvider"></xform:customizeDataSource>
						</xform:radio>
					  </td>
					</tr>
				</table>
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.kms.knowledge.model.kmsKnowledgeCompare" />
			
			<center style="margin-top: 10px;">
			<!-- 保存 -->
			<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');"></ui:button>
			</center>
		</html:form>
		
	 	<script type="text/javascript">
	 		var validation = $KMSSValidation();
	 	</script>
	</template:replace>
</template:include>
