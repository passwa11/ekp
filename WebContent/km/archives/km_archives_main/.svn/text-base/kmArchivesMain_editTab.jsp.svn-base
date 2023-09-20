<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.Calendar"%>
<%@ page import=" com.landray.kmss.util.ResourceUtil"%>
<template:replace name="content">
	<!-- 软删除部署 -->
	<c:import url="/sys/recycle/import/redirect.jsp">
		<c:param name="formBeanName" value="kmArchivesMainForm"></c:param>
	</c:import>
	<div class='lui_form_title_frame'>
		<div class='lui_form_subject'>
			${lfn:message('km-archives:table.kmArchivesMain')}</div>
		<div class='lui_form_baseinfo'></div>
	</div>
	<c:if test="${param.approveModel ne 'right'}">
		<form name="kmArchivesMainForm" method="post" action="${KMSS_Parameter_ContextPath}km/archives/km_archives_main/kmArchivesMain.do">
	</c:if>
	<html:hidden property="fdId" />
    <html:hidden property="docStatus" />
    <html:hidden property="method_GET" />
	<c:choose>
		<c:when test="${param.approveModel eq 'right'}">
			<c:import url="/km/archives/km_archives_main/kmArchivesMain_editContent.jsp" charEncoding="UTF-8">
 		 		<c:param name="contentType" value="xform" />
  			</c:import>
			<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="5" var-average='false' var-useMaxWidth='true'
				var-supportExpand="true" var-expand="true">
				<c:import url="/km/archives/km_archives_main/kmArchivesMain_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="approveModel" value="${param.approveModel}"></c:param>
	  			</c:import>
			</ui:tabpanel>
		</c:when>
		<c:otherwise>
			<c:import url="/km/archives/km_archives_main/kmArchivesMain_editContent.jsp" charEncoding="UTF-8">
 		 		<c:param name="contentType" value="xform"></c:param>
  			</c:import>
			<ui:tabpage expand="false" var-navwidth="90%">
	  			<c:import url="/km/archives/km_archives_main/kmArchivesMain_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="approveModel" value="${param.approveModel}"></c:param>
	  			</c:import>
			</ui:tabpage>
			</form>
		</c:otherwise>
	</c:choose>
	<%	
		int nowYear = Calendar.getInstance().get(Calendar.YEAR);
		int nowMonth = Calendar.getInstance().get(Calendar.MONTH);
		int nowDay = Calendar.getInstance().get(Calendar.DATE);
		pageContext.setAttribute("nowYear",nowYear);
		pageContext.setAttribute("nowMonth",nowMonth);
		pageContext.setAttribute("nowDay",nowDay);
		pageContext.setAttribute("dateFormatter",ResourceUtil.getString("date.format.date"));
	%>
	<script type="text/javascript">
		seajs.use([
			'km/archives/resource/js/dateUtil'], function(dateUtil) {
			window.changePeriod = function (rtn,obj){
				var fdFileDate = $('[name="fdFileDate"]').val();
				if(fdFileDate&&rtn){
					var d = dateUtil.parseDate(fdFileDate);
					var fdYear = d.getFullYear();
					var fdMonth = parseInt(d.getMonth());
					var fdDate = d.getDate();
					var dd = new Date(parseInt(fdYear)+parseInt(rtn),fdMonth+"",fdDate+"");
					var fdValidityDate = dateUtil.formatDate(dd,'${dateFormatter}');
					$('[name="fdValidityDate"]').val(fdValidityDate);
				}
				if (!rtn) {
					$('[name="fdValidityDate"]').val("");
				}
			};
			window.changeFileDate = function (rtn,obj){
				var fdPeriod  = $('[name="fdPeriod"]').val();
				var fdFileDate = rtn;
				if(fdFileDate&&fdPeriod){
					var d = dateUtil.parseDate(fdFileDate);
					var fdYear = d.getFullYear();
					var fdMonth = parseInt(d.getMonth());
					var fdDate = d.getDate();
					var dd = new Date(parseInt(fdYear)+parseInt(fdPeriod),fdMonth+"",fdDate+"");
					var fdValidityDate = dateUtil.formatDate(dd,'${dateFormatter}');
					$('[name="fdValidityDate"]').val(fdValidityDate);
				}
			}
		});
	</script>
</template:replace>
<c:if test="${param.approveModel eq 'right'}">
	<template:replace name="barRight">
		<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
			<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmArchivesMainForm" />
				<c:param name="fdKey" value="kmArchivesMain" />
				<c:param name="showHistoryOpers" value="true" />
				<c:param name="isExpand" value="true" />
				<c:param name="approveType" value="right" />
				<c:param name="approvePosition" value="right" />
			</c:import>
		</ui:tabpanel>
	</template:replace>
</c:if>