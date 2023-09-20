<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view" sidebar="no">
	<template:replace name="title">
		<bean:message bundle="sys-rule" key="table.sysRuleSetDoc"/>
	</template:replace>
	<template:replace name="head">
		<style>
			.rel_btn{
				color: #2574ad;
				border-bottom: 1px solid;
    			border-bottom: 1px solid transparent;
			}
			.rel_btn:hover{
				color: #123a6b;
    			border-bottom-color: #123a6b;
			}
		</style>
		<script type="text/javascript">
			seajs.use(['theme!list']);
		</script>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/ui/extend/theme/default/style/icon.css" />
		<script type="text/javascript" src="${LUI_ContextPath}/resource/js/jquery.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript" src="${ LUI_ContextPath}/sys/ui/extend/template/module/list.js?s_cache=${LUI_Cache}"></script>
		<script>
			Com_IncludeFile("data.js|dialog.js");
		</script>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<ui:button text="${lfn:message('button.edit')}" order="1" onclick="Com_OpenWindow('sysRuleSetDoc.do?method=edit&fdId=${sysRuleSetDocForm.fdId}','_self');" />
			<c:if test="${sysRuleSetDocForm.fdIsAvailable == true}">
				<ui:button text="${lfn:message('sys-rule:sysRuleSetDoc.available.result.false')}" order="2" onclick="Com_OpenWindow('sysRuleSetDoc.do?method=invalidated&fdId=${sysRuleSetDocForm.fdId}','_self');" />
			</c:if>
			<c:if test="${sysRuleSetDocForm.fdIsAvailable == false}">
				<ui:button text="${lfn:message('sys-rule:sysRuleSetDoc.available.result.true')}" order="2" onclick="Com_OpenWindow('sysRuleSetDoc.do?method=invalidated&fdId=${sysRuleSetDocForm.fdId}','_self');" />
			</c:if>
			<ui:button text="${lfn:message('button.delete') }" order="3" onclick="Com_OpenWindow('sysRuleSetDoc.do?method=delete&fdId=${sysRuleSetDocForm.fdId}','_self');"></ui:button>
    	 	<ui:button text="${lfn:message('button.close') }" order="4" onclick="Com_CloseWindow();"/>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<div style="width: 95%; margin: 10px auto;">
		<p class="txttitle">
			<bean:message bundle="sys-rule" key="table.sysRuleSetDoc"/>
		</p>
		
		<center>
			<ui:tabpanel>
				<ui:content title="${lfn:message('sys-rule:sysRuleSetDoc.tab.title01') }">
					<table class="tb_normal" width=95%>
						<tr>
							<td width=15% class="td_normal_title">
							    <bean:message bundle="sys-rule" key="sysRuleSetDoc.fdName"/>
							</td><td width=35% colspan="3">
								<bean:write name="sysRuleSetDocForm" property="fdName"/>
								<input type="hidden" name="fdVersion"/>
							</td>
						</tr>
						<tr>
							<td width=15% class="td_normal_title">
							    <bean:message bundle="sys-rule" key="sysRuleSetDoc.sysRuleSetCate"/>
							</td><td width=35% colspan="3">
								<html:hidden property="sysRuleSetCateId"/>
								<bean:write name="sysRuleSetDocForm" property="sysRuleSetCateName"/>
							</td>
						</tr>
						<tr>
							<td width=15% class="td_normal_title">
							    <bean:message bundle="sys-rule" key="sysRuleSetDoc.fdOrder"/>
							</td><td width=35% colspan="3">
								<bean:write name="sysRuleSetDocForm" property="fdOrder"/>
							</td>
						</tr>
						<tr>
							<td width=15% class="td_normal_title">
							    <bean:message bundle="sys-rule" key="sysRuleSetDoc.fdIsAvailable"/>
							</td><td width=35% colspan="3">
								<sunbor:enumsShow value="${sysRuleSetDocForm.fdIsAvailable}"  enumsType="sys_rule_available"/>
							</td>
						</tr>
						<!-- 参数展示 -->
						<tr>
							<td width=15% class="td_normal_title">
							    <bean:message bundle="sys-rule" key="sysRuleSetParam.setting"/>
							</td>
							<td width=85%>
								<c:import url="/sys/rule/sys_ruleset_param/view.jsp" charEncoding="UTF-8">
								</c:import>
							</td>
						</tr>
						<!-- 规则展示 -->
						<tr>
							<td width=15% class="td_normal_title">
							    <bean:message bundle="sys-rule" key="sysRuleSetRule.setting"/>
							</td>
							<td width=85%>
								<c:import url="/sys/rule/sys_ruleset_rule/view.jsp" charEncoding="UTF-8">
								</c:import>
							</td>
						</tr>
						<!-- 结果取向 -->
						<tr>
							<td width=15% class="td_normal_title">
								<bean:message bundle="sys-rule" key="sysRuleSetDoc.fdMode"/>
							</td>
							<td width=85% colspan="3">
							    <sunbor:enumsShow value="${sysRuleSetDocForm.fdMode}" enumsType="sys_ruleset_mode"/>
							</td>
						</tr>
						<!-- 异常或者不匹配时默认值设置 -->
						<tr>
							<td width=10% class="td_normal_title">
								<!-- #149064-规则引擎没有做多语言适配 -->
								<bean:message bundle="sys-rule" key="sysRuleSetDoc.defaultValSetting"/>
							</td>
						    <td width=30% colspan="1">
								<xform:select property="defaultValType" style="width:20%;" showStatus="view">
									<xform:enumsDataSource enumsType="sys_rule_return_type" />
								</xform:select>&nbsp;&nbsp;
								<xform:text property="defaultDisVal" showStatus="view" value="${sysRuleSetDocForm.defaultDisVal }"></xform:text>
							</td>
						</tr>
						<!-- 可使用者 -->
						<tr>
							<td class="td_normal_title" width=15%><bean:message bundle="sys-rule" key="sysRuleSetDoc.authReaders"/></td>
							<td  width=85% colspan="3">
							  <kmss:showText value="${sysRuleSetDocForm.authReaderNames}"/>
						   </td>
						</tr>
						<!-- 可维护者 -->
						<tr>
							<td class="td_normal_title" width=15%><bean:message bundle="sys-rule" key="sysRuleSetDoc.authEditors"/></td>
							<td width=85% colspan="3">
							  <kmss:showText value="${sysRuleSetDocForm.authEditorNames}"/>
							</td>
						</tr>
					</table>
				</ui:content>
				<ui:content title="${lfn:message('sys-rule:sysRuleSetDoc.tab.title02') }">
					<table class="tb_normal" width=95%>
						<tr>
							<td width=100% colspan="2">
								<list:listview id="listview">
									<ui:source type="AjaxJson">
										{url:'/sys/rule/sys_ruleset_temp/sysRuleTemplate.do?method=getRelationData&sysRuleSetDocId=${sysRuleSetDocForm.fdId }'}
									</ui:source>
									<list:colTable isDefault="false"
										layout="sys.ui.listview.columntable"
										name="columntable">
										<list:col-serial></list:col-serial>
										<list:col-auto props=""></list:col-auto>
									</list:colTable>
								</list:listview>
								<list:paging></list:paging>
							</td>
						</tr>
					</table>
				</ui:content>
			</ui:tabpanel>
			
		</center>
		
		<script language="JavaScript">
			//查看引用
			window.openRefDialog = function(modelId){
				var dialog = new KMSSDialog();
				dialog.URL = Com_Parameter.ContextPath + "sys/rule/sys_ruleset_temp/sysRuleTemplate.do?method=getReferenceInfo&modelId="+modelId;
				dialog.Show(window.screen.width*872/1366,window.screen.height*616/768);
			}
		</script>
	</template:replace>
</template:include>
