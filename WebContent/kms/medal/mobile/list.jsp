<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include ref="mobile.list">
	<template:replace name="title">
		${zone_TA_text}${lfn:message('kms-medal:kmsMedalMobile.my.medal') }-${lfn:message('kms-medal:kmsMedalMobile.list.page') }
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/kms/medal/mobile/resource/css/medal.css" />
		<script>

		</script>
	</template:replace>
	<template:replace name="content">
		<!-- 我的勋章-列表页 Starts -->
		<div class="_mui_medal_list_wrap">				
			<%--我的勋章 --%>
			<div data-dojo-type="kms/medal/mobile/list/kmsMedalListTitle"
				data-dojo-props="title:'${zone_TA_text}${lfn:message('kms-medal:kmsMedalMobile.my.medal') }',fdMedalNum:'${fdMedalNum}',zone_TA_text:'${zone_TA_text}'">
				
				<c:if test="${fdMedalNum ne '0'}">
					<div data-dojo-type="sys/mportal/mobile/card/JsonStoreCardList" 
						data-dojo-mixins="kms/medal/mobile/list/kmsMedalListMixin"
						data-dojo-props="personId:'${param.personId}',fdMedalNum:'${fdMedalNum}'">
					</div>
				</c:if>
				
				<c:if test="${fdMedalNum eq '0'}">
					<div class="mui_medal_list_content mui_medal_list_default">
						<div class="mui_medal_list_default_imgContent">
							<div class="mui_medal_list_default_img"></div>
							<p class="mui_medal_txt">${zone_TA_text}${lfn:message('kms-medal:kmsMedalMobile.has.no.medal') }</p>
						</div>
					</div>
				</c:if>
				
			</div>
		</div>
		<!-- 我的勋章-列表页 Ends -->
	
	</template:replace>
</template:include>

