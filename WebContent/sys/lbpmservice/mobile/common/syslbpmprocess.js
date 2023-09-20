define([
  "./workflow",
  "./syslbpmprocess_nodes_filter",
  "./syslbpmprocess_submit",
  "dojo/dom",
  "dojo/query",
  "dojo/request",
  "mui/dialog/Confirm",
  "mui/dialog/Dialog",
  "dojo/dom-construct",
  "mui/i18n/i18n!sys-mobile",
  "mui/i18n/i18n!sys-lbpmservice:lbpmservice.saveDraft",
  "dojo/dom-style",
  "dojo/_base/lang",
  "mui/util",
  "dojo/json"
], function(workFlow, nodeFilter, submitFlow, dom, query, request, Confirm, Dialog,domConstruct, Msg, Msg1, domStyle, lang, util,JSON) {
  var process = {}

  var Formula_GetVarInfoByModelName = function(modelName) {
    return new KMSSData()
      .AddBeanData("sysFormulaDictVarTree&modelName=" + modelName)
      .GetHashMapArray()
  }

  process.getfdUsageContent = lbpm.operations.getfdUsageContent = function() {
    return $("[name=fdUsageContent]")[0]
  }

  process.initialContextParams = lbpm.globals.initialContextParams = function() {
    lbpm.globals.getWfBusinessFormModelName()
    lbpm.globals.getWfBusinessFormModelId()
    lbpm.globals.getWfBusinessFormFdKey()
    lbpm.globals.getWfBusinessFormDocStatus()

    lbpm.handlerId = $("[name='sysWfBusinessForm.fdCurHanderId']")[0].value
    lbpm.draftorName = $("[name='sysWfBusinessForm.fdDraftorName']")[0].value
  }

  //解析当前节点XML成对象
  process.parseProcessorObj = lbpm.globals.parseProcessorObj = function() {
    var curNodeXMLObj = window.WorkFlow_LoadXMLData(
      $("input[name='sysWfBusinessForm.fdCurNodeXML']")[0].value
    )
    if (!curNodeXMLObj || (!curNodeXMLObj.nodes && !curNodeXMLObj.tasks)) return //流程结束
    //当前节点的详版XML
    if (curNodeXMLObj.nodes) {
      for (var i = 0, size = curNodeXMLObj.nodes.length; i < size; i++) {
        var node = curNodeXMLObj.nodes[i]
        for (o in node) {
          if (lbpm.nodes[node.id]) {
            lbpm.nodes[node.id][o] = node[o]
          }
          if (lbpm.nodesInit[node.id]) {
            lbpm.nodesInit[node.id][o] = node[o]
          }
        }
        if (lbpm.isSubForm) {
          if (!lbpm.nodes[node.id]["subFormMobileId"]) {
            lbpm.nodes[node.id]["subFormMobileId"] = "default"
            lbpm.nodesInit[node.id]["subFormMobileId"] = "default"
          }
        }
      }
    }
    lbpm.drafterInfoObj = lbpm.globals.getDrafterInfoObj(curNodeXMLObj) //当前用户以起草人身份所拥有的信息
    lbpm.authorityInfoObj = lbpm.globals.getAuthorityInfoObj(curNodeXMLObj) //当前用户以特权人身份所拥有的信息
    lbpm.historyhandlerInfoObj = lbpm.globals.getHistoryhandlerInfoObj(
      curNodeXMLObj
    ) //当前用户以已处理人身份所拥有的信息
    lbpm.branchAdminInfoObj = lbpm.globals.getBranchAdminInfoObj(curNodeXMLObj);//当前用户以分支特权人身份所拥有的信息
    var processorInfoObj = lbpm.globals.getProcessorInfoObj(curNodeXMLObj)
    //processorInfoObj，打开特权人窗口或者起草人窗口处理文档时，此对象跟authorityInfoObj或者drafterInfoObj对象一样
    lbpm.processorInfoObj = processorInfoObj //当前用户以处理人身份所拥有的信息，如所属哪个工作项
    //当有多个工作项时，也是默认取第一个
    if (processorInfoObj) {
      var selectedIndex = 0
      if (
        processorInfoObj.length > 1 &&
        lbpm.defaultTaskId &&
        lbpm.defaultTaskId != ""
      ) {
        for (var i = 0; i < processorInfoObj.length; i++) {
          if (processorInfoObj[i].id == lbpm.defaultTaskId) {
            selectedIndex = i
            break
          }
        }
      }
      lbpm.nowProcessorInfoObj = processorInfoObj[selectedIndex]
      if (lbpm.nowProcessorInfoObj) {
        lbpm.nowNodeId = lbpm.nowProcessorInfoObj.nodeId
        if (lbpm.isFreeFlow) {
          //自由流
          if (lbpm.nowNodeId == "N2") {
            lbpm.nowNodeFlowPopedom = "2"
          } else {
            lbpm.nowNodeFlowPopedom = lbpm.globals.getNodeObj(
              lbpm.nowNodeId
            ).flowPopedom
          }
        }
      }
    }
  }

  //解析当前多表单信息XML成对象
  process.parseSubFormInfoObj = lbpm.globals.parseSubFormInfoObj = function() {
    if (lbpm.isSubForm) {
      var subFormXMLObj = WorkFlow_LoadXMLData(
        $("input[name='sysWfBusinessForm.fdSubFormXML']")[0].value
      )
      if (subFormXMLObj.subforms) {
        var subformsInfoObj = $.extend(true, [], subFormXMLObj.subforms)
        lbpm.subFormInfoObj = subformsInfoObj //当前用户所拥有的子表单信息集
      }
    }
  }

  process.normalSorter = lbpm.globals.normalSorter = function(node1, node2) {
    if (node1.y == node2.y) return node1.x - node2.x
    return node1.y - node2.y
  }
  //内部方法外部切勿调用 #作者：曹映辉 #日期：2013年5月27日
  lbpm.globals._levelCalc = function(startNodeId, level, allNodes) {
    if (!allNodes) {
      allNodes = []
    }
    if (!level) {
      level = 0
    }
    level++
    var nodeObj = lbpm.nodes[startNodeId]
    if (!nodeObj) {
      return
    }
    if (nodeObj.level < level) {
      nodeObj.level = level
    }
    allNodes.push(nodeObj)
    var nextNodes = lbpm.globals.getNextNodeObjs(startNodeId)
    for (var i = 0; i < nextNodes.length; i++) {
      //防止循环节点 出现死循环
      var isIn = false
      var nodeDesc = lbpm.nodedescs[nextNodes[i].nodeDescType]
      for (var j = 0; j < allNodes.length; j++) {
        //列表中已经存在的分支节点 后 表示已经形成了环形
        if (
          allNodes[j].id == nextNodes[i].id &&
          nodeDesc.isBranch(nextNodes[i])
        ) {
          isIn = true
          break
        }
      }
      if (isIn) {
        continue
      }
      lbpm.globals._levelCalc(nextNodes[i].id, level, allNodes)
    }
  }
  //修改为层级排序方式 #作者：曹映辉 #日期：2013年5月27日
  process.setNodeLevel = lbpm.globals.setNodeLevel = function() {
    lbpm.globals._levelCalc("N1")
  }

  process.levelSorter = lbpm.globals.levelSorter = function(node1, node2) {
    return node1.level - node2.level
  }

  Array.prototype.contains = function(arr) {
    for (var i = 0; i < this.length; i++) {
      if (this[i] == arr) {
        return true
      }
    }
    return false
  }

  //系统全局的排序函数
  process.getNodeSorter = lbpm.globals.getNodeSorter = function() {
    //routingSortNodes=lbpm.globals.getSortNodes();
    return lbpm.globals.levelSorter
  }

  /*
   * 解析XML成lbpm对象nnn
   */
  process.parseXMLObj = lbpm.globals.parseXMLObj = function() {
    //解析流程的XML成对象
    var processData = WorkFlow_LoadXMLData(
      document.getElementById("sysWfBusinessForm.fdFlowContent").value
    )
    var processDataInit = WorkFlow_LoadXMLData(
      document.getElementById("sysWfBusinessForm.fdFlowContent").value
    )
    if (!processData) return

    //节点排序
    var processNodes = processData.nodes
    var processNodesInit = processDataInit.nodes
    //此时流程图未加载完成，不能使用路由排序，只能使用位置排序 #作者：曹映辉 #日期：2013年5月21日
    processNodes.sort(lbpm.globals.normalSorter)
    processNodesInit.sort(lbpm.globals.normalSorter)
    //流程图需要的属性
    for (o in processData) {
      if (o != "nodes" && o != "lines") {
        lbpm.flowcharts[o] = processData[o]
      }
    }
    //去掉被删除的节点与连线
    var idNodesArray = new Array(),
      idLinesArray = new Array()
    for (var i = 0, j = processNodes.length; i < j; i++) {
      idNodesArray[i] = processNodes[i].id
    }
    for (i = 0; i < processData.lines.length; i++) {
      idLinesArray[i] = processData.lines[i].id
    }
    for (var o in lbpm.nodes) {
      if (!idNodesArray.contains(o)) {
        delete lbpm.nodes[o]
      }
    }
    for (var o in lbpm.lines) {
      if (!idLinesArray.contains(o)) {
        delete lbpm.lines[o]
      }
    }

    //节点对象
    for (var i = 0, j = processNodes.length; i < j; i++) {
      var nodeObj = processNodes[i]
      lbpm.nodes[nodeObj.id] = nodeObj
      lbpm.nodes[nodeObj.id].startLines = []
      lbpm.nodes[nodeObj.id].endLines = []
      lbpm.nodes[nodeObj.id].Status = 1
      lbpm.nodes[nodeObj.id].nodeDescType =
        lbpm.nodeDescMap[nodeObj.XMLNODENAME]
      lbpm.nodes[nodeObj.id].level = 0 //设置默认层级，用于节点排序
    }
    for (var i = 0, j = processNodesInit.length; i < j; i++) {
      var nodeObjInit = processNodesInit[i]
      lbpm.nodesInit[nodeObjInit.id] = nodeObjInit
    }
    //连线对象
    for (i = 0; i < processData.lines.length; i++) {
      var lineObj = processData.lines[i]
      lbpm.lines[lineObj.id] = lineObj
      //连线的起始指向的节点
      lbpm.lines[lineObj.id].startNode = lbpm.nodes[lineObj.startNodeId]
      //连线的结束指向的节点
      lbpm.lines[lineObj.id].endNode = lbpm.nodes[lineObj.endNodeId]
      //节点的结束连线
      lbpm.nodes[lineObj.startNodeId].endLines.push(lineObj)
      //节点的开始连线
      lbpm.nodes[lineObj.endNodeId].startLines.push(lineObj)
    }
    //更新历史节点属性
    var processData = window.WorkFlow_LoadXMLData(
      document.getElementById("sysWfBusinessForm.fdTranProcessXML").value
    )
    for (i = 0; i < processData.historyNodes.length; i++) {
      var hNodeInfo = processData.historyNodes[i]
      var hNode = lbpm.nodes[hNodeInfo.id]
      if (hNode == null) {
        continue
      }
      if (lbpm.isFreeFlow) {
        // 自由流时，历史节点的Status值就取2，不根据routeType特殊处理，和流程图内的显示保持一致
        hNode.Status = 2
      } else {
        if (hNodeInfo.routeType == "BACK" || hNodeInfo.routeType == "JUMP") {
          hNode.Status = 1
        } else {
          hNode.Status = 2
        }
      }
      hNode.targetId = hNodeInfo.targetId
    }
    //设置节点状态是否为当前节点
    for (i = 0; i < processData.runningNodes.length; i++) {
        lbpm.nodes[processData.runningNodes[i].id].Status = 3;
        //自由流，当前节点后续节点状态全部置为1
		if (lbpm.isFreeFlow) {
			var nodes = lbpm.globals.getNextNodes(lbpm.nodes[processData.runningNodes[i].id],[]);
			for(var j = 0;j<nodes.length;j++){
				nodes[j].Status=1;
			}
		}
    }
    lbpm.notifyType = lbpm.flowcharts.notifyType
    //初始化流程说明
    if (lbpm.flowcharts && lbpm.flowcharts.description) {
      var changedText = Com_HtmlEscape(lbpm.flowcharts.description)
      if (changedText) {
        // &已经被Com_HtmlEscape转义了
        changedText = changedText.replace(/&amp;#xD;&amp;#xA;/g, "<br />")
        changedText = changedText.replace(/&amp;#xD;/g, "")
        changedText = changedText.replace(/&amp;#xA;/g, "<br />")
      }
      $("#fdFlowDescription").html(changedText)
    }
  }


  process.getNextNodes = lbpm.globals.getNextNodes = function(node, nodes) {
  	var nexts = lbpm.globals.getNextNodeObjs(node.id);
  	for (var i = 0; i < nexts.length; i ++) {
  		var nNode = nexts[i];
  		if (lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_END,nNode)) {
  			continue;
  		}
  		if (lbpm.globals.containNode(nodes, nNode)) {
  			continue;
  		}
  		nodes.push(nNode);
  		lbpm.globals.getNextNodes(nNode, nodes);
  	}
  	return nodes;
  };

  process.containNode = lbpm.globals.containNode = function(nodes, node) {
  	for (var n = 0; n < nodes.length; n ++) {
  		if (node.id == nodes[n].id) {
  			return true;
  		}
  	}
  	return false;
  };
  
  process.objectToJSONString = lbpm.globals.objectToJSONString = function(
    value,
    replacer,
    space
  ) {
    return JSON.stringify(value, replacer, space)
  }

  /**
   * @param node 节点对象
   * 	JS文件路径
   * 功能：根据节点对象获取到节点的类型，用得判断节点的类型的地方
   */
  process.checkNodeType = lbpm.globals.checkNodeType = function(
    nodeType,
    node
  ) {
    if (!node) return false
    var constObj = lbpm.constant
    var nodeDescObj = lbpm.nodedescs[node.nodeDescType]
    switch (nodeType) {
      case constObj.NODETYPE_HANDLER: // 是否是有处理人类型的节点
        return nodeDescObj["isHandler"](node)
        break
      case constObj.NODETYPE_CANREFUSE: //是否可以被驳回（是人工处理节点，不是自动运行，不是分支，且uniqueMark为空）
        if (
          nodeDescObj["isHandler"](node) &&
          !nodeDescObj["isAutomaticRun"](node) &&
          !nodeDescObj["isBranch"](node)
        ) {
          return true
        } else {
          return false
        }
        break
      case constObj.NODETYPE_SEND: //是否是抄送节点(是人工，是自动运行，不是分支，不是子流程，不是并发，uniqueMark为空)
        if (
          nodeDescObj["isHandler"](node) &&
          nodeDescObj["isAutomaticRun"](node) &&
          !nodeDescObj["isBranch"](node) &&
          !nodeDescObj["isSubProcess"](node) &&
          !nodeDescObj["isConcurrent"](node) &&
          nodeDescObj["uniqueMark"](node) == null
        ) {
          return true
        } else {
          return false
        }
        break
      case constObj.NODETYPE_SPLIT: //并发分支开始（不是人工，是自动运行，是分支，不是子流程，是并发，uniqueMark为空）
        if (
          !nodeDescObj["isHandler"](node) &&
          nodeDescObj["isAutomaticRun"](node) &&
          nodeDescObj["isBranch"](node) &&
          !nodeDescObj["isSubProcess"](node) &&
          nodeDescObj["isConcurrent"](node) &&
          nodeDescObj["uniqueMark"](node) == null
        ) {
          return true
        } else {
          return false
        }
        break
      case constObj.NODETYPE_REVIEW: //审批节点类型（是人工，不是自动运行，不是分支，不是子流程，不是并发，uniqueMark为空）
        if (
          nodeDescObj["isHandler"](node) &&
          !nodeDescObj["isAutomaticRun"](node) &&
          !nodeDescObj["isBranch"](node) &&
          !nodeDescObj["isSubProcess"](node) &&
          !nodeDescObj["isConcurrent"](node) &&
          nodeDescObj["uniqueMark"](node) == null
        ) {
          return true
        } else {
          return false
        }
        break
      case constObj.NODETYPE_STARTSUBPROCESS: //启动子流程（不是人工，是自动运行，不是分支，是子流程，是并发，uniqueMark为空）
        if (
          !nodeDescObj["isHandler"](node) &&
          nodeDescObj["isAutomaticRun"](node) &&
          !nodeDescObj["isBranch"](node) &&
          nodeDescObj["isSubProcess"](node) &&
          nodeDescObj["isConcurrent"](node) &&
          nodeDescObj["uniqueMark"](node) == null
        ) {
          return true
        } else {
          return false
        }
        break
      case constObj.NODETYPE_RECOVERSUBPROCESS: //结束子流程（不是人工，是自动运行，不是分支，是子流程，不是并发，uniqueMark为空）
        if (
          !nodeDescObj["isHandler"](node) &&
          nodeDescObj["isAutomaticRun"](node) &&
          !nodeDescObj["isBranch"](node) &&
          nodeDescObj["isSubProcess"](node) &&
          !nodeDescObj["isConcurrent"](node) &&
          nodeDescObj["uniqueMark"](node) == null
        ) {
          return true
        } else {
          return false
        }
        break
      case constObj.NODETYPE_AUTOBRANCH: //自动分支(不是人工，是自动分支，是分支，不是子流程，不是并发，uniqueMark为空)
        if (
          !nodeDescObj["isHandler"](node) &&
          nodeDescObj["isAutomaticRun"](node) &&
          nodeDescObj["isBranch"](node) &&
          !nodeDescObj["isSubProcess"](node) &&
          !nodeDescObj["isConcurrent"](node) &&
          nodeDescObj["uniqueMark"](node) == null
        ) {
          return true
        } else {
          return false
        }
        break
      case constObj.NODETYPE_MANUALBRANCH: //人工决策(是人工，不是自动运行，是分支，不是子流程，不是并发，uniqueMark为空)
        if (
          nodeDescObj["isHandler"](node) &&
          !nodeDescObj["isAutomaticRun"](node) &&
          nodeDescObj["isBranch"](node) &&
          !nodeDescObj["isSubProcess"](node) &&
          !nodeDescObj["isConcurrent"](node) &&
          nodeDescObj["uniqueMark"](node) == null
        ) {
          return true
        } else {
          return false
        }
        break
      case constObj.NODETYPE_ROBOT: //机器人(不是人工，是自动运行，不是分支，不是子流程，不是并发，uniqueMark为空)
        if (
          !nodeDescObj["isHandler"](node) &&
          nodeDescObj["isAutomaticRun"](node) &&
          !nodeDescObj["isBranch"](node) &&
          !nodeDescObj["isSubProcess"](node) &&
          !nodeDescObj["isConcurrent"](node) &&
          nodeDescObj["uniqueMark"](node) == null
        ) {
          return true
        } else {
          return false
        }
        break
      default:
        //其他固定写死的节点（签字节点、并发分支结束节点、起草节点、开始节点、结束节点）
        if (node.nodeDescType == nodeType) return true
        break
    }
    return false
  }

  process.validateControlItem = lbpm.globals.validateControlItem = function() {
    var saveDraftButton = document.getElementById("saveDraftButton")
    var updateButton = document.getElementById("updateButton")

    var operationItemsRow = document.getElementById("operationItemsRow")
    var operationMethodsRow = document.getElementById("operationMethodsRow")
    var descriptionRow = document.getElementById("descriptionRow")
    //显示隐藏签章
    var showSignature = document.getElementById("showSignature")
    var notifyTypeRow = document.getElementById("notifyTypeRow")
    var attachmentRow = document.getElementById("attachmentRow")
    var notifyOptionTR = document.getElementById("notifyOptionTR")
    var checkChangeFlowTR = document.getElementById("checkChangeFlowTR")
    var oprNames = lbpm.globals.getOperationParameterJson("operations")

    var commonUsagesRow = document.getElementById("commonUsagesRow")
    var signaturePicUL = document.getElementById("signaturePicUL")
    var signatureTitleDiv = document.getElementById("signatureTitleDiv")
    var commonUsagesDiv = document.getElementById("commonUsagesDiv")

    //移动端不显示操作
    var notifyLevelRow = document.getElementById("notifyLevelRow")
    lbpm.globals.hiddenObject(operationMethodsRow, true)
    if (oprNames == null || oprNames.length == 0) {
      //隐藏操作行
      lbpm.globals.hiddenObject(operationItemsRow, true)
      if (showSignature) {
        lbpm.globals.hiddenObject(showSignature, true)
      }
      lbpm.globals.hiddenObject(descriptionRow, true)
      lbpm.globals.hiddenObject(notifyTypeRow, true)
      lbpm.globals.hiddenObject(attachmentRow, true)
      lbpm.globals.hiddenObject(notifyOptionTR, true)
      lbpm.globals.hiddenObject(checkChangeFlowTR, true)
      lbpm.globals.hiddenObject(saveDraftButton, true)
      lbpm.globals.hiddenObject(updateButton, true)

      lbpm.globals.hiddenObject(notifyLevelRow, true)
    } else {
      if (showSignature) {
        if (typeof seajs != "undefined") {
          lbpm.globals.hiddenObject(showSignature, false)
        } else {
          lbpm.globals.hiddenObject(showSignature, true)
        }
      }
      // #60715 起草节点隐藏审批意见框
      if (
        lbpm.nowNodeId == "N2" &&
        Lbpm_SettingInfo.isDraftNodeDisplayOpinion == "true"
      ) {
        if (
          lbpm.constant.DOCSTATUS != "11" &&
          Lbpm_SettingInfo.isNewPageAndDraftsManRecallPage == "true"
        ) {
          lbpm.globals.hiddenObject(commonUsagesRow, true)
          lbpm.globals.hiddenObject(signaturePicUL, true)
          lbpm.globals.hiddenObject(commonUsagesDiv, true)
          lbpm.globals.hiddenObject(signatureTitleDiv, true)
        }
        if (
          lbpm.constant.DOCSTATUS == "11" &&
          Lbpm_SettingInfo.isRejectPage == "true"
        ) {
          lbpm.globals.hiddenObject(commonUsagesRow, true)
          lbpm.globals.hiddenObject(signaturePicUL, true)
          lbpm.globals.hiddenObject(commonUsagesDiv, true)
          lbpm.globals.hiddenObject(signatureTitleDiv, true)
        }
      } else {
        lbpm.globals.hiddenObject(commonUsagesRow, false)
      }
      lbpm.globals.hiddenObject(notifyTypeRow, false)
      lbpm.globals.hiddenObject(attachmentRow, false)
      lbpm.globals.hiddenObject(notifyOptionTR, false)
      lbpm.globals.hiddenObject(saveDraftButton, false)
      lbpm.globals.hiddenObject(updateButton, false)
      lbpm.globals.hiddenObject(checkChangeFlowTR, false)

      lbpm.globals.hiddenObject(notifyLevelRow, false)
      if (
        Lbpm_SettingInfo &&
        Lbpm_SettingInfo.isNotifyLevelOptional == "false"
      ) {
        lbpm.globals.hiddenObject(notifyLevelRow, true)
      }
    }
    lbpm.globals.controlProcessStatusRow()

    if (window.OptBar_Refresh) {
      OptBar_Refresh(true)
    }
    if (!lbpm.nowProcessorInfoObj) {
      $("#lbpm_highLevelTab").each(function() {
        lbpm.globals.setNotionPopedomTRHidden(this)
      })
    }

    // 控制自由流相关行
    if (lbpm.isFreeFlow) {
      lbpm.globals.hiddenObject(document.getElementById("freeflowRow"), false)
      var nodeObj = lbpm.globals.getCurrentNodeObj()
      if (nodeObj) {
        var freeFlowNodeDIV = document.getElementById("freeFlowNodeDIV")
        lbpm.globals.hiddenObject(freeFlowNodeDIV, false)
      }
    }

    //当前处理人行-开关控制显示
    if (Lbpm_SettingInfo.isShowCurrentHandlers == "false") {
      var currentHandlersRow = $("#currentHandlersRow")[0]
      lbpm.globals.hiddenObject(currentHandlersRow, true)
    }
  }

  //自由流私密意见初始化
  process.initPrivateOpinion = lbpm.globals.initPrivateOpinion = function() {
    require([
      "dojo/ready",
      "dijit/registry",
      "dojo/query",
      "dojo/topic",
      "dojo/dom-style"
    ], function(ready, registry, query, topic, domStyle) {
      ready(function() {
        var privateOpinionTr = query("#privateOpinionTr")
        if (privateOpinionTr.length > 0) {
          var commonUsagesDiv = document.getElementById("commonUsagesDiv")
          if (
            !lbpm.isFreeFlow ||
            !commonUsagesDiv ||
            commonUsagesDiv.style.display == "none" ||
            Lbpm_SettingInfo.isPrivateOpinion == "false"
          ) {
            domStyle.set(privateOpinionTr[0], {display: "none"})
          } else {
            var switchWgt = registry.byId("privateOpinion")
            if (switchWgt) {
              topic.subscribe("mui/form/checkbox/change", function(wgt) {
                if (wgt && wgt == switchWgt) {
                  var privateOpinionCanViewTr = query(
                    "#privateOpinionCanViewTr"
                  )[0]
                  if (wgt.checked) {
                    domStyle.set(privateOpinionCanViewTr, {display: "block"})
                  } else {
                    domStyle.set(privateOpinionCanViewTr, {display: "none"})
                    var myWgt = registry.byId("privateOpinionPerson")
                    myWgt.set("curIds", "")
                    myWgt.set("curNames", "")
                  }
                }
              })
            }
          }
        }
      })
    })
  }

  // 控制流程状态行
  process.controlProcessStatusRow = lbpm.globals.controlProcessStatusRow = function() {
    var fdProcessStatus = $(
      "input[name='sysWfBusinessForm.fdProcessStatus']"
    ).val()
    if (fdProcessStatus) {
      $("#processStatusRow").show()
      $("#processStatusLabel").html(
        "<font color=red>" + fdProcessStatus + "</font>"
      )
    }
  }

  //对附件机制的显示行的控制
  process.setupAttachmentRow = lbpm.globals.setupAttachmentRow = function() {
    var assignmentRow = dom.byId("assignmentRow")
    if (assignmentRow == null) {
      return
    }
    if (!lbpm.nowProcessorInfoObj) {
      lbpm.globals.hiddenObject(assignmentRow, true)
      return
    }
    var canAddAuditNoteAtt = lbpm.globals.getCurrentNodeObj().canAddAuditNoteAtt
    var fdkey = dom.byId("sysWfBusinessForm.fdAuditNoteFdId").value
    if (canAddAuditNoteAtt == "false") {
      lbpm.globals.hiddenObject(assignmentRow, true)
      if (
        typeof window.AttachmentList != "undefined" &&
        window.AttachmentList[fdkey]
      ) {
        window.AttachmentList[fdkey].fileStatus = -1
      }
    } else {
      lbpm.globals.hiddenObject(assignmentRow, false)
      if (
        typeof window.AttachmentList != "undefined" &&
        window.AttachmentList[fdkey]
      ) {
        window.AttachmentList[fdkey].fileStatus = 1
      }
    }

    var tasksId = lbpm.globals.getOperationParameterJson("id")
    var showTableKey = tasksId + "_" + lbpm.handlerId
    var attachmentTableArray = assignmentRow.getElementsByTagName("Div")
    var $attachmentTitleArray = $(assignmentRow).find("[name='attachmentTtile']");
    for (var i = 0; i < attachmentTableArray.length; i++) {
      var attachmentTable = attachmentTableArray[i]
      if (attachmentTable.getAttribute("name") != "attachmentDiv") {
        continue
      }
      if (attachmentTable.getAttribute("id") == showTableKey) {
        lbpm.globals.hiddenObject(attachmentTable, false);
        if($attachmentTitleArray[i]){
        	var muiFormEleTitleDom = $(attachmentTable).find(".muiFormEleTitle")[0];
        	if(muiFormEleTitleDom){//隐藏本身的
        		lbpm.globals.hiddenObject(muiFormEleTitleDom, true);
        	}
        	var muiAttachmentEditItemDom = $(attachmentTable).find(".muiAttachmentEditItem")[0];
        	if(muiAttachmentEditItemDom){
        		$(muiAttachmentEditItemDom).addClass("attr_adapter");
        	}
        	lbpm.globals.hiddenObject($attachmentTitleArray[i], false);
        }
      } else {
        lbpm.globals.hiddenObject(attachmentTable, true);
        if($attachmentTitleArray[i]){
        	lbpm.globals.hiddenObject($attachmentTitleArray[i], true);
        }
      }
    }
  }

  process.hiddenObject = lbpm.globals.hiddenObject = function(obj, flag) {
    if (obj != null) {
      if (flag) {
        $(obj).hide()
      } else {
        $(obj).show()
      }
    }
  }

  //取得当前节点的对象信息
  process.getNodeObj = lbpm.globals.getNodeObj = function(nodeId) {
    if (nodeId == "" || nodeId == null) {
      return {}
    }
    return lbpm.nodes[nodeId]
  }

  //取得当前节点的连线对象信息
  process.getLineObj = lbpm.globals.getLineObj = function(
    nodeId,
    showStartNode
  ) {
    var nodeObj = lbpm.nodes[nodeId]
    if (showStartNode == null || showStartNode == true) {
      return nodeObj.startLines[0]
    } else {
      return nodeObj.endLines[0]
    }
  }

  //取得下一个节点的对象
  process.getNextNodeObj = lbpm.globals.getNextNodeObj = function(nodeId) {
    var nodeObj = lbpm.nodes[nodeId]
    return nodeObj.endLines[0].endNode
  }
  //获取当前主文档类型
  process.getWfBusinessFormModelName = lbpm.globals.getWfBusinessFormModelName = function() {
    var modelName = lbpm.modelName
    var fdModelName = document.getElementsByName(
      "sysWfBusinessForm.fdModelName"
    )
    //#2202 修改为 优先取 sysWfBusinessForm.fdModelName 中的modelName #曹映辉 日期 2014.08.19
    if (fdModelName && fdModelName.length > 0) {
      modelName = lbpm.modelName = fdModelName[0].value
    }
    return modelName
  }
  //获取当前主文档ID
  process.getWfBusinessFormModelId = lbpm.globals.getWfBusinessFormModelId = function() {
    var modelId = lbpm.modelId
    var fdModelId = document.getElementsByName("sysWfBusinessForm.fdModelId")
    if (fdModelId && fdModelId.length > 0) {
      modelId = lbpm.modelId = fdModelId[0].value
    }
    return modelId
  }
  //获取当前主文档fdkey
  process.getWfBusinessFormFdKey = lbpm.globals.getWfBusinessFormFdKey = function() {
    var fdkey = lbpm.constant.FDKEY
    var _fdkey = document.getElementsByName("sysWfBusinessForm.fdKey")
    if (_fdkey && _fdkey.length > 0) {
      fdkey = lbpm.constant.FDKEY = _fdkey[0].value
    }
    return fdkey
  }
  //获取当前主文档ID
  process.getWfBusinessFormDocStatus = lbpm.globals.getWfBusinessFormDocStatus = function() {
    var docStatus = lbpm.constant.DOCSTATUS
    var _docStatus = document.getElementsByName("docStatus")
    if (_docStatus && _docStatus.length > 0) {
      docStatus = lbpm.constant.DOCSTATUS = _docStatus[0].value
    }
    return docStatus
  }

  process.checkModifyNodeAuthorization = lbpm.globals.checkModifyNodeAuthorization = function(
    nodeObj,
    allowModifyNodeId
  ) {
    if (
      nodeObj.mustModifyHandlerNodeIds != null &&
      nodeObj.mustModifyHandlerNodeIds != ""
    ) {
      var index = (nodeObj.mustModifyHandlerNodeIds + ";").indexOf(
        allowModifyNodeId + ";"
      )
      if (index != -1) {
        return true
      }
    }
    return false
  }

  //解析当前处理人的Info，返回当前操作对象
  process.analysisProcessorInfoToObject = lbpm.globals.analysisProcessorInfoToObject = function() {
    return lbpm.nowProcessorInfoObj
  }

  //清除操operationsRow信息，保证没有不必要的提示信息
  process.handlerOperationClearOperationsRow = lbpm.globals.handlerOperationClearOperationsRow = function() {
    if (lbpm.globals.destroyOperations) {
      lbpm.globals.destroyOperations()
    }
    $("[lbpmMark='operation']").each(function() {
      $(this)
        .find("[lbpmDetail]")
        .each(function() {
          this.innerHTML = ""
        })
      $(this)
        .find("td")
        .each(function() {
          this.innerHTML = ""
        })
      lbpm.globals.hiddenObject(this, true)
    })
    $("[lbpmMark='hide']").each(function() {
      lbpm.globals.hiddenObject(this, true)
    })
  }

  //取得下一节点的对象数组
  process.getNextNodeObjs = lbpm.globals.getNextNodeObjs = function(nodeId) {
    var nodeObj = lbpm.nodes[nodeId]
    var nextNodeObjs = new Array()
    for (var i = 0, j = nodeObj.endLines.length; i < j; i++) {
      nextNodeObjs.push(nodeObj.endLines[i].endNode)
    }
    return nextNodeObjs
  }

  //取得上一节点的对象组
  process.getPreviousNodeObjs = lbpm.globals.getPreviousNodeObjs = function(
    nodeId
  ) {
    var nodeObj = lbpm.nodes[nodeId]
    var preNodeObjs = new Array()
    for (var i = 0, j = nodeObj.startLines.length; i < j; i++) {
      preNodeObjs.push(nodeObj.startLines[i].startNode)
    }
    return preNodeObjs
  }

  //取得上一节点的对象
  process.getPreviousNodeObj = lbpm.globals.getPreviousNodeObj = function(
    nodeId
  ) {
    return lbpm.globals.getPreviousNodeObjs(nodeId)[0]
  }

  //获取当前节点对象
  process.getCurrentNodeObj = lbpm.globals.getCurrentNodeObj = function() {
    if (lbpm.nowNodeId && lbpm.nowNodeId != "")
      return lbpm.nodes[lbpm.nowNodeId]
    else return null
  }

  lbpm.globals.parseTasksInfo = function(curNodeXMLObj, taskFrom, identity) {
    var tasksArr = $.grep(curNodeXMLObj.tasks, function(n, i) {
      return n.taskFrom == taskFrom
    })
    if (tasksArr.length == 0) return tasksArr
    var _tasksArr = $.extend(true, [], tasksArr) // clone对象
    var rtnArr = $.grep(_tasksArr, function(task, i) {
      if (task.operations) {
        //过滤操作
        var arr = $.grep(task.operations, function(n, i) {
          return n.operationHandlerType == identity
        })
        if (arr.length == 0) {
          return false
        }
        task.operations = arr
        return true
      }
      return false
    })
    return rtnArr
  }

  process.getDrafterInfoObj = lbpm.globals.getDrafterInfoObj = function(
    curNodeXMLObj
  ) {
    if (curNodeXMLObj == null) return lbpm.drafterInfoObj
    return lbpm.globals.parseTasksInfo(curNodeXMLObj, "node", "drafter")
  }

  process.getAuthorityInfoObj = lbpm.globals.getAuthorityInfoObj = function(
    curNodeXMLObj
  ) {
    if (curNodeXMLObj == null) return lbpm.authorityInfoObj
    return lbpm.globals.parseTasksInfo(curNodeXMLObj, "node", "admin")
  }

  process.getHistoryhandlerInfoObj = lbpm.globals.getHistoryhandlerInfoObj = function(
    curNodeXMLObj
  ) {
    if (curNodeXMLObj == null) return lbpm.historyhandlerInfoObj
    return lbpm.globals.parseTasksInfo(curNodeXMLObj, "node", "historyhandler")
  }
  
  process.getBranchAdminInfoObj = lbpm.globals.getBranchAdminInfoObj = function(
    curNodeXMLObj
  ) {
    if (curNodeXMLObj == null) return lbpm.branchAdminInfoObj
    return lbpm.globals.parseTasksInfo(curNodeXMLObj, "node", "admin")
  }

  //获取当前用户的对当前流程的信息（多表单模式时，当前方法返回的是根据当前表单id过滤过的事务信息；可从lbpm.allMyProcessorInfoObj中获取未过滤过当前用户的事务信息）
  process.getProcessorInfoObj = lbpm.globals.getProcessorInfoObj = function(
    curNodeXMLObj
  ) {
    //LBPM当前节点的XML信息解析
    var roleType = lbpm.constant.ROLETYPE
    if (roleType == lbpm.constant.DRAFTERROLETYPE) {
      return lbpm.globals.getDrafterInfoObj(curNodeXMLObj)
    } else if (roleType == lbpm.constant.AUTHORITYROLETYPE) {
      return lbpm.globals.getAuthorityInfoObj(curNodeXMLObj)
    } else if (roleType == lbpm.constant.HISTORYHANDLERROLETYPE) {
      return lbpm.globals.getHistoryhandlerInfoObj(curNodeXMLObj)
    }else if(roleType == lbpm.constant.BRANCHADMINROLETYPE){
      return lbpm.globals.getBranchAdminInfoObj(curNodeXMLObj)
    }
    if (curNodeXMLObj == null) return lbpm.processorInfoObj

    var allMyProcessorInfoObj = (lbpm.allMyProcessorInfoObj = lbpm.globals.parseTasksInfo(
      curNodeXMLObj,
      "workitem",
      "handler"
    ))
    if (lbpm.isSubForm) {
      var subFormProcessInfoObj = new Array()
      // 多表单模式时根据当前的表单ID过滤事务
      for (var i = 0; i < allMyProcessorInfoObj.length; i++) {
        if (
          lbpm.nowSubFormId ==
          lbpm.nodes[allMyProcessorInfoObj[i]["nodeId"]]["subFormMobileId"]
        ) {
          subFormProcessInfoObj.push(allMyProcessorInfoObj[i])
        }
      }
      return subFormProcessInfoObj
    } else {
      return allMyProcessorInfoObj
    }
  }

  //转换数组为字符串 add by limh 2010年9月24日
  process.arrayToStringByKey = lbpm.globals.arrayToStringByKey = function(
    arr,
    key
  ) {
    var str = ""
    if (arr) {
      for (var index = 0; index < arr.length; index++) {
        str = str + ";" + arr[index][key]
      }
      str = str.substring(1)
    }
    return str
  }

  process.isPassedSubprocessNode = lbpm.globals.isPassedSubprocessNode = function() {
    var rtnValue = false
    $.each(lbpm.nodes, function(index, nodeData) {
      if (nodeData.Status == lbpm.constant.STATUS_PASSED) {
        if (
          lbpm.globals.checkNodeType(
            lbpm.constant.NODETYPE_STARTSUBPROCESS,
            nodeData
          )
        ) {
          rtnValue = true
          return false // 中断循环
        }
      }
    })
    return rtnValue
  }

  //取得有效的当前节点
  process.getAvailableRunningNodes = lbpm.globals.getAvailableRunningNodes = function() {
    var RunningNodesArr = new Array()
    $.each(lbpm.nodes, function(index, nodeData) {
      if (nodeData.Status == lbpm.constant.STATUS_RUNNING)
        RunningNodesArr.push(nodeData)
    })
    return RunningNodesArr
  }
  //取得有效的历史节点
  process.getAvailableHistoryNodes = lbpm.globals.getAvailableHistoryNodes = function() {
    var HistoryNodesArr = new Array()
    $.each(lbpm.nodes, function(index, nodeData) {
      if (nodeData.Status == lbpm.constant.STATUS_PASSED)
        HistoryNodesArr.push(nodeData)
    })
    return HistoryNodesArr
  }

  //获取节点的数量
  process.getNodeSize = lbpm.globals.getNodeSize = function() {
    var nodeSize = 0
    $.each(lbpm.nodes, function(index, nodeData) {
      nodeSize = nodeSize + 1
    })
    return nodeSize
  }

  function _getOperationParameterJson(arr, processorObj) {
    var rtnObject = new Object()

    for (var i = 0, l = arr.length; i < l; i++) {
      var param = arr[i]
      if (!processorObj) rtnObject[param] = null
      else {
        if (processorObj[param] != null) rtnObject[param] = processorObj[param]
        else {
          rtnObject[param] = ""
          processorObj[param] = ""
        }
      }
    }
    //如果传递过来是一个参数，直接返回值，不返回数组对象了
    if (arr.length == 1) return rtnObject[arr[0]]
    return rtnObject
  }

  //获取后台参数
  process.getOperationParameterJson = lbpm.globals.getOperationParameterJson = function(
    params,
    fromWorkitem,
    nodeObj,
    callback
  ) {
    var processorObj = lbpm.nowProcessorInfoObj
    var arr = params.split(":")

    var arrNotValue = lbpm.globals.getNullParamArr(arr, processorObj)

    if (arrNotValue.length > 0) {
      if (callback) {
        lbpm.globals.getOperationParameterFromAjax(
          arrNotValue,
          fromWorkitem,
          processorObj,
          nodeObj,
          function() {
            callback(_getOperationParameterJson(arr, processorObj))
          }
        )
        return
      }

      lbpm.globals.getOperationParameterFromAjax(
        arrNotValue,
        fromWorkitem,
        processorObj,
        nodeObj
      )
    }

    if (callback) {
      callback(_getOperationParameterJson(arr, processorObj))
      return
    }

    return _getOperationParameterJson(arr, processorObj)
  }
  //获取没有缓存的参数数组
  process.getNullParamArr = lbpm.globals.getNullParamArr = function(
    arr,
    processorObj
  ) {
    var rtnArr = new Array()
    if (!processorObj) return rtnArr
    for (var i = 0, size = arr.length; i < size; i++) {
      if (processorObj[arr[i]] == null) rtnArr.push(arr[i])
    }
    return rtnArr
  }
  /**
   * 通过AJAX方式获取参数值<br>
   * 有回调函数则走异步
   */
  process.getOperationParameterFromAjax = lbpm.globals.getOperationParameterFromAjax = function(
    arr,
    fromWorkitem,
    processorObj,
    nodeObj,
    callback
  ) {
	  
    if (!processorObj) return
    var jsonObj = {}
    if (fromWorkitem) {
      jsonObj.taskType = processorObj.type
      jsonObj.taskId = processorObj.id
    } else {
      //如果传递了节点对象，直接取传的节点对象，否则取当前节点对象
      if (nodeObj) {
        jsonObj.nodeId = nodeObj.id
        jsonObj.nodeType = nodeObj.XMLNODENAME
      } else {
        jsonObj.nodeId = lbpm.nowNodeId
        jsonObj.nodeType = lbpm.globals.getCurrentNodeObj().XMLNODENAME
      }
    }
    jsonObj.params = arr.join(":")

    // 异步
    if (callback) {
      lbpm.globals._getOperationParameterFromAjax(jsonObj, function(
        jsonRtnObj
      ) {
        _getOperationParameterFromAjax(processorObj, jsonRtnObj, arr)
        callback()
      })
      return
    }

    // 同步
    var jsonRtnObj = lbpm.globals._getOperationParameterFromAjax(jsonObj)
    _getOperationParameterFromAjax(processorObj, jsonRtnObj, arr)
  }

  function _getOperationParameterFromAjax(processorObj, jsonRtnObj, arr) {
    if (jsonRtnObj != null) {
      for (o in jsonRtnObj) {
        if (jsonRtnObj[o] != null) processorObj[o] = jsonRtnObj[o]
        else processorObj[o] = ""
      }
    } else {
      for (var i = 0, size = arr.length; i < size; i++) {
        processorObj[arr[i]] = ""
      }
    }
  }

  lbpm.globals._getOperationParameterFromAjax = function(jsonObj, callback) {
    var _dojoConfig = window.dojoConfig || {}
    var jsonUrl =
      Com_Parameter.ContextPath +
      "sys/lbpmservice/include/sysLbpmdata.jsp" +
      "?m_Seq=" +
      Math.random()
    if (_dojoConfig && _dojoConfig.serverPrefix) {
      jsonUrl =
        _dojoConfig.serverPrefix +
        "/sys/lbpmservice/include/sysLbpmdata.jsp" +
        "?m_Seq=" +
        Math.random()
    }

    jsonUrl +=
      "&processId=" + $("[name='sysWfBusinessForm.fdProcessId']")[0].value
    jsonUrl += "&modelName=" + lbpm.modelName
    var jsonRtnObj = null

    var options = {
      url: jsonUrl,
      data: jsonObj,
      async: false,
      dataType: "json",
      success: function(json) {
        jsonRtnObj = json
      }
    }

    if (callback) {
      options.async = true
      options.success = function(json) {
        callback(json)
      }
    }

    if (window.dojo4OfflineKK) {
      options.crossDomain = true
      options.xhrFields = options.xhrFields || {}
      options.xhrFields.withCredentials = true
    }
    $.ajax(options)
    return jsonRtnObj
  }
  /***********************************************
		功能：替换HTML代码中的敏感字符
		***********************************************/
  process.htmlUnEscape = lbpm.globals.htmlUnEscape = function(s) {
    if (s == null || s == "") return ""
    var re = /&amp;/g
    s = s.replace(re, "&")
    re = /&quot;/g
    s = s.replace(re, '"')
    re = /&#39;/g
    s = s.replace(re, "'")
    re = /&lt;/g
    s = s.replace(re, "<")
    re = /&gt;/g
    return s.replace(re, ">")
  }

  // 获取当前选中审批操作
  process.getCurrentOperation = lbpm.globals.getCurrentOperation = function() {
    $("[name='oprGroup']").each(function() {
      if (this.checked || this.type == "select" || this.type == "select-one") {
        var oprArr = this.value.split(":")
        lbpm.currentOperationType = oprArr[0]
        lbpm.currentOperationName = oprArr[1]
        return false
      }
    })
    if (lbpm.currentOperationType == null) {
      return null
    }
    return {
      type: lbpm.currentOperationType,
      name: lbpm.currentOperationName,
      operation: lbpm.operations[lbpm.currentOperationType]
    }
  }

  if (!lbpm.address) lbpm.address = {}

  process.is_pda = lbpm.address.is_pda = function() {
    return window.dojo ? true : false
  }

  var alertText = function(options) {
    return options.alertText ? options.alertText : ""
  }
  var idNameKeyHtml = function(field) {
    return "id=" + field + " name=" + field + " key=" + field + ""
  }
  process.html_build = lbpm.address.html_build = function(options) {
    var cateFieldShow = ""
    if (options.cateFieldShow) {
      cateFieldShow =
        ", cateFieldShow:'#_" +
        options.idField +
        '_label\'" style="float:right;width:35px;height:30px;margin:0 5px 0 0;'
    }
    var idValue = options["idValue"] || ""
    var nameValue = options["nameValue"] || ""
    var html =
      "<input type='hidden' alertText='" +
      alertText(options) +
      "' value='" +
      Com_HtmlEscape(idValue) +
      "' " +
      idNameKeyHtml(options.idField) +
      ">" +
      "<input type='hidden' " +
      idNameKeyHtml(options.nameField) +
      " alertText='' value='" +
      Com_HtmlEscape(nameValue) +
      "'>"
    var mixin = ""
    if (options.groupBtn) {
      mixin += dojoConfig.baseUrl + "sys/lbpmservice/mobile/GroupButtonMixin.js"
    }
    var validate = options.validate ? "validate:\'"+options.validate+"\'," : '';
    if(options.validate && (options.validate.indexOf(" required")>=0 || options.validate.indexOf("required ")>=0)){
    	validate += " required:true, ";
    }
    var subject = "";
    if(options.isShowSubject){
    	if(options.subject){
    		subject = "subject:\'"+options.subject+"\',";
    	}else{
    		subject = "subject:\'"+alertText(options)+"\',";
    	}
    }
    var id = options.id ? "id=\'" + options.id + "\' " : "";
    html +=
      '<div '+id+' data-dojo-type="mui/form/Address"' +
      ' data-dojo-props="type: ' +
      (options.selectType || ORG_TYPE_POSTORPERSON) +
      ",idField:'" +
      options.idField +
      "'," +
      "nameField:'" +
      options.nameField +
      "'," +
      "exceptValue:'" +
      (options.exceptValue == null ? "" : options.exceptValue.join(";")) +
      "'," +
      "curIds:'" +
      (options.idValue == null ? "" : options.idValue) +
      "'," +
      "curNames:'" +
      (options.nameValue == null ? "" : options.nameValue) +
      "'," +
      ""+validate+subject+"isMul:" +
      options.mulSelect +
      cateFieldShow +
      '"></div>'
    return html
  }

  //获取主文档和表单数据字典
  process.getFormFieldList = lbpm.globals.getFormFieldList = function() {
    var fdKey = lbpm.constant.FDKEY
      ? lbpm.constant.FDKEY
      : $("[name='sysWfBusinessForm.fdKey']").val()
    var func = "XForm_getXFormDesignerObj_" + fdKey
    if (window[func]) {
      return window[func]()
    } else {
      return Formula_GetVarInfoByModelName(
        lbpm.globals.getWfBusinessFormModelName()
      )
    }
  }

  //获取指定拓展属性
  process.getExtAttribute = lbpm.globals.getExtAttribute = function(
    nodeObj,
    attrName
  ) {
    if (!nodeObj || !nodeObj.extAttributes) return
    var extAttributes = nodeObj.extAttributes
    if (extAttributes.length > 0) {
      for (var i = 0; i < extAttributes.length; i++) {
        var extAttribute = extAttributes[i]
        if (extAttribute.name == attrName) {
          return extAttribute
        }
      }
    }
    return null
  }
  
  process.updateProcessToFinalVersion = lbpm.globals.updateProcessToFinalVersion = function(){
		var docStatus = query("#__docStatus").val();
		var method = query("#__method").val();
		if(method == "edit" && docStatus == "10"){//编辑页面下并且文档状态是草稿
			var url = Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpmSaveDraftAction.do?method=checkIsDraft";
			var data = {"processId":query("[name='sysWfBusinessForm.fdProcessId']")[0].value};
			request.post(url,{data:data,handleAs:'json',sync:false}).then(
			function(data){
			    //成功后回调
				if(data == true || data == "true"){
					var html = '<span style="font-size:14px">'+Msg1["lbpmservice.saveDraft.content"]+'</span>';
					var title = Msg1['lbpmservice.saveDraft.title'];
					var canClose = false;
					var callback = function(value,dialog){
						if(value){
							var updateUrl = Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpmSaveDraftAction.do?method=updateProcessToFinalVersion";
							var updateData = {"processId":query("[name='sysWfBusinessForm.fdProcessId']")[0].value};
							request.post(updateUrl,{data:updateData,handleAs:'json',sync:true}).then(
								function(result){
									if(result == true || result == "true"){
										//刷新
										window.location.href = location.href+'&time='+((new Date()).getTime());
									}
								},
								function(e){
								}
							);
						}
					};
					var dialogCallBack = function(){
						callback(true);
					}
					var contentNode = domConstruct.create('div', {
						className : 'muiConfirmDialogElement',
						innerHTML : '<div>' + html + '</div>'
					});
					var options = {
							'title' : title,
							'showClass' : 'muiConfirmDialogShow',
							'element' : contentNode,
							'scrollable' : false,
							'parseable' : false, 
							'canClose' : false,
							'callback' : dialogCallBack,
							'buttons' : [{
								title : '<span style="font-size:1.6rem">' + Msg["mui.button.ok"] + '</span>',
								fn : function(dialog) {
									dialog.hide();
									callback(true, dialog);
								}
							} ]
						};
					var dialog = Dialog.element(options);
				}
			},function(error){
				//错误回调
			});
		}
	}

  process.updateShowLimitTimeTotal = lbpm.globals.updateShowLimitTimeTotal = function(){
	  var formatTotalTime = function(total){
	  	var data = {};
	  	var length = 2;
	  	total = total / 1000;
		// 得到天数
		var day = parseInt(total / (3600 * 24));
		data.day = day;

		// 得到小时数
		total = total - day * 3600 * 24;
		var hour = parseInt(total / 3600);
		data.hour = ('' + hour).length < length ? ((new Array(length + 1)).join('0') + hour).slice(-length) : '' + hour;

		// 得到分钟数
		total = total - hour * 3600;
		var minute = parseInt(total / 60);
		data.minute = ('' + minute).length < length ? ((new Array(length + 1)).join('0') + minute).slice(-length) : '' + minute;

		// 得到秒数
		var second = parseInt(total - minute * 60);
		data.second = ('' + second).length < length ? ((new Array(length + 1)).join('0') + second).slice(-length) : '' + second;
		
		return data;
	  }
	  
	  setInterval(function(){
		  if(lbpm.limitTotalTime){
			  var totalTime = parseInt(lbpm.limitTotalTime);
			  var isTimeout = lbpm.isTimeoutTotal;
			  if(isTimeout == 'true'){//超时，时间增加
				  totalTime = totalTime + 1000;
			  }else if(isTimeout == 'false'){//限时，时间减少
				  totalTime = totalTime - 1000;
			  }
			  if(totalTime < 0){
				  totalTime = Math.abs(totalTime);
				  isTimeout = "true";//超时了
				  var title = Data_GetResourceString("sys-lbpmservice:lbpmNode.processingNode.timeoutRow");
				  $("#limitTimeMethodRowTitle .titleNode").html(title);
			  }
			  var rtnVal = formatTotalTime(totalTime);
			  var detail = '<span class="limit_time_span" style="border-radius: 2px; padding: 2px 7px;">'+rtnVal.day+'</span> '+Data_GetResourceString("sys-lbpmservice:FlowChartObject.Lang.Node.day")+' <span class="limit_time_span" style="border-radius: 2px; padding: 2px 7px;">'+rtnVal.hour+'</span> : <span class="limit_time_span" style="border-radius: 2px; padding: 2px 7px;">'+rtnVal.minute+'</span> : <span class="limit_time_span" style="border-radius: 2px; padding: 2px 7px;">'+rtnVal.second+'</span>';
			  $("#limitTimeMethodRow .detailNode").html(detail);
			  lbpm.limitTotalTime = totalTime;
			  lbpm.isTimeoutTotal = isTimeout;
		  }
	  }, 1000);
  }
  
  //查看节点帮助
  process.showNodeHelpDailog = window.showNodeHelpDialog = function(){
	  var contentNode = domConstruct.create('div');
	  domConstruct.place(dom.byId("nodeDescriptionDialog"),contentNode,'first');
	  var options = {
			'title' : "节点帮助",
			'showClass' : 'nodeDescriptionDialog',
			'element' : contentNode,
			'scrollable' : false,
			'parseable' : false, 
			'iconClose' : true,
			'buttons' : [],
			'callback': lang.hitch(this, function(win,evt) {
				domStyle.set(dom.byId("nodeDescriptionDialog"),{
					"display":"none"
				})
				domConstruct.place(query("#nodeDescriptionDialog",evt.element)[0],dom.byId("nodeDescriptionRow"),'last');
			}),
			'onDrawed':lang.hitch(this, function(evt) {
				domStyle.set(dom.byId("nodeDescriptionDialog"),{
					"display":"block"
				})
				 domStyle.set(evt.contentNode, {
					   'min-height' :'10rem',
					   'max-height' :'32rem',
					   'overflow-y':'auto',
					   'overflow-x':'hidden'
				 });
			})
		};
	  Dialog.element(options);
  }
  
//查看流程说明
  process.showFlowDescriptionDialog = window.showFlowDescriptionDialog = function(){
	  var contentNode = domConstruct.create('div');
	  domConstruct.place(dom.byId("flowDescriptionDialog"),contentNode,'first');
	  var options = {
			'title' : "流程说明",
			'showClass' : 'flowDescriptionDialog',
			'element' : contentNode,
			'scrollable' : false,
			'parseable' : false, 
			'iconClose' : true,
			'buttons' : [],
			'callback': lang.hitch(this, function(win,evt) {
				domStyle.set(dom.byId("flowDescriptionDialog"),{
					"display":"none"
				})
				domConstruct.place(query("#flowDescriptionDialog",evt.element)[0],dom.byId("fdFlowDescriptionRow"),'last');
			}),
			'onDrawed':lang.hitch(this, function(evt) {
				domStyle.set(dom.byId("flowDescriptionDialog"),{
					"display":"block"
				})
				 domStyle.set(evt.contentNode, {
					   'min-height' :'10rem',
					   'max-height' :'32rem',
					   'overflow-y':'auto',
					   'overflow-x':'hidden'
				 });
			})
		};
	  Dialog.element(options);
  }

  process.handleDescriptionLang4View = lbpm.globals.handleDescriptionLang4View = function(processData,def){
		function _getLangLabelByJson(defLabel,langsArr,lang){
			if(langsArr==null){
				return defLabel;
			}
			for(var i=0;i<langsArr.length;i++){
				if(lang==langsArr[i]["lang"]){
					return _formatValues(langsArr[i]["value"])||defLabel;
				}
			}
			return _formatValues(defLabel);
		}
		function _formatValues(value){
			value=value||"";
			value  = util.formatText(value);
			value=value.replace(/&amp;#xD;&amp;#xA;/g,"<br />");
			value=value.replace(/&amp;#xD;/g,"");
			value=value.replace(/&amp;#xA;/g,"<br />");
			return value;
		}

		if(!_isLangSuportEnabled){
			return def;
		}
		if(processData.descriptionLangJson){
			var descriptionLangJson = JSON.parse(processData.descriptionLangJson);
			var lang = WorkFlow_GetCurrUserLang();
			var value =  _getLangLabelByJson(processData.description,descriptionLangJson, lang);
			return value||"";
		}
		return def;
	}
	
  process.fdFlowDescriptionInit = lbpm.globals.fdFlowDescriptionInit=function(){
		try{
			//初始化流程说明
			if(lbpm.flowcharts && lbpm.flowcharts.description && Lbpm_SettingInfo.isShowFlowDescription == "true") {
				var changedText = util.formatText(lbpm.flowcharts.description);
				if(changedText) {
					// &已经被formatText转义了
					changedText=changedText.replace(/&amp;#xD;&amp;#xA;/g,"<br />");
					changedText=changedText.replace(/&amp;#xD;/g,"");
					changedText=changedText.replace(/&amp;#xA;/g,"<br />");
				}
				changedText = lbpm.globals.handleDescriptionLang4View(lbpm.flowcharts,changedText);
				query("#currentflowDescription").html(changedText);
				domStyle.set(dom.byId("fdFlowDescriptionRow"),{
					"display":"inline-block"
				})
				domStyle.set(dom.byId("lbpmOtherInfo"),{
					"display":""
				})
				lbpm.isShowLbpmOtherInfo = true;
			}else{
				domStyle.set(dom.byId("fdFlowDescriptionRow"),{
					"display":"none"
				})
				if(!lbpm.isShowLbpmOtherInfo){
					domStyle.set(dom.byId("lbpmOtherInfo"),{
						"display":"none"
					})
					lbpm.isShowLbpmOtherInfo = false;
				}
			}
		}catch(e){}
	}
  
  	process.lbpmIsRemoveNodeIdentifier = lbpm.globals.lbpmIsRemoveNodeIdentifier = function(){
	  	var isRemoveNodeIdentifier = false;
		if (lbpm && lbpm.settingInfo){
			if (lbpm.settingInfo.isRemoveNodeIdentifier === "true"){
				isRemoveNodeIdentifier = true;
			}
		}
		return isRemoveNodeIdentifier;
	};

  /**
   * 是否隐藏所有节点编号的显示
   */
  process.lbpmIsHideAllNodeIdentifier = lbpm.globals.lbpmIsHideAllNodeIdentifier = function(){
    var isHideAllNodeIdentifier = false;
    if (Lbpm_SettingInfo && Lbpm_SettingInfo.isHideNodeIdentifier === "true" && Lbpm_SettingInfo.hideNodeIdentifierType === "isHideAllNodeIdentifier"){
      isHideAllNodeIdentifier = true;
    }
    return isHideAllNodeIdentifier;
  };
  
  //初始化
  process.init = function() {
    lbpm.globals.initialContextParams()
    lbpm.globals.parseXMLObj()
    //设置节点层级，用于排序 #作者：曹映辉 #日期：2013年5月27日
    lbpm.globals.setNodeLevel()
    lbpm.globals.parseSubFormInfoObj()
    lbpm.globals.parseProcessorObj()
    lbpm.globals.validateControlItem()
    lbpm.globals.initPrivateOpinion()
    lbpm.globals.setupAttachmentRow();
    lbpm.events.addListener(
      lbpm.constant.EVENT_CHANGEWORKITEM,
      lbpm.globals.setupAttachmentRow
    )
    //增加是否是KM-REVIEW业务模块请求的弹窗，如果是则不走下面的流程弹窗
    if(window.fromReview != "true"){
      //判断草稿下是否需要更新到最新模板
      lbpm.globals.updateProcessToFinalVersion();
    }
    lbpm.events.addListener("updateShowLimitTimeOperation",lbpm.globals.updateShowLimitTimeOperation);
    //限时总的时间毫秒数
	lbpm.limitTotalTime = window.limitTotalTime;
	lbpm.isTimeoutTotal = window.isTimeoutTotal;
    //开启限时倒计时
	lbpm.globals.updateShowLimitTimeTotal();
	 lbpm.globals.fdFlowDescriptionInit();
  }
  return process
})
