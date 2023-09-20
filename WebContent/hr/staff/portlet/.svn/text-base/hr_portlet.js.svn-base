
 /******************
	    ** 功能 初始化日历
	    ******************/
	function hrPlatformCalendarInit(data,type) {
		console.log("__hrPlatformCalendarInit");
		//左侧调整
		var t_arr = type.split(';');
		$(".lui_hr_platform_calendar_schedule_li").hide();
		for(var i=0;i<t_arr.length;i++){		
			 $("li[data-lui-mark='lui_hr_schedule_" + t_arr[i] + "']").show();
		}
	    //点击日期
	    var calendMap = data.calendMap

	    function changeHrCalendarSchedule(key) {
	        $(".schedule_num").text(0);
	        var scheduleDate = calendMap[key];
	
	        if (typeof scheduleDate != 'undefined') {
	            for (var k in scheduleDate) {
	                var keyli = $("li[data-lui-mark='lui_hr_schedule_" + k + "']");
	                keyli.find(".schedule_num").text(scheduleDate[k])
	                keyli.find(".schedule_num").attr("data-lui-mark",key);
	            }
	        }

	    }

	    //初始化日历
	    //宽度调整
	 	var $hrPlatComList =  $(".lui_hr_platform_component");
		$.each($hrPlatComList,function (idx,ele) {
			var $ele = $(ele);
			var com_width = $ele.width();
			var $wap = $ele.find(".lui_hr_platform_calendar_wrap");
			var $schedule = $ele.find(".lui_hr_platform_calendar_schedule");
			$wap.css("width", com_width - 200 + "px");
			$schedule.css("margin-left", com_width - 200 + "px");
		});
	    //标题
	    var dateMmonth = data.calendFormat.month;
	    dateMmonth = dateMmonth>9?dateMmonth:('0'+dateMmonth);
	    var dateTxt = data.calendFormat.year +"-"+dateMmonth ;
	   
	    $(".lui_hr_platform_calendar_date_txt").text(dateTxt);
	    $(".lui_hr_platform_calendar_date_txt").attr("data-lui-mark",dateTxt);
	    //日历构建
	    var start = data.calendFormat.week;

	    var content = $(".lui_hr_platform_calendar_table_content");
	    content.empty();
	    for (var i = 0; i < start; i++) {

		    var emptyLi = $("<li class='lui_hr_platform_calendar_table_empty'/>")
	        content.append(emptyLi);
	    }
	    var dateIdx = data.dateIdx;
	    for (var i in dateIdx) {
	        var txt = parseInt(i) + 1;
	        var dayLi = $("<li class='lui_hr_platform_calendar_table_day'/>");
	        if (i == data.calendFormat.day-1) {
	            changeHrCalendarSchedule(dateIdx[i].date)
	            dayLi.addClass("lui_hr_platform_calendar_table_day_select");
	        }
	        var textSpan = $("<span class='lui_hr_platform_calendar_table_day_txt'/>");
	        textSpan.attr("data-lui-mark", dateIdx[i].date);
	        textSpan.text(parseInt(i) + 1);
	        dayLi.append(textSpan)

	        if (dateIdx[i].isToDo) {
	            var pinter = "<span class='lui_hr_platform_calendar_table_day_pointer'/>";
	            dayLi.append(pinter);
	        }
	        content.append(dayLi);
	    }
	    content.append("<div style='clear:both;'></div>");

	    $(".lui_hr_platform_calendar_table_day").click(function () {
	        $(".lui_hr_platform_calendar_table_day").removeClass("lui_hr_platform_calendar_table_day_select");
	        $(this).addClass("lui_hr_platform_calendar_table_day_select");
	        var key = $(this).find(".lui_hr_platform_calendar_table_day_txt").attr("data-lui-mark");
	        changeHrCalendarSchedule(key);
	    })
	}
seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic', 'lui/dialog_common'], function ($, dialog, topic, dialogCommon) {
	
	window.hrPlatformCalendarInit =hrPlatformCalendarInit;
	 /******************
	    ** 功能 弹窗
	    ******************/
	function onclickSchedule(type,auth){
		if(!auth){
			return ;
		}
		var sche = $("li[data-lui-mark='lui_hr_schedule_"+type+"']");
	    var mark = sche.attr("data-lui-mark");
	    var num =sche.find(".schedule_num").text();
	    var date=sche.find(".schedule_num").attr("data-lui-mark");
	    if(parseInt(num)>0){
	    	//离职
		    if( mark == "lui_hr_schedule_leave"){
		    	    dialog.iframe('/hr/staff/portlet/dlg/leave_dlg.jsp'+'#cri.q=fdLeaveTime:'+date+';fdLeaveTime:'+date,
			            "离职" , function (value) {     }, { width: 800, height: 600 }
		    	    );
		    	}
	    	//转正
		    if( mark == "lui_hr_schedule_positive"){
		    	    dialog.iframe('/hr/staff/portlet/dlg/positive_dlg.jsp'+'#cri.q=fdPositiveTime:'+date+';fdPositiveTime:'+date,
			            "转正" , function (value) {     }, { width: 800, height: 600 }
		    	    );
		    	}
		  //生日
		    if( mark == "lui_hr_schedule_birthday"){
		    	var d = new Date(date);
		    	var month = d.getMonth()+1;
		    	month = month<9?'0'+month:month;
		    	var day  = d.getDate();
		    	day = day<9?'0'+day:day;
		    	var bir = month+"-"+day;
		    	    dialog.iframe('/hr/staff/portlet/dlg/birth_dlg.jsp'+'#cri.q=fdBirthdayOfYear:'+bir+';fdBirthdayOfYear:'+bir,
			            "生日" , function (value) {    }, { width: 800, height: 600 }
		    	    );
		    	}
		  //合同
		    if( mark == "lui_hr_schedule_contract"){
		    	    dialog.iframe('/hr/staff/portlet/dlg/contract_dlg.jsp'+'#cri.q=fdEndDate:'+date+';fdEndDate:'+date,
			            "合同" , function (value) {    }, { width: 800, height: 600 }
		    	    );
		    	}
		   //周年   year -1 + month + date
		    if( mark == "lui_hr_schedule_annual"){
		    	var d = new Date(date);
		    	var year = d.getFullYear()-1;
		    	var month = d.getMonth()+1;
		    	month = month<9?'0'+month:month;
		    	var day  = d.getDate();
		    	day = day<9?'0'+day:day;
		    	var t = year+"-"+month+"-"+day
		    	dialog.iframe('/hr/staff/portlet/dlg/annual_dlg.jsp?searchDate='+t,
		            "周年" , function (value) {   isDlg=false;   }, { width: 800, height: 600 }
	    	    );
	    	}
		  //入职
		    if( mark == "lui_hr_schedule_entry"){
	    	    dialog.iframe('/hr/staff/portlet/dlg/entry_dlg.jsp'+'#cri.q=fdPlanEntryTime:'+date+';fdPlanEntryTime:'+date,
		            "入职" , function (value) {   isDlg=false;   }, { width: 800, height: 600 }
	    	    );
	    	}
		  //调岗
		    if( mark == "lui_hr_schedule_transfer"){
	    	    dialog.iframe('/hr/staff/portlet/dlg/transfer_dlg.jsp'+'#cri.q=fdRatifyDate:'+date+';fdRatifyDate:'+date,
		            "调岗" , function (value) {   isDlg=false;   }, { width: 800, height: 600 }
	    	    );
	    	}
	    }

	   
	}
	window.onclickSchedule =onclickSchedule;
	 /******************
	    ** 功能 更新页面
	    ******************/
	function calendarChange(idx,type){
		var  dateTxt= $(".lui_hr_platform_calendar_date_txt").attr("data-lui-mark")+"-00";
		reHrPlatformCalendarInit(idx,dateTxt,type)
	}
	 /******************
	    ** 功能 重置
	    ******************/
	function reHrPlatformCalendarInit(idx,date,type){
		 window._load = dialog.loading();
		var url = Com_Parameter.ContextPath + "hr/staff/portlet.do?method=calendAjax&idx="+idx+"&date="+date+"&type="+type;
        $.ajax({
            url: url,
            //data: {"idx":idx,"date":date },
            dataType: 'json',
            type: 'GET',
            success: function (data) {
            	hrPlatformCalendarInit(data,type);
            	 window._load.hide();
            },
            error: function (req) {
            	 window._load.hide();
            	
            }
        });
	}
	window.calendarChange =calendarChange;
});


