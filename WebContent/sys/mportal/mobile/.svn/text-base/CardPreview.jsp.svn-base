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
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mportal/sys_mportal_card/css/cardPreview.css"></link>
	</template:replace>
	<template:replace name="content">
	    <div class="card_background">
		    <div id="dragWrap" class="card_content element" data-dojo-type="sys/mportal/mobile/CardPreview"
			     data-dojo-props="cardId:'${param.cardId}'">
		    </div>
	    </div>	
	    <script>
		</script>		
	</template:replace>
</template:include>


