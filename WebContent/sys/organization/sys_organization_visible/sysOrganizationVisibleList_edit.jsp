<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="head">
		<script>
			Com_IncludeFile("doclist.js|dialog.js", null, "js");
			
			function changeOrgAeraEnable(){
				var table_obj = document.getElementById("visible_switch");
				var visible_setting = document.getElementById("visible_setting"),
					help_link = document.getElementById('helpLink');
				if($("input[name='isOrgAeraEnable']").val() == 'false'){
					table_obj.style.display = "";
					if($("input[name='isOrgVisibleEnable']").val() == 'true'){
						visible_setting.style.display = "";
						help_link.style.display = '';				
					}
				}else if($("input[name='isOrgAeraEnable']").val() == 'true'){
					table_obj.style.display = "none";
					visible_setting.style.display = "none";
					help_link.style.display = 'none';
				}
			}

			function changeOrgVisibleEnable(){
				var table_obj = document.getElementById("visible_setting"),
					help_link = document.getElementById('helpLink');
				if($("input[name='isOrgVisibleEnable']").val() == 'true' && $("input[name='isOrgAeraEnable']").val() != 'true'){
					table_obj.style.display = "";
					help_link.style.display = '';
				}else{
					table_obj.style.display = "none";
					help_link.style.display = 'none';
				}
			}
			
			function validate(){
				var table_obj = document.getElementById("TABLE_DocList");
				var input_objs = table_obj.getElementsByTagName("input");
				for(var i=0;i<input_objs.length;i++){
					input_obj = input_objs[i];
					//debugger;
					if(input_obj.value==''){
						alert(i + "不能为空");
						return false;
					}
				}
				return true;
			}
			
			LUI.ready(function() {
				changeOrgAeraEnable();
				changeOrgVisibleEnable();
			});
		</script>
	</template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title"><bean:message bundle="sys-organization" key="sysOrganizationVisible.config" /></span>
		</h2>
		<html:form action="/sys/organization/sys_organization_visible/sysOrganizationVisibleList.do">
			
			<%
			if(ISysAuthConstant.IS_AREA_ENABLED){
				// 启用地址本数据在用户场景下隔离
			%>
			<div  align="left" style="margin-left: 20px;">
			<table>
				<tr>
					<td>
						<ui:switch property="isOrgAeraEnable" checked="${sysOrganizationVisibleListForm.isOrgAeraEnable }" onValueChange="changeOrgAeraEnable();" enabledText="${lfn:message('sys-organization:sysOrganizationVisible.isOrgAeraEnable')}" disabledText="${lfn:message('sys-organization:sysOrganizationVisible.isOrgAeraEnable')}"></ui:switch>
					</td>
					<td>
						
					</td>
				</tr>
			</table>
			</div>
			<br>
			<%}%>

			<div align="left" style="margin-left: 20px;">
				<table id="visible_switch">
					<tr>
						<td>
							<ui:switch property="isOrgVisibleEnable" checked="${sysOrganizationVisibleListForm.isOrgVisibleEnable }" onValueChange="changeOrgVisibleEnable();" enabledText="${lfn:message('sys-organization:sysOrganizationVisible.enable')}" disabledText="${lfn:message('sys-organization:sysOrganizationVisible.enable')}"></ui:switch>
						</td>
						<td>
							<a id='helpLink' href="sysOrganizationVisible_help.jsp" target="_blank" ><font color="red">${lfn:message('sys-organization:sysOrganization.help')}</font></a>
						</td>
					</tr>
				</table>
			<div>

		    <div id="visible_setting">
		    	<table width=95% style="margin-left: 1px;">
			    	<tr>
				    	<td>
					   		<div width="95%" style="width: 95%" align="center">
					    		<div style="width: 95%" align="left">
						    		<br/>
									<bean:message bundle="sys-organization" key="sysOrganizationVisible.defaultVisibleLevel.prefix"/>
									<xform:text property="defaultVisibleLevel" showStatus="edit" style="width:30px;" validators="digits min(0)" value="${sysOrganizationVisibleListForm.defaultVisibleLevel }"></xform:text><bean:message bundle="sys-organization" key="sysOrganizationVisible.defaultVisibleLevel.suffix"/>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div width="95%" style="width: 95%" align="center">
								<div style="width: 95%" align="left">
									<br/>
									<bean:message bundle="sys-organization" key="sysOrganizationVisible.visible.prompt"/>
					    		</div>
					   		</div>
				   		</td>
			   		</tr>
		   		</table>
		   		<br/>
		       	<table id="TABLE_DocList" class="tb_normal" width=95% style="margin-left: 20px;">
					<tr>
						<td width="48%" align="center" class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrganizationVisible.visiblePrincipals"/></td>
						<td width="48%" align="center" class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrganizationVisible.visibleSubordinates"/></td>
						<td width="4%" align="center" class="td_normal_title">
							<img src="../../../resource/style/default/icons/add.gif" alt="<bean:message bundle="sys-organization" key="sysOrganizationVisible.add"/>" onclick="DocList_AddRow();" style="cursor:pointer">
						</td>
					</tr>
					<!--基准行-->
					<tr KMSS_IsReferRow="1" style="display:none">
						<td width="48%">
							<input type="hidden" name="sysOrganizationVisibleFormList[!{index}].visiblePrincipalIds">
							<input readonly="true" validate="required" class="inputsgl" style="width:90%" name="sysOrganizationVisibleFormList[!{index}].visiblePrincipalNames">
							<a href="#" onclick="Dialog_Address(true, 'sysOrganizationVisibleFormList[!{index}].visiblePrincipalIds', 'sysOrganizationVisibleFormList[!{index}].visiblePrincipalNames', null, ORG_TYPE_ALLORG);">
								<bean:message key="dialog.selectOrg"/>
							</a>
							<span class="txtstrong">*</span>
						</td>
						<td width="48%">
							<input type="hidden" name="sysOrganizationVisibleFormList[!{index}].visibleSubordinateIds">
							<input readonly="true" validate="required" class="inputsgl" style="width:90%" name="sysOrganizationVisibleFormList[!{index}].visibleSubordinateNames">
							<a href="#" onclick="Dialog_Address(true, 'sysOrganizationVisibleFormList[!{index}].visibleSubordinateIds', 'sysOrganizationVisibleFormList[!{index}].visibleSubordinateNames', null, 3);">
								<bean:message key="dialog.selectOrg"/>
							</a>
							<span class="txtstrong">*</span>
						</td>
						<td width="4%">
							<center>
								<img src="../../../resource/style/default/icons/delete.gif" alt="<bean:message bundle="sys-organization" key="sysOrganizationVisible.del"/>" onclick="DocList_DeleteRow();" style="cursor:pointer">
							</center>
						</td>
					</tr>
					<!--内容行-->
					<c:forEach items="${sysOrganizationVisibleListForm.sysOrganizationVisibleFormList}" var="sysOrganizationVisibleFormList" varStatus="vstatus">
						<tr KMSS_IsContentRow="1">
							<td width="48%">
								<input type="hidden" name="sysOrganizationVisibleFormList[${vstatus.index}].visiblePrincipalIds" value="${sysOrganizationVisibleFormList.visiblePrincipalIds }">
								<input class="inputsgl" validate="required" style="width:90%" name="sysOrganizationVisibleFormList[${vstatus.index}].visiblePrincipalNames" readonly="true" value="${sysOrganizationVisibleFormList.visiblePrincipalNames }"/>
								<a href="#" onclick="Dialog_Address(true, 'sysOrganizationVisibleFormList[${vstatus.index}].visiblePrincipalIds', 'sysOrganizationVisibleFormList[${vstatus.index}].visiblePrincipalNames', null, ORG_TYPE_ALLORG);">
									<bean:message key="dialog.selectOrg"/>
								</a>
								<span class="txtstrong">*</span>
							</td>
							<td width="48%">
								<input type="hidden" name="sysOrganizationVisibleFormList[${vstatus.index}].visibleSubordinateIds" value="${sysOrganizationVisibleFormList.visibleSubordinateIds}">
								<input class="inputsgl" validate="required" style="width:90%" name="sysOrganizationVisibleFormList[${vstatus.index}].visibleSubordinateNames" readonly="true" value="${sysOrganizationVisibleFormList.visibleSubordinateNames }"/>
								<a href="#" onclick="Dialog_Address(true, 'sysOrganizationVisibleFormList[${vstatus.index}].visibleSubordinateIds', 'sysOrganizationVisibleFormList[${vstatus.index}].visibleSubordinateNames', null, 3);">
									<bean:message key="dialog.selectOrg"/>
								</a>
								<span class="txtstrong">*</span>
							</td>
							
						
							<td width="4%">
								<center>
									<img src="../../../resource/style/default/icons/delete.gif" alt="<bean:message bundle="sys-organization" key="sysOrganizationVisible.del"/>" onclick="DocList_DeleteRow();" style="cursor:pointer">
									 </center>
							</td>
						</tr>
					</c:forEach>
				</table>
		
				<br/>
				<div width="95%" style="width: 95%" align="center">
					<div style="width: 95%" align="left">
						<bean:message bundle="sys-organization" key="sysOrganizationVisible.visible.range.prompt"/>
						<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message bundle="sys-organization" key="sysOrganizationVisible.visible.ROLE_SYSORG_ORG_ADMIN.prompt"/>
						<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message bundle="sys-organization" key="sysOrganizationVisible.visible.ROLE_SYSORG_DIALOG_USER.prompt"/>
					</div>
					<br/>
					<div style="width: 95%" align="left">
						<font color="red"><bean:message bundle="sys-organization" key="sysOrganizationVisible.visible.edit.prompt"/></font>
						<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<font color="red"><bean:message bundle="sys-organization" key="sysOrganizationVisible.visible.size.prompt"/></font>
					</div>
				</div>	
			</div>
			
			<table width="100%">
				<tr>
					<td>
						<center style="margin-top: 10px;">
						<kmss:auth requestURL="/sys/organization/sys_organization_visible/sysOrganizationVisibleList.do?method=update">
							<!-- 保存 -->
							<ui:button text="${lfn:message('button.save')}" suspend="bottom" height="35" width="120" onclick="Com_Submit(document.sysOrganizationVisibleListForm, 'update');"></ui:button>
						</kmss:auth>
						</center>
					</td>
				</tr>
			</table>
			
		</html:form>
		<script language="JavaScript">
			$KMSSValidation(document.forms['sysOrganizationVisibleListForm']);
		</script>
	</template:replace>
</template:include>
