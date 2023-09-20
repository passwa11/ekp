<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="java.util.Date"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page import="com.landray.kmss.km.imeeting.forms.KmImeetingMainForm"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@page import="com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil"%>
<%@page import="com.landray.kmss.km.imeeting.util.KKUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%--会议主持人/参加人/列席人员/抄送人员看到的会议通知单详情--%>
<div style="float: right;margin:10px;">
	<span style="margin-right: 10px;">
		<bean:message bundle="km-imeeting" key="kmImeetingMain.fdMeetingNum"/>：
		<c:out value="${kmImeetingMainForm.fdMeetingNum}"></c:out>
		<c:if test="${empty  kmImeetingMainForm.fdMeetingNum}">
			<bean:message bundle="km-imeeting" key="kmImeetingMain.createStep.base.fdMeetingNum.tip"/>
		</c:if>
	</span>
	<span>
		<bean:message bundle="km-imeeting" key="kmImeetingMain.docStatus"/>：
		<c:if test="${kmImeetingMainForm.docStatus!='30' && kmImeetingMainForm.docStatus!='41' }">
			<sunbor:enumsShow value="${kmImeetingMainForm.docStatus}" enumsType="common_status" />
		</c:if>
		<%--未召开--%>
		<c:if test="${kmImeetingMainForm.docStatus=='30' && isBegin==false }">
			<bean:message bundle="km-imeeting" key="kmImeeting.status.publish.unHold"/>
		</c:if>
		<%--正在召开--%>
		<c:if test="${kmImeetingMainForm.docStatus=='30' && isBegin==true && isEnd==false }">
			<bean:message bundle="km-imeeting" key="kmImeeting.status.publish.holding"/>
		</c:if>
		<%--已召开--%>
		<c:if test="${kmImeetingMainForm.docStatus=='30' && isBegin==true && isEnd==true }">
			<bean:message bundle="km-imeeting" key="kmImeeting.status.publish.hold"/>
		</c:if>
		<%--已取消--%>
		<c:if test="${kmImeetingMainForm.docStatus=='41' }">
			<bean:message bundle="km-imeeting" key="kmImeeting.status.cancel"/>
		</c:if>
	</span>
</div>
<table class="tb_normal" width="100%;">
	<%--会议变更原因--%>
	<c:if test="${kmImeetingMainForm.fdChangeMeetingFlag=='true' }">
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-imeeting" key="kmImeetingMain.changeMeetingReason"/>
			</td>
			<td colspan="3" style="color:red;">
				<xform:textarea property="changeMeetingReason" style="width:98%;" value="${fn:escapeXml(kmImeetingMainForm.changeMeetingReason)}"></xform:textarea>
				<html:hidden property="beforeChangeContent" value="${kmImeetingMainForm.beforeChangeContent }"/>
			</td>
		</tr>				
	</c:if>
	<tr>
		<%--会议名称--%>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdName"/>
		</td>			
		<td width="35%" >
			<c:out value="${kmImeetingMainForm.fdName }"></c:out>
		</td>
		<%--会议类型--%>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdTemplate"/>
		</td>			
		<td width="35%" >
			<c:out value="${kmImeetingMainForm.fdTemplateName }"></c:out>
		</td>
	</tr>
	<% if(ISysAuthConstant.IS_AREA_ENABLED) { %> 
	<tr>	
	    <td class="td_normal_title" width="15%">
	        <bean:message key="sysAuthArea.authArea" bundle="sys-authorization" />
		</td>
		<td colspan="3">
			<c:out value="${kmImeetingMainForm.authAreaName }"></c:out>
		</td>	
	</tr>
	<% } %>
	<tr>
		<%--主持人--%>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHost"/>
		</td>			
		<td width="85%" colspan="3">
			<c:out value="${kmImeetingMainForm.fdHostName }"></c:out>
			<c:if test="${not empty kmImeetingMainForm.fdOtherHostPerson }">
				&nbsp;<c:out value="${kmImeetingMainForm.fdOtherHostPerson }"/>
			</c:if>
		</td>
	</tr>
	<%
	 	if(KmImeetingConfigUtil.isBoenEnable()){
	 %>
 	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdControlPerson"/>
		</td>			
		<td width="85%" colspan="3" >
			<c:out value="${kmImeetingMainForm.fdControlPersonName }"></c:out>
		</td>
 	</tr>
 	<tr>
 		<%--监票人--%>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdBallotPerson"/>
		</td>			
		<td width="85%" colspan="3" >
			<c:out value="${kmImeetingMainForm.fdBallotPersonNames }"></c:out>
		</td>
 	</tr>
 	<%
	 	}
	 %>
	<tr>
		<%--召开时间--%>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdDate"/>
		</td>			
		<td width="35%" >
			<c:out value="${kmImeetingMainForm.fdHoldDate }"></c:out>&nbsp;~&nbsp;<c:out value="${kmImeetingMainForm.fdFinishDate }"></c:out>
		</td>
		<%--会议历时--%>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHoldDuration"/>
		</td>			
		<td width="35%" >
			<span id ="fdHoldDurationHour" ></span><bean:message key="date.interval.hour"/>
			<span id="fdHoldDurationMinSpan"><span id ="fdHoldDurationMin" ></span><bean:message key="date.interval.minute"/></span>
		</td>
	</tr>
	<%
	 	if(KmImeetingConfigUtil.isBoenEnable() || KKUtil.isKkVideoMeetingEnable()){
	 %>
	 <tr>
 		<%--回执结束时间--%>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdFeedBackDeadline"/>
		</td>			   
		<td width="85%"  colspan="3">
			<c:out value="${kmImeetingMainForm.fdFeedBackDeadline }"></c:out>
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
	 <%} %>
	<c:if test="${ not empty kmImeetingMainForm.fdRepeatType}">
		<!-- 周期性会议设置 -->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-imeeting" key="kmImeetingMain.fdRepeatType"/>
			</td>
			<td width="85%" colspan="3">
				<c:out value="${kmImeetingMainForm.fdRepeatType }"></c:out>
			</td>
		</tr>
	</c:if>
	<c:if test="${ not empty kmImeetingMainForm.fdRepeatFrequency}">
		<!-- 周期性会议设置 -->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-imeeting" key="kmImeetingMain.fdRepeatFrequency"/>
			</td>
			<td width="85%" colspan="3">
				<c:out value="${kmImeetingMainForm.fdRepeatFrequency }"></c:out>
			</td>
		</tr>
	</c:if>
	<c:if test="${ not empty kmImeetingMainForm.fdRepeatTime}">
		<!-- 周期性会议设置 -->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-imeeting" key="kmImeetingMain.fdRepeatTime"/>
			</td>
			<td width="85%" colspan="3">
				<c:out value="${kmImeetingMainForm.fdRepeatTime }"></c:out>
			</td>
		</tr>
	</c:if>
	<c:if test="${ not empty kmImeetingMainForm.fdRepeatUtil}">
		<!-- 周期性会议设置 -->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-imeeting" key="kmImeetingMain.fdRepeatUtil"/>
			</td>
			<td width="85%" colspan="3">
				<% 
					request.setAttribute("localeByUser", ResourceUtil.getLocaleByUser().toString());
				%>
				<c:choose>
					<c:when test="${localeByUser eq 'en_US' && fn:startsWith(kmImeetingMainForm.fdRepeatUtil,'Until') }">
						<c:out value="Until "></c:out><c:out value="${fn:substring(kmImeetingMainForm.fdRepeatUtil,9, 11)}"></c:out>/<c:out value="${fn:substring(kmImeetingMainForm.fdRepeatUtil, 11, 13)}"></c:out>/<c:out value="${fn:substring(kmImeetingMainForm.fdRepeatUtil, 5, 9)}"></c:out>
					</c:when>
					<c:otherwise>
						<c:out value="${kmImeetingMainForm.fdRepeatUtil }"></c:out>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</c:if>
	<%
	 	if(KmImeetingConfigUtil.isVideoMeetingEnable()){
	 %>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdNeedPlace" />
		</td>
		<td width="35%"  >
			<ui:switch property="fdNeedPlace" showType="show" checked="${kmImeetingMainForm.fdNeedPlace}" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdIsVideo" />
		</td>
		<td width="35%"  >
			<ui:switch property="fdIsVideo" showType="show" checked="${kmImeetingMainForm.fdIsVideo}" />
		</td>
	</tr>				
	<%} %>				
				
	<c:if test="${kmImeetingMainForm.fdNeedPlace ne 'false'}">
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdPlace"/>
		</td>
		<td width="85%" colspan="3" >
			<c:choose>
				<c:when test="${not empty kmImeetingMainForm.fdVicePlaceNames or not empty kmImeetingMainForm.fdOtherVicePlace }">
					<!-- 主会场 -->
					<bean:message bundle="km-imeeting" key="kmImeetingMain.fdMainPlace"/>：
					<c:out value="${kmImeetingMainForm.fdPlaceName}" />
					<input type="hidden" name="fdPlaceUserTime" value="${ kmImeetingMainForm.fdUserTime}"
							subject="${lfn:message('km-imeeting:kmImeetingMain.fdMainPlace') }"/>
					&nbsp;
					<!-- 外部主会场 -->
		 			<c:set var="hasSysAttend" value="false"></c:set>
					<kmss:ifModuleExist path="/sys/attend">
						<c:set var="hasSysAttend" value="true"></c:set>
					</kmss:ifModuleExist>
					<c:if test="${hasSysAttend == true }">
						<c:import url="/sys/attend/import/map_tag.jsp" charEncoding="UTF-8">
							<c:param name="propertyName" value="fdOtherPlace"></c:param>
							<c:param name="propertyCoordinate" value="fdOtherPlaceCoordinate"></c:param>
							<c:param name="validators" value="validateplace"></c:param>
							<c:param name="subject" value="${ lfn:message('km-imeeting:kmImeetingMain.fdPlace')}"></c:param>
							<c:param name="style" value="width:40%;"></c:param>
							<c:param name="showStatus" value="view"></c:param>
						</c:import>
					</c:if>
					<c:if test="${hasSysAttend == false }">
						<xform:text property="fdOtherPlace" style="width:40%;"></xform:text>
					</c:if>
					<br/><br/>
					<!-- 分会场 -->
					<bean:message bundle="km-imeeting" key="kmImeetingMain.fdVicePlaces"/>：
					<c:out value="${kmImeetingMainForm.fdVicePlaceNames}" />
					&nbsp;
					<!-- 外部分会场 -->
					<c:if test="${hasSysAttend == true }">
						<c:import url="/sys/attend/import/map_tag.jsp" charEncoding="UTF-8">
							<c:param name="propertyName" value="fdOtherVicePlace"></c:param>
							<c:param name="propertyCoordinate" value="fdOtherVicePlaceCoord"></c:param>
							<c:param name="subject" value="${ lfn:message('km-imeeting:kmImeetingMain.fdOtherMainPlace')}"></c:param>
							<c:param name="style" value="width:40%;"></c:param>
							<c:param name="showStatus" value="view"></c:param>
						</c:import>
					</c:if>
					<c:if test="${hasSysAttend == false }">
						<xform:text property="fdOtherVicePlace" style="width:40%;"></xform:text>
					</c:if>
					<input type="hidden" name="fdVicePlaceUserTimes" value="${ kmImeetingMainForm.fdVicePlaceUserTimes}"/>
				</c:when>
				<c:otherwise>
					<c:out value="${kmImeetingMainForm.fdPlaceName}" />
					<input type="hidden" name="fdPlaceUserTime" value="${ kmImeetingMainForm.fdUserTime}"
							subject="${lfn:message('km-imeeting:kmImeetingMain.fdPlace') }"/>
					&nbsp;
					<!-- 其他会议地点 -->
		 			<c:set var="hasSysAttend" value="false"></c:set>
					<kmss:ifModuleExist path="/sys/attend">
						<c:set var="hasSysAttend" value="true"></c:set>
					</kmss:ifModuleExist>
					<c:if test="${hasSysAttend == true }">
						<c:import url="/sys/attend/import/map_tag.jsp" charEncoding="UTF-8">
							<c:param name="propertyName" value="fdOtherPlace"></c:param>
							<c:param name="propertyCoordinate" value="fdOtherPlaceCoordinate"></c:param>
							<c:param name="validators" value="validateplace"></c:param>
							<c:param name="subject" value="${ lfn:message('km-imeeting:kmImeetingMain.fdOtherPlace')}"></c:param>
							<c:param name="style" value="width:40%;"></c:param>
							<c:param name="showStatus" value="view"></c:param>
						</c:import>
					</c:if>
					<c:if test="${hasSysAttend == false }">
						<xform:text property="fdOtherPlace" style="width:40%;"></xform:text>
					</c:if>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	</c:if>
	<tr>
 		<%--会议室辅助设备--%>
 		<td class="td_normal_title" width=15% title="${lfn:message('km-imeeting:kmImeetingMain.kmImeetingEquipment.tip') }">
 			<bean:message bundle="km-imeeting" key="kmImeetingMain.kmImeetingEquipment"/>
 		</td>
 		<td width="85%"  colspan="3">
 			<c:out value="${kmImeetingMainForm.kmImeetingEquipmentNames}"></c:out>
		</td>
 	</tr>
	<tr>
		<td class="td_normal_title" width=15% title="${lfn:message('km-imeeting:kmImeetingMain.kmImeetingDevices.tip') }">
			<bean:message bundle="km-imeeting" key="kmImeetingMain.kmImeetingDevices"/>
		</td>
		<td width="85%" colspan="3" >
			<c:out value="${kmImeetingMainForm.kmImeetingDeviceNames}"></c:out>
		</td>
	</tr>
	<tr>
 		<%--会议布场要求--%>
 		<td class="td_normal_title" width=15%>
 			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdArrange"/>
 		</td>
 		<td width="85%" colspan="3" >
 			<xform:textarea property="fdArrange" value="${ fn:escapeXml(kmImeetingMainForm.fdArrange) }" showStatus="view"></xform:textarea>
		</td>
 	</tr>
 	<tr>
		<%--协助人员--%>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdAssistPersons"/>
		</td>			
		<td width="85%"  colspan="3">
			<c:if test="${not empty kmImeetingMainForm.fdAssistPersonNames }">
				<div>
	 				<img src="${LUI_ContextPath}/km/imeeting/resource/images/inner_person.png" />
					<span style="vertical-align: top;">
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdInnerPerson"/>：<c:out value="${kmImeetingMainForm.fdAssistPersonNames }"></c:out>
					</span>
				</div>
			</c:if>
			<%--外部协助人--%>
			<c:if test="${not empty kmImeetingMainForm.fdOtherAssistPersons }">
				<div>
					<img src="${LUI_ContextPath}/km/imeeting/resource/images/other_person.png" />
					<span style="vertical-align: top;">
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdOtherAssistPersons"/>：<c:out value="${kmImeetingMainForm.fdOtherAssistPersons }"></c:out>
					</span>
				</div>
			</c:if>
		</td>
	</tr>
 	<tr>
 		<%--会议组织人--%>
 		<td class="td_normal_title" width=15%>
 			<bean:message bundle="km-imeeting" key="kmImeetingTemplate.fdEmcee"/>
 		</td>
 		<td width="35%" >
 			<c:out value="${kmImeetingMainForm.fdEmceeName}"></c:out>
		</td>
		<%--组织部门--%>
 		<td class="td_normal_title" width=15%>
 			<bean:message bundle="km-imeeting" key="kmImeetingMain.docDept"/>
 		</td>
 		<td width="35%" >
 			<c:out value="${kmImeetingMainForm.docDeptName}"></c:out>
		</td>
 	</tr>
</table>