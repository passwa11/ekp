/**
 * 资源日历组件
 */
define(function(require, exports, module) {
	
	var $=require("resource/js/jquery-ui/jquery.ui");
	var Calendar = require("lui/calendar");
	var topic = require("lui/topic");
	var Fixed=require("lui/fixed").Fixed;
	var env=require('lui/util/env');
	var lang = require('lang!sys-ui');
	
	//日历中空白日期选择事件
	var LUI_CALENDAR_SELECT = "calendar.select";
	//日历中日历数据的点击事件
	var LUI_CALENDAR_THING_CLICK = "calendar.thing.click";
	//日历中日历数据的改变事件
	var LUI_CALENDAR_THING_CHANGE = "calendar.thing.change";
	
	//资源抽象视图
	var ResAbstractCalenderMode = Calendar.AbstractCalenderMode.extend({
		initialize : function($super,_config){
			$super(_config);
			this.pageno = 1;
			
			try {
				this.selectedCategories = this.calSetting.vars.selectedCategories || 'all';
			} catch(err) {
				this.selectedCategories = 'all'; //选中的分类,字符串以;分割ID。特殊字符all代表全部选中
			}
			
			this.datas = {};
		},
		draw : function(){
			if(!this.drawed){
				this.setCalendarDate();
				this.render();
			}
			this.drawed = true;
			this.calendarDiv.show();
		},
		setCalendarDate : function(){
			this.start = this.clearTime(new Date());
			this.end = this.clearTime(new Date());
		},
		refreshSchedules: function() {
			this.pageno = 1;
			this.render(); 
			if(this.calSetting.viewDisplay){
				this.calSetting.viewDisplay(this.getView());
			}
		},
		render : function(renderType /* 渲染方式,'append':追加、'refresh':刷新、null:初始化  */){
			var self = this; 
			var evts = this.calSetting.events;
			var startVar = $.fullCalendar.cloneDate(this.start);//开始时间
			var endVar = $.fullCalendar.cloneDate(this.end);
			endVar.setDate(endVar.getDate()+1);//结束时间
			if(evts){
				if ($.isFunction(evts)) {
					evts(startVar, endVar , function(datas){
						self.total = datas.resource.total;//总数
						self.persize = datas.resource.rowsize; //分页数
						self.datas = datas;
						self._render(startVar, endVar, datas, renderType);
						self.afterRender();
					},{'pageno' : this.pageno, 'selectedCategories' : this.selectedCategories});
				}
			}else{
				self._render(startVar,endVar,null);
				self.afterRender();
			}
		},
		_render:function(start ,end ,datas,renderType){
			if(!renderType){
				this.calendarDiv.html('');
				//绘制资源区域
				this.resContent = $('<ul />')
									.addClass('res_calendar_rescontent')
									.append(this.renderCate(datas.category, datas.categoryName))
									.appendTo(this.calendarDiv);
				var rightContent = $('<div/>')
									.addClass('res_calendar_rightContent')
									.appendTo(this.calendarDiv);
				//绘制头部区域
				this.headerContainer = this.renderHeader(start ,end).appendTo(rightContent);
				//绘制日程区域
				this.tableContent =  $('<table />').addClass('res_calendar_content').appendTo(rightContent);
				$('<div/>').addClass('clearfloat').appendTo(rightContent);
				//设置资源区域高度
				//rightContent.css('height', '97%');
			}else {
				if(renderType == 'refresh'){
					this.resetContent();
				}
			}
			var main = datas.main;
			if(main != null){
				for(var id in main){
					//绘制资源内容行
					this.resContent.append(this.renderResRow(main[id]));
					//绘制日程行
					var row = this.renderTableRow(start, end, main[id]);
					row.attr('resId',main[id]['fdId']);
					row.attr('resName',main[id]['name']);
					this.tableContent.append(row);
				}
			}
			if(!renderType){
				//绘制加载区域
				this.renderLoaing();
			}
		},
		resetContent : function(){
			var resrow = this.resContent.children('.res_calendar_rescontent_row');
			if(resrow){
				resrow.remove();
			}
			this.tableContent.html('');
		},
		renderHeader : function(start ,end ,datas){
			var headerContainer = $('<table />').addClass('res_calendar_header'),
				header = $('<tr />').addClass('res_calendar_header_row');
			//绘制头部时间
			this.renderHeaderDate(header);
			headerContainer.append(header);
			return headerContainer;
		},
		renderCate : function(categorys, categoryName){
			var self = this;
			var node = $('<li />')
						.addClass('res_calendar_cate')
						.append($('<div/>').addClass('res_calendar_cate_title').append(categoryName));
			//分类选择框	
			var	dialog = $('<div/>')
						.addClass('res_calendar_cate_dialog')
						.width(this.calendarDiv.width() - 15)
						.appendTo(node);
			//全选
			//由于下面的点击处理逻辑，这里采用点击文字传递事件实现扩大点击区域
			var selectAllInput = $('<input/>').attr('type','checkbox').attr('checked','check').addClass('res_calendar_cate_all_ck');
			var selectAllLabel = $('<span/>').html(lang['ui.listview.selectall']);
			selectAllLabel.click(function() {
				selectAllInput.click();
			});
			
			var	selectAll = $('<div/>')
						.addClass('res_calendar_cate_all')	
						.append(selectAllInput)
						.append(selectAllLabel)
						.appendTo(dialog);
			//绑定全选事件
			selectAll.click(function(){
				var ck = $(this).find('input[type="checkbox"]');
				if(ck.prop('checked')){//全选
					$(self.calendarDiv).find('.res_calendar_cate_one').prop('checked',true);
					self.pageno = 1;
					self.selectedCategories = 'all';
					self.render('refresh');
				}else{//取消全选
					$(self.calendarDiv).find('.res_calendar_cate_one').prop('checked',false);
					self.pageno = 1;
					self.selectedCategories = '';
					self.render('refresh');
				}
			});
			//分类项
			var selectList = $('<ul/>').addClass('res_calendar_cate_list').appendTo(dialog);
			for(var hierarchyId in categorys){
				
				var category = categorys[hierarchyId];
				
				//由于下面的点击处理逻辑，这里采用点击文字传递事件实现扩大点击区域
				var oneInput = $('<input/>').attr('type','checkbox').addClass('res_calendar_cate_one').attr('hierarchyId',hierarchyId);
				var name = $('<span/>').html(category.name);
				(function(_name, _oneInput) {
					_name.click(function() {
						_oneInput.click();
					});
				})(name, oneInput);
				
				var one = $('<li/>')
						.append(oneInput)
						.append(name)
						.appendTo(selectList);
				
				if(self.selectedCategories == 'all') {
					oneInput.attr('checked','check');
				} else if(self.selectedCategories.indexOf(hierarchyId) >= 0){
					oneInput.attr('checked','check');
				} else {
					selectAll.find('input[type="checkbox"]').removeAttr('checked');
				}
				
				one.click(function(){
					var categoryIds = [],
						ck = $(self.calendarDiv).find('.res_calendar_cate_one:checked');
					ck.each(function(index,elem){
						var categoryId = $(elem).attr('hierarchyId');
						categoryIds.push(categoryId);	
					});
					self.pageno = 1;
					self.selectedCategories = categoryIds.join(';');
					self.render('refresh');
					if($(self.calendarDiv).find('.res_calendar_cate_one:checked').length == $(self.calendarDiv).find('.res_calendar_cate_one').length){
						$(self.calendarDiv).find('.res_calendar_cate_all_ck').prop('checked',true);
					}else{
						$(self.calendarDiv).find('.res_calendar_cate_all_ck').prop('checked',false);
					}
				});
			}
			node.mouseout(function(){
				$(this).removeClass("on");
				dialog.hide();
			}).mouseover(function(){
				$(this).addClass("on");
				dialog.show();
			});
			return node;
		},
		renderHeaderDate : function(container){
			//for override
		},
		addHeaderSlide : function(container){
		},
		renderResRow : function(data){
			var row = $('<li/>')
					.addClass('res_calendar_rescontent_row');
			var divObj = $('<div/>').html(data.name);
			divObj.appendTo(row);

			// 新增数据处理接口
			try{
				data._renderResRow && data._renderResRow(row, data);
			} catch(e) {
				console.error(e);
			}
			
			return row;
		},
		renderTableRow : function(start, end, data){
			var row = $('<tr/>').addClass('res_calendar_tablecontent_row');
			var cellStartDate = $.fullCalendar.cloneDate(start);
			var cellEndDate = $.fullCalendar.cloneDate(end);
			var column  = $('<td/>').addClass('res_calendar_tablecontent_column');
			column.appendTo(row);//绘制日期列
			var isHasData = false;
			if(data.list.length>0){
				for(var j = 0; j < data.list.length; j++){
					var tmpFlag = this._chkInstect(cellStartDate,cellEndDate,data.list[j],column);
					if(tmpFlag == true && !isHasData){
						isHasData = true;
					}
				}
			}
			
			// 新增数据处理接口
			try{
				data._renderTableRow && data._renderTableRow(row, start, end, data);
			} catch(e) {
				console.error(e);
			}
			
		},
		//功能：较验日程信息是否在时间区域内
		_chkInstect : function(start, end, evt , dataTd){
			var allDay = evt.allDay;
			var dataStart=evt.start;
			if(typeof(dataStart)=="string")
				dataStart = $.fullCalendar.parseDate(evt.start,this.calSetting.ignoreTimezone);
			evt.start = dataStart;
			var dataStartInt = dataStart.getTime();
			//日程开始时间位于这一天
			if(dataStartInt>=start.getTime() && dataStartInt< end.getTime()){
				//没有结束时间,TD=1
				if(evt.end==null || evt.end==''){
					evt.renderLength =  this.getDataLength(dataStart, dataEnd, allDay);
					dataTd.append(this.renderData(evt));
					return true;
				}else{
					//有结束时间,TD=日程结束时间-日程开始时间
					var dataEnd = evt.end;
					if(typeof(dataEnd)=="string")
						dataEnd = $.fullCalendar.parseDate(evt.end,this.calSetting.ignoreTimezone);
					evt.end = dataEnd;
					
					evt.renderLength =  this.getDataLength(dataStart, dataEnd, allDay);
					dataTd.append(this.renderData(evt));
					return true;
				}
			//日程开始时间不处于日历中,TD=日程结束时间-日历开始时间
			}else if(dataStartInt<this.start.getTime()&&evt.end!=null&&evt.end!=''&&start.getTime()==this.start.getTime()){
				var dataEnd = evt.end;
				if(typeof(dataEnd)=="string")
					dataEnd = $.fullCalendar.parseDate(evt.end,this.calSetting.ignoreTimezone);
				evt.end = dataEnd;
				evt.renderLength =  this.getDataLength(dataStart, dataEnd, allDay);
				dataTd.append(this.renderData(evt));
				return true;
			}
			return false;
		},
		getDataLength : function(start, end, evt){
			return 1;
		},
		renderData : function(data){
			var divObj = $('<div/>')
							.addClass('res_calendar_data_inner')
							.append($('<p/>').addClass('textEllipsis').html(this.fortmatTitle(data)).attr('title',this.formatTitleAttr(data)));
			divObj = $('<div/>').addClass('res_calendar_data').attr('length',data.renderLength).append(divObj);
			divObj.data("data",data);
			var cssObj={};
			if(data.color){
				cssObj['backgroundColor'] = data.color;
				cssObj['borderColor'] = data.color;
			}
			if(data.backgroundColor){
				cssObj['backgroundColor'] = data.backgroundColor;
			}
			if(data.borderColor){
				cssObj['borderColor'] = data.borderColor;
			}
			if(data.textColor){
				cssObj['color'] = data.textColor;
			}
			divObj.css(cssObj);
			if(data.className){
				divObj.addClass(data.className);
			}
			divObj = $('<div style="position:relative"/>').append(divObj);
			return divObj;
		},
		fortmatTitle : function(data){
			var formaStr=seajs.data.env.pattern.datetime+"{ ' ~ ' "+seajs.data.env.pattern.datetime+"}";
			var title="",titleAttr="";
			if(data.allDay){
				title='';
			}else{
				titleAttr=$.fullCalendar.formatDate(data['start'],formaStr)+" ~ "+$.fullCalendar.formatDate(data['end'],formaStr);
				title=$.fullCalendar.formatDate(data['start'],"HH:mm{ - HH:mm}");
			}
			title+=" "+data.title;
			title=env.fn.formatText(title);
			return title;
		},
		formatTitleAttr : function(data){
			return data.title;
		},
		renderLoaing : function(){
			var self = this,
				container = $('<div/>').addClass('res_calendar_loadingContainer');
			this.loadingNode = $('<div/>')
								.addClass('res_calendar_loading')
								.append($('<span/>').html(lang['ui.rescalendar.loading']))
								.append($('<i/>'))
								.appendTo(container);
			this.loadMoreNode = $('<div/>')
								.addClass('res_calenadr_loadmore')
								.append($('<span/>').html(lang['ui.rescalendar.loadMore']))
								.append($('<img/>').attr('src',Com_Parameter.ContextPath + 'sys/ui/extend/theme/default/images/calendar/showMore.png'))
								.appendTo(container);
			this.loadNoneNode = $('<div/>')
								.addClass('res_calendar_nodata')
								.html(lang['ui.rescalendar.nodata'])
								.appendTo(container);
			this.loadMoreNode.on('click',function(){
				self.pageno++;
				if( (self.pageno -1 ) * self.persize >= self.total){
					//没有更多
					self.loadMoreNode.hide();
					self.loadNoneNode.show();
				}else{
					self.loadMoreNode.hide();
					self.loadingNode.show();
					self.render('append');
				}
			});
			container.appendTo(this.calendarDiv);
			this.loadMoreNode.show();
			return container;
		},
		formatTime : function(hour,min){
			return (hour >= 10 ? hour : '0' + hour) + ':' + ( min < 0 || min > 60 || !min ? '00' : (min >=10 ? min : '0' + min) );
		},
		afterRender : function(){
			var t = this;
			var rows = $('.res_calendar_tablecontent_row[init!="true"]',this.calendarDiv);
			var cateRows = $('.res_calendar_rescontent_row[init!="true"]',this.calendarDiv);
			rows.each(function(){
				var Maxtop = 0,
					tops = [],
					row = $(this);
				
				row.find('.res_calendar_data').each(function(){
					var cellObj = $(this).parents('td').eq(0),
						cellIndex = cellObj.index(),
						cellPerHeight = parseInt($(this).outerHeight()),
						cellLength = parseInt($(this).attr("length"));
					
					$(this).css("width", cellLength * 100 - 2 + "%");
					$(this).css("top",tops[cellIndex] || 1);
					$(this).css("left",1);//日程位置
					for(var k = 0;k < cellLength; k++){
						tops[k + cellIndex] = tops[k + cellIndex] || 1;
						if(k == 0){
							tops[k + cellIndex] += cellPerHeight + 1;
						}else{
							tops[k + cellIndex] = tops[cellIndex];
						}
						if(tops[ k + cellIndex] > Maxtop)
							Maxtop = tops[k + cellIndex];
					}
					//可点击
					$(this).click(function(ev){
						if (!$(this).hasClass('ui-draggable-dragging')) {
							var arg={
								"evt":ev,
								"data":$(this).data("data")
							};
							topic.channel(t.calendar).publish(LUI_CALENDAR_THING_CLICK,arg);
						}
					}).mousedown(function(ev){
						ev.stopPropagation();
					});
				});
				
				//存储当前高度信息,添加区域选择事件
				$(this).find('td').each(function(index){
					$(this).attr('top',tops[index]);
					var ____lastcell,____origcell;
					$(this).mousedown(function(ev){
						//阻止事件冒泡
						ev.stopPropagation();
						//原先选中删除
						$(t.calendarDiv).find('.res_calendar_tablecontent_column').removeClass('res_calendar_state_highlight');
						$(this).addClass('res_calendar_state_highlight');
						hoverListener.start(function(cell, origCell){
							//同一行
							if(cell && goordinateGrid.cellToTD(cell).parent()[0] == goordinateGrid.cellToTD(origCell).parent()[0]){
								____lastcell=cell;
								____origcell=origCell;
								goordinateGrid.cellToTD(cell).addClass('res_calendar_state_highlight');
							}
						},ev);
						var td = this;
						$(document).one('mouseup', function(ev) {
							hoverListener.stop();
							//trigger
							if(____origcell.col > ____lastcell.col){
								var tmp = ____origcell;
								____origcell = ____lastcell;
								____lastcell = tmp;
							}
							var arg={
								"evt":ev,
								"start":goordinateGrid.cellToTD(____origcell).attr('date'),
								"end":goordinateGrid.cellToTD(____lastcell).attr('date'),
								"time":goordinateGrid.cellToTD(____lastcell).attr('time'),
								"resId":$(td).parent().attr("resId"),
								"resName":$(td).parent().attr("resName")
							};
							
							topic.channel(t.calendar).publish(LUI_CALENDAR_SELECT,arg);
						});
					});
				});
				
				//取消区域选择事件
				$(document).mousedown(function(ev) {
					$(t.calendarDiv).find('.res_calendar_tablecontent_column').removeClass('res_calendar_state_highlight');
				});

				//最大设定高度重新
				var rowIndex = $(this).index(),
					rowHeight = ($(this).height() > Maxtop ? $(this).height() : Maxtop) + 15,
					cate = cateRows.eq(rowIndex);
			
				var divHeight = $(cate).children("div").height();
				rowHeight = rowHeight > divHeight ? rowHeight : divHeight;
				
				$(this).children().height(rowHeight);
				$(cate).height(rowHeight);
				$(cate).css("line-height",(rowHeight)+"px");
				
				
				var goordinateGrid=new CoordinateGrid(t.calendarDiv);
				var hoverListener=new HoverListener(goordinateGrid);
				
				//已初始化.下次加载更多日程时,此行无需初始化了..
				$(this).attr("init",true);
				
			});
			
			//是否显示加载更多
			this.loadingNode.hide();
			if( this.pageno * this.persize >= this.total){
				this.loadNoneNode.show();
				this.loadMoreNode.hide();
			}else{
				this.loadNoneNode.hide();
				this.loadMoreNode.show();
			}
		}
	});
	
	//资源周视图模式
	var ResWeekCalendarMode = ResAbstractCalenderMode.extend({
		//设置时间,开始=周一、结束=周日
		setCalendarDate : function(){
			var today = new Date();
			this.start = this.clearTime(today);
			this.start.setDate(today.getDate() - (today.getDay() == 0 ? 7 : today.getDay()) + 1);
			this.end = $.fullCalendar.cloneDate(this.start);
			this.end.setDate(this.end.getDate() + 6);
		},
		//绘制星期列
		renderHeaderDate : function(container){
			for(var i = 0; i < 7; i++){
				var now = new Date();
				var cellStartDate = $.fullCalendar.cloneDate(this.start);
				cellStartDate.setDate(this.start.getDate() + i);
				var dateTd = $('<td/>').addClass('res_calendar_headerDate');
				//今天
				if(cellStartDate.getDate() == now.getDate()
						&& cellStartDate.getMonth() == now.getMonth()
						&& cellStartDate.getFullYear()
						&& now.getFullYear()){
					dateTd.addClass('fc-state-highlight');
				}
				$('<div/>').append(this.calSetting.dayNamesShort[cellStartDate.getDay()]+"("+$.fullCalendar.formatDate(cellStartDate,'MM-dd')+")").appendTo(dateTd);
				dateTd.appendTo(container);
			}
		},
		renderTableRow : function(start, end, data){
			var row = $('<tr/>').addClass('res_calendar_tablecontent_row'); 
			for(var i = 0; i < 7; i++){
				var cellStartDate = $.fullCalendar.cloneDate(start);
				cellStartDate.setDate(start.getDate() + i);
				var cellEndDate = $.fullCalendar.cloneDate(cellStartDate);
				cellEndDate.setDate(cellEndDate.getDate() + 1);
				var dateTD  = $('<td />')
								.addClass('res_calendar_tablecontent_column')
								.attr('date',$.fullCalendar.formatDate(cellStartDate,"yyyy-MM-dd"))
								.appendTo(row);
				var isHasData = false;
				if(data.list.length > 0){
					for(var j = 0; j < data.list.length; j++){
						var tmpFlag = this._chkInstect(cellStartDate,cellEndDate,data.list[j],dateTD);
						if(tmpFlag == true && !isHasData){
							isHasData = true;
						}
					}
				}
			}
			
			// 新增数据处理接口
			try{
				data._renderTableRow && data._renderTableRow(row, start, end, data);
			} catch(e) {
				console.error(e);
			}
			
			return row;
		},
		getDataLength : function(startDate, endDate, isAllDay){
			var tstart = $.fullCalendar.cloneDate(this.start);//日历当前显示的第一天
			var tend = $.fullCalendar.cloneDate(this.end);//日历当前显示的最后的一天
			var tmpStartDate = $.fullCalendar.cloneDate(startDate,true);
			var tmpEndDate = $.fullCalendar.cloneDate(endDate,true);
			//不允许结束时间小于开始时间(强制将结束时间设为开始时间)
			if(tmpStartDate.getTime() >= tmpEndDate.getTime()){
				tmpEndDate = tmpStartDate;
			}
			//(日历开始时间和日程开始时间取较大者)
			if(tstart.getTime() > tmpStartDate.getTime()){
				tmpStartDate = tstart;
			}
			//(日历结束时间和日程结束时间取较小者)
			if(tend.getTime() < tmpEndDate.getTime()){
				tmpEndDate = tend;
			}
			var days=parseInt(Math.abs((tmpStartDate.getTime() - tmpEndDate.getTime())) / (1000 * 60 * 60 * 24));
			if(isAllDay){
				days += 1;  
			}else if(endDate.getTime() - tmpEndDate.getTime() > 0){
				days += 1;
			}
			if(days<1){
				days=1;
			}
			return days;
		},
		//功能：获取当前view信息
		getView : function(){
			var	formaStr = "MM/dd/yyyy{' - 'MM/dd/yyyy}";
			if(Com_Parameter['Lang']!=null && (Com_Parameter['Lang']=='zh-cn' || Com_Parameter['Lang']=='zh-hk'|| Com_Parameter['Lang']=='ja-jp')){
				formaStr="yyyy-M-d{ ' ~  'yyyy-M-d }";
			}
			return {name:'week' , title:$.fullCalendar.formatDates(this.start,this.end,formaStr), start:this.start, end:this.end};
		},
		/*****************************************************
		 * 功能：上一页
		 ****************************************************/
		prev: function(){
			this.start.setDate(this.start.getDate()-7);
			this.end.setDate(this.end.getDate()-7);
			this.pageno = 1;
			this.render(); 
			if(this.calSetting.viewDisplay){
				this.calSetting.viewDisplay(this.getView());
			}
		},
		/*****************************************************
		 * 功能：下一页
		 ****************************************************/
		next : function(){
			this.start.setDate(this.start.getDate()+7);
			this.end.setDate(this.end.getDate()+7);
			this.pageno = 1;
			this.render(); 
			if(this.calSetting.viewDisplay){
				this.calSetting.viewDisplay(this.getView());
			}
		},
		/*****************************************************
		 * 功能：回到今天
		 ****************************************************/
		today : function(){
			var today = new Date();
			if(today.getTime()>=this.start.getTime() && today.getTime()<this.end.getTime())
				return;
			this.start = this.clearTime(today);
			this.start.setDate(today.getDate()-(today.getDay()==0?7:today.getDay())+1);//设为星期一
			this.end = $.fullCalendar.cloneDate(this.start);
			this.end.setDate(this.end.getDate()+6);
			this.pageno = 1;
			this.render(); 
			if(this.calSetting.viewDisplay){
				this.calSetting.viewDisplay(this.getView());
			}
		}
	});
	
	//资源日视图模式
	var ResDayCalendarMode = ResWeekCalendarMode.extend({
		//设置时间,开始=今天、结束=明天
		setCalendarDate : function(){
			var today = new Date();
			this.start = this.clearTime(today);
			this.end = $.fullCalendar.cloneDate(this.start);
			this.end.setDate(this.end.getDate() + 1);
		},
		renderHeaderDate : function(container){
			for(var i = 2; i <= 24;i=i+2){
				var cellStartDate = $.fullCalendar.cloneDate(this.start);
				var dateTd = $('<td/>').addClass('res_calendar_headerDate');
				
				$('<div/>').append(this.formatTime(i)).appendTo(dateTd);
				dateTd.appendTo(container);
			}
		},
		addHeaderSlide : function(){
			$(this.calendarDiv).find('.res_calendar_header tbody tr').each(function(i){                   // 遍历 tr
    	      $(this).children('td').each(function(j){  // 遍历 tr 的各个 td
    	    	  var tdArr = $(this);
    	    	  if(tdArr.css("display") != 'none'){
    	    		 $('<div/>').addClass('res_calendar_day_left').append("上一页").appendTo(tdArr);
 		    		 tdArr.click(function(){
 		    			 alert("1");
 		    		 });
 		    	 }
    	      });
    	   });
		},
		renderTableRow : function(start, end, data){
			var row = $('<tr/>').addClass('res_calendar_tablecontent_row');
			for(var i = 2; i <= 24;i=i+2){
				var cellStartDate = $.fullCalendar.cloneDate(start);
				cellStartDate.setHours(i-2);
				cellStartDate.setMinutes(0);
				var cellEndDate = $.fullCalendar.cloneDate(cellStartDate);
				cellEndDate.setHours(i);
				cellEndDate.setMinutes(0);
				var dateTD  = $('<td />')
								.addClass('res_calendar_tablecontent_column')
								.attr('date',$.fullCalendar.formatDate(cellStartDate,"yyyy-MM-dd"))
								.attr('time',this.formatTime(i))
								.appendTo(row);
			
				var isHasData = false;
				if(data.list.length > 0){
					for(var j = 0; j < data.list.length; j++){
						var tmpFlag = this._chkInstect(cellStartDate,cellEndDate,data.list[j],dateTD);
						if(tmpFlag == true && !isHasData){
							isHasData = true;
						}
					}
				}
			}
			
			// 新增数据处理接口
			try{
				data._renderTableRow && data._renderTableRow(row, start, end, data);
			} catch(e) {
				console.error(e);
			}
			
			return row;
		},
		_render:function(start ,end ,datas,renderType){
			
			var self = this;
			
			if(!renderType){
				this.calendarDiv.html('');
				//绘制资源区域
				this.resContent = $('<ul />')
									.addClass('res_calendar_rescontent')
									.append(this.renderCate(datas.category, datas.categoryName))
									.appendTo(this.calendarDiv);
				var rightContent = $('<div/>')
									.addClass('res_calendar_rightContent')
									.appendTo(this.calendarDiv);
				var allRightContent = $('<div/>')
									.addClass('res_calendar_allRightContent')
									.appendTo(rightContent);
				//绘制头部区域
				this.headerContainer = this.renderHeader(start ,end).appendTo(allRightContent);
				//绘制日程区域
				this.tableContent =  $('<table />').addClass('res_calendar_content').appendTo(allRightContent);
				$('<div/>').addClass('clearfloat').appendTo(rightContent);
				//设置日视图默认显示8-20时
				var gridwith = $(self.calendarDiv).find('.res_calendar_allRightContent').width() / 12;
				$(self.calendarDiv).find('.res_calendar_allRightContent').css("left", -gridwith * 3 + "px");
				//设置资源区域高度
				//rightContent.css('height', '97%');
				
				//日视图左右移动
				var dayLeft = $('<div/>').addClass('res_calendar_day_left').appendTo(rightContent).click(function(){
					var left = $(self.calendarDiv).find('.res_calendar_allRightContent').position().left;
					if(left < 0){
						$(self.calendarDiv).find('.res_calendar_allRightContent').css("left", left + gridwith + "px");
						$(self.calendarDiv).find('.res_calendar_day_right').css("background-color", "#56a8da");
					}else{
						$(self.calendarDiv).find('.res_calendar_day_left').css("background-color", "#8c8c8c");
				
					}
				});
				var dayRight = $('<div/>').addClass('res_calendar_day_right').appendTo(rightContent).click(function(){
					var left = $(self.calendarDiv).find('.res_calendar_allRightContent').position().left;
					//#106216 日视图中24:00无法显示
					if(left > -gridwith * 4.5){
						$(self.calendarDiv).find('.res_calendar_allRightContent').css("left", left - gridwith + "px");
						$(self.calendarDiv).find('.res_calendar_day_left').css("background-color", "#56a8da");
					}else{
						$(self.calendarDiv).find('.res_calendar_day_right').css("background-color", "#8c8c8c");
					
					}
				});
			}else {
				if(renderType == 'refresh'){
					this.resetContent();
				}
			}
			var main = datas.main;
			if(main != null){
				for(var id in main){
					//绘制资源内容行
					this.resContent.append(this.renderResRow(main[id]));
					//绘制日程行
					var row = this.renderTableRow(start, end, main[id]);
					row.attr('resId',main[id]['fdId']);
					row.attr('resName',main[id]['name']);
					this.tableContent.append(row);
				}
			}
			if(!renderType){
				//绘制加载区域
				this.renderLoaing();
			}
		},
		getDataLength : function(startDate, endDate, isAllDay){
			//debugger;
			var tstart = $.fullCalendar.cloneDate(this.start);//日历当前显示的第一天
			var tend = $.fullCalendar.cloneDate(this.end);//日历当前显示的最后的一天
			var tmpStartDate = $.fullCalendar.cloneDate(startDate,true);
			var tmpEndDate = $.fullCalendar.cloneDate(endDate,true);
			var days = null;
			if(endDate.getDate() == startDate.getDate()){
				var endHour = endDate.getHours();
				var startHour = startDate.getHours();
				if(endHour%2 == 0&&startHour%2 == 0){
					if((endHour - startHour) == 2){
						days = 1;
					}else{
						days = (endHour - startHour)/2;
					}
					//不是整点length+1
					if(endDate.getMinutes() > 0){
						days = days + 1;
					}
				}else if ((endHour%2 != 0&&startHour%2 == 0)||(endHour%2 == 0&&startHour%2 != 0)){
					var interval = endHour - startHour;
					days =  Math.ceil(interval / 2);
					if(endHour%2 == 0 && endDate.getMinutes() > 0){
						days = days + 1;
					}
				}else if(endHour%2 != 0&&startHour%2 != 0){
					days =  Math.ceil((endHour - startHour)/2)+1;
				}
			} else{
				if(tstart.getDate() == startDate.getDate()){
					days =  parseInt((24 - startDate.getHours()) / 2);
				}else if(startDate.getTime() <= tstart.getTime() && endDate.getTime() >= tend.getTime()){
					days = 12;
				}else{
					var interval = endDate.getHours() - tstart.getHours();
					days =  Math.ceil(interval / 2);
					//不是整点length+1
					if(endDate.getMinutes() > 0){
						days = days + 1;
					}
					if(endDate.getMinutes() == 0 && endDate.getHours() % 2 != 0){
						days = days + 1;
					}
				}
			}
			return days;
		},
		/*****************************************************
		 * 功能：获取当前view信息
		 ****************************************************/
		getView : function(){
			var formaStr = "yyyy-MM-dd";
			return {name:'day' , title:$.fullCalendar.formatDate(this.start,formaStr), start:this.start, end:this.end};
		},
		/*****************************************************
		 * 功能：上一页
		 ****************************************************/
		prev: function(){
			this.start.setDate(this.start.getDate()-1);
			this.end.setDate(this.end.getDate()-1);
			this.pageno = 1;
			this.render(); 
			if(this.calSetting.viewDisplay){
				this.calSetting.viewDisplay(this.getView());
			}
		},
		/*****************************************************
		 * 功能：下一页
		 ****************************************************/
		next : function(){
			this.start.setDate(this.start.getDate()+1);
			this.end.setDate(this.end.getDate()+1);
			this.pageno = 1;
			this.render(); 
			if(this.calSetting.viewDisplay){
				this.calSetting.viewDisplay(this.getView());
			}
		},
		/*****************************************************
		 * 功能：回到今天
		 ****************************************************/
		today : function(){
			var today = new Date();
			this.start = this.clearTime(today);
			this.start.setDate(today.getDate());
			this.end = $.fullCalendar.cloneDate(this.start);
			this.end.setDate(today.getDate());
			this.pageno = 1;
			this.render(); 
			if(this.calSetting.viewDisplay){
				this.calSetting.viewDisplay(this.getView());
			}
		}
	});
	
	//资源日历
	var ResCalendar = Calendar.Calendar.extend({
		initProps : function($super,_config){
			//_config.mode = 'week'; //资源日历默认周视图
			$super(_config);
		},
		initialize : function($super,_config){
			$super(_config);
			this.element.addClass('lui-rescalendar');
		},
		startup : function(){
			//#94123 切换页面时会议日历重绘导致空白问题。base.js中进行重绘，改动代价太大，只能对日历特殊处理
			var id = this.element.parents("[data-lui-type]").eq(0).attr("data-lui-cid")
			if(id){
				var parentDataView = LUI(id);
				if(parentDataView && parentDataView.isDrawed){
					parentDataView.isDrawed = false;
				}
			}
		},
		modeSetting : {
			"week" : {
				id:"week",
				name : "资源周视图",
				func : ResWeekCalendarMode,
				cache: null
			},
			"day" :{
				id:"day",
				name : "资源日视图",
				func : ResDayCalendarMode,
				cache: null
			}
		}
	});
	
	
	/**
	 * 坐标网格类
	 */
	var CoordinateGrid=function(calendarDiv){
		this.rows = [];
		this.cols = [];
		//构建行、列
		this._build=function(){
			var self = this;
			var headcells = $('.res_calendar_headerDate', calendarDiv),
				rowcells = $('.res_calendar_tablecontent_row', calendarDiv);
			var p,n,element;
			headcells.each(function(i,_element){
				element = $(_element);
				n = element.position().left;
				if(i > 0){
					p[1] = n;
				}
				p = [n];
				$(_element).attr('data-left', p);
				self.cols[i] = p;
			});
			p[1] = n + element.outerWidth();
			rowcells.each(function(i,_element){
				element = $(_element);
				n = element.position().top;
				if(i > 0){
					p[1] = n;
				}
				p = [n];
				$(_element).attr('data-top', p);
				self.rows[i] = p;
			});
			p[1] = n + element.outerHeight();
		};
		this._build();
		
		//获取单元格
		this.cell=function(x,y){
			
			// 修复定位问题 by 彭伟聪
			var t = $('.res_calendar_allRightContent', calendarDiv);
			if(t.get(0)) {				
				var w = t.width() / 12 * 3;
				var l = t.position().left;
				x = x - (w + l);
			}
			
			var rowCnt = this.rows.length;
			var colCnt = this.cols.length;
			var i, r=-1, c=-1;
			for (i=0; i < rowCnt; i++) {
				if (y >= this.rows[i][0] && y < this.rows[i][1]) {
					r = i;
					break;
				}
			}
			for (i=0; i<colCnt; i++) {
				if (x >= this.cols[i][0] && x < this.cols[i][1]) {
					c = i;
					break;
				}
			}
			
			return (r >= 0 && c >= 0) ? { row:r, col:c } : null;
		};
		//将单元格转为TD
		this.cellToTD = function(cell){
			var TR = $('.res_calendar_tablecontent_row', calendarDiv).eq(cell.row);
			var TD = TR.children().eq(cell.col)
			return TD;
		};
	};
	
	/**
	 * 移动监听类
	 */
	var HoverListener=function(coordinateGrid){
		var bindType,firstCell,cell,fn;
		//移动中....
		var mouse=function(ev){
			var coordinate = _getCoordinate(ev);
			var newCell = coordinateGrid.cell(coordinate.x, coordinate.y);
			if (!newCell != !cell || newCell && (newCell.row != cell.row || newCell.col != cell.col)) {
				if (newCell) {
					if (!firstCell) {
						firstCell = newCell;
					}
					fn(newCell, firstCell, newCell.row-firstCell.row, newCell.col-firstCell.col);
				}else{
					fn(newCell, firstCell);
				}
				cell = newCell;
			}
		};
		//移动开始触发
		this.start=function(_fn, ev, _bindType){
			fn=_fn;
			firstCell = cell = null;
			bindType = _bindType || 'mousemove';
			mouse(ev);
			$(document).bind(bindType, mouse);
		};
		//移动结束触发
		this.stop=function(){
			$(document).unbind(bindType, mouse);
			return cell;
		};
		
		// 根据TR TD来获取x、y
		function _getCoordinate(event){
			var trNode,tdNode;
			var parent = event.target;
			while(parent){
				if($(parent).hasClass('res_calendar_tablecontent_column')){
					tdNode = parent;
				}
				if($(parent).hasClass('res_calendar_tablecontent_row')){
					trNode = parent;
					break;
				}
				parent = parent.parentNode
			}
			if(tdNode && trNode){
				return { x: $(tdNode).position().left  + $(tdNode).width() / 2 , y : $(trNode).position().top  + $(tdNode).height() / 2  };
			}
			return { x : event.pageX || event.originalEvent.pageX, y : event.pageY || event.originalEvent.pageY};
		}
	};
	
	exports.ResCalendar = ResCalendar;
	
});
