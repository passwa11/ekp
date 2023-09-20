<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="main" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 提醒项名称 -->
		<list:data-column headerClass="width200" property="fdName" title="${ lfn:message('sys-remind:sysRemindMain.fdName') }">
		</list:data-column>
		<!-- 是否启用 -->
		<list:data-column headerClass="width100" col="fdIsEnable" title="${ lfn:message('sys-remind:sysRemindMain.fdIsEnable') }">
			<sunbor:enumsShow value="${main.fdIsEnable}" enumsType="sys_remind_main_enable" />
		</list:data-column>
		<!-- 创建时间 -->
		<list:data-column headerClass="width100" col="docCreateTime" title="${ lfn:message('sys-remind:sysRemindMain.docCreateTime') }">
			<kmss:showDate value="${main.docCreateTime}" type="datetime" />
		</list:data-column>
		<!-- 创建者 -->
		<list:data-column headerClass="width100" col="docCreator" title="${ lfn:message('sys-remind:sysRemindMain.docCreator') }">
			${main.docCreator.fdName}
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<a class="btn_txt" href="javascript:showTask('${main.fdId}');">${lfn:message('sys-remind:sysRemindMain.showTask')}</a>
					<a class="btn_txt" href="javascript:showLog('${main.fdId}');">${lfn:message('sys-remind:sysRemindMain.showLog')}</a>
					<a class="btn_txt" href="javascript:showRemind('${main.fdId}');">${lfn:message('button.view')}</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>