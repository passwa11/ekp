<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<ui:content title="基本信息" titleicon="lui-fm-icon-2">
	<table class="tb_normal lui-fm-noneBorderTable" width=100%>
		<!--主题-->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdName"/>
			</td>
			<td>
				<c:out value="${kmImeetingSummaryForm.fdName }"></c:out>
			</td>
		</tr>
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdTemplate"/>
			</td>			
			<td>
				<c:out value="${kmImeetingSummaryForm.fdTemplateName}"></c:out>
			</td>
		</tr>
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdHost"/>
			</td>			
			<td>
				<c:out value="${kmImeetingSummaryForm.fdHostName} ${kmImeetingSummaryForm.fdOtherHostPerson}"></c:out>
			</td>
		</tr>
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHoldDuration"/>
			</td>			
			<td>
				<span id ="fdHoldDurationHour2" ></span><bean:message key="date.interval.hour"/>
				<span id="fdHoldDurationMinSpan2"><span id ="fdHoldDurationMin" ></span><bean:message key="date.interval.minute"/></span>
				<script type="text/javascript">
				seajs.use([ 'km/imeeting/resource/js/dateUtil'], function(dateUtil) {
					//初始化会议历时
					if('${kmImeetingSummaryForm.fdHoldDuration}'){
						//将小时分解成时分
						var timeObj=dateUtil.splitTime({"ms":"${kmImeetingSummaryForm.fdHoldDuration}"});
						$('#fdHoldDurationHour2').html(timeObj.hour);
						$('#fdHoldDurationMin').html(timeObj.minute);
						if(timeObj.minute){
							$('#fdHoldDurationMinSpan2').show();
						}else{
							$('#fdHoldDurationMinSpan2').hide();
						}
					}
				});
				</script>
			</td>
		</tr>
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdPlace"/>
			</td>
			<td>
				<c:out value="${kmImeetingSummaryForm.fdPlaceName} ${kmImeetingSummaryForm.fdOtherPlace}"></c:out>
			</td>
		</tr>
		<tr>
			<td class="tr_normal_title" width=30%>
	 			<bean:message bundle="km-imeeting" key="kmImeetingSummary.docCreator"/>
	 		</td>
	 		<td>
	 			<c:out value="${kmImeetingSummaryForm.docCreatorName }"></c:out>
			</td>
		</tr>
		<tr>
			<%--创建时间--%>
	 		<td class="tr_normal_title" width=30%>
	 				<bean:message bundle="km-imeeting" key="kmImeetingSummary.docCreateTime"/>
	 		</td>
	 		<td>
	 			<c:out value="${kmImeetingSummaryForm.docCreateTime }"></c:out>
			</td>
		</tr>
		<%-- 所属场所 --%>
		<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp" charEncoding="UTF-8">
               <c:param name="id" value="${kmImeetingSummaryForm.authAreaId}"/>
               <c:param name="rightModel" value="true"/>
         </c:import> 
	</table>
</ui:content>