define(function(require, exports, module) {
	
	function ElementList(element, data, options) {
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
	
	ElementList.prototype = {
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
			//self.element.find('.class-item').remove();
			
			var otherElement = $('<div/>').addClass('lui_seat_setting_aside_item_row').appendTo(self.element);
			var screenElement = $('<div/>').addClass('lui_seat_setting_aside_item_row').appendTo(self.element);
			var doorElement = $('<div/>').addClass('lui_seat_setting_aside_item_row').appendTo(self.element);
			
			$.each(self.data || [], function(_, item) {
	          	var  itemWrapperNode ; 
	          	if(item.type == "0" || item.type == "1" || item.type == "2" || item.type == "3"){
	          		itemWrapperNode = $('<div/>').addClass('lui_seat_graphic_item').appendTo(otherElement);
	          	}else if(item.type == "41" || item.type == "42" || item.type == "43" || item.type == "44"){
	          		itemWrapperNode = $('<div/>').addClass('lui_seat_graphic_item').appendTo(screenElement);
	          	}else if(item.type == "51" || item.type == "52" || item.type == "53" || item.type == "54"){
	          		itemWrapperNode = $('<div/>').addClass('lui_seat_graphic_item').appendTo(doorElement);
	          	}
	          	
	          	if(self.options.mode == 'edit') {
	          		itemWrapperNode.click(function() {
	          			if($(this).hasClass('lui_seat_select')) {
	    					$(this).removeClass('lui_seat_select');
	    		    		$('.lui_seat_setting_wrap').removeClass(item.style);
	    		    		self.options.onSelect(null);
	    				} else {
	    					$('.lui_seat_graphic_item').removeClass('lui_seat_select');
	    					$('.lui_seat_setting_wrap').removeClass('status_seat status_occupation status_speech');
	    					$('.lui_seat_setting_wrap').removeClass('status_screen_up status_screen_down status_screen_left status_screen_right');
	    		    		$('.lui_seat_setting_wrap').removeClass('status_door_up status_door_down status_door_left status_door_right');
	    					$('.lui_seat_setting_wrap').addClass(item.style);
	    					$(this).addClass('lui_seat_select');
	    					self.options.onSelect(item);
	    				}
					});
          		}
          		$('<div/>').addClass('lui_seat_graphic_icon').addClass(item.style).appendTo(itemWrapperNode);
          		$('<p/>').addClass('lui_seat_graphic_title').text(item.name).appendTo(itemWrapperNode);
				
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
	
	module.exports = ElementList;
	
});