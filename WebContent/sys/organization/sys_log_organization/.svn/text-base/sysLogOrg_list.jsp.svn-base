<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysLogOrg" list="${queryPage.list }" varIndex="status">
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		<%--序号--%>
		<list:data-column col="index">${status+1 }</list:data-column>
		
		<list:data-column property="fdCreateTime" title="${ lfn:message('sys-log:sysLogApp.fdCreateTime') }" style="text-align:center;width:100px" />
		<list:data-column property="fdIp" title="${ lfn:message('sys-log:sysLogApp.fdIp') }" style="text-align:center;width:100px" />
		<list:data-column property="fdBrowser" title="${ lfn:message('sys-log:sysLogOrganization.fdBrowser') }" style="text-align:center;width:90px" />
		<list:data-column property="fdEquipment" title="${ lfn:message('sys-log:sysLogOrganization.fdEquipment') }" style="text-align:center;width:130px" />
		<list:data-column property="fdOperator" title="${ lfn:message('sys-log:sysLogApp.fdOperator') }" style="text-align:center;width:90px" />
		<list:data-column col="fdParaMethod" title="${ lfn:message('sys-log:sysLogApp.fdParaMethod') }" style="text-align:center;width:100px" >
		<% try{ %>
			<bean:message key="button.${sysLogOrg.fdParaMethod}"/>
		<% }catch(Exception e){ %>
				<% try{ %>
					<bean:message bundle="sys-log" key="sysLogOrganization.${sysLogOrg.fdParaMethod}"/>
				<%}catch(Exception ex){ %>
					<bean:write name="sysLogOrg" property="fdParaMethod"/>
				<% } %>
		<% } %>
		</list:data-column>
		<list:data-column property="fdDetails" title="${ lfn:message('sys-log:sysLogOrganization.fdDetails') }" style="text-align:left;width:400px" />
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>