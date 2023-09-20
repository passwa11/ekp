<%@ page language="java" pageEncoding="UTF-8"%>

<script type="text/javascript">
	var _validator = $KMSSValidation(document.forms['hrStaffEntryForm']);
	// 增加一个字符串的startsWith方法
	function startsWith(value, prefix) {
		return value.slice(0, prefix.length) === prefix;
	}
    //根据身份证号自动填充出生日期
    function idCardChange(value) {
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
                var fdDateOfBirth = $("input[name='fdDateOfBirth']")[0].value;
                if(fdDateOfBirth == ""){
                    $("input[name='fdDateOfBirth']").val(birth);
                }
            }
            //偶数为女性，奇数为男性
            if ((sexcode & 1) == 0) {
                $("input[name='fdSex'][value='F']").prop("checked",true);
            } else {
                $("input[name='fdSex'][value='M']").prop("checked",true);
            }
        }
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
					var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=hrStaffEntryService&mobileNo="
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
					var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=hrStaffEntryService&staffNo="
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
				var fdId = '${param.fdId}';
				var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=hrStaffEntryService&loginName="
						+ value + "&fdId=" + fdId + "&checkType=unique&date=" + new Date());
				var result = _CheckUnique(url);
				if (result=="false") 
					return false;
				return true;
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
	
	
	_validator.addValidator("idCard", "<bean:message key='hrStaffPersonInfo.idCard.err' bundle='hr-staff' />", function(v, e, o) {
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
	}  
	
	_validator.addValidator("workTime", "<bean:message key='hrStaffPersonInfo.workTime.err' bundle='hr-staff' />", function(v, e, o) {
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
		
		var fdTimeOfEnterprise=$("input[name$='fdTimeOfEnterprise']").val();
		var timeOfEnterprise=Com_GetDate(fdTimeOfEnterprise);
		timeOfEnterprise=timeOfEnterprise.getTime();
		
		 var fdTrialExpirationTime=$("input[name$='fdTrialExpirationTime']").val();
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
	
	_validator.addValidator("timeOfEnterprise", "<bean:message key='hrStaffPersonInfo.timeOfEnterprise.err' bundle='hr-staff' />", function(v, e, o) {
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
		
		var fdTrialExpirationTime=$("input[name$='fdTrialExpirationTime']").val();
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
	
	_validator.addValidator("trialExpirationTime", "<bean:message key='hrStaffPersonInfo.trialExpirationTime.err' bundle='hr-staff' />", function(v, e, o) {
		if(v=="") {
			return true;
		}
		if(trialExpirationTimeCheck(v)) {
			
		     return true; // 校验通过
		}
		return false;
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
	
	$(function() {
		var fdHistory_vstatusLength = $('input[name="fdHistory_vstatusLength"]').val();
		for (var rowIndex = 0; rowIndex < fdHistory_vstatusLength; rowIndex++) {
			$('input[name="fdHistory_Form['+rowIndex+'].fdPostNew"]').val( $('input[name="fdHistory_Form['+rowIndex+'].fdPost"]').val());
			$('input[name="fdHistory_Form['+rowIndex+'].fdNameNew"]').val( $('input[name="fdHistory_Form['+rowIndex+'].fdName"]').val());
			$('input[name="fdHistory_Form[' + rowIndex + '].fdStartDateNew"]').val($('input[name="fdHistory_Form['+rowIndex+'].fdStartDate"]').val());
			$('input[name="fdHistory_Form[' + rowIndex + '].fdEndDateNew"]').val($('input[name="fdHistory_Form['+rowIndex+ '].fdEndDate"]').val());
		}
		
		var fdEducations_vstatusLength = $('input[name="fdEducations_vstatusLength"]').val();
		for (var rowIndex = 0; rowIndex < fdEducations_vstatusLength; rowIndex++) {
			$('input[name="fdEducations_Form['+rowIndex+'].fdNameNew"]').val( $('input[name="fdEducations_Form['+rowIndex+'].fdName"]').val());
			$('input[name="fdEducations_Form['+rowIndex+'].fdMajorNew"]').val( $('input[name="fdEducations_Form['+rowIndex+'].fdMajor"]').val());
			$('input[name="fdEducations_Form['+rowIndex+'].fdAcademicNew"]').val( $('input[name="fdEducations_Form['+rowIndex+'].fdAcadeRecordName"]').val());
			$('input[name="fdEducations_Form[' + rowIndex + '].fdEntranceDateNew"]').val($('input[name="fdEducations_Form['+rowIndex+'].fdEntranceDate"]').val());
			$('input[name="fdEducations_Form[' + rowIndex + '].fdGraduationDateNew"]').val($('input[name="fdEducations_Form['+rowIndex+ '].fdGraduationDate"]').val());
		}
		
		var fdTrains_vstatusLength = $('input[name="fdTrains_vstatusLength"]').val();
		for (var rowIndex = 0; rowIndex < fdTrains_vstatusLength; rowIndex++) {
			$('input[name="fdTrains_Form['+rowIndex+'].fdTrainCompanyNew"]').val( $('input[name="fdTrains_Form['+rowIndex+'].fdTrainCompany"]').val());
			$('input[name="fdTrains_Form['+rowIndex+'].fdNameNew"]').val( $('input[name="fdTrains_Form['+rowIndex+'].fdName"]').val());
			$('input[name="fdTrains_Form[' + rowIndex + '].fdStartDateNew"]').val($('input[name="fdTrains_Form['+rowIndex+'].fdStartDate"]').val());
			$('input[name="fdTrains_Form[' + rowIndex + '].fdEndDateNew"]').val($('input[name="fdTrains_Form['+rowIndex+ '].fdEndDate"]').val());
		}
		
		var fdCertificate_vstatusLength = $('input[name="fdCertificate_vstatusLength"]').val();
		for (var rowIndex = 0; rowIndex < fdCertificate_vstatusLength; rowIndex++) {
			$('input[name="fdCertificate_Form['+rowIndex+'].fdIssuingUnitNew"]').val( $('input[name="fdCertificate_Form['+rowIndex+'].fdIssuingUnit"]').val());
			$('input[name="fdCertificate_Form['+rowIndex+'].fdNameNew"]').val( $('input[name="fdCertificate_Form['+rowIndex+'].fdName"]').val());
			$('input[name="fdCertificate_Form[' + rowIndex + '].fdIssueDateNew"]').val($('input[name="fdCertificate_Form['+rowIndex+'].fdIssueDate"]').val());
			$('input[name="fdCertificate_Form[' + rowIndex + '].fdInvalidDateNew"]').val($('input[name="fdCertificate_Form['+rowIndex+ '].fdInvalidDate"]').val());
		}
		
		var fdRewardsPunishments_vstatusLength = $('input[name="fdRewardsPunishments_vstatusLength"]').val();
		for (var rowIndex = 0; rowIndex < fdRewardsPunishments_vstatusLength; rowIndex++) {
			$('input[name="fdRewardsPunishments_Form['+rowIndex+'].fdNameNew"]').val( $('input[name="fdRewardsPunishments_Form['+rowIndex+'].fdName"]').val());
			$('input[name="fdRewardsPunishments_Form[' + rowIndex + '].fdDateNew"]').val($('input[name="fdRewardsPunishments_Form['+rowIndex+ '].fdDate"]').val());
		}
		
		var fdfamilys_vstatusLength = $('input[name="fdfamilys_vstatusLength"]').val();
		for (var rowIndex = 0; rowIndex < fdfamilys_vstatusLength; rowIndex++) {
				$('input[name="fdfamily_Form['+rowIndex+'].fdRelatedNew"]').val($('input[name="fdfamily_Form['+rowIndex+'].fdRelated"]').val());
				$('input[name="fdfamily_Form['+rowIndex+'].fdNameNew"]').val($('input[name="fdfamily_Form['+rowIndex+'].fdName"]').val());
				$('input[name="fdfamily_Form['+rowIndex+'].fdOccupationNew"]').val($('input[name="fdfamily_Form['+rowIndex+'].fdOccupation"]').val());
				$('input[name="fdfamily_Form['+rowIndex+'].fdCompanyNew"]').val($('input[name="fdfamily_Form['+rowIndex+'].fdCompany"]').val());
				$('input[name="fdfamily_Form['+rowIndex+'].fdConnectNew"]').val($('input[name="fdfamily_Form['+rowIndex+'].fdConnect"]').val());
				$('input[name="fdfamily_Form['+rowIndex+'].fdMemoNew"]').val($('input[name="fdfamily_Form['+rowIndex+'].fdMemo"]').val());
		}
	});

	/*******************************************
	 * 添加工作经历 
	 *******************************************/

	function HR_AddRatifyEntryNew(form, element) {
		clearnValue(form, element);
		updateRequired(false);
		var newrow = DocList_AddRow(form);
		var rowIndex = $(newrow)[0].rowIndex;
		if ("TABLE_DocList_fdHistory_Form" == form) {
			$('#fdOOrderHistory' + rowIndex).html(rowIndex);
		} else if ("TABLE_DocList_fdEducations_Form" == form) {
			$('#fdOOrderEducations' + rowIndex).html(rowIndex);
		} else if ("TABLE_DocList_fdTrains_Form" == form) {
			$('#fdOOrderTrains' + rowIndex).html(rowIndex);
		} else if ("TABLE_DocList_fdCertificate_Form" == form) {
			$('#fdOOrderCertificate' + rowIndex).html(rowIndex);
		} else if ("TABLE_DocList_fdRewardsPunishments_Form" == form) {
			$('#fdOOrderRewardsPunishments' + rowIndex).html(rowIndex);
		}else if ("TABLE_DocList_fdfamily_Form" == form) {
			$('#fdOOrderfdfamilys' + rowIndex).html(rowIndex);
		}
		$(newrow).bind(
				'click',
				customClickRow(newrow, "add", $("#" + form + " tr:first"),
						form, element));
		DocList_DeleteRow(newrow);
	}

	/**
     * 因为submit会验证弹出层的表单。
     * 该方法处理 必填和不必填的切换
     */
    function updateRequired(isDel){
        var mustNameArr=["companyName","fdStartDateHistory","expName"
            ,"fdMajorName"
            ,"fdEntranceDate"
            ,"certifiName"
            ,"fdIssuingUnit"
            ,"fdIssueDate"
            ,"rewPuniName"
            ,"fdRelated"
            ,"fdFamilyName"
        ];

        for(var i in mustNameArr){
            var mustNameElement = $("[name='"+mustNameArr[i]+"']");
            if(mustNameElement && mustNameElement.length > 0){
                var element =$(mustNameElement[0]);
                var validate = element.attr('validate');
                if(isDel){
                    var reg = new RegExp("required","g");//g,表示全部替换。
                    validate =  validate.replace(reg,"");
                }else{
                    if(validate.indexOf('required') == -1){
                        validate = validate + ' required';
                    }
                }
                element.attr('validate',validate)
            }

        }
    }
	/*******************************************
	 * 删除工作经历 
	 *******************************************/
	function HR_DelRatifyEntryNew(tdName, element) {

        updateRequired(true);

		/*$("#table_of_fdHistory_detail_edit").each(function(){
			$(this).remove();
		});
		$("#table_of_fdProjects_detail_edit").each(function(){
			$(this).remove();
		});
		$("#table_of_fdEducations_detail_edit").each(function(){
			$(this).remove();
		});
		$("#table_of_fdTrains_detail_edit").each(function(){
			$(this).remove();
		});
		$("#table_of_fdCertificate_detail_edit").each(function(){
			$(this).remove();
		});
		$("#table_of_fdRewardsPunishments_detail_edit").each(function(){
			$(this).remove();
		});
		$("#table_of_fdfamily_detail_edit").each(function(){
			$(this).remove();
		});*/
	}
	/***************************************
	 * 编辑状态查看工作经历
	 **************************************/
	function HR_EditRatifyEntryNew(val, form, element) {
		clearnValue(form, element);
		$(val).parent().addClass("current");
		customClickRow($(val).parent(), "update", "", form, element);
	}
	function HR_ViewRatifyEntryNew(val, form, element) {
		clearnValue(form, element);
		$(val).addClass("current");
		customClickRow($(val),"view", "", form, element);
	}
	
	seajs.use(['lui/jquery','lui/topic'],function($,topic){
		//打开招聘岗位表单
		window.customClickRow = function(row,methodNew,rowValueOf,form,element){
			var elementVlue = $('#'+element);
			var rowOrRowValueOf;
			if(rowValueOf!=""&&rowValueOf!=null&&rowValueOf!="undefined"){
				rowOrRowValueOf=rowValueOf;
			}else{
				rowOrRowValueOf=row;
			}
			if("table_of_fdHistory_detail_edit"==element||"table_of_fdHistory_detail_view"==element){
				historyRowOpt(rowOrRowValueOf,{element:elementVlue});
			}else if("table_of_fdEducations_detail_edit"==element||"table_of_fdEducations_detail_view"==element){
				educationsRowOpt(rowOrRowValueOf,{element:elementVlue});
			}else if("table_of_fdTrains_detail_edit"==element||"table_of_fdTrains_detail_view"==element){
				trainsRowOpt(rowOrRowValueOf,{element:elementVlue});
			}else if("table_of_fdCertificate_detail_edit"==element||"table_of_fdCertificate_detail_view"==element){
				certificateRowOpt(rowOrRowValueOf,{element:elementVlue});
			}else if("table_of_fdRewardsPunishments_detail_edit"==element||"table_of_fdRewardsPunishments_detail_view"==element){
				rewardsPunishmentsRowOpt(rowOrRowValueOf,{element:elementVlue});
			}else if("table_of_fdfamily_detail_edit"==element||"table_of_fdfamily_detail_view"==element){
				familyRowOpt(rowOrRowValueOf,{element:elementVlue});
			}
			
			var rowIndex=$(row)[0].rowIndex-1;
			$('input[name="status"]').val(methodNew);
			$('input[name="rowIndex"]').val(rowIndex);
			if("TABLE_DocList_fdHistory_Form"==form){
				if("update"==methodNew){ 
					$('input[name="companyName"]').val($('input[name="fdHistory_Form['+rowIndex+'].fdName"]').val());
					$('input[name="fdPost"]').val($('input[name="fdHistory_Form['+rowIndex+'].fdPost"]').val());
					$('input[name="fdStartDateHistory"]').val($('input[name="fdHistory_Form['+rowIndex+'].fdStartDate"]').val());
					$('input[name="fdEndDateHistory"]').val($('input[name="fdHistory_Form['+rowIndex+'].fdEndDate"]').val());
					$('textarea[name="fdDescHistory"]').val($('input[name="fdHistory_Form['+rowIndex+'].fdDesc"]').val());
					$('textarea[name="fdLeaveReason"]').val($('input[name="fdHistory_Form['+rowIndex+'].fdLeaveReason"]').val());
			    }else if("view"==methodNew){ 
					$('input[name="companyName"]').val($('input[name="fdHistory_Form['+rowIndex+'].fdName"]').val());
					$('input[name="fdPost"]').val($('input[name="fdHistory_Form['+rowIndex+'].fdPost"]').val());
					$('input[name="fdStartDateHistory"]').val($('input[name="fdHistory_Form['+rowIndex+'].fdStartDate"]').val());
					$('input[name="fdEndDateHistory"]').val($('input[name="fdHistory_Form['+rowIndex+'].fdEndDate"]').val());
					$('input[name="fdDescHistory"]').val($('input[name="fdHistory_Form['+rowIndex+'].fdDesc"]').val());
					$('input[name="fdLeaveReason"]').val($('input[name="fdHistory_Form['+rowIndex+'].fdLeaveReason"]').val());
			    }
			}else if("TABLE_DocList_fdEducations_Form"==form){
				if("update"==methodNew){
					$('input[name="expName"]').val($('input[name="fdEducations_Form['+rowIndex+'].fdName"]').val());
					$('input[name="fdMajorName"]').val($('input[name="fdEducations_Form['+rowIndex+'].fdMajor"]').val());
					var fdId=$('input[name="fdEducations_Form['+rowIndex+'].fdAcadeRecord"]').val();
					$('select[name="fdAcadeRecordId"]').val(fdId);
					$('input[name="fdAcadeRecordName"]').val($('input[name="fdEducations_Form['+rowIndex+'].fdAcadeRecordName"]').val());
					$('input[name="fdEntranceDate"]').val($('input[name="fdEducations_Form['+rowIndex+'].fdEntranceDate"]').val());
					$('input[name="fdGraduationDate"]').val($('input[name="fdEducations_Form['+rowIndex+'].fdGraduationDate"]').val());
					$('textarea[name="fdRemarkEduExp"]').val($('input[name="fdEducations_Form['+rowIndex+'].fdRemark"]').val());
			    }else if("view"==methodNew){ 
			    	$('input[name="expName"]').val($('input[name="fdEducations_Form['+rowIndex+'].fdName"]').val());
					$('input[name="fdMajor"]').val($('input[name="fdEducations_Form['+rowIndex+'].fdMajor"]').val());
					$('input[name="fdAcadeRecordName"]').val($('input[name="fdEducations_Form['+rowIndex+'].fdAcadeRecordName"]').val());
					$('input[name="fdEntranceDate"]').val($('input[name="fdEducations_Form['+rowIndex+'].fdEntranceDate"]').val());
					$('input[name="fdGraduationDate"]').val($('input[name="fdEducations_Form['+rowIndex+'].fdGraduationDate"]').val());
					$('input[name="fdRemarkEduExp"]').val($('input[name="fdEducations_Form['+rowIndex+'].fdRemark"]').val()); 
			    }
			}else if("TABLE_DocList_fdTrains_Form"==form){
				if("update"==methodNew){ 
					$('input[name="trainName"]').val($('input[name="fdTrains_Form['+rowIndex+'].fdName"]').val());
					$('input[name="fdTrainCompany"]').val($('input[name="fdTrains_Form['+rowIndex+'].fdTrainCompany"]').val());
					$('input[name="fdStartDateTrain"]').val($('input[name="fdTrains_Form['+rowIndex+'].fdStartDate"]').val());
					$('input[name="fdEndDateTrain"]').val($('input[name="fdTrains_Form['+rowIndex+'].fdEndDate"]').val());
					$('textarea[name="fdRemarkTrain"]').val($('input[name="fdTrains_Form['+rowIndex+'].fdRemark"]').val());
			    }else if("view"==methodNew){ 
			    	$('input[name="trainName"]').val($('input[name="fdTrains_Form['+rowIndex+'].fdName"]').val());
					$('input[name="fdTrainCompany"]').val($('input[name="fdTrains_Form['+rowIndex+'].fdTrainCompany"]').val());
					$('input[name="fdStartDateTrain"]').val($('input[name="fdTrains_Form['+rowIndex+'].fdStartDate"]').val());
					$('input[name="fdEndDateTrain"]').val($('input[name="fdTrains_Form['+rowIndex+'].fdEndDate"]').val());
					$('input[name="fdRemarkTrain"]').val($('input[name="fdTrains_Form['+rowIndex+'].fdRemark"]').val());
			    }
			}else if("TABLE_DocList_fdCertificate_Form"==form){
				if("update"==methodNew){ 
					$('input[name="certifiName"]').val($('input[name="fdCertificate_Form['+rowIndex+'].fdName"]').val());
					$('input[name="fdIssuingUnit"]').val($('input[name="fdCertificate_Form['+rowIndex+'].fdIssuingUnit"]').val());
					$('input[name="fdIssueDate"]').val($('input[name="fdCertificate_Form['+rowIndex+'].fdIssueDate"]').val());
					$('input[name="fdInvalidDate"]').val($('input[name="fdCertificate_Form['+rowIndex+'].fdInvalidDate"]').val());
					$('textarea[name="fdRemarkCertificate"]').val($('input[name="fdCertificate_Form['+rowIndex+'].fdRemark"]').val());
			    }else if("view"==methodNew){ 
			    	$('input[name="certifiName"]').val($('input[name="fdCertificate_Form['+rowIndex+'].fdName"]').val());
					$('input[name="fdIssuingUnit"]').val($('input[name="fdCertificate_Form['+rowIndex+'].fdIssuingUnit"]').val());
					$('input[name="fdIssueDate"]').val($('input[name="fdCertificate_Form['+rowIndex+'].fdIssueDate"]').val());
					$('input[name="fdInvalidDate"]').val($('input[name="fdCertificate_Form['+rowIndex+'].fdInvalidDate"]').val());
					$('input[name="fdRemarkCertificate"]').val($('input[name="fdCertificate_Form['+rowIndex+'].fdRemark"]').val());
			    }
			}else if("TABLE_DocList_fdRewardsPunishments_Form"==form){
				if("update"==methodNew){ 
					$('input[name="rewPuniName"]').val($('input[name="fdRewardsPunishments_Form['+rowIndex+'].fdName"]').val());
					$('input[name="fdDate"]').val($('input[name="fdRewardsPunishments_Form['+rowIndex+'].fdDate"]').val());
					$('textarea[name="fdRemarkRewPuni"]').val($('input[name="fdRewardsPunishments_Form['+rowIndex+'].fdRemark"]').val());
			    }else if("view"==methodNew){ 
			    	$('input[name="rewPuniName"]').val($('input[name="fdRewardsPunishments_Form['+rowIndex+'].fdName"]').val());
					$('input[name="fdDate"]').val($('input[name="fdRewardsPunishments_Form['+rowIndex+'].fdDate"]').val());
					$('input[name="fdRemarkRewPuni"]').val($('input[name="fdRewardsPunishments_Form['+rowIndex+'].fdRemark"]').val()); 
			    }
			}else if("TABLE_DocList_fdfamily_Form"==form){
				if("update"==methodNew || "view"==methodNew ){ 
					$('input[name="fdRelated"]').val($('input[name="fdfamily_Form['+rowIndex+'].fdRelated"]').val());
					$('input[name="fdFamilyName"]').val($('input[name="fdfamily_Form['+rowIndex+'].fdName"]').val());
					$('input[name="fdOccupation"]').val($('input[name="fdfamily_Form['+rowIndex+'].fdOccupation"]').val());
					$('input[name="fdCompany"]').val($('input[name="fdfamily_Form['+rowIndex+'].fdCompany"]').val());
					$('input[name="fdConnect"]').val($('input[name="fdfamily_Form['+rowIndex+'].fdConnect"]').val());
					$('textarea[name="fdMemo"]').val($('input[name="fdfamily_Form['+rowIndex+'].fdMemo"]').val());
			    }	
			}
		};
		//关闭招聘岗位表单
		window.closeDetail=function(form,element){
			//clearnValue(form,element);
			if("table_of_fdHistory_detail_edit"==element||"table_of_fdHistory_detail_view"==element){
				topic.publish('historyRowPoplayer.display.hide');
			}else if("table_of_fdEducations_detail_edit"==element||"table_of_fdEducations_detail_view"==element){
				topic.publish('educationsRowPoplayer.display.hide');
			}else if("table_of_fdTrains_detail_edit"==element||"table_of_fdTrains_detail_view"==element){
				topic.publish('trainsRowPoplayer.display.hide');
			}else if("table_of_fdCertificate_detail_edit"==element||"table_of_fdCertificate_detail_view"==element){
				topic.publish('certificateRowPoplayer.display.hide');
			}else if("table_of_fdRewardsPunishments_detail_edit"==element||"table_of_fdRewardsPunishments_detail_view"==element){
				topic.publish('rewardsPunishmentsRowPoplayer.display.hide');
			}else if("table_of_fdfamily_detail_edit"==element||"table_of_fdfamily_detail_view"==element){
				topic.publish('familyRowPoplayer.display.hide');
			}
		}
		
		//清除招聘岗位表单缓存
		window.clearnValue=function(form,element){
			$(".ajustReason").hide();
			$('input[name="fdRow"]').val("");
			$(".validation-advice").each(function(){
				$(this).remove();
			});
			$(".lui_validate").each(function(){
				$(this).remove();
			});
			$("#"+form).find("tr").each(function(){
				$(this).removeClass("current");
			});
			$("#"+element).find("input").each(function(){
				$(this).val("");
			});
			$("#"+element).find("li").each(function(){
				$(this).remove();
			});
			$("#"+element).find("textarea").each(function(){
				$(this).val("");
			});
			$("#"+element).find("select").each(function(){
				$(this).val("");
			});
		}
		
			window.historyRowOpt = function(row,config){
				var historyRowPoplayer=null;
				var row = $(row),
					tables = row.parents('table'), 
					config = config || {};
				if(tables.length > 0){
					var table = tables.eq(0),//离tr最近的table
						container = table.parent();
					if(container && ( container.css('position')=='static' || container.css('position')=='' ) ){
						container.css('position','relative');
					}
					if(!container){
						container = $(document.body);
					}
					if( !window.historyRowPoplayer){
						window.historyRowPoplayer = $('<div class="tb_normal_poplayer"/>');
						container.append(window.historyRowPoplayer);
						window.historyRowPoplayer.css({
							'position': 'absolute',
							'border': '1px #d2d2d2 solid',
							'background-color': 'white'
						});
						topic.subscribe('historyRowPoplayer.display.hide',function(){
							window.clearInterval(window.historyRowPoplayer.resize);
							window.historyRowPoplayer.resize = null;
							if(window.historyRowPoplayer.activeRow){
								window.historyRowPoplayer.activeRow.css("height","");
								window.historyRowPoplayer.activeRow.css('vertical-align','');
							}
							window.historyRowPoplayer.hide();
						});
					}
					window.clearInterval(window.historyRowPoplayer.resize);
					if(window.historyRowPoplayer.activeRow){
						window.historyRowPoplayer.activeRow.css("height","");
						window.historyRowPoplayer.activeRow.css('vertical-align','');
					}
					window.historyRowPoplayer.resize = null;
					window.historyRowPoplayer.resize = window.setInterval(function(){
						if(window.historyRowPoplayer.activeRow){
							var __height = window.historyRowPoplayer.height(),
								__rowHeight = window.historyRowPoplayer.rowHeight;
							window.historyRowPoplayer.activeRow.height(__height+__rowHeight);
							window.historyRowPoplayer.activeRow.css('vertical-align','top');
						}
					});//监听高度变化改变tr高度
					var	rowPoplayer = window.historyRowPoplayer,
						height = config.height,
						width = config.width || table.innerWidth(),
						rowHeight = row.height();
					if(!rowPoplayer.rowHeigth){
						rowPoplayer.rowHeight = rowHeight;
					}
				/*	if(rowPoplayer.activeRow){
						rowPoplayer.activeRow.css("height","");
						window.rowPoplayer.activeRow.css('vertical-align','');
					}*/
					rowPoplayer.activeRow = row;
					rowPoplayer.css('min-height',height);
					rowPoplayer.width(width);
					if(config.element){
						rowPoplayer.append(config.element);
					}
					var offsetTop = getOffsetTop(row,container);
					rowPoplayer.css("top",offsetTop+rowHeight );
					rowPoplayer.show();
				}
				return rowPoplayer[0];
			};
			
			window.educationsRowOpt = function(row,config){
				var educationsRowPoplayer=null;
				var row = $(row),
					tables = row.parents('table'), 
					config = config || {};
				if(tables.length > 0){
					var table = tables.eq(0),//离tr最近的table
						container = table.parent();
					if(container && ( container.css('position')=='static' || container.css('position')=='' ) ){
						container.css('position','relative');
					}
					if(!container){
						container = $(document.body);
					}
					if( !window.educationsRowPoplayer){
						window.educationsRowPoplayer = $('<div class="tb_normal_poplayer"/>');
						container.append(window.educationsRowPoplayer);
						window.educationsRowPoplayer.css({
							'position': 'absolute',
							'border': '1px #d2d2d2 solid',
							'background-color': 'white'
						});
						topic.subscribe('educationsRowPoplayer.display.hide',function(){
							window.clearInterval(window.educationsRowPoplayer.resize);
							window.educationsRowPoplayer.resize = null;
							if(window.educationsRowPoplayer.activeRow){
								window.educationsRowPoplayer.activeRow.css("height","");
								window.educationsRowPoplayer.activeRow.css('vertical-align','');
							}
							window.educationsRowPoplayer.hide();
						});
					}
					window.clearInterval(window.educationsRowPoplayer.resize);
					if(window.educationsRowPoplayer.activeRow){
						window.educationsRowPoplayer.activeRow.css("height","");
						window.educationsRowPoplayer.activeRow.css('vertical-align','');
					}
					window.educationsRowPoplayer.resize = null;
					window.educationsRowPoplayer.resize = window.setInterval(function(){
						if(window.educationsRowPoplayer.activeRow){
							var __height = window.educationsRowPoplayer.height(),
								__rowHeight = window.educationsRowPoplayer.rowHeight;
							window.educationsRowPoplayer.activeRow.height(__height+__rowHeight);
							window.educationsRowPoplayer.activeRow.css('vertical-align','top');
						}
					});//监听高度变化改变tr高度
					var	rowPoplayer = window.educationsRowPoplayer,
						height = config.height,
						width = config.width || table.innerWidth(),
						rowHeight = row.height();
					if(!rowPoplayer.rowHeigth){
						rowPoplayer.rowHeight = rowHeight;
					}
				/*	if(rowPoplayer.activeRow){
						rowPoplayer.activeRow.css("height","");
						window.rowPoplayer.activeRow.css('vertical-align','');
					}*/
					rowPoplayer.activeRow = row;
					rowPoplayer.css('min-height',height);
					rowPoplayer.width(width);
					if(config.element){
						rowPoplayer.append(config.element);
					}
					var offsetTop = getOffsetTop(row,container);
					rowPoplayer.css("top",offsetTop+rowHeight );
					rowPoplayer.show();
				}
				return rowPoplayer[0];
			};
			
			window.trainsRowOpt = function(row,config){
				var trainsRowPoplayer=null;
				var row = $(row),
					tables = row.parents('table'), 
					config = config || {};
				if(tables.length > 0){
					var table = tables.eq(0),//离tr最近的table
						container = table.parent();
					if(container && ( container.css('position')=='static' || container.css('position')=='' ) ){
						container.css('position','relative');
					}
					if(!container){
						container = $(document.body);
					}
					if( !window.trainsRowPoplayer){
						window.trainsRowPoplayer = $('<div class="tb_normal_poplayer"/>');
						container.append(window.trainsRowPoplayer);
						window.trainsRowPoplayer.css({
							'position': 'absolute',
							'border': '1px #d2d2d2 solid',
							'background-color': 'white'
						});
						topic.subscribe('trainsRowPoplayer.display.hide',function(){
							window.clearInterval(window.trainsRowPoplayer.resize);
							window.trainsRowPoplayer.resize = null;
							if(window.trainsRowPoplayer.activeRow){
								window.trainsRowPoplayer.activeRow.css("height","");
								window.window.trainsRowPoplayer.activeRow.css('vertical-align','');
							}
							window.trainsRowPoplayer.hide();
						});
					}
					window.clearInterval(window.trainsRowPoplayer.resize);
					if(window.trainsRowPoplayer.activeRow){
						window.trainsRowPoplayer.activeRow.css("height","");
						window.trainsRowPoplayer.activeRow.css('vertical-align','');
					}
					window.trainsRowPoplayer.resize = null;
					window.trainsRowPoplayer.resize = window.setInterval(function(){
						if(window.trainsRowPoplayer.activeRow){
							var __height = window.trainsRowPoplayer.height(),
								__rowHeight = window.trainsRowPoplayer.rowHeight;
							window.trainsRowPoplayer.activeRow.height(__height+__rowHeight);
							window.trainsRowPoplayer.activeRow.css('vertical-align','top');
						}
					});//监听高度变化改变tr高度
					var	rowPoplayer = window.trainsRowPoplayer,
						height = config.height,
						width = config.width || table.innerWidth(),
						rowHeight = row.height();
					if(!rowPoplayer.rowHeigth){
						rowPoplayer.rowHeight = rowHeight;
					}
					/*if(rowPoplayer.activeRow){
						rowPoplayer.activeRow.css("height","");
						window.rowPoplayer.activeRow.css('vertical-align','');
					}*/
					rowPoplayer.activeRow = row;
					rowPoplayer.css('min-height',height);
					rowPoplayer.width(width);
					if(config.element){
						rowPoplayer.append(config.element);
					}
					var offsetTop = getOffsetTop(row,container);
					rowPoplayer.css("top",offsetTop+rowHeight );
					rowPoplayer.show();
				}
				return rowPoplayer[0];
			};
			
			window.certificateRowOpt = function(row,config){
				var certificateRowPoplayer=null;
				var row = $(row),
					tables = row.parents('table'), 
					config = config || {};
				if(tables.length > 0){
					var table = tables.eq(0),//离tr最近的table
						container = table.parent();
					if(container && ( container.css('position')=='static' || container.css('position')=='' ) ){
						container.css('position','relative');
					}
					if(!container){
						container = $(document.body);
					}
					if( !window.certificateRowPoplayer){
						window.certificateRowPoplayer = $('<div class="tb_normal_poplayer"/>');
						container.append(window.certificateRowPoplayer);
						window.certificateRowPoplayer.css({
							'position': 'absolute',
							'border': '1px #d2d2d2 solid',
							'background-color': 'white'
						});
						topic.subscribe('certificateRowPoplayer.display.hide',function(){
							window.clearInterval(window.certificateRowPoplayer.resize);
							window.certificateRowPoplayer.resize = null;
							if(window.certificateRowPoplayer.activeRow){
								window.certificateRowPoplayer.activeRow.css("height","");
								window.certificateRowPoplayer.activeRow.css('vertical-align','');
							}
							window.certificateRowPoplayer.hide();
						});
					}
					window.clearInterval(window.certificateRowPoplayer.resize);
					if(window.certificateRowPoplayer.activeRow){
						window.certificateRowPoplayer.activeRow.css("height","");
						window.certificateRowPoplayer.activeRow.css('vertical-align','');
					}
					window.certificateRowPoplayer.resize = null;
					window.certificateRowPoplayer.resize = window.setInterval(function(){
						if(window.certificateRowPoplayer.activeRow){
							var __height = window.certificateRowPoplayer.height(),
								__rowHeight = window.certificateRowPoplayer.rowHeight;
							window.certificateRowPoplayer.activeRow.height(__height+__rowHeight);
							window.certificateRowPoplayer.activeRow.css('vertical-align','top');
						}
					});//监听高度变化改变tr高度
					var	rowPoplayer = window.certificateRowPoplayer,
						height = config.height,
						width = config.width || table.innerWidth(),
						rowHeight = row.height();
					if(!rowPoplayer.rowHeigth){
						rowPoplayer.rowHeight = rowHeight;
					}
					/*if(rowPoplayer.activeRow){
						rowPoplayer.activeRow.css("height","");
						window.rowPoplayer.activeRow.css('vertical-align','');
					}*/
					rowPoplayer.activeRow = row;
					rowPoplayer.css('min-height',height);
					rowPoplayer.width(width);
					if(config.element){
						rowPoplayer.append(config.element);
					}
					var offsetTop = getOffsetTop(row,container);
					rowPoplayer.css("top",offsetTop+rowHeight );
					rowPoplayer.show();
				}
				return rowPoplayer[0];
			};
			
			window.rewardsPunishmentsRowOpt = function(row,config){
				var rewardsPunishmentsRowPoplayer=null;
				var row = $(row),
					tables = row.parents('table'), 
					config = config || {};
				if(tables.length > 0){
					var table = tables.eq(0),//离tr最近的table
						container = table.parent();
					if(container && ( container.css('position')=='static' || container.css('position')=='' ) ){
						container.css('position','relative');
					}
					if(!container){
						container = $(document.body);
					}
					if( !window.rewardsPunishmentsRowPoplayer){
						window.rewardsPunishmentsRowPoplayer = $('<div class="tb_normal_poplayer"/>');
						container.append(window.rewardsPunishmentsRowPoplayer);
						window.rewardsPunishmentsRowPoplayer.css({
							'position': 'absolute',
							'border': '1px #d2d2d2 solid',
							'background-color': 'white'
						});
						topic.subscribe('rewardsPunishmentsRowPoplayer.display.hide',function(){
							window.clearInterval(window.rewardsPunishmentsRowPoplayer.resize);
							window.rewardsPunishmentsRowPoplayer.resize = null;
							if(window.rewardsPunishmentsRowPoplayer.activeRow){
								window.rewardsPunishmentsRowPoplayer.activeRow.css("height","");
								window.rewardsPunishmentsRowPoplayer.activeRow.css('vertical-align','');
							}
							window.rewardsPunishmentsRowPoplayer.hide();
						});
					}
					window.clearInterval(window.rewardsPunishmentsRowPoplayer.resize);
					if(window.rewardsPunishmentsRowPoplayer.activeRow){
						window.rewardsPunishmentsRowPoplayer.activeRow.css("height","");
						window.rewardsPunishmentsRowPoplayer.activeRow.css('vertical-align','');
					}
					window.rewardsPunishmentsRowPoplayer.resize = null;
					window.rewardsPunishmentsRowPoplayer.resize = window.setInterval(function(){
						if(window.rewardsPunishmentsRowPoplayer.activeRow){
							var __height = window.rewardsPunishmentsRowPoplayer.height(),
								__rowHeight = window.rewardsPunishmentsRowPoplayer.rowHeight;
							window.rewardsPunishmentsRowPoplayer.activeRow.height(__height+__rowHeight);
							window.rewardsPunishmentsRowPoplayer.activeRow.css('vertical-align','top');
						}
					});//监听高度变化改变tr高度
					var	rowPoplayer = window.rewardsPunishmentsRowPoplayer,
						height = config.height,
						width = config.width || table.innerWidth(),
						rowHeight = row.height();
					if(!rowPoplayer.rowHeigth){
						rowPoplayer.rowHeight = rowHeight;
					}
				/*	if(rowPoplayer.activeRow){
						rowPoplayer.activeRow.css("height","");
						window.rowPoplayer.activeRow.css('vertical-align','');
					}*/
					rowPoplayer.activeRow = row;
					rowPoplayer.css('min-height',height);
					rowPoplayer.width(width);
					if(config.element){
						rowPoplayer.append(config.element);
					}
					var offsetTop = getOffsetTop(row,container);
					rowPoplayer.css("top",offsetTop+rowHeight );
					rowPoplayer.show();
				}
				return rowPoplayer[0];
			};
			
			//家庭信息
			window.familyRowOpt = function(row,config){
				var familyRowPoplayer=null;
				var row = $(row),
					tables = row.parents('table'), 
					config = config || {};
				if(tables.length > 0){
					var table = tables.eq(0),//离tr最近的table
						container = table.parent();
					if(container && ( container.css('position')=='static' || container.css('position')=='' ) ){
						container.css('position','relative');
					}
					if(!container){
						container = $(document.body);
					}
					if( !window.familyRowPoplayer){
						window.familyRowPoplayer = $('<div class="tb_normal_poplayer"/>');
						container.append(window.familyRowPoplayer);
						window.familyRowPoplayer.css({
							'position': 'absolute',
							'border': '1px #d2d2d2 solid',
							'background-color': 'white'
						});
						topic.subscribe('familyRowPoplayer.display.hide',function(){
							window.clearInterval(window.familyRowPoplayer.resize);
							window.familyRowPoplayer.resize = null;
							if(window.familyRowPoplayer.activeRow){
								window.familyRowPoplayer.activeRow.css("height","");
								window.familyRowPoplayer.activeRow.css('vertical-align','');
							}
							window.familyRowPoplayer.hide();
						});
					}
					window.clearInterval(window.familyRowPoplayer.resize);
					if(window.familyRowPoplayer.activeRow){
						window.familyRowPoplayer.activeRow.css("height","");
						window.familyRowPoplayer.activeRow.css('vertical-align','');
					}
					window.familyRowPoplayer.resize = null;
					window.familyRowPoplayer.resize = window.setInterval(function(){
						if(window.familyRowPoplayer.activeRow){
							var __height = window.familyRowPoplayer.height(),
								__rowHeight = window.familyRowPoplayer.rowHeight;
							window.familyRowPoplayer.activeRow.height(__height+__rowHeight);
							window.familyRowPoplayer.activeRow.css('vertical-align','top');
						}
					});//监听高度变化改变tr高度
					var	rowPoplayer = window.familyRowPoplayer,
						height = config.height,
						width = config.width || table.innerWidth(),
						rowHeight = row.height();
					if(!rowPoplayer.rowHeigth){
						rowPoplayer.rowHeight = rowHeight;
					}
					/*if(rowPoplayer.activeRow){
						rowPoplayer.activeRow.css("height","");
						window.rowPoplayer.activeRow.css('vertical-align','');
					}*/
					rowPoplayer.activeRow = row;
					rowPoplayer.css('min-height',height);
					rowPoplayer.width(width);
					if(config.element){
						rowPoplayer.append(config.element);
					}
					var offsetTop = getOffsetTop(row,container);
					rowPoplayer.css("top",offsetTop+rowHeight );
					rowPoplayer.show();
				}
				return rowPoplayer[0];
			};
			
			
			function getOffsetTop(child,parent){
				var offsetTop = 0;
				while( child[0] != parent[0] ){
					offsetTop += child[0].offsetTop;
					child = child.parent();
				}
				return offsetTop;
			}
	});

</script>
