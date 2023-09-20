<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsWebOfficeUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%
	 pageContext.setAttribute("_isJGEnabled", new Boolean(
			com.landray.kmss.sys.attachment.util.JgWebOffice
					.isJGEnabled()));
     pageContext.setAttribute("_isWpsWebOfficeEnable", new Boolean(SysAttWpsWebOfficeUtil.isEnable()));
%>

<template:replace name="content"> 
	<script>
	seajs.use(['lui/jquery','lui/dialog'], function($, dialog){
		<c:if test="${kmReviewMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.yqqSign =='true' && yqqFlag=='true'  && kmReviewMainForm.fdSignEnable=='true'}">
		 Com_Parameter.event["submit"].push(function(){
			//操作类型为通过类型 ，才判断是否已经签署
			if(lbpm.globals.getCurrentOperation().operation && lbpm.globals.getCurrentOperation().operation['isPassType'] == true){
				 var flag = true;
				 var url = Com_Parameter.ContextPath+"km/review/km_review_main/kmReviewOutSign.do?method=queryFinish&signId=${param.fdId}";
				 $.ajax({
						url : url,
						type : 'post',
						data : {},
						dataType : 'text',
						async : false,     
						error : function(){
							dialog.alert('请求出错');
						} ,   
						success:function(data){
							if(data == "true"){
								flag = true;
							}else{
								dialog.alert("当前签署任务未完成，无法提交！！");
								flag = false;
							}
						}
					});
				 return flag;
			}else{
				return true;
			}
			
		 });
	</c:if>
	});
	</script>
	<c:import url="/sys/recycle/import/redirect.jsp">
		<c:param name="formBeanName" value="kmReviewMainForm"></c:param>
	</c:import>
	<c:if test="${kmReviewMainForm.method_GET=='add'}">
		<script type="text/javascript">
			Com_IncludeFile("calendar.js");
			
			window.changeDocTemp = function(modelName,url,canClose){
				if(modelName==null || modelName=='' || url==null || url=='')
					return;
		 		seajs.use(['sys/ui/js/dialog'],function(dialog) {
		 			// 创建文档只需要模板ID即可，额外的参数反而会引起其它问题
		 			//业务活动需要其它参数：#109818
		 			if(url.indexOf('com.landray.kmss.km.bam2.model.KmBam2Project') == -1)
		 			{
		 				url = '/km/review/km_review_main/kmReviewMain.do?method=add&fdTemplateId=!{id}';
		 			}
				 	dialog.categoryForNewFile(modelName,url,false,null,function(rtn) {
						// 无分类状态下（一般于门户快捷操作）创建文档，取消操作同时关闭当前窗口
						if (!rtn){
							window.opener = null;
							window.open("", "_self");
							window.close();
						}
					},'${JsParam.categoryId}','_self',canClose);
			 	});
		 	};
		 	
			if('${JsParam.fdTemplateId}'==''){
				//window.changeDocTemp('com.landray.kmss.km.review.model.KmReviewTemplate','/km/review/km_review_main/kmReviewMain.do?method=add&fdTemplateId=!{id}',true);
				window.changeDocTemp('com.landray.kmss.km.review.model.KmReviewTemplate','/km/review/km_review_main/kmReviewMain.do?method=add&fdTemplateId=!{id}&fdTemplateName=!{name}&fdWorkId=${JsParam.fdWorkId}&fdPhaseId=${JsParam.fdPhaseId}&fdModelId=${JsParam.fdModelId}&fdModelName=${JsParam.fdModelName}',true);
			}
		</script>
	</c:if>
	<div class="lui_review_type">
		<div class="lui_review_type_btn_wrap">
			<span class="lui_review_type_btn is-active" show-content="review_content">
				${ lfn:message('km-review:kmReviewMain.faqishenpi') }
			 </span>
			<span class="lui_review_type_btn" show-content="view_data">
				${ lfn:message('km-review:kmReviewMain.chakanshuju') }
			 </span>
		</div>
	</div>
	<c:if test="${param.approveModel ne 'right'}">
		<form name="kmReviewMainForm" method="post" action ="${KMSS_Parameter_ContextPath}km/review/km_review_main/kmReviewMain.do">
	</c:if>	
	<html:hidden property="fdId" value="${kmReviewMainForm.fdId}"/>
	<html:hidden property="fdUseWord" value="${kmReviewMainForm.fdUseWord}"/>
	<html:hidden property="fdWorkId" />
	<html:hidden property="fdPhaseId" />
	<html:hidden property="fdModelId" />
	<html:hidden property="fdModelName" />
	<html:hidden property="docStatus" />
	<html:hidden property="fdUseForm" />
	<ui:tabpage id="review_content" expand="false" var-navwidth="90%" collapsed="true">
		<c:import url="/km/review/km_review_ui/dingSuit/editContent.jsp" charEncoding="UTF-8">
			<c:param name="contentType" value="xform"></c:param>
		</c:import>
	</ui:tabpage>
	<div id="view_data" style="display: none">
		<ui:tabpage expand="false" var-navwidth="90%" collapsed="true">
			<ui:iframe id="iframe_checkDataList" src="${KMSS_Parameter_ContextPath}km/review/km_review_ui/dingSuit/viewData.jsp?type=category&categoryId=${JsParam.fdTemplateId}&nodeType=TEMPLATE"></ui:iframe>
		</ui:tabpage>
	</div>
	<%--提交按钮--%>
	<div class="submit_btn_div" style="display: inline-block;margin-bottom: 10px!important;">
		<c:choose>
			<c:when test="${kmReviewMainForm.method_GET=='edit'&&(kmReviewMainForm.docStatus=='10'
						||kmReviewMainForm.docStatus=='11'||kmReviewMainForm.docStatus=='20')}">
					<ui:button text="${ lfn:message('button.submit') }" order="2"  styleClass="lui_mix_submit_btn"
							onclick="_publishDraft();">
					</ui:button>
			</c:when>
			<c:otherwise>
				<div class="edit_btn_div btn_div_item"  style="margin-right: 40px; margin-left:99px; display: inline-block;">
					<ui:button text="${ lfn:message('button.savedraft') }" order="2" styleClass="lui_mix_save_doc_btn lui_mix_btu_lefts"
						onclick="_saveDoc();">
					</ui:button>
				</div>
				<div class="submit_btn_div btn_div_item" style="display: inline-block; margin-left: 0px;">
					<ui:button styleClass="lui_mix_submit_btn lui_mix_btu_lefts" text="${ lfn:message('button.submit') }" onclick="${kmReviewMainForm.method_GET=='add' ? '_submitDoc();' : '_updateDoc();'}">
					</ui:button>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	</form>

	<script language="JavaScript">
		//加载页面后改变提交按钮的位置到流程tab下方
		$(function () {
			changeSubmitBtnPosition();
		});

		function changeSubmitBtnPosition() {
			var btn = $(".submit_btn_div");
			var target = $(".lui-lbpm-foldOrUnfold");
			if (target) {
				target.parent().append(btn);
			}
			btn.show();
		}

		var _reviewValdate = $KMSSValidation(document.forms['kmReviewMainForm']);
		function _changeAttValidate(remove){
			if(window.Attachment_ObjectInfo){
				for(var tmpKey in window.Attachment_ObjectInfo){
					if(window.Attachment_ObjectInfo[tmpKey]){
						if(remove){
							window.Attachment_ObjectInfo[tmpKey]._reqired = window.Attachment_ObjectInfo[tmpKey].required;
							window.Attachment_ObjectInfo[tmpKey].required = false;
						}else{
							if(window.Attachment_ObjectInfo[tmpKey]._reqired!=null){
								window.Attachment_ObjectInfo[tmpKey].required = window.Attachment_ObjectInfo[tmpKey]._reqired;
							}
						}
					}
				}
			}
		}
		function _saveDoc(){ 
			_reviewValdate.removeElements($('#kmReviewXform')[0],'required');
			_changeAttValidate(true);
			Com_Submit(document.kmReviewMainForm, 'saveDraft');
		}
		function _submitDoc(){
			_reviewValdate.resetElementsValidate($('#kmReviewXform')[0]);
			_changeAttValidate(false);
			var submitFlag = true;
			var is_leave_flag = $("#is_leave_flag").val();
			console.log("is_leave_flag:"+is_leave_flag);
			//请假套件提交：is_leave_flag=0
			if(is_leave_flag && '0'===is_leave_flag){
				submitFlag = isLeave();
			}
			var cancelLevelDetailFlag = $("#cancelLevelDetailFlag").val();
			if(cancelLevelDetailFlag && '0'==cancelLevelDetailFlag){
				saveCancelDetail();
			}
			if(submitFlag){
				Com_Submit(document.kmReviewMainForm, 'save');
				//刷新遮罩位置
				if(typeof refreshValidatorTrPosition != "undefined" && refreshValidatorTrPosition) {
					refreshValidatorTrPosition();
				} 
			}
		}
		function _updateDoc(){ 
			_reviewValdate.removeElements($('#kmReviewXform')[0],'required');
			_changeAttValidate(true);
			Com_Submit(document.kmReviewMainForm, 'update');
		}
		function _updateDraft(){ 
			_reviewValdate.removeElements($('#kmReviewXform')[0],'required');
			_changeAttValidate(true);
			Com_Submit(document.kmReviewMainForm, 'updateDraft');
		}
		function _publishDraft(){
			_reviewValdate.resetElementsValidate($('#kmReviewXform')[0]);
			_changeAttValidate(false);
			
			var submitFlag = true;
			var is_leave_flag = $("#is_leave_flag").val();
			console.log("is_leave_flag:"+is_leave_flag);
			//请假套件提交：is_leave_flag=0
			if(is_leave_flag && '0'===is_leave_flag){
				submitFlag = isLeave();
			}
			var cancelLevelDetailFlag = $("#cancelLevelDetailFlag").val();
			if(cancelLevelDetailFlag && '0'==cancelLevelDetailFlag){
				saveCancelDetail();
			}
			if(submitFlag){
				Com_Submit(document.kmReviewMainForm, 'publishDraft');
			}
		}

		// ADD BY WUZB 20171102
		function checkEditType(useForm,useWord){
			if('true' == useWord && 'true' != useForm){
				var _wordEdit = $('#wordEdit');
				var wordFloat = $("#wordEditFloat");
				var reviewButtonDiv = $("#reviewButtonDiv");
				_wordEdit.css({'display':"block",'width':"100%",'height':"600px"});
				var xw = $("#wordEditWrapper").width();
				wordFloat.css({'width':xw+'px','height':'600px','filter':'alpha(opacity=100)','opacity':'1'});
				reviewButtonDiv.css({'width':xw+'px','height':'25px','filter':'alpha(opacity=100)','opacity':'1'});
				
				if ("${pageScope._isWpsWebOfficeEnable}" == "true" ){
					wps_mainContent.load();
				}else{
					var obj_JG = document.getElementById("JGWebOffice_mainContent");
					if ("${pageScope._isJGEnabled}" == "true"&& obj_JG) {
						
						setTimeout(function(){
							 if(Attachment_ObjectInfo['mainContent']){
								jg_attachmentObject_mainContent.load();
								jg_attachmentObject_mainContent.show();
								jg_attachmentObject_mainContent.ocxObj.Active(true);
							 }
							},1500);
						
						seajs.use(['lui/topic'],function(topic){
							topic.subscribe("Sidebar",function(data){
								var xw = $("#wordEditWrapper").width();
								wordFloat.css({'width':xw+'px','height':'600px','filter':'alpha(opacity=100)','opacity':'1','overflow':'hidden'});
								reviewButtonDiv.css({'width':xw+'px','height':'25px','filter':'alpha(opacity=100)','opacity':'1','overflow':'hidden'});
							});
						});
						
						$("#JGWebOffice_mainContent").height("600px");
						
					}
					
				}
				
			}
		}

		// ADD BY WUZB 20171102
		LUI.ready(function(){
			checkEditType('${kmReviewMainForm.fdUseForm}','${kmReviewMainForm.fdUseWord}');
		});

		// ADD BY WUZB 20171102
		Com_Parameter.event["submit"].push(function(){
			var useWord = document.getElementsByName('fdUseWord')[0];
			if ("true" == useWord.value) {
				
				if ("${pageScope._isWpsWebOfficeEnable}" != "true" && "${pageScope._isJGEnabled}" == "true") {
					
					var obj_JG = document.getElementById("JGWebOffice_mainContent");
					if(obj_JG&&Attachment_ObjectInfo['mainContent']&&jg_attachmentObject_mainContent.hasLoad){
						jg_attachmentObject_mainContent.ocxObj.Active(true);
						jg_attachmentObject_mainContent._submit();
						return true;
			    	 }
				} else {
					if ("${pageScope._isWpsWebOfficeEnable}" == "true" ){
						wps_mainContent.submit();
					}
				}
				
				 /*if(Attachment_ObjectInfo['mainContent']){
		       		jg_attachmentObject_mainContent.ocxObj.Active(true);
		       		jg_attachmentObject_mainContent._submit();
		       	 }else{
		       		seajs.use(['lui/jquery','lui/dialog'], function($, dialog){
		       			dialog.alert('<bean:message key="kmReviewMain.create.support" bundle="km-review" />');
		       		});
		       		return false;
		       	 }*/
		       	 
			}else{
				if ("${pageScope._isWpsWebOfficeEnable}" != "true" && "${pageScope._isJGEnabled}" == "true") {
					if("${kmReviewMainForm.method_GET}"=="add"){
			       		 if(Attachment_ObjectInfo['mainContent']){
			       			jg_attachmentObject_mainContent.unLoad();
			       		 }
			       	 }
				}
			}
			return true;
		});
	</script>
	<c:if test="${kmReviewMainForm.docStatus=='10' && kmReviewMainForm.fdIsImportXFormData == 'true'}">
		<script language="JavaScript">
			Com_IncludeFile("sysFormMainImport.js",Com_Parameter.ContextPath + "sys/xform/impt/js/","js",true);
			$(function(){
				getImportXFormDataSetting('com.landray.kmss.km.review.model.KmReviewTemplate');
			});
		</script>
	</c:if>
</template:replace>