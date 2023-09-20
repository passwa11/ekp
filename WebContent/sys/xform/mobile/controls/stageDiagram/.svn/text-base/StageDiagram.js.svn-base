define([ "dojo/_base/declare",  "dojo/dom-construct", 
         "dojo/dom-prop",  "sys/xform/mobile/controls/xformUtil", 
         "dijit/registry",  "mui/form/_FormBase", 
         "dojo/query","dojo/dom", "dojo/dom-class",
         "dojo/dom-attr",  "dojo/dom-style",
         "dojo/on", "dojo/_base/lang",
         "dojo/dom-geometry",
         "mui/i18n/i18n!sys-xform-base:mui",
         "dojo/html","dojo/topic"
         ], function(declare, domConstruct, domProp, xUtil, registry,
        		 _FormBase, query,dom,domClass,domAttr,domStyle,on,lang,domGeometry,Msg,html,topic) {
	var claz = declare("sys.xform.mobile.controls.stageDiagram.StageDiagram", [ _FormBase], {
		
		buildRendering : function() {
			this.inherited(arguments);
			this.getLbpmData();
		},
		
		postCreate: function() {
			this.inherited(arguments);
            this.subscribe("/mui/navitem/_selected", lang.hitch(this,'handleItemSelected'));
		},
		
		handlerShow : false,
		
		startup:function(){
			this.inherited(arguments);
		},

        handleItemSelected : function(evt) {
            if (evt && evt.moveTo === "baseinfoView") {
                var self = this;
                this.defer(function() {
                    self.reDraw();
                }, 100);
            }
        },

        reDraw : function() {
            domConstruct.empty(this.domNode);
            this.doDraw();
            this.processPassedStage();
            this.processHandler();
			topic.publish("/mui/list/resize", this);
        },
		
		STARTNODE : Msg["mui.stageDiagram.startNode"],
		ENDNODE : Msg["mui.stageDiagram.endNode"],
		HANDLER : Msg["mui.stageDiagram.handler"],
		CURRENTHANDLER : Msg["mui.stageDiagram.currentHandler"],
		HISTORYHANDLER : Msg["mui.stageDiagram.historyHandler"],
		
		STAGE_DIAGRAM_DATA_BEAN : "stageDiagramDataBean",
		NODEID_SEPARATOR : ";",
		NODE_STATUS : {RUNNING : "2",PASS : "3"},
		
		
		/**
		 * 发送请求获取流程相关数据
		 * @returns
		 */
		getLbpmData : function(){
			var info = {};
			info.processId = _xformMainModelId;
			var data = new KMSSData();
			data.UseCache = false;
			data.AddHashMap(info);
			var self = this;
			//回调函数
			var action = function (rtnVal){
				var data = rtnVal.GetHashMapArray();
				if (data.length == 0){
					self.lbpmInfo = [];
					return;
				}else{
					self.lbpmInfo = data;
					self.draw();
				}
			}
			data.SendToBean(this.STAGE_DIAGRAM_DATA_BEAN, action);
		},
		
		draw : function(){
			this.init();
			this.doDraw();
			this.processPassedStage();
			this.processHandler();
			topic.publish("/mui/list/resize", this);
		},
		
		init : function(){
			var nowProcessorInfoObj = this.getAvailableRunningNodes();
			this.startEndNode = this.startEndNode === "true";
			this.approvalTime = this.approvalTime === "true";
			this.stage = JSON.parse(this.stage.replace(/&quot;/g, "\""));
			for (var i = 0; i < nowProcessorInfoObj.length; i++){
				var currentNodeId = nowProcessorInfoObj[i].fdNodeId;
				for (var j = 0; j < this.stage.length; j++){
					var stageNodeIds = this.stage[j].stageNodeId.split(this.NODEID_SEPARATOR);
					if (this.isContainCurrentNodeId(stageNodeIds,currentNodeId)){
						this.stage[j].containCurrentNodeId = true;
					}
				}
			}
		},
		
		processHandler : function(){
			//获取正在运行的节点
			var currentStages = query("li[currentnode]",this.domNode);;
			for (var i = 0; i < currentStages.length; i++){
				var currentHanlderId = [];
				var currentHanlderName = [];
				var historyHandlerId = [];
				var historyHandlerName = [];
				var nodeIds = domAttr.get(currentStages[i],"nodeids");
				nodeIds = nodeIds.split(";");
				for (var j = 0; j < nodeIds.length; j++){
					var nodeInfo = this.getNodeInfoById(nodeIds[j]);
					if (nodeInfo && nodeInfo.fdCurrentHandlerId){
						currentHanlderId = currentHanlderId.concat(nodeInfo.fdCurrentHandlerId.split(";"));
						currentHanlderName = currentHanlderName.concat(nodeInfo.fdCurrentHandlerName.split(";"));
					}
					if (nodeInfo && nodeInfo.fdHistoryHandlerId){
						historyHandlerId = historyHandlerId.concat(nodeInfo.fdHistoryHandlerId.split(";"));
						historyHandlerName = historyHandlerName.concat(nodeInfo.fdHistoryHandlerName.split(";"));
					}
				}
				domAttr.set(currentStages[i],"currentHandlerId",currentHanlderId.join(";"));
				domAttr.set(currentStages[i],"currentHandlerName",currentHanlderName.join(";"));
				domAttr.set(currentStages[i],"historyHandlerId",historyHandlerId.join(";"));
				domAttr.set(currentStages[i],"historyHandlerName",historyHandlerName.join(";"));
				var stageImg = query("[class='spot spot-current']",currentStages[i]);
				on(stageImg,"click",lang.hitch(this, this.currentStageOnMouseover));
			}
		},
		
		currentStageOnMouseover : function(event){
			event = event || window.event;
			event.cancelBubble = true;
			if (event.preventDefault){
				event.preventDefault();
			}
			if (event.stopPropagation) {
				event.stopPropagation();
			}
			//连续点击不能超过一秒,不知道为啥ios只点了一次会进来两次这个事件
			var nowTime = new Date().getTime();
		    var clickTime = this.ctime;
		    if( clickTime != 'undefined' && (nowTime - clickTime < 500)){
		        return false;
		     }
		    this.ctime = nowTime;
		    var srcElement = event.srcElement || event.relatedTarget;
			var liObj = srcElement.parentNode;
			var handlerObj = query("[name='handlerWrap']",liObj)[0];
		    if (this.handlerShow){
				domStyle.set(handlerObj,"display","none");
				this.handlerShow = false;
			}else{
				domStyle.set(handlerObj,"z-index",100);
				domStyle.set(handlerObj,"display","block");
				var historyHandlerObj = query("[name='historyHandler']",handlerObj)[0];
				var currentHandlerObj = query("[name='currentHandler']",handlerObj)[0];
				var currentHandlerNames = domAttr.get(liObj,"currentHandlerName");
				var historyHandlerNames = domAttr.get(liObj,"historyHandlerName");
				//已提交
				if (historyHandlerNames){
					historyHandlerNames = historyHandlerNames.split(";");
					historyHandlerNames = historyHandlerNames.join(" ");
				}else{
					historyHandlerNames = "";
				}
				historyHandlerObj.innerHTML = historyHandlerNames;
				//未提交
				if (currentHandlerNames){
					currentHandlerNames = currentHandlerNames.split(";");
					currentHandlerNames = currentHandlerNames.join(" ");
					
				}else{
					currentHandlerNames = "";
				}
				currentHandlerObj.innerHTML = currentHandlerNames;
				this.contentElementPosition(liObj,handlerObj);
				this.handlerShow = true;
			}
		},
		
		/**
		 * 提示元素定位
		 * @returns
		 */
		contentElementPosition : function(srcElement,handlerObj){
			var offset = this.getOffsetTopByBody(srcElement);
			//图标x轴坐标
			var x = offset.left + document.body.scrollLeft;
			//图标y轴坐标
			var y = srcElement.offsetTop;
			//scrollWidth 对象的实际内容的宽度
			var scrollWidth = document.body.scrollWidth;
			//scrollHeight 对象的实际内容的高度
			var scrollHeight = document.body.scrollHeight;
			//提示框的宽度
			var divWidth = domStyle.get(handlerObj,"width");
			//提示框的高度
			var divHeight = domStyle.get(handlerObj,"height");
			//这几个数字怎么来滴，我也不知道
			if(x > divWidth){
				domStyle.set(handlerObj,"marginLeft","-255px");
			}else{
				domStyle.set(handlerObj,"marginLeft","-110px");
			}
			if(y < divHeight){
				domStyle.set(handlerObj,"bottom",'-70px');
			}
		},
		
		getOffsetTopByBody : function (el) {
			  var offsetTop = 0;
			  var offsetLeft = 0;
			  while (el && el.tagName !== 'BODY') {
			    offsetTop += el.offsetTop;
			    offsetLeft += el.offsetLeft;
			    el = el.offsetParent;
			  }
			  return {left:offsetLeft,top:offsetTop};
		},
		
		createHandlerDom : function (){
			var html = [];
			html.push("<div name='handlerWrap' class='lui_flow_rotundity_tooltip' style='position:absolute;'>");
			html.push("<div class='lui_flow_rotundity_handlerTitle'>" + this.HANDLER + "</div>");
			html.push("<div class='lui_flow_rotundity_historyHandlerTitle'>" + this.CURRENTHANDLER + "</div>");
			html.push("<div class='lui_flow_rotundity_historyHandler' name='historyHandler'></div>");
			html.push("<div class='lui_flow_rotundity_currentHandlerTitle'>" + this.HISTORYHANDLER + "</div>");
			html.push("<div class='lui_flow_rotundity_currentHandler' name='currentHandler'></div>");
			html.push("</div>");
			return html.join("");
		},
		
		/**
		 * 根据节点获取节点信息
		 * @param nodeId
		 * @returns
		 */
		getNodeInfoById : function(nodeId){
			for (var j = 0; j < this.lbpmInfo.length; j++){
				var nodeInfo = this.lbpmInfo[j];
				if (nodeId == nodeInfo.fdNodeId){
					return nodeInfo;
				}
			}
		},
		
		doDraw : function(){
			var size = this.stage.length;
			//如果显示开始/结束节点
			if (this.startEndNode){
				size += 2;
			}
			var padding = 10;
			//当前阶段图控件所在父节点宽度(-10是因为有padding)
            var width = domGeometry.getMarginBox(this.domNode.parentNode).w-10;
			var html = [];
			//125为一个阶段的宽度,width/125计算出阶段图控件所在的父节点能存放的阶段个数,也就是一个ul里面包含的li个数
			var oneStageWidth = 125;
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
					if(this.startEndNode){
						//开始节点
						if (i == 0 && j == 0){
							stageName = this.STARTNODE;
							isStartNode = true;
						}else if(i == lineNum - 1 && j == (size - (i * lineLiNum + 1))){//结束节点
							stageName = this.ENDNODE;
							isEndNode = true;
						}else{//阶段节点
							var stageObj = this.stage[index - 1];
							stageName = stageObj.stageName;
							stageNodeId = stageObj.stageNodeId;
						}
					}else{//不显示开始/结束节点
						var stageObj = this.stage[index];
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
					if (this.isLastStage(i,lineLiNum,j)){
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
						if (this.startEndNode){
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
					if (this.approvalTime){
						html.push("<p class='subhead'></p>");
					}
					if (stageObj && stageObj.containCurrentNodeId){
						html.push(this.createHandlerDom());
					}
					html.push("</li>")
				}
				html.push("</ul>");
			}
			html.push("</div>");
			//圆环流程图表 Ends
			domConstruct.place(html.join(""),this.domNode,'last');
		},
		
		processPassedStage : function(){
			var passedStage = this.findPassedStage(this.findPrveStageByCurrentStage());
			this.addPassedCss(passedStage);
			this.addPassTime(passedStage);
			this.passedStage = passedStage;
			if (this.isAllPass(this.stage)){
				this.passedStage = passedStage;
				var canApplyStage = this.findPassedStage(this.findAllStage());
				this.addPassedCss(canApplyStage);
				this.addPassTime(canApplyStage);
			}
		},
		
		 findPrveStageByCurrentStage : function(){
			var currentStage = this.findCanApplyCurrentStage();
			var prevStage = [];
			if (!currentStage){
				return prevStage;
			}
			var currentIndex = domAttr.get(currentStage,"index");
			var allStage = this.findAllStage();
			for(var i = 0; i < allStage.length; i++){
				var obj = allStage[i];
				var index = domAttr.get(obj,"index");
				if (index && parseInt(index) < parseInt(currentIndex)){
					prevStage.push(obj);
				}
			}
			return prevStage;
		},
		
		findAllStage : function (){
			 return query("li",this.domNode);
		},
		
		/**
		 * 判断所有的阶段是否已经通过
		 * @param stages 所有阶段
		 * @returns
		 */
		isAllPass : function(stages){
			var isAllPass = true;
			if (this.processIsPass()){
				return true;
			}
			stages = stages || [];
			for (var i = 0; i < stages.length; i++){
				var stageNodeId = stages[i].stageNodeId.split(this.NODEID_SEPARATOR);
				if (!this.stageIsPass(stageNodeId)){
					isAllPass = false;
					break;
				}
			}
			return isAllPass;
		},
		
		processIsPass : function (){
			for(var i = 0; i < this.lbpmInfo.length; i++){
				var info = this.lbpmInfo[i];
				if (info.fdStatus === "30"){
					return true;
				}
			}
			return false;
		},
		
		/**
		 * 判断某个阶段的所有节点是否已经通过
		 * @param stageNodeIds 某个阶段所对应的节点数组
		 * @returns
		 */
		stageIsPass : function(stageNodeIds){
			stageNodeIds = stageNodeIds || [];
			var nodes = this.getPassNodes(this.lbpmInfo);
			var isPass = true;
			nodes = nodes || [];
			for (var i = 0; i < stageNodeIds.length; i++){
				if (!this.passNodeContain(nodes,stageNodeIds[i])){
					isPass = false;
					return isPass;
				}
			}
			return isPass;
		},
		
		passNodeContain : function(nodes,stageNodeId){
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
		},
		
		getPassNodes : function (){
			var info = this.lbpmInfo || [];
			var passNodes = [];
			for (var i = 0; i < info.length; i++){
				var nodeObj = info[i];
				if (nodeObj.fdStatus === this.NODE_STATUS.PASS){
					passNodes.push(nodeObj);
				}
			}
			return passNodes;
		},
		
		/**
		 * 获取可以使用的当前阶段,可能存在多个正在运行的阶段,这里获取最后一个
		 * @returns
		 */
		findCanApplyCurrentStage : function(){
			var currentStages = query("li[currentnode]",this.domNode);
			var canApplyStage = [];
			var canApplyCurrentStage;
			var index = 0;
			if (currentStages.length == 0){
				return ;
			}else{
				canApplyCurrentStage = currentStages[0];
				index = parseInt(domAttr.get(canApplyCurrentStage,"index") || 0);
				for(var i = 1; i < currentStages.length; i++){
					var stageIndex = domAttr.get(currentStages[i],"index"); 
					if (stageIndex >= index){
						 index = parseInt(stageIndex || 0);
						 canApplyCurrentStage = currentStages[i];
					 }
				}
			}
			
			return canApplyCurrentStage;
		},
		
		findPassedStage : function(candicateStages){
			candicateStages = candicateStages || [];
			var canApplyStage = [];
			for(var j = 0; j < candicateStages.length; j++){
				if(domAttr.get(candicateStages[j],"startNode") == "true" 
					|| domAttr.get(candicateStages[j],"endNode") == "true"
						|| domAttr.get(candicateStages[j],"currentnode") == "true"){
					continue;
				}else{
					canApplyStage.push(candicateStages[j]);
				}
			}
			return canApplyStage;
		},
		
		addPassedCss : function(passedStage){
			passedStage = passedStage || [];
			for(var i = 0; i < passedStage.length; i++){
				var node = query("span[class='spot']",passedStage[i])[0];
				domClass.add(node,'spot-finish');
			}
		},
		
		/**
		 * 添加通过时间
		 * @param passedStage 通过的阶段
		 * @param lbpmData 通过时间
		 * @returns
		 */
		addPassTime : function (passedStage){
			if(!this.approvalTime || this.lbpmInfo.length == 0 
					|| passedStage.length == 0){
				return;
			}
			passedStage = passedStage || [];
			for(var i = 0; i < passedStage.length;i++){
				var obj = passedStage[i];
				var nodeIds =  domAttr.get(obj,"nodeIds").split(this.NODEID_SEPARATOR);
				var date = "";
				var subhead = query("p[class='subhead']",obj)[0];
				var maxDate = this.getPassTimeByNodeId(nodeIds[0]) || "";
				var minDate = maxDate;
				for (var j = 1; j < nodeIds.length; j++){
					var nextDate = this.getPassTimeByNodeId(nodeIds[j]) || "";
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
					date = minDate + "~" + maxDate;
				}
				html.set(subhead,date);
			};
		},
		
		/**
		 * 获取指定节点的通过时间
		 */
		getPassTimeByNodeId : function(nodeId){
			var info = this.lbpmInfo;
			for (var i = 0; i < info.length; i++){
				var nodeObj = info[i];
				if (nodeObj.fdNodeId == nodeId){
					return nodeObj.fdFinishDate;
				}
			}
		},
		
		/**
		 * 判断是否是最后一个阶段
		 * @param rowIndex 当前行索引
		 * @param rowSize	每行个数
		 * @param colIndex	当前列索引
		 * @returns
		 */
		isLastStage : function(rowIndex,rowSize,colIndex){
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
		},
		
		/**
		 * 获取当前流程正在运行的节点
		 */
		getAvailableRunningNodes : function(){
			var info = this.lbpmInfo || [];
			var runningNode = [];
			for (var i = 0; i < info.length; i++){
				var nodeObj = info[i];
				if (nodeObj.fdStatus === this.NODE_STATUS.RUNNING){
					runningNode.push(nodeObj);
				}
			}
			return runningNode;
		},
		
		/**
		 * 判断当前流程节点是否包含在指定阶段内
		 * @param stageNodeIds 阶段所包含的节点
		 * @param currentNodeId 当前正在运行的流程节点
		 * @returns
		 */
		isContainCurrentNodeId : function(stageNodeIds,currentNodeId){
			stageNodeIds = stageNodeIds || [];
			for (var i = 0; i < stageNodeIds.length; i++){
				var id = stageNodeIds[i];
				if (id === currentNodeId){
					return true;
				}
			}
			return false;
		}
	});
	return claz;
});