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

	//得到文档状态，用于控制留痕按钮在发布状态中不显示
	String docStatus = null;
	try{
		docStatus = (String)org.apache.commons.beanutils.PropertyUtils.getProperty(formBean,"docStatus");
		pageContext.setAttribute("_docStatus", docStatus);
	}catch(Exception e){
	}
%>
<c:set var="fdKey" value="${param.fdKey}" />
<c:set var="fdModelId" value="${param.fdModelId}" />
<c:set var="fdModelName" value="${param.fdModelName}" />
<c:set var="attForms" value="${_formBean.attachmentForms[fdKey]}" />
<c:set var="attachmentId" value=""/>
<c:set var="fdFileName" value="" />
<c:forEach items="${attForms.attachments}" var="sysAttMain"	varStatus="vstatus">
	<c:set var="attachmentId" value="${sysAttMain.fdId}"/>
	<c:set var="fdFileName" value="${sysAttMain.fdFileName}" />
</c:forEach>

<script>Com_IncludeFile("jquery.js");</script>
<script>Com_IncludeFile("wps_linux_attachment.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/oaassist/linux/js/","js",true);</script>
<c:set var="buttonDiv" value="${param.buttonDiv}" />
<c:if test="${empty buttonDiv}">
	<c:if test="${param.method == 'add'}">
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

<div style="width: 100%;height: 100%">
	<div id="wpsLinux_${param.fdKey}" style="width: 100%;height: 700px;">
	</div>
</div>
<ui:event event="show">
	if("${param.load}" == 'false'){
		if((!wps_linux_${param.fdKey}.hasLoad && !wps_linux_${param.fdKey}.isLoading) || wps_linux_${param.fdKey}.forceLoad){
			wps_linux_${param.fdKey}.load();
		}
	}
</ui:event>
<script>

	function openLocalFile(){
		$("#uploader_${param.fdKey}_container label").click();
	}

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

	var wps_linux_${param.fdKey};

	$(document).ready(function(){
		setButton();//设置按钮

		var fdAttMainId = "${attachmentId}";
		var fdFileName = '${fdFileName}';
		if("${attachmentId}" != ""){
			wps_linux_${param.fdKey}  = new WPSLinuxOffice_AttachmentObject(fdAttMainId,"${param.fdKey}","${param.fdModelId}","${param.fdModelName}","write",fdFileName);
			if("${param.contentFlag}" != "" && "${param.contentFlag}" == "true" && "${_docStatus}" == "30"){
				wps_linux_${param.fdKey}.contentFlag = true;
			}
			if("${param.load}" != 'false'){
				wps_linux_${param.fdKey}.load();
			}
		}

		var flag = false;
		attachmentObject_${param.fdKey}.uploadAfterCustom = function(id){
			if(flag){
				wps_linux_${param.fdKey}.fdId = id;
				wps_linux_${param.fdKey}.load();
				fdAttMainId = id;
			}else{
				if(id){
					var delUrl="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=delete";
					$.ajax({
						type:"post",
						url:delUrl,
						data:{fdId:fdAttMainId,format:'json'},
						async:false,    //用同步方式
						dataType:"json",
						success:function(results){
							if(results){
								if(results['status'] && results['status'] == "1"){
									wps_linux_${param.fdKey}.fdId = id;
									wps_linux_${param.fdKey}.load();
									fdAttMainId = id;
									flag = true;
								}
							}
						}
					});
				}
			}

		}



	});

</script>