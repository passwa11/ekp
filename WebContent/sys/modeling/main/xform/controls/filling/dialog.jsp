<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.edit" sidebar="no">
	<template:replace name="head">
    	<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/main/xform/controls/placeholder/css/listview.css" />
    	<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/main/xform/controls/placeholder/css/filling.css" />
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modelTable.css?s_cache=${LUI_Cache}"/>
	</template:replace>
	<template:replace name="content">
	<style>
		#top{
			display: none !important;
		}
	</style>
		<div class="filling">
			<div class="filling-left-botton">
			</div>
			<div class="filling-right-content">
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

					<div id="listView" data-lui-type="sys/modeling/main/xform/controls/filling/listView!listView" style="display:none;background-color:#fff;">
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
						src : '/sys/modeling/main/xform/controls/filling/listViewRender.html#'
					}
 				</script>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="filling-button">
			<ui:button text="${ lfn:message('button.ok') }" order="1"  onclick="okFilling();"></ui:button>
			<ui:button text="${ lfn:message('button.cancel') }" order="2"  onclick="closeWindow();"></ui:button>
		</div>
		<script>
			Com_IncludeFile("fillingDialog.js",Com_Parameter.ContextPath + "sys/modeling/main/xform/controls/filling/",null,true);
			var interval = setInterval(____Interval, "50");
			function ____Interval() {
				if (!window['$dialog'])
					return;
				var parentParams = $dialog.___params;
				var fillingMapArr = parentParams.fillingMapArr;
				var fillingCfgInfos = parentParams.fillingCfgInfos;
				var modelId = parentParams.modelId;
				var flagid = parentParams.flagid;
				var bindDom = parentParams.bindDom;
				seajs.use(["sys/modeling/main/xform/controls/filling/fillingDialog","lui/topic"],function(fillingDialog,topic){
					var fillingWgt = new fillingDialog.FillingDialog({
						element : $(".filling-left-botton"),
						fillingMapArr: fillingMapArr,
						fillingCfgInfos: fillingCfgInfos,
						flagid: flagid,
						modelId: modelId,
						bindDom : bindDom
					});
					fillingWgt.startup();
				})

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
						doshowSearch(arr,"7");
						break;
				}
			}

			function doshowSearch(arr,length){
				var searchLength = $("#dialogSearch").find("ul li").length;
				for(var i = 0;i < arr.length; i++){
					$("#dialogSearch").find("ul li").eq(arr[i]).css("display","inline-block");
				}
				if(searchLength > length){
					$(".hearder-search-expand").text("展开更多筛选");
					$(".hearder-search-expand").addClass("showmany");
				}else{
					$(".hearder-search-expand").text("收起筛选");
				}
			}
			var selectList = {};
			var fillingSelect = {};
			var fillingRelationMap = {};
			function multiSelect(rowIdx){
				var listViewWgt = LUI("listView");
				// var rowsInfo = listViewWgt.changeSelected(rowIdx);
				$("#listView").find(".lui-listview-table-tr").removeClass("active");
				$("#listView").find("[data-rowindex='"+rowIdx+"']").addClass("active");
			}

			function okFilling() {
				var listViewWgt = LUI("listView");
				var result = {
					"fillingRelationMap":fillingRelationMap,
					"fillingSelect":fillingSelect
				}
				listViewWgt.hide(result);
				_modelingFillingNum = 0; //全局变量恢复初始值
			}

			function closeWindow(){
				var listViewWgt = LUI("listView");
				listViewWgt.hide();
				_modelingFillingNum = 0; //全局变量恢复初始值
			}
		</script>
	</template:replace>
</template:include>
