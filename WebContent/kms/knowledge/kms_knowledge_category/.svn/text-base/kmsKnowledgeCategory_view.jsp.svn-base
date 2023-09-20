<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@ include file="/resource/jsp/view_top.jsp"%>

<%@ page
	import="com.landray.kmss.kms.category.model.KmsCategoryConfig"%>
<c:set var="kmsCategoryEnabled" value="false"></c:set>	
<c:set var="menuKmdocCategoryconfig" value="${lfn:message('kms-knowledge:menu.kmdoc.categoryconfig') }"></c:set>	
<%
	KmsCategoryConfig kmsCategoryConfig = new KmsCategoryConfig();
	String kmsCategoryEnabled = (String) kmsCategoryConfig.getDataMap().get("kmsCategoryEnabled");
	if ("true".equals(kmsCategoryEnabled)) {
%>
	<c:set var="kmsCategoryEnabled" value="true"></c:set>	
	<c:set var="menuKmdocCategoryconfig" value="${lfn:message('kms-knowledge:menu.kmdoc.categoryconfig.categoryTrue') }"></c:set>
<%
	}
%>
	
<%--参数--%>
<c:set var="formName" value="kmsKnowledgeCategoryForm" />
<c:set var="fdId" value="${kmsKnowledgeCategoryForm.fdId}" />
<c:set var="fdModelName" value="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory" />
<script language="JavaScript">
	Com_IncludeFile("dialog.js");
</script>
	<%--标题--%>
	<kmss:windowTitle
		subjectKey="kms-knowledge:menu.kmdoc.categoryconfig"
		moduleKey="kms-knowledge:module.kms.knowledge" /> 
	<%--按钮 ---%>
	<div id="optBarDiv">
		<kmss:auth
			requestURL="/kms/knowledge/kms_knowledge_category/kmsKnowledgeCategory.do?method=edit&fdId=${param.fdId}"
			requestMethod="GET">
			<input type="button" value="<bean:message key="button.edit"/>"
			onclick="Com_OpenWindow('${KMSS_Parameter_ContextPath}kms/knowledge/kms_knowledge_category/kmsKnowledgeCategory.do?method=edit&fdId=${param.fdId}&fdModelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory','_self');">
		</kmss:auth> 
		<input type="button" value="<bean:message key="button.close"/>"
			    onclick="Com_CloseWindow();">
	</div>
	
	
	<p class="txttitle">${menuKmdocCategoryconfig }
	</p>

	<center>
	<table
		id="Label_Tabel"
		width="95%">
		<%--基本信息--%>	 
		<tr LKS_LabelName="<bean:message bundle="sys-simplecategory" key="table.sysSimpleCategory" />">
			<td>
				<table
					class="tb_normal"
					width=100%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message
								  bundle="sys-simplecategory" key="sysSimpleCategory.fdParentName" />
					    </td>
						<td colspan="3">
							<c:out value="${kmsKnowledgeCategoryForm.fdParentName}"/>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%><bean:message
								bundle="sys-simplecategory" key="sysSimpleCategory.fdName" /></td>
						<td colspan="3">
							<c:out value="${kmsKnowledgeCategoryForm.fdName}"/>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message  bundle="sys-property" key="table.sysPropertyTemplate"/>
						</td>
						<td colspan="3">
							<c:out value="${kmsKnowledgeCategoryForm.fdSysPropTemplateName}"/>
						</td>
					</tr>
					<%-- <tr>
						<td
							class="td_normal_title"
							width="15%"><bean:message
							bundle="kms-knowledge"
							key="kmsKnowledgeCategory.fdNumberPrefix" /></td>
						<td colspan=3>
							<c:out value="${kmsKnowledgeCategoryForm.fdNumberPrefix}"/>
						</td>
					 </tr> --%>
					 <tr>
						<td class="td_normal_title" width=15%>
								<bean:message
								bundle="kms-knowledge" key="kmsKnowledgeCategory.fdTemplateType" />
						</td>
						<td colspan="3">
							<xform:checkbox showStatus="view" property="fdTemplateType" >
								<xform:customizeDataSource 
									className="com.landray.kmss.kms.knowledge.service.spring.KmsKnowledgeTemplateService">
								</xform:customizeDataSource>
							</xform:checkbox>
						</td>
					</tr>
					<%if(ISysAuthConstant.IS_AREA_ENABLED){ %>
					<tr>
						<%-- 所属场所 --%>
						<td class="td_normal_title" width="15%">
							<bean:message key="sysAuthArea.authArea" bundle="sys-authorization" />
						</td>
						<td colspan="3">
						    <c:out value="${kmsKnowledgeCategoryForm.authAreaName}" />
						</td>
					</tr>
					<%} %>
					
					<tr>
						<td class="td_normal_title" width=15%><bean:message
							key="model.fdOrder" /></td>
						<td colspan="3">
							<c:out value="${kmsKnowledgeCategoryForm.fdOrder}"/>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%><bean:message
							key="model.tempEditorName" /></td>
						<td colspan="3">
							<xform:textarea property="authEditorNames" showStatus="view"/>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%><bean:message
							key="model.tempReaderName" /></td>
						<td colspan="3">
							 <xform:textarea property="authReaderNames" showStatus="view"/>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%><bean:message
							key="model.fdCreator" /></td>
						<td width=35%>
							<c:out value="${kmsKnowledgeCategoryForm.docCreatorName }"></c:out>
						</td>
						<td class="td_normal_title" width=15%><bean:message
							key="model.fdCreateTime" /></td>
						<td width=35%>
							<c:out value="${kmsKnowledgeCategoryForm.docCreateTime}"></c:out>
						</td>			
					</tr>
				</table>
			</td>
		</tr>
		<%--基本信息--%>	 
		<tr LKS_LabelName="${lfn:message('kms-knowledge:kmsKnowledgeCategory.edit.docTemplate')}" > 
			<td>
			<table
				class="tb_normal"
				width=100%>
				<c:if test="${kms_professional}">
					<tr >
						<td class="td_normal_title" width=15%>
							<bean:message bundle="kms-knowledge" key="kmsKnowledgeCategory.docTemplate"/>
						</td>
						<td colspan="3">
							<c:out value="${kmsKnowledgeCategoryForm.docTemplateName }"/>
						</td>
					</tr>
					<tr >
						<td class="td_normal_title" width=15%>
							<bean:message bundle="kms-knowledge" key="kmsKnowledgeCategory.wikiTemplate"/>
						</td>
						<td colspan="3">
							<c:out value="${kmsKnowledgeCategoryForm.wikiTemplateName }"/>
						</td>
					</tr>
				</c:if>
				<!-- 标签机制 -->
				<c:import url="/sys/tag/include/sysTagTemplate_view.jsp"
					charEncoding="UTF-8">
					<c:param name="formName" value="kmsKnowledgeCategoryForm" />
					<c:param name="fdKey" value="mainDoc" /> 
					<c:param name="diyTitle" value="默认关键字" /> 
				</c:import>
			</table>
			</td>
		</tr>
		
				
		<%--流程机制--%>
		<c:import url="/sys/workflow/include/sysWfTemplate_view.jsp"
		charEncoding="UTF-8">
			<c:param name="formName" value="kmsKnowledgeCategoryForm" />
			<c:param name="fdKey" value="mainDoc" />
		</c:import> 
		<!-- 纠错流程设置  -->
		<c:import url="/sys/workflow/include/sysWfTemplate_view.jsp"
			charEncoding="UTF-8">  
			<c:param name="formName" value="kmsKnowledgeCategoryForm" />
			<c:param name="fdKey" value="knowledgeErrorCorrectionFlow" /> 
			<c:param name="diyTitle" value="设置" /> 
			<c:param name="messageKey"  value="kms-knowledge:kmsKnowledgeCategory.kmsCommonDocErrorCorrectionFlow" /> 
		</c:import>
		
		
		<!-- 推荐流程设置  -->
		<c:import url="/sys/workflow/include/sysWfTemplate_view.jsp"
			charEncoding="UTF-8">
			<c:param name="formName" value="kmsKnowledgeCategoryForm" />
			<c:param name="fdKey" value="introDoc" /> 
			<c:param name="messageKey"  value="kms-knowledge:kmsKnowledgeCategory.introFlow" />
		</c:import>
		<%----发布机制--%>
		<c:import url="/sys/news/include/sysNewsPublishCategory_view.jsp"
			charEncoding="UTF-8">
			<c:param name="formName" value="KmsKnowledgeCategoryForm" />
			<c:param name="fdKey" value="mainDoc" />
		</c:import>
		<%----权限--%>
		<tr LKS_LabelName="<bean:message bundle="sys-right" key="right.moduleName" />">
		<td>
			<table
				class="tb_normal"
				width=100%>
				<c:import
					url="/sys/right/tmp_right_view.jsp"
					charEncoding="UTF-8">
					<c:param
						name="formName"
						value="kmsKnowledgeCategoryForm" />
					<c:param
						name="moduleModelName"
						value="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory" />
				</c:import>
			</table>
		</td>
		</tr>
        <!-- 规则机制 -->
        <c:if test="${kms_professional}">
            <c:set var="knowledgeMsg" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.knowledge.msg1') }"></c:set>
            <c:if test="${borrowEnabled}">
                <c:set var="knowledgeMsg" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.knowledge.msg1') };${lfn:message('kms-knowledge:kmsKnowledgeCategory.knowledge.msg3') }"></c:set>
            </c:if>
			<c:import url="/sys/rule/sys_ruleset_temp/sysRuleTemplate_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmsKnowledgeCategoryForm" />
				<c:param name="fdKey" value="mainDoc;knowledgeErrorCorrectionFlow;introDoc${borrowEnabled ? ';kmsKnowledgeCategoryBorrow' : ''}" />
				<c:param name="messageKey" value="${knowledgeMsg}" />
				<c:param name="templateModelName" value="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory"></c:param>
			</c:import>
		</c:if>
        <c:if test="${!kms_professional}">
            <c:import url="/sys/rule/sys_ruleset_temp/sysRuleTemplate_view.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="kmsKnowledgeCategoryForm" />
                <c:param name="fdKey" value="mainDoc;introDoc" />
                <c:param name="messageKey" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.knowledge.msg2') }" />
                <c:param name="templateModelName" value="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory"></c:param>
            </c:import>
        </c:if>
	</table>
	</center>
<%@ include file="/resource/jsp/view_down.jsp"%>
	