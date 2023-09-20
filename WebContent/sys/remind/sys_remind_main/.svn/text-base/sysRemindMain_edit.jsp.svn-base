<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.dialog">
	<template:replace name="head">
		<script language="JavaScript">
			Com_IncludeFile("jquery.js|dialog.js|formula.js|doclist.js|validation.jsp|validation.js|plugin.js|xform.js");
		</script>
		<script language="JavaScript">
		DocList_Info.push("TABLE_DocList_Trigger");
		DocList_Info.push("TABLE_DocList_Receiver");
		seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
			// 初始化表单数据
			window.isInit = false;
			window.init_field = function() {
				if(window.isInit) {
					return;
				}
				// 获取表单字段列表
				LBPM_Template_FormFieldList = parent.LBPM_Template_FormFieldList;
				if(!LBPM_Template_FormFieldList) {
					dialog.failure("无法获取表单内容，请检查当前模板是否有部署主文档！");
					return false;
				}
				if(window.console) {
					var FormFieldList = [];
					for(var i in LBPM_Template_FormFieldList) {
						var formField = LBPM_Template_FormFieldList[i];
						FormFieldList.push("{'id':"+formField.name+",'name':"+formField.label+"}");
					}
				}
				// 提取需要用到的表单字段
				Sender_List = [], Receiver_List = [], Date_List = [], DateTime_List = [];
				for(var i in LBPM_Template_FormFieldList) {
					var formField = LBPM_Template_FormFieldList[i];
					if(formField.type == "DateTime") {
						// 日期时间
						DateTime_List.push(formField);
					} else if(formField.type == "Date") {
						// 日期
						Date_List.push(formField);
					} else if(formField.controlType == "address" || formField.controlType == "new_address"
							|| formField.type == "com.landray.kmss.sys.organization.model.SysOrgPerson") {
						// 接收人，所有组织架构
						Receiver_List.push(formField);
						if(formField.type == "com.landray.kmss.sys.organization.model.SysOrgPerson") {
							// 发送人，只能是“单人”
							Sender_List.push(formField);
						}
					}
				}
				// 发送人
				var sender_options = [];
				for(var i in Sender_List) {
					var field = Sender_List[i];
					sender_options.push('<option value="' + field.name + '">' + field.label + '</option>');
				}
				$("#sysRemind_SenderType_xform select[name=fdSender_xform]").append(sender_options.join(""));
				// 接收人
				var receiver_options = [];
				for(var i in Receiver_List) {
					var field = Receiver_List[i];
					receiver_options.push('<option value="' + field.name + '">' + field.label + '</option>');
				}
				$("#Receiver_Xform_Tpl select[name=fdReceiver]").append(receiver_options.join(""));
				window.isInit = true;
			}
			
			// 提醒过滤切换
			window.filterChange = function(checked) {
				var area = $("#sys_remind_condition_area");
				if(checked) {
					area.show();
					$("#fdIsFilter_desc_true").show();
					$("#fdIsFilter_desc_false").hide();
					area.find("input").prop('disabled', false);
				} else {
					area.hide();
					$("#fdIsFilter_desc_true").hide();
					$("#fdIsFilter_desc_false").show();
					area.find("input").prop('disabled', true);
				}
			}
			
			// 发送人切换
			window.senderTypeChange = function(data) {
				var xform = $("#sysRemind_SenderType_xform");
				var address = $("#sysRemind_SenderType_address");
				if(data == "xform") {
					xform.show();
					address.hide();
				} else {
					xform.hide();
					address.show();
				}
			}
			
			// 增加“提醒设置”
			window._DocList_AddRow_Trigger = function(tab) {
				// 增加一行
				DocList_AddRow(tab);
				// 获取最后一行，新增的一行在最后
				var row = $("#TABLE_DocList_Trigger tr:last");
				// 设置“日期控件”
				var date_options = [];
				// 流程相关(当前模板有部署流程机制时)
				date_options.push('<option value="flowSubmitAfter" type="DateTime">${lfn:message("sys-remind:sysRemindMainTrigger.field.afterSubmit")}</option>');
				date_options.push('<option value="flowPublishAfter" type="DateTime">${lfn:message("sys-remind:sysRemindMainTrigger.field.afterPublish")}</option>');
				// 表单字段（分别获取：日期时间，日期，时间 控件）
				for(var i in DateTime_List) {
					var field = DateTime_List[i];
					date_options.push('<option value="' + field.name + '" type="DateTime">' + field.label + '</option>');
				}
				for(var i in Date_List) {
					var field = Date_List[i];
					date_options.push('<option value="' + field.name + '" type="Date">' + field.label + '</option>');
				}
				row.find("select[name$='fdField']").append(date_options.join(""));
				// 设置时间格式
				Trigger_Mode_Change(row.find("select[name$='fdMode']"));
				// 设置字段
				Trigger_Field_Change(row.find("select[name$='fdField']"));
			}
			
			// 新增一行接收人
			window._DocList_AddRow_Receiver = function(tab, type) {
				// 增加一行
				DocList_AddRow(tab);
				// 获取最后一行，新增的一行在最后
				var row = $("#TABLE_DocList_Receiver tr:last"), tpl;
				if(type == "xform") {
					tpl = $("#Receiver_Xform_Tpl").html();
				} else {
					$("#Receiver_Address_Btn").hide();
					tpl = $("#Receiver_Address_Tpl").html();
				}
				row.find("[name='Receiver_area']").html(tpl);
			}
			
			// 删除一行接收人
			window._DocList_DelRow_Receiver = function(elem) {
				var address = $(elem).parent().find("[name=Receiver_area]").find("[name='fdReceiverOrgIds']");
				// 删除行
				DocList_DeleteRow();
				if(address.length > 0) {
					$("#Receiver_Address_Btn").show();
				}
			}
			
			// 提醒设置字段切换
			window.Trigger_Field_Change = function(elem) {
				var option = $(elem).find("option:selected"), fdMode = $(elem).parent().find("[name='fdMode']"), fdTime = $(elem).parent().find("[name='fdTime_area']");
				if(option.attr("type") == "DateTime") {
					// 增加“当时”
					var exist = false;
					fdMode.find("option").each(function(i, n) {
						if($(n).val() == "time") {
							exist = true;
							return false;
						}
					});
					if(!exist) {
						fdMode.append('<option value="time">${lfn:message("sys-remind:sysRemindMainTrigger.mode.sameTime")}</option>');
					}
				} else {
					// 删除“当时”
					fdMode.find("option").each(function(i, n) {
						if($(n).val() == "time") {
							$(n).remove();
							return false;
						}
					});
					fdTime.show();
					fdTime.find("input").prop('disabled', false);
				}
				// 重设日期属性
				$(elem).parent().find("[name='fdDay_area']").hide();
				$(elem).parent().find("[name='fdHour_area']").hide();
				$(elem).parent().find("[name='fdTime_area']").hide();
				// 设置时间格式
				Trigger_Mode_Change($(elem).parent().find("select[name='fdMode']"));
			}
			
			// 提醒设置模式切换
			window.Trigger_Mode_Change = function(elem) {
				var val = $(elem).val(),
					type = $(elem).parent().find("[name$='fdField'] option:selected").attr("type"),
					fdDay = $(elem).parent().find("[name$='fdDay_area']"),
					fdHour = $(elem).parent().find("[name$='fdHour_area']"),
					fdTime = $(elem).parent().find("[name$='fdTime_area']"),
					dateTime_area = $(elem).parent().find("[name='dateTime_area']");
				// 清除校验提醒
				$(elem).parent().find(".validation-advice").remove();
				// 禁用日期时间输入框
				dateTime_area.find("input").prop('disabled', true);
				fdDay.hide();
				fdHour.hide();
				fdTime.hide();
				fdDay.find("input").val("");
				fdHour.find("input").val("");
				fdTime.find("input").val("");
				if(type == "DateTime" && (val == "before" || val == "after")) {
					// 日期时间 + “之前/之后”： XX天，XX时，XX分
					fdDay.show();
					fdHour.show();
					fdDay.find("input").prop('disabled', false);
					fdHour.find("input").prop('disabled', false);
				} else if((type == "DateTime" || type == "Date") && val == "day") {
					// 日期时间 + “当天”：时间
					// 日期 + “当天”：时间
					fdTime.show();
					fdTime.find("input").prop('disabled', false);
				} else if(type == "Date" && (val == "before" || val == "after")) {
					// 日期 + “之前/之后”：XX天，时间
					fdDay.show();
					fdTime.show();
					fdDay.find("input").prop('disabled', false);
					fdTime.find("input").prop('disabled', false);
				} else {
					// 日期时间 + “当时”：
				}
			}
			
			// 公式定义器
			window.selectByFormula = function(idField, nameField, returnType) {
				Formula_Dialog(idField,
						nameField,
						LBPM_Template_FormFieldList, 
						returnType,
						null,
						"com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction",
						"com.landray.kmss.sys.remind.model.SysRemindMain");
			}
			
			// 更新时初始化表单数据
			window.initForm = function(data) {
				var form = $("#sysRemindMainForm");
				form.find("[name=fdId]").val(data.fdId);
				form.find("[name=fdTemplateId]").val(data.fdTemplateId);
				form.find("[name=fdIsEnable]").val(data.fdIsEnable);
				form.find("[name=fdName]").val(data.fdName);
				form.find("[name=fdIsFilter]").val(data.fdIsFilter);
				if(data.fdIsEnable == false || "false" == data.fdIsEnable) {
					$("#fdIsEnable :checkbox").click();
				}
				if(data.fdIsFilter == true || "true" == data.fdIsFilter) {
					$("#fdIsFilter :checkbox").click();
				}
				form.find("[name=fdConditionName]").val(data.fdConditionName);
				form.find("[name=fdConditionId]").val(data.fdConditionId);
				form.find("[name=fdSubjectName]").val(data.fdSubjectName);
				form.find("[name=fdSubjectId]").val(data.fdSubjectId);
				form.find("[name=fdSenderType][value=" + data.fdSenderType + "]").click();
				if(data.fdSenderType == "address") {
					var fdSender = Address_GetAddressObj("fdSenderName");
					fdSender.reset(";", ORG_TYPE_PERSON, false, {"id":data.fdSenderId, "name":data.fdSenderName});
				} else {
					form.find("[name=fdSender_xform]").val(data.fdSenderId);
				}
				if(data.fdNotifyType) {
					var notifyType = form.find("[name=fdNotifyType]"), typeId = notifyType.attr("id").replace("_fdNotifyType", "");
					notifyType.val(data.fdNotifyType);
					form.find("[name='" + typeId + "']").prop("checked", false);
					var types = data.fdNotifyType.split(";");
					for(i in types) {
						form.find("[name='" + typeId + "'][value=" + types[i] + "]").prop("checked", true);
					}
				}
				if(data.fdTriggers) {
					var tab = "TABLE_DocList_Trigger";
					for(i in data.fdTriggers) {
						var trigger = data.fdTriggers[i];
						// 增加一行
						_DocList_AddRow_Trigger(tab);
						// 获取最后一行，新增的一行在最后
						var row = $("#" + tab + " tr:last");
						row.find("[name=fdTriggerId]").val(trigger.fdId);
						row.find("[name=fdRemindId]").val(trigger.fdRemindId);
						row.find("[name=fdField]").val(trigger.fdFieldId);
						Trigger_Field_Change(row.find("[name=fdField]"));
						row.find("[name=fdMode]").val(trigger.fdMode);
						Trigger_Mode_Change(row.find("[name=fdMode]"));
						row.find("[name=fdDay]").val(trigger.fdDay);
						row.find("[name=fdHour]").val(trigger.fdHour);
						row.find("[name=fdMinute]").val(trigger.fdMinute);
						row.find("[name$=fdTime]").val(trigger.fdTime);
					}
				}
				if(data.fdReceivers) {
					var tab = "TABLE_DocList_Receiver";
					for(i in data.fdReceivers) {
						var receiver = data.fdReceivers[i];
						// 增加一行
						_DocList_AddRow_Receiver(tab, receiver.fdType);
						// 获取最后一行，新增的一行在最后
						var row = $("#" + tab + " tr:last");
						row.find("[name=fdReceiverId]").val(receiver.fdId);
						row.find("[name=fdRemindId]").val(receiver.fdRemindId);
						if("xform" == receiver.fdType) {
							row.find("[name=fdReceiver]").val(receiver.fdReceiverId);
						} else {
							var ids = receiver.fdReceiverOrgIds.split(";");
							var names = receiver.fdReceiverOrgNames.split(";");
							var values = [];
							for(var len=0; len<ids.length; len++) {
								values.push({"id":ids[len], "name":names[len]});
							}
							var addr = Address_GetAddressObj("fdReceiverOrgNames");
							addr.reset(";", ORG_TYPE_ALLORG, true, values);
						}
					}
				}
			}
		});
		</script>
		<style>
			.sys_remind{
				padding: 10px;
			}
			.sys_remind .sys_remind_inline{
				padding: 5px 0;
			}
			.sys_remind .sys_remind_inline input{
				width: 100px;
			}
			.sys_remind .sys_remind_table tr{
				height: 35px;
			}
			.sys_remind .sys_remind_table .optStyle{
				margin-left: 25px;
			}
			.btn_txt {
			    margin: 0px 2px;
			    color: #2574ad;
			    border-bottom: 1px solid transparent;
			}
		</style>
	</template:replace>
	<template:replace name="content">
		<div class="sys_remind">
			<div>
				${lfn:message('sys-remind:sysRemindMain.describe')}
			</div>
			<form id="sysRemindMainForm" name="sysRemindMainForm" action="#">
			<input name="fdId" type="hidden">
			<input name="fdTemplateId" type="hidden">
			<table class="tb_normal" width="100%" style="margin-top: 10px;">
				<tr>
					<td width=15% class="td_normal_title">
						${lfn:message('sys-remind:sysRemindMain.fdName')}
					</td>
					<td width=85% colspan="3">
						<xform:text property="fdName" subject="${lfn:message('sys-remind:sysRemindMain.fdName')}" style="width:90%" showStatus="edit" required="true"></xform:text>
					</td>
				</tr>
				<tr>
					<td width=15% class="td_normal_title">
						${lfn:message('sys-remind:sysRemindMain.fdIsEnable')}
					</td>
					<td width=85% colspan="3">
						<ui:switch id="fdIsEnable" property="fdIsEnable" showType="edit" checked="true" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"/>
					</td>
				</tr>
				<tr>
					<td width=15% class="td_normal_title">
						${lfn:message('sys-remind:sysRemindMain.fdIsFilter')}
					</td>
					<td width=85% colspan="3">
						<ui:switch id="fdIsFilter" property="fdIsFilter" onValueChange="filterChange(this.checked)" showType="edit" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"/>
						<span id="fdIsFilter_desc_false">${lfn:message('sys-remind:sysRemindMain.fdIsFilter.desc.false')}</span>
						<span id="fdIsFilter_desc_true" style="display: none;">${lfn:message('sys-remind:sysRemindMain.fdIsFilter.desc.true')}</span>
					</td>
				</tr>
				<tr id="sys_remind_condition_area" style="display: none;">
					<td width=15% class="td_normal_title">
						${lfn:message('sys-remind:sysRemindMain.fdCondition')}
					</td>
					<td width=85% colspan="3">
						<xform:text property="fdConditionName" style="width:90%" showStatus="edit" subject="${lfn:message('sys-remind:sysRemindMain.fdCondition')}" htmlElementProperties="readonly='readonly' disabled='disabled'" required="true"></xform:text>
						<input name="fdConditionId" type="hidden" disabled="disabled">
						<a class="btn_txt" href="javascript:selectByFormula('fdConditionId', 'fdConditionName', 'Boolean');">${lfn:message('sys-remind:sysRemindMain.setting')}</a>
					</td>
				</tr>
				<tr>
					<td width=15% class="td_normal_title">
						${lfn:message('sys-remind:sysRemindMain.fdTriggers')}
					</td>
					<td width=85% colspan="3">
						<table width=100% class="sys_remind_table" id="TABLE_DocList_Trigger">
							<!-- 基准行 -->
							<tr KMSS_IsReferRow="1" style="display: none">
								<td>
									<input name="fdTriggerId" type="hidden">
									<input name="fdRemindId" type="hidden">
									<select class="inputsgl" name="fdField" onchange="Trigger_Field_Change(this);"></select>
									<select class="inputsgl" name="fdMode" onchange="Trigger_Mode_Change(this);" style="margin-left: 10px;">
										<option value="day">${lfn:message('sys-remind:sysRemindMainTrigger.mode.sameDay')}</option>
										<option value="before">${lfn:message('sys-remind:sysRemindMainTrigger.mode.before')}</option>
										<option value="after">${lfn:message('sys-remind:sysRemindMainTrigger.mode.after')}</option>
									</select>
									<span name="dateTime_area">
										<span name="fdDay_area" style="display: none;margin-left: 10px;">
											<xform:text property="fdDay" showStatus="edit" subject="${lfn:message('sys-remind:sysRemindMainTrigger.fdDay')}" style="width: 50px;" required="true" validators="number min(0)"></xform:text>
											${lfn:message('sys-remind:sysRemindMainTrigger.fdDay')}
										</span>
										<span name="fdHour_area" style="display: none;margin-left: 10px;">
											<xform:text property="fdHour" showStatus="edit" subject="${lfn:message('sys-remind:sysRemindMainTrigger.fdHour')}" style="width: 50px;" required="true" validators="number min(0) max(24)"></xform:text>
											${lfn:message('sys-remind:sysRemindMainTrigger.fdHour')}
											<xform:text property="fdMinute" showStatus="edit" subject="${lfn:message('sys-remind:sysRemindMainTrigger.fdMinute')}" style="width: 50px;" required="true" validators="number min(0) max(60)"></xform:text>
											${lfn:message('sys-remind:sysRemindMainTrigger.fdMinute')}
										</span>
										<span name="fdTime_area" style="display: none;margin-left: 10px;">
											<xform:datetime property="[!{index}]fdTime" showStatus="edit" subject="${lfn:message('sys-remind:sysRemindMainTrigger.fdTime')}" dateTimeType="time" required="true"></xform:datetime>
										</span>
									</span>
									<span style='cursor:pointer;' class='optStyle opt_del_style' title="${lfn:message('button.delete') }" onclick='DocList_DeleteRow();'></span>
								</td>
							</tr>
						</table>
						<div class="sys_remind_inline">
							<a id="Trigger_Btn" class="btn_txt" href="javascript:_DocList_AddRow_Trigger('TABLE_DocList_Trigger');">${lfn:message('sys-remind:sysRemindMainTrigger.btn.add')}</a>
						</div>
					</td>
				</tr>
				<tr>
					<td width=15% class="td_normal_title">
						${lfn:message('sys-remind:sysRemindMain.fdNotifyType')}
					</td>
					<td width=85% colspan="3">
						<kmss:editNotifyType property="fdNotifyType" required="true" />
					</td>
				</tr>
				<tr>
					<td width=15% class="td_normal_title">
						${lfn:message('sys-remind:sysRemindMain.fdSender')}
					</td>
					<td width=85% colspan="3">
						<div>
							<xform:radio property="fdSenderType" showStatus="edit" onValueChange="senderTypeChange" >
								<xform:simpleDataSource value="xform" textKey="sysRemindMain.sender.xform" bundle="sys-remind"></xform:simpleDataSource>
								<xform:simpleDataSource value="address" textKey="sysRemindMain.sender.address" bundle="sys-remind"></xform:simpleDataSource>
							</xform:radio>
						</div>
						<div id="sysRemind_SenderType_xform" class="sys_remind_inline" style="display: none;">
							<select class="inputsgl" name="fdSender_xform"></select>
						</div>
						<div id="sysRemind_SenderType_address" class="sys_remind_inline" style="display: none;">
							<input name="fdSender_address" type="hidden">
							<xform:address propertyId="fdSender_address" propertyName="fdSenderName" showStatus="edit" style="width: 70%" orgType="ORG_TYPE_PERSON"></xform:address>
						</div>
					</td>
				</tr>
				<tr>
					<td width=15% class="td_normal_title">
						${lfn:message('sys-remind:sysRemindMain.fdContent')}
					</td>
					<td width=85% colspan="3">
						<xform:text property="fdSubjectName" style="width:90%" subject="${lfn:message('sys-remind:sysRemindMain.fdContent')}" showStatus="edit" htmlElementProperties="readonly='readonly'" required="true"></xform:text>
						<input name="fdSubjectId" type="hidden">
						<a class="btn_txt" href="javascript:selectByFormula('fdSubjectId', 'fdSubjectName', 'String');">${lfn:message('sys-remind:sysRemindMain.setting')}</a>
					</td>
				</tr>
				<tr>
					<td width=15% class="td_normal_title">
						${lfn:message('sys-remind:sysRemindMain.fdReceivers')}
					</td>
					<td width=85% colspan="3">
						<table width=100% class="sys_remind_table" id="TABLE_DocList_Receiver">
							<!-- 基准行 -->
							<tr KMSS_IsReferRow="1" style="display: none">
								<td>
									<input name="fdReceiverId" type="hidden">
									<input name="fdRemindId" type="hidden">
									<div name="Receiver_area" style="display: inline;"></div>
									<span style='cursor:pointer' class='optStyle opt_del_style' title="${lfn:message('button.delete') }" onclick='_DocList_DelRow_Receiver(this);'></span>
								</td>
							</tr>
						</table>
						<div class="sys_remind_inline">
							<a id="Receiver_Xform_Btn" class="btn_txt" href="javascript:_DocList_AddRow_Receiver('TABLE_DocList_Receiver', 'xform');">${lfn:message('sys-remind:sysRemindMainReceiver.btn.add1')}</a>
							<a id="Receiver_Address_Btn" class="btn_txt" href="javascript:_DocList_AddRow_Receiver('TABLE_DocList_Receiver', 'address');">${lfn:message('sys-remind:sysRemindMainReceiver.btn.add2')}</a>
						</div>
					</td>
				</tr>
			</table>
			</form>
		</div>
		
		<!-- 模板 -->
		<div id="Receiver_Xform_Tpl" style="display: none;">
			<select class="inputsgl" name="fdReceiver"></select>
		</div>
		<!-- 模板 -->
		<div id="Receiver_Address_Tpl" style="display: none;">
			<xform:address mulSelect="true" propertyId="fdReceiverOrgIds" propertyName="fdReceiverOrgNames" showStatus="edit" style="width: 70%" orgType="ORG_TYPE_ALLORG"></xform:address>
		</div>
		
		<script language="JavaScript">
			validator = $KMSSValidation(document.forms['sysRemindMainForm']);
			var Trigger_Init = false, Receiver_Init = false;
			$("#TABLE_DocList_Trigger").on("detaillist-init", function() {
				Trigger_Init = true;
				if(Trigger_Init && Receiver_Init) {
					init_form();
				}
			});
			$("#TABLE_DocList_Receiver").on("detaillist-init", function() {
				Receiver_Init = true;
				if(Trigger_Init && Receiver_Init) {
					init_form();
				}
			});
			seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
				// 初始化表单
				window.init_form = function() {
					// 初始化表单字段
					init_field();
					var fdId = "${JsParam.fdId}";
					if(fdId.length > 0) {
						// 更新页面初始化
						for(i in parent.sysRemind_MainList) {
							if(fdId == parent.sysRemind_MainList[i].fdId) {
								initForm(parent.sysRemind_MainList[i]);
								break;
							}
						}
					} else {
						// 新增页面初始化
						_DocList_AddRow_Trigger("TABLE_DocList_Trigger");
						_DocList_AddRow_Receiver("TABLE_DocList_Receiver", "xform");
						$("[name='fdSenderType']:first").click();
					}
				}
				// 提交
				window.remind_submit = function() {
					if(validator.validate()) {
						var form = $("#sysRemindMainForm");
						// 封装当前表单字段
						var data = {
							"fdId": form.find("[name='fdId']").val(),
							"fdIsEnable": form.find("[name='fdIsEnable']").val(),
							"fdName": form.find("[name='fdName']").val(),
							"fdIsFilter": form.find("[name='fdIsFilter']").val(),
							"fdConditionId": form.find("[name='fdConditionId']").val(),
							"fdConditionName": form.find("[name='fdConditionName']").val(),
							"fdNotifyType": form.find("[name='fdNotifyType']").val(),
							"fdSenderType": form.find("[name='fdSenderType']:checked").val(),
							"fdSubjectId": form.find("[name='fdSubjectId']").val(),
							"fdSubjectName": form.find("[name='fdSubjectName']").val()
						};
						if("xform" == data.fdSenderType) {
							var option = form.find("[name='fdSender_xform'] option:selected");
							data["fdSenderId"] = option.val();
							data["fdSenderName"] = option.text();
						} else {
							data["fdSenderId"] = form.find("[name='fdSender_address']").val();
							data["fdSenderName"] = form.find("[name='fdSenderName']").val();
						}
						data["fdTriggers"] = [];
						$("#TABLE_DocList_Trigger tr").each(function(i, n) {
							var trigger = {
								"fdId": $(n).find("[name=fdTriggerId]").val(),
								"fdMode": $(n).find("[name=fdMode]").val(),
								"fdDay": $(n).find("[name=fdDay]").val(),
								"fdHour": $(n).find("[name=fdHour]").val(),
								"fdMinute": $(n).find("[name=fdMinute]").val(),
								"fdTime": $(n).find("[name$=fdTime]").val()
							};
							var option = $(n).find("[name='fdField'] option:selected");
							trigger["fdFieldId"] = option.val();
							trigger["fdFieldName"] = option.text();
							data["fdTriggers"].push(trigger);
						});
						data["fdReceivers"] = [];
						$("#TABLE_DocList_Receiver tr").each(function(i, n) {
							var fdReceiver = $(n).find("[name='fdReceiver'] option:selected");
							var fdReceiverOrgIds = $(n).find("[name='fdReceiverOrgIds']").val();
							var fdReceiverOrgNames = $(n).find("[name='fdReceiverOrgNames']").val();
							var receiver = {
								"fdId": $(n).find("[name=fdReceiverId]").val()
							};
							if(fdReceiver.length > 0) {
								receiver["fdType"] = "xform";
								receiver["fdReceiverId"] = fdReceiver.val();
								receiver["fdReceiverName"] = fdReceiver.text();
							} else {
								receiver["fdType"] = "address";
								receiver["fdReceiverOrgIds"] = fdReceiverOrgIds;
								receiver["fdReceiverOrgNames"] = fdReceiverOrgNames;
							}
							if(receiver["fdReceiverId"] || receiver["fdReceiverOrgIds"]){
								data["fdReceivers"].push(receiver);
							}
						});
						// 判断“提醒设置”，“发送人”和“接收人”
						if(data["fdTriggers"].length < 1) {
							dialog.alert("${lfn:message('sys-remind:sysRemindMain.error.fdTriggers.empty')}");
							return null;
						}
						if(data["fdReceivers"].length < 1) {
							dialog.alert("${lfn:message('sys-remind:sysRemindMain.error.fdReceivers.empty')}");
							return null;
						}
						if(!data["fdSenderId"]) {
							dialog.alert("${lfn:message('sys-remind:sysRemindMain.error.fdSender.empty')}");
							return null;
						}
						return data;
					}
					return null;
				}
			});
		</script>
	</template:replace>
</template:include>
