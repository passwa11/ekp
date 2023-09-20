<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="java.util.List,java.util.ArrayList,com.landray.kmss.util.StringUtil,com.landray.kmss.util.ResourceUtil,com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@page import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%@page import="com.landray.kmss.sys.attachment.dao.ISysAttMainCoreInnerDao"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.sys.ui.plugin.SysUiPluginUtil,com.landray.kmss.sys.attachment.plugin.HistoryVersionPlugin,com.landray.kmss.sys.attachment.model.SysAttMainHistoryConfig"%>
<%@ page import="com.landray.kmss.sys.attachment.util.SysAttConfigUtil" %>

<%
	Object _preload = request.getAttribute("view_swf_preload");//string 类型
	if(_preload==null){
		//这个方法执行时间相对较长，但大多数情况不变，可以缓存到request scope中
		_preload = request.getContextPath()+"/"+SysUiPluginUtil.getThemesFileByName(request,"module");
		request.setAttribute("view_swf_preload",_preload);
	}
%>
<link rel="preload" type="text/css" href="<%=request.getAttribute("view_swf_preload")%>"></link>

<c:if test="${empty param.fdModelId}">
	<c:set var="fdKey" value="${param.fdKey}" />
</c:if>
<c:if test="${not empty param.fdModelId}">
	<!-- fdUID的作用主要是 用于在同一个页面中同一份附件需要重复显示，在审批意见展示中有应用场景 -->
	<c:set var="fdKey" value="${param.fdKey}_${param.fdModelId}${param.fdUID}" />
</c:if>
<c:set var="_htmlKey" value="${lfn:escapeHtml(fdKey)}" />
<c:set var="_jsKey" value="${lfn:escapeJs(fdKey)}" />
<script>
	if(typeof Com_Parameter.__sysAttMainlocale__ == "undefined")
		Com_Parameter.__sysAttMainlocale__= "<%= UserUtil.getKMSSUser(request).getLocale().toString().toLowerCase().replace('_', '-') %>";
</script>
<script>Com_IncludeFile("swf_attachment.js","${KMSS_Parameter_ContextPath}sys/attachment/js/","js",true);</script>
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
<c:set var="attForms" value="${_formBean.attachmentForms[param.fdKey]}" />
<c:set var="sysAttMains" value="${attForms.attachments}" />
<div id="attachmentObject_${_htmlKey}_content_div" class="lui_upload_img_box">
	<img src="${KMSS_Parameter_ContextPath}sys/attachment/swf/loading.gif" border="0" style="width: 60px;height: 60px;"/>
</div>
<%
	request.setAttribute("isOfficePDFJudge", String.valueOf( JgWebOffice.isOfficePDFJudge()));
	request.setAttribute("smallMaxSize", ResourceUtil.getKmssConfigString("sys.att.smallMaxSize"));
%>
<script type="text/javascript">
	var attachmentObject_${_jsKey} = new  Swf_AttachmentObject("${_jsKey}","${JsParam.fdModelName}","${JsParam.fdModelId}","${JsParam.fdMulti}","${JsParam.fdAttType}","view");
	<%
		//以下代码用于附件不通过form读取的方式
		List sysAttMains = (List)pageContext.getAttribute("sysAttMains");
		if(sysAttMains==null || sysAttMains.isEmpty()){
			try{
				String _modelName = request.getParameter("fdModelName");
				String _modelId = request.getParameter("fdModelId");
				if(StringUtil.isNotNull(_modelName) 
						&& StringUtil.isNotNull(_modelId)){
					String cacheKey = _modelName + "_" + _modelId;
					List cacheAtts = (List)request.getAttribute(cacheKey);
					if(cacheAtts!=null && !cacheAtts.isEmpty()){
						sysAttMains = cacheAtts;
					}else{
						String caheFlag = (String)request.getAttribute(cacheKey+"_flag");
						if(!"1".equals(caheFlag)){
							ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService)SpringBeanUtil.getBean("sysAttMainService");
							sysAttMains = ((ISysAttMainCoreInnerDao) sysAttMainService
									.getBaseDao()).findAttListByModel(_modelName,_modelId);
							request.setAttribute(cacheKey,sysAttMains);
							request.setAttribute(cacheKey+"_flag","1");
						}else{
							sysAttMains = new ArrayList();
						}
					}
					pageContext.setAttribute("sysAttMains",sysAttMains);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	%>
	//填充附件信息
	<c:forEach items="${sysAttMains}" var="sysAttMain"	varStatus="vstatus">
		<c:if test="${sysAttMain.fdKey==param.fdKey}">
		//统计下载次数
		attachmentObject_${_jsKey}.addDoc("${lfn:escapeJs(sysAttMain.fdFileName)}","${sysAttMain.fdId}",true,"${sysAttMain.fdContentType}","${sysAttMain.fdSize}","${sysAttMain.fdFileId}","${sysAttMain.downloadSum}");
		<c:set var="attachmentId" value="${sysAttMain.fdId}"/>
		</c:if>
	</c:forEach>
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
	<c:if test="${param.viewType!=null && param.viewType!=''}">
		//设置显示模式
		attachmentObject_${_jsKey}.fdViewType="${JsParam.viewType}";	
	</c:if>	
	<c:if test="${param.fdLabel!=null && param.fdLabel!=''}">
		attachmentObject_${_jsKey}.setFdLabel("${JsParam.fdLabel}");	
	</c:if>	
	<c:if test="${param.fdGroup!=null && param.fdGroup!=''}">
		attachmentObject_${_jsKey}.setFdGroup("${JsParam.fdGroup}");	
	</c:if>	
	<c:if test="${param.addToPreview!=null && param.addToPreview!=''}">
		attachmentObject_${_jsKey}.addToPreview("${JsParam.addToPreview}");
	</c:if>	
	<c:if test="${param.drawFunction!=null && param.drawFunction!=''}">
		//设置自定义列表显示函数
		attachmentObject_${_jsKey}.drawFunction=${JsParam.drawFunction};	
	</c:if>	
	<c:if test="${param.fdShowMsg !=null && param.fdShowMsg!=''}">
		//如果fdShowMsg参数为false,则默认不显示附件的信息
		attachmentObject_${_jsKey}.fdShowMsg=${JsParam.fdShowMsg};	
	</c:if>	
	<c:if test="${param.hidePicName !=null && param.hidePicName!=''}">
		//如果fdShowMsg参数为false,则默认不显示附件的信息
		attachmentObject_${_jsKey}.hidePicName=${JsParam.hidePicName};	
	</c:if>	
	<c:if test="${param.width!=null && param.width!=''}">
		//设置宽
		attachmentObject_${_jsKey}.width="${JsParam.width}";	
	</c:if>	
	<c:if test="${param.height!=null && param.height!=''}">
		//设置高
		attachmentObject_${_jsKey}.height="${JsParam.height}";	
	</c:if>
	<c:if test="${not empty param.fdViewType}">
		attachmentObject_${_jsKey}.setFdViewType("${JsParam.fdViewType}");
	</c:if>
	//是否强制使用金格，公文和合同场景
	<c:if test="${not empty param.fdForceUseJG}">
		attachmentObject_${_jsKey}.setFdForceUseJG("${JsParam.fdForceUseJG}");
	</c:if>
	<c:if test="${param.fdImgHtmlProperty!=null && param.fdImgHtmlProperty!=''}">
		attachmentObject_${_jsKey}.fdImgHtmlProperty="${JsParam.fdImgHtmlProperty}";
	</c:if>
	<c:if test="${isOfficePDFJudge eq 'true'}">
		attachmentObject_${_jsKey}.appendPrintFile("pdf");
	</c:if>
	<c:if test="${not empty smallMaxSize}">
		attachmentObject_${_jsKey}.smallMaxSizeLimit = Number("${smallMaxSize}");
	</c:if>
	<c:if test="${param.fdPicContentWidth!=null && param.fdPicContentWidth!=''}">
		attachmentObject_${_jsKey}.fdPicContentWidth = "${JsParam.fdPicContentWidth}";
	</c:if>
	<c:if test="${param.fdPicContentHeight!=null && param.fdPicContentHeight!=''}">
		attachmentObject_${_jsKey}.fdPicContentHeight = "${JsParam.fdPicContentHeight}";
	</c:if>
	<c:if test="${param.fdLayoutType!=null && param.fdLayoutType!=''}">
		attachmentObject_${_jsKey}.setFdLayoutType("${JsParam.fdLayoutType}");
	</c:if>
 	<c:if test="${param.fdForceDisabledOpt!=null && param.fdForceDisabledOpt!=''}">
		attachmentObject_${_jsKey}.forceDisabledOpt = '${JsParam.fdForceDisabledOpt}';
 	</c:if>
 	<c:if test="${param.isShowDownloadCount!=null && param.isShowDownloadCount!=''}">
		attachmentObject_${_jsKey}.isShowDownloadCount = ${JsParam.isShowDownloadCount};
	</c:if>

    <c:if test="${param.slideDown!=null && param.slideDown!=''}">
        attachmentObject_${_jsKey}.slideDown = ${JsParam.slideDown};
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
	
	//wps加载项编辑
	<c:if test="${param.canEdit!=null && param.canEdit!=''}">
		attachmentObject_${_jsKey}.setCanEdit('${JsParam.canEdit}');
	</c:if>
	
	<c:if test="${param.wpsExtAppModel!=null && param.wpsExtAppModel!=''}">
		attachmentObject_${_jsKey}.setWpsExtAppModel('${JsParam.wpsExtAppModel}');
	</c:if>
	
	//保存痕迹稿
	<c:if test="${param.saveRevisions!=null && param.saveRevisions!=''}">
		attachmentObject_${_jsKey}.setSaveRevisions('${JsParam.saveRevisions}');
	</c:if>
	
	//保存痕迹稿
	<c:if test="${param.cleardraft!=null && param.cleardraft!=''}">
		attachmentObject_${_jsKey}.setCleardraft('${JsParam.cleardraft}');
	</c:if>
	
	//强制不留痕
	<c:if test="${param.forceRevisions!=null && param.forceRevisions!=''}">
		attachmentObject_${_jsKey}.setForceRevisions('${JsParam.forceRevisions}');
	</c:if>
	
	//wps加载项套红书签
	<c:if test="${not empty JsParam.bookMarks}">
		attachmentObject_${_jsKey}.setBookMarks('${JsParam.bookMarks}');
	</c:if>
	
	//wps加载项套红节点
	<c:if test="${param.nodevalue!=null && param.nodevalue!=''}">
		attachmentObject_${_jsKey}.setNodevalue('${JsParam.nodevalue}');
	</c:if>
	
	//wps加载项印章
	<c:if test="${param.signtrue!=null && param.signtrue!=''}">
		attachmentObject_${_jsKey}.setSignTrue('${JsParam.signtrue}');
	</c:if>
	
	//权限设置
	<kmss:auth
		requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=print&fdId=${attachmentId}"
		requestMethod="GET">
		attachmentObject_${_jsKey}.canPrint=true;
		<c:set var="canPrint" value="1"/>
	</kmss:auth>
	<c:if test="${param.canDownload!='false'}">	
		<kmss:auth	
			requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${attachmentId}"
					requestMethod="GET">
			attachmentObject_${_jsKey}.canDownload=true;		
		</kmss:auth>
	</c:if>
	<c:if test="${param.canDownload=='false'}">	
		attachmentObject_${_jsKey}.canDownload=false;
		attachmentObject_${_jsKey}.canDownPic=false;
	</c:if>
	<kmss:auth	
		requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=read&fdId=${attachmentId}"
				requestMethod="GET">
		attachmentObject_${_jsKey}.canRead=true;		
	</kmss:auth>   
	<kmss:auth	
		requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=copy&fdId=${attachmentId}"
				requestMethod="GET">
		attachmentObject_${_jsKey}.canCopy=true;	
		<c:set var="canCopy" value="1"/>	
	</kmss:auth>
	//v=true 附件参考模式，只校验主文档的编辑权限才可以编辑，忽略当前处理人可编辑
	<kmss:auth	
		requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=edit&v=true&fdId=${attachmentId}"
				requestMethod="GET">
		attachmentObject_${_jsKey}.canEdit=true;
	</kmss:auth>

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

<c:if test="${not empty ____content____ }">
	<ui:event event="show">
		attachmentObject_${_jsKey}.show();
	</ui:event>
</c:if>
<%
	if (originFormBean != null) {
		pageContext.setAttribute("com.landray.kmss.web.taglib.FormBean",
				originFormBean);
	}
%>