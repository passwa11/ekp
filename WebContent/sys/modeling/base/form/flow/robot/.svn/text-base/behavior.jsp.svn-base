<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.edit" sidebar="no">
	<template:replace name="content" >

		<table width="100%" class="tb_normal">
			<tr>
				<td class="td_normal_title" width="15%">${lfn:message('sys-modeling-base:lbpm.robot.action')}</td>
				<td>
					<xform:dialog propertyName="behaviorName" propertyId="behaviorId" showStatus="edit" style="width:95%" dialogJs="behaviorDialog();"></xform:dialog>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">${lfn:message('sys-modeling-base:lbpm.robot.dynamic')}</td>
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

		<script>
			LUI.ready(function(){
				initDocument();
			});

			function initDocument() {
				if(parent.NodeData && parent.NodeData.content){
					var datas = JSON.parse(parent.NodeData.content);
					$("input[name='behaviorId']").val(datas.behaviorId);
					$("input[name='behaviorName']").val(datas.behaviorName);
					if(datas.behaviorId){
						if(datas.inputs){
							LUI("inputsView").config.values = datas.inputs;
						}
						buildInputs(datas.behaviorId);
					}
				}
			};

			function behaviorDialog(){
				var dialog = new KMSSDialog();
				dialog.winTitle = "${lfn:message('sys-modeling-base:modeling.form.Choice')}";
				//findInfosByModelId
				dialog.URL =  Com_Parameter.ContextPath+"sys/modeling/base/form/flow/flow_trigger_select.jsp?type=1&modelId="+parent.FlowChartObject.FdAppModelId;
				dialog.SetAfterShow(function(rtn){
					if(rtn && rtn.data && rtn.data.length > 0){
						var values = rtn.data[0].data;
						$("[name='behaviorId']").val(values.fdId);
						$("[name='behaviorName']").val(values.fdName);
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
				rs.behaviorId = $("input[name='behaviorId']").val();
				rs.behaviorName = $("input[name='behaviorName']").val();
				// 处理入参
				rs.inputs = LUI("inputsView").getKeyData();
				return JSON.stringify(rs);
			}
		</script>
	</template:replace>
</template:include>