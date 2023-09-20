<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="lbpm"%>
<lbpm:lbpmApproveModel modelName="com.landray.kmss.sys.attend.model.SysAttendMainExc"></lbpm:lbpmApproveModel> 
<c:choose>
	<c:when test="${lbpmApproveModel eq 'right'}">
		<template:include ref="lbpm.right" showQrcode="true" isEdit="true" formName="sysAttendMainExcForm" formUrl="${KMSS_Parameter_ContextPath}sys/attend/sys_attend_main_exc/sysAttendMainExc.do">
			<c:import url="/sys/attend/sys_attend_main_exc/sysAttendMainExc_editHead.jsp" charEncoding="UTF-8">
				<c:param name="approveModel" value="${lbpmApproveModel}"></c:param>
 		 	</c:import>
 		 	<c:import url="/sys/attend/sys_attend_main_exc/sysAttendMainExc_editTab.jsp" charEncoding="UTF-8">
 		 		<c:param name="approveModel" value="${lbpmApproveModel}"></c:param>
  			</c:import>
		</template:include>
	</c:when>
	<c:otherwise>
		<template:include ref="default.edit" sidebar="auto">
			<c:import url="/sys/attend/sys_attend_main_exc/sysAttendMainExc_editHead.jsp" charEncoding="UTF-8">
 		 	</c:import>
 		 	<c:import url="/sys/attend/sys_attend_main_exc/sysAttendMainExc_editTab.jsp" charEncoding="UTF-8">
  			</c:import>
		</template:include>
	</c:otherwise>
</c:choose>