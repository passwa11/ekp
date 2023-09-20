<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<center>
<table class="tb_normal" width=100% id="TABLE_DocList">
	 		<tr>	
				<td class="td_normal_title" width="10%">
					<bean:message bundle="kms-knowledge" key="kmsKnowledgeWikiCatalog.fdOrder"/>
				</td> 
				<td class="td_normal_title" width="25%">
					<bean:message bundle="kms-knowledge" key="kmsKnowledgeWikiCatalog.fdName"/>
				</td>
				<td class="td_normal_title" width="25%">
					<bean:message bundle="kms-knowledge" key="kmsKnowledgeWikiCatalog.fdParentName"/>
				</td>
				<td class="td_normal_title" width="35%">
					<bean:message bundle="kms-knowledge" key="kmsKnowledgeWikiCatalog.authTmpEditors"/>
				</td>
			</tr>
			<%---内容行--%>
			<c:forEach items="${kmsKnowledgeWikiTemplateForm.fdCatelogList}" var="kmsKnowledgeWikiCatalogForm" varStatus="vstatus">
			<tr KMSS_IsContentRow="1">
				<td><c:out value='${kmsKnowledgeWikiCatalogForm.fdOrder}'/></td>
				<td>
					<c:out value='${kmsKnowledgeWikiCatalogForm.fdName}'/>
				</td>
				<td>
					<c:out value='${kmsKnowledgeWikiCatalogForm.fdParentName}'/>
				</td>
				<td>
					<c:out value='${kmsKnowledgeWikiCatalogForm.authTmpEditorNames}'/>
				</td>
			</tr>
			</c:forEach>
</table>
</center>