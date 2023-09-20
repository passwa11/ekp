<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysNotifySystemTodo" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 标题 -->
		<list:data-column headerClass="width300" col="fdSubject" title="${ lfn:message('sys-notify:sysNotifyTodo.fdSubject') }" escape="false" style="text-align: left;">
			<span id="notify_content_${sysNotifySystemTodo.fdType}"></span>  ${lfn:escapeHtml(sysNotifySystemTodo.fdSubject)}
		</list:data-column>
		<c:if test="${showApp == 1}">
		<!-- 待办来源 -->
		<list:data-column headerClass="width100" col="fdAppName" title="${ lfn:message('sys-notify:sysNotifyTodo.fdAppName') }">
			<c:set var="appName" value="${sysNotifySystemTodo.fdAppName}"/>
			<c:choose>
				<c:when test="${appName == null || appName == '' }">
					<bean:message bundle="sys-notify" key="sysNotifyTodo.todo.local.application.ekp.notify" />
				</c:when>
				<c:otherwise>
					<c:out value="${appName}"/>
				</c:otherwise>
			</c:choose>
		</list:data-column>
		</c:if>
		<c:if test="${fdType == null || fdType == ''}">
		<!-- 类型 -->
		<list:data-column headerClass="width100" col="fdType" title="${ lfn:message('sys-notify:sysNotifyTodo.fdType') }">
			<sunbor:enumsShow value="${sysNotifySystemTodo.fdType}" enumsType="sys_todo_cate" bundle="sys-notify"/>
		</list:data-column>
		</c:if>
		<!-- 接收时间 -->
		<list:data-column headerClass="width120" col="fdCreateTime" title="${ lfn:message('sys-notify:sysNotifyTodo.fdCreateDate') }">
			<kmss:showDate value="${sysNotifySystemTodo.fdCreateTime}" type="datetime" />
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width80" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<c:if test="${empty owner || owner == 'true'}">
					<kmss:auth requestURL="/sys/notify/sys_notify_todo/sysNotifySystemTodo.do?method=deleteall" requestMethod="POST">
						<!-- 置为已办事项 -->
						<a class="btn_txt" href="javascript:__del('deleteall','2','${sysNotifySystemTodo.fdId}')">${lfn:message('sys-notify:sysNotify.todo.finish')}</a>
					</kmss:auth>
					</c:if>
					<c:if test="${not empty oprType && owner == 'false'}">
					<kmss:auth requestURL="/sys/notify/sys_notify_todo/sysNotifySystemTodo.do?method=mngDelete" requestMethod="POST">
						<!-- 置为已办事项 -->
						<a class="btn_txt" href="javascript:__del('mngDelete','2','${sysNotifySystemTodo.fdId}')">${lfn:message('sys-notify:sysNotify.todo.finish')}</a>
					</kmss:auth>
					</c:if>
					<kmss:auth requestURL="/sys/notify/sys_notify_lang/sysNotifyLang.do?method=selfTitleLang" requestMethod="POST">
						<a class="btn_txt" href="javascript:setNotifySelfTitle('2','${sysNotifySystemTodo.fdId}')"> ${ lfn:message('sys-notify:sysNotifySelfTitleSetting.self.notify') }</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>