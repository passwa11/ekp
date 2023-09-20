define(['dojo/query', "dijit/registry", 'dojo/_base/array', "sys/lbpmservice/mobile/common/syslbpmprocess"],function(query, registry, array, syslbpmprocess){
	
	var genRoute={};
	function _generateNextNodeInfo(operatorInfo,currentNodeObj) {
		  var html = ""
		  // 是则显示同一节点下一个处理人并不允许编辑。
		  // 不是则显示下一个节点的所有处理人并根据权限显示是否编辑
		  if (
		    operatorInfo.currNodeNextHandlersId &&
		    currentNodeObj.processType == lbpm.constant.PROCESSTYPE_SERIAL
		  ) {
		    var langNodeName = WorkFlow_getLangLabel(
		      currentNodeObj.name,
		      currentNodeObj["langs"],
		      "nodeName"
		    )
		    var labelText = genRoute.resolveNodeIdentifier(
		      currentNodeObj.id,
		      langNodeName
		    )
		    html =
		      '<div class="lbpmNextRouteInfoRow "><div id="nextNodeName">' +
		      labelText +
		      "</div>"
		    html +=
		      '<input type="hidden" id="handlerIds" name="handlerIds" value="' +
		      operatorInfo.currNodeNextHandlersId +
		      '">'
		    html +=
		      '<input type="hidden" id="handlerNames" name="handlerNames" readonly class="inputSgl" onChange="lbpm.globals.setHandlerInfoes();" value="' +
		      Com_HtmlEscape(operatorInfo.currNodeNextHandlersName) +
		      '">'
		    html +=
		      '<div id="handlerShowNames" class=handlerNamesLabel nodeId="' +
		      currentNodeObj.id +
		      '">(' +
		      Com_HtmlEscape(
		        operatorInfo.currNodeNextHandlersName.replace(/;/g, "; ")
		      ) +
		      ")</div></div>"
		  } else if (operatorInfo.toRefuseThisNodeId && lbpm.globals.getNodeObj(operatorInfo.toRefuseThisNodeId)) {
		    html = lbpm.globals.generateRefuseThisNodeIdInfo(
		      operatorInfo.toRefuseThisNodeId,
		      operatorInfo.toRefuseThisHandlerIds,
		      operatorInfo.toRefuseThisHandlerNames,
		      operatorInfo.currNodeNextHandlersId,
		      operatorInfo.currNodeNextHandlersName
		    )
		  } else {
		    var nextNodeObj = lbpm.globals.getNextNodeObj(lbpm.nowNodeId)
		    var routeLines = lbpm.nodedescs[nextNodeObj.nodeDescType].getLines(
		      lbpm.globals.getCurrentNodeObj(),
		      nextNodeObj,
		      true
		    )
		    if (
		      nextNodeObj.nodeDescType == "splitNodeDesc" ||
		      nextNodeObj.nodeDescType == "autoBranchNodeDesc"
		    ) {
		      var filterRouteLine = new Array()
		      lbpm.globals.getThroughNodes(
		        function(throughtNodes) {
		          var throughtIds = lbpm.globals.getIdsByNodes(throughtNodes) + ","
		          for (var i = 0; i < routeLines.length; i++) {
		            var lineLinkNode =
		              routeLines[i].startNodeId + "," + routeLines[i].endNodeId + ","
		            if (throughtIds.indexOf(lineLinkNode) > -1) {
		              filterRouteLine.push(routeLines[i])
		            }
		          }
		        },
		        null,
		        null,
		        false
		      )
		      routeLines = filterRouteLine
		      if (
		        nextNodeObj.nodeDescType == "autoBranchNodeDesc" &&
		        filterRouteLine.length != 1
		      ) {
		        routeLines = lbpm.nodedescs[nextNodeObj.nodeDescType].getLines(
		          lbpm.globals.getCurrentNodeObj(),
		          nextNodeObj,
		          false
		        )
		      }
		    }
		    // 如果连线的结束节点为组结束节点则显示对应组节点的下一节点
		    if (nextNodeObj.nodeDescType == "groupEndNodeDesc"&&nextNodeObj.groupNodeType!="freeSubFlowNode") {
		      routeLines = lbpm.nodes[nextNodeObj.groupNodeId].endLines
		    }
		    html = lbpm.globals.getNextRouteInfo(routeLines)
		    // 针对即将流向的节点以及当前节点是即席子流程内的子节点时
		    if (
		      lbpm.nodes[lbpm.nowNodeId].groupNodeId != null &&
		      lbpm.nodes[lbpm.nowNodeId].groupNodeType == "adHocSubFlowNode" &&
		      nextNodeObj.nodeDescType == "groupEndNodeDesc"
		    ) {
		      lbpm.nowAdHocSubFlowNodeId = lbpm.nodes[lbpm.nowNodeId].groupNodeId // lbpm.nowAdHocSubFlowNodeId - 当前即席子流程节点ID
		      lbpm.adHocRouteId = lbpm.nodes[lbpm.nowNodeId].routeId // lbpm.adHocRouteId - 当前即席子流程的当前子节点所在环节的标识（就是环节首节点的编号)，用于选择下一步环节时排除掉当前所在环节
		      html = lbpm.globals.getNextAdHocSubFlowRouteInfo()
		    } else if (nextNodeObj.nodeDescType == "adHocSubFlowNodeDesc") {
		      lbpm.nowAdHocSubFlowNodeId = nextNodeObj.id
		      lbpm.adHocRouteId = null
		      html = lbpm.globals.getNextAdHocSubFlowRouteInfo()
		    }
		  }
		  return html
		}
	lbpm.globals.getAutoNextInfo = function(){
		var nextNodeTD = document.getElementById("nextNodeTD");
		if(nextNodeTD!=null) {
			lbpm.globals.generateNextNodeInfo(function (html) {
				lbpm.globals.innerHTMLGenerateNextNodeInfo(html, nextNodeTD);
				lbpm.globals.hiddenObject(nextNodeTD.parentNode, false);
				//隐藏即将流向
				if (Lbpm_SettingInfo.isHideOperationsRow == "true" && lbpm.canHideNextNodeTr) {
					lbpm.globals.hiddenObject(nextNodeTD.parentNode, true);
				}
			});
		}
	};
		// 生成即将流向节点的HTML信息
		genRoute.generateNextNodeInfo = lbpm.globals.generateNextNodeInfo = function(
		  callback
		) {
		  //如果发现即将处理人在缓存中有时，直接获取缓存中的记录（不用重新计算）
		  if (
		    lbpm.nowProcessorInfoObj["lbpm_nextHandlerName"] != null &&
		    lbpm.nowProcessorInfoObj["lbpm_nextHandlerNameId"] != null &&
		    lbpm.nowProcessorInfoObj["lbpm_nextNodeId"] != null
		  ) {
		    var node = lbpm.nodes[lbpm.nowProcessorInfoObj["lbpm_nextNodeId"]]
		    var nextShowHandlerName = lbpm.nowProcessorInfoObj["lbpm_nextHandlerName"]
		    var langNodeName = WorkFlow_getLangLabel(
		      node.name,
		      node["langs"],
		      "nodeName"
		    )
		    var labelText = genRoute.resolveNodeIdentifier(node.id, langNodeName)
		    html = '<label id="nextNodeName"><b>' + labelText + "</b></label>"
		    html +=
		      '<input type="hidden" id="handlerIds" name="handlerIds" value="' +
		      lbpm.nowProcessorInfoObj["lbpm_nextHandlerNameId"] +
		      '">'
		    html +=
		      '<input type="hidden" id="handlerNames" name="handlerNames" readonly class="inputSgl" onChange="lbpm.globals.setHandlerInfoes();" value="' +
		      Com_HtmlEscape(nextShowHandlerName) +
		      '">'
		    html +=
		      '<label id="handlerShowNames" class=handlerNamesLabel nodeId="' +
		      node.id +
		      '">(' +
		      Com_HtmlEscape(nextShowHandlerName.replace(/;/g, "; ")) +
		      ")</label>"
		    return html
		  }
		  //判断即将流向的处理人是否还是同一节点：
		  var currentNodeObj = lbpm.nodes[lbpm.nowNodeId]
		  /*
		   * currNodeNextHandlersId:当前节点处理人的下一处理人(串行)
		   * currNodeNextHandlersName:当前节点处理人的下一处理人名称(串行)
		   * toRefuseThisNodeId:驳回时如果选择重新回到本节点时，驳回时节点的的ID（如N1,N2）
		   * toRefuseThisHandlerIds:驳回时如果选择重新回到本节点时，驳回时未处理人的ID集
		   * toRefuseThisHandlerNames:驳回时如果选择重新回到本节点时，驳回时未处理人名称集
		   */

		  // 异步
		  if (callback) {
		    lbpm.globals.getOperationParameterJson(
		      "currNodeNextHandlersId" +
		        ":currNodeNextHandlersName" +
		        ":toRefuseThisNodeId" +
		        ":toRefuseThisHandlerIds" +
		        ":toRefuseThisHandlerNames" +
		        ":futureNodeId",
		      null,
		      null,
		      function(operatorInfo) {
		        callback(_generateNextNodeInfo(operatorInfo, currentNodeObj))
		      }
		    )
		    return
		  }

		  // 同步
		  var operatorInfo = lbpm.globals.getOperationParameterJson(
		    "currNodeNextHandlersId" +
		      ":currNodeNextHandlersName" +
		      ":toRefuseThisNodeId" +
		      ":toRefuseThisHandlerIds" +
		      ":toRefuseThisHandlerNames" +
		      ":futureNodeId"
		  )

		  return _generateNextNodeInfo(operatorInfo, currentNodeObj)
		}


	function _parseNextNodeHandler(nodeObj,handlerIds){
		var html=""
		//如果是处理人为公式计算则不显示原公式改为显示“公式计算” modify by limh 2010年11月29日
		var dataNextNodeHandler;
		var nextNodeHandlerNames4View="";
		if(nodeObj.handlerSelectType){
			if (nodeObj.handlerSelectType=="formula") {
				dataNextNodeHandler=lbpm.globals.formulaNextNodeHandler(handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj),undefined,nodeObj.id);
			} else if (nodeObj.handlerSelectType=="matrix") {
				dataNextNodeHandler=lbpm.globals.matrixNextNodeHandler(handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj),undefined,nodeObj.id);
			} else if (nodeObj.handlerSelectType=="rule") {
				dataNextNodeHandler=lbpm.globals.ruleNextNodeHandler(nodeObj.id, handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj));
			} else {
				dataNextNodeHandler=lbpm.globals.parseNextNodeHandler(handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nodeObj),undefined,nodeObj.id);
			}
			for(var j=0;j<dataNextNodeHandler.length;j++){
				if(nextNodeHandlerNames4View==""){
					nextNodeHandlerNames4View=dataNextNodeHandler[j].name;
				}else{
					nextNodeHandlerNames4View+=";"+dataNextNodeHandler[j].name;
				}
			}
		}
		if(nextNodeHandlerNames4View == "" && nodeObj.handlerIds != null) {
			nextNodeHandlerNames4View = lbpm.constant.COMMONNODEHANDLERORGNULL;
		}
		html +=  (nextNodeHandlerNames4View.replace(/;/g, '; ')) ;
		return html;
	}

	//点击通过时，当前节点如果是某个节点驳回过来的时，下个节点的即将流程向应该是那个驳回过来的节点
	genRoute.generateRefuseThisNodeIdInfo = lbpm.globals.generateRefuseThisNodeIdInfo=function(
				toRefuseThisNodeId,
				toRefuseThisHandlerIds,
				toRefuseThisHandlerNames,
				nextShowHandlerId,
				nextShowHandlerName){
		var nodeObj = lbpm.globals.getNodeObj(toRefuseThisNodeId);
		var nextHandlerIds = toRefuseThisHandlerIds || "";
		var nextHandlerNames = toRefuseThisHandlerNames || "";
		if (nextHandlerIds) {
			// 审批路由类型为串行、串行时，返回驳回时第一个未处理人
			if(nextHandlerIds!="_onNode" && nextHandlerIds!="_onTheNode"){
				if(nodeObj.processType == lbpm.constant.PROCESSTYPE_SERIAL || nodeObj.processType == lbpm.constant.PROCESSTYPE_SINGLE) {
					nextHandlerIds = nextHandlerIds.split(";")[0];
					nextHandlerNames = nextHandlerNames.split(";")[0];
				}
			}else{
				if(nodeObj.processType == lbpm.constant.PROCESSTYPE_SERIAL) {
					nextHandlerIds = nodeObj.handlerIds.split(";")[0];
					nextHandlerNames = _parseNextNodeHandler(nodeObj,nodeObj.handlerIds.split(";")[0]);
				}else{
					nextHandlerIds = nodeObj.handlerIds;
					nextHandlerNames = _parseNextNodeHandler(nodeObj,nodeObj.handlerIds);
				}
			}
		} else if(nextShowHandlerId != null){
			// 不可到达
			nodeObj = lbpm.globals.getCurrentNodeObj();
			nextHandlerIds = nextShowHandlerId;
			nextHandlerNames = nextShowHandlerName;
		}
		var langNodeName = WorkFlow_getLangLabel(nodeObj.name,nodeObj["langs"],"nodeName");
		var labelText = genRoute.resolveNodeIdentifier(nodeObj.id,langNodeName);
		html = "<label id='nextNodeName[0]'><b>" + labelText + "</b></label>";
		html += "<input type='hidden' id='handlerIds[0]' name='handlerIds[0]' value='" + nextHandlerIds + "'>";
		html += "<input type='hidden' id='handlerNames[0]' name='handlerNames[0]' readonly class='inputSgl' onChange='lbpm.globals.setHandlerInfoes();' value='" + Com_HtmlEscape(nextHandlerNames) + "'>";
		html += "<label id='handlerShowNames[0]' nodeId='" + nodeObj.id + "'>(" + Com_HtmlEscape(nextHandlerNames.replace(/;/g, '; ')) + ")</label>";
		return html;
	};
	genRoute.setFutureHandlerFormulaDialog = lbpm.globals.setFutureHandlerFormulaDialog=function(idField, nameField, modelName) {
		var action = function(rtv){lbpm.globals.afterChangeFurtureHandlerInfoes(rtv,lbpm.constant.ADDRESS_SELECT_FORMULA);};
		lbpm.globals.setHandlerFormulaDialog_(idField, nameField, modelName, action);
	};
	//显示或隐藏即将流向节点选项框
	genRoute.showFutureNodeSelectedLink = lbpm.globals.showFutureNodeSelectedLink=function(futureNodeObj) {
		var index = futureNodeObj.getAttribute("index");
		$("#operationsTDContent, #nextNodeTD").find('.divselect').each(function() {
			var self = $(this);
			if (self.attr("index") == index) {
				self.show();
			} else {
				self.hide();
			}
		});
		var futureNodeLinkObjs = futureNodeObj.parentNode.parentNode.getElementsByTagName("a");
		for(var i = 0; i < futureNodeLinkObjs.length; i++){
			var futureNodeLinkObj = futureNodeLinkObjs[i];
			if(futureNodeLinkObj.getAttribute("index") != null) {
				if(futureNodeLinkObj.getAttribute("index") == futureNodeObj.getAttribute("index")){
					futureNodeLinkObj.parentNode.style.display = '';
				} else {
					futureNodeLinkObj.parentNode.style.display = 'none';
				}
			}
		}
		lbpm.events.fireListener(lbpm.constant.EVENT_SELECTEDFUTURENODE,null);
	};
	genRoute.innerHTMLGenerateNextNodeInfo = lbpm.globals.innerHTMLGenerateNextNodeInfo = function(html, dom, cb) {
		if(lbpm.globals.destroyOperations)
			lbpm.globals.destroyOperations();
		query(dom).forEach(function(node) {
			array.forEach(registry.findWidgets(node), function(widget) {
				widget.destroy && !widget._destroyed && widget.destroy();
			});
		}).html(html, {parseContent: true, onEnd: function() {
			this.inherited("onEnd", arguments);
			if (this.parseDeferred && cb) {
				this.parseDeferred.then(cb);
			}
		}});
			
	};
	//取得手工决策节点下的所有节点的信息(routeLines连接集合)
	genRoute.getNextRouteInfo = lbpm.globals.getNextRouteInfo=function(routeLines){
		if(routeLines.length==1){
			lbpm.canHideNextNodeTr = true;
		}
		var lineInfo = '';
		if(routeLines.length>0){
			array.forEach(routeLines,function(line){
				lineInfo = lineInfo + ";" + line.id;
			});
		}
		if(lineInfo.length>0)
			lineInfo = lineInfo.substring(1);
		var futureNodeValue = query("input[name='futureNodes']").val();
		var valVar="";
		if(futureNodeValue!=null && futureNodeValue!=""){//取人工分支决定后的分支条件
			valVar = "value:\'"+futureNodeValue+"\'";
		} else {
			array.forEach(routeLines,function(line){
				if(lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_MANUALBRANCH,line.startNode)) {
					if (line.startNode.defaultBranch == line.id) {
						valVar = "value:\'"+line.endNodeId+"\'";
					}
				}
			});
		}
		if(!futureNodeValue){//兼容暂存
			var checkedNodeIds = lbpm.globals.getOperationParameterJson("futureNodeId");
			if(checkedNodeIds){
				if(valVar){
					valVar += ",checkedNodeIds:\'"+checkedNodeIds+"\'";
				}else{
					valVar = "checkedNodeIds:\'"+checkedNodeIds+"\'";
				}
			}
		}
		var nextNodeObj = lbpm.globals.getNextNodeObj(lbpm.nowNodeId);
		if(nextNodeObj.nodeDescType=="autoBranchNodeDesc"){
			var calcBranchLabel = '(&nbsp;<label id="calc" style="color:#dd772c;cursor:pointer;" onclick="lbpm.globals.getAutoNextInfo()">'+lbpm.constant.COMMONNODEREPLAYCAL+'</label>)';
			var nextNodeTDTitle = query("#nextNodeTDTitle");
			if(nextNodeTDTitle.length>0 && nextNodeTDTitle[0].innerHTML!=null && nextNodeTDTitle[0].innerHTML.indexOf("label") == -1){
				nextNodeTDTitle[0].innerHTML = nextNodeTDTitle[0].innerHTML + calcBranchLabel;
			}
		}
		// 控制显示自由子流程行
		if ((nextNodeObj != null && nextNodeObj.nodeDescType == "freeSubFlowNodeDesc") || (lbpm.nodes[lbpm.nowNodeId].groupNodeId != null && lbpm.nodes[lbpm.nowNodeId].groupNodeType == "freeSubFlowNode")) {
			lbpm.globals.hiddenObject(document.getElementById("freeSubFlowNodeRow"), false);
			var nodeObj = lbpm.globals.getCurrentNodeObj();
			if (nodeObj) {
				var freeFlowNodeDIV = document.getElementById("freeSubFlowNodeDIV");
				lbpm.globals.hiddenObject(freeFlowNodeDIV, false);
			}
		}
		var currentNodeObj = lbpm.nodes[lbpm.nowNodeId];
		var isLastHandler = lbpm.globals.isLastHandler();
		var html = "";
		var validateHtml = "validate:\'futureNodeSelected required\',required:true";
		if(routeLines.length>1
			&& nextNodeObj.nodeDescType == "manualBranchNodeDesc"
			&& currentNodeObj.processType == "2"
			&& !isLastHandler){
			lbpm.noValidateFutureNode = true;
			html += "<div style='color:#999999;margin-bottom: 5px;' id='futureNodeTip'>"+lbpm.workitem.constant.FUTURENODESTIP+"</div>";
			validateHtml = "";
		}
		//判断是否是并行分支使用了自定义启动，是自定义启动时使用复选框控件
		if(nextNodeObj.nodeDescType=="splitNodeDesc"&&nextNodeObj.splitType&&nextNodeObj.splitType=="custom"){
			return html+'<div id="sys_lbpmservice_mobile_workitem_FutureNodesChekBoxGroup" data-dojo-type="sys/lbpmservice/mobile/workitem/FutureNodesChekBoxGroup" ' +
			'class="lbpmNextRouteInfoRow no_border_bottom dingNodeContent" data-dojo-props="'+(valVar==''?'':(valVar + "," ))+'name:\'futureNodes\', routeLineInfo:\'' + lineInfo+'\',subject:\'\','+validateHtml+',orient:\'vertical\'"></div>';
		}
		else{
			//如果下个节点是人工决策，并且当前节点的流转方式是会审/会签，则需要进入判断环节
			//若不是最后一个处理人，则即将流向显示，但是不可以进行编辑
			//若是最后一个处理人，则以下逻辑正常执行
			var isEditStatus = true;
			if(nextNodeObj.nodeDescType=="manualBranchNodeDesc" && currentNodeObj.processType == "2" && !isLastHandler){
				isEditStatus = false;
			}
			if(nextNodeObj.XMLNODENAME != "splitNode" && lineInfo && lineInfo.split(";").length > 1){
				return html+'<div data-dojo-type="sys/lbpmservice/mobile/workitem/FutureNodes" ' +
				'class="lbpmNextRouteInfoRow no_border_bottom dingNodeContent" data-dojo-props="isEditStatus:'+isEditStatus+','+(valVar==''?'':(valVar + "," ))+'name:\'futureNodes\', routeLineInfo:\'' + lineInfo+ '\',subject:\''+lbpm.constant.CHKNEXTNODENOTNULL+'\','+validateHtml+'"></div>';
			}else{
				return html+'<div data-dojo-type="sys/lbpmservice/mobile/workitem/FutureNodes" ' +
				'class="lbpmNextRouteInfoRow no_border_bottom dingNodeContent" data-dojo-props="isEditStatus:'+isEditStatus+','+(valVar==''?'':(valVar + "," ))+'name:\'futureNodes\', routeLineInfo:\'' + lineInfo+ '\',subject:\''+lbpm.constant.CHKNEXTNODENOTNULL+'\'"></div>';
			}
		}
//		return '<div data-dojo-type="sys/lbpmservice/mobile/workitem/FutureNodes" ' + 
//		'class="lbpmNextRouteInfoRow" data-dojo-props="'+(valVar==''?'':(valVar + "," ))+'name:\'futureNodes\', routeLineInfo:\'' + lineInfo+ '\'"></div>';
	};
	//获取当前节点选择的人工决策节点（[{NodeName:N3, NextRoute:N4},{NodeName:N9, NextRoute:N11}]）
	genRoute.getSelectedFurtureNode = lbpm.globals.getSelectedFurtureNode=function(){
		var furtureNodeSelect = new Array();
		$("input[name='futureNode']:checked").each(function(index, input){
	    	var json = {};
	    	input = $(input);
			json.NodeName = input.attr('manualBranchNodeId');
			json.NextRoute = input.val();
			furtureNodeSelect.push(json);
	    });
		return furtureNodeSelect;
	};
	//人工决策节点设置即将流向处理人
	genRoute.setFurtureHandlerInfoes = lbpm.globals.setFurtureHandlerInfoes=function(rtv,handlerSelectType){
		var isNull = (rtv == null);
		var handlerIdsObj;
		var handlerNamesObj;
		var handlerShowNames;
		var nextNodeId; 
		var futureNodeObj=$("input[name='futureNode']:checked");
		var futureIndex=null;
		if(futureNodeObj.length>0){
			nextNodeId = futureNodeObj[0].value;
			futureIndex=futureNodeObj[0].getAttribute("index");
		}else{	
			var currentNodeObj=lbpm.globals.getCurrentNodeObj();
			var nextNodeObj=lbpm.globals.getNextNodeObj(currentNodeObj.id);
			nextNodeId=nextNodeObj.id;
			futureIndex="0";
		}
		handlerIdsObj = document.getElementsByName("handlerIds[" + futureIndex + "]")[0];
		handlerNamesObj = document.getElementsByName("handlerNames[" + futureIndex + "]")[0];
		handlerShowNames = document.getElementById("handlerShowNames[" + futureIndex + "]");
		if (isNull) {
				handlerIdsObj.value = handlerIdsObj.getAttribute("defaultValue");
				handlerNamesObj.value = handlerNamesObj.getAttribute("defaultValue");
			return;
		}
		if(handlerSelectType==lbpm.constant.ADDRESS_SELECT_FORMULA){
			handlerIdsObj.setAttribute("isFormula", "true");
		}
		else{
			handlerIdsObj.setAttribute("isFormula", "false");
		}
		handlerIdsObj.setAttribute("defaultValue", handlerIdsObj.value);
		handlerNamesObj.setAttribute("defaultValue", handlerNamesObj.value);
		handlerShowNames.innerHTML = "(" + handlerNamesObj.value + ")";
		var operatorInfo = lbpm.globals.analysisProcessorInfoToObject();
		if(operatorInfo == null){
			return;
		}	
		var currentNodeId = lbpm.nowNodeId; 
		//返回json对象
		var rtnNodesMapJSON= new Array();
		var nodeObj=new Object();
		nodeObj.id=nextNodeId;
		nodeObj.handlerIds=handlerIdsObj.value;
		nodeObj.handlerNames=handlerNamesObj.value;
		if(handlerSelectType!=null){
			nodeObj.handlerSelectType=handlerSelectType;
		}
		rtnNodesMapJSON.push(nodeObj);
		var param={};
		param.nodeInfos=rtnNodesMapJSON;
		lbpm.events.fireListener(lbpm.constant.EVENT_MODIFYNODEATTRIBUTE,param);
	};

	//人工选择节点修改处理人后设置即将流向处理人 limh 2011年3月30日
	genRoute.afterChangeFurtureHandlerInfoes = lbpm.globals.afterChangeFurtureHandlerInfoes=function(rtv,handlerSelectType){
		var handlerIdsObj ;
		var handlerNamesObj ;
		var handlerShowNames;
		if(rtv){
			var rtvArray = rtv.GetHashMapArray();
			if(rtvArray){
				var futureNodeObj=$("input[name='futureNode']:checked");
				var futureIndex=null;
				if(futureNodeObj.length>0){
					futureIndex=futureNodeObj[0].getAttribute("index");
				}else{
					futureIndex="0";
				}
				handlerIdsObj = document.getElementsByName("handlerIds[" + futureIndex + "]")[0];
				handlerNamesObj = document.getElementsByName("handlerNames[" + futureIndex + "]")[0];
				handlerShowNames = document.getElementById("handlerShowNames[" + futureIndex + "]");
				var idValue = "";
				var nameValue = "";
				for(var i=0;i<rtvArray.length;i++){
					idValue += ";"+rtvArray[i]["id"];
					nameValue += ";"+rtvArray[i]["name"];
				}
				handlerIdsObj.value = idValue.substring(1);
				handlerNamesObj.value =  nameValue.substring(1);
				lbpm.globals.setFurtureHandlerInfoes(rtv,handlerSelectType);
			}
		}
	};
	genRoute.isRemoveNodeIdentifier = function(){
		var isRemoveNodeIdentifier = false;
		if (lbpm && lbpm.settingInfo){
			if ((lbpm.settingInfo.isHideNodeIdentifier === "false" && lbpm.settingInfo.hideNodeIdentifierType === "false")&&
				lbpm.settingInfo.isRemoveNodeIdentifier === "true"){
				isRemoveNodeIdentifier = true;
			}else if (lbpm.settingInfo.isHideNodeIdentifier === "true" && lbpm.settingInfo.hideNodeIdentifierType === "isRemoveNodeIdentifier"){
				isRemoveNodeIdentifier = true;
			}
		}
		return isRemoveNodeIdentifier;
	};
	genRoute.isHideAllNodeIdentifier = function(){
		var isHideAllNodeIdentifier = false;
		if (lbpm && lbpm.settingInfo){
			if (lbpm.settingInfo.isHideNodeIdentifier === "true" && lbpm.settingInfo.hideNodeIdentifierType === "isHideAllNodeIdentifier"){
				isHideAllNodeIdentifier = true;
			}
		}
		return isHideAllNodeIdentifier;
	};
	
	genRoute.resolveNodeIdentifier = function(id,langNodeName){
		var text = "";
		if (genRoute.isRemoveNodeIdentifier() || genRoute.isHideAllNodeIdentifier()){
			text = langNodeName;
		}else{
			text = id + "." + langNodeName;
		}
		return text;
	};
	
	//---------------------即席子流程@即将流向---------------------
	genRoute.getNextAdHocSubFlowRouteInfo = lbpm.globals.getNextAdHocSubFlowRouteInfo = function() {
		lbpm.canHideNextNodeTr = false;
		if (lbpm.adHocSubFlowNodeInfo == null || lbpm.adHocSubFlowNodeInfo[lbpm.nowAdHocSubFlowNodeId] == null) {
			lbpm.globals.initAdHocSubFlowInfo();
		}
		lbpm.adHocRoutes = lbpm.adHocSubFlowNodeInfo[lbpm.nowAdHocSubFlowNodeId];
		//获取选中的值
		var nextAdHocRouteId = query("input[name='nextAdHocRouteId']:checked")[0] ? query("input[name='nextAdHocRouteId']:checked")[0].value : null;
		var valStr = nextAdHocRouteId ? "value:\'"+nextAdHocRouteId+"\'," : "";
		return '<div data-dojo-type="sys/lbpmservice/mobile/workitem/NextAdHocRoutes" data-dojo-props="'+valStr+'validate:\'adHocSubFlowNodeSelected required\',required:true,subject:\''+lbpm.constant.CHKNEXTNODENOTNULL+'\'" class="lbpmNextRouteInfoRow" style="border-bottom:0"></div>';
	};

	// 初始化即席子流程节点对象信息（数据来源于节点配置时的adHocSubFlowData)
	lbpm.globals.initAdHocSubFlowInfo = function() {
		if (lbpm.adHocSubFlowNodeInfo == null) {
			//即席子流程节点的信息对象，以即席子流程节点的ID作为key来存放
			//对应的即席子流程的各环节信息(包含环节首节点ID以及环节内的子节点配置信息,默认以环节首节点ID作为环节的标识)
			//以及对应的即席子流程节点的全部子节点的配置信息
			lbpm.adHocSubFlowNodeInfo = new Object(); 
		}
		var adHocRoutes = new Array();
		var adHocSubNodes = new Object();
		var adHocSubLines = new Object();
		lbpm.adHocSubFlowNodeInfo[lbpm.nowAdHocSubFlowNodeId] = adHocRoutes;
		lbpm.adHocRoutes = lbpm.adHocSubFlowNodeInfo[lbpm.nowAdHocSubFlowNodeId];
		var adHocSubFlowNode = lbpm.nodes[lbpm.nowAdHocSubFlowNodeId];
		var data = adHocSubFlowNode["adHocSubFlowData"];
		if(data){
			var adHocSubFlowData = WorkFlow_LoadXMLData(data);
			for(var i=0; i<adHocSubFlowData.nodes.length; i++){
				var nodeObj=adHocSubFlowData.nodes[i];
				adHocSubNodes[nodeObj.id] = {};
				adHocSubNodes[nodeObj.id].id = nodeObj.id;
				adHocSubNodes[nodeObj.id].data = nodeObj;
				adHocSubNodes[nodeObj.id].startLines=[];
				adHocSubNodes[nodeObj.id].endLines=[];
			}
			lbpm.adHocSubFlowNodeInfo[lbpm.nowAdHocSubFlowNodeId].adHocSubNodes = adHocSubNodes;
			for(i=0; i<adHocSubFlowData.lines.length; i++){
				var lineObj=adHocSubFlowData.lines[i];
				adHocSubLines[lineObj.id] = {};
				adHocSubLines[lineObj.id].id = lineObj.id;
				adHocSubLines[lineObj.id].data = lineObj;
				adHocSubLines[lineObj.id].startNode = adHocSubNodes[lineObj.startNodeId];
				adHocSubLines[lineObj.id].endNode = adHocSubNodes[lineObj.endNodeId];
				(adHocSubNodes[lineObj.startNodeId].endLines).push(adHocSubLines[lineObj.id]);
				(adHocSubNodes[lineObj.endNodeId].startLines).push(adHocSubLines[lineObj.id]);
			}
			var routeNextAdHocSubNode = function(nodeObj,adHocRoute){
				adHocRoute.subNodes.push(nodeObj);
				for (var j=0;j<nodeObj.endLines.length;j++) {
					adHocRoute.subLines.push(nodeObj.endLines[j]);
					routeNextAdHocSubNode(nodeObj.endLines[j].endNode,adHocRoute);
				}
			};
			// 分组(adHocRoute)
			$.each(adHocSubNodes, function(index, nodeObj) {
				// 没有流入的节点就是每个adHocRoute的首节点，从首节点的流出往下遍历就能找出每个adHocRoute的全部子节点
				if (nodeObj.startLines.length == 0) {
					var adHocRoute = new Array();
					adHocRoute.subNodes = new Array();
					adHocRoute.subLines = new Array();
					adHocRoute.startNodeId = nodeObj.data.id;
					routeNextAdHocSubNode(nodeObj,adHocRoute);
					adHocRoutes.push(adHocRoute);
				}
			});
		}
	};
	
	return genRoute;
});


