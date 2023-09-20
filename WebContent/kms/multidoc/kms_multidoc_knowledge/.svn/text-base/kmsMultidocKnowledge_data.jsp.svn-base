<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmsMultidocKnowledge" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<%-- 文档标题 --%>
		<list:data-column style="width:35%;text-align:center" property="docSubject" title="${ lfn:message('kms-multidoc:kmsMultidoc.kmsMultidocKnowledge.docSubject') }">
		</list:data-column>
		<%-- 录入者 --%>
		<list:data-column property="docCreator.fdName" title="${ lfn:message('kms-multidoc:kmsMultidoc.inputUser') }">
		</list:data-column>
		<%-- 所属部门 --%>
		<list:data-column col="docDept.fdName" title="${ lfn:message('kms-multidoc:kmsMultidoc.form.main.docDeptId') }">
			<c:out value="${kmsMultidocKnowledge.docCreator.fdParent.fdName}" />
		</list:data-column>
		<%-- 文档状态 --%>
		<list:data-column col="docStatus" title="${ lfn:message('kms-multidoc:kmsMultidoc.kmsMultidocKnowledge.docStatus') }">
			<sunbor:enumsShow value="${kmsMultidocKnowledge.docStatus}" enumsType="kms_doc_status" />
		</list:data-column>
		<%-- 录入时间 --%>
		<list:data-column col="docCreateTime"  title="${ lfn:message('kms-multidoc:kmsMultidocKnowledge.docCreateTime') }">
			<kmss:showDate value="${kmsMultidocKnowledge.docCreateTime}" type="date"></kmss:showDate>
		</list:data-column>
	</list:data-columns>
	<list:data-paging page="${queryPage}" />
</list:data>