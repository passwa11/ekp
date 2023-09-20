/*
* @Author: liwenchang
* @Date:   2019-02-26 22:04:29
* @Last Modified by:   liwenchang
* @Last Modified time: 2019-02-26 08:58:14
*/
(function(window){
	var win = window;
	var document = win.document;
	var STAGEDIAGRAM_CONTROL_SELECTOR = "[fd_type='stageDiagram']";
	var LBPM_DATA_INIT_EVENT = "lbpm-init-complete";
	var STAGE_DIAGRAM_DATA_BEAN = "stageDiagramDataBean";
	var NODEID_SEPARATOR = ";";
	var lbpmInfo = [];
	var NODE_STATUS = {
		RUNNING : "2",
		PASS : "3"
	};
	//所有阶段图控件对象,key是id,value是控件对象
	var xformStageDiagramControl = [];
	$(document).ready(function(){
		//获取明细表外的阶段图控件对象
		var stageDiagrams = $(document).find(STAGEDIAGRAM_CONTROL_SELECTOR);
		buildControl(stageDiagrams);
		getLbpmData();
				
	});
	$(document).on(LBPM_DATA_INIT_EVENT,function(event,source){
		for (var i = 0; i < xformStageDiagramControl.length; i++){
			xformStageDiagramControl[i].draw();
		}
	});
	
	$(document).bind("slideSpread", function(){
		for (var i = 0; i < xformStageDiagramControl.length; i++){
			xformStageDiagramControl[i].reDraw();
		}
	});

	//获取明细表内的控件
	$(document).on("table-add",function(event,source){
		tableAdd(event,source);
	});

	/**
	*阶段图控件对象
	*/
	function StageDiagramControl(dom){
		var obj = $(dom);
		//id
		this.id = obj.attr("flagid");
		//dom元素
		this.domElement = dom;
		//是否显示开始/结束节点
		this.startEndNode = $(dom).attr("startendnode") === "true";
		//是否显示审批时间
		this.approvalTime = $(dom).attr("approvalTime") === "true";
		//阶段项字符串
		this.stageStr = $(dom).attr("stage") || "[]";
		//阶段项
		this.stage = JSON.parse(this.stageStr);
		this.draw = draw;
		this.doDraw = doDrawStageDiagram;
		this.reDraw = reDraw;
		this.init = init;
		this.processPassedStage = processPassedStage;
		this.findCanApplyCurrentStage = findCanApplyCurrentStage;
		this.findPrveStageByCurrentStage = findPrveStageByCurrentStage;
		this.findAllStage = findAllStage;
		this.findPassedStage = findPassedStage;
		this.isLastStage = isLastStage;
		this.addPassedCss = addPassedCss;
		this.addPassTime = addPassTime;
		this.processHandler = processHandler;
	}
	
	function reDraw() {
		//先清空
		$(this.domElement).empty();
		var self = this;
		setTimeout(function(){
			self.doDraw();
			self.processPassedStage();
			self.processHandler();
		},500);
	}
	
	function draw(){
		this.init();
		var self = this;
		var isDo = false;
		//每隔200毫秒查询一次，只要父级（一般是表格）的宽度>0，则进行绘画，目的是为了能正确获取父级的宽度
		//不用延时的原因，有些页面表格初始化慢，具体时间无法确定
		var num = setInterval(function(){
			var width = parseInt($(self.domElement).parent().width());
			if(width <= 0 || isDo)
				return;
			isDo = true;
			clearInterval(num);
			self.doDraw();
			self.processPassedStage();
			self.processHandler();
		},200);
	}
	
	/**
	 * 发送请求获取后台数据
	 * @returns
	 */
	function getLbpmData(){
		var info = {};
		info.processId = _xformMainModelId;
		var data = new KMSSData();
		data.UseCache = false;
		data.AddHashMap(info);
		var self = this;
		//回调函数
		var action = function (rtnVal){
			var data = rtnVal.GetHashMapArray();
			lbpmInfo = data;//兼容模板预览，为空的时候不再跳过
			$(document).trigger(LBPM_DATA_INIT_EVENT);
		}
		data.SendToBean(STAGE_DIAGRAM_DATA_BEAN, action);
	}
	
	/**
	 * 获取流程当前节点,判断正在运行阶段,并添加containCurrentNodeId属性
	 * 当流程结束时,nowProcessorInfoObj为空数组,导致所有的节点已经通过,但是stage对象并没有添加containCurrentNodeId属性
	 * 所以,在processPassedStage这个方法内再一次判断所有阶段是否已经通过
	 * @returns
	 */
	function init(){
		var nowProcessorInfoObj = getAvailableRunningNodes(lbpmInfo);
		for (var i = 0; i < nowProcessorInfoObj.length; i++){
			var currentNodeId = nowProcessorInfoObj[i].fdNodeId;
			for (var j = 0; j < this.stage.length; j++){
				var stageNodeIds = this.stage[j].stageNodeId.split(NODEID_SEPARATOR);
				if (isContainCurrentNodeId(stageNodeIds,currentNodeId)){
					this.stage[j].containCurrentNodeId = true;
				}
			}
		}
	}
	
	function getAvailableRunningNodes(lbpmInfo){
		lbpmInfo = lbpmInfo || [];
		var runningNode = [];
		for (var i = 0; i < lbpmInfo.length; i++){
			var nodeObj = lbpmInfo[i];
			if (nodeObj.fdStatus === NODE_STATUS.RUNNING){
				runningNode.push(nodeObj);
			}
		}
		return runningNode;
	}
	
	function getPassNodes(lbpmInfo){
		lbpmInfo = lbpmInfo || [];
		var passNodes = [];
		for (var i = 0; i < lbpmInfo.length; i++){
			var nodeObj = lbpmInfo[i];
			if (nodeObj.fdStatus === NODE_STATUS.PASS){
				passNodes.push(nodeObj);
			}
		}
		return passNodes;
	}
	
	
	function processHandler(){
		//获取正在运行的节点
		var currentStages = $("li[currentnode]",this.domElement);
		for (var i = 0; i < currentStages.length; i++){
			var currentHanlderId = [];
			var currentHanlderName = [];
			var historyHandlerId = [];
			var historyHandlerName = [];
			var nodeIds = $(currentStages[i]).attr("nodeids");
			nodeIds = nodeIds.split(";");
			for (var j = 0; j < nodeIds.length; j++){
				var nodeInfo = getNodeInfoById(nodeIds[j]);
				if (nodeInfo && nodeInfo.fdCurrentHandlerId){
					currentHanlderId = currentHanlderId.concat(nodeInfo.fdCurrentHandlerId.split(";"));
					currentHanlderName = currentHanlderName.concat(nodeInfo.fdCurrentHandlerName.split(";"));
				}
				if (nodeInfo && nodeInfo.fdHistoryHandlerId){
					historyHandlerId = historyHandlerId.concat(nodeInfo.fdHistoryHandlerId.split(";"));
					historyHandlerName = historyHandlerName.concat(nodeInfo.fdHistoryHandlerName.split(";"));
				}
			}
			$(currentStages[i]).attr("currentHandlerId",currentHanlderId.join(";"));
			$(currentStages[i]).attr("currentHandlerName",currentHanlderName.join(";"));
			$(currentStages[i]).attr("historyHandlerId",historyHandlerId.join(";"));
			$(currentStages[i]).attr("historyHandlerName",historyHandlerName.join(";"));
			var stageImg = $(currentStages[i]).find("[class='spot spot-current']")
			stageImg.on("mouseover",currentStageOnMouseover);
			stageImg.on("mouseout",currentStageOnMouseOut);
		}
	}
	
	function createHandlerDom(){
		var html = [];
		html.push("<div name='handlerWrap' class='lui_flow_rotundity_tooltip' style='position:absolute;'>");
		html.push("<div class='lui_flow_rotundity_handlerTitle'>" + XformObject_Lang.stage_diagram_handler + "</div>");
		html.push("<div class='lui_flow_rotundity_historyHandlerTitle'>" + XformObject_Lang.stage_diagram_historyHandler + "</div>");
		html.push("<div class='lui_flow_rotundity_historyHandler' name='historyHandler'></div>");
		html.push("<div class='lui_flow_rotundity_currentHandlerTitle'>" + XformObject_Lang.stage_diagram_currentHandler + "</div>");
		html.push("<div class='lui_flow_rotundity_currentHandler' name='currentHandler'></div>");
		html.push("</div>");
		return html.join("");
	}
	
	function currentStageOnMouseover(event){
		event = event || window.event;
		var srcElement = event.toElement || event.relatedTarget;
		var liObj = $(srcElement).closest("li");
		var handlerObj = liObj.find("[name='handlerWrap']");
		handlerObj.css("z-index",100);
		handlerObj.show();
		var historyHandlerObj = handlerObj.find("[name='historyHandler']");
		var currentHandlerObj = handlerObj.find("[name='currentHandler']");
		var currentHandlerNames = liObj.attr("currentHandlerName");
		var historyHandlerNames = liObj.attr("historyHandlerName");
		//已提交
		if (historyHandlerNames){
			historyHandlerNames = historyHandlerNames.split(";");
			historyHandlerNames = historyHandlerNames.join(" ");
		}else{
			historyHandlerNames = "";
		}
		historyHandlerObj.text(historyHandlerNames);
		//未提交
		if (currentHandlerNames){
			currentHandlerNames = currentHandlerNames.split(";");
			currentHandlerNames = currentHandlerNames.join(" ");
			
		}else{
			currentHandlerNames = "";
		}
		currentHandlerObj.text(currentHandlerNames);
		contentElementPosition(srcElement,handlerObj);
	}
	
	function currentStageOnMouseOut(event){
		event = event || window.event;
		var srcElement = event.toElement || event.relatedTarget;
		var liObj = $(srcElement).closest("li");
		var handlerObj = liObj.find("[name='handlerWrap']");
		handlerObj.hide();
	}
	
	/**
	 * 提示元素定位
	 * @returns
	 */
	function contentElementPosition(srcElement,handlerObj){
		//图标x轴坐标
		var x = $(srcElement).offset().left;
		//图标y轴坐标
		var y = $(srcElement).offset().top;
		//scrollWidth 对象的实际内容的宽度
		var scrollWidth = document.body.scrollWidth;
		//scrollHeight 对象的实际内容的高度
		var scrollHeight = document.body.scrollHeight;
		//提示框的宽度
		var divWidth = handlerObj.width();
		//提示框的高度
		var paddingWidth = 20;
		var divHeight = handlerObj.height() + paddingWidth;
		offset = {};
		offset.left = x - divWidth/2 + 'px';
		//这几个数字怎么来滴，我也不知道
		if(y < divHeight){
			handlerObj.css("bottom","-95px");
		}
	}
	
	/**
	 * 根据节点获取节点信息
	 * @param nodeId
	 * @returns
	 */
	function getNodeInfoById(nodeId){
		for (var j = 0; j < lbpmInfo.length; j++){
			var nodeInfo = lbpmInfo[j];
			if (nodeId == nodeInfo.fdNodeId){
				return nodeInfo;
			}
		}
	}
	
	/**
	 * 判断当前流程节点是否包含在阶段内
	 * @param stageNodeIds 阶段所包含的节点
	 * @param currentNodeId 当前正在运行的流程节点
	 * @returns
	 */
	function isContainCurrentNodeId(stageNodeIds,currentNodeId){
		stageNodeIds = stageNodeIds || [];
		for (var i = 0; i < stageNodeIds.length; i++){
			var id = stageNodeIds[i];
			if (id === currentNodeId){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 处理已经通过的阶段,设置通过的样式,设置通过时间
	 * @returns
	 */
	function processPassedStage(){
		var passedStage = this.findPassedStage(this.findPrveStageByCurrentStage());
		this.addPassedCss(passedStage);
		this.addPassTime(passedStage);
		this.passedStage = passedStage;
		if (isAllPass(this.stage)){
			var canApplyStage = this.findPassedStage(this.findAllStage());
			this.passedStage = canApplyStage;
			this.addPassedCss(canApplyStage);
			this.addPassTime(canApplyStage);
		}
	}
	
	function findAllStage(){
		 return $("li",this.domElement);
	}
	
	
	/**
	 * 添加通过时间
	 * @param passedStage 通过的阶段
	 * @param lbpmData 通过时间
	 * @returns
	 */
	function addPassTime(passedStage){
		if(!this.approvalTime ||
				lbpmInfo.length == 0 || passedStage.length == 0){
			return;
		}
		passedStage = passedStage || [];
		$.each(passedStage,function(index,obj){
			var nodeIds =  $(obj).attr("nodeIds").split(NODEID_SEPARATOR);
			var date = "";
			var subhead = $(obj).find("p[class='subhead']");
			var maxDate = getPassTimeByNodeId(nodeIds[0]) || "";
			var minDate = maxDate;
			for (var i = 1; i < nodeIds.length; i++){
				var nextDate = getPassTimeByNodeId(nodeIds[i]) || "";
				if (nextDate.replace(/\-/g,"") > maxDate.replace(/\-/g,"")){
					maxDate = nextDate;
				}
				if (nextDate.replace(/\-/g,"") < minDate.replace(/\-/g,"")){
					minDate = nextDate;
				}
				
			}
			if (minDate == maxDate){
				date = minDate;
			}else{
				date = maxDate;
			}
			subhead.text(date);
		});
	}
	
	function getPassTimeByNodeId(nodeId){
		for (var i = 0; i < lbpmInfo.length; i++){
			var nodeObj = lbpmInfo[i];
			if (nodeObj.fdNodeId == nodeId){
				return nodeObj.fdFinishDate;
			}
		}
	}
	
	function findPrveStageByCurrentStage(){
		var currentStage = this.findCanApplyCurrentStage();
		
		var prevStage = [];
		if (!currentStage){
			return prevStage;
		}
		var currentIndex = $(currentStage).attr("index");
		var allStage = this.findAllStage();
		allStage.each(function(i,obj){
			var index = $(obj).attr("index");
			if (index && parseInt(index) < parseInt(currentIndex)){
				prevStage.push(obj);
			}
		});
		return prevStage;
	}
	
	/**
	 * 获取可以使用的当前阶段,可能存在多个正在运行的阶段,这里获取最后一个
	 * @returns
	 */
	function findCanApplyCurrentStage(){
		var currentStages = $("li[currentnode]",this.domElement);
		var canApplyStage = [];
		var canApplyCurrentStage;
		var index = 0;
		if (currentStages.length == 0){
			return ;
		}else{
			canApplyCurrentStage = currentStages[0];
			index = parseInt($(canApplyCurrentStage).attr("index") || 0);
			for(var i = 1; i < currentStages.length; i++){
				 if ($(currentStages[i]).attr("index") >= index){
					 index = parseInt($(currentStages[i]).attr("index") || 0);
					 canApplyCurrentStage = currentStages[i];
				 }
			}
		}
		
		return canApplyCurrentStage;
	}
	
	/**
	 * 判断所有的阶段是否已经通过
	 * @param stages 所有阶段
	 * @returns
	 */
	function isAllPass(stages){
		var isAllPass = true;
		if (processIsPass()){
			return true;
		}
		stages = stages || [];
		for (var i = 0; i < stages.length; i++){
			var stageNodeId = stages[i].stageNodeId.split(NODEID_SEPARATOR);
			if (!stageIsPass(stageNodeId)){
				isAllPass = false;
				break;
			}
		}
		return isAllPass;
	}
	
	function processIsPass(){
		for(var i = 0; i < lbpmInfo.length; i++){
			var info = lbpmInfo[i];
			if (info.fdStatus === "30"){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断某个阶段的所有节点是否已经通过
	 * @param stageNodeIds 某个阶段所对应的节点数组
	 * @returns
	 */
	function stageIsPass(stageNodeIds){
		stageNodeIds = stageNodeIds || [];
		var nodes = getPassNodes(lbpmInfo);
		var isPass = true;
		nodes = nodes || [];
		for (var i = 0; i < stageNodeIds.length; i++){
			if (!passNodeContain(nodes,stageNodeIds[i])){
				isPass = false;
				return isPass;
			}
		}
		return isPass;
	}
	
	function passNodeContain(nodes,stageNodeId){
		var isContain = false;
		for (var j = 0; j < nodes.length; j++){
			//2标识节点已经通过
			var passNode = nodes[j];
			if(stageNodeId === passNode["fdNodeId"]){
				isContain = true;
				return isContain;
			}
		}
		return isContain;
	}
	
	/**
	 * 设置通过样式
	 * @param passedStage
	 * @returns
	 */
	function addPassedCss(passedStage){
		passedStage = passedStage || [];
		$.each(passedStage,function(index,obj){
			$(obj).find("span[class='spot']").addClass("spot-finish");
		});
	}
	
	/**
	 * 获取已经通过的节点
	 * @param fun 
	 * @returns
	 */
	function findPassedStage(candicateStages){
		candicateStages = candicateStages || [];
		var canApplyStage = [];
		for(var j = 0; j < candicateStages.length; j++){
			if($(candicateStages[j]).attr("startNode") == "true" 
				|| $(candicateStages[j]).attr("endNode") == "true"
					|| $(candicateStages[j]).attr("currentnode") == "true"){
				continue;
			}else{
				canApplyStage.push(candicateStages[j]);
			}
		}
		return canApplyStage;
	}
	
	/**
	 * 绘制阶段图
	 */
	function doDrawStageDiagram(){
		var size = this.stage.length;
		//如果显示开始/结束节点
		if (this.startEndNode){
			size += 2;
		}
		
		var self = this;
		//当前阶段图控件所在父节点宽度
		var width = parseInt($(self.domElement).parent().width()) - 30;
		var html = [];
		var oneStageWidth = 125;
		//125为一个阶段的宽度,width/125计算出阶段图控件所在的父节点能存放的阶段个数,也就是一个ul里面包含的li个数
		var lineLiNum = parseInt(width/oneStageWidth) || 3;
		//计算需要多少行,也就是需要多少个ul
		var lineNum = Math.ceil(size/lineLiNum);
		//圆环流程图表 Starts
		html.push("<div class='lui-flow-rotundity'>");
		for (var i = 0; i < lineNum; i++){
			html.push("<ul class='lui-flow-rotundity-list'>");
			for(var j = 0; j < lineLiNum; j++){
				if ((i * lineLiNum + j) == size){
					break;
				}
				var index = i * lineLiNum + j;
				var stageName = "";
				var stageNodeId = "";
				//显示开始/结束节点
				var isStartNode = false;
				var isEndNode = false;
				if(self.startEndNode){
					//开始节点
					if (i == 0 && j == 0){
						stageName = XformObject_Lang.stage_diagram_start_node;
						isStartNode = true;
					}else if(i == lineNum - 1 && j == (size - (i * lineLiNum + 1))){//结束节点
						stageName = XformObject_Lang.stage_diagram_end_node;
						isEndNode = true;
					}else{//阶段节点
						var stageObj = self.stage[index - 1];
						stageName = stageObj.stageName;
						stageNodeId = stageObj.stageNodeId;
					}
				}else{//不显示开始/结束节点
					var stageObj = self.stage[index];
					stageName = stageObj.stageName;
					stageNodeId = stageObj.stageNodeId;
				}
				//i != lineNum -1 说明多行非最后一行,j == lineLiNum - 1说明当前行的最后一个,就是除了最后一行,其它行的最后一个阶段都要添加一个last-child样式
				if (i != lineNum - 1 && j == lineLiNum - 1){
					html.push("<li class='last-child'");
				}else{
					html.push("<li");
				}
				//判断是否是最后一个阶段
				if (self.isLastStage(i,lineLiNum,j)){
					html.push(" lastStage='true'");
				}
				html.push(" nodeIds='" + stageNodeId +  "'")
				if (isStartNode){
					html.push(" startNode='true'")
				}else if (isEndNode){
					html.push(" endNode='true'")
				}else {
					if (stageObj && stageObj.containCurrentNodeId){
						html.push(" currentNode='true'");
					}
					if (self.startEndNode){
						html.push(" index=" + (index - 1));
					}else{
						html.push(" index=" + index);
					}
				}
				
				html.push(">")
				
				html.push("<span class='spot");
				if (isStartNode){
					html.push(" spot-start");
				}else if (isEndNode){
					html.push(" spot-end");
				}else if (stageObj && stageObj.containCurrentNodeId){
					html.push(" spot-current");
				}
				
				html.push("'></span>")
				html.push("<p class='title' title='" + stageName + "'>" + stageName + "</p>");
				if (self.approvalTime){
					html.push("<p class='subhead'></p>");
				}
				if (stageObj && stageObj.containCurrentNodeId){
					html.push(createHandlerDom());
				}
				html.push("</li>")
			}
			html.push("</ul>");
		}
		
		html.push("</div>");
		//圆环流程图表 Ends
		$(self.domElement).append(html.join(""));
	}
	
	/**
	 * 判断是否是最后一个阶段
	 * @param rowIndex 当前行索引
	 * @param rowSize	每行个数
	 * @param colIndex	当前列索引
	 * @returns
	 */
	function isLastStage(rowIndex,rowSize,colIndex){
		var size = this.stage.length;
		if (this.startEndNode){
			size += 2;
		}
		var isLastStage = false;
		if (this.startEndNode && size == (rowIndex * rowSize + colIndex + 2)){
			isLastStage = true;
		}
		if (!this.startEndNode && size == (rowIndex * rowSize + colIndex + 1)){
			isLastStage = true;
		}
		return isLastStage;
	}
	
	/**
	 * 明细表行增加事件,初始化的时候也会触发此事件
	 * @param event
	 * @param source
	 * @returns
	 */
	function tableAdd(event,source){
		var stageDiagrams = $(source).find(STAGEDIAGRAM_CONTROL_SELECTOR);
		var stageControl = buildControl(stageDiagrams);
		$.each(stageControl,function(index,obj){
			obj.draw();
		});
	}
	
	function buildControl(stageDiagrams){
		var stages = [];
		if (stageDiagrams instanceof jQuery){
			for (var i = 0; i < stageDiagrams.length; i++){
				if(stageDiagrams[i] && stageDiagrams[i].nodeType){
					var control = new StageDiagramControl(stageDiagrams[i]);
					xformStageDiagramControl.push(control);
					stages.push(control);
					var isPrint = $(control.domElement).attr("isprint");
					//打印页面隐藏阶段图控件
					if (isPrint === "print"){
						$(control["domElement"]).hide();
					}
				}
			}
		}
		return stages;
	}
	
	function htmlUnEscape (s){
		if (s == null || s ==' ') return '';
		s = s.replace(/&amp;/g, "&");
		s = s.replace(/&quot;/g, "\"");
		s = s.replace(/&lt;/g, "<");
		return s.replace(/&gt;/g, ">");
	};
})(window);