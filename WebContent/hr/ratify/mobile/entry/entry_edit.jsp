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
            <c:choose>
                <c:when test="${hrRatifyEntryForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') }  ${ lfn:message('hr-ratify:table.hrStaffEntry') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${lfn:message('hr-ratify:table.hrStaffEntry') }" />
                </c:otherwise>
            </c:choose>
        </template:replace>
        <template:replace name="head">
        	
       		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/hr/ratify/mobile/resource/css/personer.css"></link>
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/hr/ratify/mobile/resource/font/dabFont/dabFont.css"></link>
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/hr/ratify/mobile/resource/css/formreset.css"></link>
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
               	.personal-info-nav li{
               		list-style:none;
               	}
               	.personal-info-nav{
					position: fixed;
					left: 0;
					top: 0;
					width: 100%;z-index: 1000
               	}
               	.muiSimple .muiTitle{
               		padding-left:15px;
               	}
               	.expandTitle{
               		width:34%!important;
               	}
               	.basic-info-title{
               		font-size:1.32rem;
               	}
               	#scrollView{
               		background:#fff;
               	}
               	#content .mblTabBarButtonLabel{
             
               		font-size:1.76rem;
               	}
               	#content .muiDetailTableAdd{
               		border: 1px solid #C9CAD1;
					border-radius: .552rem;
					margin:0 1.65rem;
               	}
               	.detailTableContent{
					margin:0 1.65rem;
				}
               	 .muiFormEleWrap.popup .muiSelInput{
               	 	padding-left:.5rem;
               	 }
            </style>
            <script type="text/javascript">
                var formInitData = {

                };
                var lang = {
                    "the": "${lfn:message('page.the')}",
                    "row": "${lfn:message('page.row')}"
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
            <html:form  styleId="formReset" action="/resource/hr/staff/hr_staff_entry_anonymous/hrStaffEntry.do?method=update">
            	<ul class="personal-info-nav" style="background: #fff">
		            <li data-info="base">
		                <a>基本信息</a>
		                <div class="blue-line"></div>
		            </li>
		            <li data-info="connect">
		                <a>联系方式</a>
		                <div></div>
		            </li>
		            <li data-info="history">
		                <a>工作经历</a>
		                <div></div>
		            </li>
		            <li data-info="other">
		                <a>其他</a>
		                <div></div>
		            </li>
		        </ul>
              <div id="scrollView" class="requiredStyle" data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin,hr/ratify/mobile/resource/js/EntryView">
                <div id="info" style="margin-top:44px;">
                	<div id="basic">
                		 <div  class="basic-info-title">基本信息</div>
          		 		<table class="muiSimple" cellpadding="0" cellspacing="0">
			                           <tr>
			                               <td class="muiTitle" style="width:35%;">
			                                   ${lfn:message('hr-ratify:hrStaffEntry.fdName')}<span class="requredTitle">必填</span>
			                               </td>
			                               <td class="newInput">
			                                   <div id="_xform_fdName" _xform_type="text">
			                                       <xform:text property="fdName" showStatus="edit" mobile="true" style="width:95%;" />
			                                   </div>
			                               </td>
			                           </tr>
			                           <tr>
			                               <td class="muiTitle">
			                                   ${lfn:message('hr-ratify:hrStaffEntry.fdNameUsedBefore')}
			                               </td>
			                               <td class="newInput">
			                                   <div id="_xform_fdName" _xform_type="text">
			                                       <xform:text property="fdNameUsedBefore" showStatus="edit" mobile="true" style="width:95%;" />
			                                   </div>
			                               </td>
			                            </tr>
			                            <tr>
			                                <td class="muiTitle">
			                                    ${lfn:message('hr-ratify:hrStaffEntry.fdPlanEntryDept')}
			                                </td>
			                                <td class="newAddress">
			                                    <div id="_xform_fdName" _xform_type="text">
			                                        <xform:address propertyId="fdPlanEntryDeptId" propertyName="fdPlanEntryDeptName" mobile="true" orgType="ORG_TYPE_DEPT" showStatus="edit" style="width:95%;" />
			                                    </div>
			                                </td>
			                            </tr>
			                            <tr>
			                            	<td class="muiTitle">拟入职日期<span class="requredTitle">必填</span></td>
			                            	<td class="newDate">
         		                                  <div id="_xform_fdName" _xform_type="datetime">
                                                      <xform:datetime property="fdPlanEntryTime" showStatus="edit" required="true" dateTimeType="date" mobile="true" style="width:95%;" />
                                                  </div>
			                            	</td>
			                            </tr>
			                            <tr>
			                                <td class="muiTitle">
			                                    	拟入职岗位
			                                </td>
			                                <td class="newAddress">
			                                    <div id="_xform_fdName" _xform_type="text">
			                                    	<xform:address propertyId="fdOrgPostIds" propertyName="fdOrgPostNames" mulSelect="true" mobile="true" orgType="ORG_TYPE_POST" showStatus="edit" style="width:95%;" />
			                                    </div>
			                                </td>
			                            </tr>
			                            <!-- 性别 -->
			                            <tr>
			                                <td class="muiTitle">
			                                    ${lfn:message('hr-ratify:hrStaffEntry.fdSex')}
			                                </td>
			                                <td class="newRadio">
			                                    <div id="_xform_fdSex" _xform_type="radio">
			                                        <xform:radio property="fdSex" htmlElementProperties="id='fdSex'" showStatus="edit" mobile="true">
			                                            <xform:enumsDataSource enumsType="sys_org_person_sex" />
			                                        </xform:radio>
			                                    </div>
			                                </td>
			                            </tr>
			                            <!-- 出生日期 -->
			                             <tr>
			                                 <td class="muiTitle">
			                                     ${lfn:message('hr-ratify:hrStaffEntry.fdDateOfBirth')}
			                                 </td>
			                                 <td class="newDate">
			                                     <div id="_xform_fdBirthDate" _xform_type="datetime">
			                                         <xform:datetime property="fdDateOfBirth" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
			                                     </div>
			                                 </td>
			                             </tr>
			                             <!-- 身份证号 -->
			                             <tr>
			                                <td class="muiTitle">
			                                    ${lfn:message('hr-ratify:hrRatifyEntry.fdIdCard')}
			                                </td>
			                                <td class="newInput">
			                                    <div id="_xform_fdCardNo" _xform_type="text">
			                                        <xform:text property="fdIdCard" showStatus="edit" mobile="true" style="width:95%;" />
			                                    </div>
			                                </td>
			                             </tr>
			                             <tr>
			                                  <td class="muiTitle">
			                                      ${lfn:message('hr-ratify:hrRatifyEntry.fdWorkTime')}
			                                  </td>
			                                  <td class="newDate">
			                                      <div id="_xform_fdJoinWork" _xform_type="datetime">
			                                          <xform:datetime property="fdWorkTime" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
			                                      </div>
			                                  </td>
			                              </tr>
			                              <tr>
			                                  <td class="muiTitle">
			                                      ${lfn:message('hr-ratify:hrRatifyEntry.fdProfession')}
			                                  </td>
			                                  <td class="newInput">
			                                      <div id="_xform_fdMajor" _xform_type="text">
			                                          <xform:text property="fdProfession" showStatus="edit" mobile="true" style="width:95%;" />
			                                      </div>
			                                  </td>
			                              </tr>
			                               <tr>
			                                  <td class="muiTitle">
			                                      ${lfn:message('hr-ratify:hrRatifyEntry.fdNation')}
			                                  </td>
			                                  <td class="newSelect">
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
									<td width="35%" class="newSelect">
										<xform:select property="fdPoliticalLandscapeId" showStatus="edit" mobile="true">
											<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType = 'fdPoliticalLandscape'"></xform:beanDataSource>
										</xform:select>
									</td>
								</tr>
								<tr>
									<!-- 入团日期 -->
									<td width="15%" class="muiTitle">
										<bean:message bundle="hr-staff" key="hrStaffEntry.fdDateOfGroup" />
									</td>
									<td width="35%" class="newDate">
										<xform:datetime property="fdDateOfGroup" dateTimeType="date" mobile="true"></xform:datetime>
									</td>
								</tr>
								<tr>
									<!-- 入党日期 -->
									<td width="15%" class="muiTitle">
										<bean:message bundle="hr-staff" key="hrStaffEntry.fdDateOfParty" />
									</td>
									<td width="35%" class="newDate">
										<xform:datetime property="fdDateOfParty" dateTimeType="date" mobile="true"></xform:datetime>
									</td>
								</tr>
								<!-- 现居住地 -->
                                <tr>
                                    <td class="muiTitle">
                                        ${lfn:message('hr-ratify:hrRatifyEntry.fdLivingPlace')}
                                    </td>
                                    <td class="newInput">
                                        <div id="_xform_fdCurrentPlace" _xform_type="text">
                                            <xform:text property="fdLivingPlace" showStatus="edit" mobile="true" style="width:95%;" />
                                        </div>
                                    </td>
                                </tr>
                                <!-- 学位 -->
                                <tr>
                                    <td class="muiTitle">
                                        ${lfn:message('hr-ratify:hrRatifyEntry.fdHighestDegree')}
                                    </td>
                                    <td class="newSelect">
                                        <div id="_xform_fdAcademicId" _xform_type="select">
                                            <xform:select property="fdHighestDegreeId" htmlElementProperties="id='fdHighestDegreeId'" showStatus="edit" mobile="true">
                                                <xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType = 'fdHighestDegree'"  />
                                            </xform:select>
                                        </div>
                                    </td>
                                </tr>
                                <!-- 学历 -->
                                <tr>
                                    <td class="muiTitle">
                                        ${lfn:message('hr-ratify:hrRatifyEntry.fdHighestEducation')}
                                    </td>
                                    <td class="newSelect">
                                        <div id="_xform_fdEducationId" _xform_type="select">
                                            <xform:select property="fdHighestEducationId" htmlElementProperties="id='fdHighestEducationId'" showStatus="edit" mobile="true">
                                                <xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType = 'fdHighestEducation'"  />
                                            </xform:select>
                                        </div>
                                    </td>
                                </tr>
                                <!-- 婚姻状况 -->
                                 <tr>
                                    <td class="muiTitle">
                                        ${lfn:message('hr-ratify:hrRatifyEntry.fdMaritalStatus')}
                                    </td>
                                    <td class="newSelect">
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
                                    <td class="newSelect">
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
                                    <td class="newInput">
                                        <div id="_xform_fdMajor" _xform_type="text">
                                            <xform:text property="fdStature" showStatus="edit" mobile="true" style="width:95%;" />
                                        </div>
                                    </td>
                                </tr>			                              
										                					<tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrStaffEntry.fdWeight')}
                                        </td>
                                        <td class="newInput">
                                            <div id="_xform_fdMajor" _xform_type="text">
                                                <xform:text property="fdWeight" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>

									<tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrStaffEntry.fdNativePlace')}
                                        </td>
                                        <td class="newInput">
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
										<td width="35%" class="newInput">
											<xform:text property="fdHomeplace" style="width:98%;" showStatus="edit" mobile="true"/>
										</td>
									</tr>
									<tr>
										<!-- 户口性质 -->
										<td class="muiTitle">
											${lfn:message('hr-ratify:hrStaffEntry.fdAccountProperties')}
										</td>
										<td width="35%" class="newInput">
											<xform:text property="fdAccountProperties" style="width:98%;" showStatus="edit" mobile="true"/>
										</td>
									</tr>
									<tr>
										<!-- 户口所在地 -->
										<td width="15%" class="muiTitle">
											${lfn:message('hr-ratify:hrStaffEntry.fdRegisteredResidence')}
										</td>
										<td width="35%" class="newInput">
											<xform:text property="fdRegisteredResidence" style="width:98%;" showStatus="edit" mobile="true" />
										</td>
									</tr>
									<tr>
										<!-- 户口所在派出所 -->
										<td width="15%" class="muiTitle">
											${lfn:message('hr-ratify:hrStaffEntry.fdResidencePoliceStation')}
										</td>
										<td width="35%" class="newInput">
											<xform:text property="fdResidencePoliceStation" style="width:98%;" showStatus="edit" mobile="true" />
										</td>
									</tr>
			                       </table>
			                	</div>
			       <div id="connect">
			           <div  class="basic-info-title">联系方式</div>
		            		<table class="muiSimple" cellpadding="0" cellspacing="0">
									 <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrRatifyEntry.fdMobileNo')}<span class="requredTitle">必填</span>
                                        </td>
                                        <td class="newInput">
                                            <div id="_xform_fdPhoneNum" _xform_type="text">
                                                <xform:text property="fdMobileNo" showStatus="edit" validators="phoneNumber" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrRatifyEntry.fdEmail')}
                                        </td>
                                        <td class="newInput">
                                            <div id="_xform_fdPhoneNum" _xform_type="text">
                                                <xform:text property="fdEmail" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrRatifyEntry.fdEmergencyContact')}
                                        </td>
                                        <td class="newInput">
                                            <div id="_xform_fdPhoneNum" _xform_type="text">
                                                <xform:text property="fdEmergencyContact" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="muiTitle expandTitle">
                                            ${lfn:message('hr-ratify:hrRatifyEntry.fdEmergencyContactPhone')}
                                        </td>
                                        <td class="newInput">
                                            <div id="_xform_fdPhoneNum" _xform_type="text">
                                                <xform:text property="fdEmergencyContactPhone" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('hr-ratify:hrRatifyEntry.fdOtherContact')}
                                        </td>
                                        <td class="newInput">
                                            <div id="_xform_fdPhoneNum" _xform_type="text">
                                                <xform:text property="fdOtherContact" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
	                        </table>
                	</div>
                	<div id="history" class="tableList">
                		<div class="basic-info-title">工作经历</div>
                		<table class="muiSimple" cellpadding="0" cellspacing="0">
                                    <!-- 工作经历 -->
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
                                                            <table class="muiSimple listTable">
                                                                <tr celltr-title="true" onclick="expandRow(this);">
                                                                    <td colspan="2" align="left" valign="middle" class="muiTitle">
                                                                        <div class="muiDetailTableDel" onclick="delRowExpand('TABLE_DocList_fdHistory_Form',this);">
                                                                            <bean:message key="button.delete" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true" >
                                                                    <td class="muiTitle" style="width:35%;">
                                                                        ${lfn:message('hr-ratify:hrRatifyHistory.fdName')}<span class="requredTitle">必填</span>
                                                                    </td>
                                                                    <td class="newInput">

                                                                        <div id="_xform_fdHistory_Form[!{index}].fdName" _xform_type="text">
                                                                            <xform:text property="fdHistory_Form[!{index}].fdName" showStatus="edit" required="true" subject="${lfn:message('hr-ratify:hrRecruitHistory.fdName')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyHistory.fdPost')}
                                                                    </td>
                                                                    <td class="newInput">
                                                                        <div id="_xform_fdHistory_Form.fdPost" _xform_type="text">
                                                                            <xform:text property="fdHistory_Form[!{index}].fdPost" showStatus="edit" subject="${lfn:message('hr-ratify:hrRecruitHistory.fdPost')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyHistory.fdStartDate')}
                                                                    </td>
                                                                    <td class="newDate">

                                                                        <div id="_xform_fdHistory_Form[!{index}].fdStartDate" _xform_type="datetime">
                                                                            <xform:datetime property="fdHistory_Form[!{index}].fdStartDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyHistory.fdEndDate')}
                                                                    </td>
                                                                    <td class="newDate">

                                                                        <div id="_xform_fdHistory_Form[!{index}].fdEndDate" _xform_type="datetime">
                                                                            <xform:datetime property="fdHistory_Form[!{index}].fdEndDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyHistory.fdDesc')}
                                                                    </td>
                                                                    <td class="newTextarea">
                                                                        <div id="_xform_fdHistory_Form[!{index}].fdDesc" _xform_type="textarea">
                                                                            <xform:textarea property="fdHistory_Form[!{index}].fdDesc" showStatus="edit" subject="${lfn:message('hr-ratify:hrRecruitHistory.fdDesc')}" validators=" maxLength(2000)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyHistory.fdLeaveReason')}
                                                                    </td>
                                                                    <td class="newTextarea">

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
                          
                                                                            <div class="muiDetailTableDel" onclick="delRowExpand('TABLE_DocList_fdHistory_Form',this);">
                                                                                <bean:message key="button.delete" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle" style="width:35%;">
                                                                            ${lfn:message('hr-ratify:hrRatifyHistory.fdName')}<span class="requredTitle">必填</span>
                                                                        </td>
                                                                        <td class="newInput">

                                                                            <div id="_xform_fdHistory_Form[${vstatus.index}].fdName" _xform_type="text">
                                                                                <xform:text property="fdHistory_Form[${vstatus.index}].fdName" showStatus="edit" required="true" subject="${lfn:message('hr-ratify:hrRecruitHistory.fdName')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyHistory.fdPost')}
                                                                        </td>
                                                                        <td class="newInput">

                                                                            <div id="_xform_fdHistory_Form[${vstatus.index}].fdPost" _xform_type="text">
                                                                                <xform:text property="fdHistory_Form[${vstatus.index}].fdPost" showStatus="edit" subject="${lfn:message('hr-ratify:hrRecruitHistory.fdPost')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyHistory.fdStartDate')}
                                                                        </td>
                                                                        <td class="newDate">

                                                                            <div id="_xform_fdHistory_Form[${vstatus.index}].fdStartDate" _xform_type="datetime">
                                                                                <xform:datetime property="fdHistory_Form[${vstatus.index}].fdStartDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyHistory.fdEndDate')}
                                                                        </td>
                                                                        <td class="newDate">

                                                                            <div id="_xform_fdHistory_Form[${vstatus.index}].fdEndDate" _xform_type="datetime">
                                                                                <xform:datetime property="fdHistory_Form[${vstatus.index}].fdEndDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyHistory.fdDesc')}
                                                                        </td>
                                                                        <td class="newTextarea">

                                                                            <div id="_xform_fdHistory_Form[${vstatus.index}].fdDesc" _xform_type="textarea">
                                                                                <xform:textarea property="fdHistory_Form[${vstatus.index}].fdDesc" showStatus="edit" subject="${lfn:message('hr-ratify:hrRecruitHistory.fdDesc')}" validators=" maxLength(2000)" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyHistory.fdLeaveReason')}
                                                                        </td>
                                                                        <td class="newTextarea">

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
                			</table>
                	
                	</div>
                	<div id="others" class="tableList">
                			 <div  class="basic-info-title">其他</div>
                			<table class="muiSimple" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td class="muiTitle" colspan="2">
                                            <div class="mblListItemLayoutLeft">
                                                ${lfn:message('hr-ratify:table.hrRatifyCertifi')}<img src="/ekp/hr/ratify/mobile/resource/images/qualification.png" alt="">
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
                                                            <table class="muiSimple listTable">
                                                                <tr celltr-title="true" onclick="expandRow(this);">
                                                                    <td colspan="2" align="left" valign="middle" class="muiTitle">
                                                                        <div class="muiDetailTableDel" onclick="delRowExpand('TABLE_DocList_fdCertificate_Form',this);">
                                                                            <bean:message key="button.delete" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle" style="width:35%;">
                                                                        ${lfn:message('hr-ratify:hrRatifyCertifi.fdName')}<span class="requredTitle">必填</span>
                                                                    </td>
                                                                    <td class="newInput">

                                                                        <div id="_xform_fdCertificate_Form[!{index}].fdName" _xform_type="text">
                                                                            <xform:text property="fdCertificate_Form[!{index}].fdName" showStatus="edit" required="true" subject="${lfn:message('hr-ratify:hrRecruitCertifi.fdName')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyCertifi.fdIssuingUnit')}
                                                                    </td>
                                                                    <td class="newInput">

                                                                        <div id="_xform_fdCertificate_Form[!{index}].fdIssuingUnit" _xform_type="text">
                                                                            <xform:text property="fdCertificate_Form[!{index}].fdIssuingUnit" showStatus="edit" subject="${lfn:message('hr-ratify:hrRecruitCertifi.fdIssuingUnit')}" validators=" maxLength(100)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyCertifi.fdIssueDate')}
                                                                    </td>
                                                                    <td class="newDate">

                                                                        <div id="_xform_fdCertificate_Form[!{index}].fdIssueDate" _xform_type="datetime">
                                                                            <xform:datetime property="fdCertificate_Form[!{index}].fdIssueDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyCertifi.fdInvalidDate')}
                                                                    </td>
                                                                    <td class="newDate">

                                                                        <div id="_xform_fdCertificate_Form[!{index}].fdInvalidDate" _xform_type="datetime">
                                                                            <xform:datetime property="fdCertificate_Form[!{index}].fdInvalidDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyCertifi.fdRemark')}
                                                                    </td>
                                                                    <td class="newTextarea">

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
                                                                <table class="muiSimple listTable">
                                                                    <tr celltr-title="true" onclick="expandRow(this);">
                                                                        <td colspan="2" align="left" valign="middle" class="muiTitle">                                                                        
                                                                            <div class="muiDetailTableDel" onclick="delRowExpand('TABLE_DocList_fdCertificate_Form',this);">
                                                                                <bean:message key="button.delete" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle" style="width:35%;">
                                                                            ${lfn:message('hr-ratify:hrRatifyCertifi.fdName')}<span class="requredTitle">必填</span>
                                                                        </td>
                                                                        <td class="newInput">

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
                                                                                <xform:text property="fdCertificate_Form[${vstatus.index}].fdIssuingUnit" showStatus="edit" subject="${lfn:message('hr-ratify:hrRecruitCertifi.fdIssuingUnit')}" validators=" maxLength(100)" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyCertifi.fdIssueDate')}
                                                                        </td>
                                                                        <td class="newDate">

                                                                            <div id="_xform_fdCertificate_Form[${vstatus.index}].fdIssueDate" _xform_type="datetime">
                                                                                <xform:datetime property="fdCertificate_Form[${vstatus.index}].fdIssueDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyCertifi.fdInvalidDate')}
                                                                        </td>
                                                                        <td class="newDate">

                                                                            <div id="_xform_fdCertificate_Form[${vstatus.index}].fdInvalidDate" _xform_type="datetime">
                                                                                <xform:datetime property="fdCertificate_Form[${vstatus.index}].fdInvalidDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyCertifi.fdRemark')}
                                                                        </td>
                                                                        <td class="newTextarea">

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
                                    <!--  -->
                                    <tr>
                                        <td class="muiTitle" colspan="2">
                                            <div class="mblListItemLayoutLeft">
                                                ${lfn:message('hr-ratify:table.hrRatifyEduExp')}<img src="/ekp/hr/ratify/mobile/resource/images/edubackground.png" alt="">
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
                                                            <table class="muiSimple listTable">
                                                                <tr celltr-title="true" onclick="expandRow(this);">
                                                                    <td colspan="2" align="left" valign="middle" class="muiTitle">
                                                                       
                                                                        <div class="muiDetailTableDel" onclick="delRowExpand('TABLE_DocList_fdEducations_Form',this);">
                                                                            <bean:message key="button.delete" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle" style="width:35%;">
                                                                        ${lfn:message('hr-ratify:hrRatifyEduExp.fdName')}<span class="requredTitle">必填</span>
                                                                    </td>
                                                                    <td class="newInput">

                                                                        <div id="_xform_fdEducations_Form[!{index}].fdName" _xform_type="text">
                                                                            <xform:text property="fdEducations_Form[!{index}].fdName" showStatus="edit" required="true" subject="${lfn:message('hr-ratify:hrRecruitEduExp.fdName')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyEduExp.fdMajor')}
                                                                    </td>
                                                                    <td class="newInput">

                                                                        <div id="_xform_fdEducations_Form[!{index}].fdMajor" _xform_type="text">
                                                                            <xform:text property="fdEducations_Form[!{index}].fdMajor" showStatus="edit" subject="${lfn:message('hr-ratify:hrRecruitEduExp.fdMajor')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyEduExp.fdAcademic')}
                                                                    </td>
                                                                    <td class="newSelect">

                                                                        <div id="_xform_fdEducations_Form[!{index}].fdAcadeRecordId" _xform_type="text">
                                                                        	<xform:select property="fdEducations_Form[!{index}].fdAcadeRecordId" htmlElementProperties="id='fdAcademic_[!{index}]'" showStatus="edit" mobile="true">
                                                                        		<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType = 'fdHighestDegree'"></xform:beanDataSource>
                                                                        	</xform:select>
                                                                        </div>
                                                                        
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyEduExp.fdEntranceDate')}
                                                                    </td>
                                                                    <td class="newDate">

                                                                        <div id="_xform_fdEducations_Form[!{index}].fdEntranceDate" _xform_type="datetime">
                                                                            <xform:datetime property="fdEducations_Form[!{index}].fdEntranceDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyEduExp.fdGraduationDate')}
                                                                    </td>
                                                                    <td class="newDate">

                                                                        <div id="_xform_fdEducations_Form[!{index}].fdGraduationDate" _xform_type="datetime">
                                                                            <xform:datetime property="fdEducations_Form[!{index}].fdGraduationDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyEduExp.fdRemark')}
                                                                    </td>
                                                                    <td class="newTextarea">

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
                                                                <table class="muiSimple listTable">
                                                                    <tr celltr-title="true" onclick="expandRow(this);">
                                                                        <td colspan="2" align="left" valign="middle" class="muiTitle">
                                                                           
                                                                            <div class="muiDetailTableDel" onclick="delRowExpand('TABLE_DocList_fdEducations_Form',this);">
                                                                                <bean:message key="button.delete" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle" style="width:35%;">
                                                                            ${lfn:message('hr-ratify:hrRatifyEduExp.fdName')}<span class="requredTitle">必填</span>
                                                                        </td>
                                                                        <td class="newInput">

                                                                            <div id="_xform_fdEducations_Form[${vstatus.index}].fdName" _xform_type="text">
                                                                                <xform:text property="fdEducations_Form[${vstatus.index}].fdName" showStatus="edit" required="true" subject="${lfn:message('hr-ratify:hrRecruitEduExp.fdName')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyEduExp.fdMajor')}
                                                                        </td>
                                                                        <td class="newInput">

                                                                            <div id="_xform_fdEducations_Form[${vstatus.index}].fdMajor" _xform_type="text">
                                                                                <xform:text property="fdEducations_Form[${vstatus.index}].fdMajor" showStatus="edit" subject="${lfn:message('hr-ratify:hrRecruitEduExp.fdMajor')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyEduExp.fdAcademic')}
                                                                        </td>
                                                                        <td class="newSelect">

                                                                            <div id="_xform_fdEducations_Form[${vstatus.index}].fdAcadeRecordId" _xform_type="text">
		                                                                        <xform:select property="fdEducations_Form[${vstatus.index}].fdAcadeRecordId" htmlElementProperties="id='fdAcademic'" showStatus="edit" mobile="true">
                                                                        			<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType = 'fdHighestDegree'"></xform:beanDataSource>
                                                                        		</xform:select>
  																			</div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyEduExp.fdEntranceDate')}
                                                                        </td>
                                                                        <td class="newDate">

                                                                            <div id="_xform_fdEducations_Form[${vstatus.index}].fdEntranceDate" _xform_type="datetime">
                                                                                <xform:datetime property="fdEducations_Form[${vstatus.index}].fdEntranceDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyEduExp.fdGraduationDate')}
                                                                        </td>
                                                                        <td class="newDate">

                                                                            <div id="_xform_fdEducations_Form[${vstatus.index}].fdGraduationDate" _xform_type="datetime">
                                                                                <xform:datetime property="fdEducations_Form[${vstatus.index}].fdGraduationDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyEduExp.fdRemark')}
                                                                        </td>
                                                                        <td class="newTextarea">

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
                                    <tr>
                                        <td class="muiTitle" colspan="2">
                                            <div class="mblListItemLayoutLeft">
                                                ${lfn:message('hr-ratify:hrStaffEntry.fdRewardsPunishments')}<img src="/ekp/hr/ratify/mobile/resource/images/medal.png" alt="">
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
                                                            <table class="muiSimple listTable">
                                                                <tr celltr-title="true" onclick="expandRow(this);">
                                                                    <td colspan="2" align="left" valign="middle" class="muiTitle">
                                                                       
                                                                        <div class="muiDetailTableDel" onclick="delRowExpand('TABLE_DocList_fdRewardsPunishments_Form',this);">
                                                                            <bean:message key="button.delete" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle" style="width:35%;">
                                                                        ${lfn:message('hr-ratify:hrRatifyRewPuni.fdName')}<span class="requredTitle">必填</span>
                                                                    </td>
                                                                    <td class="newInput">

                                                                        <div id="_xform_fdRewardsPunishments_Form[!{index}].fdName" _xform_type="text">
                                                                            <xform:text property="fdRewardsPunishments_Form[!{index}].fdName" showStatus="edit" required="true" subject="${lfn:message('hr-ratify:hrRecruitRewPuni.fdName')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyRewPuni.fdDate')}
                                                                    </td>
                                                                    <td class="newDate">

                                                                        <div id="_xform_fdRewardsPunishments_Form[!{index}].fdDate" _xform_type="datetime">
                                                                            <xform:datetime property="fdRewardsPunishments_Form[!{index}].fdDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr data-celltr="true">
                                                                    <td class="muiTitle">
                                                                        ${lfn:message('hr-ratify:hrRatifyRewPuni.fdRemark')}
                                                                    </td>
                                                                    <td class="newTextarea">

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
                                                                <table class="muiSimple listTable">
                                                                    <tr celltr-title="true" onclick="expandRow(this);">
                                                                        <td colspan="2" align="left" valign="middle" class="muiTitle">
                                                                            <div class="muiDetailDisplayOpt muiDetailDown"></div>
                                                                            <span>${lfn:message('page.the')}${vstatus.index+1}${ lfn:message('page.row') }</span>
                                                                          
                                                                           
                                                                            <div class="muiDetailTableDel" onclick="delRowExpand('TABLE_DocList_fdRewardsPunishments_Form',this);">
                                                                                <bean:message key="button.delete" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle" style="width:35%;">
                                                                            ${lfn:message('hr-ratify:hrRatifyRewPuni.fdName')}<span class="requredTitle">必填</span>
                                                                        </td>
                                                                        <td class="newInput">

                                                                            <div id="_xform_fdRewardsPunishments_Form[${vstatus.index}].fdName" _xform_type="text">
                                                                                <xform:text property="fdRewardsPunishments_Form[${vstatus.index}].fdName" showStatus="edit" required="true" subject="${lfn:message('hr-ratify:hrRecruitRewPuni.fdName')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyRewPuni.fdDate')}
                                                                        </td>
                                                                        <td class="newDate">

                                                                            <div id="_xform_fdRewardsPunishments_Form[${vstatus.index}].fdDate" _xform_type="datetime">
                                                                                <xform:datetime property="fdRewardsPunishments_Form[${vstatus.index}].fdDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr data-celltr="true">
                                                                        <td class="muiTitle">
                                                                            ${lfn:message('hr-ratify:hrRatifyRewPuni.fdRemark')}
                                                                        </td>
                                                                        <td class="newTexarea">

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
                                </table>
                	</div>
                </div>
                    <ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>
                    	<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnNext " data-dojo-props="colSize:2,onClick:function(){submitCancel();}">
                            <bean:message key="button.cancel" />
                        </li>
                        <li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnNext " data-dojo-props="colSize:2,onClick:function(){submitFormValidate2('update','${param.fdId }');}">
                            <bean:message key="button.submit" />
                        </li>
                    </ul>
                </div>
                <html:hidden property="fdId" />
                <html:hidden property="method_GET" />
                <c:import url="/sys/right/mobile/edit_hidden.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="hrStaffEntryForm" />
                    <c:param name="moduleModelName" value="com.landray.kmss.hr.staff.model.HrStaffEntry" />
                </c:import>
                </div>
            </html:form>
            <script>
    		$(".personal-info-nav li").on("click",function(){
    			var oLineDiv = $(this).find("div")
    			if(!oLineDiv.hasClass('blue-line')){
    				$(".personal-info-nav li").find("div").removeClass('blue-line');
    				oLineDiv.addClass('blue-line')
    			}
    			var othersHeight = $("#others").height();
    			var histroyHeight = $("#history").height();
    			var connectHeight = $("#connect").height();
    			var baseHeight = $("#basic").height();
    			var otherTop = histroyHeight+connectHeight+baseHeight;
    			var connectTop = baseHeight;
    			var baseTop = 0
    			var histroyTop = baseHeight+connectHeight;
    			switch($(this).data("info")){
    			 case "base":
    				 $('.mblScrollableViewContainer').attr("style","position: absolute; top: 0px; width: 100%; transform: translate3d(0px, -"+baseTop+"px, 0px);transition:transform 1s;")
    				 // $(window).scrollTop(baseTop);
    				 break;
    			 case "connect":
    				 $('.mblScrollableViewContainer').attr("style","position: absolute; top: 0px; width: 100%; transform: translate3d(0px, -"+connectTop+"px, 0px);transition:transform 1s;")
    				 // $(window).scrollTop(connectTop);
    				 break;
    			 case "history":
    				 $('.mblScrollableViewContainer').attr("style","position: absolute; top: 0px; width: 100%; transform: translate3d(0px, -"+histroyTop+"px, 0px);transition:transform 1s;")
    				 //$(window).scrollTop(histroyTop);
    				 break;
    			 case "other":
    				 $('.mblScrollableViewContainer').attr("style","position: absolute; top: 0px; width: 100%; transform: translate3d(0px, -"+otherTop+"px, 0px);transition:transform 1s;")
    				 //$(window).scrollTop(otherTop);
    				 break;
    			}
    		})
            function submitCancel(){
    			window.location.href=dojoConfig.baseUrl+"hr/staff/hr_staff_entry/hrStaffEntry.do?method=view&fdId="+"${param.fdId}";
    		}	
    		
            </script>
        </template:replace>
    </template:include>