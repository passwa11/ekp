<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("jquery.js|form.js");
</script>
<body>
<script type="text/javascript">
<!--
/**
 * 功能：页面初始化（标准接口）
 *
 * @param config 当前修改的监听器参数配置信息，从节点属性窗口传递。
 * @param eventType 当前修改的事件完整参数配置信息。
 *    格式为：{id: 唯一标识, name: 事件配置显示名, type: 事件类型, typeName: 事件显示名, eventConfig: 事件参数配置,
 *	             listenerId: 监听器类型, listenerName: 监听器显示名, listenerConfig: 监听器参数配置}
 * @param nodeObject 当前配置的节点在流程图中的JS对象
 * @param context 参数配置需要的相关上下文信息。
 *    格式为：{selectedId: 当前选中的事件类型, nodeEvents: 当前节点能选择的事件类型集, modelName: 当前模块的modelName}
 */
function initValue(config, eventType, nodeObject, context){
	// 监听器配置参数
	var listenerConfig = (config == null) ? null : $.parseJSON(config);
	if (listenerConfig == null) {
		return;
	}
	
	// 初始化通知方式选项
	var notifyTypes = listenerConfig.notifyTypes.split(";");
	findNotifyType().each(function() {
		if($.inArray($(this).val(), notifyTypes) > -1){
			$(this).prop("checked", true);
		}
	});
	$("input[name='notifyType']").val(listenerConfig.notifyTypes);
	
	// 初始化相关人，除了其他人
	$("input[type='checkbox'][person]").each(function() {
		if(listenerConfig[$(this).attr("name")]){
			$(this).prop("checked", true);
		}
	});
	
	// 初始化其他人
	$form("personIds").val(listenerConfig.personIds || "");
	$form("personNames").val(listenerConfig.personNames || "");
	
	// 初始化消息参数
	$("input[name='messageParam']").val(listenerConfig.messageParam || "");

	// 初始化主题自动生成规则
	$("input[name='titleRegulation']").val(Com_StrDeEscape(listenerConfig.titleRegulation) || "");
	$("input[name='titleRegulationName']").val(Com_StrDeEscape(listenerConfig.titleRegulationName) || "");
	
	// 初始化是否自动跳过起草节点
	$("input[name='skipDraftNode']").each(function() {
		if(listenerConfig[$(this).attr("name")]){
			$(this).prop("checked", true);
		}
	});
};

// 由于xform生成的消息类型，不是常规的模式，必须要按照其生成的HTML的格式来解析
function findNotifyType() {
	var factFieldName = $("input[name='notifyType']").attr("id").replace("_notifyType", "");
	// 由于配置的是多选，输出的是checkbox
	return $("input[name='" + factFieldName + "']");
};

/**
 * 功能：页面提交时校验方法（标准接口）
 */
function checkValue() {
	// 消息类型没选择...
	if (!$("input[name='notifyType']").val()) {
		alert('<kmss:message bundle="sys-lbpmservice-event-timer" key="lbpm.listener.timer.notifyType.check" />');
		findNotifyType().focus();
		return false;
	}
	
	if (!$("input[name='personIds']").val()) {
		var result = false, focuser = null;
		$("input[type='checkbox'][person]").each(function() {
			focuser = $(this);
			if(focuser.prop("checked")){
				result = true;
				// 退出循环
				return false;
			}
		});
		// 没选择任何通知人...
		if (!result) {
			alert('<kmss:message bundle="km-review" key="kmReviewMain.listener.timer.checkPerson" />');
			if (focuser != null) focuser.focus();
			return false;
		}
	}

	if (!$("input[name='titleRegulation']").val()) {
		alert('<kmss:message bundle="km-review" key="kmReviewMain.listener.timer.titleRegulation.check" />');
		$("input[name='titleRegulationName']").focus();
		return false;
	}
	
	return true;
};

/**
 * 功能：页面提交前被调用（标准接口）
 * @param eventType 字符串，当前对应事件类型
 * @param eventConfig 字符串，当前对应事件的参数配置信息。
 */
function beforeReturnValue(eventType, eventConfig) {
	if (eventType == "timerForHandler") {
		// 对应事件类型为定时器事件
		var _eventConfig = (eventConfig == null) ? null : $.parseJSON(eventConfig);
		if (_eventConfig != null) {
			// 此信息作为通知相关人的消息主题中需要的参数值
			$("input[name='messageParam']").val("timerForHandler:" + _eventConfig.condition);
		}
	}
};

//公式选择器
function genTitleRegByFormula(fieldId, fieldName){
	Formula_Dialog(fieldId,fieldName,Formula_GetVarInfoByModelName("com.landray.kmss.km.review.model.KmReviewMain"), "String");
}

function Formula_GetVarInfoByModelName(modelName){
	return new KMSSData().AddBeanData("sysFormulaDictVarTree&modelName="+modelName).GetHashMapArray();
}

function Formula_Dialog(idField, nameField, varInfo, returnType, action, funcs, model){
	var dialog = new KMSSDialog();
	var funcBean = "sysFormulaFuncTree";
	if(funcs!=null)
		funcBean += "&funcs=" + funcs;
	if(model!=null)
		funcBean += "&model=" + model;
	var funcInfo = new KMSSData().AddBeanData(funcBean).GetHashMapArray();
	dialog.formulaParameter = {
			varInfo: varInfo, 
			funcInfo: funcInfo, 
			returnType: returnType || "Object",
			funcs:funcs==null?"":funcs,
			model:model==null?"":model};
	dialog.BindingField(idField, nameField);
	dialog.SetAfterShow(action);
	dialog.URL = Com_Parameter.ContextPath + "sys/formula/dialog_edit.jsp";
	dialog.Show(850, 550);
}

/***********************************************
功能：转义代码中的敏感字符
***********************************************/
function Com_StrEscape(s){
	if(s==null || s=="")
		return "";
	var re = /&/g;
	s = s.replace(re, "&amp;");
	re = /\\/g;
	s = s.replace(re, "\\\\");
	re = /\"/g;
	s = s.replace(re, "&quot;");
	re = /'/g;
	s = s.replace(re, '&#39;');
	re = /</g;
	s = s.replace(re, "&lt;");
	re = /\r\n|[\r\n]/g;
	s = s.replace(re, "<BR>");
	re = />/g;
	s.replace(re, "&gt;");
	re = /\r\n|[\r\n]/g;
	return s = s.replace(re, "<BR>");
}

/***********************************************
功能：代码中转义符还原成敏感字符
***********************************************/
function Com_StrDeEscape(s){
	if(s==null || s=="")
		return "";
	var re = /<BR>/ig;
	s = s.replace(re, "\r\n");
	re = /&amp;/g;
	s = s.replace(re, "&");
	re = /&quot;/g;
	s = s.replace(re, "\"");
	re = /&#39;/g;
	s = s.replace(re, "'");	
	re = /&lt;/g;
	s = s.replace(re, "<");	
	re = /&gt;/g;
	return s.replace(re, ">");
}

/**
 * 功能：页面提交时返回值（标准接口）
 */
function returnValue() {
	var result = '"notifyTypes":"' + $("input[name='notifyType']").val() + '"';
	// 相关岗位信息
	$("input[type='checkbox'][person]").each(function() {
		if ($(this).prop("checked")) {
			result += ',"' + $(this).attr("name") + '":"' + $(this).val() + '"';
		}
	});
	// 选择的具体人员
	var personIds = $("input[name='personIds']").val();
	if ($.trim(personIds).length > 0) {
		result += ',"personIds":"' + personIds + '"';
		result += ',"personNames":"' + $("input[name='personNames']").val() + '"';
	}
	// 通知相关人的消息主题中需要的参数值
	result += ',"messageParam":"' + $("input[name='messageParam']").val() + '"';

	// 主题自动生成规则
	result += ',"titleRegulation":"' + Com_StrEscape($("input[name='titleRegulation']").val()) + '"';
	result += ',"titleRegulationName":"' + Com_StrEscape($("input[name='titleRegulationName']").val()) + '"';
	
	// 是否自动跳过起草节点
	$("input[name='skipDraftNode']").each(function() {
		if ($(this).prop("checked")) {
			result += ',"' + $(this).attr("name") + '":"' + $(this).val() + '"';
		}
	});
	
	return '{' + result + '}';
};
//-->
</script>
<center>
<input type="hidden" name="messageParam">
<table id="List_ViewTable" class="tb_normal" width="98%">
	<tr height="21">
		<!-- 标题自动生成规则 -->
		<td class="td_normal_title" width="20%">
			<bean:message bundle="km-review" key="kmReviewTemplate.titleRegulation" />
		</td>
		<td>
			<c:if test="${param.method != 'edit'}">
			<input type="hidden" name="titleRegulation">
			<input name="titleRegulationName" class="inputsgl" style="width:85%" readonly style="border-bottom: 0px;" />
			</c:if>
			<c:if test="${param.method == 'edit'}">
			<input type="hidden" name="titleRegulation">
			<input name="titleRegulationName" class="inputsgl" style="width:85%" readonly style="border-bottom: 0px;" /></c:if><span class="txtstrong">*</span>
			<a href="#" onclick="genTitleRegByFormula('titleRegulation','titleRegulationName')"><bean:message bundle="km-review" key="kmReviewTemplate.formula" /></a>
			<br/> 
				<bean:message bundle="km-review" key="kmReviewMain.listener.timer.titleRegulation.tip"/>
		</td>
	</tr>
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-lbpmservice-event-timer" key="lbpm.listener.timer.notifyType"/>
		</td>
		<td>
			<kmss:editNotifyType property="notifyType" value="" /><span class="txtstrong">*</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="20%"><kmss:message key="kmReviewMain.listener.timer.startRule" bundle="km-review" /></td>
		<td>
			<label><input type="checkbox" name="skipDraftNode" value="true"><kmss:message key="kmReviewMain.listener.timer.startRuleSkipDraftNode" bundle="km-review" /></label>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="20%" rowspan="2">
			<bean:message bundle="km-review" key="kmReviewMain.listener.timer.createScope"/>
		</td>
		<td>
			<div _xform_type="address">
				<c:if test="${param.method != 'edit'}">
				<input type="hidden" name="personIds">
				<input name="personNames" class="inputsgl" style="width:95%" readonly style="border-bottom: 0px;" />
				</c:if>
				<c:if test="${param.method == 'edit'}">
				<xform:address propertyId="personIds" propertyName="personNames" 
					mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" style="width:85%" /><span class="txtstrong">*</span>
				</c:if>
			</div>
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/edit_down.jsp"%>