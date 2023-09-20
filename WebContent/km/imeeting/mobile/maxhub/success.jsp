<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="head">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/km/imeeting/mobile/maxhub/resource/css/success.css?s_cache=${MUI_Cache}">
	</template:replace>
	
	<template:replace name="title">
		操作成功
	</template:replace>
	
	<template:replace name="content">
		
		<span class="icon icon-success"></span>
		<p style="color: #999; font-size: 2.016rem; margin-top: 1.536rem;">
			<c:if test="${tip == 'success' }">
				您已加入会议
			</c:if>
			<c:if test="${tip == 'exist' }">
				您已在会议中
			</c:if>
		</p>
		<button class="btn btn-primary" id="btn" style="margin-top: 2.688rem;">返回</button>
		
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


