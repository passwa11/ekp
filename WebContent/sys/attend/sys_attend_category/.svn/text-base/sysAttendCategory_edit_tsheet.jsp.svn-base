<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.dialog">
	<template:replace name="title">
		${ lfn:message('sys-attend:sysAttendCategory.timeSheet.setting') }
	</template:replace>
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/sys/attend/resource/css/attend.css" />
		<script>
			Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
		</script>
	</template:replace>
	<template:replace name="content">
		<html:form action="/sys/attend/sys_attend_category_tsheet/sysAttendCategoryTSheet.do">
		<div class="lui-singin-timesheet">
			<div class="lui-singin-timesheet-panel">
				<div class="lui-singin-timesheet-panel-body">
					<%-- 工作日 --%>
					<div class="lui-singin-timesheet-table">
			    		<div class="caption" style="padding-top: 16px">${ lfn:message('sys-attend:sysAttendMain.fdDateType.workday') }</div>
			    		<div class="content">
			    			<table class="tb_simple" width="100%">
			    				<tr>
			    					<td id="weekTd">
			    						<xform:checkbox showStatus="edit" property="fdWeek" subject="${ lfn:message('sys-attend:sysAttendMain.fdDateType.workday') }" isArrayValue="false" required="true" value="${JsParam.fdWeek }" validators="weekRepeat">
											<xform:enumsDataSource enumsType="sysAttendCategory_fdWeek" />
										</xform:checkbox>
			    					</td>
			    				</tr>
			    			</table>
			    		</div>
			    	</div>
			    	<%-- 工作时间 --%>
			    	<div class="lui-singin-timesheet-table">
			    		<div class="caption">${ lfn:message('sys-attend:sysAttendCategory.worktime') }</div>
			    		<div class="content">
			    			<div class="lui-singin-timesheet-tab">
			    				<ul class="lui-singin-timesheet-tab-heading">
			    					<html:hidden property="fdWork" value="${JsParam.fdWork }" />
			    					<li id='onceType' onclick="changeWorkType('1')"><a href="javascript:void(0);">${ lfn:message('sys-attend:sysAttendCategory.fdWork.once') }</a></li>
			    					<li id='twiceType' onclick="changeWorkType('2')"><a href="javascript:void(0);">${ lfn:message('sys-attend:sysAttendCategory.fdWork.twice') }</a></li>
			    				</ul>
			    				<div class="lui-singin-timesheet-tab-body" style="padding-bottom: 0">
			    					<table class="tb_simple" width="100%">
			    						<%-- 第一班次  --%>
			    						<tr id="onceWorkTime">
			    							<%-- 上班 --%>
											<td style="width: 40px" class="td_normal_title td_tab_title">
												${ lfn:message('sys-attend:sysAttendMain.fdWorkType.onwork') }
											</td>
											<td style="width: 100px">
												<html:hidden property="fdWorkTime[0].fdId" value="${JsParam.fdWorkTimeId1 }"/>
												<html:hidden property="fdWorkTime[0].fdIsAvailable" value="${JsParam.fdIsAvailable1 }"/>
												<xform:datetime minuteStep="1" showStatus="edit" property="fdWorkTime[0].fdStartTime" subject="${ lfn:message('sys-attend:sysAttendCategoryWorktime.fdStartTime') }" validators="afterOpen" required="true" dateTimeType="time" style="width:80%;" value="${JsParam.fdOnTime1 }" onValueChange="calTotalTime"></xform:datetime>
											</td>
											<%-- 下班 --%>
											<td style="width: 60px" class="td_normal_title td_tab_title">
												&nbsp;—&nbsp;&nbsp;
												${ lfn:message('sys-attend:sysAttendMain.fdWorkType.offwork') }
											</td>
											<td style="width: 160px">
												<div id='overTimeTypeOnce'>
													<xform:select property="fdWorkTime[0].fdOverTimeType"  showStatus="edit" showPleaseSelect="false" title="" onValueChange="changeFocus('fdWorkTime[0].fdEndTime');" style="width:35%;height:30px;margin-right:7px;" value="${JsParam.fdOverTimeType1 }">
														<xform:simpleDataSource value='1'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }</xform:simpleDataSource>
														<xform:simpleDataSource value='2'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }</xform:simpleDataSource>
													</xform:select>
												</div>
												<div id='overTimeTypeTwice'>
													<xform:select property="fdWorkTime[0].fdOverTimeType" showStatus="edit" showPleaseSelect="false" title="" onValueChange="changeFocus('fdWorkTime[0].fdEndTime');" style="width:35%;height:30px;margin-right:7px;" value="${JsParam.fdOverTimeType1 }">
														<xform:simpleDataSource value='1'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }</xform:simpleDataSource>
													</xform:select>
												</div>
												<xform:datetime minuteStep="1" showStatus="edit" property="fdWorkTime[0].fdEndTime" subject="${ lfn:message('sys-attend:sysAttendCategoryWorktime.fdEndTime') }" validators="afterFirstStart afterAcrossFirstStart" required="true" dateTimeType="time" style="width:50%;" value="${JsParam.fdOffTime1 }" onValueChange="calTotalTime"></xform:datetime>
											</td>
											<%-- 最早打卡 --%>
											<td style="width: 70px" class="td_normal_title td_tab_title">
												${ lfn:message('sys-attend:sysAttendCategory.earliest.startTime') }：
											</td>
											<td style="width: 100px">
												<xform:datetime minuteStep="1" showStatus="edit" property="fdStartTime1" dateTimeType="time" required="true" style="width:80%" value="${JsParam.fdStartTime1 }" validators="beforeFirstStart afterAcrossFirstEnd"></xform:datetime>
											</td>
											<%-- 最晚打卡 --%>
											<td style="width: 70px" class="td_normal_title td_tab_title">
												${ lfn:message('sys-attend:sysAttendCategory.latest.endTime') }：
											</td>
											<td style="">
												<div id='endTimeOnce'>
													<xform:select showStatus="edit" property="fdEndDay" showPleaseSelect="false" title="${ lfn:message('sys-attend:sysAttendCategory.fdEndDay') }" onValueChange="changeFocus('fdEndTime');" style="width:35%;height:30px;margin-right:7px;" value="${JsParam.fdEndDay }">
														<xform:simpleDataSource value='1'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }</xform:simpleDataSource>
														<xform:simpleDataSource value='2'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }</xform:simpleDataSource>
													</xform:select>
													<xform:datetime minuteStep="1" showStatus="edit" property="fdEndTime" dateTimeType="time" validators="afterEnd acrossDay" required="true" style="width: 48%" value="${JsParam.fdEndTime2 }"></xform:datetime>
												</div>
												
												<div id='endTimeTwice'>
													<xform:datetime minuteStep="1" showStatus="edit" property="fdEndTime1" dateTimeType="time" validators="firstEndTime" style="width:90%" value="${JsParam.fdEndTime1 }"></xform:datetime>
												</div>
											</td>
			    						</tr>
			    						<%-- 第二班次  --%>
			    						<tr id="twiceWorkTime">
											<%-- 上班 --%>
											<td style="width: 40px" class="td_normal_title td_tab_title">
												${ lfn:message('sys-attend:sysAttendMain.fdWorkType.onwork') }
											</td>
											<td style="width: 100px">
												<html:hidden property="fdWorkTime[1].fdId" value="${JsParam.fdWorkTimeId2 }"/>
												<html:hidden property="fdWorkTime[1].fdIsAvailable" value="${JsParam.fdIsAvailable2 }"/>
												<xform:datetime minuteStep="1" showStatus="edit" property="fdWorkTime[1].fdStartTime" subject="${ lfn:message('sys-attend:sysAttendCategoryWorktime.fdStartTime') }" validators="afterFirstEnd" required="true" dateTimeType="time" style="width:80%;" onValueChange="calTotalTime" value="${JsParam.fdOnTime2 }"></xform:datetime>
											</td>
											<%-- 下班 --%>
											<td style="width: 60px" class="td_normal_title td_tab_title">
												&nbsp;—&nbsp;&nbsp;
												${ lfn:message('sys-attend:sysAttendMain.fdWorkType.offwork') }
											</td>
											<td style="width: 160px">
												<xform:select property="fdWorkTime[1].fdOverTimeType" showStatus="edit" showPleaseSelect="false" title="" onValueChange="changeFocus('fdWorkTime[1].fdEndTime');" value="${JsParam.fdOverTimeType2 }" style="width:30%;height:33px;margin-right:7px;">
														<xform:simpleDataSource value='1'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }</xform:simpleDataSource>
														<xform:simpleDataSource value='2'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }</xform:simpleDataSource>
													</xform:select>
												<xform:datetime minuteStep="1" showStatus="edit" property="fdWorkTime[1].fdEndTime" subject="${ lfn:message('sys-attend:sysAttendCategoryWorktime.fdEndTime') }" validators="afterSecondStart afterAcrossSecondStart"  required="true" dateTimeType="time" style="width:50%;" onValueChange="calTotalTime" value="${JsParam.fdOffTime2 }"></xform:datetime>
											</td>
											<%-- 最早打卡 --%>
											<td style="width: 70px" class="td_normal_title td_tab_title">
												${ lfn:message('sys-attend:sysAttendCategory.earliest.startTime') }：
											</td>
											<td style="width: 100px">
												<xform:datetime minuteStep="1" showStatus="edit" property="fdStartTime2" dateTimeType="time" validators="secondStartTime afterAcrossSecondEnd" style="width:80%" value="${JsParam.fdStartTime2 }"></xform:datetime>
											</td>
											<%-- 最晚打卡 --%>
											<td style="width: 70px" class="td_normal_title td_tab_title">
												${ lfn:message('sys-attend:sysAttendCategory.latest.endTime') }：
											</td>
											<td style="" id="endTimeTwice2">
												<xform:select showStatus="edit" property="fdEndDay" showPleaseSelect="false" title="${ lfn:message('sys-attend:sysAttendCategory.fdEndDay') }" onValueChange="changeFocus('fdEndTime2');"  style="width:35%;height:30px;margin-right:7px;" value="${JsParam.fdEndDay }">
													<xform:simpleDataSource value='1'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }</xform:simpleDataSource>
													<xform:simpleDataSource value='2'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }</xform:simpleDataSource>
												</xform:select>
												<xform:datetime minuteStep="1" showStatus="edit" property="fdEndTime2" dateTimeType="time" validators="afterEnd acrossDay afterArcossEnd" required="true" style="width: 48%" value="${JsParam.fdEndTime2 }"></xform:datetime>
											</td>
			    						</tr>
			    					</table>
			    				</div>
			    			</div>
			    		</div>
			    	</div>
			    	<%-- 午休时间 --%>
			    	<div class="lui-singin-timesheet-table" id="restTimeTB">
			    		<div class="caption" style="padding-top: 18px;">
			    			${ lfn:message('sys-attend:sysAttendCategory.noon.restTime') }
			    		</div>
			    		<div class="content">
			    			<table class="tb_simple" width="100%">
			    				<tr>
			    					<td>
										<xform:select property="fdRestStartType"
													  showPleaseSelect="false"
													  title="${ lfn:message('sys-attend:sysAttendCategory.fdRestStartType') }"
													  style="width:80px;height:32px;margin-right:7px;"
													  showStatus="edit"
													  value="${JsParam.fdRestStartType }"
										>
											<xform:simpleDataSource value='1'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }</xform:simpleDataSource>
											<xform:simpleDataSource value='2'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }</xform:simpleDataSource>
										</xform:select>
			    						<xform:datetime minuteStep="1" showStatus="edit" property="fdRestStartTime" dateTimeType="time" validators="restTimeNull restTimeRange restTimeStart" style="width:100px" onValueChange="calTotalTime" value="${JsParam.fdRestStartTime }"></xform:datetime>
			    						<span style="float: left;margin:0 20px;">—</span>
										<xform:select property="fdRestEndType"
													  showPleaseSelect="false"
													  title="${ lfn:message('sys-attend:sysAttendCategory.fdRestEndType') }"
													  style="width:80px;height:32px;margin-right:7px;"
													  showStatus="edit"
													  value="${JsParam.fdRestEndType }"
										>
											<xform:simpleDataSource value='1'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }</xform:simpleDataSource>
											<xform:simpleDataSource value='2'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }</xform:simpleDataSource>
										</xform:select>
			    						<xform:datetime minuteStep="1" showStatus="edit" property="fdRestEndTime" dateTimeType="time" validators="restTimeNull restTimeRange restTimeEnd" style="width:100px" onValueChange="calTotalTime" value="${JsParam.fdRestEndTime }"></xform:datetime>
			    					</td>
			    				</tr>
			    			</table>
		    			</div>
			    	</div>
			    	<%-- 总工时 --%>
			    	<div class="lui-singin-timesheet-table">
			    		<div class="caption" style="padding-top: 18px;">
			    			${ lfn:message('sys-attend:sysAttendStat.fdTotalTime') }
			    		</div>
			    		<div class="content">
			    			<html:hidden property="fdTotalTime" value="${JsParam.fdTotalTime }" />
			    			<div id='totalTimeDiv' style="padding-top: 13px;margin-left: 10px;">
			    				${JsParam.fdTotalTime }${ lfn:message('sys-attend:sysAttendCategory.hour') }
			    			</div>
							<%--统计时按多少天算--%>
							<xform:select showStatus="edit" property="fdTotalDay" showPleaseSelect="false" title="${ lfn:message('sys-attend:sysAttendCategory.total.day.one') }"  style="width:35%;height:30px;margin-right:7px;" value="${JsParam.fdTotalDay }">
								<xform:simpleDataSource value='1'>${ lfn:message('sys-attend:sysAttendCategory.total.day.one') }</xform:simpleDataSource>
								<xform:simpleDataSource value='0.5'>${ lfn:message('sys-attend:sysAttendCategory.total.day.half') }</xform:simpleDataSource>
							</xform:select>
			    		</div>
			    	</div>
			    	<div class="lui-singin-timesheet-table" style="text-align: center">
			    		<ui:button text="${ lfn:message('button.ok') }" styleClass="lui_widget_btn lui_toolbar_btn_def" style="width: 120px;margin-right: 50px;" order="1"
			    			onclick="doSubmit();"></ui:button>
			    		<ui:button text="${ lfn:message('button.cancel') }"  styleClass="lui_widget_btn lui_toolbar_btn_gray" style="width: 120px" order="2"
			    			onclick="doCancel();"></ui:button>
			    	</div>
				</div>
			</div>
		</div>
		</html:form>
		<script>
			var vld = $KMSSValidation(document.forms['sysAttendCategoryTSheetForm']);
			
			window.doSubmit = function() {
				if(vld && vld.validate()) {
					var rtnObj = $(document.forms['sysAttendCategoryTSheetForm']).serializeArray();
					$dialog.hide(rtnObj);
				}
			};
			
			window.doCancel = function() {
				$dialog.hide(null);
			};
			
			LUI.ready(function(){
				// 一班制还是两班制
				initWorkType('${JsParam.fdWork }');
				// 计算总工时
				<c:if test="${empty JsParam.fdTotalTime}">
					calTotalTime();
				</c:if>
			});
			
			// 初始化班次类型
			window.initWorkType = function initWorkType(v) {
				if(!v) 
					return;
				var workTypeField = $('input[name="fdWork"]:hidden');
				if(v =='1'){
					workTypeField.val('1');
					hideAndRemoveVld($('#twiceWorkTime'));
					$('[name="fdWorkTime[0].fdIsAvailable"]').val('true');
					$('[name="fdWorkTime[1].fdIsAvailable"]').val('false');
					showAndAbled('endTimeOnce');
					showAndAbled('overTimeTypeOnce');
					hideAndDisabled('endTimeTwice');
					hideAndDisabled('endTimeTwice2');
					hideAndDisabled('overTimeTypeTwice');
					showAndAbled('restTimeTB');
					$('#onceType').addClass('active');
					$('#twiceType').removeClass('active');
				} else if(v == '2'){
					workTypeField.val('2');
					showAndResetVld($('#twiceWorkTime'));
					$('[name="fdWorkTime[0].fdIsAvailable"]').val('true');
					$('[name="fdWorkTime[1].fdIsAvailable"]').val('true');
					hideAndDisabled('endTimeOnce');
					hideAndDisabled('overTimeTypeOnce');
					showAndAbled('endTimeTwice');
					showAndAbled('endTimeTwice2');
					showAndAbled('overTimeTypeTwice');
					hideAndDisabled('restTimeTB');
					$('#twiceType').addClass('active');
					$('#onceType').removeClass('active');
				}
			};
			
			// 班次类型，一班制还是两班制
			window.changeWorkType = function changeWorkType(v) {
				if(!v) 
					return;
				var workTypeField = $('input[name="fdWork"]:hidden');
				if(v =='1'){
					workTypeField.val('1');
					hideAndRemoveVld($('#twiceWorkTime'));
					$('[name="fdWorkTime[0].fdIsAvailable"]').val('true');
					$('[name="fdWorkTime[1].fdIsAvailable"]').val('false');
					showAndAbled('endTimeOnce');
					showAndAbled('overTimeTypeOnce');
					hideAndDisabled('endTimeTwice');
					hideAndDisabled('endTimeTwice2');
					hideAndDisabled('overTimeTypeTwice');
					showAndAbled('restTimeTB');
					$('#onceType').addClass('active');
					$('#twiceType').removeClass('active');
					$('[name="fdWorkTime[0].fdStartTime"]').val('09:00');
					$('[name="fdWorkTime[0].fdEndTime"]').val('18:00');
					$('[name="fdWorkTime[0].fdOverTimeType"]').val('1');
					setTimeout(function() {
						calTotalTime();
					}, 0);
				} else if(v=='2'){
					workTypeField.val('2');
					showAndResetVld($('#twiceWorkTime'));
					$('[name="fdWorkTime[0].fdIsAvailable"]').val('true');
					$('[name="fdWorkTime[1].fdIsAvailable"]').val('true');
					hideAndDisabled('endTimeOnce');
					hideAndDisabled('overTimeTypeOnce');
					showAndAbled('endTimeTwice');
					showAndAbled('endTimeTwice2');
					showAndAbled('overTimeTypeTwice');
					hideAndDisabled('restTimeTB');
					$('#twiceType').addClass('active');
					$('#onceType').removeClass('active');
					$('[name="fdWorkTime[0].fdStartTime"]').val('09:00');
					$('[name="fdWorkTime[0].fdEndTime"]').val('12:00');
					$('[name="fdWorkTime[0].fdOverTimeType"]').val('1');
					$('[name="fdWorkTime[1].fdStartTime"]').val('14:00');
					$('[name="fdWorkTime[1].fdEndTime"]').val('18:00');
					$('[name="fdWorkTime[1].fdOverTimeType"]').val('1');
					$('[name="fdEndTime1"]').val('14:00');
					$('[name="fdStartTime2"]').val('12:00');
					setTimeout(function() {
						calTotalTime();
					}, 0);
				}
			};
			
			// 计算总工时
			window.calTotalTime = function () {
				var workType = $('input[name="fdWork"]:hidden').val();
				var on1 = $('[name="fdWorkTime[0].fdStartTime"]:enabled').val();
				var off1 = $('[name="fdWorkTime[0].fdEndTime"]:enabled').val();
				var type1 = $('[name="fdWorkTime[0].fdOverTimeType"]:enabled:visible').val();
				var on2 = $('[name="fdWorkTime[1].fdStartTime"]:enabled').val();
				var off2 = $('[name="fdWorkTime[1].fdEndTime"]:enabled').val();
				var type2 = $('[name="fdWorkTime[1].fdOverTimeType"]:enabled:visible').val();
				var restStart = $('[name="fdRestStartTime"]:enabled').val();
				var restEnd = $('[name="fdRestEndTime"]:enabled').val();
				var totalTimeDiv = $('#totalTimeDiv');
				var fdTotalTime = $('input[name="fdTotalTime"]:hidden');

				var totalTime = 0;
				if(workType == '1' && on1 && off1) {//一班制
					/* var onMin1 = parseInt(on1.split(':')[0]) * 60 + parseInt(on1.split(':')[1]);
					var offMin1 = parseInt(off1.split(':')[0]) * 60 + parseInt(off1.split(':')[1]); */
					var dateStart1=getDateTime(on1,1);
					var dateEnd1=getDateTime(off1,type1);
					var onMin1 = dateStart1.getTime();
					var offMin1 = dateEnd1.getTime();
					if(offMin1 > onMin1) {
						totalTime =(offMin1 - onMin1)/(60*1000);
						if(restStart && restEnd) {
							//午休时间
							var fdRestStartType = $('select[name="fdRestStartType"]:enabled').val();
							var fdRestEndType = $('select[name="fdRestEndType"]:enabled').val();
							var restStartDate = getDateTime(restStart,fdRestStartType);
							var restEndDate = getDateTime(restEnd,fdRestEndType);
							if(restEndDate.getTime() > restStartDate.getTime()) {
								var restLongTime = restEndDate.getTime() - restStartDate.getTime();
								var restMins = restLongTime / 1000 / 60
								totalTime = totalTime - restMins;
							}
						}
					}
					totalTime = parseFloat((totalTime / 60).toFixed(1));
				} else if (workType == '2' && on1 && off1 && on2 && off2) {//两班制
					/* var onMin1 = parseInt(on1.split(':')[0]) * 60 + parseInt(on1.split(':')[1]);
					var offMin1 = parseInt(off1.split(':')[0]) * 60 + parseInt(off1.split(':')[1]);
					var onMin2 = parseInt(on2.split(':')[0]) * 60 + parseInt(on2.split(':')[1]);
					var offMin2 = parseInt(off2.split(':')[0]) * 60 + parseInt(off2.split(':')[1]); */
					var dateStart1=getDateTime(on1,1);
					var dateEnd1=getDateTime(off1,type1);
					var dateStart2=getDateTime(on2,1);
					var dateEnd2=getDateTime(off2,type2);
					var onMin1 = dateStart1.getTime();
					var offMin1 = dateEnd1.getTime();
					var onMin2 = dateStart2.getTime();
					var offMin2 = dateEnd2.getTime();
					if(offMin1 > onMin1 && offMin2 > onMin2) {
						totalTime = parseFloat(((offMin2 - onMin2 + offMin1 - onMin1) / (60*60*1000)).toFixed(1));
					}
				}
				totalTime = totalTime < 0 ? 0 : totalTime;
				totalTimeDiv.html(totalTime  + "${ lfn:message('sys-attend:sysAttendCategory.hour') }");
				fdTotalTime.val(totalTime);
			};
			
			var showAndAbled = function(id) {
				var parentDom = $('#' + id);
				if(!parentDom)
					return;
				var childInputs = parentDom.find(':input');
				if(childInputs)
					childInputs.removeAttr('disabled');
				parentDom.show();
			};
			
			var hideAndDisabled= function(id) {
				var parentDom = $('#' + id);
				if(!parentDom)
					return;
				var childInputs = parentDom.find(':input');
				if(childInputs)
					childInputs.prop('disabled', 'disabled');
				parentDom.hide();
			};
			
			var showAndResetVld = function(ele) {
				$(ele).show();
				vld.resetElementsValidate($(ele));
			};
			
			var hideAndRemoveVld = function(ele) {
				$(ele).hide();
				vld.removeElements($(ele));
			}
			
			window.changeFocus=function(name){
				$('input[name="'+name+'"]:enabled:visible').focus();
			}
			
			window.getDateTime=function(time,type){
				var date=new Date();
				if(type && type==2){
					date.setDate(date.getDate()+1);
				}
				date.setHours(parseInt(time.split(':')[0]),parseInt(time.split(':')[1]),0);
				return date;
			}
			
			//以下为校验器
			
			vld.addValidator('weekRepeat', "${ lfn:message('sys-attend:sysAttendCategory.validate.weekRepeat') }", function(v,e,o){
				if(!v){
					return true;
				}
				var fdWeeks = '${JsParam.fdWeeks}';
				var fdWeek = '${JsParam.fdWeek}'
				var weekList = v.split(/[,;]/);
				for(var idx in weekList) {
					if(fdWeeks.indexOf(weekList[idx]) > -1 && fdWeek.indexOf(weekList[idx]) == -1) {
						return false;
					} else {
						continue;
					}
				}
				return true;
			});
			
			vld.addValidator('beforeFirstStart', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.beforeFirstStart') }", function(v,e,o){
				vld.validateElement($('input[name="fdWorkTime[0].fdStartTime"]:enabled')[0]);
				var firstStart = $('input[name="fdWorkTime[0].fdStartTime"]:enabled').val();
				if(firstStart && v) {
					return firstStart >= v;
				} else {
					return true;
				}
			});
			
			vld.addValidator('afterOpen', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterOpen') }", function(v,e,o){
				var openTime = $('input[name="fdStartTime1"]:enabled').val();
				if(openTime && v) {
					return openTime <= v;
				} else {
					return true;
				}
			});
			
			vld.addValidator('afterFirstStart', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterFirstStart') }", function(v,e,o){
				var firstStart = $('input[name="fdWorkTime[0].fdStartTime"]:enabled').val();
				var overTimeType = $('select[name="fdWorkTime[0].fdOverTimeType"]:enabled:visible').val();
				if(firstStart && v) {
					if(overTimeType && overTimeType==2) {
						return true;	
					}
					return firstStart <= v;
				} else {
					return true;
				}
				
			});
			
			vld.addValidator('afterAcrossFirstStart', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterAcrossFirstStart') }", function(v,e,o){
				if($(e).is(':disabled'))
					return true;
				var firstStart = $('input[name="fdWorkTime[0].fdStartTime"]:enabled').val();
				var overTimeType = $('select[name="fdWorkTime[0].fdOverTimeType"]:enabled:visible').val();
				var overTimeType2 = $('select[name="fdWorkTime[1].fdOverTimeType"]:enabled:visible').val();
				if($(e).attr("name")=="fdWorkTime[1].fdEndTime"){
					overTimeType=overTimeType2;
				}
				if(firstStart && v && overTimeType && overTimeType==2) {
					return firstStart > v;	
				} 
				return true;
			});
			
			vld.addValidator('afterAcrossFirstEnd', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterAcrossFirstEnd') }", function(v,e,o){
				if($(e).is(':disabled'))
					return true;
				var firstEnd = $('input[name="fdWorkTime[0].fdEndTime"]:enabled:visible').val();
				var overTimeType = $('select[name="fdWorkTime[0].fdOverTimeType"]:enabled:visible').val();
				if(firstEnd && v && overTimeType && overTimeType==2) {
					return firstEnd < v;	
				} 
				return true;
			});
			
			vld.addValidator('afterAcrossSecondEnd', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterAcrossSecondEnd') }", function(v,e,o){
				if($(e).is(':disabled'))
					return true;
				var secondEnd = $('input[name="fdWorkTime[1].fdEndTime"]:enabled:visible').val();
				var overTimeType = $('select[name="fdWorkTime[1].fdOverTimeType"]:enabled:visible').val();
				if(secondEnd && v && overTimeType && overTimeType==2) {
					return secondEnd < v;	
				}
				return true;
			});
			
			vld.addValidator('afterFirstEnd', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterFirstEnd') }", function(v,e,o){
				if($(e).is(':disabled'))
					return true;
				var firstEnd = $('input[name="fdWorkTime[0].fdEndTime"]:enabled').val();
				var overTimeType = $('select[name="fdWorkTime[0].fdOverTimeType"]:enabled:visible').val();
				if(firstEnd && v) {
					if(overTimeType && overTimeType==2) {
						return false;
					} else {
						return firstEnd <= v;
					}
				} else {
					return true;
				}
				
			});
			
			vld.addValidator('afterSecondStart', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterSecondStart') }", function(v,e,o){
				if($(e).is(':disabled'))
					return true;
				var secondStart = $('input[name="fdWorkTime[1].fdStartTime"]:enabled').val();
				var overTimeType = $('select[name="fdWorkTime[1].fdOverTimeType"]:enabled:visible').val();
				if(overTimeType && overTimeType==2) {
					return true;
				}
				if(secondStart && v) {
					return secondStart <= v;
				} else {
					return true;
				}
			});
			
			vld.addValidator('afterAcrossSecondStart', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterAcrossFirstStart') }", function(v,e,o){
				if($(e).is(':disabled'))
					return true;
				var secondStart = $('input[name="fdWorkTime[0].fdStartTime"]:enabled').val();
				var overTimeType = $('select[name="fdWorkTime[1].fdOverTimeType"]:enabled:visible').val();
				if(secondStart && v && overTimeType && overTimeType==2) {
					return secondStart > v;
				} else {
					return true;
				}
			});
			
			vld.addValidator('afterEnd', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterEnd') }", function(v,e,o){
				var firstEnd = $('input[name="fdWorkTime[0].fdEndTime"]:enabled:visible').val();
				var secondEnd = $('input[name="fdWorkTime[1].fdEndTime"]:enabled:visible').val();
				var isAcrossDay = $('select[name="fdEndDay"]:enabled').val();
				var overTimeType = $('select[name="fdWorkTime[0].fdOverTimeType"]:enabled:visible').val();
				if(overTimeType && overTimeType==2) {
					if(isAcrossDay != '2'){
						return false;
					}else{
						var endTime = secondEnd || firstEnd;
						if(endTime) {
							return endTime <v;
						}
					}
				} else {
					if(isAcrossDay != '2'){
						var endTime = secondEnd || firstEnd;
						if(endTime) {
							return endTime <=v;
						}
					}
				}
				return true;
			});
			
			vld.addValidator('afterArcossEnd', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterEnd') }", function(v,e,o){
				var secondEnd = $('input[name="fdWorkTime[1].fdEndTime"]:enabled:visible').val();
				var isAcrossDay = $('select[name="fdEndDay"]:enabled:visible').val();
				var overTimeType2 = $('select[name="fdWorkTime[1].fdOverTimeType"]:enabled:visible').val();
				if(overTimeType2 && overTimeType2==2) {
					if(isAcrossDay != '2'){
						return false;
					}else{
						if(secondEnd) {
							return secondEnd <v;
						}
					}
				} else {
					if(isAcrossDay != '2'){
						if(secondEnd) {
							return secondEnd <=v;
						}
					}
				}
				return true;
			});
			
			vld.addValidator('acrossDay', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.acrossDay') }", function(v,e,o){
				var isAcrossDay = $('select[name="fdEndDay"]:enabled').val();
				var fdStartTime = $('input[name="fdStartTime1"]:enabled').val();
				if(isAcrossDay == '2'){
					if(fdStartTime){
						return fdStartTime > v;
					}
				}
				return true;
			});
			
			vld.addValidator('restTimeNull',"${ lfn:message('sys-attend:sysAttendCategory.validate.restTimeNull') }", function(v,e,o){
				var fieldName = $(e).attr('name');
				var restStart = $('[name="fdRestStartTime"]:enabled');
				var restEnd = $('[name="fdRestEndTime"]:enabled');
				if(restStart && restEnd) {
					if(v) {
						if(fieldName.indexOf('fdRestStartTime') > -1) {
							if(!restEnd.val()){
								vld.validateElement(restEnd[0]);
							}
						} else if(fieldName.indexOf('fdRestEndTime') > -1){
							if(!restStart.val()){
								vld.validateElement(restStart[0]);
							}
						}
						return true;
					} else {
						if(fieldName.indexOf('fdRestStartTime') > -1) {
							if(restEnd.val()){
								return false;
							}
						} else if(fieldName.indexOf('fdRestEndTime') > -1){
							if(restStart.val()){
								return false;
							}
						}
						return true;
					}
				} else {
					return true;
				}
			});
			
			vld.addValidator('restTimeRange',"${ lfn:message('sys-attend:sysAttendCategory.validate.restTimeRange') }", function(v,e,o){
				var restStart = $('[name="fdRestStartTime"]:enabled').val();
				//午休开始类型
				var fdRestStartType = $('select[name="fdRestStartType"]:enabled').val();
				if(restStart){
					restStart =getDateTime(restStart,fdRestStartType);
				}
				//午休结束时间
				var restEnd = $('[name="fdRestEndTime"]:enabled').val();
				//午休结束类型
				var fdRestEndType = $('select[name="fdRestEndType"]:enabled').val();
				if(restEnd){
					restEnd =getDateTime(restEnd,fdRestEndType);
				}
				//1班次的上班时间
				var workStartTime = $('[name="fdWorkTime[0].fdStartTime"]:enabled').val();
				if(workStartTime){
					workStartTime =getDateTime(workStartTime,1);
				}
				//1班次的下班时间
				var workEndTime = $('[name="fdWorkTime[0].fdEndTime"]:enabled').val();
				//1班次的下班时间是次日还是当日
				var overTimeType1 = $('select[name="fdWorkTime[0].fdOverTimeType"]:enabled:visible').val();
				if(workEndTime){
					workEndTime =getDateTime(workEndTime,overTimeType1);
				}
				if(restStart && restEnd && workStartTime && workEndTime){
					//开始打卡时间 小于休息开始时间，午休结束时间 小于 打卡结束时间
					return workStartTime < restStart && restEnd <= workEndTime;
				}
				return true;
			});
			
			vld.addValidator('restTimeStart',"${ lfn:message('sys-attend:sysAttendCategory.validate.restTimeStart') }", function(v,e,o){
				//午休时间的验证规则。结束时间必须大于等于开始时间
				if(!v) {
					return true;
				}
				var restStart = $('[name="fdRestStartTime"]:enabled').val();
				//午休开始类型
				var fdRestStartType = $('select[name="fdRestStartType"]:enabled').val();

				var restEnd = $('[name="fdRestEndTime"]:enabled').val();
				var fdRestEndType = $('select[name="fdRestEndType"]:enabled').val();
				if(restEnd && restStart){
					//午休开始时间
					restStart =getDateTime(restStart,fdRestStartType);
					//午休结束时间
					restEnd = getDateTime(restEnd,fdRestEndType);
					return restEnd.getTime() >= restStart.getTime();
				}
				return true;
			});
			
			vld.addValidator('restTimeEnd',"${ lfn:message('sys-attend:sysAttendCategory.validate.restTimeEnd') }", function(v,e,o){
				vld.validateElement($('[name="fdRestStartTime"]:enabled')[0]);
				return true;
			});
			
			vld.addValidator('firstEndTime',"${ lfn:message('sys-attend:sysAttendCategory.validate.firstEndTime') }", function(v,e,o){
				if(!v) {
					return true;
				}
				var offTime1 = $('[name="fdWorkTime[0].fdEndTime"]:enabled').val();
				var onTime2 = $('[name="fdWorkTime[1].fdStartTime"]:enabled').val();
				if(offTime1 && onTime2) {
					return v >= offTime1 && v <= onTime2;
				}
			});
			
			vld.addValidator('secondStartTime',"${ lfn:message('sys-attend:sysAttendCategory.validate.secondStartTime') }", function(v,e,o){
				if(!v) {
					return true;
				}
				var offTime1 = $('[name="fdWorkTime[0].fdEndTime"]:enabled').val();
				var onTime2 = $('[name="fdWorkTime[1].fdStartTime"]:enabled').val();
				if(offTime1 && onTime2) {
					return v >= offTime1 && v <= onTime2;
				}
			});
		</script>
	</template:replace>
</template:include>