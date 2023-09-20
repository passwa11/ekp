<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysRestserviceServerMain" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  property="fdName" title="${lfn:message('sys-restservice-server:sysRestserviceServerMain.fdName')}" style="text-align:left;min-width:180px">
		</list:data-column>
		<list:data-column headerClass="width200" property="fdServiceName" title="${lfn:message('sys-restservice-server:sysRestserviceServerMain.fdServiceName') }">
		</list:data-column>
		<list:data-column headerClass="width80" col="fdServiceStatus" title="${lfn:message('sys-restservice-server:sysRestserviceServerMain.fdServiceStatus') }" escape="false">
 					<c:if test="${sysRestserviceServerMain.fdServiceStatus == 1}"> 
                        <bean:message bundle="sys-restservice-server" key="sysRestserviceServerMain.fdServiceStatus.start"/>
                    </c:if> 
			        <c:if test="${sysRestserviceServerMain.fdServiceStatus == 0}">
                        <bean:message bundle="sys-restservice-server" key="sysRestserviceServerMain.fdServiceStatus.stop"/>
                    </c:if> 
		</list:data-column>
		<list:data-column headerClass="width80" col="fdStartupType" title="${lfn:message('sys-restservice-server:sysRestserviceServerMain.fdStartupType') }" escape="false">
		    <c:if test="${sysRestserviceServerMain.fdStartupType == '0'}"> 
                <bean:message bundle="sys-restservice-server" key="sysRestserviceServerMain.fdStartupType.auto"/>
            </c:if> 
	        <c:if test="${sysRestserviceServerMain.fdStartupType == '1'}"> 
                <bean:message bundle="sys-restservice-server" key="sysRestserviceServerMain.fdStartupType.manual"/>
            </c:if> 					
	        <c:if test="${sysRestserviceServerMain.fdStartupType == '2'}">
                <bean:message bundle="sys-restservice-server" key="sysRestserviceServerMain.fdStartupType.disable"/>
            </c:if> 
		</list:data-column>
		<list:data-column headerClass="width180" col="operations" title="${lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<c:if test="${sysRestserviceServerMain.fdServiceStatus=='0'}">
						<kmss:auth requestURL="/sys/restservice/server/sys_restservice_server_main/sysRestserviceServerMain.do?method=start&fdId=${sysRestserviceServerMain.fdId}" requestMethod="GET">
							<a class="btn_txt" href="javascript:startAll('${sysRestserviceServerMain.fdId}')">${lfn:message('sys-restservice-server:button.startservice')}</a>
						</kmss:auth>
					</c:if>
					<c:if test="${sysRestserviceServerMain.fdServiceStatus=='1'}">
						<kmss:auth requestURL="/sys/restservice/server/sys_restservice_server_main/sysRestserviceServerMain.do?method=stop&fdId=${sysRestserviceServerMain.fdId}" requestMethod="GET">
							<a class="btn_txt" href="javascript:stopAll('${sysRestserviceServerMain.fdId}')">${lfn:message('sys-restservice-server:button.stopservice')}</a>
						</kmss:auth>
					</c:if>
					<kmss:auth requestURL="/sys/restservice/server/sys_restservice_server_main/sysRestserviceServerMain.do?method=edit&fdId=${sysRestserviceServerMain.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysRestserviceServerMain.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>
