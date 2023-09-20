<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.sunbor.web.tag.Page"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.sys.authentication.token.server.model.SysTokenInfo"%>

<list:data>
	<list:data-columns var="sysTokenInfo" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column headerClass="width80" property="fdValue" title="${ lfn:message('sys-authentication:sysTokenInfo.fdValue') }">
		</list:data-column>
		<list:data-column headerClass="width30" property="fdVisitCount" title="${ lfn:message('sys-authentication:sysTokenInfo.fdVisitCount') }">
		</list:data-column>
		<list:data-column headerClass="width30" property="fdVisitMaxCount" title="${ lfn:message('sys-authentication:sysTokenInfo.fdVisitMaxCount') }">
		</list:data-column>
		<list:data-column headerClass="width30" property="fdVisitEndPeriod" title="${ lfn:message('sys-authentication:sysTokenInfo.fdVisitEndPeriod') }">
		</list:data-column>
		<list:data-column headerClass="width80" col="fdInvalidation" title="${ lfn:message('sys-authentication:sysTokenInfo.fdInvalidation') }" escape="false">
			<c:if test="${sysTokenInfo.fdInvalidation=='0' }">
				${ lfn:message('sys-authentication:sysTokenInfo.invalidation') }
			</c:if>
			<c:if test="${sysTokenInfo.fdInvalidation=='1' }">
				${ lfn:message('sys-authentication:sysTokenInfo.effective') }
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width200" property="docCreator.fdName" title="${ lfn:message('sys-authentication:sysTokenInfo.docCreator') }">
		</list:data-column>
		<list:data-column headerClass="width140" property="docCreateTime" title="${ lfn:message('sys-authentication:sysTokenInfo.docCreateTime') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/token/sys_token_info/sysTokenInfo.do?method=updateInvalidation&fdId=${sysTokenInfo.fdId}" requestMethod="POST">
						<!-- 禁用 -->
						<c:if test="${sysTokenInfo.fdInvalidation=='0' }">
							<a class="btn_txt" href="javascript:updateInvalidation('${sysTokenInfo.fdId}')">${lfn:message('sys-authentication:button.invalidation')}</a>
						</c:if>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>