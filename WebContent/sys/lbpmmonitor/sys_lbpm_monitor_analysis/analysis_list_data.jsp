<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<list:data>
	<list:data-columns var="lbpmProcess" list="${queryPage.list }">
		 <list:data-column property="fdId">
		</list:data-column >
		<list:data-column col="url" escape="false">
		    <c:if test="${not empty urltMap[lbpmProcess.fdId]}">
	             ${urltMap[lbpmProcess.fdId]}
	        </c:if>
		</list:data-column >
		<list:data-column col="index">
		     ${status+1}
		</list:data-column >
		
		<list:data-column col="subject" title="标题" headerClass="width80" escape="false" >
			<c:if test="${not empty subjectMap[lbpmProcess.fdId]}">
		          <span class="com_subject">${subjectMap[lbpmProcess.fdId]}</span>
	        </c:if>
		</list:data-column>
		
		<list:data-column headerClass="width80" col="fdStatus" title="${ lfn:message('km-review:kmReviewTemplate.fdStatus') }" escape="false">
		    <c:if test="${lbpmProcess.fdStatus=='20'}">
				流程中
			</c:if>
			<c:if test="${lbpmProcess.fdStatus=='10'}">
				草稿
			</c:if>
			<c:if test="${lbpmProcess.fdStatus=='30'}">
				结束
			</c:if>
			<c:if test="${lbpmProcess.fdStatus=='00'}">
				废弃
			</c:if>
			<c:if test="${lbpmProcess.fdStatus=='21'}">
				流程出错
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width80" property="fdIdentity.fdName" title="${ lfn:message('km-review:kmReviewTemplate.docCreatorId') }">
		</list:data-column>
		<list:data-column headerClass="width80" property="fdCreateTime" title="${ lfn:message('km-review:kmReviewTemplate.docCreateTime') }">
		</list:data-column>
		<list:data-column headerClass="width80" property="fdEndedTime" title="${ lfn:message('km-review:kmReviewMain.docPublishTime') }">
		</list:data-column>
		<list:data-column headerClass="width80" col="fdCostTime" title="审批耗时">
			<c:if test="${lbpmProcess.fdCostTime!=-1}">
					<fmt:formatNumber pattern="#.##"  value="${lbpmProcess.fdCostTime/1000/60/60}" minFractionDigits="2"/>h
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width120" col="fdNodes" title="当前节点">
			<c:forEach var="fdNode"   items="${lbpmProcess.fdNodes}" varStatus="status">
				<c:if test="${status.index==0}">
					${fdNode.fdFactNodeName}
				</c:if>
		       <c:if test="${status.index!=0}">
					;${fdNode.fdFactNodeName}
				</c:if>
		   </c:forEach>
		</list:data-column>
		
		<list:data-column headerClass="width120" col="fdNodesExpecter" title="当前处理人">
			<c:forEach var="fdNode"   items="${lbpmProcess.fdNodes}" varStatus="status">
				<c:forEach var="workitem"   items="${fdNode.fdWorkitems}" varStatus="status1" >
						<c:if test="${workitem.fdStatus=='20' or workitem.fdStatus=='51' or workitem.fdStatus=='50'}">
							${workitem.fdExpecter.fdName};
						</c:if>
				</c:forEach>
		   </c:forEach>
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>