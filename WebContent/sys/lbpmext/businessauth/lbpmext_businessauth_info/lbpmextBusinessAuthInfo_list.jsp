<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="lbpmExtBusinessAuthInfo" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 条目编号 -->
		<list:data-column headerClass="width160" property="fdNumber" title="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.authorizationCode')}">
		</list:data-column>
		<!-- 被授权人 -->
		<list:data-column headerClass="" property="fdAuthorizedPerson.fdName" title="${ lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.fdAuthorizedPerson') }">
		</list:data-column>
		<!-- 被授权岗位 -->
		<list:data-column headerClass="" property="fdAuthorizedPost.fdName" title="${ lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.fdAuthorizedPost') }">
		</list:data-column>
		<!-- 开始时间 -->
		<list:data-column headerClass="width160" property="fdStartTime" title="${ lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.fdStartTime') }">
		</list:data-column>
		<!-- 结束时间 -->
		<list:data-column headerClass="width160" property="fdEndTime" title="${ lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.fdEndTime') }">
		</list:data-column>
		<list:data-column headerClass="" col="docStatus" title="${ lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.status') }">
			<sunbor:enumsShow value="${lbpmExtBusinessAuthInfo.docStatus}" enumsType="authInfo_docStatus" />
		</list:data-column>
		<!-- 创建时间 -->
		<list:data-column headerClass="width160" property="fdCreateTime" title="${ lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.fdCreateTime') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width160" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuthInfo.do?method=add" requestMethod="GET">
						<!-- 复制 -->
						<a class="btn_txt" href="javascript:clone('${lbpmExtBusinessAuthInfo.fdId}')">${lfn:message('button.copy')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuthInfo.do?method=edit&fdId=${lbpmExtBusinessAuthInfo.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${lbpmExtBusinessAuthInfo.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuthInfo.do?method=delete&fdId=${lbpmExtBusinessAuthInfo.fdId}" requestMethod="GET">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:delDoc('${lbpmExtBusinessAuthInfo.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>