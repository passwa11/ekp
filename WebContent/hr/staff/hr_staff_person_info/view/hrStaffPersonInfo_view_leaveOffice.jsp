<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
     <div class="lui-personnel-file-staffInfo" id="departureInfo">
      	<c:choose>
      	<c:when test="${param.print==null }">
        <div class="lui-personnel-file-header-title">
            <div class="lui-personnel-file-header-title-left">
              <div class="lui-personnel-file-header-title-text"><bean:message key="mobile.hr.staff.view.5" bundle="hr-staff"/></div>
            </div>
            <c:if test="${!readOnly}">
            <div class="lui-personnel-file-edit">
              <span class="lui-personnel-file-edit-icon"></span>
              <span class="lui-personnel-file-edit-text">${lfn:message('button.edit')}</span>
            </div>
            </c:if>
          </div>
        </c:when>
         <c:otherwise>
	 		<div class="tr_label_title">
				<div class="title"><bean:message key="mobile.hr.staff.view.5" bundle="hr-staff"/></div>
			</div>
		</c:otherwise>
	   </c:choose> 
	   	<div class="lui-split-line"></div>
         <table>
             <tbody>
             <tr>
                 <%--申请离职日期--%>
                 <td width="160"><bean:message key="hrStaffPersonInfo.fdLeaveApplyDate" bundle="hr-staff"/></td>
                 <td width="300"><xform:datetime value="${hrStaffPersonInfoForm.fdLeaveApplyDate}"
                                                 property="fdLeaveApplyDate" dateTimeType="date"></xform:datetime></td>
                 <%--实际离职日期--%>
                 <td width="160"><bean:message key="hrStaffPersonInfo.fdLeaveTime" bundle="hr-staff"/></td>
                 <td width="300">${hrStaffPersonInfoForm.fdLeaveTime}</td>
             </tr>
             <tr>
                 <%--薪资结算日期--%>
                 <td><bean:message key="hrStaffPersonInfo.fdLeaveSalaryEndDate" bundle="hr-staff"/></td>
                 <td><xform:datetime property="fdLeaveSalaryEndDate" dateTimeType="date"></xform:datetime></td>
                 <%--离职原因--%>
                 <td><bean:message key="hrStaffPersonInfo.fdLeaveReason" bundle="hr-staff"/></td>
                 <td>${hrStaffPersonInfoForm.fdLeaveReason}</td>
             </tr>
             <tr>
                 <td><bean:message key="hrStaffPersonInfo.fdLeaveRemark" bundle="hr-staff"/></td>
                 <td>${hrStaffPersonInfoForm.fdLeaveRemark}</td>
                 <%-- 	<td>离职去向</td><td>${hrStaffPersonInfoForm.fdNextCompany}</td> --%>
             </tr>
             </tbody>
         </table>
    </div>
   	<script>
	window.dialogObj=null
	seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
		  $('#departureInfo .lui-personnel-file-edit-text').on("click",function(){
			  dialog.iframe("/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=editType&fdId=${hrStaffPersonInfoForm.fdId }&key=leave","${lfn:message('hr-staff:hrStaffPersonInfo.fdResignation')}","",{
				  height:600,
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
	})
	</script>