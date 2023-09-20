<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/mobile/jsp/ajax-accept.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person" %>
    
        <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser());
        pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
        pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
        if(UserUtil.getUser().getFdParentOrg() != null) {
            pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
        } else {
            pageContext.setAttribute("currentOrg", "");
        } %>
    
    <template:include ref="mobile.edit" compatibleMode="true">
        <template:replace name="title">
          	 待入职员工信息
        </template:replace>
        <template:replace name="head">
            <style>
                .detailTips{
                				color: red;
                	    		font-weight: lighter;
                	    		display: inline-block;
                	    		font-size: 1rem;
                			}
                			.muiFormNoContent{
                				padding-left:1rem;
                				border-top:1px solid #ddd;
                				border-bottom: 1px solid #ddd;
                			 }
                			 .muiDocFrameExt{
                				margin-left: 0rem;
                			 }
                			 .muiDocFrameExt .muiDocInfo{
                				border: none;
                			 }
            </style>
            <script type="text/javascript">
                var formInitData = {

                };
                var lang = {
                    "the": "${lfn:message('page.the')}",
                    "row": "${lfn:message('page.row')}"
                };
                var messageInfo = {

                };

                var initData = {
                    contextPath: '${LUI_ContextPath}',
                };
                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/hr/ratify/hr_ratify_entry/", 'js', true);
                Com_IncludeFile("mobile_edit.js", "${LUI_ContextPath}/hr/ratify/resource/js/", 'js', true);
            </script>
        </template:replace>
        <template:replace name="content">
            <html:form action="/resource/hr/staff/hr_staff_entry_anonymous/hrStaffEntry.do?method=saveAnonymousByEntry">

                <div id="scrollView" data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin">
                    <div data-dojo-type="mui/panel/AccordionPanel">
                        <div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('hr-ratify:py.JiBenXinXi') }',icon:'mui-ul'">
                            <div class="muiFormContent">
                                <table class="muiSimple" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrStaffEntry.fdName')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdName" _xform_type="text">
                                                <xform:text property="fdName" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrStaffEntry.fdNameUsedBefore')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdName" _xform_type="text">
                                                <xform:text property="fdNameUsedBefore" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
								<c:choose>
									<c:when test="${hrStaffEntryForm.fdIsAllowModify ==false}">
									<tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrStaffEntry.fdPlanEntryDept')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdName" _xform_type="text">
                                                <xform:address propertyId="fdPlanEntryDeptId" propertyName="fdPlanEntryDeptName" mobile="true" orgType="ORG_TYPE_DEPT" showStatus="readOnly" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            	拟入职岗位
                                        </td>
                                        <td>
                                            <div id="_xform_fdName" _xform_type="text">
                                            	<xform:address propertyId="fdOrgPostIds" propertyName="fdOrgPostNames" mulSelect="true" mobile="true" orgType="ORG_TYPE_POST" showStatus="readOnly" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
									</c:when>
									<c:otherwise>
									<tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrStaffEntry.fdPlanEntryDept')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdName" _xform_type="text">
                                                <xform:address propertyId="fdPlanEntryDeptId" propertyName="fdPlanEntryDeptName" mobile="true" orgType="ORG_TYPE_DEPT" showStatus="readOnly" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            	拟入职岗位
                                        </td>
                                        <td>
                                            <div id="_xform_fdName" _xform_type="text">
                                            	<xform:address propertyId="fdOrgPostIds" propertyName="fdOrgPostNames" mulSelect="true" mobile="true" orgType="ORG_TYPE_POST" showStatus="readOnly" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
									</c:otherwise>
								</c:choose>
                                   
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrStaffEntry.fdSex')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdSex" _xform_type="radio">
                                                <xform:radio property="fdSex" htmlElementProperties="id='fdSex'" showStatus="edit" mobile="true">
                                                    <xform:enumsDataSource enumsType="sys_org_person_sex" />
                                                </xform:radio>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrStaffEntry.fdDateOfBirth')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdBirthDate" _xform_type="datetime">
                                                <xform:datetime property="fdDateOfBirth" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrRatifyEntry.fdIdCard')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdCardNo" _xform_type="text">
                                                <xform:text property="fdIdCard" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrRatifyEntry.fdWorkTime')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdJoinWork" _xform_type="datetime">
                                                <xform:datetime property="fdWorkTime" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrRatifyEntry.fdProfession')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdMajor" _xform_type="text">
                                                <xform:text property="fdProfession" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrRatifyEntry.fdNation')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdNationId" _xform_type="select">
                                                <xform:select property="fdNationId" htmlElementProperties="id='fdNationId'" showStatus="edit" mobile="true">
                                                    <xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType = 'fdNation' "/>
                                                </xform:select>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
	                                    <!-- 政治面貌 -->
										<td class="muiTitle">
											${lfn:message('hr-staff:hrStaffEntry.fdPoliticalLandscape')}
										</td>
										<td width="35%">
											<xform:select property="fdPoliticalLandscapeId" showStatus="edit" mobile="true">
												<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType = 'fdPoliticalLandscape'"></xform:beanDataSource>
											</xform:select>
										</td>
									</tr>
									<tr>
										<!-- 入团日期 -->
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffEntry.fdDateOfGroup" />
										</td>
										<td width="35%">
											<xform:datetime property="fdDateOfGroup" dateTimeType="date" showStatus="edit" mobile="true"></xform:datetime>
										</td>
									</tr>
									<tr>
										<!-- 入党日期 -->
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffEntry.fdDateOfParty" />
										</td>
										<td width="35%">
											<xform:datetime property="fdDateOfParty" dateTimeType="date" showStatus="edit" mobile="true"></xform:datetime>
										</td>
									</tr>
                                    <!-- 现居地 -->
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrRatifyEntry.fdLivingPlace')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdCurrentPlace" _xform_type="text">
                                                <xform:text property="fdLivingPlace" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrRatifyEntry.fdHighestDegree')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdAcademicId" _xform_type="select">
                                                <xform:select property="fdHighestDegreeId" htmlElementProperties="id='fdHighestDegreeId'" showStatus="edit" mobile="true">
                                                    <xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType = 'fdHighestDegree'"  />
                                                </xform:select>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrRatifyEntry.fdHighestEducation')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdEducationId" _xform_type="select">
                                                <xform:select property="fdHighestEducationId" htmlElementProperties="id='fdHighestEducationId'" showStatus="edit" mobile="true">
                                                    <xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType = 'fdHighestEducation'"  />
                                                </xform:select>
                                            </div>
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrRatifyEntry.fdMaritalStatus')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdMarriageId" _xform_type="select">
                                                <xform:select property="fdMaritalStatusId" htmlElementProperties="id='fdMarriageId'" showStatus="edit" mobile="true">
                                                    <xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" 	whereBlock="hrStaffPersonInfoSettingNew.fdType = 'fdMaritalStatus' " />
                                                </xform:select>
                                            </div>
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrRatifyEntry.fdHealth')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdMarriageId" _xform_type="select">
                                                <xform:select property="fdHealthId"  showStatus="edit" mobile="true">
													<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType='fdHealth'" orderBy="fdOrder"></xform:beanDataSource>
												</xform:select>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrStaffEntry.fdStature')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdMajor" _xform_type="text">
                                                <xform:text property="fdStature" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrStaffEntry.fdWeight')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdMajor" _xform_type="text">
                                                <xform:text property="fdWeight" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
									<tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrStaffEntry.fdNativePlace')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdNativePlace" _xform_type="text">
                                                <xform:text property="fdNativePlace" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
										<!-- 出生地 -->
										<td class="muiTitle">
											${lfn:message('hr-ratify:hrStaffEntry.fdHomeplace')}
										</td>
										<td width="35%">
											<xform:text property="fdHomeplace" style="width:98%;" showStatus="edit" mobile="true"/>
										</td>
									</tr>
									<tr>
										<!-- 户口性质 -->
										<td class="muiTitle">
											${lfn:message('hr-ratify:hrStaffEntry.fdAccountProperties')}
										</td>
										<td width="35%">
											<xform:text property="fdAccountProperties" style="width:98%;" showStatus="edit" mobile="true"/>
										</td>
									</tr>
									<tr>
										<!-- 户口所在地 -->
										<td width="15%" class="td_normal_title">
											${lfn:message('hr-ratify:hrStaffEntry.fdRegisteredResidence')}
										</td>
										<td width="35%">
											<xform:text property="fdRegisteredResidence" style="width:98%;" showStatus="edit" mobile="true" />
										</td>
									</tr>
									<tr>
										<!-- 户口所在派出所 -->
										<td width="15%" class="td_normal_title">
											${lfn:message('hr-ratify:hrStaffEntry.fdResidencePoliceStation')}
										</td>
										<td width="35%">
											<xform:text property="fdResidencePoliceStation" style="width:98%;" showStatus="edit" mobile="true" />
										</td>
									</tr>

								<c:choose>
									<c:when test="${hrStaffEntryForm.fdIsAllowModify ==false}">
										<tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrRatifyEntry.fdMobileNo')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdPhoneNum" _xform_type="text">
                                                <xform:text property="fdMobileNo" showStatus="readOnly" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrRatifyEntry.fdEmail')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdPhoneNum" _xform_type="text">
                                                <xform:text property="fdEmail" showStatus="readOnly" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
									</c:when>
									<c:otherwise>
										<tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrRatifyEntry.fdMobileNo')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdPhoneNum" _xform_type="text">
                                                <xform:text property="fdMobileNo" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrRatifyEntry.fdEmail')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdPhoneNum" _xform_type="text">
                                                <xform:text property="fdEmail" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
									</c:otherwise>
								</c:choose>
							
                                     <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrRatifyEntry.fdEmergencyContact')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdPhoneNum" _xform_type="text">
                                                <xform:text property="fdEmergencyContact" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrRatifyEntry.fdEmergencyContactPhone')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdPhoneNum" _xform_type="text">
                                                <xform:text property="fdEmergencyContactPhone" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrRatifyEntry.fdOtherContact')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdPhoneNum" _xform_type="text">
                                                <xform:text property="fdOtherContact" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    
                                    
                                     <tr>
                                        <td class="muiTitle" colspan="2">
                                            <div class="mblListItemLayoutLeft">
                                                ${lfn:message('hr-ratify:hrRatifyEntry.fdHistory')}
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <div class="detailTableContent">
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0" id="TABLE_DocList_fdHistory_Form">
                                                    <tr style="display:none;">
                                                        <td></td>
                                                    </tr>
                                                    <tr data-dojo-type="mui/form/Template" KMSS_IsReferRow="1" style="display:none;" border='0'>
                                                        <td class="detail_wrap_td">
                                                            <xform:text showStatus="noShow" property="fdHistory_Form[!{index}].fdId" />
                                                            <table class="muiSimple">
                                                                <tr celltr-title="true" onclick="expandRow(this);">
                                                                    <td colspan="2" align="left" valign="middle" class="muiDetailTableNo">
                                                                        <div class="muiDetailDisplayOpt muiDetailDown"></div>
                                                                        <span>${lfn:message('page.the')}!{index}${ lfn:message('page.row') }</span>
                                                                        <div class="muiDetailTableDel" onclick="delRowExpand('TABLE_DocList_fdHistory_Form',this);">
                                                                            <bean:message key="button.delete" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyHistory.fdName')}
                                                                    </td>
                                                                    <td>

                                                                        <div id="_xform_fdHistory_Form[!{index}].fdName" _xform_type="text">
                                                                            <xform:text property="fdHistory_Form[!{index}].fdName" showStatus="edit" required="true" subject="${lfn:message('hr-ratify:hrRecruitHistory.fdName')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyHistory.fdPost')}
                                                                    </td>
                                                                    <td>

                                                                        <div id="_xform_fdHistory_Form[!{index}].fdPost" _xform_type="text">
                                                                            <xform:text property="fdHistory_Form[!{index}].fdPost" showStatus="edit" subject="${lfn:message('hr-ratify:hrRecruitHistory.fdPost')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyHistory.fdStartDate')}
                                                                    </td>
                                                                    <td>

                                                                        <div id="_xform_fdHistory_Form[!{index}].fdStartDate" _xform_type="datetime">
                                                                            <xform:datetime property="fdHistory_Form[!{index}].fdStartDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyHistory.fdEndDate')}
                                                                    </td>
                                                                    <td>

                                                                        <div id="_xform_fdHistory_Form[!{index}].fdEndDate" _xform_type="datetime">
                                                                            <xform:datetime property="fdHistory_Form[!{index}].fdEndDate" showStatus="edit" required="true" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyHistory.fdDesc')}
                                                                    </td>
                                                                    <td>

                                                                        <div id="_xform_fdHistory_Form[!{index}].fdDesc" _xform_type="textarea">
                                                                            <xform:textarea property="fdHistory_Form[!{index}].fdDesc" showStatus="edit" subject="${lfn:message('hr-ratify:hrRecruitHistory.fdDesc')}" validators=" maxLength(2000)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyHistory.fdLeaveReason')}
                                                                    </td>
                                                                    <td>

                                                                        <div id="_xform_fdHistory_Form[!{index}].fdLeaveReason" _xform_type="textarea">
                                                                            <xform:textarea property="fdHistory_Form[!{index}].fdLeaveReason" showStatus="edit" subject="${lfn:message('hr-ratify:hrRecruitHistory.fdLeaveReason')}" validators=" maxLength(2000)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                    <c:forEach items="${hrStaffEntryForm.fdHistory_Form}" var="_xformEachBean" varStatus="vstatus">
                                                        <tr KMSS_IsContentRow="1">
                                                            <td class="detail_wrap_td">
                                                                <xform:text showStatus="noShow" property="fdHistory_Form[${vstatus.index}].fdId" />
                                                                <table class="muiSimple">
                                                                    <tr celltr-title="true" onclick="expandRow(this);">
                                                                        <td colspan="2" align="left" valign="middle" class="muiDetailTableNo">
                                                                            <div class="muiDetailDisplayOpt muiDetailDown"></div>
                                                                              <span>${lfn:message('page.the')}${vstatus.index+1}${ lfn:message('page.row') }</span>
                                                                            
                                                                            
                                                                            <div class="muiDetailTableDel" onclick="delRowExpand('TABLE_DocList_fdHistory_Form',this);">
                                                                                <bean:message key="button.delete" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyHistory.fdName')}
                                                                        </td>
                                                                        <td>

                                                                            <div id="_xform_fdHistory_Form[${vstatus.index}].fdName" _xform_type="text">
                                                                                <xform:text property="fdHistory_Form[${vstatus.index}].fdName" showStatus="edit" required="true" subject="${lfn:message('hr-ratify:hrRecruitHistory.fdName')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyHistory.fdPost')}
                                                                        </td>
                                                                        <td>

                                                                            <div id="_xform_fdHistory_Form[${vstatus.index}].fdPost" _xform_type="text">
                                                                                <xform:text property="fdHistory_Form[${vstatus.index}].fdPost" showStatus="edit" subject="${lfn:message('hr-ratify:hrRecruitHistory.fdPost')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyHistory.fdStartDate')}
                                                                        </td>
                                                                        <td>

                                                                            <div id="_xform_fdHistory_Form[${vstatus.index}].fdStartDate" _xform_type="datetime">
                                                                                <xform:datetime property="fdHistory_Form[${vstatus.index}].fdStartDate" showStatus="edit" required="true"  dateTimeType="date" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyHistory.fdEndDate')}
                                                                        </td>
                                                                        <td>

                                                                            <div id="_xform_fdHistory_Form[${vstatus.index}].fdEndDate" _xform_type="datetime">
                                                                                <xform:datetime property="fdHistory_Form[${vstatus.index}].fdEndDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyHistory.fdDesc')}
                                                                        </td>
                                                                        <td>

                                                                            <div id="_xform_fdHistory_Form[${vstatus.index}].fdDesc" _xform_type="textarea">
                                                                                <xform:textarea property="fdHistory_Form[${vstatus.index}].fdDesc" showStatus="edit" subject="${lfn:message('hr-ratify:hrRecruitHistory.fdDesc')}" validators=" maxLength(2000)" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyHistory.fdLeaveReason')}
                                                                        </td>
                                                                        <td>

                                                                            <div id="_xform_fdHistory_Form[${vstatus.index}].fdLeaveReason" _xform_type="textarea">
                                                                                <xform:textarea property="fdHistory_Form[${vstatus.index}].fdLeaveReason" showStatus="edit" subject="${lfn:message('hr-ratify:hrRecruitHistory.fdLeaveReason')}" validators=" maxLength(2000)" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </table>
                                            </div>
                                            <div data-dojo-type="sys/xform/mobile/controls/DetailTableAddButton" onclick="window.addRowExpand('TABLE_DocList_fdHistory_Form')">
                                                ${lfn:message('doclist.add')}(${lfn:message('hr-ratify:hrStaffEntry.fdHistory')})
                                            </div>
                                            <script type="text/javascript">
                                                Com_IncludeFile('doclist.js');
                                            </script>
                                            <script type="text/javascript">
                                                DocList_Info.push('TABLE_DocList_fdHistory_Form');
                                            </script>
                                            <input type="hidden" name="fdHistory_Flag" value="1" />
                                        </td>
                                    </tr>
                                    
                                    <tr>
                                        <td class="muiTitle" colspan="2">
                                            <div class="mblListItemLayoutLeft">
                                                ${lfn:message('hr-ratify:table.hrRatifyCertifi')}
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <div class="detailTableContent">
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0" id="TABLE_DocList_fdCertificate_Form">
                                                    <tr style="display:none;">
                                                        <td></td>
                                                    </tr>
                                                    <tr data-dojo-type="mui/form/Template" KMSS_IsReferRow="1" style="display:none;" border='0'>
                                                        <td class="detail_wrap_td">
                                                            <xform:text showStatus="noShow" property="fdCertificate_Form[!{index}].fdId" />
                                                            <table class="muiSimple">
                                                                <tr celltr-title="true" onclick="expandRow(this);">
                                                                    <td colspan="2" align="left" valign="middle" class="muiDetailTableNo">
                                                                        <div class="muiDetailDisplayOpt muiDetailDown"></div>
                                                                        <span>${lfn:message('page.the')}!{index}${ lfn:message('page.row') }</span>
                                                                        <div class="muiDetailTableDel" onclick="delRowExpand('TABLE_DocList_fdCertificate_Form',this);">
                                                                            <bean:message key="button.delete" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyCertifi.fdName')}
                                                                    </td>
                                                                    <td>

                                                                        <div id="_xform_fdCertificate_Form[!{index}].fdName" _xform_type="text">
                                                                            <xform:text property="fdCertificate_Form[!{index}].fdName" showStatus="edit" required="true" subject="${lfn:message('hr-ratify:hrRecruitCertifi.fdName')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyCertifi.fdIssuingUnit')}
                                                                    </td>
                                                                    <td>

                                                                        <div id="_xform_fdCertificate_Form[!{index}].fdIssuingUnit" _xform_type="text">
                                                                            <xform:text property="fdCertificate_Form[!{index}].fdIssuingUnit" showStatus="edit" required="true" subject="${lfn:message('hr-ratify:hrRecruitCertifi.fdIssuingUnit')}" validators=" maxLength(100)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyCertifi.fdIssueDate')}
                                                                    </td>
                                                                    <td>

                                                                        <div id="_xform_fdCertificate_Form[!{index}].fdIssueDate" _xform_type="datetime">
                                                                            <xform:datetime property="fdCertificate_Form[!{index}].fdIssueDate" showStatus="edit" required="true" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyCertifi.fdInvalidDate')}
                                                                    </td>
                                                                    <td>

                                                                        <div id="_xform_fdCertificate_Form[!{index}].fdInvalidDate" _xform_type="datetime">
                                                                            <xform:datetime property="fdCertificate_Form[!{index}].fdInvalidDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyCertifi.fdRemark')}
                                                                    </td>
                                                                    <td>

                                                                        <div id="_xform_fdCertificate_Form[!{index}].fdRemark" _xform_type="textarea">
                                                                            <xform:textarea property="fdCertificate_Form[!{index}].fdRemark" showStatus="edit" subject="${lfn:message('hr-ratify:hrRecruitCertifi.fdRemark')}" validators=" maxLength(2000)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                    <c:forEach items="${hrStaffEntryForm.fdCertificate_Form}" var="_xformEachBean" varStatus="vstatus">
                                                        <tr KMSS_IsContentRow="1">
                                                            <td class="detail_wrap_td">
                                                                <xform:text showStatus="noShow" property="fdCertificate_Form[${vstatus.index}].fdId" />
                                                                <table class="muiSimple">
                                                                    <tr celltr-title="true" onclick="expandRow(this);">
                                                                        <td colspan="2" align="left" valign="middle" class="muiDetailTableNo">
                                                                            <div class="muiDetailDisplayOpt muiDetailDown"></div>
                                                                        <span>${lfn:message('page.the')}${vstatus.index+1}${ lfn:message('page.row') }</span>
                                                                            
                                                                                                                                                     
                                                                            <div class="muiDetailTableDel" onclick="delRowExpand('TABLE_DocList_fdCertificate_Form',this);">
                                                                                <bean:message key="button.delete" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyCertifi.fdName')}
                                                                        </td>
                                                                        <td>

                                                                            <div id="_xform_fdCertificate_Form[${vstatus.index}].fdName" _xform_type="text">
                                                                                <xform:text property="fdCertificate_Form[${vstatus.index}].fdName" showStatus="edit" required="true" subject="${lfn:message('hr-ratify:hrRecruitCertifi.fdName')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyCertifi.fdIssuingUnit')}
                                                                        </td>
                                                                        <td>

                                                                            <div id="_xform_fdCertificate_Form[${vstatus.index}].fdIssuingUnit" _xform_type="text">
                                                                                <xform:text property="fdCertificate_Form[${vstatus.index}].fdIssuingUnit" showStatus="edit" required="true" subject="${lfn:message('hr-ratify:hrRecruitCertifi.fdIssuingUnit')}" validators=" maxLength(100)" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyCertifi.fdIssueDate')}
                                                                        </td>
                                                                        <td>

                                                                            <div id="_xform_fdCertificate_Form[${vstatus.index}].fdIssueDate" _xform_type="datetime">
                                                                                <xform:datetime property="fdCertificate_Form[${vstatus.index}].fdIssueDate" showStatus="edit" required="true" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyCertifi.fdInvalidDate')}
                                                                        </td>
                                                                        <td>

                                                                            <div id="_xform_fdCertificate_Form[${vstatus.index}].fdInvalidDate" _xform_type="datetime">
                                                                                <xform:datetime property="fdCertificate_Form[${vstatus.index}].fdInvalidDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyCertifi.fdRemark')}
                                                                        </td>
                                                                        <td>

                                                                            <div id="_xform_fdCertificate_Form[${vstatus.index}].fdRemark" _xform_type="textarea">
                                                                                <xform:textarea property="fdCertificate_Form[${vstatus.index}].fdRemark" showStatus="edit" subject="${lfn:message('hr-ratify:hrRecruitCertifi.fdRemark')}" validators=" maxLength(2000)" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </table>
                                            </div>
                                            <div data-dojo-type="sys/xform/mobile/controls/DetailTableAddButton" onclick="window.addRowExpand('TABLE_DocList_fdCertificate_Form')">
                                                ${lfn:message('doclist.add')}(${lfn:message('hr-ratify:table.hrRatifyCertifi')})
                                            </div>
                                            <script type="text/javascript">
                                                Com_IncludeFile('doclist.js');
                                            </script>
                                            <script type="text/javascript">
                                                DocList_Info.push('TABLE_DocList_fdCertificate_Form');
                                            </script>
                                            <input type="hidden" name="fdCertificate_Flag" value="1" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle" colspan="2">
                                            <div class="mblListItemLayoutLeft">
                                                ${lfn:message('hr-ratify:table.hrRatifyEduExp')}
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <div class="detailTableContent">
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0" id="TABLE_DocList_fdEducations_Form">
                                                    <tr style="display:none;">
                                                        <td></td>
                                                    </tr>
                                                    <tr data-dojo-type="mui/form/Template" KMSS_IsReferRow="1" style="display:none;" border='0'>
                                                        <td class="detail_wrap_td">
                                                            <xform:text showStatus="noShow" property="fdEducations_Form[!{index}].fdId" />
                                                            <table class="muiSimple">
                                                                <tr celltr-title="true" onclick="expandRow(this);">
                                                                    <td colspan="2" align="left" valign="middle" class="muiDetailTableNo">
                                                                        <div class="muiDetailDisplayOpt muiDetailDown"></div>
                                                                        <span>${lfn:message('page.the')}!{index}${ lfn:message('page.row') }</span>
                                                                        <div class="muiDetailTableDel" onclick="delRowExpand('TABLE_DocList_fdEducations_Form',this);">
                                                                            <bean:message key="button.delete" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyEduExp.fdName')}
                                                                    </td>
                                                                    <td>

                                                                        <div id="_xform_fdEducations_Form[!{index}].fdName" _xform_type="text">
                                                                            <xform:text property="fdEducations_Form[!{index}].fdName" showStatus="edit" required="true" subject="${lfn:message('hr-ratify:hrRecruitEduExp.fdName')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyEduExp.fdMajor')}
                                                                    </td>
                                                                    <td>

                                                                        <div id="_xform_fdEducations_Form[!{index}].fdMajor" _xform_type="text">
                                                                            <xform:text property="fdEducations_Form[!{index}].fdMajor" showStatus="edit" required="true" subject="${lfn:message('hr-ratify:hrRecruitEduExp.fdMajor')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyEduExp.fdAcademic')}
                                                                    </td>
                                                                    <td>

                                                                        <div id="_xform_fdEducations_Form[!{index}].fdAcadeRecordId" _xform_type="text">
                                                                        	<xform:select property="fdEducations_Form[!{index}].fdAcadeRecordId" htmlElementProperties="id='fdAcademic_[!{index}]'" showStatus="edit" mobile="true" required="true">
                                                                        		<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType = 'fdHighestDegree'"></xform:beanDataSource>
                                                                        	</xform:select>
                                                                        </div>
                                                                        
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyEduExp.fdEntranceDate')}
                                                                    </td>
                                                                    <td>

                                                                        <div id="_xform_fdEducations_Form[!{index}].fdEntranceDate" _xform_type="datetime">
                                                                            <xform:datetime property="fdEducations_Form[!{index}].fdEntranceDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyEduExp.fdGraduationDate')}
                                                                    </td>
                                                                    <td>

                                                                        <div id="_xform_fdEducations_Form[!{index}].fdGraduationDate" _xform_type="datetime">
                                                                            <xform:datetime property="fdEducations_Form[!{index}].fdGraduationDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyEduExp.fdRemark')}
                                                                    </td>
                                                                    <td>

                                                                        <div id="_xform_fdEducations_Form[!{index}].fdRemark" _xform_type="textarea">
                                                                            <xform:textarea property="fdEducations_Form[!{index}].fdRemark" showStatus="edit" subject="${lfn:message('hr-ratify:hrRecruitEduExp.fdRemark')}" validators=" maxLength(2000)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                    <c:forEach items="${hrStaffEntryForm.fdEducations_Form}" var="_xformEachBean" varStatus="vstatus">
                                                        <tr KMSS_IsContentRow="1">
                                                            <td class="detail_wrap_td">
                                                                <xform:text showStatus="noShow" property="fdEducations_Form[${vstatus.index}].fdId" />
                                                                <table class="muiSimple">
                                                                    <tr celltr-title="true" onclick="expandRow(this);">
                                                                        <td colspan="2" align="left" valign="middle" class="muiDetailTableNo">
                                                                            <div class="muiDetailDisplayOpt muiDetailDown"></div>
                                                                      
                                                                            <span>${lfn:message('page.the')}${vstatus.index+1}${ lfn:message('page.row') }</span>
                                                                            
                                                                            
                                                                            <div class="muiDetailTableDel" onclick="delRowExpand('TABLE_DocList_fdEducations_Form',this);">
                                                                                <bean:message key="button.delete" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyEduExp.fdName')}
                                                                        </td>
                                                                        <td>

                                                                            <div id="_xform_fdEducations_Form[${vstatus.index}].fdName" _xform_type="text">
                                                                                <xform:text property="fdEducations_Form[${vstatus.index}].fdName" showStatus="edit" required="true" subject="${lfn:message('hr-ratify:hrRecruitEduExp.fdName')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyEduExp.fdMajor')}
                                                                        </td>
                                                                        <td>

                                                                            <div id="_xform_fdEducations_Form[${vstatus.index}].fdMajor" _xform_type="text">
                                                                                <xform:text property="fdEducations_Form[${vstatus.index}].fdMajor" showStatus="edit" required="true" subject="${lfn:message('hr-ratify:hrRecruitEduExp.fdMajor')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyEduExp.fdAcademic')}
                                                                        </td>
                                                                        <td>

                                                                            <div id="_xform_fdEducations_Form[${vstatus.index}].fdAcadeRecordId" _xform_type="text">
		                                                                        <xform:select property="fdEducations_Form[${vstatus.index}].fdAcadeRecordId" htmlElementProperties="id='fdAcademic'" showStatus="edit" mobile="true" required="true">
                                                                        			<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType = 'fdHighestDegree'"></xform:beanDataSource>
                                                                        		</xform:select>
  																			</div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyEduExp.fdEntranceDate')}
                                                                        </td>
                                                                        <td>

                                                                            <div id="_xform_fdEducations_Form[${vstatus.index}].fdEntranceDate" _xform_type="datetime">
                                                                                <xform:datetime property="fdEducations_Form[${vstatus.index}].fdEntranceDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyEduExp.fdGraduationDate')}
                                                                        </td>
                                                                        <td>

                                                                            <div id="_xform_fdEducations_Form[${vstatus.index}].fdGraduationDate" _xform_type="datetime">
                                                                                <xform:datetime property="fdEducations_Form[${vstatus.index}].fdGraduationDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyEduExp.fdRemark')}
                                                                        </td>
                                                                        <td>

                                                                            <div id="_xform_fdEducations_Form[${vstatus.index}].fdRemark" _xform_type="textarea">
                                                                                <xform:textarea property="fdEducations_Form[${vstatus.index}].fdRemark" showStatus="edit" subject="${lfn:message('hr-ratify:hrRecruitEduExp.fdRemark')}" validators=" maxLength(2000)" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </table>
                                            </div>
                                            <div data-dojo-type="sys/xform/mobile/controls/DetailTableAddButton" onclick="window.addRowExpand('TABLE_DocList_fdEducations_Form')">
                                                ${lfn:message('doclist.add')}(${lfn:message('hr-ratify:table.hrRatifyEduExp')})
                                            </div>
                                            <script type="text/javascript">
                                                Com_IncludeFile('doclist.js');
                                            </script>
                                            <script type="text/javascript">
                                                DocList_Info.push('TABLE_DocList_fdEducations_Form');
                                            </script>
                                            <input type="hidden" name="fdEducations_Flag" value="1" />
                                        </td>
                                    </tr>
                                   <!-- 奖惩信息 start -->
                                    <tr>
                                        <td class="muiTitle" colspan="2">
                                            <div class="mblListItemLayoutLeft">
                                                ${lfn:message('hr-ratify:hrStaffEntry.fdRewardsPunishments')}
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <div class="detailTableContent">
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0" id="TABLE_DocList_fdRewardsPunishments_Form">
                                                    <tr style="display:none;">
                                                        <td></td>
                                                    </tr>
                                                    <tr data-dojo-type="mui/form/Template" KMSS_IsReferRow="1" style="display:none;" border='0'>
                                                        <td class="detail_wrap_td">
                                                            <xform:text showStatus="noShow" property="fdRewardsPunishments_Form[!{index}].fdId" />
                                                            <table class="muiSimple">
                                                                <tr celltr-title="true" onclick="expandRow(this);">
                                                                    <td colspan="2" align="left" valign="middle" class="muiDetailTableNo">
                                                                        <div class="muiDetailDisplayOpt muiDetailDown"></div>
                                                                        <span>${lfn:message('page.the')}!{index}${ lfn:message('page.row') }</span>
                                                                        <div class="muiDetailTableDel" onclick="delRowExpand('TABLE_DocList_fdRewardsPunishments_Form',this);">
                                                                            <bean:message key="button.delete" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyRewPuni.fdName')}
                                                                    </td>
                                                                    <td>
                                                                    
                                                                        <div id="_xform_fdRewardsPunishments_Form[!{index}].fdName" _xform_type="text">
                                                                            <xform:text property="fdRewardsPunishments_Form[!{index}].fdName" showStatus="edit" required="true" subject="${lfn:message('hr-ratify:hrRecruitRewPuni.fdName')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyRewPuni.fdDate')}
                                                                    </td>
                                                                    <td>
                                                                    
                                                                        <div id="_xform_fdRewardsPunishments_Form[!{index}].fdDate" _xform_type="datetime">
                                                                            <xform:datetime property="fdRewardsPunishments_Form[!{index}].fdDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyRewPuni.fdRemark')}
                                                                    </td>
                                                                    <td>

                                                                        <div id="_xform_fdRewardsPunishments_Form[!{index}].fdRemark" _xform_type="textarea">
                                                                            <xform:textarea property="fdRewardsPunishments_Form[!{index}].fdRemark" showStatus="edit" subject="${lfn:message('hr-ratify:hrRecruitRewPuni.fdRemark')}" validators=" maxLength(2000)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                    <c:forEach items="${hrStaffEntryForm.fdRewardsPunishments_Form}" var="_xformEachBean" varStatus="vstatus">
                                                        <tr KMSS_IsContentRow="1">
                                                            <td class="detail_wrap_td">
                                                                <xform:text showStatus="noShow" property="fdRewardsPunishments_Form[${vstatus.index}].fdId" />
                                                                <table class="muiSimple">
                                                                    <tr celltr-title="true" onclick="expandRow(this);">
                                                                        <td colspan="2" align="left" valign="middle" class="muiDetailTableNo">
                                                                            <div class="muiDetailDisplayOpt muiDetailDown"></div>
                                                                            <span>${lfn:message('page.the')}${vstatus.index+1}${ lfn:message('page.row') }</span>
                                                                            
                                                                            
                                                                            <div class="muiDetailTableDel" onclick="delRowExpand('TABLE_DocList_fdRewardsPunishments_Form',this);">
                                                                                <bean:message key="button.delete" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyRewPuni.fdName')}
                                                                        </td>
                                                                        <td>

                                                                            <div id="_xform_fdRewardsPunishments_Form[${vstatus.index}].fdName" _xform_type="text">
                                                                                <xform:text property="fdRewardsPunishments_Form[${vstatus.index}].fdName" showStatus="edit" required="true" subject="${lfn:message('hr-ratify:hrRecruitRewPuni.fdName')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyRewPuni.fdDate')}
                                                                        </td>
                                                                        <td>

                                                                            <div id="_xform_fdRewardsPunishments_Form[${vstatus.index}].fdDate" _xform_type="datetime">
                                                                                <xform:datetime property="fdRewardsPunishments_Form[${vstatus.index}].fdDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyRewPuni.fdRemark')}
                                                                        </td>
                                                                        <td>
                                                                        
                                                                            <div id="_xform_fdRewardsPunishments_Form[${vstatus.index}].fdRemark" _xform_type="textarea">
                                                                                <xform:textarea property="fdRewardsPunishments_Form[${vstatus.index}].fdRemark" showStatus="edit" subject="${lfn:message('hr-ratify:hrRecruitRewPuni.fdRemark')}" validators=" maxLength(2000)" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </table>
                                            </div>
                                            <div data-dojo-type="sys/xform/mobile/controls/DetailTableAddButton" onclick="window.addRowExpand('TABLE_DocList_fdRewardsPunishments_Form')">
                                                ${lfn:message('doclist.add')}(${lfn:message('hr-ratify:hrStaffEntry.fdRewardsPunishments')})
                                            </div>
                                            <script type="text/javascript">
                                                Com_IncludeFile('doclist.js');
                                            </script>
                                            <script type="text/javascript">
                                                DocList_Info.push('TABLE_DocList_fdRewardsPunishments_Form');
                                            </script>
                                            <input type="hidden" name="fdRewardsPunishments_Flag" value="1" />
                                        </td>
                                    </tr>
                                     <!-- 奖惩信息 end-->
                                       <!-- 家庭信息 start-->
									 <tr>
                                        <td class="muiTitle" colspan="2">
                                            <div class="mblListItemLayoutLeft">
                                                ${lfn:message('hr-ratify:hrStaffEntry.fdfamily')}
                                            </div>
                                        </td>
                                    </tr>
                                       <tr>
                                        <td colspan="2">
                                            <div class="detailTableContent">
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0" id="TABLE_DocList_fdfamily_Form">
                                                    <tr style="display:none;">
                                                        <td></td>
                                                    </tr>
                                                    <tr data-dojo-type="mui/form/Template" KMSS_IsReferRow="1" style="display:none;" border='0'>
                                                        <td class="detail_wrap_td">
                                                            <xform:text showStatus="noShow" property="fdfamily_Form[!{index}].fdId" />
                                                            <table class="muiSimple">
                                                                <tr celltr-title="true" onclick="expandRow(this);">
                                                                    <td colspan="2" align="left" valign="middle" class="muiDetailTableNo">
                                                                        <div class="muiDetailDisplayOpt muiDetailDown"></div>
                                                                        <span>${lfn:message('page.the')}!{index}${ lfn:message('page.row') }</span>
                                                                        <div class="muiDetailTableDel" onclick="delRowExpand('TABLE_DocList_fdfamily_Form',this);">
                                                                            <bean:message key="button.delete" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                         <!-- 家庭关系 -->
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrStaffEntry.family.related')}
                                                                    </td>
                                                                    <td>
                                                                        <div id="_xform_fdfamily_Form[!{index}].fdRelated" _xform_type="text">
                                                                            <xform:text property="fdfamily_Form[!{index}].fdRelated" showStatus="edit" required="true" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <!-- 姓名-->
                                                                 <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrStaffEntry.family.name')}
                                                                    </td>
                                                                    <td>
                                                                        <div id="_xform_fdfamily_Form[!{index}].fdName" _xform_type="text">
                                                                            <xform:text property="fdfamily_Form[!{index}].fdName" showStatus="edit" required="true"  validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
																<!-- 职业-->
                                                                 <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrStaffEntry.family.occupation')}
                                                                    </td>
                                                                    <td>
                                                                        <div id="_xform_fdfamily_Form[!{index}].fdOccupation" _xform_type="text">
                                                                            <xform:text property="fdfamily_Form[!{index}].fdOccupation" showStatus="edit"  validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                               <!-- 任职单位-->
                                                                 <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrStaffEntry.family.company')}
                                                                    </td>
                                                                    <td>
                                                                        <div id="_xform_fdfamily_Form[!{index}].fdCompany" _xform_type="text">
                                                                            <xform:text property="fdfamily_Form[!{index}].fdCompany" showStatus="edit"  validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                  <!-- 联系方式-->
                                                                 <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrStaffEntry.family.connect')}
                                                                    </td>
                                                                    <td>
                                                                        <div id="_xform_fdfamily_Form[!{index}].fdConnect" _xform_type="text">
                                                                            <xform:text property="fdfamily_Form[!{index}].fdConnect" showStatus="edit"  validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                              	<!-- 备注-->
                                                              	  <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrStaffEntry.family.memo')}
                                                                        </td>
                                                                        <td>
                                                                            <div id="_xform_fdfamily_Form[!{index}].fdMemo" _xform_type="textarea">
                                                                                <xform:textarea property="fdfamily_Form[!{index}].fdMemo" showStatus="edit"  validators=" maxLength(2000)" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                 </table>
                                                        </td>
                                                    </tr>
                                                    <c:forEach items="${hrStaffEntryForm.fdfamily_Form}" var="_xformEachBean" varStatus="vstatus">
                                                                 <tr KMSS_IsContentRow="1">
                                                            <td class="detail_wrap_td">
                                                                <xform:text showStatus="noShow" property="fdfamily_Form[${vstatus.index}].fdId" />
                                                                <table class="muiSimple">
                                                                    <tr celltr-title="true" onclick="expandRow(this);">
                                                                        <td colspan="2" align="left" valign="middle" class="muiDetailTableNo">
                                                                            <div class="muiDetailDisplayOpt muiDetailDown"></div>
                                                                            <span>${lfn:message('page.the')}${vstatus.index+1}${ lfn:message('page.row') }</span>
                                                                            <div class="muiDetailTableDel" onclick="delRowExpand('TABLE_DocList_fdfamily_Form',this);">
                                                                                <bean:message key="button.delete" />
                                                                            </div>
                                                                        </td>
                                                                        </tr>
                                                                <!-- 家庭关系 -->
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrStaffEntry.family.related')}
                                                                    </td>
                                                                    <td>
                                                                        <div id="_xform_fdfamily_Form[${vstatus.index}].fdRelated" _xform_type="text">
                                                                            <xform:text property="fdfamily_Form[${vstatus.index}].fdRelated" showStatus="edit" required="true" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <!-- 姓名-->
                                                                 <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrStaffEntry.family.name')}
                                                                    </td>
                                                                    <td>
                                                                        <div id="_xform_fdfamily_Form[${vstatus.index}].fdName" _xform_type="text">
                                                                            <xform:text property="fdfamily_Form[${vstatus.index}].fdName" showStatus="edit" required="true"  validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
																<!-- 职业-->
                                                                 <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrStaffEntry.family.occupation')}
                                                                    </td>
                                                                    <td>
                                                                        <div id="_xform_fdfamily_Form[${vstatus.index}].fdOccupation" _xform_type="text">
                                                                            <xform:text property="fdfamily_Form[${vstatus.index}].fdOccupation" showStatus="edit"  validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                               <!-- 任职单位-->
                                                                 <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrStaffEntry.family.company')}
                                                                    </td>
                                                                    <td>
                                                                        <div id="_xform_fdfamily_Form[${vstatus.index}].fdCompany" _xform_type="text">
                                                                            <xform:text property="fdfamily_Form[${vstatus.index}].fdCompany" showStatus="edit"  validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                  <!-- 联系方式-->
                                                                 <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrStaffEntry.family.connect')}
                                                                    </td>
                                                                    <td>
                                                                        <div id="_xform_fdfamily_Form[${vstatus.index}].fdConnect" _xform_type="text">
                                                                            <xform:text property="fdfamily_Form[${vstatus.index}].fdConnect" showStatus="edit"  validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                              	<!-- 备注-->
                                                              	  <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrStaffEntry.family.memo')}
                                                                        </td>
                                                                        <td>
                                                                            <div id="_xform_fdfamily_Form[${vstatus.index}].fdMemo" _xform_type="textarea">
                                                                                <xform:textarea property="fdfamily_Form[${vstatus.index}].fdMemo" showStatus="edit"  validators=" maxLength(2000)" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </table>
                                            </div>
                                            <!-- 添加行 -->
                                            <div data-dojo-type="sys/xform/mobile/controls/DetailTableAddButton" onclick="window.addRowExpand('TABLE_DocList_fdfamily_Form')">
                                                ${lfn:message('doclist.add')}(${lfn:message('hr-ratify:hrStaffEntry.fdfamily')})
                                            </div>
                                            <script type="text/javascript">
                                                Com_IncludeFile('doclist.js');
                                            </script>
                                            <script type="text/javascript">
                                                DocList_Info.push('TABLE_DocList_fdfamily_Form');
                                            </script>
                                            <input type="hidden" name="fdfamily_Flag" value="1" />
                                        </td>
                                    </tr>
                                      <!-- 家庭信息 end-->
                                      <!-- 薪酬福利 start -->
                                      <tr>
                                        <td class="muiTitle" colspan="2">
                                            <div class="mblListItemLayoutLeft">
                                                ${lfn:message('hr-staff:table.hrStaffEmolumentWelfare')}
                                            </div>
                                        </td>
                                    </tr>
                                       <!-- 工资账户名 -->
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-staff:hrStaffEmolumentWelfare.fdPayrollName')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdPhoneNum" _xform_type="text">
                                           		 <input type="hidden" name="hrStaffEmolumentWelfareForm.fdId" value="${hrStaffEntryForm.hrStaffEmolumentWelfareForm.fdId }"/>
                                                <xform:text property="hrStaffEmolumentWelfareForm.fdPayrollName" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                      <!-- 工资银行 -->
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-staff:hrStaffEmolumentWelfare.fdPayrollBank')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdPhoneNum" _xform_type="text">
                                                <xform:text property="hrStaffEmolumentWelfareForm.fdPayrollBank" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                       <!-- 工资账号 -->
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-staff:hrStaffEmolumentWelfare.fdPayrollAccount')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdPhoneNum" _xform_type="text">
                                                <xform:text property="hrStaffEmolumentWelfareForm.fdPayrollAccount" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                        <!-- 公积金账户-->
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-staff:hrStaffEmolumentWelfare.fdSurplusAccount')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdPhoneNum" _xform_type="text">
                                                <xform:text property="hrStaffEmolumentWelfareForm.fdSurplusAccount" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                      <!-- 社保号码 -->
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-staff:hrStaffEmolumentWelfare.fdSocialSecurityNumber')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdPhoneNum" _xform_type="text">
                                                <xform:text property="hrStaffEmolumentWelfareForm.fdSocialSecurityNumber" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                      <!-- 薪酬福利 end -->
                                </table>
                            </div>
                        </div>
                    </div>
                    <ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>
                        <li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnNext " data-dojo-props="colSize:2,onClick:function(){submitFormValidate2('saveAnonymousByEntry');}">
                            <bean:message key="button.submit" />
                        </li>
                    </ul>
                </div>
                <html:hidden property="fdId" />
                <html:hidden property="fdQRStatus" value="true"/>
                <html:hidden property="fdQRTime" value="<%=DateUtil.convertDateToString(new Date(), null)%>"/>
                <html:hidden property="method_GET" />


                <c:import url="/sys/right/mobile/edit_hidden.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="hrStaffEntryForm" />
                    <c:param name="moduleModelName" value="com.landray.kmss.hr.staff.model.HrStaffEntry" />
                </c:import>

            </html:form>
        </template:replace>
    </template:include>