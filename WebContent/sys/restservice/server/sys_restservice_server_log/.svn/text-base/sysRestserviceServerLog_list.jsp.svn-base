<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysRestserviceServerLog" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  property="fdName" title="${lfn:message('sys-restservice-server:sysRestserviceServerLog.fdName')}" style="text-align:left;min-width:180px">
		</list:data-column>
		<list:data-column  property="fdServiceName" title="${lfn:message('sys-restservice-server:sysRestserviceServerLog.fdServiceName')}" >
		</list:data-column>
		<list:data-column  property="fdUserName" title="${lfn:message('sys-restservice-server:sysRestserviceServerLog.fdUserName')}" >
		</list:data-column>
		<list:data-column  property="fdClientIp" title="${lfn:message('sys-restservice-server:sysRestserviceServerLog.fdClientIp')}" >
		</list:data-column>
		<list:data-column  col="fdStartTime" title="${lfn:message('sys-restservice-server:sysRestserviceServerLog.fdStartTime')}" escape="false">
		   <kmss:showDate value="${sysRestserviceServerLog.fdStartTime}" />
		</list:data-column>
		<list:data-column  col="fdEndTime" title="${lfn:message('sys-restservice-server:sysRestserviceServerLog.fdEndTime')}" escape="false" >
		   <kmss:showDate value="${sysRestserviceServerLog.fdStartTime}" />
		</list:data-column>
		<list:data-column  col="fdExecResult" title="${lfn:message('sys-restservice-server:sysRestserviceServerLog.fdExecResult')}" >
			<sunbor:enumsShow value="${sysRestserviceServerLog.fdExecResult}" bundle="sys-restservice-server" enumsType="sys_restservice_server_log_fd_exec_result"/>
		</list:data-column>
		<list:data-column headerClass="width140" col="operations" title="${lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 开启三员后不能删除 -->
					<% if(!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
					<kmss:auth requestURL="/sys/restservice/server/sys_restservice_server_log/sysRestserviceServerLog.do?method=delete&fdId=${sysRestserviceServerMain.fdId}" requestMethod="GET">
						<a class="btn_txt" href="javascript:deleteAll('${sysRestserviceServerLog.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>	
					<% } %>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>
