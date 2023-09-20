<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>

<c:set var="cateForm" value="${requestScope[param.formName] }"
	scope="page" />
<c:set var="readOnly" value="${param.readOnly eq 'true' }" scope="page" />
<c:set var="cateId" value="${param.cateId }" scope="page" />

<tr>
	<td colspan="4">
		<table id="personsTable" class="tb_normal" width="100%">
			<col width="10px" align="center">
			<col width="120px" align="center">
			<col width="120px" align="center">
			<col width="100px" align="center">
			<col width="100px" align="center">
			<col width="130px" align="center">
			<col width="150px" align="center">
			<col width="" align="center">
			<c:if test="${!readOnly }">
				<col width="80px" align="center">
			</c:if>
			<tr class="tr_normal_title">
				<td><span style="white-space: nowrap;"><bean:message
							key="page.serial" /></span></td>
				<td><bean:message bundle="sys-zone" key="zoneAddress.out.staffName" /></td>
				<td><bean:message bundle="sys-zone" key="zoneAddress.out.post" /></td>
				<td><bean:message bundle="sys-zone" key="zoneAddress.out.mobilePhone" /></td>
				<td><bean:message bundle="sys-zone" key="zoneAddress.out.workPhone" /></td>
				<td><bean:message bundle="sys-zone" key="zoneAddress.out.email" /></td>
				<td><bean:message bundle="sys-zone" key="zoneAddress.out.remarks" /></td>
				<c:if test="${!readOnly }">
					<td><a href="javascript:void(0);" class="com_btn_link"
						onclick="DocList_AddRow('personsTable');"><bean:message bundle="sys-zone" key="zoneAddress.add" /></a></td>
				</c:if>
			</tr>
			<c:if test="${!readOnly }">
				<%-- 模版行 --%>
				<tr style="display: none;" KMSS_IsReferRow="1">
					<td KMSS_IsRowIndex="1">!{index}</td>
					<td><xform:text property="cateRelations[!{index}].fdOrgName"
							style="width:80%" subject="姓名" required="true" showStatus="edit" /></td>
					<td><xform:text property="cateRelations[!{index}].fdOrgPost"
							style="width:95%" subject="职位" required="false" showStatus="edit" /></td>
					<td><xform:text property="cateRelations[!{index}].fdOrgPhone"
							style="width:95%" subject="手机" required="false" showStatus="edit" validators="phone" /></td>
					<td><xform:text
							property="cateRelations[!{index}].fdOrgWorkPhone"
							style="width:95%" subject="电话" required="false" showStatus="edit" /></td>
					<td><xform:text property="cateRelations[!{index}].fdOrgEmail"
							style="width:95%" subject="邮箱" required="false" showStatus="edit" /></td>
					<td><xform:text property="cateRelations[!{index}].fdOrgMemo"
							style="width:95%" subject="备注" required="false" showStatus="edit" /><input
						type="hidden" name="cateRelations[!{index}].fdId" value=""><input
						type="hidden" name="cateRelations[!{index}].fdOrgType"
						value="outer"><input type="hidden"
						name="cateRelations[!{index}].fdCategoryId" value="${cateId }"></td>
					<td>
						<div style="text-align: center">
							<img src="../../../resource/style/default/icons/delete.gif"
								alt="del"
								onclick="DocList_DeleteRow(this.parentNode.parentNode.parentNode);"
								style="cursor: pointer">&nbsp;&nbsp; <img
								src="../../../resource/style/default/icons/up.gif" alt="up"
								onclick="DocList_MoveRow(-1, this.parentNode.parentNode.parentNode);"
								style="cursor: pointer">&nbsp;&nbsp; <img
								src="../../../resource/style/default/icons/down.gif" alt="down"
								onclick="DocList_MoveRow(1, this.parentNode.parentNode.parentNode);"
								style="cursor: pointer">
						</div>
					</td>
				</tr>
			</c:if>
			<%-- 内容行 --%>
			<c:forEach items="${cateForm.cateRelations}" var="relation"
				varStatus="vstatus">
				<tr KMSS_IsContentRow="1">
					<td>${vstatus.index + 1}</td>
					<td><xform:text
							property="cateRelations[${vstatus.index}].fdOrgName"
							style="width:80%" subject="姓名" required="true"
							showStatus="${readOnly ? 'view' : 'edit' }"
							value="${relation.fdOrgName }" /></td>
					<td><xform:text
							property="cateRelations[${vstatus.index}].fdOrgPost"
							style="width:95%" subject="职位" required="false"
							showStatus="${readOnly ? 'view' : 'edit' }"
							value="${relation.fdOrgPost }" /></td>
					<td><xform:text
							property="cateRelations[${vstatus.index}].fdOrgPhone"
							style="width:95%" subject="手机" required="false" validators="phone"
							showStatus="${readOnly ? 'view' : 'edit' }"
							value="${relation.fdOrgPhone }" /></td>
					<td><xform:text
							property="cateRelations[${vstatus.index}].fdOrgWorkPhone"
							style="width:95%" subject="电话" required="false"
							showStatus="${readOnly ? 'view' : 'edit' }"
							value="${relation.fdOrgWorkPhone }" /></td>
					<td><xform:text
							property="cateRelations[${vstatus.index}].fdOrgEmail"
							style="width:95%" subject="邮箱" required="false"
							showStatus="${readOnly ? 'view' : 'edit' }"
							value="${relation.fdOrgEmail }" /></td>
					<td><xform:text
							property="cateRelations[${vstatus.index}].fdOrgMemo"
							style="width:95%" subject="备注" required="false"
							showStatus="${readOnly ? 'view' : 'edit' }"
							value="${relation.fdOrgMemo }" /><input type="hidden"
						name="cateRelations[${vstatus.index}].fdOrgType"
						value="${relation.fdOrgType }"><input type="hidden"
						name="cateRelations[${vstatus.index}].fdId"
						value="${relation.fdId }"><input type="hidden"
						name="cateRelations[${vstatus.index}].fdCategoryId"
						value="${relation.fdCategoryId }"></td>
					<c:if test="${!readOnly }">
						<td>
							<div style="text-align: center">
								<img src="../../../resource/style/default/icons/delete.gif"
									alt="del"
									onclick="DocList_DeleteRow(this.parentNode.parentNode.parentNode);"
									style="cursor: pointer">&nbsp;&nbsp; <img
									src="../../../resource/style/default/icons/up.gif" alt="up"
									onclick="DocList_MoveRow(-1, this.parentNode.parentNode.parentNode);"
									style="cursor: pointer">&nbsp;&nbsp; <img
									src="../../../resource/style/default/icons/down.gif" alt="down"
									onclick="DocList_MoveRow(1, this.parentNode.parentNode.parentNode);"
									style="cursor: pointer">
							</div>
						</td>
					</c:if>
				</tr>
			</c:forEach>
		</table> <script type="text/javascript">
			DocList_Info.push('personsTable');
		</script>
	</td>
</tr>
