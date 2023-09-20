<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="modelingAppModel" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="fdName" title="${lfn:message('sys-modeling-base:modeling.model.fdName')}" style="text-align:left;min-width:180px" escape="false">
			<span class="com_subject"><c:out value="${modelingAppModel.fdName}" /></span>
		</list:data-column>
		<list:data-column headerStyle="width:100px" col="docCreator.fdName" title="${lfn:message('sys-modeling-base:modeling.modelMain.docCreator')}" escape="false">
		        <ui:person personId="${modelingAppModel.docCreator.fdId}" personName="${modelingAppModel.docCreator.fdName}"></ui:person>
		</list:data-column>

		<list:data-column headerClass="width180" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!-- 操作列 -->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/modeling/base/modelingAppModel.do?method=edit&fdId=${modelingAppModel.fdId}" requestMethod="GET">
						<!-- 设置 -->
						<a class="btn_txt" href="javascript:doSetting('${modelingAppModel.fdId}')">${lfn:message('sys-modeling-base:modeling.form.Set')}</a>
					</kmss:auth>
					</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>

	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>