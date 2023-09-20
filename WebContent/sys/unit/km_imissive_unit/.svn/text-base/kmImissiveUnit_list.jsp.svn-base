<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmImissiveUnit" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  property="fdName" title="${ lfn:message('sys-unit:kmImissiveUnit.fdName') }" style="text-align:left;min-width:180px">
		</list:data-column>
		<list:data-column headerClass="width80" property="fdCode" title="${ lfn:message('sys-unit:kmImissiveUnit.fdCode') }">
		</list:data-column>
		<list:data-column headerClass="width30" property="fdOrder" title="${ lfn:message('sys-unit:kmImissiveUnit.fdOrder') }">
		</list:data-column>
		<list:data-column headerClass="width200" property="fdParent.fdName" title="上级单位">
		</list:data-column>
		<list:data-column headerClass="width200" property="fdCategory.fdName" title="${ lfn:message('sys-unit:kmImissiveUnit.fdCategoryId') }">
		</list:data-column>
		<list:data-column headerClass="width80" property="fdShortName" title="${ lfn:message('sys-unit:kmImissiveUnit.fdShortName') }">
		</list:data-column>
		<list:data-column headerClass="width60" col="fdNature" title="${ lfn:message('sys-unit:kmImissiveUnit.fdNature') }">
		   <sunbor:enumsShow value="${kmImissiveUnit.fdNature}" enumsType="kmImissiveUnit.fdNature4show" />
		</list:data-column>
		<list:data-column headerClass="width60" col="fdIsAvailable" title="${ lfn:message('sys-unit:kmImissiveUnit.fdIsAvailable') }">
		   <sunbor:enumsShow value="${kmImissiveUnit.fdIsAvailable}" enumsType="common_yesno" />
		</list:data-column>
		<list:data-column headerClass="width80" property="fdCenterCode" title="${ lfn:message('sys-unit:kmImissiveUnit.fdCenterCode') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="docCreator.fdName" title="${ lfn:message('sys-unit:kmImissiveUnit.docCreateId') }">
		</list:data-column>
		<list:data-column headerClass="width140" property="docCreateTime" title="${ lfn:message('sys-unit:kmImissiveUnit.docCreateTime') }">
		</list:data-column>
		<c:if test="${outer ne 'true'}">
		<!-- 其它操作 -->
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
				<c:if test="${kmImissiveUnit.fdNature ne 2}">
					<kmss:auth requestURL="/sys/unit/km_imissive_unit/kmImissiveUnit.do?method=edit&fdId=${kmImissiveUnit.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${kmImissiveUnit.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/unit/km_imissive_unit/kmImissiveUnit.do?method=delete&fdId=${kmImissiveUnit.fdId}" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:deleteAll('${kmImissiveUnit.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</c:if>	
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
		</c:if>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>