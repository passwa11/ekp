<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/kms/common/kms_course_notes/notes_js.jsp"%>
	<link rel="stylesheet" href="${ LUI_ContextPath}/kms/common/kms_course_notes/resource/style/notes.css" />
	<link rel="stylesheet" href="${ LUI_ContextPath}/kms/common/kms_course_notes/resource/style/notes_portlet.css" />

<c:set var="CourseNotesForm" value="${requestScope[param.formName]}" />
<c:set var="fdModelName" value="${ param.fdModelName}" />
<c:set var="fdModelId" value="${ param.fdModelId}" />
	<style>
		#my_notes_list .listview {
		    background-color: #ffffff !important;
		}
		#my_notes_list .lui_paging_box {
		    background-color: #ffffff !important;
		}
		#share_notes_list .listview {
		    background-color: #ffffff !important;
		}
		#share_notes_list .lui_paging_box {
		    background-color: #ffffff !important;
		}
	</style>

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
			'notes_prompt_fail_del':'${lfn:message("kms-common:notes_prompt_fail_del")}',

			'notes_prompt_eval_confirm':'${lfn:message("kms-common:notes_prompt_eval_confirm")}',
			'notes_prompt_success_eval':'${lfn:message("kms-common:notes_prompt_success_eval")}',
			'notes_prompt_eval_fail':'${lfn:message("kms-common:notes_prompt_eval_fail")}',

			'eval_prompt_reply_tips':'<bean:message key="sysEvaluation.reply.tips" bundle="sys-evaluation"/>',
			'eval_prompt_eval_reply':'<bean:message key="sysEvaluation.reply.ct" bundle="sys-evaluation"/>',
			'eval_prompt_read_dialog':'',
			'eval_prompt_eval_delete':'',
			'eval_prompt_eval_reply2':''
			
				
			
			
				
				
								
	};
	//Com_IncludeFile("notes.js","${KMSS_Parameter_ContextPath}/kms/common/kms_course_notes/resource/","js",true);
	Com_IncludeFile("kms_utils.js", Com_Parameter.ContextPath+"kms/common/resource/js/","js",true);
	</script>
	<c:set var="notes_addUrl"
		value="/kms/common/kms_notes/kmsCourseNotes.do?method=add&fdModelName=${notes_modelName}&fdModelId=${notes_modelId}" />

	<kmss:auth requestURL="/kms/common/kms_notes/kmsCourseNotes.do?method=save" requestMethod="GET">
		<c:set var="notes_addvalidateAuth" value="true" />
	</kmss:auth>
	<kmss:auth requestURL="/kms/common/kms_notes/kmsCourseNotes.do?method=edit" requestMethod="GET">
		<c:set var="notes_editvalidateAuth" value="true" />
	</kmss:auth>
	<kmss:auth requestURL="/kms/common/kms_notes/kmsCourseNotes.do?method=delete" requestMethod="GET">
		<c:set var="notes_deletevalidateAuth" value="true" />
	</kmss:auth>
	<div  class='notes_label_title'>
				<kmss:auth requestURL="/kms/common/kms_notes/kmsCourseNotes.do?method=save" requestMethod="GET">
				<c:import url="/kms/common/kms_course_notes/course_notes_include.jsp" charEncoding="UTF-8">
					<c:param name="fdModelName" value="${fdModelName }"></c:param>
					<c:param name="fdModelId" value="${fdModelId }"></c:param>
				</c:import>
				</kmss:auth>
			
	
			<div id="notes_ViewMain" class="km-note-share-list-wrap">
				<div class="km-note-share-list-heading">
			        <input type="button" class="actived" id="myNotes" value="${lfn:message('kms-common:kmsCommon.myNotes')}" onmouseover="btnColor_chg(this)" onmouseout="nobtnColor_chg(this)" onclick="notes_change('myNotes')"></input>
			        <input type="button"  class="" id="shareNotes" value="${lfn:message('kms-common:kmsCommon.shareNotes')}" onmouseover="btnColor_chg(this)" onmouseout="nobtnColor_chg(this)" onclick="notes_change('shareNotes')"></input>
      			</div>
			</div>
		
		<div id='my_notes_list'>
				<list:listview channel="notes_chl_1" id="main_view_1">
					<ui:source type="AjaxJson">
						{url:'/kms/common/kms_notes/kmsCourseNotes.do?method=list' +
								'&orderby=docCreateTime desc&forward=listUi&fdModelId=${fdModelId }' +
								'&fdModelName=${fdModelName }&rowsize=5&listType=my_notes'}
					</ui:source>
					<list:rowTable channel="rowtable" isDefault="true" target="_blank" cfg-norecodeLayout="simple">
						<ui:layout type="Template">
							{$<div class="notes_record" data-lui-mark='table.content.inside'>
							</div>$}
						</ui:layout>
						<list:row-template>
							<c:import
								url="/kms/common/kms_course_notes/course_my_notes_tmpl.jsp"
								charEncoding="UTF-8">
								<c:param name="notes_addvalidateAuth" value="${notes_addvalidateAuth}" />
								<c:param name="notes_editvalidateAuth" value="${notes_editvalidateAuth}" />
								<c:param name="notes_deletevalidateAuth" value="${notes_deletevalidateAuth}" />
							</c:import>
						</list:row-template>
					</list:rowTable>	
							<ui:event topic="list.loaded" args="vt">
								var color = $("#notes_button").css('background-color');
								$(".button_notes div").css("color",color)
								$(".notes_cancel_share").css("color",color)
								
								var text=$(".notes_record_content");
								for(var i=0;i<text.length;i++){
									var text1=$(text[i]).text().trim();
									var n=Math.floor(($(".notes_record_content").width())/10);
									if(text1.length>(n*3-1)){
										text1=text1.substring(0,n*3-1)+".."; 
									}
									$(text[i]).html(text1);
								}
								
							</ui:event>
				</list:listview>	
				<list:paging channel="notes_chl_1" layout="sys.ui.paging.simple"></list:paging>
		</div>
		
		<div id='share_notes_list' >
				<list:listview channel="notes_chl_2" id="main_view_2">
					<ui:source type="AjaxJson">
						{url:'/kms/common/kms_notes/kmsCourseNotes.do?method=list' +
								'&orderby=docCreateTime desc&forward=listUi&fdModelId=${fdModelId }' +
								'&fdModelName=${fdModelName }&rowsize=5&listType=share_notes'}
					</ui:source>
					<list:rowTable channel="rowtable" isDefault="true" target="_blank" cfg-norecodeLayout="simple">
						<ui:layout type="Template">
							{$<div class="notes_record" data-lui-mark='table.content.inside'>
							</div>$}
						</ui:layout>
						<list:row-template>
							<c:import
								url="/kms/common/kms_course_notes/course_share_notes_tmpl.jsp"
								charEncoding="UTF-8">
							</c:import>
						</list:row-template>
					</list:rowTable>	
					<ui:event topic="list.loaded" args="vt">
								var color = $("#notes_button").css('background-color');
								$(".view_notes").css("color",color);
								
								$(".notes_recorder .com_author").css("color",color);
								
								var text=$(".notes_record_content");
								for(var i=0;i<text.length;i++){
									var text1=$(text[i]).text().trim();
									var n=Math.floor(($(".notes_record_content").width())/10);
									if(text1.length>(n*3-1)){
										text1=text1.substring(0,n*3-1)+".."; 
									}
									$(text[i]).html(text1);
								}
					</ui:event>
				</list:listview>	
				<list:paging channel="notes_chl_2" layout="sys.ui.paging.simple" ></list:paging>
		</div>
	
	</div>
	

	

	


