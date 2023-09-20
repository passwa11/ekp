<%@page import="com.landray.kmss.util.JspGenerator"%>
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
<style type="text/css">
	table[fd_type='detailsTable'] tr[type='optRow']{
		display: none;
	}
</style>
<link rel="stylesheet" type="text/css" href="${KMSS_Parameter_ContextPath}sys/xform/include/css/sysForm_main.css"></link>
<link rel="stylesheet" type="text/css" href="${KMSS_Parameter_ContextPath}sys/xform/designer/relation_select/select2/select2.css"></link>
<script>
	// 自定义表单的全局变量
	var Xform_ObjectInfo = {};
	Xform_ObjectInfo.Xform_Controls = [];
	Xform_ObjectInfo.isArchives = true;
</script>
<!-- 引入各种控件主文档处需要用的多语言信息 -->
<%@ include file="/sys/xform/include/sysForm_lang.jsp"%>
<%@ include file="/sys/xform/include/sysForm_script.jsp" %>
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
		new JspGenerator().execGenerate(request, formFile);
	}
	
%>
	<script>Com_IncludeFile("xform.js|calendar.js");</script>
	<script>Com_IncludeFile("form.js","${LUI_ContextPath}/resource/js/","js",true);</script>
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
	<script>
		//解析表单view页面存值 作者 曹映辉 #日期 2017年5月18日
		var xform_data_hide={};
		<%
		        Map<String ,Object> map=(java.util.Map)request.getAttribute("view_xform_value");
				if(map !=null){
					for(Map.Entry<String ,Object> entry : map.entrySet()){
						if (entry.getValue() instanceof String) {
							String val=StringUtil.clearScriptTag((String)entry.getValue());
							val=StringUtil.XMLEscape(val);
							out.println("xform_data_hide[\""+entry.getKey()+"\"]=\""+val+"\";");
						} else {
							out.println("xform_data_hide[\""+entry.getKey()+"\"]=\""+entry.getValue()+"\";");
						}
					}
				}
		%>
		//将特殊字符转义还原
		for(var temp in  xform_data_hide){
			if(xform_data_hide[temp]==null){
				continue;
			}
			xform_data_hide[temp]=xform_data_hide[temp].replace(/&amp;/g, "&").replace(/&quot;/g, "\"").replace(/&lt;/g, "<").replace(/&gt;/g, ">");
		}
	</script>
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