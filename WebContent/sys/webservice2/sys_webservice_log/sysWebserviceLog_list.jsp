<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysWebserviceLog" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  property="fdServiceName" title="${lfn:message('sys-webservice2:sysWebserviceLog.fdServiceName')}" style="text-align:left;min-width:180px">
		</list:data-column>
		<list:data-column  property="fdServiceBean" title="${lfn:message('sys-webservice2:sysWebserviceLog.fdServiceBean')}" >
		</list:data-column>
		<list:data-column  property="fdUserName" title="${lfn:message('sys-webservice2:sysWebserviceLog.fdUserName')}" >
		</list:data-column>
		<list:data-column  property="fdClientIp" title="${lfn:message('sys-webservice2:sysWebserviceLog.fdClientIp')}" >
		</list:data-column>
		<list:data-column  col="fdStartTime" title="${lfn:message('sys-webservice2:sysWebserviceLog.fdStartTime')}" escape="false">
		   <kmss:showDate value="${sysWebserviceLog.fdStartTime}" />
		</list:data-column>
		<list:data-column  col="fdEndTime" title="${lfn:message('sys-webservice2:sysWebserviceLog.fdEndTime')}" escape="false" >
		   <kmss:showDate value="${sysWebserviceLog.fdStartTime}" />
		</list:data-column>
		<list:data-column  col="fdExecResult" title="${lfn:message('sys-webservice2:sysWebserviceLog.fdExecResult')}" >
		    <c:if test="${sysWebserviceLog.fdExecResult == '0'}"> 
                  <bean:message bundle="sys-webservice2" key="sysWebserviceLog.fdExecResult.success"/>
            </c:if> 
	        <c:if test="${sysWebserviceLog.fdExecResult == '1'}"> 
                  <bean:message bundle="sys-webservice2" key="sysWebserviceLog.fdExecResult.occur.exception"/>
            </c:if> 					
	        <c:if test="${sysWebserviceLog.fdExecResult == '2'}"> 
                <bean:message bundle="sys-webservice2" key="sysWebserviceLog.fdExecResult.unauthorized.user"/>
            </c:if> 	
	        <c:if test="${sysWebserviceLog.fdExecResult == '3'}"> 
                 <bean:message bundle="sys-webservice2" key="sysWebserviceLog.fdExecResult.illegal.ip"/>
             </c:if>   
	        <c:if test="${sysWebserviceLog.fdExecResult == '4'}"> 
                 <bean:message bundle="sys-webservice2" key="sysWebserviceLog.fdExecResult.locked.user"/>
             </c:if> 
             <c:if test="${sysWebserviceLog.fdExecResult == '5'}"> 
                 <bean:message bundle="sys-webservice2" key="sysWebserviceLog.fdExecResult.body.size.expire"/>
             </c:if> 
		</list:data-column>
		<list:data-column headerClass="width140" col="operations" title="${lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 开启三员后不能删除 -->
					<% if(!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
					<kmss:auth requestURL="/sys/webservice2/sys_webservice_log/sysWebserviceLog.do?method=delete&fdId=${sysWebserviceMain.fdId}" requestMethod="GET">
						<a class="btn_txt" href="javascript:deleteAll('${sysWebserviceLog.fdId}')">${lfn:message('button.delete')}</a>
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