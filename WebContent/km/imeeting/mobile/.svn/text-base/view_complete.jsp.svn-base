<%@page import="com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil"%>
<%@ page import="com.landray.kmss.sys.mobile.util.MobileUtil"%>
<%@ page import="com.landray.kmss.common.actions.RequestContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%
	request.setAttribute("userId", UserUtil.getUser().getFdId());
	//判断是否是企业微信客户端
	if(MobileUtil.THIRD_WXWORK == MobileUtil.getClientType(new RequestContext(request))){
		request.setAttribute("isQywx", true);
	}else{
		request.setAttribute("isQywx", false);
	}
%>
<template:include ref="mobile.view" compatibleMode="true">
		<template:replace name="csshead">
			<mui:cache-file name="mui-imeeting-view.css" cacheType="md5"/>
				<kmss:ifModuleExist path="/sys/task/">
				<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/task/mobile/resource/css/view.css" />
				</kmss:ifModuleExist>
		</template:replace>
	<template:replace name="head">
		<script type="text/javascript" src="${LUI_ContextPath}/sys/mobile/js/mui/device/weixin/wxAgentConfig.js"></script>
		<mui:min-file name="mui-imeeting.js"/>
	</template:replace>
	
	<template:replace name="loading">
		<c:import url="/km/imeeting/mobile/view_banner.jsp"  charEncoding="UTF-8">
			<c:param name="formBeanName" value="kmImeetingMainForm" />
			<c:param name="loading" value="true" />
		</c:import>
	</template:replace>
	
	<template:replace name="title">
		<c:if test="${not empty kmImeetingMainForm.fdName}">
			<c:out value="${ kmImeetingMainForm.fdName}"></c:out>
		</c:if>
		<c:if test="${empty kmImeetingMainForm.fdName}">
			<bean:message bundle="km-imeeting" key="module.km.imeeting"/>
		</c:if>
	</template:replace>
	<template:replace name="content">
		<div id="scrollView"  class="gray"
			data-dojo-type="mui/view/DocScrollableView"
			data-dojo-mixins="mui/list/_ViewPushAppendMixin">

			<%-- 会议历史操作信息 --%>
			<div style="display: none;">
				<c:forEach items="${kmImeetingMainForm.kmImeetingMainHistoryForms}"  var="kmImeetingMainHistoryItem" varStatus="vstatus">
					<input type="hidden" name="kmImeetingMainHistoryForms[${vstatus.index}].fdId" value="${kmImeetingMainHistoryItem.fdId}" />
				</c:forEach>
			</div>

			<c:import url="/km/imeeting/mobile/view_banner.jsp"  charEncoding="UTF-8">
				<c:param name="formBeanName" value="kmImeetingMainForm" />
			</c:import>
			
			<c:if test="${type=='admin' or type=='attend' or type=='cc' }">
				<div data-dojo-type="mui/fixed/Fixed" id="fixed">
					<div data-dojo-type="mui/fixed/FixedItem" class="ImeetingFixedItem">
						<%--切换页签--%>
						<div class="muiHeader">
							<c:choose>
								<c:when test="${kmImeetingMainForm.docStatusFirstDigit=='3' and kmImeetingMainForm.fdNeedFeedback eq 'true'}">
									<div
										data-dojo-type="mui/nav/MobileCfgNavBar" 
										data-dojo-props="defaultUrl:'/km/imeeting/mobile/view_nav.jsp?docStatus=${kmImeetingMainForm.docStatus }&fromSysAttend=${param.fromSysAttend }&fdMeetingId=${kmImeetingMainForm.fdId}' ">
									</div>
								</c:when>
								<c:otherwise>
									<div
										data-dojo-type="mui/nav/MobileCfgNavBar" 
										data-dojo-props="defaultUrl:'/km/imeeting/mobile/view_simple_nav.jsp?docStatus=${kmImeetingMainForm.docStatus }&fromSysAttend=${param.fromSysAttend }' ">
									</div>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
				
				<%--会议内容页签--%>
				<div id="contentView" data-dojo-type="dojox/mobile/View">
					<c:if test="${kmImeetingMainForm.docStatus=='30' and isBegin==false and kmImeetingMainForm.isNotify==true }">
						<c:if test="${empty param.type && emccFlag == true && emccOpt == 'UnEmccDone' }">
							<div class="muiAccordionPanelContentBottom" style="text-align: left;padding:1rem;">
								<button type="button" 
									class="muiAccordionPanelContentBtn" 
									onclick="updateEmcc()">
									<bean:message bundle="km-imeeting" key="mobile.oper.updateEmcc"/>
								</button>
							</div>
							<%-- <ul class="muiAccordionPanelContentBottom" data-dojo-type="sys/task/mobile/resource/js/OverflowTabBar">
						
								<li data-dojo-type="mui/tabbar/TabBarButton" class="muiAccordionPanelContentBtn" onclick="updateEmcc()"
									data-dojo-props="icon:'mui',
													colSize:2">${lfn:message('km-imeeting:mobile.oper.updateEmcc')}</li>
													
								<kmss:auth requestURL="/sys/task/sys_task_main/sysTaskMain.do?method=add" requestMethod="GET">
									<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtn muiBtnFeedback"
										data-dojo-props="icon:'mui mui-task-icon',colSize:2,href:'/sys/task/sys_task_main/sysTaskMain.do?method=add&fdModelId=${JsParam.fdId}&fdModelName=com.landray.kmss.km.imeeting.model.KmImeetingMain'">${lfn:message('sys-task:tag.task')}</li>
								</kmss:auth>
								<kmss:ifModuleExist path="/km/vote">
									<c:import url="/km/vote/mobile/import/button.jsp" charEncoding="UTF-8">
										<c:param name="fdModelId" value="${kmImeetingMainForm.fdId}" />
										<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain" />
									</c:import>
								</kmss:ifModuleExist> 
							</ul> --%>
						</c:if>
					</c:if>
					
					<div data-dojo-type="mui/panel/AccordionPanel" data-dojo-props="fixed:false">
						<%--会议目的--%>
						<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('km-imeeting:kmImeetingMain.fdMeetingAim') }',icon:''">
							<xform:textarea  property="fdMeetingAim" mobile="true" ></xform:textarea>
						</div>
						
						<c:if test="${not empty fdMeetingCode and canEnterAliMeeting eq 'true'}">
							<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('km-imeeting:kmImeetingMain.fdMeetingCode') }',icon:''">
								<div class="txtContent">
									<div class="muiMeetingAimDiv">
										<c:out value="${fdMeetingCode}"></c:out>
									</div>
								</div>
							</div>
						</c:if>
						
						<%--记录资料--%>
						<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('km-imeeting:kmImeetingMain.attachment.temp') }',icon:''">
							<div class="txtContent">
								<table class="muiSimple">
									<!-- 纪要人员 -->
									<tr data-celltr="true">
										<td class="muiTitle">
											<bean:message key="kmImeetingMain.fdSummaryInputPerson" bundle="km-imeeting"/>
										</td>
										<td>
											<span class="title">
												<c:out value="${kmImeetingMainForm.fdSummaryInputPersonName }"></c:out>
											</span>
										</td>
									</tr>
									<!-- 纪要完成时间 -->
									<tr data-celltr="true">
										<td class="muiTitle">
											<bean:message key="kmImeetingMain.fdSummaryCompleteTime" bundle="km-imeeting"/>
										</td>
										<td>
											<span class="title">
												<c:out value="${kmImeetingMainForm.fdSummaryCompleteTime }"></c:out>
												<label>
													<input type="checkbox" disabled <c:if test="${kmImeetingMainForm.fdIsHurrySummary == 'true'}">checked</c:if>>
													<bean:message bundle="km-imeeting" key="kmImeetingMain.fdIsHurrySummary" />
												</label>
												<c:if test="${kmImeetingMainForm.fdIsHurrySummary == 'true'}">
													<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHurrySummaryDay.0" />	
													<bean:write name="kmImeetingMainForm" property="fdHurryDate" />
													<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHurrySummaryDay.1" />
												</c:if>	
											</span>
										</td>
									</tr>
									<!-- 会场布置要求 -->
									<tr data-celltr="true">
										<td class="muiTitle">
											<bean:message key="kmImeetingMain.fdArrange" bundle="km-imeeting"/>
										</td>
										<td>
											<span class="title">
												<c:out value="${kmImeetingMainForm.fdArrange }"></c:out>
											</span>
										</td>
									</tr>
									<!-- 备注 -->
									<tr data-celltr="true">
										<td class="muiTitle">
											<bean:message key="kmImeetingMain.fdRemark" bundle="km-imeeting"/>
										</td>
										<td>
											<span class="title">
												<c:out value="${kmImeetingMainForm.fdRemark }"></c:out>
											</span>
										</td>
									</tr>
								</table>
							</div>
						</div>
						
						<c:if test="${not empty kmImeetingMainForm.fdTemplateId}">
						<%--会议议程--%>
						<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('km-imeeting:kmImeetingMain.kmImeetingAgendas') }',icon:''">
							<div class="txtContent">
						 		<c:choose>
				 					<c:when test="${kmImeetingMainForm.fdIsTopic eq 'true'}">
				 						<%
				 							request.setAttribute("isMeetingPage", true);
				 						%>
						 				<%@include file="/km/imeeting/mobile/agenda_viewTopic.jsp"%>
						 		 	</c:when>
						 		 	<c:otherwise>
						 				<%@include file="/km/imeeting/mobile/agenda_view.jsp"%>
						 			</c:otherwise>
						 		</c:choose>	
		        				<br/>
		        				<c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="kmImeetingMainForm" />
									<c:param name="fdKey" value="attachment" />
								</c:import>
		        				<%-- 录入会议纪要，条件：1、已发送会议通知 2、录入人才可录入 
								<c:if test="${kmImeetingMainForm.isNotify==true && kmImeetingMainForm.fdSummaryFlag=='false' && not empty kmImeetingMainForm.fdSummaryInputPersonId }">
									<kmss:authShow extendOrgIds="${kmImeetingMainForm.fdSummaryInputPersonId}" roles="SYSROLE_ADMIN">
										<kmss:auth
											requestURL="/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=operateSummary&meetingId=${JsParam.fdId}"
											requestMethod="GET">
										    <div class="muiReadButton" onclick="window.building();">
					        					<bean:message bundle="km-imeeting" key="mobile.kmImeetingSummary.create"/>
					        				</div>
										</kmss:auth>
									</kmss:authShow>
								</c:if>
		        				--%>
		        			</div>
						</div>
						</c:if>
						<%--查阅会议纪要--%>
        				<c:if test="${kmImeetingMainForm.docStatusFirstDigit=='3'}">
	        				<c:if test="${kmImeetingMainForm.fdSummaryFlag=='true' and not empty summaryId}">
								<kmss:auth
									requestURL="/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=view&fdId=${summaryId}"
									requestMethod="GET">
									<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('km-imeeting:kmImeetingMain.kmImeetingSummarys') }',icon:''">
										<div class="txtContent">
											<div class="muiReadButton" onclick="window.open('${LUI_ContextPath}/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=view&fdId=${summaryId}','_self')">
					        					<bean:message bundle="km-imeeting" key="mobile.kmImeetingSummary.view"/>
					        				</div>
					        			</div>
				        			</div>	
								</kmss:auth>
							</c:if>
        				</c:if>
						<kmss:ifModuleExist path="/sys/task/">
							<%-- 相关任务 --%>
							<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${ lfn:message('sys-task:sysTaskMain.relatedTask') }',icon:''">
								<div data-dojo-type="dojox/mobile/View">
									<ul data-dojo-type="sys/task/mobile/resource/js/list/SysTaskJsonStoreList"
										data-dojo-mixins="sys/task/mobile/resource/js/list/CalendarItemListMixin"
										data-dojo-props="url:'/sys/task/sys_task_main/sysTaskIndex.do?method=list&fdModelId=${kmImeetingMainForm.fdId}&fdModelName=com.landray.kmss.km.imeeting.model.KmImeetingMain',lazy:false">
									</ul>
									<div class="muiAccordionPanelContentBottom">
										<kmss:auth requestURL="/sys/task/sys_task_main/sysTaskMain.do?method=add" requestMethod="GET">
											<button type="button"
												class="muiAccordionPanelContentBtn"
												onclick="window.open('${LUI_ContextPath }/sys/task/sys_task_main/sysTaskMain.do?method=add&fdModelId=${JsParam.fdId}&fdModelName=com.landray.kmss.km.imeeting.model.KmImeetingMain', '_self')">
												+<bean:message bundle="km-imeeting" key="mobile.button.task.plan"/>
											</button>
										</kmss:auth>
									</div>
								</div>
							</div>
						</kmss:ifModuleExist>
						<%-- 相关投票 --%>
						<kmss:ifModuleExist path="/km/vote">
							<c:import url="/km/vote/mobile/import/view.jsp" charEncoding="UTF-8">
								<c:param name="fdModelId" value="${kmImeetingMainForm.fdId}" />
								<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain" />
								<c:param name="showCreate" value="true" />
							</c:import>
						</kmss:ifModuleExist>
					</div>
				</div>
				
				<%--会议人员页签--%>
				<div id="personView" data-dojo-type="dojox/mobile/View">
					<div data-dojo-type="mui/panel/AccordionPanel" data-dojo-props="fixed:false">
						<%--参加人员 --%>
						<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('km-imeeting:kmImeetingMain.fdAttendPersons') }',icon:''">
							<div class="txtContent">
								<ul class="muiMeetingList attendPersonContainer">
									<c:if test="${not empty kmImeetingMainForm.fdAttendPersonIds }">
										<li>
											<div data-dojo-type="mui/person/PersonList"
												data-dojo-props="title:'<bean:message bundle="km-imeeting"  key="mobile.kmImeetingMain.person.inner"/>',detailTitle:'<bean:message bundle="km-imeeting"  key="kmImeetingMain.fdAttendPersons"/>',personId:'${kmImeetingMainForm.fdAttendPersonIds }'">
											</div>
										</li>
									</c:if>
									<c:if test="${not empty kmImeetingMainForm.fdOtherAttendPerson }">
										<li>
											<strong>
												<bean:message bundle="km-imeeting" key="kmImeetingMain.fdOtherPerson"/>
											</strong>
											<div class="staffList">
												<c:out value="${kmImeetingMainForm.fdOtherAttendPerson }"></c:out>
											</div>
										</li>
									</c:if>
								</ul>
							</div>
						</div>
						<c:if test="${not empty kmImeetingMainForm.fdTemplateId}">
						<%--列席人员--%>
						<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('km-imeeting:kmImeetingMain.fdParticipantPersons') }',icon:''">
							<div class="txtContent">
								<ul class="muiMeetingList participantPersonContainer">
									<c:if test="${not empty kmImeetingMainForm.fdParticipantPersonIds }">
										<li>
											<div data-dojo-type="mui/person/PersonList"
												data-dojo-props="title:'<bean:message bundle="km-imeeting"  key="mobile.kmImeetingMain.person.inner"/>',detailTitle:'<bean:message bundle="km-imeeting"  key="kmImeetingMain.fdParticipantPersons"/>',personId:'${kmImeetingMainForm.fdParticipantPersonIds }'">
											</div>
										</li>
									</c:if>
									<c:if test="${not empty kmImeetingMainForm.fdOtherParticipantPerson }">
										<li>
											<strong>
												<bean:message bundle="km-imeeting" key="kmImeetingMain.fdOtherPerson"/>
											</strong>
											<div class="staffList">
												<c:out value="${kmImeetingMainForm.fdOtherParticipantPerson }"></c:out>
											</div>
										</li>
									</c:if>
								</ul>
							</div>
						</div>
						
						<%--抄送人员 --%>
						<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('km-imeeting:kmImeetingSummary.fdCopyToPersons') }',icon:''">
							<div class="txtContent">
								<ul class="muiMeetingList ccPersonContainer">
									<c:if test="${not empty kmImeetingMainForm.fdCopyToPersonIds }">
										<li>
											<div data-dojo-type="mui/person/PersonList"
												data-dojo-props="title:'<bean:message bundle="km-imeeting"  key="mobile.kmImeetingMain.person.inner"/>',detailTitle:'<bean:message bundle="km-imeeting"  key="kmImeetingMain.fdCopyToPersons"/>',personId:'${kmImeetingMainForm.fdCopyToPersonIds }'">
											</div>
										</li>
									</c:if>
									<c:if test="${not empty kmImeetingMainForm.fdOtherCopyToPerson }">
										<li>
											<strong>
												<bean:message bundle="km-imeeting" key="kmImeetingMain.fdOtherPerson"/>
											</strong>
											<div class="staffList">
												<c:out value="${kmImeetingMainForm.fdOtherCopyToPerson }"></c:out>
											</div>
										</li>
									</c:if>
								</ul>
							</div>
						</div>
						</c:if>
					</div>
				</div>
				
				<%--会议回执页签--%>
				<c:set var="viewFeedback" value="false"></c:set>
				<kmss:auth requestURL="/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=getFeedbackList&meetingId=${kmImeetingMainForm.fdId}" requestMethod="GET">
				<c:set var="viewFeedback" value="true"></c:set>
				<div id="feedbackListView" data-dojo-type="dojox/mobile/View">
					<div class="muiMeetingFeedbackHeader gray">
						<%-- 有通知数才做筛选 --%>
							<div data-dojo-type="km/imeeting/mobile/resource/js/CriteriaButtonGroup"
								 data-dojo-props="icon:'mui mui-filter',label:'<bean:message bundle="km-imeeting"  key="mobile.kmImeetingMainFeedback.criteria"/>',align:'left'">
								 
								<div data-dojo-type="km/imeeting/mobile/resource/js/CriteriaButton"
					    			 data-dojo-props="criteriaType:''">
					    			<bean:message bundle="km-imeeting"  key="mobile.kmImeetingMainFeedback.criteria.all"/>	 
					    		</div>
					    			 
					    		<div data-dojo-type="km/imeeting/mobile/resource/js/CriteriaButton"
					    			 data-dojo-props="criteriaType:'04'">
					    			<bean:message bundle="km-imeeting"  key="enumeration_km_imeeting_main_feedback_fd_operate_type_noopt"/>	 
					    		</div>
					    			 
					    		<div data-dojo-type="km/imeeting/mobile/resource/js/CriteriaButton"
					    			 data-dojo-props="criteriaType:'01'">
					    			 <bean:message bundle="km-imeeting"  key="enumeration_km_imeeting_main_feedback_fd_operate_type_attend"/>	 
					    		</div>
					    			 
					    		<div data-dojo-type="km/imeeting/mobile/resource/js/CriteriaButton"
					    			 data-dojo-props="criteriaType:'02'">
					    			<bean:message bundle="km-imeeting"  key="enumeration_km_imeeting_main_feedback_fd_operate_type_unattend"/>	 	 
					    		</div>
					    			 
					    		<div data-dojo-type="km/imeeting/mobile/resource/js/CriteriaButton"
					    			 data-dojo-props="criteriaType:'03'">
					    			<bean:message bundle="km-imeeting"  key="enumeration_km_imeeting_main_feedback_fd_operate_type_proxy"/>	 	 
					    		</div>
					    	</div>
					    	
					</div>
					<ul id="muiMeetingFeedbackList" class="muiMeetingFeedbackList" 
			    		data-dojo-type="km/imeeting/mobile/resource/js/list/FeedbackJsonStoreList" 
			    		data-dojo-mixins="km/imeeting/mobile/resource/js/list/FeedbackItemListMixin"
			    		data-dojo-props="url:'/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=list&meetingId=${kmImeetingMainForm.fdId }',lazy:false">
					</ul>
				</div>
				</kmss:auth>		
				<c:if test="${viewFeedback ne true}">
					<script type="text/javascript">
						require(['dojo/topic','dojo/ready','dijit/registry'],function(topic,ready,registry){
							ready(function(){
								topic.publish('/mui/list/pushDomHide',registry.byId('contentView'));
							});
						});
					</script>
				</c:if>		
				<%--会议流程信息 --%>
				<div id="processView" data-dojo-type="dojox/mobile/View">
					<div class="ProcessAccordionPanel" data-dojo-type="mui/panel/AccordionPanel" data-dojo-props="fixed:false">
						<div class="muiAccordionPanelContent muiImeetingPanelContent">
							<c:import url="/sys/lbpmservice/mobile/lbpm_audit_note/import/view.jsp" charEncoding="UTF-8">
								<c:param name="fdModelId" value="${kmImeetingMainForm.fdId }"/>
								<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain"/>
								<c:param name="formBeanName" value="kmImeetingMainForm"/>
							</c:import>
						</div>
					</div>
				</div>
				
				<kmss:ifModuleExist path="/sys/attend/">
				<div id="attendView"  data-dojo-type="dojox/mobile/View">
					<c:import url="/sys/attend/mobile/import/view.jsp" charEncoding="UTF-8">
						<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain"></c:param>
						<c:param name="fdModelId" value="${kmImeetingMainForm.fdId}"></c:param>
					</c:import>
				</div>
				</kmss:ifModuleExist>
			</c:if>
	
			<c:if test="${type=='assist' }">
				<div id="contentView" data-dojo-type="dojox/mobile/View">
					<div class="AssistAccordionPanel" data-dojo-type="mui/panel/AccordionPanel" data-dojo-props="fixed:false">
						<%--会议辅助服务--%>
						<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('km-imeeting:kmImeetingMain.kmImeetingDevices') }',icon:''">
							<div class="txtContent" style="padding: 1rem;">
								<c:out value="${kmImeetingMainForm.kmImeetingDeviceNames}"></c:out>
							</div>
						</div>
						<%--会议布置要求--%>
						<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('km-imeeting:kmImeetingMain.fdArrange') }',icon:''">
							<div class="txtContent" style="padding: 1rem;">
								<xform:textarea property="fdArrange" mobile="true"></xform:textarea>
							</div>
						</div>
						<%-- 会议协助人 --%>
						<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('km-imeeting:kmImeetingMain.fdAssistPersons') }',icon:''">
							<div class="txtContent" style="padding: 1rem;">
								<ul class="muiMeetingList assistPersonContainer">
									<c:if test="${not empty kmImeetingMainForm.fdAssistPersonIds }">
										<li>
											<div data-dojo-type="mui/person/PersonList"
												data-dojo-props="title:'<bean:message bundle="km-imeeting"  key="mobile.kmImeetingMain.person.inner"/>',detailTitle:'<bean:message bundle="km-imeeting"  key="kmImeetingMain.fdAssistPersons"/>',personId:'${ kmImeetingMainForm.fdAssistPersonIds}'">
											</div>
										</li>
									</c:if>
									<c:if test="${not empty kmImeetingMainForm.fdOtherAssistPersons }">
										<li>
											<strong>
												<bean:message bundle="km-imeeting" key="kmImeetingMain.fdOtherPerson"/>
											</strong>
											<div class="staffList">
												<c:out value="${kmImeetingMainForm.fdOtherAssistPersons }"></c:out>
											</div>
										</li>
									</c:if>
								</ul>
							</div>
						</div>
						<kmss:ifModuleExist path="/sys/task/">
						<%-- 相关任务 --%>
						<div data-dojo-type="mui/panel/Content" title="${ lfn:message('sys-task:sysTaskMain.relatedTask') }">
							<div data-dojo-type="dojox/mobile/View">
								<ul data-dojo-type="sys/task/mobile/resource/js/list/SysTaskJsonStoreList" 
			    					data-dojo-mixins="sys/task/mobile/resource/js/list/CalendarItemListMixin"
			    					data-dojo-props="url:'/sys/task/sys_task_main/sysTaskIndex.do?method=list&fdModelId=${kmImeetingMainForm.fdId}&fdModelName=com.landray.kmss.km.imeeting.model.KmImeetingMain',lazy:false">
								</ul>
							</div>
						</div>
						</kmss:ifModuleExist>
					</div>
				</div>
			<script type="text/javascript">
				require(['dojo/topic','dojo/ready','dijit/registry'],function(topic,ready,registry){
					ready(function(){
						topic.publish('/mui/list/pushDomHide',registry.byId('contentView'));
					});
				});
			</script>
			</c:if>
			<c:choose>
			<%-- 无模板会议（极简形式） --%>
			<c:when test="${kmImeetingMainForm.fdTemplateId == null || kmImeetingMainForm.fdTemplateId == ''}">
				<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"' id="tabBar">
					<!-- 开启伯恩之后才会有会议截止时间 -->
					<c:choose>
						<%--没过回执截止时间且已发送会议通知单的会议才显示回执按钮--%>
						<c:when test="${isFeedBackDeadline eq 'noFeedBackDeadline'}">
							<c:if test="${kmImeetingMainForm.docStatus=='30' and isBegin==false and kmImeetingMainForm.isNotify==true and kmImeetingMainForm.fdNeedFeedback eq 'true' }">
								<%--是参会人才显示回执按钮--%>
								<c:if test="${not empty optType and optType =='04'}">
									<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " onclick="attend();"
										data-dojo-props="colSize:2">
										<bean:message bundle="km-imeeting" key="enumeration_km_imeeting_main_feedback_fd_operate_type_attend"/>
									</li>
									<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " onclick="unAttend();"
										data-dojo-props="colSize:2">
										<bean:message bundle="km-imeeting" key="enumeration_km_imeeting_main_feedback_fd_operate_type_unattend"/>
									</li>
								</c:if>
							</c:if>
						</c:when>
						<c:otherwise>
							<%--未召开且已发送会议通知单的会议才显示回执按钮--%>
							<c:if test="${kmImeetingMainForm.docStatus=='30' and isFeedBackDeadline==false and kmImeetingMainForm.isNotify==true and kmImeetingMainForm.fdNeedFeedback eq 'true' }">
								<%--是参会人才显示回执按钮--%>
								<c:if test="${not empty optType and optType =='04'}">
									<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " onclick="attend();"
										data-dojo-props="colSize:2">
										<bean:message bundle="km-imeeting" key="enumeration_km_imeeting_main_feedback_fd_operate_type_attend"/>
									</li>
									<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " onclick="unAttend();"
										data-dojo-props="colSize:2">
										<bean:message bundle="km-imeeting" key="enumeration_km_imeeting_main_feedback_fd_operate_type_unattend"/>
									</li>
								</c:if>
							</c:if>
						</c:otherwise>
					</c:choose>
					<kmss:auth requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=earlyEndMeetingMobile&fdId=${kmImeetingMainForm.fdId}" requestMethod="GET">
					<c:if test="${kmImeetingMainForm.docStatus=='30' && isBegin==true && isEnd==false }">
					<!-- 提前结束会议（正在召开） -->
					<div data-dojo-type="mui/tabbar/TabBarButton"
						data-dojo-props='label:"${lfn:message('km-imeeting:kmImeeting.btn.earlyEnd')}",modelId:"${kmImeetingMainForm.fdId}"' 
						onclick="earlyEndMeeting();">
					</div>
					</c:if>
					</kmss:auth>
				 	<c:import url="/sys/bookmark/mobile/import/view.jsp" charEncoding="UTF-8">
					  <c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain"></c:param>
					  <c:param name="fdModelId" value="${kmImeetingMainForm.fdId}"></c:param>
					  <c:param name="fdSubject" value="${kmImeetingMainForm.fdName}"></c:param>
					  <c:param name="label" value="${lfn:message('sys-bookmark:button.bookmark') }"></c:param>
				      <c:param name="showOption" value="label"></c:param>
					</c:import>
					
					<!-- 视频云会议 -->
					<c:if test="${not empty vedioUrl && canEnter}">
						<li id="enterButton" class="muiBtnNext"  data-dojo-type="mui/tabbar/TabBarButton" 
						  	data-dojo-props="colSize:2" onclick="window.enterMeeting('${vedioUrl}');"><bean:message bundle="km-imeeting" key="kmImeeting.btn.enterMeeting"/></li>
					</c:if> 
					<c:if test="${not empty vedioUrl && isEnd}">
						<li id="enterButton" class="muiBtnNext"  data-dojo-type="mui/tabbar/TabBarButton" 
						  	data-dojo-props="colSize:2" onclick="window.enterMeeting('${vedioUrl}');"><bean:message bundle="km-imeeting" key="kmImeeting.btn.finishMeeting"/></li>
					</c:if>
					<c:if test="${not empty vedioUrl && (!isEnd) && (!canEnter)}">
						<li id="enterButton" class="muiBtnNext"  data-dojo-type="mui/tabbar/TabBarButton" 
						  	data-dojo-props="colSize:2" onclick="window.enterMeeting('${vedioUrl}');"><bean:message bundle="km-imeeting" key="kmImeeting.btn.cancleMeeting"/></li>
					</c:if>	
					
					<!-- 钉钉电话会议 -->
					<c:if test="${!isEnd && canEnter}">
						<li id="dingCallButton" class="muiBtnNext"  data-dojo-type="mui/tabbar/TabBarButton" 
							  	data-dojo-props="colSize:2" onclick="window.callPhone('${kmImeetingMainForm.fdAttendPersonIds }', '${kmImeetingMainForm.fdHostId }');" style="display: none">电话会议</li>
					</c:if>
					
					<%-- 会议变更，条件：1、已发送会议通知 2、未录入纪要 3、会议未开始 --%>
					<c:if test="${kmImeetingMainForm.docStatus=='30' and isBegin==false}">
						<kmss:auth requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=changeMeeting&fdId=${kmImeetingMainForm.fdId}" requestMethod="GET">
							<c:set var="showMoreBtn" value="true"></c:set>
						</kmss:auth>
					</c:if>
					<%-- 会议取消，条件：1、创建者 2、会议未开始 --%>
					<c:if test="${kmImeetingMainForm.docStatus=='30' and isBegin==false}">
						<kmss:auth requestURL="/km/imeeting/km_imeeting_main/kmImeetingMainCancel.do?method=cancelMeeting&fdId=${kmImeetingMainForm.fdId}" requestMethod="GET">
							<c:set var="showMoreBtn" value="true"></c:set>
						</kmss:auth>
					</c:if>
					
					<!-- 开启伯恩后才有回执截止时间 -->
					<c:choose>
						<c:when test="${isFeedBackDeadline eq 'noFeedBackDeadline'}">
							<c:if test="${kmImeetingMainForm.docStatus=='30' and isBegin==false and kmImeetingMainForm.isNotify==true }">
								<%--是参会人才显示回执按钮--%>
								<c:if test="${not empty optType and optType =='04' and kmImeetingMainForm.fdNeedFeedback eq 'true' }">
									<c:set var="showMoreBtn" value="true"></c:set>
								</c:if>
							</c:if>
						</c:when>
						<c:otherwise>
							<c:if test="${kmImeetingMainForm.docStatus=='30' and isFeedBackDeadline==false and kmImeetingMainForm.isNotify==true }">
								<%--是参会人才显示回执按钮--%>
								<c:if test="${not empty optType and optType =='04' and kmImeetingMainForm.fdNeedFeedback eq 'true' }">
									<c:set var="showMoreBtn" value="true"></c:set>
								</c:if>
							</c:if>
						</c:otherwise>
					</c:choose>
					
					<c:if test="${showMoreBtn eq true && kmImeetingMainForm.docStatus !='41' }">
						 <li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtn muiBtnFeedback"  id="otherListBtn" data-dojo-props="">
						 	${lfn:message('km-imeeting:button.other')}
						 </li>
					</c:if>
				</ul>
			</c:when>
			<%-- 有模板会议（完整形式） --%>
			<c:otherwise>
				<template:include file="/sys/lbpmservice/mobile/import/tarbar.jsp" 
					  formName="kmImeetingMainForm"
					  viewName="lbpmView"
					  allowReview="true">
					<template:replace name="flowArea">
						<c:import url="/sys/bookmark/mobile/import/view.jsp" charEncoding="UTF-8">
							<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain"></c:param>
							<c:param name="fdModelId" value="${kmImeetingMainForm.fdId}"></c:param>
							<c:param name="fdSubject" value="${kmImeetingMainForm.fdName}"></c:param>
							<c:param name="label" value="${lfn:message('sys-bookmark:button.bookmark') }"></c:param>
							<c:param name="showOption" value="label"></c:param>
						</c:import>
											
						<%-- 编辑文档 --%> 
						<c:if test="${kmImeetingMainForm.docStatus!='00' and kmImeetingMainForm.docStatus!='30' and kmImeetingMainForm.docStatus!='41'}">
							<kmss:auth 
								requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=edit&fdId=${kmImeetingMainForm.fdId}" 
								requestMethod="GET">
								<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit muiBtnEdit" onclick="window.open('${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=edit&fdId=${kmImeetingMainForm.fdId }','_self')"
									data-dojo-props="colSize:2">
									<bean:message key="button.edit"/>
								</li>  
							</kmss:auth>
						</c:if>
					</template:replace>
					<template:replace name="publishArea">
						<!--判断是否有企业微信模块-->
						<kmss:ifModuleExist path="/third/weixin/">
							<c:if test="${isCanLiving == true}">
								<!--可查看直播按钮条件：会议未召开或者会议正在进行-->
								<c:if test="${((kmImeetingMainForm.docStatus=='30' && isBegin == false) ||(kmImeetingMainForm.docStatus=='30' && isDoing == true))}">
									<%--参会人--%>
									<c:if test="${isAttendPerson == true}">
										<c:if test="${haveLiving ==true}">
											<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " onclick="openMyLiving()"
												data-dojo-props="colSize:2">
													${lfn:message('km-imeeting:kmImeetingMain.watch.wxvidio')}
											</li>
										</c:if>
									</c:if>
									<%--主持人或者创建人--%>
									<c:if test="${canCreatePerson == true}">
										<c:choose>
											<c:when test="${haveLiving == true}">
												<%--观看直播--%>
												<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " onclick="openMyLiving()"
													data-dojo-props="colSize:2">
														${lfn:message('km-imeeting:kmImeetingMain.watch.wxvidio')}
												</li>
											</c:when>
											<c:otherwise>
												<%--创建直播--%>
												<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " onclick="createMyLiving()"
													data-dojo-props="colSize:2">
														${lfn:message('km-imeeting:kmImeetingMain.botton.vidio')}
												</li>
											</c:otherwise>
										</c:choose>
									</c:if>
								</c:if>
							</c:if>
						</kmss:ifModuleExist>
						<!-- 开启伯恩后才有回执截止时间 -->
						<c:choose>
							<c:when test="${isFeedBackDeadline eq 'noFeedBackDeadline'}">
								<%--未召开且已发送会议通知单的会议才显示回执按钮--%>
								<c:if test="${kmImeetingMainForm.docStatus=='30' and isBegin==false and kmImeetingMainForm.isNotify==true and kmImeetingMainForm.fdNeedFeedback eq 'true' }">
									<%--是参会人才显示回执按钮--%>
									<c:if test="${not empty optType and optType =='04'}">
										<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " onclick="attend();"
											data-dojo-props="colSize:2">
											<bean:message bundle="km-imeeting" key="enumeration_km_imeeting_main_feedback_fd_operate_type_attend"/>
										</li>
										<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " onclick="unAttend();"
											data-dojo-props="colSize:2">
											<bean:message bundle="km-imeeting" key="enumeration_km_imeeting_main_feedback_fd_operate_type_unattend"/>
										</li>
									</c:if>
								</c:if>
							</c:when>
							<c:otherwise>
								<%--未召开且已发送会议通知单的会议才显示回执按钮--%>
								<c:if test="${kmImeetingMainForm.docStatus=='30' and isFeedBackDeadline==false and kmImeetingMainForm.isNotify==true and kmImeetingMainForm.fdNeedFeedback eq 'true' }">
									<%--是参会人才显示回执按钮--%>
									<c:if test="${not empty optType and optType =='04'}">
										<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " onclick="attend();"
											data-dojo-props="colSize:2">
											<bean:message bundle="km-imeeting" key="enumeration_km_imeeting_main_feedback_fd_operate_type_attend"/>
										</li>
										<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " onclick="unAttend();"
											data-dojo-props="colSize:2">
											<bean:message bundle="km-imeeting" key="enumeration_km_imeeting_main_feedback_fd_operate_type_unattend"/>
										</li>
									</c:if>
								</c:if>
							</c:otherwise>
						</c:choose>
						<kmss:auth requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=earlyEndMeetingMobile&fdId=${kmImeetingMainForm.fdId}" requestMethod="GET">
							<c:if test="${kmImeetingMainForm.docStatus=='30' && isBegin==true && isEnd==false }">
							<!-- 提前结束会议（正在召开） -->
							<div data-dojo-type="mui/tabbar/TabBarButton"
								data-dojo-props='label:"${lfn:message('km-imeeting:kmImeeting.btn.earlyEnd')}",modelId:"${kmImeetingMainForm.fdId}"' 
								onclick="earlyEndMeeting();">
							</div>
							</c:if>
						</kmss:auth>
							<!-- 钉钉电话会议 -->
							<c:if test="${!isEnd && canEnter}">
								<li id="dingCallButton" class="muiBtnNext"  data-dojo-type="mui/tabbar/TabBarButton"
									  	data-dojo-props="colSize:2" onclick="callDingding();" style="display: none">电话会议</li>
						 	</c:if>
						 	<c:import url="/sys/bookmark/mobile/import/view.jsp" charEncoding="UTF-8">
							  <c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain"></c:param>
							  <c:param name="fdModelId" value="${kmImeetingMainForm.fdId}"></c:param>
							  <c:param name="fdSubject" value="${kmImeetingMainForm.fdName}"></c:param>
							  <c:param name="label" value="${lfn:message('sys-bookmark:button.bookmark') }"></c:param>
						      <c:param name="showOption" value="label"></c:param>
							</c:import>
							<%-- 会议变更，条件：1、已发送会议通知 2、未录入纪要 3、会议未开始 --%>
							<c:if test="${kmImeetingMainForm.fdSummaryFlag=='false' and isBegin==false}">
								<kmss:auth requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=changeMeeting&fdId=${kmImeetingMainForm.fdId}" requestMethod="GET">
									<c:set var="showMoreBtn" value="true"></c:set>
								</kmss:auth>
							</c:if>
							<%-- 会议取消，条件：1、创建者 2、会议未开始 --%>
							<c:if test="${kmImeetingMainForm.docStatus=='30' and isBegin==false}">
								<kmss:auth requestURL="/km/imeeting/km_imeeting_main/kmImeetingMainCancel.do?method=cancelMeeting&fdId=${kmImeetingMainForm.fdId}" requestMethod="GET">
									<c:set var="showMoreBtn" value="true"></c:set>
								</kmss:auth>
							</c:if>
							
							<!-- 开启伯恩后才有回执截止时间 -->
							<c:choose>
								<c:when test="${isFeedBackDeadline eq 'noFeedBackDeadline'}">
									<c:if test="${kmImeetingMainForm.docStatus=='30' and isBegin==false and kmImeetingMainForm.isNotify==true }">
										<%--是参会人才显示回执按钮--%>
										<c:if test="${not empty optType and optType =='04' and kmImeetingMainForm.fdNeedFeedback eq 'true' }">
											<c:set var="showMoreBtn" value="true"></c:set>
										</c:if>
									</c:if>	
								</c:when>
								<c:otherwise>
									<c:if test="${kmImeetingMainForm.docStatus=='30' and isFeedBackDeadline==false and kmImeetingMainForm.isNotify==true }">
										<%--是参会人才显示回执按钮--%>
										<c:if test="${not empty optType and optType =='04' and kmImeetingMainForm.fdNeedFeedback eq 'true' }">
											<c:set var="showMoreBtn" value="true"></c:set>
										</c:if>
									</c:if>	
								</c:otherwise>
							</c:choose>
							<c:if test="${showMoreBtn eq true && kmImeetingMainForm.docStatus !='41' }">
								 <li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtn muiBtnFeedback"  id="otherListBtn" data-dojo-props="">
								 	${lfn:message('km-imeeting:button.other')}
								 </li>
							</c:if>
					</template:replace>
				</template:include>
			</c:otherwise>
			</c:choose>
		</div>
		<%@ include file="/km/imeeting/mobile/import/kkMeeting.jsp"%>
		<c:if test="${not empty kmImeetingMainForm.fdTemplateId}">
			<%--流程页面 --%>
			<c:import url="/sys/lbpmservice/mobile/import/view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmImeetingMainForm" />
				<c:param name="fdKey" value="ImeetingMain" />
				<c:param name="viewName" value="lbpmView" />
				<c:param name="backTo" value="scrollView" />
			</c:import>
		</c:if>
<%--		<!-- 钉钉图标 -->--%>
<%--		<kmss:ifModuleExist path="/third/ding">--%>
<%--			<c:import url="/third/ding/import/ding_view.jsp" charEncoding="UTF-8">--%>
<%--				<c:param name="formName" value="kmImeetingMainForm" />--%>
<%--				<c:param name="fdEmceeId" value="${kmImeetingMainForm.fdEmceeId  }" />--%>
<%--				<c:param name="fdHostId" value="${kmImeetingMainForm.fdHostId  }" />--%>
<%--				<c:param name="fdAttendPersonIds" value="${kmImeetingMainForm.fdAttendPersonIds }" />--%>
<%--				<c:param name="fdParticipantPersonIds" value="${kmImeetingMainForm.fdParticipantPersonIds }" />--%>
<%--				<c:param name="fdFinishDate" value="${kmImeetingMainForm.fdFinishDate }" />--%>
<%--				<c:param name="isMeeting" value="true" />--%>
<%--			</c:import>--%>
<%--		</kmss:ifModuleExist>--%>
<%--		<kmss:ifModuleExist path="/third/lding">--%>
<%--			<c:import url="/third/lding/import/ding_view.jsp" charEncoding="UTF-8">--%>
<%--				<c:param name="formName" value="kmImeetingMainForm" />--%>
<%--			</c:import>--%>
<%--		</kmss:ifModuleExist>--%>
		<!-- 钉钉图标 end -->
		<c:if test="${showMoreBtn eq true }">
			<div id="kmImeetingMain_otherBox" style="display:none;" >
	  			<div id="kmImeetingMain_other" class="kmImeetingMain_other">
					<ul>
						<!-- 开启伯恩后才有回执截止时间 -->
						<c:choose>
							<c:when test="${isFeedBackDeadline eq 'noFeedBackDeadline'}">
								<c:if test="${kmImeetingMainForm.docStatus=='30' and isBegin==false and kmImeetingMainForm.isNotify==true and kmImeetingMainForm.fdNeedFeedback eq 'true'}">
									<%--是参会人才显示回执按钮--%>
									<c:if test="${not empty optType and optType =='04'}">
								    	<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " onclick="window.open('${LUI_ContextPath}/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=edit&type=attend&fdOperateType=03&meetingId=${kmImeetingMainForm.fdId }','_self')"
											data-dojo-props="colSize:2">
											<bean:message bundle="km-imeeting" key="enumeration_km_imeeting_main_feedback_fd_operate_type_proxy"/>
										</li>
										<%--邀请他人参加--%>
										<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " onclick="window.open('${LUI_ContextPath}/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=edit&type=attend&fdType=02&fdOperateType=05&meetingId=${kmImeetingMainForm.fdId }','_self')"
											data-dojo-props="colSize:2">
											<bean:message bundle="km-imeeting" key="enumeration_km_imeeting_main_feedback_fd_operate_type_attendother"/>
										</li>
									</c:if>
								</c:if>
							</c:when>
							<c:otherwise>
								<c:if test="${kmImeetingMainForm.docStatus=='30' and isFeedBackDeadline==false and kmImeetingMainForm.isNotify==true and kmImeetingMainForm.fdNeedFeedback eq 'true'}">
									<%--是参会人才显示回执按钮--%>
									<c:if test="${not empty optType and optType =='04'}">
								    	<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " onclick="window.open('${LUI_ContextPath}/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=edit&type=attend&fdOperateType=03&meetingId=${kmImeetingMainForm.fdId }','_self')"
											data-dojo-props="colSize:2">
											<bean:message bundle="km-imeeting" key="enumeration_km_imeeting_main_feedback_fd_operate_type_proxy"/>
										</li>
										<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " onclick="window.open('${LUI_ContextPath}/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=edit&type=attend&fdType=02&fdOperateType=05&meetingId=${kmImeetingMainForm.fdId }','_self')"
											data-dojo-props="colSize:2">
											<bean:message bundle="km-imeeting" key="enumeration_km_imeeting_main_feedback_fd_operate_type_attendother"/>
										</li>
									</c:if>
								</c:if>
							</c:otherwise>
						</c:choose>
					<c:if test="${kmImeetingMainForm.fdSummaryFlag=='false' and isBegin==false and kmImeetingMainForm.docStatus == '30'}">
						<kmss:auth requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=changeMeeting&fdId=${kmImeetingMainForm.fdId}" requestMethod="GET">
							<li data-dojo-type="mui/tabbar/TabBarButton" id="changeMeetingButton" class="muiBtnSubmit" 
								data-dojo-props="colSize:2">
								<bean:message bundle="km-imeeting" key="kmImeeting.change"/>
							</li>
						</kmss:auth>
				 	</c:if>
				 	<c:if test="${kmImeetingMainForm.fdSummaryFlag=='false' and isBegin==false and kmImeetingMainForm.docStatus == '30'}">
						<kmss:auth requestURL="/km/imeeting/km_imeeting_main/kmImeetingMainCancel.do?method=cancelMeeting&fdId=${kmImeetingMainForm.fdId}" requestMethod="GET">
							<li data-dojo-type="mui/tabbar/TabBarButton" id="cancelMeetingButton" class="muiBtnSubmit"
								data-dojo-props="colSize:2">
								<bean:message bundle="km-imeeting" key="kmImeeting.btn.cancelMeeting"/>
							</li>
						</kmss:auth>
				 	</c:if>
			 		</ul>
			 		<div class="otherDialogCancel">
						${lfn:message('km-imeeting:button.cancel')}
					</div>
			 	</div>
			 </div>
			 
			 <div id="kmImeetingMain_changeMeetingBox" style="display:none;" >
			 	<div id="kmImeetingMain_changeMeeting" class="kmImeetingMain_other">
			 		<ul>
			 			<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit" onclick="updateCur()"
							data-dojo-props="colSize:2">
							<bean:message bundle="km-imeeting" key="oper.change.cur"/>
						</li>
						<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit" onclick="updateAfter()"
							data-dojo-props="colSize:2">
							<bean:message bundle="km-imeeting" key="oper.change.after"/>
						</li>
						<li data-dojo-type="mui/tabbar/TabBarButton" style="color: #475669;" onclick="changeMeetingCancel()"
							data-dojo-props="colSize:2">
							<bean:message bundle="km-imeeting" key="button.cancel"/>
						</li>
			 		</ul>
			 	</div>
			 </div>
			 
			 <div id="kmImeetingMain_cancelMeetingBox" style="display:none;" >
			 	<div id="kmImeetingMain_cancelMeeting" class="kmImeetingMain_other">
			 		<ul>
			 			<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit" onclick="cancelCur()"
							data-dojo-props="colSize:2">
							<bean:message bundle="km-imeeting" key="oper.cancel.cur"/>
						</li>
						<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit" onclick="cancelAfter()"
							data-dojo-props="colSize:2">
							<bean:message bundle="km-imeeting" key="oper.cancel.after"/>
						</li>
						<li data-dojo-type="mui/tabbar/TabBarButton" style="color: #475669;" onclick="cancelMeetingCancel()"
							data-dojo-props="colSize:2">
							<bean:message bundle="km-imeeting" key="button.cancel"/>
						</li>
			 		</ul>
			 	</div>
			 </div>
			 
			 <div id="kmImeetingMain_cancelMeeting_reasonBox" style="display:none;" >
			 	<div id="kmImeetingMain_cancelMeeting_reason" class="cancelMeetingReasonDialogView">
			 		<xform:textarea property="fdCancelReason" showStatus="edit" style="height:300px" mobile="true" placeholder="${lfn:message('km-imeeting:KmImeeting.cancel.reason') }" validators="maxLength(1500) required"/>
			 	</div>
			 </div>
		</c:if> 
		
		<div style="display: none;">
			<div id="earlyEndMeetingScrollView">
				<table class="muiSimple" cellpadding="0" cellspacing="0">
					<%--原定会议时间--%>
					<tr class="placeResTr">
						<td style="width: 35%;">
							<bean:message bundle="km-imeeting" key="kmImeetingMain.fdOriginalDate"/>
						</td>
						<td>
							<span class="simpleMeetingDate">
								<c:out value="${kmImeetingMainForm.fdHoldDate}"></c:out>
							</span>
							<span style="position: relative;">~</span>
							<span class="simpleMeetingDate">
								<c:out value="${kmImeetingMainForm.fdFinishDate}"></c:out>
							</span>
						</td>
					</tr>
					<%--选择提前结束时间--%>
					<tr class="placeResTr">
						<td style="width: 35%;">
							<bean:message bundle="km-imeeting" key="kmImeetingMain.fdEarlyFinishDate" />
						</td>
						<td>
							<xform:datetime htmlElementProperties="id='fdEarlyFinishDate'" 
											subject="${lfn:message('km-imeeting:kmImeetingMain.fdEarlyFinishDate')}"
											property="fdEarlyFinishDate" 
											dateTimeType="datetime" 
											mobile="true" 
											showStatus="edit"
											required="true"></xform:datetime>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</template:replace>
</template:include>
<%@ include file="/km/imeeting/mobile/view_simple_js.jsp"%>
<script>
	require(["dojo/dom",
			'dojo/topic',
	         'dojo/ready',
	         'dijit/registry',
	         'dojox/mobile/TransitionEvent',
	         'dojo/request',
	         'dojo/query',
	         'dojo/dom-construct',
	         'dojo/dom-style',
	         'dojo/dom-geometry',
	         'mui/util',
	         'mui/dialog/BarTip',
	         "dojo/_base/lang",
	         "dojo/request",
	         'mui/dialog/Tip',
			 'mui/dialog/Dialog',
			 'dojo/_base/array'
	         ],function(dom,topic,ready,registry,TransitionEvent,request,query,domConstruct,
			 domStyle,domGeometry,util,BarTip,lang,req,Tip,Dialog,array){
		window.earlyEndMeeting = function() {
			require(["mui/dialog/Modal"], function(Modal) {
				var modalButtons = [ 
			        {
						title : "${lfn:message('km-imeeting:button.cancel')}",
						fn : function(dialog) {
							dialog.hide();
						}
			        }, 				                        
			        {
						title : "${lfn:message('km-imeeting:button.submit')}",
						fn : function(dialog) {
							var fdEarlyFinishDate = query('[name="fdEarlyFinishDate"]')[0].value;
							if(fdEarlyFinishDate === '') {
								return ;
							}
							var processTip = Tip.processing();
							request('${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=earlyEndMeetingMobile', {
								method: 'post',
								handleAs : 'json',
								data: {
									'fdId': "${kmImeetingMainForm.fdId}",
									'earlyEndTime': fdEarlyFinishDate
								}
							}).then(function(res) {
								processTip.hide();
								if(res.success) {
									Tip.success({
										text: '<bean:message key="mobile.success.tip" bundle="km-imeeting"/>'
									});
									setTimeout(function() {
										dialog.hide();
										location.reload();
									}, 200);
								} else {
									Tip.fail({
										text: res.msg || '<bean:message key="mobile.fail.tip" bundle="km-imeeting"/>'
									});
								}
							}, function(err) {
								processTip.hide();
								Tip.fail({
									text: '<bean:message key="mobile.fail.tip" bundle="km-imeeting"/>'
								});
							});
						}
			        } 
			    ]; 
				Modal(dom.byId("earlyEndMeetingScrollView"), "${lfn:message('km-imeeting:kmImeetingMain.earlyEndMeeting')}", modalButtons);
			});
		}
		
		window.openLink = function(url){
			window.open(url,'_self'); 
		};
		 /* 其他更多操作 */
		 var otherListBtn = dom.byId("otherListBtn");
		 if(otherListBtn){
			 var OtherList = dom.byId("kmImeetingMain_other");
			 dom.byId("otherListBtn").onclick= function (){
					var DialogObj = new Dialog.claz({
						element:OtherList,
						scrollable:false,
						parseable:false,
						position:"bottom",
						canClose:false
					});
					DialogObj.show();
				    var dialogCancel =  query(".muiDialogElementContainer_bottom .otherDialogCancel");
					dialogCancel[0].onclick=function(){
						DialogObj.hide();
					}; 					
			  } 
		 }
		 
		var changeMeetingButton = dom.byId('changeMeetingButton'),cancelMeetingButton = dom.byId('cancelMeetingButton');
		if(changeMeetingButton){
			changeMeetingButton.onclick = function(){
				window.changeMeeting();
			}
		}
		if(cancelMeetingButton){
			cancelMeetingButton.onclick = function(){
				window.cancelMeeting();
			}
		}
		 
		window.callDingding=function(){
			var url = "${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=expand2PersonIds";
			req(url, {
				handleAs : 'json',
				method : 'post',
				data : {
					orgIds:'${kmImeetingMainForm.fdAttendPersonIds}'
				}
			}).then(lang.hitch(this, function(data) {
				if (data['personIds']){
					window.callPhone(data['personIds'], '${kmImeetingMainForm.fdHostId }');
				}
			}));
		};

		//观看直播
		window.openMyLiving= function(){
			var qywx = ${isQywx};
			var wxLivingId = '${wxLivingId}';
			if(qywx){//企业微信客户端 创建直播
				options={
					"modelName": "com.landray.kmss.km.imeeting.model.KmImeetingMain",
					"modelId": "${kmImeetingMainForm.fdId }",
					"theme": "${kmImeetingMainFormFdNameFormatNewLine }",
					"living_duration": 3600,
					"type": 0,
					"remind_time": 60,
					"description": "${kmImeetingMainFormfdRemarkFormatNewLine }"};
					console.log(options);
					console.log(wxLivingId);
					openLiving(wxLivingId); //打开直播
			}else{
				//非企业微信客户端
				Tip.fail({
					text : '复制链接去企业微信客户端打开'
				});
			}
		}
		//创建直播
		window.createMyLiving= function(){
			//校验是否在企业微信客户端打开链接
			var qywx = ${isQywx};
			 if(qywx){//企业微信客户端 创建直播
				options={
					"modelName": "com.landray.kmss.km.imeeting.model.KmImeetingMain",
					"modelId": "${kmImeetingMainForm.fdId }",
					"theme": "${kmImeetingMainFormFdNameFormatNewLine }",
					"living_duration": 3600,
					"liveType": 0,
					"remind_time": 60,
					"description": "${kmImeetingMainFormfdRemarkFormatNewLine }"};
				//创建直播
				console.log(options);
				livingImmediately(options);
			}else{
				//非企业微信客户端
				Tip.fail({
					text : '复制链接去企业微信客户端打开'
				});
			}
		}
		function _attend() {
			var url = "${LUI_ContextPath}/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=updateFeedback";
			var processing = Tip.processing();
			req(url, {
				handleAs : 'json',
				method : 'post',
				data : {
					fdOperateType:'01',
					fdMeetingId:"${kmImeetingMainForm.fdId}",
					attendOther:"false"
				}
			}).then(lang.hitch(this, function(data) {
				if (data){
				    if(data['flag'] == '1'){
				    	processing.hide();
				    	Tip.success({
							text:'操作成功！'
						});
				    	location.reload()
					}
				}
			}));
		}
		
		// 参会人回执时，如果有时间上冲突，弹窗确认
		var confirmSubmitDialog;
		function confirmSubmit(fdPersonName) {
			confirmSubmitDialog = new Dialog.claz({
				'element' : '<br/><br/><div >参会人 ' + '<span style="font-weight: bold;">' + fdPersonName + '</span>' + ' 已回执参加其他会议，会议时间有冲突，确定提交？</div><br/><br/>',
				'destroyAfterClose':true,
				'closeOnClickDomNode':true,
				'scrollable' : false,
				'showClass' : 'muiAttendDialogShow',
				'parseable': true,
				'position':'center',
				'buttons' : [{
					title : "${lfn:message('km-imeeting:button.cancel')}",
					fn : function(dialog) {
						dialog.hide();
					}
				}, {
					title : "${lfn:message('km-imeeting:button.submit')}",
					fn : lang.hitch(this,function(dialog) {
						_attend();
						dialog.hide();
					})
				}]
			});
			confirmSubmitDialog.show();
		}
		
		window.attend = function() {
			
			// 当前登录人
			var personId = "${KMSS_Parameter_CurrentUserId}"; 
			
			// 会议ID
			var fdMeetingId = "${kmImeetingMainForm.fdId}";

			var confirmSubmitFlag = true;
			
			/*
			* 校验参与人是否在同时间段有其他会议
			* false : 不存在
			* true : 存在
			*/
			req(util.formatUrl("/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=checkIsSameTime"), {
				method : 'post',
				data : {
					fdMeetingId : fdMeetingId,
					personIds : personId
				}
			}).then(function(data) {
				var dataJson = eval("(" + data + ")");
				dataJson = eval('(' + data + ')');
				if (dataJson.flag == "true") {
					try {
						confirmSubmit(dataJson.fdPersonName);
					} catch (e) {
						console.error(e);
					}
				} else {
					_attend();
				}
			});
		};
		
		window.changeMeeting=function(){
			var recurrenceStr = "${kmImeetingMainForm.fdRecurrenceStr}"
			if(!recurrenceStr){
				window.open('${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=changeMeeting&fdId=${kmImeetingMainForm.fdId}','_self');
			}else {
				window._changeMeeting();
			}
		}
		
		var changeMeetingDialog;
		var changeMeetingList = dom.byId("kmImeetingMain_changeMeeting");
		window._changeMeeting=function(){
			changeMeetingDialog = new Dialog.claz({
				element:changeMeetingList,
				scrollable:false,
				parseable:false,
				position:"bottom",
				canClose:false
			});
			changeMeetingDialog.show();
		}
		
		updateCur = function(){
			window.open('${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=changeMeeting&fdId=${kmImeetingMainForm.fdId}&changeType=cur','_self');
			changeMeetingDialog.hide();
		}
		
		updateAfter = function(){
			window.open('${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=add&fdOriginalId=${kmImeetingMainForm.fdId}&changeType=after&fdTemplateId=${kmImeetingMainForm.fdTemplateId}&isCycle=true','_self');
			changeMeetingDialog.hide();
		}
		
		changeMeetingCancel = function(){
			changeMeetingDialog.hide();
		}
		
		window.cancelMeeting=function(){
			var recurrenceStr = "${kmImeetingMainForm.fdRecurrenceStr}"
			if(!recurrenceStr){
				window.cancelMeetingReason();
			}else {
				window._cancelMeeting();
			}
		}
		
		var cancelMeetingDialog;
		var cancelMeetingList = dom.byId("kmImeetingMain_cancelMeeting");
		window._cancelMeeting=function(){
			cancelMeetingDialog = new Dialog.claz({
				element:cancelMeetingList,
				scrollable:false,
				parseable:false,
				position:"bottom",
				canClose:false
			});
			cancelMeetingList.show();
		}
		
		cancelCur = function(){
			window.cancelMeetingReason("cur");
			cancelMeetingDialog.hide();
		}
		
		cancelAfter = function(){
			window.cancelMeetingReason("after");
			cancelMeetingDialog.hide();
		}
		
		cancelMeetingCancel = function(){
			cancelMeetingDialog.hide();
		}
		
		var cancelMeetingReasonDialog;
		var cancelMeetingReason = dom.byId("kmImeetingMain_cancelMeeting_reason");
		window.cancelMeetingReason = function(cancelType){
			var url = "${LUI_ContextPath}/km/imeeting/km_imeeting_main_cancel/kmImeetingMainCancel.do?method=mobileCancelMeeting";
			cancelMeetingReasonDialog = new Dialog.claz({
				element:cancelMeetingReason,
				scrollable:false,
				parseable:false,
				position:"center",
				canClose:false,
				buttons: [{
					title : "${lfn:message('km-imeeting:button.cancel')}",
					fn : function(dialog) {
						dialog.hide();
					}
				} ,{
					title : "${lfn:message('km-imeeting:button.submit')}",
					fn : lang.hitch(this,function(dialog) {
						var fdReason = query('[name="fdCancelReason"]',this.domNode);
						if(fdReason[0].value && fdReason[0].value != ""){
							var processing = Tip.processing();
							req(url, {
								handleAs : 'json',
								method : 'post',
								data : {
									fdCancelReason:fdReason[0].value,
									meetingId:"${kmImeetingMainForm.fdId}",
									fdCancelType:cancelType
								}
							}).then(lang.hitch(this, function(data) {
								if (data){
								    if(data['flag'] == '1'){
								    	dialog.hide();
								    	processing.hide();
								    	Tip.success({
											text:"${lfn:message('km-imeeting:mobile.success.tip')}"
										});
								    	location.reload()
									}
								}
							}));
						}else{
							Tip.fail({
								text:"${lfn:message('km-imeeting:mobile.error.tip2')}"
							});
							return;
						}
					})
				}]
			});
			cancelMeetingReasonDialog.show();
			
		}
		
		var updateEmccDialog;
		window.updateEmcc=function(){
			updateEmccDialog = new Dialog.claz({
				'element' : '<br/><br/><div><bean:message bundle="km-imeeting" key="kmImeeting.undertake.work"/>？</div><br/><br/>',
				'destroyAfterClose':true,
				'closeOnClickDomNode':true,
				'scrollable' : false,
				'showClass' : 'muiAttendDialogShow',
				'parseable': true,
				'position':'center',
				'buttons' : [{
					title : "${lfn:message('km-imeeting:button.cancel')}",
					fn : function(dialog) {
						dialog.hide();
					}
				} ,{
					title : "${lfn:message('km-imeeting:button.submit')}",
					fn : lang.hitch(this,function(dialog) {
						var url = "${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=updateEmcc&emccFlag=emcc&fdId=${kmImeetingMainForm.fdId}";
						var processing = Tip.processing();
						var promise = request.post(url);
						promise.response.then(function(data) {
							if (data.status == 200 && data.getHeader("lui-status") == "true" ) {
								dialog.hide();
						    	processing.hide();
						    	Tip.success({
									text:"${lfn:message('km-imeeting:mobile.success.tip')}"
								});
						    	location.reload();
							}else
								Tip.fail({
									text : "${lfn:message('km-imeeting:mobile.fail.tip')}"
								});
						});
					})
				} ]
			});
			updateEmccDialog.show();
		};
		
		var unAttendDialog;
		window.unAttend=function(){
			var url = "${LUI_ContextPath}/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=updateFeedback";
			
			var iframeObj = domConstruct.create('div', {
				id: 'unAttendDialogView',
				className: 'unAttendDialogView',
				style: 'display:none;',
				innerHTML: '<xform:textarea property="fdReason" showStatus="edit" style="height:300px" mobile="true" validators="required"/>'
			})
			document.body.appendChild(iframeObj);
			var contentNode = document.getElementById("unAttendDialogView");
			unAttendDialog = new Dialog.claz({
				'element' : contentNode.innerHTML,
				'destroyAfterClose':true,
				'closeOnClickDomNode':true,
				'scrollable' : false,
				'showClass' : 'muiAttendDialogShow',
				'parseable': true,
				'position':'center',
				'buttons' : [{
					title : "${lfn:message('km-imeeting:button.cancel')}",
					fn : function(dialog) {
						dialog.hide();
					}
				} ,{
					title : "${lfn:message('km-imeeting:mobile.oper.submit')}",
					fn : lang.hitch(this,function(dialog) {
						var fdReason = query('[name="fdReason"]',this.domNode);
						if(fdReason[0].value && fdReason[0].value != ""){
							var url = "${LUI_ContextPath}/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=updateFeedback";
							var processing = Tip.processing();
							req(url, {
								handleAs : 'json',
								method : 'post',
								data : {
									fdReason:fdReason[0].value,
									fdOperateType:'02',
									fdMeetingId:"${kmImeetingMainForm.fdId}",
									attendOther:"false"
								}
							}).then(lang.hitch(this, function(data) {
								if (data){
								    if(data['flag'] == '1'){
								    	dialog.hide();
								    	processing.hide();
								    	Tip.success({
											text:"${lfn:message('km-imeeting:mobile.success.tip')}"
										});
								    	location.reload();
									}
								}
							}));
						}else{
							Tip.fail({
								text:"${lfn:message('km-imeeting:mobile.error.tip1')}"
							});
							return;
						}
					})
				} ]
			});
			unAttendDialog.show();
			
			var unAttendDialogViewNode = query('.unAttendDialogView');
			array.forEach(unAttendDialogViewNode, function(node) {
				node.remove();
			});
		};
		
		//顶部回执Tip
		<c:choose>
			<c:when test="${isFeedBackDeadline=='noFeedBackDeadline'}">
				<c:if test="${kmImeetingMainForm.docStatus=='30' and isBegin==false and kmImeetingMainForm.isNotify==true }">
					var url="${LUI_ContextPath }/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=edit&type=attend&meetingId=${kmImeetingMainForm.fdId }";
					
					<c:if test="${not empty optType && optType=='01' }">
						BarTip.tip({
							text:'<bean:message bundle="km-imeeting" key="mobile.kmImeetingMainFeedback.attend"/><a class="modifyLink" onclick="window.openLink(\''+ url +'\')"><bean:message bundle="km-imeeting"  key="mobile.kmImeetingMainFeedback.modify"/></a>'
						});
					</c:if>
					<c:if test="${not empty optType && optType=='02' }">
						BarTip.tip({
							text:'<bean:message bundle="km-imeeting"  key="mobile.kmImeetingMainFeedback.reject"/><a class="modifyLink" onclick="window.openLink(\''+ url +'\')"><bean:message bundle="km-imeeting"  key="mobile.kmImeetingMainFeedback.modify"/></a>'
						});
					</c:if>
					<c:if test="${not empty optType && optType=='03' }">
						BarTip.tip({
							text:'<bean:message bundle="km-imeeting" key="mobile.kmImeetingMainFeedback.proxy" />'
						});
					</c:if>
				</c:if>
			</c:when>
			<c:otherwise>
				<c:if test="${kmImeetingMainForm.docStatus=='30' and isFeedBackDeadline==false and kmImeetingMainForm.isNotify==true }">
					var url="${LUI_ContextPath }/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=edit&type=attend&meetingId=${kmImeetingMainForm.fdId }";
					
					<c:if test="${not empty optType && optType=='01' }">
						BarTip.tip({
							text:'<bean:message bundle="km-imeeting" key="mobile.kmImeetingMainFeedback.attend"/><a class="modifyLink" onclick="window.openLink(\''+ url +'\')"><bean:message bundle="km-imeeting"  key="mobile.kmImeetingMainFeedback.modify"/></a>'
						});
					</c:if>
					<c:if test="${not empty optType && optType=='02' }">
						BarTip.tip({
							text:'<bean:message bundle="km-imeeting"  key="mobile.kmImeetingMainFeedback.reject"/><a class="modifyLink" onclick="window.openLink(\''+ url +'\')"><bean:message bundle="km-imeeting"  key="mobile.kmImeetingMainFeedback.modify"/></a>'
						});
					</c:if>
					<c:if test="${not empty optType && optType=='03' }">
						BarTip.tip({
							text:'<bean:message bundle="km-imeeting" key="mobile.kmImeetingMainFeedback.proxy" />'
						});
					</c:if>
				</c:if>
			</c:otherwise>
		</c:choose>
			
		<c:if test="${type=='admin' or type=='attend' or type=='cc' }">
			//切换标签重新计算高度
			var _position=domGeometry.position(query('#fixed')[0]),
				_scrollTop=0;
			topic.subscribe("/mui/list/_runSlideAnimation",function(srcObj, evt) {
				_scrollTop= Math.abs(evt.to.y);//记录当前位置
			});
			topic.subscribe("/mui/navitem/_selected",function(){
				var view=registry.byId("scrollView"),
					evt={ y : 0 - _scrollTop };
				if(_scrollTop > _position.y){
					evt={ y : 0 - _position.y };
				}
				view.handleToTopTopic(null,evt);
			});
		</c:if>
		
		//拨打主持人号码
		window.dialPhone=function(mobileNo){
			if(mobileNo){
				location.href = 'tel:'+mobileNo;
			}else{
				Tip.fail({
					text:'<bean:message bundle="km-imeeting"  key="mobile.kmImeetingMian.phone.notnull"/>'
				});
			}
		};
		
		//初始化时间
		try {
			var meetingHoldDate = Com_GetDate(document.getElementById('meetingHoldDate').innerHTML.trim());
			var meetingFinishDate = Com_GetDate(document.getElementById('meetingFinishDate').innerHTML.trim());
			if(meetingHoldDate && meetingFinishDate) {
				var _meetingHoldDate = new Date(meetingHoldDate.getFullYear(), meetingHoldDate.getMonth(), meetingHoldDate.getDate());
				var _meetingFinishDate = new Date(meetingFinishDate.getFullYear(), meetingFinishDate.getMonth(), meetingFinishDate.getDate());
				
				if(_meetingHoldDate.getTime() == _meetingFinishDate.getTime()) {
					
					var meetingDateNode = query('.meeting-date');
					array.forEach(meetingDateNode, function(node) {
						node.innerHTML = util.formatDate(_meetingHoldDate, dojoConfig.Date_format) + 
											' ' + 
											util.formatDate(meetingHoldDate, dojoConfig.Time_format) + 
											' ~ ' +
											util.formatDate(meetingFinishDate,dojoConfig.Time_format)
					});
	
				}
			}
		} catch(e) {}

		//校验对象
		ready(function(){
			<kmss:ifModuleExist path="/sys/task/">
				var ul = query('ul[data-dojo-type="sys/task/mobile/resource/js/OverflowTabBar"]');
				if(ul.length&&ul.length>0){
					var accordionPanelNode = query('div[data-dojo-type="mui/panel/AccordionPanel"]')[0];
					accordionPanelNode && (domStyle.set(accordionPanelNode, 'margin', '0px'));
				}
			</kmss:ifModuleExist>
			//AJAX,到后台计算出与会人数
			var caculateAttendNum = function(){
				
				var fdHostId="${kmImeetingMainForm.fdHostId }" !="" ? ("${kmImeetingMainForm.fdHostId }") : "";//主持人
				var fdAttendPersonIds="${kmImeetingMainForm.fdAttendPersonIds }" !="" ? ("${kmImeetingMainForm.fdAttendPersonIds }") : "";
				var	fdAttendPersonArray=fdAttendPersonIds?fdAttendPersonIds.split(';'):[];//参与人员
				var fdParticipantPersonIds="${kmImeetingMainForm.fdParticipantPersonIds }" !="" ? ("${kmImeetingMainForm.fdParticipantPersonIds }") : "";
				var	fdParticipantPersonArray=fdParticipantPersonIds?fdParticipantPersonIds.split(';'):[];//列席人员
				var fdSummaryInputPersonId="${kmImeetingMainForm.fdSummaryInputPersonId }" !="" ? ("${kmImeetingMainForm.fdSummaryInputPersonId }") : "";//会议纪要人
				var personArray=[];
				personArray=personArray.concat(fdAttendPersonArray);
				personArray=personArray.concat(fdParticipantPersonArray);
				if(fdHostId){
					personArray.push(fdHostId);
				}
				if(fdSummaryInputPersonId){
					personArray.push(fdSummaryInputPersonId);
				}
				var personIds=personArray.join(';');
				
				request.post('${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=caculateAttendNumber', {
					handleAs: 'json',
					data: {personIds: personIds,fdMeetingId:'${kmImeetingMainForm.fdId}'}
				}).then(function(res) {
					if(res && res.number >= 0) {
						var attendCountsNode = query('[name="attendCounts"]');
						array.forEach(attendCountsNode, function(node) {
							node.innerHTML = res.number
						});
					}
				});
			};
			
			//初始化
			caculateAttendNum();

		});
	});
</script>

<!-- 钉钉电话会议 -->
<c:import url="/sys/mobile/jsp/ding.jsp" charEncoding="UTF-8">
</c:import>
<c:if test="${prefixDing=='ding' }">
	<c:import url="/third/ding/mobile/ding_call.jsp" charEncoding="UTF-8">
	</c:import>
</c:if>
<c:if test="${prefixDing=='lding' }">
	<c:import url="/third/lding/import/ding_call.jsp" charEncoding="UTF-8">
	</c:import>
</c:if>