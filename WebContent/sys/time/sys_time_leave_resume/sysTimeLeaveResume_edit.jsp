<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit">
	<template:replace name="title">
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3"> 
			<c:choose>
				<c:when test="${ sysTimeLeaveResumeForm.method_GET == 'edit' }">
				</c:when>
				<c:when test="${ sysTimeLeaveResumeForm.method_GET == 'add' }">
					<kmss:auth requestURL="/sys/time/sys_time_leave_resume/sysTimeLeaveResume.do?method=save">
						<ui:button text="${lfn:message('button.save') }" onclick="onCommit('save');"></ui:button>
					</kmss:auth>
					<kmss:auth requestURL="/sys/time/sys_time_leave_resume/sysTimeLeaveResume.do?method=saveadd">
						<ui:button text="${ lfn:message('button.saveadd') }" onclick="onCommit('saveadd');"></ui:button>
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
			${ lfn:message('sys-time:table.sysTimeLeaveResume') }
		</p>
		
		<html:form action="/sys/time/sys_time_leave_resume/sysTimeLeaveResume.do">
			<html:hidden property="fdId" />
			<html:hidden property="method_GET" />
			<html:hidden property="fdResumeType" />
			<html:hidden property="fdOprType" />
			<html:hidden property="fdOprStatus" />
			<html:hidden property="fdDetailName" />
			<html:hidden property="fdDetailId" />
			<html:hidden property="fdDetailStartTime" />
			<html:hidden property="fdDetailEndTime" />
			<html:hidden property="fdDetailStatType" />
			<html:hidden property="fdTotalTime" />
			<html:hidden property="fdLeaveTime" />
			<div class="lui_form_content_frame" style="padding-top:20px">
				<table class="tb_normal" width=100%>
					<tr>
						<%-- 人员 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveResume.fdPerson') }
						</td>
						<td colspan="3">
							<xform:address showStatus="readOnly" style="width: 50%" propertyId="fdPersonId" propertyName="fdPersonName" orgType="ORG_TYPE_PERSON" required="true" subject="${ lfn:message('sys-time:sysTimeLeaveResume.fdPerson') }" onValueChange="onChangeStart">
							</xform:address>
						</td>
					</tr>
					<tr>
						<%-- 请假开始时间 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveResume.fdStartTime') }
						</td>
						<td style="width: 35%">
							<c:if test="${sysTimeLeaveResumeForm.fdDetailStatType !='3' }">
								<xform:datetime property="fdStartTime" style="width: 75%" dateTimeType="date" validators="beforeEnd betweenDate required" subject="${ lfn:message('sys-time:sysTimeLeaveResume.fdStartTime') }" onValueChange="onChangeStart">
								</xform:datetime>
							</c:if>
							<c:if test="${sysTimeLeaveResumeForm.fdDetailStatType=='3' }">
								<xform:datetime property="fdStartTime" style="width: 75%" dateTimeType="datetime" validators="beforeEnd betweenDate required" subject="${ lfn:message('sys-time:sysTimeLeaveResume.fdStartTime') }" onValueChange="onChangeStart">
								</xform:datetime>
							</c:if>
							<c:if test="${sysTimeLeaveResumeForm.fdDetailStatType=='2' }">
								<xform:select property="fdStartNoon" showPleaseSelect="false" validators="${statType eq 2 ? 'required' : ''}" onValueChange="onChangeNoon" style="width: 20%">
									<xform:simpleDataSource value="1">${ lfn:message('sys-time:sysTimeLeaveDetail.morning') }</xform:simpleDataSource>
									<xform:simpleDataSource value="2">${ lfn:message('sys-time:sysTimeLeaveDetail.afterNoon') }</xform:simpleDataSource>
								</xform:select>
							</c:if>
						</td>
						<%-- 请假结束时间 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveResume.fdEndTime') }
						</td>
						<td style="width: 35%">
							<c:if test="${sysTimeLeaveResumeForm.fdDetailStatType !='3' }">
								<xform:datetime property="fdEndTime" style="width: 75%" dateTimeType="date" validators="afterStart betweenDate required" subject="${ lfn:message('sys-time:sysTimeLeaveResume.fdEndTime') }" onValueChange="onChangeStart">
								</xform:datetime>
							</c:if>
							<c:if test="${sysTimeLeaveResumeForm.fdDetailStatType=='3' }">
								<xform:datetime property="fdEndTime" style="width: 75%" dateTimeType="datetime" validators="afterStart betweenDate required" subject="${ lfn:message('sys-time:sysTimeLeaveResume.fdEndTime') }" onValueChange="onChangeStart">
								</xform:datetime>
							</c:if>
							<c:if test="${sysTimeLeaveResumeForm.fdDetailStatType=='2' }">
								<xform:select property="fdEndNoon" showPleaseSelect="false" validators="${statType eq 2 ? 'required' : ''}" onValueChange="onChangeNoon" style="width: 20%">
									<xform:simpleDataSource value="1">${ lfn:message('sys-time:sysTimeLeaveDetail.morning') }</xform:simpleDataSource>
									<xform:simpleDataSource value="2">${ lfn:message('sys-time:sysTimeLeaveDetail.afterNoon') }</xform:simpleDataSource>
								</xform:select>
							</c:if>
						</td>
					</tr>
					<tr>
						<%-- 时长--%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveResume.fdLeaveTime') }
						</td>
						<td colspan="3">
							<c:if test="${sysTimeLeaveResumeForm.fdDetailStatType !='3' }">
								<span id="leaveTimeDayStr"></span>
							</c:if>
							<c:if test="${sysTimeLeaveResumeForm.fdDetailStatType =='3' }">
								<span id="leaveTimeHourStr"></span>
							</c:if>
						</td>
					</tr>
					<tr>
						<%-- 扣减方式 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveResume.fdOprType') }
						</td>
						<td>
							<html:hidden property="fdOprType" />
							<c:if test="${sysTimeLeaveResumeForm.fdOprType eq '1' }">
								${ lfn:message('sys-time:sysTimeLeaveResume.fdOprType.review') }
							</c:if>
							<c:if test="${sysTimeLeaveResumeForm.fdOprType eq '2' }">
								${ lfn:message('sys-time:sysTimeLeaveResume.fdOprType.manual') }
							</c:if>
						</td>
						<%-- 扣减情况 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveResume.fdOprStatus') }
						</td>
						<td>
							<html:hidden property="fdOprStatus" />
							<html:hidden property="fdOprDesc" />
							<c:if test="${sysTimeLeaveResumeForm.fdOprStatus eq '0'  || sysTimeLeaveResumeForm.fdOprStatus eq null}">
								${ lfn:message('sys-time:sysTimeLeaveResume.fdOprStatus.no') }
							</c:if>
						</td>
					</tr>
					<tr>
						<%-- 关联流程 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveResume.fdReview') }
						</td>
						<td colspan="3">
							<xform:dialog propertyId="fdReviewId" propertyName="fdReviewName" style="width: 60%">
								selectReview();
							</xform:dialog>
						</td>
					</tr>
					<tr>
						<%-- 关联请假明细 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveResume.fdLeaveDetail') }
						</td>
						<td colspan="3">
							<table class="tb_normal" width=100%>
								<tr>
									<td class="td_normal_title">
										${ lfn:message('sys-time:sysTimeLeaveDetail.fdLeaveName') }
									</td>
									<td class="td_normal_title">
										${ lfn:message('sys-time:sysTimeLeaveDetail.fdStartTime') }
									</td>
									<td class="td_normal_title">
										${ lfn:message('sys-time:sysTimeLeaveDetail.fdEndTime') }
									</td>
								</tr>
								<tr>
									<td>
										${sysTimeLeaveResumeForm.fdDetailName }
									</td>
									<td>
										<c:choose>
											<c:when test="${sysTimeLeaveResumeForm.fdDetailStatType eq '1'}">
												<xform:datetime property="fdStartTime" dateTimeType="date" showStatus="view" value="${sysTimeLeaveResumeForm.fdDetailStartTime }">
												</xform:datetime>
											</c:when>
											<c:when test="${sysTimeLeaveResumeForm.fdDetailStatType eq '2'}">
												<xform:datetime property="fdStartTime" dateTimeType="date" showStatus="view" value="${sysTimeLeaveResumeForm.fdDetailStartTime }">
												</xform:datetime>
												<c:if test="${sysTimeLeaveResumeForm.fdDetailStartNoon eq '1' }">
													${ lfn:message('sys-time:sysTimeLeaveDetail.morning') }
												</c:if>
												<c:if test="${sysTimeLeaveResumeForm.fdDetailStartNoon eq '2' }">
													${ lfn:message('sys-time:sysTimeLeaveDetail.afterNoon') }
												</c:if>
											</c:when>
											<c:otherwise>
												<xform:datetime property="fdStartTime" dateTimeType="datetime" showStatus="view" value="${sysTimeLeaveResumeForm.fdDetailStartTime }">
												</xform:datetime>
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${sysTimeLeaveResumeForm.fdDetailStatType eq '1'}">
												<xform:datetime property="fdEndTime" dateTimeType="date" showStatus="view" value="${sysTimeLeaveResumeForm.fdDetailEndTime }">
												</xform:datetime>
											</c:when>
											<c:when test="${sysTimeLeaveResumeForm.fdDetailStatType eq '2'}">
												<xform:datetime property="fdEndTime" dateTimeType="date" showStatus="view" value="${sysTimeLeaveResumeForm.fdDetailEndTime }">
												</xform:datetime>
												<c:if test="${sysTimeLeaveResumeForm.fdDetailEndNoon eq '1' }">
													${ lfn:message('sys-time:sysTimeLeaveDetail.morning') }
												</c:if>
												<c:if test="${sysTimeLeaveResumeForm.fdDetailEndNoon eq '2' }">
													${ lfn:message('sys-time:sysTimeLeaveDetail.afterNoon') }
												</c:if>
											</c:when>
											<c:otherwise>
												<xform:datetime property="fdEndTime" dateTimeType="datetime" showStatus="view" value="${sysTimeLeaveResumeForm.fdDetailEndTime }">
												</xform:datetime>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<kmss:ifModuleExist path="/sys/attend">
					<tr>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveResume.fdIsUpdateAttend') }
						</td>
						<td colspan="3">
							<ui:switch property="fdIsUpdateAttend" disabledText="${ lfn:message('sys-time:sysTimeLeaveDetail.fdIsUpdateAttend.no') }" enabledText="${ lfn:message('sys-time:sysTimeLeaveDetail.fdIsUpdateAttend.yes') }">
							</ui:switch>
						</td>
					</tr>
					</kmss:ifModuleExist>
					<tr>
						<%-- 录入人员 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveResume.docCreator') }
						</td>
						<td>
							<html:hidden property="docCreatorId" />
							<html:hidden property="docCreatorName" />
							${sysTimeLeaveResumeForm.docCreatorName }
						</td>
						<%-- 录入时间 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveResume.docCreateTime') }
						</td>
						<td>
							<html:hidden property="docCreateTime" />
							${sysTimeLeaveResumeForm.docCreateTime }
						</td>
					</tr>
				</table>
			</div>	
		</html:form>
		<script type="text/javascript">
		var validation = $KMSSValidation(document.forms['sysTimeLeaveResumeForm']);
		seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
			
			window.onChangeStart = function(v) {
				changeLeaveTime();
			};
			window.onChangeNoon = function(){
				changeLeaveTime();
			};

			var changeLeaveTime = function() {
				var fdPersonId = "${sysTimeLeaveResumeForm.fdPersonId}";
				var startTime = $('[name="fdStartTime"]').val();
				var endTime = $('[name="fdEndTime"]').val();
				var fdLeaveTime =  $('[name="fdLeaveTime"]');
				var statType = "${sysTimeLeaveResumeForm.fdDetailStatType}";
				var startNoon = $('[name="fdStartNoon"]').val();
				var endNoon = $('[name="fdEndNoon"]').val();
				var resumeType = "${resumeType}";
				
				if(!fdPersonId){
					return;
				}

				if(statType=='2'){
					if(!startNoon ||  !endNoon){
						return;
					}
				}
				if(startTime && endTime) {
					var datas = {
						fdStartTime:startTime,
						fdEndTime:endTime,
						fdStatType:statType,
						fdStartNoon:startNoon,
						fdEndNoon:endNoon,
						fdResumeType:resumeType,
						docCreatorId:fdPersonId
					};
					jQuery.ajax({
			            type: "post", 
			            url: "${LUI_ContextPath}/sys/time/sys_time_leave_resume/sysTimeLeaveResume.do?method=getLeaveTime", 
			            dataType: "json",
			            data:datas,
			            success: function (data) {
			            	if(data && data.status==1){
			            		var times = data.leaveTimes;
			            		var timesTxt = '';
								var leaveDays = data.leaveDays;
								$('[name="fdTotalTime"]').val(times);
								$('[name="fdLeaveTime"]').val(leaveDays);
			            		if(statType!='3'){
			            			$('#leaveTimeDayStr').html(data.leaveTimesStr);
			            		}else{
			            			$('#leaveTimeHourStr').html(data.leaveTimesStr);
			            		}
			            	}else{
			            		fdLeaveTime.val(0);
			            	}
			            },
			            error:function(err){
			            	fdLeaveTime.val(0);
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
			
			validation.addValidator('betweenDate', "${ lfn:message('sys-time:sysTimeLeaveResume.betweenDate') }", function(v, e, o){
				var startTime = $('[name="fdDetailStartTime"]').val();
				var endTime = $('[name="fdDetailEndTime"]').val();
				var statType = '${sysTimeLeaveResumeForm.fdDetailStatType}';
				if(v && startTime && endTime){
					var date = statType=='3' ? 'datetime':'date';
					var dateObj = Com_GetDate(v, date);
					var start =  Com_GetDate(startTime, date);
					var end =  Com_GetDate(endTime, date);
		
					if(statType=='2'){
						var leaveStartNoon = "${sysTimeLeaveResumeForm.fdDetailStartNoon }";
						var leaveEndNoon = "${sysTimeLeaveResumeForm.fdDetailEndNoon }";
						
						if(leaveStartNoon=='2'){
							start.setHours(12);
						}else{
							start.setHours(0);
						}
						if(leaveEndNoon=='1'){
							end.setHours(12);
						}else{
							end.setDate(end.getDate()+1);
						}
						
						//销假时间
						if(e.name=='fdStartTime'){
							var startNoon = $('select[name="fdStartNoon"]').val();
							if(startNoon=='2'){
								dateObj.setHours(12);
							}else{
								dateObj.setHours(0);
							}
						}
						if(e.name=='fdEndTime'){
							var endNoon = $('select[name="fdEndNoon"]').val();
							if(endNoon=='1'){
								dateObj.setHours(12);
							}else{
								dateObj.setDate(dateObj.getDate()+1);
							}
						}
					}
					if(start && end && dateObj) {
						return dateObj.getTime() >= start.getTime() && dateObj.getTime() <= end.getTime();
					}
				}
				return true;
			});
			
			Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function(){
				var fdTotalTime =  $('[name="fdTotalTime"]').val();
				if(fdTotalTime<=0){
					dialog.failure('销假时长必须大于0');
					return false;
				}
				return true;
			};
			window.onCommit =function(method){
				if(!validation.validate()){
					return;
				}
				Com_Submit(document.sysTimeLeaveResumeForm, method);
			}
			
		});
		</script>
	</template:replace>
</template:include>