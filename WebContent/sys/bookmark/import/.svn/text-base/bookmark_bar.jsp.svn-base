<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<c:if test="${JsParam.enable ne 'false'}">
	<script src='${ KMSS_Parameter_ContextPath }sys/bookmark/import/bookmark.js'></script>
	<script type="text/javascript">
	
	LUI.ready(function(){
		var url = GetBookmarkUrl();
		CheckBookedInAllCate(url); 
	  });
	
	</script>
	
	<c:set var="toolbarOrder" value="4"></c:set>
	<c:if test="${ not empty param && not empty param.toolbarOrder }">
		<c:set var="toolbarOrder" value="${param.toolbarOrder}"></c:set>
	</c:if>
	
	<ui:button id="bookedBtn" order="${toolbarOrder}" parentId="toolbar" text="${ lfn:message('sys-bookmark:sysBookmark.mechanism.bookMark') }" style="display:none" onclick="openBookDia();">
	</ui:button> 
	<ui:button id="delbookedBtn" order="${toolbarOrder}" parentId="toolbar" text="${ lfn:message('sys-bookmark:sysBookmark.mechanism.cancel') }" style="display:none" onclick="deleteBookedMark(GetBookmarkUrl());">
	</ui:button> 
	
	<%@ include file="/sys/bookmark/import/bookmark_script.jsp" %>
</c:if>