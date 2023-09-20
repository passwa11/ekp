<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="model" list="${queryPage.list }" varIndex="status">
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		<%--序号--%>
		<list:data-column col="index">${status+1 }</list:data-column>
		
		<list:data-column property="fdCreateTime" title="${ lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthDetail.fdCreateTime') }" style="text-align:center;width:100px" />
		<list:data-column col="fdAuthorizerName" title="${ lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthDetail.fdAuthorizer') }" style="text-align:center;width:100px" >
			<c:if test="${not empty model.fdAuthorizer}">
				<c:out value="${model.fdAuthorizer.fdName}" />
			</c:if>
		</list:data-column>
		<list:data-column col="fdAuthorizedPersonName" title="${ lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.fdAuthorizedPerson') }" style="text-align:center;width:90px">
			<c:if test="${not empty model.fdAuthorizedPerson}">
				<c:out value="${model.fdAuthorizedPerson.fdName}" />
			</c:if>
		</list:data-column>
		<list:data-column col="fdAuthorizedPost" title="${ lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.fdAuthorizedPost') }" style="text-align:center;width:130px">
			<c:if test="${not empty model.fdAuthorizedPost}">
				<c:out value="${model.fdAuthorizedPost.fdName}" />
			</c:if>
		</list:data-column>
		<list:data-column property="fdStartTime" title="${ lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.fdStartTime') }" style="text-align:center;width:90px" />
		<list:data-column property="fdEndTime" title="${ lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.fdEndTime') }" style="text-align:center;width:90px" />
		<list:data-column col="fdTypeTxt" escape="false" title="${ lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthDetail.fdType') }" style="text-align:center;width:100px" >
			<div class="fdLimitInfo">
				<input type="radio" checked="checked" readonly="readonly"><span class="fdType"><c:out value="${model.fdTypeName}"/></span><span class="limitRange" style="display:${model.fdType==3?'none':''}">&nbsp;<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdLimit"/>&nbsp;<span class="fdMinLimit"><c:out value='${model.fdMinLimit}'/></span>~<span class="fdLimit"><c:out value='${model.fdLimit}'/></span></span>
			</div>
		</list:data-column>
		<list:data-column col="fdAuthInfoId">
			<c:out value="${model.fdAuthInfo.fdId}" />
		</list:data-column>
		
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>