
/**
 * 内置日期初始化
 */
define(function(require, exports, module) {
	var now = new Date(); // 当前日期
	var nowDayOfWeek = now.getDay(); // 今天本周的第几天
	var nowDay = now.getDate(); // 当前日
	var nowMonth = now.getMonth(); // 当前月
	var nowYear = now.getYear(); // 当前年
	nowYear += (nowYear < 2000) ? 1900 : 0;
	
	var lastMonthDate = new Date(); //上月日期 
	lastMonthDate.setDate(1);
	var lastMonth = lastMonthDate.getMonth() - 1;
	var lastYear = lastMonthDate.getYear();
	if (lastMonth < 0) 
		lastYear = lastYear - 1;
	lastMonthDate.setYear(lastYear);
	lastMonthDate.setMonth(lastMonth);
	
	// 日期格式化
	function _formatDate(date, fmt) {
		fmt = fmt || seajs.data.env.pattern.date;
		var o = {
	        "M+" : date.getMonth() + 1, // 月份
	        "d+" : date.getDate(), // 日
	        "h+" : date.getHours(), // 小时
	        "m+" : date.getMinutes(), // 分
	        "s+" : date.getSeconds(), // 秒
	        "q+" : Math.floor((date.getMonth() + 3) / 3), // 季度
	        "S" : date.getMilliseconds() // 毫秒
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
	
	// 获得某月的天数
	function getMonthDays(myMonth) {
		var monthStartDate = new Date(nowYear, myMonth, 1);
		var monthEndDate = new Date(nowYear, myMonth + 1, 1);
		var days = (monthEndDate - monthStartDate) / (1000 * 60 * 60 * 24);
		return days;
	}

	// 获得本季度的开端月份
	function getQuarterStartMonth() {
		var quarterStartMonth = 0;
		if(nowMonth < 3) {
			quarterStartMonth = 0; 
		}
		if(2 < nowMonth && nowMonth < 6) {
			quarterStartMonth = 3;
		}
		if(5 < nowMonth && nowMonth < 9) {
			quarterStartMonth = 6;
		}
		if(nowMonth > 8) {
			quarterStartMonth = 9;
		}
		return quarterStartMonth;
	}
	
	exports.formatDate = _formatDate;
	
	// 今天
	exports.getToday = function() {
		return _formatDate(now);
	}
	
	// 本周开始日期
	exports.getWeekStartDate = function() {
		// 需要根据admin.do配置的周起始日
		var _startDate = nowDay - nowDayOfWeek + Com_Parameter.FirstDayInWeek;
		var weekStartDate = new Date(nowYear, nowMonth, _startDate);
		return _formatDate(weekStartDate);
	}

	// 本周结束日期
	exports.getWeekEndDate = function() {
		// 需要根据admin.do配置的周起始日
		var _endDate = nowDay + (6 - nowDayOfWeek) + Com_Parameter.FirstDayInWeek;
		var weekEndDate = new Date(nowYear, nowMonth, _endDate);
		return _formatDate(weekEndDate);
	}
	
	// 本月开始日期
	exports.getMonthStartDate = function() {
		var monthStartDate = new Date(nowYear, nowMonth, 1);
		return _formatDate(monthStartDate);
	}

	// 本月结束日期
	exports.getMonthEndDate = function() {
		var monthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowMonth));
		return _formatDate(monthEndDate);
	}

	// 上月开始日期
	exports.getLastMonthStartDate = function() {
		var lastMonthStartDate = new Date(nowYear, lastMonth, 1);
		return _formatDate(lastMonthStartDate);
	}

	// 上月结束日期
	exports.getLastMonthEndDate = function() {
		var lastMonthEndDate = new Date(nowYear, lastMonth, getMonthDays(lastMonth));
		return _formatDate(lastMonthEndDate);
	}

	// 本季度开始日期
	exports.getQuarterStartDate = function() {
		var quarterStartDate = new Date(nowYear, getQuarterStartMonth(), 1);
		return _formatDate(quarterStartDate);
	}

	// 本季度结束日期
	exports.getQuarterEndDate = function() {
		var quarterEndMonth = getQuarterStartMonth() + 2;
		var quarterStartDate = new Date(nowYear, quarterEndMonth, getMonthDays(quarterEndMonth));
		return _formatDate(quarterStartDate);
	}
	
	// 本年开始日期
	exports.getYearStartDate = function() {
		var monthStartDate = new Date(nowYear, 0, 1);
		return _formatDate(monthStartDate);
	}

	// 本年结束日期
	exports.getYearEndDate = function() {
		var monthEndDate = new Date(nowYear, 11, getMonthDays(11));
		return _formatDate(monthEndDate);
	}
	
	// 上一年开始日期
	exports.getLastYearStartDate = function() {
		var monthStartDate = new Date(nowYear - 1, 0, 1);
		return _formatDate(monthStartDate);
	}

	// 上一年结束日期
	exports.getLastYearEndDate = function() {
		var monthEndDate = new Date(nowYear - 1, 11, getMonthDays(11));
		return _formatDate(monthEndDate);
	}
	
	exports.getLastWeek = function() {
		
		var time = now.getTime();
		
		var lastDate = new Date(time-24*60*60*1000*7); //一周前日期 
		
		return _formatDate(lastDate);
	}
	
	exports.getLastMonth = function() {
		var lastDate = new Date(); //上月日期 
		var lMonth = lastDate.getMonth() - 1;
		var lYear = lastDate.getFullYear();
		if (lMonth < 0) {
			lYear = lYear - 1;
			lMonth = 12 +lMonth;
		}
		lastDate.setYear(lYear);
		lastDate.setMonth(lMonth);
		return _formatDate(lastDate);
	}
	
	exports.getLast3Month = function() {
		var lastDate = new Date(); //上月日期 
		var lMonth = lastDate.getMonth() - 3;
		var lYear = lastDate.getFullYear();
		if (lMonth < 0) {
			lYear = lYear - 1;
			lMonth = 12 +lMonth;
		}
		lastDate.setYear(lYear);
		lastDate.setMonth(lMonth);
		return _formatDate(lastDate);
	}
	
	exports.getLastHalfYear = function() {
		var lastDate = new Date(); //半年前日期 
		var lMonth = lastDate.getMonth() - 6;
		var lYear = lastDate.getFullYear();
		if (lMonth < 0) {
			lYear = lYear - 1;
			lMonth = 12 +lMonth;
		}
		lastDate.setYear(lYear);
		lastDate.setMonth(lMonth);
		return _formatDate(lastDate);
	}
	
	exports.getLastYear = function() {
		var lastDate = new Date(); //一年前日期 
		var lYear = lastDate.getFullYear()-1;
		lastDate.setYear(lYear);
		return _formatDate(lastDate);
	}
});