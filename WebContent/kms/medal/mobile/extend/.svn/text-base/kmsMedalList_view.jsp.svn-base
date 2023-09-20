<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.landray.kmss.util.StringUtil"%>


<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="title">
	</template:replace>
	<template:replace name="head">										
		<mui:cache-file name="list.css" cacheType="md5"/>
		<link rel="stylesheet" href="./lib/swiper.min.css">
		<link rel="stylesheet" href="./style/medal.css">
		<mui:cache-file name="mui-kms-medal-extend.js"/>
		
		<script>
			require(["dojo/ready",'${LUI_ContextPath}/kms/diploma/mobile/extend/lib/swiper.min.js'], function(ready,Swiper){
			    
				ready(function(){
					
					var mySwiper = new Swiper('.swiper-container', {
						slidesPerView: "auto",
						// spaceBetween: 10,
						// slidesOffsetBefore: 28,
						// normalizeSlideIndex: false,
						// resistanceRatio: 0,
						// touchRatio: 2,
						centeredSlides: true,
						// initialSlide: 0
							
						//加上这两行是因为数据在swiper初始化之后才异步请求到，swiper则无法正确scan有多少个slide（实际上找到一个待循环模板），所以划不动。
						//https://segmentfault.com/a/1190000002962202
						observer:true,//修改swiper自己或子元素时，自动初始化swiper  
					    observeParents:true,//修改swiper的父元素时，自动初始化swiper
					})
	
				})
			})
		</script>
	</template:replace>
	<template:replace name="content">
	
			<div class="muiTcourse-bg-black">
				<div class="swiper-container swiper-container-achieve">

					<div  class="swiper-wrapper" data-dojo-type="mui/list/JsonStoreList" 
							data-dojo-mixins="kms/medal/mobile/extend/js/kmsMedalViewMixin"
							data-dojo-props="selectType:'${JsParam.selectType}',fdModelName:'${JsParam.fdModelName}',
											fdModelId:'${JsParam.fdModelId}'">
					</div>
				</div>
			</div>
	</template:replace>
</template:include>
