/**
 * 自动仿真
 */

var AutomaticSimulation = new Object();

AutomaticSimulation.testFlag = 0;//仿真表示0表示未进行过仿真，1为进行过
/**
 * 异常处理对象
 */
AutomaticSimulation.nodeExecption = NodeExecption;
/**
 * 日志操作对象
 */
AutomaticSimulation.logUtil=LogUtil;

/**
 * 历史路由
 */
AutomaticSimulation.historyRoute = new Array();

/**
* 根据并行分支的处理方式返回相应的流出线
* @param {Node} node 节点对象 
*/
AutomaticSimulation.getSplitNodeLineOut = function (node) {
    var vLineOut = new Array();
    //并行分支为公式启动时，启动类型有（all、condition）
    if (node.Data.splitType == "condition") {
        var vJsonArray = this.nodeExecption.branchFormulaCalculation(node, this.logUtil.createNodeTestLog(node));
        var vEndNodeIds = vJsonArray[0].endNode.split(",");
        for (l in node.LineOut) {
            for (i in vEndNodeIds) {
                if (node.LineOut[l].EndNode.Data.id == vEndNodeIds[i]) {
                    vLineOut.push(node.LineOut[l]);
                    break;
                }
            }
        }
    }
    else {
        //并行分支为“全部启动”时将所有的流出线放入待运行池中
        vLineOut = node.LineOut;
    }
    return vLineOut;
}
/**
 * 并行分支模拟运行
 * @param {Node} node 节点对象
 * @param {FlowChartObject} vFlowChartObject 流程对象
 */
AutomaticSimulation.splitNodeSimulation = function (node, vFlowChartObject) {
    var result = "ok";
    var vLineOut = new Array();
    vLineOut = this.getSplitNodeLineOut(node);
    var vrelatedNode = vFlowChartObject.Nodes.GetNodeById(node.Data.relatedNodeIds);
    for (l in vLineOut) {
        //设置流出线颜色
        vFlowChartObject.SetStrokeColor(vLineOut[l].DOMElement, ChartUtil.Color.LINE_AFTER_COLOR);
        result = this.nodeSimulation(vLineOut[l].EndNode, vFlowChartObject);
        //出现异常后直接退出遍历
        if (result == "error") {
            return result;
        }
        //结束并行分支为任意结束时，任意分支结束直接跳出循环
        if (vrelatedNode.Data.joinType == "anyone") {
            break;
        }
    }
    result = this.nodeSimulation(vrelatedNode, vFlowChartObject);
    return result;
}

/**
 * 校验是否重复路由，出现死循环
 * @param {currentNode} node 当前节点
 * @param {nextNode} nextNode 下一个节点 
 * @param {historyRoute} historyRoute 历史路由
 */
AutomaticSimulation.checkLoop=function (currentNode,nextNode,historyRoute){
	/**
	 * 将历史路由数组转化为“,"分割的字符串例如"N1,N2,N3,N4,N5,N6,N7"
	 * 将当前节点和下一个节点组成字符串路由例如“N3,N7”
	 * 当前路由在历史路由中出现过，则为判定为死循环
	 */
	var result=true;
    var route = historyRoute.join(",");
    var currentRoute = currentNode.Data.id+","+nextNode.Data.id;
    if(route.indexOf(currentRoute)>=0){
    	result = false;
    }
    return result;
}

/**
 * 流程节点自动仿真
 * @param {Node} node 流程节点对象
 * @param {FlowChartObject} vFlowChartObject 流程对象
 */
AutomaticSimulation.nodeSimulation = function (node, vFlowChartObject) {
    var nextNode = null;//下一个节点
    var result = "ok";
    nextNode = NodeSimulation.nodeRun(node, vFlowChartObject);	
    this.historyRoute.push(node.Data.id);//将模拟过的节点添加到历史路由中
    if (nextNode != null) {
    	if(!this.checkLoop(node,nextNode,this.historyRoute)){
        	//变更流程节点颜色
            ChartUtil.setNodeColor(nextNode, vFlowChartObject, ChartUtil.Color.PASS_NODE_COlOR);
            result = "ok";
            var nodeTestLog = this.logUtil.createNodeTestLog(nextNode);
            nodeTestLog.approvalStatus=1;
            nodeTestLog.logMessage="该节点处出现无限循环，仿真结束";
            FlowSimulation.NodeTestLogs.push(nodeTestLog);
            return result;
        } 
        //判断是否是并行分支，并行分支需要单独设置流出线颜色（并行分支的流出线颜色控制要单独处理）
        if (node.Type != "splitNode") {
            //设置流出线颜色
            ChartUtil.setOutLineColor(node, nextNode, vFlowChartObject, ChartUtil.Color.LINE_AFTER_COLOR);
        }
        //判断当前仿真是否出现警告
        if(FlowSimulation.NodeTestLogs[FlowSimulation.NodeTestLogs.length-1].approvalStatus==2){
        	//变更流程节点颜色
            ChartUtil.setNodeColor(node, vFlowChartObject, ChartUtil.Color.WARN_COLOR);
        }
        else{
        	//变更流程节点颜色
            ChartUtil.setNodeColor(node, vFlowChartObject, ChartUtil.Color.PASS_NODE_COlOR);
        }
        
        if (nextNode.Type == "endNode") {
            NodeSimulation.endNodeTest(nextNode, vFlowChartObject);
            //变更流程节点颜色
            ChartUtil.setNodeColor(nextNode, vFlowChartObject, ChartUtil.Color.PASS_NODE_COlOR);
            return result;
        }
        else if (node.Type == "splitNode") {
            //并行分支模拟
            result = this.splitNodeSimulation(node, vFlowChartObject);
        }
        else if (nextNode.Type == "joinNode") {
            return result;
        }
        else {
            result = this.nodeSimulation(nextNode, vFlowChartObject);
        }
    }
    else {
        //变更流程节点颜色
        ChartUtil.setNodeColor(node, vFlowChartObject, ChartUtil.Color.ERROR_COLOR);
        result = "error";
    }
    return result;
}