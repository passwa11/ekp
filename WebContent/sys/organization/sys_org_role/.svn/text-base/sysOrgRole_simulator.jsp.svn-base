<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<template:include ref="config.edit" sidebar="no">
	<template:replace name="head">
		<script>
			Com_IncludeFile("dialog.js|jquery.js");
			seajs.use(['sys/ui/js/dialog'], function(dialog) {
				window.dialog = dialog;
			});
		</script>
		<script type="text/javascript">
			function selectOrgs(){
				
				var fields = document.getElementsByName("fdIsMultiple");
				var multi = fields[0].checked;
				var selectType = 0;
				fields = document.getElementsByName("fdOrgType");
				for(var i=0; i<fields.length; i++){
					if(fields[i].checked)
						selectType |= parseInt(fields[i].value);
				}
				Dialog_Address(multi, 'fdOrgIds', 'fdOrgNames', ';', selectType, null, null, null, null, null, null, null, null);
			}
			function startCalculate(){
				var orgIds = document.getElementsByName("fdOrgIds")[0].value;
				if(orgIds==""){
					window.dialog.alert("<bean:message bundle="sys-organization" key="sysOrgRoleConf.simulator.orgListNotNull"/>");
					return;
				}
				var fdUserId = document.getElementsByName("fdUserId")[0].value;
				if(fdUserId==""){
					window.dialog.alert("<bean:message bundle="sys-organization" key="sysOrgRoleConf.simulator.orgUserNotNull"/>");
					return;
				}
				$(TD_Result).text("");
				var kmssdata = new KMSSData();
				kmssdata.AddHashMap({orgIds:orgIds, userId:fdUserId});
				kmssdata.SendToBean("sysOrgRoleSimulator", function(rtnData){
					if(rtnData==null)
						return;
					var rtnVal = rtnData.GetHashMapArray();
					if(rtnVal.length==0)
						return;
					$(TD_Result).html(rtnVal[0].message);
				});
			}
		</script>
	</template:replace>	
	<template:replace name="content">
		<p class="txttitle"><bean:message bundle="sys-organization" key="sysOrgRoleConf.simulator.title"/></p>
		<center>
		<table class="tb_normal" width=95%>
					
		<tr>
			<!-- 人员列表 -->
			<td class="td_normal_title" width=20%>
				<bean:message bundle="sys-organization" key="sysOrgRoleConf.simulator.orgList"/>
			</td>
			<td width="20%" colspan="3">
				<xform:dialog dialogJs="selectOrgs();" icon="orgelement" propertyId="fdOrgIds" propertyName="fdOrgNames" textarea="true" showStatus="edit" style="width:98%">
				</xform:dialog>
				
					<br>
					<label><input type="checkbox" name="fdIsMultiple" value="1" checked/><bean:message  bundle="sys-organization" key="sysOrgRole.fdIsMultiple"/></label>
					<label><input type="checkbox" name="fdOrgType" value="1" checked/><bean:message  bundle="sys-organization" key="sysOrgElement.org"/></label>
					<label><input type="checkbox" name="fdOrgType" value="2" checked/><bean:message  bundle="sys-organization" key="sysOrgElement.dept"/></label>
					<label><input type="checkbox" name="fdOrgType" value="4" checked/><bean:message  bundle="sys-organization" key="sysOrgElement.post"/></label>
					<label><input type="checkbox" name="fdOrgType" value="8" checked/><bean:message  bundle="sys-organization" key="sysOrgElement.person"/></label>
					<label><input type="checkbox" name="fdOrgType" value="16" checked/><bean:message  bundle="sys-organization" key="sysOrgElement.group"/></label>
					<label><input type="checkbox" name="fdOrgType" value="32" checked/><bean:message  bundle="sys-organization" key="table.common.sysOrgRole"/></label>
			</td>
			</tr>
		
			<tr>
				<td class="td_normal_title" width=20%>
					<bean:message  bundle="sys-organization" key="sysOrgRoleConf.simulator.user"/>
				</td><td width=80%>
					<input name="fdUserId" type="hidden" value="<%= UserUtil.getUser().getFdId() %>">
					<input name="fdUserName" class="inputsgl" readonly value="<%= UserUtil.getUser().getFdName() %>">
					<a href="#" onclick="Dialog_Address(false, 'fdUserId', 'fdUserName', ';', ORG_TYPE_ALL, null, null, null, true, null, null, null, null);">
						<bean:message key="dialog.selectOrg"/> 
					</a>
			   		<ui:button text="${lfn:message('sys-organization:sysOrgRoleConf.simulator.calculate')}" onclick="startCalculate();"></ui:button>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=20%>
					<bean:message bundle="sys-organization" key="sysOrgRoleConf.simulator.result"/>
				</td><td width=80% id="TD_Result"></td>
			</tr>
			<tr>
				<td class="td_normal_title" width=20%>
					<bean:message bundle="sys-organization" key="sysOrgRoleConf.simulator.help"/>
				</td><td width=80%>
					<bean:message  bundle="sys-organization" key="sysOrgRoleConf.simulator.help.text1"/><br><br>
					<bean:message  bundle="sys-organization" key="sysOrgRoleConf.simulator.help.text2"/><br><br>
					<bean:message  bundle="sys-organization" key="sysOrgRoleConf.simulator.help.text3"/><br>
					1）<bean:message bundle="sys-organization" key="sysOrgRole.common.location.person"/><br>
					<bean:message  bundle="sys-organization" key="sysOrgRoleConf.simulator.help.text4"/><br>
					<bean:message  bundle="sys-organization" key="sysOrgRoleConf.simulator.help.text5"/><br>
					2）<bean:message bundle="sys-organization" key="sysOrgRole.common.location.dept"/><br>
					<bean:message  bundle="sys-organization" key="sysOrgRoleConf.simulator.help.text6"/><br>
					<bean:message  bundle="sys-organization" key="sysOrgRoleConf.simulator.help.text7"/><br>
					<bean:message  bundle="sys-organization" key="sysOrgRoleConf.simulator.help.text8"/>
				</td>
			</tr>
		</table>
		</center>
	</template:replace>
</template:include>
