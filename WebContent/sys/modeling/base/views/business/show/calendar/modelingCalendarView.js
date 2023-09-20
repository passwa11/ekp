/**
 * 日历组件
 */
define(function(require, exports, module) {
	
	var $=require("resource/js/jquery-ui/jquery.ui");
	var Calendar = require("lui/calendar");
	var topic = require("lui/topic");
	var Fixed=require("lui/fixed").Fixed;
	var env=require('lui/util/env');
	var lang = require('lang!sys-ui');

	//日历
	var ModelingCalendarView = Calendar.Calendar.extend({
		initProps : function($super,_config){
			$super(_config);
		},
		_scheduleAfterAllRender:function($super,view){
			$super(view);
			if(view && view.name === "basicWeek"){
				var cell = view.dateToCell(new Date());
				var fdToday = view.element.find("td.fc-day.fc-today");
				var isModifyHeader = false;
				view.element.find("th.fc-day-header").each(function (i){
					var that = $(this);
					var fdHeader = that.find(".model-fc-header");
					if(fdHeader && fdHeader.length>0){
						isModifyHeader = true;
						return;
					}
					var headerHtml = that.html();
					that.html("");
					var htmlArray = headerHtml.split(" ");
					var $newHeaderHtml = $("<div class='model-fc-header'></div>").appendTo(that);
					var $weekHtml = $("<div class='model-fc-week'></div>").appendTo($newHeaderHtml);
					$weekHtml.html(htmlArray[0]);
					if(htmlArray.length > 1){
						var $dayHtml = $("<div class='model-fc-day'></div>").appendTo($newHeaderHtml);
						$dayHtml.html(htmlArray[1]);
					}

					if(cell && i == cell.col && fdToday && fdToday.length >0){
						$newHeaderHtml.addClass("model-fc-today");
					}
				})
				if(!isModifyHeader){
					view.element.find(".fc-event.fc-event-hori").each(function() {
						var top = $(this).css("top");
						top = parseInt(top) || 0;
						$(this).css("top",top + 30 + "px");
					})
				}
			}
		}
	});
	
	exports.ModelingCalendarView = ModelingCalendarView;
	
});
