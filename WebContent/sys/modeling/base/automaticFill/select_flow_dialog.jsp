<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple">
	<template:replace name="title">选择流程</template:replace>
	<template:replace name="head">
		<template:super/>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/main/resources/css/flowdialog.css?s_cache=${LUI_Cache }"/>
	</template:replace>
	<template:replace name="body">
		<div class="app_flow">
			<div class="flow_head">
				<div class="app_flow_search">
					<div class="app_flow_searchbox">
						<input type="text" class="app_flow_searchinput" onkeyup='searchFlowOnKeyup(event,this);' placeholder="${lfn:message('sys-modeling-main:modeling.enter.keyword.search.process')}"/>
						<a href="javascript:void(0);" onclick="searchFlow($(this).prev());" class="app_flow_searchbtn"></a>
					</div>
				</div>
			</div>
			<div class="flow_content">
				<ui:dataview id="flowDataView">
					<ui:source type="AjaxJson">
						{url : "/sys/modeling/main/modelingAppFlowMain.do?method=findFlows&fdAppModelId=${JsParam.fdAppModelId}&c.like.fdName=!{fdName}"}
					</ui:source>
					<ui:render type="Javascript">
						<c:import url="/sys/modeling/main/resources/js/flowRender.js" charEncoding="UTF-8"></c:import>
					</ui:render>
				</ui:dataview>
			</div>
		</div>
		<div class="flow_bottom">
			<div class="flow_bottom_buttons">
				<ui:button text="${lfn:message('button.ok')}" width="80" onclick="ok();" order="2" ></ui:button>
				<ui:button text="${lfn:message('button.cancel')}" styleClass="lui_toolbar_btn_gray" width="80" onclick="cancel();" order="1" ></ui:button>
			</div>
		</div>
		<script>
		
			function searchFlowOnKeyup(event , dom){
				if (event && event.keyCode == '13') {
					searchFlow(dom);
				}
			}

			function searchFlow(dom){
				var val = encodeURIComponent($(dom).val());
				var flowDataViewWgt = LUI("flowDataView");
				flowDataViewWgt.element.html("");
				flowDataViewWgt.source.resolveUrl({"fdName":val});
				flowDataViewWgt.onRefresh();
			}

			function ok(){
				if($("li.flow_li_selected").length === 0) {
					seajs.use('lui/dialog', function(dialog){
						dialog.alert('<bean:message key="page.noSelect"/>');
					});
					return;
				}
				var flowId = $("li.flow_li_selected").attr("data-flow-id");
				var flowName = $("[data-flow-id="+flowId+"]").find("a").find("span").text();
				$dialog.hide({flowId:flowId,flowName:flowName});
			}
			
			function cancel(){
				$dialog.hide();
			}
		</script>
	</template:replace>
</template:include>
