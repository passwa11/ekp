<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

      <div class="lui-personnel-file-staffInfo" id="dutyInfo">
      	<c:choose>
      	<c:when test="${param.print==null }">
           <div class="lui-personnel-file-header-title">
              <div class="lui-personnel-file-header-title-left">
                <div class="lui-personnel-file-header-title-text"><bean:message key="mobile.hr.staff.view.4" bundle="hr-staff"/></div>
              </div>
              <kmss:authShow roles="ROLE_HRSTAFF_EDIT">
				  <c:if test="${!readOnly}">
	              <div class="lui-personnel-file-edit">
	                <span class="lui-personnel-file-edit-icon"></span>
	                <span class="lui-personnel-file-edit-text">${lfn:message('button.edit')}</span>
	              </div>
				  </c:if>
              </kmss:authShow>
            </div>
        </c:when>
        <c:otherwise>
	 		<div class="tr_label_title">
				<div class="title"><bean:message key="mobile.hr.staff.view.4" bundle="hr-staff"/></div>
			</div>
		</c:otherwise>
	   </c:choose>
	   <div class="lui-split-line"></div>
            <table>
                <tbody>
                    <tr>
                    	<td width="160"><bean:message key="hrStaffPersonInfo.fdStaffNo" bundle="hr-staff"/></td><td width="300">${hrStaffPersonInfoForm.fdStaffNo}</td>
                    	<td width="15%"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdAffiliatedCompany" /></td>
		                    		<td width="45%" class="inputwidth">
		                    		${hrStaffPersonInfoForm.fdAffiliatedCompany}
		                    		</td>
                    	
                    </tr>
                    <tr>
                    	<td><bean:message key="hrStaffPersonInfo.fdStaffingLevel" bundle="hr-staff"/></td><td>${hrStaffPersonInfoForm.fdStaffingLevelName}</td>
                   
                    	
		                    			<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdCategory" /></td>
		                    		<td width="45" class="inputwidth">
		                    		${hrStaffPersonInfoForm.fdCategory}
		                    		</td>
                    </tr>
                    <tr>
                    <td><bean:message key="hrStaffPersonInfo.fdOrgPosts" bundle="hr-staff"/></td>
                    	<td>
							${hrStaffPersonInfoForm.fdOrgPostNames}
						</td>
                    <td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdOrgRank" /></td>
							<td width="45" class="inputwidth">
		                    		${hrStaffPersonInfoForm.fdOrgRankName}
							</td>
<!-- 								<td width="45" class="inputwidth"> -->
<%-- 								<xform:select property="fdOrgRankId" showStatus="readOnly" onValueChange="orgRankChange" required="true"> --%>
<%-- 									<xform:beanDataSource serviceBean="hrOrganizationRankService" selectBlock="fdId,fdName"></xform:beanDataSource> --%>
<%-- 								</xform:select > --%>
<!-- 								</td> -->
                    </tr>
                    <tr>
                    	<td><bean:message key="hrStaffPersonInfo.fdOrgParent" bundle="hr-staff"/></td>
		                    		<td width="45" class="inputwidth">
		           															<xform:address  orgType="ORG_TYPE_PERSON"  propertyName="fdOrgParentName" propertyId="fdOrgParentId" showStatus="readOnly"></xform:address>
         		</td>
							<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdDepartmentHead" /></td>
		                    	<td width="45" class="inputwidth">
		                   		                    			<xform:address  orgType="ORG_TYPE_PERSON"  propertyName="fdDepartmentHeadName" propertyId="fdDepartmentHeadId" showStatus="readOnly"></xform:address>
									
</td>
						</tr>
                    <tr>
                    <td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdPrincipalIdentification" /></td>
		                    		<td width="45" class="inputwidth">
		                    		${hrStaffPersonInfoForm.fdPrincipalIdentification}
		                    		</td>
						<!-- 汇报上级 -->
                    	<td><bean:message key="hrStaffPersonInfo.fdReportLeader" bundle="hr-staff"/></td>
                    	<td>
                    	<%-- 	<c:choose>
								<c:when test="${hrToEkpEnable == true }">
									${hrStaffPersonInfoForm.fdHrReportLeaderName }
								</c:when>
								<c:otherwise>
									${hrStaffPersonInfoForm.fdReportLeaderName }
								</c:otherwise>
							</c:choose>--%>      
 						${hrStaffPersonInfoForm.fdReportLeaderName }              	
 					</td>
                    </tr>
                    
		                    <tr>
		                    
		                    	<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdDirectSuperiorJobNumber" /></td>
		                    		<td width="45" class="inputwidth">
 						${hrStaffPersonInfoForm.fdDirectSuperiorJobNumber } 
									</td>
		                    <td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdFirstLevelDepartment" /></td>
		                    		<td width="45" class="inputwidth">											
		                    						<xform:address  orgType="ORG_TYPE_PERSON"  propertyName="fdFirstLevelDepartmentName" propertyId="fdFirstLevelDepartmentId" showStatus="readOnly"></xform:address>
									</td>
		                    </tr>
		                    <tr>
		                    
		                    		<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdHeadOfFirstLevelDepartment" /></td>
		                    		<td width="45" class="inputwidth">
		                    				                    			<xform:address  orgType="ORG_TYPE_PERSON"  propertyName="fdHeadOfFirstLevelDepartmentName" propertyId="fdHeadOfFirstLevelDepartmentId" showStatus="readOnly"></xform:address>
									
</td>
		                    		<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdSecondLevelDepartment" /></td>
		                    		<td width="45" class="inputwidth">
		                    															<xform:address  orgType="ORG_TYPE_PERSON"  propertyName="fdSecondLevelDepartmentName" propertyId="fdSecondLevelDepartmentId" showStatus="readOnly"></xform:address>
</td>
		                    </tr>
                    <tr>
                    
							
							<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdThirdLevelDepartment" /></td>
		                    		<td width="45" class="inputwidth">
		           															<xform:address  orgType="ORG_TYPE_PERSON"  propertyName="fdThirdLevelDepartmentName" propertyId="fdThirdLevelDepartmentId" showStatus="readOnly"></xform:address>
         		</td>
		                    	
                    </tr>
                    <tr>
							<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdFixedShift" /></td>
		                    		<td width="45" class="inputwidth">
		                    		${hrStaffPersonInfoForm.fdFixedShift}
		                    		</td>
							<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdPlaceOfInsurancePayment" /></td>
		                    		<td width="45" class="inputwidth">
		                    		${hrStaffPersonInfoForm.fdPlaceOfInsurancePayment}
		                    		</td>
							</tr>
                   <tr>
		                  
		                    	<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLoginName" /></td>
		                    		<td width="45" class="inputwidth">
		                    		${hrStaffPersonInfoForm.fdLoginName}
									</td>
		                    		</tr>
		                     <tr>
							<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdSocialSecurityParticipatingCompany" /></td>
		                    		<td width="45" class="inputwidth">
		                    		${hrStaffPersonInfoForm.fdSocialSecurityParticipatingCompany}
									</td>
							<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdProvidentFundInsuranceCompany" /></td>
		                    		<td width="45" class="inputwidth">
		                    		${hrStaffPersonInfoForm.fdProvidentFundInsuranceCompany}
									</td>
							</tr>	
                       <tr>
							<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdIsAttendance" /></td>
		                    		<td width="45" class="inputwidth" >
		                    		${hrStaffPersonInfoForm.fdIsAttendance}
						  		</td>
							<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdTimeCardNo" /></td>
		                    		<td width="45" class="inputwidth">
		                    		${hrStaffPersonInfoForm.fdTimeCardNo}
									</td>
							</tr>
<%--                    <c:if test="${hrToEkpEnable == true }"> --%>
<!-- 						<tr> -->
<%-- 							<td><bean:message key="hrOrganizationGrade.fdName" bundle="hr-organization"/></td> --%>
<!-- 							<td> -->
<%-- 								<c:if test="${not empty rankList}"> --%>
<%-- 									${ranGrade eq null?"":ranGrade } --%>
<%-- 								</c:if> --%>
<!-- 							</td> -->
<%-- 							<td><bean:message key="hrOrganizationRank.fdName" bundle="hr-organization"/></td> --%>
<!-- 							<td> -->
<%-- 								<c:if test="${not empty rankList}"> --%>
<%-- 									${hrStaffPersonInfoForm.fdOrgRankName } --%>
<%-- 								</c:if> --%>
<!-- 							</td> -->
<!-- 						</tr> -->
<%-- 					</c:if> --%>
					<tr>
						<td><bean:message key="hrOrganizationElement.fdIsBusiness" bundle="hr-organization"/></td>
						<td>${hrStaffPersonInfoForm.fdIsBusiness eq 'true'?'是':'否'}</td>
						<td><bean:message key="hrStaffPersonInfo.fdCanLogin" bundle="hr-staff"/></td>
						<td>${hrStaffPersonInfoForm.fdCanLogin eq 'true'?'是':'否'}</td>
					</tr>
                </tbody>
            </table>
      </div>
<c:if test="${param.print==null}">
	<script>
	window.dialogObj = null
	seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
		  $('#dutyInfo .lui-personnel-file-edit-text').on("click",function(){
			  dialog.iframe("/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=editType&fdId=${hrStaffPersonInfoForm.fdId }&key=onPost","${ lfn:message('hr-staff:hrStaffPersonInfo.jobInfo') }","",{
				  height:980,
				  width:1200,
					  buttons:[{
						  name:"${lfn:message('button.ok')}",
						  value:'',
						  fn:function(value,_dialog){
							  window.dialogObj = _dialog
							  $("#dialog_iframe iframe").get(0).contentWindow.submitBtn()
						  }
					  },
					  {
						  name:"${lfn:message('button.cancel')}",
						  value:'',
						  fn:function(value,_dialog){
							  _dialog.hide();
						  }
					  }
					  ]
			  
			  })
		  })
		  function OrgParentChange(value){
				if(value){
					var url = "${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=findOrgParents&fdDeptId="+value[0];
					$.ajax({
						url : url,
						type : 'POST',
						dataType : 'json',
						success: function(data) {
							var result = data.result;
							console.log(result);
							$("input[name='fdFirstLevelDepartmentName']").val('');
							$("input[name='fdFirstLevelDepartmentId']").val('');

							$("input[name='fdSecondLevelDepartmentName']").val('');
							$("input[name='fdSecondLevelDepartmentId']").val('');
							$("input[name='fdThirdLevelDepartmentName']").val('');
							$("input[name='fdThirdLevelDepartmentId']").val('');
							$("input[name='fdDepartmentHead']").val('');
							$("input[name='fdDepartmentHeadId']").val('');
							$("input[name='fdHeadOfFirstLevelDepartmentId']").val('');
							$("input[name='fdHeadOfFirstLevelDepartment']").val('');
							var arr = Object.keys(result[0]);
							var len = arr.length;
							for(var i=len-1;i>=0;i--){
							if(i==len-1 && result[0]['department'+i]){
								$("input[name='fdFirstLevelDepartmentName']").val(result[0]['department'+i].name);
								$("input[name='fdFirstLevelDepartmentId']").val(result[0]['department'+i].id);
							}
							if(i==len-2 && result[0]['department'+i]){
								$("input[name='fdSecondLevelDepartmentName']").val(result[0]['department'+i].name);
								$("input[name='fdSecondLevelDepartmentId']").val(result[0]['department'+i].id);
							}
							if(i==len-3 && result[0]['department'+i]){
								$("input[name='fdThirdLevelDepartmentName']").val(result[0]['department'+i].name);
								$("input[name='fdThirdLevelDepartmentId']").val(result[0]['department'+i].id);
							}
							}
							$("input[name='fdDepartmentHeadName']").val(result[1]['DepartmentHead'].name);
							$("input[name='fdDepartmentHeadId']").val(result[1]['DepartmentHead'].id);
							$("input[name='fdHeadOfFirstLevelDepartmentName']").val(result[1]['HeadOfFirstLevelDepartment'].name);
							$("input[name='fdHeadOfFirstLevelDepartmentId']").val(result[1]['HeadOfFirstLevelDepartment'].id);
						}
					});
				}

				}
		  var arr00 = [$("input[name='fdOrgParentId']").val()];
			OrgParentChange(arr00);
		  window.openAccount = function(){
			  dialog.iframe("/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=editType&fdId=${hrStaffPersonInfoForm.fdId }&key=onPost","在职信息","",{
				  height:800,
				  width:900,
					  buttons:[{
						  name:"${lfn:message('button.ok')}",
						  value:'',
						  fn:function(value,_dialog){
							  window.dialogObj = _dialog
							  $("#dialog_iframe iframe").get(0).contentWindow.submitBtn()
						  }
					  },
					  {
						  name:"${lfn:message('button.cancel')}",
						  value:'',
						  fn:function(value,_dialog){
							  _dialog.hide();
						  }
					  }
					  ]
			  })
		  }
	})
	</script>
</c:if>