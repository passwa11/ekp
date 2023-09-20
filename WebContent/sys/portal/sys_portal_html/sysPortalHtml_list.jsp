<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysPortalHtml" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  property="fdName" title="${ lfn:message('sys-portal:sysPortalHtml.fdName') }" style="text-align:left;min-width:180px">
		</list:data-column>
		<list:data-column headerClass="width50" col="fdAnonymous" title="${ lfn:message('sys-portal:sysportal.anonymous') }">
			<sunbor:enumsShow value="${sysPortalHtml.fdAnonymous==null?false:sysPortalHtml.fdAnonymous}" enumsType="sys_portal_anonymous" />
			<%-- <xform:select property="connState" value="${sysPortalHtml.fdAnonymous==null?false:sysPortalHtml.fdAnonymous}">
				<xform:enumsDataSource enumsType="sys_portal_anonymous" />
			</xform:select> --%>
		</list:data-column>
		<list:data-column headerClass="width140" property="docCreateTime" title="${ lfn:message('sys-portal:sysPortalHtml.docCreateTime') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="docCreator.fdName" title="${ lfn:message('sys-portal:sysPortalHtml.docCreator') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width80" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/portal/sys_portal_html/sysPortalHtml.do?method=edit&fdId=${sysPortalHtml.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysPortalHtml.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/portal/sys_portal_html/sysPortalHtml.do?method=delete&fdId=${sysPortalHtml.fdId}" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:deleteById('${sysPortalHtml.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>