<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.edit" sidebar="no">
	<template:replace name="head">
    	<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/main/xform/controls/placeholder/css/listview.css" />
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modelTable.css?s_cache=${LUI_Cache}"/>
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css?s_cache=${LUI_Cache}"/>
	</template:replace>
	<template:replace name="content">
		<c:if test="${JsParam.multi eq 'true' }">
			<ui:toolbar var-navwidth="90%" id="toolbar" layout="sys.ui.toolbar.float" count="2">  
				<ui:button text="${ lfn:message('button.ok') }" order="1"  onclick="ok();"></ui:button>
				<ui:button text="${ lfn:message('button.cancel') }" order="2"  onclick="closeWindow();"></ui:button>
			</ui:toolbar>		
		</c:if>
	<style>
		#top{
			display: none !important;
		}
	</style>
		<div style="background-color:#f7f7f7;">
			<!-- 搜索项 -->
			<div class="dialog-header">
				<div data-lui-type="sys/modeling/main/xform/controls/placeholder/dialog/search!Search" id="dialogSearch" class="header-search">
					<ui:source type="Static">
						{}
					</ui:source>
					<ui:render type="Javascript">
						<c:import url="/sys/modeling/main/xform/controls/placeholder/dialog/searchRender.js" charEncoding="UTF-8"></c:import>
					</ui:render>
				</div>
			</div>

			<div data-lui-type="sys/modeling/main/xform/controls/placeholder/dialog/sort!Sort" id="dialogSort">
				<ui:source type="Static">
					{}
				</ui:source>
				<ui:render type="Javascript">
					<c:import url="/sys/modeling/main/xform/controls/placeholder/dialog/sortRender.js" charEncoding="UTF-8"></c:import>
				</ui:render>
			</div>

			<div id="listView" data-lui-type="sys/modeling/main/xform/controls/placeholder/dialog/listView!listView" style="display:none;background-color:#fff;">
				<script type="text/config">
					{
						"multi" : "${JsParam.multi}"
					}
				</script>
			 	<ui:source type="AjaxJson">
		 			{ url : ''}
				</ui:source>
				<div data-lui-type="lui/view/render!Template">
					<script type="text/config">
 					{
						src : '/sys/modeling/main/xform/controls/placeholder/dialog/listViewRender.html#'
					}
 				</script>
				</div>
			</div>
		</div>
		<script>
			var interval = setInterval(____Interval, "50");
			function ____Interval() {
				if (!window['$dialog'])
					return;
				var parentParams = $dialog.___params;
				// 画搜索项
				var searchWgt = LUI("dialogSearch");
				var cfg = {
					data:parentParams.cfgInfo.search,
					land:{
						clean:"${lfn:message('sys-modeling-main:sysModeling.btn.clean')}",
						search:"${lfn:message('sys-modeling-main:sysModeling.btn.search')}",
						expandFilter:"${lfn:message('sys-modeling-main:sysModeling.btn.expandFilter')}",
						collapseFilter:"${lfn:message('sys-modeling-main:sysModeling.btn.collapseFilter')}",
						choose:"${lfn:message('sys-modeling-main:sysModeling.btn.choose')}",
						enterWord:"${lfn:message('sys-modeling-main:sysModeling.placeholder.enterWord')}",
						pleaserWord:"${lfn:message('sys-modeling-main:modeling.please.choose')}"
					},
					searchNumber:parentParams.cfgInfo.searchNumber
				}
				searchWgt.initSourceData(cfg);
				searchWgt.onRefresh();

				// 画排序项
				var sortWgt = LUI("dialogSort");
				var order = '${ lfn:message('list.orderType') }'+'：';
				var moreOrder = '${lfn:message('sys-modeling-main:modeling.more.sorting') }';
				sortWgt.initSourceData({"list":parentParams.cfgInfo.sort,"land":{"order":order,"moreOrder":moreOrder}});
				sortWgt.onRefresh();
				
				//设置搜索项每行的列数
				var searchNumber =  parentParams.cfgInfo.searchNumber;
				if(searchNumber){
					setShowCol(searchNumber);
				}else{
					defaultShow("50");
				}
				
				// 画列表
				var listViewWgt = LUI("listView");
				listViewWgt.init(parentParams);
				listViewWgt.fetch();
				
				clearInterval(interval);
			}
			
			function setShowCol(searchNumber){
				var width = parseInt(100/searchNumber);
				$("#dialogSearch").find("ul li").css({"width":width+"%"});
				defaultShow(""+width);
			}
			function defaultShow(width){
				switch(width){
				case "100":
					var arr = new Array(0,1);
					doshowSearch(arr,"2");
					break;
				case "50":
					var arr = new Array(0,1,2,3);
					doshowSearch(arr,"4");
					break;
				case "33":
					var arr = new Array(0,1,2,3,4,5);
					doshowSearch(arr,"6");
					break;
				case "25":
					var arr = new Array(0,1,2,3,4,5,6,7);
					doshowSearch(arr,"8");
					break;
				}
			}
			
			function doshowSearch(arr,length){
				var searchLength = $("#dialogSearch").find("ul li").length;
				for(var i = 0;i < arr.length; i++){
					$("#dialogSearch").find("ul li").eq(arr[i]).css("display","inline-block");
				}
				if(searchLength > length){
					$(".hearder-search-expand").text("${lfn:message('sys-modeling-main:modeling.expand.more.filters') }");
					$(".hearder-search-expand").addClass("showmany");
				}else{
					$(".hearder-search-expand").text("${lfn:message('sys-modeling-main:sysModeling.btn.collapseFilter') }");
				}
			}
			var selectList = {};
            function multiSelect(rowIdx){
                var listViewWgt = LUI("listView");
                var rowsInfo = listViewWgt.changeSelected(rowIdx);
            }
			
			function ok(){
				var listViewWgt = LUI("listView");
				var rowsInfo = listViewWgt.getRowsInfo();
				if($.isEmptyObject(rowsInfo)){
					seajs.use(["lui/dialog"],function(dialog){
						dialog.alert("${lfn:message('sys-modeling-main:modeling.select.record.first') }");
					});
					return;
				}
				listViewWgt.hide(rowsInfo);
				
			}
			
			function closeWindow(){
				var listViewWgt = LUI("listView");
				listViewWgt.hide();
			}
		</script>
	</template:replace>
</template:include>
