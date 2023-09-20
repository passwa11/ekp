<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<c:set var="__kmImeetingMainForm" value="${requestScope[param.formBeanName]}" />
<div class="muiImeetingInfoNewBanner">

	<div class="muiImeetingInfoMask"></div>

	<div class="muiImeetingInfoCard">
		
		<%-- 会议名称--%>
		<div class="muiImeetingInfoItem muiImeetingInfoTitle">
			<xform:text property="fdName"></xform:text>
		</div>
		
		<%-- 会议时间--%>
		<c:if test="${not empty __kmImeetingMainForm.fdHoldDate or not empty __kmImeetingMainForm.fdFinishDate }">
			<div class="muiImeetingInfoItem" style="display: flex;">
				<span><bean:message bundle="km-imeeting" key="mobile.info.time"/>：</span>
				<span class="meeting-date" style="white-space: pre-wrap;"><span id="meetingHoldDate"><c:out value="${__kmImeetingMainForm.fdHoldDate}"/></span> - <span id="meetingFinishDate"><c:out value="${__kmImeetingMainForm.fdFinishDate}"/></span></span>
			</div>
		</c:if>
		
		<!-- 重复规则-->
		<c:if test="${ not empty __kmImeetingMainForm.fdRecurrenceStr}">
			<div class="muiImeetingInfoItem" style="display: flex;">
				<span><bean:message bundle="km-imeeting" key="mobile.info.repeat"/>：</span>
				<span>
					<c:out value="${__kmImeetingMainForm.fdRepeatFrequency }"></c:out>
					<c:out value="${__kmImeetingMainForm.fdRepeatTime }">
					</c:out><c:out value="${__kmImeetingMainForm.fdRepeatUtil }"></c:out>
				</span>
			</div>
		</c:if>
		<%-- 地点 --%>
		<c:choose>
			<c:when test="${not empty __kmImeetingMainForm.fdVicePlaceNames or not empty __kmImeetingMainForm.fdOtherVicePlace }">
				<div class="muiImeetingInfoItem">
					<span><bean:message bundle="km-imeeting" key="kmImeetingMain.fdMainPlace"/>：</span>
					<xform:text property="fdPlaceName"></xform:text>
					<c:if test="${not empty __kmImeetingMainForm.fdOtherPlace}">
						<xform:text property="fdOtherPlace"></xform:text>
					</c:if>
				</div>
				<div class="muiImeetingInfoItem">
					<span><bean:message bundle="km-imeeting" key="kmImeetingMain.fdVicePlace"/>：</span>
					<xform:text property="fdVicePlaceNames"></xform:text>
					<xform:text property="fdOtherVicePlace"></xform:text>
				</div>
			</c:when>
			<c:otherwise>
				<c:if test="${not empty __kmImeetingMainForm.fdPlaceName or not empty __kmImeetingMainForm.fdOtherPlace }">
					<div class="muiImeetingInfoItem">
						<span><bean:message bundle="km-imeeting" key="mobile.info.address"/>：</span>
						<xform:text property="fdPlaceName"></xform:text>
						<c:if test="${not empty __kmImeetingMainForm.fdOtherPlace}">
							<xform:text property="fdOtherPlace"></xform:text>
						</c:if>	
					</div>
				</c:if>
			</c:otherwise>
		</c:choose>
		
		<c:if test="${not empty __kmImeetingMainForm.kmImeetingEquipmentIds }">
			<div class="muiImeetingInfoItem">
				<span><bean:message bundle="km-imeeting" key="kmImeetingMain.kmImeetingEquipment"/>：</span>
				<c:out value="${__kmImeetingMainForm.kmImeetingEquipmentNames}"></c:out>
			</div>
		</c:if>
		
		<c:if test="${not empty __kmImeetingMainForm.kmImeetingDeviceIds }">
			<div class="muiImeetingInfoItem">
				<span><bean:message bundle="km-imeeting" key="kmImeetingMain.kmImeetingDevices"/>：</span>
				<c:out value="${__kmImeetingMainForm.kmImeetingDeviceNames}"></c:out>
			</div>
		</c:if>
		
		<%-- 参会人数 --%>
		<div class="muiImeetingInfoItem">
			<bean:message bundle="km-imeeting" key="mobile.info.people.count"/>： <span name="attendCounts">-</span><bean:message key="mobile.info.person" bundle="km-imeeting"/>
		</div>
		
		<%-- 坐席安排座位号 --%>
		<c:if test="${not empty seatNumbers}">
			<div class="muiImeetingInfoItem">
				<bean:message bundle="km-imeeting" key="mobile.info.seat.number"/>：
				<c:forEach items="${seatNumbers }" var="seatNumber" varStatus="varStatus">
					<c:choose>
						<c:when test="${varStatus.index == 0}">
							<span title="${seatNumber}">
							<c:if test="${fn:length(seatNumber)>16}">
							<c:out value="${fn:substring(seatNumber,0,6)}"/><c:out value="..."/><c:out value="${fn:substring(seatNumber,fn:length(seatNumber)-10,fn:length(seatNumber))}"/>
							</c:if>
							<c:if test="${fn:length(seatNumber)<=16}">
								<c:out value="${seatNumber}"/>
							</c:if>
							<br/></span>
						</c:when>
						<c:otherwise>
							<span title="${seatNumber}" style="margin-left: 56px;">
							<c:if test="${fn:length(seatNumber)>16}">
							<c:out value="${fn:substring(seatNumber,0,6)}"/><c:out value="..."/><c:out value="${fn:substring(seatNumber,fn:length(seatNumber)-10,fn:length(seatNumber))}"/>
							</c:if>
							<c:if test="${fn:length(seatNumber)<=16}">
								<c:out value="${seatNumber}"/>
							</c:if>
							<br/>
							</span>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
		</c:if>
		
		<%-- 主持人 --%>
		<div class="muiImeetingInfoHost">
			<div class="avatar" onclick="window.dialPhone('${__kmImeetingMainForm.fdHostMobileNo}');">
				<img src="<person:headimageUrl contextPath="true"  personId="${__kmImeetingMainForm.fdHostId}" size="m" />" alt="" />
				<i class="mui mui-tel"></i>
			</div>
			<div class="name">
				<xform:text property="fdHostName"></xform:text>
				<xform:text property="fdOtherHostPerson"></xform:text>
			</div>
		</div>
			
		<%--状态--%>
		<c:import url="/km/imeeting/mobile/import/status.jsp"  charEncoding="UTF-8">
			<c:param name="status" value="${__kmImeetingMainForm.docStatus }"></c:param>
			<c:param name="isBegin" value="${isBegin }"></c:param>
			<c:param name="isEnd" value="${isEnd }"></c:param>
		</c:import>
		
	</div>

</div>
<c:if test="${not empty param.loading and param.loading == 'true' }">
	<div style="height: 100%;padding-top: 10rem;">
		<%@ include file="/sys/mobile/extend/combin/loading.jsp"%>
	</div>
</c:if>