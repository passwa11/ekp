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
lbpm.globals = new Object(); <%--所有函数集合 --%>
lbpm.nodeDescMap = {}
lbpm.nodedescs = {} <%--所有节点描述集合--%>
lbpm.jsfilelists = new Array() <%--所有include的的JS文件路径集合--%>
lbpm.operations = new Object() <%--所有操作集合--%>
lbpm.nodes = new Object() <%--所有节点集合--%>
lbpm.nodesInit = new Object() <%--最初的节点集合--%>
lbpm.lines = new Object() <%--所有连线集合--%>
lbpm.modifys = null <%--所有修改的数据集合--%>
lbpm.events = {} <%--所有事件集合--%>
lbpm.flowcharts = {} <%--流程图所要的属性集合--%>
lbpm.workitem = {}<%--工作项常量--%>
lbpm.workitem.constant = {}
lbpm.node = {}<%--节点常量--%>
lbpm.node.constant = {}
lbpm.modelName = "${sysWfBusinessForm.modelClass.name}";
lbpm.modelId = "${sysWfBusinessForm.fdId}";
lbpm.isFreeFlow = ("${sysWfBusinessForm.sysWfBusinessForm.fdTemplateType}" == "4"); <%--模板类型 --%>
lbpm.isSubForm = ("${sysWfBusinessForm.sysWfBusinessForm.subFormMode}" == "true");<%--是否为多表单模式--%>
lbpm.isShowSubBut = ("${sysWfBusinessForm.sysWfBusinessForm.showSubBut}" == "true");<%--是否显示切换表单按钮--%>
if (lbpm.isSubForm) {
	lbpm.nowSubFormId = "${sysWfBusinessForm.sysWfBusinessForm.fdSubFormId}";
}

var constant = (lbpm.constant = {})
constant.opt = {} <%--操作常量--%>
constant.evt = {} <%--事件常量--%>
lbpm.defaultTaskId = '<%= (request.getParameter("fdTaskInstanceId") != null) ? request.getParameter("fdTaskInstanceId") : "" %>'; <%--指定默认选中的事务--%>
require(['sys/lbpmservice/mobile/import/constant'],function(){
	var c = constant;
	c.ADDRESS_SELECT_FORMULA="<%=LbpmConstants.HANDLER_SELECT_TYPE_FORMULA%>";
	c.ADDRESS_SELECT_ORG="<%=LbpmConstants.HANDLER_SELECT_TYPE_ORG%>";
	c.ADDRESS_SELECT_MATRIX="<%=LbpmConstants.HANDLER_SELECT_TYPE_MATRIX%>";
	c.PROCESSTYPE_SERIAL="<%=LbpmConstants.PROCESS_TYPE_SERIAL%>";  <%--串行--%>
	c.PROCESSTYPE_ALL="<%=LbpmConstants.PROCESS_TYPE_ALL%>";     <%--会审/会签--%>
	c.PROCESSTYPE_SINGLE="<%=LbpmConstants.PROCESS_TYPE_SINGLE%>";  <%--并行--%>
	c.DOCSTATUS = '${sysWfBusinessForm.docStatus}'; <%--文档状态--%>
	c.ISINIT = ("${sysWfBusinessForm.sysWfBusinessForm.fdIsInit}" == "true");
	if(!c.ISINIT){
		var method = Com_GetUrlParameter(window.location.href,'method');
		c.ISINIT = (method == "add" || method == 'saveadd');
	}
	c.ROLETYPE="${JsParam.roleType}"; <%--角色类型--%>
	c.FDKEY="${JsParam.fdKey}";
	c.IS_ADMIN = c.PRIVILEGERFLAG='${sysWfBusinessForm.sysWfBusinessForm.fdIsAdmin}';
	c.IS_HANDER='${sysWfBusinessForm.sysWfBusinessForm.fdIsHander}';
	c.SHOWHISTORYOPERS='${JsParam.showHistoryOpers}';
});
lbpm.globals.includeFile = function(fileList) {
	fileList = fileList.split("|");
	for(var i = 0; i < fileList.length; i++){
		if(Com_ArrayGetIndex(lbpm.jsfilelists, fileList[i]) == -1) {
			lbpm.jsfilelists[lbpm.jsfilelists.length] = fileList[i];
		}
	}
};
</script>
<%@ include file="/sys/lbpmservice/mobile/import/desc_generator.jsp"%>