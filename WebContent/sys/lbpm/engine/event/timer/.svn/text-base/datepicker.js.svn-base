/**
 * 日历选择器，主要提供一个月的日历，此不是严格的日历，不管是哪个月份都是把31日选项；同时提供一年的月历，提供12月选项。日历可以多选。
 * 
 * @author 龚健
 * @since 2015.7.17
 */
(function($) {

var drawHack = function( element, resultElement, createDrawerFunc ) {
	var drawer = $.data( element, "drawer" );
	if ( drawer ) {
		return drawer;
	}

	drawer = createDrawerFunc( element, resultElement );
	$.data( element, "drawer", drawer );
	return drawer;
};

$.extend($.fn, {
	drawMonthCalendar: function( resultElement, status ) {
		return drawHack( this[0], resultElement, function( element, resultElem ) {
			var _status = status || "edit";
			return new $.drawer( element, "month", resultElem, {status: _status} );
		});
	},

	drawYearCalendar: function( resultElement, months, status ) {
		return drawHack( this[0], resultElement, function( element, resultElem ) {
			var _months = months || {}, _status = status || "edit";
			return new $.drawer( element, "year", resultElem, {status: _status, messages: _months} );
		});
	},

	setDateCalendar: function( value ) {
		var drawer = $.data( this[0], "drawer" );
		if ( drawer ) {
			drawer.dateValue( value );
		}
	}
});

$.drawer = function( element, type, resultElement, options ) {
	this.settings = $.extend( true, {}, $.drawer.defaults, options );
	this.element = element;
	this.cells = [];
	this.selected = [];
	this.resultElement = resultElement;
	this.init( type );
};

$.extend($.drawer, {
	defaults: {
		status: 'edit',
		messages: {}
	},

	months : {
		'Jan': 'January',
		'Feb': 'February',
		'Mar': 'March',
		'Apr': 'April',
		'May': 'May',
		'Jun': 'June',
		'Jul': 'July',
		'Aug': 'August',
		'Sept': 'September',
		'Oct': 'October',
		'Nov': 'November',
		'Dec': 'December'
	},

	prototype: {
		init: function( type ) {
			var _type = type || "month";
			if (_type == "month") {
				this.drawMonthCalendar();
			} else if (_type == "year") {
				this.drawYearCalendar();
			}
			
			if (this.settings.status == "edit") this.bindEvent();
		},
		// 绘制一个月的日表格
		drawMonthCalendar: function() {
			var table = document.createElement("table"), index = 1;
			for ( var i = 0; i < 5; i++ ) {
				var row = table.insertRow(-1);
				for ( var j = 0; j < 7; j++ ) {
					var _cell = row.insertCell(-1);
					// 超过31天后...
					if (index < 32) {
						// 渲染单元格
						this.renderCell(_cell, "" + index);
						this.resizeCell(_cell);
						// 输出描述
						_cell.appendChild(document.createTextNode("" + (index++)));
					}
				}
			};
			this.element.appendChild(table);
		},
		// 绘制一年的月表格
		drawYearCalendar: function() {
			var months = "Jan Feb Mar Apr May Jun Jul Aug Sept Oct Nov Dec".split(" ");
			// 绘制日历
			var table = document.createElement("table"), index = 0;
			for ( var i = 0; i < 2; i++ ) {
				var row = table.insertRow(-1);
				for ( var j = 0; j < 6; j++ ) {
					var _cell = row.insertCell(-1);
					// 月的文字描述
					var monthKey = months[index++], month = this.settings.messages[monthKey] || $.drawer.months[monthKey];
					// 渲染单元格
					this.renderCell(_cell, monthKey);
					this.resizeCell(_cell, 30);
					// 输出描述
					_cell.appendChild(document.createTextNode(month));
				}
			}
			this.element.appendChild(table);
		},
		// 渲染单元格
		renderCell: function( cell, value ) {
			// 格式渲染
			$(cell).css({
				"border": "1px #999999 solid",
				"border-collapse": "collapse",
				"background": "#F0F0F0",
				"padding": "5px !important",
				"font-family": "Arial",
				"word-break": "break-all",
				"vertical-align": "middle",
				"text-align": "center"
			});
			// 鼠标样式...
			if (this.settings.status == "edit") {
				$(cell).css({"cursor": "pointer"});
			}
			// 记录单元格信息
			this.cells.push( { cell: cell, value: value } );
		},
		// 设置单元格大小
		resizeCell: function( cell, width, height ) {
			$(cell).width( width || 20 );
			$(cell).height( height || 20);
		},
		// 单元格绑定选中事件
		bindEvent: function() {
			var _drawer = this;
			// 设置单元格点击事件
			$.each(this.cells, function( prop, val ) {
				$(val.cell).click(function() {
					var position = $.inArray(val.value, _drawer.selected);
					if ( position > -1 ) {
						// 取消选中单元格
						$(this).css({
							"background-color": "#F0F0F0",
							"color": ""
						});
						// 剔除当前单元格所属值
						_drawer.selected.splice(position, 1);
					} else {
						// 选中单元格
						$(this).css({
							"background-color": "#0092FB",
							"color": "#FFFFFF"
						});
						// 追加当前单元格所属值
						_drawer.selected.push(val.value);
					}
					// 更新相应的输出值
					$(_drawer.resultElement).val(_drawer.selected.join(","));
				});
			});
		},
		// 设置被选中的单元格或返回选中值
		dateValue: function( value ) {
			if ( !arguments.length ) {
				return this.selected.join(",");
			}

			var selected = value.split(",");
			// 设置选择单元格
			$.each(this.cells, function( prop, val ) {
				if ( $.inArray(val.value, selected) > -1 ) {
					$(val.cell).css({
						"background-color": "#0092FB",
						"color": "#FFFFFF"
					});
				}
			});
			// 更新被选择值
			this.selected = selected;
			// 更新输出值
			$(this.resultElement).val(value);
		}
	}
});

})(jQuery);