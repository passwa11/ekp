define(function(require, exports, module) {

	var $ = require('lui/jquery');
	
	var Seat = function(element,data, options) {
		
		this.element = $(element);
		
		this.defaultData = {
			/*data数据结构
			[
			 	{
				  	elementId,
				  	elementName,
				  	x,
				  	y,
				  	col,
				  	row,
				  	fdType,
				  	fdName
			    }
			]
			 */
			data: []
		};
		this.data = $.extend({}, this.defaultData, data);
		
		this.defaultOptions = {
			baseClass: 'lui-seat',
			mode: 'view',
			cols:22,
			rows:15,
			x: 'X',
			y: 'Y',
			beforeRenderCol: function(col, year, month, date, element) {
				//DO NOTHING
			},
	    	renderCol: function(col, year, month, date, element) {
	    		//DO NOTHING
	    	},
	    	afterRenderCol: function(col, year, month, date, element) {
	    		//DO NOTHING
	    	},
			beforeRenderRow: function(row, data, element) {
				//DO NOTHING
			},
			renderRow: function(row, element) {
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

	Seat.prototype = {
			
		init: function() {
			this.element.addClass(this.options.baseClass);
			this.element.attr('data-mode', this.options.mode);
		},
		
		refresh: function() {
			this.render();
		},
		
		render: function() {
			this.element.empty();
			this.seat = null;
			this.renderDefaultSeat();
			this.renderData();
		},
		
		renderDefaultSeat: function() {

			var self = this;
			self.element.find('.' + this.options.baseClass + '-b').remove();
			var seat = this.seat = this.element;
			
			var cols = this.options.cols;
			var rows = this.options.rows;
			
			/**
			 * 渲染行
			 */
			var seatB = this.seatB = $('<div/>').addClass(this.options.baseClass + '-b')
												.appendTo(seat);
			/**
			 * 渲染列表
			 */
			
			var seatBR = this.seatBR = $('<div/>').addClass(this.options.baseClass + '-b-r')
												.appendTo(seatB);
			var seatMain = this.seatMain = $('<table/>').addClass(this.options.baseClass + '-main')
													.appendTo(seatBR);
			for(var i = 1; i <= rows; i++) {
				
				var mainRow = $('<tr/>')
								.attr('data-type', 'main-row')
								.addClass(self.options.baseClass + '-main-row')
								.appendTo(seatMain);
				
				for(var j = 1; j <= cols; j++) {
					var mainCol = $('<td/>')
									.addClass(self.options.baseClass + '-main-col')
									.addClass(self.options.baseClass + '-main-col-temp')
									.attr('align', 'center')
									.attr('data-type', 'main-col')
									.attr('data-x', j)
									.attr('data-y', i)
									.appendTo(mainRow);
				}
			}
			
		},
		
		renderData: function() {
			var self = this;
			if(!this.seat) {
				return;
			}
			
			this.seat.find('.' + this.options.baseClass + '-main-col').each(function() {
				$(this).empty()
				self.options.beforeRenderData($(this), null, self.element);
			});
			
			self._renderDefaultSeat(this.data.data);
			
			$.each(this.data.data || [], function(_, d) {
				self._renderData(d);
				
			});
		},
		
		_renderDefaultSeat : function(data){
			var self = this;
			var table = this.element.find(
					"."+this.options.baseClass + '-main'
				);
			
			var i = 0, l = data.length;
			for(i; i < l; i++) {
				var d = data[i];
				if(d.clazz.type == "2" || d.clazz.type == "41" || d.clazz.type == "42" || d.clazz.type == "43" || d.clazz.type == "44"){
					var cell = this.seat.find(
							'.' + this.options.baseClass 
							+ '-main-col[data-x="' + d.x + '"]'
							+ '[data-y="' + d.y + '"]'
						);
					if(cell[0]){
						var rowSpan = cell[0].rowSpan;
						var colSpan = cell[0].colSpan;
						if(rowSpan != (d.row+1) || colSpan != (d.col+1)){
							self._mergeCell(table[0],d.y,d.y+d.row,d.x,d.x+d.col);
						}
					}
				}
			}
		},
		
		/**
         * 合并单元格
         * @param table1    表格的ID
         * @param startRow  起始行
         * @param endRow    结束行
         * @param startCol   起始列
         * @param endCol   结束列
         */
        _mergeCell : function(table, startRow, endRow, startCol,endCol) {
            if(!table || !table.rows || table.rows.length <= 0) {
                return;
            }
            if(startRow > endRow && endRow != 0) {
                return;
            }
            if(startCol == endCol && startRow == endRow){
            	return;
            }
            for(var i = startRow - 1; i < endRow; i++) {
            	for(var j = startCol - 1; j < endCol ; j++){
            		if( i == startRow - 1 && j == startCol - 1 ){
            			continue;
            		}
            		var cell = this.seat.find(
    						'.' + this.options.baseClass 
    						+ '-main-col[data-x="' + (j+1) + '"]'
    						+ '[data-y="' + (i+1) + '"]'
    					);
            		if(cell[0]){
            			table.rows[i].removeChild(cell[0]);
            		}
            	}
            }
            var currentCell = this.seat.find(
					'.' + this.options.baseClass 
					+ '-main-col[data-x="' + startCol + '"]'
					+ '[data-y="' + startRow + '"]'
				);
            currentCell[0].rowSpan = currentCell[0].rowSpan + endRow - startRow;
            currentCell[0].colSpan = currentCell[0].colSpan + endCol - startCol;
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
			if(!this.seat) {
				return;
			}
			
			var x = data.x;
			var y = data.y;
			var nodeType = data.clazz.nodeType;
			
			var cell = this.seat.find(
							'.' + this.options.baseClass 
							+ '-main-col[data-x="' + x + '"]'
							+ '[data-y="' + y + '"]'
						);
			
			if(cell.get(0)) {
				cell.empty();
				
				cell.attr('data-has-data', 'true');
				
				if(nodeType != "2"){
					this.options.beforeRenderData(cell, data);
				}
				
				this.options.renderData(cell, data);
				
			}
			
		},
		
		setData: function(data, reRender) {
			this.data = $.extend(this.data, data);
			
			if(reRender) {
				this.render();
			} else {
				this.renderData(reRender);
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
				
				var startX = parseInt(this._start.attr('data-x'));
				var endX = parseInt(this._end.attr('data-x'));
				if(startX > endX) {
					t = startX;
					startX = endX;
					endX = t;
				}
				
				var startY = parseInt(this._start.attr('data-y'));
				var endY = parseInt(this._end.attr('data-y'));
				if(startY > endY) {
					t = startY;
					startY = endY;
					endY = t;
				}
				for (var i =startY ; i <=endY; i++) {
					for(var j = startX; j <= endX; j++) {
						res.push({
							x:j,
							y:i
						});
					}
				}
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
						+ '[data-x="' + r.x + '"]'
						+ '[data-y="' + r.y + '"]'
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
		
		addCol:function(data){
			var cols = this.options.cols + 1;
			this.options = $.extend({}, this.options, {cols:cols});
			this.renderDefaultSeat();
			this.setData({
				data: data
			});
		},
		
		addRow:function(data){
			var rows = this.options.rows + 1;
			this.options = $.extend({}, this.options, {rows:rows});
			this.renderDefaultSeat();
			this.setData({
				data: data
			});
		},
		
		setColAndRow:function(cols,rows){
			this.options = $.extend({}, this.options, {cols:cols,rows:rows});
			this.renderDefaultSeat();
		},
		
		_buildContextMenu: function(e, cell, showItems) {
			
			var self = this;
			
			this._hideContextMenu();
			
			var x = parseInt(cell.attr('data-x'));
			var y = parseInt(cell.attr('data-y'));
			
			var data = {
				x: x,
				y: y
			};
			
			var contextMenu = this._contextMenu = $('<div/>').addClass(this.options.baseClass + '-contextmenu')
									.css('display', 'none')
									.appendTo($(document.body));
			if(e.pageX + 156 > window.innerWidth) {
				contextMenu.css('left', e.pageX - 156);
			} else {
				contextMenu.css('left', e.pageX);
			}
			contextMenu.css('top', e.pageY-window.scrollY);
			
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
						d.click(cell,data);
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
		}
		
	};
	
	module.exports = Seat;

});