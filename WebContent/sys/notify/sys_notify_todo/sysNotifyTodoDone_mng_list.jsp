<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysNotifyTodo" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 标题 -->
		<list:data-column headerClass="width300" col="fdSubject" title="${ lfn:message('sys-notify:sysNotifyTodo.fdSubject') }" escape="false" style="text-align: left;">
			<span id="notify_content_${sysNotifyTodo.fdType}"></span> ${lfn:escapeHtml(sysNotifyTodo.fdSubject)}
		</list:data-column>
		<c:if test="${showApp == 1}">
		<!-- 待办来源 -->
		<list:data-column headerClass="width100" col="fdAppName" title="${ lfn:message('sys-notify:sysNotifyTodo.fdAppName') }">
			<c:set var="appName" value="${sysNotifyTodo.fdAppName}"/>
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
			<sunbor:enumsShow value="${sysNotifyTodo.fdType}" enumsType="sys_todo_cate" bundle="sys-notify"/>
		</list:data-column>
		</c:if>
		<!-- 接收时间 -->
		<list:data-column headerClass="width120" col="fdCreateTime" title="${ lfn:message('sys-notify:sysNotifyTodo.fdCreateDate') }">
			<kmss:showDate value="${sysNotifyTodo.fdCreateTime}" type="datetime" />
		</list:data-column>
		<!-- 创建时间 -->
		<list:data-column headerClass="width120" col="finishDate" title="${ lfn:message('sys-notify:sysNotifyTodo.finishDate') }">
			<kmss:showDate value="${sysNotifyTodo.finishTime}" type="datetime" />
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width80" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<c:if test="${empty owner || owner == 'true'}">
					<kmss:auth requestURL="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=deleteall" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:__del('deleteall','1','${sysNotifyTodo.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
					</c:if>
					<c:if test="${not empty oprType && owner == 'false'}">
					<kmss:auth requestURL="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngDelete" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:__del('mngDelete','1','${sysNotifyTodo.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
					</c:if>
					<a class="btn_txt" href="javascript:setNotifySelfTitle('1','${sysNotifyTodo.fdId}')"> 自定义消息</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>