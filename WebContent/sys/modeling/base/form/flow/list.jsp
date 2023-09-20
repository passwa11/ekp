<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="modelingAppFlow" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  property="fdName" title="${ lfn:message('sys-modeling-base:modeling.flow.fdName') }" style="min-width:180px">
		</list:data-column>
		<list:data-column headerClass="width80" col="fdValid" title="${ lfn:message('sys-modeling-base:modeling.flow.fdValid') }" escape="false">
		    <c:if test="${modelingAppFlow.fdValid}">
				${ lfn:message('sys-modeling-base:modeling.flow.fdValid.true') }
			</c:if>
			<c:if test="${!modelingAppFlow.fdValid}">
				${ lfn:message('sys-modeling-base:modeling.flow.fdValid.false') }
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width80" property="docCreator.fdName" title="${ lfn:message('model.fdCreator') }">
		</list:data-column>
		<list:data-column headerClass="width140" property="docCreateTime" title="${ lfn:message('model.fdCreateTime') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 编辑 -->
					<a class="btn_txt" href="javascript:edit('${modelingAppFlow.fdId}')">${lfn:message('button.edit')}</a>
					<a class="btn_txt" href="javascript:deleteDoc('${modelingAppFlow.fdId}')">${lfn:message('button.delete')}</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>