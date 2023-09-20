<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<template:include ref="default.print">
<template:replace name="head">
</template:replace>
	<template:replace name="title">
	</template:replace>
<template:replace name="content">
	<!-- 打印机制 -->
	<c:import url="/sys/print/import/sysPrintMain_view.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="hrRatifyRemoveForm"/>
		<c:param name="modelName" value="com.landray.kmss.hr.ratify.model.HrRatifyTemplate"/>
		
<c:param name="docSubject" value="${hrRatifyRemoveForm.docSubject}"/>
	</c:import>
<center>
</center>
</template:replace>
		
</template:include>

