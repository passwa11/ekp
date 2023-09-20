<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.km.smissive.util.KmSmissiveConfigUtil"%>
<%@page import="com.landray.kmss.sys.number.util.NumberResourceUtil"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsWebOfficeUtil"%>
<%@ page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCenterUtil" %>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@ page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsoaassistUtil" %>
<c:set var="wpsoaassistEmbed" value="<%=SysAttWpsoaassistUtil.isWPSOAassistEmbed()%>"/>
<%
	 pageContext.setAttribute("_isJGEnabled", new Boolean(
			com.landray.kmss.sys.attachment.util.JgWebOffice
					.isJGEnabled()));
     pageContext.setAttribute("_isWpsWebOfficeEnable", new Boolean(SysAttWpsWebOfficeUtil.isEnable()));
     pageContext.setAttribute("_isWpsCloudEnable", new Boolean(SysAttWpsCloudUtil.isEnable()));
     //WPS加载项
     pageContext.setAttribute("_isWpsWebOffice", new Boolean(SysAttWpsCloudUtil.isEnableWpsWebOffice()));
	//WPS中台
	pageContext.setAttribute("_isWpsCenterEnable", new Boolean(SysAttWpsCenterUtil.isEnable()));

	Boolean isWindows = Boolean.FALSE;
	if("windows".equals(JgWebOffice.getOSType(request))){
		isWindows = Boolean.TRUE;
	}
	pageContext.setAttribute("isWindowsInOAassist", isWindows);
%>

<template:replace name="content"> 
<c:import url="/sys/recycle/import/redirect.jsp">
	<c:param name="formBeanName" value="kmSmissiveMainForm"></c:param>
</c:import>
	<script>
	Com_IncludeFile("doclist.js|dialog.js|calendar.js|optbar.js|jquery.js|popwin.js");
    </script>
    <c:if test="${kmSmissiveMainForm.method_GET=='add'}">
			<script language="JavaScript">
				function changeDocCate(modeName,url,canClose) {
					if(modeName==null || modeName=='' || url==null || url=='')
						return;
					seajs.use(['sys/ui/js/dialog'],	function(dialog) {
						dialog.simpleCategoryForNewFile(modeName,url,false,
						function(rtn) {
							// 无分类状态下（一般于门户快捷操作）创建文档，取消操作同时关闭当前窗口
							if (!rtn)
								window.close();
						},null,null,"_self",canClose);
					});
				};
			
			    var _doc_create_url='/km/smissive/km_smissive_main/kmSmissiveMain.do?method=add&categoryId=!{id}';
			    if('${JsParam.categoryId}'=='' && '${JsParam.fdTemplateId}'==''){
			   		changeDocCate('com.landray.kmss.km.smissive.model.KmSmissiveTemplate',_doc_create_url,true);
			    }
			</script>
		</c:if>
    <script>
		//当前页签是否是word 内嵌加载项
		var curTabWordIsEmbeddedAddons = false;
	function submitForm(method, status){
		//00废弃 10草稿 11被驳回 20审批中 30已发布 40已过期
		if(status!=null){
			document.getElementsByName("docStatus")[0].value = status;
		}
		var flag = true;
	 	if('${aaa['modifyDocNum4Draft']}' == 'true'&&'${kmSmissiveMainForm.method_GET}'=='add'){
			var docNum = document.getElementsByName("fdFileNo")[0];
		   if(""==docNum.value){
			   if("${fdNoId}" != ""){
			     generateAutoNum();
			     flag = true;
			   }else{
				   flag = confirm('<bean:message key="kmSmissive.number.notNull" bundle="km-smissive" />');
			   }
		   }else{
			var url="${KMSS_Parameter_ContextPath}km/smissive/km_smissive_main/kmSmissiveMain.do?method=checkUniqueNum"; 
			 $.ajax({     
			     type:"post",   
			     url:url,     
			     data:{fdNo:docNum.value,fdId:"${kmSmissiveMainForm.fdId}",fdTempId:"${kmSmissiveMainForm.fdTemplateId}"},
			     async:false,    //用同步方式 
				 dataType:"json",
				 success:function(results){
			    	 if(results['unique'] =='false'){
			    		 if("${fdNoId}"!=""){
			 		    	var docBufferNum = getTempNumberFromDb("${fdNoId}");
			 			    if(docBufferNum && docNum.value == docBufferNum){
			 					 delTempNumFromDb("${fdNoId}",decodeURI(docBufferNum));
			 			    }
			 			 }
			    		 alert('<bean:message key="kmSmissiveMain.message.error.fdDocNum.repeat" bundle="km-smissive" />');
			    		 flag = false;
			    	 }
				}    
		 });	
		}
	  } 
		if(flag){
			//提交时判断是否需要正文，如果不需要则移除页面控件对象
			 var type =  document.getElementsByName("fdNeedContent");
			 var _isWpsCloudEnable = "${_isWpsCloudEnable}";
			 var _isWpsWebOfficeEnable = "${_isWpsWebOfficeEnable}";
			 var _isWpsWebOffice = "${_isWpsWebOffice}";
			var _isWpsCenterEnable = "${_isWpsCenterEnable}";
			 if(_isWpsCloudEnable != "true" && _isWpsWebOfficeEnable != "true" && _isWpsWebOffice != "true" && _isWpsCenterEnable != "true"){
				 var obj = document.getElementById("JGWebOffice_mainOnline");
		       	 if(type[0].value !="1"){
			       	 if("${kmSmissiveMainForm.method_GET}"=="add"){
			       		 if(obj&&Attachment_ObjectInfo['mainOnline']&&jg_attachmentObject_mainOnline.hasLoad){
			       			jg_attachmentObject_mainOnline.unLoad();
			       		 }
			       	 }
		       	}else{
			       	 if(obj&&Attachment_ObjectInfo['mainOnline']&&jg_attachmentObject_mainOnline.hasLoad){
			       		jg_attachmentObject_mainOnline.ocxObj.Active(true);
			       		if(!jg_attachmentObject_mainOnline._submit()){
		    		    	return false;
		    		    }
			       	 }else{
			       		dialog.alert('<bean:message key="kmSmissiveMain.create.support" bundle="km-smissive" />');
			       		return;
			       	 }
		         }
			 }
	       	 
			Com_Submit(document.kmSmissiveMainForm, method);
			//删除编号缓存,避免新建每次取到同一编号
			if("${fdNoId}"!=""){
		    	var docBufferNum = getTempNumberFromDb("${fdNoId}");
			    if(docBufferNum && docNum.value == docBufferNum){
					 delTempNumFromDb("${fdNoId}",decodeURI(docBufferNum));
			    }
			 }
		}
	}
	//解决当在新窗口打开主文档时控件显示不全问题，这里打开时即为最大化修改by张文添
	function max_window(){
		window.moveTo(0, 0);
		window.resizeTo(window.screen.availWidth, window.screen.availHeight);
	}
	//max_window();
	Com_AddEventListener(window, "load", function() {
		checkEditType("${kmSmissiveMainForm.fdNeedContent}",null);
		if ("${pageScope._isWpsWebOfficeEnable}" != "true" || "${pageScope._isWpsCloudEnable}" != "true" || "${pageScope._isWpsCenterEnable}" != "true") {
			//解决非ie下控件高度问题
			var obj = document.getElementById("JGWebOffice_mainOnline");
			if(obj){
				obj.setAttribute("height", "600px");
			}
		}
	});
	function checkEditType(value){
		var type=document.getElementsByName("fdNeedContent")[0];
		type.value = "0";
		var _wordEdit = $('#wordEdit');
		var _attEdit = $('#attEdit');
		var wordFloat = $("#wordEditFloat");
		var missiveButtonDiv = $("#missiveButtonDiv");
		if("1" == value){
			type.value = "1";
			_wordEdit.css({'display':"block",'width':"100%",'height':"auto"});
			var xw = $("#wordEditWrapper").width();
			wordFloat.css({'width':xw+'px','height':'600px','filter':'alpha(opacity=100)','opacity':'1'});
			_attEdit.css({'display':"none"});
			
			
			if ("${pageScope._isWpsWebOfficeEnable}" == "true" ){
				wps_mainOnline.load();
			}else if("${pageScope._isWpsCloudEnable}" == "true"){
				wps_cloud_mainOnline.load();
			}else if("${pageScope._isWpsCenterEnable}" == "true"){
				wps_center_mainOnline.load();
			}else if("${pageScope._isWpsWebOffice}" == "true" && "${pageScope.wpsoaassistEmbed}" == "false"){
				$('#uploader_mainOnline').show();
				$('.upload_list_tr_edit_r').css({'width':'220px'});
				_wordEdit.css({'display':"block",'width':"100%",'height':"150px"});
				var xw = $("#wordEditWrapper").width();
				missiveButtonDiv.css({'width':xw+'px','height':'25px','filter':'alpha(opacity=100)','opacity':'1','overflow':'hidden'});
				$('.upload_list_tr_edit_l').css({'width':'700px'});
				$('.upload_list_filename_edit').css({'width':'600px'});
				$('.upload_list_tr_edit_r').css({'width':'350px'});
			}else if("${pageScope._isWpsWebOffice}" == "true" && "${pageScope.wpsoaassistEmbed}" == "true" && "${pageScope.isWindowsInOAassist}" == "false"){
				_wordEdit.css({'display':"block",'width':"98%",'height':"500px"});
				curTabWordIsEmbeddedAddons = true;
				setTimeout(function(){
					wps_linux_mainOnline.load();
					$("#wpsLinux_mainOnline").css('height','460px');
				},500);
			}else{
				_wordEdit.css({'display':"block",'width':"100%",'height':"600px"});
				missiveButtonDiv.css({'width':xw+'px','height':'25px','filter':'alpha(opacity=100)','opacity':'1'});
				//金格涉及到加载的情况需要加个延时,由于金格加载较慢，暂定延时1秒
				var obj = document.getElementById("JGWebOffice_mainOnline");
				setTimeout(function(){
					 if(obj&&Attachment_ObjectInfo['mainOnline'] && !jg_attachmentObject_mainOnline.hasLoad){
						jg_attachmentObject_mainOnline.load();
						jg_attachmentObject_mainOnline.show();
						jg_attachmentObject_mainOnline.ocxObj.Active(true);
					 }
					},1000);			
				seajs.use(['lui/topic'],function(topic){
					topic.subscribe("Sidebar",function(data){
						var xw = $("#wordEditWrapper").width();
						wordFloat.css({'width':xw+'px','height':'600px','filter':'alpha(opacity=100)','opacity':'1','overflow':'hidden'});
						missiveButtonDiv.css({'width':xw+'px','height':'25px','filter':'alpha(opacity=100)','opacity':'1','overflow':'hidden'});
					});
				});
				$("#JGWebOffice_editonline").height("600px");
				chromeHideJG_2015(1);
			}
		} else {
			//内嵌加载项切换页签时保存当前内容
			if('${pageScope._isWpsWebOffice}' == "true"&&"${wpsoaassistEmbed}"=="true"&&"${isWindowsInOAassist}"=="false"&&curTabWordIsEmbeddedAddons == true){
				curTabWordIsEmbeddedAddons = false;
				wps_linux_mainOnline.setTmpFileByAttKey();
				wps_linux_mainOnline.isCurrent=false;
			}
			 if("${pageScope._isWpsWebOffice}" == "true"){
					$('#uploader_mainOnline').hide();
				}
			_attEdit.css({'display':"block"}); 
			missiveButtonDiv.css({'width':'0px','height':'0px','filter':'alpha(opacity=0)','opacity':'0'});
			if ("${pageScope._isWpsWebOfficeEnable}" == "true" || "${pageScope._isWpsCloudEnable}" == "true" || "${pageScope._isWpsWebOffice}" == "true" || "${pageScope._isWpsCenterEnable}" == "true") {
				_wordEdit.css({'display':'none','width':"100%",'height':"0px"});
			} else {
				_wordEdit.css({'width':"0px",'height':"0px"});
			}
			wordFloat.css({'width':'0px','height':'0px','filter':'alpha(opacity=0)','opacity':'0'});
			chromeHideJG_2015(0);
		}
	}
</script>
<c:if test="${param.approveModel eq 'right'}">
	<script>
		$(document).bind("slideSpread",function(){
			 setTimeout(function(){
				var wordFloat = $("#wordEditFloat");
				var missiveButtonDiv = $("#missiveButtonDiv");
				var xw = $("#wordEditWrapper").width();
				wordFloat.css('width',xw+'px');
				missiveButtonDiv.css('width',xw+'px');
			 },300);
	    });
	</script>
</c:if>
<c:if test="${param.approveModel ne 'right'}">
	<form name="kmSmissiveMainForm" method="post" action ="${KMSS_Parameter_ContextPath}km/smissive/km_smissive_main/kmSmissiveMain.do">
</c:if>	
<p class="txttitle"><c:out value="${kmSmissiveMainForm.fdTitle}"></c:out></p>
<html:hidden property="fdId"/>
<html:hidden property="fdTitle"/>
<html:hidden property="fdTemplateId"/>
<html:hidden property="docStatus" />
<html:hidden property="docPublishTime" />
<html:hidden property="fdFlowFlag"/>
	<div class="lui_form_content_frame" style="padding-top:10px">
        <html:hidden property="fdNeedContent" />
	    <table class="tb_normal" width="100%">
	       <c:if test="${kmSmissiveMainForm.method_GET=='add'}">
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message key="kmSmissiveTemplate.fdNeedContent" bundle="km-smissive" />
					</td>
					<td width="85%">
						<xform:radio property="fdEditType" showStatus="edit" value="${kmSmissiveMainForm.fdNeedContent}" onValueChange="checkEditType">
							<xform:enumsDataSource enumsType="kmSmissiveTemplate_fdNeedContent" />
						</xform:radio>
					</td>
				</tr>
			</c:if>
			<tr>
				<td colspan="2">
				<div id="wordEdit" style="width:100%;height:0px;">
					<c:choose>
						<c:when test="${pageScope._isWpsCloudEnable == 'true'}">
							<div id="missiveButtonDiv" style="text-align:right;padding-bottom:5px">&nbsp;
								 <a href="javascript:void(0);" class="attbook" onclick="Com_OpenWindow(Com_Parameter.ContextPath+'km/smissive/bookMarks.jsp','_blank');">
								    <bean:message key="kmSmissiveMain.bookMarks.title" bundle="km-smissive"/>
								 </a>
							</div>
							<c:import url="/sys/attachment/sys_att_main/wps/cloud/sysAttMain_edit.jsp" charEncoding="UTF-8">
								<c:param name="fdKey" value="mainOnline" />
								<c:param name="load" value="false" />
								<c:param name="bindSubmit" value="false"/>	
								<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
								<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
								<c:param name="formBeanName" value="kmSmissiveMainForm" />
								<c:param name="fdTemplateModelId" value="${param.categoryId}" />
								<c:param name="fdTemplateModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveTemplate" />
								<c:param name="fdTemplateKey" value="mainContent" />
								<c:param name="fdTempKey" value="${param.categoryId}" />
								<c:param name="buttonDiv" value="missiveButtonDiv" />
							</c:import>
						</c:when>
						<c:when test="${pageScope._isWpsCenterEnable == 'true'}">
							<div id="missiveButtonDiv" style="text-align:right;padding-bottom:5px">&nbsp;
								<a href="javascript:void(0);" class="attbook" onclick="Com_OpenWindow(Com_Parameter.ContextPath+'km/smissive/bookMarks.jsp','_blank');">
									<bean:message key="kmSmissiveMain.bookMarks.title" bundle="km-smissive"/>
								</a>
							</div>
							<c:import url="/sys/attachment/sys_att_main/wps/center/sysAttMain_edit.jsp" charEncoding="UTF-8">
								<c:param name="fdKey" value="mainOnline" />
								<c:param name="load" value="false" />
								<c:param name="bindSubmit" value="false"/>
								<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
								<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
								<c:param name="formBeanName" value="kmSmissiveMainForm" />
								<c:param name="fdTemplateModelId" value="${param.categoryId}" />
								<c:param name="fdTemplateModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveTemplate" />
								<c:param name="fdTemplateKey" value="mainContent" />
								<c:param name="fdTempKey" value="${param.categoryId}" />
								<c:param name="buttonDiv" value="missiveButtonDiv" />
							</c:import>
						</c:when>
						<c:when test="${pageScope._isWpsWebOffice == 'true'}">
						<div id="wordEditWrapper"></div>
							<div id="missiveButtonDiv" style="text-align:right;padding-bottom:5px">&nbsp;
								 <a href="javascript:void(0);" class="attbook" onclick="Com_OpenWindow(Com_Parameter.ContextPath+'km/smissive/bookMarks.jsp','_blank');">
								    <bean:message key="kmSmissiveMain.bookMarks.title" bundle="km-smissive"/>
								 </a>
							</div>

							<c:import url="/sys/attachment/sys_att_main/wps/oaassist/sysAttMain_edit.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="mainOnline" />
										<c:param name="fdMulti" value="false" />
										<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
										<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
										<c:param name="formBeanName" value="kmSmissiveMainForm" />
										<c:param name="fdTemplateModelId" value="${param.fdTemplateId}" />
										<c:param name="fdTemplateModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveTemplate" />
										<c:param name="fdTemplateKey" value="mainContent" />
										<c:param name="templateBeanName" value="kmSmissiveTemplateForm" />
										<c:param name="showDelete" value="false" />
										<c:param name="wpsExtAppModel" value="kmSmissive" />
										<c:param name="redhead" value="${redhead}" />
										<c:param name="bookMarks" value="${bookmarkJson}" />
										<c:param name="nodevalue" value="${nodevalue}" />
										<c:param name="wpsExtAppModel" value="kmSmissive" />
										<c:param  name="signtrue"  value="${signtrue}"/>
										<c:param name="canDownload" value="${canDownload}" />
										<c:param name="newFlag" value="true" />
										<c:param  name="hideReplace"  value="true"/>
										<c:param name="canEdit" value="true" />
							            <c:param name="bookMarks" value='{"docSubject":"${kmSmissiveMainForm.docSubject}","docAuthorName":"${kmSmissiveMainForm.docAuthorName}","fdUrgency":"${kmSmissiveMainForm.fdUrgencyName}","fdTemplateName":"${kmSmissiveMainForm.fdTemplateName}","docCreateTime":"${kmSmissiveMainForm.docCreateTime}","fdSecret":"${kmSmissiveMainForm.fdSecretName}","fdFileNo":"${kmSmissiveMainForm.fdFileNo}","fdMainDeptName":"${kmSmissiveMainForm.fdMainDeptName}","fdSendDeptNames":"${kmSmissiveMainForm.fdSendDeptNames}","fdCopyDeptNames":"${kmSmissiveMainForm.fdCopyDeptNames}","fdIssuerName":"${kmSmissiveMainForm.fdIssuerName}","docCreatorName":"${kmSmissiveMainForm.docCreatorName}"}'/>
										<c:param name="load" value="false" />
							</c:import>

						</c:when>
						<c:when test="${pageScope._isWpsWebOfficeEnable == 'true'}">
							<div id="missiveButtonDiv" style="text-align:right;padding-bottom:5px">&nbsp;
								 <a href="javascript:void(0);" class="attbook" onclick="Com_OpenWindow(Com_Parameter.ContextPath+'km/smissive/bookMarks.jsp','_blank');">
								    <bean:message key="kmSmissiveMain.bookMarks.title" bundle="km-smissive"/>
								 </a>
							</div>
							<c:import url="/sys/attachment/sys_att_main/wps/sysAttMain_edit.jsp" charEncoding="UTF-8">
								<c:param name="fdKey" value="mainOnline" />
								<c:param name="load" value="true" />
								<c:param name="bindSubmit" value="false"/>	
								<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
								<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
								<c:param name="formBeanName" value="kmSmissiveMainForm" />
								<c:param name="fdTemplateModelId" value="${param.categoryId}" />
								<c:param name="fdTemplateModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveTemplate" />
								<c:param name="fdTemplateKey" value="mainOnline" />
								<c:param name="fdTempKey" value="${param.categoryId}" />
								<c:param name="buttonDiv" value="missiveButtonDiv" />
							</c:import>
						</c:when>
						<c:otherwise>
							<c:if test="${ pageScope._isJGEnabled == 'true'}">
								<div id="wordEditWrapper"></div>
								<div id="wordEditFloat" style="position: absolute;width:1px;height:1px;filter:alpha(opacity=0);opacity:0;">
								<%
							   		String jgOcxVersion = JgWebOffice.getJGBigVersion();
							   		if (null != jgOcxVersion && jgOcxVersion.equals(JgWebOffice.JG_OCX_BIG_VERSION_2015)) {
							   			request.setAttribute("isIE",true);
							   		} else {
							   			if(request.getHeader("User-Agent").toUpperCase().indexOf("MSIE")>-1){
											request.setAttribute("isIE",true);
										}else if(request.getHeader("User-Agent").toUpperCase().indexOf("TRIDENT")>-1){
											request.setAttribute("isIE",true);
										}else{
											request.setAttribute("isIE",false);
										}
							   		}
								%>
								<c:choose>
									<c:when test="${isIE}">
									<div id="missiveButtonDiv" style="text-align:right;padding-bottom:5px">&nbsp;
									 <a href="javascript:void(0);" class="attbook" onclick="Com_OpenWindow(Com_Parameter.ContextPath+'km/smissive/bookMarks.jsp','_blank');">
									    <bean:message key="kmSmissiveMain.bookMarks.title" bundle="km-smissive"/>
									 </a>
									</div>
									</c:when>
									 <c:otherwise>
									    <%if(com.landray.kmss.sys.attachment.util.JgWebOffice.isJGMULEnabled()){%>
										 <div id="missiveButtonDiv" style="text-align:right;padding-bottom:5px">&nbsp;
											 <a href="javascript:void(0);" class="attbook" onclick="Com_OpenWindow(Com_Parameter.ContextPath+'km/smissive/bookMarks.jsp','_blank');">
											    <bean:message key="kmSmissiveMain.bookMarks.title" bundle="km-smissive"/>
											 </a>
										</div>
										 <%} %>
									 </c:otherwise>
								</c:choose>
							    <c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_edit.jsp" charEncoding="UTF-8">
									<c:param name="fdKey" value="mainOnline" />
									<c:param name="fdAttType" value="office" />
									<c:param name="load" value="false" />
									<c:param name="bindSubmit" value="false"/>
									<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
									<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
									<c:param name="fdTemplateModelId" value="${kmSmissiveMainForm.fdTemplateId}" />
									<c:param name="fdTemplateModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveTemplate" />
									<c:param name="fdTemplateKey" value="mainContent" />
									<c:param name="templateBeanName" value="kmSmissiveTemplateForm" />
									<c:param name="bookMarks" value="docSubject:${kmSmissiveMainForm.docSubject},docAuthorName:${kmSmissiveMainForm.docAuthorName},fdUrgency:${kmSmissiveMainForm.fdUrgencyName},fdTemplateName:${kmSmissiveMainForm.fdTemplateName},docCreateTime:${kmSmissiveMainForm.docCreateTime},fdSecret:${kmSmissiveMainForm.fdSecretName},fdFileNo:${kmSmissiveMainForm.fdFileNo},fdMainDeptName:${kmSmissiveMainForm.fdMainDeptName},fdSendDeptNames:${kmSmissiveMainForm.fdSendDeptNames},fdCopyDeptNames:${kmSmissiveMainForm.fdCopyDeptNames},fdIssuerName:${kmSmissiveMainForm.fdIssuerName},docCreatorName:${kmSmissiveMainForm.docCreatorName}" />
									<c:param name="buttonDiv" value="missiveButtonDiv" />
									<c:param name="showDefault" value="true"/>
									<c:param name="isToImg" value="fasle"/>
								</c:import>
								</div>
							</c:if>
						</c:otherwise>
					</c:choose>
				</div>
				<div id="attEdit" <c:if test="${kmSmissiveMainForm.fdNeedContent!='0'}">style="display:none"</c:if>>
			      <div class="lui_form_spacing"></div> 
		            <div>
					   <div class="lui_form_subhead"><img src="${KMSS_Parameter_ContextPath}sys/attachment/view/img/attachment.png"><bean:message key="kmSmissiveMain.mainonline" bundle="km-smissive" /></div>
						<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
							<c:param name="fdKey" value="editOnline"/>
							<c:param  name="fdMulti" value="false" />
							<c:param name="uploadAfterSelect" value="true" /> 
							<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId }" />
							<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
						</c:import>
					</div>
				</div>
			</td>
			</tr>
		</table>
	</div>
	<div class="lui_form_content_frame" style="padding-top:10px">
		<div class="lui_form_spacing"></div> 
		<div>
			<div class="lui_form_subhead"><img src="${KMSS_Parameter_ContextPath}sys/attachment/view/img/attachment.png"> ${ lfn:message('sys-doc:sysDocBaseInfo.docAttachments') }</div>
		    <c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp"
				charEncoding="UTF-8">
				<c:param name="fdKey" value="mainAtt" />
				<c:param name="uploadAfterSelect" value="true" /> 
			</c:import> 
		</div> 	
    </div>
    <c:choose>
		<c:when test="${param.approveModel eq 'right'}">
			<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="6" var-average='false' var-useMaxWidth='true'
				var-supportExpand="true" var-expand="true">	
				<%@ include file="/km/smissive/km_smissive_main_ui/kmSmissiveMain_editContent.jsp"%>
			</ui:tabpanel>
		</c:when>
		<c:otherwise>
			<ui:tabpage expand="false" >
				<%@ include file="/km/smissive/km_smissive_main_ui/kmSmissiveMain_editContent.jsp"%>
			</ui:tabpage>
		</c:otherwise>
	</c:choose>	
	<html:hidden property="method_GET"/>
<c:if test="${param.approveModel ne 'right'}">
	</form>
</c:if>
<%@ include file="/km/smissive/cookieUtil_script.jsp"%>
<script>
$(document).ready(function(){
	<c:if  test="${aaa['modifyDocNum4Draft'] =='true' and kmSmissiveMainForm.method_GET=='add'}">
	//generateAutoNum();
	</c:if>
});
seajs.use(['sys/ui/js/dialog'], function(dialog) {
	window.dialog = dialog;
});

//文档加载时自动获取文号
function generateAutoNum(){
	var docNum = document.getElementsByName("fdFileNo")[0];
	 if(getTempNumberFromDb("${fdNoId}")){
		 var NumberFromDb = getTempNumberFromDb("${fdNoId}");
		 docNum.value = NumberFromDb;
		 document.getElementById("docnum").innerHTML = NumberFromDb;
		//填充控件中的文号书签 
	      if(Attachment_ObjectInfo['mainOnline'] && typeof(Attachment_ObjectInfo['mainOnline'].setBookmark) == 'function'){
	          Attachment_ObjectInfo['mainOnline'].setBookmark('fdFileNo',document.getElementsByName("fdFileNo")[0].value);
	      }
	 }else{
		 var url="${KMSS_Parameter_ContextPath}km/smissive/km_smissive_main/kmSmissiveMain.do?method=generateNumByNumberId"; 
		 $.ajax({     
  	     type:"post",   
  	     url:url,     
  	     data:{fdNumberId:"${fdNoId}",fdId:"${kmSmissiveMainForm.fdId}"},
  	     async:false,    //用同步方式 
		 dataType:"json",
		 success:function(results){
  		    if(results['docNum']!=null){
  		   	   docNum.value = results['docNum'];
  		       document.getElementById("docnum").innerHTML = results['docNum'];
  		       //填充控件中的文号书签 
  		        if(Attachment_ObjectInfo['mainOnline'] && typeof(Attachment_ObjectInfo['mainOnline'].setBookmark) == 'function'){
  		           Attachment_ObjectInfo['mainOnline'].setBookmark('fdFileNo',document.getElementsByName("fdFileNo")[0].value);
  		      }
  			}
  		 }    
     });
	 }
 }

//文件编号
function generateFileNum(){
	if("${fdNoId}" !=""){
	        var docNum = document.getElementsByName("fdFileNo")[0];
		    path=Com_GetCurDnsHost()+Com_Parameter.ContextPath+'km/smissive/km_smissive_main_ui/kmSmissiveNum.jsp?fdId=${kmSmissiveMainForm.fdId}&fdNumberId=${fdNoId}&isAdd=true';
		    dialog.iframe(path,"文件编号",function(rtn){
			  if(rtn!="undefined"&&rtn!=null){
	    		  docNum.value = rtn;
	   		      document.getElementById("docnum").innerHTML = rtn;
	   		      //填充控件中的文号书签
	   		      if(Attachment_ObjectInfo['mainOnline'] && typeof(Attachment_ObjectInfo['mainOnline'].setBookmark) == 'function'){
	   		         Attachment_ObjectInfo['mainOnline'].setBookmark('fdFileNo',document.getElementsByName("fdFileNo")[0].value);
	   		      }
			  }
		   },{width:400,height:200});
	}
}
</script>
<script language="JavaScript">
	$KMSSValidation(document.forms['kmSmissiveMainForm']);
</script>
<script language="javascript" for="window" event="onload">
	//var obj = document.getElementsByName("mainContent_bookmark");	//公司产品JS库有问题，其中生成的按钮没有ID属性，导致ie6不能够用该语句查找
	//obj[0].style.display = "none";
	var tt = document.getElementsByTagName("INPUT");
	
	for(var i=0;i<tt.length;i++){
		
		if(tt[i].name == "mainOnline_printPreview"){
			tt[i].style.display = "none";
		}
		
	}
</script>
</template:replace>
<c:choose>
	<c:when test="${param.approveModel eq 'right'}">
		<template:replace name="barRight">
			<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
				<c:choose>
					<c:when test="${kmSmissiveMainForm.docStatus>='30' || kmSmissiveMainForm.docStatus=='00'}">
						<!-- 基本信息-->
						<c:import url="/km/smissive/km_smissive_main_ui/kmSmissiveMain_viewBaseInfo.jsp" charEncoding="UTF-8"></c:import>
					</c:when>
					<c:otherwise>
						<%--流程--%>
						<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmSmissiveMainForm" />
							<c:param name="fdKey" value="smissiveDoc" />
							<c:param name="showHistoryOpers" value="true" />
							<c:param name="isExpand" value="true" />
							<c:param name="approveType" value="right" />
							<c:param name="approvePosition" value="right" />
						</c:import>
					</c:otherwise>
				</c:choose>
				<!-- 关联机制(与原有机制有差异) -->
				<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmSmissiveMainForm" />
					<c:param name="approveType" value="right" />
					<c:param name="needTitle" value="true" />
				</c:import>
			</ui:tabpanel>
		</template:replace>
	</c:when>
	<c:otherwise>
		<template:replace name="nav">
				<!-- 关联机制 -->
				<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmSmissiveMainForm" />
			    </c:import>
				<!-- 关联机制 -->
		</template:replace> 
	</c:otherwise>
</c:choose>

