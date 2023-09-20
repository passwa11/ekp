/**
 * 请假流程函数接口
 */
Com_IncludeFile("common.js", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/js/","js",true);
Com_IncludeFile("ding_right.js", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/js/","js",true);
Com_IncludeFile("validatorUtil.js", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/js/","js",true);

// 由于【单位】使用频率很高，直接设置为全局变量，方便使用
var Ding_Leave_Unit_Var = "day";

seajs.use(["lui/jquery","lui/topic", "lui/util/str", "lui/util/env", "lui/dialog"],function($, topic, strUtil, env, dialog){
	
	// 清空日期选择的已选项
	function ClearDateValue(){
		var $fromTime = $("input[name*='from_time']");
		var $toTime = $("input[name*='to_time']");
		$fromTime.val("");
		$toTime.val("");
		// 移除校验提醒
		if(window.Reminder){
			if($fromTime.length > 0)
				new Reminder($fromTime).hide();
			if($toTime.length > 0)
				new Reminder($toTime).hide();
		}
		
		$("select[name*='from_half_day']").val("");
		$("select[name*='to_half_day']").val("");
		
		let $duration = $("[name='extendDataFormInfo.value(duration)']");
		$duration.val("");
		$duration.data("durationInfo", null);
		$duration.nextAll('.form_tr_context_desc').addClass("duration_desc_empty");
		$duration.nextAll('.form_tr_context_desc').removeClass("duration_desc");
		//时长的标题颜色
		let titleLable = $duration.closest("tr").find("td:first > .xform_label");
		titleLable.css("color", "");
		let cssText = titleLable.attr("style") + ";color: #C7C7CC !important;";
		titleLable.css("cssText", cssText);
	}
	
	function LeaveTypeChange(info){
		if(info){
			info = eval("("+info+")");
			Ding_Leave_Unit_Var = info["leaveViewUnit"];
			$("[name='extendDataFormInfo.value(unit)']").val(info["leaveViewUnit"]);
			var calculationMode = info["naturalDayLeave"] ? 0 : 1;
			$("[name='extendDataFormInfo.value(calculate_model)']").val(calculationMode);
			$("[name='extendDataFormInfo.value(leave_txt)']").val(info["leaveName"]);
			// 清空日期选择的已选项
			ClearDateValue();
			// 假期单位
			var leaveUnit = info["leaveViewUnit"];
			// 根据不同的单位，时间选择不同的选择
			switch(leaveUnit){
				case "halfDay":
					Ding_Time_Validate_Change("date");
					break;
				case "day":
					Ding_Time_Validate_Change("date");
					break;
				case "hour":
					Ding_Time_Validate_Change("dateTime");
					break;
				default:
					console.log("can't match unit("+ leaveUnit +")!");
					break;
			}
			Ding_HalfDay_Display();
			
			// 变更时长的显示文字
			Ding_ChangeUnit();

			//选择后文字颜色改变
			$("[name='extendDataFormInfo.value(leave_code)']").addClass('selected');
		}else{
			console.log("can't find option info("+ info +")!");
		}
	}

	window.Ding_Time_Validate_Change = function(type){
		if(type === "date"){
			$("input[name*='from_time']").attr("validate","required __date dateLimit(31)");
			$("input[name*='to_time']").attr("validate","required __date compareTime");
		}else{
			$("input[name*='from_time']").attr("validate","required __datetime dateLimit(31)");
			$("input[name*='to_time']").attr("validate","required __datetime compareTime");
		}
	}
	
	// 请求计算时长
	window.Ding_RequestCalcuateDuration = function(){
		var requestUrl = getDurationUrl();
		requestUrl = env.fn.formatUrl(requestUrl);
		$.ajax({
			url : requestUrl,
			dataType : "json",
			type : "GET",
        	jsonp:"jsonpcallback",
        	success: function(data){
        		if(data.errcode === 0){
        			var duration = 0;
        			var record = data.result.form_data_list[0]["extend_value"];
        			record = eval("("+record+")");
        			try{
        				if(Ding_Leave_Unit_Var === "hour"){
        					duration = parseFloat(record["durationInHour"]);
        				}else{
        					duration = parseFloat(record["durationInDay"]);
        				}
        			}catch(e){
        				duration = record["durationInHour"];
        				console.log("can't parse " + record["durationInDay"] +" to float!");
        			}
        			var $duration = $("[name='extendDataFormInfo.value(duration)']");
        			$duration.val(duration);
        			$duration.data("durationInfo", record);
        			$("[name='extendDataFormInfo.value(extend_value)']").val(data.result.form_data_list[0]["extend_value"]);
        			$("#extendValue").val(data.result.form_data_list[0]["extend_value"]);
					$duration.nextAll('.form_tr_context_desc').removeClass("duration_desc_empty");
					$duration.nextAll('.form_tr_context_desc').addClass("duration_desc");
					if(duration == "0"){
						dialog.alert("时长不能为0");
					}
        		}else{
        			console.log(data.errmsg);
        		}
        	}
		});
	}
	/*判断是否能请假*/
	window.isLeave = function (){
		var leave_code = $("[name='extendDataFormInfo.value(leave_code)']").find("option:selected").val();
		var docSubject = $("#docSubjectSpan").html();//审批高级版
		if('false' == Com_Parameter.attendanceEnabled){
			docSubject = $("[name='docSubject']").val();//ekp
		}
		
		var reason = $("[name='extendDataFormInfo.value(reason)']").val();
		if(!leave_code){
			dialog.alert("请先选择请假类型");
			return false;//选择请假类型
		}
		var durationInfo = $("[name='extendDataFormInfo.value(extend_value)']").val();
		if(!durationInfo){
			dialog.alert("请先选择时间！");
			return false;//未请求时长接口
		}
		console.log(durationInfo);
		var url = Com_Parameter.ContextPath+'third/ding/thirdDingAttendance.do?method=canLeave';
		var requestUrl = strUtil.variableResolver(url, params);
		var params = {};
		var status = DingCommonnGetStatus();
		var fdId = "";
		if("edit" == status){
			var urlParm = window.location.search;
			if (urlParm.indexOf("?") != -1) {
				var strs = urlParm.substr(1).split("&");
				for(var i=0;i<strs.length;i++){
			        var kv = strs[i].split('=');
			        if(kv[0] == 'fdId'){
			        	fdId = kv[1];
			        	break;
			        }
				}
			}
		}
		
		params["startTime"] = Ding_GetStartTime();
		params["finishTime"] = Ding_GetEndTime();
		params["leaveCode"] = leave_code;
		params["leaveTimeInfo"] = durationInfo;
		params["methodCode"] = status;
		params["docSubject"] = docSubject;
		params["unit"] = Ding_Leave_Unit_Var;
		params["reason"] = reason;
		params["fdId"] = fdId;
		console.log(params);
		var flag = true;
		var msg = "";
		$.ajax({
			url : requestUrl,
			data:params,
			async:false,
			dataType : "json",
			type : "POST",
        	success: function(data){
        		if(data && data.success){
        			//success为true放行
        			if(data.result && data.result.form_data_list){
        				var record = data.result.form_data_list[0]["extend_value"];
        				if(record){
        					record = eval("("+record+")");
        					var canLeave = record["canLeave"];
        					if(!canLeave){
        						var errmsg ="余额不足";
        						dialog.alert(errmsg);
        						flag = false;
        					}        					
        				}
        			}
        			
        		}else{
        			flag = false;
        			var errcode = data.errcode;
        			if(831000==errcode){
        				msg = "不存在的请假类型";
        			}else if(831001==errcode){
        				msg = "请假开始/结束时间非法";
        			}else if(831002==errcode){
        				msg = "请假余额不足";
        			}else if(831003==errcode){
        				msg = "已有请假记录";
        			}else if(831004==errcode){
        				msg = "（考勤）时间校验不通过";
        			}else{
        				msg = data.errmsg
        				if(!msg){
        					msg = "网络异常，请稍后再试。";
        				}
        			}
        			dialog.alert(msg);
        		}
        	}
		});
		return flag;
	}
	
	function getDurationUrl(){
		var requestUrl = "/third/ding/thirdDingAttendance.do?method=preCalculate" +
		"&startTime=!{startTime}&finishTime=!{finishTime}&leaveCode=!{leaveCode}&methodCode=!{methodCode}&fdId=!{fdId}";
		// 假期类型详情
		var leave_code = $("[name='extendDataFormInfo.value(leave_code)']").find("option:selected").val();
		if(!leave_code){
			dialog.alert("请先选择请假类型");
			return false;//选择请假类型
		}
		var methodCode = DingCommonnGetStatus();
		var fdId = "";
		if("edit" == methodCode){
			var urlParm = window.location.search;
			if (urlParm.indexOf("?") != -1) {
				var strs = urlParm.substr(1).split("&");
				for(var i=0;i<strs.length;i++){
			        var kv = strs[i].split('=');
			        if(kv[0] == 'fdId'){
			        	fdId = kv[1];
			        	break;
			        }
				}
			}
		}
		var params = {};
		params["startTime"] = Ding_GetStartTime();
		params["finishTime"] = Ding_GetEndTime();
		params["leaveCode"] = leave_code;
		params["methodCode"] = methodCode;
		params["fdId"] = fdId;
		return strUtil.variableResolver(requestUrl, params);
	}
	
	// 弹出时长查询明细窗口
	window.Ding_DurationDialog = function(){
		var durationInfo = $("[name='extendDataFormInfo.value(duration)']").data("durationInfo");
		durationInfo = eval("("+durationInfo+")");
		if(durationInfo){
			rightShow("计算明细","将在出勤统计中记为","共请假"+ durationInfo["detailList"][0][approveInfo]["durationInDay"] +"小时", RightPanel_BuildProgressStyleContent(formatDurationInfo(durationInfo)));		
		}else{
			dialog.alert("请先选择时间");
		}
	}
	
	// TODO
	function formatDurationInfo(durationInfo){
		
	}
	
	window.Ding_Validate_Submit = function(){
		var $duration = $("[name='extendDataFormInfo.value(duration)']");
		if($duration.val() == "0"){
			dialog.alert("时长不能为0");
			return false;
		}
		return true;
	}
	
	// 订阅请假类型变更事件
	topic.channel("ding").subscribe("leave.type.change",LeaveTypeChange);
});

/********************************* ↓↓↓↓↓↓时间选择↓↓↓↓↓↓ *******************************************/
// 移除开始时间和结束时间的onclick事件
function Ding_ChangeDateWidgetEvent(){
	var $startTimeDiv = $("input[name*='from_time']").closest(".inputselectsgl");
	$startTimeDiv.removeAttr("onclick");
	$startTimeDiv.on("click", function(){
		Ding_DateSelect("from_time", "from_half_day");
	});
	
	var $endTimeDiv = $("input[name*='to_time']").closest(".inputselectsgl");
	$endTimeDiv.removeAttr("onclick");
	$endTimeDiv.on("click", function(){
		Ding_DateSelect("to_time", "to_half_day");
	});
}

// 时间选择
function Ding_DateSelect(name, halfDayName){
	name = "extendDataFormInfo.value("+ name +")";
	// 根据不同的单位，时间选择不同的选择
	switch(Ding_Leave_Unit_Var){
		case "day":
		case "halfDay":
			selectDate(name,null,function(c){
				//未选上下午则自动选择上午
				let halfDay = $("select[name*='" + halfDayName + "']");
				if (!halfDay.val() && $("input[name='" + name + "']").val()) {
					halfDay.val('AM');
				}
				__CallXFormDateTimeOnValueChange(c,__xformDispatch);
				if(Ding_IsAllDateSet()){
					Ding_RequestCalcuateDuration();
				}
			});
			break;
		case "hour":
			selectDateTime(name,null,function(c){
				__CallXFormDateTimeOnValueChange(c,__xformDispatch);
				if(Ding_IsAllDateSet()){
					Ding_RequestCalcuateDuration();
				}
			});
			break;
		default:
			break;
	}
}
/********************************* ↑↑↑↑↑↑时间选择↑↑↑↑↑↑ *******************************************/

/********************************* ↓↓↓↓↓↓时长计算↓↓↓↓↓↓ *******************************************/
// 判断是否所有的日期都已经设置值
function Ding_IsAllDateSet(){
	var $fromTime = $("input[name*='from_time']");
	var $toTime = $("input[name*='to_time']");
	// 开始时间和结束事件是否已经设置值
	if($fromTime.val() && $toTime.val()){
		// 【单位】是否设置为半天，是则校验
		if(Ding_Leave_Unit_Var === "halfDay"){
			if($("select[name*='from_half_day']").val() && $("select[name*='to_half_day']").val()){
				return true;
			}
		}else{
			return true;
		}
	}
	return false;
}

function Ding_GetStartTime(){
	var rs = "";
	var $fromTime = $("input[name*='from_time']");
	rs = $fromTime.val();
	if(Ding_Leave_Unit_Var === "halfDay"){
		rs += " " + $("select[name*='from_half_day']").val();
	}
	return rs;
}

function Ding_GetEndTime(){
	var rs = "";
	var $toTime = $("input[name*='to_time']");
	rs = $toTime.val();
	if(Ding_Leave_Unit_Var === "halfDay"){
		rs += " " + $("select[name*='to_half_day']").val();
	}
	return rs;
}

/********************************* ↑↑↑↑↑↑时长计算↑↑↑↑↑↑ *******************************************/

/**
 * 请假类型逻辑
 */
function Ding_LeaveCodeChanged(){
	seajs.use(["lui/topic"], function(topic){
		topic.channel("ding").subscribe("leave.type.unit.change",function(){
			if($(this).val != null) {
				//选请假类型后取消禁用
				removeAllDisabled();
			}
		});
	});
}

function Ding_Init(){
	Ding_Leave_Unit_Var = $("input[name*='extendDataFormInfo.value(unit)']").val() || "day";
	var status = DingCommonnGetStatus();
	switch(status){
		case "add":
		case "edit":
			Ding_Init_Edit(status);
			break;
		case "view":
			Ding_Init_View();
			break;
	}
	// 显示【开始时间】和【结束时间】
	$("#from_time_wrap").show();
	$("#to_time_wrap").show();
	if('false' == Com_Parameter.attendanceEnabled){
		var _disableds = $("._disabled");
		for (var i = 0; i<_disableds.length;i++) {
			var width = _disableds[i].style.width.replace("px","");
			var height = _disableds[i].style.height.replace("px","");
			var left = _disableds[i].style.left.replace("px","");
			left = parseFloat(left)+4+"px";
			height = parseFloat(height)-2+"px";
			width = parseFloat(width)-100+"px";
			
			_disableds[i].style.left = left;
			_disableds[i].style.width = width;
			_disableds[i].style.height = height;
			_disableds[i].style.marginTop = "2px";			
 		 }		
		document.getElementById("from_time_wrap").style.display ='inline-block';
		document.getElementById("to_time_wrap").style.display ='inline-block';		
	}
}

function Ding_Init_View(){
	// 根据单位显示上下午字段
	Ding_HalfDay_Display();
	
	// 设置【开始时间】和【结束时间】的值格式化
	var $fromXFormFlag = $("xformflag[flagid='from_time']");
	var $toXFormFlag = $("xformflag[flagid='to_time']");
	switch(Ding_Leave_Unit_Var){
		case "halfDay":
		case "day":
			var form_time_flag = $("input[name='extendDataFormInfo.value(from_time)']").val();
			var to_time_flag = $("input[name='extendDataFormInfo.value(to_time)']").val();			
			$fromXFormFlag.html(new Date(form_time_flag.trim()).format("yyyy-MM-dd"));
			$toXFormFlag.html(new Date(to_time_flag.trim()).format("yyyy-MM-dd"));
			break;
		case "hour":
			break;
		default:
			console.log("can't match unit("+ leaveUnit +")!");
			break;
	}
	
	// 显示请假类型
	var $leave_txt = $("input[name='extendDataFormInfo.value(leave_txt)']");
	$leave_txt.closest("td").append($leave_txt.val());
	
	// 时长单位初始化
	Ding_ChangeUnit();
}

/**
 * 变更时长的单位
 */
function Ding_ChangeUnit(){
	// 假期单位
	var leaveUnitName = "时长";
	switch (Ding_Leave_Unit_Var) {
		case "halfDay":
		case "day":
			leaveUnitName = "时长(天)";
			break;
		case "hour":
			leaveUnitName = "时长(小时)";
			break;
		default:
			console.log("can't match unit(" + leaveUnit + ")!");
			break;
	}
	$("[name='extendDataFormInfo.value(duration)']").closest("tr").find("label.xform_label:first").html(leaveUnitName);
}

//根据单位显示上下午字段
function Ding_HalfDay_Display(){
	// 根据不同的单位，时间选择不同的选择
	let from = $("select[name='extendDataFormInfo.value(from_half_day)']");
	let to = $("select[name='extendDataFormInfo.value(to_half_day)']");
	// 查看页面的元素为input
	if(from.length <= 0) {
		from = $("input[name='extendDataFormInfo.value(from_half_day)']");
	}
	if(to.length <= 0) {
		to = $("input[name='extendDataFormInfo.value(to_half_day)']");
	}
	switch(Ding_Leave_Unit_Var){
		case "halfDay":
			// 显示上下午
			from.closest("div").css("display","inline-block");
			to.closest("div").css("display","inline-block");
			break;
		case "day":
		case "hour":
			// 隐藏上下午
			from.closest("div").hide();
			to.closest("div").hide();
			break;
		default:
			console.log("can't match unit("+ leaveUnit +")!");
			break;
	}
}

function Ding_Init_Edit(status){
	// 初始化布局
	Ding_Edit_InitLayout();
	
	// 添加监听事件
	Ding_Edit_InitEvent();
	
}

function Ding_Edit_InitLayout(){
	// 1.根据【单位】设置上下午的显示和隐藏
	Ding_HalfDay_Display();
	// 2.根据【开始类型】或清【结束时间】设置遮罩
	var $from_time = $("[name='extendDataFormInfo.value(from_time)']");
	var $to_time = $("[name='extendDataFormInfo.value(to_time)']");
	if(!$from_time.val() && !$to_time.val()){
		//未选请假类型时禁用
		setDisabled($from_time.closest("tr"));
		setDisabled($to_time.closest("tr"));
		setDisabled($("input[name*='extendDataFormInfo.value(duration)']").closest("tr"));
		$from_time.attr('tabindex', -1);
		$to_time.attr('tabindex', -1);
	}else{
		// 3.设置【开始时间】和【结束时间】的值格式化
		switch(Ding_Leave_Unit_Var){
			case "halfDay":
			case "day":
				$from_time.val(new Date($from_time.val().trim()).format("yyyy-MM-dd"));
				$to_time.val(new Date($to_time.val().trim()).format("yyyy-MM-dd"));
				Ding_Time_Validate_Change("date");
				break;
			case "hour":
				Ding_Time_Validate_Change("dateTime");
				break;
			default:
				console.log("can't match unit("+ leaveUnit +")!");
				break;
		}
	}
}

function Ding_Edit_InitEvent(){
	// 变更开始时间和结束时间的onclick事件
	Ding_ChangeDateWidgetEvent();

	//校验器初始化
	let from_time = $("input[name*='extendDataFormInfo.value(from_time)']");
	let to_time = $("input[name*='extendDataFormInfo.value(to_time)']");
	addCompareTimeValidation('compareTime', from_time, to_time, "开始时间不能大于结束时间");
	//addDateLengthValidation('dateMaxLength(length)', from_time, to_time, "请假时长不得超过{maxLength}天");
	addDateLengthValidation('dateLimit(length)', from_time, new Date().format("yyyy-MM-dd HH:mm"), "已超过审批截止时间");

	//请假类型逻辑
	Ding_LeaveCodeChanged();

	$("select[name*='from_half_day']").on("change",function(){
		if(Ding_IsAllDateSet()){
			Ding_RequestCalcuateDuration();
		}
	});
	$("select[name*='to_half_day']").on("change",function(){
		if(Ding_IsAllDateSet()){
			Ding_RequestCalcuateDuration();
		}
	});
	$(".check_balance").on("click",Ding_DurationDialog);
	
	// 时长单位初始化
	Ding_ChangeUnit();
	
	Com_Parameter.event["submit"].push(Ding_Validate_Submit);
}

Com_AddEventListener(window, "load", Ding_Init);