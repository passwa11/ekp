<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" showQrcode="false">
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/sys/attend/resource/css/attend.css" />
		<script>
		Com_IncludeFile("doclistnew.js|jquery.js");
		</script>
		<style type="text/css">
			.add-btn{color: #37ace1 !important;text-decoration: underline;}
			.del-btn{color: #999 !important;float:right;text-decoration: underline;}
			.del-btn:hover{color: red !important;}
			.comment-text{font-size: 12px;color: red;display: block;clear: both;}
			#ovtReview > td label{line-height: 2.5;}
			#locationsList tr:first-child td{padding-top: 0}
			#osdReviewType > td label{line-height: 2.5;}
			#shiftTypeTd > label{line-height: 2.5;}
			.tb_simple > tbody > tr > td{word-break:break-word}
			#timePeriod>td .txtstrong {float: left}
		</style>
	</template:replace>
	<template:replace name="title">
			<c:out value="${ lfn:message('sys-attend:sysAttendHisCategory.name') }"></c:out>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<ui:button text="${ lfn:message('button.update') }" onclick="onSubmitMethod('update')"></ui:button>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home">
			</ui:menu-item>	
			<ui:menu-item text="${ lfn:message('sys-attend:module.sys.attend') }">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-attend:sysAttendCategory.attend') }">
			</ui:menu-item>
		</ui:menu>
	</template:replace>
	<template:replace name="content">
		<html:form action="/sys/attend/sys_attend_his_category/sysAttendHisCategory.do">
			<html:hidden property="fdId" />
			<html:hidden property="fdCategoryId" />
			<html:hidden property="sysAttendCategoryForm.fdType" />
			<html:hidden property="sysAttendCategoryForm.fdPeriodType"/>
			<html:hidden property="sysAttendCategoryForm.fdRestat" value="0"/>
			<div class="lui_form_content_frame" style="padding-top:20px">
				<div class="lui-singin-creatPage">
						<%-- 基本信息 --%>
					<div class="lui-singin-creatPage-panel">
						<div class="lui-singin-creatPage-panel-body">
							<table class="tb_simple" width="100%">
								<tr>
									<td class="td_normal_title" style="vertical-align: top;">
										<bean:message bundle="sys-attend" key="sysAttendCategory.attend.fdName"/>
									</td>
									<td>
										<xform:text property="sysAttendCategoryForm.fdName" style="width:95%" subject="${ lfn:message('sys-attend:sysAttendCategory.attend.fdName') }"/>
									</td>
								</tr>
								<tr>

									<td class="td_normal_title" style="vertical-align: top;">
										<bean:message bundle="sys-attend" key="sysAttendCategory.fdManager"/>
									</td>
									<td>
										<xform:address propertyId="sysAttendCategoryForm.fdManagerId" propertyName="sysAttendCategoryForm.fdManagerName" orgType="ORG_TYPE_PERSON" mulSelect="false"
													   subject="${ lfn:message('sys-attend:sysAttendCategory.fdManager') }" required="true"
													   style="width:95%" />
									</td>
								</tr>
								<tr>
									<td class="td_normal_title" style="vertical-align: top;">
										<bean:message bundle="sys-attend" key="sysAttendMain.export.shouldTime.attend"/>
									</td>
									<td>
										<kmss:showDate value="${sysAttendHisCategoryForm.fdBeginTime }" type="date"></kmss:showDate>
										~
										<kmss:showDate value="${sysAttendHisCategoryForm.fdEndTime }" type="date"></kmss:showDate>
									</td>
								</tr>
								<tr>
									<td class="td_normal_title" style="vertical-align: top;">
										<bean:message bundle="sys-attend" key="sysAttendCategory.fdEffectTime"/>
									</td>
									<td>
										<xform:datetime property="sysAttendCategoryForm.fdEffectTime" showStatus="readonly"
														subject="${ lfn:message('sys-attend:sysAttendCategory.fdEffectTime') }"
														dateTimeType="date" validators=""
														required="true"
														style="width:93%"></xform:datetime>
									</td>
								</tr>
							</table>
						</div>

							<%-- 签到范围 --%>
						<div class="lui-singin-creatPage-panel">
							<div class="lui-singin-creatPage-panel-heading">
								<h2 class="lui-singin-creatPage-panel-heading-title"><span id="signTargetsTitle">${ lfn:message('sys-attend:sysAttendCategoryRule.fdLimit') }</span></h2>
							</div>
								<%-- 人员 --%>
							<div class="lui-singin-creatPage-panel-body" id="signTargetsBody">
								<table class="tb_simple" width="100%">
									<tr>
										<td class="td_normal_title" style="vertical-align: top;">
											<bean:message bundle="sys-attend" key="sysAttendCategory.fdTargets"/>
										</td>
										<td>
											<xform:address propertyId="sysAttendCategoryForm.fdTargetIds" propertyName="sysAttendCategoryForm.fdTargetNames"
														   subject="${ lfn:message('sys-attend:sysAttendCategory.fdTargets') }"
														   mulSelect="true" orgType="ORG_TYPE_ORGORDEPT|ORG_TYPE_PERSON|ORG_FLAG_AVAILABLEALL" textarea="false" style="width:95%" required="true" />
										</td>
									</tr>
									<tr>
										<td class="td_normal_title">
											<bean:message bundle="sys-attend" key="sysAttendCategory.fdExcTargets"/>
										</td>
										<td>
											<xform:address propertyId="sysAttendCategoryForm.fdExcTargetIds" propertyName="sysAttendCategoryForm.fdExcTargetNames"
														   mulSelect="true" orgType="ORG_TYPE_PERSON" style="width:95%" />

										</td>
									</tr>
								</table>
							</div>
						</div>

							<%-- 签到时间 --%>
						<div class="lui-singin-creatPage-panel">
							<div class="lui-singin-creatPage-panel-heading">
								<h2 class="lui-singin-creatPage-panel-heading-title"><span id="signTimeTitle">${ lfn:message('sys-attend:sysAttendMain.docCreateTime') }</span></h2>
							</div>
							<div class="lui-singin-creatPage-panel-body" id="signTimeBody">
									<%-- 班制类型 --%>
								<div class="lui-singin-creatPage-table">
									<div class="caption" style="padding-top: 18px;">${ lfn:message('sys-attend:sysAttendCategory.fdShiftType') }</div>
									<div class="content">
										<table class="tb_simple" width="100%">
											<tr>
												<td id='shiftTypeTd'>
													<c:set var="fdShiftType_status" value="readOnly"></c:set>

													<xform:radio showStatus="${fdShiftType_status}" property="sysAttendCategoryForm.fdShiftType" alignment="V">
														<%-- 固定周期  --%>
														<xform:simpleDataSource value="0">
															${ lfn:message('sys-attend:sysAttendCategory.fdShiftType.fixed') }
															<span style="display: inline-block;color: #999;">${ lfn:message('sys-attend:sysAttendCategory.fdShiftType.fixed.tips') }</span>
															<div id='fdSameWtimeDiv' style="display: inline-block;margin-left: 30px;">
																<xform:select showStatus="${fdShiftType_status}" property="sysAttendCategoryForm.fdSameWorkTime" style="float: none;" showPleaseSelect="false"
																			  onValueChange="changeSameWTime">
																	<xform:simpleDataSource value="0">${ lfn:message('sys-attend:sysAttendCategory.fdSameWorkTime.yes') }</xform:simpleDataSource>
																	<xform:simpleDataSource value="1">${ lfn:message('sys-attend:sysAttendCategory.fdSameWorkTime.no') }</xform:simpleDataSource>
																</xform:select>
															</div>
														</xform:simpleDataSource>
														<%-- 排班  --%>
														<%-- --%>
														<xform:simpleDataSource value="1">
															${ lfn:message('sys-attend:sysAttendCategory.fdShiftType.schedule') }
															<span style="display: inline-block;color: #999;">${ lfn:message('sys-attend:sysAttendCategory.fdShiftType.schedule.tips') }</span>
														</xform:simpleDataSource>

														<%-- 自定义  --%>
														<xform:simpleDataSource value="2">
															${ lfn:message('sys-attend:sysAttendCategory.fdShiftType.custom') }
															<span style="display: inline-block;color: #999;">${ lfn:message('sys-attend:sysAttendCategory.fdShiftType.custom.tips') }</span>
														</xform:simpleDataSource>
													</xform:radio>
												</td>
											</tr>
										</table>
									</div>
								</div>
								<!-- 排班最早最晚打卡时间 -->
								<div class="lui-singin-creatPage-table" id="timeAreaTimeContent" style="display:none;margin-bottom:0px;">
									<div class="caption" style="padding-top: 18px;">
											${ lfn:message('sys-attend:sysAttendCategory.earliest.startTime') }
									</div>
									<div class="content">
										<table class="tb_simple" width="100%">
											<tr>
												<td width="15%" valign="top">
													<xform:datetime minuteStep="1" property="sysAttendCategoryForm.fdAreaStartTime"
																	dateTimeType="time" required="true"
																	validators="beforeFirstStart" style="width:90%"></xform:datetime>
												</td>
												<td valign="top">
													<div style="display:inline;float:left;padding-top: 2px;">${ lfn:message('sys-attend:sysAttendCategory.latest.endTime') }：</div>
													<xform:select property="sysAttendCategoryForm.fdEndDay" showPleaseSelect="false"
																  title="${ lfn:message('sys-attend:sysAttendCategory.latest.endTime') }"
																  onValueChange="changeFocus('fdAreaEndTime');" style="width:80px;height:32px;margin-right:7px;">
														<xform:simpleDataSource value='1'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }</xform:simpleDataSource>
														<xform:simpleDataSource value='2'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }</xform:simpleDataSource>
													</xform:select>
													<xform:datetime minuteStep="1" property="sysAttendCategoryForm.fdAreaEndTime" dateTimeType="time" validators="afterOpen acrossDay" required="true" style="width: 126px;"></xform:datetime>
												</td>
											</tr>
										</table>
									</div>
								</div>
									<%-- 一周相同工作时间 --%>
								<div id='sameWTime'>
										<%-- 工作日 --%>
									<div class="lui-singin-creatPage-table">
										<div class="caption" style="padding-top: 16px">${ lfn:message('sys-attend:sysAttendMain.fdDateType.workday') }</div>
										<div class="content">
											<table class="tb_simple" width="100%">
												<tr>
													<td id="weekTd">
														<xform:checkbox property="sysAttendCategoryForm.fdWeek" subject="${ lfn:message('sys-attend:sysAttendMain.fdDateType.workday') }" isArrayValue="false" required="true">
															<xform:enumsDataSource enumsType="sysAttendCategory_fdWeek" />
														</xform:checkbox>
													</td>
													<td id="customDateTd" style="display: none">
														<div class="inputselectsgl" onclick="selectMulDate('fdCustomDateStr')" style="width: 95%;">
															<div class="input">
																<input type="text" name="sysAttendCategoryForm.fdCustomDateStr"  readonly="readonly"
																	   value="<c:if test="${sysAttendHisCategoryForm.sysAttendCategoryForm.fdShiftType == '2' }">
																	   ${sysAttendHisCategoryForm.sysAttendCategoryForm.fdCustomDateStr }</c:if>"
																	   validate="required" subject="${ lfn:message('sys-attend:sysAttendMain.fdDateType.workday') }" />
															</div>
															<div class="inputdatetime"></div>
														</div>
														<span class="txtstrong">*</span>
													</td>
												</tr>
											</table>
										</div>
									</div>
									<%-- 工作时间 --%>
									<div id='signTime' class="lui-singin-creatPage-table">
										<div class="caption">${ lfn:message('sys-attend:sysAttendCategory.worktime') }</div>
										<div class="content">
											<div class="lui-singin-creatPage-tab">
												<ul class="lui-singin-creatPage-tab-heading">
													<html:hidden property="sysAttendCategoryForm.fdWork" />
													<li id='onceType' onclick="changeWorkType('1')"><a href="javascript:void(0);">${ lfn:message('sys-attend:sysAttendCategory.fdWork.once') }</a></li>
													<li id='twiceType' onclick="changeWorkType('2')"><a href="javascript:void(0);">${ lfn:message('sys-attend:sysAttendCategory.fdWork.twice') }</a></li>
												</ul>
												<div class="lui-singin-creatPage-tab-body" style="padding-bottom: 0">
													<table class="tb_simple" width="100%">
															<%-- 第一班次  --%>
														<tr id="onceWorkTime">
																<%-- 上班 --%>
															<td style="width: 30px" class="td_normal_title td_tab_title">
																	${ lfn:message('sys-attend:sysAttendMain.fdWorkType.onwork') }
															</td>
															<td style="width: 120px">
																<html:hidden property="sysAttendCategoryForm.fdWorkTime[0].fdId"/>
																<html:hidden property="sysAttendCategoryForm.fdWorkTime[0].fdIsAvailable" value="${fdWTAvailable1 }" />

																<xform:datetime minuteStep="1"
																				property="sysAttendCategoryForm.fdWorkTime[0].fdStartTime"
																				subject="${ lfn:message('sys-attend:sysAttendCategoryWorktime.fdStartTime') }"
																				validators="afterOpen"
																				required="true"
																				dateTimeType="time"
																				style="width:80%;"
																				onValueChange="calTotalTime"></xform:datetime>
															</td>
																<%-- 下班 --%>
															<td style="width: 80px" class="td_normal_title td_tab_title">
																—&nbsp;&nbsp;&nbsp;&nbsp;
																	${ lfn:message('sys-attend:sysAttendMain.fdWorkType.offwork') }
															</td>
															<td style="width: 170px">
																<div id='overTimeTypeOnce'>
																	<xform:select property="sysAttendCategoryForm.fdWorkTime[0].fdOverTimeType"
																				  showPleaseSelect="false" title=""
																				  onValueChange="changeFocus('sysAttendCategoryForm.fdWorkTime[0].fdEndTime');"
																				  style="width:35%;height:30px;margin-right:7px;">
																		<xform:simpleDataSource value='1'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }</xform:simpleDataSource>
																		<xform:simpleDataSource value='2'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }</xform:simpleDataSource>
																	</xform:select>
																</div>
																<div id='overTimeTypeTwice'>
																	<xform:select property="sysAttendCategoryForm.fdWorkTime[0].fdOverTimeType"
																				  showPleaseSelect="false" title=""
																				  onValueChange="changeFocus('sysAttendCategoryForm.fdWorkTime[0].fdEndTime');"
																				  style="width:35%;height:30px;margin-right:7px;">
																		<xform:simpleDataSource value='1'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }</xform:simpleDataSource>
																	</xform:select>
																</div>
																<xform:datetime minuteStep="1" property="sysAttendCategoryForm.fdWorkTime[0].fdEndTime" subject="${ lfn:message('sys-attend:sysAttendCategoryWorktime.fdEndTime') }" validators="afterFirstStart afterAcrossFirstStart" required="true" dateTimeType="time" style="width:50%;" value="${fdOffTime1 }" onValueChange="calTotalTime"></xform:datetime>
															</td>
																<%-- 最早打卡 --%>
															<td style="width: 80px" class="td_normal_title td_tab_title">
																	${ lfn:message('sys-attend:sysAttendCategory.earliest.startTime') }：
															</td>
															<td style="width: 140px">
																<xform:datetime minuteStep="1"
																				property="sysAttendCategoryForm.fdStartTime"
																				dateTimeType="time"
																				required="true"
																				validators="beforeFirstStart afterAcrossFirstEnd"
																				style="width:90%"></xform:datetime>
															</td>
																<%-- 最晚打卡 --%>
															<td style="width: 80px" class="td_normal_title td_tab_title">
																	${ lfn:message('sys-attend:sysAttendCategory.latest.endTime') }：
															</td>
															<td style="">
																<div id='endTimeOnce'>
																	<xform:select property="sysAttendCategoryForm.fdEndDay"
																				  showPleaseSelect="false"
																				  title="${ lfn:message('sys-attend:sysAttendCategory.latest.endTime') }"
																				  onValueChange="changeFocus('sysAttendCategoryForm.fdEndTime');" style="width:40%;height:32px;margin-right:7px;">
																		<xform:simpleDataSource value='1'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }</xform:simpleDataSource>
																		<xform:simpleDataSource value='2'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }</xform:simpleDataSource>
																	</xform:select>
																	<xform:datetime minuteStep="1" property="sysAttendCategoryForm.fdEndTime" dateTimeType="time" validators="afterEnd acrossDay" required="true" style="width: 48%"></xform:datetime>
																</div>

																<div id='endTimeTwice'>
																	<xform:datetime minuteStep="1" property="sysAttendCategoryForm.fdEndTime1" dateTimeType="time" validators="firstEndTime" style="width:90%"></xform:datetime>
																</div>
															</td>
														</tr>
															<%-- 第二班次  --%>
														<tr id="twiceWorkTime">
																<%-- 上班 --%>
															<td style="width: 30px" class="td_normal_title td_tab_title">
																	${ lfn:message('sys-attend:sysAttendMain.fdWorkType.onwork') }
															</td>
															<td style="width: 120px">
																<html:hidden property="sysAttendCategoryForm.fdWorkTime[1].fdId"/>
																<html:hidden property="sysAttendCategoryForm.fdWorkTime[1].fdIsAvailable" value="${fdWTAvailable2 }" />
																<xform:datetime minuteStep="1" property="sysAttendCategoryForm.fdWorkTime[1].fdStartTime" subject="${ lfn:message('sys-attend:sysAttendCategoryWorktime.fdStartTime') }" validators="afterFirstEnd" required="true" dateTimeType="time" style="width:80%;" onValueChange="calTotalTime"></xform:datetime>
															</td>
																<%-- 下班 --%>
															<td style="width: 80px" class="td_normal_title td_tab_title">
																—&nbsp;&nbsp;&nbsp;&nbsp;
																	${ lfn:message('sys-attend:sysAttendMain.fdWorkType.offwork') }
															</td>
															<td style="width: 160px">
																<xform:select property="sysAttendCategoryForm.fdWorkTime[1].fdOverTimeType"
																			  showPleaseSelect="false" title=""
																			  onValueChange="changeFocus('sysAttendCategoryForm.fdWorkTime[1].fdEndTime');"
																			  style="width:35%;height:30px;margin-right:7px;">
																	<xform:simpleDataSource value='1'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }</xform:simpleDataSource>
																	<xform:simpleDataSource value='2'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }</xform:simpleDataSource>
																</xform:select>
																<xform:datetime minuteStep="1" property="sysAttendCategoryForm.fdWorkTime[1].fdEndTime" subject="${ lfn:message('sys-attend:sysAttendCategoryWorktime.fdEndTime') }" validators="afterSecondStart afterAcrossFirstStart afterAcrossSecondStart" required="true" dateTimeType="time" style="width:50%;" onValueChange="calTotalTime"></xform:datetime>
															</td>
																<%-- 最早打卡 --%>
															<td style="width: 80px" class="td_normal_title td_tab_title">
																	${ lfn:message('sys-attend:sysAttendCategory.earliest.startTime') }：
															</td>
															<td style="width: 120px">
																<xform:datetime minuteStep="1" property="sysAttendCategoryForm.fdStartTime2" dateTimeType="time" validators="secondStartTime afterAcrossSecondEnd" style="width:90%"></xform:datetime>
															</td>
																<%-- 最晚打卡 --%>
															<td style="width: 80px" class="td_normal_title td_tab_title">
																	${ lfn:message('sys-attend:sysAttendCategory.latest.endTime') }：
															</td>
															<td style="" id="endTimeTwice2">
																<xform:select property="sysAttendCategoryForm.fdEndDay"
																			  showPleaseSelect="false"
																			  title="${ lfn:message('sys-attend:sysAttendCategory.latest.endTime') }"
																			  onValueChange="changeFocus('sysAttendCategoryForm.fdEndTime2');"
																			  style="width:40%;height:32px;margin-right:7px;">
																	<xform:simpleDataSource value='1'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }</xform:simpleDataSource>
																	<xform:simpleDataSource value='2'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }</xform:simpleDataSource>
																</xform:select>
																<xform:datetime minuteStep="1" property="sysAttendCategoryForm.fdEndTime2" dateTimeType="time" validators="afterEnd acrossDay afterArcossEnd" required="true" style="width: 48%"></xform:datetime>
															</td>
														</tr>
													</table>
												</div>
											</div>
										</div>
									</div>
										<%-- 午休时间 --%>
									<div class="lui-singin-creatPage-table" id="restTimeTB">
										<div class="caption" style="padding-top: 18px;">
												${ lfn:message('sys-attend:sysAttendCategory.noon.restTime') }
										</div>
										<div class="content">
											<table class="tb_simple" width="100%">
												<tr>
													<td>
														<xform:select property="sysAttendCategoryForm.fdRestStartType"
																	  showPleaseSelect="false"
																	  title="${ lfn:message('sys-attend:sysAttendCategory.fdRestStartType') }"
																	  style="width:80px;height:32px;margin-right:7px;"
																	  showStatus="edit"
																	  onValueChange="calTotalTime"
														>
															<xform:simpleDataSource value='1'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }</xform:simpleDataSource>
															<xform:simpleDataSource value='2'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }</xform:simpleDataSource>
														</xform:select>
														<xform:datetime minuteStep="1" property="sysAttendCategoryForm.fdRestStartTime" dateTimeType="time" validators="restTimeNull restTimeRange restTimeStart" style="width:100px" onValueChange="calTotalTime"></xform:datetime>
														<span style="float: left;margin:0 20px;">—</span>
														<xform:select property="sysAttendCategoryForm.fdRestEndType"
																	  showPleaseSelect="false"
																	  title="${ lfn:message('sys-attend:sysAttendCategory.fdRestEndType') }"
																	  style="width:80px;height:32px;margin-right:7px;"
																	  showStatus="edit"
																	  onValueChange="calTotalTime"
														>
															<xform:simpleDataSource value='1'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }</xform:simpleDataSource>
															<xform:simpleDataSource value='2'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }</xform:simpleDataSource>
														</xform:select>
														<xform:datetime minuteStep="1" property="sysAttendCategoryForm.fdRestEndTime" dateTimeType="time" validators="restTimeNull restTimeRange restTimeEnd" style="width:100px" onValueChange="calTotalTime"></xform:datetime>
													</td>
												</tr>
											</table>
										</div>
									</div>
										<%-- 总工时 --%>
									<div class="lui-singin-creatPage-table">
										<div class="caption" style="padding-top: 18px;">
												${ lfn:message('sys-attend:sysAttendStat.fdTotalTime') }
										</div>
										<div class="content">
											<html:hidden property="sysAttendCategoryForm.fdTotalTime" />
											<div id='totalTimeDiv' style="padding-top: 13px;margin-left: 10px;">
													${sysAttendHisCategoryForm.sysAttendCategoryForm.fdTotalTime }${ lfn:message('sys-attend:sysAttendCategory.hour') }
											</div>
										</div>
									</div>
								</div>
									<%-- 一周不同工作时间 --%>
								<div id="notSameWTime">
									<div class="lui-singin-creatPage-table">
										<div class="caption" style="padding-top: 18px;">
												${ lfn:message('sys-attend:sysAttendCategory.worktime') }
										</div>
										<div class="content">
											<table id="wTimeSheet" class="tb_normal" width="100%" style="text-align: center;">
												<tr>
													<td class="td_normal_title" width="40%">
															${ lfn:message('sys-attend:sysAttendMain.fdDateType.workday') }
													</td>
													<td class="td_normal_title" width="40%">
															${ lfn:message('sys-attend:sysAttendCategory.worktime') }
													</td>
													<td class="td_normal_title" width="20%">
<%--														<a href="javascript:void(0);" class="add-btn" onclick="addTimeSheet();">--%>
<%--																${ lfn:message('button.create') }--%>
<%--														</a>--%>
													</td>
												</tr>
												<tr KMSS_IsReferRow="1" style="display:none">
													<td>
													</td>
													<td>
													</td>
													<td>
														<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[!{index}].fdId" />
														<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[!{index}].fdWeek" />
														<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[!{index}].fdWork" />
														<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[!{index}].fdWorkTime[0].fdId" data-wtime-idx='0' />
														<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[!{index}].fdWorkTime[0].fdIsAvailable" data-wtime-idx='0' />
														<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[!{index}].fdWorkTime[0].fdStartTime" data-wtime-idx='0' />
														<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[!{index}].fdWorkTime[0].fdEndTime" data-wtime-idx='0' />
														<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[!{index}].fdWorkTime[0].fdOverTimeType" data-wtime-idx='0' />
														<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[!{index}].fdWorkTime[1].fdId" data-wtime-idx='1' />
														<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[!{index}].fdWorkTime[1].fdIsAvailable" data-wtime-idx='1' />
														<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[!{index}].fdWorkTime[1].fdStartTime" data-wtime-idx='1' />
														<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[!{index}].fdWorkTime[1].fdEndTime" data-wtime-idx='1' />
														<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[!{index}].fdWorkTime[1].fdOverTimeType" data-wtime-idx='0' />
														<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[!{index}].fdStartTime1" />
														<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[!{index}].fdStartTime2" />
														<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[!{index}].fdEndTime1" />
														<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[!{index}].fdEndTime2" />
														<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[!{index}].fdEndDay" />
														<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[!{index}].fdRestStartTime" />
														<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[!{index}].fdRestEndTime" />
														<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[!{index}].fdTotalTime" />
														<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[!{index}].fdTotalDay" />
														<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[!{index}].fdRestEndType" />
														<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[!{index}].fdRestStartType" />
														<a href="javascript:void(0);" class="add-btn" onclick="editTimeSheet();">
																${ lfn:message('button.edit') }
														</a>
														<a href="javascript:void(0);" class="add-btn" onclick="deleteTimeSheet();">
																${ lfn:message('button.delete') }
														</a>
													</td>
												</tr>
												<c:forEach items="${sysAttendHisCategoryForm.sysAttendCategoryForm.fdTimeSheets}" var="timeItem" varStatus="vstatus">
													<tr KMSS_IsContentRow="1">
														<td>
																${timeItem.fdWeekNames }
														</td>
														<td>
																${timeItem.fdWorkTimeText}
														</td>
														<td>
															<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[${vstatus.index }].fdId" value="${timeItem.fdId }" />
															<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[${vstatus.index }].fdWeek" value="${timeItem.fdWeek }" />
															<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[${vstatus.index }].fdWork" value="${timeItem.fdWork }" />
															<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[${vstatus.index }].fdWorkTime[0].fdId" value="${timeItem.fdWorkTime[0].fdId }" data-wtime-idx='0' />
															<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[${vstatus.index }].fdWorkTime[0].fdIsAvailable" value="${timeItem.fdWorkTime[0].fdIsAvailable }" data-wtime-idx='0' />
															<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[${vstatus.index }].fdWorkTime[0].fdStartTime" value="${timeItem.fdWorkTime[0].fdStartTime }" data-wtime-idx='0' />
															<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[${vstatus.index }].fdWorkTime[0].fdEndTime" value="${timeItem.fdWorkTime[0].fdEndTime }" data-wtime-idx='0' />
															<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[${vstatus.index }].fdWorkTime[0].fdOverTimeType" value="${timeItem.fdWorkTime[0].fdOverTimeType }" data-wtime-idx='0' />
															<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[${vstatus.index }].fdWorkTime[1].fdId" value="${timeItem.fdWorkTime[1].fdId }" data-wtime-idx='1' />
															<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[${vstatus.index }].fdWorkTime[1].fdIsAvailable" value="${timeItem.fdWorkTime[1].fdIsAvailable }" data-wtime-idx='1' />
															<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[${vstatus.index }].fdWorkTime[1].fdStartTime" value="${timeItem.fdWorkTime[1].fdStartTime }" data-wtime-idx='1' />
															<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[${vstatus.index }].fdWorkTime[1].fdEndTime" value="${timeItem.fdWorkTime[1].fdEndTime }" data-wtime-idx='1' />
															<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[${vstatus.index }].fdWorkTime[1].fdOverTimeType" value="${timeItem.fdWorkTime[1].fdOverTimeType }" data-wtime-idx='0' />
															<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[${vstatus.index }].fdStartTime1" value="${timeItem.fdStartTime1 }" />
															<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[${vstatus.index }].fdStartTime2" value="${timeItem.fdStartTime2 }" />
															<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[${vstatus.index }].fdEndTime1" value="${timeItem.fdEndTime1 }" />
															<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[${vstatus.index }].fdEndTime2" value="${timeItem.fdEndTime2 }" />
															<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[${vstatus.index }].fdEndDay" value="${timeItem.fdEndDay }" />
															<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[${vstatus.index }].fdRestStartTime" value="${timeItem.fdRestStartTime }" />
															<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[${vstatus.index }].fdRestEndTime" value="${timeItem.fdRestEndTime }" />
															<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[${vstatus.index }].fdTotalTime" value="${timeItem.fdTotalTime }" />
															<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[${vstatus.index }].fdTotalDay" value="${timeItem.fdTotalDay }" />
															<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[${vstatus.index }].fdRestEndType" value="${timeItem.fdRestEndType }" />
															<input type="hidden" name="sysAttendCategoryForm.fdTimeSheets[${vstatus.index }].fdRestStartType" value="${timeItem.fdRestStartType }" />

															<a href="javascript:void(0);" class="add-btn" onclick="editTimeSheet();">
																	${ lfn:message('button.edit') }
															</a>
<%--															<a href="javascript:void(0);" class="add-btn" onclick="deleteTimeSheet();">--%>
<%--																	${ lfn:message('button.delete') }--%>
<%--															</a>--%>
														</td>
													</tr>
												</c:forEach>
											</table>
											<div class="validation-advice" id="tSheetCountVld" style="display: none">
												<table class="validation-table">
													<tbody>
													<tr>
														<td><div
																class="lui_icon_s lui_icon_s_icon_validator"></div></td>
														<td class="validation-advice-msg"><span
																class="validation-advice-title">工作时间</span> 不能为空</td>
													</tr>
													</tbody>
												</table>
											</div>
											<div id='tSheetCountTips' style="display: none;margin-top: 10px;">
												<span class="comment-text">${ lfn:message('sys-attend:sysAttendCategory.timeSheet.count.tips') }</span>
											</div>
										</div>
									</div>
								</div>

									<%-- 节假日 --%>
								<div class="lui-singin-creatPage-table" id="holidayContent">
									<div class="caption" style="padding-top: 16px;">
											${ lfn:message('sys-attend:sysAttendCategory.fdHoliday') }
									</div>
									<div class="content">
										<table class="tb_simple" width="100%">
											<tr>
												<td>
													<html:hidden property="sysAttendCategoryForm.fdHolidayId" />
													<html:hidden property="sysAttendCategoryForm.fdHolidayName" />
													<div id='holidayNameDiv' style="display: inline-block;margin-right: 20px">
															${sysAttendHisCategoryForm.sysAttendCategoryForm.fdHolidayName }
													</div>
													<a href="javascript:void(0);" class="add-btn" onclick="selHoliday();" title="${ lfn:message('sys-attend:sysAttendCategory.holiday.import') }" style="margin-right: 20px">
															${ lfn:message('sys-attend:sysAttendCategory.holiday.select') }
													</a>
													<kmss:authShow roles="ROLE_SYS_TIME_HOLIDAY_ADMIN">
														<a href="javascript:void(0);" class="add-btn" onclick="window.open('<c:url value="/sys/profile/index.jsp?type=attend#lbpm/timeArea" />','_blank');" title="${ lfn:message('sys-attend:sysAttendCategory.holiday.import') }">
																${ lfn:message('sys-attend:sysAttendCategory.holiday.manage') }
														</a>
													</kmss:authShow>
												</td>
											</tr>
										</table>
									</div>
								</div>
									<%--追加打卡日期 --%>
								<div class="lui-singin-creatPage-table" id="appendTimeContent">
									<div class="caption" style="padding-top: 18px;width: 90px;">
											${ lfn:message('sys-attend:sysAttendCategory.fdTimes') }
									</div>
									<div class="content">
										<table class="tb_simple" width="100%">
											<tr>
												<td>
													<div class="inputselectsgl" onclick="selectMulDate('sysAttendCategoryForm.fdTimesStr')" style="width: 95%;">
														<div class="input">
															<input type="text" name="sysAttendCategoryForm.fdTimesStr"  readonly="readonly" value="<c:if test="${sysAttendHisCategoryForm.sysAttendCategoryForm.fdShiftType != '2' }">${sysAttendHisCategoryForm.sysAttendCategoryForm.fdTimesStr }</c:if>" />
														</div>
														<div class="inputdatetime"></div>
													</div>
												</td>
											</tr>
										</table>
									</div>
								</div>
									<%--排除打卡日期 --%>
								<div class="lui-singin-creatPage-table" id='excTimeContent'>
									<div class="caption" style="padding-top: 18px;width: 90px;">
											${ lfn:message('sys-attend:sysAttendCategory.fdExcTimes') }
									</div>
									<div class="content">
										<table class="tb_simple" width="100%">
											<tr>
												<td>
													<div class="inputselectsgl" onclick="selectMulDate('sysAttendCategoryForm.fdExcTimesStr')" style="width: 95%;">
														<div class="input">
															<input type="text" name="sysAttendCategoryForm.fdExcTimesStr"  readonly="readonly" value="${sysAttendHisCategoryForm.sysAttendCategoryForm.fdExcTimesStr }" />
														</div>
														<div class="inputdatetime"></div>
													</div>
												</td>
											</tr>
										</table>
									</div>
								</div>
							</div>
						</div>
							<%-- 签到方式 --%>
						<div id='signAction' class="lui-singin-creatPage-panel">
							<div class="lui-singin-creatPage-panel-heading">
								<h2 class="lui-singin-creatPage-panel-heading-title"><span id="signTypeTitle">${ lfn:message('sys-attend:sysAttendCategoryRule.fdMode') }</span></h2>
							</div>
							<div class="lui-singin-creatPage-panel-body" id="signTypeBody">
								<!-- 钉钉考勤机打卡 -->
								<c:if test="${isEnableDingConfig eq true || isEnableWxConfig eq true}">
									<div class="lui-singin-creatPage-table">
										<div class="caption" style="padding-top: 17px;">${ lfn:message('sys-attend:sysAttendCategory.fdDingClock') }</div>
										<div class="content">
											<div class="lui-singin-creatPage-tab">
												<table class="tb_simple" width="100%">
													<tr>
														<td colspan="2">
															<ui:switch property="sysAttendCategoryForm.fdDingClock" onValueChange="onDingChange();"
																	   enabledText="${ synConfigType=='qywx'?lfn:message('sys-attend:sysAttendCategory.fdWxClock.enable'):lfn:message('sys-attend:sysAttendCategory.fdDingClock.enable') }"
																	   disabledText="${ synConfigType=='qywx'?lfn:message('sys-attend:sysAttendCategory.fdWxClock.disable'):lfn:message('sys-attend:sysAttendCategory.fdDingClock.disable') }" >
															</ui:switch>
														</td>
													</tr>
												</table>
											</div>
										</div>
									</div>
								</c:if>
									<%-- 地点签到 --%>
								<div class="lui-singin-creatPage-table">
									<div class="caption" style="padding-top: 15px;">${ lfn:message('sys-attend:sysAttendCategory.singin.map') }</div>
									<div class="content">
										<div class="lui-singin-creatPage-tab">
											<table id="locationDemo" class="tb_simple" style="display:none">
												<tr EKP_IsReferRow="1">
													<td>
														<input type="hidden" attrname="sysAttendCategoryForm.fdLimitLocations[pidx].fdLocations[idx].fdId" />
														<div data-location-container="sysAttendCategoryForm.fdLimitLocations[pidx].fdLocations[idx].fdLocation" class="lui_location_container" style="width:97%;float:left;" mark-loaded="false"></div>
														<span class="txtstrong">*</span>
														<a href="javascript:void(0);" onclick="deletePosition('child');" title="${lfn:message('doclist.delete')}">
															<img src="${KMSS_Parameter_ContextPath}sys/attend/resource/images/delete_btn.png"/>
														</a>
													</td>
												</tr>
											</table>
											<table id="limitLocationsList" class="tb_simple" width="100%">
												<tr>
													<td>
														<ui:switch property="sysAttendCategoryForm.fdCanMap" onValueChange="onMapChange();" checked="${sysAttendHisCategoryForm.sysAttendCategoryForm.fdCanMap == '0'?false:true}">
														</ui:switch>
													</td>
												</tr>
												<tr id="" KMSS_IsReferRow="1">
													<td>
															<%--<%@ taglib uri="/WEB-INF/KmssConfCig/sys/attend/map.tld" prefix="map"%> --%>
														<table class="tb_simple" width="100%">
																<%-- 范围 --%>
															<tr>
																<td>
																	<xform:text property="sysAttendCategoryForm.fdLimitLocations[!{index}].fdLimit" validators="digits" required="true" subject="${ lfn:message('sys-attend:sysAttendCategoryRule.fdLimit') }"
																				value="${fdLimit }" style="width:100px;margin-right:8px;"></xform:text>
																	<bean:message bundle="sys-attend" key="sysAttendCategoryRule.fdLimit.setting"/>
																</td>
															</tr>
																<%-- 地点 --%>
															<tr>
																<td colspan="2">
																	<table id="locationsList_!{index}" class="tb_simple" width="100%">
																		<tr EKP_IsReferRow="1">
																			<td>
																				<input type="hidden" name="sysAttendCategoryForm.fdLimitLocations[!{index}].fdLocations[!{indexChild}].fdId" />
																				<map:location propertyName="sysAttendCategoryForm.fdLimitLocations[!{index}].fdLocations[!{indexChild}].fdLocation"
																							  propertyCoordinate="sysAttendCategoryForm.fdLimitLocations[!{index}].fdLocations[!{indexChild}].fdLocationCoordinate"
																							  subject="${ lfn:message('sys-attend:sysAttendCategory.fdLocations') }"
																							  style="width:97%;float:left;" required='true' ></map:location>
																				<a href="javascript:void(0);" onclick="deletePosition('child');" title="${lfn:message('doclist.delete')}">
																					<img src="${KMSS_Parameter_ContextPath}sys/attend/resource/images/delete_btn.png"/>
																				</a>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2">
																				<a href="javascript:void(0);" class="add-btn" onclick="addPosition('locationsList_!{index}',this);" title="${lfn:message('doclist.add')}">
																						${ lfn:message('sys-attend:sysAttendCategory.new.location') }
																				</a>
																				<a href="javascript:void(0);" class="del-btn" onclick="deletePosition('locationsList_!{index}');" title="${ lfn:message('sys-attend:sysAttendCategory.location.deleterow') }">
																						${ lfn:message('sys-attend:sysAttendCategory.location.deleterow') }
																				</a>
																			</td>
																		</tr>
																	</table>
																</td>
															</tr>
														</table>
													</td>
												</tr>
													<c:set var="limitstr" value="-1"></c:set>
													<c:set var="tableIndex" value="-1"></c:set>
												<c:forEach items="${sysAttendHisCategoryForm.sysAttendCategoryForm.fdLocations}" var="fdLocationsItem" varStatus="vstatus">
												<c:choose>
												<c:when test="${fdLocationsItem.fdDataType=='newdata' && limitstr!=fdLocationsItem.fdLimitIndex}">
													<c:set var="tableIndex" value="${tableIndex+1}"></c:set>
													<c:set var="limitstr" value="${fdLocationsItem.fdLimitIndex}"></c:set>
												<tr KMSS_IsContentRow="1">
													<td>
															<%--<%@ taglib uri="/WEB-INF/KmssConfCig/sys/attend/map.tld" prefix="map"%> --%>
														<table class="tb_simple" width="100%">
																<%-- 范围 --%>
															<tr>
																<td>
																	<xform:text property="sysAttendCategoryForm.fdLimitLocations[${fdLocationsItem.fdLimitIndex}].fdLimit" validators="digits" required="true" subject="${ lfn:message('sys-attend:sysAttendCategoryRule.fdLimit') }"
																				value="${fdLocationsItem.fdLimit }" style="width:100px;margin-right:8px;"></xform:text>
																	<bean:message bundle="sys-attend" key="sysAttendCategoryRule.fdLimit.setting"/>
																</td>
															</tr>
																<%-- 地点 --%>
															<tr>
																<td colspan="2">
																	<table id="locationsList_${tableIndex}" class="tb_simple" width="100%">
																		<tr KMSS_IsReferRow="1"><td></td></tr>
																		</c:when>
																			<%-- 旧数据兼容 --%>
																		<c:otherwise>
																		<c:if test="${oldLoad !='1' && fdLocationsItem.fdDataType!='newdata'}">
																			<c:set var="tableIndex" value="${tableIndex+1}"></c:set>
																			<c:set var="oldLoad" value="1"></c:set>
																			<tr KMSS_IsContentRow="1">
																				<td>
																						<%--<%@ taglib uri="/WEB-INF/KmssConfCig/sys/attend/map.tld" prefix="map"%> --%>
																					<table class="tb_simple" width="100%">
																							<%-- 范围 --%>
																						<tr>
																							<td>
																								<xform:text property="sysAttendCategoryForm.fdLimitLocations[0].fdLimit" validators="digits" required="true" subject="${ lfn:message('sys-attend:sysAttendCategoryRule.fdLimit') }"
																											value="${sysAttendHisCategoryForm.sysAttendCategoryForm.fdRule[0].fdLimit }" style="width:100px;margin-right:8px;"></xform:text>
																								<bean:message bundle="sys-attend" key="sysAttendCategoryRule.fdLimit.setting"/>
																							</td>
																						</tr>
																							<%-- 地点 --%>
																						<tr>
																							<td colspan="2">
																								<table id="locationsList_${tableIndex}" class="tb_simple" width="100%">
																									<tr KMSS_IsReferRow="1"><td></td></tr>
																									</c:if>
																								</c:otherwise>
																								</c:choose>
																									<%-- 以上新旧数据头部，以下范围及范围添加操作部分 --%>
																								<c:choose>
																									<c:when test="${fdLocationsItem.fdDataType=='newdata'}">
																										<tr KMSS_IsContentRow="1">
																											<td>
																												<input type="hidden" name="sysAttendCategoryForm.fdLimitLocations[${fdLocationsItem.fdLimitIndex}].fdLocations[${fdLocationsItem.fdRow}].fdId" />
																												<div data-location-container="sysAttendCategoryForm.fdLimitLocations[${fdLocationsItem.fdLimitIndex}].fdLocations[${fdLocationsItem.fdRow}].fdLocation" class="lui_location_container" style="width:97%;float:left;" mark-loaded="false"></div>
																												<span class="txtstrong">*</span>
																												<c:set var="LimitIndex"  value="${fdLocationsItem.fdLimitIndex}"></c:set>
																												<c:set var="fdRow" value="${fdLocationsItem.fdRow}"></c:set>
																												<c:set var="_fdLocation" value="${fdLocationsItem.fdLocation}"></c:set>
																												<c:set var="_fdLocationCoordinate" value="${fdLocationsItem.fdLocationCoordinate}"></c:set>
																												<script>
																													seajs.use(['sys/attend/map/resource/js/LocationInit.js'],function(init){
																														init( {"id":null,"propertyName":"sysAttendCategoryForm.fdLimitLocations[${LimitIndex}].fdLocations[${fdRow}].fdLocation",
																															"propertyCoordinate":"sysAttendCategoryForm.fdLimitLocations[${LimitIndex}].fdLocations[${fdRow}].fdLocationCoordinate",
																															"nameValue":"${_fdLocation}","coordinateValue":"${_fdLocationCoordinate}",
																															"showStatus":"edit","subject":"签到点","required":true,"validators":"required","propertyDetail":null,"detailValue":null,
																															"placeholder":null,"radius":null,"propertyProvince":null,"provinceValue":null,"propertyDistrict":null,"districtValue":null,
																															"propertyCity":null,"cityValue":null,"isModify":null,"defaultValue":null} );
																													})
																												</script>
																												<a href="javascript:void(0);" onclick="deletePosition('child');" title="${lfn:message('doclist.delete')}">
																													<img src="${KMSS_Parameter_ContextPath}sys/attend/resource/images/delete_btn.png"/>
																												</a>
																											</td>
																										</tr>
																									</c:when>
																									<c:otherwise>
																										<tr KMSS_IsContentRow="1">
																											<td>
																												<input type="hidden" name="sysAttendCategoryForm.fdLimitLocations[0].fdLocations[${vstatus.index}].fdId" />
																												<div data-location-container="sysAttendCategoryForm.fdLimitLocations[0].fdLocations[${vstatus.index}].fdLocation" class="lui_location_container" style="width:97%;float:left;" mark-loaded="false"></div>
																												<span class="txtstrong">*</span>
																												<c:set var="LimitIndex"  value="0"></c:set>
																												<c:set var="fdRow" value="${vstatus.index}"></c:set>
																												<c:set var="_fdLocation" value="${fdLocationsItem.fdLocation}"></c:set>
																												<c:set var="_fdLocationCoordinate" value="${fdLocationsItem.fdLocationCoordinate}"></c:set>
																												<script>
																													seajs.use(['sys/attend/map/resource/js/LocationInit.js'],function(init){
																														init( {"id":null,"propertyName":"sysAttendCategoryForm.fdLimitLocations[${LimitIndex}].fdLocations[${fdRow}].fdLocation",
																															"propertyCoordinate":"fdLimitLocations[${LimitIndex}].fdLocations[${fdRow}].fdLocationCoordinate",
																															"nameValue":"${_fdLocation}","coordinateValue":"${_fdLocationCoordinate}",
																															"showStatus":"edit","subject":"签到点","required":true,"validators":"required","propertyDetail":null,"detailValue":null,
																															"placeholder":null,"radius":null,"propertyProvince":null,"provinceValue":null,"propertyDistrict":null,"districtValue":null,
																															"propertyCity":null,"cityValue":null,"isModify":null,"defaultValue":null} );
																													})
																												</script>
																												<a href="javascript:void(0);" onclick="deletePosition('child');" title="${lfn:message('doclist.delete')}">
																													<img src="${KMSS_Parameter_ContextPath}sys/attend/resource/images/delete_btn.png"/>
																												</a>
																											</td>
																										</tr>
																									</c:otherwise>
																								</c:choose>
																								<c:if test="${fdLocationsItem.fdLimitIndex!=sysAttendHisCategoryForm.sysAttendCategoryForm.fdLocations[vstatus.index+1].fdLimitIndex}">
																								<tr>
																									<td colspan="2">
																										<a href="javascript:void(0);" class="add-btn" onclick="addPosition('locationsList_${tableIndex}',this);" title="${lfn:message('doclist.add')}">
																												${ lfn:message('sys-attend:sysAttendCategory.new.location') }
																										</a>
																										<a href="javascript:void(0);" class="del-btn" onclick="deletePosition('locationsList_${tableIndex}');" title="${ lfn:message('sys-attend:sysAttendCategory.location.deleterow') }">
																												${ lfn:message('sys-attend:sysAttendCategory.location.deleterow') }
																										</a>
																									</td>
																								</tr>
																							</table>
																						</td>
																					</tr>
																				</table>
																			</td>
																		</tr>
																		</c:if>
																		</c:forEach>
																		<tr>
																			<td colspan="2">
																				<a href="javascript:void(0);" class="add-btn" onclick="addPosition('limitLocationsList');" title="${ lfn:message('sys-attend:sysAttendCategory.location.addrow') }">
																						${ lfn:message('sys-attend:sysAttendCategory.location.addrow') }
																				</a>
																			</td>
																		</tr>
																	</table>
										</div>
									</div>
								</div>
									<%-- WIfi签到 --%>
								<div class="lui-singin-creatPage-table">
									<div class="caption" style="padding-top: 15px;">${ lfn:message('sys-attend:sysAttendCategory.singin.wifi') }</div>
									<div class="content">
										<div class="lui-singin-creatPage-tab">
											<table class="tb_simple" width="100%">
												<tr>
													<td>
														<ui:switch property="sysAttendCategoryForm.fdCanWifi" onValueChange="onWifiChange();" checked="${sysAttendCategory.fdCanWifi == '0'?false:true}">
														</ui:switch>
													</td>
												</tr>
												<tr id="fdWifiTR">
													<td colspan="2">
														<table id="wifiConfigs" class="tb_simple" width="100%">
															<tr KMSS_IsReferRow="1" style="display:none">
																<td>
																	<input type="hidden" name="fdWifiConfigs[!{index}].fdId" />
																	<xform:text property="sysAttendCategoryForm.fdWifiConfigs[!{index}].fdName" htmlElementProperties="placeholder='${ lfn:message('sys-attend:sysAttendCategory.fdWifiConfigs.fdName.placeholder') }' data-wifi-config"
																				validators="required maxLength(30)" subject="${ lfn:message('sys-attend:sysAttendCategory.fdWifiConfigs.fdName') }"
																				style="width:35%; margin-right:10px;"></xform:text>
																	<xform:text property="sysAttendCategoryForm.fdWifiConfigs[!{index}].fdMacIp" htmlElementProperties="placeholder='${ lfn:message('sys-attend:sysAttendCategory.fdWifiConfigs.fdMacIp.placeholder') }'"
																				validators="required macIp checkMacIp" subject="${ lfn:message('sys-attend:sysAttendCategory.fdWifiConfigs.fdMacIp') }"
																				style="width:58%;"></xform:text>
																	<span class="txtstrong">*</span>
																	<a href="javascript:void(0);" onclick="deletePosition();" title="${lfn:message('doclist.delete')}">
																		<img src="${KMSS_Parameter_ContextPath}sys/attend/resource/images/delete_btn.png"/>
																	</a>
																</td>
															</tr>
															<c:forEach items="${sysAttendHisCategoryForm.sysAttendCategoryForm.fdWifiConfigs}" var="fdWifi" varStatus="vstatus">
																<tr KMSS_IsContentRow="1">
																	<input type="hidden" name="fdWifiConfigs[${vstatus.index}].fdId" value="${fdWifi.fdId}" />
																	 <td>
																		<xform:text property="sysAttendCategoryForm.fdWifiConfigs[${vstatus.index}].fdName" htmlElementProperties="placeholder='${ lfn:message('sys-attend:sysAttendCategory.fdWifiConfigs.fdName.placeholder') }' data-wifi-config"
																					validators="required maxLength(30)" subject="${ lfn:message('sys-attend:sysAttendCategory.fdWifiConfigs.fdName') }"
																					style="width:35%; margin-right:10px;"></xform:text>
																		<xform:text property="sysAttendCategoryForm.fdWifiConfigs[${vstatus.index}].fdMacIp" htmlElementProperties="placeholder='${ lfn:message('sys-attend:sysAttendCategory.fdWifiConfigs.fdMacIp.placeholder') }'"
																					validators="required macIp checkMacIp" subject="${ lfn:message('sys-attend:sysAttendCategory.fdWifiConfigs.fdMacIp') }"
																					style="width:58%;"></xform:text>
																		<span class="txtstrong">*</span>
																		<a href="javascript:void(0);" onclick="deletePosition();" title="${lfn:message('doclist.delete')}">
																			<img src="${KMSS_Parameter_ContextPath}sys/attend/resource/images/delete_btn.png"/>
																		</a>
																	</td>
																</tr>
															</c:forEach>
															<tr>
																<td>
																	<a href="javascript:void(0);" class="add-btn" onclick="addPosition('wifiConfigs');" title="${lfn:message('doclist.add')}">
																			${ lfn:message('sys-attend:sysAttendCategory.new.wifi') }
																	</a>
																	<a href="javascript:void(0);" class="add-btn" style="margin-left:20px;" onclick="addBatchPosition('wifiConfigs');" title="${lfn:message('doclist.add')}">
																			${ lfn:message('sys-attend:sysAttendCategory.import.wifi.btn') }
																	</a>
																	<span class="comment-text">${ lfn:message('sys-attend:sysAttendCategory.new.wifi.tips') }</span>
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>

							<%--规则设置 --%>
						<div class="lui-singin-creatPage-panel">
							<div class="lui-singin-creatPage-panel-heading">
								<h2 class="lui-singin-creatPage-panel-heading-title"><span id="ruleTitle">${ lfn:message('sys-attend:sysAttendCategory.rule.setting.title') }</span></h2>
							</div>
							<div class="lui-singin-creatPage-panel-body" id="ruleBody">
									<%-- 外勤设置  --%>
								<div class="lui-singin-creatPage-table" id='outsideBody'>
									<div class="caption" style="padding-top: 17px;">
											${ lfn:message('sys-attend:sysAttendMain.fdOutside') }
									</div>
									<div class="content">
										<table class="tb_simple" width="100%">
											<tr>
												<td>
													<html:hidden property="sysAttendCategoryForm.fdRule[0].fdOutside" />
													<ui:switch id="fdOutsideWgt" property="__fdOutside" enabledText="${ lfn:message('sys-attend:sysAttendCategoryRule.fdOutside.allowOrNot') }"
															   disabledText="${ lfn:message('sys-attend:sysAttendCategoryRule.fdOutside.notAllow') }" onValueChange="changeFdOutside();"
															   checked="${sysAttendHisCategoryForm.sysAttendCategoryForm.fdRule[0].fdOutside }">
													</ui:switch>
												</td>
											</tr>
											<tr id='osdReviewType' style="<c:if test="${sysAttendHisCategoryForm.sysAttendCategoryForm.fdRule[0].fdOutside != 'true' }">display:none</c:if>">
												<td>
													<xform:radio property="sysAttendCategoryForm.fdOsdReviewType" alignment="V">
														<xform:simpleDataSource value="0">
															${ lfn:message('sys-attend:sysAttendCategory.fdOsdReviewType.noReview') }
															<span style="margin-left: 12px;color: #999">${ lfn:message('sys-attend:sysAttendCategory.fdOsdReviewType.noReview.tips') }</span>
														</xform:simpleDataSource>
														<xform:simpleDataSource value="1">
															${ lfn:message('sys-attend:sysAttendCategory.fdOsdReviewType.review') }
															<span style="margin-left: 12px;color: #999">${ lfn:message('sys-attend:sysAttendCategory.fdOsdReviewType.review.tips') }</span>
														</xform:simpleDataSource>
													</xform:radio>
													<br/>
													<xform:checkbox property="sysAttendCategoryForm.fdOsdReviewIsUpload">
														<xform:simpleDataSource value="1">
															${ lfn:message('sys-attend:sysAttendCategory.fdOsdReviewType.addphoto') }
															<span style="margin-left: 12px;color: #999">${ lfn:message('sys-attend:sysAttendCategory.fdOsdReviewType.photoTip') }</span>
														</xform:simpleDataSource>
													</xform:checkbox>
												</td>
											</tr>
										</table>
									</div>
								</div>
									<%-- 弹性考勤 --%>
								<div class="lui-singin-creatPage-table" id="flexContent">
									<div class="caption" style="padding-top: 17px;">
											${ lfn:message('sys-attend:sysAttendCategory.fdIsFlex') }
									</div>
									<div class="content">
										<table class="tb_simple" width="100%">
											<tr>
												<td width="15%" valign="top">
													<ui:switch property="sysAttendCategoryForm.fdIsFlex" disabledText="${ lfn:message('sys-attend:sysAttendCategory.fdIsFlex.close') }"  enabledText="${ lfn:message('sys-attend:sysAttendCategory.fdIsFlex.open') }" onValueChange="changeIsFlex()"></ui:switch>
												</td>
												<td id='flexTimeTd' valign="top">
														${ lfn:message('sys-attend:sysAttendCategory.fdFlexTime.tips') }
													<xform:text property="sysAttendCategoryForm.fdFlexTime" style="width:50px;margin:0 8px;float:none;display:inline-block;" validators="required digits flexTimeVld" subject="${ lfn:message('sys-attend:sysAttendCategory.fdFlexTime') }"></xform:text>
														${ lfn:message('sys-attend:sysAttendCategoryRule.minute') }
												</td>
											</tr>
											<tr>
												<td colspan="2">
													<span class="comment-text">${ lfn:message('sys-attend:sysAttendCategory.fdIsFlex.tips') }</span>
												</td>
											</tr>
										</table>
									</div>
								</div>
									<%-- 迟到设置  --%>
								<div class="lui-singin-creatPage-table" id='lateBody'>
									<div class="caption" style="padding-top: 17px;">
											${ lfn:message('sys-attend:sysAttendCategoryRule.fdLateTime') }
									</div>
									<div class="content">
										<table class="tb_simple" width="100%">
											<tr>
												<td>
														${ lfn:message('sys-attend:sysAttendCategoryRule.fdLateTime.afterWork') }
													<xform:text property="sysAttendCategoryForm.fdRule[0].fdLateTime" style="width:50px;margin:0 3px;float:none;display:inline-block;" validators="digits maxLate" value='${fdLateTime }'></xform:text>
														${ lfn:message('sys-attend:sysAttendCategoryRule.minute') }
												</td>
											</tr>
										</table>
									</div>
								</div>
									<%-- 早退设置  --%>
								<div class="lui-singin-creatPage-table" id='leftBody'>
									<div class="caption" style="padding-top: 17px;">
											${ lfn:message('sys-attend:sysAttendCategoryRule.fdLeftTime') }
									</div>
									<div class="content">
										<table class="tb_simple" width="100%">
											<tr>
												<td>
														${ lfn:message('sys-attend:sysAttendCategoryRule.fdLeftTime.beforeOff') }
													<xform:text property="sysAttendCategoryForm.fdRule[0].fdLeftTime" style="width:50px;margin:0 3px;float:none;display:inline-block;" validators="digits maxLeft" value='${fdLeftTime }'></xform:text>
														${ lfn:message('sys-attend:sysAttendCategoryRule.minute') }
												</td>
											</tr>
										</table>
									</div>
								</div>
									<%-- 旷工设置  --%>
								<div class="lui-singin-creatPage-table" id='absentBody'>
									<div class="caption" style="padding-top: 17px;">
											${ lfn:message('sys-attend:sysAttendCategory.absence') }
									</div>
									<div class="content">
										<table class="tb_simple" width="100%">
											<tr>
												<td>
														${ lfn:message('sys-attend:sysAttendCategory.fdLateToAbsentTime.over') }
													<xform:text property="sysAttendCategoryForm.fdLateToAbsentTime" style="width:50px;margin:0 3px;float:none;display:inline-block;" validators="digits maxLate minLateToAbs"></xform:text>
														${ lfn:message('sys-attend:sysAttendCategoryRule.minute') }
													&nbsp;
													&nbsp;
														${ lfn:message('sys-attend:sysAttendCategory.fdLeftToAbsentTime.over') }
													<xform:text property="sysAttendCategoryForm.fdLeftToAbsentTime" style="width:50px;margin:0 3px;float:none;display:inline-block;" validators="digits maxLeft minLeftToAbs"></xform:text>
														${ lfn:message('sys-attend:sysAttendCategoryRule.minute') }
														${ lfn:message('sys-attend:sysAttendCategory.half.absence') }
													&nbsp;
													<div id="absFullBody" style="display: inline-block;">
															${ lfn:message('sys-attend:sysAttendCategory.fdLateToAbsentTime.over') }
														<xform:text property="sysAttendCategoryForm.fdLateToFullAbsTime" style="width:50px;margin:0 3px;float:none;display:inline-block;" validators="digits maxLate fullLateAbscent minLateToAbs"></xform:text>
															${ lfn:message('sys-attend:sysAttendCategoryRule.minute') }
														&nbsp;
														&nbsp;
															${ lfn:message('sys-attend:sysAttendCategory.fdLeftToAbsentTime.over') }
														<xform:text property="sysAttendCategoryForm.fdLeftToFullAbsTime" style="width:50px;margin:0 3px;float:none;display:inline-block;" validators="digits maxLeft fullLeftAbscent minLeftToAbs"></xform:text>
															${ lfn:message('sys-attend:sysAttendCategoryRule.minute') }
															${ lfn:message('sys-attend:sysAttendCategory.full.absence') }
													</div>
												</td>
											</tr>
											<tr>
												<td>
				    							<span class="comment-text">
														${ lfn:message('sys-attend:sysAttendCategory.absence.tips') }
												</span>
												</td>
											</tr>
										</table>
									</div>
								</div>
									<%-- 加班设置 --%>
								<div class="lui-singin-creatPage-table">
									<div class="caption" style="padding-top: 17px;">
											${ lfn:message('sys-attend:sysAttendCategory.overTime.title') }
									</div>
									<div class="content">
										<table class="tb_simple" width="100%">
											<tr>
												<td>
													<ui:switch property="sysAttendCategoryForm.fdIsOvertime" enabledText="${ lfn:message('sys-attend:sysAttendCategory.fdIsOvertime') }" disabledText="${ lfn:message('sys-attend:sysAttendCategory.fdIsOvertime.not') }" onValueChange="changefdIsOvt()"></ui:switch>
													<div id="ovtMinHour" style="display: inline-block;padding-top: 17px;">
														<span>${ lfn:message('sys-attend:sysAttendCategory.overtime.atLeast') }</span>
														<xform:text property="sysAttendCategoryForm.fdMinOverTime" validators="min(0) max(1440)" required="true" style="height: 22px;width:40px;margin: 0 3px;float:none;" subject="${ lfn:message('sys-attend:sysAttendCategory.overtime.atLeast') }"></xform:text>
														<span>${ lfn:message('sys-attend:sysAttendCategory.overtime.hour') }</span>
														<br/>
                                                        <span>${ lfn:message('sys-attend:sysAttendCategory.convert.overTime') }</span>
                                                        <xform:text property="sysAttendCategoryForm.fdConvertOverTimeHour" validators="min(1) max(24)" required="true" style="height: 22px;width:40px;margin: 0 3px;float:none;" ></xform:text>
                                                        <span>${ lfn:message('sys-attend:sysAttendCategory.convert.overTime.toDay') }</span>

													</div>
												</td>
											</tr>
											<tr>
												<td>
													<span id="ovtMinHourTips" class="comment-text">${ lfn:message('sys-attend:sysAttendCategory.overtime.tips') }
													<br/>${ lfn:message('sys-attend:sysAttendCategory.overtime.tips2') }</span>
												</td>
											</tr>
											<tr id="ovtReview">
												<td>
													<xform:radio property="sysAttendCategoryForm.fdOvtReviewType" title="${ lfn:message('sys-attend:sysAttendCategory.fdOvtReviewType') }"  alignment="V">
														<xform:simpleDataSource value="0">
															${ lfn:message('sys-attend:sysAttendCategory.fdOvtReviewType.noReview') }
														</xform:simpleDataSource>
														<xform:simpleDataSource value="1">${ lfn:message('sys-attend:sysAttendCategory.fdOvtReviewType.review') }</xform:simpleDataSource>
														<xform:simpleDataSource value="2">${ lfn:message('sys-attend:sysAttendCategory.fdOvtReviewType.sign') }</xform:simpleDataSource>
														<xform:simpleDataSource value="3">${ lfn:message('sys-attend:sysAttendCategory.fdOvtReviewType.approval') }</xform:simpleDataSource>
													</xform:radio>
												</td>
											</tr>
										</table>
										<!-- 扣除加班工时 -->
										<table class="tb_simple" width="100%" id="deductTable">
											<tr >
												<td>
													<ui:switch property="sysAttendCategoryForm.fdBeforeWorkOverTime"
															   enabledText="${ lfn:message('sys-attend:sysAttendCategory.fdBeforeWorkOverTime.yes') }"
															   disabledText="${ lfn:message('sys-attend:sysAttendCategory.fdBeforeWorkOverTime.no') }" ></ui:switch>
													<span class="comment-text">${ lfn:message('sys-attend:sysAttendCategory.fdBeforeWorkOverTime.memo') }</span>
													<span class="comment-text">${ lfn:message('sys-attend:sysAttendCategory.fdBeforeWorkOverTime.memo1') }</span>
													<span class="comment-text">${ lfn:message('sys-attend:sysAttendCategory.fdBeforeWorkOverTime.memo2') }</span>

												</td>
											</tr>
											<tr >
												<td>
													<ui:switch property="sysAttendCategoryForm.fdIsCalculateOvertime"
															   enabledText="${ lfn:message('sys-attend:sysAttendCategory.fdIsCalculateOvertime.yes') }"
															   disabledText="${ lfn:message('sys-attend:sysAttendCategory.fdIsCalculateOvertime.no') }" ></ui:switch>
													<span class="comment-text">${ lfn:message('sys-attend:sysAttendCategory.fdIsCalculateOvertime.memo') }</span>
													<span class="comment-text">${ lfn:message('sys-attend:sysAttendCategory.fdIsCalculateOvertime.memo1') }</span>
												</td>
											</tr>
											<tr id="deductSwitch">
												<td>
													<ui:switch property="sysAttendCategoryForm.fdIsOvertimeDeduct" enabledText="${ lfn:message('sys-attend:sysAttendCategory.fdIsOvertimeDeduct') }" disabledText="${ lfn:message('sys-attend:sysAttendCategory.fdIsOvertimeDeduct.not') }" onValueChange="changefdIsOvtDeduct()"></ui:switch>
												</td>
											</tr>
											<tr id="deductTips" style="display:none">
												<td>
						    					<span class="comment-text">
						    						<!-- <div class="lui_icon_s lui_icon_s_icon_question_sign"></div> -->
<%--						    						${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.tips') }--%>
						    					</span>
												</td>
											</tr>
											<tr id="ovtDeduct">
												<td>
													<xform:radio property="sysAttendCategoryForm.fdOvtDeductType" title="${ lfn:message('sys-attend:sysAttendCategory.fdOvtReviewType') }"  onValueChange="changefdOvtDeductType" alignment="H">
														<xform:simpleDataSource value="0">
															${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.periods') }
														</xform:simpleDataSource>
														<xform:simpleDataSource value="1">
															${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.threashold') }
														</xform:simpleDataSource>
													</xform:radio>

												</td>
											</tr>
											<tr id="timePeriod" style="display:none">
												<td colspan="2">
													<table id="overtimeDeducts" class="tb_simple" width="100%">
														<tr KMSS_IsReferRow="1" style="display:none">
															<td>
																<input type="hidden" name="sysAttendCategoryForm.overtimeDeducts[!{index}].fdId" />
																<xform:datetime minuteStep="1" property="sysAttendCategoryForm.overtimeDeducts[!{index}].fdStartTime" required="true" dateTimeType="time" validators="startCompareEnd timeRangeMixed" style="width:100px"
																				htmlElementProperties="over-time-deducts" subject="${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.fdStartTime') }"></xform:datetime>
																<span style="float: left;margin:0 20px;">—</span>
																<xform:datetime minuteStep="1" property="sysAttendCategoryForm.overtimeDeducts[!{index}].fdEndTime" required="true" dateTimeType="time" validators="startCompareEnd2 timeRangeMixed" style="width:100px" subject="${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.fdEndTime') }"></xform:datetime>
																<a href="javascript:void(0);" onclick="deleteOvtTimePosition();" title="${lfn:message('doclist.delete')}">
																	<div class="lui_icon_s lui_icon_s_icon_minus_sign"></div>
																</a>
																<a href="javascript:void(0);" class="add-btn" onclick="addPosition('overtimeDeducts');" title="${lfn:message('doclist.add')}">
																	<div class="lui_icon_s lui_icon_s_icon_plus_sign"></div>
																</a>
															</td>
														</tr>
														<c:forEach items="${sysAttendHisCategoryForm.sysAttendCategoryForm.overtimeDeducts}" var="overtimeDeduct" varStatus="vstatus">
															<tr KMSS_IsContentRow="1" class="timePeriod">
																<input type="hidden" name="sysAttendCategoryForm.overtimeDeducts[${vstatus.index}].fdId" value="${overtimeDeduct.fdId}" />
																<td>
																	<xform:datetime minuteStep="1" property="sysAttendCategoryForm.overtimeDeducts[${vstatus.index}].fdStartTime" required="true" dateTimeType="time" validators="startCompareEnd timeRangeMixed" style="width:100px"
																					htmlElementProperties="over-time-deducts" subject="${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.fdStartTime') }"></xform:datetime>
																	<span style="float: left;margin:0 20px;">—</span>
																	<xform:datetime minuteStep="1" property="sysAttendCategoryForm.overtimeDeducts[${vstatus.index}].fdEndTime" required="true" dateTimeType="time" validators="startCompareEnd2 timeRangeMixed" style="width:100px" subject="${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.fdEndTime') }"></xform:datetime>
																	<a href="javascript:void(0);" onclick="deleteOvtTimePosition();" title="${lfn:message('doclist.delete')}">
																		<div class="lui_icon_s lui_icon_s_icon_minus_sign"></div>
																	</a>
																	<a href="javascript:void(0);" class="add-btn" onclick="addPosition('overtimeDeducts');" title="${lfn:message('doclist.add')}">
																		<div class="lui_icon_s lui_icon_s_icon_plus_sign"></div>
																	</a>
																</td>
															</tr>
														</c:forEach>
													</table>
												</td>
											</tr>
											<tr id="timethreshold" style="display:none">
												<td colspan="2">
													<div style="display: inline-block;">
															${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.everyDayThreshold') }
														<xform:text property="sysAttendCategoryForm.overtimeDeducts[0].fdThreshold" validators="digits min(1) thresholdBiggerHours" required="true" style="height: 22px;width:40px;margin: 0 3px;float:none;" subject="${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.thresholdSubjct') }"></xform:text>
															${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.deductTips') }
														<xform:text property="sysAttendCategoryForm.overtimeDeducts[0].fdDeductHours" validators="digits min(1) thresholdBiggerHours2" required="true" style="height: 22px;width:40px;margin: 0 3px;float:none;" subject="${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.hoursSubject') }"></xform:text>
															${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.deductHours') }
													</div>
												</td>
											</tr>
										</table>
										<!-- 取整规则 -->
										<table class="tb_simple" width="100%" id="roundingRules">
											<tr>
												<td>
													<div style="display: inline-block;">
														<span>${ lfn:message('sys-attend:sysAttendCategory.fdRoundingType') }</span>
														<xform:select showStatus="${fdRoundingType}" property="sysAttendCategoryForm.fdRoundingType" style="float: none;" showPleaseSelect="false" onValueChange="changeRoundingType">
															<xform:simpleDataSource value="0">${ lfn:message('sys-attend:sysAttendCategory.fdRoundingType.no') }</xform:simpleDataSource>
															<xform:simpleDataSource value="1">${ lfn:message('sys-attend:sysAttendCategory.fdRoundingType.upper') }</xform:simpleDataSource>
															<xform:simpleDataSource value="2">${ lfn:message('sys-attend:sysAttendCategory.fdRoundingType.lower') }</xform:simpleDataSource>
														</xform:select>
														&nbsp;&nbsp;
														<span id="overtimeHour" style="display:none;">
				   										<span>${ lfn:message('sys-attend:sysAttendCategory.overtime.unit') }</span>
			   											<xform:text property="sysAttendCategoryForm.fdMinUnitHour" validators="minUnitHour max(24)" style="height: 22px;width:40px;margin: 0 3px;float:none;" subject="${ lfn:message('sys-attend:sysAttendCategory.overtime.unit') }"></xform:text>
			   											<span class="txtstrong">*</span>
			   											<span>${ lfn:message('sys-attend:sysAttendCategory.overtime.unit.hour') }</span>
		   											</span>
													</div>
												</td>
											</tr>
										</table>
									</div>
								</div>
									<%-- 补卡 --%>
								<div class="lui-singin-creatPage-table">
									<div class="caption" style="padding-top: 17px;">
											${ lfn:message('sys-attend:sysAttendCategory.fdIsPatch') }
									</div>
									<div class="content">
										<table class="tb_simple" width="100%">
											<tr>
												<td>
													<ui:switch property="sysAttendCategoryForm.fdIsPatch" enabledText="${ lfn:message('sys-attend:sysAttendCategory.fdIsPatch.yes') }" disabledText="${ lfn:message('sys-attend:sysAttendCategory.fdIsPatch.no') }" onValueChange="changeIsPatch();"></ui:switch>
												</td>
											</tr>
											<tr class="patchContent">
												<td>
														${ lfn:message('sys-attend:sysAttendCategory.fdPatchTimes.text1') }
													<xform:text property="sysAttendCategoryForm.fdPatchTimes" style="width:50px;margin:0 3px;float:none;display:inline-block;" validators="digits min(1) max(99)"></xform:text>
														${ lfn:message('sys-attend:sysAttendCategory.fdPatchTimes.text2') }
													<span style="color: #999;">${ lfn:message('sys-attend:sysAttendCategory.fdPatchTimes.tips') }</span>
												</td>
											</tr>
											<tr class="patchContent">
												<td>
														${ lfn:message('sys-attend:sysAttendCategory.fdPatchDay.text1') }
													<xform:text property="sysAttendCategoryForm.fdPatchDay" style="width:50px;margin:0 3px;float:none;display:inline-block;" validators="digits min(0) max(180)"></xform:text>
														${ lfn:message('sys-attend:sysAttendCategory.fdPatchDay.text2') }
													<span style="color: #999;">${ lfn:message('sys-attend:sysAttendCategory.fdPatchDay.tips') }</span>
												</td>
											</tr>
										</table>
									</div>
								</div>
							</div>
						</div>

							<%--关联流程 --%>
						<div class="lui-singin-creatPage-panel">
							<div class="lui-singin-creatPage-panel-heading">
								<h2 class="lui-singin-creatPage-panel-heading-title"><span id="signOffTitle">${ lfn:message('sys-attend:sysAttendCategory.business.title') }</span></h2>
							</div>
							<div class="lui-singin-creatPage-panel-body" id="signOffBody">
								<table id="busSettingList" class="tb_normal" width="100%" style="text-align: center;">
									<tr>
										<td class="td_normal_title" width="20%">
												${ lfn:message('sys-attend:sysAttendCategory.business.name') }
										</td>
										<td class="td_normal_title" width="20%">
												${ lfn:message('sys-attend:sysAttendCategory.business.type') }
										</td>
										<td class="td_normal_title" width="50%">
												${ lfn:message('sys-attend:sysAttendCategory.business.template') }
										</td>
										<td class="td_normal_title" width="10%">
											<div class="lui-attend-bus-setting-add" onclick="DocList_AddRow('busSettingList');">
											</div>
										</td>
									</tr>
									<tr KMSS_IsReferRow="1" style="display:none">
										<td>
											<input type="hidden" name="busSettingForms[!{index}].fdId" />
											<xform:text property="sysAttendCategoryForm.busSettingForms[!{index}].fdBusName" required="true" subject="${ lfn:message('sys-attend:sysAttendCategory.business.name') }"></xform:text>
										</td>
										<td>
											<xform:select property="sysAttendCategoryForm.busSettingForms[!{index}].fdBusType" showPleaseSelect="true" required="true" subject="${ lfn:message('sys-attend:sysAttendCategory.business.type') }">
												<xform:enumsDataSource enumsType="sysAttendCategoryBus_fdBusType"></xform:enumsDataSource>
											</xform:select>
										</td>
										<td>
											<xform:dialog required="true"
														  propertyId="sysAttendCategoryForm.busSettingForms[!{index}].fdTemplateId"
														  propertyName="sysAttendCategoryForm.busSettingForms[!{index}].fdTemplateName" showStatus="edit" style="width:80%" className="inputsgl" subject="${ lfn:message('sys-attend:sysAttendCategory.business.template') }">
												selectTemplate('sysAttendCategoryForm.busSettingForms[!{index}].fdTemplateId', 'sysAttendCategoryForm.busSettingForms[!{index}].fdTemplateName');
											</xform:dialog>
										</td>
										<td>
											<div class="lui-attend-bus-setting-delete" onclick="DocList_DeleteRow();">
											</div>
										</td>
									</tr>
									<c:forEach items="${sysAttendHisCategoryForm.sysAttendCategoryForm.busSettingForms}" var="busSettingItem" varStatus="vstatus">
										<tr KMSS_IsContentRow="1">
											<input type="hidden" name="sysAttendCategoryForm.busSettingForms[${vstatus.index }].fdId" value="${busSettingItem.fdId }" />
											<td>
												<xform:text property="sysAttendCategoryForm.busSettingForms[${vstatus.index }].fdBusName" required="true" subject="${ lfn:message('sys-attend:sysAttendCategory.business.name') }"></xform:text>
											</td>
											<td>
												<xform:select property="sysAttendCategoryForm.busSettingForms[${vstatus.index }].fdBusType" showPleaseSelect="true" required="true" subject="${ lfn:message('sys-attend:sysAttendCategory.business.type') }">
													<xform:enumsDataSource enumsType="sysAttendCategoryBus_fdBusType"></xform:enumsDataSource>
												</xform:select>
											</td>
											<td>
												<xform:dialog required="true"
															  propertyId="sysAttendCategoryForm.busSettingForms[${vstatus.index }].fdTemplateId"
															  propertyName="sysAttendCategoryForm.busSettingForms[${vstatus.index }].fdTemplateName"
															  showStatus="edit" style="width:80%" className="inputsgl" subject="${ lfn:message('sys-attend:sysAttendCategory.business.template') }">
													selectTemplate('busSettingForms[${vstatus.index }].fdTemplateId', 'busSettingForms[${vstatus.index }].fdTemplateName');
												</xform:dialog>
											</td>
											<td>
												<div class="lui-attend-bus-setting-delete" onclick="DocList_DeleteRow();">
												</div>
											</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>

						<div class="lui-singin-creatPage-panel">
							<div class="lui-singin-creatPage-panel-heading">
								<h2 class="lui-singin-creatPage-panel-heading-title"><span id="signNotifyTitle">${ lfn:message('sys-attend:sysAttendCategory.notify.title') }</span></h2>
							</div>
							<div class="lui-singin-creatPage-panel-body" id="signNotifyBody">
								<table class="tb_simple" width="100%">
									<tr>
										<td class="td_normal_title">
												${ lfn:message('sys-attend:sysAttendCategory.notify.on.title') }
										</td>
										<td>
											<xform:text property="sysAttendCategoryForm.fdNotifyOnTime" validators="digits min(1) max(1000)" style="width:50px;margin-right:8px;"></xform:text>${ lfn:message('sys-attend:sysAttendCategory.notify.on.tips') }
										</td>
									</tr>
									<tr>
										<td class="td_normal_title">
												${ lfn:message('sys-attend:sysAttendCategory.notify.off.title') }
										</td>
										<td>
											<xform:text property="sysAttendCategoryForm.fdNotifyOffTime" validators="digits min(1) max(1000)" style="width:50px;margin-right:8px;"></xform:text>${ lfn:message('sys-attend:sysAttendCategory.notify.off.tips') }
										</td>
									</tr>
									<tr>
										<td class="td_normal_title">
												${ lfn:message('sys-attend:sysAttendCategory.notify.result') }
										</td>
										<td>
											<xform:checkbox property="sysAttendCategoryForm.fdNotifyResult" style="margin-right: 5px">
												<xform:simpleDataSource value="true">${ lfn:message('sys-attend:sysAttendCategory.notify.result.tips') }</xform:simpleDataSource>
											</xform:checkbox>
										</td>
									</tr>
									<tr>
										<td class="td_normal_title">
												${ lfn:message('sys-attend:sysAttendCategory.fdNotifyAttend') }
										</td>
										<td>
											<xform:checkbox property="sysAttendCategoryForm.fdNotifyAttend" style="margin-right: 5px">
												<xform:simpleDataSource value="true">${ lfn:message('sys-attend:sysAttendCategory.fdNotifyAttend.tips') }</xform:simpleDataSource>
											</xform:checkbox>
										</td>
									</tr>
								</table>
							</div>
						</div>
						<!-- 安全验证 -->
						<c:if test="${isEnableKKConfig eq true }">
							<div class="lui-singin-creatPage-panel">
								<div class="lui-singin-creatPage-panel-heading">
									<h2 class="lui-singin-creatPage-panel-heading-title"><span id="securityTitle">${ lfn:message('sys-attend:sysAttendCategory.securityMode.title') }</span></h2>
								</div>
								<div class="lui-singin-creatPage-panel-body" id="securityBody">
									<table class="tb_simple" width="100%">
										<tr>
											<td class="td_normal_title" style="vertical-align: top;">
													${ lfn:message('sys-attend:sysAttendCategory.fdSecurityMode') }
											</td>
											<td>
												<xform:radio mock="true" property="sysAttendCategoryForm.fdSecurityMode" showStatus="edit" onValueChange="onSecurityMode">
													<xform:simpleDataSource value="camera" >${ lfn:message('sys-attend:sysAttendCategory.fdSecurityMode.camera') }</xform:simpleDataSource>
													<xform:simpleDataSource value="face" >${ lfn:message('sys-attend:sysAttendCategory.fdSecurityMode.face') }</xform:simpleDataSource>
												</xform:radio>
												<div class="lui_attend_security_tip">${ lfn:message('sys-attend:sysAttendCategory.fdSecurityMode.tip') }</div>
											</td>
										</tr>
									</table>
								</div>
							</div>
						</c:if>

					</div>
				</div>

			</div>
			<div id="locationsTempHiddenDiv" style="display: none"></div>
		</html:form>
	</template:replace>
</template:include>
<%@ include file="/sys/attend/sys_attend_his_category/sysAttendHisCategory_edit_attend_script.jsp"%>
<script>
	LUI.ready(function(){
		// 判断是一班制还是两班制
		<c:if test="${sysAttendHisCategoryForm.sysAttendCategoryForm.fdWork==2}">
		hideAndDisabled('absFullBody');
		</c:if>
		<c:if test="${sysAttendHisCategoryForm.sysAttendCategoryForm.fdWork==1}">
		showAndAbled('absFullBody');
		</c:if>

		// 判断加班是否为不取整
		<c:if test="${sysAttendHisCategoryForm.sysAttendCategoryForm.fdRoundingType eq '1'|| sysAttendHisCategoryForm.sysAttendCategoryForm.fdRoundingType eq '2'}">
		showAndAbled('overtimeHour');
		</c:if>
		//编辑时注册on事件
		setTimeout(function(){
			$("#limitLocationsList table[id*='locationsList_']").each(function(){
				if(this.id.indexOf("index")==-1){
					//添加范围时给地点的table添加'添加地点'的on事件
					$("#"+this.id).on('table-add-child',function(e,row){
						var optTB = DocListFunc_GetParentByTagName("TABLE", row);
						pidx=optTB.id.replace("locationsList_","");
						var idx = row.rowIndex;//从1开始，第一个tr已使用
						$(row).find('[data-location-container="fdLimitLocations['+ pidx +'].fdLocations['+ idx +'].fdLocation"]').empty();
						var options ='{"id":"fdLimitLocations['+ pidx +'].fdLocations['+ idx +'].fdLocation","propertyName":"fdLimitLocations['+ pidx +'].fdLocations['+ idx +'].fdLocation","propertyCoordinate":"fdLimitLocations['+ pidx +'].fdLocations['+ idx +'].fdLocationCoordinate","nameValue":"","coordinateValue":"","showStatus":"edit","style":"width:97%;float:left;","subject":"${ lfn:message("sys-attend:sysAttendCategory.fdLocations") }","required":true,"validators":"required"}';
						seajs.use(['sys/attend/map/resource/js/LocationInit.js'],function(init){
							init(JSON.parse(options));
						});
					});
					// 及删除地点的事件
					$("#"+this.id).on('table-delete-child',function(evt, TBid){
						if(TBid) {
							pidx=TBid.replace("locationsList_","");
							var tbInfo = DocList_TableInfo["locationsList_"+pidx];
							for(var i = 0; i<tbInfo.lastIndex; i++) {
								var optTR = tbInfo.DOMElement.rows[i];
								var doms = optTR.getElementsByTagName('div');
								var fields = optTR.getElementsByTagName('INPUT');
								// 更新div属性中的序号
								for(var k=0; k<doms.length; k++){
									if($(doms[k]).attr("data-location-container")){
										var fieldValue = $(doms[k]).attr("data-location-container").replace(/fdLocations\[\d+\]/g, "fdLocations[!{indexChild}]");//.replace(/\.\d+\./g, "Locations.!{indexChild}.");
										fieldValue = fieldValue.replace(/!\{indexChild\}/g, i - tbInfo.firstIndex);
										$(doms[k]).attr("data-location-container", fieldValue);
									}
									if($(doms[k]).attr("data-lui-cid")){
										var fieldValue = $(doms[k]).attr("data-lui-cid").replace(/fdLocations\[\d+\]/g, "fdLocations[!{indexChild}]");//.replace(/\.\d+\./g, "Locations.!{indexChild}.");
										fieldValue = fieldValue.replace(/!\{indexChild\}/g, i - tbInfo.firstIndex);
										$(doms[k]).attr("data-lui-cid", fieldValue);
										$(doms[k]).attr("id", fieldValue);
									}
								}
								// 更新input属性中的序号
								for(var k=0; k<fields.length; k++){
									var fieldValue = $(fields[k]).attr("name").replace(/fdLocations\[\d+\]/g, "fdLocations[!{indexChild}]");
									fieldValue = fieldValue.replace(/!\{indexChild\}/g, i - tbInfo.firstIndex);
									$(fields[k]).attr("name", fieldValue);
								}
							}
						}
					});
				}
			})
		},800);
		// 地图组件在明细表中添加会多出一行，添加范围on事件
		$('#limitLocationsList').on('table-add',function(e,row){
			var pidx = row.rowIndex-1;//从1开始，第一个tr已使用
			//加一个范围时添加一个地点table
			DocList_Info.push("locationsList_"+pidx);
			DocListFunc_Init();//重载table数据
			//默认添加一行
			DocList_AddRow("locationsList_"+pidx,null,null,"child");
			//地图
			$(row).find('[data-location-container="fdLimitLocations['+ pidx +'].fdLocations[0].fdLocation"]').empty();
			var options ='{"id":"fdLimitLocations['+ pidx +'].fdLocations[0].fdLocation","propertyName":"fdLimitLocations['+ pidx +'].fdLocations[0].fdLocation","propertyCoordinate":"fdLimitLocations['+ pidx +'].fdLocations[0].fdLocationCoordinate","nameValue":"","coordinateValue":"","showStatus":"edit","style":"width:97%;float:left;","subject":"${ lfn:message("sys-attend:sysAttendCategory.fdLocations") }","required":true,"validators":"required"}';
			seajs.use(['sys/attend/map/resource/js/LocationInit.js'],function(init){
				init(JSON.parse(options));
			});
			setTimeout(function(){
				//添加范围时给地点的table添加'添加地点'的on事件
				//$("#locationsList_"+pidx).off('table-add-child');
				$("#locationsList_"+pidx).on('table-add-child',function(e,row){
					var optTB = DocListFunc_GetParentByTagName("TABLE", row);
					pidx=optTB.id.replace("locationsList_","");
					var idx = row.rowIndex;//从1开始，第一个tr已使用
					//alert(idx);
					$(row).find('[data-location-container="fdLimitLocations['+ pidx +'].fdLocations['+ idx +'].fdLocation"]').empty();
					var options ='{"id":"fdLimitLocations['+ pidx +'].fdLocations['+ idx +'].fdLocation","propertyName":"fdLimitLocations['+ pidx +'].fdLocations['+ idx +'].fdLocation","propertyCoordinate":"fdLimitLocations['+ pidx +'].fdLocations['+ idx +'].fdLocationCoordinate","nameValue":"","coordinateValue":"","showStatus":"edit","style":"width:97%;float:left;","subject":"${ lfn:message("sys-attend:sysAttendCategory.fdLocations") }","required":true,"validators":"required"}';
					seajs.use(['sys/attend/map/resource/js/LocationInit.js'],function(init){
						init(JSON.parse(options));
					});
				});
				// 及删除地点的事件
				//$("#locationsList_"+pidx).off('table-delete-child');
				$("#locationsList_"+pidx).on('table-delete-child',function(evt, TBid){
					if(TBid) {
						pidx=TBid.replace("locationsList_","");
						var tbInfo = DocList_TableInfo["locationsList_"+pidx];
						for(var i = 0; i<tbInfo.lastIndex; i++) {
							var optTR = tbInfo.DOMElement.rows[i];
							var doms = optTR.getElementsByTagName('div');
							var fields = optTR.getElementsByTagName('INPUT');
							// 更新div属性中的序号
							for(var k=0; k<doms.length; k++){
								if($(doms[k]).attr("data-location-container")){
									var fieldValue = $(doms[k]).attr("data-location-container").replace(/fdLocations\[\d+\]/g, "fdLocations[!{indexChild}]");//.replace(/\.\d+\./g, "Locations.!{indexChild}.");
									fieldValue = fieldValue.replace(/!\{indexChild\}/g, i - tbInfo.firstIndex);
									$(doms[k]).attr("data-location-container", fieldValue);
								}
								if($(doms[k]).attr("data-lui-cid")){
									var fieldValue = $(doms[k]).attr("data-lui-cid").replace(/fdLocations\[\d+\]/g, "fdLocations[!{indexChild}]");//.replace(/\.\d+\./g, "Locations.!{indexChild}.");
									fieldValue = fieldValue.replace(/!\{indexChild\}/g, i - tbInfo.firstIndex);
									$(doms[k]).attr("data-lui-cid", fieldValue);
									$(doms[k]).attr("id", fieldValue);
								}
							}
							// 更新input属性中的序号
							for(var k=0; k<fields.length; k++){
								var fieldValue = $(fields[k]).attr("name").replace(/fdLocations\[\d+\]/g, "fdLocations[!{indexChild}]");
								fieldValue = fieldValue.replace(/!\{indexChild\}/g, i - tbInfo.firstIndex);
								$(fields[k]).attr("name", fieldValue);
							}
						}
					}
				});
			},100);
		});

		// 删除范围地点
		// 地图组件在明细表中删除不更新下标
		$('#limitLocationsList').on('table-delete',function(evt, data){
			if(data) {
				var tbInfo = DocList_TableInfo["limitLocationsList"];
				for(var i = 1; i<tbInfo.lastIndex; i++) {
					var optTR = tbInfo.DOMElement.rows[i];
					var doms = optTR.getElementsByTagName('div');
					var fields = optTR.getElementsByTagName('INPUT');
					var tables = optTR.getElementsByTagName('TABLE');
					var links = optTR.getElementsByTagName('A');
					// 更新div属性中的序号
					for(var k=0; k<doms.length; k++){
						if($(doms[k]).attr("data-location-container")){
							var fieldValue = $(doms[k]).attr("data-location-container").replace(/fdLimitLocations\[\d+\]/g, "fdLimitLocations[!{index}]");//.replace(/\.\d+\./g, "Locations.!{indexChild}.");
							fieldValue = fieldValue.replace(/!\{index\}/g, i - tbInfo.firstIndex);
							$(doms[k]).attr("data-location-container", fieldValue);
						}
						if($(doms[k]).attr("data-lui-cid")){
							var fieldValue = $(doms[k]).attr("data-lui-cid").replace(/fdLimitLocations\[\d+\]/g, "fdLimitLocations[!{index}]");//.replace(/\.\d+\./g, "Locations.!{indexChild}.");
							fieldValue = fieldValue.replace(/!\{index\}/g, i - tbInfo.firstIndex);
							$(doms[k]).attr("data-lui-cid", fieldValue);
							$(doms[k]).attr("id", fieldValue);
						}
					}
					// 更新input属性中的序号
					for(var k=0; k<fields.length; k++){
						var fieldValue = $(fields[k]).attr("name").replace(/fdLimitLocations\[\d+\]/g, "fdLimitLocations[!{index}]");
						fieldValue = fieldValue.replace(/!\{index\}/g, i - tbInfo.firstIndex);
						$(fields[k]).attr("name", fieldValue);
					}
					//更新table的id
					for(var k=0; k<tables.length; k++){
						if($(tables[k]).attr("id")&&$(tables[k]).attr("id").indexOf("locationsList_")>-1){
							var fieldValue = $(tables[k]).attr("id").replace(/\d+/g, "!{index}");
							fieldValue = fieldValue.replace(/!\{index\}/g, i- tbInfo.firstIndex);
							$(tables[k]).attr("id", fieldValue);
						}
					}
					//更新a
					for(var ak=0; ak<links.length; ak++){
						var fieldValue = $(links[ak]).attr("onclick").replace(/\d+/g, "!{index}");
						fieldValue = fieldValue.replace(/!\{index\}/g, i - tbInfo.firstIndex);
						$(links[ak]).attr("onclick", fieldValue);
					}
				}
			}
		});
		// 工作时间设置明细表删除时，错误更新第二个下标，手动还原
		$('#wTimeSheet').on('table-delete-finish',function(evt, data){
			if(data) {
				var tbInfo = DocList_TableInfo['wTimeSheet'];
				for(var i = 0; i<tbInfo.lastIndex; i++) {
					var optTR = tbInfo.DOMElement.rows[i];
					$(optTR).find('[data-wtime-idx="0"]:hidden').each(function(){
						var fieldName = $(this).attr('name').replace(/fdWorkTime\[\d+\]/g, 'fdWorkTime[0]');
						$(this).attr('name', fieldName);
					});
					$(optTR).find('[data-wtime-idx="1"]:hidden').each(function(){
						var fieldName = $(this).attr('name').replace(/fdWorkTime\[\d+\]/g, 'fdWorkTime[1]');
						$(this).attr('name', fieldName);
					});
				}
			}
		});
		// 标题展开收起事件
		bindTitleEvent();
		// 一班制还是两班制
		initWorkType('${sysAttendHisCategoryForm.sysAttendCategoryForm.fdWork }');
		//初始化旷工规则
		initAbsentTime();
		// 班制类型
		initFdShiftType('${sysAttendHisCategoryForm.sysAttendCategoryForm.fdShiftType}','${sysAttendHisCategoryForm.sysAttendCategoryForm.fdSameWorkTime}');
		changeIsFlex('${sysAttendHisCategoryForm.sysAttendCategoryForm.fdIsFlex}');
		changeIsPatch();
		// 计算总工时
		<c:if test="${empty sysAttendHisCategoryForm.sysAttendCategoryForm.fdTotalTime}">
		calTotalTime();
		</c:if>
		setTimeout(function(){
			initWorkType();
			changefdIsOvt('${sysAttendHisCategoryForm.sysAttendCategoryForm.fdIsOvertime}');
			onChangePosCount();
			onMapChange(true);
			onWifiChange(true);
		}, 600);
	});

</script>