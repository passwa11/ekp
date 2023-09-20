<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysLogFaileJob" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 通知标题 -->
		<list:data-column headerClass="width300" property="docSubject" title="${ lfn:message('sys-log:sysLogFaileJob.docSubject') }">
		</list:data-column>
		<!-- 通知对象 -->
		<list:data-column headerClass="width100" property="fdNotifyTargets" title="${ lfn:message('sys-log:sysLogFaileJob.fdNotifyTargets') }">
		</list:data-column>
		<!-- 通知类型 -->
		<list:data-column headerClass="width200" property="fdNotifyType" title="${ lfn:message('sys-log:sysLogFaileJob.fdNotifyType') }">
		</list:data-column>
		<!-- 通知时间 -->
		<list:data-column headerClass="width100" col="docCreateTime" title="${ lfn:message('sys-log:sysLogFaileJob.docCreateTime') }">
			<kmss:showDate type="datetime" value="${sysLogFaileJob.docCreateTime}"/>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/log/sys_log_faile_job/sysLogFaileJob.do?method=deleteall" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${sysLogFaileJob.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>