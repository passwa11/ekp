<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.landray.kmss.util.ResourceUtil,java.io.File"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.news.model.SysNewsConfig"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsWebOfficeUtil"%>
<%@ page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCenterUtil" %>

<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@ page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsoaassistUtil" %>
<c:set var="wpsoaassistEmbed" value="<%=SysAttWpsoaassistUtil.isWPSOAassistEmbed(request)%>"/>

<%
	 pageContext.setAttribute("_isJGEnabled", new Boolean(
			com.landray.kmss.sys.attachment.util.JgWebOffice
					.isJGEnabled()));
   	 SysNewsConfig sysNewsConfig = new SysNewsConfig();
     pageContext.setAttribute("ImageW",sysNewsConfig.getfdImageW());
     pageContext.setAttribute("ImageH",sysNewsConfig.getfdImageH());
     pageContext.setAttribute("_isWpsWebOfficeEnable", new Boolean(SysAttWpsWebOfficeUtil.isEnable()));
   //WPS加载项
     pageContext.setAttribute("_isWpsWebOffice", new Boolean(SysAttWpsCloudUtil.isEnableWpsWebOffice()));
     pageContext.setAttribute("_isWpsCloudEnable", new Boolean(SysAttWpsCloudUtil.isEnable()));
	//wps中台
	pageContext.setAttribute("_isWpsCenterEnable", new Boolean(SysAttWpsCenterUtil.isEnable()));

	Boolean isWindows = Boolean.FALSE;
	if("windows".equals(JgWebOffice.getOSType(request))){
		isWindows = Boolean.TRUE;
	}
	pageContext.setAttribute("isWindowsInOAassist", isWindows);
%>

<%-- 内容 --%>
<template:replace name="content"> 
	<style>
		.task_slideDown {margin-left: 12px;padding-left: 15px;font-size: 12px;text-decoration: underline;background: url(../images/icon_arrowd_blue.png) no-repeat 0 3px;cursor: pointer;}
		.task_slideUp {margin-left: 12px;padding-left: 15px;font-size: 12px;text-decoration: underline;background: url(../images/icon_arrowU_blue.png) no-repeat 0 3px;cursor: pointer;}
	</style>
	<script>
		Com_IncludeFile("jquery.js|calendar.js|dialog.js");
		window.changeDocTemp = function(modelName,url,canClose){
			if(modelName==null || modelName=='' || url==null || url=='')
				return;
	 		seajs.use(['sys/ui/js/dialog'],function(dialog) {
			 	dialog.simpleCategoryForNewFile(modelName,url,false,
			 			function(rtn) {
					// 无分类状态下（一般于门户快捷操作）创建文档，取消操作同时关闭当前窗口
					if (!rtn)
						window.close();
				},null,'${JsParam.categoryId}','_self',canClose);
		 	});
	 	};

		  <c:if test="${sysNewsMainForm.method_GET=='add'}">
			var fdModelId='${JsParam.fdModelId}';
			var fdModelName='${JsParam.fdModelName}';
			var url='/sys/news/sys_news_main/sysNewsMain.do?method=add&fdTemplateId=!{id}&fdTemplateName=!{name}';
			if(fdModelId!=null&&fdModelId!=''){
				url+="&fdModelId="+fdModelId+"&fdModelName="+fdModelName;
			}
			if('${JsParam.fdTemplateId}'==''){
				window.changeDocTemp('com.landray.kmss.sys.news.model.SysNewsTemplate',url,true);
			}
		  </c:if>
         
	 	
		
		//展开、收起
		function showMoreSet(){
			if(document.getElementById("show_more_set_id").style.display==""){
				document.getElementById("showMoreSet").className ="task_slideDown";
				document.getElementById("showMoreSet").innerHTML="<bean:message bundle='sys-news' key='sysNewsMain.more.set.slideDown' />";
				document.getElementById("show_more_set_id").style.display="none";
			}else{
				document.getElementById("showMoreSet").className ="task_slideUp";
				document.getElementById("showMoreSet").innerHTML="<bean:message bundle='sys-news' key='sysNewsMain.more.set.slideUp' />";
				document.getElementById("show_more_set_id").style.display="";
				window.scrollBy(0,140);
			}
		}

		//按钮提交
		function submitForm(method, status){
			//提交时根据编辑方式，清空没有选中的编辑方式的文档内容
			var editTypeObj = document.getElementsByName("fdEditType");
			for(var i=0;i<editTypeObj.length;i++){
				if(!editTypeObj[i].checked){
					if("rtf" == editTypeObj[i].value){
						RTF_SetContent("docContent","");//清空rtf域的内容
					}
				}
			}
			if(status!=null){
				document.getElementsByName("docStatus")[0].value = status;
			}
			if("savePreview" == method) {
				seajs.use(['sys/ui/js/dialog'],function(dialog) {
					var formObj = document.sysNewsMainForm;
					//提交表单校验
					for(var i=0; i<Com_Parameter.event["submit"].length; i++){
						if(!Com_Parameter.event["submit"][i](formObj, method)){
							if (Com_Submit.ajaxCancelSubmit) {
								Com_Submit.ajaxCancelSubmit(formObj);
							}
							return false;
						}
					}
					//提交表单消息确认
					for(var i=0; i<Com_Parameter.event["confirm"].length; i++){
						var promise = Com_Parameter.event["confirm"][i](formObj, method);
						if(typeof promise === 'boolean' && !promise){
							if (Com_Submit.ajaxCancelSubmit) {
								Com_Submit.ajaxCancelSubmit(formObj);
							}
							return false;
						}
					}
					if (typeof (CKEDITOR) != 'undefined') {
						for (instance in CKEDITOR.instances) {
							CKEDITOR.instances[instance].updateElement();
						}
					}
					window.preview_load = dialog.loading();
					$.ajax({
						type: "POST",
						dataType: "json",
						url: "${KMSS_Parameter_ContextPath}sys/news/sys_news_main/sysNewsMain.do?method=savePreview",
						data: $(formObj).serialize(),
						success: function (data) {
							if(window.preview_load != null) {
								window.preview_load.hide();
							}
							if(data.status) {
								// 打开新页面
								Com_OpenWindow("${KMSS_Parameter_ContextPath}sys/news/sys_news_main/sysNewsMain.do?method=view&show=preview&fdId="+data.fdId);
							} else {
								dialog.failure();
							}
						},
						error: function(data) {
							if(window.preview_load != null) {
								window.preview_load.hide();
							}
							dialog.failure();
						}
					});
				});
			} else {
				Com_Submit(document.sysNewsMainForm, method);
			}
		}

		//作者与部门联动
		function onFdAuthorIdChange(){
			setTimeout(function(){
				afterFdAuthorSelect();
			},1);
		}
		function afterFdAuthorSelect() {
			var author=document.getElementsByName("fdAuthorId")[0];
			var _authorId =author.value;
			if (_authorId == null || _authorId=="") {
				return false;
			}
			var data = new KMSSData();
		    data.AddBeanData("sysNewsAuthorService&authorId=" + _authorId);
		    data.PutToField("fdDepartmentId:fdDepartmentName", "fdDepartmentId:fdDepartmentName", "", false);
		}
		//切换外部作者和组织架构作者
		function SetAuthorField() {
			var checkBox = document.getElementById("isWriter");
			var checked = checkBox.checked;
			//box区域
			var checkBoxSpan = document.getElementById("checkBoxSpan");
			//外部人员
			var isWriterSpan = document.getElementById("isWriterSpan");	
			var notWriterSpan = document.getElementById("notWriterSpan");	
			var fdAuthId=document.getElementsByName("fdAuthorId")[0];
			var fdAuthName=document.getElementsByName("fdAuthorName")[0];	
			var fdWriter=document.getElementsByName("fdWriter")[0];
			var ol=document.getElementById('notWriterSpan').getElementsByClassName('mf_list')[0];
			var li=ol.getElementsByTagName('li')[0];
			if (true == checked) {
				//输入框样式
				checkBoxSpan.style.bottom="0px";
				
				fdAuthId.value="";
				fdAuthName.value="";
				if(ol&&li){
					ol.removeChild(li);
				}
				fdAuthName.setAttribute("validate","");
				fdWriter.setAttribute("validate","required");
				fdWriter.setAttribute("maxlength","200");
				isWriterSpan.style.display="";
				notWriterSpan.style.display="none";		

				var _validate_serial=fdAuthName.getAttribute("__validate_serial");
				if(document.getElementById('advice-'+_validate_serial)){		
					document.getElementById('advice-'+_validate_serial).style.display="none";	
				}
			} else {
			    //输入框样式
				checkBoxSpan.style.bottom="10px";
				
				fdWriter.value="";
				fdAuthName.setAttribute("validate","required");
				fdWriter.setAttribute("validate","");
				isWriterSpan.style.display="none";
				notWriterSpan.style.display="";	

				var __validate_serial=fdWriter.getAttribute("__validate_serial");
				if(document.getElementById('advice-'+__validate_serial)){
					document.getElementById('advice-'+__validate_serial).style.display="none";
				}
			}
			setDeptField(checked);
		}
		function setDeptField(checked) {
			var notWriterDeptSpan1 = document.getElementById("notWriterDeptSpan1");
			var notWriterDeptSpan2 = document.getElementById("notWriterDeptSpan2");
			var fdDepartmentId = document.getElementsByName("fdDepartmentId")[0];
			var fdDepartmentName = document.getElementsByName("fdDepartmentName")[0];
			var ol=document.getElementById('notWriterDeptSpan2').getElementsByClassName('mf_list')[0];
			var li=ol.getElementsByTagName('li')[0];
			if (true == checked) {
				notWriterDeptSpan1.style.display="none";
				notWriterDeptSpan2.style.display="none";
				fdDepartmentId.value = "";
				fdDepartmentName.value = "";
				fdDepartmentName.setAttribute("validate","");
				if(ol&&li){
					ol.removeChild(li);
				}
			} else {
				notWriterDeptSpan1.style.display="";
				notWriterDeptSpan2.style.display="";
				notWriterDeptSpan2.style.disabled="";
				fdDepartmentName.setAttribute("validate","required");
			}
		}
		Com_AddEventListener(window, "load", function() {
			//box区域
			var checkBoxSpan = document.getElementById("checkBoxSpan");
			var isWriterSpan = document.getElementById("isWriterSpan");	
			var notWriterSpan = document.getElementById("notWriterSpan");		
			var fdAuthId=document.getElementsByName("fdAuthorId")[0];
			var fdAuthName=document.getElementsByName("fdAuthorName")[0];
			var fdWriter=document.getElementsByName("fdWriter")[0];			
			if (true == document.getElementById("isWriter").checked) {
				checkBoxSpan.style.bottom="0px";
				
			    fdAuthName.setAttribute("validate","");
			    fdWriter.setAttribute("validate","required");
			    fdWriter.setAttribute("maxlength","200");
				isWriterSpan.style.display="";
				notWriterSpan.style.display="none";
			}else{	
				checkBoxSpan.style.bottom="10px";
		    	fdAuthName.setAttribute("validate","required");
		    	fdWriter.setAttribute("validate","");
				isWriterSpan.style.display="none";
				notWriterSpan.style.display="";
			}
			setDeptField(document.getElementById("isWriter").checked);
			var conType = document.getElementsByName("fdContentType")[0].value;

			<c:if test="${pageScope._isWpsWebOffice=='true'}">
			   checkEditType("${sysNewsMainForm.fdContentType}", null);
			</c:if>
			
			<c:if test="${sysNewsMainForm.fdContentType=='word'}">
			
				checkEditType("${sysNewsMainForm.fdContentType}", null);
			</c:if>
		});
		<c:if test="${sysNewsMainForm.fdContentType=='rtf'}">
			window.onload = function() {
				checkEditType("${sysNewsMainForm.fdContentType}", null);
			}
		</c:if>
		//pdf上传
		<c:if test="${sysNewsMainForm.fdContentType=='att'}">
		Com_AddEventListener(window,"load",function(){
			checkEditType("${sysNewsMainForm.fdContentType}", null);
		})
		</c:if>
		
		var hasSetHtmlToContent = false;
		var htmlContent = "";
		Com_Parameter.event["submit"].push(function() {
			var type=document.getElementsByName("fdContentType")[0];
			if ("word" == type.value) {
				//debugger;
				if ("${pageScope._isWpsWebOfficeEnable}" != "true" && "${pageScope._isJGEnabled}" == "true") {
					// 保存附件
					//if(!JG_SaveDocument()){return false;}
					var obj_JG = document.getElementById("JGWebOffice_editonline");
					if(obj_JG&&Attachment_ObjectInfo['editonline']&&jg_attachmentObject_editonline.hasLoad){
						jg_attachmentObject_editonline.ocxObj.Active(true);
				        jg_attachmentObject_editonline._submit();
				        return true;
			    	 }
					// 保存附件为html
					//if(!JG_WebSaveAsHtml()){return false;}
				} else {
					if ("${pageScope._isWpsWebOfficeEnable}" == "true" ){
						wps_editonline.submit();
					}
				}
			}
			return true;
		});
		
		
		function openLocal(){
			var obj_JG = document.getElementById("JGWebOffice_editonline");
			if(obj_JG&&Attachment_ObjectInfo['editonline']){
				jg_attachmentObject_editonline.ocxObj.WebOpenLocal();
			}
		}
		function fullSize(){
			var obj_JG = document.getElementById("JGWebOffice_editonline");
			if(obj_JG&&Attachment_ObjectInfo['editonline']){
				jg_attachmentObject_editonline.ocxObj.FullSize();
			}
		}
		
		function checkEditType(value, obj){
			var mode=document.getElementsByName("fdContentType")[0];
			var _rtfEdit = $('#rtfEdit');
			var _wordEdit = $('#wordEdit');
			var _attEdit = $('#attEdit');
			if (_rtfEdit.length==0 && _wordEdit.length == 0 && _attEdit.length == 0) {
				return ;
			}
			mode.value = "rtf";
			var wordFloat = $("#wordEditFloat");
			var buttonDiv = $("#buttonDiv");
			if("word" == value){
				mode.value = "word";
				_rtfEdit.css('display','none');
				_attEdit.css({'display':"none"});
				_wordEdit.css({'display':"block",'width':"98%",'height':"650px"});
				$('.attopenLocal').show();
				var xw = $("#wordEditWrapper").width();
				wordFloat.css({'width':xw+'px','height':'620px','filter':'alpha(opacity=100)','opacity':'1'});
				if ("${pageScope._isWpsWebOfficeEnable}" == "true" ){
					wps_editonline.load();
				}else if("${pageScope._isWpsCloudEnable}" == "true"){
					wps_cloud_editonline.load();
				}else if("${pageScope._isWpsCenterEnable}" == "true"){
					wps_center_editonline.load();
				}else if("${pageScope._isWpsWebOffice}" == "true" && "${pageScope.wpsoaassistEmbed}" == "false"){
					$('#uploader_editonline').show();
					_wordEdit.css({'display':"block",'width':"98%",'height':"150px"});
				}else if("${pageScope._isWpsWebOffice}" == "true" && "${pageScope.wpsoaassistEmbed}" == "true"){
					_wordEdit.css({'display':"block",'width':"98%",'height':"700px"});
					wps_linux_editonline.load();
				}else{
					buttonDiv.css({'width':'auto','height':'25px','filter':'alpha(opacity=100)','opacity':'1','display':"block"});
					var obj_JG = document.getElementById("JGWebOffice_editonline");
					if ("${pageScope._isJGEnabled}" == "true" && obj_JG) {
						if(!jg_attachmentObject_editonline.getOcxObj()){
							buttonDiv.css({'width':'0px','height':'0px','filter':'alpha(opacity=0)','opacity':'0','display':"none"});
						}
						setTimeout(function(){
							if(obj_JG&&Attachment_ObjectInfo['editonline']&&!jg_attachmentObject_editonline.hasLoad){
								jg_attachmentObject_editonline.load();
								jg_attachmentObject_editonline.show();
								jg_attachmentObject_editonline.ocxObj.Active(true);
							}
						},1000);
						seajs.use(['lui/topic'],function(topic){
							topic.subscribe("Sidebar",function(data){
								var xw = $("#wordEditWrapper").width();
								wordFloat.css({'width':xw+'px','height':'620px','filter':'alpha(opacity=100)','opacity':'1'});
								buttonDiv.css({'width':'auto','height':'25px','filter':'alpha(opacity=100)','opacity':'1','display':"block"});
							});
						});
						$("#JGWebOffice_editonline").height("600px");
						chromeHideJG_2015(1);
					} 
				}
			} else if("att" == value){
				mode.value ="att";
				 if("${pageScope._isWpsWebOffice}" == "true")
			     {
					 $('#uploader_editonline').hide();
				 }
				 _rtfEdit.css('display','none');
				 _attEdit.css({'display':"block"});
				buttonDiv.css({'width':'0px','height':'0px','filter':'alpha(opacity=0)','opacity':'0','display':"none"});
				/* if ("${pageScope._isWpsWebOfficeEnable}" == "true" || "${pageScope._isWpsCloudEnable}" == "true") {
					_wordEdit.css({'display':'none','width':"0px",'height':"0px"});
				} else {
					_wordEdit.css({'width':"0px",'height':"0px"});
				} */
				if ("${pageScope._isWpsCenterEnable}" == "true") {
					_wordEdit.css('display', 'none');
				} else {
					_wordEdit.css({'display':"block",'width':"0px",'height':"0px"});
				}
				$('.attopenLocal').hide();
				wordFloat.css({'width':'0px','height':'0px','filter':'alpha(opacity=0)','opacity':'0'});
				$("#JGWebOffice_editonline").height("0px");
				chromeHideJG_2015(0);
			}else{
				 if("${pageScope._isWpsWebOffice}" == "true")
			     {
					 $('#uploader_editonline').hide();
					}
				 _attEdit.css({'display':"none"});
				_rtfEdit.css('display','block');
				buttonDiv.css({'width':'0px','height':'0px','filter':'alpha(opacity=0)','opacity':'0','display':"none"});
				/* if ("${pageScope._isWpsWebOfficeEnable}" == "true" || "${pageScope._isWpsCloudEnable}" == "true") {
					_wordEdit.css({'display':'none','width':"0px",'height':"0px"});
				} else {
					_wordEdit.css({'width':"0px",'height':"0px"});
				} */
				if ("${pageScope._isWpsCenterEnable}" == "true") {
					_wordEdit.css('display', 'none');
				} else {
					_wordEdit.css({'display':"block",'width':"0px",'height':"0px"});
				}
				$('.attopenLocal').hide();
				wordFloat.css({'width':'0px','height':'0px','filter':'alpha(opacity=0)','opacity':'0'});
				$("#JGWebOffice_editonline").height("0px");
				chromeHideJG_2015(0);
			}
		}
		seajs.use(['lui/dialog','lui/jquery'],function(dialog,$){
			 <c:if test="${sysNewsMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.yqqSign =='true' && yqqFlag=='true' && sysNewsMainForm.fdSignEnable=='true'}">
			 Com_Parameter.event["submit"].push(function(){
					//操作类型为通过类型 ，才判断是否已经签署
				if(lbpm.globals.getCurrentOperation().operation && lbpm.globals.getCurrentOperation().operation['isPassType'] == true){
					 var flag = true;
					 var url = Com_Parameter.ContextPath+"sys/news/sys_news_out_sign/sysNewsOutSign.do?method=queryFinish&signId=${param.fdId}";
					 $.ajax({
							url : url,
							type : 'post',
							data : {},
							dataType : 'text',
							async : false,     
							error : function(){
								dialog.alert('请求出错');
							} ,   
							success:function(data){
								if(data == "true"){
									flag = true;
								}else{
									dialog.alert("当前签署任务未完成，无法提交！！");
									flag = false;
								}
							}
						});
					 return flag;
				}else{
					return true;
				}
			 });
	 	</c:if>
		});
	</script>
	<c:set var="sysDocBaseInfoForm" value="${sysNewsMainForm}" scope="request" />
	<c:if test="${param.approveModel ne 'right'}">
		<form name="sysNewsMainForm" method="post" action="${KMSS_Parameter_ContextPath}sys/news/sys_news_main/sysNewsMain.do">
	</c:if>
	<html:hidden property="fdId" />
	<html:hidden property="fdModelId" />
	<html:hidden property="fdModelName" />
	<html:hidden property="docStatus" />
   	<c:choose>
		<c:when test="${param.approveModel eq 'right'}">
			<c:import url="/sys/news/sys_news_ui/sysNewsMain_editContent.jsp" charEncoding="UTF-8">
 		 		<c:param name="contentType" value="xform" />
  			</c:import>
			
			<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-supportExpand="true" var-expand="${empty sysNewsMainForm.docStatus || sysNewsMainForm.docStatus<30}" var-average='false' var-useMaxWidth='true'>
	  			<c:import url="/sys/news/sys_news_ui/sysNewsMain_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="right"></c:param>
	  			</c:import>
	  			<!-- 流程 -->
				<c:choose>
					<c:when test="${empty sysNewsMainForm.docStatus || sysNewsMainForm.docStatus == 10}">
						<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="sysNewsMainForm"/>
							<c:param name="fdKey" value="newsMainDoc"/>
							<c:param name="approveType" value="right" />
							<c:param name="order" value="2" />
						</c:import>
					</c:when>
					<c:otherwise>
						<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="sysNewsMainForm"/>
							<c:param name="fdKey" value="newsMainDoc"/>
							<c:param name="approveType" value="right" />
							<c:param name="order" value="${sysNewsMainForm.docStatus>=30 ? 10 : 9}" />
						</c:import>
					</c:otherwise>
				</c:choose>
			</ui:tabpanel>
		</c:when>
		<c:otherwise>
			<ui:tabpage expand="false" var-navwidth="90%">
				<c:import url="/sys/news/sys_news_ui/sysNewsMain_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="xform"></c:param>
	  			</c:import>
	  			<c:import url="/sys/news/sys_news_ui/sysNewsMain_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="right"></c:param>
	  			</c:import>
	  			<!-- 流程 -->
	  			<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="sysNewsMainForm"/>
					<c:param name="fdKey" value="newsMainDoc"/>
					<c:param name="isExpand" value="true"/>
				</c:import>
			</ui:tabpage>
			</form>
		</c:otherwise>
	</c:choose>
	<script language="JavaScript">
		$KMSSValidation(document.forms['sysNewsMainForm']);
	</script>
	
</template:replace>

<c:choose>
	<c:when test="${param.approveModel eq 'right'}">
		<template:replace name="barRight">
			<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
				<c:if test="${sysNewsMainForm.docStatus>='30' || sysNewsMainForm.docStatus=='00'}">
					<!-- 基本信息-->
					<c:import url="/sys/news/sys_news_ui/sysNewsMain_viewBaseInfo.jsp" charEncoding="UTF-8"></c:import>
				</c:if>
		 		<!-- 流程 -->
	  			<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="sysNewsMainForm"/>
					<c:param name="fdKey" value="newsMainDoc"/>
					<c:param name="approveType" value="right" />
					<c:param name="approvePosition" value="right" />
				</c:import>		
				<%--关联机制(与原有机制有差异)--%>
				<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="sysNewsMainForm" />
					<c:param name="approveType" value="right" />
					<c:param name="needTitle" value="true" />
				</c:import>
			</ui:tabpanel>
		</template:replace>
	</c:when>
	<c:otherwise>
		<template:replace name="nav">
	    	<%--关联机制(与原有机制有差异)--%>
			<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="sysNewsMainForm" />
			</c:import>
     	</template:replace>
	</c:otherwise>
</c:choose>	
