<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss" %>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<script type="text/javascript">
	Com_IncludeFile("select.js");
	Com_IncludeFile("dialog.js");
	Com_IncludeFile("form.js");
	Com_IncludeFile("formula.js");
	Com_IncludeFile("doclist.js");
	Com_IncludeFile("view.css", "${LUI_ContextPath}/sys/modeling/base/resources/css/", "css", true);
	Com_IncludeFile("dialog.css", "${LUI_ContextPath}/sys/modeling/base/resources/css/", "css", true);

</script>
<link type="text/css" rel="stylesheet"
	  href="${KMSS_Parameter_ContextPath}sys/modeling/base/automaticFill/css/common.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
	  href="${KMSS_Parameter_ContextPath}sys/modeling/base/automaticFill/css/automatic.css?s_cache=${LUI_Cache}"/>
<div class="new_strategy_mask">
	<div class="new_strategy_pop">
		<div class="new_strategy_body">
			<table class="new_strategy_table">
				<tr>
					<td class="model_automatic_strategy_title">${lfn:message('sys-modeling-base:modeling.import.name')}</td>
					<td class="model_automatic_strategy_content">
						<div class="timeout_strategy_name_item">
							<input id="timeoutStrategyName" type="text" class="model_automatic_strategy_inputsgl" placeholder="${lfn:message('sys-modeling-base:modeling.please.enter')}">
							<span class="span-red">*</span>
							<span class="error"></span>
						</div>
					</td>
				</tr>
				<tr>
					<td class="model_automatic_strategy_title">${lfn:message('sys-modeling-base:automaticfill.execution.actions')}</td>
					<td class="model_automatic_strategy_content">
						<xform:radio property="actionType" onValueChange="selectActionType"
									 htmlElementProperties="id='actionType'" showStatus="edit">
							<xform:enumsDataSource enumsType="sys_modeling_auto_action_type"/>
						</xform:radio>
					</td>
				</tr>
				<tr>
					<td class="model_automatic_strategy_title" id="timeout">${lfn:message('sys-modeling-base:automaticfill.stay.time')}</td>
					<td class="model_automatic_strategy_content">
						<div class="model_automatic_running_time">
							<div class="running_time_item">
								<input id="timeoutDay" type="number" value="0">${lfn:message('sys-modeling-base:automaticfill.day')}
								<div class="ctrl_time">
									<p class="ctrl_add" onclick="addTime(this)"></p>
									<p class="ctrl_cut" onclick="cutTime(this)"></p>
								</div>
								<span class="error">${lfn:message('sys-modeling-base:automaticfill.configure.error3')}</span>
							</div>
							<div class="running_time_item">
								<input id="timeoutHour" type="number" value="0">${lfn:message('sys-modeling-base:automaticfill.hour')}
								<div class="ctrl_time">
									<p class="ctrl_add" onclick="addTime(this)"></p>
									<p class="ctrl_cut" onclick="cutTime(this)"></p>
								</div>
								<span class="error">${lfn:message('sys-modeling-base:automaticfill.configure.error3')}</span>
							</div>
							<div class="running_time_item">
								<input id="timeoutMinute" type="number" value="0">${lfn:message('sys-modeling-base:automaticfill.minute')}
								<div class="ctrl_time">
									<p class="ctrl_add" onclick="addTime(this)"></p>
									<p class="ctrl_cut" onclick="cutTime(this)"></p>
								</div>
								<span class="error">${lfn:message('sys-modeling-base:automaticfill.configure.error3')}</span>
							</div>
							<span class="span-red" style="margin-left: -15px;">*</span>
						</div>
					</td>
				</tr>
				<tr id="actionType1Setting">
					<td colspan="2">
						<div class="strategy_reminder_notice">
							<table class="new_strategy_table">
								<tr>
									<td class="model_automatic_strategy_title">${lfn:message('sys-modeling-base:automaticfill.reminder.notice')}</td>
									<td class="model_automatic_strategy_content">
										<div class="reminder_notice_item">
											<textarea id="reminderNotice"
													  style="width: 400px;height: 80px;color:#333;padding: 8px"
													  placeholder="${lfn:message('sys-modeling-base:automaticfill.timeout.tips')}" ></textarea>
											<span class="span-red">*</span>
											<span class="error">${lfn:message('sys-modeling-base:automaticfill.remindernotification.cannot.null')}</span>
										</div>

									</td>
								</tr>
								<tr>
									<td class="model_automatic_strategy_title">${lfn:message('sys-modeling-base:automaticfill.repeat.reminder')}</td>
									<td class="model_automatic_strategy_content">
										<ui:switch property="isRepeatReminder"
												   checked = "${param.isRepeatReminder}"
												   onValueChange="isRepeatReminderChange()"
												   checkVal="1"
												   unCheckVal="0"></ui:switch>
									</td>
								</tr>
								<tr class="repeatCycle">
									<td class="model_automatic_strategy_title">${lfn:message('sys-modeling-base:automaticfill.repeat.period')}</td>
									<td class="model_automatic_strategy_content">
										<div class="model_automatic_running_time">
											<div class="repeat_time running_time_item">
												<input id="repeatCycleCount" type="number" value="0">
												<div class="ctrl_time">
													<p class="ctrl_add" onclick="addTime(this)">
													</p>
													<p class="ctrl_cut" onclick="cutTime(this)">
													</p>
												</div>
												<span class="error">${lfn:message('sys-modeling-base:automaticfill.configure.error6')} </span>
											</div>
											<div class="running_time_item">
												<div>
												<input id="repeatCycleType" class="cycle_input" type="cycle" value="${lfn:message('sys-modeling-base:automaticfill.day')}" readonly>
												</div>
												<div class="ctrl_time">
													<p class="cycle_select"  style="margin-top: 6px" >
													</p>
												</div>
												<ul class="cycle_option_list">
													<li class="cycle_option_list_item">${lfn:message('sys-modeling-base:automaticfill.hour')}</li>
													<li class="cycle_option_list_item">${lfn:message('sys-modeling-base:automaticfill.day')}</li>
													<li class="cycle_option_list_item">${lfn:message('sys-modeling-base:automaticfill.week')}</li>
													<li class="cycle_option_list_item">${lfn:message('sys-modeling-base:automaticfill.month')}</li>
												</ul>
											</div>
<%--																<span class="span-red" style="margin-left: -15px;">*</span>--%>
										</div>
									</td>
								</tr>
								<tr  class="repeatCount">
									<td class="model_automatic_strategy_title">${lfn:message('sys-modeling-base:automaticfill.repeat.times')}</td>
									<td class="model_automatic_strategy_content">
										<div class="model_automatic_running_time">
											<div class="running_time_item">
												<input id="timeoutRepeatCount" type="number" value="0">${lfn:message('sys-modeling-base:automaticfill.number')}

												<div class="ctrl_time">
													<p class="ctrl_add" onclick="addTime(this)">
													</p>
													<p class="ctrl_cut" onclick="cutTime(this)">
													</p>
												</div>
												<span class="error">${lfn:message('sys-modeling-base:automaticfill.configure.error6')}</span>
											</div>
											<span class="span-red" style="margin-left: -15px;">*</span>
										</div>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<div class="new_strategy_footer">
					<div class="btn_close">${lfn:message('sys-modeling-base:modeling.Cancel')}</div>
					<div class="btn_sure">${lfn:message('sys-modeling-base:modeling.button.ok')}</div>
				</div>
			</table>
			<input type="hidden" id="fdId">
			<input type="hidden" id="isEdit">
			<input type="hidden" id="strategyIndex">
		</div>
	</div>
</div>
		<script>
			window.colorChooserHintInfo = {
				cancelText: '${lfn:message('sys-modeling-base:modeling.Cancel')}',
				chooseText: '${lfn:message('sys-modeling-base:modeling.button.ok')}'
			};
			seajs.use(["sys/modeling/base/automaticFill/js/timeoutStrategy","lui/jquery", "sys/ui/js/dialog","lui/topic"],
					function (timeoutStrategy,$, dialog,topic) {
						$('.lui-mulit-zh-cn-html').css('overflow','hidden');
						//监听数据传入
						var _param;
						var intervalEndCount = 10;
						var interval = setInterval(__interval, "50");

						function __interval() {
							if (intervalEndCount == 0) {
								console.error("数据解析超时。。。");
								clearInterval(interval);
							}
							intervalEndCount--;
							if (!window['$dialog']) {
								return;
							}
							_param = $dialog.___params;
							init(_param);
							clearInterval(interval);
						}

						function init(param) {
							window.timeoutStrategy = new timeoutStrategy.TimeoutStrategy(param.cfg);
							window.timeoutStrategy.startup();
						}

						window.selectActionType = function(val, name){
							var dialogHeight = $(".lui_dialog_content",window.parent.document).height();
							if(0 == val){
								document.getElementById("actionType1Setting").style.display = "table-row";
								$("#timeout").text("${lfn:message('sys-modeling-base:automaticfill.stay.time')}");
								//自适应弹窗高度
								//判断重复提醒是否打开
								if("0"==$("[name='isRepeatReminder']").val()){
									//自适应弹窗高度
									$(".lui_dialog_content",window.parent.document).css("height",420);
								}else{
									//自适应弹窗高度
									$(".lui_dialog_content",window.parent.document).css("height",520);
								}
							}else if(1 == val){
								document.getElementById("actionType1Setting").style.display = "none";
								$("#timeout").text("${lfn:message('sys-modeling-base:modeling.overtime.time')}");
								//自适应弹窗高度
								$(".lui_dialog_content",window.parent.document).css("height",230);
							}
						}

						window.postTimeoutStrategy = function() {
							var fdId = $("[id='fdId']").val();
							var isEdit = $("[id='isEdit']").val();
							var strategyIndex = $("[id='strategyIndex']").val();
							var timeoutStrategyName = $("[id='timeoutStrategyName']").val();
							var timeoutDay = $("[id='timeoutDay']").val();
							var timeoutHour = $("[id='timeoutHour']").val();
							var timeoutMinute = $("[id='timeoutMinute']").val();
							var actionType = $("input[name='actionType']:checked").val();

							var reminderNotice = $("[id='reminderNotice']").val();
							var isRepeatReminder =  $("[name='isRepeatReminder']").val();
							var repeatCycleCount = $("[id='repeatCycleCount']").val();
							var repeatCycleType = $("[id='repeatCycleType']").val();
							var timeoutRepeatCount = $("[id='timeoutRepeatCount']").val();
							var actionJson ={
								reminderNotice: reminderNotice,
								isRepeatReminder:isRepeatReminder,
								repeatCycleCount: repeatCycleCount,
								repeatCycleType: repeatCycleType,
								timeoutRepeatCount: timeoutRepeatCount
							}
							var cfg = {
								fdId:fdId,
								timeoutStrategyName: timeoutStrategyName,
								timeoutDay: timeoutDay,
								timeoutHour: timeoutHour,
								timeoutMinute: timeoutMinute,
								actionType: actionType,
								actionJson: actionJson,
								isEdit:isEdit,
								strategyIndex:strategyIndex
							}
							//校验超时策略参数
							var validateResult = window.AutoTimeoutStrategyValidate.validate(cfg)

							if (!validateResult) {
								cfg.actionJson = JSON.stringify(actionJson);
								$dialog.hide(cfg);
							}else {
								// dialog.alert(validateResult)
								return;
							}

						};

						//取消按钮点击事件
						$('.btn_close').on('click',  function () {
							$dialog.hide(null);
						});

						//确定按钮点击事件
						$('.btn_sure').on('click',  function () {
							postTimeoutStrategy()
						});

					});

		</script>
<%--//修复#138646，原因是受到#134737的影响，采用方案屏蔽引入
<%@ include file="/resource/jsp/edit_down.jsp"%>--%>