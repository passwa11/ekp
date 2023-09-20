<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="head">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/km/imeeting/mobile/maxhub/resource/css/success.css?s_cache=${MUI_Cache}">
	</template:replace>
	
	<template:replace name="title">
		签到提醒
	</template:replace>
	
	<template:replace name="content">

		<span class="tip_icon"></span>
		<p style="color: #999; font-size: 2.016rem; margin-top: 1.536rem;">
			该会议仅允许通过扫码签到
		</p>
		<button class="btn btn-primary" id="btn" style="margin-top: 2.688rem;"><bean:message key="button.back" /></button>
		
	</template:replace>


</template:include>

<script type="text/javascript">

	var redirectUrl = '${LUI_ContextPath}/km/imeeting/mobile';
	
	if(redirectUrl) {
		
		document.getElementById('btn').onclick = function(e) {
			location.href = redirectUrl;
		}
		
	}
	
</script>


