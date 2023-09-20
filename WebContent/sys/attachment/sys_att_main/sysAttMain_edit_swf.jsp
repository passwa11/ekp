<%@page import="com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationDirectService"%>
<%@page import="com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil"%>
<%@page import="com.landray.kmss.sys.attachment.util.SysAttUtil"%>
<%@ page import="com.landray.kmss.sys.authentication.user.KMSSUser" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="java.util.List,com.landray.kmss.util.StringUtil,com.landray.kmss.util.ResourceUtil,com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@page import="com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm,com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService,com.landray.kmss.sys.attachment.plugin.HistoryVersionPlugin,com.landray.kmss.sys.attachment.model.SysAttMainHistoryConfig"%>

<%@page import="com.landray.kmss.sys.ui.plugin.SysUiPluginUtil"%>
<%@ page import="com.landray.kmss.sys.attachment.util.SysAttConfigUtil" %>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()+"/"+SysUiPluginUtil.getThemesFileByName(request,"module")%>"></link>

<c:set var="fdKey" value="${param.fdKey}" />
<c:set var="_htmlKey" value="${lfn:escapeHtml(fdKey)}" />
<c:set var="_jsKey" value="${lfn:escapeJs(fdKey)}" />
<%-- 附件${fdKey}开始 --%>
<script>

if (typeof(seajs) != 'undefined') {
	seajs.use( [ 'lui/jquery', 'lui/dialog', 'lui/topic' ], function($, dialog, topic) {
		var changeName;
		
		window.reName = function(att,i,self) {
			var fdFileName = att.fileList[i].fileName;
			if(fdFileName !=null &&fdFileName !=""){
				fdFileName = fdFileName.substring(0,fdFileName.lastIndexOf("."));
				// #150149
				fdFileName = encodeURIComponent(fdFileName);
			}
			
			var iframeUrl = "/sys/attachment/sys_att_main/sysAttMain.do?method=editName&fdFileName="+fdFileName;
			var title = '<bean:message bundle="sys-attachment" key="sysAttMain.button.rename"/>' ;
			dialog.iframe(iframeUrl, title, function(data) {
				if (null != data && undefined != data) {
					changeName = data.fdFileName;
					var fileName = att.fileList[i].fileName;
					var fileExt = fileName.substring(fileName.lastIndexOf("."));
					att.fileList[i].fileName = changeName+fileExt;
					if(att.fileList[i].isNew && !att.fileList[i].fdIsCreateAtt){
						var _filename = $("#" + att.fileList[i].fdId + " .upload_list_filename_title");
						_filename.text(changeName);
						_filename.parent().attr("title", changeName + fileExt);
						// 发布一个事件
						$(document).trigger("alterName", att.fileList[i]);
						// trigger()在IE下存在兼容问题，业务模块可能已经使用了trigger()触发事件，故另发布一个事件，业务模块注意区别，避免重复触发
						topic.publish("alterName-topic", att.fileList[i]);
					}else{
						$.ajax({
							url: self.alterUrl,
							type: 'POST',
							data:{id:att.fileList[i].fdId,name:att.fileList[i].fileName},
							dataType: 'json',
							async:false, 
							success: function(data){
								// 文件名称修改成功，不需要刷新控件，只需要修改元素名称
								$("[id='"+att.fileList[i].fdId+"']").each(function(i, n) {
									var _filename = $(n).find(".upload_list_filename_title");
									_filename.text(changeName);
									_filename.parent().attr("title", changeName + fileExt);
								});
								// 发布一个事件
								$(document).trigger("alterName", att.fileList[i]);
								// trigger()在IE下存在兼容问题，业务模块可能已经使用了trigger()触发事件，故另发布一个事件，业务模块注意区别，避免重复触发
								topic.publish("alterName-topic", att.fileList[i]);
							}
					   });
					}
				}
			}, {
				width : 450,
				height : 200
			});
		};
		
		window.viewHistory = function(docId,isReadDownLoad,realCanPrint,realCanDownload,self) {
			var iframeUrl = "/sys/attachment/sys_att_main/sysAttMainHistory_list.jsp?fdOriginId="+docId+"&isReadDownLoad="+isReadDownLoad+"&realCanPrint="+realCanPrint+"&realCanDownload="+realCanDownload;
			if(self.fdForceUseJG){
				iframeUrl += "&fdForceUseJG=true"
			}
			var title = '<bean:message bundle="sys-attachment" key="sysAttMain.view.history"/>' ;
			dialog.iframe(iframeUrl, title, null, {
				width : 1000,
				height : 600
			});
		};
	
	});	
	
	/****************************************
	 * 需改附件名称
	 ***************************************/
	window.alterName = function(docId,self) {   
		for (var i = 0; i < self.fileList.length; i++){ 
			if(self.fileList[i].fdId == docId){
				reName(self,i,self);
				break;
			}
		} 
		
	};
}
	if(typeof Com_Parameter.__sysAttMainlocale__ == "undefined")
		Com_Parameter.__sysAttMainlocale__= "<%= UserUtil.getKMSSUser(request).getLocale().toString().toLowerCase().replace('_', '-') %>";
</script>
<script>Com_IncludeFile("swf_attachment.js?mode=edit","${KMSS_Parameter_ContextPath}sys/attachment/js/","js",true);</script>
<%
	Object formBean = null;
	String fromBeanVar = "com.landray.kmss.web.taglib.FormBean";
	String formBeanName = request.getParameter("formBeanName");
	//记录旧的formBean,主要是避免当前界面中对于formBean的修改，影响后续的使用
	Object originFormBean = pageContext.getAttribute(fromBeanVar);
	if(StringUtil.isNotNull(formBeanName)){
		formBean = pageContext.findAttribute(formBeanName);
	}else{
		formBean = pageContext.findAttribute(fromBeanVar);
	}
	//设置使用的formBean对象
	pageContext.setAttribute(fromBeanVar, formBean);
	pageContext.setAttribute("_formBean", formBean);
%>
<c:set var="attForms" value="${_formBean.attachmentForms[fdKey]}" /> 
<div id="_List_${_htmlKey}_Attachment_Table">
	<html:hidden property="attachmentForms.${_htmlKey}.fdModelId" />
	<html:hidden property="attachmentForms.${_htmlKey}.extParam"    value="${HtmlParam.extParam }" />
	<html:hidden property="attachmentForms.${_htmlKey}.fdModelName" value="${HtmlParam.fdModelName }" />
	<html:hidden property="attachmentForms.${_htmlKey}.fdKey"		 value="${_htmlKey }" />
	<html:hidden property="attachmentForms.${_htmlKey}.fdAttType"	 value="${HtmlParam.fdAttType }" />
	<html:hidden property="attachmentForms.${_htmlKey}.fdMulti"	 value="${HtmlParam.fdMulti }" />
	<html:hidden property="attachmentForms.${_htmlKey}.deletedAttachmentIds" />
	<html:hidden property="attachmentForms.${_htmlKey}.attachmentIds" />
</div> 
<%-- 附件列表展现区 --%>
<div id="attachmentObject_${_htmlKey}_content_div" class="lui_upload_img_box">
	<img src="${KMSS_Parameter_ContextPath}sys/attachment/swf/loading.gif" border="0" style="width: 60px;height: 60px;"/>
</div>

<%
	ISysFileLocationDirectService directService =
			SysFileLocationUtil.getDirectService();
	request.setAttribute("methodName", directService.getMethodName());
	request.setAttribute("uploadUrl", directService.getUploadUrl(request.getHeader("User-Agent")));
	request.setAttribute("isSupportDirect", directService.isSupportDirect(request.getHeader("User-Agent")));
	request.setAttribute("fileVal", directService.getFileVal());
	request.setAttribute("smallMaxSize", ResourceUtil.getKmssConfigString("sys.att.smallMaxSize"));
	request.setAttribute("imageMaxSize", ResourceUtil.getKmssConfigString("sys.att.imageMaxSize"));
	request.setAttribute("fileLimitType", ResourceUtil.getKmssConfigString("sys.att.fileLimitType"));
	request.setAttribute("disabledFileType", ResourceUtil.getKmssConfigString("sys.att.disabledFileType"));
	request.setAttribute("isOfficePDFJudge", JgWebOffice.isOfficePDFJudge() ? "true" : "false");
	request.setAttribute("isSupportAttLarge", SysAttUtil.isSupportAttLarge() ? "true" : "false");
%>
<script type="text/javascript">
	//是否启用大附件
	var supportLarge = "${isSupportAttLarge}" == "true";
	<c:if test="${param.fdSupportLarge!=null && param.fdSupportLarge!=''}">
		supportLarge = ${JsParam.fdSupportLarge};
	</c:if>
	 var attachmentConfig = {
		// 上传路径
		uploadurl: '${uploadUrl}',
		// 上传方法名
		methodName: '${methodName}',
		// 是否支持直连模式
		isSupportDirect: ${isSupportDirect},
		// 文件key
		fileVal: '${fileVal}'|| null,
		//注册before-send-file事件
		beforeSendFile:true
	 }

	 var attachmentObject_${_jsKey} = new Swf_AttachmentObject("${_jsKey}","${JsParam.fdModelName}","${JsParam.fdModelId}","${JsParam.fdMulti}","${JsParam.fdAttType}","edit",supportLarge,"${JsParam.enabledFileType}",false,"${JsParam.uploadUrl}",attachmentConfig, "${JsParam.fileNumLimit}", "${JsParam.disabledImageView}");
	 //上传链接
	 //扩展信息
	 <c:if test="${not empty param.extParam}">
	 	attachmentObject_${_jsKey}.extParam = "${JsParam.extParam}";
	 </c:if>
	//参数设置
	 <c:if test="${param.fdImgHtmlProperty!=null && param.fdImgHtmlProperty!=''}">
	 	attachmentObject_${_jsKey}.fdImgHtmlProperty = "${JsParam.fdImgHtmlProperty}";
	 </c:if>
	 <c:if test="${param.fdShowMsg!=null && param.fdShowMsg!=''}">
	 	attachmentObject_${_jsKey}.fdShowMsg = ${JsParam.fdShowMsg};
	 </c:if> 
	 <c:if test="${param.hidePicName!=null && param.hidePicName!=''}">
	 	attachmentObject_${_jsKey}.hidePicName = ${JsParam.hidePicName};
	 </c:if> 
	 <c:if test="${param.showDefault!=null && param.showDefault!=''}">
		attachmentObject_${_jsKey}.showDefault = ${JsParam.showDefault};
	 </c:if>
	 <c:if test="${param.buttonDiv!=null && param.buttonDiv!=''}">
		attachmentObject_${_jsKey}.buttonDiv = "${JsParam.buttonDiv}";
	 </c:if>
	 <c:if test="${param.isTemplate!=null && param.isTemplate!=''}">
		attachmentObject_${_jsKey}.isTemplate = ${JsParam.isTemplate};
	 </c:if>
	 <c:if test="${param.fdRequired!=null && param.fdRequired!=''}">
		attachmentObject_${_jsKey}.required = ${JsParam.fdRequired};
	 </c:if>
	 <c:if test="${param.enabledFileType!=null && param.enabledFileType!=''}">
		attachmentObject_${_jsKey}.enabledFileType = "${JsParam.enabledFileType}";
	 </c:if> 
	 //是否选择附件后马上上传
	 <c:if test="${param.uploadAfterSelect!=null && param.uploadAfterSelect!=''}">
		attachmentObject_${_jsKey}.uploadAfterSelect= ${JsParam.uploadAfterSelect};
	 </c:if>
	//设置宽高
	 <c:if test="${param.width!=null && param.width!=''}">
	 	attachmentObject_${_jsKey}.width = "${JsParam.width}";	
	 </c:if>	
	 <c:if test="${param.height!=null && param.height!=''}">
	 	attachmentObject_${_jsKey}.height = "${JsParam.height}";	
	 </c:if>
	 <c:if test="${param.proportion!=null && param.proportion!=''}">
	 	attachmentObject_${_jsKey}.proportion = "${JsParam.proportion}";	
	 </c:if>
	 <c:if test="${not empty param.fdViewType}">
		attachmentObject_${_jsKey}.setFdViewType("${JsParam.fdViewType}");
	 </c:if>
	 <c:if test="${param.fdLabel!=null && param.fdLabel!=''}">
		attachmentObject_${_jsKey}.setFdLabel("${JsParam.fdLabel}");	
	</c:if>	
	<c:if test="${param.fdGroup!=null && param.fdGroup!=''}">
		attachmentObject_${_jsKey}.setFdGroup("${JsParam.fdGroup}");	
	</c:if>	
	<c:if test="${param.addToPreview!=null && param.addToPreview!=''}">
		attachmentObject_${_jsKey}.addToPreview="${JsParam.addToPreview}";
	</c:if>
	<c:if test="${param.uploadText!=null && param.uploadText!=''}">
    	attachmentObject_${_jsKey}.uploadText = "${JsParam.uploadText}";
 	</c:if>
 	<%-- 是否开启文件拖拽上传（默认开启） --%>
	<c:if test="${param.supportDnd!=null && param.supportDnd!=''}">
		attachmentObject_${_jsKey}.setSupportDnd("${JsParam.supportDnd}");
	</c:if>
 	<%-- 是否开启拖拽排序（默认开启） --%>
	<c:if test="${param.supportDndSort!=null && param.supportDndSort!=''}">
		attachmentObject_${_jsKey}.setSupportDndSort("${JsParam.supportDndSort}");
	</c:if>
 	<%-- 固定宽度 --%>
	<c:if test="${param.fixedWidth!=null && param.fixedWidth!=''}">
		attachmentObject_${_jsKey}.setFixedWidth("${JsParam.fixedWidth}");
	</c:if>
	<%-- 总宽度（可传入：90% 或 980px），默认：100% --%>
	<c:if test="${param.totalWidth!=null && param.totalWidth!=''}">
		attachmentObject_${_jsKey}.setTotalWidth("${JsParam.totalWidth}");
	</c:if>
	<%-- 是否打印模式（或者通过全局设置：window.isPrintModel = true） --%>
	<c:if test="${param.isPrintModel!=null && param.isPrintModel!=''}">
		attachmentObject_${_jsKey}.setIsPrintModel("${JsParam.isPrintModel}");
	</c:if>
	<%-- 是否允许删除（只针对单附件模式），默认允许删除 --%>
	<c:if test="${param.showDelete!=null && param.showDelete!=''}">
		attachmentObject_${_jsKey}.setShowDelete("${JsParam.showDelete}");
	</c:if>
	<%-- 单文件时，是否隐藏提示 --%>
	 <c:if test="${param.hideTips!=null && param.hideTips!='' && param.hideTips=='true'}"> 
		 attachmentObject_${_jsKey}.setHideTips(true);
	 </c:if>
	 <%-- 单文件时，是否隐藏【替换】 --%>
	 <c:if test="${param.hideReplace!=null && param.hideReplace!='' && param.hideReplace=='true'}"> 
		 attachmentObject_${_jsKey}.setHideReplace(true);
	 </c:if>
	 <%-- 自己定义文件宽度 --%>
		<c:if test="${param.filenameWidth!=null && param.filenameWidth!=''}">
			attachmentObject_${_jsKey}.setFilenameWidth("${JsParam.filenameWidth}");
		</c:if>
	 <%-- 是否可以重新命名  --%>
	 <c:if test="${param.canChangeName!=null && param.canChangeName!='' && param.canChangeName=='false'}"> 
		 attachmentObject_${_jsKey}.setCanChangeName(false);
	 </c:if>
	 <c:if test="${param.canChangeName!=null && param.canChangeName!='' && param.canChangeName=='true'}"> 
	   attachmentObject_${_jsKey}.setCanChangeName(true);
	  // attachmentObject_${_jsKey}.canChangeName=true;
    </c:if>
	 <%-- 
	//是否开启flash在线预览
	<c:if test="<%=SysAttSwfUtils.isSwfEnabled()%>">
		attachmentObject_${_jsKey}.isSwfEnabled = true;
		attachmentObject_${_jsKey}.resetReadFile(".doc;.xls;.ppt;.docx;.xlsx;.pptx;.pdf");
	</c:if>
	--%>
	//是否启用PDF控件
	<c:if test="${isOfficePDFJudge eq 'true'}">
		attachmentObject_${_jsKey}.appendPrintFile("pdf");
	</c:if>
	//设置常用附件的最大限制
	<c:if test= "${not empty smallMaxSize}">
		attachmentObject_${_jsKey}.setSmallMaxSizeLimit("${smallMaxSize}");
		attachmentObject_${_jsKey}.setSingleMaxSize("${smallMaxSize}");
	</c:if>
	//设置图片附件的最大限制
	<c:if test="${(not empty imageMaxSize) && param.fdAttType eq 'pic'}">
		attachmentObject_${_jsKey}.setSmallMaxSizeLimit("${imageMaxSize}");
	</c:if>
	//自定义图片附件的最大限制
	<c:if test="${param.fdImgSizeLimit!=null && param.fdImgSizeLimit!=''}">
	 	attachmentObject_${_jsKey}.setSmallMaxSizeLimit("${JsParam.fdImgSizeLimit}");
	</c:if>
	//设置是否限制上传的文件类型
	<c:if test='${not empty fileLimitType}'>
		attachmentObject_${_jsKey}.setFileLimitType("${fileLimitType}");
	</c:if>
	//设置限制上传的文件类型
	<c:if test="${(not empty fileLimitType) && (disabledFileType != null)}">
		attachmentObject_${_jsKey}.setDisableFileType("${disabledFileType}");
	</c:if>
	// 文件名长度的限制
	<c:if test="${param.fileNameMax!=null && param.fileNameMax!=''}">
		attachmentObject_${_jsKey}._fileNameMax = ${JsParam.fileNameMax};
	</c:if>
	<%
		//以下代码用于附件上传刷新后时，页面中根据附件ID信息，或modelname，modelID，key展现附件
		AttachmentDetailsForm _attForms = (AttachmentDetailsForm)pageContext.getAttribute("attForms");
		if(_attForms!=null){
			try{
				ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService)SpringBeanUtil.getBean("sysAttMainService");
				String _attachmentIds = _attForms.getAttachmentIds();
				List sysAttList = _attForms.getAttachments();
				if(sysAttList.isEmpty()){
					if(StringUtil.isNotNull(_attachmentIds)){
						sysAttList = sysAttMainService.findByPrimaryKeys(_attachmentIds.split(";"));
					}else{
						String _modelName = request.getParameter("fdModelName");
						String _modelId = request.getParameter("fdModelId");
						String _key = request.getParameter("fdKey");
						if(StringUtil.isNotNull(_modelName) 
								&& StringUtil.isNotNull(_modelId) 
								&& StringUtil.isNotNull(_key)){
							sysAttList = sysAttMainService.findByModelKey(_modelName,_modelId,_key);
						}
					}
					if(sysAttList!=null && !sysAttList.isEmpty()){
						_attForms.getAttachments().addAll(sysAttList);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		KMSSUser user = UserUtil.getKMSSUser();
		pageContext.setAttribute("currUserId", user.getUserId()); 
		pageContext.setAttribute("isAdmin", user.isAdmin()); 
	%>
	//填充附件信息
	<c:forEach items="${attForms.attachments}" var="sysAttMain"	varStatus="vstatus">
		<c:choose>
			<c:when test="${ isAdmin || (param.otherCanNotDelete ne 'true' && param.allCanNotDelete ne 'true') || (param.otherCanNotDelete eq 'true' && param.allCanNotDelete ne 'true' && currUserId eq sysAttMain.fdCreatorId)||  (kmReviewMainForm.docStatus=='10'&& currUserId eq sysAttMain.fdCreatorId) }">
				attachmentObject_${_jsKey}.addDoc("${lfn:escapeJs(sysAttMain.fdFileName)}", "${sysAttMain.fdId}", true, "${sysAttMain.fdContentType}", "${sysAttMain.fdSize}", "${sysAttMain.fdFileId}", 0, true);
			</c:when>
			<c:otherwise>
				attachmentObject_${_jsKey}.addDoc("${lfn:escapeJs(sysAttMain.fdFileName)}", "${sysAttMain.fdId}", true, "${sysAttMain.fdContentType}", "${sysAttMain.fdSize}", "${sysAttMain.fdFileId}", 0, false);
			</c:otherwise>
		</c:choose>
		<c:set var="attachmentId" value="${sysAttMain.fdId}"/>
	</c:forEach>
		attachmentObject_${_jsKey}.canMove = true;
		<%
			String enable = SysAttMainHistoryConfig.newInstance().getAttHistoryEnable();
			if("true".equals(enable)){
				String fdModelName = request.getParameter("fdModelName");
				if(StringUtil.isNotNull(fdModelName)){
					if(HistoryVersionPlugin.isEnableHistory(fdModelName)){
		%>
			attachmentObject_${_jsKey}.canReadHistory=true;
		<%
					}
				}
			}
		%>
	<c:if test="${param.fdPicContentWidth!=null && param.fdPicContentWidth!=''}">
		attachmentObject_${_jsKey}.fdPicContentWidth = "${JsParam.fdPicContentWidth}";
	</c:if>
	<c:if test="${param.fdPicContentHeight!=null && param.fdPicContentHeight!=''}">
		attachmentObject_${_jsKey}.fdPicContentHeight = "${JsParam.fdPicContentHeight}";
	</c:if>
	<c:if test="${param.fdLayoutType!=null && param.fdLayoutType!=''}">
		attachmentObject_${_jsKey}.setFdLayoutType("${JsParam.fdLayoutType}");
	</c:if>
	
	//wps加载项
	<c:if test="${param.wpsoaassist!=null && param.wpsoaassist!=''}">
		attachmentObject_${_jsKey}.setWpsoaassist('${JsParam.wpsoaassist}');
		attachmentObject_${_jsKey}.wpsoaassistEmbed='${JsParam.wpsoaassistEmbed}';
	</c:if>
	
	//wps加载项套红
	<c:if test="${param.redhead!=null && param.redhead!=''}">
		attachmentObject_${_jsKey}.setRedhead('${JsParam.redhead}');
	</c:if>
	
	<c:if test="${param.forceRevisions!=null && param.forceRevisions!=''}">
		attachmentObject_${_jsKey}.setForceRevisions('${JsParam.forceRevisions}');
	</c:if>
	<c:if test="${param.saveRevisions!=null && param.saveRevisions!=''}">
		attachmentObject_${_jsKey}.setSaveRevisions('${JsParam.saveRevisions}');
	</c:if>
	
	<c:if test="${param.newFlag!=null && param.newFlag!=''}">
		attachmentObject_${_jsKey}.setNewFlag('${JsParam.newFlag}');
	</c:if>
	
	//书签
	<c:if test="${not empty JsParam.bookMarks}">
		attachmentObject_${_jsKey}.setBookMarks('${JsParam.bookMarks}');
	</c:if>

	<c:if test="${param.signtrue!=null && param.signtrue!=''}">
		attachmentObject_${_jsKey}.setSignTrue('${JsParam.signtrue}');
	</c:if>
	//wps加载项清稿
	<c:if test="${param.cleardraft!=null && param.cleardraft!=''}">
		attachmentObject_${_jsKey}.setCleardraft('${JsParam.cleardraft}');
	</c:if>
	<c:if test="${param.nodevalue!=null && param.nodevalue!=''}">
		attachmentObject_${_jsKey}.setNodevalue('${JsParam.nodevalue}');
	</c:if>
	<c:if test="${param.wpsExtAppModel!=null && param.wpsExtAppModel!=''}">
		attachmentObject_${_jsKey}.setWpsExtAppModel('${JsParam.wpsExtAppModel}');
	</c:if>
	
	
	//权限设置
	<c:if test="${attachmentId!=null && attachmentId!=''}">
		<%
			String attId = (String)pageContext.getAttribute("attachmentId");
			if(attId != null && attId.startsWith("WU_FILE_")) {
		%>
				<%--附件是由模板带过来的，id以WU_FILE_开头，目前有文档知识库使用 --%>
				attachmentObject_${_jsKey}.canPrint = true;
				attachmentObject_${_jsKey}.canDownload = true;
				attachmentObject_${_jsKey}.canRead = true;
				attachmentObject_${_jsKey}.canCopy = true;
				<c:set var="canCopy" value="1"/>	
				attachmentObject_${_jsKey}.canEdit=true;
		<%
			} else {
		%>	
				attachmentObject_${_jsKey}.canPrint=false;
				<kmss:auth
					requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=print&fdId=${attachmentId}"
					requestMethod="GET">
					attachmentObject_${_jsKey}.canPrint=true;
				</kmss:auth>
				attachmentObject_${_jsKey}.canDownload=false;	
				<kmss:auth	
					requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${attachmentId}"
							requestMethod="GET">
					attachmentObject_${_jsKey}.canDownload=true;		
				</kmss:auth>
				attachmentObject_${_jsKey}.canRead=false;
				<kmss:auth	
					requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=read&fdId=${attachmentId}"
							requestMethod="GET">
					attachmentObject_${_jsKey}.canRead=true;		
				</kmss:auth>
				attachmentObject_${_jsKey}.canCopy = false;	
				<kmss:auth	
					requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=copy&fdId=${attachmentId}"
							requestMethod="GET">
					attachmentObject_${_jsKey}.canCopy = true;	
					<c:set var="canCopy" value="1"/>	
				</kmss:auth>
				attachmentObject_${_jsKey}.canEdit = false;	
				<kmss:auth	
					requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=edit&fdId=${attachmentId}"
							requestMethod="GET">
					attachmentObject_${_jsKey}.canEdit=true;
				</kmss:auth>
		<%
			}
		%>
	</c:if>
	<c:if test="${param.canRead!=null && param.canRead!='' && param.canRead=='false'}">
		 attachmentObject_${_jsKey}.canRead=false;
	</c:if>
	<c:if test="${param.canEdit!=null && param.canEdit!='' && param.canEdit=='false'}">
		 attachmentObject_${_jsKey}.canEdit=false;
	</c:if>
	<c:if test="${param.canEdit!=null && param.canEdit!='' && param.canEdit=='true'}">
	 attachmentObject_${_jsKey}.canEdit=true;
   </c:if>
   
   <c:if test="${param.canPrint!=null && param.canPrint!='' && param.canPrint=='false'}">
	 	attachmentObject_${_jsKey}.canPrint=false;
	</c:if>
	<c:if test="${param.canPrint!=null && param.canPrint!='' && param.canPrint=='true'}">
		attachmentObject_${_jsKey}.canPrint=true;
	</c:if>
	//金格iWebPDF2018签章
	<%		
		if("windows".equals(JgWebOffice.getOSType(request)) && JgWebOffice.isJGPDF2018SignatureEnabled()){	
	%>
		attachmentObject_${_jsKey}.setJGSignaturePDFEnabled(true);
	<%}%>
	// 是否使用了点聚来签章
	<%
		if(SysAttConfigUtil.isEnableAttachmentSignature() &&
		    "2".equals(SysAttConfigUtil.getAttachmentSignatureType())){
	%>
	     attachmentObject_${_jsKey}.setDianjuSignatureEnabled(true);
	<%}%>

	// 是否是使用福昕阅读
	<%
		if("6".equalsIgnoreCase(SysAttConfigUtil.getReadOLConfig())){
	%>
	attachmentObject_${_jsKey}.setReadOnlyFoxit(true);
	<%}%>
	// 是否启用附件签章
	<%
		if(SysAttConfigUtil.isEnableAttachmentSignature()){
	%>
	   attachmentObject_${_jsKey}.setEnableAttachmentSignature(true);
	<%}%>
	// 是否使用了金格签章（不用admin.do的配置）
	<%
		if(SysAttConfigUtil.isEnableAttachmentSignature()
		 && "1".equals(SysAttConfigUtil.getAttachmentSignatureType())){
	%>
	   attachmentObject_${_jsKey}.setEnableAttachmentSignatureByJG(true);
	<%}%>
	attachmentObject_${_jsKey}.beforeShow();
	
	//页面加载完成后呈现附件界面
	<c:if test="${empty ____content____ }">
		(function(){
			var ua = navigator.userAgent
				ie = ua.match( /MSIE\s([\d\.]+)/ ) || ua.match( /(?:trident)(?:.*rv:([\w.]+))?/i )
				ieversion = ie ? parseFloat( ie[ 1 ] ) : null;
			//ie8下upload用的是flash，需要等待load事件结束才show
			if(ieversion && ieversion == 8){
				Com_AddEventListener(window,"load", function() {
					attachmentObject_${_jsKey}.show();
				});
			}else{
				attachmentObject_${_jsKey}.show();
			}
		})()
	</c:if>
</script>
<!-- 判断是否处于content标签中--防止flash移动导致出错 -->
<c:if test="${not empty ____content____ }">
	<ui:event event="show">
		attachmentObject_${_jsKey}.show();
	</ui:event>
</c:if>
<%-- 附件${fdKey}结束 --%>
<%
if(originFormBean != null){
	pageContext.setAttribute(fromBeanVar, originFormBean);
}
%>
