<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%@ page
	import="com.landray.kmss.kms.category.model.KmsCategoryConfig"%>

<list:col-checkbox name="List_Selected" style="width:5%"></list:col-checkbox>
<list:col-serial title="${ lfn:message('page.serial') }" headerStyle="width:5%"></list:col-serial>
<list:col-html title="${ lfn:message('kms-multidoc:kmsMultidoc.kmsMultidocKnowledge.docSubject')}" headerStyle="width:40%" style="text-align:left;padding:0 8px">
	{$
		{%row['icon']%}
		<span class="com_subject">{%row['docSubject']%}</span>
	$}
</list:col-html>
<list:col-html title="${ lfn:message('kms-multidoc:kmsMultidoc.docAuthor')}" >
	{$
		<div class="lui_multi_author_wrap"><span class="com_author">{%row['_docAuthor.fdName']%}</span></div> 
	$}
</list:col-html> 
<list:col-auto props="docPublishTime;fdTotalCount;docIntrCount;docEvalCount;docScore"></list:col-auto>
<list:col-html title="${ lfn:message('kms-multidoc:kmsMultidocKnowledge.fdTemplateName.categoryTrue') }"
			   style="width:15%" >
   		{$
			<span title="{%row['docCategory.fdName']%}">
				{% strutil.textEllipsis(row['docCategory.fdName'], 15) %}
			</span>
		$}	
 </list:col-html>
 <c:if test="${isBorrowOpen}">
 <list:col-html title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.readStatus') }" >
	{$
		<div class="lui_multi_author_wrap"><span class="com_author">{%row['docBorrowFlagName']%}</span></div> 
	$}
</list:col-html> 
 </c:if>