<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysRestserviceServerPolicy" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  col="fdName" title="${lfn:message('sys-restservice-server:sysRestserviceServerPolicy.fdName')}" escape="false" style="text-align:left;min-width:180px">
		   <c:out value="${(sysRestserviceServerPolicy.fdName==null)?(sysRestserviceServerPolicy.fdUserName):(sysRestserviceServerPolicy.fdName)}" />
		</list:data-column>
		<list:data-column headerClass="width200" col="fdService" escape="false" title="${lfn:message('sys-restservice-server:sysRestserviceServerPolicy.fdService') }"> 
		   <c:forEach items="${sysRestserviceServerPolicy.fdService}" var="sysRestserviceServerMain" varStatus="loop">
			    <c:if test="${loop.index==0}">
			        <c:out value="${sysRestserviceServerMain.fdName}" />
			    </c:if>
			    <c:if test="${loop.index>0}">
			        <c:out value="、${sysRestserviceServerMain.fdName}" />
			    </c:if>
			</c:forEach> 
		</list:data-column>
		<list:data-column headerClass="width80" property="docCreator.fdName" title="${lfn:message('sys-restservice-server:sysRestserviceServerPolicy.docCreator') }" escape="false">
		</list:data-column>
		<list:data-column headerClass="width80" property="docCreateTime" title="${lfn:message('sys-restservice-server:sysRestserviceServerPolicy.docCreateTime') }" escape="false">
		    <kmss:showDate value="${sysRestserviceServerPolicy.docCreateTime}" />
		</list:data-column>
		<list:data-column headerClass="width180" col="operations" title="${lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/restservice/server/sys_restservice_server_policy/sysRestserviceServerPolicy.do?method=edit&fdId=${sysRestserviceServerPolicy.fdId}" requestMethod="GET">
						<a class="btn_txt" href="javascript:edit('${sysRestserviceServerPolicy.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>	
					<kmss:auth requestURL="/sys/restservice/server/sys_restservice_server_policy/sysRestserviceServerPolicy.do?method=delete&fdId=${sysRestserviceServerPolicy.fdId}" requestMethod="GET">
						<a class="btn_txt" href="javascript:deleteAll('${sysRestserviceServerPolicy.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth> 
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>
