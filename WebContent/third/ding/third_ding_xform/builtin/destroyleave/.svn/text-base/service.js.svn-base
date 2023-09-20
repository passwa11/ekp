Com_IncludeFile("common.js", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/js/","js",true);
Com_IncludeFile("validatorUtil.js", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/js/","js",true);
Com_IncludeFile("common.css", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/css/","css",true);
Com_IncludeFile("cancelLevelDetail.css", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/css/","css",true);

var unitStr = "天";//当前请假单的单位
var durationTime="";//当前请假单的-请假时长
var dw ="day";//当前请假单请假单位
var fromTimes ="";//请假开始时间
var toTimes ="";//请假结束时间
var levelCode ="";//当前请假单请假类型
var from_half_day_str ="AM";
var to_half_day_str ="AM";
var unit='';
var detailDate;//按明细销假时，所需的时长接口返回数据
var classInfo='';
var methodStatus;
seajs.use(["lui/jquery","lui/topic", "lui/util/str", "lui/util/env", "lui/dialog"],function($, topic, strUtil, env, dialog){
	//页面初始化
	function Ding_Init(){	 
		noShowMobile();//隐藏关于移动端的行
		var status = DingCommonnGetStatus();
		methodStatus = status;
		switch(status){
			case "add":
			case "edit":
				Ding_Init_Edit(status);
				break;
			case "view":
				Ding_Init_View();
				break;
		}
	}
	//隐藏移动端的卡片行
	function noShowMobile(){
		//请假开始时间-移动端
		var level_start_time_input = $("[id*='extendDataFormInfo.value(level_start_time)']");
		var level_start_time_tr = $(level_start_time_input).parents('.xform_inputText').parent('td').parent('tr');
		$(level_start_time_tr).css("display","none");
		//请假结束时间-移动端
		var level_end_time_input = $("[id*='extendDataFormInfo.value(level_end_time)']");
		var level_end_time_tr = $(level_end_time_input).parents('.xform_inputText').parent('td').parent('tr');
		$(level_end_time_tr).css("display","none");
		//请假时长-移动端
		var level_time_input = $("[id*='extendDataFormInfo.value(level_time)']");
		var level_time_tr = $(level_time_input).parents('.xform_inputText').parent('td').parent('tr');
		$(level_time_tr).css("display","none");		
		//请假编号
		var level_no_input = $("[id*='extendDataFormInfo.value(level_no)']");
		var level_no_tr = $(level_no_input).parents('.xform_inputText').parent('td').parent('tr');
		$(level_no_tr).css("display","none");		
		//销假明细
		var cancelLevelInfoInput = $("[id*='extendDataFormInfo.value(cancelLevelInfo)']");
		var cancelLevelInfotr = $(cancelLevelInfoInput).parents('.xform_inputText').parent('td').parent('tr');
		$(cancelLevelInfotr).css("display","none");			
	}
	function Ding_Init_View(){		
		var cancelType =  $("input[name*='extendDataFormInfo.value(cancelType)']").val();
		if("time"!=cancelType){
			//隐藏
			Ding_Time_Validate_Change("");
			$(".cancelTime_tr").css("display","none");
		}
		if("edit"==methodStatus || "view"==methodStatus){
			//展示信息卡片
			var cardInfoStr = $("input[name*='extendDataFormInfo.value(cardInfo)']").val();
			var cardInfo = JSON.parse(cardInfoStr);
			//初始化全局变量--start
			unitStr= cardInfo["unitStr"];
			durationTime = cardInfo["durationTime"];
			dw = cardInfo["dw"];
			fromTimes = cardInfo["fromTimes"];
			toTimes = cardInfo["toTimes"];
			levelCode = cardInfo["levelCode"] ;
			from_half_day_str = cardInfo["from_half_day_str"];
			to_half_day_str = cardInfo["to_half_day_str"];
			unit = cardInfo["unit"];	
			classInfo = cardInfo["classInfo"];
			detailDate = cardInfo["detailDate"];
			//初始化全局变量--end
			var cancelFormName = cardInfo["selectFormName"];
			if("view"==methodStatus){
				$(".view_level_form_span").text(cancelFormName);
				$(".view_level_form").css("display","block");
			}			
			$(".muiDestroyLeaveCardTitleSpan").text(cardInfo["durations"]);
			$(".muiDestroyLeaveCardDateStarts").text(cardInfo["startTime"]);
			$(".muiDestroyLeaveCardDateEnds").text(cardInfo["toTime"]);
			$(".muiDestroyLeaveCardTitleLabel").text(cardInfo["tag"]);
			$(".muiDestroyLeaveCardNumberSpan").text(cardInfo["cardNo"]);
			$(".level_form_div").css("display","block");
			
			if("detail"!=cancelType){
				//展示明细表			
				var htmlStr = $("input[name*='extendDataFormInfo.value(detailInfoForm)']").val();
				$(".detail_div").html(htmlStr);
				$(".detail_div").css("display","block");			
				
				var inputs = document.getElementsByTagName("input");			
				for (var i = 0; i < inputs.length; i++) {
					if (inputs[i].type == "checkbox") {
						if($(inputs[i]).hasClass('selectCheckBox_li')){
							$(inputs[i]).addClass('selected');
							$(inputs[i]).prop("checked", true);
							//$(inputs[i]).prop("disabled", true);
						}else{
							$(inputs[i]).prop("disabled", true);
						}
					}
				}
			}
		}
		
	}
	function Ding_Init_Edit(status){
		//默认隐藏按时间销假展示的两行
		$(".cancelTime_tr").css("display","none");		
		initLeaveForm();//初始化请假单下拉框
		selectLevelForm();//请假单-选择触发事件
		selectCancelType();//销假类型点选触发事件
		selectDateType();
		
		if("edit"==status){//修改页
			Ding_Init_View(); 
		}
	}
	function selectAlls(){
		Ding_Time_Validate_Change("");
		$(".cancelTime_tr").css("display","none");
		$(".detail_div").css("display","none");
		hideTimeTr();
		//重置销假时长和剩余时长
		$("input[name*='extendDataFormInfo.value(duration)']").val(durationTime+unitStr);
		$("input[name*='extendDataFormInfo.value(haveLeaveTime)']").val("0"+unitStr);
	}
	
	function selectTimes(){
		//展示按时间两行
		$(".detail_div").css("display","none");
		$(".cancelTime_tr").css("display","");
		//清空
		hideTimeTr();
		Ding_Time_Validate_Change(unit);
		var $cancelFromDay = $("select[name*='extendDataFormInfo.value(cancelFromDay)']");
		var $cancelToDay = $("select[name*='extendDataFormInfo.value(canceToDay)']");
		if("halfDay"!=unit){		
			$($cancelFromDay).css("display","none");
			$($cancelToDay).css("display","none");
		}
	}
	
	//初始化明细表--按明细销假时的三种
	function selectDetails(){
		//收起按时间两行
		Ding_Time_Validate_Change("");
		$(".cancelTime_tr").css("display","none");
		hideTimeTr();		
		//展示明细div
		$(".detail_div").html('');
		//1、取到请假开始时间、和结束时间、单位、请假时长
		var startLevelTimes = fromTimes;
		var endLevelTimes = fromTimes;
		var levelDuration = durationTime;
		var levelUnit = dw;
		
		//2、请求请假预计算时长,将需要的参数加载更新进全局参数
		initDetailDate();		
		
		//2、按请假单位分别初始化明细表
		var htmlStr = '<div class="container"><div class="muiDestroyLeaveContent"><div class="muiDestroyLeaveListContainer"><ul class="muiDestroyLeaveList">';
		//htmlStr += '<input id="detailDate" name="extendDataFormInfo.value(detailDate)" type="hidden" value="">';
		if("day"===levelUnit){
			htmlStr += initDayDetail();
		}
		if("halfDay"===levelUnit){
			htmlStr += initHalfDayDetail();
		}
		if("hour"===levelUnit){
			htmlStr += initHourDetail();
		}
		htmlStr +='</ul></div></div></div>';
		$(".detail_div").html(htmlStr);
		$("input[name*='extendDataFormInfo.value(detailInfoForm)']").val(htmlStr);
		$(".detail_div").css("display","block");
		//初始化默认全选：即全部销假
		var selectItemCheck = $("input[name='SelectItem']");
		if(selectItemCheck){
			for (var i = 0; i < selectItemCheck.length; i++) {
				$(selectItemCheck[i]).click();
			}
		}
	}
	
	//初始化明细表之前请求预计算时长
	function initDetailDate(){
		var requestUrl = "/third/ding/thirdDingAttendance.do?method=preCalculate&startTime=!{startTime}&finishTime=!{finishTime}&leaveCode=!{leaveCode}";
		// 假期类型详情
		if(!levelCode){
			networkFailure("请选择请假单");
			return false;
		}
		$("[name='extendDataFormInfo.value(leaveCode)']").val(levelCode);
		var params = {};
		params["startTime"] = fromTimes;
		params["finishTime"] = toTimes;
		params["leaveCode"] = levelCode;
		requestUrl = strUtil.variableResolver(requestUrl, params);
		
		Ding_RequestCalcuateDuration(requestUrl);
	}
	
	//初始化按小时销假明细表---按明细销假
	function initHourDetail(){
		var detailDayHtml = "";
		if(!detailDate){
			initDetailDate();
		}
		if(!detailDate || !detailDate.success){
			//时长响应为空,或者响应success为false，不初始化
			return detailDayHtml;
		}		
		var record = detailDate.result.form_data_list[0]["extend_value"];
		if(record){
			var detailList = new Array();
			record = eval("("+record+")");
			detailList = record["detailList"];
			var details;
			getClassInfoJson(detailList);//获取每天的班次信息存入全局变量classInfo
			for (var i = 0; i < detailList.length; i++) {
				details = detailList[i];
				var dateStartTime = details["approveInfo"]["fromTime"];
				var dateToTime = details["approveInfo"]["toTime"];
				dateStartTime = to_time(dateStartTime,"hour");
				dateToTime = to_time(dateToTime,"hour");
				var workDate = getLocalTime(details["workDate"],"date");
				var week = getWeek(workDate);
				
				if(details["isRest"]){//休息日
					detailDayHtml += '<li><label class="muiDestroyLeaveCheckbox">';
					detailDayHtml += '<input type="checkbox" id="listSelect_'+i+'" name="SelectItem" disabled>';
					detailDayHtml += '<label for="listSelect_'+i+'"></label>';
					detailDayHtml += ' <span class="day_span">'+workDate+'  '+week+'</span>';
					detailDayHtml += '<span class="rest">0小时（休息）</span>';
					detailDayHtml += ' </label></li>';
				}else{//工作日
					var widthXw;
					var widthSw;
					var classInfos = getClassInfo(workDate);//获取当天的班次信息
					if(classInfos){//有班次
						var timesimpe=[];
						var startDateTime = classInfos[0]["startTime"];
						var endDateTime = classInfos[classInfos.length-1]["endTime"];
						var jx =classInfos.length-1;; //间隙
						var sjd = 0;//总共时间段
						for (var w = 0; w < classInfos.length; w++) {
							var timesimpes  = getEveryHours(classInfos[w]["startTime"],classInfos[w]["endTime"]);
							sjd += timesimpes.length-1;
						}
						
						detailDayHtml += '<li class="hour allHour"><label class="muiDestroyLeaveCheckbox"><input type="checkbox" id="listSelect_'+i+'" name="SelectItem" onClick ="selectHourCheckBox(this);"><label for="listSelect_'+i+'"></label>';
						detailDayHtml += '<span class="day_span">'+workDate+'  '+week+' ('+dateStartTime+' ~ '+dateToTime+')</span>';
						detailDayHtml += '<span class="timesEveryDay">'+details["approveInfo"]["durationInHour"]+'小时</span></label>';//请假几个小时
						detailDayHtml += '<div class="muiDestroyLeaveDetails"><div class="muiDestroyLeaveDetailsProgress">';
						var width = 100/(sjd+jx);
						var sjdHtml_sw ='';//时间段
						var sjdHtml_xw ='';//时间段
						var sjxHtml_sw ='';
						var sjxHtml_xw ='';
						var flag = true;
						for (var f = 0; f < classInfos.length; f++) {//开始初始化工作日明细
							timesimpe  = getEveryHours(classInfos[f]["startTime"],classInfos[f]["endTime"]);
							//初始化时间段
							var isa = new Array();
							for (var m = 0; m <= timesimpe.length-1; m++) {
								var timed = timesimpe[m];
								var hour1 = parseInt(timed.split(":")[0]);
								var hour2 = parseInt(dateStartTime.split(":")[0]);
								var hour3 = parseInt(dateToTime.split(":")[0]);
								//初始化时间段
								
								if(m!=timesimpe.length-1){
									if(flag){
										if(hour1>=hour2 && hour1<= hour3){
											sjdHtml_sw += '<div class="muiDestroyLeaveItem hour selectHourItem" onClick="selectHour(this);"><span class="muiDestroyLeaveItem_span" style="display:none">'+timesimpe[m]+'~'+timesimpe[m+1]+'</span></div>';
										}else{
											sjdHtml_sw += '<div class="hour noshow"></div>';
										}
										widthSw = width*(timesimpe.length-1);
									}else{
										if(hour1>=hour2 && hour1<= hour3){
											sjdHtml_xw += '<div class="muiDestroyLeaveItem hour selectHourItem" onClick="selectHour(this);"><span class="muiDestroyLeaveItem_span" style="display:none">'+timesimpe[m]+'~'+timesimpe[m+1]+'</span></div>';
										}else{
											sjdHtml_xw += '<div class="hour noshow"></div>';
										}
										widthXw = width*(timesimpe.length-1);
									}
								}
								//初始化时间线
								if(timesimpe[m]){
									if(flag){
										if(m==0){
											sjxHtml_sw += '<span class="allDay halfDay hour hourLine">'+timesimpe[m]+'</span>';
										}else if( m == timesimpe.length-1){
											sjxHtml_sw += '<span class="allDay halfDay hour end hourLine" style="margin-right: -50px;">'+timesimpe[m]+'</span>';
										}else{
											sjxHtml_sw += ' <span class="hour hourLine">'+timesimpe[m]+'</span>';
										}	
									}else{
										if(m==0){
											sjxHtml_xw += '<span class="allDay halfDay hour hourLine">'+timesimpe[m]+'</span>';
										}else if( m == timesimpe.length-1){
											sjxHtml_xw += '<span class="allDay halfDay hour end hourLine" style="margin-right: -50px;">'+timesimpe[m]+'</span>';
										}else{
											sjxHtml_xw += ' <span class="hour hourLine" >'+timesimpe[m]+'</span>';
										}
									}
								}
								if(m==timesimpe.length-1){
									flag =false;
								}
							}
						}
						sjdHtml_sw+="</div>"
						sjdHtml_xw+="</div>"
						sjxHtml_xw+="</div>"
						sjxHtml_sw+="</div>"
							
						detailDayHtml += '<div class="muiDestroyLeaveTimeItem muiDestroyLeaveAmItem" style="width:'+widthSw+'%">';
						detailDayHtml += sjdHtml_sw;
						if(null==jx || jx==0){
							jx=1;
						}
						var noworkTimeWidth=(100-widthSw-widthXw)/jx;
						var noworkTime='<div class="noWorkTimes" style="width:'+noworkTimeWidth+'%"></div>';
						for (var q = 0; q < jx; q++) {
							detailDayHtml += noworkTime;
						}
						detailDayHtml += '<div class="muiDestroyLeaveTimeItem muiDestroyLeavePmItem"  style="width:'+widthXw+'%">';
						detailDayHtml += sjdHtml_xw;						
						detailDayHtml += '<div class="muiDestroyLeaveBaseline">';	
						detailDayHtml += '<div class="muiDestroyLeaveAmItem_sw" style="width:'+widthSw+'%">';
						detailDayHtml += sjxHtml_sw;
						for (var qs = 0; qs < jx; qs++) {
							detailDayHtml += noworkTime;
						}
						detailDayHtml += '<div class="muiDestroyLeavePmItem_xw" style="width:'+widthXw+'%">';
						detailDayHtml += sjxHtml_xw;
						detailDayHtml += '</div></div></div></li>';						
					}else{//没有班次，当休息日请假
						detailDayHtml += '<li><label class="muiDestroyLeaveCheckbox">';
						detailDayHtml += '<input type="checkbox" id="listSelect_'+i+'" name="SelectItem" disabled>';
						detailDayHtml += '<label for="listSelect_'+i+'"></label>';
						detailDayHtml += ' <span class="day_span">'+workDate+'  '+week+ '</span>';
						detailDayHtml += '<span class="rest timesEveryDay">'+details["approveInfo"]["durationInHour"]+'小时</span><span class="rest">（休息）</span>';
						detailDayHtml += ' </label></li>';
					}
				}
			}			
		}
		return detailDayHtml;		
	}
	
	//初始化按半天销假明细表---按明细销假
	function initHalfDayDetail(){
		var detailDayHtml = "";
		if(!detailDate){
			initDetailDate();
		}
		if(!detailDate || !detailDate.success){
			//时长响应为空,或者响应success为false，不初始化
			return detailDayHtml;
		}
		var record = detailDate.result.form_data_list[0]["extend_value"];
		if(record){
			var detailList = new Array();
			record = eval("("+record+")");
			detailList = record["detailList"];
			var details;
			getClassInfoJson(detailList);//获取每天的班次信息存入全局变量classInfo
			for (var i = 0; i < detailList.length; i++) {
				details = detailList[i];
				var workDate = getLocalTime(details["workDate"],"date");
				var week = getWeek(workDate);
				if(details["isRest"]){//休息日
					detailDayHtml += '<li><label class="muiDestroyLeaveCheckbox">';
					detailDayHtml += '<input type="checkbox" id="listSelect_'+i+'" name="SelectItem" disabled>';
					detailDayHtml += '<label for="listSelect_'+i+'"></label>';
					detailDayHtml += ' <span>'+workDate+'  '+week+'</span>';
					detailDayHtml += '<span class="rest">0小时（休息）</span>';
					detailDayHtml += ' </label></li>';
				}else{//工作日
					var classInfos = getClassInfo(workDate);//获取当天的班次信息
					detailDayHtml += '<li class="halfDay"><label class="muiDestroyLeaveCheckbox"><input type="checkbox" id="listSelect_'+i+'" name="SelectItem" onClick ="selectHalfDayCheckBox(this);"><label for="listSelect_'+i+'"></label>';
					detailDayHtml += '<span class="day_span">'+workDate+' '+week+'</span>';
					detailDayHtml += '<span class="timesEveryDay">'+details["approveInfo"]["durationInDay"]+'天</span>';
					
					detailDayHtml += '</label><div class="muiDestroyLeaveDetails"><div class="muiDestroyLeaveDetailsProgress"><div class="muiDestroyLeaveTimeItem muiDestroyLeaveAmItem muiDestroyLeaveItemHalfDayAM">';
					var startDateTime = getLocalTime(details["classInfo"]["sections"][0]["startTime"],"time");
					var endDateTime = getLocalTime(details["classInfo"]["sections"][0]["endTime"],"time");
					var lefts= 0;
					var durationInDay=  details["approveInfo"]["durationInDay"];
					
					var levelStartHour = getLocalTime(details["approveInfo"]["fromTime"],"time").split(":")[0];
					var levelEndHour =  getLocalTime(details["approveInfo"]["toTime"],"time").split(":")[0];
					
					var swSjd ='<div class="muiDestroyLeaveItem halfDay" onClick = "selectHalfDay(this);"><span class="half_span" style="display:none">AM<span></div>';
					var xwSjd ='<div class="muiDestroyLeaveItem halfDay" onClick = "selectHalfDay(this);"><span class="half_span"  style="display:none">PM<span></div>';
					var sjx ="";
					for (var t = 0; t < classInfos.length; t++) {
						var classInfoss = classInfos[t];
						if(t==0){
							var classStartHour = classInfos[t]["startTime"].split(":")[0];
							var classEndHour = classInfos[t]["endTime"].split(":")[0];
							//时间段
							if(0.5 == durationInDay){
								if((levelStartHour>=classStartHour && levelStartHour<= classEndHour)){
								}else{
									//当前时间段不能勾选
									swSjd = '<div class="muiDestroyLeaveItem halfDay noshow"></div>';
								}
							}
							//时间线
							lefts = (100-(classInfos.length-1)*10)/2;
							sjx += ' <span class="allDay halfDay hour">'+classInfos[t]["startTime"]+'</span>';//第一个
							sjx += '<span class="halfDay"  style="left:'+lefts+'%">'+classInfos[t]["endTime"]+'</span>';
						}
						if(t== classInfos.length-1){
							var classStartHour = classInfos[t]["startTime"].split(":")[0];
							var classEndHour = classInfos[t]["endTime"].split(":")[0];
							//时间段
							if(0.5 == durationInDay){
								if((levelStartHour>=classStartHour && levelStartHour<= classEndHour)){
								}else{
									//当前时间段不能勾选
									xwSjd = '<div class="muiDestroyLeaveItem halfDay noshow"></div>';
								}
							}							
							var start1 = lefts+(classInfos.length-1)*10;
							var end1 = start1+lefts-((classInfos.length-1)*10/2)+1;
							sjx += ' <span class="allDay halfDay hour" style="left:'+start1+'%">'+classInfos[t]["startTime"]+'</span>';//最后一个
							sjx += '<span class="halfDay" style="left:'+end1+'%">'+classInfos[t]["endTime"]+'</span>';
						}
					}
					detailDayHtml += swSjd;					
					detailDayHtml += '</div><div class="muiDestroyLeaveTimeItem muiDestroyLeavePmItem muiDestroyLeaveItemHalfDayPM">';					
					detailDayHtml += xwSjd;					
					detailDayHtml += '</div><div class="muiDestroyLeaveBaseline">';					
					detailDayHtml += sjx;					
					
					detailDayHtml += ' </div></div> </div></li>';					
				}
			}			
		}
		return detailDayHtml;
	}
	
	function getClassInfoJson(detailList){
		var dateStr ="";
		for (var i = 0; i < detailList.length; i++) {
			if(!detailList[i] || !detailList[i]["workDate"]){
				continue;
			}
			var workDate = getLocalTime(detailList[i]["workDate"],"date");
			if(workDate){
				if(!dateStr){
					dateStr += workDate;
				}else{
					dateStr += ";"+workDate;
				}
			}
		}		
		var pram ={};
		pram.dateList=dateStr;
		var url = Com_Parameter.ContextPath + "third/ding/thirdDingAttendance.do?method=getUserAttendenceClassInfo&dateList="+dateStr;
		$.ajax({
	         url: url,
	         type: 'GET',
	         dataType: 'json',
	         data:pram,
	         error : function(data) {
	       	  	networkFailure("班次获取失败，初始化异常。");
	       	  	console.log("班次获取失败，初始化异常。url:"+url);
	         },
			 success: function(data) {
				 classInfo = data;		
			 }
	     });
	}
	function getClassInfo(workDate){		
		if(classInfo){//一天的
			var classInfoJson = classInfo[workDate];
			if(!classInfoJson){
				return null;//没有排班信息，算休息日
			}
			var sections = classInfoJson["result"]["sections"];
			if(sections && sections.length>0){
				var classJson =[];
				for (var i = 0; i < sections.length; i++) {
					if(sections[i]["rests"] && sections[i]["rests"].length>0){//有休息时间
						var punches = sections[i]["punches"];//排班
						var rests = sections[i]["rests"];//休息
						if(punches && punches.length>0){
							for (var w = 0; w < punches.length; w++) {
								var classPunches ={};
								if("OnDuty" == punches[w]["check_type"]){
									var check_time = punches[w]["check_time"];
									classPunches["startTime"]=check_time.substring(check_time.length-8,check_time.length-3);
									for (var n = 0; n < rests.length; n++) {
										if("OnDuty" == rests[n]["check_type"]){
											var rests_time = rests[n]["check_time"];
											classPunches["endTime"]=rests_time.substring(rests_time.length-8,rests_time.length-3);
										}
									}
									classJson.push(classPunches);
								}
								if("OffDuty" == punches[w]["check_type"]){
									for (var s = 0; s < rests.length; s++) {
										if("OffDuty" == rests[s]["check_type"]){
											var rests_time1 = rests[s]["check_time"];
											classPunches["startTime"]=rests_time1.substring(rests_time1.length-8,rests_time1.length-3);
										}
									}									
									var check_time = punches[w]["check_time"];
									classPunches["endTime"]=check_time.substring(check_time.length-8,check_time.length-3);
									classJson.push(classPunches);
								}	
							}
						}
					}else{//没有休息时间
						var classPunches ={};
						var punches = sections[i]["punches"];//排班
						if(punches && punches.length>0){
							for (var w = 0; w < punches.length; w++) {
								if("OnDuty" == punches[w]["check_type"]){
									var check_time = punches[w]["check_time"];
									classPunches["startTime"]=check_time.substring(check_time.length-8,check_time.length-3);
								}
								if("OffDuty" == punches[w]["check_type"]){
									var check_time = punches[w]["check_time"];
									classPunches["endTime"]=check_time.substring(check_time.length-8,check_time.length-3);
								}							
							}
							classJson.push(classPunches);
						}
					}
				}
				return classJson;
			}
		}
		return null ;
	}
	
	//初始化按天销假明细表---按明细销假
	function initDayDetail(){
		var detailDayHtml = "";
		if(!detailDate){
			initDetailDate();
		}
		if(!detailDate || !detailDate.success){
			//时长响应为空,或者响应success为false，不初始化
			return detailDayHtml;
		}		
		var record = detailDate.result.form_data_list[0]["extend_value"];
		if(record){
			var detailList = new Array();
			record = eval("("+record+")");
			detailList = record["detailList"];
			var details;
			for (var i = 0; i < detailList.length; i++) {
				details = detailList[i];
				var workDate = getLocalTime(details["workDate"],"date");
				var week = getWeek(workDate);
				if(details["isRest"]){//休息日
					detailDayHtml += '<li><label class="muiDestroyLeaveCheckbox">';
					detailDayHtml += '<input type="checkbox" id="listSelect_'+i+'" name="SelectItem" disabled>';
					detailDayHtml += '<label for="listSelect_'+i+'"></label>';
					detailDayHtml += ' <span>'+workDate+'  '+week+'</span>';
					detailDayHtml += '<span class="rest">0小时（休息）</span>';
					detailDayHtml += ' </label></li>';
				}else{//工作日
					detailDayHtml += ' <li class="allDay"><label class="muiDestroyLeaveCheckbox"><input type="checkbox" id="listSelect_'+i+'" name="SelectItem" onClick ="daySelectCheck(this);"><label for="listSelect_'+i+'"></label>';
					detailDayHtml += '<span class="day_span">'+workDate+' '+  week+'</span><span class="timesEveryDay">1天</span></label>';
					detailDayHtml += ' <div class="muiDestroyLeaveDetails"><div class="muiDestroyLeaveDetailsProgress">';
						
					detailDayHtml += '<div class="muiDestroyLeaveTimeItem muiDestroyLeaveAllDayItem">';
					detailDayHtml += '<div class="muiDestroyLeaveItem halfDay" onClick = "daySelect(this);"></div>';
					detailDayHtml += '</div><div class="muiDestroyLeaveBaseline">';
					var startDateTime = getLocalTime(details["classInfo"]["sections"][0]["startTime"],"time");
					var endDateTime = getLocalTime(details["classInfo"]["sections"][0]["endTime"],"time");
					detailDayHtml += ' <span class="allDay halfDay hour">'+startDateTime+'</span>';
					detailDayHtml += ' <span class="allDay halfDay hour detail_days">'+endDateTime+'</span></div></div></div></li>';
				}
			}			
		}
		return detailDayHtml;
	}
	
	function hideTimeTr(){
		//清空时间两行的值
		$("input[name*='cancelEndTime']").val('');
		$("input[name*='cancelStartTime']").val('');
		$("select[name*='extendDataFormInfo.value(cancelFromDay)']").val('');
		$("select[name*='extendDataFormInfo.value(canceToDay)']").val('');
	}
	
	//销假类型-默认选中全部销假
	function initSelectType(){
		//隐藏按时间销假的展示框
		$(".cancelTime_tr").css("display","none");
		//初始化全局变量
		unitStr = "";
		durationTime="";
		dw ="";
		fromTimes ="";
		toTimes ="";
		levelCode ="";
		from_half_day_str ="";
		to_half_day_str ="";
		
		//默认全部销假
		$("input[name*='extendDataFormInfo.value(cancelType)']").each(function() {
			if ($(this).val() != "all") {
				$(this).removeAttr("checked");
			} else {
				$(this).prop("checked", "checked");
			}
		});
	}
	
	function selectCancelType(){
		$("input[name*='extendDataFormInfo.value(cancelType)']").on("change",function(){
			var levelForm = $("select[name*='extendDataFormInfo.value(level_form)']").val();//请假单
			if(!levelForm){
				//恢复为默认全部销假被选中
				initSelectType();
				networkFailure("请选择请假单");
				return;
			}
			var levelType = $("input[name='extendDataFormInfo.value(cancelType)']:checked").val();
			if("all"===levelType){
				selectAlls();//全部销假
			}
			if("time"===levelType){
				selectTimes();//按时间销假
			}
			if("detail"===levelType){
				selectDetails();//按明细销假
			}
		});
	}
	
	function Ding_Time_Validate_Change(units){
		if(units === "halfDay" || units === "day"){
			$("input[name*='extendDataFormInfo.value(cancelStartTime)']").attr("validate","required __date");
			$("input[name*='extendDataFormInfo.value(cancelEndTime)']").attr("validate","required __date compareTime");
			
			$("select[name*='extendDataFormInfo.value(cancelFromDay)']").css("display","");
			$("select[name*='extendDataFormInfo.value(canceToDay)']").css("display","");
			
		}else if(units ==="hour"){
			$("input[name*='extendDataFormInfo.value(cancelStartTime)']").attr("validate","required __datetime");
			$("input[name*='extendDataFormInfo.value(cancelEndTime)']").attr("validate","required __datetime compareTime");
			
			$("select[name*='extendDataFormInfo.value(cancelFromDay)']").css("display","none");
			$("select[name*='extendDataFormInfo.value(canceToDay)']").css("display","none");
		}else{
			$("input[name*='extendDataFormInfo.value(cancelStartTime)']").attr("validate","__date");
			$("input[name*='extendDataFormInfo.value(cancelEndTime)']").attr("validate","__date compareTime");
			$("select[name*='extendDataFormInfo.value(cancelFromDay)']").css("display","none");
			$("select[name*='extendDataFormInfo.value(canceToDay)']").css("display","none");
		}
	}
	
	//选择请假单-触发事件
	function selectLevelForm(){
		$("select[name*='extendDataFormInfo.value(level_form)']").on("change",function(){
			//默认全部销假
			initSelectType();
			var levelForm = $("select[name*='extendDataFormInfo.value(level_form)']").val();//选择的下拉选项value
			$(".detail_div").css("display","none");
			if(!levelForm){
				$(".level_form_div").css("display","none");
				return;
			}
			
			//取返回所有值
			var cancelLeave = $("#cancelLeave").val();
			if(!cancelLeave){
				return;
			}
			$(".level_form_div").css("display","block");
			var level = eval("("+cancelLeave+")");
			var $singerLeave = $("#singerLeave");	
			for (var i = 0; i < level.length; i++) {
				if(levelForm == level[i].fd_ekp_instance_id){
					$singerLeave.val(JSON.stringify(level[i].extend_value));
					$("#leaveCode").val(level[i].leaveCode);
					levelCode = level[i].leave_code;
					from_half_day_str = level[i].from_half_day_str;
					to_half_day_str = level[i].to_half_day_str;
					fromTimes = level[i].from_time_str;
					toTimes = level[i].to_time_str;
					unit = level[i].unit;
					break;
				}
			}	
			
			var singerLeave = JSON.parse($singerLeave.val());
			var extension = singerLeave["extension"];
			var durationInHour = singerLeave["durationInHour"]; //小时
			var durationInDay = singerLeave["durationInDay"]; //天
			var approveInfo = singerLeave["detailList"][0]["approveInfo"]; 
			var durations = "";
			if("DAY"==unit || "day"==unit || "HALFDAY" == unit || "halfDay"==unit){
				durations = durationInDay+"天"
				durationTime = durationInDay;
				unitStr = "天";
				if("HALFDAY" == unit || "halfDay"==unit){
					dw ="halfDay";
				}else{
					dw="day";
				}
			}
			if("HOUR"== unit|| "hour"==unit){
				durations = durationInHour+"小时"
				durationTime = durationInHour;
				unitStr = "小时";
				dw ="hour";
			}	
			//初始化选择请假单后的展示框
			
			var startTime=fromTimes;
			var toTime = toTimes;			
			if("halfDay"===dw){
				startTime = startTime.substring(0,10);
				toTime = toTime.substring(0,10);
				if("AM"===from_half_day_str){
					startTime = startTime+" 上午";
				}else{
					startTime = startTime+" 下午";
				}
				if("AM"===to_half_day_str){
					toTime = toTime+" 上午";
				}else{
					toTime = toTime+" 下午";
				}				
			}else if("day"===dw){
				startTime = startTime.substring(0,10);
				toTime = toTime.substring(0,10);
			}
			var selectFormName = $("select[name*='extendDataFormInfo.value(level_form)']").find("option:selected").text();
			$(".muiDestroyLeaveCardTitleSpan").text(durations);
			$(".muiDestroyLeaveCardDateStarts").text(startTime);
			$(".muiDestroyLeaveCardDateEnds").text(toTime);
			$(".muiDestroyLeaveCardTitleLabel").text(extension["tag"]);
			$(".muiDestroyLeaveCardNumberSpan").text($("select[name*='extendDataFormInfo.value(level_form)']").val());
			var cardInfo = {};
			cardInfo["startTime"] = startTime;
			cardInfo["toTime"] = toTime;
			cardInfo["durations"] = durations;
			cardInfo["tag"] = extension["tag"];
			cardInfo["cardNo"] = $("select[name*='extendDataFormInfo.value(level_form)']").val();
			cardInfo["selectFormName"] = selectFormName;
						
			$("input[name*='extendDataFormInfo.value(cardInfo)']").val(JSON.stringify(cardInfo));
			fromTimes = to_time_dw(fromTimes,dw,from_half_day_str);
			toTimes = to_time_dw(toTimes,dw,to_half_day_str);
			//初始化销假时长和剩余时长
			$("input[name*='extendDataFormInfo.value(duration)']").val(durationTime+unitStr);
			$("input[name*='extendDataFormInfo.value(haveLeaveTime)']").val("0"+unitStr);
		});
	}
	
	function to_time_dw(times ,dw,str){
		if('day'===dw){
			return times.substring(0,10);
		}
		if('halfDay'===dw){
			return times.substring(0,10)+" "+str;
		}
		return times;
	}
	
	//初始化加载请假单
	function initLeaveForm(){
		 var userId = Com_Parameter.CurrentUserId;//取当前登录用户
		 var levelForm = $("select[name*='extendDataFormInfo.value(level_form)']");
		  if(!userId || !levelForm){
			  return ;//需要登录
		  }
		  var pram = {
		  };
		  levelForm.empty();
		  levelForm.append('<option value="">==请选择==</option>');
		  var url = Com_Parameter.ContextPath + "third/ding/thirdDingAttendance.do?method=cancelLeave";
		  $.ajax({
	         url: url,
	         type: 'GET',
	         dataType: 'json',
	         data:pram,
	         error : function(data) {
	       	  	networkFailure("请假单初始化异常");
	       	  	console.log("请假单初始化异常。url:"+url);
	         },
			 success: function(data) {
				if(data && data.length>0){
					var prams=[];
					for (var i = 0; i < data.length; i++) {
						 prams.push(data[i]);
						 if(data[i] && data[i].fd_ekp_instance_id && data[i].fd_name){
							 levelForm.append('<option value="' + data[i].fd_ekp_instance_id + '">' + data[i].fd_name + '</option>');
						 }
					}				 
					$("#cancelLeave").val(JSON.stringify(prams));
					
					if("edit"==methodStatus){//修改页
						var cardInfoStrs = $("#cardInfo").val();
						var card = JSON.parse(cardInfoStrs);
						$("select[name*='extendDataFormInfo.value(level_form)']").val(card["cardNo"]);
					}
				
				}		
			 }
	     });	  
	}
	
	//事件注册--主要适用按时间销假
	function selectDateType(){
		var $cancelStartTime = $("input[name*='cancelStartTime']").closest(".inputselectsgl");
		$cancelStartTime.removeAttr("onclick");
		//销假开始时间点击事件--按时间销假
		$cancelStartTime.on("click", function(){
			Ding_DateSelect("cancelStartTime", "cancelFromDay");
		});
		
		var $cancelEndTime = $("input[name*='cancelEndTime']").closest(".inputselectsgl");
		$cancelEndTime.removeAttr("onclick");
		//销假结束时间点击事件--按时间销假
		$cancelEndTime.on("click", function(){
			Ding_DateSelect("cancelEndTime", "canceToDay");
		});
				
		var selectTime;
		//销假开始时间change事件
		$cancelStartTime.on("change", function(){
			selectTime = $("input[name*='cancelStartTime']").val();
			var flag = checkTimes(selectTime);
			if(!flag){
				$("input[name*='cancelStartTime']").val('');
				networkFailure("销假开始时间必须在请假时间范围内，请重新选择。");
			}
			checkValues();
		});
		//销假结束时间change事件
		$cancelEndTime.on("change", function(){
			selectTime = $("input[name*='cancelEndTime']").val();
			var flag = checkTimes(selectTime);
			if(!flag){
				$("input[name*='cancelEndTime']").val('');
				networkFailure("销假结束时间必须在请假时间范围内，请重新选择。");
			}
			checkValues();
		});
		//销假开始时间段change事件（上午/下午）
		$("select[name*='cancelFromDay']").on("change",function(){
			var cancelStartTime1 = $("input[name*='cancelStartTime']").val();//销假开始时间
			var cancelFromDay1 = $("select[name*='cancelFromDay']").val();//销假开始时间段
			var flag = checkHalfDay(cancelStartTime1,cancelFromDay1);
			if(!flag){
				$("select[name*='cancelFromDay']").val('');
			}
			checkValues();
		});
		//销假结束时间段change事件（上午/下午）
		$("select[name*='canceToDay']").on("change",function(){
			var cancelEndTime1 = $("input[name*='cancelEndTime']").val();//销假开始时间
			var canceToDay1 = $("select[name*='canceToDay']").val();//销假开始时间段
			var flag = checkHalfDay(cancelEndTime1,canceToDay1);
			if(!flag){
				$("select[name*='canceToDay']").val('');
			}
			checkValues();
		});		
	}

	function checkValues(){
		if(Ding_IsAllDateSet()){
			var requestUrl = getUrl();
			Ding_RequestCalcuateDuration(requestUrl);
		}
	}
	//点选时间控件-判断格式
	function Ding_DateSelect(name, halfDayName){
		name = "extendDataFormInfo.value("+ name +")";
		// 根据不同的单位，时间选择不同的选择
		switch(unit){
			case "day":
			case "halfDay":
				selectDate(name,null,function(c){
					//未选上下午则自动选择上午
					let halfDay = $("select[name*='" + halfDayName + "']");
					if (!halfDay.val() && $("input[name='" + name + "']").val()) {
						//halfDay.val('AM');
					}
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
	
	//判断是否所有的日期都已经设置值
	function Ding_IsAllDateSet(){
		var $fromTime = $("input[name*='cancelStartTime']");
		var $toTime = $("input[name*='cancelEndTime']");
		// 开始时间和结束事件是否已经设置值
		if($fromTime.val() && $toTime.val()){
			// 【单位】是否设置为半天，是则校验
			if("halfDay"===unit){
				if($("select[name*='cancelFromDay']").val() && $("select[name*='canceToDay']").val()){
					return true;
				}
			}else{
				return true;
			}
		}
		return false;
	}
	
	//请求计算时长
	function Ding_RequestCalcuateDuration(requestUrl){
		requestUrl = env.fn.formatUrl(requestUrl);
		$.ajax({
			url : requestUrl,
			dataType : "json",
			type : "GET",
	    	jsonp:"jsonpcallback",
	    	success: function(data){
	    		if(data && data.success){
	    			detailDate = data;
	    			var duration = 0;//销假时长
	    			var record = data.result.form_data_list[0]["extend_value"];
	    			record = eval("("+record+")");
	    			try{
	    				if(dw === "hour"){
	    					duration = parseFloat(record["durationInHour"]);
	    				}else{
	    					duration = parseFloat(record["durationInDay"]);
	    				}
	    			}catch(e){
	    				duration = record["durationInHour"];
	    				console.log("can't parse " + record["durationInDay"] +" to float!");
	    			}
	    			if(!durationTime){//请假单的时长为0或为空
	    				networkFailure("未获取到请假单的请假时长，请刷新后再试");
	    				return false;
	    			}else{
	    				var levelsTime = parseFloat(durationTime);
	    				var cancelTime = parseFloat(duration);
	    				var dwStr = "小时";
	    				if('day'=== dw || 'halfDay' === dw){
	    					dwStr = "天";
	    					$("[name='extendDataFormInfo.value(duration)']").val(cancelTime+""+dwStr);
		    				$("[name='extendDataFormInfo.value(haveLeaveTime)']").val((levelsTime-cancelTime)+""+dwStr);
	    				}else{
	    					//按时间销假
		    				$("[name='extendDataFormInfo.value(duration)']").val(cancelTime+""+dwStr);
		    				$("[name='extendDataFormInfo.value(haveLeaveTime)']").val((levelsTime-cancelTime)+""+dwStr);
	    				}
	    			}
	    		}else{
	    			console.log(data.errmsg);
	    		}
	    	}
		});	
	}
	//请求时长-获取url
	function getUrl(){
		var requestUrl = "/third/ding/thirdDingAttendance.do?method=preCalculate" +
		"&startTime=!{startTime}&finishTime=!{finishTime}&leaveCode=!{leaveCode}";
		// 假期类型详情
		if(!levelCode){
			networkFailure("请选择请假单");
			return false;
		}
		$("#leaveCode").val(levelCode);
		var params = {};
		var ding_starttime = Ding_GetStartTime();
		var ding_endtime = Ding_GetEndTime();
		//判断请求时长的结束时间是否小于开始时间
		var time1 = ding_starttime;
		var time2 = ding_endtime;
		if('halfDay' === dw || 'day' === dw){
			time1 = time1.substring(0,10);
			time2 = time2.substring(0,10);
		}
		if('day' === dw || 'hour' ===dw){
			var dateTime1 = new Date(time1).getTime();
			var dateTime2 = new Date(time2).getTime();
			if(dateTime2-dateTime2<0){
				networkFailure("销假结束时间不得早于销假开始时间");
				return;
			}
		}
		if('halfDay' === dw){
			
		}
		params["startTime"] = ding_starttime;
		params["finishTime"] = ding_endtime;
		params["leaveCode"] = levelCode;
		return strUtil.variableResolver(requestUrl, params);
	}
	Com_AddEventListener(window, "load", Ding_Init);
});

//checkbox选中事件--按明细销假
window.selectItem=function(v){
  var allTimes = $("input[name='extendDataFormInfo.value(duration)']").val().replace("天","").replace("小时","");
  var syTimes = $("input[name='extendDataFormInfo.value(haveLeaveTime)']").val().replace("天","").replace("小时","");
  allTimes = parseFloat(allTimes);
  syTimes = parseFloat(syTimes);
  var timesEveryDayText = $(v).parent('.muiDestroyLeaveCheckbox').find('.timesEveryDay').text().replace("天","").replace("小时","");
  var timesEveryDay = parseFloat(timesEveryDayText);
  if($(v).is(":checked")){	  
	  allTimes += timesEveryDay;
	  syTimes -= timesEveryDay;
 	  $(v).parent('.muiDestroyLeaveCheckbox').next('.muiDestroyLeaveDetails').find('.muiDestroyLeaveItem').addClass('selected');
 	  $(v).addClass("selectCheckBox_li"); 	 
  }else{
	  allTimes -= timesEveryDay;
	  syTimes += timesEveryDay;
      $(v).parent('.muiDestroyLeaveCheckbox').next('.muiDestroyLeaveDetails').find('.muiDestroyLeaveItem').removeClass('selected');
      $(v).removeClass("selectCheckBox_li"); 	
  }
  var durationTimeInt = parseFloat(durationTime);
  if(allTimes<0 || syTimes>durationTimeInt){
  	allTimes=0;
  	syTimes = durationTimeInt;
  	console.log("无效勾选，请检查请假单提交后，排班信息是否有更改！");
  }
  if(allTimes>durationTimeInt || syTimes<0){
  	allTimes = durationTimeInt;
  	syTimes=0;
  	console.log("无效勾选，请检查请假单提交后，排班信息是否有更改！");
  }
  $("input[name='extendDataFormInfo.value(duration)']").val(allTimes.toFixed(2)+unitStr);
  $("input[name='extendDataFormInfo.value(haveLeaveTime)']").val(syTimes.toFixed(2)+unitStr);
}

//时间段选中事件--按明细销假
window.muiDestroyLeaveItem = function(v){
	var node=$(v).parent('.muiDestroyLeaveTimeItem').parent('.muiDestroyLeaveDetailsProgress').find('.muiDestroyLeaveItem');
	var allTimes = $("input[name='extendDataFormInfo.value(duration)']").val().replace("天","").replace("小时","");
	var syTimes = $("input[name='extendDataFormInfo.value(haveLeaveTime)']").val().replace("天","").replace("小时","");
	allTimes = parseFloat(allTimes);
	syTimes = parseFloat(syTimes);
    if($(v).hasClass('selected')){//取消选中
    	if("halfDay"==unit){
    		allTimes -= 0.5;
			syTimes += 0.5;
    	}else{
    		allTimes -= 1;
    		syTimes += 1;
    	}
        $(v).removeClass('selected');
        var checkBoxs =  $(v).parents('.muiDestroyLeaveDetails').parent('li').find("input[name='SelectItem']");
        $(checkBoxs[0]).removeClass("selectCheckBox_li"); 
        //$(v).removeClass("selectCheckBox_li"); 	
    }else{//选中
    	if("halfDay"==unit){
    		allTimes += 0.5;
			syTimes -= 0.5;
    	}else{
    		allTimes += 1;
    		syTimes -= 1;
    	}    	
        $(v).addClass('selected');
        $(v).addClass("selectCheckBox_li"); 	
        var checkBoxs =  $(v).parents('.muiDestroyLeaveDetails').parent('li').find("input[name='SelectItem']");
        $(checkBoxs[0]).addClass("selectCheckBox_li"); 
    }
    var durationTimeInt = parseFloat(durationTime);
    if(allTimes<0 || syTimes>durationTimeInt){
    	allTimes=0;
    	syTimes = durationTimeInt;
    	console.log("无效勾选，请检查请假单提交后，排班信息是否有更改！");
    }
    if(allTimes>durationTimeInt || syTimes<0){
    	allTimes = durationTimeInt;
    	syTimes=0;
    	console.log("无效勾选，请检查请假单提交后，排班信息是否有更改！");
    }
    $("input[name='extendDataFormInfo.value(duration)']").val(allTimes.toFixed(2)+unitStr);
    $("input[name='extendDataFormInfo.value(haveLeaveTime)']").val(syTimes.toFixed(2)+unitStr);
    $(v).parents('li').find("input[name='SelectItem']").prop("checked" , node.length == node.filter(".selected").length ? true :false); 
}

//时间段选中封装数据--适用按明细销假 之 按天销假
window.daySelect=function(v){
	muiDestroyLeaveItem(v);
	var isCheck = $(v).parents('.allDay').find("input[name='SelectItem']").is(':checked');
	var day_span = $(v).parents('.allDay').find(".day_span").text().substring(0,10);
	if(isCheck){//选中事件
		setDetailDate(day_span,"");
	}else{//取消事件
		setCancelDetailDate(day_span,"");
	}
}

//checkbox选中封装数据--适用按明细销假 之 按天销假
window.daySelectCheck=function(v){
	selectItem(v);
	var isCheck = $(v).is(':checked');
	var day_span = $(v).parents('.muiDestroyLeaveCheckbox').find(".day_span").text().substring(0,10);
	if(isCheck){//选中事件
		setDetailDate(day_span,"");
	}else{//取消事件
		setCancelDetailDate(day_span,"");
	}
}

//点击半天时间段
window.selectHalfDay = function(v,o){
	muiDestroyLeaveItem(v);
	
	//var isCheck = $(v).parents('.halfDay').find("input[name='SelectItem']").is(':checked');
	var day_span = $(v).parents('.halfDay').find(".day_span").text().substring(0,10);
	var item = $(v).parents('.halfDay').find(".muiDestroyLeaveItem");
	var halfStr = "";
	for (var i = 0; i < item.length; i++) {
		if($(item[i]).hasClass('selected')){
			var text1 =$(item[i]).find(".half_span").text();
			if(!halfStr){
				halfStr+=text1;
			}else{
				halfStr+=","+text1;
			}
		}
	}
	if($(v).hasClass('selected')){//选中事件
		setDetailDate(day_span,halfStr);
	}else{//取消事件
		setCancelDetailDate(day_span,halfStr);
	}
}
//点击半天复选框
window.selectHalfDayCheckBox = function(v){
	selectItem(v);	
	var isCheck = $(v).is(':checked');
	var day_span = $(v).parents('.muiDestroyLeaveCheckbox').find(".day_span").text().substring(0,10);
	if(isCheck){//选中事件
		setDetailDate(day_span,"AM,PM");
	}else{//取消事件
		setCancelDetailDate(day_span,"AM,PM");
	}
}

//按明细销假-按小时点击checkBox
window.selectHourCheckBox=function(v){
	selectItem(v);	
	var isCheck = $(v).is(':checked');
	var day_span = $(v).parents('.muiDestroyLeaveCheckbox').find(".day_span").text().substring(0,10);
	var selectTimeSpan= $(v).parents('.allHour').find(".muiDestroyLeaveItem_span");
	var selectTimes=[];
	for (var i = 0; i < selectTimeSpan.length; i++) {
		selectTimes[i]=$(selectTimeSpan[i]).text();
	}
	if(isCheck){//选中事件
		setDetailDate(day_span,selectTimes.toString());
	}else{//取消事件
		setCancelDetailDate(day_span,selectTimes.toString());
	}
}

//按明细销假-按小时点击时间段
window.selectHour=function(v){
	muiDestroyLeaveItem(v);
	
	var day_span = $(v).parents('.allHour').find(".day_span").text().substring(0,10);
	var selectTimeSpan= $(v).parents('.allHour').find(".muiDestroyLeaveItem_span");//取到一天所有的时间段
	var selectTimes=[];
	
	if($(v).hasClass('selected')){//选中事件
		var count =0;
		for (var i = 0; i < selectTimeSpan.length; i++) {
			var selectHourItem = $(selectTimeSpan[i]).parent(".selectHourItem");
			
			if($(selectHourItem).hasClass('selected')){
				selectTimes[count]=$(selectTimeSpan[i]).text();
				count++
			}			
		}		
		setDetailDate(day_span,selectTimes.toString());
	}else{//取消事件
		var count =0;
		for (var i = 0; i < selectTimeSpan.length; i++) {
			var selectHourItem = $(selectTimeSpan[i]).parent(".selectHourItem");
			
			if($(selectHourItem).hasClass('selected')){				
				selectTimes[count]=$(selectTimeSpan[i]).text();
				count++
			}			
		}		
		setCancelDetailDateOfHour(day_span,selectTimes.toString());
	}
}

//取消选中--按明细-按小时专用....
window.setCancelDetailDateOfHour = function(date,time){
	var detaildate = $("#detailDate").val();
	var jsonStr = [];
	if(detaildate){
		if(detaildate.indexOf(date)>0){
			var detailJson = JSON.parse(detaildate);
			for (var i = 0; i < detailJson.length; i++) {
				if(detailJson[i] && date != detailJson[i]["date"]){
					jsonStr[jsonStr.length]=detailJson[i];
				}else{
					if(time){
						if(time.indexOf(",")<0){//不是勾选复选框，点击时间段一个一个取消的，无,
							var timeStr = detailJson[i]["time"].split(",");
							for (var m = 0; m < timeStr.length; m++) {
								if(time == timeStr[m]){
									timeStr = timeStr.splice(m,1);
									detailJson[i]["time"] = timeStr.toString();
								}
							}
							jsonStr[jsonStr.length]=detailJson[i];
						}else{//勾选复选框取消的，含，
							detailJson[i]["time"] = time.toString();
							jsonStr[jsonStr.length]=detailJson[i];
						}
					}
				}
			}
			if(jsonStr.length>0){
				$("#detailDate").val(JSON.stringify(jsonStr));
			}else{
				$("#detailDate").val("");
			}
		}
	}
}


//取消选中
window.setCancelDetailDate = function(date,time){
	var detaildate = $("#detailDate").val();
	var jsonStr = [];
	if(detaildate){
		if(detaildate.indexOf(date)>0){
			var detailJson = JSON.parse(detaildate);
			for (var i = 0; i < detailJson.length; i++) {
				if(detailJson[i] && date != detailJson[i]["date"]){
					jsonStr[jsonStr.length]=detailJson[i];
				}else{
					if(time){
						if(time.indexOf(",")<0){//不是勾选复选框，点击时间段一个一个取消的，无,
							var timeStr = detailJson[i]["time"].split(",");
							for (var m = 0; m < timeStr.length; m++) {
								if(time == timeStr[m]){
									timeStr = timeStr.splice(m,1);
									detailJson[i]["time"] = timeStr.toString();
								}
							}
							jsonStr[jsonStr.length]=detailJson[i];
						}else{//勾选复选框取消的，含，
//							jsonStr[jsonStr.length]="";
						}
					}
				}
			}
			if(jsonStr.length>0){
				$("#detailDate").val(JSON.stringify(jsonStr));
			}else{
				$("#detailDate").val("");
			}
		}
	}
}

//选中
window.setDetailDate = function(date,time){
	var detaildate = $("#detailDate").val();
	var jsonStr = "";
	if(detaildate){//不为空
		if(detaildate.indexOf(date)<0){
			detaildate = detaildate.replace("]","");
			detaildate+=',{';
			detaildate+='"date":"'+date+'",';
			detaildate+='"time":"'+time+'"';
			detaildate+='}]';
			jsonStr = detaildate;
		}else{
			var dataJson = JSON.parse(detaildate);
			for (var i = 0; i < dataJson.length; i++) {
				if(date == dataJson[i]["date"]){//如果detailDate存在值，则重新赋值
					if(dataJson[i]["time"]){
						dataJson[i]["time"] += (";"+time);
					}else{
						dataJson[i]["time"] += time;
					}
				}
			}
			jsonStr=JSON.stringify(dataJson);
		}		
	}else{//为空
		jsonStr+='[{';
		jsonStr+='"date":"'+date+'",';
		jsonStr+='"time":"'+time+'"';
		jsonStr+='}]';
	}
	$("#detailDate").val(jsonStr);
}

//获取销假结束时间-按时间销假时
window.Ding_GetEndTime = function(){
	var rs = "";
	var $toTime = $("input[name*='cancelEndTime']");
	rs = $toTime.val();
	if("halfDay"===unit){
		rs += " " + $("select[name*='canceToDay']").val();
	}
	return rs;
} 

//获取销假开始时间-按时间销假时
window.Ding_GetStartTime = function(){
	var rs = "";
	var $fromTime = $("input[name*='cancelStartTime']");
	rs = $fromTime.val();
	if("halfDay"===unit){
		rs += " " + $("select[name*='cancelFromDay']").val();
	}
	return rs;
}

//比较时间段-上午下午（半天制）
window.checkHalfDay = function(time,day){
	var levelStartTime1 = fromTimes;//请假开始时间
	var levelEndTime1 = toTimes;//请假结束时间
	if(!time){
		networkFailure("请先选择日期时间");
		return false;
	}
	if('day'===dw || 'halfDay'===dw){
		levelStartTime1 = fromTimes.substring(0,10);
		levelEndTime1 = toTimes.substring(0,10);
	}
	//先和请假开始时间比
	var date1 = new Date(levelStartTime1);
	var date2 = new Date(time);
	if(date1.getTime()-date2.getTime()==0){//选择的时间等于开始时间，比较选择的时间段和开始时间段
		//如果请假开始时间段是上午，那么选择销假时间段就可以随便选，因为销假时间和请假开始时间一样
		//如果请假开始时间段是下午，那么在销假时间和请假开始时间一样的情况下，销假时间段只能选择下午（上午被拦住）
		if("PM" == from_half_day_str && "AM" == day){
			networkFailure("请重新选择时间段");
			return false;
		}
	}
	date1  = new Date(levelEndTime1);
	if(date1.getTime()-date2.getTime()==0){//选择的时间等于结束时间，比较选择的时间段和结束时间段
		if("AM" == to_half_day_str && "PM" == day){
			networkFailure("请重新选择时间段");
			return false;
		}
	}
	return true;
}

//校验两个时间大小
window.checkTimes = function(selectTime){
	var fromTimes1 =fromTimes;
	var toTimes1 = toTimes;
	if('day'===dw || 'halfDay'===dw){
		fromTimes1 = fromTimes.substring(0,10);
		toTimes1 = toTimes.substring(0,10);
	}
	if(selectTime){
		var flag1 = compareTimes(selectTime, fromTimes1,false);
		var flag2 = compareTimes(selectTime, toTimes1,true);
		
		if(flag1 || !flag2){
			return false;
		}
	}
	return true;
}

//判断日期，时间大小  
window.compareTimes = function(startDate, endDate,flag) {   
	 var date1 = new Date(startDate).getTime();
	 var date2 = new Date(endDate).getTime();
	 if(!flag){
		 if(date1-date2>=0){
			 return false;  //startDate>=endDate
		 }else{
			 return true;  
		 }
	 }else{
		 if(date1-date2>0){
			 return false;  //startDate>endDate
		 }else{
			 return true;  
		 }
	 }
}

//公共提示语
window.networkFailure = function(msg){
	seajs.use(['lui/dialog'], function (dialog) {
        dialog.failure(msg);
    });
}

//时间戳转年月日时分秒
window.to_time = function(times,dw){
	if(!times||!dw){
		return "";
	}
	var time = new Date(times);
	let year = time.getFullYear();
	const month = (time.getMonth() + 1).toString().padStart(2, '0');
	const date = (time.getDate()).toString().padStart(2, '0');
	const hours = (time.getHours()).toString().padStart(2, '0');
	const minute = (time.getMinutes()).toString().padStart(2, '0');
	const second = (time.getSeconds()).toString().padStart(2, '0');
	if("day"===dw || "halfDay"===dw){
		return year + '-' + month + '-' + date;
	}
	if("hour" == dw){
		return  hours + ':' + minute;
	}
	return year + '-' + month + '-' + date + ' ' + hours + ':' + minute + ':' + second;
}

//获取两个日期之间的每一天
window.getDays = function(stime,etime){
	//初始化日期列表，数组
    var diffdate = new Array();
    var i=0;
    //开始日期小于等于结束日期,并循环
    while(stime<=etime){
        diffdate[i] = stime;
        
        //获取开始日期时间戳
        var stime_ts = new Date(stime).getTime();        
        //增加一天时间戳后的日期
        var next_date = stime_ts + (24*60*60*1000);        
        //拼接年月日，这里的月份会返回（0-11），所以要+1
        var next_dates_y = new Date(next_date).getFullYear()+'-';
        var next_dates_m = (new Date(next_date).getMonth()+1 < 10)?'0'+(new Date(next_date).getMonth()+1)+'-':(new Date(next_date).getMonth()+1)+'-';
        var next_dates_d = (new Date(next_date).getDate() < 10)?'0'+new Date(next_date).getDate():new Date(next_date).getDate();
 
        stime = next_dates_y+next_dates_m+next_dates_d;        
        //增加数组key
        i++;
    }
    return diffdate;
}

//日期格式化（按flag返回格式）
window.getLocalTime = function(nS,flag) { 
	var date = new Date(parseInt(nS)).format("yyyy-MM-dd HH:mm");
	if(date){
		if("date"==flag){
			date=date.substring(0,10);
		}else if("time"==flag){
			date=date.substring(11,16);
		}else{
			return date;  
		}
	}
	return date;  
}

//获取一个日期是星期几
window.getWeek=function(dateStr) {
	var date = new Date(dateStr);
    var week;
    if(date.getDay() == 0) week = "周日"
    if(date.getDay() == 1) week = "周一"
    if(date.getDay() == 2) week = "周二"
    if(date.getDay() == 3) week = "周三"
    if(date.getDay() == 4) week = "周四"
    if(date.getDay() == 5) week = "周五"
    if(date.getDay() == 6) week = "周六"
    return week;
}

//获取两个时间中的按1小时间隔时间列表
window.getEveryHours =function(startTime ,endtime) { 
	var dateList = new Array();
	var startHour =  startTime.split(":"); 
	var endHour =  endtime.split(":"); 
	
	var hour1 = parseInt(startHour[0]);//8
	var hour2 = parseInt(endHour[0]);//17
	var num = 0;
	for (var i = hour1; i <= hour2; i++) {
		if(i < hour2){
			dateList[num]=i+":"+startHour[1];
		}
		if(i == hour2){
			dateList[num]=i+":"+endHour[1];
		}
		num++;
	}	
    return dateList
}
window.saveCancelDetail=function(){
	var levelType = $("input[name='extendDataFormInfo.value(cancelType)']:checked").val();
	if("detail"==levelType){
		$(".detail_div").html();
		$("input[name*='extendDataFormInfo.value(detailInfoForm)']").val('');
		$("input[name*='extendDataFormInfo.value(detailInfoForm)']").val($(".detail_div").html());
	}
	var card = $("input[name*='extendDataFormInfo.value(cardInfo)']").val();
	var cardInfo = JSON.parse(card);
	cardInfo["unitStr"] = unitStr;
	cardInfo["durationTime"] = durationTime;
	cardInfo["dw"] = dw;
	cardInfo["fromTimes"] = fromTimes;
	cardInfo["fromTimes"] = fromTimes;
	cardInfo["toTimes"] = toTimes;
	cardInfo["levelCode"] = levelCode;
	cardInfo["from_half_day_str"] = from_half_day_str;
	cardInfo["to_half_day_str"] = to_half_day_str;
	cardInfo["unit"] = unit;
	//cardInfo["classInfo"] = classInfo;
	//cardInfo["detailDate"] = detailDate;
	
	var cancelStartTime = $("input[name*='extendDataFormInfo.value(cancelStartTime)']").val();
	var cancelEndTime = $("input[name*='extendDataFormInfo.value(cancelEndTime)']").val();
	
	$("input[name*='extendDataFormInfo.value(cancelLevelStartTime)']").val(cancelStartTime);
	$("input[name*='extendDataFormInfo.value(cancelLevelEndTime)']").val(cancelEndTime);
	
	
	//1、取选中的存入extendDataFormInfo.value(cancelLevelInfo)
	//2、取未被选中的传后台：detailDate
	var detailDateOld = $("input[name*='extendDataFormInfo.value(detailDate)']").val();
	var detailNew;
	var cancelLevelInfoNew="";
	var selectItemBox =  $("[name*='SelectItem']");
	$("input[name*='extendDataFormInfo.value(detailDate)']").val('');
	for (var i = 0; i < selectItemBox.length; i++) {
		var dateStr1 = $(selectItemBox[i]).parent(".muiDestroyLeaveCheckbox").find(".day_span").text().substring(0,10);
		//处理按天
		if('day' == unit){
			var selectObj = $(selectItemBox[i]).parent(".muiDestroyLeaveCheckbox").next(".muiDestroyLeaveDetails").find(".muiDestroyLeaveItem");
			if(selectObj.length==0){
				continue;
			}
			if(selectObj.hasClass("selected")){//被选中/
				if(cancelLevelInfoNew){
					cancelLevelInfoNew += ";"+dateStr1
				}else{
					cancelLevelInfoNew+=dateStr1
				} 
			}else{//未被选中
				this.setDetailDate(dateStr1,"");
			}
		}
		//处理按半天
		if('halfDay'==unit){
			var selectObj_items = $(selectItemBox[i]).parent(".muiDestroyLeaveCheckbox").next(".muiDestroyLeaveDetails").find(".muiDestroyLeaveItem");
			if(selectObj_items.length==0){
				continue;
			}
			for (var si = 0; si < selectObj_items.length; si++) {
				if($(selectObj_items[si]).hasClass("noshow")){
					continue;//隐藏的半天 跳过
				}else{
					var halfItem = $(selectObj_items[si]).find(".half_span").text();
					if($(selectObj_items[si]).hasClass("selected")){
						if("AM"==halfItem){
							halfItem="上午";
						}
						if("PM"==halfItem){
							halfItem="下午";
						}
						//被选中的-要销假--展示
						halfItem=dateStr1+" "+halfItem;
						if(cancelLevelInfoNew){
							cancelLevelInfoNew += ";"+halfItem
						}else{
							cancelLevelInfoNew+=halfItem
						}						
					}else{
						setDetailDate(dateStr1,halfItem);
					}
				}				
			}
		}
		//处理按小时
		if('hour'==unit){
			var items = $(selectItemBox[i]).parent(".muiDestroyLeaveCheckbox").next(".muiDestroyLeaveDetails").find(".muiDestroyLeaveItem");
			if(items.length==0){
				continue;
			}
			for (var sw = 0; sw < items.length; sw++) {
				if($(items[sw]).hasClass("noshow")){
					continue;//隐藏的半天 跳过
				}else{
					var halfItem = $(items[sw]).find(".muiDestroyLeaveItem_span").text();
					if($(items[sw]).hasClass("selected")){
						//被选中的-要销假--展示
						halfItem=dateStr1+" "+halfItem;
						if(cancelLevelInfoNew){
							cancelLevelInfoNew += ";"+halfItem
						}else{
							cancelLevelInfoNew+=halfItem
						}						
					}else{
						setDetailDate(dateStr1,halfItem);
					}
				}
			}
		}
	}	
	$("[name*='extendDataFormInfo.value(cancelLevelInfo)']").val(cancelLevelInfoNew);
	cardInfo["cancelLevelInfoNew"] = cancelLevelInfoNew;
	$("input[name*='extendDataFormInfo.value(cardInfo)']").val(JSON.stringify(cardInfo));
}