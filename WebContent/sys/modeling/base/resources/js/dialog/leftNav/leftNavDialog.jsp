<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<!-- 左右结构对话框(对比dialog的treelist)，仅支持通过新UI的dialog.iframe引入
	params：{
		cateBean:"左侧导航的bean，必须继承IXMLDataBean",
		dataBean:"右侧数据bean"	
	}
-->
<template:include ref="default.dialog">
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/panelDialog.css" />
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/ui/select/css/selectSearch.css" />
    </template:replace>
    <template:replace name="content">
    	<div class="panel">
			<div class="panel-header">
				<div class="app-select"></div>
			</div>
    		<div class="panel-main">
	    		<div class="panel-main-left">
	    			<div data-lui-type="sys/modeling/base/resources/js/dialog/leftNav/leftNav!LeftNav" style="display:none;" id="navAside">
						<ui:source type="AjaxXml">
					 		{ url : '/sys/common/dataxml.jsp?s_bean=!{cateBean}&fdAppId=!{fdAppId}'}
						</ui:source>
						<div data-lui-type="lui/view/render!Template">
							<script type="text/config">
 								{
									src : '/sys/modeling/base/resources/js/dialog/leftNav/<c:if test="${param.isTodo eq false}">leftNavRender.html#</c:if><c:if test="${param.isTodo eq true}">leftNavRenderTodo.html#</c:if>'
								}
 							</script>
						</div>
					</div>
	    		</div>
	    		<div class="panel-main-right">
	    		</div>
    		</div>
    		<div class="panel-bottom">
    			<ui:button text="${ lfn:message('button.ok') }" width="80" height="30" onclick="ok();"/>
    			<ui:button text="${ lfn:message('button.cancel') }" width="80" height="30" styleClass="lui_toolbar_btn_gray" onclick="Com_CloseWindow();" />
    		</div>
    	</div>
    	<script>
    	
    		seajs.use(["sys/modeling/base/resources/js/dialog/leftNav/dataList","lui/dialog",
				"sys/modeling/base/ui/select/js/selectSearch.js","lui/topic"],function(dataList, dialog,selectSearch,topic){
    			/*
    				$dialog.___params : {
    					cateBean : "左侧导航的bean",
    					dataBean : "右侧数据的bean",
    					dataRender : "右侧展示的HTML，默认/sys/modeling/base/resources/js/dialog/leftNav/dataListRender.html",
    					okFn : "点击确定时执行的函数"
    				}
    			*/


    			
    			
    			window.ok = function(){
    				if($dialog.___params.okFn){
    					$dialog.___params.okFn();
    				} else {
    					var leftValue = $(".panel-left-content li.active").attr("data-cate-value");
    					var $tr = $(".panel-main-right").find(".active");
    					if($tr.length > 0){
    						var dataListWgt = LUI("dataList");
							var appId = $dialog.___params.fdAppId || "";
							if(window.selectSearchInstance){
								appId = window.selectSearchInstance.currentAppId;
							}
							$dialog.hide({data:dataListWgt.getRecordDataByValue($tr.attr("data-record-value")),appId:appId,leftValue:leftValue});
    					}else{
    						dialog.alert("${ lfn:message('sys-modeling-base:modeling.select.record') }");
    					}
    				}
    			}

				window.init = function(){
					// 更新高度
					var iframeHeight = $dialog.height - 50;	// 50是顶部高度
					$(".panel").height(iframeHeight);
					var contentHeight = iframeHeight - $(".panel-bottom").height() - 40; // 40是新增顶部筛选的高度;
					$(".panel-main").height(contentHeight);
					$(".panel-main-left").height(contentHeight);
					$(".panel-main-right").height(contentHeight);
					window.selectSearchInstance = new selectSearch.SelectSearch({
						"viewContainer": $(".app-select"),
						"appId" : $dialog.___params.fdAppId,
						"channel" : "modeling-select"
					});
					selectSearchInstance.startup();
					window.render($dialog.___params.fdAppId);
					var isShowCalendar = $dialog.___params.isShowCalendar || false;
					// 构造右侧数据列表
					window.dataListWgt = new dataList.DataList({
						id : "dataList",
						element : $("<div />").appendTo(".panel-main-right"),
						sourceUrl : "/sys/common/dataxml.jsp?s_bean=" + $dialog.___params.dataBean + "&isShowCalendar="+isShowCalendar,
						renderSrc : $dialog.___params.dataRender || "/sys/modeling/base/resources/js/dialog/leftNav/dataListRender.html"
					});
					dataListWgt.startup();

					// 后续可以添加一个自动定位功能，不然是没办法知道当前选择的记录属于哪个分类的
				}

				window.render = function(appId){
					// 更新左侧导航
					var leftNavWgt = LUI("navAside");
					leftNavWgt.resolveUrl({
						cateBean : $dialog.___params.cateBean,
						fdAppId : appId
					});
					leftNavWgt.load();
				}
    			
    			var interval = setInterval(beginInit, "50");
    	    	function beginInit(){
    	    		if(!window['$dialog'])
    	    			return;
    	    		clearInterval(interval);
    	    		init();
    	    	}
				topic.channel("modeling-select").subscribe("app_select",function(id){
					if(id){
						window.render(id);
					}
				});
    		});
    		
	    	
    	</script>
	</template:replace>
</template:include>