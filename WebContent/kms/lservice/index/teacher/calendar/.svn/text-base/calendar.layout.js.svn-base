	seajs.use(['lang!sys-ui','lang!kms-lservice','lui/topic','lui/dialog'],function(lang,calendarLang,topic,dialog){
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
		$('<div class="lui_calendar_header_today" cal-opt="today">'+lang['ui.calendar.today']+'</div>').appendTo(leftTd).click(function(){
			calendar.today();
		});
		var centerTd = $('<td class="lui_calendar_header_center"/>').appendTo(trTmp);
		$('<div class="lui_calendar_header_title"/>').appendTo(centerTd);
		
		var search = $('<span/>').addClass('lui_calendar_search').appendTo(trTmp),
			inputContainer = $('<span/>').addClass('lui_calendar_search_inputContainer').appendTo(search),
			input = $('<input type="text"/>').addClass('lui_calendar_search_input').appendTo(inputContainer),
			button = $('<input type="button"/>').addClass('lui_calendar_search_button').appendTo(search);
		input.attr('placeholder',lang['ui.category.keyword']);
		
		input.keydown(function(event){
			if(event.keyCode == 13){
				var value = input.val();
				
				if(!value){
					dialog.failure(lang['ui.category.keyword'],null,null,'suggest',null,{
						autoCloseTimeout : 1
					});
					return;
				}
				
				topic.publish('calenadr.thing.search',{
					value : value
				});
				var calendarContainer = $('#calendar'),
					searchContainer = $('#calendar_search_container'),
					searchContainerParent = searchContainer.parent(),
					searchInput = $('.lui_calendar_search_input',searchContainer);
				searchInput.val(value);
				
				searchContainer.css({
					width : searchContainerParent.width()
				});
				searchContainer.show();
				calendarContainer.hide();
			}
		});
		button.click(function(){
			var value = input.val();
			
			if(!value){
				dialog.failure(lang['ui.category.keyword'],null,null,'suggest',null,{
					autoCloseTimeout : 1
				});
				return;
			}
			
			topic.publish('calenadr.thing.search',{
				value : value
			});
			var calendarContainer = $('#calendar'),
				searchContainer = $('#calendar_search_container'),
				searchContainerParent = searchContainer.parent(),
				searchInput = $('.lui_calendar_search_input',searchContainer);
			searchInput.val(value);
			
			searchContainer.css({
				width : searchContainerParent.width()
			});
			searchContainer.show();
			calendarContainer.hide();
		});
		
		var rightTd = $('<td class="lui_calendar_header_right"/>').appendTo(trTmp);
		
		$('<div class="lui_calendar_header_month" cal-opt="month" cal-mode="default">'+lang['ui.calendar.mode.month']+'</div>').appendTo(rightTd).click(function(){
			window.type="month";
			calendar.changeView('month');
		});
		
		$('<div class="lui_calendar_header_month" cal-opt="agendaWeek" cal-mode="default">'+lang['ui.calendar.mode.week']+'</div>').appendTo(rightTd).click(function(){
			window.type="agendaWeek";
			calendar.changeView('agendaWeek');
		});
		
		$('<div class="lui_calendar_header_refresh"/>').appendTo(rightTd).click(function(){
			calendar.refreshSchedules();
		});
		
		var ul=$("<ul class='calendar_dropdown'/>");
		
		return headObj;
	}
	
	function createBottom(){
		var bottom = $('<div class="train_calendar_botom"/>');
		$('<span class="train_calendar_label_unhold">&nbsp;</span><span class="train_calendar_label_text" >'+calendarLang['kmsLservice.calendar.layour.beforeTrain']+'</span>').appendTo(bottom);
		$('<span class="train_calendar_label_holding">&nbsp;</span><span class="train_calendar_label_text">'+calendarLang['kmsLservice.calendar.layour.training']+'</span>').appendTo(bottom);
		$('<span class="train_calendar_label_hold">&nbsp;</span><span class="train_calendar_label_text">'+calendarLang['kmsLservice.calendar.layour.afterTrain']+'</span>').appendTo(bottom);
		return bottom;
	}
	
	function displayChange(info){
		var mode = info.mode;
		var view = info.view;
		frame.find(".lui_calendar_header_tab div[cal-mode]").hide();
		frame.find(".lui_calendar_header_tab div[cal-mode='" + mode + "']").show();
		if(view!=null){
			$(".lui_calendar_header .lui_calendar_header_center .lui_calendar_header_title").text(view.title);
			$(".lui_calendar_header_tab div[cal-opt]").removeClass("lui_calendar_header_button_on");
			$(".lui_calendar_header_tab div[cal-opt='"+view.name+"']").addClass("lui_calendar_header_button_on");
			var today = new Date().getTime();
			if(!(view.start==undefined&&view.end==undefined)){
				if(view.start.getTime() <= today && today <= view.end.getTime() ){
					$(".lui_calendar_header_tab div[cal-opt='today']").addClass("lui_calendar_header_button_on");
				}
			}
		}
	}
	
	var frame = createFrame();
	frame.append(createHeader());
	frame.append(createDefaultContent());
	frame.append(createListContent());
	frame.append(createBottom());
	
	
	calendar.on("viewOrModeChange", displayChange);
	calendar.onErase(function() {
			calendar.off('viewOrModeChange', displayChange);
			});
	
	done(frame);
	
});