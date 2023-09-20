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
  	  </style>
	</template:replace>	
	<template:replace name="content">
		<html:form action="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do" >
			<html:hidden property="fdId" value="${hrStaffPersonInfoForm.fdId}"/>
				<table class="staffInfo newTable" style="margin: 20px 0" width=98%>
					<tr>
						<td width="15%" class="td_normal_title">
						<!--  离职申请日期-->
						<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLeaveApplyDate" />
						</td>
						<td width="35%" class="datawidth">
							<xform:datetime showStatus="edit" property="fdLeaveApplyDate"></xform:datetime>
						</td>
						<td width="15%" class="td_normal_title">
						<!-- 实际离职日期 -->
					<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLeaveTime" />
						</td>
						<td width="35%" class="datawidth">
							<xform:datetime showStatus="edit" property="fdLeaveTime"></xform:datetime>
						</td>
					</tr>
					<tr>
						<td width="15%" class="td_normal_title">
						<!-- 薪资结算日期 -->
						<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLeaveSalaryEndDate" />
						</td>
						<td width="35%" class="datawidth">
							<xform:datetime showStatus="edit" property="fdLeaveSalaryEndDate"></xform:datetime>
						</td>
						<td width="15%" class="td_normal_title">
						<!-- 离职原因 -->
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLeaveReason" />
						</td>
						<td width="35%" class="inputwidth">
							<xform:xselect property="fdLeaveReason" showStatus="edit" >
								<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdName" whereBlock="fdType='fdLeaveReason'" orderBy="fdOrder"></xform:beanDataSource>
							</xform:xselect>
						</td>
					</tr>
					<tr>
						<td width="15%" class="td_normal_title">
						<!-- 离职备注说明 -->
						<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLeaveRemark" />
						</td>
						<td width="35%" class="inputwidth">
							<xform:textarea showStatus="edit" property="fdLeaveRemark"></xform:textarea>
						</td>
						<td width="15%" class="td_normal_title">
							<!-- 离职去向说明 -->
						<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdNextCompany" />
						</td>
						<td width="35%" class="inputwidth">
							<xform:textarea showStatus="edit" property="fdNextCompany"></xform:textarea>
						</td>
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
		</html:form>
		<script>
			window.submitBtn = function (){
				 //Com_Submit(document.hrStaffPersonInfoForm, 'update');
				 var filed = decodeURI($(document.hrStaffPersonInfoForm).serialize());
				 var dataArr = filed.split("&");
				 var data ={}
				 $.each(dataArr,function(index,item){
					 var itemData = item.split("=")
					 data[itemData[0]]= itemData.length>1?decodeURIComponent(itemData[1]):"";
				 })
				 data['type'] = "leave"
 				 $.ajax({
					 url:"<%=request.getContextPath()%>/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=updatePersonInfo&fdId=${hrStaffPersonInfoForm.fdId}",
					 method:'post',
					 data:data,
					 success:function(res){
						 if(res.status){
							 window.parent.dialogObj.hide();
							 window.parent.location.reload();
						 }
					 }
				 })
			}
			
		</script>
	</template:replace>
</template:include>