<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysNotifyCategory" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 排序号 -->
		<list:data-column headerClass="width30" property="fdOrder" title="${ lfn:message('sys-notify:sysNotifyCategory.fdOrder') }">
		</list:data-column>
		<list:data-column headerClass="width100" col="fdCateType" title="${ lfn:message('sys-notify:sysNotifyCategory.fdCateType') }">
			 <c:if test="${sysNotifyCategory.fdCateType==0}">
				${ lfn:message('sys-notify:sysNotifyCategory.type.module')}
			 </c:if>
			<c:if test="${sysNotifyCategory.fdCateType==1}">
				${ lfn:message('sys-notify:sysNotifyCategory.type.system') } 
			</c:if>
		</list:data-column>
		<!-- 名称 -->
		<list:data-column property="fdName" title="${ lfn:message('sys-notify:sysNotifyCategory.fdName') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/notify/sys_notify_category/sysNotifyCategory.do?method=edit&fdId=${sysNotifyCategory.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysNotifyCategory.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/notify/sys_notify_category/sysNotifyCategory.do?method=deleteall" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${sysNotifyCategory.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>