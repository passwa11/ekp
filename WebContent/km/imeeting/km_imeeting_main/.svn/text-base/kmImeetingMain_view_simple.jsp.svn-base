<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<template:include ref="default.view" sidebar="no">
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/resource/css/view_simple.css" />
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/resource/css/edit_simple.css" />
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/resource/css/view.css?s_cache=${MUI_Cache}" />
	</template:replace>
	
	<template:replace name="title">
		<c:out value="${ kmImeetingMainForm.fdName} - ${ lfn:message('km-imeeting:table.kmImeetingMain') }"></c:out>
	</template:replace>
	<%--操作栏--%>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<c:if test="${kmImeetingMainForm.docStatusFirstDigit=='3'}">
			<%--发送会议通知，条件：1、通知类型为手动通知 2、未发送会议通知 3、会议未开始 --%>
			<kmss:auth requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=sendNotify&fdId=${JsParam.fdId}">
				<c:if test="${kmImeetingMainForm.fdNotifyType=='2' && kmImeetingMainForm.isNotify!='true' && isBegin==false}">
					<ui:button id="sendNotify" order="1" title="${lfn:message('km-imeeting:kmImeetingMain.createStep.sendNotify') }" 
						text="${lfn:message('km-imeeting:kmImeetingMain.createStep.sendNotify') }"   onclick="sendNotify();">
					</ui:button>
				</c:if>
			</kmss:auth>
			<%-- 催办会议，条件：1、已发送会议通知 2、会议未开始  --%>
			<c:if test="${kmImeetingMainForm.isNotify==true && isBegin==false}">
				<kmss:auth
					requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=hastenMeeting&fdId=${JsParam.fdId}" requestMethod="GET">
					<ui:button order="1" title="${ lfn:message('km-imeeting:kmImeeting.btn.hastenMeeting') }"
						 text="${ lfn:message('km-imeeting:kmImeeting.btn.hastenMeeting') }" onclick="showHastenMeeting()">
					</ui:button>
				</kmss:auth>
			</c:if>
			</c:if>
			 <%-- 编辑文档 --%> 
			<c:if test="${kmImeetingMainForm.docStatus!='00' && kmImeetingMainForm.docStatus!='30'&& kmImeetingMainForm.docStatus!='41'}">
				 <kmss:auth requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=edit&fdId=${JsParam.fdId}" requestMethod="GET">
				     <ui:button order="3" text="${ lfn:message('button.edit') }"  onclick="Com_OpenWindow('${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=edit&fdId=${JsParam.fdId}','_self');">
					 </ui:button>
				</kmss:auth>
			</c:if>
			<%-- 删除文档 --%>
			<kmss:auth requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=delete&fdId=${JsParam.fdId}" requestMethod="GET">
				<ui:button order="4" text="${ lfn:message('button.delete') }"  onclick="Delete();"></ui:button>
			</kmss:auth>
			
			<ui:button text="${ lfn:message('button.close') }" order="5"  onclick="Com_CloseWindow()"></ui:button>
		</ui:toolbar>
	</template:replace>
	
	<%--路径--%>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"  id="categoryId">  
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('km-imeeting:module.km.imeeting') }" href="/km/imeeting/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('km-imeeting:table.kmImeetingMain') }" href="/km/imeeting/#j_path=/kmImeeting_fixed&except=docStatus:00" target="_self"></ui:menu-item>
		</ui:menu>
	</template:replace>
	
	<%--内容区--%>
	<template:replace name="content">
		<html:form action="/km/imeeting/km_imeeting_main/kmImeetingMain.do">
			<html:hidden property="fdId" />
			<%--回执提示栏显示条件：1、会议发布 2、会议未开始 3、会议已发通知 --%>
			<c:if test="${kmImeetingMainForm.docStatus=='30' and isBegin==false and kmImeetingMainForm.isNotify==true and kmImeetingMainForm.fdNeedFeedback ne 'false'}">
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
			</c:if>
			<c:if test="${kmImeetingMainForm.docStatus=='30' and isBegin==false and kmImeetingMainForm.isNotify==true }">
				<%--组织人承接工作提示信息--%>
				<c:if test="${empty param.type && emccFlag == true && emccOpt == 'EmccDone' }">
					<div class="lui_metting_notice_container">
						<i class="lui_icon_s icon_success"></i>
						<span class="tips"><bean:message bundle="km-imeeting" key="kmImeeting.emcc.tip"/></span>
					</div>
				</c:if>
				<%--未承接工作安排--%>
				<c:if test="${ empty param.type && emccFlag == true && emccOpt == 'UnEmccDone' && kmImeetingMainForm.docStatus ne '41' }">
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
			<div class="meeting_content_frame">
				<p class="meeting_title">
					<%-- 会议标题 --%>
					<c:out value="${kmImeetingMainForm.fdName }" />
					<%-- 会议状态 --%>
					<div class="meeting_status">
						<%--未召开--%>
						<c:if test="${kmImeetingMainForm.docStatus=='30' && isBegin==false }">
							<bean:message bundle="km-imeeting" key="kmImeeting.status.publish.unHold"/>
						</c:if>
						<%--正在召开--%>
						<c:if test="${kmImeetingMainForm.docStatus=='30' && isBegin==true && isEnd==false }">
							<bean:message bundle="km-imeeting" key="kmImeeting.status.publish.holding"/>
						</c:if>
						<%--已召开--%>
						<c:if test="${kmImeetingMainForm.docStatus=='30' && isEnd==true }">
							<bean:message bundle="km-imeeting" key="kmImeeting.status.publish.hold"/>
						</c:if>
						<%--已取消--%>
						<c:if test="${kmImeetingMainForm.docStatus=='41' }">
							<bean:message bundle="km-imeeting" key="kmImeeting.status.cancel"/>
						</c:if>
					</div>
				</p>
				<%if(ISysAuthConstant.IS_AREA_ENABLED) { %>
				<div class="meeting_row">
					<label class="meeting_row_title">
						<bean:message key="sysAuthArea.authArea" bundle="sys-authorization" />
					</label>
					<div class="meeting_row_content">
						<xform:text style="width:97%" property="authAreaName" showStatus="view" />	
					</div>
				</div>
				<%} %>
				<%-- 会议时间 --%>
				<div class="meeting_row">
					<label class="meeting_row_title">
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdDate"/>
					</label>
					<div class="meeting_row_content">
						<c:out value="${kmImeetingMainForm.fdHoldDate }"></c:out>
						<%-- 会议历时 --%>
						<div class="meeting_duration">
							<label class="meeting_duration_title">
								<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHoldDuration"/>
							</label>
							<div class="meeting_duration_hour">${kmImeetingMainForm.fdHoldDurationHour }小时</div>
						</div>
					</div>
				</div>
				<div class="meeting_row">
					<label class="meeting_row_title">
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdNeedFeedback" />
					</label>
					<div class="meeting_row_content">
						<ui:switch property="fdNeedFeedback" showType="show" checked="${ kmImeetingMainForm.fdNeedFeedback}" />
					</div>
				</div>
				
				<%
				 	if(KmImeetingConfigUtil.isBoenEnable()|| KKUtil.isKkVideoMeetingEnable()){
				 %>
				  <c:if test="${kmImeetingMainForm.fdNeedFeedback  ne 'false'}">
				 <div class="meeting_row">
				 	<label class="meeting_row_title">
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdFeedBackDeadline"/>
					</label>
					<div class="meeting_row_content">
						<c:out value="${kmImeetingMainForm.fdFeedBackDeadline }"></c:out><font color="red">(请在该截止时间前做回执，否则将无法参会)</font>
					</div>
				</div>	
				</c:if>
				 <%} %>
				<div class="meeting_row">
					<label class="meeting_row_title">
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdNeedPlace" />
					</label>
					<div class="meeting_row_content">
						<ui:switch property="fdNeedPlace" showType="show" checked="${kmImeetingMainForm.fdNeedPlace}" />
					</div>
				</div>
				<c:if test="${kmImeetingMainForm.fdNeedPlace ne 'false'}">
					<%-- 会议地点 --%>
					<div class="meeting_row">
						<label class="meeting_row_title">
							<bean:message bundle="km-imeeting" key="kmImeetingMain.fdPlace"/>
						</label>
						<div class="meeting_row_content">
							<c:out value="${kmImeetingMainForm.fdPlaceName }"></c:out>
							<c:if test="${not empty kmImeetingMainForm.fdOtherPlace }">
								<c:out value="${kmImeetingMainForm.fdOtherPlace }"></c:out>
							</c:if>
						</div>
					</div>
				</c:if>
				<%-- 会议主持人 --%>
				<div class="meeting_row">
					<label class="meeting_row_title">
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHost"/>
					</label>
					<div class="meeting_row_content">
						<c:if test="${not empty kmImeetingMainForm.fdHostId }">
							<img class="meeting_host_img" src="<person:headimageUrl contextPath="true" personId="${kmImeetingMainForm.fdHostId }"/>" />
						</c:if>
						<span class="meeting_host_name">
							<xform:address propertyName="fdHostName" propertyId="fdHostId" orgType="ORG_TYPE_PERSON" style="width:95%;"></xform:address>
						</span>
					</div>
				</div>
				<%-- 参会人 --%>
				<div class="meeting_row">
					<label class="meeting_row_title">
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdAttendPersons"/>
					</label>
					<div class="meeting_row_content" style="word-break:break-all;">
						<xform:address  style="width:95%;" propertyId="fdAttendPersonIds" propertyName="fdAttendPersonNames" orgType="ORG_TYPE_PERSON" ></xform:address>
					</div>
				</div>
				<%-- 参会内容 --%>
				<div class="meeting_row">
					<label class="meeting_row_title">
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdMeetingAim"/>
					</label>
					<div class="meeting_row_content">
						<c:out value="${kmImeetingMainForm.fdMeetingAim }"></c:out>
					</div>
				</div>
				<%-- 阿里云视频会议口令 --%>
				<c:if test="${kmImeetingMainForm.fdIsVideo eq 'true' and kmImeetingMainForm.docStatus ne '41' and canEnterAliMeeting eq 'true' and not empty fdMeetingCode}">
					<div class="meeting_row">
						<label class="meeting_row_title">
							会议口令
						</label>
						<div class="meeting_row_content">
							<c:out value="${fdMeetingCode}"></c:out>
						</div>
					</div>
				</c:if>
				<%-- 更多信息 --%>
				<div class="lui_opt_more">
					<a class="com_help lui_arrowDn">
						<bean:message bundle="km-imeeting"  key="kmImeetingMain.show.tip.addMore"/>
					</a>
				</div>
				<div class="tb_simple_more_container">
					<div class="meeting_row">
						<label class="meeting_row_title">
							<bean:message bundle="km-imeeting" key="kmImeetingMain.fdSummaryInputPerson"/>
						</label>
						<div class="meeting_row_content">
							<c:out value="${kmImeetingMainForm.fdSummaryInputPersonName }"></c:out>
						</div>
					</div>
					<div class="meeting_row">
						<label class="meeting_row_title">
							<bean:message bundle="km-imeeting" key="kmImeetingTemplate.fdEmcee"/>
						</label>
						<div class="meeting_row_content">
							<c:out value="${kmImeetingMainForm.fdEmceeName }"></c:out>
						</div>
					</div>
					<div class="meeting_row">
						<label class="meeting_row_title">
							<bean:message bundle="km-imeeting" key="kmImeetingMain.docDept"/>
						</label>
						<div class="meeting_row_content">
							<c:out value="${kmImeetingMainForm.docDeptName }"></c:out>
						</div>
					</div>
				</div>
				<%-- 会议历史操作信息 --%>
				<div style="display: none;">
					<c:forEach items="${kmImeetingMainForm.kmImeetingMainHistoryForms}"  var="kmImeetingMainHistoryItem" varStatus="vstatus">
						<input type="hidden" name="kmImeetingMainHistoryForms[${vstatus.index}].fdId" value="${kmImeetingMainHistoryItem.fdId}" />
					</c:forEach>
				</div>
				<%-- 提交按钮 --%>
				<div class="meeting_toolbar">	
					<c:if test="${not empty vedioUrl  && canEnter && kmImeetingMainForm.docStatusFirstDigit=='3' }">		
						<ui:button id="enterButton" text="${lfn:message('km-imeeting:kmImeeting.btn.enterMeeting') }" order="2" onclick="window.open('${vedioUrl}','_self');"></ui:button>
					</c:if>
					<c:if test="${isEnd && kmImeetingMainForm.docStatusFirstDigit=='3' }">		
						<ui:button id="enterButton" text="${lfn:message('km-imeeting:kmImeeting.btn.finishMeeting') }" order="3" ></ui:button>
					</c:if>
					<c:if test="${kmImeetingMainForm.docStatusFirstDigit=='4' }">		
						<ui:button id="enterButton" text="${lfn:message('km-imeeting:kmImeeting.btn.cancleMeeting') }" order="4"></ui:button>
					</c:if>
					<c:if test="${kmImeetingMainForm.docStatusFirstDigit=='3'}">
						<div class="meeting_inner_toolbar">
							<%-- 会议提前结束，条件：1、已发送会议通知 2、正在召开的会议 --%>
							<c:if test="${isBegin==true && isEnd==false}">
								<kmss:auth
									requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=earlyEndMeeting&fdId=${JsParam.fdId}"
									requestMethod="GET">
									<a class="meeting_btn meeting_btn_change" href="javascript:window.earlyEndMeeting();">
										<bean:message bundle="km-imeeting" key="kmImeeting.btn.earlyEnd"/>
									</a>
								</kmss:auth>
							</c:if>
							<%-- 会议变更 --%>
							<c:if test="${kmImeetingMainForm.isNotify==true && isBegin==false}">
							<kmss:auth
								requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=changeMeeting&fdId=${JsParam.fdId}"
								requestMethod="GET">
								<a class="meeting_btn meeting_btn_change" href="javascript:window.updateChange();">
									<bean:message bundle="km-imeeting" key="kmImeetingMain.opt.change"/>
								</a>
							</kmss:auth>
							</c:if>
							<c:if test="${kmImeetingMainForm.isNotify==true && isBegin==false}">
							<kmss:auth
								requestURL="/km/imeeting/km_imeeting_main/kmImeetingMainCancel.do?method=cancelMeeting&fdId=${JsParam.fdId}" requestMethod="GET">
								<%-- 会议取消 --%>
								<a class="meeting_btn meeting_btn_cancel" href="javascript:window.showCancelMeeting();">
									<bean:message bundle="km-imeeting" key="kmImeetingMain.cancelMeeting"/>
								</a>
							</kmss:auth>
							</c:if>
						</div>
					</c:if>
				</div>
			</div>
			<ui:tabpage expand="false">
				<c:import url="/km/imeeting/km_imeeting_main/kmImeetingMain_view_include.jsp" charEncoding="UTF-8">
					<c:param name="hasTemplate" value="false"></c:param>
				</c:import>	
			</ui:tabpage>	
		</html:form>
		<%@ include file="/km/imeeting/km_imeeting_main/kmImeetingMain_viewKkVideo_include.jsp"%>	
	</template:replace>
	
</template:include>	
<script type="text/javascript">
seajs.use(['km/imeeting/resource/js/dateUtil','lui/dialog'],function(util,dialog){
	LUI.ready(function(){
		var button = LUI('enterButton');
		if(button){
			var now = '${now}',
				fdHoldDate = util.parseDate('${kmImeetingMainForm.fdHoldDate}').getTime(),
				fdFinishDate = util.parseDate('${kmImeetingMainForm.fdFinishDate}').getTime();
			button.setDisabled(true);
			if('${kmImeetingMainForm.docStatusFirstDigit}' == '4'){
				return;
			}
			if('${isEnd}'== 'true' && '${kmImeetingMainForm.docStatusFirstDigit}' == '3'){
				return;
			}
			
			if(now > fdFinishDate){
				return;
			}
			var _durHoldDate = fdHoldDate - now - 45 * 60 * 1000 > 0 ? fdHoldDate - now - 45 * 60 * 1000 : 0,
				_durFinishDate = fdFinishDate - now;
			setTimeout(function(){
				button.setDisabled(false);
				setTimeout(function(){
					button.setDisabled(true);
				},_durFinishDate);
			},_durHoldDate);
		}
	});
	
	var $help = $('.com_help'),
	$moreInfoContainer = $('.tb_simple_more_container');
	//完善更多信息事件
	$help.on('click',function(){
		$moreInfoContainer.slideToggle();
		$help.toggleClass('lui_arrowDn lui_arrowUp');
	});
	
	window.Delete=function(){
    	dialog.confirm("${lfn:message('page.comfirmDelete')}",function(flag){
	    	if(flag==true){
	    		Com_OpenWindow('${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=delete&fdId=${JsParam.fdId}','_self');
	    	}else{
	    		return false;
		    }
	    },"warn");
    };
	
	//会议变更
	window.updateChange = function(){
		Com_OpenWindow('${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=changeMeeting&fdId=${JsParam.fdId}','_self');
	};
	// 会议提前结束
	window.earlyEndMeeting = function(){
		var url = '/km/imeeting/km_imeeting_main/kmImeetingMain_earlyEnd_edit.jsp?fdId=${JsParam.fdId}';
		url = Com_SetUrlParameter(url,"fdHoldDate","${kmImeetingMainForm.fdHoldDate}");
		url = Com_SetUrlParameter(url,"fdFinishDate","${kmImeetingMainForm.fdFinishDate}");
		url = Com_SetUrlParameter(url,"fdEarlyFinishDate","${kmImeetingMainForm.fdEarlyFinishDate}");
		dialog.iframe(url,'${lfn:message("km-imeeting:kmImeetingMain.earlyEndMeeting")}',function(value){
			if(typeof value =="undefined"){
				location.reload();
			}
		},{width:600,height:400});
	};
	
	//会议催办
	window.showHastenMeeting=function(){
		dialog.iframe('/km/imeeting/km_imeeting_main_hasten/kmImeetingMainHasten.do?method=showHastenMeeting&meetingId=${JsParam.fdId}',
			'${lfn:message("km-imeeting:kmImeeting.btn.hastenMeeting")}',null,{width:600,height:360});
	};
	
	//会议取消
	window.showCancelMeeting=function(){
		dialog.iframe('/km/imeeting/km_imeeting_main_cancel/kmImeetingMainCancel.do?method=showCancelMeeting&meetingId=${JsParam.fdId}',
				'${lfn:message("km-imeeting:kmImeetingMain.cancelMeeting")}',function(value){
			if(typeof value =="undefined"){
				location.reload();
			}
		},{width:600,height:380});
	};
	
});
</script>	