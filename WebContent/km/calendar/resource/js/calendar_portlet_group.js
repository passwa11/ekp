/**
 * 群组日程
 */
define( function(require, exports, module) {
	
	var $ = require('lui/jquery'),
		base = require('lui/base'),
		layout = require('lui/view/layout'),
		topic = require('lui/topic'),
		util = require('lui/util/env'),
		dialog = require('lui/dialog'),
		strUtil = require('lui/util/str'),
		calendarDateUtil = require('km/calendar/resource/js/dateUtil');
	
	var lang = require('lang!'),
		timelang = require('lang!sys-time'),
		clang = require('lang!km-calendar');

	var daysInMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
	
	
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
	 * 根据日期获取年份中第几周
	 */
	function getYearWeek(day1){
	    var year = day1.getFullYear();
	    var date1 = day1;
	    var date2 = new Date(year, 0, 1);
	    var d = Math.round((date1.valueOf() - date2.valueOf()) / 86400000);
	    return Math.ceil((d + ((date2.getDay() + 1) - 1)) / 7);
	}
	function clearTime(d) {
		d.setHours(0);
		d.setMinutes(0);
		d.setSeconds(0); 
		d.setMilliseconds(0);
		return d;
	}
	function cloneDate(d){
		var date = new Date(d.getTime());
		return clearTime(date);
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
	

	
	var CalendarPortlet = base.Container.extend({

		initProps : function($super,cfg){
			$super(cfg);
			this.id = this.config.id;
			this.rowsize = this.config.rowsize || 6;
			this.groupId = this.config.groupId;
			this.groupSize = this.config.groupSize;
			this.firstDayInWeek = isNaN(Com_Parameter['FirstDayInWeek']) ? 1 : Com_Parameter['FirstDayInWeek'];
			this.currentDate = new Date();
			//当前日期与周开始日期间相隔天数
			this.setCurrentWeek();
			this.startup();
		},

		startup : function(){
			if (!this.layout) {
				this.layout = new layout.Template({
					src : require.resolve('../layout/calendar_portlet_group_layout.jsp#'),
					parent : this
				});
				this.layout.startup();
				this.children.push(this.layout);
			}
			if(!window.createGroupCalendarPortlet){
				var self = this;
				window.createGroupCalendarPortlet = function(url,method){
					//创建群组日程
					debugger;
					self.openCalendarDialog(url,method);
				};
			}
			if(!window.openGroupCalendarPortlet){
				var self = this;
				window.openGroupCalendarPortlet = function(url,method){
					window.location.href=Com_Parameter.ContextPath+"/km/calendar";
				};
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
		 * 绑定事件
		 */
		_bindEvent : function(){
			var self = this;
			var todayDom = this.element.find('[data-lui-mark="calendar_today"]'),
				refreshDom = this.element.find('[data-lui-mark="calendar_refresh"]'),
				preDom = this.element.find('[data-lui-mark="calendar_pre"]'),
				nextDom = this.element.find('[data-lui-mark="calendar_next"]');
			//回到今天事件
			todayDom.on('click',function(){
				self.setToday(new Date());
			});
			//刷新事件
			refreshDom.on('click',function(){
				self.refresh();
			});
			//翻周事件
			preDom.on('click',function(){
				self.start.setDate(self.start.getDate()-7);
				self.end.setDate(self.end.getDate()-7);
				self.calendarDataRender();
			});
			
			nextDom.on('click',function(){
				self.start.setDate(self.start.getDate()+7);
				self.end.setDate(self.end.getDate()+7);
				self.calendarDataRender();
			});
		},
		
		setCurrentWeek:function(){
			var dates = (this.currentDate.getDay()- this.firstDayInWeek + 7) % 7;
			var today = new Date(this.currentDate.getTime());
			this.start = clearTime(today);
			this.start.setDate(today.getDate()-dates);
			this.end = new Date(this.start.getTime());
			this.end.setDate(this.end.getDate()+7);
		},
		
		setToday : function(date){
			this.setCurrentWeek();
			this.calendarDataRender();
		},
		
		/**
		 * 刷新
		 */
		refresh : function(){
			this.calendarDataRender();
		},
		
		calendarDataRender : function(){
			this.weekRender();//填充星期栏
			this.calendarRender();//填充日期
			this.getUserCalenarData();
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
				weekName = clang['date.shortWeekDayNames'].replace(/\"/g,'').split(','),
				weekDom = this.element.find('[data-lui-mark="calendar_weekName"]');
			var monthDom = this.element.find('[data-lui-mark="calendar_month"]');
			for(var i  = 0; i < weekDom.length;i++){
				var text = weekName[(i + firstDayInWeek) % 7];
				weekDom.eq(i).text(text);
			}
			
			var yearMonthDom = this.element.find('[data-lui-mark="calendar_month"]'),
			yearWeekDom = this.element.find('[data-lui-mark="calendar_yearweek"]'),
			currentDate = this.start;
			var year = currentDate.getFullYear(),
				month = currentDate.getMonth() + 1 >= 10 ? currentDate.getMonth() + 1 : '0' + (currentDate.getMonth() + 1),
				date = currentDate.getDate() >= 10 ? currentDate.getDate() : '0' + currentDate.getDate();
			if(Com_Parameter.Date_format == 'dd/MM/yyyy')
				yearMonthDom.text( month + '-' + year );
			else
				yearMonthDom.text( year + '-' + month );
			
			//第几周
			var week = getYearWeek(this.start);
			var txt = clang["km.calendar.portlet.group.weekNum"];
			var value = "<em>" + week + "</em>";
			yearWeekDom.html(txt.replace("%week%",value));
		},
		
		/**
		 * 填充日期
		 */
		calendarRender : function(){
			var firstDayInWeek = this.firstDayInWeek,
				today = new Date(),
				datesDom = this.element.find('[data-lui-mark="calendar_date"]');
			var todayDom = this.element.find('.lui_group_header .today').removeClass('today');
			var startDate = new Date(this.start.getTime());
			for(var i = 0;i < datesDom.length;i++){
				dateDom = $(datesDom[i]);
				if(isSameDay(startDate,this.currentDate)){
					dateDom.parent('th').addClass('today');
				}
				dateDom.text(startDate.getDate());
				startDate.setDate(startDate.getDate()+1);
			}
		},
		
		showCurrentWeekCalendar:function(){
			var currentDate = this.currentDate;
			var height = this.element.find('.lui_pcd_shadeBox')[0].style.height;
			height = height.replace('px','') || 70;
			var currentTrNode = this.element.find('.lui_calendar_portlet_month_tr .today');
			var trNodes = this.element.find('.lui_calendar_portlet_month_tr');
			if(!isSameYearMonth(currentDate,new Date())){
				currentTrNode = this.element.find('.lui_calendar_portlet_month_tr.first td');
			}
			if(height<100){
				trNodes.hide();
				currentTrNode.parent('.lui_calendar_portlet_month_tr').show();
			}else{
				trNodes.show();
			}
		},
		
		userCalendarDataRender : function(){
			var currentDate = this.currentDate,
				currentStr = util.fn.formatDate(currentDate,seajs.data.env.pattern.date),
				nodata = false;
			if(this.datas == ""||this.datas == undefined){
				this.element.find('.lui_group_nogroup').show();
				return;
			}
			this.hideNoDataTip();
			this.personJson=this.datas["persons"];//人员数据
			this.groupDatas = this.datas["calendars"];//日程数据
			var tableObj=this.element.find(".lui_calendar_portlet_group > table");
			if(this.groupDatas && this.personJson.length > 0 && this.groupSize>0){
				for(var i in this.personJson){
					var person=this.personJson[i];
					var personId=person.fdId;
					
					var trObj=this._getTr(this.start, this.end, person, this.groupDatas[personId]);
					this._insertTr(tableObj,trObj,false);
				}
			}else{
				this.element.find('.lui_group_nodata').show();
			}
		},
		_getTr:function(start,end,person,calendars){
			var personId=person.fdId;
			var canRead=person.canRead;
			var canEditor=person.canEditor;
			var canModifier=person.canModifier;
			var tmpTR  = $('<tr class="lui_group_person_item" can-read="'+canRead+'" can-editor="'+canEditor+'" can-modifier="'+canModifier+'" person="'+ personId +'"/>');
			
			//置为初始值
			this.Maxtop=0;
			this.tops=[1,1,1,1,1,1,1];
			
			//绘制人员列
			var personTd = $('<td class="lui_calendar_group_person"/>');
			this.personRender(person,personTd);
			personTd.appendTo(tmpTR);
			
			//绘制日程列
			for(var i=0;i<7;i++){
				var cellStartDate = new Date(start.getTime());
				cellStartDate.setDate(start.getDate() + i);
				var cellEndDate = new Date(cellStartDate.getTime());
				cellEndDate.setDate(cellEndDate.getDate() + 1);
				var dateTD  = $('<td class="lui_calendar_group_date" colIndex="'+i+'" date="'+ calendarDateUtil.formatDate(cellStartDate,"yyyy-MM-dd") +'"></td>');
				dateTD.appendTo(tmpTR);//绘制日期列
				var isHasData = false;
				if(calendars.length>0){
					for(var j = 0; j<calendars.length; j++){
						var tmpFlag = this._chkInstect(cellStartDate,cellEndDate,calendars[j],dateTD);
						if(tmpFlag == true && !isHasData){
							isHasData = true;
						}
					}
				}
			}
			var trHeight=tmpTR.height()>this.Maxtop?tmpTR.height():this.Maxtop;
			tmpTR.height(trHeight);
			return tmpTR;
		},
		_insertTr:function(tableObj,trObj,inEnd){
			var personId=$(trObj).attr("person");
			var prev="";
			var hasInsert=false;
			if(!inEnd){
				for(var i in this.totalPersonJson){
					var person=this.totalPersonJson[i];
					if($(".lui_calendar_group_person_tr[person="+person.fdId+"]").length>0){
						prev=person.fdId;
					}	
					if(person.fdId==personId&&prev!=""){
						$(".lui_calendar_group_person_tr[person="+prev+"]").after(trObj);
						hasInsert=true;
						break;
					}
				}
			}
			if(hasInsert==false || inEnd ){
				trObj.appendTo(tableObj);
			}
		},
		personRender : function(person,cellObj){
			var divObj = $('<span />').append(person.fdName);
			cellObj.addClass('lui_group_username');
			divObj.appendTo(cellObj);
		},
		_chkInstect : function(start, end, evt , dataTd){
			var allDay = evt.allDay;
			var dataStart=evt.start;
			if(typeof(dataStart)=="string")
				dataStart = Com_GetDate(evt.start);
			evt.start = dataStart;
			var dataStartInt = dataStart.getTime();
			//日程开始时间位于这一天
			if(dataStartInt>=start.getTime() && dataStartInt< end.getTime()){
				//没有结束时间,TD=1
				if(evt.end==null || evt.end==''){
					this.dataRender(evt,dataTd,1);
					return true;
				}else{
					//有结束时间,TD=日程结束时间-日程开始时间
					var dataEnd = evt.end;
					if(typeof(dataEnd)=="string")
						dataEnd = Com_GetDate(evt.end);
					evt.end = dataEnd;
					//var dataEndInt = dataEnd.getTime();
					//var selectStart=dataStartInt>this.start.getTime()?dataStart:this.start;
					//var selectEnd=dataEndInt<this.end.getTime()?dataEnd:this.end;
					this.dataRender(evt,dataTd,this.getTDlength(dataStart, dataEnd, allDay));
					return true;
				}
			//日程开始时间不处于日历中,TD=日程结束时间-日历开始时间
			}else if(dataStartInt<this.start.getTime()&&evt.end!=null&&evt.end!=''&&start.getTime()==this.start.getTime()){
				var dataEnd = evt.end;
				if(typeof(dataEnd)=="string")
					dataEnd = Com_GetDate(evt.end);
				evt.end = dataEnd;
				//var dataEndInt = dataEnd.getTime();
				//var selectEnd=dataEndInt<this.end.getTime()?dataEnd:this.end;
				this.dataRender(evt,dataTd,this.getTDlength(this.start, dataEnd, allDay));
				return true;
			}
			return false;
		},
		getTDlength:function(startDate,endDate,isAllDay){
			var tstart=cloneDate(this.start);//日历当前显示的第一天
			var tend=cloneDate(this.end);//日历当前显示的最后的一天
			//tend.setDate(tend.getDate());//日历当前显示的最后的一天
			var tmpStartDate=cloneDate(startDate);
			var tmpEndDate=cloneDate(endDate);
			//不允许结束时间小于开始时间(强制将结束时间设为开始时间)
			if(tmpStartDate.getTime()>=tmpEndDate.getTime()){
				tmpEndDate=tmpStartDate;
			}
			//(日历开始时间和日程开始时间取较大者)
			if(tstart.getTime()>tmpStartDate.getTime()){
				tmpStartDate=tstart;
			}
			//(日历结束时间和日程结束时间取较小者)
			if(tend.getTime()<tmpEndDate.getTime()){
				tmpEndDate=tend;
			}
			var days=parseInt(Math.abs((tmpStartDate.getTime() - tmpEndDate.getTime()))/(1000*60*60*24));
			if(isAllDay){
				days+=1;  
			}else{
				if(endDate.getTime()-tmpEndDate.getTime()>0){
					days+=1;
				}
			}
			if(days<1){
				days=1;
			}
			return days;
		},
		/**********************************************************
		 * 功能:绘制数据
		 * 参数：
		 * 			id			标示	 		
		 * 			title		标题（必填）
		 * 			start		开始时间（必填）
		 * 			end			结束时间
		 *			allDay		是否整天（true/false）
		 * 			className	日历事务显示样式
		 *			backgroundColor背景样色
		 *			borderColor	边框颜色
		 *			textColor	文本颜色	
		 ***********************************************************/
		dataRender:function(data,cellObj,days){
			var self = this;
			var title="";
			if(data.allDay){
				title=lang['kmCalendarMain.allDay'];
			}else{
				title=calendarDateUtil.formatDate(data['start'],"HH:mm");
			}
			title+=" "+strUtil.encodeHTML(data.title);

			var divObj = $('<a href="javascript:void(0)" title="'+strUtil.encodeHTML(data.title)+'"></a>').addClass('task_chartBar');
			var txt = $('<span />').text(strUtil.encodeHTML(data.title));
			divObj.append(txt);
			var href = '/km/calendar/km_calendar_main/kmCalendarMain.do?method=edit&fdId=' + data.id;
			if(!data.isGroup){
				divObj.addClass('lui_group_personIcon');
			}else{
				href = '/km/calendar/km_calendar_main/kmCalendarMain.do?method=editGroupEvent&mainGroupId='+data.mainGroupId+'&fdId=' + data.id;
			}
			(function(url){
				divObj.click(function(){
					self.openCalendarDialog(url,'update');
				});
			})(href);
			
			divObj.attr("days",days);
			divObj.css("width",days*100+"%");//日程长度
			
			//定位（开始）
			var cellPerHeight=20;
			divObj.css("top",this.tops[cellObj.attr("colIndex")]);
			divObj.css("left",1);//日程位置
			for(var k=0;k<days;k++){
				if(k==0){
					this.tops[k+parseInt(cellObj.attr("colIndex"))]+=cellPerHeight+1;
				}else{
					this.tops[k+parseInt(cellObj.attr("colIndex"))]=this.tops[parseInt(cellObj.attr("colIndex"))];
				}
				if(this.tops[k+parseInt(cellObj.attr("colIndex"))]>this.Maxtop)
					this.Maxtop=this.tops[k+parseInt(cellObj.attr("colIndex"))];
			}
			//定位（结束）
			
			divObj.appendTo(cellObj);
		},
		openCalendarDialog : function(url,method){
			var self = this,
				methodName = method == 'add' ? clang['kmCalendarMain.opt.create'] : clang['kmCalendarMain.opt.edit'],
			 	header = $('<ul class="clrfix schedule_share"/>')
			 			.append($('<li/>').attr('id','event_base_label').addClass('current').text(methodName))
			 			.append($('<li>|</li>'))
			 			.append($('<li/>').attr('id','event_auth_label').text(clang['kmCalendar.label.table.share']));
			dialog.iframe(url,header,function(rtn){
				self.refresh();
			},{width:700,height:550});
		},
		hideNoDataTip :function(){
			this.element.find(".lui_group_person_item").remove();
			this.element.find('.lui_group_nodata').hide();
		},
		
		/**
		 * 获取日程信息
		 */
		getUserCalenarData : function(){
			var startDate = util.fn.formatDate(this.start,seajs.data.env.pattern.date),
				endDate = util.fn.formatDate(this.end,seajs.data.env.pattern.date),
				url = Com_Parameter.ContextPath + 'km/calendar/km_calendar_main/kmCalendarMain.do?method=listPersonGroupCalendar&personGroupId=' + this.groupId + '&operType=week&fdStart='+ startDate +'&fdEnd=' + endDate;
			var promise = $.ajax({
				url : url,
				dataType : 'json',
				context : this
			}).then(function(datas){
				this.datas = datas;
				this.userCalendarDataRender();
			}).fail(function(){
			    this.userCalendarDataRender();
			});
			return promise;
		}

	});
	
	exports.CalendarPortlet = CalendarPortlet;

});