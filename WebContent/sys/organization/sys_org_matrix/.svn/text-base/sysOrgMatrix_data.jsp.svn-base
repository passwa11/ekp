<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="relations" list="${queryPage.list }">
		<c:forEach items="${relations}" var="relation">
			<c:choose>
				<c:when test="${relation.fdIsPrimary}">
					<list:data-column col="fdId" title="fdId">
						${relation.fdId}
					</list:data-column>
					<c:set var="relationId" value="${relation.fdId}"/>
				</c:when>
				<c:when test="${relation.fdFieldName == 'fd_cate_id'}">
					<list:data-column col="cate" title="${lfn:message('sys-organization:sysOrgMatrix.dataCate.note')}">
						${relation.fdName}
					</list:data-column>
				</c:when>
				<c:when test="${relation.fdIsResult}">
					<list:data-column col="${relation.fdFieldName}" title="${relation.fdName}" headerClass="result" escape="false">
						<c:choose>
							<c:when test="${relation.fdType eq 'person_post'}">
								${relation.fdResultValueIds}|||||${relation.fdResultValueNames}|||||
								<c:forEach items="${relation.fdResultValues}" var="temp">
									${temp.fdOrgType};
								</c:forEach>
							</c:when>
							<c:otherwise>
								${relation.fdResultValueIds}|||||${relation.fdResultValueNames}
							</c:otherwise>
						</c:choose>
					</list:data-column>
				</c:when>
				<c:otherwise>
					<list:data-column col="${relation.fdFieldName}" title="${relation.fdName}" headerClass="conditional" escape="false">
						${relation.fdConditionalId}|||||${relation.fdConditionalValue}
					</list:data-column>
					<c:if test="${relation.fdFieldName2 != null}">
						<list:data-column col="${relation.fdFieldName}_2" headerClass="conditional" escape="false">
							${relation.fdConditionalValue2}
						</list:data-column>
					</c:if>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno}" pageSize="${queryPage.rowsize}" totalSize="${queryPage.totalrows}" />
</list:data>