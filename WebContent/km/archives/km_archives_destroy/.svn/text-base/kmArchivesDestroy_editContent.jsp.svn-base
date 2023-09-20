<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:choose>
	<c:when test="${param.contentType eq 'xform'}">
	            	<div id="kmArchivesDiv">
	                <table class="tb_normal" width="100%">
	                	<tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesDestroy.docSubject')}
                            </td>
                            <td colspan="3">
                                <xform:text property="docSubject" subject="${lfn:message('km-archives:kmArchivesDestroy.docSubject')}" showStatus="edit" style="width:95%;" required="true"/>
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
                                <xform:address propertyId="docDeptId" propertyName="docDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="edit" style="width:95%;" />
                            </td>
		                </tr>
		                <tr>
							<td class="td_normal_title" width="15%">
								${lfn:message('km-archives:kmArchivesBorrow.docTemplate')}</td>
							<td width="35%">
								<div id="selectTemplet"></div>
							</td>
							<td class="td_normal_title" width="15%">
		                        ${lfn:message('km-archives:kmArchivesDestroy.docCreateTime')}
		                    </td>
		                    <td width="35%">
		                        <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
		                    </td>
						</tr>
		                <tr>
		                	<td colspan="4">
		                		<table class="tb_normal" width=100% style="border: none">
									<tr style="border: none">
										<td style="border: none">
											<c:import url="/km/archives/km_archives_destroy_option/kmArchMainOption_edit.jsp"
												charEncoding="UTF-8">
											</c:import>
										</td>
									</tr>
								</table>
		                	</td>
		            	</tr>
		                <tr>
		                    <td class="td_normal_title" width="15%">
		                        ${lfn:message('km-archives:kmArchivesDestroy.fdDestroyIdea')}
		                    </td>
		                    <td colspan="3" width="85.0%">
		                        <xform:textarea property="fdDestroyIdea" showStatus="edit" style="width:95%;" />
		                    </td>
		                </tr>
		                <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesDestroy.attDestroy')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
                                    <c:param name="fdKey" value="attDestroy" />
                                    <c:param name="formBeanName" value="kmArchivesDestroyForm" />
                                    <c:param name="fdRequired" value="false" />
                                    <c:param name="fdMulti" value="true" />
                                </c:import>
                            </td>
                        </tr>
	          		</table>
	          		</div>
	</c:when>
	<c:when test="${param.contentType eq 'other'}">
		<c:if test="${param.approveModel ne 'right'}">
			<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmArchivesDestroyForm" />
				<c:param name="fdKey" value="kmArchivesDestroy" />
				<c:param name="showHistoryOpers" value="true" />
				<c:param name="isExpand" value="true" />
			</c:import>
		</c:if>
		<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
            <c:param name="formName" value="kmArchivesDestroyForm" />
            <c:param name="moduleModelName" value="com.landray.kmss.km.archives.model.KmArchivesDestroy" />
        </c:import>
	</c:when>
</c:choose>