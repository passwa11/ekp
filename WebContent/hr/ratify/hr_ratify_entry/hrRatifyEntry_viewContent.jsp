<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<input type="hidden" id="isRecruit" value="${hrRatifyEntryForm.fdIsRecruit }"/>
<c:choose>
	<c:when test="${param.contentType eq 'xform'}">
		<div>
			<c:if test="${hrRatifyEntryForm.docUseXform == 'false'}">
 				<table class="tb_normal" width=100%>
               		<tr>
                    	<td colspan="4">${hrRatifyEntryForm.docXform}</td>
                	</tr>
             	</table>
          	</c:if>
           	<c:if test="${hrRatifyEntryForm.docUseXform == 'true' || empty hrRatifyEntryForm.docUseXform}">
           		<c:import url="/sys/xform/include/sysForm_view.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="hrRatifyEntryForm" />
                    <c:param name="fdKey" value="hrRatifyMain" />
                    <c:param name="useTab" value="false" />
                </c:import>
            </c:if>
		</div>
		<ui:content title="${lfn:message('hr-ratify:py.GeRenXinXi') }" expand="true">
	    	
			<div class="lui_tabpage_float_content_l">
				<div class="lui_tabpage_float_content_r">
					<div class="lui_tabpage_float_content_c">
						<div>
							<div data-lui-mark="panel.content.inside" class="lui_panel_content_inside">
								<!-- 个人经历 列表 Starts -->
								<div id="personExperiences" class="staff_resume_itemlist_content">
									<dl>
										<dt>
											<h3 class="reusme_item_title" id="experienceContract">
												<span class="lui_icon_m icon_contract"></span><span>${lfn:message('hr-ratify:py.JiBenXinXi') }</span>
											</h3>
										</dt>
										<dd>
											<!-- 个人基本信息 Starts -->
											<c:import url="/hr/ratify/hr_ratify_entry/hrRatifyEntry_viewPersonBaseInfo.jsp" charEncoding="UTF-8">
	    									</c:import>
											<!--个人基本信息 End-->
										</dd>
										<dt>
											<h3 class="reusme_item_title" id="experienceTraining">
												<span class="lui_icon_m icon_train"></span><span>${lfn:message('hr-ratify:py.LianXiXinXi') }</span>
											</h3>
										</dt>
										<dd>
											<!-- 联系信息 Starts -->
											<c:import url="/hr/ratify/hr_ratify_entry/hrRatifyEntry_viewLinkInfo.jsp" charEncoding="UTF-8">
	    									</c:import>
											<!-- 联系信息 End-->
										</dd>
										<dt>
											<h3 class="reusme_item_title" id="experienceWork">
												<span class="lui_icon_m icon_work"></span><span>${ lfn:message('hr-ratify:table.hrRatifyHistory') }</span>
											</h3>
										</dt>
										<dd>
											<!-- 工作经历 Starts -->
											<c:import url="/hr/ratify/hr_ratify_entry/hrRatifyEntry_viewTable.jsp" charEncoding="UTF-8">
												<c:param name="type" value="history" />
											</c:import>
											<!--工作经历 End-->
										</dd>
										<dt>
											<h3 class="reusme_item_title" id="experienceEducation">
												<span class="lui_icon_m icon_teach"></span><span>${ lfn:message('hr-ratify:table.hrRatifyEduExp') }</span>
											</h3>
										</dt>
										<dd>
											<!-- 教育经历 Starts -->
											<c:import url="/hr/ratify/hr_ratify_entry/hrRatifyEntry_viewTable.jsp" charEncoding="UTF-8">
												<c:param name="type" value="eduExp" />
											</c:import>
											<!-- 教育经历 End-->
										</dd>
										<dt>
											<h3 class="reusme_item_title" id="experienceQualification">
												<span class="lui_icon_m icon_catalog"></span><span>${ lfn:message('hr-ratify:table.hrRatifyCertifi') }</span>
											</h3>
										</dt>
										<dd>
											<!-- 资格证书 Starts -->
											<c:import url="/hr/ratify/hr_ratify_entry/hrRatifyEntry_viewTable.jsp" charEncoding="UTF-8">
												<c:param name="type" value="certifi" />
											</c:import>
											<!-- 资格证书 End-->
										</dd>
										<dt>
											<h3 class="reusme_item_title" id="experienceBonusMalus">
												<span class="lui_icon_m icon_catalog"></span><span>${ lfn:message('hr-ratify:table.hrRatifyRewPuni') }</span>
											</h3>
										</dt>
										<dd>
											<!-- 奖励信息 Starts -->
											<c:import url="/hr/ratify/hr_ratify_entry/hrRatifyEntry_viewTable.jsp" charEncoding="UTF-8">
												<c:param name="type" value="rewPuni" />
											</c:import>
											<!-- 奖励信息 End -->
										</dd>
									</dl>
								</div>
								<!-- 个人经历 列表 Ends -->
							</div>
							<div data-lui-mark="panel.content.operation" class="lui_portlet_operations clearfloat"> </div>
						</div>
					</div>
				</div>
			</div>
 		</ui:content>
 		<c:if test="${param.approveModel ne 'right'}">
 			<ui:content title="${ lfn:message('hr-ratify:py.JiBenXinXi') }" expand="false">
            	<table class="tb_normal" width="100%">
             		<tr>
	                	<td class="td_normal_title" width="15%">
	                        ${lfn:message('hr-ratify:hrRatifyMain.docSubject')}
	                    </td>
	                    <td colspan="3">
	                        <%-- 标题--%>
	                        <div id="_xform_docSubject" _xform_type="text">
	                            <xform:text property="docSubject" showStatus="view" style="width:95%;" />
	                        </div>
	                    </td>
	                </tr>
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
	                            <xform:text property="docNumber" showStatus="view" style="width:95%;" />
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
	                        ${lfn:message('hr-ratify:hrRatifyMain.docPublishTime')}
	                    </td>
	                    <td width="35%">
	                        <%-- 结束时间--%>
	                        <div id="_xform_docPublishTime" _xform_type="datetime">
	                            <xform:datetime property="docPublishTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
	                        </div>
	                    </td>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('hr-ratify:hrRatifyMain.fdFeedback')}
	                    </td>
	                    <td width="35%">
	                        <%-- 实施反馈人--%>
	                        <div id="_xform_fdFeedbackIds" _xform_type="address">
	                            <xform:address propertyId="fdFeedbackIds" propertyName="fdFeedbackNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="view" style="width:95%;" />
	                        </div>
	                    </td>
	                </tr>
				</table>
       		</ui:content>
 		</c:if>
	</c:when>
	<c:when test="${param.contentType eq 'other'}">
		<c:if test="${param.approveModel ne 'right'}">
			<c:choose>
                <c:when test="${hrRatifyEntryForm.docUseXform == 'true' || empty hrRatifyEntryForm.docUseXform}">
                    <c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
                        <c:param name="formName" value="hrRatifyEntryForm" />
                        <c:param name="fdKey" value="${fdTempKey }" />
                        <c:param name="isExpand" value="true" />
                        <c:param name="onClickSubmitButton" value="Com_Submit(document.hrRatifyEntryForm, 'update');" />
                    </c:import>
                </c:when>
                <c:otherwise>
                    <c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
                        <c:param name="formName" value="hrRatifyEntryForm" />
                        <c:param name="fdKey" value="${fdTempKey }" />
                        <c:param name="isExpand" value="true" />
                        <c:param name="onClickSubmitButton" value="Com_Submit(document.hrRatifyEntryForm, 'update');" />
                    </c:import>
                </c:otherwise>
            </c:choose>
		</c:if>
		<c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
            <c:param name="formName" value="hrRatifyEntryForm" />
            <c:param name="moduleModelName" value="com.landray.kmss.hr.ratify.model.HrRatifyEntry" />
        </c:import>

        <c:import url="/sys/readlog/import/sysReadLog_view.jsp" charEncoding="UTF-8">
            <c:param name="formName" value="hrRatifyEntryForm" />
        </c:import>

        <c:import url="/sys/circulation/import/sysCirculationMain_view.jsp" charEncoding="UTF-8">
            <c:param name="formName" value="hrRatifyEntryForm" />
        </c:import>
		<%-- 反馈记录 --%>
		<c:import url="/hr/ratify/hr_ratify_feedback/hrRatifyFeedback_tab.jsp" charEncoding="UTF-8">
            <c:param name="formName" value="hrRatifyEntryForm" />
        </c:import>
        <%-- 发起沟通 --%>
		<c:if test="${hrRatifyEntryForm.docStatus!='10'}">
			<kmss:ifModuleExist path = "/km/collaborate/">
				<%request.setAttribute("communicateTitle",ResourceUtil.getString("hrRatifyMain.communicateTitle","hr-ratify"));%>
					<c:import url="/km/collaborate/import/kmCollaborateMain_view.jsp" charEncoding="UTF-8">
						<c:param name="commuTitle" value="${communicateTitle}" />
						<c:param name="formName" value="hrRatifyEntryForm" />
					</c:import>
			</kmss:ifModuleExist>   
		</c:if>
		<c:import url="/sys/bookmark/import/bookmark_bar.jsp" charEncoding="UTF-8">
		    <c:param name="fdSubject" value="${hrRatifyEntryForm.docSubject}" />
		    <c:param name="fdModelId" value="${hrRatifyEntryForm.fdId}" />
		    <c:param name="fdModelName" value="com.landray.kmss.hr.ratify.model.HrRatifyEntry" />
		</c:import>
	</c:when>
</c:choose>