/**********************************************************
功能：组开始节点，组结束节点
使用：
	节点初始化
作者：linbb
创建时间：2019-3-18
修改记录：
**********************************************************/
FlowChartObject.Lang.Include("sys-lbpmservice-node-group");

(function(FlowChartObject) {
	// groupStartNode
	FlowChartObject.Nodes.InitializeTypes.push(function() {
		var nodeType = new FlowChartObject.Nodes.NodeType("groupStartNode");
		nodeType.ShowInOperation = false;
		nodeType.Initialize = function(){
			this.CanDelete = false;
			this.CanCopy = false;
			this.CanLinkInCount = 0;
			this.CanChangeOut = false;
			this.TypeCode = FlowChartObject.NODETYPE_START;
			FlowChartObject.Nodes.CreateRoundRectDOM(this);
		};
	});
	
	// groupEndNode
	FlowChartObject.Nodes.InitializeTypes.push(function() {
		var nodeType = new FlowChartObject.Nodes.NodeType("groupEndNode");
		nodeType.ShowInOperation = false;
		nodeType.Initialize = function(){
			this.CanDelete = false;
			this.CanCopy = false;
			this.CanLinkOutCount = 0;
			this.TypeCode = FlowChartObject.NODETYPE_END;
			FlowChartObject.Nodes.CreateRoundRectDOM(this);
		};
	});
})(FlowChartObject);