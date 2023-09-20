<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/km/archives/km_archives_main/kmArchivesMain_view_include.jsp"%>
<c:choose>
	<c:when test="${param.contentType eq 'xform'}">
		<table class="tb_normal" width="100%" style="margin:30px 0px;">
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('km-archives:kmArchivesMain.docSubject')}</td>
				<td colspan="3" width="85.0%"><xform:text property="docSubject"
						showStatus="view" style="width:95%;" /></td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('km-archives:kmArchivesMain.docTemplate')}</td>
				<td width="35%"><xform:dialog propertyId="docTemplateId"
						propertyName="docTemplateName" showStatus="view" style="width:95%;">
		
					</xform:dialog></td>
				<td class="td_normal_title" width="15%">
					${lfn:message('km-archives:kmArchivesMain.docNumber')}</td>
				<td width="35%"><xform:text property="docNumber"
						showStatus="view" style="width:95%;" /></td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('km-archives:kmArchivesMain.fdLibrary')}</td>
				<td width="35%"><xform:text property="fdLibrary"
						showStatus="view" style="width:95%;" /></td>
				<td class="td_normal_title" width="15%">
					${lfn:message('km-archives:kmArchivesMain.fdVolumeYear')}</td>
				<td width="35%"><c:out value="${kmArchivesMainForm.fdVolumeYear}" /></td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('km-archives:kmArchivesMain.fdUnit')}</td>
				<td width="35%"><xform:text property="fdUnit" showStatus="view"
						style="width:95%;" /></td>
				<td class="td_normal_title" width="15%">
					${lfn:message('km-archives:kmArchivesMain.fdStorekeeper')}</td>
				<td width="35%"><xform:address propertyId="fdStorekeeperId"
						propertyName="fdStorekeeperName" orgType="ORG_TYPE_PERSON"
						showStatus="view" style="width:95%;" /></td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('km-archives:kmArchivesMain.fdFileDate')}</td>
				<td width="35%"><xform:datetime property="fdFileDate"
						showStatus="view" style="width:95%;" /></td>
				<td class="td_normal_title" width="15%">
					${lfn:message('km-archives:kmArchivesMain.fdDenseLevel')}</td>
				<td width="35%">
					<xform:text property="fdDenseName"
						showStatus="view" style="width:95%;" />
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('km-archives:kmArchivesMain.fdPeriod')}</td>
				<td width="35%"><xform:text property="fdPeriod" showStatus="view"
						style="width:95%;" /></td>
				<td class="td_normal_title" width="15%">
					${lfn:message('km-archives:kmArchivesMain.fdValidityDate')}</td>
				<td width="35%"><xform:datetime property="fdValidityDate"
						showStatus="view" style="width:95%;" /></td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('km-archives:kmArchivesMain.fdRemarks')}</td>
				<td colspan="3" width="85.0%"><xform:textarea
						property="fdRemarks" showStatus="view" style="width:95%;" /></td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('km-archives:kmArchivesMain.docCreator')}</td>
				<td width="35%">
					<%-- <ui:person personId="${kmArchivesMainForm.docCreatorId}" personName="${kmArchivesMainForm.docCreatorName}" /> --%>
					<xform:text property="docCreatorName" showStatus="view"
						style="width:95%;" />
				</td>
				<td class="td_normal_title" width="15%">
					${lfn:message('km-archives:kmArchivesMain.docCreateTime')}</td>
				<td width="35%"><xform:datetime property="docCreateTime"
						showStatus="view" style="width:95%;" /></td>
			</tr>
			<c:if test="${!empty kmArchivesMainForm.fdPrintState && (kmArchivesMainForm.fdPrintState eq '0' || kmArchivesMainForm.fdPrintState eq '2')}">
				<tr>
					<td colspan="4" style="text-align: center;color: red;font-weight: bold;">
						<c:if test="${kmArchivesMainForm.fdPrintState eq '0'}">
							${lfn:message('km-archives:kmArchivesMain.fdPrintState.progress')}
						</c:if>
						<c:if test="${kmArchivesMainForm.fdPrintState eq '2'}">
							${lfn:message('km-archives:kmArchivesMain.fdPrintState.failed')}
						</c:if>
					</td>
				</tr>
			</c:if>
		</table>
	</c:when>
	<c:otherwise>
<!-- 文件级 -->
    <ui:content title="${ lfn:message('km-archives:kmArchivesMain.fileLevel') }" expand="true">
		  <c:if test="${not empty kmArchivesMainForm.extendFilePath }">
	         <table class="tb_normal" width=100% >
	            <c:import url="/sys/property/include/sysProperty_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmArchivesMainForm" />
					<c:param name="fdDocTemplateId" value="${kmArchivesMainForm.docTemplateId}" />
				</c:import>
			</table>
          </c:if>
           <!-- 附件机制 -->
          <c:choose>
               <c:when test="${isConverting }">
               		<bean:message bundle="km-archives" key="kmArchivesFileTemplate.converting"/>
               </c:when>
               <c:otherwise>
             	<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
                     	<c:param name="fdKey" value="attArchivesMain" />
                      <c:param name="fdModelId" value="${param.fdId}" />
                      <c:param name="formBeanName" value="kmArchivesMainForm" />
             	</c:import>
               </c:otherwise>
          </c:choose>
     </ui:content>  
	<c:choose>
		<c:when test="${param.approveModel eq 'right'}">
			<c:choose>
				<c:when test="${kmArchivesMainForm.docStatus>='30' || kmArchivesMainForm.docStatus=='00'}">
					<!-- 流程 -->
					<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="kmArchivesMainForm" />
						<c:param name="fdKey" value="kmArchivesMain" />
						<c:param name="showHistoryOpers" value="true" />
						<c:param name="isExpand" value="true" />
						<c:param name="approveType" value="right" />
						<c:param name="needInitLbpm" value="true" />
					</c:import>
				</c:when>
				<c:otherwise>
					<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="kmArchivesMainForm" />
						<c:param name="fdKey" value="kmArchivesMain" />
						<c:param name="showHistoryOpers" value="true" />
						<c:param name="isExpand" value="true" />
						<c:param name="approveType" value="right" />
					</c:import>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmArchivesMainForm" />
				<c:param name="fdKey" value="kmArchivesMain" />
				<c:param name="showHistoryOpers" value="true" />
				<c:param name="isExpand" value="true" />
			</c:import>
		</c:otherwise>
	</c:choose>
	<c:import url="/km/archives/import/right_view.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="kmArchivesMainForm" />
	</c:import>

	<c:import url="/sys/readlog/import/sysReadLog_view.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="kmArchivesMainForm" />
	</c:import>

	<!-- 嵌入版本标签的代码 -->
	<c:import url="/sys/edition/import/sysEditionMain_view.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="kmArchivesMainForm" />
	</c:import>

	<!-- 嵌入版本标签的代码 -->
	<ui:content title="${ lfn:message('km-archives:py.BorrowRecord') }">
		<c:import url="/km/archives/km_archives_main/kmArchivesMain_borrowList.jsp"></c:import>
	</ui:content>
	</c:otherwise>
</c:choose>




				
				

				