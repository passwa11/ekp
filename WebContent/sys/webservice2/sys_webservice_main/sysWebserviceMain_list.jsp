<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysWebserviceMain" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  property="fdName" title="${lfn:message('sys-webservice2:sysWebserviceMain.fdName')}" style="text-align:left;min-width:180px">
		</list:data-column>
		<list:data-column headerClass="width200" property="fdServiceBean" title="${lfn:message('sys-webservice2:sysWebserviceMain.fdServiceBean') }">
		</list:data-column>
		<list:data-column headerClass="width80" col="fdServiceStatus" title="${lfn:message('sys-webservice2:sysWebserviceMain.fdServiceStatus') }" escape="false">
 					<c:if test="${sysWebserviceMain.fdServiceStatus == 1}"> 
                        <bean:message bundle="sys-webservice2" key="sysWebserviceMain.fdServiceStatus.start"/>
                    </c:if> 
			        <c:if test="${sysWebserviceMain.fdServiceStatus == 0}">
                        <bean:message bundle="sys-webservice2" key="sysWebserviceMain.fdServiceStatus.stop"/>
                    </c:if> 
		</list:data-column>
		<list:data-column headerClass="width80" col="fdStartupType" title="${lfn:message('sys-webservice2:sysWebserviceMain.fdStartupType') }" escape="false">
		    <c:if test="${sysWebserviceMain.fdStartupType == '0'}"> 
                <bean:message bundle="sys-webservice2" key="sysWebserviceMain.fdStartupType.auto"/>
            </c:if> 
	        <c:if test="${sysWebserviceMain.fdStartupType == '1'}"> 
                <bean:message bundle="sys-webservice2" key="sysWebserviceMain.fdStartupType.manual"/>
            </c:if> 					
	        <c:if test="${sysWebserviceMain.fdStartupType == '2'}">
                <bean:message bundle="sys-webservice2" key="sysWebserviceMain.fdStartupType.disable"/>
            </c:if> 
		</list:data-column>
		<list:data-column headerClass="width180" col="operations" title="${lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<c:if test="${sysWebserviceMain.fdServiceStatus=='0'}">
						<kmss:auth requestURL="/sys/webservice2/sys_webservice_main/sysWebserviceMain.do?method=start&fdId=${sysWebserviceMain.fdId}" requestMethod="GET">
							<a class="btn_txt" href="javascript:startAll('${sysWebserviceMain.fdId}')">${lfn:message('sys-webservice2:button.startservice')}</a>
						</kmss:auth>
					</c:if>
					<c:if test="${sysWebserviceMain.fdServiceStatus=='1'}">
						<kmss:auth requestURL="/sys/webservice2/sys_webservice_main/sysWebserviceMain.do?method=stop&fdId=${sysWebserviceMain.fdId}" requestMethod="GET">
							<a class="btn_txt" href="javascript:stopAll('${sysWebserviceMain.fdId}')">${lfn:message('sys-webservice2:button.stopservice')}</a>
						</kmss:auth>
					</c:if>
					<kmss:auth requestURL="/sys/webservice2/sys_webservice_main/sysWebserviceMain.do?method=edit&fdId=${sysWebserviceMain.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysWebserviceMain.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>