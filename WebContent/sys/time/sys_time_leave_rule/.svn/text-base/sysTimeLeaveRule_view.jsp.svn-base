<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.time.model.SysTimeLeaveConfig" %>
<%
	SysTimeLeaveConfig leaveConfig = new SysTimeLeaveConfig();
	pageContext.setAttribute("dayConvertTime", leaveConfig.getDayConvertTime());
%>
<template:include ref="default.view" sidebar="auto">
	<template:replace name="title">
		${ lfn:message('sys-time:table.sysTimeLeaveRule') }
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<kmss:auth requestURL="/sys/time/sys_time_leave_rule/sysTimeLeaveRule.do?method=edit">
			<ui:button text="${lfn:message('button.edit')}" 
				onclick="Com_OpenWindow('sysTimeLeaveRule.do?method=edit&fdId=${param.fdId}','_self');" order="2">
			</ui:button>
			</kmss:auth>
			<kmss:auth requestURL="/sys/time/sys_time_leave_rule/sysTimeLeaveRule.do?method=delete">
			<ui:button text="${lfn:message('button.delete')}" order="4" onclick="deleteDoc();">
			</ui:button>
			</kmss:auth>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content"> 
		<div class='lui_form_title_frame' style="padding-top: 10px;">
			<div class='lui_form_subject'>
				${ lfn:message('sys-time:table.sysTimeLeaveRule') }
			</div>
		</div>
		<div class="lui_form_content_frame" style="padding-top:20px">
			<table class="tb_normal" width=100%>
				<tr>
					<td width="15%" class="td_normal_title" style="text-align: center;">
						${ lfn:message('sys-time:sysTimeLeaveRule.fdName') }
					</td>
					<td width="35%">
						<c:out value="${sysTimeLeaveRuleForm.fdName}"></c:out>
					</td>
					<td width="15%" class="td_normal_title" style="text-align: center;">
						${ lfn:message('sys-time:sysTimeLeaveRule.fdSerialNo') }
					</td>
					<td width="35%">
						${sysTimeLeaveRuleForm.fdSerialNo }
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_normal_title" style="text-align: center;">
						${ lfn:message('sys-time:sysTimeLeaveRule.fdOrder') }
					</td>
					<td width="35%">
						${sysTimeLeaveRuleForm.fdOrder }
					</td>
					<td width="15%" class="td_normal_title" style="text-align: center;">
						${ lfn:message('sys-time:sysTimeLeaveRule.fdIsAvailable') }
					</td>
					<td width="35%">
						<c:if test="${sysTimeLeaveRuleForm.fdIsAvailable eq 'true'}">
							${lfn:message('sys-time:sysTimeLeaveRule.fdIsAvailable.enable')}
						</c:if>
						<c:if test="${sysTimeLeaveRuleForm.fdIsAvailable eq null || sysTimeLeaveRuleForm.fdIsAvailable eq 'false'}">
							${lfn:message('sys-time:sysTimeLeaveRule.fdIsAvailable.disable')}
						</c:if>
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_normal_title" style="text-align: center;">
						${ lfn:message('sys-time:sysTimeLeaveRule.fdStatType') }
					</td>
					<td width="35%">
						<c:if test="${sysTimeLeaveRuleForm.fdStatType eq 1}">
							${ lfn:message('sys-time:sysTimeLeaveRule.fdStatType.day') }
						</c:if>
						<c:if test="${sysTimeLeaveRuleForm.fdStatType eq 2}">
							${ lfn:message('sys-time:sysTimeLeaveRule.fdStatType.halfDay') }
						</c:if>
						<c:if test="${sysTimeLeaveRuleForm.fdStatType eq 3}">
							${ lfn:message('sys-time:sysTimeLeaveRule.fdStatType.hour') }
						</c:if>
					</td>
					<td width="15%" class="td_normal_title" style="text-align: center;">
						${ lfn:message('sys-time:sysTimeLeaveRule.fdStatDayType') }
					</td>
					<td width="35%">
						<c:if test="${sysTimeLeaveRuleForm.fdStatDayType eq 1}">
							${ lfn:message('sys-time:sysTimeLeaveRule.fdStatDayType.workDay') }
						</c:if>
						<c:if test="${sysTimeLeaveRuleForm.fdStatDayType eq 2}">
							${ lfn:message('sys-time:sysTimeLeaveRule.fdStatDayType.normalDay') }
						</c:if>
					</td>
				</tr>
				<c:if test="${sysTimeLeaveRuleForm.fdStatType eq 3}">
				<tr>
					<td width="15%" class="td_normal_title" style="text-align: center;">
						${ lfn:message('sys-time:sysTimeLeaveRule.fdDayConvertTime') }
					</td>
					<td colspan="3">
						${empty dayConvertTime ? '8' : dayConvertTime}
						${ lfn:message('sys-time:sysTimeLeaveRule.fdDayConvertTime.text') }
					</td>
				</tr>
				</c:if>
				<tr>
					<td width="15%" class="td_normal_title" style="text-align: center;">
						${ lfn:message('sys-time:sysTimeLeaveRule.fdIsAmount') }
					</td>
					<td colspan="3">
						<c:if test="${sysTimeLeaveRuleForm.fdIsAmount eq null || !sysTimeLeaveRuleForm.fdIsAmount}">
							${ lfn:message('sys-time:sysTimeLeaveRule.fdIsAmount.no') }
						</c:if>
						<c:if test="${sysTimeLeaveRuleForm.fdIsAmount }">
							${ lfn:message('sys-time:sysTimeLeaveRule.fdIsAmount.yes') }
						</c:if>
					</td>
				</tr>
				<c:if test="${sysTimeLeaveRuleForm.fdIsAmount }">
				<tr>
					<td width="15%" class="td_normal_title" style="text-align: center;">
						${ lfn:message('sys-time:sysTimeLeaveRule.fdAmountType') }
					</td>
					<td colspan="3">
						<xform:select property="fdAmountType" showStatus="view">
							<xform:simpleDataSource value="1">${ lfn:message('sys-time:sysTimeLeaveRule.fdAmountType.custom') }</xform:simpleDataSource>
							<xform:simpleDataSource value="2">${ lfn:message('sys-time:sysTimeLeaveRule.fdAmountType.auto') }</xform:simpleDataSource>
							<xform:simpleDataSource value="3">按规则自动发放(每年1月1日发放)</xform:simpleDataSource>
							<xform:simpleDataSource value="4">按规则自动发放(按入职日期发放)</xform:simpleDataSource>
						</xform:select>
					</td>
				</tr>
				<c:if test="${sysTimeLeaveRuleForm.fdAmountType eq '2'}">
				<tr>
					<td class="td_normal_title" style="text-align: center;">
						${ lfn:message('sys-time:sysTimeLeaveRule.fdAutoAmount') }
					</td>
					<td colspan="3">
						${sysTimeLeaveRuleForm.fdAutoAmount }
						${ lfn:message('sys-time:sysTimeLeaveRule.day') }
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" style="text-align: center;">
						${ lfn:message('sys-time:sysTimeLeaveRule.fdAmountCalType') }
					</td>
					<td colspan="3">
						<xform:radio property="fdAmountCalType" alignment="V" showStatus="view">
							<xform:simpleDataSource value="1">
								${ lfn:message('sys-time:sysTimeLeaveRule.fdAmountCalType.once') }
							</xform:simpleDataSource>
							<xform:simpleDataSource value="2">
								${ lfn:message('sys-time:sysTimeLeaveRule.fdAmountCalType.append') }
								<span style="color: #999">${ lfn:message('sys-time:sysTimeLeaveRule.fdAmountCalType.append.tips') }</span>
							</xform:simpleDataSource>
							<xform:simpleDataSource value="3">
								${ lfn:message('sys-time:sysTimeLeaveRule.fdAmountCalType.extend') }
								${sysTimeLeaveRuleForm.fdAmountCalType != '3' ? 'n' : sysTimeLeaveRuleForm.fdValidDays}
								${ lfn:message('sys-time:sysTimeLeaveRule.day') }
							</xform:simpleDataSource>
						</xform:radio>
					</td>
				</tr>
				</c:if>
				<c:if test="${sysTimeLeaveRuleForm.fdAmountType eq '3' || sysTimeLeaveRuleForm.fdAmountType eq '4'}">
				<%-- 额度计算规则--%> 
				<tr>
					<td class="td_normal_title" style="text-align: center;">
						${ lfn:message('sys-time:sysTimeLeaveRule.fdAmountCalRule') }
					</td>
					<td colspan="3">
						<xform:radio property="fdAmountCalRule" alignment="H" showStatus="view">
							<xform:simpleDataSource value="1">${lfn:message('sys-time:sysTimeLeaveRule.amount.rule.work') }</xform:simpleDataSource>
							<kmss:ifModuleExist path="/hr/staff">
								<xform:simpleDataSource value="2">${lfn:message('sys-time:sysTimeLeaveRule.amount.rule.company') }</xform:simpleDataSource>
							</kmss:ifModuleExist>
						</xform:radio>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" style="text-align: center;">
						${ lfn:message('sys-time:sysTimeLeaveRuleList.fdGrant') }
						<div><a href="javascript:void(0);"  onclick="Com_OpenWindow('ruleOperation_help.jsp');" class="sys_notify_add">${ lfn:message('sys-time:sysTimeLeaveRuleList.fdruleDescription') }</a></div>
					</td>
					<td colspan="3">
						<input type="hidden" name="sysTimeLeaveRuleList[0].fdId" value="${sysTimeLeaveRuleItem.fdId}" /> 
						<input type="hidden" name="sysTimeLeaveRuleList[0].fdRulesId" value="${sysTimeLeaveRuleItem.fdRulesId}" />
							<span id="countent">
							<span id="fixed">
										<input type="hidden" name="sysTimeLeaveRuleList[0].fdEntryType" value="2" />
										<span>${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay4') }<span id="startEntryId0"><xform:text property="sysTimeLeaveRuleList[0].fdStartEntry" showStatus="readOnly" style="width:50px;" subject="${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay12') }"/>${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay5') }</span>
									<span id="endEntryId0"><xform:text property="sysTimeLeaveRuleList[0].fdEndEntry" showStatus="readOnly" style="width:50px;"  subject="${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay12') }"/>${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay6') }</span></span>
										<xform:radio property="sysTimeLeaveRuleList[0].fdQuotaType" alignment="H"  onValueChange="changeQuotaType(this);" value="${sysTimeLeaveRuleForm.sysTimeLeaveRuleList[0].fdQuotaType eq null ? 1 : sysTimeLeaveRuleForm.sysTimeLeaveRuleList[0].fdQuotaType}">
											<xform:simpleDataSource value="1" >
												${ lfn:message('sys-time:sysTimeLeaveRuleList.fdQuotaType1') }
											</xform:simpleDataSource>
											<xform:simpleDataSource value="2">
												${ lfn:message('sys-time:sysTimeLeaveRuleList.fdQuotaType2') }
											</xform:simpleDataSource>
										</xform:radio>
										<span zx="current">${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay7') }</span>
										
										<span zx="laterName" style="${sysTimeLeaveRuleForm.sysTimeLeaveRuleList[0].fdQuotaType eq '2' ? '' : 'display:none'}">${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay8') }</span>
										<span zx="laterNameVal">
										<xform:text property="sysTimeLeaveRuleList[0].fdHolidayDays" validators="required number min(0.5) max(365) fraction" showStatus="readOnly" style="width:50px;" subject="${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay13') }"/>${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay1') }
										</span>
										
										<span zx="later" style="${sysTimeLeaveRuleForm.sysTimeLeaveRuleList[0].fdQuotaType eq '2' ? '' : 'display:none'}">
										
										${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay9') }
										<xform:text property="sysTimeLeaveRuleList[0].fdIncreaseDays" showStatus="readOnly" style="width:50px;" subject="${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay13') }"/>${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay1') }
										</span>
									</span>
									<span id="now">
										<input type="hidden" name="sysTimeLeaveRuleList[0].fdEntryType" value="1" />
										<span>${ lfn:message('sys-time:sysTimeLeaveRuleList.fdOneYear') }</span>
										<xform:radio property="sysTimeLeaveRuleList[0].fdQuotaType" alignment="H"  onValueChange="changeEntryType(0);" value="${sysTimeLeaveRuleForm.sysTimeLeaveRuleList[0].fdQuotaType eq null ? 1 : sysTimeLeaveRuleForm.sysTimeLeaveRuleList[0].fdQuotaType}">
											<xform:simpleDataSource value="1">
												${ lfn:message('sys-time:sysTimeLeaveRuleList.fdQuotaType1') }
											</xform:simpleDataSource>
											<xform:simpleDataSource value="3">
												${ lfn:message('sys-time:sysTimeLeaveRuleList.fdQuotaType3') }
											</xform:simpleDataSource>
										</xform:radio>
										<span id="currentEntry" style="position:relative;display: inline-block" ><xform:text property="sysTimeLeaveRuleList[0].fdHolidayDays" validators="required number min(0) max(365) fraction" showStatus="readOnly" style="width:50px;" subject="${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay10') }"/><span class="zx" style="position: absolute;right: -20px;    top: 0;">${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay1') }</span></span>
									<span id="count" style="${fdEntryType eq '3' ? '' : 'display:none'}">${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay2') }<xform:text property="sysTimeLeaveRuleList[0].fdCountDays" showStatus="readOnly" style="width:50px;" subject="${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay11') }"/>${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay3') }</span>
									</span>
									</span>
						<table id="TABLE_DocList">
								<c:forEach items="${sysTimeLeaveRuleForm.sysTimeLeaveRuleList}"  var="sysTimeLeaveRuleItem" varStatus="vstatus" begin="1">
									<tr KMSS_IsContentRow="1">
										<td style="width: 100%" KMSS_IsRowIndex="1">
										<input type="hidden" name="sysTimeLeaveRuleList[${vstatus.index}].fdId" value="${sysTimeLeaveRuleItem.fdId}" /> 
										<input type="hidden" name="sysTimeLeaveRuleList[${vstatus.index}].fdRulesId" value="${sysTimeLeaveRuleItem.fdRulesId}" />
										${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay4') }<xform:text property="sysTimeLeaveRuleList[${vstatus.index}].fdStartEntry"  value="${sysTimeLeaveRuleItem.fdStartEntry}" showStatus="readOnly" style="width:50px;"/>${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay5') }
											<xform:text property="sysTimeLeaveRuleList[${vstatus.index}].fdEndEntry" value="${sysTimeLeaveRuleItem.fdEndEntry}" showStatus="readOnly" style="width:50px;" subject="入职结束时间"/>${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay6') }
										<xform:radio property="sysTimeLeaveRuleList[${vstatus.index}].fdQuotaType" alignment="H" onValueChange="changeQuotaType();">
											<xform:simpleDataSource value="1" >
												${ lfn:message('sys-time:sysTimeLeaveRuleList.fdQuotaType1') }
											</xform:simpleDataSource>
											<xform:simpleDataSource value="2">
												${ lfn:message('sys-time:sysTimeLeaveRuleList.fdQuotaType2') }
											</xform:simpleDataSource>
										</xform:radio>
											
										<span id="current${vstatus.index}">
											${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay7') }
										</span>
										<span id="laterName${vstatus.index}"  style="${fdQuotaType eq '2' ? '' : 'display:none'}">${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay8') }</span>
										<xform:text property="sysTimeLeaveRuleList[${vstatus.index}].fdHolidayDays" showStatus="readOnly" style="width:50px;" required="true" validators="maxLength(200)" subject="入职开始时间"/>${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay1') }
										<span id="later${vstatus.index}" style="${sysTimeLeaveRuleItem.fdQuotaType eq '2' ? '' : 'display:none'}">
										${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay9') }
										<xform:text property="sysTimeLeaveRuleList[${vstatus.index}].fdIncreaseDays" showStatus="readOnly" style="width:50px;"  validators="maxLength(200)" subject="入职开始时间"/>${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay1') }
										
										</span>
										</td>
									</tr>
								</c:forEach>
							</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" style="text-align: center;">
						${ lfn:message('sys-time:sysTimeLeaveRule.fdAmountCalType') }
					</td>
					<td colspan="3">
						<xform:radio property="fdAmountCalType" alignment="V" showStatus="view">
							<xform:simpleDataSource value="1">
								${ lfn:message('sys-time:sysTimeLeaveRule.fdAmountCalType.once') }
							</xform:simpleDataSource>
							<xform:simpleDataSource value="2">
								${ lfn:message('sys-time:sysTimeLeaveRule.fdAmountCalType.append') }
								<span style="color: #999">${ lfn:message('sys-time:sysTimeLeaveRule.fdAmountCalType.append.tips') }</span>
							</xform:simpleDataSource>
							<xform:simpleDataSource value="3">
								${ lfn:message('sys-time:sysTimeLeaveRule.fdAmountCalType.extend') }
								${sysTimeLeaveRuleForm.fdAmountCalType != '3' ? 'n' : sysTimeLeaveRuleForm.fdValidDays}
								${ lfn:message('sys-time:sysTimeLeaveRule.day') }
							</xform:simpleDataSource>
						</xform:radio>
					</td>
				</tr>
				</c:if>
				</c:if>
			</table>
		</div>
		
		<script type="text/javascript">
		seajs.use(['lui/jquery','lui/topic','lui/dialog'],function($,topic,dialog){
			window.deleteDoc = function(){
				var delUrl = '<c:url value="/sys/time/sys_time_leave_rule/sysTimeLeaveRule.do"/>?method=delete&fdId=${param.fdId}';
				dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(result){
					if(result){
						Com_OpenWindow(delUrl,'_self');
					}	
				});
			};
			$(document).ready(function () {
				storage=window.localStorage;
				var childFixed = $("#fixed").html();
				var childNow = $("#now").html();
				storage.fixed=childFixed;
				storage.now=childNow;
				var fdAmountType = ${sysTimeLeaveRuleForm.fdAmountType};
				if (fdAmountType == 3) {
					$('#now').show();
					$('#fixed').hide();
					$("#countent").html("");
        			$("#countent").html(storage.now);
				}else{
					$('#fixed').show();
					$('#now').hide();
					$("#countent").html("");
        			$("#countent").html(storage.fixed);
				}
				var fdEntryType = ${sysTimeLeaveRuleForm.sysTimeLeaveRuleList[0].fdQuotaType};
				if(fdEntryType == '1'){
					$('#currentEntry').show();
					$('#count').hide();
				} else if(fdEntryType=='3') {
					$('#currentEntry').hide();
					$('#count').show();
				}
			});
		});
		</script>
	</template:replace>
</template:include>