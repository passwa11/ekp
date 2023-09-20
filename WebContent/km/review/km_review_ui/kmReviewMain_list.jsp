<%@ page language="java" contentType="text/json; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.km.review.util.KmReviewUtil"%>
<%@ page import="com.landray.kmss.sys.xform.util.SysFormDingUtil"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%
	request.setAttribute("enableModule", KmReviewUtil.getEnableModule());
%>
<list:data>
	<list:data-columns var="kmReviewMain" list="${queryPage.list }" exceptColumns="${enableModule.enableKmArchives eq 'false' ? 'fdIsFiling' : ''}" >
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdTemplate.fdName" title="${ lfn:message('km-review:kmReviewMain.process.name') }">
			<c:out value="${kmReviewMain.fdTemplate.fdName}" />
		</list:data-column>
		<list:data-column col="docSubject" title="${ lfn:message('km-review:kmReviewMain.docSubject.des') }" style="text-align:left;min-width:180px" escape="false">
			<span class="com_subject"><c:out value="${kmReviewMain.docSubject}" /></span>
		</list:data-column>
		<list:data-column headerClass="width140" property="fdNumber" title="${ lfn:message('km-review:kmReviewMain.fdNumber') }">
		</list:data-column>
		<list:data-column headerClass="width100" col="fdUseWord" title="${ lfn:message('km-review:kmReviewTemplate.fdUseWord') }">
			<c:if test="${kmReviewMain.fdUseWord == true}">
				<c:out value="${ lfn:message('km-review:kmReviewTemplate.fdUseWord.yes') }" />
			</c:if>
			<c:if test="${kmReviewMain.fdUseWord == false}">
				<c:out value="${ lfn:message('km-review:kmReviewTemplate.fdUseWord.no') }" />
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width60" col="docCreator.fdName" title="${ lfn:message('km-review:kmReviewMain.docCreatorName') }" escape="false">
			<ui:person personId="${kmReviewMain.docCreator.fdId}" personName="${kmReviewMain.docCreator.fdName}"></ui:person>
		</list:data-column>
		<%if(ISysAuthConstant.IS_AREA_ENABLED) { %>
		<!-- 所属场所-->
		<list:data-column headerStyle="width:80px" property="authArea.fdName" title="${ lfn:message('sys-authorization:sysAuthArea.authArea') }">
		</list:data-column>
		<% } %>
		<c:choose>
			<c:when test='<%="true".equals(SysFormDingUtil.getEnableDing()) || "true".equals(request.getParameter("ddpage"))%>'>
				<list:data-column headerClass="width100" col="docCreateTime" title="${ lfn:message('km-review:kmReviewMain.docCreateTime') }">
					<kmss:showDate value="${kmReviewMain.docCreateTime}" type="datetime"/>
				</list:data-column>
				<list:data-column headerClass="width100" col="docPublishTime" title="${ lfn:message('km-review:kmReviewMain.docPublishTime') }">
					<kmss:showDate value="${kmReviewMain.docPublishTime}" type="datetime"/>
				</list:data-column>
			</c:when>
			<c:otherwise>
				<list:data-column headerClass="width100" col="docCreateTime" title="${ lfn:message('km-review:kmReviewMain.docCreateTime') }">
					<kmss:showDate value="${kmReviewMain.docCreateTime}" type="date"/>
				</list:data-column>
				<!--创建时间-->
				<list:data-column headerClass="width120" col="docCreateTime_time" title="${ lfn:message('km-review:kmReviewMain.docCreateTime') }">
					<kmss:showDate value="${kmReviewMain.docCreateTime}" type="datetime"/>
				</list:data-column>
				<c:if test="${param['q.mydoc']!='approval'}">
					<list:data-column headerClass="width100" col="docPublishTime" title="${ lfn:message('km-review:kmReviewMain.docPublishTime') }">
						<kmss:showDate value="${kmReviewMain.docPublishTime}" type="date"/>
					</list:data-column>
					<list:data-column headerClass="width120" col="docPublishTime_time" title="${ lfn:message('km-review:kmReviewMain.docPublishTime') }">
						<kmss:showDate value="${kmReviewMain.docPublishTime}" type="datetime"/>
					</list:data-column>
				</c:if>
			</c:otherwise>
		</c:choose>
		<list:data-column headerClass="width40" col="docStatus" title="${ lfn:message('km-review:kmReviewMain.docStatus') }">
			<c:if test="${kmReviewMain.docStatus=='00'}">
				${ lfn:message('km-review:status.discard')}
			</c:if>
			<c:if test="${kmReviewMain.docStatus=='10'}">
				${ lfn:message('km-review:status.draft') }
			</c:if>
			<c:if test="${kmReviewMain.docStatus=='11'}">
				${ lfn:message('km-review:status.refuse')}
			</c:if>
			<c:if test="${kmReviewMain.docStatus=='20'}">
				${ lfn:message('km-review:status.append') }
			</c:if>
			<c:if test="${kmReviewMain.docStatus=='30'}">
				${ lfn:message('km-review:status.publish') }
			</c:if>
			<c:if test="${kmReviewMain.docStatus=='31'}">
				${ lfn:message('km-review:status.feedback') }
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width100" col="nodeName" title="${ lfn:message('km-review:sysWfNode.processingNode.currentProcess') }" escape="false">
			<kmss:showWfPropertyValues  var="nodevalue" idValue="${kmReviewMain.fdId}" propertyName="nodeName" />
				<div class="textEllipsis width100" style="max-width:200px" title="${fn:escapeXml(nodevalue)}">
				<c:out value="${nodevalue}"></c:out>
			</div>
		</list:data-column>
		<list:data-column headerClass="width100" col="handlerName" title="${ lfn:message('km-review:sysWfNode.processingNode.currentProcessor') }" escape="false">
			<kmss:showWfPropertyValues  var="handlerValue" idValue="${kmReviewMain.fdId}" propertyName="handlerName" />
				<div class="textEllipsis width100" style="font-weight:bold;max-width:200px" title="${handlerValue}">
				<c:out value="${handlerValue}"></c:out>
			</div>
		</list:data-column>
		<c:if test="${param['q.mydoc'] eq 'approval'}">
			<list:data-column headerClass="width120" col="arrivalTime" title="${ lfn:message('km-review:sysWfNode.processingNode.currentarrTime') }" escape="false">
				<kmss:showWfPropertyValues idValue="${kmReviewMain.fdId}" propertyName="arrivalTime" />
			</list:data-column>
		</c:if>
		<c:if test="${param['q.mydoc'] eq 'approved'}">
			<list:data-column headerClass="width120" col="resolveTime" title="${ lfn:message('km-review:sysWfNode.processingNode.resolveTime') }" escape="false">
				<kmss:showWfPropertyValues idValue="${kmReviewMain.fdId}" propertyName="resolveTime" />
			</list:data-column>
		</c:if>
		<list:data-column headerClass="width40" col="fdIsFiling" title="${ lfn:message('km-review:kmReviewMain.fdIsFiling') }">
			<c:choose>
				<c:when test="${not empty kmReviewMain.fdIsFiling and kmReviewMain.fdIsFiling}">
					${ lfn:message('km-review:kmReviewMain.fdIsFiling.have') }
				</c:when>
				<c:otherwise>
					${ lfn:message('km-review:kmReviewMain.fdIsFiling.nothave') }
				</c:otherwise>
			</c:choose>
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
					  pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>