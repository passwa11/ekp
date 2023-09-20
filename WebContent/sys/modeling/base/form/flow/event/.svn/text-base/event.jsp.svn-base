<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.edit" sidebar="no">
	<template:replace name="content" >
		<script type="text/javascript">
			/**
			 * 功能：页面初始化（标准接口）
			 *
			 * @param config 当前修改的监听器参数配置信息，从节点属性窗口传递。
			 * @param eventType 当前修改的事件完整参数配置信息。
			 *    格式为：{id: 唯一标识, name: 事件配置显示名, type: 事件类型, typeName: 事件显示名, eventConfig: 事件参数配置,
			 *	             listenerId: 监听器类型, listenerName: 监听器显示名, listenerConfig: 监听器参数配置}
			 * @param nodeObject 当前配置的节点在流程图中的JS对象
			 * @param context 参数配置需要的相关上下文信息。
			 *    格式为：{selectedId: 当前选中的事件类型, nodeEvents: 当前节点能选择的事件类型集, modelName: 当前模块的modelName}
			 */
			function initValue(config, eventType, nodeObject, context){
				// 监听器配置参数
				$("select[name='serviceType']").trigger($.Event("change"));
				if(config){
					var datas = JSON.parse(config);
					$("[name='serviceType']").val(datas.serviceType).trigger($.Event("change"));
					$("[name='serviceName']").val(datas.serviceName);
					$("[name='serviceId']").val(datas.serviceId);
					
					if(datas.serviceId){
						if(datas.inputs){
							LUI("inputsView").config.values = datas.inputs;
						}
						buildInputs(datas.serviceId);
					}
				}
			 };
			 
			 function buildInputs(id){
				 var inputsWgt = LUI("inputsView");
					inputsWgt.setSourceParams("operaId",id);
					inputsWgt.doRefresh();
			 }
			
			/**
			 * 功能：页面提交时校验方法（标准接口）
			 */
			function checkValue() {
				return true;
			};
			
			/**
			 * 功能：页面提交时返回值（标准接口）
			 */
			function returnValue() {
				var rs = {};
				rs.serviceType = $("select[name='serviceType']").val();
				rs.serviceName = $("input[name='serviceName']").val();
				rs.serviceId = $("input[name='serviceId']").val();
				// 处理入参
				rs.inputs = LUI("inputsView").getKeyData();
				
				return JSON.stringify(rs);
			};
			
			function serviceDialog(){
				var type = $("select[name='serviceType']").val();
				var sourceUrl ="sys/modeling/base/form/flow/flow_trigger_select.jsp?type=1&modelId=";
				if(type === "scene"){
					sourceUrl ="sys/modeling/base/form/flow/flow_trigger_select.jsp?type=0&modelId=";
					openStepDialog(sourceUrl, "${lfn:message('sys-modeling-base:table.sysModelingScenes')}");
				}else{
					openStepDialog(sourceUrl, "${lfn:message('sys-modeling-base:sysModelingScenes.fdBehaviors')}");
				}
			}
			
			function openStepDialog(sourceUrl, text){
				var dialog = new KMSSDialog();
				dialog.winTitle = "${lfn:message('sys-modeling-base:modeling.form.Choice')}"+text;
				//findInfosByModelId
				dialog.URL = Com_Parameter.ContextPath + sourceUrl+parent.FlowChartObject.FdAppModelId;
				dialog.SetAfterShow(function(rtn){
					if(rtn && rtn.data && rtn.data.length > 0){
						var values = rtn.data[0].data;
						$("[name='serviceId']").val(values.fdId);
						$("[name='serviceName']").val(values.fdName);
						buildInputs(values.fdId);
					}
				});
				dialog.Show(1100, 600);

			}
			
			function serviceChange(dom){
				// 清空已选
				$("input[name='serviceName']").val("");
				$("input[name='serviceId']").val("");
				if(dom.value === "scene"){
					LUI("inputsView").config.isRenderArray = "true";
					LUI("inputsView").config.requestUrl = "/sys/modeling/base/modelingAppFlow.do?method=getOperaInfo&type=scene&operaId=!{operaId}";
				}else{
					LUI("inputsView").config.isRenderArray = "true";
					LUI("inputsView").config.requestUrl = "/sys/modeling/base/modelingAppFlow.do?method=getOperaInfo&type=behavior&operaId=!{operaId}";
				}
				LUI("inputsView").config.values = {};
				LUI("inputsView").element.html("");
				
			}
			</script>
			<table class="tb_normal" width="98%">
				<tr >
					<td class="td_normal_title" width="15%">
						${lfn:message('sys-modeling-base:lbpm.event.type')}
					</td>
					<td>
						<div style="display:inline-block;">
							<select name="serviceType" onchange="serviceChange(this);">
								<option value="behavior">${lfn:message('sys-modeling-base:lbpm.robot.action')}</option>
								<option value="scene">${lfn:message('sys-modeling-base:lbpm.robot.scene')}</option>
							</select>
						</div>
						<xform:dialog propertyName="serviceName" propertyId="serviceId" showStatus="edit" style="width:80%;height:20px" dialogJs="serviceDialog();"></xform:dialog>
					</td>
				</tr>
				<tr >
					<td class="td_normal_title" width="15%">
						${lfn:message('sys-modeling-base:lbpm.robot.dynamic')}
					</td>
					<td>
						<div data-lui-type="sys/modeling/base/form/js/inputs/inputsView!InputsView" style="display:none;" id="inputsView">
							<script type="text/config">
 						{
							isRenderArray : "false",
							values : {},
							requestUrl : "/sys/modeling/base/modelingAppFlow.do?method=getOperaInfo&type=behavior&operaId=!{operaId}"
						}
 					</script>
							<ui:source type="AjaxJson">
						 		{ url : ''}
							</ui:source>
							<div data-lui-type="lui/view/render!Template" style="display:none;">
								<script type="text/config">
 							{
								src : '/sys/modeling/base/form/js/inputs/inputs.html#'
							}
 						</script>
							</div>
						</div>
					</td>
				</tr>
			</table>
	</template:replace>
</template:include>	
