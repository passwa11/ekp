<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:choose>
	<c:when test="${param.contentType eq 'xform'}">
		<ui:content title="${lfn:message('hr-ratify:py.BiaoDanNeiRong')}" expand="true" toggle="false" >
   			<table class="tb_normal" width=100%>
        		<%-- 标题--%>
            	<tr>
	           		<td align="right" style="border-right: 0px;" width=15%>
	                	${lfn:message('hr-ratify:hrRatifyMain.docSubject')}
	                </td>
	              	<td style="border-left: 0px !important;">
	            		<div id="_xform_docSubject" _xform_type="text">
	                      	<c:if test="${hrRatifyEntryForm.titleRegulation==null || hrRatifyEntryForm.titleRegulation=='' }">
								<xform:text property="docSubject" style="width:80%;" className="inputsgl" showStatus="edit" />
							</c:if>
							<c:if test="${hrRatifyEntryForm.titleRegulation!=null && hrRatifyEntryForm.titleRegulation!='' }">
								<xform:text property="docSubject" style="width:80%;height:auto;color:#333;" className="inputsgl" showStatus="readOnly" value="${lfn:message('hr-ratify:hrRatifyMain.docSubject.info') }" />
							</c:if>
							<span id="checkBoxSpan" style="position:relative;">
								<xform:checkbox property="fdIsRecruit" onValueChange="SetEntryNameField" htmlElementProperties="id='isRecruit'" showStatus="edit" value="${hrRatifyEntryForm.fdIsRecruit == null ? true : hrRatifyEntryForm.fdIsRecruit }">
									<xform:simpleDataSource value="true">引入待确认员工档案</xform:simpleDataSource>
								</xform:checkbox>
							</span>
	          			</div>
	             	</td>
               	</tr>
          	</table>
            <br>
         	<c:if test="${hrRatifyEntryForm.docUseXform == 'false'}">
              	<table class="tb_normal" width=100%>
                	<tr>
              			<td colspan="2">
                         	<kmss:editor property="docXform" width="95%" />
                     	</td>
                 	</tr>
              	</table>
          	</c:if>
            <c:if test="${hrRatifyEntryForm.docUseXform == 'true' || empty hrRatifyEntryForm.docUseXform}">
                <c:import url="/sys/xform/include/sysForm_edit.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="hrRatifyEntryForm" />
                    <c:param name="fdKey" value="${fdTempKey }" />
                    <c:param name="useTab" value="false" />
                </c:import>
            </c:if>
     	</ui:content>
                        
     	<ui:content title="${lfn:message('hr-ratify:py.GeRenXinXi') }" expand="true">
     		<div>
       			<dl>
              		<dt>
                   		${lfn:message('hr-ratify:py.JiBenXinXi') }
             		</dt>
               		<dd>
                 		<c:import url="/hr/ratify/hr_ratify_entry/hrRatifyEntry_baseInfoEdit.jsp" charEncoding="UTF-8"></c:import>
                  	</dd>
              		<dt>
						${lfn:message('hr-ratify:py.LianXiXinXi') }
					</dt>
                  	<dd>
                   		<c:import url="/hr/ratify/hr_ratify_entry/hrRatifyEntry_linkInfoEdit.jsp" charEncoding="UTF-8"></c:import>
                  	</dd>
                  	<dt>
                    	${lfn:message('hr-ratify:hrRatifyEntry.fdHistory') }
                 	</dt>
                 	<dd>
                    	<c:import url="/hr/ratify/hr_ratify_entry/hrRatifyEntry_historyEdit.jsp" charEncoding="UTF-8"></c:import>
              		</dd>
                  	<dt>
                    	${lfn:message('hr-ratify:hrRatifyEntry.fdEducations') }
                 	</dt>
               		<dd>
                		<c:import url="/hr/ratify/hr_ratify_entry/hrRatifyEntry_educationEdit.jsp" charEncoding="UTF-8"></c:import>
                	</dd>
                	<dt>
                		${lfn:message('hr-ratify:hrRatifyEntry.fdCertificate') }
              		</dt>
               		<dd>
                		<c:import url="/hr/ratify/hr_ratify_entry/hrRatifyEntry_certificateEdit.jsp" charEncoding="UTF-8"></c:import>
                	</dd>
                	<dt>
                		${lfn:message('hr-ratify:hrRatifyEntry.fdRewardsPunishments') }
               		</dt>
                	<dd>
                		<c:import url="/hr/ratify/hr_ratify_entry/hrRatifyEntry_rewPuiEdit.jsp" charEncoding="UTF-8"></c:import>
                	</dd>
             	</dl>
        	</div>
    	</ui:content>
    	<c:import url="/hr/ratify/hr_ratify_entry/hrRatifyEntryDetail_edit.jsp" charEncoding="UTF-8"/>
	</c:when>
	<c:when test="${param.contentType eq 'baseInfo'}">
		<ui:content title="${ lfn:message('hr-ratify:py.JiBenXinXi') }" expand="true">
       		<table class="tb_normal" width="100%">
	            <tr>
	       			<td class="td_normal_title" width="15%">
	            		${lfn:message('hr-ratify:hrRatifyMKeyword.docKeyword')}
	            	</td>
	            	<td colspan="3">
	            		<xform:text property="fdKeywordNames" style="width:97%" />
	            	</td>
	            </tr>
           		<tr>
              		<td class="td_normal_title" width="15%">
                         ${lfn:message('hr-ratify:hrRatifyMain.docTemplate')}
            		</td>
           			<td colspan="3">
	               		<%-- 分类模板--%>
	                	<div id="_xform_docTemplateId" _xform_type="dialog">
	                  		<html:hidden property="docTemplateId" /> 
							<c:out value="${ hrRatifyEntryForm.docTemplateName}"/>
	                 	</div>
           			</td>
       			</tr>
           		<tr>
           			<td class="td_normal_title" width="15%">
                    	${lfn:message('hr-ratify:hrRatifyMain.docCreator')}
                  	</td>
              		<td width="35%">
                		<%-- 创建人--%>
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${hrRatifyEntryForm.docCreatorId}" personName="${hrRatifyEntryForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('hr-ratify:hrRatifyMain.docNumber')}
                    </td>
                    <td width="35%">
                        <%-- 编号--%>
                        <div id="_xform_docNumber" _xform_type="text">
                            <c:if test="${not empty hrRatifyEntryForm.docNumber}">
								<xform:text property="docNumber" showStatus="readOnly" style="width:95%;" />
							</c:if>	
							<c:if test="${empty hrRatifyEntryForm.docNumber}">
								<span style="color: #868686;">${lfn:message("hr-ratify:hrRatifyMain.docNumber.title")}</span>
							</c:if>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('hr-ratify:hrRatifyMain.fdDepartment')}
                    </td>
                    <td width="35%">
                        <%-- 部门--%>
                        <div id="_xform_fdDepartmentId" _xform_type="address">
                            <xform:address propertyId="fdDepartmentId" propertyName="fdDepartmentName" orgType="ORG_TYPE_ALL" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('hr-ratify:hrRatifyMain.docCreateTime')}
                    </td>
                    <td width="35%">
                        <%-- 创建时间--%>
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('hr-ratify:hrRatifyMain.docStatus')}
                    </td>
                    <td width="35%">
                        <%-- 文档状态--%>
                        <div id="_xform_docStatus" _xform_type="select">
                            <xform:select property="docStatus" htmlElementProperties="id='docStatus'" showStatus="view">
                                <xform:enumsDataSource enumsType="hr_ratify_doc_status" />
                            </xform:select>
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('hr-ratify:hrRatifyMain.docPublishTime')}
                    </td>
                    <td width="35%">
                        <%-- 结束时间--%>
                        <div id="_xform_docPublishTime" _xform_type="datetime">
                            <xform:datetime property="docPublishTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('hr-ratify:hrRatifyMain.fdFeedback')}
                    </td>
                    <td colspan="3">
                        <%-- 实施反馈人--%>
                        <div id="_xform_fdFeedbackIds" _xform_type="address">
                            <xform:address propertyId="fdFeedbackIds" propertyName="fdFeedbackNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
            </table>
        </ui:content>
	</c:when>
	<c:when test="${param.contentType eq 'other'}">
		<c:if test="${param.approveModel ne 'right'}">
			<c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="hrRatifyEntryForm" />
                <c:param name="fdKey" value="${fdTempKey }" />
                <c:param name="isExpand" value="false" />
            </c:import>
		</c:if>
		<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
            <c:param name="formName" value="hrRatifyEntryForm" />
            <c:param name="moduleModelName" value="com.landray.kmss.hr.ratify.model.HrRatifyEntry" />
        </c:import>
	</c:when>
</c:choose>