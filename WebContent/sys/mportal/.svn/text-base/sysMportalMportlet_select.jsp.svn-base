<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="item" list="${queryPage.list}">
		<list:data-column property="id" col="fdPortletId" title="ID">
		</list:data-column>
		<list:data-column  title="${lfn:message('sys-mportal:sysMportalPortal.fdName') }"  col="fdName" property="name" style="text-align:left;width:30%;min-width:180px">
		</list:data-column>

		<list:data-column col="fdModuleName" headerClass="width140" title="${lfn:message('sys-mportal:sysMportalPortal.fdModelName') }" escape="false">
			${lfn:msg(item.fdModule)} 
		</list:data-column>
		<list:data-column col="description" title="${lfn:message('sys-mportal:sysMportalPortal.fdDescription') }">
			${lfn:msg(item.description)}
		</list:data-column>

	</list:data-columns>
	<list:data-paging page="${queryPage }" >
	</list:data-paging>
</list:data>