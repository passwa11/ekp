<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmComminfoCategory" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  property="fdName" title="${ lfn:message('km-comminfo:kmComminfoCategory.fdName') }" style="text-align:left;min-width:180px">
		</list:data-column>
		<list:data-column headerClass="width30" property="fdOrder" title="${ lfn:message('km-comminfo:kmComminfoMain.fdOrder') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="docCreator.fdName" title="${ lfn:message('km-comminfo:kmComminfoCategory.docCreatorId') }">
		</list:data-column>
		<list:data-column headerClass="width140" property="docCreateTime" title="${ lfn:message('km-comminfo:kmComminfoCategory.docCreateTime') }">
		</list:data-column>
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/km/comminfo/km_comminfo_category/kmComminfoCategory.do?method=edit&fdId=${kmComminfoCategory.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${kmComminfoCategory.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/km/comminfo/km_comminfo_category/kmComminfoCategory.do?method=delete&fdId=${kmComminfoCategory.fdId}" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:deleteAll('${kmComminfoCategory.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>