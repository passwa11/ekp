<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Calendar"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:choose>
	<c:when test="${param.contentType eq 'xform'}">
		<table class="tb_normal" width="100%">
           <tr>
               <td class="td_normal_title" width="15%">
                   ${lfn:message('km-archives:kmArchivesMain.docSubject')}
               </td>
               <td colspan="3" width="85.0%">
                   <xform:text property="docSubject" showStatus="edit" style="width:95%;" />
               </td>
           </tr>
           <tr>
               <td class="td_normal_title" width="15%">
                   ${lfn:message('km-archives:kmArchivesMain.docTemplate')}
               </td>
               <td width="35%">
               	<html:hidden property="docTemplateId" />
                   <xform:dialog propertyId="docTemplateId" propertyName="docTemplateName" showStatus="view" style="width:95%;">
                   </xform:dialog>
               </td>
               <td class="td_normal_title" width="15%">
                   ${lfn:message('km-archives:kmArchivesMain.docNumber')}
               </td>
               <td width="35%">
               	<c:if test="${kmArchivesMainForm.docStatus==10 || kmArchivesMainForm.docStatus==null || kmArchivesMainForm.docStatus=='' }">
               		${lfn:message('km-archives:kmArchivesMain.no.per')}
               	</c:if>
                   <xform:text property="docNumber" showStatus="view" style="width:95%;" />
               </td>
           </tr>
           <tr>
               <td class="td_normal_title" width="15%">
                   ${lfn:message('km-archives:kmArchivesMain.fdLibrary')}
               </td>
               <td width="35%">
                   <xform:select property="fdLibrary" showStatus="edit" subject="${lfn:message('km-archives:kmArchivesMain.fdLibrary')}"  htmlElementProperties="id=kmArchivesAtrSelect">
                       <xform:beanDataSource serviceBean="kmArchivesLibraryService" selectBlock="fdName" orderBy="fdOrder" />
                   </xform:select>
               </td>
               <td class="td_normal_title" width="15%">
                   ${lfn:message('km-archives:kmArchivesMain.fdVolumeYear')}
               </td>
               <td width="35%">
               	<xform:select property="fdVolumeYear" htmlElementProperties="id=kmArchivesAtrSelect">
               	<% int nowYear = Calendar.getInstance().get(Calendar.YEAR);
               		for(int x = nowYear;x>=1967;x--) { 
               			pageContext.setAttribute("selectYearIndex",x);%>
               			<xform:simpleDataSource value="${selectYearIndex }"></xform:simpleDataSource>
               		<%} %>
               	</xform:select>
               </td>
           </tr>
            <tr>
            	<td class="td_normal_title" width="15%">
                   ${lfn:message('km-archives:kmArchivesMain.fdUnit')}
               </td>
               <td width="35%">
                   <xform:select property="fdUnit" showStatus="edit" subject="${lfn:message('km-archives:kmArchivesMain.fdUnit')}" htmlElementProperties="id=kmArchivesAtrSelect">
                       <xform:beanDataSource serviceBean="kmArchivesUnitService" selectBlock="fdName" whereBlock="" orderBy="fdOrder asc" />
                   </xform:select>
               </td>
               <td class="td_normal_title" width="15%">
                   ${lfn:message('km-archives:kmArchivesMain.fdStorekeeper')}
               </td>
               <td width="35%">
                   <xform:address propertyId="fdStorekeeperId" propertyName="fdStorekeeperName" orgType="ORG_TYPE_PERSON|ORG_TYPE_POST" showStatus="edit" required="true" subject="${lfn:message('km-archives:kmArchivesMain.fdStorekeeper')}" style="width:95%;" />
               </td>
           </tr>
            <tr>
            	<td class="td_normal_title" width="15%">
                   ${lfn:message('km-archives:kmArchivesMain.fdFileDate')}
               </td>
               <td width="35%">
                   <xform:datetime onValueChange="changeFileDate" required="true" property="fdFileDate" showStatus="edit" dateTimeType="date" style="width:95%;" />
               </td>
               <td class="td_normal_title" width="15%">
                   ${lfn:message('km-archives:kmArchivesMain.fdDenseLevel')}
               </td>
               <td width="35%">
                   <xform:select property="fdDenseId" showStatus="edit" subject="${lfn:message('km-archives:kmArchivesMain.fdDenseLevel')}" htmlElementProperties="id=kmArchivesAtrSelect">
                       <xform:beanDataSource serviceBean="kmArchivesDenseService" orderBy="fdOrder" />
                   </xform:select>
               </td>
           </tr>
           <tr>
               <td class="td_normal_title" width="15%">
                   ${lfn:message('km-archives:kmArchivesMain.fdPeriod')}
               </td>
               <td width="35%">
                   <xform:select property="fdPeriod" showStatus="edit" subject="${lfn:message('km-archives:kmArchivesMain.fdPeriod')}" onValueChange="changePeriod" htmlElementProperties="id=kmArchivesAtrSelect">
                       <xform:beanDataSource serviceBean="kmArchivesPeriodService" selectBlock="fdSaveLife,fdName" whereBlock="" orderBy="fdOrder" />
                   </xform:select>
               </td>
               <td class="td_normal_title" width="15%">
                   ${lfn:message('km-archives:kmArchivesMain.fdValidityDate')}
               </td>
               <td width="35%">
                   <xform:datetime onValueChange="null" property="fdValidityDate" showStatus="edit" dateTimeType="date" style="width:95%;" />
               </td>
           </tr>
           <tr>
               <td class="td_normal_title" width="15%">
                   ${lfn:message('km-archives:kmArchivesMain.fdRemarks')}
               </td>
               <td colspan="3" width="85.0%">
                   <xform:textarea property="fdRemarks" showStatus="edit" style="width:95%;" />
               </td>
           </tr>
           <tr>
               <td class="td_normal_title" width="15%">
                   ${lfn:message('km-archives:kmArchivesMain.docCreator')}
               </td>
               <td width="35%">
               	<xform:text property="docCreatorName" showStatus="view" style="width:95%;" />
               </td>
               <td class="td_normal_title" width="15%">
                   ${lfn:message('km-archives:kmArchivesMain.docCreateTime')}
               </td>
               <td width="35%">
                   <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
               </td>
           </tr>
       </table>
		<br>
	</c:when>
	<c:otherwise>
		<ui:content title="${ lfn:message('km-archives:kmArchivesMain.fileLevel') }" expand="true">
			<table class="tb_normal" width="100%">
				<c:if test="${not empty kmArchivesMainForm.extendFilePath }">
					<c:import url="/sys/property/include/sysProperty_edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="kmArchivesMainForm" />
						<c:param name="fdDocTemplateId" value="${kmArchivesMainForm.docTemplateId}" />
					</c:import>
				</c:if>
				<!-- 附件机制 -->
				<tr>
					<td class="td_normal_title" width="15%">
						${ lfn:message('km-archives:kmArchivesMain.attachement') }
					</td>
					<td colspan="3" width="85.0%">
						<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
							<c:param name="fdKey" value="attArchivesMain" />
							<c:param name="formBeanName" value="kmArchivesMainForm" />
							<c:param name="fdRequired" value="true" />
						</c:import>
					</td>
				</tr>
			</table>
		</ui:content>
		<c:choose>
			<c:when test="${param.approveModel eq 'right'}">
				<!-- 流程 -->
				<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmArchivesMainForm" />
					<c:param name="fdKey" value="kmArchivesMain" />
					<c:param name="showHistoryOpers" value="true" />
					<c:param name="isExpand" value="true" />
					<c:param name="approveType" value="right" />
				</c:import>
			</c:when>
			<c:otherwise>
				<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmArchivesMainForm" />
					<c:param name="fdKey" value="kmArchivesMain" />
					<c:param name="showHistoryOpers" value="true" />
					<c:param name="isExpand" value="true" />
				</c:import>
			</c:otherwise>	
		</c:choose>
		<c:import url="/km/archives/import/right_edit.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmArchivesMainForm" />
		</c:import>
		<!-- 嵌入 版本 模板标签的代码 -->
		<c:import url="/sys/edition/import/sysEditionMain_edit.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmArchivesMainForm" />
		</c:import>
	</c:otherwise>
</c:choose>