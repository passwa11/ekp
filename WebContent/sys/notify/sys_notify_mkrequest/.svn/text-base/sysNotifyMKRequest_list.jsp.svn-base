<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysNotifyMKRequest" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 排序号 -->
		<list:data-column headerClass="width300" property="fdTraceId" title="${ lfn:message('sys-notify:sysNotifyMKRequest.fdTraceId') }">
		</list:data-column>
		<list:data-column headerClass="width30" col="fdSuccess" title="${ lfn:message('sys-notify:sysNotifyMKRequest.fdSuccess') }">
			 <c:if test="${sysNotifyMKRequest.fdSuccess=='true'}">
				成功
			 </c:if>
			<c:if test="${sysNotifyMKRequest.fdSuccess=='false'}">
				失败
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width300" property="fdModelId" title="modelId">
		</list:data-column>
		<list:data-column headerClass="width300" property="fdModelName" title="modelName">
		</list:data-column>
		<list:data-column headerClass="width300" property="fdKey" title="modelKey">
		</list:data-column>
		<!-- 名称 -->
		<list:data-column property="fdCreateTime" title="${ lfn:message('sys-notify:sysNotifyMKRequest.fdCreateTime') }">
		</list:data-column>
		<list:data-column property="fdUrl" title="${ lfn:message('sys-notify:sysNotifyMKRequest.fdUrl') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/notify/sys_notify_mkrequest/sysNotifyMKRequest.do?method=view&fdId=${sysNotifyMKRequest.fdId}" requestMethod="GET">
						<!-- 查看 -->
						<a class="btn_txt" href="javascript:view('${sysNotifyMKRequest.fdId}')">${lfn:message('button.view')}</a>
					</kmss:auth>
					<c:if test="${sysNotifyMKRequest.fdSuccess=='false'}">
						<kmss:auth requestURL="/sys/notify/sys_notify_mkrequest/sysNotifyMKRequest.do?method=retry&fdId=${sysNotifyMKRequest.fdId}" requestMethod="GET">
							<!-- 重发 -->
							<a class="btn_txt" href="javascript:retry('${sysNotifyMKRequest.fdId}')">重发</a>
						</kmss:auth>
					</c:if>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>