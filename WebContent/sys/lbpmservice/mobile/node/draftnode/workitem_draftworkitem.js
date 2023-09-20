define([
  "dijit/registry",
  "dojo/store/Memory",
  "dojo/_base/array",
  "dojo/query",
  "dojo/topic",
  "dojo/dom",
  "sys/lbpmservice/mobile/common/syslbpmprocess"
], function(registry, Memory, array, query, topic, dom) {
  var workItem = {}

  //显示草稿页面－提交身份的行
  workItem.showHandlerIdentityRow = lbpm.globals.showHandlerIdentityRow = function() {
    var handlerIdentityRow = dom.byId("handlerIdentityRow")
    var handlerIdentityIdsObj = dom.byId(
      "sysWfBusinessForm.fdHandlerRoleInfoIds"
    )
    var handlerIdentityNamesObj = dom.byId(
      "sysWfBusinessForm.fdHandlerRoleInfoNames"
    )

    var defaultIdentity = dom.byId("sysWfBusinessForm.fdDefaultIdentity").value
    var fdIdentityId = dom.byId("sysWfBusinessForm.fdIdentityId").value

    var handlerIdentityIds = handlerIdentityIdsObj.value
    var rolesIdsArray = handlerIdentityIds.split(";")
    var handlerIdentityNames = handlerIdentityNamesObj.value;
    var handlerRoleInfoNames = lbpm.globals.getHandlerRoleInfoNamesByOrgConfig(handlerIdentityIds);
	if(handlerRoleInfoNames){
		handlerIdentityNames = handlerRoleInfoNames;
	}
    var rolesNamesArray = handlerIdentityNames.split(";")
    if (rolesIdsArray.length <= 1 && handlerIdentityRow != null) {
      lbpm.globals.hiddenObject(handlerIdentityRow, true)
    } else {
      lbpm.globals.hiddenObject(handlerIdentityRow, false)
    }
    var rolesSelectObj = registry.byId("rolesSelectObj")
    if (rolesSelectObj == null) {
      return
    }
    lbpm.constant.handlerIdentityIsSameDept = (function(rolesIds){
		var result = false;
		if(rolesIds && rolesIds.length==2){
			var url = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpm_process/lbpmProcess.do?method=getHandlerRoleInfoIsSameParent&ajax=true';
			var obj = {
				"handlerRoleInfoIds":rolesIds	
			}
			var result;
			var data = new KMSSData();
			data.AddHashMap(obj);
			data.SendToUrl(url, function(http_request) {
				if(http_request.responseText.indexOf("<error>") == -1){
					result = http_request.responseText=="true";
				}
			},false);
		}
		return result;
	})(rolesIdsArray);
    var data = []
    var fdIdentityIdHasIn = false
    var defaultIdentityHasIn = false
    for (var i = 0; i < rolesIdsArray.length; i++) {
      data.push({value: rolesIdsArray[i], text: rolesNamesArray[i]})
      //确人这个ID必须在列表中存在
      if (fdIdentityId == rolesIdsArray[i]) {
        fdIdentityIdHasIn = true
      }
      if (defaultIdentity == rolesIdsArray[i]) {
        defaultIdentityHasIn = true
      }
    }
    rolesSelectObj.setStore(new Memory({data: data}))
    if (defaultIdentityHasIn && lbpm.constant.ISINIT) {
      rolesSelectObj.set("value", defaultIdentity)
    } else if (fdIdentityIdHasIn) {
      rolesSelectObj.set("value", fdIdentityId)
    } else {
      rolesSelectObj.set("value", rolesIdsArray[0])
    }

    //提交人身份-开关控制
    if (
      handlerIdentityRow != null &&
      Lbpm_SettingInfo.isShowDraftsmanStatus == "false"
    ) {
      lbpm.globals.hiddenObject(handlerIdentityRow, true)
    }

    topic.subscribe("/mui/form/valueChanged", function(srcObj) {
      if (srcObj == rolesSelectObj) {
        lbpm.events.mainFrameSynch()
      }
    })
  }

  //显示草稿页面－由起草人选择人工决策节点的行
  workItem.showManualBranchNodeRow = lbpm.globals.showManualBranchNodeRow = function() {
    var html = ""
    // 解析简版XML，查找由起草人决定分支的人工决策节点
    lbpm.globals.getOperationParameterJson(
      "draftDecidedFactIds:toRefuseThisNodeId",
      null,
      null,
      function(draftParams) {
        var draftDecidedFactIds = []
        if (
          draftParams["draftDecidedFactIds"] != null &&
          draftParams["draftDecidedFactIds"] != ""
        ) {
          draftDecidedFactIds = $.parseJSON(draftParams["draftDecidedFactIds"])
        }
        var isToRefuseThis =
          draftParams["toRefuseThisNodeId"] != null &&
          draftParams["toRefuseThisNodeId"] != ""
        var isSkipUnchecked = draftDecidedFactIds.length > 0
        $.each(lbpm.nodes, function(index, node) {
          if (
            lbpm.globals.checkNodeType(
              lbpm.constant.NODETYPE_MANUALBRANCH,
              node
            ) &&
            node.decidedBranchOnDraft == "true"
          ) {
            var isUncheckedNode = true
            $.each(draftDecidedFactIds, function(_i, decidedNode) {
              if (decidedNode.NodeName == node.id) {
                isUncheckedNode = false
                return false
              }
            })
            if (isSkipUnchecked && isUncheckedNode) return
            // 查找该节点的分支，拼装人工决策节点radio
            html += lbpm.globals.getNodeBranchesInfo(node, draftDecidedFactIds)
          }
        })

        if (html == "") {
          // 不存在由起草人决定分支的人工决策节点则隐藏行
          lbpm.globals.hiddenObject(dom.byId("manualBranchNodeRow"), true)
        } else {
          var manualNodeSelectTD = dom.byId("manualNodeSelectTD")
          if (manualNodeSelectTD != null) {
            query(manualNodeSelectTD)
              .forEach(function(node) {
                array.forEach(registry.findWidgets(node), function(widget) {
                  widget.destroy && !widget._destroyed && widget.destroy()
                })
              })
              .html(html, {parseContent: true})
            var nextNodeTd = query("#nextNodeTD")
            if (nextNodeTd.length > 0) {
              var nextObj = lbpm.globals.getNextNodeObj(lbpm.nowNodeId)
              if (nextObj && nextObj.decidedBranchOnDraft == "true") {
                if (lbpm.globals.destroyOperations)
                  lbpm.globals.destroyOperations()
                else nextNodeTd.html()
              }
            }
          }
          setTimeout(function() {
               lbpm.globals.hiddenObject(dom.byId("manualBranchNodeRow"),isToRefuseThis);
                  }, 100);
          if (isSkipUnchecked) {
            //lbpm.events.fireListener(lbpm.constant.EVENT_SELECTEDMANUAL, $("input[key='manualFutureNodeId']:checked").first());
            setTimeout(function() {
              lbpm.globals.setNextBranchNodes(
                $("input[key='manualFutureNodeId']:checked").first()
              )
            }, 350)
          }
        }
      }
    )
  }
  //获取人工决策节点分支信息（人工决策节点名称：分支信息1，分支信息2...）
  workItem.getNodeBranchesInfo = lbpm.globals.getNodeBranchesInfo = function(
    nodeData,
    manualNodeSelect
  ) {
    // 找到这个人工决策节点选择的分支
    manualNodeSelect = manualNodeSelect || lbpm.globals.getSelectedManualNode()
    var checkedId = ""
    $.each(manualNodeSelect, function(index, json) {
      if (json.NodeName == nodeData.id) {
        checkedId = json.NextRoute
      }
    })
    if (checkedId == "" && nodeData.defaultBranch) {
      for (var i = 0; i < nodeData.endLines.length; i++) {
        if (nodeData.endLines[i].id == nodeData.defaultBranch) {
          checkedId = nodeData.endLines[i].endNodeId
        }
      }
    }
    var html =
      "<div class=\'draftworkitemBrancheGroup\' data-dojo-type='sys/lbpmservice/mobile/node/draftnode/RadioGroup'" +
      " data-dojo-props='checkedId:\"" +
      checkedId +
      '",nodeId:"' +
      nodeData.id +
      '",validate:"manualFutureNodeIdSelect required",required:true' +"'></div>";
    if (html != "") {
      var langNodeName = WorkFlow_getLangLabel(
        nodeData.name,
        nodeData["langs"],
        "nodeName"
      )
      html =
        "<div class='draftworikitem_branche_row'><div class='draftworkitemBranche'>" +
        nodeData.id +
        "." +
        langNodeName +
        "：</div>" +
        html + "</div>";
    }
    return html
  }

  // 判断人工决策节点分支是否会产生闭环
  lbpm.isClosedLoop = lbpm.globals.isClosedLoop = function(nodeData, endline) {
    var isClosedLoop = false
    var nodeArray = new Array()
    nodeArray.push(endline.endNode)
    // 已查找节点，避免死循环
    var searchedNodeArray = new Array()
    outer: while (nodeArray.length > 0) {
      for (var i = 0, size = nodeArray.length; i < size; i++) {
        var node = nodeArray[i]
        searchedNodeArray.push(node)
        if (node.id == nodeData.id) {
          isClosedLoop = true
          break outer
        }
      }
      nodeArray = lbpm.globals.getNextNodesByManual(nodeArray)
      // 过滤已查找节点
      nodeArray = lbpm.globals.filterSearchedNodes(nodeArray, searchedNodeArray)
    }
    return isClosedLoop
  }

  // 根据选择过滤不会走到的人工决策节点
  workItem.setNextBranchNodes = lbpm.globals.setNextBranchNodes = function(
    curObj
  ) {
    var html = ""
    var manualNodeArray = lbpm.globals.getManualNodeArray()
    $.each(manualNodeArray, function(index, nodeData) {
      html += lbpm.globals.getNodeBranchesInfo(nodeData)
    })
    var manualNodeSelectTD = dom.byId("manualNodeSelectTD")
    query(manualNodeSelectTD)
      .forEach(function(node) {
        array.forEach(registry.findWidgets(node), function(widget) {
          widget.destroy && !widget._destroyed && widget.destroy()
        })
      })
      .html(html, {parseContent: true})
    lbpm.events.fireListener(lbpm.constant.EVENT_SELECTEDMANUAL, curObj)
  }

  //遍历流程图找到会走到的人工决策节点
  workItem.getManualNodeArray = lbpm.globals.getManualNodeArray = function() {
    var manualNodeArray = new Array()
    // 开始节点
    var nodeArray = new Array()
    nodeArray.push(lbpm.nodes["N1"])
    // 已查找节点，避免死循环
    var searchedNodeArray = new Array()
    // 遍历流程图
    while (nodeArray.length > 0) {
      $.each(nodeArray, function(index, node) {
        searchedNodeArray.push(node)
        if (
          lbpm.globals.checkNodeType(
            lbpm.constant.NODETYPE_MANUALBRANCH,
            node
          ) &&
          node.decidedBranchOnDraft == "true"
        ) {
          manualNodeArray.push(node)
        }
      })
      nodeArray = lbpm.globals.getNextNodesByManual(nodeArray)
      // 过滤已查找节点
      nodeArray = lbpm.globals.filterSearchedNodes(nodeArray, searchedNodeArray)
    }
    return manualNodeArray
  }

  //过滤已查找节点
  workItem.filterSearchedNodes = lbpm.globals.filterSearchedNodes = function(
    nodeArray,
    searchedNodeArray
  ) {
    var filterNodes = new Array()
    $.each(nodeArray, function(i, node) {
      var searchedFlag = false
      $.each(searchedNodeArray, function(j, searchedNode) {
        if (node.id == searchedNode.id) {
          searchedFlag = true
          return false
        }
      })
      if (!searchedFlag) {
        filterNodes.push(node)
      }
    })
    return filterNodes
  }

  // 获取节点的可以走到的下一个节点
  workItem.getNextNodesByManual = lbpm.globals.getNextNodesByManual = function(
    nodeArray
  ) {
    var manualNodeSelect = lbpm.globals.getSelectedManualNode()
    var nextNodes = new Array()
    $.each(nodeArray, function(i, node) {
      // 人工决策节点过滤分支
      if (
        lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_MANUALBRANCH, node) &&
        node.decidedBranchOnDraft == "true"
      ) {
        var nextRoute = ""
        // 查找该人工决策节点是否决定走哪条分支
        $.each(manualNodeSelect, function(j, json) {
          if (json.NodeName == node.id) {
            nextRoute = json.NextRoute
            return false
          }
        })
        // 遍历连线，过滤分支
        $.each(node.endLines, function(k, line) {
          // 决定走哪条分支
          if (nextRoute != "") {
            if (line.endNode.id == nextRoute) {
              lbpm.globals.pushNode(nextNodes, line.endNode)
              return false
            }
          } else {
            lbpm.globals.pushNode(nextNodes, line.endNode)
          }
        })
      } else {
        $.each(node.endLines, function(k, line) {
          lbpm.globals.pushNode(nextNodes, line.endNode)
        })
      }
    })
    return nextNodes
  }

  // 不加入相同的节点
  workItem.pushNode = lbpm.globals.pushNode = function(nextNodes, nodeToPush) {
    var flag = true
    $.each(nextNodes, function(i, node) {
      if (node.id == nodeToPush.id) {
        flag = false
        return false
      }
    })
    if (flag) {
      nextNodes.push(nodeToPush)
    }
  }

  //获取已经选择了分支的人工决策节点（[{NodeName:N3, NextRoute:N4},{NodeName:N9, NextRoute:N11}]）
  workItem.getSelectedManualNode = lbpm.globals.getSelectedManualNode = function() {
    var manualNodeSelect = new Array()
    $("input[key='manualFutureNodeId']:checked").each(function(index, input) {
      var json = {}
      input = $(input)
      json.NodeName = input.attr("manualBranchNodeId")
      json.NextRoute = input.val()
      manualNodeSelect.push(json)
    })
    return manualNodeSelect
  }

  //判断是否所有的人工决策节点都选择了要走的分支
  workItem.isSelectAllManualNode = lbpm.globals.isSelectAllManualNode = function() {
    var isSelectAll = true
    $.each($("input[key='manualFutureNodeId']"), function(index, input) {
      isSelectAll = false
      var radioes = $(
        "input[manualBranchNodeId='" +
          $(input).attr("manualBranchNodeId") +
          "']"
      )
      $.each(radioes, function(j, radio) {
        if (radio.checked) {
          isSelectAll = true
          return false
        }
      })
      if (!isSelectAll) {
        return false
      }
    })
    return isSelectAll
  }
  //初始化
  workItem.init = function() {
    lbpm.globals.getOperationParameterJson(
      "futureNodeId:draftDecidedFactIds:dayOfNotifyDrafter:hourOfNotifyDrafter:minuteOfNotifyDrafter",
      null,
      null,
      function() {}
    ) // 统一加载数据
    lbpm.globals.showHandlerIdentityRow()
    lbpm.globals.showManualBranchNodeRow()
    var dayOfNotifyDrafterObj = $("#dayOfNotifyDrafter")[0]
    if (dayOfNotifyDrafterObj != null) {
      var dayOfNotifyDrafterParam = lbpm.globals.getOperationParameterJson(
        "dayOfNotifyDrafter"
      )
      dayOfNotifyDrafterObj.value =
        !dayOfNotifyDrafterParam || dayOfNotifyDrafterParam == ""
          ? "0"
          : dayOfNotifyDrafterParam
    }
    var hourOfNotifyDrafter = $("#hourOfNotifyDrafter")[0]
    if (hourOfNotifyDrafter != null) {
      var hourOfNotifyDrafterParam = lbpm.globals.getOperationParameterJson(
        "hourOfNotifyDrafter"
      )
      hourOfNotifyDrafter.value =
        !hourOfNotifyDrafterParam || hourOfNotifyDrafterParam == ""
          ? "0"
          : hourOfNotifyDrafterParam
    }
    var minuteOfNotifyDrafter = $("#minuteOfNotifyDrafter")[0]
    if (dayOfNotifyDrafterObj != null) {
      var minuteOfNotifyDrafterParam = lbpm.globals.getOperationParameterJson(
        "minuteOfNotifyDrafter"
      )
      minuteOfNotifyDrafter.value =
        !minuteOfNotifyDrafterParam || minuteOfNotifyDrafterParam == ""
          ? "0"
          : minuteOfNotifyDrafterParam
    }
  }
  return workItem
})
