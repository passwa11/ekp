define([
  "mui/i18n/i18n!sys-lbpmservice:lbpmProcess.handler.usageContent.default",
  "mui/i18n/i18n!sys-lbpmservice:lbpmSupport",
  "mui/i18n/i18n!sys-lbpmservice:label.formula.show",
  "mui/i18n/i18n!sys-lbpmservice:lbpmNode",
  "mui/i18n/i18n!sys-lbpmservice:lbpmProcess.handler.usageContent.notNull",
  "dojo/topic"
], function(msg1, msg2, msg3, msg4, msg6, topic) {
  window.defaultUsageContent = ""
  window.defaultUsageContent_refuse = ""
  window.defaultUsageContent_commission = ""
  window.defaultUsageContent_communicate = ""
  window.defaultUsageContent_abandon = ""
  window.defaultUsageContent_superRefuse = ""
  window.defaultUsageContent_sign = ""
  window.defaultUsageContent_additionSign = ""
  window.defaultUsageContent_assign = ""
  window.defaultUsageContent_assignPass = ""
  window.defaultUsageContent_assignRefuse = ""
  window.defaultUsageContent_jump = ""
  window.defaultUsageContent_nodeSuspend = ""
  window.defaultUsageContent_nodeResume = ""
  window.isPassContentRequired = "false"
  window.isRefuseContentRequired = "true"
  window.isCommissionContentRequired = "false"
  window.isCommunicateContentRequired = "true"
  window.isAbandonContentRequired = "true"
  window.isSuperRefuseContentRequired = "true"
  window.isSignContentRequired = "true"
  window.isAdditionSignContentRequired = "false"
  window.isAssignContentRequired = "false"
  window.isAssignPassContentRequired = "false"
  window.isAssignRefuseContentRequired = "false"
  window.isJumpContentRequired = "false"
  window.isNodeSuspendContentRequired = "false"
  window.isNodeResumeContentRequired = "false"

  window.isPassContentValidated = "false";
  window.isRefuseContentValidated = "false";
  window.isCommissionContentValidated = "false";
  window.isCommunicateContentValidated = "false";
  window.isAbandonContentValidated = "false";
  window.isSuperRefuseContentValidated = "false";
  window.isSignContentValidated = "false";
  window.isAdditionSignContentValidated = "false";
  window.isAssignContentValidated = "false";
  window.isAssignPassContentValidated = "false";
  window.isAssignRefuseContentValidated = "false";
  window.isJumpContentValidated = "false";
  window.isNodeSuspendContentValidated = "false";
  window.isNodeResumeContentValidated = "false";
  //定义常量
  var constant = lbpm.workitem.constant
  constant.COMMONHANDLERISFORMULA = msg2["lbpmSupport.HandlerIsFormula"]
  constant.COMMONHANDLERISMATRIX = msg2["lbpmSupport.HandlerIsMatrix"]
  constant.COMMONSELECTADDRESS = msg2["lbpmSupport.selectAddress"]
  constant.COMMONSELECTFORMLIST = msg2["lbpmSupport.selectFormList"]
  constant.COMMONSELECTALTERNATIVE = msg2["lbpmSupport.selectOptList"]
  constant.COMMONLABELFORMULASHOW = msg3["label.formula.show"]
  constant.COMMONCHANGEPROCESSORSELECT =
    msg4["lbpmNode.processingNode.changeProcessor.select"]
  constant.COMMONNODEHANDLERORGEMPTY = msg4["lbpmNode.nodeHandler.orgEmpty"]
  constant.COMMONHANDLERISRULE = msg2["lbpmSupport.HandlerIsRule"];
  constant.COMMONPAGEFIRSTOPTION = "==请选择=="
  var content = (constant.COMMONHANDLERUSAGECONTENTDEFAULT = new Array())
  var contentR = (constant.COMMONHANDLERUSAGECONTENTDEFAULT_ISREQUIRED = new Array())
   var contentV = (constant.COMMONHANDLERUSAGECONTENTDEFAULT_ISVALIDATED = new Array())

  // 同步方法，业务上是默认回复语，需要按钮触发，置于最后加载
  topic.subscribe("mui/lbpmservice/drawReady", function() {
    setTimeout(function() {
      var data = new KMSSData()
      data.AddBeanData("lbpmUsageContentService")
      data = data.GetHashMapArray();
      if (data.length > 0) {
        if (data[0].defaultUsageContent) {
          defaultUsageContent = unescape(data[0].defaultUsageContent)
        }
        if (data[0].defaultUsageContent_refuse) {
          defaultUsageContent_refuse = unescape(
            data[0].defaultUsageContent_refuse
          )
        }
        if (data[0].defaultUsageContent_commission) {
          defaultUsageContent_commission = unescape(
            data[0].defaultUsageContent_commission
          )
        }
        if (data[0].defaultUsageContent_communicate) {
          defaultUsageContent_communicate = unescape(
            data[0].defaultUsageContent_communicate
          )
        }
        if (data[0].defaultUsageContent_abandon) {
          defaultUsageContent_abandon = unescape(
            data[0].defaultUsageContent_abandon
          )
        }
        if (data[0].defaultUsageContent_superRefuse) {
          defaultUsageContent_superRefuse = unescape(
            data[0].defaultUsageContent_superRefuse
          )
        }
        if (data[0].defaultUsageContent_sign) {
          defaultUsageContent_sign = unescape(data[0].defaultUsageContent_sign)
        }
        if (data[0].defaultUsageContent_additionSign) {
          defaultUsageContent_additionSign = unescape(
            data[0].defaultUsageContent_additionSign
          )
        }
        if (data[0].defaultUsageContent_assign) {
          defaultUsageContent_assign = unescape(
            data[0].defaultUsageContent_assign
          )
        }
        if (data[0].defaultUsageContent_assignPass) {
          defaultUsageContent_assignPass = unescape(
            data[0].defaultUsageContent_assignPass
          )
        }
        if (data[0].defaultUsageContent_assignRefuse) {
          defaultUsageContent_assignRefuse = unescape(
            data[0].defaultUsageContent_assignRefuse
          )
        }
        if (data[0].defaultUsageContent_jump) {
	      defaultUsageContent_jump = unescape(
	        data[0].defaultUsageContent_jump
	      )
	    }
        if (data[0].defaultUsageContent_nodeSuspend) {
          defaultUsageContent_nodeSuspend = unescape(
            data[0].defaultUsageContent_nodeSuspend
          )
        }
        if (data[0].defaultUsageContent_nodeResume) {
          defaultUsageContent_nodeResume = unescape(
            data[0].defaultUsageContent_nodeResume
          )
        }
        if (data[0].isPassContentRequired) {
          isPassContentRequired = data[0].isPassContentRequired
        }
        if (data[0].isRefuseContentRequired) {
          isRefuseContentRequired = data[0].isRefuseContentRequired
        }
        if (data[0].isCommissionContentRequired) {
          isCommissionContentRequired = data[0].isCommissionContentRequired
        }
        if (data[0].isCommunicateContentRequired) {
          isCommunicateContentRequired = data[0].isCommunicateContentRequired
        }
        if (data[0].isAbandonContentRequired) {
          isAbandonContentRequired = data[0].isAbandonContentRequired
        }
        if (data[0].isSuperRefuseContentRequired) {
          isSuperRefuseContentRequired = data[0].isSuperRefuseContentRequired
        }
        if (data[0].isSignContentRequired) {
          isSignContentRequired = data[0].isSignContentRequired
        }
        if (data[0].isAdditionSignContentRequired) {
          isAdditionSignContentRequired = data[0].isAdditionSignContentRequired
        }
        if (data[0].isAssignContentRequired) {
          isAssignContentRequired = data[0].isAssignContentRequired
        }
        if (data[0].isAssignPassContentRequired) {
          isAssignPassContentRequired = data[0].isAssignPassContentRequired
        }
        if (data[0].isAssignRefuseContentRequired) {
          isAssignRefuseContentRequired = data[0].isAssignRefuseContentRequired
        }
        if (data[0].isJumpContentRequired) {
          isJumpContentRequired = data[0].isJumpContentRequired
        }
        if (data[0].isNodeSuspendContentRequired) {
          isNodeSuspendContentRequired = data[0].isNodeSuspendContentRequired
        }
        if (data[0].isNodeResumeContentRequired) {
          isNodeResumeContentRequired = data[0].isNodeResumeContentRequired
        }
        
        if(data[0].isPassContentValidated){
    		isPassContentValidated = data[0].isPassContentValidated;
    	}
    	if(data[0].isRefuseContentValidated){
    		isRefuseContentValidated = data[0].isRefuseContentValidated;
    	}
    	if(data[0].isCommissionContentValidated){
    		isCommissionContentValidated = data[0].isCommissionContentValidated;
    	}
    	if(data[0].isCommunicateContentValidated){
    		isCommunicateContentValidated = data[0].isCommunicateContentValidated;
    	}
    	if(data[0].isAbandonContentValidated){
    		isAbandonContentValidated = data[0].isAbandonContentValidated;
    	}
    	if(data[0].isSuperRefuseContentValidated){
    		isSuperRefuseContentValidated = data[0].isSuperRefuseContentValidated;
    	}
    	if(data[0].isSignContentValidated){
    		isSignContentValidated = data[0].isSignContentValidated;
    	}
    	if(data[0].isAdditionSignContentValidated){
    		isAdditionSignContentValidated = data[0].isAdditionSignContentValidated;
    	}
    	if(data[0].isAssignContentValidated){
    		isAssignContentValidated = data[0].isAssignContentValidated;
    	}
    	if(data[0].isAssignPassContentValidated){
    		isAssignPassContentValidated = data[0].isAssignPassContentValidated;
    	}
    	if(data[0].isAssignRefuseContentValidated){
    		isAssignRefuseContentValidated = data[0].isAssignRefuseContentValidated;
    	}
    	if(data[0].isJumpContentValidated){
    		isJumpContentValidated = data[0].isJumpContentValidated;
    	}
    	if(data[0].isNodeSuspendContentValidated){
    		isNodeSuspendContentValidated = data[0].isNodeSuspendContentValidated;
    	}
    	if(data[0].isNodeResumeContentValidated){
    		isNodeResumeContentValidated = data[0].isNodeResumeContentValidated;
    	}

        content["handler_pass"] = defaultUsageContent
        content["handler_refuse"] = defaultUsageContent_refuse
        content["handler_commission"] = defaultUsageContent_commission
        content["handler_communicate"] = defaultUsageContent_communicate
        content["handler_abandon"] = defaultUsageContent_abandon
        content["handler_superRefuse"] = defaultUsageContent_superRefuse
        content["handler_sign"] = defaultUsageContent_sign
        content["handler_additionSign"] = defaultUsageContent_additionSign
        content["handler_assign"] = defaultUsageContent_assign
        content["handler_assignPass"] = defaultUsageContent_assignPass
        content["handler_assignRefuse"] = defaultUsageContent_assignRefuse
        content["handler_jump"] = defaultUsageContent_jump
        content["handler_nodeSuspend"] = defaultUsageContent_nodeSuspend
        content["handler_nodeResume"] = defaultUsageContent_nodeResume

        contentR["handler_pass"] = isPassContentRequired
        contentR["handler_refuse"] = isRefuseContentRequired
        contentR["handler_commission"] = isCommissionContentRequired
        contentR["handler_communicate"] = isCommunicateContentRequired
        contentR["handler_cancelCommunicate"] = isCommunicateContentRequired
        contentR["handler_abandon"] = isAbandonContentRequired
        contentR["handler_superRefuse"] = isSuperRefuseContentRequired
        contentR["handler_sign"] = isSignContentRequired
        contentR["handler_additionSign"] = isAdditionSignContentRequired
        contentR["handler_assign"] = isAssignContentRequired
        contentR["handler_assignPass"] = isAssignPassContentRequired
        contentR["handler_assignRefuse"] = isAssignRefuseContentRequired
        contentR["handler_jump"] = isJumpContentRequired
        contentR["handler_nodeSuspend"] = isNodeSuspendContentRequired
        contentR["handler_nodeResume"] = isNodeResumeContentRequired
        
        contentV["handler_pass"] = isPassContentValidated
        contentV["handler_refuse"] = isRefuseContentValidated
        contentV["handler_commission"] = isCommissionContentValidated
        contentV["handler_communicate"] = isCommunicateContentValidated
        contentV["handler_cancelCommunicate"] = isCommunicateContentValidated
        contentV["handler_abandon"] = isAbandonContentValidated
        contentV["handler_superRefuse"] = isSuperRefuseContentValidated
        contentV["handler_sign"] = isSignContentValidated
        contentV["handler_additionSign"] = isAdditionSignContentValidated
        contentV["handler_assign"] = isAssignContentValidated
        contentV["handler_assignPass"] = isAssignPassContentValidated
        contentV["handler_assignRefuse"] = isAssignRefuseContentValidated
        contentV["handler_jump"] = isJumpContentValidated
        contentV["handler_nodeSuspend"] = isNodeSuspendContentValidated
        contentV["handler_nodeResume"] = isNodeResumeContentValidated
      }
    }, 1)
  })

  constant.COMMONUSAGECONTENTNOTNULL =
    msg6["lbpmProcess.handler.usageContent.notNull"]
  constant.COMMONUSAGES = msg4["lbpmNode.createDraft.commonUsages"]
  constant.handlerOperationTypepass =
    msg4["lbpmNode.processingNode.operationsTDTitle.handlerOperationTypepass"]
  constant.FUTURENODESTIP =
      msg4["lbpmNode.futureNodes.tip"]
  //覆盖全局定义的常量
  ;(function(constant) {
    constant.MUSTMODIFYHANDLERNODEIDSISNULL =
      msg4["lbpmNode.flowContent.mustModifyHandlerNodeIds.mobile.isNull"]
    constant.VALIDATENEXTNODEHANDLERISNULL =
      msg4["lbpmNode.validate.nextNodeHandler.mobile.isNull"]
  })(lbpm.constant)
})
