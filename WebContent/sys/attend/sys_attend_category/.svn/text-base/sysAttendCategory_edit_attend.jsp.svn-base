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
		<c:choose>
			<c:when test="${sysAttendCategoryForm.method_GET == 'add' }">
				<c:out value="${ lfn:message('operation.create') } - ${ lfn:message('sys-attend:module.sys.attend') }"></c:out>	
			</c:when>
			<c:otherwise>
				<c:out value="${ lfn:message('sys-attend:module.sys.attend') }"></c:out>
			</c:otherwise>
		</c:choose>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3"> 
			<c:choose>
				<c:when test="${ sysAttendCategoryForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" onclick="onSubmitMethod('update')"></ui:button>
				</c:when>
				<c:when test="${ sysAttendCategoryForm.method_GET == 'add'}">
					<ui:button text="${ lfn:message('button.save') }" onclick="onSubmitMethod('save')"></ui:button>
					<ui:button text="${ lfn:message('button.saveadd') }" onclick="onSubmitMethod('saveadd')"></ui:button>
				</c:when>
			</c:choose>
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
		<html:form action="/sys/attend/sys_attend_category/sysAttendCategory.do">
			<html:hidden property="fdOrder" />
			<html:hidden property="fdAppId" />
			<html:hidden property="fdAppName" />
			<html:hidden property="fdAppUrl" />
			<html:hidden property="fdStatus" />
			<html:hidden property="docCreatorId" />
			<html:hidden property="docCreatorName" />
			<html:hidden property="docCreateTime" />
			<html:hidden property="fdId" />
			<html:hidden property="method_GET" />
			<html:hidden property="fdATemplateId" />
			<html:hidden property="docStatus" value="30" />
			<html:hidden property="fdPeriodType"/>
			<html:hidden property="fdRestat" value="0"/>
			<%-- 签到类型 --%>
			<html:hidden property="fdType" />
			<div class="lui_form_content_frame" style="padding-top:20px">
				<div class="lui-singin-creatPage">
					<input name="fdStatusFlag" type="hidden" value="-1"/>
					<%-- 基本信息 --%>
					<div class="lui-singin-creatPage-panel">
				    	<div class="lui-singin-creatPage-panel-body">
							<table class="tb_simple" width="100%">
								<tr>
									<td class="td_normal_title" style="vertical-align: top;">
										<bean:message bundle="sys-attend" key="sysAttendCategory.attend.fdName"/>
									</td>
									<td>
										<xform:text property="fdName" style="width:95%" subject="${ lfn:message('sys-attend:sysAttendCategory.attend.fdName') }"/>
									</td>
								</tr>
								<tr>
									<td class="td_normal_title" style="vertical-align: top;">
										<bean:message bundle="sys-attend" key="sysAttendCategory.fdManager"/>
									</td>
									<td>
										<xform:address propertyId="fdManagerId" propertyName="fdManagerName" orgType="ORG_TYPE_PERSON" mulSelect="false"
											subject="${ lfn:message('sys-attend:sysAttendCategory.fdManager') }" required="true"
											style="width:95%" />
									</td>
								</tr>
								<tr>
									<td class="td_normal_title">
										<bean:message bundle="sys-attend" key="sysAttendCategory.fdEffectTime"/>
									</td>
									<td> 
										<div style="width:45%;float:left;line-height: 35px;">
											<c:set var="fdEffectTime_status" value="edit"></c:set>
											<c:if test="${sysAttendCategoryForm.fdStatus=='1'&& param.method=='edit'}">
												<c:set var="fdEffectTime_status" value="readonly"></c:set>
											</c:if>
											<xform:datetime property="fdEffectTime" showStatus="${fdEffectTime_status }"
															subject="${ lfn:message('sys-attend:sysAttendCategory.fdEffectTime') }"
															dateTimeType="date" validators=""
															required="true"
															style="width:93%"></xform:datetime>
										</div>
										<div style="width:50%;float:left;line-height: 35px;">
											<span class="lui-singin-fdAppName-title" style="margin-left:25px">
												<bean:message bundle="sys-attend" key="table.sysAttendCategoryATemplate"/> ：
											</span>
											<span id="fdATemplateName" class="lui-singin-fdAppName">
												<c:out value="${sysAttendCategoryForm.fdATemplateName }" />
											</span>
										</div>
									</td>
								</tr>
								<%-- 所属场所 --%>
								<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp" charEncoding="UTF-8">
								    <c:param name="id" value="${sysAttendCategoryForm.authAreaId}"/>
								</c:import>
							</table>
				    	</div>
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
										<xform:address propertyId="fdTargetIds" propertyName="fdTargetNames" 
											subject="${ lfn:message('sys-attend:sysAttendCategory.fdTargets') }"
											mulSelect="true" orgType="ORG_TYPE_ALLORG" textarea="false" style="width:95%" required="true" />
									</td>
								</tr>
								<tr>
									<td class="td_normal_title">
										<bean:message bundle="sys-attend" key="sysAttendCategory.fdExcTargets"/>
									</td>
									<td>
										<xform:address propertyId="fdExcTargetIds" propertyName="fdExcTargetNames" 
											mulSelect="true" orgType="ORG_TYPE_POSTORPERSON" style="width:95%" />
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
					   							<c:set var="fdShiftType_status" value="edit"></c:set>
					   							<c:if test="${sysAttendCategoryForm.method_GET=='edit' && sysAttendCategoryForm.fdStatus!=2}">
					   								<c:set var="fdShiftType_status" value="readOnly"></c:set>
					   							</c:if>
					   							<xform:radio showStatus="${fdShiftType_status}" property="fdShiftType"   onValueChange="changeFdShiftType" alignment="V">
					   								<%-- 固定周期  --%>
					   								<xform:simpleDataSource value="0">
					   									${ lfn:message('sys-attend:sysAttendCategory.fdShiftType.fixed') }
					   									<span style="display: inline-block;color: #999;">${ lfn:message('sys-attend:sysAttendCategory.fdShiftType.fixed.tips') }</span>
					   									<div id='fdSameWtimeDiv' style="display: inline-block;margin-left: 30px;">
					   										<xform:select showStatus="${fdShiftType_status}" property="fdSameWorkTime" style="float: none;" showPleaseSelect="false" onValueChange="changeSameWTime">
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

													<%-- 综合工时  --%>
													<xform:simpleDataSource value="3">
														${ lfn:message('sys-attend:sysAttendCategory.fdShiftType.comprehensive') }
														<span style="display: inline-block;color: #999;">${ lfn:message('sys-attend:sysAttendCategory.fdShiftType.comprehensive.tips') }</span>
													</xform:simpleDataSource>
													<%-- 不定时工作制  --%>
													<xform:simpleDataSource value="4">
														${ lfn:message('sys-attend:sysAttendCategory.fdShiftType.unfixed') }
														<span style="display: inline-block;color: #999;">${ lfn:message('sys-attend:sysAttendCategory.fdShiftType.unfixed.tips') }</span>
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
												<xform:datetime minuteStep="1" property="fdAreaStartTime" dateTimeType="time" required="true" validators="beforeFirstStart" style="width:90%"></xform:datetime>
											</td>
											<td valign="top">
												<div style="display:inline;float:left;padding-top: 2px;">${ lfn:message('sys-attend:sysAttendCategory.latest.endTime') }：</div>
												<xform:select property="fdEndDay" showPleaseSelect="false" title="${ lfn:message('sys-attend:sysAttendCategory.latest.endTime') }" onValueChange="changeFocus('fdAreaEndTime');" style="width:80px;height:32px;margin-right:7px;">
													<xform:simpleDataSource value='1'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }</xform:simpleDataSource>
													<xform:simpleDataSource value='2'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }</xform:simpleDataSource>
												</xform:select>
												<xform:datetime minuteStep="1" property="fdAreaEndTime" dateTimeType="time" validators="afterOpen acrossDay" required="true" style="width: 126px;"></xform:datetime>
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
					    						<xform:checkbox property="fdWeek" subject="${ lfn:message('sys-attend:sysAttendMain.fdDateType.workday') }" isArrayValue="false" required="true">
													<xform:enumsDataSource enumsType="sysAttendCategory_fdWeek" />
												</xform:checkbox>
					    					</td>
					    					<td id="customDateTd">
					    						<div class="inputselectsgl" onclick="selectMulDate('fdCustomDateStr')" style="width: 95%;">
													<div class="input">
														<input type="text" name="fdCustomDateStr"  readonly="readonly" value="<c:if test="${sysAttendCategoryForm.fdShiftType == '2' }">${sysAttendCategoryForm.fdCustomDateStr }</c:if>" validate="required" subject="${ lfn:message('sys-attend:sysAttendMain.fdDateType.workday') }" />
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
					    					<html:hidden property="fdWork" />
					    					<li id='onceType' onclick="changeWorkType('1')"><a href="javascript:void(0);">${ lfn:message('sys-attend:sysAttendCategory.fdWork.once') }</a></li>
					    					<li id='twiceType' onclick="changeWorkType('2')"><a href="javascript:void(0);">${ lfn:message('sys-attend:sysAttendCategory.fdWork.twice') }</a></li>
					    				</ul>
					    				<div class="lui-singin-creatPage-tab-body" style="padding-bottom: 0">
					    					<table class="tb_simple" width="100%">
					    						<%-- 第一班次  --%>
					    						<tr id="onceWorkTime">
					    							<%-- 上班 --%>
													<td id="onceWorkTd1" style="width: 30px" class="td_normal_title td_tab_title">
														${ lfn:message('sys-attend:sysAttendMain.fdWorkType.onwork') }
													</td>
													<td id="onceWorkTd2"  style="width: 120px">
														<html:hidden property="fdWorkTime[0].fdId"/>
														<html:hidden property="fdWorkTime[0].fdIsAvailable" value="${fdWTAvailable1 }" />
														<xform:datetime minuteStep="1" property="fdWorkTime[0].fdStartTime" subject="${ lfn:message('sys-attend:sysAttendCategoryWorktime.fdStartTime') }" validators="afterOpen" required="true" dateTimeType="time" style="width:80%;" value="${fdOnTime1 }" onValueChange="calTotalTime"></xform:datetime>
													</td>
													<%-- 下班 --%>
													<td id="onceWorkTd3"  style="width: 80px" class="td_normal_title td_tab_title">
														—&nbsp;&nbsp;&nbsp;&nbsp;
														${ lfn:message('sys-attend:sysAttendMain.fdWorkType.offwork') }
													</td>
													<td id="onceWorkTd4"  style="width: 160px">
														<div id='overTimeTypeOnce'>
															<xform:select property="fdWorkTime[0].fdOverTimeType" showPleaseSelect="false" title="" onValueChange="changeFocus('fdWorkTime[0].fdEndTime');" style="width:35%;height:30px;margin-right:7px;">
															<xform:simpleDataSource value='1'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }</xform:simpleDataSource>
															<xform:simpleDataSource value='2'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }</xform:simpleDataSource>
														</xform:select>
														</div>
														<div id='overTimeTypeTwice'>
															<xform:select property="fdWorkTime[0].fdOverTimeType" showPleaseSelect="false" title="" onValueChange="changeFocus('fdWorkTime[0].fdEndTime');" style="width:35%;height:30px;margin-right:7px;">
																<xform:simpleDataSource value='1'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }</xform:simpleDataSource>
															</xform:select>
														</div>
														<xform:datetime minuteStep="1" property="fdWorkTime[0].fdEndTime" subject="${ lfn:message('sys-attend:sysAttendCategoryWorktime.fdEndTime') }" validators="afterFirstStart afterAcrossFirstStart" required="true" dateTimeType="time" style="width:50%;" value="${fdOffTime1 }" onValueChange="calTotalTime"></xform:datetime>
													</td>
													<%-- 最早打卡 --%>
													<td style="width: 80px" class="td_normal_title td_tab_title">
														${ lfn:message('sys-attend:sysAttendCategory.earliest.startTime') }：
													</td>
													<td style="width: 120px">
														<xform:datetime minuteStep="1" property="fdStartTime" dateTimeType="time" required="true" validators="beforeFirstStart afterAcrossFirstEnd" style="width:90%"></xform:datetime>
													</td>
													<%-- 最晚打卡 --%>
													<td style="width: 80px" class="td_normal_title td_tab_title">
														${ lfn:message('sys-attend:sysAttendCategory.latest.endTime') }：
													</td>
													<td style="">
														<div id='endTimeOnce'>
															<xform:select property="fdEndDay" showPleaseSelect="false" title="${ lfn:message('sys-attend:sysAttendCategory.latest.endTime') }" onValueChange="changeFocus('fdEndTime');" style="width:40%;height:32px;margin-right:7px;">
																<xform:simpleDataSource value='1'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }</xform:simpleDataSource>
																<xform:simpleDataSource value='2'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }</xform:simpleDataSource>
															</xform:select>
															<xform:datetime minuteStep="1" property="fdEndTime" dateTimeType="time" validators="afterEnd acrossDay" required="true" style="width: 48%"></xform:datetime>
														</div>
														
														<div id='endTimeTwice'>
															<xform:datetime minuteStep="1" property="fdEndTime1" dateTimeType="time" validators="firstEndTime" style="width:90%"></xform:datetime>
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
														<html:hidden property="fdWorkTime[1].fdId"/>
														<html:hidden property="fdWorkTime[1].fdIsAvailable" value="${fdWTAvailable2 }" />
														<xform:datetime minuteStep="1" property="fdWorkTime[1].fdStartTime" subject="${ lfn:message('sys-attend:sysAttendCategoryWorktime.fdStartTime') }" validators="afterFirstEnd" required="true" dateTimeType="time" style="width:80%;" onValueChange="calTotalTime"></xform:datetime>
													</td>
													<%-- 下班 --%>
													<td style="width: 80px" class="td_normal_title td_tab_title">
														—&nbsp;&nbsp;&nbsp;&nbsp;
														${ lfn:message('sys-attend:sysAttendMain.fdWorkType.offwork') }
													</td>
													<td style="width: 160px">
														<xform:select property="fdWorkTime[1].fdOverTimeType" showPleaseSelect="false" title="" onValueChange="changeFocus('fdWorkTime[1].fdEndTime');" style="width:35%;height:30px;margin-right:7px;">
															<xform:simpleDataSource value='1'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }</xform:simpleDataSource>
															<xform:simpleDataSource value='2'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }</xform:simpleDataSource>
														</xform:select>
														<xform:datetime minuteStep="1" property="fdWorkTime[1].fdEndTime" subject="${ lfn:message('sys-attend:sysAttendCategoryWorktime.fdEndTime') }" validators="afterSecondStart afterAcrossFirstStart afterAcrossSecondStart" required="true" dateTimeType="time" style="width:50%;" onValueChange="calTotalTime"></xform:datetime>
													</td>
													<%-- 最早打卡 --%>
													<td style="width: 80px" class="td_normal_title td_tab_title">
														${ lfn:message('sys-attend:sysAttendCategory.earliest.startTime') }：
													</td>
													<td style="width: 120px">
														<xform:datetime minuteStep="1" property="fdStartTime2" dateTimeType="time" validators="secondStartTime afterAcrossSecondEnd" style="width:90%"></xform:datetime>
													</td>
													<%-- 最晚打卡 --%>
													<td style="width: 80px" class="td_normal_title td_tab_title">
														${ lfn:message('sys-attend:sysAttendCategory.latest.endTime') }：
													</td>
													<td style="" id="endTimeTwice2">
														<xform:select property="fdEndDay" showPleaseSelect="false" title="${ lfn:message('sys-attend:sysAttendCategory.latest.endTime') }" onValueChange="changeFocus('fdEndTime2');" style="width:40%;height:32px;margin-right:7px;">
															<xform:simpleDataSource value='1'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }</xform:simpleDataSource>
															<xform:simpleDataSource value='2'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }</xform:simpleDataSource>
														</xform:select>
														<xform:datetime minuteStep="1" property="fdEndTime2" dateTimeType="time" validators="afterEnd acrossDay afterArcossEnd" required="true" style="width: 48%"></xform:datetime>
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
												<xform:select property="fdRestStartType"
															  showPleaseSelect="false"
															  title="${ lfn:message('sys-attend:sysAttendCategory.fdRestStartType') }"
															  style="width:80px;height:32px;margin-right:7px;"
															  onValueChange="calTotalTime"
															  showStatus="edit"
												>
													<xform:simpleDataSource value='1'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }</xform:simpleDataSource>
													<xform:simpleDataSource value='2'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }</xform:simpleDataSource>
												</xform:select>
					    						<xform:datetime minuteStep="1" property="fdRestStartTime" dateTimeType="time" validators="restTimeNull restTimeRange restTimeStart" style="width:100px" onValueChange="calTotalTime"></xform:datetime>
					    						<span style="float: left;margin:0 20px;">—</span>
												<xform:select property="fdRestEndType"
															  showPleaseSelect="false"
															  title="${ lfn:message('sys-attend:sysAttendCategory.fdRestEndType') }"
															  style="width:80px;height:32px;margin-right:7px;"
															  onValueChange="calTotalTime"
															  showStatus="edit"
												>
													<xform:simpleDataSource value='1'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }</xform:simpleDataSource>
													<xform:simpleDataSource value='2'>${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }</xform:simpleDataSource>
												</xform:select>
					    						<xform:datetime minuteStep="1" property="fdRestEndTime" dateTimeType="time" validators="restTimeNull restTimeRange restTimeEnd" style="width:100px" onValueChange="calTotalTime"></xform:datetime>
					    					</td>
					    				</tr>
					    			</table>


				    			</div>
					    	</div>
					    	<%-- 总工时 --%>
					    	<div class="lui-singin-creatPage-table" id="div_totalTime">
					    		<div class="caption" style="padding-top: 18px;">
					    			${ lfn:message('sys-attend:sysAttendStat.fdTotalTime') }
					    		</div>
					    		<div class="content">
					    			<html:hidden property="fdTotalTime" styleClass="inputsgl" />
									<div id='totalTimeComprehensiveDiv' style="padding-top: 13px;margin-left: 10px;">
											${ lfn:message('sys-attend:sysAttendCategory.hour') }
									</div>
					    			<div id='totalTimeDiv' style="padding-top: 13px;margin-left: 10px;">
					    				${sysAttendCategoryForm.fdTotalTime }${ lfn:message('sys-attend:sysAttendCategory.hour') }
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
												<a href="javascript:void(0);" class="add-btn" onclick="addTimeSheet();">
													${ lfn:message('button.create') }
												</a>
							    			</td>
							    		</tr>
							    		<tr KMSS_IsReferRow="1" style="display:none">
							    			<td>
							    			</td>
							    			<td>
							    			</td>
							    			<td>
							    				<input type="hidden" name="fdTimeSheets[!{index}].fdId" />
							    				<input type="hidden" name="fdTimeSheets[!{index}].fdCategoryId" value="${sysAttendCategoryForm.fdId }" />
						    					<input type="hidden" name="fdTimeSheets[!{index}].fdWeek" />
							    				<input type="hidden" name="fdTimeSheets[!{index}].fdWork" />
							    				<input type="hidden" name="fdTimeSheets[!{index}].fdWorkTime[0].fdId" data-wtime-idx='0' />
							    				<input type="hidden" name="fdTimeSheets[!{index}].fdWorkTime[0].fdIsAvailable" data-wtime-idx='0' />
							    				<input type="hidden" name="fdTimeSheets[!{index}].fdWorkTime[0].fdStartTime" data-wtime-idx='0' />
							    				<input type="hidden" name="fdTimeSheets[!{index}].fdWorkTime[0].fdEndTime" data-wtime-idx='0' />
							    				<input type="hidden" name="fdTimeSheets[!{index}].fdWorkTime[0].fdOverTimeType" data-wtime-idx='0' />
							    				<input type="hidden" name="fdTimeSheets[!{index}].fdWorkTime[1].fdId" data-wtime-idx='1' />
							    				<input type="hidden" name="fdTimeSheets[!{index}].fdWorkTime[1].fdIsAvailable" data-wtime-idx='1' />
							    				<input type="hidden" name="fdTimeSheets[!{index}].fdWorkTime[1].fdStartTime" data-wtime-idx='1' />
							    				<input type="hidden" name="fdTimeSheets[!{index}].fdWorkTime[1].fdEndTime" data-wtime-idx='1' />
							    				<input type="hidden" name="fdTimeSheets[!{index}].fdWorkTime[1].fdOverTimeType" data-wtime-idx='0' />
							    				<input type="hidden" name="fdTimeSheets[!{index}].fdStartTime1" />
							    				<input type="hidden" name="fdTimeSheets[!{index}].fdStartTime2" />
							    				<input type="hidden" name="fdTimeSheets[!{index}].fdEndTime1" />
							    				<input type="hidden" name="fdTimeSheets[!{index}].fdEndTime2" />
							    				<input type="hidden" name="fdTimeSheets[!{index}].fdEndDay" />
							    				<input type="hidden" name="fdTimeSheets[!{index}].fdRestStartTime" />
							    				<input type="hidden" name="fdTimeSheets[!{index}].fdRestEndTime" />
							    				<input type="hidden" name="fdTimeSheets[!{index}].fdTotalTime" />
												<input type="hidden" name="fdTimeSheets[!{index}].fdTotalDay" />
												<input type="hidden" name="fdTimeSheets[!{index}].fdRestEndType" />
												<input type="hidden" name="fdTimeSheets[!{index}].fdRestStartType" />

							    				<a href="javascript:void(0);" class="add-btn" onclick="editTimeSheet();">
													${ lfn:message('button.edit') }
												</a>
												<a href="javascript:void(0);" class="add-btn" onclick="deleteTimeSheet();">
													${ lfn:message('button.delete') }
												</a>
							    			</td>
							    		</tr>
							    		<c:forEach items="${sysAttendCategoryForm.fdTimeSheets}" var="timeItem" varStatus="vstatus">
							    			<tr KMSS_IsContentRow="1">
							    				<td>
							    					${timeItem.fdWeekNames }
								    			</td>
								    			<td>
								    				${timeItem.fdWorkTimeText}
								    			</td>
								    			<td>
								    				<input type="hidden" name="fdTimeSheets[${vstatus.index }].fdId" value="${timeItem.fdId }" />
								    				<input type="hidden" name="fdTimeSheets[${vstatus.index }].fdCategoryId" value="${sysAttendCategoryForm.fdId }" />
								    				<input type="hidden" name="fdTimeSheets[${vstatus.index }].fdWeek" value="${timeItem.fdWeek }" />
								    				<input type="hidden" name="fdTimeSheets[${vstatus.index }].fdWork" value="${timeItem.fdWork }" />
								    				<input type="hidden" name="fdTimeSheets[${vstatus.index }].fdWorkTime[0].fdId" value="${timeItem.fdWorkTime[0].fdId }" data-wtime-idx='0' />
								    				<input type="hidden" name="fdTimeSheets[${vstatus.index }].fdWorkTime[0].fdIsAvailable" value="${timeItem.fdWorkTime[0].fdIsAvailable }" data-wtime-idx='0' />
								    				<input type="hidden" name="fdTimeSheets[${vstatus.index }].fdWorkTime[0].fdStartTime" value="${timeItem.fdWorkTime[0].fdStartTime }" data-wtime-idx='0' />
								    				<input type="hidden" name="fdTimeSheets[${vstatus.index }].fdWorkTime[0].fdEndTime" value="${timeItem.fdWorkTime[0].fdEndTime }" data-wtime-idx='0' />
								    				<input type="hidden" name="fdTimeSheets[${vstatus.index }].fdWorkTime[0].fdOverTimeType" value="${timeItem.fdWorkTime[0].fdOverTimeType }" data-wtime-idx='0' />
							    					<input type="hidden" name="fdTimeSheets[${vstatus.index }].fdWorkTime[1].fdId" value="${timeItem.fdWorkTime[1].fdId }" data-wtime-idx='1' />
								    				<input type="hidden" name="fdTimeSheets[${vstatus.index }].fdWorkTime[1].fdIsAvailable" value="${timeItem.fdWorkTime[1].fdIsAvailable }" data-wtime-idx='1' />
								    				<input type="hidden" name="fdTimeSheets[${vstatus.index }].fdWorkTime[1].fdStartTime" value="${timeItem.fdWorkTime[1].fdStartTime }" data-wtime-idx='1' />
								    				<input type="hidden" name="fdTimeSheets[${vstatus.index }].fdWorkTime[1].fdEndTime" value="${timeItem.fdWorkTime[1].fdEndTime }" data-wtime-idx='1' />
								    				<input type="hidden" name="fdTimeSheets[${vstatus.index }].fdWorkTime[1].fdOverTimeType" value="${timeItem.fdWorkTime[1].fdOverTimeType }" data-wtime-idx='0' />
								    				<input type="hidden" name="fdTimeSheets[${vstatus.index }].fdStartTime1" value="${timeItem.fdStartTime1 }" />
								    				<input type="hidden" name="fdTimeSheets[${vstatus.index }].fdStartTime2" value="${timeItem.fdStartTime2 }" />
								    				<input type="hidden" name="fdTimeSheets[${vstatus.index }].fdEndTime1" value="${timeItem.fdEndTime1 }" />
								    				<input type="hidden" name="fdTimeSheets[${vstatus.index }].fdEndTime2" value="${timeItem.fdEndTime2 }" />
								    				<input type="hidden" name="fdTimeSheets[${vstatus.index }].fdEndDay" value="${timeItem.fdEndDay }" />
								    				<input type="hidden" name="fdTimeSheets[${vstatus.index }].fdRestStartTime" value="${timeItem.fdRestStartTime }" />
								    				<input type="hidden" name="fdTimeSheets[${vstatus.index }].fdRestEndTime" value="${timeItem.fdRestEndTime }" />
								    				<input type="hidden" name="fdTimeSheets[${vstatus.index }].fdTotalTime" value="${timeItem.fdTotalTime }" />
													<input type="hidden" name="fdTimeSheets[${vstatus.index }].fdTotalDay" value="${timeItem.fdTotalDay }" />
													<input type="hidden" name="fdTimeSheets[${vstatus.index }].fdRestEndType" value="${timeItem.fdRestEndType }" />
													<input type="hidden" name="fdTimeSheets[${vstatus.index }].fdRestStartType" value="${timeItem.fdRestStartType }" />

													<a href="javascript:void(0);" class="add-btn" onclick="editTimeSheet();">
														${ lfn:message('button.edit') }
													</a>
													<a href="javascript:void(0);" class="add-btn" onclick="deleteTimeSheet();">
														${ lfn:message('button.delete') }
													</a>
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
							<%-- 一周内固定工作时间 综合工时 --%>
					    	<%-- 节假日 --%>
					    	<div class="lui-singin-creatPage-table" id="holidayContent">
					    		<div class="caption" style="padding-top: 16px;">
					    			${ lfn:message('sys-attend:sysAttendCategory.fdHoliday') }
					    		</div>
					    		<div class="content">
					    			<table class="tb_simple" width="100%">
										<tr>
											<td>
												<html:hidden property="fdHolidayId" />
												<html:hidden property="fdHolidayName" />
												<div id='holidayNameDiv' style="display: inline-block;margin-right: 20px">
													${sysAttendCategoryForm.fdHolidayName }
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
												<div class="inputselectsgl" onclick="selectMulDate('fdTimesStr')" style="width: 95%;">
													<div class="input">
														<input type="text" name="fdTimesStr"  readonly="readonly" value="<c:if test="${sysAttendCategoryForm.fdShiftType != '2' }">${sysAttendCategoryForm.fdTimesStr }</c:if>" />
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
												<div class="inputselectsgl" onclick="selectMulDate('fdExcTimesStr')" style="width: 95%;">
													<div class="input">
														<input type="text" name="fdExcTimesStr"  readonly="readonly" value="${sysAttendCategoryForm.fdExcTimesStr }" />
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
	                                                    <ui:switch property="fdDingClock" onValueChange="onDingChange();"
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
			   										<input type="hidden" attrname="fdLimitLocations[pidx].fdLocations[idx].fdId" /> 
													<input type="hidden" attrname="fdLimitLocations[pidx].fdLocations[idx].fdCategoryId" value="${sysAttendCategoryForm.fdId}" />
													<div data-location-container="fdLimitLocations[pidx].fdLocations[idx].fdLocation" class="lui_location_container" style="width:97%;float:left;" mark-loaded="false"></div>
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
			    						            <ui:switch property="fdCanMap" onValueChange="onMapChange();" checked="${sysAttendCategory.fdCanMap == '0'?false:true}">
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
									   							<xform:text property="fdLimitLocations[!{index}].fdLimit" validators="digits" required="true" subject="${ lfn:message('sys-attend:sysAttendCategoryRule.fdLimit') }" 
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
									   										<input type="hidden" name="fdLimitLocations[!{index}].fdLocations[!{indexChild}].fdId" /> 
																			<input type="hidden" name="fdLimitLocations[!{index}].fdLocations[!{indexChild}].fdCategoryId" value="${sysAttendCategoryForm.fdId}" />
																			<map:location propertyName="fdLimitLocations[!{index}].fdLocations[!{indexChild}].fdLocation" propertyCoordinate="fdLimitLocations[!{index}].fdLocations[!{indexChild}].fdLocationCoordinate" 
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
			    						    <c:forEach items="${sysAttendCategoryForm.fdLocations}" var="fdLocationsItem" varStatus="vstatus">	
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
											   								<xform:text property="fdLimitLocations[${fdLocationsItem.fdLimitIndex}].fdLimit" validators="digits" required="true" subject="${ lfn:message('sys-attend:sysAttendCategoryRule.fdLimit') }" 
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
											   								<xform:text property="fdLimitLocations[0].fdLimit" validators="digits" required="true" subject="${ lfn:message('sys-attend:sysAttendCategoryRule.fdLimit') }" 
											   									value="${sysAttendCategoryForm.fdRule[0].fdLimit }" style="width:100px;margin-right:8px;"></xform:text>
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
											   										<input type="hidden" name="fdLimitLocations[${fdLocationsItem.fdLimitIndex}].fdLocations[${fdLocationsItem.fdRow}].fdId" value="${fdLocationsItem.fdId}" />
																					<input type="hidden" name="fdLimitLocations[${fdLocationsItem.fdLimitIndex}].fdLocations[${fdLocationsItem.fdRow}].fdCategoryId" value="${sysAttendCategoryForm.fdId}" />
																					<div data-location-container="fdLimitLocations[${fdLocationsItem.fdLimitIndex}].fdLocations[${fdLocationsItem.fdRow}].fdLocation" class="lui_location_container" style="width:97%;float:left;" mark-loaded="false"></div>
																					<span class="txtstrong">*</span>
																					<c:set var="LimitIndex"  value="${fdLocationsItem.fdLimitIndex}"></c:set>
																					<c:set var="fdRow" value="${fdLocationsItem.fdRow}"></c:set>																					
																					<c:set var="_fdLocation" value="${fdLocationsItem.fdLocation}"></c:set>
																					<c:set var="_fdLocationCoordinate" value="${fdLocationsItem.fdLocationCoordinate}"></c:set>
																					<script>
																					seajs.use(['sys/attend/map/resource/js/LocationInit.js'],function(init){
																						init( {"id":null,"propertyName":"fdLimitLocations[${LimitIndex}].fdLocations[${fdRow}].fdLocation",
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
									   			</c:when>	
									   			<c:otherwise>									   		
											   								<tr KMSS_IsContentRow="1">
											   									<td>
											   										<input type="hidden" name="fdLimitLocations[0].fdLocations[${vstatus.index}].fdId" value="${fdLocationsItem.fdId}"  />
																					<input type="hidden" name="fdLimitLocations[0].fdLocations[${vstatus.index}].fdCategoryId" value="${sysAttendCategoryForm.fdId}" />
																					<div data-location-container="fdLimitLocations[0].fdLocations[${vstatus.index}].fdLocation" class="lui_location_container" style="width:97%;float:left;" mark-loaded="false"></div>
																					<span class="txtstrong">*</span>
																					<c:set var="LimitIndex"  value="0"></c:set>
																					<c:set var="fdRow" value="${vstatus.index}"></c:set>																					
																					<c:set var="_fdLocation" value="${fdLocationsItem.fdLocation}"></c:set>
																					<c:set var="_fdLocationCoordinate" value="${fdLocationsItem.fdLocationCoordinate}"></c:set>
																					<script>
																					seajs.use(['sys/attend/map/resource/js/LocationInit.js'],function(init){
																						init( {"id":null,"propertyName":"fdLimitLocations[${LimitIndex}].fdLocations[${fdRow}].fdLocation",
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
									   			<c:if test="${fdLocationsItem.fdLimitIndex!=sysAttendCategoryForm.fdLocations[vstatus.index+1].fdLimitIndex}">	
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
                                                    <ui:switch property="fdCanWifi" onValueChange="onWifiChange();" checked="${sysAttendCategory.fdCanWifi == '0'?false:true}">
                                                    </ui:switch>
                                                </td>
                                            </tr>
						   					<tr id="fdWifiTR">
						   						<td colspan="2">
						   							<table id="wifiConfigs" class="tb_simple" width="100%">
						   								<tr KMSS_IsReferRow="1" style="display:none">
						   									<td>
						   										<input type="hidden" name="fdWifiConfigs[!{index}].fdId" /> 
																<input type="hidden" name="fdWifiConfigs[!{index}].fdCategoryId" value="${sysAttendCategoryForm.fdId}" />
				   												<xform:text property="fdWifiConfigs[!{index}].fdName" htmlElementProperties="placeholder='${ lfn:message('sys-attend:sysAttendCategory.fdWifiConfigs.fdName.placeholder') }' data-wifi-config" 
				   													validators="required maxLength(30)" subject="${ lfn:message('sys-attend:sysAttendCategory.fdWifiConfigs.fdName') }"
				   													style="width:35%; margin-right:10px;"></xform:text>
				   												<xform:text property="fdWifiConfigs[!{index}].fdMacIp" htmlElementProperties="placeholder='${ lfn:message('sys-attend:sysAttendCategory.fdWifiConfigs.fdMacIp.placeholder') }'" 
				   													validators="required macIp checkMacIp" subject="${ lfn:message('sys-attend:sysAttendCategory.fdWifiConfigs.fdMacIp') }"
				   													style="width:58%;"></xform:text>
				   												<span class="txtstrong">*</span>
				   												<a href="javascript:void(0);" onclick="deletePosition();" title="${lfn:message('doclist.delete')}">
																	<img src="${KMSS_Parameter_ContextPath}sys/attend/resource/images/delete_btn.png"/>
																</a>
				   											</td>
				   										</tr>
				   										<c:forEach items="${sysAttendCategoryForm.fdWifiConfigs}" var="fdWifi" varStatus="vstatus">
				   											<tr KMSS_IsContentRow="1">
				   												<input type="hidden" name="fdWifiConfigs[${vstatus.index}].fdId" value="${fdWifi.fdId}" /> 
																<input type="hidden" name="fdWifiConfigs[${vstatus.index}].fdCategoryId" value="${sysAttendCategoryForm.fdId}" />
				   												<td>
				    												<xform:text property="fdWifiConfigs[${vstatus.index}].fdName" htmlElementProperties="placeholder='${ lfn:message('sys-attend:sysAttendCategory.fdWifiConfigs.fdName.placeholder') }' data-wifi-config" 
				    													validators="required maxLength(30)" subject="${ lfn:message('sys-attend:sysAttendCategory.fdWifiConfigs.fdName') }"
				    													style="width:35%; margin-right:10px;"></xform:text>
				    												<xform:text property="fdWifiConfigs[${vstatus.index}].fdMacIp" htmlElementProperties="placeholder='${ lfn:message('sys-attend:sysAttendCategory.fdWifiConfigs.fdMacIp.placeholder') }'" 
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
			   									<html:hidden property="fdRule[0].fdOutside" />
			   									<ui:switch id="fdOutsideWgt" property="__fdOutside" enabledText="${ lfn:message('sys-attend:sysAttendCategoryRule.fdOutside.allowOrNot') }" 
			   										disabledText="${ lfn:message('sys-attend:sysAttendCategoryRule.fdOutside.notAllow') }" onValueChange="changeFdOutside();" checked="${sysAttendCategoryForm.fdRule[0].fdOutside }">
			   									</ui:switch>
			   								</td>
			   							</tr>
			   							<tr id='osdReviewType' style="<c:if test="${sysAttendCategoryForm.fdRule[0].fdOutside != 'true' }">display:none</c:if>">
			   								<td>
			   									<xform:radio property="fdOsdReviewType" alignment="V">
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
		   										<xform:checkbox property="fdOsdReviewIsUpload">
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
												<ui:switch property="fdIsFlex" disabledText="${ lfn:message('sys-attend:sysAttendCategory.fdIsFlex.close') }"  enabledText="${ lfn:message('sys-attend:sysAttendCategory.fdIsFlex.open') }" onValueChange="changeIsFlex()"></ui:switch>
											</td>
											<td id='flexTimeTd' valign="top">
													${ lfn:message('sys-attend:sysAttendCategory.fdFlexTime.tips') }
												<xform:text property="fdFlexTime" style="width:50px;margin:0 8px;float:none;display:inline-block;" validators="required digits flexTimeVld" subject="${ lfn:message('sys-attend:sysAttendCategory.fdFlexTime') }"></xform:text>
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
			    				<div class="caption" style="padding-top: 17px;text-align: center;">
			    					${ lfn:message('sys-attend:sysAttendCategoryRule.fdLateTime') }
			    				</div>
			    				<div class="content">
			    					<table class="tb_simple" width="100%">
			    						<tr>
											<td>
												${ lfn:message('sys-attend:sysAttendCategoryRule.fdLateTime.afterWork') }
												<xform:text property="fdRule[0].fdLateTime" style="width:50px;margin:0 3px;float:none;display:inline-block;" validators="digits maxLate" value='${fdLateTime }'></xform:text>
												${ lfn:message('sys-attend:sysAttendCategoryRule.minute') }
											</td>
			    						</tr>
			    					</table>
		    					</div>
			    			</div>
			    			<%-- 早退设置  --%>
					    	<div class="lui-singin-creatPage-table" id='leftBody'>
			    				<div class="caption" style="padding-top: 17px;text-align: center;">
			    					${ lfn:message('sys-attend:sysAttendCategoryRule.fdLeftTime') }
			    				</div>
			    				<div class="content">
			    					<table class="tb_simple" width="100%">
			    						<tr>
											<td>
												${ lfn:message('sys-attend:sysAttendCategoryRule.fdLeftTime.beforeOff') }
												<xform:text property="fdRule[0].fdLeftTime" style="width:50px;margin:0 3px;float:none;display:inline-block;" validators="digits maxLeft" value='${fdLeftTime }'></xform:text>
												${ lfn:message('sys-attend:sysAttendCategoryRule.minute') }
											</td>
			    						</tr>
			    					</table>
		    					</div>
			    			</div>
                             <%-- 事假设置 --%>
								<div class="lui-singin-creatPage-table" id='personalLeaveBody'>
									<div class="caption" style="padding-top: 17px;text-align: center;">
											${ lfn:message('sys-attend:sysAttendCategory.personalLeave') }
									</div>
									<div class="content">
										<table class="tb_simple" width="100%">
											<tr>
											迟到累计
											<xform:text property="fdLateTotalTime" style="width:50px;margin:0 3px;float:none;display:inline-block;" validators="digits min(0)"></xform:text>
											分钟开始算事假,或者迟到次数达到
											<xform:text property="fdLateNumTotalTime" style="width:50px;margin:0 3px;float:none;display:inline-block;" validators="digits min(0)"></xform:text>
											次开始算事假
											</tr>										
											<tr>
												<td>
														${ lfn:message('sys-attend:sysAttendCategory.fdLateToAbsentTime.over') }
													<xform:text property="fdLateToAbsentTime" style="width:50px;margin:0 3px;float:none;display:inline-block;" validators="digits maxLate minLateToAbs"></xform:text>
														${ lfn:message('sys-attend:sysAttendCategoryRule.minute') }
													&nbsp;
													&nbsp;
														${ lfn:message('sys-attend:sysAttendCategory.fdLeftToAbsentTime.over') }
													<xform:text property="fdLeftToAbsentTime" style="width:50px;margin:0 3px;float:none;display:inline-block;" validators="digits maxLeft minLeftToAbs"></xform:text>
														${ lfn:message('sys-attend:sysAttendCategoryRule.minute') }
														${ lfn:message('sys-attend:sysAttendCategory.half.personalLeave') }
													&nbsp;
													<div id="absFullBody" style="display: inline-block;">
															${ lfn:message('sys-attend:sysAttendCategory.fdLateToAbsentTime.over') }
														<xform:text property="fdLateToFullAbsTime" style="width:50px;margin:0 3px;float:none;display:inline-block;" validators="digits maxLate fullLateAbscent minLateToAbs"></xform:text>
															${ lfn:message('sys-attend:sysAttendCategoryRule.minute') }
														&nbsp;
														&nbsp;
															${ lfn:message('sys-attend:sysAttendCategory.fdLeftToAbsentTime.over') }
														<xform:text property="fdLeftToFullAbsTime" style="width:50px;margin:0 3px;float:none;display:inline-block;" validators="digits maxLeft fullLeftAbscent minLeftToAbs"></xform:text>
															${ lfn:message('sys-attend:sysAttendCategoryRule.minute') }
															${ lfn:message('sys-attend:sysAttendCategory.full.personalLeave') }
													</div>
												</td>
											</tr>
										</table>
									</div>
								</div>
			    			<%-- 旷工设置  --%>
								<div class="lui-singin-creatPage-table" id='absentBody'>
									<div class="caption" style="padding-top: 17px;text-align: center;">
											${ lfn:message('sys-attend:sysAttendCategory.absence') }
									</div>
									<div class="content">
										<table class="tb_simple" width="100%">
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
			    				<div class="caption" style="padding-top: 17px;text-align: center;">
			    					${ lfn:message('sys-attend:sysAttendCategory.overTime.title') }
			    				</div>
			    				<div class="content">
			    					<table class="tb_simple" width="100%">
						    			<tr>
						    				<td>
						    					<ui:switch property="fdIsOvertime" enabledText="${ lfn:message('sys-attend:sysAttendCategory.fdIsOvertime') }" disabledText="${ lfn:message('sys-attend:sysAttendCategory.fdIsOvertime.not') }" onValueChange="changefdIsOvt()"></ui:switch>
						    					<div id="ovtMinHour" style="display: inline-block;padding-top: 17px;">
				    								<span>${ lfn:message('sys-attend:sysAttendCategory.overtime.atLeast') }</span>
													<xform:text property="fdMinOverTime" validators="min(0) max(1440)" required="true" style="height: 22px;width:40px;margin: 0 3px;float:none;" subject="${ lfn:message('sys-attend:sysAttendCategory.overtime.atLeast') }"></xform:text>
													<span>${ lfn:message('sys-attend:sysAttendCategory.overtime.hour') }</span>
													<br/>

													<span>${ lfn:message('sys-attend:sysAttendCategory.convert.overTime') }</span>
													<xform:text property="fdConvertOverTimeHour" validators="min(1) max(24)" required="true" style="height: 22px;width:40px;margin: 0 3px;float:none;" ></xform:text>
													<span>${ lfn:message('sys-attend:sysAttendCategory.convert.overTime.toDay') }</span>
													<br/>
				    							</div>
						    				</td>
						    			</tr>
						    			<tr>
						    				<td>						    					
						    					<span id="ovtMinHourTips" class="comment-text">
														${ lfn:message('sys-attend:sysAttendCategory.overtime.tips') }
													<br/>${ lfn:message('sys-attend:sysAttendCategory.overtime.tips2') }
												</span>


						    				</td>
						    			</tr>
						    			<tr id="ovtReview">
						    				<td>
						    					<xform:radio property="fdOvtReviewType" title="${ lfn:message('sys-attend:sysAttendCategory.fdOvtReviewType') }"  alignment="V">
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
												<ui:switch property="fdBeforeWorkOverTime"
														   enabledText="${ lfn:message('sys-attend:sysAttendCategory.fdBeforeWorkOverTime.yes') }"
														   disabledText="${ lfn:message('sys-attend:sysAttendCategory.fdBeforeWorkOverTime.no') }" ></ui:switch>
												<span class="comment-text">${ lfn:message('sys-attend:sysAttendCategory.fdBeforeWorkOverTime.memo') }</span>
												<span class="comment-text">${ lfn:message('sys-attend:sysAttendCategory.fdBeforeWorkOverTime.memo1') }</span>
												<span class="comment-text">${ lfn:message('sys-attend:sysAttendCategory.fdBeforeWorkOverTime.memo2') }</span>

											</td>
										</tr>
										<tr >
											<td>
												<ui:switch property="fdIsCalculateOvertime"
														   enabledText="${ lfn:message('sys-attend:sysAttendCategory.fdIsCalculateOvertime.yes') }"
														   disabledText="${ lfn:message('sys-attend:sysAttendCategory.fdIsCalculateOvertime.no') }" ></ui:switch>
												<span class="comment-text">${ lfn:message('sys-attend:sysAttendCategory.fdIsCalculateOvertime.memo') }</span>
												<span class="comment-text">${ lfn:message('sys-attend:sysAttendCategory.fdIsCalculateOvertime.memo1') }</span>
											</td>
										</tr>

						    			<tr id="deductSwitch" >
						    				<td>
						    					<ui:switch property="fdIsOvertimeDeduct" enabledText="${ lfn:message('sys-attend:sysAttendCategory.fdIsOvertimeDeduct') }" disabledText="${ lfn:message('sys-attend:sysAttendCategory.fdIsOvertimeDeduct.not') }" onValueChange="changefdIsOvtDeduct()"></ui:switch>
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
						    					<xform:radio property="fdOvtDeductType" title="${ lfn:message('sys-attend:sysAttendCategory.fdOvtReviewType') }"  onValueChange="changefdOvtDeductType" alignment="H">
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
						   										<input type="hidden" name="overtimeDeducts[!{index}].fdId" /> 
																<input type="hidden" name="overtimeDeducts[!{index}].fdCategoryId" value="${sysAttendCategoryForm.fdId}" />
				   												<xform:datetime minuteStep="1" property="overtimeDeducts[!{index}].fdStartTime" required="true" dateTimeType="time" validators="startCompareEnd timeRangeMixed" style="width:100px"
				   												htmlElementProperties="over-time-deducts" subject="${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.fdStartTime') }"></xform:datetime>
									    						<span style="float: left;margin:0 20px;">—</span>
									    						<xform:datetime minuteStep="1" property="overtimeDeducts[!{index}].fdEndTime" required="true" dateTimeType="time" validators="startCompareEnd2 timeRangeMixed" style="width:100px" subject="${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.fdEndTime') }"></xform:datetime>
				   												<a href="javascript:void(0);" onclick="deleteOvtTimePosition();" title="${lfn:message('doclist.delete')}">
																	<div class="lui_icon_s lui_icon_s_icon_minus_sign"></div>
																</a>
																<a href="javascript:void(0);" class="add-btn" onclick="addPosition('overtimeDeducts');" title="${lfn:message('doclist.add')}">
																	<div class="lui_icon_s lui_icon_s_icon_plus_sign"></div>
																</a>
				   											</td>
				   										</tr>
				   										<c:forEach items="${sysAttendCategoryForm.overtimeDeducts}" var="overtimeDeduct" varStatus="vstatus">
				   											<tr KMSS_IsContentRow="1" class="timePeriod">
				   												<input type="hidden" name="overtimeDeducts[${vstatus.index}].fdId" value="${overtimeDeduct.fdId}" /> 
																<input type="hidden" name="overtimeDeducts[${vstatus.index}].fdCategoryId" value="${sysAttendCategoryForm.fdId}" />
				   												<td>
				    												<xform:datetime minuteStep="1" property="overtimeDeducts[${vstatus.index}].fdStartTime" required="true" dateTimeType="time" validators="startCompareEnd timeRangeMixed" style="width:100px"
				    												htmlElementProperties="over-time-deducts" subject="${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.fdStartTime') }"></xform:datetime>
										    						<span style="float: left;margin:0 20px;">—</span>
										    						<xform:datetime minuteStep="1" property="overtimeDeducts[${vstatus.index}].fdEndTime" required="true" dateTimeType="time" validators="startCompareEnd2 timeRangeMixed" style="width:100px" subject="${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.fdEndTime') }"></xform:datetime>
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
				    								<xform:text property="overtimeDeducts[0].fdThreshold" validators="digits min(1) thresholdBiggerHours" required="true" style="height: 22px;width:40px;margin: 0 3px;float:none;" subject="${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.thresholdSubjct') }"></xform:text>
				    								${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.deductTips') }
				    								<xform:text property="overtimeDeducts[0].fdDeductHours" validators="digits min(1) thresholdBiggerHours2" required="true" style="height: 22px;width:40px;margin: 0 3px;float:none;" subject="${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.hoursSubject') }"></xform:text>
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
													<xform:select showStatus="${fdRoundingType}" property="fdRoundingType" style="float: none;" showPleaseSelect="false" onValueChange="changeRoundingType">
			   											<xform:simpleDataSource value="0">${ lfn:message('sys-attend:sysAttendCategory.fdRoundingType.no') }</xform:simpleDataSource>
			   											<xform:simpleDataSource value="1">${ lfn:message('sys-attend:sysAttendCategory.fdRoundingType.upper') }</xform:simpleDataSource>
			   											<xform:simpleDataSource value="2">${ lfn:message('sys-attend:sysAttendCategory.fdRoundingType.lower') }</xform:simpleDataSource>
			   										</xform:select>
			   										&nbsp;&nbsp;
			   										<span id="overtimeHour" style="display:none;">
				   										<span>${ lfn:message('sys-attend:sysAttendCategory.overtime.unit') }</span>
			   											<xform:text property="fdMinUnitHour" validators="minUnitHour max(24)" style="height: 22px;width:40px;margin: 0 3px;float:none;" subject="${ lfn:message('sys-attend:sysAttendCategory.overtime.unit') }"></xform:text>			   											
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
			    				<div class="caption" style="padding-top: 17px;text-align: center;">
			    					${ lfn:message('sys-attend:sysAttendCategory.fdIsPatch') }
			    				</div>
			    				<div class="content">
			    					<table class="tb_simple" width="100%">
						    			<tr>
						    				<td>
						    					<ui:switch property="fdIsPatch" enabledText="${ lfn:message('sys-attend:sysAttendCategory.fdIsPatch.yes') }" disabledText="${ lfn:message('sys-attend:sysAttendCategory.fdIsPatch.no') }" onValueChange="changeIsPatch();"></ui:switch>
						    				</td>
						    			</tr>
						    			<tr class="patchContent">
						    				<td>
						    					${ lfn:message('sys-attend:sysAttendCategory.fdPatchTimes.text1') }
												<xform:text property="fdPatchTimes" style="width:50px;margin:0 3px;float:none;display:inline-block;" validators="digits min(1) max(99)"></xform:text>
												${ lfn:message('sys-attend:sysAttendCategory.fdPatchTimes.text2') }
												<span style="color: #999;">${ lfn:message('sys-attend:sysAttendCategory.fdPatchTimes.tips') }</span>
						    				</td>
						    			</tr>
						    			<tr class="patchContent">
						    				<td>
						    					${ lfn:message('sys-attend:sysAttendCategory.fdPatchDay.text1') }
												<xform:text property="fdPatchDay" style="width:50px;margin:0 3px;float:none;display:inline-block;" validators="digits min(0) max(180)"></xform:text>
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
				    					<input type="hidden" name="busSettingForms[!{index}].fdCategoryId" value="${sysAttendCategoryForm.fdId }" />
					    				<xform:text property="busSettingForms[!{index}].fdBusName" required="true" subject="${ lfn:message('sys-attend:sysAttendCategory.business.name') }"></xform:text>
					    			</td>
					    			<td>
					    				<xform:select property="busSettingForms[!{index}].fdBusType" showPleaseSelect="true" required="true" subject="${ lfn:message('sys-attend:sysAttendCategory.business.type') }">
					    					<xform:enumsDataSource enumsType="sysAttendCategoryBus_fdBusType"></xform:enumsDataSource>
					    				</xform:select>
					    			</td>
					    			<td>
					    				<xform:dialog required="true" propertyId="busSettingForms[!{index}].fdTemplateId" propertyName="busSettingForms[!{index}].fdTemplateName" showStatus="edit" style="width:80%" className="inputsgl" subject="${ lfn:message('sys-attend:sysAttendCategory.business.template') }">
										   selectTemplate('busSettingForms[!{index}].fdTemplateId', 'busSettingForms[!{index}].fdTemplateName');
										</xform:dialog>
					    			</td>
					    			<td>
					    				<div class="lui-attend-bus-setting-delete" onclick="DocList_DeleteRow();">
										</div>
					    			</td>
					    		</tr>
					    		<c:forEach items="${sysAttendCategoryForm.busSettingForms}" var="busSettingItem" varStatus="vstatus">
					    			<tr KMSS_IsContentRow="1">
					    				<input type="hidden" name="busSettingForms[${vstatus.index }].fdId" value="${busSettingItem.fdId }" />
					    				<input type="hidden" name="busSettingForms[${vstatus.index }].fdCategoryId" value="${sysAttendCategoryForm.fdId }" />
					    				<td>
					    					<xform:text property="busSettingForms[${vstatus.index }].fdBusName" required="true" subject="${ lfn:message('sys-attend:sysAttendCategory.business.name') }"></xform:text>
						    			</td>
						    			<td>
						    				<xform:select property="busSettingForms[${vstatus.index }].fdBusType" showPleaseSelect="true" required="true" subject="${ lfn:message('sys-attend:sysAttendCategory.business.type') }">
						    					<xform:enumsDataSource enumsType="sysAttendCategoryBus_fdBusType"></xform:enumsDataSource>
						    				</xform:select>
						    			</td>
						    			<td>
						    				<xform:dialog required="true" propertyId="busSettingForms[${vstatus.index }].fdTemplateId" propertyName="busSettingForms[${vstatus.index }].fdTemplateName" showStatus="edit" style="width:80%" className="inputsgl" subject="${ lfn:message('sys-attend:sysAttendCategory.business.template') }">
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
			    						<xform:text property="fdNotifyOnTime" validators="digits min(1) max(1000)" style="width:50px;margin-right:8px;"></xform:text>
											${ lfn:message('sys-attend:sysAttendCategory.notify.on.tips') }
									</td>
			    				</tr>
			    				<tr>
			    					<td class="td_normal_title">
			    						${ lfn:message('sys-attend:sysAttendCategory.notify.off.title') }
			    					</td>
			    					<td>
			    						<xform:text property="fdNotifyOffTime" validators="digits min(1) max(1000)" style="width:50px;margin-right:8px;"></xform:text>${ lfn:message('sys-attend:sysAttendCategory.notify.off.tips') }
			    					</td>
			    				</tr>
			    				<tr>
			    					<td class="td_normal_title">
			    						${ lfn:message('sys-attend:sysAttendCategory.notify.result') }
			    					</td>
			    					<td>
			    						<xform:checkbox property="fdNotifyResult" style="margin-right: 5px">
			    							<xform:simpleDataSource value="true">${ lfn:message('sys-attend:sysAttendCategory.notify.result.tips') }</xform:simpleDataSource>
			    						</xform:checkbox>


									</td>
			    				</tr>
			    				<tr>
			    					<td class="td_normal_title">
			    						${ lfn:message('sys-attend:sysAttendCategory.fdNotifyAttend') }
			    					</td>
			    					<td>
			    						<xform:checkbox property="fdNotifyAttend" style="margin-right: 5px">
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
				    						<xform:radio mock="true" property="fdSecurityMode" showStatus="edit" onValueChange="onSecurityMode">
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
			<ui:tabpage expand="false">
				<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="sysAttendCategoryForm" />
					<c:param name="moduleModelName" value="com.landray.kmss.sys.attend.model.SysAttendCategory" />
				</c:import>
			</ui:tabpage>

			<div id="locationsTempHiddenDiv" style="display: none"></div>
		</html:form>
	</template:replace>
</template:include>
<script>

<c:if test="${sysAttendCategoryForm.method_GET eq 'edit'}">
$("#limitLocationsList table[id*='locationsList_']").each(function(){
	if(this.id.indexOf("index")==-1){
		DocList_Info.push(this.id);
	}
});
var _flag="edit";
</c:if>
DocList_Info.push("limitLocationsList","wTimeSheet","wifiConfigs","busSettingList","overtimeDeducts");
	var cateValidation = $KMSSValidation(document.forms['sysAttendCategoryForm']);

	LUI.ready(function(){
		// 防止分类为空
		<c:if test="${sysAttendCategoryForm.method_GET=='add' && empty JsParam.fdATemplateId}">
	    	seajs.use(['sys/ui/js/dialog'],	function(dialog) {
				dialog.simpleCategoryForNewFile('com.landray.kmss.sys.attend.model.SysAttendCategoryATemplate',
						'/sys/attend/sys_attend_category/sysAttendCategory.do?method=add&type=attend&fdATemplateId=!{id}',
						false, function(res) {
							if(!res) {
								window.close();
							}
						}, null, null, "_self", true);
			});
		</c:if>
		// 防止分类为空
		<c:if test="${sysAttendCategoryForm.method_GET=='edit' && empty sysAttendCategoryForm.fdATemplateId}">
	    	seajs.use(['lui/dialog'],function(dialog){
	    		dialog.simpleCategory('com.landray.kmss.sys.attend.model.SysAttendCategoryATemplate','fdATemplateId','fdATemplateName', 
	    				false, function(res){
	    			if(res && res['id'] && res['name']) {
	    				$('[name="fdATemplateId"]:hidden').val(res['id']);
	    				$('#fdATemplateName').html(res['name']);
	    			} else {
	    				window.close();
	    			}
	    		}, null, true, null, true);
	    	});
		</c:if>
		// 判断是一班制还是两班制
		<c:if test="${sysAttendCategoryForm.fdWork==2}">
			hideAndDisabled('absFullBody');
		</c:if>
		<c:if test="${sysAttendCategoryForm.fdWork==1}">
			showAndAbled('absFullBody');
		</c:if>
		// 添加一个地点
		<c:if test="${sysAttendCategoryForm.method_GET eq 'add'}">
		setTimeout(function(){
			var rowNumber = $("#limitLocationsList").find("[id*='locationsList_']").length; 
			if(rowNumber ==0){
				DocList_AddRow('limitLocationsList');
			}
		},800);
		</c:if>
		// 判断加班是否为不取整
		<c:if test="${sysAttendCategoryForm.fdRoundingType eq '1'||sysAttendCategoryForm.fdRoundingType eq '2'}">
			showAndAbled('overtimeHour');
		</c:if>
		//编辑时注册on事件
		<c:if test="${sysAttendCategoryForm.method_GET eq 'edit'}">
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
		</c:if>
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
		initWorkType();
		//初始化旷工规则
		initAbsentTime();
		// 班制类型
		initFdShiftType();
		changeIsFlex('${sysAttendCategoryForm.fdIsFlex}');
		changeIsPatch();
		// 计算总工时
		<c:if test="${sysAttendCategoryForm.method_GET eq 'edit' && empty sysAttendCategoryForm.fdTotalTime}">
			calTotalTime();
		</c:if>
		setTimeout(function(){
			initWorkType();
			changefdIsOvt('${sysAttendCategoryForm.fdIsOvertime}');
			onChangePosCount();
			onMapChange(true);
	        onWifiChange(true);
		}, 600);
	});
	
	// 初始化旷工规则
	window.initAbsentTime = function() {
		var fdLateToAbsentTime = $('[name="fdLateToAbsentTime"]:enabled').val();
		if (fdLateToAbsentTime==0) {
			$('[name="fdLateToAbsentTime"]').val('');
		}
		
		var fdLeftToAbsentTime = $('[name="fdLeftToAbsentTime"]:enabled').val();
		if (fdLeftToAbsentTime==0) {
			$('[name="fdLeftToAbsentTime"]').val('');
		}
		
		var fdLateToFullAbsTime = $('[name="fdLateToFullAbsTime"]:enabled').val();
		if (fdLateToFullAbsTime==0) {
			$('[name="fdLateToFullAbsTime"]').val('');
		}
		
		var fdLeftToFullAbsTime = $('[name="fdLeftToFullAbsTime"]:enabled').val();
		if (fdLeftToFullAbsTime==0) {
			$('[name="fdLeftToFullAbsTime"]').val('');
		}
	};
	// 初始化班次类型
	window.initWorkType = function() {
		var fdWork = '${sysAttendCategoryForm.fdWork }';
		var shiftType = '${sysAttendCategoryForm.fdShiftType}';
		if(!fdWork)
			return;
		var workTypeField = $('input[name="fdWork"]:hidden');
		if(fdWork ==='1'){
			workTypeField.val('1');
			hideAndRemoveVld($('#twiceWorkTime'));
			$('[name="fdWorkTime[0].fdIsAvailable"]').val('true');
			$('[name="fdWorkTime[1].fdIsAvailable"]').val('false');
			showAndAbled('endTimeOnce');
			showAndAbled('overTimeTypeOnce');
			hideAndDisabled('endTimeTwice');
			hideAndDisabled('endTimeTwice2');
			hideAndDisabled('overTimeTypeTwice');
			if(!(shiftType === '3' || shiftType === '4')){
				showAndAbled('restTimeTB');
			}
			$('#onceType').addClass('active');
			$('#twiceType').removeClass('active');
		} else if(fdWork === '2'){
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
	
	// 初始化班制类型
	window.initFdShiftType = function() {
		var shiftType = '${sysAttendCategoryForm.fdShiftType}';
		var sameWork = '${sysAttendCategoryForm.fdSameWorkTime}';
		if(shiftType === '0' || shiftType === '4' || shiftType === '3') {

			if(shiftType === '3'){
				hideAndDisabled('fdSameWtimeDiv');
				hideAndDisabled('twiceType');
				hideAndDisabled('restTimeTB');
				hideAndDisabledOnceWorkTd();
				hideAndDisabled('totalTimeDiv');
				//总工时可输入并设置默认值 8
				$('input[name="fdTotalTime"]')[0].type = "text";
				$('input[name="fdTotalTime"]')[0].value = "8";
				$('input[name="fdTotalTime"]')[0].class = "inputsgl";
				hideAndDisabled('totalTimeDiv');
				showAndAbled('totalTimeComprehensiveDiv');
			}else if(shiftType === '14'){
				hideAndDisabled('fdSameWtimeDiv');
				hideAndDisabled('signTime');
				hideAndDisabled('restTimeTB');
				hideAndDisabled('div_totalTime');
				$('input[name="fdTotalTime"]')[0].value = "8";
			}else{
				hideAndDisabled('totalTimeComprehensiveDiv');
			}

			if(sameWork ==='' || sameWork === '0') {
				hideAndRemoveVld($('#notSameWTime'));
				$('#wTimeSheet tr').each(function(){
					$(this).find('[name$=".fdWorkTime[0].fdIsAvailable"]').val('false');
					$(this).find('[name$=".fdWorkTime[1].fdIsAvailable"]').val('false');
				}); 
				hideAndDisabled('customDateTd');

			} else if(sameWork === '1') {
				hideAndRemoveVld($('#sameWTime'));
				$('[name="fdWorkTime[0].fdIsAvailable"]').val('false');
				$('[name="fdWorkTime[1].fdIsAvailable"]').val('false');
				$('#wTimeSheet tr').each(function(){
					var fdWork = $(this).find('[name$=".fdWork"]').val();
					if(fdWork == '1') {
						$(this).find('[name$=".fdWorkTime[0].fdIsAvailable"]').val('true');
						$(this).find('[name$=".fdWorkTime[1].fdIsAvailable"]').val('false');
					} else if (fdWork == '2'){
						$(this).find('[name$=".fdWorkTime[0].fdIsAvailable"]').val('true');
						$(this).find('[name$=".fdWorkTime[1].fdIsAvailable"]').val('true');
					}
				});
				hideAndDisabled('appendTimeContent');
			}
			hidTimeArea();
		} else if(shiftType === '1'){
			showTimeArea();
		} else if(shiftType === '2') {
			hideAndRemoveVld($('#notSameWTime'));
			$('#wTimeSheet tr').each(function(){
				$(this).find('[name$=".fdWorkTime[0].fdIsAvailable"]').val('false');
				$(this).find('[name$=".fdWorkTime[1].fdIsAvailable"]').val('false');
			});
			hideAndDisabled('fdSameWtimeDiv');
			hideAndDisabled('weekTd');
			hideAndDisabled('excTimeContent');
			hideAndDisabled('appendTimeContent');
			hideAndDisabled('holidayContent');
			hideAndDisabled('totalTimeComprehensiveDiv');
			hidTimeArea();
		}
	};
	
	// 班制类型
	window.changeFdShiftType = function(v) {
		console.log(v);
		if(!v){
			return;
		}
		$('input[name="fdTotalTime"]')[0].type = "hidden";
		showAndAbledOnceWorkTd();
		hideAndDisabled('totalTimeComprehensiveDiv');
		showAndAbled('totalTimeDiv');
		if(v === '0' ||  v === '4' ||  v === '3' ) {
			showAndResetVld($('#sameWTime'));
			hideAndRemoveVld($('#notSameWTime'));
			showAndAbled('weekTd');
			hideAndDisabled('customDateTd');
			showAndAbled('excTimeContent');
			showAndAbled('appendTimeContent');
			showAndAbled('holidayContent');
			$('input[name="fdPeriodType"]:hidden').val('1');//兼容
			$('select[name="fdSameWorkTime"]').val('0');
			changeSameWTime('0');
			hidTimeArea();

			hideAndDisabled('totalTimeComprehensiveDiv');
			if(v === '14'){
				hideAndAbled('fdSameWtimeDiv');
				hideAndAbled('signTime');
				hideAndAbled('restTimeTB');
				hideAndAbled('div_totalTime');
				$('input[name="fdTotalTime"]')[0].value = "8";
			}else if(v === '3'){
				hideAndAbled('fdSameWtimeDiv');
				hideAndAbled('twiceType');
				hideAndAbled('restTimeTB');
				hideAndDisabledOnceWorkTd();
				hideAndAbled('totalTimeDiv');
				//总工时可输入并设置默认值 8
				$('input[name="fdTotalTime"]')[0].type = "text";
				$('input[name="fdTotalTime"]')[0].value = "8";
				$('input[name="fdTotalTime"]')[0].class = "inputsgl";
				showAndAbled('totalTimeComprehensiveDiv');
				showAndAbled('signTime');
				showAndAbled('div_totalTime');
				/*hideAndRemoveVld($('#sameWTime'));
				hideAndRemoveVld($('#notSameWTime'));
				hideAndDisabled('fdSameWtimeDiv');
				hideAndDisabled('lateBody');
				hideAndDisabled('flexContent');*/

			}else{
				showAndAbled('fdSameWtimeDiv');
				showAndAbled('signTime');
				showAndAbled('restTimeTB');
				showAndAbled('div_totalTime');
				showAndAbled('twiceType');
				showAndAbledOnceWorkTd();
				showAndAbled('totalTimeDiv');
				hideAndDisabled('totalTimeComprehensiveDiv');
				$('input[name="fdTotalTime"]')[0].type = "hidden";
			}

		} else if(v === '1') {
			showTimeArea();
		} else if(v === '2') {
			showAndAbled('twiceType');
			showAndResetVld($('#sameWTime'));
			hideAndRemoveVld($('#notSameWTime'));
			$('#wTimeSheet tr').each(function(){
				$(this).find('[name$=".fdWorkTime[0].fdIsAvailable"]').val('false');
				$(this).find('[name$=".fdWorkTime[1].fdIsAvailable"]').val('false');
			}); 
			hideAndDisabled('fdSameWtimeDiv');
			hideAndDisabled('weekTd');
			showAndAbled('customDateTd');
			hideAndDisabled('excTimeContent');
			hideAndDisabled('appendTimeContent');
			hideAndDisabled('holidayContent');
			$('input[name="fdPeriodType"]:hidden').val('2');//兼容
			changeWorkType('1');
			hidTimeArea();
		}
		
	};
	
	window.showTimeArea = function(){
		hideAndRemoveVld($('#sameWTime'));
		hideAndDisabled('fdSameWtimeDiv');
		hideAndRemoveVld($('#notSameWTime'));
		hideAndRemoveVld($('#flexContent'));
		hideAndDisabled('excTimeContent');
		hideAndDisabled('appendTimeContent');
		hideAndDisabled('holidayContent');
		
		showAndResetVld($('#timeAreaTimeContent'));
		showAndAbled('timeAreaTimeContent');
		$('#onceWorkTime select[name="fdEndDay"]').prop('disabled', 'disabled');
	},
	window.getShiftType = function(){
		var _shiftType = '${sysAttendCategoryForm.fdShiftType}';
		var method = "${sysAttendCategoryForm.method_GET}";
		var fdStatus = "${sysAttendCategoryForm.fdStatus}";
		if(method=='add' || method=='edit' && fdStatus=='2'){
			_shiftType = $('input[name="fdShiftType"]:checked').val();
		}
		return _shiftType;
	},
	window.hidTimeArea = function(){
		showAndResetVld($('#flexContent'));
		hideAndDisabled('timeAreaTimeContent');
		hideAndRemoveVld($('#timeAreaTimeContent'));
		$('#onceWorkTime select[name="fdEndDay"]').removeAttr('disabled');
	},
	//选择排班信息
	window.selTimeArea = function(){
		Dialog_List(true, "fdTimeAreaIds", "fdTimeAreaNames", ';',"sysTimeService",function(data){
			if(!data){
				return;
			}
		},"sysTimeService&search=!{keyword}", false, false,"${ lfn:message('sys-attend:sysAttend.tree.config.stat.selCate') }");
							
	}
	window.changeSameWTime = function(v) {
		if(!v) {
			return;
		}
		if(v == '1') {
			hideAndRemoveVld($('#sameWTime'));
			showAndResetVld($('#notSameWTime'));
			$('[name="fdWorkTime[0].fdIsAvailable"]').val('false');
			$('[name="fdWorkTime[1].fdIsAvailable"]').val('false');
			$('#wTimeSheet tr').each(function(){
				var fdWork = $(this).find('[name$=".fdWork"]').val();
				if(fdWork == '1') {
					$(this).find('[name$=".fdWorkTime[0].fdIsAvailable"]').val('true');
					$(this).find('[name$=".fdWorkTime[1].fdIsAvailable"]').val('false');
				} else if (fdWork == '2'){
					$(this).find('[name$=".fdWorkTime[0].fdIsAvailable"]').val('true');
					$(this).find('[name$=".fdWorkTime[1].fdIsAvailable"]').val('true');
				}
			});
			hideAndDisabled('appendTimeContent');
		} else {
			showAndResetVld($('#sameWTime'));
			hideAndRemoveVld($('#notSameWTime'));
			$('#wTimeSheet tr').each(function(){
				$(this).find('[name$=".fdWorkTime[0].fdIsAvailable"]').val('false');
				$(this).find('[name$=".fdWorkTime[1].fdIsAvailable"]').val('false');
			}); 
			changeWorkType('1');
			hideAndDisabled('customDateTd');
			showAndAbled('appendTimeContent');
		}
	};
	
	window.changeRoundingType = function(v) {
		if(!v) {
			return;
		}
		if(v == '0') {		
			hideAndDisabled('overtimeHour');
			$('[name="fdMinUnitHour"]').val('');
			cateValidation.validateElement($('[name="fdMinUnitHour"]')[0]);
		}
		else{
			showAndAbled('overtimeHour');
		}
	}
	
	// 班次类型，一班制还是两班制
	window.changeWorkType = function(v) {
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
			setTimeout(function() {
				calTotalTime();
			}, 0);
			showAndAbled('absFullBody');
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
			$('[name="fdWorkTime[1].fdStartTime"]').val('14:00');
			$('[name="fdWorkTime[1].fdEndTime"]').val('18:00');
			$('[name="fdEndTime1"]').val('14:00');
			$('[name="fdStartTime2"]').val('12:00');
			setTimeout(function() {
				calTotalTime();
			}, 0);
			hideAndDisabled('absFullBody');
		}
	};
	
	// 计算总工时
	window.calTotalTime = function () {
		var workType = $('input[name="fdWork"]:hidden').val();
		var on1 = $('[name="fdWorkTime[0].fdStartTime"]:enabled:visible').val();
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
			var dateStart1=getDateTime(on1,1);
			var dateEnd1=getDateTime(off1,type1);
			/* var onMin1 = parseInt(on1.split(':')[0]) * 60 + parseInt(on1.split(':')[1]);
			var offMin1 = parseInt(off1.split(':')[0]) * 60 + parseInt(off1.split(':')[1]); */
			var onMin1 = dateStart1.getTime();
			var offMin1 = dateEnd1.getTime();
			if(offMin1 > onMin1) {
				totalTime = (offMin1 - onMin1)/(60*1000);
				if(restStart && restEnd) {
					//午休时间
					var fdRestStartType = $('select[name="fdRestStartType"]:enabled').val();
					var fdRestEndType = $('select[name="fdRestEndType"]:enabled').val();
					var restStartDate = getDateTime(restStart,fdRestStartType);
					var restEndDate = getDateTime(restEnd,fdRestEndType);
					if(restEndDate.getTime() > restStartDate.getTime()) {
						var restLongTime = restEndDate.getTime() - restStartDate.getTime();
						var restMins = restLongTime / 1000 / 60;
						totalTime = totalTime - restMins;
					}
				}
			}
			totalTime = parseFloat((totalTime / 60).toFixed(2));
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
				totalTime = parseFloat(((offMin2 - onMin2 + offMin1 - onMin1) / (60*60*1000)).toFixed(2));
			}
		}
		totalTime = totalTime < 0 ? 0 : totalTime;
		totalTimeDiv.html(totalTime  + "${ lfn:message('sys-attend:sysAttendCategory.hour') }");
		fdTotalTime.val(totalTime);
	};
	
	// 选择出差/请假/出差/外出关联的流程
	window.selectTemplate = function selectTemplate(fdTemplateId, fdTemplateName){
		seajs.use(['sys/ui/js/dialog'], function(dialog) {
			dialog.category({
				modelName:"com.landray.kmss.km.review.model.KmReviewTemplate",
				idField: fdTemplateId,
				nameField: fdTemplateName,
				mulSelect:false,
				winTitle:"${ lfn:message('sys-attend:sysAttendCategory.select.template') }",
				canClose:true,
				isShowTemp:true,
				authType:"02",
				notNull:true
			});
	   });
	}
	
	// 是否加班
	window.changefdIsOvt = function(value) {
		var fdIsOvertime = value || $(':hidden[name="fdIsOvertime"]').val();
		if(fdIsOvertime == 'true') {
			showAndAbled('ovtReview');
			showAndAbled('deductSwitch');
			showAndAbled('ovtMinHour');
			showAndAbled('ovtMinHourTips');	
			showAndAbled('roundingRules');	
			changefdIsOvtDeduct();
		} else {
			hideAndDisabled('ovtMinHourTips');
			hideAndDisabled('ovtMinHour');
			hideAndDisabled('ovtReview');
			hideAndDisabled('deductSwitch');
			hideAndDisabled('deductTips');
			hideAndDisabled('ovtDeduct');
			hideAndDisabled('timePeriod');
			hideAndDisabled('timethreshold');
			hideAndDisabled('roundingRules');
			hideOvtTempl();
		}
	}
	
	// 加班需审批
	window.changefdOvtReviewType = function() {
		var fdOvtReviewType = $(':radio[name="fdOvtReviewType"]:checked');
		if((fdOvtReviewType.val() == '1' || fdOvtReviewType.val() == '2') && fdOvtReviewType.is(':enabled')) {
			hideAndDisabled('ovtMinHour');
			showOvtTempl();
		} else {
			showAndAbled('ovtMinHour');
			hideOvtTempl();
		}
	}
	
	// 是否加班扣除
	window.changefdIsOvtDeduct = function(value) {
		var fdIsOvertimeDeduct = value || $(':hidden[name="fdIsOvertimeDeduct"]').val();
		if(fdIsOvertimeDeduct == 'true') {
			showAndAbled('ovtDeduct');
			showAndAbled('deductTips');
			changefdOvtDeductType();
		} else {
			hideAndDisabled('deductTips');
			hideAndDisabled('ovtDeduct');
			hideAndDisabled('timePeriod');
			hideAndDisabled('timethreshold');
		}
	}
	
	// 选择加班扣除方式
	window.changefdOvtDeductType = function() {
		var fdOvtDeductType = $(':radio[name="fdOvtDeductType"]:checked');
		if(fdOvtDeductType.val() == '0' && fdOvtDeductType.is(':enabled')) {
			hideAndDisabled('timethreshold');
			showAndAbled('timePeriod');
			var deductPeriod = $('#overtimeDeducts [over-time-deducts]').length;
			if(deductPeriod <= '0'){ 	
				DocList_AddRow('overtimeDeducts')
			}
		} else if(fdOvtDeductType.val() == '1' && fdOvtDeductType.is(':enabled')) {
			hideAndDisabled('timePeriod');
			showAndAbled('timethreshold');
		}
	}
	
	// 初始化扣除加班类型
	window.initDeductType = function() {
		var fdIsOvertime = '${sysAttendCategoryForm.fdIsOvertime}' || $(':hidden[name="fdIsOvertime"]').val();
		var fdIsOvertimeDeduct = '${sysAttendCategoryForm.fdIsOvertimeDeduct}';

		if (fdIsOvertimeDeduct == 'false' || fdIsOvertime == 'false' || fdIsOvertimeDeduct == ''){
			hideAndDisabled('ovtDeduct');
			/* hideAndDisabledForClass('timePeriod'); */
			hideAndDisabled('timethreshold');
			return;
		}
		showAndAbled('deductTips');
		var fdOvtDeductType = '${sysAttendCategoryForm.fdOvtDeductType}';
		if (fdOvtDeductType == '0'){
			hideAndDisabled('timethreshold');
			showAndAbled('timePeriod');
		} else if(fdOvtDeductType == '1'){
			showAndAbled('timethreshold');
			hideAndDisabled('timePeriod');
		}
	}
	
	// 展示加班流程
	var showOvtTempl = function(){
		var selectEles = $('#busSettingList select[name$="fdBusType"]');
		for(var i in selectEles) {
			if(selectEles[i].value == '6')
				return;
		}
		var newTR = DocList_AddRow('busSettingList');
		$(newTR).find(':text[name$="fdBusName"]').val("${ lfn:message('sys-attend:sysAttendMain.fdStatus.overtime') }");
		$(newTR).find('select[name$="fdBusType"]').val('6');
	}
	
	// 隐藏加班流程
	var hideOvtTempl = function(){
		var selectEles = $('#busSettingList select[name$="fdBusType"]');
		for(var i in selectEles) {
			if(selectEles[i].value != '6')
				continue;
			var delTR = $(selectEles[i]).closest('tr');
			DocList_DeleteRow(delTR[0]);
		}
	}
	
	// 弹性上下班
	window.changeIsFlex = function(value) {
		var fdIsFlex = value || $('input[name="fdIsFlex"]').val();
		if(fdIsFlex === 'true') {
			hideAndDisabled('lateBody');
			hideAndDisabled('leftBody');
			showAndAbled('flexTimeTd');
		} else {
			showAndAbled('lateBody');
			showAndAbled('leftBody');
			hideAndDisabled('flexTimeTd');
		}
	};
	
	// 外勤需审批
	window.changeFdOutside = function(){
		if($('[name="__fdOutside"]:hidden').val() == 'true') {
			$('[name="fdRule[0].fdOutside"]:hidden').val('true');
			showAndAbled('osdReviewType')
		} else {
			$('[name="fdRule[0].fdOutside"]:hidden').val('false');
			hideAndDisabled('osdReviewType')
		}
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

	 var hideAndDisabledOnceWorkTd = function(){
		 hideAndDisabled("onceWorkTd1");
		 hideAndDisabled("onceWorkTd2");
		 hideAndDisabled("onceWorkTd3");
		 hideAndDisabled("onceWorkTd4");
	 };
	 var showAndAbledOnceWorkTd = function(){
		 showAndAbled("onceWorkTd1");
		 showAndAbled("onceWorkTd2");
		 showAndAbled("onceWorkTd3");
		 showAndAbled("onceWorkTd4");
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
	
	var hideAndDisabledForClass= function(clazz) {
		var parentDom = $('.' + clazz);
		if(!parentDom)
			return;
		var childInputs = parentDom.find(':input');
		if(childInputs)
			childInputs.prop('disabled', 'disabled');
		parentDom.hide();
	};
	
	var hideAndAbled= function(id) {
		var parentDom = $('#' + id);
		if(!parentDom)
			return;
		var childInputs = parentDom.find(':input');
		parentDom.hide();
	};
	
	var hideAndEnabled= function(id) {
		var parentDom = $('#' + id);
		if(!parentDom)
			return;
		var childInputs = parentDom.find(':input');
		parentDom.hide();
	};
	
	var showAndResetVld = function(ele) {
		$(ele).show();
		cateValidation.resetElementsValidate($(ele));
	};
	
	var hideAndRemoveVld = function(ele) {
		$(ele).hide();
		cateValidation.removeElements($(ele));
	};

	var removeVld = function(ele) {
		//$(ele).hide();
		//cateValidation.removeElements($(ele));
		$(ele).remove();
	};

	// 标题展开收起事件
	var bindTitleEvent  = function(){
		$('#signTargetsTitle').on('click',function(){
			$('#signTargetsBody').slideToggle();
		});
		$('#signTimeTitle').on('click',function(){
			$('#signTimeBody').slideToggle();
		});
		$('#signTypeTitle').on('click',function(){
			$('#signTypeBody').slideToggle();
		});
		$('#signOffTitle').on('click',function(){
			$('#signOffBody').slideToggle();
		});
		$('#signNotifyTitle').on('click',function(){
			$('#signNotifyBody').slideToggle();
		});
		$('#ruleTitle').on('click',function(){
			$('#ruleBody').slideToggle();
		});
		$('#securityTitle').on('click',function(){
			$('#securityBody').slideToggle();
		});
	}
	
	// 添加地点或WIFI
	window.addPosition = function(listName,obj) {
		if(listName.indexOf("locationsList_")>-1){
			var pidx=listName.replace("locationsList_","");
			var tableobj=$("#locationDemo tbody");
			var reg = new RegExp("pidx", "g" );
			var reg2 = new RegExp("idx", "g" );
			var reg3 = new RegExp("attrname", "g" );
			var htmlobj=tableobj.html().replace(reg,pidx).replace(reg2,$("#"+listName)[0].rows.length-1).replace(reg3,"name");
			var tableobj;
			if(listName==null)
				tableobj = DocListFunc_GetParentByTagName("TABLE");
			else if(typeof(listName)=="string")
				tableobj = document.getElementById(listName);
			var tbInfo = DocList_TableInfo[tableobj.id];
			if(tbInfo.lastIndex=="undefined"||tbInfo.lastIndex==undefined){
				tbInfo.lastIndex=1;
			}
			var newRow = tableobj.insertRow(tbInfo.lastIndex);
			var newCell = newRow.insertCell(0);
			newCell.innerHTML = $(htmlobj).html();
			tbInfo.lastIndex++;
			// 地图组件在明细表中添加会多出一行
			$("#"+listName).trigger($.Event("table-add-child"),newRow);
		}else{
			DocList_AddRow(listName);
		}
		setTimeout(function(){
			onChangePosCount();
		}, 300);
	};
	
	
	// 删除地点或WIFI
	window.deletePosition = function(type){
		if(type && type.indexOf("locationsList_")>-1){
			//删除范围
			var locationCount = $("table[id*='locationsList_']").length;
			var wifiCount = $('#wifiConfigs [data-wifi-config]').length;
			if(locationCount <= 1 && wifiCount == 0 || locationCount == 0 && wifiCount <= 1){
				seajs.use('lui/dialog',function(dialog){
					dialog.alert("${ lfn:message('sys-attend:sysAttendCategory.validate.position') }", null, 'none');
				});
				return;
			}
			var tbInfo = DocList_TableInfo["limitLocationsList"];
			var index=type.replace("locationsList_","");
			var optTR = tbInfo.DOMElement.rows[parseInt(index)+1];
			DocList_DeleteRow_ClearLast(optTR,type);
			//更新DocList_TableInfo
			var beginMake=false;
			for(var arr in DocList_TableInfo){
				if(arr==type){
					beginMake=true;
				}
				if(beginMake && arr.indexOf("locationsList_")>-1){
					var arrIndex=arr.replace("locationsList_","");
					if(DocList_TableInfo["locationsList_"+(parseInt(arrIndex)+1)]){
						for(var io in DocList_TableInfo["locationsList_"+(parseInt(arrIndex)+1)]){}
						DocList_TableInfo[arr].DOMElement=DocList_TableInfo["locationsList_"+(parseInt(arrIndex)+1)].DOMElement;	
						DocList_TableInfo[arr].cells=DocList_TableInfo["locationsList_"+(parseInt(arrIndex)+1)].cells;	
						DocList_TableInfo[arr].lastIndex=DocList_TableInfo["locationsList_"+(parseInt(arrIndex)+1)].lastIndex;	
						DocList_TableInfo[arr].firstIndex=DocList_TableInfo["locationsList_"+(parseInt(arrIndex)+1)].firstIndex;	
					}else{
						DocList_TableInfo[arr]=null;
					}					
				}
			}
			//更新下on事件
		}else{
			//删除地点
			var locationCount = $("table[id*='locationsList_'] div[data-location-container]").length;
			var wifiCount = $('#wifiConfigs [data-wifi-config]').length;
			if(locationCount <= 1 && wifiCount == 0 || locationCount == 0 && wifiCount <= 1){
				seajs.use('lui/dialog',function(dialog){
					dialog.alert("${ lfn:message('sys-attend:sysAttendCategory.validate.position') }", null, 'none');
				});
				return;
			}
			DocList_DeleteRow_ClearLast(null,type);
		}
		
		setTimeout(function(){
			onChangePosCount();
		}, 300);
	};
	
	// 删除加班扣除的时间段
	window.deleteOvtTimePosition = function(){
		var deductPeriod = $('#overtimeDeducts [over-time-deducts]').length;
		if(deductPeriod <= 1){
			seajs.use('lui/dialog',function(dialog){
				dialog.alert("休息时间段，必须设置至少要有一项", null, 'none');
			});
			return;
		}
		DocList_DeleteRow_ClearLast();
	};
	
	// 监听地点和WIFI数量的变化
	window.onChangePosCount = function() {
		var locationCount = $("table[id*='locationsList_'] div[data-location-container]").length;
		var wifiCount = $('#wifiConfigs [data-wifi-config]').length;
		if(locationCount == 0 && wifiCount > 0) {
			hideAndDisabled('fdLimitTR');
			if(!$('#fdOutsideWgt :checkbox').is(':checked')) {
				$('#fdOutsideWgt :checkbox').click();
			}
		} else {
			showAndAbled('fdLimitTR');
		}
	};
	
	window.onDingChange = function(){
		onOnlyDingEnable();
	};
	
	window.onMapChange = function(isReady) {
		if($('[name="fdCanMap"]:hidden').val() == 'false'){
			$("#limitLocationsList tr:gt(0)").hide();
			$("#limitLocationsList tr:gt(0)").find(":input").prop('disabled', 'disabled');
			//hideAndDisabled('fdMapTR');
			//hideAndDisabled('fdLimitTR');
			//判断wifi是否开启
			if($('[name="fdCanWifi"]:hidden').val() == 'false'){
				showOrHideOutside(false);
			}
		}else{
			//showAndAbled('fdMapTR');
			//showAndAbled('fdLimitTR');
			$("#limitLocationsList tr:gt(0)").show();
			$("#limitLocationsList tr:gt(0)").find(":input").removeAttr('disabled');
			showOrHideOutside(true);
		}
		if(!isReady)
			onOnlyDingEnable();     
    };
	
	window.onWifiChange = function(isReady) {
		if($('[name="fdCanWifi"]:hidden').val() == 'false'){
            hideAndDisabled('fdWifiTR');
          //判断map是否开启
			if($('[name="fdCanMap"]:hidden').val() == 'false'){
				showOrHideOutside(false);
			}
        }else{
            showAndAbled('fdWifiTR');
            showOrHideOutside(true);
        }
		if(!isReady)
			onOnlyDingEnable();
    };
	
	showOrHideOutside = function(isShow){
		if(isShow){
			$('#outsideBody').show();
			return;
		}
		//隐藏
		var value = $('input[name="fdRule[0].fdOutside"]').val();
		if(value=='true') {
			$('#fdOutsideWgt :checkbox').click();
		}
		$('#outsideBody').hide();
		
	}
	
	// 选择节假日
	function selHoliday(){
		Dialog_List(false, "fdHolidayId", "fdHolidayName", ';',"sysTimeHolidayService",function(data){
			var name = $("input[name='fdHolidayName']").val();
			$("#holidayNameDiv").text(name);
		},"sysTimeHolidayService&search=!{keyword}", false, false,"${ lfn:message('sys-attend:sysAttendCategory.select.holiday') }");
				
	}
	
	seajs.use(['sys/ui/js/dialog'], function(dialog) {
		window.addTimeSheet = function() {
			var newRow = DocList_AddRow('wTimeSheet');
			$(newRow).find('input').removeAttr('disabled');
			var fdWeek = $(newRow).find('[name$=".fdWeek"]');
			var fdWork = $(newRow).find('[name$=".fdWork"]');
			var fdWorkTimeId1 = $(newRow).find('[name$=".fdWorkTime[0].fdId"]');
			var fdIsAvailable1 = $(newRow).find('[name$=".fdWorkTime[0].fdIsAvailable"]');
			var fdOnTime1 = $(newRow).find('[name$=".fdWorkTime[0].fdStartTime"]');
			var fdOffTime1 = $(newRow).find('[name$=".fdWorkTime[0].fdEndTime"]');
			var fdOverTimeType1 = $(newRow).find('[name$=".fdWorkTime[0].fdOverTimeType"]');
			var fdWorkTimeId2 = $(newRow).find('[name$=".fdWorkTime[1].fdId"]');
			var fdIsAvailable2 = $(newRow).find('[name$=".fdWorkTime[1].fdIsAvailable"]');
			var fdOnTime2 = $(newRow).find('[name$=".fdWorkTime[1].fdStartTime"]');
			var fdOffTime2 = $(newRow).find('[name$=".fdWorkTime[1].fdEndTime"]');
			var fdOverTimeType2 = $(newRow).find('[name$=".fdWorkTime[1].fdOverTimeType"]');
			var fdStartTime1 = $(newRow).find('[name$=".fdStartTime1"]');
			var fdStartTime2 = $(newRow).find('[name$=".fdStartTime2"]');
			var fdEndTime1 = $(newRow).find('[name$=".fdEndTime1"]');
			var fdEndTime2 = $(newRow).find('[name$=".fdEndTime2"]');
			var fdEndDay = $(newRow).find('[name$=".fdEndDay"]');
			var fdRestStartTime = $(newRow).find('[name$=".fdRestStartTime"]');
			var fdRestEndTime = $(newRow).find('[name$=".fdRestEndTime"]');
			var fdTotalTime = $(newRow).find('[name$=".fdTotalTime"]');
			var fdTotalDay = $(newRow).find('[name$=".fdTotalDay"]');
			var fdRestEndType = $(newRow).find('[name$=".fdRestEndType"]');
			var fdRestStartType = $(newRow).find('[name$=".fdRestStartType"]');
			var fdWeeks = "";
			$('#wTimeSheet').find('[name$=".fdWeek"]').each(function(){
				fdWeeks += $(this).val() + ";";
			});
			
			var url = '/sys/attend/sys_attend_category/sysAttendCategory_edit_tsheet.jsp?'
				+ "fdWork=1&fdStartTime1=00:00&fdEndTime2=23:59&fdEndDay=1&fdRestStartTime=12:00&fdRestEndTime=13:00&fdTotalTime=8&fdOnTime1=09:00&fdOffTime1=18:00"
				+ "&fdWeeks=" + fdWeeks + '&fdIsAvailable1=true&fdIsAvailable1=false&fdOverTimeType1=1';
			
			dialog.iframe(url, "${ lfn:message('sys-attend:sysAttendCategory.timeSheet.setting') }", function(result){
				if(result) {
					var resultObj = {}; 
					$.each(result, function() {
						resultObj[this.name] = this.value;
				    });
					fdWeek.val(resultObj['fdWeek']);
					fdWork.val(resultObj['fdWork']);
					fdWorkTimeId1.val(resultObj['fdWorkTime[0].fdId']);
					fdIsAvailable1.val(resultObj['fdWorkTime[0].fdIsAvailable']);
					fdOnTime1.val(resultObj['fdWorkTime[0].fdStartTime']);
					fdOffTime1.val(resultObj['fdWorkTime[0].fdEndTime']);
					fdOverTimeType1.val(resultObj['fdWorkTime[0].fdOverTimeType']);
					fdWorkTimeId2.val(resultObj['fdWorkTime[1].fdId']);
					fdIsAvailable2.val(resultObj['fdWorkTime[1].fdIsAvailable']);
					fdOnTime2.val(resultObj['fdWorkTime[1].fdStartTime']);
					fdOffTime2.val(resultObj['fdWorkTime[1].fdEndTime']);
					fdOverTimeType2.val(resultObj['fdWorkTime[1].fdOverTimeType']);
					fdStartTime1.val(resultObj['fdStartTime1']);
					fdStartTime2.val(resultObj['fdStartTime2']);
					fdEndTime1.val(resultObj['fdEndTime1']);
					fdEndTime2.val(resultObj['fdEndTime2'] || resultObj['fdEndTime']);
					fdEndDay.val(resultObj['fdEndDay']);
					fdRestStartTime.val(resultObj['fdRestStartTime']);
					fdRestEndTime.val(resultObj['fdRestEndTime']);
					fdTotalTime.val(resultObj['fdTotalTime']);
					fdTotalDay.val(resultObj['fdTotalDay']);
					fdRestEndType.val(resultObj['fdRestEndType']);
					fdRestStartType.val(resultObj['fdRestStartType']);

					$(newRow).find('td:eq(0)').html(getFdWeekText(resultObj['fdWeek']));
					var fdWorkTimeText = '';
					if(resultObj['fdWork'] == '1') {
						if(resultObj['fdWorkTime[0].fdOverTimeType']=="2"){
							fdWorkTimeText="(${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') })";
						}
						fdWorkTimeText = resultObj['fdWorkTime[0].fdStartTime'] + '&nbsp;-&nbsp;' + resultObj['fdWorkTime[0].fdEndTime'] +fdWorkTimeText;
					} else if(resultObj['fdWork'] == '2'){
						if(resultObj['fdWorkTime[1].fdOverTimeType']=="2"){
							fdWorkTimeText="(${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') })";
						}
						fdWorkTimeText = resultObj['fdWorkTime[0].fdStartTime'] + '&nbsp;-&nbsp;' + resultObj['fdWorkTime[0].fdEndTime']
										+ ';&nbsp;' + resultObj['fdWorkTime[1].fdStartTime'] + '&nbsp;-&nbsp;' + resultObj['fdWorkTime[1].fdEndTime']+fdWorkTimeText;
					}
					$(newRow).find('td:eq(1)').html(fdWorkTimeText);
					$('#tSheetCountVld').hide();
					setTimeout(function() {
						if($('#wTimeSheet input[name^="fdTimeSheets["][name$="].fdWeek"]').length > 2) {
							$('#tSheetCountTips').show();
						}
					}, 0);
				} else {
					DocList_DeleteRow(newRow);
					setTimeout(function() {
						if($('#wTimeSheet input[name^="fdTimeSheets["]').length <= 0) {
							$('#tSheetCountVld').show();
						}
					}, 0);
				}
			},{width: 950, height: 500});
		};
		
		var getFdWeekText = function (fdWeek) {
			var fdWeekText = "";
			if(fdWeek) {
				var weekList = fdWeek.split(/[,;]/);
				weekList.sort();
				for(var i=0; i<weekList.length; i++) {
					var prefix = fdWeekText ? '、' : '';
					switch (weekList[i]) {
					case '1':fdWeekText += prefix + "${ lfn:message('sys-attend:sysAttendCategory.fdWeek.mon') }";break;
					case '2':fdWeekText += prefix + "${ lfn:message('sys-attend:sysAttendCategory.fdWeek.tue') }";break;
					case '3':fdWeekText += prefix + "${ lfn:message('sys-attend:sysAttendCategory.fdWeek.wed') }";break;
					case '4':fdWeekText += prefix + "${ lfn:message('sys-attend:sysAttendCategory.fdWeek.thu') }";break;
					case '5':fdWeekText += prefix + "${ lfn:message('sys-attend:sysAttendCategory.fdWeek.fri') }";break;
					case '6':fdWeekText += prefix + "${ lfn:message('sys-attend:sysAttendCategory.fdWeek.sat') }";break;
					case '7':fdWeekText += prefix + "${ lfn:message('sys-attend:sysAttendCategory.fdWeek.sun') }";break;
					default :break;
					}
				}
			}
			return fdWeekText;
		};
		
		window.editTimeSheet = function() {
			var editTR = DocListFunc_GetParentByTagName("TR");
			
			var fdWeek = $(editTR).find('[name$=".fdWeek"]');
			var fdWork = $(editTR).find('[name$=".fdWork"]');
			var fdWorkTimeId1 = $(editTR).find('[name$=".fdWorkTime[0].fdId"]');
			var fdIsAvailable1 = $(editTR).find('[name$=".fdWorkTime[0].fdIsAvailable"]');
			var fdOnTime1 = $(editTR).find('[name$=".fdWorkTime[0].fdStartTime"]');
			var fdOffTime1 = $(editTR).find('[name$=".fdWorkTime[0].fdEndTime"]');
			var fdOverTimeType1 = $(editTR).find('[name$=".fdWorkTime[0].fdOverTimeType"]');
			var fdWorkTimeId2 = $(editTR).find('[name$=".fdWorkTime[1].fdId"]');
			var fdIsAvailable2 = $(editTR).find('[name$=".fdWorkTime[1].fdIsAvailable"]');
			var fdOnTime2 = $(editTR).find('[name$=".fdWorkTime[1].fdStartTime"]');
			var fdOffTime2 = $(editTR).find('[name$=".fdWorkTime[1].fdEndTime"]');
			var fdOverTimeType2 = $(editTR).find('[name$=".fdWorkTime[1].fdOverTimeType"]');
			var fdStartTime1 = $(editTR).find('[name$=".fdStartTime1"]');
			var fdStartTime2 = $(editTR).find('[name$=".fdStartTime2"]');
			var fdEndTime1 = $(editTR).find('[name$=".fdEndTime1"]');
			var fdEndTime2 = $(editTR).find('[name$=".fdEndTime2"]');
			var fdEndDay = $(editTR).find('[name$=".fdEndDay"]');
			var fdRestStartTime = $(editTR).find('[name$=".fdRestStartTime"]');
			var fdRestEndTime = $(editTR).find('[name$=".fdRestEndTime"]');
			var fdTotalTime = $(editTR).find('[name$=".fdTotalTime"]');
			var fdTotalDay = $(editTR).find('[name$=".fdTotalDay"]');
			var fdRestEndType = $(editTR).find('[name$=".fdRestEndType"]');
			var fdRestStartType = $(editTR).find('[name$=".fdRestStartType"]');

			var fdWeeks = "";
			$('#wTimeSheet').find('[name$=".fdWeek"]').each(function(){
				fdWeeks += $(this).val() + ";";
			});
			
			var url = '/sys/attend/sys_attend_category/sysAttendCategory_edit_tsheet.jsp?'
					+ "fdWeek=" + fdWeek.val() + "&fdWork=" + fdWork.val() 
					+ "&fdWorkTimeId1=" + fdWorkTimeId1.val() + '&fdIsAvailable1=' + fdIsAvailable1.val() + "&fdOnTime1=" + fdOnTime1.val() + "&fdOffTime1=" + fdOffTime1.val()
					+ "&fdOverTimeType1=" + fdOverTimeType1.val()
					+ "&fdWorkTimeId2=" + fdWorkTimeId2.val() + '&fdIsAvailable2=' + fdIsAvailable2.val() + "&fdOnTime2=" + fdOnTime2.val() + "&fdOffTime2=" + fdOffTime2.val()
					+ "&fdOverTimeType2=" + fdOverTimeType2.val()
					+ "&fdStartTime1=" + fdStartTime1.val() + "&fdStartTime2=" + fdStartTime2.val()
					+ "&fdEndTime1=" + fdEndTime1.val() + "&fdEndTime2=" + fdEndTime2.val() + "&fdEndDay=" + fdEndDay.val()
					+ "&fdRestStartTime=" + fdRestStartTime.val() + "&fdRestEndTime=" + fdRestEndTime.val() + "&fdTotalTime=" + fdTotalTime.val()
					+ "&fdWeeks=" + fdWeeks
					+"&fdTotalDay=" + fdTotalDay.val()
					+"&fdRestEndType=" + fdRestEndType.val()
					+"&fdRestStartType=" + fdRestStartType.val();
			
			dialog.iframe(url, "${ lfn:message('sys-attend:sysAttendCategory.timeSheet.setting') }", function(result){
				if(result) {
					var resultObj = {}; 
					$.each(result, function() {
						resultObj[this.name] = this.value;
				    });
					fdWeek.val(resultObj['fdWeek']);
					fdWork.val(resultObj['fdWork']);
					fdWorkTimeId1.val(resultObj['fdWorkTime[0].fdId']);
					fdIsAvailable1.val(resultObj['fdWorkTime[0].fdIsAvailable']);
					fdOnTime1.val(resultObj['fdWorkTime[0].fdStartTime']);
					fdOffTime1.val(resultObj['fdWorkTime[0].fdEndTime']);
					fdOverTimeType1.val(resultObj['fdWorkTime[0].fdOverTimeType']);
					fdWorkTimeId2.val(resultObj['fdWorkTime[1].fdId']);
					fdIsAvailable2.val(resultObj['fdWorkTime[1].fdIsAvailable']);
					fdOnTime2.val(resultObj['fdWorkTime[1].fdStartTime']);
					fdOffTime2.val(resultObj['fdWorkTime[1].fdEndTime']);
					fdOverTimeType2.val(resultObj['fdWorkTime[1].fdOverTimeType']);
					fdStartTime1.val(resultObj['fdStartTime1']);
					fdStartTime2.val(resultObj['fdStartTime2']);
					fdEndTime1.val(resultObj['fdEndTime1']);
					fdEndTime2.val(resultObj['fdEndTime2'] || resultObj['fdEndTime']);
					fdEndDay.val(resultObj['fdEndDay']);
					fdRestStartTime.val(resultObj['fdRestStartTime']);
					fdRestEndTime.val(resultObj['fdRestEndTime']);
					fdTotalTime.val(resultObj['fdTotalTime']);
					fdTotalDay.val(resultObj['fdTotalDay']);
					fdRestEndType.val(resultObj['fdRestEndType']);
					fdRestStartType.val(resultObj['fdRestStartType']);

					$(editTR).find('td:eq(0)').html(getFdWeekText(resultObj['fdWeek']));
					var fdWorkTimeText = '';
					if(resultObj['fdWork'] == '1') {
						if(resultObj['fdWorkTime[0].fdOverTimeType']=="2"){
							fdWorkTimeText="(${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') })";
						}
						fdWorkTimeText = resultObj['fdWorkTime[0].fdStartTime'] + '&nbsp;-&nbsp;' + resultObj['fdWorkTime[0].fdEndTime'] + fdWorkTimeText;
					} else if(resultObj['fdWork'] == '2'){
						if(resultObj['fdWorkTime[1].fdOverTimeType']=="2"){
							fdWorkTimeText="(${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') })";
						}
						fdWorkTimeText = resultObj['fdWorkTime[0].fdStartTime'] + '&nbsp;-&nbsp;' + resultObj['fdWorkTime[0].fdEndTime']
										+ ';&nbsp;' + resultObj['fdWorkTime[1].fdStartTime'] + '&nbsp;-&nbsp;' + resultObj['fdWorkTime[1].fdEndTime'] + fdWorkTimeText;
					}
					$(editTR).find('td:eq(1)').html(fdWorkTimeText);
				}
			},{width: 950, height: 500});
		};
	});
	
	window.deleteTimeSheet = function() {
		DocList_DeleteRow();
		setTimeout(function() {
			var count = $('#wTimeSheet input[name^="fdTimeSheets["][name$="].fdWeek"]').length;
			if(count <= 0) {
				$('#tSheetCountVld').show();
			}

			if(count <= 2){
				$('#tSheetCountTips').hide();
			}
		}, 0);
	}
	
	window.validateTSheetCount = function() {
		if($('[name="fdShiftType"]:checked').val() == '0' && $('select[name="fdSameWorkTime"]').val() == '1'
				&& $('#wTimeSheet input[name^="fdTimeSheets["]').length <= 0) {
			$('#tSheetCountVld').show();
			$("html,body").animate({scrollTop:$('#tSheetCountVld').offset().top - $(window).height()/2},200);
			return false;
		} else {
			$('#tSheetCountVld').hide();
			return true;
		}
	};
	
	var changeIsPatch = function() {
		if($('[name="fdIsPatch"]').val() === 'false'){
			$('.patchContent').hide();
		} else {
			$('.patchContent').show();
		}
	};
	
	window.changeFocus=function(name){
		$('input[name="'+name+'"]:enabled:visible').focus();
	};
	
	window.getDateTime=function(time,type){
		var date=new Date();
		if(type && type==2){
			date.setDate(date.getDate()+1);
		}
		date.setHours(parseInt(time.split(':')[0]),parseInt(time.split(':')[1]),0);
		return date;
	}
	
	//以下为校验器
	
	cateValidation.addValidator('beforeFirstStart', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.beforeFirstStart') }", function(v,e,o){
		if(getShiftType() =='1'){
			return true;
		}
		cateValidation.validateElement($('input[name="fdWorkTime[0].fdStartTime"]:enabled')[0]);
		var firstStart = $('input[name="fdWorkTime[0].fdStartTime"]:enabled').val();
		if(firstStart && v) {
			return firstStart >= v;
		} else {
			return true;
		}
	});
	
	cateValidation.addValidator('afterOpen', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterOpen') }", function(v,e,o){
		if(getShiftType() =='1'){
			var openTime = $('input[name="fdAreaStartTime"]:enabled').val();
			var isAcrossDay = $('select[name="fdEndDay"]:enabled').val();
			if(isAcrossDay != '2' && openTime && v) {
				return openTime <= v;
			}
			return true;
		}
		var openTime = $('input[name="fdStartTime"]:enabled').val();
		if(openTime && v) {
			return openTime <= v;
		} else {
			return true;
		}
	});
	
	cateValidation.addValidator('afterFirstStart', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterFirstStart') }", function(v,e,o){
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
	
	cateValidation.addValidator('afterAcrossFirstStart', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterAcrossFirstStart') }", function(v,e,o){
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
	
	cateValidation.addValidator('afterAcrossFirstEnd', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterAcrossFirstEnd') }", function(v,e,o){
		if($(e).is(':disabled'))
			return true;
		var firstEnd = $('input[name="fdWorkTime[0].fdEndTime"]:enabled:visible').val();
		var overTimeType = $('select[name="fdWorkTime[0].fdOverTimeType"]:enabled:visible').val();
		if(firstEnd && v && overTimeType && overTimeType==2) {
			return firstEnd < v;	
		} 
		return true;
	});
	
	cateValidation.addValidator('afterAcrossSecondEnd', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterAcrossSecondEnd') }", function(v,e,o){
		if($(e).is(':disabled'))
			return true;
		var secondEnd = $('input[name="fdWorkTime[1].fdEndTime"]:enabled:visible').val();
		var overTimeType = $('select[name="fdWorkTime[1].fdOverTimeType"]:enabled:visible').val();
		if(secondEnd && v && overTimeType && overTimeType==2) {
			return secondEnd < v;	
		}
		return true;
	});
	
	cateValidation.addValidator('afterFirstEnd', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterFirstEnd') }", function(v,e,o){
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
	
	cateValidation.addValidator('afterSecondStart', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterSecondStart') }", function(v,e,o){
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
	
	cateValidation.addValidator('afterAcrossSecondStart', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterAcrossFirstStart') }", function(v,e,o){
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
	
	cateValidation.addValidator('afterEnd', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterEnd') }", function(v,e,o){
		var firstEnd = $('input[name="fdWorkTime[0].fdEndTime"]:enabled:visible').val();
		var secondEnd = $('input[name="fdWorkTime[1].fdEndTime"]:enabled:visible').val();
		var isAcrossDay = $('select[name="fdEndDay"]:enabled:visible').val();
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
	
	cateValidation.addValidator('afterArcossEnd', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.afterEnd') }", function(v,e,o){
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
	
	cateValidation.addValidator('acrossDay', "${ lfn:message('sys-attend:sysAttendCategory.validate.time.acrossDay') }", function(v,e,o){
		if($(e).is(':visible')){
			var isAcrossDay = $('select[name="fdEndDay"]:enabled:visible').val();
			var fdStartTime = $('input[name="fdStartTime"]:enabled:visible').val();
			if(getShiftType() =='1'){
				fdStartTime = $('input[name="fdAreaStartTime"]:enabled:visible').val();
			}
			if(isAcrossDay == '2' && fdStartTime){
				return fdStartTime > v;
			}
		}
		return true;
	});
	
	cateValidation.addValidator('macIp',"${ lfn:message('sys-attend:sysAttendCategory.validate.macIp') }",function(v,e,o){
		if(/[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}/.test(v)){
			return true;
		}
		return false;
	});
	cateValidation.addValidator('checkMacIp',"${ lfn:message('sys-attend:sysAttendCategory.validate.macIp.duplicate') }",function(v,e,o){
		if(!isWifiRepeaded(v,true)){
			return true;
		}
		return false;
	});
	
	cateValidation.addValidator('flexTimeVld',"${ lfn:message('sys-attend:sysAttendCategory.validate.fdFlexTime') }",function(v,e,o){
		var onTime1 = $('[name="fdWorkTime[0].fdStartTime"]:enabled').val();
		var offTime1 = $('[name="fdWorkTime[0].fdEndTime"]:enabled').val();
		var onTime2 = $('[name="fdWorkTime[1].fdStartTime"]:enabled').val();
		var offTime2 = $('[name="fdWorkTime[1].fdEndTime"]:enabled').val();
		var fdEndTime = $('[name="fdEndTime"]:enabled').val();
		var fdEndDay = $('[name="fdEndDay"]:enabled').val();
		var type1 = $('select[name="fdWorkTime[0].fdOverTimeType"]:enabled:visible').val();
		var type2 = $('select[name="fdWorkTime[1].fdOverTimeType"]:enabled:visible').val();
		if(!v) {
			return true;
		}
		if(!fdEndTime) {
			return true;
		}
		
		var endMins = fdEndDay == '2' ? getDateTime(fdEndTime,2).getTime():getDateTime(fdEndTime,1).getTime();
		debugger
		/* var endMins = fdEndDay == '2' ? (parseInt(fdEndTime.split(':')[0]) + 24) * 60 + parseInt(fdEndTime.split(':')[1]) : parseInt(fdEndTime.split(':')[0] * 60) + parseInt(fdEndTime.split(':')[1]); */
		if(onTime2 && offTime2) {
			var dateStart2=getDateTime(onTime2,1);
			var dateEnd2=getDateTime(offTime2,type2);
			var onMins2 = dateStart2.getTime();
			var offMins2 = dateEnd2.getTime();
			/* var onMins2 = parseInt(onTime2.split(':')[0] * 60) + parseInt(onTime2.split(':')[1]);
			var offMins2 = parseInt(offTime2.split(':')[0] * 60) + parseInt(offTime2.split(':')[1]); */
			if(parseInt(v) > ((offMins2 - onMins2)/(60*1000)))
				return false;
			if(parseInt(v) > ((endMins - offMins2)/(60*1000)))
				return false;
		}
		if(onTime1 && offTime1) {
			var dateStart1=getDateTime(onTime1,1);
			var dateEnd1=getDateTime(offTime1,type1);
			var onMins1 = dateStart1.getTime();
			var offMins1 = dateEnd1.getTime();
			/* var onMins1 = parseInt(onTime1.split(':')[0] * 60) +  parseInt(onTime1.split(':')[1]);
			var offMins1 = parseInt(offTime1.split(':')[0] * 60) + parseInt(offTime1.split(':')[1]); */
			if(parseInt(v) > ((offMins1 - onMins1)/(60*1000)))
				return false;
			if(parseInt(v) > ((endMins - offMins1)/(60*1000)))
				return false;
		}
		return true;
	});
	
	cateValidation.addValidator('maxLate',"${ lfn:message('sys-attend:sysAttendCategory.validate.maxLate') }",function(v,e,o){
		if(!v) {
			return true;
		}
		var fdShiftType = $('[name="fdShiftType"]:enabled').val();
		if(fdShiftType=="1"){
			return true;
		}
		var onTime1 = $('[name="fdWorkTime[0].fdStartTime"]:enabled').val();
		var offTime1 = $('[name="fdWorkTime[0].fdEndTime"]:enabled').val();
		var onTime2 = $('[name="fdWorkTime[1].fdStartTime"]:enabled').val();
		var offTime2 = $('[name="fdWorkTime[1].fdEndTime"]:enabled').val();
		var type1 = $('select[name="fdWorkTime[0].fdOverTimeType"]:enabled:visible').val();
		var type2 = $('select[name="fdWorkTime[1].fdOverTimeType"]:enabled:visible').val();
		if(onTime1 && offTime1) {
			var dateStart1=getDateTime(onTime1,1);
			var dateEnd1=getDateTime(offTime1,type1);
			var onMins1 = dateStart1.getTime();
			var offMins1 = dateEnd1.getTime();
			var workMins1 =(offMins1 - onMins1)/(60*1000);
			/* var workMins1 = parseInt(offTime1.split(':')[0]) * 60 + parseInt(offTime1.split(':')[1]) - parseInt(onTime1.split(':')[0]) * 60 - parseInt(onTime1.split(':')[1]); */
			if(parseInt(v) > workMins1) {
				return false;
			}
		}
		var workTypeField = $('input[name="fdWork"]:hidden').val();
		if(workTypeField=="1"){
			return true;
		}
		if(onTime2 && offTime2) {
			var dateStart2=getDateTime(onTime2,1);
			var dateEnd2=getDateTime(offTime2,type2);
			var onMins2 = dateStart2.getTime();
			var offMins2 = dateEnd2.getTime();
			var workMins2 = (offMins2 - onMins2)/(60*1000);
			/* var workMins2 = parseInt(offTime2.split(':')[0]) * 60 + parseInt(offTime2.split(':')[1]) - parseInt(onTime2.split(':')[0]) * 60 - parseInt(onTime2.split(':')[1]); */
			if(parseInt(v) > workMins2) {
				return false;
			}
		}
		return true;
	});
	
	cateValidation.addValidator('maxLeft',"${ lfn:message('sys-attend:sysAttendCategory.validate.maxLeft') }",function(v,e,o){
		if(!v) {
			return true;
		}
		var fdShiftType = $('[name="fdShiftType"]:enabled').val();
		if(fdShiftType=="1"){
			return true;
		}
		var onTime1 = $('[name="fdWorkTime[0].fdStartTime"]:enabled').val();
		var offTime1 = $('[name="fdWorkTime[0].fdEndTime"]:enabled').val();
		var onTime2 = $('[name="fdWorkTime[1].fdStartTime"]:enabled').val();
		var offTime2 = $('[name="fdWorkTime[1].fdEndTime"]:enabled').val();
		var type1 = $('select[name="fdWorkTime[0].fdOverTimeType"]:enabled:visible').val();
		var type2 = $('select[name="fdWorkTime[1].fdOverTimeType"]:enabled:visible').val();
		
		if(onTime1 && offTime1) {
			var dateStart1=getDateTime(onTime1,1);
			var dateEnd1=getDateTime(offTime1,type1);
			var onMins1 = dateStart1.getTime();
			var offMins1 = dateEnd1.getTime();
			var workMins1 =(offMins1 - onMins1)/(60*1000);
			/* var workMins1 = parseInt(offTime1.split(':')[0]) * 60 + parseInt(offTime1.split(':')[1]) - parseInt(onTime1.split(':')[0]) * 60 - parseInt(onTime1.split(':')[1]); */
			if(parseInt(v) > workMins1) {
				return false;
			}
		}
		var workTypeField = $('input[name="fdWork"]:hidden').val();
		if(workTypeField=="1"){
			return true;
		}
		if(onTime2 && offTime2) {
			var dateStart2=getDateTime(onTime2,1);
			var dateEnd2=getDateTime(offTime2,type2);
			var onMins2 = dateStart2.getTime();
			var offMins2 = dateEnd2.getTime();
			var workMins2 = (offMins2 - onMins2)/(60*1000);
			/* var workMins2 = parseInt(offTime2.split(':')[0]) * 60 + parseInt(offTime2.split(':')[1]) - parseInt(onTime2.split(':')[0]) * 60 - parseInt(onTime2.split(':')[1]); */
			if(parseInt(v) > workMins2) {
				return false;
			}
		}
		return true;
	});
	
	cateValidation.addValidator('fullLateAbscent',"${ lfn:message('sys-attend:sysAttendCategory.validate.fullAbscentLarger') }",function(v,e,o){
		if(!v) {
			return true;
		}
		
		var halfLateAbs = $('[name="fdLateToAbsentTime"]:enabled').val();
		if(!halfLateAbs) {
			return true;
		} else {
			return parseInt(halfLateAbs) < parseInt(v);
		}
	});
	
	cateValidation.addValidator('fullLeftAbscent',"${ lfn:message('sys-attend:sysAttendCategory.validate.fullAbscentLarger') }",function(v,e,o){
		if(!v) {
			return true;
		}
		
		var halfLeftAbs = $('[name="fdLeftToAbsentTime"]:enabled').val();
		if(!halfLeftAbs) {
			return true;
		} else {
			return parseInt(halfLeftAbs) < parseInt(v);
		}
	});
	
	cateValidation.addValidator('minLateToAbs',"${ lfn:message('sys-attend:sysAttendCategory.validate.minEqualLateToAbs') }",function(v,e,o){
		if(!v) {
			return true;
		}
		
		var fdLateTime = $('[name="fdRule[0].fdLateTime"]:enabled').val();
		if(!fdLateTime || fdLateTime == '0') {
			return parseInt(v) > 0;
		} else {
			return parseInt(v) > parseInt(fdLateTime);
		}
	});
	
	cateValidation.addValidator('minLeftToAbs',"${ lfn:message('sys-attend:sysAttendCategory.validate.minEqualLeftToAbs') }",function(v,e,o){
		if(!v) {
			return true;
		}
		
		var fdLeftTime = $('[name="fdRule[0].fdLeftTime"]:enabled').val();
		if(!fdLeftTime || fdLeftTime == '0') {
			return parseInt(v) > 0;
		} else {
			return parseInt(v) > parseInt(fdLeftTime);
		}
	});
	
	cateValidation.addValidator('restTimeNull',"${ lfn:message('sys-attend:sysAttendCategory.validate.restTimeNull') }", function(v,e,o){
		var fieldName = $(e).attr('name');
		var restStart = $('[name="fdRestStartTime"]:enabled');
		var restEnd = $('[name="fdRestEndTime"]:enabled');
		if(restStart && restEnd) {
			if(v) {
				if(fieldName.indexOf('fdRestStartTime') > -1) {
					if(!restEnd.val()){
						cateValidation.validateElement(restEnd[0]);
					}
				} else if(fieldName.indexOf('fdRestEndTime') > -1){
					if(!restStart.val()){
						cateValidation.validateElement(restStart[0]);
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
	
	cateValidation.addValidator('restTimeRange',"${ lfn:message('sys-attend:sysAttendCategory.validate.restTimeRange') }", function(v,e,o){
		//验证午休时间、在工作时间范围内
		// if(!v) {
		// 	return true;
		// }
		//午休开始时间
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
	
	cateValidation.addValidator('startCompareEnd2',"${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.endBiggerStart') }", function(v,e,o){
		var name = e.name;
		name = name.substring(0, name.indexOf('.', 0) + 1) + "fdStartTime";
		cateValidation.validateElement($('[name="'+name+'"]:enabled')[0]);
		return true;
	});
	
	cateValidation.addValidator('startCompareEnd',"${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.endBiggerStart') }", function(v,e,o){
		if(!v) {
			return true;
		}
		
		var fdStartTimes = $('#overtimeDeducts').find('input[name$="fdStartTime"]');
		var fdEndTimes = $('#overtimeDeducts').find('input[name$="fdEndTime"]');
		
		if(!fdStartTimes || fdStartTimes.length==0 || !fdEndTimes || fdEndTimes.length==0){
			return true;
		}
		
		
		// 当前时间段
		var currentObj = {};
		
		for(var i =0;i<fdStartTimes.length;i++){
			if(e.id == fdStartTimes[i].id || e.id == fdEndTimes[i].id) {
				currentObj.fdStartTime = fdStartTimes[i].value;
				currentObj.fdEndTime = fdEndTimes[i].value;
				break;
			}
		}
		
		if(currentObj.fdStartTime != "" && currentObj.fdEndTime != "" 
				&& currentObj.fdStartTime >= currentObj.fdEndTime) {
			return false;
		}
		
		return true;
	});
	
	cateValidation.addValidator('thresholdBiggerHours',"${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.thresholdBiggerHours') }", function(v,e,o){
		console.log("----------------------")
		if(!v) {
			return true;
		}
		
		var threshold = $('#timethreshold').find('input[name$="overtimeDeducts[0].fdThreshold"]').val();
		var hours = $('#timethreshold').find('input[name$="overtimeDeducts[0].fdDeductHours"]').val();
		
		if(!threshold || threshold.length==0 || !hours || hours.length==0){
			return true;
		} 
		
		if(parseInt(threshold) <= parseInt(hours)) {
			return false;
		}
		
		return true;
	});
	
	cateValidation.addValidator('thresholdBiggerHours2',"${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.thresholdBiggerHours') }", function(v,e,o){
		cateValidation.validateElement($('[name="overtimeDeducts[0].fdThreshold"]:enabled')[0]);
		return true;
	});
	
	cateValidation.addValidator('timeRangeMixed2',"${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.timeRangeMixed') }", function(v,e,o){
		var name = e.name;
		name = name.substring(0, name.indexOf('.', 0) + 1) + "fdStartTime";
		cateValidation.validateElement($('[name="'+name+'"]:enabled')[0]);
		return true;
	});
	
	// 校验时间是否交叉
	cateValidation.addValidator('timeRangeMixed',"${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.timeRangeMixed') }", function(v,e,o){
		if(!v) {
			return true;
		}

		var fdStartTimes = $('#overtimeDeducts').find('input[name$="fdStartTime"]');
		var fdEndTimes = $('#overtimeDeducts').find('input[name$="fdEndTime"]');
		
		if(!fdStartTimes || fdStartTimes.length==0 || !fdEndTimes || fdEndTimes.length==0){
			return false;
		}
		
		// 其他时间段
		var objArr = [];
		// 当前时间段
		var currentObj = {};
		
		for(var i =0;i<fdStartTimes.length;i++){
			if(e.name == fdStartTimes[i].name || e.name == fdEndTimes[i].name) {
				currentObj.fdStartTime = fdStartTimes[i].value;
				currentObj.fdEndTime = fdEndTimes[i].value;
				continue;
			}
			var obj = {};
			obj.fdStartTime = fdStartTimes[i].value;
			obj.fdEndTime = fdEndTimes[i].value;
			objArr.push(obj);
		}
		
		for(var i =0;i<objArr.length;i++){
			if(v > objArr[i].fdStartTime && v < objArr[i].fdEndTime) {
				return false;
			}
			
			if(currentObj.fdStartTime != "" && currentObj.fdEndTime != "") {
				if(currentObj.fdStartTime == objArr[i].fdStartTime
						&& currentObj.fdEndTime == objArr[i].fdEndTime) {
					return false;
				}
			}
		}

		return true;
	});
	
	cateValidation.addValidator('restTimeStart',"${ lfn:message('sys-attend:sysAttendCategory.validate.restTimeStart') }", function(v,e,o){
		//午休时间的验证规则。结束时间必须大于等于开始时间
		if(!v) {
			return true;
		}
		var restStart = $('[name="fdRestStartTime"]:enabled').val();
		var restEnd = $('[name="fdRestEndTime"]:enabled').val();
		//午休开始时间类型
		var fdRestStartType = $('select[name="fdRestStartType"]:enabled').val();
		//午休结束时间类型
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
	
	cateValidation.addValidator('restTimeEnd',"${ lfn:message('sys-attend:sysAttendCategory.validate.restTimeEnd') }", function(v,e,o){
		cateValidation.validateElement($('[name="fdRestStartTime"]:enabled')[0]);
		return true;
	});
	
	cateValidation.addValidator('firstEndTime',"${ lfn:message('sys-attend:sysAttendCategory.validate.firstEndTime') }", function(v,e,o){
		if(!v) {
			return true;
		}
		var offTime1 = $('[name="fdWorkTime[0].fdEndTime"]:enabled').val();
		var onTime2 = $('[name="fdWorkTime[1].fdStartTime"]:enabled').val();
		if(offTime1 && onTime2) {
			return v >= offTime1 && v <= onTime2;
		}
	});
	
	cateValidation.addValidator('secondStartTime',"${ lfn:message('sys-attend:sysAttendCategory.validate.secondStartTime') }", function(v,e,o){
		if(!v) {
			return true;
		}
		var offTime1 = $('[name="fdWorkTime[0].fdEndTime"]:enabled').val();
		var onTime2 = $('[name="fdWorkTime[1].fdStartTime"]:enabled').val();
		if(offTime1 && onTime2) {
			return v >= offTime1 && v <= onTime2;
		}
	});
	
	//判断是否为大于0的数(可以为小数点)
	cateValidation.addValidator('minUnitHour',"<span class=\"validation-advice-title\">${ lfn:message('sys-attend:sysAttendCategory.overtime.unit') }</span>&nbsp;${ lfn:message('sys-attend:sysAttendCategory.validate.minUnitHour') }</span>",function(v,e,o){
		var fdRoundingType = $('select[name="fdRoundingType"]').val();
		if(fdRoundingType=='0'){
			return true;
		}
		if(/^(?!(0[0-9]{0,}$))[0-9]{1,}[.]{0,}[0-9]{0,}$/.test(v) && fdRoundingType!='0'){
			return true;
		}	
		return false;
	});
	
	seajs.use(['lui/dialog','lui/jquery','sys/attend/resource/js/dateUtil'],function(dialog,$,dateUtil){

		window.onSubmitMethod = function (method){
			if(cateValidation.validate()) {
				var tipTxt = "${ lfn:message('sys-attend:sysAttendCategory.fdEffectTime.staTip') }";
				if(!validateTSheetCount()){
					return;
				}
				if(!validateAttendMode()){
					return;
				}
				setCategoryStatus();
				if(getShiftType()=='1'){
					$('input[name="fdStartTime"]').val($('input[name="fdAreaStartTime"]').val());
					$('input[name="fdEndTime"]').val($('input[name="fdAreaEndTime"]').val());
				}

				//把fdLimitLocations转成fdLocations
				var fields=$(document.sysAttendCategoryForm).serializeArray();
				var index=-1;
				var limitindex=-1;
				var limit;
				var rowindex0="0";
				var locationsTempHiddenDiv=$("#locationsTempHiddenDiv");
				//先清空地址信息的隐藏数据
				locationsTempHiddenDiv.html("");

				for (var i = 0; i < fields.length; i++) {
					if (fields[i].name.indexOf("fdLimitLocations") > -1) {
						var name = fields[i].name;
						var rowIndex, limitIndex;

						if (name.indexOf(".fdLimit") > -1) {
							limit = fields[i].value;
							index++;
							limitindex++;
							rowindex0 = "0";

							var childNode = document.createElement('input');
							childNode.setAttribute('name', 'fdLocations[' + index + '].fdDataType');
							childNode.setAttribute('value', 'newdata');
							childNode.setAttribute('type', 'hidden');
							locationsTempHiddenDiv.append(childNode);
							var childNode = document.createElement('input');
							childNode.setAttribute('name', 'fdLocations[' + index + '].fdLimit');
							childNode.setAttribute('value', limit);
							childNode.setAttribute('type', 'hidden');
							locationsTempHiddenDiv.append(childNode);

							var childNode = document.createElement('input');
							childNode.setAttribute('name', 'fdLocations[' + index + '].fdRow');
							childNode.setAttribute('value', "0");
							childNode.setAttribute('type', 'hidden');
							locationsTempHiddenDiv.append(childNode);
							var childNode = document.createElement('input');
							childNode.setAttribute('name', 'fdLocations[' + index + '].fdLimitIndex');
							childNode.setAttribute('value', limitindex);
							childNode.setAttribute('type', 'hidden');
							locationsTempHiddenDiv.append(childNode);

						} else {
							limitIndex = name.substring(name.indexOf("[") + 1);
							limitIndex = limitIndex.substring(0, limitIndex.indexOf("]"));
							rowIndex = name.substring(name.lastIndexOf("[") + 1);
							rowIndex = rowIndex.substring(0, rowIndex.indexOf("]"));
							if (rowindex0 != rowIndex) {
								rowindex0 = rowIndex;
								index++;
								var childNode = document.createElement('input');
								childNode.setAttribute('name', 'fdLocations[' + index + '].fdDataType');
								childNode.setAttribute('value', 'newdata');
								childNode.setAttribute('type', 'hidden');
								locationsTempHiddenDiv.append(childNode);

								var childNode = document.createElement('input');
								childNode.setAttribute('name', 'fdLocations[' + index + '].fdLimit');
								childNode.setAttribute('value', limit);
								childNode.setAttribute('type', 'hidden');
								locationsTempHiddenDiv.append(childNode);

								var childNode = document.createElement('input');
								childNode.setAttribute('name', 'fdLocations[' + index + '].fdRow');
								childNode.setAttribute('value', rowIndex);
								childNode.setAttribute('type', 'hidden');
								locationsTempHiddenDiv.append(childNode);
								var childNode = document.createElement('input');
								childNode.setAttribute('name', 'fdLocations[' + index + '].fdLimitIndex');
								childNode.setAttribute('value', limitIndex);
								childNode.setAttribute('type', 'hidden');
								locationsTempHiddenDiv.append(childNode);
							}
							name = name.substring(name.lastIndexOf(".") + 1);
							var childNode = document.createElement('input');
							childNode.setAttribute('name', 'fdLocations[' + index + '].' + name);
							childNode.setAttribute('value', fields[i].value);
							childNode.setAttribute('type', 'hidden');
							locationsTempHiddenDiv.append(childNode);
						}
					}
				}

				var fdCanMap = $('input[name="fdCanMap"]').val();
				if(fdCanMap=='true') {
					var isMapAddressError =false;
					//开启地图以后。验证地图中的坐标不是空，才允许提交
					var fdLocationCoordinate = locationsTempHiddenDiv.find('input[name$="fdLocationCoordinate"]');
					if (fdLocationCoordinate && fdLocationCoordinate.length > 0) {
						for(var tempLocaltion=0;tempLocaltion<fdLocationCoordinate.length;tempLocaltion++){
							if(fdLocationCoordinate[tempLocaltion]) {
								var v = $(fdLocationCoordinate[tempLocaltion]).val();
								if(!v || v.length ==0){
									isMapAddressError =true;
								}
							}
						}
					}
					if (isMapAddressError) {
						dialog.alert('${ lfn:message("sys-attend:sysAttendCategory.fdLocations.error") }');
						return;
					}
				}
				if(method=='save' || method=='saveadd'){
					//新增的时候，生效日期必须大于等于今日
					var status = getCategoryStatus(2);
					if(status=='1'){
						dialog.alert('${ lfn:message("sys-attend:sysAttendHisCategory.tip2") }');
						return;
					} else {
						Com_Submit(document.sysAttendCategoryForm,method);
					}
				}
				if(method=='update'){
					//立即生效、明日生效
					dialog.confirm('${ lfn:message("sys-attend:sysAttendHisCategory.tip3") }', function (flag, d) {

					}, null, [{
						name: '${ lfn:message("sys-attend:sysAttendHisCategory.tip4") }',
						value: true,
						focus: true,
						fn: function (value, dialog) {
							dialog.hide(value);
							$('input[name="fdStatusFlag"]').val("1");
							isExistRecord(function(result){
								if(result.count>0){
									seajs.use(['lui/dialog','lui/jquery'],function(_dialog,$) {
										_dialog.alert(tipTxt, function () {
											$('input[name="fdRestat"]').val('1');
											Com_Submit(document.sysAttendCategoryForm, method);
										});
									});
								}else{
									Com_Submit(document.sysAttendCategoryForm,method);
								}
							});
						}
					}, {
						name: '${ lfn:message("sys-attend:sysAttendHisCategory.tip5") }',
						value: false,
						fn: function (value, dialog) {
							dialog.hide(value);
							$('input[name="fdStatusFlag"]').val("0");
							Com_Submit(document.sysAttendCategoryForm,method);
						}
					}]);

				}
			}
		};
		window.getTomorrowDate=function(){
			var now = new Date();
			now.setTime(now.getTime()+24*60*60*1000);
			now.setHours(0,0,0,0)
			return now;
		};
		window.setCategoryStatus = function(){
			var method = "${param.method}";
			var value = "1";
			if(method=='edit'){
				var tmpValue = $('input[name="fdStatus"]').val();
				if(tmpValue=='0'){
					value = getCategoryStatus(1);
				}
			}else{
				value = getCategoryStatus(1);
			}
			$('input[name="fdStatus"]').val(value);
		}
		window.getCategoryStatus = function(d){
			var fdEffectTime = $('input[name="fdEffectTime"]').val();
			fdEffectTime= Com_GetDate(fdEffectTime, 'date', Com_Parameter.Date_format);
			if(d==1){
				if(fdEffectTime<getTomorrowDate()){
					return '1';
				}
			}else{
				//小于今天
				var now = new Date();
				now.setHours(0,0,0,0)
				if(fdEffectTime< now){
					return '1';
				}
			}
			return '0';
		};
		window.isExistRecord = function(callback){
			var fdTargetIds = $('input[name="fdTargetIds"]').val();
			if(!fdTargetIds) {
				cateValidation.validate();
				return;
			}
			var datas = {targets:fdTargetIds};
			jQuery.ajax({
	            type: "post", 
	            url: "${LUI_ContextPath}/sys/attend/sys_attend_main/sysAttendMain.do?method=isExistRecord", 
	            dataType: "json",
	            data:datas,
	            success: function (data) {
	            	callback && callback(data);
	            }
			});
		}
		
		cateValidation.addValidator('afterNow',"${ lfn:message('sys-attend:sysAttendCategory.fdEffectTime.tip') }",function(v){
			var result = true;
			var now = new Date();
			now.setHours(0,0,0,0)
			if(v){
				var start=Com_GetDate(v, 'date', Com_Parameter.Date_format);
				if(start.getTime() < now.getTime()){
					result = false;
				}
			}
			return result;
		});
		window.onSecurityMode = function(v,nodes){
			for(var i = 0;i<nodes.length;i++){
				if(nodes[i].value==v){
					var status = $(nodes[i]).data('cfg-selected');
					if(status=='true'){
						$(nodes[i]).data('cfg-selected','').removeAttr('checked');
						$('input[name="fdSecurityMode"]').val('');
					}else{
						$(nodes[i]).data('cfg-selected','true')
					}
				}else{
					$(nodes[i]).data('cfg-selected','');
				}
			}
		};
		window.validateAttendMode = function(){
			if($('[name="fdCanMap"]:hidden').val() == 'false' &&
					$('[name="fdCanWifi"]:hidden').val() == 'false' &&
					$('[name="fdDingClock"]:hidden').val() == 'false'){
				dialog.alert("${ lfn:message('sys-attend:sysAttendCategory.validate.signType') }");
			    return false;
	        }
			if($('[name="fdCanMap"]:hidden').val() != 'false' ||
	                $('[name="fdCanWifi"]:hidden').val() != 'false'){
				var locationCount = $("table[id*='locationsList_'] div[data-location-container]").length;
		        var wifiCount = $('#wifiConfigs [data-wifi-config]').length;
		        if(locationCount < 1 && wifiCount == 0 || locationCount == 0 && wifiCount < 1){
		        	dialog.alert("${ lfn:message('sys-attend:sysAttendCategory.validate.position') }");
		            return false;
		        }
			}
		   return true;
		};
		//打卡方式提醒
		window.onOnlyDingEnable = function(){
			var fdDingClock = $('[name="fdDingClock"]:hidden').val();
			var fdCanMap = $('[name="fdCanMap"]:hidden').val();
			var fdCanWifi = $('[name="fdCanWifi"]:hidden').val();
			if(fdCanMap=='false' && fdCanWifi=='false' && fdDingClock=='true'){
				dialog.alert('${synConfigType}'=='qywx'?"${ lfn:message('sys-attend:sysAttendCategory.validate.signType.wx.tip') }":"${ lfn:message('sys-attend:sysAttendCategory.validate.signType.tip') }");
				return;
			}
			if(fdCanMap=='false' && fdCanWifi=='false' && fdDingClock=='false'){
				dialog.alert("${ lfn:message('sys-attend:sysAttendCategory.validate.signType') }");
				return;
			}
		};
		//批量导入
		window.addBatchPosition = function(listName){
			var uploadActionUrl = '${LUI_ContextPath}/sys/attend/sys_attend_category/sysAttendCategory.do?method=importExcel';
			var downloadTempletUrl =  '${LUI_ContextPath}/sys/attend/sys_attend_category/sysAttendCategory.do?method=downloadTemplate';
			var importTitle = "${lfn:message('sys-time:sysTimeLeaveAmount.import.batch')}";
			var url = '/sys/attend/upload/common_upload_download.jsp';
			url = Com_SetUrlParameter(url, 'uploadActionUrl', uploadActionUrl);
			url = Com_SetUrlParameter(url, 'downLoadUrl', downloadTempletUrl);
			url = Com_SetUrlParameter(url, 'isRollBack', false);
			dialog.iframe(url, importTitle ,function(data) {
			}, {
				width : 680,
				height : 380
			});
		};
		window.importExcelCallback = function(datas){
			var result = datas && datas.data;
			if(result && result.length>0){
				for(var i=0;i<result.length;i++){
					var record = result[i];
					//校验mac是否重复
					if(!isWifiRepeaded(record.mac)){
						DocList_AddRow('wifiConfigs',null,{'fdWifiConfigs[!{index}].fdName':record.name || '','fdWifiConfigs[!{index}].fdMacIp':record.mac});
					}
				}
			}
		};
		window.isWifiRepeaded = function(mac,isCurrent){
			var fdMacIps = $('#wifiConfigs').find('input[name$="fdMacIp"]');
			if(!fdMacIps || fdMacIps.length==0){
				return false;
			}
			var arr = [];
			for(var i =0;i<fdMacIps.length;i++){
				if(mac==fdMacIps[i].value){
					arr.push(mac);
				}
			}
			if(isCurrent){
				//手工编辑场景
				if(arr.length>1){
					return true;
				}
				return false;
			}
			if(arr.length>0){
				return true;
			}
			return false;
		};
		//地点签到重复校验
		window.isLocationRepeated=function(location){
			var fdLocations = $('#fdLimitLocations').find('input[name$="fdLocation"]');
			if(!fdLocations||fdLocations.length==1){
				return true;
			}
			var arr=[];
			for(var i =0;i<fdLocations.length;i++){
				if(location==$(fdLocations[i]).val()){
					arr.push(location);
				}
			}
			if(arr.length>1){
				return false;
			}
			return true;
		};
		cateValidation.addValidator('checkLocationRepeated',"${ lfn:message('sys-attend:sysAttendCategory.validate.location.duplicate') }", function(v, e, o) {
			if (!v) {
				return true;
			}
			return isLocationRepeated(v);
		});
	});
</script>