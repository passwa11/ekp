<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="lbpm.right" sidebar="auto" formName="kmsKnowledgeBorrowForm"
	formUrl="${KMSS_Parameter_ContextPath}kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrow.do">
	<%-- 标题 --%>
	<template:replace name="title">
		<c:out value="${ kmsKnowledgeBorrowForm.docSubject}" />
	</template:replace>
	<%-- 按钮栏 --%>
	<template:replace name="toolbar">
		<link
			href="${ LUI_ContextPath}/kms/knowledge/kms_knowledge_borrow/resource/style/edit.css?s_cache=${ LUI_Cache }"
			rel="stylesheet">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<kmss:auth requestURL="/kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrow.do?method=edit&fdId=${JsParam.fdId}" requestMethod="GET">
				<ui:button text="${lfn:message('button.edit')}"
						   onclick="toEditView();" order="2">
				</ui:button>
			</kmss:auth>

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
				text="${ lfn:message('kms-knowledge-borrow:table.kmsKnowledgeBorrow') }">
			</ui:menu-item>
		</ui:menu>
	</template:replace>
	<%-- 内容 --%>
	<template:replace name="content">
		<html:hidden property="fdId" />
		<html:hidden property="docStatus" />

		<ui:tabpage collapsed="true">
			<ui:content title="${ lfn:message('kms-knowledge-borrow:py.JiBenXinXi') }" expand="true">
				<table class="tb_normal" width=100%>
					<tr>
						<td class="td_normal_title" width=15%>${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrow.docSubject") }</td>
						<td colspan=3>
						<a class="link_btn" target="_blank" href="${LUI_ContextPath}/kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrow.do?method=viewDoc&fdId=${kmsKnowledgeBorrowForm.fdDocId}">
							<xform:text property="docSubject"></xform:text>
						</a>
					</td>
					</tr>

					<c:if test="${not empty kmsKnowledgeBorrowForm.attachmentForms['attachment'].attachments}">
                       <tr>
                           <td class="td_normal_title" width=15%>${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrow.att.docSubject") }</td>
                           <td colspan=3>
                               <div>
                                   <c:import
                                           url="/sys/attachment/sys_att_main/sysAttMain_view.jsp"
                                           charEncoding="UTF-8">
                                       <c:param name="formBeanName" value="kmsKnowledgeBorrowForm" />
                                       <c:param name="fdKey" value="attachment" />
                                   </c:import>
                               </div>
                           </td>
                       </tr>
                    </c:if>

					<tr>
						<td class="td_normal_title" width=15%>
							${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrow.fdDuration") }
						</td>
						<td width="35%" colspan="3">
						<c:choose>
							<c:when test="${null != kmsKnowledgeBorrowForm.fdDuration and kmsKnowledgeBorrowForm.fdDuration ne '0'}">
								<xform:text property="fdDuration" style="width:50%" validators="checkDuration" />天 
							</c:when>
							<c:otherwise>
								<bean:message bundle="kms-knowledge-borrow" key="kmsKnowledgeBorrow.fdStatus.forever"/>
							</c:otherwise>
						</c:choose>		
						</td>
					</tr>

					<tr>
						<td class="td_normal_title" width=15%>
							${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrow.fdBorrowEffectiveTime") }</td>
						<td width="35%" colspan="3"><xform:datetime dateTimeType="date"
								property="fdBorrowEffectiveTime" style="width:50%"></xform:datetime>
							<span style="color: red">${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrow.fdBorrowEffectiveTime.tip") }</span>

					</tr>

					<tr>
						<%-- 摘要 --%>
						<td class="td_normal_title" width=15%>${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrow.fdReason") }</td>
						<td width="85%" colspan="3"><xform:textarea
								required="true" property="fdReason" style="width:97%" /></td>
					</tr>

					<tr>
						<!-- 借阅人 -->
						<td class="td_normal_title" width=15%>${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrow.fdBorrowers") }</td>
						<td width="85%" colspan=3><xform:address textarea="true"
								required="true" mulSelect="true" propertyId="fdBorrowerIds"
								propertyName="fdBorrowerNames" style="width:97%;height:90px;"></xform:address></td>
					</tr>

					<c:if test="${not empty kmsKnowledgeBorrowForm.attachmentForms['attachment'].attachments}">
						<tr>
							<!-- 附件权限 -->
							<td class="td_normal_title" width=15%>${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrow.fdAuth") }</td>
							<td width="85%" colspan=3 class="luiAttBorrorAuth">
								<div>${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrow.fdDownloadEnable") }<ui:switch
										property="fdDownloadEnable" disabledText="关闭"
										enabledText="开启" showType="show"></ui:switch>
								</div>
								<div>${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrow.fdCopyEnable") }<ui:switch
										property="fdCopyEnable" disabledText="关闭" enabledText="开启" showType="show"></ui:switch>
								</div>
								<div>${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrow.fdPrintEnable") }<ui:switch
										property="fdPrintEnable" disabledText="关闭" enabledText="开启" showType="show"></ui:switch>
								</div>
								<div style="color: red">${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrow.fdAuth.tip") }</div>
							</td>
						</tr>
					</c:if>
				</table>
			</ui:content>
		</ui:tabpage>
		
		<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop"
			var-count="5" var-average='false' var-useMaxWidth='true'>
			<%--流程--%>
			<c:choose>
				<c:when test="${kmsKnowledgeBorrowForm.docStatus>='30' || kmsKnowledgeBorrowForm.docStatus=='00'}">
					<c:import url="/sys/workflow/import/sysWfProcess_view.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="kmsKnowledgeBorrowForm" />
						<c:param name="fdKey" value="kmsKnowledgeBorrow" />
						<c:param name="showHistoryOpers" value="true" />
						<c:param name="isExpand" value="false" />
						<c:param name="approveType" value="right" />
						<c:param name="needInitLbpm" value="true" />
					</c:import>
				</c:when>
				<c:otherwise>
					<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="kmsKnowledgeBorrowForm" />
						<c:param name="fdKey" value="kmsKnowledgeBorrow" />
						<c:param name="showHistoryOpers" value="true" />
						<c:param name="isExpand" value="true" />
						<c:param name="approveType" value="right" />
					</c:import>
				</c:otherwise>
			</c:choose>
		</ui:tabpanel>
	</template:replace>

	<template:replace name="barRight">
		<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel" style="margin-left: 44px;">
			<%--流程--%>
			<c:choose>
				<c:when
					test="${kmsKnowledgeBorrowForm.docStatus>='30' || kmsKnowledgeBorrowForm.docStatus=='00'}">
				</c:when>

				<c:otherwise>
					<%-- 流程 --%>
					<c:import url="/sys/workflow/import/sysWfProcess_view.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="kmsKnowledgeBorrowForm" />
						<c:param name="fdKey" value="${kmsKnowledgeBorrowForm.fdFlowKey }" />
						<c:param name="showHistoryOpers" value="true" />
						<c:param name="isExpand" value="true" />
						<c:param name="approveType" value="right" />
						<c:param name="approvePosition" value="right" />
					</c:import>
					<!-- 审批记录 -->
					<c:import
						url="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote_list_content.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="kmsKnowledgeBorrowForm" />
						<c:param name="fdModelId" value="${kmsKnowledgeBorrowForm.fdId}" />
						<c:param name="fdModelName"
							value="com.landray.kmss.kms.knowledge.borrow.model.KmsKnowledgeBorrow" />
					</c:import>
				</c:otherwise>

			</c:choose>

			<ui:content
				title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.baseInfo') }"
				titleicon="lui-fm-icon-2">
				<table class="tb_normal lui-fm-noneBorderTable" width=100%>
					<tr>
						<td class="tr_normal_title" width=30%><bean:message
								bundle="kms-knowledge-borrow" key="kmsKnowledgeBorrow.docCreator" /></td>
						<td><xform:text property="docCreatorId" showStatus="noShow" />
							<c:out value="${ kmsKnowledgeBorrowForm.docCreatorName}"></c:out></td>
					</tr>
					<!--创建时间-->
					<tr>
						<td class="tr_normal_title" width=30%><bean:message
								bundle="kms-knowledge-borrow" key="kmsKnowledgeBorrow.docCreateTime" /></td>
						<td><c:out value="${ kmsKnowledgeBorrowForm.docCreateTime}"></c:out>
						</td>
					</tr>

					<tr>
						<td class="tr_normal_title" width=30%><bean:message
								bundle="kms-knowledge-borrow" key="kmsKnowledgeBorrow.docStatus" /></td>
						<td><sunbor:enumsShow value="${kmsKnowledgeBorrowForm.docStatus}"
								enumsType="common_status"></sunbor:enumsShow></td>
					</tr>

					<tr>
						<td class="tr_normal_title" width=30%><bean:message
								bundle="kms-knowledge-borrow" key="kmsKnowledgeBorrow.fdStatus" /></td>
						<td>
							<c:choose>
								<c:when test="${not empty fdStatusUnDo}">
									<sunbor:enumsShow value="${fdStatusUnDo}"
													  enumsType="kms_knowledge_borrow_status" bundle="kms-knowledge-borrow">
									</sunbor:enumsShow>
								</c:when>
								<c:otherwise>
									<sunbor:enumsShow value="${kmsKnowledgeBorrowForm.fdStatus}"
													  enumsType="kms_knowledge_borrow_status" bundle="kms-knowledge-borrow">
									</sunbor:enumsShow>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</table>
			</ui:content>

		</ui:tabpanel>

		<script>
			//编辑
			function toEditView(){
				window.location.href="${LUI_ContextPath }/kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrow.do?method=edit&fdId=${param.fdId}"
			}
		</script>
	</template:replace>
</template:include>