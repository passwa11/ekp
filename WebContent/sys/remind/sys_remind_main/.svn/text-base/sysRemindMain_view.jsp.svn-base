<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.dialog">
	<template:replace name="head">
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
		</style>
	</template:replace>
	<template:replace name="content">
		<div class="sys_remind">
			<div>
				${lfn:message('sys-remind:sysRemindMain.describe')}
			</div>
			<table class="tb_normal" width="100%" style="margin-top: 10px;">
				<tr>
					<td width=15% class="td_normal_title">
						${lfn:message('sys-remind:sysRemindMain.fdName')}
					</td>
					<td width=85% colspan="3">
						${sysRemindMainForm.fdName}
					</td>
				</tr>
				<tr>
					<td width=15% class="td_normal_title">
						${lfn:message('sys-remind:sysRemindMain.fdIsEnable')}
					</td>
					<td width=85% colspan="3">
						<ui:switch property="fdIsEnable" checked="${sysRemindMainForm.fdIsEnable}" showType="show" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"/>
					</td>
				</tr>
				<tr>
					<td width=15% class="td_normal_title">
						${lfn:message('sys-remind:sysRemindMain.fdIsFilter')}
					</td>
					<td width=85% colspan="3">
						<ui:switch property="fdIsFilter" checked="${sysRemindMainForm.fdIsFilter}" showType="show" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"/>
						<c:choose>
							<c:when test="${'true' eq sysRemindMainForm.fdIsFilter}">${lfn:message('sys-remind:sysRemindMain.fdIsFilter.desc.true')}</c:when>
							<c:otherwise>${lfn:message('sys-remind:sysRemindMain.fdIsFilter.desc.false')}</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<c:if test="${'true' eq sysRemindMainForm.fdIsFilter}">
				<tr>
					<td width=15% class="td_normal_title">
						${lfn:message('sys-remind:sysRemindMain.fdCondition')}
					</td>
					<td width=85% colspan="3">
						${sysRemindMainForm.fdConditionName}
					</td>
				</tr>
				</c:if>
				<tr>
					<td width=15% class="td_normal_title">
						${lfn:message('sys-remind:sysRemindMain.fdTriggers')}
					</td>
					<td width=85% colspan="3">
						<table width=100% class="sys_remind_table">
							<c:forEach var="trigger" items="${sysRemindMainForm.fdTriggers}">
							<tr>
								<td>
									<select class="inputsgl" name="fdField" disabled="disabled">
										<option value="${trigger.fdFieldId}" selected="selected">${trigger.fdFieldName}</option>
									</select>
									<select class="inputsgl" name="fdMode" disabled="disabled" style="margin-left: 10px;">
										<option value="${trigger.fdMode}" selected="selected">
										<c:if test="${'after' eq trigger.fdMode}">${lfn:message('sys-remind:sysRemindMainTrigger.mode.after')}</c:if>
										<c:if test="${'before' eq trigger.fdMode}">${lfn:message('sys-remind:sysRemindMainTrigger.mode.before')}</c:if>
										<c:if test="${'day' eq trigger.fdMode}">${lfn:message('sys-remind:sysRemindMainTrigger.mode.sameDay')}</c:if>
										<c:if test="${'time' eq trigger.fdMode}">${lfn:message('sys-remind:sysRemindMainTrigger.mode.sameTime')}</c:if>
										</option>
									</select>
									<c:if test="${not empty trigger.fdDay}">
										<span style="margin-left: 10px;">${trigger.fdDay}</span>
										${lfn:message('sys-remind:sysRemindMainTrigger.fdDay')}
									</c:if>
									<c:if test="${not empty trigger.fdHour}">
										<span style="margin-left: 10px;">${trigger.fdHour}</span>
										${lfn:message('sys-remind:sysRemindMainTrigger.fdHour')}
										<span style="margin-left: 10px;">${trigger.fdMinute}</span>
										${lfn:message('sys-remind:sysRemindMainTrigger.fdMinute')}
									</c:if>
									<c:if test="${not empty trigger.fdTime}">
										<span style="margin-left: 10px;">${trigger.fdTime}</span>
									</c:if>
								</td>
							</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
				<tr>
					<td width=15% class="td_normal_title">
						${lfn:message('sys-remind:sysRemindMain.fdNotifyType')}
					</td>
					<td width=85% colspan="3">
						<kmss:showNotifyType value="${sysRemindMainForm.fdNotifyType}"/>
					</td>
				</tr>
				<tr>
					<td width=15% class="td_normal_title">
						${lfn:message('sys-remind:sysRemindMain.fdSender')}
					</td>
					<td width=85% colspan="3">
						${sysRemindMainForm.fdSenderName}
					</td>
				</tr>
				<tr>
					<td width=15% class="td_normal_title">
						${lfn:message('sys-remind:sysRemindMain.fdContent')}
					</td>
					<td width=85% colspan="3">
						${sysRemindMainForm.fdSubjectName}
					</td>
				</tr>
				<tr>
					<td width=15% class="td_normal_title">
						${lfn:message('sys-remind:sysRemindMain.fdReceivers')}
					</td>
					<td width=85% colspan="3">
						<table width=100% class="sys_remind_table" id="TABLE_DocList_Receiver">
							<c:forEach var="receiver" items="${sysRemindMainForm.fdReceivers}">
							<tr>
								<td>
								<c:choose>
									<c:when test="${'xform' eq receiver.fdType}">${receiver.fdReceiverName}</c:when>
									<c:otherwise>${receiver.fdReceiverOrgNames}</c:otherwise>
								</c:choose>
								</td>
							</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</template:replace>
</template:include>
