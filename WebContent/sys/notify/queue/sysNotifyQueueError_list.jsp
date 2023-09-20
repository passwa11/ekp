<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil" %>
<%@ page import="com.landray.kmss.sys.log.util.ParseOperContentUtil" %>	
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysNotifyQueueError" list="${queryPage.list }">
		<list:data-column col="fdId">
			${sysNotifyQueueError.fdId}
			<% com.landray.kmss.sys.notify.queue.util.NotifyQueueUtil.buildMessage(pageContext); %>
		</list:data-column>
		<!-- 标题 -->
		<list:data-column headerClass="width150" col="title" title="${ lfn:message('sys-notify:sysNotifyQueueError.list.doc.title') }">
		<% if(!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
			${docSubject}
		<% } else {
			String docSubject = (String)pageContext.getAttribute("docSubject");
			out.append(ParseOperContentUtil.hideDisplayName(docSubject));
		} %>
		</list:data-column>
		<!-- 模块 -->
		<list:data-column headerClass="width100" col="modelText" title="${ lfn:message('sys-notify:sysNotifyQueueError.fdModelName.text') }">
			${modelTitle}
		</list:data-column>
		<!-- 消息类型 -->
		<list:data-column headerClass="width80" property="fdType" title="${ lfn:message('sys-notify:sysNotifyQueueError.fdType') }">
		</list:data-column>
		<!-- 方法类型 -->
		<list:data-column headerClass="width80" property="fdMethodType" title="${ lfn:message('sys-notify:sysNotifyQueueError.fdMethodType') }">
		</list:data-column>
		<!-- 操作时间 -->
		<list:data-column headerClass="width120" col="fdTime" title="${ lfn:message('sys-notify:sysNotifyQueueError.fdTime') }">
			${operTime}
		</list:data-column>
		<!-- 操作者 -->
		<list:data-column headerClass="width100" col="fdUser" title="${ lfn:message('sys-notify:sysNotifyQueueError.fdUserId') }">
			${personName}
		</list:data-column>
		<!-- 开启三员后，系统管理员不可查看标题和具体文档-->
		<% if(!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
		<!-- 主文档 -->
		<list:data-column headerClass="width80" col="doc" title="${ lfn:message('sys-notify:sysNotifyQueueError.list.doc') }" escape="false">
			<a href="${docLink}" style="text-decoration:underline;" target="_blank"><bean:message bundle="sys-notify" key="sysNotifyQueueError.list.doc.link"/></a>
		</list:data-column>
		<% } %>
		<!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/notify/queue/sysNotifyQueueError.do?method=runAll" requestMethod="POST">
						<!-- 运行 -->
						<a class="btn_txt" href="javascript:run('${sysNotifyQueueError.fdId}')">${lfn:message('sys-notify:sysNotifyQueueError.run')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/notify/queue/sysNotifyQueueError.do?method=deleteall" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${sysNotifyQueueError.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>