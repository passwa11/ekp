<%@page import="com.landray.kmss.common.actions.RequestContext"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@page import="com.landray.kmss.sys.mobile.util.MobileUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.attachment.util.SysAttPicUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<c:set var="_fdKey" value="${param.fdKey}"/>
<c:set var="_fdAttType" value="${param.fdAttType}"/>
<c:set var="_fdItemMixin" value="${param.fdItemMixin}"/>
<c:set var="_fdExtendClass" value="${param.fdExtendClass}"/>

<c:if test="${param.formName!=null && param.formName!=''}">
 	<c:set var="_formBean" value="${requestScope[param.formName]}"/>
 	<c:set var="attForms" value="${_formBean.attachmentForms[_fdKey]}" />
 </c:if>
<c:if test="${attForms!=null && fn:length(attForms.attachments)>0}">
	<div class="muiAuditLog ${_fdExtendClass}">
		<c:if test="${_fdAttType!='pic' }">
			<ul class="muiAuditList">
				<c:forEach var="sysAttMain" items="${attForms.attachments}" varStatus="vsStatus">
					<c:set var="downLoadUrl" value="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${sysAttMain.fdId}" />
					<c:set var="__fdAttId" value="${sysAttMain.fdId}"></c:set>
					<%
                       		String fdAttId = (String)pageContext.getAttribute("__fdAttId");
							String viewPicHref = SysAttPicUtils.getPreviewUrl(request,fdAttId);
							request.setAttribute("viewPicHref", viewPicHref);
                     %>
					
					<li data-dojo-type='sys/lbpmservice/mobile/audit_note_ext/log/AuditLogItem' 
						<c:if test="${ _fdItemMixin!='' }"> data-dojo-mixins='${ _fdItemMixin}' </c:if>
						data-dojo-props='fdId:"${sysAttMain.fdId}",label:"${sysAttMain.fdFileName}",href:"${downLoadUrl}",viewPicHref:"${viewPicHref}"'>
					</li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${_fdAttType=='pic' }">
			<div class="auditHandlerImgBox" data-dojo-type='sys/lbpmservice/mobile/audit_note_ext/log/AuditHandlerImgPreview'
			 	 data-dojo-mixins='mui/rtf/_ImageResizeMixin,sys/lbpmservice/mobile/audit_note_ext/log/_ImageResizeMixin'>
				<c:forEach var="sysAttMain" items="${attForms.attachments}" varStatus="vsStatus">
					<%			
						SysAttMain sysAttMain = (SysAttMain)pageContext.getAttribute("sysAttMain");
						String downLoadUrl = SysAttPicUtils.getPreviewUrl(request, sysAttMain.getFdId());
						boolean fromKKApp = MobileUtil.isFromKKApp(new RequestContext(request));
						if(fromKKApp){
							downLoadUrl = ResourceUtil.getKmssConfigString("kmss.urlPrefix") + downLoadUrl;
						}else{
							downLoadUrl = request.getContextPath() + downLoadUrl;
						}
						request.setAttribute("downLoadUrl", downLoadUrl);
					%>
					 <img auditImgGroup="audit_img_${param.fdKey}" class="muiAuditImg" border="0" width="100" src='${downLoadUrl}'/>
				</c:forEach>
			</div>
			
			<%@ include file="/sys/lbpmservice/mobile/audit_note_ext/log/sysLbpmProcess_log_pc.jsp"%>
			
		</c:if>
	</div>
</c:if>
