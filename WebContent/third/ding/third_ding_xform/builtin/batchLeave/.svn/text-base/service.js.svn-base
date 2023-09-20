/**
 * 批量请假流程函数接口
 */
Com_IncludeFile("common.js", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/js/","js",true);
Com_IncludeFile("ding_right.js", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/js/","js",true);
Com_IncludeFile("validatorUtil.js", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/js/","js",true);
Com_IncludeFile("dingTool.js", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/js/","js",true);
$(document).ready(function() {//#147931-请假套件，当前申请人打开请假套件，年假可以选到时间
var ekpUserid = "";
seajs.use(["lui/jquery","lui/topic", "lui/util/str", "lui/util/env", "lui/dialog"],function($, topic, strUtil, env, dialog){
	// 根据当前行不同的单位，时间校验格式不同
	window.Ding_Time_Validate_Change = function(obj,type){
		$(obj).parents(".xform_Select").parent("td").parent("tr").find("input[name*='fd_item_duration']").attr("validate","validateDuration validateSunDuration validateIsLeave");
		if(type === "date"){
			$(obj).parents(".xform_Select").parent("td").parent("tr").find("input[name*='fd_leave_start_time']").attr("validate","required __date repeatTime repeatContainTime");
			$(obj).parents(".xform_Select").parent("td").parent("tr").find("input[name*='fd_leave_end_time']").attr("validate","required __date compareTime repeatTime repeatContainTime");
		
		}else{			
			$(obj).parents(".xform_Select").parent("td").parent("tr").find("input[name*='fd_leave_start_time']").attr("validate","required __datetime repeatTime repeatContainTime");
			$(obj).parents(".xform_Select").parent("td").parent("tr").find("input[name*='fd_leave_end_time']").attr("validate","required __datetime compareTime repeatTime repeatContainTime");
		}
	}

	window.Ding_Time_Validate_init_Change = function(obj,type){
		if(type === "date"){
			$(obj).attr("validate","required __date repeatTime repeatContainTime");
			$(obj).attr("validate","required __date compareTime repeatTime repeatContainTime");
		}else{
			$(obj).attr("validate","required __datetime repeatTime repeatContainTime");
			$(obj).attr("validate","required __datetime compareTime repeatTime repeatContainTime");
		}
	}
	
	//取请假类型leaveCode
	function getOptionValue(record){
		record = eval("("+record+")");
		var value = record["leaveCode"];
		return value;
	}
	
	//取请假类型-展示
	function getOptionText(record){
		record = eval("("+record+")");
		var name = record["leaveName"];
		var unit = record["leaveViewUnit"];
		var unitTxt = (unit === "day" || unit === "halfDay") ? "天" : "小时";
		// record["leave_rest"]为null则不需要显示剩余额度
		if(record.hasOwnProperty("leaveBalanceQuotaVo")){
		     if(unit === "day" || unit === "halfDay"){
		       name += "（剩余" + record["leaveBalanceQuotaVo"]["quotaNumPerDay"] + unitTxt + ")";
		     }else{
		       name += "（剩余" + record["leaveBalanceQuotaVo"]["quotaNumPerHour"] + unitTxt + ")";
		     }
		}
		return name;
	}

	//取请假类型对应的余额
	function getOptionDataValue(record){
		record = eval("("+record+")");
		var unit = record["leaveViewUnit"];
		var dataValue ="";
		// record["leave_rest"]为null则不需要显示剩余额度
		if(record.hasOwnProperty("leaveBalanceQuotaVo")){
		     if(unit === "day" || unit === "halfDay"){
		       dataValue = record["leaveBalanceQuotaVo"]["quotaNumPerDay"];
		     }else{
		       dataValue = record["leaveBalanceQuotaVo"]["quotaNumPerHour"];
		     }
		}
		return dataValue;
	}
	//取请假类型对应的单位
	function getOptionUnit(record){
		record = eval("("+record+")");
		var unit = record["leaveViewUnit"];
		return unit;
	}

	window.initLeave = function(flag){
		_this = this;
		var types = $("#TABLE_DL_fd_batch_leave_table").find("[name*='fd_leave_type']");
		var optCopy = $("span.opt_del_style");
		if(types.length==0 || optCopy.length == 0){
			setTimeout("initpage();","500");
			return;
		} else{
			if(!flag){
				ekpUserid = $("[name='extendDataFormInfo.value(fd_leave_user.id)']").val();
			}
			for (var i = 0; i < types.length; i++) {
				var fd_leave_type = $(types[i]);
				if(!flag && $(fd_leave_type).hasClass("isInits")){//已初始化，则跳过
					continue;
				}
				initLeaveType(fd_leave_type);
				$(fd_leave_type).parent("xformflag").parent(".xform_Select").parent("td").parent("tr").find("[name*='fd_item_duration']").parents(".xform_inputText").css("display","none");
			}
		}
				
		$("[name*='fd_leave_type']").on('change',function(){
			var optvalue = $(this).children('option:selected').val();//取当前请假类型value值
			var optname = $(this).children('option:selected').text();//取当前请假类型name值
			var optunit = $(this).children('option:selected').attr("data-unit");//取当前请假类型单位值
			var yue = $(this).children('option:selected').attr("data-value");
			$(this).parents(".xform_Select").parent("td").find("[name*='fd_type_code']").val(optvalue);
			$(this).parents(".xform_Select").parent("td").find("[name*='fd_type_name']").val(optname);
			$(this).parents(".xform_Select").parent("td").find("[name*='type_unit']").val(optunit);
			$(this).parents(".xform_Select").parent("td").find("[name*='type_ye']").val(yue);
			//#147931-请假套件，当前申请人打开请假套件，年假可以选到时间
			//_this.LeaveTypeChange($(this),optvalue,optname,optunit);
			LeaveTypeChange($(this),optvalue,optname,optunit);

			var startDiv = $(this).parents(".xform_Select").parent("td").parent("tr").find("[name*='fd_leave_start_time']").parent("div.input").parent("div.inputselectsgl");
			var endDiv = $(this).parents(".xform_Select").parent("td").parent("tr").find("[name*='fd_leave_end_time']").parent("div.input").parent("div.inputselectsgl");
			$(startDiv).css("width","150px");
			$(endDiv).css("width","150px");
		});
		//开始时间、结束时间、开始时间段、结束时间段值变更，检查本行这四个参数是否都有值，有则请求时长-- start
		$("[name*='fd_leave_start_time']").on('blur',function(){
			var trObj = $(this).parents(".inputselectsgl").parents(".xform_datetime").parent("td").parent("tr");
			if(Ding_IsAllDateSet(trObj,$(this))){
				Ding_RequestCalcuateDuration(trObj);
			}
			//timeObjFocus(trObj);
		});
		$("[name*='fd_leave_end_time']").on('blur',function(){
			var trObj = $(this).parents(".inputselectsgl").parents(".xform_datetime").parent("td").parent("tr");
			if(Ding_IsAllDateSet(trObj,$(this))){
				Ding_RequestCalcuateDuration(trObj);
			}
			//timeObjFocus(trObj);
		});
		$("[name*='fd_start_time_one']").on('change',function(){
			var trObj = $(this).parents(".xform_Select").prev(".xform_datetime").parent("td").parent("tr");
			if(Ding_IsAllDateSet(trObj,$(this))){
				Ding_RequestCalcuateDuration(trObj);
			}
			//timeObjFocus(trObj);
		});
		$("[name*='fd_end_time_one']").on('change',function(){
			var trObj = $(this).parents(".xform_Select").prev(".xform_datetime").parent("td").parent("tr");
			if(Ding_IsAllDateSet(trObj,$(this))){
				Ding_RequestCalcuateDuration(trObj);
			}
			//timeObjFocus(trObj);
		});
		//开始时间、结束时间、开始时间段、结束时间段值变更，检查本行这四个参数是否都有值，有则请求时长-- end
	}

	//初始化请假类型下拉框
	window.initLeaveType = function(fd_leave_type){
		 var optCopy = $("span.opt_del_style");
		 if(!fd_leave_type || optCopy.length == 0){
			 setTimeout("initpage();","500");
			 return;//不初始化
		 }
		 var status = DingCommonnGetStatus();
		 if("view" == status){
			return;
		 }
		fd_leave_type.empty();
		fd_leave_type.append('<option value="">==请选择==</option>');
		var url = Com_Parameter.ContextPath +'third/ding/thirdDingAttendance.do?method=getBizsuiteTypes&findRest=true&leaveType=batch';
		
		var fdId = "";
		var pram = {};
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
		url +="&methodCode="+status+"&fdId="+fdId+"&ekpUserid="+ekpUserid;
		$.ajax({
			url : url,
			dataType : "json",
			type : "GET",
	    	jsonp:"jsonpcallback",
	    	success: function(data){
	    		if(data&&data.success && null != data.result){
	    			$("[name*='extendDataFormInfo.value(fd_leave_user.name)']").removeClass("notInDingList");
	    			$("[name*='extendDataFormInfo.value(fd_leave_user.id)']").parent(".inputselectsgl").parent("xformflag").parent(".xform_address").parent("td").find(".validation-advice").remove();
    				var prams=[];
    				var typeValues= "";
    				var typeNames= "";
    				var unit= "";
    				var dataValue= "";
    				for(var i = 0;i < data.result.form_data_list.length;i++){
    					var record = data.result.form_data_list[i]["extend_value"];
    					var recordJson = eval("("+record+")");
    					typeNames = getOptionText(record);
    					typeValues = getOptionValue(record);
    					unit = getOptionUnit(record);
    					dataValue = getOptionDataValue(record);
    					fd_leave_type.append('<option value="' + typeValues + '"  data-unit="'+unit+'" data-value="'+dataValue+'">' + typeNames + '</option>');
    				}	
    				$(fd_leave_type).addClass("isInits");
	    		}else{
	    			$("[name*='extendDataFormInfo.value(fd_leave_user.name)']").addClass("notInDingList");
	    			networkFailure("请确认请假人是否已做钉钉映射");
	    		}
	    	},error : function(data) {
	    		networkFailure("请假类型初始化异常");
	       	  	console.log("请假类型初始化异常。url:"+url);
	         }
		});
	}
	
	// 当前行请求计算时长
	window.Ding_RequestCalcuateDuration = function(obj){
		var unit = $(obj).find("[name*='fd_item_unit']").val();
		var dw = "天";
		var $duration = $(obj).find("[name*='fd_item_duration']");
		$($duration).attr("validate","validateDuration validateSunDuration validateIsLeave");
		var requestUrl = getDurationUrl(obj);
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
        			$(obj).find("[name*='fd_type_extend_value']").val(record);
        			record = eval("("+record+")");
        			try{
        				if(unit === "hour"){
        					duration = parseFloat(record["durationInHour"]);
        				}else{
        					duration = parseFloat(record["durationInDay"]);
        				}
        			}catch(e){
        				duration = record["durationInHour"];
        				console.log("can't parse " + record["durationInDay"] +" to float!");
        			}
        			
        			if("hour"==unit){
        				dw = "小时";
        			}
        			$duration.val(duration+" "+dw);//将计算的当前行时长赋值在当前行的请假时长
        			$duration.data("durationInfo", record);
        			$(obj).find("[name*='fd_item_duration']").parents(".xform_inputText").css("display","inline-block");
        			$(obj).find("[name*='fd_item_duration']").css("display","inline-block");
        			$(obj).find("[name*='fd_item_duration']").css("text-align","center");
					if(duration == "0"){
						//dialog.alert("时长不能为0");
						console.log("时长不能为0:"+JSON.stringify(data));
					}
					//当前行时长赋值完成，更新总时长
					refreshSumDuration(obj);
					//判断是否能请假
					isLeave(obj);
					$(obj).find(".fd_item_duration_span").css("display","none");
					$($duration).focus();
        		}else{
        			console.log(data.errmsg);
        		}
        	}
		});
	}
	
	function isLeave(obj){//obj为行对象
		var leave_code = $(obj).find("[name*='fd_type_code']").val();
		var durationInfo = $(obj).find("[name*='fd_type_extend_value']").val();
		var unit = $(obj).find("[name*='fd_leave_type']").children('option:selected').attr("data-unit");
		if(!leave_code || !durationInfo || !unit){
			return;
		}
		var docSubject = $("#docSubjectSpan").html();//审批高级版
		if('false' == Com_Parameter.attendanceEnabled){
			docSubject = $("[name='docSubject']").val();//ekp
		}			
		var reason = $("[name='extendDataFormInfo.value(fd_leave_remark)']").val();			
		var url = Com_Parameter.ContextPath+'third/ding/thirdDingAttendance.do?method=canLeave&leaveType=batch&ekpUserid='+ekpUserid;
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
		params["startTime"] = Ding_GetStartTime(obj);
		params["finishTime"] = Ding_GetEndTime(obj);
		params["leaveCode"] = leave_code;
		params["leaveTimeInfo"] = durationInfo;
		params["methodCode"] = status;
		params["docSubject"] = docSubject;
		params["unit"] = unit;
		params["reason"] = reason;
		params["fdId"] = fdId;
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
        						var errmsg ="假期余额已不足,请刷新页面后重新申请";
        						console.log("是否可请假接口校验：【余额不足】，请刷新页面后重新申请");
        						//dialog.alert(errmsg);
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
        			console.log(data.errmsg);
        			console.log("is Leave?："+JSON.stringify(data));
        			//dialog.alert(msg);
        		}
        	}
		});
		if(!flag){
			$(obj).find("[name*='fd_item_duration']").addClass("validateIsLeave");
			$(obj).find("[name*='fd_item_duration']").focus();
		}else{
			$(obj).find("[name*='fd_item_duration']").removeClass("validateIsLeave");
		}
		return flag;
	}
	
	//更新总时长
	function refreshSumDuration(obj){
		var fd_item_durations = $(obj).parent("tbody").parent("table").find("[name*='fd_item_duration']");
		if(fd_item_durations.length==0){
			return;
		}
		var sumDurations="";
		var days = 0.00;
		var hours = 0.00;
		for (var i = 0; i < fd_item_durations.length; i++) {
			var durations = $(fd_item_durations[i]).val();
			var fd_item_unit = $(fd_item_durations[i]).parents(".xform_inputText").parent("td").find("[name*='fd_item_unit']").val();
			durations = parseFloat(durations.split(" ")[0]);
			if(!durations){
				continue;
			}
			if("hour"==fd_item_unit){
				hours+=durations;
			}else{
				days+=durations;
			}
			//console.log(durations);
		}
		if(days>0){
			sumDurations += days+"天  ";
		}
		if(hours>0){
			sumDurations += hours+"小时  ";
		}
		if(""==sumDurations){
			sumDurations="0";
		}
		$("[name*='extendDataFormInfo.value(fd_sum_duration)']").val(sumDurations);
	}
	
	//获取请假时长url
	function getDurationUrl(obj){
		var requestUrl = "/third/ding/thirdDingAttendance.do?method=preCalculate&leaveType=batch" +
		"&startTime=!{startTime}&finishTime=!{finishTime}&leaveCode=!{leaveCode}&methodCode=!{methodCode}&fdId=!{fdId}&ekpUserid=!{ekpUserid}";
		// 假期类型详情
		var leave_code = $(obj).find("[name*='fd_leave_type']").find("option:selected").val();
		if(!leave_code){
			//dialog.alert("请先选择请假类型。");
			console.log("请先选择请假类型。");
			return false;
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
		params["startTime"] = Ding_GetStartTime(obj);
		params["finishTime"] = Ding_GetEndTime(obj);
		params["leaveCode"] = leave_code;
		params["methodCode"] = methodCode;
		params["fdId"] = fdId;
		params["ekpUserid"] = ekpUserid;
		return strUtil.variableResolver(requestUrl, params);
	}
	
	//提交事件
	window.Ding_Validate_Submit = function(){
		console.log("提交了");
		var $duration = $("[name='extendDataFormInfo.value(duration)']");
		if($duration.val() == "0"){
			dialog.alert("总时长不能为0");
			return false;
		}
		var items = $("input[name*='fd_item_duration)']");
		if(items.length>0){
			for (var i = 0; i < items.length; i++) {
				var fd_item_duration = $(items[i]).val();
				if(fd_item_duration==''||fd_item_duration==null||fd_item_duration==undefined||fd_item_duration=='0'){
					dialog.alert("系统正在计算请假时长,请稍后提交");
					return false;
					break;
				}
			}
		}
		return true;
	}

	function tableAddTrEvent(v,newRow){		
		//var newRow = argus.row;
		//处理带数据复制行时，新出的行时间单位
		var fd_item_duration = $(newRow).find("[name*='fd_item_duration']").val();
		if(fd_item_duration){
			$(newRow).find(".fd_item_duration_span").css("display","none");
		}else{
			$(newRow).find("[name*='fd_item_duration']").parents(".xform_inputText").css("display","none");
		}
		
		var fd_leave_type = $(newRow).find("[name*='fd_leave_type']").children('option:selected').val();
		if(fd_leave_type){//复制行
			var fd_item_unit = $(newRow).find("[name*='fd_item_unit']").val();
			var leaveType = $(newRow).find("[name*='fd_leave_type']");
			
			//根据单位显示上下午字段
			Ding_HalfDay_Display(leaveType,fd_item_unit);				
			// 变更当前行的开始时间和结束时间的onclick事件
			Ding_ChangeDateWidgetEvent(leaveType,fd_item_unit);
			
			if("hour"==fd_item_unit){
				Ding_Time_Validate_Change(leaveType,"dateTime");
			}else{
				Ding_Time_Validate_Change(leaveType,"date");
			}				
		}
		
		var userId = Com_Parameter.CurrentUserId;//取当前登录用户
		if(userId!=ekpUserid){ //如果请假人不是当前登录用户，新增行时，重新初始化当前行的请假
			var fd_leave_type_tr = $(newRow).find("[name*='fd_leave_type']");
			initLeaveType(fd_leave_type_tr);
		}
		
		//新增完更新总时长
		refreshSumDuration(newRow);
		isLeave(newRow);
		initLeave(false);
	}
	
	//注册事件
	window.selectDateType = function(){
		var optCopy = $("span.opt_del_style");
		if(!optCopy || optCopy.length==0){
			setTimeout("initEvent();","500");
			return;
		}
		
		$(".opt_add_style").parent("span").on('click',function(){
			var newRow = $(this).parents(".tr_normal_opt").prev("tr");
			tableAddTrEvent($(this),newRow); 
		});
		
		$(".opt_copy_style").on('click',function(){
			var newRow = $(this).parents("#TABLE_DL_fd_batch_leave_table").find(".tr_normal_opt").prev("tr");
			tableAddTrEvent($(this),newRow); 			
			$(newRow).find("[name*='fd_leave_start_time']").focus();
			$(newRow).find("[name*='fd_leave_end_time']").focus();
		});
		
		//请假人发生变更触发事件
		changeUser();
		//操作栏按钮监听事件不生效，暂时监听table的点击事件，后续优化
		//优化：#171301改为监听行删除事件'table-delete-new'
		$("#TABLE_DL_fd_batch_leave_table").on('table-delete-new',function(){
			//删除完更新总时长
			deleteEventFun();
		});
	}

	function deleteEventFun(){
		var _table = $("table[showStatisticRow]");
		var tr_objs = $(_table).find("tr");
		if(tr_objs.length>0){
			refreshSumDuration(tr_objs[0]);
		}else{
			$("[name*='extendDataFormInfo.value(fd_sum_duration)']").val("0");
		}
	}
	
	function timeObjFocus(trObj){
		var starttime = $(trObj).find("[name*='fd_leave_start_time']");
		var endtime = $(trObj).find("[name*='fd_leave_end_time']");
		if(starttime.val()){
			$(starttime).focus();
		}
		if(endtime.val()){
			$(endtime).focus();
		}
	}
	
	function Ding_IsAllDateSet(obj,_this){
		var fd_leave_type = $(obj).find("select[name*='fd_leave_type']").children('option:selected').val();
		if(!fd_leave_type){
			$(obj).find("select[name*='fd_leave_type']").focus();
			console.log("需先选择请假类型");
			$(_this).val("");
			return;
		}
		var fd_item_unit = $(obj).find("[name*='fd_item_unit']").val();
		if(!fd_item_unit){
			dialog.alert("未获取到假期单位，请重新选择请假类型");
			return;
		}	
		var fd_leave_start_time = $(obj).find("input[name*='fd_leave_start_time']");
		var fd_leave_end_time = $(obj).find("input[name*='fd_leave_end_time']");
		var fd_start_time_one = $(obj).find("select[name*='fd_start_time_one']").children('option:selected').val();
		var fd_end_time_one = $(obj).find("select[name*='fd_end_time_one']").children('option:selected').val();
		
		var startTime = fd_leave_start_time.val();
		var endTime = fd_leave_end_time.val();
		// 开始时间和结束事件是否已经设置值
		if(fd_leave_start_time.val() && fd_leave_end_time.val()){
			// 【单位】是否设置为半天，是则校验
			if(fd_item_unit === "halfDay"){
				if(fd_start_time_one && fd_end_time_one){
					startTime += " "+fd_start_time_one;
					endTime += " "+fd_end_time_one;
					if(checkDateTime(startTime,endTime,fd_item_unit)){
						return false;//开始时间大于结束时间
					}
					return true;
				}
			}else{
				if(checkDateTime(startTime,endTime,fd_item_unit)){
					return false;//开始时间大于结束时间
				}
				return true;
			}
		}
		return false;
	}
	
	//开始时间大于结束时间返回true
	function checkDateTime(startTime,endTime,unit){
		var falg = true;
		if(!startTime || !endTime || !unit){
			return falg;//任意一个为空，返回true，即：开始时间大于结束时间
		}
		var date1;
		var date2;
		if("halfDay"==unit){//带上下午的比较
			date1 = startTime.split(" ")[0];
			date2 = endTime.split(" ")[0];
			var time1 = startTime.split(" ")[1];
			var time2 = endTime.split(" ")[1];
			if(!time1 || !time2){
				return true;
			}
			date1 = new Date(date1).getTime();
			date2 = new Date(date2).getTime();
			if(date1 == date2){
				if("AM" == time1){
					falg = false;
				}
				if("PM" == time1 && "PM" ==time2){
					falg = false;
				}
			}
			if(date1<date2){
				falg = false;
			}
		}else{
			if("hour" == unit){
				startTime=startTime+":01";
				endTime=endTime+":01";
				 var userAgent = navigator.userAgent;
				 var isOpera = userAgent.indexOf("Firefox") > -1;
				 if (!isOpera) { //判断是否火狐浏览器
			    	startTime = startTime.replace('-','/');
			    	endTime = endTime.replace('-','/');
				 }
			}
			date1 = new Date(startTime).getTime();
			date2 = new Date(endTime).getTime();
			if(date1<=date2){
				falg = false;
			}
		}
		return falg
	}
	
	window.Ding_Init = function(){
		var status = DingCommonnGetStatus();
		switch(status){
			case "add":
				Ding_Init_Edit(status);
				break;
			case "edit":
				Ding_Init_Edit(status);
				Ding_Init_Edit_Show(status);
				break;
			case "view":
				Ding_Init_View();
				break;
		}
	}

	Com_AddEventListener(window, "load", selectDateType);
	Com_AddEventListener(window, "load", Ding_Init);
});

window.changeUser = function(){
	$("[name*='extendDataFormInfo.value(fd_leave_user']").on('change',function(){
		var userId = $("[name='extendDataFormInfo.value(fd_leave_user.id)']").val();
		if(ekpUserid != userId){
			//1、重新赋值给全局变量ekpUserid
			ekpUserid = userId;
			//2、明细表所有请假类型重新初始化
			initLeave(true);
			//3、清空每行内已选择数据
			clearEveryTrData();				
		}
	});
}

/********************************* ↓↓↓↓↓↓时间选择↓↓↓↓↓↓ *******************************************/
// 当前行的时间选择--选择请假类型触发
function Ding_DateSelect(name, unit){	
	// 根据不同的单位，时间选择不同的选择
	switch(unit){
		case "day":
		case "halfDay":
			selectDate(name,null,function(c){
				__CallXFormDateTimeOnValueChange(c,__xformDispatch);
			});
			break;
		case "hour":
			selectDateTime(name,null,function(c){
				__CallXFormDateTimeOnValueChange(c,__xformDispatch);
			});
			break;
		default:
			break;
	}
}
/********************************* ↑↑↑↑↑↑时间选择↑↑↑↑↑↑ *******************************************/
/********************************* ↓↓↓↓↓↓时长计算↓↓↓↓↓↓ *******************************************/
// 判断当前行-是否所有的日期控件都已经设置值
function Ding_GetStartTime(obj){	
	var rs = "";
	var fd_leave_start_time = $(obj).find("input[name*='fd_leave_start_time']");
	var fd_item_unit = $(obj).find("[name*='fd_item_unit']").val();
	rs = fd_leave_start_time.val();
	if(fd_item_unit === "halfDay"){
		var fd_start_time_one = $(obj).find("select[name*='fd_start_time_one']").children('option:selected').val();
		rs += " " + fd_start_time_one;
	}
	return rs;
}

function Ding_GetEndTime(obj){
	var rs = "";
	var fd_leave_end_time = $(obj).find("input[name*='fd_leave_end_time']");
	var fd_item_unit = $(obj).find("[name*='fd_item_unit']").val();
	rs = fd_leave_end_time.val();
	if(fd_item_unit === "halfDay"){
		var fd_end_time_one = $(obj).find("select[name*='fd_end_time_one']").children('option:selected').val();
		rs += " " + fd_end_time_one;
	}
	return rs;
}

/********************************* ↑↑↑↑↑↑时长计算↑↑↑↑↑↑ *******************************************/
function Ding_Init_View(){
	//#147931-请假套件，当前申请人打开请假套件，年假可以选到时间
	//this._export(this);
	_export();
	//1、查看页屏蔽明细表中每一行的系统自动计算提示
	$(".fd_item_duration_span").css("display","none");
	//2、查看页根据每行的单位，控制展示的时间格式
	var units = $("input[name*='fd_item_unit']");
	if(units.length>0){
		for (var i = 0; i < units.length; i++) {
			var unit = $(units[i]).val();
			if(!unit){
				continue;
			}
			
			if('day'==unit || 'halfDay' == unit){
				var fd_leave_start_time_tr = $(units[i]).parents(".xform_inputText").parent("td").parent("tr").find("input[name*='fd_leave_start_time']");
				var fd_leave_end_time_tr = $(units[i]).parents(".xform_inputText").parent("td").parent("tr").find("input[name*='fd_leave_end_time']");
				//办结查看页和未办结的查看页构造不一样，需区分处理
				if(fd_leave_start_time_tr.length==0 || fd_leave_end_time_tr.length==0){//办结查看页...
					fd_leave_start_time_tr  = $(units[i]).parent("xformflag").parent(".xform_inputText").parent("td").parent("tr").find("xformflag[property*='fd_leave_start_time']");
					fd_leave_end_time_tr = $(units[i]).parent("xformflag").parent(".xform_inputText").parent("td").parent("tr").find("xformflag[property*='fd_leave_end_time']");
					if($(fd_leave_start_time_tr).text()){
						var startTimeView = $(fd_leave_start_time_tr).text().substring(0,10);
						fd_leave_start_time_tr.text(startTimeView);
					}
					if($(fd_leave_end_time_tr).text()){
						var endTimeView = $(fd_leave_end_time_tr).text().substring(0,10);
						fd_leave_end_time_tr.text(endTimeView);
					}				
				}else{//未办结查看页...
					//console.log($(fd_leave_start_time_tr).val());
					if($(fd_leave_start_time_tr).val()){
						var startTimeView = $(fd_leave_start_time_tr).val().substring(0,10);
						fd_leave_start_time_tr.val(startTimeView);
					}
					if($(fd_leave_end_time_tr).val()){
						var endTimeView = $(fd_leave_end_time_tr).val().substring(0,10);
						fd_leave_end_time_tr.val(endTimeView);
					}
				}
			}
		}
	}
	//3、查看页展示明细表每行的请假类型
	//$("[name*='fd_leave_type']").parents('.xform_Select').text("890")
	var fd_type_names = $("input[name*='fd_type_name']");
	//console.log("fd_type_names:"+fd_type_names.val());
	if(fd_type_names.length>0){
		for (var t = 0; t < fd_type_names.length; t++) {
			var fd_type_name = $(fd_type_names[t]).val();
			if(!fd_type_name){
				continue;
			}
			 $(fd_type_names[t]).parents(".xform_inputText").parent("td").append("<span>"+fd_type_name+"</span>");
		}
	}
	//4、查看页隐藏编辑按钮
	/*#147096-ekp15 钉钉套件驳回后，在移动端页面可以编辑-开始*/
	/*var btn_span = $(".lui_normal_hearder_btn");
	if(btn_span.length==0){
		var btn = $("div.lui_toolbar_btn");
		if(btn.length>0){
			for (var l = 0; l < btn.length; l++) {
				var textname = $(btn[l]).find("div.lui_widget_btn_txt").text();
				if("编辑"==textname){
					$(btn[l]).parent("td").css("display","none");
					break;
				}
			}
		}
	}else{
		if(btn_span && btn_span.length>0){
			for (var s = 0; s < btn_span.length; s++) {*/
				//var btnText = $(btn_span[s]).text().replace(/\s*/g,"");
				/*if("编辑"==btnText){
					$(btn_span[s]).parent(".lui_normal_hearder_btnWrap").css("display","none");
					break;
				}
			}
		}
	}*/
	/*#147096-ekp15 钉钉套件驳回后，在移动端页面可以编辑-结束*/
}

//根据单位显示上下午字段
function Ding_HalfDay_Display(obj,optunit){
	// 根据不同的单位，时间选择不同的选择	
	let from = $(obj).parents(".xform_Select").parent("td").parent("tr").find("select[name*='fd_start_time_one']");
	let to = $(obj).parents(".xform_Select").parent("td").parent("tr").find("select[name*='fd_end_time_one']");
	// 查看页面的元素为input
	if(from.length <= 0) {
		from = $("input[name*='fd_start_time_one']");
	}
	if(to.length <= 0) {
		to = $("input[name*='fd_end_time_one']");
	}
	switch(optunit){
		case "halfDay":
			// 显示上下午
			from.closest("div").css("display","inline-block");
			to.closest("div").css("display","inline-block");
			/*#155331-请假套件，请假时年假，上下午不选，只选日期，可以提交，时长为0-开始*/
			//如果日期类型选择存在半天（上午、下午）的情况，则给半天下拉选择框添加上必填的校验
			from.closest("div").find("select[name*='fd_start_time_one']").attr("validate","required");
			from.closest("div").find("select[name*='fd_start_time_one']").attr("subject","请假开始时间段");
			//#155377-请假开始时间默认为上午，请假结束时间默认为下午
			from.closest("div").find("select[name*='fd_start_time_one']").val("AM");
			to.closest("div").find("select[name*='fd_end_time_one']").attr("validate","required");
			to.closest("div").find("select[name*='fd_end_time_one']").attr("subject","请假结束时间段");
			//#155377-请假开始时间默认为上午，请假结束时间默认为下午
			to.closest("div").find("select[name*='fd_end_time_one']").val("PM");
			/*#155331-请假套件，请假时年假，上下午不选，只选日期，可以提交，时长为0-结束*/
			break;
		case "day":
		case "hour":
			/*#155331-请假套件，请假时年假，上下午不选，只选日期，可以提交，时长为0-开始*/
			if(typeof(from.closest("div").find("select[name*='fd_start_time_one']").attr("validate")) != "undefined"){
				//如果日期类型选择不存在半天（上午、下午）的情况，则给半天下拉选择框去掉必填的校验
				//开始时间
				//#155377-请假开始时间默认为上午，请假结束时间默认为下午
				from.closest("div").find("select[name*='fd_start_time_one']").val("");
				from.closest("div").find("select[name*='fd_start_time_one']").removeAttr("validate");
				from.closest("div").find("select[name*='fd_start_time_one']").removeAttr("subject");
				//删掉提示文字的Div元素
				var validateFrom = from.closest("div").find("select[name*='fd_start_time_one']").attr("__validate_serial");
				$("#advice-" + validateFrom).remove();
				from.closest("div").find("select[name*='fd_start_time_one']").removeAttr("__validate_serial");
				//结束时间
				//#155377-请假开始时间默认为上午，请假结束时间默认为下午
				to.closest("div").find("select[name*='fd_end_time_one']").val("");
				to.closest("div").find("select[name*='fd_end_time_one']").removeAttr("validate");
				to.closest("div").find("select[name*='fd_end_time_one']").removeAttr("subject");
				//删掉提示文字的Div元素
				var validateTo = to.closest("div").find("select[name*='fd_end_time_one']").attr("__validate_serial");
				$("#advice-" + validateTo).remove();
				to.closest("div").find("select[name*='fd_end_time_one']").removeAttr("__validate_serial");
			}
			/*#155331-请假套件，请假时年假，上下午不选，只选日期，可以提交，时长为0-结束*/
			
			// 隐藏上下午
			from.closest("div").hide();
			to.closest("div").hide();
			break;
		default:
			console.log("can't match unit("+ optunit +")!");
			break;
	}
}

function Ding_Init_Edit(status){
	// 添加监听事件
	Ding_Edit_InitEvent();
	//修改页提交事件
	Com_Parameter.event["submit"].push(Ding_Validate_Submit);

	//#147931-请假套件，当前申请人打开请假套件，年假可以选到时间
	//this._export(this);
	_export();
}

function _export(v){
	//#147931-请假套件，当前申请人打开请假套件，年假可以选到时间
	//this.initLeave(false);
	initLeave(false);
	window.initpage = function(){
		//#147931-请假套件，当前申请人打开请假套件，年假可以选到时间
		//v.initLeave(false);
		initLeave(false);
	}
	window.initEvent = function(){
		//#147931-请假套件，当前申请人打开请假套件，年假可以选到时间
		//v.selectDateType();
		selectDateType();
	}
}

function Ding_Init_Edit_Show(status){
	//1、修改页屏蔽明细表中每一行的系统自动计算提示
	$(".fd_item_duration_span").css("display","none");
	//2、修改页根据每行的单位，控制展示的时间格式
	var units = $("input[name*='fd_item_unit']");
	if(units.length>0){
		for (var i = 0; i < units.length; i++) {
			var unit = $(units[i]).val();
			if(!unit){
				continue;
			}
			
			if('day'==unit || 'halfDay' == unit){
				var fd_leave_start_time_tr = $(units[i]).parents(".xform_inputText").parent("td").parent("tr").find("input[name*='fd_leave_start_time']");
				var fd_leave_end_time_tr = $(units[i]).parents(".xform_inputText").parent("td").parent("tr").find("input[name*='fd_leave_end_time']");
				//console.log($(fd_leave_start_time_tr).val());
				if($(fd_leave_start_time_tr).val()){
					var startTimeView = $(fd_leave_start_time_tr).val().substring(0,10);
					fd_leave_start_time_tr.val(startTimeView);
					Ding_Time_Validate_init_Change(fd_leave_start_time_tr,"date");
				}
				if($(fd_leave_end_time_tr).val()){
					var endTimeView = $(fd_leave_end_time_tr).val().substring(0,10);
					fd_leave_end_time_tr.val(endTimeView);
					Ding_Time_Validate_init_Change(fd_leave_end_time_tr,"date");
				}

			}else{
				var fd_start_time_one_obj =  $(units[i]).parents(".xform_inputText").parent("td").parent("tr").find("select[name*='fd_start_time_one']");
				var fd_end_time_one_obj =  $(units[i]).parents(".xform_inputText").parent("td").parent("tr").find("select[name*='fd_end_time_one']");
				$(fd_start_time_one_obj).parents(".xform_Select").css("display","none");
				$(fd_end_time_one_obj).parents(".xform_Select").css("display","none");
			}
		}
	}
	//3、查看页展示明细表每行的请假类型
	var fd_leave_type = $("select[name*='fd_leave_type']");
	if(fd_leave_type.length>0){
		for (var w = 0; w < fd_leave_type.length; w++) {
			var fd_type_code = $(fd_leave_type[w]).parents(".xform_Select").parent("td").find("input[name*='fd_type_code']").val();
			if(!fd_type_code){
				continue;
			}
			$(fd_leave_type[w]).val(fd_type_code);
			
		}
	}
}

function Ding_Edit_InitEvent(){
	//注册事件
	//selectDateType();
	addBatchCompareTimeValidation('compareTime',"请假开始时间不能大于请假结束时间");
	addBatchDurationValidation('validateDuration',"请假时长超过了假期余额，请重新选择");
	addBatchSunDurationValidation('validateSunDuration',"请假时长超过了假期余额，请重新选择");
	addBatchRepeatTimeValidation('repeatTime',"请假时间重复，请重新选择");
	addBatchRepeatContainTimeValidation('repeatContainTime',"请假时间重复，请重新选择");
	addBatchIsLeaveValidation('validateIsLeave',"存在已有请假记录或者其它异常");
	addBatchUserValidation('checkUserISInDingList',"请确认请假人是否已做钉钉映射");
	$("[name*='extendDataFormInfo.value(fd_leave_user.name)']").attr("validate","required checkUserISInDingList");
}
//移除开始时间和结束时间的onclick事件
function Ding_ChangeDateWidgetEvent(obj,optunit){
	var fd_leave_start_time_input = $(obj).parents(".xform_Select").parent("td").parent("tr").find("input[name*='fd_leave_start_time']").closest(".inputselectsgl");
	if(fd_leave_start_time_input.length>0){
		fd_leave_start_time_input.removeAttr("onclick");		
		fd_leave_start_time_input.on("click", function(){
			var fd_leave_start_time = $(obj).parents(".xform_Select").parent("td").parent("tr").find("input[name*='fd_leave_start_time']");
			var fd_leave_start_time_name = $(fd_leave_start_time).attr("name");
			optunit = $(obj).parents(".xform_Select").parent("td").parent("tr").find("[name*='fd_item_unit']").val();
			Ding_DateSelect(fd_leave_start_time_name, optunit);
		});
	}
	
	var fd_leave_end_time_input = $(obj).parents(".xform_Select").parent("td").parent("tr").find("input[name*='fd_leave_end_time']").closest(".inputselectsgl");
	if(fd_leave_end_time_input.length>0){
		fd_leave_end_time_input.removeAttr("onclick");
		fd_leave_end_time_input.on("click", function(){			
			var fd_leave_end_time = $(obj).parents(".xform_Select").parent("td").parent("tr").find("input[name*='fd_leave_end_time']");
			var fd_leave_end_time_name = $(fd_leave_end_time).attr("name");
			optunit = $(obj).parents(".xform_Select").parent("td").parent("tr").find("[name*='fd_item_unit']").val();
			Ding_DateSelect(fd_leave_end_time_name, optunit);
		});
	}
}

//选择请假类型事件
function LeaveTypeChange(obj,optvalue,optname,optunit){
	if(optunit){
		//存每行的单位
		$(obj).parents(".xform_Select").parent("td").parent("tr").find("[name*='fd_item_unit']").val(optunit);		
		// 清空日期选择的已选项
		ClearDateValue(obj);		
		// 根据当前行不同的单位，时间选择不同的选择
		switch(optunit){
			case "day":
			case "halfDay":
				Ding_Time_Validate_Change(obj,"date");
				break;
			case "hour":
				Ding_Time_Validate_Change(obj,"dateTime");
				break;
			default:
				console.log("can't match unit("+ optunit +")!");
				break;
		}
		//根据单位显示上下午字段
		Ding_HalfDay_Display(obj,optunit);
		
		// 变更当前行的开始时间和结束时间的onclick事件
		Ding_ChangeDateWidgetEvent(obj,optunit);
	}else{
		console.log("can't find option info("+ optunit +")!");
	}
}
$(function(){
	//初始化请假类型下拉
	var status = DingCommonnGetStatus();
	 if("view" != status){
		initLeave(false);
	 }
	changeUser();
});

//清空日期选择的已选项
window.ClearDateValue = function(obj){
	var fd_leave_start_time = $(obj).parents(".xform_Select").parent("td").parent("tr").find("input[name*='fd_leave_start_time']");
	var fd_leave_end_time = $(obj).parents(".xform_Select").parent("td").parent("tr").find("input[name*='fd_leave_end_time']");
	var fd_start_time_one = $(obj).parents(".xform_Select").parent("td").parent("tr").find("select[name*='fd_start_time_one']");
	var fd_end_time_one = $(obj).parents(".xform_Select").parent("td").parent("tr").find("select[name*='fd_end_time_one']");
	
	fd_leave_start_time.val("");
	fd_leave_end_time.val("");
	// 移除校验提醒
	if(window.Reminder){
		if(fd_leave_start_time.length > 0){
			new Reminder(fd_leave_start_time).hide();
		}
		if(fd_leave_end_time.length > 0){
			new Reminder(fd_leave_end_time).hide();
		}
	}
	$(fd_start_time_one).val("");
	$(fd_end_time_one).val("");
}

window.clearEveryTrData = function(){
	var trs = $("#TABLE_DL_fd_batch_leave_table").find("tr");
	if(trs.length>0){
		for (var i = 0; i < trs.length; i++) {
			if($(trs[i]).hasClass("tr_normal_opt") || $(trs[i]).hasClass("tr_normal_title")){
				continue
			}
			var fd_leave_type = $(trs[i]).find("[name*='fd_leave_type']");
			ClearDateValue(fd_leave_type);
			$(fd_leave_type).parents(".xform_Select").parent("td").parent("tr").find("[name*='fd_item_duration']").val("");
			$(fd_leave_type).parents(".xform_Select").parent("td").parent("tr").find("[name*='fd_type_code']").val("");
			$(fd_leave_type).parents(".xform_Select").parent("td").parent("tr").find("[name*='fd_type_name']").val("");
			$(fd_leave_type).parents(".xform_Select").parent("td").parent("tr").find("[name*='fd_type_extend_value']").val("");
			$(fd_leave_type).parents(".xform_Select").parent("td").parent("tr").find("[name*='fd_item_unit']").val("");
			$(fd_leave_type).parents(".xform_Select").parent("td").parent("tr").find("[name*='fd_item_duration']").css("display","none");
			$(fd_leave_type).parents(".xform_Select").parent("td").parent("tr").find(".fd_item_duration_span").css("display","table-row");
		}
	}
	$("[name='extendDataFormInfo.value(fd_sum_duration)']").val("0");
}
});//#147931-请假套件，当前申请人打开请假套件，年假可以选到时间