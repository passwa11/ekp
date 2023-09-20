<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.hr.staff.util.HrStaffPersonUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.edit" sidebar="auto">
	<template:replace name="title">
		<c:choose>
			<c:when test="${ hrStaffPersonInfoForm.method_GET == 'add' }">
				<c:out value="${ lfn:message('hr-staff:hrStaffPersonInfo.create.title') } - ${ lfn:message('hr-staff:module.hr.staff') }"></c:out>
			</c:when>
			<c:otherwise>
				${ hrStaffPersonInfoForm.fdName } - ${ lfn:message('hr-staff:module.hr.staff') }
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
		<link rel="stylesheet" href="../resource/css/common_view.css">
  	  <link rel="stylesheet" href="../resource/css/person_info.css">
  	  <style>
  	  .com_qrcode{
	  	  	display:none !important
	  	  }
  	  	.lui-personnel-file-baseInfo-main-content .inputsgl{
  	  		   border: 1px solid #b4b4b4;
  	  		   height:28px;
  	  		   border-radius:4px;
  	  	}
  	  	.newTable tr{
  	  		height:40px;
  	  		border-spacing:0px 10px;
  	  	}
  	  	.datawidth .inputselectsgl,.lui-custom-Prop .inputselectsgl{
  	  		width:205px!important;
  	  		height:30px!important;
  	  		border-radius:4px;
  	  		border:none;
  	  		background-color:#F7F7F8;
  	  	}
  	  	.inputwidth .inputsgl,.lui-custom-Prop .inputsgl{
  	  		height:28px;
  	  		width:200px!important;
  	  		background-color:#F7F7F8;
  	  		border:none;
  	  		border-radius:4px;
  	  	}
  	  	.hr_select{
  	  		width: 200px;
  	  		height:30px;
  	  		border-radius:4px;
  	  		background-color:#F7F7F8;
  	  		border:none!important;
  	  	}
  	  	.datawidth input,.lui-custom-Prop .inputselectsgl input{
  	  		background-color:#F7F7F8!important;
  	  	}
  	  	.lui-personnel-file-edit-text{

  	  	}
  	  	.inputselectsgl{
  	  		width:200px!important;
  	  	}
		.newTable textarea{
			width:200px;
			border:none;
			margin:4px 0;
			background-color:#f7f7f7;
		}
		.lui_form_path_frame{
			max-width:1200px!important;

		}
		.lui_form_body{
		background-color:white;
		}
		.fdLeaveTimeTd{
			display: none;
		}

  	  </style>
	</template:replace>
	<template:replace name="content">
		<html:form action="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do" >
			<html:hidden property="fdId" value="${hrStaffPersonInfoForm.fdId}"/>
				<table class="staffInfo newTable" >
					<tr>
						<!-- 状态 -->
							<!-- 人员类别 -->
								<td width="15%" class="td_normal_title">
									<bean:message bundle="hr-staff" key="hrStaff.staffCategory" />
								</td>
								<td width="45%" class="inputwidth">
								<xform:select property="fdStaffType" showStatus="edit" onValueChange="staffTypeChange" required="true">
									<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdName,fdName" whereBlock="fdType='fdStaffType'"></xform:beanDataSource>
								</xform:select >
							</td>
							<td width="15%" class="td_normal_title">
									<bean:message bundle="hr-staff" key="hrStaff.staffStatus" />
								</td>
							<td width="45%" class="inputwidth">
								<xform:select  showStatus="edit" property="fdStatus">
								<xform:enumsDataSource enumsType="hrStaffPersonInfo_fdStatus"></xform:enumsDataSource>
							</xform:select >
							</td>
								<!-- 入职日期-->
						
						</tr>
						<tr>
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdEntryTime" />
						</td>
						<td width="35%" class="datawidth">
							<xform:datetime showStatus="edit" property="fdEntryTime" dateTimeType="date" required="true" validators="compareTime(entry)"></xform:datetime>
						</td>
						<td width="15%" class="td_normal_title">
									<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdProposedEmploymentConfirmationDate" />
								</td>
								<td width="35%" class="inputwidth">
									<xform:datetime showStatus="edit" property="fdProposedEmploymentConfirmationDate" dateTimeType="date"  validators="compareTime(entry)"></xform:datetime>
								</td>
						<!-- 转正日期 -->
						
					</tr>
					 <tr>
					 <td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdPositiveTime" />
						</td>
						<td width="35%" class="datawidth">
							<xform:datetime showStatus="edit" property="fdPositiveTime" dateTimeType="date" validators="compareTime(entry)" required="true"></xform:datetime>
						</td>
						<!-- 试用期限 -->
						<td width="15%" class="td_normal_title" >
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdProbationPeriod" />
						</td>
						<td width="35%" class="inputwidth">
							<xform:text  showStatus="edit" property="fdProbationPeriod" style="width:95%;" validators="digits min(0)" className="inputsgl" required="true"/>
						</td>
						<!-- 试用到期时间 -->
						
					</tr>
								 <tr>
								 <td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdTrialExpirationTime" />
						</td>
						<td width="35%" class="datawidth">
							<xform:datetime showStatus="edit" property="fdTrialExpirationTime" dateTimeType="date" validators="compareTime(entry)"></xform:datetime>
						</td>
		                   <td width="15%" class="td_normal_title">
									<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdResignationDate" />
								</td>
								<td width="35%" class="datawidth">
									<xform:datetime showStatus="edit" property="fdResignationDate" dateTimeType="date" validators="compareTime(resignation)"></xform:datetime>
								</td>
								
		                   </tr>
		                     <tr>
		                      <td width="15%" class="td_normal_title">
									<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdResignationType" />
								</td>
								<td width="45%" class="inputwidth">
							
<%-- 							<xform:select property="fdResignationType" showStatus="edit"> --%>
<%-- 							   <xform:enumsDataSource enumsType="hr_staff_person_info_fdResignationType"></xform:enumsDataSource> --%>
<%-- 							</xform:select > --%>
							<xform:select property="fdResignationType" showStatus="edit" >
									<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdName" whereBlock="fdType='fdResignationType'"></xform:beanDataSource>
								</xform:select >
							</td>
		                    <td class="td_normal_title"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdReasonForResignation" /></td>
			                  		<td colspan="3">
								<xform:xselect property="fdReasonForResignation" showStatus="edit" >
								<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdName" whereBlock="fdType='fdLeaveReason'" orderBy="fdOrder"></xform:beanDataSource>
							</xform:xselect>			                  	
								</td>
								
		                   </tr>
					<tr>
					
		                   	<td width="15%" class="td_normal_title">
									<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdCostAttribution" />
								</td>
								<td width="45%" class="inputwidth">
										<xform:text showStatus="edit" property="fdCostAttribution" required="true"></xform:text>
		                    		</td>
					</tr>
					 <tr>
		                   		
		                   		<td width="15%" class="td_normal_title">
		                   			<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdIsOAUser"/>
		                   		</td>
		                   		<td width="35%" class="inputwidth">
		                   		
							<xform:select property="fdIsOAUser" showStatus="edit" required="true">
							   <xform:enumsDataSource enumsType="hr_staff_person_info_fdYesNo" ></xform:enumsDataSource>
							</xform:select >
		</td>
								<!-- 入职日期-->
								<td width="15%" class="td_normal_title">
									<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdOAAccount" />
								</td>
								<td width="35%" class="inputwidth">
									<xform:text showStatus="edit" property="fdOAAccount" required="true"></xform:text>
		                    		</td>
		                   	</tr>
			
					
					<tr>
						<!-- 实际离职日期 -->
						<td width="15%" class="td_normal_title fdLeaveTimeTd">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLeaveTime" />
						</td>
						<td width="35%" class="datawidth fdLeaveTimeTd">
							<xform:datetime showStatus="edit" property="fdLeaveTime" dateTimeType="date"></xform:datetime>
							<span style="display: none;" class="txtstrong" id="isRequiredFlag">*</span>
						</td>
						<td></td>
						<td></td>
					</tr>
					<%-- 引入动态属性 --%>
					<tr style="display:none">
						<td colspan="4">
							<table>
								<c:import url="/hr/staff/hr_staff_person_info/edit/custom_fieldEdit.jsp" charEncoding="UTF-8" />
							</table>
						</td>
					</tr>

				</table>
				<input id="fdAccountFlag" name="fdAccountFlag" type="hidden" value="${hrStaffPersonInfoForm.fdAccountFlag}"/>
		</html:form>
		<%@ include file="/hr/staff/hr_staff_person_info/hrStaffPersonInfo_edit_script.jsp"%>
		<script>
			seajs.use(['lui/jquery', 'lui/dialog'],function($, dialog){
				window.submitBtn = function (){
					 //Com_Submit(document.hrStaffPersonInfoForm, 'update');
					 var filed = decodeURI($(document.hrStaffPersonInfoForm).serialize());
					 var dataArr = filed.split("&");
					 var data ={}
					 $.each(dataArr,function(index,item){
						 var itemData = item.split("=")
						 data[itemData[0]]= itemData.length>1?decodeURIComponent(itemData[1]):""
					 })
					 data['type'] = "status"
					 if(_validator.validate()){
		 				 $.ajax({
							 url:"<%=request.getContextPath()%>/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=updatePersonInfo&fdId=${hrStaffPersonInfoForm.fdId}",
							 method:'post',
							 data:data,
							 success:function(res){
								 if(res.status){
									 window.parent.dialogObj.hide()
									 window.parent.location.reload();
								 }
							 }
						 })
					 }
				};

				window.submit = function(){
					if(_validator.validate()){
			  			var newStatus = $("select[name='fdStatus']").val();
			  			var oldStatus = "${hrStaffPersonInfoForm.fdStatus}";
			  			if((oldStatus == 'dismissal' || oldStatus == 'leave' || oldStatus == 'retire')
			  					&& newStatus != 'dismissal' && newStatus != 'leave' && newStatus != 'retire'){
			  				onPostStatus();
			  			}else if((oldStatus != 'dismissal' || oldStatus != 'leave' || oldStatus != 'retire') &&
			  					(newStatus == 'dismissal' || newStatus == 'leave' || newStatus == 'retire')){
			  				leaveStatus();
			  			}else{
			  				submitBtn();
			  			}
					}
				}

				window.leaveStatus = function(){
					var fdLoginName = "${hrStaffPersonInfoForm.fdLoginName}";
					if(null == fdLoginName || fdLoginName == ""){
			  			dialog.confirm("确认是否做离职操作？", function(ok) {
			  				if(ok == true) {
			  					submitBtn();
			  				}
			  			});
		  			}else{
		  				var button = [ {
							name : "注销账号",
							value : true,
							focus : true,
							fn : function(value, dialog) {
								dialog.hide(value);
							}
						}, {
							name : "不注销账号",
							value : false,
							styleClass : 'lui_toolbar_btn_gray',
							fn : function(value, dialog) {
								dialog.hide(value);
							}
						}];
		  				dialog.confirm("是否注销用户系统账号？", function(ok) {
			  				if(ok == true) {
			  					$("#fdAccountFlag").val("1");
			  					submitBtn();
			  				}else if(ok == false){
			  					$("#fdAccountFlag").val("0");
			  					submitBtn();
			  				}
			  			}, null , button);
		  			}
				}

				window.onPostStatus = function(){
					var fdLoginName = "${hrStaffPersonInfoForm.fdLoginName}";
					if(null == fdLoginName || fdLoginName == ""){
			  			dialog.confirm("确认是否将员工置为在职状态？", function(ok) {
			  				if(ok == true) {
			  					submitBtn();
			  				}
			  			});
		  			}else{
		  				var button = [ {
							name : "恢复账号",
							value : true,
							focus : true,
							fn : function(value, dialog) {
								dialog.hide(value);
							}
						}, {
							name : "不恢复账号",
							value : false,
							styleClass : 'lui_toolbar_btn_gray',
							fn : function(value, dialog) {
								dialog.hide(value);
							}
						}];
		  				dialog.confirm("是否恢复用户系统账号？", function(ok) {
			  				if(ok == true) {
			  					$("#fdAccountFlag").val("2");
			  					submitBtn();
			  				}else if(ok== false){
			  					$("#fdAccountFlag").val("0");
			  					submitBtn();
			  				}

			  			}, null , button);
		  			}
				}

			});
			window.changeStatus = function(value,obj){
				var fdLeaveTimeTd = $('.fdLeaveTimeTd');
				if(value == 'dismissal' || value == 'leave' || value == 'retire'){
					fdLeaveTimeTd.css('display','table-cell');
					$("input[name='fdLeaveTime']").attr("validate","required");
					$("input[data-propertyname='fdLeaveTime']").attr("validate","required");
					$('#isRequiredFlag').show();
				}else{
					fdLeaveTimeTd.css('display','none');
					$("input[name='fdLeaveTime']").attr("validate","");
					$("input[data-propertyname='fdLeaveTime']").attr("validate","");
					$('#isRequiredFlag').hide();
				}
			};
			$(function(){
				changeStatus('${hrStaffPersonInfoForm.fdStatus}');
			})

		</script>
	</template:replace>
</template:include>