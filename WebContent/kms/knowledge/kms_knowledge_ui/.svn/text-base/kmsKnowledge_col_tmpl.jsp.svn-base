<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%--抽取列表页模板，给个人中心用 --%>
<list:col-checkbox name="List_Selected" style="width:30px"></list:col-checkbox>
<list:col-serial title="${ lfn:message('page.serial') }" headerStyle="width:30px;"></list:col-serial>
<list:col-html title="${ lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docSubjects')}"  style="text-align:left;">
	{$
	<span class="textEllipsis">
		<span style="position: relative; top: -1px">{%row['icon']%}</span>
		<span class="com_subject" title="{%row['docSubject']%}">{%row['docSubject']%}</span>
		<span style="position: relative; float: right; margin-right: 15px;">{%row['docBorrowFlagName']%}</span>
	</span>
	$}
</list:col-html>

<list:col-html title="${ lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docAuthor')}" headerStyle="width:100px;" >
	{$
	<div class="lui_multi_author_wrap"><span>{%row['docAuthor.fdName']%}</span> </div>
	$}
</list:col-html>
<list:col-html title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docCategory') }" headerStyle="width:150px;">
	{$
	<span title="{%row['docCategory.fdName']%}"  class="textEllipsis">
		{%row['docCategory.fdName']%}
				<%--{% strutil.textEllipsis(row['docCategory.fdName'], 25) %}--%>
			</span>
	$}
</list:col-html>

<%--浏览--%>
<list:col-html title="${lfn:message('kms-knowledge:kmsKnowledge.read') }" headerStyle="width:50px;" >
	{$
	<span class="com_number">
			{%row['fdTotalCount']%}
	</span>
	$}
</list:col-html>
<%--推荐--%>
<list:col-html title="${lfn:message('kms-knowledge:kmsKnowledge.intrCount') }" headerStyle="width:50px;" >
	{$
	<span class="com_number">
			{%row['docIntrCount']%}
		</span>
	$}
</list:col-html>
<%--点评--%>
<list:col-html title="${lfn:message('kms-knowledge:kmsKnowledge.evalCount') }" headerStyle="width:50px;"  >
	{$
	<span class="com_number">
			{%row['docEvalCount']%}
		</span>
	$}
</list:col-html>
<%--评分--%>
<list:col-html title="${lfn:message('kms-knowledge:kmsKnowledge.score') }" headerStyle="width:50px;" >
	{$
	<span class="com_number">
			{%row['docScore']%}
		</span>
	$}
</list:col-html>

<%--发布日期--%>
<list:col-html title="${lfn:message('kms-knowledge:kmsKnowledge.docPublishTime') }" headerStyle="width:100px;" >
	{$
	<span title="{%row['docCategory.fdName']%}">
				{%row['docPublishTime']%}
			</span>
	$}
</list:col-html>

