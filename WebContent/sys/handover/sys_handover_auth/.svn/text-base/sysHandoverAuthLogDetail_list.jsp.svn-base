<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<list:data>
	<list:data-columns var="logDetail" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId"></list:data-column>
		<list:data-column col="index">
		     ${status+1}
		</list:data-column>
		<!--标题-->
		<list:data-column col="subject" title="${ lfn:message('sys-handover:sysHandoverConfigAuthLogDetail.fdModelSubject') }"
			escape="false" style="text-align:left;min-width:200px">
	    	<a href="<c:url value='${logDetail.fdModelUrl}'/>" target="_blank"><span class="com_subject">${logDetail.fdModelSubject}</span></a>
		</list:data-column>
		<!--模块-->
		<list:data-column col="fdModelName" title="${ lfn:message('sys-handover:sysHandoverConfigLog.fdModuleName') }">
	    	<kmss:message key="${logDetail.fdModelMessageKey}"/>
		</list:data-column>
		<!--交接人-->
		<list:data-column headerStyle="width:8%" property="fdMain.fdFromName"
			title="${ lfn:message('sys-handover:sysHandoverConfigMain.fdFromName') }">
		</list:data-column>
		<!--接收人-->
		<list:data-column headerStyle="width:8%" property="fdMain.fdToName"
			title="${ lfn:message('sys-handover:sysHandoverConfigMain.fdToName') }">
		</list:data-column>
		<!--创建人-->
		<list:data-column headerStyle="width:8%" property="fdMain.docCreator.fdName"
			title="${ lfn:message('sys-handover:sysHandoverConfigMain.docCreatorId') }">
		</list:data-column>
		<!--创建时间-->
		<list:data-column headerStyle="width:130px" col="docCreateTime"
			title="${ lfn:message('sys-handover:sysHandoverConfigMain.docCreateTime') }">
			<kmss:showDate type="datetime" value="${logDetail.docCreateTime}" />
		</list:data-column>
		<!--交接类型-->
		<list:data-column headerStyle="width:130px" col="fdAuthType"
			title="${ lfn:message('sys-handover:sysHandoverConfigAuthLogDetail.fdAuthType') }">
			<kmss:message key="sysHandoverDocAuth.${logDetail.fdAuthType}" bundle="sys-handover-support-config-auth"/>
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>