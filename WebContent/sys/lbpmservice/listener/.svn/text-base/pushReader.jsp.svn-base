<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("jquery.js|data.js");
</script>
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
	// 通知类型没选择
	if (!$("input[name='notifyType']").val()) {
		alert('<kmss:message bundle="sys-lbpmservice-event-pushreader" key="lbpm.listener.pushreader.notifyType.check" />');
		findNotifyType().focus();
		return false;
	}
	return true;
};

/**
 * 功能：页面提交时返回值（标准接口）
 */
function returnValue() {
	var result = '"notifyTypes":"' + $("input[name='notifyType']").val() + '"';
	return '{' + result + '}';
};
</script>
<table id="List_ViewTable" class="tb_normal" width="98%">
	<tr height="21">
		<td class="td_normal_title" width="15%">
			<bean:message bundle="sys-lbpmservice-event-pushreader" key="lbpm.listener.pushreader.notifyType"/>
		</td>
		<td>
			<kmss:editNotifyType property="notifyType" value="" /><span class="txtstrong">*</span>
		</td>
	</tr>
</table>
<%@ include file="/resource/jsp/edit_down.jsp"%>