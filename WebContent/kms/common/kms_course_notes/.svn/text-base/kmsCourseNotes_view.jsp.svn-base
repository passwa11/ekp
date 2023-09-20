<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgElement"%>
<%@page import="com.landray.kmss.kms.common.forms.KmsCourseNotesForm"%>

<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/kms/common/kms_course_notes/resource/style/notes_view.css" />
<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/kms/common/kms_course_notes/resource/style/notes_portlet.css" />

<template:include ref="default.view">
	<template:replace name="head">		
	<%@ include file="/kms/common/kms_course_notes/notes_js.jsp"%> 
	
	</template:replace>
	<template:replace name="content">		
		<html:form action="/kms/common/kms_notes/kmsCourseNotes.do">
			<div class="lui_form_content_frame_clearfloat note_view" >
				<div class="edit_notes"  > 
					<div class="km-note-detail-wrap share">
					    <div class="km-note-detail-person">
					      <div class="km-note-detail-avatar-wrap">
							<img src="${KMSS_Parameter_ContextPath}sys/person/sys_person_zone/sysPersonZone.do?method=img&fdId=${kmsCourseNotesForm.docCreatorId}">
					      </div>
					      <div class="km-note-detail-person-msg">
					        <span class="name">${kmsCourseNotesForm.docCreatorName}</span>
					        	<%
									ISysOrgCoreService sysOrgCoreService = 
										(ISysOrgCoreService)SpringBeanUtil.getBean("sysOrgCoreService");
									KmsCourseNotesForm kmsCourseNotesForm = (KmsCourseNotesForm)request.getAttribute("kmsCourseNotesForm");
									String docCreatorId = kmsCourseNotesForm.getDocCreatorId();
									SysOrgElement docCreator =  sysOrgCoreService.findByPrimaryKey(docCreatorId);
									SysOrgElement dept = docCreator.getFdParent();
									if(dept!=null){
										String deptName = dept.getFdName();
										request.setAttribute("deptName",deptName);
									}
								%>
					        <span class="department">${deptName}</span>
					      </div>
					      <div class="km-note-detail-time">${kmsCourseNotesForm.docCreateTime}</div>
					      <div class="notes_praise">
									<div class="btn_praise" style="float: left;">
									<c:import
										url="/sys/praise/sysPraiseMain_view.jsp" charEncoding="UTF-8">
										<c:param name="formName" value="kmsCourseNotesForm" />
										<c:param name="fdModelId" value="${kmsCourseNotesForm.fdId}" />
										<c:param name="fdModelName"
											value="com.landray.kmss.kms.common.model.KmsCourseNotes" />
									</c:import>
									</div>
							</div>
					    </div>
					    <div class="km-note-detail-content">
					      <div class="km-note-detail-body fdNotesContent">		      	
					      <%--<xform:rtf property="fdNotesContent" imgReader="true" ></xform:rtf>--%>
							  <c:out value="${kmsCourseNotesForm.fdNotesContent}"/>
						  </div>
					    </div>
					  </div>
					<div class="notes_evaluation">
						<ui:tabpage expand="false" collapsed="true"> 
						
							<c:import url="/sys/evaluation/import/sysEvaluationMain_view.jsp"
								charEncoding="UTF-8">
								<c:param name="formName" value="kmsCourseNotesForm" />
								<c:param name="showReplyInfo" value="false" />
							</c:import>
						</ui:tabpage>
					</div>
						
				</div>
			</div>
		</html:form>
	</template:replace>
</template:include>