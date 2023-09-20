<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="head">
		<mui:min-file name="mui-imeeting-view.css"/>
		<mui:min-file name="mui-imeeting.js"/>
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
		<div id="scrollView" data-dojo-type="mui/view/DocScrollableView">
			<p class="simpleMeetingH">
				<c:out value="${kmImeetingMainForm.fdName }"></c:out>
			</p>
			<div class="simpleMeetingHBackground"></div>
			<ul class="simpleMeetingUl">
				<%-- 主持人 --%>
				<li class="simpleMeetingLi">
					<div class="simpleMeetingLiInner">
						<div class="simpleMeetingDot"></div>
						<p class="simpleMeetingTitle">
							<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHost"/>
						</p>
						<p class="simpleMeetingContent">
							<img class="simpleMeetingHeader" src="<person:headimageUrl personId="${kmImeetingMainForm.fdHostId}" size="m" />" alt="" />
							<div class="simpleMeetingName"><c:out value="${kmImeetingMainForm.fdHostName }"></c:out></div>
						</p>
					</div>
				</li>
				<%-- 开始时间 --%>
				<li class="simpleMeetingLi">
					<div class="simpleMeetingLiInner">
						<div class="simpleMeetingDot"></div>
						<p class="simpleMeetingTitle">
							<bean:message bundle="km-imeeting" key="kmImeetingMain.fdDate"/>
						</p>
						<p class="simpleMeetingContent">
							<span class="simpleMeetingDate">
								<c:out value="${kmImeetingMainForm.fdHoldDate}"></c:out>
							</span>
							<span class="simpleMeetingDuration"><bean:message bundle="km-imeeting" key="kmImeetingMain.fdHoldDuration.tip1"/><span id="duration">${kmImeetingMainForm.fdHoldDurationHour }</span><bean:message bundle="km-imeeting" key="kmImeetingMain.fdHoldDuration.tip2"/></span>
						</p>
					</div>
				</li>
				<%-- 参与人 --%>
				<li class="simpleMeetingLi">
					<div class="simpleMeetingLiInner">
						<div class="simpleMeetingDot"></div>
						<p class="simpleMeetingTitle">
							<bean:message bundle="km-imeeting" key="kmImeetingMain.fdAttendPersons"/>
						</p>
						<p class="simpleMeetingContent">
							<div data-dojo-type="mui/person/PersonList"
								data-dojo-props="detailTitle:'<bean:message bundle="km-imeeting" key="kmImeetingMain.fdAttendPersons"/>',personId:'${kmImeetingMainForm.fdAttendPersonIds }'">
							</div>
						</p>
					</div>
				</li>
			</ul>
			<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>
				<li data-dojo-type="mui/back/BackButton"></li>
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
				<li data-dojo-type="mui/tabbar/TabBarButtonGroup" style="float: right;" data-dojo-props="icon1:'mui mui-more'">
		    		<div data-dojo-type="mui/back/HomeButton"></div>
		    	</li>
			</ul>
		</div>	
	</template:replace>
</template:include>
<%@ include file="/km/imeeting/mobile/view_simple_js.jsp"%>