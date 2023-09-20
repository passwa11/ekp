<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple" sidebar="auto">
	<template:replace name="body"> 
		<script type="text/javascript" src="${LUI_ContextPath}/resource/js/jquery.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript">
			var _NodeContent = eval('(' + parent.NodeContent + ')');
			var allparams = {};
			if(_NodeContent) {
				allparams = _NodeContent.params;
			}

			function serializeObject(form) {
				var o = {};
			    var a = form.serializeArray();
			    $.each(a, function() {
			        if (o[this.name] !== undefined) {
			            if (!o[this.name].push) {
			                o[this.name] = [o[this.name]];
			            }
			            o[this.name].push(this.value || '');
			        } else {
			            o[this.name] = this.value || '';
			        }
			    });
			    
			    // 保存下拉选择的文本值，因为在view页面不能读取表单数据，所以只能在这里保存
// 			    var _s = new Array("fdApplicant","fdLeaveDays","fdBeginDate","fdEndDate","fdLeaveType");
			    var _s = new Array("fdApplicant","fdStatus","fdAge","fdDateOfBirth","fdIdCard","fdStaffNo","fdWorkTime","fdTimeOfEnterprise","fdWorkingYears","fdUninterruptedWorkTime","fdTrialExpirationTime","fdEmploymentPeriod","fdStaffType","fdNameUsedBefore","fdNation","fdPoliticalLandscape","fdDateOfGroup","fdDateOfParty","fdHighestDegree","fdHighestEducation","fdMaritalStatus","fdHealth","fdStature","fdWeight","fdLivingPlace","fdNativePlace","fdHomeplace","fdAccountProperties","fdRegisteredResidence","fdResidencePoliceStation","fdTrialOperationPeriod","fdEntryTime","fdPositiveTime",
						// //通讯地址
						"fdPostalAddressProvinceId","fdPostalAddressCityId","fdPostalAddressAreaId","fdPostalAddress",
						// //家庭地址
						"fdHomeAddressProvinceId","fdHomeAddressCityId","fdHomeAddressAreaId","fdHomeAddress",
						//外语级别,是否退役军人,一级部门,二级部门,三级部门,
						"fdForeignLanguageLevel","fdIsRetiredSoldier","fdFirstLevelDepartment","fdSecondLevelDepartment","fdThirdLevelDepartment",
						//所属公司,考勤卡号,职级,所在部门,岗位名称,直接上级
						"fdAffiliatedCompany","fdTimeCardNo","fdOrgRankId","fdCategory","fdStaffingLevel","fdOrgParentId","fdOrgPostIds","fdReportLeaderId",
						// //费用归属,oa账户,是否OA用户
						"fdCostAttribution","fdOAAccount", "fdIsOAUser",
						// //私人邮箱,手机号码,紧急联系人姓名,紧急联系人电话,紧急联系人与员工关系,紧急联系人地址
						"fdPrivateMailbox","fdMobileNo","fdEmergencyContact","fdEmergencyContactPhone","fdRelationsOfEmergencyContactAndEmployee","fdEmergencyContactAddress",
						//社保号码,公积金账户,"是否二次入职","性别"
						"fdSocialSecurityNumber","fdSurplusAccount","fdSecondEntry","fdSex",
						//"密码","公司邮箱"
						"fdNewPassword","fdEmail",
						"fdLoginName","fdIsAttendance","fdFixedShift","fdProposedEmploymentConfirmationDate",
						"fdProbationPeriod","fdOfficeAreaProvinceId","fdOfficeAreaCityId","fdOfficeAreaAreaId","fdOfficeLocation"
				);
			    $.each(_s, function(i, n) {
			    	var name = n + "_text";
			    	o[name] = $("select[name=" + n + "]").find("option:selected").text();
			    });
			    return o;
			}

			// 必须实现的方法，供父窗口(attribute_robotnode.html)调用。
			function returnValue() {
				return "{\"params\":" + LUI.stringify(serializeObject($("#mainForm"))) + "}";
			};

			function setLeaveType() {
				var _allparams = allparams || {};
				// 设置下拉框内容

				$.each($("#mainForm select"), function(i, n) {
					if(n.options.length == 1)
						n.options[0].innerText = _allparams[$(n).attr("name") + "_text"];
				});
			};

			/**
			 * 获取所有表单字段
			 */
			function transFormFieldList() {
				var rtnResult = new Array();
				var fieldList = parent.FlowChartObject.FormFieldList;
				if (!fieldList)
					return rtnResult;
				// 转换成option支持的格式
				for ( var i = 0, length = fieldList.length; i < length; i++) {
					rtnResult.push( {
						value : fieldList[i].name,
						name : fieldList[i].label
					});
				}
				return rtnResult;
			};

			LUI.ready(function(){
				createSelectElementHTML("fdApplicant");
				createSelectElementHTML("fdStatus");
				createSelectElementHTML("fdStaffType");
				createSelectElementHTML("fdDateOfBirth");
				createSelectElementHTML("fdIdCard");
				createSelectElementHTML("fdStaffNo");
				createSelectElementHTML("fdWorkTime");
				createSelectElementHTML("fdTimeOfEnterprise");
				createSelectElementHTML("fdTrialExpirationTime");
				createSelectElementHTML("fdMaritalStatus");
				createSelectElementHTML("fdEmploymentPeriod");
				createSelectElementHTML("fdNameUsedBefore");
				createSelectElementHTML("fdNation");
				createSelectElementHTML("fdPoliticalLandscape");
				createSelectElementHTML("fdDateOfGroup");
				createSelectElementHTML("fdDateOfParty");
				createSelectElementHTML("fdHighestDegree");
				createSelectElementHTML("fdHighestEducation");
				createSelectElementHTML("fdHealth");
				createSelectElementHTML("fdStature");
				createSelectElementHTML("fdWeight");
				createSelectElementHTML("fdLivingPlace");
				createSelectElementHTML("fdNativePlace");
				createSelectElementHTML("fdHomeplace");
				createSelectElementHTML("fdAccountProperties");
				createSelectElementHTML("fdRegisteredResidence");
				createSelectElementHTML("fdResidencePoliceStation");
				createSelectElementHTML("fdTrialOperationPeriod");
				//createSelectElementHTML("fdOfficeLocation");
				createSelectElementHTML("fdEntryTime");
				createSelectElementHTML("fdPositiveTime");

				createSelectElementHTML("fdPostalAddressProvinceId");
				createSelectElementHTML("fdPostalAddressCityId");
				createSelectElementHTML("fdPostalAddressAreaId");
				createSelectElementHTML("fdPostalAddress");

				createSelectElementHTML("fdHomeAddressProvinceId");
				createSelectElementHTML("fdHomeAddressCityId");
				createSelectElementHTML("fdHomeAddressAreaId");
				createSelectElementHTML("fdHomeAddress");

				createSelectElementHTML("fdForeignLanguageLevel");
				createSelectElementHTML("fdIsRetiredSoldier");
				createSelectElementHTML("fdFirstLevelDepartment");
				createSelectElementHTML("fdSecondLevelDepartment");
				createSelectElementHTML("fdThirdLevelDepartment");

				createSelectElementHTML("fdAffiliatedCompany");
				createSelectElementHTML("fdTimeCardNo");
				createSelectElementHTML("fdOrgRankId");
				createSelectElementHTML("fdOrgParentId");
				createSelectElementHTML("fdOrgPostIds");
				createSelectElementHTML("fdReportLeaderId");

				createSelectElementHTML("fdCostAttribution");
				createSelectElementHTML("fdOAAccount");
				createSelectElementHTML("fdIsOAUser");

				createSelectElementHTML("fdPrivateMailbox");
				createSelectElementHTML("fdMobileNo");
				createSelectElementHTML("fdEmergencyContact");
				createSelectElementHTML("fdEmergencyContactPhone");
				createSelectElementHTML("fdRelationsOfEmergencyContactAndEmployee");
				createSelectElementHTML("fdEmergencyContactAddress");

				createSelectElementHTML("fdSocialSecurityNumber");
				createSelectElementHTML("fdSurplusAccount");
				createSelectElementHTML("fdSecondEntry");

				createSelectElementHTML("fdSex");
				createSelectElementHTML("fdNewPassword");
				createSelectElementHTML("fdEmail");

				createSelectElementHTML("fdLoginName");
				createSelectElementHTML("fdIsAttendance");
				createSelectElementHTML("fdFixedShift");
				createSelectElementHTML("fdProposedEmploymentConfirmationDate");
				createSelectElementHTML("fdProbationPeriod");
				createSelectElementHTML("fdOfficeAreaProvinceId");
				createSelectElementHTML("fdOfficeAreaCityId");
				createSelectElementHTML("fdOfficeAreaAreaId");
				createSelectElementHTML("fdOfficeLocation");

				createSelectElementHTML("fdStaffingLevel");

				createSelectElementHTML("fdCategory");

				setLeaveType();
			});

			function createSelectElementHTML(fieldName) {
				var _allparams = allparams || {};
				var options = transFormFieldList();
				var rtnResult = new Array();
				var checkVal = _allparams[fieldName];
				if (options == null || options.length == 0) {
					rtnResult.push('<option value=\'\' selected><bean:message key="hrStaff.robot.select" bundle="hr-staff"/></option>');
				} else {
					for ( var i = 0, length = options.length; i < length; i++) {
						var option = options[i], value = option.value || option.name;
						rtnResult.push('<option value=\'' + value + '\'');
						if (value == checkVal)
							rtnResult.push(' selected');
						rtnResult.push('>' + option.name + '</option>');
					}
				}
				var select = '<select name=\'' + fieldName + '\'>' + rtnResult.join('') + '</select>';
				$("#" + fieldName).html(select);
			};
		</script>
		
		<center>
			<form id="mainForm" action="">
			<h3>${lfn:message('hr-staff:hrStaff.robot.formMapping') }</h3>
			<table width="100%" class="tb_normal">
				<tr align="center">
					<td width="30%">
						${lfn:message('hr-staff:hrStaff.robot.personInfoAttribute') }
					</td>
					<td width="20%">
						${lfn:message('hr-staff:hrStaff.robot.formField') }
					</td>
					<td width="30%">
						${lfn:message('hr-staff:hrStaff.robot.personInfoAttribute') }
					</td>
					<td width="20%">
						${lfn:message('hr-staff:hrStaff.robot.formField') }
					</td>
				</tr>
				<tr align="center">
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdName') }
					</td>
					<td id="fdApplicant">
					</td>
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdStatus') }
					</td>
					<td id="fdStatus">
					</td>
				</tr>
				<tr align="center">
<!-- 					<td> -->
<%-- 						${lfn:message('hr-staff:hrStaffPersonInfo.fdAge') } --%>
<!-- 					</td> -->
<!-- 					<td id="fdAge"> -->
<!-- 					</td> -->
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdStaffType') }
					</td>
					<td id="fdStaffType">
					</td>
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdDateOfBirth') }
					</td>
					<td id="fdDateOfBirth">
					</td>
				</tr>
				<tr align="center">
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdIdCard') }
					</td>
					<td id="fdIdCard">
					</td>
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdStaffNo') }
					</td>
					<td id="fdStaffNo">
					</td>
				</tr>
				<tr align="center">
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdWorkTime') }
					</td>
					<td id="fdWorkTime">
					</td>
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdTimeOfEnterprise') }
					</td>
					<td id="fdTimeOfEnterprise">
					</td>
				</tr>
				<tr align="center">
<!-- 					<td> -->
<%-- 						${lfn:message('hr-staff:hrStaffPersonInfo.fdWorkingYears') } --%>
<!-- 					</td> -->
<!-- 					<td id="fdWorkingYears"> -->
<!-- 					</td> -->
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdTrialExpirationTime') }
					</td>
					<td id="fdTrialExpirationTime">
					</td>
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdTrialOperationPeriod') }
					</td>
					<td id="fdTrialOperationPeriod">
					</td>
				</tr>
				<tr align="center">
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdEmploymentPeriod') }
					</td>
					<td id="fdEmploymentPeriod">
					</td>
<%--					<td>--%>
<%--						${lfn:message('hr-staff:hrStaffPersonInfo.fdOfficeLocation') }--%>
<%--					</td>--%>
<%--					<td id="fdOfficeLocation">--%>
<%--					</td>--%>
				</tr>
				<tr align="center">
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdEntryTime') }
					</td>
					<td id="fdEntryTime">
					</td>
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdMaritalStatus') }
					</td>
					<td id="fdMaritalStatus">
					</td>
				</tr>
				<tr align="center">
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdPositiveTime') }
					</td>
					<td id="fdPositiveTime">
					</td>
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdNameUsedBefore') }
					</td>
					<td id="fdNameUsedBefore">
					</td>
				</tr>
				<tr align="center">
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdNation') }
					</td>
					<td id="fdNation">
					</td>
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdPoliticalLandscape') }
					</td>
					<td id="fdPoliticalLandscape">
					</td>
				</tr>
				<tr align="center">
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdDateOfGroup') }
					</td>
					<td id="fdDateOfGroup">
					</td>
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdDateOfParty') }
					</td>
					<td id="fdDateOfParty">
					</td>
				</tr>
				<tr align="center">
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdHighestDegree') }
					</td>
					<td id="fdHighestDegree">
					</td>
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdHighestEducation') }
					</td>
					<td id="fdHighestEducation">
					</td>
				</tr>
				<tr align="center">
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdHealth') }
					</td>
					<td id="fdHealth">
					</td>
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdStature') }
					</td>
					<td id="fdStature">
					</td>
				</tr>
				<tr align="center">
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdWeight') }
					</td>
					<td id="fdWeight">
					</td>
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdLivingPlace') }
					</td>
					<td id="fdLivingPlace">
					</td>
				</tr>
				<tr align="center">
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdNativePlace') }
					</td>
					<td id="fdNativePlace">
					</td>
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdHomeplace') }
					</td>
					<td id="fdHomeplace">
					</td>
				</tr>
				<tr align="center">
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdAccountProperties') }
					</td>
					<td id="fdAccountProperties">
					</td>
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdRegisteredResidence') }
					</td>
					<td id="fdRegisteredResidence">
					</td>
				</tr>
				<tr align="center">
					<td>
						${lfn:message('hr-staff:hrStaffPersonInfo.fdResidencePoliceStation') }
					</td>
					<td id="fdResidencePoliceStation">
					</td>
<!-- 					<td> -->
<%-- 						${lfn:message('hr-staff:hrStaffPersonInfo.fdUninterruptedWorkTime') } --%>
<!-- 					</td> -->
<!-- 					<td id="fdUninterruptedWorkTime"> -->
<!-- 					</td> -->
				</tr>
				<tr>
					<td>通信地址</td>
					<td id="fdPostalAddressProvinceId"></td>
					<td id="fdPostalAddressCityId"></td>
					<td id="fdPostalAddressAreaId"></td>
				</tr>
				<tr>
					<td>通信详细地址</td>
					<td id="fdPostalAddress"></td>
				</tr>
				<tr>
					<td>家庭地址</td>
					<td id="fdHomeAddressProvinceId"></td>
					<td id="fdHomeAddressCityId"></td>
					<td id="fdHomeAddressAreaId"></td>
				</tr>
				<tr>
					<td>家庭详细地址</td>
					<td id="fdHomeAddress"></td>
				</tr>
				<tr>
					<td>外语级别</td><td id="fdForeignLanguageLevel"></td>
					<td>是否退役军人</td><td id="fdIsRetiredSoldier"></td>
				</tr>
				<tr>
					<td>一级部门</td><td id="fdFirstLevelDepartment"></td>
					<td>二级部门</td><td id="fdSecondLevelDepartment"></td>
				</tr>
				<tr>
					<td>三级部门</td><td id="fdThirdLevelDepartment"></td>
					<td>所属公司</td><td id="fdAffiliatedCompany"></td>
				</tr>
				<tr>
					<td>考勤卡号</td><td id="fdTimeCardNo"></td>
					<td>职级</td><td id="fdOrgRankId"></td>
				</tr>
				<tr>
					<td>所在部门</td><td id="fdOrgParentId"></td>
					<td>岗位名称</td><td id="fdOrgPostIds"></td>
				</tr>
				<tr>
					<td>直接上级</td><td id="fdReportLeaderId"></td>
					<td>是否OA用户</td><td id="fdIsOAUser"></td>
				</tr>
				<tr>
					<td>费用归属</td><td id="fdCostAttribution"></td>
					<td>oa账户</td><td id="fdOAAccount"></td>
				</tr>
				<tr>
					<td>私人邮箱</td><td id="fdPrivateMailbox"></td>
					<td>手机号码</td><td id="fdMobileNo"></td>
				</tr>
				<tr>
					<td>紧急联系人姓名</td><td id="fdEmergencyContact"></td>
					<td>紧急联系人电话</td><td id="fdEmergencyContactPhone"></td>
				</tr>
				<tr>
					<td>紧急联系人与员工关系</td><td id="fdRelationsOfEmergencyContactAndEmployee"></td>
					<td>紧急联系人地址</td><td id="fdEmergencyContactAddress"></td>
				</tr>
				<tr>
					<td>社保号码</td><td id="fdSocialSecurityNumber"></td>
					<td>公积金账户</td><td id="fdSurplusAccount"></td>
				</tr>
				<tr>
					<td>是否二次入职</td><td id="fdSecondEntry"></td>
					<td>性别</td><td id="fdSex"></td>
				</tr>
				<tr>
					<td>公司邮箱</td><td id="fdEmail"></td>
					<td>密码</td><td id="fdNewPassword"></td>
				</tr>
				<tr>
					<td>系统账号</td><td id="fdLoginName"></td>
					<td>是否考勤</td><td id="fdIsAttendance"></td>
				</tr>
				<tr>
					<td>固定班次</td><td id="fdFixedShift"></td>
				</tr>
				<tr>
					<td>拟转正时间</td><td id="fdProposedEmploymentConfirmationDate"></td>
					<td>试用期限</td><td id="fdProbationPeriod"></td>
				</tr>
				<tr>
					<td>办公所属省区域</td><td id="fdOfficeAreaProvinceId"></td>
					<td>办公所属市区域</td><td id="fdOfficeAreaCityId"></td>
				</tr>
				<tr>
					<td>办公所属区域</td><td id="fdOfficeAreaAreaId"></td>
					<td>办公所属地址</td><td id="fdOfficeLocation"></td>
				</tr>
				<tr>
					<td>职类</td><td id="fdCategory"></td>
					<td>职务</td><td id="fdStaffingLevel"></td>
				</tr>
			</table>
			</form>
		</center>
	</template:replace>
</template:include>