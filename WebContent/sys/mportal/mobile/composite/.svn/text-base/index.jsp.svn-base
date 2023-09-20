<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="mobile.simple" tiny="true">
	<template:replace name="title">
	</template:replace>
	<template:replace name="head">
		<mui:cache-file name="mui-portal.css" cacheType="md5" />
		<mui:cache-file name="list-tiny.css" cacheType="md5" />
		<mui:cache-file name="mui-portal-portlets.css" cacheType="md5" />
		<mui:cache-file name="mui-portal.js" cacheType="md5" />
		<mui:cache-file name="mui-portal-portlets.js" cacheType="md5" />
	</template:replace>
	<template:replace name="content">
		<div data-dojo-type="sys/mportal/mobile/common" 
			 data-dojo-mixins="sys/mportal/mobile/composite/initMixin"
			 data-dojo-props="compositeId:'${JsParam.fdCompositeId}'"
			 class="mui_ekp_portal_container mui_ekp_portal_composite_container clearfix">
			 
			 <div id="searchId" class="muiHeaderBox"
				data-dojo-type="sys/mportal/mobile/composite/header/Header"
				data-dojo-mixins="sys/mportal/mobile/composite/header/SliderHeaderCompositeMixin"
				data-dojo-props="height:'5rem',pageId:'${JsParam.fdId}'"></div>			
		</div> 
		<div id="sys_mportal_Tabbar_bottom" style="display:none;" data-dojo-type="sys/mportal/mobile/composite/tab/TabBar"></div>
	</template:replace>
</template:include>


