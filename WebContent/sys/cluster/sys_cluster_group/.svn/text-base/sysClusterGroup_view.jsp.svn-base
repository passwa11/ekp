<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>

<!-- 子系统信息 查看页 -->
<div id="optBarDiv">
    <!-- 编辑按钮 -->
	<input type="button" value="<bean:message key="button.edit"/>" onclick="Com_OpenWindow('sysClusterGroup.do?method=edit&fdId=${JsParam.fdId}','_self');">
	<!-- 关闭按钮 -->
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-cluster" key="table.sysClusterGroup"/></p>

<center>
<table class="tb_normal" width="600px">
	<tr>
		<td class="td_normal_title" width="25%">
			<bean:message bundle="sys-cluster" key="sysClusterGroup.fdName"/>
		</td><td width="75%">
			<xform:text property="fdName" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title">
			<bean:message bundle="sys-cluster" key="sysClusterGroup.fdKey"/>
		</td><td>
			<xform:text property="fdKey" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title">
			<bean:message bundle="sys-cluster" key="sysClusterGroup.fdUrl"/>
		</td><td>
			<xform:text property="fdUrl" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title">
			<bean:message bundle="sys-cluster" key="sysClusterGroup.fdOrder"/>
		</td><td>
			<xform:text property="fdOrder" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title">
			<bean:message bundle="sys-cluster" key="sysClusterGroup.fdMaster"/>
		</td><td>
			<xform:radio property="fdMaster">
				<xform:enumsDataSource enumsType="common_yesno" />
			</xform:radio>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title">
			<bean:message bundle="sys-cluster" key="sysClusterGroup.fdLocal"/>
		</td><td>
			<xform:radio property="fdLocal">
				<xform:enumsDataSource enumsType="common_yesno" />
			</xform:radio>
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>