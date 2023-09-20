<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysLogJob" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 起始时间 -->
		<list:data-column headerClass="width100" col="fdStartTime" title="${ lfn:message('sys-log:sysLogJob.fdStartTime') }">
			<kmss:showDate type="datetime" value="${sysLogJob.fdStartTime}"/>
		</list:data-column>
		<!-- 终止时间 -->
		<list:data-column headerClass="width100" col="fdEndTime" title="${ lfn:message('sys-log:sysLogJob.fdEndTime') }">
			<kmss:showDate type="datetime" value="${sysLogJob.fdEndTime}"/>
		</list:data-column>
		<!-- 任务历时 -->
		<list:data-column headerClass="width100" property="fdTaskDuration" title="${ lfn:message('sys-log:sysLogJob.fdTaskDuration') }">
		</list:data-column>
		<!-- 标题 -->
		<list:data-column property="fdSubject" title="${ lfn:message('sys-log:sysLogJob.fdSubject') }">
		</list:data-column>
		<!-- 是否成功 -->
		<list:data-column headerClass="width100" col="fdSuccess" title="${ lfn:message('sys-log:sysLogJob.fdSuccess') }">
			<sunbor:enumsShow value="${sysLogJob.fdSuccess}" enumsType="common_yesno" />
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/log/sys_log_job/sysLogJob.do?method=deleteall" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${HtmlParam.isBak}','${sysLogJob.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>