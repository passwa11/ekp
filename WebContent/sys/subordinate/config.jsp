<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="head">
		<script language="JavaScript">
			Com_IncludeFile("doclist.js|dialog.js", null, "js");
			function changeSubordinateEnable() {
				var table_obj = document.getElementById("visible_setting"),
					isSubordinateEnable = $("input[name='isSubordinateEnable']").val();
				if (isSubordinateEnable == 'true') {
					table_obj.style.display = "";
					// 启用所有输入框
					$.each($("#visible_setting input"), function(i, n) {
						$(n).removeAttr("disabled");
					});
				} else {
					table_obj.style.display = "none";
					// 禁用所有输入框
					$.each($("#visible_setting input"), function(i, n) {
						$(n).attr("disabled", "disabled");
					});
				}
			}
			
			function changeAllLeaderSupport() {
				var table_tr = document.getElementById("allLeaderSupport_tr"),
					isAllLeaderSupport = $("input[name='_isAllLeaderSupport']");
				if (isAllLeaderSupport.is(':checked')) {
					table_tr.style.display = "";
				} else {
					table_tr.style.display = "none";
				}
			}

			LUI.ready(function() {
				changeSubordinateEnable();
				changeAllLeaderSupport();
			});
		</script>
	</template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title"><bean:message key="subordinate.config.title" bundle="sys-subordinate" /></span>
		</h2>
		<html:form action="/sys/subordinate/sysSubordinateMappingForm.do">
			<div align="left" style="margin-left: 20px;">
				<table id="visible_switch">
					<tr>
						<td>
							<ui:switch property="isSubordinateEnable" checked="${sysSubordinateMappingConfigForm.isSubordinateEnable}" onValueChange="changeSubordinateEnable()" enabledText="${lfn:message('sys-subordinate:subordinate.config.enabled.label')}" disabledText="${lfn:message('sys-subordinate:subordinate.config.enabled.label')}"></ui:switch>
						</td>
					</tr>
				</table>
			<div>

		    <div id="visible_setting" style="margin-left: 20px;">
		    	<table width=95%>
			    	<tr>
				    	<td>
				    		<div style="width: 95%" align="left">
					    		<br/>
					    		<label>
						    		<xform:checkbox property="isAllLeaderSupport" value="${sysSubordinateMappingConfigForm.isAllLeaderSupport}" showStatus="edit" onValueChange="changeAllLeaderSupport()">
						    			<xform:simpleDataSource value="true"><bean:message key="subordinate.config.isAllLeaderSupport.desc" bundle="sys-subordinate" /></xform:simpleDataSource>
						    		</xform:checkbox>
								</label>
							</div>
						</td>
					</tr>
					<tr id="allLeaderSupport_tr">
						<td>
							<div style="width: 100%" align="left">
								<br/>
								<input type="hidden" name="allLeaderSupportModelNames" value="${sysSubordinateMappingConfigForm.allLeaderSupportModelNames}">
								<textarea name="allLeaderSupportMessageKeys" rows="10" style="width:95%" readonly="true">${sysSubordinateMappingConfigForm.allLeaderSupportMessageKeys}</textarea>
								<a href="#" onclick="Dialog_Tree(true, 'allLeaderSupportModelNames', 'allLeaderSupportMessageKeys', null, 'modelTreeService&module=!{value}', '<bean:message key="subordinate.config.select.module" bundle="sys-subordinate" />', null, null, null);"><bean:message key="button.select"/></a>
								<br>
								<bean:message key="subordinate.config.isAllLeaderSupport.node" bundle="sys-subordinate" />
				    		</div>
				   		</td>
			   		</tr>
		   		</table>
		   		<br/><br/>
		   		<p><bean:message key="subordinate.config.fdSpecial.desc" bundle="sys-subordinate" /></p>
		       	<table id="TABLE_DocList" class="tb_normal" width=95% align="left">
					<tr>
						<td width="48%" align="center" class="td_normal_title"><bean:message key="subordinate.config.select.element" bundle="sys-subordinate" /></td>
						<td width="48%" align="center" class="td_normal_title"><bean:message key="subordinate.config.select.business" bundle="sys-subordinate" /></td>
						<td width="4%" align="center" class="td_normal_title">
							<img src='<c:url value="/resource/style/default/icons/add.gif" />' alt="<bean:message key="button.insert"/>" onclick="DocList_AddRow();" style="cursor:pointer">
						</td>
					</tr>
					<!--基准行-->
					<tr KMSS_IsReferRow="1" style="display:none">
						<td width="48%">
							<input type="hidden" name="sysSubordinateMappingFormList[!{index}].fdSpecialElementIds">
							<input readonly="true" validate="required" class="inputsgl" style="width:90%" name="sysSubordinateMappingFormList[!{index}].fdSpecialElementNames">
							<a href="#" onclick="Dialog_Address(true, 'sysSubordinateMappingFormList[!{index}].fdSpecialElementIds', 'sysSubordinateMappingFormList[!{index}].fdSpecialElementNames', null, ORG_TYPE_ALLORG);">
								<bean:message key="dialog.selectOrg"/>
							</a>
							<span class="txtstrong">*</span>
						</td>
						<td width="48%">
							<input type="hidden" name="sysSubordinateMappingFormList[!{index}].fdSpecialModelNames">
							<input readonly="true" validate="required" class="inputsgl" style="width:90%" name="sysSubordinateMappingFormList[!{index}].fdSpecialMessageKeys">
							<a href="#" onclick="Dialog_Tree(true, 'sysSubordinateMappingFormList[!{index}].fdSpecialModelNames', 'sysSubordinateMappingFormList[!{index}].fdSpecialMessageKeys', null, 'modelTreeService&module=!{value}', '<bean:message key="subordinate.config.select.module" bundle="sys-subordinate" />', null, null, null);">
								<bean:message key="dialog.selectOrg"/>
							</a>
							<span class="txtstrong">*</span>
						</td>
						<td width="4%">
							<center>
								<img src='<c:url value="/resource/style/default/icons/delete.gif" />' alt="<bean:message key="button.insert"/>" onclick="DocList_DeleteRow();" style="cursor:pointer">
							</center>
						</td>
					</tr>
					<!--内容行-->
					<c:forEach items="${sysSubordinateMappingConfigForm.sysSubordinateMappingFormList}" var="sysSubordinateMappingForm" varStatus="vstatus">
						<tr KMSS_IsContentRow="1">
							<td width="48%">
								<input type="hidden" name="sysSubordinateMappingFormList[${vstatus.index}].fdSpecialElementIds" value="${sysSubordinateMappingForm.fdSpecialElementIds}">
								<input class="inputsgl" validate="required" style="width:90%" name="sysSubordinateMappingFormList[${vstatus.index}].fdSpecialElementNames" readonly="true" value="${sysSubordinateMappingForm.fdSpecialElementNames}"/>
								<a href="#" onclick="Dialog_Address(true, 'sysSubordinateMappingFormList[${vstatus.index}].fdSpecialElementIds', 'sysSubordinateMappingFormList[${vstatus.index}].fdSpecialElementNames', null, ORG_TYPE_ALLORG);">
									<bean:message key="dialog.selectOrg"/>
								</a>
								<span class="txtstrong">*</span>
							</td>
							<td width="48%">
								<input type="hidden" name="sysSubordinateMappingFormList[${vstatus.index}].fdSpecialModelNames" value="${sysSubordinateMappingForm.fdSpecialModelNames}">
								<input class="inputsgl" validate="required" style="width:90%" name="sysSubordinateMappingFormList[${vstatus.index}].fdSpecialMessageKeys" readonly="true" value="${sysSubordinateMappingForm.fdSpecialMessageKeys}"/>
								<a href="#" onclick="Dialog_Tree(true, 'sysSubordinateMappingFormList[${vstatus.index}].fdSpecialModelNames', 'sysSubordinateMappingFormList[${vstatus.index}].fdSpecialMessageKeys', null, 'modelTreeService&module=!{value}', '<bean:message key="subordinate.config.select.module" bundle="sys-subordinate" />', null, null, null);">
									<bean:message key="dialog.selectOrg"/>
								</a>
								<span class="txtstrong">*</span>
							</td>
							
							<td width="4%">
								<center>
									<img src='<c:url value="/resource/style/default/icons/delete.gif" />' alt="<bean:message bundle="sys-organization" key="sysOrganizationVisible.del"/>" onclick="DocList_DeleteRow();" style="cursor:pointer">
									 </center>
							</td>
						</tr>
					</c:forEach>
				</table>
				<br/>
				<div style="width: 95%">
					<bean:message key="subordinate.config.tips" bundle="sys-subordinate" />
					<br>
					<bean:message key="subordinate.config.tips.node1" bundle="sys-subordinate" />
					<br>
					<bean:message key="subordinate.config.tips.node2" bundle="sys-subordinate" />
					<br>
					<bean:message key="subordinate.config.tips.node3" bundle="sys-subordinate" />
					<br>
					<bean:message key="subordinate.config.tips.node4" bundle="sys-subordinate" />
				</div>
			</div>
			<table width="100%">
				<tr>
					<td>
						<center style="margin: 10px;">
							<!-- 保存 -->
							<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysSubordinateMappingConfigForm, 'update');"></ui:button>
						</center>
					</td>
				</tr>
			</table>
		</html:form>
		
		<script language="JavaScript">
			$KMSSValidation(document.forms['sysSubordinateMappingConfigForm']);
		</script>
	</template:replace>
</template:include>
