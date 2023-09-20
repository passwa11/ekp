<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.notify.model.SysNotifyConfig"%>

<%
	SysNotifyConfig notifyConfig = new SysNotifyConfig();
	String threadCount = notifyConfig.getNotifyQueueThreadPoolSize();
	String extendThreadCount = notifyConfig.getNotifyExtendQueueThreadPoolSize();
%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message bundle="sys-notify" key="sysNotifyTodo.param.config" /></template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title"><bean:message bundle="sys-notify" key="sysNotifyTodo.param.config" /></span>
		</h2>
		<html:form action="/sys/notify/sys_notify_todo/sysNotifyConfig.do">
			<center>
				<table  class="tb_normal" width=95%>
					<tr>
						<td class="td_normal_title" width="20%">
							<bean:message bundle="sys-notify" key="sysNotifyTodo.clear.config" />
						</td>
						<td>
						<table class="tb_noborder" width=100%>
							<tr>
								<td width="50%">
									<bean:message bundle="sys-notify" key="sysNotifyTodo.clear" />
									<xform:text property="value(fdClDayToView)" validators="digits min(0)"/>
									<bean:message bundle="sys-notify" key="sysNotifyTodo.clear.fdClDayToView" />
								</td>
								<td>
									<bean:message bundle="sys-notify" key="sysNotifyTodo.clear" />
									<xform:text property="value(fdClDayTodo)" validators="digits min(0)"/>
									<bean:message bundle="sys-notify" key="sysNotifyTodo.clear.fdClDayTodo" />
								</td>			
							</tr> 
							<tr>
								<td>
									<bean:message bundle="sys-notify" key="sysNotifyTodo.clear" />
									<xform:text property="value(fdClDayViewed)" validators="digits min(0)"/>
									<bean:message bundle="sys-notify" key="sysNotifyTodo.clear.fdClDayViewed" />
								</td> 
								<td>
									<bean:message bundle="sys-notify" key="sysNotifyTodo.clear" />
									<xform:text property="value(fdClDayDone)" validators="digits min(0)"/>
									<bean:message bundle="sys-notify" key="sysNotifyTodo.clear.fdClDayDone" />
								</td>
							</tr>
							<tr>
								<td>
									<bean:message bundle="sys-notify" key="sysNotifyTodo.clear" />
									<xform:text property="value(fdClDaySystem)" validators="digits min(0)"/>
									<bean:message bundle="sys-notify" key="sysNotifyTodo.clear.fdClDaySystem" />
								</td> 
								<td>
									<bean:message bundle="sys-notify" key="sysNotifyTodo.clear" />
									<xform:text property="value(fdClDaySystemDone)" validators="digits min(0)"/>
									<bean:message bundle="sys-notify" key="sysNotifyTodo.clear.fdClDaySystemDone" />
								</td>
							</tr>							
							<tr>
								<td colspan="2">
									<bean:message bundle="sys-notify" key="sysNotifyTodo.clear" />
									<xform:text property="value(fdClDayPerson)" validators="digits min(0)"/>
									<bean:message bundle="sys-notify" key="sysNotifyTodo.clear.fdClDayPerson" />
								</td>
							</tr>
							<tr>
								<td>
									<bean:message bundle="sys-notify" key="sysNotifyTodo.clear" />
									<xform:text property="value(fdClDayWebService)" validators="digits min(30)"/>
									<bean:message bundle="sys-notify" key="sysNotifyTodo.clear.fdClDayWebService" />
								</td>
								<td>
									<bean:message bundle="sys-notify" key="sysNotifyTodo.clear" />
									<xform:text property="value(fdClDayQueueError)" validators="digits min(30)"/>
									<bean:message bundle="sys-notify" key="sysNotifyTodo.clear.fdClDayQueueError" />
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<span class="txtstrong"><bean:message bundle="sys-notify" key="sysNotifyTodo.clear.note" /></span>
								</td>
							</tr> 
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">
						<bean:message bundle="sys-notify" key="sysNotifyTodo.display.config" />
					</td>
					<td>
						<table class="tb_noborder" width=100%>
							<tr>
								<td width="50%">
									<xform:checkbox  property="value(fdDisplayAppName)" showStatus="edit">
										<xform:simpleDataSource value="1">
											<bean:message bundle="sys-notify" key="sysNotifyTodo.display.config.notify" />
										</xform:simpleDataSource>
									</xform:checkbox>
								</td>
								<td>
									<xform:checkbox  property="value(fdDisplayAppNameHome)" showStatus="edit">
										<xform:simpleDataSource value="1">
											<bean:message bundle="sys-notify" key="sysNotifyTodo.display.config.home" />
										</xform:simpleDataSource>
									</xform:checkbox>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<td class="td_normal_title" width="20%">
						<bean:message bundle="sys-notify" key="sysNotifyTodo.cate.data.config" />
					</td>
					<td>
						<table class="tb_noborder" width=100%>
							<tr>
								<td>
									<xform:checkbox  property="value(fdConfigCateInfo)" showStatus="edit">
										<xform:simpleDataSource value="1">
											<bean:message bundle="sys-notify" key="sysNotifyTodo.cate.data.config.discr" />
										</xform:simpleDataSource>
									</xform:checkbox>
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<!--add by wubing date:2014-10-09-->
				<tr>
					<td class="td_normal_title" width="20%">
						<bean:message bundle="sys-notify" key="sysNotifyTodo.notify.error.notifyCrashTargets" />
					</td>
					<td>
						<xform:address propertyId="value(notifyCrashTargetIds)" propertyName="value(notifyCrashTargetNames)" mulSelect="true" orgType="ORG_TYPE_PERSON | ORG_TYPE_POST" style="width:85%">
						</xform:address>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">
						<bean:message bundle="sys-notify" key="notifyQueue.threadPool.size" />
					</td>
					<td>
						<xform:text property="value(notifyQueueThreadPoolSize)" validators="required digits min(1)" showStatus="edit" />
						<br><bean:message bundle="sys-notify" key="sysNotifyTodo.config.threadPoolSize.note" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">
						<bean:message bundle="sys-notify" key="sysNotifyTodo.config.extendThreadPoolSize" />
					</td>
					<td>
						<xform:text property="value(notifyExtendQueueThreadPoolSize)" validators="required digits min(1)" showStatus="edit" />
						<br><bean:message bundle="sys-notify" key="sysNotifyTodo.config.extendThreadPoolSize.note" />
					</td>
				</tr>
				
				<% if(com.landray.kmss.sys.cache.redis.RedisConfig.ENABLED){ %>
					<!-- 如果开启了Redis配置，这里增加刷新时间 -->
					<tr>
					<td class="td_normal_title" width="20%">
						<bean:message bundle="sys-notify" key="sysNotifyTodo.config.refreshTime" />
					</td>
					<td>
						<xform:text property="value(notifyAutoRefreshTime)" validators="digits min(0)" showStatus="edit" />
						<br><bean:message bundle="sys-notify" key="sysNotifyTodo.config.refreshTime.desc" />
					</td>
				</tr>
				<%} %>
				<tr>
					<td>
						<bean:message bundle="sys-notify" key="sysNotifyTodo.config.desktop.push.text"></bean:message>
					</td>
					<td>
						<ui:switch property="value(isDesktopPushingEnalbed)" enabledText="${lfn:message('sys-notify:sysNotifyTodo.config.desktop.push.enable')}" disabledText="${lfn:message('sys-notify:sysNotifyTodo.config.desktop.push.disable')}"></ui:switch>
					</td>
				</tr>
			</table>
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.sys.notify.model.SysNotifyConfig" />
			
			<center style="margin-top: 10px;">
			<!-- 保存 -->
			<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');"></ui:button>
			</center>
			
			<script type="text/javascript">
		 	$KMSSValidation();
		 	function _onLoad(){
				var threadCount ="<%=threadCount%>";
				var extendThreadCount ="<%=extendThreadCount%>";
				var dom = document.getElementsByName('value(notifyQueueThreadPoolSize)')[0];
				var extendDom = document.getElementsByName('value(notifyExtendQueueThreadPoolSize)')[0];
				if(!dom.value){
					dom.value=threadCount;
				}
				if(!extendDom.value){
					extendDom.value=extendThreadCount;
				}
			}
		 	Com_AddEventListener(window, 'load', _onLoad);
			</script>
		</html:form>
	</template:replace>
</template:include>
