<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<template:include ref="default.dialog">
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/panelDialog.css" />
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/ui/select/css/selectSearch.css" />
		<style>
			.panel-main .panel-main-right{
				overflow-x:hidden;
			}
			.panel{
				display:none;
			}
		</style>
	</template:replace>
    <template:replace name="content">
    	<div class="panel">
			<div class="panel-header">
				<div class="app-select"></div>
			</div>
    		<div class="panel-main">
	    		<div class="panel-main-left">
					<ul class="panel-left-content">
						<li data-cate-value="chart">${lfn:message('sys-modeling-base:table.dbEchartsChart')}</li>
						<!-- <li data-cate-value="table">统计列表</li> -->
						<li data-cate-value="chartTable">${lfn:message('sys-modeling-base:table.dbEchartsChartSet')}</li>
					</ul>
	    		</div>
	    		<div class="panel-main-right">
	    		<table width="100%">
					<thead>
						<tr>
							<td width="50%">${lfn:message('sys-modeling-base:modelingAppMobile.docSubject')}</td>
							<td width="20%">${lfn:message('sys-modeling-base:modelingAppMobile.docCreator')}</td>
							<td width="30%">${lfn:message('sys-modeling-base:modelingAppMobile.docCreateTime')}</td>
						</tr>
					</thead>
					<tbody id="chartbody">

					</tbody>
	    		</div>
    		</div>
    		<div class="panel-bottom">
    			<ui:button text="${ lfn:message('button.ok') }" width="80" height="30" onclick="ok();"/>
    			<ui:button text="${ lfn:message('button.cancel') }" width="80" height="30" styleClass="lui_toolbar_btn_gray" onclick="Com_CloseWindow();" />
    		</div>
    	</div>
    	<script>

    		seajs.use(['lui/topic',"sys/modeling/base/mobile/design/mode/mportalList/chartDataList","lui/dialog",
				"sys/modeling/base/ui/select/js/selectSearch.js","lui/topic"],function(topic,chartDataList, dialog,selectSearch,topic){
    			window.ok = function(){
    				if($dialog.___params.okFn){
    					$dialog.___params.okFn();
    				} else {
    					var $tr = $(".panel-main-right").find(".active");
    					if($tr.length > 0){
							var appId = $dialog.___params.appId || "";
							if(window.selectSearchInstance){
								appId = window.selectSearchInstance.currentAppId;
							}
							$dialog.hide({data:chartDataListWgt.getRecordDataByValue($tr.attr("data-record-value")),appId:appId});
						}else{
    						dialog.alert("${lfn:message('sys-modeling-base:modeling.select.record')}");
    					}
    				}
    			}

    			window.init = function(){
    				var appId = $dialog.___params.appId;

    				// 更新高度
        			var iframeHeight = $dialog.height - 50;	// 50是顶部高度
        			$(".panel").height(iframeHeight);
        			var contentHeight = iframeHeight - $(".panel-bottom").height();
        			$(".panel-main").height(contentHeight);
        			$(".panel-main-left").height(contentHeight);
        			$(".panel-main-right").height(contentHeight);
					window.selectSearchInstance = new selectSearch.SelectSearch({
						"viewContainer": $(".app-select"),
						"appId" : appId,
						"channel" : "modelingChart"
					});
					selectSearchInstance.startup();
					window.render(appId);
				}

    			window.render = function (appId) {
					// 左侧导航
					$(".panel-left-content li").unbind().on("click",function(){
						$(this).siblings().removeClass("active");
						$(this).addClass("active");
						var chartType = $(this).attr("data-cate-value");
						topic.channel("modelingChart").publish("leftNavChartClick", {"chartType": chartType,"appId":appId});
					});
					$("#chartbody").empty();
					if(window.chartDataListWgt){
						window.chartDataListWgt.startup();
					}else{
						// 构造右侧数据列表
						window.chartDataListWgt = new chartDataList.ChartDataList({
							container : $("#chartbody"),
						});
						chartDataListWgt.startup();
					}

					$(".panel").css("display","block");
					$(".panel-left-content li").first().trigger($.Event("click"));
				}

    			var interval = setInterval(beginInit, "50");
    	    	function beginInit(){
    	    		if(!window['$dialog'])
    	    			return;
    	    		clearInterval(interval);
    	    		init();
    	    	}
				topic.channel("modelingChart").subscribe("app_select",function(id){
					if(id){
						window.render(id);
					}
				});
    		});


    	</script>
	</template:replace>
</template:include>