<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>

<%@ page
	import="com.landray.kmss.kms.category.model.KmsCategoryConfig"%>

<%
	String docSecCateIds = request.getParameter("docSecondCategoriesIds");
	String [] ids = docSecCateIds.split(";");
	
	String docSecCateNames = request.getParameter("docSecondCategoriesNames");
	String [] names = docSecCateNames.split(";");
	
	String kmsCategoryIds = request.getParameter("kmsCategoryIds");
	String [] categoryIds = kmsCategoryIds.split(";");
	
	String kmsCategoryNames = request.getParameter("kmsCategoryNames");
	String [] categoryNames = kmsCategoryNames.split(";");
%>

<div class="muiMultidocSecCateBox" style="overflow: hidden;">
	<%
		KmsCategoryConfig kmsCategoryConfig = new KmsCategoryConfig();
		String kmsCategoryEnabled = (String) kmsCategoryConfig.getDataMap().get("kmsCategoryEnabled");
		if ("true".equals(kmsCategoryEnabled)) {
	%>
			<p style="margin-bottom: 1rem;">${ lfn:message('kms-multidoc:kmsMultidocTemplate.docCategory') }
				<span style="margin-left: 180px;">
					<c:out value="${kmsMultidocKnowledgeForm.docCategoryName}"></c:out>
				</span>
			</p><ul style="margin-bottom: 1rem">
<%-- 			<span class="muiMultidocCate muiAuthor">
				<c:out value="${docCategoryName}"></c:out>
			</span> --%>
	<!-- 知识分类信息 -->
	<%  if(StringUtil.isNull(categoryNames[0])||categoryIds.length==0){ %>
		<%--<span class="kmsCategory muiAuthor">${ lfn:message('kms-multidoc:kmsMultidocKnowledge.notSet') }</span>--%>
	<%} %>
	<%
		for(int i=0;i<categoryIds.length;i++){
	%>
		<span class="kmsCategory muiAuthor" value="<%=categoryIds[i] %>">
	<%
			if(StringUtil.isNotNull(categoryNames[i])){
	%>
			<%if(i==0){ %>
			<%=categoryNames[i] %>
			<%}else{ %>
				<%=";"+categoryNames[i] %>
	<%
			}}
	%>
		</span>
	<%
		}}else{
	%>
			<p style="margin-bottom: 1rem;">${ lfn:message('kms-multidoc:kmsMultidocTemplate.docCategory') }
				<span style="margin-left: 180px;">
					<c:out value="${kmsMultidocKnowledgeForm.docCategoryName}"></c:out>
				</span>

			</p><ul style="margin-bottom: 1rem">
<%-- 			<span class="muiMultidocCate muiAuthor">
				<c:out value="${docCategoryName}"></c:out>
			</span> --%>
	<%  if(StringUtil.isNull(names[0])||names.length==0){ %>
		<%--<span class="kmsCategory muiAuthor">${ lfn:message('kms-multidoc:kmsMultidocKnowledge.notSet') }</span>--%>
	<%} %>
	<%
		for(int i=0;i<names.length;i++){
	%>
		<span  value="<%=ids[i] %>">
	<%
			if(StringUtil.isNotNull(names[i])){ 
	%>
			<%if(i==0){ %>	
				<%=names[i] %>
			<%}else{ %>
				<%=";"+names[i] %>
	<%
			}}
	%>
		</span>
	<%
		}}
	%>	
	</ul>
	
</div>

<script>
	require(["mui/device/adapter", "mui/util", "dojo/on", "dojo/query" ], function(adapter, util, on, query) {
		on(query(".muiMultidocCate"), "click", function() {
			var queryStr = "orderby=docPublishTime&ordertype=down&categoryId=${kmsMultidocKnowledgeForm.docCategoryId}&q.docStatus=30";
			var url = "/kms/knowledge/mobile/index.jsp?queryStr=" + encodeURIComponent(queryStr)+"#path=0&query=q.docCategory%3A${kmsMultidocKnowledgeForm.docCategoryId}";
			adapter.open(util.formatUrl(url), "_self");
		});
		
		on(query(".muiMultidocSecCate"), "click", function(e) {
			var value = this.getAttribute("value");
			var text = this.innerText.replace(";","");
			if(value){
				var queryStr = "orderby=docPublishTime&ordertype=down&categoryId="+value+"&q.docStatus=30";
				var url = "/kms/knowledge/mobile/index.jsp?"+"queryStr=" + encodeURIComponent(queryStr)+"#path=0&query=q.docCategory%3A"+value;
				adapter.open(util.formatUrl(url), "_self");
			}
		});
		
		on(query(".kmsCategory"), "click", function(e) {
			var value = this.getAttribute("value");
			var text = this.innerText.replace(";","");
			if(value){
				var queryStr = "orderby=docPublishTime&ordertype=down&q.kmsCategoryKnowledgeRels="+value+"&q.docStatus=30";
				var url = "/kms/knowledge/mobile/index.jsp?moduleName="+text+"&filter=1&queryStr=" + encodeURIComponent(queryStr);
				adapter.open(util.formatUrl(url), "_self");
			}
		});
				
	})
</script>