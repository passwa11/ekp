<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@page import="com.landray.kmss.sys.authentication.user.KMSSUser"%>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.list" canHash="true">
	<template:replace name="title">
		<bean:message bundle="km-review" key="kmReviewDocumentLableName.feedbackInfo" />
	</template:replace>
	<template:replace name="head">
		<mui:cache-file name="mui-feedbackInfo.css" cacheType="md5"/>
	</template:replace>
	<template:replace name="content">
		<div data-dojo-type="mui/header/Header" data-dojo-props="height:'4.4rem'" class="muiHeaderNav">
			<div data-dojo-type="km/review/mobile/km_review_feedback_info/js/list/FeedbackInfoHeader"
				 data-dojo-props="modelId:'${JsParam.modelId }',modelName:'${JsParam.modelName }'">
			</div>
		</div>
		<div data-dojo-type="mui/list/StoreScrollableView">
			<ul class="muiCirculationList"
				data-dojo-type="mui/list/JsonStoreList"
				data-dojo-mixins="km/review/mobile/km_review_feedback_info/js/list/FeedbackInfoListMixin"
				data-dojo-props="url:'/km/review/km_review_feedback_info/kmReviewFeedbackInfo.do?method=listdata&fdModelName=com.landray.kmss.km.review.model.KmReviewMain&fdModelId=${JsParam.modelId}&s_ajax=true&orderby=docCreateTime&ordertype=down',lazy:false,isNewVersion:true">
			</ul>
		</div>
	</template:replace>
</template:include>
