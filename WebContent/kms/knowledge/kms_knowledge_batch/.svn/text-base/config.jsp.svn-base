<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page
	import="com.landray.kmss.kms.knowledge.batch.util.KmsKnowledgeBatchType"%>
<%@page import="java.util.List"%>
<%@page
	import="com.landray.kmss.kms.knowledge.batch.util.KmsKnowledgeBatchUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit">
	<template:replace name="content">

		<h2 align="center" style="margin: 10px 0">
			<span style="color: #35a1d0;">${lfn:message("kms-knowledge-batch:kmsKnowledgeBatch.title") }</span>
		</h2>

		<html:form
			action="/kms/knowledge/kms_knowledge_batch/kmsKnowledgeBatchLog.do">

			<center>
				<table class="tb_normal" width=95%>
					<tr>
						<td class="td_normal_title" width=15%>${lfn:message("kms-knowledge-batch:kmsKnowledgeBatchLog.fdType") }</td>
						<td>
							<%
								List<KmsKnowledgeBatchType> types = KmsKnowledgeBatchUtil.getTypes();

											for (KmsKnowledgeBatchType type : types) {
												if (type.getService().enable()) {
							%><label style="padding: 4px; background-color: #F7F7D7;"><input
								type="radio" name="configForm.fdType"
								value="<%=type.getUnid()%>" /><%=type.getTitle()%> <%
 	if (StringUtil.isNotNull(type.getJsp())) {
 %> <a href="javascript:;" style="color: blue"
								onclick="onConfig('<%=type.getJsp()%>')">${lfn:message("kms-knowledge-batch:kmsKnowledgeBatch.config.type") }</a>
								<%
									}
								%></label> &nbsp; <%
 	}
 				}
 %>
						</td>
					</tr>

					<tr>
						<td class="td_normal_title" width="15%">${lfn:message("kms-knowledge-batch:kmsKnowledgeBatchLog.fdUrl") }</td>
						<td><xform:text property="configForm.fdUrl" showStatus="edit"
								required="true" style="width:90%" /></td>
					</tr>

					<tr>
						<td class="td_normal_title" width="15%">${lfn:message("kms-knowledge-batch:kmsKnowledgeBatchLog.fdModelName") }</td>
						<td><xform:radio property="configForm.fdModelName"
								showStatus="edit" onValueChange="changeModule"
								required="true">
								<xform:customizeDataSource
									className="com.landray.kmss.kms.knowledge.batch.service.spring.KmsKnowledgeBatchModuleServiceImp" />
							</xform:radio></td>
					</tr>

					<tr>
						<td class="td_normal_title" width="15%">${lfn:message("kms-knowledge-batch:kmsKnowledgeBatchLog.fdCategory") }</td>
						<td><xform:dialog propertyId="configForm.fdCategoryId"
								propertyName="configForm.fdCategoryName" showStatus="edit"
								style="width:90%;" required="true">
	                                selectCategory();
	                            </xform:dialog></td>
					</tr>
					<tr>
						<td colspan="2" style="color: red">${lfn:message("kms-knowledge-batch:kmsKnowledgeBatch.config.tip") }</td>
					</tr>
				</table>
			</center>

			<center style="margin-top: 10px;">
				<ui:button
					text="${lfn:message('kms-knowledge-batch:kmsKnowledgeBatch.submit') }"
					height="35" width="120" onclick="submit()"></ui:button>
			</center>

		</html:form>

		<script type="text/javascript"
			src="${LUI_ContextPath }/kms/knowledge/kms_knowledge_batch/js/index.js">
			
		</script>
	</template:replace>

</template:include>
