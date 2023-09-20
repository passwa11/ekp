<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss" %>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
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
<div style="margin-top: -21px;margin-bottom: 0px;">

			<div class="model_wrap">
				<div class="model_automatic_filling">
					<div class="model_automatic_title clearfloat">
						<div class="model_automatic_return" onclick="returnListPage('pc')">${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.back')}</div>
						<div class="model_automatic_save">
							<c:if test="${modelingAutomaticFillForm.method_GET=='edit' || modelingAutomaticFillForm.method_GET=='editTemplate'}">
								<div onclick="submitForm('update');">${lfn:message('sys-modeling-base:enums.behavior_type.3')}</div>
							</c:if>
							<c:if test="${modelingAutomaticFillForm.method_GET=='add'}">
								<div onclick="submitForm('save');">${lfn:message('sys-modeling-base:modeling.save')}</div>
							</c:if>
						</div>
					</div>
					<div class="model_automatic_body">
						<html:form action="/sys/modeling/base/modelingAutomaticFill.do">
						<table class="model_automatic_table" style="width: 80%;min-width:800px;table-layout: fixed;">
							<tr>
								<td class="model_automatic_tr_tag"><span></span>${lfn:message('sys-modeling-base:kmReviewDocumentLableName.baseInfo')}</td>
							</tr>
							<tr>
								<td class="model_automatic_td_title">${lfn:message('sys-modeling-base:modeling.import.name')}</td>
								<td class="model_automatic_td_content">
									<input name="fdName" type="text" class="model_automatic_inputsgl" placeholder="${lfn:message('sys-modeling-base:modeling.please.enter')}">
									<span class="span-red">*</span>
								</td>
							</tr>
							<tr>
								<td class="model_automatic_td_title">${lfn:message('sys-modeling-base:modeling.import.state')}</td>
								<td class="model_automatic_td_content">
									<ui:switch property="fdIsEnable"
											   enabledText="${lfn:message('sys-modeling-base:modeling.import.enable')}"
											   disabledText="${lfn:message('sys-modeling-base:modeling.import.deactivate')}"
											   checkVal="1"
											   unCheckVal="0"></ui:switch>
								</td>
							</tr>
							<tr>
								<td class="model_automatic_tr_tag"><span></span>${lfn:message('sys-modeling-base:modeling.process.set')}</td>
							</tr>
							<tr>
								<td class="model_automatic_td_title">${lfn:message('sys-modeling-base:modeling.informant')}</td>
								<td class="model_automatic_td_content">
									<div><xform:address textarea="true" mulSelect="true"
														propertyId="fdFillerIds"
														propertyName="fdFillerNames"
														style="width: 95%;height:120px;border: 1px solid #ddd;float:left"></xform:address>
										<span class="span-red" style="margin-left: 3px">*</span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="model_automatic_td_title">${lfn:message('sys-modeling-base:modeling.message.notification')}</td>
								<td class="model_automatic_td_content">
									<div id="_xform_fdNotifyType" _xform_type="checkbox">
										<xform:checkbox property="fdNotifyType" showStatus="edit" value="${modelingAutomaticFillForm.fdNotifyType}">
											<xform:enumsDataSource enumsType="sys_modeling_auto_notify_type"/>
										</xform:checkbox>
									</div>
								</td>
							</tr>
							<tr>
								<td class="model_automatic_td_title">${lfn:message('sys-modeling-base:modeling.filling.process')}</td>
								<td class="model_automatic_td_content" >
									<div>
										<xform:dialog propertyId="fdFlowId"
													  propertyName="fdFlowName"
													  dialogJs="selectFdFlow();"
													  style="float:left;"
													  />

									</div>
									<span class="span-red" style="margin-left: 3px;">*</span>
								</td>
							</tr>
							<tr>
								<td class="model_automatic_td_title">${lfn:message('sys-modeling-base:modeling.skip.informant')}</td>
								<td class="model_automatic_td_content">
									<div class="jump-switch">
											<ui:switch checkVal="1"
													   unCheckVal="0" property="fdIsJumpFiller"></ui:switch>
									</div>
								</td>
							</tr>
							<tr>
								<td class="model_automatic_tr_tag"><span></span>${lfn:message('sys-modeling-base:modelingAutomaticFill.fdTimeCfg')}</td>
							</tr>
							<tr>
								<td class="model_automatic_td_title">${lfn:message('sys-modeling-base:modeling.Operating.frequency')}</td>
								<td class="model_automatic_td_content">
										<%-- 运行频率。。--%>
									<div>
										<input type="hidden" name="fdCron">
										<select name="fdCronTimes" onchange="refreshInputType0();" style="color: #333">
											<option value=""><bean:message key="page.firstOption"/></option>
											<option value="once"><bean:message bundle="sys-quartz"
																			   key="sysQuartzJob.cronExpression.frequency.once"/></option>
											<option value="year"><bean:message bundle="sys-quartz"
																			   key="sysQuartzJob.cronExpression.frequency.year"/></option>
											<option value="month"><bean:message bundle="sys-quartz"
																				key="sysQuartzJob.cronExpression.frequency.month"/></option>
											<option value="week"><bean:message bundle="sys-quartz"
																			   key="sysQuartzJob.cronExpression.frequency.week"/></option>
											<option value="day"><bean:message bundle="sys-quartz"
																			  key="sysQuartzJob.cronExpression.frequency.day"/></option>
											<option value="hour"><bean:message bundle="sys-quartz"
																			   key="sysQuartzJob.cronExpression.frequency.hour"/></option>
											<option value="every"><bean:message bundle="sys-quartz"
																				key="sysQuartzJob.cronExpression.frequency.every"/></option>
										</select>
										<span class="span-red" style="margin-left: 3px">*</span>
									</div>

								</td>
							</tr>
							<tr  id="TR_FrequencySetting" style="display:none">
								<td class="model_automatic_td_title">${lfn:message('sys-modeling-base:modeling.operation.hours')}</td>
								<td class="model_automatic_td_content">
									<div>
                                        <span>
                                            <bean:message bundle="sys-quartz"
														  key="sysQuartzJob.cronExpression.field.year1"/>
                                            <input name="fdYear" size="4" type="number" value="1" class="inputSgl">
                                            <bean:message bundle="sys-quartz"
														  key="sysQuartzJob.cronExpression.field.year2"/>
                                        </span>
										<span>
                                            <bean:message bundle="sys-quartz"
														  key="sysQuartzJob.cronExpression.field.month1"/>
                                            <input name="fdMonth" size="2" type="number" value="1" class="inputSgl">
                                            <bean:message bundle="sys-quartz"
														  key="sysQuartzJob.cronExpression.field.month2"/>
                                        </span>
										<span>
                                            <bean:message bundle="sys-quartz"
														  key="sysQuartzJob.cronExpression.field.day1"/>
                                            <input name="fdDay" size="2" type="number" value="1" class="inputSgl">
                                            <bean:message bundle="sys-quartz"
														  key="sysQuartzJob.cronExpression.field.day2"/>
                                        </span>
										<span>
                                            <bean:message bundle="sys-quartz"
														  key="sysQuartzJob.cronExpression.field.week1"/>
                                            <select name="fdWeek">
                                                <c:forEach begin="0" end="6" var="i">
                                                    <option value="${i+1}">
                                                        <bean:message key="date.weekDay${i}"/>
                                                    </option>
												</c:forEach>
                                            </select>
                                            <bean:message bundle="sys-quartz"
														  key="sysQuartzJob.cronExpression.field.week2"/>
                                        </span>
										<span>
                                            <bean:message bundle="sys-quartz"
														  key="sysQuartzJob.cronExpression.field.hour1"/>
                                            <input name="fdHour" size="2"  type="number" value="0" class="inputSgl">
                                            <bean:message bundle="sys-quartz"
														  key="sysQuartzJob.cronExpression.field.hour2"/>
                                        </span>
										<span>
                                            <bean:message bundle="sys-quartz"
														  key="sysQuartzJob.cronExpression.field.minute1"/>
                                            <input name="fdMinute" size="2" value="0"  type="number" class="inputSgl">
                                            <bean:message bundle="sys-quartz"
														  key="sysQuartzJob.cronExpression.field.minute2"/>
                                        </span>
										<span>
                                            <bean:message bundle="sys-quartz"
														  key="sysQuartzJob.cronExpression.field.second1"/>
                                            <input name="fdSecond" size="2"  value="0" type="number" class="inputSgl">
                                            <bean:message bundle="sys-quartz"
														  key="sysQuartzJob.cronExpression.field.second2"/>
                                        </span>
										<span>
                                            <bean:message bundle="sys-quartz"
														  key="sysQuartzJob.cronExpression.field.every1"/>
                                            <select name="fdEvery">
                                                <option value="5">5</option>
                                                <option value="10">10</option>
                                                <option value="15">15</option>
                                                <option value="20">20</option>
                                                <option value="30">30</option>
									        </select>
                                            <bean:message bundle="sys-quartz"
														  key="sysQuartzJob.cronExpression.field.every2"/>
                                        </span>
										<span class="span-red">*</span>
									</div>

								</td>
							</tr>
							<tr>
								<td class="model_automatic_td_title" id="startTimeTitle">${lfn:message('sys-modeling-base:modeling.start.Time')}</td>
								<td class="model_automatic_td_content" id="startTimeContent">
									<div class="inputselectsgl " style="width:200px;float: left;" onclick="xform_main_data_triggleSelectdatetime(event,this,'startTime');">
										<div class="input"><input name="startTime" type="text" validate="__datetime" value="" ></div>
										<div class="inputdatetime"></div>
									</div>
									<span class="span-red" style="margin-left: 3px">*</span>
								</td>
							</tr>
							<tr>
								<td class="model_automatic_td_title" id="endTypeTitle" style="padding-bottom: 8px">${lfn:message('sys-modeling-base:modeling.end.form')}</td>
								<td class="model_automatic_td_content" id="endTypeContent" style="padding-bottom: 8px">
									<xform:radio property="endType" onValueChange="selectEndType"
												 htmlElementProperties="id='endType'" showStatus="edit">
										<xform:enumsDataSource enumsType="sys_modeling_auto_end_type"/>
									</xform:radio>
								</td>
							</tr>
							<tr  id="endType1Setting" >
								<td class="model_automatic_td_title" style="padding-top: 0px"></td>
								<td class="model_automatic_td_content" style="padding-top: 0px">
									<div class="inputselectsgl " style="width:200px;float: left;" onclick="xform_main_data_triggleSelectdatetime(event,this,'endTime');">
										<div class="input"><input name="endTime" type="text" validate="__datetime1" value="" ></div>
										<div class="inputdatetime"></div>
									</div>
									<span class="span-red" style="margin-left: 3px">*</span>
								</td>
							</tr>
							<tr  id="endType2Setting" style="display:none">
								<td class="model_automatic_td_title"  style="padding-top: 0px"></td>
								<td class="model_automatic_td_content"  style="padding-top: 0px">
									<div>
                                        <span>
                                            <input name="repeatCount" size="2" type="number" class="inputSgl" style="height: 27px">
                                            次
                                        </span>
										<span class="span-red">*</span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="model_automatic_td_title">${lfn:message('sys-modeling-base:modeling.noworking.day')}</td>
								<td class="model_automatic_td_content">
									<xform:radio property="nonWorkingDayWay" onValueChange="selectNonWorkingDayWay"
												 showStatus="edit">
										<xform:enumsDataSource enumsType="sys_modeling_auto_non_workday_way"/>
									</xform:radio>
								</td>
							</tr>
						</table>
						<html:hidden property="fdId"/>
						<input type="hidden" name="fdTimeCfg">
						<input type="hidden" name="fdModelId"
							   value="<c:out value='${modelingAutomaticFillForm.fdModelId}' />">
						<input type="hidden" name="docCreateTime"
							   value="<c:out value='${modelingAutomaticFillForm.docCreateTime}' />">
						<input type="hidden" name="docCreateTime"
							   value="<c:out value='${modelingAutomaticFillForm.docCreateTime}' />">
						<input type="hidden" name="docCreatorId"
							   value="<c:out value='${modelingAutomaticFillForm.docCreatorId}' />">
						<input type="hidden" name="autoTimeoutStrategyList">
						</html:form>
					</div>
				</div>
			</div>

</div>
		<script>
			Com_IncludeFile('calendar.js');
			var errorInteger = "<kmss:message key="errors.integer" />";
			var errorRange = "<kmss:message key="errors.range" />";
			var fieldMessages = "<bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.fields"/>".split(",");
			var re_Number = /[^\d]/gi;
			var autoFillOption={
				modelingAutomaticFillForm:{
					fdName: '${modelingAutomaticFillForm.fdName}',
					fdIsEnable: '${modelingAutomaticFillForm.fdIsEnable}',
					fdNotifyType:'${modelingAutomaticFillForm.fdNotifyType}',
					fdFlowId: '${modelingAutomaticFillForm.fdFlowId}',
					fdIsJumpFiller:'${modelingAutomaticFillForm.fdIsJumpFiller}',
					fdTimeCfg: '${modelingAutomaticFillForm.fdTimeCfg}',
					fdModelId: '${modelingAutomaticFillForm.fdModelId}'
				},
				frequencyField : ''
			};

			seajs.use(["sys/modeling/base/automaticFill/js/automaticFill","lui/jquery", "sys/ui/js/dialog","lui/topic"],
					function (automaticFill,$, dialog,topic) {

						window.openTimeoutStrategyIframe = function(cfg){
							var isRepeatReminder = 0;
							if(cfg && cfg.fdActionJson && cfg.fdActionJson.isRepeatReminder){
								isRepeatReminder = cfg.fdActionJson.isRepeatReminder;
							}
							var url = '/sys/modeling/base/automaticFill/timeout_strategy.jsp?isRepeatReminder='+isRepeatReminder;
							dialog.iframe(url, "${lfn:message('sys-modeling-base:table.modelingAutoTimeoutStrategy')}", function (data) {
								if (data) {
									var autoTimeoutStrategy = $("[name='autoTimeoutStrategyList']").val();
									if(!autoTimeoutStrategy){
										autoTimeoutStrategy = '[]';
									}

									var autoTimeoutStrategyList = JSON.parse(autoTimeoutStrategy);
									if('true' == data.isEdit){
										//编辑
										editTimeoutRecordDom(data);
										for (var i = 0; i < autoTimeoutStrategyList.length; i++) {
											if(data.strategyIndex == autoTimeoutStrategyList[i].strategyIndex){
												data.actionJson.replace(/\\/g,"");
												autoTimeoutStrategyList[i] = data;
											}
										}
									}else{
										//新建
										var strategyIndex = $('.model_automatic_strategy').length;
										data.strategyIndex = strategyIndex;
										appTimeoutRecordDom(data);
										data.actionJson.replace(/\\/g,"");
										autoTimeoutStrategyList.push(data);
									}
									$("[name='autoTimeoutStrategyList']").val(JSON.stringify(autoTimeoutStrategyList));
								}
							}, {
								width: 650,
								height: 470,
								params: {
									fdModelId: '${modelingAutomaticFillForm.fdModelId}',
									fdAutomaticFillId: '${modelingAutomaticFillForm.fdId}',
									cfg:cfg
								},
							});
						}

						//初始化时间频率
						parseCronExpression();

						function init() {
							var autoTimeoutStrategyList = '${autoTimeoutStrategyList}';
							if(!autoTimeoutStrategyList){
								autoTimeoutStrategyList = '[]';
							}
							var cfg = {
								fdName:autoFillOption.modelingAutomaticFillForm.fdName,
								fdFlowId: '${fdFlowId}',
								fdFlowName: '${fdFlowName}',
								fdIsEnable:autoFillOption.modelingAutomaticFillForm.fdIsEnable,
								fdIsJumpFiller:autoFillOption.modelingAutomaticFillForm.fdIsJumpFiller,
								fdTimeCfg:autoFillOption.modelingAutomaticFillForm.fdTimeCfg,
								autoTimeoutStrategyList:autoTimeoutStrategyList
							};
							window.automaticFill = new automaticFill.AutomaticFill(cfg);
							window.automaticFill.startup();
						}
						init();

						window.returnListPage = function(evt) {
							var url = Com_Parameter.ContextPath + 'sys/modeling/base/automaticFill/index.jsp?fdModelId=${modelingAutomaticFillForm.fdModelId}';
							var iframe = window.parent.document.getElementById("cfg_iframe");
							$(iframe).attr("src", url);
							return false;
						};
						window.selectFdFlow = function(){
							//有流程
							var url = Com_Parameter.ContextPath + "sys/modeling/main/modelingAppFlowMain.do?method=findFlows&fdAppModelId=" + '${modelingAutomaticFillForm.fdModelId}';
							$.ajax({
								url: url,
								method: 'GET',
								async: false
							}).success(function (ret) {
								if (ret && ret.data) {
									var url = "/sys/modeling/base/automaticFill/select_flow_dialog.jsp?fdAppModelId=" + '${modelingAutomaticFillForm.fdModelId}';
									dialog.iframe(url, "${lfn:message('sys-modeling-base:modelingAppView.selectFlow')}",  function (data) {
										if (data) {
											//赋值
											$("[name='fdFlowId']").val(data.flowId);
											$("[name='fdFlowName']").val(data.flowName);
										}
									}, {width: 750, height: 500})
								} else {
									dialog.alert(ret);
								}
							});
						};
						window.selectEndType = function(val, name){
							if(1 == val){
								document.getElementById("endType1Setting").style.display = "table-row";
								document.getElementById("endType2Setting").style.display = "none";
							}else if(2 == val){
								document.getElementById("endType1Setting").style.display = "none";
								document.getElementById("endType2Setting").style.display = "table-row";
							}else if(3 == val){
								document.getElementById("endType1Setting").style.display = "none";
								document.getElementById("endType2Setting").style.display = "none";
							}
						}
						window.selectActionType = function(val, name){
							if(0 == val){
								document.getElementById("actionType1Setting").style.display = "table-row";
							}else if(1 == val){
								document.getElementById("actionType1Setting").style.display = "none";
							}
						}
						window.selectNonWorkingDayWay = function(val, name){
						};
						window.submitForm = function(method){
							//校验参数
							if(false == buildCronExpression()){
								//不通过则return
								return;
							}
							var cfg = window.automaticFill.getKeyData();
							//校验参数
							var validateResult = window.AutomaticFillValidate.validate(cfg)
							if (!validateResult) {
								Com_Submit(document.modelingAutomaticFillForm, method);
							}else {
								dialog.alert(validateResult)
							}
						};

						window.delTimoutStrategy = function (strategyIndex){
							var modelAutomaticStrategy =  $("[automatic_strategy_index='"+strategyIndex+"']");
							modelAutomaticStrategy.remove();
							var autoTimeoutStrategy = $("[name='autoTimeoutStrategyList']").val();
							if(!autoTimeoutStrategy){
								autoTimeoutStrategy = '[]';
							}

							var autoTimeoutStrategyList = JSON.parse(autoTimeoutStrategy);
							for (var i = 0; i < autoTimeoutStrategyList.length; i++) {
								if(strategyIndex == autoTimeoutStrategyList[i].strategyIndex){
									autoTimeoutStrategyList[i].isDelete= true;
								}
							}
							$("[name='autoTimeoutStrategyList']").val(JSON.stringify(autoTimeoutStrategyList));
						};

						window.editTimoutStrategy = function (strategyIndex,fdId,fdName,fdTimeoutStamp,fdActionType,fdActionJson){
							var jsonStr = JSON.stringify(fdActionJson);
							var json = JSON.parse(jsonStr);
							var cfg = {
								fdId:fdId,
								fdName:fdName,
								fdTimeoutStamp:fdTimeoutStamp,
								fdActionType:fdActionType,
								fdActionJson:json,
								isEdit:true,
								strategyIndex:strategyIndex,
							}
							openTimeoutStrategyIframe(cfg);
						};

						//整理域的信息，若校验出错，抛出""
						window.formatCronExpressionField = function (value, fieldMsg, minValue, maxValue) {
							if (value == "*")
								return value;
							if (value == "")
								return minValue;
							value = parseInt(value, 10);
							if (isNaN(value)) {
								dialog.alert(errorInteger.replace(/\{0\}/, fieldMsg));
								throw "";
							}
							if (value < minValue || value > maxValue) {
								var msg = errorRange.replace(/\{0\}/, fieldMsg);
								msg = msg.replace(/\{1\}/, minValue);
								msg = msg.replace(/\{2\}/, maxValue);
								dialog.alert(msg);
								throw "";
							}
							if (value < minValue || value > maxValue)
								return value;
							return value;
						}

					});

			function refreshInputType0() {
				var frequencyField = document.getElementsByName("fdCronTimes")[0];
				var trObj = document.getElementById("TR_FrequencySetting");
				if (frequencyField.selectedIndex < 1) {
					//频率没有设置，隐藏设置栏
					trObj.style.display = "none";
					return;
				}
				trObj.style.display = "table-row";
				//调整设置项的显示
				var nowDate = new Date;
				var year = nowDate.getFullYear(); //获取当前年
				var month = nowDate.getMonth() + 1; //获取当前月
				var date = nowDate.getDate();//获取当前日
				var hour = nowDate.getHours();//获取当前时
				var minute = nowDate.getMinutes();//获取当前分
				var displayArr; //年,月,日,星期,时,分,秒,间隔
				var defaultArr; //默认值年,月,日,时,分,秒, -1代表不设置
				$('#endTypeContent').css("display","table-cell");
				$('#endTypeTitle').css("display","table-cell");
				$('#startTimeContent').css("display","table-cell");
				$('#startTimeTitle').css("display","table-cell");
				var endTypeValue = $('input[name="endType"]:checked').val()
				if(1 == endTypeValue){
					document.getElementById("endType1Setting").style.display = "table-row";
					document.getElementById("endType2Setting").style.display = "none";
				}else if(2 == endTypeValue){
					document.getElementById("endType1Setting").style.display = "none";
					document.getElementById("endType2Setting").style.display = "table-row";
				}else if(3 == endTypeValue){
					document.getElementById("endType1Setting").style.display = "none";
					document.getElementById("endType2Setting").style.display = "none";
				}
				switch (frequencyField.options[frequencyField.selectedIndex].value) {
					case "once":
						displayArr = new Array("", "", "", "none", "", "", "", "none");
						defaultArr = new Array(year, month, date, hour, minute, 0, 0);
						$('#endTypeContent').css("display","none");
						$('#endTypeTitle').css("display","none");
						$('#endType1Setting').css("display","none");
						$('#endType2Setting').css("display","none");
						$('#startTimeContent').css("display","none");
						$('#startTimeTitle').css("display","none");
						break;
					case "year":
						displayArr = new Array("none", "", "", "none", "", "", "", "none");
						defaultArr = new Array(1, 1, 1, 0, 0, 0);
						break;
					case "month":
						displayArr = new Array("none", "none", "", "none", "", "", "", "none");
						defaultArr =  new Array(1, 1, 1, 0, 0, 0);
						break;
					case "week":
						displayArr = new Array("none", "none", "none", "", "", "", "", "none");
						defaultArr =  new Array(1, 1, 1, 0, 0, 0);
						break;
					case "day":
						displayArr = new Array("none", "none", "none", "none", "", "", "", "none");
						defaultArr =  new Array(1, 1, 1, 0, 0, 0);
						break;
					case "hour":
						displayArr = new Array("none", "none", "none", "none", "none", "", "", "none");
						defaultArr =  new Array(1, 1, 1, 0, 0, 0);
						break;
					case "every":
						displayArr = new Array("none", "none", "none", "none", "none", "none", "", "");
						defaultArr =  new Array(1, 1, 1, 0, 0, 0);
				}
				var spanArr = trObj.cells[1].getElementsByTagName("SPAN");
				for (var i = 0; i < spanArr.length; i++){
					spanArr[i].style.display = displayArr[i];
				}
				if("once" != autoFillOption.frequencyField && "once" == frequencyField.options[frequencyField.selectedIndex].value){
					var inputArr = trObj.cells[1].getElementsByTagName("input");
					for (var i = 0; i < inputArr.length; i++){
						if(defaultArr[i] >-1){
							inputArr[i].value = defaultArr[i];
						}
					}
				}
			}

			//构造CronExpression，并写入fdCronExpression中，返回false则表示构造失败
			function buildCronExpression() {
				var frequencyField = document.getElementsByName("fdCronTimes")[0];
				//若没有选择频率，不处理
				if (frequencyField.selectedIndex < 1)
					return true;
				//获取所有设置项的信息
				var year = document.getElementsByName("fdYear")[0].value;
				var month = document.getElementsByName("fdMonth")[0].value;
				var day = document.getElementsByName("fdDay")[0].value;
				var week = document.getElementsByName("fdWeek")[0].options[document.getElementsByName("fdWeek")[0].selectedIndex].value;
				var hour = document.getElementsByName("fdHour")[0].value;
				var minute = document.getElementsByName("fdMinute")[0].value;
				var second = document.getElementsByName("fdSecond")[0].value;
				var every = document.getElementsByName("fdEvery")[0].options[document.getElementsByName("fdEvery")[0].selectedIndex].value;
				var frequency = frequencyField.options[frequencyField.selectedIndex].value;
				//根据频率调整参数
				switch (frequency) {
					case "every":
						minute = "0/" + every;
					case "hour":
						hour = "*";
					case "day":
						day = "*";
					case "week":
					case "month":
						month = "*";
					case "year":
						year = "";
						break;
				}
				//构造CronExpression
				try {
					var expression = formatCronExpressionField(second, fieldMessages[0], 0, 59) + " ";
					if (frequency == "every")
						expression += minute + " ";
					else
						expression += formatCronExpressionField(minute, fieldMessages[1], 0, 59) + " ";
					expression += formatCronExpressionField(hour, fieldMessages[2], 0, 23) + " ";
					if (frequency == "week") {
						expression += "? ";
						expression += formatCronExpressionField(month, fieldMessages[4], 1, 12) + " ";
						expression += week;
					} else {
						expression += formatCronExpressionField(day, fieldMessages[3], 1, 31) + " ";
						expression += formatCronExpressionField(month, fieldMessages[4], 1, 12) + " ";
						expression += "?";
					}
					if (year != "")
						expression += " " + formatCronExpressionField(year, fieldMessages[6], 1970, 2099);
				} catch (e) {
					//构造过程校验出错，返回false，e==""表示是formatCronExpressionField函数抛出的错误
					if (e == "")
						return false;
					throw e;
				}
				$("[name='fdCron']").val(expression);
				return true;
			}


			//解释CronExpression，并将值写入到相关的设置项中
			function parseCronExpression() {
				var expressionField = '${cron}';
				if( '' == expressionField){
					return;
				}
				var expression;
				expression = expressionField.split(/\s+/gi);
				var data = new Array();
				var frequency = null;
				try {
					switch (expression.length) {
						case 7:
							//判断年
							if (!checkCronExpressionField("year", expression[6], data, frequency))
								frequency = "once";
						case 6:
							//判断月
							if (!checkCronExpressionField("month", expression[4], data, frequency) && frequency == null)
								frequency = "year";
							//判断星期
							if (expression[5] != "?") {
								if (expression[3] != "?" || frequency != null)
									throw "";
								if (expression[5] != "*") {
									if (re_Number.test(expression[5]))
										throw "";
									data.week = expression[5];
									frequency = "week";
								}
							} else {
								//判断日期
								if (!checkCronExpressionField("day", expression[3], data, frequency) && frequency == null)
									frequency = "month";
							}
							//判断小时
							if (!checkCronExpressionField("hour", expression[2], data, frequency) && frequency == null) {
								if (data.week == null)
									frequency = "day";
								else
									frequency = "week";
							}
							//判断分
							if (expression[1] == "*") {
								throw "";
							}
							if (re_Number.test(expression[1])) {
								if (frequency != null)
									throw "";
								var tmpArr = expression[1].split("/");
								if (tmpArr.length != 2 || re_Number.test(tmpArr[0]) || re_Number.test(tmpArr[1]))
									throw "";
								data.every = tmpArr[1];
								frequency = "every";
							} else {
								if (frequency == null)
									frequency = "hour";
								data.minute = expression[1];
							}
							//判断秒
							if (checkCronExpressionField("second", expression[0], data, frequency)) {
								throw "";
							}
					}
				} catch (e) {
					if (e == "") {
						frequency = null;
					}else {
						throw e;
					}
				}
				if (frequency == null) {
					data = new Array();
				}else {
					data.frequency = frequency;
				}
				setCronExpressionField(data);
			}

			//将数据写入设置数据项中
			function setCronExpressionField(data) {
				document.getElementsByName("fdYear")[0].value = data.year == null ? "" : data.year;
				document.getElementsByName("fdMonth")[0].value = data.month == null ? "" : data.month;
				document.getElementsByName("fdDay")[0].value = data.day == null ? "" : data.day;
				setSelectFieldValue(document.getElementsByName("fdWeek")[0], data.week);
				document.getElementsByName("fdHour")[0].value = data.hour == null ? 0 : data.hour;
				document.getElementsByName("fdMinute")[0].value = data.minute == null ? 0 : data.minute;
				document.getElementsByName("fdSecond")[0].value = data.second == null ? 0 : data.second;
				setSelectFieldValue(document.getElementsByName("fdEvery")[0], data.every);
				autoFillOption.frequencyField = data.frequency;
				setSelectFieldValue(document.getElementsByName("fdCronTimes")[0], data.frequency);


			}

			/*
          校验CronExpression的域（年、月、时、秒），并把值写到data中。
          返回：true（该字段未限定）false（该字段已经限定）
          抛出：""，该域无法解释
          */
			function checkCronExpressionField(fieldName, fieldValue, data, frequency) {
				if (fieldValue == "*" || fieldValue == "") {
					//若前面频率已经确定，但当前字段却没有限定，不满足常用的模式，抛出无法解释
					if (frequency != null)
						throw "";
					return true;
				}
				if (re_Number.test(fieldValue))
					throw "";
				data[fieldName] = fieldValue;
				return false;
			}

			//设置下拉框的信息
			function setSelectFieldValue(fieldObj, value) {
				fieldObj.selectedIndex = 0;
				if (value == null)
					return;
				for (var i = 0; i < fieldObj.options.length; i++) {
					if (fieldObj.options[i].value == value) {
						fieldObj.selectedIndex = i;
						refreshInputType0();
						return;
					}
				}
			}

			//datetime控件触发
			function xform_main_data_triggleSelectdatetime(event,dom,name){
				var input;
				if('startTime' == name){
					input =  $("[name='startTime']");
				}else if('endTime' == name){
					input =  $("[name='endTime']");
				}
				selectDateTime(event,input);
			}

		</script>