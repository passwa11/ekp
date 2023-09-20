<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/kms/common/kms_course_notes/resource/js/eval_js.jsp"%>

<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.evaluation.model.SysEvaluationNotesConfig"%>
<%@page import="com.landray.kmss.kms.common.forms.KmsCourseNotesForm"%>
<link rel="stylesheet" href="${ LUI_ContextPath}/kms/common/kms_course_notes/notes_eval/style/ctEval.css" />

<c:set var="sysEvaluationForm" value="${requestScope[param.formName]}" />
	<script type="text/javascript">
		//Com_IncludeFile("eval.js","${KMSS_Parameter_ContextPath}kms/common/kms_course_notes/resource/js/","js",true);
		//Com_IncludeFile("eval.js","${KMSS_Parameter_ContextPath}sys/evaluation/import/resource/","js",true);
		
	</script>
	<c:set var="eval_modelName" value="${sysEvaluationForm.evaluationForm.fdModelName}"/>
	<c:set var="eval_modelId" value="${sysEvaluationForm.evaluationForm.fdModelId}"/>
	
		<!-- 是否有点评回复权限 -->
		<input type="hidden" name="hasReplyRight" value="true"/> 
		<!-- 是否有删除全文点评回复权限 -->
		<input type="hidden" name="delReplyRight" value="true"/>
		<input type="hidden" name="showReplyInfo" value="true"/>
		
	<script>
	var eval_lang = {
			'eval_star_4':'<bean:message key="sysEvaluation.oneStar.showText" bundle="sys-evaluation" />',
			'eval_star_3':'<bean:message key="sysEvaluation.twoStar.showText" bundle="sys-evaluation" />',
			'eval_star_2':'<bean:message key="sysEvaluation.threeStar.showText" bundle="sys-evaluation" />',
			'eval_star_1':'<bean:message key="sysEvaluation.fourStar.showText" bundle="sys-evaluation" />',
			'eval_star_0':'<bean:message key="sysEvaluation.oneStar.fiveText" bundle="sys-evaluation" />',
			'eval_prompt_1':'<bean:message key="sysEvaluation.pda.alert1" bundle="sys-evaluation"/>',
			'eval_prompt_2':'<bean:message key="sysEvaluation.pda.alert2" bundle="sys-evaluation"/>',
			'eval_prompt_3':'<bean:message key="sysEvaluation.pda.alert3" bundle="sys-evaluation"/>',
			'eval_prompt_sucess_add':'<bean:message key="sysEvaluation.save.msg.success" bundle="sys-evaluation"/>',
			'eval_prompt_sucess_del':'<bean:message key="return.optSuccess"/>',
			'eval_prompt_fail_del':'<bean:message key="return.optFailure"/>',
			'eval_prompt_del_confirm':'<bean:message key="page.comfirmDelete"/>',
			'eval_prompt_btn_cancel':'<bean:message key="button.cancel"/>',
			'eval_prompt_icon_tip':'<bean:message key="sysEvaluation.reply.icon.tip" bundle="sys-evaluation"/>',
			'eval_prompt_icon_smile':'<bean:message key="sysEvaluation.reply.icon.smile" bundle="sys-evaluation"/>',
			'eval_prompt_reply_commment':'<bean:message key="sysEvaluation.reply.commment" bundle="sys-evaluation"/>',
			'eval_prompt_words_alert1':'<bean:message key="sysEvaluationNotes.alert1" bundle="sys-evaluation"/>',
			'eval_prompt_words_alert2':'<bean:message key="sysEvaluationNotes.alert2" bundle="sys-evaluation"/>',
			'eval_prompt_words_alert3':'<bean:message key="sysEvaluationNotes.alert3" bundle="sys-evaluation"/>',
			'eval_prompt_publish_from':'<bean:message key="sysEvaluation.publish.from" bundle="sys-evaluation"/>',
			'eval_prompt_eval_reply':'<bean:message key="sysEvaluation.reply.ct" bundle="sys-evaluation"/>',
			'eval_prompt_eval_delete':'',
			'eval_prompt_read_dialog':'',
			'eval_prompt_read_dialog2':'<bean:message key="sysEvaluation.read.dialog" bundle="sys-evaluation"/>',
			'eval_prompt_reply_tips':'<bean:message key="sysEvaluation.reply.tips" bundle="sys-evaluation"/>',
			'eval_prompt_sucess_evaluate':'<bean:message key="mui.sysEvaluation.success" bundle="sys-evaluation"/>',
			'eval_prompt_eval_reply2':''
		};
		if(window.eval_opt==null)
			window.eval_opt = new EvaluationOpt('${eval_modelName}','${eval_modelId}','${param.key}',eval_lang);
	</script>
	<kmss:auth requestURL="/sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=delete&fdModelName=${eval_modelName}&fdModelId=${eval_modelId}" requestMethod="GET">
		<c:set var="eval_validateAuth" value="true" />
	</kmss:auth>
<c:if test="${sysEvaluationForm.evaluationForm.fdIsShow=='true'}">
		<div class='eval_info'>
			<div id='eval_count'>
			</div>
		</div>		
		<div id="eval_ViewMain">
			<div id="eval_main" >
				<!-- 点赞 -->
				<c:import
					url="/sys/evaluation/import/sysEvaluationMain_view_praise.jsp"
					charEncoding="UTF-8">
					<c:param name="praiseAreaName" value="eval_main" />
					<c:param name="listChannel" value="notes_eval_chl" />
				</c:import>
				<list:listview channel="eval_chl" id="main_view">
						<ui:source type="AjaxJson">
							{url:'/sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=list' +
									'&orderby=fdEvaluationTime&ordertype=down&forward=listUi&fdModelId=${sysEvaluationForm.evaluationForm.fdModelId}' +
									'&fdModelName=${sysEvaluationForm.evaluationForm.fdModelName}&rowsize=3'}
						</ui:source>
						<list:rowTable channel="rowtable" isDefault="true" target="_blank" cfg-norecodeLayout="simple">
							<ui:layout type="Template">
								{$<div class="eval_record" data-lui-mark='table.content.inside'>
								</div>$}
							</ui:layout>
							<list:row-template>
								<c:import
									url="/kms/common/kms_course_notes/notes_eval/notes_eval_view_tmpl.jsp"
									charEncoding="UTF-8">
									<c:param name="isViewMain" value="true" />
									<c:param name="fdModelName" value="com.landray.kmss.sys.evaluation.model.SysEvaluationMain" />
									<c:param name="eval_validateAuth" value="${eval_validateAuth}" />
								</c:import>
							</list:row-template>
						</list:rowTable>	
						<ui:event topic="list.loaded" args="vt">
							
						
						 
							$("#eval_main").css({height:"auto"});
							var showReplyInfo = $("input[name='showReplyInfo']")[0].value;
							if(showReplyInfo!='false'){
								var ids = vt.table.ids;
								if(ids){
									for(var i=0;i<ids.length;i++){
										eval_opt.listReply(ids[i]);
									}
									vt.table.ids = [];
								}
							};
								var notesId = "${eval_modelId}";
								seajs.use(['sys/ui/js/dialog'], function(dialog,LUI) {
									
											$.ajax({
												url : Com_Parameter.ContextPath + "kms/common/kms_notes/kmsCourseNotes.do?method=findDocEvalCount",
												type : 'post',
												cache : false,
												data: {"fdId" :notesId},
												success : function(data) { 
														var models = eval("("+data+")");
														var divobj = document.getElementById("eval_count");
														var evalCount = "${lfn:message("kms-common:kmsCommon.courseNotes.evaluation")}"+"("+models.docEvalCount+")";
														divobj.innerHTML  = evalCount;
													},
												error: function() {
													alert("取值失败");
												}
											});
								
								});
							
						</ui:event>
					</list:listview>	
					<list:paging channel="eval_chl" layout="sys.ui.paging.simple" pageSize="3"></list:paging>
					<ui:event topic="evaluation.submit.success" args="info">
							eval_opt.refreshNum(info);
					</ui:event>
			</div>
		</div>
	<kmss:auth requestURL="/sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=save&fdModelName=${eval_modelName}&fdModelId=${eval_modelId}" requestMethod="GET">
		<div class='eval_txt'>
			<c:import url="/kms/common/kms_course_notes/notes_eval/notes_eval_include.jsp" charEncoding="UTF-8">
						<c:param name="fdModelName" value="${eval_modelName }"></c:param>
						<c:param name="fdModelId" value="${eval_modelId }"></c:param>
			</c:import>
		</div>
	</kmss:auth>
	
	<script>
		eval_opt.onload();
	</script>
</c:if>


