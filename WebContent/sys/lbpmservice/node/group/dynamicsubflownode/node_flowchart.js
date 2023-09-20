/**********************************************************
 功能：动态子流程节点
 使用：
 节点初始化
 作者：wangxiao
 创建时间：2021-10-12
 修改记录：
 **********************************************************/
FlowChartObject.Lang.Include("sys-lbpmservice-node-group");

(function(FlowChartObject) {

	// dynamicSubFlowNode
	FlowChartObject.Nodes.InitializeTypes.push(function() {
		var nodeType = new FlowChartObject.Nodes.NodeType("dynamicSubFlowNode");
		nodeType.Hotkey = "Y";
		nodeType.ImgUrl = '../images/buttonbar_dynamicSubFlowNode.png';
		nodeType.isGroupNode = true;
		nodeType.ButtonIndex = 18;
		nodeType.CreateNode = function() {
			var dNode = new FlowChart_Node("dynamicSubFlowNode");
			dNode.MoveTo(EVENT_X, EVENT_Y, true);
			FlowChart_NodeType_AutoLink(dNode);
			// 创建dynamicSubFlowNode的同时，把groupStartNode和groupEndNode以及连线一并创建（组内的子都挪到可见区域外）
			var stNode = new FlowChart_Node("groupStartNode");
			stNode.MoveTo(-EVENT_X, -EVENT_Y, true);

			//内置一个并行分支
			var sNode = new FlowChart_Node("splitNode");
			sNode.MoveTo(-EVENT_X, -EVENT_Y, true);
			sNode.Data.splitType = "all";
			var sline = new FlowChart_Line();
			sline.LinkNode(stNode, sNode, FlowChartObject.NODEPOINT_POSITION_BOTTOM, FlowChartObject.NODEPOINT_POSITION_TOP);
			var eNode = new FlowChart_Node("joinNode");
			eNode.MoveTo(-EVENT_X, -(EVENT_Y + 100), true);
			eNode.Data.joinType = "all";
			FlowChartObject.Nodes.RelateNodes(new Array(sNode, eNode));
			var eline = new FlowChart_Line();
			eline.LinkNode(sNode, eNode, FlowChartObject.NODEPOINT_POSITION_BOTTOM, FlowChartObject.NODEPOINT_POSITION_TOP);
			eline.Refresh(FlowChartObject.LINE_REFRESH_TYPE_DOM);

			var edNode = new FlowChart_Node("groupEndNode");
			edNode.MoveTo(-EVENT_X, -EVENT_Y, true);
			var gline = new FlowChart_Line();
			gline.LinkNode(eNode, edNode, FlowChartObject.NODEPOINT_POSITION_BOTTOM, FlowChartObject.NODEPOINT_POSITION_TOP);

			// 组与子的关系绑定
			dNode.Data.startNodeId = stNode.Data.id;
			dNode.Data.endNodeId = edNode.Data.id;
			dNode.Data.splitNodeId = sNode.Data.id;
			dNode.Data.joinNodeId = eNode.Data.id;

			sNode.Data.groupNodeId = dNode.Data.id;
			sNode.Data.groupNodeType = dNode.Type;
			eNode.Data.groupNodeId = dNode.Data.id;
			eNode.Data.groupNodeType = dNode.Type;

			stNode.Data.groupNodeId = dNode.Data.id;
			stNode.Data.groupNodeType = dNode.Type;
			edNode.Data.groupNodeId = dNode.Data.id;
			edNode.Data.groupNodeType = dNode.Type;
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
				var preNode = this.LineIn[0].StartNode;
				var desc = FlowChartObject.Nodes.nodeDesc(preNode);
				if ((!desc.isHandler && !desc.isDraftNode && preNode.Type != "manualBranchNode") || desc.isSendNode || preNode.Type == "voteNode") {
					alert("节点("+this.Data.id + "." +this.Data.name + ")的上一个节点不是人工审批类节点，请调整!");
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