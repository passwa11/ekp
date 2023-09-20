<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.ui.plugin.SysUiPluginUtil"%>
<%@ page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@ page import="com.landray.kmss.sys.ui.xml.model.*"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysPortalPortlet" list="${ queryList }">
		<list:data-column property="fdId" title="ID">
		</list:data-column>
		<list:data-column property="fdName" title="${ lfn:message('sys-portal:sysPortalPage.fdName') }">
		</list:data-column>
		<c:if test="${not empty sysPortalPortlet.path}">
			<list:data-column col="fdThumb" title="${ lfn:message('sys-portal:sys.portal.thumbnail') }">
				${sysPortalPortlet.thumbPath}
			</list:data-column>
		</c:if>
		<c:if test="${empty sysPortalPortlet.path}">
			<list:data-column property="fdThumb" title="${ lfn:message('sys-portal:sys.portal.thumbnail') }">
			</list:data-column>
		</c:if>
	</list:data-columns> 
</list:data>