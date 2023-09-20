<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="ticRestAuth" list="${queryPage.list }">
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		<%--标题--%>
		<list:data-column col="docSubject" title="${ lfn:message('tic-rest-connector:ticRestAuth.docSubject') }" style="text-align:center" escape="false">
			<span class="com_subject"><c:out value="${ticRestAuth.docSubject}" /></span>
		</list:data-column>
		<list:data-column col="fdUseCustAt" title="${ lfn:message('tic-rest-connector:ticRestAuth.fdUseCustAt') }" style="text-align:center" escape="false">
			<sunbor:enumsShow value="${ticRestAuth.fdUseCustAt}" enumsType="rest_fdOauth_type"/>
		</list:data-column>
		<list:data-column col="fdAgentId" title="$AgentID" style="text-align:center" escape="false">
			<span class="com_subject"><c:out value="${ticRestAuth.fdAgentId}" /></span>
		</list:data-column>
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
						<a class="btn_txt" style="color:#4285f4;" href="javascript:setAuth('${ticRestAuth.fdId}')">选择</a>
						<a class="btn_txt" style="color:#4285f4;" href="javascript:editAuth('${ticRestAuth.fdId}')">编辑</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
