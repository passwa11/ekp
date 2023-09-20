<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("jquery.js|form.js");
</script>
<body>
<script type="text/javascript">
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
		var baseInfoData = new KMSSData().AddBeanData("lbpmBaseInfoService").PutToField("defaultNotifyType", "notifyType");
		var notifyTypes = "";
		if(baseInfoData){
			findNotifyType().each(function() {
				if($.inArray($(this).val(), baseInfoData.data[0].defaultNotifyType.split(";")) > -1){
					$(this).prop("checked", true);
					if (notifyTypes == "") {
						notifyTypes += $(this).val();
					} else {
						notifyTypes += ";" + $(this).val();
					}
				}
			});
		}
		$("input[name='notifyType']").val(notifyTypes);
		return;
	}
	
	// 初始化通知方式选项
	var notifyTypes = listenerConfig.notifyTypes.split(";");
	findNotifyType().each(function() {
		if($.inArray($(this).val(), notifyTypes) > -1){
			$(this).prop("checked", true);
		} else {
			$(this).prop("checked", false);
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
	//$("input[name='personIds']").val(listenerConfig.personIds || "");
	//$("input[name='personNames']").val(listenerConfig.personNames || "");
	
	// 初始化消息参数
	$("input[name='messageParam']").val(listenerConfig.messageParam || "");

	// 初始化通用岗位计算方式
	$("input[name='calType']").each(function() {
		if(listenerConfig.calType == $(this).attr("value")){
			$(this).attr("checked", "checked");
		}else{
			$(this).removeAttr("checked");
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
			alert('<kmss:message bundle="sys-lbpmservice-event-timer" key="lbpm.listener.timer.related.check" />');
			if (focuser != null) focuser.focus();
			return false;
		}
	}
	
	return true;
};

/**
 * 功能：页面提交前被调用（标准接口）
 * @param eventType 字符串，当前对应事件类型
 * @param eventConfig 字符串，当前对应事件的参数配置信息。
 */
function beforeReturnValue(eventType, eventConfig) {
	if (eventType == "timerIntermediate") {
		// 对应事件类型为定时器事件
		var _eventConfig = (eventConfig == null) ? null : $.parseJSON(eventConfig);
		if (_eventConfig != null) {
			// 此信息作为通知相关人的消息主题中需要的参数值
			$("input[name='messageParam']").val("timerIntermediate:" + _eventConfig.condition);
		}
	}
};

// 变更通用岗位计算方式的选择项
function changeSelect(obj){
	if($("input[name='calType'][checked]").val() == obj.value){
		return;
	}
	$("input[name='calType'][checked]").removeAttr("checked");
	$(obj).attr("checked", "checked");
};

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

	// 选择的通用岗位计算方式
	result += ',"calType":"' + $("input[name='calType'][checked]").val() + '"';
	
	return '{' + result + '}';
};
</script>
<center>
<input type="hidden" name="messageParam">
<table id="List_ViewTable" class="tb_normal" width="98%">
	<tr height="21">
		<td class="td_normal_title" width="15%">
			<bean:message bundle="sys-lbpmservice-event-timer" key="lbpm.listener.timer.notifyType"/>
		</td>
		<td>
			<kmss:editNotifyType property="notifyType" value="" /><span class="txtstrong">*</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%" rowspan="2">
			<bean:message bundle="sys-lbpmservice-event-timer" key="lbpm.listener.timer.related"/>
		</td>
		<td>
			<c:if test="${param.method != 'edit'}">
			<input type="hidden" name="personIds">
			<input name="personNames" class="inputsgl" style="width:95%" readonly style="border-bottom: 0px;" />
			</c:if>
			<c:if test="${param.method == 'edit'}">
			<div _xform_type="address">
			<xform:address propertyId="personIds" propertyName="personNames" 
				mulSelect="true" orgType="ORG_TYPE_PERSON | ORG_TYPE_POST | ORG_TYPE_ROLE" showStatus="edit" style="width:85%" />
			</div>
			</c:if>
			<br><div id="DIV_NotifyRelatedPersonCalType"><kmss:message key="lbpm.listener.timer.notifyRelatedPersonCalType" bundle="sys-lbpmservice-event-timer" />: 
						<label>
							<input name="calType" type="radio" onclick="changeSelect(this)" value="1" >
							<kmss:message key="FlowChartObject.Lang.Node.handler" bundle="sys-lbpm-engine" />
						</label><label>
							<input name="calType" type="radio" onclick="changeSelect(this)" value="2" checked>
							<kmss:message key="FlowChartObject.Lang.Node.creator" bundle="sys-lbpm-engine" />
						</label><br></div>
		</td>
	</tr>
	<tr>
		<td>
			<label>
				<input type="checkbox" name="handlerLeader" person="true" value="1">
				<kmss:message key="lbpm.listener.timer.related.handlerLeader" bundle="sys-lbpmservice-event-timer" />
			</label>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<label>
				<input type="checkbox" name="admin" person="true" value="1">
				<bean:message key="lbpmBaseInfo.notify.crash.target.type.author"  bundle="sys-lbpm-engine" />
			</label>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<label>
				<input type="checkbox" name="drafter" person="true" value="1">
				<bean:message key="lbpmBaseInfo.notify.crash.target.type.sumbit" bundle="sys-lbpm-engine" />
			</label>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<label>
				<input type="checkbox" name="handler" person="true" value="1">
				<bean:message key="lbpm.listener.timer.related.handler" bundle="sys-lbpmservice-event-timer" />
			</label>
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/edit_down.jsp"%>