/**
 * 日程月视图
 */
define( function(require, exports, module) {
	
	var $ = require('lui/jquery'),
		base = require('lui/base'),
		layout = require('lui/view/layout'),
		topic = require('lui/topic'),
		util = require('lui/util/env'),
		dialog = require('lui/dialog');
	var calendar = require('lui/calendar');
	var lang = require('lang!'),
		timelang = require('lang!sys-time'),
		clang = require('lang!km-calendar');

	var daysInMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
	
	/**
	 * 是否闰年
	 * @param  {[Object]} dateObject [指定日期]
	 */
	function isLeapYear(dateObject){
		var year = dateObject.getFullYear();
		return !(year%400) || (!(year%4) && !!(year%100));
	}
	
	/**
	 * 是否同一天
	 */
	function isSameDay(day1,day2){
		return day1.getFullYear() == day2.getFullYear() 
				&& day1.getMonth() == day2.getMonth() 
				&& day1.getDate() == day2.getDate();
	}
	function isSameYearMonth(day1,day2){
		return day1.getFullYear() == day2.getFullYear() 
				&& day1.getMonth() == day2.getMonth();
	}
	
	/**
	 * 获取指定日期所在月的天数
	 * @param  {[Object]} dateObject [指定日期]
	 */
	function getDaysInMonth(dateObject){
		var month = dateObject.getMonth(),
			days = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
		if(month == 1 && isLeapYear(dateObject)){ 
			return 29; 
		}
		return days[month];
	}
	
	function getHPDay(){
		var url =  Com_Parameter.ContextPath+"sys/time/sys_time_area/sysTimeArea.do?method=getHPDay";
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
	}
	
	var CalendarPortlet = base.Container.extend({
		initProps : function($super,cfg){
			$super(cfg);
			this.id = this.config.id;
			this.rowsize = this.config.rowsize || 6;
			this.firstDayInWeek = isNaN(Com_Parameter['FirstDayInWeek']) ? 1 : Com_Parameter['FirstDayInWeek'];
			this.currentDate = new Date();
			this.startup();
		},

		startup : function(){
			if (!this.layout) {
				this.layout = new layout.Template({
					src : require.resolve('../layout/calendar_portlet_white_layout.jsp#'),
					parent : this
				});
				this.layout.startup();
				this.children.push(this.layout);
			}
		},
		
		doLayout : function(obj){
			var self = this;
			this.element.empty();
			this.element.append($(obj));
			this._bindEvent();
			this.calendarDataRender();
		},
		
		/**
		 * 设置日历日期
		 */
		setDate : function(currentDate){
			var preCurrentDate = this.currentDate; 
			this.currentDate = currentDate;
			//不是同一天,更新头部
			if(!isSameDay(preCurrentDate,currentDate)){
				var datesDom = this.element.find('[data-lui-mark="calendar_date"]'),
					currentStr = util.fn.formatDate(currentDate,seajs.data.env.pattern.date),
					dateDom = datesDom.filter('[data-lui-date="' + currentStr +'"]');
				datesDom.removeClass('current');
				if(!isSameDay(new Date(),currentDate)){
					dateDom.addClass('current');
				}
				this.headerRender();
			}
			//不是同一个月,更新body
			if(preCurrentDate.getMonth() != currentDate.getMonth()
					|| preCurrentDate.getFullYear() != currentDate.getFullYear()){
				this.calendarRender();
				this.getUserCalenarData().then(function(){
					this.userCalendarDataRender();
					this.isHasCalendarData();
				});
			}else{
				this.userCalendarDataRender();
			}
		},
		
		/**
		 * 刷新
		 */
		refresh : function(){
			this.calendarRender();
			this.getUserCalenarData().then(function(){
				this.userCalendarDataRender();
				this.isHasCalendarData();
			});
		},
		
		/**
		 * 打开日程对话框(简单页面)
		 */
		openEventSimple:function(evt,date){
			topic.publish("kmCalendar_openSimpleDialog",{evt:evt,date:date});
		},
		
		/**
		 * 打开日程页面
		 */
		openCalendarDialog : function(url,method,type){
			var self = this,
				methodName = method == 'add' ? clang['kmCalendarMain.opt.create'] : clang['kmCalendarMain.opt.edit'],
			 	header = $('<ul class="clrfix schedule_share"/>')
			 			.append($('<li/>').attr('id','event_base_label').addClass('current').text(methodName))
			 			.append($('<li>|</li>'))
			 			.append($('<li/>').attr('id','event_auth_label').text(clang['kmCalendar.label.table.share']));
			if(type=='note'){
				methodName = method == 'add' ? clang['kmCalendarMain.opt.note.create'] : clang['kmCalendarMain.opt.note.edit'];
				header = '<span class="note_lable_select" id="note_base_label" >'+ methodName +'</span>';
			}
			var myDateFormat = Com_Parameter.Date_format;
			// 英文下mmddyyyy传入后台，convertStringToDate无法正常解析格式-->mmddhhhh #170547
			if(myDateFormat == "MM/dd/yyyy"){
				myDateFormat = "yyyy/MM/dd";
			}
			var startDate = util.fn.formatDate(self.currentDate,myDateFormat);
			url = url + '&startTime='+ startDate +'&endTime=' + startDate;
			dialog.iframe(url,header,function(rtn){
				self.refresh();
			},{width:700,height:550});
		},
		
		/**
		 * 绑定事件
		 */
		_bindEvent : function(){
			var self = this,
				todayDom = this.element.find('[data-lui-mark="calendar_today"]'),
				refreshDom = this.element.find('[data-lui-mark="calendar_refresh"]'),
				datesDom = this.element.find('[data-lui-mark="calendar_date"]'),
				toggleDom = this.element.find('.lui_pcd_dropdowm_button i');
			//回到今天事件
			todayDom.on('click',function(){
				self.setDate(new Date());
			});
			//刷新事件
			refreshDom.on('click',function(){
				self.refresh();
			});
			//日期选择事件
			datesDom.on('click',function(){
				var dateStr = $(this).attr('data-lui-date'),
					dateObj = util.fn.parseDate(dateStr,seajs.data.env.pattern.date);
				self.setDate(dateObj);
			});
			//双击新建窗口
			datesDom.on('dblclick',function(evt){
				var dateStr = $(this).attr('data-lui-date'),
				dateObj = util.fn.parseDate(dateStr,seajs.data.env.pattern.date);
				self.openEventSimple(evt,dateObj);
			});
			
			var preDom = this.element.find('[data-lui-mark="calendar_pre"]'),
				nextDom = this.element.find('[data-lui-mark="calendar_next"]');
			//翻月事件
			preDom.on('click',function(){
				//获取上个月日期
				function getPreMonthDate(date){
					var preYear = date.getMonth() - 1 < 0 ? date.getFullYear()-1 : date.getFullYear();
					var preMonth = date.getMonth() - 1 < 0 ? 11 : date.getMonth() - 1,
						preDate = date.getDate() > daysInMonth[preMonth] ? daysInMonth[preMonth] :  date.getDate(),
						preMonthDate = new Date( +date );
					preMonthDate.setDate(preDate);	
					preMonthDate.setMonth(preMonth);
					preMonthDate.setFullYear(preYear);
					return preMonthDate;
				}
				var preMonthDate = getPreMonthDate(self.currentDate);
				self.setDate(preMonthDate);
			});
			
			nextDom.on('click',function(){
				//获取下个月日期
				function getNextMonthDate(date){
					//alert(date.getFullYear());
					var nextYear = date.getMonth() + 1 > 11 ? date.getFullYear()+1 : date.getFullYear();
					var nextMonth = date.getMonth() + 1 > 11 ? 0 : date.getMonth() + 1,
						nextDate = date.getDate() > daysInMonth[nextMonth] ? daysInMonth[nextMonth] :  date.getDate(),
						nextMonthDate = new Date( +date );
					nextMonthDate.setDate(nextDate);	
					nextMonthDate.setMonth(nextMonth);
					nextMonthDate.setFullYear(nextYear);
					return nextMonthDate;	
				}
				var nextMonthDate = getNextMonthDate(self.currentDate);
				self.setDate(nextMonthDate);
			});
			toggleDom.click(function (e) {
				var shadeBoxDom = self.element.find('.lui_pcd_shadeBox');
		        if(shadeBoxDom.height() < 100) {
		          shadeBoxDom.height(273);
		          toggleDom.css({'transform':'rotate(180deg)'});
		        }else {
		          shadeBoxDom.height(70);
		          toggleDom.css({'transform':'rotate(0deg)'}); 
		        }
		        self.element.find('.lui_smd_table--tr').show();
		        self.showCurrentWeekCalendar();
		     });
		},
		
		calendarDataRender : function(){
			this.headerRender();//填充头部
			this.weekRender();//填充星期栏
			this.calendarRender();//填充日期
			this.getUserCalenarData().then(function(){
				this.userCalendarDataRender();
				this.isHasCalendarData();
			});
		},
		
		headerRender : function(){
			var dateTextDom = this.element.find('[data-lui-mark="calendar_datetxt"]'),
				yearMonthDom = this.element.find('[data-lui-mark="calendar_yearmonth"]'),
				currentDate = this.currentDate;
			var year = currentDate.getFullYear(),
				month = currentDate.getMonth() + 1 >= 10 ? currentDate.getMonth() + 1 : '0' + (currentDate.getMonth() + 1),
				date = currentDate.getDate() >= 10 ? currentDate.getDate() : '0' + currentDate.getDate();		
				dateTextDom.text(date);
			if(Com_Parameter.Date_format == 'dd/MM/yyyy')
				yearMonthDom.text( month + '-' + year );
			else
				yearMonthDom.text( year + '-' + month );
		},
		
		/**
		 * 填充星期栏
		 */
		weekRender : function(){
			var firstDayInWeek = this.firstDayInWeek,
				weekName = clang['date.shortWeekDayName'].replace(/\"/g,'').split(','),
				weekDom = this.element.find('[data-lui-mark="calendar_weekName"]');
			for(var i  = 0; i < weekDom.length;i++){
				var text = weekName[(i + firstDayInWeek) % 7];
				weekDom.eq(i).text(text);
			}
		},
		
		/**
		 * 填充日期
		 */
		calendarRender : function(){
			var firstDayInWeek = this.firstDayInWeek,
				today = new Date(),
				datesDom = this.element.find('[data-lui-mark="calendar_date"]');

			var	currentMonth = new Date( +this.currentDate );
			currentMonth.setDate(1);
			
			var	previousMonth = new Date( +currentMonth ), 
				nextMonth = new Date( +currentMonth );
			previousMonth.setMonth(previousMonth.getMonth() - 1);
			nextMonth.setMonth(nextMonth.getMonth() + 1);
			
			// 本月第一天星期几
			var firstDay = (currentMonth.getDay()
					- this.firstDayInWeek + 7) % 7;
			// 上个月多少天
			var daysInPreviousMonth = getDaysInMonth(previousMonth);
			// 本月多少天
			var daysInMonth = getDaysInMonth(currentMonth);
			datesDom.removeClass('pre next today current');
			var holidayPach = getHPDay();
			for(var i = 0;i < datesDom.length;i++){
				var number, _date ,dateDom = datesDom.eq(i);
				if (i < firstDay) {//上个月
					number = daysInPreviousMonth - firstDay + i + 1;
					dateDom.addClass('pre');
					_date = new Date( +previousMonth);
				}else if (i >= (firstDay + daysInMonth)) {//下个月
					number = i - firstDay - daysInMonth + 1;
					dateDom.addClass('next');
					_date = new Date( +nextMonth );
				}else {// 当前
					number = i - firstDay + 1;
					_date = new Date( +currentMonth );
				}
				_date.setDate(number);
				if(isSameDay(_date,new Date())){
					dateDom.addClass('today');
				}else if( isSameDay(_date,this.currentDate)){
					dateDom.addClass('current');
				}
				
				//第一天
				if(i == 0){
					this.startDate = _date;
				}
				//最后一天
				if( i == datesDom.length -1 ){
					this.endDate = _date;
				}
				_date.setHours(0);
				_date.setMinutes(0);
				var solarDay = calendar.solayDayWithType(_date);
				var isSolarTerm = calendar.isSolarTerm(_date);//是否农节气
				var solarClass="lui_solar";
				if(isSolarTerm){
					solarClass += " lui_solar_term";
				}else if(solarDay && (solarDay.type=='solarFestival' || solarDay.type=='lunarFestival')){
					solarClass += " lui_solar_holiday";
				}
				var numberHtml = '<div class="lui_date_box"><span class="lui_number">'+number+'</span>';
				var solarHmtl = '';
				// 中文环境下显示农历
				if(Com_Parameter['Lang']!=null && (Com_Parameter['Lang']=='zh-cn' || Com_Parameter['Lang']=='zh-hk')){
					solarHmtl='<div class="'+solarClass+'">' +solarDay.value +'</div></div>';
				}
				dateDom.html(numberHtml+solarHmtl);
				dateDom.attr('data-lui-date',util.fn.formatDate(_date,seajs.data.env.pattern.date));
				if(this.isWeekRest(_date)){
					dateDom.addClass('muiWeekRest');
				}
				var cd = util.fn.formatDate(_date,seajs.data.env.pattern.date);
				if(cd&&holidayPach){//TODO
					for(var k=0;k<holidayPach.length;k++){
						if(cd==holidayPach[k].date){
							var boxNode = dateDom.find('.lui_date_box');
							if("1"==holidayPach[k].type){
								dateDom.attr("style","position:relative");
								boxNode.html(boxNode.html()+"<span class='lui_calendar_work lui_calendar_holiday'>"+timelang['calendar.holiday.cal']+"</span>");								
							}else{
								dateDom.attr("style","position:relative");
								boxNode.html(boxNode.html()+"<span class='lui_calendar_work'>"+timelang['calendar.work.cal']+"</span>");
							}
							
						}
					}
				}
			}
			//日历默认只显示当前周
			this.showCurrentWeekCalendar();
		},
		
		showCurrentWeekCalendar:function(){
			var currentDate = this.currentDate;
			var height = this.element.find('.lui_calendar_white_content')[0].style.height;
			height = height.replace('px','') || 70;
			var currentTrNode = this.element.find('.lui_smd_table--tr .today');
			var trNodes = this.element.find('.lui_smd_table--tr');
			if(!isSameYearMonth(currentDate,new Date())){
				currentTrNode = this.element.find('.lui_smd_table--tr .first td');
			}
			if(height<100){
				trNodes.show();
				currentTrNode.parent('.lui_smd_table--tr').show();
			}else{
				trNodes.show();
			}
		},
		
		userCalendarDataRender : function(){
			var currentDate = this.currentDate,
				currentStr = util.fn.formatDate(currentDate,seajs.data.env.pattern.date),
				footerDom = this.element.find('[data-lui-mark="calendar_footer"]'),
				nodata = false;
			footerDom.html('');
			this.hideNoDataTip();
			if(this.datas && this.datas[currentStr] && this.datas[currentStr].length > 0){
				for(var i = 0;i < this.datas[currentStr].length; i++){
					if(i == this.rowsize) break;//只取相应条数
					var data = this.datas[currentStr][i],
					
						$li = $('<div class="lui_smt_ui--item" />'),
						//$colorLabel = $('<div class="lui_calendar_portlet_item_color_label" />').css('background-color',data.color).appendTo($li),
						$title = $('<div class="lui_smt_ui--div" />'),
						$content = $('<li class="lui_smt_ul--li" />');
					var date = clang['kmCalendarMain.allDay'];
					//判断是否是群组还是个人日程
					var labelName = clang['kmCalendarMain.calendar.header.title'];
					//是群组日程
					if(typeof data.isGroup!='undefined' && data.isGroup==true){
						labelName = clang['kmCalendarMain.group.header.title'];
					}
					labelName = data.labelName ? data.labelName : labelName;
					if(!data.allDay){
						var start = !data.start ? '' : util.fn.formatDate( util.fn.parseDate(data.start,seajs.data.env.pattern.datetime) , seajs.data.env.pattern.time ),
							end = !data.end ? '' : util.fn.formatDate( util.fn.parseDate(data.end,seajs.data.env.pattern.datetime) , seajs.data.env.pattern.time );
						date = start + '-' + end;	
					}
					//日程结束时间
					var endTime = !data.end ? '':data.end;
					if(data.allDay){
						endTime = !data.end ? '':data.end+' 23:59';
					}
					endTime = endTime ? util.fn.parseDate(data.start,seajs.data.env.pattern.datetime):'';
					if (data.allDay) {
						$('<i class="lui_smt_ui__i" />').appendTo($li);
					}else{
						$('<i class="lui_smt_ui__i lui_smt_ui__i--backgroundBlue" />').appendTo($li);
					}
					
					$title.append($('<span />').text(date));
					var linkLabel = $('<a class="label" href="javascript:void(0)" />').text(labelName).css({
						'color':data.color,
						'border-color':data.color
					});
					$title.append(linkLabel);
					var content = $('<span />').text(data.title).css({
						'cursor':'pointer',
					});
					$title.append(linkLabel);
					var content = $('<span />').text(data.title);
					$li.append(content);
					if(data.relationUrl){
						var href =  seajs.data.env.contextPath + data.relationUrl;
						linkLabel.attr('href',href).attr('target','_blank');
					}else{
						var href = '/km/calendar/km_calendar_main/kmCalendarMain.do?method=edit&fdId=' + data.id;
						//群组日程
						if(typeof data.isGroup!='undefined' && data.isGroup==true){
							href = '/km/calendar/km_calendar_main/kmCalendarMain.do?method=editGroupEvent&mainGroupId='+data.mainGroupId+'&fdId=' + data.id;
						}
						var self = this;
						(function(url, type){
							linkLabel.click(function(){
								self.openCalendarDialog(url,'update',type);
							});
						})(href, data.type);
					}
					
					$content.append($title);
					$content.append($li).appendTo(footerDom);
					lastLiNode = $content;
					
				}
			}else{
				this.element.find('.lui_calendar_white_data').hide();
				$ls = $("<div class='lui_calendar_white_nodata'><div class='lui_pce_iconStyle'>"+
					    "<i class='lui_pce_is_emptyImg'></i>"+
					    "<div class='lui_pce_is_title'>"+clang['calendar.portlet.month.nodata']+"</div>"+
					    "<div class='lui_pce_is_text'>"+clang['calendar.portlet.month.nodata.advice']+"</div></div>");
				var nodataNode = this.element.find('.lui_calendar_portlet_white_right');
				$ls.appendTo(nodataNode);
				lastLiNode=$ls;
			}
		},
		
		hideNoDataTip :function(){
			this.element.find('.lui_calendar_white_data').show();
			this.element.find('.lui_calendar_white_nodata').hide();
		},
		
		/**
		 * 获取日程信息
		 */
		getUserCalenarData : function(){
			var startDate = util.fn.formatDate(this.startDate,seajs.data.env.pattern.date),
				endDate = util.fn.formatDate(this.endDate,seajs.data.env.pattern.date),
				url = Com_Parameter.ContextPath + 'km/calendar/km_calendar_main/kmCalendarMain.do?method=data&fdStart='+ startDate +'&fdEnd=' + endDate;
			var promise = $.ajax({
				url : url,
				dataType : 'json',
				context : this
			}).then(function(datas){
				this.datas = this._formatDate(datas);
			});
			return promise;
		},
		
		//是否周未
		isWeekRest:function(date){
			if(date){
				var day = date.getDay();
				if(day==0 || day==6){
					return true;
				}
			}
			return false;
		},
		
		_formatDate : function(datas){
			var __datas = {};
			for(var i = 0;i < datas.length;i++){
				var tmpDate = util.fn.parseDate(datas[i].start,seajs.data.env.pattern.date),
					endDate = util.fn.parseDate(datas[i].end,seajs.data.env.pattern.date);
				if(tmpDate.getTime() > endDate.getTime()){
					continue;
				}
				tmpDate.setHours(0,0,0,0);
				var key = null;
				while( !isSameDay(tmpDate,endDate) ){
					key= util.fn.formatDate(tmpDate,seajs.data.env.pattern.date);
					if(!__datas[key]){
						__datas[key]=[];
					}
					__datas[key].push(datas[i]);
					tmpDate.setDate(tmpDate.getDate() + 1);
				}
				
				key= util.fn.formatDate(tmpDate,seajs.data.env.pattern.date);
				if(!__datas[key]){
					__datas[key]=[];
				}
				__datas[key].push(datas[i]);
				tmpDate.setDate(tmpDate.getDate() + 1);
			}
			return __datas;
		},
		
		isHasCalendarData : function(){
			var datesDom = this.element.find('[data-lui-mark="calendar_date"]');
			for(var i = 0;i < datesDom.length;i++){
				var dateDom = datesDom.eq(i),
					dateStr = dateDom.attr('data-lui-date');
				if(this.datas[dateStr] && this.datas[dateStr].length > 0){
					dateDom.addClass('lui_calendar_dot');
					//右上角点
					var point = $('<div />').addClass('point');
					dateDom.find('.lui_date_box').append(point);
				}
			}
		}

	});
	
	exports.CalendarPortlet = CalendarPortlet;

});