<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysWebserviceUser" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  col="fdName" title="${lfn:message('sys-webservice2:sysWebserviceUser.fdName')}" escape="false" style="text-align:left;min-width:180px">
		   <c:out value="${(sysWebserviceUser.fdName==null)?(sysWebserviceUser.fdUserName):(sysWebserviceUser.fdName)}" />
		</list:data-column>
		<list:data-column headerClass="width200" col="fdService" escape="false" title="${lfn:message('sys-webservice2:sysWebserviceUser.fdService') }"> 
		   <c:forEach items="${sysWebserviceUser.fdService}" var="sysWebserviceMain" varStatus="loop">
			    <c:if test="${loop.index==0}">
			        <c:out value="${sysWebserviceMain.fdName}" />
			    </c:if>
			    <c:if test="${loop.index>0}">
			        <c:out value="、${sysWebserviceMain.fdName}" />
			    </c:if>
			</c:forEach> 
		</list:data-column>
		<list:data-column headerClass="width80" property="docCreator.fdName" title="${lfn:message('sys-webservice2:sysWebserviceUser.docCreator') }" escape="false">
		</list:data-column>
		<list:data-column headerClass="width80" property="docCreateTime" title="${lfn:message('sys-webservice2:sysWebserviceUser.docCreateTime') }" escape="false">
		    <kmss:showDate value="${sysWebserviceUser.docCreateTime}" />
		</list:data-column>
		<list:data-column headerClass="width180" col="operations" title="${lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/webservice2/sys_webservice_user/sysWebserviceUser.do?method=edit&fdId=${sysWebserviceUser.fdId}" requestMethod="GET">
						<a class="btn_txt" href="javascript:edit('${sysWebserviceUser.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>	
					<kmss:auth requestURL="/sys/webservice2/sys_webservice_user/sysWebserviceUser.do?method=delete&fdId=${sysWebserviceUser.fdId}" requestMethod="GET">
						<a class="btn_txt" href="javascript:deleteAll('${sysWebserviceUser.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth> 
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>