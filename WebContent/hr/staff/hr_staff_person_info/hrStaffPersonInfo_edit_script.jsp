<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.organization.model.SysOrganizationConfig"%>
<%
	SysOrganizationConfig sysOrganizationConfig = new SysOrganizationConfig();
	String isLoginSpecialChar = sysOrganizationConfig.getIsLoginSpecialChar();
%>

<script type="text/javascript">
	var _validator = $KMSSValidation(document.forms['hrStaffPersonInfoForm']);
	var isLoginSpecialChar = <%=isLoginSpecialChar%>;
	var errorMsg ="<bean:message key='sysOrgPerson.error.loginName.abnormal' bundle='sys-organization'/>";
	<%  if("true".equals(isLoginSpecialChar)){%>
		errorMsg = "只能包含部分特殊字符 @ # $ % ^ & ( ) - + = { } : ; \ ' ? / < > , . \" [ ] | _ 空格";
	<% } %>
	// 增加一个字符串的startsWith方法
	function startsWith(value, prefix) {
		return value.slice(0, prefix.length) === prefix;
	}
	
	// 校验手机号是否正确
	_validator.addValidator(
		'phone',
		"<bean:message key='hrStaffPersonInfo.phone.err' bundle='hr-staff' />",
		function(v, e, o) {
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
	var MobileNoValidators = {
			"uniqueMobileNo" : {
				error : "<bean:message key='sysOrgPerson.error.newMoblieNoSameOldName' bundle='sys-organization' />",
				test : function (value) {
					if(startsWith(value, "+86")) {
						// 如果是+86开头的手机号，保存到数据库时强制去掉+86前缀
						value = value.slice(3, value.length)
					}
					if(startsWith(value, "+")) {
						value = value.replace("+", "x")
					}
					var fdId = document.getElementsByName("fdId")[0].value;
					var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=hrStaffPersonInfoService&mobileNo="
							+ value + "&fdId=" + fdId + "&checkType=unique&date=" + new Date());
					var result = _CheckUnique(url);
					if (!result) 
						return false;
					return true;
			      }
			}
	};
	
	// 验证工号是是否唯一的
	var StaffNoValidators = {
			"uniqueStaffNo" : {
				error : "<bean:message key='hrStaffPersonInfo.staffNo.unique.err' bundle='hr-staff' />",
				test : function (value) {
					var fdId = document.getElementsByName("fdId")[0].value;
					var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=hrStaffPersonInfoService&staffNo="
							+ value + "&fdId=" + fdId + "&checkType=unique&date=" + new Date());
					var result = _CheckUnique(url);
					if (!result) 
						return false;
					return true;
			      }
			}
	};
	var StaffLoginNameValidators = {
			"uniqueLoginName":{
				error:"账号已经存在",
				test:function(value){
					var fdId = document.getElementsByName("fdId")[0].value;
					if(!value)return true;
					var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=sysOrgPersonService&loginName="
							+ value + "&fdId=" + fdId + "&checkType=unique&date=" + new Date());
					var result = _CheckUnique(url);
					if (!result) 
						return false;
					return true;
				}
			},
			'invalidLoginName': {
				error : "<bean:message key='sysOrgPerson.error.newLoginNameSameOldName' bundle='sys-organization' />",
				test  : function(value) {
					    if (StaffLoginNameValidators["lgName"] && (StaffLoginNameValidators["lgName"]==value)){
						    return true;
					    }
					    StaffLoginNameValidators["lgName"]=null;
					    var fdId = document.getElementsByName("fdId")[0].value;
					    var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=sysOrgPersonService&loginName="
								+ value + "&fdId=" + fdId + "&checkType=invalid&date=" + new Date());
						var result = _CheckUnique(url);
						if (!result){
							return false;
						}else{
							return true;	
						}
			    }
			},			
			
			'normalLoginName':{
			
				error:errorMsg,
				test:function(value){
					
					var pattern;
					
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
				}
			}
	}
	_validator.addValidators(MobileNoValidators);
	_validator.addValidators(StaffNoValidators);
	_validator.addValidators(StaffLoginNameValidators);
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
	
	
	_validator.addValidator("workPhone", "<bean:message key='hrStaffPersonInfo.phone.err' bundle='hr-staff' />", function(v, e, o) {
		if(v=="") {
			return true;
		}
		if(/^0\d{2,3}-?\d{7,8}$/.test(v)) {
		     return true; // 校验通过
		 }
		return false;
	});
	
	
	/* _validator.addValidator("idCard", "<bean:message key='hrStaffPersonInfo.idCard.err' bundle='hr-staff' />", function(v, e, o) {
		if(v=="") {
			return true;
		}
		if(idCardNoCheck()) {
		     return true; // 校验通过
		}
		return false;
	});
	
	// 验证身份证格式
	function idCardNoCheck(card)  
	{  
	   // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X  
	   var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
	   var fdIdCard=$("input[name$='fdIdCard']").val();
	   
	   if(reg.test(fdIdCard) == false)  
	   {  
		   
	      	return  false;  
	   }  
	   
	   		return  true;  
	} */ 
	
	 _validator.addValidator("validateCard", "<bean:message key='hrStaffPersonInfo.idCard.err' bundle='hr-staff' />", function(v, e, o) {
		 var idCardFlag = false;
		 if(v!=undefined && v.length>0){
			 idCardFlag=IsHKID(v);
			 if(!idCardFlag){
				 idCardFlag= isCardId(v);
			 }
		 }
		 return idCardFlag;
	 });
	 
	 function isCardId(v){
		 var idCardFlag = false;
		 if(v!=undefined && v.length == 18){
			var url = Com_SetUrlParameter(Com_Parameter.ContextPath+"sys/profile/sysCommonSensitiveConfig.do","method","isIdCardNo");
			var data ={idCardNo:encodeURIComponent(v)};
			LUI.$.ajax({
				url: url,
				type: 'post',
				dataType: 'json',
				async: false,
				data: data,
				success: function(data, textStatus, xhr) {
					idCardFlag = data;
				}
			});
		}
		return idCardFlag;
	 }
	
	//香港身份证验证
	function IsHKID(str) {
	    var strValidChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

	    // basic check length
	    if (str.length < 8)
	        return false;
	  
	    // handling bracket
	    if (str.charAt(str.length-3) == '(' && str.charAt(str.length-1) == ')')
	        str = str.substring(0, str.length - 3) + str.charAt(str.length -2);

	    // convert to upper case
	    str = str.toUpperCase();
	    console.log("str:"+str);

	    // regular expression to check pattern and split
	    var hkidPat = /^([A-Z]{1,2})([0-9]{6})([A0-9])$/;
	    var matchArray = str.match(hkidPat);

	    // not match, return false
	    if (matchArray == null)
	        return false;
	    return true;
	 } 
	
	_validator.addValidator("workTime", "<bean:message key='hrStaffPersonInfo.workTime.err.plus' bundle='hr-staff' />", function(v, e, o) {
		if(v=="") {
			return true;
		}
		if(workTimeCheck(v)) {
		     return true; // 校验通过
		}
		return false;
	});
	_validator.addValidator("workTimeAndTrialExpirationTime", "<bean:message key='hrStaffPersonInfo.trialExpirationTime.err.plus' bundle='hr-staff' />", function(v, e, o) {
		if(v=="") {
			return true;
		}
		if(workTimeAndTrialExpirationTimeCheck(v)) {
		     return true; // 校验通过
		}
		return false;
	});
	//参加工作时间一定是小于等于到本单位时间，
	function workTimeCheck(workTime){
		var fdworkTime=Com_GetDate(workTime);
			 workTime=fdworkTime.getTime();
		
		var fdTimeOfEnterprise=$("input[name$='fdTimeOfEnterprise']").val();
		var timeOfEnterprise=Com_GetDate(fdTimeOfEnterprise);
		timeOfEnterprise=timeOfEnterprise.getTime();
		
		 var fdTrialExpirationTime=$("input[name$='fdTrialExpirationTime']").val();
		 var trialExpirationTime=Com_GetDate(fdTrialExpirationTime);
		 trialExpirationTime=trialExpirationTime.getTime();
		if(fdTimeOfEnterprise != "" && workTime>timeOfEnterprise){
			console.log(fdTimeOfEnterprise);
			alert(333);
			return false;
		}
		return true;
	}
	
	//试用到期时间项一定是大于或等于参加工作时间
	function workTimeAndTrialExpirationTimeCheck(workTime){
		var fdworkTime=Com_GetDate(workTime);
		workTime=fdworkTime.getTime();
		
		var fdTrialExpirationTime=$("input[name$='fdTrialExpirationTime']").val();
		var trialExpirationTime=Com_GetDate(fdTrialExpirationTime);
		trialExpirationTime=trialExpirationTime.getTime();
		if(fdTrialExpirationTime !="" && workTime>trialExpirationTime){
			return false;
		}
		return true;
	}
	
	_validator.addValidator("timeOfEnterprise", "<bean:message key='hrStaffPersonInfo.timeOfEnterprise.err.plus' bundle='hr-staff' />", function(v, e, o) {
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
		
		var fdWorkTime=$("input[name$='fdWorkTime']").val();
		
		var workTime=Com_GetDate(fdWorkTime);
		workTime=workTime.getTime();
		if(timeOfEnterprise>=workTime)
			return true;
		else
			return false;
		
		/*var fdTrialExpirationTime=$("input[name$='fdTrialExpirationTime']").val();
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
		}*/
		
	}
	
	_validator.addValidator("trialExpirationTime", "<bean:message key='hrStaffPersonInfo.trialExpirationTime.err.plus' bundle='hr-staff' />", function(v, e, o) {
		if(v=="") {
			return true;
		}
		if(trialExpirationTimeCheck(v)) {
			
		     return true; // 校验通过
		}
		return false;
	});
	_validator.addValidator("compareTime(type)", "<bean:message bundle='hr-staff' key='hrStaffPersonInfo.compareTime.error.msg' arg0='{start}' arg1='{end}' />",function(v,e,o){
		var type = o['type'];
		var fdStartDate,fdEndDate;
		if(type == 'group'){
			fdStartDate = $("[name='fdDateOfGroup']").val();
			fdEndDate = $("[name='fdDateOfParty']").val();
			o['start'] = "<bean:message bundle='hr-staff' key='hrStaffPersonInfo.fdDateOfGroup' />";
			o['end'] = "<bean:message bundle='hr-staff' key='hrStaffPersonInfo.fdDateOfParty' />";
		}
		if(type == 'entry'){
			fdStartDate = $("[name='fdEntryTime']").val();
			fdEndDate = $("[name='fdPositiveTime']").val();
			o['start'] = "<bean:message bundle='hr-staff' key='hrStaffPersonInfo.fdEntryTime' />";
			o['end'] = "<bean:message bundle='hr-staff' key='hrStaffPersonInfo.fdPositiveTime' />";
		}
		if(type == 'resignation'){
			fdStartDate = $("[name='fdEntryTime']").val();
			fdEndDate = $("[name='fdResignationDate']").val();
			o['start'] = "<bean:message bundle='hr-staff' key='hrStaffPersonInfo.fdEntryTime' />";
			o['end'] = "<bean:message bundle='hr-staff' key='hrStaffPersonInfo.fdResignationDate' />";
		}
		if(type == 'work'){
			 fdEndDate = $("[name='fdEntryTime']").val();
			 fdStartDate = $("[name='fdWorkTime']").val();
			o['end'] = "<bean:message bundle='hr-staff' key='hrStaffPersonInfo.fdEntryTime' />";
			o['start'] = "<bean:message bundle='hr-staff' key='hrStaffPersonInfo.fdWorkTime' />";
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
		
		var fdWorkTime=$("input[name$='fdWorkTime']").val();
		var workTime=Com_GetDate(fdWorkTime);
		workTime=workTime.getTime();
		
		var fdTimeOfEnterprise=$("input[name$='fdTimeOfEnterprise']").val();
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
	function enableRehire(obj){
		if(obj.value == 'true'){
			$("#rehireTime").show();
		}else{
			$("input[name='fdRehireTime']").val('');
			$("#rehireTime").hide();
		}
	}
	window.workYearChange=function(value,obj){
		if(!value){
			return;
		}
		var oldValue=$("input[name=fdWorkingYearsValue]").val();
		oldValue=isNaN(parseInt(oldValue))?0:parseInt(oldValue);
		var year = $("input[name=fdWorkingYearsYear]").val();
		var month = $("input[name=fdWorkingYearsMonth]").val();
		if(year == "" && month == ""){
			$("input[name='fdWorkingYearsDiff']").val(0);
		}else{
			year=isNaN(parseInt(year))?0:parseInt(year);
			month=isNaN(parseInt(month))?0:parseInt(month);
			var result = year*12+month-oldValue;
			$("input[name='fdWorkingYearsDiff']").val(result);
		}
	}
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
	
	window.uninterruptedWorkTimeChange=function(value,obj){
		if(!value){
			return;
		}
		var oldValue=$("input[name=fdUninterruptedWorkTimeValue]").val();
		oldValue=isNaN(parseInt(oldValue))?0:parseInt(oldValue);
		var year = $("input[name=fdUninterruptedWorkTimeYear]").val();
		var month = $("input[name=fdUninterruptedWorkTimeMonth]").val();
		if(year == "" && month == ""){
			$("input[name='fdWorkTimeDiff']").val(0);
		}else{
			year=isNaN(parseInt(year))?0:parseInt(year);
			month=isNaN(parseInt(month))?0:parseInt(month);
			var result = year*12+month-oldValue;
			$("input[name='fdWorkTimeDiff']").val(result);
		}
	}
	seajs.use(['lui/dialog','lui/jquery'],function(dialog,$){
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
					$("input[name=fdWorkingYearsYear]").val(diffYear);
					$("input[name=fdWorkingYearsMonth]").val(diffMonth);
					$("input[name='fdWorkingYearsValue']").val(diffMonths);
					$("input[name='fdWorkingYearsDiff']").val(0);
				}else{
					$("input[name='fdWorkingYearsValue']").val(fdWorkingYearsValue);
					$("input[name=fdWorkingYearsYear]").val(fdWorkingYearsYear);
					$("input[name=fdWorkingYearsMonth]").val(fdWorkingYearsMonth);
					$("input[name='fdWorkingYearsDiff']").val(fdWorkingYearsDiff);
				}
				$("input[name=fdWorkingYearsYear]").removeAttr("disabled");
				$("input[name=fdWorkingYearsMonth]").removeAttr("disabled");
			}else{
				$("input[name=fdWorkingYearsYear]").val("");
				$("input[name=fdWorkingYearsMonth]").val("");
				$("input[name=fdWorkingYearsYear]").prop("disabled","disabled");
				$("input[name=fdWorkingYearsMonth]").prop("disabled","disabled");
				$("input[name='fdWorkingYearsValue']").val(0);
				$("input[name='fdWorkingYearsDiff']").val(0);
			}
		}
		
		window.workTimeChange=function(value,obj){
			if(value){
				var fdWorkTime = '${hrStaffPersonInfoForm.fdWorkTime}';
				var fdUninterruptedWorkTimeValue = '${hrStaffPersonInfoForm.fdUninterruptedWorkTimeValue}';
				var fdWorkTimeDiff = '${hrStaffPersonInfoForm.fdWorkTimeDiff}';
				var fdUninterruptedWorkTimeYear = '${hrStaffPersonInfoForm.fdUninterruptedWorkTimeYear}';
				var fdUninterruptedWorkTimeMonth = '${hrStaffPersonInfoForm.fdUninterruptedWorkTimeMonth}';
				console.log("fdUninterruptedWorkTimeValue:"+fdUninterruptedWorkTimeValue);
				console.log("fdWorkTimeDiff");
				if(fdWorkTime!=value){
					var diffMonths = getDiffMonths(value);
					var diffYear = parseInt(diffMonths/12);
					var diffMonth = diffMonths % 12;
					$("input[name=fdUninterruptedWorkTimeYear]").val(diffYear);
					$("input[name=fdUninterruptedWorkTimeMonth]").val(diffMonth);
					$("input[name='fdUninterruptedWorkTimeValue']").val(diffMonths);
					$("input[name='fdWorkTimeDiff']").val(0);
				}else{
					$("input[name='fdUninterruptedWorkTimeValue']").val(fdUninterruptedWorkTimeValue);
					$("input[name=fdUninterruptedWorkTimeYear]").val(fdUninterruptedWorkTimeYear);
					$("input[name=fdUninterruptedWorkTimeMonth]").val(fdUninterruptedWorkTimeMonth);
					$("input[name='fdWorkTimeDiff']").val(fdWorkTimeDiff);
				}
				$("input[name=fdUninterruptedWorkTimeYear]").removeAttr("disabled");
				$("input[name=fdUninterruptedWorkTimeMonth]").removeAttr("disabled");
			}else{
				$("input[name=fdUninterruptedWorkTimeYear]").val("");
				$("input[name=fdUninterruptedWorkTimeMonth]").val("");
				$("input[name=fdUninterruptedWorkTimeYear]").prop("disabled","disabled");
				$("input[name=fdUninterruptedWorkTimeMonth]").prop("disabled","disabled");
				$("input[name='fdUninterruptedWorkTimeValue']").val(0);
				$("input[name='fdWorkTimeDiff']").val(0);
			}
		}

		window.entryTimeChange=function(value,obj){
			if(value){
				var fdEntryTime = '${hrStaffPersonInfoForm.fdEntryTime}';
				console.log(fdEntryTime);
				console.log("-------------------------------");
				console.log(value);
				var fdWorkingYearsValue = '${hrStaffPersonInfoForm.fdWorkingYearsValue}';
				var fdWorkingYearsDiff = '${hrStaffPersonInfoForm.fdWorkingYearsDiff}';
				var fdWorkingYearsYear = '${hrStaffPersonInfoForm.fdWorkingYearsYear}';
				var fdWorkingYearsMonth = '${hrStaffPersonInfoForm.fdWorkingYearsMonth}';
				if(fdEntryTime!=value){
					var diffMonths = getDiffMonths(value);
					var diffYear = parseInt(diffMonths/12);
					var diffMonth = diffMonths % 12;
					$("input[name=fdWorkingYearsYear]").val(diffYear);
					$("input[name=fdWorkingYearsMonth]").val(diffMonth);
					$("input[name='fdWorkingYearsValue']").val(diffMonths);
					$("input[name='fdWorkingYearsDiff']").val(0);
				}else{
					$("input[name='fdWorkingYearsValue']").val(fdWorkingYearsValue);
					$("input[name=fdWorkingYearsYear]").val(fdWorkingYearsYear);
					$("input[name=fdWorkingYearsMonth]").val(fdWorkingYearsMonth);
					$("input[name='fdWorkingYearsDiff']").val(fdWorkingYearsDiff);
				}
				$("input[name=fdWorkingYearsYear]").removeAttr("disabled");
				$("input[name=fdWorkingYearsMonth]").removeAttr("disabled");
			}else{
				$("input[name=fdWorkingYearsYear]").val("");
				$("input[name=fdWorkingYearsMonth]").val("");
				$("input[name=fdWorkingYearsYear]").prop("disabled","disabled");
				$("input[name=fdWorkingYearsMonth]").prop("disabled","disabled");
				$("input[name='fdWorkingYearsValue']").val(0);
				$("input[name='fdEntryTimeDiff']").val(0);
			}
			console.log(333);
			console.log(331);
			var arr = value.split('-');
			console.log(arr.join('-'));
			var fdAffiliatedCompany = $("select[name='fdAffiliatedCompany']").val();
			console.log(fdAffiliatedCompany);
			var fdOrgRankId = $("select[name='fdOrgRankId']")[0].options[$("select[name='fdOrgRankId']")[0].selectedIndex].text;
			var fdAffiliatedCompany = $("select[name='fdAffiliatedCompany']").val();
			console.log(fdOrgRankId);
			console.log(fdAffiliatedCompany);
			var fdStaffType = $("select[name='fdStaffType']").val();
			if(fdStaffType=="在职派遣"){
				var month = parseInt(arr[1])+2;
				if(month<10)
					arr[1]='0'+month;
				else
					arr[1]=month;
				if(parseInt(arr[1])>12){
					arr[0]=parseInt(arr[0])+1;
					arr[1]='0'+parseInt(arr[1])%12;
				}
				$("input[name='fdProposedEmploymentConfirmationDate']").val(arr.join('-'));
			}else if(fdStaffType=="在职正编"){
				debugger;
				var arr1 = ['M1','M2','M3','M4','M5','M6','M7','P1','P2','P3','P4','P5','P6','01','02','03','04','05','06','07','08','09','010'];
				var arr2 = ['M8','M9','M10','M11','M12','M13','M14','M15','M16','M17','M18','M19','M20','P7','P8','P9','P10','P11','P12','P13','P14','P15','P16','P17','P18','P19','P20'];
				if(arr1.indexOf(fdOrgRankId)!=-1){
					var month = parseInt(arr[1])+3;
					if(month<10)
						arr[1]='0'+month;
					else
						arr[1]=month;
					if(parseInt(arr[1])>12){
						arr[0]=parseInt(arr[0])+1;
						arr[1]='0'+parseInt(arr[1])%12;
					}
					$("input[name='fdProposedEmploymentConfirmationDate']").val(arr.join('-'));
				}else if(arr2.indexOf(fdOrgRankId)!=-1||fdOrgRankId.indexOf('S')!= -1){
					var month = parseInt(arr[1])+6;
					if(month<10)
						arr[1]='0'+month;
					else
						arr[1]=month;
					if(parseInt(arr[1])>12){
						arr[0]=parseInt(arr[0])+1;
						arr[1]='0'+parseInt(arr[1])%12;
					}
					$("input[name='fdProposedEmploymentConfirmationDate']").val(arr.join('-'));
				}
			}else if(fdStaffType=="在职实习生"){
				$("input[name='fdProposedEmploymentConfirmationDate']").val('');
			}
				var arr3= ['圣菲可派遣','佳禧派遣','勤劳派遣','广西新伯乐'];
				if(isStrInArray(fdAffiliatedCompany,arr3)){
					$("input[name='fdProbationPeriod']").val(2);
			}
		if(fdAffiliatedCompany=="海格物流" || fdAffiliatedCompany=="广西海格" && fdStaffType=="在职实习生") {
			$("input[name='fdProbationPeriod']").val('');
		}
			if(fdAffiliatedCompany=="海格正编"){
				var arr1 = ['M1','M2','M3','M4','M5','M6','M7','P1','P2','P3','P4','P5','P6','01','02','03','04','05','06','07','08','09','010'];
				var arr2 = ['M8','M9','M10','M11','M12','M13','M14','M15','M16','M17','M18','M19','M20','P7','P8','P9','P10','P11','P12','P13','P14','P15','P16','P17','P18','P19','P20'];
				if(arr1.indexOf(fdOrgRankId)){
					$("input[name='fdProbationPeriod']").val(3);
				}else if(arr2.indexOf(fdOrgRankId)||fdOrgRankId.indexOf('S')!= -1){
					$("input[name='fdProbationPeriod']").val(6);
				}
			}
			
			var arr3 = ['圣菲可派遣','佳禧派遣','勤劳派遣','广西新伯乐'];
			if(isStrInArray(fdAffiliatedCompany,arr3)){
				$("select[name='fdProbationPeriod']").val(2);
			}
		}
		window.setDisabled=function(value,yname,mname){
			if(value){
				if(yname){
					$("input[name="+yname+"]").removeAttr("disabled");
				}
				if(mname){
					$("input[name="+mname+"]").removeAttr("disabled");
				}
			}else{
				if(yname){
					$("input[name="+yname+"]").prop("disabled","disabled");
				}
				if(mname){
					$("input[name="+mname+"]").prop("disabled","disabled");
				}
			}
		}
		LUI.ready(function(){
			setDisabled("${hrStaffPersonInfoForm.fdWorkTime}","fdUninterruptedWorkTimeYear","fdUninterruptedWorkTimeMonth");
			setDisabled("${hrStaffPersonInfoForm.fdTimeOfEnterprise}","fdWorkingYearsYear","fdWorkingYearsMonth");
		})
	})
	function setCities1(_this){
			var text = _this.options[_this.selectedIndex].text;
			var name = _this.name;
			if($(_this).val()==810000){
			var validate = $("input[name='fdMobileNo']").attr("validate");
				$("input[name='fdMobileNo']").attr("validate",validate.replace(/phoneNumber/g,"phoneNumber1"));
				$("input[name='"+name.slice(0,-2)+'Name'+"']").val(_this.options[_this.selectedIndex].text);
			}else{
				var validate = $("input[name='fdMobileNo']").attr("validate");
					$("input[name='fdMobileNo']").attr("validate",validate.replace(/phoneNumber1/g,"phoneNumber"));
					$("input[name='"+name.slice(0,-2)+'Name'+"']").val(_this.options[_this.selectedIndex].text);
			}
			var sel = ''+$(_this).parent().next().next().find('select')[0].name;
			var json = {};
			json[name] = $(_this).val();
			json ["prevFieldName"] = name;
			json ["fieldName"] = sel;
			console.log($(_this).val());
			$.ajax({
                url: '${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=buildCitiesHtml',
                data: json,
                dataType: 'json',
                type: 'POST',
                async:false,
                success: function(data) {
					if(data){
						if(data.html){
							$(_this).parent().next().next().empty();
							$(_this).parent().next().next().append(data.html);
						}
					}
                },
                error: function(req) {
					$("#staffLevingBox").empty();
                }
            });
		}
		function setCities(_this){
			var text = _this.options[_this.selectedIndex].text;
			var name = _this.name;
			$("input[name='"+name.slice(0,-2)+'Name'+"']").val(_this.options[_this.selectedIndex].text);
			var sel = ''+$(_this).parent().next().next().find('select')[0].name;
			var json = {};
			json[name] = $(_this).val();
			json ["prevFieldName"] = name;
			json ["fieldName"] = sel;
			console.log(name);
			$.ajax({
                url: '${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=buildCitiesHtml',
                data: json,
                dataType: 'json',
                type: 'POST',
                async:false,
                success: function(data) {
					if(data){
						if(data.html){
							$(_this).parent().next().next().empty();
							$(_this).parent().next().next().append(data.html);
						}
					}
                },
                error: function(req) {
					$("#staffLevingBox").empty();
                }
            });
		}
		function setAreasValue(_this){
			var name=_this.name; 
			$("input[name='"+name.slice(0,-2)+'Name'+"']").val(_this.options[_this.selectedIndex].text);
		}
		function setAreas(_this){
			var name=_this.name; 
			$("input[name='"+name.slice(0,-2)+'Name'+"']").val(_this.options[_this.selectedIndex].text);
			var sel = $(_this).parent().parent().next().children().next().find('select')[0].name;
			var json = {};
			json[name] = $(_this).val();
			json ["prevFieldName"] = name;
			json ["fieldName"] = sel;
			
			console.log(json);
			$.ajax({
                url: '${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=buildAreasHtml',
                data: json,
                dataType: 'json',
                type: 'POST',
                async:false,
                success: function(data) {
					if(data){
						if(data.html){
							$(_this).parent().parent().next().children(":first").next().empty();
							$(_this).parent().parent().next().children(":first").next().append(data.html);
						}
					}
                },
                error: function(req) {
					$("#staffLevingBox").empty();
                }
            });
		}
		window.fdReportLeaderIdChange = function (v){
			console.log(v[0]);
			console.log(this);
			$.ajax({
                url: '${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=getFdDirectSuperiorJobNumber',
                data: {"fdId":v[0]},
                dataType: 'json',
                type: 'POST',
                async:false,
                success: function(data) {
                	console.log(data);
					if(data){
						if(data.result){
							$("input[name='fdDirectSuperiorJobNumber']").val(data.result[0]['No']);}
					}
                },
                error: function(req) {
					$("#staffLevingBox").empty();
                }
            });
		}
		function orgRankChange(value,_this){
			$("input[name='fdOrgRankName']").val(_this.options[_this.selectedIndex].text);
			var arr = $("input[name='fdEntryTime']").val().split('-');
			if(arr[0]){
			console.log(arr.join('-'));
			var fdOrgRankId = _this.options[_this.selectedIndex].text;
			console.log(fdOrgRankId);
			var fdStaffType = $("select[name='fdStaffType']").val();
			if(fdStaffType=="在职派遣"){
				var month = parseInt(arr[1])+2;
				if(month<10)
					arr[1]='0'+month;
				else
					arr[1]=month;
				if(parseInt(arr[1])>12){
					arr[0]=parseInt(arr[0])+1;
					arr[1]='0'+parseInt(arr[1])%12;
				}
			}else if(fdStaffType=="在职正编"){
				var arr1 = ['M1','M2','M3','M4','M5','M6','M7','P1','P2','P3','P4','P5','P6','01','02','03','04','05','06','07','08','09','010'];
				var arr2 = ['M8','M9','M10','M11','M12','M13','M14','M15','M16','M17','M18','M19','M20','P7','P8','P9','P10','P11','P12','P13','P14','P15','P16','P17','P18','P19','P20'];
				if(arr1.indexOf(fdOrgRankId)){
					var month = parseInt(arr[1])+3;
					if(month<10)
						arr[1]='0'+month;
					else
						arr[1]=month;
				}else if(isStrInArray(fdOrgRankId,arr2)||fdOrgRankId.indexOf('S')!= -1){
					var month = parseInt(arr[1])+6;
					if(month<10)
						arr[1]='0'+month;
					else
						arr[1]=month;
				}
			}

			if(parseInt(arr[1])>12){
				arr[0]=parseInt(arr[0])+1;
				arr[1]='0'+parseInt(arr[1])%12;
			}
			$("input[name='fdProposedEmploymentConfirmationDate']").val(arr.join('-'));
			}
		}
		function isStrInArray(str, arr) {

			let n = arr.length;

			for (let i = 0; i < n; i++) {

			if (arr[i] == str) {

			return true;

			}

			}

			return false;

			}
		function staffTypeChange(value){
			var arr = $("input[name='fdEntryTime']").val().split('-');
			if(arr[0]){
			console.log(arr.join('-'));
			var fdOrgRankId = $("select[name='fdOrgRankId']")[0].options[$("select[name='fdOrgRankId']")[0].selectedIndex].text;
			console.log(fdOrgRankId);
			var fdStaffType = value;
			if(fdStaffType=="在职派遣"){
				arr[1]=parseInt(arr[1])+2;
			}else if(fdStaffType=="在职正编"){
				var arr1 = ['M1','M2','M3','M4','M5','M6','M7','P1','P2','P3','P4','P5','P6','01','02','03','04','05','06','07','08','09','010'];
				var arr2 = ['M8','M9','M10','M11','M12','M13','M14','M15','M16','M17','M18','M19','M20','P7','P8','P9','P10','P11','P12','P13','P14','P15','P16','P17','P18','P19','P20'];
				if(isStrInArray(fdOrgRankId,arr1)){
					console.log("fdOrgRankId"+fdOrgRankId);
					var month = parseInt(arr[1])+3;
					if(month<10)
						arr[1]='0'+month;
					else
						arr[1]=month;
					if(parseInt(arr[1])>12){
						arr[0]=parseInt(arr[0])+1;
						arr[1]='0'+parseInt(arr[1])%12;
					}
						var str = arr.join('-');
					$("input[name='fdProposedEmploymentConfirmationDate']").val(str);
				}else if(isStrInArray(fdOrgRankId,arr2)||fdOrgRankId.indexOf('S')!= -1){
					console.log("1123"+fdOrgRankId.indexOf('S'));
					var month = parseInt(arr[1])+6;
					if(month<10)
						arr[1]='0'+month;
					else
						arr[1]=month;
					if(parseInt(arr[1])>12){
						arr[0]=parseInt(arr[0])+1;
						arr[1]='0'+parseInt(arr[1])%12;
					}
						console.log(arr.join('-'));
						var str = arr.join('-');
					$("input[name='fdProposedEmploymentConfirmationDate']").val(str);
				}
			}else if(fdStaffType=="在职实习生"){
				$("input[name='fdProposedEmploymentConfirmationDate']").val('');
			}
			}
		}

		function affiliatedCompanyChange(value){
			if($("input[name='fdEntryTime']").val()){
				var arr = ['圣菲可派遣','佳禧派遣','勤劳派遣','广西新伯乐'];
				var fdStaffType = $("select[name='fdStaffType']").val();
				if(isStrInArray(value,arr)){
				$("input[name='fdProbationPeriod']").val(2);
				}else if(value="海格物流"||value=="广西海格"&&fdStaffType.include("实习生")){
					$("input[name='fdProbationPeriod']").val('');
				}else if(value=="海格正编"){
					debugger;
					var arr1 = ['M1','M2','M3','M4','M5','M6','M7','P1','P2','P3','P4','P5','P6','01','02','03','04','05','06','07','08','09','010'];
					var arr2 = ['M8','M9','M10','M11','M12','M13','M14','M15','M16','M17','M18','M19','M20','P7','P8','P9','P10','P11','P12','P13','P14','P15','P16','P17','P18','P19','P20'];
					if(isStrInArray(fdOrgRankId,arr1)){
						$("input[name='fdProbationPeriod']").val(3);
					}else if(arr2.indexOf(value)!=-1||value.indexOf('S')!= -1){
						$("input[name='fdProbationPeriod']").val(6);
					}
				}
			}
		}
		
		function nameChange(value){
			var fdId = document.getElementsByName("fdId")[0].value;
			$.ajax({
                url: '${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=getStrPinYin',
                data: {"str":value},
                dataType: 'json',
                type: 'POST',
                async:false,
                success: function(data) {
                	console.log(data.result[0]['str']);
					if(data){
						if(data.result){
							value=data.result[0].str;
							if(!value)return true;
							var result = false;
							var kj = 1;
							let res='';
							let str = value.split("");
							for(let i = str.length-1; i >= 0; i--){
							      var el = str[i];
							      if(+el){
							      }else{
							    	  res+=el;
							      }
							   };
							   console.log(res);
							   value=res.split("").reverse().join("");
							while(!result){
							var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=sysOrgPersonService&loginName="
									+ value + "&fdId=" + fdId + "&checkType=unique&date=" + new Date());
							var result = _CheckUnique(url);
							if (!result) {
								value+=kj;
								kj++;
							}
							}
							$("input[name='fdLoginName']").val(value);}
					}
                },
                error: function(req) {
					$("#staffLevingBox").empty();
                }
            });
		}
</script>
