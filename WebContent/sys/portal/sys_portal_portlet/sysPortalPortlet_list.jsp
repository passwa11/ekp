<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysPortalPortlet" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column headerClass="width250"  property="fdName" title="${ lfn:message('sys-portal:sysPortalPortlet.fdName') }" style="text-align:left;min-width:180px">
		</list:data-column>
		<list:data-column headerClass="width50" col="fdAnonymous" title="${ lfn:message('sys-portal:sysportal.anonymous') }">
			<sunbor:enumsShow value="${sysPortalPortlet.fdAnonymous==null?false:sysPortalPortlet.fdAnonymous}" enumsType="sys_portal_anonymous" />
			<%-- <xform:select property="connState" value="${sysPortalPortlet.fdAnonymous==null?false:sysPortalPortlet.fdAnonymous}">
				<xform:enumsDataSource enumsType="sys_portal_anonymous" />
			</xform:select> --%>
		</list:data-column>
		<list:data-column headerClass="width120" property="fdModule" title="${ lfn:message('sys-portal:sysPortalPortlet.fdModule') }">
		</list:data-column>
		<list:data-column  property="fdDescription" title="${ lfn:message('sys-portal:sysPortalPortlet.fdDescription') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdFormat" title="${ lfn:message('sys-portal:sysPortalPortlet.fdFormat') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width80" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 预览 -->
					<a class="btn_txt" href="javascript:preview('${sysPortalPortlet.fdId}')">${lfn:message('sys-portal:sys.portal.preview')}</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>