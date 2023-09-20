/**
 * 套件公用工具函数
 * 1、目前是从销假套件提取了部分出来，可能还要优化一下才能用在批量销假里
 * 2、其它套件的公共工具类也可以抽取进来
 */ 

/**
 * 比较时间段-上午下午（半天制）
 */
window.compareHalfDay = function(time,day,fromTimes,toTimes){
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

//校验两个时间大小（兼容半天制）
window.compareTwoTime = function(selectTime,fromTimes,toTimes,dw){
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

//判断日期，时间大小  (flag为true判断>;为false时判断>=)
window.compareTime = function(startDate, endDate,flag) {   
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
window.timespaceToTime = function(times,dw){
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
		return  year + '-' + month + '-' + date + ' ' + hours + ':' + minute ;
	}
	return year + '-' + month + '-' + date + ' ' + hours + ':' + minute + ':' + second;
}

//日期格式化-字符串转yyyy-MM-dd HH:mm（按flag返回格式）
window.getFormatTime = function(nS,flag) { 
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

//判断某日期是星期几
window.getWeekForDate=function(dateStr) {
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

//查看页隐藏编辑按钮
window.hideEditBtn = function(){
	var btn_span = $(".lui_normal_hearder_btn");
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
			for (var s = 0; s < btn_span.length; s++) {
				var btnText = $(btn_span[s]).text().replace(/\s*/g,"");
				if("编辑"==btnText){
					$(btn_span[s]).parent(".lui_normal_hearder_btnWrap").css("display","none");
					break;
				}
			}
		}
	}
}

/**
 * 获取A时间N天后的时间
 * dateTime:传入时间
 * days:天数
 */
window.addDateTime = function(dateTime,days){
	console.log("addDateTime in :"+dateTime);
	var date1 = new Date(dateTime);
	var date2 = new Date(date1);
	date2.setDate(date1.getDate() + days);
	var returnDate = date2.getFullYear() + "-" + (date2.getMonth() + 1) + "-" + date2.getDate();
	return returnDate;
}