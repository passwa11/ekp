<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit">
	<template:replace name="title">
		<c:if test="${sysTagGroupForm.method_GET == 'edit' }">
			<c:out value="${sysTagGroupForm.fdName }"></c:out> - ${lfn:message('button.edit')}
		</c:if>
		<c:if test="${sysTagGroupForm.method_GET == 'add' }">
			${lfn:message('button.add')} - ${lfn:message('sys-tag:table.sysTagGroup') }
		</c:if>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<c:choose>
				<c:when test="${ sysTagGroupForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.sysTagGroupForm, 'update');"></ui:button>
				</c:when>
				<c:when test="${ sysTagGroupForm.method_GET == 'add' }">	
					<ui:button text="${ lfn:message('button.save') }" onclick="Com_Submit(document.sysTagGroupForm, 'save');"></ui:button>
					<ui:button text="${ lfn:message('button.saveadd') }" onclick="Com_Submit(document.sysTagGroupForm, 'saveadd');"></ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
	
	<html:form action="/sys/tag/sys_tag_group/sysTagGroup.do">
		<p class="txttitle"><bean:message bundle="sys-tag" key="table.sysTagGroup"/></p>
		<center>
			<table class="tb_normal" width=90% style="min-width:980px"> 
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-tag" key="sysTagGroup.fdName"/>
				</td><td width="85%" colspan="3">
					<xform:text property="fdName" style="width:85%" /><a href="javascript:void(0);" onclick="selectModules(this);" class="com_btn_link ">
						${lfn:message('button.select')}
					</a>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-tag" key="sysTagGroup.fdModelName"/>
				</td><td width="85%" colspan="3">
					<xform:text property="fdModelName" style="width:85%" required="true" validators="maxLength(100)"/>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-tag" key="sysTagGroup.fdEnabled"/>
				</td><td width="35%">
					<xform:radio property="fdEnabled">
						<xform:enumsDataSource enumsType="common_yesno" />
					</xform:radio>
				</td>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-tag" key="sysTagGroup.fdOrder"/>
				</td><td width="35%">
					<xform:text property="fdOrder" style="width:85%" />
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-tag" key="sysTagGroup.fdDetails"/>
				</td>
				<td width="85%" colspan="3">
					 <%--配置明细  --%>
					 <table  class="tb_normal" width="100%" id="TABLE_DocList"
								 style="TABLE-LAYOUT: fixed;WORD-BREAK: break-all;">
							<tr>
								<td width="5%" KMSS_IsRowIndex="1" class="td_normal_title">
									<bean:message key="page.serial" />
								</td>
								<td width="35%" class="td_normal_title" align="center">${lfn:message('sys-tag:sysTagGroupDetail.fdName')}</td>
								<td width="50%" class="td_normal_title" align="center">${lfn:message('sys-tag:sysTagGroupDetail.fdCate')}</td>
								<td width="10%" align="center" class="td_normal_title">
									<img src="../../../resource/style/default/icons/add.gif" alt="add" onclick="addRow();" style="cursor:pointer">
								</td>
							</tr>
							
							<!--基准行-->
							<tr KMSS_IsReferRow="1" style="display:none">
								<td width="5%" KMSS_IsRowIndex="1"></td>
								<td width="35%" align="center">
									<xform:text style="width:95%" property="fdDetailForms[!{index}].fdName" required="true" validators="maxLength(200)"></xform:text>
								</td>
								<td width="50%" >
									<xform:dialog style="width:95%"  required="true" propertyId="fdDetailForms[!{index}].fdCateIds" propertyName="fdDetailForms[!{index}].fdCateNames">
									Dialog_List(true, 'fdDetailForms[!{index}].fdCateIds', 'fdDetailForms[!{index}].fdCateNames', ';', 'sysTagCategorTreeService',null,null,null,null,'<bean:message key="sysTagTags.fdCategoryId" bundle="sys-tag"/>')
									</xform:dialog>
								</td>
								<td align="center" width="10%">
									<div style="white-space:nowrap;min-width:65px">
										<a href="javascript:void(0);" onclick="DocList_DeleteRow();" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/delete.gif" border="0"  title="<bean:message key="button.delete"/>"/></a>
										<a href="javascript:void(0);" onclick="DocList_MoveRow(-1);" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/up.gif" border="0"  title="<bean:message key="button.moveup"/>"/></a>
										<a href="javascript:void(0);" onclick="DocList_MoveRow(1);" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/down.gif" border="0"  title="<bean:message key="button.movedown"/>"/></a>
									</div>
								</td>
							</tr>
							
							<!--内容行-->
							<c:if test="${sysTagGroupForm.method_GET=='edit'}">
								<c:forEach items="${sysTagGroupForm.fdDetailForms}" 
										var="itemForm" varStatus="vstatus">
									<tr KMSS_IsContentRow="1">
										<td width="5%" KMSS_IsRowIndex="1"
											 id="KMSS_IsRowIndex_Edit">${vstatus.index+1}</td>
										<td width="35%"  align="center">
											<xform:text style="width:95%" value="${itemForm.fdName }" 
														validators="maxLength(200)"
														property="fdDetailForms[${vstatus.index}].fdName" required="true" />
										</td>
										<td width="50%">
											<xform:dialog propertyId="fdDetailForms[${vstatus.index}].fdCateIds"  
														 propertyName="fdDetailForms[${vstatus.index}].fdCateNames"
														 idValue="${itemForm.fdCateIds }"
														 nameValue="${itemForm.fdCateNames }"
														 required="true"
														 style="width:95%">
											Dialog_List(true, 'fdDetailForms[${vstatus.index}].fdCateIds', 'fdDetailForms[${vstatus.index}].fdCateNames', ';', 'sysTagCategorTreeService',null,null,null,null,'<bean:message key="sysTagTags.fdCategoryId" bundle="sys-tag"/>')
											</xform:dialog>
										</td>
										<td align="center" width="10%" style="white-space: nowrap;">
											<div style="white-space:nowrap;min-width:65px">
												<a href="javascript:void(0);" 
													onclick="DocList_DeleteRow();"
												 style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/delete.gif" border="0" title="<bean:message key="button.delete"/>" /></a>
												<a href="javascript:void(0);" 
													onclick="DocList_MoveRow(-1);" 
												 style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/up.gif" border="0" title="<bean:message key="button.moveup"/>" /></a>
												<a href="javascript:void(0);" 
													onclick="DocList_MoveRow(1);" 
												 style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/down.gif" border="0" title="<bean:message key="button.movedown"/>" /></a>
											</div>
										</td>
									</tr>
								</c:forEach>
							</c:if>
						</table>
					 
					 <%--配置明细 结束 --%>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-tag" key="sysTagGroup.authEditors"/>
				</td>
				<td width="85%" colspan="3">
					<xform:address propertyId="authEditorIds" propertyName="authEditorNames" mulSelect="true" orgType="ORG_TYPE_ALL" textarea="true" style="width:85%" />
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-tag" key="sysTagGroup.docCreator"/>
				</td><td width="35%">
					<xform:address propertyId="docCreatorId" 
					propertyName="docCreatorName" orgType="ORG_TYPE_PERSON" showStatus="view" />
				</td>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-tag" key="sysTagGroup.fdCreateTime"/>
				</td><td width="35%">
					<xform:datetime property="fdCreateTime" showStatus="view" />
				</td>
			</tr>
			</table>
		</center>
		<html:hidden property="docCreatorId"/>
		<html:hidden property="fdCreateTime"/>
		<html:hidden property="fdId" />
		<html:hidden property="method_GET" />
		<script>
			Com_IncludeFile("dialog.js|doclist.js");
			$KMSSValidation();
		</script>
		<script src="${LUI_ContextPath}/sys/tag/sys_tag_group/js/edit.js"></script>
		<script>
			function addRow() {
				 DocList_AddRow('TABLE_DocList', null);
			}
		</script>
	</html:form>
	</template:replace>
</template:include>