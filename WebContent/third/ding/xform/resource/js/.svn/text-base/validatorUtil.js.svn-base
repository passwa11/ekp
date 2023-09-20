/**
 * 添加半透明遮罩层禁用元素
 * @param $dom    jquery对象
 * @param flag    禁用or解除禁用
 */
let disabledList = [];

function setDisabled($dom) {
    if(!$dom || $dom.length < 1) {
        return;
    }
    let width = $($dom).width();
    let height = $($dom).height();
    let offset = $($dom).offset();

    let div = $("<div/>");
    div.addClass("_disabled");
    div.css("width", width);
    div.css("height", height);
    div.css("left", offset.left);
    //div.css("top", offset.top);   //适应tr的display: table-row;
    div.appendTo($dom);

    disabledList.push($dom);
}

function removeAllDisabled() {
    for (let i = 0; i < disabledList.length; i++) {
        disabledList[i].find("._disabled").remove();
    }
    disabledList = [];
}

$(window).resize(function () {
    refreshValidatorTrPosition();
});

function refreshValidatorTrPosition() {
    for (let i = 0; i < disabledList.length; i++) {
        let offset = disabledList[i].offset();
        let forbiddenDiv = disabledList[i].find("._disabled");
        forbiddenDiv.css("left", offset.left);
        //forbiddenDiv.css("top", offset.top);  //适应tr的display: table-row;
    }
}
/**
 * 添加校验请假时间重复---适用例如批量请假的明细表中
 * 1、判断当前选择时间的行中的 开始时间和结束时间 是否包含其他行已选的开始结束日期时间
 * 2、判断当前选择时间的行中的 开始时间和结束时间 是否包含整体在其他行已选的开始结束日期时间之间
 * @param validatorName
 * @param errorMessage
 * @returns
 */
function addBatchRepeatContainTimeValidation(validatorName, errorMessage) {
	let validation = $KMSSValidation();
    validation.addValidator(validatorName, errorMessage, function (v, e, o) {
        let result = true;
        if(!v){
        	return;
        }
        /* ************************************** 取当前触发行的数据 start ********************************************************** */
        var nowTr = $(e).parent(".input").parent(".inputselectsgl").parent("xformflag").parent(".xform_datetime").parent("td").parent("tr");
        //当前行的开始时间、结束时间、单位 开始时间段、结束时间段
        var nowStartTime = $(nowTr).find("[name*='fd_leave_start_time']").val();
        var nowEndTime = $(nowTr).find("[name*='fd_leave_end_time']").val();
        var nowUnit = $(nowTr).find("[name*='type_unit']").val();
        
        var nowStartOne = $(nowTr).find("[name*='fd_start_time_one']").children('option:selected').val();
        var nowEndOne = $(nowTr).find("[name*='fd_end_time_one']").children('option:selected').val();
        
        //当前行index
        var nowTrIndex = nowTr[0].rowIndex;
        /* ************************************** 取当前触发行的数据 end ********************************************************** */
       
        if(!nowStartTime || !nowEndTime || !nowUnit){
        	return result;
        }
        if("halfDay" == nowUnit){
        	if(!nowStartOne || nowEndOne){
        		return result;
        	}
        }
        
        var _table = $(e).parent(".input").parent(".inputselectsgl").parents(".xform_datetime").parent("td").parent("tr").parent("tbody").parent("table");
        var trs = $(_table).find("tr"); 
        
        // 循环每一行 来和当前触发行进行校验 start 
        for (var i = 0; i < trs.length; i++) {
        	var rowIndex = trs[i].rowIndex;
        	if(rowIndex==nowTrIndex){
        		continue;//当前行不和当前行校验重复
        	}
        	
			var start_time_tr = $(trs[i]).find("[name*='fd_leave_start_time']").val();//每一行的开始时间
			var end_time_tr = $(trs[i]).find("[name*='fd_leave_end_time']").val();	//每一行的结束时间		
			
			var start_one_tr = $(trs[i]).find("[name*='fd_start_time_one']").children('option:selected').val();			
			var end_one_tr = $(trs[i]).find("[name*='fd_end_time_one']").children('option:selected').val();			
			
			var type_unit_tr = $(trs[i]).find("[name*='type_unit']").val();	//每一行的单位	
			
			if(!start_time_tr || !end_time_tr || !type_unit_tr){//请假开始时间或请假结束时间为空的行：跳过
				continue;
			}
			 /* ************************************** 校验 开始 ********************************************************** */
			if("day"==nowUnit){//当前触发行是按天
				nowStartTime = new Date(nowStartTime+" 00:00");
				nowEndTime = new Date(nowEndTime+" 23:59");
				if("day" == type_unit_tr){
					start_time_tr = new Date(start_time_tr+" 00:00");
					end_time_tr = new Date(end_time_tr+" 23:59");
					if(nowStartTime.getTime() <= start_time_tr.getTime() && nowEndTime.getTime() >= end_time_tr.getTime()){
						result =false;
						break;
					}					
				}else if("hour" == type_unit_tr){
					start_time_tr = new Date(start_time_tr);
					end_time_tr = new Date(end_time_tr);
					if(nowStartTime.getTime() <= start_time_tr.getTime() && nowEndTime.getTime() >= end_time_tr.getTime()){
						result =false;
						break;
					}					
				}else if("halfDay" == type_unit_tr){
					if("PM" == start_one_tr){
						start_time_tr += " 12:00:01";
					}else{
						start_time_tr += " 00:00:01";
					}
					
					if("PM" == end_one_tr){
						end_time_tr += " 23:59:59";
					}else{
						end_time_tr += " 12:00:00";						
					}
					start_time_tr = new Date(start_time_tr);
					end_time_tr = new Date(end_time_tr);
					if(nowStartTime.getTime() <= start_time_tr.getTime() && nowEndTime.getTime() >= end_time_tr.getTime()){
						result =false;
						break;
					}					
				}else{
					continue;				
				}
			}else if("hour"==nowUnit){//当前触发行是时分秒
				nowStartTime = new Date(nowStartTime);
				nowEndTime = new Date(nowEndTime);
				if("day" == type_unit_tr){
					start_time_tr = new Date(start_time_tr+" 00:00");
					end_time_tr = new Date(end_time_tr+" 23:59");
					if(nowStartTime.getTime() <= start_time_tr.getTime() && nowEndTime.getTime() >= end_time_tr.getTime()){
						result =false;
						break;
					}					
				}else if("hour" == type_unit_tr){
					start_time_tr = new Date(start_time_tr);
					end_time_tr = new Date(end_time_tr);
					if(nowStartTime.getTime() <= start_time_tr.getTime() && nowEndTime.getTime() >= end_time_tr.getTime()){
						result =false;
						break;
					}	
				}else if("halfDay" == type_unit_tr){
					if("PM" == start_one_tr){
						start_time_tr += " 12:00:01";
					}else{
						start_time_tr += " 00:00:01";
					}
					
					if("PM" == end_one_tr){
						end_time_tr += " 23:59:59";
					}else{
						end_time_tr += " 12:00:00";						
					}
					start_time_tr = new Date(start_time_tr);
					end_time_tr = new Date(end_time_tr);
					if(nowStartTime.getTime() <= start_time_tr.getTime() && nowEndTime.getTime() >= end_time_tr.getTime()){
						result =false;
						break;
					}
				}else{
					continue;				
				}
			}else if("halfDay"==nowUnit){//当前处理行是半天
				if("PM" == nowStartOne){
					nowStartTime += " 12:00:01";
				}else{
					nowStartTime += " 00:00:01";
				}
				
				if("PM" == nowEndOne){
					nowEndTime += " 23:59:59";
				}else{
					nowEndTime += " 12:00:00";						
				}				
				nowStartTime = new Date(nowStartTime);
				nowEndTime = new Date(nowEndTime);
				if("day" == type_unit_tr){
					start_time_tr = new Date(start_time_tr+" 00:00:01");
					end_time_tr = new Date(end_time_tr+" 23:59:59");
					if(nowStartTime.getTime() <= start_time_tr.getTime() && nowEndTime.getTime() >= end_time_tr.getTime()){
						result =false;
						break;
					}		
				}else if("hour" == type_unit_tr){
					start_time_tr = new Date(start_time_tr);
					end_time_tr = new Date(end_time_tr);
					if(nowStartTime.getTime() <= start_time_tr.getTime() && nowEndTime.getTime() >= end_time_tr.getTime()){
						result =false;
						break;
					}	
				}else if("halfDay" == type_unit_tr){
					if("PM" == start_one_tr){
						start_time_tr += " 12:00:01";
					}else{
						start_time_tr += " 00:00:01";
					}
					
					if("PM" == end_one_tr){
						end_time_tr += " 23:59:59";
					}else{
						end_time_tr += " 12:00:00";						
					}
					start_time_tr = new Date(start_time_tr);
					end_time_tr = new Date(end_time_tr);
					if(nowStartTime.getTime() <= start_time_tr.getTime() && nowEndTime.getTime() >= end_time_tr.getTime()){
						result =false;
						break;
					}
				}else{
					continue;				
				}
			}else{
				continue;				
			}			
			 /* ************************************** 校验 结束 ********************************************************** */
        }
        
        //  循环每一行 来和当前触发行进行校验 end 
       return  result;
    });
}

/**
 * 添加校验请假时间重复---适用例如批量请假的明细表中
 * 判断当前选的时间 是否在其他行已选的时间段内
 */
function addBatchRepeatTimeValidation(validatorName, errorMessage) {
	let validation = $KMSSValidation();
    validation.addValidator(validatorName, errorMessage, function (v, e, o) {
        let result = true;
        if(!v){
        	return;
        }
        var objName = e.name;
        //当前选择时间的单位
        var nowTime = v;
        var objUnit = $(e).parent(".input").parent(".inputselectsgl").parent("xformflag").parent(".xform_datetime").parent("td").parent("tr").find("[name*='type_unit']").val();
        if(objName.indexOf("fd_leave_start_time")>0){//当前选的是开始时间
        	 if("hour" == objUnit){
        		 nowTime = nowTime+":01";
             }
        	 if("day"==objUnit){
        		 nowTime = nowTime+" 00:00:01";
        	 }
        	 if("halfDay"==objUnit){
        		 var startOne = $(e).parent(".input").parent(".inputselectsgl").parent("xformflag").parent(".xform_datetime").parent("td").parent("tr").find("[name*='fd_start_time_one']").children('option:selected').val();
        		 if("PM"==startOne){
        			 nowTime = nowTime+" 23:59:59";
        		 }else{
        			 nowTime = nowTime+" 12:00:00";
        		 }
        	 }
        }else{//当前选的是结束时间
        	 if("hour" == objUnit){
        		 nowTime = nowTime+":00"
             }
        	 if("day"==objUnit){
        		 nowTime = nowTime+" 23:59:00"
        	 }
        	 if("halfDay"==objUnit){
        		 var endOne = $(e).parent(".input").parent(".inputselectsgl").parent("xformflag").parent(".xform_datetime").parent("td").parent("tr").find("[name*='fd_end_time_one']").children('option:selected').val();
        		 if("AM"==endOne){
        			 nowTime = nowTime+" 11:59:00";
        		 }else{
        			 nowTime = nowTime+" 23:59:00";
        		 }
        	 }
        }
        //当前选择的时间
        var objTime = new Date(nowTime);
        var _table = $(e).parent(".input").parent(".inputselectsgl").parents(".xform_datetime").parent("td").parent("tr").parent("tbody").parent("table");
        var trs = $(_table).find("tr");     
        //当前选择行的开始时间和结束时间
        var fd_leave_start_time_tr = $(e).parent(".input").parent(".inputselectsgl").parents(".xform_datetime").parent("td").parent("tr").find("[name*='fd_leave_start_time']").val();
        var fd_leave_end_time_tr = $(e).parent(".input").parent(".inputselectsgl").parents(".xform_datetime").parent("td").parent("tr").find("[name*='fd_leave_end_time']").val();
        //当前行index
        var nowTrIndex = $(e).parent(".input").parent(".inputselectsgl").parents(".xform_datetime").parent("td").parent("tr")[0].rowIndex;
        //循环所有tr
        for (var i = 0; i < trs.length; i++) {
        	var rowIndex = trs[i].rowIndex;
        	if(rowIndex==nowTrIndex){
        		continue;//当前行不和当前行校验重复
        	}
			var fd_leave_start_time = $(trs[i]).find("[name*='fd_leave_start_time']");//每一行的开始时间
			var fd_leave_end_time = $(trs[i]).find("[name*='fd_leave_end_time']");	//每一行的结束时间		
			
			var start_one = $(trs[i]).find("[name*='fd_start_time_one']").children('option:selected').val();			
			var end_one = $(trs[i]).find("[name*='fd_end_time_one']").children('option:selected').val();			
			
			var type_unit = $(trs[i]).find("[name*='type_unit']").val();	//每一行的单位		
			if(!fd_leave_start_time || !fd_leave_end_time){//没有请假开始时间和结束时间的行： 跳过
				continue;
			}
			if(!fd_leave_start_time.val() || !fd_leave_end_time.val() || !type_unit){//请假开始时间或请假结束时间为空的行：跳过
				continue;
			}
			var startTime1= fd_leave_start_time.val();
			var endTime1= fd_leave_end_time.val();
			if("day" == type_unit){
				startTime1 = startTime1+" 00:00:00";
				endTime1 = endTime1+" 23:59:59";
			}
			if("halfDay" == type_unit){
				if("PM" == start_one){
					startTime1 = startTime1+" 12:00:01";
				}else{
					startTime1 = startTime1+" 00:00:00";
				}
				if("AM" == end_one){
					endTime1 = endTime1+" 12:00:00";
				}else{
					endTime1 = endTime1+" 23:59:59";
				}
			}			
			
			var startTime = new Date(startTime1);
			var endTime = new Date(endTime1);
						
			if(objTime.getTime() >=startTime.getTime() && objTime.getTime()<= endTime.getTime()){
				 result = false;
				 break;
			}
			//校验复制行时的重复
			if(!fd_leave_start_time_tr || !fd_leave_end_time_tr){
				continue;
			}
			if(rowIndex != nowTrIndex){
				var startTime_tr = new Date(fd_leave_start_time_tr);
				var endTime_tr = new Date(fd_leave_end_time_tr);
				if(startTime_tr.getTime() == startTime.getTime() && endTime_tr.getTime() == endTime.getTime()){
					result = false;
				}
			}
			
		}
        return result;
    });
}

/**
 * 校验是否能请假---适用例如批量请假的明细表中
 */
function addBatchIsLeaveValidation(validatorName, errorMessage) {
	let validation = $KMSSValidation();
	validation.addValidator(validatorName, errorMessage, function (v, e, o) {
		let result = true;
		
		var itemDuration = $(e).parents(".xform_inputText").parent("td").parent("tr").find("[name*='fd_item_duration']");
		if(itemDuration.length>0){
			if($(itemDuration).hasClass("validateIsLeave")){
				result = false;
			}
		}
		return result;
	});
}

/**
 * 添加校验同一类型的请假时长是否超额---适用例如批量请假的明细表中
 */
function addBatchSunDurationValidation(validatorName, errorMessage) {
	let validation = $KMSSValidation();
	validation.addValidator(validatorName, errorMessage, function (v, e, o) {
		let result = true;
		//当前行假期类型的余额
		var jqye = $(e).parents(".xform_inputText").parent("td").parent("tr").find("[name*='fd_leave_type']").children('option:selected').attr("data-value");
		//当前行的请假类型
		var tr_code = $(e).parents(".xform_inputText").parent("td").parent("tr").find("[name*='fd_leave_type']").children('option:selected').val();
		var _table = $(e).parents(".xform_inputText").parent("td").parent("tr").parent("tbody").parent("table");
		var trs = $(_table).find("tr"); 
		 var sunDuration = 0.00;
         //循环所有tr
         for (var i = 0; i < trs.length; i++) {
        	 //获取每行选中的假期类型code
			var leaveCode = $(trs[i]).find("[name*='fd_leave_type']").children('option:selected').val();
			if(leaveCode != tr_code){
				continue;
			}			
			var duration_Str = $(trs[i]).find("[name*='fd_item_duration']").val();
			if(!duration_Str){
				continue
			}
			var duration  = duration_Str.split(" ")[0];
			sunDuration += parseFloat(duration);
         }
         var ed = parseFloat(jqye);
         if(sunDuration-ed >0){
        	 result = false;
         }		
		return result;
	});
}
/**
 * 添加校验当前行的请假时长是否超过假期余额---适用例如批量请假的明细表中
 */
function addBatchDurationValidation(validatorName, errorMessage) {
	let validation = $KMSSValidation();
    validation.addValidator(validatorName, errorMessage, function (v, e, o) {
        let result = true;
        var jqye = $(e).parents(".xform_inputText").parent("td").parent("tr").find("[name*='fd_leave_type']").children('option:selected').attr("data-value");
        var qjsc = $(e).parents(".xform_inputText").parent("td").parent("tr").find("[name*='fd_item_duration']").val();
        qjsc = qjsc.split(" ")[0];
        if (jqye && qjsc) {
        	qjsc=parseFloat(qjsc);
        	jqye=parseFloat(jqye);
        	// 传递进来不一定是dateTime
        	// Com_GetDate(fdEndTime.val(), 'datetime', Com_Parameter.DateTime_format);
            if (jqye - qjsc < 0) {
                result = false;
            }
        }
        return result;
    });
}

/**
 * 添加开始时间与结束时间的日期比较校验---适用例如批量请假的明细表中
 */
function addBatchCompareTimeValidation(validatorName, errorMessage) {
    let validation = $KMSSValidation();
    validation.addValidator(validatorName, errorMessage, function (v, e, o) {
        let result = true;
        var fd_leave_start_time = $(e).parent(".input").parent(".inputselectsgl").parents(".xform_datetime").parent("td").parent("tr").find("[name*='fd_leave_start_time']").val();
        var fd_leave_end_time =  $(e).parent(".input").parent(".inputselectsgl").parents(".xform_datetime").parent("td").parent("tr").find("[name*='fd_leave_end_time']").val();
        if (fd_leave_start_time && fd_leave_end_time) {
        	// 传递进来不一定是dateTime
        	// Com_GetDate(fdEndTime.val(), 'datetime', Com_Parameter.DateTime_format);
            let start = new Date(fd_leave_start_time);
            let end = new Date(fd_leave_end_time);
            if (end.getTime() < start.getTime()) {
                result = false;
            }
        }
        return result;
    });
}

/**
 * 添加开始时间与结束时间的日期比较校验
 */
function addCompareTimeValidation(validatorName, fdStartTime, fdEndTime, errorMessage) {
    let validation = $KMSSValidation();
    validation.addValidator(validatorName, errorMessage, function (v, e, o) {
        let result = true;
        if (fdStartTime.val() && fdEndTime.val()) {
        	// 传递进来不一定是dateTime
        	// Com_GetDate(fdEndTime.val(), 'datetime', Com_Parameter.DateTime_format);
            let start = new Date(fdStartTime.val());
            let end = new Date(fdEndTime.val());
            if (end.getTime() < start.getTime()) {
                result = false;
            }
        }
        return result;
    });
}
/**
 * 添加增减明细表开始日期与结束日期比较校验
 */
function addCompareDateValidation(validatorName,errorMessage) {
	let validation = $KMSSValidation();
	var fdStartTime;
	var fdEndTime;	
	validation.addValidator(validatorName, errorMessage, function (v, e, o) {
		fdStartTime = $(e).closest(".ding_detailstable_row").find("[name*='from_time']").val();
		fdEndTime = $(e).closest(".ding_detailstable_row").find("[name*='to_time']").val();
		let result = true;
		if(fdStartTime && fdStartTime.indexOf(':') == -1 ){
			fdStartTime = fdStartTime + " 00:00:00";
		}
		if(fdEndTime && fdEndTime.indexOf(':') == -1 ){
			fdEndTime   = fdEndTime + " 00:00:00";
		}
		var start = Date.parse(fdStartTime);
		var end = Date.parse(fdEndTime);
		if (end < start) {
			result = false;
		}
		return result;
	});
}

/**
 * 添加开始时间与结束时间的日期跨度校验
 */
function addDateLengthValidation(validatorName, fdStartTime, fdEndTime, errorMessage) {
    let validation = $KMSSValidation();
    validation.addValidator(validatorName, errorMessage, function (v, e, o) {
        let result = true;
        if (fdStartTime.val() && fdEndTime) {
            let fdEndTimeDate;
            if(typeof(fdEndTime)!='string') {
                fdEndTimeDate = fdEndTime.val();
            } else {
                fdEndTimeDate = fdEndTime;
            }
            let length = isNaN(o['length']) ? 0 : parseInt(o['length']);
            if (length === 0 || this.getValidator('isEmpty').test(v)) {
                return true;
            }
            let start = new Date(fdStartTime.val());
            let end = new Date(fdEndTimeDate);
            if ((end.getTime() - start.getTime()) / 1000 / 60 > length * 24 * 60) {
                result = false;
                o['maxLength'] = length;
            }
        }
        return result;
    });
}

/**
 * 批量补卡：是否能补卡接口校验
 */
function addBatchCheckNotCanSupplyValidation(validatorName, errorMessage) {
	let validation = $KMSSValidation();
	validation.addValidator(validatorName, errorMessage, function (v, e, o) {
		let result = true;
		if($(e).hasClass("notCanSupply")){
			result = false;
		}
		if(!result){
				$(e).parents(".inputselectsgl").parents(".xform_datetime").parent("td").parent("tr").find(".form_tr_context_desc").css("display","none");
		}
		return result;
	});
}
/**
 * 批量补卡：补卡时间重复，请重新选择。--适用于PC端明细表-批量补卡
 * 校验当前行的时间是否和其它行的时间一样
 */
function checkReplacementValidation(validatorName, errorMessage) {
    let validation = $KMSSValidation();
    validation.addValidator(validatorName, errorMessage, function (v, e, o) {
        let result = true;
        var nowValue = v;
        //取当前触发时间Obj的id，避免当前时间和当前行比较
        var nowId = e.id;
        if(!nowValue || !nowId){
        	return result;
        }
       
        var userAgent = navigator.userAgent;
		var isOpera = userAgent.indexOf("Firefox") > -1;
		if (!isOpera) { //判断是否火狐浏览器
			nowValue = nowValue.replace('-','/');
		}
		var nowTime = new Date(nowValue).getTime();
        //取所有补卡时间
        var fd_replacement_times = $("#TABLE_DL_fd_batch_replacement_table").find("[name*='fd_replacement_time']");
        if(fd_replacement_times.length > 1){
        	//补卡时间数量超过一个才进行比较
        	for (var i = 0; i < fd_replacement_times.length; i++) {        		
        		var trId = fd_replacement_times[i].id;
        		var trValue = $(fd_replacement_times[i]).val();
				
        		if(nowId == trId){
        			continue;//触发行和循环到的当前行不做比较
        		}
        		if (!isOpera) { //判断是否火狐浏览器
        			trValue = trValue.replace('-','/');
        		}
        		var trTime = new Date(trValue).getTime();
        		if(nowTime - trTime == 0){//两个时间相等，则当前触发的时间控件校验住
        			result = false;
        		}
			}
        }     
        if(!result){
			$(e).parents(".inputselectsgl").parents(".xform_datetime").parent("td").parent("tr").find(".form_tr_context_desc").css("display","none");
		}
        return result;
    });
}

/**
 * 批量补卡：当前时间没有考勤异常，无需补卡。--适用于PC端明细表-批量补卡
 */
function checkTimeScheduleValidation(validatorName, errorMessage) {
	let validation = $KMSSValidation();
	validation.addValidator(validatorName, errorMessage, function (v, e, o) {
		let result = true;
		if($(e).hasClass("noreplacement")){
			result = false;
		}
		if(v){
			/*#148691-钉钉套件请假模板，360兼容模式下选择补卡时间校验出错提示“请选择当月补卡时间”-开始*/
			/*var repaymentTime = new Date(v+":00");*/
			var formatDate = v + ":00";
			formatDate = formatDate.replace(new RegExp(/-/gm) ,"/");
			var repaymentTime = new Date(formatDate);
			/*#148691-钉钉套件请假模板，360兼容模式下选择补卡时间校验出错提示“请选择当月补卡时间”-结束*/
			var nowTime = new Date();
			if(repaymentTime.getFullYear() != nowTime.getFullYear() || repaymentTime.getMonth() != nowTime.getMonth()){
				var interfacevalidator = this.getValidator("checkTimeSchedule");
				interfacevalidator.error = "请选择当月时间补卡";
				result = false;
			}
		}
		if(!result){
			$(e).parents(".inputselectsgl").parents(".xform_datetime").parent("td").parent("tr").find(".form_tr_context_desc").css("display","none");
		}
		return result;
	});
}

/**
 * 批量补卡：已超出剩余可申请补卡次数。--适用于PC端明细表-批量补卡
 */
function checkReplacementCountsValidation(validatorName, errorMessage) {
	let validation = $KMSSValidation();
	validation.addValidator(validatorName, errorMessage, function (v, e, o) {
		let result = true;
		//校验当前补卡时间是不是当月，如果不是当月则此处放行,是当月则校验 by 2021-03-17
		var replacement_time = new Date(v+":00");
		var nowDate = new Date();
		if(replacement_time.getFullYear() == nowDate.getFullYear() && replacement_time.getMonth() == nowDate.getMonth()){
			var replacement_count = $("[name*='fd_replacement_count']").val();
			replacement_count = replacement_count.substring(replacement_count.lastIndexOf("剩余")+2,replacement_count.lastIndexOf("次"));
			var fd_replacement_times = $("#TABLE_DL_fd_batch_replacement_table").find("[name*='fd_replacement_time']");
			if(replacement_count && fd_replacement_times && fd_replacement_times.length > 0){
				replacement_count = parseFloat(replacement_count);
				if(fd_replacement_times.length>replacement_count){
						result = false;
					}
				}
			}
		if(!result){
			$(e).parents(".inputselectsgl").parents(".xform_datetime").parent("td").parent("tr").find(".form_tr_context_desc").css("display","none");
		}
		return result;
	});
}

/**
 * 批量补卡：未获取到考勤异常信息。--适用于PC端明细表-批量补卡
 */
function checkReplacementExceptionValidation(validatorName, errorMessage) {
	let validation = $KMSSValidation();
	validation.addValidator(validatorName, errorMessage, function (v, e, o) {
		let result = true;
		if($(e).hasClass("nullinfo")){
			result = false;
		}
		if(!result){
			$(e).parents(".inputselectsgl").parents(".xform_datetime").parent("td").parent("tr").find(".form_tr_context_desc").css("display","none");
		}
		return result;
	});
}
/**
 * 校验用户是否在钉钉映射表中
 */
function addBatchUserValidation(validatorName, errorMessage) {
	let validation = $KMSSValidation();
	validation.addValidator(validatorName, errorMessage, function (v, e, o) {
		let result = true;
		if($(e).hasClass("notInDingList")){
			result = false;
		}
		return result;
	});
}
/**
 * 校验换班记录重复，请重新选择-明细表批量换班套件
 * 校验触发行与其他行之间是否存在：换班人+换班日期  或  替班人+换班日期  完全相同
 * 存在：在换班日期下方提示:换班记录重复，请重新选择   
 */
function addBatchChangeCheckChangeTimeValidation(validatorName, errorMessage) {
	let validation = $KMSSValidation();
	validation.addValidator(validatorName, errorMessage, function (v, e, o) {
		let result = true;
		//触发当前行信息
		var nowtr = $(e).parents(".inputselectsgl").parents(".xform_datetime").parent("td").parent("tr");//当前行对象		
		if(nowtr.length==0){
			nowtr = $(e).parents(".xform_address").parent("td").parent("tr");//当前行对象		
		}
		var now_tr_change_user = $(nowtr).find("[name*='fd_change_apply_user.id']").val();//当前行换班人	
		var now_tr_swap_user = $(nowtr).find("[name*='fd_change_swap_user.id']").val();//当前行替班人	
		var now_tr_change_time = $(nowtr).find("[name*='fd_change_date']").val(); //当前行换班日期
		
		//取当前触发时间Obj的id，避免后续当前行和循环时的当前行比较
        var nowId = $(nowtr).find("[name*='fd_change_date']")[0].id;
        //换班日期为空，则不进行比较
        if(!nowId || !now_tr_change_time){
        	return result;
        }
        //触发行换班人和替班人都为空，则不进行比较
        if(!now_tr_change_user && !now_tr_swap_user){
        	return result;
        }
        
        //PC端需做火狐浏览器兼容
        var userAgent = navigator.userAgent;
		var isFirefox = userAgent.indexOf("Firefox") > -1;
		if (!isFirefox) { //判断是否火狐浏览器
			now_tr_change_time = now_tr_change_time.replace('-','/');
		}
		now_tr_change_time = new Date(now_tr_change_time).getTime();	
		
		//取所有行的换班日期
		var fd_change_dates = $("#TABLE_DL_fd_change_table").find("[name*='fd_change_date']");
		if(fd_change_dates.length > 1){
			//换班日期数量超过一个才进行比较,循环每一行与触发行进行比较
			for (var i = 0; i < fd_change_dates.length; i++) {        		
				var trId = fd_change_dates[i].id;
				if(nowId == trId){
					continue;//触发行和循环到的当前行是同一行时不做比较
				}
				var tr_fd_change_date = $(fd_change_dates[i]).val();//每行的换班日期
				if(!tr_fd_change_date){
					continue;//循环的其它行里换班日期为空则不与触发行进行比较
				}
				
				if (!isFirefox) { //火狐浏览器兼容
					tr_fd_change_date = tr_fd_change_date.replace('-','/');
        		}
        		var trTime = new Date(tr_fd_change_date).getTime();
        		if(now_tr_change_time - trTime == 0){//两个时间相等，则校验换班人和替班人
        			var tr_change_user = $(fd_change_dates[i]).parents(".inputselectsgl").parents(".xform_datetime").parent("td").parent("tr").find("[name*='fd_change_apply_user.id']").val();
        			var tr_swap_user = $(fd_change_dates[i]).parents(".inputselectsgl").parents(".xform_datetime").parent("td").parent("tr").find("[name*='fd_change_swap_user.id']").val();
        			if(tr_change_user && tr_change_user == now_tr_change_user){
        				result = false;
        			}
        			if(tr_swap_user && tr_swap_user == now_tr_swap_user){
        				result = false;
        			}
        		}
			}
        } 
		return result;
	});
}

/**
 * 校验还班日期必须晚于换班日期-明细表批量换班套件
 * 校验单行
 */
function addBatchChangeCompareTimeValidation(validatorName, errorMessage) {
	let validation = $KMSSValidation();
	validation.addValidator(validatorName, errorMessage, function (v, e, o) {
		let result = true;
		//触发当前行信息
		var nowtr = $(e).parents(".inputselectsgl").parents(".xform_datetime").parent("td").parent("tr");//当前行对象		
		var change_date = $(nowtr).find("[name*='fd_change_date']").val();//当前行换班日期	
		var return_date = $(nowtr).find("[name*='fd_return_date']").val(); //当前行还班日期
		if(!change_date || !return_date){
			return result;
		}
		//PC端需做火狐浏览器兼容
        var userAgent = navigator.userAgent;
		var isFirefox = userAgent.indexOf("Firefox") > -1;
		if (!isFirefox) { //判断是否火狐浏览器
			change_date = change_date.replace('-','/');
			return_date = return_date.replace('-','/');
		}
		var change_date = new Date(change_date).getTime();	
		var return_date = new Date(return_date).getTime();	
		if(return_date <= change_date){//当还班日期小于等于换班日期时，返回false
			result = false;
		}
		return result;
	});
}

/**
 * 校验还班记录重复，请重新选择-明细表批量换班套件
 * 校验触发行与其他行之间是否存在：换班人+还班日期  或  替班人+还班日期  完全相同
 * 存在：在还班日期下方提示:还班记录重复，请重新选择 
 */
function addBatchChangeCheckReturnTimeValidation(validatorName, errorMessage) {
	let validation = $KMSSValidation();
	validation.addValidator(validatorName, errorMessage, function (v, e, o) {
		let result = true;
		//触发当前行信息
		var nowtr = $(e).parents(".inputselectsgl").parents(".xform_datetime").parent("td").parent("tr");//当前行对象	
		if(nowtr.length==0){
			nowtr = $(e).parents(".xform_address").parent("td").parent("tr");//当前行对象		
		}
		var change_user = $(nowtr).find("[name*='fd_change_apply_user.id']").val();//当前行换班人	
		var swap_user = $(nowtr).find("[name*='fd_change_swap_user.id']").val();//当前行替班人	
		var return_date= $(nowtr).find("[name*='fd_return_date']").val(); //当前行还班日期
		
		//取当前触发还班日期Obj的id，避免后续当前行和循环时的当前行比较
        var nowId = $(nowtr).find("[name*='fd_return_date']")[0].id;
        if(!nowId || !return_date){
        	return result;
        }
        //触发行换班人和替班人都为空，则不进行比较
        if(!change_user && !swap_user){
        	return result;
        }
        
        //PC端需做火狐浏览器兼容
        var userAgent = navigator.userAgent;
		var isFirefox = userAgent.indexOf("Firefox") > -1;
		if (!isFirefox) { //判断是否火狐浏览器
			return_date = return_date.replace('-','/');
		}
		return_date = new Date(return_date).getTime();	
		
		//取所有行的还班日期
		var fd_return_dates = $("#TABLE_DL_fd_change_table").find("[name*='fd_return_date']");
		if(fd_return_dates.length > 1){
			//还班日期数量超过一个才进行比较,循环每一行与触发行进行比较
			for (var i = 0; i < fd_return_dates.length; i++) {        		
				var trId = fd_return_dates[i].id;
				if(nowId == trId){
					continue;//触发行和循环到的当前行是同一行时不做比较
				}
				var tr_fd_return_date = $(fd_return_dates[i]).val();//每行的还班日期
				if(!tr_fd_return_date){
					continue;//循环的其它行里还班日期为空则不与触发行进行比较
				}
				
				if (!isFirefox) { //火狐浏览器兼容
					tr_fd_return_date = tr_fd_return_date.replace('-','/');
        		}
        		var trTime = new Date(tr_fd_return_date).getTime();
        		if(return_date - trTime == 0){//两个时间相等，则校验换班人和替班人
        			var tr_change_user = $(fd_return_dates[i]).parents(".inputselectsgl").parents(".xform_datetime").parent("td").parent("tr").find("[name*='fd_change_apply_user.id']").val();
        			var tr_swap_user = $(fd_return_dates[i]).parents(".inputselectsgl").parents(".xform_datetime").parent("td").parent("tr").find("[name*='fd_change_swap_user.id']").val();
        			if(tr_change_user && tr_change_user == change_user){
        				result = false;
        			}
        			if(tr_swap_user && tr_swap_user == swap_user){
        				result = false;
        			}
        		}
			}
        } 
		return result;
	});
}

/**
 * 校验是否可换班接口返回的信息-明细表批量换班套件
 * 
 */
function addBatchChangeCheckInterfaceValidation(validatorName, errorMessage) {
	let validation = $KMSSValidation();
	validation.addValidator(validatorName, errorMessage, function (v, e, o) {
		let result = true;
		var tr = $(e).parents(".inputselectsgl").parents(".xform_datetime").parent("td").parent("tr");//当前行对象		
		var fd_change_date_span = $(tr).find(".fd_change_date_span");
		if($(fd_change_date_span).hasClass("noChange")){
			var msg = $(tr).find(".fd_return_date_tip").text();
			if(msg){
				var interfacevalidator = this.getValidator("checkChangeInterface");
				interfacevalidator.error = msg;
			}
			result = false;
		}
		return result;
	});
}

/**
 * 校验换班人和还班人相同时，还班日期不能为空-明细表批量换班套件
 * 
 */
function addBatchChangecheckChangeUserValidation(validatorName, errorMessage) {
	let validation = $KMSSValidation();
	validation.addValidator(validatorName, errorMessage, function (v, e, o) {
		let result = true;
		var tr = $(e).parents(".inputselectsgl").parents(".xform_datetime").parent("td").parent("tr");//当前行对象		
		var change_user = $(tr).find("[name*='fd_change_apply_user.id']").val();//当前行换班人	
		var swap_user = $(tr).find("[name*='fd_change_swap_user.id']").val();//当前行替班人	
		var return_date= $(tr).find("[name*='fd_return_date']").val();//当前行还班日期
		if(change_user == swap_user && !return_date){
			result = false;
		}
		return result;
	});
}

/**
 * 校验加班开始时间不得大于结束时间-明细表批量加班套件
 * 校验加班开始时间和结束时间需为同一天-明细表批量加班套件
 */
function addBatchCompareWorkTimeValidation (validatorName, errorMessage) {
	let validation = $KMSSValidation();
	validation.addValidator(validatorName, errorMessage, function (v, e, o) {
		let result = true;
		var tr = $(e).parents(".inputselectsgl").parents(".xform_datetime").parent("td").parent("tr");//当前行对象
		var fd_work_start_time = $(tr).find("[name*='fd_work_start_time']").val();
		var fd_work_end_time = $(tr).find("[name*='fd_work_end_time']").val();
		if(fd_work_start_time && fd_work_end_time){
			/*#156142-钉钉套件批量加班，IE浏览器选择同一天也提示不能跨天-开始*/
			fd_work_start_time = fd_work_start_time.replace(/-/g, "/");
			fd_work_end_time = fd_work_end_time.replace(/-/g, "/");
			/*#156142-钉钉套件批量加班，IE浏览器选择同一天也提示不能跨天-结束*/
			fd_work_start_time = new Date(fd_work_start_time);	
			fd_work_end_time = new Date(fd_work_end_time);	
			var interfacevalidator = this.getValidator("compareWorkTime");
			if(fd_work_end_time.getTime() - fd_work_start_time.getTime() < 0){
				console.log(e.name.indexOf("fd_work_end_time"));
				if(e.name.indexOf("fd_work_end_time") == -1){
					interfacevalidator.error = "加班开始时间不能大于加班结束时间";
				}
				result = false;
			}
			/*#155133-钉钉套件加班问题-开始*/
			/*if(fd_work_start_time.getDate() != fd_work_end_time.getDate()){
				interfacevalidator.error = "加班开始时间和结束时间需为同一天，跨天可新增明细行";
				result = false;
			}*/
			var ms = fd_work_end_time.getTime() - fd_work_start_time.getTime();
			//获取相差小时数
			var hour = ms / 1000 / 60 / 60;
			//如果超过一天则提示
			if (hour > '24') {
				interfacevalidator.error = "加班开始时间和结束时间需为同一天，跨天可新增明细行";
				result = false;
			}
			/*#155133-钉钉套件加班问题-结束*/
		}
		return result;
	});
}

/**
 * 校验加班时间重复-明细表批量加班套件
 */
function addBatchRepeatWorkTimeValidation(validatorName, errorMessage) {
	let validation = $KMSSValidation();
	validation.addValidator(validatorName, errorMessage, function (v, e, o) {
        let result = true;
        if(!v){
        	return;
        }
        /* ************************************** 取当前触发行的数据 start ********************************************************** */
        var nowTr = $(e).parent(".input").parent(".inputselectsgl").parent("xformflag").parent(".xform_datetime").parent("td").parent("tr");
        //当前行的开始时间、结束时间、单位 开始时间段、结束时间段
        var nowStartTime = $(nowTr).find("[name*='fd_work_start_time']").val();
        var nowEndTime = $(nowTr).find("[name*='fd_work_end_time']").val();
        var nowUserId = $(nowTr).find("[name*='fd_work_user.id']").val();
        //当前行index
        var nowTrIndex = nowTr[0].rowIndex;
        /* ************************************** 取当前触发行的数据 end ********************************************************** */
       
        if(!nowStartTime || !nowEndTime || !nowTrIndex || !nowUserId || nowUserId.length==0){
        	return result;
        }
        var trs = $("#TABLE_DL_fd_batch_work_table").find("tr"); 
        for (var i = 0; i < trs.length; i++) {
        	//1、当前行不和循环到的当前行比较
        	var rowIndex = trs[i].rowIndex;
        	if(rowIndex==nowTrIndex){
        		continue;
        	}
        	//2、取每一行的开始、结束时间
			var start_time_tr = $(trs[i]).find("[name*='fd_work_start_time']").val();
			var end_time_tr = $(trs[i]).find("[name*='fd_work_end_time']").val();
			var user_id_tr = $(trs[i]).find("[name*='fd_work_user.id']").val();
			
			if(!start_time_tr || !end_time_tr){//循环到的行加班开始时间或加班结束时间为空：跳过
				continue;
			}
			/* ************************************** check start ********************************************************** */
			nowStartTime = new Date(nowStartTime);
			nowEndTime = new Date(nowEndTime);
			start_time_tr = new Date(start_time_tr);
			end_time_tr = new Date(end_time_tr);
			//1、先将当前触发行的加班人和其他行做比对，只要当前行有一个加班人在其他行被包含，则进行比对
			var userids = nowUserId.split(";");
			for (var a = 0; a < userids.length; a++) {
				if(!result){
					break;
				}
				if(user_id_tr.indexOf(userids[a]) != -1){
					//A行 startTime <= B行开始时间 && A行endTime >= B 行 endtime
					if(nowStartTime.getTime() <= start_time_tr.getTime() && nowEndTime.getTime() >= end_time_tr.getTime()){
						result =false;
					}	
					if(nowStartTime.getTime() >= start_time_tr.getTime() && nowStartTime.getTime() <= end_time_tr.getTime()){
						result =false;
					}	
					if(nowEndTime.getTime() >= start_time_tr.getTime() && nowEndTime.getTime() <= end_time_tr.getTime()){
						result =false;
					}	
				}
			}
			if(!result){
				break;
			}				
			/* ************************************** check end ********************************************************** */
        }
       return  result;
	});
}