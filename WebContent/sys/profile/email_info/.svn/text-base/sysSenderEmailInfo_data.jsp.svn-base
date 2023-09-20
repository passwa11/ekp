<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.profile.model.SysSenderEmailInfo" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<list:data>
	<list:data-columns var="senderEmailInfo" list="${queryPage.list}"
		varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column property="docSubject"
			title="${ lfn:message('sys-profile:sys.email.info.docSubject') }">
		</list:data-column>
		<list:data-column col="docCreator.fdName"
			title="${ lfn:message('sys-profile:sys.email.info.docCreator') }">
			<%
				Object basedocObj = pageContext.getAttribute("senderEmailInfo");
				if (basedocObj != null) {
					SysSenderEmailInfo senderEmailInfo = (SysSenderEmailInfo) basedocObj;
					out.print(senderEmailInfo.getDocCreator().getFdName());
				}
			%>
		</list:data-column>
		<list:data-column property="docCreateTime"
			title="${ lfn:message('sys-profile:sys.email.info.docCreateTime') }">
		</list:data-column>
		<list:data-column property="fdEmailUsername"
			title="${ lfn:message('sys-profile:sys.email.info.fdEmailUsername') }">
		</list:data-column>
		
	</list:data-columns>
	<list:data-paging page="${queryPage}" />
</list:data>
