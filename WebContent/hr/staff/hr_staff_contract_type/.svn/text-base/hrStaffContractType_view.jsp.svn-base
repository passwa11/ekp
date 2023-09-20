<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
	<script>
		function confirmDelete(msg){
			var del = confirm("<bean:message key="page.comfirmDelete"/>");
			return del;
		}
		Com_IncludeFile("data.js|dialog.js");
	</script>
	<div id="optBarDiv">
		<input type="button" value="<bean:message key="button.edit"/>" onclick="Com_OpenWindow('hrStaffContractType.do?method=edit&fdId=${JsParam.fdId}','_self');">
		<input type="button" value="<bean:message key="button.delete"/>" onclick="if(!confirmDelete())return;Com_OpenWindow('hrStaffContractType.do?method=delete&fdId=${JsParam.fdId}','_self');">
		<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
	</div>
	<p class="txttitle"><bean:message bundle="hr-staff" key="table.hrStaffContractType"/></p>
	<center>
		<html:hidden name="hrStaffContractTypeForm" property="fdId"/>
		<table id="Label_Tabel" width=95%>
			<tr LKS_LabelName="基本信息">
				<td>
					<table class="tb_normal" width=95%>
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message  bundle="hr-staff" key="hrStaffContractType.fdOrder"/>
							</td><td width=35%>
								<c:out value="${hrStaffContractTypeForm.fdOrder}" />
							</td>
								<td class="td_normal_title" width=15%>
								<bean:message  bundle="hr-staff" key="hrStaffContractType.fdName"/>
							</td><td width=35%>
								<c:out value="${hrStaffContractTypeForm.fdName}" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<!-- 打印机制 -->
			<c:import url="/sys/print/include/sysPrintTemplate_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="hrStaffContractTypeForm" />
				<c:param name="fdKey" value="hrStaffPersonExperienceContract" />
				<c:param name="modelName" value="com.landray.kmss.hr.staff.model.HrStaffPersonExperienceContract"></c:param>
				<c:param name="templateModelName" value="com.landray.kmss.hr.staff.model.HrStaffContractType"></c:param>
				<c:param name="usePrintTemplate" value="是否启用打印模板"></c:param>
				<c:param name="fdModelTemplateId" value="${hrStaffContractTypeForm.fdId}"></c:param>
			</c:import>
		</table>
	</center>
<%@ include file="/resource/jsp/view_down.jsp"%>