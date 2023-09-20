<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" width="95%" sidebar="no" showQrcode="false">
	<template:replace name="title"> 
		<bean:message bundle="sys-mportal" key="table.sysMportalHtml"/>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav" style="height:40px;line-height:40px;">
			<ui:menu-item text="${ lfn:message('home.home') }"
					 icon="lui_icon_s_home">
			</ui:menu-item>
			<ui:menu-item text="移动办公">
			</ui:menu-item>
			<ui:menu-item text="移动门户">
			</ui:menu-item>
			<ui:menu-item text="门户元素">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-mportal:table.sysMportalHtml') }">
			</ui:menu-item>
		</ui:menu>
	</template:replace>
	
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
				<c:choose>
					<c:when test="${ sysMportalHtmlForm.method_GET == 'add' }">
						<ui:button text="${lfn:message('button.save') }" order="2" onclick="Com_Submit(document.sysMportalHtmlForm, 'save');">
						</ui:button>
						<ui:button text="${lfn:message('button.saveadd') }" order="2" onclick="Com_Submit(document.sysMportalHtmlForm, 'saveadd');">
						</ui:button>
					</c:when>
					<c:when test="${ sysMportalHtmlForm.method_GET == 'edit' }">					
						<ui:button text="${lfn:message('button.update') }" order="2" onclick="Com_Submit(document.sysMportalHtmlForm, 'update');">
						</ui:button>							
					</c:when>
				</c:choose>
				<ui:button text="${lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();">
				</ui:button>
		</ui:toolbar>
	</template:replace>
	
	<template:replace name="content">
		<p class="txttitle"><bean:message bundle="sys-mportal" key="table.sysMportalHtml"/></p>
		
		<html:form action="/sys/mportal/sys_mportal_html/sysMportalHtml.do">
		<center>
		<table class="tb_normal" width=95%>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-mportal" key="sysMportalHtml.fdName"/>
				</td><td width="85%" colspan="3">
					<xform:text required="true" 
						subject="${ lfn:message('sys-mportal:sysMportalHtml.fdName') }" 
						property="fdName" style="width:85%" />
				</td>
			</tr>
			<tr>	
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-mportal" key="sysMportalHtml.fdContent"/>
				</td><td width="85%" colspan="3">
					<xform:rtf property="fdContent" needFilter="false"/>
				</td>
			</tr>	
			<tr>
				<td class="td_normal_title" width="15%">${ lfn:message('sys-mportal:sysMportal.msg.editors') }</td>
				<td colspan="3">
					<xform:address textarea="true" mulSelect="true" propertyId="fdEditorIds"
						 propertyName="fdEditorNames" style="width:100%;height:90px;" ></xform:address>
				</td>
			</tr> 
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-mportal" key="sysMportalHtml.docCreator"/>
				</td><td width="35%">
					<xform:address propertyId="docCreatorId" propertyName="docCreatorName" 
					orgType="ORG_TYPE_PERSON" showStatus="view" />
				</td>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-mportal" key="sysMportalHtml.docCreateTime"/>
				</td><td width="35%">
					<xform:datetime property="docCreateTime" showStatus="view" />
				</td>
			</tr>
			<c:if test="${sysMportalHtmlForm.method_GET!='add'}">
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-mportal" key="sysMportalHtml.docAlteror"/>
				</td><td width="35%">
					<xform:address propertyId="docAlterorId" propertyName="docAlterorName" 
						orgType="ORG_TYPE_PERSON" showStatus="view" />
				</td>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-mportal" key="sysMportalHtml.docAlterTime"/>
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
		</script>
		</html:form>
		<br>
	</template:replace>
</template:include>
