<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="head">
		<mui:min-file name="mui-imeeting-view.css"/>
	</template:replace>
	<template:replace name="title">
		<c:if test="${not empty kmImeetingBookForm.fdName}">
			<c:out value="${ kmImeetingBookForm.fdName}"></c:out>
		</c:if>
		<c:if test="${empty kmImeetingBookForm.fdName}">
			<bean:message bundle="km-imeeting" key="module.km.imeeting"/>
		</c:if>
	</template:replace>
	
	<template:replace name="content">
		<div id="scrollView" data-dojo-type="mui/view/DocScrollableView">
			<p class="simpleMeetingH">
				<c:out value="${kmImeetingBookForm.fdName }"></c:out>
			</p>
			<div class="simpleMeetingHBackground"></div>
			<ul class="simpleMeetingUl">
				<%-- 主持人 --%>
				<li class="simpleMeetingLi">
					<div class="simpleMeetingLiInner">
						<div class="simpleMeetingDot"></div>
						<p class="simpleMeetingTitle">
							<bean:message bundle="km-imeeting" key="kmImeetingBook.docCreator"/>
						</p>
						<p class="simpleMeetingContent">
							<img class="simpleMeetingHeader" src="<person:headimageUrl contextPath="true" personId="${kmImeetingBookForm.docCreatorId}" size="m" />" alt="" />
							<div class="simpleMeetingName"><c:out value="${kmImeetingBookForm.docCreatorName }"></c:out></div>
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
								<c:out value="${kmImeetingBookForm.fdHoldDate}"></c:out>
							</span> ~
							<span class="simpleMeetingDate">
								<c:out value="${kmImeetingBookForm.fdFinishDate}"></c:out>
							</span>
						</p>
					</div>
				</li>
			</ul>
			
		</div>	
	</template:replace>
</template:include>