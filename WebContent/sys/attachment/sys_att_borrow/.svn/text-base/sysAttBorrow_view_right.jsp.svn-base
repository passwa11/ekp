<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>

<template:include ref="lbpm.right" sidebar="auto" formName="sysAttBorrowForm"
	formUrl="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_borrow/sysAttBorrow.do">
	<%-- 标题 --%>
	<template:replace name="title">
		<c:out value="${sysAttBorrowForm.docSubject }"></c:out>
	</template:replace>
	<%-- 按钮栏 --%>
	<template:replace name="toolbar">

		<link
			href="${ LUI_ContextPath}/sys/attachment/sys_att_borrow/resource/style/edit.css?s_cache=${ LUI_Cache }"
			rel="stylesheet">

		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<ui:button text="${lfn:message('button.close') }" order="5"
				onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>

	</template:replace>
	<%-- 路径 --%>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav">
			<ui:menu-item text="${ lfn:message('home.home') }"
				icon="lui_icon_s_home">
			</ui:menu-item>
			<ui:menu-item
				text="${ lfn:message('sys-attachment-borrow:table.sysAttBorrow') }">
			</ui:menu-item>
		</ui:menu>
	</template:replace>
	<%-- 内容 --%>
	<template:replace name="content">

		<html:hidden property="fdId" />
		<html:hidden property="docStatus" />


		<ui:tabpage collapsed="true" id="reviewTabPage">

			<ui:content
				title="${lfn:message('sys-attachment-borrow:sysAttBorrow.title') }">
				<table class="tb_normal" width=100%>
					<tr>
						<td class="td_normal_title" width=15%>${lfn:message("sys-attachment-borrow:sysAttBorrow.docSubject") }</td>
						<td colspan=3>
							<div>
								<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp"
									charEncoding="UTF-8">
									<c:param name="formBeanName" value="sysAttBorrowForm" />
									<c:param name="fdKey" value="${fdKey }" />
								</c:import>
							</div>
						</td>
					</tr>

					<tr>
						<td class="td_normal_title" width=15%>
							${lfn:message("sys-attachment-borrow:sysAttBorrow.fdDuration") }</td>
						<td width="35%"><xform:text property="fdDuration"
								style="width:50%" />${lfn:message("sys-attachment-borrow:attachment.borrow.days") }</td>

						<td class="td_normal_title" width=15%>
							${lfn:message("sys-attachment-borrow:sysAttBorrow.fdBorrowEffectiveTime") }</td>
						<td width="35%"><xform:datetime dateTimeType="date"
								property="fdBorrowEffectiveTime" style="width:50%"></xform:datetime>
					</tr>

					<tr>
						<%-- 摘要 --%>
						<td class="td_normal_title" width=15%>${lfn:message("sys-attachment-borrow:sysAttBorrow.fdReason") }</td>
						<td width="85%" colspan="3"><xform:textarea
								property="fdReason" style="width:97%" /></td>
					</tr>

					<tr>
						<!-- 借阅人 -->
						<td class="td_normal_title" width=15%>${lfn:message("sys-attachment-borrow:sysAttBorrow.fdBorrowers") }</td>
						<td width="85%" colspan=3><xform:address textarea="true"
								mulSelect="true" propertyId="fdBorrowerIds"
								propertyName="fdBorrowerNames" style="width:97%;height:90px;"></xform:address></td>
					</tr>

					<tr>
						<!-- 借阅权限 -->
						<td class="td_normal_title" width=15%>${lfn:message("sys-attachment-borrow:sysAttBorrow.fdAuth") }</td>
						<td width="85%" colspan=3 class="luiAttBorrorAuth">
							<div>${lfn:message("sys-attachment-borrow:sysAttBorrow.fdReadEnable") }<ui:switch
									property="fdReadEnable" disabledText="${lfn:message('sys-attachment-borrow:sysAttBorrow.fdReadDisable') }" enabledText="${lfn:message('sys-attachment-borrow:sysAttBorrow.fdReadEnabled') }"
									showType="show"></ui:switch>
							</div>
							<div>${lfn:message("sys-attachment-borrow:sysAttBorrow.fdDownloadEnable") }<ui:switch
									property="fdDownloadEnable" disabledText="${lfn:message('sys-attachment-borrow:sysAttBorrow.fdReadDisable') }" enabledText="${lfn:message('sys-attachment-borrow:sysAttBorrow.fdReadEnabled') }"
									showType="show"></ui:switch>
							</div>
							<div>${lfn:message("sys-attachment-borrow:sysAttBorrow.fdCopyEnable") }<ui:switch
									property="fdCopyEnable" disabledText="${lfn:message('sys-attachment-borrow:sysAttBorrow.fdReadDisable') }" enabledText="${lfn:message('sys-attachment-borrow:sysAttBorrow.fdReadEnabled') }"
									showType="show"></ui:switch>
							</div>
							<div>${lfn:message("sys-attachment-borrow:sysAttBorrow.fdPrintEnable") }<ui:switch
									property="fdPrintEnable" disabledText="${lfn:message('sys-attachment-borrow:sysAttBorrow.fdReadDisable') }" enabledText="${lfn:message('sys-attachment-borrow:sysAttBorrow.fdReadEnabled') }"
									showType="show"></ui:switch>
							</div>
						</td>
					</tr>

				</table>

			</ui:content>

		</ui:tabpage>

		<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop"
			var-count="5" var-average='false' var-useMaxWidth='true'>

			<c:choose>
				<c:when
					test="${sysAttBorrowForm.docStatus>='30' || sysAttBorrowForm.docStatus=='00'}">
					<c:import url="/sys/workflow/import/sysWfProcess_view.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="sysAttBorrowForm" />
						<c:param name="fdKey" value="sysAttBorrow" />
						<c:param name="showHistoryOpers" value="true" />
						<c:param name="isExpand" value="false" />
						<c:param name="approveType" value="right" />
						<c:param name="needInitLbpm" value="true" />
					</c:import>
				</c:when>
				<c:otherwise>
					<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="sysAttBorrowForm" />
						<c:param name="fdKey" value="sysAttBorrow" />
						<c:param name="showHistoryOpers" value="true" />
						<c:param name="isExpand" value="true" />
						<c:param name="approveType" value="right" />
					</c:import>
				</c:otherwise>

			</c:choose>

		</ui:tabpanel>

	</template:replace>

	<template:replace name="barRight">
		<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">

			<c:choose>
				<c:when
					test="${sysAttBorrowForm.docStatus>='30' || sysAttBorrowForm.docStatus=='00'}">
				</c:when>

				<c:otherwise>
					<%-- 流程 --%>
					<c:import url="/sys/workflow/import/sysWfProcess_view.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="sysAttBorrowForm" />
						<c:param name="fdKey" value="sysAttBorrow" />
						<c:param name="showHistoryOpers" value="true" />
						<c:param name="isExpand" value="true" />
						<c:param name="approveType" value="right" />
						<c:param name="approvePosition" value="right" />
					</c:import>
					<!-- 审批记录 -->
					<c:import
						url="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote_list_content.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="sysAttBorrowForm" />
						<c:param name="fdModelId" value="${sysAttBorrowForm.fdId}" />
						<c:param name="fdModelName"
							value="com.landray.kmss.sys.attachment.borrow.model.SysAttBorrow" />
					</c:import>
				</c:otherwise>

			</c:choose>

			<ui:content
				title="${lfn:message('sys-attachment-borrow:sysAttBorrow.baseInfo') }"
				titleicon="lui-fm-icon-2">
				<table class="tb_normal lui-fm-noneBorderTable" width=100%>
					<!--申请人-->
					<tr>
						<td class="tr_normal_title" width=30%><bean:message
								bundle="sys-attachment-borrow" key="sysAttBorrow.docCreator" /></td>
						<td><xform:text property="docCreatorId" showStatus="noShow" />
							<c:out value="${ sysAttBorrowForm.docCreatorName}"></c:out></td>
					</tr>
					<!--创建时间-->
					<tr>
						<td class="tr_normal_title" width=30%><bean:message
								bundle="sys-attachment-borrow" key="sysAttBorrow.docCreateTime" /></td>
						<td><c:out value="${ sysAttBorrowForm.docCreateTime}"></c:out>
						</td>
					</tr>

					<tr>
						<td class="tr_normal_title" width=30%><bean:message
								bundle="sys-attachment-borrow" key="sysAttBorrow.docStatus" /></td>
						<td><sunbor:enumsShow value="${sysAttBorrowForm.docStatus}"
								enumsType="common_status"></sunbor:enumsShow></td>
					</tr>

					<tr>
						<td class="tr_normal_title" width=30%><bean:message
								bundle="sys-attachment-borrow" key="sysAttBorrow.fdStatus" /></td>
						<td><sunbor:enumsShow value="${sysAttBorrowForm.fdStatus}"
								enumsType="sys_att_borrow_status" bundle="sys-attachment-borrow"></sunbor:enumsShow>
						</td>
					</tr>
				</table>
			</ui:content>

		</ui:tabpanel>
	</template:replace>
</template:include>