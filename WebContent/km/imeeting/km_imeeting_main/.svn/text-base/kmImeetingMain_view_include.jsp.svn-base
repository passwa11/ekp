<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@page import="com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
	<%
		request.setAttribute("boenUrl", KmImeetingConfigUtil.getBoenUrl());
		request.setAttribute("unitAdmin", KmImeetingConfigUtil.getUnitAdmin());
	%>

	<%--收藏--%>
	<c:import url="/sys/bookmark/import/bookmark_bar.jsp" charEncoding="UTF-8">
		<c:param name="fdSubject" value="${kmImeetingMainForm.fdName}" />
		<c:param name="fdModelId" value="${kmImeetingMainForm.fdId}" />
		<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain" />
	</c:import>
	
	<%--回执页签，管理员、参与人、抄送人可见--%>
	<c:if test="${type=='admin' or type=='attend' or type=='cc'}">
		<%--会议回执--%>
		<c:if test="${kmImeetingMainForm.docStatusFirstDigit=='3' and kmImeetingMainForm.fdNeedFeedback eq 'true'}">
		<kmss:auth requestURL="/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=getFeedbackList&meetingId=${JsParam.fdId}" requestMethod="GET">
			<ui:content title="${ lfn:message('km-imeeting:table.kmImeetingMainFeedback') }" cfg-order="1">
				<script type="text/javascript">	seajs.use(['theme!listview']);</script>
				<ui:dataview>
					<ui:source type="AjaxJson">
						{url:'/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=getFeedbackList&meetingId=${JsParam.fdId }&sort=asc&type=creator&rowsize=10'}
					</ui:source>
					<ui:render type="Javascript">
						<%--
							//已废弃,被feedbackList.js替代 #7924
							<c:import url="/km/imeeting/resource/tmpl/feedbackList.jsp" charEncoding="UTF-8"></c:import>
						--%>
						<c:import url="/km/imeeting/resource/tmpl/feedbackList.js" charEncoding="UTF-8"></c:import>
					</ui:render>
				</ui:dataview>
				<list:paging></list:paging>
			</ui:content>
		</kmss:auth>
		</c:if>
    </c:if>
    
    <%--签到页签，管理员、参与人、抄送人可见--%>
	<c:if test="${kmImeetingMainForm.docStatus!='10' and (type=='admin' or type=='attend' or type=='cc')}">
		<c:if test="${kmImeetingMainForm.docStatus ne '20' }">
			<%if(KmImeetingConfigUtil.isBoenEnable()){ %>
				<ui:content title="${ lfn:message('sys-attend:sysAttendCategory.importView.signDetail') }" cfg-order="2">
					<ui:iframe src="${boenUrl}/checkStatistics.html?userId=${unitAdmin}">
					</ui:iframe>
				</ui:content>
		  	<%}else{ %>
				<%-- 签到 --%>
				<%-- <% if(!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { // 开启三员后不能使用 %> --%>
				<kmss:ifModuleExist path="/sys/attend/">
					<c:set var="isHasAttendAuth" value="false"></c:set>
					<kmss:authShow roles="ROLE_SYSATTEND_DEFAULT">
						<c:set var="isHasAttendAuth" value="true"></c:set>
					</kmss:authShow>
					<c:if test="${isHasAttendAuth eq 'true' }">
						<c:set var="isShowBtn" value="false"></c:set>
						<c:set var="isExpandTab" value="false"></c:set>
						
						<kmss:auth
							requestURL="/sys/attend/sys_attend_category/sysAttendCategory.do?method=add&fdModelName=com.landray.kmss.km.imeeting.model.KmImeetingMain&fdModelId=${kmImeetingMainForm.fdId}" requestMethod="GET">
							<c:if test="${kmImeetingMainForm.docStatus=='30' && isEnd==false }">
								<c:set var="isShowBtn" value="true"></c:set>
							</c:if>
						</kmss:auth>
						<c:if test="${HtmlParam.showtab=='attend'}">
							<c:set var="isExpandTab" value="true"></c:set>
						</c:if>
						<c:if test="${kmImeetingMainForm.docStatusFirstDigit=='3'}">
							<c:import url="/sys/attend/import/view.jsp" charEncoding="UTF-8">
								<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain"></c:param>
								<c:param name="fdModelId" value="${kmImeetingMainForm.fdId}"></c:param>
								<c:param name="isShowBtn" value="${isShowBtn}"></c:param>
								<c:param name="isExpandTab" value="${isExpandTab}"></c:param>
								<c:param name="expandCateId" value="${HtmlParam.expandCateId}"></c:param>
								<c:param name="order" value="2"></c:param>
							</c:import>
						</c:if>
					</c:if>
					<c:if test="${isHasAttendAuth ne 'true' }">
						<ui:content title="${ lfn:message('sys-attend:sysAttendCategory.importView.signDetail') }" expand="true" id="${kmImeetingMainForm.fdId}" cfg-order="2" cfg-disable="false">
							${ lfn:message('global.accessDenied') }
						</ui:content>
					</c:if>
				</kmss:ifModuleExist>
				<%-- <% } %> --%>
			<% } %>
		</c:if>
	</c:if>
	
	<!-- 投票 -->
	<c:if test="${kmImeetingMainForm.docStatusFirstDigit=='3'}">
	<%if(KmImeetingConfigUtil.isBoenEnable()){ %>
		<c:if test="${canBallot || isAdmin}">
			<c:if test="${kmImeetingMainForm.fdVoteEnable == 'true' }">
				<ui:content title="${lfn:message('km-imeeting:kmImeetingVote.tab')}" cfg-order="3">
					<ui:iframe src="${LUI_ContextPath}/km/imeeting/km_imeeting_main/import/kmImeeting_vote.jsp?fdId=${kmImeetingMainForm.fdId}"></ui:iframe>
				</ui:content>
			</c:if>
			<c:if test="${kmImeetingMainForm.fdBallotEnable == 'true' && not empty kmImeetingMainForm.kmImeetingAgendaForms}">
				<ui:content title="${lfn:message('km-imeeting:kmImeetingAgenda.tab')}" cfg-order="3">
					<ui:iframe src="${LUI_ContextPath}/km/imeeting/km_imeeting_main/import/kmImeeting_ballot.jsp?fdId=${kmImeetingMainForm.fdId}"></ui:iframe>
				</ui:content>
			</c:if>
		</c:if>
		<c:if test="${canControl || isAdmin}">
			<c:import url="/km/imeeting/km_imeeting_main/import/kmImeeting_control.jsp" charEncoding="UTF-8">
	       		<c:param name="order" value="3"/>
	       	</c:import>
		</c:if>
	<%}else{ %>
		<kmss:ifModuleExist path="/km/vote">
			<c:import url="/km/vote/import/kmVoteMain_view.jsp" charEncoding="UTF-8">
				<c:param name="fdModelId" value="${kmImeetingMainForm.fdId}" />
				<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain" />
				<c:param name="order" value="3"/>
			</c:import>
		</kmss:ifModuleExist>
	<%} %>
	</c:if>
	
	
	<%--相关任务--%>
	<c:if test="${kmImeetingMainForm.docStatusFirstDigit=='3'}">
	<kmss:ifModuleExist  path = "/sys/task/">
		<c:import url="/sys/task/import/sysTaskMain_view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmImeetingMainForm" />
			<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain" />
			<c:param name="order" value="4"/>
		</c:import>
	</kmss:ifModuleExist>
	</c:if>
	
	<!-- 督办 -->
	<c:if test="${kmImeetingMainForm.docStatusFirstDigit=='3'}">
	<kmss:ifModuleExist path="/km/supervise/">
		<c:import url="/km/supervise/import/kmSuperviseMain_edit.jsp"
			charEncoding="UTF-8">
			<c:param name="fdModelId" value="${kmImeetingMainForm.fdId}" />
			<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain" />
			<c:param name="order" value="5"/>
		</c:import>
	</kmss:ifModuleExist>
	</c:if>
    
    <%-- 流程 --%>
	<c:if test="${param.hasTemplate != 'false'}">
	 	<%@include file="/km/imeeting/km_imeeting_main/kmImeetingMain_view_wf_include.jsp"%>
	</c:if>
	
	<%-- 权限 --%>
	<c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="kmImeetingMainForm" />
		<c:param name="moduleModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain" />
		<c:param name="order" value="7"/>
	</c:import>
	
	<c:if test="${type=='admin'}">
		<%--传阅记录--%>
		<c:if test="${kmImeetingMainForm.docStatusFirstDigit=='3'}">
			<c:import url="/sys/circulation/import/sysCirculationMain_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmImeetingMainForm" />
				<c:param name="order" value="8"/>
			</c:import>
		</c:if>
	</c:if>	
	
	<c:if test="${kmImeetingMainForm.docStatus!='10' and kmImeetingMainForm.docStatus!='00' and kmImeetingMainForm.docStatus!='41' and (type=='admin' or type=='attend' or type=='cc')}">
		<%--会议时间轴--%>
		<ui:content title="${ lfn:message('km-imeeting:table.kmImeetingMainHistory') }" cfg-order="9">
			<ui:dataview>
				<ui:source type="AjaxJson">
					{url:'/km/imeeting/km_imeeting_main_history/kmImeetingMainHistory.do?method=getHistorysByMeeting&meetingId=${JsParam.fdId }'}
				</ui:source>
				<ui:render type="Template">
					<c:import url="/km/imeeting/resource/tmpl/history.jsp" charEncoding="UTF-8"></c:import>
				</ui:render>
			</ui:dataview>
		</ui:content>
	</c:if>	
	
	<%--机制类页签，管理员可见--%>
    <c:if test="${type=='admin'}">
    	<c:if test="${kmImeetingMainForm.docStatusFirstDigit=='3'}">
	    	<%--日程机制--%>
	    	<c:if test="${ not empty kmImeetingMainForm.fdTemplateId}">
			<c:if test="${kmImeetingMainForm.syncDataToCalendarTime=='sendNotify'||kmImeetingMainForm.syncDataToCalendarTime=='personAttend'}">
				<ui:content title="${ lfn:message('km-imeeting:kmImeetingMain.agenda.syn') }" cfg-order="10">
					<table class="tb_normal" width=100%>
						<%--同步时机--%>
						<tr>
							<td class="td_normal_title" width="15%">
						 		<bean:message bundle="sys-agenda" key="module.sys.agenda.syn.time" />
						 	</td>
						 	<td colspan="3">
						 		<xform:radio property="syncDataToCalendarTime">
					       			<xform:enumsDataSource enumsType="kmImeetingMain_syncDataToCalendarTime" />
								</xform:radio>
							</td>
						</tr>
						<tr>
							<td colspan="4" style="padding: 0px;">
							 	<c:import url="/sys/agenda/import/sysAgendaMain_general_view.jsp"	charEncoding="UTF-8">
							    	<c:param name="formName" value="kmImeetingMainForm" />
							    	<c:param name="fdKey" value="ImeetingMain" />
							    	<c:param name="fdPrefix" value="sysAgendaMain_formula_view" />
							 	</c:import>
					 		</td>
					 	</tr>
					</table>
				</ui:content>
			</c:if>
			</c:if>
		
			<%--发布机制--%>
			<c:import url="/sys/news/import/sysNewsPublishMain_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmImeetingMainForm" />
				<c:param name="fdKey" value="ImeetingMain" />
				<c:param name="order" value="11"/>
			</c:import>
		
		
	      	<%--阅读次数--%>
			<c:import url="/sys/readlog/import/sysReadLog_view.jsp" charEncoding="UTF-8">
	       		<c:param name="formName" value="kmImeetingMainForm" />
	       		<c:param name="order" value="12"/>
	       	</c:import>
       	</c:if>
	</c:if>
	<!-- 分享机制  -->
	<kmss:ifModuleExist path="/third/ywork/">
		<c:import url="/third/ywork/ywork_share/yworkDoc_share.jsp"
			charEncoding="UTF-8">
			<c:param name="modelId" value="${kmImeetingMainForm.fdId}" />
			<c:param name="modelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain" />
			<c:param name="templateId" value="${ kmImeetingMainForm.fdTemplateId}" />
			<c:param name="allPath" value="${ kmImeetingMainForm.fdTemplateName}" />
			<c:param name="showCondition" value="${kmImeetingMainForm.docStatus=='30' }" />
			<c:param name="showRecord" value="false" />
		</c:import>
	</kmss:ifModuleExist>
