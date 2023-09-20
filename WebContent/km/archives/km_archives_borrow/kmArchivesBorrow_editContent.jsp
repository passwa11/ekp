<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.landray.kmss.km.archives.model.KmArchivesConfig"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:choose>
	<c:when test="${param.contentType eq 'xform'}">
		<table class="tb_normal" width="100%">
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('km-archives:kmArchivesBorrow.docSubject')}
				</td>
				<td colspan="3" width="85.0%">
					<xform:text property="docSubject" showStatus="edit" style="width:95%;" />
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('km-archives:kmArchivesBorrow.docTemplate')}
				</td>
				<td width="35%">
					<div id="selectTemplet"></div>
				</td>
				<td class="td_normal_title" width="15%">
					${lfn:message('km-archives:kmArchivesBorrow.fdBorrowDate')}</td>
				<td width="35%">
					<xform:datetime onValueChange="null" property="fdBorrowDate" showStatus="edit" dateTimeType="datetime" style="width:95%;" required="true" />
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('km-archives:kmArchivesBorrow.fdBorrower')}</td>
				<td width="35%">
					<xform:address propertyId="fdBorrowerId" propertyName="fdBorrowerName" orgType="ORG_TYPE_PERSON" showStatus="readOnly" style="width:95%;"
						subject="${lfn:message('km-archives:kmArchivesBorrow.fdBorrower')}" required="true" onValueChange="selectDept" />
				</td>
				<td class="td_normal_title" width="15%">
					${lfn:message('km-archives:kmArchivesBorrow.docDept')}
				</td>
				<td width="35%">
					<xform:address propertyId="docDeptId" propertyName="docDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="readOnly" style="width:95%;" />
				</td>
			</tr>
			<c:if test="${not empty fdDenses}">
				<tr>
					<td class="td_normal_title" width="15%">
						<bean:message key="kmArchivesBorrow.canDense" bundle="km-archives" />
					</td>
					<td width="85%" colspan="3">
						<font color="red"><c:out value="${fdDenses}"></c:out></font>
					</td>
				</tr>	
			</c:if>
			<tr>
				<td colspan="4">
					<table class="tb_normal" width=100% style="border: none">
						<tr style="border: none">
							<td style="border: none">
								<c:import url="/km/archives/km_archives_borrow_option/kmArchMainOption_edit.jsp" charEncoding="UTF-8">
								</c:import>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('km-archives:kmArchivesBorrow.fdBorrowReason')}</td>
				<td colspan="3" width="85.0%"><xform:textarea
						property="fdBorrowReason" showStatus="edit" style="width:95%;" />
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('km-archives:kmArchivesBorrow.attBorrow')}</td>
				<td colspan="3" width="85.0%">
					<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
						<c:param name="fdKey" value="attBorrow" />
						<c:param name="formBeanName" value="kmArchivesBorrowForm" />
						<c:param name="fdRequired" value="false" />
						<c:param name="fdMulti" value="true" />
					</c:import></td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('km-archives:kmArchivesBorrow.docCreator')}</td>
				<td width="35%"><ui:person
						personId="${kmArchivesBorrowForm.docCreatorId}"
						personName="${kmArchivesBorrowForm.docCreatorName}" /></td>
				<td class="td_normal_title" width="15%">
					${lfn:message('km-archives:kmArchivesBorrow.docCreateTime')}</td>
				<td width="35%"><xform:datetime property="docCreateTime"
						showStatus="view" style="width:95%;" /></td>
			</tr>
		</table>
		<br>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${param.approveModel eq 'right'}">
				<!-- 流程 -->
				<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmArchivesBorrowForm" />
					<c:param name="fdKey" value="kmArchivesBorrow" />
					<c:param name="approveType" value="right" />
				</c:import>
			</c:when>
			<c:otherwise>
				<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmArchivesBorrowForm" />
					<c:param name="fdKey" value="kmArchivesBorrow" />
					<c:param name="showHistoryOpers" value="true" />
					<c:param name="isExpand" value="true" />
				</c:import>
			</c:otherwise>
		</c:choose>	
		<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmArchivesBorrowForm" />
			<c:param name="moduleModelName" value="com.landray.kmss.km.archives.model.KmArchivesBorrow" />
		</c:import>
	</c:otherwise>	
</c:choose>