<%@page import="com.landray.kmss.sys.language.utils.SysLangUtil"%>
<%@page import="com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextareaGroupTag"%>
<%@ page import="com.landray.kmss.sys.xform.XFormConstant"%>
<%
	pageContext.setAttribute("isLangSuportEnabled", MultiLangTextareaGroupTag.isLangSuportEnabled());
%>

<tr>
	<td class="td_normal_title" width=15%>
		<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.fdDescription"/>
	</td><td>
	<html:hidden property="${lbpmTemplateFormPrefix}fdModelName" value="${lbpmTemplate_ModelName}" />
	<html:hidden property="${lbpmTemplateFormPrefix}fdKey" value="${lbpmTemplate_Key}" />
	<html:hidden property="${lbpmTemplateFormPrefix}fdId" />
	<html:hidden property="${lbpmTemplateFormPrefix}fdIsModified" />
	<html:hidden property="${lbpmTemplateFormPrefix}fdEmbeddedInfo" />
	<html:hidden property="${lbpmTemplateFormPrefix}fdDynamicInfo" />
	<%
		String subject = "";
		if(SysLangUtil.isLangEnabled()){
			String tip = ResourceUtil.getStringValue("lbpm.lang.multi.tip.name",
					"sys-lbpmservice", UserUtil.getKMSSUser().getLocale());
			String langStr = ResourceUtil.getKmssConfigString("kmss.lang.official");
			if (StringUtil.isNotNull(langStr)) {
				String[] langInfo = langStr.split("\\|");
				subject = langInfo[0];
			}
			if(StringUtil.isNotNull(subject)){
				subject += "(" + tip + ")";
			}
		}
	%>
	<%-- <xform:textarea property="wf_${lbpmTemplateFormPrefix}description" validators="maxLength(2000)" style="width:92%" subject="<%=subject %>"/>
    <xlang:lbpmlangArea property="_wf_${lbpmTemplateFormPrefix}description" style="width:92%;" langs="" validators="maxLength(2000)"/> --%>
	<c:if test="${!isLangSuportEnabled }">
		<xform:textarea property="wf_${lbpmTemplateFormPrefix}description" validators="maxLength(2000)" style="width:92%" subject="<%=subject %>"/>
	</c:if>
	<c:if test="${isLangSuportEnabled }">
		<xlang:lbpmlangAreaNew property="_wf_${lbpmTemplateFormPrefix}description" alias="wf_${lbpmTemplateFormPrefix}description" style="width:92%;" langs="" subject="<%=subject %>" validators="maxLength(2000)"/>
	</c:if>
</td>
</tr>
<tr id="flowContentRow">
	<td colspan="2">
		<html:textarea property="${lbpmTemplateFormPrefix}fdFlowContent" style="display:none"/>
		<iframe ${_lbpm_panel_src_prefix}src="<c:url value="/sys/lbpm/flowchart/page/panel.html" />?edit=true&extend=oa&templateId4View=${param.fdId}&template=true&contentField=${lbpmTemplateFormPrefix}fdFlowContent&modelName=${lbpmTemplate_MainModelName}&FormFieldList=WF_FormFieldList_${lbpmTemplate_Key}&templateModelName=${lbpmTemplate_ModelName}"
				style="width:100%;height:500px" scrolling="no" id="${lbpmTemplateFormPrefix}WF_IFrame"></iframe>
		<html:textarea property="${lbpmTemplateFormPrefix}fdFlowContentDefault" style="display:none"/>
		<iframe ${_lbpm_panel_src_prefix}src="<c:url value="/sys/lbpm/flowchart/page/freeflowPanel.jsp" />?edit=true&extend=oa&flowType=1&templateId4View=${param.fdId}&template=true&contentField=${lbpmTemplateFormPrefix}fdFlowContentDefault&modelName=${lbpmTemplate_MainModelName}&FormFieldList=WF_FormFieldList_${lbpmTemplate_Key}&templateModelName=${lbpmTemplate_ModelName}"
				style="width:100%;height:500px;display:none;" scrolling="no" id="${lbpmTemplateFormPrefix}WF_IFrame_Default"></iframe>
	</td>
</tr>
<tr id="optionSettingRow">
	<td class="td_normal_title" width=15%>
		<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.optionSetting"/>
	</td>
	<td>
		<label id="isHiddenPostInNoteLabel">
			<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}hiddenPostInNote" value="true">
			<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHiddenPostInNoteConfigurable"/>&nbsp;&nbsp;
		</label>
		<label id="isHiddenDayOfPassInfoLabel">
			<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}hiddenDayOfPassInfo" value="true">
			<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHiddenDayOfPassInfoConfigurable"/>&nbsp;&nbsp;
		</label>
		<label id="isHiddenIdentityRepeatOfPassInfoLabel">
			<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}hiddenIdentityRepeatOfPassInfo" value="true">
			<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHiddenIdentityRepeatOfPassInfo"/>&nbsp;&nbsp;
		</label>
		<label id="isMultiCommunicateEnabledLabel">
			<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}multiCommunicateEnabled" value="true">
			<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isMultiCommunicateConfigurable"/>&nbsp;&nbsp;
		</label>
		<label id="isHiddenCommunicateNoteEnabledLabel">
			<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}hiddenCommunicateNoteEnabled" value="true">
			<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHiddenCommunicateNoteConfigurable"/>&nbsp;&nbsp;
		</label>
		<label>
			<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}ignoreOnFutureHandlerSame" value="true">
			<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.ignoreOnFutureHandlerSame"/>&nbsp;
		</label>
		<label id="ignoreOnFutureHandlerSamePlusLabel" title='<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.ignoreOnFutureHandlerSamePlus.title"/>'>
			<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}ignoreOnFutureHandlerSamePlus" value="true">
			<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.ignoreOnFutureHandlerSamePlus"/>&nbsp;
		</label>
		<label onclick="showSkipRuleDetailDialog();">
			<img src="${KMSS_Parameter_ContextPath}sys/lbpmservice/resource/images/icon_help.png" style="margin-top:-2px;"></img>&nbsp;&nbsp;
		</label>
		<label title='<bean:message bundle="sys-lbpmservice-node-votenode" key="lbpmTemplate.anonymousVote.title"/>'>
			<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}anonymousVote" value="true">
			<bean:message bundle="sys-lbpmservice-node-votenode" key="lbpmTemplate.anonymousVote"/>&nbsp;
		</label>

		<label id="isRefuseSelectPeople">
			<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}refuseSelectPeople" value="true">
			<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isRefuseSelectPeople"/>&nbsp;
		</label>

		<label id="isSendHistoryHandlerNotify">
			<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}sendHistoryHandlerNotify"  value="true">
			<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.notify.historyHandlerNotify.send"/>&nbsp;&nbsp;
		</label>

	</td>
</tr>
<tr id="freeFlowOptionSettingRow" style="display:none;">
	<td class="td_normal_title" width=15%>
		<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.optionSetting"/>
	</td>
	<td>
		<label>
			<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}isCanAddOtherNode" value="true">
			<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.isCanAddOtherNode"/>
		</label>
	</td>
</tr>
<tr id="processOptionsRow">
	<td class="td_normal_title" width=15%>
		<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.processOptions"/>
	</td><td>
	<label>
		<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}rejectReturn" value="true">
		<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.rejectReturn"/>
	</label>
	<label>
		<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}recalculateHandler" value="true"
			   checked onclick="LBPM_Template_ChangeRecalculateHandler(this, '${lbpmTemplateFormPrefix}');">
		<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.isRecalculate"/>
	</label>
</td>
</tr>
<tr id="processPopedomRow">
	<td class="td_normal_title" width=15%>
		<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.processPopedom"/>
	</td><td>
	<a href="javascript:void(0)" onclick="LBPM_Template_ChangeProcessPopedom('${lbpmTemplateFormPrefix}');">
		<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.processPopedomModify"/>
	</a>
</td>
</tr>
<tr id="auditnoteRow">
	<style>
		.auditnoteTipArea:before,.auditnoteTipArea:after{
			content:"";
			display:inline-block;
			width:0;
			height:0;
			border: 6px solid transparent;
			border-bottom-color: #EAEDF3;
			position:absolute;
			top: -13px;
			z-index: 1;
			left: 50%;
			transform: translateX(-50%);
		}
		.auditnoteTipArea:after{
			border-bottom-color: #fff;
			margin-top: 1px;
		}
		.auditnoteTipArea{
			display: none;
			list-style: none;
			background: #FFFFFF;
			border: 1px solid #EAEDF3;
			box-shadow: 0 1px 3px 0 rgba(0,0,0,0.04);
			border-radius: 4px;
			width: 200px;
			text-align: left;
			position: fixed;
			z-index: 999;
			word-break:break-all;
		}
	</style>
	<td class="td_normal_title" width=15%>
		<bean:message bundle='sys-lbpmservice-support' key='lbpmTemplate.opinionSort'/><img class="auditnoteTip" data-title="<bean:message bundle='sys-lbpmservice-support' key='lbpmTemplate.opinionSortTip'/>" src="${KMSS_Parameter_ContextPath}sys/lbpmservice/resource/images/icon_help.png" style="margin-top:-2px;"></img>
		<div class="auditnoteTipArea"></div>
	</td><td>
	<input type="hidden" name="wf_${lbpmTemplateFormPrefix}opinionSortIds">
	<input type="hidden" name="wf_${lbpmTemplateFormPrefix}opinionSortNames">
	<input type="hidden" name="wf_${lbpmTemplateFormPrefix}opinionSortType" value="node">
	<a href="javascript:void(0)" onclick="LBPM_Template_SETAUDITNOTE('${lbpmTemplateFormPrefix}');">
		<bean:message bundle='sys-lbpmservice-support' key='lbpmTemplate.opinionSortNode'/>
	</a>
	<img class="auditnoteTip" data-title="<bean:message bundle='sys-lbpmservice-support' key='lbpmTemplate.opinionSortNodeTip'/>" src="${KMSS_Parameter_ContextPath}sys/lbpmservice/resource/images/icon_help.png" style="margin-top:-2px;"></img>
</td>
</tr>
<tr id="eventsRow">
	<td class="td_normal_title" width=15%>
		<bean:message bundle='sys-lbpmservice-support' key='lbpmEventsLog.eventsSetting'/>
	</td><td>

	<a href="javascript:void(0)" onclick="LBPM_Template_SETEVENTSONNODE('${lbpmTemplateFormPrefix}');">
		<bean:message bundle='sys-lbpmservice-support' key='lbpmEventsLog.settingNodeEvents'/>
	</a>
</td>
</tr>
<tr id="notifyOptionsRow">
	<td class="td_normal_title" width=15%>
		<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.notifyOptions"/>
	</td><td id="${lbpmTemplateFormPrefix}WF_TD_notifyType">
	<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.notifyType.default"/>
	<kmss:editNotifyType property="wf_${lbpmTemplateFormPrefix}notifyType" value="" /><span class="txtstrong">*</span><br>
	<span class="com_help"><bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.notifyType.default.info"/></span>
	<br>
	<label>
		<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}notifyOnFinish" value="true" checked="checked">
		<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.notifyOnFinish"/>
	</label>
	<label>
		<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}notifyDraftOnFinish" value="true" checked="checked">
		<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.notifyDraftOnFinish"/>
	</label>
	<br>
	<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.dayOfNotifyPrivileger1"/>
	<input name="wf_${lbpmTemplateFormPrefix}dayOfNotifyPrivileger" class="inputsgl" style="text-align:center" value="0" size="3" maxlength="3"><kmss:message key="FlowChartObject.Lang.Node.day" bundle="sys-lbpmservice" />
	<input name="wf_${lbpmTemplateFormPrefix}hourOfNotifyPrivileger" class="inputsgl" style="text-align:center" value="0" size="3" maxlength="4"><kmss:message key="FlowChartObject.Lang.Node.hour" bundle="sys-lbpmservice" />
	<input name="wf_${lbpmTemplateFormPrefix}minuteOfNotifyPrivileger" class="inputsgl" style="text-align:center" value="0" size="3" maxlength="5"><kmss:message key="FlowChartObject.Lang.Node.minute" bundle="sys-lbpmservice" />
	<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.dayOfNotifyPrivileger2"/>

	<br>
	<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.dayOfNotifyPrivileger1"/>
	<input name="wf_${lbpmTemplateFormPrefix}dayOfNotifyDrafter" class="inputsgl" style="text-align:center" value="0" size="3" maxlength="3"><kmss:message key="FlowChartObject.Lang.Node.day" bundle="sys-lbpmservice" />
	<input name="wf_${lbpmTemplateFormPrefix}hourOfNotifyDrafter" class="inputsgl" style="text-align:center" value="0" size="3" maxlength="4"><kmss:message key="FlowChartObject.Lang.Node.hour" bundle="sys-lbpmservice" />
	<input name="wf_${lbpmTemplateFormPrefix}minuteOfNotifyDrafter" class="inputsgl" style="text-align:center" value="0" size="3" maxlength="5"><kmss:message key="FlowChartObject.Lang.Node.minute" bundle="sys-lbpmservice" />
	<bean:message bundle="sys-lbpmservice" key="FlowChartObject.Lang.Node.tranNotifyDraft1"/>

</td>
</tr>
<tr>
	<td class="td_normal_title" width=15%>
		<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.privileger"/>
	</td><td>
	<input type="hidden" name="wf_${lbpmTemplateFormPrefix}privilegerIds">
	<input name="wf_${lbpmTemplateFormPrefix}privilegerNames" readonly class="inputsgl" style="width:95%">
	<a href="javascript:void(0)" onclick="Dialog_Address(true, 'wf_${lbpmTemplateFormPrefix}privilegerIds', 'wf_${lbpmTemplateFormPrefix}privilegerNames', null, ORG_TYPE_POSTORPERSON|ORG_TYPE_ROLE);">
		<bean:message key="dialog.selectOrg"/></a>
</td>
</tr>

<script src="<c:url value="/sys/lbpm/flowchart/js/workflow.js"/>"></script>
<script>
	Com_IncludeFile("jquery.js|data.js");

	Com_IncludeFile("json2.js");
	var langJson = <%=MultiLangTextareaGroupTag.getLangsJsonStr()%>;
	var isLangSuportEnabled = <%=MultiLangTextareaGroupTag.isLangSuportEnabled()%>;

	if(window.LBPM_Template_Prefix == null) {
		LBPM_Template_Prefix = new Array();
	}
	LBPM_Template_Prefix["${lbpmTemplate_Key}"] = "${lbpmTemplateFormPrefix}";

	// 流程图初始内容，用于比较流程图内容是否有修改
	if(window.LBPM_Template_InitFlowContent == null) {
		LBPM_Template_InitFlowContent = new Array();
	}

	// 数据初始化
	function LBPM_Template_LoadProcessData(key, prefix) {
		var content = $("textarea[name='"+prefix+"fdFlowContent']").val();
		if(content == "") {
			LBPM_Template_InitFlowContent[key] = "";
			// 设置特权人默认通知天数
			var _dayOfNotifyPrivileger = document.getElementsByName("wf_" + prefix + "dayOfNotifyPrivileger")[0];
			if(_dayOfNotifyPrivileger) {
				_dayOfNotifyPrivileger.value = "15";
			}
			// 设置默认通知方式
			new KMSSData().AddBeanData("lbpmBaseInfoService").PutToField("defaultNotifyType", "wf_" + prefix + "notifyType");

			// 设置流程意见隐藏岗位默认值
			/* new KMSSData().AddBeanData("lbpmBaseInfoService").PutToField("hiddenPostInNote", "wf_" + prefix + "hiddenPostInNote");
            var isHidden = $("input[name='wf_"+prefix+"hiddenPostInNote']").val();
            $("input[name='wf_"+prefix+"hiddenPostInNote']").each(function(){
                if(isHidden && isHidden=="true"){
                    $(this).attr("checked",'checked');
                }
            }); */
		} else {
			var processData = WorkFlow_LoadXMLData(content);
			if(LBPM_Template_InitFlowContent[key] == "" || typeof(LBPM_Template_InitFlowContent[key]) == "undefined"){
				LBPM_Template_InitFlowContent[key] = WorkFlow_BuildXMLString(processData, "process");
			}
			if(processData.description){
				var changedText = processData.description;
				// 还原换行符
				changedText = changedText.replace(/&#xD;/g, "\r");
				changedText = changedText.replace(/&#xA;/g, "\n");
				processData.description = changedText;
			}

			handleDescriptionLang4Edit(processData,prefix);

			WorkFlow_PutDataToField(processData, function(propertyName){
				return "wf_" + prefix + propertyName;
			});
		}
		var field = $("input[name='wf_"+prefix+"notifyType']").val();
		WorkFlow_RefreshNotifyType(prefix+"WF_TD_notifyType", field);
		LBPM_Template_LoadSettingOption(prefix);
	}

	function handleDescriptionLang4Edit(processData,prefix){
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
			// 还原换行符
			value = value.replace(/&#xD;/g, "\r");
			value = value.replace(/&#xA;/g, "\n");
			return value;
		}

		if(!isLangSuportEnabled){
			return ;
		}
		if(processData.descriptionLangJson){
			var descriptionLangJson = $.parseJSON(processData.descriptionLangJson);
			for(var i=0;i<langJson["support"].length;i++){
				var lang = langJson["support"][i]["value"];
				var elName = "_wf_" + prefix + "description";
				document.getElementsByName(elName+"_"+lang)[0].value= _getLangLabelByJson("",descriptionLangJson, lang);
			}
		}
	}
	function handleDescriptionLang4Save(processData,prefix){
		//[{lang:"zh-CN","value":""},{lang:"en-US","value":""}]
		var elLang=[];
		if(!isLangSuportEnabled){
			return ;
		}
		var eleName = "wf_" + prefix + "description";
		var fdValue = document.getElementsByName(eleName)[0].value;
		fdValue = _formatDescription(fdValue);
		var officialElName="_"+eleName+"_"+langJson["official"]["value"];
		document.getElementsByName(officialElName)[0].value=fdValue;
		var lang={};
		lang["lang"]=langJson["official"]["value"];
		lang["value"]=fdValue;
		elLang.push(lang);
		for(var i=0;i<langJson["support"].length;i++){
			var elName = "_"+eleName+"_"+langJson["support"][i]["value"];
			if(elName==officialElName){
				continue;
			}
			lang={};
			lang["lang"]=langJson["support"][i]["value"];
			lang["value"]=_formatDescription(document.getElementsByName(elName)[0].value);
			elLang.push(lang);
		}
		processData.descriptionLangJson=JSON.stringify(elLang);
	}

	function _formatDescription(value){
		value=value||"";
		value = value.replace(/\r/g, "&#xD;");
		value = value.replace(/\n/g, "&#xA;");
		return value;
	}

	Com_AddEventListener(window, "load", function() {
		LBPM_Template_LoadProcessData("${lbpmTemplate_Key}", "${lbpmTemplateFormPrefix}");
		if("${lbpmTemplateForm.fdType}"=="4"){
			//若为自由流编辑，则还原自定义的流程图为默认的，因自定义的流程图加载的也是fdFlowContent的流程图
			var prefix = "${lbpmTemplateFormPrefix}";
			$("textarea[name='"+prefix+"fdFlowContent']").val("");
			var iframe = document.getElementById(prefix+"WF_IFrame").contentWindow;
			//chrome无法刷新iframe?
			setTimeout(function(){
				iframe.location.reload();
			},0);
			setTimeout(LBPM_Template_Load_FlowChartObject_${lbpmTemplate_Key},500);
		}
	});


	function findNewFormIdByOldId(formIds, oldId) {
		for (var i = 0; i < formIds.length; i++) {
			if (formIds[i] === oldId) {
				return formIds[i];
			}
		}
	}

	function findNewFormIdByOldFormId(formList,oldFormId) {
		if (formList && oldFormId) {
			for (var i = 0; i < formList.length; i++) {
				if (oldFormId === formList[i].fdOldFormId) {
					return formList[i];
				}
			}
		}
	}

	// 提交校验
	Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function() {
		var key = "${lbpmTemplate_Key}", prefix = "${lbpmTemplateFormPrefix}";
		if(LBPM_Template_InitFlowContent[key] == null) {
			return false;
		}
		var _fdType = $("input[name='"+prefix+"fdType']:checked");
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

			var FlowChartObject = LBPM_Template_GetFlowChartWindow(prefix).FlowChartObject;
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
			//嵌入子流程信息
			var fdEmbeddedInfo = [];
			var fdDynamicInfo = [];
			if (processData["nodes"]){
				var nodes = processData["nodes"];
				//多表单，判断节点中配置表单id是否存在
				var oldIds = [];
				var formList = [];
				var isExitWebDef = false;
				$("#TABLE_DocList_SubForm").find("tr[ischecked]").each(function(){
					oldIds.push(this.id);
					if($(this).attr("defaultWebForm") && $(this).attr("defaultWebForm")=="true"){
						isExitWebDef = true;
					}
				});
				//if ($("#TABLE_DocList_SubForm").length == 0) {
				if (typeof XForm_getSubFormInfo_${lbpmTemplate_Key} != "undefined") {
					var formData = XForm_getSubFormInfo_${lbpmTemplate_Key}();
					formList = formData;
					for (var i = 0; i < formData.length; i++) {
						if (oldIds.indexOf(formData[i].id) < 0) {
							oldIds.push(formData[i].id);
						}
					}
				}
				//}
				$("#TABLE_DocList_Print").find("tr[ischecked]").each(function(){
					oldIds.push(this.id);
				});
				//兼容建模
				if($("#TABLE_DocList_Print").find("tr[ischecked]").length <= 0 && '${lbpmTemplate_Key}' == 'modelingApp' && Print_getSubPrintIds){
					var ids = Print_getSubPrintIds();
					oldIds = oldIds.concat(ids);
				}
				for(var index = 0; index < nodes.length; index++){
					if(nodes[index] && nodes[index].XMLNODENAME === "embeddedSubFlowNode" && nodes[index].embeddedRefId){
						fdEmbeddedInfo.push({nodeId:nodes[index].id,fdRefId:nodes[index].embeddedRefId});
					}
					if(nodes[index] && nodes[index].XMLNODENAME === "dynamicSubFlowNode" && nodes[index].dynamicGroupId){
						fdDynamicInfo.push({nodeId:nodes[index].id,fdId:nodes[index].dynamicGroupId});
					}
					if (nodes[index] && nodes[index].XMLNODENAME === "draftNode" && typeof nodes[index].canAddAuditNoteAtt === "undefined"){
						nodes[index].canAddAuditNoteAtt = getSettingInfo()["isCanAddAuditNoteAtt"];
					}
					if(nodes[index] && nodes[index].subFormId && oldIds.join(",").indexOf(nodes[index].subFormId)<0){
						var formInfo = findNewFormIdByOldFormId(formList, nodes[index].subFormId);
						if (formInfo) {
							nodes[index].subFormId = formInfo.id;
							nodes[index].subFormName = formInfo.name;
						} else {
							nodes[index].subFormId = "default";
							nodes[index].subFormName = "<kmss:message key="lbpmNode.subform.defaut_form" bundle="sys-lbpmservice" />";
						}
					}
					if(nodes[index] && ((nodes[index].subFormMobileId && oldIds.join(",").indexOf(nodes[index].subFormMobileId)<0))){
						var formInfo = findNewFormIdByOldFormId(formList, nodes[index].subFormMobileId);
						if (formInfo) {
							nodes[index].subFormMobileId = formInfo.id;
							nodes[index].subFormMobileName = formInfo.name;
						} else {
							nodes[index].subFormMobileId = "default";
							if(isExitWebDef){
								nodes[index].subFormMobileName = "<kmss:message key="lbpmNode.subform.default_web_form" bundle="sys-lbpmservice" />";
							}else{
								nodes[index].subFormMobileName = "<kmss:message key="lbpmNode.subform.defaut_form" bundle="sys-lbpmservice" />";
							}
						}
					}
					if(nodes[index] && nodes[index].subFormPrintId && oldIds.join(",").indexOf(nodes[index].subFormPrintId)<0){
						nodes[index].subFormPrintId = "default";
						nodes[index].subFormPrintName = "<kmss:message key="lbpmNode.subform.default_print_form" bundle="sys-lbpmservice" />";
					}
				}
			}
			processData['orgAttribute'] = "privilegerIds:privilegerNames";

			handleDescriptionLang4Save(processData,prefix);

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
			}, document.getElementById("TB_LbpmTemplate_"+key));
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
			//添加是否为多表单标识
			if(typeof Form_getModeValue != "undefined" && Form_getModeValue(key)=="<%=XFormConstant.TEMPLATE_SUBFORM%>"){
				processData["subFormMode"] = 'true';
			}
			// 设置流程内容
			var xml = WorkFlow_BuildXMLString(processData, "process");
			$("textarea[name='"+prefix+"fdFlowContent']").val(xml);
			// 比较流程内容是否修改
			var _fdIsModified = $("input[name='"+prefix+"fdIsModified']");
			if(LBPM_Template_InitFlowContent[key] != xml || _fdIsModified.val() == 'true') {
				_fdIsModified.val("true");
				if(fdEmbeddedInfo.length>0){
					$("input[name='"+prefix+"fdEmbeddedInfo']").val(JSON.stringify(fdEmbeddedInfo));
				}
				if(fdDynamicInfo.length>0){
					$("input[name='"+prefix+"fdDynamicInfo']").val(JSON.stringify(fdDynamicInfo));
				}
			} else {
				_fdIsModified.val("false");
			}
		}
		return true;
	};
	// 流程图页面Window对象
	function LBPM_Template_GetFlowChartWindow(prefix) {
		var _fdType = $("input[name='"+prefix+"fdType']:checked");
		if(_fdType.val() == "4") {
			// 自由流默认流程图
			return document.getElementById(prefix+"WF_IFrame_Default").contentWindow;
		} else {
			return document.getElementById(prefix+"WF_IFrame").contentWindow;
		}
	}
	// 是否重新计算处理人
	function LBPM_Template_ChangeRecalculateHandler(checkbox, prefix) {
		var FlowChartObject = LBPM_Template_GetFlowChartWindow(prefix).FlowChartObject;
		FlowChartObject.ProcessData.recalculateHandler = checkbox.checked ? "true" : "false";
	}
	// 修改流程权限
	function LBPM_Template_ChangeProcessPopedom(prefix) {
		Com_EventPreventDefault();
		var chartWindow = LBPM_Template_GetFlowChartWindow(prefix);
		var width = 640;
		var height = 480;
		var left = (screen.width-width)/2;
		var top = (screen.height-height)/2;
		var parameter={FlowChartObject:chartWindow.FlowChartObject,Window:chartWindow};
		if(window.showModalDialog){
			var winStyle = "resizable:1;scroll:1;dialogwidth:"+width+"px;dialogheight:"+height+"px;dialogleft:"+left+";dialogtop:"+top;
			window.showModalDialog('<c:url value="/sys/lbpm/flowchart/page/process_popedom_modify_content.html"/>', parameter, winStyle);
		}else{
			var winStyle = "resizable=1,scrollbars=1,width="+width+",height="+height+",left="+left+",top="+top+",dependent=yes,alwaysRaised=1";
			Com_Parameter.Dialog = parameter;
			window.open('<c:url value="/sys/lbpm/flowchart/page/process_popedom_modify_content.html"/>', "_blank", winStyle);
		}

	}

	var __SettingInfo = null;

	//统一调用此方法获取默认值与功能开关的值
	function getSettingInfo(){
		if (__SettingInfo == null) {
			__SettingInfo = new KMSSData().AddBeanData("lbpmSettingInfoService").GetHashMapArray()[0];
		}
		return __SettingInfo;
	}

	// 加载功能开关以及默认值设置
	function LBPM_Template_LoadSettingOption(prefix){
		var data = new Array();
		data = new KMSSData().AddBeanData("lbpmSettingInfoService").GetHashMapArray()[0];
		var hasOpt = false;
		if(data['isHiddenPostInNoteConfigurable']=="true"){
			hasOpt = true;
		} else {
			$("#isHiddenPostInNoteLabel").remove();
		}

		if(data['isHiddenDayOfPassInfoConfigurable']=="true"){
			hasOpt = true;
		} else {
			$("#isHiddenDayOfPassInfoLabel").remove();
		}

		if(data['isHiddenIdentityRepeatOfPassInfo']=="true"){
			hasOpt = true;
		} else {
			$("#isHiddenIdentityRepeatOfPassInfoLabel").remove();
		}

		if(data['isRefuseSelectPeople']=="true"){
			hasOpt = true;
		} else {
			$("#isRefuseSelectPeople").remove();
		}

		if(data['isSendHistoryHandlerNotify']=="true"){
			hasOpt = true;
		} else {
			$("#isSendHistoryHandlerNotify").remove();
		}

		if(data['isMultiCommunicateConfigurable']==null || data['isMultiCommunicateConfigurable']=="true"){
			hasOpt = true;
			if (data['isMultiCommunicateEnabledDefault']==null || data['isMultiCommunicateEnabledDefault']=="true") {
				$("input[name='wf_"+prefix+"multiCommunicateEnabled']").each(function(){
					$(this).attr("checked",'checked');

				});
			}
		} else {
			$("#isMultiCommunicateEnabledLabel").remove();
		}

		if(data['isHiddenCommunicateNoteConfigurable']==null || data['isHiddenCommunicateNoteConfigurable']=="true"){
			hasOpt = true;
			if (data['isHiddenCommunicateNoteEnabledDefault']==null || data['isHiddenCommunicateNoteEnabledDefault']=="true") {
				$("input[name='wf_"+prefix+"hiddenCommunicateNoteEnabled']").each(function(){
					$(this).attr("checked",'checked');
				});
			}
		} else {
			$("#isHiddenCommunicateNoteEnabledLabel").remove();
		}

		if (data['isShowRefuseOptonal'] === "true"){
			$("#processOptionsRow").find("input[name='wf_" + prefix + "rejectReturn']").parent("label").show();
		}else{
			$("#processOptionsRow").find("input[name='wf_" + prefix + "rejectReturn']").parent("label").hide();
		}

		if (hasOpt == false) {
			if($("#optionSettingRow").find("input").length <=0 && $("#optionSettingRow").find("textarea").length <= 0){
				$("#optionSettingRow").remove();
			}
		}

		var content = $("textarea[name='"+prefix+"fdFlowContent']").val();
		if(!content){
			if(data['notifyOnFinish'] === "false"){
				$("input[name='wf_"+prefix+"notifyOnFinish']").prop("checked",false);
			}
			if(data['notifyDraftOnFinish'] === "false"){
				$("input[name='wf_"+prefix+"notifyDraftOnFinish']").prop("checked",false);
			}
			if(data['dayOfNotifyPrivileger']){
				$("input[name='wf_"+prefix+"dayOfNotifyPrivileger']").val(data['dayOfNotifyPrivileger']);
			}
			if(data['hourOfNotifyPrivileger']){
				$("input[name='wf_"+prefix+"hourOfNotifyPrivileger']").val(data['hourOfNotifyPrivileger']);
			}
			if(data['minuteOfNotifyPrivileger']){
				$("input[name='wf_"+prefix+"minuteOfNotifyPrivileger']").val(data['minuteOfNotifyPrivileger']);
			}
			if(data['dayOfNotifyDrafter']){
				$("input[name='wf_"+prefix+"dayOfNotifyDrafter']").val(data['dayOfNotifyDrafter']);
			}
			if(data['hourOfNotifyDrafter']){
				$("input[name='wf_"+prefix+"hourOfNotifyDrafter']").val(data['hourOfNotifyDrafter']);
			}
			if(data['minuteOfNotifyDrafter']){
				$("input[name='wf_"+prefix+"minuteOfNotifyDrafter']").val(data['minuteOfNotifyDrafter']);
			}
		}
	}

	function showSkipRuleDetailDialog(){
		var height = screen.height * 0.6;
		var width = screen.width * 0.6;
		if (typeof(seajs) != 'undefined' && typeof( top['seajs'] ) != 'undefined' ) {
			seajs.use(['lui/dialog'], function(dialog) {
				var url = "/sys/lbpmservice/support/lbpm_template_new/lbpmTemplate_help_skipRule.jsp";
				dialog.iframe(url,'<bean:message key="lbpmTemplate.instructions" bundle="sys-lbpmservice-support"/>',null,{width:width,height : height});
			});
		}else{
			Dialog_PopupWindow("<c:url value="/sys/lbpmservice/support/lbpm_template_new/lbpmTemplate_help_skipRule.jsp"/>", width, height, null);
		}
	}

	$(function(){
		$(".auditnoteTip").mouseover(function(){
			$(".auditnoteTipArea").text($(this).data("title"));
			$(".auditnoteTipArea").show();
			var left = $(this).offset().left-$(".auditnoteTipArea").width()/2+$(this).width()/2;
			var top = $(this).offset().top+23;
			var h = document.documentElement.scrollTop || document.body.scrollTop;
			$(".auditnoteTipArea").css({
				"left" : left,
				"top" : top-h
			});
		});
		$(".auditnoteTip").mouseout(function(){
			$(".auditnoteTipArea").hide();
		});
	})

function LBPM_Template_SETEVENTSONNODE(prefix) {

	var param = prefix;
	var fdProcessTemplateId = $("[name='"+prefix+"fdId']").val();
	if (typeof(seajs) != 'undefined' && typeof( top['seajs'] ) != 'undefined' ) {
		seajs.use(['lui/dialog'], function(dialog) {
			var url = "/sys/lbpmservice/support/lbpm_template/lbpmTemplate_eventsSetting.jsp?&param="+param +"&fdProcessTemplateId="+fdProcessTemplateId;
			dialog.iframe(url,'<bean:message key="table.lbpmEventsLog" bundle="sys-lbpmservice-support"/>',null,{width:800,height : 600});
		});
	}else{
		Dialog_PopupWindow("<c:url value="/sys/lbpmservice/support/lbpm_template/lbpmTemplate_eventsSetting.jsp"/>", 800, 600, null);
	}
}
function LBPM_Template_SETAUDITNOTE(prefix) {
		Com_EventPreventDefault();
		var chartWindow = LBPM_Template_GetFlowChartWindow(prefix);
		var FlowChartObject = chartWindow.FlowChartObject;
		var data = new KMSSData();
		for(var i=0; i<FlowChartObject.Nodes.all.length; i++){
			var node = FlowChartObject.Nodes.all[i];
			if(node.Data.groupNodeId != null)
				continue;
			var nodDesc = FlowChartObject.Nodes.nodeDesc(node);
			if(node.Type == "embeddedSubFlowNode"){
				if(lbpm){
					var fdContent = getContentByRefId(node.Data.embeddedRefId);
					if(fdContent){
						//嵌入的流程图对象
						var embeddedFlow = WorkFlow_LoadXMLData(fdContent);
						for(var j = 0;j<embeddedFlow.nodes.length;j++){
							var eNode = embeddedFlow.nodes[j];
							if(_isHandler(eNode) || _isDraftNode(eNode) || _isSendNode(eNode)){
								data.AddHashMap({id:node.Data.id+"-"+eNode.id, name:node.Data.id+"."+node.Data.name+"("+eNode.id+"."+eNode.name+")"});
							}
						}
					}
				}
			}else if(node.Type == "adHocSubFlowNode"){
				if(lbpm){
					var fdContent = node.Data.adHocSubFlowData;
					if(fdContent){
						//即席的流程图对象
						var adHocSubFlow = WorkFlow_LoadXMLData(fdContent);
						for(var j = 0;j<adHocSubFlow.nodes.length;j++){
							var sNode = adHocSubFlow.nodes[j];
							if(_isHandler(sNode) || _isDraftNode(sNode) || _isSendNode(sNode)){
								data.AddHashMap({id:node.Data.id+"-"+sNode.id, name:node.Data.id+"."+node.Data.name+"("+sNode.id+"."+sNode.name+")"});
							}
						}
					}
				}
			}else if (nodDesc.isHandler || nodDesc.isDraftNode || nodDesc.isSendNode || node.Type == "freeSubFlowNode") {
				data.AddHashMap({id:node.Data.id, name:node.Data.id+"."+node.Data.name});
			}
		}
		var dialog = new KMSSDialog(true, true);
		dialog.winTitle = FlowChartObject.Lang.dialogTitle;
		dialog.AddDefaultOption(data);
		dialog.BindingField("wf_"+prefix+"opinionSortIds", "wf_"+prefix+"opinionSortNames", ";");
		dialog.Show();
	}

	function _isHandler(node) {
		var sNodDesc = lbpm.nodedescs[lbpm.nodeDescMap[node.XMLNODENAME]];
		return sNodDesc ? (
				sNodDesc.isHandler() &&
				!sNodDesc.isAutomaticRun() &&
				!sNodDesc.isBranch() &&
				!sNodDesc.isSubProcess() &&
				!sNodDesc.isConcurrent() &&
				sNodDesc.uniqueMark() == null
		) ||  sNodDesc.uniqueMark() == 'signNodeDesc' ||  sNodDesc.uniqueMark() == 'voteNodeDesc' : false;
	};
	function _isDraftNode(node) {
		var sNodDesc = lbpm.nodedescs[lbpm.nodeDescMap[node.XMLNODENAME]];
		return sNodDesc ? (sNodDesc.uniqueMark() == 'draftNodeDesc') : false;
	};
	function _isSendNode(node) {
		var sNodDesc = lbpm.nodedescs[lbpm.nodeDescMap[node.XMLNODENAME]];
		return sNodDesc ? (
				sNodDesc.isHandler() &&
				sNodDesc.isAutomaticRun() &&
				!sNodDesc.isBranch() &&
				!sNodDesc.isSubProcess() &&
				!sNodDesc.isConcurrent() &&
				sNodDesc.uniqueMark() == null
		) : false;
	};

	//嵌入子流程根据redId获得流程图xml
	function getContentByRefId(fdRefId){
		var fdContent = "";
		var ajaxurl = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpmEmbeddedSubFlow.do?method=getContentByRefId&fdRefId='+fdRefId;
		var kmssData = new KMSSData();
		kmssData.SendToUrl(ajaxurl, function(http_request) {
			var responseText = http_request.responseText;
			var json = eval("("+responseText+")");
			if (json.fdContent){
				fdContent = json.fdContent;
			}
		},false);
		return fdContent;
	}

	//动态子流程根据fdId获得nodeGroups
	var getGroupsByFdId = function(fdId){
		var kmssData = new KMSSData();
		kmssData.UseCache = false;
		var fields = kmssData.AddBeanData("lbpmDynamicSubFlowTreeServiceImp&type=groups&fdId="+fdId).GetHashMapArray();
		if(fields && fields.length>0 && fields[0].groups){
			return fields[0].groups;
		}
		return null;
	}
</script>