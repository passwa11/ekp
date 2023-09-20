define(function(require, exports, module) {
	
	function TempSeatList(element, data, options) {
		this.element = element;
		this.data = data || [];
		this.defaultOptions = {
			onSelect: function(claxx) {
				console.log('select', claxx);
			}
		};
		this.options = $.extend(this.defaultOptions, options);
		this.initialize();
	}
	
	TempSeatList.prototype = {
		initialize: function() {
			this.render();
		},
		render: function() {
			this.beforeRender();
			this.renderData();
		},
		beforeRender: function() {
		},
		renderData: function() {
			var self = this;
			
			$.each(self.data || [], function(_, item) {
	          	var  itemWrapperNode = $('<div/>').addClass('lui_seat_fixBar_item').appendTo(self.element);
	          	
	          	if(self.options.mode == 'edit') {
	          		itemWrapperNode.click(function() {
	          			if($(this).hasClass('lui_seat_select')) {
	    					$(this).removeClass('lui_seat_select');
	    					$('.lui_seat_arrangement_wrap').removeClass(item.style);
	    					self.options.onSelect(null);
	    				} else {
	    					$('.lui_seat_fixBar_item').removeClass('lui_seat_select');
	    					$('.lui_seat_arrangement_wrap').addClass(item.style);
	    					$(this).addClass('lui_seat_select');
	    					self.options.onSelect(item);
	    				}
					});
          		}
          		$('<span/>').addClass(item.style).appendTo(itemWrapperNode);
          		$('<p/>').text(item.name).appendTo(itemWrapperNode);
				
			});
		},
		setData: function(data) {
			
			// [
			//   {
			//    
			//     "type": "1",         //类型
			//     "name": "xxx",      //名称 
			//	   "color": "#2B9AE0", //颜色
			//   }
			// ]
			this.data = data;
			this.renderData();
		},
		reset: function() {
			this.setData([]);
		},
		noSelect: function() {
			this.element.find('.lui_seat_graphic_icon').removeClass('lui_seat_select');
			this.options.onSelect(null);
		}
	};
	
	module.exports = TempSeatList;
	
});