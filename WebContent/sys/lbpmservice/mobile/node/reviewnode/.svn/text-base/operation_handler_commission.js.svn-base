define([
  "dijit/registry",
  "dojo/topic", 
  "dojo/query",
  "sys/lbpmservice/mobile/common/syslbpmprocess",
  "mui/dialog/Tip",
  "mui/i18n/i18n!sys-lbpmservice:lbpmNode",
  "mui/i18n/i18n!sys-lbpmservice-support:lbpmOperations.turnToDo.hiddenNote"
], function(registry,topic, query, syslbpmprocess, tip, msg, msg2) {
  /*******************************************************************************
	 * 功能：处理人“转办”操作的审批所用JSP，此JSP路径在处理人“转办”操作扩展点定义的reviewJs参数匹配
	  使用：
	  作者：罗荣飞
	 创建时间：2012-06-06
	 ******************************************************************************/

  var constant = lbpm.constant
  //定义常量
  constant.opt.CommissionPeople =
    msg["lbpmNode.processingNode.operationsTDTitle.handlerOperationType.people"]
  constant.opt.CommissionIsNull =
    msg["lbpmNode.validate.toOtherHandlerIds.Commission.isNull"]
  constant.opt.returnToCommissionedPerson =
    msg[
      "lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeCommission.returnToCommissionedPerson"
    ]
  constant.opt.TurnToDoNoteHiddenNote =
    msg2["lbpmOperations.turnToDo.hiddenNote"]

  var commission = {}

  var OperationBlur = function() {
    lbpm.globals.clearDefaultUsageContent("handler_commission")
  }

  //处理人操作：转办
  var OperationClick = function(operationName) {
    lbpm.globals.setDefaultUsageContent("handler_commission")
    var operationsRow = document.getElementById("operationsRow")
    var operationsTDTitle = document.getElementById("operationsTDTitle")
    var operationsTDContent = document.getElementById("operationsTDContent")
    operationsTDTitle.innerHTML =
      operationName + lbpm.constant.opt.CommissionPeople
    var currentOrgIdsObj = document.getElementById(
      "sysWfBusinessForm.fdHandlerRoleInfoIds"
    )
    var addressSubject = msg['lbpmNode.toOtherHandler.address.subject.select']+operationName+msg['lbpmNode.toOtherHandler.address.subject.person'];
    var options = {
      mulSelect: false,
      idField: "toOtherHandlerIds",
      nameField: "toOtherHandlerNames",
      splitStr: ";",
      selectType: ORG_TYPE_PERSON | ORG_TYPE_POST,
      notNull: true,
      exceptValue: currentOrgIdsObj.value.split(";"),
      text: lbpm.constant.SELECTORG,
      alertText:lbpm.constant.opt.CommissionIsNull,
      validate:'commissionHandlerRequired required',
      isShowSubject:true,
      id:'toHandlerAddress',
      subject:addressSubject
    }
    var html = ""
    var currentNodeObj = lbpm.nodes[lbpm.nowNodeId]
    if (
      currentNodeObj.operationScope &&
      currentNodeObj.operationScope["handler_commission"] &&
      currentNodeObj.operationScope["handler_commission"] != "all"
    ) {
      var scopeType =
        currentNodeObj.operationScope["handler_commission"] == null
          ? ""
          : currentNodeObj.operationScope["handler_commission"]
      var customHandlerSelectType =
        currentNodeObj.operationScope[
          "handler_commission_customHandlerSelectType"
        ] == null
          ? "org"
          : currentNodeObj.operationScope[
              "handler_commission_customHandlerSelectType"
            ]
      var handlerSelectType =
        currentNodeObj.handlerSelectType == null
          ? "org"
          : currentNodeObj.handlerSelectType
      var customHandlerIds =
        currentNodeObj.operationScope["handler_commission_customIds"] == null
          ? ""
          : currentNodeObj.operationScope["handler_commission_customIds"]
      var handlerIdentity = $(
        "input[name='sysWfBusinessForm.fdHandlerRoleInfoIds']"
      )
        .val()
        .split(";")[0]
      //限定范围人员
      var dataUrl =
        "&currentId=" +
        lbpm.nowProcessorInfoObj.expectedId +
        "&handlerIdentity=" +
        handlerIdentity +
        "&customHandlerSelectType=" +
        customHandlerSelectType +
        "&customHandlerIds=" +
        encodeURIComponent(customHandlerIds) +
        "&scopeType=" +
        scopeType +
        "&fdModelName=" +
        lbpm.modelName +
        "&fdModelId=" +
        lbpm.modelId +
        "&exceptValue=" +
        currentOrgIdsObj.value

      html +=
        "<input type='hidden' name='toOtherHandlerIds' id='toOtherHandlerIds'/>"
      html +=
        "<input type='hidden' name='toOtherHandlerNames' id='toOtherHandlerNames'/>"
      html +=
        '<div id=\'toHandlerAddress\' data-dojo-type="sys/lbpmservice/mobile/opthandler/OptHandler"' +
        " data-dojo-props=\"subject:\'"+addressSubject+"\',validate:\'commissionHandlerRequired required\',required:true,showHeadImg:true,idField:'toOtherHandlerIds',isMul:false," +
        "dataUrl:'/sys/lbpmservice/mobile/opthanlder.do?method=scopeHandlers" +
        dataUrl +
        "'," +
        "nameField:'toOtherHandlerNames'\"></div>"
    } else {
      html = lbpm.address.html_build(options)
    }

    // 是否转办隐藏意见
    if (
      Lbpm_SettingInfo &&
      Lbpm_SettingInfo["isHiddeTurnToDoNoteConfigurable"] == "true" &&
      lbpm.flowcharts["isHiddeTurnToDoNoteConfigurable"] != "true"
    ) {
      html += '<div class="isHiddenNote">'
      html +=
        '<div id="isHiddenNote" alertText="" key="isHiddenNote" data-dojo-type="mui/form/CheckBox"'
      html +=
        " data-dojo-props=\"name:'isHiddenNote', value:'true', mul:false, text:'"
      html +=
        lbpm.constant.opt.TurnToDoNoteHiddenNote.replace(
          "{commission}",
          operationName
        ) + "'"
      html += '"></div></div>'
    }

    // 在转办时，增加“流程重新流经本节点时，直接由转办人员处理 ”的开关
    if (currentNodeObj.handlerSelectType == "org") {
      html += '<div class="returnToCommissionedPerson">'
      html +=
        '<div id="returnToCommissionedPerson" alertText="" key="returnToCommissionedPerson" data-dojo-type="mui/form/CheckBox"'
      html +=
        " data-dojo-props=\"name:'returnToCommissionedPerson', value:'true', mul:false, text:'"
      html +=
        lbpm.constant.opt.returnToCommissionedPerson.replace(
          "{commission}",
          operationName
        ) + "'"
      html += '"></div></div>'
    }

    query("#operationsTDContent").html(html, {parseContent: true, onEnd: function() {
		this.inherited("onEnd", arguments);
		if (this.parseDeferred) {
			this.parseDeferred.then(function() {
				//这里的时候地址本已经解析完毕，发出事件让弹窗出现
				topic.publish("/lbpm/operation/toHandlerAddress/parseFinish");
			});
		}
	}})
    lbpm.globals.hiddenObject(operationsRow, false)
  }

  //“转办”操作的检查
  var OperationCheck = function() {
    var input = query("#toOtherHandlerIds")[0]
    if (input && input.value == "") {
    	tip["warn"]({text:lbpm.constant.opt.CommissionIsNull});
      //alert(lbpm.constant.opt.CommissionIsNull)
      return false
    }
    if (lbpm.globals.isUsageContenRequired(lbpm.currentOperationType)) {
      return lbpm.globals.validateMustSignYourSuggestion()
    }
    return true
  }

  //设置"转办"操作的参数
  var setOperationParam = function() {
    //转办人员
    var input = query("#toOtherHandlerIds")[0]
    lbpm.globals.setOperationParameterJson(input, null, "param")
    var widget = registry.byId("returnToCommissionedPerson")
    if (widget) {
      lbpm.globals.setOperationParameterJson(
        widget.get("checked"),
        "returnToCommissionedPerson",
        "param"
      )
    }

    var hiddenOpetion = registry.byId("isHiddenNote")
    if (hiddenOpetion) {
      lbpm.globals.setOperationParameterJson(
        hiddenOpetion.get("checked"),
        "isHiddenNote",
        "param"
      )
    }
  }

  commission["handler_commission"] = lbpm.operations["handler_commission"] = {
    click: OperationClick,
    check: OperationCheck,
    blur: OperationBlur,
    setOperationParam: setOperationParam
  }

  commission.init = function() {}

  return commission
})
