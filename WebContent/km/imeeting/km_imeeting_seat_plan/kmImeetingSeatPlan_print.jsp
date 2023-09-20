<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page import="java.util.Date"%>
<template:include ref="default.print" sidebar="no">
<template:replace name="head">
	<link href="${LUI_ContextPath}/km/imeeting/resource/css/seat.css?s_cache=${LUI_Cache}" rel="stylesheet">
</template:replace>
<template:replace name="title">
	<c:out value="${kmReviewMainForm.docSubject }"></c:out>
</template:replace>
<template:replace name="toolbar">
	<ui:toolbar  id="toolbar" layout="sys.ui.toolbar.float" count="9"> 
	  		 <ui:button text="${ lfn:message('button.print') }"   onclick="printDoc();">
		    </ui:button>
		      <ui:button text="${ lfn:message('button.close') }"   onclick="window.close();">
		    </ui:button>
	</ui:toolbar>
</template:replace>
<template:replace name="content">

<form name="kmImeetingSeatPlanForm" method="post" action="<c:url value="/km/imeeting/km_imeeting_seat_plan/kmImeetingSeatPlan.do"/>">
<center>
	<c:choose>
		<c:when test="${kmImeetingSeatPlanForm.fdIsTopicPlan == 'true' }">
			<c:forEach var="i" begin="1" end="${kmImeetingSeatPlanForm.fdTopicSize}">
			    <div class="lui_seat_print_wrap">
				    <p class="lui_form_subject" id="plan_subject_${i}">
					</p>
					<div id="seat_${i}" class="lui_seat_print_content">
					</div>			
				</div>
			</c:forEach>
		</c:when>
		<c:otherwise>
		    <div class="lui_seat_print_wrap">
			    <p class="lui_form_subject">
					<c:if test="${not empty kmImeetingSeatPlanForm.docSubject}">
						<c:out value="${kmImeetingSeatPlanForm.docSubject}" />
					</c:if>
				</p>
				<div id="seat" class="lui_seat_print_content">
				</div>			
			</div>
		</c:otherwise>
	</c:choose>
	    
	<%@include file="/km/imeeting/km_imeeting_seat_plan/kmImeetingSeatPlan_print_js.jsp"%>

</center>
</form>

<c:set var="frameShowTop" scope="page" value="${(empty param.showTop) ? 'yes' : (param.showTop)}"/>
<c:if test="${frameShowTop=='yes' }">
	<ui:top id="top"></ui:top>
	<kmss:ifModuleExist path="/sys/help">
		<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
	</kmss:ifModuleExist>
</c:if>
</template:replace>
		
</template:include>

