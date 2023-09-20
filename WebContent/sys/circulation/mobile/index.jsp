<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.list">
	<template:replace name="head">
		<template:replace name="title"><bean:message bundle="sys-circulation" key="sysCirculationMain.fdRecord"/></template:replace>
		<c:set var="s_requestUrl" value="/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=add&fdModelName=${param.modelName}&fdModelId=${param.modelId}" />
		<script type="text/javascript">
			require([ "dojo/topic", "dojo/dom","dojo/query","dijit/registry","dojo/domReady!" ], function(
					topic, dom, query,registry) {
				
				// 监听事件，改变传阅数
				topic.subscribe("/mui/list/loaded", function(obj) {
						if(obj.id != 'muiCirStoreList')
							return;
						var count = 0;
						if(obj){
							count = obj.totalSize;
						}
						dom.byId("cir_count").innerHTML = count;
				});
				
				window.addCirculate = function(){
					var modelKey = '${param.modelKey}';
					var path;
					if(modelKey != 'imissive'){
						path = "${LUI_ContextPath}/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=add&fdModelName=${JsParam.modelName}&fdModelId=${JsParam.modelId}";
					}else{
						path = "${LUI_ContextPath}/km/imissive/km_imissive_circulation/kmImissiveCirculation.do?method=add&fdModelName=${JsParam.modelName}&fdModelId=${JsParam.modelId}";
					}
					window.open(path,"_self");
				};
				
				window.addCirculateAgain = function(){
					var path = "${LUI_ContextPath}/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=addCirculate&fdModelName=${JsParam.modelName}&fdModelId=${JsParam.modelId}";
					window.open(path,"_self");
				};
				
				// 没数据给最小高度
				topic.subscribe('/mui/list/noData',function(obj){
					if(obj.id != 'muiCirStoreList')
						return;
					var muiOpt = registry.byId('muiCirOpt');
					if (muiOpt)
						muiOpt.showMask();
					
					var h = dom.byId('cir_scollView').offsetHeight - query('.muiCirTitle')[0].offsetHeight - 20;
							
					query('.muiListNoData').style({
						'line-height' : h + 'px',
						'height' : h + 'px'
					});
				});
			});
		</script>
	</template:replace>
	<template:replace name="content">

		<div id="cir_scollView"
			data-dojo-type="mui/list/StoreScrollableView">
			<div class="muiCirTitle">
				<span></span>
				<div>
					<bean:message bundle="sys-circulation" key="sysCirculationMain.mobile.lastCirculation"/>(<span class="muiCirCount" id="cir_count"></span>)
				</div>
			</div>
			<c:choose>
				<c:when test="${param.isNew eq 'true'}">
					<ul class="muiCirStoreList" data-dojo-type="mui/list/JsonStoreList" id="muiCirStoreList"
						data-dojo-mixins="${LUI_ContextPath}/sys/circulation/mobile/js/CirculationItemListNewMixin.js"
						data-dojo-props="url:'/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=viewAll&forward=mobileList&fdModelId=${JsParam.modelId}&fdModelName=${JsParam.modelName}',lazy:false">
					</ul>
				</c:when>
				<c:otherwise>
					<ul class="muiCirStoreList" data-dojo-type="mui/list/JsonStoreList" id="muiCirStoreList"
						data-dojo-mixins="${LUI_ContextPath}/sys/circulation/mobile/js/CirculationItemListMixin.js"
						data-dojo-props="url:'/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=viewAll&forward=mobileList&fdModelId=${JsParam.modelId}&fdModelName=${JsParam.modelName}',lazy:false">
					</ul>
				</c:otherwise>
			</c:choose>
		</div>
		<kmss:auth requestURL="${s_requestUrl}" requestMethod="GET">
			<div style="display: none;" id="cir_formData">
				<input type="hidden" name="fdKey" value="${HtmlParam.fdKey}"/>
				<input type="hidden" name="fdModelId" value="${HtmlParam.modelId}"/>
				<input type="hidden" name="fdModelName" value="${HtmlParam.modelName}"/>
				<kmss:editNotifyType property="fdNotifyType" value="todo"/>
			</div>
		</kmss:auth>

		<ul data-dojo-type="mui/tabbar/TabBar" id="cir_tabbar" fixed="bottom">
			<c:set var="showButton" value="false"></c:set>
			<c:choose> 
			   <c:when test="${param.isNew eq 'true'}">
				   	<kmss:auth requestURL="${s_requestUrl}" requestMethod="GET">
					    <div data-dojo-type="mui/tabbar/TabBarButton" onclick="addCirculate();">
							传阅
						</div>
						<c:set var="showButton" value="true"></c:set>
					</kmss:auth>	
					<%--继续传阅按钮 --%>
					<c:if test="${showButton eq 'false'}">
						<kmss:auth requestURL="/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=addCirculate&fdModelName=${param.modelName}&fdModelId=${param.modelId}" requestMethod="GET">
							 <div data-dojo-type="mui/tabbar/TabBarButton" onclick="addCirculateAgain();">
								传阅
							</div>
						</kmss:auth>
					</c:if>
			   </c:when>
			   <c:otherwise>
				   <kmss:auth requestURL="${s_requestUrl}" requestMethod="GET">
					    <div class="muiCirOpt" 
							data-dojo-type="${LUI_ContextPath}/sys/circulation/mobile/js/Cir.js"
							data-dojo-props="fdModelId:'${JsParam.modelId}',fdModelName:'${JsParam.modelName}'" 
							id="muiCirOpt">
							<input type="text" readonly="readonly" class="muiCirText" placeholder="<bean:message bundle="sys-circulation" key="sysCirculationMain.mobile.reply"/>" />
						</div>
					</kmss:auth>
				</c:otherwise>
			</c:choose>
		</ul>

	</template:replace>
</template:include>