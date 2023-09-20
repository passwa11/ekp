<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.landray.kmss.util.ResourceUtil,java.io.File"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.news.model.SysNewsConfig"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%
	 pageContext.setAttribute("_isJGEnabled", new Boolean(
			com.landray.kmss.sys.attachment.util.JgWebOffice
					.isJGEnabled()));
   	 SysNewsConfig sysNewsConfig = new SysNewsConfig();
     pageContext.setAttribute("ImageW",sysNewsConfig.getfdImageW());
     pageContext.setAttribute("ImageH",sysNewsConfig.getfdImageH());
     
 	 //判断配置的金格控件类型
 	 String jgPluginType = com.landray.kmss.sys.attachment.util.JgWebOffice.getJGPluginType();
 	 pageContext.setAttribute("_jgPluginType", jgPluginType);

%>
<c:import url="/sys/recycle/import/redirect.jsp">
	<c:param name="formBeanName" value="sysNewsMainForm"></c:param>
</c:import>

<template:include ref="default.edit" sidebar="auto">
<%-- 标题 --%>
	<template:replace name="title">
	<c:choose>
		<c:when test="${sysNewsMainForm.method_GET=='add' }">
			<c:out value="${ lfn:message('sys-news:sysNews.create.title') } - ${ lfn:message('sys-news:news.moduleName') }"></c:out>	
		</c:when>
		<c:otherwise>
			<c:out value="${sysNewsMainForm.docSubject} - ${ lfn:message('sys-news:news.moduleName') }"></c:out>
		</c:otherwise>
	</c:choose>
	</template:replace>
	<%-- 按钮栏 --%>
	<template:replace name="toolbar">
		<c:if test="${kmReviewMainForm.docDeleteFlag ==1}">
			<div id="toolbar" style="display:none"></div>
		</c:if>
		<c:if test="${kmReviewMainForm.docDeleteFlag !=1}">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5">
			<c:choose>
				<c:when test="${ sysNewsMainForm.method_GET == 'add' }">
					<ui:button text="${lfn:message('sys-news:news.button.store') }" order="2" onclick="submitForm('save','10');">
					</ui:button>
					<ui:button text="${lfn:message('button.submit') }" order="2" onclick="submitForm('save','20');">
					</ui:button>
				</c:when>
				<c:when test="${ sysNewsMainForm.method_GET == 'edit' && (sysNewsMainForm.docStatus=='10' || sysNewsMainForm.docStatus=='11')}">
					<ui:button text="${lfn:message('sys-news:news.button.store') }" order="2" onclick="submitForm('update','10');">
					</ui:button>
					<ui:button text="${lfn:message('button.submit') }" order="2" onclick="submitForm('update','20');">
					</ui:button>	
				</c:when>
				<c:when test="${sysNewsMainForm.method_GET=='edit'&& sysNewsMainForm.docStatus!='10'&& sysNewsMainForm.docStatus!='11'}">
					<ui:button text="${lfn:message('button.submit') }" order="2" onclick="submitForm('update');">
					</ui:button>	
				</c:when>			
			</c:choose>		
			<ui:button text="${lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
		</c:if>
	</template:replace>
	<%-- 路径 --%>
	<template:replace name="path">
			<ui:combin ref="menu.path.simplecategory">
				<ui:varParams 
					moduleTitle="${ lfn:message('sys-news:news.moduleName') }" 
					modelName="com.landray.kmss.sys.news.model.SysNewsTemplate" 
					autoFetch="false"
					categoryId="${sysNewsMainForm.fdTemplateId}" />
			</ui:combin>
	</template:replace>	
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
					    Com_Submit(document.sysNewsMainForm, method);		
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
					checkEditType("${sysNewsMainForm.fdContentType}", null);
				});
				var hasSetHtmlToContent = false;
				var htmlContent = "";
				Com_Parameter.event["submit"].push(function() {
					var type=document.getElementsByName("fdContentType")[0];
					if ("word" == type.value) {
						//debugger;
						if ("${pageScope._isJGEnabled}" == "true") {
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
							//蓝凌控件需要做的事
							return getHTMLtoContent("editonline", "fdAttachmentPic");
						}
					}
					return true;
				});
				Com_Parameter.event["confirm"].push(function() {
					if (hasSetHtmlToContent) {
						document.sysNewsMainForm.fdHtmlContent.value = htmlContent;
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
					mode.value = "rtf";
					var _rtfEdit = $('#rtfEdit');
					var _wordEdit = $('#wordEdit');
					if (_rtfEdit.length==0 || _wordEdit.length == 0) {
						return ;
					}
					var wordFloat = $("#wordEditFloat");
					if("word" == value){
						mode.value = "word";
						_rtfEdit.css('display','none');
						_wordEdit.css({'display':"block",'width':"98%",'height':"650px"});
						var xw = $("#wordEditWrapper").width();
						wordFloat.css({'width':xw+'px','height':'620px','filter':'alpha(opacity=100)','opacity':'1'});
						if ("${pageScope._isJGEnabled}" == "true") {
							var obj_JG = document.getElementById("JGWebOffice_editonline");
							setTimeout(function(){
								if(obj_JG&&Attachment_ObjectInfo['editonline']&&!jg_attachmentObject_editonline.hasLoad){
									jg_attachmentObject_editonline.load();
									jg_attachmentObject_editonline.show();
									jg_attachmentObject_editonline.ocxObj.Active(true);
								}
							},1000);
						} 
						seajs.use(['lui/topic'],function(topic){
							topic.subscribe("Sidebar",function(data){
								var xw = $("#wordEditWrapper").width();
								wordFloat.css({'width':xw+'px','height':'620px','filter':'alpha(opacity=100)','opacity':'1'});
							});
						});
						$("#JGWebOffice_editonline").height("600px");
					} else {
						_rtfEdit.css('display','block');
						_wordEdit.css({'width':"0px",'height':"0px"});
						wordFloat.css({'width':'0px','height':'0px','filter':'alpha(opacity=0)','opacity':'0'});
						$("#JGWebOffice_editonline").height("0px");
					}
				}
				function getTempFilePath(fdKey){
					Attachment_ObjectInfo[fdKey].getOcxObj();
					var tempFold = Attachment_ObjectInfo[fdKey].ocxObj.GetUniqueFileName();
					tempFold = tempFold.substring(0, tempFold.lastIndexOf('\\'));
					return tempFold;
				}
				
				function getHTMLtoContent(fdKey, picfdKey) {
					if (!fdKey || !picfdKey) {
						return false;
					}
					if (hasSetHtmlToContent) return true;
					var hasImg = false;
					var path = getTempFilePath(fdKey);
					var tbody = document.createElement('tempdom');
			  		tbody.innerHTML = Attachment_ObjectInfo[fdKey].ocxObj.getHTMLEx();
			  		for(var i = 0; i < tbody.all.length; i++) {
						if(tbody.all[i].src != null) {
							var Vshape = tbody.all[i].parentElement;
							if(Vshape.tagName == "shape" || tbody.all[i].tagName == "img") {
								var ImgUrl = tbody.all[i].src;
								ImgUrl = "\\" + ImgUrl;
								ImgUrl = ImgUrl.replace("/","\\");
								Attachment_ObjectInfo[fdKey].getOcxObj();
								Attachment_ObjectInfo[fdKey].addDoc(path+ImgUrl,null,false,null,null);
								hasImg = true;
							}
						}
					}
					Attachment_ObjectInfo[fdKey].onFinishPostCustom = function(fileList) {
						var fdIds = new Array();
						for(var i = 0; i < fileList.length; i ++) {
							fdIds.push(fileList[i].fdId+":"+fileList[i].fileName);
						}
						if (fdIds.length > 0) {
							updatePicHTML(tbody, fdIds);
						}
					};
					if (!hasImg && typeof tbody == "object") {
						htmlContent = tbody.innerHTML;
					}
					hasSetHtmlToContent = true;
					return true;
				}
			
				function formatHTML(html){
					var up = "<%=request.getRequestURL()%>";
			        if(up.indexOf("https")==0){
			           //https下
					  up = up.substring(0,up.indexOf("/",8));
					}else{
						//http下
						up = up.substring(0,up.indexOf("/",7));
						}
					up = up.replace("//","\\/\\/");
					var rep = "/"+up+"/gi";
					html = html.replace(eval(rep),"");
					return html;
				}
				
				function updatePicHTML(tbody, fdIds){
			  		for(var i = 0; i < tbody.all.length; i++) {	
						if(tbody.all[i].src != null) {
							var Vshape = tbody.all[i].parentElement;
							if(Vshape.tagName == "shape" || tbody.all[i].tagName == "img") {
								var objstr = Vshape.innerHTML;
								var fdId='';
								var result = getImgFdId(fdIds,objstr);
								if(result != "false"){
									fdId = result;
								}
								var pp = 'sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId='+fdId;
								if(tbody.all[i].tagName == "img") {
									tbody.all[i].src = Com_Parameter.ContextPath + pp;
									tbody.all[i].kmss_ref = pp;
								}else{
									Vshape.innerHTML = "<img  src=\"" + Com_Parameter.ContextPath + pp + "\" style=\"" + Vshape.style.cssText + "\" kmss_ref='" + pp + "'>";
								}
							}
						}
					}
			  		htmlContent = formatHTML(tbody.innerHTML);
				}
			
				//获取图片的id
				function getImgFdId(fdIds,fileName){
					for(var i = 0; i < fdIds.length; i ++) {
						var fdName = fdIds[i].split(":")[1];
						var fdid = fdIds[1].split(":")[0];
						if(fileName.indexOf(fdName,0)>0){
							//return fdName.split(":")[0];
							return fdid;
						}
					}
					return false;
				}
			
				function len(s) { var l = 0; var a = s.split(""); for (var i=0;i<a.length;i++) { if (a[i].charCodeAt(0)<299) { l++; } else { l+=2; } } return l; }
			</script>
		<c:set var="sysDocBaseInfoForm" value="${sysNewsMainForm}" scope="request" />
		<html:form action="/sys/news/sys_news_main/sysNewsMain.do">
		<html:hidden property="fdId" />
		<html:hidden property="fdModelId" />
		<html:hidden property="fdModelName" />
		<html:hidden property="docStatus" />
	   <div class="lui_form_content_frame" style="padding-top:20px">		
		    <%-- 新闻信息页签--%>
				<table class="tb_simple" width=100%>				
				<!-- 新闻主题 -->
				<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-news"
							key="sysNewsMain.docSubject" /></td>
						<td colspan=3>
						<xform:text property="docSubject" style="width:97%" validators="senWordsValidator(sysNewsMainForm)"/>
						</td>
				</tr>
			
			<tr>
					<%-- 作者 --%>
					<td class="td_normal_title" width=15%><bean:message bundle="sys-news"  key="sysNewsMain.fdAuthorId" />
					</td>
					<td width=35% >	
					  <span id="isWriterSpan" >
				         	  	<xform:text property="fdWriter" style="width:70%;" isLoadDataDict="false" subject="${lfn:message('sys-news:sysNewsMain.fdAuthorId')}"  required="true"/>
					  </span>
					  
					  <span id="notWriterSpan">
					 		   <xform:address onValueChange="onFdAuthorIdChange" isLoadDataDict="false" required="true"  style="width:70%" subject="${lfn:message('sys-news:sysNewsMain.fdAuthorId')}" propertyId="fdAuthorId" propertyName="fdAuthorName" orgType='ORG_TYPE_PERSON' className="input"></xform:address>
					  </span>
					  <span id="checkBoxSpan"  style="position:relative;">
						       <html:checkbox property="fdIsWriter" onclick="SetAuthorField();" styleId="isWriter" />
							   <bean:message bundle="sys-news" key="sysNewsMain.fdIsWriter" />
					 </span>
					</td>
						<!-- 所属部门 -->
					<td class="td_normal_title" width=10%><bean:message
						bundle="sys-news" key="sysNewsMain.fdDepartmentId" /></td>				
					<td width=35%>
					    <xform:address isLoadDataDict="false" required="true" style="width:94%" propertyId="fdDepartmentId" subject="${lfn:message('sys-news:sysNewsMain.fdDepartmentId')}" propertyName="fdDepartmentName" orgType='ORG_TYPE_ORG|ORG_TYPE_DEPT'></xform:address>
					</td>
				</tr>	
				<tr>
					<%-- 摘要 --%>
					<td class="td_normal_title" width=15%><bean:message
						bundle="sys-news" key="sysNewsMain.fdDescription" /></td>
					<td width="85%" colspan="3">
						<xform:textarea property="fdDescription"  style="width:97%;height:90px" validators="senWordsValidator(sysNewsMainForm)"/>
					</td>
				</tr>
				<%-- 编辑方式 --%>
				<html:hidden property="fdContentType" />
				<html:hidden property="fdHtmlContent" />
				<c:if test="${sysNewsMainForm.method_GET=='add'}">
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-news" key="sysNewsMain.fdContentType" />
					</td>
					<td colspan="3">
						<xform:radio property="fdEditType" showStatus="edit" value="${sysNewsMainForm.fdContentType}" onValueChange="checkEditType">
							<xform:enumsDataSource enumsType="sysNewsMain_fdContentType" />
						</xform:radio>
					</td>
				</tr>
				</c:if>
				
				<!-- 新闻内容 -->
				<tr>
					<td class="td_normal_title"  width="15%"></td>
					<td colspan="3">
						<c:if test="${sysNewsMainForm.fdIsLink&& not empty sysNewsMainForm.fdLinkUrl}">
							<bean:message bundle="sys-news" key="SysNewsMain.linkNews" />
							<a href='<c:url value="${sysNewsMainForm.fdLinkUrl}"/>'/>
								${sysNewsMainForm.docContent}
							</a>
						</c:if> 
						<c:if test="${empty sysNewsMainForm.fdIsLink || empty sysNewsMainForm.fdLinkUrl}">
							<div id="rtfEdit" 
							<c:if test="${sysNewsMainForm.fdContentType!='rtf'}">style="display:none"</c:if>>
							<xform:rtf property="docContent" width="97%" toolbarSet="Default" validators="senWordsValidator(sysNewsMainForm)"></xform:rtf>
							</div>
							<div id="missiveButtonDiv" style="text-align:right; display: none;">
							</div>
							<div id="wordEdit" style="width:0px;height:0px">
							<c:choose>
								<c:when test="${pageScope._isJGEnabled == 'true'}">
									<div id="wordEditWrapper"></div>
									<div id="wordEditFloat" style="position: absolute; width: 0px; height: 0px; filter: alpha(opacity = 0); opacity: 0;">
										<div id="buttonDiv" style="text-align: right; padding-bottom: 5px">
										  <a href="javascript:void(0);" class="attopenLocal" onclick="openLocal();">
										     <bean:message bundle="sys-news" key="SysNewsMain.tools.openLocal"/>
										  </a>&nbsp;
										  <c:if test="${pageScope._jgPluginType ne '2003'}">
										  <a href="javascript:void(0);" class="attfullSize" onclick="fullSize();">
										     <bean:message bundle="sys-news" key="SysNewsMain.tools.fullsize"/>
										  </a>
										  </c:if>
										</div>										
										<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_edit.jsp" charEncoding="UTF-8">
											<c:param name="fdKey" value="editonline" />
											<c:param name="fdAttType" value="office" />
											<c:param name="load" value="false" />
											<c:param name="bindSubmit" value="false"/>													
											<c:param name="fdModelId" value="${sysNewsMainForm.fdId}" />
											<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
											<c:param name="formBeanName" value="sysNewsMainForm" />
											<c:param name="fdTemplateModelId" value="${param.fdTemplateId}" />
											<c:param name="fdTemplateModelName" value="com.landray.kmss.sys.news.model.SysNewsTemplate" />
											<c:param name="fdTemplateKey" value="editonline" />
											<c:param name="templateBeanName" value="sysNewsTemplateForm" />
											<c:param name="buttonDiv" value="missiveButtonDiv" />
											<c:param  name="attHeight" value="600"/>
											<c:param name="isToImg" value="false"/>
										</c:import>
									</div>
								</c:when>
								<c:otherwise>
									<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
										<c:param name="fdModelId" value="${sysNewsMainForm.fdId}" />
										<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
										<c:param name="fdKey" value="editonline" />
										<c:param name="formBeanName" value="sysNewsMainForm" />
										<c:param name="fdAttType" value="office" />
										<c:param name="fdTemplateModelId" value="${param.fdTemplateId}" />
										<c:param name="fdTemplateName" value="${param.fdTemplateName}" />
										<c:param name="fdTemplateModelName" value="com.landray.kmss.sys.news.model.SysNewsTemplate" />
										<c:param name="fdTemplateKey" value="editonline" />
										<c:param name="templateBeanName" value="sysNewsTemplateForm" />
									</c:import>
									<div id="pic" style="display: none">
										<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
											<c:param name="fdKey" value="fdAttachmentPic" />
											<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
										</c:import>
									</div>
								</c:otherwise>
							</c:choose>
							</div>
						</c:if>
					</td>
				</tr>
				<!-- 附件 -->
				<tr KMSS_RowType="documentNews">
					<td class="td_normal_title" width=15% valign="top"><bean:message
						bundle="sys-news" key="sysNewsMain.attachment" /></td>
					<td colspan=3><c:import
						url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp"
						charEncoding="UTF-8">
						<c:param name="fdAttType" value="byte" />
						<c:param name="uploadAfterSelect" value="true" />
						<c:param name="fdMulti" value="true" />
						<c:param name="fdShowMsg" value="true" />
						<c:param name="fdImgHtmlProperty" />
						<c:param name="fdKey" value="fdAttachment" />
						<c:param name="fdModelId" value="${param.fdId }" />
						<c:param name="fdModelName"
							value="com.landray.kmss.sys.news.model.SysNewsMain" />
					</c:import></td>
			    </tr>
				
					<%-- 标题图片--%>
				<tr>
					<td class="td_normal_title" width="15%" valign="top"><bean:message
						bundle="sys-news" key="sysNewsMain.fdMainPicture" /></td>
					<td colspan="3">		
					 	<c:import
							url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp"
							charEncoding="UTF-8">
							<c:param name="fdKey" value="Attachment" />
							<c:param name="fdMulti" value="false" />
							<c:param name="fdAttType" value="pic" />
							<c:param name="fdImgHtmlProperty" value="width=120" />
							<c:param name="fdModelId" value="${param.fdId }" />
							<c:param name="fdModelName"
								value="com.landray.kmss.sys.news.model.SysNewsMain" />
							<%-- 图片设定大小 --%>
							<c:param name="picWidth" value="${ImageW}" />
							<c:param name="picHeight" value="${ImageH}" />
							<c:param name="proportion" value="false" />
							<c:param name="fdLayoutType" value="pic"/>
							<c:param name="fdPicContentWidth" value="${ImageW}"/>
							<c:param name="fdPicContentHeight" value="${ImageH}"/>
							<c:param name="fdViewType" value="pic_single"/>
						</c:import>
						<br>
					    <bean:message bundle="sys-news" key="sysNewsMain.config.desc" />
						 <font color="red">${ImageW}(<bean:message bundle="sys-news" key="sysNewsMain.config.width"/>)*${ImageH}(<bean:message bundle="sys-news" key="sysNewsMain.config.height"/>)</font>										  				 
					</td>
				</tr>
				<tr>
				  <%--更多设置按钮--%>
					<td colspan="4" style="text-align: center;">
						<a href="javascript:void(0);" onclick="showMoreSet();" id="showMoreSet" class="task_slideDown">
						<bean:message bundle="sys-news" key="sysNewsMain.more.set.slideDown" /></a>
					</td>		
				</tr>  
				<tr id="show_more_set_id" style="display:none">
				 <td colspan="4" width="100%">
						<table class="tb_simple" width="100%">				
				  			<!-- 新闻重要度 -->
								<tr>	
									<td class="td_normal_title" width=15%><bean:message
										bundle="sys-news" key="sysNewsTemplate.fdImportance" /></td>
									<td colspan="3"><sunbor:enums property="fdImportance"
										enumsType="sysNewsMain_fdImportance" elementType="radio" />
									</td>
								</tr>				
								<tr>
								 	<!-- 发布时间 -->
									<td class="td_normal_title" width=15%><bean:message
										bundle="sys-news" key="sysNewsMain.docPublishTime" /></td>
									<td width=35%>
										<xform:datetime property="docPublishTime" style="width:100%" dateTimeType="datetime"></xform:datetime>
									</td>
									<!-- 新闻来源 -->
									<td class="td_normal_title" width="15%">
									<bean:message
										bundle="sys-news" key="sysNewsMain.fdNewsSource" />
									</td>
									<td width=35%>
									    <html:hidden property="fdTemplateId" />
										<html:hidden property="fdTemplateName" />
										<xform:text property="fdNewsSource" style="width:94%;"/>
		            		      </td>
						     </tr>
							<!-- 标签机制 -->
							<c:import url="/sys/tag/import/sysTagMain_edit.jsp"
								charEncoding="UTF-8">
								<c:param name="formName" value="sysNewsMainForm" />
								<c:param name="fdKey" value="newsMainDoc" /> 
								<c:param name="modelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
								<c:param name="fdQueryCondition" value="fdTemplateId;fdDepartmentId" /> 
							</c:import>
							<%-- 所属场所 --%>
			                <c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp" charEncoding="UTF-8">
			                    <c:param name="id" value="${sysNewsMainForm.authAreaId}"/>
			                </c:import> 
					     </table>
			       </td>
				</tr>
			</table>
		 </div>	
        <ui:tabpage expand="false" > 
		<%--权限机制 --%>
		<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="sysNewsMainForm" />
			<c:param name="moduleModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
		</c:import>	
		<!-- 以下代码为嵌入流程模板标签的代码 -->
		<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="sysNewsMainForm"/>
			<c:param name="fdKey" value="newsMainDoc"/>
		</c:import>
		
		<!-- 以上代码为嵌入流程模板标签的代码 -->			
		</ui:tabpage>
		</html:form>
			<script language="JavaScript">
			$KMSSValidation(document.forms['sysNewsMainForm']);
		</script>
	</template:replace>
    <template:replace name="nav">
    	<%--关联机制(与原有机制有差异)--%>
		<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="sysNewsMainForm" />
		</c:import>
     </template:replace>
</template:include>