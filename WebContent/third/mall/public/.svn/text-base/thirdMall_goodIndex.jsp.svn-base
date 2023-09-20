<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> 
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.edit" >
	 <template:replace name="head">
		<script type="text/javascript">
			seajs.use(['theme!profile']);
			seajs.use(['theme!iconfont']);
			Com_IncludeFile("common.css","${LUI_ContextPath}/third/mall/resource/css/","css",true);

		</script>
		 <%@ include file="/third/mall/public/thirdMall_search_script.jsp"%>
		 <script>
			 $(document).ready(function(){
				 iframeSearch();
			 });
			 var __taleType = '${orderby}';
			 function iframeSearch(){
				 // 打开iframe子页面
				 var keyWord ='${keyWord}'.length > 0?encodeURI('${keyWord}'):'';
				 var url = '<c:url value="thirdMallPublic.do" />?method=getList'+
						 '&rowsize='+rowsize+'&pageno='+pageno+'&orderby=${orderby}'+'&keyWord='+keyWord+
						 '&industryId='+industryIds+'&areaId='+areaIds+'&fdKeyType=${fdKey}&tabType=${orderby}&fdType=${fdType}'
				 			+'&cateId='+cateIds+'&formatId='+formatIds+'&componentType='+componentTypeIds;
				 			+'&n='+Math.random()

				 var iframeObj = document.getElementById("iframeSearch");
				 iframeObj.src = url;
				 seajs.use( ['lui/dialog' ], function(dialog) {
					 window.loadingInfo =dialog.loading();
				 });
				 setSearchIframeHeight(iframeObj);
				 scrollTo(0,0);
			 }

			 function setSearchIframeHeight(iframe){
				 try{
					 if(iframe.attachEvent){
						 iframe.attachEvent("onload", function(){
							 if(window.loadingInfo){
								 window.loadingInfo.hide();
								 window.loadingInfo =null;
							 }
							 /**最好的办法是计算页面所有元素的高度，这里因为时间问题，暂时先根据最大值来写吧*/
							 var height = window.top.innerHeight ;
							 //全部应用高度iframe重新计算
							 if("all" == __taleType){
								 $("#fy-filter-container").find(".fy-filter-select-collapse").addClass("expand");
								 // 筛选器高度
								 var sHeight = $("#fy-filter-container").outerHeight(true);
								 // 内容区margin高度(top15 + bottom80)
								 var bHeight = 15 + 80;
								 // 分页高度
								 var pHeight = 32;
								 height = height + sHeight + bHeight + pHeight;
								 //默认收缩
								 setTimeout(function(){
									 $("#fy-filter-container").find(".fy-filter-select-collapse").removeClass("expand");
								 },5);
							 }

							 $(iframe).css({height: height});
							 parent.document.getElementById("goods_listview_${orderby}").style.height = height +"px";
							 parent.setIframeHeight(height);
						 });
						 return;
					 }else{
						 iframe.onload = function(){
							 if(window.loadingInfo){
								 window.loadingInfo.hide();
								 window.loadingInfo =null;
							 }
							 /**最好的办法是计算页面所有元素的高度，这里因为时间问题，暂时先根据最大值来写吧*/
							 var height = window.top.innerHeight ;

							 //全部应用高度iframe重新计算
							 if("all" == __taleType){
								 //全部应用iframe添加唯一标志，以便iframe.js更改默认设置样式 - css('overflow-y','auto')
								 //$("body.lui_config_form").addClass("allCloudMallContainer");
								 $("#fy-filter-container").find(".fy-filter-select-collapse").addClass("expand");
								 // 筛选器高度
								 var sHeight = $("#fy-filter-container").outerHeight(true);
								 // 内容区margin高度(top15 + bottom80)
								 var bHeight = 15 + 80;
								 // 分页高度
								 var pHeight = 32;
								 height = height + sHeight + bHeight + pHeight;
								 //默认收缩
								 setTimeout(function(){
									 $("#fy-filter-container").find(".fy-filter-select-collapse").removeClass("expand");
								 },5);
							 }

							 $(iframe).css({height: height});
							 parent.document.getElementById("goods_listview_${orderby}").style.height = height +"px";
							 parent.setIframeHeight(height);
						 };
						 return;
					 }
				 }catch(e){
					 throw new Error('setIframeHeight Error');
				 }
			 }
		 </script>
	 </template:replace>
	 <template:replace name="content">
		 <div class="fy-process-list-container">
			 <div class="fy-process-list-center">
				 <c:if test="${orderby=='all'}">
					 <%@ include file="/third/mall/public/thirdMall_search_html.jsp"%>
				 </c:if>
				 <!-- 列表 start -->
				 <iframe id='iframeSearch' frameborder="0" scrolling="no" marginheight="no">
				 </iframe>
				 <!-- 列表 end -->
			 </div>
		 </div>
	</template:replace>
</template:include>