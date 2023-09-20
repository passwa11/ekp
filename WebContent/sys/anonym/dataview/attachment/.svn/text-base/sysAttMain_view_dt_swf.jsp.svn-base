<%@page import="com.landray.kmss.util.IDGenerator"%>
<%@page import="java.util.Map"%>
<%@page import="com.landray.kmss.sys.metadata.interfaces.IExtendDataForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="java.util.List,com.landray.kmss.util.StringUtil,com.landray.kmss.util.ResourceUtil,com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@page import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@page import="com.landray.kmss.common.forms.IExtendForm"%>

<% 
	if(request.getParameter("idx").indexOf("!{index}")!=-1){
		return;
	}
	String dTableType=request.getParameter("dTableType"); 
	if("nonxform".equals(dTableType)){
		String formName=request.getParameter("formName");
		String key= request.getParameter("fdKey");
		String fieldName=key.split("\\.")[1];
		String formListAttribute=request.getParameter("formListAttribute");
		IExtendForm formxx=(IExtendForm)request.getAttribute(formName);
		List formList= (List)PropertyUtils.getProperty(formxx,formListAttribute);
		IExtendForm listDetail=(IExtendForm)formList.get(Integer.parseInt(request.getParameter("idx")));
		String key2=(String)PropertyUtils.getProperty(listDetail,fieldName);
		if(StringUtil.isNull(key2)){
			key2="uid_"+IDGenerator.generateID();
		}
		pageContext.setAttribute("fdKey", key2);
	}else{
		//表单名称，
		String formName=request.getParameter("formName");
		String key= request.getParameter("fdKey");
		String detailTableName=key.split("\\.")[0];
		String fieldName=key.split("\\.")[1];
	    IExtendDataForm form=(IExtendDataForm)request.getAttribute(formName);
	    Map<String,Object> map=form.getExtendDataFormInfo().getFormData();
	    List list=(List)map.get(detailTableName);
	    Map<String,Object> mapDetail=(Map<String,Object>)list.get(Integer.parseInt(request.getParameter("idx")));
	    String key2=(String)mapDetail.get(fieldName);
	    if(StringUtil.isNull(key2)){
			key2="uid_"+IDGenerator.generateID();
			mapDetail.put(fieldName, key2);
		}
	    pageContext.setAttribute("fdKey", key2);
	}
%>
<c:if test="${empty param.fdModelId}">
	<c:set var="param_fdKey" value="${fdKey}" />
</c:if>
<c:if test="${not empty param.fdModelId}">
	<c:set var="param_fdKey" value="${fdKey}" />
	<c:set var="fdKey" value="${fdKey}_${param.fdModelId}" />
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
<c:set var="attForms" value="${_formBean.attachmentForms[param_fdKey]}" />
<c:set var="sysAttMains" value="${attForms.attachments}" />
<div id="attachmentObject_${_htmlKey}_content_div" class="lui_upload_img_box">
	<img src="${KMSS_Parameter_ContextPath}sys/attachment/swf/loading.gif" border="0" style="width: 60px;height: 60px;"/>
</div>
<script type="text/javascript">
	var attachmentObject_${_jsKey} = new  Swf_AttachmentObject("${_jsKey}","${JsParam.fdModelName}","${JsParam.fdModelId}","${JsParam.fdMulti}","${JsParam.fdAttType}","view");
	//填充附件信息
	<c:forEach items="${sysAttMains}" var="sysAttMain"	varStatus="vstatus">
		attachmentObject_${_jsKey}.addDoc("${lfn:escapeJs(sysAttMain.fdFileName)}","${sysAttMain.fdId}",true,"${sysAttMain.fdContentType}","${sysAttMain.fdSize}","${sysAttMain.fdFileId}","${sysAttMain.downloadSum}");
		<c:set var="attachmentId" value="${sysAttMain.fdId}"/>
	</c:forEach>
	<c:if test="${param.viewType!=null && param.viewType!=''}">
		//设置显示模式
		attachmentObject_${_jsKey}.fdViewType="${JsParam.viewType}";	
	</c:if>	
	<c:if test="${param.drawFunction!=null && param.drawFunction!=''}">
		//设置自定义列表显示函数
		attachmentObject_${_jsKey}.drawFunction=${JsParam.drawFunction};	
	</c:if>	
	<c:if test="${param.fdShowMsg !=null && param.fdShowMsg!=''}">
		//如果fdShowMsg参数为false,则默认不显示附件的信息
		attachmentObject_${_jsKey}.fdShowMsg=${JsParam.fdShowMsg};	
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
	<c:if test="${param.fdImgHtmlProperty!=null && param.fdImgHtmlProperty!=''}">
		attachmentObject_${_jsKey}.fdImgHtmlProperty="${JsParam.fdImgHtmlProperty}";
	</c:if>

	<c:if test="<%=JgWebOffice.isOfficePDFJudge()%>">
		attachmentObject_${_jsKey}.appendPrintFile("pdf");
	</c:if>
	<c:if test='<%=StringUtil.isNotNull(ResourceUtil.getKmssConfigString("sys.att.smallMaxSize"))%>'>
		attachmentObject_${_jsKey}.smallMaxSizeLimit = <%=ResourceUtil.getKmssConfigString("sys.att.smallMaxSize")%>;
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
	<kmss:auth	
		requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=edit&v=true&fdId=${attachmentId}"
				requestMethod="GET">
		attachmentObject_${_jsKey}.canEdit=true;
	</kmss:auth>

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
