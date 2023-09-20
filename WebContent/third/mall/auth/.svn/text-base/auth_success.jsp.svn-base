<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>	
<template:include ref="default.message">
	<template:replace name="title">云商城开启授权成功</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/third/mall/resource/css/mall.css?s_cache=${MUI_Cache}"></link>
	</template:replace>
	<template:replace name="body">
		<div class="luiMallAuthSuc">
		    <div class="cm-start-mask"></div>
		    <div class="cm-suc">
		      <div class="cm-suc-wrap">
		        <div class="cm-suc-top">
		          <div>恭喜您，已授权成功！请返回系统继续配置。</div>
		        </div>
		        <div class="cm-suc-bottom">
		          <p>已开通以下服务：</p>
		          <ul>
		            <c:if test="${fn:length(fdBusKeys)>0}">
			        	<c:forEach var="item" items="${fdBusKeys}">
					         <li>${item.messageKey}</li>
			        	</c:forEach>
			        </c:if>
			       
			        <c:if test="${fn:length(fdBusKeys)==0}">
			        	<li class="lui_mall_nodata">暂不支持相关服务！</li>
			        </c:if>
		          </ul>
		          <div><span id="_timeArea" class="prompt_content_timer"></span></div>
		        </div>
		      </div>
		    </div>
		  </div>
	</template:replace>
</template:include>
<script>
	var time = 0;
	var intvalId =null;
	function timeInteval(){
		var _timeArea = document.getElementById("_timeArea");
		if(time==2){
			window.location='${LUI_ContextPath}/sys/profile/index.jsp#integrate/saas/mall';
			return;
		}
		if(_timeArea) {
			_timeArea.innerHTML = "<bean:message key="return.autoClose"/>".replace('{0}', 2 - time);
		}
		time =time+1;
		intvalId=setTimeout("timeInteval()",1000);
	}
	Com_AddEventListener(window,"load",function(){
		try {
			timeInteval();
		}catch(e){
			if(window.console) {
				window.console.warn(e);
			}
		}
	});
</script>