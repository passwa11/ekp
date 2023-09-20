<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.list">
	<template:replace name="title">
		${lfn:message('sys-fans:sysFansMain.my')}
	</template:replace>
	<template:replace name="head">
	   <link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/sys/fans/mobile/list/style/list.css" />
	</template:replace>
	<template:replace name="content">
		<c:import url="/sys/fans/mobile/listview.jsp" charEncoding="UTF-8">
		 </c:import>
	</template:replace>
</template:include>


