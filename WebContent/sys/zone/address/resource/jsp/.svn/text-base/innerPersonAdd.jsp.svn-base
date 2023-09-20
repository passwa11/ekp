<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>

<c:set var="readOnly" value="${param.readOnly eq 'true' }" scope="page" />
<c:set var="relationForm" value="${requestScope[param.formName] }"
	scope="page" />
<c:set var="readOnly" value="${param.readOnly eq 'true' }" scope="page" />
<c:set var="cateId" value="${param.cateId }" scope="page" />
<div id="popLayer"></div>
<tr>
	<td class="td_normal_title" style="width: 15%"><bean:message bundle="sys-zone" key="zoneAddress.itemPeople" /></td>
	<td colspan="3">
		<table id="personsTable" class="tb_normal" width="100%">
			<col width="10px" align="center">
			<col width="160px" align="center">
			<col width="" align="center">
			<c:if test="${!readOnly }">
				<col width="100px" align="center">
			</c:if>
			<tr class="tr_normal_title">
				<td><span style="white-space: nowrap;"><bean:message
							key="page.serial" /></span></td>
				<td><bean:message bundle="sys-zone" key="zoneAddress.staff" /></td>
				<td><bean:message bundle="sys-zone" key="zoneAddress.remarks" /></td>
				<c:if test="${!readOnly }">
					<td><a href="javascript:void(0);" class="com_btn_link"
						onclick="DocList_AddRow('personsTable');"><bean:message bundle="sys-zone" key="zoneAddress.add" /></a>
						<a href="javascript:void(0);" class="com_btn_link"
						onclick="popBox();"><bean:message bundle="sys-zone" key="zoneAddress.bulkAdd" /></a>
						</td>
					
				</c:if>
			</tr>
			<c:if test="${!readOnly }">
				<%-- 模版行 --%>
				<tr style="display: none;" KMSS_IsReferRow="1">
					<td KMSS_IsRowIndex="1">!{index}</td>
					<td><xform:address textarea="false" mulSelect="false"
							subject="人员" propertyId="cateRelations[!{index}].fdOrgId"
							propertyName="cateRelations[!{index}].fdOrgName"
							style="width:90%;" orgType="8" required="true" showStatus="edit"></xform:address></td>
					<td><xform:text property="cateRelations[!{index}].fdOrgMemo"
							style="width:95%" subject="备注" required="false" showStatus="edit" /><input
						type="hidden" name="cateRelations[!{index}].fdId" value=""><input
						type="hidden" name="cateRelations[!{index}].fdOrgType"
						value="inner"><input type="hidden"
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
			<c:forEach items="${relationForm.cateRelations}" var="relation"
				varStatus="vstatus">
				<tr KMSS_IsContentRow="1">
					<td>${vstatus.index + 1}</td>
					<td><xform:address textarea="false" mulSelect="false"
							propertyId="cateRelations[${vstatus.index}].fdOrgId"
							propertyName="cateRelations[${vstatus.index}].fdOrgName"
							style="width:90%;" orgType="8" required="true"></xform:address></td>
					<td><xform:text
							property="cateRelations[${vstatus.index}].fdOrgMemo"
							style="width:95%" subject="备注" required="false"
							showStatus="${readOnly ? 'view' : 'edit' }" /><input
						type="hidden" name="cateRelations[${vstatus.index}].fdOrgType"
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
