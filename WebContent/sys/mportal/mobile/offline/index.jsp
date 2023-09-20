<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.offline.simple">
	<template:replace name="title">
		<bean:message bundle="sys-mportal" key="module.sys.mportal"/>
	</template:replace>
	<template:replace name="head">
		<mui:min-file name="list.css" />
		<mui:min-file name="mui-portal.js" />
		<mui:min-file name="mui-portal.css" />
	</template:replace>
	<template:replace name="content">
		<div class="muiPortalCommon gray">
			<div data-dojo-type="sys/mportal/mobile/Header"
				data-dojo-mixins="sys/mportal/mobile/header/SliderHeaderMixin"></div>

			<div class="muiPortalView">
				<div data-dojo-type="sys/mportal/mobile/CommonPanel"></div>
			</div>

			<div data-dojo-type="sys/mportal/mobile/Footer"></div>
		</div>
		<div data-dojo-type="mui/top/Top"
			data-dojo-mixins="sys/mportal/mobile/TopMixin"></div>
	</template:replace>
</template:include>