<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysTimeArea" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 区域组名称 -->
		<list:data-column property="fdName" title="${ lfn:message('sys-time:sysTimeArea.fdName') }">
		</list:data-column>
		<!-- 范围 -->
		<list:data-column style="word-break: break-all;" col="scope" title="${ lfn:message('sys-time:sysTimeArea.scope') }" >
			<kmss:joinListProperty value="${sysTimeArea.areaMembers}" properties="fdName" split=";"/>

		</list:data-column>
		<!-- 时间维护人 -->
		<list:data-column headerClass="width100" styleClass="lui_timearea_column_td" col="timeAdmin" title="${ lfn:message('sys-time:sysTimeArea.timeAdmin') }">
			<kmss:joinListProperty value="${sysTimeArea.areaAdmins}" properties="fdName" split=";"/>
		</list:data-column>
		<!-- 创建人 -->
		<list:data-column headerClass="width100" col="docCreator" title="${ lfn:message('model.fdCreator') }">
			${sysTimeArea.docCreator.fdName}
		</list:data-column>
		<!-- 创建时间 -->
		<list:data-column headerClass="width100" col="docCreateTime" title="${ lfn:message('sys-time:sysTimeArea.docCreateTime') }">
			<kmss:showDate value="${sysTimeArea.docCreateTime}" type="datetime"/>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/time/sys_time_area/sysTimeArea.do?method=edit&fdId=${sysTimeArea.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysTimeArea.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/time/sys_time_area/sysTimeArea.do?method=edit&fdId=${sysTimeArea.fdId}" requestMethod="GET">
						<a class="btn_txt" href="javascript:onDoScheduling('${sysTimeArea.fdId}')">${lfn:message('sys-time:sysTimeArea.button.workEdit')}</a>
						<a class="btn_txt" href="javascript:doViewSchedule('${sysTimeArea.fdId}')">${lfn:message('sys-time:sysTimeArea.button.workView')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/time/sys_time_area/sysTimeArea.do?method=delete&fdId=${sysTimeArea.fdId}" requestMethod="GET">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${sysTimeArea.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>