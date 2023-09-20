<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ page import="java.util.Date" %>
<%@ page import="com.landray.kmss.km.imeeting.forms.KmImeetingMainForm" %>
<%@ page import="com.landray.kmss.util.DateUtil" %>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%
	Date now=new Date();
	Boolean isBegin=false,isEnd=false, isFeedBackDeadline = false;
	KmImeetingMainForm kmImeetingMainForm = (KmImeetingMainForm)request.getAttribute("kmImeetingMainForm");
	// 会议已开始
	if (DateUtil.convertStringToDate(kmImeetingMainForm.getFdHoldDate(),
			DateUtil.TYPE_DATETIME,request.getLocale()).getTime() < now.getTime()) {
		isBegin = true;
	}
	// 会议已结束
	if (DateUtil.convertStringToDate(kmImeetingMainForm.getFdFinishDate(),
			DateUtil.TYPE_DATETIME,request.getLocale()).getTime() < now.getTime()) {
		isEnd = true;
	}
	
	// 回执截止时间是否已过（只有开启伯恩才会有截止时间）
	if (StringUtil.isNotNull(kmImeetingMainForm.getFdFeedBackDeadline())) {
		if (DateUtil.convertStringToDate(kmImeetingMainForm.getFdFeedBackDeadline(),
				DateUtil.TYPE_DATETIME,request.getLocale()).getTime() < now.getTime()) {
			isFeedBackDeadline = true;
		}
		request.setAttribute("isFeedBackDeadline", isFeedBackDeadline);
	} else {
		request.setAttribute("isFeedBackDeadline", "noFeedBackDeadline");
	}
	request.setAttribute("isBegin", isBegin);
	request.setAttribute("isEnd", isEnd);
	
%>
<%
	request.setAttribute("isMeetingPage", false);
	request.setAttribute("fdCurUserId", UserUtil.getKMSSUser().getUserId());
%>
<template:include ref="default.edit" sidebar="no" >
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/resource/css/view.css?s_cache=${MUI_Cache}" />
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/resource/css/view_simple.css" /> 
	</template:replace>
	<template:replace name="title">
		<c:out value="${kmImeetingMainForm.fdName} - ${ lfn:message('km-imeeting:table.kmImeetingMain') }"></c:out>
	</template:replace>
	
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"> 
			<c:choose>
				<c:when test="${isFeedBackDeadline eq 'noFeedBackDeadline'}">
					<c:if test="${isBegin==false && kmImeetingMainFeedbackForm.fdOperateType!='03' && shouldFeedback == 'true' }">
						<ui:button text="${lfn:message('button.submit') }" order="2" onclick="commitMethod('update', 'false');"></ui:button>
					</c:if>
					<c:if test="${isBegin == false }">
						<ui:button text="${ lfn:message('button.close') }" order="5"  onclick="Com_CloseWindow()"></ui:button>
					</c:if>
					<c:if test="${isBegin == true }">
						<ui:button text="${ lfn:message('button.close') }" order="5"  onclick="top.opener = top;top.open('', '_self');top.close()"></ui:button>
					</c:if>
				</c:when>
				<c:otherwise>
					<c:if test="${isFeedBackDeadline==false && kmImeetingMainFeedbackForm.fdOperateType!='03' && shouldFeedback == 'true' }">
						<ui:button text="${lfn:message('button.submit') }" order="2" onclick="commitMethod('update', 'false');"></ui:button>
					</c:if>
					<c:if test="${isFeedBackDeadline == false }">
						<ui:button text="${ lfn:message('button.close') }" order="5"  onclick="Com_CloseWindow()"></ui:button>
					</c:if>
					<c:if test="${isFeedBackDeadline == true }">
						<ui:button text="${ lfn:message('button.close') }" order="5"  onclick="top.opener = top;top.open('', '_self');top.close()"></ui:button>
					</c:if>
				</c:otherwise>
			</c:choose>
		</ui:toolbar>
	</template:replace>
	
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"  id="categoryId"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('km-imeeting:module.km.imeeting') }"  ></ui:menu-item>
			<ui:menu-item text="${ lfn:message('km-imeeting:table.kmImeetingMain') }"></ui:menu-item>
			<ui:menu-source autoFetch="false">
				<ui:source type="AjaxJson">
					{"url":"/sys/category/criteria/sysCategoryCriteria.do?method=path&modelName=com.landray.kmss.km.imeeting.model.KmImeetingTemplate&categoryId=${kmImeetingMainForm.fdTemplateId}"} 
				</ui:source>
			</ui:menu-source>
		</ui:menu>
	</template:replace>
	<template:replace name="content"> 
		<html:form action="/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do">
			<p class="txttitle">
				<bean:message bundle="km-imeeting" key="kmImeetingMain.fdNotifyView" />
			</p>
			<c:choose>
				<c:when test="${isFeedBackDeadline eq 'noFeedBackDeadline'}">
					<%-- 如果会议开始不能进行回执提示 --%>
					<c:if test="${isBegin==true}">
						<div style="color: red;text-align: center;">
							<bean:message bundle="km-imeeting" key="kmImeetingMain.hasBegin.tip"/>
						</div>
					</c:if>
				</c:when>
				<c:otherwise>
					<%-- 如果已过回执截止时间，不能进行回执提示 --%>
					<c:if test="${isFeedBackDeadline==true}">
						<div style="color: red;text-align: center;">
							<bean:message bundle="km-imeeting" key="kmImeetingMain.hasFeedBackDeadline.tip"/>
						</div>
					</c:if>
				</c:otherwise>
			</c:choose>
			<div class="lui_form_content_frame" style="padding-top:5px">
			<%--需要回复--%>
			<c:if test="${shouldFeedback==true }">
				<html:hidden property="fdId" />
				<html:hidden property="fdMeetingId"  value="${HtmlParam['meetingId'] ?HtmlParam['meetingId']:kmImeetingMainFeedbackForm.fdMeetingId}"/>
				<html:hidden property="fdType"  value="${kmImeetingMainFeedbackForm.fdType}"/>
				<html:hidden property="fdAgendaId"  value="${kmImeetingMainFeedbackForm.fdAgendaId}"/>
				<html:hidden property="fdUnitName"  value="${kmImeetingMainFeedbackForm.fdUnitName}"/>
				<input name="attendOther" value="false" type="hidden">
				<table class="tb_normal" width="100%;">
					<tr>
						<%--回执留言--%>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="km-imeeting" key="kmImeetingMainFeedback.fdReason"/>
						</td>
						<td width="85%" colspan="3" >
							<xform:textarea property="fdReason" style="width:98%;" validators="validateReason"></xform:textarea>
						</td>
					</tr>
					<tr>
						<%--回执操作--%>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="km-imeeting" key="kmImeetingMainFeedback.fdOperateType"/>
						</td>
						<td width="35%" >
							 <xform:radio property="fdOperateType" required="true" onValueChange="changeOptType">
								<xform:enumsDataSource enumsType="km_imeeting_main_feedback_fd_operate_type" />
							</xform:radio>
							<c:if test="${kmImeetingMainForm.syncDataToCalendarTime=='personAttend' }">
								<script>
									//同步时机为“参与人点击参加后同步”时，参加radio上面显示提示文字：点击确认参会并自动写入个人日程中
									seajs.use(['lui/jquery'],function($){
										$('[name="fdOperateType"][value="01"]').parent().attr('title','${lfn:message("km-imeeting:kmImeetingMain.attend.sync.tip")}');					
									});
								</script>
							</c:if>
							<script>
								//邀请他人参加提示语
								seajs.use(['lui/jquery'],function($){
									$('[name="fdOperateType"][value="05"]').parent().attr('title','${lfn:message("km-imeeting:kmImeetingMain.attendother.tip")}');					
								});
							</script>
						</td>
						<%--实际参与人员--%>
						<td class="td_normal_title" width=15%>
							<div class="docAttend" <c:if test="${kmImeetingMainFeedbackForm.fdOperateType!='03' }">style="display:none;"</c:if>>
								<bean:message bundle="km-imeeting" key="kmImeetingMainFeedback.docAttendId"/>
							</div>
							<div class="docAttendOther" style="display: none;">
								<bean:message bundle="km-imeeting" key="kmImeetingMainFeedback.Invitees"/>
							</div>
						</td>
						<td width="35%" >
							<div class="docAttend" <c:if test="${kmImeetingMainFeedbackForm.fdOperateType!='03' }">style="display:none;"</c:if>>
								<xform:address propertyName="docAttendName" propertyId="docAttendId" orgType="ORG_TYPE_PERSON" style="width:50%;" validators="validateDocAttend"></xform:address>
							</div>
							<div class="docAttendOther" style="display: none;">
								<xform:address mulSelect="true"  propertyName="attendOtherNames" propertyId="attendOtherIds" orgType="ORG_TYPE_PERSON" style="width:50%;" validators="validateDocAttendOther"></xform:address>
							</div>
						</td>
					</tr>
				</table>
				<br/>
			</c:if>
			<c:choose>
				<c:when test="${kmImeetingMainForm.fdTemplateId == null || kmImeetingMainForm.fdTemplateId == ''}">
					<jsp:include page="/km/imeeting/km_imeeting_notify_letter/km_imeeting_notify_letter_simple.jsp" />
				</c:when>
				<c:otherwise>
					<%--会议通知单--%>
					<c:if test="${type=='admin'  }">
						<%--管理员，可以看到通知单所有信息--%>
						<jsp:include page="/km/imeeting/km_imeeting_notify_letter/km_imeeting_notify_letter_admin.jsp" />
					</c:if>
					<c:if test="${JsParam.type=='attend' }">
						<%--会议主持人/参加人/列席人员看到的会议通知单--%>
						<jsp:include page="/km/imeeting/km_imeeting_notify_letter/km_imeeting_notify_letter_attend.jsp"/>
					</c:if>
					<c:if test="${JsParam.type=='assist' }">
						<%--会议协助人看到的会议通知单--%>
						<jsp:include page="/km/imeeting/km_imeeting_notify_letter/km_imeeting_notify_letter_assist.jsp" />
					</c:if>
					<c:if test="${JsParam.type=='cc' }">
						<%--抄送人员看到的会议通知单--%>
						<jsp:include page="/km/imeeting/km_imeeting_notify_letter/km_imeeting_notify_letter_cc.jsp" />
					</c:if>
				</c:otherwise>
			</c:choose>
			</div>
		</html:form>
		<%--回执页签，管理员、参与人、抄送人可见--%>
		<c:if test="${JsParam.type=='admin' or JsParam.type=='attend' or JsParam.type=='cc'}">
			<%--会议回执--%>
			<c:if test="${kmImeetingMainForm.docStatusFirstDigit=='3' }">
			<kmss:auth requestURL="/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=getFeedbackList&meetingId=${kmImeetingMainForm.fdId}" requestMethod="GET">
				<ui:tabpage expand="false">
					<ui:content title="${ lfn:message('km-imeeting:table.kmImeetingMainFeedback') }">
						<script type="text/javascript">	
							seajs.use(['theme!listview']);
							seajs.use(['km/imeeting/resource/css/feedback.css']);	
						</script>
						<ui:dataview>
							<ui:source type="AjaxJson">
								{url:'/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=getFeedbackList&meetingId=${kmImeetingMainForm.fdId }&sort=desc&type=creator&rowsize=10'}
							</ui:source>
							<ui:render type="Javascript">
								<c:import url="/km/imeeting/resource/tmpl/feedbackList.js" charEncoding="UTF-8"></c:import>
							</ui:render>
						</ui:dataview>
						<list:paging></list:paging>
					</ui:content>
				</ui:tabpage>
			</kmss:auth>
			</c:if>
       	</c:if>
	</template:replace>
</template:include>
<script>
	var validation=$KMSSValidation();//校验框架
</script>
<script>
seajs.use([
          'km/imeeting/resource/js/dateUtil',
 	      'lui/jquery',
 	      'lui/dialog'
 	        ],function(dateUtil,$,dialog){
	
		window.samePerson = "";
		
		//会议开始后禁用所有文本输入
		if("${isFeedBackDeadline}"=="true"){
			$(':input').attr('readOnly',true);
			$("input[type=radio]").each(function(){
				this.disabled="disabled";
		     });
		}

		//自定义校验器1：当回复不参加时，留言为必填
		validation.addValidator("validateReason","不参加必须填写理由",function(v, e, o){
			var fdOperateType=$('[name="fdOperateType"]:checked');
			var reason=$('[name="fdReason"]').val();
			if(fdOperateType && fdOperateType.val()=="02" && !reason){
				return false;	
			}
			return true;
		});
		
		//自定义校验器2：当回复找人代参加时，实际参与人为必填
		validation.addValidator("validateDocAttend","找人代参加必须填写实际参与人",function(v, e, o){
			var fdOperateType=$('[name="fdOperateType"]:checked');
			var docAttendId=$('[name="docAttendId"]').val();
			if(fdOperateType && fdOperateType.val()=="03" && !docAttendId){
				return false;	
			}
			return true;
		});
		
		//自定义校验器3：邀请他人参加,参加人不能为空
		validation.addValidator("validateDocAttendOther","邀请人不能为空",function(v, e, o){
			var fdOperateType=$('[name="fdOperateType"]:checked');
			var attendOtherIds=$('[name="attendOtherIds"]').val();
			if(fdOperateType && fdOperateType.val()=="05" && !attendOtherIds){
				return false;	
			}
			return true;
		});
		

		//修改回执操作
		window.changeOptType=function(){
			KMSSValidation_HideWarnHint(document.getElementsByName("docAttendName"));
			KMSSValidation_HideWarnHint(document.getElementsByName("fdReason"));
			var fdOperateType=$('[name="fdOperateType"]:checked');
			$('.docAttend,.docAttendOther').hide();
			if(fdOperateType && fdOperateType.val()=="03"){
				$('.docAttend').show();
				setTimeout(function(){
					//$('[name="docAttendName"]').trigger('click');
					Dialog_Address(false, 'docAttendId', 'docAttendName',null,ORG_TYPE_PERSON);
				},1);
			}
			if(fdOperateType && fdOperateType.val()=="05"){
				$('.docAttendOther').show();
				setTimeout(function(){
					Dialog_Address(true, 'attendOtherIds', 'attendOtherNames',null,ORG_TYPE_PERSON);
				},1);
			}
		};
		
		//提交
		window.commitMethod=function(commitType, saveDraft){
			if (!checkIsSameTime()) {
				 _commitMethod(commitType, saveDraft);
			} else {
				dialog.confirm(window.samePerson + "已回执参加其他会议，会议时间有冲突，确定提交？", function(value) {
					if(value) {
						_commitMethod(commitType, saveDraft);
					}
				})
			}
		};
		
		function _commitMethod(commitType, saveDraft) {
			var formObj = document.kmImeetingMainFeedbackForm;
			var fdOperateType = $('[name="fdOperateType"]:checked');
			if(fdOperateType && fdOperateType.val()=="05"){
				$('[name="attendOther"]').val('true');
				$('[name="fdOperateType"]').val('01');
			}
			if ('save' == commitType) {
				Com_Submit(formObj, commitType, 'fdId');
			} else {
				Com_Submit(formObj, commitType);
			}
		}

		//初始化会议历时
		if('${kmImeetingMainForm.fdHoldDuration}'){
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

		/*
		* 校验参与人是否在同时间段有其他会议
		* false : 不存在
		* true : 存在
		*/
		function checkIsSameTime() {
			window.samePerson = "";
			// 会议ID
			var fdMeetingId = "${kmImeetingMainFeedbackForm.fdMeetingId}"
			// 当前登录人ID
			var fdCurUserId = "${fdCurUserId}";
			// 回执结果
			var fdOptType = $('[name="fdOperateType"]:checked');
			
			// 为空或者不参加不校验
			if (!fdOptType || fdOptType.val() == "02") {
				return false;
			}
			
			// 实际参与人
			var docAttendId = $("[name='docAttendId']").val();
			
			// 邀请别人参加
			var fdAttendOtherIds = "";
			if (fdOptType && fdOptType.val() == "05") {
				fdAttendOtherIds = $("[name='attendOtherIds']").val();
			}
			
			if (!docAttendId) {
				docAttendId = fdCurUserId;
			}
			
			var attendOtherIdArray = fdAttendOtherIds ? fdAttendOtherIds.split(';') : [];
			
			var personArray=[];
			personArray = personArray.concat(attendOtherIdArray);
			personArray.push(docAttendId);
			var personIds = personArray.join(';');
			var flag = false;
			$.ajax({
	   			url:"${KMSS_Parameter_ContextPath}km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=checkIsSameTime",
				type:"GET",   
				async:false,    //用同步方式 
				data:{
					fdMeetingId : fdMeetingId,
					personIds : personIds
				},
				 success: function(data){
					 data = eval('(' + data + ')');
					 if (data.flag == "true") {
						 window.samePerson = data.fdPersonName;
						 flag = true;
					 }
				 }
	   		});
	   		return flag;
    	}
    });
</script>