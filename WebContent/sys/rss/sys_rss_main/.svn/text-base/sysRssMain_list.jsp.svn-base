<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysRssMain" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  property="docSubject" title="${ lfn:message('sys-rss:sysRssMain.docSubject') }" style="text-align:center;min-width:180px">
		</list:data-column>
		<list:data-column headerClass="width40" property="fdOrder" title="${ lfn:message('sys-rss:sysRssMain.fdOrder') }">
		</list:data-column>
		<list:data-column property="fdLink" title="${ lfn:message('sys-rss:sysRssMain.fdLink') }" style="text-align:left;min-width:180px">
		</list:data-column>
		<list:data-column headerClass="width100" property="docCategory.fdName" title="${ lfn:message('sys-rss:sysRssMain.docCategoryId') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="docCreator.fdName" title="${ lfn:message('sys-rss:sysRssMain.docCreatorId') }">
		</list:data-column>
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/rss/sys_rss_main/sysRssMain.do?method=edit&fdId=${sysRssMain.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysRssMain.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/rss/sys_rss_main/sysRssMain.do?method=delete&fdId=${sysRssMain.fdId}" requestMethod="POST">
						<!-- 禁用 -->
						<a class="btn_txt" href="javascript:deleteAll('${sysRssMain.fdId}')">删除</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>