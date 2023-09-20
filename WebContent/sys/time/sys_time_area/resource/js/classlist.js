define(function(require, exports, module) {
	
	function ClassList(element, data, options) {
		this.element = element;
		this.data = data || [];
		this.defaultOptions = {
			onSelect: function(claxx) {
				console.log('select', claxx);
			},
			onModify: function(claxx) {
				console.log('modify', claxx);
			},
			onDelete: function(claxx) {
				console.log('delete', claxx);
			},
			onAdd: function() {
				console.log('add');
			}
		};
		this.options = $.extend(this.defaultOptions, options);
		this.initialize();
	}
	
	ClassList.prototype = {
		initialize: function() {
			this.initProps();
			this.render();
			this.bindEvents();
		},
		initProps: function() { 
			this.element.addClass('class-container');
		},
		bindEvents: function() {
			var self = this;
			self.element.on('click', '.class-item-wrapper', function() {
				
				if($(this).hasClass('active')) {
					$(this).removeClass('active');
					self.options.onSelect(null);
				} else {
					self.element.find('.class-item-wrapper').removeClass('active');
					$(this).addClass('active');
				}
				
			});
		},
		render: function() {
			this.beforeRender();
			this.renderData();
		},
		beforeRender: function() {
			var self = this;
			self.createBtn = $('<div/>').addClass('class-btn')
								.attr('data-action', 'create')
								.append(
									$('<div/>')
										.addClass('class-btn-wrapper')
										.html('+ 新增班次')
								)
								.appendTo(self.element);
			
			self.createBtn.click(function() {
				self.options.onAdd();
			});
		},
		renderData: function() {
			var self = this;
			
			self.element.find('.class-item').remove();
			$.each(self.data || [], function(_, item) {
				var itemNode = $('<div/>').addClass('class-item').insertBefore(self.createBtn);
				
				if(item.type != '1') {
					var btnDel = $('<div/>').addClass('class-item-btn')
						.attr('data-action', 'remove')
						.append($('<span/>'))
						.appendTo(itemNode);
				
					btnDel.click(function() {
						self.options.onDelete(item);
					});
				
					var btnMod = $('<div/>').addClass('class-item-btn')
						.attr('data-action', 'modify')
						.append($('<span/>'))
						.appendTo(itemNode);
					
					btnMod.click(function() {
						self.options.onModify(item);
					});
				}

				var itemWrapperNode = $('<div/>')
										.addClass('class-item-wrapper')
										.appendTo(itemNode);
				
				itemWrapperNode.click(function() {
					self.options.onSelect(item);
				});
				
				$('<div/>')
					.addClass('class-item-check')
					.append($('<span/>'))
					.appendTo(itemWrapperNode);
				
				$('<span/>')
					.addClass('class-item-sign')
					.css('background-color', item.color || '')
					.appendTo(itemWrapperNode);
				
				var itemMainNode = $('<span/>')
									.addClass('class-item-main')
									.appendTo(itemWrapperNode);
				
				$('<div/>')
					.addClass('class-item-name')
					.text(item.name).appendTo(itemMainNode);
				
				var itemTimeNode = $('<div/>')
					.addClass('class-item-time')
					.appendTo(itemMainNode);
				
				$.each(item.times || [], function(__, time) {
					var endStr = '';
					if(time.overTimeType == 2){
						endStr = '(次日)';
					}
					
					$('<span/>').html((time.start || '') + ' ~ '+ (time.end || '') + ' ' + endStr )
						.appendTo(itemTimeNode);
				});
				
			});
		},
		setData: function(data) {
			
			// [
			//   {
			//     "isCustom": false,  //标记是否是自定义班次
			//     "fdId": "",         //通用班次ID
			//     "name": "xxx",      //班次名称 
			//	   "color": "#2B9AE0", //班次颜色
			//     "times": [          //班次详情
			//       {"start": "00:00", "end": "12:00"}
			//     ]
			//   }
			// ]
			
			this.data = data;
			this.renderData();
		},
		reset: function() {
			this.setData([]);
		},
		noSelect: function() {
			this.element.find('.class-item-wrapper').removeClass('active');
			this.options.onSelect(null);
		}
	};
	
	module.exports = ClassList;
	
});