<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.landray.kmss.util.DateUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<c:set var="kmsMultidocKnowledgeForm" value="${requestScope[param.formBeanName]}" />

<div style="text-align: left;">
	<div class="muiDocSubject">
		<c:out value="${ kmsMultidocKnowledgeForm.docSubject }" />
	</div>
	<div class="muiDocInfo">
		     <c:if
				test="${not empty kmsMultidocKnowledgeForm.docAuthorId }">
				<span onclick="authorShow()" style="color:#3395FA;" <c:if test="${kmsMultidocKnowledgeForm.fdDocAuthorList.size()>=3}">  class="muiDocAuthor" </c:if>>
					<c:out  value="${ kmsMultidocKnowledgeForm.docAuthorName }" />
				</span>
				<p class="pop" id="authorNames" ><c:out value="${ kmsMultidocKnowledgeForm.docAuthorName }" /></p>
			 </c:if> 
			 <c:if test="${ empty kmsMultidocKnowledgeForm.docAuthorId }">
				 <span  onclick="authorShow()" style="color: #3395FA;" <c:if test="${kmsMultidocKnowledgeForm.outerAuthor.length()>15}">  class="muiDocAuthor" </c:if>>
				 		<c:out value="${ kmsMultidocKnowledgeForm.outerAuthor }" />
				 </span>
				 <p class="pop" id="authorNames" ><c:out value="${ kmsMultidocKnowledgeForm.outerAuthor }" /></p>
			</c:if>
		<c:if test="${not empty publishTime }">
			<span class="muiTimeView"> 
				${publishTime}
			</span>
		</c:if>
		<c:if test="${kmsMultidocKnowledgeForm.docStatus >= '30' }">
			<i class="fontmuis muis-views muiViewsNum"></i>
			<span><c:out value="${ kmsMultidocKnowledgeForm.docReadCount }" /></span>
		</c:if>
	</div>
	
	<c:if
		test="${kmsMultidocKnowledgeForm.fdDescription!=null && kmsMultidocKnowledgeForm.fdDescription!='' }">
		<div class="muiDocSummary muiDocSummaryBottom">
			<div class="muiDocSummarySign muiSummaryView">${lfn:message('kms-multidoc:kmsMultidocKnowledge.fdDescription') }</div>
			<div class="muiSummaryMain"><c:out value="${ kmsMultidocKnowledgeForm.fdDescription }" /></div>
		</div>
	</c:if>
</div>
