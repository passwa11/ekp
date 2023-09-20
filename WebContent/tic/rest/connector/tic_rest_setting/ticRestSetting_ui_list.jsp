<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="ticRestSetting" list="${queryPage.list }">
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		<%--标题--%>
		<list:data-column col="docSubject" title="${ lfn:message('tic-rest-connector:ticRestSetting.docSubject') }" style="text-align:center" escape="false">
			<span class="com_subject"><c:out value="${ticRestSetting.docSubject}" /></span>
		</list:data-column>
		<list:data-column col="fdHttpProxy" title="${ lfn:message('tic-rest-connector:ticRestSetting.fdHttpProxy') }" escape="false" style="text-align:center;">
			<sunbor:enumsShow value="${ticRestSetting.fdHttpProxy}" enumsType="common_yesno"/>
		</list:data-column>
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
				   <kmss:auth
						requestURL="/tic/rest/connector/tic_rest_main/ticRestMain.do?method=add&ticRestSettingId=${ticRestSetting.fdId}"
						requestMethod="GET">
						<a class="btn_txt" href="javascript:addFunc('${ticRestSetting.fdId}','${ticRestSetting.fdAppType}')">${lfn:message('tic-core-common:ticCoreCommon.newCreateFunc')}</a></kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
