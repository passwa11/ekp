/**
 * 节点异常解析
 */
var NodeExecption = new Object();

/**
 * 节点处理人需要其他节点指定的节点ID，例：N4;N5
 */
NodeExecption.mustModifyHandlerNodeIds = "";

/**
 * 节点异常处理
 * @param {Node} node 节点对象
 * @param {NodeTestLog} logObj 日志对象
 * @param {Array<String>} execptionList 需要处理的异常类型名称
 */
NodeExecption.nodeExecptionAction = function (node, logObj, execptionList) {
	var nextNode = null;
	//收集处理人需要其他节点指定的节点ID
	if (node.Data.mustModifyHandlerNodeIds) {
		this.mustModifyHandlerNodeIds += node.Data.mustModifyHandlerNodeIds + ";";
	}
	for (e in execptionList) {
		nextNode = NodeExecption[execptionList[e]](node, logObj);
		if (nextNode == null) {
			break;
		}
	}
	return nextNode;
}
/**
 * 节点流出线异常处理
 * @param {node} node 节点对象
 * @param {NodeTestLog} logObj 日志对象
 */
NodeExecption.nodeLineOutIsNullExecption = function (node, logObj) {
	var nextNode = null;//下一个节点
	if (node.LineOut.length > 0) {
		var lineOutSort = node;
		function getSortData(){
			var o1=[],o2=[];
			if(lineOutSort!=null && lineOutSort.LineOut.length>0 && lineOutSort.LineOut[0].EndNode && lineOutSort.LineOut[0].EndNode.Data){
				for(var i=0;i<lineOutSort.LineOut.length;i++){
					var nn = lineOutSort.LineOut[i].EndNode.Data.name+"["+lineOutSort.LineOut[i].EndNode.Data.id+"]";
					var o = {nextNodeId:lineOutSort.LineOut[i].EndNode.Data.id ||"",nextNodeName:nn, name : lineOutSort.LineOut[i].Data["name"]||"",
							condition:lineOutSort.LineOut[i].Data["condition"]||"",disCondition:lineOutSort.LineOut[i].Data["disCondition"]||"",
							priority:lineOutSort.LineOut[i].Data["priority"]||""
						};
						//补多语言
						//_appendLangsValue(o,LineOut[i].Data);
					if(o["priority"]==""){
						o2[o2.length] = o;
					}else{
						o1[o["priority"]] = o;
					}
				}
			}
			o1 = o1.concat(o2);
			return o1;
		}
		
		if(node.Data.XMLNODENAME == "splitNode" || node.Type == "splitNode" || node.Data.XMLNODENAME == "autoBranchNode" || node.Type == "autoBranchNode"){
			lineOutSort = getSortData();
			for (l in node.LineOut) {
				if (node.LineOut[l].EndNode.Data.id == lineOutSort[0].nextNodeId) {
					//找到公式计算结果匹配的节点并返回
					nextNode = node.LineOut[l].EndNode;
					break;
				}
			}
		}else{
			nextNode = node.LineOut[0].EndNode;
		}
	}
	else {
		logObj.approvalStatus = 0;
		logObj.logMessage = LogUitlLang.message_a;
	}
	return nextNode;
}
/**
 * 人工决策流向检测异常
 * @param {Node} node 节点对象
 * @param {NodeTestLog} logObj 日志对象
 */
NodeExecption.manualBranchExecption = function (node, logObj) {
	var nextNode = null;
	var nextNodeId = $("#" + node.Data.id + " option:selected").val();//获取流程实例中用户设定的默认流向节点
	if (nextNodeId != "" && nextNodeId != undefined) {
		nextNode = this.getEndNodeByNodeId(node, nextNodeId);
	}
	else if (node.Data.defaultBranch != undefined && node.Data.defaultBranch!="") {
		//判断决策节点是否有默认流向
		var defaultLineId = node.Data.defaultBranch;
		nextNode = this.getEndNodeByLineId(node, defaultLineId);
	}
	else {
		logObj.approvalStatus = 0;
		logObj.logMessage = LogUitlLang.message_b;
	}
	return nextNode;
}
/**
 * 节点处理人异常处理
 * @param {node} node 节点对象
 * @param {NodeTestLog} logObj 日志对象
 */
NodeExecption.nodeHandlerExecption = function (node, logObj) {
	var nextNode = null;
	//如果handlerIds为空可能出现了该节点处理人需要由其他节点来指定，所以直接跳过节点处理人解析的内容
	if (this.mustModifyHandlerNodeIds.indexOf(node.Data.id)>=0) {
		nextNode = node.LineOut[0].EndNode;
		logObj.handlerNames = LogUitlLang.message_c;
		return nextNode;
	}
	var vJsonArray = this.parseOrgAttributeInfo(node, logObj);
	if (vJsonArray != null && vJsonArray.status == undefined) {
		//#58791 处理节点的“处理人为空时自动跳过”属性
		logObj.approvalStatus = 0;
		logObj.logMessage = vJsonArray[0].message;
		if (vJsonArray[0].type == "ok") {
			if(vJsonArray[0].data&&vJsonArray[0].data.org!=""){
				logObj.handlerNames = vJsonArray[0].data.org;
				nextNode = node.LineOut[0].EndNode;
				logObj.approvalStatus = 1;
			}		
		}
		else if(vJsonArray[0].type == "warn"){
			if(vJsonArray[0].data){
				if(node.Data.ignoreOnHandlerEmpty=="true"){
					logObj.handlerNames = vJsonArray[0].data.org;
					nextNode = node.LineOut[0].EndNode;
					logObj.approvalStatus = 1;
				}
				else{
					logObj.handlerNames = vJsonArray[0].data.org;
					nextNode = node.LineOut[0].EndNode;
					logObj.approvalStatus = 2;
				}
			}			
		}
	}
	else {
		logObj.approvalStatus = 0;
		logObj.logMessage = LogUitlLang.message_i;
	}
	return nextNode;
}
/**
 * 条件分支结果计算
 * @param {Node} node 节点对象 
 */
NodeExecption.branchFormulaCalculation = function (node, logObj) {
	var vfiledsJSON = {};
	//请求参数数组
	var paramArray = new Array();
	paramArray.push("RequestType=formula");
	var vFormula = "";
	//获取所有分支的公式内容
	for (l in node.LineOut) {
		if (vFormula == "") {
			vFormula += node.LineOut[l].Data.condition + "#node;" + node.LineOut[l].EndNode.Data.id;
		}
		else {
			vFormula += "#split;" + node.LineOut[l].Data.condition + "#node;" + node.LineOut[l].EndNode.Data.id;
		}
	}
	for (f in vFieldList) {
		if (vFormula.indexOf(vFieldList[f].name) > 0) {
			//将公式中用到的值放入到对象中
			this.setFormulaValue(vFieldList[f], paramArray, vfiledsJSON, logObj);
		}
	}
	paramArray.push("formula=" + encodeURIComponent(vFormula));
	paramArray.push("modelName=" + encodeURIComponent(flowChartObject.ModelName));
	paramArray.push("extendFilePath=" + encodeURIComponent(vExtendFilePath))
	var result = RequestUtil.postRequestServers(paramArray.join("&"));
	return result;
}
/**
 * 条件分支节点公式解析异常
 * @param {Node} node 节点对象
 * @param {NodeTestLog} logObj 日志对象
 */
NodeExecption.autoBranchFormulaExecption = function (node, logObj) {
	var nextNode = null;
	var lineOutSort = node;
	function getSortData(){
		var o1=[],o2=[];
		if(lineOutSort!=null && lineOutSort.LineOut.length>0 && lineOutSort.LineOut[0].EndNode && lineOutSort.LineOut[0].EndNode.Data){
			for(var i=0;i<lineOutSort.LineOut.length;i++){
				var nn = lineOutSort.LineOut[i].EndNode.Data.name+"["+lineOutSort.LineOut[i].EndNode.Data.id+"]";
				var o = {nextNodeId:lineOutSort.LineOut[i].EndNode.Data.id ||"", nextNodeName:nn, name : lineOutSort.LineOut[i].Data["name"]||"",
						condition:lineOutSort.LineOut[i].Data["condition"]||"",disCondition:lineOutSort.LineOut[i].Data["disCondition"]||"",
						priority:lineOutSort.LineOut[i].Data["priority"]||""
					};
					//补多语言
					//_appendLangsValue(o,LineOut[i].Data);
				if(o["priority"]==""){
					o2[o2.length] = o;
				}else{
					o1[o["priority"]] = o;
				}
			}
		}
		o1 = o1.concat(o2);
		return o1;
	}
	if(node.Data.XMLNODENAME == "splitNode" || node.Type == "splitNode" || node.Data.XMLNODENAME == "autoBranchNode" || node.Type == "autoBranchNode"){
		lineOutSort = getSortData();
	}
	var vJsonArray = this.branchFormulaCalculation(node, logObj);
	if (vJsonArray != null && vJsonArray.status == undefined) {
		if (vJsonArray[0].type == "ok") {
			//默认取多个结果中的第一个
			var endNodeStr = vJsonArray[0].endNode.split(",");
			var vEndNodeId = vJsonArray[0].endNode.split(",")[0];
			for (s in lineOutSort) {
				for(e in endNodeStr){
					if (lineOutSort[lineOutSort.length-s-1].nextNodeId == endNodeStr[e]) {
						vEndNodeId = endNodeStr[e];
						break;
					}
				}
			}
			
			for (l in node.LineOut) {
				if (node.LineOut[l].EndNode.Data.id == vEndNodeId) {
					//找到公式计算结果匹配的节点并返回
					nextNode = node.LineOut[l].EndNode;
					break;
				}
			}
		}
		else {
			logObj.approvalStatus = 0;
			logObj.logMessage += "，" + vJsonArray[0].message;
		}
	}
	else {
		logObj.approvalStatus = 0;
		logObj.logMessage += "，" + LogUitlLang.message_e;
	}
	return nextNode;
}
/**
 * 节点处理人解析
 * @param {Node} node 节点对象
 */
NodeExecption.parseOrgAttributeInfo = function (node, logObj) {
	var result = null;
	var vfiledsJSON = {};
	var paramArray = new Array();
	paramArray.push("RequestType=parseOrgAttributeInfo");
	for (f in vFieldList) {
		if (node.Data.handlerIds && node.Data.handlerIds.indexOf(vFieldList[f].name) > 0) {
			//将公式中用到的值放入到对象中detail_attr_name_fd_3554a52fb48a38
			this.setFormulaValue(vFieldList[f], paramArray, vfiledsJSON, logObj);
		}
		var businessAuthInfo=node.Data.businessAuthInfo;
		if( businessAuthInfo!=null || businessAuthInfo!= undefined ){
	        var businessAuthInfoJson= JSON.parse(businessAuthInfo);
	        var businessFormId = businessAuthInfoJson.businessFormId;
            var businessRuleId = businessAuthInfoJson.businessRuleId;
            var businessAuthId = businessAuthInfoJson.businessAuthId;
            if (businessFormId && businessFormId.indexOf(vFieldList[f].name) > -1) {
			    //将公式中用到的值放入到对象中detail_attr_name_fd_3554a52fb48a38
			    this.setFormulaValue(vFieldList[f], paramArray, vfiledsJSON, logObj);
            }
            if (businessRuleId && businessRuleId.indexOf(vFieldList[f].name) > -1) {
			    //将公式中用到的值放入到对象中detail_attr_name_fd_3554a52fb48a38
			    this.setFormulaValue(vFieldList[f], paramArray, vfiledsJSON, logObj);
            }
            if (businessAuthId && businessAuthId.indexOf(vFieldList[f].name) > -1) {
			    //将公式中用到的值放入到对象中detail_attr_name_fd_3554a52fb48a38
			    this.setFormulaValue(vFieldList[f], paramArray, vfiledsJSON, logObj);
            }
		}
    
		
	}
	
	paramArray.push("businessAuthInfo=" + encodeURIComponent(node.Data.businessAuthInfo));
	paramArray.push("formula=" + encodeURIComponent(node.Data.handlerIds));
	paramArray.push("modelName=" + encodeURIComponent(flowChartObject.ModelName));
	paramArray.push("extendFilePath=" + encodeURIComponent(vExtendFilePath));
	paramArray.push("handlerSelectType=" + encodeURIComponent(node.Data.handlerSelectType));//节点处理人类型
	paramArray.push("handlerIds=" + encodeURIComponent(node.Data.handlerIds));//节点处理人明细（人员ID或岗位ID、公式等）
	//起草人身份
	var processFdIdentity = $("#sFdHandlerRoleInfoIds option:selected").val();
	paramArray.push("processFdIdentity=" + encodeURIComponent(processFdIdentity));
	var vJsonArray = RequestUtil.postRequestServers(paramArray.join("&"));
	if (vJsonArray != null && vJsonArray != undefined) {
		result = vJsonArray;
	}
	return result;
}
/**
 * 地址本控件值获取
 * @param {Field} field 
 * @param {Array} arr 
 * @param {JSON} filedsJSON 
 * @param {NodeTestLog} log 日志对象
 */
NodeExecption.detailAttrValue = function (field, arr, filedsJSON) {
	var personArray = new Array();
	personArray.push(document.getElementById("address_value_" + field.name).value);
	personArray.push(document.getElementById("address_name_" + field.name).value);
	filedsJSON[field.name] = personArray;
	arr.push(encodeURIComponent(field.name) + "=" + encodeURIComponent(filedsJSON[field.name]));
}
/**
 * 公式表达式组装方法
 * @param {Field} field 表单字段对象
 * @param {Array} arr 数字对象
 * @param {JSON} filedsJSON Json对象
 * @param {NodeTestLog} log 日志对象
 */
NodeExecption.setFormulaValue = function (field, arr, filedsJSON, log) {
	if (field.type.indexOf("com.landray.kmss.sys.organization.model") >= 0) {//.SysOrgPerson
		this.detailAttrValue(field, arr, filedsJSON);
	}
	else {
		filedsJSON[field.name] = document.getElementById(field.name).value;
		arr.push(encodeURIComponent(field.name) + "=" + encodeURIComponent(filedsJSON[field.name]));
		//将值放入日志中
		log.logMessage = LogUitlLang.message_f + "：" + document.getElementById(vFieldList[f].name).value;
	}
}
/**
 * 在节点对象中，通过节点ID返回下一个节点的节点对象
 * @param {Node} node 节点对象
 * @param {string} nodeId 节点Id
 */
NodeExecption.getEndNodeByNodeId = function (node, nodeId) {
	var result = null;
	for (l in node.LineOut) {
		if (node.LineOut[l].EndNode.Data.id == nodeId) {
			result = node.LineOut[l].EndNode;
			break;
		}
	}
	return result;
}
/**
 * 在节点对象中，通过流出线ID返回下一个节点的节点对象
 * @param {Node} node 节点对象
 * @param {string} outLineId 流出线id
 */
NodeExecption.getEndNodeByLineId = function (node, outLineId) {
	var result = null;
	for (l in node.LineOut) {
		if (node.LineOut[l].Data.id == outLineId) {
			result = node.LineOut[l].EndNode;
			break;
		}
	}
	return result;
}