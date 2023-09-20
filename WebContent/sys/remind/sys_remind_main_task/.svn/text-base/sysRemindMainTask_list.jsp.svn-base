<%@ page import="com.landray.kmss.sys.remind.model.SysRemindMainTask"%>
<%@ page import="com.landray.kmss.sys.remind.util.SysRemindUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="task" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 发送标题 -->
		<list:data-column headerClass="width400" property="fdSubject" title="${ lfn:message('sys-remind:sysRemindMainTask.fdSubject') }">
		</list:data-column>
		<!-- 执行时间 -->
		<list:data-column headerClass="width200" col="fdRunTime" title="${ lfn:message('sys-remind:sysRemindMainTask.fdRunTime') }">
			<kmss:showDate value="${task.fdRunTime}" type="datetime" />
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<%
					if(SysRemindUtil.isFinish(((SysRemindMainTask)pageContext.getAttribute("task")).getFdRunTime())) {
					%>
					<!-- 查看日志 -->
					<a class="btn_txt" href="javascript:showLog('${task.fdId}')">${lfn:message('sys-remind:sysRemindMain.showLog')}</a>
					<%
					} else {
					%>
					<!-- 执行任务 -->
					<a class="btn_txt" href="javascript:runTask('${task.fdId}')">${lfn:message('sys-remind:sysRemindMain.runTask')}</a>
					<%
					}
					%>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>