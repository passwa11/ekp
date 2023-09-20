<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="lbpm.right" isEdit="true" fdUseForm="true"
	formName="kmsKnowledgeBorrowAttAuthForm"
	formUrl="${KMSS_Parameter_ContextPath}kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrowAttAuth.do">
	<%-- 标题 --%>
	<template:replace name="title">
			    <c:choose>
	                <c:when test="${kmsKnowledgeBorrowAttAuthForm.method_GET == 'add' }">
	                    <c:out value="${ lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.button.add') } - ${ lfn:message('kms-knowledge-borrow:table.kmsKnowledgeBorrowAttAuth') }" />
	                </c:when>
	                <c:otherwise>
	                	<c:out value="${ lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.button.edit') } - " />
	                    <c:out value="${ kmsKnowledgeBorrowAttAuthForm.docSubject} - " />
	                    <c:out value="${ lfn:message('kms-knowledge-borrow:table.kmsKnowledgeBorrowAttAuth') }" />
	                </c:otherwise>
            	</c:choose>
			</template:replace>
	<%-- 按钮栏 --%>
	<template:replace name="toolbar">
		<link
			href="${ LUI_ContextPath}/kms/knowledge/kms_knowledge_borrow/att_auth/resource/style/edit.css?s_cache=${ LUI_Cache }"
			rel="stylesheet">

		<script type="text/javascript"
			src="${ LUI_ContextPath}/kms/knowledge/kms_knowledge_borrow/att_auth/resource/js/edit.js?s_cache=${ LUI_Cache }"></script>

		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">

			<c:choose>
				<c:when test="${kmsKnowledgeBorrowAttAuthForm.method_GET == 'add' }">
					<ui:button text="${lfn:message('button.submit') }" order="2"
							   onclick="submitForm('save','20');">
					</ui:button>
				</c:when>
				<c:otherwise>
					<ui:button text="${lfn:message('button.submit') }" order="2"
							   onclick="submitForm('update');">
					</ui:button>
				</c:otherwise>
			</c:choose>

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
				text="${ lfn:message('kms-knowledge-borrow:table.kmsKnowledgeBorrowAttAuth') }">
			</ui:menu-item>
		</ui:menu>
	</template:replace>
	<%-- 内容 --%>
	<template:replace name="content">
		<html:hidden property="fdId" />
		<html:hidden property="docStatus" />

		<ui:tabpage collapsed="true">
			<ui:content title="${ lfn:message('kms-knowledge-borrow:table.kmsKnowledgeBorrowAttAuth') }" expand="true">
				<table class="tb_normal" width=100%>
					<tr>
						<td class="td_normal_title" width=15%>${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrow.docSubject") }</td>
						<td colspan=3>
                            <xform:dialog propertyName="docSubject" propertyId="fdDocId" style="width:97%" required="true">
                                selectDoc('${kmsKnowledgeBorrowAttAuthForm.fdDocId }','${kmsKnowledgeBorrowAttAuthForm.docSubject }','30');
                            </xform:dialog>
						</td>
					</tr>
					<c:if test="${not empty kmsKnowledgeBorrowAttAuthForm.attachmentForms['attMain'].attachments}">
						<tr>
							<td class="td_normal_title" width=15%>${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrow.att.docSubject") }</td>
							<td colspan=3>
								<div>
									<c:import
											url="/sys/attachment/sys_att_main/sysAttMain_view.jsp"
											charEncoding="UTF-8">
										<c:param name="formBeanName" value="kmsKnowledgeBorrowAttAuthForm" />
										<c:param name="fdKey" value="attMain" />
									</c:import>
								</div>
							</td>
						</tr>
					</c:if>
					<tr>
						<td class="td_normal_title" width=15%>
							${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.fdDuration") }
						</td>
						<td width="35%" colspan=3 id="fdDuration_td">
						    <span id="fdDurationArea">
						    	<xform:text property="fdDuration" style="width:20%" validators="checkDuration" />${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.info.unit.day')}
						    </span>
							<xform:checkbox property="fdDurationLimit" value="${fdDurationLimit}"  onValueChange="fdDurationLimitChange" htmlElementProperties="data-cate='select'" subject="<bean:message bundle='kms-knowledge-borrow' key='kmsKnowledgeBorrow.fdStatus.forever'/>" >
								<xform:simpleDataSource value="true"><bean:message bundle="kms-knowledge-borrow" key="kmsKnowledgeBorrow.fdStatus.forever"/></xform:simpleDataSource>
							</xform:checkbox>
							<span style="color: red">${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.fdDuration.tip") }</span>
						</td>
					</tr>

					<tr>
						<td class="td_normal_title" width=15%>
							${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.fdEffectiveTime") }</td>
						<td width="35%" colspan=3><xform:datetime dateTimeType="date" validators="after"
								property="fdEffectiveTime" style="width:50%"></xform:datetime>
							<span style="color: red">${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.fdEffectiveTime.tip") }</span>

					</tr>

					<tr>
						<%-- 申请原因 --%>
						<td class="td_normal_title" width=15%>${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.fdReason") }</td>
						<td width="85%" colspan="3"><xform:textarea
								required="true" property="fdReason" style="width:97%" /></td>
					</tr>

					<tr>
						<!-- 申请人 -->
						<td class="td_normal_title" width=15%>${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.fdApplicants") }</td>
						<td width="85%" colspan=3><xform:address textarea="true"
								required="true" mulSelect="false" propertyId="fdApplicantIds"
								propertyName="fdApplicantNames" showStatus="view"></xform:address></td>
					</tr>
					<tr>
						<!-- 附件权限 -->
						<td class="td_normal_title" width=15%>${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrow.fdAuth") }</td>
						<td width="85%" colspan=3 class="luiAttBorrorAuth">
							<div>${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrow.fdDownloadEnable") }
								<ui:switch
										property="fdDownloadEnable"
										disabledText="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.info.close')}"
										enabledText="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.info.open')}"
										onValueChange="configDownloadChange(this);">
								</ui:switch>
								<input type="hidden" name="fdDownloadEnableOriginal" value="${kmsKnowledgeBorrowAttAuthForm.fdDownloadEnable }">
							</div>
							<div>${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrow.fdCopyEnable") }
								<ui:switch
										property="fdCopyEnable"
										disabledText="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.info.close')}"
										enabledText="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.info.open')}"
										onValueChange="configCopyChange(this);">
								</ui:switch>
								<input type="hidden" name="fdCopyEnableOriginal" value="${kmsKnowledgeBorrowAttAuthForm.fdCopyEnable }">
							</div>
							<div>${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrow.fdPrintEnable") }
								<ui:switch
										property="fdPrintEnable"
										disabledText="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.info.close')}"
										enabledText="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.info.open')}"
										onValueChange="configPrintChange(this);">
								</ui:switch>
								<input type="hidden" name="fdPrintEnableOriginal" value="${kmsKnowledgeBorrowAttAuthForm.fdPrintEnable }">
							</div>
							<div style="color: red">${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrow.fdAuth.tip") }</div>
						</td>
					</tr>
				</table>
			</ui:content>
		</ui:tabpage>
		<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop"
			var-count="5" var-average='false' var-useMaxWidth='true'>
			<%--流程--%>
			<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp"
				charEncoding="UTF-8">
				<c:param name="formName" value="kmsKnowledgeBorrowAttAuthForm" />
				<c:param name="fdKey" value="${kmsKnowledgeBorrowAttAuthForm.fdFlowKey }" />
				<c:param name="showHistoryOpers" value="true" />
				<c:param name="isExpand" value="true" />
				<c:param name="approveType" value="right" />
			</c:import>
		</ui:tabpanel>
	</template:replace>

	<template:replace name="barRight">
		<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
            <html:hidden property="fdFlowKey"/>
            <%--流程--%>
			<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp"
				charEncoding="UTF-8">
				<c:param name="formName" value="kmsKnowledgeBorrowAttAuthForm" />
				<c:param name="fdKey" value="${kmsKnowledgeBorrowAttAuthForm.fdFlowKey}" />
				<c:param name="showHistoryOpers" value="true" />
				<c:param name="isExpand" value="true" />
				<c:param name="approveType" value="right" />
				<c:param name="approvePosition" value="right" />
			</c:import>

			<ui:content
				title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.baseInfo') }"
				titleicon="lui-fm-icon-2">
				<table class="tb_normal lui-fm-noneBorderTable" width=100%>
					<tr>
						<td class="tr_normal_title" width=30%><bean:message
								bundle="kms-knowledge-borrow" key="kmsKnowledgeBorrowAttAuth.docCreator" /></td>
						<td><xform:text property="docCreatorId" showStatus="noShow" />
							<c:out value="${kmsKnowledgeBorrowAttAuthForm.docCreatorName}"></c:out></td>
					</tr>
					<!--创建时间-->
					<tr>
						<td class="tr_normal_title" width=30%><bean:message
								bundle="kms-knowledge-borrow" key="kmsKnowledgeBorrowAttAuth.docCreateTime" /></td>
						<td><c:out value="${ kmsKnowledgeBorrowAttAuthForm.docCreateTime}"></c:out>
						</td>
					</tr>

					<tr>
						<td class="tr_normal_title" width=30%><bean:message
								bundle="kms-knowledge-borrow" key="kmsKnowledgeBorrow.docStatus" /></td>
						<td><sunbor:enumsShow value="${kmsKnowledgeBorrowAttAuthForm.docStatus}"
								enumsType="common_status"></sunbor:enumsShow></td>
					</tr>

					<tr>
						<td class="tr_normal_title" width=30%><bean:message
								bundle="kms-knowledge-borrow" key="kmsKnowledgeBorrow.fdStatus" /></td>
						<td><sunbor:enumsShow value="${kmsKnowledgeBorrowAttAuthForm.fdStatus}"
								enumsType="kms_knowledge_borrow_status" bundle="kms-knowledge-borrow"></sunbor:enumsShow>
						</td>
					</tr>
				</table>
			</ui:content>
			<html:hidden property="method_GET" />

		</ui:tabpanel>
	</template:replace>
</template:include>