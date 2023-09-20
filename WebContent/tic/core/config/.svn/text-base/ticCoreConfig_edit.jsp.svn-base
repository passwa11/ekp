<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message bundle="tic-core" key="ticCore.config.cache"/></template:replace>
	<template:replace name="content">
	<br>
	<br>
		<h2 align="center">
			<span class="profile_config_title"><bean:message bundle="tic-core" key="ticCore.config.cache.jdbc"/></span>
		</h2>
		
		<html:form action="/tic/core/config/tic_core_config/ticCoreConfig.do">
			<center>
				<table class="tb_normal" width=95%>
	
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core" key="ticCore.config.cache.jdbc"/>
		</td>
		<td colspan="3" width="85%">
			<xform:radio property="jdbcCache">
				<xform:enumsDataSource enumsType="common_yesno" />
			</xform:radio>
		</td>
	</tr>
	<tr>
	<td colspan="2">
		<div><font size="3" color="red"><bean:message bundle="tic-core" key="ticCore.config.cache.tip"/></font></div>
	</td>
	</tr>
</table>
			</center>
			
			<center style="margin-top: 10px;">
			<!-- 保存 -->
			<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.ticCoreConfigForm, 'update');" order="1" ></ui:button>
			</center>
		</html:form>
		
	 	
	</template:replace>
</template:include>
