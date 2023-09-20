<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
	 <script type="text/template" id="personInfoTemplate">
		  <div class="lui-personnel-file-header-title">
		    <div class="lui-personnel-file-header-title-left">
		      <span></span><div class="lui-personnel-file-header-title-text">个人信息</div>
		    </div>
		    <div class="lui-personnel-file-edit">
		      <span class="lui-personnel-file-edit-icon"></span>
		      <span class="lui-personnel-file-edit-text">编辑</span>
		    </div>
		  </div>
		  <table>
		    <tbody>
		        <tr><td width="160">姓名</td><td width="240">{fdName}</td><td width="160">曾用名</td><td width="160">{fdNameUsedBefore}</td></tr>
		        <tr><td width="160">身份证号码</td><td width="240">{fdIdCard}</td><td width="160">性别</td><td width="160">{fdSex}</td></tr>
		        <tr><td width="160">出生日期</td><td width="240">{fdDateOfBirth}</td><td width="160">年龄</td><td width="160">{fdAge}</td></tr>
		        <tr><td width="160">婚姻状况</td><td width="240">{fdMaritalStatus}</td><td width="160">是否已育</td><td width="160">否</td></tr>
		        <tr><td width="160">民族</td><td width="240">{fdNation}</td><td width="160">政治面貌</td><td width="160">{fdPoliticalLandscape}</td></tr>
		        <tr><td width="160">籍贯</td><td width="240">{fdNativePlace}</td><td width="160">现居住地</td><td width="160">{fdLivingPlace}</td></tr>
		        <tr><td width="160">出生地</td><td width="240">{fdHomeplace}</td><td width="160">户口性质</td><td width="160">{fdAccountProperties}</td></tr>
		        <tr><td width="160">户口所在地</td><td width="240">{fdRegisteredResidence}</td><td width="160">户口所在派出所</td><td width="160">{fdResidencePoliceStation}</td></tr>
		    </tbody>
		  </table>
	  </script>
	<div class="lui-personnel-file-staffInfo" id="staffInfo"></div>
	<script>
		var jsonData = {
			fdName:'${hrStaffPersonInfoForm.fdName}',
			fdNameUsedBefore:'${hrStaffPersonInfoForm.fdNameUsedBefore}',
			fdIdCard:'${hrStaffPersonInfoForm.fdIdCard}',
			fdSex:'${hrStaffPersonInfoForm.fdSex}',
			fdMaritalStatus:'${hrStaffPersonInfoForm.fdMaritalStatus}',
			fdDateOfBirth:'${hrStaffPersonInfoForm.fdDateOfBirth}',
			fdPoliticalLandscape:'${hrStaffPersonInfoForm.fdPoliticalLandscape}',
			fdNativePlace:'${hrStaffPersonInfoForm.fdNativePlace}',
			fdHomeplace:'${hrStaffPersonInfoForm.fdHomeplace}',
			fdRegisteredResidence:'${hrStaffPersonInfoForm.fdRegisteredResidence}',
			fdResidencePoliceStation:'${hrStaffPersonInfoForm.fdResidencePoliceStation}',
			fdNation:'${hrStaffPersonInfoForm.fdNation}',
			fdAge:'${hrStaffPersonInfoForm.fdAge}',
			fdAccountProperties:'${hrStaffPersonInfoForm.fdAccountProperties}',
			fdLivingPlace:'${hrStaffPersonInfoForm.fdLivingPlace}',
			fdAccountProperties:'${hrStaffPersonInfoForm.fdAccountProperties}'
		}
		
		$('.lui-personnel-file-staffInfo').html(updateDomData(jsonData,$("#personInfoTemplate").html()))
	</script>