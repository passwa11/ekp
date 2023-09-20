<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
					<!-- 家庭信息 -->
			        <div class="personal_profile_content_card" data-dojo-type="mui/list/JsonStoreList"
			        	data-dojo-mixins="hr/staff/mobile/resource/js/list/HrStaffPersonFamilyListMixin"
			        	data-dojo-props="url:'/hr/staff/hr_staff_person_family/hrStaffPersonFamily.do?method=listData&personInfoId=${hrStaffPersonInfoForm.fdId}',lazy:false">
			        	<div class="ppc_c_title">
			        		<div>
			        			<i class="ppc_c_title-icon"></i>
			        			<span><bean:message bundle="hr-staff" key="hrStaffPerson.family"/></span>
			        		</div>
			        		<kmss:auth requestURL="/hr/staff/hr_staff_person_family/hrStaffPersonFamily.do?method=add">
				        		<div class="ppc_c_add_button" onClick="window.addTableList('familyinfo')">
				        			<i class="ppc_c_add_icon"></i>
				        			<span><bean:message bundle="hr-staff" key="hr.staff.btn.add"/></span>
				        		</div>
			        		</kmss:auth>
			        	</div>
			        </div>
			        <!-- 任职记录 -->
			        <div class="personal_profile_content_card" data-dojo-type="mui/list/JsonStoreList"
			        	data-dojo-mixins="hr/staff/mobile/resource/js/list/HrStaffTrackRecordListMixin"
			        	data-dojo-props="url:'/hr/staff/hr_staff_person_track_record/hrStaffTrackRecord.do?method=listData&personInfoId=${hrStaffPersonInfoForm.fdId}',lazy:false">
			        	<div class="ppc_c_title">
			        		<div>
			        			<i class="ppc_c_title-icon"></i>
			        			<span><bean:message bundle="hr-staff" key="table.hrStaffTrackRecord"/></span>
			        		</div>
			        		<kmss:auth requestURL="/hr/staff/hr_staff_person_track_record/hrStaffTrackRecord.do?method=add">
				        		<div class="ppc_c_add_button" onClick="window.addTableList('trackrecord')">
				        			<i class="ppc_c_add_icon"></i>
				        			<span><bean:message bundle="hr-staff" key="hr.staff.btn.add"/></span>
				        		</div>
			        		</kmss:auth>
			        	</div>
			        </div>
			        <!-- 工作经历 -->
			        <div class="personal_profile_content_card" data-dojo-type="mui/list/JsonStoreList" 
			        	data-dojo-mixins="hr/staff/mobile/resource/js/list/HrStaffExperienceWorkListMixin"
			        	data-dojo-props="url:'/hr/staff/hr_staff_person_experience/work/hrStaffPersonExperienceWork.do?method=listData&personInfoId=${hrStaffPersonInfoForm.fdId}',lazy:false">
			        	<div class="ppc_c_title">
							<div>
							  <i class="ppc_c_title-icon"></i>
							  <span><bean:message bundle="hr-staff" key="table.hrStaffHistory"/></span>
							</div>
							<kmss:auth requestURL="/hr/staff/hr_staff_person_experience/work/hrStaffPersonExperienceWork.do?method=add">
								<div class="ppc_c_add_button" onClick="window.addTableList('work')">
								  <i class="ppc_c_add_icon"></i>
								  <span><bean:message bundle="hr-staff" key="hr.staff.btn.add"/></span>
								</div>
							</kmss:auth>
						</div>
			        </div>
			        <!-- 教育记录 -->
			        <div class="personal_profile_content_card" data-dojo-type="mui/list/JsonStoreList" 
			        	data-dojo-mixins="hr/staff/mobile/resource/js/list/HrStaffExperienceEducationListMixin"
			        	data-dojo-props="url:'/hr/staff/hr_staff_person_experience/education/hrStaffPersonExperienceEducation.do?method=listData&personInfoId=${hrStaffPersonInfoForm.fdId}',lazy:false">
			        	<div class="ppc_c_title">
							<div>
							  <i class="ppc_c_title-icon"></i>
							  <span><bean:message bundle="hr-staff" key="table.hrStaffEduExp"/></span>
							</div>
							<kmss:auth requestURL="/hr/staff/hr_staff_person_experience/education/hrStaffPersonExperienceEducation.do?method=add">
								<div class="ppc_c_add_button" onClick="window.addTableList('education')">
								  <i class="ppc_c_add_icon"></i>
								  <span><bean:message bundle="hr-staff" key="hr.staff.btn.add"/></span>
								</div>
							</kmss:auth>
						</div>
			        </div>
			        <!-- 培训记录 -->
			        <div class="personal_profile_content_card" data-dojo-type="mui/list/JsonStoreList" 
			        	data-dojo-mixins="hr/staff/mobile/resource/js/list/HrStaffExperienceTrainListMixin"
			        	data-dojo-props="url:'/hr/staff/hr_staff_person_experience/training/hrStaffPersonExperienceTraining.do?method=listData&personInfoId=${hrStaffPersonInfoForm.fdId}',lazy:false">
			        	<div class="ppc_c_title">
							<div>
							  <i class="ppc_c_title-icon"></i>
							  <span><bean:message bundle="hr-staff" key="hrStaffPersonExperience.type.training"/></span>
							</div>
							<kmss:auth requestURL="/hr/staff/hr_staff_person_experience/training/hrStaffPersonExperienceTraining.do?method=add">
								<div class="ppc_c_add_button" onClick="window.addTableList('training')">
								  <i class="ppc_c_add_icon"></i>
								  <span><bean:message bundle="hr-staff" key="hr.staff.btn.add"/></span>
								</div>
							</kmss:auth>
						</div>
			        </div>
			        <!-- 资格证书 -->
			        <div class="personal_profile_content_card" data-dojo-type="mui/list/JsonStoreList" 
			        	data-dojo-mixins="hr/staff/mobile/resource/js/list/HrStaffExperienceQualificationListMixin"
			        	data-dojo-props="url:'/hr/staff/hr_staff_person_experience/qualification/hrStaffPersonExperienceQualification.do?method=listData&personInfoId=${hrStaffPersonInfoForm.fdId}',lazy:false">
			        	<div class="ppc_c_title">
							<div>
							  <i class="ppc_c_title-icon"></i>
							  <span><bean:message bundle="hr-staff" key="hrStaffPersonExperience.type.qualification"/></span>
							</div>
							<kmss:auth requestURL="/hr/staff/hr_staff_person_experience/qualification/hrStaffPersonExperienceQualification.do?method=add">
								<div class="ppc_c_add_button" onClick="window.addTableList('qualification')">
								  <i class="ppc_c_add_icon"></i>
								  <span><bean:message bundle="hr-staff" key="hr.staff.btn.add"/></span>
								</div>
							</kmss:auth>
						</div>
			        </div>
			        <!-- 合同信息 -->
			        <div class="personal_profile_content_card" data-dojo-type="mui/list/JsonStoreList" 
			        	data-dojo-mixins="hr/staff/mobile/resource/js/list/HrStaffExperienceContractListMixin"
			        	data-dojo-props="url:'/hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=listData&personInfoId=${hrStaffPersonInfoForm.fdId}',lazy:false">
			        	<div class="ppc_c_title">
							<div>
							  <i class="ppc_c_title-icon"></i>
							  <span><bean:message bundle="hr-staff" key="hrStaffPersonExperience.type.contract"/></span>
							</div>
							<kmss:auth requestURL="/hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=add">
							<div class="ppc_c_add_button" onClick="window.addTableList('contract')">
							  <i class="ppc_c_add_icon"></i>
							  <span><bean:message bundle="hr-staff" key="hr.staff.btn.add"/></span>
							</div>
							</kmss:auth>
						</div>
			        </div>
			        <!-- 奖惩信息 -->
			        <div class="personal_profile_content_card" data-dojo-type="mui/list/JsonStoreList" 
			        	data-dojo-mixins="hr/staff/mobile/resource/js/list/HrStaffExperienceBonusMalusListMixin"
			        	data-dojo-props="url:'/hr/staff/hr_staff_person_experience/bonusMalus/hrStaffPersonExperienceBonusMalus.do?method=listData&personInfoId=${hrStaffPersonInfoForm.fdId}',lazy:false">
			        	<div class="ppc_c_title">
							<div>
							  <i class="ppc_c_title-icon"></i>
							  <span><bean:message bundle="hr-staff" key="hrStaffPersonExperience.type.bonusMalus"/></span>
							</div>
							<kmss:auth requestURL="/hr/staff/hr_staff_person_experience/bonusMalus/hrStaffPersonExperienceBonusMalus.do?method=add">
							<div class="ppc_c_add_button" onClick="window.addTableList('bonusmalus')">
							  <i class="ppc_c_add_icon"></i>
							  <span><bean:message bundle="hr-staff" key="hr.staff.btn.add"/></span>
							</div>
							</kmss:auth>
						</div>
			        </div>