<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.authentication.user.KMSSUser"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include file="/sys/mportal/module/mobile/jsp/module.jsp">
	<template:replace name="title">
		<bean:message key="module.km.archives" bundle="km-archives"/>
	</template:replace>
	<template:replace name="head">
        <mui:cache-file name="mui-archives-index.js" cacheType="md5"/>
        <script type="text/javascript">
			window.functionalMenus=[];
			<%-- 档案鉴定 列表入口 --%>
			<kmss:authShow roles="ROLE_KMARCHIVES_APPRAISE">
				window.functionalMenus.push("auth_appraisal");
			</kmss:authShow>
			<%-- 档案销毁 列表入口 --%>
			<kmss:authShow roles="ROLE_KMARCHIVES_DESTROY">
				window.functionalMenus.push("auth_destroy");
			</kmss:authShow>
			<%-- 预归档库 列表入口 --%>
			<kmss:authShow roles="ROLE_KMARCHIVES_PREFILE_MANAGER">
				window.functionalMenus.push("auth_prefile");
			</kmss:authShow>
		</script>
	</template:replace>
	<template:replace name="content">
		<div data-dojo-type="sys/mportal/module/mobile/Module"
			data-dojo-mixins="km/archives/mobile/module/js/ModuleMixin"></div>
	</template:replace>
</template:include>