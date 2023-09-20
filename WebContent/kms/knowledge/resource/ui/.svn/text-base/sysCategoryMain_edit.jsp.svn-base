<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<tr LKS_LabelName="<c:if test="${empty JsParam.cateTitle}"><bean:message bundle="sys-simplecategory"
		key="table.sysSimpleCategory" /></c:if><c:if test="${not empty JsParam.cateTitle}">${param.cateTitle}</c:if>">
	<td>
		<table class="tb_normal" width="100%" ${JsParam.styleValue}>
			<c:import url="/kms/knowledge/resource/ui/sysCategoryMain_edit_body.jsp"
					  charEncoding="UTF-8">
				<c:param name="formName" value="${JsParam.formName}" />
				<c:param name="requestURL" value="${JsParam.requestURL}" />
				<c:param name="fdModelName" value="${JsParam.fdModelName}" />
				<c:param name="mainModelName" value="${JsParam.mainModelName}" />
				<c:param name="fdCopyId" value="${JsParam.fdCopyId}"/>
			</c:import>
			<c:if test="${param.fdModelName == 'com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory'}">
				<c:if test="${not empty JsParam.fdCopyId}">
					<tr>
						<td class="td_normal_title" width="15%">
								${lfn:message('kms-common:category.isCopyChildren')}
						</td>
						<td colspan=3>
							<label>
								<input type="hidden" name="fdCopyId" >
								<input type="checkbox" name="isCopyChildren" value="1" /> ${lfn:message('kms-common:category.copy')}
							</label>
						</td>
					</tr>
				</c:if>
			</c:if>
		</table>
	</td>
</tr>
