define(function(require, exports, module) {
	
	function PersonList(element, data, options) {
		this.element = element;
		this.data = data || [];
		this.defaultOptions = {
			mode: 'edit',
			onSelect: function(claxx) {
				console.log('select', claxx);
			}
		};
		this.options = $.extend(this.defaultOptions, options);
		this.initialize();
	}
	
	PersonList.prototype = {
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
			self.element.find('.lui_seat_btn').remove();
			$.each(self.data || [], function(_, item) {
				var itemNode = $('<span/>').addClass('lui_seat_btn lui_seat_btn_plain')
					.attr('data-elementid', item.elementId).text(item.elementName).appendTo(self.element);
				if(self.options.mode == 'edit') {
					itemNode.click(function() {
						if($(this).hasClass('lui_seat_person_selected')){
							return;
						}
						if($(this).hasClass('lui_text_primary')) {
							$(this).removeClass('lui_text_primary');
							self.options.onSelect(null);
						} else {
							$('.lui_seat_btn').removeClass('lui_text_primary');
							$('.lui_seat_fixBar_item').removeClass('lui_seat_select');
							$('.lui_seat_arrangement_wrap').removeClass('status_temporary');
							$(this).addClass('lui_text_primary');
							self.options.onSelect(item);
							
						}
					});
				}
				
			});
		},
		setData: function(data) {
			
			// [
			//   {
			//    
			//     "elementName": "xxx",      //人员name 
			//	   "elementId": "", //人员id
			//   }
			// ]
			this.data = data;
			this.renderData();
		},
		reset: function() {
			this.setData([]);
		},
		noSelect: function() {
			this.element.find('.lui_seat_btn').removeClass('lui_text_primary');
			this.options.onSelect(null);
		}
	};
	
	module.exports = PersonList;
	
});