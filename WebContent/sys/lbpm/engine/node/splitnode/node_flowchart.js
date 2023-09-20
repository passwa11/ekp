/**********************************************************
功能：并行分支开始节点
使用：
	并行分支开始节点类型初始化方法
作者：傅游翔
创建时间：2012-6-6
修改记录：
**********************************************************/

FlowChartObject.Lang.Include("sys-lbpm-engine");
FlowChartObject.Lang.Include("sys-lbpm-engine-node-splitnode");

(function(FlowChartObject) {
	
	FlowChartObject.Nodes.InitializeTypes.push(function() {
		
		function FlowChart_OAProcess_SplitJoinNodeCreate(){
			var sNode = new FlowChart_Node("splitNode");
			sNode.MoveTo(EVENT_X, EVENT_Y, true);
			sNode.Data.splitType = "all";
			var eNode = new FlowChart_Node("joinNode");
			eNode.MoveTo(EVENT_X, EVENT_Y + 100, true);
			eNode.Data.joinType = "all";
			FlowChartObject.Nodes.RelateNodes(new Array(sNode, eNode));
			
			var line = new FlowChart_Line();
			line.LinkNode(sNode, eNode, FlowChartObject.NODEPOINT_POSITION_BOTTOM, FlowChartObject.NODEPOINT_POSITION_TOP);
			line.Refresh(FlowChartObject.LINE_REFRESH_TYPE_DOM);
			
			FlowChartObject.Nodes.AutoLink(sNode, eNode);
		}
		
		//并行分支开始和结束节点的虚拟节点
		var nodeType = new FlowChartObject.Nodes.NodeType("splitJoinNode");
		nodeType.embedded = true;
		nodeType.Hotkey = "B";
		//nodeType.ImgIndex  = 34;
		nodeType.ImgUrl = '../images/buttonbar_splitJoinNode.png';
		nodeType.ButtonIndex = 9;
		nodeType.CreateNode = FlowChart_OAProcess_SplitJoinNodeCreate;
		//并行分支开始节点
		nodeType = new FlowChartObject.Nodes.NodeType("splitNode");
		nodeType.ShowInOperation = false;
		nodeType.Initialize = function(){
			this.CanLinkOutCount = 2;
			this.CanLinkInCount = 1; //限制并行分支开始节点的可接入连线数为一条 by linbb
			//this.TypeCode = FlowChartObject.NODETYPE_CONDITION;
			FlowChartObject.Nodes.CreateSplitDOM(this);

			/*#148973 快速审批节点后加自定义启动并行审批，点击快速通过后，流程异常 start ouyu*/
			this.Check = function() {
				if(!FlowChartObject.CheckFlowNode(this)){
					return false;
				}
				if(this.LineIn[0]) {
					var preNode = this.LineIn[0].StartNode;
					//快速审批
					var preNodeCanFastReview = preNode.Data.canFastReview;
					//自定义分支
					var nodeSplitType = this.Data.splitType;

					if ((preNodeCanFastReview != null && preNodeCanFastReview != '' && preNodeCanFastReview != undefined) &&
						(nodeSplitType != null && nodeSplitType != '' && nodeSplitType != undefined)) {
						if (preNodeCanFastReview == 'true' && nodeSplitType == 'custom') {
							//alert("节点("+this.Data.id + "." +this.Data.name + "):是自定义并行分支,它的上一个节点("+preNode.Data.id + "." +preNode.Data.name + ")不能设置为快速审批，请调整!");
							alert(FlowChartObject.Lang.GetMessage(
								FlowChartObject.Lang.checkSplit,
								this.Data.id + "." + this.Data.name,
								preNode.Data.id + "." + preNode.Data.name));
							return false;
						}
					}
				}
				return true;
			};
			/*#148973 快速审批节点后加自定义启动并行审批，点击快速通过后，流程异常 end*/
		};
		
	});
})(FlowChartObject);