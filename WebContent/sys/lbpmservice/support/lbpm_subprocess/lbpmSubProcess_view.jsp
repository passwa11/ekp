<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page errorPage="/resource/jsp/jsperror.jsp" %>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<script type="text/javascript">
Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
Com_IncludeFile("docutil.js|optbar.js|data.js");
</script>
<title>${mainModel.docSubject}(${mainModel.docCreator.fdName})</title>
<style>
h1 {
	font-family : Arial, "宋体";
	font-size : 16px;
	text-align: center;
	padding: 0;
	margin: 5px;
}
</style>
<script type="text/javascript">
//支持从主流程文档中打开子流程图后，在子流程图中鼠标悬停显示节点信息时计算节点处理人，rdm单号“#46295”  2017-11-22 王祥
var ModelId="${lbpmProcessMainForm.sysWfBusinessForm.fdModelId}";
var ModelName="${lbpmProcessMainForm.sysWfBusinessForm.fdModelName}";

var LbpmNextHandler = new Object();
//构建处理人信息loading效果
LbpmNextHandler.buildNextNodeHandlerLoading=function() {
	return '<div><img src="' + Com_Parameter.ContextPath + 'resource/style/common/images/loading.gif"/><kmss:message key="sys-lbpmservice:WorkFlow.Loading.Msg"/></div>';
}
//解析节点处理人详细信息（组织架构配置）
LbpmNextHandler.parseNextNodeHandler=function(ids, analysis4View, distinct, action) {
	if (ids == '' || ids == null) {
		return [{name: '<kmss:message bundle="sys-lbpmservice" key="lbpmNode.nodeHandler.orgEmpty"/>'}];
	}
	ids = encodeURIComponent(ids);
	var other = "&modelId=" + ModelId;
	var rolesSelectObj = document.getElementsByName('rolesSelectObj');
	if (rolesSelectObj != null && rolesSelectObj.length > 0) {
		other += "&drafterId=" + rolesSelectObj[0].value;
	}
	var url = "lbpmHandlerParseService&handlerIds=" + ids + other+"&analysis4View="+analysis4View;
	if(distinct) {
		url += "&distinct=true";
	}
	var data = new KMSSData(); 
	if(action) {
		data.SendToBean(url, action);
	} else {
		return data.AddBeanData(url).GetHashMapArray();
	}
}
//解析节点处理人详细信息（公式配置）
LbpmNextHandler.formulaNextNodeHandler=function(formula, analysis4View, distinct, action) {
	if (formula == '' || formula == null) {
		return [{name: '('+'<kmss:message bundle="sys-lbpmservice" key="lbpmNode.nodeHandler.orgEmpty"/>'+')'}];
	}
	formula = encodeURIComponent(formula);
	var other = "&modelId=" + ModelId + "&modelName=" + ModelName;
	var rolesSelectObj = document.getElementsByName('rolesSelectObj');
	if (rolesSelectObj != null && rolesSelectObj.length > 0) {
		other += "&drafterId=" + rolesSelectObj[0].value;
	}
	var url = "lbpmHandlerParseService&formula=" + formula + other+"&analysis4View="+analysis4View;
	if(distinct) {
		url += "&distinct=true";
	}
	var data = new KMSSData();
	if(action) {
		data.SendToBean(url, action);
	} else {
		return data.AddBeanData(url).GetHashMapArray();
	}
}
//解析节点处理人详细信息（矩阵组织配置）
LbpmNextHandler.matrixNextNodeHandler=function(matrix, analysis4View, distinct, action) {
	if (matrix == '' || matrix == null) {
		return [{name: '('+'<kmss:message bundle="sys-lbpmservice" key="lbpmNode.nodeHandler.orgEmpty"/>'+')'}];
	}
	matrix = encodeURIComponent(matrix);
	var other = "&modelId=" + ModelId + "&modelName=" + ModelName;
	var rolesSelectObj = document.getElementsByName('rolesSelectObj');
	if (rolesSelectObj != null && rolesSelectObj.length > 0) {
		other += "&drafterId=" + rolesSelectObj[0].value;
	}
	var url = "lbpmHandlerParseService&matrix=" + matrix + other+"&analysis4View="+analysis4View;
	if(distinct) {
		url += "&distinct=true";
	}
	var data = new KMSSData();
	if(action) {
		data.SendToBean(url, action);
	} else {
		return data.AddBeanData(url).GetHashMapArray();
	}
}
//解析节点处理人详细信息（规则配置）
LbpmNextHandler.ruleNextNodeHandler=function(nodeId, rule, analysis4View, distinct, action) {
	if (rule == '' || rule == null) {
		return [{name: '('+'<kmss:message bundle="sys-lbpmservice" key="lbpmNode.nodeHandler.orgEmpty"/>'+')'}];
	}
	rule = encodeURIComponent(rule);
	var other = "&modelId=" + ModelId + "&modelName=" + ModelName;
	var rolesSelectObj = document.getElementsByName('rolesSelectObj');
	if (rolesSelectObj != null && rolesSelectObj.length > 0) {
		other += "&drafterId=" + rolesSelectObj[0].value;
	}
	var url = "lbpmHandlerParseService&nowNodeId="+nodeId+"&rule=" + rule + other+"&analysis4View="+analysis4View;
	if(distinct) {
		url += "&distinct=true";
	}
	var data = new KMSSData();
	if(action) {
		data.SendToBean(url, action);
	} else {
		return data.AddBeanData(url).GetHashMapArray();
	}
}
//构建处理人信息详细信息
LbpmNextHandler.buildNextNodeHandlerView=function(type, data) {
	var html = '';
	var foreache = function(name,str) {
		html += name + ':<div style="text-align: center;">'; //
		if (data.length < 1) {
			return (html += '<kmss:message bundle="sys-lbpmservice" key="lbpmNode.nodeHandler.parseError"/>');
		}
		for (var i = 0; i < data.length; i ++) {
			if (i > 0) html += str;
			html += data[i].name;
		}
	};

	if (type == "0") { // 串行
		foreache('('+'<kmss:message bundle="sys-lbpmservice" key="lbpmNode.nodeHandler.processtypeSerial"/>'+')', '<br/>&darr;<br/>');
	} else if (type == "2") { // 会审/会签
		foreache('('+'<kmss:message bundle="sys-lbpmservice" key="lbpmNode.nodeHandler.processtypeAll"/>'+')', '<br/>+<br/>');
	} else if (type == "1") { // 并行
		foreache('('+'<kmss:message bundle="sys-lbpmservice" key="lbpmNode.nodeHandler.processtypeSingle"/>'+')', '/');
	} else {
		foreache('', ';');
		html = html.replace(/\//g, ';');
	}
	html += '</div>';
	return html;
}

//构建流程图处理人信息详细信息框（loading时）
LbpmNextHandler.buildNextNodeHandlerNodeRefreshInfo=function(name, processType, value, isFormulaType) {
	var html = "<div>·"+'<kmss:message bundle="sys-lbpmservice" key="lbpmNode.nodeHandler.nodeName"/>'+":" + name + '</div>';
	html += "<div id='nodehandler_box'>·"+'<kmss:message bundle="sys-lbpmservice" key="lbpmNode.nodeHandler.nodeHandler"/>' + LbpmNextHandler.buildNextNodeHandlerLoading() + '</div>';

	if (isFormulaType) {
		html += "<div>·"+'<kmss:message bundle="sys-lbpmservice" key="lbpmNode.nodeHandler.hint"/>'+"</div>";
	}
	return html;
}
//构建流程图处理人信息详细信息（详细内容）
LbpmNextHandler.buildNextNodeHandlerNodeShowDetail=function(doc, NodeData) {
	var box = doc.getElementById('nodehandler_box');
	if (box == null || NodeData == null) {
		return;
	}
	var html = "·"+'<kmss:message bundle="sys-lbpmservice" key="lbpmNode.nodeHandler.nodeHandler"/>';
	var data = [];
	if (NodeData.handlerSelectType=="formula") {
		data = LbpmNextHandler.formulaNextNodeHandler(NodeData.handlerIds,false,checkNodeType(NodeData));
	} else if (NodeData.handlerSelectType=="matrix") {
		data = LbpmNextHandler.matrixNextNodeHandler(NodeData.handlerIds,false,checkNodeType(NodeData));
	}else if (NodeData.handlerSelectType=="rule") {
		data = LbpmNextHandler.ruleNextNodeHandler(NodeData.handlerIds,false,checkNodeType(NodeData));
	} else {
		data = LbpmNextHandler.parseNextNodeHandler(NodeData.handlerIds,false,checkNodeType(NodeData));
	}
	html += LbpmNextHandler.buildNextNodeHandlerView(NodeData.processType, data);
	box.innerHTML = html;
}
/**
 * 判断是否是抄送节点
 */
function checkNodeType(NodeData){
	if(NodeData.XMLNODENAME=="sendNode"){
		return true;
	}
	return false;
}
</script>
</head>
<body>
<input type="hidden" name="sysWfBusinessForm.fdFlowContent" value='<c:out value="${lbpmProcessMainForm.sysWfBusinessForm.fdFlowContent}" />' >
<input type="hidden" name="sysWfBusinessForm.fdTranProcessXML" value='<c:out value="${lbpmProcessMainForm.sysWfBusinessForm.fdTranProcessXML}" />' >
<h1><a href="${mainModelUrl}">${mainModel.docSubject}(${mainModel.docCreator.fdName})</a></h1>
<iframe width="100%" height="100%" scrolling="no" 
	src="<c:url value="/sys/lbpm/flowchart/page/panel.html">
								<c:param name="edit" value="false" />
								<c:param name="extend" value="oa" />
								<c:param name="template" value="false" />
								<c:param name="contentField" value="sysWfBusinessForm.fdFlowContent" />
								<c:param name="statusField" value="sysWfBusinessForm.fdTranProcessXML" />
								<c:param name="modelName" value="${lbpmProcessMainForm.sysWfBusinessForm.fdModelName}" />
								<c:param name="modelId" value="${lbpmProcessMainForm.sysWfBusinessForm.fdModelId}" />
								<c:param name="hasParentProcess" value="${lbpmProcessMainForm.sysWfBusinessForm.internalForm.hasParentProcess}" />
								<c:param name="hasSubProcesses" value="${lbpmProcessMainForm.sysWfBusinessForm.internalForm.hasSubProcesses}" />
							</c:url>" ></iframe>

</body>
</html>