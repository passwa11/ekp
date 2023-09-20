<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysTimeHoliday" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		
		<list:data-column property="fdName" title="${ lfn:message('sys-time:sysTimeHoliday.fdName') }">
		</list:data-column>
		<list:data-column property="docCreateTime" title="${ lfn:message('sys-time:sysTimeHoliday.docCreateTime') }">
		</list:data-column>
		<list:data-column col="docCreator.fdName" title="${ lfn:message('sys-time:sysTimeHoliday.docCreator') }">
			<c:out value="${sysTimeHoliday.docCreator.fdName}" />
		</list:data-column>
			<!-- 其它操作 -->
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/time/sys_time_holiday/sysTimeHoliday.do?method=edit&fdId=${sysTimeHoliday.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysTimeHoliday.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/time/sys_time_holiday/sysTimeHoliday.do?method=delete&fdId=${sysTimeHoliday.fdId}" requestMethod="GET">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${sysTimeHoliday.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>