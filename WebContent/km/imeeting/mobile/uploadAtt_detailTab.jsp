<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:if test="${fn:length(kmImeetingMainForm.kmImeetingAgendaForms)  > 0 }">
<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message bundle="km-imeeting"  key="kmImeetingMain.createStep.base.agenda"/>',icon:'mui-ul'">
	<table class="muiSimple " width="100%" border="0" cellspacing="0" cellpadding="0">
		<c:forEach items="${kmImeetingMainForm.kmImeetingAgendaForms}"  var="kmImeetingAgendaItem" varStatus="vstatus">
			<c:if test="${kmImeetingAgendaItem.docResponsId==currentUser.fdId or isAdmin==true}">
			<table class="muiSimple ">
			
			<%--序号--%> 
			<tr>
				<td style="font-weight:bold ;color:#4285f4">
					0${vstatus.index+1}
				</td>
				<td colspan="2" align="left" valign="middle" class="muiDetailTableNo">
					<div class="muiDetailTableUp" style="margin-right:1rem">
						<i class="meetingMoreIcon mui mui-up-n"></i>
					</div>
				</td>
			</tr>
			<table class="muiSimple">
				<%--会议议题--%> 
				<tr>
					<td>
						<bean:message bundle="km-imeeting" key="kmImeetingAgenda.docSubject"/>
					</td>
					<td>
						<xform:text property="kmImeetingAgendaForms[${vstatus.index}].docSubject" />
					</td>
				</tr>
				<%--汇报人--%> 
				<tr>
					<td>
						<bean:message bundle="km-imeeting" key="kmImeetingAgenda.docReporter"/>
					</td>
					<td>
						<xform:text property="kmImeetingAgendaForms[${vstatus.index}].docReporterName" />
						<%-- <ul class="muiMeetingList">
							<li>
								<div data-dojo-type="mui/person/PersonList"
									data-dojo-props="title:'<bean:message bundle="km-imeeting"  key="kmImeetingAgenda.docReporter"/>',personId:'${ kmImeetingAgendaItem.docReporterId }'">
								</div>
							</li>
						</ul> --%>
					</td>
				</tr>
				<%--汇报时间（分钟）--%> 
				<tr>
					<td>
						<bean:message bundle="km-imeeting" key="kmImeetingAgenda.docReporterTime"/>
					</td>
					<td>
						<xform:text property="kmImeetingAgendaForms[${vstatus.index}].docReporterTime" />
						<c:if test="${not empty kmImeetingMainForm.kmImeetingAgendaForms[vstatus.index].docReporterTime }">
							<bean:message key="date.interval.minute"/>
						</c:if>
					</td>
				</tr>
				<%--上会所需材料--%> 
				<tr>
					<td>
						<bean:message bundle="km-imeeting" key="kmImeetingAgenda.attachmentName"/>
					</td>
					<td>
						<xform:text property="kmImeetingAgendaForms[${vstatus.index}].attachmentName" />
					</td>
				</tr>
				<%--材料负责人--%> 
				<tr>
					<td>
						<bean:message bundle="km-imeeting" key="kmImeetingAgenda.docRespons"/>
					</td>
					<td>
						<xform:text property="kmImeetingAgendaForms[${vstatus.index}].docResponsName" />
					</td>
				</tr>
				<%--上会所需材料--%>
				<tr>
					<td colspan="2">
						<bean:message bundle="km-imeeting" key="kmImeetingAgenda.attachment.submit"/>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="padding-top:0px ">
						<c:if test="${canUpload==true}">
							<c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="kmImeetingMainForm" />
								<c:param name="fdKey" value="ImeetingUploadAtt_${kmImeetingAgendaItem.fdId }" />
								<c:param name="fdModelId" value="${JsParam.fdId }" />
								<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain" />
								<c:param name="uploadAfterSelect" value="true" />
							</c:import>
						</c:if>
						<c:if test="${canUpload==false}">
							<c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
								<c:param name="fdKey" value="ImeetingUploadAtt_${kmImeetingAgendaItem.fdId }" />
								<c:param name="fdModelId" value="${JsParam.fdId }" />
								<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain" />
								<c:param name="formName" value="kmImeetingMainForm" />
							</c:import>
						</c:if>
					</td>
				</tr>
				</table>
			</c:if>
			</table>
		</c:forEach>
		<tr KMSS_IsContentRow="1" align="center"></tr>
	</table>
</div>
</c:if>