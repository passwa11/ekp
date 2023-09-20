<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.web.taglib.TagUtils,com.landray.kmss.sys.attachment.forms.*,java.util.*"%>
<%@ page import="com.landray.kmss.sys.attachment.service.*"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="java.util.regex.Pattern"%>
<%@ page import="java.util.regex.Matcher"%>
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

	//格式合同add页面、非格式合同部分映射不需要本地初稿按钮
	String fdIssample = null;
	String fdNotSampleType =  null;
	try{
		fdIssample = (String)org.apache.commons.beanutils.PropertyUtils.getProperty(formBean,"fdIssample");
		fdNotSampleType = (String)org.apache.commons.beanutils.PropertyUtils.getProperty(formBean,"fdNotSampleType");
	}catch(Exception e){

	}
	if(fdIssample == null) {
		fdIssample = "false";
	} else {
		if ("false".equals(fdIssample) && "formTomainPart".equals(fdNotSampleType)) {
			fdIssample = "true";
		}
	}
	pageContext.setAttribute("_fdIssample", fdIssample);

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
<c:forEach items="${attForms.attachments}" var="sysAttMain"	varStatus="vstatus">
	<c:set var="attachmentId" value="${sysAttMain.fdId}"/>
</c:forEach>
<c:set var="buttonDiv" value="${param.buttonDiv}" />
<style>
#office-iframe{
	width:100%;
	min-height:700px;
}
</style>

<script>Com_IncludeFile("jquery.js");</script>
<script>Com_IncludeFile("polyfill.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/js/","js",true);</script>
<script>Com_IncludeFile("web-office-sdk-v1.1.16.umd.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/center/js/","js",true);</script>
<script>Com_IncludeFile("wps_center_attachment.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/center/js/","js",true);</script>
<c:if test="${empty buttonDiv}">
	<c:if test="${param.method == 'add' && _fdIssample eq 'false'}">
		<div style="text-align:right;">
			<a href="javascript:void(0);" name="editonline_openLocal" class="attopenLocal" onclick="openLocalFile();">打开本地初稿</a>
		</div>
	</c:if>
</c:if>
<div style="display:none">
	<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
		<c:param name="fdKey" value="${param.fdKey}" />
		<c:param name="fdModelId" value="${fdModelId }" />
		<c:param name="fdModelName" value="${fdModelName }" />
		<c:param name="fdMulti" value="false" />
		<c:param name="addToPreview" value="false" />
		<c:param name="enabledFileType" value=".doc;.docx;.wps;" />
		<c:param name="uploadAfterSelect" value="true" />
	</c:import>
</div>

<div id="WPSCenterOffice_${param.fdKey}" class="wps-container">
</div>
<ui:event event="show">
	if("${param.load}" == 'false'){
		if((!wps_center_${param.fdKey}.hasLoad && !wps_center_${param.fdKey}.isLoading) || wps_center_${param.fdKey}.forceLoad){
			wps_center_${param.fdKey}.load();
		}
	}
</ui:event>

<script>
	function openLocalFile(){
		$("#uploader_${param.fdKey}_container label").click();
	}

	var wps_center_${param.fdKey};

	var wps_center_canPrint='${param.canPrint}';

	var wps_center_canExport='${param.canExport}';

	function setButton(){
		var method = "${param.method}";
		if(method != 'add'){
			return;
		}
		var buttonDiv = "${buttonDiv}";
		if(buttonDiv != null){
			var buttonObj = document.getElementById(buttonDiv);
			if(buttonObj){
				var _createTempInput = function(name,value,flag){
					  var inputDom = document.createElement("a");
					  var a_name = document.createAttribute("name");
					    a_name.nodeValue = name;
					  var a_href = document.createAttribute("href");
					    a_href.nodeValue = "javascript:void(0);";
					  var a_class = document.createAttribute("class");
					     a_class.nodeValue = flag;
					    var a_Text = document.createTextNode(value);
					    inputDom.setAttributeNode(a_href);
					    inputDom.setAttributeNode(a_name);
					    inputDom.setAttributeNode(a_class);
					    inputDom.appendChild(a_Text);
					    return inputDom;
				};
				var _insertDomBefore = function(insertTo,insertObj){
					if(insertTo.firstChild!=null){
						insertTo.insertBefore(insertObj,insertTo.firstChild);
					}else{
						insertTo.appendChild(insertObj);
					}
				};
				// 打开本地初稿
				var _openLocal = _createTempInput("${param.key}" + "_openLocal","打开本地初稿","attopenLocal");
				_openLocal.onclick = function() {
					openLocalFile();
				};
				_insertDomBefore(buttonObj,_openLocal);
				var blank = document.createElement("SPAN");
				blank.innerHTML = "&nbsp;&nbsp;";
				_insertDomBefore(buttonObj,blank);
			}
		}
	}

	$(document).ready(function(){
		setButton();//设置按钮

		var fdAttMainId = "${attachmentId}";
		var flag = false;
		if("${attachmentId}" == ""){
		   //请求在线编辑附件的id
		   var url="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=addCenterOnlineFile";
		   $.ajax({
		 	     type:"post",
		 	     url:url,
		 	     data:{fdTemplateModelId:"${param.fdTemplateModelId}",fdTemplateModelName:"${param.fdTemplateModelName}",fdTemplateKey:"${param.fdTemplateKey}",fdModelName:"${param.fdModelName}",fdKey:"${param.fdKey}",fdTempKey:"${param.fdTempKey}",fdModelId:"${param.fdModelId}",fdFileExt:"${param.fdFileExt}"},
		 	     async:false,    //用同步方式
		 	     dataType:"json",
		 	     success:function(data){
		 	    	if (data){
		 	    		var results = data;
					    if(results['editOnlineAttId']!=null){
						    	fdAttMainId = results['editOnlineAttId'];
						    	wps_center_${param.fdKey} = new WPSCenterOffice_AttachmentObject(fdAttMainId,"${param.fdKey}","${param.fdModelId}","${param.fdModelName}","${isWrite}","normal",wps_center_canPrint,wps_center_canExport);
						    	if("${param.isTemplate}" == 'true'){
						    		wps_center_${param.fdKey}.isTemplate = true;
						    	}
						    	if("${param.load}" != 'false'){
						    		wps_center_${param.fdKey}.load();
						    	}
						}
					}
		 		 }
		    });

		   attachmentObject_${param.fdKey}.uploadAfterCustom = function(id){
		   		if(flag){
		   			wps_center_${param.fdKey}.fdId = id;
					wps_center_${param.fdKey}.load();
					fdAttMainId = id;
		   		}else{
		   			if(id){
						var delUrl="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=delete";
						$.ajax({
					 	     type:"post",
					 	     url:delUrl,
					 	     data:{fdId:fdAttMainId,format:'json'},
					 	     async:false,    //用同步方式
					 	     success:function(data){
					 	    	 if(data){
					 	    		var results =  eval("("+data+")");
					 	    		if(results['status'] && results['status'] == "1"){
						 	    		wps_center_${param.fdKey}.fdId = id;
										wps_center_${param.fdKey}.load();
										fdAttMainId = id;
										flag = true;
						 	    	 }
					 	    	 }
					 		 }
					    });
					}
		   		}

			}

		}else{
			wps_center_${param.fdKey} = new WPSCenterOffice_AttachmentObject(fdAttMainId,"${param.fdKey}","${param.fdModelId}","${param.fdModelName}","${isWrite}","normal",wps_center_canPrint,wps_center_canExport);
			if("${param.isTemplate}" == 'true'){
		    		wps_center_${param.fdKey}.isTemplate = true;
		    	}
			if("${param.load}" != 'false'){
				wps_center_${param.fdKey}.load();
			}

			attachmentObject_${param.fdKey}.uploadAfterCustom = function(id){
	   			wps_center_${param.fdKey}.fdId = id;
				wps_center_${param.fdKey}.load();
				fdAttMainId = id;
			}
		}

	});
</script>