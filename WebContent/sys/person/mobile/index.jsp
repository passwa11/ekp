<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include file="/sys/mportal/module/mobile/jsp/module.jsp">
	<template:replace name="title">
		个人中心
	</template:replace>
	<template:replace name="head">
		<mui:min-file name="mui-person-index.js" />
	</template:replace>
	<template:replace name="content">
		<div data-dojo-type="sys/mportal/module/mobile/Module"
			data-dojo-mixins="sys/person/mobile/js/ModuleMixin"></div>
	</template:replace>
</template:include>

