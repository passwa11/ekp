<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@page import="com.landray.kmss.sys.authentication.user.KMSSUser"%>
<%
	KMSSUser user = UserUtil.getKMSSUser(request);
	request.setAttribute("user", user);
%>
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
		<mui:cache-file name="mui-review.js" cacheType="md5"/>
		<mui:cache-file name="mui-review-list.css" cacheType="md5"/>
		<mui:cache-file name="mui-review-ding.css" cacheType="md5"/>
	</template:replace>
	<template:replace name="content">
		
		<%-- 导航头部，通常放导航页签、搜索 --%>
		<div data-dojo-type="mui/header/Header" data-dojo-props="height:'4.4rem'" class="muiHeaderNav">
		    <div data-dojo-type="mui/nav/MobileCfgNavBar" 
				 data-dojo-props="modelName:'com.landray.kmss.km.review.model.KmReviewMain'"
				 data-dojo-mixins="km/review/mobile/resource/js/ding/ReviewNavMixin"> 
			</div>
			<div data-dojo-type="mui/search/SearchBar"
					data-dojo-props="modelName:'com.landray.kmss.km.review.model.KmReviewMain'">
				</div>
			<%-- 搜索 --%>
			<c:if test="${empty HtmlParam.fdTemplate }">
				<div class="muiHeaderCategoryWrap">
					<div class="muiHeaderItemRight" 
						data-dojo-type="mui/catefilter/FilterItem"
						data-dojo-mixins="mui/syscategory/SysCategoryMixin,km/review/mobile/resource/js/ding/ReviewCategoryFilterMixin"
						data-dojo-props="modelName: 'com.landray.kmss.km.review.model.KmReviewTemplate',catekey: 'fdTemplate'"></div>
				</div>
			</c:if>
		</div>
		
		<%-- 筛选器头部，通常放排序、标签筛选器、重要筛选器、筛选器。  
			 	注1: 根据nav.json定义的headerTemplate进行渲染
			 	注2: 考虑到移动端大小问题，业务应该在排序、标签筛选器、重要筛选器三个组件中三选一
		--%>
		<div data-dojo-type="mui/header/NavHeader">
		    <%-- 排序（创建时间）  --%>
			<div data-dojo-type="mui/sort/SortItem" 
			    data-dojo-props="name:'docCreateTime',subject:'<bean:message  bundle="km-review" key="mui.kmReviewMain.docCreatetime" />',value:'down'">
			</div>
		    <%-- 排序（结束时间）  --%>
			<div data-dojo-type="mui/sort/SortItem" 
			    data-dojo-props="name:'docPublishTime',subject:'<bean:message  bundle="km-review" key="mui.kmReviewMain.docEndTime" />',value:''">
			</div>
			
			<%-- 默认筛选器头部模板 --%>
			<div class="muiHeaderItemRight" 
				data-dojo-type="mui/property/FilterItem"
				data-dojo-mixins="km/review/mobile/resource/js/header/ReviewPropertyMixin,km/review/mobile/resource/js/ding/DingReviewPropertyMixin"></div>
			
		</div>
		
		<div data-dojo-type="mui/list/NavView">
			<%--  默认列表模板   --%>
			<ul class="muiList"
				data-dojo-type="mui/list/HashJsonStoreList" 
				data-dojo-mixins="km/review/mobile/resource/js/ding/ReviewItemListMixin,km/review/mobile/resource/js/ding/HashJsonStoreListMixin">
			</ul>
		</div>
	</template:replace>
</template:include>
<script>
var modelName = 'com.landray.kmss.km.review.model.KmReviewMain';
window.navMemory={
		[modelName]:[
		 <c:if test="${JsParam.type=='create'}">           
          {
      		"url" : "/km/review/km_review_index/kmReviewIndex.do?method=list&q.mydoc=create&orderby=docCreateTime&ordertype=down",
      		"text" : "${ lfn:message('km-review:kmReviewMain.mobile.create.mine') }"
      	  }
      	 </c:if>
      	<c:if test="${JsParam.type=='approval'}"> 
      	{
      		"url" : "/km/review/km_review_index/kmReviewIndex.do?method=list&q.mydoc=approval&orderby=docCreateTime&ordertype=down", 
      		"text" : "${ lfn:message('km-review:kmReviewMain.mobile.approval.mine') }", 
      		"headerTemplate" : "/km/review/mobile/resource/js/ding/ApprovalReviewPropertyTemplate.js"
      	}
      	</c:if>
      	<c:if test="${JsParam.type=='approved'}"> 
      	{ 
      		"url" : "/km/review/km_review_index/kmReviewIndex.do?method=list&q.mydoc=approved&orderby=docCreateTime&ordertype=down", 
      		"text" : "${ lfn:message('km-review:kmReviewMain.mobile.approved.mine') }"
      	}
      	</c:if>
      	<c:if test="${JsParam.type=='all'}"> 
      	{
      		"url" : "/km/review/km_review_index/kmReviewIndex.do?method=list&orderby=docCreateTime&ordertype=down&q.fdTemplate=${HtmlParam.fdTemplate}", 
      		"text" : "${ lfn:message('km-review:kmReviewMain.mobile.all.mine') }"
      	}
      	</c:if>
      ]
}
</script>