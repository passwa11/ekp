<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.edit">
    <template:replace name="head">
    </template:replace>
    <template:replace name="title">
    	<c:choose>
            <c:when test="${sysHelpConfigForm.method_GET == 'edit' }">
                <c:out value="${sysHelpConfigForm.fdName} - " />
                <c:out value="${ lfn:message('button.edit') }" />
            </c:when>
            <c:otherwise>
		    	${ lfn:message('sys-help:table.sysHelpConfig') } - ${ lfn:message('button.add') }
            </c:otherwise>
        </c:choose>
    </template:replace>
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
        	<c:choose>
	            <c:when test="${sysHelpConfigForm.method_GET == 'edit' }">
		            <ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(sysHelpConfigForm, 'update');" />
	            </c:when>
	            <c:otherwise>
		            <ui:button text="${ lfn:message('button.save') }" onclick="Com_Submit(sysHelpConfigForm, 'save');" />
		            <ui:button text="${ lfn:message('button.saveadd') }" onclick="Com_Submit(sysHelpConfigForm, 'saveadd');" />
	            </c:otherwise>
	        </c:choose>
            <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
            <ui:menu-item text="${ lfn:message('sys-help:module.sys.help') }" />
            <ui:menu-item text="${ lfn:message('sys-help:sysHelpConfig.edit') }" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
        <html:form action="/sys/help/sys_help_config/sysHelpConfig.do">
			<table class="tb_normal" width="100%" style="margin-top: 30px;">
				<tr>
					<td width="15%" class="td_normal_title">
						<bean:message bundle="sys-help" key="sysHelpConfig.fdModuleName" />
					</td>
					<td width="85%">
						<xform:text property="fdModuleName" showStatus="edit" style="width:95%;" required="true"/>
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_normal_title">
						<bean:message bundle="sys-help" key="sysHelpConfig.fdModulePath" />
					</td>
					<td width="85%">
						<xform:text property="fdModulePath" showStatus="edit" style="width:95%;" required="true"/>
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_normal_title">
						<bean:message bundle="sys-help" key="sysHelpConfig.fdName" />
					</td>
					<td width="85%">
						<xform:text property="fdName" showStatus="edit" style="width:95%;" required="true"/>
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_normal_title">
						<bean:message bundle="sys-help" key="sysHelpConfig.fdUrl" />
					</td>
					<td width="85%">
						<xform:text property="fdUrl" showStatus="edit" style="width:95%;" required="true"/>
					</td>
				</tr>
			</table>
        </html:form>
		<script>
			$KMSSValidation();
		</script>
    </template:replace>
</template:include>