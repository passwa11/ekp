<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<list:data>
	<list:data-columns var="mainData" list="${page.list}" varIndex="index">
		<list:data-column col="index">
		     ${index + 1}
		</list:data-column>
		<c:forEach items="${subjects}" var="subject" varStatus="status">
			<c:choose>
				<c:when test="${subject.name == 'fdId'}">
					<list:data-column col="${subject.name}">
						${mainData[status.index]}
					</list:data-column>
				</c:when>
				<c:otherwise>
					<list:data-column col="${subject.name}" title="${subject.label}" styleClass="${subject.isSubject == true ? 'mainData_title' : ''}">
						<c:if test="${subject.enumType != null}">
							<sunbor:enumsShow value="${mainData[status.index]}" enumsType="${subject.enumType}" />
						</c:if>
						<c:if test="${subject.enumType == null}">
							${mainData[status.index]}
						</c:if>
					</list:data-column>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</list:data-columns>
	<list:data-paging currentPage="${page.pageno}" pageSize="${page.rowsize}" totalSize="${page.totalrows}">
	</list:data-paging>
</list:data>