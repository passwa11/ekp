<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit">
	<template:replace name="title">
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3"> 
			<c:choose>
				<c:when test="${ sysTimeLeaveDetailForm.method_GET == 'edit' }">
					<kmss:auth requestURL="/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=update">
						<ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.sysTimeLeaveDetailForm, 'update');"></ui:button>
					</kmss:auth>
				</c:when>
				<c:when test="${ sysTimeLeaveDetailForm.method_GET == 'add' }">
					<kmss:auth requestURL="/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=save">
						<ui:button text="${lfn:message('button.save') }" onclick="Com_Submit(document.sysTimeLeaveDetailForm, 'save');"></ui:button>
					</kmss:auth>
					<kmss:auth requestURL="/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=saveadd">
						<ui:button text="${ lfn:message('button.saveadd') }" onclick="Com_Submit(document.sysTimeLeaveDetailForm, 'saveadd');"></ui:button>
					</kmss:auth>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="head">
		<style type="text/css">
		</style>
	</template:replace>
	<template:replace name="content">
		<p class="txttitle" style="margin: 15px 0;">
			${ lfn:message('sys-time:table.sysTimeLeaveDetail') }
		</p>
		
		<html:form action="/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do">
			<html:hidden property="fdId" />
			<html:hidden property="method_GET" />
			<html:hidden property="fdTotalTime" />
			<html:hidden property="fdLeaveTime" />

			<div class="lui_form_content_frame" style="padding-top:20px">
				<table class="tb_normal" width=100%>
					<tr>
						<%-- 人员 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveDetail.fdPerson') }
						</td>
						<td width="35%">
							<xform:address style="width: 95%" propertyId="fdPersonId" propertyName="fdPersonName" orgType="ORG_TYPE_PERSON" required="true" subject="${ lfn:message('sys-time:sysTimeLeaveDetail.fdPerson') }" isExternal="false" onValueChange="onChangePerson">
							</xform:address>
						</td>
						<%-- 请假类型 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveDetail.fdLeaveName') }
						</td>
						<td width="35%">
							<xform:select property="fdLeaveName" style="width: 150px" showPleaseSelect="false" required="true" subject="${ lfn:message('sys-time:sysTimeLeaveDetail.fdLeaveName') }" onValueChange="onChangeLeaveName"> 
								<c:forEach items="${leaveRuleList }" var="leaveRule">
									<xform:simpleDataSource value="${leaveRule.fdName }">${leaveRule.fdName }</xform:simpleDataSource>
								</c:forEach>
							</xform:select>
						</td>
					</tr>
					<tr>
						<%-- 请假开始时间 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveDetail.fdStartTime') }
						</td>
						<td>
							<html:hidden property="fdStartTime"></html:hidden>
							<html:hidden property="fdEndTime"></html:hidden>
							<c:set var="statType" value="${not empty sysTimeLeaveDetailForm.fdStatType ? sysTimeLeaveDetailForm.fdStatType : null}"></c:set>
							<html:hidden property="fdStatType" value="${statType }" />
							<html:hidden property="fdLeaveType" />
							<div id="dayStart" style="${statType eq 1 ? '' : 'display:none'}">
								<xform:datetime property="day_start" style="width: 75%" dateTimeType="date" validators="${statType eq 1 ? 'beforeEnd lessOneYear required' : ''}" subject="${ lfn:message('sys-time:sysTimeLeaveDetail.fdStartTime') }" onValueChange="onChangeStart" value="${sysTimeLeaveDetailForm.fdStartTime }">
								</xform:datetime>
							</div>	
							<div id="halfStart" style="${statType eq 2 ? '' : 'display:none'}">
								<xform:datetime property="half_start" style="width: 75%" dateTimeType="date" validators="${statType eq 2 ? 'beforeEnd lessOneYear required' : ''}" subject="${ lfn:message('sys-time:sysTimeLeaveDetail.fdStartTime') }" onValueChange="onChangeStart" value="${sysTimeLeaveDetailForm.fdStartTime }">
								</xform:datetime>
								<xform:select property="fdStartNoon" showPleaseSelect="false" validators="${statType eq 2 ? 'required' : ''}" onValueChange="onChangeStartNoon" style="width: 20%">
									<xform:simpleDataSource value="1">${ lfn:message('sys-time:sysTimeLeaveDetail.morning') }</xform:simpleDataSource>
									<xform:simpleDataSource value="2">${ lfn:message('sys-time:sysTimeLeaveDetail.afterNoon') }</xform:simpleDataSource>
								</xform:select>
							</div>
							<div id="hourStart" style="${statType eq 3 ? '' : 'display:none'}">
								<xform:datetime property="hour_start" style="width: 75%" dateTimeType="datetime" validators="${statType eq 3 ? 'beforeEnd lessOneYear required' : ''}" subject="${ lfn:message('sys-time:sysTimeLeaveDetail.fdStartTime') }" onValueChange="onChangeStart" value="${sysTimeLeaveDetailForm.fdStartTime }">
								</xform:datetime>
							</div>
						</td>
						<%-- 请假结束时间 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveDetail.fdEndTime') }
						</td>
						<td>
							<div id="dayEnd" style="${statType eq 1 ? '' : 'display:none'}">
								<xform:datetime property="day_end" style="width: 75%" dateTimeType="date" validators="${statType eq 1 ? 'afterStart lessOneYear required' : ''}" subject="${ lfn:message('sys-time:sysTimeLeaveDetail.fdEndTime') }" onValueChange="onChangeEnd" value="${sysTimeLeaveDetailForm.fdEndTime }">
								</xform:datetime>
							</div>
							<div id="halfEnd" style="${statType eq 2 ? '' : 'display:none'}">
								<xform:datetime property="half_end" style="width: 75%" dateTimeType="date" validators="${statType eq 2 ? 'afterStart lessOneYear required' : ''}" subject="${ lfn:message('sys-time:sysTimeLeaveDetail.fdEndTime') }" onValueChange="onChangeEnd" value="${sysTimeLeaveDetailForm.fdEndTime }">
								</xform:datetime>
								<xform:select property="fdEndNoon" showPleaseSelect="false" validators="${statType eq 2 ? 'required' : ''}" onValueChange="onChangeEndNoon" style="width: 20%">
									<xform:simpleDataSource value="1">${ lfn:message('sys-time:sysTimeLeaveDetail.morning') }</xform:simpleDataSource>
									<xform:simpleDataSource value="2">${ lfn:message('sys-time:sysTimeLeaveDetail.afterNoon') }</xform:simpleDataSource>
								</xform:select>
							</div>
							<div id="hourEnd" style="${statType eq 3 ? '' : 'display:none'}">
								<xform:datetime property="hour_end" style="width: 75%" dateTimeType="datetime" validators="${statType eq 3 ? 'afterStart lessOneYear required' : ''}" subject="${ lfn:message('sys-time:sysTimeLeaveDetail.fdEndTime') }" onValueChange="onChangeEnd" value="${sysTimeLeaveDetailForm.fdEndTime }">
								</xform:datetime>
							</div>
						</td>
					</tr>
					<tr>
						<%-- 时长--%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveDetail.fdLeaveTime') }
						</td>
						<td colspan="3">
							<div id="leaveTimeDay" style="${statType eq 3 ? 'display:none' : ''}">
								<xform:text showStatus="noShow" property="fdLeaveTime" style="width: 30%" subject="${ lfn:message('sys-time:sysTimeLeaveDetail.fdLeaveTime') }" validators="${statType eq 3 ? '' : 'required number min(0.5) max(365) fraction'}"></xform:text>
								<span id="leaveTimeDayStr"></span>
							</div>
							<div id="leaveTimeHour"  style="${statType eq 3 ? '' : 'display:none'}">
								<xform:text showStatus="noShow" property="fdLeaveHour" style="width: 30%" subject="${ lfn:message('sys-time:sysTimeLeaveDetail.fdLeaveTime') }" validators="${statType eq 3 ? 'required number digits min(1) max(1000)' : ''}"></xform:text>
								<span id="leaveTimeHourStr"></span>
							</div>
							<div id="leaveTimeTip" style="display:none;color:red;font-size:12px;">
								${ lfn:message('sys-time:sysTimeLeaveDetail.fdLeaveTime.tip') }
							</div>
						</td>
					</tr>
					<tr>
						<%-- 扣减方式 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveDetail.fdOprType') }
						</td>
						<td>
							<html:hidden property="fdOprType" />
							<c:if test="${sysTimeLeaveDetailForm.fdOprType eq '1' }">
								${ lfn:message('sys-time:sysTimeLeaveDetail.fdOprType.review') }
							</c:if>
							<c:if test="${sysTimeLeaveDetailForm.fdOprType eq '2' }">
								${ lfn:message('sys-time:sysTimeLeaveDetail.fdOprType.manual') }
							</c:if>
						</td>
						<%-- 扣减情况 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveDetail.fdOprDesc') }
						</td>
						<td>
							<html:hidden property="fdOprStatus" />
							<html:hidden property="fdOprDesc" />
							<c:if test="${sysTimeLeaveDetailForm.fdOprStatus eq '0'  || sysTimeLeaveDetailForm.fdOprStatus eq null}">
								${ lfn:message('sys-time:sysTimeLeaveDetail.deduct.no') }
							</c:if>
						</td>
					</tr>
					<kmss:ifModuleExist path="/sys/attend">
					<tr>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveDetail.fdIsUpdateAttend') }
						</td>
						<td colspan="3">
							<ui:switch property="fdIsUpdateAttend" disabledText="${ lfn:message('sys-time:sysTimeLeaveDetail.fdIsUpdateAttend.no') }" enabledText="${ lfn:message('sys-time:sysTimeLeaveDetail.fdIsUpdateAttend.yes') }">
							</ui:switch>
						</td>
					</tr>
					</kmss:ifModuleExist>
					<tr>
						<%-- 关联流程 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveDetail.fdReview') }
						</td>
						<td colspan="3">
							<xform:dialog propertyId="fdReviewId" propertyName="fdReviewName" style="width: 60%">
								selectReview();
							</xform:dialog>
						</td>
					</tr>
					<%-- 所属场所 --%>
				    <%if(ISysAuthConstant.IS_AREA_ENABLED){ %>
						<tr>
							<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field_single.jsp" charEncoding="UTF-8">
				                <c:param name="id" value="${sysTimeLeaveDetailForm.authAreaId}"/>
				            </c:import>
						</tr>
					<%} %>
					<tr>
						<%-- 录入人员 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveDetail.docCreator') }
						</td>
						<td>
							<html:hidden property="docCreatorId" />
							<html:hidden property="docCreatorName" />
							${sysTimeLeaveDetailForm.docCreatorName }
						</td>
						<%-- 录入时间 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveDetail.docCreateTime') }
						</td>
						<td>
							<html:hidden property="docCreateTime" />
							${sysTimeLeaveDetailForm.docCreateTime }
						</td>
					</tr>
				</table>
			</div>	
		</html:form>
		<script type="text/javascript">
		var validation = $KMSSValidation(document.forms['sysTimeLeaveDetailForm']);
		seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
			
			window.onChangeLeaveName = function() {
				var leaveName = $('[name="fdLeaveName"]').val();
				var __statType = $('[name="fdStatType"]').val();
				var statType = getStatType(leaveName);
				$('[name="fdStatType"]').val(statType);
				$('[name="fdLeaveType"]').val(getLeaveType(leaveName));
				if(__statType == statType) {
					return;
				}
				$('[name="fdStartTime"]').val('');
				$('[name="fdEndTime"]').val('');
				$('[name="fdLeaveTime"]').val('');
				$('[name="fdLeaveHour"]').val('');
				if(statType == 1){
					showDay();
					hideHalf();
					hideHour();
				} else if(statType == 2) {
					hideDay()
					showHalf();
					hideHour();
				} else if(statType == 3) {
					hideDay();
					hideHalf();
					showHour();
				} else {
					hideDay();
					hideHalf();
					hideHour();
				}
				$('#leaveTimeTip').hide();
			};
			
			var showDay = function() {
				$('#dayStart').show();
				validation.addElements($('[name="day_start"]')[0], 'beforeEnd lessOneYear required');
				$('#dayEnd').show();
				validation.addElements($('[name="day_end"]')[0], 'beforeEnd lessOneYear required');
			};
			
			var hideDay = function() {
				$('#dayStart').hide();
				$('[name="day_start"]').val('');
				validation.removeElements($('#dayStart'));
				$('#dayEnd').hide();
				$('[name="day_end"]').val('');
				validation.removeElements($('#dayEnd'));
			};
			
			var showHalf = function() {
				$('#halfStart').show();
				validation.addElements($('[name="half_start"]')[0], 'beforeEnd lessOneYear required');
				validation.addElements($('[name="fdStartNoon"]')[0], 'required');
				$('#halfEnd').show();
				validation.addElements($('[name="half_end"]')[0], 'beforeEnd lessOneYear required');
				validation.addElements($('[name="fdEndNoon"]')[0], 'required');
			};
			
			var hideHalf = function() {
				$('#halfStart').hide();
				$('[name="half_start"]').val('');
				validation.removeElements($('#halfStart'));
				$('#halfEnd').hide();
				$('[name="half_end"]').val('');
				validation.removeElements($('#halfEnd'));
			};
			
			var showHour = function() {
				$('#hourStart').show();
				validation.addElements($('[name="hour_start"]')[0], 'beforeEnd lessOneYear required');
				$('#hourEnd').show();
				validation.addElements($('[name="hour_end"]')[0], 'beforeEnd lessOneYear required');
				$('#leaveTimeDay').hide();
				$('#leaveTimeHour').show();
				validation.removeElements($('[name="fdLeaveTime"]')[0]);
				validation.addElements($('[name="fdLeaveHour"]')[0], 'required number digits min(1) max(1000)');
			};
			
			var hideHour = function() {
				$('#hourStart').hide();
				$('[name="hour_start"]').val('');
				validation.removeElements($('#hourStart'));
				$('#hourEnd').hide();
				$('[name="hour_end"]').val('');
				validation.removeElements($('#hourEnd'));
				$('#leaveTimeDay').show();
				$('#leaveTimeHour').hide();
				validation.removeElements($('[name="fdLeaveHour"]')[0]);
				validation.addElements($('[name="fdLeaveTime"]')[0], 'required number min(0.5) max(365) fraction');
			};
			
			var getStatType = function(leaveName) {
				var leaveRules = eval('${leaveRules}');
				if(leaveRules && leaveRules.length > 0){
					for(var i in leaveRules) {
						var leaveRule = leaveRules[i];
						if(leaveRule && leaveRule.leaveName == leaveName){
							return leaveRule.statType;
						}
					}
				}
				return '';
			};
			
			var getLeaveType = function(leaveName) {
				var leaveRules = eval('${leaveRules}');
				if(leaveRules && leaveRules.length > 0){
					for(var i in leaveRules) {
						var leaveRule = leaveRules[i];
						if(leaveRule && leaveRule.leaveName == leaveName){
							return leaveRule.leaveType;
						}
					}
				}
				return '';
			};
			window.onChangePerson = function(){
				changeLeaveTime();
			};
			
			window.onChangeStart = function(v) {
				$('[name="fdStartTime"]').val(v);
				changeLeaveTime();
			};
			
			window.onChangeEnd = function(v) {
				$('[name="fdEndTime"]').val(v);
				changeLeaveTime();
			};
			
			window.onChangeStartNoon = window.onChangeEndNoon = function() {
				changeLeaveTime();
			};
			
			var changeLeaveTime = function() {
				var fdPersonId = $('[name="fdPersonId"]').val();
				var startTime = $('[name="fdStartTime"]').val();
				var endTime = $('[name="fdEndTime"]').val();
				var startNoon = $('[name="fdStartNoon"]').val();
				var endNoon = $('[name="fdEndNoon"]').val();
				var statType = $('[name="fdStatType"]').val();
				var fdLeaveTime =  $('[name="fdLeaveTime"]');
				var fdLeaveType = $('[name="fdLeaveType"]').val();
				var leaveDays = 0;
				if(!fdPersonId){
					return;
				}
				if(statType=='2'){
					if(!startNoon ||  !endNoon){
						return;
					}
				}
				if(startTime && endTime && statType) {
					var datas = {
							fdStartTime:startTime,
							fdEndTime:endTime,
							fdStatType:statType,
							fdStartNoon:startNoon,
							fdEndNoon:endNoon,
							fdLeaveType:fdLeaveType,
							docCreatorId:fdPersonId
						};
						jQuery.ajax({
				            type: "post", 
				            url: "${LUI_ContextPath}/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=getLeaveTime", 
				            dataType: "json",
				            data:datas,
				            success: function (data) {
				            	if(data && data.status==1){
				            		var times = data.leaveTimes;
									var leaveDays = data.leaveDays;
				            		$('[name="fdTotalTime"]').val(times);
									$('[name="fdLeaveTime"]').val(leaveDays);
									if(statType!='3'){
				            			times = times/(60*24);
				            			times = times < 0 ? '' : times;
				            			$('[name="fdLeaveTime"]').val(times);

				            			$('#leaveTimeDayStr').html(data.leaveTimesStr);
				            		}else{
				            			times = times/(60);
				            			times = times < 0 ? '' : times;
				            			times =  parseInt(times *10)/10;
				            			$('[name="fdLeaveHour"]').val(times);
				            			$('#leaveTimeHourStr').html(data.leaveTimesStr);
				            		}

				            		if(times==0){
				            			$('#leaveTimeTip').show();
				            		}else{
				            			$('#leaveTimeTip').hide();
				            		}
				            	}else{
				            		$('[name="fdLeaveHour"]').val(0);
				            		$('[name="fdLeaveTime"]').val(0);
				            		$('#leaveTimeTip').show();
				            	}
				            },
				            error:function(err){
				            	$('[name="fdLeaveHour"]').val(0);
				            	$('[name="fdLeaveTime"]').val(0);
				            	$('#leaveTimeTip').show();
				            }
						});
				}
			};
			
			window.selectReview = function(){
				var fdReviewId = $('[name="fdReviewId"]').val();
				var url="/sys/time/sys_time_leave_detail/reviewDialog.jsp?reviewId=" + fdReviewId;
				dialog.iframe(url, "${ lfn:message('sys-time:sysTimeLeaveDetail.selectReview') }", function(arg){
					if(arg){
						$('[name="fdReviewId"]').val(arg.reviewId);
						$('[name="fdReviewName"]').val(arg.reviewName);
					}
				},{width:800,height:540});
			};
			
			validation.addValidator('fraction', "${ lfn:message('sys-time:sysTimeLeaveRule.atMostHalf') }", function(v, e, o){
				if(v && e){
					return /^[0-9]+(\.[05])?$/g.test(v);
				}
			});
			
			validation.addValidator('beforeEnd', "${ lfn:message('sys-time:sysTimeLeaveDetail.beforeEnd') }", function(v, e, o){
				var endTime = $('[name="fdEndTime"]').val();
				if(v && endTime){
					var dateObj = Com_GetDate(v, 'date', Com_Parameter.Date_format);
					var endTimeObj =  Com_GetDate(endTime, 'date', Com_Parameter.Date_format);
					if(dateObj && endTimeObj) {
						return endTimeObj.getTime() >= dateObj.getTime();
					}
				}
				return true;
			});
			
			validation.addValidator('afterStart', "${ lfn:message('sys-time:sysTimeLeaveDetail.afterStart') }", function(v, e, o){
				var startTime = $('[name="fdStartTime"]').val();
				if(v && startTime){
					var dateObj = Com_GetDate(v, 'date', Com_Parameter.Date_format);
					var startTimeObj =  Com_GetDate(startTime, 'date', Com_Parameter.Date_format);
					if(dateObj && startTimeObj) {
						return dateObj.getTime() >= startTimeObj.getTime();
					}
				}
				return true;
			});
			
			validation.addValidator('lessOneYear', "${ lfn:message('sys-time:sysTimeLeaveDetail.lessOneYear') }", function(v, e, o){
				var startTime = $('[name="fdStartTime"]').val();
				var endTime = $('[name="fdEndTime"]').val();
				if(startTime && endTime){
					var start =  Com_GetDate(startTime, 'date', Com_Parameter.Date_format);
					var end =  Com_GetDate(endTime, 'date', Com_Parameter.Date_format);
					if(start && end) {
						return (end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24)  <= 365;
					}
				}
				return true;
			});
			
			Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function(){
				var fdTotalTime =  $('[name="fdTotalTime"]').val();
				if(fdTotalTime<=0){
					dialog.failure('请假时长必须大于0');
					return false;
				}
				return true;
			};
			
		});
		</script>
	</template:replace>
</template:include>