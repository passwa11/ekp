<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysFormMainDataInsystem" list="${queryPage.list }">
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		
		<list:data-column headerClass="width30" property="fdNewOrder" title="${ lfn:message('model.fdOrder') }"></list:data-column>
		
		<%--标题--%>
		<list:data-column col="docSubject" title="${ lfn:message('sys-xform-maindata:sysFormJdbcDataSet.docSubject') }" style="text-align:center" escape="false">
			<span class="com_subject"><c:out value="${sysFormMainDataInsystem.docSubject}" /></span>
		</list:data-column>
		
		<list:data-column col="docCategory" title="${ lfn:message('sys-xform-maindata:sysFormJdbcDataSet.docCategory') }" style="text-align:center" escape="false">
			<c:out value="${sysFormMainDataInsystem.docCategory.fdName}" />
		</list:data-column>
		
		<list:data-column headerClass="width80" property="docCreator.fdName" title="${ lfn:message('model.fdCreator') }"></list:data-column>
		
		<list:data-column headerClass="width140" property="docCreateTime" title="${ lfn:message('model.fdCreateTime') }"></list:data-column>
		
		<!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">					
					<!-- 编辑 -->
					<a class="btn_txt" href="javascript:edit('${sysFormMainDataInsystem.fdId}')">${lfn:message('button.edit')}</a>				
					<!-- 删除 -->
					<a class="btn_txt" href="javascript:delDoc('${sysFormMainDataInsystem.fdId}')">${lfn:message('button.delete')}</a>					
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
