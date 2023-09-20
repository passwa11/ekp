<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.lbpmservice.constant.LbpmConstants" %>
<%@ page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextGroupTag"%>

<%@ include file="/resource/jsp/common.jsp"%>
<script language="JavaScript">
var _isLangSuportEnabled = <%=MultiLangTextGroupTag.isLangSuportEnabled()%> ;
var _langJson = <%=MultiLangTextGroupTag.getLangsJsonStr()%>;
var _userLang = "<%=MultiLangTextGroupTag.getUserLangKey()%>";

<%--流程审批界面的语言包、常量表--%>
var lbpm = new Object();
lbpm.globals = new Object(); //所有函数集合
lbpm.operations = new Object(); //所有操作集合
lbpm.nodes = new Object(); //所有节点集合
lbpm.nodesInit = new Object(); //最初的节点集合
lbpm.lines = new Object(); //所有连线集合
lbpm.modifys = null; //所有修改的数据集合
lbpm.events={}; //所有事件集合
lbpm.onLoadEvents={};//页面加载完成事件
lbpm.onLoadEvents.once=[];//页面加载完成事件-立即执行
lbpm.onLoadEvents.delay=[];//页面加载完成事件-延后执行
lbpm.flowcharts={};//流程图所要的属性集合

lbpm.nodeDescMap = {};
lbpm.nodedescs = {}; //所有节点描述集合
lbpm.jsfilelists = new Array(); //所有include的的JS文件路径集合

lbpm.modelName = "${sysWfBusinessForm.modelClass.name}";
lbpm.modelId = "${sysWfBusinessForm.fdId}";
lbpm.isFreeFlow = ("${sysWfBusinessForm.sysWfBusinessForm.fdTemplateType}" == "4"); //模板类型
lbpm.isSubForm = ("${sysWfBusinessForm.sysWfBusinessForm.subFormMode}" == "true");// 是否为多表单模式
lbpm.isShowSubBut = ("${sysWfBusinessForm.sysWfBusinessForm.showSubBut}" == "true");// 是否显示切换表单按钮
if (lbpm.isSubForm) {
	lbpm.nowSubFormId = "${sysWfBusinessForm.sysWfBusinessForm.fdSubFormId}";
}
lbpm.defaultTaskId = '<%= (request.getParameter("fdTaskInstanceId") != null) ? request.getParameter("fdTaskInstanceId") : "" %>'; // 指定默认选中的事务
( function(lbpm) {
	var constant=lbpm.constant={};
	//工作项常量
	lbpm.workitem={};
	lbpm.workitem.constant={};
	//节点常量
	lbpm.node={};
	lbpm.node.constant={};
	constant.opt={};//操作常量
	constant.evt={};//事件常量
	//址本本选择
	constant.ADDRESS_SELECT_FORMULA="<%=LbpmConstants.HANDLER_SELECT_TYPE_FORMULA%>";
	constant.ADDRESS_SELECT_ORG="<%=LbpmConstants.HANDLER_SELECT_TYPE_ORG%>";
	constant.ADDRESS_SELECT_MATRIX="<%=LbpmConstants.HANDLER_SELECT_TYPE_MATRIX%>";
	constant.ADDRESS_SELECT_RULE="<%=LbpmConstants.HANDLER_SELECT_TYPE_RULE%>";
	constant.ADDRESS_SELECT_POSTPERSONROLE="ORG_TYPE_POSTORPERSON | ORG_TYPE_ROLE";
	constant.ADDRESS_SELECT_ALLROLE="ORG_TYPE_ALL | ORG_TYPE_ROLE";
	constant.SELECTORG="<bean:message key='dialog.selectOrg' />"; //选择
	constant.PROCESSTYPE_SERIAL="<%=LbpmConstants.PROCESS_TYPE_SERIAL%>";  //串行
	constant.PROCESSTYPE_ALL="<%=LbpmConstants.PROCESS_TYPE_ALL%>";     //会审/会签
	constant.PROCESSTYPE_SINGLE="<%=LbpmConstants.PROCESS_TYPE_SINGLE%>";  //并行
	
	constant.METHOD="${JsParam.method}";
	constant.METHODEDIT="edit";
	constant.METHODVIEW="view";
	constant.IDENTITY_PROCESSOR = "processor";
	
	constant.DOCSTATUS = '${sysWfBusinessForm.docStatus}'; //文档状态
	//是否为新建的流程
	constant.ISINIT = ("${sysWfBusinessForm.sysWfBusinessForm.fdIsInit}" == "true");
	if(!constant.ISINIT){
		var method = Com_GetUrlParameter(window.location.href,'method');
		constant.ISINIT = (method == "add" || method == 'saveadd');
	}
	constant.STATE_COMPLETED='30';//流程结束
	constant.STATE_ACTIVATED='20'; //流程中
	constant.STATE_CREATED='10'; //流程创建
	
	constant.STATUS_UNINIT=0; //状态：未初始化
	constant.STATUS_NORMAL=1;//状态：普通
	constant.STATUS_PASSED=2; //状态：曾经流过
	constant.STATUS_RUNNING=3; //状态：当前
	
	constant.ROLETYPE="${JsParam.roleType}"; //角色类型
	constant.FDKEY="${JsParam.fdKey}";
	
	constant.PRIVILEGERFLAG='${sysWfBusinessForm.sysWfBusinessForm.fdIsAdmin}';
	constant.IS_ADMIN=constant.PRIVILEGERFLAG;
	constant.IS_HANDER='${sysWfBusinessForm.sysWfBusinessForm.fdIsHander}';
	constant.SHOWHISTORYOPERS='${JsParam.showHistoryOpers}';
	
	//处理人身份标识，起草人
	constant.DRAFTERROLETYPE="drafter";
	constant.HANDLER_IDENTITY_DRAFT=1;
	//处理人身份标识，处理人
	constant.PROCESSORROLETYPE = "processor";
	constant.HANDLER_IDENTITY_HANDLER=2;
	//处理人身份标识，特权人
	constant.AUTHORITYROLETYPE="authority";
	constant.HANDLER_IDENTITY_SPECIAL=3;
	//处理人身份标识，已处理人
	constant.HISTORYHANDLERROLETYPE="historyhandler";
	constant.HANDLER_IDENTITY_HISTORYHANDLER=4;
	//处理人身份表示，分支特权人
	constant.BRANCHADMINROLETYPE="branchadmin";
	constant.HANDLER_IDENTITY_BRANCHADMINROLETYPE=5;
	
	constant.SUCCESS="success";
	constant.FAILURE="failure";
	constant.VALIDATEISNULL="<bean:message bundle='sys-lbpmservice' key='validate.isNull' />";
	constant.VALIDATEISNAN="<bean:message bundle='sys-lbpmservice' key='validate.isNaN' />";

    //节点类型
    constant.NODETYPE_START="startNodeDesc"; //开始节点类型
    constant.NODETYPE_END="endNodeDesc"; //结束节点类型
   	constant.NODETYPE_STARTSUBPROCESS = "startSubProcessNodeDesc";//开始启动子流程节点类型
	constant.NODETYPE_DRAFT="draftNodeDesc";//起草节点类型
   	constant.NODETYPE_JOIN="joinNodeDesc";//并发分支结束节点类型
   	constant.NODETYPE_SIGN="signNodeDesc";//签字节点类型
   	constant.NODETYPE_SPLIT = "isSplitNode";//并发分支开始节点类型 	
   	constant.NODETYPE_REVIEW="isReviewNode";//审批节点类型 	
   	constant.NODETYPE_HANDLER="isHandler";//带处理人的节点类型
   	constant.NODETYPE_SEND="isSendNode";//抄送节点类型
   	constant.NODETYPE_CANREFUSE="canRefuse";//可以被驳回节点类型
   	constant.NODETYPE_AUTOBRANCH="isAutoBranch";//自动分支节点类型
   	constant.NODETYPE_MANUALBRANCH="isManualBranch";//人工分支节点类型
   	constant.NODETYPE_RECOVERSUBPROCESS="isRecoverSubProcess";//结束子流程节点类型
   	constant.NODETYPE_ROBOT="isRobot";//机器人节点类型
   	constant.NODETYPE_GROUPSTART="groupStartNodeDesc";//组开始节点类型
   	constant.NODETYPE_GROUPEND="groupEndNodeDesc";//组结束节点类型
	constant.NODETYPE_DRAFTNODE='<kmss:message bundle="sys-lbpmservice" key="lbpm.nodeType.draftNode"/>';

 	//事件常量定义
 	constant.EVENT_MODIFYNODEATTRIBUTE="modifyNodeAttribute"; //修改节点属性
 	constant.EVENT_MODIFYPROCESS="modifyProcess"; //修改流程图
 	constant.EVENT_SELECTEDMANUAL="selectedManual"; //选择了人工分支(起草人选择后续分支)
 	constant.EVENT_SELECTEDFUTURENODE="selectedFutureNode"; //选择即将流向分支
 	constant.EVENT_FILLLBPMOBJATTU="fillLbpmObjAttu"; //允许项目定制修改节点属性（像美的代理节点）
 	constant.EVENT_CHANGEWORKITEM="changeWorkitem"; //当用户有多个工作项时切换工作项
	constant.EVENT_validateMustSignYourSuggestion = "validateMustSignYourSuggestion"; // 校验是否填写审批意见
	constant.EVENT_HANDLERTYPECHANGE = "handlerChange";//处理人身份发生改变
	constant.EVENT_CHANGEOPERATION = "changeOperation"; // 操作发生变更事件
	constant.EVENT_SETOPERATIONPARAM = "setOperationParam"; // 提交时设置操作参数事件
	constant.EVENT_BEFORELBPMSUBMIT = "beforeLbpmSubmit";//流程提交前事件
 	//流程图常量
	constant.COMMONNODEHANDLERPARSEERROR='<kmss:message bundle="sys-lbpmservice" key="lbpmNode.nodeHandler.parseError"/>';
	constant.COMMONNODEHANDLERPROCESSTYPESERIAL='<kmss:message bundle="sys-lbpmservice" key="lbpmNode.nodeHandler.processtypeSerial"/>';
	constant.COMMONNODEHANDLERPROCESSTYPEALL='<kmss:message bundle="sys-lbpmservice" key="lbpmNode.nodeHandler.processtypeAll"/>';
	constant.COMMONNODEHANDLERPROCESSTYPESINGLE='<kmss:message bundle="sys-lbpmservice" key="lbpmNode.nodeHandler.processtypeSingle"/>';
	constant.COMMONNODENAME='<kmss:message bundle="sys-lbpmservice" key="lbpmNode.nodeHandler.nodeName"/>';
	constant.COMMONNODEHANDLER='<kmss:message bundle="sys-lbpmservice" key="lbpmNode.nodeHandler.nodeHandler"/>';
	constant.COMMONNODEHANDLERHINT='<kmss:message bundle="sys-lbpmservice" key="lbpmNode.nodeHandler.hint"/>';
    //JS提示
    constant.CHKNEXTNODENOTNULL="<bean:message bundle='sys-lbpmservice' key='lbpmNode.validate.nextNode.isNull' />";
    constant.LOADINGMSG="<bean:message bundle='sys-lbpmservice' key='WorkFlow.Loading.Msg' />";
	constant.CONCURRENCYBRANCHSELECT="<bean:message bundle='sys-lbpmservice' key='lbpmSupport.concurrencyBranchSelect'/>";
	constant.CONCURRENCYBRANCHTITLE="<bean:message bundle='sys-lbpmservice' key='lbpmSupport.concurrencyBranchTitle'/>";
	constant.VALIDATENOTIFYTYPEISNULL="<bean:message bundle='sys-lbpmservice' key='lbpmNode.validate.notifyType.isNull'/>";
	constant.VALIDATEOPERATIONTYPEISNULL="<bean:message bundle='sys-lbpmservice' key='lbpmNode.validate.operationType.isNull' />";
	constant.ERRORMAXLENGTH="<bean:message key='errors.maxLength.simple' />";
	constant.OPINION="<bean:message bundle='sys-lbpmservice' key='lbpmNode.createDraft.opinion' />";
	constant.ERROR_FDUSAGECONTENT_MAXLENGTH='<bean:message bundle="sys-lbpmservice" key="lbpmNode.createDraft.opinion.maxLength" arg0="{name}" arg1="{maxLength}" />';
	constant.CREATEDRAFTCOMMONUSAGES="<bean:message bundle='sys-lbpmservice' key='lbpmNode.createDraft.commonUsages' />";
	constant.MUSTMODIFYHANDLERNODEIDSISNULL="<bean:message bundle='sys-lbpmservice' key='lbpmNode.flowContent.mustModifyHandlerNodeIds.isNull' />";
	constant.MUSTMODIFYHANDLERNODEIDSPARSEISNULL="<bean:message bundle='sys-lbpmservice' key='lbpmNode.flowContent.mustModifyHandlerNodeIds.parse.isNull' />";
	constant.MUSTMODIFYHANDLERNODEIDSPARSEPOSTEMPTY="<bean:message bundle='sys-lbpmservice' key='lbpmNode.flowContent.mustModifyHandlerNodeIds.parse.postEmpty' />";
	constant.MUSTMODIFYHANDLERNODEIDSPARSEELEMENTDISABLED="<bean:message bundle='sys-lbpmservice' key='lbpmNode.flowContent.mustModifyHandlerNodeIds.parse.elementDisabled' />";
	constant.VALIDATENEXTNODEHANDLERISNULL="<bean:message bundle='sys-lbpmservice' key='lbpmNode.validate.nextNodeHandler.isNull' />";
	constant.FLOWCONTENTMUSTMODIFYNODENEXTHANDLER="<bean:message bundle='sys-lbpmservice' key='lbpmNode.flowContent.mustModifyNodeNextHandlerNodeIds.isNull' />";
	constant.COMMONNODEHANDLERORGEMPTY='<kmss:message bundle="sys-lbpmservice" key="lbpmNode.nodeHandler.orgEmpty"/>';
	constant.COMMONNODEHANDLERORGNULL='<kmss:message bundle="sys-lbpmservice" key="lbpmNode.nodeHandler.orgNull"/>';

	constant.opt.EnvironmentUnsupportOperation='<bean:message bundle="sys-lbpmservice" key="lbpmSupport.environmentUnsupportOperation" />';
	constant.opt.MustSignYourSuggestion='<bean:message bundle="sys-lbpmservice" key="lbpmNode.mustSignYourSuggestion" />';

	constant.NOCALCBRANCHCRESULT='<bean:message bundle="sys-lbpmservice" key="lbpmNode.noCalcBranchResult" />';
	constant.MODIFYRETURNBACKHANDLER='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.modifyRefuseBackHandler" />';
	constant.BTNSAVEDRAFT='<bean:message key="button.savedraft"/>';
	constant.BTNOK='<bean:message key="button.ok"/>';
	constant.BTNCANCEL='<bean:message key="button.cancel"/>';

	constant.OPRSUCCESS = '<bean:message bundle="sys-mobile" key="mui.return.success"/>';
	constant.OPRFAILURE = '<bean:message bundle="sys-mobile" key="mui.return.failure"/>';
	
	constant.FREEFLOW =  '<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.fdType.free"/>';
	constant.FREEFLOW_MUSTAPPENDNODE = '<bean:message bundle="sys-lbpmservice" key="lbpm.freeFlow.mustAppendSomeNode"/>';
	constant.FREEFLOW_DELETENODEMSG = '<bean:message bundle="sys-lbpmservice" key="lbpmservice.freeflow.deleteNodeMsg"/>';
	constant.FREEFLOW_TIELE_ADD = '<bean:message bundle="sys-lbpmservice" key="lbpmservice.freeflow.addTitle"/>';
	constant.FREEFLOW_TIELE_EDIT = '<bean:message bundle="sys-lbpmservice" key="lbpmservice.freeflow.editTitle"/>';
	constant.FREEFLOW_TIELE_DELETE = '<bean:message bundle="sys-lbpmservice" key="lbpmservice.freeflow.deleteTitle"/>';
	constant.FREEFLOW_TIELE_DRAG = '<bean:message bundle="sys-lbpmservice" key="lbpmservice.freeflow.dragTitle"/>';
	constant.FREEFLOW_TIELE_FIXED = '<bean:message bundle="sys-lbpmservice" key="lbpmservice.freeflow.fixedTitle"/>';
	constant.FREEFLOW_TIELE_PASSED = '<bean:message bundle="sys-lbpmservice" key="lbpmservice.freeflow.passedTitle"/>';
	constant.FREEFLOW_TIELE_RUNNING = '<bean:message bundle="sys-lbpmservice" key="lbpmservice.freeflow.runningTitle"/>';
	constant.FREEFLOW_TIELE_ADDHANDLER = '<bean:message bundle="sys-lbpmservice" key="lbpmservice.freeflow.addHandlerTitle"/>';
	constant.FREEFLOW_TIELE_NODE = '<bean:message bundle="sys-lbpmservice" key="lbpmservice.freeflow.node"/>';
	constant.FREEFLOW_TIELE_NOHANDLER = '<bean:message bundle="sys-lbpmservice" key="lbpmservice.freeflow.noHandlerTitle"/>';
	constant.FREEFLOW_TIELE_SAVECONFIRM = '<bean:message bundle="sys-lbpmservice" key="lbpmservice.freeflow.saveConfirm"/>';
	constant.FREEFLOW_LOAD_OK = '<bean:message bundle="sys-lbpmservice" key="lbpmservice.freeflow.loadOk"/>';
	constant.FREEFLOW_SAVEOTHERTEMP = '<bean:message bundle="sys-lbpmservice" key="lbpmservice.freeflow.saveOtherTemp"/>';
	constant.FREEFLOW_LOADOTHERTEMP = '<bean:message bundle="sys-lbpmservice" key="lbpmservice.freeflow.loadOtherTemp"/>';
	constant.FREEFLOW_INSERTTEMPLATENAME = '<bean:message bundle="sys-lbpmservice" key="lbpmservice.freeflow.insertTemplateName"/>';
	constant.FREEFLOW_TEMPLATENAME = '<bean:message bundle="sys-lbpmservice" key="lbpmservice.freeflow.templateName"/>';

	constant.SETNEXTSTEP = '<bean:message bundle="sys-lbpmservice" key="lbpm.group.setNextStep"/>';
	constant.UPDATENEXTSTEP = '<bean:message bundle="sys-lbpmservice" key="lbpm.group.updateNextStep"/>';
	
	constant.lbpmRight_fold = '<bean:message bundle="sys-lbpmservice" key="lbpmRight.fold"/>';
	constant.lbpmRight_Unfold = '<bean:message bundle="sys-lbpmservice" key="lbpmRight.Unfold"/>';
	constant.lbpmRight_drafter = '<bean:message bundle="sys-lbpmservice" key="lbpmProcess.handlerType.drafter"/>';
	constant.lbpmRight_authority = '<bean:message bundle="sys-lbpmservice" key="lbpmProcess.handlerType.authority"/>';
	constant.lbpmRight_hisHandler = '<bean:message bundle="sys-lbpmservice" key="lbpmProcess.handlerType.hisHandler"/>';
	constant.lbpmRight_branchadmin = '<bean:message bundle="sys-lbpmservice" key="lbpmProcess.handlerType.branchadmin"/>';
	constant.lbpmRight_curHandler = '<bean:message bundle="sys-lbpmservice" key="lbpmProcess.handlerType.curHandler"/>';
	constant.lbpmNode_modifyFlow = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.currentNode.modifyFlow" />';
	constant.pleaseSelect = '<bean:message bundle="sys-lbpmservice" key="FlowChartObject.Lang.Node.pleaseSelect" />';
	constant.checkForm = '<bean:message bundle="sys-lbpmservice" key="lbpmservice.checkForm" />';
	constant.select_modify = '<bean:message bundle="sys-lbpmservice" key="select.modify.org" />';

	constant.opt.checkSubProcessInProcess='<bean:message bundle="sys-lbpmservice" key="lbpmOperations.confirm.checkSubProcessInProcess" />';
	constant.opt.adminOperationTypeReplace='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle_Type.adminOperationTypeReplace" />';
	constant.opt.adminOperationTypeAppend='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle_Type.adminOperationTypeAppend" />';
	constant.opt.adminOperationTypeModifyType='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle_Type.adminOperationTypeModifyType" />';
	constant.opt.adminOperationTypeChgCurHandler='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.adminOperationTypeChgCurHandler" />';
	constant.opt.selectOrg='<bean:message key="dialog.selectOrg" />';
	constant.opt.adminOperationTypeRepCurHandler='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.adminOperationTypeRepCurHandler" />';
	constant.opt.mustSelectCurHandler='<bean:message bundle="sys-lbpmservice" key="lbpmNode.flowContent.admin_operation_type.chgcurhandle.mustSelectCurHandler" />';
	constant.opt.mustChangeOperator='<bean:message bundle="sys-lbpmservice" key="lbpmNode.flowContent.admin_operation_type.chgcurhandle.mustChangeOperator" />';
	constant.opt.mustChangeAppendOperator='<bean:message bundle="sys-lbpmservice" key="lbpmNode.flowContent.admin_operation_type.chgcurhandle.mustChangeAppendOperator" />';

	constant.opt.adminOperationTypeJump='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.adminOperationTypeJump" />';
	constant.opt.abandonSubprocess='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.adminOperationTypeJump.abandonSubprocess" />';
	constant.opt.noJumpNode='<bean:message bundle="sys-lbpmservice" key="lbpmNode.flowContent.noJumpNode" />';
	constant.opt.jumpConfirm='<bean:message key='lbpmNode.validate.hanlderEmpty.jump.confirm' bundle='sys-lbpmservice' />';

	constant.opt.modifyProcessError='<bean:message key="lbpmProcess.privileger.modify.process.error" bundle="sys-lbpmservice-operation-admin"/>';

	constant.opt.adminOperationTypeRecover='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.adminOperationTypeRecover" />';
	constant.opt.adminrecoverAlertText='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.adminrecover.alertText" />';

	constant.opt.retryQueueTitel='<kmss:message key="sys-lbpmservice-operation-admin:lbpmOperations.fdOperType.processor.retry.queueTitel" />';
	constant.opt.retryQueueTemp='<kmss:message key="sys-lbpmservice-operation-admin:lbpmOperations.fdOperType.processor.retry.queueTemp" />';
	constant.opt.retryShowDetail='<kmss:message key="sys-lbpmservice-operation-admin:lbpmOperations.fdOperType.processor.retry.showDetail" />';
	constant.opt.retryHideDetail='<kmss:message key="sys-lbpmservice-operation-admin:lbpmOperations.fdOperType.processor.retry.hideDetail" />';
	constant.opt.retrySelect='<kmss:message key="sys-lbpmservice-operation-admin:lbpmOperations.fdOperType.processor.retry.select" />';

	constant.opt.backbranch='<bean:message bundle="sys-lbpmservice-operation-branchadmin" key="lbpmOperations.fdOperType.processor.backbranch" />';
	constant.opt.backbranchMsg='<bean:message bundle="sys-lbpmservice-operation-branchadmin" key="lbpmOperations.backbranch.msg" />';
	constant.opt.backbranchMsg2='<bean:message bundle="sys-lbpmservice-operation-branchadmin" key="lbpmOperations.backbranch.msg2" />';

	constant.opt.endbranch='<bean:message bundle="sys-lbpmservice-operation-branchadmin" key="lbpmOperations.fdOperType.processor.endbranch" />';
	constant.opt.endbranchMsg='<bean:message bundle="sys-lbpmservice-operation-branchadmin" key="lbpmOperations.endbranch.msg" />';

	constant.opt.restartconbranch='<bean:message bundle="sys-lbpmservice-node-joinnode" key="lbpmNode.processingNode.operationsTDTitle.restartconbranch" />';
	constant.opt.restartbranchCheck='<bean:message bundle="sys-lbpmservice-node-joinnode" key="lbpmProcess.joinnode.conbranch.restartbranch.check" />';

	constant.opt.abandonConfirm='<bean:message bundle="sys-lbpmservice" key="lbpmNode.validate.abandon.confirm" />';

	constant.opt.handlerOperationTypepass='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypepass" />';
	constant.opt.calcBranch='<bean:message bundle="sys-lbpmservice" key="lbpmNode.calcBranch" />';

	constant.opt.checkSubProcessInProcess='<bean:message bundle="sys-lbpmservice" key="lbpmOperations.confirm.checkSubProcessInProcess" />';

	constant.opt.AdditionSignPeople='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationType.people" />' //补签人员;
	constant.opt.AdditionSignIsNull="<bean:message bundle='sys-lbpmservice' key='lbpmNode.validate.toOtherHandlerIds.additionSign.isNull' />";
	constant.opt.PleaseChoose="<bean:message bundle='sys-lbpmservice' key='lbpmNode.validate.toOtherHandlerIds.assign.isNull' />";
	constant.opt.Staff="<bean:message bundle='sys-lbpmservice' key='lbpmNode.processingNode.operationsTDTitle.handlerOperationType.people' />";

	constant.opt.CommissionPeople='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationType.people" />' //转办人员;
	constant.opt.CommissionIsNull="<bean:message bundle='sys-lbpmservice' key='lbpmNode.validate.toOtherHandlerIds.Commission.isNull' />";
	constant.opt.returnToCommissionedPerson='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeCommission.returnToCommissionedPerson" />';

	constant.opt.handlerOperationTypeRefuse = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse" />';
	constant.opt.abandonSubprocess = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.abandonSubprocess" />';
	constant.opt.noRefuseNode = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.flowContent.noRefuseNode" />';
	constant.opt.sequenceFlow = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.sequenceFlow" />';
	constant.opt.sequenceFlowTitle = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.sequenceFlow.title" />';
	constant.opt.returnBackMe = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.returnBackMe" />';
	constant.opt.returnBackMeTitle = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.returnBackMe.title" />';
	constant.opt.returnBackTitle = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.returnBack.title" />';
	constant.opt.returnBackTheNode = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.returnBackTheNode" />';
	constant.opt.returnBackTheNodeTitle = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.returnBackTheNode.title" />';
	constant.opt.thisNode = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.flowContent.thisNode" />';
	constant.opt.refusePeople = "${lfn:message('sys-lbpmservice:lbpmservice.select.refusePeople')}";
	constant.opt.refusePeopleSearch = "${lfn:message('sys-lbpmservice:lbpmservice.select.refusePeople.search')}";

	constant.opt.returnBack = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.returnBack" />';
	constant.opt.noReturnBackNode = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.flowContent.noReturnBackNode" />';

	constant.opt.draftAbandon = '<bean:message key="lbpmOperations.fdOperType.draft.abandon" bundle="sys-lbpmservice-operation-drafter" />';
	constant.opt.abandonConfirm = '<bean:message key="lbpmNode.validate.abandon.confirm" bundle="sys-lbpmservice" />';

	constant.opt.calcBranch = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.calcBranch" />';
	constant.opt.handlerOperationTypepass = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypepass" />';
	constant.opt.savedraft = "<bean:message key='button.savedraft'/>";
	constant.opt.noSelectAll = "<bean:message bundle='sys-lbpmservice' key='lbpmNode.manualNodeOnDraft.noSelectAll'/>";

	constant.opt.Assignee='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationType.people" />'; //人员;
	constant.opt.AssigneeIsNull='<bean:message bundle="sys-lbpmservice" key="lbpmNode.validate.toOtherHandlerIds.assign.isNull" />';
	constant.opt.AssignCheckObj='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.check.assign.obj" />';
	constant.opt.AssignNeedSelectCanceler='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.assign.NeedSelectCanceler" />';
	constant.opt.AssignMustSignYourSuggestion='<bean:message bundle="sys-lbpmservice" key="lbpmNode.mustSignYourSuggestion" />';
	constant.opt.AssignAllowMulti='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle_Scope.allow.muti" />';
	constant.opt.AssignPassSkip='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle_Scope.assignPass.skip" />';
	constant.opt.AssignType='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle_Scope.assignType" />';  //流转方式
	constant.opt.AssignType_0='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle_Scope.assignType_0" />';
	constant.opt.AssignType_1='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle_Scope.assignType_1" />';
	constant.opt.AssignType_21='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle_Scope.assignType_21" />';

	constant.opt.CommunicatePeople='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationType.people" />'; //沟通人员;
	constant.opt.CommunicateIsNull='<bean:message bundle="sys-lbpmservice" key="lbpmNode.validate.toOtherHandlerIds.Communicate.isNull" />';
	constant.opt.CommunicateScopeAllowMuti='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle_Scope.allow.muti" />';
	constant.opt.CommunicateScopeLimitSub='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle_Scope.Limit.sub" />';
	constant.opt.CommunicateScopeLimitScope='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle_Scope.Limit.scope" />';
	constant.opt.CommunicateScopeIsNullNoLimit='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle_Scope.isNull.NoLimit" />';
	constant.opt.CommunicateCheckObj='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.check.communicate.obj" />';
	constant.opt.CommunicateNeedSelectCanceler='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.communicate.NeedSelectCanceler" />';
	constant.opt.CommunicateMustSignYourSuggestion='<bean:message bundle="sys-lbpmservice" key="lbpmNode.mustSignYourSuggestion" />';
	constant.opt.CommunicateHiddenNote='<bean:message bundle="sys-lbpmservice-operation-communicate" key="lbpmOperations.communicate.hiddenNote" />';
	constant.opt.TurnToDoNoteHiddenNote='<bean:message bundle="sys-lbpmservice-support" key="lbpmOperations.turnToDo.hiddenNote" />';

	constant.opt.handlerOperationTypeJump='<bean:message bundle="sys-lbpmservice-operation-handler" key="lbpmNode.checknode.operationsTDTitle.handlerOperationTypeJump" />';
	constant.opt.abandonSubprocess='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.abandonSubprocess" />';
	constant.opt.noRefuseNode='<bean:message bundle="sys-lbpmservice" key="lbpmNode.flowContent.noRefuseNode" />';
	constant.opt.noJumpNode='<bean:message bundle="sys-lbpmservice" key="lbpmNode.flowContent.noJumpNode" />';
	constant.opt.jumpConfirm='<bean:message bundle="sys-lbpmservice" key="lbpmNode.validate.hanlderEmpty.jump.confirm" />';
	constant.opt.drafter='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.identifyRole.button.drafter" />';
	constant.opt.authority='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.identifyRole.button.authority" />';
	constant.opt.historyhandler='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.identifyRole.button.historyhandler" />';
	constant.opt.branchadmin='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.identifyRole.button.branchadmin" />';

})(lbpm);
</script>
<%@ include file="/sys/lbpmservice/include/sysLbpmPluginLoad.jsp"%>