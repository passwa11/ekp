<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysBookmarkMain" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  headerClass="width200" property="docSubject" title="${ lfn:message('sys-bookmark:sysBookmarkMain.docSubject') }" style="text-align:left;min-width:180px">
		</list:data-column>
		<list:data-column  property="docCategory.fdName" title="${ lfn:message('sys-bookmark:sysBookmarkCategory.fdParentId') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="docCreateTime" title="${ lfn:message('sys-bookmark:sysBookmarkMain.docCreateTime') }">
		</list:data-column>
		<list:data-column  col="fdUrl" title="${ lfn:message('sys-bookmark:sysBookmarkMain.link') }" escape="false">
		   <a href="<c:url value="${sysBookmarkMain.fdUrl }" />" target="_blank" ><bean:message key="button.view"/></a>
		</list:data-column>
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/bookmark/sys_bookmark_main/sysBookmarkMain.do?method=edit&fdId=${sysBookmarkMain.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysBookmarkMain.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/bookmark/sys_bookmark_main/sysBookmarkMain.do?method=delete&fdId=${sysBookmarkMain.fdId}" requestMethod="POST">
						<!-- 禁用 -->
						<a class="btn_txt" href="javascript:deleteAll('${sysBookmarkMain.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>