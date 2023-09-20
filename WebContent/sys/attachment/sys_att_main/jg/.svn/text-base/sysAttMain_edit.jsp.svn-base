<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.web.taglib.TagUtils,com.landray.kmss.sys.attachment.forms.*,java.util.*"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="com.landray.kmss.sys.attachment.service.*"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="java.util.regex.Pattern"%>
<%@ page import="java.util.regex.Matcher"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@ include file="/sys/attachment/sys_att_main/jg/sysAttMain_CheckEdit_js.jsp"%>
<%@ include file="/sys/attachment/sys_att_main/jg/sysAttMain_CheckSupport.jsp"%>

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
String docStatus = null;
String fdIssample = null;
try{
	docStatus = (String)org.apache.commons.beanutils.PropertyUtils.getProperty(formBean,"docStatus");
	fdIssample = (String)org.apache.commons.beanutils.PropertyUtils.getProperty(formBean,"fdIssample");
}catch(Exception e){}
if(docStatus==null) docStatus = "30";
if(fdIssample == null) {
	fdIssample = "false";
};

pageContext.setAttribute("_docStatus", docStatus);
pageContext.setAttribute("_fdIssample", fdIssample);

//得到文档标题,下载时取文档标题
String fileName= null;
try{
	String docSubject = (String)org.apache.commons.beanutils.PropertyUtils.getProperty(formBean,"docSubject"); 
	if(StringUtil.isNotNull(docSubject)){
		fileName = docSubject;
	}else{
		String fdName = (String)org.apache.commons.beanutils.PropertyUtils.getProperty(formBean,"fdName"); 
		if(StringUtil.isNotNull(fdName)){
			fileName = fdName;
		}
	}
	
	Pattern pattern = Pattern.compile("[\\s\\\\/:\\*\\?\\\"<>\\|]");
    Matcher matcher = pattern.matcher(fileName);
    fileName= matcher.replaceAll("");
    
    
    if(fileName.length() > 100 ){
    	fileName = fileName.substring(0, 100);
    }
    
	//标题加上".doc"作为文件名
	pageContext.setAttribute("fileName", fileName);
	pageContext.setAttribute("_fileName", fileName+".doc");
}catch(Exception e){}
%>
<c:set var="fdKey" value="${param.fdKey}" />
<c:set var="attForms" value="${_formBean.attachmentForms[fdKey]}" />
<table class="tb_noborder">
<tr><td id="_button_${HtmlParam.fdKey}_JG_Attachment_TD"></td>
</tr>
</table>
<%--提示当前在线编辑用户的div--%>
<div id="warnDiv">
</div>
<html:hidden property="attachmentForms.${HtmlParam.fdKey}.fdModelId" />
<html:hidden property="attachmentForms.${HtmlParam.fdKey}.fdModelName"
	value="${HtmlParam.fdModelName}" />
<html:hidden property="attachmentForms.${HtmlParam.fdKey}.fdKey"
	value="${HtmlParam.fdKey}" />
<html:hidden property="attachmentForms.${HtmlParam.fdKey}.fdAttType"
	value="${HtmlParam.fdAttType}" />
<html:hidden property="attachmentForms.${HtmlParam.fdKey}.fdMulti"
	value="${HtmlParam.fdMulti}" />
<html:hidden property="attachmentForms.${HtmlParam.fdKey}.deletedAttachmentIds" />
<html:hidden property="attachmentForms.${HtmlParam.fdKey}.attachmentIds" />
<%@ include file="sysAttMain_OCX.jsp"%>
<script>
	if(typeof Com_Parameter.__sysAttMainlocale__ == "undefined")
		Com_Parameter.__sysAttMainlocale__= "<%= UserUtil.getKMSSUser(request).getLocale().toString().toLowerCase().replace('_', '-') %>";
</script>
<script>Com_IncludeFile("jg_attachment.js","${KMSS_Parameter_ContextPath}sys/attachment/js/","js",true);</script>
<%
	//synchronize deletedAttachmentIds,attachmentIds and attachments
	AttachmentDetailsForm _attForms = (AttachmentDetailsForm)pageContext.getAttribute("attForms");
	if(_attForms != null){
		try{
			String _deleteAttachmentIds = _attForms.getDeletedAttachmentIds();
			String _attachmentIds = _attForms.getAttachmentIds();
			String[] attachmentIds = _attachmentIds==null?new String[]{}:_attachmentIds.split(";");
			String[] deleteAttachemntIds = _deleteAttachmentIds==null?new String[]{}:_deleteAttachmentIds.split(";");
			List attachmentIdsList = Arrays.asList(attachmentIds);
			//List deleteAttachemntIdsList = Arrays.asList(deleteAttachemntIds);
			List l_ids = new ArrayList();
			for(int i=0;i<attachmentIdsList.size();i++){
				String id = attachmentIdsList.get(i).toString();
				if(id != null && id.trim().length()>0)
					l_ids.add(attachmentIdsList.get(i).toString());
				
			}
			String[] _ids = (String[])l_ids.toArray(new String[l_ids.size()]);
			ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
			ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService)ctx.getBean("sysAttMainService");
			List sysAttMains=sysAttMainService.findByPrimaryKeys(_ids);
			for(Iterator it=sysAttMains.iterator();it.hasNext();){
				Object _obj = it.next();
				if(_attForms.getAttachments().contains(_obj))
					continue;
				_attForms.getAttachments().add(_obj);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
<c:set var="attachmentId" value=""/>
<c:set var="fdFileName" value="${_fileName}"/>	
<c:forEach items="${attForms.attachments}" var="sysAttMain"	varStatus="vstatus">
		<c:set var="attachmentId" value="${sysAttMain.fdId}"/>
		<c:set var="fdFileName" value="${sysAttMain.fdFileName}"/>
</c:forEach>

<script type="text/javascript">
var jg_attachmentObject_${JsParam.fdKey} = new JG_AttachmentObject("${attachmentId}", "${JsParam.fdKey}", "${JsParam.fdModelName}", "${JsParam.fdModelId}", "${param.fdAttType}", "edit");
jg_attachmentObject_${JsParam.fdKey}.userId = "<%=com.landray.kmss.util.UserUtil.getUser().getFdId()%>";
jg_attachmentObject_${JsParam.fdKey}.userName = "<%=com.landray.kmss.util.UserUtil.getUser().getFdName()%>";
<c:if test="${not empty param.isTemplate && param.isTemplate == 'true'}">
	jg_attachmentObject_${JsParam.fdKey}.isTemplate = true;
</c:if>
<c:if test="${not empty param.fdTemplateModelId}">
	jg_attachmentObject_${JsParam.fdKey}.fdTemplateModelId = "${JsParam.fdTemplateModelId}";
</c:if>
<c:if test="${not empty param.fdTemplateModelName}">
	jg_attachmentObject_${JsParam.fdKey}.fdTemplateModelName = "${JsParam.fdTemplateModelName}";
</c:if>
<c:if test="${not empty param.fdTemplateKey}">
	jg_attachmentObject_${JsParam.fdKey}.fdTemplateKey = "${JsParam.fdTemplateKey}";
</c:if>
<c:if test="${not empty param.editMode}">
	jg_attachmentObject_${JsParam.fdKey}.editMode = "${JsParam.editMode}";
</c:if>
<c:if test="${not empty param.buttonDiv}">
	jg_attachmentObject_${JsParam.fdKey}.buttonDiv = "${JsParam.buttonDiv}";
</c:if>
<c:if test="${empty _docStatus || _docStatus == '10'}">
	jg_attachmentObject_${JsParam.fdKey}.trackRevisions = false;
	<c:if test="${empty _fdIssample || _fdIssample eq 'false'}">
		jg_attachmentObject_${JsParam.fdKey}.showOpenDraft = true; //草稿状态下显示打开本地初稿按钮
	</c:if>
</c:if>
<c:if test="${not empty _fdIssample && _fdIssample eq 'true'}">
	jg_attachmentObject_${JsParam.fdKey}.fdIssample = true; 
</c:if>

<c:if test="${not empty param.trackRevisions || param.trackRevisions == 'false'}">
jg_attachmentObject_${JsParam.fdKey}.trackRevisions = false;
</c:if>
	
<c:if test="${not empty param.bookMarks}">
	jg_attachmentObject_${JsParam.fdKey}.bookMarks = "${JsParam.bookMarks}"; 
</c:if>
<c:if test="${_docStatus == '20'}">
jg_attachmentObject_${JsParam.fdKey}.showRevisions = true;
</c:if>

<%--业务模块指定是否显示--%>
<c:if test="${not empty JsParam.showRevisions and JsParam.showRevisions eq 'true'}">
jg_attachmentObject_${JsParam.fdKey}.showRevisions = true;
</c:if>
<c:if test="${not empty JsParam.showRevisions and JsParam.showRevisions eq 'false'}">
jg_attachmentObject_${JsParam.fdKey}.showRevisions = false;
</c:if>

<c:if test="${not empty param.forceRevisions}">
jg_attachmentObject_${JsParam.fdKey}.forceRevisions = false;
</c:if>
<c:if test="${not empty param.clearDownloadRevisions and param.clearDownloadRevisions == 'true'}">
jg_attachmentObject_${JsParam.fdKey}.clearDownloadRevisions = true;
</c:if>
<c:if test="${not empty param.bindSubmit}">
jg_attachmentObject_${JsParam.fdKey}.bindSubmit = false;
</c:if>
<c:if test="${not empty param.isReadOnly and  param.isReadOnly == 'true'}">
jg_attachmentObject_${JsParam.fdKey}.isReadOnly = true;
</c:if>

<c:if test="${_formBean.method_GET !='add' and  param.isReadOnly == 'true'}">
	jg_attachmentObject_${JsParam.fdKey}.canPrint=false;
	<kmss:auth requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=print&fdId=${attachmentId}" requestMethod="GET">
		jg_attachmentObject_${JsParam.fdKey}.canPrint=true;
	</kmss:auth>
	jg_attachmentObject_${JsParam.fdKey}.canDownload=false;
	<kmss:auth requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${attachmentId}" requestMethod="GET">
		jg_attachmentObject_${JsParam.fdKey}.canDownload=true;
	</kmss:auth>
	jg_attachmentObject_${JsParam.fdKey}.canCopy=false;
	<kmss:auth	requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=copy&fdId=${attachmentId}" requestMethod="GET">
		jg_attachmentObject_${JsParam.fdKey}.canCopy=true;	
	</kmss:auth>
</c:if>
<%--文档转图片页面--%>
<c:if test="${param.isToImg == 'true' and param.bindSubmit != 'false'}">
Com_Parameter.event["confirm"].push(function() {
	jg_attachmentObject_${JsParam.fdKey}.ocxObj.Active(true);
	return jg_attachmentObject_${JsParam.fdKey}.saveAsImage();
});
</c:if>
var fdDocFileName = "${fdFileName}";
var fdDocFileName_Index=fdDocFileName.lastIndexOf(".");
var fdDocFileName_Len=fdDocFileName.length;
var fdDocFileName_Suffix = fdDocFileName.substring(fdDocFileName_Index,fdDocFileName_Len);
var fdId = '${attachmentId}';
var fdKey = '${JsParam.fdKey}';
var fdModelId = '${JsParam.fdModelId}';
var fdModelName = '${JsParam.fdModelName}';
if (fdKey == 'mainOnline' && fdModelName == 'com.landray.kmss.km.agreement.model.KmAgreementApply') {
	//合同主文档使用附件机制的文件名称作为下载时文件名
} else {
	fdDocFileName = "${fileName}" + fdDocFileName_Suffix;
}

Com_Parameter.event["submit"].push( function() {
	if(jg_attachmentObject_${JsParam.fdKey}.updateTimer){
		clearInterval(jg_attachmentObject_${JsParam.fdKey}.updateTimer);
		return true;
	}else{
		return true;
	}
});
	
Com_AddEventListener(window, "load", function() {
	var copyValue;
	//国产化系统，金格控件的CopyType类型为boolean，非国产化为string
	if (window.userOpSysType.indexOf("Win") == -1 
	  		&& navigator.userAgent.indexOf("Firefox") >= 0) {
		copyValue = true;
	} else {
		copyValue = "1";
	}
	//load参数指定是否要加载控件，默认为加载（公文收文有是否需要正文的情况，如果不需要正文则不加载）
	if("${JsParam.fdAttType}" == "office"&&"${JsParam.load}"!="false") {
		setTimeout(function(){
			jg_attachmentObject_${JsParam.fdKey}.load(encodeURIComponent(fdDocFileName), "${JsParam.officeType}");
			jg_attachmentObject_${JsParam.fdKey}.show();
			if(jg_attachmentObject_${JsParam.fdKey}.ocxObj){
				
				//公文中，审批人编号操作不一定需要正文编辑权限，根据业务传过来的isReadOnly参数对正文状态进行设置
				if("${JsParam.isReadOnly}"=="true"){
					if(!jg_attachmentObject_${JsParam.fdKey}.canCopy){
						jg_attachmentObject_${JsParam.fdKey}.ocxObj.CopyType = copyValue;
						jg_attachmentObject_${JsParam.fdKey}.ocxObj.EditType = "0,1";
					}else{
						jg_attachmentObject_${JsParam.fdKey}.ocxObj.CopyType = copyValue;
						jg_attachmentObject_${JsParam.fdKey}.ocxObj.EditType = "4,1";
					}
				}
				jg_attachmentObject_${JsParam.fdKey}.ocxObj.Active(true);
			}
		},1000);
	}
});
Com_AddEventListener(window, "unload", function() {
    //清除当前在线编辑用户的信息
	clearEdit(fdId,fdModelId,fdModelName,fdKey);
	jg_attachmentObject_${JsParam.fdKey}.unLoad();
});
</script>

<ui:event event="show">
	if("${JsParam.loadJg}" == 'false'){
		Attachment_ObjectInfo['${JsParam.fdKey}'].unLoad();
		Attachment_ObjectInfo['${JsParam.fdKey}'].load(encodeURIComponent(fdDocFileName), "${JsParam.officeType}");
		Attachment_ObjectInfo['${JsParam.fdKey}'].show();
	}
</ui:event>