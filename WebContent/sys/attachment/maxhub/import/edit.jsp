<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@ page import="com.landray.kmss.sys.metadata.interfaces.IExtendDataForm"%>
<%@ page import="com.landray.kmss.sys.attachment.util.SysAttConstant"%>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/attachment/maxhub/css/edit.css?s_cache=${MUI_Cache}"></link>

<c:set var="_fdKey" value="${param.fdKey}"/>
<c:if test="${param.fdInDetail=='true'}">
	<%
		IExtendDataForm form = (IExtendDataForm)request.getAttribute(request.getParameter("formName"));
		String key= request.getParameter("fdFiledName");
		key = key.replace("extendDataFormInfo.value(","").replace(")","");
		String[] keys = key.split("\\.");
		if("!{index}".equals(keys[1])){
			pageContext.setAttribute("_fdKey", "");
		}else{
		   	Map<String,Object> map = form.getExtendDataFormInfo().getFormData();
		    List list = (List)map.get(keys[0]);
		    if(!list.isEmpty()){
			    Map<String,Object> mapDetail=(Map<String,Object>)list.get(Integer.parseInt(keys[1]));
			    pageContext.setAttribute("_fdKey", mapDetail.get(keys[2]));
		    }else{
		    	 pageContext.setAttribute("_fdKey", "");
		    }
		}
	%>
</c:if>

<c:if test="${param.formName!=null && param.formName!=''}">
 	<c:set var="_formBean" value="${requestScope[param.formName]}"/>
 	<c:set var="attForms" value="${_formBean.attachmentForms[_fdKey]}" />
 </c:if>
 
 <c:set var="_fdModelName" value="${param.fdModelName}"/>
 <c:if test="${_fdModelName==null || _fdModelName == ''}">
 	<c:if test="${_formBean!=null}">
 		<c:set var="_fdModelName" value="${_formBean.modelClass.name}"/>
 	</c:if>
 </c:if>
 
 <c:set var="_fdModelId" value="${param.fdModelId}"/>
 <c:if test="${_fdModelId==null || _fdModelId == ''}">
 	<c:if test="${_formBean!=null}">
 		<c:set var="_fdModelId" value="${_formBean.fdId}"/>
 	</c:if>
 </c:if>

<c:set var="_fdMulti" value="true"/>
<c:if test="${param.fdMulti!=null}">
	<c:set var="_fdMulti" value="${param.fdMulti}"/>
</c:if>

<%-- fdAttType: byte/pic--%>
<c:set var="_fdAttType" value="byte"/>
<c:if test="${param.fdAttType!=null}">
	<c:set var="_fdAttType" value="${param.fdAttType}"/>
</c:if>

<c:set var="_fdRequired" value="false"></c:set>
<c:if test="${param.fdRequired==true || param.fdRequired=='true'}">
	<c:set var="_fdRequired" value="true"/>
</c:if>

<%-- fdViewType: normal/simple--%>
<c:set var="_fdViewType" value="normal"></c:set>
<c:if test="${param.fdViewType!=null}">
	<c:set var="_fdViewType" value="${param.fdViewType}"/>
</c:if>

<c:set var="_extParam" value=""></c:set>
<c:if test="${param.extParam!=null}">
	<c:set var="_extParam" value="${param.extParam}"/>
</c:if>

<c:set var="_fdName" value=""></c:set>
<c:if test="${param.fdName!=null}">
	<c:set var="_fdName" value="${param.fdName}"/>
</c:if>

<c:set var="_subject" value="${ lfn:message('sys-attachment:mui.sysAttMain.button.upload') }"></c:set>
<c:if test="${param.fdAttType!=null&&param.fdAttType=='pic'}">
	<c:set var="_subject" value="${ lfn:message('sys-attachment:sysAttMain.button.img.upload') }"/>
</c:if>
<c:if test="${param.label!=null}">
	<c:set var="_subject" value="${param.label}"/>
</c:if>
<c:set var="_orient" value="none"></c:set>
<c:if test="${param.orient!=null}">
	<c:set var="_orient" value="${param.orient}"/>
</c:if>

<c:set var="_customSubject" value=""></c:set>
<c:if test="${param.customSubject!=null}">
	<c:set var="_customSubject" value="${param.customSubject}"/>
</c:if>

<c:set var="_customWarnTipMsg" value=""></c:set>
<c:if test="${param.customWarnTipMsg!=null}">
	<c:set var="_customWarnTipMsg" value="${param.customWarnTipMsg}"/>
</c:if>

<!-- 附件类型控制 -->
<c:set var="_enabledFileType" value=""></c:set>
<c:if test="${param.enabledFileType!=null}">
	<c:set var="_enabledFileType" value="${param.enabledFileType}"/>
</c:if>

<c:set var="_fdDisabledFileType" value=""/>
<% 
	if(StringUtil.isNotNull(ResourceUtil.getKmssConfigString("sys.att.fileLimitType"))){
		pageContext.setAttribute("_fileLimitType",ResourceUtil.getKmssConfigString("sys.att.fileLimitType"));
		pageContext.setAttribute("_fdDisabledFileType",ResourceUtil.getKmssConfigString("sys.att.disabledFileType"));
	}else{
		pageContext.setAttribute("_fileLimitType","1");
		pageContext.setAttribute("_fdDisabledFileType",SysAttConstant.DISABLED_FILE_TYPE);
	}
    if(StringUtil.isNotNull(ResourceUtil.getKmssConfigString("sys.att.smallMaxSize"))){
		pageContext.setAttribute("_fdSmallMaxSize",ResourceUtil.getKmssConfigString("sys.att.smallMaxSize"));
	}  
	if(StringUtil.isNotNull(ResourceUtil.getKmssConfigString("sys.att.imageMaxSize"))){
		pageContext.setAttribute("_fdImageMaxSize",ResourceUtil.getKmssConfigString("sys.att.imageMaxSize"));
    }
%>
<%
	JSONObject jsonObj = new JSONObject();
	jsonObj.accumulate("fdKey",pageContext.getAttribute("_fdKey"));
	jsonObj.accumulate("name",pageContext.getAttribute("_fdName"));
	jsonObj.accumulate("fdFiledName",request.getParameter("fdFiledName"));
	jsonObj.accumulate("fdModelName",pageContext.getAttribute("_fdModelName"));
	jsonObj.accumulate("fdModelId",pageContext.getAttribute("_fdModelId"));
	jsonObj.accumulate("viewType",pageContext.getAttribute("_fdViewType"));
	jsonObj.accumulate("required","true".equals(pageContext.getAttribute("_fdRequired")));
	jsonObj.accumulate("editMode","edit");
	jsonObj.accumulate("extParam",pageContext.getAttribute("_extParam"));
	jsonObj.accumulate("fdAttType",pageContext.getAttribute("_fdAttType"));
	jsonObj.accumulate("fileLimitType",pageContext.getAttribute("_fileLimitType"));	
	jsonObj.accumulate("fdDisabledFileType",pageContext.getAttribute("_fdDisabledFileType"));
	jsonObj.accumulate("fdSmallMaxSize",pageContext.getAttribute("_fdSmallMaxSize"));
	jsonObj.accumulate("fdImageMaxSize",pageContext.getAttribute("_fdImageMaxSize"));
	jsonObj.accumulate("fdMulti","true".equals(pageContext.getAttribute("_fdMulti")));
	jsonObj.accumulate("subject",pageContext.getAttribute("_subject"));
	jsonObj.accumulate("orient",pageContext.getAttribute("_orient"));
	jsonObj.accumulate("customWarnTipMsg",pageContext.getAttribute("_customWarnTipMsg"));
	jsonObj.accumulate("enabledFileType",pageContext.getAttribute("_enabledFileType"));
	String jsonProp = jsonObj.toString();
	pageContext.setAttribute("jsonProp",StringUtil.XMLEscape(jsonProp.substring(1,jsonProp.length()-1)));
	pageContext.setAttribute("required","true".equals(pageContext.getAttribute("_fdRequired")));
	pageContext.setAttribute("_fdAttType",pageContext.getAttribute("_fdAttType"));
%>

<div id="attachmentObject_${_fdKey}" 
	data-dojo-type="sys/attachment/maxhub/js/AttachmentList" <c:if test="${not empty param.widgitId }">id="${param.widgitId}"</c:if>
	data-dojo-props="${ jsonProp }" <c:if test="${not empty param.hidden && param.hidden eq true }">style="display:none;"</c:if> >
	<c:forEach var="sysAttMain" items="${attForms.attachments}" varStatus="vsStatus">
		<c:set var="downLoadUrl" value="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${sysAttMain.fdId}" />
		<c:if test="${_downLoadNoRight==true || _downLoadNoRight=='true'}">
			<c:set var="downLoadUrl" value="/third/pda/attdownload.jsp?fdId=${sysAttMain.fdId}" />
		</c:if>
		<%
			SysAttMain sysAtt = (SysAttMain) pageContext.getAttribute("sysAttMain");
			String escapeFileName = StringEscapeUtils.escapeJavaScript(sysAtt.getFdFileName());
			pageContext.setAttribute("escapeFileName", escapeFileName);
		%>
		<div data-dojo-type="sys/attachment/maxhub/js/AttachmentEditListItem" 
			data-dojo-props="name:'${escapeFileName}',
				size:'${sysAttMain.fdSize}',
				href:'${downLoadUrl}',
				thumb:'/sys/attachment/sys_att_main/sysAttMain.do?method=view&picthumb=small&fdId=${sysAttMain.fdId}',
				fdId:'${sysAttMain.fdId}',
				type:'${sysAttMain.fdContentType}'">
		</div>
	</c:forEach>
	<div data-dojo-type="sys/attachment/maxhub/js/AttachmentOptListItem" 
		data-dojo-props="_fdAttType:'${_fdAttType}',customSubject:'${_customSubject }'"
		<c:if test="${not empty param.hiddenOpt && param.hiddenOpt eq true }">style="display:none;"</c:if> >
	</div>
	<div data-dojo-type="sys/attachment/maxhub/js/AttachmentEvent"></div>
	<!-- 兼容form.js -->
	<c:if test="${param.fdInDetail=='true'}">
		<xform:text showStatus="noShow"  property="${param.fdFiledName}" value="${_fdKey}"/>
	</c:if>
</div>

