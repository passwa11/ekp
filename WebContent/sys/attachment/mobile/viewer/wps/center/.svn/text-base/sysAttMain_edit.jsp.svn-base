<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ page import="com.landray.kmss.web.taglib.TagUtils,com.landray.kmss.sys.attachment.forms.*,java.util.*"%>
<%@ page import="com.landray.kmss.sys.attachment.service.*"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="java.util.regex.Pattern"%>
<%@ page import="java.util.regex.Matcher"%>
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
<c:set var="fdModelId" value="${param.fdModelId}" />
<c:set var="fdModelName" value="${param.fdModelName}" />
<c:set var="attForms" value="${_formBean.attachmentForms[fdKey]}" />
<!-- 引用编辑页面，但用查看打开wps，类型：write(编辑)，read(只读) -->
<c:set var="isWrite" value="${param.fdMode}" />
<c:if test="${empty isWrite}">
	<c:set var="isWrite" value="write" />
</c:if>
<c:set var="attachmentId" value=""/>
<c:set var="fdFileName" value=""/>
<c:forEach items="${attForms.attachments}" var="sysAttMain"	varStatus="vstatus">
	<c:set var="attachmentId" value="${sysAttMain.fdId}"/>
	<c:set var="fdFileName" value="${sysAttMain.fdFileName}"/>
</c:forEach>

<div id="WPSCENTER_${param.fdKey}" style="height: 100vh;"></div>
<script src="${LUI_ContextPath}/sys/attachment/mobile/viewer/wps/center/js/web-office-sdk-v1.1.11.umd.js"></script>
<script>	
var fdAttMainId ='${attachmentId}';
var fdKey = '${param.fdKey}';
var fdModelId = '${param.fdModelId}';
var fdModelName = '${param.fdModelName}';
var fdFileName = '${fdFileName}';
var fileExt = fdFileName.substring(fdFileName.lastIndexOf("."));
var wpsCenterAattachment_${param.fdKey};

require(["sys/attachment/mobile/viewer/wps/center/js/WpsCenterAattachment","dojo/request","mui/util", "dojo/_base/lang","dojo/domReady!","dojo/dom-construct","dojo/topic","dijit/registry",'dojo/query'], function(WpsCenterAattachment,req,util,lang,domReady,domConstruct,topic,registry,query) {
	
	var fdAttMainId = "${attachmentId}";
	if("${attachmentId}" == ""){
	 
		//请求在线编辑附件的id
		var addOnlineFileUrl = util.formatUrl("/sys/attachment/sys_att_main/sysAttMain.do?method=addCenterOnlineFile");
		req(addOnlineFileUrl, {
			method : 'post',
			data:{
				fdTemplateModelId:"${param.fdTemplateModelId}",
				fdTemplateModelName:"${param.fdTemplateModelName}",
				fdTemplateKey:"${param.fdTemplateKey}",
				fdModelName:"${param.fdModelName}",
				fdKey:"${param.fdKey}",
				fdTempKey:"${param.fdTempKey}",
				fdModelId:"${param.fdModelId}",
				fdFileExt:"${param.fdFileExt}"
			}
		}).then(lang.hitch(this, function(data) {
			if (data){
 	    			var results =  eval("("+data+")");
			    if(results['editOnlineAttId']!=null){
			   	 	fdAttMainId = results['editOnlineAttId'];
			        wpsCenterAattachment_${param.fdKey} = new WpsCenterAattachment({
				    		fdId: fdAttMainId,
				    		fdKey: fdKey,
				    		fdModelId: fdModelId,
				    		fdModelName: fdModelName,
				    		fdMode: "${isWrite}",
				    	});
				    	if("${param.load}" != 'false'){
				    		wpsCenterAattachment_${param.fdKey}.load();
				    	}else{
				    		topic.subscribe('/mui/navbar/slideBar',function(view){
				    			if(view && view.moveTo !=""){
				    				var contentNode = registry.byId(view.moveTo);
				    				if(contentNode){
				    					var wpsCloudContainerId = "WPSCENTER_${param.fdKey}";
				    	    				var wpsCloudContainerEle = query('#'+wpsCloudContainerId,contentNode.domNode);
				    	    				if(wpsCloudContainerEle.length > 0){
				    	    					if(!wpsCenterAattachment_${param.fdKey}.hasLoad){
				    	    						wpsCenterAattachment_${param.fdKey}.load();
				    	    					}
				    	    				}
				    				}
				    			}
				    		}); 
			        	}
				}
			}
		}));
	}else{
		wpsCenterAattachment_${param.fdKey} = new WpsCenterAattachment({
	    		fdId: fdAttMainId,
	    		fdKey: fdKey,
	    		fdModelId: fdModelId,
	    		fdModelName: fdModelName,
	    		fdMode: "${isWrite}",
	    		fdFileName:fdFileName,
	    		wpsPreviewIsWindows:${wpsPreviewIsWindows},
	    		wpsPreviewIsLinux:${wpsPreviewIsLinux}
	    	});
	    	if("${param.load}" != 'false'){
	    		wpsCenterAattachment_${param.fdKey}.load();
	    	}else{
	    		topic.subscribe('/mui/navbar/slideBar',function(view){
	    			if(view && view.moveTo !=""){
	    				var contentNode = registry.byId(view.moveTo);
	    				if(contentNode){
	    					var wpsCloudContainerId = "WPSCENTER_${param.fdKey}";
	    	    				var wpsCloudContainerEle = query('#'+wpsCloudContainerId,contentNode.domNode);
	    	    				if(wpsCloudContainerEle.length > 0){
	    	    					if(!wpsCenterAattachment_${param.fdKey}.hasLoad){
	    	    						wpsCenterAattachment_${param.fdKey}.load();
	    	    					}
	    	    				}
	    				}
	    			}
	    		}); 
	     }
	}
}); 
</script>
