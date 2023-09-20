<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.util.SpringBeanUtil,java.util.Iterator,net.sf.json.JSONObject,com.landray.kmss.dbcenter.echarts.application.service.IDbEchartsNavTreeShowService" %>
<template:include ref="default.simple">
	<template:replace name="body">
		<script>
			Com_IncludeFile("data.js");
		</script>
		<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}dbcenter/echarts/application/navTree/css/nav.css?s_cache=${LUI_Cache}" />
		 <div class="lui-dbcenter-panel-heading">			     
		    <div class="lui-dbcenter-extra-wrap">
		      	<h2 class="lui-dbcenter-panel-heading-title"></h2>
				<div class="lui-dbcenter-extra-border"></div>
			</div> 
		</div>
		<!-- 筛选区 -->
		<div class="criterion-wrap">
			<div data-lui-type="dbcenter/echarts/application/navTree/js/modelItems!modelItems" id="modelItems" class="criterion-modelItems">
				<script type="text/config">
					{
						"modelInfos":<%	
							IDbEchartsNavTreeShowService navTreeShowService = (IDbEchartsNavTreeShowService)SpringBeanUtil.getBean("dbEchartsNavTreeShowService");
							JSONObject infos = navTreeShowService.getNavTreeShowJSON(request.getParameter("mainModelName"), request.getParameter("fdKey"));
							out.print(infos);
						%>
					}
				</script>
			
			</div>
			<!-- 所有的业务逻辑都丢在dataview里面做 -->
			<div data-lui-type="dbcenter/echarts/application/navTree/js/dataView!dataView" class="criterion-dataView" id="dataView">
				<script type="text/config">
					{"linedBlockId":"modelItems",
					 "showAreaId" : "dbNavTree_Iframe"}
				</script>
			</div>			
		</div>
		
		<!-- 展示区 -->
		<ui:iframe id="dbNavTree_Iframe"></ui:iframe>
		<script>
			LUI.ready(function(){
				$('.lui-dbcenter-panel-heading-title').html(decodeURIComponent("${JsParam.title}"));
				// 默认第一个页签被选中
				var modelItemsWgt = LUI("modelItems");
				if(modelItemsWgt.modelItems && modelItemsWgt.modelItems.length > 0){
					modelItemsWgt.modelItems[0]["domNode"].trigger("click");
					setTimeout(function(){
						LUI("dataView").currentView.find("li:first").trigger("click");
					},100);
				}
			});
		</script>
	</template:replace>
</template:include>
