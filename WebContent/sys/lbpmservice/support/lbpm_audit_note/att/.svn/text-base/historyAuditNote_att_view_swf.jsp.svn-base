<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="java.util.List,java.util.ArrayList,com.landray.kmss.util.StringUtil,com.landray.kmss.util.ResourceUtil,com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@page import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%@page import="com.landray.kmss.sys.attachment.dao.ISysAttMainCoreInnerDao"%>

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
<%-- <c:set var="attForms" value="${_formBean.attachmentForms[param.fdKey]}" /> --%>
<%-- <c:set var="sysAttMains" value="${attForms.attachments}" /> --%>
<div id="attachmentObject_${_htmlKey}_content_div">
	<img src="${KMSS_Parameter_ContextPath}sys/attachment/swf/loading.gif" border="0" style="width: 60px;height: 60px;"/>
</div>
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
 
	<c:if test="<%=JgWebOffice.isJGPDFEnabled()%>">
		attachmentObject_${_jsKey}.appendReadFile("pdf");
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
	
	//权限设置
	<kmss:auth
		requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=print&fdId=${attachmentId}"
		requestMethod="GET">
		attachmentObject_${_jsKey}.canPrint=true;
		<c:set var="canPrint" value="1"/>
	</kmss:auth>
	<kmss:auth	
		requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${attachmentId}"
				requestMethod="GET">
		attachmentObject_${_jsKey}.canDownload=true;		
	</kmss:auth>
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
		requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=edit&fdId=${attachmentId}"
				requestMethod="GET">
		attachmentObject_${_jsKey}.canEdit=true;
	</kmss:auth>
	
	//页面加载完成后呈现附件界面
	<c:if test="${empty ____content____ }">
		Com_AddEventListener(window,"load", function() {
			attachmentObject_${_jsKey}.show();
		});
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