<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="sysEvaluationForm" value="${requestScope[param.formName]}" />
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<!-- 点赞 -->
<c:import
	url="/sys/evaluation/import/sysEvaluationMain_view_praise.jsp"
	charEncoding="UTF-8">
	<c:param name="praiseAreaName" value="main_view" />
	<c:param name="listChannel" value="eval_chl" />
</c:import>

<c:import
	url="/sys/evaluation/import/sysEvaluationMain_view_praise.jsp"
	charEncoding="UTF-8">
	<c:param name="praiseAreaName" value="my_view" />
	<c:param name="listChannel" value="eval_my" />
</c:import>

<div id="eval_my_title" class="lui_evaluation_title"></div>
<list:listview channel="eval_my" id="my_view" cfg-needMinHeight="false">
	<ui:source type="AjaxJson">
		{url:'/sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=listMyEva' +
				'&orderby=fdEvaluationTime&ordertype=down&forward=listUi&fdModelId=${sysEvaluationForm.evaluationForm.fdModelId}' +
				'&fdModelName=${sysEvaluationForm.evaluationForm.fdModelName}&myDoc=true&type=top'}
	</ui:source>
	<list:rowTable channel="rowtable" isDefault="true" target="_blank" cfg-norecodeLayout="none">
		<ui:layout type="Template">
			{$<div class="eval_record" data-lui-mark='table.content.inside'>
			</div>$}
		</ui:layout>
		<list:row-template>
			<c:import
				url="/sys/evaluation/import/sysEvaluationMain_view_tmpl.jsp"
				charEncoding="UTF-8">
				<c:param name="isViewMain" value="true" />
				<c:param name="fdModelName" value="com.landray.kmss.sys.evaluation.model.SysEvaluationMain" />
				<c:param name="eval_validateAuth" value="${param.eval_validateAuth=='true'}" />
			</c:import>
		</list:row-template>
	</list:rowTable>	
	<ui:event topic="list.loaded" args="vt"> 
		var ids = vt.table.ids;
		var showReplyInfo = $("input[name='showReplyInfo']")[0].value;
		if(ids){
			eval_opt.superaddition(ids, vt.table.element, ${param.eval_validateAuth=='true'});
			eval_opt.listMainAtt(ids, "data-eval-main-att")
			if(showReplyInfo!='false'){
				for(var i=0;i<ids.length;i++){
					eval_opt.listReply(ids[i]);
				} 
			}
			vt.table.ids = [];
		}
		
		if(vt.listview._data.datas && vt.listview._data.datas.length > 0) {
			$("#eval_my_title").text("${lfn:message('sys-evaluation:sysEvaluationMain.myEva')}");
			$("#eval_chl_title").text("${lfn:message('sys-evaluation:sysEvaluationMain.other')}");
			$(".lui_evaluation_title").show();
		} else {
			$("#eval_my_title").text("");
			$("#eval_chl_title").text("");
			$(".lui_evaluation_title").hide();
		}
	</ui:event> 
</list:listview>


<div id="eval_chl_title" class="lui_evaluation_title"></div>
<list:listview channel="eval_chl" id="main_view">
	<ui:source type="AjaxJson">
		{url:'/sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=list' +
				'&orderby=fdEvaluationTime&ordertype=down&forward=listUi&fdModelId=${sysEvaluationForm.evaluationForm.fdModelId}' +
				'&type=top&fdModelName=${sysEvaluationForm.evaluationForm.fdModelName}&rowsize=${param.rowsize}&myDoc=false'}
	</ui:source>
	<list:rowTable channel="rowtable" isDefault="true" target="_blank" cfg-norecodeLayout="simple">
		<ui:layout type="Template">
			{$<div class="eval_record" data-lui-mark='table.content.inside'>
			</div>$}
		</ui:layout>
		<list:row-template>
			<c:import
				url="/sys/evaluation/import/sysEvaluationMain_view_tmpl.jsp"
				charEncoding="UTF-8">
				<c:param name="isViewMain" value="true" />
				<c:param name="fdModelName" value="com.landray.kmss.sys.evaluation.model.SysEvaluationMain" />
				<c:param name="eval_validateAuth" value="${param.eval_validateAuth=='true'}" />
			</c:import>
		</list:row-template>
	</list:rowTable>	
	<ui:event topic="list.loaded" args="vt"> 
		$("#eval_main").css({height:"auto"});
		var showReplyInfo = $("input[name='showReplyInfo']")[0].value;
		var ids = vt.table.ids;
		if(ids){
			eval_opt.listMainAtt(ids, "data-eval-main-att");
			eval_opt.superaddition(ids, vt.table.element, ${param.eval_validateAuth=='true'});
			if(showReplyInfo!='false'){
				for(var i=0;i<ids.length;i++){
					eval_opt.listReply(ids[i]);
				}
			}
			vt.table.ids = [];
		}
	</ui:event>
</list:listview>
<% 
	Object obj = (Object)pageContext.getAttribute("sysEvaluationForm");
	if(((PropertyUtils.isReadable(obj, "docAuthorName"))||(PropertyUtils.isReadable(obj, "docAlterorName")))||PropertyUtils.isReadable(obj, "docAlterorName")){
%>
<div id="lui_evalNote_hidden" style="display: none">
	<div style="width:600px;height:400px;padding-bottom:10px">
		<div class="lui_eval_eva_title"><bean:message key="sysEvaluationNotes.docSubject" bundle="sys-evaluation" />
		</div>
		<div class="lui_form_summary_frame lui_eval_eva_object" id="lui_evalNote_subject">
			<p>subject</p>
		</div>
		<div class="lui_eval_eva_title "><bean:message key="sysEvaluationNotes.fdEvaluationContent" bundle="sys-evaluation" />
		</div>
		<div class="lui_eval_eva_content">
			<textarea onkeyup="checkWordsCount(this)" id="eval_eva_content" style="width:98.5%"></textarea>
		</div>
		<span class="eval_reply_tip"><bean:message key="sysEvaluationNotes.alert1" bundle="sys-evaluation" />
			<font style="font-family: Constantia, Georgia; font-size: 20px;">300
			</font><bean:message key="sysEvaluationNotes.alert3" bundle="sys-evaluation" />
		</span>
		<div style="clear:both">
		</div>
		
		<div class="lui_eval_eva_content">
			<bean:message key="sysEvaluation.evalNotifyTarget" bundle="sys-evaluation" />
			<% 
				if(PropertyUtils.isReadable(obj, "docAuthorName")){
			%>
			<c:if test="${sysEvaluationForm.docAuthorName!='' && sysEvaluationForm.docAuthorName!= null}">
				<label class="eval_notify eval_summary_color">
					<input name="isNotify1" type="checkbox" value="docAuthor"
						onclick="var isNotify1 = document.getElementsByName('isNotify1');if(isNotify1[0].checked){isNotify1[0].value='docAuthor'}else{isNotify1[0].value=''}">
					<bean:message key="sysEvaluation.evalDocAuthorName" bundle="sys-evaluation" />
				</label>
			</c:if>
			&nbsp;&nbsp;
			<% }%>
			<c:if test="${sysEvaluationForm.docCreatorName!='' && sysEvaluationForm.docCreatorName!= null}">
				<label class="eval_notify eval_summary_color2">
					<input name="isNotify2" type="checkbox" value="docCreator"
						onclick="var isNotify2 = document.getElementsByName('isNotify2');if(isNotify2[0].checked){isNotify2[0].value='docCreator'}else{isNotify2[0].value=''}">
					<bean:message key="sysEvaluation.evalDocCreatorName" bundle="sys-evaluation" />
				</label>
			</c:if>
			<% 
				if(PropertyUtils.isReadable(obj, "docAlterorName")){
			%>
			<c:if test="${sysEvaluationForm.docAlterorName!='' && sysEvaluationForm.docAlterorName!= null}">
			&nbsp;&nbsp;
				<label class="eval_notify eval_summary_color3">
					<input  name="isNotify3" type="checkbox" value="docAlteror"
						onclick="var isNotify3 = document.getElementsByName('isNotify3');if(isNotify3[0].checked){isNotify3[0].value='docAlteror'}else{isNotify3[0].value=''}">
					<bean:message key="sysEvaluation.evalDocAlterorName" bundle="sys-evaluation" />
				</label>
			</c:if>
			<% }%>
			<br>
			<label class="eval_notify eval_summary_color">
				<bean:message key="sysNotifySetting.fdNotifyType" bundle="sys-notify" />：<kmss:editNotifyType property="fdNotifyType" />
			</label>
			<span class="eval_prompt"></span>
		</div>
	</div>
</div>	
<% }%>
<list:paging channel="eval_chl" layout="sys.ui.paging.simple"></list:paging>