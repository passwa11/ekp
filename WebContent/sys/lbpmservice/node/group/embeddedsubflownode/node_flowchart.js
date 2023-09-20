/**********************************************************
功能：嵌入子流程节点
使用：
	节点初始化
作者：wx
创建时间：2019-7-8
修改记录：
**********************************************************/
FlowChartObject.Lang.Include("sys-lbpmservice-node-group");

(function(FlowChartObject) {
	
	// freeSubFlowNode
	FlowChartObject.Nodes.InitializeTypes.push(function() {
		var nodeType = new FlowChartObject.Nodes.NodeType("embeddedSubFlowNode");
		nodeType.Hotkey = "M";
		nodeType.ImgUrl = '../images/buttonbar_embeddedSubFlowNode.png';
		nodeType.isGroupNode = true;
		nodeType.ButtonIndex = 15;
		nodeType.CreateNode = function() {
			var gNode = new FlowChart_Node("embeddedSubFlowNode");
			gNode.MoveTo(EVENT_X, EVENT_Y, true);
			FlowChart_NodeType_AutoLink(gNode);
			// 创建embeddedSubFlowNode的同时，把groupStartNode和groupEndNode以及连线一并创建（组内的子都挪到可见区域外）
			var sNode = new FlowChart_Node("groupStartNode");
			sNode.MoveTo(-EVENT_X, -EVENT_Y, true);
			var eNode = new FlowChart_Node("groupEndNode");
			eNode.MoveTo(-EVENT_X, -EVENT_Y, true);
			var line = new FlowChart_Line();
			line.LinkNode(sNode, eNode, FlowChartObject.NODEPOINT_POSITION_BOTTOM, FlowChartObject.NODEPOINT_POSITION_TOP);
			
			// 组与子的关系绑定
			gNode.Data.startNodeId = sNode.Data.id;
			gNode.Data.endNodeId = eNode.Data.id;
			
			sNode.Data.groupNodeId = gNode.Data.id;
			sNode.Data.groupNodeType = gNode.Type;
			eNode.Data.groupNodeId = gNode.Data.id;
			eNode.Data.groupNodeType = gNode.Type;
		};
		nodeType.Initialize = function() { // 节点初始化代码
			FlowChartObject.Nodes.CreateRectDOM(this);
			this.CanLinkInCount = 1;
			this.CanLinkOutCount = 1;
			this.RefreshInfo = function() {
				var info = "·" + FlowChartObject.Lang.Node.name + ": " + this.Data.name;
				info += "\r\n·" + FlowChartObject.Lang.Node.id + ": " + this.Data.id;
				this.Info = Com_FormatText(info);
			};
			this.Check = function() {
				if(!FlowChartObject.CheckFlowNode(this)){
					return false;
				}
				return true;
			};
			this.Delete = function () {
				for(;this.LineOut.length>0;)
					this.LineOut[0].Delete();
				for(; this.LineIn.length>0;)
					this.LineIn[0].Delete();
				FlowChartObject.Nodes.all = Com_ArrayRemoveElem(FlowChartObject.Nodes.all, this);
				FlowChartObject.RemoveElement(this.DOMElement);
				// 还要由groupStartNode往下遍历组内所有的节点与连线，将其删除
				var groupStartNode = FlowChartObject.Nodes.GetNodeById(this.Data.startNodeId);
				var deleteNodes = new Array();
				var deleteLines = new Array();
				var trackDeleteNodeLine = function(node) {
					deleteNodes.push(node);
					for (var i=0;i<node.LineOut.length;i++) {
						if (Com_ArrayGetIndex(deleteLines, node.LineOut[i]) == -1) {
							deleteLines.push(node.LineOut[i]);
							trackDeleteNodeLine(node.LineOut[i].EndNode);
						}
					}
				}
				trackDeleteNodeLine(groupStartNode);
				
				for (var j=0;j<deleteNodes.length;j++) {
					var deleteNode = deleteNodes[j];
					FlowChartObject.Nodes.all = Com_ArrayRemoveElem(FlowChartObject.Nodes.all, deleteNode);
					FlowChartObject.RemoveElement(deleteNode.DOMElement);
				}
				for (var k=0;k<deleteLines.length;k++) {
					var deleteLine = deleteLines[k]
					FlowChartObject.Lines.all = Com_ArrayRemoveElem(FlowChartObject.Lines.all, deleteLine);
					if(deleteLine.DOMText) {
						FlowChartObject.RemoveElement(deleteLine.DOMText);
					}
					FlowChartObject.RemoveElement(deleteLine.DOMElement);
				}
			};
		};
	});
})(FlowChartObject);