seajs.use(['lang!','lang!sys-ui','lang!km-imeeting'],function(lang,uilang,imeetinglang){

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
		var leftTd = $('<td class="lui_calendar_header_left" style="width:50%;"/>').appendTo(trTmp);
		
		var $ul = $("<ul>");
		$('<li><span class="calendar-title-limit limit-part"></span><span class="title-limit-font">'+imeetinglang['kmImeeting.Participate.my']+'</span></li>').appendTo($ul);
		$('<li><span class="calendar-title-limit limit-host"></span><span class="title-limit-font">'+imeetinglang['kmImeeting.Host.my']+'</span></li>').appendTo($ul);
		$('<li><span class="calendar-title-limit limit-report"></span><span class="title-limit-font">'+imeetinglang['kmImeeting.Report.my']+'</span></li>').appendTo($ul);
		$('<li><span class="calendar-title-limit limit-emcc"></span><span class="title-limit-font">'+imeetinglang['kmImeeting.Emcc.my']+'</span></li>').appendTo($ul);
		$ul.appendTo(leftTd);
		var centerTd = $('<td class="lui_calendar_header_center" style="text-align:left;"/>').appendTo(trTmp);
		
		$('<div class="lui_calendar_btn_arrow lui_calendar_btn_arrow_prev"><i></i></div>').appendTo(centerTd).click(function(){
			calendar.prev();
		});
		
		$('<span class="lui_calendar_header_title" style="margin:0 5px"/>').appendTo(centerTd);
		
		$('<div class="lui_calendar_btn_arrow lui_calendar_btn_arrow_next"><i></i></div>').appendTo(centerTd).click(function(){
			calendar.next();
		});
		
		var rightTd = $('<td class="lui_calendar_header_right" style="width:35%"/>').appendTo(trTmp);
		
		$('<div class="lui_calendar_header_today" cal-opt="today" style="padding:0 5px;">'+lang['calendar.today']+'</div>').appendTo(rightTd).click(function(){
			calendar.today();
		});
		
		$('<div class="lui_calendar_header_month" cal-opt="month">'+lang['calendar.type.month']+'</div>').appendTo(rightTd).click(function(){
			calendar.changeView('month');
		});
		
		$('<div class="lui_calendar_header_month" cal-opt="basicWeek">'+lang['calendar.type.week']+'</div>').appendTo(rightTd).click(function(){
			calendar.changeView('basicWeek');
		});
		
		$('<div class="lui_calendar_header_date" cal-mode="default"/>').attr('title',uilang['ui.calendar.mode.list']).appendTo(rightTd).click(function(){
			calendar.changeMode('default');
		});
		
		$('<div class="lui_calendar_header_list" cal-mode="list"/>').attr('title',uilang['ui.calendar.mode.list']).appendTo(rightTd).click(function(){
			calendar.changeMode('list');
		});
		for(var key in modeSetting){
			if(key=='default'||key=='list') continue;
			$('<div cal-mode="' + key + '">'+modeSetting[key].name+'</div>').appendTo(rightTd).click(function(){
				calendar.changeMode(key);
			});
		}
		$('<div class="lui_calendar_header_refresh"/>').appendTo(rightTd).click(function(){
			calendar.refreshSchedules();
		});
		return headObj;
	}

	function displayChange(info){
		var mode = info.mode;
		var view = info.view;
		frame.find(".lui_calendar_header_tab .lui_calendar_header_right div[cal-mode]").show();
		frame.find(".lui_calendar_header_tab .lui_calendar_header_right div[cal-mode='" + mode + "']").hide();
		if(mode=="default"){
			frame.find(".lui_calendar_header_tab .lui_calendar_header_right div[cal-opt]").show();
		}else{
			frame.find(".lui_calendar_header_tab .lui_calendar_header_right div[cal-opt]").hide();
		}
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

