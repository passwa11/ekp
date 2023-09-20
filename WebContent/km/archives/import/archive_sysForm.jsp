<%@page import="com.landray.kmss.km.archives.service.spring.KmArchivesJspGenerator"%>
<%@page import="java.io.ByteArrayInputStream"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="org.apache.commons.io.IOUtils"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Map"%>
<%@page import="com.landray.kmss.sys.xform.service.DictLoadService"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.xform.service.SysFormFileMannager"%>
<%@page import="com.landray.kmss.sys.xform.base.model.BaseFormTemplateHistory"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.xform.base.service.SysFormUtil"%>
<%@page import="com.landray.kmss.sys.xform.base.dao.ISysFormTemplateHistoryDao"%>
<%@page import="com.landray.kmss.sys.xform.base.dao.ISysFormCommonTempHistoryDao"%>
<%@page import="com.landray.kmss.sys.xform.base.model.AbstractFormTemplate"%>
<%@page import="com.landray.kmss.common.forms.IExtendForm" %>
<%@page import="javax.servlet.jsp.PageContext" %>

<link rel="stylesheet" type="text/css" href="${KMSS_Parameter_ContextPath}sys/xform/include/css/sysForm_main.css"></link>
<link rel="stylesheet" type="text/css" href="${KMSS_Parameter_ContextPath}sys/xform/designer/relation_select/select2/select2.css"></link>

<%
String formBeanName = request.getParameter("formName");
String mainFormName = null;
String xformFormName = null;
int indexOf = formBeanName.indexOf('.');
if (indexOf > -1) {
	mainFormName = formBeanName.substring(0, indexOf);
	xformFormName = formBeanName.substring(indexOf + 1);
	pageContext.setAttribute("_formName", xformFormName);
} else {
	mainFormName = formBeanName;
	pageContext.setAttribute("_formName", null);
}

Object mainForm = request.getAttribute(mainFormName);
Object xform = xformFormName == null ? mainForm : PropertyUtils.getProperty(mainForm, xformFormName);

if(xform instanceof IExtendForm){
	IExtendForm extendForm = (IExtendForm)xform;
	String mainModelName = extendForm.getModelClass().getName();
	request.setAttribute("_mainModelName", mainModelName);
}

DictLoadService dictService=(DictLoadService)SpringBeanUtil.getBean("sysFormDictLoadService");
String path = "";
if(xform instanceof IExtendForm){
	IExtendForm extendForm = (IExtendForm)xform;
	path=dictService.findExtendFileFullPath(extendForm);
}else{
	path = (String) PropertyUtils.getProperty(xform, "extendDataFormInfo.extendFilePath");
	path=dictService.findExtendFileFullPath(path);
}

if (StringUtil.isNotNull(path)) {
	if (path.startsWith("/")) {
		path = path.substring(1);
	}
	request.setAttribute("_xformMainForm", mainForm);
	request.setAttribute("_xformForm", xform);
	pageContext.setAttribute("_formFilePath", path);
	
	request.setAttribute("SysForm.isPrint", "true");
	request.setAttribute("SysForm.showStatus", "view");
	request.setAttribute("SysForm.base.showStatus", "view");
	
	// 兼容EKP3.1代码
	Object originFormBean = pageContext.getAttribute("com.landray.kmss.web.taglib.FormBean", PageContext.REQUEST_SCOPE);
	pageContext.setAttribute("com.landray.kmss.web.taglib.FormBean", mainForm, PageContext.REQUEST_SCOPE);
	
	Boolean isUpTab = false;

	/************
	归档表单
	*************/
	String formFilePath = "/" + path + ".jsp";
	File formFile = new File(application.getRealPath(formFilePath));
	if(formFile.isFile()){
		new KmArchivesJspGenerator().execGenerate(request, formFile);
	}
	
%>
	<link rel="stylesheet" type="text/css" href="<c:url value="/${_formFilePath}.css"/>">
	<xform:config formName="${_formName}" >
	<div class="sysDefineXform">
	<c:import url="/${_formFilePath}_archive.jsp" charEncoding="UTF-8">
		<c:param name="method" value="${param.method}" />
		<c:param name="fdKey" value="${param.fdKey}" />
		<c:param name="formFilePath" value="${_formFilePath}" />
		<c:param name="mainModelName" value="${_mainModelName}" />
		<c:param name="uploadAfterSelect" value="true" />
	</c:import>
	</div>
	</xform:config>
<%
	//兼容EKP3.1代码
	pageContext.setAttribute("com.landray.kmss.web.taglib.FormBean", originFormBean, PageContext.REQUEST_SCOPE);
}
pageContext.removeAttribute("_formName",PageContext.REQUEST_SCOPE);
pageContext.removeAttribute("_formFilePath",PageContext.REQUEST_SCOPE);
request.removeAttribute("SysForm.base.showStatus");
request.removeAttribute("SysForm.isPrint");
request.removeAttribute("SysForm.showStatus");
request.removeAttribute("_xformMainForm");
request.removeAttribute("_xformForm");
if(xform instanceof IExtendForm){
	request.removeAttribute("_mainModelName");
}

%>