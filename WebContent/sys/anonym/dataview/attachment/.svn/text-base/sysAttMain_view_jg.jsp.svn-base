<%@page import="com.landray.kmss.util.UserUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@page import="com.landray.kmss.sys.attachment.util.SysAttConfigUtil"%>
<%@ include file="/sys/attachment/sys_att_main/jg/sysAttMain_CheckSupport.jsp"%>

<%@ include file="/sys/attachment/sys_att_main/jg/sysAttMain_OCX.jsp"%>

<%
	
	//判断金格控件大版本号2009 or 2015
	String JGBigVersion_A = JgWebOffice.getJGBigVersion();
	request.setAttribute("JGBigVersion_A", JGBigVersion_A);	
	
	//判断金格pdf控件大版本号iWebPDF or iWebPDF2018
	String JGBigWebPDFVersion_A = JgWebOffice.getPDFBigVersion();
	request.setAttribute("JGBigWebPDFVersion_A", JGBigWebPDFVersion_A);
	
	//判断当前操作系统
	String osType_A = JgWebOffice.getOSType(request);
	request.setAttribute("osType_A", osType_A);
	
	//判断是否开启国产化控件
	String isEnabled_A = "false";
	String isEnabledZZKK_A = JgWebOffice.getIsJGHandZzkkEnabled();
	if (null != isEnabledZZKK_A && isEnabledZZKK_A.equals("true")) {
		isEnabled_A = "true";
	}
	request.setAttribute("isEnabled_A", isEnabled_A);
	request.setAttribute("showWindow_A", SysAttConfigUtil.isShowWindow());

%>
<script>
window.jgBigVersionParam = "${JGBigVersion_A}";

window.jgBigWebPDFVersionParam = "${JGBigWebPDFVersion_A}";

window.userOsTypeParam = "${osType_A}";

window.isEnabledParam = "${isEnabled_A}";

window.jg_showWindow = "${showWindow_A}";
</script>

<script>
	if(typeof Com_Parameter.__sysAttMainlocale__ == "undefined")
		Com_Parameter.__sysAttMainlocale__= "<%= UserUtil.getKMSSUser(request).getLocale().toString().toLowerCase().replace('_', '-') %>";
</script>
<script>Com_IncludeFile("jg_attachment.js","${KMSS_Parameter_ContextPath}sys/attachment/js/","js",true);</script>
<script type="text/javascript">
var jg_attachmentObject_${JsParam.fdKey} = new JG_AttachmentObject("${attachmentId}", "${JsParam.fdKey}", "${JsParam.fdModelName}", "${JsParam.fdModelId}", "${JsParam.fdAttType}", "view");
jg_attachmentObject_${JsParam.fdKey}.userName = "<%=UserUtil.getUser().getFdName()%>";
<c:if test="${not empty param.isTemplate && param.isTemplate == 'true'}">
	jg_attachmentObject_${JsParam.fdKey}.isTemplate = true;
</c:if>
<c:if test="${not empty param.editMode}">
	jg_attachmentObject_${JsParam.fdKey}.editMode = "${JsParam.editMode}";
</c:if>
<c:if test="${not empty param.buttonDiv}">
	jg_attachmentObject_${JsParam.fdKey}.buttonDiv = "${JsParam.buttonDiv}";
</c:if>
<c:if test="${not empty param.bookMarks}">
	jg_attachmentObject_${JsParam.fdKey}.bookMarks = "${JsParam.bookMarks}"; 
</c:if>
<c:if test="${JsParam.showToolBar =='false'}">
jg_attachmentObject_${JsParam.fdKey}.showToolBar =false;
</c:if>
//得到文档状态，用于控制留痕按钮在发布状态中不显示
	jg_attachmentObject_${JsParam.fdKey}.hiddenRevisions=false;
	jg_attachmentObject_${JsParam.fdKey}.protectstatus = false;
	jg_attachmentObject_${JsParam.fdKey}.canPrint = false;
	jg_attachmentObject_${JsParam.fdKey}.canDownload = false;
	jg_attachmentObject_${JsParam.fdKey}.canRead = true;		
	jg_attachmentObject_${JsParam.fdKey}.canCopy = false;	
	jg_attachmentObject_${JsParam.fdKey}.canEdit = false;

function OnToolsClick(vIndex,vCaption){
	if(vIndex=='-1'&&vCaption=='全屏_BEGIN'){
        if(jg_attachmentObject_${JsParam.fdKey}.canCopy){
      	  jg_attachmentObject_${JsParam.fdKey}.ocxObj.CopyType = "1";
      	  jg_attachmentObject_${JsParam.fdKey}.ocxObj.ShowToolBar = 2;
      	  jg_attachmentObject_${JsParam.fdKey}.ocxObj.EditType = "4,1";
  	  }else{
  		  jg_attachmentObject_${JsParam.fdKey}.ocxObj.CopyType = "1";
  		  jg_attachmentObject_${JsParam.fdKey}.ocxObj.ShowToolBar = 2;
  		  jg_attachmentObject_${JsParam.fdKey}.ocxObj.EditType = "0,1";
        }
    }
}
Com_AddEventListener(window, "unload", function() {
	jg_attachmentObject_${JsParam.fdKey}.unLoad();
});
</script>
<%
    boolean isExpand = "true".equals(request.getParameter("isExpand"));
if(isExpand){
%>
<script>
   if(jg_attachmentObject_${JsParam.fdKey}){
	   setTimeout(function(){
			jg_attachmentObject_${JsParam.fdKey}.load(encodeURIComponent("${fdFileName}"), "");
			jg_attachmentObject_${JsParam.fdKey}.show();
			if(jg_attachmentObject_${JsParam.fdKey}.ocxObj){
				if(!jg_attachmentObject_${JsParam.fdKey}.canCopy){
					jg_attachmentObject_${JsParam.fdKey}.ocxObj.CopyType = "1";
					jg_attachmentObject_${JsParam.fdKey}.ocxObj.EditType = "0,1";
				}else{
					jg_attachmentObject_${JsParam.fdKey}.ocxObj.CopyType = "1";
					jg_attachmentObject_${JsParam.fdKey}.ocxObj.EditType = "4,1";
				}
				if(Com_Parameter.IE)
					jg_attachmentObject_${JsParam.fdKey}.activeObj.setAttribute("OnToolsClick","OnToolsClick(vIndex,vCaption);");
				else
					jg_attachmentObject_${JsParam.fdKey}.activeObj.setAttribute("event_OnToolsClick","OnToolsClick");
			}
		},500);
	}
</script>
<%}else{%>
<ui:event event="show">
		if(jg_attachmentObject_${JsParam.fdKey}){
			setTimeout(function(){
			jg_attachmentObject_${JsParam.fdKey}.load(encodeURIComponent("${fdFileName}"), "");
			jg_attachmentObject_${JsParam.fdKey}.show();
			if(!jg_attachmentObject_${JsParam.fdKey}.canCopy){
				jg_attachmentObject_${JsParam.fdKey}.ocxObj.CopyType = "1";
				jg_attachmentObject_${JsParam.fdKey}.ocxObj.EditType = "0,1";
			}else{
				jg_attachmentObject_${JsParam.fdKey}.ocxObj.CopyType = "1";
				jg_attachmentObject_${JsParam.fdKey}.ocxObj.EditType = "4,1";
			}
			if(Com_Parameter.IE)
				jg_attachmentObject_${JsParam.fdKey}.activeObj.setAttribute("OnToolsClick","OnToolsClick(vIndex,vCaption);");
			else
				jg_attachmentObject_${JsParam.fdKey}.activeObj.setAttribute("event_OnToolsClick","OnToolsClick");
			},500);
		}
</ui:event>
<%}%>