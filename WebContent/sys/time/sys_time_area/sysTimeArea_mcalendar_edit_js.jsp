<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<script>
	
	seajs.use(['lui/calendar', 'lui/topic', 'lui/dialog', 'lui/dateUtil', 'sys/time/sys_time_area/resource/js/classlist',
	           'sys/time/sys_time_area/resource/js/mcalendar', 'sys/time/sys_time_area/resource/js/util'],
			function (LunarCalendar, topic, dialog, dateUtil, ClassList, MonthCalendar, util) {	
		
		//////////////////////////////////////////////////////////////////
		// 全局变量
		//////////////////////////////////////////////////////////////////
		var WEEKS = ['日', '一', '二', '三', '四', '五', '六' ];

		//////////////////////////////////////////////////////////////////
		// 动态调整容器高度
		//////////////////////////////////////////////////////////////////
		window.pageResize = function(){	
			$(window.parent.document).find('iframe#calendarContent').height($(document.body).height());
		};
		setTimeout(function() {
			toobarResize();
			// calendar && calendar.setOptions({
			// 	maxHeight: (window.screen.height - ($('.toolbar').height()) * 2 - 128) + 'px'
			// });
		}, 2000);
		
		window.toobarResize = function() {
			$('.main').css('padding-top', $('.toolbar').height() + 12);
		}
		
		setTimeout(function() {
			toobarResize();
		});
		
		//////////////////////////////////////////////////////////////////
		// 主逻辑
		//////////////////////////////////////////////////////////////////
		
		// 用于标记数据
		var _symbol = 0;
		
		var now = new Date();
		now = new Date(now.getFullYear(), now.getMonth(), now.getDate());
		// 排班元数据
		var srcData = [];
		var tmpData = [];
		
		// 修改年月
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
		
		// 节假日数据
	    var holidayData = [];
	    
	    // 当前选择的班次
	    var activeClass = null;
	    var activeType = '1';
	    
	    function getColorsInUse() {
	    	var colors = [];
	    	$.each(allClassData || [], function(_, c) {
	    		colors.push(c.color);
	    	});
	    	return colors;
	    }
	    
	    // 工作日班次
	    var allClassData = [];
	    var allClassList = new ClassList($('#classTab'), [], {
			onSelect: function(clazz) {
			    activeClass = clazz;
			},    	
	    	onAdd: function() {
		        dialog.iframe(
		            '/sys/time/sys_time_area/dialog/createClass.jsp',
		            '新建班次',
		            function(res) {
		                if (!res) { return; }
		                if(!res.fdId) {
		                	res.fdId = 'class-' + _symbol++;
		                }
		                res.type = '2';
		                allClassData.push(res);
		                allClassList.setData(allClassData);
		                
		                toobarResize();
		            },
		            {
		                width: 850,
		                height: 450,
		                params: {
		                    data: null,
		                    colors: getColorsInUse(),
		                    method: 'add'
		                }
		            }
		        );
	    	},
	    	onDelete: function(clazz) {
	    		dialog.confirm('是否确认删除该班次？', function(check) {
	    			if(check) {
						var res = [];
						$.each(allClassData || [], function(_, d) {
							if(d.fdId != clazz.fdId) {
								res.push(d);
							}
						});
						allClassData = res;
						allClassList.setData(allClassData);
						
						activeClass = null;
						
						var _tmpData = [];
						$.each(tmpData || [], function(_, d) {
							try {
								if((d.type == 1 || d.type == 3) && d.clazz.fdId == clazz.fdId) {
								} else {
									_tmpData.push(d);
								}
							} catch(e) {
								_tmpData.push(d);
							}
						});
						
						tmpData = _tmpData;
						calendar.setData({
							data: tmpData
						});
			    		

						toobarResize();
	    			}
	    		});
	    	},
	    	onModify: function(clazz) {
		        dialog.iframe(
		            '/sys/time/sys_time_area/dialog/createClass.jsp',
		            '修改班次',
		            function(res) {
		                if (!res) {
		                    return;
		                }
		                
		                activeClass = null;

		                var i = 0; l = allClassData.length;
		                for(i ; i < l; i++) {
		                	if(allClassData[i].fdId == res.fdId) {
		                		allClassData[i] = res;
		                	}
		                }
		                allClassList.setData(allClassData);
		                
		                $.each(tmpData || [], function(_, d) {
							try {
								if((d.type == 1 || d.type == 3) && d.clazz.fdId == clazz.fdId) {
									d.name = clazz.name;
									d.clazz = $.extend(true, {}, clazz);
								}
							} catch(e) { }
						});
						calendar.setData({
							data: tmpData
						});
		            },
		            {
		                width: 850,
		                height: 450,
		                params: {
		                    data: clazz,
		                    colors: getColorsInUse(),
		                    method: 'modify'
		                }
		            }
		        );
	    	}
	    });

	    
	    // 获取班次数据
	    var ajaxGetAll = $.get('${LUI_ContextPath}/sys/time/sys_time_area/sysTimeArea.do?method=getDefine&fdId=${JsParam.fdId}');
	    var ajaxGetCommonClass = $.get('${LUI_ContextPath}/sys/time/sys_time_common_time/sysTimeCommonTime.do?method=commonTimeById');
	    $.when(ajaxGetAll, ajaxGetCommonClass).then(function(all, cc){
	    	
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
	    	
	    	
	    	var _allClassData = [];
			$.each(all || [], function(idx, c) {
				_allClassData.push({
					fdId: c.clazz.fdId||('class-' + _symbol++), 	    			
	    			type: '2',
	    			name: c.clazz.name|| ('自定义班次'),
	    			color: c.clazz.color || '',
	    			times: c.clazz.times,
                    restStart:c.clazz.restStart,
                    restEnd:c.clazz.restEnd,
                    fdRestStartType:c.clazz.fdRestStartType,
                    fdRestEndType:c.clazz.fdRestEndType,
                    fdTotalDay:c.clazz.fdTotalDay
	    		});
			});    
	    	
	    	var _commonClassData = [];
	    	$.each(cc || [], function(_, c) {
	    		_commonClassData.push({
	    			type: '1',
	    			fdId: c.fdId,
	    			name: c.name || '',
	    			color: c.color,
	    			times: c.times,
                    restStart:c.restStart,
                    restEnd:c.restEnd,
                    fdRestStartType:c.fdRestStartType,
                    fdRestEndType:c.fdRestEndType,
                    fdTotalDay:c.fdTotalDay
	    		});
	    	});
	    	
	    	allClassData = _commonClassData.concat(_allClassData);
			allClassList.setData(allClassData);
	    });
	    
		// TAB切换逻辑
		$('#typeTabs').on('click', '.tab', function() {
			allClassList.noSelect();
			$('#typeTabs').find('.tab').removeClass('active');
			$(this).addClass('active');
			$('.' + $('#typeTabs').attr('data-content-class')).hide();
			$('#' + $(this).attr('data-content-id')).show();
			activeType = $(this).attr('data-type');
			activeClass = null;
			toobarResize();
		});
		
	    // 初始化月历
	    var calendar = new MonthCalendar($('#calendar'), {
	    	year: currentYear,
	    	month: currentMonth,
	    	elements: [],
	    	data: []
	    }, {
			y: '人员',	
			x: '',
			deptName:'部门',
			mode: 'edit',
	    	renderCol: function(col, year, month, date) {
	    		var d = new Date(year, month, date);
	    		$('<div/>').attr('data-type', 'date').text(date).appendTo(col);
	    		
	    		var _d = LunarCalendar.solayDayWithType(d);
				if(_d.type) {
					$('<div/>').attr('data-type', 'day').text(_d.value).appendTo(col);
				} else {
		    		$('<div/>').attr('data-type', 'day').text(WEEKS[d.getDay()]).appendTo(col);
				}
	    		
	    	},
	    	afterRenderCol: function(col, year, month, date) {
	    		
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
	    				break;
	    			}
	    		}
	    		
	    	},
	    	
	    	beforeRenderData: function(cell, d) {
	    		(cell.attr('data-old') == 'true') ? '' : cell.css('background-color', 'transparent');
	    	},
	    	
	    	renderData: function(cell, d) {
				switch(d.type) {
					case 1: 
						cell.css('background-color', d.clazz.color);
						break;
					case 2: 
						cell.attr('data-label', d.name);
						$('<span/>').attr('data-type', 'sign').attr('data-date-type', d.type).appendTo(cell);
						$('<span/>').attr('data-type', 'label').attr('data-date-type', d.type).text('假').appendTo(cell);
						break;
					case 3:
						cell.css('background-color', d.clazz.color);
						$('<span/>').attr('data-type', 'sign').attr('data-date-type', d.type).appendTo(cell);
						break;
					default: break;
				}
	    		
	    	},
	    	selectRange: function(data) {

	    		if(!window.__FLAG_SELECTRAMGE__ && tmpData && tmpData.length > 3000) {
	    			window.__FLAG_SELECTRAMGE__ = true;
	    			dialog.alert('当前排班数据过大，建议使用Excel导入数据');
	    		}
	    		var i = 0, l = data.length, t, d;
	    		for(i; i < l; i++) {
	    			t = data[i];
	    			d = new Date(t.year, t.month, t.date);
					if(d < now) {
						dialog.alert('请勿选择过期时间！');
						return;
					}
	    		}
	    		
	    		var res = [];
	    		
	            switch(activeType) {
	            
	    	        case '1': 
			            var fromWeek = parseInt($('select[name="fromWeek"]').val());
			            var toWeek = parseInt($('select[name="toWeek"]').val());
	    	        	
	    	        	if(!activeClass) {
	    	        		dialog.alert('请选择工作日班次！');
	    	        		return;
	    	        	}
	    	        	
	    	        	if(fromWeek > toWeek) {
	    	        		dialog.alert('默认工作日选择有误！');
	    	        		return;
	    	        	}
	    	        	
	    	        	//{ elementId: 'fdId1', elementName: '1', date: '2019-01-08', type: 1, clazz: {fdId: 'xxx', color: '#FF5500'} },
	    	        	for(i = 0; i < l; i++) {
	    	        		
	    	        		t = data[i];
	    	        		d = new Date(t.year, t.month, t.date);
	    	        		
	    	        		/*
	    	        		if(d.getDay() < fromWeek - 1 || d.getDay() > toWeek - 1) {
	    	        			continue;
	    	        		}
	    	        		*/
	    	        		
	    	        		res.push({
	    	        			elementId: t.elementId,
	    	        			date: d,
	    	        			type: 1,
	    	        			clazz: activeClass,
	    	        			fromWeek: fromWeek,
	    	        			toWeek: toWeek
	    	        		});
	    	        		
	    	        	}
	    	        	
	    	        	tmpData = util.resolveDataInMonth(tmpData.concat(res), true, false, true);
	    	        	calendar.setData({
	    	        		data: tmpData
	    	        	});

	    	        	break;
		            case '2': 

						var dia = null;
						var element = $('<div/>').addClass('calendar-dialog');
						var top = $('<div/>').addClass('calendar-dialog-top').appendTo(element);
						var inputName = $('<input/>').appendTo(top);
						
						if(data.length > 1) {
							//选取日期超过一天则不设置初始值
						} else {
							var first = data[0];
							inputName.val(
								$(
									'[data-year="' + first.year + '"]' +
									'[data-month="' + first.month + '"]' + 
									'[data-date="' + first.date + '"]' + 
									'[data-element-id="' + first.elementId + '"]'
								).attr('data-label')
							)
						}
						
						var bottom = $('<div/>').addClass('calendar-dialog-bottom').appendTo(element);
						var checkBtn = $('<div/>')
										.text('确定')
										.attr('data-type', 'check')	
										.addClass('calendar-dialog-btn')
										.appendTo(bottom);
						checkBtn.click(function() {
							if(!inputName.val()) {
								dialog.alert('请填写名称！');
							} else {
								for(i = 0; i < l; i++) {
			    	        		t = data[i];
			    	        		d = new Date(t.year, t.month, t.date);
			    	        		res.push({
										name: inputName.val(),		    	        			
			    	        			elementId: t.elementId,
			    	        			date: d,
			    	        			type: 2
			    	        		});
			    	        	}
								
								tmpData = util.resolveDataInMonth(tmpData.concat(res), true, false, true);
			    	        	calendar.setData({
			    	        		data: tmpData
			    	        	});
								
								dia.hide();
							}
						});
						
						var closeBtn = $('<div/>')
										.text('取消')
										.attr('data-type', 'close')	
										.addClass('calendar-dialog-btn')
										.appendTo(bottom);
						closeBtn.click(function() {
							dia.hide();
						});
						
						dia = dialog.build({
							config: {
								width: 350,
								height: 108,
								lock: true,
								cache: false,
								content: {
									type : "element",
									elem : element
								},
								title: '设置名称'
							}
						}).show();
						
		            	break;
		            case '3': 
		            	
		            	if(!activeClass) {
	    	        		dialog.alert('请选择补班班次！');
	    	        		return;
	    	        	}
		            	
						for(i = 0; i < l; i++) {
	    	        		t = data[i];
	    	        		d = new Date(t.year, t.month, t.date);
	    	        		res.push({
	    	        			name: activeClass.name,	    	        			
	    	        			elementId: t.elementId,
	    	        			date: d,
	    	        			type: 3,
	    	        			clazz: activeClass
	    	        		});
	    	        	}
						
						tmpData = util.resolveDataInMonth(tmpData.concat(res), true, false, true);
	    	        	calendar.setData({
	    	        		data: tmpData
	    	        	});
	    	        	
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
			},
			contextMenuItems: [
		    	{ 
		    		text: '删除',
		    		click: function(data) {

		    			var res = [], d = new Date(data.year, data.month, data.date), 
		    				i = 0, l = tmpData.length;
		    			for(i; i < l; i++) {
		    				var t = tmpData[i];
		    				if(t.elementId == data.elementId && t.date.getTime() == d.getTime()) {
								// DO NOTHING
		    				} else {
								res.push(t);
							}
		    			}
		    			
		    			tmpData = res;
		    			calendar.setData({
		    				data: tmpData
		    			});
		    			
		    		},
		    		visible: function(data) {
		    			if(new Date(data.year, data.month, data.date) < now) {
		    				return false;
		    			}
		    			
		    			var d = new Date(data.year, data.month, data.date), i = 0, l = tmpData.length;
		    			for(i; i < l; i++) {
							var t = tmpData[i];
							if(t.elementId == data.elementId && t.date.getTime() == d.getTime()) {
								return true;
							}
						}
		    			return false;
		    		}
		    	}
		    ]
	    });
	    window.json_load=dialog.loading();
	    //加载人员
	    $.get('${LUI_ContextPath}/sys/time/sys_time_area/sysTimeArea.do?method=mCalendarJson&fdId=' + '${sysTimeAreaForm.fdId}').then(function(res) {
	    	
	    	var data = [];
	    	$.each(res.data || [], function(_, d) {
	    		data.push($.extend({}, d, {
	    			date: Com_GetDate(d.date)
	    		}));
	    	});
	    	
	    	tmpData = data || [];
	    	
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
		window.handleHolidayChange($('[name="fdHolidayId"]').val())
		
		// 清空全部数据
		window.clearAllCustomData = function() {
			dialog.confirm('是否确认清空全部？', function(check) {
				if(check) {
					tmpData = [];
					calendar.setData({
						data: tmpData
					});
				}
			});
		}
		
		// 导入数据
		window.importData = function() {
			
		}
		
		// 导出Excel
		window.exportDataAsExcel = function() {
			
		}	
		
	    // 保存数据
		window.submit = function() {
			
			var finalData = [];
			var classData=[];
			var clazzData=[];
			$.each(allClassData || [], function(_, dt) {
				if(dt.type=="2"){
					classData.push(dt.fdId);
				}
			});
			$.each(tmpData || [], function(_, d) {
				var t = {};
				if(d.type == 1 || d.type == 3) {
					var clazz = d.clazz;
					var times = [];
					$.each(clazz.times || [], function(_, time) {
						times.push({
							fdWorkStartTime: (new Date('1970/01/01 ' + time.start).getTime()),
	    					fdWorkEndTime: (new Date('1970/01/01 ' + time.end).getTime()),
	    					fdOverTimeType: time.overTimeType,
                            fdStartTime: (new Date('1970/01/01 ' + time.fdStartTime).getTime()),
                            fdOverTime: (new Date('1970/01/01 ' + time.fdOverTime).getTime()),
                            fdEndOverTimeType: time.fdEndOverTimeType
						});
					});
					t['clazz'] = $.extend({}, clazz, {
						times: times
					});
					if(classData.length>0&&clazz.type=="2"){
						$.each(classData || [], function(_, id) {
							if(id==clazz.fdId && clazzData.indexOf(clazz.fdId)==-1){
								clazzData.push(id);
								return false; 
							}
						});
					}
				}
				
				finalData.push($.extend({}, d, {
					date: dateUtil.formatDate(d.date, Com_Parameter.Date_format)
				}, t));
			});
			var data = JSON.stringify(finalData);
			$('[name="orgElementData"]').val(data);
			if(classData.length>0&&classData.length!=clazzData.length){
				dialog.confirm("${lfn:message('sys-time:sysTimeArea.saveConfirm')}",function(value){
					if(value){
						Com_Submit(document.sysTimeAreaForm, 'saveOrgElementData');
					}else{
						return;
					}
				});
			}else{
				Com_Submit(document.sysTimeAreaForm, 'saveOrgElementData');
			}
		}
	    
	    window.redirectToWorkView=function(){
	    	if (Com_Parameter.CloseInfo != null) {
	    		dialog.confirm(Com_Parameter.CloseInfo,
						function(value) {
							if (value) {
								var fdId=$("input[name=fdId]").val();
								var url = '<c:url value="/sys/time/sys_time_area/sysTimeArea.do" />?method=view&fdId=' + fdId + "&forward=scheduleView";
					 			Com_OpenWindow(url,"_self");
							} else
								return;
				});
	    	}
	    }
	    
	});

</script>
