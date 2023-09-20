<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ page import="com.landray.kmss.web.taglib.TagUtils,com.landray.kmss.sys.attachment.forms.*,java.util.*"%>
<%@ page import="com.landray.kmss.sys.attachment.service.*"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="java.util.regex.Pattern"%>
<%@ page import="java.util.regex.Matcher"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@page import="com.landray.kmss.sys.attachment.util.SysAttViewerUtil"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil"%>
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
	pageContext.setAttribute("wpsPreviewIsLinux", SysAttWpsCloudUtil.checkWpsPreviewIsLinux());
	pageContext.setAttribute("wpsPreviewIsWindows", SysAttWpsCloudUtil.checkWpsPreviewIsWindows());

%>
<c:set var="fdKey" value="${param.fdKey}" />
<c:set var="attForms" value="${_formBean.attachmentForms[fdKey]}" />
<c:set var="attachmentId" value=""/>
<c:set var="fdFileName" value=""/>
<c:forEach items="${attForms.attachments}" var="sysAttMain"	varStatus="vstatus">
	<c:set var="attachmentId" value="${sysAttMain.fdId}"/>
	<c:set var="fdFileName" value="${sysAttMain.fdFileName}"/>
	<%
		SysAttMain sysAttMain = (SysAttMain) pageContext.getAttribute("sysAttMain");
		String path = SysAttViewerUtil.getViewerPath( sysAttMain, request);
		Boolean hasViewer = Boolean.FALSE;
		if (StringUtil.isNotNull(path))
			hasViewer = Boolean.TRUE;
		pageContext.setAttribute("hasViewer", hasViewer);
	%>
</c:forEach>

<div id="WPSCENTER_${param.fdKey}"></div>
<script src="${LUI_ContextPath}/sys/attachment/mobile/viewer/wps/center/js/web-office-sdk-v1.1.11.umd.js"></script>
<script>	
var fdAttMainId ='${attachmentId}';
var fdKey = '${param.fdKey}';
var fdModelId = '${param.fdModelId}';
var fdModelName = '${param.fdModelName}';
var fdFileName = '${fdFileName}';
var fileExt = fdFileName.substring(fdFileName.lastIndexOf("."))
var wpsCenterAattachment_${param.fdKey};

require(["sys/attachment/mobile/viewer/wps/center/js/WpsCenterAattachment","dojo/request","mui/util", "dojo/_base/lang","dojo/domReady","dojo/dom-construct","dojo/topic","dijit/registry",'dojo/query'], function(WpsCenterAattachment,req,util,lang,domReady,domConstruct,topic,registry,query) {

	wpsCenterAattachment_${param.fdKey} = new WpsCenterAattachment({
    		fdId: fdAttMainId,
    		fdKey: fdKey,
    		fdModelId: fdModelId,
    		fdModelName: fdModelName,
    		fdMode: "read",
			hasViewer:${hasViewer},
    		fdFileName:fdFileName
    	});
    	if("${param.load}" != 'false'){
			wpsCenterAattachment_${param.fdKey}.load();
    	}else{
    		topic.subscribe('/mui/navbar/slideBar',function(view){
    			if(view && view.moveTo !=""){
    				var contentNode = registry.byId(view.moveTo);
    				if(contentNode){
    					var wpsCenterContainerId = "WPSCENTER_${param.fdKey}";
    	    				var wpsCenterContainerEle = query('#'+wpsCenterContainerId,contentNode.domNode);
    	    				if(wpsCenterContainerEle.length > 0){
    	    					if(!wpsCenterAattachment_${param.fdKey}.hasLoad){
									wpsCenterAattachment_${param.fdKey}.load();
    	    					}
    	    				}
    				}
    			}
    		}); 
     }
}); 

function isJSON(str) {
    if (typeof str == 'string') {
        try {
            JSON.parse(str);
            return true;
        } catch(e) {
            console.log(e);
            return false;
        }
    }
    return false;
}
</script>
