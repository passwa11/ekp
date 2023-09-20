<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.mobile.util.MobileUtil"%>
<%@ page import="com.landray.kmss.common.actions.RequestContext"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@page import="com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil"%>
<%
	//判断是否是企业微信客户端
	if(MobileUtil.WXWORK_PC == MobileUtil.getClientType(new RequestContext(request))){
		request.setAttribute("isQywx", true);
	}else{
		request.setAttribute("isQywx", false);
	}
	request.setAttribute("isMeetingPage", true); 
%>
<%--内容区--%>
<template:replace name="content">
	<c:if test="${param.approveModel ne 'right'}">
		<form name="kmImeetingMainForm" method="post" action ="${KMSS_Parameter_ContextPath}km/imeeting/km_imeeting_main/kmImeetingMain.do">
	</c:if>
		<input type="hidden" name="fdId" value="${kmImeetingMainForm.fdId}" />
		<input type="hidden" name="fdRecurrenceStr" value="${kmImeetingMainForm.fdRecurrenceStr}" />
		
		<input type="hidden" name="fdPlaceId" value="${kmImeetingMainForm.fdPlaceId}" />
		<input type="hidden" name="fdHoldDate" value="${kmImeetingMainForm.fdHoldDate}" />
		<input type="hidden" name="fdFinishDate" value="${kmImeetingMainForm.fdFinishDate}" />
		<%-- 会议历史操作信息 --%>
		<div style="display: none;">
			<c:forEach items="${kmImeetingMainForm.kmImeetingMainHistoryForms}"  var="kmImeetingMainHistoryItem" varStatus="vstatus">
				<input type="hidden" name="kmImeetingMainHistoryForms[${vstatus.index}].fdId" value="${kmImeetingMainHistoryItem.fdId}" />
			</c:forEach>
		</div>
		<p class="txttitle" style="position:relative">
			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdNotifyView" />
			<c:if test="${ not empty kmImeetingMainForm.fdRecurrenceStr}">
				<span class="meeting_cyclicity" title="${ lfn:message('km-imeeting:kmImeeting.tree.cyclicity.meeting') }"></span>
			</c:if>
		</p>
		<%--回执提示栏显示条件：1、会议发布 2、会议未开始 3、会议已发通知 --%>
		<c:if test="${kmImeetingMainForm.docStatus=='30' and isBegin==false and kmImeetingMainForm.isNotify==true }">
			<%
			 	if("true".equals(KmImeetingConfigUtil.isTopicMng())){
			 %>
			 	<c:if test="${isFeedBackDeadline==false && not empty optType}">
				    <div style="color: red;text-align: center;">
						<a  style="color: red;text-decoration: underline"  href="JavaScript:void(0);" onclick="feedBack();">
							<bean:message bundle="km-imeeting" key="kmImeeting.feedback.create"/>						
						</a>
					</div>
				</c:if>
			<%}else{%>
				<%--已参加--%>
				<c:if test="${empty param.type && not empty optType && optType=='01' }">
					<div style="color: red;text-align: center;">
						<bean:message bundle="km-imeeting" key="kmImeeting.feedback.attend"/> 
						<a  style="color: red;text-decoration: underline" 
								data-href="${LUI_ContextPath }/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=edit&type=attend&meetingId=${kmImeetingMainForm.fdId }"  target="_blank" onclick="Com_OpenNewWindow(this)">
							<bean:message bundle="km-imeeting" key="kmImeeting.feedback.edit"/>								
						</a>
					</div>
				</c:if>
				<%--不参加--%>
				<c:if test="${empty param.type && not empty optType && optType=='02' }">
					<div style="color: red;text-align: center;">
						<bean:message bundle="km-imeeting" key="kmImeeting.feedback.unattend"/>
						<a style="color: red;text-decoration: underline"  
							data-href="${LUI_ContextPath }/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=edit&type=attend&meetingId=${kmImeetingMainForm.fdId }"  
							target="_blank" onclick="Com_OpenNewWindow(this)">
							<bean:message bundle="km-imeeting" key="kmImeeting.feedback.edit"/>			
						</a>
					</div>
				</c:if>
				<%--找人代理--%>
				<c:if test="${empty param.type && not empty optType && optType=='03' }">
					<div style="color: red;text-align: center;">
						<bean:message bundle="km-imeeting" key="kmImeeting.feedback.proxy"/>
					</div>
				</c:if>
				<%--未回执--%>
				<c:if test="${empty param.type && not empty optType && optType=='04' }">
					<div style="color: red;text-align: center;">
						<bean:message bundle="km-imeeting" key="kmImeeting.feedback.noopt"/>
						<a style="color: red;text-decoration: underline" 
							data-href="${LUI_ContextPath }/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=edit&type=attend&meetingId=${kmImeetingMainForm.fdId }"  target="_blank" onclick="Com_OpenNewWindow(this)">
							<bean:message bundle="km-imeeting" key="kmImeeting.feedback.create"/>	
						</a>
					</div>
				</c:if>
			<%} %>
			<%--组织人承接工作提示信息--%>
			<c:if test="${empty param.type && emccFlag == true && emccOpt == 'EmccDone' }">
				<div class="lui_metting_notice_container">
					<i class="lui_icon_s icon_success"></i>
					<span class="tips"><bean:message bundle="km-imeeting" key="kmImeeting.emcc.tip"/></span>
				</div>
			</c:if>
			<%--未承接工作安排--%>
			<c:if test="${empty param.type && emccFlag == true && emccOpt == 'UnEmccDone' }">
				<div class="lui_metting_notice_container">
					<i class="lui_icon_s icon_warn"></i>
					<span class="tips"><bean:message bundle="km-imeeting" key="kmImeeting.emcc.noopt"/></span>
					<a class="lui_form_button" style=text-decoration: none" 
                           data-href="${LUI_ContextPath }/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=editEmcee&type=admin&fdId=${kmImeetingMainForm.fdId }"  target="_blank" onclick="Com_OpenNewWindow(this)">
                           <bean:message bundle="km-imeeting" key="kmImeeting.emcc.create"/>   
                       </a>
				</div>
			</c:if>
		</c:if>
		<%--内容区--%>
		<div class="lui_form_content_frame" style="padding-top:10px">
			<div style="float: right"></div>
		<%--会议通知单--%>
		<c:if test="${type=='admin'  }">
			<%--管理员、流程审批人、创建人，可以看到通知单所有信息--%>
			<jsp:include page="/km/imeeting/km_imeeting_notify_letter/km_imeeting_notify_letter_admin.jsp" />
		</c:if>
		<c:if test="${type=='attend' }">
			<%--会议主持人/参加人/列席人员看到的会议通知单--%>
			<jsp:include page="/km/imeeting/km_imeeting_notify_letter/km_imeeting_notify_letter_attend.jsp" />
		</c:if>
		<c:if test="${type=='assist'  }">
			<%--会议协助人、会议室保管员看到的会议通知单--%>
			<jsp:include page="/km/imeeting/km_imeeting_notify_letter/km_imeeting_notify_letter_assist.jsp" />
		</c:if>
		<c:if test="${type=='cc'  }">
			<%--抄送人、可阅读者看到的会议通知单--%>
			<jsp:include page="/km/imeeting/km_imeeting_notify_letter/km_imeeting_notify_letter_cc.jsp" />
		</c:if>
		<%--变更记录--%>
		<ui:dataview>
			<ui:source type="AjaxJson">
				{url:'/km/imeeting/km_imeeting_main_history/kmImeetingMainHistory.do?method=getChangeHistorysByMeeting&meetingId=${JsParam.fdId }'}
			</ui:source>
			<ui:render type="Template">
				<c:import url="/km/imeeting/resource/tmpl/changeHistory.jsp" charEncoding="UTF-8"></c:import>
			</ui:render>
		</ui:dataview>
		</div>
		<c:choose>
			<c:when test="${param.approveModel eq 'right'}">
				<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="5" var-average='false' var-useMaxWidth='true'
					var-supportExpand="true" var-expand="true">
					<jsp:include page="/km/imeeting/km_imeeting_main/kmImeetingMain_view_include.jsp" />
				</ui:tabpanel>
			</c:when>
			<c:otherwise>
				<ui:tabpage expand="false" var-navwidth="90%">
					<jsp:include page="/km/imeeting/km_imeeting_main/kmImeetingMain_view_include.jsp" />
				</ui:tabpage>
			</c:otherwise>
		</c:choose>
	<c:if test="${param.approveModel ne 'right'}">
	 </form>
	</c:if>
<jsp:include page="/km/imeeting/km_imeeting_main/kmImeetingMain_viewKkVideo_include.jsp" />
<script type="text/javascript">
seajs.use([ 'km/imeeting/resource/js/dateUtil', 'sys/ui/js/dialog','lui/topic','lui/jquery'], function(dateUtil,dialog,topic,$) {

	topic.subscribe('successReloadPage', function() {
		window.location.reload();
	});

	window.voteConfig = function(){
		 var url = "/km/imeeting/km_imeeting_vote/kmImeetingVote_view_list.jsp?fdMeetingId=${kmImeetingMainForm.fdId}";
		dialog.iframe(url,"投票配置",null,{width:1200, height:600, topWin : window, close: true});
	}

	window.feedBack= function(){
		var url = '/km/imeeting/km_imeeting_main_feedback/index.jsp?meetingId=${JsParam.fdId}';
		dialog.iframe(url,'<bean:message bundle="km-imeeting" key="kmImeetingFeedback.feedbackList" />',function(value){},{width:900,height:400});
	};

	window.beginMeeting=function(){
		var url = '<c:url value="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=updateBeginMeeting"/>';
		window.del_load = dialog.loading();
		$.ajax({
			url: url,
			type: 'post',
			dataType: 'json',
			data:{fdMeetingId:'${kmImeetingMainForm.fdId}'},
			error: function(data){
				if(window.del_load!=null){
					window.del_load.hide();
				}
				dialog.result(data.responseJSON);
			},
			success: function(data){
				console.log(data.flag)
				//var results =  eval("("+data+")");
				if(data.flag=="success"){
					dialog.success('开启成功');
					location.reload();
				}
			}
	   });
	};

	window.getMeetingAtt=function(){
		var url = '<c:url value="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=updateMeetingAtt"/>';
		window.del_load = dialog.loading();
		$.ajax({
			url: url,
			type: 'post',
			dataType: 'json',
			data:{fdMeetingId:'${kmImeetingMainForm.fdId}'},
			error: function(data){
				if(window.del_load!=null){
					window.del_load.hide();
				}
				dialog.result(data.responseJSON);
			},
			success: function(data){
				if(data.flag=="success"){
					dialog.success('批注获取成功');
					location.reload();
				}
			}
	   });
	};


	window.getMeetingAttTest=function(){
		 path=Com_GetCurDnsHost()+Com_Parameter.ContextPath+'km/imeeting/km_imeeting_main/kmImeetingAttTest.jsp';
	    dialog.iframe(path,'33333',function(rtn){
		  if(rtn!="undefined"&&rtn!=null){
			  console.log(rtn);
			  var url = '<c:url value="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=addAttFromBoenTest"/>';
				window.del_load = dialog.loading();
				$.ajax({
					url: url,
					type: 'post',
					dataType: 'json',
					data:{fdId:'${kmImeetingMainForm.fdId}',attachmentName:rtn.attachmentName,attachmentUrl:rtn.attachmentUrl,fdKey:rtn.fdKey},
					error: function(data){
						if(window.del_load!=null){
							window.del_load.hide();
						}
						dialog.result(data.responseJSON);
					},
					success: function(){
						var results =  eval("("+data+")");
						if(results['flag']=="success"){
							dialog.success('批注获取成功');
							 location.reload();
						}
					}
			   });
		  }
	   },{width:800,height:400});
	};

	window.copyMeeting = function() {
		var copyMeetingUrl = "${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?"
					+ "method=add&fdTemplateId=${kmImeetingMainForm.fdTemplateId}&meetingId=${JsParam.fdId}&copyMeeting=true";
		var recurrenceStr = $("input[name='fdRecurrenceStr']").val();
		if (recurrenceStr) {
			copyMeetingUrl += "&isCycle=true";
		}
		Com_OpenWindow(copyMeetingUrl,'_self');
	}

	window.gun = function(){
		//驳回时进行资源冲突校验
		if("${kmImeetingMainForm.docStatus}" ==  "11"){
			//资源冲突检测
			_checkResConflict().done(function(){
				//设备冲突检测
				_checkEquipmentConflict().done(function(){
					Com_Submit(document.kmImeetingMainForm, 'approveDraft');
					//__validateFinish(commitType, saveDraft,isChange);
				});
			});
		}else{
			Com_Submit(document.kmImeetingMainForm, 'approveDraft');
		}
	};
	window.updateChange = function(){
		var recurrenceStr = $("input[name='fdRecurrenceStr']").val();
		//非周期性会议变更前提示是否变更
		if(!recurrenceStr) {
			dialog.confirm('${lfn:message("km-imeeting:kmImeetingMain.opt.change.tip")}',function(value){
				if(value){
					Com_OpenWindow('${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=changeMeeting&fdId=${JsParam.fdId}','_self');
				}
			});
		}else {
			window._updateChange();
		}

	};

	window._updateChange = function(){
		var recurrenceStr = $("input[name='fdRecurrenceStr']").val(),
			url = "${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?";
		if(recurrenceStr){
				var _dialog = dialog.build({
				config : {
					width : 536,
					title : '${lfn:message("km-imeeting:kmImeeting.btn.change")}',
					lock : true,
					cahce : false,
					close : true,
					content : {
						type : "common",
						html : '${lfn:message("km-imeeting:tips.changeType")}',
						iconType : 'question',
						buttons : [{
							name : '${lfn:message("km-imeeting:tips.changeCurrent")}',
							styleClass : 'lui_toolbar_btn_gray',
							fn : function(value, dialog) {
								url += 'method=changeMeeting&fdId=${JsParam.fdId}&changeType=cur';
								Com_OpenWindow(url,'_self');
								_dialog.hide(value);
							}
						},{
							name : '${lfn:message("km-imeeting:tips.changeFollow-up")}',
							styleClass : 'lui_toolbar_btn_gray',
							fn : function(value, dialog) {
								url =  url + 'method=add&fdOriginalId=${JsParam.fdId}&changeType=after&fdTemplateId=${kmImeetingMainForm.fdTemplateId}&isCycle=true';
								Com_OpenWindow(url,'_self');
								_dialog.hide(value);
							}
						}]
					}
				},
				callback : function(){}
			}).show();
		}
	}

	window.addSeatPlan = function(){
		Com_OpenWindow('${LUI_ContextPath}/km/imeeting/km_imeeting_seat_plan/kmImeetingSeatPlan.do?method=add&fdImeetingMainId=${JsParam.fdId}','_self');
	}

	window.viewSeatPlan = function(){
		Com_OpenWindow('${LUI_ContextPath}/km/imeeting/km_imeeting_seat_plan/kmImeetingSeatPlan.do?method=view&fdId=${kmImeetingMainForm.fdSeatPlanId}','_blank');
	}


	window.earlyEndMeeting = function(){
		var url = '/km/imeeting/km_imeeting_main/kmImeetingMain_earlyEnd_edit.jsp?fdId=${JsParam.fdId}';
		url = Com_SetUrlParameter(url,"fdHoldDate","${kmImeetingMainForm.fdHoldDate}");
		url = Com_SetUrlParameter(url,"fdFinishDate","${kmImeetingMainForm.fdFinishDate}");
		url = Com_SetUrlParameter(url,"fdEarlyFinishDate",dateUtil.formatDate(new Date(),'yyyy-MM-dd HH:mm'));
		dialog.iframe(url,'${lfn:message("km-imeeting:kmImeetingMain.earlyEndMeeting")}',function(value){
			if(typeof value =="undefined"){
				location.reload();
			}
		},{width:600,height:400});
	};
	//创建直播
	window.createMyLiving = function(){
		//校验是否在企业微信客户端打开链接
		var qywx = ${isQywx};
		if(qywx){//企业微信客户端 创建直播
			options={
				"modelName": "com.landray.kmss.km.imeeting.model.KmImeetingMain",
				"modelId": "${kmImeetingMainForm.fdId }",
				"anchor_userid": "${KMSS_Parameter_CurrentUserId}",
				"theme": "${kmImeetingMainFormFdNameFormatNewLine }",
				"living_duration": 3600,
				"liveType": 0,
				"remind_time": 60,
				"description": "${kmImeetingMainFormfdRemarkFormatNewLine }"};
			//创建直播
			console.log(options);
			livingImmediately(options);
		}else{
			dialog.failure("复制链接去企业微信客户端打开");
		}
	}
	//观看直播
	window.openMyLiving = function(){
		var qywx = ${isQywx};
		var wxLivingId = '${wxLivingId}';
		if(qywx){//企业微信客户端 创建直播
		options={
			"modelName": "com.landray.kmss.km.imeeting.model.KmImeetingMain",
			"modelId": "${kmImeetingMainForm.fdId }",
			"theme": "${kmImeetingMainFormFdNameFormatNewLine }",
			"living_duration": 3600,
			"liveType": 0,
			"remind_time": 60,
			"description": "${kmImeetingMainFormfdRemarkFormatNewLine }"};
		console.log(options);
		console.log(wxLivingId);
		openLiving(wxLivingId); //打开直播
		}else{
			dialog.failure("复制链接去企业微信客户端打开");
		}
	}

	window.Delete=function(){
    	dialog.confirm("${lfn:message('page.comfirmDelete')}",function(flag){
	    	if(flag==true){
	    		Com_OpenWindow('${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=delete&fdId=${JsParam.fdId}','_self');
	    	}else{
	    		return false;
		    }
	    },"warn");
    };

	//发送会议通知
	window.sendNotify=function(){
		var names=(function(){
			var hostName='${kmImeetingMainForm.fdHostName }',
				attendName='${kmImeetingMainForm.fdAttendPersonNames }',
				participantName='${kmImeetingMainForm.fdParticipantPersonNames }';
			return convertToArray(hostName,attendName,participantName);
		})();
		//#9196 提示语：给人1，人2...发送会议通知，邀请您参加会议：XX会议主题，召开时间：XX，会议地点：XX
		var confirmTip="${lfn:message('km-imeeting:kmImeetingMain.attend.notify.confirm.tip')}"
						.replace('%km-imeeting:kmImeetingMain.attend%',names)
						.replace('%km-imeeting:kmImeetingMain.fdName%','<c:out value="${kmImeetingMainForm.fdName}" />')
						.replace('%km-imeeting:kmImeetingMain.fdDate%','${kmImeetingMainForm.fdHoldDate}')
						.replace('%km-imeeting:kmImeetingMain.fdPlace%','<c:out value="${kmImeetingMainForm.fdPlaceName}" />'+'<c:out value="${kmImeetingMainForm.fdOtherPlace}" />');

		dialog.confirm(confirmTip,function(flag){
			if(flag==true){
				window._load = dialog.loading();
				$.post('<c:url value="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=sendNotify&fdId=${JsParam.fdId}"/>',
						function(data){
							if(window._load!=null)
								window._load.hide();
							if(data!=null && data.status==true){
								dialog.success('会议通知已发送');
								 LUI('toolbar').removeButton(LUI('sendNotify'));
							}else{
								dialog.failure('<bean:message key="return.optFailure" />');
							}
						},'json');
			}else{
				return false;
			}

		});
	};

	//会议催办
	window.showHastenMeeting=function(){
		dialog.iframe('/km/imeeting/km_imeeting_main_hasten/kmImeetingMainHasten.do?method=showHastenMeeting&meetingId=${JsParam.fdId}',
			'${lfn:message("km-imeeting:kmImeeting.btn.hastenMeeting")}',null,{width:600,height:360});
	};

	//会议取消
	window.showCancelMeeting=function(){
		var recurrenceStr = $("input[name='fdRecurrenceStr']").val();
		//非周期性会议变更前提示是否变更
		if(!recurrenceStr) {
			dialog.iframe('/km/imeeting/km_imeeting_main_cancel/kmImeetingMainCancel.do?method=showCancelMeeting&meetingId=${JsParam.fdId}',
					'${lfn:message("km-imeeting:kmImeetingMain.cancelMeeting")}',function(value){
				if(typeof value =="undefined"){
					location.reload();
				}
			},{width:600,height:380});
		}else{
			window._showCancelMeeting();
		}
	};

	window._showCancelMeeting = function(){
		var recurrenceStr = $("input[name='fdRecurrenceStr']").val(),
		url = "${LUI_ContextPath}/km/imeeting/km_imeeting_main_cancel/kmImeetingMainCancel.do?method=showCancelMeeting&meetingId=${JsParam.fdId}";
		if(recurrenceStr){
				var _dialog = dialog.build({
				config : {
					width : 536,
					title : '${lfn:message("km-imeeting:kmImeetingMain.cancelMeeting")}',
					lock : true,
					cahce : false,
					close : true,
					content : {
						type : "common",
						html : '${lfn:message("km-imeeting:tips.cancelType")}',
						iconType : 'question',
						buttons : [{
							name : '${lfn:message("km-imeeting:tips.cancelCurrent")}',
							styleClass : 'lui_toolbar_btn_gray',
							fn : function(value, dialog) {
								url += '&cancelType=cur';
								Com_OpenWindow(url,'_self');
								_dialog.hide(value);
							}
						},{
							name : '${lfn:message("km-imeeting:tips.cancelFollow-up")}',
							styleClass : 'lui_toolbar_btn_gray',
							fn : function(value, dialog) {
								url =  url + '&cancelType=after';
								Com_OpenWindow(url,'_self');
								_dialog.hide(value);
							}
						}]
					}
				},
				callback : function(){}
			}).show();
		}
	}

	//初始化会议历时
	if( "${kmImeetingMainForm.fdHoldDuration}" ){
		//将小时分解成时分
		var timeObj=dateUtil.splitTime({"ms":"${kmImeetingMainForm.fdHoldDuration}"});
		$('#fdHoldDurationHour').html(timeObj.hour);
		$('#fdHoldDurationMin').html(timeObj.minute);
		if(timeObj.minute){
			$('#fdHoldDurationMinSpan').show();
		}else{
			$('#fdHoldDurationMinSpan').hide();
		}
	}

	//转换成数组
	function convertToArray(){
		var slice=Array.prototype.slice,
			args=slice.call(arguments,0),
			arr=[];
		for(var i=0;i<args.length;i++){
			if(args[i]){
				var ids=args[i].split(';');
				for(var j=0;j<ids.length;j++){
					if(ids[j])
						arr.push(ids[j]);
				}
			}
		}
		return arr;
	}

	//提交前校验资源是否被占用
	function _checkResConflict(){
		var deferred=$.Deferred();
		if($('[name="fdPlaceId"]').val()){
			$.ajax({
				url: "${LUI_ContextPath}/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=checkConflict",
				type: 'POST',
				dataType: 'json',
				data: {meetingId : $('[name="fdId"]').val() , fdPlaceId: $('[name="fdPlaceId"]').val(), "fdHoldDate":$('[name="fdHoldDate"]').val() , "fdFinishDate":$('[name="fdFinishDate"]').val() },
				success: function(data, textStatus, xhr) {//操作成功
					if(data && !data.result ){
						//不冲突
						deferred.resolve();
					}else{
						//冲突
						var fdPlaceName = $('[name="fdPlaceName"]').val();
						if(!fdPlaceName || $.trim(fdPlaceName).length == 0){
							fdPlaceName = _getConflictImmeetingPlaceName(data.conflictArr);
						}
						dialog.alert("${lfn:message('km-imeeting:kmImeetingMain.conflict.tip')}".replace('%Place%',fdPlaceName));
						deferred.reject();
					}
				}
			});
		}else{
			setTimeout(function(){
				deferred.resolve();
			},1);
		}
		return deferred.promise();
	}

	function _getConflictImmeetingPlaceName(conflictArr) {
		if(!conflictArr || conflictArr.length == 0){
			return  '';
		}
		if(conflictArr.length == 1) {
			return conflictArr[0].conflictName;
		}
		var places = '';
		for(var i=0;i<conflictArr.length; i++){
			places+=conflictArr[i].conflictName
			if(i != conflictArr.length - 1) {
				places+=', ';
			}
		}
	}

	//提交前校验有设备是否被占用
	function _checkEquipmentConflict(){
		var deferred=$.Deferred();
		if($('[name="kmImeetingEquipmentIds"]').val()){
			//设备冲突检测
			$.ajax({
				url: "${LUI_ContextPath}/km/imeeting/km_imeeting_equipment/kmImeetingEquipment.do?method=checkConflict",
				type: 'POST',
				dataType: 'json',
				data: { meetingId : $('[name="fdId"]').val() ,equipmentIds: $('[name="kmImeetingEquipmentIds"]').val(), "fdHoldDate":$('[name="fdHoldDate"]').val() , "fdFinishDate":$('[name="fdFinishDate"]').val() },
				success: function(data, textStatus, xhr) {//操作成功
					if(data && !data.conflict ){
						deferred.resolve();
					}else{
						//冲突
						var conflictNames = '';
						for(var i = 0 ;i < data.conflictArray.length;i++){
							conflictNames += data.conflictArray[i].name + ';';
						}
						dialog.alert('资源：'+ conflictNames + '当前时间段存在冲突,请重新选择');
						deferred.reject();
					}
				}
			});
		}else{
			setTimeout(function(){
				deferred.resolve();
			},1);
		}
		return deferred.promise();
	}

});
</script>
</template:replace>
<c:if test="${param.approveModel eq 'right'}">
	<template:replace name="barRight">
		<c:choose>
			<c:when test="${kmImeetingMainForm.docStatus>='30' || kmImeetingMainForm.docStatus=='00'}">
				<ui:accordionpanel>
					<c:import url="/km/imeeting/km_imeeting_main/kmImeetingMain_viewBaseInfo.jsp" charEncoding="UTF-8">
					</c:import>
				</ui:accordionpanel>
			</c:when>
			<c:otherwise>
				<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel" >
				<%-- 流程 --%>
				<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmImeetingMainForm" />
					<c:param name="fdKey" value="ImeetingMain" />
					<c:param name="showHistoryOpers" value="true" />
					<c:param name="isExpand" value="true" />
					<c:param name="onClickSubmitButton" value="gun();" />
					<c:param name="approveType" value="right" />
					<c:param name="approvePosition" value="right" />
				</c:import>
				<!-- 审批记录 -->
				<c:import url="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote_list_content.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmImeetingMainForm" />
					<c:param name="fdModelId" value="${kmImeetingMainForm.fdId}" />
					<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain" />
				</c:import>
				<c:import url="/km/imeeting/km_imeeting_main/kmImeetingMain_viewBaseInfo.jsp" charEncoding="UTF-8">
				</c:import>
			</ui:tabpanel>
			</c:otherwise>
		</c:choose>		
	</template:replace>
</c:if>
	