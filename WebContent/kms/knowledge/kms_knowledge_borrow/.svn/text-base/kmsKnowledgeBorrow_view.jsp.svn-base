<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld"
	prefix="lbpm"%>
<lbpm:lbpmApproveModel
	modelName="com.landray.kmss.kms.knowledge.borrow.model.KmsKnowledgeBorrow"></lbpm:lbpmApproveModel>

<c:choose>
	<c:when test="${lbpmApproveModel eq 'right'}">
		<c:import
			url="/kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrow_view_right.jsp"
			charEncoding="UTF-8">
		</c:import>
	</c:when>

	<c:otherwise>
		<template:include ref="default.view" sidebar="auto">
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
				
				<ui:tabpage>
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
												property="fdDownloadEnable" disabledText="关闭" enabledText="开启" showType="show"></ui:switch>
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
					<c:import url="/sys/workflow/import/sysWfProcess_view.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="kmsKnowledgeBorrowForm" />
						<c:param name="fdKey" value="${kmsKnowledgeBorrowForm.fdFlowKey }" />
						<c:param name="showHistoryOpers" value="true" />
						<c:param name="isExpand" value="true" />
					</c:import>
				</ui:tabpage>

				<script>
					//编辑
					function toEditView(){
						window.location.href="${LUI_ContextPath }/kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrow.do?method=edit&fdId=${param.fdId}"
					}
				</script>
			</template:replace>
		</template:include>
	</c:otherwise>
</c:choose>