<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
     <div class="lui-personnel-file-staffInfo" id="staffStatus">
      <c:choose>
      	<c:when test="${param.print==null }">
          <div class="lui-personnel-file-header-title">
             <div class="lui-personnel-file-header-title-left">
               <div class="lui-personnel-file-header-title-text"><bean:message key="hrStaffPersonInfo.fdStatus" bundle="hr-staff"/></div>
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
				<div class="title"><bean:message key="hrStaffPersonInfo.fdStatus" bundle="hr-staff"/></div>
			</div>
		</c:otherwise>
	   </c:choose>
	   <div class="lui-split-line"></div>
           <table>
               <tbody>
                   <tr>
	                   	<td width="160"><bean:message key="hr.staff.tree.category" bundle="hr-staff"/></td>
	                   	<td width="300">
	                   		${hrStaffPersonInfoForm.fdStaffType}
	                   	</td>
	                   	<td width="15%" class="td_normal_title">
									<bean:message bundle="hr-staff" key="hrStaff.staffStatus" />
								</td>
<!-- 								<td width="45%" class="inputwidth"> -->
<%-- 								<xform:select  showStatus="edit" property="fdStatus"> --%>
<%-- 								<xform:enumsDataSource enumsType="hrStaffPersonInfo_fdStatus"></xform:enumsDataSource> --%>
<%-- 							</xform:select > --%>
<!-- 							</td> -->
<c:if test="${hrStaffPersonInfoForm.fdStatus eq 'trial'}">
			 
<td>试用人员</td>
		</c:if>
		<c:if test="${hrStaffPersonInfoForm.fdStatus eq 'onpost'}">
			 
<td>在职人员</td>
		</c:if>
		<c:if test="${hrStaffPersonInfoForm.fdStatus eq 'official'}">
			 
<td>正式人员</td>
		</c:if>
                <c:if test="${hrStaffPersonInfoForm.fdStatus eq 'rehireAfterRetirement'}">
			 
<td>返聘人员</td>
		</c:if>
		<c:if test="${hrStaffPersonInfoForm.fdStatus eq 'leave'}">
			 
<td>离职人员</td>
		</c:if>
		<c:if test="${hrStaffPersonInfoForm.fdStatus eq 'blacklist'}">
			 
<td>黑名单</td>
		</c:if>  
                   </tr>
                   <tr>
                    <td><bean:message key="hrStaffPersonInfo.fdEntryTime" bundle="hr-staff"/></td>
                   <td>${hrStaffPersonInfoForm.fdEntryTime}</td>
	                 <td width="15%" class="td_normal_title">
									<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdProposedEmploymentConfirmationDate" />
								</td>
								<td width="35%" class="inputwidth">
								
	                   		${hrStaffPersonInfoForm.fdProposedEmploymentConfirmationDate}</td>
                 
                     </tr>
                   <tr> 
                   <td><bean:message key="hrStaffPersonInfo.fdPositiveTime" bundle="hr-staff"/></td><td>${hrStaffPersonInfoForm.fdPositiveTime}</td>
                   		<td><bean:message key="hrStaffPersonInfo.fdTrialOperationPeriod" bundle="hr-staff"/></td>
                   		<td>${hrStaffPersonInfoForm.fdProbationPeriod}</td>
                   		
                   </tr>
                   <tr>
                   <td><bean:message key="hrStaffPersonInfo.fdTrialExpirationTime" bundle="hr-staff"/></td>
                   		<td>${hrStaffPersonInfoForm.fdTrialExpirationTime}</td>
                    <td width="15%" class="td_normal_title">
									<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdResignationDate" />
								</td>
								<td width="35%" class="datawidth">
             ${hrStaffPersonInfoForm.fdResignationDate}
								</td>
								 
		                   </tr>
		                    <tr>
		                    <td width="15%" class="td_normal_title">
									<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdResignationType" />
								</td>
								<td width="45%" class="inputwidth">
							
             ${hrStaffPersonInfoForm.fdResignationType}
							
							</td>
		                   <td class="td_normal_title"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdReasonForResignation" /></td>
			                  		<td colspan="3">
             ${hrStaffPersonInfoForm.fdReasonForResignation}
			                  		</td>
								
		                   </tr>
                   <tr>
		                   	<td width="15%" class="td_normal_title">
									<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdCostAttribution" />
								</td>
								<td width="45%" class="inputwidth">
             ${hrStaffPersonInfoForm.fdCostAttribution}
		                    		</td>
                   </tr>
		                    <tr>
		                   		
		                   		<td width="15%" class="td_normal_title">
		                   			<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdIsOAUser"/>
		                   		</td>
		                   		<td width="35%" class="inputwidth">
		                   		
             ${hrStaffPersonInfoForm.fdIsOAUser}
		</td>
								<!-- 入职日期-->
								<td width="15%" class="td_normal_title">
									<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdOAAccount" />
								</td>
								<td width="35%" class="inputwidth">
             ${hrStaffPersonInfoForm.fdOAAccount}
		                    		</td>
		                   	</tr>
                 
               </tbody>
           </table>
     </div>
	<script>
	window.dialogObj=null
	seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
		  $('#staffStatus .lui-personnel-file-edit-text').on("click",function(){
			  dialog.iframe("/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=editType&fdId=${hrStaffPersonInfoForm.fdId }&key=staffStatus","${lfn:message('hr-staff:hrStaffPersonInfo.fdStatus')}","",{
				  height:900,
				  width:1200,
					  buttons:[{
						  name:"${lfn:message('button.ok')}",
						  value:'',
						  fn:function(value,_dialog){
							  window.dialogObj = _dialog
							  $("#dialog_iframe iframe").get(0).contentWindow.submit();
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