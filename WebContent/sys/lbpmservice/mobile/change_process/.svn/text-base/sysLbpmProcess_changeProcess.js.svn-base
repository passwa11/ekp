define([
  "dojo/_base/array",
  "dojo/topic",
  "dijit/registry",
  "mui/util",
  "mui/form/Address",
  "dojo/query"
], function(array, topic, registry, util, Address, query) {
  var changePrcess = {}
  var checkModifyNodeAuthorization = function(
    nodeObj,
    allowModifyNodeId,
    throughNodesStr
  ) {
    var index, nodeIds
    throughNodesStr += ","
    //如果要修改的节点不在当前计算后应该出现的节点中（及自动决策将流向的分支）这不出现在待修改列表中
    if (throughNodesStr.indexOf(allowModifyNodeId + ",") == -1) {
      return false
    }
    if (
      nodeObj.canModifyHandlerNodeIds != null &&
      nodeObj.canModifyHandlerNodeIds != ""
    ) {
      nodeIds = nodeObj.canModifyHandlerNodeIds + ";"
      index = nodeIds.indexOf(allowModifyNodeId + ";")
      if (index != -1) {
        return true
      }
    }
    if (
      nodeObj.mustModifyHandlerNodeIds != null &&
      nodeObj.mustModifyHandlerNodeIds != ""
    ) {
      nodeIds = nodeObj.mustModifyHandlerNodeIds + ";"
      index = nodeIds.indexOf(allowModifyNodeId + ";")
      if (index != -1) {
        return true
      }
    }

    return false
  }

  var getAllNodeArray = function() {
    var rtnNodeArray = new Array()
    for (var key in lbpm.nodes) {
      var node = lbpm.nodes[key]
      var nodeDescObj = lbpm.nodedescs[node.nodeDescType]
      if (nodeDescObj["isHandler"](node) && !nodeDescObj["isBranch"](node)) {
        rtnNodeArray.push(node)
      }
    }
    rtnNodeArray.sort(lbpm.globals.getNodeSorter());
    return rtnNodeArray
  }

  var updateFutureHandlers = function(key, idValue, nameValue, address) {
    if (key.indexOf("handlerIds_") == -1) {
      return
    }
    var nodeId = key.replace("handlerIds_", "")

    var param = {}
    param.sourceObj = location.href
    var handlerSelectType = "org"
    if (address && address.handlerSelectType && idValue == "") {
      handlerSelectType = address.handlerSelectType
    }
    param.nodeInfos = [
      {
        id: nodeId,
        handlerSelectType: handlerSelectType,
        handlerIds: idValue,
        handlerNames: nameValue
      }
    ]
    param.isChangeHandlerAddress = address.isChangeHandlerAddress == true ? true : false;
    lbpm.events.fireListener(lbpm.constant.EVENT_MODIFYNODEATTRIBUTE, param)
  }

  var getSelectType = function(node) {
    var selectType = lbpm.constant.ADDRESS_SELECT_POSTPERSONROLE
    if (lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND, node)) {
      selectType = lbpm.constant.ADDRESS_SELECT_ALLROLE
    }
    if (node.nodeDescType == "shareReviewNodeDesc") {
      selectType = "ORG_TYPE_PERSON"
    }
    return selectType
  }

  var getMulSelect = function(node) {
    if (node.nodeDescType == "shareReviewNodeDesc") {
      return false
    }
    return true
  }

  var build_change_panel = function() {
    var modelName = lbpm.modelName
    var modelId = lbpm.modelId
    lbpm.globals.getThroughNodes(
      function(throughtNodes) {
        var throughNodesStr = lbpm.globals.getIdsByNodes(throughtNodes)
        var currentNode = lbpm.globals.getNodeObj(lbpm.nowNodeId)
        var nextNodeArray = getAllNodeArray()
        var htmls = []
        var nodeIds = []
        for (var i = 0; i < nextNodeArray.length; i++) {
          var nextNode = nextNodeArray[i]
          var nextNodeId = nextNode.id
          if (!lbpm.globals.judgeIsNecessaryAlert(nextNode)) continue
          if (
            checkModifyNodeAuthorization(
              currentNode,
              nextNodeId,
              throughNodesStr
            )
          ) {
            var handlerSelectType = nextNode.handlerSelectType
            var handlerIds =
              nextNode.handlerIds == null ? "" : nextNode.handlerIds
            var handlerNames =
              nextNode.handlerNames == null ? "" : nextNode.handlerNames
            //若是公式，规则或者矩阵组织（handlerids置为空，和pc保持一致，这样地址本才不会报错）
            if (handlerSelectType == "formula") {
            	handlerIds = !nextNode.handlerIds ? "" : "formula";
            	//handlerIds = ""	;
              handlerNames = lbpm.workitem.constant.COMMONHANDLERISFORMULA
            } else if (handlerSelectType == "matrix") {
              handlerIds = !nextNode.handlerIds ? "" : "matrix"
            	//handlerIds = ""	;
              handlerNames = lbpm.workitem.constant.COMMONHANDLERISMATRIX
            } else if (handlerSelectType == "rule") {
              handlerIds = !nextNode.handlerIds ? "" : "rule"
            	//handlerIds = ""	;
              handlerNames = lbpm.workitem.constant.COMMONHANDLERISRULE
            }
            var handlerIdsKey = "handlerIds_" + nextNodeId
            var handlerNamesKey = "handlerNames_" + nextNodeId
            var handlerIdentity = (function() {
              if (
                nextNode.optHandlerCalType == null ||
                nextNode.optHandlerCalType == "2"
              ) {
                var rolesSelectObj = query("input[name='rolesSelectObj']")
                if (
                  rolesSelectObj.length > 0 &&
                  rolesSelectObj[0].selectedIndex > -1
                ) {
                  return rolesSelectObj[0].value
                }
                return query("input[name='sysWfBusinessForm.fdIdentityId']")[0]
                  .value
              }
              var rolesIdsArray = query(
                "input[name='sysWfBusinessForm.fdHandlerRoleInfoIds']"
              )[0].value.split(";")
              return rolesIdsArray[0]
            })()
            var options = {
              idField: handlerIdsKey,
              nameField: handlerNamesKey,
              mulSelect: getMulSelect(nextNode),
              splitStr: ";",
              selectType: getSelectType(nextNode),
              addAction: "lbpm.globals.temp_updateFutureHandlers",
              deleteAction: "lbpm.globals.temp_updateFutureHandlers",
              idValue: handlerIds,
              nameValue: handlerNames,
              handlerSelectType: nextNode.handlerSelectType,
              cateFieldShow: true,
              nodeId:nextNodeId,
              isChangeHandlerAddress:true
            }
            var html = '<div class="modifyNodeAuthorizationSelector">'
            var langNodeName = WorkFlow_getLangLabel(
              nextNode.name,
              nextNode["langs"],
              "nodeName"
            )
            html += "<b>" + nextNode.id + "." + Com_HtmlEscape(langNodeName) + "</b>"
            html +=
              '<div style=\'margin-top: .5rem;\' id="_' +
              handlerIdsKey +
              '_label" class="muiCateFiledShow"></div>'
            html += HandlerHiddenInput(options)
            if (nextNode.useOptHandlerOnly == "true") {
              // 只有备选列表
              html +=
                " " +
                OptHandlerWidget(
                  options,
                  handlerIdentity,
                  nextNode.optHandlerIds,
                  nextNode.optHandlerSelectType,
                  modelName,
                  modelId,
                  nextNode.useOptHandlerOnly
                )
            } else {
              if (
                nextNode.optHandlerIds ||
                nextNode.optHandlerSelectType == "dept" ||
                nextNode.optHandlerSelectType == "mechanism" || 
                nextNode.optHandlerSelectType == "otherOrgDept"
              ) {
                options["groupBtn"] = true
                html +=
                  '<div data-dojo-type="sys/lbpmservice/mobile/change_process/AddressButtonGroup" data-dojo-props="icon1:\'mui mui-address\'">'
                html += MobileAddress(options)
                html += OptHandlerWidget(
                  options,
                  handlerIdentity,
                  nextNode.optHandlerIds,
                  nextNode.optHandlerSelectType,
                  modelName,
                  modelId,
                  nextNode.useOptHandlerOnly
                )
                html += "</div>"
              } else {
                html += " " + MobileAddress(options)
              }
            }
            html += "</div>"
            htmls.push(html)
            nodeIds.push(nextNodeId)
          }
        }
        var detailArea = query("#modifyNodeAuthorizationDetail")
        detailArea.forEach(function(node) {
          //TODO 由于修改处理人时，地址本的修改事件会调用自身所在区域的重绘，导致组件对象无法释放，故原组件对象设置属性_overTime加以区分，后续在优化
          array.forEach(registry.findWidgets(node), function(widget) {
            widget._overTime = true
            widget.destroy && !widget._destroyed && widget.destroy()
          })
        })
        if (htmls.length > 0) {
          query("#modifyNodeAuthorizationTr").style("display", "")
          detailArea.html(htmls.join(""), {parseContent: true})
        } else {
          query("#modifyNodeAuthorizationTr").style("display", "none")
          detailArea.html()
        }
      },
      null,
      null,
      false,
      lbpm.nowNodeId
    )
    //重新校验一下
    query(".opt_address").forEach(function(node){
    	var wgt = registry.byNode(node);
    	if(wgt && wgt.validation){
    		wgt.validation.validateElement(wgt);
    	}
    })
  }

  var alertText = function(options) {
    return options.alertText ? options.alertText : ""
  }

  var idNameKeyHtml = function(field) {
    return "id=" + field + " name=" + field + " key=" + field + ""
  }

  var HandlerHiddenInput = function(options) {
    var idValue = options["idValue"] || ""
    var nameValue = options["nameValue"] || ""
    var html =
      "<input type='hidden' alertText='" +
      alertText(options) +
      "' value='" +
      util.formatText(idValue) +
      "' " +
      idNameKeyHtml(options.idField) +
      ">" +
      "<input type='hidden' " +
      idNameKeyHtml(options.nameField) +
      " alertText='' value='" +
      util.formatText(nameValue) +
      "'>"
    return html
  }

  var OptHandlerWidget = function(
    options,
    handlerIdentity,
    optHandlerIds,
    optHandlerSelectType,
    modelName,
    modelId,
    useOptHandlerOnly
  ) {
    var idValue = options["idValue"] || ""
    var nameValue = options["nameValue"] || ""
    var nodeid = options['nodeId'] || '';
    var mixin = ""
    if (options.groupBtn) {
      mixin +=
        dojoConfig.baseUrl +
        "sys/lbpmservice/mobile/change_process/GroupButtonMixin.js"
    }
	var validateStr = '';
    if(useOptHandlerOnly == "true"){//只有备选时需要挂上校验
    	validateStr = " validate:\'futureNodeObjs\', ";
    }
    var html =
      '<div class=\'opt_address\' data-dojo-type="sys/lbpmservice/mobile/opthandler/OptHandler"' +
      ' data-dojo-mixins="' +
      mixin +
      '"' +
      " data-dojo-props=\""+validateStr+"idField:'" +
      util.formatText(options.idField) +
      "'," +
      "optHandlerIds:'" +
      util.formatText(optHandlerIds) +
      "'," +
      "handlerIdentity:'" +
      util.formatText(handlerIdentity) +
      "'," +
      "optHandlerSelectType:'" +
      optHandlerSelectType +
      "'," +
      "handlerSelectType:'" +
      (options.handlerSelectType == null ? "org" : options.handlerSelectType) +
      "'," +
      "fdModelName:'" +
      modelName +
      "'," +
      "fdModelId:'" +
      modelId +
      "'," +
      "curIds:'" +
      util.formatText(idValue) +
      "'," +
      "value:'" +
      util.formatText(idValue) +
      "'," +
      "optClass:'fontmuis muis-new optAddressAdd'," +
      "curNames:'" +
      util.formatText(nameValue) +
      "'," +
      "nodeid:'" +
      nodeid +
      "'," +
      "nameField:'" +
      options.nameField +
      "'\""
    if (options.groupBtn) html += ' style="display:none" nodeId="'+options.nodeId+'"'
    html += " >";
    html += " </div>"

    //选择的是本部门或者本机构
    if (optHandlerSelectType == "dept" || optHandlerSelectType == "mechanism" || optHandlerSelectType == "otherOrgDept") {
      var scopeType = "dept";
      var deptLimit = "";
      if (optHandlerSelectType == "mechanism") {
        scopeType = "org"
      }else if(optHandlerSelectType == "otherOrgDept"){
    	  scopeType = "otherOrgDept";
    	  deptLimit = 'otherOrgDept-' + optHandlerIds;
      }
      var currentOrgIdsObj = document.getElementById(
        "sysWfBusinessForm.fdHandlerRoleInfoIds"
      )
      //限定范围人员
      var dataUrl =
        "&currentId=" +
        lbpm.nowProcessorInfoObj.expectedId +
        "&handlerIdentity=" +
        handlerIdentity +
        "&scopeType=" +
        scopeType +
        "&fdModelName=" +
        modelName +
        "&fdModelId=" +
        modelId +
        "&exceptValue=" +
        currentOrgIdsObj.value +
        "&deptLimit=" +
        deptLimit

      html =
        '<div class=\'opt_address\' data-dojo-type="sys/lbpmservice/mobile/opthandler/OptHandler"' +
        ' data-dojo-mixins="' +
        mixin +
        '"' +
        " data-dojo-props=\"idField:'" +
        util.formatText(options.idField) +
        "'," +
        "optHandlerIds:'" +
        util.formatText(optHandlerIds) +
        "'," +
        "handlerIdentity:'" +
        util.formatText(handlerIdentity) +
        "'," +
        "optHandlerSelectType:'" +
        optHandlerSelectType +
        "'," +
        "handlerSelectType:'" +
        (options.handlerSelectType == null
          ? "org"
          : options.handlerSelectType) +
        "'," +
        "fdModelName:'" +
        modelName +
        "'," +
        "fdModelId:'" +
        modelId +
        "'," +
        "curIds:'" +
        util.formatText(idValue) +
        "'," +
        "value:'" +
        util.formatText(idValue) +
        "'," +
        "optClass:'fontmuis muis-new optAddressAdd'," +
        "curNames:'" +
        util.formatText(nameValue) +
        "'," +
        "dataUrl:'/sys/lbpmservice/mobile/opthanlder.do?method=scopeHandlers" +
        dataUrl +
        "'," +
        "deptLimit:'"+
        deptLimit + 
        "'," +
        "nameField:'" +
        options.nameField +
        "'\""
      if (options.groupBtn) html += ' style="display:none"'
      html += " >";
      html += " </div>"
    }
    return html
  }

  var MobileAddress = function(options) {
    var cateFieldShow =
      ", cateFieldShow:'#_" + options.idField + '_label\'" style="'

    var idValue = options["idValue"] || ""
    var nameValue = options["nameValue"] || ""
    var mixin = ""
    if (options.groupBtn) {
      mixin +=
        dojoConfig.baseUrl +
        "sys/lbpmservice/mobile/change_process/GroupButtonMixin.js"
    }

    var isCalc = false;
    if(options.handlerSelectType == "rule" || options.handlerSelectType == "formula" || options.handlerSelectType == "matrix"){
      //公式定义器，矩阵组织和规则引擎的处理人数据是公式，不可以直接作为地址本的数据处理，处理默认值的情况
      // 需要解析成具体的人员id和name做对象传入，如：lbpm.nodeParsehandlerId 和 lbpm.nodeParseHandlerName
      var nodeData = lbpm.globals.getNodeObj(options.nodeId);
      lbpm.globals.setNodeParseHandler(nodeData);
      isCalc = true;
    }

    //该字段是用来屏蔽修改处理人发出的值改变事件影响即将流向的渲染（原先是会将未选择的即将流向选项隐藏，现在改成不要影响）
    if(!options.isChangeHandlerAddress){
      options.isChangeHandlerAddress = false;
    }

    var html =
      '<div class="opt_address" data-dojo-type="mui/form/Address" data-dojo-mixins="' +
      mixin +
      '"' +
      ' data-dojo-props="validate:\'futureNodeObjs\',type: ' +
      options.selectType +
      ",isChangeHandlerAddress:" +
      options.isChangeHandlerAddress +
      ",idField:'" +
      options.idField +
      "'," +
      "nameField:'" +
      options.nameField +
      "'," +
      "showHeadImg:false," +
      "isMul:" +
      options.mulSelect +
      "," +
      "optClass:'fontmuis muis-new optAddressAdd'," +
      "exceptValue:'" +
      (options.exceptValue == null ? "" : options.exceptValue.join(";")) +
      "'," +
      "curIds:'" + (isCalc ? (util.formatText(lbpm.nodeParseHandlerId ? lbpm.nodeParseHandlerId.value : "")) : util.formatText(idValue)) +
      "'," +
      "value:'" + (isCalc ? (util.formatText(lbpm.nodeParseHandlerId ? lbpm.nodeParseHandlerId.value : "")) : util.formatText(idValue)) +
      "'," +
      "curNames:'" + (isCalc ? (util.formatText(lbpm.nodeParseHandlerName ? lbpm.nodeParseHandlerName.value : "")) : util.formatText(nameValue)) +
      "'," +
      "orient:'none',align:'left" +
      "'," +
      "handlerIds:'" + (isCalc ? (util.formatText(lbpm.nodeParseHandlerId ? lbpm.nodeParseHandlerId.value : "")) : util.formatText(idValue)) +
      "'," +
      "handlerSelectType:'" +
      (options.handlerSelectType == null ? "org" : options.handlerSelectType) +
      "'" +
      '" nodeId="'+options.nodeId+'"></div>'
    return html
  }

  // modifyNodeAuthorizationTr
  var isRootWorkitemOperationFun = function() {
    if (!lbpm.nowProcessorInfoObj) return false
    return array.some(lbpm.nowProcessorInfoObj.operations, function(opt) {
      return lbpm.operations[opt.id] && lbpm.operations[opt.id].isPassType
    })
  }

  changePrcess.init = function() {
    if (!isRootWorkitemOperationFun()) return
    topic.subscribe("/mui/form/valueChanged", function(srcObj, ctx) {
      if (
        srcObj &&
        srcObj instanceof Address &&
        !srcObj._overTime &&
        (srcObj.handlerIds != null || srcObj.optHandlerIds != null)
      ) {
        //修改处理人
        var key = srcObj.key || ctx.key
        updateFutureHandlers(key, srcObj.curIds, srcObj.curNames, srcObj)
      }
    })
    // 此方法涉及同步操作，而此处初始化并没有看到必要性，一般都是点击操作按钮触发事件来进行这个面板的构建【纯属猜测】
    //还原，需要计算下一个是否有修改处理人
    build_change_panel()
    lbpm.events.addListener(
      lbpm.constant.EVENT_SELECTEDFUTURENODE,
      build_change_panel
    )
    lbpm.events.addListener(
        lbpm.constant.EVENT_CHANGEWORKITEM,
        build_change_panel
    )
  }

  return changePrcess
})
