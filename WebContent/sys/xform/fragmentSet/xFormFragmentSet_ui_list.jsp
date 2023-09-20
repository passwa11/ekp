<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="xFormFragmentSet" list="${queryPage.list }">
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		
		<list:data-column headerClass="width30" property="fdOrder" title="${ lfn:message('model.fdOrder') }"></list:data-column>
		
		<%--标题--%>
		<list:data-column col="fdName" title="${ lfn:message('sys-xform-fragmentSet:sysFormFragmentSet.fdName') }" style="text-align:center" escape="false">
			<span class="com_subject"><c:out value="${xFormFragmentSet.fdName}" /></span>
		</list:data-column>
		<!-- 类别 -->
		<list:data-column col="docCategory" title="${ lfn:message('sys-xform-fragmentSet:sysFormFragmentSet.docCategory ') }" style="text-align:center" escape="false">
			<c:out value="${xFormFragmentSet.docCategory.fdName}" />
		</list:data-column>
		<!-- 创建人 -->
		<list:data-column headerClass="width80" property="fdCreator.fdName" title="${ lfn:message('sys-xform-fragmentSet:sysFormFragmentSet.docCreatorId') }">
		</list:data-column>
		<!-- 创建时间 -->
		<list:data-column headerClass="width120" property="fdCreateTime" title="${ lfn:message('sys-xform-fragmentSet:sysFormFragmentSet.docCreateTime') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/xform/maindata/jdbc_data_set/xFormFragmentSet.do?method=edit&fdId=${xFormFragmentSet.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${xFormFragmentSet.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/xform/maindata/jdbc_data_set/xFormFragmentSet.do?method=deleteall" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:delDoc('${xFormFragmentSet.fdId}')">${lfn:message('button.delete')}</a>
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
