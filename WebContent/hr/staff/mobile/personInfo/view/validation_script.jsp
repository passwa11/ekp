<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.organization.model.SysOrganizationConfig"%>
<%
	SysOrganizationConfig sysOrganizationConfig = new SysOrganizationConfig();
	String isLoginSpecialChar = sysOrganizationConfig.getIsLoginSpecialChar();
%>
<script>
	require(["dojo/ready","dojo/request","dojo/dom-form","dojo/query","dojo/on","dijit/registry"],function(ready,request,domForm,query,on,registry){
		var _validatorObj = null;
		ready(function(){
			_validatorObj = registry.byId("baseInfoForm");
			var isLoginSpecialChar = <%=isLoginSpecialChar%>;
			var errorMsg ="<bean:message key='sysOrgPerson.error.loginName.abnormal' bundle='sys-organization'/>";
			<%  if("true".equals(isLoginSpecialChar)){%>
				errorMsg = "只能包含部分特殊字符 @ # $ % ^ & ( ) - + = { } : ; \ ' ? / < > , . \" [ ] | _ 空格";
			<% } %>
			// 增加一个字符串的startsWith方法
			function startsWith(value, prefix) {
				return value.slice(0, prefix.length) === prefix;
			}
			console.log(_validatorObj);
			// 校验手机号是否正确
			_validatorObj._validation.addValidator('phone','<bean:message key="hrStaffPersonInfo.phone.err" bundle="hr-staff" />',function(v,e,o){
				if (v == "") {
					return true;
				}
				// 国内手机号可以有+86，但是后面必须是11位数字
				// 国际手机号必须要以+区号开头，后面可以是6~11位数据
				if(startsWith(v, "+")) {
					if(startsWith(v, "+86")) {
						return /^(\+86)(\d{11})$/.test(v);
					} else {
						return /^(\+\d{1,5})(\d{6,11})$/.test(v);
					}
				} else {
					// 没有带+号开头，默认是国内手机号
					return /^(\d{11})$/.test(v);
				}
			});
			// 验证手机号是否已被注册
			_validatorObj._validation.addValidator('uniqueMobileNo','<bean:message key="sysOrgPerson.error.newMoblieNoSameOldName" bundle="sys-organization" />',function(value,e,o){
				if(startsWith(value, "+86")) {
					// 如果是+86开头的手机号，保存到数据库时强制去掉+86前缀
					value = value.slice(3, value.length)
				}
				if(startsWith(value, "+")) {
					value = value.replace("+", "x")
				}
				var fdId = '${hrStaffPersonInfoForm.fdId}';
				var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=hrStaffPersonInfoService&mobileNo="
						+ value + "&fdId=" + fdId + "&checkType=unique&date=" + new Date());
				var result = _CheckUnique(url);
				if (!result) 
					return false;
				return true;
			});
			// 验证工号是是否唯一的
			_validatorObj._validation.addValidator('uniqueStaffNo','<bean:message key="hrStaffPersonInfo.staffNo.unique.err" bundle="hr-staff" />',function(value,e,o){
				var fdId = '${hrStaffPersonInfoForm.fdId}';
				var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=hrStaffPersonInfoService&staffNo="
						+ value + "&fdId=" + fdId + "&checkType=unique&date=" + new Date());
				var result = _CheckUnique(url);
				if (!result) 
					return false;
				return true;
			});
			// 验证系统账号是否唯一
			_validatorObj._validation.addValidator('uniqueLoginName','账号已经存在',function(value,e,o){
				var fdId = '${hrStaffPersonInfoForm.fdId}';
				if(!value)return true;
				var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=sysOrgPersonService&loginName="
						+ value + "&fdId=" + fdId + "&checkType=unique&date=" + new Date());
				var result = _CheckUnique(url);
				if (!result) 
					return false;
				return true;
			});
			// 验证系统账号有效性
			var StaffLoginNameValidators = {};
			_validatorObj._validation.addValidator('invalidLoginName','<bean:message key="sysOrgPerson.error.newLoginNameSameOldName" bundle="sys-organization" />',function(value,e,o){
				if (StaffLoginNameValidators["lgName"] && (StaffLoginNameValidators["lgName"]==value)){
				    return true;
			    }
			    StaffLoginNameValidators["lgName"]=null;
			    var fdId = '${hrStaffPersonInfoForm.fdId}';
			    var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=sysOrgPersonService&loginName="
						+ value + "&fdId=" + fdId + "&checkType=invalid&date=" + new Date());
				var result = _CheckUnique(url);
				if (!result){
					return false;
				}else{
					return true;	
				}
			});
			// 验证系统账号是否含有特殊字符
			_validatorObj._validation.addValidator('normalLoginName',errorMsg,function(value,e,o){
				<% if("true".equals(isLoginSpecialChar)){%>
					pattern=new RegExp("^[A-Za-z0-9_@#$%^&()={}:;\'?/<>,.\"\\[\\]|\\-\\+ ]+$");
				<% }else{ %>
					pattern=new RegExp("^[A-Za-z0-9_]+$");
				<% }%>
				console.log("normalLoginName "+pattern.test(value))
				if(pattern.test(value) || ((value == null) ||value.replace(/^(\s|\u00A0)+|(\s|\u00A0)+$/g,"")==''|| (value.length == 0))){
					return true;
				}else{
					return false;
				}
			});
			// 检查是否唯一
			function _CheckUnique(url) {
				var xmlHttpRequest;
				if (window.XMLHttpRequest) { // Non-IE browsers
					xmlHttpRequest = new XMLHttpRequest();
				} else if (window.ActiveXObject) { // IE
					try {
						xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
					} catch (othermicrosoft) {
						try {
							xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
						} catch (failed) {
							xmlHttpRequest = false;
						}
					}
				}
				if (xmlHttpRequest) {
					xmlHttpRequest.open("GET", url, false);
					xmlHttpRequest.send();
					var result = xmlHttpRequest.responseText.replace(/\s/g, "").replace(/;/g, "\n");
					if (result != "") {
						return false;
					}
				}
				return true;
			}
			// 验证手机号码
			_validatorObj._validation.addValidator('workPhone','<bean:message key="hrStaffPersonInfo.phone.err" bundle="hr-staff" />',function(v,e,o){
				if(v=="") {
					return true;
				}
				if(/^0\d{2,3}-?\d{7,8}$/.test(v)) {
				     return true; // 校验通过
				 }
				return false;
			});
			// 验证参加工作时间
			_validatorObj._validation.addValidator('workTime','<bean:message key="hrStaffPersonInfo.workTime.err.plus" bundle="hr-staff" />',function(v,e,o){
				if(v=="") {
					return true;
				}
				if(workTimeCheck(v)) {
				     return true; // 校验通过
				}
				return false;
			});
			//参加工作时间一定是小于等于到本单位时间，试用到期时间项一定是大于或等于参加工作时间
			function workTimeCheck(workTime){
				var fdworkTime=Com_GetDate(workTime);
					 workTime=fdworkTime.getTime();
				
				var fdTimeOfEnterprise=query("[name='fdTimeOfEnterprise']")[0].value;
				var timeOfEnterprise=Com_GetDate(fdTimeOfEnterprise);
				timeOfEnterprise=timeOfEnterprise.getTime();
				
				 var fdTrialExpirationTime=query("input[name='fdTrialExpirationTime']")[0].value;
				 var trialExpirationTime=Com_GetDate(fdTrialExpirationTime);
				 trialExpirationTime=trialExpirationTime.getTime();
				if(fdTimeOfEnterprise != ""){
					if(fdTrialExpirationTime !=""){
					if(workTime<=timeOfEnterprise && workTime<trialExpirationTime){
						return true;
						}
						return false;
					}else{
						if(workTime>timeOfEnterprise){
							return false;
							}
							return true;
					}
				}else{
					if(fdTrialExpirationTime !=""){
						if(workTime>trialExpirationTime){
							return false;
							}
							return true;
						}
							return true;
				}
			}
			// 验证到本单位时间
			_validatorObj._validation.addValidator('timeOfEnterprise','<bean:message key="hrStaffPersonInfo.timeOfEnterprise.err.plus" bundle="hr-staff" />',function(v,e,o){
				if(v=="") {
					return true;
				}
				if(timeOfEnterpriseCheck(v)) {
				     return true; // 校验通过
				}
				return false;
			});
			function timeOfEnterpriseCheck(timeOfEnterprise){
				 var fdtimeOfEnterprise=Com_GetDate(timeOfEnterprise);
				 timeOfEnterprise=fdtimeOfEnterprise.getTime();
				
				var fdWorkTime=query("[name='fdWorkTime']")[0].value;
				var workTime=Com_GetDate(fdWorkTime);
				workTime=workTime.getTime();
				
				var fdTrialExpirationTime=query("[name='fdTrialExpirationTime']")[0].value;
				var trialExpirationTime=Com_GetDate(fdTrialExpirationTime);
				trialExpirationTime=trialExpirationTime.getTime();
				if(fdWorkTime != ""){
					if(fdTrialExpirationTime !=""){
					if(timeOfEnterprise>=workTime && timeOfEnterprise<trialExpirationTime){
						return true;
						}
						return false;
					}else{
						if(timeOfEnterprise<workTime){
							return false;
							}
							return true;
					}
				}else{
					if(fdTrialExpirationTime !=""){
						if(timeOfEnterprise>trialExpirationTime){
							return false;
							}
							return true;
						}
							return true;
				}
				
			}
			// 验证试用到期时间
			_validatorObj._validation.addValidator("trialExpirationTime","<bean:message key='hrStaffPersonInfo.trialExpirationTime.err.plus' bundle='hr-staff' />",function(v,e,o){
				if(v=="") {
					return true;
				}
				if(trialExpirationTimeCheck(v)) {
					
				     return true; // 校验通过
				}
				return false;
			});
			_validatorObj._validation.addValidator('compareTime(type)','<bean:message bundle="hr-staff" key="hrStaffPersonInfo.compareTime.error.msg" arg0="{start}" arg1="{end}" />',function(v,e,o){
				var type = o['type'];
				var fdStartDate,fdEndDate;
				if(type == 'group'){
					fdStartDate = query("[name='fdDateOfGroup']")[0].value;
					fdEndDate = query("[name='fdDateOfParty']")[0].value;
					o['start'] = "<bean:message bundle='hr-staff' key='hrStaffPersonInfo.fdDateOfGroup' />";
					o['end'] = "<bean:message bundle='hr-staff' key='hrStaffPersonInfo.fdDateOfParty' />";
				}
				if(type == 'entry'){
					fdStartDate = query("[name='fdEntryTime']")[0].value;
					fdEndDate = query("[name='fdPositiveTime']")[0].value;
					o['start'] = "<bean:message bundle='hr-staff' key='hrStaffPersonInfo.fdEntryTime' />";
					o['end'] = "<bean:message bundle='hr-staff' key='hrStaffPersonInfo.fdPositiveTime' />";
				}
				var result = true;
				if(fdStartDate && fdEndDate){
					var start = Com_GetDate(fdStartDate);
					var end = Com_GetDate(fdEndDate);
					if (start.getTime() > end.getTime()) {
						result = false;
					}
				}
				return result;
			});
			function trialExpirationTimeCheck(trialExpirationTime){
				var fdtrialExpirationTime=Com_GetDate(trialExpirationTime);
				trialExpirationTime=fdtrialExpirationTime.getTime();
				
				var fdWorkTime=query("input[name$='fdWorkTime']")[0].value;
				var workTime=Com_GetDate(fdWorkTime);
				workTime=workTime.getTime();
				
				var fdTimeOfEnterprise=query("input[name$='fdTimeOfEnterprise']")[0].value;
				var timeOfEnterprise=Com_GetDate(fdTimeOfEnterprise);
				timeOfEnterprise=timeOfEnterprise.getTime();
				
				if(fdWorkTime != ""){
					if(fdTimeOfEnterprise !=""){
					if(trialExpirationTime>workTime && trialExpirationTime>timeOfEnterprise){
						return true;
						}
						return false;
					}else{
						if(trialExpirationTime<workTime){
							return false;
							}
							return true;
					}
				}else{
					if(fdTimeOfEnterprise !=""){
						if(timeOfEnterprise>trialExpirationTime){
							return false;
							}
							return true;
						}
							return true;
				}
				
			}
			window.workYearChange=function(value,obj){
				if(!value){
					return;
				}
				var oldValue=query("input[name='fdWorkingYearsValue']")[0].value;
				oldValue=isNaN(parseInt(oldValue))?0:parseInt(oldValue);
				var year = query("input[name='fdWorkingYearsYear']")[0].value;
				var month = query("input[name='fdWorkingYearsMonth']")[0].value;
				if(year == "" && month == ""){
					query("input[name='fdWorkingYearsDiff']")[0].value=0;
				}else{
					year=isNaN(parseInt(year))?0:parseInt(year);
					month=isNaN(parseInt(month))?0:parseInt(month);
					var result = year*12+month-oldValue;
					query("input[name='fdWorkingYearsDiff']")[0].value=result;
				}
			}
			
			window.uninterruptedWorkTimeChange=function(value,obj){
				if(!value){
					return;
				}
				var oldValue=query("input[name=fdUninterruptedWorkTimeValue]")[0].value;
				oldValue=isNaN(parseInt(oldValue))?0:parseInt(oldValue);
				var year = query("input[name='fdUninterruptedWorkTimeYear']")[0].value;
				var month = query("input[name='fdUninterruptedWorkTimeMonth']")[0].value;
				if(year == "" && month == ""){
					$("input[name='fdWorkTimeDiff']").val(0);
				}else{
					year=isNaN(parseInt(year))?0:parseInt(year);
					month=isNaN(parseInt(month))?0:parseInt(month);
					var result = year*12+month-oldValue;
					query("input[name='fdWorkTimeDiff']")[0].value=result;
				}
			}
			
			window.getDiffMonths=function(value){
				var diffMonths=0;
				if(value){
					var date = Com_GetDate(value);
					var year,month,day;
					if(date){
						year = date.getFullYear();
						month = date.getMonth()+1;
						day = date.getDate();
					}
					var now= new Date();
					var nowyear = now.getFullYear();
					var nowmonth = now.getMonth()+1;
					var nowday = now.getDate();
					if(year && month && day){
						if(nowyear>year){
							diffMonths=(nowyear-year)*12-month;
							if(nowday>=day){
								diffMonths+=nowmonth;
							}else{
								diffMonths+=nowmonth-1;
							}
						}else if(nowyear == year){
							if(nowmonth>month){
								if(nowday>=day){
									diffMonths+=nowmonth-month;
								}else{
									diffMonths+=nowmonth-month-1;
								}
							}
						}
					}
				}
				return diffMonths;
			}
			window.timeOfEnterpriseChange=function(value,obj){
				if(value){
					var fdWorkTime = '${hrStaffPersonInfoForm.fdTimeOfEnterprise}';
					var fdWorkingYearsValue = '${hrStaffPersonInfoForm.fdWorkingYearsValue}';
					var fdWorkingYearsDiff = '${hrStaffPersonInfoForm.fdWorkingYearsDiff}';
					var fdWorkingYearsYear = '${hrStaffPersonInfoForm.fdWorkingYearsYear}';
					var fdWorkingYearsMonth = '${hrStaffPersonInfoForm.fdWorkingYearsMonth}';
					if(fdWorkTime!=value){
						var diffMonths = getDiffMonths(value);
						var diffYear = parseInt(diffMonths/12);
						var diffMonth = diffMonths % 12;
						query("input[name=fdWorkingYearsYear]").val(diffYear);
						query("input[name=fdWorkingYearsMonth]").val(diffMonth);
						query("input[name='fdWorkingYearsValue']").val(diffMonths);
						query("input[name='fdWorkingYearsDiff']").val(0);
					}else{
						query("input[name='fdWorkingYearsValue']").val(fdWorkingYearsValue);
						query("input[name=fdWorkingYearsYear]").val(fdWorkingYearsYear);
						query("input[name=fdWorkingYearsMonth]").val(fdWorkingYearsMonth);
						query("input[name='fdWorkingYearsDiff']").val(fdWorkingYearsDiff);
					}
					query("input[name=fdWorkingYearsYear]").removeAttr("disabled");
					query("input[name=fdWorkingYearsMonth]").removeAttr("disabled");
					
				}else{
					query("input[name=fdWorkingYearsYear]").val("");
					query("input[name=fdWorkingYearsMonth]").val("");
					query("input[name=fdWorkingYearsYear]").attr("disabled","disabled");
					query("input[name=fdWorkingYearsMonth]").attr("disabled","disabled");
					query("input[name='fdWorkingYearsValue']").val(0);
					query("input[name='fdWorkingYearsDiff']").val(0);
				}
			}
			
			window.workTimeChange=function(value,obj){
				if(value){
					var fdWorkTime = '${hrStaffPersonInfoForm.fdWorkTime}';
					var fdUninterruptedWorkTimeValue = '${hrStaffPersonInfoForm.fdUninterruptedWorkTimeValue}';
					var fdWorkTimeDiff = '${hrStaffPersonInfoForm.fdWorkTimeDiff}';
					var fdUninterruptedWorkTimeYear = '${hrStaffPersonInfoForm.fdUninterruptedWorkTimeYear}';
					var fdUninterruptedWorkTimeMonth = '${hrStaffPersonInfoForm.fdUninterruptedWorkTimeMonth}';
					if(fdWorkTime!=value){
						var diffMonths = getDiffMonths(value);
						var diffYear = parseInt(diffMonths/12);
						var diffMonth = diffMonths % 12;
						query("input[name=fdUninterruptedWorkTimeYear]").val(diffYear);
						query("input[name=fdUninterruptedWorkTimeMonth]").val(diffMonth);
						query("input[name='fdUninterruptedWorkTimeValue']").val(diffMonths);
						query("input[name='fdWorkTimeDiff']").val(0);
					}else{
						query("input[name='fdUninterruptedWorkTimeValue']").val(fdUninterruptedWorkTimeValue);
						query("input[name=fdUninterruptedWorkTimeYear]").val(fdUninterruptedWorkTimeYear);
						query("input[name=fdUninterruptedWorkTimeMonth]").val(fdUninterruptedWorkTimeMonth);
						query("input[name='fdWorkTimeDiff']").val(fdWorkTimeDiff);
					}
					query("input[name=fdUninterruptedWorkTimeYear]").removeAttr("disabled");
					query("input[name=fdUninterruptedWorkTimeMonth]").removeAttr("disabled");
				}else{
					query("input[name=fdUninterruptedWorkTimeYear]").val("");
					query("input[name=fdUninterruptedWorkTimeMonth]").val("");
					query("input[name=fdUninterruptedWorkTimeYear]").attr("disabled","disabled");
					query("input[name=fdUninterruptedWorkTimeMonth]").attr("disabled","disabled");
					query("input[name='fdUninterruptedWorkTimeValue']").val(0);
					query("input[name='fdWorkTimeDiff']").val(0);
				}
			}
			window.setDisabled=function(value,yname,mname){
				if(value){
					if(yname){
						query("input[name="+yname+"]").removeAttr("disabled");
					}
					if(mname){
						query("input[name="+mname+"]").removeAttr("disabled");
					}
				}else{
					if(yname){
						query("input[name="+yname+"]").attr("disabled","disabled");
					}
					if(mname){
						query("input[name="+mname+"]").attr("disabled","disabled");
					}
				}
			}

			//根据身份证号自动填充出生日期
			window.idCardChange = function(value) {
				var sexcode = "";
				var birth = "";
				var flag = false;
				if (value.length == 15) {
					sexcode = value.substring(14, 15);
					birth ="19" + value.substring(6, 8) + "-" + value.substring(8, 10) + "-" + value.substring(10, 12);
					flag = true;
				}
				if (value.length == 18) {
					sexcode = value.substring(16, 17);
					birth = value.substring(6, 10) + "-" + value.substring(10, 12) + "-" + value.substring(12, 14);
					flag = true;
				}
				if(flag){
					var idefctdate = new Date(Date.parse(birth.replace(/-/g, "/"))).valueOf();
					if(!isNaN(idefctdate)){
						var fdDateOfBirth = query("input[name='fdDateOfBirth']")[0].value;
						if(fdDateOfBirth == ""){
							registry.byId('fdDateOfBirth')._setValueAttr(birth);
						}
					}
					//偶数为女性，奇数为男性
					if ((sexcode & 1) == 0) {
						registry.byId('fdSex')._setValueAttr("F");
					} else {
						registry.byId('fdSex')._setValueAttr("M");
					}
				}
			}
			setDisabled("${hrStaffPersonInfoForm.fdWorkTime}","fdUninterruptedWorkTimeYear","fdUninterruptedWorkTimeMonth");
			setDisabled("${hrStaffPersonInfoForm.fdTimeOfEnterprise}","fdWorkingYearsYear","fdWorkingYearsMonth");
		})
	});
</script>