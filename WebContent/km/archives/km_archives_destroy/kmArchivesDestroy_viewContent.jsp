<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:choose>
	<c:when test="${param.contentType eq 'xform'}">
                   <table class="tb_normal" width="100%">
                   	<tr>
                           <td class="td_normal_title" width="15%">
                               ${lfn:message('km-archives:kmArchivesDestroy.docSubject')}
                           </td>
                           <td colspan="3">
                               <xform:text property="docSubject" showStatus="view" style="width:95%;" />
                           </td>
                       </tr>
                   	<tr>
		                <td class="td_normal_title" width="15%">
		                    ${lfn:message('km-archives:kmArchivesDestroy.docCreator')}
		                </td>
		                <td width="35%">
		                    <ui:person personId="${kmArchivesDestroyForm.docCreatorId}" personName="${kmArchivesDestroyForm.docCreatorName}" />
		                </td>
		                <td class="td_normal_title" width="15%">
                            ${lfn:message('km-archives:kmArchivesBorrow.docDept')}
                        </td>
                        <td width="35%">
                            <xform:address propertyId="docDeptId" propertyName="docDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="view" style="width:95%;" />
                        </td>
		            </tr>
		            <tr>
		            	<td class="td_normal_title" width="15%">
                            ${lfn:message('km-archives:kmArchivesDestroy.docTemplate')}
                        </td>
                        <td width="35%">
                            <xform:dialog propertyId="docTemplateId" propertyName="docTemplateName" showStatus="view" style="width:95%;">
                            </xform:dialog>
                        </td>
                        <td class="td_normal_title" width="15%">
		                    ${lfn:message('km-archives:kmArchivesDestroy.docCreateTime')}
		                </td>
		                <td width="35%">
		                    <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
		                </td>
		            </tr>
		            <tr>
                       	<td colspan="4" width="100%">
                       		<center style="margin-top: 20px; margin-bottom: 20px">
                        		<span><b>${lfn:message('km-archives:table.kmArchivesDestroyDetails')}</b>
                        		</span>
                       		</center>
                       		<table class="tb_normal" width="100%" id="TABLE_DocList">
								<tr>
									<td align="center" class="td_normal_title" width="5%">
						 				${lfn:message('page.serial')}
						 			</td>
						 			<td align="center" class="td_normal_title">
										<bean:message bundle="km-archives" key="kmArchivesDestroy.fdArchivesName" />
									</td>
									<td align="center" class="td_normal_title">
										<bean:message bundle="km-archives" key="kmArchivesDestroy.fdArchivesNumber" />
									</td>
									<td align="center" class="td_normal_title">
										<bean:message bundle="km-archives" key="kmArchivesDestroy.fdCategoryName" />
									</td>
									<td align="center" class="td_normal_title">
										<bean:message bundle="km-archives" key="kmArchivesDestroy.fdReturnDate" />
									</td>
									<td align="center" class="td_normal_title">
										<bean:message bundle="km-archives" key="kmArchivesDestroy.fdReturnPerson" />
									</td>
								</tr>
								
								<c:forEach items="${kmArchivesDestroyForm.fdDestroyDetail_Form }" var="item" varStatus="varStatus">
							 		<tr>
							 			<td align="center">
											${varStatus.index+1 }
										</td>
										<td align="center">
											${item.fdArchivesName }
										</td>
										<td align="center">
											${item.fdArchivesNumber }
										</td>
										<td align="center">
											${item.fdCategoryName }
										</td>
										<td align="center">
											${item.fdReturnDate }
										</td>
										<td align="center">
											${item.fdReturnPerson }
										</td>
							 		</tr>
								</c:forEach>
							</table>
						</td>
					</tr>
		            <tr>
		                <td class="td_normal_title" width="15%">
		                    ${lfn:message('km-archives:kmArchivesDestroy.fdDestroyIdea')}
		                </td>
		                <td colspan="3" width="85.0%">
		                	<div style="width:95%;">
		                		<c:out value="${kmArchivesDestroyForm.fdDestroyIdea}"></c:out>
		                	</div>
		                </td>
		            </tr>
		            <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('km-archives:kmArchivesDestroy.attDestroy')}
	                    </td>
	                    <td colspan="3" width="85.0%">
	                        <c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
	                            <c:param name="fdKey" value="attDestroy" />
	                            <c:param name="formBeanName" value="kmArchivesDestroyForm" />
	                            <c:param name="fdRequired" value="true" />
	                            <c:param name="fdMulti" value="true" />
	                        </c:import>
	                    </td>
	                </tr>
				</table>
	</c:when>
	<c:when test="${param.contentType eq 'other'}">
		<c:choose>
			<c:when test="${param.approveModel eq 'right'}">
				<c:choose>
					<c:when test="${kmArchivesDestroyForm.docStatus>='30' || kmArchivesDestroyForm.docStatus=='00'}">
						<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmArchivesDestroyForm" />
							<c:param name="fdKey" value="kmArchivesDestroy" />
							<c:param name="showHistoryOpers" value="true" />
							<c:param name="isExpand" value="true" />
							<c:param name="approveType" value="right" />
							<c:param name="needInitLbpm" value="true" />
						</c:import>
					</c:when>
					<c:otherwise>
						<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmArchivesDestroyForm" />
							<c:param name="fdKey" value="kmArchivesDestroy" />
							<c:param name="showHistoryOpers" value="true" />
							<c:param name="isExpand" value="true" />
							<c:param name="approveType" value="right" />
						</c:import>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmArchivesDestroyForm" />
					<c:param name="fdKey" value="kmArchivesDestroy" />
					<c:param name="showHistoryOpers" value="true" />
					<c:param name="isExpand" value="true" />
				</c:import>
			</c:otherwise>
		</c:choose>
		
		<c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmArchivesDestroyForm" />
			<c:param name="moduleModelName" value="com.landray.kmss.km.archives.model.KmArchivesDestroy" />
		</c:import>
	</c:when>
</c:choose>

