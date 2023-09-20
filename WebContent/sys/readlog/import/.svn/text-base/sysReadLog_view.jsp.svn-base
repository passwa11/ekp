<%@page import="com.landray.kmss.sys.attachment.service.ISysAttDownloadLogService"%>
<%@page import="com.landray.kmss.sys.print.service.ISysPrintLogService"%>
<%@page import="com.landray.kmss.sys.readlog.service.ISysReadLogService"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.util.ModelUtil"%>
<%@page import="com.landray.kmss.sys.print.interfaces.ISysPrintLogForm"%>
<%@page import="com.landray.kmss.common.forms.IExtendForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="sysLogForm" value="${requestScope[param.formName]}" />
<c:if test="${JsParam.enable ne 'false' && sysLogForm.readLogForm.fdIsShow=='true'}">
<%
	Integer count = Integer.valueOf(PropertyUtils.getProperty(pageContext.getAttribute("sysLogForm"),"readLogForm.fdReadCount").toString());
	IExtendForm mainForm = (IExtendForm)pageContext.getAttribute("sysLogForm");
	String fdModelId = mainForm.getFdId();
	String fdModelName = mainForm.getModelClass().getName();
	pageContext.setAttribute("fdModelId", fdModelId);
	pageContext.setAttribute("fdModelName", fdModelName);
//	Integer count =((ISysReadLogService)SpringBeanUtil.getBean("sysReadLogService")).getReadLogCount(fdModelId,fdModelName);
	Long printCount = ((ISysPrintLogService)SpringBeanUtil.getBean("sysPrintLogService")).getPrintCount(fdModelName, fdModelId);
	Long downloadCount = ((ISysAttDownloadLogService)SpringBeanUtil.getBean("sysAttDownloadLogService")).getDownloadCount(fdModelName, fdModelId);
	if(count==0){
		pageContext.setAttribute("__readlog__",ResourceUtil.getString("sys-readlog:sysReadLog.readRecord"));
	}else{
		pageContext.setAttribute("__readlog__",ResourceUtil.getString("sys-readlog:sysReadLog.readRecord")+"("+count+")");
	}
	if(printCount==0){
		pageContext.setAttribute("__printRecord__",ResourceUtil.getString("sys-readlog:sysReadLog.printRecord"));
	}else{
		pageContext.setAttribute("__printRecord__",ResourceUtil.getString("sys-readlog:sysReadLog.printRecord")+"("+printCount+")");
	}
	if(downloadCount==0){
		pageContext.setAttribute("__attDownloadRecord__",ResourceUtil.getString("sys-readlog:sysReadLog.attDownloadRecord"));
	}else{
		pageContext.setAttribute("__attDownloadRecord__",ResourceUtil.getString("sys-readlog:sysReadLog.attDownloadRecord")+"("+downloadCount+")");
	}
	pageContext.setAttribute("__accessStatistics__",ResourceUtil.getString("sys-readlog:sysReadLog.accessStatistics"));
%> 
	
	<c:choose>
		<c:when test="${param.isTab eq 'true' }">
			<script>
				function readLog_LoadIframe(){
					showTab();
				}
			</script>
			<tr LKS_LabelName="${__accessStatistics__ }" style="display:none" >
				<td onresize="readLog_LoadIframe();" valign="top">
					<table class="tb_normal" width="100%" ${HtmlParam.styleValue}>
						<tr>
							<td>
								<%@include file="/sys/readlog/import/sysReadLog_view_include.jsp" %>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</c:when>
		<c:otherwise>
			<c:set var="order" value="${empty param.order ? '65' : param.order}"/>
			<c:set var="disable" value="${empty param.disable ? 'false' : param.disable}"/>
			<c:set var="expand" value="${empty param.expand ? 'false' : param.expand}"/>
			<ui:content title="${__accessStatistics__ }" titleicon="${not empty param.titleicon?param.titleicon:''}" cfg-order="${order}" cfg-disable="${disable}" expand="${expand }">
				<ui:event event="show">
					showTab();
				</ui:event>
				<%@include file="/sys/readlog/import/sysReadLog_view_include.jsp" %>
			</ui:content>
		</c:otherwise>
	</c:choose>
</c:if>


