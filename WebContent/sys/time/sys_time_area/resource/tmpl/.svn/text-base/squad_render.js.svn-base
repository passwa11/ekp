seajs.use(['lui/jquery', 'lui/topic', 'lui/dialog'], function (jquery, topic, dialog) {
	
	// 组件自带属性
	var srcData = data || [];
	var parentNode = render.parent.element;
	var vars = render.vars || {};

	// 转换数据格式
	var convertDataFunc = vars.convertDataFunc,
		name = vars.name;
	
	if(convertDataFunc && window[convertDataFunc]) {
		srcData = window[convertDataFunc](srcData);
	}
	
	// 班次类
	function SquadList(element, options) {
		this.element = element;
		this.element.addClass('squad-container');
		
		this.data = [];
		
		this.defaultOptions = {};
		this.options = $.extend(this.defaultOptions, options || {});
		
		this._index = 0;
		
		this.initialize();
		
	}
	
	SquadList.prototype = {
		
		initialize: function() {
			this.render();
			this.bindEvents();
		},
		
		bindEvents: function() {
			
			var self = this;
			
			self.element.on('click', '.squad-item-wrapper', function() {
				self.element.find('.squad-item-wrapper').removeClass('active');
				$(this).addClass('active');
				
				var symbol = $(this).attr('data-symbol');
				var squad = null;
				$.each(self.data || [], function(_, d) {
					if(d.symbol == symbol) {
						squad = d;
					}
				});
				
				if(squad) {
					topic.publish('/sys/time/selectSquad', squad);
				}
				
			});
		},
			
		setData: function(data) {
			this.data = data || [];
			this.convertData();
			this.renderData();
		},
		
		convertData: function() {
			var self = this;
			$.each(this.data || [], function(_, d) {
				d.symbol = name + self._index;
				self._index += 1;
			});
		},
		
		render: function() {
			this.renderCreateBtn();
			this.renderData();
		},
		
		renderCreateBtn: function() {
			var createBtn = this.createBtn = $('<div/>').addClass('squad-btn')
												.attr('data-action', 'create')
												.appendTo(this.element);
			$('<div/>')
				.addClass('squad-btn-wrapper')
				.html('+ 新增班次')
				.appendTo(createBtn);
			
			createBtn.click(function() {
				topic.publish('/sys/time/createSquad', vars);
			});
		},
		
		renderData: function() {
			
			var self = this;
			self.element.find('.squad-item').remove();
			$.each(self.data || [], function(_, d) {
				self.createItemNode(d).insertBefore(self.createBtn);
			});
			
		},
		
		addItem: function(item) {
			
			item.symbol = name + this._index;
			this._index += 1;
			
			this.data.push(item);
			this.createItemNode(item).insertBefore(this.createBtn);
			
		},
		
		setItem: function(item) {
			
			$.each(this.data || [], function(_, d) {
				
				if(item.symbol == d.symbol) {
					d.name = item.name;
					d.color = item.color;
					d.times = item.times;
				}
				
			});
			
			var itemNode = this.element.find('.squad-item[data-symbol="' + item.symbol + '"]');
			itemNode.find('.squad-item-name').text(item.name);
			itemNode.find('.squad-item-sign').css('background-color', item.color);
			
			var timeNode = itemNode.find('.squad-item-time');
			timeNode.empty();
			$.each(item.times || [], function(_, time) {
				$('<span/>').text((time[0] || '') + ' ~ ' + (time[1] || ''))
					.appendTo(timeNode);
			});
			
		},
		
		clearSelected: function() {
			this.element.find('.squad-item-wrapper').removeClass('active');
		},
		
		createItemNode: function(item) {
			
			var itemNode = $('<div/>').attr('data-symbol', item.symbol).addClass('squad-item');
			
			// 自定义班次数据删除与修改操作
			if(item.isCustom) {
				var btnDel = $('<div/>').addClass('squad-item-btn')
					.attr('data-action', 'remove')
					.attr('data-symbol', item.symbol)
					.append($('<span/>'))
					.appendTo(itemNode);
			
				btnDel.click(function() {
					dialog.confirm('确认删除该班次？', function(check) {
						if(check) {
							topic.publish('/sys/time/delSquad', item.symbol);
							itemNode.remove();
						}
					});
				});
				
				var squadItemMdfBtn = $('<div/>').addClass('squad-item-btn')
					.attr('data-action', 'modify')
					.attr('data-symbol', item.symbol)
					.append($('<span/>'))
					.appendTo(itemNode);
				
				squadItemMdfBtn.click(function() {
					topic.publish('/sys/time/modifySquad', {
						name: name,
						squad: item
					});
				});
				
			}
			
			var itemWrapperNode = $('<div/>').attr('data-symbol', item.symbol)
											.addClass('squad-item-wrapper')
											.appendTo(itemNode);
			
			$('<div/>')
				.addClass('squad-item-check')
				.append($('<span/>').html('&radic;'))
				.appendTo(itemWrapperNode);
			
			$('<span/>')
				.addClass('squad-item-sign')
				.css('background-color', item.color || '')
				.appendTo(itemWrapperNode);
			
			var itemMainNode = $('<span/>')
										.addClass('squad-item-main')
										.appendTo(itemWrapperNode);
			
			$('<div/>')
				.addClass('squad-item-name')
				.text(item.name).appendTo(itemMainNode);
			
			var squadItemTimeNode = $('<div/>')
				.addClass('squad-item-time')
				.appendTo(itemMainNode);
			
			$.each(item.times || [], function(_idx, _item) {
				$('<span/>').text((_item[0] || '') + ' ~ ' + (_item[1] || ''))
					.appendTo(squadItemTimeNode);
			});
			
			return itemNode;
		}
		
		
	};
	
	
	
	/////////////////////////////////////////
	// 主要渲染逻辑
	////////////////////////////////////////
	
	var squadList = new SquadList($(parentNode));
	squadList.setData(srcData);
	
	topic.subscribe('/sys/time/addSquad', function(payload) {
		
		if(payload.name != name || !payload.squad) {
			return;
		}
		
		payload.squad['isCustom'] = true;
		squadList.addItem(payload.squad);
	});
	
	topic.subscribe('/sys/time/setSquad', function(payload) {
		
		if(payload.name != name || !payload.squad) {
			return;
		}
		
		squadList.setItem(payload.squad);
	});
	
	topic.subscribe('/sys/time/clearSelected', function() {
		squadList.clearSelected();
	});
	
	done();

});