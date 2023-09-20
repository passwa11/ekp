<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="lbpm.right" isEdit="true" fdUseForm="true"
	formName="kmsKnowledgeBorrowForm"
	formUrl="${KMSS_Parameter_ContextPath}kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrow.do">
	<%-- 标题 --%>
	<template:replace name="title">
			    <c:choose>
	                <c:when test="${kmsKnowledgeBorrowForm.method_GET == 'add' }">
	                    <c:out value="${ lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.button.add') } - ${ lfn:message('kms-knowledge-borrow:table.kmsKnowledgeBorrow') }" />
	                </c:when>
	                <c:otherwise>
	                	<c:out value="${ lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.button.edit') } - " />
	                    <c:out value="${ kmsKnowledgeBorrowForm.docSubject} - " />
	                    <c:out value="${ lfn:message('kms-knowledge-borrow:table.kmsKnowledgeBorrow') }" />
	                </c:otherwise>
            	</c:choose>
			</template:replace>
	<%-- 按钮栏 --%>
	<template:replace name="toolbar">
		<link
			href="${ LUI_ContextPath}/kms/knowledge/kms_knowledge_borrow/resource/style/edit.css?s_cache=${ LUI_Cache }"
			rel="stylesheet">

		<script type="text/javascript"
			src="${ LUI_ContextPath}/kms/knowledge/kms_knowledge_borrow/resource/js/edit.js?s_cache=${ LUI_Cache }"></script>

		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<c:choose>
				<c:when test="${kmsKnowledgeBorrowForm.method_GET == 'add' }">
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
                            <xform:dialog propertyName="docSubject" propertyId="fdDocId" style="width:97%" required="true">
                                selectDoc('${kmsKnowledgeBorrowForm.fdDocId }','${kmsKnowledgeBorrowForm.docSubject }','30');
                            </xform:dialog>
						</td>
					</tr>

					<tr>
						<td class="td_normal_title" width=15%>
							${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrow.fdDuration") }
						</td>
						<td width="35%" colspan=3 id="fdDuration_td">
						    <span id="fdDurationArea">
						    	<xform:text property="fdDuration" style="width:20%" validators="checkDuration" />天
						    </span>
							<xform:checkbox property="fdDurationLimit" value="${fdDurationLimit}"  onValueChange="fdDurationLimitChange" htmlElementProperties="data-cate='select'" subject="<bean:message bundle='kms-knowledge-borrow' key='kmsKnowledgeBorrow.fdStatus.forever'/>" >
								<xform:simpleDataSource value="true"><bean:message bundle="kms-knowledge-borrow" key="kmsKnowledgeBorrow.fdStatus.forever"/></xform:simpleDataSource>
							</xform:checkbox>
							<span style="color: red">${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrow.fdDuration.tip") }</span>
						</td>
					</tr>

					<tr>
						<td class="td_normal_title" width=15%>
							${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrow.fdBorrowEffectiveTime") }</td>
						<td width="35%" colspan=3><xform:datetime dateTimeType="date" validators="after" 
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
								required="true" mulSelect="false" propertyId="fdBorrowerIds"
								propertyName="fdBorrowerNames" showStatus="view"></xform:address></td>
					</tr>

  					<c:if test="${not empty kmsKnowledgeBorrowForm.attachmentForms['attachment'].attachments}">
						<tr>
							<!-- 附件权限 -->
							<td class="td_normal_title" width=15%>${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrow.fdAuth") }</td>
							<td width="85%" colspan=3 class="luiAttBorrorAuth">
								<div>${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrow.fdDownloadEnable") }
								<ui:switch
									property="fdDownloadEnable" 
									disabledText="关闭"
									enabledText="开启"  
									onValueChange="configDownloadChange(this);">
								</ui:switch>
								<input type="hidden" name="fdDownloadEnableOriginal" value="${kmsKnowledgeBorrowForm.fdDownloadEnable }">
								</div>
								<div>${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrow.fdCopyEnable") }
								<ui:switch
									property="fdCopyEnable" 
									disabledText="关闭" 
									enabledText="开启"
									onValueChange="configCopyChange(this);">
								</ui:switch>
								<input type="hidden" name="fdCopyEnableOriginal" value="${kmsKnowledgeBorrowForm.fdCopyEnable }">		
								</div>
								<div>${lfn:message("kms-knowledge-borrow:kmsKnowledgeBorrow.fdPrintEnable") }
								<ui:switch
									property="fdPrintEnable" 
									disabledText="关闭" 
									enabledText="开启"
									onValueChange="configPrintChange(this);">
								</ui:switch>
								<input type="hidden" name="fdPrintEnableOriginal" value="${kmsKnowledgeBorrowForm.fdPrintEnable }">		
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
			<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp"
				charEncoding="UTF-8">
				<c:param name="formName" value="kmsKnowledgeBorrowForm" />
				<c:param name="fdKey" value="${kmsKnowledgeBorrowForm.fdFlowKey }" />
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
				<c:param name="formName" value="kmsKnowledgeBorrowForm" />
				<c:param name="fdKey" value="${kmsKnowledgeBorrowForm.fdFlowKey }" />
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
						<td><sunbor:enumsShow value="${kmsKnowledgeBorrowForm.fdStatus}"
								enumsType="kms_knowledge_borrow_status" bundle="kms-knowledge-borrow"></sunbor:enumsShow>
						</td>
					</tr>
				</table>
			</ui:content>

			<html:hidden property="method_GET" />
		</ui:tabpanel>
	</template:replace>
</template:include>