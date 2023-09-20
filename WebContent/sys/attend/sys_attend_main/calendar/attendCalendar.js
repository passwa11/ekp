define(function(require, exports, module) {
	var Calendar = require("lui/calendar");
	var $ = require('lui/jquery');
	
	// 原sys/ui/calendar.js中获取的是全局的节假日，这里获取所在考勤组的节假日
	var getHPDay = function() {
		var url =  Com_Parameter.ContextPath + "sys/attend/sys_attend_main/sysAttendMain.do?method=getHPDay";
		var da;
		$.ajax({
		   type: "GET",
		   url: url,
		   dataType: "json",
		   async:false,
		   success: function(data){
		     da = data;
		   }
		});
		return da;
	};
	
	var AttendCalendarMode = Calendar.FullCalendarMode.extend({
		
		init : function(){
			var self = this;
			var tmpConfig={};
			var holidayPach = getHPDay();
			//绘制日历时调用
			tmpConfig.dayRender = function(date,cellObj){
				self._dayShow(date,cellObj,holidayPach);
			};
			//日历点击时触发
			tmpConfig.dayClick = function(date, allDay, evt){
				if(window.console)
					window.console.log("FullCalendar:click");
			};
			
			//日历数据拖动开始时调用
			tmpConfig.eventDragStart = function(thing, evt, ui){
				if(window.console)
					window.console.log("FullCalendar:eventDragStart");
			};
			
			//日历数据拖动结束时调用
			tmpConfig.eventDragStop = function(thing, evt, ui){
				if(window.console)
					window.console.log("FullCalendar:eventDragStop");
			};
			//日历数据拖动后调用
			tmpConfig.eventDrop = function(thing, dayDelta, minuteDelta, allDay , moveFun , evt, ui){
				self._scheduleDrag(thing, dayDelta, minuteDelta, allDay , moveFun , evt, ui);
			};
			//日历数据时间事件区域开始时执行
			tmpConfig.eventResizeStart = function(thing,evt){
				if(window.console)
					window.console.log("FullCalendar:eventResizeStart");
			};
			//日历数据时间事件区域结束时执行
			tmpConfig.eventResizeStop = function(thing,evt){
				if(window.console)
					window.console.log("FullCalendar:eventResizeStop");	
			};
			//日历数据调整时间区域时执行
			tmpConfig.eventResize = function(thing,dayDelta,minuteDelta,moveFun,evt,ui){
				self._scheduleResize(thing,dayDelta,minuteDelta,moveFun,evt,ui);
			};
			
			//日历区域选择时执行
			tmpConfig.select = function(startDate, endDate, allDay , evt){
				self._select(startDate, endDate, allDay , evt);
			};
			//日历区域选择离开时执行
			tmpConfig.unselect = function(evt){
				self._unselect(evt);
			};
			//TODO 暂不清楚什么时候调用
			tmpConfig.drop = function(){
				if(window.console)
					window.console.log("drop");
			};
			this.calSetting = Object.extend(this.calSetting,tmpConfig);
		},
	});
	
	module.exports = AttendCalendarMode;
});