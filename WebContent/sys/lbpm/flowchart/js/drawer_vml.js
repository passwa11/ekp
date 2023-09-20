(function(FlowChartObject) {

	// 显示隐藏
	FlowChartObject.ShowElement = function(element, show) {
		if (!element) {
			return;
		}
		element.style.display = show ? "" : "none";
	};
	// 删除元素
	FlowChartObject.RemoveElement = function(element) {
		element.parentNode.removeChild(element);
	};
	// 获取元素大小
	FlowChartObject.GetElementSize = function(element) {
		return {
			width : element.clientWidth,
			height : element.clientHeight
		};
	};
	// 设置位置
	FlowChartObject.SetPosition = function(element, left, top) {
		element.style.left = left + "px";
		element.style.top = top + "px";
	};
	// 设置填充颜色
	FlowChartObject.SetFillcolor = function(obj, color) {
		if (obj.nodeName && obj.nodeName == "group") {
			obj.firstChild.fillcolor = color; // 并发节点
		} else {
			obj.fillcolor = color;
		}
	};
	// 获取填充颜色
	FlowChartObject.GetFillcolor = function(obj) {
		if (obj.nodeName && obj.nodeName == "group") {
			return obj.firstChild.fillcolor; // 并发节点
		} else {
			return obj.fillcolor;
		}
	};
	// 设置边框颜色
	FlowChartObject.SetStrokeColor = function(obj, color) {
		if (obj.nodeName && obj.nodeName == "group") {
			obj.firstChild.StrokeColor = color; // 并发节点
		} else {
			obj.StrokeColor = color;
		}
	};
	// 设置连线拐点
	FlowChartObject.SetLinePoints = function(obj, points) {
		obj.points.value = points;
		
		//#58210 辅助操作线,避免连接线过窄点击困难
		if(obj.LKSObject&&obj.LKSObject.PolyLineAssist){
			obj.LKSObject.PolyLineAssist.points.value=points;
		}
	};
	// 设置文本
	FlowChartObject.SetText = function(obj, text) {
		if (obj) {
			var label = "";
			if (text && text.constructor == Array) {
				for ( var i = 0; i < text.length; i++) {
					if (i > 0) {
						label += "<hr size=1>";
					}
					label += Com_HtmlEscapeText(text[i]);
				}
				text = label;
			} else {
				label += Com_HtmlEscapeText(text);
			}
			obj.rows[0].cells[0].innerHTML = label;
		}
	};

	FlowChartObject.Nodes.SetRectDOMImage = function(tbObj, obj) {
		var nodeType = FlowChartObject.Nodes.Types[obj.Type];
		var backgroundImage = nodeType.BackgroundImage ? "url("
				+ nodeType.BackgroundImage + ")" : "url(../images/"
				+ obj.Type.toLowerCase() + ".png)";
		tbObj.style.backgroundImage = backgroundImage;
	};

	// 线
	FlowChartObject.Lines.CreateLineDOM = function(obj) {
		//#58210 辅助操作线,避免连接线过窄点击困难
		var polyLineAssist = "<v:PolyLine filled='false' style='position:absolute;background-color:rgba(0,0,0,0)' strokeWeight='"
			+ 10
			+ "px' StrokeColor='#ffffff'/>";//StrokeColor='rgba(0,0,0,0)' 
		var newElemPolyLineAssist = document.createElement(polyLineAssist);
		document.body.appendChild(newElemPolyLineAssist);
		obj.PolyLineAssist=newElemPolyLineAssist;
		newElemPolyLineAssist.LKSObject=obj;
				
		var htmlCode = "<v:PolyLine filled='false' style='position:absolute' strokeWeight='"
				+ obj.Weight
				+ "px' StrokeColor='"
				+ FlowChartObject.LINESTYLE_STATUSCOLOR[obj.Status] + "' />";
		var newElem = document.createElement(htmlCode);
		document.body.appendChild(newElem);
		obj.DOMElement = newElem;
		newElem.className = "line_" + obj.Type;
		if (obj.Type == "opt") {
			newElem.innerHTML = "<v:stroke EndArrow=Classic /><v:stroke dashstyle=LongDash />";
		} else {
			newElem.innerHTML = "<v:stroke EndArrow=Classic />";
			newElem.LKSObject = obj;
			// 文本
			var tableElem = document.createElement("table");
			tableElem.className = "line_text";
			tableElem.style.position = "absolute";
			tableElem.style.display = "none";
			tableElem.insertRow(-1).insertCell(-1);
			document.body.appendChild(tableElem);
			tableElem.LKSObject = obj;
			obj.DOMText = tableElem;
		}
	};

	// 点
	FlowChartObject.Points.CreatePointDOM = function(obj) {
		var htmlCode = obj.Type == "line" ? "<v:oval class='point_main' style='position:absolute;width:8;height:8;display:none' fillcolor='#00FF00'/>"
				: "<v:oval class='point_main' style='position:absolute;width:8;height:8;display:none' strokecolor='#0000FF' fillcolor='#9999FF'/>";
		var newElem = document.createElement(htmlCode);
		document.body.appendChild(newElem);
		obj.DOMElement = newElem;
		if (obj.Type == "line") {
			newElem.title = FlowChartObject.Lang.PointHelp;
			newElem.LKSObject = obj;
			newElem.onmouseover = function(e) {
				this.fillcolor = "#FF0000";
			};
			newElem.onmouseout = function(e) {
				this.fillcolor = "#00FF00";
			};
		}
	};

	// 功能：创建圆角节点
	FlowChartObject.Nodes.CreateRoundRectDOM = function(obj) {
		if (obj.DOMElement != null)
			FlowChartObject.RemoveElement(obj.DOMElement);
		obj.Width = 120;
		obj.Height = 40;
		if (FlowChartObject.isNeedSmall && !FlowChartObject.IsEdit
				&& !FlowChartObject.IsTemplate) {
			obj.Width = obj.Width - obj.Small_WidthRank;
			obj.Height = obj.Height - obj.Small_HeightRank;
		}

		// 模板不隐藏节点文本和背景 @作者：曹映辉 @日期：2013年3月5日
		if (FlowChartObject.isNeedSmall && !FlowChartObject.IsEdit
				&& !FlowChartObject.IsTemplate && obj.Small_ImgageURL) {
			// 缩小图标，不需要显示文本
			// 构造页面对象
			var newElem = document
					.createElement("<v:RoundRect class='node_main' arcsize='0.5' style='position:absolute;width:"
							+ obj.Width + "px;height:" + obj.Height + "px' />");

			var htmlCode = "<v:imagedata src='" + obj.Small_ImgageURL + "'/>";
			htmlCode += "<v:TextBox inset=\"0pt,0pt,0pt,0pt\">";
			htmlCode += "<table><tr><td></td></tr></table></v:TextBox>";
			newElem.innerHTML = htmlCode;
			document.body.appendChild(newElem);
			newElem.LKSObject = obj;
			obj.DOMElement = newElem;

			var tbObj = newElem.lastChild.lastChild;
			FlowChartObject.Nodes.SetRectDOMImage(tbObj, obj);
		} else {
			// 构造页面对象
			var newElem = document
					.createElement("<v:RoundRect class='node_main' arcsize='0.5' style='position:absolute;width:"
							+ obj.Width + "px;height:" + obj.Height + "px' />");			
			var htmlCode = "<v:shadow on=\"T\" type=\"single\" color=\"#ffffff\" offset=\"3px,3px\"/>";
			htmlCode += "<v:TextBox inset=\"0pt,0pt,0pt,0pt\">";
			//更换图标显示方式
			var backgroundImage = "../images/"+ obj.Type.toLowerCase() + ".png";
			htmlCode+="<img style=\"position:absolute;z-index:10;margin:3px 0px 0px 4px;\" src=\""+backgroundImage+"\" />";
			htmlCode += "<table class=\"round_nodetb\"><tr><td></td></tr></table></v:TextBox>";
			newElem.innerHTML = htmlCode;
			document.body.appendChild(newElem);
			newElem.LKSObject = obj;
			obj.DOMElement = newElem;

			var tbObj = newElem.lastChild.lastChild;
			//FlowChartObject.Nodes.SetRectDOMImage(tbObj, obj);
			obj.DOMText = tbObj;
		}
	};
	/**
	 * 新增 王祥 2017-10-27
	 * 创建节点快捷操作菜单
	 */
	FlowChartObject.Nodes.quickBuild=function(obj){
		//目前只做了svg的实现，Vml实现暂未实现，为让程序能够在Vml模式下正常运行，添加空函数
	}
	/**
	 * 新增 王祥 2017-10-27
	 * 清除快捷菜单的缓存
	 */
	FlowChartObject.Nodes.removeQuickBuild=function(){
		//目前只做了svg的实现，Vml实现暂未实现，为让程序能够在Vml模式下正常运行，添加空函数
	}

	// 功能：创建方角节点
	FlowChartObject.Nodes.CreateRectDOM = function(obj) {
		if (obj.DOMElement != null)
			FlowChartObject.RemoveElement(obj.DOMElement);
		obj.Width = 120;
		obj.Height = 40;
		// 增加 图标缩小功能@作者：曹映辉 @日期：2013年4月3日

		if (FlowChartObject.isNeedSmall && !FlowChartObject.IsEdit
				&& !FlowChartObject.IsTemplate) {
			obj.Width = obj.Width - obj.Small_WidthRank;
			obj.Height = obj.Height - obj.Small_HeightRank;
		}

		// 模板不隐藏节点文本和背景 @作者：曹映辉 @日期：2013年3月5日
		if (FlowChartObject.isNeedSmall && !FlowChartObject.IsEdit
				&& !FlowChartObject.IsTemplate && obj.Small_ImgageURL) {
			// 构造页面对象
			var newElem = document
					.createElement("<v:RoundRect stroked='f' class='node_main' style='border:0px;position:absolute;width:"
							+ obj.Width + "px;height:" + obj.Height + "px' />");
			var htmlCode = "<v:imagedata src='" + obj.Small_ImgageURL + "'/>";
			htmlCode += "<v:TextBox>";
			htmlCode += "<table><tr><td></td></tr></table></v:TextBox>";
			newElem.innerHTML = htmlCode;
			document.body.appendChild(newElem);
			newElem.LKSObject = obj;
			obj.DOMElement = newElem;
			var tbObj = newElem.lastChild.lastChild;
			FlowChartObject.Nodes.SetRectDOMImage(tbObj, obj);
			// obj.DOMText = tbObj.rows[0].cells[0];
			obj.Refresh = FlowChart_Node_Refresh;
		} else {
			// 构造页面对象
			var newElem = document
					.createElement("<v:RoundRect class='node_main' style='position:absolute;width:"
							+ obj.Width + "px;height:" + obj.Height + "px' />");
			var htmlCode = "<v:shadow on=\"T\" type=\"single\" color=\"#ffffff\" offset=\"3px,3px\"/>";
			htmlCode += "<v:TextBox inset=\"0pt,0pt,0pt,0pt\">";
			//更换图标显示方式
			var backgroundImage = "../images/"+ obj.Type.toLowerCase() + ".png";
			htmlCode+="<img style=\"position:absolute;z-index:10;margin:8px 0px 0px 7px;\" src=\""+backgroundImage+"\" />";
			htmlCode += "<table class=\"normal_nodetb\"><tr><td></td></tr></table></v:TextBox>";
			newElem.innerHTML = htmlCode;
			document.body.appendChild(newElem);
			newElem.LKSObject = obj;
			obj.DOMElement = newElem;
			var tbObj = newElem.lastChild.lastChild;
			//FlowChartObject.Nodes.SetRectDOMImage(tbObj, obj);
			obj.DOMText = tbObj;
			obj.Refresh = FlowChart_Node_Refresh;
		}
	};

	// 功能：创建大图标节点
	FlowChartObject.Nodes.CreateBigRectDOM = function(obj) {
		if (FlowChartObject.IconType == ICONTYPE_BIG) {
			if (obj.DOMElement != null)
				FlowChartObject.RemoveElement(obj.DOMElement);
			obj.Width = 200;
			obj.Height = 80;
			// 构造页面对象
			var newElem = document
					.createElement("<v:RoundRect class='node_main' style='position:absolute;width:200px;height:80px' />");
			var htmlCode = "<v:shadow on=\"T\" type=\"single\" color=\"#ffffff\" offset=\"3px,3px\"/>";
			htmlCode += "<v:TextBox inset=\"0pt,0pt,0pt,0pt\">";
			htmlCode += "<table class=\"big_nodetb\"><tr><td></td></tr></table></v:TextBox>";
			newElem.innerHTML = htmlCode;
			document.body.appendChild(newElem);
			newElem.LKSObject = obj;
			obj.DOMElement = newElem;
			var tbObj = newElem.lastChild.lastChild;
			FlowChartObject.Nodes.SetRectDOMImage(tbObj, obj);
			obj.DOMText = tbObj;
			obj.Refresh = FlowChart_Node_BigIconRefresh;
		} else {
			FlowChartObject.Nodes.CreateRectDOM(obj);
		}
		obj.ChangeIconType = FlowChart_Node_ChangeIconType;
	};

	// 功能：创建菱形节点
	FlowChartObject.Nodes.CreateDiamondDOM = function(obj) {
		if (obj.DOMElement != null)
			FlowChartObject.RemoveElement(obj.DOMElement);
		obj.Width = 120;
		obj.Height = 80;
		// 增加 图标缩小功能 @作者：曹映辉 @日期：2013年3月6日
		if (FlowChartObject.isNeedSmall && !FlowChartObject.IsEdit
				&& !FlowChartObject.IsTemplate) {
			obj.Width = obj.Width - obj.Small_WidthRank;
			obj.Height = obj.Height - obj.Small_HeightRank;
		}

		// 模板不隐藏节点文本和背景 @作者：曹映辉 @日期：2013年3月5日
		if (FlowChartObject.isNeedSmall && !FlowChartObject.IsEdit
				&& !FlowChartObject.IsTemplate && obj.Small_ImgageURL) {
			// 缩小图标，不需要显示文本
			// 构造页面对象
			var newElem = document
					.createElement("<v:shape stroked='f' class='node_main' type='#diamond' style='position:absolute;width:"
							+ obj.Width + "px;height:" + obj.Height + "px' />");
			var htmlCode = "<v:imagedata src='" + obj.Small_ImgageURL + "'/>";
			htmlCode += "<v:TextBox inset=\"0pt,0pt,0pt,0pt\">";
			htmlCode += "<table><tr><td></td></tr></table></v:TextBox>";
			newElem.innerHTML = htmlCode;
			document.body.appendChild(newElem);
			newElem.LKSObject = obj;
			obj.DOMElement = newElem;
			var tbObj = newElem.lastChild.lastChild;
			FlowChartObject.Nodes.SetRectDOMImage(tbObj, obj);

		} else {
			// 构造页面对象
			var newElem = document
					.createElement("<v:shape class='node_main' type='#diamond' style='position:absolute;width:"
							+ obj.Width + "px;height:" + obj.Height + "px' />");
			var htmlCode = "<v:shadow on=\"T\" type=\"single\" color=\"#b3b3b3\" offset=\"3px,3px\"/>";
			htmlCode += "<v:TextBox inset=\"0pt,0pt,0pt,0pt\">";
			htmlCode += "<table class=\"diamond_nodetb\"><tr><td></td></tr></table></v:TextBox>";
			newElem.innerHTML = htmlCode;
			document.body.appendChild(newElem);
			newElem.LKSObject = obj;
			obj.DOMElement = newElem;
			var tbObj = newElem.lastChild.lastChild;
			FlowChartObject.Nodes.SetRectDOMImage(tbObj, obj);
			obj.DOMText = tbObj;
		}
	};

	function createConcurrentDOM(obj) {
		if (obj.DOMElement != null)
			FlowChartObject.RemoveElement(obj.DOMElement);
		obj.Width = 40;
		obj.Height = 40;
		// 构造页面对象
		var newElem = document
				.createElement("<v:group style='position:absolute;width:40px;height:40px' coordsize='40,40' />");
		var htmlCode = "<v:shape type='#diamond' style='width:40px;height:40px' >";
		htmlCode += "<v:shadow on=\"T\" type=\"single\" color=\"#b3b3b3\" offset=\"3px,3px\"/></v:shape>";
		htmlCode += "<v:shape class='node_main' type='#cross' fillcolor=black style='z-index:200;width:20px;height:20px;top:10px;left:10px;' />";
		newElem.innerHTML = htmlCode;
		document.body.appendChild(newElem);
		newElem.LKSObject = obj;
		obj.DOMElement = newElem;
	}

	// 功能：创建并行分支节点
	FlowChartObject.Nodes.CreateSplitDOM = function(obj) {
		createConcurrentDOM(obj);
	};

	// 功能：创建合并分支节点
	FlowChartObject.Nodes.CreateJoinDOM = function(obj) {
		createConcurrentDOM(obj);
	};
	//缓存选中的标题
	FlowChartObject.Lane.SelectedText=null;
	/**
	 * 在画布上绘画一条角色泳道
	 */
	FlowChartObject.Lane.Roles.CreateRoleDOM=function(obj){		
			
	}
	//在画布上绘制一条阶段泳道
	FlowChartObject.Lane.Stages.CreateStageDOM=function(obj){
				
	}

	//缓存选中的标题
	FlowChartObject.Lane.SelectedText=null;
	/**
	 * 根据坐标变更线的位置
	 */
	FlowChartObject.MoveLineByPoint=function(lineDom,point){

	}
	//变更角色泳道宽度
	FlowChartObject.SetLaneRoleWidth=function(obj){

		
	}
	//变更阶段泳道宽度
	FlowChartObject.SetLaneStageHeight=function(obj){

	}
	/**
	 * 泳道：根据泳道的坐标刷新角色泳道的位置
	 */
	FlowChartObject.Lane.Roles.Refresh=function(role){

	}
	/**
	 * 泳道：根据泳道的坐标刷新阶段泳道的位置
	 */
	FlowChartObject.Lane.Stages.Refresh=function(stage){

	}
	//刷新角色泳道高度
	FlowChartObject.RefreshAllLaneRoleHeight=function(){
		
	}
	//刷新阶段泳道宽度
	FlowChartObject.RefreshAllLaneStageHeight=function(){
		
	}
	
	// 创建子节点
	FlowChartObject.Nodes.createSubNode = function(nodeType,groupNodeId,groupNodeType){
		var node = new FlowChart_Node(nodeType);
		var gNode = FlowChartObject.Nodes.GetNodeById(groupNodeId);
		var eNode = FlowChartObject.Nodes.GetNodeById(gNode.Data.endNodeId);
		var sNode = eNode.LineIn[0].StartNode;
		node.MoveTo(sNode.Data.x, sNode.Data.y,true);
		eNode.LineIn[0].LinkNode(sNode, node, FlowChartObject.NODEPOINT_POSITION_BOTTOM, FlowChartObject.NODEPOINT_POSITION_TOP);
		var line = new FlowChart_Line();
		line.LinkNode(node, eNode, FlowChartObject.NODEPOINT_POSITION_BOTTOM, FlowChartObject.NODEPOINT_POSITION_TOP);
		initCommonNodeData(node);
		initCommonManualNodeData(node);
		node.Data["handlerSelectType"]="org";
		node.Data["ignoreOnHandlerSame"]="false";
		node.Data["onAdjoinHandlerSame"]="false";
		node.Data["operations"]["refId"]="13c5ae137925b6f5b55b6cf4051ac33a";
		node.Data["groupNodeId"]=groupNodeId;
		node.Data["groupNodeType"]=groupNodeType;
		node.Data["processType"] = "2"; // 节点流转方式默认会审
		return node;
	}
	
	// 删除子节点
	FlowChartObject.Nodes.deleteSubNode = function(subNodeId){
		var subNodeObj = FlowChartObject.Nodes.GetNodeById(subNodeId);
		if (subNodeObj != null) {
			if(subNodeObj.LineOut.length==1){
				var endNode = subNodeObj.LineOut[0].EndNode;
				for(var j=subNodeObj.LineIn.length-1; j>=0; j--){
					var line = subNodeObj.LineIn[j];
					line.LinkNode(null, endNode, null, subNodeObj.LineOut[0].Data.endPosition);
				}
			}
			subNodeObj.Delete();
		}
	}
	
	// 初始化人工类节点数据
	FlowChartObject.Nodes.initNodeData = function initNodeData(node,data){
		if(node.Type == "reviewNode"){
			initReviewNodeData(node,data);
		} else if (node.Type == "signNode") {
			initSignNodeData(node,data);
		} else if (node.Type == "sendNode") {
			initSendNodeData(node,data);
		}
		if(node.Status==FlowChartObject.STATUS_UNINIT){
			node.SetStatus(FlowChartObject.STATUS_NORMAL);
		}
		node.Refresh();
	}
	
	function initNodeDataInFreeFlow(node,data){
		FlowChartObject.Nodes.initNodeData(node,data);
		node.Data["canModifyFlow"]="true";
		node.Data["flowPopedom"]="1";
		node.Refresh();
	}
	
	function initReviewNodeData(node,data){
		initCommonNodeData(node,data);
		initCommonManualNodeData(node,data);
	}
	
	function initSignNodeData(node,data){
		initCommonNodeData(node,data);
		initCommonManualNodeData(node,data);
	}
	
	function initSendNodeData(node,data){
		initCommonNodeData(node,data);
		node.Data["canAddOpinion"]="false";
	}
	
	function initCommonManualNodeData(node,data){
		node.Data["processType"]="0";
		var operations = new Array();
		var refId =null;
		operations.refId = refId;
		node.Data["operations"]=operations;
		node.Data["ignoreOnHandlerSame"]="true";
		node.Data["onAdjoinHandlerSame"]="true";
		node.Data["canAddAuditNoteAtt"]="true";
		node.Data["canModifyFlow"]="false";
		node.Data["canModifyMainDoc"]="false";
		node.Data["canModifyNotionPopedom"]="false";
		node.Data["optHandlerCalType"]="2";
		node.Data["optHandlerIds"]="";
		node.Data["optHandlerNames"]="";
		node.Data["optHandlerSelectType"]="org";
		node.Data["orgAttributes"]="handlerIds:handlerNames;optHandlerIds:optHandlerNames;otherCanViewCurNodeIds:otherCanViewCurNodeNames";
		node.Data["recalculateHandler"]=FlowChartObject.ProcessData.recalculateHandler;
		node.Data["useOptHandlerOnly"]="false";
		node.Data["dayOfNotify"]="0";
		node.Data["dayOfPass"]="0";
		node.Data["hourOfNotify"]="0";
		node.Data["hourOfPass"]="0";
		node.Data["hourOfTranNotifyDraft"]="0";
		node.Data["hourOfTranNotifyPrivate"]="0";
		node.Data["minuteOfNotify"]="0";
		node.Data["minuteOfPass"]="0";
		node.Data["minuteOfTranNotifyDraft"]="0";
		node.Data["minuteOfTranNotifyPrivate"]="0";
		node.Data["tranNotifyDraft"]="0";
		node.Data["tranNotifyPrivate"]="0";
		node.Data["flowPopedom"]="1";
	}
	
	function initCommonNodeData(node,data){
		node.Data["handlerSelectType"]="org";
		node.Data["handlerNames"]="";
		node.Data["handlerIds"]="";
		node.Data["ignoreOnHandlerEmpty"]="false";
		var langsValue = _getNodeNameLang(node.Type);
		var nodeLangs={};
		nodeLangs["nodeName"]=langsValue;
		nodeLangsStr=JSON.stringify(nodeLangs);
		if(nodeLangsStr){
			node.Data["langs"]=nodeLangsStr;
		}
		node.Data["orgAttributes"]="handlerIds:handlerNames;optHandlerIds:optHandlerNames;otherCanViewCurNodeIds:otherCanViewCurNodeNames";
	}
	
	function _getNodeNameLang(nodeType){
		//[{lang:"zh-CN","value":""},{lang:"en-US","value":""}]
		var elLang=[];
		if(!_isLangSuportEnabled){
			return elLang;
		}
		if(!(typeof _allNodeName == "undefined" || _allNodeName==null)){
			elLang = _allNodeName[nodeType];
		}
		return elLang;
	}
	
})(FlowChartObject);
