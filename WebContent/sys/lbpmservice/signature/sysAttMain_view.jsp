<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.attachment.util.AttImageUtils"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<!-- #132398-流程导出PDF报错，初步判断是因为签字图片跨域问题导致-开始 -->
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<!-- #132398-流程导出PDF报错，初步判断是因为签字图片跨域问题导致-结束 -->
<style>
.tab_img{ width:100%; zoom:1;}
.tab_img:after{ display:block; clear:both; visibility:hidden; line-height:0px; content:"clear";}
</style>
<%
	String formBeanName = request.getParameter("formBeanName");
	String attKey = request.getParameter("fdKey");
	Object formBean = null;
	if(formBeanName != null && formBeanName.trim().length()!= 0){
		formBean = pageContext.getAttribute(formBeanName);
		if(formBean == null)
			formBean = request.getAttribute(formBeanName);
		if(formBean == null)
			formBean = session.getAttribute(formBeanName);
	}
	//pageContext.setAttribute("_downLoadNoRight",new PdaRowsPerPageConfig().getFdAttDownload());
	pageContext.setAttribute("_formBean", formBean);
	/* #132398-流程导出PDF报错，初步判断是因为签字图片跨域问题导致-开始 */
	String location = ResourceUtil.getKmssConfigString("sys.att.location");
	if("server".equals(location)){
		location = "false";
	}else if("aliyun".equals(location)){
		location = "false";
	}else{
		location = "false";
	}
	pageContext.setAttribute("location", location); 
	/* #132398-流程导出PDF报错，初步判断是因为签字图片跨域问题导致-结束 */
%>
<c:set var="attForms" value="${_formBean.attachmentForms[param.fdKey]}" />
<c:set var="msgkey" value="${param.msgkey}"/>
<c:if test="${attForms!=null && fn:length(attForms.attachments)>0}">
	<p class="tab_img muiLbpmserviceSignature">
	<c:forEach items="${attForms.attachments}" varStatus="vsStatus">
		<c:if test="${attForms.attachments[fn:length(attForms.attachments)-1-vsStatus.index].fdAttType=='pic'}">
				<img style="float: right"  height="60" src='<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${attForms.attachments[fn:length(attForms.attachments)-1-vsStatus.index].fdId}&isSupportDirect=${location}"/>' ></img>
		</c:if>
	</c:forEach>
	</p>
</c:if>
