<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextareaGroupTag"%>

<tr>
	<td class="td_normal_title" width=15%>
		<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.fdDescription"/>
	</td><td>
		<span id="wf_${lbpmTemplateFormPrefix}description" name="wf_${lbpmTemplateFormPrefix}description"></span>
	</td>
</tr>
<tr id="flowContentRow">
	<td colspan="2" id="WF_IF_${lbpmTemplateFormPrefix}Chart" onresize="LBPM_Template_LoadWFIFrame_${lbpmTemplate_Key}('${lbpmTemplateFormPrefix}');">
		<textarea name="${lbpmTemplateFormPrefix}fdFlowContent" style="display:none"><c:out value="${lbpmTemplateForm.fdFlowContent}"/></textarea>
		<iframe width="100%" height="100%" scrolling="no" id="${lbpmTemplateFormPrefix}WF_IFrame"></iframe>
		<textarea name="${lbpmTemplateFormPrefix}fdFlowContentDefault" style="display:none"><c:out value="${lbpmTemplateForm.fdFlowContentDefault}"/></textarea>
		<iframe ${_lbpm_panel_src_prefix}src="<c:url value="/sys/lbpm/flowchart/page/freeflowPanel.jsp" />?edit=false&extend=oa&flowType=1&templateId4View=${param.fdId}&template=true&contentField=${lbpmTemplateFormPrefix}fdFlowContentDefault&modelName=${lbpmTemplate_MainModelName}&FormFieldList=WF_FormFieldList_${lbpmTemplate_Key}&templateModelName=${lbpmTemplate_ModelName}"
			style="width:100%;height:500px;display:none;" scrolling="no" id="${lbpmTemplateFormPrefix}WF_IFrame_Default"></iframe>
	</td>
</tr>
<tr id="optionSettingRow">
	<td class="td_normal_title" width=15%>
		<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.optionSetting"/>
	</td>
	<td>
		<label id="isHiddenPostInNoteLabel">
			<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}hiddenPostInNote" value="true" disabled>
			<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHiddenPostInNoteConfigurable"/>&nbsp;&nbsp;
		</label>
		<label id="isHiddenDayOfPassInfoLabel">
			<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}hiddenDayOfPassInfo" value="true" disabled>
			<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHiddenDayOfPassInfoConfigurable"/>&nbsp;&nbsp;
		</label>
		<label id="isHiddenIdentityRepeatOfPassInfoLabel">
			<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}hiddenIdentityRepeatOfPassInfo" value="true" disabled>
			<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHiddenIdentityRepeatOfPassInfo"/>&nbsp;&nbsp;
		</label>
		<label id="isMultiCommunicateEnabledLabel">
			<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}multiCommunicateEnabled" value="true" disabled>
			<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isMultiCommunicateConfigurable"/>&nbsp;&nbsp;
		</label>
		<label id="isHiddenCommunicateNoteEnabledLabel">
			<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}hiddenCommunicateNoteEnabled" value="true" disabled>
			<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHiddenCommunicateNoteConfigurable"/>&nbsp;&nbsp;
		</label>
		<label>
			<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}ignoreOnFutureHandlerSame" value="true" disabled>
			<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.ignoreOnFutureHandlerSame"/>&nbsp;
		</label>
		<label id="ignoreOnFutureHandlerSamePlusLabel" title='<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.ignoreOnFutureHandlerSamePlus.title"/>'>
			<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}ignoreOnFutureHandlerSamePlus" value="true" disabled>
			<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.ignoreOnFutureHandlerSamePlus"/>&nbsp;
		</label>
		<label onclick="showSkipRuleDetailDialog();">
			<img src="${KMSS_Parameter_ContextPath}sys/lbpmservice/resource/images/icon_help.png" style="margin-bottom:-2px;"></img>&nbsp;&nbsp;
		</label>
		<label title='<bean:message bundle="sys-lbpmservice-node-votenode" key="lbpmTemplate.anonymousVote.title"/>'>
			<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}anonymousVote" value="true" disabled>
			<bean:message bundle="sys-lbpmservice-node-votenode" key="lbpmTemplate.anonymousVote"/>&nbsp;
		</label>
		
		<label id="isRefuseSelectPeople">
			<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}refuseSelectPeople" value="true" disabled>
			<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isRefuseSelectPeople"/>&nbsp;
		</label>

		<label id="isSendHistoryHandlerNotify">
			<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}sendHistoryHandlerNotify"  value="true" disabled>
			<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.notify.historyHandlerNotify.send"/>&nbsp;&nbsp;
		</label>

	</td>
</tr>
<tr id="freeFlowOptionSettingRow">
	<td class="td_normal_title" width=15%>
		<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.optionSetting"/>
	</td>
	<td>
		<label>
			<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}isCanAddOtherNode" value="true" disabled>
			<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.isCanAddOtherNode"/>
		</label>
	</td>
</tr>
<tr id="processOptionsRow">
	<td class="td_normal_title" width=15%>
		<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.processOptions"/>
	</td><td>
		<label>
			<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}rejectReturn" value="true" disabled>
			<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.rejectReturn"/>
		</label>
		<label>
			<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}recalculateHandler" value="true" disabled>
			<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.isRecalculate"/>
		</label>
	</td>
</tr>
<tr id="notifyOptionsRow">
	<td class="td_normal_title" width=15%>
		<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.notifyOptions"/>
	</td><td id="${lbpmTemplateFormPrefix}WF_TD_notifyType">
		<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.notifyType.default"/>
		<kmss:showNotifyType value="" /><br>
		<span class="com_help"><bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.notifyType.default.info"/></span>
		<br>
		<label>
			<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}notifyOnFinish" value="true" disabled>
			<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.notifyOnFinish"/>			
		</label>
		<label>
			<input type="checkbox" name="wf_${lbpmTemplateFormPrefix}notifyDraftOnFinish" value="true" disabled>
			<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.notifyDraftOnFinish"/>			
		</label>
		<br>
		<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.dayOfNotifyPrivileger1"/>
		<input name="wf_${lbpmTemplateFormPrefix}dayOfNotifyPrivileger" class="inputread" style="text-align:center" value="0" size="3"><kmss:message key="FlowChartObject.Lang.Node.day" bundle="sys-lbpmservice" />
		<input name="wf_${lbpmTemplateFormPrefix}hourOfNotifyPrivileger" class="inputread" style="text-align:center" value="0" size="3"><kmss:message key="FlowChartObject.Lang.Node.hour" bundle="sys-lbpmservice" />
		<input name="wf_${lbpmTemplateFormPrefix}minuteOfNotifyPrivileger" class="inputread" style="text-align:center" value="0" size="3"><kmss:message key="FlowChartObject.Lang.Node.minute" bundle="sys-lbpmservice" />
		<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.dayOfNotifyPrivileger2"/>

		<br>
		<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.dayOfNotifyPrivileger1"/>
		<input name="wf_${lbpmTemplateFormPrefix}dayOfNotifyDrafter" class="inputread" style="text-align:center" value="0" size="3"><kmss:message key="FlowChartObject.Lang.Node.day" bundle="sys-lbpmservice" />
		<input name="wf_${lbpmTemplateFormPrefix}hourOfNotifyDrafter" class="inputread" style="text-align:center" value="0" size="3"><kmss:message key="FlowChartObject.Lang.Node.hour" bundle="sys-lbpmservice" />
		<input name="wf_${lbpmTemplateFormPrefix}minuteOfNotifyDrafter" class="inputread" style="text-align:center" value="0" size="3"><kmss:message key="FlowChartObject.Lang.Node.minute" bundle="sys-lbpmservice" />
		<bean:message bundle="sys-lbpmservice" key="FlowChartObject.Lang.Node.tranNotifyDraft1"/>

	</td>
</tr>
<tr>
	<td class="td_normal_title" width=15%>
		<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.privileger"/>
	</td><td>
		<span id="wf_${lbpmTemplateFormPrefix}privilegerNames" name="wf_${lbpmTemplateFormPrefix}privilegerNames"></span>
	</td>
</tr>
<script src="<c:url value="/sys/lbpm/flowchart/js/workflow.js"/>"></script>
<script>
Com_IncludeFile("jquery.js|data.js|docutil.js|doclist.js");

Com_IncludeFile("json2.js");
var langJson = <%=MultiLangTextareaGroupTag.getLangsJsonStr()%>;
var isLangSuportEnabled = <%=MultiLangTextareaGroupTag.isLangSuportEnabled()%>;
function handleDescriptionLang4View(processData,prefix){
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
		var elName = "wf_" + prefix + "description";
		var lang = WorkFlow_GetCurrUserLang();
		var value =  _getLangLabelByJson(processData.description,descriptionLangJson, lang);
		document.getElementById(elName).innerText = value||"";
	}
}

if(window.LBPM_Template_Prefix == null) {
	LBPM_Template_Prefix = new Array();
}
LBPM_Template_Prefix["${lbpmTemplate_Key}"] = "${lbpmTemplateFormPrefix}";

function LBPM_Template_LoadProcessData(key, prefix) {
	if(prefix == "") {
		LBPM_Template_LoadWFIFrame_${lbpmTemplate_Key}(prefix);
	}
	var content = document.getElementsByName(prefix + "fdFlowContent")[0].value;
	if(content != ""){
		var processData = WorkFlow_LoadXMLData(content);
		if(processData.description){
			var changedText = processData.description;
			// 还原换行符
			changedText = changedText.replace(/&#xD;/g, "\r");
			changedText = changedText.replace(/&#xA;/g, "\n");
			processData.description = changedText;
		}
		WorkFlow_PutDataToField(processData, function(propertyName){
			return "wf_"+prefix+propertyName;
		});
		handleDescriptionLang4View(processData,prefix);
		WorkFlow_RefreshNotifyType(prefix+"WF_TD_notifyType", processData.notifyType);
		LBPM_Template_LoadSettingOption(prefix);
	}
	if ("${lbpmTemplateForm.fdType}" == "4") {
		//$("#notifyOptionsRow").hide();
		$("#processOptionsRow").hide();
		$("#optionSettingRow").hide();
		$("#freeFlowOptionSettingRow").show();
		//$("#flowContentRow").hide();
		$("[id='"+prefix+"WF_IFrame']").hide();
		$("[id='"+prefix+"WF_IFrame_Default']").show();
	}else{
		$("[id='"+prefix+"WF_IFrame']").show();
		$("[id='"+prefix+"WF_IFrame_Default']").hide();
		$("#freeFlowOptionSettingRow").hide();
	}
}
function LBPM_Template_LoadWFIFrame_${lbpmTemplate_Key}(prefix) {
	Doc_LoadFrame('WF_IF_'+prefix+'Chart', '<c:url value="/sys/lbpm/flowchart/page/panel.html" />?edit=false&extend=oa&template=true&templateId4View=${param.fdId}'
			+'&contentField='+prefix+'fdFlowContent&modelName=${lbpmTemplate_MainModelName}');
}
Com_AddEventListener(window, "load", function() {
	LBPM_Template_LoadProcessData("${lbpmTemplate_Key}", "${lbpmTemplateFormPrefix}");
});

//加载功能开关以及默认值设置
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
	
	if (hasOpt == false) {
		if($("#optionSettingRow").find("input").length <=0 && $("#optionSettingRow").find("textarea").length <= 0){
			$("#optionSettingRow").remove();
		}
	}
	
	if (data['isShowRefuseOptonal'] === "true"){
		$("#processOptionsRow").find("input[name='wf_" + prefix + "rejectReturn']").parent("label").show();
	}else{
		$("#processOptionsRow").find("input[name='wf_" + prefix + "rejectReturn']").parent("label").hide();
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
</script>