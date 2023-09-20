<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmImeetingResource" list="${list}" varIndex="status">
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		<%--序号--%>
		<list:data-column col="index">${status+1 }</list:data-column>
		<%--排序号--%>
		<list:data-column property="fdOrder" headerClass="width30" styleClass="width30" title="${ lfn:message('model.fdOrder') }">
		</list:data-column>
		<%--会议室名字--%>
		<list:data-column  col="fdName" headerClass="width140" styleClass="width140"  title="${ lfn:message('km-imeeting:kmImeetingRes.fdName') }" escape="false">
		 	<c:out value="${kmImeetingResource.fdName}"></c:out>
		</list:data-column>
		<%--地点楼层--%>
		<list:data-column  property="fdAddressFloor" title="${ lfn:message('kmImeetingRes.fdAddressFloor') }" >
		</list:data-column>
		<%--容纳人数--%>
		<list:data-column  property="fdSeats" title="${ lfn:message('kmImeetingRes.fdSeats') }" >
		</list:data-column>
		<%--设备详情--%>
		<list:data-column col="fdDetail" title="${ lfn:message('km-imeeting:kmImeetingRes.fdDetail') }" escape="false">
			<c:out value="${kmImeetingResource.fdDetail}"></c:out>
		</list:data-column>
		<%--会议室类别--%>
		<list:data-column headerClass="width120" styleClass="width120" property="docCategory.fdName" title="${ lfn:message('km-imeeting:kmImeetingRes.docCategory') }">
		</list:data-column>
		<%--会议室最大使用时长--%>
		<list:data-column  property="fdUserTime" title="${ lfn:message('km-imeeting:kmImeetingRes.fdUserTime') }" >
		</list:data-column>
		<%--是否可选--%>
		<c:if test="${empty conflictRes ||not empty conflictRes && fn:indexOf(conflictRes,kmImeetingResource.fdId)<0  }">
			<list:data-column col="select">1</list:data-column>
		</c:if>
		<c:if test="${not empty conflictRes && fn:indexOf(conflictRes,kmImeetingResource.fdId)>-1 }">
			<list:data-column col="select">0</list:data-column>
		</c:if>
		<list:data-column col="docKeeper.fdName" title="${ lfn:message('km-imeeting:kmImeetingRes.docKeeper') }" >
			<c:if test="${not empty kmImeetingResource.docKeeper and kmImeetingResource.docKeeper.fdIsAvailable }">
				<c:choose>
					<c:when test="${kmImeetingResource.docKeeper.fdIsAvailable }">
						<c:out value="${kmImeetingResource.docKeeper.fdName}"></c:out>
					</c:when>
					<c:otherwise>
						<c:out value="${kmImeetingResource.docKeeper.fdName}"></c:out>${ lfn:message('km-imeeting:kmImeetingRes.docKeeper.disable') }
					</c:otherwise>
				</c:choose>
			</c:if>
		</list:data-column>
	</list:data-columns>
	
</list:data>