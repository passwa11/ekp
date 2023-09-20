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
		<mui:cache-file name="mui-review.js" cacheType="md5"/>
		<mui:cache-file name="mui-review-ding.css" cacheType="md5"/>
	</template:replace>
	<template:replace name="content">
		<div class="mui_review_platform_home">
			<div class="mui-review-platform-nav">
				<ul class="clearfix">
					<li id="approval">
						<div class="mui-review-platform-nav-top">
							<img src="./resource/images/todo@2x.png" alt=""> <span class="mui-num"></span>
						</div>
						<p>待处理</p>
					</li>
					<li id="approved">
						<div class="mui-review-platform-nav-top">
							<img src="./resource/images/dealed@2x.png" alt="">
						</div>
						<p>已处理</p>
					</li>
					<li id="create">
						<div class="mui-review-platform-nav-top">
							<img src="./resource/images/initiate@2x.png" alt="">
						</div>
						<p>已发起</p>
					</li>
					<li id="all">
						<div class="mui-review-platform-nav-top">
							<img src="./resource/images/all@2x.png" alt="">
						</div>
						<p>全部</p>
					</li>
				</ul>
			</div>
			<div data-dojo-type="km/review/mobile/resource/js/ding/ReviewCategoryView"
				 data-dojo-props=""
			  	 class="mui-review-platform-template-content"></div>
		</div>
	</template:replace>
</template:include>
<script>
	require(['mui/util','dojo/query','dojo/ready','dojo/request','dojo/dom-style','dojo/touch','dojo/on'],
			function(util,query,ready,request,domStyle,touch,on){
		ready(function(){
			var countUrl = "/km/review/km_review_index/kmReviewIndex.do?method=list&pagingSetting=&q.mydoc=approval"
			request(util.formatUrl(countUrl), {
				handleAs : 'json',
				method : 'post',
				data : ''
			}).then(function(result){
				var totalSize = result.page.totalSize;
				if(totalSize > 0){
					var numNode = query('.mui-review-platform-nav .mui-num')[0];
					numNode.innerHTML=totalSize;
					domStyle.set(numNode,'display','block');
				}
			},function(e){
				window.console.error("获取待处理数失败,error:" + e);
			});
		});
		query('.mui-review-platform-nav li').map(function(item){
			on(item,touch.press,function(evt){
				onReviewNavItemClick(evt);
			});
			
		});
		window.onReviewNavItemClick = function(evt){
			var type = evt.currentTarget.id;
			var url = '/km/review/mobile/listview_ding.jsp?type='+type;
			location.href=util.formatUrl(url);
		}
	});
	
</script>
