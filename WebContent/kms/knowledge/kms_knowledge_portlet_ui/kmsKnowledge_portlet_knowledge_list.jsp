<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/kms/knowledge/kms_knowledge_ui/kmsKnowledge_categoryId_handle.jsp"%> 
<%@ page language="java"  import="com.landray.kmss.kms.knowledge.util.KmsKnowledgeUtil" %>
<%@ page language="java"  import="java.util.Map" %>
<%@ page language="java"  import="com.landray.kmss.kms.knowledge.service.IKmsKnowledgeCategoryService"%>
<%@ page language="java"  import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page language="java"  import="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory"%>
<% 
	Map<String , String> configMap = KmsKnowledgeUtil.getFilterConfig();
	pageContext.setAttribute("configMap", configMap);
%>

<template:include ref="default.simple4list">
	<template:replace name="title">${lfn:message('kms-knowledge:module.kms.knowledge') }</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/kms/knowledge/kms_knowledge_ui/style/index.css">
	<style>
		.lui_listview_columntable_table input[type='checkbox'] {
	   
	    display: none;
	}
	.lui_list_operation {
	    background-color: #e3f4fe;
	    border-top: 1px #b9d4e7 solid;
	    border-bottom: 1px #b9d4e7 solid;
	    padding: 0px 10px;
	    height: 40px;
	    line-height: 40px;
	}
	
	
	</style>
	<%-- 当前路径 --%>
		</template:replace>
		<%-- 当前路径 --%>
	<template:replace name="path">
		<ui:combin ref="menu.path.simplecategory">
			<ui:varParams 
				id="simplecategoryId"
				modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory" 
				moduleTitle="${lfn:message('kms-knowledge:module.kms.knowledge') }"
				href="/kms/knowledge/"
				categoryId="${param.categoryId }" />
		</ui:combin>
	</template:replace>
	<template:replace name="content">
		<c:choose>
			<c:when test="${not empty param.orderBy  }">
				<c:set var="fdOrderBy" value="kmsKnowledgeBaseDoc.${param.orderBy}"/>
			</c:when>
			<c:otherwise>
				<c:set var="fdOrderBy" value="kmsKnowledgeBaseDoc.docPublishTime"/> 
			</c:otherwise>
		</c:choose>
		<list:criteria id="criteria1">
			<list:cri-ref key="docSubject" ref="criterion.sys.docSubject">
			</list:cri-ref>
			<list:cri-property modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory" 
						categoryId="${param.categoryId }"/>
	        <c:if test="${configMap.department != '0' }">
				<list:cri-auto modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc" 
					property="docDept"/>
		    </c:if>
		    <c:if test="${configMap.disableDepartment == '1' }">
				<list:cri-ref key="docDept" ref="criterion.sys.dep.disable" 
						title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docDeptId.disable') }">
				</list:cri-ref>
		    </c:if>
			<c:if test="${configMap.author != '0' }">
				<list:cri-ref key="fdDocAuthorList"  ref="criterion.sys.person"  multi="false" expand="true"
				        title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.list.docAuthor') }">
				</list:cri-ref>
				
				<list:cri-auto modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc" property="outerAuthor"/>
			</c:if>
			<c:if test="${configMap.docCreator == '1' }">
				<list:cri-auto modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc" 
					property="docCreator" expand="true"/>
			</c:if>

			
			
		</list:criteria>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		<%-- 按钮 --%>
		<div class="lui_list_operation">
				<div class="lui_list_operation_order_text"> 
					${lfn:message('kms-knowledge:kmsKnowledge.list.orderType')}：
				</div>
				<%--排序按钮  --%>
				<div  class="lui_list_operation_order_btn">
						<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" > 
							<list:sortgroup>
							<c:choose>
								<c:when test="${ param.orderBy=='fdTotalCount'}">
									 <list:sort property="kmsKnowledgeBaseDoc.docPublishTime" 
										   text="${lfn:message('kms-knowledge:kmsKnowledge.docPublishTime') }" 
										   group="sort.list" />
									<list:sort property="kmsKnowledgeBaseDoc.fdTotalCount" 
										   text="${lfn:message('kms-knowledge:kmsKnowledge.readCount') }" 
										   group="sort.list" value="down"/>	
									<list:sort property="kmsKnowledgeBaseDoc.docIntrCount" 
										   text="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docIntrCount.num') }" 
										   group="sort.list" />	
									<list:sort property="kmsKnowledgeBaseDoc.docEvalCount" 
										   text="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docEvalCount.num') }" 
										   group="sort.list" />	
									<list:sort property="kmsKnowledgeBaseDoc.docScore" 
										   text="${lfn:message('kms-knowledge:kmsKnowledge.score') }" 
										   group="sort.list" />	
								</c:when>
								<c:otherwise>

									<list:sort property="kmsKnowledgeBaseDoc.fdTotalCount" 
										   text="${lfn:message('kms-knowledge:kmsKnowledge.readCount') }" 
										   group="sort.list"/>	
									<list:sort property="kmsKnowledgeBaseDoc.docIntrCount" 
										   text="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docIntrCount.num') }" 
										   group="sort.list" />	
									<list:sort property="kmsKnowledgeBaseDoc.docEvalCount" 
										   text="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docEvalCount.num') }" 
										   group="sort.list" />	
									<list:sort property="kmsKnowledgeBaseDoc.docScore" 
										   text="${lfn:message('kms-knowledge:kmsKnowledge.score') }" 
										   group="sort.list" />	
								</c:otherwise>
							</c:choose>
							</list:sortgroup>	
						
						</ui:toolbar>
				</div>
				<div  class="lui_list_operation_page_top">
					<list:paging layout="sys.ui.paging.top" > 		
					</list:paging>
				</div>

		</div>
	
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDocIndex.do?method=listChildren&categoryId=${param.categoryId }&orderby=${fdOrderBy}&ordertype=down'}
			</ui:source>
		
			<%-- 列表视图--%>
			<list:colTable layout="sys.ui.listview.columntable" name="columntable"
				rowHref="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=view&fdId=!{fdId}&fdKnowledgeType=!{fdKnowledgeType}">
				<%@ include file="/kms/knowledge/kms_knowledge_ui/kmsKnowledge_col_tmpl.jsp"%>
			</list:colTable>
			
		</list:listview>
		<%-- 列表分页 --%>
	 	<list:paging></list:paging>
	 	</template:replace>
</template:include>
	 	