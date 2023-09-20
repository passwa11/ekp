<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.profile.model.SysListshowCategory"%>

<list:data>
	<list:data-columns var="sysListShowCategory" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="fdName" title="${ lfn:message('sys-profile:sysListshowCategory.fdName') }" escape="false">
			<%
				if(pageContext.getAttribute("sysListShowCategory")!=null){
					SysListshowCategory sysListShowCategory = (SysListshowCategory)pageContext.getAttribute("sysListShowCategory");
					String fdName = "";
					if(StringUtil.isNotNull(sysListShowCategory.getFdName())){
						fdName = ResourceUtil.getString(sysListShowCategory.getFdName());
						if(StringUtil.isNull(fdName)){
							fdName = sysListShowCategory.getFdName();
						}
					}
					request.setAttribute("fdName",fdName);
				}
			%>
			<span class="com_subject">
				<c:out value="${fdName}" />
			</span>
		</list:data-column>
		<list:data-column  property="fdModel" title="${ lfn:message('sys-profile:sysListshowCategory.fdModel') }">
		</list:data-column>
		<list:data-column  property="fdPage" title="${ lfn:message('sys-profile:sysListshowCategory.fdPage') }" escape="false">
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>