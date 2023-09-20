<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<script>
	
	seajs.use(['lui/calendar'], function (LunarCalendar) {	
		
		window.pageResize = function(){	
			$(window.parent.document).find('iframe#calendarContent').height($(document.body).height());
		};

		$(window).resize(function(){
			pageResize();
		});
		
		// 延迟一秒重新计算容器高度
		setTimeout(function() {
			pageResize();
		}, 1000);
		
		Com_IncludeJSFiles(['resource/libs/bootstrap/bootstrap.min.js']);
		Com_IncludeJSFiles(['resource/libs/yearcalendar/yearcalendar.js']);
		
		if(Com_Parameter.Lang == 'zh-cn' || Com_Parameter.Lang =='zh-hk'){ 
			Com_IncludeJSFiles(['resource/libs/yearcalendar/language/yearcalendar.zh-CN.js']);
		}
		
		var now = new Date();
		now = new Date(now.getFullYear(), now.getMonth(), now.getDate());
		
	    // 日期类型权重顺序
	    var TYPE_WEIGHT = [
	                       1, // 工作日
	                       4, // 法定节日
	                       2, // 假期
	                       5, // 法定节日补班
	                       3  // 补班
	                       ];
		
	    var calendar = $('#calendar');
	    calendar.calendar({ 
	    	language: 'zh-CN',
	        enableRangeSelection: true,
	        maxDate: new Date(now.getFullYear() + 3, 11, 31),
	        /*
	    	enableContextMenu: true,
	        contextMenuItems:[
	            {
	                text: 'Update',
	                click: editEvent
	            },
	            {
	                text: 'Delete',
	                click: deleteEvent
	            }
	        ],
	        dayContextMenu: function(e) {
	        },
	        */
	        style: 'custom',
	        
	        selectRange: function(e) {
	            //console.log({ startDate: e.startDate, endDate: e.endDate });
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
	        	
	        	var _data = data[selectedIndex];
	        	
	        	if(!_data) {
	        		return;
	        	}
	        	
	        	switch(_data.type){
				
					case 1: 
						
						var daySignNode = $('<div class="day-sign" data-type="1"></div>').insertBefore(ctx);
						daySignNode.css('background-color', _data.color);
						_data.color && ctx.css('color', 'white');
						
						break;
					case 2:
					case 4:
						$('<div class="day-sign" data-type="2"></div>').insertBefore(ctx);
						_data.name && (ctx.parent().find('.day-lunar span').text(data.name));
						break;
					case 3:
					case 5: 
						var daySignNode = $('<div class="day-sign" data-type="1"></div>').insertBefore(ctx);
						daySignNode.css('background-color', _data.color);
						
						$('<div class="day-sign" data-type="3"></div>').insertBefore(ctx);
						
						_data.color && ctx.css('color', 'white');
						
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
	    
	    // 获取排班数据
	    $.get('${LUI_ContextPath}/sys/time/sys_time_area/sysTimeArea.do?method=calendarJson&fdId=${JsParam.fdId}').then(function(res){
			
	    	var dataSource = [];
	    	$.each(res, function(_, data) {
	    		dataSource.push($.extend({}, data, {
	    			startDate: Com_GetDate(data.fromDate),
	    			endDate: data.toDate ? Com_GetDate(data.toDate) : new Date(now.getFullYear() + 3, 11, 31),
	    			color: data.color || ''
	    		}));
	    	});

	    	calendar.data('calendar').setDataSource(dataSource);
			
			pageResize();
	    });
	    
	    // 获取班次数据
	    //<div class="tag">
		//	<span class="tag-flag" style="background: ${work.color};"></span>
		//	<span class="tag-label">${work.time}</span>
		//</div>	
		
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
	    				+' ' + 
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
	    				+' '+
	    				endStr 
	    			);
	    		});
	    		clazz['times'] = times.join('; ');
	    		createClassNode(clazz).appendTo(classesNode);
	    	});
	    	
	    }, function(err) {
	    });
	    
		
	});

</script>
