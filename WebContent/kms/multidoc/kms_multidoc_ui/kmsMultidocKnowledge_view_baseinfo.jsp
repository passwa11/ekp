<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:if test="${kmsMultidocKnowledgeForm.docStatus=='10'}">
	<ui:event event="layoutDone">
		$("i.lui-fm-icon-2").closest(".lui_tabpanel_vertical_icon_navs_item_l").click();
    </ui:event>
</c:if>
<ui:content title="${lfn:message('kms-multidoc:kmsMultidocKnowledge.docInfo') }" titleicon="lui-fm-icon-2">
	<div style="margin-bottom: 10px;">
		
		<div class="msg_box">
		<table class="tb_simple tb_simple_view">
			<tr>
				<td width="30%" class="td_normal_title">
					<bean:message bundle="kms-multidoc" key="kmsMultidoc.creator" />
				</td>
				<td  colspan="3" width="70%">
					<ui:person personId="${kmsMultidocKnowledgeForm.docCreatorId}" personName="${kmsMultidocKnowledgeForm.docCreatorName}">
					</ui:person>
				</td>
			</tr>
			<tr>
				<td width="30%" class="td_normal_title">
					<bean:message bundle="sys-doc" key="sysDocBaseInfo.docDept" />
				</td>
				<td  colspan="3" width="70%">
					<c:out  value="${kmsMultidocKnowledgeForm.docDeptName}" />
				</td>
			</tr>
			<tr>
				<td width="30%" class="td_normal_title">
					<bean:message bundle="kms-multidoc" key="kmsMultidocKnowledge.createTime" />
				</td>
				<td  colspan="3" width="70%">
					${createTime}
				</td>
			</tr>
			<c:if test="${not empty kmsMultidocKnowledgeForm.docEffectiveTime }">
			    <tr>
					<td width="30%" class="td_normal_title">
						<bean:message bundle="kms-multidoc" key="kmsMultidocKnowledge.docEffectiveTime" />
					</td>
					<td  colspan="3" width="70%">
					 	${kmsMultidocKnowledgeForm.docEffectiveTime}
					</td>
				</tr>
			</c:if>
			
			<c:if test="${ not empty kmsMultidocKnowledgeForm.docFailureTime }">
			   <tr>
					<td width="30%" class="td_normal_title">
						<bean:message bundle="kms-multidoc" key="kmsMultidocKnowledge.docFailureTime" />
					</td>
					<td  colspan="3" width="70%">
						${kmsMultidocKnowledgeForm.docFailureTime}
					</td>
				</tr>
			</c:if>
			
			<c:if test="${ not empty kmsMultidocKnowledgeForm.docExpireTime }">
				<tr>
					<td width="30%" class="td_normal_title">
						<bean:message bundle="kms-multidoc" key="kmsMultidocKnowledge.docExpireTime" />
					</td>
					<td  colspan="3" width="70%">
						<xform:datetime value="${kmsMultidocKnowledgeForm.docExpireTime}" 
									property="docExpireTime" dateTimeType="date" />
					</td>
				</tr>
			</c:if>
		
			<tr>
				<td width="30%" class="td_normal_title">
					<bean:message bundle="sys-doc" key="sysDocBaseInfo.docStatus" />
				</td>
				<td  colspan="3" width="70%">
					<sunbor:enumsShow	value="${sysDocBaseInfoForm.docStatus}"	enumsType="kms_doc_status" />
				</td>
			</tr>
			<tr>
				<td width="30%" class="td_normal_title">
					<bean:message bundle="sys-edition" key="sysEditionMain.tab.label" />
				</td>
				<td  colspan="3" width="70%">
					V ${kmsMultidocKnowledgeForm.editionForm.mainVersion}.${kmsMultidocKnowledgeForm.editionForm.auxiVersion}
				</td>
			</tr>
			
			<c:if test="${not empty kmsMultidocKnowledgeForm.fdNumber}">
				<tr>
					<td width="30%" class="td_normal_title">
						<bean:message bundle="kms-multidoc" key="kmsMultidocKnowledge.fdNumber"/>
					</td>
					<td  colspan="3" width="70%">
						<c:out value="${kmsMultidocKnowledgeForm.fdNumber}"/>
					</td>
				</tr>
			</c:if>
			<c:if test="${not empty kmsMultidocKnowledgeForm.docAlterorId}">
				<tr>
					<td width="30%" class="td_normal_title">
						<bean:message bundle="kms-multidoc" key="kmsMultidoc.lastUpdateUser" />
					</td>
					<td  colspan="3" width="70%">
						<ui:person personId="${kmsMultidocKnowledgeForm.docAlterorId}" personName="${kmsMultidocKnowledgeForm.docAlterorName}">
						</ui:person>
					</td>
				</tr>
				<tr>
					<td width="35%" class="td_normal_title">
						<bean:message bundle="kms-multidoc" key="kmsMultidoc.lastUpdateTime" />
					</td>
					<td  colspan="3" width="65%">
						${ alterTime}
					</td>
				</tr>
			</c:if>
			<c:if test="${not empty lbpmTitle }">
				<tr>
					<td width="30%" class="td_normal_title">
						${lfn:message('kms-multidoc:kmsMultidoc.doc.source') }
					</td>
					<td  colspan="3" width="70%">
						<a class="com_btn_link" href="${ LUI_ContextPath}${lbpmUrl }" target="blank"><c:out value="${lbpmTitle }"></c:out></a>
					</td>
				</tr>
			</c:if>		
			
			<tr>
				<td width="30%" class="td_normal_title">
					${HtmlParam.kmsMultidocKnowledgeTemplateName } 
				</td>
				<td  colspan="3" width="70%">
					<a href="${LUI_ContextPath }/kms/multidoc/?categoryId=${kmsMultidocKnowledgeForm.docCategoryId}" target="_blank"><c:out value="${kmsMultidocKnowledgeForm.docCategoryName}"/></a>
				</td>
			</tr>
			<c:choose>
				<c:when test="${HtmlParam.kmsCategoryEnabled}">
					<c:set var="mainForm" value="${requestScope['kmsMultidocKnowledgeForm']}"/>
					<c:set var="kmsCategoryKnowledgeRelForm" value="${mainForm.kmsCategoryKnowledgeRelForm}"/>
					<c:if test="${not empty kmsCategoryKnowledgeRelForm.kmsCategoryNames}">
						<td width="30%" class="td_normal_title">
							<bean:message bundle="kms-category" key="table.kmsCategoryMain"/>
						</td>
						<td  colspan="3" width="70%">
							${kmsCategoryKnowledgeRelForm.kmsCategoryNamesView}
						</td>
					</c:if>
				</c:when>
				<c:otherwise>
					<!-- 辅助分类 -->
					<c:if test="${not empty secondCategoriesNames[0]}">
						<tr>
							<td width="30%" class="td_normal_title">
								<bean:message bundle="kms-multidoc" key="kmsMultidoc.kmsMultidocKnowledge.docProperties"/>
							</td>
							<td  colspan="3" width="70%">
								<c:forEach items="${secondCategoriesNames}" var="docSecondCateName" varStatus="varStatus">
									<a href="${LUI_ContextPath }/kms/multidoc/?categoryId=${secondCategoriesIds[varStatus.index]}" target="_blank">${docSecondCateName}</a>
									<c:if test="${!varStatus.last }">;</c:if>
								</c:forEach>
							</td>
						</tr>
					</c:if>
				</c:otherwise>
			</c:choose>
			<!-- 知识标签 -->
			<c:import url="/sys/tag/import/sysTagMain_view.jsp" charEncoding="UTF-8">
					<c:param name="isInContent" value="true"></c:param>
					<c:param name="formName" value="kmsMultidocKnowledgeForm" />
					<c:param name="useTab" value="true"></c:param>
					<c:param name="toolbarOrder" value="3"></c:param>
					<c:param name="showEditButton" value="true"></c:param>
					<c:param name="fdQueryCondition" value="${kmsMultidocKnowledgeForm.docCategoryId };${kmsMultidocKnowledgeForm.docDeptId }" />
					<c:param name="titleicon" value="lui-fm-icon-2"/>
			</c:import>
			<c:if test="${hiezEnable&&hiezAutoTag}">
				<tr>
				<c:import url="/kms/common/smartlabel/view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmsMultidocKnowledgeForm" />
				</c:import>
				</tr>
			</c:if>
		</table>
		</div>
		
	</div>
</ui:content>
<!-- 智能标签 -->
<kmss:ifModuleExist path="/third/intell/">
	<c:import url="/third/intell/import/sysTagMain_view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmsMultidocKnowledgeForm" />
			<c:param name="modelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
			<c:param name="modelId" value="${kmsMultidocKnowledgeForm.fdId}" />
			<c:param name="titleicon" value="lui-fm-icon-2"/>
	</c:import>
</kmss:ifModuleExist>