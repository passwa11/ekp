define([
  "mui/i18n/i18n!sys-lbpmservice:validate.isNull",
  "mui/i18n/i18n!sys-lbpmservice:validate.isNaN",
  "mui/i18n/i18n!sys-mobile",
  "mui/i18n/i18n!sys-lbpmservice:lbpmSupport",
  "mui/i18n/i18n!sys-lbpmservice-support:lbpmTemplate.fdType.free",
  "mui/i18n/i18n!sys-lbpmservice:lbpm.freeFlow.mustAppendSomeNode",
  "mui/i18n/i18n!sys-lbpmservice:lbpm.group",
  "mui/i18n/i18n!sys-lbpmservice:WorkFlow.Loading.Msg",
  "mui/i18n/i18n!sys-lbpmservice:mui",
  "mui/i18n/i18n!sys-lbpmservice:lbpmNode"
], function(msg1, msg2, msg3, msg5, msg7, msg8, msg9, msg10, msg11, msg12) {
  //地址本
  constant.ADDRESS_SELECT_POSTPERSONROLE =
    "ORG_TYPE_POSTORPERSON | ORG_TYPE_ROLE"
  constant.ADDRESS_SELECT_ALLROLE = "ORG_TYPE_ALL | ORG_TYPE_ROLE"

  constant.SELECTORG = msg11["mui.dialog.selectOrg"] //选择
  constant.METHODEDIT = "edit"
  constant.METHODVIEW = "view"
  constant.IDENTITY_PROCESSOR = "processor"

  constant.STATE_COMPLETED = "30" //流程结束
  constant.STATE_ACTIVATED = "20" //流程中
  constant.STATE_CREATED = "10" //流程创建

  constant.STATUS_UNINIT = 0 //状态：未初始化
  constant.STATUS_NORMAL = 1 //状态：普通
  constant.STATUS_PASSED = 2 //状态：曾经流过
  constant.STATUS_RUNNING = 3 //状态：当前

  constant.IS_ADMIN = constant.PRIVILEGERFLAG

  //处理人身份标识，起草人
  constant.DRAFTERROLETYPE = "drafter"
  constant.HANDLER_IDENTITY_DRAFT = 1
  //处理人身份标识，处理人
  constant.PROCESSORROLETYPE = "processor"
  constant.HANDLER_IDENTITY_HANDLER = 2
  //处理人身份标识，特权人
  constant.AUTHORITYROLETYPE = "authority"
  constant.HANDLER_IDENTITY_SPECIAL = 3
  //处理人身份标识，已处理人
  constant.HISTORYHANDLERROLETYPE = "historyhandler"
  constant.HANDLER_IDENTITY_HISTORYHANDLER = 4
  //处理人身份标识，分支特权人
  constant.BRANCHADMINROLETYPE = "branchadmin"
  constant.HANDLER_IDENTITY_BRANCHADMINROLETYPE = 5

  constant.SUCCESS = "success"
  constant.FAILURE = "failure"
  constant.VALIDATEISNULL = msg1["validate.isNull"]
  constant.VALIDATEISNAN = msg2["validate.isNaN"]

  //节点类型
  constant.NODETYPE_START = "startNodeDesc" //开始节点类型
  constant.NODETYPE_END = "endNodeDesc" //结束节点类型
  constant.NODETYPE_STARTSUBPROCESS = "startSubProcessNodeDesc" //开始启动子流程节点类型
  constant.NODETYPE_DRAFT = "draftNodeDesc" //起草节点类型
  constant.NODETYPE_JOIN = "joinNodeDesc" //并发分支结束节点类型
  constant.NODETYPE_SIGN = "signNodeDesc" //签字节点类型
  constant.NODETYPE_SPLIT = "isSplitNode" //并发分支开始节点类型
  constant.NODETYPE_REVIEW = "isReviewNode" //审批节点类型
  constant.NODETYPE_HANDLER = "isHandler" //带处理人的节点类型
  constant.NODETYPE_SEND = "isSendNode" //抄送节点类型
  constant.NODETYPE_CANREFUSE = "canRefuse" //可以被驳回节点类型
  constant.NODETYPE_AUTOBRANCH = "isAutoBranch" //自动分支节点类型
  constant.NODETYPE_MANUALBRANCH = "isManualBranch" //人工分支节点类型
  constant.NODETYPE_RECOVERSUBPROCESS = "isRecoverSubProcess" //结束子流程节点类型
  constant.NODETYPE_ROBOT = "isRobot" //机器人节点类型
  constant.NODETYPE_GROUPSTART="groupStartNodeDesc";//组开始节点类型
  constant.NODETYPE_GROUPEND="groupEndNodeDesc";//组结束节点类型
	  
  //事件常量定义
  constant.EVENT_MODIFYNODEATTRIBUTE = "modifyNodeAttribute" //修改节点属性
  constant.EVENT_MODIFYPROCESS = "modifyProcess" //修改流程图
  constant.EVENT_SELECTEDMANUAL = "selectedManual" //选择了人工分支(起草人选择后续分支)
  constant.EVENT_SELECTEDFUTURENODE = "selectedFutureNode" //选择即将流向分支
  constant.EVENT_FILLLBPMOBJATTU = "fillLbpmObjAttu" //允许项目定制修改节点属性（像美的代理节点）
  constant.EVENT_CHANGEWORKITEM = "changeWorkitem" //当用户有多个工作项时切换工作项
  constant.EVENT_validateMustSignYourSuggestion =
    "validateMustSignYourSuggestion" // 校验是否填写审批意见
  constant.EVENT_HANDLERTYPECHANGE = "handlerChange" //处理人身份发生改变
  constant.EVENT_CHANGEOPERATION = "changeOperation" // 操作发生变更事件
  constant.EVENT_SETOPERATIONPARAM = "setOperationParam" // 提交时设置操作参数事件
  constant.EVENT_BEFORELBPMSUBMIT = "beforeLbpmSubmit" //流程提交前事件
  //流程图常量
  constant.COMMONNODEHANDLERPARSEERROR =
    msg12["lbpmNode.nodeHandler.parseError"]
  constant.COMMONNODEHANDLERPROCESSTYPESERIAL =
    msg12["lbpmNode.nodeHandler.processtypeSerial"]
  constant.COMMONNODEHANDLERPROCESSTYPEALL =
    msg12["lbpmNode.nodeHandler.processtypeAll"]
  constant.COMMONNODEHANDLERPROCESSTYPESINGLE =
    msg12["lbpmNode.nodeHandler.processtypeSingle"]
  constant.COMMONNODENAME = msg12["lbpmNode.nodeHandler.nodeName"]
  constant.COMMONNODEHANDLER = msg12["lbpmNode.nodeHandler.nodeHandler"]
  constant.COMMONNODEHANDLERHINT = msg12["lbpmNode.nodeHandler.hint"]
  //JS提示
  constant.CHKNEXTNODENOTNULL = msg12["lbpmNode.validate.nextNode.isNull"]
  constant.LOADINGMSG = msg10["WorkFlow.Loading.Msg"]
  constant.CONCURRENCYBRANCHSELECT = msg5["lbpmSupport.concurrencyBranchSelect"]
  constant.CONCURRENCYBRANCHTITLE = msg5["lbpmSupport.concurrencyBranchTitle"]
  constant.VALIDATENOTIFYTYPEISNULL =
    msg12["lbpmNode.validate.notifyType.isNull"]
  constant.VALIDATEOPERATIONTYPEISNULL =
    msg12["lbpmNode.validate.operationType.isNull"]
  constant.ERRORMAXLENGTH = msg11["mui.errors.maxLength.simple"]
  constant.OPINION = msg12["lbpmNode.createDraft.opinion"]

  constant.ERROR_FDUSAGECONTENT_MAXLENGTH = msg12[
    "lbpmNode.createDraft.opinion.maxLength"
  ]
    .replace("{0}", "{name}")
    .replace("{1}", "{maxLength}")
  constant.CREATEDRAFTCOMMONUSAGES = msg12["lbpmNode.createDraft.commonUsages"]
  constant.MUSTMODIFYHANDLERNODEIDSISNULL =
    msg12["lbpmNode.flowContent.mustModifyHandlerNodeIds.isNull"]
  constant.MUSTMODIFYHANDLERNODEIDSPARSEISNULL =
    msg12["lbpmNode.flowContent.mustModifyHandlerNodeIds.parse.isNull"]
  constant.MUSTMODIFYHANDLERNODEIDSPARSEPOSTEMPTY =
    msg12["lbpmNode.flowContent.mustModifyHandlerNodeIds.parse.postEmpty"]
  constant.MUSTMODIFYHANDLERNODEIDSPARSEELEMENTDISABLED =
    msg12["lbpmNode.flowContent.mustModifyHandlerNodeIds.parse.elementDisabled"]
  constant.VALIDATENEXTNODEHANDLERISNULL =
    msg12["lbpmNode.validate.nextNodeHandler.isNull"]
  constant.FLOWCONTENTMUSTMODIFYNODENEXTHANDLER =
    msg12["lbpmNode.flowContent.mustModifyNodeNextHandlerNodeIds.isNull"]
  constant.COMMONNODEHANDLERORGEMPTY = msg12["lbpmNode.nodeHandler.orgEmpty"]
  constant.COMMONNODEHANDLERORGNULL = msg12["lbpmNode.nodeHandler.orgNull"]

  constant.opt.EnvironmentUnsupportOperation =
    msg5["lbpmSupport.environmentUnsupportOperation"]
  constant.opt.MustSignYourSuggestion = msg12["lbpmNode.mustSignYourSuggestion"]

  constant.NOCALCBRANCHCRESULT = msg12["lbpmNode.noCalcBranchResult"]
  constant.MODIFYRETURNBACKHANDLER =
    msg12[
      "lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.modifyRefuseBackHandler"
    ]
  constant.BTNSAVEDRAFT = msg11["mui.button.savedraft"]

  constant.OPRSUCCESS = msg3["mui.return.success"]
  constant.OPRFAILURE = msg3["mui.return.failure"]

  constant.FREEFLOW = msg7["lbpmTemplate.fdType.free"]
  constant.FREEFLOW_MUSTAPPENDNODE = msg8["lbpm.freeFlow.mustAppendSomeNode"]

  constant.SETNEXTSTEP = msg9["lbpm.group.setNextStep"]
  constant.UPDATENEXTSTEP = msg9["lbpm.group.updateNextStep"]
  constant.COMMONNODEREPLAYCAL = msg12["lbpmNode.recalculation.handler"]
})
