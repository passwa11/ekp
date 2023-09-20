var now = new Date();
var locale = env.config.locale || 'zh-cn';
locale = locale.toLowerCase();
function createDateInfo(data){
	//日期只支持中英
	var container = $('<div class="lui_dataview_emotion_date"/>')
	var h5 = $('<h5 />');
	$('<span />').text(getDateRender(now)).appendTo(h5);
	$('<span />').text(getWeekRender(now.getDay())).appendTo(h5);
	h5.appendTo(container);
	$('<h4 />').text(getTimeRender(now)).appendTo(container);
	return container;
}
function getDateRender(value){
	var template ="yyyy年MM月dd日";
	if(locale.indexOf('cn')==-1){
		template ="MM dd yyyy";
	}
	template = template.replace("yyyy",now.getFullYear())
					   .replace("MM",getMonthRender(now.getMonth()+1))
					   .replace("dd",now.getDate());
	return template;
}
function getTimeRender(value){
	return timeFormat(value.getHours())+':' + timeFormat(value.getMinutes())+":" + timeFormat(value.getSeconds());
}
function getMonthRender(value){
	if(locale.indexOf('cn')>-1){
		return value;
	}
	var months = {
		1:'Jan',
		2:'Feb',
		3:'Mar',
		4:'Apr',
		5:'May',
		6:'Jun',
		7:'Jul',
		8:'Aug',
		9:'Sep',
		10:'Oct',
		11:'Nov',
		12:'Dec'
	};
	return months[value];
}

function getWeekRender(value){
	var weeks = {
		0:'周日',
		1:'周一',
		2:'周二',
		3:'周三',
		4:'周四',
		5:'周五',
		6:'周六'
	};
	var weekNamesShort= ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'];
	if(locale.indexOf('cn')>-1){
		return weeks[value];
	}
	return weekNamesShort[value];
}

function isBetweenTime(startTime,endTime) {
	if (!startTime || !endTime) {
		return false;
	}
	var startTimes = startTime.split(":");
	var endTimes = endTime.split(":");

	var start = parseInt(startTimes[0]) * 60
			+ parseInt(startTimes[1]);
	var end = parseInt(endTimes[0]) * 60
			+ parseInt(endTimes[1]);
	var date = new Date();
	var now = date.getHours() * 60 + date.getMinutes();
	if (start <= end) {
		if (now >= start && now <= end) {
			return true;
		}
	} else {
		var startTime1 = start;
		var endTime1 = 23 * 60 + 59;
		var startTime2 = 0;
		var endTime2 = end;
		if (now >= startTime1 && now <= endTime1
				|| now >= startTime2 && now <= endTime2) {
			return true;
		}
	}
	return false;
}

function getCurrentTip(data){
	var tips = data.tips;
	for(var i = 0 ;i < tips.length;i++){
		var record = tips[i];
		var startTime = record.startTime;
		var endTime = record.endTime;
		var texts = record.texts;
		if(isBetweenTime(startTime,endTime)){
			var idx = parseInt(Math.random() * texts.length);
			var value = texts[idx].value || ''
			return {
				value:value,
				startTime:startTime,
				endTime:endTime
			};
		}
	}
	return {};
}

function timeCounter(){
	now = new Date();
	var time = timeFormat(now.getHours())+':' + timeFormat(now.getMinutes())+":" + timeFormat(now.getSeconds());
	html.find('.lui_dataview_emotion_date h4').text(time);
}

function timeFormat(v){
	return v >= 10 ? v : "0" + v;
}

function refreshTip(data,domNode){
	var tips = data.tips;
	var nowTip = domNode.attr('cfg-times');
	for(var i = 0 ;i < tips.length;i++){
		var record = tips[i];
		var startTime = record.startTime;
		var endTime = record.endTime;
		var between = startTime + "_" + endTime;
		var texts = record.texts;
		if(isBetweenTime(startTime,endTime)){
			if(between==nowTip){
				break;
			}
			var idx = parseInt(Math.random() * texts.length);
			var value = texts[idx].value || '';
			domNode.find('p').html(value);
			domNode.attr('cfg-times',between)
			break;
		}
	}
}

var html = (function(){
	if(data.tips && data.tips.length>0){
		var box = $('<div class="lui_dataview_emotion" />')
		createDateInfo(data).appendTo(box);
		$('<div class="lui_dataview_emotion_line" />').appendTo(box);
		setInterval(function(){
			timeCounter();
		},1000);
		
		var ret = getCurrentTip(data);
		var tipsNode = $('<div class="lui_dataview_emotion_tips" />').attr('cfg-times',ret.startTime + "_" + ret.endTime).appendTo(box);
		$('<p />').html(ret.value || '').appendTo(tipsNode);
		
		setInterval(function(){
			refreshTip(data,tipsNode);
		},60000);
	}
	return box;
})();

done(html);
