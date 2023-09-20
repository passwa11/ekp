<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.edit" sidebar="no">
	<template:replace name="content" >
	
		<table width="100%" class="tb_normal">
			<tr>
				<td class="td_normal_title" width="15%">${lfn:message('sys-modeling-base:lbpm.robot.scene')}</td>
				<td>
					<xform:dialog propertyName="sceneName" propertyId="sceneId" showStatus="edit" style="width:95%" dialogJs="sceneDialog();"></xform:dialog>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">${lfn:message('sys-modeling-base:lbpm.robot.dynamic')}</td>
				<td>
					<div data-lui-type="sys/modeling/base/form/js/inputs/inputsView!InputsView" style="display:none;" id="inputsView">
						<script type="text/config">
 							{
								isRenderArray : "true",
								values : {},
								requestUrl : "/sys/modeling/base/modelingAppFlow.do?method=getOperaInfo&type=scene&operaId=!{operaId}"
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
		
		<script>
			LUI.ready(function(){
				initDocument();
			});
			
			function initDocument() {
				if(parent.NodeData && parent.NodeData.content){
					var datas = JSON.parse(parent.NodeData.content);
					$("input[name='sceneId']").val(datas.sceneId);
					$("input[name='sceneName']").val(datas.sceneName);
					if(datas.sceneId){
						if(datas.inputs){
							LUI("inputsView").config.values = datas.inputs;
						}
						buildInputs(datas.sceneId);
					}
				}
			};
		
			function sceneDialog(){
				var dialog = new KMSSDialog();
				dialog.URL =  Com_Parameter.ContextPath+"sys/modeling/base/form/flow/flow_trigger_select.jsp?type=0&modelId="+parent.FlowChartObject.FdAppModelId;
				dialog.SetAfterShow(function(rtn){
					if(rtn && rtn.data && rtn.data.length > 0){
						var values = rtn.data[0].data;
						$("[name='sceneId']").val(values.fdId);
						$("[name='sceneName']").val(values.fdName);
						buildInputs(values.fdId);
					}
				});
				dialog.Show(1100, 600);
				
			}
			
			function buildInputs(id){
				var inputsWgt = LUI("inputsView");
				inputsWgt.setSourceParams("operaId",id);
				inputsWgt.doRefresh();
			}
			
			function returnValue(){
				var rs = {};
				rs.sceneId = $("input[name='sceneId']").val();
				rs.sceneName = $("input[name='sceneName']").val();
				// 处理入参
				rs.inputs = LUI("inputsView").getKeyData();
				
				return JSON.stringify(rs);
			}
		</script>
	</template:replace>
</template:include>