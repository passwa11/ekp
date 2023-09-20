<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<script>
	if(typeof Com_Parameter.__sysAttMainlocale__ == "undefined")
		Com_Parameter.__sysAttMainlocale__= "<%= UserUtil.getKMSSUser(request).getLocale().toString().toLowerCase().replace('_', '-') %>";
</script>

<c:import url="/sys/anonym/dataview/attachment/sysAttMain_view_swf.jsp" charEncoding="UTF-8">
	<c:param name="formBeanName" value="${ param.formBeanName }" />	
	<c:param name="fdModelName" value="${ param.fdModelName }" />
	<c:param name="fdModelId" value="${ param.fdModelId }" />
	<c:param name="fdMulti" value="${ param.fdMulti }" />
	<c:param name="fdAttType" value="${ param.fdAttType }" />
	<c:param name="fdViewType" value="${ param.fdViewType }" />
	<c:param name="fdImgHtmlProperty" value="${ param.fdImgHtmlProperty }" />
	<c:param name="fdForceDisabledOpt" value="${ param.fdForceDisabledOpt }" />
	<c:param name="fdShowMsg" value="${ param.fdShowMsg }" />
	<c:param name="showDefault" value="${ param.showDefault }" />
	<c:param name="buttonDiv" value="${ param.buttonDiv }" />
	<c:param name="isTemplate" value="${ param.isTemplate }" />
	<c:param name="fdKey" value="${ param.fdKey }" />
	<c:param name="fdOfficeType" value="${param.fdOfficeType}" />
	<c:param name="bookMarks" value="${param.bookMarks}" />
	<c:param name="width" value="${param.picWidth}" />
	<c:param name="height" value="${param.picHeight}" />
	<c:param name="fdLayoutType" value="${param.fdLayoutType}" />	
	<c:param name="fdPicContentWidth" value="${param.fdPicContentWidth}" />	
	<c:param name="fdPicContentHeight" value="${param.fdPicContentHeight}" />
	<c:param name="fdUID" value="${param.fdUID}" />
	<c:param name="isShowDownloadCount" value="${param.isShowDownloadCount}" />
	<c:param name="canDownload" value="${param.canDownload}" />	
	<c:param name="fdLabel" value="${ param.fdLabel }" />
	<c:param name="fdGroup" value="${ param.fdGroup }" />	
	<c:param name="addToPreview" value="${ param.addToPreview }" />
</c:import>
