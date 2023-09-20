<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<script>
	
	seajs.use(['lui/calendar', 'lui/topic', 'lui/dialog', 'lui/dateUtil', 'sys/time/sys_time_area/resource/js/classlist',
	           'sys/time/sys_time_area/resource/js/util'],
			function (LunarCalendar, topic, dialog, dateUtil, ClassList, dataUtil) {	
		
		//////////////////////////////////////////////////////////////////
		// 动态调整容器高度
		//////////////////////////////////////////////////////////////////
		window.pageResize = function(){	
			$(window.parent.document).find('iframe#calendarContent').height($(document.body).height());
		};
		$(window).resize(function(){
			pageResize();
		});
		
		window.toobarResize = function() {
			$('.main').css('padding-top', $('.toolbar').height());
		}
		
		setTimeout(function() {
			toobarResize();
		}, 1000);
		
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
		
		var workData = [];
		var vacationData = [];
		var patchWorkData = [];
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
						calendar.data('calendar').setDataSource(tmpData.concat(holidayData));
			    		
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
						calendar.data('calendar').setDataSource(tmpData.concat(holidayData));
		                
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
		
		// 年历展示逻辑
		Com_IncludeJSFiles(['resource/libs/bootstrap/bootstrap.min.js']);
		Com_IncludeJSFiles(['resource/libs/yearcalendar/yearcalendar.js']);
		if(Com_Parameter.Lang == 'zh-cn' || Com_Parameter.Lang =='zh-hk') {
			Com_IncludeJSFiles(['resource/libs/yearcalendar/language/yearcalendar.zh-CN.js']);
		}
		
	    // 日期类型权重顺序
	    var TYPE_WEIGHT = [
	                       1, // 工作日
	                       4, // 法定节日
	                       2, // 假期
	                       5, // 法定节日补班
	                       3  // 补班
	                       ];
		
	    // 初始化年历
	    var calendar = $('#calendar');
	    
	    calendar.calendar({ 
	    	language: 'zh-CN',
	        enableRangeSelection: true,
	    	enableContextMenu: true,
	    	maxDate: new Date(now.getFullYear() + 3, 11, 31),
	        contextMenuItems:[
	            {
	                text: '编辑',
	                click: function(d) {
	                	
						var dia = null;
						var element = $('<div/>').addClass('calendar-dialog');
						var top = $('<div/>').addClass('calendar-dialog-top').appendTo(element);
						var inputName = $('<input/>').val(d.name || '').appendTo(top);
						var bottom = $('<div/>').addClass('calendar-dialog-bottom').appendTo(element);
						var checkBtn = $('<div/>')
										.text('确定')
										.attr('data-type', 'check')	
										.addClass('calendar-dialog-btn')
										.appendTo(bottom);
						checkBtn.click(function() {
							d.name = inputName.val();
							calendar.data('calendar').setDataSource(tmpData.concat(holidayData));
							dia.hide();
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
								title: '修改名称'
							}
						}).show();
	                },
	                visible: function(d) {
	                	
	                	if(d.startDate < now) {
	                		return false;
	                	}
	                	
	                	if(d.type == 2) {
	                		return true;
	                	}
	                	return false;
	                }
	            },
	            {
	                text: '删除',
	                click: function(d) {
	                	var t = [];
	                	$.each(tmpData || [], function(_, _d) {
	                		
	                		if(_d.fdId != d.fdId) {
	                			t.push(_d);
	                		}
	                		
	                	});
	                	tmpData = t;
	                	calendar.data('calendar').setDataSource(tmpData.concat(holidayData));
	                },
	                visible: function(d) {
	                	if(d.startDate < now) {
	                		return false;
	                	}
	                	
	                	if(d.type == 1 || d.type == 2 || d.type == 3) {
	                		return true;
	                	}
	                	return false;
	                }
	            }
	        ],
	        /*
	        dayContextMenu: function(e) {
	        	//console.log(e);
	        },
	        */
	        style: 'custom',
	        selectRange: function(e) {
	            //console.log({ startDate: e.startDate, endDate: e.endDate });
				
	            if(e.startDate < now) {
	            	dialog.alert('请勿选择过期时间！');
	            	return;
	            }

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
	    	        	
   	        			tmpData.push({
	    	        		name: activeClass.name,
	    	        		startDate: e.startDate,
	    	        		endDate: e.endDate,
	    	        		fromWeek: fromWeek,
	    	        		toWeek: toWeek,
	    	        		type: 1,
	    	        		clazz: $.extend(true, {}, activeClass)
	    	        	});
	    	        	
	    	        	tmpData = dataUtil.resolveData(tmpData, true, false, true);
	    	        	calendar.data('calendar').setDataSource(tmpData.concat(holidayData));
	    	        	
	    	        	break;
		            case '2': 

						var dia = null;
						var element = $('<div/>').addClass('calendar-dialog');
						var top = $('<div/>').addClass('calendar-dialog-top').appendTo(element);
						var inputName = $('<input/>').val('假期').appendTo(top);
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
				            	tmpData.push({
									name: inputName.val(),		            		
			    	        		startDate: e.startDate,
			    	        		endDate: e.endDate,
			    	        		type: 2,
			    	        		clazz: {times:null}
			    	        	});
			    	        	
				            	tmpData = dataUtil.resolveData(tmpData, true, false, true);
			    	        	calendar.data('calendar').setDataSource(tmpData.concat(holidayData));
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
		            	
		            	tmpData.push({
	    	        		name: activeClass.name,
	    	        		startDate: e.startDate,
	    	        		endDate: e.endDate,
	    	        		type: 3,
	    	        		clazz: $.extend(true, {}, activeClass)
	    	        	});
	    	        	
		            	tmpData = dataUtil.resolveData(tmpData, true, false, true);
	    	        	calendar.data('calendar').setDataSource(tmpData.concat(holidayData));
		            	break;
	            	default: break;
	            }
	            
	        },
	        
	        customDayRenderer: function(cell, d) {
	        	
	        	if(d < now) {
	        		cell.parent().addClass('late');
	        	}
	        	
	            if(Com_Parameter.Lang == 'zh-cn' || Com_Parameter.Lang =='zh-hk'){  
	            	$('<div/>').addClass('day-lunar').append($('<span/>').text(LunarCalendar.solarDay(d))).insertAfter(cell);
	            }
	        },
	        
	        customDataSourceRenderer: function(ctx, currentDate, data, events) {
	        	
	        	//console.log(ctx, currentDate, data);
	        	
	        	var selectedIndex = -1;
	        	var t = -1;
	        	
	        	$.each(data, function(idx, item) {
	        		
	        		if(item.fromWeek && item.toWeek &&
	        				(currentDate.getDay() < item.fromWeek - 1 || currentDate.getDay() > item.toWeek - 1)) {
	        			return;
	        		}
	        		
					if($.inArray(item.type, TYPE_WEIGHT) >= $.inArray(t, TYPE_WEIGHT)) {
						selectedIndex = idx;
						t = item.type;
					}			        		
	        	});
	        	
	        	data = data[selectedIndex];
	        	
	        	if(!data) {
	        		return;
	        	}
	        	
	        	switch(data.type){
				
					case 1: 
						var daySignNode = $('<div class="day-sign" data-type="1"></div>').insertBefore(ctx);
						daySignNode.css('background-color', data.clazz.color);
						ctx.css('color', 'white');
						
						break;
					case 2:
					case 4:
						$('<div class="day-sign" data-type="2"></div>').insertBefore(ctx);
						data.name && (ctx.parent().find('.day-lunar span').text(data.name));
						break;
					case 3:
						var daySignNode = $('<div class="day-sign" data-type="1"></div>').insertBefore(ctx);
						daySignNode.css('background-color', data.clazz.color);
						$('<div class="day-sign" data-type="3"></div>').insertBefore(ctx);
						ctx.css('color', 'white');
						
						break;
					case 5: 
						$('<div class="day-sign" data-type="3"></div>').insertBefore(ctx);
						break;
					default: break;
			
			
				}
							        	
	        },
	        dataSource: [
	            /*
	            {
	                name: 'Google I/O',
	                startDate: new Date(currentYear, 4, 28),
	                endDate: new Date(currentYear, 4, 28),
	                ...
	            }
	            */
	        ]
	    });
	    
	 	// 获取排班数据初始化年历展示
	    var ajaxGetWorkData = $.get(
	        '${LUI_ContextPath}/sys/time/sys_time_area/sysTimeArea.do?method=getWorkTime&fdId=${JsParam.fdId}'
	    );
	    var ajaxGetPatchWorkData = $.get(
	        '${LUI_ContextPath}/sys/time/sys_time_area/sysTimeArea.do?method=getPatchWork&fdId=${JsParam.fdId}'
	    );
	    var ajaxGetVacation = $.get(
	        '${LUI_ContextPath}/sys/time/sys_time_area/sysTimeArea.do?method=getVacation&fdId=${JsParam.fdId}'
	    );
	    var ajaxGetHolidayData = $.get('${LUI_ContextPath}/sys/time/sys_time_area/sysTimeArea.do?method=getHoliday&fdId=${JsParam.fdId}');

	    $.when(
	        ajaxGetWorkData,
	        ajaxGetPatchWorkData,
	        ajaxGetVacation,
	        ajaxGetHolidayData
	    ).then(function(_workData, _patchWorkData, _vacationData, _holidayData) {

	    	if(Object.prototype.toString.call(_workData[0]) == '[object Array]') {
		    	_workData = _workData[0] || [];
	    	} else {
	    		_workData = [];    		
	    	}
	    	$.each(_workData || [], function(_, d) {
	    		workData.push($.extend(true, {}, d, {
					name: d.name || '工作日',
	    			startDate: Com_GetDate(d.startDate),
	                endDate: d.endDate ? Com_GetDate(d.endDate) : new Date(now.getFullYear() + 3, 11, 31),
	                fromWeek: d.fromWeek,
	                toWeek: d.toWeek,
	                type: 1,
	                clazz: d.clazz.type == '1' ? $.extend(true, {}, d.clazz, {
	                	type: '1'
	                }) : $.extend(true, {}, d.clazz, {
	                	fdId: d.clazz.fdId||('class-' + _symbol++), 
	                	type: '2'
	                })
	    		}));
	    	});
	    	
	    	if(Object.prototype.toString.call(_patchWorkData[0]) == '[object Array]') {
		    	_patchWorkData = _patchWorkData[0] || [];
	    	} else {
	    		_patchWorkData = [];		
	    	}
	    	$.each(_patchWorkData || [], function(_, d) {
	    		patchWorkData.push($.extend(true, {}, {
	    			name: d.name || '补班',
	    			startDate: Com_GetDate(d.startDate),
	                endDate: Com_GetDate(d.endDate),
	                type: 3,
	                clazz: d.clazz.type == '1' ? $.extend(true, {}, d.clazz, {
	                	type: '1'
	                }) : $.extend(true, {}, d.clazz, {
	                	fdId: d.clazz.fdId||('class-' + _symbol++), 
	                	type: '2'
	                })
	    		}));
	    	});
	    	
	    	if(Object.prototype.toString.call(_vacationData[0]) == '[object Array]') {
		    	_vacationData = _vacationData[0] || [];
	    	} else {
	    		_vacationData = [];    		
	    	}
	    	$.each(_vacationData || [], function(_, d) {
	    		var _startDate = Com_GetDate(d.startDate);
	    		var _endDate = Com_GetDate(d.endDate);
	    		vacationData.push($.extend(true, {}, {
	    			name: d.name || '',
	    			startDate: new Date(_startDate.getFullYear(), _startDate.getMonth(), _startDate.getDate()),
	                endDate: new Date(_endDate.getFullYear(), _endDate.getMonth(), _endDate.getDate()),
	                type: 2,
	                clazz: {times:null}
	    		}));
	    	});
	    	
	    	if(Object.prototype.toString.call(_holidayData[0]) == '[object Array]') {
		        _holidayData = _holidayData[0] || [];
	    	} else {
	    		_holidayData = [];  		
	    	}
	        $.each(_holidayData, function(_, d) {
	            holidayData.push($.extend(true, {}, d, {
	            	startDate: Com_GetDate(d.startDate),
	                endDate: Com_GetDate(d.endDate),
	                type: 4
	            }));

	            $.each(d.patchDay || [], function(__, _d) {
	                holidayData.push($.extend(true, {}, {
	                    startDate: Com_GetDate(_d),
	                    endDate: Com_GetDate(_d),
	                    type: 5
	                }));
	            });
	        });

	        var t = [].concat(workData).concat(patchWorkData).concat(vacationData);

	        srcData = t.slice(0);
	        
	        t = dataUtil.resolveData(t, true, true);
	        tmpData = t.slice(0);
	        
	        calendar.data('calendar').setDataSource(tmpData.concat(holidayData));
	    });
	    
		// 修改所属节假日
		window.handleHolidayChange = function(holidayId) {
			
			if(!holidayId) {
				calendar.data('calendar').setDataSource(tmpData.concat([]));
				return;
			}
			
			$.get('${LUI_ContextPath}/sys/time/sys_time_holiday/sysTimeHoliday.do?method=getHolidayById&fdId=' + holidayId).then(function(data) {

				var res = [];
				
				$.each(data.sysTimeHolidayDetail || [], function(_, d) {
					
					res.push($.extend(true, {}, {
						startDate: Com_GetDate(d.startDay),
						endDate: Com_GetDate(d.endDay),
						type: 4
					}));
					
					var t = (d.patchDay || '').split(',');
					$.each(t || [], function(__, _d) {
						res.push($.extend(true, {}, {
							startDate: Com_GetDate(_d),
							endDate: Com_GetDate(_d),
							type: 5
						}));
					});
					
				});
				
				holidayData = res;
				calendar.data('calendar').setDataSource(tmpData.concat(holidayData));
				
			});
		}
		// 清空全部数据
		window.clearAllCustomData = function() {
			
			dialog.confirm('是否确认清空全部？', function(check) {
				if(check) {
					tmpData = [];
					calendar.data('calendar').setDataSource(tmpData.concat(holidayData));
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
			
	    	var loading = dialog.loading();

	    	var fdId = '${JsParam.fdId}';
	    	var fdHolidayId = $('select[name="fdHolidayId"]').val();

			var copyData = $.extend(true, [], tmpData);
			
	    	var finalData = [];
	    	var classData=[];
			var clazzData=[];
			$.each(allClassData || [], function(_, dt) {
				if(dt.type=="2"){
					classData.push(dt.fdId);
				}
			});
	    	$.each(copyData || [], function(_, d) {
	    		
	    		var t = $.extend(true, {}, d, {
	    			startDate: dateUtil.formatDate(d.startDate, Com_Parameter.Date_format),
	    			endDate: dateUtil.formatDate(d.endDate, Com_Parameter.Date_format)
	    		});
	    		
	    		if(t.type == 1 || t.type == 3) {
	    			var times = [];
	    			var clazz=t.clazz;
	    			$.each(t.clazz.times || [], function(_, time) {
	    				times.push({
	    					fdWorkStartTime: (new Date('1970/01/01 ' + time.start).getTime()),
	    					fdWorkEndTime: (new Date('1970/01/01 ' + time.end).getTime()),
	    					fdOverTimeType: time.overTimeType,
                            fdStartTime: (new Date('1970/01/01 ' + time.fdStartTime).getTime()),
                            fdOverTime: (new Date('1970/01/01 ' + time.fdOverTime).getTime()),
                            fdEndOverTimeType: time.fdEndOverTimeType
	    				});
	    			});
	    			t.clazz.times = times;
	    			if(classData.length>0&&clazz.type=="2"){
						$.each(classData || [], function(_, id) {
							if(id==clazz.fdId && clazzData.indexOf(clazz.fdId)==-1){
								clazzData.push(id);
								return false; 
							}
						});
					}
	    		}
	    		finalData.push(t);
	    	});
	    	if(classData.length>0&&classData.length!=clazzData.length){
				dialog.confirm("${lfn:message('sys-time:sysTimeArea.saveConfirm')}",function(value){
					if(!value){
						loading.hide();
						return;
					}else{
						saveData(fdId,finalData,fdHolidayId,loading);
					}
				});
			}else{
				saveData(fdId,finalData,fdHolidayId,loading);
			}
		}
		window.saveData=function(fdId,finalData,fdHolidayId,loading){
	    	$.post('${LUI_ContextPath}/sys/time/sys_time_area/sysTimeArea.do?method=editCalendarJson', {
				fdId: fdId,
				data: JSON.stringify(finalData),
				fdHolidayId: fdHolidayId
			}, function(res) {
				if(loading){
					loading.hide();
				}
				try {
					if(res.success) {
						dialog.success('保存成功', null, function() {
							window.close();
						});
					} else {
						dialog.failure('保存失败');
					}
				} catch(e) {
					dialog.failure('保存失败');
				}					
			}, 'json');
	    }
	});

</script>
