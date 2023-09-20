<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.list" tiny="true">
	<template:replace name="head">
	
		<mui:cache-file name="mui-intr.css" cacheType="md5"/>
		<mui:cache-file name="mui-intr.js" cacheType="md5"/>
		<mui:cache-file name="mui-form.js" cacheType="md5"/>

		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/sys/introduce/mobile/css/intr-index.css" />
		<c:set var="s_requestUrl" value="/sys/introduce/sys_introduce_main/sysIntroduceMain.do?method=add&fdModelName=${param.modelName}&fdModelId=${param.modelId}" />
		<script type="text/javascript">
			require([ "dojo/topic", "dojo/dom","dojo/query","dijit/registry","dojo/domReady!" ], function(
					topic, dom, query,registry) {
				
				// 监听事件，改变推荐数
				topic.subscribe("/mui/list/loaded", function(obj) {
						if(obj.id != 'muiIntrStoreList')
							return;
						var count = 0;
						if(obj){
							count = obj.totalSize;
						}
						dom.byId("intr_count").innerHTML = count;
				});
				
				// 没数据给最小高度
				topic.subscribe('/mui/list/noData',function(obj){
					if(obj.id != 'muiIntrStoreList')
						return;
					var muiOpt = registry.byId('muiIntrOpt');
					if (muiOpt)
						muiOpt.showMask();
					
					var h = dom.byId('intr_scollView').offsetHeight
							- query('.muiIntrTitle')[0].offsetHeight - 20;
					query('.muiListNoData').style({
						'line-height' : h + 'px',
						'height' : h + 'px'
					});
				});
			});
		</script>
	</template:replace>
	<template:replace name="content">
	<xform:config  orient="vertical">
		<div id="intr_scollView"
			data-dojo-type="mui/list/StoreScrollableView">
			<div class="muiIntrTitle">
				<span></span>
				<div>
					<bean:message bundle="sys-introduce" key="sysIntroduceMain.mobile.lastInstroduce"/>(<span class="muiIntrCount" id="intr_count"></span>)
				</div>
			</div>
			<ul class="muiIntrStoreList" data-dojo-type="mui/list/JsonStoreList" id="muiIntrStoreList"
				data-dojo-mixins="${LUI_ContextPath}/sys/introduce/mobile/js/IntroduceItemListMixin.js"
				data-dojo-props="url:'/sys/introduce/sys_introduce_main/sysIntroduceMain.do?method=viewAll&forward=mobileList&fdModelId=${HtmlParam.modelId}&fdModelName=${HtmlParam.modelName}',lazy:false">
			</ul>
		</div>
		<kmss:auth requestURL="${s_requestUrl}" requestMethod="GET">
			<div style="display: none;" id="intr_formData">
				<input type="hidden" name="fdKey" value="${HtmlParam.fdKey}"/>
				<input name="fdIsNotify" type="checkbox" value="1" checked="checked">
				<input type="hidden" name="fdModelId" value="${HtmlParam.modelId}"/>
				<input type="hidden" name="fdModelName" value="${HtmlParam.modelName}"/>
				<kmss:editNotifyType property="fdNotifyType" value="todo"/>
			</div>
		</kmss:auth>

		<ul data-dojo-type="mui/tabbar/TabBar" id="intr_tabbar" fixed="bottom">
			<kmss:auth requestURL="${s_requestUrl}" requestMethod="GET">
					<div 
						class="muiIntrOpt" 
						data-dojo-type="${LUI_ContextPath}/sys/introduce/mobile/js/Intr.js"
						data-dojo-props="fdModelId:'${HtmlParam.modelId}', 
										 fdModelName:'${HtmlParam.modelName}'"
						id="muiIntrOpt">
						<input type="text" readonly="readonly" class="muiIntrText" placeholder='<bean:message bundle="sys-introduce" key="sysIntroduceMain.mobile.reply"/>' />
					</div>
			</kmss:auth>
		</ul>
	</xform:config>
	</template:replace>
</template:include>