<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
.initialized .lui_upload_img_box{ display: block;}
</style>
<%
	pageContext.setAttribute("__fdModelId", com.landray.kmss.util.IDGenerator.generateID());
	pageContext.setAttribute("__fdUID", System.currentTimeMillis());
	String formBeanName = request.getParameter("formName");
	String attKey = request.getParameter("fdKey");
	Object formBean = null;
	if(formBeanName != null && formBeanName.trim().length()!= 0){
		formBean = pageContext.getAttribute(formBeanName);
		if(formBean == null)
			formBean = request.getAttribute(formBeanName);
		if(formBean == null)
			formBean = session.getAttribute(formBeanName);
	}
	pageContext.setAttribute("_formBean", formBean);
	String auditNoteId = request.getParameter("auditNoteFdId");
	pageContext.setAttribute("_fdKey", auditNoteId+"_sp");
	
%>

<c:set var="attForms" value="${_formBean.attachmentForms[_fdKey]}" />
<c:if test="${attForms!=null && fn:length(attForms.attachments)>0}">
	<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
		<c:param name="formBeanName" value="${param.formName}" />
		<c:param name="fdKey" value="${param.auditNoteFdId}_sp" />
		<c:param name="fdViewType" value="simple" />
	   	<c:param name="fdForceDisabledOpt" value="edit" />
	   	<c:param name="fdModelId" value="${__fdModelId}"></c:param>
	   	<c:param name="fdUID" value="${__fdUID}"></c:param>
	</c:import>
</c:if>