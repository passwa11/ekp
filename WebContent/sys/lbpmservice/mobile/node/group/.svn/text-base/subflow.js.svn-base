define(["dojo/_base/lang", 
         "dojo/_base/array",
         "dojo/topic",
         "dijit/registry", 
         "dojo/ready", 
         "mui/util",
         "mui/form/Address",
         'dojo/query',
         'dojo/NodeList-dom', 
         'dojo/NodeList-html', 
         "dojo/domReady!"], function(lang, array, topic, registry, ready, util, Address, query) {

	var subFlow={};
	
	subFlow.init=function(){
		// 自由子流程相关全局变量
		lbpm.nowFreeSubFlowNodeId = null; //当前自由子流程节点ID
		lbpm.myAddedSubNodes=new Array(); //当前由我添加的子节点
		lbpm.subNodeHandlerIds = new Array(); //我添加的子节点的节点处理人ids
		lbpm.subNodeHandlerNames = new Array(); //我添加的子节点的节点处理人names
		
		lbpm.nowAdHocSubFlowNodeId = null; //当前即席子流程节点ID
		if(!lbpm.nowNodeId){
			return;
		}
		var nextNodeObj = lbpm.globals.getNextNodeObj(lbpm.nowNodeId);
		if(nextNodeObj.nodeDescType=='freeSubFlowNodeDesc') { //下一节点是自由子流程节点（自由子流程节点外)
			lbpm.nowFreeSubFlowNodeId = nextNodeObj.id
		} else if (lbpm.nodes[lbpm.nowNodeId].groupNodeId != null && lbpm.nodes[lbpm.nowNodeId].groupNodeType == "freeSubFlowNode") { //当前节点是自由子流程节点内的子节点（自由子流程节点内)
			lbpm.nowFreeSubFlowNodeId = lbpm.nodes[lbpm.nowNodeId].groupNodeId;
		}
		
		if ((lbpm.nodes[lbpm.nowNodeId].groupNodeId != null && lbpm.nodes[lbpm.nowNodeId].groupNodeType == "adHocSubFlowNode") 
				&& nextNodeObj.nodeDescType=="groupEndNodeDesc") { // 当前节点是即席子流程节点内的最后一个子节点（即席子流程节点内)
			lbpm.nowAdHocSubFlowNodeId = lbpm.nodes[lbpm.nowNodeId].groupNodeId;
		} else if (nextNodeObj.nodeDescType == "adHocSubFlowNodeDesc") { // 下一个节点是即席子流程节点（即席子流程节点外)
			lbpm.nowAdHocSubFlowNodeId = nextNodeObj.id;
		}
		
		if (lbpm.nowFreeSubFlowNodeId == null && lbpm.nowAdHocSubFlowNodeId == null) {
			return;
		}
		
		// 加载详细的流程图信息用于动态修改流程图
		var fdIsModify=$("input[name='sysWfBusinessForm.fdIsModify']")[0];
		if(fdIsModify==null || fdIsModify.value!="1"){
			var processXml = lbpm.globals.getProcessXmlString();
			document.getElementsByName("sysWfBusinessForm.fdFlowContent")[0].value = processXml;
		}
		lbpm.flow_chart_load_Frame();
		
		// 自由子流程相关
		if (lbpm.nowFreeSubFlowNodeId != null) {
			var html = "<div data-dojo-type=\"sys/lbpmservice/mobile/node/group/freesubflownode/freeSubFlowNodes\"></div>";
			
			var detailArea = query("#freeSubFlowNodeDIV");
			detailArea.html(html, {parseContent: true});
			
			topic.subscribe("/lbpm/operation/switch", function(wgt,ctx){
				// 只在切换事务时触发
				if(ctx && !ctx.methodSwitch){
					lbpm.globals.hiddenObject(document.getElementById("freeSubFlowNodeRow"), true);
					if (lbpm.myAddedSubNodes.length > 0) {
						var iframe = document.getElementById('WF_IFrame');
						var FlowChartObject = iframe.contentWindow.FlowChartObject;
						FlowChartObject.Nodes.deleteSubNode(lbpm.myAddedSubNodes[0]);
						lbpm.myAddedSubNodes = new Array();
						lbpm.subNodeHandlerIds = new Array();
						lbpm.subNodeHandlerNames = new Array();
						var flowXml = FlowChartObject.BuildFlowXML();
						if (!flowXml){
							return;
						}
						var processXMLObj = document.getElementsByName("sysWfBusinessForm.fdFlowContent")[0];
						processXMLObj.value = flowXml;
						lbpm.globals.parseXMLObj();
						lbpm.modifys = {};
						$("input[name='sysWfBusinessForm.fdIsModify']")[0].value = "1";
					}
					var nextNodeObj = lbpm.globals.getNextNodeObj(lbpm.nowNodeId);
					if(nextNodeObj.nodeDescType=='freeSubFlowNodeDesc') { //下一节点是自由子流程节点（自由子流程节点外)
						lbpm.nowFreeSubFlowNodeId = nextNodeObj.id
					} else if (lbpm.nodes[lbpm.nowNodeId].groupNodeId != null && lbpm.nodes[lbpm.nowNodeId].groupNodeType == "freeSubFlowNode") { //当前节点是自由子流程节点内的子节点（自由子流程节点内)
						lbpm.nowFreeSubFlowNodeId = lbpm.nodes[lbpm.nowNodeId].groupNodeId;
					}
					var widget = registry.byId("freeSubFlowNodes");
					if (widget) {
						widget.buildValue(widget.contentNode);
					}
				}
			});
		}
		
	};
	
	return subFlow;
});