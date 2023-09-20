<%@page import="java.util.Map"%>
<%@page import="com.landray.kmss.sys.metadata.interfaces.IExtendDataForm"%>
<%@page import="com.landray.kmss.common.forms.IExtendForm"%>
<%@page import="com.landray.kmss.util.IDGenerator"%>
<%@page import="com.landray.kmss.sys.attachment.util.SysAttUtil"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@ page import="com.landray.kmss.sys.authentication.user.KMSSUser" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="java.util.List,com.landray.kmss.util.StringUtil,com.landray.kmss.util.ResourceUtil,com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@page import="com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm,com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService,com.landray.kmss.sys.attachment.plugin.HistoryVersionPlugin,com.landray.kmss.sys.attachment.model.SysAttMainHistoryConfig"%>
<%
if(request.getParameter("idx").indexOf("!{index}")!=-1){
	//return;
}
String key=request.getParameter("fdKey");
String detailTableName=key.split("\\.")[0];
String fieldName=key.split("\\.")[1];
pageContext.setAttribute("fieldName", fieldName);
if(key.indexOf(".")>=0){
	key=key.replaceAll("\\.", "_");
}
pageContext.setAttribute("fdKey", key);

//System.out.println("detailTableName:"+detailTableName);
//System.out.println("key:"+key);


if(request.getParameter("idx").indexOf("!{index}")==-1){
	//表单名称，
	String formName=request.getParameter("formName");
	IExtendForm formxx=(IExtendForm)request.getAttribute(formName);
	String dTableType=request.getParameter("dTableType");
	if("nonxform".equals(dTableType)){
		String formListAttribute=request.getParameter("formListAttribute");
		List formList= (List)PropertyUtils.getProperty(formxx,formListAttribute);
		IExtendForm listDetail=(IExtendForm)formList.get(Integer.parseInt(request.getParameter("idx")));
		String key2=(String)PropertyUtils.getProperty(listDetail,fieldName);
		if(StringUtil.isNull(key2)){
			key2="uid_"+IDGenerator.generateID();
		}
		pageContext.setAttribute("fdKey", key2);
	}else{
		if(formxx instanceof IExtendDataForm){
			IExtendDataForm form=(IExtendDataForm)formxx;
			Map<String,Object> map=form.getExtendDataFormInfo().getFormData();
			List list=(List)map.get(detailTableName);
			Map<String,Object> mapDetail=(Map<String,Object>)list.get(Integer.parseInt(request.getParameter("idx")));
			String key2=(String)mapDetail.get(fieldName);
			if(StringUtil.isNull(key2)){
				key2="uid_"+IDGenerator.generateID();
				mapDetail.put(fieldName, key2);
			}
			pageContext.setAttribute("fdKey", key2);
			//System.out.println("fieldName:"+fieldName);
			//System.out.println("key2:"+key2);
		}else{
			String formListAttribute=request.getParameter("formListAttribute");
			List formList= (List)PropertyUtils.getProperty(formxx,formListAttribute);
			IExtendForm listDetail=(IExtendForm)formList.get(Integer.parseInt(request.getParameter("idx")));
			String key2=(String)PropertyUtils.getProperty(listDetail,fieldName);
			if(StringUtil.isNull(key2)){
				key2="uid_"+IDGenerator.generateID();
			}
			pageContext.setAttribute("fdKey", key2);
		}
	}
}

KMSSUser user = UserUtil.getKMSSUser();
pageContext.setAttribute("currUserId", user.getUserId()); 
pageContext.setAttribute("isAdmin", user.isAdmin()); 
%>
<%-- 附件${fdKey}开始 --%>

<script>
if (typeof(seajs) != 'undefined') {
	seajs.use( [ 'lui/jquery', 'lui/dialog', 'lui/topic' ], function($, dialog, topic) {
		var changeName;
		
		window.reName = function(att,i,self) {
			var fdFileName = att.fileList[i].fileName;
			if(fdFileName !=null &&fdFileName !=""){
				fdFileName = fdFileName.substring(0,fdFileName.lastIndexOf("."))
				fdFileName = encodeURI(encodeURI(fdFileName));
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
					} else {
						$.ajax({
							url: self.alterUrl,
							type: 'POST',
							data:{id:att.fileList[i].fdId,name:att.fileList[i].fileName},
							dataType: 'json',
							async:false, 
							success: function(data){
								self.showed = false;
								self.btnIntial = false;
								att.initMode = false;
								att.show();
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
<script>
//明细表附件相关的js引入，放到全局的sysForm_main.jsp中防止在编辑模式重复引入导致样式渲染错误
//Com_IncludeFile("swf_attachment.js","${KMSS_Parameter_ContextPath}sys/attachment/js/","js",true);
</script>
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
<c:set var="_htmlKey" value="${lfn:escapeHtml(fdKey)}" />
<c:set var="_jsKey" value="${lfn:escapeJs(fdKey)}" />
<div id="_List_${_htmlKey}_Attachment_Table">
	<html:hidden property="attachmentForms.${_htmlKey}.fdModelId" />
	<html:hidden property="attachmentForms.${_htmlKey}.extParam"    value="${HtmlParam.extParam }" />
	<html:hidden property="attachmentForms.${_htmlKey}.fdModelName" value="${HtmlParam.fdModelName }" />
	<html:hidden property="attachmentForms.${_htmlKey}.fdKey"		 value="${_htmlKey}" />
	<html:hidden property="attachmentForms.${_htmlKey}.fdAttType"	 value="${HtmlParam.fdAttType }" />
	<html:hidden property="attachmentForms.${_htmlKey}.fdMulti"	 value="${HtmlParam.fdMulti }" />
	<html:hidden property="attachmentForms.${_htmlKey}.deletedAttachmentIds" />
	<html:hidden property="attachmentForms.${_htmlKey}.attachmentIds" />
</div> 
<%-- 附件列表展现区 --%>
<div id="attachmentObject_${_htmlKey}_content_div" class="lui_upload_img_box">
	<img src="${KMSS_Parameter_ContextPath}sys/attachment/swf/loading.gif" border="0" style="width: 60px;height: 60px;"/>
</div>
<script type="text/javascript">
	//是否启用大附件
	var supportLarge = <%=SysAttUtil.isSupportAttLarge()?"true":"false"%>;
	<c:if test="${param.fdSupportLarge!=null && param.fdSupportLarge!=''}">
		supportLarge = ${JsParam.fdSupportLarge};
	</c:if>
	
</script>

<script type="text/javascript" >
//这个判断 确保默认的fdkey不做渲染，防止报错
if("${_jsKey}".indexOf("<%=fieldName%>")==-1){
	var attachmentConfig = {
	    beforeSendFile:true
    };
	var attachmentObject_${_jsKey} = new Swf_AttachmentObject("${_jsKey}","${JsParam.fdModelName}","${JsParam.fdModelId}","${JsParam.fdMulti}"
			,"${JsParam.fdAttType}","edit",supportLarge,"${JsParam.enabledFileType}",false,"${JsParam.uploadUrl}",attachmentConfig);
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
	 <%-- 
	//是否开启flash在线预览
	<c:if test="<%=SysAttSwfUtils.isSwfEnabled()%>">
		attachmentObject_${fdKey}.isSwfEnabled = true;
		attachmentObject_${fdKey}.resetReadFile(".doc;.xls;.ppt;.docx;.xlsx;.pptx;.pdf");
	</c:if>
	--%>
	//是否启用PDF控件
	<c:if test="<%=JgWebOffice.isOfficePDFJudge()%>">
		attachmentObject_${_jsKey}.appendPrintFile("pdf");
	</c:if>	
	//设置常用附件的最大限制
	<c:if test='<%=StringUtil.isNotNull(ResourceUtil.getKmssConfigString("sys.att.smallMaxSize"))%>'>
		attachmentObject_${_jsKey}.setSmallMaxSizeLimit(<%=ResourceUtil.getKmssConfigString("sys.att.smallMaxSize")%>);
	</c:if>
	//设置图片附件的最大限制
	<c:if test='<%=StringUtil.isNotNull(ResourceUtil.getKmssConfigString("sys.att.imageMaxSize"))&& "pic".equals(request.getParameter("fdAttType"))%>'>
		attachmentObject_${_jsKey}.setSmallMaxSizeLimit(<%=ResourceUtil.getKmssConfigString("sys.att.imageMaxSize")%>);
	</c:if>
	//设置是否限制上传的文件类型
	<c:if test='<%=StringUtil.isNotNull(ResourceUtil.getKmssConfigString("sys.att.fileLimitType"))%>'>
		attachmentObject_${_jsKey}.setFileLimitType('<%=ResourceUtil.getKmssConfigString("sys.att.fileLimitType")%>');
	</c:if>
	//设置限制上传的文件类型
	<c:if test='<%=StringUtil.isNotNull(ResourceUtil.getKmssConfigString("sys.att.fileLimitType"))&&ResourceUtil.getKmssConfigString("sys.att.disabledFileType")!=null%>'>
		attachmentObject_${_jsKey}.setDisableFileType('<%=ResourceUtil.getKmssConfigString("sys.att.disabledFileType")%>');
	</c:if>
	//填充附件信息
	<c:forEach items="${attForms.attachments}" var="sysAttMain"	varStatus="vstatus">
		<c:choose>
		<c:when test="${ isAdmin || (param.otherCanNotDelete ne 'true' && param.allCanNotDelete ne 'true') || (param.otherCanNotDelete eq 'true' && param.allCanNotDelete ne 'true' && currUserId eq sysAttMain.fdCreatorId) || ( kmReviewMainForm.docStatus=='10'&& currUserId eq sysAttMain.fdCreatorId) }">
				attachmentObject_${_jsKey}.addDoc("${lfn:escapeJs(sysAttMain.fdFileName)}", "${sysAttMain.fdId}", true, "${sysAttMain.fdContentType}", "${sysAttMain.fdSize}", "${sysAttMain.fdFileId}", 0, true,"${param.hideReplace}");
			</c:when>
			<c:otherwise>
				attachmentObject_${_jsKey}.addDoc("${lfn:escapeJs(sysAttMain.fdFileName)}", "${sysAttMain.fdId}", true, "${sysAttMain.fdContentType}", "${sysAttMain.fdSize}", "${sysAttMain.fdFileId}", 0, false,"${param.hideReplace}");
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
//权限设置
<c:if test="${attachmentId!=null && attachmentId!=''}">
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
</c:if>


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
}
</script>

<c:if test="${not empty ____content____ }">
	<ui:event event="show">
		if(attachmentObject_${_jsKey})
			attachmentObject_${_jsKey}.show();
		
	</ui:event>
</c:if>
<!-- 防止重复添加监听事件代码导致重复监听处理 -->
<c:if test="${param.idx eq '!{index}'}">
<script type="text/javascript">
//明细表内控件 有明细表的 table-add 事件触发初始化
if(typeof window.Attachment_Object_LoadInfo == "undefined"){
    window.Attachment_Object_LoadInfo = {};
}
if (!Attachment_Object_LoadInfo['${_jsKey}']) {
    Attachment_Object_LoadInfo['${_jsKey}'] = true;
    $(document).on('table-add','table[id$=<%=detailTableName%>]',function(e,row){
        var uid="uid_"+parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
        $(row).find("input[name *='${fieldName}'][type='hidden']").val(uid);
        //$(row).find("div[name ='attachmentObject_content_div']").attr("id","attachmentObject_"+uid+"_content_div");
        //$(row).find("div[name ='_List_Attachment_Table']").attr("id","_List_"+uid+"_Attachment_Table");

        $(row).find("div[id ='attachmentObject_${_jsKey}_content_div']").attr("id","attachmentObject_"+uid+"_content_div");
        $(row).find("div[id ='_List_${_jsKey}_Attachment_Table']").attr("id","_List_"+uid+"_Attachment_Table");
        $(row).find("input[name ^='attachmentForms.${_jsKey}'][type='hidden']").each(function(){
            $(this).attr("name",$(this).attr("name").replace(/(attachmentForms)\.\w+\.(\w+)/g,"$1."+uid+".$2"));
            if($(this).attr("name").indexOf("fdKey")!=-1){
                $(this).val(uid);
            }

        });
         var attachmentConfig = {
            beforeSendFile:true
         };
         var attachmentObject_ = new Swf_AttachmentObject(uid,"${JsParam.fdModelName}","${JsParam.fdModelId}","${JsParam.fdMulti}"
                    ,"${JsParam.fdAttType}","edit",supportLarge,"${JsParam.enabledFileType}",false,null,attachmentConfig);
         attachmentObject_.canMove = true;
         //扩展信息
         <c:if test="${not empty param.extParam}">
            attachmentObject_.extParam = "${JsParam.extParam}";
         </c:if>
        //参数设置
         <c:if test="${param.fdImgHtmlProperty!=null && param.fdImgHtmlProperty!=''}">
            attachmentObject_.fdImgHtmlProperty = "${JsParam.fdImgHtmlProperty}";
         </c:if>
         <c:if test="${param.fdShowMsg!=null && param.fdShowMsg!=''}">
            attachmentObject_.fdShowMsg = ${JsParam.fdShowMsg};
         </c:if>
         <c:if test="${param.hidePicName!=null && param.hidePicName!=''}">
            attachmentObject_.hidePicName = ${JsParam.hidePicName};
         </c:if>
         <c:if test="${param.showDefault!=null && param.showDefault!=''}">
            attachmentObject_.showDefault = ${JsParam.showDefault};
         </c:if>
         <c:if test="${param.buttonDiv!=null && param.buttonDiv!=''}">
            attachmentObject_.buttonDiv = "${JsParam.buttonDiv}";
         </c:if>
         <c:if test="${param.isTemplate!=null && param.isTemplate!=''}">
            attachmentObject_.isTemplate = ${JsParam.isTemplate};
         </c:if>
         <c:if test="${param.fdRequired!=null && param.fdRequired!=''}">
            attachmentObject_.required = ${JsParam.fdRequired};
         </c:if>
         <c:if test="${param.enabledFileType!=null && param.enabledFileType!=''}">
            attachmentObject_.enabledFileType = "${JsParam.enabledFileType}";
         </c:if>
         //是否选择附件后马上上传
         <c:if test="${param.uploadAfterSelect!=null && param.uploadAfterSelect!=''}">
            attachmentObject_.uploadAfterSelect= ${JsParam.uploadAfterSelect};
         </c:if>
        //设置宽高
         <c:if test="${param.width!=null && param.width!=''}">
            attachmentObject_.width = "${JsParam.width}";
         </c:if>
         <c:if test="${param.height!=null && param.height!=''}">
            attachmentObject_.height = "${JsParam.height}";
         </c:if>
         <c:if test="${param.proportion!=null && param.proportion!=''}">
            attachmentObject_.proportion = "${JsParam.proportion}";
         </c:if>
         <c:if test="${not empty param.fdViewType}">
            attachmentObject_.setFdViewType("${JsParam.fdViewType}");
         </c:if>
         <c:if test="${param.fdLayoutType!=null && param.fdLayoutType!=''}">
            attachmentObject_.setFdLayoutType("${JsParam.fdLayoutType}");
         </c:if>
         <c:if test="${param.fdPicContentWidth!=null && param.fdPicContentWidth!=''}">
            attachmentObject_.fdPicContentWidth = "${JsParam.fdPicContentWidth}";
         </c:if>
         <c:if test="${param.fdPicContentHeight!=null && param.fdPicContentHeight!=''}">
            attachmentObject_.fdPicContentHeight = "${JsParam.fdPicContentHeight}";
         </c:if>
         <%--
        //是否开启flash在线预览
        <c:if test="<%=SysAttSwfUtils.isSwfEnabled()%>">
            attachmentObject_${fdKey}.isSwfEnabled = true;
            attachmentObject_${fdKey}.resetReadFile(".doc;.xls;.ppt;.docx;.xlsx;.pptx;.pdf");
        </c:if>
        --%>
        //是否启用PDF控件
        <c:if test="<%=JgWebOffice.isOfficePDFJudge()%>">
            attachmentObject_.appendPrintFile("pdf");
        </c:if>
        //设置常用附件的最大限制
        <c:if test='<%=StringUtil.isNotNull(ResourceUtil.getKmssConfigString("sys.att.smallMaxSize"))%>'>
            attachmentObject_.setSmallMaxSizeLimit(<%=ResourceUtil.getKmssConfigString("sys.att.smallMaxSize")%>);
            attachmentObject_.setSingleMaxSize('<%=ResourceUtil.getKmssConfigString("sys.att.smallMaxSize")%>');
        </c:if>
        //设置图片附件的最大限制
        <c:if test='<%=StringUtil.isNotNull(ResourceUtil.getKmssConfigString("sys.att.imageMaxSize"))&& "pic".equals(request.getParameter("fdAttType"))%>'>
            attachmentObject_.setSmallMaxSizeLimit(<%=ResourceUtil.getKmssConfigString("sys.att.imageMaxSize")%>);
        </c:if>
        //设置是否限制上传的文件类型
        <c:if test='<%=StringUtil.isNotNull(ResourceUtil.getKmssConfigString("sys.att.fileLimitType"))%>'>
            attachmentObject_.setFileLimitType('<%=ResourceUtil.getKmssConfigString("sys.att.fileLimitType")%>');
        </c:if>
        //设置限制上传的文件类型
        <c:if test='<%=StringUtil.isNotNull(ResourceUtil.getKmssConfigString("sys.att.fileLimitType"))&&ResourceUtil.getKmssConfigString("sys.att.disabledFileType")!=null%>'>
            attachmentObject_.setDisableFileType('<%=ResourceUtil.getKmssConfigString("sys.att.disabledFileType")%>');
        </c:if>

        //填充附件信息
        <c:forEach items="${attForms.attachments}" var="sysAttMain"	varStatus="vstatus">
            <c:choose>
            <c:when test="${ isAdmin || (param.otherCanNotDelete ne 'true' && param.allCanNotDelete ne 'true') || (param.otherCanNotDelete eq 'true' && param.allCanNotDelete ne 'true' && currUserId eq sysAttMain.fdCreatorId) || (kmReviewMainForm.docStatus=='10'&& currUserId eq sysAttMain.fdCreatorId) }">
                    attachmentObject_.addDoc("${lfn:escapeJs(sysAttMain.fdFileName)}","${sysAttMain.fdId}",true,"${sysAttMain.fdContentType}","${sysAttMain.fdSize}","${sysAttMain.fdFileId}",0 true,"${param.hideReplace}");
                </c:when>
                <c:otherwise>
                    attachmentObject_.addDoc("${lfn:escapeJs(sysAttMain.fdFileName)}","${sysAttMain.fdId}",true,"${sysAttMain.fdContentType}","${sysAttMain.fdSize}","${sysAttMain.fdFileId}",0 false,"${param.hideReplace}");
                </c:otherwise>
            </c:choose>
            <c:set var="attachmentId" value="${sysAttMain.fdId}"/>
        </c:forEach>

        //权限设置
        <c:if test="${attachmentId!=null && attachmentId!=''}">
            attachmentObject_.canPrint=false;
            <kmss:auth
                requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=print&fdId=${attachmentId}"
                requestMethod="GET">
                attachmentObject_.canPrint=true;
            </kmss:auth>
            attachmentObject_.canDownload=false;
            <kmss:auth
                requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${attachmentId}"
                        requestMethod="GET">
                attachmentObject_.canDownload=true;
            </kmss:auth>
            attachmentObject_.canRead=false;
            <kmss:auth
                requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=read&fdId=${attachmentId}"
                        requestMethod="GET">
                attachmentObject_.canRead=true;
            </kmss:auth>
            attachmentObject_.canCopy = false;
            <kmss:auth
                requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=copy&fdId=${attachmentId}"
                        requestMethod="GET">
                attachmentObject_.canCopy = true;
                <c:set var="canCopy" value="1"/>
            </kmss:auth>
            attachmentObject_.canEdit = false;
            <kmss:auth
                requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=edit&e=true&fdId=${attachmentId}"
                        requestMethod="GET">
                attachmentObject_.canEdit=true;
            </kmss:auth>
        </c:if>
        attachmentObject_.show();
    });
}
$(document).on('table-delete','table[showStatisticRow][id$=<%=detailTableName%>]',function(e,row){
	var uid = $(row).find("input[name *='${fieldName}'][type='hidden']").val();
	if(Attachment_ObjectInfo[uid]){
		Attachment_ObjectInfo[uid].disabled = true;
		delete Attachment_ObjectInfo[uid];
	}
});

</script>
</c:if>

<%-- 附件${fdKey}结束 --%>
<%
if(originFormBean != null){
	pageContext.setAttribute(fromBeanVar, originFormBean);
}
%>


