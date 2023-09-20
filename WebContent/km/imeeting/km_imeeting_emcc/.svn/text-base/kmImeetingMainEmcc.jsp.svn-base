<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	Date now=new Date();
	Boolean isBegin=false,isEnd=false;
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
	request.setAttribute("isBegin", isBegin);
	request.setAttribute("isEnd", isEnd);
%>
<%
	request.setAttribute("isMeetingPage", true);
%>
<template:include ref="default.edit" sidebar="no" >
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/resource/css/view_simple.css" />
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/resource/css/view.css?s_cache=${MUI_Cache}" />
	</template:replace>
	<template:replace name="title">
		<c:out value="${kmImeetingMainForm.fdName} - ${ lfn:message('km-imeeting:table.kmImeetingMain') }"></c:out>
	</template:replace>
	
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"> 
			<c:if test="${isBegin == false }">
				<ui:button text="${ lfn:message('button.close') }" order="5"  onclick="Com_CloseWindow()"></ui:button>
			</c:if>
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
		<html:form action="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=updateEmcc&emccFlag=emcc" onsubmit="return checkEmcc();">
			<html:hidden property="fdId" />
			<p class="txttitle">
				<bean:message bundle="km-imeeting" key="kmImeetingMain.fdNotifyView" />
			</p>
			<br/>
			<%-- 如果会议开始不能进行回执提示 --%>
			<c:if test="${isBegin==true }">
				<div style="color: red;text-align: center;">
					<bean:message bundle="km-imeeting" key="kmImeetingMain.hasBegin.tip"/>
				</div>
			</c:if>
			<c:if test="${isBegin==false }">
				
				<%--组织人承接工作提示信息--%>
				<c:if test="${ emccFlag == true && emccOpt == 'EmccDone' }">
					<div class="lui_metting_notice_container">
						<i class="lui_icon_s icon_success"></i>
						<span class="tips"><bean:message bundle="km-imeeting" key="kmImeeting.emcc.tip"/></span>
					</div>
				</c:if>
				<%--未承接工作安排--%>
				<c:if test="${ emccFlag == true && emccOpt == 'UnEmccDone' }">
					<div class=lui_metting_notice_container>
						<label><input type="checkbox" id="emcc" class="boxEmcc"><bean:message bundle="km-imeeting" key="kmImeetingMain.fdNotify.emcc.tip" /></label> 
						<input type="submit" name="submit" value="<bean:message key='button.ok' />" class="lui_form_button" >
					</div>
				</c:if>
			</c:if>
			<c:choose>
				<c:when test="${kmImeetingMainForm.fdTemplateId == null || kmImeetingMainForm.fdTemplateId == ''}">
						<%@include file="/km/imeeting/km_imeeting_notify_letter/km_imeeting_notify_letter_simple.jsp"%>
				</c:when>
				<c:otherwise>
						<%@include file="/km/imeeting/km_imeeting_notify_letter/km_imeeting_notify_letter_admin.jsp"%>
				</c:otherwise>
			</c:choose>			
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
seajs.use(['sys/ui/js/dialog','km/imeeting/resource/js/dateUtil'], function(dialog,dateUtil) {
window.checkEmcc=function(){
	var flag = false;
	var isCheck = document.getElementById('emcc');
	if(isCheck.checked==true){
		flag = true;
	}
	if(flag==false){
		dialog.alert('<bean:message bundle="km-imeeting" key="kmImeetingMain.fdNotify.emcc.confirm" />');
		return false;
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
});
</script>


