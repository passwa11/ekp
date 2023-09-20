seajs.use(['lang!sys-ui','lang!km-calendar','lui/dialog','lui/jquery','lui/util/env','lui/topic','km/calendar/resource/js/calendar_personGroup'],function(lang,calendarLang,dialog,$,env,topic,personGroup){
var calendar = layout.calendar;
var frame = null;
function createFrame(){
	return $('<div class="lui_calendar_frame"/>');
}
function createDefaultContent(){
	return $('<div data-lui-mark="calender.content.inside.default" class="lui_calendar_content"/>');
}
function createListContent(){
	return $('<div data-lui-mark="calender.content.inside.list" class="lui_calendar_content"/>');
}
function createGroupContent(){
	return $('<div data-lui-mark="calender.content.inside.personGroupCalendar" class="lui_calendar_content"/>');
}
function createHeader(){
	var headObj = $('<div class="lui_calendar_header"/>');
	var tabTmp = $('<table class="lui_calendar_header_tab"/>').appendTo(headObj);
	var trTmp  = $('<tr/>').appendTo(tabTmp);
	var leftTd = $('<td class="lui_calendar_header_left"/>').appendTo(trTmp);
	$('<div cal-opt="prev"/>').append('<span class="lui_calendar_header_prev"/>').appendTo(leftTd).click(function(){
		calendar.prev();
	});
	$('<div cal-opt="next"/>').append('<span class="lui_calendar_header_next"/>').appendTo(leftTd).click(function(){
		calendar.next();
	});
	$('<div style="width:55px;" class="lui_calendar_header_today" cal-opt="today">'+lang['ui.calendar.today']+'</div>').appendTo(leftTd).click(function(){
		calendar.today();
	});
	var centerTd = $('<td class="lui_calendar_header_center"/>').appendTo(trTmp);
	$('<div class="lui_calendar_header_title"/>').appendTo(centerTd);
	
	var rightTd = $('<td style="width:45%;" class="lui_calendar_header_right"/>').appendTo(trTmp);
	
	$('<span class="group_calendar">&nbsp;</span><span class="group_calendar_label_text">'+calendarLang['kmCalendarMain.group.header.title']+'</span>').appendTo(rightTd);
	$('<div class="lui_calendar_header_month"  cal-opt="month" cal-mode="default">'+lang['ui.calendar.mode.month']+'</div>').appendTo(rightTd).click(function(){
		calendar.changeMode('default');
		calendar.changeView('month');
		resetDataSource();
		calendar.refreshSchedules();
	});
	$('<div class="lui_calendar_header_month" cal-opt="personGroupCalendar">'+lang['ui.calendar.mode.week']+'</div>').appendTo(rightTd).click(function(){
		calendar.modeSetting.personGroupCalendar = {
				id:"personGroupCalendar",
				name : "群组日历",
				func : personGroup.PersonGroupCalendarMode,
				cache: null
		};
		resetDataSource();
		calendar.changeMode('personGroupCalendar');

	});
	$('<div class="lui_calendar_header_refresh"/>').appendTo(rightTd).click(function(){
		resetDataSource();
		calendar.refreshSchedules();
	});
	
	var ul=$("<ul class='calendar_dropdown'/>");
	
	//设置
	$("<div class='calendar-setting' id='calendar_setting' unselectable='on'>"+calendarLang['km.calendar.tree.set']+"<i class='trig'></i></div>")
		.append($("<div id='setting_down' />").append(ul))
		.appendTo(rightTd).one('mousemove',function(evt){ 
            $(".calendar_dropdown").show();
            syncroMunu();
        }).mouseleave(function (evt) {
            $('.calendar_dropdown').hide();
            $("#calendar_setting").one('mousemove',function(evt){
                $(".calendar_dropdown").show();
                syncroMunu();
            });
        });
	
	return headObj;
}

function resetQueryPerson(){
	var select_all_person = $("#select_all_person");
	select_all_person.prop('checked',true);
	$("#person_view_list input[type=checkbox]").prop("checked",true);
	topic.publish("IsSelectAll",true);
}
function resetDataSource(){
	var source = calendar.source.source;
	var url = source.url;
	url = Com_SetUrlParameter(url, "fdPersonIds",'');
	source.setUrl(url);
}

function getMenuHtml(){
	// 导出
	var menuHtml ="<li><span><a onclick='kmCalendarExport();' class='textEllipsis' title='"+calendarLang['kmCalendarMain.calendar.group.export']+"'>"+calendarLang['kmCalendarMain.calendar.group.export']+"</a></span></li>";
	// 创建群组日程
	var url = "/km/calendar/km_calendar_main/kmCalendarMain.do?method=addGroupEvent&personGroupId="+Com_GetUrlParameter(window.location,'personGroupId');
	if(env.fn.authURL(url)){
		menuHtml += "<li class='split'><span><a onclick='addGroupCalendar(\"" + url + "\")' class='textEllipsis' title='"+calendarLang['kmCalendarMain.calendar.group.create']+"'>"+calendarLang['kmCalendarMain.calendar.group.create']+"</a></span></li>";
	}
	menuHtml += "</ul>";
	return menuHtml;
}
function syncroMunu(){
	var menuHtml = getMenuHtml();
	$(".calendar_dropdown").html(menuHtml);
	$(".calendar_dropdown").show();
}

function displayChange(info){
	var mode = info.mode;
	var view = info.view;
	var viewName = info.mode=='personGroupCalendar' ? "personGroupCalendar":"month";
	if(view!=null){
		$(".lui_calendar_header .lui_calendar_header_center .lui_calendar_header_title").text(view.title);
		$(".lui_calendar_header_tab div[cal-opt]").removeClass("lui_calendar_header_button_on");
		$(".lui_calendar_header_tab div[cal-opt='"+viewName+"']").addClass("lui_calendar_header_button_on");
		var today = new Date().getTime();
		if(view.start.getTime() <= today && today <= view.end.getTime() ){
			$(".lui_calendar_header_tab div[cal-opt='today']").addClass("lui_calendar_header_button_on");
		}
	}
}

var frame = createFrame();
frame.append(createHeader());
frame.append(createDefaultContent());
frame.append(createListContent());
frame.append(createGroupContent());
calendar.on("viewOrModeChange", displayChange);
calendar.onErase(function() {
		calendar.off('viewOrModeChange', displayChange);
		});

done(frame);
});