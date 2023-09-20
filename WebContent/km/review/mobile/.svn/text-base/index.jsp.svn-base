<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@page import="com.landray.kmss.sys.authentication.user.KMSSUser"%>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.list" canHash="true">
	<template:replace name="title">
		<c:if test="${param.moduleName!=null && param.moduleName!=''}">
			<c:out value="${param.moduleName}"></c:out>
		</c:if>
		<c:if test="${param.moduleName==null || param.moduleName==''}">
			<c:out value="${lfn:message('km-review:module.km.review')}"></c:out>
		</c:if>
	</template:replace>
	<template:replace name="head">
		<mui:cache-file name="mui-nav.js" cacheType="md5"/>
		<mui:cache-file name="mui-sysCate.js" cacheType="md5" />
		<mui:cache-file name="mui-review.js" cacheType="md5"/>
		<mui:cache-file name="mui-review-list.css" cacheType="md5"/>
	</template:replace>
	<template:replace name="content">
		<div data-dojo-type="km/review/mobile/resource/js/list/ReviewListRefreshMixin" style="display: none">
		</div>
		 <c:import url="/km/review/mobile/listview.jsp" charEncoding="UTF-8"></c:import>
	
	     <%--  新建按钮   --%>
		<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom">
			<kmss:authShow roles="ROLE_KMREVIEW_CREATE">
		  		<li data-dojo-type="mui/tabbar/CreateButton" data-dojo-mixins="mui/syscategory/SysCategoryMixin"
			  		data-dojo-props="icon1:'',createUrl:'/km/review/mobile/add.jsp?fdTemplateId=!{curIds}',mainModelName:'com.landray.kmss.km.review.model.KmReviewMain',
			  		modelName:'com.landray.kmss.km.review.model.KmReviewTemplate'"><bean:message  bundle="km-review"  key="kmReviewMain.opt.create" /></li>
	  		</kmss:authShow>
	  		<!-- 模板编辑按钮 -->
	  		<kmss:authShow roles="ROLE_KMREVIEW_BACKSTAGE_MANAGER">
	  			<li data-dojo-type="km/review/mobile/resource/js/button/TemplateSettingButton" data-dojo-mixins="mui/syscategory/SysCategoryMixin"
			  		data-dojo-props="icon1:'',createUrl:'/km/review/km_review_template/kmReviewTemplate.do?method=index',mainModelName:'com.landray.kmss.km.review.model.KmReviewMain',
			  		modelName:'com.landray.kmss.km.review.model.KmReviewTemplate'"><bean:message  bundle="km-review"  key="kmReviewTemplate.opt.create" /></li>
	  		</kmss:authShow>
		</ul>
	</template:replace>
</template:include>
