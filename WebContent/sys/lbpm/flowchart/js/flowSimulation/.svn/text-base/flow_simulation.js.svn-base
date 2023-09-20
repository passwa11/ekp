/**
 * 流程仿真对象
 */
var FlowSimulation = new Object();

/**
 * 自动仿真对象
 */
FlowSimulation.automaticSimulation = AutomaticSimulation;

/**
 * 仿真日志
 */
FlowSimulation.NodeTestLogs = new Array();
/**
 * 批量仿真日志缓存
 */
FlowSimulation.BatchLog=null;

/**
 * 日志操作对象
 */
FlowSimulation.logUtil = LogUtil;
/**
 * 流程图操作对象
 */
FlowSimulation.chartUtil = ChartUtil;
/**
 * 缓存默认的日志HTML结构用于还原日志内容至初始化
 */
FlowSimulation.tempTdTestLog = "";
/**
 * 手动仿真对象
 */
FlowSimulation.manualSimulation = ManualSimulation;
/**
 * 启动流程仿真
 */
FlowSimulation.startAutomaticFlowTest = function () {
	//重置手动仿真状态
	this.manualSimulation.testFlag=0;
	
	//获取流程图中的流程对象
	var vFlowChartObject = flowChartObject;//document.getElementById("sysWfTemplateForms.reviewMainDoc.WF_IFrame").contentWindow.FlowChartObject;
	//判断是否选择了起草人身份
	if (!this.handlerRoleIsNull()) {
		$("#btnStartFlowTest").attr("disabled", "disabled");
		//判断是否启动过模拟
		if (this.automaticSimulation.testFlag == 0) {
			this.automaticSimulation.testFlag = 1;
		}
		else {
			//重置流程图
			this.resetFlowTest(vFlowChartObject);
		}
		$("#btnStartFlowTest").val(FlowSimulationLang.starting);//将按钮文字改为“程序模拟中”
		var domTdTestLog = vFlowChartObject.FlowSimulation.LogIframe.contentWindow.document.getElementById("tdTestLog");
		var vTdTestLog=$(domTdTestLog);//获取日志的jQuery对象，方便支持多浏览器下进行操作
		//缓存默认的日志结构用于日志内容初始化
		this.tempTestLogInnerHTML(domTdTestLog);
		//获取开始节点
		var vStartNode = this.getStartNodeByFlowChartObject(vFlowChartObject);
		if (vStartNode != null) {
			this.automaticSimulation.historyRoute = new Array();//重置历史路由
			var vtestResult = this.automaticSimulation.nodeSimulation(vStartNode, vFlowChartObject);//开始模拟运行
			vTdTestLog.append(this.logUtil.testLogHandler(this.NodeTestLogs));//日志填充
			
			//显示日志
			ShowLog(vFlowChartObject);

		}
		else {
			var nodeTestLog = new NodeTestLog(0, "", "", LogUitlLang.isNul, LogUitlLang.isNul, LogUitlLang.isNul, "");
			this.NodeTestLogs.push(nodeTestLog);
			vTdTestLog.append(this.logUtil.testLogHandler(this.NodeTestLogs));//日志填充
		}
		$("#btnStartFlowTest").val(FlowSimulationLang.startAutomaticFlowTest);
		$('#btnStartFlowTest').removeAttr("disabled");
	}
}
/**
 * 开始批量仿真
 */
FlowSimulation.startBatchSimulation = function () {
	//重置手动仿真状态
	this.manualSimulation.testFlag=0;
	
	//获取流程图中的流程对象
	this.BatchLog=null;
	var vFlowChartObject = flowChartObject;	
	var vCheckExample=document.getElementsByName("checkExample");
	var vCheckExample=$("[name='checkExample']:checked");
	//判断是否启动过模拟
	if (this.automaticSimulation.testFlag == 0) {
		this.automaticSimulation.testFlag = 1;
	}
	//缓存默认的日志结构用于日志内容初始化
	var domTdTestLog = vFlowChartObject.FlowSimulation.LogIframe.contentWindow.document.getElementById("tdTestLog");
	this.tempTestLogInnerHTML(domTdTestLog);
	
	var logList={};
	if(vCheckExample.length>0){	
		//初始化状态列
		$("[name='divTestType']").attr("class","divTag").attr("onclick","").html("");
		
		for(var i=0;i<vCheckExample.length;i++){
			//将实例数据绑定到实例表单中，用于仿真
			FlowExample.dataBindById(vCheckExample[i].value);
			//获取开始节点
			var vStartNode = this.getStartNodeByFlowChartObject(vFlowChartObject);
			if (vStartNode != null) {
				var vtestResult = this.automaticSimulation.nodeSimulation(vStartNode, vFlowChartObject);//开始模拟运行
				if(vtestResult=="ok"){
					$("#"+vCheckExample[i].value+"_type").html(LogUitlLang.pass).attr("class","divTagPass");
				}
				else{
					$("#"+vCheckExample[i].value+"_type").html(LogUitlLang.error).attr("class","divTagError");
				}
				$("#"+vCheckExample[i].value+"_type").attr("onclick","FlowSimulation.showLogByExampleId('"+vCheckExample[i].value+"')");
			}
			else {
				var nodeTestLog = new NodeTestLog(0, "", "", LogUitlLang.isNul, LogUitlLang.isNul, LogUitlLang.isNul, "");
				this.NodeTestLogs.push(nodeTestLog);
			}
			logList[vCheckExample[i].value]=this.NodeTestLogs;//将日志用实例ID关联并保存起来
			
			//清除缓存日志的内容
			this.NodeTestLogs = null;
			this.NodeTestLogs = new Array();
		}
		//缓存批量日志
		this.BatchLog=logList;
	}
	else{
		alert(FlowSimulationLang.message_e);
	}
	//清空实例表单页面中的所有控件的值
	$("[data-from='example']").val("");
	$("#trFdHandlerRoleInfoIds").hide();
	this.chartUtil.resetChart(vFlowChartObject);
}
/**
 * 点击列表中状态显示指定行的日志
 */
FlowSimulation.showLogByExampleId=function(id){
	var vFlowChartObject = flowChartObject;
	var domTdTestLog = vFlowChartObject.FlowSimulation.LogIframe.contentWindow.document.getElementById("tdTestLog");
	//显示日之前先初始化日志内容
	FlowSimulation.resetFlowTest(vFlowChartObject);
	var vTdTestLog=$(domTdTestLog);//获取日志的jQuery对象，方便支持多浏览器下进行操作
	vTdTestLog.append(this.logUtil.testLogHandler(this.BatchLog[id]));//日志填充	
	//显示日志
	ShowLog(vFlowChartObject);
}
/**
 * 启动手动仿真
 */
FlowSimulation.startManualSimulation = function () {
	var vFlowChartObject = flowChartObject//document.getElementById("sysWfTemplateForms.reviewMainDoc.WF_IFrame").contentWindow.FlowChartObject;
	//判断是否选择了起草人身份
	if (!this.handlerRoleIsNull()) {
		//判断是否启动过模拟
		if (this.automaticSimulation.testFlag == 0) {
			this.automaticSimulation.testFlag = 1;
		}
		else {
			//重置流程图
			this.resetFlowTest(vFlowChartObject);
		}
		if(this.manualSimulation.testFlag==0){
			this.manualSimulation.testFlag=1;
		}
		var domTdTestLog = vFlowChartObject.FlowSimulation.LogIframe.contentWindow.document.getElementById("tdTestLog");
		var vTdTestLog=$(domTdTestLog);//将Dom对象转换为jQuery对象
		//缓存默认的日志结构用于日志内容初始化
		this.tempTestLogInnerHTML(domTdTestLog);
		//获取开始节点
		var vStartNode = this.getStartNodeByFlowChartObject(vFlowChartObject);
		if (vStartNode != null) {
			this.manualSimulation.startSimulation(vStartNode, vFlowChartObject);
			var vNodeTestLog = this.NodeTestLogs[this.NodeTestLogs.length - 1];
			vTdTestLog.append(this.logUtil.getLogHtml(vNodeTestLog,this.NodeTestLogs.length));//日志填充
		}
		else {
			var nodeTestLog = new NodeTestLog(0, "", "", LogUitlLang.isNul, LogUitlLang.isNul,LogUitlLang.isNul, "");
			this.NodeTestLogs.push(nodeTestLog);
			vTdTestLog.append(this.logUtil.testLogHandler(this.NodeTestLogs));//日志填充
		}

	}
}
/**
 * 手动仿真的下一步操作
 */
FlowSimulation.nextStep = function () {
	var vFlowChartObject = flowChartObject//document.getElementById("sysWfTemplateForms.reviewMainDoc.WF_IFrame").contentWindow.FlowChartObject;
	var domTdTestLog = vFlowChartObject.FlowSimulation.LogIframe.contentWindow.document.getElementById("tdTestLog");
	var vTdTestLog=$(domTdTestLog);//Dom对象转换为jQuery对象
	if (this.automaticSimulation.testFlag == 0||this.manualSimulation.testFlag==0) {
		alert(FlowSimulationLang.message_a);
	}
	else {
		if (this.manualSimulation.isEndType == 1) {
			alert(FlowSimulationLang.message_d);
		}
		else {
			var vType = this.manualSimulation.nextStep(this.manualSimulation.currentNode, vFlowChartObject);
			var vNodeTestLog = this.NodeTestLogs[this.NodeTestLogs.length - 1];
			vTdTestLog.append(this.logUtil.getLogHtml(vNodeTestLog,this.NodeTestLogs.length));//日志填充
			if(vType=="error"){
				//显示日志
				ShowLog(vFlowChartObject);
			}
		}
	}
}
/**
 * 手动仿真的上一步操作
 */
FlowSimulation.previousStep = function () {
	var vFlowChartObject = flowChartObject//document.getElementById("sysWfTemplateForms.reviewMainDoc.WF_IFrame").contentWindow.FlowChartObject;
	if (this.automaticSimulation.testFlag == 0||this.manualSimulation.testFlag==0) {
		alert(FlowSimulationLang.message_a);
	}
	else{
		this.manualSimulation.previousStep(vFlowChartObject);
	}
	
}

/**
 * 从流程前端对象中找出开始节点
 * vFlowChartObject:流程前端对象
 */
FlowSimulation.getStartNodeByFlowChartObject = function (vFlowChartObject) {
	var result = null;
	for (n in vFlowChartObject.Nodes.all) {
		if (vFlowChartObject.Nodes.all[n].Type == "startNode") {
			//获取到节点对象中的开始节点
			result = vFlowChartObject.Nodes.all[n];
			break;
		}
	}
	return result;
}

/**
 * 缓存默认的日志结构用于日志内容初始化
 */
FlowSimulation.tempTestLogInnerHTML = function (domTdTestLog) {
	if (this.tempTdTestLog == "") {
		this.tempTdTestLog = domTdTestLog.innerHTML;//缓存默认的日志结构用于日志内容初始化
	}
}
/**
 * 初始化流程图和仿真日志
 */
FlowSimulation.resetFlowTest = function (vFlowChartObject) {
	$(vFlowChartObject.FlowSimulation.LogIframe.contentWindow.document.getElementById("divTestLog")).hide();//重置时收起日志模块
	$(vFlowChartObject.FlowSimulation.LogIframe).height(21);
	//重置日志
	var domTdTestLog =$(vFlowChartObject.FlowSimulation.LogIframe.contentWindow.document.getElementById("tdTestLog")); //document.getElementById("tdTestLog");
	domTdTestLog.html(this.tempTdTestLog);
	this.NodeTestLogs = null;
	this.NodeTestLogs = new Array();
	//重置流程图
	// var vsysWfTemplateForms = document.getElementById("sysWfTemplateForms.reviewMainDoc.WF_IFrame");
	// vsysWfTemplateForms.contentWindow.location.reload(true)
	this.chartUtil.resetChart(vFlowChartObject);
}
/**
 * 异步请求服务
 * arg：请求参数
*/
FlowSimulation.postRequestServers = function (arg) {
	var result = null;
	$.ajax({
		url: Com_Parameter.ContextPath + "sys/lbpm/engine/jsonp.jsp?s_bean=flowSimulationService",
		async: false,
		data: arg,
		type: "POST",
		dataType: 'json',
		success: function (data) {
			result = data;
		},
		error: function (er) {

		}
	});
	return result;
}
/**
 * 检测是否选择了起草人
 */
FlowSimulation.handlerRoleIsNull = function () {
	var result = false;
	var vHandlerRole = document.getElementById("detail_attr_name");
	if (vHandlerRole.value == "") {
		result = true;
		alert(FlowSimulationLang.message_c);
	}
	return result
}

/**
 * 根据sysOrgElement的FdId获取人员所有身份
 * @param {sysOrgElementFdId} sysOrgElementFdId
 */
FlowSimulation.getFdHandlerRoleInfoIds = function (sysOrgElementFdId) {
	var paramArray = new Array();
	paramArray.push("RequestType=getFdHandlerRoleInfoIds");
	paramArray.push("fdId=" + encodeURIComponent(sysOrgElementFdId));
	var vJsonArray = this.postRequestServers(paramArray.join("&"));
	return vJsonArray;
}
/**
 * 根据起草人加载起草人身份
 */
FlowSimulation.loadFdHandlerRoleInfoIds = function () {
	var vSysOrgElementFdId = document.getElementById("detail_attr_value").value;
	var vJsonArray = null;
	if (vSysOrgElementFdId != "") {
		vJsonArray = this.getFdHandlerRoleInfoIds(vSysOrgElementFdId);
	}
	else {
		//起草人为空时隐藏该项
		$("#trFdHandlerRoleInfoIds").hide();
	}
	if (vJsonArray != null) {
		if (vJsonArray[0].type == "ok") {
			$("#sFdHandlerRoleInfoIds").html("");//加载前清空历史内容
			var vFdHandlerRoleInfoIds = vJsonArray[0].handlerRoleInfoIds.split(";");
			for (i in vFdHandlerRoleInfoIds) {
				var iTemp = vFdHandlerRoleInfoIds[i].split("|");
				if (iTemp == 0) {
					$("#sFdHandlerRoleInfoIds").append("<option value='" + iTemp[0] + "' selected=\"selected\">" + iTemp[1] + "</option>");
				}
				else {
					$("#sFdHandlerRoleInfoIds").append("<option value='" + iTemp[0] + "'>" + iTemp[1] + "</option>");
				}

			}
			$("#trFdHandlerRoleInfoIds").show();//起草人身份请求成功后显示该内容
		}
	}
}
/**
 * 显示日志
 * @returns null
 */
function ShowLog(vFlowChartObject){
	$(vFlowChartObject.FlowSimulation.LogIframe).height(300);
	$(vFlowChartObject.FlowSimulation.LogIframe.contentWindow.document.getElementById("divTestLog")).show();//显示日志
	$(vFlowChartObject.FlowSimulation.LogIframe.contentWindow.document.getElementById("tableBody")).scrollTop(1000000);//直接显示到日志最底部
}