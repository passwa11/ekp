/**
 * 节点仿真
 */
var NodeSimulation = new Object();

/**
 * 日志操作对象
 */
NodeSimulation.logUtil = LogUtil;
/**
 * 异常解析类
 */
NodeSimulation.nodeExecption = NodeExecption;
/**
* 节点测试方法
* @param {Node} node 节点对象
*/
NodeSimulation.nodeRun = function (node) {
	var nextNode = null;
	var vFunctionName = node.Type + "Test";
	if (NodeSimulation[vFunctionName]) {
		nextNode = NodeSimulation[vFunctionName](node);
	} else {
		nextNode = node.LineOut[0].EndNode;
	}
	return nextNode;
}
/**
 * 结束节点测试
 * @param {Node} node 节点对象
 */
NodeSimulation.endNodeTest=function (node) {
	var nextNode = node;
	var nodeTestLog = this.logUtil.createNodeTestLog(node);
	FlowSimulation.NodeTestLogs.push(nodeTestLog);
	return nextNode;
}
/**
 * 开始节点测试
 * @param {Node} node 节点对象
 */
NodeSimulation.startNodeTest = function (node) {
	var nextNode = null;
	var nodeTestLog = this.logUtil.createNodeTestLog(node);
	var nodeExecptionActionList = new Array();
	//节点流出线异常处理
	nodeExecptionActionList.push("nodeLineOutIsNullExecption");
	nextNode = this.nodeExecption.nodeExecptionAction(node, nodeTestLog, nodeExecptionActionList);
	FlowSimulation.NodeTestLogs.push(nodeTestLog);
	return nextNode;
}
/**
 * 起草节点测试
 * @param {Node} node 节点对象
 */
NodeSimulation.draftNodeTest=function (node) {
	var nextNode = null;
	var nodeTestLog = this.logUtil.createNodeTestLog(node);
	var nodeExecptionActionList = new Array();
	//节点流出线异常处理
	nodeExecptionActionList.push("nodeLineOutIsNullExecption");
	nextNode = this.nodeExecption.nodeExecptionAction(node, nodeTestLog, nodeExecptionActionList);
	//起草人身份
	var processFdIdentity = $("#sFdHandlerRoleInfoIds option:selected").text();
	nodeTestLog.handlerNames=processFdIdentity;
	FlowSimulation.NodeTestLogs.push(nodeTestLog);
	return nextNode;
}
/**
 * 审批节点测试
 * @param {Node} node 节点对象
 */
NodeSimulation.reviewNodeTest = function (node) {
	var nextNode = null;
	var nodeTestLog = this.logUtil.createNodeTestLog(node);
	var nodeExecptionActionList = new Array();
	//节点流出线异常处理
	nodeExecptionActionList.push("nodeLineOutIsNullExecption");
	//节点处理人异常处理
	nodeExecptionActionList.push("nodeHandlerExecption");
	nextNode = this.nodeExecption.nodeExecptionAction(node, nodeTestLog, nodeExecptionActionList);
	FlowSimulation.NodeTestLogs.push(nodeTestLog);
	return nextNode;
}
/**
 * 人工决策节点测试
 * @param {Node} node 节点对象
 */
NodeSimulation.manualBranchNodeTest = function (node) {
	var nextNode = null;
	var nodeTestLog = this.logUtil.createNodeTestLog(node);
	var nodeExecptionActionList = new Array();
	//节点流出线异常处理
	nodeExecptionActionList.push("nodeLineOutIsNullExecption");
	//人工决策流向检测异常
	nodeExecptionActionList.push("manualBranchExecption");
	nextNode = this.nodeExecption.nodeExecptionAction(node, nodeTestLog, nodeExecptionActionList);
	FlowSimulation.NodeTestLogs.push(nodeTestLog);
	return nextNode;
}
/**
 * 条件审批节点测试
 * @param {Node} node 节点对象
 */
NodeSimulation.autoBranchNodeTest = function (node) {
	var nextNode = null;
	var nodeTestLog = this.logUtil.createNodeTestLog(node);
	var nodeExecptionActionList = new Array();
	//节点流出线异常处理
	nodeExecptionActionList.push("nodeLineOutIsNullExecption");
	//条件分支节点公式解析异常
	nodeExecptionActionList.push("autoBranchFormulaExecption");
	nextNode = this.nodeExecption.nodeExecptionAction(node, nodeTestLog, nodeExecptionActionList);
	FlowSimulation.NodeTestLogs.push(nodeTestLog);
	return nextNode;
}
/**
 * 签字节点测试
 * @param {Node} node 节点对象
 */
NodeSimulation.signNodeTest = function (node) {
	var nextNode = null;
	var nodeTestLog = this.logUtil.createNodeTestLog(node);
	var nodeExecptionActionList = new Array();
	//节点流出线异常处理
	nodeExecptionActionList.push("nodeLineOutIsNullExecption");
	//节点处理人异常处理
	nodeExecptionActionList.push("nodeHandlerExecption");
	nextNode = this.nodeExecption.nodeExecptionAction(node, nodeTestLog, nodeExecptionActionList);
	FlowSimulation.NodeTestLogs.push(nodeTestLog);
	return nextNode;
}
/**
 * 抄送节点测试
 * @param {Node} node 节点对象
 */
NodeSimulation.sendNodeTest = function (node) {
	var nextNode = null;
	var nodeTestLog = this.logUtil.createNodeTestLog(node);
	var nodeExecptionActionList = new Array();
	//节点流出线异常处理
	nodeExecptionActionList.push("nodeLineOutIsNullExecption");
	//节点处理人异常处理
	nodeExecptionActionList.push("nodeHandlerExecption");
	nextNode = this.nodeExecption.nodeExecptionAction(node, nodeTestLog, nodeExecptionActionList);
	FlowSimulation.NodeTestLogs.push(nodeTestLog);
	return nextNode;
}
/**
 * 机器人节点测试
 * @param {Node} node 节点对象
 */
NodeSimulation.robotNodeTest = function (node) {
	var nextNode = null;
	var nodeTestLog = this.logUtil.createNodeTestLog(node);
	var nodeExecptionActionList = new Array();
	//节点流出线异常处理
	nodeExecptionActionList.push("nodeLineOutIsNullExecption");
	nextNode = this.nodeExecption.nodeExecptionAction(node, nodeTestLog, nodeExecptionActionList);
	FlowSimulation.NodeTestLogs.push(nodeTestLog);
	return nextNode;
}
/**
 * 启动子流程节点测试
 * @param {Node} node 节点对象
 */
NodeSimulation.startSubProcessNodeTest = function (node) {
	var nextNode = null;
	var nodeTestLog = this.logUtil.createNodeTestLog(node);
	var nodeExecptionActionList = new Array();
	//节点流出线异常处理
	nodeExecptionActionList.push("nodeLineOutIsNullExecption");
	nextNode = this.nodeExecption.nodeExecptionAction(node, nodeTestLog, nodeExecptionActionList);
	FlowSimulation.NodeTestLogs.push(nodeTestLog);
	return nextNode;
}
/**
 * 回收子流程节点测试
 * @param {Node} node 节点对象
 */
NodeSimulation.recoverSubProcessNodeTest = function (node) {
	var nextNode = null;
	var nodeTestLog = this.logUtil.createNodeTestLog(node);
	var nodeExecptionActionList = new Array();
	//节点流出线异常处理
	nodeExecptionActionList.push("nodeLineOutIsNullExecption");
	nextNode = this.nodeExecption.nodeExecptionAction(node, nodeTestLog, nodeExecptionActionList);
	FlowSimulation.NodeTestLogs.push(nodeTestLog);
	return nextNode;
}
/**
 * 启动并行分支节点测试
 * @param {Node} node 节点对象
 */
NodeSimulation.splitNodeTest = function (node) {
	var nextNode = null;
	var nodeTestLog = this.logUtil.createNodeTestLog(node);
	var nodeExecptionActionList = new Array();
	//节点流出线异常处理
	nodeExecptionActionList.push("nodeLineOutIsNullExecption");
	//并行分支启动方式为公式启动时添加公式解析异常处理
	if (node.Data.splitType == "condition") {
		//分支公式解析异常处理
		nodeExecptionActionList.push("autoBranchFormulaExecption");
	}

	nextNode = this.nodeExecption.nodeExecptionAction(node, nodeTestLog, nodeExecptionActionList);
	FlowSimulation.NodeTestLogs.push(nodeTestLog);
	return nextNode;
}
/**
 * 结束并行分支节点测试
 * @param {Node} node 节点对象
 */
NodeSimulation.joinNodeTest = function (node) {
	var nextNode = null;
	var nodeTestLog = this.logUtil.createNodeTestLog(node);
	var nodeExecptionActionList = new Array();
	//节点流出线异常处理
	nodeExecptionActionList.push("nodeLineOutIsNullExecption");
	nextNode = this.nodeExecption.nodeExecptionAction(node, nodeTestLog, nodeExecptionActionList);
	FlowSimulation.NodeTestLogs.push(nodeTestLog);
	return nextNode;
}
/**
 * 投票节点测试
 * @param {Node} node 节点对象
 */
NodeSimulation.voteNodeTest = function (node) {
	var nextNode = null;
	var nodeTestLog = this.logUtil.createNodeTestLog(node);
	var nodeExecptionActionList = new Array();
	//节点流出线异常处理
	nodeExecptionActionList.push("nodeLineOutIsNullExecption");
	//节点处理人异常处理
	nodeExecptionActionList.push("nodeHandlerExecption");
	nextNode = this.nodeExecption.nodeExecptionAction(node, nodeTestLog, nodeExecptionActionList);
	FlowSimulation.NodeTestLogs.push(nodeTestLog);
	return nextNode;
}
/**
 * 在节点对象中，通过流出线ID返回下一个节点的节点对象
 * @param {Node} node 节点对象
 * @param {string} outLineId 流出线id
 */
NodeSimulation.getEndNodeByLineId = function (node, outLineId) {
	var result = null;
	for (l in node.LineOut) {
		if (node.LineOut[l].Data.id == outLineId) {
			result = node.LineOut[l].EndNode;
			break;
		}
	}
	return result;
}