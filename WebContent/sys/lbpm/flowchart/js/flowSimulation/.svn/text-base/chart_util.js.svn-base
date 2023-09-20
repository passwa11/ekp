/**
 * 流程图操作
 */

var ChartUtil=new Object();

//常用颜色集合
ChartUtil.Color = {
    PASS_NODE_COlOR: "#E4FEEF",//通过
    ERROR_COLOR: "#FED6D6",//异常
    WARN_COLOR:"#FFF68F",//警告
	LINE_AFTER_COLOR: "#009900",//经过的流出线颜色
	NODE_COLOR:"#FFFFFF",//节点颜色
	LINE_COLOR:"#444444"//连接线颜色
};
/**
 * 设置流出线颜色
 * @param {Node} node 流出源头节点对象
 * @param {Node} nextNode 流向节点对象
 * @param {FlowChartObject} vFlowChartObject 流程对象
 * @param {String} vColor 颜色
 */
ChartUtil.setOutLineColor=function(node, nextNode, vFlowChartObject, vColor) {
	//设置流出线颜色
	for (l in node.LineOut) {
		if (node.LineOut[l].EndNode.Data.id == nextNode.Data.id) {
			this.setLineColor(node.LineOut[l],vFlowChartObject, vColor);
		}
	}
}
/**
 * 设置连接线颜色
 * @param {Line} line 流出源头节点对象
 * @param {FlowChartObject} vFlowChartObject 流程对象
 * @param {String} vColor 颜色
 */
ChartUtil.setLineColor=function(line,vFlowChartObject,vColor){
	vFlowChartObject.SetStrokeColor(line.DOMElement, vColor);
}
/**
 * 设置节点颜色
 * @param {Node} node 流出源头节点对象
 * @param {FlowChartObject} vFlowChartObject 流程对象
 * @param {String} vColor 颜色
 */
ChartUtil.setNodeColor=function(node,vFlowChartObject,vColor){
    vFlowChartObject.SetFillcolor(node.DOMElement, vColor);//变更流程节点颜色
}
/**
 * 重置流程图节点和连接线的颜色
 * @param {FlowChartObject} vFlowChartObject 流程对象
 */
ChartUtil.resetChart=function(vFlowChartObject){
	var nodes=vFlowChartObject.Nodes.all;//获得所有节点元素
	//重置所有节点颜色
	for(n in nodes){
		this.setNodeColor(nodes[n],vFlowChartObject,this.Color.NODE_COLOR);
	}
	var lines=vFlowChartObject.Lines.all;
	for(l in lines){
		this.setLineColor(lines[l],vFlowChartObject,this.Color.LINE_COLOR);
	}
}