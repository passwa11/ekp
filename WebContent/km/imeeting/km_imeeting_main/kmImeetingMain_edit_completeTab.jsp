<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import=" com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil"%>
<%@page import="com.landray.kmss.km.imeeting.util.KKUtil"%>
<%@page import="com.landray.kmss.sys.appconfig.service.ISysAppConfigService"%>
<%@page import="java.util.Map"%>	
<template:replace name="content"> 
	<c:if test="${param.approveModel ne 'right'}">
		<form name="kmImeetingMainForm" method="post" action ="${KMSS_Parameter_ContextPath}km/imeeting/km_imeeting_main/kmImeetingMain.do">
	</c:if>	
		<html:hidden property="fdId" />
		<html:hidden property="docStatus" />
		<html:hidden property="docCreatorId" />
		<html:hidden property="docCreateTime" />
		<html:hidden property="fdNotifyerId" />
		<html:hidden property="fdChangeMeetingFlag" />
		<html:hidden property="fdSummaryFlag" />
		<html:hidden property="fdIsTopic" value="${kmImeetingMainForm.fdIsTopic}"/>
		<html:hidden property="fdModelId" value = "${kmImeetingMainForm.fdModelId}" />
		<html:hidden property="fdModelName" value = "${kmImeetingMainForm.fdModelName}" />
		<html:hidden property="fdPhaseId" value = "${kmImeetingMainForm.fdPhaseId}" />
		<html:hidden property="fdWorkId" value = "${kmImeetingMainForm.fdWorkId}" />
		<html:hidden property="fdChangeType" value = "${kmImeetingMainForm.fdChangeType}" />
		<div class="lui_form_content_frame" style="padding-top:20px"> 
			 <table class="tb_normal" width=100% id="Table_Main"> 
			 	<%--会议变更原因--%>
				<c:if test="${kmImeetingMainForm.fdChangeMeetingFlag=='true' }">
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingMain.changeMeetingReason"/>
					</td>
					<td colspan="3">
						<xform:textarea property="changeMeetingReason" style="width:95%;" htmlElementProperties="data-actor-expand='true'"
							required="true" showStatus="edit" validators="maxLength(1500)"></xform:textarea>
						<html:hidden property="beforeChangeContent"/>
					</td>
				</tr>
				</c:if>
			 	<tr>
			 		<td colspan="4" class="com_subject" style="font-size: 110%;font-weight: bold;">
			 			<bean:message bundle="km-imeeting" key="kmImeetingMain.createStep.base"/>
			 		</td>
			 	</tr>
			 	<tr>
			 		<%--会议名称--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdName"/>
					</td>			
					<td width="85%" colspan="3">
						<xform:text property="fdName" style="width:95%" showStatus="edit"/>		 	
					</td>
			 	</tr>
			 	<tr>
			 		<%--召开时间--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdDate"/>
					</td>			
					<td width="35%" >
						<xform:datetime property="fdHoldDate" dateTimeType="datetime" showStatus="edit" 
							onValueChange="changeDateTime"  required="true" validators="after compareTime"></xform:datetime>
						<span style="position: relative;top:-5px;">~</span>
						<xform:datetime property="fdFinishDate" dateTimeType="datetime" showStatus="edit" 
							onValueChange="changeDateTime" required="true" validators="after compareTime"></xform:datetime>
						<%--隐藏域,保存改变前的时间，用于回退--%>
						<input type="hidden" name="fdHoldDateTmp" value="${kmImeetingMainForm.fdHoldDate}">
						<input type="hidden" name="fdFinishDateTmp" value="${kmImeetingMainForm.fdFinishDate}">
					</td>
					<%--会议历时--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHoldDuration"/>
					</td>			
					<td width="35%" >
						<input type="text" name="fdHoldDurationHour" validate="digits min(0) maxLength(4) validateDuration" class="inputsgl"
							 style="width:50px;text-align: center;"  onchange="changeDuration();"  subject="${lfn:message('km-imeeting:kmImeetingMain.fdHoldDuration')}"/>
						<bean:message key="date.interval.hour"/>
						<input type="text" name="fdHoldDurationMin" validate="digits min(0) maxLength(4) validateDuration" class="inputsgl" 
							style="width:50px;text-align: center;"  onchange="changeDuration();"  subject="${lfn:message('km-imeeting:kmImeetingMain.fdHoldDuration')}"/>
						<bean:message key="date.interval.minute"/>
						<xform:text property="fdHoldDuration" showStatus="noShow" subject="${lfn:message('km-imeeting:kmImeetingMain.fdHoldDuration')}"/>
					</td>
			 	</tr>
			 	<c:if test="${ kmImeetingMainForm.fdChangeType eq 'after' || ( not empty kmImeetingMainForm.fdRepeatType && empty kmImeetingMainForm.fdChangeType)}">
					 <c:if test="${kmImeetingConfig.useCyclicity eq '2' || kmImeetingConfig.useCyclicity eq '3' && fn:contains(kmImeetingConfig.useCyclicityPersonId,userId) == true}">
					 	<!-- 周期性会议设置 -->
					 	<tr>
					 		<td class="td_normal_title" width=15%>
					 			<bean:message bundle="km-imeeting" key="kmImeetingMain.cyclicityConfig"/>
					 		</td>
					 		<td width="85%" colspan="3" class="customContainerTD">
								<div>
									<ui:recurrence id="fdRecurrence" property="fdRecurrenceStr" customContainer="#customContainer" isOn="true" cfg-finishDate="fdFinishDate"></ui:recurrence>
								</div>
								<div id="customContainer"></div>
					 		</td>
				 		</tr>
				 	</c:if>
			 	</c:if>
			 	 <%
				 	if(KmImeetingConfigUtil.isBoenEnable() || KKUtil.isKkVideoMeetingEnable()){
				 %>
				 <tr>
			 		<%--回执结束时间--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdFeedBackDeadline"/>
					</td>			   
					<td width="85%"  colspan="3">
						<xform:datetime property="fdFeedBackDeadline" dateTimeType="datetime" showStatus="edit" subject="${lfn:message('km-imeeting:kmImeetingMain.fdFeedBackDeadline')}" required="true" validators="after valDeadline">
						</xform:datetime><font color="red"><bean:message bundle="km-imeeting" key="kmImeetingMain.feedbackDeadline" /></font>
					</td>
				</tr>
				<c:if test="${kmImeetingMainForm.fdIsVideo eq 'true' and kmImeetingMainForm.docStatus ne '41' and canEnterAliMeeting eq 'true' and not empty fdMeetingCode}">
					<tr>
				 		<%--回执结束时间--%>
						<td class="td_normal_title" width=15%>
							会议口令
						</td>			   
						<td width="85%"  colspan="3">
							<c:out value="${fdMeetingCode}"></c:out>
						</td>
					</tr>
				</c:if>
				 <%} %>
			 	<tr>
			 		<%--会议目的--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdMeetingAim"/>
					</td>			
					<td width="85%" colspan="3" >
						<xform:textarea htmlElementProperties="data-actor-expand='true'" property="fdMeetingAim" style="width:97%;" showStatus="edit"/>
					</td>
			 	</tr>
			 	<tr>
			 		<%--会议类型--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdTemplate"/>
					</td>			
					<td width="35%" >
						<html:hidden property="fdTemplateId"/>
						<c:out value="${kmImeetingMainForm.fdTemplateName }"></c:out>
					</td>
					<%--会议编号--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdMeetingNum"/>
					</td>			
					<td width="35%" >
						<c:out value="${kmImeetingMainForm.fdMeetingNum }"></c:out>
						<html:hidden property="fdMeetingNum"/>
					</td>
			 	</tr>
			 	<tr>
			 		<%--会议组织人--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingTemplate.fdEmcee"/>
					</td>			
					<td width="35%" >
						<xform:address propertyName="fdEmceeName" propertyId="fdEmceeId" orgType="ORG_TYPE_PERSON" style="width:50%;" showStatus="edit"></xform:address>
					</td>
					<%--组织部门--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingMain.docDept"/>
					</td>			
					<td width="35%" >
						<xform:address propertyName="docDeptName" propertyId="docDeptId" subject="${lfn:message('km-imeeting:kmImeetingMain.docDept') }"
							orgType="ORG_TYPE_ORG|ORG_TYPE_DEPT" style="width:50%;" showStatus="edit" required="true"></xform:address>
					</td>
			 	</tr>
			 	<tr>
			 		<%--会议发起人--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingMain.docCreator"/>
					</td>			
					<td width="35%" >
						<html:hidden property="docCreatorId"/>
						<c:out value="${kmImeetingMainForm.docCreatorName }"></c:out>
					</td>
					<%-- 所属场所 --%>
					<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field_single.jsp" charEncoding="UTF-8">
	                     <c:param name="id" value="${kmImeetingMainForm.authAreaId}"/>
	                </c:import>
			 	</tr>
			 	<tr>
			 		<td colspan="4" style="font-size: 110%;font-weight: bold;">
			 			<span class="com_subject"  style="width: 15%;display: inline-block;">
			 				<bean:message bundle="km-imeeting" key="kmImeetingMain.createStep.base.fdAttendPersons"/>
			 			</span>
			 			<span style="float: right;margin-right: 2%;">
			 				<ui:button text="${lfn:message('km-imeeting:kmImeetingMain.checkFree.text') }"  onclick="checkFree();" href="javascript:void(0);"
				 					title="${lfn:message('km-imeeting:kmImeetingMain.checkFree.title') }"/>
				 			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdAttendNumber.0" />
			 				<xform:text property="fdAttendNum" validators="min(0)" style="width:40px;text-align:center;" showStatus="edit"/><bean:message bundle="km-imeeting" key="kmImeetingMain.fdAttendNumber.1" />
			 			</span>
			 		</td>
			 	</tr>
			 	<tr>
			 		<%--主持人--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHost"/>
					</td>			
					<td width="85%" colspan="3" >
						<xform:address propertyName="fdHostName" propertyId="fdHostId" orgType="ORG_TYPE_PERSON" style="width:47%;" onValueChange="caculateAttendNum" showStatus="edit"></xform:address>&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;
						<xform:text property="fdOtherHostPerson" htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdOtherHostPerson') }'" style="width:47%;position: relative;top:-4px;" showStatus="edit"/>
					</td>
			 	</tr>
			 	<%
				 	if(KmImeetingConfigUtil.isBoenEnable()){
				 %>
			 	<tr>
			 		<%--主持人--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdControlPerson"/>
					</td>			
					<td width="85%" colspan="3" >
						<xform:address style="width:97%;height:80px;" textarea="true" required="true" showStatus="edit" propertyName="fdControlPersonName" propertyId="fdControlPersonId" orgType="ORG_TYPE_PERSON" mulSelect="false"  subject="${lfn:message('km-imeeting:kmImeetingMain.fdControlPerson') }" ></xform:address>
					</td>
			 	</tr>
			 	<tr>
			 		<%--监票人--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdBallotPerson"/>
					</td>			
					<td width="85%" colspan="3" >
					<xform:address  style="width:97%;height:80px;" textarea="true" showStatus="edit"  propertyId="fdBallotPersonIds" propertyName="fdBallotPersonNames" 
							orgType="ORG_TYPE_PERSON" mulSelect="true"  subject="${lfn:message('km-imeeting:kmImeetingMain.fdBallotPerson') }"></xform:address>
					</td>
			 	</tr>
			 	<%
				 	}
				 %>
			 	<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdAttendPersons"/>
					</td>			
					<td width="85%" colspan="3" >
						<%--与会人员--%>
						<xform:address  style="width:46%;height:80px;" textarea="true" showStatus="edit"  propertyId="fdAttendPersonIds" propertyName="fdAttendPersonNames" 
							orgType="ORG_TYPE_ALL" mulSelect="true" onValueChange="caculateAttendNum" validators="validateattend"
							subject="${lfn:message('km-imeeting:kmImeetingMain.fdAttendPersons') }"></xform:address>
				  		&nbsp;&nbsp;
				  		<%--外部与会人员--%>
				  		<xform:textarea style="width:46%;border:1px solid #b4b4b4" property="fdOtherAttendPerson" showStatus="edit"  
				  			htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdOtherAttendPerson') }' data-actor-expand='true'"  
				  			validators="validateattend maxLength(1500)" 
				  			subject="${lfn:message('km-imeeting:kmImeetingMain.fdAttendPersons') }"/>
				  		<span class="txtstrong">*</span>
					</td>
			 	</tr>
			 	<tr>
			 		<%--列席人员--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdParticipantPersons"/>
					</td>			
					<td width="85%" colspan="3" >
						<xform:address style="width:46%;height:80px" textarea="true" showStatus="edit"  propertyId="fdParticipantPersonIds" propertyName="fdParticipantPersonNames" orgType="ORG_TYPE_ALL" mulSelect="true" onValueChange="caculateAttendNum"></xform:address>
				  		&nbsp;&nbsp;
				  		<xform:textarea style="width:46%;border:1px solid #b4b4b4" property="fdOtherParticipantPerson" showStatus="edit"  validators="maxLength(1500)"
				  			htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdOtherParticipantPerson') }' data-actor-expand='true'"/>
					</td>
			 	</tr>
			 	<tr>
			 		<%--抄送人员--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdCopyToPersons"/>
					</td>			
					<td width="85%" colspan="3" >
						<xform:address style="width:46%;height:80px" textarea="true" showStatus="edit"  propertyId="fdCopyToPersonIds" propertyName="fdCopyToPersonNames" orgType="ORG_TYPE_ALL" mulSelect="true"></xform:address>
				  		&nbsp;&nbsp;
				  		<xform:textarea style="width:46%;border:1px solid #b4b4b4" property="fdOtherCopyToPerson" showStatus="edit"  validators="maxLength(1500)"
				  			htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdOtherCopyToPerson') }' data-actor-expand='true'"/>
					</td>
			 	</tr>
			 	<tr>
			 		<%--纪要录入人--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdSummaryInputPerson"/>
					</td>
					<td width="35%" >
						<xform:address style="width:150px;" propertyId="fdSummaryInputPersonId" propertyName="fdSummaryInputPersonName" showStatus="edit"
							orgType="ORG_TYPE_PERSON" onValueChange="caculateAttendNum"  validators="validateSummaryInputPerson"></xform:address>
					</td>
					<%--纪要完成时间--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdSummaryCompleteTime"/>
					</td>			
					<td width="35%" >
						<xform:datetime property="fdSummaryCompleteTime" showStatus="edit"  dateTimeType="date"  validators="validateSummaryCompleteTime validateWithHoldDate"></xform:datetime>
						<%--是否催办纪要--%>
						<span>
				 			<input type="checkbox" style="margin-left:10px" name="fdIsHurrySummary" value="true" onclick="showHurryDayDiv();" 
								<c:if test="${kmImeetingMainForm.fdIsHurrySummary == 'true'}">checked</c:if>> 
								<bean:message bundle="km-imeeting" key="kmImeetingMain.fdIsHurrySummary" />
						</span>
						<span id="HurryDayDiv" style="display:none">
						&nbsp;<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHurrySummaryDay.0" />
							<xform:text validators="validateHurrySummaryDay" property="fdHurryDate" style="width:30px" showStatus="edit"/> 
							<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHurrySummaryDay.1" /> 
						</span> 
					</td>
			 	</tr>
			 	<tr>
			 		<td colspan="4" class="com_subject" style="font-size: 110%;font-weight: bold;">
			 			<bean:message bundle="km-imeeting" key="kmImeetingMain.createStep.base.agenda"/>
			 			<%
							Boolean isTopicMng = false;
				 			if("true".equals(KmImeetingConfigUtil.isTopicMng())){
				 				isTopicMng = true;
				 			}
				 			request.setAttribute("isTopicMng", isTopicMng);
				 		%>
				 		<c:if test="${isTopicMng eq 'true' and kmImeetingMainForm.fdIsTopic eq 'true'}">
			 				&nbsp;&nbsp;&nbsp;<input type="button" class="lui_form_button"
			 				 value='<bean:message key="kmImeetingAgenda.operation.addDetailTopic.mobile" bundle="km-imeeting"  />' 
			 				 onclick="selectTopicList();"/>
			 			</c:if>
			 		</td>
			 	</tr>
			 	<tr>
			 		<%--会议议程信息--%>
			 		<td colspan="4">
				 		<c:choose>
				 			<c:when test="${kmImeetingMainForm.fdIsTopic eq 'true'}">
				 				<%@include file="/km/imeeting/km_imeeting_agenda/kmImeetingAgenda_editTopic.jsp"%>
				 			</c:when>
				 			<c:otherwise>
				 				<%@include file="/km/imeeting/km_imeeting_agenda/kmImeetingAgenda_edit.jsp"%>
				 			</c:otherwise>
				 		</c:choose>
			 		</td>
			 	</tr>
			 	<%
			 		if(!KmImeetingConfigUtil.isBoenEnable()){
		 		%>
			 	<tr>
			 		<%--相关资料--%>
			 		<td class="td_normal_title" width=15%>
			 			<bean:message bundle="km-imeeting" key="kmImeetingMain.attachment"/>
			 		</td>
			 		<td width="85%" colspan="3" >
						<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
							<c:param name="fdKey" value="attachment" />
							<c:param name="fdModelId" value="${JsParam.fdId }" />
							<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain" />
						</c:import>
					</td>
			 	</tr>
			 	<% } %>
			 	<%
				 	if(KmImeetingConfigUtil.isBoenEnable()){
				%>
				 	<tr>
				 		<td class="td_normal_title" width=15%>
				 			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdVoteEnable"/>
				 		</td>
				 		<td width="35%" >
							<ui:switch property="fdVoteEnable" showType="edit" checked="${kmImeetingMainForm.fdVoteEnable}"  checkVal="true" unCheckVal="false" onValueChange="voteEnableChange()"/>
							
							<span id="voteConfig" onclick="voteConfig();" style="cursor: pointer;" class="lui_text_primary"><bean:message bundle="km-imeeting" key="table.kmImeetingVote"/></span>
						</td>
						<td class="td_normal_title" width=15%>
				 			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdBallotEnable"/>
				 		</td>
				 		<td width="35%" >
							<ui:switch property="fdBallotEnable" showType="edit" checked="${kmImeetingMainForm.fdBallotEnable}"  checkVal="true" unCheckVal="false" />
						</td>
					</tr>
				<%
				 	}
				%>
			 	<tr>
			 		<%--备注--%>
			 		<td class="td_normal_title" width=15%>
			 			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdRemark"/>
			 		</td>
			 		<td width="85%" colspan="3" >
						<xform:textarea property="fdRemark" style="width:97%;" showStatus="edit" htmlElementProperties="data-actor-expand='true'"></xform:textarea>
					</td>
			 	</tr>
			 </table>
		</div>
		<c:choose>
			<c:when test="${param.approveModel eq 'right'}">
				<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="5" var-average='false' var-useMaxWidth='true'>
					<%@include file="/km/imeeting/km_imeeting_main/kmImeetingMain_edit_completeContent.jsp"%>
				</ui:tabpanel>	
			</c:when>
			<c:otherwise>
				<ui:tabpage expand="false">    
					<%@include file="/km/imeeting/km_imeeting_main/kmImeetingMain_edit_completeContent.jsp"%>
				</ui:tabpage>
			</c:otherwise>
		</c:choose>
		<%-- 会议历史操作信息 --%>
		<div style="display: none;">
			<c:forEach items="${kmImeetingMainForm.kmImeetingMainHistoryForms}"  var="kmImeetingMainHistoryItem" varStatus="vstatus">
				<input type="hidden" name="kmImeetingMainHistoryForms[${vstatus.index}].fdId" value="${kmImeetingMainHistoryItem.fdId}" /> 
			</c:forEach>
		</div>
	<c:if test="${param.approveModel ne 'right'}">
	 </form>
	</c:if>
	<%@include file="/km/imeeting/km_imeeting_main/kmImeetingMain_add_js.jsp"%>
	<%@include file="/km/imeeting/km_imeeting_main/kmImeetingMain_edit_complete_js.jsp"%>
</template:replace>
<c:if test="${param.approveModel eq 'right'}">
	<template:replace name="barRight">
		<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
			<%--流程--%>
			<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmImeetingMainForm" />
				<c:param name="fdKey" value="ImeetingMain" />
				<c:param name="showHistoryOpers" value="true" />
				<c:param name="isExpand" value="true" />
				<c:param name="approveType" value="right" />
				<c:param name="approvePosition" value="right" />
			</c:import>
		</ui:tabpanel>
	</template:replace>
</c:if>
