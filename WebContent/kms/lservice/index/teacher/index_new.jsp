<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include rwd="true" spa="true"
	ref="expand.list">
	<template:replace name="title">
		<c:out value="${ lfn:message('kms-lservice:kmsLservice.homePage') }"></c:out>-${lfn:message('kms-lservice:lservice.role.teacher')}
	</template:replace>
	
	<template:replace name="path">
		<c:import url="/kms/lservice/index/teacher/path.jsp"
			charEncoding="UTF-8">
			<c:param name="title"
				value="${lfn:message('kms-lservice:lservice.role.teacher') }"></c:param>
		</c:import>
	</template:replace>

	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/kms/lservice/index/common/style/person_common.css?s_cache=${ LUI_Cache }" />
		<link rel="Stylesheet" href="${LUI_ContextPath}/kms/lservice/index/student/calendar/css/calendar_lservice.css" />
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/kms/lservice/index/common/style/icon/iconfont.css?s_cache=${ LUI_Cache }" />
	</template:replace>
	
	<template:replace name="banner">
		<c:import url="/kms/lservice/index/teacher/banner.jsp" charEncoding="UTF-8">
			<c:param name="modelName"
				value="com.landray.kmss.kms.learn.model.KmsLearnMain"></c:param>
		</c:import>
	</template:replace>
	
	<template:replace name="nav">
		<c:import url="/kms/lservice/index/teacher/nav.jsp"
			charEncoding="utf-8">
			<c:param name="modelName"
				value="com.landray.kmss.kms.lservice.model.PStudyCenter"></c:param>
			<c:param name="type" value="teacher" />
		</c:import>

	</template:replace>

	<template:replace name="content">
		<div>
			<div class="lservice_student_mission">
				<c:import url="/kms/lservice/index/teacher/index_mission.jsp" charEncoding="utf-8"></c:import>				
			 	<kmss:ifModuleExist path="/kms/train">
					<kmss:auth requestURL="/kms/train">
				 	<div style="margin-top: 50px;" class="lservice_student_mission_title"><img style="height: 23px;margin-right: 11px;vertical-align: bottom;" src="${LUI_ContextPath}/kms/lservice/index/common/style/img/pic_2.png">${lfn:message('kms-lservice:kmsLservice.teacher.teaching.calendar') }</div>
			    		<c:import url="/kms/lservice/index/teacher/calendar/kms_lservice_calendar.jsp">
				 		</c:import>
					</kmss:auth>
				</kmss:ifModuleExist>
			</div>
		</div>
	</template:replace>
	
</template:include>

