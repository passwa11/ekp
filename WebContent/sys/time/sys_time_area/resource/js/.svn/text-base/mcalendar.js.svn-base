define(function(require, exports, module) {

	var $ = require('lui/jquery');
	var dateUtil = require('lui/dateUtil');
	var dialog = require('lui/dialog');
	
	function getDays(year, month) {
		month += 1;
		switch(month) {
			case 2:
				if(year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
					return 29;
				} else {
					return 28;
				}
			case 4:
			case 6:
			case 9:
			case 11:
				return 30;
			default: 
				return 31;
		}
	}
	
	var MonthCalendar = function(element, data, options) {
		
		this.element = $(element);

		var now = this.now = new Date();
		this.nowYear = now.getFullYear();
		this.nowMonth = now.getMonth();
		this.nowDate = now.getDate();
		
		this.defaultData = {
			/*elements数据结构
			[
				{
					fdId: 'xxx',
					name: 'xxx
				}
			]
			*/				
			elements: [],

			/*data数据结构
			[
			 	{
				  	elementId,
				  	elementName,
				  	date, //Date对象
				  	type,
				  	clazz: {
					  	fdId,
				    	name
				  	}
			    }
			]
			 */
			data: [],
			year: now.getFullYear(),
			month: now.getMonth()
		};
		this.data = $.extend({}, this.defaultData, data);
		this.cacheData();
		
		this.defaultOptions = {
			baseClass: 'lui-mcalendar',
			mode: 'view',
			width: 'auto',
			maxWidth: 'unset',
			height: 'auto',
			maxHeight: 'unset',
			colWidth: 36,
			fitColWidth: true, //宽度自适应，若为true则colWidth为最小宽度
			colHeight: 48,
			x: 'X',
			y: 'Y',
			beforeRenderCol: function(col, year, month, date, element) {
				//DO NOTHING
			},
	    	renderCol: function(col, year, month, date, element) {
	    		var d = new Date(year, month, date);
	    		$('<div/>').addClass('').attr('data-type', 'date').text(date).appendTo(col);
	    		$('<div/>').attr('data-type', 'day').text(WEEKS[d.getDay()]).appendTo(col);
	    	},
	    	afterRenderCol: function(col, year, month, date, element) {
	    		//DO NOTHING
	    	},
			beforeRenderRow: function(row, data, element) {
				//DO NOTHING
			},
			renderRow: function(row, data, element) {
				var deptName = data.deptName ? data.deptName:'&nbsp;';
				$('<div/>').attr('data-type','dept').attr('title',deptName).css('display', 'inline-block').css('width', this.colWidth * 3).html(deptName).appendTo(row);
				$('<div/>').attr('data-type','person').attr('title',data.name).css('display', 'inline-block').css('width', this.colWidth * 3).text(data.name).appendTo(row);
				//row.text(data.name);
			},
			afterRenderRow: function(row, data, element) {
				//DO NOTHING
			},
			beforeRenderData: function(cell, data, element) {
				//DO NOTHING
			},
			renderData: function(cell, data, element) {
				//TODO
			},
			afterRenderData: function(cell, data, element) {
				//DO NOTHING
			},
			selectRange: function(data) {
				//DO NOTHING
			},
			showContextMenu: true,
			contextMenuTitle: null, // NULL OR STRING OR FUNCTION function(cell, data) { return 'title'; },
			contextMenuItems: [
                /*
	            {
	                text: '删除',
	                click: function(data) {
						//DO SOMETHING
	                },
	                visible: function(data) {
	                	return true;
	                }
	            }
	            */
	        ]
			
		};
		this.options = $.extend({}, this.defaultOptions, options);
		
		this.init();
		this.render();
		this.bindEvents();
	};

	MonthCalendar.prototype = {
		init: function() {
			this.element.addClass(this.options.baseClass);
			this.element.attr('data-mode', this.options.mode);
		},
		
		cacheData: function() {
			var elementIds = this.elementIds = [];
			$.each(this.data.elements || [], function(_, element) {
				elementIds.push(element.fdId);
			});
		},
		
		refresh: function() {
			this.render();
		},
		
		render: function() {
			this.element.empty();
			this.calendar = null;
			this.renderCalendar();
			this.renderData();
		},
		
		renderCalendar: function() {
			
			var self = this;
			
			var calendar = this.calendar = this.element;
			calendar.css('width', this.options.width).css('max-width', this.options.maxWidth);
			
			/**
			 * 渲染列
			 */
			var calendarDept = this.calendarT = $('<div/>').addClass(this.options.baseClass + '-t').appendTo(calendar);
			var calendarT = this.calendarT = $('<div/>').addClass(this.options.baseClass + '-t')
													.appendTo(calendar);
			var calendarTL = this.calendarTL = $('<div/>').addClass(this.options.baseClass + '-t-l')
													.appendTo(calendarT);
			//部门
			$('<div/>').addClass(this.options.baseClass + '-col')
			.css('width', this.options.colWidth * 3)
			.css('height', this.options.colHeight)
			.append($('<span/>').attr('data-type', 'y').text(this.options.deptName))
			.appendTo(calendarTL);
			//人员
			$('<div/>').addClass(this.options.baseClass + '-col')
			.css('width', this.options.colWidth * 3)
			.css('height', this.options.colHeight)
			.append($('<span/>').attr('data-type', 'y').text(this.options.y))
			.append($('<span/>').attr('data-type', 'x').text(this.options.x))
			.appendTo(calendarTL);
			
			var calendarTR = this.calendarTR = $('<div/>').addClass(this.options.baseClass + '-t-r')
													.css('margin-left', this.options.colWidth * 3 * 2)
													.appendTo(calendarT);
			var calendarCols = this.calendarCols = $('<div/>').addClass(this.options.baseClass + '-cols')
													.appendTo(calendarTR);

			var year = this.data.year;
			var month = this.data.month;
			var days = getDays(year, month);
			
			var nowDay = this.now.getDate();
			
			var colWidth = this.options.colWidth;
			if(this.options.fitColWidth) {
				if(colWidth * (days + 3) < this.element.width()) {
					colWidth = (this.element.width() - colWidth * 3) / days
				}
			}
			
			for(var i = 1; i <= days; i++) {
				var col = $('<div/>').addClass(this.options.baseClass + '-col')
							.css('width', colWidth)
							.css('height', this.options.colHeight)
							.attr('data-type', 'col')
							.attr('data-year', year)
							.attr('data-month', month)
							.attr('data-date', i)
							.appendTo(calendarCols);
				
				var dd = new Date(year, month, i);
				if(dd < new Date(this.nowYear, this.nowMonth, this.nowDate)) {
					col.attr('data-old', 'true').addClass('old');
					
				}
				
				this.options.beforeRenderCol(col, year, month, i, this.element);
				this.options.renderCol(col, year, month, i, this.element);
				
				(function(_col, _year, _month, _i, _element) {
					setTimeout(function() {
						self.options.afterRenderCol(_col, _year, _month, _i, _element);
					});
				})(col, year, month, i, this.element);
				
			}
			
			/**
			 * 渲染行
			 */
			var calendarB = this.calendarB = $('<div/>').addClass(this.options.baseClass + '-b')
												.appendTo(calendar);
			var calendarBL = this.calendarBL = $('<div/>').addClass(this.options.baseClass + '-b-l')
												.appendTo(calendarB);
			var calendarRows = this.calendarRows = $('<div/>').addClass(this.options.baseClass + '-rows')
												.appendTo(calendarBL);
//			debugger;
			$.each(this.data.elements, function(_, d) {
				var row = $('<div/>').addClass(self.options.baseClass + '-row')
							.css('width', self.options.colWidth * 3*2)
							.css('height', self.options.colHeight)
							.attr('data-type', 'row')
							.attr('data-element-id',d.fdId)
							.appendTo(calendarRows);
				self.options.beforeRenderRow(row, d, self.element);
				self.options.renderRow(row, d, self.element);
				
				(function(_row, _d, _element) {
					setTimeout(function() {
						self.options.afterRenderRow(_row, _d, _element);
					});
				})(row, d, self.element);
			});
			
			/**
			 * 渲染日历数据格子
			 */
			var calendarBR = this.calendarBR = $('<div/>').addClass(this.options.baseClass + '-b-r')
												.css('height', this.options.height)
												.css('max-height', this.options.maxHeight)
												.css('margin-left', this.options.colWidth * 3 *2)
												.appendTo(calendarB);
			var calendarMain = this.calendarMain = $('<div/>').addClass(this.options.baseClass + '-main')
													.css('width', (days * colWidth))
													.appendTo(calendarBR);
			$.each(this.data.elements, function(_, d) {
				
				var mainRow = $('<div/>')
								.css('height', self.options.colHeight)
								.attr('data-type', 'main-row')
								.addClass(self.options.baseClass + '-main-row')
								.appendTo(calendarMain);
				
				for(var i = 1; i <= days; i++) {
					var mainCol = $('<div/>')
									.addClass(self.options.baseClass + '-main-col')
									.css('width', colWidth)
									.css('height', self.options.colHeight)
									.attr('data-type', 'main-col')
									.attr('data-year', year)
									.attr('data-month', month)
									.attr('data-date', i)
									.attr('data-element-id', d.fdId)
									.attr('data-element-name', d.name)
									.appendTo(mainRow);
					
					var dd = new Date(year, month, i);
					if(dd < new Date(self.nowYear, self.nowMonth, self.nowDate)) {
						mainCol.attr('data-old', 'true').addClass('old');
					}
				}
			});
			
			/**
			 * 滚动联动
			 */
			
			calendarBR.scroll(function() {
				var left = $(this).scrollLeft();
				var top = $(this).scrollTop();

				calendarCols.css('left', -left);
				calendarRows.css('top', -top);
	        });
		},
		
		renderData: function() {
			var self = this;
			if(!this.calendar) {
				return;
			}
			
			this.calendar.find('.' + this.options.baseClass + '-main-col').each(function() {
				$(this).empty()
				self.options.beforeRenderData($(this), null, self.element);
			});
			var timeData=[];
			$.each(this.data.data || [], function(_, d) {
				timeData.push(d);
			});
			var initTimeData=self.timeChunk(timeData,function(d) {
				self._renderData(d);
			},100);
			initTimeData();
		},
		
		_renderData: function(data) {
			
			var self = this;
			
			/*
				{
				  	elementId,
				  	elementName,
				  	date,
				  	type,
				  	clazz: {
					  	fdId,
				    	name
				  	}
			    }
			 */
			if(!this.calendar) {
				return;
			}
			
			var date = data.date;
			var year = date.getFullYear();
			var month = date.getMonth();
			var date = date.getDate();
			
			var cell = this.calendar.find(
							'.' + this.options.baseClass
							+ '-main-col[data-year="' + year + '"]'
							+ '[data-month="' + month + '"]'
							+ '[data-date="' + date + '"]'
							+ '[data-element-id="' + data.elementId + '"]'
						);
			
			if(cell.get(0)) {
				cell.empty();
				
				cell.attr('data-has-data', 'true');
				
				this.options.beforeRenderData(cell, data, this.element);
				this.options.renderData(cell, data, this.element);
				
				(function(_cell, _data, _element) {
					setTimeout(function() {
						self.options.afterRenderData(_cell, _data, _element);
					});
				})(cell, data, this.element);
			}
			
		},
		
		setData: function(data, reRender) {
			var del_load=null;
			if(this.data&&this.data.elements&&this.data.elements.length>0){
				del_load=dialog.loading();
			}
			this.data = $.extend(this.data, data);
//			debugger;
			this.cacheData();
			
			if(reRender) {
				this.render();
			} else {
				this.renderData();
			}
			if(del_load != null){
				del_load.hide();
			}
		},
		
		setOptions: function(options) {
			this.options = $.extend(this.options, options);
			this.render();
		},
		
		getRange: function() {
			
			var self = this;
			
			var res = [];
			
			if(this._start && this._end) {
				
				var t = null;
				
				var startDate = parseInt(this._start.attr('data-date'));
				var endDate = parseInt(this._end.attr('data-date'));
				if(startDate > endDate) {
					t = startDate;
					startDate = endDate;
					endDate = t;
				}
				
				var startElementId = this._start.attr('data-element-id');
				var endElementId = this._end.attr('data-element-id');
				
				var startIndex = $.inArray(startElementId, this.elementIds || []);
				var endIndex = $.inArray(endElementId, this.elementIds || []);
				if(startIndex > endIndex) {
					t = startIndex;
					startIndex = endIndex;
					endIndex = t;
				}
				
				var elements = this.elementIds.slice(startIndex, endIndex + 1);
				
				$.each(elements || [], function(_, fdId) {
					for(var i = startDate; i <= endDate; i++) {
						res.push({
							elementId: fdId,
							year: self.data.year,
							month: self.data.month,
							date: i
						});
					}
				});
				
			}
			
			return res;
		},
		
		selectRange: function() {
		
			var self = this;
			
			this.element.find('.range').removeClass('range');
			this.element.find('.range-start').removeClass('range-start');
			this.element.find('.range-end').removeClass('range-end');
			
			var range = this.getRange();
			this._start && this._start.addClass('range-start');
			this._end && this._end.addClass('range-end');
			
			$.each(range || [], function(_, r) {
				self.element.find(
						'.' + self.options.baseClass + '-main-col' 
						+ '[data-element-id="' + r.elementId + '"]'
						+ '[data-date="' + r.date + '"]'
				).addClass('range');
			});
			
			
		},
		
		bindSelectEvent: function() {
			var self = this;
			
			this.element.on('mousedown', function(e) {
				
				//不监听鼠标右键
				if(e.which == 3) {
					return;
				}
				
				var _start = $(e.target).closest('.' + self.options.baseClass + '-main-col');
				if(_start.get(0)) {
					self._start = _start;
					self._end = _start;
				} else {
					self._start = null;
					self._end = null;
				}
				self.selectRange();
			});
			this.element.on('mousemove', function(e) {
				
				//非鼠标左键状态不执行
				if(e.which != 1) {
					return;
				}
				
				if(self._start) {
					var _end = $(e.target).closest('.' + self.options.baseClass + '-main-col');
					if(_end.get(0)) {
						self._end = _end;
					}
					self.selectRange();
					self._timer = null;
				}
				
			});
			$(document).on('mouseup', function(e) {
				//不监听鼠标右键
				if(e.which == 3) {
					return;
				}
				
				if(self._start) {
					var _end = $(e.target).closest('.' + self.options.baseClass + '-main-col');
					if(_end.get(0) && self.element.has(_end)) {
						self.options.selectRange(self.getRange());
					}
				}
				
				self._start = null;
				self._end = null;
				self.selectRange();
			});
		},
		
		
		_buildContextMenu: function(e, cell, showItems) {
			
			var self = this;
			this._hideContextMenu();
			
			var elementId = cell.attr('data-element-id');
			var year = parseInt(cell.attr('data-year'));
			var month = parseInt(cell.attr('data-month'));
			var date = parseInt(cell.attr('data-date'));
			
			var data = {
				elementId: elementId,
				year: year,
				month: month,
				date: date	
			};
			
			var contextMenu = this._contextMenu = $('<div/>').addClass(this.options.baseClass + '-contextmenu')
									.css('display', 'none')
									.appendTo($(document.body));
			
			if(e.pageX + 156 > window.innerWidth) {
				contextMenu.css('left', e.pageX - 156);
			} else {
				contextMenu.css('left', e.pageX);
			}
			contextMenu.css('top', e.pageY);
			
			var typeOfGetTitle = Object.prototype.toString.call(this.options.contextMenuTitle);
			var contextMenuTitle = '';
			if(typeOfGetTitle == '[object String]') {
				contextMenuTitle = this.options.contextMenuTitle;
			} else if(typeOfGetTitle == '[object Function]') {
				contextMenuTitle = this.options.contextMenuTitle(cell, data);
			} else {
				//DO NOTHING
			}
			if(contextMenuTitle) {
				$('<div/>').addClass(this.options.baseClass + '-contextmenu-item')
					.attr('data-type', 'menu-title')
					.text(contextMenuTitle)
					.appendTo(contextMenu);
			}
			
			if(showItems) {
				$.each(this.options.contextMenuItems || [], function(_, d) {
					var menuItem = $('<div/>').addClass(self.options.baseClass + '-contextmenu-item')
									.attr('data-type', 'menu-item')
									.text(d.text)
									.appendTo(contextMenu);
	
					menuItem.click(function() {
						d.click(data);
					});
					
					if(d.visible && !d.visible(data)) {
						menuItem.hide();
					}
					
				});
			}
			contextMenu.fadeIn(300);
		},
		_hideContextMenu: function() {
			if(this._contextMenu) {
				this._contextMenu.remove();
				this._contextMenu = null;
				
			}
		},
		
		bindContextMenuEvent: function() {
			var self = this;
			
			this.element.contextmenu(function(e) {
				e.preventDefault();
				e.stopPropagation();
				
				var target = $(e.target).closest('.' + self.options.baseClass + '-main-col');
				if(target.get(0) && self.options.showContextMenu) {
					self._buildContextMenu(e, target, self.options.mode == 'edit');
				}
			});
			
			//隐藏菜单
			$(document).click(function() {
				self._hideContextMenu();
			});
		},
		
		bindEvents: function() {
			//编辑模式下鼠标选取动作
			if(this.options.mode == 'edit') {
				this.bindSelectEvent();
			}
			this.bindContextMenuEvent();
		},
		timeChunk:function(ary,callback,count){
		    var t;//定时器
		    //开始执行函数    
		    function start(){
		        for(var i=0;i<Math.min(count||1,ary.length);i++){
		            callback(ary.shift());
		        }
		    }
		    return function(){
		        t=setInterval(function(){
		            if(ary.length===0){
		                return clearInterval(t);
		            }
		            start();
		        },50);
		    }
		}
		
	};
	
	MonthCalendar.showTip = function(e, options) {
		
		var defaultOptions = {
			duration: 1000,
			text: ''
		};
		
		options = $.extend({}, defaultOptions, options);
		
		if(window._MCALENDAR_TIPNODE_) {
			window._MCALENDAR_TIPNODE_.remove();
			window._MCALENDAR_TIPNODE_ = null;
		}
		
		if(window._MCALENDAR_TIPNODE_TIMER_) {
			clearTimeout(window._MCALENDAR_TIPNODE_TIMER_);
			window._MCALENDAR_TIPNODE_TIMER_ = null;
		}
		
		
		var tipNode = window._MCALENDAR_TIPNODE_ = $('<div/>').text(options.text)
										.addClass('lui-mcalendar-tip')
										.appendTo($(document.body));
		
		tipNode
			.css('left', e.pageX - (tipNode.width() / 2) - 8)
			.css('top', e.pageY - 36)
		
		window._MCALENDAR_TIPNODE_TIMER_ = setTimeout(function() {
			window._MCALENDAR_TIPNODE_.fadeOut();
			window._MCALENDAR_TIPNODE_ = null;
		}, options.duration);
	}

	module.exports = MonthCalendar;

});