/**********************************************************
功能：抢办节点
使用：
	抢办节点节点类型初始化方法
作者：wangxiao
创建时间：2021-12-3
修改记录：
**********************************************************/

FlowChartObject.Lang.Include("sys-lbpmservice");
FlowChartObject.Lang.Include("sys-lbpmservice-node-robtodonode");
Com_IncludeJsFile(Com_Parameter.ContextPath+"sys/lbpmservice/node/common/node_handler.js",true);

(function(FlowChartObject) {
	FlowChartObject.Nodes.InitializeTypes.push(function() {
		var nodeType = new FlowChartObject.Nodes.NodeType("robtodoNode");
		nodeType.Hotkey = "T";
		nodeType.embedded = true;
		nodeType.ImgUrl = '../images/buttonbar_robtodoNode.png';
		nodeType.ButtonIndex = 14;
		nodeType.Initialize = function() { // 节点初始化代码
			FlowChartObject.Nodes.CreateBigRectDOM(this);
			if(this.Data.ignoreOnHandlerEmpty==null)
				this.SetStatus(FlowChartObject.STATUS_UNINIT);
			this.RefreshInfo = FlowChartObject.OAProcess.ReviewNodeRefreshInfo;
			this.Check = FlowChartObject.OAProcess.ReviewNodeCheck;
			this.RefreshRefId = function(idMap){
				this.Data.canModifyHandlerNodeIds = FlowChart_OAProcess_GetNewNodeIds(this.Data.canModifyHandlerNodeIds, idMap);
				this.Data.mustModifyHandlerNodeIds = FlowChart_OAProcess_GetNewNodeIds(this.Data.mustModifyHandlerNodeIds, idMap);
				//增加意见权限属性的初始化
				this.Data.canModifyNotionPopedom = FlowChart_OAProcess_GetNewNodeIds(this.Data.canModifyNotionPopedom, idMap);
				this.Data.nodeCanViewCurNodeIds = FlowChart_OAProcess_GetNewNodeIds(this.Data.nodeCanViewCurNodeIds, idMap);
				this.Data.nodeCanViewCurNodeNames = FlowChart_OAProcess_GetNewNodeIds(this.Data.nodeCanViewCurNodeNames, idMap);
				this.Data.otherCanViewCurNodeIds = FlowChart_OAProcess_GetNewNodeIds(this.Data.otherCanViewCurNodeIds, idMap);
				this.Data.otherCanViewCurNodeNames = FlowChart_OAProcess_GetNewNodeIds(this.Data.otherCanViewCurNodeNames, idMap);
			};
			this.holdType = "0"; // 会审类型
			this.ShowDetailAfter = FlowChartObject.OAProcess.ShowDetailAfter;
		};
	});
})(FlowChartObject);