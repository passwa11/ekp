<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.hr.staff.util.HrStaffPersonUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.hr.staff.model.HrStaffAlertIdcardSetting" %>
<%  HrStaffAlertIdcardSetting hrStaffAlertIdcardSetting = new HrStaffAlertIdcardSetting();
	String isIdcardValidate = hrStaffAlertIdcardSetting.getisIdcardValidate();
	request.setAttribute("isIdcardValidate", isIdcardValidate);
%>
<template:include ref="default.edit" sidebar="auto">
	<template:replace name="title">
		<c:choose>
			<c:when test="${ hrStaffEntryForm.method_GET == 'add' }">
				<c:out value="${ lfn:message('hr-staff:hrStaffEntry.create.title') } - ${ lfn:message('hr-staff:module.hr.staff') }"></c:out>	
			</c:when>
			<c:otherwise>
				${ hrStaffEntryForm.fdName } - ${ lfn:message('hr-staff:module.hr.staff') }
			</c:otherwise>
		</c:choose>
	</template:replace>
	<template:replace name="head">
		<style>
			.hr_select{
				width: 50%;
				max-width: 80%;
			}
		</style>
		<link rel="stylesheet" href="<c:url value="/hr/staff/resource/css/public.css"/>" />
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<c:if test="${ hrStaffEntryForm.method_GET == 'add' }">
				<ui:button text="${lfn:message('button.submit')}" 
					onclick="HR_DelRatifyEntryNew();Com_Submit(document.hrStaffEntryForm, 'save');" order="1">
				</ui:button>
			</c:if>
			<c:if test="${ hrStaffEntryForm.method_GET == 'edit' }">
				<ui:button text="${lfn:message('button.save')}" 
					onclick="HR_DelRatifyEntryNew();Com_Submit(document.hrStaffEntryForm, 'update');" order="1">
				</ui:button>
			</c:if>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"  id="categoryId"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:module.hr.staff') }" href="/hr/staff/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:table.HrStaffEntry') }" href="/hr/staff/hr_staff_entry/" target="_self"></ui:menu-item>
		</ui:menu>
	</template:replace>	
	<template:replace name="content">
		<html:form action="/hr/staff/hr_staff_entry/hrStaffEntry.do" >
			<html:hidden property="fdId" value="${hrStaffEntryForm.fdId}"/>
			<html:hidden property="fdStatus" value="${hrStaffEntryForm.fdStatus}"/>
			<div class='lui_form_title_frame' align="center">
				<div class='lui_form_subject'>
					<c:choose>
						<c:when test="${ hrStaffEntryForm.method_GET == 'add' }">
							<c:out value="${ lfn:message('hr-staff:hrStaffEntry.create.title') }"></c:out>	
						</c:when>
						<c:otherwise>
							${ hrStaffEntryForm.fdName }
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<ui:tabpage expand="false">
				<%-- 个人信息 --%>
				<ui:content expand="true" title="${ lfn:message('hr-staff:table.HrStaffPersonInfo') }">
					<table class="tb_normal" style="margin: 20px 0" width=98%>
						<tr>
							<!-- 姓名 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffEntry.fdName" />
							</td>
							<td width="35%">
								<xform:text property="fdName" required="true" subject="${ lfn:message('hr-staff:hrStaffEntry.fdName') }"></xform:text>
							</td>
							<!-- 曾用名 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffEntry.fdNameUsedBefore" />
							</td>
							<td width="35%">
								<xform:text property="fdNameUsedBefore" subject="${ lfn:message('hr-staff:hrStaffEntry.fdNameUsedBefore') }"></xform:text>
							</td>
						</tr>
						<tr>
							<!-- 拟入职部门 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffEntry.fdPlanEntryDept" />
							</td>
							<td width="35%">
								<xform:address propertyName="fdPlanEntryDeptName" propertyId="fdPlanEntryDeptId" orgType="ORG_TYPE_ORGORDEPT" required="true" subject="${ lfn:message('hr-staff:hrStaffEntry.fdPlanEntryDept') }" style="width:50%"></xform:address>
							</td>
							<!-- 拟入职日期 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffEntry.fdPlanEntryTime" />
							</td>
							<td width="35%">
								<xform:datetime property="fdPlanEntryTime" dateTimeType="date" required="true" subject="${ lfn:message('hr-staff:hrStaffEntry.fdPlanEntryTime') }"></xform:datetime>
							</td>
						</tr>
						<tr>
							<!-- 拟入职岗位 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffEntry.fdOrgPosts" />
							</td>
							<td>
								<xform:address propertyName="fdOrgPostNames" propertyId="fdOrgPostIds" orgType="ORG_TYPE_POST" required="true" mulSelect="true" subject="${lfn:message('hr-staff:hrStaffEntry.fdOrgPosts') }" style="width:50%"></xform:address>
							</td>
							<!-- 工号 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffEntry.fdStaffNo" />
							</td>
							<td>
								<xform:text property="fdStaffNo" validators="uniqueStaffNo" required="true" style="50%"></xform:text>
							</td>
						</tr>
						<tr>
							<!-- 性别 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffEntry.fdSex" />
							</td>
							<td width="35%">
								<sunbor:enums property="fdSex" enumsType="sys_org_person_sex" elementType="radio" />
							</td>
							<!-- 出生日期 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffEntry.fdDateOfBirth" />
							</td>
							<td width="35%">
								<xform:datetime property="fdDateOfBirth" dateTimeType="date"></xform:datetime>
							</td>
						</tr>
						<tr>
							<!-- 身份证号码 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffEntry.fdIdCard" />
							</td>
							<td>
								<c:choose>
									<c:when test="${isIdcardValidate==true}">
										<xform:text property="fdIdCard" onValueChange="idCardChange" style="width:95%;" className="inputsgl" validators="idCard" />
									</c:when>
									<c:otherwise>
										<xform:text property="fdIdCard" onValueChange="idCardChange" style="width:95%;" className="inputsgl" />
									</c:otherwise>
								</c:choose>
							</td>
							<!-- 参加工作时间 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffEntry.fdWorkTime" />
							</td>
							<td width="35%">
								<xform:datetime property="fdWorkTime" dateTimeType="date" validators="workTime"></xform:datetime>
							</td>
						</tr>
						<tr>
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffEntry.fdProfession" />
							</td>
							<td colspan="3">
								<xform:text property="fdProfession" style="width:50%"></xform:text>
							</td>
						</tr>
						<tr>
							<!-- 民族 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffEntry.fdNation" />
							</td>
							<td width="35%">
								<xform:select property="fdNationId" showStatus="edit">
									<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType = 'fdNation'"></xform:beanDataSource>
								</xform:select>
							</td>
							<!-- 政治面貌 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffEntry.fdPoliticalLandscape" />
							</td>
							<td width="35%">
								<xform:select property="fdPoliticalLandscapeId" showStatus="edit">
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
								<xform:datetime property="fdDateOfGroup" dateTimeType="date"></xform:datetime>
							</td>
							<!-- 入党日期 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffEntry.fdDateOfParty" />
							</td>
							<td width="35%">
								<xform:datetime property="fdDateOfParty" dateTimeType="date"></xform:datetime>
							</td>
						</tr>
						<tr>
							<!-- 最高学历 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffEntry.fdHighestEducation" />
							</td>
							<td width="35%">
								<xform:select property="fdHighestEducationId" showStatus="edit">
									<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType = 'fdHighestEducation'"></xform:beanDataSource>
								</xform:select>
							</td>
							<!-- 最高学位 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffEntry.fdHighestDegree" />
							</td>
							<td width="35%">
								<xform:select property="fdHighestDegreeId" showStatus="edit">
									<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType = 'fdHighestDegree'"></xform:beanDataSource>
								</xform:select>
							</td>
						</tr>
						<tr>
							<!-- 婚姻情况 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffEntry.fdMaritalStatus" />
							</td>
							<td width="35%">
								<xform:select property="fdMaritalStatusId" showStatus="edit">
									<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType = 'fdMaritalStatus'"></xform:beanDataSource>
								</xform:select>
							</td>
							<!-- 健康情况 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffEntry.fdHealth" />
							</td>
							<td width="35%">
								<xform:select property="fdHealthId" showStatus="edit">
									<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType = 'fdHealth'"></xform:beanDataSource>
								</xform:select>
							</td>
						</tr>
						<tr>
							<!-- 身高（厘米） -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffEntry.fdStature" />
							</td>
							<td width="35%">
								<xform:text property="fdStature" style="width:98%;" validators="digits min(1)" className="inputsgl" />
							</td>
							<!-- 体重（千克） -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffEntry.fdWeight" />
							</td>
							<td width="35%">
								<xform:text property="fdWeight" style="width:98%;" validators="digits min(1)" className="inputsgl" />
							</td>
						</tr>
						<tr>
							<!-- 现居地 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffEntry.fdLivingPlace" />
							</td>
							<td width="35%">
								<xform:text property="fdLivingPlace" style="width:98%;" className="inputsgl" />
							</td>
							<!-- 籍贯 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffEntry.fdNativePlace" />
							</td>
							<td width="35%">
								<xform:text property="fdNativePlace" style="width:98%;" className="inputsgl" />
							</td>
						</tr>
						<tr>
							<!-- 出生地 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffEntry.fdHomeplace" />
							</td>
							<td width="35%">
								<xform:text property="fdHomeplace" style="width:98%;" className="inputsgl" />
							</td>
							<!-- 户口性质 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffEntry.fdAccountProperties" />
							</td>
							<td width="35%">
								<xform:text property="fdAccountProperties" style="width:98%;" className="inputsgl" />
							</td>
						</tr>
						<tr>
							<!-- 户口所在地 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffEntry.fdRegisteredResidence" />
							</td>
							<td width="35%">
								<xform:text property="fdRegisteredResidence" style="width:98%;" className="inputsgl" />
							</td>
							<!-- 户口所在派出所 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffEntry.fdResidencePoliceStation" />
							</td>
							<td width="35%">
								<xform:text property="fdResidencePoliceStation" style="width:98%;" className="inputsgl" />
							</td>
						</tr>
					</table>
				</ui:content>
				<%--联系信息--%>
				<ui:content expand="true" title="${ lfn:message('hr-staff:hrStaffPersonInfo.contactInfo') }">
					<table class="tb_normal" width=100%>
						<tr>
							<!-- 手机 -->
							<td width="15%" class="td_normal_title">
								${ lfn:message('hr-staff:hrStaffEntry.fdMobileNo') }
							</td>
							<td width="35%">
								<xform:text property="fdMobileNo" validators="phoneNumber uniqueMobileNo" style="width:95%;" className="inputsgl"
										htmlElementProperties="placeholder='${ lfn:message('hr-staff:hrStaffEntry.moblieNo.tips') }'"/>
							</td>
							<!-- 邮箱 -->
							<td width="15%" class="td_normal_title">
								${ lfn:message('hr-staff:hrStaffEntry.fdEmail') }
							</td>
							<td width="35%">
								<xform:text property="fdEmail" validators="email" style="width:98%;" className="inputsgl" />
							</td>
						</tr>
						<tr>
							<!-- 紧急联系人 -->
							<td width="15%" class="td_normal_title">
								${ lfn:message('hr-staff:hrStaffEntry.fdEmergencyContact') }
							</td>
							<td width="35%">
								<xform:text property="fdEmergencyContact" style="width:98%;" className="inputsgl" />
							</td>
							<!-- 紧急联系人电话 -->
							<td width="15%" class="td_normal_title">
								${ lfn:message('hr-staff:hrStaffEntry.fdEmergencyContactPhone') }
							</td>
							<td width="35%">
								<xform:text property="fdEmergencyContactPhone" style="width:98%;" className="inputsgl" validators="phoneNumber"/>
							</td>
						</tr>
						<tr>
							<!-- 其他联系方式 -->
							<td width="15%" class="td_normal_title">
								${ lfn:message('hr-staff:hrStaffEntry.fdOtherContact') }
							</td>
							<td width="85%" colspan="3">
								<xform:text property="fdOtherContact" style="width:98%;" className="inputsgl" />
							</td>
						</tr>
					</table>
				</ui:content>
				<%-- 工作经历 --%>
				<ui:content title="${lfn:message('hr-staff:hrStaffEntry.fdHistory') }" expand="true">
					<table class="tb_normal" width="100%">
						<tr>
							<td><span class="fsBtnGroup"> <a class="fsBtnAdd"
									href="javascript:void(0);"
									onclick="HR_AddRatifyEntryNew('TABLE_DocList_fdHistory_Form','table_of_fdHistory_detail_edit');">
										<bean:message bundle="hr-staff"
											key="hrStaffHistory.addStaffHistory" />
								</a>
							</span></td>
						</tr>
					
						<tr>   
							<td colspan="4" width="100%">
								<table class="tb_normal" width="100%" id="TABLE_DocList_fdHistory_Form" align="center" tbdraggable="true">
									<tr align="center" >
										<td style="width: 20px;" class="bgColorTd"></td>
										<td style="width: 40px;" class="bgColorTd">${lfn:message('page.serial')}</td>
										<td class="bgColorTd">
											${lfn:message('hr-staff:hrStaffHistory.fdName')}</td>
										<td class="bgColorTd">
											${lfn:message('hr-staff:hrStaffHistory.fdPost')}</td>
										<td class="bgColorTd">
											${lfn:message('hr-staff:hrStaffHistory.fdStartDate')}</td>
										<td class="bgColorTd">
											${lfn:message('hr-staff:hrStaffHistory.fdEndDate')}</td>
										</td>
									</tr>
									<tr KMSS_IsReferRow="1" style="display: none;" class="fdItem">
										<td align="center">
										   <a href="javascript:void(0);" onclick="DocList_DeleteRow();closeDetail('TABLE_DocList_fdHistory_Form','table_of_fdHistory_detail_edit');"
											title="${lfn:message('doclist.delete')}"> 
											<img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
										    </a> 
											<xform:text property="fdHistory_Form[!{index}].fdDesc" showStatus="noShow" />
											<xform:text property="fdHistory_Form[!{index}].fdLeaveReason" showStatus="noShow" />
										</td>
										<td align="center" KMSS_IsRowIndex="1">
										   <span id="fdOOrderHistory!{index}"><c:out value="!{index}"></c:out></span>
										</td>
										<td align="center">
											<%-- 公司名称--%> 
											<input type="hidden" name="fdHistory_Form[!{index}].fdId" value="" disabled="true" />
											<input type="text" name="fdHistory_Form[!{index}].fdNameNew" disabled="true" class="inputsgl">
											<div id="_xform_fdHistory_Form[!{index}].fdName" _xform_type="text" style="display: none;">
											<xform:text property="fdHistory_Form[!{index}].fdName" showStatus="edit"
													subject="${lfn:message('hr-staff:hrStaffHistory.fdName')}"
													validators=" maxLength(200)" style="width:95%;" />
											</div>
										</td>
										<td align="center">
											<%-- 职位--%> <input type="text"
											name="fdHistory_Form[!{index}].fdPostNew" disabled="true"
											class="inputsgl">
											<div id="_xform_fdHistory_Form[!{index}].fdPost"
												_xform_type="text" style="display: none;">
												<xform:text property="fdHistory_Form[!{index}].fdPost"
													showStatus="edit"
													subject="${lfn:message('hr-staff:hrStaffHistory.fdPost')}"
													validators=" maxLength(200)" style="width:95%;" />
											</div>
										</td>
										 <td align="center">
					                                        <%-- 开始日期--%>
					                                        <input type="text" name="fdHistory_Form[!{index}].fdStartDateNew" disabled="true"  class="inputsgl">
					                                        <div id="_xform_fdHistory_Form[!{index}].fdStartDate" _xform_type="datetime" style="display:none;">
					                                            <xform:datetime property="fdHistory_Form[!{index}].fdStartDate" showStatus="edit" dateTimeType="date" style="width:95%;" />
					                                        </div>
					                                   </td>
										<td align="center">
											<%-- 结束日期--%> 
											<input type="text" name="fdHistory_Form[!{index}].fdEndDateNew" disabled="true" class="inputsgl">
											<div id="_xform_fdHistory_Form[!{index}].fdEndDate" _xform_type="datetime" style="display: none;">
												<xform:datetime property="fdHistory_Form[!{index}].fdEndDate" showStatus="edit" dateTimeType="date" style="width:95%;" />
											</div>
										</td>
									</tr>
									<input type="hidden" name="fdHistory_vstatusLength" value="${fn:length(hrStaffEntryForm.fdHistory_Form)}">
									<c:forEach items="${hrStaffEntryForm.fdHistory_Form}"
										var="fdHistory_FormItem" varStatus="vstatus">
										<tr KMSS_IsContentRow="1" class="fdItem">
											<td align="center"><a href="javascript:void(0);"
												onclick="DocList_DeleteRow();closeDetail('TABLE_DocList_fdHistory_Form','table_of_fdHistory_detail_edit');"
												title="${lfn:message('doclist.delete')}"> <img
													src="${KMSS_Parameter_StylePath}icons/icon_del.png"
													border="0" />
											</a> <xform:text
													property="fdHistory_Form[${vstatus.index}].fdDesc"
													showStatus="noShow" /> <xform:text
													property="fdHistory_Form[${vstatus.index}].fdLeaveReason"
													showStatus="noShow" /></td>
											<td align="center"
												onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdHistory_Form','table_of_fdHistory_detail_edit')">
												<span id="fdOOrderHistory!{index}">${vstatus.index+1}</span>
											</td>
											<td align="center"
												onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdHistory_Form','table_of_fdHistory_detail_edit')">
												<%-- 公司名称--%> 
												<input type="hidden" name="fdHistory_Form[${vstatus.index}].fdId" value="${fdHistory_FormItem.fdId}" />
												<input type="text" name="fdHistory_Form[${vstatus.index}].fdNameNew" disabled="true"  class="inputsgl" />
												<div id="_xform_fdHistory_Form[${vstatus.index}].fdName"
													_xform_type="text" style="display:none;">
													<xform:text
														property="fdHistory_Form[${vstatus.index}].fdName"
														showStatus="edit"
														subject="${lfn:message('hr-staff:hrStaffHistory.fdName')}"
														validators=" maxLength(200)" style="width:95%;" />
												</div>
											</td>
											<td align="center"
												onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdHistory_Form','table_of_fdHistory_detail_edit')">
												<%-- 职位--%>
												<input type="text" name="fdHistory_Form[${vstatus.index}].fdPostNew" disabled="true"  class="inputsgl" />
												<div id="_xform_fdHistory_Form[${vstatus.index}].fdPost"
													_xform_type="text" style="display:none;">
													<xform:text
														property="fdHistory_Form[${vstatus.index}].fdPost"
														showStatus="edit"
														subject="${lfn:message('hr-staff:hrStaffHistory.fdPost')}"
														validators=" maxLength(200)" style="width:95%;" />
												</div>
											</td>
											<td align="center"
												onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdHistory_Form','table_of_fdHistory_detail_edit')">
												<%-- 开始日期--%>
												<input type="text" name="fdHistory_Form[${vstatus.index}].fdStartDateNew" disabled="true"  class="inputsgl">
												<div
													id="_xform_fdHistory_Form[${vstatus.index}].fdStartDate"
													_xform_type="datetime " style="display:none;">
													<xform:datetime
														property="fdHistory_Form[${vstatus.index}].fdStartDate"
														showStatus="edit" dateTimeType="date"
														style="width:95%;" />
												</div>
											</td>
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdHistory_Form','table_of_fdHistory_detail_edit')">
												<%-- 结束日期--%>
												<input type="text" name="fdHistory_Form[${vstatus.index}].fdEndDateNew" disabled="true"  class="inputsgl">
												<div id="_xform_fdHistory_Form[${vstatus.index}].fdEndDate" _xform_type="datetime" style="display:none;">
													<xform:datetime
														property="fdHistory_Form[${vstatus.index}].fdEndDate"
														showStatus="edit" dateTimeType="date"
														style="width:95%;" />
												</div>
											</td>
										</tr>
									</c:forEach>
								</table> <input type="hidden" name="fdHistory_Flag" value="1"> 
								<script>Com_IncludeFile("doclist.js");</script> 
					                           <script>DocList_Info.push('TABLE_DocList_fdHistory_Form');</script>
							</td>
						</tr>
					</table>
				</ui:content>
				<%-- 教育记录 --%>
				<ui:content title="${lfn:message('hr-staff:hrStaffEntry.fdEducations') }" expand="true">
					<table class="tb_normal" width="100%">
						<tr>
							<td><span class="fsBtnGroup"> <a class="fsBtnAdd"
									href="javascript:void(0);"
									onclick="HR_AddRatifyEntryNew('TABLE_DocList_fdEducations_Form','table_of_fdEducations_detail_edit');">
										<bean:message bundle="hr-staff"
											key="hrStaffEduExp.addStaffEduExp" />
								</a>
							</span></td>
						</tr>
						<tr>
							<td colspan="4" width="100%">
								<table class="tb_normal" width="100%"
									id="TABLE_DocList_fdEducations_Form" align="center"
									tbdraggable="true">

									<tr align="center" class="tr_normal_title">
										<td style="width: 20px;" class="bgColorTd"></td>
										<td style="width: 40px;" class="bgColorTd">${lfn:message('page.serial')}</td>
										<td class="bgColorTd">${lfn:message('hr-staff:hrStaffEduExp.fdName')}</td>
										<td class="bgColorTd">${lfn:message('hr-staff:hrStaffEduExp.fdMajor')}</td>
										<td class="bgColorTd">${lfn:message('hr-staff:hrStaffEduExp.fdAcademic')}</td>
										<td class="bgColorTd">${lfn:message('hr-staff:hrStaffEduExp.fdEntranceDate')}</td>
										<td class="bgColorTd">${lfn:message('hr-staff:hrStaffEduExp.fdGraduationDate')}</td>
									</tr>
									<tr KMSS_IsReferRow="1" style="display: none;" class="fdItem">
										<td align="center">
										<a href="javascript:void(0);" onclick="DocList_DeleteRow();closeDetail('TABLE_DocList_fdEducations_Form','table_of_fdEducations_detail_edit');"
											title="${lfn:message('doclist.delete')}"> 
											<img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
										</a>
										<xform:text property="fdEducations_Form[!{index}].fdRemark" showStatus="noShow" />
										</td>
										<td align="center" KMSS_IsRowIndex="1"><span
											id="fdOOrderEducations!{index}"><c:out value="!{index}"></c:out></span>
										</td>
										<td align="center">
											<%-- 学校名称--%> <input type="hidden"
											name="fdEducations_Form[!{index}].fdId" value=""
											disabled="true" />
											<input type="text" name="fdEducations_Form[!{index}].fdNameNew" disabled="true"  class="inputsgl">
											<div id="_xform_fdEducations_Form[!{index}].fdName" style="display: none;"
												_xform_type="text"> 
												<xform:text property="fdEducations_Form[!{index}].fdName"
													showStatus="edit" 
													subject="${lfn:message('hr-staff:hrStaffEduExp.fdName')}"
													validators=" maxLength(200)" style="width:95%;" />
											</div>
										</td>
										<td align="center">
											<%-- 专业名称--%>
											<input type="text" name="fdEducations_Form[!{index}].fdMajorNew" disabled="true" class="inputsgl"/>
											<div id="_xform_fdEducations_Form[!{index}].fdMajor" style="display: none;"
												_xform_type="text">
												<xform:text property="fdEducations_Form[!{index}].fdMajor"
													showStatus="edit"
													subject="${lfn:message('hr-staff:hrStaffEduExp.fdMajor')}"
													validators=" maxLength(200)" style="width:95%;" />
											</div>
										</td>
										<td align="center">
											<%-- 学位--%>
											<input type="text" name="fdEducations_Form[!{index}].fdAcadeRecordName" disabled="true" class="inputsgl"/>
											<input type="hidden" name="fdEducations_Form[!{index}].fdAcadeRecord" class="inputsgl" />
											<div id="_xform_fdEducations_Form[!{index}].fdAcadeRecordId" style="display: none;"
												_xform_type="text">
												<xform:select property="fdEducations_Form[!{index}].fdAcadeRecordId" htmlElementProperties="id='fdAcadeRecordId'" showStatus="edit">
                                                    <xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" 
                                                    whereBlock="hrStaffPersonInfoSettingNew.fdType = 'fdHighestDegree'"/>
                                                </xform:select>
											</div>
										</td>
										<td align="center">
											<%-- 入学日期--%>
											<input type="text" name="fdEducations_Form[!{index}].fdEntranceDateNew" disabled="true"  class="inputsgl">
											<div id="_xform_fdEducations_Form[!{index}].fdEntranceDate" style="display: none;"
												_xform_type="datetime">
												<xform:datetime
													property="fdEducations_Form[!{index}].fdEntranceDate"
													showStatus="edit" dateTimeType="date" style="width:95%;" />
											</div>
										</td>
										<td align="center">
											<%-- 毕业日期--%>
											<input type="text" name="fdEducations_Form[!{index}].fdGraduationDateNew" disabled="true"  class="inputsgl">
											<div id="_xform_fdEducations_Form[!{index}].fdGraduationDate" style="display: none;"
												_xform_type="datetime">
												<xform:datetime
													property="fdEducations_Form[!{index}].fdGraduationDate"
													showStatus="edit" dateTimeType="date" style="width:95%;" />
											</div>
										</td>
									</tr>
									<input type="hidden" name="fdEducations_vstatusLength" value="${fn:length(hrStaffEntryForm.fdEducations_Form)}">
									<c:forEach items="${hrStaffEntryForm.fdEducations_Form}"
										var="fdEducations_FormItem" varStatus="vstatus">
										<tr KMSS_IsContentRow="1" class="fdItem">
											<td align="center"><a href="javascript:void(0);"
												onclick="DocList_DeleteRow();closeDetail('TABLE_DocList_fdEducations_Form','table_of_fdEducations_detail_edit');"
												title="${lfn:message('doclist.delete')}"> <img
													src="${KMSS_Parameter_StylePath}icons/icon_del.png"
													border="0" />
											</a> <xform:text
													property="fdEducations_Form[${vstatus.index}].fdRemark"
													showStatus="noShow" /></td>
											<td align="center"
												onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdEducations_Form','table_of_fdEducations_detail_edit')">
												<span id="fdOOrderEducations!{index}">${vstatus.index+1}</span>
											</td>
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdEducations_Form','table_of_fdEducations_detail_edit')">
												<%-- 学校名称--%> <input type="hidden"
												name="fdEducations_Form[${vstatus.index}].fdId"
												value="${fdEducations_FormItem.fdId}" />
												<input type="text" name="fdEducations_Form[${vstatus.index}].fdNameNew" disabled="true"  class="inputsgl" />
												<div id="_xform_fdEducations_Form[${vstatus.index}].fdName"
													_xform_type="text" style="display:none;">
													<xform:text
														property="fdEducations_Form[${vstatus.index}].fdName"
														showStatus="edit" 
														subject="${lfn:message('hr-staff:hrStaffEduExp.fdName')}"
														validators=" maxLength(200)" style="width:95%;" />
												</div>
											</td>
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdEducations_Form','table_of_fdEducations_detail_edit')">
												<%-- 专业名称--%>
												<input type="text" name="fdEducations_Form[${vstatus.index}].fdMajorNew" disabled="true"  class="inputsgl" />
												<div id="_xform_fdEducations_Form[${vstatus.index}].fdMajor"
													_xform_type="text" style="display:none;">
													<xform:text
														property="fdEducations_Form[${vstatus.index}].fdMajor"
														showStatus="edit"
														subject="${lfn:message('hr-staff:hrStaffEduExp.fdMajor')}"
														validators=" maxLength(200)" style="width:95%;" />
												</div>
											</td>
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdEducations_Form','table_of_fdEducations_detail_edit')">
												<%-- 学位--%>
												<input type="text" value="${fdEducations_FormItem.fdAcadeRecordName }" name="fdEducations_Form[${vstatus.index}].fdAcadeRecordName" disabled="true"  class="inputsgl" />
												<input type="hidden" value="${fdEducations_FormItem.fdAcadeRecordId }" name="fdEducations_Form[${vstatus.index}].fdAcadeRecord" disabled="true"  class="inputsgl" />
												<div
													id="_xform_fdEducations_Form[${vstatus.index}].fdAcadeRecordId"
													_xform_type="text" style="display:none;">
													<xform:select value="${fdEducations_FormItem.fdAcadeRecordId }" property="fdEducations_Form[${vstatus.index}].fdAcadeRecordId" htmlElementProperties="id='fdAcadeRecordId'" showStatus="edit">
	                                                    <xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" 
	                                                    whereBlock="hrStaffPersonInfoSettingNew.fdType = 'fdHighestDegree'"/>
                                                	</xform:select>
												</div>
											</td>
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdEducations_Form','table_of_fdEducations_detail_edit')">
												<%-- 入学日期--%>
												<input type="text" name="fdEducations_Form[${vstatus.index}].fdEntranceDateNew" disabled="true"  class="inputsgl" />
												<div
													id="_xform_fdEducations_Form[${vstatus.index}].fdEntranceDate"
													_xform_type="datetime" style="display:none;">
													<xform:datetime
														property="fdEducations_Form[${vstatus.index}].fdEntranceDate"
														showStatus="readOnly" dateTimeType="date" style="width:95%;" />
												</div>
											</td>
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdEducations_Form','table_of_fdEducations_detail_edit')">
												<%-- 毕业日期--%>
												<input type="text" name="fdEducations_Form[${vstatus.index}].fdGraduationDateNew" disabled="true"  class="inputsgl" />
												<div
													id="_xform_fdEducations_Form[${vstatus.index}].fdGraduationDate"
													_xform_type="datetime" style="display:none;">
													<xform:datetime
														property="fdEducations_Form[${vstatus.index}].fdGraduationDate"
														showStatus="readOnly" dateTimeType="date" style="width:95%;" />
												</div>
											</td>
										</tr>
									</c:forEach>	
								</table> <input type="hidden" name="fdEducations_Flag" value="1">
								<script>
                                        DocList_Info.push('TABLE_DocList_fdEducations_Form');
                                    </script>
							</td>
						</tr>
					</table>
				</ui:content>
				<%-- 资格证书 --%>
				<ui:content title="${lfn:message('hr-staff:hrStaffEntry.fdCertificate') }" expand="true">
					<table class="tb_normal" width="100%">
						<tr>
							<td><span class="fsBtnGroup"> <a class="fsBtnAdd"
									href="javascript:void(0);"
									onclick="HR_AddRatifyEntryNew('TABLE_DocList_fdCertificate_Form','table_of_fdCertificate_detail_edit');">
										<bean:message bundle="hr-staff"
											key="hrStaffCertifi.addStaffCertifi" />
								</a>
							</span></td>
						</tr>
						<tr>
							<td colspan="4" width="100%">
								<table class="tb_normal" width="100%"
									id="TABLE_DocList_fdCertificate_Form" align="center"
									tbdraggable="true">

									<tr align="center" class="tr_normal_title">
										<td style="width: 20px;" class="bgColorTd"></td>
										<td style="width: 40px;" class="bgColorTd">${lfn:message('page.serial')}</td>
										<td class="bgColorTd">${lfn:message('hr-staff:hrStaffCertifi.fdName')}</td>
										<td class="bgColorTd">${lfn:message('hr-staff:hrStaffCertifi.fdIssuingUnit')}</td>
										<td class="bgColorTd">${lfn:message('hr-staff:hrStaffCertifi.fdIssueDate')}</td>
										<td class="bgColorTd">${lfn:message('hr-staff:hrStaffCertifi.fdInvalidDate')}</td>
									</tr>
									<tr KMSS_IsReferRow="1" style="display: none;" class="fdItem">
										<td align="center"><a href="javascript:void(0);"
											onclick="DocList_DeleteRow();closeDetail('TABLE_DocList_fdCertificate_Form','table_of_fdCertificate_detail_edit');"
											title="${lfn:message('doclist.delete')}"> <img
												src="${KMSS_Parameter_StylePath}icons/icon_del.png"
												border="0" />
										</a> <xform:text property="fdCertificate_Form[!{index}].fdRemark"
												showStatus="noShow" /></td>
										<td align="center" KMSS_IsRowIndex="1"><span
											id="fdOOrderCertificate!{index}"><c:out value="!{index}"></c:out></span>
										</td>
										<td align="center">
											<%-- 证书名称--%> <input type="hidden"
											name="fdCertificate_Form[!{index}].fdId" value=""
											disabled="true" />
											<input type="text" name="fdCertificate_Form[!{index}].fdNameNew" disabled="true"  class="inputsgl">
											<div id="_xform_fdCertificate_Form[!{index}].fdName" style="display: none;"
												_xform_type="text">
												<xform:text property="fdCertificate_Form[!{index}].fdName"
													showStatus="edit" 
													subject="${lfn:message('hr-staff:hrStaffCertifi.fdName')}"
													validators=" maxLength(200)" style="width:95%;" />
											</div>
										</td>
										<td align="center">
											<%-- 颁发单位--%>
											<input type="text" name="fdCertificate_Form[!{index}].fdIssuingUnitNew" disabled="true"  class="inputsgl">
											<div id="_xform_fdCertificate_Form[!{index}].fdIssuingUnit" style="display: none;"
												_xform_type="text">
												<xform:text
													property="fdCertificate_Form[!{index}].fdIssuingUnit"
													showStatus="edit"
													subject="${lfn:message('hr-staff:hrStaffCertifi.fdIssuingUnit')}"
													validators=" maxLength(100)" style="width:95%;" />
											</div>
										</td>
										<td align="center">
											<%-- 颁发日期--%>
											<input type="text" name="fdCertificate_Form[!{index}].fdIssueDateNew" disabled="true"  class="inputsgl">
											<div id="_xform_fdCertificate_Form[!{index}].fdIssueDate" style="display: none;"
												_xform_type="datetime">
												<xform:datetime
													property="fdCertificate_Form[!{index}].fdIssueDate"
													showStatus="edit" dateTimeType="date" style="width:95%;" />
											</div>
										</td>
										<td align="center">
											<%-- 失效日期--%>
											<input type="text" name="fdCertificate_Form[!{index}].fdInvalidDateNew" disabled="true"  class="inputsgl">
											<div id="_xform_fdCertificate_Form[!{index}].fdInvalidDate" style="display: none;"
												_xform_type="datetime">
												<xform:datetime
													property="fdCertificate_Form[!{index}].fdInvalidDate"
													showStatus="edit" dateTimeType="date" style="width:95%;" />
											</div>
										</td>
									</tr>
									<input type="hidden" name="fdCertificate_vstatusLength" value="${fn:length(hrStaffEntryForm.fdCertificate_Form)}">
									<c:forEach items="${hrStaffEntryForm.fdCertificate_Form}"
										var="fdCertificate_FormItem" varStatus="vstatus">
										<tr KMSS_IsContentRow="1" class="fdItem">
											<td align="center"><a href="javascript:void(0);"
												onclick="DocList_DeleteRow();closeDetail('TABLE_DocList_fdCertificate_Form','table_of_fdCertificate_detail_edit');"
												title="${lfn:message('doclist.delete')}"> <img
													src="${KMSS_Parameter_StylePath}icons/icon_del.png"
													border="0" />
											</a> <xform:text
													property="fdCertificate_Form[${vstatus.index}].fdRemark"
													showStatus="noShow" /></td>
											<td align="center"
												onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdCertificate_Form','table_of_fdCertificate_detail_edit')">
												<span id="fdOOrderCertificate!{index}">${vstatus.index+1}</span>
											</td>
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdCertificate_Form','table_of_fdCertificate_detail_edit')">
												<%-- 证书名称--%> <input type="hidden"
												name="fdCertificate_Form[${vstatus.index}].fdId"
												value="${fdCertificate_FormItem.fdId}" />
												<input type="text" name="fdCertificate_Form[${vstatus.index}].fdNameNew" disabled="true"  class="inputsgl" />
												<div id="_xform_fdCertificate_Form[${vstatus.index}].fdName"
													_xform_type="text" style="display:none;">
													<xform:text
														property="fdCertificate_Form[${vstatus.index}].fdName"
														showStatus="edit" 
														subject="${lfn:message('hr-staff:hrStaffCertifi.fdName')}"
														validators=" maxLength(200)" style="width:95%;" />
												</div>
											</td>
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdCertificate_Form','table_of_fdCertificate_detail_edit')">
												<%-- 颁发单位--%>
												<input type="text" name="fdCertificate_Form[${vstatus.index}].fdIssuingUnitNew" disabled="true"  class="inputsgl" />
												<div
													id="_xform_fdCertificate_Form[${vstatus.index}].fdIssuingUnit"
													_xform_type="text" style="display:none;">
													<xform:text
														property="fdCertificate_Form[${vstatus.index}].fdIssuingUnit"
														showStatus="edit"
														subject="${lfn:message('hr-staff:hrStaffCertifi.fdIssuingUnit')}"
														validators=" maxLength(100)" style="width:95%;" />
												</div>
											</td>
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdCertificate_Form','table_of_fdCertificate_detail_edit')">
												<%-- 颁发日期--%>
												<input type="text" name="fdCertificate_Form[${vstatus.index}].fdIssueDateNew" disabled="true"  class="inputsgl" />
												<div
													id="_xform_fdCertificate_Form[${vstatus.index}].fdIssueDate"
													_xform_type="datetime" style="display:none;">
													<xform:datetime
														property="fdCertificate_Form[${vstatus.index}].fdIssueDate"
														showStatus="readOnly" dateTimeType="date" style="width:95%;" />
												</div> 
											</td>
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdCertificate_Form','table_of_fdCertificate_detail_edit')">
												<%-- 失效日期--%>
												<input type="text" name="fdCertificate_Form[${vstatus.index}].fdInvalidDateNew" disabled="true"  class="inputsgl" />
												<div
													id="_xform_fdCertificate_Form[${vstatus.index}].fdInvalidDate"
													_xform_type="datetime" style="display:none;">
													<xform:datetime
														property="fdCertificate_Form[${vstatus.index}].fdInvalidDate"
														showStatus="readOnly" dateTimeType="date" style="width:95%;" />
												</div>
											</td>
										</tr>
									</c:forEach>
								</table> <input type="hidden" name="fdCertificate_Flag" value="1">
								<script>
                                        DocList_Info.push('TABLE_DocList_fdCertificate_Form');
                                    </script>
							</td>
						</tr>
					</table>
				</ui:content>
				<%-- 奖惩信息 --%>
				<ui:content title="${lfn:message('hr-staff:hrStaffEntry.fdRewardsPunishments') }" expand="true">
					<table class="tb_normal" width="100%">
						<tr>
							<td><span class="fsBtnGroup"> <a class="fsBtnAdd"
									href="javascript:void(0);"
									onclick="HR_AddRatifyEntryNew('TABLE_DocList_fdRewardsPunishments_Form','table_of_fdRewardsPunishments_detail_edit');">
										<bean:message bundle="hr-staff"
											key="hrStaffRewPuni.addStaffRewPuni" />
								</a>
							</span></td>
						</tr>
						<tr>
							<td colspan="4" width="100%">
								<table class="tb_normal" width="100%"
									id="TABLE_DocList_fdRewardsPunishments_Form" align="center"
									tbdraggable="true">

									<tr align="center" class="tr_normal_title">
										<td style="width: 20px;" class="bgColorTd"></td>
										<td style="width: 40px;" class="bgColorTd">${lfn:message('page.serial')}</td>
										<td class="bgColorTd">${lfn:message('hr-staff:hrStaffRewPuni.fdName')}</td>
										<td class="bgColorTd">${lfn:message('hr-staff:hrStaffRewPuni.fdDate')}</td>
									</tr>
									<tr KMSS_IsReferRow="1" style="display: none;" class="fdItem">
										<td align="center"><a href="javascript:void(0);"
											onclick="DocList_DeleteRow();closeDetail('TABLE_DocList_fdRewardsPunishments_Form','table_of_fdRewardsPunishments_detail_edit');"
											title="${lfn:message('doclist.delete')}"> <img
												src="${KMSS_Parameter_StylePath}icons/icon_del.png"
												border="0" />
										</a> <xform:text
												property="fdRewardsPunishments_Form[!{index}].fdRemark"
												showStatus="noShow" /></td>
										<td align="center" KMSS_IsRowIndex="1"><span
											id="fdOOrderRewardsPunishments!{index}"><c:out value="!{index}"></c:out></span>
										</td>
										<td align="center">
											<%-- 奖惩名称--%> <input type="hidden"
											name="fdRewardsPunishments_Form[!{index}].fdId" value=""
											disabled="true" />
											<input type="text" name="fdRewardsPunishments_Form[!{index}].fdNameNew" disabled="true"  class="inputsgl">
											<div id="_xform_fdRewardsPunishments_Form[!{index}].fdName" style="display: none;"
												_xform_type="text">
												<xform:text
													property="fdRewardsPunishments_Form[!{index}].fdName"
													showStatus="edit" 
													subject="${lfn:message('hr-staff:hrStaffRewPuni.fdName')}"
													validators=" maxLength(200)" style="width:95%;" />
											</div>
										</td>
										<td align="center">
											<%-- 奖惩日期--%>
											<input type="text" name="fdRewardsPunishments_Form[!{index}].fdDateNew" disabled="true"  class="inputsgl">
											<div id="_xform_fdRewardsPunishments_Form[!{index}].fdDate" style="display: none;"
												_xform_type="datetime">
												<xform:datetime
													property="fdRewardsPunishments_Form[!{index}].fdDate"
													showStatus="edit" dateTimeType="date" style="width:95%;" />
											</div>
										</td>
									</tr>
									<input type="hidden" name="fdRewardsPunishments_vstatusLength" value="${fn:length(hrStaffEntryForm.fdRewardsPunishments_Form)}">
									<c:forEach
										items="${hrStaffEntryForm.fdRewardsPunishments_Form}"
										var="fdRewardsPunishments_FormItem" varStatus="vstatus">
										<tr KMSS_IsContentRow="1" class="fdItem">
											<td align="center"><a href="javascript:void(0);"
												onclick="DocList_DeleteRow();closeDetail('TABLE_DocList_fdRewardsPunishments_Form','table_of_fdRewardsPunishments_detail_edit');"
												title="${lfn:message('doclist.delete')}"> <img
													src="${KMSS_Parameter_StylePath}icons/icon_del.png"
													border="0" />
											</a> <xform:text
													property="fdRewardsPunishments_Form[${vstatus.index}].fdRemark"
													showStatus="noShow" /></td>
											<td align="center"
												onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdRewardsPunishments_Form','table_of_fdRewardsPunishments_detail_edit')">
												<span id="fdOOrderRewardsPunishments!{index}">${vstatus.index+1}</span>
											</td>
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdRewardsPunishments_Form','table_of_fdRewardsPunishments_detail_edit')">
												<%-- 奖惩名称--%> <input type="hidden"
												name="fdRewardsPunishments_Form[${vstatus.index}].fdId"
												value="${fdRewardsPunishments_FormItem.fdId}" />
												<input type="text" name="fdRewardsPunishments_Form[${vstatus.index}].fdNameNew" disabled="true"  class="inputsgl" />
												<div
													id="_xform_fdRewardsPunishments_Form[${vstatus.index}].fdName"
													_xform_type="text" style="display:none;">
													<xform:text
														property="fdRewardsPunishments_Form[${vstatus.index}].fdName"
														showStatus="edit" 
														subject="${lfn:message('hr-staff:hrStaffRewPuni.fdName')}"
														validators=" maxLength(200)" style="width:95%;" />
												</div>
											</td>
											 
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdRewardsPunishments_Form','table_of_fdRewardsPunishments_detail_edit')">
												<%-- 奖惩日期--%>
												<input type="text" name="fdRewardsPunishments_Form[${vstatus.index}].fdDateNew" disabled="true"  class="inputsgl" />
												<div
													id="_xform_fdRewardsPunishments_Form[${vstatus.index}].fdDate"
													_xform_type="datetime" style="display:none;">
													<xform:datetime
														property="fdRewardsPunishments_Form[${vstatus.index}].fdDate"
														showStatus="readOnly" dateTimeType="date" style="width:95%;" />
												</div>
											</td>
										</tr>
									</c:forEach>
								</table> <input type="hidden" name="fdRewardsPunishments_Flag" value="1">
								<script>
                                        DocList_Info.push('TABLE_DocList_fdRewardsPunishments_Form');
                                    </script>
							</td>
						</tr>
					</table>
				</ui:content>
				<!-- 家庭信息 start -->
				<ui:content title="${lfn:message('hr-staff:hrStaffPerson.family') }" expand="true">
					<table class="tb_normal" width="100%">
					<!-- 添加家庭信息 -->
						<tr>
							<td><span class="fsBtnGroup"> <a class="fsBtnAdd"
									href="javascript:void(0);"
									onclick="HR_AddRatifyEntryNew('TABLE_DocList_fdfamily_Form','table_of_fdfamily_detail_edit');">
										<bean:message bundle="hr-staff" key="hrStaffPerson.family.add" />
								</a>
							</span></td>
						</tr>
						<tr>
							<td colspan="4" width="100%">
								<table class="tb_normal" width="100%"
									id="TABLE_DocList_fdfamily_Form" align="center"
									tbdraggable="true">
									<tr align="center" class="tr_normal_title">
										<td style="width: 20px;" class="bgColorTd"></td>
										<td style="width: 40px;" class="bgColorTd">${lfn:message('page.serial')}</td>
										<td class="bgColorTd">${lfn:message('hr-staff:hrStaffPerson.family.related')}</td>
										<td class="bgColorTd">${lfn:message('hr-staff:hrStaffPerson.family.name')}</td>
										<td class="bgColorTd">${lfn:message('hr-staff:hrStaffPerson.family.occupation')}</td>
										<td class="bgColorTd">${lfn:message('hr-staff:hrStaffPerson.family.company')}</td>
										<td class="bgColorTd">${lfn:message('hr-staff:hrStaffPerson.family.connect')}</td>
									</tr>
									<tr KMSS_IsReferRow="1" style="display: none;" class="fdItem">
										<td align="center"><a href="javascript:void(0);"
											onclick="DocList_DeleteRow();closeDetail('TABLE_DocList_fdfamily_Form','table_of_fdfamily_detail_edit');"
											title="${lfn:message('doclist.delete')}"> <img
												src="${KMSS_Parameter_StylePath}icons/icon_del.png"
												border="0" />
										</a> <xform:text
												property="fdfamily_Form[!{index}].fdMemo"
												showStatus="noShow" /></td>
										<td align="center" KMSS_IsRowIndex="1"><span
											id="fdOOrderfdfamilys!{index}"><c:out value="!{index}"></c:out></span>
										</td>
										<td align="center">
												<%-- 家庭信息--%> <input type="hidden"
												name="fdfamily_Form[!{index}].fdId"
												value="${fdfamily_Form.fdId}" />
												<input type="text" name="fdfamily_Form[!{index}].fdRelatedNew" disabled="true"  class="inputsgl" />
												<div
													id="_xform_fdfamily_Form[!{index}].fdRelated"
													_xform_type="text" style="display:none;">
													<xform:text
														property="fdfamily_Form[!{index}].fdRelated"
														showStatus="edit" 
														subject="${lfn:message('hr-staff:hrStaffPerson.family.related')}"
														validators=" maxLength(200)" style="width:95%;" />
												</div>
											</td>
											<!-- 姓名 -->
											<td align="center">
												<input type="text" name="fdfamily_Form[!{index}].fdNameNew" disabled="true"  class="inputsgl" />
												<div
													id="_xform_fdfamily_Form[!{index}].fdName"
													_xform_type="text" style="display:none;">
													<xform:text
														property="fdfamily_Form[!{index}].fdName"
														showStatus="edit" 
														subject="${lfn:message('hr-staff:hrStaffPerson.family.name')}"
														validators=" maxLength(200)" style="width:95%;" />
												</div>
											</td>
											<!-- 职业 -->
											<td align="center">
												<input type="text" name="fdfamily_Form[!{index}].fdOccupationNew" disabled="true"  class="inputsgl" />
												<div
													id="_xform_fdfamily_Form[!{index}].fdOccupation"
													_xform_type="text" style="display:none;">
													<xform:text
														property="fdfamily_Form[!{index}].fdOccupation"
														showStatus="edit" 
														subject="${lfn:message('hr-staff:hrStaffPerson.family.occupation')}"
														validators=" maxLength(200)" style="width:95%;" />
												</div>
											</td>
												<!-- 任职单位 -->
											<td align="center" >
												<input type="text" name="fdfamily_Form[!{index}].fdCompanyNew" disabled="true"  class="inputsgl" />
												<div
													id="_xform_fdfamily_Form[!{index}].fdCompany"
													_xform_type="text" style="display:none;">
													<xform:text
														property="fdfamily_Form[!{index}].fdCompany"
														showStatus="edit" 
														subject="${lfn:message('hr-staff:hrStaffPerson.family.company')}"
														validators=" maxLength(200)" style="width:95%;" />
												</div>
											</td>
												<!-- 联系信息 -->
											<td align="center" >
												<input type="text" name="fdfamily_Form[!{index}].fdConnectNew" disabled="true"  class="inputsgl" />
												<div
													id="_xform_fdfamily_Form[!{index}].fdConnect"
													_xform_type="text" style="display:none;">
													<xform:text
														property="fdfamily_Form[!{index}].fdConnect"
														showStatus="edit" 
														subject="${lfn:message('hr-staff:hrStaffPerson.family.connect')}"
														validators=" maxLength(200)" style="width:95%;" />
												</div>
											</td>
									</tr>
									<input type="hidden" name="fdfamilys_vstatusLength" value="${fn:length(hrStaffEntryForm.fdRewardsPunishments_Form)}">
                                    <c:forEach
										items="${hrStaffEntryForm.fdfamily_Form}"
										var="fdfamily_Form" varStatus="vstatus">
										<tr KMSS_IsContentRow="1" class="fdItem">
											<td align="center">
												<a href="javascript:void(0);"
													onclick="DocList_DeleteRow();closeDetail('TABLE_DocList_fdfamily_Form','table_of_fdfamily_detail_edit');"
													title="${lfn:message('doclist.delete')}"> 
													<img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
												</a> 
											<!-- 备注 -->
											<xform:text
												property="fdfamily_Form[${vstatus.index}].fdMemo"
												showStatus="noShow" />
											</td>
											<!-- 序号 -->
											<td align="center"
												onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdfamily_Form','table_of_fdfamily_detail_edit')">
												<span id="fdOOrderfdfamilys${vstatus.index}">${vstatus.index+1}</span>
											</td>
											<!-- 内容体 satrt -->
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdfamily_Form','table_of_fdfamily_detail_edit')">
												<%-- 家庭信息--%> <input type="hidden"
												name="fdfamily_Form[${vstatus.index}].fdId"
												value="${fdfamily_Form.fdId}" />
												<input type="text" name="fdfamily_Form[${vstatus.index}].fdRelatedNew" disabled="true"  class="inputsgl" value="${ fdfamily_Form.fdRelated}" />
												<div
													id="_xform_fdfamily_Form[${vstatus.index}].fdRelated"
													_xform_type="text" style="display:none;">
													<xform:text
														property="fdfamily_Form[${vstatus.index}].fdRelated"
														showStatus="edit" 
														subject="${lfn:message('hr-staff:hrStaffPerson.family.related')}"
														validators=" maxLength(200)" style="width:95%;" />
												</div>
											</td>
											<!-- 姓名 -->
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdfamily_Form','table_of_fdfamily_detail_edit')">
												<input type="text" name="fdfamily_Form[${vstatus.index}].fdNameNew" disabled="true"  class="inputsgl"  value="${ fdfamily_Form.fdName}"/>
												<div
													id="_xform_fdfamily_Form[${vstatus.index}].fdName"
													_xform_type="text" style="display:none;">
													<xform:text
														property="fdfamily_Form[${vstatus.index}].fdName"
														showStatus="edit" 
														subject="${lfn:message('hr-staff:hrStaffPerson.family.name')}"
														validators=" maxLength(200)" style="width:95%;" />
												</div>
											</td>
											<!-- 职业 -->
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdfamily_Form','table_of_fdfamily_detail_edit')">
												<input type="text" name="fdfamily_Form[${vstatus.index}].fdOccupationNew" disabled="true"  class="inputsgl"  value="${ fdfamily_Form.fdOccupation}"/>
												<div
													id="_xform_fdfamily_Form[${vstatus.index}].fdOccupation"
													_xform_type="text" style="display:none;">
													<xform:text
														property="fdfamily_Form[${vstatus.index}].fdOccupation"
														showStatus="edit" 
														subject="${lfn:message('hr-staff:hrStaffPerson.family.occupation')}"
														validators=" maxLength(200)" style="width:95%;" />
												</div>
											</td>
												<!-- 任职单位 -->
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdfamily_Form','table_of_fdfamily_detail_edit')">
												<input type="text" name="fdfamily_Form[${vstatus.index}].fdCompanyNew" disabled="true"  class="inputsgl"  value="${ fdfamily_Form.fdCompany}"/>
												<div
													id="_xform_fdfamily_Form[${vstatus.index}].fdCompany"
													_xform_type="text" style="display:none;">
													<xform:text
														property="fdfamily_Form[${vstatus.index}].fdCompany"
														showStatus="edit" 
														subject="${lfn:message('hr-staff:hrStaffPerson.family.company')}"
														validators=" maxLength(200)" style="width:95%;" />
												</div>
											</td>
												<!-- 联系信息 -->
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdfamily_Form','table_of_fdfamily_detail_edit')">
												<input type="text" name="fdfamily_Form[${vstatus.index}].fdConnectNew" disabled="true"  class="inputsgl"  value="${ fdfamily_Form.fdConnect}"/>
												<div
													id="_xform_fdfamily_Form[${vstatus.index}].fdConnect"
													_xform_type="text" style="display:none;">
													<xform:text
														property="fdfamily_Form[${vstatus.index}].fdConnect"
														showStatus="edit" 
														subject="${lfn:message('hr-staff:hrStaffPerson.family.connect')}"
														validators=" maxLength(200)" style="width:95%;" />
												</div>
											</td>
											
											<!-- 内容体 end -->
										</tr>
									</c:forEach>
									</tr>
								</table> 
								<input type="hidden" name="fdfamily_Flag" value="1">
								<script>
                                        DocList_Info.push('TABLE_DocList_fdfamily_Form');
                                    </script>
							</td>
						</tr>
					</table>
				</ui:content>
				<!-- 家庭信息 end -->
			 <%--薪酬福利 satrt--%>
	        <ui:content expand="true" title="${ lfn:message('hr-staff:table.hrStaffEmolumentWelfare') }">
                     <table class="tb_normal" width=100%>
                         <tr>
                             <!-- 工资账户名 -->
                             <td width="15%" class="td_normal_title">
                                 ${ lfn:message('hr-staff:hrStaffEmolumentWelfare.fdPayrollName') }
                             </td>
                             <td width="35%">
                             	<input type="hidden" name="hrStaffEmolumentWelfareForm.fdId" value="${hrStaffEntryForm.hrStaffEmolumentWelfareForm.fdId }"/>
                                 <xform:text property="hrStaffEmolumentWelfareForm.fdPayrollName" style="width:95%;" className="inputsgl"/>
                             </td>
                             <!-- 工资银行 -->
                             <td width="15%" class="td_normal_title">
                                 ${ lfn:message('hr-staff:hrStaffEmolumentWelfare.fdPayrollBank') }
                             </td>
                             <td width="35%">
                                 <xform:text property="hrStaffEmolumentWelfareForm.fdPayrollBank"  style="width:98%;" className="inputsgl" />
                             </td>
                         </tr>
                         <tr>
                             <!-- 工资账号 -->
                             <td width="15%" class="td_normal_title">
                                 ${ lfn:message('hr-staff:hrStaffEmolumentWelfare.fdPayrollAccount') }
                             </td>
                             <td width="35%">
                                 <xform:text property="hrStaffEmolumentWelfareForm.fdPayrollAccount" style="width:98%;" className="inputsgl" />
                             </td>
                             <!-- 公积金账户-->
                             <td width="15%" class="td_normal_title">
                                 ${ lfn:message('hr-staff:hrStaffEmolumentWelfare.fdSurplusAccount') }
                             </td>
                             <td width="35%">
                                 <xform:text property="hrStaffEmolumentWelfareForm.fdSurplusAccount" style="width:98%;" className="inputsgl" />
                             </td>
                         </tr>
                         <tr>
                             <!-- 社保号码 -->
                             <td width="15%" class="td_normal_title">
                                 ${ lfn:message('hr-staff:hrStaffEmolumentWelfare.fdSocialSecurityNumber') }
                             </td>
                             <td width="85%" colspan="3">
                                 <xform:text property="hrStaffEmolumentWelfareForm.fdSocialSecurityNumber" style="width:98%;" className="inputsgl" />
                             </td>
                         </tr>
                     </table>
                 </ui:content>
                 <!-- 薪酬福利 end -->
			</ui:tabpage>
		</html:form>
		<%@ include file="/hr/staff/hr_staff_entry/hrStaffEntry_edit_script.jsp"%>
		<c:import url="/hr/staff/hr_staff_entry/hrStaffEntryDetail_edit.jsp" charEncoding="UTF-8"/>
	</template:replace>
</template:include>