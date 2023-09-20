<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="compDbcp" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  headerClass="width200" property="fdName" title="${ lfn:message('component-dbop:compDbcp.fdName') }" style="text-align:left;min-width:180px">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdType" title="${ lfn:message('component-dbop:compDbcp.fdType') }">
		</list:data-column>
		<list:data-column  col="fdDescription" title="${ lfn:message('component-dbop:compDbcp.fdDescription') }" escape="false">
	        <c:choose>
				<c:when test="${fn:length(compDbcp.fdDescription)>30 }"><c:out value="${fn:substring(compDbcp.fdDescription,0,29)}..." /></c:when>
				<c:otherwise><c:out value="${compDbcp.fdDescription}" /></c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/component/dbop/compDbcp.do?method=edit&fdId=${compDbcp.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${compDbcp.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/component/dbop/compDbcp.do?method=delete&fdId=${compDbcp.fdId}" requestMethod="POST">
						<!-- 禁用 -->
						<a class="btn_txt" href="javascript:deleteAll('${compDbcp.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>