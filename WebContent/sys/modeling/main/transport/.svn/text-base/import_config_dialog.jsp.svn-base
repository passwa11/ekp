<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple">
	<template:replace name="title">${lfn:message('sys-modeling-main:modeling.import.configuration') }</template:replace>
	<template:replace name="head">
		<template:super/>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/main/resources/css/flowdialog.css?s_cache=${LUI_Cache }"/>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/modeling.css?s_cache=${LUI_Cache }"/>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/transport/css/transport.css?s_cache=${LUI_Cache }"/>
	</template:replace>
	<template:replace name="body">
		<script>
			var selectId = '${param.fdId}'
		</script>
		<!-- 需选择流程模板 -->
		<c:if test="${JsParam.changeFlow }">
			<div class="model-step">
			<div class="model-step-wrap">
				<div class="model-step-tab changeFlowTemplate" style="display:none;">
					<div class="model-step-tab-item oneItem active">
						<div class="model-step-tab-icon"><i></i>1</div>
						<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.select.process') }</div>
					</div>
					<div class="model-step-tab-item twoItem">
						<div class="model-step-tab-icon"><i></i>2</div>
						<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.select.import.template') }</div>
					</div>
					<div class="model-step-tab-item">
						<div class="model-step-tab-icon"><i></i>3</div>
						<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.upload.template') }</div>
					</div>
					<c:if test="${JsParam.enableFlow }">
						<div class="model-step-tab-item">
							<div class="model-step-tab-icon"><i></i>4</div>
							<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.import.setting') }</div>
						</div>
					</c:if>
					<div class="model-step-tab-item">
						<c:if test="${JsParam.enableFlow }">
							<div class="model-step-tab-icon"><i></i>5</div>
						</c:if>
						<c:if test="${empty JsParam.enableFlow or JsParam.enableFlow ne true}">
							<div class="model-step-tab-icon"><i></i>4</div>
						</c:if>
						<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.finish') }</div>
					</div>
				</div>
				<div class="model-step-content">
					<div class ="multiFlow" style="display:none;">
						<div class="app_flow">
							<div class="flow_head">
								<div class="app_flow_search">
									<div class="app_flow_searchbox">
										<input type="text" class="app_flow_searchinput" onkeyup='searchFlowOnKeyup(event,this);' placeholder="${lfn:message('sys-modeling-main:modeling.enter.keyword.search.process') }"/>
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
					</div>
					<div class="model-step-content-wrap importTemplate" style="display:none;">
						<div class="model-record-wrap">
							<ui:dataview id="importDataView">
								<ui:source type="AjaxJson">
									{url : "/sys/modeling/main/modelingImportConfig.do?method=findConfigs&fdAppModelId=${JsParam.fdAppModelId}&c.like.fdName=!{fdName}"}
								</ui:source>
								<ui:render type="Javascript">
									<c:import url="/sys/modeling/base/resources/js/importConfigRender.js" charEncoding="UTF-8"></c:import>
								</ui:render>
							</ui:dataview>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="toolbar-bottom changeTemplate">
			<ui:button text="${lfn:message('sys-modeling-main:modeling.next.step') }" order="1" onclick="changeTemplate();"/>
		</div>
		<div class="toolbar-bottom ok" style="display:none;">
			<ui:button text="${lfn:message('sys-modeling-main:modeling.next.step') }" order="1" onclick="ok();"/>
			<ui:button  styleClass="lui_toolbar_btn_gray" text="${lfn:message('sys-modeling-main:modeling.previous') }" order="1" onclick="goback();"/>
		</div>
		</c:if>
		<!-- 无需选择流程模板 -->
		<c:if test="${empty JsParam.changeFlow or JsParam.changeFlow ne true }">
		<div class="model-step">
			<div class="model-step-wrap">
				<div class="model-step-tab">
					<div class="model-step-tab-item active">
						<div class="model-step-tab-icon"><i></i>1</div>
						<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.select.import.template') }</div>
					</div>
					<div class="model-step-tab-item">
						<div class="model-step-tab-icon"><i></i>2</div>
						<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.upload.template') }</div>
					</div>
					<c:if test="${JsParam.enableFlow }">
						<div class="model-step-tab-item">
							<div class="model-step-tab-icon"><i></i>3</div>
							<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.import.setting') }</div>
						</div>
					</c:if>
					<div class="model-step-tab-item">
						<c:if test="${JsParam.enableFlow }">
							<div class="model-step-tab-icon"><i></i>4</div>
						</c:if>
						<c:if test="${empty JsParam.enableFlow or JsParam.enableFlow ne true}">
							<div class="model-step-tab-icon"><i></i>3</div>
						</c:if>
						<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.finish') }</div>
					</div>
				</div>
				<div class="model-step-content">
					<div class="model-step-content-wrap">
						<div class="model-record-wrap">
							<ui:dataview id="importDataView">
								<ui:source type="AjaxJson">
									{url : "/sys/modeling/main/modelingImportConfig.do?method=findConfigs&fdAppModelId=${JsParam.fdAppModelId}&c.like.fdName=!{fdName}"}
								</ui:source>
								<ui:render type="Javascript">
									<c:import url="/sys/modeling/base/resources/js/importConfigRender.js" charEncoding="UTF-8"></c:import>
								</ui:render>
							</ui:dataview>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="toolbar-bottom">
			<ui:button text="${lfn:message('sys-modeling-main:modeling.next.step') }" order="1" onclick="ok();"/>
		</div>
		</c:if>
		<script>
			var langs = {
					"return.noRecord":"${lfn:message('return.noRecord')}"
			};
			var modelingfdAppFlowId = "${param.fdAppFlowId}";
			function ok(){
				var configId = $(".model-record-item.active").attr("data-config-id");
				if (!configId){
					seajs.use(['lui/dialog'],function(dialog){
						dialog.alert('${lfn:message('sys-modeling-main:modeling.select.template.first') }');
					});
					return
				}

				var url = Com_Parameter.ContextPath + 
					"sys/modeling/main/modelingImportConfig.do?method=importDoc&type=upload&fdId="+configId+"&fdAppModelId=${param.fdAppModelId}" + 
							"&enableFlow=${param.enableFlow}"+"&fdAppFlowId="+modelingfdAppFlowId+"&changeFlow=${JsParam.changeFlow }";
				window.location.href =url;
			}
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

			function changeTemplate(){
				if($("li.flow_li_selected").length === 0) {
					seajs.use('lui/dialog', function(dialog){
						dialog.alert('<bean:message key="page.noSelect"/>');
					});
					return;
				}
				var flowId = $("li.flow_li_selected").attr("data-flow-id");
				modelingfdAppFlowId = flowId;
				$(".multiFlow").css("display","none");
				$(".importTemplate").css("display","block");
				$(".oneItem").removeClass("active");
				$(".oneItem").addClass("finished");
				$(".twoItem").addClass("active");
				$(".changeTemplate").css("display","none");
				$(".ok").css("display","block");
			}
			function goback(){
				$(".multiFlow").css("display","block");
				$(".importTemplate").css("display","none");
				$(".oneItem").addClass("active");
				$(".oneItem").removeClass("finished");
				$(".twoItem").removeClass("active");
				$(".changeTemplate").css("display","block");
				$(".ok").css("display","none");
			}
			Com_AddEventListener(window,"load",function(){
				$(".changeFlowTemplate").css("display","block");
				$(".multiFlow").css("display","block");
				var gobackFlag = "${JsParam.goback}";
				if(gobackFlag == "true"){
					$(".multiFlow").css("display","none");
					$(".importTemplate").css("display","block");
					$(".oneItem").removeClass("active");
					$(".oneItem").addClass("finished");
					$(".twoItem").addClass("active");
					$(".changeTemplate").css("display","none");
					$(".ok").css("display","block");
				}
			})
		</script>
	</template:replace>
</template:include>
