<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDictModel"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDataDict"%>
<%@page
	import="com.landray.kmss.kms.common.forms.KmsCourseNotesForm"%>
<%@page import="com.landray.kmss.common.service.IBaseService"%>
<%@page import="com.landray.kmss.common.model.IBaseModel"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDictCommonProperty"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="java.util.Map"%>
<%@page
	import="com.landray.kmss.util.ResourceUtil"%>
	
<%
	String[] fields = { "fdName", "fdSubject","docSubject" };
	KmsCourseNotesForm form = (KmsCourseNotesForm)request.getAttribute("kmsCourseNotesForm");
	String fdModelName = form.getFdModelName();
	String fdModelId = form.getFdModelId();
	SysDictModel docModel = SysDataDict.getInstance().getModel(fdModelName);
	if(docModel!=null){
		IBaseService docService = (IBaseService) SpringBeanUtil.getBean(docModel.getServiceBean());
		
		IBaseModel baseModel = docService.findByPrimaryKey(fdModelId,null,true);
		if(baseModel !=null){
			Map<String, SysDictCommonProperty> propertyMap = docModel.getPropertyMap();
			for (String field : fields) {
				if (propertyMap.get(field) != null) {
					String docSubject=(String) PropertyUtils.getSimpleProperty(baseModel, field);
					request.setAttribute("docSubject",docSubject);
				}
			}
		}else{
			String docSubject = "<font style='color:red;'>"+ResourceUtil.getString("kms-common:kmsCourseNotes.deleteSourse")+"</font>";
			request.setAttribute("docSubject",docSubject);
		}

	}

	
%>
<script type="text/javascript">
	var notes_lang = {
			'notes_prompt_cal_confirm':'${lfn:message("kms-common:notes_prompt_cal_confirm")}',
			'notes_prompt_success_cal':'${lfn:message("kms-common:notes_prompt_sucess_cal")}',
			'notes_prompt_fail_cal':'${lfn:message("kms-common:notes_prompt_fail_cal")}',
			'notes_prompt_success_add':'${lfn:message("kms-common:notes_prompt_sucess_add")}',
			
			'notes_prompt_upt_confirm':'${lfn:message("kms-common:notes_prompt_upt_confirm")}',
			'notes_prompt_success_upt':'${lfn:message("kms-common:notes_prompt_success_upt")}',
			'notes_prompt_fail_upt':'${lfn:message("kms-common:notes_prompt_fail_upt")}',
			'notes_prompt_del_confirm':'${lfn:message("kms-common:notes_prompt_del_confirm")}',
			'notes_prompt_success_del':'${lfn:message("kms-common:notes_prompt_success_del")}',
			'notes_prompt_del_success':'${lfn:message("kms-common:notes_prompt_del_success")}',
			'notes_prompt_fail_del':'${lfn:message("kms-common:notes_prompt_fail_del")}'	
	};
	//Com_IncludeFile("notes.js","${KMSS_Parameter_ContextPath}/kms/common/kms_course_notes/resource/","js",true);
	</script>
<template:include ref="default.view">
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/kms/common/kms_course_notes/resource/style/notes_portlet.css" />
		<%@ include file="/kms/common/kms_course_notes/notes_porlet/js/notes_porlet_js.jsp"%>
		
	</template:replace>
	<template:replace name="title">
		<c:out value="${ lfn:message('kms-common:kmsCommon.myNotes') }"></c:out>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="4">
			<!-- 编辑 -->
			<kmss:auth requestURL="/kms/common/kms_notes/kmsCourseNotes.do?method=edit" requestMethod="GET">
				<ui:button text="${lfn:message('button.edit')}" onclick="edit_notes('${kmsCourseNotesForm.fdId}')">
				</ui:button>
			</kmss:auth> 
	
			<kmss:auth requestURL="/kms/common/kms_notes/kmsCourseNotes.do?method=delete" requestMethod="GET">
				<ui:button text="${lfn:message('button.delete')}" 
						onclick="notes_delete('${kmsCourseNotesForm.fdId}')">
				</ui:button>
			</kmss:auth>
			<!-- 关闭 -->
			<ui:button text="${lfn:message('button.close')}" onclick="Com_CloseWindow(); " order="5">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	
	<template:replace name="content"> 
		<c:set
			var="kmsCourseNotesForm"
			value="${kmsCourseNotesForm}"
			scope="request" />
		
		
		<!-- 文档内容 -->
		<div class="km-note-detail-wrap">
		    <div class="km-note-detail-person">
		      <div class="km-note-detail-avatar-wrap">
					<img src="${KMSS_Parameter_ContextPath}sys/person/sys_person_zone/sysPersonZone.do?method=img&fdId=${kmsCourseNotesForm.docCreatorId}">
		      </div>
		      <div class="km-note-detail-person-msg">
		        <span class="name">${kmsCourseNotesForm.docCreatorName}</span>
		      </div>
		      <div class="km-note-detail-time">${kmsCourseNotesForm.docCreateTime}</div>
		    </div>
		    <div class="km-note-detail-content">
		      <div class="km-note-detail-heading">
		        <span class="tag"></span>
		        <span class="gray"><bean:message bundle="kms-common" key="kmsCourseNotes.sourse" />:</span>
		        <span class="bold">${docSubject}</span>
		      </div>
		      <div class="km-note-detail-body fdNotesContent">							
		      	<%--<xform:rtf property="fdNotesContent" imgReader="true" ></xform:rtf>--%>
				<%--<textarea style="width: 100%;color: #333333;height: 250px;white-space: pre-line;">
						${fn:escapeXml(kmsCourseNotesForm.fdNotesContent)}
				</textarea>--%>
				<c:out value="${kmsCourseNotesForm.fdNotesContent}"/>
			  </div>
		      <div class="km-note-list-footer" style="min-width: 150px;">
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
		        <a href="javascript:void(0);" class="km-note-list-comment">
		          <span class="icon" title="${lfn:message('kms-common:table.kmsCommentMain')}"></span>
		          <span class="num">${not empty kmsCourseNotesForm.evaluationForm.fdEvaluateCount?kmsCourseNotesForm.evaluationForm.fdEvaluateCount : '(0)'}</span>
		        </a>
		      </div>
		    </div>
		  </div>
		<ui:tabpage>
			<!-- 点评 -->
			<c:import url="/sys/evaluation/import/sysEvaluationMain_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmsCourseNotesForm" />
			</c:import>
			
		</ui:tabpage>
		<style>
		.listview {
		    background-color: #ffffff !important;
		}
		</style>
	</template:replace>
	
</template:include>
