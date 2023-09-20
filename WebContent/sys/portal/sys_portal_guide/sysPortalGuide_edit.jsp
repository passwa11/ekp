<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" width="95%" sidebar="no">
	<template:replace name="title"> 
		<bean:message bundle="sys-portal" key="table.sysPortalGuide"/>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav" style="height:40px;line-height:40px;">
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-portal:module.sys.portal') }">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-portal:nav.sys.portal.portlet') }">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-portal:table.sysPortalGuide') }">
			</ui:menu-item>
		</ui:menu>
	</template:replace>
	
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
				<c:choose>
					<c:when test="${ sysPortalGuideForm.method_GET == 'add' }">
						<ui:button text="${lfn:message('button.save') }" order="2" onclick="Com_Submit(document.sysPortalGuideForm, 'save');">
						</ui:button>
						<ui:button text="${lfn:message('button.saveadd') }" order="2" onclick="Com_Submit(document.sysPortalGuideForm, 'saveadd');">
						</ui:button>
					</c:when>
					<c:when test="${ sysPortalGuideForm.method_GET == 'edit' }">					
						<ui:button text="${lfn:message('button.update') }" order="2" onclick="Com_Submit(document.sysPortalGuideForm, 'update');">
						</ui:button>	
						<kmss:auth requestURL="/sys/portal/sys_portal_guide/sysPortalGuide.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
							<ui:button order="3" text="${lfn:message('button.delete') }" onclick="deleteHtml();">
							</ui:button>
						</kmss:auth> 						
					</c:when>
				</c:choose>
				<ui:button text="${lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();">
				</ui:button>
		</ui:toolbar>
	</template:replace>
	
	<template:replace name="content">
	<script type="text/javascript">
			function deleteHtml(){
				seajs.use(['lui/dialog'],function(dialog){
					dialog.confirm("${ lfn:message('sys-portal:sysPortalPage.msg.delete') }",function(val){
						if(val==true){
							location.href = "${LUI_ContextPath}/sys/portal/sys_portal_guide/sysPortalGuide.do?method=delete&fdId=${sysPortalGuideForm.fdId}";
						}
					})
				});
			}
	</script>
		<p class="txttitle"><bean:message bundle="sys-portal" key="table.sysPortalGuide"/></p>
		
		<html:form action="/sys/portal/sys_portal_guide/sysPortalGuide.do">
		<center>
		<table class="tb_normal" width=95%>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-portal" key="sysPortalGuide.fdName"/>
				</td><td width="85%" colspan="3">
					<xform:text required="true" subject="${ lfn:message('sys-portal:sysPortalGuide.fdName') }" property="fdName" style="width:85%" />
				</td>
			</tr>
			<tr>	
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-portal" key="sysPortalGuide.fdLink"/>
				</td>
				<td width="85%" colspan="3">
					<div style="display: none;">
						<xform:radio property="fdType" showStatus="edit" value="${sysPortalGuideForm.fdType}" onValueChange="changeFdType">
							<xform:enumsDataSource enumsType="sys_portal_guide_type" />
						</xform:radio>
						<br/><br/>
					</div>	
					<div id="fdType_Rtf"><xform:rtf property="fdContent" needFilter="false"/></div>
					<div id="fdType_Link"><xform:text property="fdLink" style="width:98%;"></xform:text></div>
				</td>
			</tr>	
			<tr>
				<td class="td_normal_title" width="15%">${ lfn:message('sys-portal:common.msg.editors') }</td>
				<td colspan="3">
					<xform:address textarea="true" mulSelect="true" propertyId="fdEditorIds" propertyName="fdEditorNames" style="width:100%;height:90px;" ></xform:address>
				</td>
			</tr> 
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-portal" key="sysPortalGuide.docCreator"/>
				</td><td width="35%">
					<xform:address propertyId="docCreatorId" propertyName="docCreatorName" orgType="ORG_TYPE_PERSON" showStatus="view" />
				</td>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-portal" key="sysPortalGuide.docCreateTime"/>
				</td><td width="35%">
					<xform:datetime property="docCreateTime" showStatus="view" />
				</td>
			</tr>
			<c:if test="${sysPortalGuideForm.method_GET!='add'}">
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-portal" key="sysPortalGuide.docAlteror"/>
				</td><td width="35%">
					<xform:address propertyId="docAlterorId" propertyName="docAlterorName" orgType="ORG_TYPE_PERSON" showStatus="view" />
				</td>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-portal" key="sysPortalGuide.docAlterTime"/>
				</td><td width="35%">
					<xform:datetime property="docAlterTime" showStatus="view" />
				</td>
			</tr>
			</c:if>
		</table>
		</center>
		<html:hidden property="fdId" />
		<html:hidden property="method_GET" />		
		<script type="text/javascript">	
		Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|xform.js", null, "js");
		</script>		
		<script>
			$KMSSValidation();
			seajs.use(['lui/jquery'],function($){
				$(function(){
					var fdType = '${sysPortalGuideForm.fdType}';
					if(fdType == 'rtf'){
						$('#fdType_Rtf').show();
						$('#fdType_Link').hide();
					}else{
						$('#fdType_Link').show();
						$('#fdType_Rtf').hide();
					}
				});
				//修改内容类型
				window.changeFdType = function(value){
					if(value == 'rtf'){
						$('#fdType_Rtf').show();
						$('#fdType_Link').hide();
					}else{
						$('#fdType_Link').show();
						$('#fdType_Rtf').hide();
					}
				};
			});
		</script>
		</html:form>
		<br>
	</template:replace>
</template:include>
