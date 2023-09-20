/**
 * 手动仿真
 */
var ManualSimulation = new Object();
/**
 * 流程图操作对象
 */
ManualSimulation.chartUtil = ChartUtil;
/**
 * 异常处理对象
 */
ManualSimulation.nodeExecption = NodeExecption;
/**
 * 日志操作对象
 */
ManualSimulation.logUtil = LogUtil;
/**
 * 运行历史
 */
ManualSimulation.historyNode = new Array();
/**
 * 当前节点
 */
ManualSimulation.currentNode = null;
/**
 * 待处理的并行分支队列
 */
ManualSimulation.splitNodes = new Array();
/**
 * 待处理的并行分支流出线分支
 */
ManualSimulation.splitNodesLineOut = new Array();
/**
 * 是否已经完成所有的仿真,0为否，1为是
 */
ManualSimulation.isEndType = 0;

/**
 * 是否已手动仿真，0为否，1为是
 */
ManualSimulation.testFlag = 0;

/**
 * 节点模拟对象
 */
ManualSimulation.nodeSimulation = NodeSimulation;
/**
 * 开始单步调试
 */
ManualSimulation.startSimulation = function (node, vFlowChartObject) {
    //清理数据缓存
    this.historyNode = new Array();
    this.splitNodes = new Array();
    this.splitNodesLineOut = new Array();
    this.isEndType = 0;
    this.currentNode = null;


    this.nextStep(node, vFlowChartObject);
}
/**
* 根据并行分支的处理方式返回相应的流出线
* @param {Node} node 节点对象 
*/
ManualSimulation.getSplitNodeLineOut = function (node) {
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
ManualSimulation.splitNodeHandle = function (node, type) {
    var result = null;
    var vlastOneNode = null;
    //方法状态为0时才进行节点信息的缓存，0为正常调用，1为从结束并行分支节点返回
    if (type == 0) {
        this.splitNodes.push(node);//缓存该并行分支节点
        var vLineOutTemp = new Array();
        vLineOutTemp = vLineOutTemp.concat(this.getSplitNodeLineOut(node));
        this.splitNodesLineOut.push(vLineOutTemp);//将该并行分支的可用流出线缓存
        this.nodeSimulation.nodeRun(node);//执行节点测试
    }
    var nextLineOut = null;
    nextLineOut = this.splitNodesLineOut[this.splitNodesLineOut.length - 1].pop();
    result = nextLineOut.EndNode;
    return result;
}
ManualSimulation.joinNodeHandle = function (node) {
    var result = null;
    if (node.Data.joinType == "anyone") {
        //并行分支结束节点的状态为任意结束时结束并行分支
        result = this.nodeSimulation.nodeRun(node);
        this.splitNodesLineOut.pop();
        this.splitNodes.pop();
    }
    else if (this.splitNodesLineOut[this.splitNodesLineOut.length - 1].length == 0) {
        //当该分支的所有分支都运行结束后，结束并行分支
        result = this.nodeSimulation.nodeRun(node);
        this.splitNodesLineOut.pop();
        this.splitNodes.pop();
    }
    else {
        result = this.splitNodeHandle(this.splitNodes[this.splitNodes.length - 1], 1);
    }
    return result;
}
/**
 * 下一步
 */
ManualSimulation.nextStep = function (node, vFlowChartObject) {
    var result = "ok";
    var vlastOneNode = null;
    if (this.historyNode.length > 0) {
        vlastOneNode = this.historyNode[this.historyNode.length - 1];
        //设置流出线颜色
        this.chartUtil.setOutLineColor(vlastOneNode, node, vFlowChartObject, this.chartUtil.Color.LINE_AFTER_COLOR);
    }
    var nodeTemp = null;
    var splitNodeTemp = null;
    if (node.Type == "splitNode") {
        //节点类型为开始并行分支
        nodeTemp = this.splitNodeHandle(node, 0);
    }
    else if (node.Type == "joinNode") {
        if (node.Data.joinType != "anyone" && this.splitNodesLineOut[this.splitNodesLineOut.length - 1].length != 0) {
            splitNodeTemp = this.splitNodes[this.splitNodes.length - 1];
        }
        //节点类型为结束并行分支
        nodeTemp = this.joinNodeHandle(node);
    }
    else {
        nodeTemp = this.nodeSimulation.nodeRun(node);//执行节点测试
    }
    if (nodeTemp != null) {
        this.currentNode = nodeTemp;
        this.chartUtil.setNodeColor(node, vFlowChartObject, this.chartUtil.Color.PASS_NODE_COlOR);
        //将运行过的节点放入运行历史中
        this.historyNode.push(node);
        if (splitNodeTemp != null) {
            this.historyNode.push(splitNodeTemp);
        }
        if (nodeTemp.Type == "endNode" && node.Type == "endNode") {
            //手动仿真已经到达结束节点并且无异常，表示正常结束
            this.isEndType = 1;
        }
    }
    else {
        //节点异常处理
        this.chartUtil.setNodeColor(node, vFlowChartObject, this.chartUtil.Color.ERROR_COLOR);
        result = "error";
        //判断是否已经添加过,避免异常的时候重复添加
        if (vlastOneNode.Data.id != node.Data.id) {
            this.historyNode.push(node);
        }
    }
    return result;
}
/**
 * 上一步
 */
ManualSimulation.previousStep = function (vFlowChartObject) {
    this.currentNode = this.historyNode.pop();
    if (this.isEndType == 1) {
        this.isEndType = 0;
    }
    if (this.currentNode.Type == "startNode") {
        this.historyNode.push(this.currentNode);
        alert(ManualSimulationLang.Message_a);
    }
    else {
        //重置节点颜色
        this.chartUtil.setNodeColor(this.currentNode, vFlowChartObject, this.chartUtil.Color.NODE_COLOR);
        //重置链接线颜色
        var vlastOneNode = this.historyNode[this.historyNode.length - 1];
        this.chartUtil.setOutLineColor(vlastOneNode, this.currentNode, vFlowChartObject, this.chartUtil.Color.LINE_COLOR);
    }
}
