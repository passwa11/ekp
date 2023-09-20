<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextGroupTag"%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title"><bean:message bundle="sys-lbpmservice" key="module.node.paramsSetup.functionSwitch"/></span>
		</h2>
		
		<html:form action="/sys/lbpmservice/support/lbpmConfigAction.do">
		<center>
		<div style="margin:auto auto 60px;">
		<table class="tb_normal" width=95%>
			<!-- 审批记录相关设置项 -->
			<tr>
				<td class="td_normal_title" width="15%" align="center">
					<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.auditNote"/>
				</td>
				<td width="85%">
					<!-- 审批记录隐藏岗位 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHiddenPostInNoteConfigurable"/>
							</td>
							<td width="68%">
								<ui:switch property="value(isHiddenPostInNoteConfigurable)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHiddenPostInNoteConfigurable.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isHiddenPostInNoteConfigurable');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					
					<!-- 隐藏节点超时跳过信息 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHiddenDayOfPassInfoConfigurable"/>
							</td>
							<td width="68%">
								<ui:switch property="value(isHiddenDayOfPassInfoConfigurable)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHiddenDayOfPassInfoConfigurable.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isHiddenDayOfPassInfoConfigurable',true);"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
								
							</td>
						</tr>
					</table>
					
					<!-- 隐藏身份重复跳过信息 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHiddenIdentityRepeatOfPassInfo"/>
							</td>
							<td width="68%">
								<ui:switch property="value(isHiddenIdentityRepeatOfPassInfo)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHiddenIdentityRepeatOfPassInfo.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isHiddenIdentityRepeatOfPassInfo',true);"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
								
							</td>
						</tr>
					</table>
					
					<!-- 显示抄送节点信息 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showSendNodeInfo"/>
							</td>
							<td width="68%">
								<ui:switch property="value(showSendNodeInfo)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showSendNodeInfo.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('showSendNodeInfo');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
				
					<!--#40544 修改审批意见功能-->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isModifyAuditNote"/>
							</td>
							<td width="68%">
								<ui:switch property="value(isModifyAuditNote)" onValueChange="affectAuditNoteModify(this.checked)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<div id="auditNoteModifier" style="display:none;">
										<!-- 创建人修改 -->
										<c:if test="${sysAppConfigForm.map.isModifyAuditNote != null}">
											<xform:checkbox property="value(isCreatorModify)" showStatus="edit">
												<xform:simpleDataSource value="true">
													<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.creatorModify"/>
												</xform:simpleDataSource>
											</xform:checkbox>
										</c:if>
										<c:if test="${sysAppConfigForm.map.isModifyAuditNote == null}">
											<xform:checkbox property="value(isCreatorModify)" showStatus="edit" value="true">
												<xform:simpleDataSource value="true">
													<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.creatorModify"/>
												</xform:simpleDataSource>
											</xform:checkbox>
										</c:if>
										<!-- 特权人修改 -->
										<c:if test="${sysAppConfigForm.map.isModifyAuditNote != null}">
											<xform:checkbox property="value(isPrivilegerModify)" showStatus="edit">
												<xform:simpleDataSource value="true">
													<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.privilegerModify"/>
												</xform:simpleDataSource>
											</xform:checkbox>
										</c:if>
										<c:if test="${sysAppConfigForm.map.isModifyAuditNote == null}">
											<xform:checkbox property="value(isPrivilegerModify)" showStatus="edit" value="false">
												<xform:simpleDataSource value="true">
													<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.privilegerModify"/>
												</xform:simpleDataSource>
											</xform:checkbox>
										</c:if>
										<!-- 流程结束后是否可修改 -->
										<c:if test="${sysAppConfigForm.map.isCanModifyAfterEnd != null}">
											<xform:checkbox property="value(isCanModifyAfterEnd)" showStatus="edit">
												<xform:simpleDataSource value="true">
													<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isCanModifyAfterEnd"/>
												</xform:simpleDataSource>
											</xform:checkbox>
										</c:if>
										<c:if test="${sysAppConfigForm.map.isCanModifyAfterEnd == null}">
											<xform:checkbox property="value(isCanModifyAfterEnd)" showStatus="edit" value="true">
												<xform:simpleDataSource value="true">
													<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isCanModifyAfterEnd"/>
												</xform:simpleDataSource>
											</xform:checkbox>
										</c:if>
								</div>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isModifyAuditNote.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isModifyAuditNote');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					<!-- 排班耗时 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isWorkTime"/>
							</td>
							<td width="68%">
								<ui:switch property="value(isWorkTime)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isWorkTime.desc"/>
							</td>
						</tr>
					</table>
					<!-- 流程处理环节的办理时效 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isShowApprovalTime"/>
							</td>
							<td width="68%">
								<c:if test="${sysAppConfigForm.map.isShowApprovalTime != null}">
									<ui:switch property="value(isShowApprovalTime)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								</c:if>
								<c:if test="${sysAppConfigForm.map.isShowApprovalTime == null}">
									<ui:switch checked="true" property="value(isShowApprovalTime)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								</c:if>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isShowApprovalTime.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isShowApprovalTime');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					<!-- 沟通意见分组分层级显示 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isShowCommunicationsGroup"/>
							</td>
							<td width="68%">
								<ui:switch property="value(isShowCommunicationsGroup)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isShowCommunicationsGroup.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isShowCommunicationsGroup');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					<!-- 加签意见分组分层级显示 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isShowAssignGroup"/>
							</td>
							<td width="68%">
								<ui:switch property="value(isShowAssignGroup)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isShowAssignGroup.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isShowAssignGroup');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
			<!-- 审批操作相关设置项 -->
			<tr>
				<td class="td_normal_title" width="15%" align="center">
					<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.operations"/>
				</td>
				<td width="85%">
					<!-- 起草节点隐藏审批意见框 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isDraftNodeDisplayOpinion" />
							</td>
							<td width="68%">
								<ui:switch property="value(isDraftNodeDisplayOpinion)" 
											onValueChange="affectDraftNodeDisplayOpinion(this.checked)" 
											enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
											disabledText="${lfn:message('sys-ui:ui.switch.disabled')}">
								</ui:switch>
								<div id="draftNodeDisplayOpinion" style="display: none;">
									<!-- 新建页面 -->
									<c:if test="${sysAppConfigForm.map.isDraftNodeDisplayOpinion != null}">
										<xform:checkbox property="value(isNewPageAndDraftsManRecallPage)" showStatus="edit">
											<xform:simpleDataSource value="true">
												<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.newPageAndDraftsManRecallPage" />
											</xform:simpleDataSource>
										</xform:checkbox>
									</c:if>
									<c:if test="${sysAppConfigForm.map.isDraftNodeDisplayOpinion == null}">
										<xform:checkbox property="value(isNewPageAndDraftsManRecallPage)" showStatus="edit" value="true">
											<xform:simpleDataSource value="true">
												<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.newPageAndDraftsManRecallPage" />
											</xform:simpleDataSource>
										</xform:checkbox>
									</c:if>
									<!-- 驳回后的页面 -->
									<c:if test="${sysAppConfigForm.map.isDraftNodeDisplayOpinion != null}">
										<xform:checkbox property="value(isRejectPage)" showStatus="edit">
											<xform:simpleDataSource value="true">
												<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.rejectPage" />
											</xform:simpleDataSource>
										</xform:checkbox>
									</c:if>
									<c:if test="${sysAppConfigForm.map.isDraftNodeDisplayOpinion == null}">
										<xform:checkbox property="value(isRejectPage)" showStatus="edit" value="true">
											<xform:simpleDataSource value="true">
												<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.rejectPage" />
											</xform:simpleDataSource>
										</xform:checkbox>
									</c:if>
									</div>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isDraftNodeDisplayOpinion.desc" /> <br />
								<a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isDraftNodeDisplayOpinion');">
									<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail" />
								</a>
							</td>
						</tr>
					</table>
				    <!-- 审批时可进行多级沟通 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isMultiCommunicateConfigurable"/>
							</td>
							<td width="68%">
								<c:if test="${sysAppConfigForm.map.isMultiCommunicateConfigurable != null}">
									<ui:switch property="value(isMultiCommunicateConfigurable)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								</c:if>
								<c:if test="${sysAppConfigForm.map.isMultiCommunicateConfigurable == null}">
									<ui:switch checked="true" property="value(isMultiCommunicateConfigurable)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								</c:if>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isMultiCommunicateConfigurable.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isMultiCommunicateConfigurable');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					<!-- 沟通时可隐藏沟通意见 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHiddenCommunicateNoteConfigurable"/>
							</td>
							<td width="68%">
								<c:if test="${sysAppConfigForm.map.isHiddenCommunicateNoteConfigurable != null}">
									<ui:switch property="value(isHiddenCommunicateNoteConfigurable)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								</c:if>
								<c:if test="${sysAppConfigForm.map.isHiddenCommunicateNoteConfigurable == null}">
									<ui:switch checked="true" property="value(isHiddenCommunicateNoteConfigurable)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								</c:if>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHiddenCommunicateNoteConfigurable.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isHiddenCommunicateNoteConfigurable');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					<!-- 取消沟通时保留流程意见 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isKeepCommunicateNote"/>
							</td>
							<td width="68%">
								<c:if test="${sysAppConfigForm.map.isKeepCommunicateNote != null}">
									<ui:switch property="value(isKeepCommunicateNote)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								</c:if>
								<c:if test="${sysAppConfigForm.map.isKeepCommunicateNote == null}">
									<ui:switch checked="true" property="value(isKeepCommunicateNote)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								</c:if>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isKeepCommunicateNote.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isKeepCommunicateNote');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					
					<!-- 转办时可隐藏意见 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHiddeTurnToDoNoteConfigurable"/>
							</td>
							<td width="68%">
								<ui:switch property="value(isHiddeTurnToDoNoteConfigurable)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHiddeTurnToDoNoteConfigurable.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isHiddeTurnToDoNoteConfigurable');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					
					<!-- 自由流私密意见 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isPrivateOpinion"/>
							</td>
							<td width="68%">
								<ui:switch property="value(isPrivateOpinion)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isPrivateOpinion.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isPrivateOpinion');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					
					<!-- 默认沟通流程发起人 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isCommunicateWithCreatorDefault"/>
							</td>
							<td width="68%">
								<c:if test="${sysAppConfigForm.map.isCommunicateWithCreatorDefault != null}">
									<ui:switch property="value(isCommunicateWithCreatorDefault)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								</c:if>
								<c:if test="${sysAppConfigForm.map.isCommunicateWithCreatorDefault == null}">
									<ui:switch checked="true" property="value(isCommunicateWithCreatorDefault)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								</c:if>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isCommunicateWithCreatorDefault.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isCommunicateWithCreatorDefault');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					 <!-- 审批时可进行多级加签 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isMultiAssignEnabled"/>
							</td>
							<td width="68%">
								<ui:switch property="value(isMultiAssignEnabled)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isMultiAssignEnabled.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isMultiAssignEnabled');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					<!-- 审批时可选择通知紧急程度 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isNotifyLevelOptional"/>
							</td>
							<td width="68%">
								<c:if test="${sysAppConfigForm.map.isNotifyLevelOptional != null}">
									<ui:switch property="value(isNotifyLevelOptional)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								</c:if>
								<c:if test="${sysAppConfigForm.map.isNotifyLevelOptional == null}">
									<ui:switch checked="true" property="value(isNotifyLevelOptional)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								</c:if>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isNotifyLevelOptional.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isNotifyLevelOptional');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					<!-- 撤回审批时强制保留流程意见 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.forceKeepAuditNote"/>
							</td>
							<td width="68%">
								<ui:switch property="value(forceKeepAuditNote)" onValueChange="affectOptionalRow(this.checked)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.forceKeepAuditNote.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('forceKeepAuditNote');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					<!-- 撤回审批时可选择是否保留流程意见 -->
					<c:if test="${sysAppConfigForm.map.forceKeepAuditNote != 'true'}">
						<table class="tb_normal" id="keepAuditNoteOptionalRow" width="100%">
					</c:if>
					<c:if test="${sysAppConfigForm.map.forceKeepAuditNote == 'true'}">
						<table class="tb_normal" id="keepAuditNoteOptionalRow" width="100%" style="display:none">
					</c:if>
							<tr>
								<td align="center">
									<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isKeepAuditNoteOptional"/>
								</td>
								<td width="68%">
									<ui:switch property="value(isKeepAuditNoteOptional)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isKeepAuditNoteOptional.desc"/>
									<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isKeepAuditNoteOptional');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
								</td>
							</tr>
						</table>
					<!-- 撤回审批是否发送通知当前审批人  -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isWithdrawalApproveNotSendNotice"/>
							</td>
							<td width="68%">
								<c:if test="${sysAppConfigForm.map.isWithdrawalApproveNotSendNotice != null}">
									<ui:switch property="value(isWithdrawalApproveNotSendNotice)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								</c:if>
								<c:if test="${sysAppConfigForm.map.isWithdrawalApproveNotSendNotice == null}">
									<ui:switch checked="false" property="value(isWithdrawalApproveNotSendNotice)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								</c:if>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isWithdrawalApproveNotSendNotice.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isWithdrawalApproveNotSendNotice');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					<!-- 驳回选项（返回功能）增加显示开关  -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isShowRefuseOptional" />
							</td>
							<td width="68%">
								<ui:switch checked="true" property="value(isShowRefuseOptional)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isShowRefuseOptional.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isShowRefuseOptional');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					<!-- 流程说明  -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isShowFlowDescription" />
							</td>
							<td width="68%">
								<ui:switch checked="true" property="value(isShowFlowDescription)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isShowFlowDescription.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isShowFlowDescription');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					<!-- 通知选项  -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isShowNotifyType" />
							</td>
							<td width="68%">
								<ui:switch checked="true" property="value(isShowNotifyType)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isShowNotifyType.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isShowNotifyType');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					<!-- 已处理人 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isShowHistoryHandlers" />
							</td>
							<td width="68%">
								<ui:switch checked="true" property="value(isShowHistoryHandlers)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isShowHistoryHandlers.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isShowHistoryHandlers');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					<!-- 当前处理人 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isShowCurrentHandlers" />
							</td>
							<td width="68%">
								<ui:switch checked="true" property="value(isShowCurrentHandlers)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isShowCurrentHandlers.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isShowCurrentHandlers');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					<!-- 审批时显示办理限时 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isShowLimitTimeOperation" />
							</td>
							<td width="68%">
								<ui:switch checked="true" property="value(isShowLimitTimeOperation)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isShowLimitTimeOperation.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isShowLimitTimeOperation');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					<!-- 流程附言 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.lbpmPostscript" />
							</td>
							<td width="68%">
								<ui:switch property="value(lbpmPostscript)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.lbpmPostscript.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('lbpmPostscript');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					
					<!-- 驳回时可选择某节点中任意已处理人  -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isRefuseSelectPeople" />
							</td>
							<td width="68%">
								<ui:switch checked="false" property="value(isRefuseSelectPeople)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isRefuseSelectPeople.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isRefuseSelectPeople');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					
				</td>
			</tr>
			
			<!-- 审批要点设置项 -->
			<tr>
				<td class="td_normal_title" width="15%" align="center">
					<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.others"/>
				</td>
				<td width="85%">
					<!-- 打印时显示审批要点 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isPrintShowAuditPoint" />
							</td>
							<td width="68%">
								<ui:switch property="value(isPrintShowAuditPoint)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isPrintShowAuditPoint.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isPrintShowAuditPoint');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					<!-- 打印时显示流程附言 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.printLbpmPostscript" />
							</td>
							<td width="68%">
								<ui:switch property="value(printLbpmPostscript)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.printLbpmPostscript.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('printLbpmPostscript');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					
					<!-- 审批时支持手写签批  -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHandSignatureEnabled" />
							</td>
							<td width="68%">
								<ui:switch id="handSignature" property="value(isHandSignatureEnabled)" onValueChange="isHandSignatureEnabled('handSignature');" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<c:choose>
									<c:when test="${sysAppConfigForm.map.isHandSignatureEnabled}">
										<span id="handSignatureSpan">
									</c:when>
									<c:otherwise>
										<span id="handSignatureSpan" style="display: none;">
									</c:otherwise>
								</c:choose>
									<c:if test="${sysAppConfigForm.map.handSignatureType == null}">
										<label>
											<input type="radio" name="value(handSignatureType)" value="jg" checked = "checked" onclick="setHandSignatureMsg(this);" />
											<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.handSignatureType.jg"/>
										</label>
										&nbsp;&nbsp;
										<label>
											<input type="radio" name="value(handSignatureType)" value="tsd" onclick="setHandSignatureMsg(this);" />
											<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.handSignatureType.tsd"/>
										</label>
									</c:if>
									<c:if test="${sysAppConfigForm.map.handSignatureType != null}">
										<xform:radio property="value(handSignatureType)" showStatus="edit" onValueChange="setHandSignatureMsg(this);">
											<xform:simpleDataSource value="jg"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.handSignatureType.jg"/>&nbsp;&nbsp;</xform:simpleDataSource>
											<xform:simpleDataSource value="tsd"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.handSignatureType.tsd"/>&nbsp;&nbsp;</xform:simpleDataSource>
										</xform:radio>
									</c:if>
									<br/>
								</span>
								<c:choose>
									<c:when test="${sysAppConfigForm.map.handSignatureType=='tsd'}">
										<span id="handSignatureMsg"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.handSignatureType.tsd.desc"/> </span>
									</c:when>
									<c:otherwise>
										<span id="handSignatureMsg"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHandSignatureEnabled.desc"/></span>
									</c:otherwise>
								</c:choose>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isHandSignatureEnabled');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
				
					<!-- 审批意见是否显示历史部门和岗位  -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isShowHistoryDeptAndPost" />
							</td>
							<td width="68%">
								<ui:switch property="value(isShowHistoryDeptAndPost)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isShowHistoryDeptAndPost.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isShowHistoryDeptAndPost');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					<!-- 处理人为空跳过，并且处理人被禁用时，是否跳过  -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHanderUnvaluableSkip" />
							</td>
							<td width="68%">
								<ui:switch property="value(isHanderUnvaluableSkip)" onValueChange="isHanderUnvaluableSkip(this.checked);" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHanderUnvaluableSkip.desc"/>
							</td>
						</tr>
					</table>
					<!-- 处理人无效工作转交  -->
					<table class="tb_normal" width="100%" id="isHandoverIfUnAvailableTb">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHandoverIfUnAvailable" />
							</td>
							<td width="68%">
								<ui:switch id="handover" property="value(isHandoverIfUnAvailable)" onValueChange="isHandoverIfUnAvailable('handover');" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<c:choose>
								<c:when test="${sysAppConfigForm.map.isHandoverIfUnAvailable}">
								<span id="handoverSpan">
									</c:when>
									<c:otherwise>
										<span id="handoverSpan" style="display: none;">
									</c:otherwise>
								</c:choose>
								<c:if test="${sysAppConfigForm.map.handoverType == null}">
									<label>
										<input type="radio" name="value(handoverType)" value="recipient" checked = "checked" />
										<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.handoverType.recipient"/>
									</label>
									<label>
										<input type="radio" name="value(handoverType)" value="heads" />
										<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.handoverType.heads"/>
									</label>
								</c:if>
								<c:if test="${sysAppConfigForm.map.handoverType != null}">
									<xform:radio property="value(handoverType)" showStatus="edit">
										<xform:simpleDataSource value="recipient"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.handoverType.recipient"/>&nbsp;&nbsp;</xform:simpleDataSource>
										<xform:simpleDataSource value="heads"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.handoverType.heads"/>&nbsp;&nbsp;</xform:simpleDataSource>
									</xform:radio>
								</c:if>
								<br/>
								</span>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHandoverIfUnAvailable.desc"/>
							</td>
						</tr>
					</table>
					<!-- 特权人处理流程时，可选择是否通知当前处理人，默认开启  -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isNotifyCurrentHandler" />
							</td>
							<td width="68%">
								<ui:switch checked="true" property="value(isNotifyCurrentHandler)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isNotifyCurrentHandler.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isNotifyCurrentHandler');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					<!-- 流程重启  -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isProcessRestart" />
							</td>
							<td width="68%">
								<ui:switch checked="false" property="value(isProcessRestart)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isProcessRestart.desc"/>
							</td>
						</tr>
					</table>
					<!-- 异常重试  -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isErrorRestart" />
							</td>
							<td width="68%">
								<ui:switch checked="false" property="value(isErrorRestart)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isErrorRestart.desc"/>
							</td>
						</tr>
					</table>
					<!-- 业务授权 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.businessauth" />
							</td>
							<td width="68%">
								<ui:switch checked="false" property="value(businessauth)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}" onValueChange="businessauth(this.checked);"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.businessauth.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('businessauth',true);"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					
					<!-- 个人业务授权 -->
					<table class="tb_normal" width="100%"  id="personBusinessauthTable">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.personBusinessauth" />
							</td>
							<td width="68%">
								<ui:switch checked="false" property="value(personBusinessauth)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.personBusinessauth.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('personBusinessauth');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					
					<!-- 预审授权 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.advanceApprovalAuth" />
							</td>
							<td width="68%">
								<ui:switch checked="false" property="value(advanceApprovalAuth)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.advanceApprovalAuth.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('advanceApprovalAuth');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					
					<!--  起草人身份新增全局功能开关，支持隐藏-->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isShowDraftsmanStatus" />
							</td>
							<td width="68%">
								<ui:switch id="showDraftsmanStatus" checked="true" property="value(isShowDraftsmanStatus)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" onValueChange="isShowDraftsmanStatus(this.checked);" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isShowDraftsmanStatus.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isShowDraftsmanStatus');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					<!--  提交人身份为多个时，新建页面是否弹窗提醒 -->
					<table class="tb_normal" width="100%" id="popupWindowRemindSubmitterTable">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isPopupWindowRemindSubmitter" />
							</td>
							<td width="68%">
								<ui:switch id="popupWindowRemindSubmitter" checked="true" property="value(isPopupWindowRemindSubmitter)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isPopupWindowRemindSubmitter.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isPopupWindowRemindSubmitter');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>

					<!--  隐藏节点标识 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHideNodeIdentifier" />
							</td>
							<td width="68%">
								<ui:switch property="value(isHideNodeIdentifier)" id="isHideNodeSetting" onValueChange="nodeIdentifierModify(this.checked)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<div id="nodeIdentifierModifier" style="display:none;">
									<c:if test="${sysAppConfigForm.map.isHideNodeIdentifier == null || sysAppConfigForm.map.isHideNodeIdentifier eq false}">
										<xform:radio className="hideNodeIdentifierType" property="value(hideNodeIdentifierType)" showStatus="edit" value="isRemoveNodeIdentifier">
											<xform:simpleDataSource value="isRemoveNodeIdentifier">
												<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isRemoveNodeIdentifier" />
											</xform:simpleDataSource>
											<xform:simpleDataSource value="isHideAllNodeIdentifier">
												<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHideAllNodeIdentifier" />
											</xform:simpleDataSource>
										</xform:radio>
									</c:if>
									<c:if test="${sysAppConfigForm.map.isHideNodeIdentifier eq true && ('isRemoveNodeIdentifier' eq sysAppConfigForm.map.hideNodeIdentifierType || sysAppConfigForm.map.hideNodeIdentifierType == null)}">
										<xform:radio className="hideNodeIdentifierType" property="value(hideNodeIdentifierType)" showStatus="edit"  value="isRemoveNodeIdentifier">
											<xform:simpleDataSource value="isRemoveNodeIdentifier">
												<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isRemoveNodeIdentifier" />
											</xform:simpleDataSource>
											<xform:simpleDataSource value="isHideAllNodeIdentifier">
												<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHideAllNodeIdentifier" />
											</xform:simpleDataSource>
										</xform:radio>
									</c:if>
									<c:if test="${sysAppConfigForm.map.isHideNodeIdentifier eq true && 'isHideAllNodeIdentifier' eq sysAppConfigForm.map.hideNodeIdentifierType}">
										<xform:radio className="hideNodeIdentifierType" property="value(hideNodeIdentifierType)" showStatus="edit" value="isHideAllNodeIdentifier">
											<xform:simpleDataSource value="isRemoveNodeIdentifier">
												<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isRemoveNodeIdentifier" />
											</xform:simpleDataSource>
											<xform:simpleDataSource value="isHideAllNodeIdentifier">
												<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHideAllNodeIdentifier" />
											</xform:simpleDataSource>
										</xform:radio>
									</c:if>
								</div>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHideAllNodeIdentifier.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isRemoveNodeIdentifier');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					<!--  隐藏即将流向 -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHideOperationsRow" />
							</td>
							<td width="68%">
								<ui:switch checked="false" property="value(isHideOperationsRow)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHideOperationsRow.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isHideOperationsRow');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					<!-- 起草人废弃  -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isCanDrafterAbandon" />
							</td>
							<td width="68%">
								<ui:switch checked="true" property="value(isCanDrafterAbandon)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isCanDrafterAbandon.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isCanDrafterAbandon');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
					
					<!-- 启用 GKP 公文流程样式  -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isShowProcessImissiveStyle"/>
							</td>
							<td width="68%">
								<ui:switch property="value(isShowProcessImissiveStyle)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isShowProcessImissiveStyle.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isShowProcessImissiveStyle');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>

					<!-- 驳回到已处理人发送待办  -->
					<table class="tb_normal" width="100%">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.notify.historyHandlerNotify.send" />
							</td>
							<td width="68%">
								<ui:switch checked="false" property="value(isSendHistoryHandlerNotify)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.notify.historyHandlerNotify.send.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isSendHistoryHandlerNotify');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>

					<!-- 系统通知类型消息切换为待阅消息  -->
					<table class="tb_normal" width="100%" style="display: none">
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.notify.type.sendChange" />
							</td>
							<td width="68%">
								<ui:switch checked="false" property="value(isChangeNotifyType)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.notify.type.sendChange.desc"/>
							</td>
						</tr>
					</table>

				</td>
			</tr>

		</table>
		</div>
		</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.sys.lbpmservice.support.model.LbpmSetting" />
			
			<center style="margin-top: 10px;">
			<!-- 保存 -->
			<ui:button text="${lfn:message('button.save')}" style="position: fixed;bottom:0px;left: 15px;width:95%;background: #fff;" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');" order="1" ></ui:button>
			</center>
		</html:form>
		
		<!--#68650 图片容器-开始 -->
		<div id="container" style="top: 0px; left: 0px; width: 100%; height: 100%; position: fixed; z-index: 999;display:none;"></div>
		<!--#68650 图片容器-结束 -->
		<script type="text/javascript">
			Com_IncludeFile("doclist.js");
			Com_IncludeFile("data.js");
	 		$KMSSValidation();
	 		var SettingInfo = null;
	 		//统一调用此方法获取默认值与功能开关的值
	 		function getSettingInfo(){
	 			if (SettingInfo == null) {
	 				SettingInfo = new KMSSData().AddBeanData("lbpmSettingInfoService").GetHashMapArray()[0];
	 			}
	 			return SettingInfo;
	 		}
	 		
	 		var userLang = "<%=MultiLangTextGroupTag.getUserLangKey()%>";
	 		
	 		 $(function(){
	 			//#40544 修改审批意见功能
	 			var _isModifyAuditNote = getSettingInfo()["isModifyAuditNote"];
	 			if (_isModifyAuditNote === "true"){
		 			$("#auditNoteModifier").show();
		 		}else{
		 			$("#auditNoteModifier").hide();
		 		}
	 			
	 			//#60715 起草节点显示审批意见框
	 			var _isDraftNodeDisplayOpinion = getSettingInfo()["isDraftNodeDisplayOpinion"];
	 			if (_isDraftNodeDisplayOpinion === "true") {
	 				$("#draftNodeDisplayOpinion").show();
	 			}else{
	 				$("#draftNodeDisplayOpinion").hide();
	 			}
	 		})
	 		
	 		LUI.ready(function(){
	 			//#提交人身份
	 			var showDraftsmanStatusObj =  getSettingInfo()["isShowDraftsmanStatus"];
	 			if (showDraftsmanStatusObj === "false"){
	 				var selCk = LUI("popupWindowRemindSubmitter");
					$("#popupWindowRemindSubmitterTable").hide();
	 			}
	 			//#业务授权
	 			var businessauthObj =  getSettingInfo()["businessauth"];
	 			if(businessauthObj == "false"){
	 				$("#personBusinessauthTable").hide();
	 			}
				//隐藏节点标识
				var _isHideNodeIdentifier = getSettingInfo()["isHideNodeIdentifier"];
				var _isHideAllNodeIdentifier = getSettingInfo()["isHideAllNodeIdentifier"];
				var _hideNodeIdentifier = getSettingInfo()["hideNodeIdentifierType"];
				var _isRemoveNodeIdentifier = getSettingInfo()["isRemoveNodeIdentifier"];
				if (_isHideNodeIdentifier === "true") {
					$("#nodeIdentifierModifier").show();
					//

				}else if(_isRemoveNodeIdentifier === "true" && _hideNodeIdentifier === "false"){// 若原本开启了隐藏即将流向则兼容
					$("#nodeIdentifierModifier").show();
					LUI("isHideNodeSetting").checkbox.click();
				}else{

					$("#nodeIdentifierModifier").hide();
				}
				//
				var isHanderUnvaluableSkipObj =  getSettingInfo()["isHanderUnvaluableSkip"];
				if(isHanderUnvaluableSkipObj == "true"){
					$("#isHandoverIfUnAvailableTb").hide();
				}
	 		})
	 		
	 		function writeFormField(){
	 			var form = document.sysAppConfigForm;
	 			for(var i=0;i<form.length;i++){
	 				if(form[i].value == ""){
	 					form[i].value = "false";
	 				}
	 			}
	 			return true;
	 		}
	 		function affectOptionalRow(checked){
	 			if (checked==true){
	 				$("#keepAuditNoteOptionalRow").hide();
	 			} else {
	 				$("#keepAuditNoteOptionalRow").show();
	 			}
	 		}
	 		function affectAuditNoteModify(checked){
	 			if (checked==true){
	 				$("#auditNoteModifier").show();
	 			} else {
	 				$("#auditNoteModifier").hide();
	 			}
	 		}
	 		//#60715 起草节点隐藏审批意见框
	 		function affectDraftNodeDisplayOpinion(checked) {
				if(checked == true) {
					$("#draftNodeDisplayOpinion").show();
				} else {
					$("#draftNodeDisplayOpinion").hide();
				}
			}
	 		
	 		window.showItemDetail=function(key,isTemp){
	 			var url = '/sys/lbpmservice/support/lbpm_setting/lbpmSetting_diagram_function.jsp?key=';
	 			var resourceKey = 'sys-lbpmservice-support:lbpmSetting.' + key;
	 			var title  = Data_GetResourceString(resourceKey);
	 			
	 			//创建数组作为传递参数
	 			//可能是两个容器，也可能是四个容器，两个传两张图片，四个传四张图片
	 			var arr=new Array(key+"0"+(userLang=="en-US"?"_"+userLang:""),key+"1"+(userLang=="en-US"?"_"+userLang:""));
	 			if(isTemp){
	 				arr.push(key+"Temp0"+(userLang=="en-US"?"_"+userLang:""));
	 				arr.push(key+"Temp1"+(userLang=="en-US"?"_"+userLang:""));
	 			}
	 			url=url+arr;
	 			var afterShow = function(){
					var mask=$(document).find('.lui_dialog_mask');
					//不知道为啥用css()无效
					var style = mask.attr("style") + ";background-color:#000 !important;";
					mask.attr("style",style);
				};
	 			if(arr.length==4){
	 				//四个容器
	 				seajs.use([ 'lui/dialog'], function(dialog) {
						dialog.iframe(url,
								title,
								function(value) {
								}, {
									"width" : 600,
									"height" : 510,//四个容器的是510，两个容器的是280
									topWin : window
						}).on("show",afterShow);
					});
	 			}else{
	 				//两个容器
	 				seajs.use([ 'lui/dialog'], function(dialog) {
						dialog.iframe(url,
								title,
								function(value) {
								}, {
									"width" : 600,
									"height" : 280,//四个容器的是510，两个容器的是280
									topWin : window
						}).on("show",afterShow);
					});
	 			}
	 		};
	 		Com_Parameter.event["submit"].push(writeFormField);
	 		
	 		function isHandSignatureEnabled(id){
	 			var isJGHandSignatureEnabled = <%=ResourceUtil.getKmssConfigString("sys.att.isJGHandSignatureEnabled")%>;
	 			var isTstudyEnabled = <%=ResourceUtil.getKmssConfigString("sys.tstudy.enable")%>;
	 			var selCk;
	 			selCk = LUI(id);
	 			if(selCk.checkbox.is(':checked')){
	 				if(!isJGHandSignatureEnabled && !isTstudyEnabled){
	 					alert('<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.handSignatureType.msg"/>');
	 					selCk.checkbox.prop('checked',false);
	 					var dom = document.getElementsByName("value(isHandSignatureEnabled)");
	 					dom[0].value = "false";
	 					selCk.setText(false);
	 					$("#handSignatureSpan").hide();
	 				}else{
	 					$("#handSignatureSpan").show();
	 				}
	 			}else{
	 				$("#handSignatureSpan").hide();
	 			}
	 		}
			function isHanderUnvaluableSkip(checked){
				if(checked == true) {
					$("#isHandoverIfUnAvailableTb").hide();
				} else {
					$("#isHandoverIfUnAvailableTb").show();
				}
			}
			function isHandoverIfUnAvailable(id){
				var selCk = LUI(id);
				if(selCk.checkbox.is(':checked')){
					$("#handoverSpan").show();
				}else{
					$("#handoverSpan").hide();
				}
			}
			// 隐藏节点标识
			function nodeIdentifierModify(checked){
				if(checked == true) {
					$("#nodeIdentifierModifier").show();
				} else {
					$("#nodeIdentifierModifier").hide();
				}
			}
	 		function isShowDraftsmanStatus(checked){
	 			if(checked == true) {
					$("#popupWindowRemindSubmitterTable").show();
				} else {
					var selCk = LUI("popupWindowRemindSubmitter");
					$("#popupWindowRemindSubmitterTable").hide();
				}
	 		}

	 		function businessauth(checked){
	 			if(checked == true) {
					$("#personBusinessauthTable").show();
				} else {
					$("#personBusinessauthTable").hide();
				}
	 		}
	 		
	 		$(function(){
	 			var isJGHandSignatureEnabled = <%=ResourceUtil.getKmssConfigString("sys.att.isJGHandSignatureEnabled")%>;
	 			var isTstudyEnabled = <%=ResourceUtil.getKmssConfigString("sys.tstudy.enable")%>;
	 			if(!isJGHandSignatureEnabled){
	 				$("input[name='value(handSignatureType)'][value='jg']").prop("disabled","false");
	 			}
	 			if(!isTstudyEnabled){
	 				$("input[name='value(handSignatureType)'][value='tsd']").prop("disabled","false");
	 			}
	 		});

	 		function setHandSignatureMsg(dom){
	 			if(dom.value=="jg"){
	 				$("#handSignatureMsg").text('<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHandSignatureEnabled.desc"/>');
	 			}else{
	 				$("#handSignatureMsg").text('<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.handSignatureType.tsd.desc"/>');
	 			}
	 		}
	 		
	 	</script>
	</template:replace>
</template:include>
