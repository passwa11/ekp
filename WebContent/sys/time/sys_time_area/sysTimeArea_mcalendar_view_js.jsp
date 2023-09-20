<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<script>
	
	seajs.use(['lui/calendar', 'lui/topic', 'lui/dialog', 'lui/dateUtil', 'sys/time/sys_time_area/resource/js/classlist',
	           'sys/time/sys_time_area/resource/js/mcalendar', 'sys/time/sys_time_area/resource/js/util'],
				function (LunarCalendar, topic, dialog, dateUtil, ClassList, MonthCalendar, util) {	

		//////////////////////////////////////////////////////////////////
		// 动态调整容器高度
		//////////////////////////////////////////////////////////////////
		window.pageResize = function(){	
			$(window.parent.document).find('iframe#calendarContent').height(900);
		};

		$(window).resize(function(){
			pageResize();
			window._MCALENDAR_ && window._MCALENDAR_.refresh();
			//重新根据条件过滤呈现
		});
		
		// 延迟一秒重新计算容器高度
		setTimeout(function() {
			pageResize();
			// calendar && calendar.setOptions({
			// 	maxHeight: (window.screen.height - ($('.toolbar').height()) * 2 - 188) + 'px'
			// });
		}, 1000);
		
		//////////////////////////////////////////////////////////////////
		// 全局变量
		//////////////////////////////////////////////////////////////////
		var WEEKS = ['日', '一', '二', '三', '四', '五', '六' ];
		
		var now = new Date();
		now = new Date(now.getFullYear(), now.getMonth(), now.getDate());
		
		// 排班元数据
		var srcData = [];
		var tmpData = [];
		
		// 节假日数据
	    var holidayData = [];
		
		var currentYear = now.getFullYear();
		var currentMonth = now.getMonth();

		window.genCurrentYearSelect = function(date,domNode){
			var _currentYear = date.getFullYear();
			var _currentMonth = date.getMonth();
			for(var i = 2019; i <= currentYear + 3; i++) {
				var opt = $('<option/>').attr('value', i).text(i).appendTo($(domNode));
				if(i == _currentYear) {
					opt.attr('selected', 'selected');
				}
			}
		};
		genCurrentYearSelect(now,$('select[name="year"]'));
		
		$('select[name="year"]').change(function() {
			currentYear = parseInt($(this).val());	
			calendar.setData({
				year: currentYear			
			}, true);		
		});
		
		$('select[name="month"] option[value="' + currentMonth + '"]').prop('selected', 'selected');
		$('select[name="month"]').change(function() {
			currentMonth = parseInt($(this).val());			
			calendar.setData({
				month: currentMonth			
			}, true);
		});
		$('input[name="search_name"]').change(function() {
			var searchTxt = $('input[name="search_name"]').val();
			if(!$.trim(searchTxt)){
				currentMonth = parseInt($('select[name="month"]').val());		
				calendar.setData({
					month: currentMonth			
				}, true);
				return;
			}
			onCalendarSearch();
		});
		window.onCalendarSearch = function(){
			var searchTxt = $('input[name="search_name"]').val();
			if(!$.trim(searchTxt)){
				return;
			}
			currentMonth = parseInt($('select[name="month"]').val());	
			currentYear = $('select[name="year"]').val();
			
			loadUserInfo(searchTxt,function(data){
				$('#calendar .lui-mcalendar-b').hide();
				if(data.length==0){
					return;
				}
				showOrHideOrg(data);
				$('#calendar .lui-mcalendar-b').show();
			});
		};
		window.resetMonthSelected = function(month){
			$('select[name="month"] option:selected').removeAttr('selected');
			$('select[name="month"] option[value="' + month + '"]').prop('selected', 'selected');
		};
		window.resetYearSelected = function(year){
			$('select[name="year"] option:selected').removeAttr('selected');
			$('select[name="year"] option[value="' + year + '"]').prop('selected', 'selected');
		};
		window.showOrHideOrg = function(data){
			var orgRows = $('#calendar .lui-mcalendar-row');
			var orgDateRows = $('#calendar .lui-mcalendar-main-row');
			var datastr = data.join(',');
			for(var i = 0; i < orgRows.length;i++){
				var row = orgRows[i];
				var orgId = $(row).attr('data-element-id');
				if(datastr.indexOf(orgId) >-1){
					$(row).show();
				}else{
					$(row).hide();
				}
			}
			for(var i = 0; i < orgDateRows.length;i++){
				var row = orgDateRows[i];
				var orgId = $(row).children(":first").attr('data-element-id');
				if(datastr.indexOf(orgId) >-1){
					$(row).show();
				}else{
					$(row).hide();
				}
			}
		};
		
		window.loadUserInfo = function(searchTxt,callback){
			var url = '${LUI_ContextPath}/sys/time/sys_time_area/sysTimeArea.do?method=searchUser&fdId=' + '${sysTimeAreaForm.fdId}';
			$.ajax({
				  url: url,
				  data: {searchTxt:searchTxt},
				  dataType: 'json',
				  success: function(result){
					  if(result.status !='true'){
						  alert('查询失败,请重试!');
						  return;
					  }
					  if(callback){
						  callback(result.data);
					  }
				  },
				  error : function(e){
					 alert('查询失败,请重试!'); 
				  }
				});
		};
		
		var classesNode = $('#classes');
		function createClassNode(clazz) {
			
			var classTag = $('<div/>').addClass('tag').attr('title', clazz.title || '');

			if(clazz.isTip) {
				$('<span/>').addClass('tag-label')
					.text(clazz.title || '')
					.appendTo(classTag);
				return classTag;
			}
			
			$('<span/>').addClass('tag-flag')
				.css('background-color', clazz.color || '')
				.appendTo(classTag);
			
			$('<span/>').addClass('tag-label')
				.text(clazz.times || '')
				.appendTo(classTag);
			
			return classTag;
		}
		
	    // 获取班次数据
	    var ajaxGetAll = $.get('${LUI_ContextPath}/sys/time/sys_time_area/sysTimeArea.do?method=getDefine&fdId=${JsParam.fdId}');
	    var ajaxGetCommon = $.get('${LUI_ContextPath}/sys/time/sys_time_common_time/sysTimeCommonTime.do?method=commonTimeById');
	    
	    $.when(ajaxGetAll, ajaxGetCommon).then(function(all, cc){

	    	if(Object.prototype.toString.call(all[0]) == '[object Array]') {
		    	all = all[0] || [];
	    	} else {
				all = [];			    		
	    	}
	    	
	    	if(Object.prototype.toString.call(cc[0]) == '[object Array]') {
		    	cc = cc[0] || [];
	    	} else {
				cc = [];	    		
	    	}
	    	
	    	if(all.length <= 0 && cc.length <= 0) {
	    		createClassNode({
	    			isTip: true,
	    			title: '暂无班次'
	    		}).appendTo(classesNode);;
	    		return;
	    	}
	    	

	    	
	    	$.each(cc || [], function(_, item) {
	    		
	    		var clazz = {};
	    		clazz['title'] = item.name;
	    		clazz['color'] = item.color;
	    		
	    		var times = [];
	    		$.each(item.times || [], function(__, time) {
	    			var endStr = '';
					if(time.overTimeType == 2){
						endStr = '(${ lfn:message("sys-time:sysTimeWork.fdOverTimeType.type2") })';
					}
	    			times.push(
    					(time.start || '')
	    				+
	    				'~'
	    				+
	    				(time.end || '')
	    				 +
	    				 ' ' +
	    				 endStr 
	    			);
	    			
	    		});
	    		
	    		clazz['times'] = times.join('; ');
	    		createClassNode(clazz).appendTo(classesNode);
	    	});
			
	    	$.each(all, function(_, item) {
	    		var clazz = {};
	    		clazz['title'] = item.clazz.name || '';
	    		clazz['color'] = item.clazz.color;
	    		
	    		var times = [];
	    		$.each(item.clazz.times || [], function(__, time) {
	    			var endStr = '';
					if(time.overTimeType == 2){
						endStr = '(${ lfn:message("sys-time:sysTimeWork.fdOverTimeType.type2") })';
					}
	    			times.push(
    					(time.start || '')
	    				+
	    				'~'
	    				+
	    				(time.end || '')
	    				 +
	    				 ' '
	    				 + 
	    				 endStr 
	    			);
	    		});
	    		clazz['times'] = times.join('; ');
	    		createClassNode(clazz).appendTo(classesNode);
	    	});
	    	
	    }, function(err) {
	    });
	    
	    
	    var calendar = window._MCALENDAR_ = new MonthCalendar($('#calendar'), {
	    	year: currentYear,
	    	month: currentMonth,
	    	elements: [],
	    	data: []
	    }, {
			y: '人员',	
			x: '',
			deptName:'部门',
			maxHeight: (window.screen.height - 256) + 'px',
	    	renderCol: function(col, year, month, date, element) {
	    		var d = new Date(year, month, date);
	    		$('<div/>').attr('data-type', 'date').text(date).appendTo(col);
	    		
	    		var _d = LunarCalendar.solayDayWithType(d);
				if(_d.type) {
					$('<div/>').attr('data-type', 'day').text(_d.value).appendTo(col);
				} else {
		    		$('<div/>').attr('data-type', 'day').text(WEEKS[d.getDay()]).appendTo(col);
				}
	    		
	    	},
	    	afterRenderCol: function(col, year, month, date, element) {
	    		
	    		var d = new Date(year, month, date);
	    		var i = 0, l = holidayData.length;
	    		for(i; i < l; i++) {
	    			var hd = holidayData[i];
	    			if(d >= hd.startDate && d <= hd.endDate) {
						var signNode = $('<span/>').attr('data-type', 'sign').attr('data-date-type', hd.type).appendTo(col);
						signNode.click(function(e) {
							if(hd.type == 4) {
								MonthCalendar.showTip(e, {text: hd.name});
							} else {
								MonthCalendar.showTip(e, {text: hd.name + '补班'});
							}
						});
						
						//渲染节假日信息（不存在数据的情况）
						element.find('[data-type="main-col"][data-year="' + year + '"][data-month="' + month + '"][data-date="' + date + '"]').each(function() {
							if($(this).attr('data-has-data') != 'true') {
								if(hd.type == 4) {
									$('<span/>').attr('data-type', 'label').attr('data-date-type', hd.type).text('假').appendTo($(this));								
								} else {
									$('<span/>').attr('data-type', 'label').attr('data-date-type', hd.type).text('班').appendTo($(this));
								}
							}
						});
						
	    				break;
	    			}
	    		}
	    		
	    	},
	    	
	    	beforeRenderData: function(cell, d, element) {
	    		(cell.attr('data-old') == 'true') ? '' : cell.css('background-color', 'transparent');
	    	},
	    	
	    	renderData: function(cell, d, element) {
				switch(d.type) {
					case 1: 
						cell.css('background-color', d.clazz.color);
						break;
					case 2: 
						var signNode = $('<span/>').attr('data-type', 'sign').attr('data-date-type', d.type).appendTo(cell);
						signNode.click(function(e) {
							MonthCalendar.showTip(e, {text: d.name});
						});
						$('<span/>').attr('data-type', 'label').attr('data-date-type', d.type).text('假').appendTo(cell);
						break;
					case 3:
						cell.css('background-color', d.clazz.color);
						$('<span/>').attr('data-type', 'sign').attr('data-date-type', d.type).appendTo(cell);
						break;
					default: break;
				}
	    		
	    	},
			contextMenuTitle: function(cell, data) {
				var menuTitle = '暂无数据';
				var d = new Date(data.year, data.month, data.date), i = 0, l = tmpData.length;
				for(i; i < l; i++) {
					var t = tmpData[i];
					if(t.elementId == data.elementId && t.date.getTime() == d.getTime()) {
						switch(t.type) {
							case 1:
								menuTitle = t.clazz ? (t.clazz.name || '工作日') : '工作日';
								break;
							case 3:
								menuTitle = t.clazz ? (t.clazz.name || '补班') : '补班';
								break;
							case 2:
								menuTitle = t.name;
								break;
							default:
								break;
						}
					}
				}
				return menuTitle;
			}
	    });
	    
	    window.json_load=dialog.loading();
	    $.get('${LUI_ContextPath}/sys/time/sys_time_area/sysTimeArea.do?method=mCalendarJson&fdId=' + '${sysTimeAreaForm.fdId}').then(function(res) {
	    	var data = [];
	    	$.each(res.data || [], function(_, d) {
	    		data.push($.extend(d, {
	    			date: Com_GetDate(d.date)
	    		}));
	    	});
	    	
	    	tmpData = data;

	    	calendar.setData({
		    	elements: res.elements || [],
		    	data: data
		    }, true);
	    	if(window.json_load!=null){
	    		window.json_load.hide();
	    	}
	    })
	    
	    
		// 修改所属节假日
		window.handleHolidayChange = function(holidayId) {
			if(!holidayId) {
				holidayData = [];
				calendar.refresh();
				return;
			}
			$.get('${LUI_ContextPath}/sys/time/sys_time_holiday/sysTimeHoliday.do?method=getHolidayById&fdId=' + holidayId).then(function(data) {
				$('[name="fdHolidayName"]').val(data.name);
				var res = [];
				$.each(data.sysTimeHolidayDetail || [], function(_, d) {
					res.push($.extend(true, {}, d, {
						startDate: Com_GetDate(d.startDay),
						endDate: Com_GetDate(d.endDay),
						type: 4
					}));
					var t = (d.patchDay || '').split(',');
					$.each(t || [], function(__, _d) {
						
						if(!_d) {
							return;
						}
						
						res.push($.extend(true, {}, d, {
							startDate: Com_GetDate(_d),
							endDate: Com_GetDate(_d),
							type: 5
						}));
					});
				});
				holidayData = res;
				calendar.refresh();
			});
		}
		// 加载当前所属节假日数据
		window.handleHolidayChange('${sysTimeAreaForm.fdHolidayId}');
	    
		
	});
	
	function importExcel(){
		Com_OpenWindow('<c:url value="/sys/time/sys_time_area/sysTimeArea.do" />?method=importExcel&fdId=${sysTimeAreaForm.fdId}');
	}
	
	function exportExcel(isTemplate){//isTemplate 是否为下载模版
		var fdYear = $('select[name="year"] option:selected').val();
		var fdMonth = $('select[name="month"] option:selected').val();
		Com_OpenWindow('<c:url value="/sys/time/sys_time_area/sysTimeArea.do" />?method=exportExcel&fdId=${sysTimeAreaForm.fdId}&fdYear='+fdYear+'&fdMonth='+fdMonth+'&isTemplate='+isTemplate);
	}

</script>
