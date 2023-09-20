seajs.use(['lui/yearcalendar/yearcalendar', 'lui/yearcalendar/calendar', 'lui/calendar', 'lui/topic'], function (YearCalendar, Calendar, LunarCalendar, Topic) {

    $.fn.extend({
        YearCalendar: YearCalendar.Component
    });

	var sourceData = data;

    var now = new Date();
    
    var CONFIG = {
        selectedYear: now.getFullYear(),
        startYear: now.getFullYear(),
        maxYearDistance: 4,
        restDays: [0, 6]
    };
    
    // 日期类型权重顺序
    var TYPE_WEIGHT = [
                       1, // 工作日
                       4, // 法定节日
                       2, // 假期
                       5, // 法定节日补班
                       3  // 补班
                       ];

    // 通过星期判断是否为休息日
    function isRestDay(day) {
        for (var i = 0; i < CONFIG.restDays.length; i++) {
            if (CONFIG.restDays[i] == day) {
                return true;
            }
        }
        return false;
    }
    
    // 生成年数据字典
    function convertData(year, data){

    	var res = {};
    	
		for(var m = 1; m <=12; m++){

			var ds = Calendar.getDays(year, m);

			for(var d = 1; d <= ds; d++){

				res[year + '/' + Calendar.formatInt(m) + '/' + Calendar.formatInt(d)] = {
					name: null,
					type: 0,
					color: null
				}

			}
		}
		
		// 对数据进行排序
    	data = data || [];
    	
    	if(!$.isArray(data)){
    		return res;
    	}
    	
    	data.sort(function(a, b){
    		
  	      try {

	          var _a = new Date(a.createdAt.replace(/\-/g, '/')).getTime();
	          var _b = new Date(b.createdAt.replace(/\-/g, '/')).getTime();

	          if (_a > _b) {
	            return 1;
	          } else if (_a == _b) {
	            return 0;
	          } else {
	            return -1;
	          }

	        } catch (err) {
	          return 0;
	        }
    		
    	});

		$.each(data, function(idx ,item){
			
			var fromDate, toDate;
			
			if(!item.fromDate){
				return;
			}
			
			var fd = new Date(item.fromDate.replace(/\-/g, '/'));
			fromDate = fd.getFullYear() + '/' + Calendar.formatInt(fd.getMonth() + 1) + '/' + Calendar.formatInt(fd.getDate());

			if(!item.toDate){
				if(fd.getFullYear() == year + ''){
					toDate = fd.getFullYear() + '/12/31';
				} else if(fd.getFullYear() < year){
					fromDate = year + '/01/01';
					toDate = year + '/12/31';
				}
			}else{
				var td = new Date(item.toDate.replace(/\-/g, '/'));
				toDate = td.getFullYear() + '/' + Calendar.formatInt(td.getMonth() + 1) + '/' + Calendar.formatInt(td.getDate());
			}

			if(item.fromWeek){
				if(!item.toWeek){
					item.toWeek = item.fromWeek;
				}
			}

			
			var tdd = Date.parse(toDate); 
			
			for (var dd = Date.parse(fromDate); dd <= tdd; dd += 86400000){

				var _d = new Date(dd);

				var key = _d.getFullYear() + '/' + Calendar.formatInt(_d.getMonth() + 1) + '/' + Calendar.formatInt(_d.getDate());
				
				var _item = res[key];
					
				if(!_item){
					continue;
				}
				
				// 低于权重跳过
				if($.inArray(item.type, TYPE_WEIGHT) < $.inArray(_item.type, TYPE_WEIGHT)){
					continue;
				}
				

				// 超过星期跳过
				if(item.fromWeek && item.toWeek){
					if(_d.getDay() < item.fromWeek-1 || _d.getDay() > item.toWeek-1){
						continue;
					}
				}
				
				_item.name = item.name;
				_item.type = item.type;
				_item.color = item.color;

			}

		});
		
		return res;
    	
    }
    
    var dataView = render.parent;

    var mainWrap = $('<div class="main-wrap"></div>').appendTo(dataView.element);

    //////////////////
    // 渲染年份选择器
    /////////////////
    var yearSelector = $('<div class="year-selector" id="yearSelector"></div>').appendTo(mainWrap);
    
    var yearSelectorLeftNav = $('<a class="year-selector-nav year-selector-nav-l" href="javascript: void(0)" data-action="left"></a>')
    	.appendTo(yearSelector);
    
    var yearSelectorMain = $('<div class="year-selector-main"></div>').appendTo(yearSelector);
    
    var yearSelectorRightNav = $('<a class="year-selector-nav year-selector-nav-r" href="javascript: void(0)" data-action="right"></a>')
    	.appendTo(yearSelector);
    
    
    function renderYearSelector(){
    	
    	yearSelectorMain.html('');
    	
    	for (var i = CONFIG.startYear - parseInt(CONFIG.maxYearDistance / 2); i <= CONFIG.startYear + parseInt(CONFIG.maxYearDistance / 2); i++) {
    		var y = $('<a class="year-selector-item" href="javascript: void(0)"></a>').appendTo(yearSelectorMain);
    		y.attr('data-year', i);
    		y.html(i);
    		
    		if (i == CONFIG.selectedYear) {
    			y.addClass('active');
    		}
    		
    	}
    	
    }
    yearSelector.on('click', 'a.year-selector-item', function () {
    	
    	var selectedYear = parseInt($(this).attr('data-year'));
    	if(selectedYear == CONFIG.selectedYear){
    		return;
    	}
    	
    	yearSelector.find('a.year-selector-item')
    		.removeClass('active')
    		.removeAttr('disabled');
    	
        $(this)
        	.addClass('active')
        	.attr('disabled');

				CONFIG.selectedYear = selectedYear;
				
        renderYearCalendar(convertData(CONFIG.selectedYear, sourceData));

    });
    
    yearSelector.on('click', 'a.year-selector-nav', function(){
    	
    	var action = $(this).attr('data-action');
    	
    	switch(action){
    	
	    	case 'left': 
	    		CONFIG.startYear -= CONFIG.maxYearDistance;
	    		break;
	    	case 'right': 
	    		CONFIG.startYear += CONFIG.maxYearDistance;
	    		break;
	    	default: 
	    		return;
    	
    	}
    	
    	renderYearSelector();
    	
    });

    renderYearSelector();
    
    //////////////////
    // 渲染年历
    //////////////////

    var yearCalendar = $('<div id="yearCalendar"></div>').appendTo(mainWrap);

    function renderYearCalendar(data) {
        yearCalendar.YearCalendar({
            year: CONFIG.selectedYear,
            startDay: Com_Parameter.FirstDayInWeek == null ? 1 : Com_Parameter.FirstDayInWeek,
            lang: Com_Parameter.Lang || 'en-us',
            renderCalendarWeekItem: function (day, dayLabel) {
                if (isRestDay(day)) {
                    return '<span style="color: #999999">' + dayLabel + '</span>';
                }

                return '<span>' + dayLabel + '</span>';
            },
            renderCalendarOuterPageItem: function (year, month, date, day) {
                var el = $('<div class="calendar-page-item-wrap"></div>'),
                	main = $('<div class="calendar-page-item-main"></div>').appendTo(el),
            	
                d = new Date(year + '/' + Calendar.formatInt(month) + '/' + Calendar.formatInt(date));


                main.css('color', '#D5D5D5');
                main.html(date);

                var label = $('<span class="calendar-page-item-label"></span>').appendTo(main);
                label.css('color', '#D5D5D5');
                if(Com_Parameter.Lang == 'zh-cn' || Com_Parameter.Lang =='zh-hk'){                	
                	label.html(LunarCalendar.solarDay(d));
                }
                
            	return el;
            },
            renderCalendarPageItem: function (year, month, date, day) {

                var el = $('<div class="calendar-page-item-wrap"></div>'),
                	mask = $('<div class="calendar-page-item-mask"></div>').appendTo(el),
                	main = $('<div class="calendar-page-item-main"></div>').appendTo(el),
                	flag = $('<div class="calendar-page-item-flag"></div>').appendTo(el),
	                d = new Date(year + '/' + Calendar.formatInt(month) + '/' + Calendar.formatInt(date));

                main.html(date);

                var label = $('<span class="calendar-page-item-label"></span>').appendTo(main);
                
                if(Com_Parameter.Lang == 'zh-cn' || Com_Parameter.Lang =='zh-hk'){                	
                	label.html(LunarCalendar.solarDay(d));
                }

                if (isRestDay(day)) {
                	main.css('color', '#999999');
                }

				try{

					var item = data[year + '/' + Calendar.formatInt(month) + '/' + Calendar.formatInt(date)];
		
					switch(item.type){
					
						case 1: 
							main.css('color', item.color ? 'white' : '#333333');
							label.css('color', '#999999');
							flag.css('border-top-color', 'none');
							mask.css('background', item.color || 'transparent');
							break;
						case 2:
						case 4:
							main.css('color', '#999999');
							label.css('color', '#999999');
							flag.css('border-top-color', '#E60000');
							mask.css('background', 'none');
							break;
						case 3:
						case 5: 
							main.css('color', 'white');
							label.css('color', '#999999');
							flag.css('border-top-color', '#2B9AE0');
							mask.css('background', item.color || '#2B9AE0');        							
							break;
				
						default: break;
				
				
					}
					
				} catch(err){ 

				}
			
									
                return el;

            }
        });
    }

    renderYearCalendar(convertData(CONFIG.selectedYear, sourceData));

    // 开放渲染接口
    var loadData = function(data){
				sourceData = data;
        renderYearCalendar(convertData(CONFIG.selectedYear, sourceData));
    }
    
    Topic.subscribe('calendar.render', function(data){
    	loadData(data);
    });

    Topic.publish('calendar.load');


});