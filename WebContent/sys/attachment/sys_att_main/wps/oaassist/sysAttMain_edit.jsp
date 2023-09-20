<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.web.taglib.TagUtils,com.landray.kmss.sys.attachment.forms.*,java.util.*"%>
<%@ page import="com.landray.kmss.sys.attachment.service.*"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="java.util.regex.Pattern"%>
<%@ page import="java.util.regex.Matcher"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@ page import="org.apache.commons.io.FilenameUtils" %>
<%@ page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsoaassistUtil" %>
<c:set var="wpsoaassistEmbed" value="<%=SysAttWpsoaassistUtil.isWPSOAassistEmbed()%>"/>
<%
	String formBeanName = request.getParameter("formBeanName");
	Object formBean = null;
	if(formBeanName != null && formBeanName.trim().length() != 0){
		formBean = pageContext.getAttribute(formBeanName);
		if(formBean == null){
			formBean = request.getAttribute(formBeanName);
		}
		if(formBean == null){
			formBean = session.getAttribute(formBeanName);
		}
		pageContext.setAttribute("_formBean", formBean);
	}else{
		formBeanName = "com.landray.kmss.web.taglib.FormBean";
	}
	Object originFormBean = pageContext.getAttribute("com.landray.kmss.web.taglib.FormBean");
	pageContext.setAttribute("com.landray.kmss.web.taglib.FormBean", formBean);
	if(formBean == null){
		formBean = com.landray.kmss.web.taglib.TagUtils.getInstance().lookup(pageContext,
				formBeanName, null);
		pageContext.setAttribute("_formBean", formBean);
	}


%>
<c:set var="fdKey" value="${param.fdKey}" />
<c:set var="fdModelId" value="${param.fdModelId}" />
<c:set var="fdModelName" value="${param.fdModelName}" />
<c:set var="attForms" value="${_formBean.attachmentForms[fdKey]}" />
<c:set var="attachmentId" value=""/>
<c:set var="attachmentFileName" value=""/>
<c:forEach items="${attForms.attachments}" var="sysAttMain"	varStatus="vstatus">
	<c:set var="attachmentId" value="${sysAttMain.fdId}"/>
	<c:set var="attachmentFileName" value="${sysAttMain.fdFileName}"/>
</c:forEach>

<%
	Boolean isWindows = Boolean.FALSE;
	if("windows".equals(JgWebOffice.getOSType(request))){
		isWindows = Boolean.TRUE;
	}
	request.setAttribute("isWindows", isWindows);
	String ext = FilenameUtils.getExtension((String)pageContext.getAttribute("attachmentFileName"));
	Boolean isUseWpsLinux = Boolean.FALSE;
	if ("doc".equals(ext.toLowerCase())||"docx".equals(ext.toLowerCase())||"wps".equals(ext.toLowerCase())||"xls".equals(ext.toLowerCase())||"xlsx".equals(ext.toLowerCase())||"et".equals(ext.toLowerCase())||"ppt".equals(ext.toLowerCase())||"pptx".equals(ext.toLowerCase())||"dps".equals(ext.toLowerCase())) {
		isUseWpsLinux = Boolean.TRUE;
	}
	request.setAttribute("isUseWpsLinux", isUseWpsLinux);
	Boolean wpsoaassistEmbed = SysAttWpsoaassistUtil.isWPSOAassistEmbed();
	request.setAttribute("wpsoaassistEmbed", wpsoaassistEmbed);
%>

<c:choose>
	<c:when test="${isWindows == 'false'&&isUseWpsLinux=='true'&&wpsoaassistEmbed=='true'}">
		<c:import url="/sys/attachment/sys_att_main/wps/oaassist/linux/sysAttMain_edit.jsp" charEncoding="UTF-8">
			<c:param name="fdKey" value="${param.fdKey}" />
			<c:param name="fdAttType" value="${param.office}" />
			<c:param name="fdModelId" value="${param.fdModelId}" />
			<c:param name="fdModelName" value="${param.fdModelName}" />
			<c:param name="formBeanName" value="${param.formBeanName}" />
			<c:param name="load" value="${param.load}" />
			<c:param name="buttonDiv" value="${param.buttonDiv}" />
		</c:import>
	</c:when>
	<c:otherwise>
		<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
			<c:param name="fdKey" value="${param.fdKey}" />
			<c:param name="fdMulti" value="${param.fdMulti}" />
			<c:param name="fdModelId" value="${param.fdModelId}" />
			<c:param name="fdModelName" value="${param.fdModelName}" />
			<c:param name="formBeanName" value="${param.formBeanName}" />
			<c:param name="bindSubmit" value="${param.bindSubmit}"/>
			<c:param name="isTemplate" value="${param.isTemplate}"/>
			<c:param name="fdTemplateModelId" value="${param.fdTemplateId}" />
			<c:param name="fdTemplateModelName" value="${param.fdTemplateModelName}" />
			<c:param name="fdTemplateKey" value="${param.fdTemplateKey}" />
			<c:param name="templateBeanName" value="${param.templateBeanName}" />
			<c:param name="showDelete" value="${param.showDelete}" />
			<c:param name="wpsExtAppModel" value="${param.wpsExtAppModel}" />
			<c:param name="canRead" value="${param.canRead}" />
			<c:param name="canEdit" value="${param.canEdit}" />
			<c:param name="canPrint" value="${param.canPrint}" />
			<c:param name="addToPreview" value="${param.addToPreview}" />
			<c:param  name="hideTips"  value="${param.hideTips}"/>
			<c:param  name="hideReplace"  value="${param.hideReplace}"/>
			<c:param  name="canChangeName"  value="${param.canChangeName}"/>
			<c:param  name="filenameWidth"  value="${param.filenameWidth}"/>
		</c:import>
	</c:otherwise>
</c:choose>