<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.km.imeeting.util.KKUtil"%>
<template:include ref="default.edit" sidebar="no" >

	<%-- 样式 --%>
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/resource/css/edit_simple.css" />
	</template:replace>

	<template:replace name="title">
		<c:out value="${ lfn:message('km-imeeting:kmImeetingMain.opt.change') }"></c:out>
	</template:replace>
	
	<%--导航路径--%>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"  id="categoryId"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('km-imeeting:module.km.imeeting') }"  ></ui:menu-item>
			<ui:menu-item text="${ lfn:message('km-imeeting:table.kmImeetingMain') }"></ui:menu-item>
		</ui:menu>
	</template:replace>
	
	<%--内容区--%>
	<template:replace name="content"> 
		<html:form action="/km/imeeting/km_imeeting_main/kmImeetingMain.do">
			<html:hidden property="fdId" />
			<html:hidden property="docStatus" />
			<html:hidden property="docCreatorId" />
			<html:hidden property="docCreateTime" />
			<html:hidden property="fdNotifyerId" />
			<html:hidden property="fdChangeMeetingFlag" />
			<html:hidden property="syncDataToCalendarTime"/>
			<html:hidden property="fdSummaryFlag" />
			<html:hidden property="method_GET" />
			<html:hidden property="fdModelId" value = "${kmImeetingMainForm.fdModelId}" />
			<html:hidden property="fdModelName" value = "${kmImeetingMainForm.fdModelName}" />
			<html:hidden property="fdPhaseId" value = "${kmImeetingMainForm.fdPhaseId}" />
			<html:hidden property="fdWorkId" value = "${kmImeetingMainForm.fdWorkId}" />
			<html:hidden property="fdTemplateId"/>
			<html:hidden property="beforeChangeContent"/>
			<html:hidden property="fdIsVideo"/>
			
			<div class="lui_form_content_frame">
				<div class="tb_simple_container">
					<table class="tb_simple" width=100%>
						<tr>
							<td width="15%" class="td_normal_title">
								<bean:message bundle="km-imeeting" key="kmImeetingMain.fdName" />
							</td>
							<td width="55%">
								<xform:text property="fdName" style="width:95%" />	
							</td>
							<td width="7%" class="td_normal_title td_align_center">
								
							</td>
							<td width="23%" class="">
								<div class="swichDiv_simple"> 
									<div><bean:message bundle="km-imeeting" key="kmImeetingMain.fdNeedFeedback" /></div>
									<div class="feedback">
										<ui:switch property="fdNeedFeedback" showType="edit" checked="${ kmImeetingMainForm.fdNeedFeedback}" onValueChange="changeNeedFeedback(false)"/>
									</div>
									<div><bean:message bundle="km-imeeting" key="kmImeetingMain.fdNeedPlace" /></div>
									<div class="place">
										<ui:switch property="fdNeedPlace" showType="edit" checked="${ kmImeetingMainForm.fdNeedPlace}" onValueChange="changeNeedPlace(false)"/>
									</div>
								</div>
							</td>
							<%--
							<td width="25%" class="">
								<ui:switch property="isCloud" showType="edit" checked="${ kmImeetingMainForm.isCloud}"/>
							</td> 
							--%>
						</tr>
						<%-- 所属场所 --%>
						<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp" charEncoding="UTF-8">
		                     <c:param name="id" value="${kmImeetingMainForm.authAreaId}"/>
		                </c:import>
						<tr>
							<td width="15%" class="td_normal_title">
								<bean:message bundle="km-imeeting" key="kmImeetingMain.fdDate"/>
							</td>
							<td width="55%" >
								<xform:datetime property="fdHoldDate" dateTimeType="datetime" style="width:95%;"
									showStatus="edit" required="true" validators="after"></xform:datetime>
							</td>
							<td width="7%" class="td_normal_title td_align_center">
								<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHoldDuration" />
							</td>
							<td width="23%" class="">
								<select name="fdHoldDurationHour" style="width:80%;">
									<c:forEach begin="0" end="15" varStatus="varstatus">
										<option value="${ varstatus.count/2  }" <c:if test="${varstatus.count/2 == kmImeetingMainForm.fdHoldDurationHour }">selected</c:if>>
											${ varstatus.count / 2 }<bean:message key="date.interval.hour"/>
										</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<%
						 	if(KKUtil.isKkVideoMeetingEnable()){
						 %>
							 <tr id="feedBackDeadlineRow" style="display: none">
						 		<%--回执结束时间--%>
								<td class="td_normal_title" width=15%>
									<bean:message bundle="km-imeeting" key="kmImeetingMain.fdFeedBackDeadline"/>
								</td>			   
								<td width="85%"  colspan="3">
									<xform:datetime property="fdFeedBackDeadline" dateTimeType="datetime" showStatus="edit" subject="${lfn:message('km-imeeting:kmImeetingMain.fdFeedBackDeadline')}"  validators="after valDeadline">
									</xform:datetime><span class="txtstrong">*</span></br>
									<font color="red">(请在该截止时间前做回执，否则将无法参会)</font>
								</td>
							</tr>
						 <%} %>
						<tr id="placeTr">
					 		<%--选择会议室--%>
					 		<td class="td_normal_title" width=15%>
					 			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdPlace"/>
					 		</td>
					 		<td colspan="3">
					 			<xform:dialog propertyId="fdPlaceId" propertyName="fdPlaceName" showStatus="edit" validators="validateUserTime"
					 				className="inputsgl" style="width:95%;" 
					 				subject="${lfn:message('km-imeeting:kmImeetingMain.fdPlace') }">
							  	 	selectHoldPlace();
								</xform:dialog>
								<input type="hidden" name="fdPlaceUserTime" value="${ kmImeetingMainForm.fdUserTime}"
										subject="${lfn:message('km-imeeting:kmImeetingMain.fdPlace') }"/>
					 		</td>
					 	</tr>
					 	<tr>
					 		<%--参会人--%>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="km-imeeting" key="kmImeetingMain.fdAttendPersons"/>
							</td>
							<td colspan="3">
								<%--参加人员--%>
								<xform:address  style="width:95%;" subject="${lfn:message('km-imeeting:kmImeetingMain.fdAttendPersons')}" textarea="true" showStatus="edit"  propertyId="fdAttendPersonIds" propertyName="fdAttendPersonNames" 
									orgType="ORG_TYPE_PERSON" mulSelect="true" required="true" validators="validateattend" ></xform:address>
							</td>
						</tr>
						<tr>
					 		<%--备注--%>
					 		<td class="td_normal_title" width=15%>
					 			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdMeetingAim"/>
					 		</td>
					 		<td width="85%" colspan="3" >
								<xform:textarea property="fdMeetingAim" style="width:95%;"></xform:textarea>
							</td>
					 	</tr>
					 	<c:if test="${kmImeetingMainForm.fdIsVideo eq 'true' and kmImeetingMainForm.docStatus ne '41' and canEnterAliMeeting eq 'true' and not empty fdMeetingCode}">
					 		<tr>
						 		<%--阿里云视频会议口令--%>
						 		<td class="td_normal_title" width=15%>
						 			会议口令
						 		</td>
						 		<td width="85%" colspan="3" >
									<c:out value="${fdMeetingCode}"></c:out>
								</td>
						 	</tr>
					 	</c:if>
					</table>
				</div>
				
				<%-- 更多信息 --%>
				<div class="lui_opt_more">
					<a class="com_help lui_arrowDn">
						<bean:message bundle="km-imeeting"  key="kmImeetingMain.tip.addMore"/>
					</a>
				</div>
				<div class="tb_simple_more_container">
					<table class="tb_simple tb_simple_more" width=100%>
						<tr>
					 		<%--主持人--%>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHost"/>
							</td>			
							<td width="85%" colspan="3" >
								<xform:address propertyName="fdHostName" propertyId="fdHostId" subject="${lfn:message('km-imeeting:kmImeetingMain.fdHost') }"
									orgType="ORG_TYPE_PERSON" required="false" style="width:95%;"></xform:address>
							</td>
					 	</tr>
						<tr>
					 		<%--纪要录入人--%>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="km-imeeting" key="kmImeetingMain.fdSummaryInputPerson"/>
							</td>
							<td width="85%" colspan="3" >
								<xform:address propertyId="fdSummaryInputPersonId" propertyName="fdSummaryInputPersonName" orgType="ORG_TYPE_PERSON" style="width:95%;"></xform:address>
							</td>
						</tr>
						<tr>
					 		<%--会议组织人--%>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="km-imeeting" key="kmImeetingTemplate.fdEmcee"/>
							</td>			
							<td width="85%" colspan="3" >
								<xform:address propertyName="fdEmceeName" propertyId="fdEmceeId" orgType="ORG_TYPE_PERSON" style="width:95%;"></xform:address>
							</td>
						</tr>
						<tr>	
							<%--组织部门--%>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="km-imeeting" key="kmImeetingMain.docDept"/>
							</td>			
							<td width="85%" colspan="3" >
								<xform:address propertyName="docDeptName" propertyId="docDeptId" orgType="ORG_TYPE_ORG|ORG_TYPE_DEPT" style="width:95%;"></xform:address>
							</td>
					 	</tr>
					</table>
				</div>
				
				<%-- 通知方式，取系统默认的 --%>
				<div style="display: none;">
					<xform:radio property="fdNotifyType" showStatus="edit">
		   				<xform:enumsDataSource enumsType="km_imeeting_main_fd_notify_type" />
					</xform:radio>
					<kmss:editNotifyType property="fdNotifyWay" />
				</div>
				<%-- 会议历史操作信息 --%>
				<div style="display: none;">
					<c:forEach items="${kmImeetingMainForm.kmImeetingMainHistoryForms}"  var="kmImeetingMainHistoryItem" varStatus="vstatus">
						<input type="hidden" name="kmImeetingMainHistoryForms[${vstatus.index}].fdId" value="${kmImeetingMainHistoryItem.fdId}" /> 
					</c:forEach>
				</div>
				
				<%-- 提交按钮 --%>
				<div class="toolbar">			
					<ui:button text="${lfn:message('km-imeeting:kmImeeting.change') }" order="2" onclick="updateMethod();"></ui:button>
					<ui:button text="${lfn:message('button.cancel') }" order="2" onclick="closeMethod();" styleClass="lui_toolbar_btn_gray"></ui:button>
				</div>
			</div>
		</html:form>	
	</template:replace>		
	
</template:include>	
<%@include file="/km/imeeting/km_imeeting_main/kmImeetingMain_edit_simple_js.jsp"%>