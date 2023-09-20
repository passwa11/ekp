seajs.use(['lang!km-imeeting'],function(lang){

	var calendar = layout.calendar;
	var frame = null;
	var modeSetting = calendar.modeSetting;
	var showHeader = true;
	if(calendar.config.vars && calendar.config.vars.showHeader){
		showHeader = (calendar.config.vars.showHeader=='false'||calendar.config.vars.showHeader==false)?false:true;
	}

	function createFrame(){
		return $('<div class="lui_calendar_frame"/>');
	}
	function createContent(modeId){
		return $('<div data-lui-mark="calender.content.inside.' + modeId + '" class="lui_calendar_content"/>');
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
		$('<div class="lui_calendar_header_today" cal-opt="today">'+lang['kmImeeting.today']+'</div>').appendTo(leftTd).click(function(){
			calendar.today();
		});
		var centerTd = $('<td class="lui_calendar_header_center"/>').appendTo(trTmp);
		$('<div class="lui_calendar_header_title"/>').appendTo(centerTd);
		
		var rightTd = $('<td class="lui_calendar_header_right"/>').appendTo(trTmp);
		
		//日试图
		$('<div class="lui_calendar_header_month" cal-opt="day">'+lang['kmImeeting.day']+'</div>').appendTo(rightTd).click(function(){
			calendar.changeMode('day');
		});
		//周视图
		$('<div class="lui_calendar_header_month" cal-opt="week">'+lang['kmImeeting.week']+'</div>').appendTo(rightTd).click(function(){
			calendar.changeMode('week');
		});
		
		$('<span class="meeting_calendar_label_examing">&nbsp;</span><span class="meeting_calendar_label_text" >'+lang['kmImeeting.status.publish.examing']+'</span>').appendTo(rightTd);
		
		$('<span class="meeting_calendar_label_unhold">&nbsp;</span><span class="meeting_calendar_label_text" >'+lang['kmImeeting.status.publish.unHold']+'</span>').appendTo(rightTd);
		
		$('<span class="meeting_calendar_label_holding">&nbsp;</span><span class="meeting_calendar_label_text">'+lang['kmImeeting.status.publish.holding']+'</span>').appendTo(rightTd);
		
		$('<span class="meeting_calendar_label_hold">&nbsp;</span><span class="meeting_calendar_label_text">'+lang['kmImeeting.status.publish.hold']+'</span>').appendTo(rightTd);
		
		$('<div class="lui_calendar_header_refresh" />').appendTo(rightTd).click(function(){
			//window.location.reload();
			setTimeout(function(){
				LUI('____calendar____imeeting').refreshSchedules();
			}, 100);
		});
		return headObj;
	}

	function displayChange(info){
		var mode = info.mode;
		var view = info.view;
		frame.find(".lui_calendar_header_tab .lui_calendar_header_right div[cal-mode]").show();
		//frame.find(".lui_calendar_header_tab .lui_calendar_header_right div[cal-mode='" + mode + "']").hide();
		/*if(mode=="default"){
			frame.find(".lui_calendar_header_tab .lui_calendar_header_right div[cal-opt]").show();
		}else{
			frame.find(".lui_calendar_header_tab .lui_calendar_header_right div[cal-opt]").hide();
		}*/
		if(view!=null){
			frame.find(".lui_calendar_header .lui_calendar_header_center .lui_calendar_header_title").text(view.title);
			frame.find(".lui_calendar_header_tab div[cal-opt]").removeClass("lui_calendar_header_button_on");
			frame.find(".lui_calendar_header_tab div[cal-opt='"+view.name+"']").addClass("lui_calendar_header_button_on");
			var today = new Date().getTime();
			if(view.start.getTime() <= today && today <= view.end.getTime() ){
				frame.find(".lui_calendar_header_tab div[cal-opt='today']").addClass("lui_calendar_header_button_on");
			}
		}
	}

	var frame = createFrame();
	if(showHeader)
		frame.append(createHeader());
	for(var key in modeSetting){
		frame.append(createContent(key));
	}

	calendar.on("viewOrModeChange", displayChange);
	calendar.onErase(function() {
			calendar.off('viewOrModeChange', displayChange);
			});

	done(frame);
	
});

