<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div style="display: none;">
	<div id="table_of_fdHistory_detail_edit">
		<input type="hidden" name="status"> <input type="hidden"
			name="rowIndex">
		<center>
			<table class="tb_normal">
				<tr>
					<!-- 公司名称 -->
					<td width="15%" class="td_normal_title"><bean:message
							bundle="hr-staff" key="hrStaffHistory.fdName" /></td>
					<td width="32%"><xform:text property="companyName"
							showStatus="edit" validators="maxLength(200)" required="true"
							subject="${lfn:message('hr-staff:hrStaffHistory.fdName')}" />
					</td>
					<!-- 职位 -->
					<td width="15%" class="td_normal_title"><bean:message
							bundle="hr-staff" key="hrStaffHistory.fdPost" /></td>
					<td width="32%"><xform:text property="fdPost" 
							showStatus="edit" validators="maxLength(200)"
							subject="${lfn:message('hr-staff:hrStaffHistory.fdPost')}" />
					</td>
				</tr>
				<tr>
					<!-- 开始日期 -->
					<td width="15%" class="td_normal_title"><bean:message
							bundle="hr-staff" key="hrStaffHistory.fdStartDate" /></td>
					<td width="32%"><xform:datetime property="fdStartDateHistory"  validators="before compareFdStartDateHistory"
							showStatus="edit" dateTimeType="date" required="true" subject="${lfn:message('hr-staff:hrStaffHistory.fdStartDate')}"/></td>
					<!-- 结束日期 -->
					<td width="15%" class="td_normal_title"><bean:message
							bundle="hr-staff" key="hrStaffHistory.fdEndDate" /></td>
					<td width="32%"><xform:datetime property="fdEndDateHistory" validators="compareFdStartDateHistory"
							showStatus="edit" dateTimeType="date"  subject="${lfn:message('hr-staff:hrStaffHistory.fdEndDate')}"/></td>
				</tr>
				<tr>
					<!-- 工作描述 -->
					<td width="11%" class="td_normal_title"><bean:message
							bundle="hr-staff" key="hrStaffHistory.fdDesc" /></td>
					<td width="88%" colspan="5"><xform:textarea property="fdDescHistory"
							style="width:93%" validators="maxLength(1000)" /></td>
				</tr>
				<tr>
					<!-- 离职原因 -->
					<td width="11%" class="td_normal_title"><bean:message
							bundle="hr-staff" key="hrStaffHistory.fdLeaveReason" /></td>
					<td width="88%" colspan="5"><xform:textarea
							property="fdLeaveReason" style="width:93%" validators="maxLength(500)"/></td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: center;"><ui:button
							text="${lfn:message('button.ok')}" order="2"
							onclick="addStaffEntry('TABLE_DocList_fdHistory_Form','table_of_fdHistory_detail_edit');">
						</ui:button> <ui:button text="${lfn:message('button.cancel')}" order="3"
							onclick="closeDetail('TABLE_DocList_fdHistory_Form','table_of_fdHistory_detail_edit');">
						</ui:button></td>
				</tr>
			</table>
		</center>


	</div>
	
	<div id="table_of_fdEducations_detail_edit">
		<input type="hidden" name="status"> <input type="hidden"
			name="rowIndex">
		<center>
			<table class="tb_normal">
				<tr>
					<!-- 学校名称 -->
					<td width="11%" class="td_normal_title"><bean:message
							bundle="hr-staff" key="hrStaffEduExp.fdName" /></td>
					<td width="88%" colspan="5"><xform:text property="expName"
							showStatus="edit" style="width:93%" validators="maxLength(200)" required="true"
							subject="${lfn:message('hr-staff:hrStaffEduExp.fdName')}" />
					</td>
				</tr>
				<tr>
					<!-- 专业名称 -->
					<td width="15%" class="td_normal_title"><bean:message
							bundle="hr-staff" key="hrStaffEduExp.fdMajor" /></td>
					<td width="32%"><xform:text property="fdMajorName"
							showStatus="edit" validators="maxLength(200)" required="true"
							subject="${lfn:message('hr-staff:hrStaffEduExp.fdMajor')}" />
					</td>
					<!-- 学位 -->
					<td width="15%" class="td_normal_title"><bean:message
							bundle="hr-staff" key="hrStaffEduExp.fdAcadeRecord" /></td>
					<td width="32%">
						<xform:select property="fdAcadeRecordId" subject="${lfn:message('hr-staff:hrStaffEduExp.fdAcadeRecord')}" 
							htmlElementProperties="id='fdAcadeRecordId'" required="false" showStatus="edit">
                            <xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" 
                            whereBlock="hrStaffPersonInfoSettingNew.fdType = 'fdHighestDegree'"/>
                        </xform:select>
					</td>
				</tr>
				<tr>
					<!-- 入学日期 -->
					<td width="15%" class="td_normal_title"><bean:message
							bundle="hr-staff" key="hrStaffEduExp.fdEntranceDate" /></td>
					<td width="32%"><xform:datetime property="fdEntranceDate" validators="before compareFdEntranceDate"
							showStatus="edit" dateTimeType="date" required="true" subject="${lfn:message('hr-staff:hrStaffEduExp.fdEntranceDate')}"/></td>
					<!-- 毕业日期 -->
					<td width="15%" class="td_normal_title"><bean:message
							bundle="hr-staff" key="hrStaffEduExp.fdGraduationDate" /></td>
					<td width="32%"><xform:datetime property="fdGraduationDate" validators="before compareFdEntranceDate"
							showStatus="edit" dateTimeType="date" subject="${lfn:message('hr-staff:hrStaffEduExp.fdGraduationDate')}"/></td>
				</tr>
				<tr>
					<!--备注 -->
					<td width="11%" class="td_normal_title"><bean:message
							bundle="hr-staff" key="hrStaffEduExp.fdRemark" /></td>
					<td width="88%" colspan="5"><xform:textarea property="fdRemarkEduExp"
							style="width:93%" validators="maxLength(200)"/></td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: center;"><ui:button
							text="${lfn:message('button.ok')}" order="2"
							onclick="addStaffEntry('TABLE_DocList_fdEducations_Form','table_of_fdEducations_detail_edit');">
						</ui:button> <ui:button text="${lfn:message('button.cancel')}" order="3"
							onclick="closeDetail('TABLE_DocList_fdEducations_Form','table_of_fdEducations_detail_edit');">
						</ui:button></td>
				</tr>
			</table>
		</center>
	</div>
	<div id="table_of_fdCertificate_detail_edit">
		<input type="hidden" name="status"> <input type="hidden"
			name="rowIndex">
		<center>
			<table class="tb_normal">
				<tr>
					<!-- 证书名称 -->
					<td width="15%" class="td_normal_title"><bean:message
							bundle="hr-staff" key="hrStaffCertifi.fdName" /></td>
					<td width="32%"><xform:text property="certifiName"
							showStatus="edit" validators="maxLength(200)" required="true"
							subject="${lfn:message('hr-staff:hrStaffCertifi.fdName')}" />
					</td>
					<!-- 颁发单位 -->
					<td width="15%" class="td_normal_title"><bean:message
							bundle="hr-staff" key="hrStaffCertifi.fdIssuingUnit" /></td>
					<td width="32%"><xform:text property="fdIssuingUnit"
							showStatus="edit" validators="maxLength(200)" required="true"
							subject="${lfn:message('hr-staff:hrStaffCertifi.fdIssuingUnit')}" />
					</td>
				</tr>
				<tr>
					<!-- 颁发日期 -->
					<td width="15%" class="td_normal_title"><bean:message
							bundle="hr-staff" key="hrStaffCertifi.fdIssueDate" /></td>
					<td width="32%"><xform:datetime property="fdIssueDate" validators="before compareFdIssueDate"
							showStatus="edit" dateTimeType="date" required="true"  subject="${lfn:message('hr-staff:hrStaffCertifi.fdIssueDate')}"/></td>
					<!-- 失效日期 -->
					<td width="15%" class="td_normal_title"><bean:message
							bundle="hr-staff" key="hrStaffCertifi.fdInvalidDate" /></td>
					<td width="32%"><xform:datetime property="fdInvalidDate" validators="compareFdIssueDate"
							showStatus="edit" dateTimeType="date" /></td>
				</tr>
				<tr>
					<!--备注 -->
					<td width="11%" class="td_normal_title"><bean:message
							bundle="hr-staff" key="hrStaffCertifi.fdRemark" /></td>
					<td width="88%" colspan="5"><xform:textarea property="fdRemarkCertificate"
							style="width:93%" validators="maxLength(1000)"/></td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: center;"><ui:button
							text="${lfn:message('button.ok')}" order="2"
							onclick="addStaffEntry('TABLE_DocList_fdCertificate_Form','table_of_fdCertificate_detail_edit');">
						</ui:button> <ui:button text="${lfn:message('button.cancel')}" order="3"
							onclick="closeDetail('TABLE_DocList_fdCertificate_Form','table_of_fdCertificate_detail_edit');">
						</ui:button></td>
				</tr>
			</table>
		</center>
	</div>
	<div id="table_of_fdRewardsPunishments_detail_edit">
		<input type="hidden" name="status"> <input type="hidden"
			name="rowIndex">
		<center>
			<table class="tb_normal">
				<tr>
					<!-- 奖惩名称 -->
					<td width="15%" class="td_normal_title"><bean:message
							bundle="hr-staff" key="hrStaffRewPuni.fdName" /></td>
					<td width="32%"><xform:text property="rewPuniName"
							showStatus="edit" validators="maxLength(200)" required="true"
							subject="${lfn:message('hr-staff:hrStaffRewPuni.fdName')}" />
					</td>
					<!-- 奖惩日期 -->
					<td width="15%" class="td_normal_title"><bean:message
							bundle="hr-staff" key="hrStaffRewPuni.fdDate" /></td>
					<td width="32%"><xform:datetime property="fdDate"  validators="before"
							showStatus="edit" dateTimeType="date" /></td>
				</tr>
				<tr>
					<!--备注 -->
					<td width="11%" class="td_normal_title"><bean:message
							bundle="hr-staff" key="hrStaffRewPuni.fdRemark" /></td>
					<td width="88%" colspan="5"><xform:textarea property="fdRemarkRewPuni"
							style="width:93%" validators="maxLength(200)"/></td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: center;"><ui:button
							text="${lfn:message('button.ok')}" order="2"
							onclick="addStaffEntry('TABLE_DocList_fdRewardsPunishments_Form','table_of_fdRewardsPunishments_detail_edit');">
						</ui:button> <ui:button text="${lfn:message('button.cancel')}" order="3"
							onclick="closeDetail('TABLE_DocList_fdRewardsPunishments_Form','table_of_fdRewardsPunishments_detail_edit');">
						</ui:button></td>
				</tr>
			</table>
		</center>
	</div>
	<!-- 家庭信息 -->
	<div id="table_of_fdfamily_detail_edit">
		<input type="hidden" name="status"> <input type="hidden"
			name="rowIndex">
		<center>
		<table class="tb_normal" >
				<tr>
					<!-- 家庭关系-->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPerson.family.related') }
					</td>
					<td colspan="3">
						<xform:text property="fdRelated" required="true" style="width:95%;"/>
					</td>
				</tr>
				<tr>
					<!-- 名字 -->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPerson.family.name') }
					</td>
					<td width="35%">
						<xform:text property="fdFamilyName" required="true" style="width:95%;"/>
					</td>
					<!-- 职业 -->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPerson.family.occupation') }
					</td>
					<td width="35%">
						<xform:text property="fdOccupation" style="width:98%;"/>
					</td>
				</tr>
				<tr>
					<!-- 任职单位 -->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPerson.family.company') }
					</td>
					<td width="35%">
						<xform:text property="fdCompany" style="width:98%;"/>
					</td>
					<!-- 联系信息 -->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPerson.family.connect') }
					</td>
					<td width="35%">
						<xform:text property="fdConnect" style="width:98%;"/>
					</td>
				</tr>
				<tr>
					<!-- 备注-->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonExperience.fdMemo') }
					</td>
					<td colspan="3">
						<xform:textarea property="fdMemo" validators="maxLength(200)" style="width:98%;height:50px;"/>
					</td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: center;"><ui:button
							text="${lfn:message('button.ok')}" order="2"
							onclick="addStaffEntry('TABLE_DocList_fdfamily_Form','table_of_fdfamily_detail_edit');">
						</ui:button> <ui:button text="${lfn:message('button.cancel')}" order="3"
							onclick="closeDetail('TABLE_DocList_fdfamily_Form','table_of_fdfamily_detail_edit');">
						</ui:button></td>
				</tr>
			</table>
		</center>
	</div>
	<script type="text/javascript">  
			 function addStaffEntry(form,element) {
				 if(verification(element)){
					 var methodNew = $('input[name="status"]').val();
					 var rowIndex = $('input[name="rowIndex"]').val();
					 if("add"==methodNew){
						 var newrow = DocList_AddRow(form);
						 rowIndex = $(newrow)[0].rowIndex-1;
						 $(newrow).find("td").attr("onclick","javascript:HR_EditRatifyEntryNew(this,'"+form+"','"+element+"');");
						 $(newrow).find("td:eq(0)").attr("onclick","");
					 }
					 if("table_of_fdHistory_detail_edit"==element){
						 var companyName=$('input[name="companyName"]').val();
						 var fdPost=$('input[name="fdPost"]').val();
						 var fdStartDateHistory=$('input[name="fdStartDateHistory"]').val();
						 var fdEndDateHistory=$('input[name="fdEndDateHistory"]').val();
						 var fdDescHistory=$('textarea[name="fdDescHistory"]').val();
						 var fdLeaveReason=$('textarea[name="fdLeaveReason"]').val();
						 $('input[name="fdHistory_Form['+rowIndex+'].fdName"]').val(companyName);
						 $('input[name="fdHistory_Form['+rowIndex+'].fdNameNew"]').val(companyName);
						 $('input[name="fdHistory_Form['+rowIndex+'].fdPost"]').val(fdPost);
						 $('input[name="fdHistory_Form['+rowIndex+'].fdPostNew"]').val(fdPost);
						 $('input[name="fdHistory_Form['+rowIndex+'].fdStartDate"]').val(fdStartDateHistory);
						 $('input[name="fdHistory_Form['+rowIndex+'].fdStartDateNew"]').val(fdStartDateHistory);
						 $('input[name="fdHistory_Form['+rowIndex+'].fdEndDate"]').val(fdEndDateHistory);
						 $('input[name="fdHistory_Form['+rowIndex+'].fdEndDateNew"]').val(fdEndDateHistory);
						 $('input[name="fdHistory_Form['+rowIndex+'].fdDesc"]').val(fdDescHistory);
						 $('input[name="fdHistory_Form['+rowIndex+'].fdLeaveReason"]').val(fdLeaveReason);
					 }else if("table_of_fdEducations_detail_edit"==element){
						 var expName=$('input[name="expName"]').val();
						 var fdMajorName=$('input[name="fdMajorName"]').val();
						 //var fdAcademic=$('input[name="fdAcademic"]').val();
						 var fdEntranceDate=$('input[name="fdEntranceDate"]').val();
						 var fdGraduationDate=$('input[name="fdGraduationDate"]').val();
						 var fdRemarkEduExp=$('textarea[name="fdRemarkEduExp"]').val();
						 var fdAcadeRecord=$('select[name="fdAcadeRecordId"]').val();
						 var fdAcadeRecordName=$('select[name="fdAcadeRecordId"]').find("option:selected").text();
						 $('select[name="fdEducations_Form['+rowIndex+'].fdAcadeRecordId"]').val(fdAcadeRecord);
						 $('input[name="fdEducations_Form['+rowIndex+'].fdName"]').val(expName);
						 $('input[name="fdEducations_Form['+rowIndex+'].fdNameNew"]').val(expName);
						 $('input[name="fdEducations_Form['+rowIndex+'].fdMajor"]').val(fdMajorName);
						 $('input[name="fdEducations_Form['+rowIndex+'].fdMajorNew"]').val(fdMajorName);
						 $('input[name="fdEducations_Form['+rowIndex+'].fdAcadeRecord"]').val(fdAcadeRecord);
						 $('input[name="fdEducations_Form['+rowIndex+'].fdAcadeRecordName"]').val(fdAcadeRecordName);
						 //$('input[name="fdEducations_Form['+rowIndex+'].fdAcademic"]').val(fdAcademic);
						 $('input[name="fdEducations_Form['+rowIndex+'].fdAcademicNew"]').val(fdAcadeRecordName);
						 $('input[name="fdEducations_Form['+rowIndex+'].fdEntranceDate"]').val(fdEntranceDate);
						 $('input[name="fdEducations_Form['+rowIndex+'].fdEntranceDateNew"]').val(fdEntranceDate);
						 $('input[name="fdEducations_Form['+rowIndex+'].fdGraduationDate"]').val(fdGraduationDate);
						 $('input[name="fdEducations_Form['+rowIndex+'].fdGraduationDateNew"]').val(fdGraduationDate);
						 $('input[name="fdEducations_Form['+rowIndex+'].fdRemark"]').val(fdRemarkEduExp);
					 }else if("table_of_fdTrains_detail_edit"==element){
						 var trainName=$('input[name="trainName"]').val();
						 var fdTrainCompany=$('input[name="fdTrainCompany"]').val();
						 var fdStartDateTrain=$('input[name="fdStartDateTrain"]').val();
						 var fdEndDateTrain=$('input[name="fdEndDateTrain"]').val();
						 var fdRemarkTrain=$('textarea[name="fdRemarkTrain"]').val();
						 $('input[name="fdTrains_Form['+rowIndex+'].fdName"]').val(trainName);
						 $('input[name="fdTrains_Form['+rowIndex+'].fdNameNew"]').val(trainName);
						 $('input[name="fdTrains_Form['+rowIndex+'].fdTrainCompany"]').val(fdTrainCompany);
						 $('input[name="fdTrains_Form['+rowIndex+'].fdTrainCompanyNew"]').val(fdTrainCompany);
						 $('input[name="fdTrains_Form['+rowIndex+'].fdStartDate"]').val(fdStartDateTrain);
						 $('input[name="fdTrains_Form['+rowIndex+'].fdStartDateNew"]').val(fdStartDateTrain);
						 $('input[name="fdTrains_Form['+rowIndex+'].fdEndDate"]').val(fdEndDateTrain);
						 $('input[name="fdTrains_Form['+rowIndex+'].fdEndDateNew"]').val(fdEndDateTrain);
						 $('input[name="fdTrains_Form['+rowIndex+'].fdRemark"]').val(fdRemarkTrain);
					 }else if("table_of_fdCertificate_detail_edit"==element){
						 var certifiName=$('input[name="certifiName"]').val();
						 var fdIssuingUnit=$('input[name="fdIssuingUnit"]').val();
						 var fdIssueDate=$('input[name="fdIssueDate"]').val();
						 var fdInvalidDate=$('input[name="fdInvalidDate"]').val();
						 var fdRemarkCertificate=$('textarea[name="fdRemarkCertificate"]').val();
						 $('input[name="fdCertificate_Form['+rowIndex+'].fdName"]').val(certifiName);
						 $('input[name="fdCertificate_Form['+rowIndex+'].fdNameNew"]').val(certifiName);
						 $('input[name="fdCertificate_Form['+rowIndex+'].fdIssuingUnit"]').val(fdIssuingUnit);
						 $('input[name="fdCertificate_Form['+rowIndex+'].fdIssuingUnitNew"]').val(fdIssuingUnit);
						 $('input[name="fdCertificate_Form['+rowIndex+'].fdIssueDate"]').val(fdIssueDate);
						 $('input[name="fdCertificate_Form['+rowIndex+'].fdIssueDateNew"]').val(fdIssueDate);
						 $('input[name="fdCertificate_Form['+rowIndex+'].fdInvalidDate"]').val(fdInvalidDate);
						 $('input[name="fdCertificate_Form['+rowIndex+'].fdInvalidDateNew"]').val(fdInvalidDate);
						 $('input[name="fdCertificate_Form['+rowIndex+'].fdRemark"]').val(fdRemarkCertificate);
					 }else if("table_of_fdRewardsPunishments_detail_edit"==element){
						 var rewPuniName=$('input[name="rewPuniName"]').val();
						 var fdDate=$('input[name="fdDate"]').val();
						 var fdRemarkRewPuni=$('textarea[name="fdRemarkRewPuni"]').val();
						 $('input[name="fdRewardsPunishments_Form['+rowIndex+'].fdName"]').val(rewPuniName);
						 $('input[name="fdRewardsPunishments_Form['+rowIndex+'].fdNameNew"]').val(rewPuniName);
						 $('input[name="fdRewardsPunishments_Form['+rowIndex+'].fdDate"]').val(fdDate);
						 $('input[name="fdRewardsPunishments_Form['+rowIndex+'].fdDateNew"]').val(fdDate);
						 $('input[name="fdRewardsPunishments_Form['+rowIndex+'].fdRemark"]').val(fdRemarkRewPuni);
					 }
					 else if("table_of_fdfamily_detail_edit" == element){
						 $('input[name="fdfamily_Form['+rowIndex+'].fdRelatedNew"]').val($('input[name="fdRelated"]').val());
						$('input[name="fdfamily_Form['+rowIndex+'].fdNameNew"]').val($('input[name="fdFamilyName"]').val());
						$('input[name="fdfamily_Form['+rowIndex+'].fdOccupationNew"]').val($('input[name="fdOccupation"]').val());
						$('input[name="fdfamily_Form['+rowIndex+'].fdCompanyNew"]').val($('input[name="fdCompany"]').val());
						$('input[name="fdfamily_Form['+rowIndex+'].fdConnectNew"]').val($('input[name="fdConnect"]').val());
						$('input[name="fdfamily_Form['+rowIndex+'].fdMemoNew"]').val($('textarea[name="fdMemo"]').val());
						 $('input[name="fdfamily_Form['+rowIndex+'].fdRelated"]').val($('input[name="fdRelated"]').val());
							$('input[name="fdfamily_Form['+rowIndex+'].fdName"]').val($('input[name="fdFamilyName"]').val());
							$('input[name="fdfamily_Form['+rowIndex+'].fdOccupation"]').val($('input[name="fdOccupation"]').val());
							$('input[name="fdfamily_Form['+rowIndex+'].fdCompany"]').val($('input[name="fdCompany"]').val());
							$('input[name="fdfamily_Form['+rowIndex+'].fdConnect"]').val($('input[name="fdConnect"]').val());
							$('input[name="fdfamily_Form['+rowIndex+'].fdMemo"]').val($('textarea[name="fdMemo"]').val());
					 }
						closeDetail(form,element);
		     }
		 }
			//自定义校验器
			 function verification(detailsForm){
				//这里表单元素穷举校验
				$("#"+detailsForm+" input").focus();
				$("#"+detailsForm+" select").focus();
				$("#"+detailsForm+" input").focus();
				$("#"+detailsForm+" select").blur();
				$("#"+detailsForm+" textarea").focus();
				$("#"+detailsForm+" textarea").blur();
				
				var len=0;
				$("#"+detailsForm+" .validation-advice").each(function(){
					if($(this).css('display')!='none'){
						len++;
					}
				});
				if(len>0){
					return false;
				}
				return true;
			}
	 	</script>
</div>
