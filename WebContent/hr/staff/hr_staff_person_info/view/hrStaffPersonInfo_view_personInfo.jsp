<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
     <div class="lui-personnel-file-staffInfo" id="staffInfo">
      <c:choose>
      	<c:when test="${param.print==null }">
	       <div class="lui-personnel-file-header-title">
	         <div class="lui-personnel-file-header-title-left">
	           <div class="lui-personnel-file-header-title-text"><bean:message key="mobile.hr.staff.view.2" bundle="hr-staff"/></div>
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
				<div class="title"><bean:message bundle="hr-staff" key="table.HrStaffPersonInfo" /></div>
			</div>
		</c:otherwise>
	   </c:choose>
	   <div class="lui-split-line"></div>
       <table>
         <tbody>
             <tr>
				 <td width="160">
					 <bean:message key="hrStaffPersonInfo.fdName" bundle="hr-staff"/>
				 </td>
				 <td width="300">${hrStaffPersonInfoForm.fdName}</td>
             	<td width="160"><bean:message key="hrStaffPersonInfo.fdNameUsedBefore" bundle="hr-staff"/></td><td width="300">${hrStaffPersonInfoForm.fdNameUsedBefore}</td>
             </tr>
             <tr>
             	<td  ><bean:message key="hrStaffPersonInfo.fdIdCard" bundle="hr-staff"/></td><td  >${hrStaffPersonInfoForm.fdIdCard}</td><td  ><bean:message key="hrStaffPersonInfo.fdSex" bundle="hr-staff"/></td>
             	<td  >
             		<c:if test="${not empty hrStaffPersonInfoForm.fdSex}">
             			<c:if test="${hrStaffPersonInfoForm.fdSex eq 'M'}">
             				<bean:message key="hrStaff.overview.report.staffSex.M" bundle="hr-staff"/>
             			</c:if>
             			<c:if test="${hrStaffPersonInfoForm.fdSex eq 'F'}">
             				<bean:message key="hrStaff.overview.report.staffSex.F" bundle="hr-staff"/>
             			</c:if>             			
             		</c:if>
             	</td>
             </tr>
             <tr><td><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdDateOfBirth" /></td><td>${hrStaffPersonInfoForm.fdDateOfBirth}</td><td  ><bean:message key="hrStaffPersonInfo.fdAge" bundle="hr-staff"/></td><td  >${hrStaffPersonInfoForm.fdAge}</td></tr>
             <tr><td><bean:message key="hrStaffPersonInfo.fdNation" bundle="hr-staff"/></td><td  >${hrStaffPersonInfoForm.fdNation}</td><td><bean:message key="hrStaffPersonInfo.fdPoliticalLandscape" bundle="hr-staff"/></td><td  >${hrStaffPersonInfoForm.fdPoliticalLandscape}</td></tr>
             <tr>
             	<td><bean:message key="hrStaffPersonInfo.fdHighestEducation" bundle="hr-staff"/></td>
             	<td  >${hrStaffPersonInfoForm.fdHighestEducation}</td>
<%--              	<td><bean:message key="hrStaffPersonInfo.fdHighestDegree" bundle="hr-staff"/></td> --%>
<%--              	<td  >${hrStaffPersonInfoForm.fdHighestDegree}</td> --%>
     <td>
             <bean:message key="hrStaffPersonInfo.fdWorkTime" bundle="hr-staff"/>
             </td>
             <td  >${hrStaffPersonInfoForm.fdWorkTime}</td>
             	</tr>
             <tr>
             <td>
             <bean:message key="hrStaffPersonInfo.fdMaritalStatus" bundle="hr-staff"/>
             </td>
             <td  >
             ${hrStaffPersonInfoForm.fdMaritalStatus}
             </td>
             <td>
             <bean:message key="hrStaffPersonInfo.fdNativePlace" bundle="hr-staff"/>
             </td>
             <td> 
             <span title="${hrStaffPersonInfoForm.fdNativePlace}">${hrStaffPersonInfoForm.fdNativePlace}</span>
             </td>
             </tr>
             <tr>
             <td><bean:message key="hrStaffPersonInfo.fdPostalAddress" bundle="hr-staff"/></td>
             <td> 
             <span title="${hrStaffPersonInfoForm.fdPostalAddressProvinceName}">
				 ${hrStaffPersonInfoForm.fdPostalAddressProvinceName}
			 </span>
				 <span title="${hrStaffPersonInfoForm.fdPostalAddressCityName}">
					 ${hrStaffPersonInfoForm.fdPostalAddressCityName}
				 </span>
				 <span title="${hrStaffPersonInfoForm.fdPostalAddressAreaName}">
					 ${hrStaffPersonInfoForm.fdPostalAddressAreaName}
				 </span>
				 <span title="${hrStaffPersonInfoForm.fdPostalAddress}">
					 ${hrStaffPersonInfoForm.fdPostalAddress}
				 </span>
             </td>
             </tr>
             <tr>
             	<td>
             		<bean:message key="hrStaffPersonInfo.fdHomeAddress" bundle="hr-staff"/>
             	</td>
             	<td>
              <span title="${hrStaffPersonInfoForm.fdHomeAddressProvinceName}">${hrStaffPersonInfoForm.fdHomeAddressProvinceName}</span><span title="${hrStaffPersonInfoForm.fdHomeAddressCityName}">${hrStaffPersonInfoForm.fdHomeAddressCityName}</span><span title="${hrStaffPersonInfoForm.fdHomeAddressAreaName}">${hrStaffPersonInfoForm.fdHomeAddressAreaName}</span><span title="${hrStaffPersonInfoForm.fdHomeAddress}">${hrStaffPersonInfoForm.fdHomeAddress}</span>
            	</td>
             	
             </tr>
             <tr>
                    <td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdForeignLanguageLevel" />
							</td>
							<td width="35%" class="inputwidth">
								<xform:text property="fdForeignLanguageLevel" style="width:98%;" className="inputsgl" />
							</td>
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdIsRetiredSoldier" />
							</td>
							<td width="35%" class="inputwidth">
						
							${hrStaffPersonInfoForm.fdIsRetiredSoldier}
							</td>
             </tr>
             <tr>
             <td><bean:message key="hrStaffPersonInfo.fdAccountProperties" bundle="hr-staff"/></td>
             	<td>${hrStaffPersonInfoForm.fdAccountProperties}</td>
             <td><bean:message key="hrStaffPersonInfo.fdRegisteredResidence" bundle="hr-staff"/></td><td  ><span title="${hrStaffPersonInfoForm.fdRegisteredResidence}">${hrStaffPersonInfoForm.fdRegisteredResidence}</span></td>
<!--              <tr> -->
<%--              <td  ><bean:message key="hrStaffPersonInfo.fdResidencePoliceStation" bundle="hr-staff"/></td><td  ><span title="${hrStaffPersonInfoForm.fdResidencePoliceStation}">${hrStaffPersonInfoForm.fdResidencePoliceStation}</span></td> --%>
             
   
        
<!--              </tr> -->
            
             <tr>
             <td  >
             <bean:message key="hrStaffPersonInfo.fdWorkingYears" bundle="hr-staff"/>
             </td>
             <td  >
             ${hrStaffPersonInfoForm.fdWorkingYears}
             </td>
             <td>
             <bean:message key="hrStaffPersonInfo.fdUninterruptedWorkTime" bundle="hr-staff"/>
             </td>
             <td  >
             ${hrStaffPersonInfoForm.fdUninterruptedWorkTime}
             </td>
             </tr>
<%-- 			 <c:import url="/sys/property/custom_field/custom_fieldView.jsp" charEncoding="UTF-8" />	 --%>
			 <tr>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="hr-staff" key="hrStaffPersonInfo.att" />
				</td>
				<td colspan="3">
					<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
						<c:param name="formBeanName" value="hrStaffPersonInfoForm" />
						<c:param name="fdKey" value="hrStaffPerson" />
					</c:import>
				</td>
			</tr>
         </tbody>
       </table>
     </div>
   	<c:if test="${param.print==null}">
	<script>
		window.dialogObj = null
		seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
		
			  $('#staffInfo .lui-personnel-file-edit-text').on("click",function(){
				  var url = "/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=editType&fdId=${hrStaffPersonInfoForm.fdId }&key=personInfo";
				  dialog.iframe(url,"${lfn:message('hr-staff:table.HrStaffPersonInfo1')}",function(rtn){
					  window.location.reload();
				  },{
					  height:600,
					  width:1200,
						  buttons:[{
							  name:"${lfn:message('button.ok')}",
							  value:'',
							  fn:function(value,_dialog){
								  window.dialogObj = _dialog
								  $("#dialog_iframe iframe").get(0).contentWindow.submitBtn();
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
		})
	</script>
	</c:if>