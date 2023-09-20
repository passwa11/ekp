<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmImissiveUnitCategory" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  property="fdName" title="${ lfn:message('sys-unit:kmImissiveUnitCategory.fdName') }" style="text-align:left;min-width:180px">
		</list:data-column>
		<list:data-column  headerClass="width140" property="fdParent.fdName" title="${ lfn:message('sys-unit:kmImissiveUnitCategory.fdParentName') }">
		</list:data-column>
		<list:data-column headerClass="width80" property="fdOrder" title="${ lfn:message('sys-unit:kmImissiveUnitCategory.fdOrder') }">
		</list:data-column>
		<list:data-column headerClass="width80" col="fdIsAvailable" title="${ lfn:message('sys-unit:kmImissiveUnitCategory.fdIsAvailable') }">
		   <sunbor:enumsShow value="${kmImissiveUnitCategory.fdIsAvailable}" enumsType="common_yesno" />
		</list:data-column>
		<list:data-column headerClass="width100" property="docCreator.fdName" title="${ lfn:message('sys-unit:kmImissiveUnitCategory.docCreateId') }">
		</list:data-column>
		<list:data-column headerClass="width140" property="docCreateTime" title="${ lfn:message('sys-unit:kmImissiveUnitCategory.docCreateTime') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/unit/km_imissive_unit_category/kmImissiveUnitCategory.do?method=edit&fdId=${kmImissiveUnitCategory.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${kmImissiveUnitCategory.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/unit/km_imissive_unit_category/kmImissiveUnitCategory.do?method=delete&fdId=${kmImissiveUnitCategory.fdId}" requestMethod="POST">
						<!-- 禁用 -->
						<a class="btn_txt" href="javascript:deleteAll('${kmImissiveUnitCategory.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>