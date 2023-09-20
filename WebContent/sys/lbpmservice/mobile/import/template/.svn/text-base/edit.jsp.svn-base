<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.lbpmservice.support.util.LbpmTemplateUtil" %>
<%@ page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextGroupTag"%>
<%@ page import="com.landray.kmss.common.actions.RequestContext"%>
<%@ page import="com.landray.kmss.common.service.IXMLDataBean"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page import="java.util.*" %>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<c:set var="lbpmTemplateForm" value="${requestScope[param.formName].sysWfTemplateForms[param.fdKey]}" />
<c:set var="lbpmTemplateFormPrefix" value="sysWfTemplateForms.${param.fdKey}." />
<c:set var="lbpmTemplate_ModelName" value="${requestScope[param.formName].modelClass.name}" />
<c:set var="lbpmTemplate_Key" value="${param.fdKey}" />
<%
	pageContext.setAttribute("lbpmTemplate_MainModelName",
			LbpmTemplateUtil.getMainModelName(
					(String)pageContext.getAttribute("lbpmTemplate_ModelName"),
					(String)pageContext.getAttribute("lbpmTemplate_Key")));
	IXMLDataBean bean = (IXMLDataBean) SpringBeanUtil.getBean("lbpmSettingInfoService");
	List<Map<String, String>> rtnList = bean.getDataList(new RequestContext(request));
	request.setAttribute("settingInfo", JSONObject.fromObject(rtnList.get(0)));
					
%>
<script type="text/javascript">
var _isLangSuportEnabled = <%=MultiLangTextGroupTag.isLangSuportEnabled()%> ;
var _langJson = <%=MultiLangTextGroupTag.getLangsJsonStr()%>;
var _userLang = "<%=MultiLangTextGroupTag.getUserLangKey()%>";
require(["sys/lbpmservice/mobile/freeflow/freeflow"]);
var lbpm = new Array();
lbpm.globals = new Array();
lbpm.freeFlow = new Array();
lbpm.freeFlow.defOperRefIds = new Array();
lbpm.freeFlow.defFlowPopedom = null;
var Lbpm_SettingInfo = lbpm.settingInfo = ${settingInfo}; 
lbpm.myAddedNodes=new Array();
lbpm.flow_chart_load_Frame=function(){
	require(["dojo/query"], function(query) {
		query("#lbpmView iframe").forEach(function(iframe) {
			if (!iframe.src) {
				iframe.src ='<c:url value="/sys/lbpm/flowchart/page/freeflowPanel.jsp">'
					+'<c:param name="edit" value="true" />'
					+'<c:param name="extend" value="oa" />'
					+'<c:param name="template" value="true" />'
					+'<c:param name="flowType" value="1" />'
					+'<c:param name="mobile" value="true" />'
					+'<c:param name="templateId4View" value="${param.fdId}" />'
					+'<c:param name="contentField" value="${lbpmTemplateFormPrefix}fdFlowContentDefault" />'
					+'<c:param name="modelName" value="${lbpmTemplate_MainModelName}" />'
					+'<c:param name="templateModelName" value="${lbpmTemplate_ModelName}" />'
					+'<c:param name="FormFieldList" value="WF_FormFieldList_${lbpmTemplate_Key}" />'
				+'</c:url>';
			}
		});
	});
};
Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function() {
	var key = "${lbpmTemplate_Key}", prefix = "${lbpmTemplateFormPrefix}";
	var _fdType = $("input[name='"+prefix+"fdType']");
	if(_fdType.length == 0 || _fdType.val() == "3" || _fdType.val() == "4") {// 通用模板和自定义才校验
		if (_fdType.val() == "4"){
			// 配置自由流模板时检测是否已配置了默认操作方式
			var data = new KMSSData();
			data.AddBeanData("getOperTypesByNodeService&nodeType=freeFlowReviewNode");
			var mapData = data.GetHashMapArray();
			var refId = null;
			for(var j=0;j<mapData.length;j++){
				if(mapData[j].isDefault=="true"){
					refId = mapData[j].value;
					break;
				}
			}
			mapData = data.AddBeanData("getOperTypesByNodeService&nodeType=freeFlowSignNode").GetHashMapArray();
			for(var j=0;j<mapData.length;j++){
				if(mapData[j].isDefault=="true"){
					refId = mapData[j].value;
					break;
				}
			}
			if(refId==null){
				alert('<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.freeflow.defaultOperations.isNull"/>');
				return false;
			}
		}
		var iframe = document.getElementById('WF_IFrame');
		var FlowChartObject = iframe.contentWindow.FlowChartObject;
		if(!FlowChartObject.CheckFlow(true)) {
			// 流程图校验
			return false;
		}
		// 判断到连线链接的不是分支节点，则不保存连线上的分支条件
		for(var i=0; i<FlowChartObject.Lines.all.length; i++){
			var line = FlowChartObject.Lines.all[i];
			if(line.Data.condition || line.Data.disCondition){
				var node = line.StartNode;
				if (node != null) {
					var nodeDescObj = node.Desc;
					if (nodeDescObj && !((nodeDescObj["isBranch"](node)) && !nodeDescObj["isHandler"](node))) {
						delete line.Data.condition;
						delete line.Data.disCondition;
					}
				}
			}
		}
		var processData = FlowChartObject.BuildFlowData();
		//#50872 配置表单模板时，如果没有打开起草节点并点击确认，起草节点的“审批意见中上传附件”没有以流程引擎“审批意见中上传附件”的默认值保存 by liwc
		if (processData["nodes"]){
			var nodes = processData["nodes"];
			for(var index = 0; index < nodes.length; index++){
				if (nodes[index] && nodes[index].XMLNODENAME === "draftNode" && typeof nodes[index].canAddAuditNoteAtt === "undefined"){
					nodes[index].canAddAuditNoteAtt = Lbpm_SettingInfo["isCanAddAuditNoteAtt"];
				}
			}
		}
		processData['orgAttribute'] = "privilegerIds:privilegerNames";
		
		//handleDescriptionLang4Save(processData,prefix);
		
		WorkFlow_GetDataFromField(processData, function(fieldName) {
			if(fieldName.substring(0,3) != "wf_") {
				return null;
			}
			fieldName = fieldName.substring(3);
			var index = fieldName.lastIndexOf(".");
			if(index > -1) {
				fieldName = fieldName.substring(index+1);
			}
			return fieldName;
		});
		// 通知特权人天数必须为正整数
		if((processData.dayOfNotifyPrivileger && /\D/gi.test(processData.dayOfNotifyPrivileger))
				|| (processData.hourOfNotifyPrivileger && /\D/gi.test(processData.hourOfNotifyPrivileger))
				|| (processData.minuteOfNotifyPrivileger && /\D/gi.test(processData.minuteOfNotifyPrivileger))) {
			alert('<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.validate.dayOfNotifyPrivileger"/>');
			return false;
		}
		// 通知方式不能为空
		if(processData.notifyType != null && processData.notifyType == "") {
			alert('<bean:message key="lbpmTemplate.validate.notifyType.isNull" bundle="sys-lbpmservice-support"/>');
			return false;
		}
		//流程描述替换换行符
		if(processData.description) {
			var changedText = processData.description;
			changedText = changedText.replace(/\r/g, "&#xD;");
			changedText = changedText.replace(/\n/g, "&#xA;");
			processData.description = changedText;
		}
		//记录模板类型
		processData["templateType"] = _fdType.val();
		// 设置流程内容
		var xml = WorkFlow_BuildXMLString(processData, "process");
		$("textarea[name='"+prefix+"fdFlowContent']").val(xml);
		// 比较流程内容是否修改
		var _fdIsModified = $("input[name='"+prefix+"fdIsModified']");
		_fdIsModified.val("true");
	}
	return true;
};
</script>
<div data-dojo-type="sys/lbpmservice/mobile/freeflow/freeflowChartView" 
	class="lbpmView" id="lbpmView" style="background-color: #F5F6FB">
	<html:hidden property="${lbpmTemplateFormPrefix}fdType" />
	<html:hidden property="${lbpmTemplateFormPrefix}fdCommonId" />
	<html:hidden property="${lbpmTemplateFormPrefix}fdModelName" value="${lbpmTemplate_ModelName}" />
	<html:hidden property="${lbpmTemplateFormPrefix}fdKey" value="${lbpmTemplate_Key}" />
	<html:hidden property="${lbpmTemplateFormPrefix}fdId" />
	<html:hidden property="${lbpmTemplateFormPrefix}fdIsModified" />
	<html:hidden property="${lbpmTemplateFormPrefix}fdEmbeddedInfo" />
	<html:textarea property="${lbpmTemplateFormPrefix}fdFlowContent" style="display:none"/>
	<html:textarea property="${lbpmTemplateFormPrefix}fdFlowContentDefault" style="display:none"/>
	<iframe style="width:100%;height:500px;display:none;" scrolling="no" id="WF_IFrame"></iframe>
	<div id="freeflowChartArea" class="actionArea">
		<div class="actionView">
			<div class="freeflowChartRow">
				<div class="titleNode" id="freeflowRowTitle">
					
				</div>
				<div class="detailNode">
					<div>
						<div data-dojo-type="sys/lbpmservice/mobile/freeflow/freeflowChartPanel" data-dojo-props="template:true"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div data-dojo-type="mui/tabbar/TabBar" fixed="bottom">
		<c:if test="${not empty param.onClickNextToButton}">
			<li data-dojo-type="mui/tabbar/TabBarButton" class="mainTabBarButton" onclick="${param.onClickNextToButton};">
				<bean:message  bundle="sys-lbpmservice"  key="mui.extOperation.next" /></li>
			</li>
		</c:if>
	</div>
	<script src="<c:url value="/sys/lbpm/flowchart/js/workflow.js"/>"></script>
</div>