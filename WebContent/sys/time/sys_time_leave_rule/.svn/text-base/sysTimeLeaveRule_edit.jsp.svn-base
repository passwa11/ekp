<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.time.model.SysTimeLeaveConfig" %>
<%
	SysTimeLeaveConfig leaveConfig = new SysTimeLeaveConfig();
	pageContext.setAttribute("dayConvertTime", leaveConfig.getDayConvertTime());
%>
<template:include ref="default.edit">
	<template:replace name="title">
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3"> 
			<c:choose>
				<c:when test="${ sysTimeLeaveRuleForm.method_GET == 'edit' }">
					<kmss:auth requestURL="/sys/time/sys_time_leave_rule/sysTimeLeaveRule.do?method=update">
					<ui:button text="${ lfn:message('button.update') }" onclick="updateDoc();"></ui:button>
					</kmss:auth>
				</c:when>
				<c:when test="${ sysTimeLeaveRuleForm.method_GET == 'add' }">
					<kmss:auth requestURL="/sys/time/sys_time_leave_rule/sysTimeLeaveRule.do?method=save">
					<ui:button text="${lfn:message('button.save') }" onclick="saveDoc();"></ui:button>
					</kmss:auth>
					<kmss:auth requestURL="/sys/time/sys_time_leave_rule/sysTimeLeaveRule.do?method=saveadd">
					<ui:button text="${ lfn:message('button.saveadd') }" onclick="saveAddDoc();"></ui:button>
					</kmss:auth>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="head">
		<style type="text/css">
			#amountCalType label{line-height: 2;}
		</style>
	</template:replace>
	<template:replace name="content">
	<script type="text/javascript">
		 Com_IncludeFile("doclist.js");
		 Com_IncludeFile("config.css", "${LUI_ContextPath}/sys/time/sys_time_leave_rule/", "css", true);	 
	</script>
		<p class="txttitle" style="margin: 15px 0;">
			${ lfn:message('sys-time:table.sysTimeLeaveRule') }
		</p>
		
		<html:form action="/sys/time/sys_time_leave_rule/sysTimeLeaveRule.do">
			<html:hidden property="fdId" />
			<html:hidden property="method_GET" />
			<html:hidden property="fdDayConvertTime" />
			<html:hidden property="isUpdateAmount" />
		
			<div class="lui_form_content_frame" style="padding-top:20px">
				<table class="tb_normal" width=100%>
					<tr>
						 <td width="15%" class="td_normal_title" style="text-align: center;">
							${ lfn:message('sys-time:sysTimeLeaveRule.fdName') }
						</td> 
						<td width="35%">
							<xform:text property="fdName" required="true" showStatus="edit" subject="${ lfn:message('sys-time:sysTimeLeaveRule.fdName') }" onValueChange="nameChange" validators="nameUnique">
							</xform:text>
						</td>
						<td width="15%" class="td_normal_title" style="text-align: center;">
							${ lfn:message('sys-time:sysTimeLeaveRule.fdSerialNo') }
						</td>
						<td width="35%">
							<xform:text property="fdSerialNo" required="true" showStatus="edit" subject="${ lfn:message('sys-time:sysTimeLeaveRule.fdSerialNo') }" validators="digits numUnique maxLength(9)">
							</xform:text>
							<c:if test="${param.method=='edit' }">
								<div style="color: red;font-size:12px;">${ lfn:message('sys-time:sysTimeLeaveRule.fdSerialNo.tip') }</div>
							</c:if>
						</td>
					</tr>
					<tr>
						<td width="15%" class="td_normal_title" style="text-align: center;">
							${ lfn:message('sys-time:sysTimeLeaveRule.fdOrder') }
						</td>
						<td width="35%">
							<xform:text property="fdOrder" showStatus="edit" subject="${ lfn:message('sys-time:sysTimeLeaveRule.fdOrder') }" validators="number">
							</xform:text>
						</td>
						<td width="15%" class="td_normal_title" style="text-align: center;">
							${ lfn:message('sys-time:sysTimeLeaveRule.fdIsAvailable') }
						</td>
						<td width="35%">
							<ui:switch property="fdIsAvailable" enabledText="${ lfn:message('sys-time:sysTimeLeaveRule.fdIsAvailable.enable') }" disabledText="${ lfn:message('sys-time:sysTimeLeaveRule.fdIsAvailable.disable') }" onValueChange="changeIsAvail()">
							</ui:switch>
						</td>
					</tr>
					<tr>
						<td width="15%" class="td_normal_title" style="text-align: center;">
							${ lfn:message('sys-time:sysTimeLeaveRule.fdStatType') }
						</td>
						<td width="35%">
							<xform:select property="fdStatType" required="true" showPleaseSelect="false" subject="${ lfn:message('sys-time:sysTimeLeaveRule.fdStatType') }" onValueChange="changeStatType" style="width: 100px;">
								<xform:simpleDataSource value="1">${ lfn:message('sys-time:sysTimeLeaveRule.fdStatType.day') }</xform:simpleDataSource>
								<xform:simpleDataSource value="2">${ lfn:message('sys-time:sysTimeLeaveRule.fdStatType.halfDay') }</xform:simpleDataSource>
								<xform:simpleDataSource value="3">${ lfn:message('sys-time:sysTimeLeaveRule.fdStatType.hour') }</xform:simpleDataSource>
							</xform:select>
						</td>
						<td width="15%" class="td_normal_title" style="text-align: center;">
							${ lfn:message('sys-time:sysTimeLeaveRule.fdStatDayType') }
						</td>
						<td width="35%">
							<xform:select property="fdStatDayType" required="true" showPleaseSelect="false" subject="${ lfn:message('sys-time:sysTimeLeaveRule.fdStatDayType') }" style="width: 150px;">
								<xform:simpleDataSource value="1">${ lfn:message('sys-time:sysTimeLeaveRule.fdStatDayType.workDay') }</xform:simpleDataSource>
								<xform:simpleDataSource value="2">${ lfn:message('sys-time:sysTimeLeaveRule.fdStatDayType.normalDay') }</xform:simpleDataSource>
							</xform:select>
						</td>
					</tr>
					<c:set var="isStatHour" value="${sysTimeLeaveRuleForm.fdStatType eq '3'}"></c:set>
					<tr id="convertBlock" style="${isStatHour ? '' : 'display:none'}">
						<td width="15%" class="td_normal_title" style="text-align: center;">
							${ lfn:message('sys-time:sysTimeLeaveRule.fdDayConvertTime') }
						</td>
						<td colspan="3">
							${empty dayConvertTime ? '8' : dayConvertTime}
							${ lfn:message('sys-time:sysTimeLeaveRule.fdDayConvertTime.text') }
						</td>
					</tr>
					<c:set var="isAmount" value="${sysTimeLeaveRuleForm.fdIsAmount eq null ? false : sysTimeLeaveRuleForm.fdIsAmount}"></c:set>
					<c:set var="amountType" value="${sysTimeLeaveRuleForm.fdAmountType eq null ? '1' : sysTimeLeaveRuleForm.fdAmountType}"></c:set>
					<c:set var="autoAmount" value="${sysTimeLeaveRuleForm.fdAutoAmount eq null ? '5' : sysTimeLeaveRuleForm.fdAutoAmount}"></c:set>
					<c:set var="amountCalType" value="${sysTimeLeaveRuleForm.fdAmountCalType eq null ? '1' : sysTimeLeaveRuleForm.fdAmountCalType}"></c:set>
					<c:set var="isOvertimeLeaveFlag" value="${sysTimeLeaveRuleForm.fdOvertimeLeaveFlag eq null ? false : sysTimeLeaveRuleForm.fdOvertimeLeaveFlag}"></c:set>
					<c:set var="amountCalRule" value="${sysTimeLeaveRuleForm.fdAmountCalRule eq null ? '1' : sysTimeLeaveRuleForm.fdAmountCalRule}"></c:set>
					<%-- 是否额度限制--%> 
					<tr>
						<td class="td_normal_title" style="text-align: center;">
							${ lfn:message('sys-time:sysTimeLeaveRule.fdIsAmount') }
						</td>
						<td colspan="3">
							<ui:switch property="fdIsAmount" enabledText="${ lfn:message('sys-time:sysTimeLeaveRule.fdIsAmount.yes') }" disabledText="${ lfn:message('sys-time:sysTimeLeaveRule.fdIsAmount.no') }" onValueChange="changeIsAmount();" checked="${isAmount }">
							</ui:switch>
							<div style="color: red;margin-top: 5px;font-size:12px;">
								${ lfn:message('sys-time:sysTimeLeaveRule.fdIsAmount.tips') }
							</div>
						</td>
					</tr>
					<tr id="overtimeToLeave_tr" style="${isAmount ? '' : 'display:none'}">
						<td class="td_normal_title" style="text-align: center;">
							${ lfn:message('sys-time:sysTimeLeaveRule.overtimeToLeave') }
						</td>
						<td colspan="3">
							<ui:switch property="fdOvertimeLeaveFlag" enabledText="${ lfn:message('sys-time:sysTimeLeaveRule.overtimeToLeave.enable') }" disabledText="${ lfn:message('sys-time:sysTimeLeaveRule.overtimeToLeave.unable') }" checked="${isOvertimeLeaveFlag }">
							</ui:switch>
							<div style="color: red;margin-top: 5px;font-size:12px;">
								${ lfn:message('sys-time:sysTimeLeaveRule.overtimeToLeave.tip') }
							</div>
						</td>
					</tr>
					<%-- 额度发放方式--%> 
					<tr id="amountType" style="${isAmount ? '' : 'display:none'}">
						<td class="td_normal_title" style="text-align: center;">
							${ lfn:message('sys-time:sysTimeLeaveRule.fdAmountType') }
						</td>
						<td colspan="3">
							<xform:select property="fdAmountType" showPleaseSelect="false" onValueChange="changeAmountType();" value="${amountType }">
								<xform:simpleDataSource value="1">${ lfn:message('sys-time:sysTimeLeaveRule.fdAmountType.custom') }</xform:simpleDataSource>
								<xform:simpleDataSource value="2">${ lfn:message('sys-time:sysTimeLeaveRule.fdAmountType.auto') }</xform:simpleDataSource>
								<xform:simpleDataSource value="3">按规则自动发放(每年1月1日发放)</xform:simpleDataSource>
								<xform:simpleDataSource value="4">按规则自动发放(按入职日期发放)</xform:simpleDataSource>
							</xform:select>
						</td>
					</tr>
					<%-- 自动发放额度 --%> 
					<tr id="autoAmount" style="${isAmount && sysTimeLeaveRuleForm.fdAmountType eq '2' ? '' : 'display:none'}">
						<td class="td_normal_title" style="text-align: center;">
							${ lfn:message('sys-time:sysTimeLeaveRule.fdAutoAmount') }
						</td>
						<td colspan="3">
							<xform:text property="fdAutoAmount" style="text-align: center;width: 100px;" validators="${isAmount && sysTimeLeaveRuleForm.fdAmountType eq '2' ? 'required number min(0.5) max(365) fraction' : ''}" subject="${ lfn:message('sys-time:sysTimeLeaveRule.fdAutoAmount') }" value="${autoAmount }">
							</xform:text>
							${ lfn:message('sys-time:sysTimeLeaveRule.day') }
						</td>
					</tr>
					<%-- 额度计算规则--%> 
					<tr id="amountRule" style="${isAmount && sysTimeLeaveRuleForm.fdAmountType eq '3' ? '' : 'display:none' || isAmount && sysTimeLeaveRuleForm.fdAmountType eq '4' ? '' : 'display:none'}">
						<td class="td_normal_title" style="text-align: center;">
							${ lfn:message('sys-time:sysTimeLeaveRule.fdAmountCalRule') }
						</td>
						<td colspan="3">
							<xform:radio property="fdAmountCalRule" alignment="H" showStatus="edit" value="${amountCalRule }">
								<xform:simpleDataSource value="1">${lfn:message('sys-time:sysTimeLeaveRule.amount.rule.work') }</xform:simpleDataSource>
								<kmss:ifModuleExist path="/hr/staff">
									<xform:simpleDataSource value="2">${lfn:message('sys-time:sysTimeLeaveRule.amount.rule.company') }</xform:simpleDataSource>
								</kmss:ifModuleExist>
							</xform:radio>
						</td>
					</tr>
					<%-- 通用发放规则 --%> 
					<tr id="openRules" style="${isAmount && sysTimeLeaveRuleForm.fdAmountType eq '3' ? '' : 'display:none' || isAmount && sysTimeLeaveRuleForm.fdAmountType eq '4' ? '' : 'display:none'}">
						<td class="td_normal_title" style="text-align: center;">
							${ lfn:message('sys-time:sysTimeLeaveRuleList.fdGrant') }
							<div><a href="javascript:void(0);"  onclick="Com_OpenWindow('ruleOperation_help.jsp');" class="sys_notify_add">${ lfn:message('sys-time:sysTimeLeaveRuleList.fdruleDescription') }</a></div>
						</td>
						<td colspan="3">
							<c:if test="${ sysTimeLeaveRuleForm.method_GET == 'add' }">
							<script type="text/javascript">
								 $(function(){
									$(document).on('detaillist-init',"table[id$='TABLE_DocList']",function(e,row){
									    DocList_TableInfo["TABLE_DocList"].lastIndex=1;
									    DocList_TableInfo["TABLE_DocList"].firstIndex=0;
									  });
								}); 
							</script>
							</c:if>	
							<c:if test="${ sysTimeLeaveRuleForm.method_GET == 'edit' }">
								<script type="text/javascript">
									 $(function(){
										$(document).on('detaillist-init',"table[id$='TABLE_DocList']",function(){
											var listSize = ${fn:length(sysTimeLeaveRuleForm.sysTimeLeaveRuleList)};
											DocList_TableInfo["TABLE_DocList"].lastIndex=listSize;
											DocList_TableInfo["TABLE_DocList"].firstIndex=0;
										  });
									});
								</script>
							</c:if>	
							
							<table id="TABLE_DocList">
								<tr>
									<td id="countent">
										<input type="hidden" name="sysTimeLeaveRuleList[0].fdId" value="${sysTimeLeaveRuleItem.fdId}" />
										<input type="hidden" name="sysTimeLeaveRuleList[0].fdRulesId" value="${sysTimeLeaveRuleItem.fdRulesId}" />
										<span id="fixed">
											<input type="hidden" name="sysTimeLeaveRuleList[0].fdEntryType" value="2" />
											<span>${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay4') }<span id="startEntryId0"><xform:text property="sysTimeLeaveRuleList[0].fdStartEntry" showStatus="edit" style="width:50px;" subject="${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay12') }"/>${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay5') }</span>
											<span id="endEntryId0"><xform:text property="sysTimeLeaveRuleList[0].fdEndEntry" showStatus="edit" style="width:50px;" subject="${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay12') }"/>${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay6') }</span></span>
											<xform:radio property="sysTimeLeaveRuleList[0].fdQuotaType" alignment="H" onValueChange="changeQuotaType(this);"  value="${sysTimeLeaveRuleForm.sysTimeLeaveRuleList[0].fdQuotaType eq null ? 1 : sysTimeLeaveRuleForm.sysTimeLeaveRuleList[0].fdQuotaType}">
												<xform:simpleDataSource value="1" >
													${ lfn:message('sys-time:sysTimeLeaveRuleList.fdQuotaType1') }
												</xform:simpleDataSource>
												<xform:simpleDataSource value="2">
													${ lfn:message('sys-time:sysTimeLeaveRuleList.fdQuotaType2') }
												</xform:simpleDataSource>
											</xform:radio>
											<span zx="current">${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay7') }</span>

											<span zx="laterName"  style="${sysTimeLeaveRuleForm.sysTimeLeaveRuleList[0].fdQuotaType eq '2' ? '' : 'display:none'}">${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay8') }</span>
											<span zx="laterNameVal">
											<xform:text property="sysTimeLeaveRuleList[0].fdHolidayDays" validators="required number min(0.5) max(365) fraction" showStatus="edit" style="width:50px;" subject="${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay13') }"/>${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay1') }
											</span>

											<span zx="later" style="${sysTimeLeaveRuleForm.sysTimeLeaveRuleList[0].fdQuotaType eq '2' ? '' : 'display:none'}">

											${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay9') }
											<xform:text property="sysTimeLeaveRuleList[0].fdIncreaseDays" showStatus="edit" style="width:50px;" subject="${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay13') }"/>${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay1') }
											</span>
										</span>
										<span id="now">
											<input type="hidden" name="sysTimeLeaveRuleList[0].fdEntryType" value="1" />
											<span>${ lfn:message('sys-time:sysTimeLeaveRuleList.fdOneYear') }</span>
											<xform:radio property="sysTimeLeaveRuleList[0].fdQuotaType" alignment="H"  onValueChange="changeEntryType(0);" value="${sysTimeLeaveRuleForm.sysTimeLeaveRuleList[0].fdQuotaType eq null ? 1 : (sysTimeLeaveRuleForm.sysTimeLeaveRuleList[0].fdQuotaType eq '2'?1:sysTimeLeaveRuleForm.sysTimeLeaveRuleList[0].fdQuotaType )}">
												<xform:simpleDataSource value="1">
													${ lfn:message('sys-time:sysTimeLeaveRuleList.fdQuotaType1') }
												</xform:simpleDataSource>
												<xform:simpleDataSource value="3">
													${ lfn:message('sys-time:sysTimeLeaveRuleList.fdQuotaType3') }
												</xform:simpleDataSource>
											</xform:radio>
											<span id="currentEntry" style="position:relative;display: inline-block" ><xform:text property="sysTimeLeaveRuleList[0].fdHolidayDays" validators="required number min(0) max(365) fraction" showStatus="edit" style="width:50px;" subject="${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay10') }"/><span class="zx" style="position: absolute;right: -20px;    top: 0;">${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay1') }</span></span>
											<span id="count" style="${fdEntryType eq '3' ? '' : 'display:none'}">${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay2') }<xform:text property="sysTimeLeaveRuleList[0].fdCountDays" showStatus="edit" style="width:50px;" subject="${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay11') }"/>${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay3') }</span>
										</span>
									</td>
								</tr>
								<tr KMSS_IsReferRow="1">
									<td>
										<input type="hidden" name="sysTimeLeaveRuleList[!{index}].fdId" value="${sysTimeLeaveRuleItem.fdId}" />
										<input type="hidden" name="sysTimeLeaveRuleList[!{index}].fdRulesId" value="${sysTimeLeaveRuleItem.fdRulesId}" />
										<input type="hidden" name="sysTimeLeaveRuleList[!{index}].fdEntryType" value="2" />
										${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay4') }<span id="startEntryId!{index}"><xform:text property="sysTimeLeaveRuleList[!{index}].fdStartEntry"  validators=" required number digits min(1) max(365)" showStatus="edit" style="width:50px;" subject="${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay12') }"/>${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay5') }</span>
										<span id="endEntryId!{index}"><xform:text property="sysTimeLeaveRuleList[!{index}].fdEndEntry" validators=" checkValue required number digits min(1) max(365)" showStatus="edit" style="width:50px;" subject="${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay12') }"/>${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay6') }</span>
										<xform:radio property="sysTimeLeaveRuleList[!{index}].fdQuotaType" alignment="H" onValueChange="changeQuotaType(this);" value="1">
											<xform:simpleDataSource value="1" >
												${ lfn:message('sys-time:sysTimeLeaveRuleList.fdQuotaType1') }
											</xform:simpleDataSource>
											<xform:simpleDataSource value="2">
												${ lfn:message('sys-time:sysTimeLeaveRuleList.fdQuotaType2') }
											</xform:simpleDataSource>
										</xform:radio>

										<span zx="current">${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay7') }</span>

										<span zx="laterName"  style="${fdQuotaType eq '2' ? '' : 'display:none'}">${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay8') }</span>
										<span zx="laterNameVal">
										<xform:text property="sysTimeLeaveRuleList[!{index}].fdHolidayDays" validators="required number min(0.5) max(365) fraction" showStatus="edit" style="width:50px;" subject="${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay13') }"/>${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay1') }
										</span>

										<span zx="later" style="${fdQuotaType eq '2' ? '' : 'display:none'}">

										${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay9') }
										<xform:text property="sysTimeLeaveRuleList[!{index}].fdIncreaseDays" showStatus="edit" style="width:50px;" subject="${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay13') }"/>${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay1') }


										</span>
									</td>
									<td align="center" class="remove_config" KMSS_IsRowIndex="1">
										 <a href="javascript:void(0);"  onclick="DocList_DeleteRow();" class="sys_notify_add">
										 	<img src="${KMSS_Parameter_StylePath}/icons/icon_del.png" border="0" />
										 </a>
									</td>
								</tr>
								<c:forEach items="${sysTimeLeaveRuleForm.sysTimeLeaveRuleList}"  var="sysTimeLeaveRuleItem" varStatus="vstatus" begin="1">
									<tr KMSS_IsContentRow="1">
										<td  style="width: 100%" KMSS_IsRowIndex="1">
											<input type="hidden" name="sysTimeLeaveRuleList[${vstatus.index}].fdId" value="${sysTimeLeaveRuleItem.fdId}" />
										<input type="hidden" name="sysTimeLeaveRuleList[${vstatus.index}].fdRulesId" value="${sysTimeLeaveRuleItem.fdRulesId}" />
										${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay4') }<xform:text property="sysTimeLeaveRuleList[${vstatus.index}].fdStartEntry"  value="${sysTimeLeaveRuleItem.fdStartEntry}" validators=" required number digits min(1) max(365)" style="width:50px;" subject="${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay12') }" showStatus="edit"/>${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay5') }
											<xform:text property="sysTimeLeaveRuleList[${vstatus.index}].fdEndEntry" value="${sysTimeLeaveRuleItem.fdEndEntry}" title="${vstatus.index}" validators=" checkValue required number digits min(1) max(365)" showStatus="edit" style="width:50px;" subject="${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay12') }"/>${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay6') }
										<xform:radio property="sysTimeLeaveRuleList[${vstatus.index}].fdQuotaType" alignment="H" onValueChange="changeQuotaType(this);" value="${sysTimeLeaveRuleItem.fdQuotaType}">
											<xform:simpleDataSource value="1" >
												${ lfn:message('sys-time:sysTimeLeaveRuleList.fdQuotaType1') }
											</xform:simpleDataSource>
											<xform:simpleDataSource value="2">
												${ lfn:message('sys-time:sysTimeLeaveRuleList.fdQuotaType2') }
											</xform:simpleDataSource>
										</xform:radio>

										<span zx="current">
											${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay7') }
										</span>
										<span zx="laterName"  style="${sysTimeLeaveRuleItem.fdQuotaType eq '2' ? '' : 'display:none'}">${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay8') }</span>
										<span zx="laterNameVal">
											<xform:text property="sysTimeLeaveRuleList[${vstatus.index}].fdHolidayDays" validators="required number min(0.5) max(365) fraction" showStatus="edit" style="width:50px;" subject="${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay13') }"/>${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay1') }
										</span>
										<span zx="later" style="${sysTimeLeaveRuleItem.fdQuotaType eq '2' ? '' : 'display:none'}">
										${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay9') }
										<xform:text property="sysTimeLeaveRuleList[${vstatus.index}].fdIncreaseDays" showStatus="edit" style="width:50px;" subject="${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay13') }"/>${ lfn:message('sys-time:sysTimeLeaveRuleList.fdDay1') }

										</span>
										</td>
										<td align="center" class="remove_config" KMSS_IsRowIndex="1">
										 <a href="javascript:void(0);"  onclick="DocList_DeleteRow();" class="sys_notify_add">
										 	<img src="${KMSS_Parameter_StylePath}/icons/icon_del.png" border="0" />
										 </a>
										</td>
									</tr>
								</c:forEach>
							</table>
							
							
							 <a href="javascript:void(0);"  onclick="DocList_AddRow('TABLE_DocList');" class="sys_notify_add">${ lfn:message('sys-time:sysTimeLeaveRuleList.fdAddGrant') }</a>
						</td>
					</tr>
					<%-- 额度结算方式 --%>
					<tr id="amountCalType" style="${isAmount && sysTimeLeaveRuleForm.fdAmountType eq '2' ? '' : 'display:none' || sysTimeLeaveRuleForm.fdAmountType eq '3' ? '' : 'display:none' || sysTimeLeaveRuleForm.fdAmountType eq '4' ? '' : 'display:none'}">
						<td class="td_normal_title" style="text-align: center;">
							${ lfn:message('sys-time:sysTimeLeaveRule.fdAmountCalType') }
						</td>
						<td colspan="3" id="amountCalType">
							<xform:radio property="fdAmountCalType" alignment="V" onValueChange="changeAmountCalType();" value="${amountCalType }">
								<xform:simpleDataSource value="1">
									${ lfn:message('sys-time:sysTimeLeaveRule.fdAmountCalType.once') }
								</xform:simpleDataSource>
								<xform:simpleDataSource value="2">
									${ lfn:message('sys-time:sysTimeLeaveRule.fdAmountCalType.append') }
									<span style="color: #999">${ lfn:message('sys-time:sysTimeLeaveRule.fdAmountCalType.append.tips') }</span>
								</xform:simpleDataSource>
								<xform:simpleDataSource value="3">
									${ lfn:message('sys-time:sysTimeLeaveRule.fdAmountCalType.extend') }
									<xform:text property="fdValidDays" style="text-align: center;width: 100px;" validators="${isAmount && sysTimeLeaveRuleForm.fdAmountType eq '2' && sysTimeLeaveRuleForm.fdAmountCalType eq '3' ? 'required number digits min(1) max(365)' : 'number digits min(1) max(365)'}" subject="${ lfn:message('sys-time:sysTimeLeaveRule.fdValidDays') }">
									</xform:text>
									${ lfn:message('sys-time:sysTimeLeaveRule.day') }
								</xform:simpleDataSource>
							</xform:radio>
						</td>
					</tr>
				</table>
			</div>	
		</html:form>
		<script type="text/javascript">
		var validation = $KMSSValidation(document.forms['sysTimeLeaveRuleForm']);
		seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
			$(document).ready(function () {
				
				var fdAmountType = $('[name="fdAmountType"]').val();
				if (fdAmountType=='2'||fdAmountType=='1') {
					validation.removeElements($("[name='sysTimeLeaveRuleList[0].fdHolidayDays']")[0]);
					$("[name='sysTimeLeaveRuleList[0].fdHolidayDays']").removeAttr('validate');
				}
				storage=window.localStorage;
				var childFixed = $("#fixed").html();
				var childNow = $("#now").html();
				storage.fixed=childFixed;
				storage.now=childNow;

				if(fdAmountType == '3' || fdAmountType == '4'){
					//每年1月1日。入职日期发放
					$('[name^="sysTimeLeaveRuleList"]').each(function(i,item){
					  	validation.addElements($("[name='sysTimeLeaveRuleList["+i+"].fdStartEntry']")[0],'required number digits min(1) max(365)');
					  	validation.addElements($("[name='sysTimeLeaveRuleList["+i+"].fdEndEntry']")[0],' checkValue required number digits min(1) max(365)');
					  	
					  	var fdQuotaType = $("[name='sysTimeLeaveRuleList["+i+"].fdQuotaType']:checked").val();
					  	if(fdQuotaType == '1'){
					  		if (i==0) {
					  			validation.addElements($("[name='sysTimeLeaveRuleList["+i+"].fdHolidayDays']")[0],'required number min(0) max(365) fraction');
							}else{
					  			validation.addElements($("[name='sysTimeLeaveRuleList["+i+"].fdHolidayDays']")[0],'required number min(0.5) max(365) fraction');
							}
					  		validation.removeElements($("[name='sysTimeLeaveRuleList["+i+"].fdIncreaseDays']")[0]);
							$("[name='sysTimeLeaveRuleList["+i+"].fdIncreaseDays']").removeAttr('validate');
					  	}else{
					  		if (i==0) {
					  			validation.addElements($("[name='sysTimeLeaveRuleList["+i+"].fdHolidayDays']")[0],'required number min(0) max(365) fraction');
							}else{
					  			validation.addElements($("[name='sysTimeLeaveRuleList["+i+"].fdHolidayDays']")[0],'required number min(0.5) max(365) fraction');
							}
							validation.addElements($("[name='sysTimeLeaveRuleList["+i+"].fdIncreaseDays']")[0],'required number min(0.5) max(365) fraction');
					  	} 
						$(item).attr('_validate');
						$(item).attr('validate');
				    });
					
					
					if (fdAmountType == '3') {
						//每年1月1日
						$('#now').show();
						$('#fixed').hide();
						$("#countent").html("");
            			$("#countent").html(storage.now);
					}else{
						//入职日期
						$('#fixed').show();
						$('#now').hide();
						$("#countent").html("");
            			$("#countent").html(storage.fixed);
					}
					var fdEntryType = $("[name='sysTimeLeaveRuleList[0].fdQuotaType']:checked").val();
					if(fdEntryType == '1'){
						validation.addElements($('#currentEntry')[0],'required number min(0) max(365) fraction');
						$('#currentEntry').show();
						validation.removeElements($('#count')[0]);
						$('#count').hide();
						$('#count').removeAttr('validate');
					} else if(fdEntryType=='3') {
						$('#currentEntry').hide();
						$('#count').show();
						
						validation.removeElements($("[name='sysTimeLeaveRuleList[0].fdHolidayDays']")[0]);
						$("[name='sysTimeLeaveRuleList[0].fdHolidayDays']").removeAttr('validate');
						validation.addElements($('#count')[0],'required number min(0) max(365) fraction');
					}
				}
				
				if(fdAmountType == '4'){
					validation.addElements($('#startEntryId0'),'required number min(0) max(365) fraction');
					validation.addElements($('#endEntryId0'),'required number min(0) max(365) fraction');
				}else
				{
					$('#startEntryId0').removeAttr('validate');
					$('#endEntryId0').removeAttr('validate');
				}
				
			});
			
			window.nameChange=function(value,obj){
				if(value){
					$(obj).val($.trim(value));
				}
			}
			
			window.changeStatType = function(value, elm) {
				if(value == '3') {
					$('#convertBlock').show();
				} else {
					$('#convertBlock').hide();
				}
			}
			
			window.changeIsAvail = function() {
				var isAvail = $('[name="fdIsAvailable"]').val();
				var isAmount = $('[name="fdIsAmount"]').val();
				if(isAmount == 'true' && isAvail == 'false'){
					dialog.alert("${ lfn:message('sys-time:sysTimeLeaveRule.warn.disable.deleteAmount') }");
				}
			}
			validation.addValidator('fraction', "${ lfn:message('sys-time:sysTimeLeaveRule.atMostHalf') }", function(v, e, o){
				if(v && e){
					return /^[0-9]+(\.[05])?$/g.test(v);
				}
			});
			validation.addValidator('numUnique', "${ lfn:message('sys-time:sysTimeLeaveRule.fdSerialNo.unique') }", function(v,e,o) {
				if(!v){
					return true;
				}
				v = parseInt(v);
				var serialNums = '${serialNums}';
				var curSerialNum = '${sysTimeLeaveRuleForm.fdSerialNo}';
				if(serialNums) {
					var numList = serialNums.split(';');					
					for(var i in numList){
						if(v == numList[i] && v != curSerialNum){
							return false;
						}
					}
				}
				return true;
			});
			validation.addValidator('nameUnique', "${ lfn:message('sys-time:sysTimeLeaveRule.fdName.unique') }", function(v,e,o) {
				if(!v){
					return true;
				}
				var serialNums = '${leaveNames}';
				var curSerialNum = '${sysTimeLeaveRuleForm.fdName}';
				if(serialNums) {
					var numList = serialNums.split(';');					
					for(var i in numList){
						if(v == numList[i] && v != curSerialNum){
							return false;
						}
					}
				}
				return true;
			});
			
			validation.addValidator('checkValue', "开始时间不能大于结束时间", function(v,e,o) {
				if(!v){
					return true;
				}
				var name=e.name;
				var regex = /\[(.+?)\]/g;
				var  options = name.match(regex)
			    var option = options[0]
			    var result = option.substring(1, option.length - 1)
				var value=$("[name='sysTimeLeaveRuleList["+result+"].fdStartEntry']").val();
				var valueEnd=$("[name='sysTimeLeaveRuleList["+result+"].fdEndEntry']").val();
				if (Number(value)>Number(valueEnd)) {
					return false;
				}
				return true;
			});
		
		
			window.changeIsAmount = function() {
				var oldValue = '${isAmount}';
				if($('[name="fdIsAmount"]').val() == 'true'){
					$('#amountType').show();
					changeAmountType();
					$('#overtimeToLeave_tr').show();
					//changeAddType();
					//通用发放规则校验
				} else {
					if(oldValue == 'true'){
						dialog.alert("${ lfn:message('sys-time:sysTimeLeaveRule.warn.turnoff.deleteAmount') }");
					}
					$('#amountType').hide();
					$('#autoAmount').hide();
					$('#amountCalType').hide();
					$('#overtimeToLeave_tr').hide();
					$('#openRules').hide();
					validation.removeElements($('[name="fdAutoAmount"]')[0]);
					validation.removeElements($('[name="fdValidDays"]')[0]);
					//通用发放规则校验
					validation.removeElements($('#currentEntry')[0]);
					$('#currentEntry').removeAttr('validate');
					validation.removeElements($('#count')[0]);
					$('#count').removeAttr('validate');
					changeRemoveType();
				}
			};
			
			
			 window.changeAddType = function() {
			  $('[name^="sysTimeLeaveRuleList"]').each(function(i,item){
				  	validation.addElements($("[name='sysTimeLeaveRuleList["+i+"].fdStartEntry']")[0],'required number digits min(1) max(365)');
				  	validation.addElements($("[name='sysTimeLeaveRuleList["+i+"].fdEndEntry']")[0],' checkValue required number digits min(1) max(365)');
				  	
				  	var fdQuotaType = $("[name='sysTimeLeaveRuleList["+i+"].fdQuotaType']:checked").val();
				  	//alert(fdQuotaType);
				  	if(fdQuotaType == '1'){
				  		if (i==0) {
				  			validation.addElements($("[name='sysTimeLeaveRuleList["+i+"].fdHolidayDays']")[0],'required number min(0) max(365) fraction');
						}else{
				  			validation.addElements($("[name='sysTimeLeaveRuleList["+i+"].fdHolidayDays']")[0],'required number min(0.5) max(365) fraction');
						}
				  		validation.removeElements($("[name='sysTimeLeaveRuleList["+i+"].fdIncreaseDays']")[0]);
						$("[name='sysTimeLeaveRuleList["+i+"].fdIncreaseDays']").removeAttr('validate');
				  	}else{
				  		if (i==0) {
				  			validation.addElements($("[name='sysTimeLeaveRuleList["+i+"].fdHolidayDays']")[0],'required number min(0) max(365) fraction');
						}else{
				  			validation.addElements($("[name='sysTimeLeaveRuleList["+i+"].fdHolidayDays']")[0],'required number min(0.5) max(365) fraction');
						}
						validation.addElements($("[name='sysTimeLeaveRuleList["+i+"].fdIncreaseDays']")[0],'required number min(0.5) max(365) fraction');
				  	} 
				  	//changeQuotaType(i);
				  	
					/* validation.removeElements($("[name^='sysTimeLeaveRuleLis["+i+"]']")[0],'required');
					validation.removeElements($("[name^='sysTimeLeaveRuleLis["+i+"]']")[0]); */
					$(item).attr('_validate');
					$(item).attr('validate');
			    }); 
			} 
				
			window.changeRemoveType = function() {
			$('[name^="sysTimeLeaveRuleList"]').each(function(i,item){
					validation.removeElements($("[name='sysTimeLeaveRuleLis["+i+"].fdStartEntry']")[0]);
					validation.removeElements($("[name='sysTimeLeaveRuleLis["+i+"].fdEndEntry']")[0]);
					validation.removeElements($("[name='sysTimeLeaveRuleLis["+i+"].fdHolidayDays']")[0]);
					validation.removeElements($("[name='sysTimeLeaveRuleLis["+i+"].fdIncreaseDays']")[0]);
					$("[name='sysTimeLeaveRuleLis["+i+"].fdStartEntry']").removeAttr('validate');
					$("[name='sysTimeLeaveRuleLis["+i+"].fdEndEntry']").removeAttr('validate');
					$("[name='sysTimeLeaveRuleLis["+i+"].fdHolidayDays']").removeAttr('validate');
					$("[name='sysTimeLeaveRuleLis["+i+"].fdIncreaseDays']").removeAttr('validate');
					$(item).removeAttr('_validate',"required");
					$(item).removeAttr('validate',"required");
			    });   
			}
			window.changeAmountType = function() {
				var fdAmountType = $('[name="fdAmountType"]').val();
				if(fdAmountType == '2') {
					$('#autoAmount').show();
					$('#amountCalType').show();
					$('#openRules').hide();
					$('#amountRule').hide();
					validation.addElements($('[name="fdAutoAmount"]')[0], 'required number min(0.5) max(365) fraction');
					//通用发放规则校验
					validation.removeElements($('#currentEntry')[0]);
					validation.removeElements($('#count')[0]);
					$('#currentEntry').removeAttr('validate');
					$('#count').removeAttr('validate');
					$('#startEntryId0').removeAttr('validate');
					$('#endEntryId0').removeAttr('validate');
					changeAmountCalType();
					changeRemoveType();
				} else if (fdAmountType == '1') {
					$('#autoAmount').hide();
					$('#amountCalType').hide();
					$('#amountRule').hide();
					$('#openRules').hide();
					validation.removeElements($('[name="fdAutoAmount"]')[0]);
					validation.removeElements($('[name="fdValidDays"]')[0]);
					//通用发放规则校验
					validation.removeElements($('#currentEntry')[0]);
					validation.removeElements($('#count')[0]);
					$('#currentEntry').removeAttr('validate');
					$('#count').removeAttr('validate');
					$('#startEntryId0').removeAttr('validate');
					$('#endEntryId0').removeAttr('validate');
					changeRemoveType();
				}else{
					
					if (fdAmountType == '3') {
						$('#openRules').show();
						$('#now').show();
						$('#fixed').hide();
						$('#openRules').show();
						$('#autoAmount').hide();
						$('#amountCalType').show();
						$('#amountRule').show();
						$("#countent").html("");
            			$("#countent").html(storage.now);
            			$('#startEntryId0').removeAttr('validate');
    					$('#endEntryId0').removeAttr('validate');
						
						changeEntryType();
						changeAddType();
						changeAmountCalType();
					}
					else if (fdAmountType == '4') {
						$('#now').hide();
						$('#fixed').show();
						$('#openRules').show();
						$('#autoAmount').hide();
						$('#amountCalType').show();
						$('#amountRule').show();
						$("#countent").html("");
	            		$("#countent").html(storage.fixed);
	            		validation.addElements($('#startEntryId0'),'required number min(0) max(365) fraction');
						validation.addElements($('#endEntryId0'),'required number min(0) max(365) fraction');
						//通用发放规则校验
						changeEntryType();
						changeAddType();
						changeAmountCalType();
					}
					
				}
			};
			
			window.changeEntryType = function(data) {
				var fdEntryType = $("[name='sysTimeLeaveRuleList[0].fdQuotaType']:checked").val();
				if(fdEntryType == '1'){
					validation.addElements($('#currentEntry')[0],'required number min(0) max(365) fraction');
					$('#currentEntry').show();
					validation.removeElements($('#count')[0]);
					$('#count').hide();
					$('#count').removeAttr('validate');
				} else if(fdEntryType=='3') {
					$('#currentEntry').hide();
					$('#count').show();
					validation.removeElements($('#currentEntry')[0]);
					$('#currentEntry').removeAttr('validate');
					validation.addElements($('#count')[0],'required number min(0) max(365) fraction');
				}
			};
			
			window.changeQuotaType = function(data) {
				
				var flag = ${param.method=='edit'}
				if (flag) {
					var fdQuotaType = $("[name='sysTimeLeaveRuleList["+data+"].fdQuotaType']:checked").val();
					if (fdQuotaType==null||fdQuotaType=="") {
						var fdQuotaType = $("[name='"+data.name+"']:checked").val();
					}
				}else{
					var fdQuotaType = $("[name='"+data.name+"']:checked").val();
				}
				 var tr= GetDomOwnerDomTag("td");
				
				if(fdQuotaType == '1'){
					$(tr).find("[zx*='current']").show();
					$(tr).find("[zx*='later']").hide();
					$(tr).find("[zx*='laterName']").hide();
					$(tr).find("[zx*='laterNameVal']").show();
					validation.addElements($(tr).find("[name*='fdHolidayDays']")[0],'required number min(0.5) max(365) fraction');
					validation.removeElements($(tr).find("[name*='fdIncreaseDays']")[0]);
					$(tr).find("[name*='fdIncreaseDays']").removeAttr('validate');
				} else {
					$(tr).find("[zx*='current']").hide();
					$(tr).find("[zx*='later']").show();
					$(tr).find("[zx*='laterName']").show();
					validation.addElements($(tr).find("[name*='fdIncreaseDays']")[0],'required number min(0.5) max(365) fraction');
				}
			};
			window.changeAmountCalType = function() {
				var fdAmountCalType = $('[name="fdAmountCalType"]:checked').val();
				if(fdAmountCalType == '3'){
					$('[name="fdValidDays"]').attr("validate","required number digits min(1) max(365)");
					//validation.addElements($('[name="fdValidDays"]')[0], 'required number digits min(1) max(365)');
				} else {
					$('[name="fdValidDays"]').attr("validate","number digits min(1) max(365)");
					//validation.removeElements($('[name="fdValidDays"]')[0]);
				}
			};
			// 保存
			window.saveDoc = function() {
				if($('[name="fdIsAvailable"]').val() == 'true' && $('[name="fdIsAmount"]').val() == 'true' 
						&& $('[name="fdAmountType"]').val() == 2 && $('[name="fdAutoAmount"]').val() != null){
					window.saveConfirmSysTimeRule( 'save');
				} else {
					Com_Submit(document.sysTimeLeaveRuleForm, 'save');
				}
			};

			window.saveConfirmSysTimeRule =function(type){
				if(validation.validate()) {
					//立即生效、明日生效
					dialog.confirm('是否生成今年的额度数据？', function (flag, d) {

					}, null, [{
						name: '是',
						value: true,
						focus: true,
						fn: function (value, dialog) {
							dialog.hide(value);
							$('input[name="isUpdateAmount"]').val('1');
							Com_Submit(document.sysTimeLeaveRuleForm, type);
						}
					}, {
						name: '否',
						value: false,
						styleClass: 'lui_toolbar_btn_gray',
						fn: function (value, dialog) {
							dialog.hide(value);
							$('input[name="isUpdateAmount"]').val('0');
							Com_Submit(document.sysTimeLeaveRuleForm, type);
						}
					}]);
				}
			}

			// 保存并新建
			window.saveAddDoc = function() {
				if($('[name="fdIsAvailable"]').val() == 'true' && $('[name="fdIsAmount"]').val() == 'true' 
					&& $('[name="fdAmountType"]').val() == 2 && $('[name="fdAutoAmount"]').val() != null){
					var fdAmountType = $('[name="fdAmountType"]').val();
					if (fdAmountType=='3'||fdAmountType=='4') {
						$('input[name="isUpdateAmount"]').val('0');
						Com_Submit(document.sysTimeLeaveRuleForm, 'update');
					}else{
						window.saveConfirmSysTimeRule('saveadd');
					}
				} else {
					Com_Submit(document.sysTimeLeaveRuleForm, 'saveadd');
				}
			};
			// 更新
			window.updateDoc = function() {
				if($('[name="fdIsAvailable"]').val() == 'true' && $('[name="fdIsAmount"]').val() == 'true'){
					var fdAmountType = $('[name="fdAmountType"]').val();
					if (fdAmountType=='3'||fdAmountType=='4') {
						$('input[name="isUpdateAmount"]').val('0');
						Com_Submit(document.sysTimeLeaveRuleForm, 'update');
					}else{
						window.saveConfirmSysTimeRule( 'update');
					}
				}  else {
					Com_Submit(document.sysTimeLeaveRuleForm, 'update');
				} 
			};
		});
		</script>
	</template:replace>
</template:include> 