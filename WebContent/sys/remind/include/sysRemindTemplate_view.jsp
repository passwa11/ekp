<%@page import="com.landray.kmss.sys.remind.forms.SysRemindMainForm"%>
<%@page import="com.landray.kmss.sys.remind.forms.SysRemindTemplateForm"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<c:set var="formName" value="${param.formName}" />
<c:set var="fdKey" value="${param.fdKey}" />
<c:set var="templateModelName" value="${param.templateModelName}" />
<c:set var="modelName" value="${param.modelName}" />
<c:set var="templateProperty" value="${param.templateProperty}" />
<c:set var="moduleUrl" value="${param.moduleUrl}" />

<c:set var="templateForm" value="${requestScope[param.formName]}" />
<c:set var="sysRemind" value="${templateForm.sysRemind}" />

<%
	// 多属性中获取提醒中心配置
	Object _sysRemind = pageContext.getAttribute("sysRemind");
	if(_sysRemind instanceof Map) {
		// 获取提醒中心模板配置
		Object _sysRemindTemplate = ((Map)_sysRemind).get("sysRemindTemplate");
		if(_sysRemindTemplate instanceof SysRemindTemplateForm) {
			// 获取提醒项配置
			List<SysRemindMainForm> fdMains = ((SysRemindTemplateForm)_sysRemindTemplate).getFdMains();
			pageContext.setAttribute("__fdMains__", fdMains);
		}
	}
%>

<script type="text/javascript">
	window.sysRemind_MainList = [];
</script>
<style>
	.btn_txt{
		margin: 0px 2px;
	    color: #2574ad;
	    border-bottom: 1px solid transparent;
	}
</style>
<tr LKS_LabelName="${lfn:message('sys-remind:module.sys.remind')}" LKS_LabelEnable="${JsParam.enable eq 'false' ? 'false' : 'true'}">
	<td>
 		<table id="sysRemind_Table" class="tb_normal" style="width: 95%;text-align: center;">
			<tr>
				<td class="td_normal_title" width=5%>
					${lfn:message('page.serial')}
				</td>
				<td class="td_normal_title" width=30%>
					${lfn:message('sys-remind:sysRemindMain.fdName')}
				</td>
				<td class="td_normal_title" width=15%>
					${lfn:message('sys-remind:sysRemindMain.fdNotifyType')}
				</td>
				<td class="td_normal_title" width=20%>
					${lfn:message('sys-remind:sysRemindMain.fdReceivers')}
				</td>
				<td class="td_normal_title" width=10%>
					${lfn:message('sys-remind:sysRemindMain.fdIsEnable')}
				</td>
				<td class="td_normal_title" width=20%>
					${lfn:message('list.operation')}
				</td>
			</tr>
			<c:if test="${__fdMains__ != null}">
			<c:forEach var="remind" items="${__fdMains__}" varStatus="status">
				<tr id="${remind.fdId}">
					<td name="rowNum">${status.index + 1}</td>
					<td>${remind.fdName}</td>
					<td><kmss:showNotifyType value="${remind.fdNotifyType}"/></td>
					<td>
						<c:forEach var="receiver" items="${remind.fdReceivers}" varStatus="status1">
							<c:if test="${status1.index > 0}">;</c:if>
							<c:choose>
								<c:when test="${'xform' eq receiver.fdType}">
									${receiver.fdReceiverName}
								</c:when>
								<c:when test="${'address' eq receiver.fdType}">
									${receiver.fdReceiverOrgNames}
								</c:when>
							</c:choose>
						</c:forEach>
					</td>
					<td><sunbor:enumsShow value="${remind.fdIsEnable}" enumsType="sys_remind_main_enable" /></td>
					<td>
						<a class="btn_txt" href="javascript:showTask('${remind.fdId}');">${lfn:message('sys-remind:sysRemindMain.showTask')}</a>
						<a class="btn_txt" href="javascript:showLog('${remind.fdId}');">${lfn:message('sys-remind:sysRemindMain.showLog')}</a>
<%--						<a class="btn_txt" href="javascript:showRemind('${remind.fdId}');">${lfn:message('button.view')}</a>--%>
						<script type="text/javascript">
							$.post('<c:url value="/sys/remind/sys_remind_main/sysRemindMain.do?method=getById&fdId=${remind.fdId}&cloneId=${remind.cloneId}" />', function(res) {
								sysRemind_MainList.push(res);
							}, "json");
						</script>
					</td>
				</tr>
			</c:forEach>
			</c:if>
		</table>

		<script type="text/javascript">
			seajs.use(['lui/dialog'], function(dialog) {
				// 查看任务
				window.showTask = function(fdId) {
					dialog.iframe("/sys/remind/sys_remind_main_task/index.jsp?remindId=" + fdId, "${lfn:message('sys-remind:table.sysRemindMainTask')}", null,
							{width:1200, height:500, topWin:window, close:true});
				}

				// 查看日志
				window.showLog = function(fdId) {
					dialog.iframe("/sys/remind/sys_remind_main_task_log/index.jsp?remindId=" + fdId, "${lfn:message('sys-remind:table.sysRemindMainTaskLog')}", null,
							{width:1200, height:500, topWin:window, close:true});
				}

				// 查看提醒设置
				window.showRemind = function(fdId) {
					dialog.iframe("${ KMSS_Parameter_ContextPath }/sys/remind/sys_remind_main/sysRemindMain.do?method=view&fdId=" + fdId, "${lfn:message('button.view')}", null,
							{width:1200, height:500, topWin:window, close:true});
				}
			});
		</script>
	</td>
</tr>
