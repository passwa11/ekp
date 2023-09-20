
seajs.use(['lang!sys-ui','lui/topic','lui/dialog','lang!sys-modeling-main' ],function(lang,topic,dialog,modelingLang){
    var calendar = layout.calendar;
    var frame = null;
    var modeSetting = calendar.modeSetting;
    function createFrame(){
        return $('<div class="lui_calendar_frame"/>');
    }
    function createDefaultContent(){
        return $('<div data-lui-mark="calender.content.inside.default" class="lui_calendar_content"/>');
    }
    function createListContent(){
        return $('<div data-lui-mark="calender.content.inside.list" class="lui_calendar_content"/>');
    }
    function createContent(modeId){
        return $('<div data-lui-mark="calender.content.inside.' + modeId + '" class="lui_calendar_content"/>');
    }
    function createHeader(modeId){
        var headObj = $('<div class="lui_calendar_header" cal-mode="'+modeId+'"/>');
        var tabTmp = $('<div class="lui_calendar_header_tab"/>').appendTo(headObj);
        var trTmp  = $('<div/>').appendTo(tabTmp);
        var leftTd = $('<div class="lui_calendar_header_right"/>').appendTo(trTmp);
        var leftUl = $('<ul class="lui_calendar_header_right_month"/>').appendTo(leftTd);

        $('<li class="lui_calendar_header_month" cal-opt="basicWeek" cal-mode="'+modeId+'">'+lang['ui.calendar.mode.week']+'</li>').appendTo(leftUl).click(function(){
            calendar.changeView('basicWeek');
            $("#calendar_view").hide();
        });
        $('<li class="lui_calendar_header_month" cal-opt="month" cal-mode="'+modeId+'">'+lang['ui.calendar.mode.month']+'</li>').appendTo(leftUl).click(function(){
            calendar.changeView('month');
            $("#calendar_view").hide();
        });
        $('<div class="lui_calendar_header_today" cal-opt="today" cal-mode="'+modeId+'">'+lang['ui.calendar.today']+'</div>').appendTo(leftTd).click(function(){
            calendar.today();
            $("#calendar_view").hide();
        });
        $('<div class="lui_calendar_header_refresh" cal-mode="'+modeId+'"/>').appendTo(leftTd).click(function(){
            $("#calendar_view").hide();
            calendar.refreshSchedules();
        });
        var rightTd = $('<div class="lui_calendar_header_left" cal-mode="'+modeId+'"/>').appendTo(trTmp);
        $('<div cal-opt="prev"/>').append('<span class="lui_calendar_header_prev"/>').appendTo(rightTd).click(function(){
            calendar.prev();
            $("#calendar_view").hide();
        });
        var centerTd = $('<div class="lui_calendar_header_center"/>').appendTo(rightTd);
        $('<div class="lui_calendar_header_title"/>').appendTo(centerTd);
        $('<div cal-opt="next"/>').append('<span class="lui_calendar_header_next"/>').appendTo(rightTd).click(function(){
            calendar.next();
            $("#calendar_view").hide();
        });

        return headObj;
    }

    function displayChange(info){
        var mode = info.mode;
        var view = info.view;
        frame.find(".lui_calendar_header_tab li[cal-mode]").hide();
        frame.find(".lui_calendar_header_tab li[cal-mode='" + mode + "']").show();
        if(view!=null){
            $(".lui_calendar_header .lui_calendar_header_center .lui_calendar_header_title").text(view.title);
            $(".lui_calendar_header_tab li[cal-opt]").removeClass("lui_calendar_header_button_on");
            $(".lui_calendar_header_tab li[cal-opt='"+view.name+"']").addClass("lui_calendar_header_button_on");
            var today = new Date().getTime();
            if(view.start.getTime() <= today && today <= view.end.getTime() ){
                $(".lui_calendar_header_tab div[cal-opt='today']").addClass("lui_calendar_header_button_on");
            }else{
                $(".lui_calendar_header_tab div[cal-opt='today']").removeClass("lui_calendar_header_button_on");
            }
            if(view.name === "basicWeek"){
                $(".lui_calendar_header_tab div[cal-opt='today']").html(modelingLang['modelingCalendar.this.week']);
            }else{
                $(".lui_calendar_header_tab div[cal-opt='today']").html(lang['ui.calendar.today']);
            }
        }
    }

    var frame = createFrame();

    frame.append(createHeader("modelingCalendar"));
    frame.append(createContent("modelingCalendar"));


    calendar.on("viewOrModeChange", displayChange);
    calendar.onErase(function() {
        calendar.off('viewOrModeChange', displayChange);
    });
    done(frame);
    //显示周视图
    if(calendar.modeSetting["modelingCalendar"] && calendar.modeSetting["modelingCalendar"].showMode){
        if(calendar.modeSetting["modelingCalendar"].showMode == "0"){
            calendar.changeView('basicWeek');
        }
    }

});