<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" width="95%" sidebar="no">
	<template:replace name="title"> 
		<bean:message bundle="sys-portal" key="table.sysPortalHtml"/>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav" style="height:40px;line-height:40px;">
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-portal:module.sys.portal') }">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-portal:nav.sys.portal.portlet') }">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-portal:table.sysPortalHtml') }">
			</ui:menu-item>
		</ui:menu>
	</template:replace>
	
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
				<c:choose>
					<c:when test="${ sysPortalHtmlForm.method_GET == 'add' }">
						<ui:button text="${lfn:message('button.save') }" order="2" onclick="onDoSubmit('save');">
						</ui:button>
						<ui:button text="${lfn:message('button.saveadd') }" order="2" onclick="onDoSubmit('saveadd');">
						</ui:button>
					</c:when>
					<c:when test="${ sysPortalHtmlForm.method_GET == 'edit' }">					
						<ui:button text="${lfn:message('button.update') }" order="2" onclick="onDoSubmit('update');">
						</ui:button>	
						<kmss:auth requestURL="/sys/portal/sys_portal_html/sysPortalHtml.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
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
							location.href = "${LUI_ContextPath}/sys/portal/sys_portal_html/sysPortalHtml.do?method=delete&fdId=${sysPortalHtmlForm.fdId}";
						}
					})
				});
			}
	</script>
		<p class="txttitle"><bean:message bundle="sys-portal" key="table.sysPortalHtml"/></p>
		
		<html:form action="/sys/portal/sys_portal_html/sysPortalHtml.do">
		<center>
		<table class="tb_normal" width=95%>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-portal" key="sysPortalHtml.fdName"/>
				</td><td width="85%" colspan="3">
					<xform:text required="true" subject="${ lfn:message('sys-portal:sysPortalHtml.fdName') }" property="fdName" style="width:85%" />
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-portal" key="sysportal.switch.anonymous"/>
				</td>
				<td colspan="3" >
					 	<c:import
							url="/sys/portal/designer/jsp/sys_anonym_edit.jsp"
							charEncoding="UTF-8">
							<c:param name="formName" value="sysPortalHtmlForm" />
						</c:import>
				</td>
			</tr>
			<tr>	
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-portal" key="sysPortalHtml.fdContent"/>
				</td><td width="85%" colspan="3">
					<xform:rtf property="fdContent" needFilter="false"/>
				</td>
			</tr>	
			<%-- 所属场所 --%>
			<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp" charEncoding="UTF-8">
		        <c:param name="id" value="${sysPortalHtmlForm.authAreaId}"/>
		    </c:import> 
			<tr>
				<td class="td_normal_title" width="15%">${ lfn:message('sys-portal:common.msg.editors') }</td>
				<td colspan="3">
					<xform:address textarea="true" mulSelect="true" propertyId="fdEditorIds" propertyName="fdEditorNames" style="width:100%;height:90px;" ></xform:address>
				</td>
			</tr> 
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-portal" key="sysPortalHtml.docCreator"/>
				</td><td width="35%">
					<xform:address propertyId="docCreatorId" propertyName="docCreatorName" orgType="ORG_TYPE_PERSON" showStatus="view" />
				</td>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-portal" key="sysPortalHtml.docCreateTime"/>
				</td><td width="35%">
					<xform:datetime property="docCreateTime" showStatus="view" />
				</td>
			</tr>
			<c:if test="${sysPortalHtmlForm.method_GET!='add'}">
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-portal" key="sysPortalHtml.docAlteror"/>
				</td><td width="35%">
					<xform:address propertyId="docAlterorId" propertyName="docAlterorName" orgType="ORG_TYPE_PERSON" showStatus="view" />
				</td>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-portal" key="sysPortalHtml.docAlterTime"/>
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
		<script type="text/javascript">
			$KMSSValidation();
			CKFilter.Base64Encode =  function(str,xForm){
				return base64Encodex(str, null);
			}
			function onDoSubmit(method){
				Com_Submit(document.sysPortalHtmlForm, method);
			}
			
			function switchChange(flag){
				$("input[name='fdAnonymous']").val(flag);
			}
		</script>
		</html:form>
		<br>
	</template:replace>
</template:include>
