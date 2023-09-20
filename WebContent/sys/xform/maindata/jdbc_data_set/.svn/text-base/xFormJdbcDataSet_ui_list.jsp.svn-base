<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="xFormjdbcDataSet" list="${queryPage.list }">
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		
		<list:data-column headerClass="width30" property="fdNewOrder" title="${ lfn:message('model.fdOrder') }"></list:data-column>
		
		<%--标题--%>
		<list:data-column col="docSubject" title="${ lfn:message('sys-xform-maindata:sysFormJdbcDataSet.docSubject') }" style="text-align:center" escape="false">
			<span class="com_subject"><c:out value="${xFormjdbcDataSet.docSubject}" /></span>
		</list:data-column>
		<list:data-column col="fdDataSource" title="${ lfn:message('sys-xform-maindata:sysFormJdbcDataSet.fdDataSource') }" escape="false" style="text-align:center;">
			<xform:select property="fdDataSource" style="float: left;" showStatus="view" value="${xFormjdbcDataSet.fdDataSource}">
			 	<xform:beanDataSource serviceBean="compDbcpService"
					selectBlock="fdId,fdName" orderBy="" />
			</xform:select>
		</list:data-column>
		<list:data-column col="docCategory" title="${ lfn:message('sys-xform-maindata:sysFormJdbcDataSet.docCategory') }" style="text-align:center" escape="false">
			<c:out value="${xFormjdbcDataSet.docCategory.fdName}" />
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/xform/maindata/jdbc_data_set/xFormjdbcDataSet.do?method=edit&fdId=${xFormjdbcDataSet.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${xFormjdbcDataSet.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
				<%-- 	<kmss:auth requestURL="/sys/xform/maindata/jdbc_data_set/xFormjdbcDataSet.do?method=deleteall" requestMethod="POST"> --%>
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:delDoc('${xFormjdbcDataSet.fdId}')">${lfn:message('button.delete')}</a>
				<%-- 	</kmss:auth> --%>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
