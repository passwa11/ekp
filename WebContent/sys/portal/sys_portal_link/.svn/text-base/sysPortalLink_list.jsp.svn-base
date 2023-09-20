<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysPortalLink" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  property="fdName" title="${ lfn:message('sys-portal:sysPortalLink.fdName') }" style="text-align:left;min-width:180px">
		</list:data-column>
		<list:data-column headerClass="width50" col="fdAnonymous" title="${ lfn:message('sys-portal:sysportal.anonymous') }">
			<sunbor:enumsShow value="${sysPortalLink.fdAnonymous==null?false:sysPortalLink.fdAnonymous}" enumsType="sys_portal_anonymous" />
			<%-- <xform:select property="fdAnonymous" value="${sysPortalLink.fdAnonymous==null?false:sysPortalLink.fdAnonymous}">
				<xform:enumsDataSource enumsType="sys_portal_anonymous" />
			</xform:select> --%>
		</list:data-column>
		<list:data-column headerClass="width140" property="docCreateTime" title="${ lfn:message('sys-portal:sysPortalLink.docCreateTime') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="docCreator.fdName" title="${ lfn:message('sys-portal:sysPortalLink.docCreator') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width80" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/portal/sys_portal_link/sysPortalLink.do?method=edit&fdId=${sysPortalLink.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysPortalLink.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/portal/sys_portal_link/sysPortalLink.do?method=delete&fdId=${sysPortalLink.fdId}" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:deleteById('${sysPortalLink.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>