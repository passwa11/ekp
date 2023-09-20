/**
 * 仿真日志
 */
/**
 * 测试日志对象构造函数
 * @param {int} approvalStatus
 * @param {string} nodeName 
 * @param {string} nodeType 
 * @param {string} handlerName 
 * @param {string} handlerNames 
 * @param {string} logMessage 
 * @param {string} nodeId
 */
function NodeTestLog(approvalStatus, nodeName, nodeType, handlerName, handlerNames, logMessage, nodeId) {
    this.approvalStatus = approvalStatus;//审批状态:0错误、1审批通过
    this.nodeName = nodeName;//节点名称
    this.nodeType = nodeType;//节点类型
    this.handlerName = handlerName;//当前处理人
    this.handlerNames = handlerNames;//预计处理人
    this.logMessage = logMessage;//日志信息
    this.nodeId = nodeId;//节点id
}
var LogUtil = new Object();
/**
 * 流程图操作对象
 */
LogUtil.chartUtil = ChartUtil;

LogUtil.NodeType = {
    "startNode": FlowSimulationLang.startNode,
    "endNode": FlowSimulationLang.endNode,
    "draftNode": FlowSimulationLang.draftNode,
    "reviewNode": FlowSimulationLang.reviewNode,
    "autoBranchNode": FlowSimulationLang.autoBranchNode,
    "manualBranchNode": FlowSimulationLang.manualBranchNode,
    "signNode": FlowSimulationLang.signNode,
    "sendNode": FlowSimulationLang.sendNode,
    "robotNode": FlowSimulationLang.robotNode,
    "startSubProcessNode": FlowSimulationLang.startSubProcessNode,
    "recoverSubProcessNode": FlowSimulationLang.recoverSubProcessNode,
    "splitNode": FlowSimulationLang.splitNode,
    "joinNode": FlowSimulationLang.joinNode,
    "voteNode": FlowSimulationLang.voteNode
};
/**
 * 将节点类型转换为文字描述
 * @param {Node} node 
 */
LogUtil.nodeTypeAnalysis = function (type) {
    var result = "";
    result = this.NodeType[type];
    return result;
}
/**
* 使用Node对象构造初始化的日志对象
* @param {Node} node 节点对象
*/
LogUtil.createNodeTestLog = function (node) {
    var nodeTestLog = new NodeTestLog(1, node.Text, node.Type, LogUitlLang.isNul, LogUitlLang.isNul, LogUitlLang.isNul, node.Data.id);
    return nodeTestLog;
}
/**
 * 根据审批日志表示返回指定描述
 * @param {int} status 
 */
LogUtil.approvalStatusAnalysis = function (status) {
    var result = "";
    switch (status) {
        case 0:
            result = LogUitlLang.error;
            break;
        case 1:
            result = LogUitlLang.pass;
            break;
        case 2:
            result = LogUitlLang.warn;
            break;
    }
    return result;
}
/**
 * 测试日志行tr标签代码生成
 * @param {Array<NodeTestLog>} nodeTestLogs 日志集合
 */
LogUtil.testLogHandler = function (nodeTestLogs) {
    var result = "";
    var vNum = 1;
    if (nodeTestLogs.length > 0) {
        for (n in nodeTestLogs) {
            result += this.getLogHtml(nodeTestLogs[n], vNum++);
        }
    }
    return result;
}
LogUtil.getLogHtml = function (nodeTestLog, vNum) {
    var result = "";
    if (nodeTestLog.approvalStatus == 0) {
        result += "<tr style=\"background-color:" + this.chartUtil.Color.ERROR_COLOR + ";\">";
    }
    else if(nodeTestLog.approvalStatus == 2){
    	result += "<tr style=\"background-color:" + this.chartUtil.Color.WARN_COLOR + ";\">";
    }
    else if(nodeTestLog.nodeType=="endNode"){
    	result += "<tr style=\"background-color:" + this.chartUtil.Color.PASS_NODE_COlOR + ";\">";
    }
    else {
        result += "<tr>";
    }
    result += "<td style=\"text-align:center;\">" + (vNum) + "</td>";
    result += "<td style=\"text-align:center;\">" + this.approvalStatusAnalysis(nodeTestLog.approvalStatus) + "</td>";
    result += "<td style=\"text-align:center;\">" + nodeTestLog.nodeId + "." + nodeTestLog.nodeName + "</td>";
    result += "<td style=\"text-align:center;\">" + this.nodeTypeAnalysis(nodeTestLog.nodeType) + "</td>";
    result += "<td>" + nodeTestLog.handlerNames + "</td>";
    result += "<td>" + nodeTestLog.logMessage + "</td>";
    result += "</tr>";
    return result;
}